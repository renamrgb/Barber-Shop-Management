package com.rpx.bsm.entities;

import com.rpx.bsm.records.ScheduleRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Customer client;
    @Column(nullable = false)
    private LocalDateTime date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "procedure_id", nullable = false)
    private Procedure procedure;

    public Schedule(Customer client, LocalDateTime date, Procedure procedure) {
        this.client = client;
        this.date = date;
        this.procedure = procedure;
    }
    public Schedule(ScheduleRecord r) {
        this.client = r.client();
        this.date = r.date();
        this.procedure = r.procedure();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(id, schedule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", client=" + client +
                ", date=" + date +
                ", procedure=" + procedure +
                '}';
    }
}
