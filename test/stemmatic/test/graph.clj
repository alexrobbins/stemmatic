(ns stemmatic.test.graph
  (:require [clojure.test :refer :all]
            [stemmatic.graph :refer :all]))

(defrecord DummyNode [id x-position]
  Node
  (distance [this other] (java.lang.Math/abs (- x-position (:x-position other))))
  (id [this] id))

(deftest test-nodes->edges
  (are
   [nodes edges]
   (= (set (nodes->edges (map #(apply ->DummyNode %) nodes))) (set edges))
   [[:a 0] [:b 1]] [[[:a :b] 1]]
   [[:a 0] [:b 1] [:c 2]] [[[:a :b] 1] [[:a :c] 2] [[:b :c] 1]]))

(deftest test-get-edges
  (let [nodes #{:a :b :c :d}
        edges {[:a :b] 1
               [:a :c] 2
               [:a :d] 3
               [:b :c] 1
               [:b :d] 1
               [:c :d] 1}]
    (are
     [node covered-nodes output]
     (= (set (get-edges node nodes edges covered-nodes)) output)
     :a #{} #{[[:a :b] 1] [[:a :c] 2] [[:a :d] 3]}
     :a #{:b} #{[[:a :c] 2] [[:a :d] 3]}
     :a #{:b :c} #{[[:a :d] 3]}
     :a #{:b :c :d} #{}
     :d #{:a} #{[[:b :d] 1] [[:c :d] 1]}
     :d #{:a :c} #{[[:b :d] 1]}
     )))

(deftest test-get-mst
  (are
   [nodes edges mst]
   (= (get-mst nodes edges) mst)
   #{}         {}                                #{}
   #{:a :b}    {[:a :b] 1}                       #{[:a :b]}
   #{:a :b :c} {[:a :b] 1, [:b :c] 1, [:a :c] 5} #{[:a :b] [:b :c]}
   #{:a :b :c} {[:a :b] 1, [:b :c] 3, [:a :c] 5} #{[:a :b] [:b :c]}
   #{:a :b :c} {[:a :b] 3, [:b :c] 3, [:a :c] 5} #{[:a :b] [:b :c]}
   #{:a :b :c} {[:a :b] 7, [:b :c] 3, [:a :c] 5} #{[:a :c] [:b :c]}
   ))