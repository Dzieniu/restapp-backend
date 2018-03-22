package com.github.dzieniu2.service;

import com.github.dzieniu2.entity.Grade;
import com.github.dzieniu2.entity.dto.GradeDto;
import com.github.dzieniu2.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {

    private GradeRepository gradeRepository;

    @Autowired
    public GradeService(GradeRepository gradeRepository){
        this.gradeRepository = gradeRepository;
    }

    public List<Grade> getAll(){
        return gradeRepository.findAll();
    }

    public Grade getById(Long id){
        return gradeRepository.findOne(id);
    }

    public void removeById(Long id) {
        this.gradeRepository.delete(id);
    }

    public void update(long id, GradeDto update){
        Grade grade = gradeRepository.findOne(id);
        grade.setValue(update.getValue());
        gradeRepository.save(grade);
    }

    public void insert(Grade grade) {
        this.gradeRepository.save(grade);
    }

    public boolean exists(long id){
        return gradeRepository.exists(id);
    }
}
