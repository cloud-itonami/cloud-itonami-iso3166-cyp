(ns marketentry.registry
  "Pure-function market-entry filing-draft + filing-submit record
  construction -- an append-only market-entry book-of-record draft.

  Like every sibling actor's registry, there is no single international
  reference-number standard for a public-procurement market-entry
  filing -- every jurisdiction assigns its own format. This namespace
  does NOT invent one; it builds a jurisdiction-scoped sequence number
  and validates the record's required fields, the same honest,
  non-fabricating discipline `marketentry.facts` uses.

  `engagement-fee-matches-claim?` is an HONEST reapplication of the
  SAME ground-truth-recompute DISCIPLINE sibling actors use (verify a
  claimed monetary total against the entity's own recorded quantity x
  unit fields), reapplied to a market-entry engagement fee line.

  `self-cleaning-eligible?` / `self-cleaning-claim-unsupported?` are the
  SAME discipline applied to a genuinely Cyprus-specific mechanism: Ν.
  73(Ι)/2016 (the Republic's own transposition of EU Directive
  2014/24/EU), Article 57(6)-(7)'s own 'self-cleaning' regime. An
  economic operator otherwise caught by an exclusion ground under
  Article 57(1)/(4) may avoid exclusion by proving, CUMULATIVELY (an
  AND of three, not an OR of three), that it has (a) paid or committed
  to pay compensation for damage caused by the offence/misconduct, (b)
  clarified the facts and circumstances by actively cooperating with
  the investigating authorities, and (c) taken concrete technical,
  organisational and personnel measures to prevent further offences/
  misconduct -- Article 57(6)(β)'s own conjunctive text ('ότι έχει
  καταβάλει ... ότι έχει διευκρινίσει ... και έχει λάβει ...'). Article
  57(6)(γ)'s own proviso separately BARS self-cleaning outright for an
  operator already excluded by a FINAL decision, for the period that
  decision itself sets -- independent of how many of the three
  cumulative conditions are otherwise met.

  This is a GENUINELY DIFFERENT check SHAPE than every prior iso3166
  sibling this repo mirrors: Bulgaria's ЗОП Art. 54(5) de-minimis is a
  PERCENTAGE-OF-TURNOVER ELIGIBILITY formula, Albania's Neni 76(2)(c)
  carve-out is a FLAT-CONSTANT ELIGIBILITY threshold, Azerbaijan's/
  Armenia's flagship checks are BOOLEAN registry-membership ELIGIBILITY
  reads, Antigua and Barbuda's vendor-class check is a THREE-TIER
  ELIGIBILITY-THRESHOLD classification, Benin's MPME mechanism is a
  BID-EVALUATION PRICE ADJUSTMENT, Bhutan's FDI Negative List is a
  CATEGORICAL SECTOR-EXCLUSION allow-list gate, and the Central African
  Republic's Marché réservé mechanism is a MULTI-CRITERION INCLUSION-
  ELIGIBILITY test built from an OR of three workforce-composition
  thresholds and a legal-form set-membership test. Cyprus's self-
  cleaning mechanism is none of these: it is a CONJUNCTIVE (AND, not OR)
  REMEDIATION-SUFFICIENCY test over three independent evidence
  conditions, gated by a separate FINAL-DECISION override that can bar
  the whole test regardless of how the three conditions individually
  resolve -- the first in this family to test whether a bidder that
  ALREADY has an exclusion ground has done enough to be REINSTATED,
  rather than testing eligibility from a clean slate.

  This namespace is pure data + pure functions -- no I/O, no network
  call to any real procurement portal. It builds the RECORD an operator
  would keep, not the act of submitting a portal registration itself
  (that is `marketentry.operation`'s `:filing/submit`, always
  human-gated -- see README Actuation)."
  (:require [clojure.string :as str]))

(defn- unsigned-certificate
  "Every certificate this actor produces is UNSIGNED -- signature is
  the market-entry operator's act, not this actor's."
  [kind subject record-id]
  {"@context" ["https://www.w3.org/ns/credentials/v2"]
   "type" ["VerifiableCredential" kind]
   "credentialSubject" {"id" subject "record" record-id}
   "proof" nil
   "issued_by_registry" false
   "status" "draft-unsigned"})

(defn- zero-pad [n w]
  (let [s (str n)]
    (str (apply str (repeat (max 0 (- w (count s))) "0")) s)))

