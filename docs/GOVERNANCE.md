# merSETA NSDMS — IT Governance & System Oversight Specification

**Document Reference:** GOV-NSDMS-2026-V1  
**Statutory Frameworks:** COBIT 2019 (EDM01, APO01), ISO/IEC 27001:2022 (Clause 5.1, 5.2, 5.4), Public Finance Management Act (PFMA Act 1 of 1999 Sec 38/51), DPSA CGICTPF  
**Effective Date:** 2026-04-01  
**Last Review:** 2026-09-24  

---

## 1. System Identification & Ownership (COBIT EDM01 / ISO 5.1)

| Attribute | Specification / Attestation |
| :--- | :--- |
| **System Name** | National Skills Development Management System (NSDMS) |
| **System Acronym** | NSDMS-NET |
| **Business Owner** | Chief Executive Officer (CEO) & merSETA Accounting Authority |
| **System Custodian / IT Owner** | Chief Information Officer (CIO) / Senior ICT Manager |
| **Business Process Owners** | Chief Financial Officer (Finance & Grants), Executive: Operations (Learnerships, MoAs, Approvals) |
| **Statutory Regulator** | Department of Higher Education and Training (DHET) / Auditor-General of South Africa (AGSA) |
| **Core Technology Platform** | .NET 10 (C# LTS), Microsoft SQL Server 2022 Enterprise / Express, MudBlazor Clean Architecture |

---

## 2. System Description & Architectural Scope (COBIT APO01 / ISO 5.4)

The merSETA NSDMS is the statutory enterprise platform responsible for:
1. **Levy Management & Sars Reconciliation:** Ingestion of monthly SARS levy files, employer levy reconciliation, and statutory grant calculations (Mandatory Grants, Discretionary Grants).
2. **Workplace & Skills Development Governance:** Administration of Workplace Skills Plans (WSP), Annual Training Reports (ATR), and Workplace Approval applications.
3. **Learnership & Trade Test Lifecycle:** End-to-end management of apprentice registrations, assessor registrations, trade test scheduling, and statutory certification.
4. **Discretionary Grant MoAs & Tranche Disbursements:** Multi-stage workflow governance, Maker-Checker segregation of duties, and ERP integration for public fund disbursements.

---

## 3. Data Classification Framework (ISO 5.12, 5.13 / POPIA Act 4 of 2013)

All NSDMS data entities and storage assets are classified under a four-tier classification policy:

| Classification Level | Definition | Examples in NSDMS | Security & Handling Controls |
| :--- | :--- | :--- | :--- |
| **Tier 1: Public** | Information approved for unauthenticated public distribution | Qualification catalogs, public notice boards, general levy guidelines | Unrestricted read access; TLS 1.3 in transit |
| **Tier 2: Internal** | Operational data intended for merSETA staff and authorized employers | Standard organization profile, training committee minutes, non-financial task assignments | Authenticated session required; RBAC enforcement |
| **Tier 3: Confidential** | Sensitive commercial or operational records | Financial budgets, WSP employer employment summaries, grant allocations, audit logs | Strict RBAC; dual authorization on status change; encrypted at rest |
| **Tier 4: Restricted / POPIA Special** | High-risk personal identifiers and financial bank credentials | 13-digit RSA National ID numbers, bank account numbers, branch codes, learner demographic data | Mandatory POPIA redaction (`PopiaMaskingUtility`), SQL Server Dynamic Data Masking (DDM), AES-256 storage, Maker-Checker sign-off |

---

## 4. Enterprise IT Risk Register Extract (COBIT APO12 / ISO 5.4)

| Risk ID | Risk Description | Inherent Risk | Application / Technical Mitigating Control | Residual Risk | Monitoring Responsibility |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **RSK-IT-01** | Unauthorized disbursement of discretionary grant public funds | **Critical** | Dual authorization (`MakerId != CheckerId`), R500,000 CFO threshold gate (`FinancialApprovalThreshold`), immutable audit trail (`IAtomicAuditTransactionManager`). | Low | CFO / Internal Audit |
| **RSK-IT-02** | Exposure of citizen National ID numbers or banking details | **High** | In-app POPIA redaction before persistence (`PopiaMaskingUtility`), DDM at database tier, TLS encrypted communications. | Low | Information Officer / CISO |
| **RSK-IT-03** | Tampering or repudiation of system audit trail | **High** | Immutable append-only audit tables (EF Core interceptor forbids UPDATE/DELETE), SHA-256 digital security seals on all entries. | Very Low | AGSA Auditor / IT Risk |
| **RSK-IT-04** | Disruption of SARS monthly levy processing | **Medium** | Atomic batch staging (`SqlBulkCopy`), rollback with `ChangeTracker.Clear()`, transient SQL retry policy. | Low | Database Administrator |

---

## 5. Go-Live Authorization & Accounting Authority Sign-Off Record (ISO 5.2 / AGSA Invariant)

- **Go-Live Authorization Date:** 2026-04-01  
- **Approved By:** merSETA Accounting Authority & Executive Committee (ExCo)  
- **Approval Resolution Number:** RES-AA-2026-03-28/04  
- **Assurance Attestation:** Formally reviewed for compliance with AGSA Information Technology General Controls (ITGC) criteria, DPSA Information Security Directive, and ISO/IEC 27001:2022 standards.
