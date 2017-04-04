package ru.ifmo.ctddev.dovzhik.expression;

public abstract class AbstractBinaryOperator implements TripleExpression {
    private final TripleExpression firstOperand;
    private final TripleExpression secondOperand;

    protected AbstractBinaryOperator(final TripleExpression x, final TripleExpression y) {
        firstOperand = x;
        secondOperand = y;
    }

    protected abstract int apply(final int x, final int y);

    public int evaluate(final int x, final int y, final int z) {
        return apply(firstOperand.evaluate(x, y, z), secondOperand.evaluate(x, y, z));
    }

}
