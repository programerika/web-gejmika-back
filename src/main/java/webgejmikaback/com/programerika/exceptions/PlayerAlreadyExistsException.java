package webgejmikaback.com.programerika.exceptions;

public class PlayerAlreadyExistsException extends Exception{

    private static final long serialVersionUID = 1L;

    public PlayerAlreadyExistsException(String message) {
        super(message);
    }
}
