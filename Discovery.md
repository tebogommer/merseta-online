# Discovery.md

---

# 1. System Overview

* **Purpose of the system:** A comprehensive enterprise skills development, grants management, and training provider accreditation platform for the Manufacturing, Engineering and Related Services SETA (merSETA).
* **Primary domain:** Grants (Mandatory & Discretionary), Work Place Skills Plans (WSP), Learner Management (Apprenticeships, Learnerships), Training Provider Accreditation (QCTO/SAQA aligned), Assessor & Moderator management, and Levy collection (SARS integration).
* **Key capabilities:**
  * WSP and Annual Training Report (ATR) submissions and approvals.
  * Discretionary and Mandatory Grant processing and allocations.
  * Training Provider (SDP) re-accreditation and monitoring.
  * Assessor and Moderator registration and scope extensions.
  * Learner registrations, progress tracking, trade tests, and certification.
  * Data integrations with SARS, SAQA, NLRD, and SETMIS.
* **High-level architecture summary:**
  * Java monolith running on a Servlet container (likely Tomcat/JBoss).
  * Presentation Layer: JSF 2.x (JavaServer Faces) with PrimeFaces UI components.
  * API Layer: Jersey JAX-RS for RESTful web services (for mobile/external).
  * Business Logic: Stateful and Stateless services in the `haj.com.service` package.
  * Persistence: Hibernate / JPA ORM with a MySQL backend (configured in `hibernate.cfg.xml`), utilizing EHCache for caching.
  * Asynchronous Comm: PrimePush / Atmosphere for WebSockets/push notifications.
* **Major subsystems/modules:**
  * WSP Management
  * Grant Management (DG/MG)
  * Provider Applications
  * Learner Management
  * SARS Data ingestion
  * Workflow / Task Engine
  * Reporting & Extracts (SETMIS/NLRD)

---

# 2. Module Inventory

### Workplace Skills Plan (WSP)
* **Purpose:** Submission and approval of employer skills plans and training reports.
* **Key components:** `Wsp.java`, `WspService.java`, `WspSignoff.java`, `WspChecklist.java`.
* **Key responsibilities:** Form validation, sign-off chains, rejection/disputes handling.
* **Dependencies on other modules:** Requires `Company` and `User` (SDF) modules.

### Grants Management (Mandatory & Discretionary)
* **Purpose:** Allocation and tracking of funds back to qualifying employers.
* **Key components:** `MandatoryGrant.java`, `DgAllocation.java`, `PaymentRequest.java`, `DgVerification.java`.
* **Key responsibilities:** Verification of WSP criteria, payment requests, tracking allocations against budgets.
* **Dependencies on other modules:** WSP module, SARS levy module (to verify funds).

### Training Provider Management (SDP)
* **Purpose:** Accreditation, re-accreditation, and monitoring of Skills Development Providers.
* **Key components:** `TrainingProviderApplication.java`, `SDPExtensionOfScope.java`, `WorkplaceMonitoring.java`, `TrainingSite.java`.
* **Key responsibilities:** Application evaluation, site visits (Scheduling, SME reports), extension of scope to new unit standards.
* **Dependencies on other modules:** Lookup module (SAQA/Unit standards).

### Assessor & Moderator Management
* **Purpose:** Registration and linking of assessors to training providers.
* **Key components:** `AssessorModeratorApplication.java`, `AssessorModExtensionOfScope.java`.
* **Key responsibilities:** Managing validity of assessors, linking them to specific trade unit standards.

### Learner Management
* **Purpose:** Tracking the lifecycle of a learner from registration to certification.
* **Key components:** `CompanyLearners.java`, `CompanyLearnersTradeTest.java`, `LearnershipDevelopmentRegistration.java`.
* **Key responsibilities:** Tracking transfers, lost time, terminations, EISA (External Integrated Summative Assessment) status, and Trade Tests.

### Reporting & Integrations (NLRD / SETMIS / SARS / SAQA)
* **Purpose:** Ingesting levy data and reporting compliance to national bodies.
* **Key components:** `SarsFiles.java`, `SetmisFile*.java`, `NLRDFile*.java`, `QCTOFile*.java`.
* **Key responsibilities:** Batch processing, data extraction to text/XML files for DHET/QCTO.

