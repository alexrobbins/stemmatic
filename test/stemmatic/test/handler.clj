(ns stemmatic.test.handler
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [stemmatic.handler :refer :all]))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "Main graph"))))

  (testing "show-doc"
    (let [response (app (request :get "/doc/test-name"))]
      (is (= (:status response) 200)
          (= (:body response) "test-name"))))

  (testing "show-diff"
    (let [response (app (request :get "/diff/name1/name2"))]
      (is (= (:status response) 200)
          (= (:body response) "Diff of name1 and name2"))))

  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))