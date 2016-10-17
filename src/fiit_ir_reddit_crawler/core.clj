(ns fiit-ir-reddit-crawler.core
  (:require [clj-http.client :as client]
            [fiit-ir-reddit-crawler.secret :as secret])
  (:gen-class))

(def ^:dynamic oauth-token nil)
(def ^:dynamic rate-used 0)
(def ^:dynamic rate-remaining 5000)
(def ^:dynamic rate-reset 5000)

(def subbredit-root-xml "http://reddit-sitemaps.s3-website-us-east-1.amazonaws.com/subreddit-sitemaps.xml")
(def comments-root "http://reddit-sitemaps.s3-website-us-east-1.amazonaws.com/comment-page-sitemaps.xml")
(def agent-name "fiit-ir-reddit-crawler:0.0.2 (by /u/zx-roboc)")
(def reddit "http://www.reddit.com")
(def oauth-url "https://oauth.reddit.com")

(defn print-info
  []
  (println "rate-used " rate-used)
  (println "rate-remaining " rate-remaining)
  (println "rate-reset" rate-reset))

(defn parse-token-response
  [resp-json]
  (let [token (:access_token resp-json)]
    (alter-var-root #'oauth-token (fn [old new] new) token)))

(defn refresh-token!
  []
  (parse-token-response
    (:body (client/post secret/token-auth-url
                        {:basic-auth secret/login-string
                         :throw-entire-message? true
                         :debug false
                         :headers {"User-Agent" agent-name}
                         :as :json}))))

(defn set-rates!
  "Set limits and rates and returns response"
  [response]
  (let [headers (:headers response)
        used (read-string (get headers "x-ratelimit-used"))
        rem (read-string (get headers "x-ratelimit-remaining"))
        reset (read-string (get headers "x-ratelimit-reset"))]
    (alter-var-root #'rate-used       (fn [old new] new) used)
    (alter-var-root #'rate-remaining  (fn [old new] new) rem)
    (alter-var-root #'rate-reset      (fn [old new] new) reset))
  response)

(defn query
  "Makes get query on oauth url"
  [url]
  (client/get (str oauth-url url)
               {:headers {"User-Agent" agent-name}
                :oauth-token oauth-token
                :as :json}))

(defn query-rate!
  "Makes get query on oauth url and calls set-rates! on response"
  [url]
  (set-rates! (query url)))