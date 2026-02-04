package com.ruis.dailymood.service;

import com.ruis.dailymood.domain.entity.FamilyMember;
import com.ruis.dailymood.repository.FamilyMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamilyMemberService {

    private final FamilyMemberRepository repository;

    public FamilyMemberService(FamilyMemberRepository repository) {
        this.repository = repository;
    }
    public List<FamilyMember> findAll() {
        return repository.findAll();
    }

    public FamilyMember create(FamilyMember familyMember) {
        return repository.save(familyMember);
    }

    public void update(FamilyMember familyMember) {
        repository.save(familyMember);
    }
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
    public void delete(FamilyMember familyMember) {
        repository.delete(familyMember);
    }
    public FamilyMember findById(Long id) {
        return
                repository.findById(id).orElse(null);
    }
    public FamilyMember save(FamilyMember familyMember) {
        return repository.save(familyMember);
    }


}
