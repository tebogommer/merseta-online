# Project Plan: Learner Application Registration Alignment
**File:** docs/PLAN-learner-registration.md  
**Task Slug:** learner-registration  
**Reference Document:** Learner Application Registration ALL Programmes Use Case_Final.docx  
**Status:** Planned (Awaiting User Review / Approval)

---

## 1. Context & Objectives
This plan outlines the end-to-end implementation of recommendations from the architectural assessment of merSETA NSDMS 2.0 Learner Application Registration against the statutory specification.

### Key Deliverables:
1. **Domain & Database:** PersonGuardian entity, LearnerRegisteredUnitStandard entity, CompanyLearner statutory extensions (LearnerSignatureDate, SubmissionDate, WithdrawalReasonCode, etc.), and idempotent T-SQL migration (V2026_21_Phase5_Learner_Registration_Alignment.sql).
2. **Statutory Workflow:** Upgrade workflow LRN in WorkflowDefinitionSeeder to the full 8-state Maker-Checker model (DRAFT, SUBMITTED, REJECTED_RESUBMIT, RESUBMITTED, RECOMMENDED, REGISTERED, REJECTED, WITHDRAWN).
3. **Validation & Business Rules:** Enforce the 30-working-day submission deadline, minor (< 18) guardian mandate, anti-placeholder string sanitization, and qualification validity check (LastDateForEnrolment).
4. **UI/UX Workbenches:** Update LearnerAgreementRegistrationWizard.razor to support all 7 learning programmes, dynamic guardian step, programme-specific document gating, and withdrawal action in LearnerDetail.razor.

---

## 2. Task Breakdown & Agent Assignments

### Phase 1: Database & Domain Layer
- **Agent:** database-architect
- **Tasks:**
  - Create dotnet/Nsdms.Domain/Entities/PersonGuardian.cs.
  - Create dotnet/Nsdms.Domain/Entities/LearnerRegisteredUnitStandard.cs.
  - Add statutory fields to dotnet/Nsdms.Domain/Entities/CompanyLearner.cs.
  - Add non-employer entity support to dotnet/Nsdms.Domain/Entities/Organisation.cs.
  - Configure EF Core mappings in dotnet/Nsdms.Infrastructure/Data/NsdmsDbContext.cs.
  - Author idempotent T-SQL DDL script dotnet/Nsdms.Infrastructure/Data/SqlScripts/V2026_21_Phase5_Learner_Registration_Alignment.sql.
  - Create migrator dotnet/Nsdms.Infrastructure/Data/Phase21LearnerRegistrationAlignmentMigrator.cs.

### Phase 2: Workflow Engine & State Matrix
- **Agent:** ackend-specialist
- **Tasks:**
  - Redefine workflow code LRN in dotnet/Nsdms.Infrastructure/Data/WorkflowDefinitionSeeder.cs with the 8 statutory states and role-gated transitions.
  - Add withdrawal and remediation transitions with required comments and state rollbacks.
  - Wire automated workflow notifications to Reviewer and Approver roles.

### Phase 3: Statutory Validation & Services
- **Agent:** ackend-specialist / security-auditor
- **Tasks:**
  - Update dotnet/Nsdms.Application/Validation/PersonDomainValidator.cs with anti-placeholder sanitization (%UNKNOWN%, %AS ABOVE%, etc.), 10-digit telephone regex (^0\d{9}$), and email validation.
  - Update dotnet/Nsdms.Application/Validation/CompanyLearnerDomainValidator.cs with 30-working-day validation, minor guardian validation, and Candidacy / AET programme rules.
  - Implement IQualificationEnrolmentGatekeeperService in dotnet/Nsdms.Application/Services/.
  - Extend dotnet/Nsdms.Application/Services/LearnerService.cs with WithdrawLearnerApplicationAsync, ResubmitLearnerApplicationAsync, and update gating.

### Phase 4: UI/UX Wizard & Master-Detail Components
- **Agent:** rontend-specialist
- **Tasks:**
  - Update LearnerAgreementRegistrationWizard.razor to handle all 7 learning programmes, dynamic guardian step when age < 18, and document checklist gating.
  - Update LearnerAgreementRegistrationFields.razor with postal address toggle, guardian inputs, and unit standards selector.
  - Update LearnerDetail.razor to display guardian data, handle withdrawal reasons dialog, and lock edits post-approval.

### Phase 5: Verification & Quality Assurance
- **Agent:** 	est-engineer
- **Tasks:**
  - Author comprehensive xUnit test suite dotnet/Nsdms.Tests/LearnerApplicationAlignmentTests.cs.
  - Validate all 22 assessment criteria with passing tests.
  - Verify build without errors.

---

## 3. Verification Checklist
- [ ] Database migrator applies idempotently to SQL Server.
- [ ] Minor age < 18 automatically activates the Guardian capture step.
- [ ] 30-working-day submission check flags overdue submissions.
- [ ] Anti-garbage address sanitizer rejects placeholder strings.
- [ ] Candidacy programme allows unlisted qualifications with valid council reference.
- [ ] AET Level 1-3 relaxes SAQA ID requirement; AET Level 4 mandates it.
- [ ] Full 8-state workflow transitions pass all transition guards.
- [ ] Application withdrawal records reason code and audit log entry.
- [ ] All unit tests in Nsdms.Tests execute with 0 failures.