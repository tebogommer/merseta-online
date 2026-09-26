---
name: application-controls
description: >-
  Conducts code-level and schema-level statutory public-sector audits (AGSA, COBIT 2019, ISO 27001)
  across the Top 20 Core Business Application Controls (BACs: AC-01 to AC-20) and generates
  audit-ready 5-table evidentiary compliance dossiers with native in-app test proofs.
---

# Top 20 Core Business Application Controls (BAC) Audit & Implementation Skill

Use this skill when:
1. Conducting statutory IT audit readiness reviews (AGSA, COBIT DSS06/BAI03, ISO/IEC 27001 Annex A.8, PFMA Sec 38/51).
2. Implementing or reviewing enterprise controls across financial, statutory, or grant-management workflows.
3. Designing database constraints, audit logs, DDM masking, and export governance.

---

## The Top 20 Core Controls Matrix (AC-01 through AC-20)

1. **AC-01: Segregation of Duties (Maker-Checker / Dual Authorization)**
   - Backend service invariant (`MakerId != CheckerId`) + physical database CHECK constraint (`CHECK (SecondSignoffUserId IS NULL OR CreatedBy <> SecondSignoffUserId)`).
2. **AC-02: Input & Boundary Validation**
   - Server-side DTO validation, regex matching, 13-digit RSA National ID Luhn Modulus 10 checksum validation (`RsaIdValidator`).
3. **AC-03: Automated Duplicate Detection & Collision Prevention**
   - Composite unique database indexes (`sys.indexes`) on natural business keys (`SdlNumber`, `ApplicationNumber`, `BatchRef`) + API idempotency tables.
4. **AC-04: Delegation of Authority (DoA) & Financial Threshold Enforcements**
   - Role-based monetary limits (`FinancialApprovalThreshold`); transactions $\ge R500,000.00$ or exceeding role limits automatically routed to `PendingCfoApproval`.
5. **AC-05: Immutable Transaction Audit Trail (Double-Write)**
   - Atomic double-write (`IAtomicAuditTransactionManager.ExecuteAtomicAsync`); ISO 9001 Clause 7.5 positive RecordId requirement; DPSA anonymous actor ban; SHA-256 digital security seal.
6. **AC-06: Master & Reference Data Change Governance**
   - System-Versioned Temporal Tables with `PERIOD FOR SYSTEM_TIME` routing historical deltas to `[history].[TableName_History]`; dual authorization on banking changes.
7. **AC-07: Processing Integrity & ACID Transaction Safety**
   - Wrap multi-table updates in `db.Database.CreateExecutionStrategy().ExecuteAsync(...)`; rollback catches and clears ORM state via `ChangeTracker.Clear()`.
8. **AC-08: Automated Calculation Integrity & Anti-Tamper Logic**
   - Backend-only financial calculations; statutory Hare-Niemeyer largest-remainder split algorithm producing zero-cent variance.
9. **AC-09: Unbroken Sequential Numbering & Gap Detection**
   - Monotonic atomic sequences (`seq_NonLevyOrganisationNumber`); statutory certificate formulas (`17` + ID middle 4 + 6 digits).
10. **AC-10: Batch Ingestion, Staging & Interface Reconciliation**
    - High-throughput `SqlBulkCopy` into staging tables (`dbo.SarsLevyStaging`); control total reconciliation (line count + sum total) before ledger commit.
11. **AC-11: Automated Error Handling, Exception Queues & Suspense Tracking**
    - Transactional Outbox pattern (`ErpOutboxMessage`); dead-letter queue isolation with automated real-time alerts to `FinanceAdmin` and `DEAD_LETTER_THRESHOLD_ESCALATION` audit logs.
12. **AC-12: Row-Level / Multi-Tenant Isolation & Anti-IDOR**
    - EF Core Global Query Filters (`HasQueryFilter(e => e.OrganisationId == _tenantProvider.CurrentOrganisationId)`).
13. **AC-13: Data Masking & PII Protection (POPIA / Data Privacy)**
    - In-app redaction utility (`PopiaMaskingUtility`) for RSA IDs, bank accounts, and phone numbers; SQL Server native Dynamic Data Masking (DDM) for data at rest.
14. **AC-14: Concurrency Control & Optimistic Locking**
    - `RowVersion` (`byte[]` / `[Timestamp]`) tokens; `DbUpdateConcurrencyException` handling.
15. **AC-15: State-Machine Lifecycle & Workflow Transition Gates**
    - Transition matrix enforcement via `WorkflowEngineService`; illegal state skipping or unauthorized rollbacks rejected.
16. **AC-16: Mandatory Relational Integrity & Orphan Prevention**
    - Foreign keys with `Restrict`/`Cascade`; mandatory parent links (e.g. `VisitService` enforcing verified `ContactPersonId > 0`).
17. **AC-17: Output & Export Governance (DLP & Watermarking)**
    - 2D QR barcode security seals on QuestPDF documents; CSV exports embedded with `# merSETA CONFIDENTIAL - Exported by {user} on {timestamp}` and logged as `EXPORT_DATASET` audit records.
18. **AC-18: Accounting Period Cut-Off & Financial Lock Controls**
    - Fiscal calendar locks (`FinancialYear.IsClosed = 1`); statutory submission deadlines (30 April per Regulation 4(1)) gating intake.
19. **AC-19: Database Resilience, Deadlock Retries & Circuit Hygiene**
    - Transient deadlock retry policies; `ChangeTracker.Clear()` on rollback; SQL Server RCSI enabled (`is_read_committed_snapshot_on = 1`) and `AUTO_CLOSE OFF`.
20. **AC-20: Secure File Upload & Attachment Validation**
    - Strict file extension whitelisting, regex sanitization against path traversal (`..`), SHA-256 hash storage, preamble magic-byte sniffing (`FileFormatSniffer`).

---

## Audit Dossier Generation Format (5 Mandatory Tables)

When requested to perform an Application Controls Audit, always produce the 5 official Markdown tables:
1. **TABLE 1: Master Control Design & Assurance Matrix (Test of Design - ToD)**
   - Headers: `Control Ref | Application Control Name | Primary Audit Risk Addressed | Framework Mapping (AGSA / COBIT / ISO) | Control Nature | Status | Code & Schema Evidence | Design Evaluation & Findings Summary`
2. **TABLE 2: Operating Effectiveness Testing (ToE) & Proof Log**
   - Headers: `Control Ref | Test Case Name & Scenario | Test Type | Test Input Payload / Action | Expected System Response | Actual Code Invariant / Test Assertion | Test Result`
3. **TABLE 3: Native Database Verification & Evidence Extraction Scripts**
   - Headers: `Control Ref | Verification Objective | Target Entity / Table | Native Runnable SQL / CLI Script | Expected Audit Output`
4. **TABLE 4: Evidentiary Artifact Register (Auditor Population Index)**
   - Headers: `Artifact ID | Control Ref | Artifact Description | Generating System Component | Native Storage / Log Destination | Purpose in Audit Working Papers`
5. **TABLE 5: Native Remediation & Gap Closure Roadmap**
   - Headers: `Priority | Control Ref | Identified Deficiency & Threat | Target File / Class / Entity | Native Remediation Implemented & Status | Verification Proof & Residual Risk`
