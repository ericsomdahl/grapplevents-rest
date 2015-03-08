(ns grapplevents-rest.scraper.usa-judo
  (:require [net.cgrand.enlive-html :as html]
            [clojure.core.memoize :as memo]
            [grapplevents-rest.scraper.utils :as utils]))

;base url for USA Judo published events
(def base-url "http://www.teamusa.org/USA-Judo/Events")

;the pages are structured to return 10 results per page
(def results-per-page 10)

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

(defn get-all-events
  "Get lazy seq of events harvested from all of the populated pages"
  ([]
   (get-all-events 0))
  ([c]
   (let [pg (inc (quot c results-per-page))
         idx (mod c results-per-page)
         l (cached-page-events pg)]
      (if (< idx (count l)) ;The events come in X blocks per page so decompose a bit
          (cons (nth l idx)
                (lazy-seq (get-all-events (inc c))))
          nil))))





