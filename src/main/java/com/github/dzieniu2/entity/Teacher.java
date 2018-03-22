package com.github.dzieniu2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "firstName",nullable = false)
    private String firstName;

    @Column(name = "lastName",nullable = false)
    private String lastName;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy="teacher")
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
            oldUser.setTeacher(null);

        if (user!=null)
            user.setTeacher(this);
    }

    public boolean checkUser(User user){

        if(this.user==null) return user == null;
        return this.user.equals(user);
    }

    public Set<Subject> getSubjects() {
        return new HashSet<>(subjects);
    }

    public void addSubject(Subject subject) {

        if (subjects.contains(subject))
            return ;

        subjects.add(subject);
        subject.setTeacher(this);
    }

    public void removeSubject(Subject subject) {

        if (!subjects.contains(subject))
            return ;

        subjects.remove(subject);
        subject.setTeacher(null);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", user=" + user +
                '}';
    }
}
