# MerSETA NSDMS — Entity-Relationship (ER) Diagrams

> **Target Engine:** Microsoft SQL Server Express (`localhost / NSDMS-NET`)  
> **Schema Count:** 2 (`dbo`, `lookup`) | **Total Tables:** 79  
> **Last Updated:** August 2026

---

## 1. Core Identity & Organisation Subsystem

```mermaid
erDiagram
    Person ||--o{ AppUser : "authenticates as"
    Organisation ||--o{ AppUser : "default organisation"
    Organisation ||--o{ OrganisationContact : "has contact"
    Person ||--o{ OrganisationContact : "is contact for"
    Organisation ||--o{ OrganisationSite : "operates site"

    Person {
        int Id PK
        string RsaIdNumber "UK, indexed"
        string FirstName
        string LastName
        string GenderCode FK
        string EquityCode FK
        string NationalityCode FK
        string CitizenStatusCode FK
        string ProvinceCode FK
        datetime DateOfBirth
        string Email
        string Phone
        bool Active
    }

    Organisation {
        int Id PK
        string SdlNumber "UK, indexed"
        string CompanyName "indexed"
        string TradingName
        string RegistrationNumber
        string OrganisationTypeCode FK
        string ChamberCode FK
        string SicCode FK
        string CategoryCode FK
        string CompanySizeCode FK
        string ProvinceCode FK
        string SetaRegionCode
        bool Active
    }

    OrganisationContact {
        int Id PK
        int OrganisationId FK
        int PersonId FK
        string ContactTypeCode
        bool IsPrimarySdf
        bool IsCeo
        bool Active
    }

    OrganisationSite {
        int Id PK
        int OrganisationId FK
        string SiteName
        string PhysicalAddress
        string ProvinceCode FK
        bool Active
    }

    AppUser {
        int Id PK
        string UserName "UK"
        string Email
        int PersonId FK
        int DefaultOrganisationId FK
        bool IsActive
    }
```

---

## 2. Learner Management & Lifecycle Subsystem

```mermaid
erDiagram
    Organisation ||--o{ CompanyLearner : "employs / hosts"
    Person ||--o{ CompanyLearner : "registers as learner"
    TrainingProvider ||--o{ CompanyLearner : "delivers training"
    CompanyLearner ||--o{ CompanyLearnerTransfer : "transfers"
    CompanyLearner ||--o{ CompanyLearnerLostTime : "logs lost time"
    CompanyLearner ||--o{ CompanyLearnerTermination : "terminates"
    CompanyLearner ||--o{ LearnerAssessment : "assessed in"
    CompanyLearner ||--o{ LearnerTradeTest : "tested in"

    CompanyLearner {
        int Id PK
        int PersonId FK
        int OrganisationId FK
        int TrainingProviderId FK
        string LearnerContractNumber "UK, indexed"
        string QualificationTitle
        string SaqaQualificationId
        int NqfLevel
        string LearningProgrammeTypeCode FK
        string EnrolmentTypeCode FK
        datetime RegistrationDate
        datetime ContractStartDate
        datetime ContractEndDate
        string StatusCode "indexed"
        bool Active
    }

    CompanyLearnerTransfer {
        int Id PK
        int CompanyLearnerId FK
        int FromOrganisationId FK
        int ToOrganisationId FK
        string TransferReasonCode
        datetime TransferDate
        datetime EffectiveDate
        string StatusCode
    }

    CompanyLearnerLostTime {
        int Id PK
        int CompanyLearnerId FK
        string LostTimeReasonCode
        datetime StartDate
        datetime EndDate
        int DaysLost
        datetime RevisedContractEndDate
        string StatusCode
    }

    CompanyLearnerTermination {
        int Id PK
        int CompanyLearnerId FK
        string TerminationReasonCode
        datetime EffectiveDate
        bool DisputeLogged
        string StatusCode
    }

    LearnerAssessment {
        int Id PK
        int CompanyLearnerId FK
        int AssessorId FK
        string UnitStandardId
        string AssessmentResultCode
        datetime AssessmentDate
        string ModerationResultCode
    }

    LearnerTradeTest {
        int Id PK
        int PersonId FK
        int OrganisationId FK
        string TradeTitle
        string TestSerialReference "UK"
        string AttemptNumber
        string CompetencyStatus
        bool IsArpl
    }
```

