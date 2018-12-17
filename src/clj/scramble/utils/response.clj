(ns scramble.utils.response)

(defn response-ok [body]
  {:status 200
   :headers {"Content-Type" "application/edn"}
   :body (pr-str body)})

(defn response-error [body]
  {:status 400
   :headers {"Content-Type" "application/edn"}
   :body (pr-str body)})
