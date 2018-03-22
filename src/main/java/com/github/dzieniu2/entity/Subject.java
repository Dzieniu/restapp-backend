package com.github.dzieniu2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,updatable = false)
    private long id;

    @Column(name = "name",unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name="teacher_id")
    @JsonIgnore
    private Teacher teacher;

    @OneToMany(fetch = FetchType.EAGER,mappedBy="subject")
    @JsonIgnore
    private Set<Grade> grades = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinTable(
            name = "subjects_students",
            joinColumns = @JoinColumn(name = "subject_id"), inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    @JsonIgnore
    private Set<Student> students = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {

        if (checkTeacher(teacher))
            return ;

        Teacher oldTeacher = this.teacher;
        this.teacher = teacher;

        if (oldTeacher!=null)
            oldTeacher.removeSubject(this);

        if (teacher!=null)
            teacher.addSubject(this);
    }

    private boolean checkTeacher(Teacher teacher) {
        return this.teacher==null? teacher == null : this.teacher.equals(teacher);
    }

    public Set<Grade> getGrades() {
        return new HashSet<>(grades);
    }

    public void addGrade(Grade grade) {

        if (grades.contains(grade))
            return ;

        grades.add(grade);
        grade.setSubject(this);
    }

    public void removeGrade(Grade grade) {

        if (!grades.contains(grade))
            return ;

        grades.remove(grade);
        grade.setSubject(null);
    }

    public Set<Student> getStudents() {
        return new HashSet<Student>(students);
    }

    public void addStudent(Student student){
        if(students.contains(student)) return;
        students.add(student);
        student.addSubject(this);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teacher=" + teacher +
                '}';
    }
}
