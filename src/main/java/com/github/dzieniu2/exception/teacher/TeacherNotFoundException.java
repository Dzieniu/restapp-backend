package com.github.dzieniu2.exception.teacher;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such teacher")
public class TeacherNotFoundException extends RuntimeException{
}
