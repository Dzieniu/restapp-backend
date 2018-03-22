package com.github.dzieniu2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "grades")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,updatable = false)
    private long id;

    @Column(name = "value",nullable = false)
    private int value;

    @ManyToOne
    @JoinColumn(name="student_id", nullable=false)
    @JsonIgnore
    private Student student;

    @ManyToOne
    @JoinColumn(name="subject_id", nullable=false)
    @JsonIgnore
    private Subject subject;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {

        if (checkStudent(student))
            return ;

        Student oldStudent = this.student;
        this.student = student;

        if (oldStudent!=null)
            oldStudent.removeGrade(this);

        if (student!=null)
            student.addGrade(this);
    }

    private boolean checkStudent(Student student) {
        return this.student == null ? student == null : this.student.equals(student);
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {

        if (checkSubject(subject))
            return ;

        Subject oldSubject = this.subject;
        this.subject = subject;

        if (oldSubject!=null)
            oldSubject.removeGrade(this);

        if (subject!=null)
            subject.addGrade(this);
    }

    private boolean checkSubject(Subject subject) {
        return this.subject == null ? subject == null : this.subject.equals(subject);
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", value=" + value +
                ", student=" + student +
                ", subject=" + subject +
                '}';
    }
}
