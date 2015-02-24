(ns grapplevents-rest.scraper.utils
  (:require [net.cgrand.enlive-html :as html]))

(defn fetch-url
  "Grab the contents of the url specified"
  [url]
  (html/html-resource  (java.net.URL. url)))

(defn not-empty?
  "Complement of empty?"
  [c]
  ((complement empty?) c))
