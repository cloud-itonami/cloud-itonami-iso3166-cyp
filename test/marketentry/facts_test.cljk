(ns marketentry.facts-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.facts :as facts]))

(deftest cyp-has-spec-basis
  (let [sb (facts/spec-basis "CYP")]
    (is (some? sb))
    (is (string? (:provenance sb)))
    (is (seq (:required-evidence sb)))
    (is (some? (facts/corporate-number-spec-basis "CYP")))
    (is (some? (facts/reserved-market-spec-basis "CYP")))))

(deftest cyp-rep-spec-basis-is-present-and-current
  (testing "unlike CAF's honestly-nil entry, Article 57(1)'s own representative/management-body exclusion extension is real and current for CYP"
    (is (some? (facts/rep-spec-basis "CYP")))
    (is (string? (:rep-legal-basis (facts/rep-spec-basis "CYP"))))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ"))))

(deftest required-evidence-satisfied
  (let [sb (facts/spec-basis "CYP")
        all (:required-evidence sb)]
    (is (true? (facts/required-evidence-satisfied? "CYP" all)))
    (is (not (facts/required-evidence-satisfied? "CYP" (take 1 all))))
    (is (nil? (facts/required-evidence-satisfied? "ATL" all)))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["CYP" "USA" "ATL"])]
    (is (= 3 (:requested c)))
    (is (= 2 (:covered c)))
    (is (= ["ATL"] (:missing-jurisdictions c)))))

(deftest reserved-market-spec-basis-self-cleaning-criteria
  (testing "the flagship self-cleaning gate's own criteria are recorded, kept under the `reserved-market-*` field names for cross-sibling-family parity"
    (let [rm (facts/reserved-market-spec-basis "CYP")]
      (is (= #{:compensation-paid-or-committed :facts-clarified-cooperated :remedial-measures-taken}
             (get-in rm [:reserved-market-criteria :self-cleaning-conditions])))
      (is (= 5 (get-in rm [:reserved-market-criteria :default-cap-years-conviction-grounds])))
      (is (= 3 (get-in rm [:reserved-market-criteria :default-cap-years-discretionary-grounds]))))))
