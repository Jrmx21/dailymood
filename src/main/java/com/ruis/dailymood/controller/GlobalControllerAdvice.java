package com.ruis.dailymood.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("navItems")
    public List<Map<String, String>> navItems() {
        List<Map<String, String>> navItems = new ArrayList<>();
        navItems.add(Map.of("url", "/daily-statuses", "label", "Daily Status"));
        navItems.add(Map.of("url", "/residents", "label", "Residents"));
        navItems.add(Map.of("url", "/settings", "label", "Settings"));
        return navItems;
    }

    @ModelAttribute("request")
    public HttpServletRequest request(HttpServletRequest request) {
        return request;
    }

    @ExceptionHandler(Exception.class) // Captura cualquier excepción
    public String handleException(HttpServletRequest request, Exception ex, Model model) {
        // Guardamos la URL de donde vino el error y el mensaje
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorPage", request.getRequestURI());
        // Devolvemos la misma vista que provocó el error
        return getViewFromRequest(request);
    }

    // Método simple para determinar la vista según la URL
    private String getViewFromRequest(HttpServletRequest request){
        String uri = request.getRequestURI();
        if(uri.equals("/")) return "index";
        // Ajusta según tus rutas
        if(uri.startsWith("/items")) return "items";
        // por defecto
        return "index";
    }
}
