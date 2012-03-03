(ns l-system.test.core
  (:use [l-system.core]
        [l-system.grammars]
        [clojure.test]))

(deftest generate-axial-tree-a
         (is (= "F[+F]F[-F]F"
                (generate axial-tree-a 1)))
         (is (= "F[+F]F[-F]F[+F[+F]F[-F]F]F[+F]F[-F]F[-F[+F]F[-F]F]F[+F]F[-F]F"
                (generate axial-tree-a 2))))

(deftest generate-dragon-curve
         (is (= "FX+YF"
                (generate dragon-curve 1)))
         (is (= "FX+YF+FX-YF+FX+YF-FX-YF+FX+YF+FX-YF-FX+YF-FX-YF"
                (generate dragon-curve 4))))

(deftest generate-sierpinski-triangle
         (is (= "A+B+A-B-A-B-A+B+A"
                (generate sierpinski-triangle 2)))
         (is (= "B-A-B+A+B+A+B-A-B-A+B+A-B-A-B-A+B+A-B-A-B+A+B+A+B-A-B"
                (generate sierpinski-triangle 3))))

(deftest gen-coords-test
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
