package expression;

public abstract class AbstractBinaryOperator implements Expression {
    private final Expression firstOperand;
    private final Expression secondOperand;

    public AbstractBinaryOperator(Expression x, Expression y) {
        firstOperand = x;
        secondOperand = y;
    }

    protected abstract int Apply(int x, int y);

    public int evaluate(int x) {
        return Apply(firstOperand.evaluate(x), secondOperand.evaluate(x));
    }
}
