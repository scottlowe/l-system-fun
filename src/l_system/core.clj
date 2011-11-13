(ns l-system.core)

(defprotocol StackOps
  (push-stack [this x])
  (pop-stack [this])
  (top-stack [this])
  (empty-stack? [this]))

(defrecord Stack [elements]
  StackOps
  (push-stack [_ x]
    (dosync (alter elements conj x)))
  (pop-stack [_]
    (let [fst (first @elements)]
      (dosync (alter elements rest)) fst))
  (top-stack [_]
    (first @elements))
  (empty-stack? [_]
    (= () @elements)))

(defn new-stack [elements] (new Stack elements))

(defn apply-rules
  "Applies production rules to a sequence of characters for a given grammer"
  [grammar pattern]
  (apply str
         (replace (:rules grammar) pattern)))

(defn generate
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
                \- :right
                \[ :push
                \] :pop
                }
   :angle       25.7})

(def tree-f
  {:constants #{\F \+ \- \[ \]}
   :start      [\X]
   :rules      {\X "F-[[X]+X]+F[+FX]-X"
                \F "FF"}
   :commands   {\F :forward
                \+ :left
                \- :right
                \[ :push
                \] :pop}
   :angle       22.5})