---

## 3. Quality Assurance & ETQA Subsystem

```mermaid
erDiagram
    TrainingProvider ||--o{ TrainingProviderQualification : "accredited for"
    TrainingProvider ||--o{ TrainingProviderUnitStandard : "accredited for"
    EtqaAssessor ||--o{ AssessorModeratorScope : "holds scope"
    Person ||--o{ EtqaAssessor : "registered as"
    Organisation ||--o{ WorkplaceApproval : "approved for"
    WorkplaceApproval ||--o{ WorkplaceApprovalMentor : "assigns mentor"
    WorkplaceApproval ||--o{ WorkplaceApprovalToolList : "checks tool"
    Organisation ||--o{ Visit : "receives visit"
    Person ||--o{ Visit : "contact person for visit"

    TrainingProvider {
        int Id PK
        string AccreditationNumber "UK, indexed"
        string ProviderName
        string ProviderTypeCode FK
        string ProviderStatusCode FK
        datetime AccreditationStartDate
        datetime AccreditationEndDate
        bool Active
    }

    TrainingProviderQualification {
        int Id PK
        int TrainingProviderId FK
        string SaqaQualificationId
        string QualificationTitle
        datetime AccreditationEndDate
        bool Active
    }

    EtqaAssessor {
        int Id PK
        int PersonId FK
        string AssessorRegistrationNumber "UK"
        string RegistrationTypeCode
        string StatusCode
        datetime ExpiryDate
    }

    AssessorModeratorScope {
        int Id PK
        int EtqaAssessorId FK
        string SaqaQualificationId
        string ScopeStatusCode
    }

    WorkplaceApproval {
        int Id PK
        int OrganisationId FK
        string SaqaQualificationId
        string QualificationTitle
        int MaxLearnerCapacity
        string ApprovalStatusCode
        datetime ExpiryDate
    }

    WorkplaceApprovalMentor {
        int Id PK
        int WorkplaceApprovalId FK
        int PersonId FK
        string TradeCertificateNumber
        int AssignedLearnerCount
    }

    Visit {
        int Id PK
        int OrganisationId FK
        int ContactPersonId FK
        string VisitTypeCode FK
        datetime ScheduledDate
        datetime CompletedDate
        string ApprovalStatusCode FK
        string OutcomeNotes
    }
```

---

## 4. Grants & Financial Subsystem

