(ns stemmatic.test.web.renderer
  (:require [clojure.test :refer :all]
            [stemmatic.web.renderer :refer :all]))

(def state {:docs {"a" {:name "a" :content "Doc a"}
                   "b" {:name "b" :content "Doc b"}}})

(deftest test-render-graph
  (is (= (render-graph state) (list "Main graph"
                                    "Upload docs form"))))

(deftest test-render-doc
  (is (= (render-doc (:docs state) "a")
         "<h2>a</h2><pre content=\"Doc a\" name=\"a\"></pre>")
      (= (render-doc (:docs state) "not-found") nil)))

(deftest test-render-diff
  (is (= (render-diff (:docs state) "doc1" "doc2") "Diff of doc1 and doc2")))
