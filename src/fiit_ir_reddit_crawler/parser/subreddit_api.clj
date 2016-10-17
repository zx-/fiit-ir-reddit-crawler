(ns fiit-ir-reddit-crawler.parser.subreddit-api
  (:require [clojure.data.json :as json]
            [fiit-ir-reddit-crawler.core :as core]))


(defn parse-subreddit
  "returns hash of info from subreddit"
  [subreddit]
  (let [res-data (:data subreddit)]
    (-> {}
        (assoc :sub-count (:subscribers res-data))
        (assoc :title (:title res-data))
        (assoc :url (:url res-data))
        (assoc :nsfw (:over18 res-data)))))

(defn reddit-info
  "returns info for subreddit: '/r/subreddit/' does query"
  [reddit-url]
  (parse-subreddit
    (:body (core/query-rate! (str reddit-url "about")))))

(def m-reddit-info (memoize reddit-info))