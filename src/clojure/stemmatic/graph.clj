(ns stemmatic.graph
  "Handle the work of creating a graph from the data, as well as
   running algorithms across it."
  (:require [clojure.data.priority-map :refer (priority-map)]))

(defn get-edges
  "Get the edges containing a node, minus edges to already covered nodes.."
  [node nodes edges covered-nodes]
  (for [n nodes :when (not (covered-nodes n)) :when (not= node n)
        :let [k (vec (sort [node n]))]]
    [k (edges k)]))

(defn get-mst
  "Execute Prim's algorithm on the given graph. Return a minimum
   spanning tree of edges. (The nodes are the same.)"
  [nodes edges]
  (if-let [root-node (first nodes)]
    (let [nodes (set nodes)]
      (loop [covered-nodes #{root-node}
             minimal-edges #{}
             edge-queue (reduce conj (priority-map)
                                (get-edges root-node nodes edges covered-nodes))]
        (let [[edge-nodes _] (peek edge-queue)
              edge-queue (pop edge-queue)]
          (if (every? covered-nodes edge-nodes) ; both nodes already covered, skip this edge
            (recur covered-nodes minimal-edges edge-queue)
            (let [new-node      (first (remove covered-nodes edge-nodes))
                  covered-nodes (conj covered-nodes new-node)
                  minimal-edges (conj minimal-edges edge-nodes)
                  edge-queue (reduce conj edge-queue
                                     (get-edges new-node nodes edges covered-nodes))]
              (if (= nodes covered-nodes)
                minimal-edges
                (recur covered-nodes minimal-edges edge-queue)))))))
    #{}))