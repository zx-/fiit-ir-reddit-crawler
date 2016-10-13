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
  (c/normalize-text
    (html/text
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

(defn get-user-flair
  [title-res]
  (c/get-content title-res [:.flair]))

; <link rel="canonical"
(defn get-link
  [resource]
  (-> resource
      (html/select [(html/attr= :rel "canonical")])
      first
      :attrs
      :href))

(defn get-subreddit
  [resource]
  (-> resource
      (html/select [:#siteTable :> :.thing])
      first
      :attrs
      :data-subreddit))

(defn get-date
  [title-res]
  (-> title-res
      (html/select [:time.live-timestamp])
      first
      :attrs
      :datetime))

(defn get-user-text
  [title-res]
  (if-let [e (first (html/select title-res [:form.usertext :> :.usertext-body]))]
    (-> e html/text c/normalize-text)))

(defn get-num-of-comments
  [title-res]
  (-> (c/get-content title-res [:.buttons :.first :.comments])
      (clojure.string/split #" ")
      (nth 0)
      read-string))

(defn get-target
  [title-res]
  (if-let [url (first (html/select title-res [:p.title :a.title.outbound]))]
    (:data-href-url (:attrs url))))

(defn is-media?
  [title-res]
  (if-let [media (first (html/select title-res [:.media-preview]))]
    true
    false))

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
        (assoc :user-link (get-user-link title-area))
        (assoc :user-flair (get-user-flair title-area))
        (assoc :link (get-link res))
        (assoc :subreddit (get-subreddit res))
        (assoc :date (get-date title-area))
        (assoc :user-text (get-user-text title-area))
        (assoc :num-of-comments (get-num-of-comments title-area))
        (assoc :target (get-target title-area))
        (assoc :media (is-media? title-area)))))