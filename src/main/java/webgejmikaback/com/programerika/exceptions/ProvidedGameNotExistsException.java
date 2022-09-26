package webgejmikaback.com.programerika.exceptions;

public class ProvidedGameNotExistsException extends IllegalArgumentException{

    private static final long serialVersionUID = 1L;
    public ProvidedGameNotExistsException(String s) {
        super(s);
    }

}
