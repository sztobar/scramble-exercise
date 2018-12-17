(ns scramble.scramble-test
  (:require [clojure.test :refer :all]
            [scramble.utils.scramble :refer [scramble?]]
            [scramble.validators.chars :refer [scramble-invalid-chars?]]))


(deftest scramble-validity
  (is (= (scramble? "rekqodlw" "world") true))
  (is (= (scramble? "cedewaraaossoqqyt" "codewars") true))
  (is (= (scramble? "katas"  "steak") false))
  (is (= (scramble? "a" "aa") false) "number of letters in source must match it's occurence in destination")
  (is (= (scramble? "az" "za") true) "a-z are allowed range of chars"))


(deftest scramble-validator
  (is (= (scramble-invalid-chars? "1")))
  (is (= (scramble-invalid-chars? ".")))
  (is (= (scramble-invalid-chars? "A")))
  (is (= (scramble-invalid-chars? "Z"))))
