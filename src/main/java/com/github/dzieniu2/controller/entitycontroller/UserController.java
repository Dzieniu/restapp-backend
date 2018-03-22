package com.github.dzieniu2.controller.entitycontroller;

import com.github.dzieniu2.entity.User;
import com.github.dzieniu2.entity.dto.UserDto;
import com.github.dzieniu2.exception.user.UserAlreadyExistsException;
import com.github.dzieniu2.exception.user.UserBindingException;
import com.github.dzieniu2.exception.user.UserNotFoundException;
import com.github.dzieniu2.security.Role;
import com.github.dzieniu2.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger USER_CONTROLLER = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public List<User> getAllUsers(){

        USER_CONTROLLER.info("Get request for all users");
        return userService.getAll();
    }

    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable Long id) throws Exception{

        USER_CONTROLLER.info("Get request for user with id: {}",id);
        checkIfNotFound(id);
        return userService.getById(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult){

        USER_CONTROLLER.info("Post user request with data: {}", userDto);
        checkIfAlreadyExists(userDto.getEmail());
        checkBindingResult(bindingResult);

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.valueOf(userDto.getRole().toUpperCase()));

        userService.insert(user);
    }

    @PutMapping(value = "/{id}")
    public void updateUser(@PathVariable long id, @Valid @RequestBody UserDto userDto, BindingResult bindingResult){

        USER_CONTROLLER.info("Put user request with data:{}", userDto);
        checkIfNotFound(id);
        checkBindingResult(bindingResult);

        userService.update(id, userDto);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable Long id){

        USER_CONTROLLER.info("Delete user request with id:{}",id);
        checkIfNotFound(id);

        userService.removeById(id);
    }

    private void checkIfAlreadyExists(String email) {
        if(userService.exists(email)) throw new UserAlreadyExistsException();
    }

    private void checkIfNotFound(long id) {
        if(!userService.exists(id)) throw new UserNotFoundException();
    }

    private void checkBindingResult(BindingResult bindingResult){
        if(bindingResult.hasErrors()) throw new UserBindingException();
    }
}
