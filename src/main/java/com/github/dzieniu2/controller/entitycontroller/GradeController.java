package com.github.dzieniu2.controller.entitycontroller;

import com.github.dzieniu2.entity.Grade;
import com.github.dzieniu2.entity.User;
import com.github.dzieniu2.entity.dto.GradeDto;
import com.github.dzieniu2.exception.grade.GradeBindingException;
import com.github.dzieniu2.exception.grade.GradeNotFoundException;
import com.github.dzieniu2.security.MyUserDetails;
import com.github.dzieniu2.service.GradeService;
import com.github.dzieniu2.service.StudentService;
import com.github.dzieniu2.service.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("grade")
@CrossOrigin(origins = "http://localhost:4200")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private SubjectService subjectService;

    private static final Logger GRADE_CONTROLLER = LoggerFactory.getLogger(GradeController.class);

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
    public List<Grade> getAll(){
        return gradeService.getAll();
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER','ROLE_STUDENT')")
    public Grade getById(@PathVariable long id) throws AccessDeniedException{

        checkIfNotFound(id);
        checkIfAllowed(id);

        return gradeService.getById(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
    public void insert(@RequestBody @Valid GradeDto gradeDto, BindingResult bindingResult){

        checkBindingResult(bindingResult);

        Grade grade = new Grade();
        grade.setValue(gradeDto.getValue());
        grade.setStudent(studentService.getById(gradeDto.getStudent_id()));
        grade.setSubject(subjectService.getById(gradeDto.getSubject_id()));
        gradeService.insert(grade);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
    public void update(@RequestBody @Valid GradeDto gradeDto, BindingResult bindingResult, @PathVariable long id){

        checkIfNotFound(id);
        checkBindingResult(bindingResult);

        gradeService.update(id, gradeDto);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
    public void delete(@PathVariable long id){

        checkIfNotFound(id);
        gradeService.removeById(id);
    }

    private void checkIfNotFound(long id) {
        if(!gradeService.exists(id)) throw new GradeNotFoundException();
    }

    private void checkBindingResult(BindingResult bindingResult){
        if(bindingResult.hasErrors()) throw new GradeBindingException();
    }

    private void checkIfAllowed(long id){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STUDENT"))){
            MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
            User user = myUserDetails.getUser();
            if(user.getId()!=gradeService.getById(id).getStudent().getUser().getId()) throw new AccessDeniedException("Acces forbidden!");
        }
    }
}
