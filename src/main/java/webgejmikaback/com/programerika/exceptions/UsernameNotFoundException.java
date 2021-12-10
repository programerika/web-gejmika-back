package webgejmikaback.com.programerika.exceptions;

public class UsernameNotFoundException extends IndexOutOfBoundsException{

    private static final long serialVersionUID = 1L;

    public UsernameNotFoundException(String message) {
        super(message);
    }
}
