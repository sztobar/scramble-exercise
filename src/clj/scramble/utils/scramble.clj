(ns scramble.utils.scramble)


(defonce a_code (int \a))
(defonce z_code (int \z))


(defn- invalid-letter? [char]
  (let [char_code (int char)]
    (or (< char_code a_code)
        (> char_code z_code))))


(defn- illegal-arg-exception []
  (IllegalArgumentException. "Arguments must contain letters from a-z only."))


(defn- group-letters [source]
  (reduce
   (fn [agg char]
     (if (agg char)
       (update agg char inc)
       (if (invalid-letter? char)
         (throw (illegal-arg-exception))
         (assoc agg char 1))))
   {}
   (vec source)))


(defn- can-use? [letter-groups char]
  (> (get letter-groups char 0)
     0))


(defn- scramble-check [source destination]
  (reduce
   (fn [letter-groups char]
     (if (can-use? letter-groups char)
       (update letter-groups char dec)
       (if (invalid-letter? char)
         (throw (illegal-arg-exception))
         (reduced false))))
   (group-letters source)
   (vec destination)))


(defn scramble? [source destination]
  (boolean (scramble-check source destination)))
