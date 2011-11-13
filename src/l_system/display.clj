(ns l-system.display
  "Display generated L-system data using 'Turtle graphics'.
   Uses Processing http://processing.org/"
  (:use [l-system.core]
        [rosado.processing]
        [rosado.processing.applet]))

(defn- draw-line [turtle length]
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

(defn- forward [turtle length]
  (swap! turtle draw-line length))

(defn- turn [turtle direction angle]
  (letfn [(change-course [turtle direction angle]
            (assoc turtle :angle
              (direction (:angle turtle) angle)))]
    (swap! turtle change-course direction angle)))

(defn plot-system [grammar params]
  (let [turtle (atom {:x (first (:origin params))
                      :y (second (:origin params))
                      :angle 180})
        angle  (:angle grammar)
        length (:line-length params)
        stack  (new-stack (ref ()))
        dispatch (fn [constant]
                   (cond (= \F constant) (forward turtle length)
                         (= \+ constant) (turn turtle + angle)
                         (= \- constant) (turn turtle - angle)
                         (= \[ constant) (push-stack stack @turtle)
                         (= \] constant) (swap! turtle #(do % (pop-stack stack)))))]
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
 :title "Tree A"
 :setup #(do (init tree-a {:origin [450 800]
                           :n-productions 4
                           :line-length 8}))
 :size [900 800])

(defapplet tree-f-app
 :title "Tree F"
 :setup #(do (init tree-f {:origin [450 800]
                           :n-productions 5
                           :line-length 9}))
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
