(ns fiit-ir-reddit-crawler.parser.comment-api
  (:require [clojure.data.json :as json]
            [fiit-ir-reddit-crawler.parser.subreddit-api :as s-api]))

(defn get-subreddit
  [subreddit-name]
  (-> (s-api/m-reddit-info
        (str "/r/" subreddit-name "/"))
      (assoc :name subreddit-name)))

(declare parse-comment)

(defn process-children
  [children depth]
  (if (nil? children)
    nil
    (reduce (fn [s ch]
              (parse-comment s ch depth))
            '()
            children)))

(defn parse-comment
  [c-seq comment depth]
  (let [data (:data comment)
        children (get-in data [:replies :data :children])]
    (-> c-seq
        (concat (process-children children (inc depth)))
        (conj {:author (:author data)
               :depth depth
               :score (:score data)
               :text (:body data)}))))

(defn get-comments
  [comments]
  (process-children (get-in comments [:data :children]) 0))

(defn parse-post
  "returns comment post document, takes response body"
  [res-json]
  (let [head (get-in (first res-json)
                     [:data :children 0 :data])
        comments (nth res-json 1)]
    (-> {}
        (assoc :domain (:domain head))
        (assoc :link-flair (:link_flair_text head))
        (assoc :title (:title head))
        (assoc :score (:score head))
        (assoc :user-name (:author head))
        ;(assoc :user-link (get-user-link title-area))
        (assoc :user-flair (:author_flair_text head))
        (assoc :link (:permalink head))
        (assoc :subreddit (get-subreddit (:subreddit head)))
        (assoc :date (long (:created head)))
        (assoc :user-text (:selftext head))
        (assoc :num-of-comments (:num_comments head))
        (assoc :target (:url head))
        (assoc :nsfw (:over_18 head))
        (assoc :comments (get-comments comments))
        ;(assoc :media (is-media? title-area))
        )))

