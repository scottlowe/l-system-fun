(ns l-system.test.core
  (:use [l-system.core]
        [l-system.grammars]
        [clojure.test]))

(deftest gen-commands-axial-tree-a
         (is (= "F[+F]F[-F]F"
                (gen-commands axial-tree-a 1)))
         (is (= "F[+F]F[-F]F[+F[+F]F[-F]F]F[+F]F[-F]F[-F[+F]F[-F]F]F[+F]F[-F]F"
                (gen-commands axial-tree-a 2))))

(deftest gen-commands-dragon-curve
         (is (= "FX+YF"
                (gen-commands dragon-curve 1)))
         (is (= "FX+YF+FX-YF+FX+YF-FX-YF+FX+YF+FX-YF-FX+YF-FX-YF"
                (gen-commands dragon-curve 4))))

(deftest gen-commands-sierpinski-triangle
         (is (= "A+B+A-B-A-B-A+B+A"
                (gen-commands sierpinski-triangle 2)))
         (is (= "B-A-B+A+B+A+B-A-B-A+B+A-B-A-B-A+B+A-B-A-B+A+B+A+B-A-B"
                (gen-commands sierpinski-triangle 3))))

(deftest gen-coords-axial-tree-test
         (is (= [[200 670 200.0 662.0]
                 [200.0 662.0 196.53072732329966 654.7913838294232]
                 [200.0 662.0 200.0 654.0]
                 [200.0 654.0 203.46927267670034 646.7913838294232]
                 [200.0 654.0 200.0 646.0]]
                (gen-coords axial-tree-a
                            {:origin [200 670]
                             :n-productions 1
                             :line-length 8
                             :start-angle 180}))))

(deftest gen-coords-sierpinski-test
         ; Check fallthrough condition: The sierpinksi rules include no-ops
         (is (= [[20 20 25.0 20.0]
                 [25.0 20.0 27.5 15.669872981077805]
                 [27.5 15.669872981077805 25.0 11.339745962155613]
                 [25.0 11.339745962155613 27.5 7.009618943233419]
                 [27.5 7.009618943233419 32.5 7.009618943233419]
                 [32.5 7.009618943233419 35.0 11.339745962155613]
                 [35.0 11.339745962155613 32.5 15.669872981077805]
                 [32.5 15.669872981077805 35.0 20.0]
                 [35.0 20.0 40.0 20.0]]
                (gen-coords sierpinski-triangle
                            {:origin [20 20]
                             :n-productions 2
                             :line-length 5
                             :start-angle 90}))))
