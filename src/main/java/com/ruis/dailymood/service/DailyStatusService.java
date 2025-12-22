package com.ruis.dailymood.service;

import com.ruis.dailymood.domain.entity.DailyStatus;

import java.util.List;

public interface DailyStatusService {

    DailyStatus create(DailyStatus dailyStatus);

    List<DailyStatus> findAll();
}
