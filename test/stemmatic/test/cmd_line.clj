(ns stemmatic.test.cmd-line
  (:require [clojure.test :refer :all]
            [stemmatic.cmd-line :refer :all]))

(deftest test-mst->dot
  (is
   (= (mst->dot #{["1" "2"] ["2" "6"] ["2" "4"] ["4" "5"]}))
   (str
    "graph stemma {"
    "  \"1\" -- \"2\""
    "  \"2\" -- \"6\""
    "  \"2\" -- \"4\""
    "  \"4\" -- \"5\""
    "}")
   ))
