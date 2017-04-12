package expression.exceptions.myEcxeptions;

/**
 * Created by Лев on 09.04.2017.
 */
public class IllegalConstantException extends ParsingException {
    public IllegalConstantException(final String reason, final String s, final int ind) {
        super("Integer constant is too large for its type: '" + reason + "' at position " + ind + "\n" + s + "\n" + getPlace(ind, reason.length()));
    }
}
