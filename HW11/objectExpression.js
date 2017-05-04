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
        var res = "\n";
        for (var i = 0; i < ind; i++) {
            res += " ";
        }
        res += "^";
        for (var i = 0; i < len - 1; i++) {
            res += "~";
        }
        return res;
    }

    function MyException(message) { this.message = message }
    MyException.prototype = Error.prototype;
    MyException.prototype.name = "MyException";

    function MakeException(name) { this.name = name }
    MakeException.prototype = MyException.prototype;

    function exceptionFactory(name, howToCreateMessage) {
        var result = function () {
            var args = arguments;
            MyException.call(this, howToCreateMessage.apply(null, args));
        }
        result.prototype = new MakeException(name);
        return result;
    }

    var OddClosingParenthesisException = exceptionFactory(
        "OddClosingParenthesisException",
        function (expr, ind) {
            return ("Odd closing parenthesis at position: " + ind + "\n" + expr + pointer(ind, 1));
        }
    );

    var OddOpeningParenthesisException = exceptionFactory(
        "OddOpeningParenthesisException",
        function (expr, ind) {
            return ("Odd opening parenthesis at position: " + ind + "\n" + expr + pointer(ind, 1));
        }
    );

    var MissingClosingParenthesisException = exceptionFactory(
        "MissingClosingParenthesisException",
        function (expr, ind) {
            return ("Expected closing parenthesis before position: " + ind + "\n" + expr +  pointer(ind, 1));
        }
    );

    var MissingOperandException = exceptionFactory(
        "MissingOperandException",
        function (expr, op, ind) {
            return ("Too few operands for operation '" + op + "' at position: " + ind + "\n" + expr + pointer(ind, op.length));
        }
    );

    var OddOperandException = exceptionFactory(
        "OddOperandException",
        function (expr, op, ind) {
            return ("Too many operands for operation '" + op + "' at position: " + ind + "\n" + expr + pointer(ind, op.length));
        }
    );

    var UnknownIdentifierException = exceptionFactory(
        "UnknownIdentifierException",
        function (expr, id, ind) {
            return ("Unknown identifier '" + id + "' at position: " + ind + "\n" + expr + pointer(ind, id.length));
        }
    );

    var UnknownSymbolException = exceptionFactory(
        "UnknownSymbolException",
        function (expr, ind) {
            return ("Unknown symbol '" + expr.charAt(ind) + "' at position: " + ind + "\n" + expr + pointer(ind, 1));
        }
    );

    var MissingOperationException = exceptionFactory(
        "MissingOperationException",
        function (expr, ind, where) {
            var reason = "";
            if (!where) {
                reason = "Expected operation after opening parenthesis at position ";
            } else {
                reason = "Expected operation before closing parenthesis at position ";
            }
            return (reason + ind + "\n" + expr + pointer(ind, 1));
        }
    );

    var OddSuffixException = exceptionFactory(
        "OddSuffixException",
        function (expr, ind) {
            return ("Unexpected suffix after correct expression at position: " + ind  + "\n" + expr + pointer(ind, expr.length - ind));
        }
    );

    var MissingOpeningParenthesisException = exceptionFactory(
        "MissingOpeningParenthesisException",
        function (expr, op, ind) {
            return ("Opening parenthesis expected for operation '" + op + "' at position: " + ind + "\n" + expr + pointer(ind, op.length));
        }
    )

    return {
        "MyException": MyException,
        "OddClosingParenthesisException": OddClosingParenthesisException,
        "OddOpeningParenthesisException": OddOpeningParenthesisException,
        "MissingClosingParenthesisException": MissingClosingParenthesisException,
        "MissingOperandException": MissingOperandException,
        "UnknownIdentifierException": UnknownIdentifierException,
        "UnknownSymbolException": UnknownSymbolException,
        "MissingOperationException": MissingOperationException,
        "OddSuffixException": OddSuffixException,
        "OddOperandException": OddOperandException,
        "MissingOpeningParenthesisException": MissingOpeningParenthesisException
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

    var primitive  = {simplify: function () { return this }};
    function Const(x) {
        this.getValue = function () { return x }
    }
    Const.prototype = Object.create(primitive);
    Const.prototype.toString = function () { return this.getValue().toString() }
    Const.prototype.prefix = Const.prototype.toString;
    Const.prototype.postfix = Const.prototype.toString;
    Const.prototype.evaluate = function () { return this.getValue() }
    var ZERO = new Const(0);
    var ONE = new Const(1);
    Const.prototype.diff = function (v) { return ZERO }

    function Variable(s) {
        var ind = VARIABLES[s];
        this.getName = function () { return s }
        this.getInd = function () { return ind }
    }

    Variable.prototype = Object.create(primitive);
    Variable.prototype.toString = function () { return this.getName() }
    Variable.prototype.prefix = Variable.prototype.toString;
    Variable.prototype.postfix = Variable.prototype.toString;
    Variable.prototype.evaluate = function () { return arguments[this.getInd()] }
    Variable.prototype.diff = function (v) { return v === this.getName() ? ONE : ZERO }

    function Operation() {
        var operands = [].slice.call(arguments);
        this.getOperands = function () { return operands }
    }
    Operation.prototype.toString = function () {
        return this.getOperands().join(" ") + " " + this.getSymbol();
    }
    Operation.prototype.prefix = function () {
        return "(" +  this.getSymbol() + " " + this.getOperands().map(function (value) { return value.prefix() }).join(" ") + ")";
    }
    Operation.prototype.postfix = function () {
        return "(" + this.getOperands().map(function (value) { return value.postfix() }).join(" ") + " " + this.getSymbol() + ")";
    }
    Operation.prototype.evaluate = function () {
        var args = arguments;
        var res = this.getOperands().map(function(value) { return value.evaluate.apply(value, args) });
        return this._action.apply(null, res);
    }
    Operation.prototype.diff = function (v) {
        var ops = this.getOperands();
        return this._doDiff.apply(this, ops.concat(ops.map(function (value) { return value.diff(v) })));
    }

    function DefineOperation(maker, action, symbol, howToDiff) {
        this.constructor = maker;
        this._action = action;
        this.getSymbol = function () {
            return symbol;
        }
        this._doDiff = howToDiff;
    }
    DefineOperation.prototype = Operation.prototype;

    function operationFactory(action, symbol, howToDiff) {
        var result = function () {
            var args = arguments;
            Operation.apply(this, args);
        }
        result.prototype = new DefineOperation(result, action, symbol, howToDiff);
        return result;
    }

    var Add = operationFactory(
        function (a, b) { return a + b },
        "+",
        function (a, b, da, db) { return new Add(da, db) }
    );

    var Subtract = operationFactory(
        function (a, b) { return a - b },
        "-",
        function (a, b, da, db) { return new Subtract(da, db) }
    );

    var Multiply = operationFactory(
        function (a, b) { return a * b },
        "*",
        function (a, b, da, db) { return new Add(new Multiply(da, b), new Multiply(a, db)) }
    );

    var Divide = operationFactory(
        function (a, b) { return a / b },
        "/",
        function (a, b, da, db) { return new Divide(new Subtract(new Multiply(da, b), new Multiply(a, db)), new Multiply(b, b)) }
    );

    var Negate = operationFactory(
        function (a) { return -a },
        "negate",
        function (a, da) { return new Negate(da) }
    );

    var Sin = operationFactory(
        function (a) { return Math.sin(a) },
        "sin",
        function (a, da) { return new Multiply(new Cos(a), da) }
    )

    var Cos = operationFactory(
        function (a) { return Math.cos(a) },
        "cos",
        function (a, da) { return new Multiply(new Negate(new Sin(a)), da) }
    )

    var OP = {
        "+": Add,
        "-": Subtract,
        "*": Multiply,
        "/": Divide,
        "negate": Negate,
        "cos": Cos,
        "sin": Sin
    };
    var ARGS_CNT = {
        "+": 2,
        "-": 2,
        "*": 2,
        "/": 2,
        "negate": 1,
        "cos": 1,
        "sin": 1
    };

    var expr = "";
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
        while (ind < expr.length && /\d/.test(expr.charAt(ind))) {
            res += expr.charAt(ind++);
        }
        return res;
    }
    function checkPrev(prev, index, id) {
        if ((id in OP) && (prev !== "(")) {
            throw new MissingOpeningParenthesisException(expr, id, index);
        }
        if (!(id in OP) && (prev === "(")) {
            throw new MissingOperationException(expr, index, 0);
        }
    }
    function checkEmpty() {
        if (ind === expr.length) {
            throw new MyException("Empty input");
        }
    }
    function getIdentifier() {
        if (!(/[A-Za-z]/.test(expr.charAt(ind)))) {
            throw new UnknownSymbolException(expr, ind);
        }
        var res = "";
        while (ind < expr.length && /\w/.test(expr.charAt(ind))) {
            res += expr.charAt(ind++);
        }
        return res;
    }
    function tryNumber() {
        var curNumber = getNumber();
        if (curNumber !== "" && curNumber !== "-") {
            return new Const(parseInt(curNumber));
        }
        if (curNumber === "-") {
            ind--;
        }
        return undefined;
    }

    function parsePrefix(s) {
        expr = s;
        ind = 0;
        function getExpression(prev) {
            skipWhiteSpace();
            checkEmpty();
            if (expr.charAt(ind) === ')') {
                throw new OddClosingParenthesisException(expr, ind);
            }
            var curInd = ind;
            if (expr.charAt(curInd) === '(') {
                ind++;
                try {
                    var res = getExpression('(');
                } catch (e) {
                    if (e.name === "MyException") {
                        throw new OddOpeningParenthesisException(expr, curInd);
                    } else if (e.name === "OddClosingParenthesisException") {
                        throw new MissingOperationException(expr, ind, 0);
                    } else {
                        throw e;
                    }
                }
                skipWhiteSpace();
                if (expr.charAt(ind) !== ')') {
                    throw new MissingClosingParenthesisException(expr, ind);
                }
                ind++;
                return res;
            }
            var curNumber = tryNumber(prev);
            if (curNumber !== undefined) {
                checkPrev(prev, curInd);
                return curNumber;
            }
            var curOp = undefined;
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
            if (curOp !== undefined) {
                checkPrev(prev, curInd, curOp);
                var operands = [];
                for (var i = 0; i < ARGS_CNT[curOp]; i++) {
                    try {
                        operands.push(getExpression(""));
                    } catch (e) {
                        if (e.name === "MyException" || e.name === "OddClosingParenthesisException") {
                            throw new MissingOperandException(expr, curOp, curInd);
                        }
                        throw e;
                    }
                }
                var tempInd = ind;
                try {
                    getExpression("operand");
                } catch (e) {
                    ind = tempInd;
                    return myNew(OP[curOp], operands);
                }
                throw new OddOperandException(expr, curOp, curInd);
            }
            if (curId in VARIABLES) {
                checkPrev(prev, curInd);
                return new Variable(curId);
            }
            throw new UnknownIdentifierException(expr, curId, curInd);
        }

        var ans = getExpression("");
        skipWhiteSpace();
        if (ind !== expr.length) {
            throw new OddSuffixException(expr, ind);
        }
        return ans;
    }

    function parsePostfix(s) {
        var stack = [];
        expr = s;
        ind = 0;
        var balance = 0;
        var sufInd = 0;
        var assigned = false;
        skipWhiteSpace();
        checkEmpty();
        var prev = "";
        while (true) {
            skipWhiteSpace();
            if (ind === expr.length) {
                break;
            }
            if (expr.charAt(ind) === "(") {
                stack.push("(");
                prev = "(";
                balance++;
                ind++;
                continue;
            }
            if (expr.charAt(ind) === ")") {
                if (stack.pop() !== "(") {
                    throw new OddClosingParenthesisException(expr, ind);
                }
                if (!(prev in OP)) {
                    throw new MissingOperationException(expr, ind, 1);
                }
                ind++;
                balance--;
                prev = "operand";
                continue;
            }
            var curNumber = tryNumber();
            if (curNumber !== undefined) {
                stack.push(curNumber);
                prev = "operand";
                if (sufInd === 0) {
                    sufInd = ind;
                }
                continue;
            }
            var curInd = ind;
            var curOp = undefined;
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
            if (curOp !== undefined) {
                var operands = [];
                var n = ARGS_CNT[curOp];
                for (var i = 0; i < n; i++) {
                    var tmp = stack.pop();
                    if (tmp === "(" || tmp === undefined) {
                        throw new MissingOperandException(expr, curOp, curInd);
                    }
                    operands[n - i - 1] = tmp;
                }
                tmp = stack.pop();
                if (tmp !== "(") {
                    if (tmp !== undefined) {
                        throw new OddOperandException(expr, curOp, curInd);
                    }
                    throw new MissingOpeningParenthesisException(expr, curOp, curInd);
                }
                skipWhiteSpace();
                if (expr.charAt(ind) !== ")") {
                    throw new MissingClosingParenthesisException(expr, ind);
                }
                stack.push(myNew(OP[curOp],operands));
                prev = "operand";
                ind++;
                balance--;
                if (balance === 0 && !assigned) {
                    sufInd = ind;
                    assigned = true;
                }
                continue;
            }
            if (curId in VARIABLES) {
                stack.push(new Variable(curId));
                prev = "operand";
                if (sufInd === 0) {
                    sufInd = ind;
                }
                continue;
            }
            throw new UnknownIdentifierException(expr, curId, curInd);
        }
        if (balance > 0) {
            throw new MissingClosingParenthesisException(expr, ind);
        }
        var res = stack.pop();
        if (stack.length > 0) {
            throw new OddSuffixException(expr, sufInd);
        }
        return res;
    }

    return {
        "Const": Const,
        "Variable": Variable,
        "Add": Add,
        "Subtract": Subtract,
        "Multiply": Multiply,
        "Divide": Divide,
        "Negate": Negate,
        "Sin": Sin,
        "Cos": Cos,
        "parsePrefix": parsePrefix,
        "parsePostfix": parsePostfix
    }
})();

importPackage(expression);
