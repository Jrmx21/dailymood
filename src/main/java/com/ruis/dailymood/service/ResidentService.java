package com.ruis.dailymood.service;

import com.ruis.dailymood.domain.entity.Resident;
import com.ruis.dailymood.repository.ResidentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResidentService {

    private final ResidentRepository repository;

    public ResidentService(ResidentRepository repository) {
        this.repository = repository;
    }


    public Resident create(Resident resident) {
        return repository.save(resident);
    }


    public List<Resident> findAll() {
        return repository.findAll();
    }

}
