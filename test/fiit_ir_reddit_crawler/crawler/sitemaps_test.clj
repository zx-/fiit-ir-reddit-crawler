(ns fiit-ir-reddit-crawler.crawler.sitemaps_test
  (:require [clojure.test :refer :all]
            [fiit-ir-reddit-crawler.crawler.sitemaps :refer :all]))


(def xml-links (slurp "dev-resources/xml-comments.html"))

(def links '("https://www.reddit.com/r/RocketLeagueExchange/comments/57e3n5/ps4_h_purple_suns_certified_acrobat_loopers/"
              "https://www.reddit.com/r/RocketLeagueExchange/comments/57dvps/xbox_h_heatwave_purple_loopers_breakout_type_s/"
              "https://www.reddit.com/r/RocketLeagueExchange/comments/579vuv/xboxhparallax_2_exotic_wheels_xdevil_mk2_and_c3/"
              "https://www.reddit.com/r/RocketLeagueExchange/comments/579vuw/ps4_h_certified_slipstream_for_centers_w_keys_and/"))

(deftest get-links-test
  (is (= links
         (get-links xml-links))))