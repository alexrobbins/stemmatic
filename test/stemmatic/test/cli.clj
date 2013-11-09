(ns stemmatic.test.cli
  (:require [clojure.test :refer :all]
            [stemmatic.cli :refer :all]))

(deftest test-mst->dot
  (is
   (= (mst->dot #{["1" "2"] ["2" "6"] ["2" "4"] ["4" "5"]}))
   (str
    "graph stemma {"
    "  \"1\" -- \"2\""
    "  \"2\" -- \"6\""
    "  \"2\" -- \"4\""
    "  \"4\" -- \"5\""
    "}")))