(defn compute-engagement-fee
  "The ground-truth engagement fee for `engagement`'s own `:base-fee`
  and `:monitoring-months` x `:monthly-rate` -- a single flat
  base + months x rate calculation, not a full pricing engine."
  [{:keys [base-fee monthly-rate monitoring-months]}]
  (+ (double base-fee)
     (* (double monthly-rate) (double monitoring-months))))

(defn engagement-fee-matches-claim?
  "Does `engagement`'s own `:claimed-fee` equal the independently
  recomputed `compute-engagement-fee`?"
  [{:keys [claimed-fee] :as engagement}]
  (== (double claimed-fee) (compute-engagement-fee engagement)))

(def self-cleaning-conditions
  "Ν. 73(Ι)/2016, Article 57(6)(β), own definition of self-cleaning
  (OCR/HTML-verified 2026-07-22 against cylaw.org's own consolidated
  text): the THREE cumulative evidence conditions, ALL of which must be
  satisfied (an AND, not an OR) for an economic operator otherwise
  caught by an Article 57(1)/(4) exclusion ground to avoid exclusion."
  #{:compensation-paid-or-committed :facts-clarified-cooperated :remedial-measures-taken})

(defn self-cleaning-sufficient?
  "The ground-truth Article 57(6)(β) self-cleaning sufficiency for
  `engagement`, independently recomputed from its own declared evidence
  -- an AND of all three conditions. A missing/falsy value on any one
  condition fails the whole test (conjunctive, unlike the Central
  African Republic sibling's OR-of-three workforce test)."
  [{:keys [compensation-paid-or-committed? facts-clarified-cooperated? remedial-measures-taken?]}]
  (boolean (and compensation-paid-or-committed?
                facts-clarified-cooperated?
                remedial-measures-taken?)))

(defn self-cleaning-barred-by-final-decision?
  "Article 57(6)(γ)'s own proviso: an economic operator ALREADY excluded
  by a FINAL decision cannot invoke self-cleaning during the period that
  decision itself sets -- independent of the three evidence conditions."
  [{:keys [excluded-by-final-decision? within-exclusion-period?]}]
  (boolean (and excluded-by-final-decision? within-exclusion-period?)))

(defn self-cleaning-eligible?
  "The ground-truth self-cleaning eligibility for `engagement`: the
  three cumulative evidence conditions are ALL satisfied, AND the
  engagement is not barred by an already-final exclusion decision."
  [engagement]
  (boolean (and (self-cleaning-sufficient? engagement)
                (not (self-cleaning-barred-by-final-decision? engagement)))))

(defn self-cleaning-claim-unsupported?
  "Does `engagement` declare `:exclusion-ground? true` (it has a ground
  for exclusion under Article 57(1)/(4)) AND `:invokes-self-cleaning?
  true` (it is bidding relying on self-cleaning to avoid that
  exclusion), while the INDEPENDENTLY recomputed `self-cleaning-
  eligible?` is false? An engagement with no declared exclusion ground
  is never flagged by this check (entity/engagement-scope-gated, the
  same discipline Bhutan's `:foreign-company?`-gated FDI check uses)."
  [{:keys [exclusion-ground? invokes-self-cleaning?] :as engagement}]
  (boolean (and exclusion-ground?
                invokes-self-cleaning?
                (not (self-cleaning-eligible? engagement)))))

(defn register-draft
  "Validate + construct the FILING-DRAFT registration DRAFT -- the
  market-entry operator's own act of preparing a portal registration
  package. Pure function -- does not touch any real procurement
  portal."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "draft: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "draft: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "draft: sequence must be >= 0" {})))
  (let [draft-number (str (str/upper-case jurisdiction) "-DFT-" (zero-pad sequence 6))
        record {"record_id" draft-number
                "kind" "filing-draft"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "draft_number" draft-number
     "certificate" (unsigned-certificate "FilingDraft" draft-number draft-number)}))

(defn register-submit
  "Validate + construct the FILING-SUBMIT registration DRAFT -- the
  market-entry operator's own act of actually submitting a portal
  registration (always human-gated upstream)."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "submit: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "submit: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "submit: sequence must be >= 0" {})))
  (let [submit-number (str (str/upper-case jurisdiction) "-SUB-" (zero-pad sequence 6))
        record {"record_id" submit-number
                "kind" "filing-submit"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "submit_number" submit-number
     "certificate" (unsigned-certificate "FilingSubmit" submit-number submit-number)}))

(defn append [history result]
  (conj (vec history) (get result "record")))
