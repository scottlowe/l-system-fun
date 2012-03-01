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
