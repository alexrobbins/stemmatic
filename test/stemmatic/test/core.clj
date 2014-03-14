(ns stemmatic.test.core
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [stemmatic.core :refer :all]
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
       [{:id "1" :content "The dog ran."}
        {:id "2" :content "The red dog ran."}
        {:id "3" :content "The red dog ran quickly."}
        {:id "4" :content "The red dog ran quickly home."}
        {:id "5" :content "The red dog rat."}]))

(deftest test-get-tree
  (is
   (= (get-tree docs)
      #{["1" "2"] ["2" "5"] ["2" "3"] ["3" "4"]})))

(deftest test-app
  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))