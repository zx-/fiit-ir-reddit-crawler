(ns fiit-ir-reddit-crawler.parser.subreddit
  (:require [net.cgrand.enlive-html :as html]
            [fiit-ir-reddit-crawler.parser.common :refer :all]))


(defn get-sub-count
  [resource]
  (-> (html/select resource [:.subscribers :> :.number])
      first
      :content
      first
      (clojure.string/replace #"," "")
      read-string))

(defn get-name
  [resource]
  (-> (html/select resource [:.redditname :> :a])
      first
      :content
      first))

(defn get-link
  [resource]
  (-> (html/select resource [:.redditname :> :a])
      first
      :attrs
      :href))

(defn parse-subreddit
  "returns hash of info from subreddit"
  [html-string]
  (let [res (string->resource html-string)]
    (-> {}
        (assoc :sub-count (get-sub-count res))
        (assoc :name (get-name res))
        (assoc :url (get-link res)))))