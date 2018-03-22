package com.github.dzieniu2.controller.entitycontroller;

import com.github.dzieniu2.entity.Teacher;
import com.github.dzieniu2.entity.User;
import com.github.dzieniu2.entity.dto.TeacherDto;
import com.github.dzieniu2.exception.teacher.TeacherBindingException;
import com.github.dzieniu2.exception.teacher.TeacherNotFoundException;
import com.github.dzieniu2.security.MyUserDetails;
import com.github.dzieniu2.service.TeacherService;
import com.github.dzieniu2.service.UserService;
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
@RequestMapping("teacher")
@CrossOrigin(origins = "http://localhost:4200")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private UserService userService;

    private static final Logger USER_CONTROLLER = LoggerFactory.getLogger(TeacherController.class);

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Teacher> getAll(){
        return teacherService.getAll();
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
    public Teacher getById(@PathVariable long id){

        checkIfNotFound(id);
        checkIfAllowed(id);

        return teacherService.getById(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void insert(@RequestBody @Valid TeacherDto teacherDto, BindingResult bindingResult){

        checkBindingResult(bindingResult);

        Teacher teacher = new Teacher();
        teacher.setFirstName(teacherDto.getFirstName());
        teacher.setLastName(teacherDto.getLastName());
        teacher.setUser(userService.getById(teacherDto.getUser_id()));
        teacherService.insert(teacher);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void update(@RequestBody @Valid TeacherDto teacherDto, BindingResult bindingResult, @PathVariable long id){

        checkIfNotFound(id);
        checkBindingResult(bindingResult);

        teacherService.update(id, teacherDto);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable long id){

        checkIfNotFound(id);
        teacherService.removeById(id);
    }

    private void checkIfNotFound(long id) {
        if(!teacherService.exists(id)) throw new TeacherNotFoundException();
    }

    private void checkBindingResult(BindingResult bindingResult){
        if(bindingResult.hasErrors()) throw new TeacherBindingException();
    }

    private void checkIfAllowed(long id){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TEACHER"))){
            MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
            User user = myUserDetails.getUser();
            if(user.getId()!=teacherService.getById(id).getUser().getId()) throw new AccessDeniedException("Acces forbidden!");
        }
    }
}
