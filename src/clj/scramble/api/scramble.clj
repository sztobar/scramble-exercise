(ns scramble.api.scramble
  (:require [scramble.utils.scramble :refer [scramble?]]
            [scramble.validators.chars :refer [scramble-invalid-chars?]]
            [scramble.messages :refer [invalid-arg-message missing-arg-message]]
            [scramble.utils.response :refer [response-ok response-error]]))


(defn scramble-api [source destination]
  (cond
    (nil? source)
    (response-error {:error (missing-arg-message "source")
                     :source source
                     :destination destination})

    (nil? destination)
    (response-error {:error (missing-arg-message "destination")
                     :source source
                     :destination destination})

    (scramble-invalid-chars? source)
    (response-error {:error (invalid-arg-message "source")
                     :source source
                     :destination destination})

    (scramble-invalid-chars? destination)
    (response-error {:error (invalid-arg-message "destination")
                     :source source
                     :destination destination})

    :else
    (let [result (scramble? source destination)]
      (response-ok {:result result
                    :source source
                    :destination destination}))))
