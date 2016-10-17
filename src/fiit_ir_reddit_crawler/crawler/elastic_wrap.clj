(ns fiit-ir-reddit-crawler.crawler.elastic-wrap
  (:require [clojurewerkz.elastisch.rest  :as esr]
            [clojurewerkz.elastisch.rest.bulk :as bulk]
            [clojurewerkz.elastisch.query :as q]
            [clojurewerkz.elastisch.rest.index :as esi]
            [clojurewerkz.elastisch.rest.document :as esd]))

(def server "http://192.168.99.100:9200")

(def ^:dynamic connection (esr/connect server))

(defn push-links
  "Take seq of link documents and index them"
  [links]
  (let [insert-ops (bulk/bulk-index links)
        response (bulk/bulk-with-index-and-type connection
                                                "links"
                                                "link"
                                                insert-ops {:refresh true})
        docs (->> response :items (map :create))]

    docs))

(defn get-links
  []
  (-> (esd/search connection "links" "link"
                  :query (q/term :visited false))
      :hits
      :hits))


;GET /links/_search
;{
; "from" : 0, "size" : 10,
;"sort" : [{ "timestamp" : {"order" : "desc"}}],
;"query": { "term" : {"visited" : false}}
;}
;}

(defn get-unvisited-links
  [n]
  (-> (esd/search connection "links" "link"
                  :query {:term {"visited"  false}}
                                     :sort [{ :timestamp {:order :desc}}]
                                     :from 0
                                     :size n)
      :hits
      :hits))

(defn set-visited
  [link]
  (esd/update-with-partial-doc connection "links" "link"
                               (:_id link)
                               {:visited true}))

(defn index-post
  [doc]
  (esd/create connection "posts" "post" doc))