(ns scramble.messages)


(defn invalid-arg-message [arg-type]
  (str "\"" arg-type "\" string contains not allowed chars. Only lowercase letters in range a-z are allowed."))


(defn missing-arg-message [arg-type]
  (str "\"" arg-type "\" argument is missing."))
