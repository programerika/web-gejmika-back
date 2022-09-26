package webgejmikaback.com.programerika.exceptions;

public class UserDoesNotHaveScoreForProvidedGameException extends IndexOutOfBoundsException{

    private static final long serialVersionUID = 1L;
    public UserDoesNotHaveScoreForProvidedGameException(String message) {
        super(message);
    }

}
