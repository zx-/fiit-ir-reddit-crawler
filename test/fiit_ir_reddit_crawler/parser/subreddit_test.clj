(ns fiit-ir-reddit-crawler.parser.subreddit_test
  (:require [clojure.test :refer :all]
            [fiit-ir-reddit-crawler.parser.subreddit :refer :all]
            [fiit-ir-reddit-crawler.parser.common :refer :all]))


(def test-subreddit (slurp "dev-resources/test-subreddit.html"))

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