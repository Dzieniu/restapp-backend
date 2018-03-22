package com.github.dzieniu2.controller.entitycontroller;

import com.github.dzieniu2.entity.Subject;
import com.github.dzieniu2.entity.dto.SubjectDto;
import com.github.dzieniu2.exception.subject.SubjectBindingException;
import com.github.dzieniu2.exception.subject.SubjectNotFoundException;
import com.github.dzieniu2.service.SubjectService;
import com.github.dzieniu2.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("subject")
@CrossOrigin(origins = "http://localhost:4200")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private TeacherService teacherService;

    private static final Logger SUBJECT_CONTROLLER = LoggerFactory.getLogger(SubjectController.class);

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER','ROLE_STUDENT')")
    public List<Subject> getAll(){
        return subjectService.getAll();
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER','ROLE_STUDENT')")
    public Subject getById(@PathVariable long id){

        checkIfNotFound(id);
        return subjectService.getById(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
    public void insert(@RequestBody @Valid SubjectDto subjectDto, BindingResult bindingResult){

        checkBindingResult(bindingResult);

        Subject subject = new Subject();
        subject.setName(subjectDto.getName());
        subject.setTeacher(teacherService.getById(subjectDto.getTeacher_id()));
        subjectService.insert(subject);

    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
    public void update(@RequestBody @Valid SubjectDto subjectDto, BindingResult bindingResult, @PathVariable long id){

        checkIfNotFound(id);
        checkBindingResult(bindingResult);

        subjectService.update(id, subjectDto);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
    public void delete(@PathVariable long id){

        checkIfNotFound(id);
        subjectService.removeById(id);
    }

    private void checkIfNotFound(long id) {
        if(!subjectService.exists(id)) throw new SubjectNotFoundException();
    }

    private void checkBindingResult(BindingResult bindingResult){
        if(bindingResult.hasErrors()) throw new SubjectBindingException();
    }
}
