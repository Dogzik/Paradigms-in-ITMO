package exceptions;

/**
 * Created by Лев on 12.04.2017.
 */
public class IncorrectConstException extends ParsingException {
    public IncorrectConstException() {
        super("Bad const");
    }
}
