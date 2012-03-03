(ns l-system.core
 (:use [l-system.grammars]))

(def ^:dynamic *grammar* axial-tree-a)

(def ^:dynamic *params* {:origin [200 200]
                         :n-productions 4
                         :line-length 7
                         :start-angle 180})

(defn apply-rules
  [grammar pattern]
  (apply str
         (replace (:rules grammar) pattern)))

(defn gen-commands
  [grammar n]
  (nth
    (iterate
      (partial apply-rules grammar) (:start grammar))
    n))

(defn line-end [turtle length]
  (let [deg   (/ (Math/PI) 180)
        angle (:angle turtle)]
    {:angle angle
     :x (-> (Math/sin (* angle deg))
            (* length)
            (+ (:x turtle)))
     :y (-> (Math/cos (* angle deg))
            (* length)
            (+ (:y turtle)))}))

(defn- turn [turtle direction angle]
  (assoc turtle :angle
         (direction (:angle turtle) angle)))

(defn exec-cmd [turtle cmd stack lines]
  (let [angle  (:angle *grammar*)
        length (:line-length *params*)]
    (cond
      (= :left cmd)    [(turn turtle + angle) stack lines]
      (= :push cmd)    [turtle (cons turtle stack) lines]
      (= :pop cmd)     [(first stack) (rest stack) lines]
      (= :right cmd)   [(turn turtle - angle) stack lines]
      (= :forward cmd) (do
                         (let [new-turtle (line-end turtle length)
                               {x1 :x y1 :y} turtle
                               {x2 :x y2 :y} new-turtle
                               new-lines (conj lines [x1 y1 x2 y2])]
                           [new-turtle stack new-lines]))
      :else [turtle stack lines])))

(defn calc-moves [start-pos commands]
  (loop [turtle start-pos
         cmds commands
         stack '()
         line-coords []]
    (if (empty? cmds)
      line-coords
      (let [[new-turtle
             new-stack
             new-lines] (exec-cmd turtle
                                  ((:cmd-map *grammar*) (first cmds))
                                  stack
                                  line-coords)]
        (recur new-turtle (rest cmds) new-stack new-lines)))))

(defn gen-coords [grammar params]
  (binding [*grammar* grammar
            *params*  params]
    (let [turtle {:x (first (:origin params))
                  :y (second (:origin params))
                  :angle (:start-angle params)}]
      (calc-moves turtle
                  (gen-commands grammar (:n-productions params))))))
