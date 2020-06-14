package il.ac.afeka.fdp.software.layout;

import il.ac.afeka.fdp.software.exceptions.BadReqException;
import il.ac.afeka.fdp.software.exceptions.NotFoundException;
import il.ac.afeka.fdp.software.exceptions.ServerErrorException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerResponse extends ResponseEntityExceptionHandler {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Message {
        private String error;
    }

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<Object> handleResourceNotFound(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, new Message(ex.getMessage()),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {BadReqException.class})
    protected ResponseEntity<Object> handleBadRequest(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, new Message(ex.getMessage()),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {ServerErrorException.class})
    protected ResponseEntity<Object> handleServerErrorException(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, new Message(ex.getMessage()),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
