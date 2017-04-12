package expression.exceptions.myEcxeptions;

/**
 * Created by Лев on 10.04.2017.
 */
public class MissingClosingParenthesisException extends ParsingException {
    public MissingClosingParenthesisException(final String s, final int ind){
        super("Missing closing parenthesis for opening one at position: " + ind + "\n" + s + "\n" + getPlace(ind, 1));
    }
}
