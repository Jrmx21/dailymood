package com.ruis.dailymood.repository;

import com.ruis.dailymood.domain.entity.DailyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyStatusRepository extends JpaRepository<DailyStatus, Long> {

    Optional<DailyStatus> findByResidentIdAndDate(Long residentId, LocalDate date);
    List<DailyStatus> findDailyStatusByResident_Id(Long residentId);
}
