(ns dash.core
  (:require [cljs.nodejs :as nodejs]
            [cljs.reader :as r]
            [dash.dashboard :as d]
            [dash.config :as c])
  (:require-macros [cljs.spec.alpha :as s]))

(nodejs/enable-util-print!)

(def express (js/require "express"))
(def fs (js/require "fs"))
(def path (js/require "path"))


(def args (.-argv js/process))
(when (<= (count args) 2)
  (throw (js/Error. "Missing config.edn")))

(def config (r/read-string (.toString (.readFileSync fs (last (.-argv js/process))))))


(when-not (c/valid? config)
  (throw (js/Error. (str "Configuration is invalid\n\n"
                         (c/validation-error-message config)))))



(defn -main []
  (let [app (express)]
    (.use app (.static express (:images-folder config)))
    (.use app (.static express (.join path (js* "__dirname") "public")))
    (.get app "/" (fn [_ resp] (.send resp (d/dashboard-page (c/parse config)))))
    (.listen app (:port config) #(println (str "Listening on " (:port config))))))

(set! *main-cli-fn* -main)
