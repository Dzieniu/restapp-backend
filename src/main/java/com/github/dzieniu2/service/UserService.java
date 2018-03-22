package com.github.dzieniu2.service;

import com.github.dzieniu2.entity.User;
import com.github.dzieniu2.entity.dto.UserDto;
import com.github.dzieniu2.repository.UserRepository;
import com.github.dzieniu2.security.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static Logger USER_SERVICE = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User getById(Long id){
        return this.userRepository.findOne(id);
    }

    public User getByEmail(String email){
        return this.userRepository.findByEmail(email);
    }

    public void removeById(Long id) {
        this.userRepository.delete(id);
    }

    public void update(long id,UserDto update){
        User user = userRepository.findOne(id);
        user.setEmail(update.getEmail());
        user.setPassword(update.getPassword());
        user.setRole(Role.valueOf(update.getRole()));
        userRepository.save(user);
    }

    public void insert(User user) {
        this.userRepository.save(user);
    }

    public boolean exists(long id){
        return userRepository.exists(id);
    }

    public boolean exists(String email){
         return userRepository.findByEmail(email)!=null;
    }
}
