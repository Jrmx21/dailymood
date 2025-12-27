package com.ruis.dailymood.controller;

import com.ruis.dailymood.domain.entity.DailyStatus;
import com.ruis.dailymood.domain.entity.Resident;
import com.ruis.dailymood.service.DailyStatusService;
import com.ruis.dailymood.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DailyStatusController {

    @Autowired
    private ResidentService residentService;


    @Autowired
    private DailyStatusService dailyStatusService;

    @GetMapping("/daily-statuses")
    public String showDailyStatus(Model model) {
        List<DailyStatus> allDailyStatuses = dailyStatusService.findAll();
        model.addAttribute("dailyStatuses", allDailyStatuses);
        return "daily-status.html";
    }

    @GetMapping("/daily-statuses/delete/{id}")
    public String deleteDailyStatus(@PathVariable("id") Long id) {
        dailyStatusService.deleteById(id);
        return "redirect:/daily-statuses";
    }

    @GetMapping({"/daily-statuses/form", "/daily-statuses/form/{id}"})
    public String dailyStatusForm(@PathVariable(required = false) Long id, Model model) {
        DailyStatus dailyStatus;
        if (id != null) {
            dailyStatus = dailyStatusService.findById(id); // busca el estado diario existente
            if (dailyStatus == null) {
                return "redirect:/daily-statuses"; // si no existe, vuelve a la lista
            }
        } else {
            dailyStatus = new DailyStatus(); // si no hay id, es un nuevo estado diario
        }
        model.addAttribute("dailyStatus", dailyStatus);
        model.addAttribute("residents", residentService.findAll());
        return "daily-status-form.html"; // una sola vista para crear y editar
    }

    @PostMapping("/daily-statuses/save")
    public String saveOrUpdateDailyStatus(@ModelAttribute("dailyStatus") DailyStatus dailyStatus
    ) {
        if (dailyStatus.getId() != null) {
            // actualizar
            dailyStatusService.update(dailyStatus);
        } else {
            // crear nuevo
            dailyStatusService.create(dailyStatus);
        }
        return "redirect:/daily-statuses";
    }
}
