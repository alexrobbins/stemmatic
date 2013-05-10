(ns stemmatic.test.core
  (:require [clojure.test :refer :all]
            [stemmatic.core :refer :all]))

(deftest test-get-weight
  (are
   [s1 s2 distance]
   (= (get-weight s1 s2) distance)
   "abc"  "abc" 0
   "ab"   "abc" 1
   "abc"  "ab"  1
   "abcd" "ab"  2
   "abcd" "cd"  2
   "The dog" "The dog ran" 4
   "The dog ran" "The red dog ran" 4
   ))

(def docs
  {
   "1" {:content "The dog ran."}
   "2" {:content "The red dog ran."}
   "3" {:content "The red dog ran."}
   "4" {:content "The red dog ran quickly."}
   "5" {:content "The red dog ran quickly home."}
   "6" {:content "The red dog rat."}}
  )

(deftest test-deduplicate-documents
  (is
   (= (count (deduplicate-documents docs)) 5)))

(deftest test-get-edges
  (let [edges (get-edges docs)]
    (are
     [key-pair edge-value]
     (= (edges key-pair) edge-value)
     ["1" "2"] 4
     ["2" "3"] 0
     ["1" "3"] 4
     ["1" "4"] 12
     ["2" "4"] 8
     )))

(deftest test-mst->dot
  (is
   (= (mst->dot (get-tree docs)))
   (str
    "graph stemma {"
    "  \"1\" -- \"2\""
    "  \"2\" -- \"6\""
    "  \"2\" -- \"4\""
    "  \"4\" -- \"5\""
    "}")
   ))

(deftest test-get-tree
  (is
   (= (get-tree (deduplicate-documents docs))
      #{["1" "2"] ["2" "6"] ["2" "4"] ["4" "5"]})))