(ns fiit-ir-reddit-crawler.core
  (:require [clj-http.client :as client]
            [fiit-ir-reddit-crawler.secret :as secret]
            [clojure.data.json :as json]))

(def ^:dynamic oauth-token nil)

(def subbredit-root-xml "http://reddit-sitemaps.s3-website-us-east-1.amazonaws.com/subreddit-sitemaps.xml")
(def comments-root "http://reddit-sitemaps.s3-website-us-east-1.amazonaws.com/comment-page-sitemaps.xml")
(def agent-name "fiit-ir-reddit-crawler:0.0.2 (by /u/zx-roboc)")
(def reddit "http://www.reddit.com")
(def oauth-url "https://oauth.reddit.com")


(defn parse-token-response
  [resp-json]
  (let [r (json/read-str resp-json)
        token (get r "access_token")]
    (alter-var-root #'oauth-token (fn [old new] new) token)))

(defn refresh-token!
  []
  (parse-token-response
    (:body (client/post secret/token-auth-url
                        {:basic-auth secret/token-auth-url
                         :throw-entire-message? true
                         :debug false
                         :headers {"User-Agent" agent-name}}))))

