package expression.exceptions;

import expression.TripleExpression;

public class MyExceptionTest {
    public static void main(String[] args) {
        Integer temp = 124;
        temp = (temp * 2) & 0xFF;
        System.out.println(temp.byteValue());
        try {
            TripleExpression exp = new ExpressionParser().parse("(1 + 2) * ((x + 7)");
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }
}
