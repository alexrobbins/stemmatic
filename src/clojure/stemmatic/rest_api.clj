(ns stemmatic.rest-api
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [stemmatic.documents :refer [deduplicate-documents get-tree]]))

;; TODO Replace this with datomic
(defonce state (atom {}))

(defn get-doc
  [doc-name]
  (get-in @state [:docs doc-name]))

(defn add-doc
  "Add a document to the working set. Drop any cached mst if it exists."
  [doc]
  (swap! state assoc-in))

(defn list-docs []
  "List all documents."
  (-> @state :docs vals))

;; TODO use liberator to setup rest resources
#_(defresource document)

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
