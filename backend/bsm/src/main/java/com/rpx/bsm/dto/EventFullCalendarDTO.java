package com.rpx.bsm.dto;

import com.rpx.bsm.entities.Schedule;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class EventFullCalendarDTO  implements Serializable {
    private Long id;
    private String title;
    private LocalDateTime start;
    private LocalDateTime end;
    private String backgroundColor;

    public EventFullCalendarDTO(Schedule obj) {
        setId(obj.getId());
        setTitle(obj.getCustomer().getUser().getName());
        setStart(obj.getStartDate());
        setEnd(obj.getEndDate());
        if(obj.getPaymentSchedule().getPaymentMethod() != null)
            setBackgroundColor("red");
        else
            setBackgroundColor("blue");
    }
}
