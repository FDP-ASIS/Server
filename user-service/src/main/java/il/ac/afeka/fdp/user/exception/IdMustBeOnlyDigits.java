package il.ac.afeka.fdp.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "ID must be digits")
public class IdMustBeOnlyDigits extends RuntimeException {
}
