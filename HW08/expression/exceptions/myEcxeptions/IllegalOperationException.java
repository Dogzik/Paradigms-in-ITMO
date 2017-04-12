package expression.exceptions.myEcxeptions;

/**
 * Created by Лев on 03.04.2017.
 */
public class IllegalOperationException extends Exception{
    public IllegalOperationException(final String message) {
        super("Illegar operation: " + message);
    }
}
