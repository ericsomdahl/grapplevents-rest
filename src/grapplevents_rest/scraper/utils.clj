(ns grapplevents-rest.scraper.utils
  (:require [net.cgrand.enlive-html :as html]
            [clojure.string :as st]))

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

(defn not-blank?
  "Complement of blank?"
  [c]
  ((complement st/blank?) c))


(defn- resolve-string-month
  "Convert randomly-formatted string values into a month, ranging from 1-12.  Try to 
  handle typos as well"
  [in-month]
  (let [m (-> (st/lower-case in-month)
              (st/trim-newline)
              (st/replace #"\s" ""))]
    (cond
      (not-blank? (re-find #"ja" m)) 1
      (not-blank? (or (re-find #"f" m)
                      (re-find #"uary" m)
                      (re-find #"urary" m))) 2
      (not-blank? (re-find #"mar" m)) 3
      (not-blank? (re-find #"ap" m)) 4
      (not-blank? (re-find #"may" m)) 5
      (not-blank? (or (re-find #"jun" m)
                      (re-find #"une" m))) 6
      (not-blank? (or (re-find #"jul" m)
                      (re-find #"uly" m))) 7
      (not-blank? (re-find #"au" m)) 8
      (not-blank? (or (re-find #"sep" m)
                      (re-find #"temb" m))) 9
      (not-blank? (or (re-find #"oc" m)
                      (re-find #"tob" m))) 10
      (not-blank? (or (re-find #"no" m)
                      (re-find #"vemb" m))) 11
      (not-blank? (or (re-find #"d" m)
                      (re-find #"cemb" m))) 12

      :else nil
      )
    )
  )


(defn resolve-month
  "Normalize the random formatting of month strings to a single standard"
  [in-month]
  (cond
    (number? in-month) in-month
    (string? in-month) (resolve-string-month in-month)
    :else nil))

