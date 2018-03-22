package com.github.dzieniu2.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.ACCEPTED, reason = "Incorrect user details, user cannot be created")
public class UserBindingException extends RuntimeException{
}
