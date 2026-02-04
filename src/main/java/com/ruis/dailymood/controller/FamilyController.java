package com.ruis.dailymood.controller;

import com.ruis.dailymood.domain.entity.Family;
import com.ruis.dailymood.domain.entity.FamilyMember;
import com.ruis.dailymood.domain.entity.Resident;
import com.ruis.dailymood.service.FamilyMemberService;
import com.ruis.dailymood.service.FamilyService;
import com.ruis.dailymood.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
class FamilyController {

    @Autowired
    private FamilyMemberService familyMemberService;

    @Autowired
    private FamilyService familyService;

    @Autowired
    private ResidentService residentService;

    @GetMapping("/family_member")
    public String showFamilyMembers(Model model) {
        List<FamilyMember> allFamilyMember = familyMemberService.findAll();
        model.addAttribute("familyMembers", allFamilyMember);
        return "family_member";
    }

    @GetMapping("/family_member/delete/{id}")
    public String deleteFamilyMember(@PathVariable("id") Long id) {
        familyMemberService.deleteById(id);
        return "redirect:/family_member";
    }

    @GetMapping("/family_member/edit/{id}")
    public String editFamilyMember(@PathVariable("id") Long id, Model model) {
        FamilyMember familyMember = familyMemberService.findById(id);
        if (familyMember == null) {
            return "redirect:/family_member"; // si no existe, vuelve a la lista
        }
        List<Resident> residents = residentService.findAll();
        model.addAttribute("residents", residents);
        model.addAttribute("familyMember", familyMember);
        return "family_member_form"; // vista para editar
    }

    @GetMapping("/family_member/form")
    public String familyMemberForm(
            @RequestParam(required = false) String keyword,
            Model model
    ) {
        FamilyMember familyMember = new FamilyMember();
        model.addAttribute("familyMember", familyMember);

        List<Resident> residents = (keyword == null || keyword.isBlank())
                ? residentService.findAll()
                : residentService.findByNameContainingIgnoreCase(keyword);

        model.addAttribute("residents", residents);
        model.addAttribute("keyword", keyword);

        return "family_member_form";
    }

    @PostMapping("/family_member/save")
    public String saveFamilyMember(
            @ModelAttribute FamilyMember familyMember,
            @RequestParam(required = false) List<Long> residentIds // Ahora sí, como lista
    ) {
        // 1. Asegurar que existe la familia
        Family family = familyMember.getFamily();
        if (family == null) {
            family = new Family();
        }

        // 2. Si vienen IDs, buscamos los objetos y los metemos en la lista de la familia
        if (residentIds != null && !residentIds.isEmpty()) {
            List<Resident> chosenResidents = residentService.findAllById(residentIds);
            family.setResidents(chosenResidents); // <--- Aquí es donde usas la lista
            familyService.save(family); // Guardamos la familia con sus residentes
            familyMember.setFamily(family);
        }

        familyMemberService.save(familyMember);
        return "redirect:/family_member";
    }

}
