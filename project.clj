(defproject grapplevents-rest "0.1.0-SNAPSHOT"
  :description "REST services for grapplevents.com"
  :url "http://www.grapplevents.com"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.3.1"]
                 [org.clojure/core.memoize "0.5.6"]
                 [enlive-helper "0.1.0"]
                 [enlive "1.1.5"]
                 [ring/ring-core "1.3.2"]
                 [ring/ring-defaults "0.1.3"]
                 [ring/ring-jetty-adapter "1.3.2"]
                 [liberator "0.12.2"] ]
  :plugins [[lein-ring "0.9.1"]]
  :ring {:handler grapplevents-rest.handler/handler
         :nrepl  {:start? true
                  :port 9998}}

  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring-mock "0.1.5"]
                                  [midje "1.6.3"]]
                   :plugins [[lein-midje "3.1.3"]]}})


