package com.ruis.dailymood.domain.entity;


import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Resident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surnames;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;
    private String conditions;
    private String room;

    @OneToMany(mappedBy = "resident")
    private List<DailyStatus> dailyStatuses;


    @ManyToMany(mappedBy = "residents")
    private List<Family> families = new ArrayList<>();

    // getters y setters

    public List<Family> getFamilies() {
        return families;
    }

    public void setFamilies(List<Family> families) {
        this.families = families;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id_resident) {
        this.id = id_resident;
    }

    public String getName() {
        return name;
    }

    public List<DailyStatus> getDailyStatuses() {
        return dailyStatuses;
    }

    public void setDailyStatuses(List<DailyStatus> dailyStatuses) {
        this.dailyStatuses = dailyStatuses;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }


    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}