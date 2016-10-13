(ns fiit-ir-reddit-crawler.parser.comment-test
  (:require [clojure.test :refer :all]
            [fiit-ir-reddit-crawler.parser.comment :refer :all]
            [fiit-ir-reddit-crawler.parser.common :refer :all]))

(def html-geo (slurp "dev-resources/test-comment-geo.html"))
(def html-worldnews (slurp "dev-resources/test-comment-worldnews.html"))
(def html-gif (slurp "dev-resources/test-comment-gif.html"))

(def geo-attrs
  {:domain "self.science"
   :link-flair "Hurricane AMA"
   :title "Science AMA Series: Hi Reddit, I'm Phil Klotzbach, a research scientist in the Department of Atmospheric Science at Colorado State University. I'm here to talk about the 2016 Atlantic hurricane season specifically as well as any other hurricane/typhoon related questions you have. Ask me anything!"
   :score 984
   :user-name "AmGeophysicalU-AMA"
   :user-link "https://www.reddit.com/user/AmGeophysicalU-AMA"
   :user-flair "American Geophysical Union AMA Guest"
   :link "https://www.reddit.com/r/science/comments/579v6t/science_ama_series_hi_reddit_im_phil_klotzbach_a/"
   :subreddit "science"
   :date "2016-10-13T11:59:35+00:00"
   :user-text "I am Phil Klotzbach, a research scientist in the Department of Atmospheric Science at Colorado State University. I worked for over 15 years with the late Dr. Bill Gray, a renowned scientist who conducted groundbreaking studies in hurricane genesis, structure and intensity change as well as pioneering Atlantic basin seasonal hurricane prediction. While our Tropical Meteorology Project is best known among the general public for the seasonal hurricane predictions, I conduct research on a variety of hurricane-related topics including shorter-term prediction as well as potential future changes in tropical cyclone activity driven both by natural variability as well as anthropogenic causes. I maintain a very active presence on social media through my Twitter feed (@philklotzbach) where I provide frequent updates on current global tropical cyclone activity and compare them with historical statistics. I also maintain global real-time hurricane statistics. In cooperation with the Barcelona Supercomputing Centre, I helped create a repository of all publicly-available seasonal hurricane forecasts for the Atlantic basin from various government agencies, universities and private forecasting companies. Currently, I am working on a variety of research projects, including the generation of an updated global tropical cyclone climatology as well as a paper on the life and legacy of Dr. Gray. I am also closely monitoring the potential shift away from the active Atlantic hurricane era that we have been in since 1995. I was lead author on a paper last year that raised the question that we might be moving out of the active era for Atlantic hurricanes. I look forward to chatting with you about all things hurricane! be back at noon EST (9 am PST, 5 pm UTC) to answer your questions, ask me anything!"
   :num-of-comments 86
   :media false
   :target nil})

(def worldnews-attrs
  {:domain "theguardian.com"
   :link-flair nil
   :title "Bob Dylan has been awarded the 2016 Nobel Prize in Literature"
   :score 407
   :user-name "Hugpandas"
   :user-link "https://www.reddit.com/user/Hugpandas"
   :user-flair nil
   :link "https://www.reddit.com/r/worldnews/comments/579q17/bob_dylan_has_been_awarded_the_2016_nobel_prize/"
   :subreddit "worldnews"
   :date "2016-10-13T11:18:01+00:00"
   :user-text nil
   :num-of-comments 139
   :target "http://www.theguardian.com/books/live/2016/oct/13/nobel-prize-in-literature-2016-liveblog"
   :media false})

(def gif-attrs
  {:domain "i.imgur.com"
   :link-flair nil
   :title "Happiness"
   :score 7811
   :user-name "Ms_Virtualizza"
   :user-link "https://www.reddit.com/user/Ms_Virtualizza"
   :user-flair nil
   :link "https://www.reddit.com/r/gifs/comments/579km2/happiness/"
   :subreddit "gifs"
   :date "2016-10-13T10:28:11+00:00"
   :user-text nil
   :num-of-comments 782
   :target "http://i.imgur.com/ik3lIft.gifv"
   :media true})

(deftest get-domain-test
  (let [geo-res (get-title-area (string->resource html-geo))
        wrldn-res (get-title-area (string->resource html-worldnews))
        gif-res (get-title-area (string->resource html-gif))]
    (is (= (:domain geo-attrs)
           (get-domain geo-res)))
    (is (= (:domain worldnews-attrs)
           (get-domain wrldn-res)))
    (is (= (:domain gif-attrs)
           (get-domain gif-res)))))

(deftest get-link-flair-test
  (let [geo-res (get-title-area (string->resource html-geo))
        wrldn-res (get-title-area (string->resource html-worldnews))
        gif-res (get-title-area (string->resource html-gif))]
    (is (= (:link-flair geo-attrs)
           (get-link-flair geo-res)))
    (is (= (:link-flair worldnews-attrs)
           (get-link-flair wrldn-res)))
    (is (= (:link-flair gif-attrs)
           (get-link-flair gif-res)))))

(deftest get-title-test
  (let [geo-res (get-title-area (string->resource html-geo))
        wrldn-res (get-title-area (string->resource html-worldnews))
        gif-res (get-title-area (string->resource html-gif))]
    (is (= (:title geo-attrs)
           (get-title geo-res)))
    (is (= (:title worldnews-attrs)
           (get-title wrldn-res)))
    (is (= (:title gif-attrs)
           (get-title gif-res)))))

