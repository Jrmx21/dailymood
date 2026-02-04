package com.ruis.dailymood.service;

import com.ruis.dailymood.domain.entity.Family;
import com.ruis.dailymood.repository.FamilyRepository;
import org.springframework.stereotype.Service;

@Service
public class FamilyService {
    private final FamilyRepository repository;

    public FamilyService(FamilyRepository repository) {
        this.repository = repository;
    }

    public Family save(Family family) {
        return repository.save(family);
    }

}
