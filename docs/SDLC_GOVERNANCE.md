# merSETA NSDMS — SDLC Governance, Definition of Done & Quality Gates

**Document Reference:** SDLC-NSDMS-2026-V1  
**Statutory Frameworks:** COBIT 2019 (BAI01, BAI02, BAI03, BAI06), ISO/IEC 27001:2022 (Clause 5.8, 8.25, 8.29, 8.31), AGSA Information Systems Audit Guidelines  
**Effective Date:** 2026-04-01  
**Last Review:** 2026-09-24  

---

## 1. SDLC Policy & Phased Methodology (COBIT BAI01 / ISO 8.25)

The NSDMS engineering process follows a disciplined, automated Clean Architecture lifecycle governed by continuous automated testing and statutory assurance gates:

```
[Requirements & Acceptance Criteria] 
         ↓
[Design & Architectural Review (ADR)] 
         ↓
[Clean Code Implementation & Automated Unit/Integration Tests]
         ↓
[Security & Application Controls Static Analysis]
         ↓
[User Acceptance Testing (UAT) & Sign-Off]
         ↓
[Change Advisory Board (CAB) & Automated Deployment]
```

---

## 2. Requirements & Acceptance Criteria Standard (COBIT BAI02)

Every feature, enhancement, or bug fix must have:
1. **User Story with Statutory Rationale:** Clear citation of governing South African legislation (Skills Development Act, PFMA, POPIA, SARS Regulations).
2. **Explicit Acceptance Criteria:** Given-When-Then behavioral specifications detailing both normal operating paths and edge cases (boundary limits, negative inputs, concurrency).
3. **Audit Trail Requirements:** Specification of every entity mutation and state transition that must write to `dbo.AuditLog` or `dbo.WorkflowHistory`.

---

## 3. Definition of Done (DoD) Incorporating Security & Audit Requirements (ISO 8.25 / 8.29)

No work item is marked "Done" or eligible for production promotion unless all the following criteria are satisfied:

| Checkpoint | Mandatory Verification Standard | Evidence Artifact |
| :--- | :--- | :--- |
| **Clean Architecture & Standards** | Conforms to .NET 10 LTS, MudBlazor Stacked Master-Detail UI pattern, singular PascalCase table naming, and 64-bit auto-generated IDs. | Code Review & Roslyn Ast Auditor |
| **Audit Double-Write Integrity** | Domain mutations wrapped in `IAtomicAuditTransactionManager.ExecuteAtomicAsync`; zero partial commits allowed. | Unit test proof: `IsoDpsaAuditComplianceAndZeroPartialCommitTests` |
| **POPIA Sensitive Data Protection** | 13-digit RSA National ID numbers and bank account numbers are redacted in JSON snapshots before storage. | Unit test proof: `PopiaMaskingAndAuditRedactionTests` |
| **Append-Only Immutability** | Audit records protected by EF Core interceptors against `UPDATE` and `DELETE`. | Interceptor: `AuditableEntityInterceptor` |
| **Zero Anonymous Actors** | Rejection of `"ANONYMOUS"`, `"UNKNOWN"`, or empty actor strings on all state mutations. | Unit test proof: DPSA Invariant Tests |
| **Automated Test Suite** | 100% of existing and new automated unit and integration tests passing (`dotnet test`). | Test Run Report |
| **Static Code Quality** | Zero compiler errors, zero unhandled warnings, Roslyn AST checks passed. | Build Log |

---

## 4. User Acceptance Testing (UAT) & Change Authorization (COBIT BAI03 / ISO 8.31)

1. **UAT Evidence Recording:** All business-critical workflows (Levy Reconciliation, Workplace Approvals, WSP Submissions, Tranche Disbursements) are verified by business users in the Staging environment.
2. **Pre-Release Sign-Off:** Written or digital approval from the Business Process Owner (CFO or Operations Executive) is archived prior to deployment.
3. **Change Advisory Board (CAB) Governance:** Production releases require a registered CAB ticket referencing code commit hashes, test execution logs, and rollback procedures.
