package expression;

public class Add extends AbstractBinaryOperator {
    public Add(Expression x, Expression y) {
        super(x, y);
    }

    protected int Apply(int x, int y) {
        return x + y;
    }
}
