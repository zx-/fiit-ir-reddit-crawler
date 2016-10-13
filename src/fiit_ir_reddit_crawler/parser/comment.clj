(ns fiit-ir-reddit-crawler.parser.comment
  (:require [net.cgrand.enlive-html :as html]
            [fiit-ir-reddit-crawler.parser.common :as c]))

(defn get-title-area
  [resource]
  (html/select resource [:#siteTable :.thing :.entry]))

(defn get-domain
  [resource]
  (c/get-content resource [:.domain :> :a]))

(defn get-link-flair
  [resource]
  (c/get-content resource [:.linkflairlabel]))

(defn get-title
  [title-res]
  (html/text
    (c/normalize-text
      (c/get-content title-res [:p.title :a.title]))))

(defn get-score
  [resource]
  (read-string (c/get-content resource [:.score.unvoted])))

(defn get-user-name
  [title-res]
  (c/get-content title-res [:.author]))

(defn get-user-link
  [title-res]
  (-> title-res
      (html/select [:.author])
      first
      :attrs
      :href))

(defn parse-comment
  "returns hash of info from subreddit"
  [html-string]
  (let [res (c/string->resource html-string)
        title-area (get-title-area res)]
    (-> {}
        (assoc :domain (get-domain title-area))
        (assoc :link-flair (get-link-flair title-area))
        (assoc :title (get-title title-area))
        (assoc :score (get-score res))
        (assoc :user-name (get-user-name title-area))
        (assoc :user-link (get-user-link title-area)))))