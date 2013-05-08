(ns stemmatic.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

(defn show-graph
  "Show the document stemma."
  []
  "Main graph")

(defn show-doc
  "Show a document's text."
  [doc-name]
  doc-name)

(defn show-diff
  "Show the diff between two documents."
  [doc-name-1 doc-name-2]
  (str "Diff of " doc-name-1 " and " doc-name-2))

(defroutes app-routes
  (GET "/" [] (show-graph))
  (GET "/doc/:doc" [doc] (show-doc doc))
  (GET "/diff/:doc1/:doc2" [doc1 doc2] (show-diff doc1 doc2))
  (route/resources "/static/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