```mermaid
erDiagram
    Organisation ||--o{ WspSubmission : "submits WSP"
    WspSubmission ||--o{ WspEmploymentSummary : "employment profile"
    WspSubmission ||--o{ WspTrainingPlan : "planned training"
    Organisation ||--o{ GrantApplication : "applies for grant"
    GrantFundingWindow ||--o{ GrantApplication : "funding window"
    GrantApplication ||--o{ GrantProjectBudget : "budget lines"
    GrantApplication ||--o{ GrantMoa : "contracts via MOA"
    GrantMoa ||--o{ GrantMoaMilestone : "milestones"
    GrantMoaMilestone ||--o{ GrantTranchePayment : "claims tranche"
    Organisation ||--o{ MandatoryGrantDisbursement : "receives rebate"
    LevyFile ||--o{ LevyFileLine : "contains line"
    LevyFileLine ||--o{ MandatoryGrantDisbursement : "reconciles rebate"
    Organisation ||--o{ InterSetaTransfer : "transfers SETA"

    WspSubmission {
        int Id PK
        int OrganisationId FK
        string FinancialYear
        datetime SubmissionDate
        string ApprovalStatusCode
        decimal GrantLevyAmount
    }

    GrantFundingWindow {
        int Id PK
        string WindowCode "UK"
        string Title
        string GrantTypeCode FK
        datetime OpeningDate
        datetime ClosingDate
        decimal TotalBudget
        bool IsActive
    }

    GrantApplication {
        int Id PK
        int OrganisationId FK
        int FundingWindowId FK
        string ApplicationNumber "UK"
        string ProjectTitle
        string GrantTypeCode
        decimal RequestedAmount
        decimal ApprovedAmount
        string StatusCode
    }

    GrantMoa {
        int Id PK
        int GrantApplicationId FK
        string MoaNumber "UK, indexed"
        datetime ContractStartDate
        datetime ContractEndDate
        decimal TotalContractValue
        string MoaStatusCode "indexed"
    }

    GrantMoaMilestone {
        int Id PK
        int GrantMoaId FK
        int MilestoneNumber
        string MilestoneTitle
        decimal TranchePercentage
        decimal TrancheAmount
        string MilestoneStatusCode
    }

    GrantTranchePayment {
        int Id PK
        int GrantMoaMilestoneId FK
        int GrantApplicationId FK
        string PaymentReferenceNumber "UK"
        string InvoiceNumber
        decimal ClaimedAmount
        decimal ApprovedPaymentAmount
        string PaymentStatusCode
        string BatchNumber
    }

    MandatoryGrantDisbursement {
        int Id PK
        int OrganisationId FK
        int WspSubmissionId FK
        string SchemeYear
        decimal CalculatedRebateAmount
        decimal DisbursedAmount
        string DisbursementStatusCode
    }

    LevyFile {
        int Id PK
        string BatchNumber "UK"
        string FileMonth
        decimal TotalLevyAmount
        string ImportStatusCode
    }

    LevyFileLine {
        int Id PK
        int LevyFileId FK
        string SdlNumber "indexed"
        decimal MandatoryLevyAmount
        decimal DiscretionaryLevyAmount
        decimal TotalLevyAmount
    }
```

---

## 5. Universal Workflow Engine & Document Management

```mermaid
erDiagram
    WorkflowDefinition ||--o{ WorkflowState : "defines states"
    WorkflowDefinition ||--o{ WorkflowTransition : "defines transitions"
    WorkflowState ||--o{ WorkflowTransition : "origin state"
    WorkflowState ||--o{ WorkflowTransition : "target state"
    WorkflowDefinition ||--o{ WorkflowInstance : "executes as"
    WorkflowInstance ||--o{ WorkflowTask : "spawns task"
    WorkflowInstance ||--o{ WorkflowHistory : "records history"
    WorkflowInstance ||--o{ WorkflowNotification : "sends alert"
    WorkflowDefinition ||--o{ DocumentRequirementRule : "mandates document"
    DocumentMetadata ||--o{ DocumentRequirementRule : "satisfies rule"

    WorkflowDefinition {
        int Id PK
        string ProcessCode "UK"
        string ProcessName
        string TargetEntityType
        bool IsActive
    }

    WorkflowState {
        int Id PK
        int WorkflowDefinitionId FK
        string StateCode
        string StateName
        bool IsInitialState
        bool IsFinalState
    }

    WorkflowTransition {
        int Id PK
        int WorkflowDefinitionId FK
        int FromStateId FK
        int ToStateId FK
        string TransitionName
        string RequiredRole
        bool RequiresComments
    }

    WorkflowInstance {
        int Id PK
        int WorkflowDefinitionId FK
        string TargetEntityType
        int TargetEntityId
        int CurrentStateId FK
        bool IsCompleted
    }

    WorkflowTask {
        int Id PK
        int WorkflowInstanceId FK
        string TaskTitle
        string AssignedGroupRole
        string AssignedUserId
        string TaskStatus
        datetime DueDate
        string TargetRoute
    }

    WorkflowHistory {
        int Id PK
        int WorkflowInstanceId FK
        string ActionName
        string ActorUserId
        string DecisionComments
        datetime TransitionTimestamp
    }

    DocumentMetadata {
        int Id PK
        string DocumentTypeCode
        string FileName
        string StoragePath
        string FileHashSha256
        long FileSizeBytes
        bool IsVerified
    }
```
