# MerSETA NSDMS — Database Data Dictionary

> **Generated:** 2026-09-05 11:40:27 UTC | **Target Engine:** Microsoft SQL Server Express | **Total Tables:** 187

---

## 📋 Schema Overview Table

| Schema | Table Name | CLR Entity | Columns | Primary Key | Description |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `dbo` | [`AppRole`](#approle) | `ApplicationRole` | 6 | `Id` | Application security role for CASL/RBAC authorization. |
| `dbo` | [`AppRoleClaim`](#approleclaim) | `IdentityRoleClaim`1` | 4 | `Id` | System entity for IdentityRoleClaim`1 data governance. |
| `dbo` | [`AppUser`](#appuser) | `ApplicationUser` | 22 | `Id` | Application authentication user account integrated with ASP.NET Core Identity. |
| `dbo` | [`AppUserClaim`](#appuserclaim) | `IdentityUserClaim`1` | 4 | `Id` | System entity for IdentityUserClaim`1 data governance. |
| `dbo` | [`AppUserLogin`](#appuserlogin) | `IdentityUserLogin`1` | 4 | `LoginProvider, ProviderKey` | System entity for IdentityUserLogin`1 data governance. |
| `dbo` | [`AppUserRole`](#appuserrole) | `IdentityUserRole`1` | 2 | `UserId, RoleId` | System entity for IdentityUserRole`1 data governance. |
| `dbo` | [`AppUserToken`](#appusertoken) | `IdentityUserToken`1` | 4 | `UserId, LoginProvider, Name` | System entity for IdentityUserToken`1 data governance. |
| `dbo` | [`AqpLearnerAssessment`](#aqplearnerassessment) | `AqpLearnerAssessment` | 20 | `Id` | External Integrated Summative Assessment (EISA) record administered by an AQP for a registered learner. |
| `dbo` | [`AqpPartner`](#aqppartner) | `AqpPartner` | 19 | `Id` | Assessment Quality Partner (AQP) accredited by QCTO to develop assessment instruments and manage EISA exams. |
| `dbo` | [`AqpQualificationScope`](#aqpqualificationscope) | `AqpQualificationScope` | 12 | `Id` | Qualifications in scope for which the AQP is authorized to administer external summative assessments (EISA). |
| `dbo` | [`ArplExperienceDetail`](#arplexperiencedetail) | `ArplExperienceDetail` | 12 | `Id` | Historical work experience item claimed under ARPL. |
| `dbo` | [`ArplTradeTestInformation`](#arpltradetestinformation) | `ArplTradeTestInformation` | 17 | `Id` | Artisan Recognition of Prior Learning (ARPL) Portfolio Assessment record. |
| `dbo` | [`ArplTrainingDetail`](#arpltrainingdetail) | `ArplTrainingDetail` | 10 | `Id` | Prior formal or non-formal training modules completed by an ARPL candidate. |
| `dbo` | [`AssessorCpdActivity`](#assessorcpdactivity) | `AssessorCpdActivity` | 12 | `Id` | Individual Continuous Professional Development (CPD) activity logged in support of practitioner renewal. |
| `dbo` | [`AssessorExtensionOfScope`](#assessorextensionofscope) | `AssessorExtensionOfScope` | 16 | `Id` | System entity for AssessorExtensionOfScope data governance. |
| `dbo` | [`AssessorModeratorApplication`](#assessormoderatorapplication) | `AssessorModeratorApplication` | 12 | `Id` | Assessor and Moderator Accreditation Application. |
| `dbo` | [`AssessorModeratorScope`](#assessormoderatorscope) | `AssessorModeratorScope` | 10 | `Id` | Specific SAQA qualification registration scope granted to an ETQA Assessor or Moderator. |
| `dbo` | [`AssessorReRegistrationApplication`](#assessorreregistrationapplication) | `AssessorReRegistrationApplication` | 19 | `Id` | Represents a statutory 3-year re-registration or scope extension application for ETQA registered Assessors and Moderators. |
| `dbo` | [`AuditLog`](#auditlog) | `AuditLog` | 11 | `Id` | Immutable operational audit trail recording entity mutations, actor identity, timestamps, and JSON snapshots. |
| `dbo` | [`BankingDetails`](#bankingdetails) | `BankingDetails` | 34 | `Id` | System entity for BankingDetails data governance. |
| `dbo` | [`BankingDetailsAudit`](#bankingdetailsaudit) | `BankingDetailsAudit` | 8 | `Id` | System entity for BankingDetailsAudit data governance. |
| `dbo` | [`CompanyLearner`](#companylearner) | `CompanyLearner` | 43 | `Id` | Represents a learner registration agreement linked to an employer organisation, training provider, and MerSETA learning programme, capturing all statutory fields for SETMIS Files 500, 501, 502, and 506. |
| `dbo` | [`CompanyLearnerChangeRequest`](#companylearnerchangerequest) | `CompanyLearnerChangeRequest` | 16 | `Id` | Formal change request for amending an active registered CompanyLearner contract or demographic details. |
| `dbo` | [`CompanyLearnerLostTime`](#companylearnerlosttime) | `CompanyLearnerLostTime` | 16 | `Id` | Tracks suspended or lost training time and recalculates revised contract end dates. |
| `dbo` | [`CompanyLearnerTermination`](#companylearnertermination) | `CompanyLearnerTermination` | 15 | `Id` | Manages formal bilateral or unilateral cancellation of learner contracts. |
| `dbo` | [`CompanyLearnerTransfer`](#companylearnertransfer) | `CompanyLearnerTransfer` | 15 | `Id` | Tracks transfer of an active learner contract from one employer to another. |
| `dbo` | [`ContractAddenda`](#contractaddenda) | `ContractAddenda` | 19 | `Id` | System entity for ContractAddenda data governance. |
| `dbo` | [`ContractExtensionRequest`](#contractextensionrequest) | `ContractExtensionRequest` | 16 | `Id` | System entity for ContractExtensionRequest data governance. |
| `dbo` | [`ContractTerminationRequest`](#contractterminationrequest) | `ContractTerminationRequest` | 15 | `Id` | System entity for ContractTerminationRequest data governance. |
| `dbo` | [`CurriculumWorkingGroupMember`](#curriculumworkinggroupmember) | `CurriculumWorkingGroupMember` | 13 | `Id` | Expert stakeholder member participating in the QCTO Qualification Development Working Group. |
| `dbo` | [`DocumentAttachment`](#documentattachment) | `DocumentAttachment` | 16 | `Id` | Polymorphic document attachment linked to any entity record. |
| `dbo` | [`DocumentClause`](#documentclause) | `DocumentClause` | 11 | `Id` | Reusable atomic document clause or statutory boilerplate paragraph. |
| `dbo` | [`DocumentMetadata`](#documentmetadata) | `DocumentMetadata` | 19 | `Id` | SHA-256 integrity-verified digital document evidence stored in the Document Vault. |
| `dbo` | [`DocumentRequirementRule`](#documentrequirementrule) | `DocumentRequirementRule` | 11 | `Id` | Governance rule defining mandatory document evidence required before workflow gate advancement. |
| `dbo` | [`DocumentSnapshot`](#documentsnapshot) | `DocumentSnapshot` | 27 | `Id` | Immutable cryptographically frozen snapshot of any issued statutory document, certificate, or letter. |
| `dbo` | [`DocumentTemplate`](#documenttemplate) | `DocumentTemplate` | 20 | `Id` | Universal enterprise document template for statutory letters, certificates, agreements, and notices. |
| `dbo` | [`DocumentTemplateSection`](#documenttemplatesection) | `DocumentTemplateSection` | 12 | `Id` | Ordered section mapping a reusable clause to a document template. |
| `dbo` | [`EisaAssessmentEntry`](#eisaassessmententry) | `EisaAssessmentEntry` | 15 | `Id` | External Integrated Summative Assessment (EISA) exam entry for QCTO occupational qualifications. |
| `dbo` | [`ErpOutboxMessage`](#erpoutboxmessage) | `ErpOutboxMessage` | 22 | `Id` | Transactional Outbox message entity for Microsoft Dynamics GP and ERP Web Services integration. Ensures resilient, decoupled asynchronous execution with automatic pause on GP outage and resumption upon recovery. |
| `dbo` | [`ErpPaymentBatchEntry`](#erppaymentbatchentry) | `ErpPaymentBatchEntry` | 17 | `Id` | Individual line item voucher within an ERP payment batch. |
| `dbo` | [`ErpPaymentBatchHeader`](#erppaymentbatchheader) | `ErpPaymentBatchHeader` | 14 | `Id` | ERP Payment Batch Header for staging mandatory/discretionary grant disbursements to Dynamics GP / Sage. |
| `dbo` | [`EtqaAssessor`](#etqaassessor) | `EtqaAssessor` | 17 | `Id` | Registered ETQA Assessors and Moderators with approved qualification scopes, capturing all statutory fields required for SETMIS File 401 (Person Designation) reporting. |
| `dbo` | [`FinancialApprovalThreshold`](#financialapprovalthreshold) | `FinancialApprovalThreshold` | 12 | `Id` | Represents a tiered financial delegation limit (DoA) enforcing role-based threshold caps on grants and disbursements. |
| `dbo` | [`FundingWindowPriority`](#fundingwindowpriority) | `FundingWindowPriority` | 12 | `Id` | Gazetted Strategic Priority sub-budget envelope and beneficiary quota allocated to a Discretionary Grant Funding Window. |
| `dbo` | [`GrantApplication`](#grantapplication) | `GrantApplication` | 20 | `Id` | Discretionary Grant funding applications submitted by employers for skills development projects. |
| `dbo` | [`GrantFundingWindow`](#grantfundingwindow) | `GrantFundingWindow` | 13 | `Id` | Gazette-announced Discretionary Grant funding window opening and allocation cycle. |
| `dbo` | [`GrantMoa`](#grantmoa) | `GrantMoa` | 16 | `Id` | Memorandum of Agreement (MOA) for approved Discretionary Grants. |
| `dbo` | [`GrantMoaMilestone`](#grantmoamilestone) | `GrantMoaMilestone` | 17 | `Id` | Delivery milestones linked to MOA tranches. |
| `dbo` | [`GrantPaymentClaim`](#grantpaymentclaim) | `GrantPaymentClaim` | 23 | `Id` | Milestone-based Payment Claim against a Project Implementation Plan. |
| `dbo` | [`GrantProjectBudget`](#grantprojectbudget) | `GrantProjectBudget` | 12 | `Id` | Detailed cost item and financial budget breakdown submitted in a Discretionary Grant application. |
| `dbo` | [`GrantTranchePayment`](#granttranchepayment) | `GrantTranchePayment` | 19 | `Id` | Tranche Invoices and Payment Requisitions. |
| `dbo` | [`InterSetaTransfer`](#intersetatransfer) | `InterSetaTransfer` | 16 | `Id` | Inter-SETA Transfer of employer registration and levy funds between SETAs. |
| `dbo` | [`LearnerAssessment`](#learnerassessment) | `LearnerAssessment` | 30 | `Id` | Formative and summative learning programme unit standard assessment evaluations, capturing all statutory fields required for SETMIS File 503 (Unit Standard Enrolment) reporting. |
| `dbo` | [`LearnerTradeTest`](#learnertradetest) | `LearnerTradeTest` | 28 | `Id` | Trade test final competency evaluation and artisan certification attempt records (ARPL & standard), fully normalized with statutory columns required for SETMIS File 505 (Trade Test) reporting. |
| `dbo` | [`LearnerTradeTestApplication`](#learnertradetestapplication) | `LearnerTradeTestApplication` | 33 | `Id` | Artisan Trade Test Application and Assessment Record (Section 26D / Section 28 ARPL / Apprenticeships). |
| `dbo` | [`LevyFile`](#levyfile) | `LevyFile` | 16 | `Id` | Monthly SARS Skills Development Levy file import batches. |
| `dbo` | [`LevyFileLine`](#levyfileline) | `LevyFileLine` | 21 | `Id` | Individual employer monthly SARS levy transaction breakdown. |
| `dbo` | [`MandatoryGrantDisbursement`](#mandatorygrantdisbursement) | `MandatoryGrantDisbursement` | 17 | `Id` | Mandatory Grant 20% Rebate Payouts for compliant employers submitting WSP/ATR. |
| `dbo` | [`MoaClause`](#moaclause) | `MoaClause` | 11 | `Id` | Reusable atomic legal clause in the MerSETA clause library. |
| `dbo` | [`MoaExecutionSnapshot`](#moaexecutionsnapshot) | `MoaExecutionSnapshot` | 14 | `Id` | Cryptographically frozen snapshot of an issued MoA contract for legal non-repudiation and audit defense. |
| `dbo` | [`MoaTemplate`](#moatemplate) | `MoaTemplate` | 17 | `Id` | Master legal template defining an MoA contract structure for a specific financial year and grant policy. |
| `dbo` | [`MoaTemplateSection`](#moatemplatesection) | `MoaTemplateSection` | 12 | `Id` | Ordered section mapping a reusable clause into a specific MoA template with custom numbering and conditions. |
| `dbo` | [`NambDecisionHistory`](#nambdecisionhistory) | `NambDecisionHistory` | 12 | `Id` | National Artisan Moderation Body (NAMB) adjudication trace and serial assignment log. |
| `dbo` | [`NambSubmissionBatch`](#nambsubmissionbatch) | `NambSubmissionBatch` | 15 | `Id` | Represents a formal National Artisan Moderation Body (NAMB) moderation and serial allocation submission batch. |
| `dbo` | [`NonSetaCompany`](#nonsetacompany) | `NonSetaCompany` | 13 | `Id` | Non-SETA External Employer / Organisation registered with other Quality Councils / SETAs. |
| `dbo` | [`NonSetaQualificationsCompletion`](#nonsetaqualificationscompletion) | `NonSetaQualificationsCompletion` | 19 | `Id` | Cross-SETA Qualification & TVET College Achievement Verification for merSETA articulation. |
| `dbo` | [`Organisation`](#organisation) | `Organisation` | 44 | `Id` | Registered employer, host workplace, or skills development entity under MerSETA jurisdiction, fully normalized with statutory columns required for SETMIS File 100 and File 200 reporting. |
| `dbo` | [`OrganisationContact`](#organisationcontact) | `OrganisationContact` | 12 | `Id` | Liaison contact person link associating an individual Person with an Employer Organisation. |
| `dbo` | [`OrganisationSite`](#organisationsite) | `OrganisationSite` | 25 | `Id` | Operational branch facility, plant, or training site belonging to an Employer Organisation, supporting multi-site mapping and GPS geolocation for SETMIS File 200 reporting. |
| `dbo` | [`Person`](#person) | `Person` | 47 | `Id` | Core demographic profile storing individual identity, SA ID/Passport, Washington Group functioning, POPIA compliance, educational background, and contact details for SETMIS statutory reporting. |
| `dbo` | [`PersonContact`](#personcontact) | `PersonContact` | 16 | `Id` | Vertically partitioned satellite entity storing residential, postal, and telecommunications contact data for an individual. |
| `dbo` | [`PersonDemographics`](#persondemographics) | `PersonDemographics` | 15 | `Id` | Vertically partitioned satellite entity storing demographic, language, equity, and statutory POPIA consent metadata. |
| `dbo` | [`PersonDisabilityRating`](#persondisabilityrating) | `PersonDisabilityRating` | 17 | `Id` | Vertically partitioned satellite entity isolating special personal information under POPIA: Washington Group Functioning disability difficulty ratings, assessment records, and support notes. |
| `dbo` | [`PipLearnerAllocation`](#piplearnerallocation) | `PipLearnerAllocation` | 12 | `Id` | Breakdown of awarded learner interventions and allowance budgets in a PIP. |
| `dbo` | [`ProjectImplementationPlan`](#projectimplementationplan) | `ProjectImplementationPlan` | 17 | `Id` | Discretionary Grant Project Implementation Plan (PIP) and contracting governance. |
| `dbo` | [`QualificationsCurriculumDevelopment`](#qualificationscurriculumdevelopment) | `QualificationsCurriculumDevelopment` | 31 | `Id` | Qualifications Curriculum Development (QCD) Application for QCTO Occupational Qualifications and Realignment. |
| `dbo` | [`ReviewCommitteeMeeting`](#reviewcommitteemeeting) | `ReviewCommitteeMeeting` | 15 | `Id` | Executive & ETQA Review Committee / MANCO Governance Meeting. |
| `dbo` | [`ReviewCommitteeMeetingAgenda`](#reviewcommitteemeetingagenda) | `ReviewCommitteeMeetingAgenda` | 16 | `Id` | Specific agenda item submitted for committee adjudication or voting resolution. |
| `dbo` | [`ReviewCommitteeMeetingMember`](#reviewcommitteemeetingmember) | `ReviewCommitteeMeetingMember` | 9 | `Id` | Committee attendee / voting member. |
| `dbo` | [`SarsLevyReconAudit`](#sarslevyreconaudit) | `SarsLevyReconAudit` | 26 | `Id` | Advanced SARS Monthly SDL Levy Reconciliation Audit and Discrepancy Tracking. |
| `dbo` | [`SarsLevyStaging`](#sarslevystaging) | `SarsLevyStaging` | 25 | `Id` | Staging entity for high-speed bulk ingestion of raw monthly SARS Skills Development Levy transactions. Serves as the landing table for streaming SqlBulkCopy before promotion into the production financial ledger. |
| `dbo` | [`SarsSchemeYearCalculation`](#sarsschemeyearcalculation) | `SarsSchemeYearCalculation` | 17 | `Id` | System entity for SarsSchemeYearCalculation data governance. |
| `dbo` | [`SdfAppointmentHistory`](#sdfappointmenthistory) | `SdfAppointmentHistory` | 7 | `Id` | System entity for SdfAppointmentHistory data governance. |
| `dbo` | [`SdfCompany`](#sdfcompany) | `SdfCompany` | 21 | `Id` | System entity for SdfCompany data governance. |
| `dbo` | [`SdpExtensionOfScope`](#sdpextensionofscope) | `SdpExtensionOfScope` | 19 | `Id` | System entity for SdpExtensionOfScope data governance. |
| `dbo` | [`SdpReAccreditationApplication`](#sdpreaccreditationapplication) | `SdpReAccreditationApplication` | 15 | `Id` | System entity for SdpReAccreditationApplication data governance. |
| `dbo` | [`SdpScopeExtensionApplication`](#sdpscopeextensionapplication) | `SdpScopeExtensionApplication` | 12 | `Id` | Skills Development Provider (SDP) Scope Extension Application. |
| `dbo` | [`SkillsRegistration`](#skillsregistration) | `SkillsRegistration` | 17 | `Id` | Skills Programme / Part-Qualification Curriculum Registration, capturing all statutory fields required for SETMIS File 304 (Non NQF Intervention) reporting. |
| `dbo` | [`StatementOfResults`](#statementofresults) | `StatementOfResults` | 14 | `Id` | Formal merSETA Statement of Results (SOR) document record with cryptographic tamper-proof hash. |
| `dbo` | [`StatutoryBatchFile`](#statutorybatchfile) | `StatutoryBatchFile` | 16 | `Id` | Individual fixed-width data file generated within a statutory batch. |
| `dbo` | [`StatutorySubmissionBatch`](#statutorysubmissionbatch) | `StatutorySubmissionBatch` | 18 | `Id` | Represents a statutory submission batch run for DHET SETMIS or SAQA NLRD. |
| `dbo` | [`StrategicPriority`](#strategicpriority) | `StrategicPriority` | 13 | `Id` | Statutory Strategic Priorities, Key Focus Areas, and Sector Skills Plan (SSP) Themes mapped to National Skills Development Plan (NSDP III) Outcomes and Strategic Infrastructure Projects (SIPs). |
| `dbo` | [`SummativeAssessmentReport`](#summativeassessmentreport) | `SummativeAssessmentReport` | 26 | `Id` | Comprehensive Summative Assessment Report capturing unit standard credits, EISA exams, and moderation outcomes. |
| `dbo` | [`SummativeAssessmentUnitStandard`](#summativeassessmentunitstandard) | `SummativeAssessmentUnitStandard` | 16 | `Id` | Unit Standard credit assessment and moderation outcome line item. |
| `dbo` | [`SystemConfig`](#systemconfig) | `SystemConfig` | 12 | `Id` | Runtime system configuration key-value overrides. |
| `dbo` | [`SystemFeatureFlag`](#systemfeatureflag) | `SystemFeatureFlag` | 11 | `Id` | Granular runtime feature flags for enabling/disabling modules and integrations. All external third-party integrations default to IsEnabled = false. |
| `dbo` | [`SystemNotification`](#systemnotification) | `SystemNotification` | 14 | `Id` | Persistent system notification for real-time SignalR push and user inbox alerts. |
| `dbo` | [`TradeMentorRatioPolicy`](#tradementorratiopolicy) | `TradeMentorRatioPolicy` | 15 | `Id` | Statutory artisan mentor-to-apprentice ratio policy per trade qualification or OFO occupational code. Regulates maximum learner supervision capacity for workplace approvals under NAMB and QCTO frameworks. |
| `dbo` | [`TradeTestTask`](#tradetesttask) | `TradeTestTask` | 15 | `Id` | Practical Task and Scoring Item evaluated during the Trade Test. |
| `dbo` | [`TrainingCommittee`](#trainingcommittee) | `TrainingCommittee` | 10 | `Id` | Employer Workplace Training Committee constituted per statutory merSETA guidelines. |
| `dbo` | [`TrainingCommitteeMember`](#trainingcommitteemember) | `TrainingCommitteeMember` | 10 | `Id` | Individual Union and Employer representatives on a Training Committee. |
| `dbo` | [`TrainingProvider`](#trainingprovider) | `TrainingProvider` | 25 | `Id` | Accredited Skills Development Provider (SDP) offering registered occupational qualifications and skills programmes, capturing all statutory fields required for SETMIS File 100 reporting. |
| `dbo` | [`TrainingProviderAssessorLink`](#trainingproviderassessorlink) | `TrainingProviderAssessorLink` | 13 | `Id` | Relational binding between an accredited ETQA Assessor/Moderator and an SDP delivery campus. |
| `dbo` | [`TrainingProviderCampus`](#trainingprovidercampus) | `TrainingProviderCampus` | 18 | `Id` | Physical training site or delivery campus belonging to an accredited Skills Development Provider (SDP). |
| `dbo` | [`TrainingProviderQualification`](#trainingproviderqualification) | `TrainingProviderQualification` | 11 | `Id` | Registered SAQA qualification delivery scope accredited to a Training Provider. |
| `dbo` | [`TrainingProviderUnitStandard`](#trainingproviderunitstandard) | `TrainingProviderUnitStandard` | 10 | `Id` | Registered SAQA unit standard accredited for delivery by a Training Provider. |
| `dbo` | [`Visit`](#visit) | `Visit` | 14 | `Id` | On-site monitoring, workplace inspection, and employer liaison visits scheduled and executed by MerSETA staff. |
| `dbo` | [`WorkflowDefinition`](#workflowdefinition) | `WorkflowDefinition` | 10 | `Id` | Universal workflow process blueprint defining lifecycle states, gates, and transitions. |
| `dbo` | [`WorkflowDelegation`](#workflowdelegation) | `WorkflowDelegation` | 14 | `Id` | Represents a time-bounded role and module delegation from one user to another (e.g., leave, acting capacity). |
| `dbo` | [`WorkflowHistory`](#workflowhistory) | `WorkflowHistory` | 14 | `Id` | Immutable audit snapshot recording every workflow transition execution and decision comments. |
| `dbo` | [`WorkflowInstance`](#workflowinstance) | `WorkflowInstance` | 15 | `Id` | Execution tracking instance of a workflow machine for a specific entity record. |
| `dbo` | [`WorkflowNotification`](#workflownotification) | `WorkflowNotification` | 13 | `Id` | User notification alert dispatched upon workflow advancement or assignment. |
| `dbo` | [`WorkflowSignoffAttestation`](#workflowsignoffattestation) | `WorkflowSignoffAttestation` | 16 | `Id` | Represents a cryptographic digital signoff attestation captured during approval/rejection milestones for AGSA and King IV compliance. |
| `dbo` | [`WorkflowState`](#workflowstate) | `WorkflowState` | 12 | `Id` | Lifecycle step / gate within a workflow machine (e.g. Draft, Under Review, Pending Approval, Approved). |
| `dbo` | [`WorkflowTask`](#workflowtask) | `WorkflowTask` | 17 | `Id` | Task assigned to a specific role or user requiring review, inspection, or verification action. |
| `dbo` | [`WorkflowTaskLease`](#workflowtasklease) | `WorkflowTaskLease` | 12 | `Id` | Represents a distributed concurrency lock lease on a workflow task to prevent simultaneous claims and race conditions. |
| `dbo` | [`WorkflowTransition`](#workflowtransition) | `WorkflowTransition` | 14 | `Id` | Authorized state transition path with role authorization gates and UI button metadata. |
| `dbo` | [`WorkplaceApproval`](#workplaceapproval) | `WorkplaceApproval` | 22 | `Id` | Workplace site inspection approval for hosting apprentice and learnership training. |
| `dbo` | [`WorkplaceApprovalMentor`](#workplaceapprovalmentor) | `WorkplaceApprovalMentor` | 16 | `Id` | Qualified artisan mentor assigned to supervise apprentices and learners at an approved workplace. |
| `dbo` | [`WorkplaceApprovalToolList`](#workplaceapprovaltoollist) | `WorkplaceApprovalToolList` | 11 | `Id` | Tool, equipment, or health and safety checklist item inspected for workplace qualification approval. |
| `dbo` | [`WorkplaceMonitoringActionPlan`](#workplacemonitoringactionplan) | `WorkplaceMonitoringActionPlan` | 15 | `Id` | Corrective Action Plan for identified defects or non-compliances. |
| `dbo` | [`WorkplaceMonitoringComplianceSurvey`](#workplacemonitoringcompliancesurvey) | `WorkplaceMonitoringComplianceSurvey` | 13 | `Id` | 10-Point Statutory Compliance Survey Questions answered during the visit. |
| `dbo` | [`WorkplaceMonitoringLearnerSurvey`](#workplacemonitoringlearnersurvey) | `WorkplaceMonitoringLearnerSurvey` | 15 | `Id` | Confidential Learner Interview Log conducted on-site. |
| `dbo` | [`WorkplaceMonitoringMitigationPlan`](#workplacemonitoringmitigationplan) | `WorkplaceMonitoringMitigationPlan` | 12 | `Id` | Specific risk mitigation plan for high-risk non-compliance findings. |
| `dbo` | [`WorkplaceMonitoringSiteVisit`](#workplacemonitoringsitevisit) | `WorkplaceMonitoringSiteVisit` | 21 | `Id` | Comprehensive Workplace Monitoring and Quality Assurance Site Visit. |
| `dbo` | [`WspDispute`](#wspdispute) | `WspDispute` | 13 | `Id` | Dispute logged regarding Workplace Skills Plan (WSP) approval or committee sign-off. |
| `dbo` | [`WspEmploymentSummary`](#wspemploymentsummary) | `WspEmploymentSummary` | 18 | `Id` | Employment profile demographic breakdown by occupational category (SETMIS Form 500). |
| `dbo` | [`WspSignoffAttestation`](#wspsignoffattestation) | `WspSignoffAttestation` | 20 | `Id` | Cryptographically sealed attestation for WSP/ATR multi-party sign-off (SDF, Labour Union, CEO). |
| `dbo` | [`WspSkillsGap`](#wspskillsgap) | `WspSkillsGap` | 11 | `Id` | Critical and Scarce Skills Gap identified during WSP compilation. |
| `dbo` | [`WspStrategicPriority`](#wspstrategicpriority) | `WspStrategicPriority` | 11 | `Id` | Strategic priorities and alignment with National Skills Development Plan (NSDP) goals in WSP submissions. |
| `dbo` | [`WspStrategicSkillsGap`](#wspstrategicskillsgap) | `WspStrategicSkillsGap` | 15 | `Id` | Captures identified organizational skills gaps, root causes, and targeted interventions for WSP/ATR submissions. |
| `dbo` | [`WspSubmission`](#wspsubmission) | `WspSubmission` | 17 | `Id` | Mandatory Grant Workplace Skills Plan (WSP) and Annual Training Report (ATR) submissions. |
| `dbo` | [`WspTrainingImpactSurvey`](#wsptrainingimpactsurvey) | `WspTrainingImpactSurvey` | 11 | `Id` | Qualitative training impact questionnaire evaluating the business effectiveness of previous year training. |
| `dbo` | [`WspTrainingPlan`](#wsptrainingplan) | `WspTrainingPlan` | 10 | `Id` | Planned learning and development interventions submitted in the annual Workplace Skills Plan. |
| `lookup` | [`AbetBandType`](#abetbandtype) | `AbetBandType` | 8 | `Code` | NLRD Adult Basic Education and Training Band classifications (Field: ABET_BAND_ID - 1 to 5). |
| `lookup` | [`AlternateIdType`](#alternateidtype) | `AlternateIdType` | 8 | `Code` | SETMIS statutory alternate identification types (Field: Alternate_Id_Type_Id - e.g. Passport, Birth Certificate, Work Permit). |
| `lookup` | [`CategoryType`](#categorytype) | `CategoryType` | 8 | `Code` | Employer entity levy compliance categories (e.g. Levy Paying, Non-Levy Paying, Government/Exempt). |
| `lookup` | [`ChamberType`](#chambertype) | `ChamberType` | 8 | `Code` | MerSETA sub-sector chambers (Auto, Metal & Engineering, Plastics, Motor Retail). |
| `lookup` | [`CitizenStatusType`](#citizenstatustype) | `CitizenStatusType` | 8 | `Code` | SETMIS South African citizenship and residency legal status classifications (Field: Citizen_Resident_Status_Code - SA, PR, D, O, U). |
| `lookup` | [`CommunicatingRatingType`](#communicatingratingtype) | `CommunicatingRatingType` | 8 | `Code` | Washington Group Communication functional rating (Field: Communicating_Rating_Id - 1: No difficulty to 6: Cannot yet be determined). |
| `lookup` | [`CompanySizeType`](#companysizetype) | `CompanySizeType` | 8 | `Code` | Enterprise headcount and turnover size bands (Small: 0-49, Medium: 50-149, Large: 150+). |
| `lookup` | [`CountryType`](#countrytype) | `CountryType` | 8 | `Code` | Country classification reference codes (Field: Country_Code - ISO-3166 2-Letter Alpha codes covering 249 global jurisdictions). |
| `lookup` | [`DesignationStructureStatusType`](#designationstructurestatustype) | `DesignationStructureStatusType` | 8 | `Code` | Assessor and Moderator ETQA registration structure status codes (Field: Designation_Structure_Status_Id - Registered, Deregistered, etc.). |
| `lookup` | [`DesignationType`](#designationtype) | `DesignationType` | 8 | `Code` | Assessor and Moderator statutory designation types (Field: Designation_Id - 1: Assessor, 0: Moderator). |
| `lookup` | [`DisabilityType`](#disabilitytype) | `DisabilityType` | 8 | `Code` | Disability impairment classifications per Employment Equity & SETMIS standards. |
| `lookup` | [`EconomicStatusType`](#economicstatustype) | `EconomicStatusType` | 8 | `Code` | Learner / employee economic employment status classifications (Field: Economic_Status_Id - Employed, Unemployed, etc.). |
| `lookup` | [`EmployerApprovalStatusType`](#employerapprovalstatustype) | `EmployerApprovalStatusType` | 8 | `Code` | Employer workplace approval hosting eligibility status codes (Field: Employer_Approval_Status_Id - Active, Inactive, Legacy). |
| `lookup` | [`EnrolmentStatusReasonType`](#enrolmentstatusreasontype) | `EnrolmentStatusReasonType` | 8 | `Code` | Reasons for learner enrolment status transitions (Field: Enrolment_Status_Reason_Id - Medical, Financial, Social, etc.). |
| `lookup` | [`EnrolmentStatusType`](#enrolmentstatustype) | `EnrolmentStatusType` | 8 | `Code` | Learner agreement registration lifecycle status codes (Field: Enrolment_Status_Id - Enrolled, Achieved, Certificated, etc.). |
| `lookup` | [`EnrolmentType`](#enrolmenttype) | `EnrolmentType` | 8 | `Code` | Learner enrolment intake and delivery modality types (Field: Enrolment_Type_Id - Contact, Distance, Mixed Mode, etc.). |
| `lookup` | [`EquityType`](#equitytype) | `EquityType` | 8 | `Code` | Statutory Employment Equity / BBBEE racial demographic classifications (Field: Equity_Code - BA, BC, BI, Wh, U). |
| `lookup` | [`FundingType`](#fundingtype) | `FundingType` | 8 | `Code` | Learning intervention funding source classifications (Field: Funding_Id - SETA funded, Employer funded, Learner funded, etc.). |
| `lookup` | [`GenderType`](#gendertype) | `GenderType` | 8 | `Code` | Gender classification reference lookups (Field: Gender_Code - F, M, U). |
| `lookup` | [`GrantTypeType`](#granttypetype) | `GrantTypeType` | 8 | `Code` | MerSETA Discretionary and Mandatory grant allocation funding types. |
| `lookup` | [`HearingRatingType`](#hearingratingtype) | `HearingRatingType` | 8 | `Code` | Washington Group Hearing functional rating (Field: Hearing_Rating_Id - 1: No difficulty to 6: Cannot yet be determined). |
| `lookup` | [`HomeLanguageType`](#homelanguagetype) | `HomeLanguageType` | 8 | `Code` | Official South African home language classifications (Field: Home_Language_Code - 14 statutory codes including SASL). |
| `lookup` | [`HonoursClassType`](#honoursclasstype) | `HonoursClassType` | 8 | `Code` | Higher Education and Umalusi academic achievement classification distinctions (Field: HONOURS_CLASS_ID - Cum Laude, Honours, etc.). |
| `lookup` | [`InternshipStatusType`](#internshipstatustype) | `InternshipStatusType` | 8 | `Code` | Work Integrated Learning (WIL) and Internship lifecycle status (Field: Internship_Status_Id - Active, Completed, Discontinued). |
| `lookup` | [`InterventionType`](#interventiontype) | `InterventionType` | 8 | `Code` | Targeted skills development intervention categories. |
| `lookup` | [`LearnerEvidenceType`](#learnerevidencetype) | `LearnerEvidenceType` | 8 | `Code` | Portfolio of Evidence (PoE) digital artifact and evidence document types. |
| `lookup` | [`LearningProgrammeType`](#learningprogrammetype) | `LearningProgrammeType` | 8 | `Code` | Learning intervention modality types (Field: Learning_Programme_Type_Id - Learnership, Apprenticeship, Skills Programme, Internship, Bursary, etc.). |
| `lookup` | [`NationalityType`](#nationalitytype) | `NationalityType` | 8 | `Code` | SETMIS regional nationality classifications (Field: Nationality_Code - 23 statutory nationality codes). |
| `lookup` | [`NonNqfInterventionStatusType`](#nonnqfinterventionstatustype) | `NonNqfInterventionStatusType` | 8 | `Code` | Non-NQF skills intervention registration status codes (Field: Non_NQF_Interv_Status_Id - Active, Inactive, Provisional). |
| `lookup` | [`OfoCodeType`](#ofocodetype) | `OfoCodeType` | 8 | `Code` | DHET Organising Framework for Occupations occupational classification codes (Field: OFO_Code - 1,454 statutory codes). |
| `lookup` | [`OrganisationType`](#organisationtype) | `OrganisationType` | 8 | `Code` | Enterprise legal constitution types (e.g. Pty Ltd, Close Corporation, Public Entity, NGO/NPO). |
| `lookup` | [`PartOfType`](#partoftype) | `PartOfType` | 8 | `Code` | Programme articulation hierarchy standing (Field: Part_Of_Id - Stand-alone, Part of Qualification, Part of Learnership, etc.). |
| `lookup` | [`PopiActStatusType`](#popiactstatustype) | `PopiActStatusType` | 8 | `Code` | Protection of Personal Information Act compliance consent status (Field: POPI_Act_Status_ID - Agree, Disagree, N/A). |
| `lookup` | [`ProviderClassType`](#providerclasstype) | `ProviderClassType` | 8 | `Code` | Skills Development Provider institutional classification (Field: Provider_Class_Id - Public, Private, NGO, Foreign, etc.). |
| `lookup` | [`ProviderStatusType`](#providerstatustype) | `ProviderStatusType` | 8 | `Code` | Skills Development Provider ETQA accreditation standing codes (Field: Provider_Status_Id - Accredited, Registered, Closed, etc.). |
| `lookup` | [`ProviderType`](#providertype) | `ProviderType` | 8 | `Code` | Skills Development Provider functional entity type (Field: Provider_Type_Id - Education, Training, Employer, NGO). |
| `lookup` | [`ProvinceType`](#provincetype) | `ProvinceType` | 8 | `Code` | South African provincial geopolitical regions (Field: Province_Code - 11 statutory provincial and national codes). |
| `lookup` | [`QualificationTypeType`](#qualificationtypetype) | `QualificationTypeType` | 8 | `Code` | SAQA National Qualifications Framework qualification formal types (Field: QUALIFICATION_TYPE_ID - National Certificate, Diploma, Occupational Certificate, etc.). |
| `lookup` | [`RememberingRatingType`](#rememberingratingtype) | `RememberingRatingType` | 8 | `Code` | Washington Group Memory and Cognitive functional rating (Field: Remembering_Rating_Id - 1: No difficulty to 6: Cannot yet be determined). |
| `lookup` | [`SectorType`](#sectortype) | `SectorType` | 8 | `Code` | MerSETA industrial economic sector classifications (Auto, Metal, Motor Retail, Plastics, Tyre). |
| `lookup` | [`SeeingRatingType`](#seeingratingtype) | `SeeingRatingType` | 8 | `Code` | Washington Group Vision functional rating (Field: Seeing_Rating_Id - 1: No difficulty to 6: Cannot yet be determined). |
| `lookup` | [`SelfCareRatingType`](#selfcareratingtype) | `SelfCareRatingType` | 8 | `Code` | Washington Group Self-Care functional rating (Field: Self_Care_Rating_Id - 1: No difficulty to 6: Cannot yet be determined). |
| `lookup` | [`SetaType`](#setatype) | `SetaType` | 8 | `Code` | South African Sector Education and Training Authorities (Field: SETA_Id - 21 SETAs including MerSETA). |
| `lookup` | [`SicCodeType`](#siccodetype) | `SicCodeType` | 10 | `Code` | Standard Industrial Classification (SIC) 5-digit economic activity codes (Field: SIC_Code - 815 statutory codes). |
| `lookup` | [`SiteVisitApprovalStatusType`](#sitevisitapprovalstatustype) | `SiteVisitApprovalStatusType` | 8 | `Code` | On-site physical facility inspection approval status codes. |
| `lookup` | [`StatssaAreaCodeType`](#statssaareacodetype) | `StatssaAreaCodeType` | 8 | `Code` | Stats SA spatial geographic sub-place and municipal area codes (Field: STATSSA_Area_Code - 22,108 statutory codes). |
| `lookup` | [`StatusType`](#statustype) | `StatusType` | 8 | `Code` | General system and workflow operational status codes (Active, Inactive, Pending, Approved, Rejected, Suspended). |
| `lookup` | [`SubfieldType`](#subfieldtype) | `SubfieldType` | 8 | `Code` | SAQA National Qualifications Framework subfield classifications (Field: Subfield_Id - 68 statutory subfields). |
| `lookup` | [`TradeTestResultReasonType`](#tradetestresultreasontype) | `TradeTestResultReasonType` | 8 | `Code` | Artisan trade test assessment specific outcome reasons (Field: Trade_Test_Result_Reason_Id). |
| `lookup` | [`TradeTestResultType`](#tradetestresulttype) | `TradeTestResultType` | 8 | `Code` | Artisan practical trade test assessment competency outcomes (Field: Trade_Test_Result_Id - Competent, Not yet competent). |
| `lookup` | [`UrbanRuralType`](#urbanruraltype) | `UrbanRuralType` | 8 | `Code` | Spatial demographic urban versus rural intervention classifications (Field: Urban_Rural_ID - Urban, Rural, Unknown). |
| `lookup` | [`VisitTypeType`](#visittypetype) | `VisitTypeType` | 8 | `Code` | Employer on-site inspection and monitoring visit activity types. |
| `lookup` | [`WalkingRatingType`](#walkingratingtype) | `WalkingRatingType` | 8 | `Code` | Washington Group Mobility and Walking functional rating (Field: Walking_Rating_Id - 1: No difficulty to 6: Cannot yet be determined). |

---

## 🏛️ Table Details & Column Specifications

### <a id="approle"></a> `dbo.AppRole`

**Description:** Application security role for CASL/RBAC authorization.  
**CLR Model:** `Nsdms.Domain.Entities.ApplicationRole`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the role is active in the system. |
| `ConcurrencyStamp` | `nvarchar(max)` | NULL |  | Domain property for ConcurrencyStamp. |
| `Description` | `nvarchar(250)` | NULL |  | Descriptive summary of the responsibilities and permissions associated with this role. |
| `Name` | `nvarchar(100)` | **NOT NULL** |  | Display title / name of the record. |
| `NormalizedName` | `nvarchar(100)` | **NOT NULL** |  | Domain property for NormalizedName. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `RoleNameIndex` | `NormalizedName` | ✅ Yes |

---

### <a id="approleclaim"></a> `dbo.AppRoleClaim`

**Description:** System entity for IdentityRoleClaim`1 data governance.  
**CLR Model:** `Nsdms.Domain.Entities.IdentityRoleClaim`1`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ClaimType` | `nvarchar(max)` | NULL |  | Domain property for ClaimType. |
| `ClaimValue` | `nvarchar(max)` | NULL |  | Domain property for ClaimValue. |
| `RoleId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_AppRoleClaim_AppRole_RoleId` | `RoleId` | `dbo.AppRole` | `Cascade` |

---

### <a id="appuser"></a> `dbo.AppUser`

**Description:** Application authentication user account integrated with ASP.NET Core Identity.  
**CLR Model:** `Nsdms.Domain.Entities.ApplicationUser`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AccessFailedCount` | `int` | **NOT NULL** |  | Domain property for AccessFailedCount. |
| `ConcurrencyStamp` | `nvarchar(max)` | NULL |  | Domain property for ConcurrencyStamp. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the user account was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or service that created the account. |
| `DefaultOrganisationId` | `int` | NULL | 🔗 **FK** | Foreign key referencing the primary employer organisation context for external users. |
| `Email` | `nvarchar(150)` | NULL |  | Primary email address. |
| `EmailConfirmed` | `bit` | **NOT NULL** |  | Domain property for EmailConfirmed. |
| `IsActive` | `bit` | **NOT NULL** |  | Indicates whether the login account is enabled and active. |
| `LockoutEnabled` | `bit` | **NOT NULL** |  | Domain property for LockoutEnabled. |
| `LockoutEnd` | `nvarchar(max)` | NULL |  | Domain property for LockoutEnd. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the user account was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier that last modified the account. |
| `NormalizedEmail` | `nvarchar(150)` | NULL |  | Domain property for NormalizedEmail. |
| `NormalizedUserName` | `nvarchar(100)` | **NOT NULL** |  | Domain property for NormalizedUserName. |
| `PasswordHash` | `nvarchar(max)` | NULL |  | Domain property for PasswordHash. |
| `PersonId` | `int` | NULL | 🔗 **FK** | Foreign key referencing the demographic profile in the Person registry. |
| `PhoneNumber` | `nvarchar(50)` | NULL |  | Primary contact telephone number. |
| `PhoneNumberConfirmed` | `bit` | **NOT NULL** |  | Domain property for PhoneNumberConfirmed. |
| `SecurityStamp` | `nvarchar(max)` | NULL |  | Domain property for SecurityStamp. |
| `TwoFactorEnabled` | `bit` | **NOT NULL** |  | Domain property for TwoFactorEnabled. |
| `UserName` | `nvarchar(100)` | **NOT NULL** |  | Domain property for UserName. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_AppUser_Organisation_DefaultOrganisationId` | `DefaultOrganisationId` | `dbo.Organisation` | `SetNull` |
| `FK_AppUser_Person_PersonId` | `PersonId` | `dbo.Person` | `SetNull` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_AppUser_DefaultOrganisationId` | `DefaultOrganisationId` | No |
| `EmailIndex` | `NormalizedEmail` | No |
| `UserNameIndex` | `NormalizedUserName` | ✅ Yes |
| `IX_AppUser_PersonId` | `PersonId` | ✅ Yes |

---

### <a id="appuserclaim"></a> `dbo.AppUserClaim`

**Description:** System entity for IdentityUserClaim`1 data governance.  
**CLR Model:** `Nsdms.Domain.Entities.IdentityUserClaim`1`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ClaimType` | `nvarchar(max)` | NULL |  | Domain property for ClaimType. |
| `ClaimValue` | `nvarchar(max)` | NULL |  | Domain property for ClaimValue. |
| `UserId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_AppUserClaim_AppUser_UserId` | `UserId` | `dbo.AppUser` | `Cascade` |

---

### <a id="appuserlogin"></a> `dbo.AppUserLogin`

**Description:** System entity for IdentityUserLogin`1 data governance.  
**CLR Model:** `Nsdms.Domain.Entities.IdentityUserLogin`1`  
**Primary Key:** `LoginProvider, ProviderKey`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `LoginProvider` | `nvarchar(max)` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ProviderKey` | `nvarchar(max)` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ProviderDisplayName` | `nvarchar(max)` | NULL |  | Domain property for ProviderDisplayName. |
| `UserId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_AppUserLogin_AppUser_UserId` | `UserId` | `dbo.AppUser` | `Cascade` |

---

### <a id="appuserrole"></a> `dbo.AppUserRole`

**Description:** System entity for IdentityUserRole`1 data governance.  
**CLR Model:** `Nsdms.Domain.Entities.IdentityUserRole`1`  
**Primary Key:** `UserId, RoleId`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `UserId` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `RoleId` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_AppUserRole_AppRole_RoleId` | `RoleId` | `dbo.AppRole` | `Cascade` |
| `FK_AppUserRole_AppUser_UserId` | `UserId` | `dbo.AppUser` | `Cascade` |

---

### <a id="appusertoken"></a> `dbo.AppUserToken`

**Description:** System entity for IdentityUserToken`1 data governance.  
**CLR Model:** `Nsdms.Domain.Entities.IdentityUserToken`1`  
**Primary Key:** `UserId, LoginProvider, Name`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `UserId` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `LoginProvider` | `nvarchar(max)` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `Name` | `nvarchar(max)` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `Value` | `nvarchar(max)` | NULL |  | Domain property for Value. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_AppUserToken_AppUser_UserId` | `UserId` | `dbo.AppUser` | `Cascade` |

---

### <a id="aqplearnerassessment"></a> `dbo.AqpLearnerAssessment`

**Description:** External Integrated Summative Assessment (EISA) record administered by an AQP for a registered learner.  
**CLR Model:** `Nsdms.Domain.Entities.AqpLearnerAssessment`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AqpPartnerId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `AssessmentCenter` | `nvarchar(max)` | **NOT NULL** |  | Domain property for AssessmentCenter. |
| `AssessmentDate` | `datetime2` | **NOT NULL** |  | Domain property for AssessmentDate. |
| `AssessmentNumber` | `nvarchar(100)` | **NOT NULL** |  | Domain property for AssessmentNumber. |
| `CertificateIssuedDate` | `datetime2` | NULL |  | Domain property for CertificateIssuedDate. |
| `CertificateNumber` | `nvarchar(max)` | NULL |  | Domain property for CertificateNumber. |
| `CompanyLearnerId` | `int` | NULL | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `EisaExamSession` | `nvarchar(100)` | **NOT NULL** |  | Domain property for EisaExamSession. |
| `FinalOverallPercentage` | `decimal(18,2)` | **NOT NULL** |  | Domain property for FinalOverallPercentage. |
| `ModerationStatusCode` | `nvarchar(max)` | **NOT NULL** |  | Domain property for ModerationStatusCode. |
| `ModeratorComments` | `nvarchar(max)` | NULL |  | Domain property for ModeratorComments. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `PersonId` | `int` | NULL | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `PracticalScorePercentage` | `decimal(18,2)` | NULL |  | Domain property for PracticalScorePercentage. |
| `ResultStatusCode` | `nvarchar(max)` | **NOT NULL** |  | Domain property for ResultStatusCode. |
| `TheoryScorePercentage` | `decimal(18,2)` | NULL |  | Domain property for TheoryScorePercentage. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_AqpLearnerAssessment_AqpPartner_AqpPartnerId` | `AqpPartnerId` | `dbo.AqpPartner` | `Cascade` |
| `FK_AqpLearnerAssessment_CompanyLearner_CompanyLearnerId` | `CompanyLearnerId` | `dbo.CompanyLearner` | `ClientSetNull` |
| `FK_AqpLearnerAssessment_Person_PersonId` | `PersonId` | `dbo.Person` | `ClientSetNull` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_AqpLearnerAssessment_AqpPartnerId` | `AqpPartnerId` | No |
| `IX_AqpLearnerAssessment_CompanyLearnerId` | `CompanyLearnerId` | No |
| `IX_AqpLearnerAssessment_PersonId` | `PersonId` | No |

---

### <a id="aqppartner"></a> `dbo.AqpPartner`

**Description:** Assessment Quality Partner (AQP) accredited by QCTO to develop assessment instruments and manage EISA exams.  
**CLR Model:** `Nsdms.Domain.Entities.AqpPartner`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AccreditationEndDate` | `datetime2` | **NOT NULL** |  | Domain property for AccreditationEndDate. |
| `AccreditationNumber` | `nvarchar(100)` | **NOT NULL** |  | Domain property for AccreditationNumber. |
| `AccreditationStartDate` | `datetime2` | **NOT NULL** |  | Domain property for AccreditationStartDate. |
| `AqpCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for AqpCode. |
| `AqpName` | `nvarchar(200)` | **NOT NULL** |  | Domain property for AqpName. |
| `ContactPersonId` | `int` | NULL | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `Email` | `nvarchar(max)` | NULL |  | Primary email address. |
| `IsActive` | `bit` | **NOT NULL** |  | Indicates whether the record is active and operational. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `PhoneNumber` | `nvarchar(max)` | NULL |  | Primary contact telephone number. |
| `PhysicalAddress` | `nvarchar(max)` | NULL |  | Domain property for PhysicalAddress. |
| `PostalCode` | `nvarchar(max)` | NULL |  | Domain property for PostalCode. |
| `ProvinceCode` | `nvarchar(max)` | **NOT NULL** |  | Domain property for ProvinceCode. |
| `QualityAssuranceBody` | `nvarchar(max)` | **NOT NULL** |  | Domain property for QualityAssuranceBody. |
| `StatusCode` | `nvarchar(max)` | **NOT NULL** |  | Current lifecycle state code in the workflow engine. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_AqpPartner_Person_ContactPersonId` | `ContactPersonId` | `dbo.Person` | `ClientSetNull` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_AqpPartner_AqpCode` | `AqpCode` | No |
| `IX_AqpPartner_IsActive` | `IsActive` | No |

---

### <a id="aqpqualificationscope"></a> `dbo.AqpQualificationScope`

**Description:** Qualifications in scope for which the AQP is authorized to administer external summative assessments (EISA).  
**CLR Model:** `Nsdms.Domain.Entities.AqpQualificationScope`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AqpPartnerId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `AssessmentModel` | `nvarchar(max)` | **NOT NULL** |  | Domain property for AssessmentModel. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `CurriculumCode` | `nvarchar(max)` | NULL |  | Domain property for CurriculumCode. |
| `IsActive` | `bit` | **NOT NULL** |  | Indicates whether the record is active and operational. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `NqfLevel` | `int` | **NOT NULL** |  | Domain property for NqfLevel. |
| `QualificationTitle` | `nvarchar(250)` | **NOT NULL** |  | Domain property for QualificationTitle. |
| `SaqaQualificationId` | `nvarchar(max)` | NULL |  | Domain property for SaqaQualificationId. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_AqpQualificationScope_AqpPartner_AqpPartnerId` | `AqpPartnerId` | `dbo.AqpPartner` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_AqpQualificationScope_AqpPartnerId` | `AqpPartnerId` | No |

---

### <a id="arplexperiencedetail"></a> `dbo.ArplExperienceDetail`

**Description:** Historical work experience item claimed under ARPL.  
**CLR Model:** `Nsdms.Domain.Entities.ArplExperienceDetail`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DutiesDescription` | `nvarchar(max)` | **NOT NULL** |  | Domain property for DutiesDescription. |
| `EmployerName` | `nvarchar(200)` | **NOT NULL** |  | Domain property for EmployerName. |
| `EndDate` | `datetime2` | NULL |  | Domain property for EndDate. |
| `EvidenceDocumentName` | `nvarchar(max)` | NULL |  | Domain property for EvidenceDocumentName. |
| `JobTitle` | `nvarchar(150)` | **NOT NULL** |  | Domain property for JobTitle. |
| `LearnerTradeTestApplicationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `StartDate` | `datetime2` | **NOT NULL** |  | Domain property for StartDate. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_ArplExperienceDetail_LearnerTradeTestApplication_LearnerTradeTestApplicationId` | `LearnerTradeTestApplicationId` | `dbo.LearnerTradeTestApplication` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_ArplExperienceDetail_LearnerTradeTestApplicationId` | `LearnerTradeTestApplicationId` | No |

---

### <a id="arpltradetestinformation"></a> `dbo.ArplTradeTestInformation`

**Description:** Artisan Recognition of Prior Learning (ARPL) Portfolio Assessment record.  
**CLR Model:** `Nsdms.Domain.Entities.ArplTradeTestInformation`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ArplRecommendation` | `nvarchar(100)` | **NOT NULL** |  | Domain property for ArplRecommendation. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `CurrentEmployerName` | `nvarchar(max)` | NULL |  | Domain property for CurrentEmployerName. |
| `EmployerContactPersonName` | `nvarchar(max)` | NULL |  | Domain property for EmployerContactPersonName. |
| `EmployerContactPhone` | `nvarchar(max)` | NULL |  | Domain property for EmployerContactPhone. |
| `LearnerTradeTestApplicationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `PortfolioAssessmentDate` | `datetime2` | NULL |  | Domain property for PortfolioAssessmentDate. |
| `PortfolioAssessorUserId` | `nvarchar(max)` | NULL |  | Domain property for PortfolioAssessorUserId. |
| `PortfolioOfEvidenceVerified` | `bit` | **NOT NULL** |  | Domain property for PortfolioOfEvidenceVerified. |
| `PortfolioScorePercentage` | `decimal(18,2)` | **NOT NULL** |  | Domain property for PortfolioScorePercentage. |
| `ToolkitChecklistVerified` | `bit` | **NOT NULL** |  | Domain property for ToolkitChecklistVerified. |
| `WorkplaceMentorName` | `nvarchar(max)` | NULL |  | Domain property for WorkplaceMentorName. |
| `YearsOfExperienceInTrade` | `int` | **NOT NULL** |  | Domain property for YearsOfExperienceInTrade. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_ArplTradeTestInformation_LearnerTradeTestApplication_LearnerTradeTestApplicationId` | `LearnerTradeTestApplicationId` | `dbo.LearnerTradeTestApplication` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_ArplTradeTestInformation_LearnerTradeTestApplicationId` | `LearnerTradeTestApplicationId` | ✅ Yes |

---

### <a id="arpltrainingdetail"></a> `dbo.ArplTrainingDetail`

**Description:** Prior formal or non-formal training modules completed by an ARPL candidate.  
**CLR Model:** `Nsdms.Domain.Entities.ArplTrainingDetail`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CertificateObtained` | `nvarchar(max)` | NULL |  | Domain property for CertificateObtained. |
| `CompletionDate` | `datetime2` | NULL |  | Domain property for CompletionDate. |
| `CourseOrModuleTitle` | `nvarchar(200)` | **NOT NULL** |  | Domain property for CourseOrModuleTitle. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `InstitutionName` | `nvarchar(200)` | **NOT NULL** |  | Domain property for InstitutionName. |
| `LearnerTradeTestApplicationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_ArplTrainingDetail_LearnerTradeTestApplication_LearnerTradeTestApplicationId` | `LearnerTradeTestApplicationId` | `dbo.LearnerTradeTestApplication` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_ArplTrainingDetail_LearnerTradeTestApplicationId` | `LearnerTradeTestApplicationId` | No |

---

### <a id="assessorcpdactivity"></a> `dbo.AssessorCpdActivity`

**Description:** Individual Continuous Professional Development (CPD) activity logged in support of practitioner renewal.  
**CLR Model:** `Nsdms.Domain.Entities.AssessorCpdActivity`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ActivityCategory` | `nvarchar(50)` | **NOT NULL** |  | Activity category code (IndustryPractice, SetaWorkshop, PeerModeration, CourseAttendance, Mentorship). |
| `ActivityDate` | `datetime2` | **NOT NULL** |  | Date when the CPD activity was undertaken. |
| `ActivityTitle` | `nvarchar(200)` | **NOT NULL** |  | Title or description of the training, moderation workshop, or industry practice. |
| `AssessorReRegistrationApplicationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the parent renewal application. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `EvidenceDocumentRef` | `nvarchar(250)` | NULL |  | Supporting certificate or document file reference. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `PointsApproved` | `int` | **NOT NULL** |  | Number of CPD points officially accredited by the ETQA evaluator. |
| `PointsClaimed` | `int` | **NOT NULL** |  | Number of CPD points claimed by the applicant. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_AssessorCpdActivity_AssessorReRegistrationApplication_AssessorReRegistrationApplicationId` | `AssessorReRegistrationApplicationId` | `dbo.AssessorReRegistrationApplication` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_AssessorCpdActivity_ActivityCategory` | `ActivityCategory` | No |
| `IX_AssessorCpdActivity_AssessorReRegistrationApplicationId` | `AssessorReRegistrationApplicationId` | No |

---

### <a id="assessorextensionofscope"></a> `dbo.AssessorExtensionOfScope`

**Description:** System entity for AssessorExtensionOfScope data governance.  
**CLR Model:** `Nsdms.Domain.Entities.AssessorExtensionOfScope`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ApplicationNumber` | `nvarchar(50)` | **NOT NULL** |  | Domain property for ApplicationNumber. |
| `AssessorPersonId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | **NOT NULL** |  | User identifier or system process that created the record. |
| `EndorsedByUserId` | `nvarchar(max)` | NULL |  | Domain property for EndorsedByUserId. |
| `EndorsementDate` | `datetime2` | NULL |  | Domain property for EndorsementDate. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `NqfLevel` | `int` | **NOT NULL** |  | Domain property for NqfLevel. |
| `PractitionerTypeCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for PractitionerTypeCode. |
| `RelevantIndustryCvVerified` | `bit` | **NOT NULL** |  | Domain property for RelevantIndustryCvVerified. |
| `RequestedQualificationTitle` | `nvarchar(250)` | **NOT NULL** |  | Domain property for RequestedQualificationTitle. |
| `SaqaQualificationId` | `nvarchar(50)` | NULL |  | Domain property for SaqaQualificationId. |
| `StatusCode` | `nvarchar(50)` | **NOT NULL** |  | Current lifecycle state code in the workflow engine. |
| `SubjectMatterCertificateVerified` | `bit` | **NOT NULL** |  | Domain property for SubjectMatterCertificateVerified. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_AssessorExtensionOfScope_Person_AssessorPersonId` | `AssessorPersonId` | `dbo.Person` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_AssessorExtensionOfScope_AssessorPersonId` | `AssessorPersonId` | No |
| `IX_AssessorExtensionOfScope_StatusCode` | `StatusCode` | No |

---

### <a id="assessormoderatorapplication"></a> `dbo.AssessorModeratorApplication`

**Description:** Assessor and Moderator Accreditation Application.  
**CLR Model:** `Nsdms.Domain.Entities.AssessorModeratorApplication`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ApplicationNumber` | `nvarchar(50)` | **NOT NULL** |  | Domain property for ApplicationNumber. |
| `ApplicationTypeCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for ApplicationTypeCode. |
| `CertificateNumber` | `nvarchar(50)` | NULL |  | Domain property for CertificateNumber. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `ExpiryDate` | `datetime2` | NULL |  | Domain property for ExpiryDate. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `PersonId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `ReviewCommitteeMeetingAgendaId` | `int` | NULL | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `StatusCode` | `nvarchar(50)` | **NOT NULL** |  | Current lifecycle state code in the workflow engine. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_AssessorModeratorApplication_Person_PersonId` | `PersonId` | `dbo.Person` | `Restrict` |
| `FK_AssessorModeratorApplication_ReviewCommitteeMeetingAgenda_ReviewCommitteeMeetingAgendaId` | `ReviewCommitteeMeetingAgendaId` | `dbo.ReviewCommitteeMeetingAgenda` | `SetNull` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_AssessorModeratorApplication_ApplicationNumber` | `ApplicationNumber` | No |
| `IX_AssessorModeratorApplication_PersonId` | `PersonId` | No |
| `IX_AssessorModeratorApplication_StatusCode` | `StatusCode` | No |

---

### <a id="assessormoderatorscope"></a> `dbo.AssessorModeratorScope`

**Description:** Specific SAQA qualification registration scope granted to an ETQA Assessor or Moderator.  
**CLR Model:** `Nsdms.Domain.Entities.AssessorModeratorScope`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `EtqaAssessorId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the parent EtqaAssessor. |
| `ExpiryDate` | `datetime2` | NULL |  | Validity expiration date of this qualification scope. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `QualificationTitle` | `nvarchar(200)` | **NOT NULL** |  | Title of the registered qualification in scope. |
| `RegistrationStatusCode` | `nvarchar(15)` | NULL |  | Registration status of this specific scope (e.g. Registered, Expired, Suspended). |
| `SaqaQualificationId` | `int` | **NOT NULL** |  | SAQA Registered Qualification ID code. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_AssessorModeratorScope_EtqaAssessor_EtqaAssessorId` | `EtqaAssessorId` | `dbo.EtqaAssessor` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_AssessorModeratorScope_EtqaAssessorId` | `EtqaAssessorId` | No |
| `IX_AssessorModeratorScope_RegistrationStatusCode` | `RegistrationStatusCode` | No |
| `IX_AssessorModeratorScope_SaqaQualificationId` | `SaqaQualificationId` | No |

---

### <a id="assessorreregistrationapplication"></a> `dbo.AssessorReRegistrationApplication`

**Description:** Represents a statutory 3-year re-registration or scope extension application for ETQA registered Assessors and Moderators.  
**CLR Model:** `Nsdms.Domain.Entities.AssessorReRegistrationApplication`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AdjudicatedByUserId` | `nvarchar(max)` | NULL |  | User ID of the ETQA manager or committee secretary who adjudicated the application. |
| `AdjudicationDate` | `datetime2` | NULL |  | Timestamp when the ETQA committee completed adjudication. |
| `AdjudicationNotes` | `nvarchar(max)` | NULL |  | Adjudication committee feedback or justification notes. |
| `ApplicationReferenceNumber` | `nvarchar(50)` | **NOT NULL** |  | Statutory application tracking reference (e.g. REG-2026-ASS-0042). |
| `ApplicationTypeCode` | `nvarchar(50)` | **NOT NULL** |  | Application type code (e.g. ReRegistration, ScopeExtension, InitialRegistration). |
| `CommitteeDecisionNumber` | `nvarchar(50)` | NULL |  | Official ETQA Committee Decision Number approving the 3-year re-registration. |
| `CpdPointsAccumulated` | `int` | **NOT NULL** |  | Cumulative CPD points accumulated for this renewal cycle (statutory target >= 30 points). |
| `CpdPortfolioSummary` | `nvarchar(max)` | NULL |  | Detailed narrative summary of continuous professional development and workplace practice. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `CurrentExpirationDate` | `datetime2` | **NOT NULL** |  | Practitioner expiration date prior to renewal. |
| `DigitalSecuritySeal` | `nvarchar(64)` | NULL |  | Immutable SHA-256 digital security seal certifying renewal approval and certificate issuance. |
| `EtqaAssessorId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the parent EtqaAssessor record. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `ProposedNewExpirationDate` | `datetime2` | **NOT NULL** |  | Proposed 3-year extended expiration date upon approval. |
| `ReviewStatusCode` | `nvarchar(50)` | **NOT NULL** |  | Current adjudication review status (Draft, Submitted, CommitteeReview, Approved, Rejected, AdditionalInfoRequired). |
| `ScopeConfirmationJson` | `nvarchar(max)` | **NOT NULL** |  | JSON serialized snapshot of confirmed renewal scopes and requested new unit standards. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_AssessorReRegistrationApplication_EtqaAssessor_EtqaAssessorId` | `EtqaAssessorId` | `dbo.EtqaAssessor` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_AssessorReRegistrationApplication_ApplicationReferenceNumber` | `ApplicationReferenceNumber` | ✅ Yes |
| `IX_AssessorReRegistrationApplication_EtqaAssessorId` | `EtqaAssessorId` | No |
| `IX_AssessorReRegistrationApplication_ReviewStatusCode` | `ReviewStatusCode` | No |

---

### <a id="auditlog"></a> `dbo.AuditLog`

**Description:** Immutable operational audit trail recording entity mutations, actor identity, timestamps, and JSON snapshots.  
**CLR Model:** `Nsdms.Domain.Entities.AuditLog`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `bigint` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ActionName` | `nvarchar(50)` | **NOT NULL** |  | Specific CRUD or workflow action executed (e.g. Created, Updated, Deleted, StatusChanged, Disbursed). |
| `Actor` | `nvarchar(100)` | **NOT NULL** |  | Username, email, or system process identity of the actor performing the action. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `EntityName` | `nvarchar(100)` | **NOT NULL** |  | Name of the target domain entity modified (e.g. Organisation, CompanyLearner, GrantMoa). |
| `MetadataJson` | `nvarchar(max)` | NULL |  | Structured JSON payload capturing before/after state snapshots and contextual metadata. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `RecordId` | `bigint` | **NOT NULL** |  | Primary key identifier of the target record (64-bit integer supporting high-volume entities). |
| `Timestamp` | `datetime2` | **NOT NULL** |  | Exact UTC timestamp when the audit event occurred. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_AuditLog_Timestamp` | `Timestamp` | No |
| `IX_AuditLog_EntityName_RecordId` | `EntityName, RecordId` | No |

---

### <a id="bankingdetails"></a> `dbo.BankingDetails`

**Description:** System entity for BankingDetails data governance.  
**CLR Model:** `Nsdms.Domain.Entities.BankingDetails`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AccountHolderName` | `nvarchar(50)` | **NOT NULL** |  | Domain property for AccountHolderName. |
| `AccountNumber` | `nvarchar(50)` | **NOT NULL** |  | Domain property for AccountNumber. |
| `AccountTypeCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for AccountTypeCode. |
| `ApprovalStatusCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for ApprovalStatusCode. |
| `AvsStatusResponse` | `nvarchar(max)` | NULL |  | Domain property for AvsStatusResponse. |
| `AvsVerificationReference` | `nvarchar(max)` | NULL |  | Domain property for AvsVerificationReference. |
| `AvsVerifiedAt` | `datetime2` | NULL |  | Domain property for AvsVerifiedAt. |
| `BankConfirmationDate` | `datetime2` | NULL |  | Domain property for BankConfirmationDate. |
| `BankConfirmationDocumentId` | `int` | NULL |  | Domain property for BankConfirmationDocumentId. |
| `BankConfirmationDocumentPath` | `nvarchar(200)` | NULL |  | Domain property for BankConfirmationDocumentPath. |
| `BankName` | `nvarchar(100)` | **NOT NULL** |  | Domain property for BankName. |
| `BranchCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for BranchCode. |
| `BranchName` | `nvarchar(100)` | NULL |  | Domain property for BranchName. |
| `CoolingOffExpiresAt` | `datetime2` | NULL |  | Domain property for CoolingOffExpiresAt. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | **NOT NULL** |  | User identifier or system process that created the record. |
| `ErpSyncDate` | `datetime2` | NULL |  | Domain property for ErpSyncDate. |
| `ErpVendorId` | `nvarchar(max)` | NULL |  | Domain property for ErpVendorId. |
| `FirstSignoffDate` | `datetime2` | NULL |  | Domain property for FirstSignoffDate. |
| `FirstSignoffNotes` | `nvarchar(max)` | NULL |  | Domain property for FirstSignoffNotes. |
| `FirstSignoffUserId` | `nvarchar(max)` | NULL |  | Domain property for FirstSignoffUserId. |
| `FraudRiskFlags` | `nvarchar(max)` | NULL |  | Domain property for FraudRiskFlags. |
| `IsActive` | `bit` | **NOT NULL** |  | Indicates whether the record is active and operational. |
| `IsCoolingOffActive` | `bit` | **NOT NULL** |  | Domain property for IsCoolingOffActive. |
| `IsErpActive` | `bit` | **NOT NULL** |  | Domain property for IsErpActive. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `OrganisationId` | `int` | NULL | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `RequiresForensicApproval` | `bit` | **NOT NULL** |  | Domain property for RequiresForensicApproval. |
| `SecondSignoffDate` | `datetime2` | NULL |  | Domain property for SecondSignoffDate. |
| `SecondSignoffNotes` | `nvarchar(max)` | NULL |  | Domain property for SecondSignoffNotes. |
| `SecondSignoffUserId` | `nvarchar(max)` | NULL |  | Domain property for SecondSignoffUserId. |
| `TrainingProviderId` | `int` | NULL | 🔗 **FK** | Foreign key relational reference to parent entity. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_BankingDetails_Organisation_OrganisationId` | `OrganisationId` | `dbo.Organisation` | `ClientSetNull` |
| `FK_BankingDetails_TrainingProvider_TrainingProviderId` | `TrainingProviderId` | `dbo.TrainingProvider` | `ClientSetNull` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_BankingDetails_ApprovalStatusCode` | `ApprovalStatusCode` | No |
| `IX_BankingDetails_OrganisationId` | `OrganisationId` | No |
| `IX_BankingDetails_TrainingProviderId` | `TrainingProviderId` | No |

---

### <a id="bankingdetailsaudit"></a> `dbo.BankingDetailsAudit`

**Description:** System entity for BankingDetailsAudit data governance.  
**CLR Model:** `Nsdms.Domain.Entities.BankingDetailsAudit`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ActionType` | `nvarchar(100)` | **NOT NULL** |  | Domain property for ActionType. |
| `BankingDetailsId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `ChangedAt` | `datetime2` | **NOT NULL** |  | Domain property for ChangedAt. |
| `ChangedByUserId` | `nvarchar(100)` | **NOT NULL** |  | Domain property for ChangedByUserId. |
| `IpAddress` | `nvarchar(max)` | NULL |  | Domain property for IpAddress. |
| `NewStateJson` | `nvarchar(max)` | NULL |  | Domain property for NewStateJson. |
| `PreviousStateJson` | `nvarchar(max)` | NULL |  | Domain property for PreviousStateJson. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_BankingDetailsAudit_BankingDetails_BankingDetailsId` | `BankingDetailsId` | `dbo.BankingDetails` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_BankingDetailsAudit_BankingDetailsId` | `BankingDetailsId` | No |
| `IX_BankingDetailsAudit_ChangedAt` | `ChangedAt` | No |

---

### <a id="companylearner"></a> `dbo.CompanyLearner`

**Description:** Represents a learner registration agreement linked to an employer organisation, training provider, and MerSETA learning programme, capturing all statutory fields for SETMIS Files 500, 501, 502, and 506.  
**CLR Model:** `Nsdms.Domain.Entities.CompanyLearner`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AssessorEtqaId` | `nvarchar(10)` | NULL |  | Submitting ETQA ID of the registered Assessor (references lookup.SetaType, default 17 for merSETA). |
| `AssessorRegistrationNumber` | `nvarchar(50)` | NULL |  | Registered ETQA Assessor registration number assigned to this learner agreement (SETMIS Files 500, 501, 502). |
| `CertificateNumber` | `nvarchar(100)` | NULL |  | SAQA or merSETA National Certificate Serial Number awarded upon successful achievement. |
| `ChamberCode` | `nvarchar(max)` | NULL |  | MerSETA industrial chamber code (e.g. Auto, Metal, Plastic, Motor, New Tyre). |
| `CommencementDate` | `datetime2` | NULL |  | Commencement start date of training in the workplace / provider. |
| `CompletionDate` | `datetime2` | NULL |  | Actual completion or certification signoff date. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `CumulativeSpend` | `decimal(18,2)` | **NOT NULL** |  | Cumulative Discretionary Grant / Levy stipend funding disbursed to date in ZAR. |
| `EconomicStatusId` | `nvarchar(10)` | NULL |  | Learner economic employment standing code (references lookup.EconomicStatusType: 01 Employed 18.1, 02 Unemployed 18.2, 03 Student). |
| `EnrolmentStatusCode` | `nvarchar(50)` | NULL |  | Legacy enrolment status code mapping. |
| `EnrolmentStatusDate` | `datetime2` | NULL |  | Date when current enrolment status milestone became effective (SETMIS Files 500, 501, 502). |
| `EnrolmentStatusId` | `nvarchar(10)` | NULL |  | Current statutory enrolment lifecycle status code (references lookup.EnrolmentStatusType: 01 Enrolled, 02 Achieved, 03 Certificated, 04 Terminated, 05 Transferred). |
| `EnrolmentStatusReasonId` | `nvarchar(10)` | NULL |  | Statutory reason code for contract termination or status change (references lookup.EnrolmentStatusReasonType). |
| `EnrolmentTypeId` | `nvarchar(10)` | NULL |  | Learner delivery mode classification code (references lookup.EnrolmentType: 01 Contact, 02 Distance, 03 Mixed Mode, 04 Workplace Based). |
| `ExpectedCompletionDate` | `datetime2` | NULL |  | Projected completion date based on registered programme curriculum duration. |
| `FundingId` | `nvarchar(10)` | NULL |  | Funding source lookup identifier (references lookup.FundingType: 01 SETA Funded, 02 Employer Funded, 03 NSF). |
| `FundingTypeCode` | `nvarchar(50)` | NULL |  | Funding vehicle classification code (e.g. MandatoryGrant, DiscretionaryGrant, SelfFunded). |
| `InternshipStatusId` | `nvarchar(10)` | NULL |  | Work Integrated Learning / Internship milestone standing (references lookup.InternshipStatusType: 01 In Progress, 02 Completed, 03 Withdrawn). |
| `IsActive` | `bit` | **NOT NULL** |  | Indicates whether the learner record is active in reporting and SETMIS submissions. |
| `LearnerContractNumber` | `nvarchar(50)` | NULL |  | Unique MerSETA-generated learner agreement / contract registration number. |
| `LearnershipId` | `nvarchar(50)` | NULL |  | Registered SAQA Learnership ID Code (e.g. 18Q180026241203) for SETMIS File 500 and 501. |
| `LearningProgrammeTypeCode` | `nvarchar(50)` | NULL |  | Classification code of the learning programme (references lookup.LearningProgrammeType: 01 Apprenticeship, 02 Learnership, 03 Skills Programme, 04 Internship, 05 Bursary). |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `NonNqfInterventionCode` | `nvarchar(50)` | NULL |  | Registered Non-NQF Skills Programme or Course Code for SETMIS File 502 (references SkillsRegistration.CourseCode). |
| `NqfLevel` | `int` | NULL |  | National Qualifications Framework (NQF) level descriptor (e.g. 2, 3, 4, 5). |
| `OfoCode` | `nvarchar(20)` | NULL |  | DHET Organising Framework for Occupations statutory code (references lookup.OfoCodeType, e.g. 264202 Editor, 651202 Welder). |
| `OrganisationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the host or sponsoring Employer Organisation. |
| `OrganisationSiteId` | `int` | NULL | 🔗 **FK** | Optional foreign key referencing the specific host branch / plant facility. |
| `PartOfId` | `nvarchar(10)` | NULL |  | Programme hierarchy and articulation type code (references lookup.PartOfType: 01 Stand-alone, 02 Part of Qualification, 03 Part of Learnership). |
| `PersonId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the demographic Person record (names, RSA ID, contact details). |
| `PracticalProviderCode` | `nvarchar(50)` | NULL |  | Secondary practical training provider code for decentralized apprenticeship workplace programmes (SETMIS File 501). |
| `PracticalProviderEtqaId` | `nvarchar(10)` | NULL |  | Secondary practical training provider ETQA ID (default 17). |
| `PriorQualificationAchievementDate` | `datetime2` | NULL |  | Date when prior qualification was achieved. |
| `PriorQualificationId` | `nvarchar(50)` | NULL |  | Prior completed TVET / Higher Education qualification ID achieved before entering internship. |
| `QualificationTitle` | `nvarchar(250)` | **NOT NULL** |  | Title of the registered SAQA / QCTO qualification or learning programme. |
| `RegistrationDate` | `datetime2` | **NOT NULL** |  | Official date when the learner contract was officially registered with MerSETA. |
| `SaqaQualificationId` | `int` | NULL |  | SAQA Registered Qualification ID code (SETMIS File 501, 505, 506). |
| `SetaRegion` | `nvarchar(max)` | NULL |  | MerSETA regional office responsible for managing this learner agreement. |
| `TrainingProviderId` | `int` | NULL | 🔗 **FK** | Optional foreign key referencing the accredited Skills Development Training Provider (SDP). |
| `UrbanRuralId` | `nvarchar(10)` | NULL |  | Geographic intervention area classification (references lookup.UrbanRuralType: 01 Urban, 02 Rural, 98 Unknown). |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_CompanyLearner_Organisation_OrganisationId` | `OrganisationId` | `dbo.Organisation` | `Restrict` |
| `FK_CompanyLearner_OrganisationSite_OrganisationSiteId` | `OrganisationSiteId` | `dbo.OrganisationSite` | `SetNull` |
| `FK_CompanyLearner_Person_PersonId` | `PersonId` | `dbo.Person` | `Restrict` |
| `FK_CompanyLearner_TrainingProvider_TrainingProviderId` | `TrainingProviderId` | `dbo.TrainingProvider` | `Restrict` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_CompanyLearner_EconomicStatusId` | `EconomicStatusId` | No |
| `IX_CompanyLearner_EnrolmentStatusCode` | `EnrolmentStatusCode` | No |
| `IX_CompanyLearner_EnrolmentStatusId` | `EnrolmentStatusId` | No |
| `IX_CompanyLearner_EnrolmentTypeId` | `EnrolmentTypeId` | No |
| `IX_CompanyLearner_FundingId` | `FundingId` | No |
| `IX_CompanyLearner_LearnerContractNumber` | `LearnerContractNumber` | No |
| `IX_CompanyLearner_LearnershipId` | `LearnershipId` | No |
| `IX_CompanyLearner_NonNqfInterventionCode` | `NonNqfInterventionCode` | No |
| `IX_CompanyLearner_OfoCode` | `OfoCode` | No |
| `IX_CompanyLearner_OrganisationId` | `OrganisationId` | No |
| `IX_CompanyLearner_OrganisationSiteId` | `OrganisationSiteId` | No |
| `IX_CompanyLearner_PartOfId` | `PartOfId` | No |
| `IX_CompanyLearner_PersonId` | `PersonId` | No |
| `IX_CompanyLearner_RegistrationDate` | `RegistrationDate` | No |
| `IX_CompanyLearner_TrainingProviderId` | `TrainingProviderId` | No |
| `IX_CompanyLearner_UrbanRuralId` | `UrbanRuralId` | No |

---

### <a id="companylearnerchangerequest"></a> `dbo.CompanyLearnerChangeRequest`

**Description:** Formal change request for amending an active registered CompanyLearner contract or demographic details.  
**CLR Model:** `Nsdms.Domain.Entities.CompanyLearnerChangeRequest`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ApprovalDate` | `datetime2` | NULL |  | Domain property for ApprovalDate. |
| `ApprovedByUserId` | `nvarchar(max)` | NULL |  | Domain property for ApprovedByUserId. |
| `ChangeStatusCode` | `nvarchar(50)` | **NOT NULL** |  | Status code: Pending, Approved, Rejected. |
| `ChangeTypeCode` | `nvarchar(50)` | **NOT NULL** |  | Type of amendment: DemographicCorrection, QualificationAmendment, ProviderChange, ContractDateExtension, StipendAdjustment. |
| `CompanyLearnerId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `CurrentValuesSnapshotJson` | `nvarchar(max)` | **NOT NULL** |  | Serialized JSON snapshot of the learner record state prior to amendment. |
| `JustificationReason` | `nvarchar(max)` | **NOT NULL** |  | Applicant or employer justification for the requested changes. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `RequestedValuesJson` | `nvarchar(max)` | **NOT NULL** |  | Serialized JSON containing the proposed field values. |
| `ReviewDate` | `datetime2` | NULL |  | Domain property for ReviewDate. |
| `ReviewedByUserId` | `nvarchar(max)` | NULL |  | Domain property for ReviewedByUserId. |
| `ReviewerComments` | `nvarchar(max)` | NULL |  | Domain property for ReviewerComments. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_CompanyLearnerChangeRequest_CompanyLearner_CompanyLearnerId` | `CompanyLearnerId` | `dbo.CompanyLearner` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_CompanyLearnerChangeRequest_ChangeStatusCode` | `ChangeStatusCode` | No |
| `IX_CompanyLearnerChangeRequest_CompanyLearnerId` | `CompanyLearnerId` | No |

---

### <a id="companylearnerlosttime"></a> `dbo.CompanyLearnerLostTime`

**Description:** Tracks suspended or lost training time and recalculates revised contract end dates.  
**CLR Model:** `Nsdms.Domain.Entities.CompanyLearnerLostTime`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ApprovalComments` | `nvarchar(1000)` | NULL |  | Verification and approval justification comments. |
| `ApprovalDate` | `datetime2` | NULL |  | Timestamp when the lost time extension was approved. |
| `ApprovedByUserId` | `nvarchar(100)` | NULL |  | User identifier of the approving MerSETA official. |
| `CompanyLearnerId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the affected CompanyLearner record. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DaysLost` | `int` | **NOT NULL** |  | Total cumulative days lost during this interruption period. |
| `EndDate` | `datetime2` | **NOT NULL** |  | Date when the training interruption ended. |
| `LostTimeReasonCode` | `nvarchar(50)` | **NOT NULL** |  | Categorized reason code for training interruption (e.g. MaternityLeave, MedicalLeave, Suspension, WorkplaceShutdown). |
| `LostTimeStatusCode` | `nvarchar(50)` | **NOT NULL** |  | Current approval status code for the time extension request (Pending, Approved, Rejected). |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `OriginalContractEndDate` | `datetime2` | **NOT NULL** |  | Original contractual completion date prior to lost time extension. |
| `RevisedContractEndDate` | `datetime2` | **NOT NULL** |  | Revised contractual completion date extended by the lost days. |
| `StartDate` | `datetime2` | **NOT NULL** |  | Date when the training interruption commenced. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_CompanyLearnerLostTime_CompanyLearner_CompanyLearnerId` | `CompanyLearnerId` | `dbo.CompanyLearner` | `Restrict` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_CompanyLearnerLostTime_CompanyLearnerId` | `CompanyLearnerId` | No |
| `IX_CompanyLearnerLostTime_LostTimeStatusCode` | `LostTimeStatusCode` | No |

---

### <a id="companylearnertermination"></a> `dbo.CompanyLearnerTermination`

**Description:** Manages formal bilateral or unilateral cancellation of learner contracts.  
**CLR Model:** `Nsdms.Domain.Entities.CompanyLearnerTermination`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ApprovalComments` | `nvarchar(1000)` | NULL |  | Adjudication and approval decision notes. |
| `ApprovalDate` | `datetime2` | NULL |  | Timestamp when the termination was formally approved. |
| `ApprovedByUserId` | `nvarchar(100)` | NULL |  | User identifier of the authorized MerSETA official approving the cancellation. |
| `CompanyLearnerId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the terminated CompanyLearner record. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DisputeLogged` | `bit` | **NOT NULL** |  | Indicates whether a formal labour dispute or CCMA conciliation is lodged. |
| `EffectiveDate` | `datetime2` | **NOT NULL** |  | Official date on which the learner agreement was terminated. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `SettlementNotes` | `nvarchar(2000)` | NULL |  | Detailed settlement notes, mutual release terms, or disciplinary minutes. |
| `TerminationReasonCode` | `nvarchar(50)` | **NOT NULL** |  | Statutory contract cancellation reason code (e.g. Deceased, Absconded, DismissedForMisconduct, MutualCancellation, MedicalIncapacity). |
| `TerminationStatusCode` | `nvarchar(50)` | **NOT NULL** |  | Current cancellation approval status code (Pending, Approved, Rejected). |
| `UnionRepresentativeName` | `nvarchar(150)` | NULL |  | Name of the trade union representative or legal counsel involved. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_CompanyLearnerTermination_CompanyLearner_CompanyLearnerId` | `CompanyLearnerId` | `dbo.CompanyLearner` | `Restrict` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_CompanyLearnerTermination_CompanyLearnerId` | `CompanyLearnerId` | No |
| `IX_CompanyLearnerTermination_TerminationStatusCode` | `TerminationStatusCode` | No |

---

### <a id="companylearnertransfer"></a> `dbo.CompanyLearnerTransfer`

**Description:** Tracks transfer of an active learner contract from one employer to another.  
**CLR Model:** `Nsdms.Domain.Entities.CompanyLearnerTransfer`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ApprovalComments` | `nvarchar(1000)` | NULL |  | Reviewer or approval justification notes. |
| `ApprovalDate` | `datetime2` | NULL |  | Timestamp when the transfer was formally approved. |
| `ApprovedByUserId` | `nvarchar(100)` | NULL |  | User identifier of the approving MerSETA official. |
| `CompanyLearnerId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the active CompanyLearner record. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `EffectiveDate` | `datetime2` | **NOT NULL** |  | Effective operational start date at the new employer. |
| `FromOrganisationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the releasing employer Organisation. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `ToOrganisationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the receiving employer Organisation. |
| `TransferDate` | `datetime2` | **NOT NULL** |  | Date when the transfer request was formally initiated. |
| `TransferReasonCode` | `nvarchar(50)` | **NOT NULL** |  | Transfer rationale code (e.g. CompanyDownsized, MutualAgreement, Relocation, DisputeResolution). |
| `TransferStatusCode` | `nvarchar(50)` | **NOT NULL** |  | Current transfer workflow state code (Pending, Approved, Rejected, Completed). |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_CompanyLearnerTransfer_CompanyLearner_CompanyLearnerId` | `CompanyLearnerId` | `dbo.CompanyLearner` | `Restrict` |
| `FK_CompanyLearnerTransfer_Organisation_FromOrganisationId` | `FromOrganisationId` | `dbo.Organisation` | `Restrict` |
| `FK_CompanyLearnerTransfer_Organisation_ToOrganisationId` | `ToOrganisationId` | `dbo.Organisation` | `Restrict` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_CompanyLearnerTransfer_CompanyLearnerId` | `CompanyLearnerId` | No |
| `IX_CompanyLearnerTransfer_FromOrganisationId` | `FromOrganisationId` | No |
| `IX_CompanyLearnerTransfer_ToOrganisationId` | `ToOrganisationId` | No |
| `IX_CompanyLearnerTransfer_TransferStatusCode` | `TransferStatusCode` | No |

---

### <a id="contractaddenda"></a> `dbo.ContractAddenda`

**Description:** System entity for ContractAddenda data governance.  
**CLR Model:** `Nsdms.Domain.Entities.ContractAddenda`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AddendaNumber` | `nvarchar(50)` | **NOT NULL** |  | Domain property for AddendaNumber. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | **NOT NULL** |  | User identifier or system process that created the record. |
| `ExecutiveApprovalDate` | `datetime2` | NULL |  | Domain property for ExecutiveApprovalDate. |
| `ExecutiveApprovedByUserId` | `nvarchar(max)` | NULL |  | Domain property for ExecutiveApprovedByUserId. |
| `GrantMoaId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `LegalReviewComments` | `nvarchar(max)` | NULL |  | Domain property for LegalReviewComments. |
| `LegalReviewDate` | `datetime2` | NULL |  | Domain property for LegalReviewDate. |
| `LegalReviewerUserId` | `nvarchar(max)` | NULL |  | Domain property for LegalReviewerUserId. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `MotivationReason` | `nvarchar(1000)` | **NOT NULL** |  | Domain property for MotivationReason. |
| `OriginalContractValue` | `decimal(18,2)` | **NOT NULL** |  | Domain property for OriginalContractValue. |
| `OriginalEndDate` | `datetime2` | **NOT NULL** |  | Domain property for OriginalEndDate. |
| `RevisedContractValue` | `decimal(18,2)` | **NOT NULL** |  | Domain property for RevisedContractValue. |
| `RevisedEndDate` | `datetime2` | **NOT NULL** |  | Domain property for RevisedEndDate. |
| `StatusCode` | `nvarchar(50)` | **NOT NULL** |  | Current lifecycle state code in the workflow engine. |
| `VariationTypeCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for VariationTypeCode. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_ContractAddenda_GrantMoa_GrantMoaId` | `GrantMoaId` | `dbo.GrantMoa` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_ContractAddenda_AddendaNumber` | `AddendaNumber` | ✅ Yes |
| `IX_ContractAddenda_GrantMoaId` | `GrantMoaId` | No |
| `IX_ContractAddenda_StatusCode` | `StatusCode` | No |

---

### <a id="contractextensionrequest"></a> `dbo.ContractExtensionRequest`

**Description:** System entity for ContractExtensionRequest data governance.  
**CLR Model:** `Nsdms.Domain.Entities.ContractExtensionRequest`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | **NOT NULL** |  | User identifier or system process that created the record. |
| `CurrentEndDate` | `datetime2` | **NOT NULL** |  | Domain property for CurrentEndDate. |
| `GrantMoaId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `MitigationPlanSummary` | `nvarchar(1000)` | **NOT NULL** |  | Domain property for MitigationPlanSummary. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `ProjectProgressStatus` | `nvarchar(1000)` | **NOT NULL** |  | Domain property for ProjectProgressStatus. |
| `ProposedNewEndDate` | `datetime2` | **NOT NULL** |  | Domain property for ProposedNewEndDate. |
| `RequestNumber` | `nvarchar(50)` | **NOT NULL** |  | Domain property for RequestNumber. |
| `RequestedExtensionMonths` | `int` | **NOT NULL** |  | Domain property for RequestedExtensionMonths. |
| `ReviewDate` | `datetime2` | NULL |  | Domain property for ReviewDate. |
| `ReviewedByUserId` | `nvarchar(max)` | NULL |  | Domain property for ReviewedByUserId. |
| `ReviewerComments` | `nvarchar(max)` | NULL |  | Domain property for ReviewerComments. |
| `StatusCode` | `nvarchar(50)` | **NOT NULL** |  | Current lifecycle state code in the workflow engine. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_ContractExtensionRequest_GrantMoa_GrantMoaId` | `GrantMoaId` | `dbo.GrantMoa` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_ContractExtensionRequest_GrantMoaId` | `GrantMoaId` | No |
| `IX_ContractExtensionRequest_StatusCode` | `StatusCode` | No |

---

### <a id="contractterminationrequest"></a> `dbo.ContractTerminationRequest`

**Description:** System entity for ContractTerminationRequest data governance.  
**CLR Model:** `Nsdms.Domain.Entities.ContractTerminationRequest`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ClawbackAmountRecoverable` | `decimal(18,2)` | **NOT NULL** |  | Domain property for ClawbackAmountRecoverable. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | **NOT NULL** |  | User identifier or system process that created the record. |
| `DetailedMotivation` | `nvarchar(1000)` | **NOT NULL** |  | Domain property for DetailedMotivation. |
| `GrantMoaId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `SettledByUserId` | `nvarchar(max)` | NULL |  | Domain property for SettledByUserId. |
| `SettlementDate` | `datetime2` | NULL |  | Domain property for SettlementDate. |
| `StatusCode` | `nvarchar(50)` | **NOT NULL** |  | Current lifecycle state code in the workflow engine. |
| `TerminationNumber` | `nvarchar(50)` | **NOT NULL** |  | Domain property for TerminationNumber. |
| `TerminationReasonCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for TerminationReasonCode. |
| `TotalFundsDisbursedToDate` | `decimal(18,2)` | **NOT NULL** |  | Domain property for TotalFundsDisbursedToDate. |
| `TotalValueDeliverablesAchieved` | `decimal(18,2)` | **NOT NULL** |  | Domain property for TotalValueDeliverablesAchieved. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_ContractTerminationRequest_GrantMoa_GrantMoaId` | `GrantMoaId` | `dbo.GrantMoa` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_ContractTerminationRequest_GrantMoaId` | `GrantMoaId` | No |
| `IX_ContractTerminationRequest_StatusCode` | `StatusCode` | No |

---

### <a id="curriculumworkinggroupmember"></a> `dbo.CurriculumWorkingGroupMember`

**Description:** Expert stakeholder member participating in the QCTO Qualification Development Working Group.  
**CLR Model:** `Nsdms.Domain.Entities.CurriculumWorkingGroupMember`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `EmailAddress` | `nvarchar(max)` | **NOT NULL** |  | Domain property for EmailAddress. |
| `IsConfirmedAttendee` | `bit` | **NOT NULL** |  | Domain property for IsConfirmedAttendee. |
| `MemberName` | `nvarchar(150)` | **NOT NULL** |  | Domain property for MemberName. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `OrganisationRepresented` | `nvarchar(200)` | **NOT NULL** |  | Domain property for OrganisationRepresented. |
| `PersonId` | `int` | NULL | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `PhoneNumber` | `nvarchar(max)` | **NOT NULL** |  | Primary contact telephone number. |
| `QualificationsCurriculumDevelopmentId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `StakeholderRoleTitle` | `nvarchar(100)` | **NOT NULL** |  | Domain property for StakeholderRoleTitle. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_CurriculumWorkingGroupMember_Person_PersonId` | `PersonId` | `dbo.Person` | `ClientSetNull` |
| `FK_CurriculumWorkingGroupMember_QualificationsCurriculumDevelopment_QualificationsCurriculumDevelopmentId` | `QualificationsCurriculumDevelopmentId` | `dbo.QualificationsCurriculumDevelopment` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_CurriculumWorkingGroupMember_QualificationsCurriculumDevelopmentId` | `QualificationsCurriculumDevelopmentId` | No |

---

### <a id="documentattachment"></a> `dbo.DocumentAttachment`

**Description:** Polymorphic document attachment linked to any entity record.  
**CLR Model:** `Nsdms.Domain.Entities.DocumentAttachment`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ContentType` | `nvarchar(100)` | **NOT NULL** |  | MIME content type (e.g. application/pdf, image/png). |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DocumentCategoryCode` | `nvarchar(50)` | NULL |  | Document categorization code (e.g. ID_DOCUMENT, QUALIFICATION_CERT, SITE_PHOTO, BANK_CONFIRMATION, SIGNED_MOA). |
| `FileHashSha256` | `nvarchar(100)` | NULL |  | Cryptographic SHA-256 integrity hash for document tampering verification. |
| `FileName` | `nvarchar(255)` | **NOT NULL** |  | Stored sanitized file name. |
| `FileSizeBytes` | `bigint` | **NOT NULL** |  | File size in bytes. |
| `IsArchived` | `bit` | **NOT NULL** |  | Indicates whether the document has been archived or soft-deleted. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `OriginalFileName` | `nvarchar(255)` | **NOT NULL** |  | Original file name as uploaded by the user. |
| `StoragePath` | `nvarchar(500)` | **NOT NULL** |  | Relative or absolute storage path URI. |
| `StorageProvider` | `nvarchar(50)` | **NOT NULL** |  | Storage provider engine (e.g. Local, AzureBlob, Database). |
| `TargetEntityId` | `int` | **NOT NULL** |  | Primary key identifier of the associated target entity. |
| `TargetEntityName` | `nvarchar(100)` | **NOT NULL** |  | Name of the target entity type (e.g. Organisation, CompanyLearner, GrantMoa, WorkplaceApproval). |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_DocumentAttachment_DocumentCategoryCode` | `DocumentCategoryCode` | No |
| `IX_DocumentAttachment_IsArchived` | `IsArchived` | No |
| `IX_DocumentAttachment_TargetEntityName_TargetEntityId` | `TargetEntityName, TargetEntityId` | No |

---

### <a id="documentclause"></a> `dbo.DocumentClause`

**Description:** Reusable atomic document clause or statutory boilerplate paragraph.  
**CLR Model:** `Nsdms.Domain.Entities.DocumentClause`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `Category` | `nvarchar(50)` | **NOT NULL** |  | Classification category matching document domains (e.g. MandatoryGrant, TradeTest, EtqaAccreditation, Compliance, Signatures). |
| `ClauseCode` | `nvarchar(50)` | **NOT NULL** |  | Unique clause reference code (e.g. CL-WSP-APPROVE-BODY, CL-TRADE-COMPETENT, CL-POPIA-NOTICE). |
| `ClauseContent` | `nvarchar(max)` | **NOT NULL** |  | Rich Markdown content containing dynamic evaluation tokens (e.g. {{RecipientName}}, {{CertificateNumber}}, {{IssuedDate}}). |
| `ClauseTitle` | `nvarchar(200)` | **NOT NULL** |  | Human-readable title of the clause. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `IsActive` | `bit` | **NOT NULL** |  | Whether this clause is active for inclusion. |
| `IsMandatory` | `bit` | **NOT NULL** |  | Indicates if this clause is legally mandatory across all template variants. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_DocumentClause_Category` | `Category` | No |
| `IX_DocumentClause_ClauseCode` | `ClauseCode` | ✅ Yes |
| `IX_DocumentClause_IsActive` | `IsActive` | No |

---

### <a id="documentmetadata"></a> `dbo.DocumentMetadata`

**Description:** SHA-256 integrity-verified digital document evidence stored in the Document Vault.  
**CLR Model:** `Nsdms.Domain.Entities.DocumentMetadata`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ContentType` | `nvarchar(100)` | **NOT NULL** |  | MIME content type (e.g. application/pdf, image/png). |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DocumentTypeCode` | `nvarchar(50)` | **NOT NULL** |  | Document taxonomy type code (e.g. RSA_ID, PROOF_OF_BANKING, ACCREDITATION_CERT, TOOL_LIST_EVIDENCE, WSP_SIGNOFF). |
| `DocumentTypeName` | `nvarchar(150)` | **NOT NULL** |  | Display name of the document category. |
| `FileName` | `nvarchar(255)` | **NOT NULL** |  | Original file name of uploaded evidence. |
| `FileSizeBytes` | `bigint` | **NOT NULL** |  | File size in bytes. |
| `IsVerified` | `bit` | **NOT NULL** |  | Indicates whether the document has been verified by an authorized officer. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `Sha256Hash` | `nvarchar(100)` | **NOT NULL** |  | Cryptographic SHA-256 digital fingerprint hash for audit tampering protection. |
| `StorageUri` | `nvarchar(500)` | **NOT NULL** |  | Storage blob URI or relative storage path. |
| `TargetEntityId` | `int` | **NOT NULL** |  | Primary key integer value of the target entity record. |
| `TargetEntityName` | `nvarchar(100)` | **NOT NULL** |  | Target domain entity name linked to this document (e.g. Organisation, CompanyLearner, WorkplaceApproval, WspSubmission). |
| `UploadDate` | `datetime2` | **NOT NULL** |  | Timestamp when the file was uploaded. |
| `UploadedByUserId` | `nvarchar(100)` | **NOT NULL** |  | User identifier of the uploader. |
| `UploadedByUserName` | `nvarchar(150)` | **NOT NULL** |  | Full display name of the uploader. |
| `VerificationNotes` | `nvarchar(max)` | NULL |  | Document verification remarks or rejection reasons. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_DocumentMetadata_DocumentTypeCode` | `DocumentTypeCode` | No |
| `IX_DocumentMetadata_UploadDate` | `UploadDate` | No |
| `IX_DocumentMetadata_TargetEntityName_TargetEntityId` | `TargetEntityName, TargetEntityId` | No |

---

### <a id="documentrequirementrule"></a> `dbo.DocumentRequirementRule`

**Description:** Governance rule defining mandatory document evidence required before workflow gate advancement.  
**CLR Model:** `Nsdms.Domain.Entities.DocumentRequirementRule`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `Description` | `nvarchar(300)` | **NOT NULL** |  | Descriptive requirement context and guidelines. |
| `DocumentTypeCode` | `nvarchar(50)` | **NOT NULL** |  | Required document type code. |
| `DocumentTypeName` | `nvarchar(150)` | **NOT NULL** |  | Display name of the required document. |
| `IsMandatory` | `bit` | **NOT NULL** |  | Indicates whether this document is strictly mandatory to advance. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `RequiredAtStateId` | `int` | **NOT NULL** |  | Foreign key referencing the WorkflowState where this document must be verified before proceeding. |
| `WorkflowProcessCode` | `nvarchar(50)` | **NOT NULL** |  | Workflow process code (e.g. PROVIDER, WSP, DG, WPAPP, LRN, TRADETEST). |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_DocumentRequirementRule_DocumentTypeCode` | `DocumentTypeCode` | No |
| `IX_DocumentRequirementRule_WorkflowProcessCode` | `WorkflowProcessCode` | No |

---

### <a id="documentsnapshot"></a> `dbo.DocumentSnapshot`

**Description:** Immutable cryptographically frozen snapshot of any issued statutory document, certificate, or letter.  
**CLR Model:** `Nsdms.Domain.Entities.DocumentSnapshot`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DocumentSnapshotNumber` | `nvarchar(50)` | **NOT NULL** |  | Unique public tracking and verification reference (e.g. DOC-2026-TT-00123, DOC-2026-WSP-98765). |
| `DocumentTemplateId` | `int` | NULL | 🔗 **FK** | Optional foreign key referencing the DocumentTemplate used at issuance. |
| `DocumentTypeCode` | `nvarchar(50)` | **NOT NULL** |  | Document type classification code (e.g. TradeTestCertificate, WspApprovalLetter, AccreditationCertificate, RemittanceAdvice). |
| `IsRevoked` | `bit` | **NOT NULL** |  | Whether this issued document has been revoked or superseded (e.g. due to fraudulent trade test or re-adjudication). |
| `IssuedAt` | `datetime2` | **NOT NULL** |  | Official date and time when the document was frozen and issued. |
| `IssuedBy` | `nvarchar(100)` | **NOT NULL** |  | Username or system process that authorized and issued the document. |
| `LastVerifiedAt` | `datetime2` | NULL |  | Timestamp of the most recent verification lookup. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `PdfStorageUri` | `nvarchar(500)` | NULL |  | Storage location URI of the signed PDF artifact. |
| `RecipientIdentifier` | `nvarchar(50)` | **NOT NULL** |  | Recipient primary identifier (e.g. RSA ID Number, SDL Number, Accreditation Number). |
| `RecipientName` | `nvarchar(200)` | **NOT NULL** |  | Full legal name of the recipient individual or organisation. |
| `RelatedEntityId` | `int` | **NOT NULL** |  | Identifier of the related entity (e.g. LearnerTradeTest.Id, WspSubmission.Id, TrainingProvider.Id, GrantMoa.Id). |
| `RelatedEntityType` | `nvarchar(50)` | **NOT NULL** |  | Name of the related entity table/domain (e.g. LearnerTradeTest, WspSubmission, TrainingProvider, GrantMoa, SarsLevyReconAudit). |
| `RenderedContent` | `nvarchar(max)` | **NOT NULL** |  | Fully assembled and interpolated document text captured at point of issuance. |
| `RenderedContentHash` | `nvarchar(100)` | **NOT NULL** |  | Cryptographic SHA-256 digital fingerprint hash of the assembled document content. |
| `RevocationReason` | `nvarchar(500)` | NULL |  | Reason description if the document was revoked. |
| `SignatoryName` | `nvarchar(150)` | NULL |  | Full name of the designated authorized signatory (e.g. Chief Executive Officer, Senior Manager: ETQA). |
| `SignatorySignedAt` | `datetime2` | NULL |  | Date when digital/electronic signature was affixed. |
| `SignatoryTitle` | `nvarchar(150)` | NULL |  | Official title of the designated signatory. |
| `TemplateVersionNumber` | `nvarchar(20)` | **NOT NULL** |  | Version number of the template captured at point of issuance. |
| `VerificationQrBase64` | `nvarchar(max)` | NULL |  | Embedded Base64 QR code image payload for offline and optical verification. |
| `VerificationScanCount` | `int` | **NOT NULL** |  | Total count of times this document has been scanned and verified via the public verification portal. |
| `VerificationUri` | `nvarchar(500)` | **NOT NULL** |  | Canonical public verification URI (e.g. https://nsdms.merseta.org.za/verify/document/a1b2c3d4...). |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_DocumentSnapshot_DocumentTemplate_DocumentTemplateId` | `DocumentTemplateId` | `dbo.DocumentTemplate` | `Restrict` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_DocumentSnapshot_DocumentSnapshotNumber` | `DocumentSnapshotNumber` | ✅ Yes |
| `IX_DocumentSnapshot_DocumentTypeCode` | `DocumentTypeCode` | No |
| `IX_DocumentSnapshot_IssuedAt` | `IssuedAt` | No |
| `IX_DocumentSnapshot_RecipientIdentifier` | `RecipientIdentifier` | No |
| `IX_DocumentSnapshot_RenderedContentHash` | `RenderedContentHash` | No |
| `IX_DocumentSnapshot_RelatedEntityType_RelatedEntityId` | `RelatedEntityType, RelatedEntityId` | No |

---

### <a id="documenttemplate"></a> `dbo.DocumentTemplate`

**Description:** Universal enterprise document template for statutory letters, certificates, agreements, and notices.  
**CLR Model:** `Nsdms.Domain.Entities.DocumentTemplate`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ApprovalStatus` | `nvarchar(30)` | **NOT NULL** |  | Governance approval lifecycle status (Draft, UnderReview, Approved, Sunset). |
| `ApprovedAt` | `datetime2` | NULL |  | Timestamp when governance approval was executed. |
| `ApprovedBy` | `nvarchar(100)` | NULL |  | Governance/legal officer who approved this template. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DocumentCategory` | `nvarchar(50)` | **NOT NULL** |  | Document category (e.g. MandatoryGrant, DiscretionaryGrant, TradeTest, EtqaAccreditation, LearnerContract, FinanceAudit). |
| `DocumentTypeCode` | `nvarchar(50)` | **NOT NULL** |  | Specific document type classification code (e.g. WspApprovalLetter, WspRejectionLetter, TradeTestCertificate, StatementOfResults, AccreditationCertificate). |
| `EffectiveFrom` | `datetime2` | **NOT NULL** |  | Effective starting date for this template version. |
| `EffectiveTo` | `datetime2` | NULL |  | Optional expiration / sunset date for this template version. |
| `FinancialYear` | `int` | **NOT NULL** |  | Financial/statutory scheme year this template is active for (e.g. 2026). |
| `FooterDisclaimerText` | `nvarchar(500)` | NULL |  | Statutory footer disclaimer text. |
| `HeaderBannerUrl` | `nvarchar(500)` | NULL |  | Optional custom header branding banner URL or resource path. |
| `IsActive` | `bit` | **NOT NULL** |  | Whether this template is active for new document generation. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `TargetEntityType` | `nvarchar(50)` | **NOT NULL** |  | Target entity legal classification filter (e.g. All, Employer, Provider, Learner, Assessor). |
| `TemplateCode` | `nvarchar(50)` | **NOT NULL** |  | Unique template identifier code (e.g. WSP-APPROVAL-STD, TRADE-CERT-STD, ETQA-ACCRED-STD). |
| `TemplateTitle` | `nvarchar(200)` | **NOT NULL** |  | Human-readable title of the document template. |
| `VersionNumber` | `nvarchar(20)` | **NOT NULL** |  | Semantic policy version number (e.g. 1.0.0, 2.1.0). |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_DocumentTemplate_ApprovalStatus` | `ApprovalStatus` | No |
| `IX_DocumentTemplate_TemplateCode` | `TemplateCode` | ✅ Yes |
| `IX_DocumentTemplate_DocumentCategory_DocumentTypeCode_FinancialYear_IsActive` | `DocumentCategory, DocumentTypeCode, FinancialYear, IsActive` | No |

---

### <a id="documenttemplatesection"></a> `dbo.DocumentTemplateSection`

**Description:** Ordered section mapping a reusable clause to a document template.  
**CLR Model:** `Nsdms.Domain.Entities.DocumentTemplateSection`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ConditionRuleJson` | `nvarchar(max)` | NULL |  | Optional conditional inclusion rule expression in JSON format. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DocumentClauseId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the assigned DocumentClause. |
| `DocumentTemplateId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the parent DocumentTemplate. |
| `IsMandatory` | `bit` | **NOT NULL** |  | Whether this section is mandatory for this template. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `SectionNumber` | `nvarchar(30)` | **NOT NULL** |  | Custom section number or bullet label (e.g. "1.0", "Clause 4", "Annexure A"). |
| `SectionTitle` | `nvarchar(200)` | **NOT NULL** |  | Section heading title override. |
| `SequenceOrder` | `int` | **NOT NULL** |  | Sorting sequence order within the document layout. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_DocumentTemplateSection_DocumentClause_DocumentClauseId` | `DocumentClauseId` | `dbo.DocumentClause` | `Restrict` |
| `FK_DocumentTemplateSection_DocumentTemplate_DocumentTemplateId` | `DocumentTemplateId` | `dbo.DocumentTemplate` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_DocumentTemplateSection_DocumentClauseId` | `DocumentClauseId` | No |
| `IX_DocumentTemplateSection_DocumentTemplateId` | `DocumentTemplateId` | No |
| `IX_DocumentTemplateSection_DocumentTemplateId_SequenceOrder` | `DocumentTemplateId, SequenceOrder` | No |

---

### <a id="eisaassessmententry"></a> `dbo.EisaAssessmentEntry`

**Description:** External Integrated Summative Assessment (EISA) exam entry for QCTO occupational qualifications.  
**CLR Model:** `Nsdms.Domain.Entities.EisaAssessmentEntry`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AssessmentPaperCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for AssessmentPaperCode. |
| `CompetencyStatusCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for CompetencyStatusCode. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `EisaAssessmentDate` | `datetime2` | **NOT NULL** |  | Domain property for EisaAssessmentDate. |
| `EisaCenterName` | `nvarchar(200)` | **NOT NULL** |  | Domain property for EisaCenterName. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `PercentageScore` | `decimal(18,2)` | **NOT NULL** |  | Domain property for PercentageScore. |
| `QctoModerationReferenceNumber` | `nvarchar(max)` | NULL |  | Domain property for QctoModerationReferenceNumber. |
| `QctoSignOffDate` | `datetime2` | NULL |  | Domain property for QctoSignOffDate. |
| `ScoreAchieved` | `decimal(18,2)` | **NOT NULL** |  | Domain property for ScoreAchieved. |
| `SummativeAssessmentReportId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `TotalScorePossible` | `decimal(18,2)` | **NOT NULL** |  | Domain property for TotalScorePossible. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_EisaAssessmentEntry_SummativeAssessmentReport_SummativeAssessmentReportId` | `SummativeAssessmentReportId` | `dbo.SummativeAssessmentReport` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_EisaAssessmentEntry_AssessmentPaperCode` | `AssessmentPaperCode` | No |
| `IX_EisaAssessmentEntry_SummativeAssessmentReportId` | `SummativeAssessmentReportId` | No |

---

### <a id="erpoutboxmessage"></a> `dbo.ErpOutboxMessage`

**Description:** Transactional Outbox message entity for Microsoft Dynamics GP and ERP Web Services integration. Ensures resilient, decoupled asynchronous execution with automatic pause on GP outage and resumption upon recovery.  
**CLR Model:** `Nsdms.Domain.Entities.ErpOutboxMessage`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DeliveredAtUtc` | `datetime2` | NULL |  | UTC timestamp when the GP transaction was confirmed delivered. |
| `ExecutionPriority` | `int` | **NOT NULL** |  | Execution priority order: 1 = Pre-requisite (VendorSync, BankingDetails), 2 = Standard (Disbursements). |
| `GpBatchNumber` | `nvarchar(100)` | NULL |  | GP Payment Batch reference assigned to this transaction (e.g. GP-LIVE-YYYYMMDD-XXXX). |
| `LastAttemptAtUtc` | `datetime2` | NULL |  | UTC timestamp of the last attempted invocation. |
| `LastError` | `nvarchar(max)` | NULL |  | Diagnostic error message or stack trace if the last attempt failed. |
| `LockExpiresAtUtc` | `datetime2` | NULL |  | UTC expiration time for worker lock token. |
| `LockToken` | `nvarchar(100)` | NULL |  | Concurrency lock token to ensure safe single-worker processing. |
| `MaxRetries` | `int` | **NOT NULL** |  | Maximum retry attempts before escalating to DeadLetter. |
| `MessageCorrelationId` | `nvarchar(64)` | **NOT NULL** |  | Unique correlation ID for tracking and idempotency across ERP boundaries. |
| `MessageType` | `nvarchar(50)` | **NOT NULL** |  | Type of GP operation: VendorSync, BankingDetailsUpdate, DgTrancheDisbursement, MgRebateDisbursement, PaymentDisbursement. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `NextAttemptAtUtc` | `datetime2` | NULL |  | UTC timestamp when the message is eligible for next delivery attempt. |
| `OrganisationId` | `int` | NULL | 🔗 **FK** | Associated Organisation ID if applicable. |
| `PayloadJson` | `nvarchar(max)` | **NOT NULL** |  | Serialized JSON payload containing parameters for the GP Web Service invocation. |
| `QueueStatusCode` | `nvarchar(50)` | **NOT NULL** |  | Queue status: Pending, Processing, Delivered, FailedRetryable, DeadLetter, Suspended. |
| `ReferenceKey` | `nvarchar(100)` | **NOT NULL** |  | Business reference key (e.g. SDL number, MoA number, Voucher number, or statutory reference). |
| `RetryCount` | `int` | **NOT NULL** |  | Current count of retry attempts. |
| `TransactionReference` | `nvarchar(100)` | NULL |  | Transaction reference returned by Dynamics GP (e.g. TRX-XXXXXX or voucher reference). |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_ErpOutboxMessage_Organisation_OrganisationId` | `OrganisationId` | `dbo.Organisation` | `ClientSetNull` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_ErpOutboxMessage_MessageCorrelationId` | `MessageCorrelationId` | ✅ Yes |
| `IX_ErpOutboxMessage_MessageType` | `MessageType` | No |
| `IX_ErpOutboxMessage_NextAttemptAtUtc` | `NextAttemptAtUtc` | No |
| `IX_ErpOutboxMessage_OrganisationId` | `OrganisationId` | No |
| `IX_ErpOutboxMessage_QueueStatusCode` | `QueueStatusCode` | No |
| `IX_ErpOutboxMessage_ReferenceKey` | `ReferenceKey` | No |

---

### <a id="erppaymentbatchentry"></a> `dbo.ErpPaymentBatchEntry`

**Description:** Individual line item voucher within an ERP payment batch.  
**CLR Model:** `Nsdms.Domain.Entities.ErpPaymentBatchEntry`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `BankAccountNumber` | `nvarchar(max)` | **NOT NULL** |  | Domain property for BankAccountNumber. |
| `BankBranchCode` | `nvarchar(max)` | **NOT NULL** |  | Domain property for BankBranchCode. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `EftReferenceNumber` | `nvarchar(max)` | NULL |  | Domain property for EftReferenceNumber. |
| `EntryStatusCode` | `nvarchar(max)` | **NOT NULL** |  | Domain property for EntryStatusCode. |
| `ErpPaymentBatchHeaderId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `GrantPaymentClaimId` | `int` | NULL | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `MandatoryGrantDisbursementId` | `int` | NULL |  | Domain property for MandatoryGrantDisbursementId. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `OrganisationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `PaymentAmount` | `decimal(18,2)` | **NOT NULL** |  | Domain property for PaymentAmount. |
| `PaymentDescription` | `nvarchar(max)` | **NOT NULL** |  | Domain property for PaymentDescription. |
| `PaymentVoucherNumber` | `nvarchar(100)` | **NOT NULL** |  | Domain property for PaymentVoucherNumber. |
| `VendorNumber` | `nvarchar(max)` | **NOT NULL** |  | Domain property for VendorNumber. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_ErpPaymentBatchEntry_ErpPaymentBatchHeader_ErpPaymentBatchHeaderId` | `ErpPaymentBatchHeaderId` | `dbo.ErpPaymentBatchHeader` | `Cascade` |
| `FK_ErpPaymentBatchEntry_GrantPaymentClaim_GrantPaymentClaimId` | `GrantPaymentClaimId` | `dbo.GrantPaymentClaim` | `ClientSetNull` |
| `FK_ErpPaymentBatchEntry_Organisation_OrganisationId` | `OrganisationId` | `dbo.Organisation` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_ErpPaymentBatchEntry_ErpPaymentBatchHeaderId` | `ErpPaymentBatchHeaderId` | No |
| `IX_ErpPaymentBatchEntry_GrantPaymentClaimId` | `GrantPaymentClaimId` | No |
| `IX_ErpPaymentBatchEntry_OrganisationId` | `OrganisationId` | No |
| `IX_ErpPaymentBatchEntry_PaymentVoucherNumber` | `PaymentVoucherNumber` | No |

---

### <a id="erppaymentbatchheader"></a> `dbo.ErpPaymentBatchHeader`

**Description:** ERP Payment Batch Header for staging mandatory/discretionary grant disbursements to Dynamics GP / Sage.  
**CLR Model:** `Nsdms.Domain.Entities.ErpPaymentBatchHeader`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ApprovalComments` | `nvarchar(max)` | NULL |  | Domain property for ApprovalComments. |
| `BatchNumber` | `nvarchar(100)` | **NOT NULL** |  | Financial transaction batch grouping reference. |
| `BatchStatusCode` | `nvarchar(max)` | **NOT NULL** |  | Domain property for BatchStatusCode. |
| `BatchTypeCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for BatchTypeCode. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `ExportFileName` | `nvarchar(max)` | NULL |  | Domain property for ExportFileName. |
| `ExportedByUserId` | `nvarchar(max)` | NULL |  | Domain property for ExportedByUserId. |
| `ExportedDate` | `datetime2` | NULL |  | Domain property for ExportedDate. |
| `ItemCount` | `int` | **NOT NULL** |  | Domain property for ItemCount. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `TotalAmount` | `decimal(18,2)` | **NOT NULL** |  | Domain property for TotalAmount. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_ErpPaymentBatchHeader_BatchNumber` | `BatchNumber` | ✅ Yes |
| `IX_ErpPaymentBatchHeader_BatchStatusCode` | `BatchStatusCode` | No |

---

### <a id="etqaassessor"></a> `dbo.EtqaAssessor`

**Description:** Registered ETQA Assessors and Moderators with approved qualification scopes, capturing all statutory fields required for SETMIS File 401 (Person Designation) reporting.  
**CLR Model:** `Nsdms.Domain.Entities.EtqaAssessor`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DesignationStructureStatusId` | `nvarchar(10)` | **NOT NULL** |  | Practitioner registration standing code (references lookup.DesignationStructureStatusType: 01 Registered, 02 Deregistered, 03 Suspended). |
| `DesignationTypeId` | `nvarchar(10)` | **NOT NULL** |  | Practitioner designation type code (references lookup.DesignationType: 01 Assessor, 02 Moderator). |
| `EndDate` | `datetime2` | **NOT NULL** |  | Registration validity expiration date (SETMIS File 401). |
| `EtqaId` | `nvarchar(10)` | **NOT NULL** |  | Submitting ETQA ID (references lookup.SetaType, default 17 for merSETA). |
| `EtqaRole` | `nvarchar(50)` | **NOT NULL** |  | ETQA role classification (Assessor, Moderator, Both). |
| `EtqeDecisionNumber` | `nvarchar(50)` | NULL |  | ETQA Committee decision number approving registration scope (SETMIS File 401). |
| `IsActive` | `bit` | **NOT NULL** |  | Indicates whether the assessor registration is currently active. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `PersonId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the assessor's demographic Person record. |
| `RegistrationNumber` | `nvarchar(50)` | **NOT NULL** |  | MerSETA ETQA assessor or moderator registration number (SETMIS File 401). |
| `RegistrationStatusCode` | `nvarchar(15)` | NULL |  | Current registration lifecycle status code (e.g. Active, PendingRenewal, Suspended, Expired). |
| `StartDate` | `datetime2` | **NOT NULL** |  | Registration validity start date (SETMIS File 401). |
| `TrainingProviderId` | `int` | NULL | 🔗 **FK** | Optional foreign key referencing primary affiliated Skills Development Provider. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_EtqaAssessor_Person_PersonId` | `PersonId` | `dbo.Person` | `Restrict` |
| `FK_EtqaAssessor_TrainingProvider_TrainingProviderId` | `TrainingProviderId` | `dbo.TrainingProvider` | `SetNull` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_EtqaAssessor_DesignationStructureStatusId` | `DesignationStructureStatusId` | No |
| `IX_EtqaAssessor_DesignationTypeId` | `DesignationTypeId` | No |
| `IX_EtqaAssessor_EtqaId` | `EtqaId` | No |
| `IX_EtqaAssessor_PersonId` | `PersonId` | No |
| `IX_EtqaAssessor_RegistrationNumber` | `RegistrationNumber` | No |
| `IX_EtqaAssessor_RegistrationStatusCode` | `RegistrationStatusCode` | No |
| `IX_EtqaAssessor_TrainingProviderId` | `TrainingProviderId` | No |

---

### <a id="financialapprovalthreshold"></a> `dbo.FinancialApprovalThreshold`

**Description:** Represents a tiered financial delegation limit (DoA) enforcing role-based threshold caps on grants and disbursements.  
**CLR Model:** `Nsdms.Domain.Entities.FinancialApprovalThreshold`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ApprovalLevelName` | `nvarchar(100)` | **NOT NULL** |  | Domain property for ApprovalLevelName. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | **NOT NULL** |  | User identifier or system process that created the record. |
| `Description` | `nvarchar(500)` | **NOT NULL** |  | Detailed description and contextual notes. |
| `IsActive` | `bit` | **NOT NULL** |  | Indicates whether the record is active and operational. |
| `MaxApprovalAmount` | `decimal(18,2)` | **NOT NULL** |  | Domain property for MaxApprovalAmount. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `ModuleCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for ModuleCode. |
| `RequiresBoardApproval` | `bit` | **NOT NULL** |  | Domain property for RequiresBoardApproval. |
| `RoleName` | `nvarchar(100)` | **NOT NULL** |  | Domain property for RoleName. |

---

### <a id="fundingwindowpriority"></a> `dbo.FundingWindowPriority`

**Description:** Gazetted Strategic Priority sub-budget envelope and beneficiary quota allocated to a Discretionary Grant Funding Window.  
**CLR Model:** `Nsdms.Domain.Entities.FundingWindowPriority`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AllocatedBudget` | `decimal(18,2)` | **NOT NULL** |  | Monetary sub-budget allocation envelope dedicated to this theme in ZAR. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `FundingWindowId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the parent GrantFundingWindow. |
| `IsActive` | `bit` | **NOT NULL** |  | Indicates whether this theme allocation is active within the funding window. |
| `IsRingFenced` | `bit` | **NOT NULL** |  | If true, funds under this priority cannot be vired or reallocated to other themes without MANCO approval. |
| `MinScoreThreshold` | `decimal(18,2)` | **NOT NULL** |  | Minimum technical evaluation score threshold required for approval under this theme (e.g. 65.00%). |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `StrategicPriorityId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the StrategicPriority theme. |
| `TargetBeneficiaries` | `int` | **NOT NULL** |  | Target number of learners/beneficiaries planned for this strategic priority. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_FundingWindowPriority_GrantFundingWindow_FundingWindowId` | `FundingWindowId` | `dbo.GrantFundingWindow` | `Cascade` |
| `FK_FundingWindowPriority_StrategicPriority_StrategicPriorityId` | `StrategicPriorityId` | `dbo.StrategicPriority` | `Restrict` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_FundingWindowPriority_FundingWindowId` | `FundingWindowId` | No |
| `IX_FundingWindowPriority_IsActive` | `IsActive` | No |
| `IX_FundingWindowPriority_StrategicPriorityId` | `StrategicPriorityId` | No |
| `IX_FundingWindowPriority_FundingWindowId_StrategicPriorityId` | `FundingWindowId, StrategicPriorityId` | ✅ Yes |

---

### <a id="grantapplication"></a> `dbo.GrantApplication`

**Description:** Discretionary Grant funding applications submitted by employers for skills development projects.  
**CLR Model:** `Nsdms.Domain.Entities.GrantApplication`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ApplicationDate` | `datetime2` | **NOT NULL** |  | Date when the grant application was officially submitted. |
| `ApplicationNumber` | `nvarchar(50)` | **NOT NULL** |  | Unique grant application reference tracking number (e.g. DG-2026-0001). |
| `ApplicationStatusCode` | `nvarchar(15)` | NULL |  | Current workflow review and adjudication status code. |
| `ApprovedAmount` | `decimal(18,2)` | NULL |  | Final grant funding amount approved by the MerSETA adjudication committee in ZAR. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `FundingWindowId` | `int` | NULL | 🔗 **FK** | Optional foreign key referencing the open GrantFundingWindow. |
| `FundingWindowPriorityId` | `int` | NULL | 🔗 **FK** | Optional foreign key referencing the specific FundingWindowPriority allocation envelope. |
| `GrantTypeCode` | `nvarchar(15)` | NULL |  | Discretionary grant funding type code (e.g. PIVOTAL, NON_PIVOTAL, BURSARY, APPRENTICESHIP). |
| `IsWspCompliant` | `bit` | **NOT NULL** |  | Indicates whether the applicant has an approved, compliant WSP/ATR on file for the scheme year. |
| `IsWspExempt` | `bit` | **NOT NULL** |  | Indicates whether the applicant is legally exempt from WSP submission (e.g. Non-Levy Payer, Public TVET, NGO, Community Trust). |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `OrganisationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the applying Employer Organisation. |
| `ProjectTitle` | `nvarchar(300)` | **NOT NULL** |  | Descriptive title of the skills development project. |
| `RequestedAmount` | `decimal(18,2)` | **NOT NULL** |  | Total grant funding amount requested by the applicant in ZAR. |
| `StrategicPriorityId` | `int` | NULL | 🔗 **FK** | Optional foreign key referencing the primary StrategicPriority theme addressed by this application. |
| `WspExemptionReason` | `nvarchar(500)` | NULL |  | Statutory justification or rationale for WSP submission exemption. |
| `WspSubmissionId` | `int` | NULL | 🔗 **FK** | Optional foreign key referencing the compliant Mandatory Grant WSP submission for this financial year (SETA Grant Regulation 4(4)). |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_GrantApplication_GrantFundingWindow_FundingWindowId` | `FundingWindowId` | `dbo.GrantFundingWindow` | `SetNull` |
| `FK_GrantApplication_FundingWindowPriority_FundingWindowPriorityId` | `FundingWindowPriorityId` | `dbo.FundingWindowPriority` | `SetNull` |
| `FK_GrantApplication_Organisation_OrganisationId` | `OrganisationId` | `dbo.Organisation` | `Restrict` |
| `FK_GrantApplication_StrategicPriority_StrategicPriorityId` | `StrategicPriorityId` | `dbo.StrategicPriority` | `SetNull` |
| `FK_GrantApplication_WspSubmission_WspSubmissionId` | `WspSubmissionId` | `dbo.WspSubmission` | `SetNull` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_GrantApplication_ApplicationNumber` | `ApplicationNumber` | No |
| `IX_GrantApplication_ApplicationStatusCode` | `ApplicationStatusCode` | No |
| `IX_GrantApplication_FundingWindowId` | `FundingWindowId` | No |
| `IX_GrantApplication_FundingWindowPriorityId` | `FundingWindowPriorityId` | No |
| `IX_GrantApplication_OrganisationId` | `OrganisationId` | No |
| `IX_GrantApplication_StrategicPriorityId` | `StrategicPriorityId` | No |
| `IX_GrantApplication_WspSubmissionId` | `WspSubmissionId` | No |

---

### <a id="grantfundingwindow"></a> `dbo.GrantFundingWindow`

**Description:** Gazette-announced Discretionary Grant funding window opening and allocation cycle.  
**CLR Model:** `Nsdms.Domain.Entities.GrantFundingWindow`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ClosingDate` | `datetime2` | **NOT NULL** |  | Hard deadline closing date and time after which no new applications are accepted. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `Description` | `nvarchar(max)` | NULL |  | Optional gazette reference or policy notice circular reference. |
| `FinYear` | `int` | **NOT NULL** |  | Financial scheme year for this funding allocation window (e.g. 2026). |
| `GrantTypeCode` | `nvarchar(15)` | NULL |  | Grant funding type code (e.g. PIVOTAL, APPRENTICESHIP, SKILLS_PROGRAMME, BURSARY). |
| `IsActive` | `bit` | **NOT NULL** |  | Indicates whether this funding window is active and accepting submissions. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `OpeningDate` | `datetime2` | **NOT NULL** |  | Official window opening date and time for employer application submissions. |
| `TotalAvailableBudget` | `decimal(18,2)` | **NOT NULL** |  | Total aggregate discretionary budget allocated to this funding window in ZAR. |
| `WindowName` | `nvarchar(200)` | **NOT NULL** |  | Descriptive window name (e.g. 2026/27 Discretionary Grant Funding Window 1). |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_GrantFundingWindow_FinYear` | `FinYear` | No |
| `IX_GrantFundingWindow_GrantTypeCode` | `GrantTypeCode` | No |
| `IX_GrantFundingWindow_IsActive` | `IsActive` | No |

---

### <a id="grantmoa"></a> `dbo.GrantMoa`

**Description:** Memorandum of Agreement (MOA) for approved Discretionary Grants.  
**CLR Model:** `Nsdms.Domain.Entities.GrantMoa`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ContractEndDate` | `datetime2` | **NOT NULL** |  | Contractual project completion and closeout deadline. |
| `ContractStartDate` | `datetime2` | **NOT NULL** |  | Contractual project commencement date. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `GrantApplicationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the originating Discretionary Grant application. |
| `MoaNumber` | `nvarchar(100)` | **NOT NULL** |  | Unique MerSETA MOA legal contract reference number (e.g. MOA-2026-DG-001). |
| `MoaStatusCode` | `nvarchar(50)` | **NOT NULL** |  | Lifecycle contract state code (e.g. Draft, Pending Signature, Active, Terminated, Completed). |
| `MoaTemplateId` | `int` | NULL | 🔗 **FK** | Foreign key referencing the active MoaTemplate version applied to this agreement. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `SignoffDateEmployer` | `datetime2` | NULL |  | Date when the employer authorized signatory signed the contract. |
| `SignoffDateSeta` | `datetime2` | NULL |  | Date when the MerSETA CEO / delegated authority executed the agreement. |
| `SignoffDocumentUri` | `nvarchar(500)` | NULL |  | Digital storage URI of the executed bilateral MOA legal document. |
| `SpecialConditions` | `nvarchar(2000)` | NULL |  | Special conditions, covenants, or bespoke performance clauses attached to this agreement. |
| `TotalContractValue` | `decimal(18,2)` | **NOT NULL** |  | Total committed monetary contract allocation in ZAR. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_GrantMoa_GrantApplication_GrantApplicationId` | `GrantApplicationId` | `dbo.GrantApplication` | `Restrict` |
| `FK_GrantMoa_MoaTemplate_MoaTemplateId` | `MoaTemplateId` | `dbo.MoaTemplate` | `SetNull` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_GrantMoa_GrantApplicationId` | `GrantApplicationId` | No |
| `IX_GrantMoa_MoaNumber` | `MoaNumber` | ✅ Yes |
| `IX_GrantMoa_MoaStatusCode` | `MoaStatusCode` | No |
| `IX_GrantMoa_MoaTemplateId` | `MoaTemplateId` | No |

---

### <a id="grantmoamilestone"></a> `dbo.GrantMoaMilestone`

**Description:** Delivery milestones linked to MOA tranches.  
**CLR Model:** `Nsdms.Domain.Entities.GrantMoaMilestone`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DeliverableRequirement` | `nvarchar(1000)` | NULL |  | Mandatory physical or digital evidence deliverables required for tranche release. |
| `GrantMoaId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the parent GrantMoa contract. |
| `MilestoneDescription` | `nvarchar(max)` | NULL |  | Detailed description of delivery expectations. |
| `MilestoneNumber` | `int` | **NOT NULL** |  | Sequential milestone sequence index (e.g. 1, 2, 3, 4). |
| `MilestoneStatusCode` | `nvarchar(50)` | **NOT NULL** |  | Milestone verification and disbursement state code (e.g. Pending, Submitted, Verified, Approved, Paid). |
| `MilestoneTitle` | `nvarchar(200)` | **NOT NULL** |  | Descriptive milestone title (e.g. Inception & Learner Contracting, Midterm Progress, Final Assessment & Closeout). |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `TargetDueDate` | `datetime2` | **NOT NULL** |  | Target due date for milestone deliverable submission. |
| `TrancheAmount` | `decimal(18,2)` | **NOT NULL** |  | Calculated tranche disbursement payout amount in ZAR. |
| `TranchePercentage` | `decimal(5,2)` | **NOT NULL** |  | Deliverable payment allocation percentage (e.g. 30%, 40%, 30%). |
| `VerificationComments` | `nvarchar(1000)` | NULL |  | Verifier audit assessment notes and inspection remarks. |
| `VerificationDate` | `datetime2` | NULL |  | Date when the MerSETA monitoring officer verified milestone evidence. |
| `VerifiedByUserId` | `nvarchar(max)` | NULL |  | User identifier of the reviewing MerSETA verifier. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_GrantMoaMilestone_GrantMoa_GrantMoaId` | `GrantMoaId` | `dbo.GrantMoa` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_GrantMoaMilestone_GrantMoaId` | `GrantMoaId` | No |
| `IX_GrantMoaMilestone_MilestoneStatusCode` | `MilestoneStatusCode` | No |

---

### <a id="grantpaymentclaim"></a> `dbo.GrantPaymentClaim`

**Description:** Milestone-based Payment Claim against a Project Implementation Plan.  
**CLR Model:** `Nsdms.Domain.Entities.GrantPaymentClaim`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ApprovalDate` | `datetime2` | NULL |  | Domain property for ApprovalDate. |
| `ApprovedByUserId` | `nvarchar(100)` | NULL |  | Domain property for ApprovedByUserId. |
| `CfoApprovedBy` | `nvarchar(max)` | NULL |  | Domain property for CfoApprovedBy. |
| `CfoApprovedDate` | `datetime2` | NULL |  | Domain property for CfoApprovedDate. |
| `ClaimAmount` | `decimal(18,2)` | **NOT NULL** |  | Domain property for ClaimAmount. |
| `ClaimNumber` | `nvarchar(50)` | **NOT NULL** |  | Domain property for ClaimNumber. |
| `CloVerifiedBy` | `nvarchar(max)` | NULL |  | Domain property for CloVerifiedBy. |
| `CloVerifiedDate` | `datetime2` | NULL |  | Domain property for CloVerifiedDate. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DeliverableDescription` | `nvarchar(1000)` | **NOT NULL** |  | Domain property for DeliverableDescription. |
| `ErpBatchNumber` | `nvarchar(100)` | NULL |  | Domain property for ErpBatchNumber. |
| `FinanceOfficerApprovedBy` | `nvarchar(max)` | NULL |  | Domain property for FinanceOfficerApprovedBy. |
| `FinanceOfficerApprovedDate` | `datetime2` | NULL |  | Domain property for FinanceOfficerApprovedDate. |
| `GrantMoaMilestoneId` | `int` | NULL | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `PaymentVoucherNumber` | `nvarchar(max)` | NULL |  | Domain property for PaymentVoucherNumber. |
| `ProjectImplementationPlanId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `RequiresCfoApproval` | `bit` | **NOT NULL** |  | Domain property for RequiresCfoApproval. |
| `StatusCode` | `nvarchar(50)` | **NOT NULL** |  | Current lifecycle state code in the workflow engine. |
| `TrancheNumber` | `int` | **NOT NULL** |  | Domain property for TrancheNumber. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_GrantPaymentClaim_GrantMoaMilestone_GrantMoaMilestoneId` | `GrantMoaMilestoneId` | `dbo.GrantMoaMilestone` | `ClientSetNull` |
| `FK_GrantPaymentClaim_ProjectImplementationPlan_ProjectImplementationPlanId` | `ProjectImplementationPlanId` | `dbo.ProjectImplementationPlan` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_GrantPaymentClaim_ClaimNumber` | `ClaimNumber` | No |
| `IX_GrantPaymentClaim_ProjectImplementationPlanId` | `ProjectImplementationPlanId` | No |
| `IX_GrantPaymentClaim_StatusCode` | `StatusCode` | No |

---

### <a id="grantprojectbudget"></a> `dbo.GrantProjectBudget`

**Description:** Detailed cost item and financial budget breakdown submitted in a Discretionary Grant application.  
**CLR Model:** `Nsdms.Domain.Entities.GrantProjectBudget`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed specification and cost justification. |
| `ExpenseCategory` | `nvarchar(100)` | **NOT NULL** |  | Expense classification category (e.g. TUITION, STIPEND, PPE_SAFETY, LEARNER_ALLOWANCE, ASSESSMENTS). |
| `GrantApplicationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the parent GrantApplication. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `Quantity` | `int` | **NOT NULL** |  | Total number of beneficiary or item units budgeted. |
| `StrategicPriorityId` | `int` | NULL | 🔗 **FK** | Optional foreign key referencing the specific StrategicPriority theme this line item finances. |
| `TotalCost` | `decimal(18,2)` | **NOT NULL** |  | Total aggregate line item cost (UnitCost * Quantity) in ZAR. |
| `UnitCost` | `decimal(18,2)` | **NOT NULL** |  | Unit cost per beneficiary / deliverable unit in ZAR. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_GrantProjectBudget_GrantApplication_GrantApplicationId` | `GrantApplicationId` | `dbo.GrantApplication` | `Cascade` |
| `FK_GrantProjectBudget_StrategicPriority_StrategicPriorityId` | `StrategicPriorityId` | `dbo.StrategicPriority` | `SetNull` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_GrantProjectBudget_ExpenseCategory` | `ExpenseCategory` | No |
| `IX_GrantProjectBudget_GrantApplicationId` | `GrantApplicationId` | No |
| `IX_GrantProjectBudget_StrategicPriorityId` | `StrategicPriorityId` | No |

---

### <a id="granttranchepayment"></a> `dbo.GrantTranchePayment`

**Description:** Tranche Invoices and Payment Requisitions.  
**CLR Model:** `Nsdms.Domain.Entities.GrantTranchePayment`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ApprovalComments` | `nvarchar(1000)` | NULL |  | Financial audit notes, ledger allocations, or rejection justifications. |
| `ApprovedPaymentAmount` | `decimal(18,2)` | **NOT NULL** |  | Net verified amount approved for EFT release by MerSETA finance in ZAR. |
| `BankReference` | `nvarchar(100)` | NULL |  | Electronic bank payment reference appearing on statements. |
| `BatchNumber` | `nvarchar(100)` | NULL |  | Bank disbursement batch grouping reference identifier. |
| `ClaimedAmount` | `decimal(18,2)` | **NOT NULL** |  | Gross claimed invoice monetary value in ZAR. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `FinanceApprovalDate` | `datetime2` | NULL |  | Date and time of finance dual-authorization signoff. |
| `FinanceApproverUserId` | `nvarchar(max)` | NULL |  | User identifier of the finance manager who authorized payment. |
| `GrantApplicationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the originating GrantApplication. |
| `GrantMoaMilestoneId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the parent GrantMoaMilestone. |
| `InvoiceDate` | `datetime2` | **NOT NULL** |  | Official date on the submitted tax invoice. |
| `InvoiceNumber` | `nvarchar(100)` | **NOT NULL** |  | Employer or provider Tax Invoice reference number. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `PaymentDate` | `datetime2` | NULL |  | Date when the bank EFT transaction cleared. |
| `PaymentReferenceNumber` | `nvarchar(100)` | **NOT NULL** |  | Internal financial voucher reference tracking number. |
| `PaymentStatusCode` | `nvarchar(50)` | **NOT NULL** |  | Payout workflow state code (e.g. Draft, Submitted, Finance Approved, Batch Scheduled, Paid, Rejected). |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_GrantTranchePayment_GrantApplication_GrantApplicationId` | `GrantApplicationId` | `dbo.GrantApplication` | `Restrict` |
| `FK_GrantTranchePayment_GrantMoaMilestone_GrantMoaMilestoneId` | `GrantMoaMilestoneId` | `dbo.GrantMoaMilestone` | `Restrict` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_GrantTranchePayment_GrantApplicationId` | `GrantApplicationId` | No |
| `IX_GrantTranchePayment_GrantMoaMilestoneId` | `GrantMoaMilestoneId` | No |
| `IX_GrantTranchePayment_PaymentReferenceNumber` | `PaymentReferenceNumber` | ✅ Yes |
| `IX_GrantTranchePayment_PaymentStatusCode` | `PaymentStatusCode` | No |

---

### <a id="intersetatransfer"></a> `dbo.InterSetaTransfer`

**Description:** Inter-SETA Transfer of employer registration and levy funds between SETAs.  
**CLR Model:** `Nsdms.Domain.Entities.InterSetaTransfer`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `Comments` | `nvarchar(1000)` | NULL |  | Legal comments and audit trail notes. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DhetReferenceNumber` | `nvarchar(100)` | NULL |  | Department of Higher Education & Training (DHET) gazetted approval number. |
| `EffectiveDate` | `datetime2` | **NOT NULL** |  | Effective gazetted date of transfer. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `OrganisationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the migrating Employer Organisation. |
| `OtherSetaCode` | `nvarchar(50)` | **NOT NULL** |  | Acronym/Code of counterpart SETA (e.g. CHIETA, EWSETA, TETA, SERVICES, W&RSETA). |
| `OtherSetaName` | `nvarchar(150)` | **NOT NULL** |  | Full statutory title of counterpart SETA. |
| `SetaApprovalReference` | `nvarchar(100)` | NULL |  | Official SETA executive signoff reference. |
| `TransferAmount` | `decimal(18,2)` | **NOT NULL** |  | Uncommitted accumulated levy funds transferred between SETAs in ZAR. |
| `TransferReason` | `nvarchar(500)` | **NOT NULL** |  | Operational or legal justification for employer migration. |
| `TransferStatusCode` | `nvarchar(50)` | **NOT NULL** |  | Transfer lifecycle status code (e.g. Initiated, Documents Verified, Approved by CEO, Transferred, Rejected). |
| `TransferType` | `nvarchar(50)` | **NOT NULL** |  | Direction of migration (Incoming to MerSETA or Outgoing to another SETA). |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_InterSetaTransfer_Organisation_OrganisationId` | `OrganisationId` | `dbo.Organisation` | `Restrict` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_InterSetaTransfer_OrganisationId` | `OrganisationId` | No |
| `IX_InterSetaTransfer_OtherSetaCode` | `OtherSetaCode` | No |
| `IX_InterSetaTransfer_TransferStatusCode` | `TransferStatusCode` | No |

---

### <a id="learnerassessment"></a> `dbo.LearnerAssessment`

**Description:** Formative and summative learning programme unit standard assessment evaluations, capturing all statutory fields required for SETMIS File 503 (Unit Standard Enrolment) reporting.  
**CLR Model:** `Nsdms.Domain.Entities.LearnerAssessment`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AssessmentDate` | `datetime2` | **NOT NULL** |  | Date when the assessment evaluation occurred. |
| `AssessorEtqaId` | `nvarchar(10)` | **NOT NULL** |  | Submitting ETQA ID of the Assessor (references lookup.SetaType, default 17 for merSETA). |
| `AssessorRegistrationNumber` | `nvarchar(50)` | NULL |  | Registered ETQA Assessor number conducting evaluation (SETMIS File 503). |
| `CertificateNumber` | `nvarchar(100)` | NULL |  | Statement of Results or Certificate serial number. |
| `CompanyLearnerId` | `int` | NULL | 🔗 **FK** | Foreign key referencing the parent CompanyLearner agreement. |
| `CompetencyStatusCode` | `nvarchar(15)` | NULL |  | Competency outcome code (e.g. COMPETENT, NOT_YET_COMPETENT). |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `CumulativeSpend` | `decimal(18,2)` | **NOT NULL** |  | Cumulative funding disbursed for this unit standard intervention. |
| `EconomicStatusId` | `nvarchar(10)` | **NOT NULL** |  | Learner economic employment standing code (references lookup.EconomicStatusType). |
| `EnrolmentStatusId` | `nvarchar(10)` | **NOT NULL** |  | Statutory enrolment assessment status code (references lookup.EnrolmentStatusType: 01 Enrolled, 02 Achieved, 03 Certificated, 04 Terminated). |
| `EnrolmentStatusReasonId` | `nvarchar(10)` | NULL |  | Statutory reason code for status changes (references lookup.EnrolmentStatusReasonType). |
| `EnrolmentTypeId` | `nvarchar(10)` | **NOT NULL** |  | Learner delivery mode classification code (references lookup.EnrolmentType: 01 Contact, 02 Distance, 03 Mixed Mode, 04 Workplace). |
| `EtqaAssessorId` | `int` | NULL | 🔗 **FK** | Foreign key referencing the conducting EtqaAssessor. |
| `FundingId` | `nvarchar(10)` | **NOT NULL** |  | Funding vehicle identifier (references lookup.FundingType: 01 SETA Funded, 02 Employer, 03 NSF). |
| `ModerationDate` | `datetime2` | NULL |  | Date when assessment moderation review was finalized. |
| `ModeratorPersonId` | `int` | NULL | 🔗 **FK** | Optional foreign key referencing the internal/external moderator Person. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `NonNqfInterventionCode` | `nvarchar(50)` | NULL |  | Non-NQF Skills Programme / Course Code if applicable. |
| `OfoCode` | `nvarchar(20)` | NULL |  | DHET Organising Framework for Occupations statutory code (references lookup.OfoCodeType). |
| `OrganisationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the host Employer Organisation or provider. |
| `PartOfId` | `nvarchar(10)` | **NOT NULL** |  | Programme hierarchy and articulation classification code (references lookup.PartOfType: 01 Stand-alone, 02 Part of Qual, 03 Part of Learnership). |
| `PersonId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the learner Person evaluated. |
| `QualificationTitle` | `nvarchar(200)` | **NOT NULL** |  | Registered qualification title assessed. |
| `TrainingProviderId` | `int` | NULL | 🔗 **FK** | Foreign key referencing the accredited Skills Development Training Provider (Assessment Centre). |
| `UnitStandardId` | `int` | NULL |  | SAQA Unit Standard ID (e.g. 119472, 243272) for SETMIS File 503. |
| `UnitStandardTitle` | `nvarchar(250)` | NULL |  | SAQA Unit Standard title. |
| `UrbanRuralId` | `nvarchar(10)` | **NOT NULL** |  | Geographic intervention area classification (references lookup.UrbanRuralType: 01 Urban, 02 Rural). |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_LearnerAssessment_CompanyLearner_CompanyLearnerId` | `CompanyLearnerId` | `dbo.CompanyLearner` | `Cascade` |
| `FK_LearnerAssessment_EtqaAssessor_EtqaAssessorId` | `EtqaAssessorId` | `dbo.EtqaAssessor` | `Restrict` |
| `FK_LearnerAssessment_Person_ModeratorPersonId` | `ModeratorPersonId` | `dbo.Person` | `Restrict` |
| `FK_LearnerAssessment_Organisation_OrganisationId` | `OrganisationId` | `dbo.Organisation` | `Restrict` |
| `FK_LearnerAssessment_Person_PersonId` | `PersonId` | `dbo.Person` | `Restrict` |
| `FK_LearnerAssessment_TrainingProvider_TrainingProviderId` | `TrainingProviderId` | `dbo.TrainingProvider` | `Restrict` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_LearnerAssessment_AssessmentDate` | `AssessmentDate` | No |
| `IX_LearnerAssessment_CompanyLearnerId` | `CompanyLearnerId` | No |
| `IX_LearnerAssessment_CompetencyStatusCode` | `CompetencyStatusCode` | No |
| `IX_LearnerAssessment_EnrolmentStatusId` | `EnrolmentStatusId` | No |
| `IX_LearnerAssessment_EnrolmentTypeId` | `EnrolmentTypeId` | No |
| `IX_LearnerAssessment_EtqaAssessorId` | `EtqaAssessorId` | No |
| `IX_LearnerAssessment_ModeratorPersonId` | `ModeratorPersonId` | No |
| `IX_LearnerAssessment_OrganisationId` | `OrganisationId` | No |
| `IX_LearnerAssessment_PartOfId` | `PartOfId` | No |
| `IX_LearnerAssessment_PersonId` | `PersonId` | No |
| `IX_LearnerAssessment_TrainingProviderId` | `TrainingProviderId` | No |
| `IX_LearnerAssessment_UnitStandardId` | `UnitStandardId` | No |

---

### <a id="learnertradetest"></a> `dbo.LearnerTradeTest`

**Description:** Trade test final competency evaluation and artisan certification attempt records (ARPL & standard), fully normalized with statutory columns required for SETMIS File 505 (Trade Test) reporting.  
**CLR Model:** `Nsdms.Domain.Entities.LearnerTradeTest`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AssessorEtqaId` | `nvarchar(10)` | NULL |  | Submitting ETQA ID of the registered Assessor (default 17). |
| `AssessorPersonId` | `int` | NULL | 🔗 **FK** | Foreign key referencing the examining trade test assessor Person. |
| `AssessorRegistrationNumber` | `nvarchar(50)` | NULL |  | Registered ETQA Assessor registration number conducting examination (SETMIS File 505). |
| `CertificateIssueDate` | `datetime2` | NULL |  | Date when the artisan qualification certificate was issued. |
| `CompanyLearnerId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the registered CompanyLearner contract. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `ModeratorEtqaId` | `nvarchar(10)` | NULL |  | Submitting ETQA ID of the registered Moderator (default 17). |
| `ModeratorPersonId` | `int` | NULL | 🔗 **FK** | Foreign key referencing the verifying moderator Person. |
| `ModeratorRegistrationNumber` | `nvarchar(50)` | NULL |  | Registered ETQA Moderator / NAMB Verifier registration number (SETMIS File 505). |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `QualificationId` | `nvarchar(50)` | NULL |  | SAQA Qualification ID or OFO Trade Code corresponding to the trade test (SETMIS File 505). |
| `Remarks` | `nvarchar(max)` | NULL |  | Assessment feedback and examiner moderation remarks. |
| `ResultStatusCode` | `nvarchar(50)` | NULL |  | Legacy assessment outcome status code (e.g. Scheduled, Competent, NotYetCompetent, Absent). |
| `SerialCertificateNumber` | `nvarchar(50)` | NULL |  | National Red Seal Artisan Trade Certificate serial number issued upon competency. |
| `TestCenterName` | `nvarchar(150)` | **NOT NULL** |  | Name and facility location of accredited Trade Test Center (TTC). |
| `TradeCode` | `nvarchar(50)` | NULL |  | OFO Trade Code corresponding to the trade test (SETMIS File 505). |
| `TradeTestCentreCode` | `nvarchar(50)` | NULL |  | Accredited Trade Test Centre (TTC) Provider Code as registered on NLRD/SETMIS (SETMIS File 505). |
| `TradeTestCentreEtqaId` | `nvarchar(10)` | NULL |  | Submitting ETQA ID of Trade Test Centre (references lookup.SetaType, default 17 for merSETA). |
| `TradeTestDate` | `datetime2` | **NOT NULL** |  | Examination date on which the trade test was conducted. |
| `TradeTestNumber` | `int` | **NOT NULL** |  | Trade test examination attempt index number (e.g. 1, 2, 3) for SETMIS File 505. |
| `TradeTestResultId` | `nvarchar(10)` | NULL |  | Trade test competency outcome lookup code (references lookup.TradeTestResultType: 01 Competent, 02 Not Yet Competent). |
| `TradeTestResultReasonId` | `nvarchar(10)` | NULL |  | Statutory assessment outcome reason code (references lookup.TradeTestResultReasonType: 01 Assessment Criteria Satisfied). |
| `TradeTitle` | `nvarchar(150)` | **NOT NULL** |  | Designated artisan trade title (e.g. Diesel Mechanic, Boilermaker, Fitter & Turner, Electrician, Welder). |
| `TrainingProviderEtqaId` | `nvarchar(10)` | NULL |  | Original training provider Submitting ETQA ID (default 17). |
| `TrainingProviderId` | `int` | NULL | 🔗 **FK** | Foreign key referencing original Skills Development Training Provider (SDP). |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_LearnerTradeTest_Person_AssessorPersonId` | `AssessorPersonId` | `dbo.Person` | `Restrict` |
| `FK_LearnerTradeTest_CompanyLearner_CompanyLearnerId` | `CompanyLearnerId` | `dbo.CompanyLearner` | `Cascade` |
| `FK_LearnerTradeTest_Person_ModeratorPersonId` | `ModeratorPersonId` | `dbo.Person` | `Restrict` |
| `FK_LearnerTradeTest_TrainingProvider_TrainingProviderId` | `TrainingProviderId` | `dbo.TrainingProvider` | `SetNull` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_LearnerTradeTest_CompanyLearnerId` | `CompanyLearnerId` | No |
| `IX_LearnerTradeTest_QualificationId` | `QualificationId` | No |
| `IX_LearnerTradeTest_ResultStatusCode` | `ResultStatusCode` | No |
| `IX_LearnerTradeTest_SerialCertificateNumber` | `SerialCertificateNumber` | No |
| `IX_LearnerTradeTest_TradeCode` | `TradeCode` | No |
| `IX_LearnerTradeTest_TradeTestCentreCode` | `TradeTestCentreCode` | No |
| `IX_LearnerTradeTest_TradeTestDate` | `TradeTestDate` | No |
| `IX_LearnerTradeTest_TradeTestResultId` | `TradeTestResultId` | No |
| `IX_LearnerTradeTest_TrainingProviderId` | `TrainingProviderId` | No |

---

### <a id="learnertradetestapplication"></a> `dbo.LearnerTradeTestApplication`

**Description:** Artisan Trade Test Application and Assessment Record (Section 26D / Section 28 ARPL / Apprenticeships).  
**CLR Model:** `Nsdms.Domain.Entities.LearnerTradeTestApplication`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ApplicationNumber` | `nvarchar(50)` | **NOT NULL** |  | Domain property for ApplicationNumber. |
| `ApplicationTypeCode` | `nvarchar(50)` | **NOT NULL** |  | Section26D (Standard Apprenticeship), Section28 (ARPL - Recognition of Prior Learning), Section26F |
| `AssessmentCenterName` | `nvarchar(max)` | NULL |  | Domain property for AssessmentCenterName. |
| `AssessmentDate` | `datetime2` | NULL |  | Domain property for AssessmentDate. |
| `AssessorName` | `nvarchar(max)` | NULL |  | Domain property for AssessorName. |
| `AssessorRegistrationNumber` | `nvarchar(max)` | NULL |  | Domain property for AssessorRegistrationNumber. |
| `AttemptNumber` | `int` | **NOT NULL** |  | Domain property for AttemptNumber. |
| `CertificateIssueDate` | `datetime2` | NULL |  | Domain property for CertificateIssueDate. |
| `CompanyLearnerId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `CompetencyStatusCode` | `nvarchar(50)` | **NOT NULL** |  | Assessment outcome: Competent, NotYetCompetent, Absent, Deferred |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DesignatedTradeLevel` | `nvarchar(max)` | NULL |  | Domain property for DesignatedTradeLevel. |
| `LearnerReadinessDate` | `datetime2` | NULL |  | Domain property for LearnerReadinessDate. |
| `ModeratorName` | `nvarchar(max)` | NULL |  | Domain property for ModeratorName. |
| `ModeratorRegistrationNumber` | `nvarchar(max)` | NULL |  | Domain property for ModeratorRegistrationNumber. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `NambApprovalDate` | `datetime2` | NULL |  | Domain property for NambApprovalDate. |
| `NambDecisionStatusCode` | `nvarchar(max)` | **NOT NULL** |  | Domain property for NambDecisionStatusCode. |
| `NambSerialNumber` | `nvarchar(100)` | NULL |  | Serial number allocated by the National Artisan Moderation Body (NAMB). |
| `NambSubmissionBatchId` | `int` | NULL | 🔗 **FK** | Foreign key referencing the parent NAMB submission batch. |
| `NambSubmissionDate` | `datetime2` | NULL |  | Domain property for NambSubmissionDate. |
| `Notes` | `nvarchar(max)` | NULL |  | Domain property for Notes. |
| `OrganisationId` | `int` | NULL | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `PersonId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `ScheduledStartTime` | `nvarchar(max)` | NULL |  | Domain property for ScheduledStartTime. |
| `SerialCertificateNumber` | `nvarchar(100)` | NULL |  | Final Serial Number for the Trade Certificate. |
| `StatusCode` | `nvarchar(50)` | **NOT NULL** |  | Workflow status: Draft, Submitted, TradeCenterAllocated, AwaitingNambApproval, Assessing, Competent, Certified, Rejected |
| `TradeOfoCode` | `nvarchar(max)` | NULL |  | Domain property for TradeOfoCode. |
| `TradeTitle` | `nvarchar(200)` | **NOT NULL** |  | Domain property for TradeTitle. |
| `TrainingProviderId` | `int` | NULL | 🔗 **FK** | Foreign key relational reference to parent entity. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_LearnerTradeTestApplication_CompanyLearner_CompanyLearnerId` | `CompanyLearnerId` | `dbo.CompanyLearner` | `Restrict` |
| `FK_LearnerTradeTestApplication_NambSubmissionBatch_NambSubmissionBatchId` | `NambSubmissionBatchId` | `dbo.NambSubmissionBatch` | `ClientSetNull` |
| `FK_LearnerTradeTestApplication_Organisation_OrganisationId` | `OrganisationId` | `dbo.Organisation` | `SetNull` |
| `FK_LearnerTradeTestApplication_Person_PersonId` | `PersonId` | `dbo.Person` | `Restrict` |
| `FK_LearnerTradeTestApplication_TrainingProvider_TrainingProviderId` | `TrainingProviderId` | `dbo.TrainingProvider` | `SetNull` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_LearnerTradeTestApplication_ApplicationNumber` | `ApplicationNumber` | ✅ Yes |
| `IX_LearnerTradeTestApplication_CompanyLearnerId` | `CompanyLearnerId` | No |
| `IX_LearnerTradeTestApplication_NambSerialNumber` | `NambSerialNumber` | No |
| `IX_LearnerTradeTestApplication_PersonId` | `PersonId` | No |
| `IX_LearnerTradeTestApplication_SerialCertificateNumber` | `SerialCertificateNumber` | No |
| `IX_LearnerTradeTestApplication_StatusCode` | `StatusCode` | No |

---

### <a id="levyfile"></a> `dbo.LevyFile`

**Description:** Monthly SARS Skills Development Levy file import batches.  
**CLR Model:** `Nsdms.Domain.Entities.LevyFile`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ControlRecordCount` | `int` | NULL |  | Stated record count from file trailer record for control reconciliation. |
| `ControlTotalAmount` | `decimal(18,2)` | NULL |  | Stated total Rand value from file trailer record for control reconciliation. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DigitalSecuritySeal` | `nvarchar(64)` | NULL |  | Cryptographic Digital Security Seal (SHA-256 hash) for file verification and non-repudiation. |
| `FileName` | `nvarchar(255)` | **NOT NULL** |  | Original file name uploaded from SARS levy distribution feed. |
| `FileRef` | `nvarchar(100)` | **NOT NULL** |  | Internal unique batch reference identifier. |
| `ImportDate` | `datetime2` | **NOT NULL** |  | Timestamp when the levy file was ingested into the system. |
| `ImportStatusCode` | `nvarchar(25)` | NULL |  | Processing status code (e.g. Uploaded, Processed, Reconciled, Error). |
| `IsControlValidated` | `bit` | **NOT NULL** |  | Indicates whether line counts and gross Rand totals perfectly matched the file trailer control totals. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `ProcessingDurationMs` | `bigint` | NULL |  | Ingestion and reconciliation processing duration in milliseconds. |
| `TotalAmount` | `decimal(18,2)` | **NOT NULL** |  | Aggregate monetary value of all levy allocations in this file in ZAR. |
| `TotalRecords` | `int` | **NOT NULL** |  | Total count of line items contained in the levy file. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_LevyFile_DigitalSecuritySeal` | `DigitalSecuritySeal` | No |
| `IX_LevyFile_FileRef` | `FileRef` | No |
| `IX_LevyFile_ImportDate` | `ImportDate` | No |
| `IX_LevyFile_ImportStatusCode` | `ImportStatusCode` | No |

---

### <a id="levyfileline"></a> `dbo.LevyFileLine`

**Description:** Individual employer monthly SARS levy transaction breakdown.  
**CLR Model:** `Nsdms.Domain.Entities.LevyFileLine`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `bigint` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AdminLevyAmount` | `decimal(18,2)` | **NOT NULL** |  | 10.5% MerSETA administration levy portion in ZAR. |
| `ChamberCode` | `nvarchar(20)` | NULL |  | Resolved merSETA Chamber Code based on statutory SIC mapping (AUTO, METAL, MOTOR, NEW_TYRE, PLASTICS, OTHER). |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DiscretionaryLevyAmount` | `decimal(18,2)` | **NOT NULL** |  | 49.5% Discretionary Grant portion allocated to sector skills funding in ZAR. |
| `HasSicCodeMismatch` | `bit` | **NOT NULL** |  | Indicates whether the declared SARS SIC code differs from the employer's verified master profile. |
| `InterestAmount` | `decimal(18,2)` | **NOT NULL** |  | SARS penalty interest charged on late levy payments in ZAR. |
| `IsOutOfScopeSeta` | `bit` | **NOT NULL** |  | Indicates whether this levy transaction belongs to a non-merSETA industry requiring Inter-SETA transfer. |
| `IsReconciled` | `bit` | **NOT NULL** |  | Indicates whether this levy line has been matched and reconciled to an employer ledger. |
| `LevyFileId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the parent LevyFile batch. |
| `MandatoryLevyAmount` | `decimal(18,2)` | **NOT NULL** |  | 20% Mandatory Grant portion reserved for compliant employer rebates in ZAR. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `PenaltyAmount` | `decimal(18,2)` | **NOT NULL** |  | Statutory penalty fees charged on late levy submissions in ZAR. |
| `QctoLevyAmount` | `decimal(18,2)` | **NOT NULL** |  | 0.5% Quality Council for Trades and Occupations (QCTO) levy portion in ZAR. |
| `SchemeYear` | `nvarchar(10)` | **NOT NULL** |  | Scheme year or payment month reference (e.g. 2026-04). |
| `SdlNumber` | `nvarchar(20)` | **NOT NULL** |  | SARS Skills Development Levy number (e.g. L123456789). |
| `SetaCode` | `nvarchar(10)` | **NOT NULL** |  | Resolved statutory SETA code (SETA 17 for merSETA, or other SETA code for out-of-scope files). |
| `SicCode` | `nvarchar(20)` | NULL |  | Declared 5-digit SIC code reported in the SARS monthly submission. |
| `TotalLevyAmount` | `decimal(18,2)` | **NOT NULL** |  | Gross total levy amount received for this employer in ZAR. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_LevyFileLine_LevyFile_LevyFileId` | `LevyFileId` | `dbo.LevyFile` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_LevyFileLine_ChamberCode` | `ChamberCode` | No |
| `IX_LevyFileLine_HasSicCodeMismatch` | `HasSicCodeMismatch` | No |
| `IX_LevyFileLine_IsOutOfScopeSeta` | `IsOutOfScopeSeta` | No |
| `IX_LevyFileLine_LevyFileId` | `LevyFileId` | No |
| `IX_LevyFileLine_SdlNumber` | `SdlNumber` | No |
| `IX_LevyFileLine_SetaCode` | `SetaCode` | No |
| `IX_LevyFileLine_SicCode` | `SicCode` | No |

---

### <a id="mandatorygrantdisbursement"></a> `dbo.MandatoryGrantDisbursement`

**Description:** Mandatory Grant 20% Rebate Payouts for compliant employers submitting WSP/ATR.  
**CLR Model:** `Nsdms.Domain.Entities.MandatoryGrantDisbursement`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `BankAccountSnapshot` | `nvarchar(200)` | NULL |  | JSON snapshot of verified employer banking details at time of disbursement. |
| `BatchNumber` | `nvarchar(100)` | NULL |  | Finance EFT disbursement batch identifier. |
| `CalculatedRebateAmount` | `decimal(18,2)` | **NOT NULL** |  | Statutory 20% Mandatory Grant rebate amount calculated in ZAR. |
| `Comments` | `nvarchar(1000)` | NULL |  | Governance remarks and reconciliation ledger comments. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DisbursementReference` | `nvarchar(100)` | **NOT NULL** |  | Unique mandatory grant disbursement voucher reference number. |
| `DisbursementStatusCode` | `nvarchar(50)` | **NOT NULL** |  | Payout processing state code (e.g. Calculated, Approved, Paid, On Hold). |
| `FinYear` | `int` | **NOT NULL** |  | Financial scheme year for the rebate calculation. |
| `LeviesReceivedAmount` | `decimal(18,2)` | **NOT NULL** |  | Total SARS levy received for this employer during the period in ZAR. |
| `LevyPeriod` | `nvarchar(max)` | NULL |  | Scheme month or quarterly levy distribution period (e.g. 2026-Q1). |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `OrganisationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the receiving Employer Organisation. |
| `PaymentDate` | `datetime2` | NULL |  | Date when the rebate EFT payment cleared. |
| `WspSubmissionId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the compliant WspSubmission. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_MandatoryGrantDisbursement_Organisation_OrganisationId` | `OrganisationId` | `dbo.Organisation` | `Restrict` |
| `FK_MandatoryGrantDisbursement_WspSubmission_WspSubmissionId` | `WspSubmissionId` | `dbo.WspSubmission` | `Restrict` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_MandatoryGrantDisbursement_DisbursementReference` | `DisbursementReference` | ✅ Yes |
| `IX_MandatoryGrantDisbursement_DisbursementStatusCode` | `DisbursementStatusCode` | No |
| `IX_MandatoryGrantDisbursement_OrganisationId` | `OrganisationId` | No |
| `IX_MandatoryGrantDisbursement_WspSubmissionId` | `WspSubmissionId` | No |

---

### <a id="moaclause"></a> `dbo.MoaClause`

**Description:** Reusable atomic legal clause in the MerSETA clause library.  
**CLR Model:** `Nsdms.Domain.Entities.MoaClause`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `Category` | `nvarchar(50)` | **NOT NULL** |  | Classification category (e.g. Statutory, Financial, Compliance, General, Annexure). |
| `ClauseCode` | `nvarchar(100)` | **NOT NULL** |  | Unique clause reference code (e.g. CLAUSE-PREAMBLE, CLAUSE-POPIA, CLAUSE-TRANCHES-01). |
| `ClauseContent` | `nvarchar(max)` | **NOT NULL** |  | Rich Markdown content of the clause containing dynamic tokens (e.g. {{OrganisationName}}, {{TotalContractValue}}). |
| `ClauseTitle` | `nvarchar(200)` | **NOT NULL** |  | Human-readable title of the legal clause. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `IsActive` | `bit` | **NOT NULL** |  | Indicates whether the clause is active in the clause library. |
| `IsMandatory` | `bit` | **NOT NULL** |  | Indicates if this clause is legally mandatory across all template variants. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_MoaClause_Category` | `Category` | No |
| `IX_MoaClause_ClauseCode` | `ClauseCode` | ✅ Yes |
| `IX_MoaClause_IsActive` | `IsActive` | No |

---

### <a id="moaexecutionsnapshot"></a> `dbo.MoaExecutionSnapshot`

**Description:** Cryptographically frozen snapshot of an issued MoA contract for legal non-repudiation and audit defense.  
**CLR Model:** `Nsdms.Domain.Entities.MoaExecutionSnapshot`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `FrozenAt` | `datetime2` | **NOT NULL** |  | Timestamp when this snapshot was frozen and issued. |
| `GrantMoaId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the issued GrantMoa contract. |
| `MoaTemplateId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the MoaTemplate version applied at the moment of issuance. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `PdfStorageUri` | `nvarchar(500)` | NULL |  | Immutable digital storage URI of the generated signed PDF document. |
| `RenderedContent` | `nvarchar(max)` | **NOT NULL** |  | Fully assembled legal text in Markdown/Text format with all tokens interpolated. |
| `RenderedContentHash` | `nvarchar(100)` | **NOT NULL** |  | Cryptographic SHA-256 digital fingerprint hash of the assembled document content. |
| `SignatoryEmployer` | `nvarchar(150)` | NULL |  | Employer signatory name captured at signing time. |
| `SignatorySeta` | `nvarchar(150)` | NULL |  | MerSETA delegated signatory name captured at signing time. |
| `TemplateVersionNumber` | `nvarchar(20)` | **NOT NULL** |  | Captured template version string (e.g. 1.2.0). |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_MoaExecutionSnapshot_GrantMoa_GrantMoaId` | `GrantMoaId` | `dbo.GrantMoa` | `Cascade` |
| `FK_MoaExecutionSnapshot_MoaTemplate_MoaTemplateId` | `MoaTemplateId` | `dbo.MoaTemplate` | `Restrict` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_MoaExecutionSnapshot_FrozenAt` | `FrozenAt` | No |
| `IX_MoaExecutionSnapshot_GrantMoaId` | `GrantMoaId` | No |
| `IX_MoaExecutionSnapshot_MoaTemplateId` | `MoaTemplateId` | No |
| `IX_MoaExecutionSnapshot_RenderedContentHash` | `RenderedContentHash` | No |

---

### <a id="moatemplate"></a> `dbo.MoaTemplate`

**Description:** Master legal template defining an MoA contract structure for a specific financial year and grant policy.  
**CLR Model:** `Nsdms.Domain.Entities.MoaTemplate`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ApprovalStatus` | `nvarchar(50)` | **NOT NULL** |  | Governance approval lifecycle status (Draft, UnderReview, Approved, Archived). |
| `ApprovedAt` | `datetime2` | NULL |  | Timestamp when governance approval was executed. |
| `ApprovedBy` | `nvarchar(100)` | NULL |  | Username of the governance/legal officer who approved this template. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `EffectiveFrom` | `datetime2` | **NOT NULL** |  | Effective starting date for this template version. |
| `EffectiveTo` | `datetime2` | NULL |  | Optional expiration / sunset date for this template version. |
| `FinancialYear` | `int` | **NOT NULL** |  | Financial year the template is effective for (e.g. 2026). |
| `GrantTypeCode` | `nvarchar(50)` | **NOT NULL** |  | Statutory Grant Type code (e.g. DiscretionaryGrant, SpecialProject, Bursary, Candidacy). |
| `IsActive` | `bit` | **NOT NULL** |  | Whether this template is active for new contract issuances. |
| `LegalEntityType` | `nvarchar(50)` | **NOT NULL** |  | Target legal entity type filter (e.g. Employer, TVET, University, NGO, All). |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `TemplateCode` | `nvarchar(100)` | **NOT NULL** |  | Unique administrative template code identifier (e.g. DG-STD-2026, SP-TVET-2026). |
| `TemplateTitle` | `nvarchar(200)` | **NOT NULL** |  | Descriptive name of the MoA template. |
| `VersionNumber` | `nvarchar(20)` | **NOT NULL** |  | Semantic version of the template policy (e.g. 1.0.0, 1.2.0, 2.0.0). |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_MoaTemplate_ApprovalStatus` | `ApprovalStatus` | No |
| `IX_MoaTemplate_TemplateCode` | `TemplateCode` | ✅ Yes |
| `IX_MoaTemplate_FinancialYear_GrantTypeCode_IsActive` | `FinancialYear, GrantTypeCode, IsActive` | No |

---

### <a id="moatemplatesection"></a> `dbo.MoaTemplateSection`

**Description:** Ordered section mapping a reusable clause into a specific MoA template with custom numbering and conditions.  
**CLR Model:** `Nsdms.Domain.Entities.MoaTemplateSection`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ConditionRuleJson` | `nvarchar(1000)` | NULL |  | Optional conditional inclusion rule expression in JSON format. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `IsMandatory` | `bit` | **NOT NULL** |  | Whether this section is mandatory for this specific template. |
| `MoaClauseId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the assigned MoaClause. |
| `MoaTemplateId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the parent MoaTemplate. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `SectionNumber` | `nvarchar(50)` | **NOT NULL** |  | Custom section number label in the generated contract (e.g. "1.0", "2.1", "Schedule A"). |
| `SectionTitle` | `nvarchar(200)` | **NOT NULL** |  | Section heading title override. |
| `SequenceOrder` | `int` | **NOT NULL** |  | Sorting sequence order within the document layout (e.g. 10, 20, 30). |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_MoaTemplateSection_MoaClause_MoaClauseId` | `MoaClauseId` | `dbo.MoaClause` | `Restrict` |
| `FK_MoaTemplateSection_MoaTemplate_MoaTemplateId` | `MoaTemplateId` | `dbo.MoaTemplate` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_MoaTemplateSection_MoaClauseId` | `MoaClauseId` | No |
| `IX_MoaTemplateSection_MoaTemplateId` | `MoaTemplateId` | No |
| `IX_MoaTemplateSection_MoaTemplateId_SequenceOrder` | `MoaTemplateId, SequenceOrder` | No |

---

### <a id="nambdecisionhistory"></a> `dbo.NambDecisionHistory`

**Description:** National Artisan Moderation Body (NAMB) adjudication trace and serial assignment log.  
**CLR Model:** `Nsdms.Domain.Entities.NambDecisionHistory`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DecisionDate` | `datetime2` | **NOT NULL** |  | Domain property for DecisionDate. |
| `DecisionNotes` | `nvarchar(max)` | NULL |  | Domain property for DecisionNotes. |
| `DecisionStatusCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for DecisionStatusCode. |
| `LearnerTradeTestApplicationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `NambBatchReference` | `nvarchar(max)` | NULL |  | Domain property for NambBatchReference. |
| `NambOfficerName` | `nvarchar(150)` | **NOT NULL** |  | Domain property for NambOfficerName. |
| `NambOfficialUserId` | `nvarchar(max)` | NULL |  | Domain property for NambOfficialUserId. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_NambDecisionHistory_LearnerTradeTestApplication_LearnerTradeTestApplicationId` | `LearnerTradeTestApplicationId` | `dbo.LearnerTradeTestApplication` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_NambDecisionHistory_DecisionStatusCode` | `DecisionStatusCode` | No |
| `IX_NambDecisionHistory_LearnerTradeTestApplicationId` | `LearnerTradeTestApplicationId` | No |

---

### <a id="nambsubmissionbatch"></a> `dbo.NambSubmissionBatch`

**Description:** Represents a formal National Artisan Moderation Body (NAMB) moderation and serial allocation submission batch.  
**CLR Model:** `Nsdms.Domain.Entities.NambSubmissionBatch`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AdjudicationDate` | `datetime2` | NULL |  | Date when NAMB concluded adjudication and serial assignment. |
| `ApprovedCandidates` | `int` | **NOT NULL** |  | Total candidates approved with NAMB serial allocation. |
| `BatchDescription` | `nvarchar(250)` | **NOT NULL** |  | Submission cycle or intake description. |
| `BatchReferenceNumber` | `nvarchar(50)` | **NOT NULL** |  | Unique statutory batch reference (e.g. NAMB-2026-B001). |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DigitalSecuritySeal` | `nvarchar(64)` | NULL |  | Cryptographic SHA-256 seal anchoring candidate records and allocated serials. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `NambModeratorNotes` | `nvarchar(max)` | NULL |  | Official NAMB moderator or committee notes. |
| `RejectedCandidates` | `int` | **NOT NULL** |  | Total candidates queried or rejected by NAMB moderation. |
| `Status` | `nvarchar(50)` | **NOT NULL** |  | Batch processing status (Draft, SubmittedToNamb, Approved, PartiallyApproved, Rejected). |
| `SubmissionDate` | `datetime2` | **NOT NULL** |  | Date when the batch was submitted to NAMB. |
| `TotalCandidates` | `int` | **NOT NULL** |  | Total number of trade test candidates included in the batch. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_NambSubmissionBatch_BatchReferenceNumber` | `BatchReferenceNumber` | ✅ Yes |
| `IX_NambSubmissionBatch_Status` | `Status` | No |

---

### <a id="nonsetacompany"></a> `dbo.NonSetaCompany`

**Description:** Non-SETA External Employer / Organisation registered with other Quality Councils / SETAs.  
**CLR Model:** `Nsdms.Domain.Entities.NonSetaCompany`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CompanyName` | `nvarchar(200)` | **NOT NULL** |  | Domain property for CompanyName. |
| `CompanyRegistrationNumber` | `nvarchar(max)` | NULL |  | Domain property for CompanyRegistrationNumber. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `Email` | `nvarchar(max)` | **NOT NULL** |  | Primary email address. |
| `IsActive` | `bit` | **NOT NULL** |  | Indicates whether the record is active and operational. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `PhoneNumber` | `nvarchar(max)` | **NOT NULL** |  | Primary contact telephone number. |
| `PhysicalAddress` | `nvarchar(max)` | NULL |  | Domain property for PhysicalAddress. |
| `PrimarySetaCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for PrimarySetaCode. |
| `SdlNumber` | `nvarchar(50)` | NULL |  | SARS Skills Development Levy registration number (e.g. L123456789). |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_NonSetaCompany_CompanyName` | `CompanyName` | No |
| `IX_NonSetaCompany_PrimarySetaCode` | `PrimarySetaCode` | No |
| `IX_NonSetaCompany_SdlNumber` | `SdlNumber` | No |

---

### <a id="nonsetaqualificationscompletion"></a> `dbo.NonSetaQualificationsCompletion`

**Description:** Cross-SETA Qualification & TVET College Achievement Verification for merSETA articulation.  
**CLR Model:** `Nsdms.Domain.Entities.NonSetaQualificationsCompletion`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AchievementDate` | `datetime2` | **NOT NULL** |  | Domain property for AchievementDate. |
| `CompanyLearnerId` | `int` | NULL | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `EndorsedByUserId` | `nvarchar(max)` | NULL |  | Domain property for EndorsedByUserId. |
| `EndorsementDate` | `datetime2` | NULL |  | Domain property for EndorsementDate. |
| `EndorsementNotes` | `nvarchar(max)` | NULL |  | Domain property for EndorsementNotes. |
| `ExternalCertificateNumber` | `nvarchar(100)` | **NOT NULL** |  | Domain property for ExternalCertificateNumber. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `NonSetaCompanyId` | `int` | NULL | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `NqfLevel` | `int` | **NOT NULL** |  | Domain property for NqfLevel. |
| `OriginatingSetaCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for OriginatingSetaCode. |
| `PersonId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `QualificationTitle` | `nvarchar(250)` | **NOT NULL** |  | Domain property for QualificationTitle. |
| `SaqaQualificationId` | `nvarchar(max)` | NULL |  | Domain property for SaqaQualificationId. |
| `TotalCreditsAchieved` | `int` | **NOT NULL** |  | Domain property for TotalCreditsAchieved. |
| `VerificationStatusCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for VerificationStatusCode. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_NonSetaQualificationsCompletion_CompanyLearner_CompanyLearnerId` | `CompanyLearnerId` | `dbo.CompanyLearner` | `ClientSetNull` |
| `FK_NonSetaQualificationsCompletion_NonSetaCompany_NonSetaCompanyId` | `NonSetaCompanyId` | `dbo.NonSetaCompany` | `SetNull` |
| `FK_NonSetaQualificationsCompletion_Person_PersonId` | `PersonId` | `dbo.Person` | `Restrict` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_NonSetaQualificationsCompletion_ExternalCertificateNumber` | `ExternalCertificateNumber` | No |
| `IX_NonSetaQualificationsCompletion_PersonId` | `PersonId` | No |
| `IX_NonSetaQualificationsCompletion_VerificationStatusCode` | `VerificationStatusCode` | No |

---

### <a id="organisation"></a> `dbo.Organisation`

**Description:** Registered employer, host workplace, or skills development entity under MerSETA jurisdiction, fully normalized with statutory columns required for SETMIS File 100 and File 200 reporting.  
**CLR Model:** `Nsdms.Domain.Entities.Organisation`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `BankAccountNumber` | `nvarchar(50)` | NULL |  | Bank account number for EFT transfers. |
| `BankAccountType` | `nvarchar(50)` | NULL |  | Bank account type (e.g. CHEQUE, CURRENT, SAVINGS). |
| `BankBranchCode` | `nvarchar(20)` | NULL |  | Universal bank branch clearance code. |
| `BankName` | `nvarchar(100)` | NULL |  | Commercial bank name for grant disbursement rebates. |
| `BankingDetailsVerified` | `bit` | **NOT NULL** |  | Indicates whether banking details and proof of banking have been verified by MerSETA finance. |
| `BrandColorHex` | `nvarchar(max)` | NULL |  | Primary corporate brand color hex code (e.g. #865300). |
| `ChamberCode` | `nvarchar(15)` | NULL |  | MerSETA chamber allocation code (references lookup.ChamberType: AUTO, METAL, PLASTICS, MOTOR, NEW_TYRE, OTHER). |
| `ChamberOverrideApprovedBy` | `nvarchar(100)` | NULL |  | Executive or administrator username who authorized the chamber override. |
| `ChamberOverrideDate` | `datetime2` | NULL |  | Date when the chamber override was approved and recorded. |
| `ChamberOverrideReason` | `nvarchar(500)` | NULL |  | Governance justification and Board/SSP reference for the manual chamber assignment override. |
| `CompanyName` | `nvarchar(200)` | **NOT NULL** |  | Registered legal corporate name of the enterprise. |
| `CompanySizeCode` | `nvarchar(15)` | NULL |  | Enterprise size classification code (references lookup.CompanySizeType: MICRO, SMALL, MEDIUM, LARGE). |
| `CountryCode` | `nvarchar(10)` | **NOT NULL** |  | Sovereign country lookup code (references lookup.CountryType, default ZA). |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `CustomMentorRatioCap` | `int` | NULL |  | Optional enterprise-wide uniform mentor capacity cap override (e.g. 5 learners per mentor across all site workshops). |
| `FaxNumber` | `nvarchar(50)` | NULL |  | Facsimile transmission number. |
| `IsActive` | `bit` | **NOT NULL** |  | Indicates whether the organisation is currently active. |
| `IsManualChamberOverride` | `bit` | **NOT NULL** |  | Indicates whether the organisation's chamber assignment was manually overridden instead of auto-derived from the SIC code. |
| `IsMentorRatioEnforced` | `bit` | NULL |  | Explicit mentor ratio enforcement override for this organisation (null = inherit Global, true = enforce, false = exempt). |
| `LevyCategoryCode` | `nvarchar(15)` | NULL |  | Organisation SARS levy compliance category code (e.g. LEVY_PAYING, NON_LEVY_PAYING, EXEMPT). |
| `LogoDocumentId` | `int` | NULL |  | Foreign key referencing the organisation high-DPI brand logo in DocumentMetadata vault. |
| `MainSdlNumber` | `nvarchar(20)` | NULL |  | Parent or holding company Main SDL number for enterprise site networks (SETMIS File 200). |
| `MentorRatioExemptionReason` | `nvarchar(500)` | NULL |  | Statutory or executive justification when the organisation is granted an exemption from mentor ratios (e.g. State-Owned Enterprise Training Academy). |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `OrganisationStatusCode` | `nvarchar(15)` | NULL |  | Current operational registration status code (references lookup.StatusType: ACTIVE, INACTIVE, SUSPENDED). |
| `OrganisationTypeCode` | `nvarchar(15)` | NULL |  | Legal organisation constitution type code (references lookup.OrganisationType: PTY_LTD, CC, PUBLIC_ENTITY, NGO_NPO). |
| `PhoneNumber` | `nvarchar(50)` | NULL |  | Primary telephone switchboard number. |
| `PhysicalAddress` | `nvarchar(500)` | NULL |  | Primary physical street address line 1. |
| `PhysicalAddressPostalCode` | `nvarchar(max)` | NULL |  | Physical address postal delivery code. |
| `PostalAddress` | `nvarchar(500)` | NULL |  | Postal delivery address. |
| `PostalAddressPostalCode` | `nvarchar(max)` | NULL |  | Postal delivery code. |
| `PrimaryContactPersonId` | `int` | NULL | 🔗 **FK** | Foreign key referencing the primary contact person. |
| `ProvinceCode` | `nvarchar(15)` | NULL |  | Head office geographic province lookup code (references lookup.ProvinceType: GP, KZN, WC, EC, FS, MP, NW, NC, LP). |
| `RegistrationNumber` | `nvarchar(50)` | NULL |  | CIPC Company or Close Corporation registration number (e.g. 2015/123456/07). |
| `SdlNumber` | `nvarchar(20)` | **NOT NULL** |  | SARS Skills Development Levy registration number (e.g. L123456789). |
| `SectorCode` | `nvarchar(15)` | NULL |  | SETA industrial sector classification code (references lookup.SectorType). |
| `SetaId` | `nvarchar(10)` | **NOT NULL** |  | Originating SETA classification code (references lookup.SetaType, default 17 for merSETA). |
| `SicCode` | `nvarchar(15)` | NULL |  | Standard Industrial Classification (SIC) 5-digit economic activity code (references lookup.SicCodeType). |
| `TaxNumber` | `nvarchar(50)` | NULL |  | SARS Income Tax reference number. |
| `TradingName` | `nvarchar(200)` | NULL |  | Trading name if operating under a different commercial title (T/A). |
| `WebsiteUrl` | `nvarchar(200)` | NULL |  | Corporate website URL. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_Organisation_Person_PrimaryContactPersonId` | `PrimaryContactPersonId` | `dbo.Person` | `Restrict` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_Organisation_ChamberCode` | `ChamberCode` | No |
| `IX_Organisation_CompanyName` | `CompanyName` | No |
| `IX_Organisation_CompanySizeCode` | `CompanySizeCode` | No |
| `IX_Organisation_IsActive` | `IsActive` | No |
| `IX_Organisation_IsManualChamberOverride` | `IsManualChamberOverride` | No |
| `IX_Organisation_IsMentorRatioEnforced` | `IsMentorRatioEnforced` | No |
| `IX_Organisation_LevyCategoryCode` | `LevyCategoryCode` | No |
| `IX_Organisation_MainSdlNumber` | `MainSdlNumber` | No |
| `IX_Organisation_OrganisationStatusCode` | `OrganisationStatusCode` | No |
| `IX_Organisation_ProvinceCode` | `ProvinceCode` | No |
| `IX_Organisation_SdlNumber` | `SdlNumber` | ✅ Yes |
| `IX_Organisation_SectorCode` | `SectorCode` | No |
| `IX_Organisation_SetaId` | `SetaId` | No |
| `IX_Organisation_SicCode` | `SicCode` | No |

---

### <a id="organisationcontact"></a> `dbo.OrganisationContact`

**Description:** Liaison contact person link associating an individual Person with an Employer Organisation.  
**CLR Model:** `Nsdms.Domain.Entities.OrganisationContact`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ContactType` | `nvarchar(max)` | **NOT NULL** |  | Alias property exposing ContactTypeCode. |
| `ContactTypeCode` | `nvarchar(15)` | NULL |  | Contact role classification code (e.g. Primary, SDF, Secondary, Financial, HR, TrainingManager). |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `Designation` | `nvarchar(max)` | NULL |  | Job title or professional designation within the enterprise. |
| `IsActive` | `bit` | **NOT NULL** |  | Indicates whether this contact link is currently active. |
| `IsPrimary` | `bit` | **NOT NULL** |  | Indicates whether this individual is the primary designated contact for official notices. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `OrganisationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the parent Employer Organisation. |
| `PersonId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the individual Person demographic profile. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_OrganisationContact_Organisation_OrganisationId` | `OrganisationId` | `dbo.Organisation` | `Cascade` |
| `FK_OrganisationContact_Person_PersonId` | `PersonId` | `dbo.Person` | `Restrict` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_OrganisationContact_ContactTypeCode` | `ContactTypeCode` | No |
| `IX_OrganisationContact_IsActive` | `IsActive` | No |
| `IX_OrganisationContact_OrganisationId` | `OrganisationId` | No |
| `IX_OrganisationContact_PersonId` | `PersonId` | No |

---

### <a id="organisationsite"></a> `dbo.OrganisationSite`

**Description:** Operational branch facility, plant, or training site belonging to an Employer Organisation, supporting multi-site mapping and GPS geolocation for SETMIS File 200 reporting.  
**CLR Model:** `Nsdms.Domain.Entities.OrganisationSite`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `City` | `nvarchar(max)` | NULL |  | Municipality or city location of the site. |
| `ContactPersonId` | `int` | NULL | 🔗 **FK** | Alias property referencing PrimaryContactPersonId. |
| `CountryCode` | `nvarchar(10)` | **NOT NULL** |  | Country code of site location (references lookup.CountryType, default ZA). |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `Email` | `nvarchar(150)` | NULL |  | Direct site general email address. |
| `FaxNumber` | `nvarchar(50)` | NULL |  | Direct site fax number. |
| `IsActive` | `bit` | **NOT NULL** |  | Indicates whether this site is actively operating. |
| `IsHeadOffice` | `bit` | **NOT NULL** |  | Indicates whether this facility is the corporate head office. |
| `Latitude` | `decimal(18,2)` | NULL |  | Decimal GPS Latitude coordinate (e.g. -26.204100). |
| `Longitude` | `decimal(18,2)` | NULL |  | Decimal GPS Longitude coordinate (e.g. 28.047300). |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `OrganisationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the parent Employer Organisation. |
| `PhoneNumber` | `nvarchar(50)` | NULL |  | Direct site telephone number. |
| `PhysicalAddress` | `nvarchar(500)` | NULL |  | Physical street address of the facility. |
| `PostalAddress` | `nvarchar(500)` | NULL |  | Postal delivery address of the branch site. |
| `PostalCode` | `nvarchar(max)` | NULL |  | Postal code of the site facility. |
| `PrimaryContactPersonId` | `int` | NULL | 🔗 **FK** | Foreign key referencing the designated site manager or contact person. |
| `ProvinceCode` | `nvarchar(15)` | NULL |  | South African province code where the facility operates (references lookup.ProvinceType). |
| `SiteCode` | `nvarchar(max)` | NULL |  | Internal branch or site reference code. |
| `SiteName` | `nvarchar(200)` | **NOT NULL** |  | Commercial or operational name of the facility site. |
| `SiteNumber` | `nvarchar(20)` | **NOT NULL** |  | SETMIS Statutory Site Number (e.g. 001, 002) for File 200 reporting. |
| `StatssaAreaCode` | `nvarchar(50)` | NULL |  | Statistics South Africa Spatial Area Code (references lookup.StatssaAreaCodeType). |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_OrganisationSite_Person_ContactPersonId` | `ContactPersonId` | `dbo.Person` | `ClientSetNull` |
| `FK_OrganisationSite_Organisation_OrganisationId` | `OrganisationId` | `dbo.Organisation` | `Cascade` |
| `FK_OrganisationSite_Person_PrimaryContactPersonId` | `PrimaryContactPersonId` | `dbo.Person` | `Restrict` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_OrganisationSite_IsActive` | `IsActive` | No |
| `IX_OrganisationSite_OrganisationId` | `OrganisationId` | No |
| `IX_OrganisationSite_PrimaryContactPersonId` | `PrimaryContactPersonId` | No |
| `IX_OrganisationSite_ProvinceCode` | `ProvinceCode` | No |
| `IX_OrganisationSite_SiteNumber` | `SiteNumber` | No |
| `IX_OrganisationSite_StatssaAreaCode` | `StatssaAreaCode` | No |

---

### <a id="person"></a> `dbo.Person`

**Description:** Core demographic profile storing individual identity, SA ID/Passport, Washington Group functioning, POPIA compliance, educational background, and contact details for SETMIS statutory reporting.  
**CLR Model:** `Nsdms.Domain.Entities.Person`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AlternateIdTypeId` | `nvarchar(10)` | NULL |  | SETMIS Alternate Identification Type Code (references lookup.AlternateIdType, e.g. 527 Passport, 540 Birth Certificate, 537 Work Permit). |
| `CellNumber` | `nvarchar(30)` | NULL |  | Mobile / cellular phone number. |
| `CitizenStatusCode` | `nvarchar(max)` | NULL |  | Citizen status lookup code (references lookup.CitizenStatusType: SA, PR, D, O, U). |
| `CommunicatingRatingId` | `nvarchar(10)` | NULL |  | Washington Group Communication functional difficulty rating (references lookup.CommunicatingRatingType: 01 None to 06 Cannot determine). |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DateOfBirth` | `datetime2` | NULL |  | Date of birth (auto-derived from RSA ID when available). |
| `DisabilityCode` | `nvarchar(15)` | NULL |  | Legacy disability classification lookup code (references lookup.DisabilityType). |
| `Email` | `nvarchar(150)` | NULL |  | Primary email contact address. |
| `EquityCode` | `nvarchar(15)` | NULL |  | BBBEE / SETMIS statutory equity classification code (references lookup.EquityType: BA, BC, BI, WH, OTH). |
| `FaxNumber` | `nvarchar(30)` | NULL |  | Facsimile contact number. |
| `FirstName` | `nvarchar(100)` | **NOT NULL** |  | Legal first / given name(s). |
| `Gender` | `nvarchar(max)` | NULL |  | Gender description (e.g. Male, Female, Other). |
| `GenderCode` | `nvarchar(15)` | NULL |  | Statutory gender lookup code (references lookup.GenderType: F, M). |
| `HearingRatingId` | `nvarchar(10)` | NULL |  | Washington Group Hearing functional difficulty rating (references lookup.HearingRatingType: 01 None to 06 Cannot determine). |
| `HomeLanguageCode` | `nvarchar(15)` | NULL |  | Home / native language classification code (references lookup.HomeLanguageType: ENG, AFR, ZUL, XHO, SASL, etc.). |
| `IsActive` | `bit` | **NOT NULL** |  | Indicates whether the person record is active. |
| `IsSouthAfricanCitizen` | `bit` | NULL |  | Indicates whether the individual is a South African citizen. |
| `LastName` | `nvarchar(100)` | **NOT NULL** |  | Legal family surname. |
| `LastSchoolEmisNumber` | `nvarchar(50)` | NULL |  | Department of Basic Education EMIS (Education Management Information System) School Registration Number. |
| `LastSchoolYear` | `nvarchar(10)` | NULL |  | Year in which individual exited / matriculated from last school attended (YYYY format). |
| `MiddleName` | `nvarchar(100)` | NULL |  | Middle or secondary given name(s). |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `NationalityCode` | `nvarchar(15)` | NULL |  | Country nationality lookup code (references lookup.NationalityType: SA, SDC, NAM, ZIM, etc.). |
| `PassportNumber` | `nvarchar(50)` | NULL |  | Foreign passport number or alternate identification number for non-South African citizens. |
| `PhoneNumber` | `nvarchar(30)` | NULL |  | Primary telephone contact number. |
| `PhysicalAddress` | `nvarchar(500)` | NULL |  | Physical residential street address line 1. |
| `PhysicalAddressPostalCode` | `nvarchar(max)` | NULL |  | Physical residential address postal code. |
| `PopiActConsentDate` | `datetime2` | NULL |  | Date when POPIA data processing consent was recorded. |
| `PopiActStatusId` | `nvarchar(10)` | NULL |  | Protection of Personal Information Act Statutory Consent Status (references lookup.PopiActStatusType: 01 Agreed, 02 Declined, 98 Unknown). |
| `PostalAddress` | `nvarchar(500)` | NULL |  | Postal delivery address. |
| `PostalAddressPostalCode` | `nvarchar(max)` | NULL |  | Postal address delivery code. |
| `PreviousAlternateId` | `nvarchar(50)` | NULL |  | Previous alternate identity number. |
| `PreviousAlternateIdTypeId` | `nvarchar(10)` | NULL |  | Previous alternate identity type code. |
| `PreviousLastName` | `nvarchar(100)` | NULL |  | Previous legal family surname before marriage / legal change. |
| `PreviousProviderCode` | `nvarchar(50)` | NULL |  | Previous Skills Development Provider code associated with this individual. |
| `PreviousProviderEtqaId` | `nvarchar(10)` | NULL |  | Previous Submitting ETQA ID associated with this individual. |
| `ProvinceCode` | `nvarchar(15)` | NULL |  | South African province code of primary residence (references lookup.ProvinceType: GP, KZN, WC, EC, FS, MP, NW, NC, LP). |
| `RememberingRatingId` | `nvarchar(10)` | NULL |  | Washington Group Memory / Cognitive functional difficulty rating (references lookup.RememberingRatingType: 01 None to 06 Cannot determine). |
| `RsaIdNumber` | `nvarchar(20)` | NULL |  | 13-digit South African National Identity Number. |
| `SeeingRatingId` | `nvarchar(10)` | NULL |  | Washington Group Seeing functional difficulty rating (references lookup.SeeingRatingType: 01 None to 06 Cannot determine). |
| `SelfCareRatingId` | `nvarchar(10)` | NULL |  | Washington Group Self-Care functional difficulty rating (references lookup.SelfCareRatingType: 01 None to 06 Cannot determine). |
| `StatssaAreaCode` | `nvarchar(50)` | NULL |  | Statistics South Africa Spatial Area Code (references lookup.StatssaAreaCodeType). |
| `Title` | `nvarchar(20)` | NULL |  | Honorific title (e.g. Mr, Mrs, Ms, Dr, Prof). |
| `WalkingRatingId` | `nvarchar(10)` | NULL |  | Washington Group Mobility / Walking functional difficulty rating (references lookup.WalkingRatingType: 01 None to 06 Cannot determine). |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_Person_AlternateIdTypeId` | `AlternateIdTypeId` | No |
| `IX_Person_Email` | `Email` | No |
| `IX_Person_EquityCode` | `EquityCode` | No |
| `IX_Person_GenderCode` | `GenderCode` | No |
| `IX_Person_IsActive` | `IsActive` | No |
| `IX_Person_LastName` | `LastName` | No |
| `IX_Person_NationalityCode` | `NationalityCode` | No |
| `IX_Person_PassportNumber` | `PassportNumber` | No |
| `IX_Person_PopiActStatusId` | `PopiActStatusId` | No |
| `IX_Person_ProvinceCode` | `ProvinceCode` | No |
| `IX_Person_RsaIdNumber` | `RsaIdNumber` | No |
| `IX_Person_StatssaAreaCode` | `StatssaAreaCode` | No |

---

### <a id="personcontact"></a> `dbo.PersonContact`

**Description:** Vertically partitioned satellite entity storing residential, postal, and telecommunications contact data for an individual.  
**CLR Model:** `Nsdms.Domain.Entities.PersonContact`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CellNumber` | `nvarchar(30)` | NULL |  | Mobile / cellular phone number. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `Email` | `nvarchar(150)` | NULL |  | Primary email contact address. |
| `FaxNumber` | `nvarchar(30)` | NULL |  | Facsimile contact number. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `PersonId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the parent Person record. |
| `PhoneNumber` | `nvarchar(30)` | NULL |  | Primary telephone contact number. |
| `PhysicalAddress` | `nvarchar(500)` | NULL |  | Physical residential street address line 1. |
| `PhysicalAddressPostalCode` | `nvarchar(20)` | NULL |  | Physical residential address postal code. |
| `PostalAddress` | `nvarchar(500)` | NULL |  | Postal delivery address. |
| `PostalAddressPostalCode` | `nvarchar(20)` | NULL |  | Postal address delivery code. |
| `ProvinceCode` | `nvarchar(15)` | NULL |  | South African province code of primary residence (references lookup.ProvinceType: GP, KZN, WC, EC, FS, MP, NW, NC, LP). |
| `StatssaAreaCode` | `nvarchar(50)` | NULL |  | Statistics South Africa Spatial Area Code (references lookup.StatssaAreaCodeType). |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_PersonContact_Person_PersonId` | `PersonId` | `dbo.Person` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_PersonContact_Email` | `Email` | No |
| `IX_PersonContact_PersonId` | `PersonId` | ✅ Yes |
| `IX_PersonContact_ProvinceCode` | `ProvinceCode` | No |
| `IX_PersonContact_StatssaAreaCode` | `StatssaAreaCode` | No |

---

### <a id="persondemographics"></a> `dbo.PersonDemographics`

**Description:** Vertically partitioned satellite entity storing demographic, language, equity, and statutory POPIA consent metadata.  
**CLR Model:** `Nsdms.Domain.Entities.PersonDemographics`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CitizenStatusCode` | `nvarchar(15)` | NULL |  | Citizen status lookup code (references lookup.CitizenStatusType: SA, PR, D, O, U). |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DisabilityCode` | `nvarchar(15)` | NULL |  | Legacy disability classification lookup code (references lookup.DisabilityType). |
| `EquityCode` | `nvarchar(15)` | NULL |  | BBBEE / SETMIS statutory equity classification code (references lookup.EquityType: BA, BC, BI, WH, OTH). |
| `HomeLanguageCode` | `nvarchar(15)` | NULL |  | Home / native language classification code (references lookup.HomeLanguageType: ENG, AFR, ZUL, XHO, SASL, etc.). |
| `LastSchoolEmisNumber` | `nvarchar(50)` | NULL |  | Department of Basic Education EMIS School Registration Number. |
| `LastSchoolYear` | `nvarchar(10)` | NULL |  | Year in which individual exited / matriculated from last school attended (YYYY format). |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `NationalityCode` | `nvarchar(15)` | NULL |  | Country nationality lookup code (references lookup.NationalityType: SA, SDC, NAM, ZIM, etc.). |
| `PersonId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the parent Person record. |
| `PopiActConsentDate` | `datetime2` | NULL |  | Date when POPIA data processing consent was recorded. |
| `PopiActStatusId` | `nvarchar(10)` | NULL |  | Protection of Personal Information Act Statutory Consent Status (references lookup.PopiActStatusType: 01 Agreed, 02 Declined, 98 Unknown). |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_PersonDemographics_Person_PersonId` | `PersonId` | `dbo.Person` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_PersonDemographics_CitizenStatusCode` | `CitizenStatusCode` | No |
| `IX_PersonDemographics_EquityCode` | `EquityCode` | No |
| `IX_PersonDemographics_NationalityCode` | `NationalityCode` | No |
| `IX_PersonDemographics_PersonId` | `PersonId` | ✅ Yes |
| `IX_PersonDemographics_PopiActStatusId` | `PopiActStatusId` | No |

---

### <a id="persondisabilityrating"></a> `dbo.PersonDisabilityRating`

**Description:** Vertically partitioned satellite entity isolating special personal information under POPIA: Washington Group Functioning disability difficulty ratings, assessment records, and support notes.  
**CLR Model:** `Nsdms.Domain.Entities.PersonDisabilityRating`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AssessedBy` | `nvarchar(150)` | NULL |  | Name or registration number of the assessing medical practitioner / occupational specialist. |
| `AssessedDate` | `datetime2` | NULL |  | Date when the formal disability assessment took place. |
| `CommunicatingRatingId` | `nvarchar(10)` | NULL |  | Washington Group Communication functional difficulty rating (references lookup.CommunicatingRatingType: 01 None to 06 Cannot determine). |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DisabilityCode` | `nvarchar(15)` | NULL |  | Legacy disability classification lookup code (references lookup.DisabilityType: 00 None, 01 Sight, 02 Hearing, etc.). |
| `DisabilitySupportNotes` | `nvarchar(1000)` | NULL |  | Workplace or learning environment special accommodation and support requirements. |
| `HearingRatingId` | `nvarchar(10)` | NULL |  | Washington Group Hearing functional difficulty rating (references lookup.HearingRatingType: 01 None to 06 Cannot determine). |
| `IsDisabilityAssessed` | `bit` | **NOT NULL** |  | Indicates whether a formal medical practitioner or occupational therapist disability assessment was conducted. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `PersonId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the parent Person record. |
| `RememberingRatingId` | `nvarchar(10)` | NULL |  | Washington Group Memory / Cognitive functional difficulty rating (references lookup.RememberingRatingType: 01 None to 06 Cannot determine). |
| `SeeingRatingId` | `nvarchar(10)` | NULL |  | Washington Group Seeing functional difficulty rating (references lookup.SeeingRatingType: 01 None to 06 Cannot determine). |
| `SelfCareRatingId` | `nvarchar(10)` | NULL |  | Washington Group Self-Care functional difficulty rating (references lookup.SelfCareRatingType: 01 None to 06 Cannot determine). |
| `WalkingRatingId` | `nvarchar(10)` | NULL |  | Washington Group Mobility / Walking functional difficulty rating (references lookup.WalkingRatingType: 01 None to 06 Cannot determine). |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_PersonDisabilityRating_Person_PersonId` | `PersonId` | `dbo.Person` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_PersonDisabilityRating_DisabilityCode` | `DisabilityCode` | No |
| `IX_PersonDisabilityRating_PersonId` | `PersonId` | ✅ Yes |

---

### <a id="piplearnerallocation"></a> `dbo.PipLearnerAllocation`

**Description:** Breakdown of awarded learner interventions and allowance budgets in a PIP.  
**CLR Model:** `Nsdms.Domain.Entities.PipLearnerAllocation`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `LearnerCount` | `int` | **NOT NULL** |  | Domain property for LearnerCount. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `ProjectImplementationPlanId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `QualificationTitle` | `nvarchar(250)` | NULL |  | Domain property for QualificationTitle. |
| `SaqaQualificationId` | `int` | NULL |  | Domain property for SaqaQualificationId. |
| `TotalAllowanceBudget` | `decimal(18,2)` | **NOT NULL** |  | Domain property for TotalAllowanceBudget. |
| `TotalTuitionBudget` | `decimal(18,2)` | **NOT NULL** |  | Domain property for TotalTuitionBudget. |
| `UnitCost` | `decimal(18,2)` | **NOT NULL** |  | Domain property for UnitCost. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_PipLearnerAllocation_ProjectImplementationPlan_ProjectImplementationPlanId` | `ProjectImplementationPlanId` | `dbo.ProjectImplementationPlan` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_PipLearnerAllocation_ProjectImplementationPlanId` | `ProjectImplementationPlanId` | No |

---

### <a id="projectimplementationplan"></a> `dbo.ProjectImplementationPlan`

**Description:** Discretionary Grant Project Implementation Plan (PIP) and contracting governance.  
**CLR Model:** `Nsdms.Domain.Entities.ProjectImplementationPlan`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ContractSignOffDate` | `datetime2` | NULL |  | Domain property for ContractSignOffDate. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `FundingWindowId` | `int` | NULL | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `GrantApplicationId` | `int` | NULL | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `GrantMoaId` | `int` | NULL | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `InterventionTypeCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for InterventionTypeCode. |
| `LearnersWithDisabilityCount` | `int` | **NOT NULL** |  | Domain property for LearnersWithDisabilityCount. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `OrganisationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `PlanReferenceNumber` | `nvarchar(50)` | **NOT NULL** |  | Domain property for PlanReferenceNumber. |
| `RecoverableAmount` | `decimal(18,2)` | **NOT NULL** |  | Domain property for RecoverableAmount. |
| `StatusCode` | `nvarchar(50)` | **NOT NULL** |  | Current lifecycle state code in the workflow engine. |
| `TotalAwardedAmount` | `decimal(18,2)` | **NOT NULL** |  | Domain property for TotalAwardedAmount. |
| `TotalLearnersAwarded` | `int` | **NOT NULL** |  | Domain property for TotalLearnersAwarded. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_ProjectImplementationPlan_GrantFundingWindow_FundingWindowId` | `FundingWindowId` | `dbo.GrantFundingWindow` | `Restrict` |
| `FK_ProjectImplementationPlan_GrantApplication_GrantApplicationId` | `GrantApplicationId` | `dbo.GrantApplication` | `Restrict` |
| `FK_ProjectImplementationPlan_GrantMoa_GrantMoaId` | `GrantMoaId` | `dbo.GrantMoa` | `ClientSetNull` |
| `FK_ProjectImplementationPlan_Organisation_OrganisationId` | `OrganisationId` | `dbo.Organisation` | `Restrict` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_ProjectImplementationPlan_OrganisationId` | `OrganisationId` | No |
| `IX_ProjectImplementationPlan_PlanReferenceNumber` | `PlanReferenceNumber` | No |
| `IX_ProjectImplementationPlan_StatusCode` | `StatusCode` | No |

---

### <a id="qualificationscurriculumdevelopment"></a> `dbo.QualificationsCurriculumDevelopment`

**Description:** Qualifications Curriculum Development (QCD) Application for QCTO Occupational Qualifications and Realignment.  
**CLR Model:** `Nsdms.Domain.Entities.QualificationsCurriculumDevelopment`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ApplicationNumber` | `nvarchar(50)` | **NOT NULL** |  | Domain property for ApplicationNumber. |
| `AssessmentQualityPartner` | `nvarchar(max)` | **NOT NULL** |  | Domain property for AssessmentQualityPartner. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DevelopmentQualityPartner` | `nvarchar(max)` | **NOT NULL** |  | Domain property for DevelopmentQualityPartner. |
| `DevelopmentTypeCode` | `nvarchar(50)` | **NOT NULL** |  | Development type: NewDevelopment, ReAlignment, CurriculumReview |
| `IndustrialPolicyActionPlanChecked` | `bit` | **NOT NULL** |  | Domain property for IndustrialPolicyActionPlanChecked. |
| `IndustrialPolicyActionPlanEvidence` | `nvarchar(max)` | NULL |  | Domain property for IndustrialPolicyActionPlanEvidence. |
| `IndustryDemandJustification` | `nvarchar(max)` | NULL |  | Domain property for IndustryDemandJustification. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `NationalDevelopmentPlanChecked` | `bit` | **NOT NULL** |  | Domain property for NationalDevelopmentPlanChecked. |
| `NationalDevelopmentPlanEvidence` | `nvarchar(max)` | NULL |  | Domain property for NationalDevelopmentPlanEvidence. |
| `NewGrowthPlanChecked` | `bit` | **NOT NULL** |  | Domain property for NewGrowthPlanChecked. |
| `NewGrowthPlanEvidence` | `nvarchar(max)` | NULL |  | Domain property for NewGrowthPlanEvidence. |
| `NqfLevel` | `int` | **NOT NULL** |  | Domain property for NqfLevel. |
| `OfoCode` | `nvarchar(max)` | NULL |  | Domain property for OfoCode. |
| `OrganisationId` | `int` | NULL | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `PublicCommentClosingDate` | `datetime2` | NULL |  | Domain property for PublicCommentClosingDate. |
| `PurposeOfQualification` | `nvarchar(max)` | NULL |  | Domain property for PurposeOfQualification. |
| `QualificationTitle` | `nvarchar(250)` | **NOT NULL** |  | Domain property for QualificationTitle. |
| `SaqaQualificationId` | `nvarchar(max)` | NULL |  | Domain property for SaqaQualificationId. |
| `SaqaRegistrationDate` | `datetime2` | NULL |  | Domain property for SaqaRegistrationDate. |
| `SaqaRegistrationNumber` | `nvarchar(100)` | NULL |  | Domain property for SaqaRegistrationNumber. |
| `SaqaSubmissionDate` | `datetime2` | NULL |  | Domain property for SaqaSubmissionDate. |
| `StatusCode` | `nvarchar(50)` | **NOT NULL** |  | Status: Draft, WorkingGroupConvened, PublicCommentOpen, SubmittedToQcto, ApprovedBySaqa, Rejected |
| `StrategicInfrastructureChecked` | `bit` | **NOT NULL** |  | Domain property for StrategicInfrastructureChecked. |
| `TargetLearnerAudience` | `nvarchar(max)` | NULL |  | Domain property for TargetLearnerAudience. |
| `TotalCreditsRequired` | `int` | **NOT NULL** |  | Domain property for TotalCreditsRequired. |
| `WorkingGroupConvenedDate` | `datetime2` | NULL |  | Domain property for WorkingGroupConvenedDate. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_QualificationsCurriculumDevelopment_Organisation_OrganisationId` | `OrganisationId` | `dbo.Organisation` | `SetNull` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_QualificationsCurriculumDevelopment_ApplicationNumber` | `ApplicationNumber` | ✅ Yes |
| `IX_QualificationsCurriculumDevelopment_OfoCode` | `OfoCode` | No |
| `IX_QualificationsCurriculumDevelopment_StatusCode` | `StatusCode` | No |

---

### <a id="reviewcommitteemeeting"></a> `dbo.ReviewCommitteeMeeting`

**Description:** Executive & ETQA Review Committee / MANCO Governance Meeting.  
**CLR Model:** `Nsdms.Domain.Entities.ReviewCommitteeMeeting`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AdditionalInfo` | `nvarchar(2000)` | NULL |  | Domain property for AdditionalInfo. |
| `ChairpersonUserId` | `nvarchar(100)` | NULL |  | Domain property for ChairpersonUserId. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `FromDateTime` | `datetime2` | **NOT NULL** |  | Domain property for FromDateTime. |
| `MeetingNumber` | `nvarchar(50)` | **NOT NULL** |  | Domain property for MeetingNumber. |
| `MeetingTypeCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for MeetingTypeCode. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `QuorumReached` | `bit` | **NOT NULL** |  | Domain property for QuorumReached. |
| `StatusCode` | `nvarchar(50)` | **NOT NULL** |  | Current lifecycle state code in the workflow engine. |
| `Title` | `nvarchar(300)` | **NOT NULL** |  | Domain property for Title. |
| `ToDateTime` | `datetime2` | **NOT NULL** |  | Domain property for ToDateTime. |
| `Venue` | `nvarchar(250)` | **NOT NULL** |  | Domain property for Venue. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_ReviewCommitteeMeeting_FromDateTime` | `FromDateTime` | No |
| `IX_ReviewCommitteeMeeting_MeetingTypeCode` | `MeetingTypeCode` | No |
| `IX_ReviewCommitteeMeeting_StatusCode` | `StatusCode` | No |

---

### <a id="reviewcommitteemeetingagenda"></a> `dbo.ReviewCommitteeMeetingAgenda`

**Description:** Specific agenda item submitted for committee adjudication or voting resolution.  
**CLR Model:** `Nsdms.Domain.Entities.ReviewCommitteeMeetingAgenda`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AbstainCount` | `int` | **NOT NULL** |  | Domain property for AbstainCount. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DecisionCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for DecisionCode. |
| `DecisionNotes` | `nvarchar(2000)` | NULL |  | Domain property for DecisionNotes. |
| `Description` | `nvarchar(2000)` | NULL |  | Detailed description and contextual notes. |
| `ItemNumber` | `int` | **NOT NULL** |  | Domain property for ItemNumber. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `ReviewCommitteeMeetingId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `TargetEntityId` | `int` | NULL |  | Domain property for TargetEntityId. |
| `TargetEntityName` | `nvarchar(100)` | NULL |  | Domain property for TargetEntityName. |
| `Title` | `nvarchar(300)` | **NOT NULL** |  | Domain property for Title. |
| `VotedNoCount` | `int` | **NOT NULL** |  | Domain property for VotedNoCount. |
| `VotedYesCount` | `int` | **NOT NULL** |  | Domain property for VotedYesCount. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_ReviewCommitteeMeetingAgenda_ReviewCommitteeMeeting_ReviewCommitteeMeetingId` | `ReviewCommitteeMeetingId` | `dbo.ReviewCommitteeMeeting` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_ReviewCommitteeMeetingAgenda_DecisionCode` | `DecisionCode` | No |
| `IX_ReviewCommitteeMeetingAgenda_ReviewCommitteeMeetingId` | `ReviewCommitteeMeetingId` | No |

---

### <a id="reviewcommitteemeetingmember"></a> `dbo.ReviewCommitteeMeetingMember`

**Description:** Committee attendee / voting member.  
**CLR Model:** `Nsdms.Domain.Entities.ReviewCommitteeMeetingMember`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `Attended` | `bit` | **NOT NULL** |  | Domain property for Attended. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `PersonId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `ReviewCommitteeMeetingId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `RoleInMeeting` | `nvarchar(50)` | **NOT NULL** |  | Domain property for RoleInMeeting. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_ReviewCommitteeMeetingMember_Person_PersonId` | `PersonId` | `dbo.Person` | `Restrict` |
| `FK_ReviewCommitteeMeetingMember_ReviewCommitteeMeeting_ReviewCommitteeMeetingId` | `ReviewCommitteeMeetingId` | `dbo.ReviewCommitteeMeeting` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_ReviewCommitteeMeetingMember_PersonId` | `PersonId` | No |
| `IX_ReviewCommitteeMeetingMember_ReviewCommitteeMeetingId` | `ReviewCommitteeMeetingId` | No |

---

### <a id="sarslevyreconaudit"></a> `dbo.SarsLevyReconAudit`

**Description:** Advanced SARS Monthly SDL Levy Reconciliation Audit and Discrepancy Tracking.  
**CLR Model:** `Nsdms.Domain.Entities.SarsLevyReconAudit`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ActualSarsChamberCode` | `nvarchar(max)` | NULL |  | Domain property for ActualSarsChamberCode. |
| `ActualSarsSicCode` | `nvarchar(max)` | NULL |  | Domain property for ActualSarsSicCode. |
| `AuditNotes` | `nvarchar(max)` | NULL |  | Domain property for AuditNotes. |
| `AuditStatusCode` | `nvarchar(50)` | **NOT NULL** |  | Status: Reconciled, DiscrepancyFlagged, ClawbackIssued, Resolved |
| `AuditorUserId` | `nvarchar(max)` | **NOT NULL** |  | Domain property for AuditorUserId. |
| `ClawbackActionRequired` | `bit` | **NOT NULL** |  | Domain property for ClawbackActionRequired. |
| `ClawbackAmount` | `decimal(18,2)` | **NOT NULL** |  | Domain property for ClawbackAmount. |
| `ClawbackIssuedDate` | `datetime2` | NULL |  | Domain property for ClawbackIssuedDate. |
| `ClawbackSettledDate` | `datetime2` | NULL |  | Domain property for ClawbackSettledDate. |
| `CounterpartSetaCode` | `nvarchar(max)` | NULL |  | Domain property for CounterpartSetaCode. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DiscrepancyReasonCode` | `nvarchar(50)` | **NOT NULL** |  | Discrepancy Category: ExactMatch, Underpayment, Overpayment, ChamberMisallocation, SicCodeMismatch, OutOfScopeSeta, SchemeYearMismatch |
| `ExpectedChamberCode` | `nvarchar(max)` | NULL |  | Domain property for ExpectedChamberCode. |
| `ExpectedSicCode` | `nvarchar(max)` | NULL |  | Domain property for ExpectedSicCode. |
| `FinancialYear` | `nvarchar(10)` | **NOT NULL** |  | Domain property for FinancialYear. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `OrganisationId` | `int` | NULL | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `ReconciliationDate` | `datetime2` | **NOT NULL** |  | Domain property for ReconciliationDate. |
| `SchemeYear` | `nvarchar(max)` | **NOT NULL** |  | Domain property for SchemeYear. |
| `SdlNumber` | `nvarchar(50)` | **NOT NULL** |  | SARS Skills Development Levy registration number (e.g. L123456789). |
| `TotalCalculatedLeviesExpected` | `decimal(18,2)` | **NOT NULL** |  | Domain property for TotalCalculatedLeviesExpected. |
| `TotalSarsLeviesReceived` | `decimal(18,2)` | **NOT NULL** |  | Domain property for TotalSarsLeviesReceived. |
| `VarianceAmount` | `decimal(18,2)` | **NOT NULL** |  | Domain property for VarianceAmount. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_SarsLevyReconAudit_Organisation_OrganisationId` | `OrganisationId` | `dbo.Organisation` | `SetNull` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_SarsLevyReconAudit_AuditStatusCode` | `AuditStatusCode` | No |
| `IX_SarsLevyReconAudit_DiscrepancyReasonCode` | `DiscrepancyReasonCode` | No |
| `IX_SarsLevyReconAudit_FinancialYear` | `FinancialYear` | No |
| `IX_SarsLevyReconAudit_SdlNumber` | `SdlNumber` | No |

---

### <a id="sarslevystaging"></a> `dbo.SarsLevyStaging`

**Description:** Staging entity for high-speed bulk ingestion of raw monthly SARS Skills Development Levy transactions. Serves as the landing table for streaming SqlBulkCopy before promotion into the production financial ledger.  
**CLR Model:** `Nsdms.Domain.Entities.SarsLevyStaging`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `bigint` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AdminLevyAmount` | `decimal(18,2)` | **NOT NULL** |  | 10.5% merSETA administration levy portion in ZAR. |
| `BatchIdentifier` | `nvarchar(100)` | **NOT NULL** |  | Unique ingestion batch tracking identifier (e.g. SARS-STAGING-20260903-ABC123). |
| `ChamberCode` | `nvarchar(20)` | NULL |  | Resolved merSETA Chamber Code (AUTO, METAL, MOTOR, NEW_TYRE, PLASTICS, OTHER). |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DiscretionaryLevyAmount` | `decimal(18,2)` | **NOT NULL** |  | 49.5% Discretionary Grant portion in ZAR. |
| `HasSicCodeMismatch` | `bit` | **NOT NULL** |  | Indicates whether the declared SARS SIC code differs from the employer's master verified record. |
| `InterestAmount` | `decimal(18,2)` | **NOT NULL** |  | Statutory interest on late payments in ZAR. |
| `IsOutOfScopeSeta` | `bit` | **NOT NULL** |  | Indicates whether this transaction belongs to a non-merSETA industry requiring Inter-SETA transfer. |
| `LineNumber` | `int` | **NOT NULL** |  | Sequential line number in the source SARS text file (1-indexed). |
| `MandatoryLevyAmount` | `decimal(18,2)` | **NOT NULL** |  | 20% Mandatory Grant portion in ZAR. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `PenaltyAmount` | `decimal(18,2)` | **NOT NULL** |  | Statutory penalty fee on late payments in ZAR. |
| `PromotedLevyFileId` | `int` | NULL |  | Foreign key referencing the promoted LevyFile once batch promotion completes. |
| `QctoLevyAmount` | `decimal(18,2)` | **NOT NULL** |  | 0.5% Quality Council for Trades and Occupations (QCTO) levy portion in ZAR. |
| `RawRecord` | `nvarchar(1000)` | NULL |  | Raw unparsed line content retained for forensic audit and diagnostic inspection. |
| `SchemeYear` | `nvarchar(10)` | **NOT NULL** |  | Levy scheme year or accounting period (e.g. 2026). |
| `SdlNumber` | `nvarchar(20)` | **NOT NULL** |  | Employer SARS Skills Development Levy registration reference (e.g. L123456789). |
| `SetaCode` | `nvarchar(10)` | **NOT NULL** |  | Statutory SETA code (SETA 17 for merSETA, or other SETA code for out-of-scope records). |
| `SicCode` | `nvarchar(20)` | NULL |  | Declared 5-digit Standard Industrial Classification (SIC) code reported in the SARS schedule. |
| `StagingStatus` | `nvarchar(25)` | **NOT NULL** |  | Staging lifecycle status: Pending, Validated, Promoted, Rejected. |
| `TotalLevyAmount` | `decimal(18,2)` | **NOT NULL** |  | Total gross levy amount in ZAR. |
| `ValidationMessage` | `nvarchar(500)` | NULL |  | Optional diagnostic message or reason for rejection. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_SarsLevyStaging_BatchIdentifier` | `BatchIdentifier` | No |
| `IX_SarsLevyStaging_IsOutOfScopeSeta` | `IsOutOfScopeSeta` | No |
| `IX_SarsLevyStaging_SdlNumber` | `SdlNumber` | No |
| `IX_SarsLevyStaging_SicCode` | `SicCode` | No |
| `IX_SarsLevyStaging_StagingStatus` | `StagingStatus` | No |

---

### <a id="sarsschemeyearcalculation"></a> `dbo.SarsSchemeYearCalculation`

**Description:** System entity for SarsSchemeYearCalculation data governance.  
**CLR Model:** `Nsdms.Domain.Entities.SarsSchemeYearCalculation`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AdminPercentage` | `decimal(18,2)` | **NOT NULL** |  | Domain property for AdminPercentage. |
| `AllowInvoicesDiscretionary` | `bit` | **NOT NULL** |  | Domain property for AllowInvoicesDiscretionary. |
| `AllowInvoicesMandatory` | `bit` | **NOT NULL** |  | Domain property for AllowInvoicesMandatory. |
| `AllowReturnsDiscretionary` | `bit` | **NOT NULL** |  | Domain property for AllowReturnsDiscretionary. |
| `AllowReturnsMandatory` | `bit` | **NOT NULL** |  | Domain property for AllowReturnsMandatory. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DiscretionaryPercentage` | `decimal(18,2)` | **NOT NULL** |  | Domain property for DiscretionaryPercentage. |
| `MandatoryPercentage` | `decimal(18,2)` | **NOT NULL** |  | Domain property for MandatoryPercentage. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `Notes` | `nvarchar(max)` | NULL |  | Domain property for Notes. |
| `QctoPercentage` | `decimal(18,2)` | **NOT NULL** |  | Domain property for QctoPercentage. |
| `SchemeYear` | `nvarchar(10)` | **NOT NULL** |  | Domain property for SchemeYear. |
| `StatusCode` | `nvarchar(50)` | **NOT NULL** |  | Current lifecycle state code in the workflow engine. |
| `TotalPercentage` | `decimal(18,2)` | **NOT NULL** |  | Domain property for TotalPercentage. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_SarsSchemeYearCalculation_SchemeYear` | `SchemeYear` | No |
| `IX_SarsSchemeYearCalculation_StatusCode` | `StatusCode` | No |

---

### <a id="sdfappointmenthistory"></a> `dbo.SdfAppointmentHistory`

**Description:** System entity for SdfAppointmentHistory data governance.  
**CLR Model:** `Nsdms.Domain.Entities.SdfAppointmentHistory`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ChangeReason` | `nvarchar(max)` | NULL |  | Domain property for ChangeReason. |
| `ChangedAt` | `datetime2` | **NOT NULL** |  | Domain property for ChangedAt. |
| `ChangedByUserId` | `nvarchar(100)` | **NOT NULL** |  | Domain property for ChangedByUserId. |
| `NewStatusCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for NewStatusCode. |
| `PreviousStatusCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for PreviousStatusCode. |
| `SdfCompanyId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_SdfAppointmentHistory_SdfCompany_SdfCompanyId` | `SdfCompanyId` | `dbo.SdfCompany` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_SdfAppointmentHistory_SdfCompanyId` | `SdfCompanyId` | No |

---

### <a id="sdfcompany"></a> `dbo.SdfCompany`

**Description:** System entity for SdfCompany data governance.  
**CLR Model:** `Nsdms.Domain.Entities.SdfCompany`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AllowDgApplication` | `bit` | **NOT NULL** |  | Domain property for AllowDgApplication. |
| `AllowTrancheClaims` | `bit` | **NOT NULL** |  | Domain property for AllowTrancheClaims. |
| `AllowWspSubmission` | `bit` | **NOT NULL** |  | Domain property for AllowWspSubmission. |
| `AppointmentEndDate` | `datetime2` | NULL |  | Domain property for AppointmentEndDate. |
| `AppointmentLetterDocumentPath` | `nvarchar(250)` | NULL |  | Domain property for AppointmentLetterDocumentPath. |
| `AppointmentStartDate` | `datetime2` | **NOT NULL** |  | Domain property for AppointmentStartDate. |
| `ApprovalComments` | `nvarchar(max)` | NULL |  | Domain property for ApprovalComments. |
| `ApprovalDate` | `datetime2` | NULL |  | Domain property for ApprovalDate. |
| `ApprovedByUserId` | `nvarchar(max)` | NULL |  | Domain property for ApprovedByUserId. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | **NOT NULL** |  | User identifier or system process that created the record. |
| `IsActive` | `bit` | **NOT NULL** |  | Indicates whether the record is active and operational. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `OrganisationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `PersonId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `SdfStatusCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for SdfStatusCode. |
| `SdfTypeCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for SdfTypeCode. |
| `SignedAcceptanceDeclarationReceived` | `bit` | **NOT NULL** |  | Domain property for SignedAcceptanceDeclarationReceived. |
| `SignedAppointmentLetterReceived` | `bit` | **NOT NULL** |  | Domain property for SignedAppointmentLetterReceived. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_SdfCompany_Organisation_OrganisationId` | `OrganisationId` | `dbo.Organisation` | `Cascade` |
| `FK_SdfCompany_Person_PersonId` | `PersonId` | `dbo.Person` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_SdfCompany_OrganisationId` | `OrganisationId` | No |
| `IX_SdfCompany_PersonId` | `PersonId` | No |
| `IX_SdfCompany_SdfStatusCode` | `SdfStatusCode` | No |

---

### <a id="sdpextensionofscope"></a> `dbo.SdpExtensionOfScope`

**Description:** System entity for SdpExtensionOfScope data governance.  
**CLR Model:** `Nsdms.Domain.Entities.SdpExtensionOfScope`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AdditionalQualificationTitle` | `nvarchar(250)` | **NOT NULL** |  | Domain property for AdditionalQualificationTitle. |
| `ApplicationNumber` | `nvarchar(50)` | **NOT NULL** |  | Domain property for ApplicationNumber. |
| `ApprovalDate` | `datetime2` | NULL |  | Domain property for ApprovalDate. |
| `ApprovedByUserId` | `nvarchar(max)` | NULL |  | Domain property for ApprovedByUserId. |
| `CommitteeDecisionReference` | `nvarchar(max)` | NULL |  | Domain property for CommitteeDecisionReference. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | **NOT NULL** |  | User identifier or system process that created the record. |
| `Credits` | `int` | **NOT NULL** |  | Domain property for Credits. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `NqfLevel` | `int` | **NOT NULL** |  | Domain property for NqfLevel. |
| `ProgrammeTypeCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for ProgrammeTypeCode. |
| `SaqaQualificationId` | `nvarchar(50)` | NULL |  | Domain property for SaqaQualificationId. |
| `SiteEvaluatorUserId` | `nvarchar(max)` | NULL |  | Domain property for SiteEvaluatorUserId. |
| `SiteInspectionDate` | `datetime2` | NULL |  | Domain property for SiteInspectionDate. |
| `SiteInspectionPassed` | `bit` | **NOT NULL** |  | Domain property for SiteInspectionPassed. |
| `StatusCode` | `nvarchar(50)` | **NOT NULL** |  | Current lifecycle state code in the workflow engine. |
| `TrainingProviderId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_SdpExtensionOfScope_TrainingProvider_TrainingProviderId` | `TrainingProviderId` | `dbo.TrainingProvider` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_SdpExtensionOfScope_ApplicationNumber` | `ApplicationNumber` | ✅ Yes |
| `IX_SdpExtensionOfScope_StatusCode` | `StatusCode` | No |
| `IX_SdpExtensionOfScope_TrainingProviderId` | `TrainingProviderId` | No |

---

### <a id="sdpreaccreditationapplication"></a> `dbo.SdpReAccreditationApplication`

**Description:** System entity for SdpReAccreditationApplication data governance.  
**CLR Model:** `Nsdms.Domain.Entities.SdpReAccreditationApplication`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ApplicationNumber` | `nvarchar(50)` | **NOT NULL** |  | Domain property for ApplicationNumber. |
| `CouncilDecisionNumber` | `nvarchar(max)` | NULL |  | Domain property for CouncilDecisionNumber. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | **NOT NULL** |  | User identifier or system process that created the record. |
| `CurrentAccreditationExpiryDate` | `datetime2` | **NOT NULL** |  | Domain property for CurrentAccreditationExpiryDate. |
| `FacilitatorAssessorRatiosCompliant` | `bit` | **NOT NULL** |  | Domain property for FacilitatorAssessorRatiosCompliant. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `OshSafetyCertificatesValid` | `bit` | **NOT NULL** |  | Domain property for OshSafetyCertificatesValid. |
| `ProposedAccreditationExpiryDate` | `datetime2` | **NOT NULL** |  | Domain property for ProposedAccreditationExpiryDate. |
| `QmsComplianceAudited` | `bit` | **NOT NULL** |  | Domain property for QmsComplianceAudited. |
| `ReAccreditationDecisionDate` | `datetime2` | NULL |  | Domain property for ReAccreditationDecisionDate. |
| `StatusCode` | `nvarchar(50)` | **NOT NULL** |  | Current lifecycle state code in the workflow engine. |
| `TrainingProviderId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_SdpReAccreditationApplication_TrainingProvider_TrainingProviderId` | `TrainingProviderId` | `dbo.TrainingProvider` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_SdpReAccreditationApplication_StatusCode` | `StatusCode` | No |
| `IX_SdpReAccreditationApplication_TrainingProviderId` | `TrainingProviderId` | No |

---

### <a id="sdpscopeextensionapplication"></a> `dbo.SdpScopeExtensionApplication`

**Description:** Skills Development Provider (SDP) Scope Extension Application.  
**CLR Model:** `Nsdms.Domain.Entities.SdpScopeExtensionApplication`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ApplicationNumber` | `nvarchar(50)` | **NOT NULL** |  | Domain property for ApplicationNumber. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `QualificationTitle` | `nvarchar(250)` | NULL |  | Domain property for QualificationTitle. |
| `RecommendationNotes` | `nvarchar(2000)` | NULL |  | Domain property for RecommendationNotes. |
| `ReviewCommitteeMeetingAgendaId` | `int` | NULL | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `SaqaQualificationId` | `int` | NULL |  | Domain property for SaqaQualificationId. |
| `StatusCode` | `nvarchar(50)` | **NOT NULL** |  | Current lifecycle state code in the workflow engine. |
| `TrainingProviderId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_SdpScopeExtensionApplication_ReviewCommitteeMeetingAgenda_ReviewCommitteeMeetingAgendaId` | `ReviewCommitteeMeetingAgendaId` | `dbo.ReviewCommitteeMeetingAgenda` | `SetNull` |
| `FK_SdpScopeExtensionApplication_TrainingProvider_TrainingProviderId` | `TrainingProviderId` | `dbo.TrainingProvider` | `Restrict` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_SdpScopeExtensionApplication_StatusCode` | `StatusCode` | No |
| `IX_SdpScopeExtensionApplication_TrainingProviderId` | `TrainingProviderId` | No |

---

### <a id="skillsregistration"></a> `dbo.SkillsRegistration`

**Description:** Skills Programme / Part-Qualification Curriculum Registration, capturing all statutory fields required for SETMIS File 304 (Non NQF Intervention) reporting.  
**CLR Model:** `Nsdms.Domain.Entities.SkillsRegistration`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `Credits` | `int` | **NOT NULL** |  | Domain property for Credits. |
| `EtqaId` | `nvarchar(10)` | **NOT NULL** |  | Submitting ETQA ID (references lookup.SetaType, default 17 for merSETA). |
| `LearningProgrammeTypeId` | `nvarchar(10)` | **NOT NULL** |  | Statutory Learning Programme Type code (references lookup.LearningProgrammeType, default 03 Skills Programme). |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `NonNqfIntervCode` | `nvarchar(50)` | **NOT NULL** |  | Statutory Non-NQF Intervention Code (SETMIS File 304). |
| `NonNqfIntervName` | `nvarchar(200)` | **NOT NULL** |  | Statutory Non-NQF Intervention descriptive title. |
| `NonNqfIntervStatusId` | `nvarchar(10)` | **NOT NULL** |  | Statutory Registration Status (references lookup.NonNqfInterventionStatusType: 01 Registered, 02 Approved, 03 Concluded). |
| `NqfLevel` | `int` | **NOT NULL** |  | Domain property for NqfLevel. |
| `QualificationsCurriculumDevelopmentId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `RegistrationEndDate` | `datetime2` | NULL |  | Registration validity expiration date. |
| `RegistrationStartDate` | `datetime2` | **NOT NULL** |  | Registration validity start date (SETMIS File 304). |
| `SubfieldId` | `nvarchar(10)` | **NOT NULL** |  | SAQA NQF Subfield ID (references lookup.SubfieldType). |
| `UnitStandardsIncludedJson` | `nvarchar(max)` | **NOT NULL** |  | Domain property for UnitStandardsIncludedJson. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_SkillsRegistration_QualificationsCurriculumDevelopment_QualificationsCurriculumDevelopmentId` | `QualificationsCurriculumDevelopmentId` | `dbo.QualificationsCurriculumDevelopment` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_SkillsRegistration_NonNqfIntervCode` | `NonNqfIntervCode` | No |
| `IX_SkillsRegistration_QualificationsCurriculumDevelopmentId` | `QualificationsCurriculumDevelopmentId` | No |

---

### <a id="statementofresults"></a> `dbo.StatementOfResults`

**Description:** Formal merSETA Statement of Results (SOR) document record with cryptographic tamper-proof hash.  
**CLR Model:** `Nsdms.Domain.Entities.StatementOfResults`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CompanyLearnerId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DateIssued` | `datetime2` | **NOT NULL** |  | Domain property for DateIssued. |
| `IssuedByUserId` | `nvarchar(100)` | **NOT NULL** |  | Domain property for IssuedByUserId. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `PersonId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `QrVerificationUrl` | `nvarchar(max)` | NULL |  | Domain property for QrVerificationUrl. |
| `SorSerialNumber` | `nvarchar(100)` | **NOT NULL** |  | Domain property for SorSerialNumber. |
| `SummativeAssessmentReportId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `TamperProofHashSha256` | `nvarchar(100)` | **NOT NULL** |  | SHA-256 integrity hash for instant online verification. |
| `TotalCreditsCertified` | `int` | **NOT NULL** |  | Domain property for TotalCreditsCertified. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_StatementOfResults_CompanyLearner_CompanyLearnerId` | `CompanyLearnerId` | `dbo.CompanyLearner` | `Restrict` |
| `FK_StatementOfResults_Person_PersonId` | `PersonId` | `dbo.Person` | `Restrict` |
| `FK_StatementOfResults_SummativeAssessmentReport_SummativeAssessmentReportId` | `SummativeAssessmentReportId` | `dbo.SummativeAssessmentReport` | `Restrict` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_StatementOfResults_CompanyLearnerId` | `CompanyLearnerId` | No |
| `IX_StatementOfResults_SorSerialNumber` | `SorSerialNumber` | ✅ Yes |
| `IX_StatementOfResults_SummativeAssessmentReportId` | `SummativeAssessmentReportId` | No |
| `IX_StatementOfResults_TamperProofHashSha256` | `TamperProofHashSha256` | No |

---

### <a id="statutorybatchfile"></a> `dbo.StatutoryBatchFile`

**Description:** Individual fixed-width data file generated within a statutory batch.  
**CLR Model:** `Nsdms.Domain.Entities.StatutoryBatchFile`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ChecksumSha256` | `nvarchar(128)` | NULL |  | SHA-256 digital security seal of this individual file. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `FileCode` | `nvarchar(10)` | **NOT NULL** |  | Statutory file code (e.g. "100", "200", "304", "400", "401", "500", "501", "502", "503", "505", "506" for SETMIS, or "21", "24", "25", "26", "27", "28", "29", "30" for NLRD). |
| `FileContent` | `nvarchar(max)` | NULL |  | Extracted fixed-width text content or storage path reference. |
| `FileName` | `nvarchar(255)` | **NOT NULL** |  | Generated file name (e.g. MERS_0006_100_v001_20260902.dat or MERS21260902.dat). |
| `FileSizeBytes` | `bigint` | **NOT NULL** |  | File size in bytes. |
| `FileTitle` | `nvarchar(150)` | **NOT NULL** |  | Statutory file title / description (e.g. "Provider File 100", "Learner Enrolment File 500"). |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `RecordCount` | `int` | **NOT NULL** |  | Number of lines / records extracted in this file. |
| `RecordLength` | `int` | **NOT NULL** |  | Fixed-width record length in characters (per specification). |
| `StatusCode` | `nvarchar(50)` | **NOT NULL** |  | File status ("Extracted", "Empty", "Failed"). |
| `StatutorySubmissionBatchId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the parent StatutorySubmissionBatch. |
| `ValidationErrorsCount` | `int` | **NOT NULL** |  | Number of validation discrepancies recorded for this file. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_StatutoryBatchFile_StatutorySubmissionBatch_StatutorySubmissionBatchId` | `StatutorySubmissionBatchId` | `dbo.StatutorySubmissionBatch` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_StatutoryBatchFile_FileCode` | `FileCode` | No |
| `IX_StatutoryBatchFile_StatusCode` | `StatusCode` | No |
| `IX_StatutoryBatchFile_StatutorySubmissionBatchId` | `StatutorySubmissionBatchId` | No |

---

### <a id="statutorysubmissionbatch"></a> `dbo.StatutorySubmissionBatch`

**Description:** Represents a statutory submission batch run for DHET SETMIS or SAQA NLRD.  
**CLR Model:** `Nsdms.Domain.Entities.StatutorySubmissionBatch`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ArchiveFileName` | `nvarchar(255)` | NULL |  | Name of the generated zip archive package (e.g. SETMIS_MERS_0006_20260902.zip). |
| `ArchiveStorageUri` | `nvarchar(500)` | NULL |  | File storage URI or relative path to the archived package. |
| `BatchNumber` | `nvarchar(100)` | **NOT NULL** |  | Unique business reference for the statutory batch (e.g. SETMIS-2026-Q1-001). |
| `BatchType` | `nvarchar(20)` | **NOT NULL** |  | Type of statutory submission ("SETMIS" or "NLRD"). |
| `Comments` | `nvarchar(max)` | NULL |  | Data steward notes, audit remarks, or DHET transmission acknowledgements. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DigitalSecuritySeal` | `nvarchar(128)` | NULL |  | SHA-256 digital security seal / checksum of the combined extract package. |
| `ExtractionDate` | `datetime2` | **NOT NULL** |  | Timestamp when data extraction was initiated. |
| `FatalErrorsCount` | `int` | **NOT NULL** |  | Number of fatal blocking validation errors detected before extraction. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `StatusCode` | `nvarchar(50)` | **NOT NULL** |  | Batch status code ("Draft", "Validated", "Extracted", "Submitted", "Accepted", "Rejected"). |
| `SubmissionQuarter` | `int` | NULL |  | Quarterly submission cycle (1, 2, 3, 4, or null for annual/ad-hoc). |
| `SubmissionYear` | `int` | **NOT NULL** |  | Scheme / Financial submission year (e.g. 2026). |
| `TotalRecords` | `int` | **NOT NULL** |  | Total records across all generated files in this batch. |
| `WarningsCount` | `int` | **NOT NULL** |  | Number of non-blocking warning anomalies detected. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_StatutorySubmissionBatch_BatchNumber` | `BatchNumber` | ✅ Yes |
| `IX_StatutorySubmissionBatch_BatchType` | `BatchType` | No |
| `IX_StatutorySubmissionBatch_ExtractionDate` | `ExtractionDate` | No |
| `IX_StatutorySubmissionBatch_StatusCode` | `StatusCode` | No |
| `IX_StatutorySubmissionBatch_SubmissionYear` | `SubmissionYear` | No |

---

### <a id="strategicpriority"></a> `dbo.StrategicPriority`

**Description:** Statutory Strategic Priorities, Key Focus Areas, and Sector Skills Plan (SSP) Themes mapped to National Skills Development Plan (NSDP III) Outcomes and Strategic Infrastructure Projects (SIPs).  
**CLR Model:** `Nsdms.Domain.Entities.StrategicPriority`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `Code` | `nvarchar(50)` | **NOT NULL** |  | Unique strategic priority identification code (e.g. SP-GREEN-01, SP-4IR-02, SP-ARTISAN-03). |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `Description` | `nvarchar(1000)` | NULL |  | Detailed description and statutory objective of this focus area. |
| `IsActive` | `bit` | **NOT NULL** |  | Indicates whether this strategic theme is active for allocation in new funding windows. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `Name` | `nvarchar(200)` | **NOT NULL** |  | Display title / name of the record. |
| `NsdpOutcomeCode` | `nvarchar(50)` | **NOT NULL** |  | National Skills Development Plan (NSDP III) Outcome reference (e.g. Outcome 1, Outcome 2, Outcome 5). |
| `NsdpOutcomeDescription` | `nvarchar(500)` | **NOT NULL** |  | Full description of the NSDP Outcome goal. |
| `SipCategory` | `nvarchar(150)` | NULL |  | Relevant Strategic Infrastructure Project (SIP) category (e.g. SIP 8: Green Energy, SIP 2: Freight). |
| `TargetSector` | `nvarchar(100)` | NULL |  | Domain property for TargetSector. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_StrategicPriority_Code` | `Code` | ✅ Yes |
| `IX_StrategicPriority_IsActive` | `IsActive` | No |
| `IX_StrategicPriority_NsdpOutcomeCode` | `NsdpOutcomeCode` | No |

---

### <a id="summativeassessmentreport"></a> `dbo.SummativeAssessmentReport`

**Description:** Comprehensive Summative Assessment Report capturing unit standard credits, EISA exams, and moderation outcomes.  
**CLR Model:** `Nsdms.Domain.Entities.SummativeAssessmentReport`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AssessmentDate` | `datetime2` | **NOT NULL** |  | Domain property for AssessmentDate. |
| `AssessorPersonId` | `int` | NULL | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `AssessorRegistrationNumber` | `nvarchar(max)` | NULL |  | Domain property for AssessorRegistrationNumber. |
| `CompanyLearnerId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `ExternalModeratorApprovalDate` | `datetime2` | NULL |  | Domain property for ExternalModeratorApprovalDate. |
| `ExternalModeratorComments` | `nvarchar(max)` | NULL |  | Domain property for ExternalModeratorComments. |
| `ExternalModeratorUserId` | `nvarchar(max)` | NULL |  | Domain property for ExternalModeratorUserId. |
| `InternalModeratorPersonId` | `int` | NULL | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `InternalModeratorRegistrationNumber` | `nvarchar(max)` | NULL |  | Domain property for InternalModeratorRegistrationNumber. |
| `InterventionTypeCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for InterventionTypeCode. |
| `ModerationDate` | `datetime2` | NULL |  | Domain property for ModerationDate. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `NqfLevel` | `int` | **NOT NULL** |  | Domain property for NqfLevel. |
| `OrganisationId` | `int` | NULL | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `PersonId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `QualificationTitle` | `nvarchar(250)` | **NOT NULL** |  | Domain property for QualificationTitle. |
| `ReportNumber` | `nvarchar(50)` | **NOT NULL** |  | Domain property for ReportNumber. |
| `SaqaQualificationId` | `nvarchar(max)` | NULL |  | Domain property for SaqaQualificationId. |
| `StatusCode` | `nvarchar(50)` | **NOT NULL** |  | Workflow status: Draft, Assessed, InternalModerated, EtqaModerated, CreditsApproved, SorIssued, Rejected |
| `TotalCreditsEarned` | `int` | **NOT NULL** |  | Domain property for TotalCreditsEarned. |
| `TotalCreditsRequired` | `int` | **NOT NULL** |  | Domain property for TotalCreditsRequired. |
| `TrainingProviderId` | `int` | NULL | 🔗 **FK** | Foreign key relational reference to parent entity. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_SummativeAssessmentReport_Person_AssessorPersonId` | `AssessorPersonId` | `dbo.Person` | `SetNull` |
| `FK_SummativeAssessmentReport_CompanyLearner_CompanyLearnerId` | `CompanyLearnerId` | `dbo.CompanyLearner` | `Restrict` |
| `FK_SummativeAssessmentReport_Person_InternalModeratorPersonId` | `InternalModeratorPersonId` | `dbo.Person` | `SetNull` |
| `FK_SummativeAssessmentReport_Organisation_OrganisationId` | `OrganisationId` | `dbo.Organisation` | `SetNull` |
| `FK_SummativeAssessmentReport_Person_PersonId` | `PersonId` | `dbo.Person` | `Restrict` |
| `FK_SummativeAssessmentReport_TrainingProvider_TrainingProviderId` | `TrainingProviderId` | `dbo.TrainingProvider` | `SetNull` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_SummativeAssessmentReport_CompanyLearnerId` | `CompanyLearnerId` | No |
| `IX_SummativeAssessmentReport_PersonId` | `PersonId` | No |
| `IX_SummativeAssessmentReport_ReportNumber` | `ReportNumber` | ✅ Yes |
| `IX_SummativeAssessmentReport_StatusCode` | `StatusCode` | No |

---

### <a id="summativeassessmentunitstandard"></a> `dbo.SummativeAssessmentUnitStandard`

**Description:** Unit Standard credit assessment and moderation outcome line item.  
**CLR Model:** `Nsdms.Domain.Entities.SummativeAssessmentUnitStandard`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AssessmentDate` | `datetime2` | **NOT NULL** |  | Domain property for AssessmentDate. |
| `AssessorComments` | `nvarchar(max)` | NULL |  | Domain property for AssessorComments. |
| `CompetencyStatusCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for CompetencyStatusCode. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `Credits` | `int` | **NOT NULL** |  | Domain property for Credits. |
| `IsModerated` | `bit` | **NOT NULL** |  | Domain property for IsModerated. |
| `ModerationOutcome` | `nvarchar(50)` | **NOT NULL** |  | Domain property for ModerationOutcome. |
| `ModeratorComments` | `nvarchar(max)` | NULL |  | Domain property for ModeratorComments. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `NqfLevel` | `int` | **NOT NULL** |  | Domain property for NqfLevel. |
| `SummativeAssessmentReportId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `UnitStandardCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for UnitStandardCode. |
| `UnitStandardTitle` | `nvarchar(250)` | **NOT NULL** |  | Domain property for UnitStandardTitle. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_SummativeAssessmentUnitStandard_SummativeAssessmentReport_SummativeAssessmentReportId` | `SummativeAssessmentReportId` | `dbo.SummativeAssessmentReport` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_SummativeAssessmentUnitStandard_SummativeAssessmentReportId` | `SummativeAssessmentReportId` | No |
| `IX_SummativeAssessmentUnitStandard_UnitStandardCode` | `UnitStandardCode` | No |

---

### <a id="systemconfig"></a> `dbo.SystemConfig`

**Description:** Runtime system configuration key-value overrides.  
**CLR Model:** `Nsdms.Domain.Entities.SystemConfig`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ConfigCategory` | `nvarchar(50)` | **NOT NULL** |  | Configuration category grouping (e.g. General, Storage, Integrations, Finance, Scheduler, Security). |
| `ConfigKey` | `nvarchar(150)` | **NOT NULL** |  | Unique configuration hierarchical key (e.g. General:AppVersion, Storage:LocalPath, Integrations:DynamicsGp:Endpoint). |
| `ConfigValue` | `nvarchar(max)` | NULL |  | Current configured runtime value. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DataType` | `nvarchar(30)` | **NOT NULL** |  | Data type encoding (e.g. String, Boolean, Integer, Decimal, Json). |
| `Description` | `nvarchar(500)` | NULL |  | Descriptive explanation and operational purpose of this configuration setting. |
| `IsActive` | `bit` | **NOT NULL** |  | Indicates whether this configuration override is active. |
| `IsEncrypted` | `bit` | **NOT NULL** |  | Indicates whether the stored configuration value is encrypted with AES-256. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_SystemConfig_ConfigCategory` | `ConfigCategory` | No |
| `IX_SystemConfig_ConfigKey` | `ConfigKey` | ✅ Yes |
| `IX_SystemConfig_IsActive` | `IsActive` | No |

---

### <a id="systemfeatureflag"></a> `dbo.SystemFeatureFlag`

**Description:** Granular runtime feature flags for enabling/disabling modules and integrations. All external third-party integrations default to IsEnabled = false.  
**CLR Model:** `Nsdms.Domain.Entities.SystemFeatureFlag`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `Description` | `nvarchar(500)` | NULL |  | Business explanation and functional scope of the feature toggle. |
| `FeatureCategory` | `nvarchar(50)` | **NOT NULL** |  | Feature flag module grouping category (e.g. Integrations, Storage, Scheduler, Workflow, Compliance). |
| `FeatureKey` | `nvarchar(150)` | **NOT NULL** |  | Unique feature flag key identifier (e.g. Integrations.DynamicsGp, Features.TradeTestOnlineBooking). |
| `FeatureName` | `nvarchar(150)` | **NOT NULL** |  | Human-readable display name of the feature flag. |
| `IsActive` | `bit` | **NOT NULL** |  | Indicates whether the feature flag definition is active. |
| `IsEnabled` | `bit` | **NOT NULL** |  | State toggle (true = active, false = disabled/simulated fallback). |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_SystemFeatureFlag_FeatureCategory` | `FeatureCategory` | No |
| `IX_SystemFeatureFlag_FeatureKey` | `FeatureKey` | ✅ Yes |
| `IX_SystemFeatureFlag_IsEnabled` | `IsEnabled` | No |

---

### <a id="systemnotification"></a> `dbo.SystemNotification`

**Description:** Persistent system notification for real-time SignalR push and user inbox alerts.  
**CLR Model:** `Nsdms.Domain.Entities.SystemNotification`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ActionUrl` | `nvarchar(300)` | NULL |  | Domain property for ActionUrl. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `IsRead` | `bit` | **NOT NULL** |  | Domain property for IsRead. |
| `Message` | `nvarchar(1000)` | **NOT NULL** |  | Domain property for Message. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `NotificationType` | `nvarchar(50)` | **NOT NULL** |  | Domain property for NotificationType. |
| `ReadAt` | `datetime2` | NULL |  | Domain property for ReadAt. |
| `RecipientRole` | `nvarchar(100)` | NULL |  | Domain property for RecipientRole. |
| `RecipientUsername` | `nvarchar(150)` | NULL |  | Domain property for RecipientUsername. |
| `Severity` | `nvarchar(30)` | **NOT NULL** |  | Domain property for Severity. |
| `Title` | `nvarchar(200)` | **NOT NULL** |  | Domain property for Title. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_SystemNotification_CreatedAt` | `CreatedAt` | No |
| `IX_SystemNotification_IsRead` | `IsRead` | No |
| `IX_SystemNotification_RecipientRole` | `RecipientRole` | No |
| `IX_SystemNotification_RecipientUsername` | `RecipientUsername` | No |

---

### <a id="tradementorratiopolicy"></a> `dbo.TradeMentorRatioPolicy`

**Description:** Statutory artisan mentor-to-apprentice ratio policy per trade qualification or OFO occupational code. Regulates maximum learner supervision capacity for workplace approvals under NAMB and QCTO frameworks.  
**CLR Model:** `Nsdms.Domain.Entities.TradeMentorRatioPolicy`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `EnforceStrictly` | `bit` | **NOT NULL** |  | Indicates whether this trade enforces strict blocking on excess learner enrollments or advisory warnings only. |
| `IsActive` | `bit` | **NOT NULL** |  | Indicates whether the trade policy is active. |
| `MaxAllowedRatio` | `int` | **NOT NULL** |  | Maximum permissible ratio under special dispensation / approval (e.g. 6). |
| `MinExperienceYearsRequired` | `int` | **NOT NULL** |  | Minimum post-apprenticeship / post-trade test verified years of experience required to mentor in this trade. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `Notes` | `nvarchar(500)` | NULL |  | Regulatory notes, gazette references, or curriculum guidelines. |
| `SaqaQualificationId` | `int` | NULL |  | SAQA Registered Qualification ID code associated with this trade. |
| `StandardRatio` | `int` | **NOT NULL** |  | Standard statutory ratio of learners per qualified artisan mentor (e.g. 4 for 1:4). |
| `TradeCode` | `nvarchar(50)` | **NOT NULL** |  | Unique Trade / Occupational Code (e.g. WELD, ELEC, FITT, BOIL, 651202). |
| `TradeOfoCode` | `nvarchar(50)` | NULL |  | Organising Framework for Occupations (OFO) code. |
| `TradeTitle` | `nvarchar(200)` | **NOT NULL** |  | Official trade qualification or occupational title (e.g. Welder, Electrician, Fitter and Turner). |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_TradeMentorRatioPolicy_IsActive` | `IsActive` | No |
| `IX_TradeMentorRatioPolicy_SaqaQualificationId` | `SaqaQualificationId` | No |
| `IX_TradeMentorRatioPolicy_TradeCode` | `TradeCode` | ✅ Yes |
| `IX_TradeMentorRatioPolicy_TradeOfoCode` | `TradeOfoCode` | No |

---

### <a id="tradetesttask"></a> `dbo.TradeTestTask`

**Description:** Practical Task and Scoring Item evaluated during the Trade Test.  
**CLR Model:** `Nsdms.Domain.Entities.TradeTestTask`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AssessorComments` | `nvarchar(max)` | NULL |  | Domain property for AssessorComments. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `IsCompetent` | `bit` | **NOT NULL** |  | Domain property for IsCompetent. |
| `LearnerTradeTestApplicationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `MarksObtained` | `decimal(18,2)` | **NOT NULL** |  | Domain property for MarksObtained. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `PassPercentage` | `decimal(18,2)` | **NOT NULL** |  | Domain property for PassPercentage. |
| `PercentageAchieved` | `decimal(18,2)` | **NOT NULL** |  | Domain property for PercentageAchieved. |
| `TaskDescription` | `nvarchar(max)` | NULL |  | Domain property for TaskDescription. |
| `TaskNumber` | `int` | **NOT NULL** |  | Domain property for TaskNumber. |
| `TaskTitle` | `nvarchar(200)` | **NOT NULL** |  | Domain property for TaskTitle. |
| `TotalMarksAvailable` | `decimal(18,2)` | **NOT NULL** |  | Domain property for TotalMarksAvailable. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_TradeTestTask_LearnerTradeTestApplication_LearnerTradeTestApplicationId` | `LearnerTradeTestApplicationId` | `dbo.LearnerTradeTestApplication` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_TradeTestTask_LearnerTradeTestApplicationId` | `LearnerTradeTestApplicationId` | No |
| `IX_TradeTestTask_TaskNumber` | `TaskNumber` | No |

---

### <a id="trainingcommittee"></a> `dbo.TrainingCommittee`

**Description:** Employer Workplace Training Committee constituted per statutory merSETA guidelines.  
**CLR Model:** `Nsdms.Domain.Entities.TrainingCommittee`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CommitteeStatusCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for CommitteeStatusCode. |
| `ConstitutionalQuorumMet` | `bit` | **NOT NULL** |  | Domain property for ConstitutionalQuorumMet. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `FinancialYear` | `int` | **NOT NULL** |  | Domain property for FinancialYear. |
| `LastMeetingDate` | `datetime2` | NULL |  | Domain property for LastMeetingDate. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `OrganisationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_TrainingCommittee_Organisation_OrganisationId` | `OrganisationId` | `dbo.Organisation` | `Restrict` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_TrainingCommittee_CommitteeStatusCode` | `CommitteeStatusCode` | No |
| `IX_TrainingCommittee_FinancialYear` | `FinancialYear` | No |
| `IX_TrainingCommittee_OrganisationId` | `OrganisationId` | No |

---

### <a id="trainingcommitteemember"></a> `dbo.TrainingCommitteeMember`

**Description:** Individual Union and Employer representatives on a Training Committee.  
**CLR Model:** `Nsdms.Domain.Entities.TrainingCommitteeMember`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `Constituency` | `nvarchar(100)` | **NOT NULL** |  | Domain property for Constituency. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `IsActive` | `bit` | **NOT NULL** |  | Indicates whether the record is active and operational. |
| `MemberRoleCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for MemberRoleCode. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `PersonId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `TrainingCommitteeId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_TrainingCommitteeMember_Person_PersonId` | `PersonId` | `dbo.Person` | `Restrict` |
| `FK_TrainingCommitteeMember_TrainingCommittee_TrainingCommitteeId` | `TrainingCommitteeId` | `dbo.TrainingCommittee` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_TrainingCommitteeMember_PersonId` | `PersonId` | No |
| `IX_TrainingCommitteeMember_TrainingCommitteeId` | `TrainingCommitteeId` | No |

---

### <a id="trainingprovider"></a> `dbo.TrainingProvider`

**Description:** Accredited Skills Development Provider (SDP) offering registered occupational qualifications and skills programmes, capturing all statutory fields required for SETMIS File 100 reporting.  
**CLR Model:** `Nsdms.Domain.Entities.TrainingProvider`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AccreditationEndDate` | `datetime2` | NULL |  | Expiration date of current ETQA accreditation cycle. |
| `AccreditationNumber` | `nvarchar(50)` | **NOT NULL** |  | Official ETQA accreditation certificate number. |
| `AccreditationStartDate` | `datetime2` | NULL |  | Start date of current ETQA accreditation cycle. |
| `BrandColorHex` | `nvarchar(max)` | NULL |  | Primary brand color in HEX format (e.g. #865300). |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `EtqaDecisionNumber` | `nvarchar(50)` | NULL |  | MerSETA ETQA committee decision minute reference number (SETMIS File 100). |
| `EtqaId` | `nvarchar(10)` | NULL |  | Submitting ETQA / SETA Identifier (references lookup.SetaType, default 17 for merSETA). |
| `FaxNumber` | `nvarchar(50)` | NULL |  | Facsimile transmission contact number. |
| `IsActive` | `bit` | **NOT NULL** |  | Indicates whether the provider is active for new learner enrolments. |
| `LogoDocumentId` | `int` | NULL |  | Foreign key referencing the high-resolution brand logo DocumentMetadata. |
| `MaxLearnerCapacity` | `int` | NULL |  | Maximum concurrent learner enrolment capacity authorized for facilities. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `OrganisationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the parent legal Organisation. |
| `PrimaryContactPersonId` | `int` | NULL | 🔗 **FK** | Optional foreign key referencing the SDP principal or training director. |
| `ProviderClassId` | `nvarchar(10)` | NULL |  | Provider institutional class code (references lookup.ProviderClassType: 01 Public TVET, 02 Private, 03 University, 04 NGO/CBO). |
| `ProviderCode` | `nvarchar(50)` | NULL |  | Statutory Provider Code as recorded on the National Learners' Records Database (NLRD) and SETMIS (SETMIS File 100). |
| `ProviderStatusCode` | `nvarchar(15)` | NULL |  | Legacy Provider Status Code mapping. |
| `ProviderStatusId` | `nvarchar(10)` | NULL |  | Current accreditation standing code (references lookup.ProviderStatusType: 01 Accredited, 02 Provisional, 03 De-accredited). |
| `ProviderTypeCode` | `nvarchar(15)` | NULL |  | Legacy Provider Type Code mapping. |
| `ProviderTypeId` | `nvarchar(10)` | NULL |  | Provider functional operational type code (references lookup.ProviderType: 01 Education, 02 Training, 03 Employer Provider). |
| `SarsNumber` | `nvarchar(50)` | NULL |  | SARS Income Tax reference number (SETMIS File 100). |
| `WebsiteUrl` | `nvarchar(200)` | NULL |  | Corporate website URL. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_TrainingProvider_Organisation_OrganisationId` | `OrganisationId` | `dbo.Organisation` | `Restrict` |
| `FK_TrainingProvider_Person_PrimaryContactPersonId` | `PrimaryContactPersonId` | `dbo.Person` | `Restrict` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_TrainingProvider_AccreditationNumber` | `AccreditationNumber` | No |
| `IX_TrainingProvider_EtqaId` | `EtqaId` | No |
| `IX_TrainingProvider_IsActive` | `IsActive` | No |
| `IX_TrainingProvider_OrganisationId` | `OrganisationId` | No |
| `IX_TrainingProvider_PrimaryContactPersonId` | `PrimaryContactPersonId` | No |
| `IX_TrainingProvider_ProviderClassId` | `ProviderClassId` | No |
| `IX_TrainingProvider_ProviderCode` | `ProviderCode` | No |
| `IX_TrainingProvider_ProviderStatusId` | `ProviderStatusId` | No |
| `IX_TrainingProvider_ProviderTypeId` | `ProviderTypeId` | No |

---

### <a id="trainingproviderassessorlink"></a> `dbo.TrainingProviderAssessorLink`

**Description:** Relational binding between an accredited ETQA Assessor/Moderator and an SDP delivery campus.  
**CLR Model:** `Nsdms.Domain.Entities.TrainingProviderAssessorLink`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `EndDate` | `datetime2` | NULL |  | Domain property for EndDate. |
| `EtqaAssessorId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `RoleTypeCode` | `nvarchar(50)` | **NOT NULL** |  | Role type: Assessor, Moderator, LeadAssessor |
| `StartDate` | `datetime2` | **NOT NULL** |  | Domain property for StartDate. |
| `Status` | `nvarchar(50)` | **NOT NULL** |  | Status: Active, Terminated, PendingVerification |
| `TrainingProviderCampusId` | `int` | NULL | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `TrainingProviderId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `VerificationNotes` | `nvarchar(max)` | NULL |  | Domain property for VerificationNotes. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_TrainingProviderAssessorLink_EtqaAssessor_EtqaAssessorId` | `EtqaAssessorId` | `dbo.EtqaAssessor` | `Cascade` |
| `FK_TrainingProviderAssessorLink_TrainingProviderCampus_TrainingProviderCampusId` | `TrainingProviderCampusId` | `dbo.TrainingProviderCampus` | `NoAction` |
| `FK_TrainingProviderAssessorLink_TrainingProvider_TrainingProviderId` | `TrainingProviderId` | `dbo.TrainingProvider` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_TrainingProviderAssessorLink_EtqaAssessorId` | `EtqaAssessorId` | No |
| `IX_TrainingProviderAssessorLink_TrainingProviderCampusId` | `TrainingProviderCampusId` | No |
| `IX_TrainingProviderAssessorLink_TrainingProviderId` | `TrainingProviderId` | No |

---

### <a id="trainingprovidercampus"></a> `dbo.TrainingProviderCampus`

**Description:** Physical training site or delivery campus belonging to an accredited Skills Development Provider (SDP).  
**CLR Model:** `Nsdms.Domain.Entities.TrainingProviderCampus`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CampusCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for CampusCode. |
| `CampusName` | `nvarchar(150)` | **NOT NULL** |  | Domain property for CampusName. |
| `City` | `nvarchar(max)` | NULL |  | Domain property for City. |
| `ContactEmail` | `nvarchar(max)` | NULL |  | Domain property for ContactEmail. |
| `ContactPersonName` | `nvarchar(max)` | NULL |  | Domain property for ContactPersonName. |
| `ContactPhone` | `nvarchar(max)` | NULL |  | Domain property for ContactPhone. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `IsPrimarySite` | `bit` | **NOT NULL** |  | Domain property for IsPrimarySite. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `PhysicalAddressLine1` | `nvarchar(max)` | NULL |  | Domain property for PhysicalAddressLine1. |
| `PhysicalAddressLine2` | `nvarchar(max)` | NULL |  | Domain property for PhysicalAddressLine2. |
| `PostalCode` | `nvarchar(max)` | NULL |  | Domain property for PostalCode. |
| `ProvinceCode` | `nvarchar(max)` | NULL |  | Domain property for ProvinceCode. |
| `Status` | `nvarchar(50)` | **NOT NULL** |  | Current lifecycle state code in the workflow engine. |
| `TrainingProviderId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_TrainingProviderCampus_TrainingProvider_TrainingProviderId` | `TrainingProviderId` | `dbo.TrainingProvider` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_TrainingProviderCampus_CampusCode` | `CampusCode` | No |
| `IX_TrainingProviderCampus_TrainingProviderId` | `TrainingProviderId` | No |

---

### <a id="trainingproviderqualification"></a> `dbo.TrainingProviderQualification`

**Description:** Registered SAQA qualification delivery scope accredited to a Training Provider.  
**CLR Model:** `Nsdms.Domain.Entities.TrainingProviderQualification`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AccreditationStatusCode` | `nvarchar(15)` | NULL |  | Accreditation standing code for this specific qualification scope. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `ExpiryDate` | `datetime2` | NULL |  | Scope expiration date. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `NqfLevel` | `int` | NULL |  | National Qualifications Framework (NQF) level descriptor (e.g. 2, 3, 4, 5). |
| `QualificationTitle` | `nvarchar(200)` | **NOT NULL** |  | Official title of the registered qualification. |
| `SaqaQualificationId` | `int` | **NOT NULL** |  | SAQA Registered Qualification ID code. |
| `TrainingProviderId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the parent TrainingProvider. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_TrainingProviderQualification_TrainingProvider_TrainingProviderId` | `TrainingProviderId` | `dbo.TrainingProvider` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_TrainingProviderQualification_AccreditationStatusCode` | `AccreditationStatusCode` | No |
| `IX_TrainingProviderQualification_SaqaQualificationId` | `SaqaQualificationId` | No |
| `IX_TrainingProviderQualification_TrainingProviderId` | `TrainingProviderId` | No |

---

### <a id="trainingproviderunitstandard"></a> `dbo.TrainingProviderUnitStandard`

**Description:** Registered SAQA unit standard accredited for delivery by a Training Provider.  
**CLR Model:** `Nsdms.Domain.Entities.TrainingProviderUnitStandard`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `Credits` | `int` | **NOT NULL** |  | SAQA credit value assigned to the unit standard. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `NqfLevel` | `int` | NULL |  | National Qualifications Framework (NQF) level descriptor. |
| `TrainingProviderId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the parent TrainingProvider. |
| `UnitStandardId` | `int` | **NOT NULL** |  | SAQA Registered Unit Standard ID number. |
| `UnitStandardTitle` | `nvarchar(200)` | **NOT NULL** |  | Official title of the registered unit standard. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_TrainingProviderUnitStandard_TrainingProvider_TrainingProviderId` | `TrainingProviderId` | `dbo.TrainingProvider` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_TrainingProviderUnitStandard_TrainingProviderId` | `TrainingProviderId` | No |
| `IX_TrainingProviderUnitStandard_UnitStandardId` | `UnitStandardId` | No |

---

### <a id="visit"></a> `dbo.Visit`

**Description:** On-site monitoring, workplace inspection, and employer liaison visits scheduled and executed by MerSETA staff.  
**CLR Model:** `Nsdms.Domain.Entities.Visit`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ContactPersonId` | `int` | **NOT NULL** | 🔗 **FK** | Mandatory foreign key referencing the employer's designated Contact Person attending the visit. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `Location` | `nvarchar(300)` | NULL |  | Physical location, facility branch, or site address where the visit was held. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `OrganisationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the host or inspected Employer Organisation. |
| `OutcomeNotes` | `nvarchar(max)` | NULL |  | Official findings, remediation recommendations, and outcome notes recorded by the visiting officer. |
| `Purpose` | `nvarchar(max)` | NULL |  | Detailed statutory or operational purpose for conducting the visit. |
| `Title` | `nvarchar(200)` | **NOT NULL** |  | Concise summary title or subject of the visit. |
| `VisitDate` | `datetime2` | **NOT NULL** |  | Scheduled date and time of the visit activity. |
| `VisitStatusCode` | `nvarchar(15)` | NULL |  | Current execution status code of the visit (e.g. Scheduled, InProgress, Completed, Cancelled). |
| `VisitTypeCode` | `nvarchar(15)` | NULL |  | Classification code of the visit activity (e.g. WORKPLACE_APPROVAL, MONITORING, VERIFICATION, SDF_LIAISON). |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_Visit_Person_ContactPersonId` | `ContactPersonId` | `dbo.Person` | `Restrict` |
| `FK_Visit_Organisation_OrganisationId` | `OrganisationId` | `dbo.Organisation` | `Restrict` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_Visit_ContactPersonId` | `ContactPersonId` | No |
| `IX_Visit_OrganisationId` | `OrganisationId` | No |
| `IX_Visit_VisitDate` | `VisitDate` | No |
| `IX_Visit_VisitStatusCode` | `VisitStatusCode` | No |

---

### <a id="workflowdefinition"></a> `dbo.WorkflowDefinition`

**Description:** Universal workflow process blueprint defining lifecycle states, gates, and transitions.  
**CLR Model:** `Nsdms.Domain.Entities.WorkflowDefinition`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `Code` | `nvarchar(50)` | **NOT NULL** |  | Unique process blueprint code (e.g. PROVIDER, WSP, DG, WPAPP, LRN, TRADETEST). |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `IsActive` | `bit` | **NOT NULL** |  | Indicates whether the workflow definition is active. |
| `KeyFieldName` | `nvarchar(50)` | **NOT NULL** |  | Primary key property name on the target domain entity (default: Id). |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `Name` | `nvarchar(150)` | **NOT NULL** |  | Display name of the workflow business process. |
| `TargetEntityName` | `nvarchar(100)` | **NOT NULL** |  | Target domain entity managed by this workflow machine (e.g. Organisation, WspSubmission, GrantApplication). |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_WorkflowDefinition_Code` | `Code` | ✅ Yes |
| `IX_WorkflowDefinition_TargetEntityName` | `TargetEntityName` | No |

---

### <a id="workflowdelegation"></a> `dbo.WorkflowDelegation`

**Description:** Represents a time-bounded role and module delegation from one user to another (e.g., leave, acting capacity).  
**CLR Model:** `Nsdms.Domain.Entities.WorkflowDelegation`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AllowedModulesJson` | `nvarchar(max)` | **NOT NULL** |  | JSON array of allowed module codes e.g. ["Grants", "Wsp", "Etqa"] or ["*"] for full authority. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | **NOT NULL** |  | User identifier or system process that created the record. |
| `DelegateeUserId` | `int` | **NOT NULL** |  | Domain property for DelegateeUserId. |
| `DelegateeUserName` | `nvarchar(max)` | **NOT NULL** |  | Domain property for DelegateeUserName. |
| `DelegatorUserId` | `int` | **NOT NULL** |  | Domain property for DelegatorUserId. |
| `DelegatorUserName` | `nvarchar(max)` | **NOT NULL** |  | Domain property for DelegatorUserName. |
| `EndDate` | `datetime2` | **NOT NULL** |  | Domain property for EndDate. |
| `IsActive` | `bit` | **NOT NULL** |  | Indicates whether the record is active and operational. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `Reason` | `nvarchar(500)` | **NOT NULL** |  | Domain property for Reason. |
| `StartDate` | `datetime2` | **NOT NULL** |  | Domain property for StartDate. |

---

### <a id="workflowhistory"></a> `dbo.WorkflowHistory`

**Description:** Immutable audit snapshot recording every workflow transition execution and decision comments.  
**CLR Model:** `Nsdms.Domain.Entities.WorkflowHistory`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ActionDate` | `datetime2` | **NOT NULL** |  | UTC timestamp when the transition was executed. |
| `ActionName` | `nvarchar(100)` | **NOT NULL** |  | Name of the transition action executed. |
| `ActorName` | `nvarchar(150)` | **NOT NULL** |  | Full display name of the actor. |
| `ActorRole` | `nvarchar(100)` | **NOT NULL** |  | System role assumed by the actor at time of action. |
| `ActorUserId` | `nvarchar(100)` | **NOT NULL** |  | User identifier of the actor who triggered the transition. |
| `Comments` | `nvarchar(1000)` | NULL |  | Mandatory or optional decision justification comments recorded by the actor. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `FromStateId` | `int` | NULL | 🔗 **FK** | Foreign key referencing the state prior to transition. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `ToStateId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the destination state. |
| `WorkflowInstanceId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the parent WorkflowInstance. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_WorkflowHistory_WorkflowState_FromStateId` | `FromStateId` | `dbo.WorkflowState` | `Restrict` |
| `FK_WorkflowHistory_WorkflowState_ToStateId` | `ToStateId` | `dbo.WorkflowState` | `Restrict` |
| `FK_WorkflowHistory_WorkflowInstance_WorkflowInstanceId` | `WorkflowInstanceId` | `dbo.WorkflowInstance` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_WorkflowHistory_ActionDate` | `ActionDate` | No |
| `IX_WorkflowHistory_WorkflowInstanceId` | `WorkflowInstanceId` | No |

---

### <a id="workflowinstance"></a> `dbo.WorkflowInstance`

**Description:** Execution tracking instance of a workflow machine for a specific entity record.  
**CLR Model:** `Nsdms.Domain.Entities.WorkflowInstance`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CompletedDate` | `datetime2` | NULL |  | Timestamp when the workflow reached a terminal state. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `CurrentWorkflowStateId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the current active WorkflowState. |
| `EntityId` | `int` | **NOT NULL** |  | Primary key integer value of the target entity record. |
| `EntityReferenceNumber` | `nvarchar(100)` | **NOT NULL** |  | Business reference number of the target record (e.g. WSP-2026-001). |
| `EntityTitle` | `nvarchar(250)` | **NOT NULL** |  | Human-readable title or description of the target record. |
| `InitiatedDate` | `datetime2` | **NOT NULL** |  | Timestamp when the workflow was started. |
| `InitiatorName` | `nvarchar(150)` | **NOT NULL** |  | Full display name of the initiating user. |
| `InitiatorUserId` | `nvarchar(100)` | **NOT NULL** |  | User identifier who initiated the workflow lifecycle. |
| `IsCompleted` | `bit` | **NOT NULL** |  | Indicates whether the workflow instance is closed. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `WorkflowDefinitionId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the parent WorkflowDefinition. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_WorkflowInstance_WorkflowState_CurrentWorkflowStateId` | `CurrentWorkflowStateId` | `dbo.WorkflowState` | `Restrict` |
| `FK_WorkflowInstance_WorkflowDefinition_WorkflowDefinitionId` | `WorkflowDefinitionId` | `dbo.WorkflowDefinition` | `Restrict` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_WorkflowInstance_CurrentWorkflowStateId` | `CurrentWorkflowStateId` | No |
| `IX_WorkflowInstance_IsCompleted` | `IsCompleted` | No |
| `IX_WorkflowInstance_WorkflowDefinitionId` | `WorkflowDefinitionId` | No |
| `IX_WorkflowInstance_WorkflowDefinitionId_EntityId` | `WorkflowDefinitionId, EntityId` | No |

---

### <a id="workflownotification"></a> `dbo.WorkflowNotification`

**Description:** User notification alert dispatched upon workflow advancement or assignment.  
**CLR Model:** `Nsdms.Domain.Entities.WorkflowNotification`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `CreatedDate` | `datetime2` | **NOT NULL** |  | Timestamp when the notification was created. |
| `IsRead` | `bit` | **NOT NULL** |  | Indicates whether the recipient has viewed the notification. |
| `MessageHtml` | `nvarchar(max)` | **NOT NULL** |  | HTML / rich message body. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `ReadDate` | `datetime2` | NULL |  | Timestamp when the recipient marked the alert as read. |
| `RecipientUserId` | `nvarchar(100)` | **NOT NULL** |  | User identifier of the notification recipient. |
| `TargetRoute` | `nvarchar(250)` | **NOT NULL** |  | Navigation target route for the notification click action. |
| `Title` | `nvarchar(200)` | **NOT NULL** |  | Headline title of the notification alert. |
| `WorkflowInstanceId` | `int` | NULL | 🔗 **FK** | Optional foreign key referencing the associated WorkflowInstance. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_WorkflowNotification_WorkflowInstance_WorkflowInstanceId` | `WorkflowInstanceId` | `dbo.WorkflowInstance` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_WorkflowNotification_CreatedDate` | `CreatedDate` | No |
| `IX_WorkflowNotification_IsRead` | `IsRead` | No |
| `IX_WorkflowNotification_RecipientUserId` | `RecipientUserId` | No |

---

### <a id="workflowsignoffattestation"></a> `dbo.WorkflowSignoffAttestation`

**Description:** Represents a cryptographic digital signoff attestation captured during approval/rejection milestones for AGSA and King IV compliance.  
**CLR Model:** `Nsdms.Domain.Entities.WorkflowSignoffAttestation`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AttestationNotes` | `nvarchar(1000)` | **NOT NULL** |  | Domain property for AttestationNotes. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | **NOT NULL** |  | User identifier or system process that created the record. |
| `DocumentSha256Checksum` | `nvarchar(128)` | **NOT NULL** |  | Hex-encoded SHA-256 hash of the payload / document snapshot at signoff time. |
| `EntityId` | `int` | **NOT NULL** |  | Domain property for EntityId. |
| `EntityName` | `nvarchar(100)` | **NOT NULL** |  | Domain property for EntityName. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `SignedAt` | `datetime2` | **NOT NULL** |  | Domain property for SignedAt. |
| `SignerName` | `nvarchar(150)` | **NOT NULL** |  | Domain property for SignerName. |
| `SignerRsaId` | `nvarchar(20)` | **NOT NULL** |  | Domain property for SignerRsaId. |
| `SignerUserId` | `int` | **NOT NULL** |  | Domain property for SignerUserId. |
| `SignoffAction` | `nvarchar(50)` | **NOT NULL** |  | Domain property for SignoffAction. |
| `SignoffRole` | `nvarchar(100)` | **NOT NULL** |  | Domain property for SignoffRole. |
| `WorkflowTaskId` | `int` | NULL |  | Domain property for WorkflowTaskId. |

---

### <a id="workflowstate"></a> `dbo.WorkflowState`

**Description:** Lifecycle step / gate within a workflow machine (e.g. Draft, Under Review, Pending Approval, Approved).  
**CLR Model:** `Nsdms.Domain.Entities.WorkflowState`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AllowedGroupRole` | `nvarchar(100)` | NULL |  | User group / role authorized to act on this stage (e.g. CLO, RegionManager, ReviewCommittee, QA_Manager, CEO, Admin). |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `IsInitial` | `bit` | **NOT NULL** |  | Indicates whether this is the entry state for newly initiated instances. |
| `IsTerminal` | `bit` | **NOT NULL** |  | Indicates whether this is a terminal end state (Approved / Rejected / Cancelled). |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `StateCode` | `nvarchar(50)` | **NOT NULL** |  | Machine code representing the state (e.g. DRAFT, REVIEW, APPROVED, REJECTED). |
| `StateName` | `nvarchar(100)` | **NOT NULL** |  | Human-readable name of the lifecycle state. |
| `StepOrder` | `int` | **NOT NULL** |  | Sequence order index for UI stepper rendering. |
| `WorkflowDefinitionId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the parent WorkflowDefinition. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_WorkflowState_WorkflowDefinition_WorkflowDefinitionId` | `WorkflowDefinitionId` | `dbo.WorkflowDefinition` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_WorkflowState_StateCode` | `StateCode` | No |
| `IX_WorkflowState_WorkflowDefinitionId` | `WorkflowDefinitionId` | No |

---

### <a id="workflowtask"></a> `dbo.WorkflowTask`

**Description:** Task assigned to a specific role or user requiring review, inspection, or verification action.  
**CLR Model:** `Nsdms.Domain.Entities.WorkflowTask`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AssignedGroupRole` | `nvarchar(100)` | NULL |  | Target group role eligible to claim this task. |
| `AssignedUserId` | `nvarchar(100)` | NULL |  | User identifier of the assignee who claimed or was assigned the task. |
| `AssignedUserName` | `nvarchar(150)` | NULL |  | Full display name of the assignee. |
| `ClaimedDate` | `datetime2` | NULL |  | Timestamp when the user claimed ownership of the task. |
| `CompletedDate` | `datetime2` | NULL |  | Timestamp when the task was finalized. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DueDate` | `datetime2` | **NOT NULL** |  | Target completion deadline based on SLA governance. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `Priority` | `nvarchar(20)` | **NOT NULL** |  | Task urgency level (e.g. Low, Normal, High, Urgent). |
| `TargetRoute` | `nvarchar(250)` | **NOT NULL** |  | Stacked master-detail deep link route (e.g. /wsp/1, /employers/10). |
| `TaskDescription` | `nvarchar(500)` | **NOT NULL** |  | Detailed instructions and checklist requirements. |
| `TaskStatus` | `nvarchar(50)` | **NOT NULL** |  | Task status code (e.g. Open, Claimed, Completed, Cancelled). |
| `TaskTitle` | `nvarchar(250)` | **NOT NULL** |  | Concise summary title of the assigned task. |
| `WorkflowInstanceId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the parent WorkflowInstance. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_WorkflowTask_WorkflowInstance_WorkflowInstanceId` | `WorkflowInstanceId` | `dbo.WorkflowInstance` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_WorkflowTask_AssignedGroupRole` | `AssignedGroupRole` | No |
| `IX_WorkflowTask_AssignedUserId` | `AssignedUserId` | No |
| `IX_WorkflowTask_DueDate` | `DueDate` | No |
| `IX_WorkflowTask_TaskStatus` | `TaskStatus` | No |
| `IX_WorkflowTask_WorkflowInstanceId` | `WorkflowInstanceId` | No |

---

### <a id="workflowtasklease"></a> `dbo.WorkflowTaskLease`

**Description:** Represents a distributed concurrency lock lease on a workflow task to prevent simultaneous claims and race conditions.  
**CLR Model:** `Nsdms.Domain.Entities.WorkflowTaskLease`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ClaimedByUserId` | `int` | **NOT NULL** |  | Domain property for ClaimedByUserId. |
| `ClaimedByUserName` | `nvarchar(150)` | **NOT NULL** |  | Domain property for ClaimedByUserName. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | **NOT NULL** |  | User identifier or system process that created the record. |
| `IsReleased` | `bit` | **NOT NULL** |  | Domain property for IsReleased. |
| `LastHeartbeatTime` | `datetime2` | **NOT NULL** |  | Domain property for LastHeartbeatTime. |
| `LeaseExpiryTime` | `datetime2` | **NOT NULL** |  | Domain property for LeaseExpiryTime. |
| `LeaseStartTime` | `datetime2` | **NOT NULL** |  | Domain property for LeaseStartTime. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `WorkflowTaskId` | `int` | **NOT NULL** |  | Domain property for WorkflowTaskId. |

---

### <a id="workflowtransition"></a> `dbo.WorkflowTransition`

**Description:** Authorized state transition path with role authorization gates and UI button metadata.  
**CLR Model:** `Nsdms.Domain.Entities.WorkflowTransition`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ActionName` | `nvarchar(100)` | **NOT NULL** |  | Action trigger name displayed on the UI Action Bridge button (e.g. Submit, Recommend, Approve, Reject). |
| `ButtonColor` | `nvarchar(50)` | NULL |  | Hex color code for the UI Action button (e.g. #1e40af, #16a34a, #dc2626). |
| `ButtonIcon` | `nvarchar(50)` | NULL |  | Material icon identifier for the UI Action button. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `FromStateId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the originating WorkflowState. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `NewEntityStatusCode` | `nvarchar(50)` | NULL |  | Status code automatically pushed to the parent entity upon transition execution. |
| `RequiredPermission` | `nvarchar(100)` | NULL |  | CASL permission or policy claim required to execute this transition. |
| `RequiresComments` | `bit` | **NOT NULL** |  | Indicates whether the user must provide justification comments when executing this transition. |
| `ToStateId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the target destination WorkflowState. |
| `WorkflowDefinitionId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the parent WorkflowDefinition. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_WorkflowTransition_WorkflowState_FromStateId` | `FromStateId` | `dbo.WorkflowState` | `Restrict` |
| `FK_WorkflowTransition_WorkflowState_ToStateId` | `ToStateId` | `dbo.WorkflowState` | `Restrict` |
| `FK_WorkflowTransition_WorkflowDefinition_WorkflowDefinitionId` | `WorkflowDefinitionId` | `dbo.WorkflowDefinition` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_WorkflowTransition_FromStateId` | `FromStateId` | No |
| `IX_WorkflowTransition_ToStateId` | `ToStateId` | No |
| `IX_WorkflowTransition_WorkflowDefinitionId` | `WorkflowDefinitionId` | No |

---

### <a id="workplaceapproval"></a> `dbo.WorkplaceApproval`

**Description:** Workplace site inspection approval for hosting apprentice and learnership training.  
**CLR Model:** `Nsdms.Domain.Entities.WorkplaceApproval`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ApprovalDate` | `datetime2` | NULL |  | Official decision date granting workplace approval. |
| `ApprovalNumber` | `nvarchar(50)` | **NOT NULL** |  | Official workplace approval certificate reference number (e.g. WPA-2026-001). |
| `ApprovalStatusCode` | `nvarchar(50)` | **NOT NULL** |  | Current approval lifecycle status code (e.g. Pending, Approved, Rejected, Expired). |
| `AssessorPersonId` | `int` | NULL | 🔗 **FK** | Foreign key referencing the MerSETA officer / assessor who performed the inspection. |
| `ContactPersonId` | `int` | NULL | 🔗 **FK** | Mandatory Foreign key referencing the designated Employer Contact Person present during the workplace visit/approval. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `CustomTradeRatio` | `int` | NULL |  | Custom site-specific learner-to-mentor ratio override (e.g. 3 for 1:3), overriding the standard trade policy. |
| `ExpiryDate` | `datetime2` | NULL |  | Validity expiration date of the workplace approval certificate. |
| `InspectionDate` | `datetime2` | NULL |  | Date when the physical on-site audit inspection occurred. |
| `IsActive` | `bit` | **NOT NULL** |  | Indicates whether the workplace approval is currently active. |
| `IsRatioEnforced` | `bit` | NULL |  | Explicit mentor ratio enforcement override for this workplace approval (null = inherit Org/Global, true = enforce, false = exempt). |
| `MentorRatioExemptionNotes` | `nvarchar(500)` | NULL |  | Exemption justification or special dispensation notes for this workplace approval. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `OrganisationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the host Employer Organisation. |
| `OrganisationSiteId` | `int` | NULL | 🔗 **FK** | Optional foreign key referencing the specific branch or plant site approved. |
| `QualificationTitle` | `nvarchar(250)` | **NOT NULL** |  | Title of the registered qualification approved for on-site hosting. |
| `Recommendations` | `nvarchar(max)` | NULL |  | Official auditor recommendations, tool adjustments, or compliance notes. |
| `SaqaQualificationId` | `int` | NULL |  | SAQA Registered Qualification ID code approved for practical workplace training. |
| `TradeCode` | `nvarchar(50)` | NULL |  | Designated Trade / Occupational Code (references TradeMentorRatioPolicy.TradeCode, e.g. WELD, ELEC, FITT). |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_WorkplaceApproval_Person_AssessorPersonId` | `AssessorPersonId` | `dbo.Person` | `Restrict` |
| `FK_WorkplaceApproval_Person_ContactPersonId` | `ContactPersonId` | `dbo.Person` | `Restrict` |
| `FK_WorkplaceApproval_Organisation_OrganisationId` | `OrganisationId` | `dbo.Organisation` | `Restrict` |
| `FK_WorkplaceApproval_OrganisationSite_OrganisationSiteId` | `OrganisationSiteId` | `dbo.OrganisationSite` | `Restrict` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_WorkplaceApproval_ApprovalNumber` | `ApprovalNumber` | No |
| `IX_WorkplaceApproval_ApprovalStatusCode` | `ApprovalStatusCode` | No |
| `IX_WorkplaceApproval_ContactPersonId` | `ContactPersonId` | No |
| `IX_WorkplaceApproval_IsRatioEnforced` | `IsRatioEnforced` | No |
| `IX_WorkplaceApproval_OrganisationId` | `OrganisationId` | No |
| `IX_WorkplaceApproval_OrganisationSiteId` | `OrganisationSiteId` | No |
| `IX_WorkplaceApproval_TradeCode` | `TradeCode` | No |

---

### <a id="workplaceapprovalmentor"></a> `dbo.WorkplaceApprovalMentor`

**Description:** Qualified artisan mentor assigned to supervise apprentices and learners at an approved workplace.  
**CLR Model:** `Nsdms.Domain.Entities.WorkplaceApprovalMentor`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ArtisanTradeNumber` | `nvarchar(50)` | NULL |  | Red Seal Artisan Trade Certificate serial number. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `Designation` | `nvarchar(100)` | **NOT NULL** |  | Enterprise job title / role designation (e.g. Master Artisan, Lead Fitter, Foreman). |
| `IsActive` | `bit` | **NOT NULL** |  | Indicates whether the mentor is actively mentoring learners at this site. |
| `IsCertifiedArtisan` | `bit` | **NOT NULL** |  | Indicates whether the mentor is certified as a qualified Red Seal artisan. |
| `IsRatioEnforced` | `bit` | **NOT NULL** |  | Indicates whether ratio enforcement is active for this mentor. |
| `IsRatioExempt` | `bit` | **NOT NULL** |  | Indicates whether this mentor is exempt from statutory ratio enforcement. |
| `MaxLearnerCapacity` | `int` | NULL |  | Optional custom maximum apprentice supervision capacity for this specific mentor (overrides trade baseline). |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `Notes` | `nvarchar(500)` | NULL |  | Specific notes or special conditions regarding this mentor's capacity. |
| `PersonId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the mentor's demographic Person record. |
| `WorkplaceApprovalId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the parent WorkplaceApproval. |
| `YearsExperience` | `int` | **NOT NULL** |  | Number of verified years of post-apprenticeship industry experience. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_WorkplaceApprovalMentor_Person_PersonId` | `PersonId` | `dbo.Person` | `Restrict` |
| `FK_WorkplaceApprovalMentor_WorkplaceApproval_WorkplaceApprovalId` | `WorkplaceApprovalId` | `dbo.WorkplaceApproval` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_WorkplaceApprovalMentor_IsActive` | `IsActive` | No |
| `IX_WorkplaceApprovalMentor_IsRatioEnforced` | `IsRatioEnforced` | No |
| `IX_WorkplaceApprovalMentor_IsRatioExempt` | `IsRatioExempt` | No |
| `IX_WorkplaceApprovalMentor_PersonId` | `PersonId` | No |
| `IX_WorkplaceApprovalMentor_WorkplaceApprovalId` | `WorkplaceApprovalId` | No |

---

### <a id="workplaceapprovaltoollist"></a> `dbo.WorkplaceApprovalToolList`

**Description:** Tool, equipment, or health and safety checklist item inspected for workplace qualification approval.  
**CLR Model:** `Nsdms.Domain.Entities.WorkplaceApprovalToolList`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AvailableQuantity` | `int` | **NOT NULL** |  | Actual operational quantity verified during the on-site physical inspection. |
| `Category` | `nvarchar(50)` | NULL |  | Tool category classification (e.g. Mechanical, Electrical, Welding, Safety, PPE). |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `Remarks` | `nvarchar(250)` | NULL |  | Auditor remarks on equipment condition, calibration, or servicing status. |
| `RequiredQuantity` | `int` | **NOT NULL** |  | Minimum required quantity specified in the trade training curriculum regulations. |
| `ToolName` | `nvarchar(150)` | **NOT NULL** |  | Name / description of the required tool, machinery, or PPE safety item. |
| `WorkplaceApprovalId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the parent WorkplaceApproval. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_WorkplaceApprovalToolList_WorkplaceApproval_WorkplaceApprovalId` | `WorkplaceApprovalId` | `dbo.WorkplaceApproval` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_WorkplaceApprovalToolList_WorkplaceApprovalId` | `WorkplaceApprovalId` | No |

---

### <a id="workplacemonitoringactionplan"></a> `dbo.WorkplaceMonitoringActionPlan`

**Description:** Corrective Action Plan for identified defects or non-compliances.  
**CLR Model:** `Nsdms.Domain.Entities.WorkplaceMonitoringActionPlan`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ActionRequired` | `nvarchar(2000)` | **NOT NULL** |  | Domain property for ActionRequired. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `Criteria` | `nvarchar(500)` | **NOT NULL** |  | Domain property for Criteria. |
| `IsAtRisk` | `bit` | **NOT NULL** |  | Domain property for IsAtRisk. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `ResolutionDate` | `datetime2` | NULL |  | Domain property for ResolutionDate. |
| `ResolutionNotes` | `nvarchar(2000)` | NULL |  | Domain property for ResolutionNotes. |
| `ResponsiblePersonName` | `nvarchar(150)` | **NOT NULL** |  | Domain property for ResponsiblePersonName. |
| `StatusCode` | `nvarchar(50)` | **NOT NULL** |  | Current lifecycle state code in the workflow engine. |
| `TargetCompletionDate` | `datetime2` | **NOT NULL** |  | Domain property for TargetCompletionDate. |
| `ValidationTypeCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for ValidationTypeCode. |
| `WorkplaceMonitoringSiteVisitId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_WorkplaceMonitoringActionPlan_WorkplaceMonitoringSiteVisit_WorkplaceMonitoringSiteVisitId` | `WorkplaceMonitoringSiteVisitId` | `dbo.WorkplaceMonitoringSiteVisit` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_WorkplaceMonitoringActionPlan_IsAtRisk` | `IsAtRisk` | No |
| `IX_WorkplaceMonitoringActionPlan_StatusCode` | `StatusCode` | No |
| `IX_WorkplaceMonitoringActionPlan_ValidationTypeCode` | `ValidationTypeCode` | No |
| `IX_WorkplaceMonitoringActionPlan_WorkplaceMonitoringSiteVisitId` | `WorkplaceMonitoringSiteVisitId` | No |

---

### <a id="workplacemonitoringcompliancesurvey"></a> `dbo.WorkplaceMonitoringComplianceSurvey`

**Description:** 10-Point Statutory Compliance Survey Questions answered during the visit.  
**CLR Model:** `Nsdms.Domain.Entities.WorkplaceMonitoringComplianceSurvey`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `Answer` | `nvarchar(20)` | **NOT NULL** |  | Domain property for Answer. |
| `Category` | `nvarchar(max)` | **NOT NULL** |  | Domain property for Category. |
| `Comments` | `nvarchar(1000)` | NULL |  | Domain property for Comments. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `NonComplianceRisk` | `bit` | **NOT NULL** |  | Domain property for NonComplianceRisk. |
| `QuestionNumber` | `int` | **NOT NULL** |  | Domain property for QuestionNumber. |
| `QuestionText` | `nvarchar(500)` | **NOT NULL** |  | Domain property for QuestionText. |
| `SurveyCategory` | `nvarchar(50)` | **NOT NULL** |  | Domain property for SurveyCategory. |
| `WorkplaceMonitoringSiteVisitId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_WorkplaceMonitoringComplianceSurvey_WorkplaceMonitoringSiteVisit_WorkplaceMonitoringSiteVisitId` | `WorkplaceMonitoringSiteVisitId` | `dbo.WorkplaceMonitoringSiteVisit` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_WorkplaceMonitoringComplianceSurvey_NonComplianceRisk` | `NonComplianceRisk` | No |
| `IX_WorkplaceMonitoringComplianceSurvey_SurveyCategory` | `SurveyCategory` | No |
| `IX_WorkplaceMonitoringComplianceSurvey_WorkplaceMonitoringSiteVisitId` | `WorkplaceMonitoringSiteVisitId` | No |

---

### <a id="workplacemonitoringlearnersurvey"></a> `dbo.WorkplaceMonitoringLearnerSurvey`

**Description:** Confidential Learner Interview Log conducted on-site.  
**CLR Model:** `Nsdms.Domain.Entities.WorkplaceMonitoringLearnerSurvey`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CompanyLearnerId` | `int` | NULL | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `ExposedToFullCurriculum` | `bit` | **NOT NULL** |  | Domain property for ExposedToFullCurriculum. |
| `HasQualifiedMentor` | `bit` | **NOT NULL** |  | Domain property for HasQualifiedMentor. |
| `HasRequiredPPE` | `bit` | **NOT NULL** |  | Domain property for HasRequiredPPE. |
| `LearnerComments` | `nvarchar(2000)` | NULL |  | Domain property for LearnerComments. |
| `LearnerName` | `nvarchar(150)` | **NOT NULL** |  | Domain property for LearnerName. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `ReceivedToolbox` | `bit` | **NOT NULL** |  | Domain property for ReceivedToolbox. |
| `ReceivesStipendWage` | `bit` | **NOT NULL** |  | Domain property for ReceivesStipendWage. |
| `SatisfiedWithTraining` | `bit` | **NOT NULL** |  | Domain property for SatisfiedWithTraining. |
| `WorkplaceMonitoringSiteVisitId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_WorkplaceMonitoringLearnerSurvey_CompanyLearner_CompanyLearnerId` | `CompanyLearnerId` | `dbo.CompanyLearner` | `Restrict` |
| `FK_WorkplaceMonitoringLearnerSurvey_WorkplaceMonitoringSiteVisit_WorkplaceMonitoringSiteVisitId` | `WorkplaceMonitoringSiteVisitId` | `dbo.WorkplaceMonitoringSiteVisit` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_WorkplaceMonitoringLearnerSurvey_CompanyLearnerId` | `CompanyLearnerId` | No |
| `IX_WorkplaceMonitoringLearnerSurvey_WorkplaceMonitoringSiteVisitId` | `WorkplaceMonitoringSiteVisitId` | No |

---

### <a id="workplacemonitoringmitigationplan"></a> `dbo.WorkplaceMonitoringMitigationPlan`

**Description:** Specific risk mitigation plan for high-risk non-compliance findings.  
**CLR Model:** `Nsdms.Domain.Entities.WorkplaceMonitoringMitigationPlan`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `IdentifiedRisk` | `nvarchar(500)` | **NOT NULL** |  | Domain property for IdentifiedRisk. |
| `MitigationSteps` | `nvarchar(2000)` | **NOT NULL** |  | Domain property for MitigationSteps. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `ReviewerNotes` | `nvarchar(2000)` | NULL |  | Domain property for ReviewerNotes. |
| `StatusCode` | `nvarchar(50)` | **NOT NULL** |  | Current lifecycle state code in the workflow engine. |
| `TargetResolutionDate` | `datetime2` | **NOT NULL** |  | Domain property for TargetResolutionDate. |
| `WorkplaceMonitoringActionPlanId` | `int` | NULL | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `WorkplaceMonitoringSiteVisitId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_WorkplaceMonitoringMitigationPlan_WorkplaceMonitoringActionPlan_WorkplaceMonitoringActionPlanId` | `WorkplaceMonitoringActionPlanId` | `dbo.WorkplaceMonitoringActionPlan` | `Restrict` |
| `FK_WorkplaceMonitoringMitigationPlan_WorkplaceMonitoringSiteVisit_WorkplaceMonitoringSiteVisitId` | `WorkplaceMonitoringSiteVisitId` | `dbo.WorkplaceMonitoringSiteVisit` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_WorkplaceMonitoringMitigationPlan_StatusCode` | `StatusCode` | No |
| `IX_WorkplaceMonitoringMitigationPlan_WorkplaceMonitoringActionPlanId` | `WorkplaceMonitoringActionPlanId` | No |
| `IX_WorkplaceMonitoringMitigationPlan_WorkplaceMonitoringSiteVisitId` | `WorkplaceMonitoringSiteVisitId` | No |

---

### <a id="workplacemonitoringsitevisit"></a> `dbo.WorkplaceMonitoringSiteVisit`

**Description:** Comprehensive Workplace Monitoring and Quality Assurance Site Visit.  
**CLR Model:** `Nsdms.Domain.Entities.WorkplaceMonitoringSiteVisit`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `ApprovalComments` | `nvarchar(2000)` | NULL |  | Domain property for ApprovalComments. |
| `ApprovalDate` | `datetime2` | NULL |  | Domain property for ApprovalDate. |
| `ApprovedByUserId` | `nvarchar(100)` | NULL |  | Domain property for ApprovedByUserId. |
| `CloUserId` | `nvarchar(100)` | NULL |  | Domain property for CloUserId. |
| `ContactPersonId` | `int` | **NOT NULL** | 🔗 **FK** | Mandatory Contact Person at the employer site during the visit. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `CrmUserId` | `nvarchar(100)` | NULL |  | Domain property for CrmUserId. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `MonitoringDate` | `datetime2` | **NOT NULL** |  | Domain property for MonitoringDate. |
| `MonitoringStatusCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for MonitoringStatusCode. |
| `NonComplianceApprovalDate` | `datetime2` | NULL |  | Domain property for NonComplianceApprovalDate. |
| `NonComplianceHoldingArea` | `bit` | **NOT NULL** |  | Domain property for NonComplianceHoldingArea. |
| `NonComplianceNotes` | `nvarchar(2000)` | NULL |  | Domain property for NonComplianceNotes. |
| `NonComplianceSubmittedDate` | `datetime2` | NULL |  | Domain property for NonComplianceSubmittedDate. |
| `NonCompliancesIdentified` | `bit` | **NOT NULL** |  | Domain property for NonCompliancesIdentified. |
| `OrganisationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `SignOffState` | `bit` | **NOT NULL** |  | Domain property for SignOffState. |
| `StatusCode` | `nvarchar(max)` | **NOT NULL** |  | Current lifecycle state code in the workflow engine. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_WorkplaceMonitoringSiteVisit_Person_ContactPersonId` | `ContactPersonId` | `dbo.Person` | `Restrict` |
| `FK_WorkplaceMonitoringSiteVisit_Organisation_OrganisationId` | `OrganisationId` | `dbo.Organisation` | `Restrict` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_WorkplaceMonitoringSiteVisit_ContactPersonId` | `ContactPersonId` | No |
| `IX_WorkplaceMonitoringSiteVisit_MonitoringDate` | `MonitoringDate` | No |
| `IX_WorkplaceMonitoringSiteVisit_MonitoringStatusCode` | `MonitoringStatusCode` | No |
| `IX_WorkplaceMonitoringSiteVisit_NonCompliancesIdentified` | `NonCompliancesIdentified` | No |
| `IX_WorkplaceMonitoringSiteVisit_OrganisationId` | `OrganisationId` | No |

---

### <a id="wspdispute"></a> `dbo.WspDispute`

**Description:** Dispute logged regarding Workplace Skills Plan (WSP) approval or committee sign-off.  
**CLR Model:** `Nsdms.Domain.Entities.WspDispute`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `Description` | `nvarchar(2000)` | **NOT NULL** |  | Detailed description and contextual notes. |
| `DisputeReasonCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for DisputeReasonCode. |
| `DisputeReferenceNumber` | `nvarchar(50)` | **NOT NULL** |  | Domain property for DisputeReferenceNumber. |
| `DisputeStatusCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for DisputeStatusCode. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `OrganisationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `ResolutionDate` | `datetime2` | NULL |  | Domain property for ResolutionDate. |
| `ResolutionNotes` | `nvarchar(2000)` | NULL |  | Domain property for ResolutionNotes. |
| `WspSubmissionId` | `int` | NULL | 🔗 **FK** | Foreign key relational reference to parent entity. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_WspDispute_Organisation_OrganisationId` | `OrganisationId` | `dbo.Organisation` | `Restrict` |
| `FK_WspDispute_WspSubmission_WspSubmissionId` | `WspSubmissionId` | `dbo.WspSubmission` | `Restrict` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_WspDispute_DisputeReferenceNumber` | `DisputeReferenceNumber` | No |
| `IX_WspDispute_DisputeStatusCode` | `DisputeStatusCode` | No |
| `IX_WspDispute_OrganisationId` | `OrganisationId` | No |

---

### <a id="wspemploymentsummary"></a> `dbo.WspEmploymentSummary`

**Description:** Employment profile demographic breakdown by occupational category (SETMIS Form 500).  
**CLR Model:** `Nsdms.Domain.Entities.WspEmploymentSummary`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DisabledCount` | `int` | **NOT NULL** |  | Total headcount of employees declared with disabilities. |
| `FemaleAfrican` | `int` | **NOT NULL** |  | Headcount of African female employees in this occupational level. |
| `FemaleColoured` | `int` | **NOT NULL** |  | Headcount of Coloured female employees. |
| `FemaleIndian` | `int` | **NOT NULL** |  | Headcount of Indian female employees. |
| `FemaleWhite` | `int` | **NOT NULL** |  | Headcount of White female employees. |
| `MaleAfrican` | `int` | **NOT NULL** |  | Headcount of African male employees in this occupational level. |
| `MaleColoured` | `int` | **NOT NULL** |  | Headcount of Coloured male employees. |
| `MaleIndian` | `int` | **NOT NULL** |  | Headcount of Indian male employees. |
| `MaleWhite` | `int` | **NOT NULL** |  | Headcount of White male employees. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `OccupationalCategory` | `nvarchar(100)` | NULL |  | Major occupational level title (e.g. Managers, Professionals, Technicians, Clerical, Artisans, Elementary). |
| `OfoCode` | `nvarchar(20)` | NULL |  | Organising Framework for Occupations (OFO) 6-digit classification code. |
| `TotalEmployees` | `int` | **NOT NULL** |  | Total headcount sum of all employees in this category. |
| `WspSubmissionId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the parent WspSubmission. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_WspEmploymentSummary_WspSubmission_WspSubmissionId` | `WspSubmissionId` | `dbo.WspSubmission` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_WspEmploymentSummary_OfoCode` | `OfoCode` | No |
| `IX_WspEmploymentSummary_WspSubmissionId` | `WspSubmissionId` | No |

---

### <a id="wspsignoffattestation"></a> `dbo.WspSignoffAttestation`

**Description:** Cryptographically sealed attestation for WSP/ATR multi-party sign-off (SDF, Labour Union, CEO).  
**CLR Model:** `Nsdms.Domain.Entities.WspSignoffAttestation`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AttestationStatement` | `nvarchar(max)` | **NOT NULL** |  | Domain property for AttestationStatement. |
| `AttestationStatusCode` | `nvarchar(max)` | **NOT NULL** |  | Domain property for AttestationStatusCode. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DigitalSecuritySeal` | `nvarchar(128)` | **NOT NULL** |  | Domain property for DigitalSecuritySeal. |
| `DisputeLogged` | `bit` | **NOT NULL** |  | Domain property for DisputeLogged. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `OtpToken` | `nvarchar(max)` | NULL |  | Domain property for OtpToken. |
| `OtpVerifiedAt` | `datetime2` | NULL |  | Domain property for OtpVerifiedAt. |
| `SignerEmail` | `nvarchar(200)` | **NOT NULL** |  | Domain property for SignerEmail. |
| `SignerFullName` | `nvarchar(250)` | **NOT NULL** |  | Domain property for SignerFullName. |
| `SignerNotes` | `nvarchar(max)` | NULL |  | Domain property for SignerNotes. |
| `SignerRoleCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for SignerRoleCode. |
| `SignerRsaId` | `nvarchar(max)` | NULL |  | Domain property for SignerRsaId. |
| `SignoffDate` | `datetime2` | **NOT NULL** |  | Domain property for SignoffDate. |
| `UnionName` | `nvarchar(max)` | NULL |  | Domain property for UnionName. |
| `WspDisputeId` | `int` | NULL | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `WspSubmissionId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_WspSignoffAttestation_WspDispute_WspDisputeId` | `WspDisputeId` | `dbo.WspDispute` | `ClientSetNull` |
| `FK_WspSignoffAttestation_WspSubmission_WspSubmissionId` | `WspSubmissionId` | `dbo.WspSubmission` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_WspSignoffAttestation_AttestationStatusCode` | `AttestationStatusCode` | No |
| `IX_WspSignoffAttestation_SignerRoleCode` | `SignerRoleCode` | No |
| `IX_WspSignoffAttestation_WspSubmissionId` | `WspSubmissionId` | No |

---

### <a id="wspskillsgap"></a> `dbo.WspSkillsGap`

**Description:** Critical and Scarce Skills Gap identified during WSP compilation.  
**CLR Model:** `Nsdms.Domain.Entities.WspSkillsGap`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `FinancialYear` | `int` | **NOT NULL** |  | Domain property for FinancialYear. |
| `HardToFillVacanciesCount` | `int` | **NOT NULL** |  | Domain property for HardToFillVacanciesCount. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `OccupationTitle` | `nvarchar(200)` | **NOT NULL** |  | Domain property for OccupationTitle. |
| `OfoCode` | `nvarchar(50)` | **NOT NULL** |  | Domain property for OfoCode. |
| `OrganisationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |
| `SkillsGapReason` | `nvarchar(1000)` | **NOT NULL** |  | Domain property for SkillsGapReason. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_WspSkillsGap_Organisation_OrganisationId` | `OrganisationId` | `dbo.Organisation` | `Restrict` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_WspSkillsGap_FinancialYear` | `FinancialYear` | No |
| `IX_WspSkillsGap_OfoCode` | `OfoCode` | No |
| `IX_WspSkillsGap_OrganisationId` | `OrganisationId` | No |

---

### <a id="wspstrategicpriority"></a> `dbo.WspStrategicPriority`

**Description:** Strategic priorities and alignment with National Skills Development Plan (NSDP) goals in WSP submissions.  
**CLR Model:** `Nsdms.Domain.Entities.WspStrategicPriority`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `AlignmentDescription` | `nvarchar(max)` | **NOT NULL** |  | Domain property for AlignmentDescription. |
| `AllocatedBudget` | `decimal(18,2)` | **NOT NULL** |  | Domain property for AllocatedBudget. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `IsAlignedWithNsdp` | `bit` | **NOT NULL** |  | Domain property for IsAlignedWithNsdp. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `PriorityCode` | `nvarchar(100)` | **NOT NULL** |  | Domain property for PriorityCode. |
| `StrategicObjective` | `nvarchar(max)` | **NOT NULL** |  | Domain property for StrategicObjective. |
| `WspId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_WspStrategicPriority_WspSubmission_WspId` | `WspId` | `dbo.WspSubmission` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_WspStrategicPriority_WspId` | `WspId` | No |

---

### <a id="wspstrategicskillsgap"></a> `dbo.WspStrategicSkillsGap`

**Description:** Captures identified organizational skills gaps, root causes, and targeted interventions for WSP/ATR submissions.  
**CLR Model:** `Nsdms.Domain.Entities.WspStrategicSkillsGap`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CauseOfGap` | `nvarchar(max)` | NULL |  | Domain property for CauseOfGap. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `EstimatedBudget` | `decimal(18,2)` | **NOT NULL** |  | Domain property for EstimatedBudget. |
| `IsActive` | `bit` | **NOT NULL** |  | Indicates whether the record is active and operational. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `OccupationTitle` | `nvarchar(150)` | **NOT NULL** |  | Domain property for OccupationTitle. |
| `OfoCode` | `nvarchar(max)` | NULL |  | Domain property for OfoCode. |
| `PlannedIntervention` | `nvarchar(max)` | NULL |  | Domain property for PlannedIntervention. |
| `PriorityLevel` | `nvarchar(50)` | **NOT NULL** |  | Domain property for PriorityLevel. |
| `SkillGapDescription` | `nvarchar(max)` | **NOT NULL** |  | Domain property for SkillGapDescription. |
| `TargetLearnerCount` | `int` | **NOT NULL** |  | Domain property for TargetLearnerCount. |
| `WspId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_WspStrategicSkillsGap_WspSubmission_WspId` | `WspId` | `dbo.WspSubmission` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_WspStrategicSkillsGap_IsActive` | `IsActive` | No |
| `IX_WspStrategicSkillsGap_WspId` | `WspId` | No |

---

### <a id="wspsubmission"></a> `dbo.WspSubmission`

**Description:** Mandatory Grant Workplace Skills Plan (WSP) and Annual Training Report (ATR) submissions.  
**CLR Model:** `Nsdms.Domain.Entities.WspSubmission`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CompletedSignoffCount` | `int` | **NOT NULL** |  | Total count of completed, OTP-verified signatory attestations recorded. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `DisputeLogged` | `bit` | **NOT NULL** |  | Flag indicating whether a formal labour dispute has been lodged against this submission. |
| `EmployeeCount` | `int` | **NOT NULL** |  | Total headcount of employees declared in the organisation profile. |
| `FinYear` | `int` | **NOT NULL** |  | Statutory financial/scheme year for this submission (e.g. 2026). |
| `IsSignoffQuorumMet` | `bit` | **NOT NULL** |  | Indicates whether the constitutional sign-off quorum has been satisfied. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `OrganisationId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the submitting Employer Organisation. |
| `PlannedTrainingBudget` | `decimal(18,2)` | **NOT NULL** |  | Total aggregate training budget planned for the upcoming financial year in ZAR. |
| `ReferenceNumber` | `nvarchar(50)` | **NOT NULL** |  | Unique statutory WSP submission reference tracking number. |
| `RequiredSignoffCount` | `int` | **NOT NULL** |  | Domain property for RequiredSignoffCount. |
| `SignoffDigitalSecuritySeal` | `nvarchar(max)` | NULL |  | SHA-256 digital security seal certifying completion of all requisite multi-party attestations. |
| `SubmissionDate` | `datetime2` | NULL |  | Official date and time when the submission was locked and signed off. |
| `WspApprovalStatusCode` | `nvarchar(15)` | NULL |  | Current workflow review and approval status code (e.g. Draft, Submitted, Approved, Rejected). |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_WspSubmission_Organisation_OrganisationId` | `OrganisationId` | `dbo.Organisation` | `Restrict` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_WspSubmission_FinYear` | `FinYear` | No |
| `IX_WspSubmission_OrganisationId` | `OrganisationId` | No |
| `IX_WspSubmission_ReferenceNumber` | `ReferenceNumber` | No |
| `IX_WspSubmission_WspApprovalStatusCode` | `WspApprovalStatusCode` | No |

---

### <a id="wsptrainingimpactsurvey"></a> `dbo.WspTrainingImpactSurvey`

**Description:** Qualitative training impact questionnaire evaluating the business effectiveness of previous year training.  
**CLR Model:** `Nsdms.Domain.Entities.WspTrainingImpactSurvey`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `int` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `EvidenceDocumentUrl` | `nvarchar(max)` | NULL |  | Domain property for EvidenceDocumentUrl. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `QualitativeImpactNotes` | `nvarchar(max)` | NULL |  | Domain property for QualitativeImpactNotes. |
| `QuestionText` | `nvarchar(max)` | **NOT NULL** |  | Domain property for QuestionText. |
| `RatingScore` | `int` | **NOT NULL** |  | Domain property for RatingScore. |
| `SurveyCategory` | `nvarchar(100)` | **NOT NULL** |  | Domain property for SurveyCategory. |
| `WspId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key relational reference to parent entity. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_WspTrainingImpactSurvey_WspSubmission_WspId` | `WspId` | `dbo.WspSubmission` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_WspTrainingImpactSurvey_WspId` | `WspId` | No |

---

### <a id="wsptrainingplan"></a> `dbo.WspTrainingPlan`

**Description:** Planned learning and development interventions submitted in the annual Workplace Skills Plan.  
**CLR Model:** `Nsdms.Domain.Entities.WspTrainingPlan`  
**Primary Key:** `Id`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Id` | `bigint` | **NOT NULL** | 🔑 **PK** | Auto-generated integer primary key identifier. |
| `BeneficiaryCount` | `int` | **NOT NULL** |  | Total number of planned employee / unemployed beneficiaries. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the record was initially created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that created the record. |
| `EstimatedCost` | `decimal(18,2)` | **NOT NULL** |  | Total estimated investment cost budgeted for delivery in ZAR. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the record was last updated. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | User identifier or system process that last updated the record. |
| `NqfLevel` | `int` | NULL |  | Targeted National Qualifications Framework (NQF) level descriptor. |
| `ProgrammeTypeCode` | `nvarchar(15)` | NULL |  | Learning programme type code (e.g. Learnership, Apprenticeship, SkillsProgramme, ShortCourse). |
| `WspSubmissionId` | `int` | **NOT NULL** | 🔗 **FK** | Foreign key referencing the parent WspSubmission. |

#### Foreign Key Constraints

| Constraint Name | Foreign Columns | Principal Table | Delete Rule |
| :--- | :--- | :--- | :--- |
| `FK_WspTrainingPlan_WspSubmission_WspSubmissionId` | `WspSubmissionId` | `dbo.WspSubmission` | `Cascade` |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_WspTrainingPlan_ProgrammeTypeCode` | `ProgrammeTypeCode` | No |
| `IX_WspTrainingPlan_WspSubmissionId` | `WspSubmissionId` | No |

---

### <a id="abetbandtype"></a> `lookup.AbetBandType`

**Description:** NLRD Adult Basic Education and Training Band classifications (Field: ABET_BAND_ID - 1 to 5).  
**CLR Model:** `Nsdms.Domain.Entities.AbetBandType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_AbetBandType_Active` | `Active` | No |
| `IX_AbetBandType_Name` | `Name` | No |

---

### <a id="alternateidtype"></a> `lookup.AlternateIdType`

**Description:** SETMIS statutory alternate identification types (Field: Alternate_Id_Type_Id - e.g. Passport, Birth Certificate, Work Permit).  
**CLR Model:** `Nsdms.Domain.Entities.AlternateIdType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_AlternateIdType_Active` | `Active` | No |
| `IX_AlternateIdType_Name` | `Name` | No |

---

### <a id="categorytype"></a> `lookup.CategoryType`

**Description:** Employer entity levy compliance categories (e.g. Levy Paying, Non-Levy Paying, Government/Exempt).  
**CLR Model:** `Nsdms.Domain.Entities.CategoryType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_CategoryType_Active` | `Active` | No |
| `IX_CategoryType_Name` | `Name` | No |

---

### <a id="chambertype"></a> `lookup.ChamberType`

**Description:** MerSETA sub-sector chambers (Auto, Metal & Engineering, Plastics, Motor Retail).  
**CLR Model:** `Nsdms.Domain.Entities.ChamberType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_ChamberType_Active` | `Active` | No |
| `IX_ChamberType_Name` | `Name` | No |

---

### <a id="citizenstatustype"></a> `lookup.CitizenStatusType`

**Description:** SETMIS South African citizenship and residency legal status classifications (Field: Citizen_Resident_Status_Code - SA, PR, D, O, U).  
**CLR Model:** `Nsdms.Domain.Entities.CitizenStatusType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_CitizenStatusType_Active` | `Active` | No |
| `IX_CitizenStatusType_Name` | `Name` | No |

---

### <a id="communicatingratingtype"></a> `lookup.CommunicatingRatingType`

**Description:** Washington Group Communication functional rating (Field: Communicating_Rating_Id - 1: No difficulty to 6: Cannot yet be determined).  
**CLR Model:** `Nsdms.Domain.Entities.CommunicatingRatingType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_CommunicatingRatingType_Active` | `Active` | No |
| `IX_CommunicatingRatingType_Name` | `Name` | No |

---

### <a id="companysizetype"></a> `lookup.CompanySizeType`

**Description:** Enterprise headcount and turnover size bands (Small: 0-49, Medium: 50-149, Large: 150+).  
**CLR Model:** `Nsdms.Domain.Entities.CompanySizeType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_CompanySizeType_Active` | `Active` | No |
| `IX_CompanySizeType_Name` | `Name` | No |

---

### <a id="countrytype"></a> `lookup.CountryType`

**Description:** Country classification reference codes (Field: Country_Code - ISO-3166 2-Letter Alpha codes covering 249 global jurisdictions).  
**CLR Model:** `Nsdms.Domain.Entities.CountryType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_CountryType_Active` | `Active` | No |
| `IX_CountryType_Name` | `Name` | No |

---

### <a id="designationstructurestatustype"></a> `lookup.DesignationStructureStatusType`

**Description:** Assessor and Moderator ETQA registration structure status codes (Field: Designation_Structure_Status_Id - Registered, Deregistered, etc.).  
**CLR Model:** `Nsdms.Domain.Entities.DesignationStructureStatusType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_DesignationStructureStatusType_Active` | `Active` | No |
| `IX_DesignationStructureStatusType_Name` | `Name` | No |

---

### <a id="designationtype"></a> `lookup.DesignationType`

**Description:** Assessor and Moderator statutory designation types (Field: Designation_Id - 1: Assessor, 0: Moderator).  
**CLR Model:** `Nsdms.Domain.Entities.DesignationType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_DesignationType_Active` | `Active` | No |
| `IX_DesignationType_Name` | `Name` | No |

---

### <a id="disabilitytype"></a> `lookup.DisabilityType`

**Description:** Disability impairment classifications per Employment Equity & SETMIS standards.  
**CLR Model:** `Nsdms.Domain.Entities.DisabilityType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_DisabilityType_Active` | `Active` | No |
| `IX_DisabilityType_Name` | `Name` | No |

---

### <a id="economicstatustype"></a> `lookup.EconomicStatusType`

**Description:** Learner / employee economic employment status classifications (Field: Economic_Status_Id - Employed, Unemployed, etc.).  
**CLR Model:** `Nsdms.Domain.Entities.EconomicStatusType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_EconomicStatusType_Active` | `Active` | No |
| `IX_EconomicStatusType_Name` | `Name` | No |

---

### <a id="employerapprovalstatustype"></a> `lookup.EmployerApprovalStatusType`

**Description:** Employer workplace approval hosting eligibility status codes (Field: Employer_Approval_Status_Id - Active, Inactive, Legacy).  
**CLR Model:** `Nsdms.Domain.Entities.EmployerApprovalStatusType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_EmployerApprovalStatusType_Active` | `Active` | No |
| `IX_EmployerApprovalStatusType_Name` | `Name` | No |

---

### <a id="enrolmentstatusreasontype"></a> `lookup.EnrolmentStatusReasonType`

**Description:** Reasons for learner enrolment status transitions (Field: Enrolment_Status_Reason_Id - Medical, Financial, Social, etc.).  
**CLR Model:** `Nsdms.Domain.Entities.EnrolmentStatusReasonType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_EnrolmentStatusReasonType_Active` | `Active` | No |
| `IX_EnrolmentStatusReasonType_Name` | `Name` | No |

---

### <a id="enrolmentstatustype"></a> `lookup.EnrolmentStatusType`

**Description:** Learner agreement registration lifecycle status codes (Field: Enrolment_Status_Id - Enrolled, Achieved, Certificated, etc.).  
**CLR Model:** `Nsdms.Domain.Entities.EnrolmentStatusType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_EnrolmentStatusType_Active` | `Active` | No |
| `IX_EnrolmentStatusType_Name` | `Name` | No |

---

### <a id="enrolmenttype"></a> `lookup.EnrolmentType`

**Description:** Learner enrolment intake and delivery modality types (Field: Enrolment_Type_Id - Contact, Distance, Mixed Mode, etc.).  
**CLR Model:** `Nsdms.Domain.Entities.EnrolmentType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_EnrolmentType_Active` | `Active` | No |
| `IX_EnrolmentType_Name` | `Name` | No |

---

### <a id="equitytype"></a> `lookup.EquityType`

**Description:** Statutory Employment Equity / BBBEE racial demographic classifications (Field: Equity_Code - BA, BC, BI, Wh, U).  
**CLR Model:** `Nsdms.Domain.Entities.EquityType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_EquityType_Active` | `Active` | No |
| `IX_EquityType_Name` | `Name` | No |

---

### <a id="fundingtype"></a> `lookup.FundingType`

**Description:** Learning intervention funding source classifications (Field: Funding_Id - SETA funded, Employer funded, Learner funded, etc.).  
**CLR Model:** `Nsdms.Domain.Entities.FundingType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_FundingType_Active` | `Active` | No |
| `IX_FundingType_Name` | `Name` | No |

---

### <a id="gendertype"></a> `lookup.GenderType`

**Description:** Gender classification reference lookups (Field: Gender_Code - F, M, U).  
**CLR Model:** `Nsdms.Domain.Entities.GenderType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_GenderType_Active` | `Active` | No |
| `IX_GenderType_Name` | `Name` | No |

---

### <a id="granttypetype"></a> `lookup.GrantTypeType`

**Description:** MerSETA Discretionary and Mandatory grant allocation funding types.  
**CLR Model:** `Nsdms.Domain.Entities.GrantTypeType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_GrantTypeType_Active` | `Active` | No |
| `IX_GrantTypeType_Name` | `Name` | No |

---

### <a id="hearingratingtype"></a> `lookup.HearingRatingType`

**Description:** Washington Group Hearing functional rating (Field: Hearing_Rating_Id - 1: No difficulty to 6: Cannot yet be determined).  
**CLR Model:** `Nsdms.Domain.Entities.HearingRatingType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_HearingRatingType_Active` | `Active` | No |
| `IX_HearingRatingType_Name` | `Name` | No |

---

### <a id="homelanguagetype"></a> `lookup.HomeLanguageType`

**Description:** Official South African home language classifications (Field: Home_Language_Code - 14 statutory codes including SASL).  
**CLR Model:** `Nsdms.Domain.Entities.HomeLanguageType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_HomeLanguageType_Active` | `Active` | No |
| `IX_HomeLanguageType_Name` | `Name` | No |

---

### <a id="honoursclasstype"></a> `lookup.HonoursClassType`

**Description:** Higher Education and Umalusi academic achievement classification distinctions (Field: HONOURS_CLASS_ID - Cum Laude, Honours, etc.).  
**CLR Model:** `Nsdms.Domain.Entities.HonoursClassType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_HonoursClassType_Active` | `Active` | No |
| `IX_HonoursClassType_Name` | `Name` | No |

---

### <a id="internshipstatustype"></a> `lookup.InternshipStatusType`

**Description:** Work Integrated Learning (WIL) and Internship lifecycle status (Field: Internship_Status_Id - Active, Completed, Discontinued).  
**CLR Model:** `Nsdms.Domain.Entities.InternshipStatusType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_InternshipStatusType_Active` | `Active` | No |
| `IX_InternshipStatusType_Name` | `Name` | No |

---

### <a id="interventiontype"></a> `lookup.InterventionType`

**Description:** Targeted skills development intervention categories.  
**CLR Model:** `Nsdms.Domain.Entities.InterventionType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_InterventionType_Active` | `Active` | No |
| `IX_InterventionType_Name` | `Name` | No |

---

### <a id="learnerevidencetype"></a> `lookup.LearnerEvidenceType`

**Description:** Portfolio of Evidence (PoE) digital artifact and evidence document types.  
**CLR Model:** `Nsdms.Domain.Entities.LearnerEvidenceType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_LearnerEvidenceType_Active` | `Active` | No |
| `IX_LearnerEvidenceType_Name` | `Name` | No |

---

### <a id="learningprogrammetype"></a> `lookup.LearningProgrammeType`

**Description:** Learning intervention modality types (Field: Learning_Programme_Type_Id - Learnership, Apprenticeship, Skills Programme, Internship, Bursary, etc.).  
**CLR Model:** `Nsdms.Domain.Entities.LearningProgrammeType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_LearningProgrammeType_Active` | `Active` | No |
| `IX_LearningProgrammeType_Name` | `Name` | No |

---

### <a id="nationalitytype"></a> `lookup.NationalityType`

**Description:** SETMIS regional nationality classifications (Field: Nationality_Code - 23 statutory nationality codes).  
**CLR Model:** `Nsdms.Domain.Entities.NationalityType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_NationalityType_Active` | `Active` | No |
| `IX_NationalityType_Name` | `Name` | No |

---

### <a id="nonnqfinterventionstatustype"></a> `lookup.NonNqfInterventionStatusType`

**Description:** Non-NQF skills intervention registration status codes (Field: Non_NQF_Interv_Status_Id - Active, Inactive, Provisional).  
**CLR Model:** `Nsdms.Domain.Entities.NonNqfInterventionStatusType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_NonNqfInterventionStatusType_Active` | `Active` | No |
| `IX_NonNqfInterventionStatusType_Name` | `Name` | No |

---

### <a id="ofocodetype"></a> `lookup.OfoCodeType`

**Description:** DHET Organising Framework for Occupations occupational classification codes (Field: OFO_Code - 1,454 statutory codes).  
**CLR Model:** `Nsdms.Domain.Entities.OfoCodeType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_OfoCodeType_Active` | `Active` | No |
| `IX_OfoCodeType_Name` | `Name` | No |
| `IX_OfoCodeType_Code_Name` | `Code, Name` | No |

---

### <a id="organisationtype"></a> `lookup.OrganisationType`

**Description:** Enterprise legal constitution types (e.g. Pty Ltd, Close Corporation, Public Entity, NGO/NPO).  
**CLR Model:** `Nsdms.Domain.Entities.OrganisationType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_OrganisationType_Active` | `Active` | No |
| `IX_OrganisationType_Name` | `Name` | No |

---

### <a id="partoftype"></a> `lookup.PartOfType`

**Description:** Programme articulation hierarchy standing (Field: Part_Of_Id - Stand-alone, Part of Qualification, Part of Learnership, etc.).  
**CLR Model:** `Nsdms.Domain.Entities.PartOfType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_PartOfType_Active` | `Active` | No |
| `IX_PartOfType_Name` | `Name` | No |

---

### <a id="popiactstatustype"></a> `lookup.PopiActStatusType`

**Description:** Protection of Personal Information Act compliance consent status (Field: POPI_Act_Status_ID - Agree, Disagree, N/A).  
**CLR Model:** `Nsdms.Domain.Entities.PopiActStatusType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_PopiActStatusType_Active` | `Active` | No |
| `IX_PopiActStatusType_Name` | `Name` | No |

---

### <a id="providerclasstype"></a> `lookup.ProviderClassType`

**Description:** Skills Development Provider institutional classification (Field: Provider_Class_Id - Public, Private, NGO, Foreign, etc.).  
**CLR Model:** `Nsdms.Domain.Entities.ProviderClassType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_ProviderClassType_Active` | `Active` | No |
| `IX_ProviderClassType_Name` | `Name` | No |

---

### <a id="providerstatustype"></a> `lookup.ProviderStatusType`

**Description:** Skills Development Provider ETQA accreditation standing codes (Field: Provider_Status_Id - Accredited, Registered, Closed, etc.).  
**CLR Model:** `Nsdms.Domain.Entities.ProviderStatusType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_ProviderStatusType_Active` | `Active` | No |
| `IX_ProviderStatusType_Name` | `Name` | No |

---

### <a id="providertype"></a> `lookup.ProviderType`

**Description:** Skills Development Provider functional entity type (Field: Provider_Type_Id - Education, Training, Employer, NGO).  
**CLR Model:** `Nsdms.Domain.Entities.ProviderType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_ProviderType_Active` | `Active` | No |
| `IX_ProviderType_Name` | `Name` | No |

---

### <a id="provincetype"></a> `lookup.ProvinceType`

**Description:** South African provincial geopolitical regions (Field: Province_Code - 11 statutory provincial and national codes).  
**CLR Model:** `Nsdms.Domain.Entities.ProvinceType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_ProvinceType_Active` | `Active` | No |
| `IX_ProvinceType_Name` | `Name` | No |

---

### <a id="qualificationtypetype"></a> `lookup.QualificationTypeType`

**Description:** SAQA National Qualifications Framework qualification formal types (Field: QUALIFICATION_TYPE_ID - National Certificate, Diploma, Occupational Certificate, etc.).  
**CLR Model:** `Nsdms.Domain.Entities.QualificationTypeType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_QualificationTypeType_Active` | `Active` | No |
| `IX_QualificationTypeType_Name` | `Name` | No |

---

### <a id="rememberingratingtype"></a> `lookup.RememberingRatingType`

**Description:** Washington Group Memory and Cognitive functional rating (Field: Remembering_Rating_Id - 1: No difficulty to 6: Cannot yet be determined).  
**CLR Model:** `Nsdms.Domain.Entities.RememberingRatingType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_RememberingRatingType_Active` | `Active` | No |
| `IX_RememberingRatingType_Name` | `Name` | No |

---

### <a id="sectortype"></a> `lookup.SectorType`

**Description:** MerSETA industrial economic sector classifications (Auto, Metal, Motor Retail, Plastics, Tyre).  
**CLR Model:** `Nsdms.Domain.Entities.SectorType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_SectorType_Active` | `Active` | No |
| `IX_SectorType_Name` | `Name` | No |

---

### <a id="seeingratingtype"></a> `lookup.SeeingRatingType`

**Description:** Washington Group Vision functional rating (Field: Seeing_Rating_Id - 1: No difficulty to 6: Cannot yet be determined).  
**CLR Model:** `Nsdms.Domain.Entities.SeeingRatingType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_SeeingRatingType_Active` | `Active` | No |
| `IX_SeeingRatingType_Name` | `Name` | No |

---

### <a id="selfcareratingtype"></a> `lookup.SelfCareRatingType`

**Description:** Washington Group Self-Care functional rating (Field: Self_Care_Rating_Id - 1: No difficulty to 6: Cannot yet be determined).  
**CLR Model:** `Nsdms.Domain.Entities.SelfCareRatingType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_SelfCareRatingType_Active` | `Active` | No |
| `IX_SelfCareRatingType_Name` | `Name` | No |

---

### <a id="setatype"></a> `lookup.SetaType`

**Description:** South African Sector Education and Training Authorities (Field: SETA_Id - 21 SETAs including MerSETA).  
**CLR Model:** `Nsdms.Domain.Entities.SetaType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_SetaType_Active` | `Active` | No |
| `IX_SetaType_Name` | `Name` | No |

---

### <a id="siccodetype"></a> `lookup.SicCodeType`

**Description:** Standard Industrial Classification (SIC) 5-digit economic activity codes (Field: SIC_Code - 815 statutory codes).  
**CLR Model:** `Nsdms.Domain.Entities.SicCodeType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `ChamberCode` | `nvarchar(20)` | NULL |  | Associated merSETA Chamber Code (references lookup.ChamberType: AUTO, METAL, MOTOR, NEW_TYRE, PLASTICS, OTHER). |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |
| `SetaCode` | `nvarchar(10)` | **NOT NULL** |  | Designated statutory SETA Code (SETA 17 for merSETA, or other SETA code if out-of-scope). |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_SicCodeType_Active` | `Active` | No |
| `IX_SicCodeType_ChamberCode` | `ChamberCode` | No |
| `IX_SicCodeType_Name` | `Name` | No |
| `IX_SicCodeType_SetaCode` | `SetaCode` | No |
| `IX_SicCodeType_Code_Name` | `Code, Name` | No |

---

### <a id="sitevisitapprovalstatustype"></a> `lookup.SiteVisitApprovalStatusType`

**Description:** On-site physical facility inspection approval status codes.  
**CLR Model:** `Nsdms.Domain.Entities.SiteVisitApprovalStatusType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_SiteVisitApprovalStatusType_Active` | `Active` | No |
| `IX_SiteVisitApprovalStatusType_Name` | `Name` | No |

---

### <a id="statssaareacodetype"></a> `lookup.StatssaAreaCodeType`

**Description:** Stats SA spatial geographic sub-place and municipal area codes (Field: STATSSA_Area_Code - 22,108 statutory codes).  
**CLR Model:** `Nsdms.Domain.Entities.StatssaAreaCodeType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_StatssaAreaCodeType_Active` | `Active` | No |
| `IX_StatssaAreaCodeType_Name` | `Name` | No |
| `IX_StatssaAreaCodeType_Code_Name` | `Code, Name` | No |

---

### <a id="statustype"></a> `lookup.StatusType`

**Description:** General system and workflow operational status codes (Active, Inactive, Pending, Approved, Rejected, Suspended).  
**CLR Model:** `Nsdms.Domain.Entities.StatusType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_StatusType_Active` | `Active` | No |
| `IX_StatusType_Name` | `Name` | No |

---

### <a id="subfieldtype"></a> `lookup.SubfieldType`

**Description:** SAQA National Qualifications Framework subfield classifications (Field: Subfield_Id - 68 statutory subfields).  
**CLR Model:** `Nsdms.Domain.Entities.SubfieldType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_SubfieldType_Active` | `Active` | No |
| `IX_SubfieldType_Name` | `Name` | No |

---

### <a id="tradetestresultreasontype"></a> `lookup.TradeTestResultReasonType`

**Description:** Artisan trade test assessment specific outcome reasons (Field: Trade_Test_Result_Reason_Id).  
**CLR Model:** `Nsdms.Domain.Entities.TradeTestResultReasonType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_TradeTestResultReasonType_Active` | `Active` | No |
| `IX_TradeTestResultReasonType_Name` | `Name` | No |

---

### <a id="tradetestresulttype"></a> `lookup.TradeTestResultType`

**Description:** Artisan practical trade test assessment competency outcomes (Field: Trade_Test_Result_Id - Competent, Not yet competent).  
**CLR Model:** `Nsdms.Domain.Entities.TradeTestResultType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_TradeTestResultType_Active` | `Active` | No |
| `IX_TradeTestResultType_Name` | `Name` | No |

---

### <a id="urbanruraltype"></a> `lookup.UrbanRuralType`

**Description:** Spatial demographic urban versus rural intervention classifications (Field: Urban_Rural_ID - Urban, Rural, Unknown).  
**CLR Model:** `Nsdms.Domain.Entities.UrbanRuralType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_UrbanRuralType_Active` | `Active` | No |
| `IX_UrbanRuralType_Name` | `Name` | No |

---

### <a id="visittypetype"></a> `lookup.VisitTypeType`

**Description:** Employer on-site inspection and monitoring visit activity types.  
**CLR Model:** `Nsdms.Domain.Entities.VisitTypeType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_VisitTypeType_Active` | `Active` | No |
| `IX_VisitTypeType_Name` | `Name` | No |

---

### <a id="walkingratingtype"></a> `lookup.WalkingRatingType`

**Description:** Washington Group Mobility and Walking functional rating (Field: Walking_Rating_Id - 1: No difficulty to 6: Cannot yet be determined).  
**CLR Model:** `Nsdms.Domain.Entities.WalkingRatingType`  
**Primary Key:** `Code`

#### Columns

| Column | SQL Store Type | Nullable | Key | Description |
| :--- | :--- | :--- | :--- | :--- |
| `Code` | `nvarchar(50)` | **NOT NULL** | 🔑 **PK** | Unique alphanumeric code identifier acting as primary key. |
| `Active` | `bit` | **NOT NULL** |  | Indicates whether the lookup value is active and selectable in UI workflows. |
| `CreatedAt` | `datetime2` | **NOT NULL** |  | UTC timestamp when the lookup record was created. |
| `CreatedBy` | `nvarchar(max)` | NULL |  | Username or system process that created the lookup record. |
| `Description` | `nvarchar(500)` | NULL |  | Detailed description and statutory context of the lookup code. |
| `ModifiedAt` | `datetime2` | NULL |  | UTC timestamp when the lookup record was last modified. |
| `ModifiedBy` | `nvarchar(max)` | NULL |  | Username or system process that last modified the lookup record. |
| `Name` | `nvarchar(250)` | **NOT NULL** |  | Display name / title of the lookup option. |

#### Performance Indexes

| Index Name | Columns | Unique |
| :--- | :--- | :--- |
| `IX_WalkingRatingType_Active` | `Active` | No |
| `IX_WalkingRatingType_Name` | `Name` | No |

---

