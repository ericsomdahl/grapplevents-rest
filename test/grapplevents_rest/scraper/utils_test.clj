(ns grapplevents-rest.scraper.utils-test
  (:require [clojure.test :refer :all]
           [grapplevents-rest.scraper.utils :as utils]))

(deftest scraper-utils-not-empty
  (testing "not-empty?"
    (let [x nil
          y '()
          z [1 2 3]]
      (is (= false (utils/not-empty? x)))
      (is (= false (utils/not-empty? y)))
      (is (= true (utils/not-empty? z)))
      )))
