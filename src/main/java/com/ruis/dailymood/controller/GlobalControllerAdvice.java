package com.ruis.dailymood.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("navItems")
    public List<Map<String, String>> navItems() {
        List<Map<String, String>> navItems = new ArrayList<>();
        navItems.add(Map.of("url", "/daily_status", "label", "Daily Status"));
        navItems.add(Map.of("url", "/residents", "label", "Residents"));
        navItems.add(Map.of("url", "/family_member", "label", "Families"));
        navItems.add(Map.of("url", "/settings", "label", "Settings"));
        return navItems;
    }

    @ModelAttribute("request")
    public HttpServletRequest request(HttpServletRequest request) {
        return request;
    }

    @ExceptionHandler(Exception.class)
    public String handleException(HttpServletRequest request, Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());

        // Convertir stack trace a String
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        String stackTrace = sw.toString();

        model.addAttribute("errorTrace", stackTrace);

        model.addAttribute("navItems", navItems()); // para navbar
        return "error"; // nombre de la vista error.html
    }
}
