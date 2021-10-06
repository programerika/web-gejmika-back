package webgejmikaback.com.programerika.exceptions;

public class PlayerNotFoundException extends Exception{

    private static final long serialVersionUID = 1L;

    public PlayerNotFoundException(String message) {
        super(message);
    }
}
