/**
 * Created by Lev on 23.04.2017.
 */
"use strict"

function importPackage(pack) {
    for (var x in pack) {
        global[x] = pack[x];
    }
}

var expression = (function () {
    function myNew(constructor, args) {
        var tmp = Object.create(constructor.prototype);
        constructor.apply(tmp, args);
        return tmp;
    }

    var VARIABLES = {"x": 0, "y": 1, "z": 2};

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
    Const.prototype.evaluate = function () {
        return this.getValue();
    }
    var ZERO = new Const(0);
    var ONE = new Const(1);
    var TWO = new Const(2);
    Const.prototype.diff = function (v) {
        return ZERO;
    }
    function isZero(a) {
        return ((a instanceof Const) && (a.getValue() === 0));
    }
    function isOne(a) {
        return ((a instanceof Const) && (a.getValue() === 1));
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
    Operation.prototype.evaluate = function () {
        var args = arguments;
        var res = this.getOperands().map(function(value) { return value.evaluate.apply(value, args) });
        return this._action.apply(null, res);
    }
    Operation.prototype.diff = function (v) {
        var ops = this.getOperands();
        return this._doDiff.apply(this, ops.concat(ops.map(function (value) { return value.diff(v) })));
    }
    Operation.prototype.simplify = function () {
        var ops = this.getOperands().map(function (item) { return item.simplify() });
        var f = true;
        ops.forEach(function (value) {
            if (!(value instanceof Const)) { f = false; }
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
        this._action = action;
        this.getSymbol = function () {
            return symbol;
        }
        this._doDiff = howToDiff;
        this._doSimplify = howToSimplify;
    }
    DefineOperation.prototype = Operation.prototype;

    function operationFactory(action, symbol, howToDiff, howToSimplify) {
        var result = function () {
            var args = arguments;
            Operation.apply(this, args);
        }
        result.prototype = new DefineOperation(result, action, symbol, howToDiff, howToSimplify);
        return result;
    }

    var Add = operationFactory(
        function (a, b) {
            return a + b;
        },
        "+",
        function (a, b, da, db) {
            return new Add(da, db);
        },
        function (a, b) {
            if (isZero(a)) {
                return b;
            }
            if (isZero(b)) {
                return a;
            }
            return new Add(a, b);
        }
    );

    var Subtract = operationFactory(
        function (a, b) {
            return a - b;
        },
        "-",
        function (a, b, da, db) {
            return new Subtract(da, db);
        },
        function (a, b) {
            if (isZero(b)) {
                return a;
            }
            return new Subtract(a, b);
        }
    );

    var Multiply = operationFactory(
        function (a, b) {
            return a * b;
        },
        "*",
        function (a, b, da, db) {
            return new Add(new Multiply(da, b), new Multiply(a, db));
        },
        function (a, b) {
            if (isZero(a)) {
                return ZERO;
            }
            if (isZero(b)) {
                return ZERO;
            }
            if (isOne(a)) {
                return b;
            }
            if (isOne(b)) {
                return a;
            }
            return new Multiply(a, b);
        }
    );

    var Divide = operationFactory(
        function (a, b) {
            return a / b;
        },
        "/",
        function (a, b, da, db) {
            return new Divide(new Subtract(new Multiply(da, b), new Multiply(a, db)), new Multiply(b, b));
        },
        function (a, b) {
            if (isZero(a)) {
                return ZERO;
            }
            if (isOne(b)) {
                return a;
            }
            return new Divide(a, b);
        }
    );

    var Negate = operationFactory(
        function (a) {
            return -a;
        },
        "negate",
        function (a, da) {
            return new Negate(da);
        }
    );

    var Square = operationFactory(
        function (a) {
            return a * a;
        },
        "square",
        function (a, da) {
            return new Multiply(new Multiply(TWO, a), da);
        }
    );

    var Sqrt = operationFactory(
        function (a) {
            return Math.sqrt(Math.abs(a));
        },
        "sqrt",
        function (a, da) {
            var x = new Multiply(a, da);
            var y = new Multiply(TWO, new Sqrt(new Multiply(new Square(a), a)));
            return new Divide(x, y);
        }
    );

    var OP = {
        "+": Add,
        "-": Subtract,
        "*": Multiply,
        "/": Divide,
        "negate": Negate,
        "sqrt": Sqrt,
        "square": Square,
    };
    var ARGS_CNT = {
        "+": 2,
        "-": 2,
        "*": 2,
        "/": 2,
        "negate": 1,
        "sqrt": 1,
        "square": 1,
    };

    function parse (s) {
        var stack = [];
        var tokens = s.split(/\s/).filter(function (t) { return t.length > 0 });
        for (var i = 0; i < tokens.length; i++) {
            if (tokens[i] in VARIABLES) {
                stack.push(new Variable(tokens[i]));
            } else if (tokens[i] in OP) {
                stack.push(myNew(OP[tokens[i]], stack.splice(-ARGS_CNT[tokens[i]], ARGS_CNT[tokens[i]])));
            } else {
                stack.push(new Const(parseInt(tokens[i])));
            }
        }
        return stack.pop();
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
    }
})();

importPackage(expression);
