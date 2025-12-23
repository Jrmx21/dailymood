package com.ruis.dailymood.service;

import com.ruis.dailymood.domain.entity.DailyStatus;
import com.ruis.dailymood.repository.DailyStatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyStatusService {

    private final DailyStatusRepository repository;

    public DailyStatusService(DailyStatusRepository repository) {
        this.repository = repository;
    }


    public DailyStatus create(DailyStatus dailyStatus) {
        return repository.save(dailyStatus);
    }

    public List<DailyStatus> findAll() {
        return repository.findAll();
    }
}
