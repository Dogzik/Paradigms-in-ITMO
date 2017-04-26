package exceptions;

/**
 * Created by Лев on 12.04.2017.
 */
public class UnknownModeException extends Exception{
    public UnknownModeException(final String mode) {
        super("Unknown mode for tabulation: " + mode);
    }
}
