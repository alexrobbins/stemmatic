(ns stemmatic.cmd-line
  (:require [clojure.java.io :refer (file)]
            [stemmatic.core :refer (deduplicate-documents get-tree)]))

(defn get-files
  "Get the documents from the given directory."
  [directory]
  (into {}
        (map (fn [in-file] [(.getName in-file) {:content (slurp in-file)}])
             (filter (memfn isFile)
                     (file-seq (file directory))))))

(defn mst->dot
  "Convert the documents and a minimum spanning tree to a dot file for command
   line usage."
  [mst]
  (str
   (apply str "graph stemma {\n"
          (map (fn [[n1 n2]] (str "\"" n1 "\" -- \"" n2 "\"\n")) mst))
   "}"))

(defn -main
  "Call get-tree on the documents in the given directory, returns a dot file of
   a probably document tree."
  [directory & args]
  (let [files (get-files directory)
        docs  (deduplicate-documents files)
        mst   (get-tree docs)]
    (println (mst->dot mst))))
