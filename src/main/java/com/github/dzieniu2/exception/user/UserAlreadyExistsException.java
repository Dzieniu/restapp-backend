package com.github.dzieniu2.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.ACCEPTED, reason = "User with this email already exists")
public class UserAlreadyExistsException extends RuntimeException{
}
