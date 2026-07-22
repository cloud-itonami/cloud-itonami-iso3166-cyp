(ns marketentry.registry-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.registry :as registry]))

(deftest engagement-fee-recompute
  (let [e {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12 :claimed-fee 860000.0}]
    (is (== 860000.0 (registry/compute-engagement-fee e)))
    (is (true? (registry/engagement-fee-matches-claim? e))))
  (let [bad {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12 :claimed-fee 999000.0}]
    (is (false? (registry/engagement-fee-matches-claim? bad)))))

(deftest register-draft-and-submit
  (let [d (registry/register-draft "eng-1" "CYP" 0)
        s (registry/register-submit "eng-1" "CYP" 0)]
    (is (= "CYP-DFT-000000" (get d "draft_number")))
    (is (= "CYP-SUB-000000" (get s "submit_number")))
    (is (nil? (get-in d ["certificate" "proof"])))
    (is (= "draft-unsigned" (get-in s ["certificate" "status"])))))

(deftest register-requires-ids
  (is (thrown? Exception (registry/register-draft "" "CYP" 0)))
  (is (thrown? Exception (registry/register-submit "eng-1" "" 0))))

(deftest self-cleaning-sufficient-requires-all-three-cumulatively
  (testing "an AND of three, not an OR of three -- any single missing condition fails the whole test"
    (is (true? (registry/self-cleaning-sufficient?
                {:compensation-paid-or-committed? true
                 :facts-clarified-cooperated? true
                 :remedial-measures-taken? true})))
    (is (false? (registry/self-cleaning-sufficient?
                 {:compensation-paid-or-committed? false
                  :facts-clarified-cooperated? true
                  :remedial-measures-taken? true})))
    (is (false? (registry/self-cleaning-sufficient?
                 {:compensation-paid-or-committed? true
                  :facts-clarified-cooperated? false
                  :remedial-measures-taken? true})))
    (is (false? (registry/self-cleaning-sufficient?
                 {:compensation-paid-or-committed? true
                  :facts-clarified-cooperated? true
                  :remedial-measures-taken? false})))
    (is (false? (registry/self-cleaning-sufficient? {}))
        "no qualifying evidence at all -> insufficient")))

(deftest self-cleaning-barred-by-final-decision
  (testing "Article 57(6)(γ)'s own proviso bars self-cleaning outright during an already-final exclusion period"
    (is (true? (registry/self-cleaning-barred-by-final-decision?
                {:excluded-by-final-decision? true :within-exclusion-period? true})))
    (is (false? (registry/self-cleaning-barred-by-final-decision?
                 {:excluded-by-final-decision? true :within-exclusion-period? false}))
        "a final decision whose own period has already elapsed no longer bars self-cleaning")
    (is (false? (registry/self-cleaning-barred-by-final-decision?
                 {:excluded-by-final-decision? false :within-exclusion-period? true}))
        "no final decision at all -> not barred by this branch")))

(deftest self-cleaning-eligible-combines-both-gates
  (testing "sufficient evidence AND not barred by a final decision -> eligible"
    (is (true? (registry/self-cleaning-eligible?
                {:compensation-paid-or-committed? true
                 :facts-clarified-cooperated? true
                 :remedial-measures-taken? true
                 :excluded-by-final-decision? false
                 :within-exclusion-period? false}))))
  (testing "sufficient evidence but barred by a final decision -> NOT eligible, the final-decision override wins"
    (is (false? (registry/self-cleaning-eligible?
                 {:compensation-paid-or-committed? true
                  :facts-clarified-cooperated? true
                  :remedial-measures-taken? true
                  :excluded-by-final-decision? true
                  :within-exclusion-period? true}))))
  (testing "insufficient evidence, even with no final-decision bar -> not eligible"
    (is (false? (registry/self-cleaning-eligible?
                 {:compensation-paid-or-committed? true
                  :facts-clarified-cooperated? false
                  :remedial-measures-taken? true
                  :excluded-by-final-decision? false
                  :within-exclusion-period? false})))))

(deftest self-cleaning-claim-unsupported-is-entity-scope-gated
  (testing "an engagement with NO declared exclusion ground is never flagged, even with zero self-cleaning evidence"
    (is (false? (registry/self-cleaning-claim-unsupported?
                 {:exclusion-ground? false :invokes-self-cleaning? false}))))
  (testing "an exclusion-ground engagement NOT invoking self-cleaning is never flagged by this check"
    (is (false? (registry/self-cleaning-claim-unsupported?
                 {:exclusion-ground? true :invokes-self-cleaning? false}))))
  (testing "an exclusion-ground engagement invoking self-cleaning that fails the cumulative evidence test -> unsupported claim"
    (is (true? (registry/self-cleaning-claim-unsupported?
                {:exclusion-ground? true :invokes-self-cleaning? true
                 :compensation-paid-or-committed? true
                 :facts-clarified-cooperated? true
                 :remedial-measures-taken? false
                 :excluded-by-final-decision? false
                 :within-exclusion-period? false}))))
  (testing "an exclusion-ground engagement invoking self-cleaning that DOES satisfy every condition -> not flagged"
    (is (false? (registry/self-cleaning-claim-unsupported?
                 {:exclusion-ground? true :invokes-self-cleaning? true
                  :compensation-paid-or-committed? true
                  :facts-clarified-cooperated? true
                  :remedial-measures-taken? true
                  :excluded-by-final-decision? false
                  :within-exclusion-period? false})))))
