package exceptions;

/**
 * Created by Лев on 09.04.2017.
 */
public class MissingOperationException extends ParsingException {
    public MissingOperationException(final String s, final int ind) {
        super("Missing operation before position: " + ind + "\n" + s + "\n" + getPlace(ind, 1));
    }
}
