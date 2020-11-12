package org.example.web.errorController;

import org.apache.log4j.Logger;
import org.example.CustomException.EmptyResourceException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ExceptionService {

    Logger log = Logger.getLogger(this.getClass());

    @ExceptionHandler(NoHandlerFoundException.class)
    public String noHandlerHandler(NoHandlerFoundException e) {
        log.error(e.getMessage(), e);
        return "redirect:/404";
    }

    @ExceptionHandler(EmptyResourceException.class)
    public String emptyResource(EmptyResourceException e){
        log.error(e.getMessage(), e);
        return e.getMessage();
    }

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return e.getMessage();
    }

    @ExceptionHandler(BindException.class)
    public String onBindException(BindException e) {
        log.error(e.getMessage(), e);
        return e.getMessage();
    }
}
