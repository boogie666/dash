(ns dash.html
  (:require [clojure.spec.alpha :as s]
            [clojure.string :as string])
  (:require-macros [cljs.spec.alpha :as s]))

(def non-closing-elements
  #{:area :base :br :col :command
    :embed :hr :img :input :link
    :meta :keygen :param :source
    :track :wbr})

(s/def ::params (s/map-of keyword? any?))

(s/def ::element
  (s/cat :name (s/and keyword? #(not (non-closing-elements %)))
         :args (s/? ::params)
         :body (s/* ::node)))

(s/def ::void-element
  (s/cat :name non-closing-elements
         :args (s/? ::params)))

(s/def ::node
  (s/or :text #(or (string? %) (number? %))
        :void-element ::void-element
        :element ::element))

(s/def ::style
  (s/map-of keyword? string?))

(declare stringify-html)

(defn- stringify-params [params]
  (let [kvs (map #(str (name (first %)) "=\"" (second %) "\"" ) params)]
    (if (seq kvs)
      (str " " (string/join " " kvs))
      nil)))

(defn- stringify-element [data]
  (let [tag (name (:name data))
        params (:args data)
        content (:body data)]
    (if (nil? content)
      (str "<" tag (stringify-params params) "/>")
      (let [children (map stringify-html content)]
        (str "<" tag (stringify-params params) ">"
            (string/join "" children)
            "</" tag ">")))))

(defn- stringify-void-element [data]
  (let [tag (name (:name data))
        params (:args data)]
    (str "<" tag (stringify-params params) ">")))


(defn- stringify-html [html-ast]
  (let [[type data] html-ast]
    (case type
      :text (str data)
      :void-element (stringify-void-element data)
      :element (stringify-element data))))


(defn html->string [html]
  (let [parsed-html (s/conform ::node html)]
    (if (= ::s/invalid parsed-html)
      (throw (js/Error. (s/explain-str ::node html)))
      (str "<!DOCTYPE html>\n" (stringify-html parsed-html)))))
