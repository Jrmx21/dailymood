package com.ruis.dailymood.controller;

import com.ruis.dailymood.domain.entity.DailyStatus;
import com.ruis.dailymood.domain.entity.Family;
import com.ruis.dailymood.domain.entity.FamilyMember;
import com.ruis.dailymood.domain.entity.Resident;
import com.ruis.dailymood.microservices.EmailService;
import com.ruis.dailymood.service.DailyStatusService;
import com.ruis.dailymood.service.FamilyMemberService;
import com.ruis.dailymood.service.FamilyService;
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
    EmailService emailService;

    @Autowired
    private DailyStatusService dailyStatusService;
    @Autowired
    private FamilyService familyService;
    @Autowired
    private FamilyMemberService familyMemberService;

    @GetMapping("/daily_status")
    public String showDailyStatus(Model model) {
        List<DailyStatus> allDailyStatuses = dailyStatusService.findAll();
        model.addAttribute("dailyStatuses", allDailyStatuses);
        return "daily_status.html";
    }

    @GetMapping("/daily_status/delete/{id}")
    public String deleteDailyStatus(@PathVariable("id") Long id) {
        dailyStatusService.deleteById(id);
        return "redirect:/daily_status";
    }

    @GetMapping({"/daily_status/form", "/daily_status/form/{id}"})
    public String dailyStatusForm(@PathVariable(required = false) Long id, Model model) {
        DailyStatus dailyStatus;
        if (id != null) {
            dailyStatus = dailyStatusService.findById(id); // busca el estado diario existente
            if (dailyStatus == null) {
                return "redirect:/daily_status"; // si no existe, vuelve a la lista
            }
        } else {
            dailyStatus = new DailyStatus(); // si no hay id, es un nuevo estado diario
        }
        model.addAttribute("dailyStatus", dailyStatus);
        model.addAttribute("residents", residentService.findAll());
        return "daily_status_form.html"; // una sola vista para crear y editar
    }

    @PostMapping("/daily_status/save")
    public String saveOrUpdateDailyStatus(@ModelAttribute("dailyStatus") DailyStatus dailyStatus
    ) {
        if (dailyStatus.getId() != null) {
            // actualizar
            dailyStatusService.update(dailyStatus);
        } else {
            // crear nuevo
            dailyStatusService.create(dailyStatus);
        }
        List<FamilyMember> familiyMembers = familyMemberService.findByResidentIdThroughFamily(dailyStatus.getResident().getId());
        String[] toSendEmails = new String[familiyMembers.size()];
        for (int i = 0; i < familiyMembers.size(); i++) {
            toSendEmails[i] = familiyMembers.get(i).getEmail();
        }
        // emailService.sendEmail("Test Subject", "This is a test OF DAILY STATUS email body.", toSendEmails);
        return "redirect:/daily_status";
    }
}
