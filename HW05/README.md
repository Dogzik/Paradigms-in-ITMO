# Домашнее задание 5. Вычисление выражений
1. Разработайте классы Const, Variable, Add, Subtract, Multiply, Divide для вычисления выражений с одной переменной.
2. Классы должны позволять составлять выражения вида  
```java
                new Subtract(
                    new Multiply(
                        new Const(2),
                        new Variable("x")
                    ),
                    new Const(3)
                ).evaluate(5)
            
```
 При вычислении такого выражения вместо каждой переменной подставляется значение, переданное в качестве параметра методу evaluate (на данном этапе имена переменных игнорируются). Таким образом, результатом вычисления приведенного примера должно стать число 7.
3. Для тестирования программы должен быть создан класс Main, который вычисляет значение выражения x2−2x+1, для x, заданного в командной строке.
4. При выполнение задания следует обратить внимание на:
     * Выделение общего интерфейса создаваемых классов.
     * Выделение абстрактного базового класса для бинарных операций.
     

## Домашнее задание 5. Вычисление выражений

Модификации
 * *Базовая*
    * Реализовать интерфейс [Expression](expression/Expression.java)
 * *Простая*
    * Реализовать интерфейс [DoubleExpression](expression/DoubleExpression.java)
 * *Усложненная*
    * Реализовать интерфейсы [DoubleExpression](expression/DoubleExpression.java) и [TripleExpression](expression/TripleExpression.java)