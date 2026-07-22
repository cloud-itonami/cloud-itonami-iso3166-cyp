# cloud-itonami-iso3166-cyp

Open ISO 3166 Blueprint for **CYP**: Republic of Cyprus.

Independent public-sector market-entry & procurement-compliance service
for an already-incorporated operator entering public contracts in Cyprus.

- Public Procurement Directorate (Διεύθυνση Δημόσιων Συμβάσεων, PPD) of
  the Treasury of the Republic of Cyprus -- Competent Authority for
  Public Procurement, Ν. 73(Ι)/2016; e-PPS (eprocurement.gov.cy)
  e-tendering portal
- DRCOR (Department of Registrar of Companies and Intellectual
  Property, Ministry of Energy, Commerce and Industry) incorporation
  record, Companies Law (Cap. 113); Tax Department Φορολογική Πύλη (Tax
  Portal, taxportal.mof.gov.cy, TAXISnet) tax record
- Ν. 73(Ι)/2016 Article 57(6)-(7) self-cleaning eligibility gate

AGPL-3.0-or-later.

## Market-entry / statute catalogs

Governed public-sector market-entry compliance actor, same architecture
as the other `cloud-itonami-iso3166-*` siblings:

- `src/marketentry/{facts,governor,phase,sim,operation,registry,store,
  marketentryllm}.cljc` -- the actor. `facts.cljc` cites the Public
  Procurement Directorate (PPD) of the Treasury of the Republic of
  Cyprus (designated Competent Authority for Public Procurement per its
  own page), Ν. 73(Ι)/2016 (the Republic's own transposition of EU
  Directive 2014/24/EU, fetched and read via CyLaw's consolidated
  text), the e-PPS e-tendering portal (eprocurement.gov.cy), DRCOR
  (Department of Registrar of Companies and Intellectual Property,
  Ministry of Energy, Commerce and Industry) incorporation, and the Tax
  Department's own Φορολογική Πύλη (Tax Portal, taxportal.mof.gov.cy,
  TAXISnet-credentialed) tax record. `governor.cljc`'s flagship check
  independently recomputes Article 57(6)'s own cumulative self-cleaning
  conditions (compensation paid/committed, facts clarified via
  cooperation, remedial measures taken -- an AND of three, not an OR),
  gated by Article 57(6)(γ)'s final-decision override -- a check shape
  genuinely different from every other iso3166 sibling's (see the
  namespace docstrings for the full research trail and honestly-
  narrowed scope, including facts this iteration could NOT verify, such
  as a Cyprus-specific data-protection implementing act, and one it
  found unreachable, the Ministry of Labour's own site).
- `src/statute/facts.cljc` -- general-law catalog: the Companies Law
  (Cap. 113, DRCOR's own consolidated PDF, read directly via
  `pdftotext`) and the Termination of Employment Law of 1967 (Ν.
  24/1967, via CyLaw's own consolidated index).

Every citation is curl/WebFetch-verified against an official source
(gov.cy/treasury, eprocurement.gov.cy, companies.gov.cy, gov.cy/mof,
cylaw.org); this iteration also recorded which working assumptions did
NOT survive verification (e.g. `e-ps.treasury.gov.cy` and `tfaportal.
mof.gov.cy` do not resolve -- the real portals are `eprocurement.gov.cy`
and `taxportal.mof.gov.cy`) -- see `marketentry.facts`'s docstring for
the full citation trail.

## Culture catalog

This repo carries a **country-level regional-culture catalog**
(ADR-2607171400 addendum 2, `cloud-itonami-municipality-culture-catalog`
Wave 1, in `com-junkawasaki/root`) — national dishes, protected products,
beverages, crafts, festivals and heritage sites for Cyprus:

- `src/culture/facts.cljc` — the catalog, source of truth (keyed by
  uppercase ISO3, mirroring the fleet's `statute.facts` convention).
- `schema/culture.edn` — DataScript schema.
- `data/culture-tx.edn` — derived DataScript tx-data (regenerated from
  the catalog, never hand-edited).

City-level counterparts live in the `cloud-itonami-municipality-*` repos.
Same provenance discipline as the compliance catalogs: every entry cites a
source URL that was actually fetched and read on `:culture/retrieved-at`;
summaries state only what the cited source confirms. An item not in
`culture.facts/catalog` has no spec-basis — never fabricate one.
