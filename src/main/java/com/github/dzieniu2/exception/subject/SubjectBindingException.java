package com.github.dzieniu2.exception.subject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.ACCEPTED, reason = "Incorrect subject details, teacher cannot be created")
public class SubjectBindingException extends RuntimeException{
}
