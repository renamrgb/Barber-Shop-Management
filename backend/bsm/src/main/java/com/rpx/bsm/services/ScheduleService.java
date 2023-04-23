package com.rpx.bsm.services;

import com.rpx.bsm.entities.Procedure;
import com.rpx.bsm.entities.Professional;
import com.rpx.bsm.entities.Schedule;
import com.rpx.bsm.entities.ServiceItems;
import com.rpx.bsm.records.ProcedimentoIdRecord;
import com.rpx.bsm.records.ProfessionalRecord;
import com.rpx.bsm.records.ScheduleRecord;
import com.rpx.bsm.records.util.WorkSchedule;
import com.rpx.bsm.repositories.ScheduleRepository;
import com.rpx.bsm.resources.exceptions.DatabaseException;
import com.rpx.bsm.resources.exceptions.ResourceNotFoundException;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.sound.midi.Soundbank;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository repository;
    @Autowired
    private ParameterService parameterService;
    @Autowired
    private ServiceItemsService serviceItemsService;

    public Schedule insert(ScheduleRecord record) {
        return repository.save(new Schedule(record));
    }

    @Transactional
    public Page<Schedule> find(Pageable pageable) {
        Page<Schedule> list = repository.findAll(pageable);
        return list;
    }

    public Schedule findById(Long id) {
        Optional<Schedule> obj = repository.findById(id);
        return obj.get();
    }

    public List<Schedule> findByDayBetween(LocalDateTime startOfDay, LocalDateTime endOfDay, Long professionalId) {
        LocalDateTime start = startOfDay.withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = endOfDay.withHour(23).withMinute(59).withSecond(59).withNano(59);
        return repository.findByStartDateBetweenAndProfessional(start, endOfDay, professionalId);
    }

    @Transactional
    public List<LocalTime> availableTimes(LocalDateTime startOfDay, LocalDateTime endOfDay, Long professionalId) {
        List<Schedule> timesNotAvailable = findByDayBetween(startOfDay, endOfDay, professionalId);
        List<LocalTime> availableTimes = createArrayOfAvailableTimes();
        return removeUnavailableTimes(availableTimes, timesNotAvailable);
    }

//    private List<LocalTime> removeUnavailableTimes(List<LocalTime> availableTimes, List<Schedule> schedules) {
//        int j = 0, i = 0;
//        LocalTime timeToCheck, startTime, endTime;
//        Boolean itsBetween;
//
//        while (i < availableTimes.size() && j < schedules.size()) {
//            startTime = schedules.get(j).getStartDate().toLocalTime();
//            endTime = schedules.get(j).getEndDate().toLocalTime();
//            timeToCheck = availableTimes.get(i);
//            itsBetween = isBetween(timeToCheck, startTime, endTime);
//            if (itsBetween) {
//                while (itsBetween && i < availableTimes.size()) {
//                    availableTimes.remove(availableTimes.get(i));
//                    timeToCheck = availableTimes.get(i);
//                    itsBetween = isBetween(timeToCheck, startTime, endTime);
//                }
//                j++;
//            }
//            i++;
//        }
//        sortLocalTimeList(availableTimes);
//        return availableTimes;
//    }

    private boolean isBetween(LocalTime timeToCheck, LocalTime startTime, LocalTime endTime) {
        // Verifica se o horário a ser checado é posterior ou igual ao horário de início
        boolean isAfterStartTime = timeToCheck.isAfter(startTime) || timeToCheck.equals(startTime);
        // Verifica se o horário a ser checado é anterior ao horário final
        boolean isBeforeEndTime = timeToCheck.isBefore(endTime);
        // Retorna verdadeiro se o horário a ser checado estiver entre os dois horários passados
        return isAfterStartTime && isBeforeEndTime;
    }

//    private List<LocalTime> createArrayOfAvailableTimes() {
//        List<LocalTime> availableTimes = new ArrayList<>();
//        WorkSchedule workSchedule = parameterService.getTimeWork();
//        LocalTime localDateTime = workSchedule.getStarTime();
//        while (!localDateTime.equals(workSchedule.getEndTime())) {
//            if(!(isBetween(localDateTime, workSchedule.getLunchStartTime(), workSchedule.getLunchEndTime()))){
//                availableTimes.add(localDateTime);
//            }
//            localDateTime = localDateTime.plusMinutes(30);
//        }
//        return availableTimes;
//    }

    private void sortLocalTimeList(List<LocalTime> times) {
        Collections.sort(times, (t1, t2) -> t1.getHour() - t2.getHour());
    }


    private List<LocalTime> removeUnavailableTimes(List<LocalTime> availableTimes, List<Schedule> schedules) {
        for (Schedule schedule : schedules) {
            LocalTime start = schedule.getStartDate().toLocalTime();
            LocalTime end = schedule.getEndDate().toLocalTime();
            removeUnavailableTimes(availableTimes, time -> (time.equals(start) || time.isAfter(start)), end);
        }
        return availableTimes.stream()
                .sorted()
                .collect(Collectors.toList());
    }
    
    private List<LocalTime> createArrayOfAvailableTimes() {
        List<LocalTime> availableTimes = new ArrayList<>();
        WorkSchedule workSchedule = parameterService.getTimeWork();
        LocalTime starTime = workSchedule.getStarTime();

        while (!starTime.equals(workSchedule.getEndTime())) {
            availableTimes.add(starTime);
            starTime = starTime.plusMinutes(30);
        }

        final LocalTime lunchStartTime = workSchedule.getLunchStartTime();
        final LocalTime lunchEndTime = workSchedule.getLunchEndTime();

        removeUnavailableTimes(availableTimes, time -> time.isAfter(lunchStartTime.minusMinutes(30)), lunchEndTime);

        return availableTimes;
    }

    private void removeUnavailableTimes(List<LocalTime> availableTimes, Function<LocalTime, Boolean> condition, LocalTime endTime) {
        availableTimes.removeIf(time -> condition.apply(time) && time.isBefore(endTime));
    }

    public List<Schedule> consultScheduledTimes(){
        return repository.findAll();
    }

    @Transactional
    public Schedule update(Long id, ScheduleRecord record) {
        try {
            Schedule obj = repository.getReferenceById(id);
            if(record.products().size() < obj.getServiceItems().size())
                serviceItemsService.removenonCommonItems(obj.getServiceItems(), record.products());
            obj = updateData(record, obj);
            return repository.save(obj);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }
    private Schedule updateData(ScheduleRecord record, Schedule obj) {
        obj.setProfessional(record.professional());
        obj.setCustomer(record.client());
        obj.setProcedures(record.procedures());
        obj.setEndDate(record.endDate());
        obj.setStartDate(record.startDate());
        List<ServiceItems> serviceItems = new ArrayList<>();
        for(ServiceItems s : record.products()){
            s.setSchedule(obj);
            serviceItems.add(s);
        }
        obj.setServiceItems(serviceItems);
        return obj;
    }
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
