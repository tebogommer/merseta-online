# Statutory Compliance Register — merSETA NSDMS

> **Companion Document to REQUIREMENTS.md**  
> **Schema Version:** 3.0  
> **Register Revision:** 1.2  
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
| **FR-010** | SDLA, PFMA | SDLA 1999 §3 & PFMA §38: Monthly SARS levy file reactive streaming pipeline, digital seal check, and set-based bulk staging. | Constant-memory streaming log; staging ledger checksum reconciliation. |
| **FR-011** | SDA, SETMIS | DHET SETMIS Positional Specification: 11 statutory flat files matching exact character widths. | Fixed character width test suite; SHA-256 batch digital security seal. |
| **FR-012** | SDA, NLRD | SAQA NLRD Edu.Dex Standard: Supplier Code 599 extract compilation with statutory HEADER599 prefix. | Edu.Dex file validator logs; byte length verification records. |
| **FR-013** | PFMA | PFMA §38(1)(b): Institutional public holiday and merSETA closure tracking with dynamic officer SLA pausing. | NonWorkingDay calendar records; SLA pause computation audit trail. |
| **FR-014** | PFMA, POPIA | PFMA §50 & POPIA §11: Mandatory annual conflict of interest disclosures and institutional shareholder scans. | Declaration audit log; meeting conflict detection matrix. |
| **FR-015** | PFMA | PFMA §38(1)(a): Open Knowledge Format (OKF) attested computation and T-SQL formula attestation audit. | Attestation event records; signed concept verification history. |
| **FR-016** | SDLA, PFMA | SDLA 1999 & PFMA §38(1)(j): Discretionary Grant MoA contracting, 4-tier milestone verification, and tranche disbursement. | Milestone verification audit log; dual authorization signoff on tranche payments. |
| **FR-017** | SDLA, PFMA | SDLA 1999 & PFMA §38: Discretionary Grant contract variation management (addenda, extensions, clawback terminations). | Addendum approval records; segregation of duties logs between CLO and Executive. |
| **FR-018** | PFMA | PFMA §38(1)(a)(i): Employer banking details verification, Bankserv AVS validation, and 14-day statutory cooling-off. | Bankserv AVS verification response; cooling-off expiry timestamp logs. |
| **FR-019** | SDA, NAMB | SDA 1998 §28 & NAMB ARPL Guidelines 2023: ARPL Section 28 assessment workflow and 50% task credit retention. | Toolkit eligibility verification logs; multi-attempt task credit history. |
| **FR-020** | SDA, QCTO | SDA 1998 §26H & QCTO Assessment Policy: Summative assessment EISA capture, external moderation, and SOR issuance. | Moderation sample records; POPIA-compliant Statement of Results issuance logs. |
| **FR-021** | SDA, NLRD | SDA 1998 & SAQA Policy: Non-SETA qualification verification, cross-SETA endorsement, and NLRD routing. | Cross-SETA endorsement records; NLRD verification query audit logs. |
| **FR-022** | SDA, QCTO | SDA 1998 §26 & QCTO ETQA Guidelines: Assessor and moderator registration, scope accreditation, and 3-year tenure renewal. | Committee approval audit snapshot; credential expiry tracking records. |
| **FR-023** | PFMA | PFMA §38 & §44: Institutional financial approval delegation tiers and executive CFO dual-signoff escalation. | Delegated authority threshold checks; CFO approval timestamps on high-value batches. |
| **FR-024** | PFMA | PFMA §38(1)(a): Universal BPM workflow state machine engine with decoupled status synchronization and audit logging. | State transition logs; dual-write audit records with before/after state snapshots. |
| **FR-025** | PFMA | PFMA §38(1)(a): Hierarchical fiscal calendar quarters contiguity, leap-year days, and revision locking. | Quarter contiguity validation test logs; temporal fiscal year revision history. |
| **FR-026** | SDA, QCTO | SDA 1998 §17 & QCTO Workplace Standards: Workplace approval audit inspection, monitoring visit tracking, and mentor capacity. | Verified contact person relational links; site visit report snapshots. |
| **FR-027** | SDA, SDLA, PFMA | SDLA 1999 §3 & SDA 1998 §9: Inter-SETA Transfer (IST) intake, SARS Chamber re-allocation, and financial journal settlement. | DHET approval references; inter-SETA ledger balancing transactions. |
| **FR-028** | PFMA | PFMA §38(1)(a)(i): Claims-based role access control (RBAC), CASL ability evaluation, and temporal administrative delegation. | CASL ability rule evaluation audit records; temporal delegation permission logs. |
| **FR-029** | SDLA, PFMA | SDLA 1999 §3 & PFMA §38: Organisation 360-degree profile hub, legal form registration, and parent-subsidiary linking. | Organisation profile audit snapshots; Chamber affiliation and SIC code records. |
| **FR-030** | SDA, QCTO | SDA 1998 §17 & QCTO Accreditation Policy: Skills Development Provider accreditation, multi-campus governance, and disciplinary sanctions. | Provider accreditation workflow logs; satellite campus audit evidence; sanction records. |
| **FR-031** | PFMA | PFMA §38(1)(j): Dynamics GP ERP financial voucher transmission via transactional outbox with retry backoff. | Transactional outbox dispatch logs; dead-letter queue records; ERP sync audit snapshots. |
| **FR-032** | PFMA | PFMA §38: Enterprise document template studio, single-active version invariant, and QuestPDF rendering. | Template version activation transaction logs; QuestPDF binary generation records. |
| **FR-033** | PFMA | PFMA §38: Enterprise broadcast circular communication, throttled delivery, and Microsoft 365 daily ceiling governance. | Email outbox dispatch logs; rate-limiting token bucket metrics; quota ceiling logs. |
| **FR-034** | SDA, SETAREG | SETA Grant Regulations (2012) Reg 4: Workplace Skills Plan 60% training plan deviation detection and small employer exemption. | Training deviation calculation logs; small employer (<50 employees) sign-off audit records. |
| **FR-035** | SDA, QCTO | SDA 1998 §17 & QCTO Standards: Periodic workplace monitoring audits, equipment inspection, and corrective action plans. | Monitoring visit audit reports; health and safety inspection evidence; deficiency resolution logs. |
| **FR-036** | PFMA | PFMA §38(1)(b): Regional operations zoning, municipal district mapping, and officer caseload rebalancing. | Municipal zone allocation logs; officer caseload variance metrics; rebalancing history. |
| **FR-037** | PFMA | PFMA §38: Dynamic business rules execution, cascading system configuration overrides, and feature flag management. | Dynamic configuration override audit logs; feature flag evaluation test records. |
| **FR-038** | SDLA, PFMA | SDLA 1999 §3 & PFMA §38: Standard Industrial Classification (SIC) chamber sector derivation and precision Hare-Niemeyer levy splits. | Precision levy split calculation ledgers; 20% MG / 49.5% DG reconciliation records. |
