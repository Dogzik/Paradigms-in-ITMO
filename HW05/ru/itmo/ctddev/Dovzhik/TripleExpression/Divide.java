package ru.itmo.ctddev.Dovzhik.TripleExpression;

public strictfp class Divide extends AbstractBinaryOperator {
    public Divide(AnyExpression x, AnyExpression y) {
        super(x, y);
    }

    protected double apply(double x, double y) {
        return x / y;
    }

    protected int apply(int x, int y) {
        return x / y;
    }
}
