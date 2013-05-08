(ns stemmatic.test.renderer
  (:require [clojure.test :refer :all]
            [stemmatic.renderer :refer :all]))

(def state {:docs [{:name "a" :content "Doc a"}
                   {:name "b" :content "Doc b"}]
            :mst [["a" "b"]]})

(deftest test-render-graph
  (is (= (render-graph state) "Main graph")))

(deftest test-render-doc
  (is (= (render-doc (:docs state) "test-doc") "test-doc")))

(deftest test-render-diff
  (is (= (render-diff (:docs state) "doc1" "doc2") "Diff of doc1 and doc2")))
