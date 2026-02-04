package com.ruis.dailymood.domain.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ManyToAny;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinTable( name = "family_resident", joinColumns = @JoinColumn(name = "family_id"), inverseJoinColumns = @JoinColumn(name = "resident_id") )
    @ManyToMany
    private List<Resident> residents = new ArrayList<>();

    public Family(List<FamilyMember> familyMembers) {
        this.familyMembers = familyMembers;
    }

    @OneToMany(mappedBy = "family")
    private List<FamilyMember> familyMembers;

    public Family() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Resident> getResidents() {
        return residents;
    }

    public void setResidents(List<Resident> residents) {
        this.residents = residents;
    }

    public List<FamilyMember> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(List<FamilyMember> familyMembers) {
        this.familyMembers = familyMembers;
    }
}
