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

(defn get-content
  "Returns content"
  [resource selector]
  (-> (html/select resource selector)
      first
      :content
      first))

(defn normalize-text
  "replaces \n with space and then merges spaces"
  [string]
  (-> string
      clojure.string/trim
      (clojure.string/replace #"\n" " ")
      (clojure.string/replace #"\s+" " ")))