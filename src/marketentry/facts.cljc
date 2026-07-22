(ns marketentry.facts
  "Per-jurisdiction public-procurement market-entry regulatory catalog
  -- the G2-style spec-basis table the Market-Entry Compliance Governor
  checks every `:jurisdiction/assess` proposal against ('did the advisor
  cite an OFFICIAL public source for this jurisdiction's requirements,
  or did it invent one?').

  Cyprus's real market-entry surface (curl/WebFetch-verified 2026-07-22;
  the government's own sites are split across several distinct WordPress
  subsites of `gov.cy` plus a few dedicated e-service domains -- every
  fact below traces to a page this iteration actually fetched and read,
  not a name assumed by analogy to another EU member state):

  - **Public procurement's Competent Authority is the Public Procurement
    Directorate (Διεύθυνση Δημόσιων Συμβάσεων, PPD) of the Treasury of
    the Republic of Cyprus (Γενικό Λογιστήριο της Δημοκρατίας).** This
    iteration fetched the Treasury's own organisational page
    (`gov.cy/treasury/geniko-logistirio/domi/`, HTML, fetched directly)
    which lists 'Διεύθυνση Δημόσιων Συμβάσεων' among its directorates,
    then fetched the PPD's OWN dedicated page
    (`gov.cy/treasury/geniko-logistirio/domi/c/`, fetched directly) whose
    own text states, verbatim: 'Η Διεύθυνση Δημόσιων Συμβάσεων του
    Γενικού Λογιστηρίου της Δημοκρατίας έχει οριστεί ως η Αρμόδια Αρχή
    για τις Δημόσιες Συμβάσεις' ('The Public Procurement Directorate of
    the Treasury of the Republic has been designated as the Competent
    Authority for Public Procurement') -- HIGH confidence, read directly.
    The SAME PPD page also states that, regarding contract execution,
    the Treasury operates and chairs the 'Κεντρική Επιτροπή Αλλαγών και
    Απαιτήσεων' (ΚΕΑΑ, Central Committee for Changes and Claims), which
    'διατηρεί και το Μητρώο Αποκλεισμού' ('also maintains the Exclusion
    Register'). This iteration did NOT independently fetch the delegated
    Κανονισμοί (Regulations issued under the Law's own Article 93) that
    would define ΚΕΑΑ's/the register's procedural detail -- an honestly
    flagged gap; nothing in this catalog asserts a fact about those
    Regulations' own text.
  - **Legal basis, independently fetched and read (not merely cited by
    a secondary source): Ο περί της Ρύθμισης των Διαδικασιών Σύναψης
    Δημοσίων Συμβάσεων και για Συναφή Θέματα Νόμος του 2016 (Ν.
    73(Ι)/2016)**, the Republic's own transposition of EU Directive
    2014/24/EU. This iteration found the law via CyLaw (`cylaw.org`,
    the Cyprus Bar Association's legislation database -- an
    official-body-hosted secondary source, the SAME kind of source the
    task brief itself names as acceptable), fetched its own consolidated
    index page (`cylaw.org/nomoi/indexes/2016_1_73.html`) confirming the
    exact title and Official Gazette citation ('Ε.Ε., Παρ.Ι(I), Αρ.4565,
    28/4/2016'), amended by Ν. 205(Ι)/2020, Ν. 74(Ι)/2022 and Ν.
    113(Ι)/2025 (all three listed on the same index page as already
    incorporated into the consolidated text), then fetched the FULL
    consolidated text (`cylaw.org/nomoi/enop/non-ind/2016_1_73/full.html`,
    ~490KB HTML, fetched and read directly) and located this vertical's
    own Article 57 ('Λόγοι αποκλεισμού' / 'Grounds for exclusion'), which
    this catalog's `reserved-market`-equivalent flagship grounds itself
    on -- see `reserved-market-spec-basis` below (kept under that key
    name for cross-sibling-family consistency even though CYP's own
    mechanism is a SELF-CLEANING gate, not a reserved-contract-eligibility
    gate; see `marketentry.governor`'s docstring for the full shape
    contrast).
  - **e-Procurement**: `eprocurement.gov.cy`, the 'e-PPS' (electronic
    Public Procurement System), fetched directly (HTTP 200, own page
    title 'eProcurement - Καλώς ήρθατε στο e-PPS' / 'Welcome to e-PPS').
    Its own 'Contact Us' popup (fetched directly) names 'European
    Dynamics' as the platform's technical operator/helpdesk
    (`cyepro-helpdesk@eurodyn.com`) -- i.e. the SAME company whose
    copyright footer this iteration independently found on the Central
    African Republic sibling catalog's own portal note pattern; this is
    reported as the platform vendor, NOT the legal Competent Authority
    (the PPD remains the Authority per the Treasury's own page above).
  - **Business/company registration**: this iteration specifically
    investigated -- rather than assumed by analogy -- Cyprus's registrar
    of companies. `businessregistry.mcit.gov.cy` (a name this iteration
    considered before fetching anything) does NOT resolve at all (DNS
    failure, confirmed directly); the REAL, live site is
    `gov.cy/en/directorate/department-of-registrar-of-companies-and-
    intellectual-property/` (fetched directly, own page title
    'Intellectual Property Section, Department of Registrar of Companies
    and Intellectual Property', own body text: 'The Department of
    Registrar of Companies and Intellectual Property is participating in
    the EUIPO Spring Campaign 2026') plus its companies-specific sibling
    section `companies.gov.cy/en/` (fetched directly, own page title
    'Companies Section, Department of Registrar of Companies and
    Intellectual Property'). Both pages link to `meci.gov.cy/en/`, the
    Ministry of Energy, Commerce and Industry (fetched directly, own
    page title 'Αρχική - Υπουργείο Ενέργειας, Εμπορίου και Βιομηχανίας'),
    confirming DRCOR's parent ministry.
  - **Companies Law, Cap. 113** is cited both here and in
    `statute.facts`: this iteration fetched DRCOR's OWN legislation page
    (`companies.gov.cy/en/knowledgebase/legislation/the-companies-law`,
    fetched directly) which links the consolidated English translation
    PDF (`companies.gov.cy/assets/modules/wgp/articles/201801/49/docs/
    cap_113_translation_made_july_2014.pdf`, 3.7MB) AND names the most
    recent amendment on the same page, 'Companies (Amending) Law No.2 of
    2025' -- this iteration then downloaded that PDF directly and ran
    `pdftotext` (a real, machine-readable, non-scanned PDF, unlike the
    Central African Republic sibling's scanned Code) confirming its own
    cover page reads, verbatim: 'REPUBLIC OF CYPRUS / THE COMPANIES LAW
    (English translation and consolidation) / Cap. 113.' followed by a
    long list of amendment years -- HIGH confidence, read directly.
  - **Tax registration**: this iteration specifically investigated
    rather than assumed the portal name. The task's own working
    assumption going in ('Tax For All / TFA portal') did NOT survive
    verification: `tfaportal.mof.gov.cy` does not resolve (DNS failure,
    confirmed directly). The REAL name, found directly on the Ministry
    of Finance's own Tax Department page (`gov.cy/mof/tmima-forologias/`,
    fetched directly, own page title 'Τμήμα Φορολογίας - Υπουργείο
    Οικονομικών'), is the 'Φορολογική Πύλη' ('Tax Portal'): its own text
    reads, verbatim, 'Για πρόσβαση στη «Φορολογική Πύλη», το κοινό μπορεί
    να επισκέπτεται το https://taxportal.mof.gov.cy και να καταχωρεί
    τους κωδικούς που χρησιμοποιεί για πρόσβαση στο σύστημα TAXISnet'
    ('To access the \"Tax Portal\", the public may visit
    https://taxportal.mof.gov.cy and enter the credentials they use to
    access the TAXISnet system'). This iteration independently confirmed
    `taxportal.mof.gov.cy` resolves (HTTP 200). The SAME Tax Department
    page also states, verbatim: 'Από την 1η Ιουλίου 2014 έχει συσταθεί
    το Τμήμα Φορολογίας, το οποίο έχει ενοποιήσει το Τμήμα Εσωτερικών
    Προσόδων και την Υπηρεσία ΦΠΑ' ('Since 1 July 2014 the Tax
    Department has been established, merging the Department of Inland
    Revenue and the VAT Service') -- HIGH confidence, read directly.
  - This iteration also looked for a CYP-specific representative/
    director exclusion-extension provision (the shape Bulgaria's ЗОП
    Art. 54(2)-(3) / Benin's Art. 61/62 document for their own laws, and
    which the Central African Republic sibling honestly left nil).
    UNLIKE CAF, this iteration DID find and confirm the current,
    in-force provision directly in Ν. 73(Ι)/2016's own Article 57(1)
    proviso (machine-read HTML, HIGH confidence): 'Νοείται ότι, η
    υποχρέωση αποκλεισμού οικονομικού φορέα εφαρμόζεται επίσης όταν το
    πρόσωπο, εις βάρος του οποίου εκδόθηκε τελεσίδικη καταδικαστική
    απόφαση, είναι μέλος του διοικητικού, διευθυντικού ή εποπτικού
    οργάνου του εν λόγω οικονομικού φορέα ή έχει εξουσία εκπροσώπησης,
    λήψης αποφάσεων ή ελέγχου σε αυτό' ('the obligation to exclude an
    economic operator also applies when the person against whom a final
    conviction was issued is a member of the administrative, management
    or supervisory body of that economic operator, or has power of
    representation, decision-making or control in it'). `rep-spec-basis`
    below cites this directly.
  - `reserved-market-spec-basis` (kept under this key name for
    cross-sibling-family field-shape consistency; see the flagship-check
    contrast note above) grounds this vertical's FLAGSHIP check -- see
    `marketentry.governor` / `marketentry.registry` -- in Ν. 73(Ι)/2016's
    own Article 57(6) and 57(7), both fetched and read directly from the
    same consolidated CyLaw text as Article 57(1) above: an economic
    operator otherwise caught by an exclusion ground under Article
    57(1)/(4) may avoid exclusion ('self-cleaning') by proving,
    CUMULATIVELY (all three, not any one), that it has (a) paid or
    committed to pay compensation for damage caused by the offence/
    misconduct, (b) clarified the facts and circumstances by actively
    cooperating with the investigating authorities, and (c) taken
    concrete technical, organisational and personnel measures to prevent
    further offences/misconduct -- Article 57(6)(β)'s own text, own
    conjunction ('ότι έχει καταβάλει ... ότι έχει διευκρινίσει ... και
    έχει λάβει ...'). Article 57(6)(γ)'s own proviso separately BARS
    self-cleaning outright for an operator already excluded by a FINAL
    decision, for the period that decision itself sets. Article 57(7)
    separately caps the DEFAULT exclusion period (when no final decision
    fixes one) at five years for the Article 57(1)/(2)(a) (conviction/
    tax-or-social-security) grounds and three years for the Article
    57(4) (discretionary) grounds -- this catalog's `self-cleaning-
    criteria` records both the three cumulative evidence conditions and
    these two default-cap numbers, but does not model the delegated
    Article 93 Regulations' own procedural detail (an honest scope
    limit, the same discipline the Central African Republic sibling used
    for its own delegated-arrêté branch).

  Coverage is reported HONESTLY (see `coverage`): a jurisdiction not in
  this table has NO spec-basis, full stop -- the advisor must not
  fabricate one, and the governor holds if it tries.")

(def catalog
  "iso3 -> requirement map. `:required-evidence` mirrors the generic
  intake/portal-registration/filing evidence set; `:legal-basis` /
  `:owner-authority` / `:provenance` are the G2 citation the governor
  requires before any `:jurisdiction/assess` proposal can commit.
  `:rep-owner-authority` / `:rep-legal-basis` / `:rep-provenance` ground
  Article 57(1)'s own representative/management-body exclusion
  extension (present and current for CYP, unlike CAF's honestly-nil
  entry). `:reserved-market-owner-authority` / `:reserved-market-legal-
  basis` / `:reserved-market-criteria` / `:reserved-market-provenance`
  ground this vertical's flagship governor check (`self-cleaning-
  eligible?`/`self-cleaning-claim-unsupported?` in
  `marketentry.registry`) -- kept under the `reserved-market-*` key
  names for field-shape parity with the rest of the iso3166 family, even
  though CYP's own mechanism is a self-cleaning evidence-sufficiency
  gate rather than a reserved-contract-eligibility gate."
  {"CYP" {:name "Republic of Cyprus"
          :owner-authority "Public Procurement Directorate (Διεύθυνση Δημόσιων Συμβάσεων, PPD), Treasury of the Republic of Cyprus (Γενικό Λογιστήριο της Δημοκρατίας) -- own page states it 'has been designated as the Competent Authority for Public Procurement' (Αρμόδια Αρχή για τις Δημόσιες Συμβάσεις)"
          :legal-basis "Ο περί της Ρύθμισης των Διαδικασιών Σύναψης Δημοσίων Συμβάσεων και για Συναφή Θέματα Νόμος του 2016 (Ν. 73(Ι)/2016), Ε.Ε. Παρ.Ι(Ι) Αρ.4565, 28/4/2016 -- transposing EU Directive 2014/24/EU, amended by Ν. 205(Ι)/2020, Ν. 74(Ι)/2022 and Ν. 113(Ι)/2025 (all incorporated into the consolidated text this iteration fetched and read); own Article 57 (Λόγοι αποκλεισμού / grounds for exclusion) and Article 93 (delegated Regulations)"
          :national-spec "Ηλεκτρονικό Σύστημα Σύναψης Δημοσίων Συμβάσεων (e-PPS / eProcurement, eprocurement.gov.cy) -- self-service e-tendering portal, technically operated by European Dynamics S.A.; the Treasury also operates and chairs the Κεντρική Επιτροπή Αλλαγών και Απαιτήσεων (ΚΕΑΑ, Central Committee for Changes and Claims), whose own PPD page states it 'also maintains the Exclusion Register' (Μητρώο Αποκλεισμού) -- the delegated Regulations defining ΚΕΑΑ's/the register's own procedural detail were NOT independently fetched by this iteration"
          :provenance "https://www.gov.cy/treasury/geniko-logistirio/domi/c/ ; https://www.gov.cy/treasury/geniko-logistirio/domi/ ; https://www.eprocurement.gov.cy/ ; http://www.cylaw.org/nomoi/enop/non-ind/2016_1_73/full.html"
          :required-evidence ["DRCOR (Department of Registrar of Companies and Intellectual Property) incorporation record, per the Companies Law (Cap. 113)"
                              "Tax Department TIN / Φορολογική Πύλη (Tax Portal, taxportal.mof.gov.cy, TAXISnet-credentialed) registration record"
                              "e-PPS (eprocurement.gov.cy) economic-operator registration confirmation record"
                              "Self-cleaning evidence record, when the engagement declares :exclusion-ground? true and :invokes-self-cleaning? true"
                              "Authorized-representative confirmation record"]
          :rep-owner-authority "Public Procurement Directorate (PPD), Treasury of the Republic of Cyprus -- same Competent Authority as :owner-authority, applying Article 57(1)'s own representative/management-body extension"
          :rep-legal-basis "Ν. 73(Ι)/2016, own Article 57(1) proviso (own text, fetched and read directly): 'the obligation to exclude an economic operator also applies when the person against whom a final conviction was issued is a member of the administrative, management or supervisory body of that economic operator, or has power of representation, decision-making or control in it'"
          :rep-provenance "http://www.cylaw.org/nomoi/enop/non-ind/2016_1_73/full.html"
          :corporate-number-owner-authority "Τμήμα Φορολογίας (Tax Department), Ministry of Finance -- own page states it was formed 1 July 2014 by merging the Department of Inland Revenue and the VAT Service"
          :corporate-number-legal-basis "Tax Department's own page (gov.cy/mof/tmima-forologias/, fetched directly): 'Since 1 July 2014 the Tax Department has been established, merging the Department of Inland Revenue and the VAT Service'; TIN registration/filing via the Φορολογική Πύλη (Tax Portal), TAXISnet-credentialed"
          :corporate-number-provenance "https://www.gov.cy/mof/tmima-forologias/ ; https://taxportal.mof.gov.cy/"
          :reserved-market-owner-authority "Public Procurement Directorate (PPD), Treasury of the Republic of Cyprus -- each αναθέτουσα αρχή (contracting authority) applies Article 57 directly; the self-cleaning sufficiency determination is the contracting authority's own, per Article 57(6)"
          :reserved-market-legal-basis "Ν. 73(Ι)/2016, own Article 57(6)(β) (own text, fetched and read directly): self-cleaning requires cumulative proof of (i) compensation paid or committed for damage caused, (ii) facts/circumstances clarified via active cooperation with investigating authorities, and (iii) concrete technical/organisational/personnel measures taken to prevent further offences/misconduct; Article 57(6)(γ) bars self-cleaning outright during a period already fixed by a FINAL exclusion decision; Article 57(7) caps the DEFAULT (no final decision) exclusion period at five years for Article 57(1)/(2)(a) grounds and three years for Article 57(4) grounds"
          :reserved-market-criteria {:self-cleaning-conditions #{:compensation-paid-or-committed :facts-clarified-cooperated :remedial-measures-taken}
                                     :default-cap-years-conviction-grounds 5
                                     :default-cap-years-discretionary-grounds 3}
          :reserved-market-provenance "http://www.cylaw.org/nomoi/enop/non-ind/2016_1_73/full.html"}
   "USA" {:name "United States"
          :owner-authority "U.S. General Services Administration (GSA) / SAM.gov"
          :legal-basis "Federal Acquisition Regulation (FAR); System for Award Management"
          :national-spec "SAM.gov entity registration + NAICS self-certification"
          :provenance "https://sam.gov/"
          :required-evidence ["EIN record"
                              "SAM.gov registration record"
                              "State business registration record"
                              "Authorized-representative record"]}
   "DEU" {:name "Germany"
          :owner-authority "Beschaffungsamt des BMI / e-Vergabe platforms"
          :legal-basis "Gesetz gegen Wettbewerbsbeschränkungen (GWB) / VgV"
          :national-spec "e-Vergabe supplier registration under EU procurement directives"
          :provenance "https://www.evergabe-online.de/"
          :required-evidence ["Handelsregister extract"
                              "e-Vergabe registration record"
                              "USt-IdNr record"
                              "Authorized-representative record"]}})

(defn spec-basis
  "The jurisdiction's requirement map, or nil -- nil means NO spec-basis,
  and the governor must hold any proposal that tries to assess or file
  on it."
  [iso3]
  (get catalog iso3))

(defn coverage
  "Honest coverage report: how many of the requested jurisdictions actually
  have a spec-basis entry. Never report a missing jurisdiction as covered."
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-cyp R0: " (count catalog)
                 " jurisdictions seeded with an official spec-basis. "
                 "This is a starting catalog for market-entry navigation, "
                 "not a survey of all ~194 jurisdictions -- extend "
                 "`marketentry.facts/catalog`, never fabricate a "
                 "jurisdiction's requirements.")})))

(defn required-evidence-satisfied?
  "Does `submitted` (a set/coll of evidence keywords or strings) satisfy
  every evidence item listed for `iso3`? Missing spec-basis -> never
  satisfied."
  [iso3 submitted]
  (when-let [{:keys [required-evidence]} (spec-basis iso3)]
    (let [need (count required-evidence)
          have (count (filter (set submitted) required-evidence))]
      (= need have))))

(defn evidence-checklist [iso3]
  (:required-evidence (spec-basis iso3) []))

(defn rep-spec-basis
  "The jurisdiction's representative-related requirement map, or nil when
  this catalog has no such regime. For CYP this is a REAL, current
  provision (unlike CAF's honestly-nil entry) -- Article 57(1)'s own
  proviso extending exclusion to administrative/management/supervisory
  body members and persons with representation/decision-making/control
  power."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:rep-owner-authority sb)
      (select-keys sb [:rep-owner-authority :rep-legal-basis :rep-provenance]))))

(defn corporate-number-spec-basis
  "The jurisdiction's corporate-number / tax-id regime, or nil."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:corporate-number-owner-authority sb)
      (select-keys sb [:corporate-number-owner-authority
                       :corporate-number-legal-basis
                       :corporate-number-provenance]))))

(defn reserved-market-spec-basis
  "The jurisdiction's self-cleaning eligibility regime, or nil. For CYP
  this is real and current -- the flagship check this vertical adds is
  grounded here (Ν. 73(Ι)/2016, Article 57(6)-(7), 'self-cleaning')."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:reserved-market-owner-authority sb)
      (select-keys sb [:reserved-market-owner-authority
                       :reserved-market-legal-basis
                       :reserved-market-criteria
                       :reserved-market-provenance]))))
