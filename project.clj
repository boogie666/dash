(defproject dash "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :min-lein-version "2.5.3"

  :dependencies [[org.clojure/clojure "1.9.0-alpha15"]
                 [org.clojure/clojurescript "1.9.521"]
                 [figwheel-sidecar "0.5.10"]]
  :plugins [[lein-cljsbuild "1.1.6"]
            [lein-figwheel "0.5.10"]
            [lein-ancient "0.6.10"]]
  :source-paths ["src"]
  :clean-targets ["server.js" "target"]
  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src"]
                        :figwheel true
                        :compiler {:main dash.core
                                   :output-to "target/server_dev/dash.js"
                                   :output-dir "target/server_dev"
                                   :target :nodejs
                                   :optimizations :none
                                   :source-map true}}
                       {:id "prod"
                        :source-paths ["src"]
                        :resource-path ["node_modules"]
                        :compiler {:output-to "server.js"
                                   :output-dir "target/server_prod"
                                   :target :nodejs
                                   :optimizations :simple}}]})
