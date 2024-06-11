package io.softwarestrategies.eventmgr.web;

import io.softwarestrategies.eventmgr.exception.EntityNotFoundException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ApiExceptionResponseHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ApiExceptionResponseHandler.class);

    @Getter
    public static class ErrorResponse {
        private final String errorMessage;
        public ErrorResponse(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }

    @ExceptionHandler(value = { EntityNotFoundException.class })
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(WebRequest request, EntityNotFoundException ex) {
        LOG.error("EntityNotFoundException occurred during call: " + ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(WebRequest request, IllegalArgumentException ex) {
        LOG.error("IllegalArgumentException occurred during call: " + ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<ErrorResponse> handleException(WebRequest request, Exception ex) {
        LOG.error("Exception occurred during call: " + ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
