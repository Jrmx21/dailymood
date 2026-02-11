package com.ruis.dailymood.service;

import com.ruis.dailymood.ai.MoodTrendService;
import com.ruis.dailymood.domain.entity.DailyStatus;
import com.ruis.dailymood.domain.entity.FamilyMember;
import com.ruis.dailymood.microservices.EmailService;
import com.ruis.dailymood.repository.DailyStatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyStatusService {

    private final DailyStatusRepository repository;

    public DailyStatusService(DailyStatusRepository repository, MoodTrendService moodTrendService) {
        this.repository = repository;
        this.moodTrendService = moodTrendService;
    }
    private final MoodTrendService moodTrendService;

    public DailyStatus create(DailyStatus dailyStatus) {
        DailyStatus saved = repository.save(dailyStatus);
        moodTrendService.analyzeAndAlert(saved.getResident().getId());
        return repository.save(dailyStatus);
    }

    public List<DailyStatus> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public DailyStatus findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void update(DailyStatus dailyStatus) {
        repository.save(dailyStatus);
    }

    public String [] getEmailToSend(FamilyMemberService familyMemberService, DailyStatus dailyStatus) {
        List<FamilyMember> familiyMembers = familyMemberService.findByResidentIdThroughFamily(dailyStatus.getResident().getId());
        String[] toSendEmails = new String[familiyMembers.size()];
        for (int i = 0; i < familiyMembers.size(); i++) {
            toSendEmails[i] = familiyMembers.get(i).getEmail();
        }
        return toSendEmails;
    }

    public void sendEmail(EmailService emailService, String[] toSendEmails) {

    }
}
