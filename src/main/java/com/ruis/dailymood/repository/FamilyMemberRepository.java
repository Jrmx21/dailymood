package com.ruis.dailymood.repository;

import com.ruis.dailymood.domain.entity.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {
    List<FamilyMember> findAll();
    //find by resident id of family
    @Query("SELECT fm FROM FamilyMember fm " +
            "JOIN fm.family f " +
            "JOIN f.residents r " +
            "WHERE r.id = :residentId")
    List<FamilyMember> findByResidentIdThroughFamily(Long residentId);
}