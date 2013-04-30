(ns stemmatic.core
  (:require [clojure.java.io :refer (file)]))

(defn get-tree
  "Given a list of documents, return the probably tree of documents."
  [documents]
  )

(defn get-files
  "Get a seq of strings, which are the contents of the files in a given directory."
  [directory]
  (map slurp
       (filter (memfn isFile)
               (file-seq (file directory)))))

(defn -main
  "Call get-tree on the documents in the given directory."
  [directory & args]
  ;; list the files in the directory
  (let [files (get-files directory)]
    (doseq [f files] (println f)))
  ;; for each file, load it into memory
  )
