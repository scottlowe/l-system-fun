(ns l-system.display
  "Display generated L-system data using a sequence of [x1 y1 x2 y2] coordinates.
   Uses Processing http://processing.org/"
  (:use [l-system.core]
        [l-system.grammars]
        [quil.core]
        [quil.applet :only [applet]]))

(defn plot-system [coordinates]
  (dorun
    (map #(apply line %) coordinates)))

(defn- setup [coordinates]
  (smooth)
  (no-stroke)
  (fill 226)
  (frame-rate 10)
  (background-float 255)
  (stroke-float 0 160 0)
  (stroke-weight 1.4)
  (plot-system coordinates))

(defn display [app-definition]
  (apply applet (reduce concat app-definition)))

(def tree-a-applet
  {:title "Axial Tree A"
   :size [400 670]
   :setup #(do
             (setup
               (gen-coords axial-tree-a
                           {:origin [200 670]
                            :n-productions 4
                            :line-length 8
                            :start-angle 180})))})

(def tree-c-applet
  {:title "Axial Tree C"
   :size [400 600]
   :setup #(do
             (setup
               (gen-coords axial-tree-c
                           {:origin [132 600]
                            :n-productions 4
                            :line-length 10
                            :start-angle 180})))})

(def tree-d-applet
  {:title "Axial Tree D"
   :size [480 700]
   :setup #(do
             (setup
               (gen-coords axial-tree-d
                           {:origin [240 700]
                            :n-productions 7
                            :line-length 2.7
                            :start-angle 180})))})

(def tree-e-applet
  {:title "Axial Tree E"
   :size [480 700]
   :setup #(do
             (setup
               (gen-coords axial-tree-e
                           {:origin [240 700]
                            :n-productions 7
                            :line-length 2.7
                            :start-angle 180})))})

(def tree-f-applet
  {:title "Axial Tree F"
   :size [600 700]
   :setup #(do
             (setup
               (gen-coords axial-tree-f
                           {:origin [350 700]
                            :n-productions 5
                            :line-length 8
                            :start-angle 180})))})

(def sierpinski-applet
  {:title "Sierpinski Triangle"
   :size [700 600]
   :setup #(do
             (setup
               (gen-coords sierpinski-triangle
                           {:origin [20 20]
                            :n-productions 7
                            :line-length 5
                            :start-angle 90})))})

(def dragon-applet
  {:title "Dragon Curve"
   :size [630 470]
   :setup #(do
             (setup
               (gen-coords dragon-curve
                           {:origin [470 280]
                            :n-productions 12
                            :line-length 6
                            :start-angle 90})))})
