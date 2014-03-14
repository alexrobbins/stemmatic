(ns stemmatic.graph
  "Handle the work of creating a graph from the data, as well as
   running algorithms across it."
  (:require [clojure.data.priority-map :refer (priority-map)]))

(defprotocol Node
  "A node is any object that knows how close it is to other nodes. The distance
   should be commutative. (= (distance a b) (distance b a)). Nodes also provide an
   identifier."
  (id [this] "The identifier of this node.")
  (distance [this other] "The distance between this node and the other node."))

(defn nodes->edges
  "Calculate the the edges and their distances between the nodes."
  [nodes]
  (into {}
        (for [n1 nodes
              :let [id1 (id n1)]
              n2 nodes
              :let [id2 (id n2)]
              :when (neg? (compare id1 id2))]
          [[id1 id2] (distance n1 n2)])))

(defn get-edges
  "Get the edges containing a node, minus edges to already covered nodes."
  [node nodes edges covered-nodes]
  (for [n nodes :when (not (covered-nodes n)) :when (not= node n)
        :let [k (vec (sort [node n]))]]
    [k (edges k)]))

;; TODO lazily calculate edge costs, maybe with a memoized distance fn?
(defn get-mst
  "Execute Prim's algorithm on the given graph. Return a minimum
   spanning tree of edges. (The nodes are the same.)"
  [nodes]
  (let [node-ids (map id nodes)
        edges (nodes->edges nodes)]
    (if-let [root-node (first node-ids)]
      (let [node-ids (set node-ids)]
        (loop [covered-nodes #{root-node}
               minimal-edges #{}
               edge-queue (reduce conj (priority-map)
                                  (get-edges root-node node-ids edges covered-nodes))]
          (let [[edge-nodes _] (peek edge-queue)
                edge-queue (pop edge-queue)]
            (if (every? covered-nodes edge-nodes) ; both nodes already covered, skip this edge
              (recur covered-nodes minimal-edges edge-queue)
              (let [new-node      (first (remove covered-nodes edge-nodes))
                    covered-nodes (conj covered-nodes new-node)
                    minimal-edges (conj minimal-edges edge-nodes)
                    edge-queue (reduce conj edge-queue
                                       (get-edges new-node node-ids edges covered-nodes))]
                (if (= node-ids covered-nodes)
                  minimal-edges
                  (recur covered-nodes minimal-edges edge-queue)))))))
      #{})))