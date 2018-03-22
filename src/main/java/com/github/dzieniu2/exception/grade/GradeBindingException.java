package com.github.dzieniu2.exception.grade;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.ACCEPTED, reason = "Incorrect grade details, grade cannot be created")
public class GradeBindingException extends RuntimeException{
}
