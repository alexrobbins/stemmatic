(ns stemmatic.core
  (:require [clojure.java.io :refer (file)]
            [stemmatic.graph :refer (get-mst)])
  (:import [name.fraser.neil.plaintext diff_match_patch]))

(defn deduplicate-documents
  "Collapse identical documents into a single doc with aliases"
  [docs]
  (let [doc-map (group-by #(.hashCode (:content %)) docs)]
    (for [[_ same-docs] doc-map]
      {:name (:name (first same-docs)) :content (:content (first same-docs))
       :aliases (set (map :name same-docs))})))

(defn get-weight
  "Get the levenshtein distance between two strings"
  [s1 s2]
  (let [dmp (diff_match_patch.)]
    (.diff_levenshtein dmp (.diff_main dmp s1 s2))))

(defn get-edges
  "Calculate the weight of the edges between the documents."
  [docs]
  (into {}
        (for [d1 docs d2 docs :when (> 0 (compare (:name d1) (:name d2)))]
          [[(:name d1) (:name d2)]
           (get-weight (:content d1) (:content d2))])))

(defn get-tree
  "Given a list of documents, return the probable tree of documents."
  [docs]
  (let [doc-names (map :name docs)
        edges (get-edges docs)]
    (get-mst doc-names edges)))

(defn get-files
  "Get a seq of strings, which are the content of the files in a given directory."
  [directory]
  (map (fn [in-file] {:name (.getName in-file) :content (slurp in-file)})
       (filter (memfn isFile)
               (file-seq (file directory)))))

(defn mst->dot
  "Convert the documents and a minimum spanning tree to a dot file for command
   line usage."
  [mst]
  (str
   (apply str "graph stemma {\n"
          (map (fn [[n1 n2]] (str "\"" n1 "\" -- \"" n2 "\"\n")) mst))
   "}")
  )

(defn -main
  "Call get-tree on the documents in the given directory, returns a dot file of
   a probably document tree."
  [directory & args]
  (let [files (get-files directory)
        docs  (deduplicate-documents files)
        mst   (get-tree docs)]
    (println (mst->dot mst))))