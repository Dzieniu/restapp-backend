package com.github.dzieniu2.service;

import com.github.dzieniu2.entity.Subject;
import com.github.dzieniu2.entity.dto.SubjectDto;
import com.github.dzieniu2.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<Subject> getAll(){
        return subjectRepository.findAll();
    }

    public Subject getById(Long id){
        return subjectRepository.findOne(id);
    }

    public void removeById(Long id) {
        this.subjectRepository.delete(id);
    }

    public void update(long id, SubjectDto update){
        Subject subject = subjectRepository.findOne(id);
        subject.setName(update.getName());
        subjectRepository.save(subject);
    }

    public void insert(Subject subject) {
        this.subjectRepository.save(subject);
    }

    public boolean exists(long id){
        return subjectRepository.exists(id);
    }
}

