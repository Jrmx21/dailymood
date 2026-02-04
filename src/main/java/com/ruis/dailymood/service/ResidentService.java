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
    public Resident delete(Resident resident) {
        repository.delete(resident);
        return resident;
    }

    public Resident create(Resident resident) {
        return repository.save(resident);
    }


    public List<Resident> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Resident findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void update(Resident resident) {
        repository.save(resident);
    }

    public List<Resident> findAllById(List<Long> residentIds) {
        return repository.findAllById(residentIds);
    }

    public List<Resident> findByNameContainingIgnoreCase(String keyword) {
        return repository.findByNameContainingIgnoreCase(keyword);
    }
}
