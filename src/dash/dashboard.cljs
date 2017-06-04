(ns dash.dashboard
  (:require [dash.html :as h]))

(defn head [config]
  [:head
    [:title (:page-title config)]
    [:meta {:name "viewport"
            :content "width=device-width, initial-scale=1"}]
    [:link {:rel "stylesheet"
            :href "css/w3.css"}]
    [:link {:rel "stylesheet"
            :href "css/style.css"}]])


(defn dash-item [{:keys [url img title desc]}]
  [:div {:class "w3-card-2 w3-hover-shadow w3-quarter w3-margin-top w3-margin-right card"}
    [:a {:href url :style "text-decoration: none;"}
      [:img {:class "w3-border-bottom" :src img :style "width: 100%" :alt title}]
      [:div {:class "w3-container"}
        [:h4 title]
        [:p desc]]]])


(defn body [config]
  [:body
    [:div {:class "w3-container"}
       [:h4 {:class "w3-border-bottom"} (:page-title config)]]
    (apply conj [:div {:class "w3-container"}]
                (map dash-item (:dash-items config)))])


(defn dashboard-page [config]
  (h/html->string
    [:html
      (head config)
      (body config)]))
