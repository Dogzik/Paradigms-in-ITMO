package exceptions;

/**
 * Created by Лев on 01.04.2017.
 */
public class OverflowException extends EvaluatingException {
    public OverflowException() {
        super("overflow");
    }
}
