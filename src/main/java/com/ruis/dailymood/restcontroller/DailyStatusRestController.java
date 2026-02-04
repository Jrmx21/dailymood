package com.ruis.dailymood.restcontroller;

import com.ruis.dailymood.domain.entity.DailyStatus;
import com.ruis.dailymood.service.DailyStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/daily_status")
public class DailyStatusRestController {

    private final DailyStatusService service;

    public DailyStatusRestController(DailyStatusService service) {
        this.service = service;
    }

    @GetMapping
    public List<DailyStatus> list() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DailyStatus create(@RequestBody DailyStatus dailyStatus) {
        return service.create(dailyStatus);
    }
}
