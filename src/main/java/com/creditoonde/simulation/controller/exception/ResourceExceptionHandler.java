package com.creditoonde.simulation.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError stdError = new StandardError(System.currentTimeMillis(), status.value(), "Not found.",
                e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(stdError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> requestBodyNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<String> errors = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        StandardError stdError = new StandardError(System.currentTimeMillis(), status.value(), "Request body is not valid.",
                errors.stream().collect(Collectors.joining(", ")), request.getRequestURI());
        return ResponseEntity.status(status).body(stdError);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<StandardError> simulationValuesNotValid(NumberFormatException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        StandardError stdError = new StandardError(System.currentTimeMillis(), status.value(), "Simulation values are not valid.",
                e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(stdError);
    }
}
