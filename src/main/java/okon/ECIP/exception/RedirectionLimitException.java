package okon.ECIP.exception;

public class RedirectionLimitException extends RuntimeException {
    public RedirectionLimitException(Throwable cause) {
        super(cause);
    }

    public RedirectionLimitException(String message) {
        super(message);
    }
}