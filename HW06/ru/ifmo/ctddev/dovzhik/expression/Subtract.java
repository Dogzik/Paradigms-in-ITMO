package ru.ifmo.ctddev.dovzhik.expression;

public class Subtract extends AbstractBinaryOperator {
    public Subtract(final TripleExpression x, final TripleExpression y) {
        super(x, y);
    }

    protected int apply(final int x, final int y) {
        return x - y;
    }
}
