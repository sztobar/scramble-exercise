(ns scramble.validators.chars)


(defonce a_code (int \a))
(defonce z_code (int \z))


(defn- invalid-char-code? [char]
  (let [char_code (int char)]
    (or (< char_code a_code)
        (> char_code z_code))))


(defn scramble-invalid-chars? [text]
  (some?
   (some invalid-char-code? (vec text))))
