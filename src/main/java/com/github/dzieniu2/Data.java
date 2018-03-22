package com.github.dzieniu2;

import com.github.dzieniu2.entity.*;
import com.github.dzieniu2.security.Role;
import com.github.dzieniu2.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Data implements CommandLineRunner{

    @Autowired
    private UserService userService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void run(String... strings) throws Exception {

        User admin = new User();
        admin.setEmail("admin@gmail.com");
        admin.setPassword(encoder.encode("admin"));
        admin.setRole(Role.ROLE_ADMIN);
        this.userService.insert(admin);

        User user1 = new User();
        user1.setEmail("teacher1@gmail.com");
        user1.setPassword(encoder.encode("teacher1"));
        user1.setRole(Role.ROLE_TEACHER);
        this.userService.insert(user1);

        User user2 = new User();
        user2.setEmail("student1@gmail.com");
        user2.setPassword(encoder.encode("student1"));
        user2.setRole(Role.ROLE_STUDENT);
        this.userService.insert(user2);

        User user3 = new User();
        user3.setEmail("student2@gmail.com");
        user3.setPassword(encoder.encode("student2"));
        user3.setRole(Role.ROLE_STUDENT);
        this.userService.insert(user3);

        Teacher teacher1 = new Teacher();
        teacher1.setFirstName("Józef");
        teacher1.setLastName("Piłsudski");
        teacher1.setUser(userService.getById(2L));
        this.teacherService.insert(teacher1);

        Student student1 = new Student();
        student1.setFirstName("Ignacy");
        student1.setLastName("Paderewski");
        student1.setUser(userService.getById(3L));
        this.studentService.insert(student1);

        Student student2 = new Student();
        student2.setFirstName("Władysław");
        student2.setLastName("Sikorski");
        student2.setUser(userService.getById(4L));
        this.studentService.insert(student2);

        Subject subject1 = new Subject();
        subject1.setName("Podstawy Javy");
        subject1.setTeacher(teacherService.getById(1L));
        subject1.addStudent(studentService.getById(1L));
        subject1.addStudent(studentService.getById(2L));
        this.subjectService.insert(subject1);

        Grade grade1 = new Grade();
        grade1.setValue(5);
        grade1.setStudent(studentService.getById(1L));
        grade1.setSubject(subjectService.getById(1L));
        this.gradeService.insert(grade1);

        Grade grade2 = new Grade();
        grade2.setValue(3);
        grade2.setStudent(studentService.getById(1L));
        grade2.setSubject(subjectService.getById(1L));
        this.gradeService.insert(grade2);

        Grade grade3 = new Grade();
        grade3.setValue(5);
        grade3.setStudent(studentService.getById(2L));
        grade3.setSubject(subjectService.getById(1L));
        this.gradeService.insert(grade3);
    }
}
