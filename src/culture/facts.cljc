(ns culture.facts
  "Country-level regional-culture catalog for Cyprus (CYP) -- national
  dishes, protected products, beverages, crafts, festivals and heritage
  sites, per ADR-2607171400 addendum 2 (cloud-itonami-municipality-
  culture-catalog Wave 1, in com-junkawasaki/root). First facts namespace
  in this blueprint-stage repo; the marketentry/statute catalogs land with
  :implemented (ADR-2607141700). City-level counterparts live in the
  cloud-itonami-municipality-* repos.

  Catalog is keyed by UPPERCASE ISO3 (mirrors the fleet's `statute.facts`
  convention); entries carry no :culture/municipality (that attribute is
  city-level only).

  Every entry cites a source URL that was actually fetched and read on
  :culture/retrieved-at -- never fabricated. Summaries state only what the
  cited source confirms. An item not in this table has NO spec-basis, full
  stop; extend `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of culture entries."
  {"CYP"
   [{:culture/id "cyp.dish.souvla"
     :culture/name "Souvla"
     :culture/name-local "Σούβλα"
     :culture/country "CYP"
     :culture/kind :dish
     :culture/summary "Popular dish from Cyprus of large chunks of lamb, pork or chicken barbecued on a long skewer over charcoal, traditionally prepared for celebrations such as Christmas and Easter."
     :culture/url "https://en.wikipedia.org/wiki/Souvla"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "cyp.dish.sheftalia"
     :culture/name "Sheftalia"
     :culture/country "CYP"
     :culture/kind :dish
     :culture/summary "Traditional sausage that originated in Cyprus, made by wrapping meat in caul fat instead of a conventional casing; regarded as one of the main staples of Cypriot cuisine."
     :culture/url "https://en.wikipedia.org/wiki/Sheftalia"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "cyp.dish.afelia"
     :culture/name "Afelia"
     :culture/country "CYP"
     :culture/kind :dish
     :culture/summary "Sauté of pork, red wine and coriander seeds, a traditional dish of Cypriot cuisine."
     :culture/url "https://en.wikipedia.org/wiki/Cypriot_cuisine"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "cyp.product.halloumi"
     :culture/name "Halloumi"
     :culture/name-local "Χαλλούμι / Hellim"
     :culture/country "CYP"
     :culture/kind :product
     :culture/summary "Cheese possibly originating from Cyprus, protected in the EU as a protected designation of origin under which only products made in certain parts of Cyprus may be called halloumi."
     :culture/url "https://en.wikipedia.org/wiki/Halloumi"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "cyp.beverage.commandaria"
     :culture/name "Commandaria"
     :culture/country "CYP"
     :culture/kind :beverage
     :culture/summary "Amber-coloured sweet dessert wine from the foothills of the Troödos Mountains in Cyprus, described as the world's oldest named wine still in production, with PDO status in the EU, the US and Canada."
     :culture/url "https://en.wikipedia.org/wiki/Commandaria"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "cyp.beverage.zivania"
     :culture/name "Zivania"
     :culture/country "CYP"
     :culture/kind :beverage
     :culture/summary "Cypriot pomace brandy produced from the distillation of a mixture of grape pomace and local dry wines."
     :culture/url "https://en.wikipedia.org/wiki/Zivania"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "cyp.craft.lefkara-lace"
     :culture/name "Lefkara lace"
     :culture/name-local "Lefkaritika"
     :culture/country "CYP"
     :culture/kind :craft
     :culture/summary "White embroidery art from the Lefkara villages of Cyprus dating back to at least the fourteenth century; added to UNESCO's Representative List of the Intangible Cultural Heritage in 2009."
     :culture/url "https://en.wikipedia.org/wiki/Lefkara_lace"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "cyp.heritage.paphos"
     :culture/name "Paphos"
     :culture/name-local "Πάφος"
     :culture/country "CYP"
     :culture/kind :heritage
     :culture/summary "Coastal city in southwest Cyprus included on the UNESCO World Heritage List in 1980 for its ancient architecture, mosaics and ancient religious importance."
     :culture/url "https://en.wikipedia.org/wiki/Paphos"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}]})

(defn spec-basis [iso3] (get catalog iso3))

(defn coverage
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-cyp culture catalog "
                 "(ADR-2607171400 addendum 2, Wave 1): " (count (get catalog "CYP"))
                 " CYP entries, each with a fetched-and-read citation. "
                 "Extend `culture.facts/catalog`, never fabricate an id/url.")})))

(defn by-kind [iso3 kind]
  (filterv #(= (:culture/kind %) kind) (spec-basis iso3)))
