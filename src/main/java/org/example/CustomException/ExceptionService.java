package org.example.CustomException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ExceptionService {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle() {
        return "redirect:/404";
    }
}
