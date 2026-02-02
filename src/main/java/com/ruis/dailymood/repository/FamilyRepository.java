package com.ruis.dailymood.repository;

import com.ruis.dailymood.domain.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FamilyRepository extends JpaRepository<Family, Long> {
    List<Family> findAll();
}