package com.ruis.dailymood.ai;

import com.ruis.dailymood.domain.entity.DailyStatus;
import com.ruis.dailymood.domain.entity.FamilyMember;
import com.ruis.dailymood.microservices.EmailService;
import com.ruis.dailymood.repository.DailyStatusRepository;
import com.ruis.dailymood.service.FamilyMemberService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MoodTrendService {
    private final DailyStatusRepository dailyStatusRepository;
    private final EmailService emailService;
    private final FamilyMemberService familyMemberService;
    private final OpenRouterService openRouterService; // tu servicio de IA

    public MoodTrendService(DailyStatusRepository dailyStatusRepository,
                            EmailService emailService,
                            FamilyMemberService familyMemberService,
                            OpenRouterService openRouterService) {
        this.dailyStatusRepository = dailyStatusRepository;
        this.emailService = emailService;
        this.familyMemberService = familyMemberService;
        this.openRouterService = openRouterService;
    }

    /**
     * Analiza el historial de un residente y envía alertas si hay racha mala o regular
     */
    public void analyzeAndAlert(Long residentId) {
        List<DailyStatus> history = dailyStatusRepository.findDailyStatusByResident_Id(residentId);

        if (history.isEmpty()) return;

        // Convertir el historial a texto para la IA
        String historyText = historyToText(history);

        // Llamar a OpenRouter para detectar racha
        String analysis = openRouterService.analyzeMoodHistory(historyText);
        // Si la IA detecta racha mala o regular, enviar correo
        if (analysis.toLowerCase().contains("bad") || analysis.toLowerCase().contains("regular")) {
            sendEmailAlert(residentId, analysis);
        }
    }

    private String historyToText(List<DailyStatus> history) {
        return history.stream()
                .map(ds -> ds.getDate() + ": " + ds.getStatusType() + " (" + ds.getObservations() + ")")
                .collect(Collectors.joining("\n"));
    }

    private void sendEmailAlert(Long residentId, String analysis) {
        List<FamilyMember> familyMembers = familyMemberService.findByResidentIdThroughFamily(residentId);

        // Filtrar solo los que quieren recibir notificaciones
        List<String> emails = familyMembers.stream()
                .filter(FamilyMember::isReceiveStatusNotifications)
                .map(FamilyMember::getEmail)
                .collect(Collectors.toList());

        if (!emails.isEmpty()) {
            String subject = "Alerta de racha de estado mental del residente ID " + residentId;
            emailService.sendEmail(subject, analysis, emails.toArray(new String[0]));
        }
    }
}