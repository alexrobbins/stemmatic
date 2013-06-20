(ns stemmatic.core
  (:require [stemmatic.graph :refer (get-mst)])
  (:import [name.fraser.neil.plaintext diff_match_patch]))

(defn deduplicate-documents
  "Collapse identical documents into a single doc with aliases"
  [docs]
  (let [doc-map (group-by #(.hashCode (:content (second %))) docs)]
    (into {}
          (for [[_ same-docs] doc-map]
            [(:name (ffirst same-docs)) {:content (:content (second (first same-docs)))
                                         :aliases (set
                                                   (concat
                                                    (map :name same-docs)
                                                    ))}]))))

(defn get-weight
  "Get the levenshtein distance between two strings"
  [s1 s2]
  (let [dmp (diff_match_patch.)]
    (.diff_levenshtein dmp (.diff_main dmp s1 s2))))

(defn get-edges
  "Calculate the weight of the edges between the documents."
  [docs]
  (into {}
        (for [d1 docs d2 docs
              :let [n1 (first d1) n2 (first d2)]
              :when (> 0 (compare n1 n2))]
          [[n1 n2]
           (get-weight (:content (second d1)) (:content (second d2)))])))

(defn get-tree
  "Given a list of documents, return the probable tree of documents."
  [docs]
  (let [doc-names (keys docs)
        edges (get-edges docs)]
    (get-mst doc-names edges)))
