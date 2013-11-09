(ns stemmatic.web.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [stemmatic.documents :refer [deduplicate-documents get-tree]]
            [stemmatic.web.renderer :refer [render-graph render-doc render-diff list-docs]]))

(defonce ^:private state (atom {:docs {}}))

(defn get-docs
  "Return the documents in the working set."
  []
  (:docs @state))

(defn set-docs
  "Set the current working set of documents."
  [documents]
  (let [docs    (deduplicate-documents documents)
        doc-map (into {} (for [{name :name :as d} docs] [name d]))]
    (swap! state (constantly {:docs doc-map}))))

(defn add-doc
  "Add a document to the working set. Drop any cached mst if it exists."
  [doc]
  (dosync
   (let [docs (conj (vals (:docs @state)) doc)]
     (reset! state {:docs (deduplicate-documents docs)}))))

(defroutes app-routes
  (GET "/" [] (render-graph @state))
  (GET "/doc/" [] (list-docs (:docs @state)))
  (GET "/doc/:doc" [doc] (render-doc (:docs @state) doc))
  (GET "/diff/:doc1/:doc2" [doc1 doc2] (render-diff (:docs @state) doc1 doc2))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
