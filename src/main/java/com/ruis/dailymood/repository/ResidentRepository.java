package com.ruis.dailymood.repository;

import com.ruis.dailymood.domain.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResidentRepository extends JpaRepository<Resident, Long> {
    List<Resident> findAll();

}
