package com.github.dzieniu2.controller.entitycontroller;

import com.github.dzieniu2.entity.Student;
import com.github.dzieniu2.entity.User;
import com.github.dzieniu2.entity.dto.StudentDto;
import com.github.dzieniu2.exception.student.StudentBindingException;
import com.github.dzieniu2.exception.student.StudentNotFoundException;
import com.github.dzieniu2.repository.specifications.StudentSpecificationBuilder;
import com.github.dzieniu2.security.MyUserDetails;
import com.github.dzieniu2.service.StudentService;
import com.github.dzieniu2.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
    public Page<Student> getAll(
            @RequestParam(value = "search", required = false) String searchCriteria,
            @PageableDefault(size = 15, direction = Sort.Direction.ASC, sort = "id", value = 30) Pageable pageable
    ){
        return studentService.getAll(searchCriteria, pageable);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER','ROLE_STUDENT')")
    public Student getById(@PathVariable long id){

        checkIfNotFound(id);
        checkIfAllowed(id);

        return studentService.getById(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
    public void insert(@RequestBody @Valid StudentDto studentDto, BindingResult bindingResult){

        checkBindingResult(bindingResult);

        Student student = new Student();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setUser(userService.getById(studentDto.getUser_id()));
        studentService.insert(student);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
    public void update(@RequestBody @Valid StudentDto studentDto, BindingResult bindingResult, @PathVariable long id){

        checkIfNotFound(id);
        checkBindingResult(bindingResult);

        studentService.update(id, studentDto);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
    public void delete(@PathVariable long id){

        checkIfNotFound(id);
        studentService.removeById(id);
    }

    private void checkIfNotFound(long id) {
        if(!studentService.exists(id)) throw new StudentNotFoundException();
    }

    private void checkBindingResult(BindingResult bindingResult){
        if(bindingResult.hasErrors()) throw new StudentBindingException();
    }

    private void checkIfAllowed(long id){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STUDENT"))){
            MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
            User user = myUserDetails.getUser();
            if(user.getId()!=studentService.getById(id).getUser().getId()) throw new AccessDeniedException("Acces forbidden!");
        }
    }
}
