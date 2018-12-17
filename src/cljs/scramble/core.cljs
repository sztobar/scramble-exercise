(ns scramble.core
  (:require [rum.core :as rum]
            [cljs.reader :as reader]))


(enable-console-print!)


(defonce app-state
  (atom {:source ""
         :destination ""
         :result nil
         :loading false
         :error nil}))


(defn targetValue [e]
  (.. e -target -value))


(defn get-form-value [form]
  (let [formData (js/FormData. form)]
    {:source (.get formData "source")
     :destination (.get formData "destination")}))


(defn set-scramble-response! [response]
  (swap!
   app-state
   assoc
   :source (:source response)
   :destination (:destination response)
   :result (:result response)
   :error (:error response)
   :loading false))


(defn set-scramble-error! [response]
  (swap!
   app-state
   assoc
   :source (:source response)
   :destination (:destination response)
   :result nil
   :error (:error response)
   :loading false))


(defn submit-form [e]
  (.preventDefault e)
  (let [form (.-target e)
        params (get-form-value form)]
    (swap! app-state assoc :loading true)
    (->
     (js/fetch
      "/api/scramble"
      #js {:method "post"
           :headers #js {"Content-Type" "application/edn"}
           :body (prn-str params)})
     (.then (fn [response]
              (.text response)))
     (.then (fn [text-response]
              (-> (reader/read-string text-response)
                  (set-scramble-response!)))))))


(rum/defcs input-group
  < (rum/local "" ::value)
  [state name]
  (let [local-value (::value state)]
    [:div.form-group
     [:label.form-group__label
      (str name ": ")
      [:input.form-input
       {:name name
        :value @local-value
        :on-input #(reset! local-value (targetValue %))}]]]))


(rum/defc scramble-message < rum/reactive []
  (let [state (rum/react app-state)]
    (cond
      (:loading state)
      [:div.scramble__message.scramble__message--loading
       "Loading the results"]

      (some? (:result state))
      [:div.scramble__message.scramble__message--result
       (str "Result for source: \"" (:source state) "\" and destination: \"" (:destination state) "\" is " (:result state))]

      (:error state)
      [:div.scramble__message.scramble__message--error
       (str "There was an error for source: \"" (:source state) "\" and destination: \"" (:destination state) "\". " (:error state))]

      :else [:div.scramble__message.scramble__message--empty])))


(rum/defc scramble-app < rum/static []
  [:div.container.scramble
   [:h1.scramble__title "Scramble"]
   [:span.scramble__description "Scramble is a function that returns true if a portion of source string can be rearranged to match destination string."]
   [:span.scramble__description "Fill input fields and click \"submit\" to get the result from the server."]
   [:form.scramble__form {:on-submit submit-form}
    (input-group "source")
    (input-group "destination")
    [:button.button.scramble__submit
     {:type :submit}
     "check"]]
   (scramble-message)])


(defn render []
  (rum/mount (scramble-app) (. js/document (getElementById "app"))))
