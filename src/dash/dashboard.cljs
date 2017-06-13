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
  [:div {:class "w3-card-2 w3-hover-shadow w3-quarter w3-margin-right card"}
        [:a {:href url :style "text-decoration: none;"}
          [:img {:class "w3-border-bottom" :src img :style "width: 100%" :alt title}]
          [:div {:class "w3-container"}
            [:h4 title]
            (if desc
              [:p desc]
              [:p "&nbsp;"])]]])

(defn group-item [{:keys [group items]}]
  [:div
    [:h5 group]
    (into [:div {:class "w3-container"}]
          (map dash-item items))])


(defn extract-first [type]
  (comp (filter #(= type (first %)))
        (map second)))

(defn body [config]
  (let [dash-items (:dash-items config)
        groups (into [] (extract-first :group) dash-items)
        misc {:group "Misc." :items (into [] (extract-first :item) dash-items)}]
    [:body
      [:div {:class "w3-container"}
         [:h4 {:class "w3-border-bottom"} (:page-title config)]]
      (into [:div {:class "w3-container"}]
            (map group-item
                (conj groups (when (seq (:items misc)) misc))))]))


(defn dashboard-page [config]
  (h/html->string
    [:html
      (head config)
      (body config)]))
