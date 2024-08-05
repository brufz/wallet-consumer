package com.project.wallet.handler;

import com.project.wallet.exception.AmountNotValidException;
import com.project.wallet.exception.ErrorDetails;
import com.project.wallet.exception.RestExceptionHandler;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestExceptionHandlerTest {

    private final RestExceptionHandler restExceptionHandler = new RestExceptionHandler();

    @Test
    void testHandleTransactionEnumNotFoundException() {
        AmountNotValidException ex = new AmountNotValidException("Test Exception");

        ResponseEntity<ErrorDetails> responseEntity = restExceptionHandler.handlePermissionDeniedException(ex);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ErrorDetails errorDetails = responseEntity.getBody();
        assertEquals("400", errorDetails.getCode());
        assertEquals("BAD_REQUEST", errorDetails.getTitle());
        assertEquals("Test Exception", errorDetails.getDetail());
    }

    @Test
    void testHandleConstraintViolationException() {
        ConstraintViolationException ex = new ConstraintViolationException("Test Constraint Exception", null);
        ResponseEntity<ErrorDetails> responseEntity = restExceptionHandler.handleConstraintViolationException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ErrorDetails errorDetails = responseEntity.getBody();
        assertEquals("400", errorDetails.getCode());
        assertEquals("BAD_REQUEST", errorDetails.getTitle());
        assertEquals("Test Constraint Exception", errorDetails.getDetail());
    }

    @Test
    void testErrorDataBuild() {
        Exception ex = new Exception("Test Exception");
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ErrorDetails errorDetails = RestExceptionHandler.errorDataBuild(ex, httpStatus);
        assertEquals("400", errorDetails.getCode());
        assertEquals("BAD_REQUEST", errorDetails.getTitle());
        assertEquals("Test Exception", errorDetails.getDetail());
    }
}

