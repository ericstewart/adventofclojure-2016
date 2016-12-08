(ns adventofcode-2016.day2
  (:require [clojure.java.io :as io]
            [clojure.set :as set]))

;; Day 2 problem for Advent of Code 2016
;; http://adventofcode.com/2016/day/2  

;; Keypads have each button mapped to a location [x y]
;; With button coordinates laid out relative to each other
;; given a Cartesian coordinate system

(def square-keypad-map
  {1 [-1 1]  2 [0 1]  3 [1 1]
   4 [-1 0]  5 [0 0]  6 [1 0]
   7 [-1 -1] 8 [0 -1] 9 [1 -1]
   })

(def diamond-keypad-map
  {                    1  [0 2]
            2  [-1 1]  3  [0 1]  4  [1 1]
   5 [-2 0] 6  [-1 0]  7  [0 0]  8  [1 0]  9 [2 0]
            \A [-1 -1] \B [0 -1] \C [1 -1]
                       \D [0 -2]
   })

(def movement-vector {
                      \L [-1 0]
                      \R [1 0]
                      \U [0 1]
                      \D [0 -1]
                      })

(defn move
  [current-button direction keypad]
  "Move from the current button to a new button (if possible) for the
   given keypad configuration. Will stay on the current button if a
   move is not possible."
  (or ((set/map-invert keypad) (mapv + (movement-vector direction)
                                     (keypad current-button)))
      current-button))

(defn ending-button
  [input keypad-map]
  "Determine the button to press given a series of input
   instructions (L,R,U,D) and a keypad configuration. Assumes
   that we always start with 5"
  (->> input
       (reduce (fn [button movement]
                 (move button movement keypad-map))
               5)))

(defn keycode
  [instruction-lines keypad]
  "Given lines of instructions and a keypad config,
   return the keycode sequence"
  (->> instruction-lines
       (map seq)
       (map (fn [x]
              (ending-button x keypad)))
       (apply str)))

;; My problem input
(def lines (line-seq (io/reader (io/resource "day2"))))

#_(keycode lines square-keypad-map)

#_(keycode lines diamond-keypad-map)
