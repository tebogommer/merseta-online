# Workplace Approval Specification Alignment (Spec NMok_19122022)

Align the NSDMS Workplace Approval subsystem with the signed merSETA specification:
`Workplace Approval Application Use Case_19122022.NMok.signed.pdf` (Reference: `MerSeta\NSDMS\LMS\LR\01 – Workplace Application – Use Case\01`).

## User Review Required

> [!IMPORTANT]
> **Zero Feature Loss Guarantee:**
> All existing advanced features currently implemented in the codebase MUST BE 100% PRESERVED without regression:
> 1. **Live NAMB 1:4 Mentor-to-Apprentice Ratio Policy Engine** (`IMentorRatioPolicyEngine`, `TradeMentorRatioPolicy`, tiered cascading overrides, utilization progress bar, warnings).
> 2. **360-Degree Relational Detail Hub**:
>    - Placed Apprentices & Learners tab (`GetPlacedLearnersAsync`)
>    - Partnering Skills Development Providers (SDP) tab (`GetPartnerSdpsAsync`)
>    - Physical Site Verification Visits tab (`GetVerificationVisitsAsync`)
>    - Workshop Tools & Machinery Inventory tab (`WorkplaceApprovalToolList`, compliant/deficient chips)
>    - Document & Evidence Vault tab (`DocumentVault.razor`)
>    - Visual Workflow Step Tracker (`WorkflowProgressBar`) & Workflow Action Bridge (`WorkflowActionBridge`)
>    - Complete Workflow History Timeline (`WorkflowTimeline`)
> 3. **Mandatory Contact Person Linkage** (`ContactPersonId`) on all employer visits.
> 4. **Wizard Draft Persistence & Resume Engine** (`IWizardDraftService`, draft key tracking, auto-save, time-ago indicators).
> 5. **Double-Write Audit Logging** (`IAuditService`) with structured change snapshots.

## Proposed Changes

### 1. Domain Entities & Database Schema (`Nsdms.Domain` & `Nsdms.Infrastructure`)

Extend `WorkplaceApproval` in a clean, non-breaking manner to support Section 6 of the spec:

#### [MODIFY] [WorkplaceApproval.cs](file:///c:/Antigravity/nsdms-2026-04-01/nsdms/MerSETA/dotnet/Nsdms.Domain/Entities/WorkplaceApproval.cs)
- Add `LearningProgramTypeCode` (string: `Apprenticeship`, `Learnership`, `InternshipNDiploma`, `OccupationalQual`, `Candidacy`, `SkillsProgramme`).
- Add `RequiresWorkplaceApproval` (bool).
- Add CLO verification attributes (Section 6.2):
  * `IsSiteVisitRequired` (bool?).
  * `SiteVisitJustification` (string?).
  * `InspectionDueDate` (DateTime? - 20 working days SLA clock).
  * `CloRecommendationReasonId` (int?), `CloRecommendationExplanation` (string?).
  * `CloRejectionReasonId` (int?), `CloRejectionExplanation` (string?).
  * `CloVerifiedDate` (DateTime?), `CloPersonId` (int?).
- Add QA approval attributes (Section 6.3):
  * `QaApprovalReasonId` (int?), `QaApprovalExplanation` (string?).
  * `QaRejectionReasonId` (int?), `QaRejectionExplanation` (string?).
  * `QaApprovedDate` (DateTime?), `QaPersonId` (int?).
- Add Non-merSETA support:
  * `IsNonMerSetaCompany` (bool), `HomeSetaName` (string?), `HomeSetaAgreementRef` (string?).

#### [NEW] [V2026_16_WorkplaceApproval_Spec_Alignment.sql](file:///c:/Antigravity/nsdms-2026-04-01/nsdms/MerSETA/dotnet/Nsdms.Infrastructure/Data/SqlScripts/V2026_16_WorkplaceApproval_Spec_Alignment.sql)
- Idempotent T-SQL DDL script adding columns and indexes to `WorkplaceApproval`.

---

### 2. Workflow & Application Service Layer (`Nsdms.Application` & `Nsdms.Infrastructure`)

#### [MODIFY] [WorkflowDefinitionSeeder.cs](file:///c:/Antigravity/nsdms-2026-04-01/nsdms/MerSETA/dotnet/Nsdms.Infrastructure/Data/WorkflowDefinitionSeeder.cs)
- Upgrade `WPAPP` to the statutory Maker-Checker process:
  * `DRAFT` $\rightarrow$ `APPLICATION` $\rightarrow$ `CLO_VERIFICATION` (or `AUDIT_SCHEDULED`) $\rightarrow$ `QA_EVALUATION` $\rightarrow$ `APPROVED` / `REJECTED` / `REVISE_REQUESTED`.

#### [MODIFY] [WorkplaceApprovalService.cs](file:///c:/Antigravity/nsdms-2026-04-01/nsdms/MerSETA/dotnet/Nsdms.Application/Services/WorkplaceApprovalService.cs)
- Add statutory methods:
  * `SubmitApplicationAsync(int id, string currentUser)`: Enforces document presence, sets status to `APPLICATION`, computes 20-working-day SLA `InspectionDueDate`.
  * `VerifyWorkplaceAsync(int id, bool isVisitRequired, string? visitJustification, int? recReasonId, string? recExp, int? rejReasonId, string? rejExp, string currentUser)`.
  * `EvaluateWorkplaceAsync(int id, bool isApproved, int? reasonId, string? explanation, string currentUser)`.
  * `WithdrawApprovalAsync(int id, string revocationReason, string currentUser)`.
