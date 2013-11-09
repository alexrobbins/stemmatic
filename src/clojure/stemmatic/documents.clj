(ns stemmatic.documents
  (:require [stemmatic.graph :refer [distance get-mst nodes->edges Node]])
  (:import [name.fraser.neil.plaintext diff_match_patch]))

(def dmp (diff_match_patch.))

(defrecord Document [name content aliases]
  Node
  (id [this] name)
  (distance [this other]
    (.diff_levenshtein
     dmp
     (.diff_main dmp content (:content other)))))

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

(defn get-tree
  "Given a list of documents, return a probable tree of documents."
  [docs]
  (let [doc-names (map :name docs)
        edges (nodes->edges docs)]
    (get-mst doc-names edges)))
