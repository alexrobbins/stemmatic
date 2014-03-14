(ns stemmatic.core
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [stemmatic.graph :refer [distance get-mst nodes->edges Node]])
  (:import [name.fraser.neil.plaintext diff_match_patch]))

(def dmp (diff_match_patch.))

(defrecord Document [id name content]
  Node
  (id [this] id)
  (distance [this other]
    (.diff_levenshtein
     dmp
     (.diff_main dmp content (:content other)))))

(defn get-tree
  "Given a list of documents, return a probable tree of documents."
  [docs]
  (let [doc-ids (map :id docs)
        edges (nodes->edges docs)]
    (get-mst doc-ids edges)))

;;;;;;;;;;;;;;;;;;;;;
;; Web api
;;;;;;;;;;;;;;;;;;;;;
(defn app-state []
  "Return the app state as an edn datastructure.
   App state is all documents plus their edges."
  )

(defn add-document [doc & [image]]
  "Add a document. Return the document data map.")

(defn auto-tree []
  "Automatically compute a minimum spanning tree and generate
   edges.
   Reuse existing edges where possible.")

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))