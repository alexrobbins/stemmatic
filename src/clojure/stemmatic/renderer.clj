(ns stemmatic.renderer
  (:require [hiccup.core :refer (html)])
  (:import [name.fraser.neil.plaintext diff_match_patch]))

(defn render-graph
  "Render the graph of the document stemma."
  []
  "Main graph")

(defn render-doc
  "Render the document."
  [doc-name]
  doc-name)

(defn render-diff
  "Render the diff of the two documents."
  [doc-name-1 doc-name-2]
  (str "Diff of " doc-name-1 " and " doc-name-2))
