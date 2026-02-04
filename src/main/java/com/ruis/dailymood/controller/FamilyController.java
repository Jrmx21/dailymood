package com.ruis.dailymood.controller;

import com.ruis.dailymood.domain.entity.FamilyMember;
import com.ruis.dailymood.service.FamilyMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
class FamilyController {

    @Autowired
    private FamilyMemberService familyMemberService;

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
        model.addAttribute("familyMember", familyMember);
        return "family_member_form"; // vista para editar
    }

    @GetMapping("/family_member/form")
    public String familyMemberForm(Model model) {
        FamilyMember familyMember = new FamilyMember(); // nuevo miembro de la familia
        model.addAttribute("familyMember", familyMember);
        return "family_member_form"; // vista para crear
    }
    @PostMapping("/family_member/save")
    public String saveFamilyMember(@ModelAttribute FamilyMember familyMember) {
        familyMemberService.save(familyMember);
        return "redirect:/family_member";
    }


}
