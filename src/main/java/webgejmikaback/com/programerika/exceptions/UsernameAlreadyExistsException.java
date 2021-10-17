package webgejmikaback.com.programerika.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public UsernameAlreadyExistsException(String message) {
        super("Username Already Exists in the Repository or input is not correct");
    }
}