### Workflow & Task Management
* **Purpose:** Guiding records through various states of approval.
* **Key components:** `Tasks.java`, `TaskUsers.java`, `TaskServiceFlow.java`.
* **Key responsibilities:** Assigning items to reviewers/committees, rejection reasons tracking.

---

# 3. User & Role Model

* **Identify user types:** 
  * SDF (Skills Development Facilitator)
  * Internal Admins (merSETA staff, Review Committee Members)
  * Super Admin
  * Training Provider / SDP
  * Assessor / Moderator
  * Employer / Company Rep
  * External Reviewer / SME (Subject Matter Expert)
* **Role definitions (explicit or inferred):** Mapped dynamically via `UsersRole`, `ProcessRoles`, and `UserPermissions`.
* **Permission patterns:** Handled via JSF Security Filters (`SecurityCheckFilter`) and explicit queries against the `UserPermissions` and `ProcessRoles` tables before rendering UI buttons or processing backend tasks.
* **Role-to-action mapping:** Inferred to be deeply tied to the Task Workflow engine. Users can only act on records if a `Task` is explicitly assigned to them or their `ProcessRole`.
* **Any unit/organisation scoping logic:** Extensive company-linked security. A user is tied to a `Company` via `CompanyUsers`, `HostingCompany`, `SDFCompany` and can only view/edit WSP/Grants for their linked company.

---

# 4. Business Process & Workflow Analysis

All workflows are modelled using the central `Tasks` entity, pushing records between states.

### Workflow: WSP Submission & Approval
* **Trigger:** Employer/SDF initiates a new WSP application.
* **States (Inferred):** Draft -> Pending SDF Signoff -> Pending Review Committee -> Approved / Rejected.
* **Transitions:** Signoff actions (`WspSignoff`), Rejection (`WspRejectionInformation`).
* **Actors involved:** SDF, Employer Rep, Region Admin, Review Committee.
* **Conditions for transitions:** All required documents uploaded, form fields completed, OTP/Digital signatures acquired.
* **Side effects:** Email notifications (`MailLog`), Task assignment re-routing, Updates to Company History.

### Workflow: Training Provider Accreditation
* **Trigger:** Provider submits application.
* **States (Inferred):** Application -> Desktop Review -> Site Visit Scheduled -> Site Visit Report Pending -> Namb/QCTO Approval -> Accredited.
* **Transitions:** Passing desktop review, completing site visit (`SiteVisitReportSME`).
* **Actors involved:** Provider, Quality Assuror, SME.
* **Side effects:** Generation of Accreditation Letters (JasperReports).

### Workflow: Learner Trade Test
* **Trigger:** Learner completes required hours/modules.
* **States (Inferred):** Applied -> Scheduled -> Evaluated -> Competent / Not Yet Competent.
* **Transitions:** Capturing of `TradeTestTaskResult`, Upload of `SummativeAssessmentReport`.

**Important Distinction:** System uses explicit `Tasks` for workflow transitioning, whereas the actual entities (like `CompanyLearners` or `Wsp`) hold a `Status` field that gets updated as a side-effect of a Task completion.

---

# 5. Business Rules Catalogue

### Validation Rules
* **field-level:** SAQA Qualifications must exist in the Lookups before being assigned to an SDP or Learner.
* **cross-field:** WSP Levy amounts must reconcile with SARS ingested data.
* **conditional validation:** If an application is rejected, at least one `RejectReasonsChild` / `RejectReasonMultipleSelection` MUST be provided.

### Calculation Rules
* **formulas:** Grant allocations and payments calculated from ingested SARS Levies (`SarsLevyDetailCalculation`).
* **derived values:** System auto-calculates workplace ratios (`CompanyLearnersRatio`).

### Decision Rules
* **approvals:** High-value or final accreditations require `ReviewCommitteeMeeting` resolutions.
* **thresholds:** Budget ceilings on Discretionary Grants (`DgAllocation Parent/Child` limits).

### Permission Rules
* **who can do what and when:** Only users with `ActiveContracts` or specific `ProcessRoles` can sign off. SDFs must be explicitly registered and linked (`SdfCompany`) before creating a WSP.

### Data Integrity Rules
* **uniqueness:** ID numbers for Users and Company Registration numbers are strictly unique and enforced across the DB.
* **required relationships:** A `CompanyLearner` cannot exist without a valid `Company`, `Learner` and `Qualification/UnitStandard`.

