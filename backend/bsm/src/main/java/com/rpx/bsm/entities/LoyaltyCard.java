package com.rpx.bsm.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "loyalty_card")
public class LoyaltyCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long points;
    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private Long quantityUsed;

    public LoyaltyCard(Long points, Customer customer, Long quantityUsed) {
        this.points = points;
        this.customer = customer;
        this.quantityUsed = quantityUsed;
    }

    public LoyaltyCard(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "LoyaltyCard{" +
                "id=" + id +
                ", points=" + points +
                ", customer=" + customer +
                ", quantityUsed=" + quantityUsed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoyaltyCard that = (LoyaltyCard) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
