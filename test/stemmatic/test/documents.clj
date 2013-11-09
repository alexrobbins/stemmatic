(ns stemmatic.test.documents
  (:require [clojure.test :refer :all]
            [stemmatic.documents :refer :all]
            [stemmatic.graph :refer [distance]]))

(deftest test-document-distance
  (are
   [s1 s2 output]
   (= (distance (map->Document {:content s1})
                (map->Document {:content s2})) output)
   "abc"  "abc" 0
   "ab"   "abc" 1
   "abc"  "ab"  1
   "abcd" "ab"  2
   "abcd" "cd"  2
   "The dog" "The dog ran" 4
   "The dog ran" "The red dog ran" 4
   ))

(def docs
  (map map->Document
       [{:name "1" :content "The dog ran."}
        {:name "2" :content "The red dog ran."}
        {:name "3" :content "The red dog ran."}
        {:name "4" :content "The red dog ran quickly."}
        {:name "5" :content "The red dog ran quickly home."}
        {:name "6" :content "The red dog rat."}]))

(deftest test-deduplicate-documents
  (let [dd (deduplicate-documents docs)]
    (is
     (= (count dd) 5))
    (is
     (= (:aliases (first (filter #(= "2" (:name %)) dd)))
        #{"3"}))))

(deftest test-get-tree
  (is
   (= (get-tree (deduplicate-documents docs))
      #{["1" "2"] ["2" "6"] ["2" "4"] ["4" "5"]})))
