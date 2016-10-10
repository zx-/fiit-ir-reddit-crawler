(ns fiit-ir-reddit-crawler.core
  (:require [clj-http.client :as client]))

(def subbredit-root-xml "http://reddit-sitemaps.s3-website-us-east-1.amazonaws.com/subreddit-sitemaps.xml")
(def threads-root "http://reddit-sitemaps.s3-website-us-east-1.amazonaws.com/comment-page-sitemaps.xml")
(def agent-name "clojure:fiit-ir-reddit-crawler:0.0.1 (by /u/zx-roboc)")
(def reddit "http://www.reddit.com")

(defn api-action [url method opts path]
    (client/request
      (merge {:method method :url (str url path)} opts)))

(def rdt-get (partial api-action reddit :get {"User-Agent" agent-name}))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
