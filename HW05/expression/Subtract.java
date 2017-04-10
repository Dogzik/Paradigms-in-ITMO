package expression;

public class Subtract extends AbstractBinaryOperator {
    public Subtract(Expression x, Expression y) {
        super(x, y);
    }

    protected int Apply(int x, int y) {
        return x - y;
    }
}
