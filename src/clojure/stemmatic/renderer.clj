(ns stemmatic.renderer
  (:require [hiccup.core :refer (html)])
  (:import [name.fraser.neil.plaintext diff_match_patch]))

(defn render-graph
  "Render the graph of the document stemma."
  [state]
  (list
   (when-not (empty? state)
     "Main graph")
   "Upload docs form"))

(defn render-doc
  "Render the document."
  [docs doc-name]
  (if-let [doc (docs doc-name)]
    (let [aliases (:aliases doc)]
      (html
       [:h2 doc-name]
       (when-not (empty? aliases)
         [:h3 "Aliases"]
         [:div (apply str (interpose ", " aliases))])
       [:pre
        (docs doc-name)]))
    nil))

(defn render-diff
  "Render the diff of the two documents."
  [docs doc-name-1 doc-name-2]
  (str "Diff of " doc-name-1 " and " doc-name-2))

(defn list-docs
  "List all docs currently loaded."
  [docs]
  (html [:head [:title "Loaded Documents"]]
        [:body
         [:h1 "Loaded Documents"]
         (for [{doc-name :name} docs]
           [:div [:a {:href (str "/doc/" doc-name)} doc-name]])]))

(defn add-new-doc
  "Show add new doc form."
  []
  "New doc form")
