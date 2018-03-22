package com.github.dzieniu2.exception.student;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.ACCEPTED, reason = "Incorrect student details, student cannot be created")
public class StudentBindingException extends RuntimeException{
}
