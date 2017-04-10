package expression;

public class Variable implements Expression {
    private final String name;

    public Variable(String x) {
        name = x;
    }

    public int evaluate(int x) {
        return x;
    }
}
