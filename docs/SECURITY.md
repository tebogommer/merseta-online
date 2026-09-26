# merSETA NSDMS — Information Security Policy & Security Architecture

**Document Reference:** SEC-NSDMS-2026-V1  
**Statutory Frameworks:** COBIT 2019 (APO13, DSS05), ISO/IEC 27001:2022 (Clause 5.1, 6.3, Annex A.5/A.8), Protection of Personal Information Act (POPIA Act 4 of 2013), DPSA Information Security Directive  
**Effective Date:** 2026-04-01  
**Last Review:** 2026-09-24  

---

## 1. Information Security Policy Purpose & Scope (ISO 5.1 / COBIT APO13)

This policy establishes the mandatory technical, procedural, and cryptographic security controls for the merSETA National Skills Development Management System (NSDMS). It applies to all application components, microservices, databases, interfaces, background workers, and administrative operations.

---

## 2. Core Security Architecture Principles & Design Decisions

The NSDMS architecture is founded on zero-trust and defense-in-depth principles:

### 2.1 Identity & Access Management (ISO 5.15, 5.18)
- **Zero Anonymous Actors:** Every database mutation, workflow transition, and report generation must identify a verified authenticated actor (`Actor != "ANONYMOUS"` and `Actor != "UNKNOWN"`).
- **Separation of Person and User:** Demographics (`Person`) are cleanly separated from system credentials (`User`).
- **Role-Based Access Control (RBAC):** Strict least-privilege role assignment (`SysAdmin`, `FinanceAdmin`, `ExecutiveManager`, `QualityAssuror`, `ClientServices`, `EmployerUser`, `SdpUser`).
- **External IdP / MFA Integration Readiness:** Application provides hook points (`IMfaProviderHook`) to integrate with enterprise Identity Providers (Microsoft Entra ID, Okta) and enforce multi-factor authentication for high-privilege roles and disbursements exceeding R500,000.

### 2.2 Segregation of Duties (SoD) & Maker-Checker Enforcements (ISO 5.3)
- Invariant: A user cannot approve their own submission (`CreatedBy != ApproverUserId` / `MakerId != CheckerId`).
- Physical database `CHECK` constraints prevent single-actor signoffs on critical workflows (Banking details, Grant MoAs, Discretionary claims).

### 2.3 POPIA Sensitive PII Protection & Data Minimization (ISO 8.11 / POPIA Sec 19)
- **In-App Masking:** 13-digit RSA National ID numbers are masked to retain only birth year and gender markers (`880101****082`), and banking account numbers are masked before persisting to `MetadataJson` audit logs via `PopiaMaskingUtility`.
- **Database Dynamic Data Masking (DDM):** SQL Server native masking functions applied to sensitive columns.

### 2.4 Cryptographic Integrity & Tamper-Evident Records (ISO 8.24)
- **Digital Security Seals:** Every audited transaction computes a cryptographic SHA-256 hash (`ComputeDigitalSecuritySeal`) across the payload, record ID, and actor.
- **Append-Only Immutability:** Audit records are strictly append-only. Application interceptors forbid SQL `UPDATE` and `DELETE` on all operational and audit tables.

### 2.5 Secure Communication & Transport (ISO 8.20, 8.26)
- Mandatory HTTPS / TLS 1.3 for all web and API traffic.
- Secure transport protocol enforcement for external batch feeds (SFTP over SSH on port 22 for SARS file transfers; unencrypted FTP on port 21 is programmatically blocked).

---

## 3. Incident Management & Vulnerability Reporting Process (ISO 5.24, 6.3)

### 3.1 Security Point of Contact
- **Security Operations Center / CISO Office:** `security@merseta.org.za`
- **Hotline / Incident Escalation:** +27 (0)11 551 5200 (Ext: Cyber Security)
- **Physical Location:** merSETA Head Office, 8 Hillside Road, Metropolitan Park, Parktown, Johannesburg

### 3.2 Reporting Vulnerabilities (Coordinated Disclosure)
Any staff member, auditor, or external security researcher who discovers a potential vulnerability or security weakness in the NSDMS must:
1. Immediately email `security@merseta.org.za` with the subject `[VULNERABILITY REPORT] NSDMS - {Brief Description}`.
2. Provide reproducible proof-of-concept steps, affected endpoints, and parameter details.
3. Not disclose the vulnerability publicly until a fix has been deployed and verified by the merSETA ICT Security Committee.
4. All reported incidents are triaged within 4 hours and assigned a severity level (P0 Critical, P1 High, P2 Medium, P3 Low) per the Incident Management Standard.
