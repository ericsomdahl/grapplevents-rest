(ns grapplevents-rest.scraper.utils
  (:require [net.cgrand.enlive-html :as html]))

(defn fetch-url
  "Grab the contents of the url specified"
  [url]
  (html/html-resource  (java.net.URL. url)))

(defn fetch-file
  "Grab the contents of the file specified"
  [filename]
  (html/html-resource  (java.io.File. filename)))

(defn not-empty?
  "Complement of empty?"
  [c]
  ((complement empty?) c))
