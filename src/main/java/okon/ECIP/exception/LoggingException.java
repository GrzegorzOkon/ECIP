package okon.ECIP.exception;

public class LoggingException extends RuntimeException {
    public LoggingException(Throwable cause) {
        super(cause);
    }

    public LoggingException(String message) {
        super(message);
    }
}