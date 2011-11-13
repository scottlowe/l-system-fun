(ns l-system.test.core
  (:use [l-system.core]
        [clojure.test]))


(deftest generate-tree-a-test
  (is (= "F[+F]F[-F]F"
         (generate tree-a 1)))
  (is (= "F[+F]F[-F]F[+F[+F]F[-F]F]F[+F]F[-F]F[-F[+F]F[-F]F]F[+F]F[-F]F"
         (generate tree-a 2))))
