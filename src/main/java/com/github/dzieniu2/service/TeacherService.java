package com.github.dzieniu2.service;

import com.github.dzieniu2.entity.Teacher;
import com.github.dzieniu2.entity.dto.TeacherDto;
import com.github.dzieniu2.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public List<Teacher> getAll(){
        return teacherRepository.findAll();
    }

    public Teacher getById(Long id){
        return teacherRepository.findOne(id);
    }

    public void removeById(Long id) {
        this.teacherRepository.delete(id);
    }

    public void update(long id, TeacherDto update){
        Teacher teacher = teacherRepository.findOne(id);
        teacher.setFirstName(update.getFirstName());
        teacher.setLastName(update.getLastName());
        teacherRepository.save(teacher);
    }

    public void insert(Teacher teacher) {
        this.teacherRepository.save(teacher);
    }

    public boolean exists(long id){
        return teacherRepository.exists(id);
    }
}