- Strictly preserve all existing methods:
  * `GetPlacedLearnersAsync`, `GetPartnerSdpsAsync`, `GetVerificationVisitsAsync`, `GetRatioEvaluationAsync`, `AddMentorAsync`, `RemoveMentorAsync`, `AddToolItemAsync`, `RemoveToolItemAsync`.

---

### 3. Statutory Document Generation (`Nsdms.Infrastructure` - QuestPDF)

#### [MODIFY] [IApplicationServices.cs](file:///c:/Antigravity/nsdms-2026-04-01/nsdms/MerSETA/dotnet/Nsdms.Application/Common/Interfaces/IApplicationServices.cs)
- Declare `GenerateWorkplaceApprovalLetterPdfAsync(int approvalId)` (Annexure 10.1: `ETQ-TP-003`).
- Declare `GenerateWorkplaceApprovalReportPdfAsync(int approvalId)` (Annexure 10.2: `ETQ-TP-054`).

#### [MODIFY] [QuestPdfDocumentService.cs](file:///c:/Antigravity/nsdms-2026-04-01/nsdms/MerSETA/dotnet/Nsdms.Infrastructure/Services/QuestPdfDocumentService.cs)
- Implement `GenerateWorkplaceApprovalLetterPdfAsync`:
  * Official merSETA outcome letter layout (`ETQ-TP-003`).
  * RSA Coat of Arms, merSETA header branding.
  * Trade Code, Trade Title, Validity Period, QA Manager sign-off.
  * Dynamic high-resolution QR verification code.
- Implement `GenerateWorkplaceApprovalReportPdfAsync`:
  * Official inspection report layout (`ETQ-TP-054`).
  * Company details, site details, contact person details, tool checklist results, ratio evaluation results, CLO/QA findings.

---

### 4. MudBlazor Application Wizard (`Nsdms.Web`)

#### [MODIFY] [WorkplaceApprovalWizard.razor](file:///c:/Antigravity/nsdms-2026-04-01/nsdms/MerSETA/dotnet/Nsdms.Web/Components/Pages/WorkplaceApprovals/WorkplaceApprovalWizard.razor)
- Fix site selection bug: Fetch `_sites = await OrgService.GetSitesByOrganisationIdAsync(_organisationId)` when organisation changes.
- Add Learning Programme Type dropdown in Step 2.
- Add Document Upload step prior to review (Structured Programme, Tool Checklist/Agreement, Ratio Confirmation, Mentor Credentials, Inter-SETA Agreement for non-merSETA).
- Enforce mandatory document presence before enabling submission.
- Add Applicant Sign-Off acknowledgement checkbox (Section 4.2.6).
- Preserve Draft Persistence (`IWizardDraftService`), resume, and auto-save capabilities.

---

### 5. Master-Detail Visual Parity Hub (`Nsdms.Web`)

#### [MODIFY] [WorkplaceApprovalDetail.razor](file:///c:/Antigravity/nsdms-2026-04-01/nsdms/MerSETA/dotnet/Nsdms.Web/Components/Pages/WorkplaceApprovals/WorkplaceApprovalDetail.razor)
- Enforce View by Default: View mode displays `<ReadOnlyField>` components.
- Top action bar: Add direct PDF download buttons for Approval Letter (`ETQ-TP-003`) and Inspection Report (`ETQ-TP-054`).
- Add 20-working-day SLA countdown badge in header.
- Add CLO Verification card/modal with:
  * Yes/No site visit switch.
  * Recommendation vs Rejection reason dropdowns and explanation inputs.
- Add QA Approval card/modal with:
  * Decision reason dropdowns and explanation inputs.
- Retain all 360-degree relational tabs (Learners, SDPs, Visits, Tools, Vault, Workflow Timeline) with zero disruption.

---

## Verification Plan

### Automated Tests
- `dotnet test dotnet/Nsdms.Tests/Nsdms.Tests.csproj --filter "FullyQualifiedName~WorkplaceApproval"`:
  * Verify 20-working-day SLA calculation.
  * Verify CLO verification and QA approval state transitions.
  * Verify QuestPDF document generation for both Annexure 10.1 and 10.2.
  * Regression test all NAMB ratio policy engine evaluations.

### Manual Verification
1. Navigate to `/workplace-approvals/create`:
   - Verify sites dropdown populates for the selected employer.
   - Select Learning Programme Type.
   - Upload mandatory documents.
   - Complete applicant sign-off and submit.
2. Open the submitted approval in `/workplace-approvals/{id}`:
   - Verify 20-day SLA countdown badge.
   - Test CLO verification (Yes/No visit decision).
   - Test QA approval.
   - Click "Download Approval Letter" and verify PDF layout against Annexure 10.1.
   - Click "Download Inspection Report" and verify PDF layout against Annexure 10.2.
   - Confirm all 360-degree tabs (Learners, SDPs, Visits, Mentors, Tools) remain 100% intact.
