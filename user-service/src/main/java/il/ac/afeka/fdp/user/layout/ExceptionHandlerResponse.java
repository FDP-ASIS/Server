package il.ac.afeka.fdp.user.layout;

import il.ac.afeka.fdp.user.exception.UserNotFound;
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

    @ExceptionHandler(value = UserNotFound.class)
    public ResponseEntity<Object> handlingUserNotFoundException(UserNotFound exception) {
        return new ResponseEntity<>(new Message("User not found"), HttpStatus.NOT_FOUND);
    }
}

