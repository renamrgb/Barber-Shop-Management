package com.rpx.bsm.entities;

import com.rpx.bsm.records.StockWriteOffRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "stock_write_off")
public class StockWriteOff implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long id;
    @OneToOne
    private Product product;
    @ManyToOne
    private User user;
    private LocalDate record;
    private Integer qty;
    @Column(columnDefinition = "VARCHAR(255)")
    private String reason;

    public StockWriteOff(Product product, User user, LocalDate record, Integer qty, String reason) {
        this.product = product;
        this.user = user;
        this.record = record;
        this.qty = qty;
        this.reason = reason;
    }
    public StockWriteOff(StockWriteOffRecord r) {
        this.product = r.product();
        this.user = r.user();
        this.record = r.record();
        this.qty = r.qty();
        this.reason = r.reason();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockWriteOff that = (StockWriteOff) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "stockWriteOff{" +
                "id=" + id +
                ", product=" + product +
                ", user=" + user +
                ", record=" + record +
                ", qty=" + qty +
                ", reason='" + reason + '\'' +
                '}';
    }
}
