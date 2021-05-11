package br.com.biot.integracaopagarmeapi.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ValidacaoExceptionHandler {

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<?> handleResouseNotFoundException(ValidacaoException validacaoException) {
        ExceptionDetails validationExceptionDetails = new ExceptionDetails();
        validationExceptionDetails.setStatus(HttpStatus.BAD_REQUEST.value());
        validationExceptionDetails.setMessage(validacaoException.getMessage());
        return new ResponseEntity<>(validationExceptionDetails, HttpStatus.BAD_REQUEST);
    }
}
