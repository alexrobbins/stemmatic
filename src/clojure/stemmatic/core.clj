(ns stemmatic.core
  (:require [stemmatic.graph :refer (get-mst)])
  (:import [name.fraser.neil.plaintext diff_match_patch]))

(defn deduplicate-documents
  "Collapse identical documents into a single doc with aliases"
  [docs]
  (let [doc-map (group-by #(.hashCode (:content %)) docs)]
    (for [[_ same-docs] doc-map :let [first-doc (first same-docs)]]
      (assoc first-doc
        :aliases (->
                  (concat
                   (map :name same-docs)
                   (mapcat :aliases same-docs))
                  set
                  (disj (:name first-doc)))))))

(defn get-weight
  "Get the levenshtein distance between two strings"
  [s1 s2]
  (let [dmp (diff_match_patch.)]
    (.diff_levenshtein dmp (.diff_main dmp s1 s2))))

(defn get-edges
  "Calculate the weight of the edges between the documents."
  [docs]
  (into {}
        (for [{n1 :name d1 :content} docs {n2 :name d2 :content} docs
              :when (neg? (compare n1 n2))]
          [[n1 n2]
           (get-weight d1 d2)])))

(defn get-tree
  "Given a list of documents, return a probable tree of documents."
  [docs]
  (let [doc-names (map :name docs)
        edges (get-edges docs)]
    (get-mst doc-names edges)))
