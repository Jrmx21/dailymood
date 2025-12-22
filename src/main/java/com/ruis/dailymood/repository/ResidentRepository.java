package com.ruis.dailymood.repository;

import com.ruis.dailymood.domain.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResidentRepository extends JpaRepository<Resident, Long> {
}
