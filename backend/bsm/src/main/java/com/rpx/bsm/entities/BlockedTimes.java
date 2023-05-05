package com.rpx.bsm.entities;

import com.rpx.bsm.records.BlockedTimesRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "blockedTimes")
@Getter
@Setter
@NoArgsConstructor
public class BlockedTimes implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime startDate;
    @Column(nullable = false)
    private LocalDateTime endDate;
    @Column(nullable = false, columnDefinition = "VARCHAR(60)")
    private String description;

    public BlockedTimes(BlockedTimesRecord r) {
        setDescription(r.description());
        setEndDate(r.endDate());
        setStartDate(r.startDate());
    }

    public BlockedTimes(LocalDateTime startDate, LocalDateTime endDate, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockedTimes that = (BlockedTimes) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "blockedTimes{" + "id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", description='" + description + '\'' + '}';
    }
}
