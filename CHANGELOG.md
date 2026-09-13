# Requirements Register Audit Change Log — merSETA NSDMS

> **Companion Document to REQUIREMENTS.md**  
> **Schema Version:** 3.0  
> **Register Revision:** 1.0  
> **Named Owner / Approver:** Tebogo Moepi  
> **Last Updated:** 2026-09-13  

---

## Change Log Entries

### [Register Revision 1.0] — 2026-09-13

**Trigger:** Schema 3.0 Greenfield Bootstrap & System Alignment  
**Author / Steward:** Tebogo Moepi (`@tmoepi`)  
**Linter Status:** `requirements_lint.py v1.1` — Exit 0 (0 errors, 0 warnings, strict mode passed)  

#### Summary of Changes:
1. **Established Living Register:** Bootstrapped `REQUIREMENTS.md` under Schema 3.0 with 15 verified functional requirements (`FR-001` through `FR-015`).
2. **Established Measurable NFRs:** Defined 4 measurable non-functional requirements (`NFR-001` through `NFR-004`) with numeric thresholds and stated measurement suites.
3. **Statutory Business Rules:** Codified 6 binding statutory business rules (`BR-001` through `BR-006`) covering WSP deadlines, extension horizons, levy splits, designated trade toolkits, and mentor ratios.
4. **Architectural Decisions:** Registered 5 core architectural decisions (`DEC-001` through `DEC-005`) for temporal tables, stacked master-detail UX, channel-decoupled background pipelines, audited double-writes, and dynamic system configurations.
5. **Assumptions & Risks:** Initialized `ASM-001` and `ASM-002` tracking annual submission volumes and monthly SARS levy transaction capacities.
6. **Acceptance Criteria:** Authored GIVEN/WHEN/THEN acceptance criteria for all 15 functional requirements with 100% verified status (`[x]`).
7. **Traceability Matrix:** Verified and bound exact code file paths, test file paths, and commit SHAs for all functional requirements.
8. **Statutory Compliance Matrix:** Linked all compliance-tagged requirements to the Statutory Compliance Matrix in `COMPLIANCE.md` (`SDA`, `SDLA`, `SETAREG`, `PFMA`, `POPIA`, `QCTO`, `NAMB`, `SETMIS`, `NLRD`).
9. **Governance Protection:** Protected `requirements_lint.py` under `CODEOWNERS` and wired the CI validation gate.

### [Register Revision 1.1] — 2026-09-13

**Trigger:** Document Provenance & Section-Anchored Linking Enhancement  
**Author / Steward:** Tebogo Moepi (`@tmoepi`)  
**Linter Status:** `requirements_lint.py v1.1` — Exit 0 (0 errors, 0 warnings, strict mode passed)  

#### Summary of Changes:
1. **Source Document Registry Table:** Added dedicated Document Registry table in `REQUIREMENTS.md` tracking 17 core specifications and statutory legal authorities (both internal repository specs with section anchors and external official gazettes/legislation URLs).
2. **Doc Ref Column Integration:** Enriched the Functional Requirements table with a normalized `Doc Ref` column binding each FR to its originating specification(s) and legal mandate(s).
3. **Section Anchor Precision:** Attached specific section anchors (e.g. `#1-domain-overview`) for instantaneous navigation into internal specification markdown files.
