package io.project.InternetRadioChecker.exceptions;

public class BitrateException extends RuntimeException {

    public BitrateException() {
    }

    public BitrateException(String message) {
        super(message);
    }

    public BitrateException(Throwable cause) {
        super(cause);
    }

    public BitrateException(String message, Throwable cause) {
        super(message, cause);
    }
}
