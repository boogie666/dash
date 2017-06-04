(ns dash.core
  (:require [cljs.nodejs :as nodejs]
            [cljs.reader :as r]
            [dash.dashboard :as d]))

(nodejs/enable-util-print!)

(def express (js/require "express"))
(def fs (js/require "fs"))
(def path (js/require "path"))

(def config (r/read-string (.toString (.readFileSync fs (last (.-argv js/process))))))


(defn -main []
  (let [app (express)]
    (.use app (.static express (:images-folder config)))
    (.use app (.static express (.join path (js* "__dirname") "public")))
    (.get app "/" (fn [_ resp] (.send resp (d/dashboard-page config))))
    (.listen app (:port config) #(println (str "Listening on " (:port config))))))

(set! *main-cli-fn* -main)
