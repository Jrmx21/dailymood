package com.ruis.dailymood.controller;

import com.ruis.dailymood.domain.entity.Resident;
import com.ruis.dailymood.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ResidentController {

    @Autowired
    private ResidentService residentService;

    @RequestMapping("/residents")
    public String showResident(Model model) {
        List<Resident> allResidents= residentService.findAll();
        model.addAttribute("residents",allResidents );
        return "residents.html";
    }
    @GetMapping("/residents/delete/{id}")
    public String deleteResident(@PathVariable("id") Long id) {
        residentService.deleteById(id);
        return "redirect:/residents";
    }

    @GetMapping({"/residents/form", "/residents/form/{id}"})
    public String residentForm(@PathVariable(required = false) Long id, Model model) {
        Resident resident;
        if (id != null) {
            resident = residentService.findById(id); // busca el residente existente
            if (resident == null) {
                return "redirect:/residents"; // si no existe, vuelve a la lista
            }
        } else {
            resident = new Resident(); // si no hay id, es un nuevo residente
        }
        model.addAttribute("resident", resident);
        return "residents_form.html"; // una sola vista para crear y editar
    }

    @PostMapping("/residents/save")
    public String saveOrUpdateResident(@ModelAttribute("resident") Resident resident) {
        if (resident.getId() != null) {
            // actualizar
            residentService.update(resident);
        } else {
            // crear nuevo
            residentService.create(resident);
        }
        return "redirect:/residents";
    }

}
