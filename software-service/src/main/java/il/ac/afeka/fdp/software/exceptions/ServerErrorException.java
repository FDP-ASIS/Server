package il.ac.afeka.fdp.software.exceptions;

import il.ac.afeka.fdp.software.utils.FinalStrings;

public class ServerErrorException extends RuntimeException {
    public ServerErrorException() {
        this(FinalStrings.SERVER_ERROR);
    }

    public ServerErrorException(String message) {
        super(message);
    }

    public ServerErrorException(Throwable cause) {
        super(cause);
    }

}