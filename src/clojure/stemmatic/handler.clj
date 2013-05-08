(ns stemmatic.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [stemmatic.renderer :refer (render-graph render-doc render-diff)]))

(defroutes app-routes
  (GET "/" [] (render-graph))
  (GET "/doc/:doc" [doc] (render-doc doc))
  (GET "/diff/:doc1/:doc2" [doc1 doc2] (render-diff doc1 doc2))
  (route/resources "/static/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
