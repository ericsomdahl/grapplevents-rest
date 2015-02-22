(ns grapplevents-rest.scraper.usa-judo
  (:require [net.cgrand.enlive-html :as html]
            [grapplevents-rest.scraper.utils :as utils]))

;base url for published events
(def base-url "http://www.teamusa.org/USA-Judo/Events")

(defn url-by-page
  "Construct a URL for an single page of results"
  [p]
  (str base-url "?pg=" p))

(defn get-some
  []
  (-> (url-by-page 9)
      (utils/fetch-url)
      (html/select [:div.list-item.clearfix])
      (html/select [:div.num.event-date])
      )
  )
