/**
 * Created by Lev on 23.04.2017.
 */
"use strict"

function importPackage(pack) {
    for (var x in pack) {
        global[x] = pack[x];
    }
}

var exceptions = (function () {
    function pointer(ind, len) {
        var res = "";
        for (var i = 0; i < ind; i++) {
            res += " ";
        }
        res += "^";
        for (var i = 0; i < len - 1; i++) {
            res += "~";
        }
        return res;
    }

    function MyException(message) {
        this.message = message;
    }
    MyException.prototype = Object.create(Error.prototype);
    MyException.prototype.name = "MyException";

    function MakeException(name) {
        this.name = name;
    }
    MakeException.prototype = MyException.prototype;

    function OddClosingParenthesisException(expr, ind) {
        MyException.call(this, "Odd closing parenthesis at position: " + ind + "\n" + expr + "\n" + pointer(ind, 1));
    }
    OddClosingParenthesisException.prototype = new MakeException("OddClosingParenthesisException");

    function MissingClosingParenthesisException(expr, ind) {
        MyException.call(this, "Missing closing parenthesis for opening one at position: " + ind + "\n" + expr + "\n" + pointer(ind, 1));
    }
    MissingClosingParenthesisException.prototype = new MakeException("MissingClosingParenthesisException");

    function MissingOperandException(expr, op, ind) {
        MyException.call(this, "Missing operand for operation '" + op + "' at position: " + ind + "\n" + expr + "\n" + pointer(ind, op.length));
    }
    MissingOperandException.prototype = new MakeException("MissingOperandException");

    function UnknownIdentifierException(expr, id, ind) {
        MyException.call(this, "Unknown identifier '" + id + "' at position: " + ind + "\n" + expr + "\n" + pointer(ind, id.length));
    }
    UnknownIdentifierException.prototype = new MakeException("UnknownIdentifierException");

    function UnknownSymbolException(expr, sym, ind) {
        MyException.call(this, "Unknown symbol '" + sym + "' at position: " + ind + "\n" + expr + "\n" + pointer(ind, 1));
    }
    UnknownSymbolException.prototype = new MakeException("UnknownSymbolException");

    return {
        "pointer": pointer,
        "MyException": MyException,
        "OddClosingParenthesisException": OddClosingParenthesisException,
        "MissingClosingParenthesisException": MissingClosingParenthesisException,
        "MissingOperandException": MissingOperandException,
        "UnknownIdentifierException": UnknownIdentifierException,
        "UnknownSymbolException": UnknownSymbolException
    }
})();

