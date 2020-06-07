package il.ac.afeka.fdp.auth.layout;

import il.ac.afeka.fdp.auth.exception.InvalidCredentials;
import il.ac.afeka.fdp.auth.exception.UserNotFound;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerResponse {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Message{
        private String text;
    }

    @ExceptionHandler(value = InvalidCredentials.class)
    public ResponseEntity<Object> handlingInvalidCredentialsException(InvalidCredentials exception) {
        return new ResponseEntity<>(new Message("Invalid credentials"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = UserNotFound.class)
    public ResponseEntity<Object> handlingUserNotFoundException(UserNotFound exception) {
        return new ResponseEntity<>(new Message("User not found"), HttpStatus.NOT_FOUND);
    }
}