---

# 6. Data Model Analysis

## 6.1 Entity Inventory
The system contains over 300 entities. Core entities include:
* **Company:** Employer details. `CompanyHistory` tracks audits. `CompanyUsers` handles linkage.
* **Wsp:** Workplace Skills Plan header. Linked to `WspSkillsGap`, `WspCalculationData`, `WspSignoff`.
* **TrainingProviderApplication:** SDP details and status. Linked to `WorkPlaceApproval`.
* **Users:** Merged table for all identity types. Linked to `Address`.
* **CompanyLearners:** The bridging lifecycle record of a Learner undergoing training at a Company.

## 6.2 Relationships
* **one-to-many:** Company `1 -> N` CompanyLearners. Wsp `1 -> N` WspSkillsGap.
* **many-to-many:** Usually resolved via mapping tables (e.g., `HostingCompanyDepartmentsEmployees`, `SkillsRegistrationUnitStandards`).
* **ownership:** `Doc` (Documents) are owned generically by the entity they attach to (via polymorphic-like links or explicit FK tables like `TrainingProviderDocParent`).

## 6.3 Constraints
* **primary keys:** Standard auto-increment `id` across `BaseEntity` lineage.
* **foreign keys:** Heavily utilized and enforced by Hibernate (`@ManyToOne`).
* **nullability:** Auditing fields (`UpdateAuditTrail`) and document trackers must not be null.

## 6.4 Lookup / Type Tables
* Extreme use of lookup tables in the `haj.com.entity.lookup` package (100+ entities). Includes `SaqaQualification`, `OfoCodes`, `Gender`, `Seta`, `Aqp`, `Status`.

## 6.5 Audit Model
* Extensive auditing at the row level via `UpdateAuditTrail` and `UpdateAuditTrailChanges`.
* Many core entities have explicit history tables (e.g., `WspCompanyHistory`, `EmployeesHistory`, `SitesHistory`) to trace versions and alterations.

---

# 7. API & Interface Surface

## 7.1 Internal APIs
* Uses standard Jersey (`haj.com.rest`) for internal cross-app APIs or mobile hooks.
* Heavily relies on JSF Bean backing methods for standard UI interactions.
* PrimePush API (`org.primefaces.push.PushServlet`) for websocket/long-polling event pushes.

## 7.2 External Integrations
* **SARS:** (Inbound/DB/File) `SarsFiles` ingestion. Pulls levy data to calculate available grants.
* **SAQA / NLRD / SETMIS:** (Inbound/Outbound) Formatted flat files and extracts mapped via dedicated exporter entities (`SetmisFile100`, `QCTOFile01`).
* **DHET:** (Outbound) Statutory reporting via `DhetReporting`.

---

# 8. UI & User Interaction Model

* **screen types:** Admin Dashboards, heavy data-entry forms (WSP), paged DataTables (PrimeFaces `<p:dataTable>`).
* **navigation structure (inferred):** Sidebar navigation mapped via `LegacyMenu` and `LookupMenu` based on dynamic roles.
* **key user journeys:** SDF Registering a Company, completing a multipage WSP wizard, signing off via OTP.
* **forms and inputs:** Wizard layouts (`<p:wizard>`), extensive lookups with lazy-loaded dropdowns, heavy use of inline field validation and modal dialogue (`<p:dialog>`).
* **major UX patterns:** RAG status indicators, complex nested grids, tabbed panel layouts (`<p:tabView>`).

---

# 9. Background Processing & Jobs

* **scheduled jobs:** Inferred from the `haj.com.jobs` package. Likely Quartz scheduler heavily utilized for batch jobs.
* **batch processes:** Nightly processing of SETMIS Extracts, SARS Levy Recons (`SarsLevyReconService`), and Escalation loops for stalled `Tasks`.
* **asynchronous tasks:** Generating large Jasper PDF reports is tracked asynchronously via `JasperDownloadTracker`.

---

# 10. File & Document Handling

