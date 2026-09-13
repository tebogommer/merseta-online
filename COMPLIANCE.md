# Statutory Compliance Register — merSETA NSDMS

> **Companion Document to REQUIREMENTS.md**  
> **Schema Version:** 3.0  
> **Register Revision:** 1.0  
> **Named Compliance Officer / Owner:** Tebogo Moepi  
> **Last Updated:** 2026-09-13  

---

## 1. Statutory Frameworks & Regulatory Authorities

| Tag | Regulatory Framework | Statutory Purpose & Scope |
|---|---|---|
| **SDA** | Skills Development Act 97 of 1998 | Governs workplace skills planning, learnership agreements, and artisan trade testing. |
| **SDLA** | Skills Development Levies Act 9 of 1999 | Governs collection, calculation, and distribution of statutory skills levies. |
| **SETAREG** | SETA Grant Regulations (2012) | Governs Mandatory and Discretionary Grant allocations, statutory windows, and submission deadlines. |
| **PFMA** | Public Finance Management Act 1 of 1999 | Governs financial controls, delegations, dual authorisation (Segregation of Duties), and ethics disclosures. |
| **POPIA** | Protection of Personal Information Act 4 of 2013 | Governs confidentiality, consent, personal data protection, and masking of national identity numbers. |
| **QCTO** | Quality Council for Trades and Occupations | Governs occupational qualification accreditation, SDP quality assurance, and assessment standards. |
| **NAMB** | National Artisan Moderation Body | Governs national artisan trade testing standards, designated toolkits, and mentor ratios. |
| **SETMIS** | DHET SETMIS Standard | Governs Department of Higher Education statutory flat-file reporting specifications across 11 files. |
| **NLRD** | SAQA National Learners' Records Database | Governs SAQA Edu.Dex data submission specifications for Supplier Code 599. |

---

## 2. Requirement Compliance Traceability

| Requirement ID | Primary Statutory Tag(s) | Statutory Clause & Legal Mandate | Verification & Audit Evidence |
|---|---|---|---|
| **FR-001** | SDA, SETAREG | SETA Grant Regulations (2012) Reg 4(1): Mandatory annual submission window closes strictly on 30 April. | Rejected submission error logs; audit snapshot in `audit_logs`. |
| **FR-002** | SDA, SETAREG, PFMA | SETA Grant Regulations (2012) Reg 4(2) & PFMA §38(1)(a)(i): Two-tier maker-checker extension capped at 31 May. | Two-tier approval history; distinct Reviewer and Approver user IDs. |
| **FR-003** | SDLA, PFMA | SDLA 1999 & PFMA §38: Discretionary Grant gazetted window governance and dual authorization control. | Window opening audit snapshot; segregation of duties validator. |
| **FR-004** | SDLA, PFMA | SDLA 1999 Reg 6: Consolidated Discretionary Grant contracting under a single Memorandum of Agreement (MoA). | Single MoA contract generation; line item rollup reconciliation. |
| **FR-005** | SDA, QCTO, NAMB | SDA 1998 §26D & NAMB Regulations: Artisan trade test registration milestone and serial number issuance. | Generated serial number format `TT-SER-{yyyy}-{id:D5}` on document snapshot. |
| **FR-006** | SDA, NAMB | NAMB ARPL & Trade Test Specification 2023: 17 designated trade toolkit whitelist and 50% task credit retention. | Validation gate logs; historical task partitioning by attempt number. |
| **FR-007** | SDA, QCTO, NAMB | NAMB Workplace Approval Guidelines: Artisan mentor-to-apprentice ratio policy engine (1:4 standard cap). | Policy evaluation logs; cascading precedence audit trail. |
| **FR-008** | SDA, POPIA, SETMIS | SDA 1998 §16, POPIA §14, SETMIS File 500: Tripartite agreement execution and 13-digit RSA ID checksum validation. | Tripartite agreement signatures; RSA Luhn algorithm validation test. |
| **FR-009** | POPIA, PFMA | POPIA §14 & PFMA §38: Universal QuestPDF 2D barcode verification seal and confidential RSA ID masking. | Truncated SHA-256 digital security seal prefix; masked ID display (`9504******082`). |
| **FR-010** | SDLA, PFMA | SDLA 1999 §3 & PFMA §38: Monthly SARS levy file streaming, digital security seal check, and set-based bulk staging. | Constant-memory streaming log; staging ledger checksum reconciliation. |
| **FR-011** | SDA, SETMIS | DHET SETMIS Positional Specification: 11 statutory flat files matching exact character widths. | Fixed character width test suite; SHA-256 batch digital security seal. |
| **FR-012** | SDA, NLRD | SAQA NLRD Edu.Dex Standard: Supplier Code 599 extract compilation with statutory HEADER599 prefix. | Edu.Dex file validator logs; byte length verification records. |
| **FR-013** | PFMA | PFMA §38(1)(b): Institutional public holiday and merSETA closure tracking with dynamic officer SLA pausing. | NonWorkingDay calendar records; SLA pause computation audit trail. |
| **FR-014** | PFMA, POPIA | PFMA §50 & POPIA §11: Mandatory annual conflict of interest disclosures and institutional shareholder scans. | Declaration audit log; meeting conflict detection matrix. |
| **FR-015** | PFMA | PFMA §38(1)(a): Open Knowledge Format (OKF) attested computation and T-SQL formula attestation audit. | Attestation event records; signed concept verification history. |