var expression = (function () {
    importPackage(exceptions);

    function myNew(constructor, args) {
        var tmp = Object.create(constructor.prototype);
        constructor.apply(tmp, args);
        return tmp;
    }

    var VARIABLES = {"x": 0, "y": 1, "z": 2};
    var OP = {
        "+": Add,
        "-": Subtract,
        "*": Multiply,
        "/": Divide,
        "negate": Negate,
        "sqrt": Sqrt,
        "square": Square
    };
    var ARGS_CNT = {
        "+": 2,
        "-": 2,
        "*": 2,
        "/": 2,
        "negate": 1,
        "sqrt": 1,
        "square": 1
    };

    var primitive  = {simplify: function () { return this }};
    function Const(x) {
        this.getValue = function () {
            return x;
        }
    }
    Const.prototype = Object.create(primitive);
    Const.prototype.toString = function () {
        return this.getValue().toString();
    }
    Const.prototype.prefix = Const.prototype.toString;
    Const.prototype.evaluate = function () {
        return this.getValue();
    }
    var ZERO = new Const(0);
    var ONE = new Const(1);
    var TWO = new Const(2);
    Const.prototype.diff = function (v) {
        return ZERO;
    }

    function Variable(s) {
        var ind = VARIABLES[s];
        this.getName = function () {
            return s;
        }
        this.getInd = function () {
            return ind;
        }
    }

    Variable.prototype = Object.create(primitive);
    Variable.prototype.toString = function () {
        return this.getName();
    }
    Variable.prototype.prefix = Variable.prototype.toString;
    Variable.prototype.evaluate = function () {
        return arguments[this.getInd()];
    }
    Variable.prototype.diff = function (v) {
        return v === this.getName() ? ONE : ZERO;
    }

    function Operation() {
        var operands = [].slice.call(arguments);
        this.getOperands = function () {
            return operands;
        }
    }
    Operation.prototype.toString = function () {
        return this.getOperands().join(" ") + " " + this.getSymbol();
    }
    Operation.prototype.prefix = function () {
        return "(" +  this.getSymbol() + " " + this.getOperands().map(function (value) { return value.prefix() }).join(" ") + ")";
    }
    Operation.prototype.evaluate = function () {
        var args = arguments;
        var res = this.getOperands().map(function(value) { return value.evaluate.apply(value, args) });
        return this.getAction().apply(null, res);
    }
    Operation.prototype.diff = function (v) {
        var ops = this.getOperands();
        return this._doDiff.apply(this, ops.concat(ops.map(function (value) { return value.diff(v) })));
    }
    Operation.prototype.simplify = function () {
        var ops = this.getOperands().map(function (item) { return item.simplify() });
        var f = true;
        ops.forEach(function (value) {
            if (!(value instanceof Const)) {
                f = false;
            }
        });
        var res = myNew(this.constructor, ops);
        if (f) {
            return new Const(res.evaluate());
        }
        if (this._doSimplify !== undefined) {
            return this._doSimplify.apply(this, ops);
        }
        return res;
    }

    function DefineOperation(maker, action, symbol, howToDiff, howToSimplify) {
        this.constructor = maker;
        this.getAction = function () {
            return action;
        }
        this.getSymbol = function () {
            return symbol;
        }
        this._doDiff = howToDiff;
        this._doSimplify = howToSimplify;
    }
    DefineOperation.prototype = Operation.prototype;

    function Add() {
        Operation.apply(this, arguments);
    }
    Add.prototype = new DefineOperation(
        Add,
        function (a, b) {
            return a + b;
        },
        "+",
        function (a, b, da, db) {
            return new Add(da, db);
        },
        function (a, b) {
            if ((a instanceof Const) && (a.getValue() === 0)) {
                return b;
            }
            if ((b instanceof Const) && (b.getValue() === 0)) {
                return a;
            }
            return new Add(a, b);
        }
    );

    function Subtract() {
        Operation.apply(this, arguments);
    }
    Subtract.prototype = new DefineOperation(
        Subtract,
        function (a, b) {
            return a - b;
        },
        "-",
        function (a, b, da, db) {
            return new Subtract(da, db);
        },
        function (a, b) {
            if ((b instanceof  Const) && (b.getValue() === 0)) {
                return a;
            }
            return new Subtract(a, b);
        }
    );

    function Multiply() {
        Operation.apply(this, arguments);
    }
    Multiply.prototype = new DefineOperation(
        Multiply,
        function (a, b) {
            return a * b;
        },
        "*",
        function (a, b, da, db) {
            return new Add(new Multiply(da, b), new Multiply(a, db));
        },
        function (a, b) {
            if ((a instanceof Const) && (a.getValue() === 0)) {
                return ZERO;
            }
            if ((b instanceof Const) && (b.getValue() === 0)) {
                return ZERO;
            }
            if ((a instanceof Const) && (a.getValue() === 1)) {
                return b;
            }
            if ((b instanceof  Const) && (b.getValue() === 1)) {
                return a;
            }
            return new Multiply(a, b);
        }
    );

    function Divide() {
        Operation.apply(this, arguments);
    }
    Divide.prototype = new DefineOperation(
        Divide,
        function (a, b) {
            return a / b;
        },
        "/",
        function (a, b, da, db) {
            return new Divide(new Subtract(new Multiply(da, b), new Multiply(a, db)), new Multiply(b, b));
        },
        function (a, b) {
            if ((a instanceof Const) && (a.getValue() === 0)) {
                return ZERO;
            }
            if ((b instanceof  Const) && (b.getValue() === 1)) {
                return a;
            }
            return new Divide(a, b);
        }
    );

    function Negate() {
        Operation.apply(this, arguments);
    }
    Negate.prototype = new DefineOperation(
        Negate,
        function (a) {
            return -a;
        },
        "negate",
        function (a, da) {
            return new Negate(da);
        }
    );

    function Square() {
        Operation.apply(this, arguments);
    }
    Square.prototype = new DefineOperation(
        Square,
        function (a) {
            return a * a;
        },
        "square",
        function (a, da) {
            return new Multiply(new Multiply(TWO, a), da);
        }
    )

    function Sqrt() {
        Operation.apply(this, arguments);
    }
    Sqrt.prototype = new DefineOperation(
        Sqrt,
        function (a) {
            return Math.sqrt(Math.abs(a));
        },
        "sqrt",
        function (a, da) {
            var x = new Multiply(a, da);
            var y = new Multiply(TWO, new Sqrt(new Multiply(new Square(a), a)));
            return new Divide(x, y);
        }
    )

    function parse (s) {
        var tokens = s.split(/\s/).filter(function (t) { return t.length > 0 });
        var stack = [];
        for (var i = 0; i < tokens.length; i++) {
            if (tokens[i] in VARIABLES) {
                stack.push(new Variable(tokens[i]));
                continue;
            }
            if (tokens[i] in OP) {
                stack.push(myNew(OP[tokens[i]], stack.splice(-ARGS_CNT[tokens[i]], ARGS_CNT[tokens[i]])));
                continue;
            }
            stack.push(new Const(parseInt(tokens[i])));
        }
        return stack.pop();
    }

    function parsePrefix(expr) {
        var ind = 0;
        function skipWhiteSpace() {
            while (ind < expr.length && /\s/.test(expr.charAt(ind))) {
                ind++;
            }
        }
        function getNumber() {
            var res = "";
            if (expr.charAt(ind) === "-") {
                res += "-";
                ind++;
            }
            while (ind < expr.length && /[0-9]/.test(expr.charAt(ind))) {
                res += expr.charAt(ind++);
            }
            return res;
        }
        function getIdentifier() {
            if (!(/[A-Za-z]/.test(expr.charAt(ind)))) {
                throw new UnknownSymbolException(expr, ind);
            }
            var res = "";
            while (ind < expr.length && /[A-Za-z0-9_]/.test(expr.charAt(ind))) {
                res += expr.charAt(ind++);
            }
            return res;
        }

        function getExpression() {
            skipWhiteSpace();
            if (ind === expr.length) {
                throw new MyException("Bad expression\n" + expr);
            }

            if (expr.charAt(ind) === ')') {
                throw new OddClosingParenthesisException(expr, ind);
            }

            var curInd = ind;

            if (expr.charAt(curInd) === '(') {
                ind++;
                var res = getExpression();
                skipWhiteSpace();
                if (expr.charAt(ind) !== ')') {
                    throw new MissingClosingParenthesisException(expr, curInd);
                }
                ind++;
                return res;
            }

            var curNumber = getNumber();
            if (curNumber !== "" && curNumber !== "-") {
                return new Const(parseInt(curNumber));
            }
            if (curNumber === "-") {
                ind--;
            }

            var curOp = "";
            var curId;

            if (expr.charAt(curInd) in OP) {
                curOp = expr.charAt(curInd);
                ind++;
            } else {
                curId = getIdentifier();
                if (curId in OP) {
                    curOp = curId;
                }
            }

            if (curOp !== "") {
                var operands = [];
                for (var i = 0; i < ARGS_CNT[curOp]; i++) {
                    try {
                        operands.push(getExpression());
                    } catch (e) {
                        if (e.name === "MyException") {
                            throw new MissingOperandException(expr, curOp, curInd);
                        }
                        throw e;
                    }
                }
                return myNew(OP[curOp], operands);
            }

            if (curId in VARIABLES) {
                return new Variable(curId);
            }

            throw new UnknownIdentifierException(expr, curId, curInd);
        }

        var ans = getExpression();
        skipWhiteSpace();
        if (ind !== expr.length) {
            throw new MyException("Odd suffix after correct expression at position: " + ind  + "\n" + expr + "\n" + pointer(ind, expr.length - ind));
        }
        return ans;
    }
    return {
        "Const": Const,
        "Variable": Variable,
        "Add": Add,
        "Subtract": Subtract,
        "Multiply": Multiply,
        "Divide": Divide,
        "Negate": Negate,
        "Square": Square,
        "Sqrt": Sqrt,
        "parse": parse,
        "parsePrefix": parsePrefix
    }
})();

importPackage(expression);
