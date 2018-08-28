package com.github.dzieniu2.controller;

import com.github.dzieniu2.entity.User;
import com.github.dzieniu2.entity.dto.UserDto;
import com.github.dzieniu2.exception.user.UserAlreadyExistsException;
import com.github.dzieniu2.exception.user.UserBindingException;
import com.github.dzieniu2.exception.user.UserNotFoundException;
import com.github.dzieniu2.security.Role;
import com.github.dzieniu2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<User> getAllUsers(){

        return userService.getAll();
    }

    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable Long id) {

        checkIfNotFound(id);
        return userService.getById(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult){

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

        checkIfNotFound(id);
        checkBindingResult(bindingResult);

        userService.update(id, userDto);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable Long id){

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
