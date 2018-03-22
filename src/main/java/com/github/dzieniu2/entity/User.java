package com.github.dzieniu2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.dzieniu2.security.Role;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,updatable = false)
    private long id;

    @Column(name = "email",nullable = false,unique = true)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "role",nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private Student student;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private Teacher teacher;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {

        if(checkStudent(student)) return;

        Student oldStudent = this.student;
        this.student = student;

        if(oldStudent!=null){
            oldStudent.setUser(null);
        }
        if(this.student!=null){
            this.student.setUser(this);
        }
    }

    public boolean checkStudent(Student student){

        return this.student == null ? student == null : student.equals(this.student);
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {

        if(checkTeacher(teacher)) return;

        Teacher oldTeacher = this.teacher;
        this.teacher = teacher;

        if(oldTeacher!=null){
            oldTeacher.setUser(null);
        }
        if(this.teacher!=null){
            this.teacher.setUser(this);
        }
    }

    public boolean checkTeacher(Teacher teacher){
        return this.teacher == null ? teacher == null : teacher.equals(this.teacher);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
