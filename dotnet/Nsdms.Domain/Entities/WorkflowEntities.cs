using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

public class WorkflowDefinition : BaseEntity
{
    public string Code { get; set; } = string.Empty; // PROVIDER, WSP, DG, WPAPP, LRN, TRADETEST
    public string Name { get; set; } = string.Empty;
    public string TargetEntityName { get; set; } = string.Empty; // Organisation, WspSubmission, GrantApplication, WorkplaceApproval, CompanyLearner, LearnerTradeTest
    public string KeyFieldName { get; set; } = "Id";
    public bool IsActive { get; set; } = true;

    public ICollection<WorkflowState> States { get; set; } = new List<WorkflowState>();
    public ICollection<WorkflowTransition> Transitions { get; set; } = new List<WorkflowTransition>();
    public ICollection<WorkflowInstance> Instances { get; set; } = new List<WorkflowInstance>();
}

public class WorkflowState : BaseEntity
{
    public int WorkflowDefinitionId { get; set; }
    public WorkflowDefinition? WorkflowDefinition { get; set; }

    public string StateName { get; set; } = string.Empty; // Draft, Under Review, Pending Approval, Approved, Rejected
    public string StateCode { get; set; } = string.Empty;
    public int StepOrder { get; set; }
    public bool IsInitial { get; set; } = false;
    public bool IsTerminal { get; set; } = false;
    public string? AllowedGroupRole { get; set; } // CLO, RegionManager, ReviewCommittee, QA_Manager, CEO, Admin
}

public class WorkflowTransition : BaseEntity
{
    public int WorkflowDefinitionId { get; set; }
    public WorkflowDefinition? WorkflowDefinition { get; set; }

    public int FromStateId { get; set; }
    public WorkflowState? FromState { get; set; }

    public int ToStateId { get; set; }
    public WorkflowState? ToState { get; set; }

    public string ActionName { get; set; } = string.Empty; // Submit, Recommend, Approve, Reject, Request Clarification
    public string? ButtonColor { get; set; } = "#1e40af"; // Hex color for UI Action Bridge
    public string? ButtonIcon { get; set; } // Material icon identifier
    public string? RequiredPermission { get; set; } // APPROVE_WSP, RECOMMEND_DG, etc.
    public bool RequiresComments { get; set; } = false;
    public string? NewEntityStatusCode { get; set; } // Pushed to parent entity
}

public class WorkflowInstance : BaseEntity
{
    public int WorkflowDefinitionId { get; set; }
    public WorkflowDefinition? WorkflowDefinition { get; set; }

    public int EntityId { get; set; } // Foreign key to the target entity (e.g. WspSubmission.Id)
    public string EntityTitle { get; set; } = string.Empty;
    public string EntityReferenceNumber { get; set; } = string.Empty;

    public int CurrentWorkflowStateId { get; set; }
    public WorkflowState? CurrentWorkflowState { get; set; }

    public string InitiatorUserId { get; set; } = string.Empty;
    public string InitiatorName { get; set; } = string.Empty;
    public DateTime InitiatedDate { get; set; } = DateTime.UtcNow;
    public DateTime? CompletedDate { get; set; }
    public bool IsCompleted { get; set; } = false;

    public ICollection<WorkflowTask> Tasks { get; set; } = new List<WorkflowTask>();
    public ICollection<WorkflowHistory> History { get; set; } = new List<WorkflowHistory>();
    public ICollection<WorkflowNotification> Notifications { get; set; } = new List<WorkflowNotification>();
}

public class WorkflowTask : BaseEntity
{
    public int WorkflowInstanceId { get; set; }
    public WorkflowInstance? WorkflowInstance { get; set; }

    public string TaskTitle { get; set; } = string.Empty;
    public string TaskDescription { get; set; } = string.Empty;
    public string? AssignedGroupRole { get; set; } // Region Manager, CLO, Review Committee, Administrator
    public string? AssignedUserId { get; set; }
    public string? AssignedUserName { get; set; }
    public string TaskStatus { get; set; } = "Open"; // Open, Claimed, Completed, Cancelled
    public string Priority { get; set; } = "Normal"; // Low, Normal, High, Urgent
    public DateTime DueDate { get; set; } = DateTime.UtcNow.AddDays(7);
    public DateTime? ClaimedDate { get; set; }
    public DateTime? CompletedDate { get; set; }
    public string TargetRoute { get; set; } = string.Empty; // Deep link URL e.g. /wsp/1
}

public class WorkflowHistory : BaseEntity
{
    public int WorkflowInstanceId { get; set; }
    public WorkflowInstance? WorkflowInstance { get; set; }

    public int? FromStateId { get; set; }
    public WorkflowState? FromState { get; set; }

    public int ToStateId { get; set; }
    public WorkflowState? ToState { get; set; }

    public string ActionName { get; set; } = string.Empty;
    public string ActorUserId { get; set; } = string.Empty;
    public string ActorName { get; set; } = string.Empty;
    public string ActorRole { get; set; } = string.Empty;
    public DateTime ActionDate { get; set; } = DateTime.UtcNow;
    public string? Comments { get; set; }
}

public class WorkflowNotification : BaseEntity
{
    public int? WorkflowInstanceId { get; set; }
    public WorkflowInstance? WorkflowInstance { get; set; }

    public string RecipientUserId { get; set; } = string.Empty;
    public string Title { get; set; } = string.Empty;
    public string MessageHtml { get; set; } = string.Empty;
    public string TargetRoute { get; set; } = string.Empty;
    public bool IsRead { get; set; } = false;
    public DateTime CreatedDate { get; set; } = DateTime.UtcNow;
    public DateTime? ReadDate { get; set; }
}

public class DocumentMetadata : BaseEntity
{
    public string TargetEntityName { get; set; } = string.Empty; // Organisation, CompanyLearner, WorkplaceApproval, WspSubmission
    public int TargetEntityId { get; set; }
    public string DocumentTypeCode { get; set; } = string.Empty; // RSA_ID, PROOF_OF_BANKING, ACCREDITATION_CERT, TOOL_LIST_EVIDENCE, WSP_SIGNOFF
    public string DocumentTypeName { get; set; } = string.Empty;
    public string FileName { get; set; } = string.Empty;
    public string StorageUri { get; set; } = string.Empty; // Virtual storage path or Cloud Blob URI
    public long FileSizeBytes { get; set; }
    public string ContentType { get; set; } = "application/pdf";
    public string Sha256Hash { get; set; } = string.Empty;
    public string UploadedByUserId { get; set; } = string.Empty;
    public string UploadedByUserName { get; set; } = string.Empty;
    public DateTime UploadDate { get; set; } = DateTime.UtcNow;
    public bool IsVerified { get; set; } = false;
    public string? VerificationNotes { get; set; }
}

public class DocumentRequirementRule : BaseEntity
{
    public string WorkflowProcessCode { get; set; } = string.Empty; // PROVIDER, WSP, DG, WPAPP, LRN, TRADETEST
    public string DocumentTypeCode { get; set; } = string.Empty;
    public string DocumentTypeName { get; set; } = string.Empty;
    public string Description { get; set; } = string.Empty;
    public bool IsMandatory { get; set; } = true;
    public int RequiredAtStateId { get; set; } // Workflow state where document is required before advancement
}
