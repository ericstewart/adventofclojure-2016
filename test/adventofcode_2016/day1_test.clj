(ns adventofcode-2016.day1-test
  (:require  [clojure.test :refer :all]
             [adventofcode-2016.day1 :as day1]))

(deftest new-direction-right-turns-test
  (testing "Turning right"
    (is (= :east
           (day1/new-direction :north \R)))
    (is (= :south
           (day1/new-direction :east \R)))
    (is (= :west
           (day1/new-direction :south \R)))
    (is (= :north
           (day1/new-direction :west \R)))))

(deftest new-direction-left-turns-test
  (testing "Turning left"
    (is (= :west
           (day1/new-direction :north \L)))
    (is (= :south
           (day1/new-direction :west \L)))
    (is (= :east
           (day1/new-direction :south \L)))
    (is (= :north
           (day1/new-direction :east \L)))))

(deftest move-test
  (testing "Movement"
    (is (= [[:south [1 -1]]]
           (day1/move [[:east [1, 0]]] "R1")))
    (is (= [[:east [1 0]][:east [2 0]]]
           (day1/move [[:north [0, 0]]] "R2")))))

(deftest new-coordinates-test
  (testing "New coordinates"
    (is (= '([:east [1 0]])
           (day1/new-coordinates [0, 0] :east 1)))
    (is (= '([:north [1 2]])
           (day1/new-coordinates [1 1] :north 1)))))

(deftest one-block
  (testing "Move per instructions"
    (is (= [1 0] 
           (:location (day1/move-blocks ["R1"]))))
    (is (= [1 -1] 
           (:location (day1/move-blocks ["R1","R1"]))))
    (is (= [0 0] 
           (:location (day1/move-blocks ["R1" "L1" "L1" "L1"]))))
    (is (= [125 0] 
           (:location (day1/move-blocks ["R125"]))))
    (is (= [2 3]
           (:location (day1/move-blocks ["R2" "L3"]))))
    (is (= [0 -2]
           (:location (day1/move-blocks ["R2" "R2" "R2"]))))))

(deftest shortest-path-to-destination
  (testing "Stop at first coordinates encountered twice"
    (is (= [4, 0]
           (:location (day1/move-blocks ["R8" "R4" "R4" "R8"]))))))

(deftest calc-distance
  (testing "Calculate taxicab distance"
    (is (= 1
           (day1/taxicab-distance [1 0])))
    (is (= 1
           (day1/taxicab-distance [-1 0]))) 
    (is (= 2
           (day1/taxicab-distance [1 1]))) 
    (is (= 4
           (day1/taxicab-distance [2 2])))
    (is (= 4
           (day1/taxicab-distance [-2 -2])))
    (is (= 125 
           (day1/taxicab-distance [125 0])))))
