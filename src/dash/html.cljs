(ns dash.html
  (:require [clojure.spec.alpha :as s]
            [clojure.string :as string]
            [clojure.spec.gen.alpha :as gen])

  (:require-macros [cljs.spec.alpha :as s]
                   [cljs.spec.gen.alpha :as gen]))

(s/def ::params (s/map-of keyword? any?))

(s/def ::element
  (s/cat :name keyword?
         :args (s/? ::params)
         :body (s/* ::node)))

(s/def ::void-element
  (s/cat :name #{:area :base :br :col :command :embed
                 :hr :img :input :link :meta
                 :keygen :param :source :track :wbr}
         :args (s/? ::params)))


(defn maybe-text? [x]
  (or (string? x) (number? x) (nil? x)))

(s/def ::text-element
  (s/with-gen maybe-text?
    #(gen/fmap (fn [[what string number]]
                  (case what
                    0 nil
                    1 string
                    2 number))
        (gen/tuple (s/gen #{0 1 2}) (gen/string-alphanumeric) (gen/int)))))



(s/def ::node
  (s/or :text ::text-element
        :void-element ::void-element
        :element ::element))

(declare stringify-html)

(defn- stringify-params [params]
  (let [kvs (map #(str (name (first %)) "=\"" (second %) "\"") params)]
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

(defn- stringify-html [[type data]]
  (case type
    :text (str data)
    :void-element (stringify-void-element data)
    :element (stringify-element data)))

(s/fdef html->string
  :args (s/cat :node ::node)
  :ret string?)

(defn html->string
  [html]
  (let [parsed-html (s/conform ::node html)]
    (when (= parsed-html ::s/invalid)
      (throw (js/Error. (s/explain-str ::node html))))
    (str "<!DOCTYPE html>\n" (stringify-html parsed-html))))
