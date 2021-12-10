package webgejmikaback.com.programerika.exceptions;

public class UsernameBadValidationException extends Exception{

    private static final long serialVersionUID = 1L;

    public UsernameBadValidationException(String message) {
        super(message);
    }
}
