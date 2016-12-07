(ns adventofcode-2016.day1
  (:require [clojure.math.numeric-tower :as math]
            [clojure.string :as s]))

;; Day 1 problem for Advent of Code 2016
;; Based on https://en.wikipedia.org/wiki/Taxicab_geometry

(def directions [:north :east :south :west])

(defn string->integer [s] 
  (when-let [d (re-find #"-?\d+" s)] (Integer. d)))

(defn new-direction
  [current-direction movement]
  (let [movement-modifier (if (= \R
                                 movement)
                            1
                            -1)
        current-index (.indexOf directions
                                current-direction)]
    (nth directions
         (mod (+ movement-modifier
                 current-index)
              4))))

(defn new-coordinates
  [[current-x current-y] direction distance]
  (let [modifier-tuple (case direction
                         :north [0 distance]
                         :east  [distance 0]
                         :south [0 (* -1  distance)]
                         :west  [(* -1 distance) 0]) 
        new-x (+ current-x
                 (first modifier-tuple))
        new-y (+ current-y
                 (last modifier-tuple))]
    (do 
      (if (#{:north :south} direction)
        (map (fn [y]
                [direction [new-x y]])
              (range (+ (if (= direction :south) -1 1) current-y) 
                     (if (= direction :south) (- new-y 1) (+ new-y 1))
                     (if (= direction :south) -1 1)))
        (map (fn [x]
                [direction [x new-y]])
              (range (+ (if (= direction :west) -1 1) current-x)
                     (if (= direction :west) (- new-x 1) (+ new-x 1))
                     (if (= direction :west) -1 1)))))))

(defn move
  [current-position movement]
  (let [direction (new-direction (first (last current-position))
                                 (first movement))
        distance (string->integer (apply str (rest movement)))]
    (new-coordinates (second (last current-position))
                     direction
                     distance)))

(defn move-blocks
  [instructions]
  "From a starting location, move according to provide instructions
   and return the ending coordinates"
  (let []
    (->> (map str instructions)
         (reductions move [[:north [0 0]]])
         (apply concat)
         (map second)
         (reduce (fn [memory location]
                   (if ((:visited memory) location)
                     (reduced 
                      (assoc memory :location location :visited (conj (:visited memory) location)))
                     (assoc memory :location location :visited (conj (:visited memory) location))))
                 {:location [] :visited  #{}}))))

(defn taxicab-distance
  [[end-x end-y]]
  "Calculate the taxicab distance assuming an origin of [0 0]"
  (+ (math/abs end-x)
     (math/abs end-y)))

(def problem-instructions (read-string  "[L1, L5, R1, R3, L4, L5, R5, R1, L2, L2, L3, R4, L2, R3, R1, L2, R5, R3, L4, R4, L3, R3, R3, L2, R1, L3, R2, L1, R4, L2, R4, L4, R5, L3, R1, R1, L1, L3, L2, R1, R3, R2, L1, R4, L4, R2, L189, L4, R5, R3, L1, R47, R4, R1, R3, L3, L3, L2, R70, L1, R4, R185, R5, L4, L5, R4, L1, L4, R5, L3, R2, R3, L5, L3, R5, L1, R5, L4, R1, R2, L2, L5, L2, R4, L3, R5, R1, L5, L4, L3, R4, L3, L4, L1, L5, L5, R5, L5, L2, L1, L2, L4, L1, L2, R3, R1, R1, L2, L5, R2, L3, L5, L4, L2, L1, L2, R3, L1, L4, R3, R3, L2, R5, L1, L3, L3, L3, L5, R5, R1, R2, L3, L2, R4, R1, R1, R3, R4, R3, L3, R3, L5, R2, L2, R4, R5, L4, L3, L1, L5, L1, R1, R2, L1, R3, R4, R5, R2, R3, L2, L1, L5]"))

#_(move-blocks problem-instructions)

#_(distance-between [0 0]
                    (:location (move-blocks problem-instructions)))
