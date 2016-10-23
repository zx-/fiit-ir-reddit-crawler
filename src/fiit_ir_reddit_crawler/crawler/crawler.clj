(ns fiit-ir-reddit-crawler.crawler.crawler
  (:require [fiit-ir-reddit-crawler.core :as core]
            [fiit-ir-reddit-crawler.crawler.elastic-wrap :as el]
            [fiit-ir-reddit-crawler.parser.comment-api :as comm]
            [slingshot.slingshot :as sling]))
(defn sleep-sec
  [seconds-to-sleep]
  (do (println "waiting " seconds-to-sleep)
      (Thread/sleep (* 1000 seconds-to-sleep))))

(defn process-link!
  "takes link, queries api, updates link stack"
  [link]
  (let [link-data (:_source link)]
    (sling/try+
      (let [post-q-res (core/query-rate! (:url link-data))
            body (:body post-q-res)
            com-doc (comm/parse-post body)]
          (el/index-post com-doc))
      (catch [:status 404] {:keys [request-time headers body]}
        (println "NOT Found 404" request-time headers body))
      (catch [:status 403] {:keys [request-time headers body]}
        (println "403" request-time headers)))))

    ;;(el/set-visited link)
    ;(println "processed : " (:url link-data))


(defn process-links!
  "takes links and uses process-link! in parallel"
  [links]
  (doall (pmap process-link! links)))

(defn crawl!
  "takes number of threads to use to crawl and number of posts to parse"
  [threads n start]
  (core/refresh-token!)
  (loop [gather n
         page-start start]
    (do (core/print-info)
        (println "remaining " gather)
        (let [to-process (el/get-unvisited-links threads page-start)]
          (if (> (count to-process)
                 core/rate-remaining)
            (do (sleep-sec (+ core/rate-reset 10))
                (core/refresh-token!)))
          (process-links! to-process)
          (let [new-g (- gather threads)]
            (if (> new-g 0)
              (recur new-g
                     (+ page-start threads))
              (println "finished")))))))

;done 1000

(crawl! 5 36000 130614)


