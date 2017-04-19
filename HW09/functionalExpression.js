/**
 * Created by Лев on 17.04.2017.
 */
"use strict"
var MY_CONSTS = {
    "pi": Math.PI,
    "e": Math.E
}
var VARIABLES = ["x", "y", "z", "u", "v", "w"];

var operation = function (action) {
    return function () {
        var operands = arguments;
        return function () {
            var res = [];
            for (var i = 0; i < operands.length; i++) {
                res.push(operands[i].apply(null, arguments));
            }
            return action.apply(null, res);
        }
    }
}

var cnst = function (value) {
    return function () {
        return value;
    };
};

for (var x in MY_CONSTS) {
    this[x] = cnst(MY_CONSTS[x]);
}

var variable = function (name) {
    var index = VARIABLES.indexOf(name);
    return function () {
        return arguments[index];
    }
}

for (var temp = 0; temp < VARIABLES.length; temp++) {
    this[VARIABLES[temp]] = variable(VARIABLES[temp]);
}

var add = operation(function (a, b) {
    return a + b;
});

var subtract = operation(function (a, b) {
    return a - b;
});

var multiply = operation(function (a, b) {
    return a * b;
});

var divide = operation(function (a, b) {
    return a / b;
});

var negate = operation(function (a) {
    return -a;
});

var min3 = operation(function () {
    return Math.min.apply(null, arguments);
});

var max5 = operation(function () {
    return Math.max.apply(null, arguments)
});

var parse = function (s) {
    var tokens = s.split(" ").filter(function (t) {
        return t.length > 0;
    });
    var stack = [];
    var OP = {
        "+": add,
        "-": subtract,
        "*": multiply,
        "/": divide,
        "negate": negate,
        "max5": max5,
        "min3": min3,
    };
    var ARGS_CNT = {
        "+": 2,
        "-": 2,
        "*": 2,
        "/": 2,
        "negate": 1,
        "min3": 3,
        "max5": 5
    };
    for (var i = 0; i < tokens.length; i++) {
        if (tokens[i] in MY_CONSTS) {
            stack.push(cnst(MY_CONSTS[tokens[i]]));
            continue;
        }
        if (VARIABLES.indexOf(tokens[i]) !== -1) {
            stack.push(variable(tokens[i]));
            continue;
        }
        if (tokens[i] in OP) {
            var args = [];
            for (var j = 0; j < ARGS_CNT[tokens[i]]; j++) {
                args.push(stack.pop());
            }
            args.reverse();
            stack.push(OP[tokens[i]].apply(null, args));
            continue;
        }
        stack.push(cnst(parseInt(tokens[i])));
    }
    return stack.pop();
}
