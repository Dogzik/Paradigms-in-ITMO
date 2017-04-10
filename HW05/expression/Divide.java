package expression;

public class Divide extends AbstractBinaryOperator {
    public Divide(Expression x, Expression y) {
        super(x, y);
    }

    protected int Apply(int x, int y) {
        return x / y;
    }
}
