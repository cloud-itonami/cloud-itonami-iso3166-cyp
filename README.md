# cloud-itonami-iso3166-cyp

Open ISO 3166 Blueprint for **CYP**: Republic of Cyprus.

Independent public-sector market-entry & procurement-compliance service
for an already-incorporated operator entering public contracts in Cyprus.

## Official surface

- Procurement: eProcurement system of the Treasury of the Republic of Cyprus / e-ps.treasury.gov.cy
- Business/tax: Registrar of Companies + Tax Department TIN

## License

AGPL-3.0-or-later.

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
