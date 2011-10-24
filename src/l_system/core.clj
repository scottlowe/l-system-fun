(ns l-system.core
  "A Simple example of the iterative application of L-System grammars.
   http://en.wikipedia.org/wiki/L-system")

(defn apply-rules
  "Apply a grammar's production rules to a sequence of characters"
  [grammar pattern]
  (apply str
         (replace (:rules grammar) pattern)))

(defn grow
  "Generate the nth generation for a grammar"
  [grammar n]
  (nth
    (iterate
      (partial apply-rules grammar) (:start grammar))
    n))


;; grammar definitions from here on

(def tree-a
  {:constants #{\F \+ \- \[ \]}
   :start      [\F]
   :rules      {\F "F[+F]F[-F]F"}
   :commands   {\F :forward
                \+ :left
                \- :right}
   :angle       25.7})

(def tree-f
  {:constants #{\F \+ \- \[ \]}
   :start      [\X]
   :rules      {\X "F-[[X]+X]+F[+FX]-X"
                \F "FF"}
   :commands   {\F :forward
                \+ :left
                \- :right}
   :angle       22.5})
