package com.rpx.bsm.services;

import com.rpx.bsm.entities.Parameter;
import com.rpx.bsm.entities.ParameterValue;
import com.rpx.bsm.records.ParameterRecord;
import com.rpx.bsm.records.util.WorkSchedule;
import com.rpx.bsm.repositories.ParameterRepository;
import com.rpx.bsm.resources.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ParameterService {
    @Autowired
    private ParameterRepository repository;
    @Autowired
    private OrganizationService organizationService;

    public List<Parameter> findAll() {
        return repository.findAll();
    }

    public WorkSchedule getTimeWork(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        WorkSchedule workSchedule = new WorkSchedule();
        Parameter parameter = findById();
        List<ParameterValue> parameterValues =  parameter.getParameterValues();

        for (ParameterValue e : parameterValues){
            if(e.getParameter_key().equals("START_TIME")){
                workSchedule.setStarTime(LocalTime.parse(e.getParameter_value(), formatter));
            }
            if(e.getParameter_key().equals("LUNCH_START_TIME")){
                workSchedule.setLunchStartTime(LocalTime.parse(e.getParameter_value(), formatter));
            }
            if(e.getParameter_key().equals("LUNCH_BREAK_TIME")){
                workSchedule.setLunchEndTime(LocalTime.parse(e.getParameter_value(), formatter));
            }
            if(e.getParameter_key().equals("END_TIME")){
                workSchedule.setEndTime(LocalTime.parse(e.getParameter_value(), formatter));
            }

        }
        return workSchedule;
    }

    public WorkSchedule getSaturdayWorkTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        WorkSchedule workSchedule = new WorkSchedule();
        Parameter parameter = findById();
        List<ParameterValue> parameterValues =  parameter.getParameterValues();

        for (ParameterValue e : parameterValues){
            if(e.getParameter_key().equals("SATURDAY_WORKING_HOURS")){
                workSchedule.setStarTime(LocalTime.parse(e.getParameter_value(), formatter));
            }
            if(e.getParameter_key().equals("END_OF_WORK_HOURS_ON_SATURDAY")){
                workSchedule.setEndTime(LocalTime.parse(e.getParameter_value(), formatter));
            }

        }
        return workSchedule;
    }

    public Parameter findById() {
        Optional<Parameter> obj = repository.findById(1L);
        Parameter entity = obj.orElseThrow(() -> new ResourceNotFoundException(1L));
        return entity;
    }

    public ParameterValue findByPameterKey(String parameterKey) {
        ParameterValue entity = repository.findByParameterKey(parameterKey);
        return entity;
    }

    @Transactional
    public Parameter update(Long id, ParameterRecord obj) {
        try {
            Parameter entity = findById();
            organizationService.update(obj.organization().getId(), obj.organization());
            return repository.save(updateData(obj, entity));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private Parameter updateData(ParameterRecord r, Parameter obj) {
        for (int i = 0; i < r.parameterValues().size(); i++) {
            obj.getParameterValues().get(i).setParameter_key(r.parameterValues().get(i).getParameter_key());
            obj.getParameterValues().get(i).setParameter_value(r.parameterValues().get(i).getParameter_value());
            obj.getParameterValues().get(i).setParameter(obj);
        }
        obj.setOrganization(r.organization());
        return obj;
    }
}
