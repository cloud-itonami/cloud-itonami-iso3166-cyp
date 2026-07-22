# Business Model — Republic of Cyprus

## Offer
- Public Procurement Directorate (Διεύθυνση Δημόσιων Συμβάσεων, PPD) of
  the Treasury of the Republic of Cyprus (Γενικό Λογιστήριο της
  Δημοκρατίας) -- Competent Authority for Public Procurement, Ν.
  73(Ι)/2016 (transposing EU Directive 2014/24/EU); e-PPS e-tendering
  portal at eprocurement.gov.cy (see `src/marketentry/facts.cljc`)
- DRCOR (Department of Registrar of Companies and Intellectual
  Property, Ministry of Energy, Commerce and Industry) incorporation
  record, Companies Law (Cap. 113)
- Τμήμα Φορολογίας (Tax Department), Ministry of Finance -- tax
  registration via the Φορολογική Πύλη (Tax Portal, taxportal.mof.gov.cy,
  TAXISnet-credentialed)
- Ν. 73(Ι)/2016 Article 57(6)-(7) self-cleaning eligibility gate --
  bars an economic operator with a declared exclusion ground from
  relying on self-cleaning to bid unless it independently satisfies ALL
  THREE cumulative conditions (compensation paid/committed, facts
  clarified via cooperation, remedial measures taken) and is not barred
  by an already-final exclusion decision
- EU member — no national-content quota; EU single-market access

## Trust Controls
- `:filing/submit` never automated
- fabricated claims are HARD holds
