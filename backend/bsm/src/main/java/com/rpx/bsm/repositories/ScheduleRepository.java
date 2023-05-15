package com.rpx.bsm.repositories;

import com.rpx.bsm.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM Schedule s "
            + "JOIN FETCH s.professional p "
            +" WHERE p.Id = :professionalId  " +
            "AND (s.startDate  >= :startOfDay AND s.endDate <= :endOfDay)" +
            " ORDER BY s.startDate asc ")
    List<Schedule> findByStartDateBetweenAndProfessional(LocalDateTime startOfDay, LocalDateTime endOfDay, Long professionalId);

    @Query("SELECT s FROM Schedule s WHERE s.professional.Id = :id")
    List<Schedule> findByProfessional(Long id);
}
