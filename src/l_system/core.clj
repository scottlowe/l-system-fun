(ns l-system.core
  (use l-system.grammars))

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

;; mutable stack implementation

(defprotocol IStack
  (push-stack [this x])
  (pop-stack [this])
  (top-stack [this])
  (empty-stack? [this]))

(defrecord Stack [elements]
  IStack
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
