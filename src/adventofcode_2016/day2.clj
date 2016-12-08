(ns adventofcode-2016.day2
  (:require [clojure.java.io :as io]
            [clojure.set :as set]))


(def lines (line-seq (io/reader (io/resource "day2"))))

(def square-keypad-map
  {1 [-1 1]  2 [0 1]  3 [1 1]
   4 [-1 0]  5 [0 0]  6 [1 0]
   7 [-1 -1] 8 [0 -1] 9 [1 -1]
   })

;; (def diamond-keypad-map {})

(def diamond-keypad-map
  {                    1  [0 2]
            2  [-1 1]  3  [0 1]  4  [1 1]
   5 [-2 0] 6  [-1 0]  7  [0 0]  8  [1 0]  9 [2 0]
            \A [-1 -1] \B [0 -1] \C [1 -1]
                       \D [0 -2]
   })

(def movement-vectors {
                       \L [-1 0]
                       \R [1 0]
                       \U [0 1]
                       \D [0 -1]
                       })

(defn move
  [current-button direction keypad]
  (or ((set/map-invert keypad) (mapv + (movement-vectors direction)
                                 (keypad current-button)))
      current-button))

(defn ending-button
  [input keypad-map]
  (->> input
       (reduce (fn [button movement]
                 (move button movement keypad-map))
               5)))

(defn keycode
  [instruction-lines keypad]
  (->> instruction-lines
       (map seq)
       (map (fn [x]
              (ending-button x keypad)))
       (apply str)))

#_(keycode lines square-keypad-map)

#_(keycode lines diamond-keypad-map)
