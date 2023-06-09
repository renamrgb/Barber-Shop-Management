package com.rpx.bsm.services;

import com.rpx.bsm.dto.EventFullCalendarDTO;
import com.rpx.bsm.entities.*;
import com.rpx.bsm.records.ScheduleRecord;
import com.rpx.bsm.records.util.WorkSchedule;
import com.rpx.bsm.repositories.ScheduleRepository;
import com.rpx.bsm.resources.exceptions.DatabaseException;
import com.rpx.bsm.resources.exceptions.DefaultErrorException;
import com.rpx.bsm.resources.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    @Autowired
    private LoyaltyCardService loyaltyCardService;
    @Autowired
    private BlockedTimesService blockedTimesService;

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

    private List<Schedule> findByDayBetween(LocalDateTime startOfDay, LocalDateTime endOfDay, Long professionalId) {
        LocalDateTime start = startOfDay.withHour(0).withMinute(0).withSecond(0).withNano(0);
        return repository.findByStartDateBetweenAndProfessional(start, endOfDay, professionalId);
    }

    @Transactional
    public List<LocalTime> availableTimes(LocalDateTime startOfDay, LocalDateTime endOfDay, Long professionalId) {
        Boolean isSaturday = validateDayOfTheWeek(startOfDay);
        List<Schedule> timesNotAvailable = findByDayBetween(startOfDay, endOfDay, professionalId);
        List<BlockedTimes> blockedTimes = blockedTimesService.findByDateAndProfessional(startOfDay, professionalId);
        List<LocalTime> availableTimes;
        if (!isSaturday) availableTimes = createArrayOfAvailableTimes();
        else availableTimes = createSaturdayArrayOfAvailableTimes();
        availableTimes = removeBlockedSchedules(availableTimes, blockedTimes);
        availableTimes = removeUnavailableTimes(availableTimes, timesNotAvailable);
        return availableTimes;
    }

    private Boolean validateDayOfTheWeek(LocalDateTime date) {
        ParameterValue parameterValue;
        Boolean isSaturday = false;
        if (date.getDayOfWeek() == DayOfWeek.MONDAY) {
            parameterValue = parameterService.findByPameterKey("WORKS_ON_MONDAY");
            if (parameterValue.getParameter_value() == "false")
                throw new DefaultErrorException("Não é permitido realizar atendimentos no segunda-feira");
        }
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
            parameterValue = parameterService.findByPameterKey("WORKS_ON_SATURDAY");
            if (parameterValue.getParameter_value() == "false")
                throw new DefaultErrorException("Não é permitido realizar atendimentos no sábado");
            else isSaturday = true;
        }
        if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            parameterValue = parameterService.findByPameterKey("WORKS_ON_SUNDAY");
            if (parameterValue.getParameter_value() == "false")
                throw new DefaultErrorException("Não é permitido realizar atendimentos no domingo");
        }
        return isSaturday;
    }

    private List<LocalTime> removeUnavailableTimes(List<LocalTime> availableTimes, List<Schedule> schedules) {
        for (Schedule schedule : schedules) {
            LocalTime start = schedule.getStartDate().toLocalTime();
            LocalTime end = schedule.getEndDate().toLocalTime();
            removeUnavailableTimes(availableTimes, time -> (time.equals(start) || time.isAfter(start)), end);
        }
        return availableTimes.stream().sorted().collect(Collectors.toList());
    }

    private List<LocalTime> removeBlockedSchedules(List<LocalTime> availableTimes, List<BlockedTimes> blockedTimes) {
        for (BlockedTimes b : blockedTimes) {
            LocalTime start = b.getStartDate().toLocalTime();
            LocalTime end = b.getEndDate().toLocalTime();
            removeUnavailableTimes(availableTimes, time -> (time.equals(start) || time.isAfter(start)), end);
        }
        return availableTimes.stream().sorted().collect(Collectors.toList());
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

    private List<LocalTime> createSaturdayArrayOfAvailableTimes() {
        List<LocalTime> availableTimes = new ArrayList<>();
        WorkSchedule workSchedule = parameterService.getSaturdayWorkTime();
        LocalTime starTime = workSchedule.getStarTime();

        while (!starTime.equals(workSchedule.getEndTime())) {
            availableTimes.add(starTime);
            starTime = starTime.plusMinutes(30);
        }

//        final LocalTime lunchStartTime = workSchedule.getLunchStartTime();
//        final LocalTime lunchEndTime = workSchedule.getLunchEndTime();
//
//        removeUnavailableTimes(availableTimes, time -> time.isAfter(lunchStartTime.minusMinutes(30)), lunchEndTime);

        return availableTimes;
    }

    private void removeUnavailableTimes(List<LocalTime> availableTimes, Function<LocalTime, Boolean> condition, LocalTime endTime) {
        availableTimes.removeIf(time -> condition.apply(time) && time.isBefore(endTime));
    }

    public List<EventFullCalendarDTO> consultScheduledTimes() {
        List<BlockedTimes> blockedTimes = blockedTimesService.getAll();
        List<Schedule> schedules = repository.findAll();
        List<EventFullCalendarDTO> listDto = schedules.stream().map(x -> new EventFullCalendarDTO(x)).collect(Collectors.toList());
        for (BlockedTimes b : blockedTimes) {
            listDto.add(new EventFullCalendarDTO(b));
        }

        return listDto;
    }

    @Transactional
    public Schedule update(Long id, ScheduleRecord record) {
        try {
            Schedule obj = repository.getReferenceById(id);
            loyaltyCardService.update(record.client().getLoyaltyCard());
            loyaltyCardService.setQtyUsed(record.client().getLoyaltyCard());
            if (record.products().size() < obj.getServiceItems().size())
                serviceItemsService.removenonCommonItems(obj.getServiceItems(), record.products());

            if (record.payment() != null && record.startDate().toLocalDate().compareTo(LocalDate.now()) > 0)
                throw new DefaultErrorException("Você não pode finalizar um atendimento futuro");
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
        for (ServiceItems s : record.products()) {
            s.setSchedule(obj);
            serviceItems.add(s);
        }
        obj.setServiceItems(serviceItems);
        if (record.payment().getPaymentMethod().getId() != null) {
            obj.getPaymentSchedule().setSchedule(obj);
            obj.getPaymentSchedule().setPaymentMethod(new PaymentMethod(record.payment().getPaymentMethod().getId()));
            obj.getPaymentSchedule().setAmount(record.payment().getAmount());
            obj.getPaymentSchedule().setDiscount(record.payment().getDiscount());
            obj.getPaymentSchedule().setGrossvalue(record.payment().getGrossvalue());
        }
        return obj;
    }

    public void delete(Long id) {
        try {
            Schedule obj = findById(id);
            if (obj.getPaymentSchedule().getPaymentMethod() == null && obj.getPaymentSchedule().getAmount() == null && obj.getPaymentSchedule().getGrossvalue() == null)
                repository.deleteById(id);
            else
                throw new DefaultErrorException("Não é permitido excluir atendimentos que já foram pagos");
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Transactional
    public void reverseService(Long id) {
        try {
            LocalDate dateNow = LocalDate.now();
            Schedule obj = repository.getReferenceById(id);
            int days = dateNow.compareTo(obj.getStartDate().toLocalDate());
            if (days == 0) {
                obj.getPaymentSchedule().setPaymentMethod(null);
                obj.getPaymentSchedule().setAmount(null);
                obj.getPaymentSchedule().setDiscount(null);
                obj.getPaymentSchedule().setGrossvalue(null);
            } else throw new DefaultErrorException("Não é permitido estornar atendimentos fora da data dele");
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

}
