package com.oocl.packagebooking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotWorkingTimeException.class)
    public ResponseEntity<String> handlerNotWorkingTimeExpection() {
        return new ResponseEntity<>("not in working time", HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(GoodTakeAwayException.class)
    public ResponseEntity handlerGoodTakeAwayException() {
        return new ResponseEntity<>("good has been take away", HttpStatus.BAD_REQUEST);
    }
}
