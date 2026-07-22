(ns statute.facts-test
  (:require [clojure.string :as str]
            [clojure.test :refer [deftest is]]
            [statute.facts :as facts]))

(deftest cyp-has-spec-basis
  (let [sb (facts/spec-basis "CYP")]
    (is (= 2 (count sb)))
    (is (every? #(str/starts-with? (:statute/url %) "https://") (filter #(= (:statute/url-provenance %) :official-drcor-gov-cy) sb)))
    (is (every? :statute/law-number sb))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ"))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["CYP" "JPN" "ATL"])]
    (is (= 3 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["ATL" "JPN"] (:missing-jurisdictions c)))))

(deftest by-topic-filters
  (is (= ["cyp.companies-law-cap113"]
         (mapv :statute/id (facts/by-topic "CYP" :corporate-governance))))
  (is (= ["cyp.termination-of-employment-law-1967"]
         (mapv :statute/id (facts/by-topic "CYP" :labor))))
  (is (empty? (facts/by-topic "CYP" :data-protection))
      "a Cyprus-specific data-protection implementing act could not be independently verified this iteration -- honestly absent, see namespace docstring")
  (is (empty? (facts/by-topic "ATL" :labor))))
