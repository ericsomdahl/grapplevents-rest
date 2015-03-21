(ns grapplevents-rest.scraper.usa-judo-test
  (:require [clojure.test :refer :all]
            [net.cgrand.enlive-html :as html]
            [grapplevents-rest.scraper.usa-judo :as usa]
            [grapplevents-rest.scraper.utils :as utils]))

;mock a sequence of 12 events, ten in the first request and 2 in the second
(def get-files (fn [url]
                    (let [idx (Integer/parseInt (re-find #"\d$" url))]
                      (cond
                        (= idx 1) (utils/fetch-file "test/grapplevents_rest/scraper/10-results.txt")
                        (= idx 2) (utils/fetch-file "test/grapplevents_rest/scraper/02-results.txt")
                        :else (utils/fetch-file "test/grapplevents_rest/scraper/00-results.txt")
                        ))))

(deftest usa-judo-scraper
  (testing "url-by-page"
    (let [test-url (usa/url-by-page 1)]
      ;the url contains the base-url
      (is (re-find (re-pattern usa/base-url) test-url))))

  (testing "get-page-events"
    ;mock the web request
    (with-redefs [utils/fetch-url
                    (fn [_]
                      (utils/fetch-file "test/grapplevents_rest/scraper/10-results.txt" ))]
      (let [events (usa/get-page-events 1)]
        (is (seq events))
        (is (= 10 (count events))))))

  (testing "get-all-events"
    (with-redefs [utils/fetch-url get-files]
      (let [all-events (usa/get-all-events)]
        (is (seq all-events))
        (is (= 12 (count all-events)))
        (is (= 12 (count (take 23 all-events)))))))

  (testing "add-event-basics"
    (let [actual (usa/add-event-basics {} {:sport "curling"})]
      (is (= true (contains? (actual :sport) "judo")))
      (is (= 1 (count (actual :sport))))
      (is (= "USA Judo" (actual :org)))
      (is (= "http://www.teamusa.org/USA-Judo" (actual :org-url)))
      ))

  )
