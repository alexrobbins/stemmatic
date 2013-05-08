(ns stemmatic.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [stemmatic.core :refer (deduplicate-documents get-tree)]
            [stemmatic.renderer :refer (render-graph render-doc render-diff)]))

(def state (atom {}))

(defn set-docs
  "Set the current working set of documents."
  [documents]
  (let [docs (deduplicate-documents documents)]
    (swap! state (constantly {:docs docs
                              :tree (get-tree docs)}))))

(defroutes app-routes
  (GET "/" [] (render-graph state))
  (GET "/doc/:doc" [doc] (render-doc (:docs state) doc))
  (GET "/diff/:doc1/:doc2" [doc1 doc2] (render-diff (:docs state) doc1 doc2))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
