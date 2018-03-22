package com.github.dzieniu2.service;

import com.github.dzieniu2.entity.Student;
import com.github.dzieniu2.entity.dto.StudentDto;
import com.github.dzieniu2.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public List<Student> getAll(){
        return studentRepository.findAll();
    }

    public Student getById(Long id){
        return studentRepository.findOne(id);
    }

    public void removeById(Long id) {
        this.studentRepository.delete(id);
    }

    public void update(long id, StudentDto update){
        Student student = studentRepository.findOne(id);
        student.setFirstName(update.getFirstName());
        student.setLastName(update.getLastName());
        studentRepository.save(student);
    }

    public void insert(Student student) {
        this.studentRepository.save(student);
    }

    public boolean exists(long id){
        return studentRepository.exists(id);
    }
}
