package com.github.dzieniu2.exception.teacher;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.ACCEPTED, reason = "Incorrect teacher details, teacher cannot be created")
public class TeacherBindingException extends RuntimeException{
}
