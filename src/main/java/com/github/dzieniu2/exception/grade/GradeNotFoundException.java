package com.github.dzieniu2.exception.grade;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such grade")
public class GradeNotFoundException extends RuntimeException{
}