(deftest get-score-test
  (let [geo-res (string->resource html-geo)
        wrldn-res (string->resource html-worldnews)
        gif-res (string->resource html-gif)]
    (is (= (:score geo-attrs)
           (get-score geo-res)))
    (is (= (:score worldnews-attrs)
           (get-score wrldn-res)))
    (is (= (:score gif-attrs)
           (get-score gif-res)))))

(deftest get-user-name-test
  (let [geo-res (get-title-area (string->resource html-geo))
        wrldn-res (get-title-area (string->resource html-worldnews))
        gif-res (get-title-area (string->resource html-gif))]
    (is (= (:user-name geo-attrs)
           (get-user-name geo-res)))
    (is (= (:user-name worldnews-attrs)
           (get-user-name wrldn-res)))
    (is (= (:user-name gif-attrs)
           (get-user-name gif-res)))))

(deftest get-user-link-test
  (let [geo-res (get-title-area (string->resource html-geo))
        wrldn-res (get-title-area (string->resource html-worldnews))
        gif-res (get-title-area (string->resource html-gif))]
    (is (= (:user-link geo-attrs)
           (get-user-link geo-res)))
    (is (= (:user-link worldnews-attrs)
           (get-user-link wrldn-res)))
    (is (= (:user-link gif-attrs)
           (get-user-link gif-res)))))

(deftest get-link-test
  (let [geo-res (string->resource html-geo)
        wrldn-res (string->resource html-worldnews)
        gif-res (string->resource html-gif)]
    (is (= (:link geo-attrs)
           (get-link geo-res)))
    (is (= (:link worldnews-attrs)
           (get-link wrldn-res)))
    (is (= (:link gif-attrs)
           (get-link gif-res)))))

(deftest get-user-flair-test
  (let [geo-res (get-title-area (string->resource html-geo))
        wrldn-res (get-title-area (string->resource html-worldnews))
        gif-res (get-title-area (string->resource html-gif))]
    (is (= (:user-flair geo-attrs)
           (get-user-flair geo-res)))
    (is (= (:user-flair worldnews-attrs)
           (get-user-flair wrldn-res)))
    (is (= (:user-flair gif-attrs)
           (get-user-flair gif-res)))))

(deftest get-subreddit-test
  (let [geo-res (string->resource html-geo)
        wrldn-res (string->resource html-worldnews)
        gif-res (string->resource html-gif)]
    (is (= (:subreddit geo-attrs)
           (get-subreddit geo-res)))
    (is (= (:subreddit worldnews-attrs)
           (get-subreddit wrldn-res)))
    (is (= (:subreddit gif-attrs)
           (get-subreddit gif-res)))))

(deftest get-date-test
  (let [geo-res (get-title-area (string->resource html-geo))
        wrldn-res (get-title-area (string->resource html-worldnews))
        gif-res (get-title-area (string->resource html-gif))]
    (is (= (:date geo-attrs)
           (get-date geo-res)))
    (is (= (:date worldnews-attrs)
           (get-date wrldn-res)))
    (is (= (:date gif-attrs)
           (get-date gif-res)))))

(deftest get-user-text-test
  (let [geo-res (get-title-area (string->resource html-geo))
        wrldn-res (get-title-area (string->resource html-worldnews))
        gif-res (get-title-area (string->resource html-gif))]
    (is (= (:user-text geo-attrs)
           (get-user-text geo-res)))
    (is (= (:user-text worldnews-attrs)
           (get-user-text wrldn-res)))
    (is (= (:user-text gif-attrs)
           (get-user-text gif-res)))))

(deftest get-num-of-comments-test
  (let [geo-res (get-title-area (string->resource html-geo))
        wrldn-res (get-title-area (string->resource html-worldnews))
        gif-res (get-title-area (string->resource html-gif))]
    (is (= (:num-of-comments geo-attrs)
           (get-num-of-comments geo-res)))
    (is (= (:num-of-comments worldnews-attrs)
           (get-num-of-comments wrldn-res)))
    (is (= (:num-of-comments gif-attrs)
           (get-num-of-comments gif-res)))))

(deftest get-target-test
  (let [geo-res (get-title-area (string->resource html-geo))
        wrldn-res (get-title-area (string->resource html-worldnews))
        gif-res (get-title-area (string->resource html-gif))]
    (is (= (:target geo-attrs)
           (get-target geo-res)))
    (is (= (:target worldnews-attrs)
           (get-target wrldn-res)))
    (is (= (:target gif-attrs)
           (get-target gif-res)))))

(deftest is-media?-test
  (let [geo-res (get-title-area (string->resource html-geo))
        wrldn-res (get-title-area (string->resource html-worldnews))
        gif-res (get-title-area (string->resource html-gif))]
    (is (= (:media geo-attrs)
           (is-media? geo-res)))
    (is (= (:media worldnews-attrs)
           (is-media? wrldn-res)))
    (is (= (:media gif-attrs)
           (is-media? gif-res)))))



(deftest parse-comment-test
  (is (= geo-attrs (parse-comment html-geo)))
  (is (= worldnews-attrs (parse-comment html-worldnews)))
  (is (= gif-attrs (parse-comment html-gif))))