(ns fiit-ir-reddit-crawler.parser.common-test
  (:require [clojure.test :refer :all]
            [fiit-ir-reddit-crawler.parser.common :refer :all]))

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