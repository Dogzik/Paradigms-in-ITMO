package expression.parser;

import java.util.Arrays;

/**
 * @author Georgiy Korneev
 */
public class ParserHardTest extends ParserShiftsTest {
    protected ParserHardTest() {
        unary.add(op(" abs ", Math::abs));
        unary.add(op(" square ", a -> a * a));

        tests.addAll(Arrays.asList(
                op("abs -5", (x, y, z) -> 5L),
                op("abs (x - y)", (x, y, z) -> Math.abs(x - y)),
                op("x - abs -y", (x, y, z) -> x - Math.abs(-y)),
                op("abs -x", (x, y, z) -> Math.abs(-x)),
                op("abs(x+y)", (x, y, z) -> Math.abs(x + y)),
                op("square -5", (x, y, z) -> 25L),
                op("square (x - y)", (x, y, z) -> (x - y) * (x - y)),
                op("x - square y", (x, y, z) -> x - y * y),
                op("square -x", (x, y, z) -> x * x),
                op("square(x+y)", (x, y, z) -> (x + y) * (x + y))
        ));
    }

    public static void main(final String[] args) {
        new ParserHardTest().run();
    }
}
