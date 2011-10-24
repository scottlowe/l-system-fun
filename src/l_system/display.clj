(ns l-system.display
  "Display generated L-system data using 'Turtle graphics'.
   Uses Processing http://processing.org/"
  (:use [l-system.core]
        [rosado.processing]
        [rosado.processing.applet]))

(defonce line-length 7)
(def pen (atom {:x 0 :y 0 :angle 0}))

(defn draw-line [pen length]
  (let [angle (:angle pen)
        deg   (/ (Math/PI) 180)
        end-x (-> (Math/sin (* angle deg))
                  (* length)
                  (+ (:x pen)))
        end-y (-> (Math/cos (* angle deg))
                  (* length)
                  (+ (:y pen)))]
    (line (:x pen) (:y pen) end-x end-y)
    {:x end-x :y end-y :angle angle}))

(defn- forward []
  (swap! pen (draw-line @pen line-length)))

(defn- turn [operator turn-angle]
  (swap! pen (fn [pen operator turn-angle]
               (assoc :angle
                      (operator (:angle pen) turn-angle)))))

(defn interpret
  "Interprets L-system model constant as a 'Turtle' graphics command"
  [grammar constant]
  (let [command ((:commands grammar) constant)
        turn-angle (:angle grammar)]
    (cond (= :forward command) (forward)
          (= :left    command) (turn + turn-angle)
          (= :right   command) (turn - turn-angle))))

(defn plot-system [grammar n]
  (dorun
    (map #(interpret grammar %) (grow grammar n))))

(defn- setup [position grammar n]
  (smooth)
  (no-stroke)
  (fill 226)
  (framerate 10)
  (background-float 255)
  (stroke-float 230 0 0)
  (reset! pen (merge position {:angle (:angle grammar)}))
  (stroke-weight 1.5)
  (plot-system grammar n))

;(defapplet koch-curve-app
;  :title "L-system Koch Curve"
;  :setup #(do (setup {:x 12 :y 350} koch-curve 4))
;  :size [600 480])
;
;(defapplet sierpinski-app
;  :title "L-system Sierpinski Triangle"
;  :setup #(do (setup {:x 80 :y 234} sierpinski-triangle 6))
;  :size [600 480])
;
;(defapplet dragon-curve-app
;  :title "L-system Dragon Curve"
;  :setup #(do (setup {:x 350 :y 350} dragon-curve 11))
;  :size [600 480])
