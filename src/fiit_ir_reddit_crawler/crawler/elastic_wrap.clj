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