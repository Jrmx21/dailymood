package com.ruis.dailymood.controller;

import com.ruis.dailymood.domain.entity.FamilyMember;
import com.ruis.dailymood.service.FamilyMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
class FamilyController {

    @Autowired
    private FamilyMemberService familyMemberService;

    @GetMapping("/family-member")
    public String showFamilyMembers(Model model) {
        List<FamilyMember> allFamilyMember = familyMemberService.findAll();
        model.addAttribute("familyMembers", allFamilyMember);
        return "family_member";
    }
}
