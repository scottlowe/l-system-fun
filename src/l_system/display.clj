(ns l-system.display
  "Display generated L-system data using 'Turtle graphics'.
   Uses Processing http://processing.org/"
  (:use [l-system.core]
        [rosado.processing]
        [rosado.processing.applet]))

(def line-length 8)
(def stack (new-stack (ref ())))

(defn draw-line [turtle length]
  (let [angle (:angle turtle)
        deg   (/ (Math/PI) 180)
        end-x (-> (Math/sin (* angle deg))
                  (* length)
                  (+ (:x turtle)))
        end-y (-> (Math/cos (* angle deg))
                  (* length)
                  (+ (:y turtle)))]
    (line (:x turtle) (:y turtle) end-x end-y)
    {:x end-x :y end-y :angle angle}))

(defn- forward [turtle]
  (swap! turtle draw-line line-length))

(defn new-angle [t o a]
  (let [x (o (:angle t) a)]
    (assoc t :angle x)))

(defn- turn [turtle operator turn-angle]
  (swap! turtle new-angle operator turn-angle))

(defn interpret
  "Interprets L-system model constant as a 'Turtle' graphics command"
  [turtle grammar constant]
  (let [angle (:angle grammar)]
    (cond (= \F constant) (forward turtle)
          (= \+ constant) (turn turtle + angle)
          (= \- constant) (turn turtle - angle)
          (= \[ constant) (push-stack stack @turtle)
          (= \] constant) (swap! turtle #(do % (pop-stack stack))))))

(defn plot-system [grammar params]
  (let [turtle (atom {:x (:start-x params)
                      :y (:start-y params)
                      :angle 180})]
    (dorun
      (map #(interpret turtle grammar %)
           (seq (grow grammar (:iterations params)))))))

(defn- setup [grammar params]
  (smooth)
  (no-stroke)
  (fill 226)
  (framerate 10)
  (background-float 255)
  (stroke-float 230 0 0)
  (stroke-weight 1.5)
  (plot-system grammar params))

(defapplet tree-a-app
 :title "Tree A"
 :setup #(do (setup tree-a {:start-x 450 :start-y 800 :iterations 5}))
 :size [900 800])

(defapplet tree-f-app
 :title "Tree F"
 :setup #(do (setup tree-f {:start-x 450 :start-y 800 :iterations 5}))
 :size [900 800])


;(defapplet sierpinski-app
;  :title "L-system Sierpinski Triangle"
;  :setup #(do (setup {:x 80 :y 234} sierpinski-triangle 6))
;  :size [600 480])
;
;(defapplet dragon-curve-app
;  :title "L-system Dragon Curve"
;  :setup #(do (setup {:x 350 :y 350} dragon-curve 11))
;  :size [600 480])
