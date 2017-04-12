package expression.generic.exceptions;

/**
 * Created by Лев on 03.04.2017.
 */
public class IllegalOperationException extends EvaluatingException {
    public IllegalOperationException(final String message) {
        super("Illegar operation: " + message);
    }
}
