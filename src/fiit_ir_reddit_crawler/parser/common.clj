(ns fiit-ir-reddit-crawler.parser.common
  (:require [net.cgrand.enlive-html :as html]
            [clojure.java.io :as io]))

(defn string->resource
  [string]
  "Creates html resource from given string"
  (html/html-resource (-> string
                          .getBytes
                          java.io.ByteArrayInputStream.
                          io/input-stream )))
