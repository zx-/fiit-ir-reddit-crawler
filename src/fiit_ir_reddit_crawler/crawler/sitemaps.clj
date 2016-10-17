(ns fiit-ir-reddit-crawler.crawler.sitemaps
  (:require [clj-http.client :as client]
            [net.cgrand.enlive-html :as html]
            [fiit-ir-reddit-crawler.parser.common :as c]
            [clojure.string :as string]
            [fiit-ir-reddit-crawler.core :as core]
            [fiit-ir-reddit-crawler.crawler.elastic-wrap :as elastic]))

(def agent-name core/agent-name)
(def sitemaps core/comments-root)


(defn get-links
  "returns list of comment links from comment-map string"
  [map-string]
  (let [locations (html/select (c/string->resource map-string)
                               [:loc])]
    (map (fn [location] (clojure.string/trim (first (:content location))))
         locations)))

(defn link->el-link
  "Creates elastic link resource from link string"
  [link-string]
  {:url (string/replace link-string #"^https://www.reddit.com" "")
   :visited false
   :timestamp (System/currentTimeMillis)})

(defn links->el-links
  [link-seq]
  (map link->el-link link-seq))


(defn get-urls-from-map-site
  "extracts urls from xml reddit loc map"
  [site-url]
  (-> (client/get site-url
                  {:headers {"User-Agent" agent-name}})
      :body
      get-links))

(defn process-sitemap-urls
  [urls]
  (reduce (fn [count url]
            (println "processing " count)
            (println url)
            (-> url
                get-urls-from-map-site
                links->el-links
                elastic/push-links)
            (println "processing " count "finished")
            (Thread/sleep 3000)
            (inc count))
       0
       urls))


(def sitemap-urls (get-urls-from-map-site sitemaps))
(process-sitemap-urls (drop 5 (reverse sitemap-urls)))