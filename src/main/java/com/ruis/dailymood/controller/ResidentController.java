package com.ruis.dailymood.controller;

import com.ruis.dailymood.domain.entity.Resident;
import com.ruis.dailymood.service.ResidentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/get-residents")
public class ResidentController {

    private final ResidentService service;

    public ResidentController(ResidentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Resident> list() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Resident create(@RequestBody Resident resident) {
        return service.create(resident);
    }
}
