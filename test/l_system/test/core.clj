(ns l-system.test.core
  (:use [l-system.core]
        [clojure.test]))


(deftest generate-tree-a-test
  (is (= "F[+F]F[-F]F"
         (generate tree-a 1)))
  (is (= "F[+F]F[-F]F[+F[+F]F[-F]F]F[+F]F[-F]F[-F[+F]F[-F]F]F[+F]F[-F]F"
         (generate tree-a 2))))


(deftest generate-coordinates-test
         (is (= [{:x 200 :y 670 :end-x 200.0 :end-y 662.0}
                 {:x 200.0 :y 662.0 :end-x 196.53072732329966 :end-y 654.7913838294232}
                 {:x 200.0 :y 662.0 :end-x 200.0 :end-y 654.0}
                 {:x 200.0 :y 654.0 :end-x 203.46927267670034 :end-y 646.7913838294232}
                 {:x 200.0 :y 654.0 :end-x 200.0 :end-y 646.0}
                 {:x 200.0 :y 646.0 :end-x 196.53072732329966 :end-y 638.7913838294232}
                 {:x 196.53072732329966 :y 638.7913838294232 :end-x 190.2785635439491 :end-y 633.8003470557481}
                 {:x 196.53072732329966 :y 638.7913838294232 :end-x 193.06145464659932 :end-y 631.5827676588465}]
                (take 10
                      (coords-seq {:x 200 :y 670 :angle 180} rules)))))


