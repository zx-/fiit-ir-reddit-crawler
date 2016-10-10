(ns fiit-ir-reddit-crawler.parser.subreddit_test
  (:require [clojure.test :refer :all]
            [fiit-ir-reddit-crawler.parser.subreddit :refer :all]))

(deftest parse-subreddt-test)
(def test-subreddit (slurp "dev-resources/test-subreddit.html"))

(deftest string->resource-test
  (let [string-html "<div>aa</div>"
        result (string->resource string-html)]
    (is (= (:tag (first result))
           :html))
    (is (= (-> result
               first
               :content
               first
               :content
               first
               :tag)
           :div))
    (is (= (-> result
               first
               :content
               first
               :content
               first
               :content
               first)
           "aa"))))

(deftest get-sub-count-test
  (let [resource (string->resource test-subreddit)]
    (is (= (get-sub-count resource)
           13278867))))

(deftest get-name-test
  (let [resource (string->resource test-subreddit)]
    (is (= (get-name resource)
           "worldnews"))))

(deftest get-link-test
  (let [resource (string->resource test-subreddit)]
    (is (= (get-link resource)
           "https://www.reddit.com/r/worldnews/"))))

(deftest parse-subreddit-test
    (is (= (parse-subreddit test-subreddit)
           {:sub-count 13278867
            :name "worldnews"
            :url "https://www.reddit.com/r/worldnews/"})))