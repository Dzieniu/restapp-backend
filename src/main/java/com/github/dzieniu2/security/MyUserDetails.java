package com.github.dzieniu2.security;

import com.github.dzieniu2.entity.User;
import com.github.dzieniu2.security.Role;
import org.springframework.security.core.authority.AuthorityUtils;

public class MyUserDetails extends org.springframework.security.core.userdetails.User{

    User user;

    public MyUserDetails(User user){
        super(user.getEmail(),user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "CurrentUser{" +
                "user=" + user +
                "} " + super.toString();
    }
}
