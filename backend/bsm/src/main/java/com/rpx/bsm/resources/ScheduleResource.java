package com.rpx.bsm.resources;

import com.rpx.bsm.dto.*;
import com.rpx.bsm.entities.Product;
import com.rpx.bsm.entities.Professional;
import com.rpx.bsm.entities.Schedule;
import com.rpx.bsm.entities.StockEntry;
import com.rpx.bsm.records.ProductRecord;
import com.rpx.bsm.records.ProfessionalRecord;
import com.rpx.bsm.records.ScheduleRecord;
import com.rpx.bsm.services.AddressService;
import com.rpx.bsm.services.ScheduleService;
import com.rpx.bsm.services.StockEntryService;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping(value = "/schedules")
public class ScheduleResource {
    @Autowired
    private ScheduleService service;

    @Transactional
    @GetMapping(value = "/paged")
    public ResponseEntity<Page<ScheduleDTO>> findAllPaged(Pageable pageable) {
        Page<Schedule> list = service.find(pageable);
        Page<ScheduleDTO> listDto = list.map(x -> new ScheduleDTO(x));
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping()@Transactional
    public ResponseEntity<List<EventFullCalendarDTO>> consultScheduledTimes() {
        List<Schedule> list = service.consultScheduledTimes();
        List<EventFullCalendarDTO> listDto = list.stream().map(x -> new EventFullCalendarDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ScheduleDTO> findById(@PathVariable Long id) {
        Schedule obj = service.findById(id);
        return ResponseEntity.ok().body(new ScheduleDTO(obj));
    }

    @PostMapping
    public ResponseEntity<ScheduleDTO> insert(@Valid @RequestBody ScheduleRecord record) {
        Schedule obj = service.insert(record);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(new ScheduleDTO(obj));
    }

    @GetMapping("/available")
    public List<LocalTime> getAvailableTimes(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                             @RequestParam("professionalId") Long professionalId) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);
        return service.availableTimes(startOfDay, endOfDay, professionalId);
    }
}
