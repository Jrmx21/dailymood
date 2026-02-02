package com.ruis.dailymood.service;

import com.ruis.dailymood.domain.entity.FamilyMember;
import com.ruis.dailymood.repository.FamilyMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamilyMemberService {

    private final FamilyMemberRepository repository;

    public FamilyMemberService(FamilyMemberRepository repository) {
        this.repository = repository;
    }
    public List<FamilyMember> findAll() {
        return repository.findAll();
    }



}
