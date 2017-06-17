(ns dash.config
  (:require [clojure.spec.alpha :as s])
  (:require-macros [cljs.spec.alpha :as s]))

(s/def ::port number?)

(s/def ::page-title string?)

(s/def ::images-folder string?)

(s/def ::single-dash-item
  (s/keys :req-un [::url ::title ::img]
          :opt-un [::desc]))

(s/def ::items
  (s/coll-of ::single-dash-item))

(s/def ::dash-group
  (s/keys :req-un [::group ::items]))

(s/def ::dash-item
  (s/or :item ::single-dash-item
        :group ::dash-group))

(s/def ::dash-items
  (s/coll-of ::dash-item))

(s/def ::config
  (s/keys :req-un [::port ::page-title ::images-folder ::dash-items]))

(defn valid? [config]
  (s/valid? ::config config))

(defn parse [config]
  (s/conform ::config config))

(defn validation-error-message [config]
  (s/explain-str ::config config))
