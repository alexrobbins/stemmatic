(ns stemmatic.test.rest-api
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [stemmatic.rest-api :refer :all]))

(deftest test-app
  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))