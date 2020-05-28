package il.ac.afeka.fdp.software.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {SoftwareNotFoundException.class})
    protected ResponseEntity<Object> handleCourseNotFound(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, "{\"error\" : \"" + ex.getMessage() + "\" }",
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {BadReqException.class})
    protected ResponseEntity<Object> handleBadRequest(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, "{\"error\" : \"" + ex.getMessage() + "\" }",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {SoftwareAlreadyExistsException.class})
    protected ResponseEntity<Object> handleCourseExists(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, "{\"error\" : \"" + ex.getMessage() + "\" }",
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
