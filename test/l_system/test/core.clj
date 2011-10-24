(ns l-system.test.core
  (:use [l-system.core]
        [clojure.test]))


(deftest grow-tree-a-test
  (is (= "F[+F]F[-F]F"
         (grow tree-a 1)))
  (is (= "F[+F]F[-F]F[+F[+F]F[-F]F]F[+F]F[-F]F[-F[+F]F[-F]F]F[+F]F[-F]F"
         (grow tree-a 2))))
