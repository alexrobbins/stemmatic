(ns stemmatic.test.renderer
  (:require [clojure.test :refer :all]
            [stemmatic.renderer :refer :all]))

(deftest test-render-graph
  (is (= (render-graph) "Main graph")))

(deftest test-render-doc
  (is (= (render-doc "test-doc") "test-doc")))

(deftest test-render-diff
  (is (= (render-diff "doc1" "doc2") "Diff of doc1 and doc2")))
