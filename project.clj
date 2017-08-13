(defproject dash "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :min-lein-version "2.5.3"

  :dependencies [[org.clojure/clojure "1.9.0-alpha16"]
                 [org.clojure/clojurescript "1.9.854"]
                 [org.clojure/spec.alpha "0.1.123"]]
  :plugins [[lein-cljsbuild "1.1.6"]
            [lein-figwheel "0.5.10"]
            [lein-ancient "0.6.10"]]
  :source-paths ["src"]
  :clean-targets ["server.js" "target"]
  :profiles {:dev {:dependencies [[figwheel-sidecar "0.5.12"]
                                  [com.cemerick/piggieback "0.2.2"]
                                  [org.clojure/tools.nrepl  "0.2.13"]
                                  [org.clojure/test.check "0.9.0"]]

                   :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}}


  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src"]
                        :figwheel true
                        :compiler {:main dash.core
                                   :output-to "target/server_dev/dash.js"
                                   :output-dir "target/server_dev"
                                   :target :nodejs
                                   :optimizations :none
                                   :language-in :ecmascript5-strict
                                   :language-out :ecmascript5-strict
                                   :source-map true}}
                       {:id "prod"
                        :source-paths ["src"]
                        :compiler {:output-to "server.js"
                                   :output-dir "target/server_prod"
                                   :target :nodejs
                                   :language-in :ecmascript5-strict
                                   :language-out :ecmascript5-strict
                                   :optimizations :simple}}]})
