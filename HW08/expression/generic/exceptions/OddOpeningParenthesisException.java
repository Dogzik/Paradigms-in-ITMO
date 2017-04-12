package expression.generic.exceptions;

/**
 * Created by Лев on 10.04.2017.
 */
public class OddOpeningParenthesisException extends ParsingException {
    public OddOpeningParenthesisException(final String s, final int ind) {
        super("Odd opening parenthesis at position: " + ind + "\n" + s + "\n" + getPlace(ind, 1));
    }
}
