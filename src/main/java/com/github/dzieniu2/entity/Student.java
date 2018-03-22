package com.github.dzieniu2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = false)
    private long id;

    @Column(name = "firstName",nullable = false)
    private String firstName;

    @Column(name = "lastName",nullable = false)
    private String lastName;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "student")
    @JsonIgnore
    private Set<Grade> grades = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE,mappedBy = "students",fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Subject> subjects = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {

        if(checkUser(user)) return;

        User oldUser = this.user;
        this.user = user;

        if (oldUser!=null)
            oldUser.setStudent(null);

        if (user!=null)
            user.setStudent(this);
    }

    public boolean checkUser(User user){

        if(this.user==null) return user == null;
        return this.user.equals(user);
    }

    public Set<Grade> getGrades() {
        return new HashSet<>(grades);
    }


    public void addGrade(Grade grade) {

        if (grades.contains(grade))
            return ;

        grades.add(grade);
        grade.setStudent(this);
    }

    public void removeGrade(Grade grade) {

        if (!grades.contains(grade))
            return ;

        grades.remove(grade);
        grade.setStudent(null);
    }

    public Set<Subject> getSubjects() {
        return new HashSet<Subject>(subjects);
    }

    public void addSubject(Subject subject){
        if(subjects.contains(subject)) return;
        subjects.add(subject);
        subject.addStudent(this);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", user=" + user +
                '}';
    }
}
