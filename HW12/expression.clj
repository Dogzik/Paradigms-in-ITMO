(defn operator [action]
    (fn [& operands]
          (fn [args] (apply action (map (fn [x] (x args)) operands)))
      )
  )

(defn constant [value] (fn [vars] value))
(defn variable [name] (fn [vars] (vars name)))

(def add (operator +))
(def subtract (operator -))
(def multiply (operator *))
(def divide (operator (fn [x y] (/ (double x) (double y)))))
(def negate (operator -))

(def sin (operator (fn [x] (Math/sin x))))
(def cos (operator (fn [x] (Math/cos x))))
(def sinh (operator (fn [x] (Math/sinh x))))
(def cosh (operator (fn [x] (Math/cosh x))))
(def sqrt (operator (fn [x] (Math/sqrt (Math/abs x)))))
(def square (operator (fn [x] (* x x))))

(defn constant [value] (fn [vars] value))
(defn variable [name] (fn [vars] (get vars name)))

(def op {'+ add, '- subtract, '* multiply, '/ divide,
         'negate negate, 'sin sin, 'cos cos, 'sinh sinh,
         'cosh cosh, 'sqrt sqrt, 'square square
         })
(defn parseFunction [expression]
     (cond
        (string? expression) (parseFunction (read-string expression))
        (seq? expression) (apply (op (first expression)) (map parseFunction (rest expression)))
        (number? expression) (constant expression)
        (symbol? expression) (variable (str expression))
        )
  )