(ns l-system.display
  "Display generated L-system data using 'Turtle graphics'.
   Uses Processing http://processing.org/"
  (:use [l-system.core]
        [rosado.processing]
        [rosado.processing.applet]))

(defn- forward [turtle length]
   (let [draw-line (fn [turtle length]
                     (let [angle (:angle turtle)
                           deg   (/ (Math/PI) 180)
                           end-x (-> (Math/sin (* angle deg))
                                     (* length)
                                     (+ (:x turtle)))
                           end-y (-> (Math/cos (* angle deg))
                                     (* length)
                                     (+ (:y turtle)))]
                       (line (:x turtle) (:y turtle) end-x end-y)
                       {:x end-x :y end-y :angle angle}))]
  (swap! turtle draw-line length)))

(defn- turn [turtle direction angle]
  (let [change-course (fn [turtle direction angle]
                        (assoc turtle :angle
                        (direction (:angle turtle) angle)))]
  (swap! turtle change-course direction angle)))

(defn plot-system [grammar params]
  (let [turtle (atom {:x (first (:origin params))
                      :y (second (:origin params))
                      :angle (:start-angle params)})
        angle  (:angle grammar)
        length (:line-length params)
        commands (:commands grammar)
        stack  (new-stack (ref ()))
        dispatch (fn [constant]
                   (cond
                     (= :left    (commands constant)) (turn turtle + angle)
                     (= :push    (commands constant)) (push-stack stack @turtle)
                     (= :pop     (commands constant)) (swap! turtle #(do % (pop-stack stack)))
                     (= :right   (commands constant)) (turn turtle - angle)
                     (= :forward (commands constant)) (forward turtle length)))]
    (dorun
      (map dispatch
           (seq (generate grammar (:n-productions params)))))))

(defn- init [grammar params]
  (smooth)
  (no-stroke)
  (fill 226)
  (framerate 10)
  (background-float 255)
  (stroke-float 0 160 0)
  (stroke-weight 1.4)
  (plot-system grammar params))

(defapplet tree-a-app
  :title "Axial Tree A"
  :size [400 670]
  :setup #(do (init axial-tree-a {:origin [200 670]
                                  :n-productions 4
                                  :line-length 8
                                  :start-angle 180})))

(defapplet tree-f-app
  :title "Axial Tree F"
  :size [600 700]
  :setup #(do (init axial-tree-f {:origin [350 700]
                                  :n-productions 5
                                  :line-length 8
                                  :start-angle 180})))

(defapplet sierpinski-app
  :title "Sierpinski Triangle"
  :size [700 600]
  :setup #(do (init sierpinski-triangle {:origin [20 20]
                                         :n-productions 7
                                         :line-length 5
                                         :start-angle 90})))
(defapplet dragon-app
  :title "Dragon Curve"
  :size [630 470]
  :setup #(do (init dragon-curve {:origin [470 280]
                                  :n-productions 12
                                  :line-length 6
                                  :start-angle 90})))
