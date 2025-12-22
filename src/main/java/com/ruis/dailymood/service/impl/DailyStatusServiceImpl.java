package com.ruis.dailymood.service.impl;

import com.ruis.dailymood.domain.entity.DailyStatus;
import com.ruis.dailymood.repository.DailyStatusRepository;
import com.ruis.dailymood.service.DailyStatusService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyStatusServiceImpl implements DailyStatusService {

    private final DailyStatusRepository repository;

    public DailyStatusServiceImpl(DailyStatusRepository repository) {
        this.repository = repository;
    }

    @Override
    public DailyStatus create(DailyStatus dailyStatus) {
        return repository.save(dailyStatus);
    }

    @Override
    public List<DailyStatus> findAll() {
        return repository.findAll();
    }
}
