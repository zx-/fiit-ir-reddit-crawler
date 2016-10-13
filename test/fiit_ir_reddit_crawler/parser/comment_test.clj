(ns fiit-ir-reddit-crawler.parser.comment-test
  (:require [clojure.test :refer :all]
            [fiit-ir-reddit-crawler.parser.comment :refer :all]
            [fiit-ir-reddit-crawler.parser.common :refer :all]))

(def test-comment-geo (slurp "dev-resources/test-comment-geo.html"))
(def geo-attrs {:domain "self.science"
                :link-flair "Hurricane AMA"
                :title "Science AMA Series: Hi Reddit, I'm Phil Klotzbach, a research scientist in the Department of Atmospheric Science at Colorado State University. I'm here to talk about the 2016 Atlantic hurricane season specifically as well as any other hurricane/typhoon related questions you have. Ask me anything!"
                :score 984
                :user-name "AmGeophysicalU-AMA"
                :user-address "https://www.reddit.com/user/AmGeophysicalU-AMA"
                :user-flair "American Geophysical Union AMA Guest"
                :link "/r/science/comments/579v6t/science_ama_series_hi_reddit_im_phil_klotzbach_a/"
                :subreddit "science"
                :date "2016-10-13T11:59:35+00:00"
                :user-text "I am Phil Klotzbach, a research scientist in the Department of Atmospheric Science at Colorado State University. I worked for over 15 years with the late Dr. Bill Gray, a renowned scientist who conducted groundbreaking studies in hurricane genesis, structure and intensity change as well as pioneering Atlantic basin seasonal hurricane prediction. While our Tropical Meteorology Project is best known among the general public for the seasonal hurricane predictions, I conduct research on a variety of hurricane-related topics including shorter-term prediction as well as potential future changes in tropical cyclone activity driven both by natural variability as well as anthropogenic causes. I maintain a very active presence on social media through my Twitter feed (@philklotzbach) where I provide frequent updates on current global tropical cyclone activity and compare them with historical statistics. I also maintain global real-time hurricane statistics. In cooperation with the Barcelona Supercomputing Centre, I helped create a repository of all publicly-available seasonal hurricane forecasts for the Atlantic basin from various government agencies, universities and private forecasting companies.\nCurrently, I am working on a variety of research projects, including the generation of an updated global tropical cyclone climatology as well as a paper on the life and legacy of Dr. Gray. I am also closely monitoring the potential shift away from the active Atlantic hurricane era that we have been in since 1995. I was lead author on a paper last year that raised the question that we might be moving out of the active era for Atlantic hurricanes.\nI look forward to chatting with you about all things hurricane!\nI’ll be back at noon EST (9 am PST, 5 pm UTC) to answer your questions, ask me anything!"
                :num-of-comments 86})