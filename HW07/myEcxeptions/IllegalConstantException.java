package myEcxeptions;

/**
 * Created by Лев on 09.04.2017.
 */
public class IllegalConstantException extends ParsingException {
    public IllegalConstantException(final String reason, final String s, final int ind) {
        super("Constant '" + reason + "' is unsuitable for int at position: " + ind + "\n" + s + "\n" + getPlace(ind, reason.length()));
    }
}
