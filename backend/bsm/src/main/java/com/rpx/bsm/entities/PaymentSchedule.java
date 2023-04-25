package com.rpx.bsm.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "payment_schedule")
@Getter
@Setter
@NoArgsConstructor
public class PaymentSchedule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long Id;
    private Double amount;
    private Double grossvalue;
    private Double discount;
    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;
    @OneToMany(mappedBy = "paymentSchedule")
    private List<Schedule> schedules;

    public PaymentSchedule(Double amount, Double grossvalue, Double discount, PaymentMethod paymentMethod) {
        this.amount = amount;
        this.grossvalue = grossvalue;
        this.discount = discount;
        this.paymentMethod = paymentMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentSchedule that = (PaymentSchedule) o;
        return Objects.equals(Id, that.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }

    @Override
    public String toString() {
        return "PaymentSchedule{" +
                "Id=" + Id +
                ", amount=" + amount +
                ", grossvalue=" + grossvalue +
                ", discount=" + discount +
                ", paymentMethod=" + paymentMethod +
                '}';
    }
}
