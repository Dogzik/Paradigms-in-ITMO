package ru.ifmo.ctddev.dovzhik.expression;

public class Add extends AbstractBinaryOperator {
    public Add(final TripleExpression x, TripleExpression y) {
        super(x, y);
    }

    protected int apply(final int x, final int y) {
        return x + y;
    }
}
