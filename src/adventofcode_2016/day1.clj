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
  [current-position direction distance]
  (let [modifier-tuple (case direction
                         :north [0 distance]
                         :east  [distance 0]
                         :south [0 (* -1  distance)]
                         :west  [(* -1 distance) 0])]
    [(+ (first current-position)
        (first modifier-tuple))
     (+ (last current-position)
        (last modifier-tuple))]))

(defn move
  [current-position movement]
  (let [direction (new-direction (first current-position)
                                 (first (s/trim movement)))
        distance (string->integer (apply str (rest (s/trim  movement))))]
    [direction (new-coordinates (last current-position)
                                direction
                                distance)]))

(defn move-blocks
  [instructions]
  "From a starting location, move according to provide instructions
   and return the ending coordinates"
  (let [movements (s/split instructions #",")]
    (reduce move [:north [0,0]] movements)))

(defn distance-between
  [start end]
  (+ (math/abs (- (first end)
                  (first start)))
     (math/abs (- (last end)
                  (last start)))))

(def problem-instructions "L1, L5, R1, R3, L4, L5, R5, R1, L2, L2, L3, R4, L2, R3, R1, L2, R5, R3, L4, R4, L3, R3, R3, L2, R1, L3, R2, L1, R4, L2, R4, L4, R5, L3, R1, R1, L1, L3, L2, R1, R3, R2, L1, R4, L4, R2, L189, L4, R5, R3, L1, R47, R4, R1, R3, L3, L3, L2, R70, L1, R4, R185, R5, L4, L5, R4, L1, L4, R5, L3, R2, R3, L5, L3, R5, L1, R5, L4, R1, R2, L2, L5, L2, R4, L3, R5, R1, L5, L4, L3, R4, L3, L4, L1, L5, L5, R5, L5, L2, L1, L2, L4, L1, L2, R3, R1, R1, L2, L5, R2, L3, L5, L4, L2, L1, L2, R3, L1, L4, R3, R3, L2, R5, L1, L3, L3, L3, L5, R5, R1, R2, L3, L2, R4, R1, R1, R3, R4, R3, L3, R3, L5, R2, L2, R4, R5, L4, L3, L1, L5, L1, R1, R2, L1, R3, R4, R5, R2, R3, L2, L1, L5")

(distance-between [0 0]
                  (last (move-blocks problem-instructions)))
