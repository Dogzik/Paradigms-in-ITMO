package myEcxeptions;

/**
 * Created by Лев on 09.04.2017.
 */
public class MissingOperandException extends ParsingException {
    public MissingOperandException(final String s, final int ind) {
        super("Missing operand before position: " + ind + "\n" + s + "\n" + getPlace(ind, 1));
    }
}
