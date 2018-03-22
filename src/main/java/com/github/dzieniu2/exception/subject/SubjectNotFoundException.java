package com.github.dzieniu2.exception.subject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such subject")
public class SubjectNotFoundException extends RuntimeException{
}
