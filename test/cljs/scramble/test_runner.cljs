(ns scramble.test-runner
  (:require
   [doo.runner :refer-macros [doo-tests]]
   [scramble.core-test]
   [scramble.common-test]))

(enable-console-print!)

(doo-tests 'scramble.core-test
           'scramble.common-test)
