(ns scramble.views.index
  (:require [clojure.java.io :as io]
            [ring.util.response :refer [response]]))

(defn index-view []
  (-> "public/index.html"
      io/resource
      io/input-stream
      response
      (assoc :headers {"Content-Type" "text/html; charset=utf-8"})))
