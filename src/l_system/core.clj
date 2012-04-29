(ns l-system.core
 (:use [l-system.grammars]))

(def ^:dynamic *grammar* axial-tree-a)

(def ^:dynamic *environment* {:origin [200 200]
                              :n-productions 4
                              :line-length 7
                              :start-angle 180})

(defn apply-rules [grammar pattern]
  (apply str
         (replace (:rules grammar) pattern)))

(defn gen-commands [grammar n]
  (nth
    (iterate
      (partial apply-rules grammar) (:start grammar))
    n))

(defn- new-position [turtle length]
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

(defn- exec-cmd [state command]
  "For a given command and state, returns a new state consisting of a turtle
  position, line drawing coordinates & a FIFO stack of turtle positions"
  (let [angle (:angle *grammar*)
        length (:line-length *environment*)
        cmd ((:cmd-map *grammar*) command)
        {turtle :turtle stack :stack} state]
    (cond
      (= :left cmd)    (assoc state :turtle (turn turtle + angle))
      (= :push cmd)    (assoc state :stack (cons turtle stack))
      (= :pop cmd)     (assoc state :turtle (first stack) :stack (rest stack))
      (= :right cmd)   (assoc state :turtle (turn turtle - angle))
      (= :forward cmd) (let [new-turtle (new-position turtle length)
                             {x1 :x y1 :y} turtle
                             {x2 :x y2 :y} new-turtle
                             new-lines (conj (:lines state) [x1 y1 x2 y2])]
                         (assoc state :turtle new-turtle :lines new-lines))
      :else state)))

(defn- calc-moves [turtle-start commands]
  "Calculates line drawing coordinates for a collection of l-system commands"
  (let [state {:turtle turtle-start :stack '() :lines []}]
    (:lines
      (reduce exec-cmd state commands))))

(defn gen-coords [grammar environment]
  "Generates drawing coordinates for an l-system grammar and start conditions"
  (binding [*grammar* grammar
            *environment*  environment]
    (let [turtle {:x (first (:origin environment))
                  :y (second (:origin environment))
                  :angle (:start-angle environment)}]
      (calc-moves turtle
                  (gen-commands grammar
                                (:n-productions environment))))))
