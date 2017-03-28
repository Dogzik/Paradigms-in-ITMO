package ru.itmo.ctddev.Dovzhik.TripleExpression;

public strictfp abstract class AbstractBinaryOperator implements AnyExpression {
    private final AnyExpression firstOperand;
    private final AnyExpression secondOperand;

    public AbstractBinaryOperator(AnyExpression x, AnyExpression y) {
        firstOperand = x;
        secondOperand = y;
    }

    protected abstract double apply(double x, double y);

    protected abstract int apply(int x, int y);

    public int evaluate(int x, int y, int z) {
        return apply(firstOperand.evaluate(x, y, z), secondOperand.evaluate(x, y, z));
    }

    public double evaluate(double x) {
        return apply(firstOperand.evaluate(x), secondOperand.evaluate(x));
    }

    public int evaluate(int x) {
        return apply(firstOperand.evaluate(x), secondOperand.evaluate(x));
    }
}
