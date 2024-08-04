package com.project.wallet.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.webjars.NotFoundException;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AmountNotValidException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorDetails> handlePermissionDeniedException(AmountNotValidException ex) {
        return  new ResponseEntity<>(errorDataBuild(ex, HttpStatus.BAD_REQUEST), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorDetails> handleInvalidTokenException(InvalidTokenException ex) {
        return  new ResponseEntity<>(errorDataBuild(ex, HttpStatus.UNAUTHORIZED), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorDetails> handleForbiddenException(ForbiddenException ex) {
        return new ResponseEntity<>(errorDataBuild(ex, HttpStatus.FORBIDDEN), new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EmptyListException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDetails> handleEmptyListException(EmptyListException ex) {
        return  new ResponseEntity<>(errorDataBuild(ex, HttpStatus.NOT_FOUND), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDetails> handleNotFoundException(NotFoundException ex) {
        return  new ResponseEntity<>(errorDataBuild(ex, HttpStatus.NOT_FOUND), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDetails> handleConstraintViolationException(ConstraintViolationException ex) {
        return  new ResponseEntity<>(errorDataBuild(ex, HttpStatus.BAD_REQUEST), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    public static ErrorDetails errorDataBuild(Exception ex, HttpStatus httpStatus) {
        return ErrorDetails.builder()
                .code(String.valueOf(httpStatus.value()))
                .title(httpStatus.name())
                .detail(ex.getMessage())
                .build();
    }


}
