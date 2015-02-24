(ns grapplevents-rest.scraper.usa-judo
  (:require [net.cgrand.enlive-html :as html]
            [clojure.core.memoize :as memo]
            [grapplevents-rest.scraper.utils :as utils]))

;base url for USA Judo published events
(def base-url "http://www.teamusa.org/USA-Judo/Events")

(defn url-by-page
  "Construct a URL for an single page of results"
  [page]
  (str base-url "?pg=" page))

(defn get-page-events
  "Return a list of events found on a given page, a map with enlive tags"
  [p]
  (-> (url-by-page p)
      (utils/fetch-url)
      (html/select [:div.list-item.clearfix])))

;memoize this to be good scraping citizens.  Cache for 60 seconds
(def cached-page-events
  (memo/ttl get-page-events :ttl/threshold 60000))

(def event-seq
  (for [x (range 1 15)
        :when (utils/not-empty? (cached-page-events x))]
    x))

(defn get-all-events
  "Get events harvested from all of the populated pages"
  []
  (mapcat cached-page-events event-seq))






