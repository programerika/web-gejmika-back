package webgejmikaback.com.programerika.exceptions;

public class ScoreOutOfRangeException extends IllegalArgumentException{

    private static final long serialVersionUID = 1L;

    public ScoreOutOfRangeException(String s) {
        super(s);
    }
}
