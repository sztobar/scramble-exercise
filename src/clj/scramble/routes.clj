(ns scramble.routes
  (:require [compojure.core :refer [GET POST routes context]]
            [compojure.route :refer [resources]]
            [scramble.api.scramble :refer [scramble-api]]
            [scramble.views.index :refer [index-view]]))

(defn scramble-routes [endpoint]
  (routes
    (GET "/" _
        (index-view))
    (context "/api" _
            (POST "/scramble" [source destination]
                  (scramble-api source destination)))
    (resources "/")))
