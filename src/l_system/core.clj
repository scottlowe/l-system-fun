(ns l-system.core)

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

(defn line-end [turtle length]
  (let [angle (:angle turtle)
        deg   (/ (Math/PI) 180)
        end-x (-> (Math/sin (* angle deg))
                (* length)
                (+ (:x turtle)))
        end-y (-> (Math/cos (* angle deg))
                (* length)
                (+ (:y turtle)))]
    ;(println (str "[" (:x turtle) " " (:y turtle) " " end-x " " end-y "]"))
    {:x end-x :y end-y :angle angle}))

(defn make-coords [rules grammar depth]
  (when (seq rules)
    (let [command ((:commands grammar) (first rules))]
      (do (println command ", depth: " depth)
      (cond
        (= :push command) (make-coords (rest rules)
                                        grammar
                                       (inc depth))
        (= :pop  command) (make-coords (rest rules)
                                        grammar
                                       (dec depth))
        :else (make-coords (rest rules) grammar depth))))))

;; grammar definitions

(def tree-commands {\F :forward
                    \+ :left
                    \- :right
                    \[ :push
                    \] :pop})

(def axial-tree-a {:start     [\F]
                   :rules     {\F "F[+F]F[-F]F"}
                   :angle     25.7
                   :constants #{\F \+ \- \[ \]}
                   :commands  tree-commands })

(def axial-tree-d {:start     [\X]
                   :rules     {\X "F[+X]F[-X]+X"
                               \F "FF"}
                   :angle     20
                   :constants #{\F \+ \- \[ \]}
                   :commands  tree-commands})

(def axial-tree-f {:start     [\X]
                   :rules     {\X "F-[[X]+X]+F[+FX]-X"
                               \F "FF"}
                   :angle     22.5
                   :constants #{\F \+ \- \[ \]}
                   :commands  tree-commands})

(def dragon-curve {:constants  #{\F \+ \-}
                   :start      [\F \X]
                   :rules      {\X "X+YF"
                                \Y "FX-Y"}
                   :angle       90
                   :commands   {\F :forward
                                \+ :left
                                \- :right}})

(def sierpinski-triangle {:constants  #{\+ \-}
                          :start      [\A]
                          :rules      {\A "B-A-B"
                                       \B "A+B+A"}
                          :angle       60
                          :commands   {\A :forward
                                       \B :forward
                                       \+ :left
                                       \- :right}})


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