* **file uploads/downloads:** Handled natively by `<p:fileUpload>` configured for `commons` uploader. 
* **document generation (PDF, etc.):** Extensive use of JasperReports (`JasperService.java`) to dynamically generate Certificates, Approvals, and Letters.
* **storage approach:** Files managed by the `Doc` and `DocByte` entities. Likely stored directly in the Database `DocByte` or a secure network location mapped by paths.
* **linkage to entities:** Highly decoupled. Uses standard `Doc` tables linked by Target Class/ID.

---

# 11. Logging, Error Handling & Observability

* **logging frameworks:** Utilizes `log4j` (`log4j.properties`).
* **log patterns:** Basic debug/error logging. JavaMelody (`net.bull.javamelody.MonitoringFilter`) is configured for JVM metrics and SQL profiling but currently seems disabled to end users.
* **error handling strategy:** Global Exception handler mapping to `/errors/error.jsf`.
* **audit/event logging:** Explicitly handled in business logic via Java history entities (`UpdateAuditTrailChanges`). `MailLog` tracks outbound system comms.

---

# 12. Security Model

* **authentication approach:** Likely session-based using JSF Forms, intercepted by `SecurityCheckFilter` (`haj.com.servlet.SecurityCheckFilter`).
* **authorization approach:** Custom role/permission resolution via DB lookup on a per-action basis. Not relying on standard Spring Security config.
* **session handling:** Standard JEE HttpSession, tracked by `SessionListener`. 60-minute timeout.
* **vulnerabilities or gaps (inferred):** Custom security filters are prone to bypass if paths aren't exhaustively mapped. The mixture of Rest API and JSF requires two distinct authentication contexts.

---

# 13. Technical Architecture Summary

* **layering:** Classic N-Tier. JSF Views `->` Backing Beans (`haj.com.bean`) `->` Service Layer (`haj.com.service`) `->` DAO / Hibernate ORM (`haj.com.dao`).
* **coupling patterns:** High tight-coupling. Services call other services directly (e.g., `CompanyService` calling `TaskService` and `MailService`).
* **dependency structure:** Monolithic. Business logic, reporting, and web UI are all strictly compiled together. Custom workflow engine is deeply baked into every domain model.
* **notable frameworks used:** Java EE / Servlet 3.x, Hibernate / JPA, PrimeFaces + Ultima Theme, Jersey (JAX-RS), JasperReports, JavaMelody.

---

# 14. Risks, Complexity & Migration Considerations

Identify:

* **tightly coupled components:** The `Tasks` mechanism and auditing framework are deeply intertwined into all saving mechanisms. Moving to microservices or generic routers will require a complete rewrite of the workflow engine. **(High Risk)**
* **hidden business logic:** Massive service files (e.g., `CompanyLearnersService.java` is ~388KB) suggest extensive "God Classes" with deeply nested legacy business rules. **(High Risk)**
* **duplicated logic:** Assessor/Moderator and SDP Provider flows share similar architectures but use detached isolated classes. **(Medium Risk)**
* **inconsistent validation:** Validation rules split between PrimeFaces UI tags and Java Backend Services. **(High Risk)**
* **overloaded status fields:** Entities having explicit statuses vs interacting with `Tasks`. **(Medium Risk)**
* **risky integrations:** SETMIS, SARS, NLRD flat-file logic and mappings will be highly brittle to schema changes. **(High Risk)**
* **data anomalies:** Relying on `DocByte` for file storage can cause massive DB bloat, making initial data portability / migration to Next.js slow. **(High Risk)**
* **areas likely to break during migration:** Jasper Reports to Next.js / React-PDF logic transition; Rebuilding the custom JSF SecurityCheckFilter into Next.js middleware and NextAuth. **(High Risk)**

---

# 15. Assumptions & Inferences

* **Assumption 1:** It's assumed the system operates heavily around a central "Review Committee" sign-off structure based on the existence of the `ReviewCommitteeMeeting` entities.
* **Assumption 2:** Without seeing the method bodies, we assume the `Tasks` table is the sole source of truth for workflow queues and user inboxes.
* **Assumption 3:** Assumed data layer relies almost entirely on soft deletes and History tables instead of hard deletes (based on `SoftDeleteExample` mappings and Auditing tables).
* **Assumption 4:** The deployment is assumed to be an On-Premise Tomcat / WildFly application server given the JEE Web.xml configuration and JNDI Database lookup (`java:comp/env/jdbc/mersetadatasource`).
