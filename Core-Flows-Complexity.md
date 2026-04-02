# Core Business Flows & Architectural Complexity

Based on a quantitative analysis of the `haj.com.service` layer—specifically looking at file payload sizes (Lines of Code / Bytes) which act as a direct proxy for cyclomatic complexity in a legacy monolith—here is a breakdown of the primary business workflows within the NSDMS application.

> [!CAUTION] 
> **Complexity Definition:** In this codebase, any service approaching or exceeding **100 KB** is inherently highly complex due to the "God Object" anti-pattern (lack of dependency injection, tight coupling, and massive multi-stage methods handling validation, database IO, and email triggers).

---

## 1. 🎓 Learner Lifecycle Management (Extreme Complexity)
This is the single most massive and entangled flow in the entire application, dealing with the end-to-end management of apprentices, learnerships, and skills programs.

*   **Primary Services:**
    *   `CompanyLearnersService` (**388 KB** - The largest file in the app)
    *   `CompanyLearnersTradeTestService` (**312 KB**)
    *   `CompanyLearnersOtpSignoffService` (**258 KB**)
*   **Workflow Scope:** Registration, document validation, OTP (One-Time-PIN) multi-party signoffs, trade test scheduling, and completion certifications.
*   **Complexity:** 🔴 **Extreme**. These services handle enormous branching logic for different intervention types (Bursaries vs. Apprenticeships) and generate massive database cascading operations.

## 2. 🏢 Skills Development Provider (SDP) Accreditation (High Complexity)
The process of evaluating, approving, and verifying external training providers.

*   **Primary Services:**
    *   `TrainingProviderApplicationService` (**289 KB**)
    *   `TrainingProviderVerficationService` (**125 KB**)
    *   `TrainingProviderMonitoringService` (**99 KB**)
*   **Workflow Scope:** New provider applications, document uploads, linking Assessors/Moderators, site evaluations, and periodic re-accreditation/monitoring.
*   **Complexity:** 🟠 **High**. Heavily reliant on multi-user approval workflows (SMEs, Evaluators, Review Committees) and deep historical tracking mapping qualifications to providers.

## 3. 🏢 Employer / Stakeholder Management (High Complexity)
General management of Seta and Non-Seta organizations, including banking details, SDF linkages, and general data.

*   **Primary Services:**
    *   `CompanyService` (**232 KB**)
*   **Workflow Scope:** Employer registration, linking to SARS SDL numbers, and linking Skills Development Facilitators (SDFs). 
*   **Complexity:** 🟠 **High**. While standard CRUD in theory, integrating complex validation rules for SARS and SETMIS extracts makes this a tangled object.

## 4. 👷 Assessor & Moderator Registrations (High Complexity)
Managing the individuals legally authorized to evaluate and moderate learners.

*   **Primary Services:**
    *   `AssessorModeratorApplicationService` (**169 KB**)
*   **Workflow Scope:** Application submission, verification of qualifications (SAQA/NLRD), and granting or extending scopes of practice.
*   **Complexity:** 🟠 **High**. Like SDPs, heavily reliant on multi-tier status state machines (Pending -> Under Review -> Approved/Rejected).

## 5. 🏭 Workplace Approvals (High Complexity)
Validating that an employer has the correct infrastructure and mentors to legally host a specific apprenticeship or learnership.

*   **Primary Services:**
    *   `WorkPlaceApprovalService` (**182 KB**)
*   **Workflow Scope:** Extracting company sites, assigning mentors, checking tool lists (`WorkPlaceApprovalToolListService`), and logging site visit dates.
*   **Complexity:** 🟠 **High**. A single `WorkPlaceApprovalService` drives the entire workflow, making it a severe bottleneck for any schema changes.

## 6. 💰 Grant Management: WSP, ATR, & Discretionary Grants (High Complexity)
The financial beating heart of the SETA: Mandatory and Discretionary Grant submissions and allocations.

*   **Primary Services:**
    *   `WspService` (**145 KB**)
    *   `WspDGService` (**119 KB**)
    *   `ActiveContractsService` (**129 KB**)
*   **Workflow Scope:** Companies declaring their Workplace Skills Plan (WSP) and Annual Training Report (ATR). Discretionary Grant (DG) application, allocation calculation, and contract generation.
*   **Complexity:** 🟠 **High**. These flows contain the most risk because they interact directly with financial logic (Tranches, Allocations, Levies) and integrate deep into the `SarsLevyDetailsService` (21 KB).

---

## 💡 Summary of Flow Complexity

If you are planning to leverage the **Strangler Fig Pattern** (Option B from the previous brainstorm), the most critical (but riskiest) area to extract first would be the **Workplace Approvals** or **Assessor/Moderator Registrations**. 

I highly advise **NOT** attempting to rewrite the **Learner Lifecycle Management** (`CompanyLearnersService`) initially, as its 388 KB size indicates a massive amount of hidden, undocumented business logic that requires deep discovery before migration.
