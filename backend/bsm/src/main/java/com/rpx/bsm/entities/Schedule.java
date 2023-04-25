package com.rpx.bsm.entities;

import com.rpx.bsm.records.ScheduleRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    @Column(nullable = false)
    private LocalDateTime startDate;
    @Column(nullable = false)
    private LocalDateTime endDate;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "schedule_procedure",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "procedure_id"))
    private Set<Procedure> procedures;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professional_id", nullable = false)
    private Professional professional;
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<ServiceItems> serviceItems = new ArrayList<>();
    @OneToOne(cascade = CascadeType.PERSIST, mappedBy = "schedule")
    private PaymentSchedule paymentSchedule;

    public Schedule(Customer customer, LocalDateTime startDate, LocalDateTime endDate, Set<Procedure> procedures, Professional professional) {
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.procedures = procedures;
        this.professional = professional;
    }

    public Schedule(ScheduleRecord r) {
        this.customer = r.client();
        this.startDate = r.startDate();
        this.endDate = r.endDate();
        this.procedures = r.procedures();
        this.professional = r.professional();
        if(r.payment() == null){
            setPaymentSchedule(new PaymentSchedule(this));
        }
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
                ", customer=" + customer +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", procedures=" + procedures +
                ", professional=" + professional +
                ", serviceItems=" + serviceItems +
                ", paymentSchedule=" + paymentSchedule +
                '}';
    }
}
