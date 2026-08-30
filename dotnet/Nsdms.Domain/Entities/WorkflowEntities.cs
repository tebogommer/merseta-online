using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Universal workflow process blueprint defining lifecycle states, gates, and transitions.
/// </summary>
public class WorkflowDefinition : BaseEntity
{
    /// <summary>
    /// Unique process blueprint code (e.g. PROVIDER, WSP, DG, WPAPP, LRN, TRADETEST).
    /// </summary>
    public string Code { get; set; } = string.Empty;

    /// <summary>
    /// Display name of the workflow business process.
    /// </summary>
    public string Name { get; set; } = string.Empty;

    /// <summary>
    /// Target domain entity managed by this workflow machine (e.g. Organisation, WspSubmission, GrantApplication).
    /// </summary>
    public string TargetEntityName { get; set; } = string.Empty;

    /// <summary>
    /// Primary key property name on the target domain entity (default: Id).
    /// </summary>
    public string KeyFieldName { get; set; } = "Id";

    /// <summary>
    /// Indicates whether the workflow definition is active.
    /// </summary>
    public bool IsActive { get; set; } = true;

    /// <summary>
    /// Configured lifecycle states belonging to this workflow definition.
    /// </summary>
    public ICollection<WorkflowState> States { get; set; } = new List<WorkflowState>();

    /// <summary>
    /// Configured state transition rules belonging to this workflow definition.
    /// </summary>
    public ICollection<WorkflowTransition> Transitions { get; set; } = new List<WorkflowTransition>();

    /// <summary>
    /// Active and historic execution instances of this workflow machine.
    /// </summary>
    public ICollection<WorkflowInstance> Instances { get; set; } = new List<WorkflowInstance>();
}

/// <summary>
/// Lifecycle step / gate within a workflow machine (e.g. Draft, Under Review, Pending Approval, Approved).
/// </summary>
public class WorkflowState : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent WorkflowDefinition.
    /// </summary>
    public int WorkflowDefinitionId { get; set; }

    /// <summary>
    /// Navigational reference to the parent WorkflowDefinition.
    /// </summary>
    public WorkflowDefinition? WorkflowDefinition { get; set; }

    /// <summary>
    /// Human-readable name of the lifecycle state.
    /// </summary>
    public string StateName { get; set; } = string.Empty;

    /// <summary>
    /// Machine code representing the state (e.g. DRAFT, REVIEW, APPROVED, REJECTED).
    /// </summary>
    public string StateCode { get; set; } = string.Empty;

    /// <summary>
    /// Sequence order index for UI stepper rendering.
    /// </summary>
    public int StepOrder { get; set; }

    /// <summary>
    /// Indicates whether this is the entry state for newly initiated instances.
    /// </summary>
    public bool IsInitial { get; set; } = false;

    /// <summary>
    /// Indicates whether this is a terminal end state (Approved / Rejected / Cancelled).
    /// </summary>
    public bool IsTerminal { get; set; } = false;

    /// <summary>
    /// User group / role authorized to act on this stage (e.g. CLO, RegionManager, ReviewCommittee, QA_Manager, CEO, Admin).
    /// </summary>
    public string? AllowedGroupRole { get; set; }
}

/// <summary>
/// Authorized state transition path with role authorization gates and UI button metadata.
/// </summary>
public class WorkflowTransition : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent WorkflowDefinition.
    /// </summary>
    public int WorkflowDefinitionId { get; set; }

    /// <summary>
    /// Navigational reference to the parent WorkflowDefinition.
    /// </summary>
    public WorkflowDefinition? WorkflowDefinition { get; set; }

    /// <summary>
    /// Foreign key referencing the originating WorkflowState.
    /// </summary>
    public int FromStateId { get; set; }

    /// <summary>
    /// Navigational reference to the originating WorkflowState.
    /// </summary>
    public WorkflowState? FromState { get; set; }

    /// <summary>
    /// Foreign key referencing the target destination WorkflowState.
    /// </summary>
    public int ToStateId { get; set; }

    /// <summary>
    /// Navigational reference to the destination WorkflowState.
    /// </summary>
    public WorkflowState? ToState { get; set; }

    /// <summary>
    /// Action trigger name displayed on the UI Action Bridge button (e.g. Submit, Recommend, Approve, Reject).
    /// </summary>
    public string ActionName { get; set; } = string.Empty;

    /// <summary>
    /// Hex color code for the UI Action button (e.g. #1e40af, #16a34a, #dc2626).
    /// </summary>
    public string? ButtonColor { get; set; } = "#1e40af";

    /// <summary>
    /// Material icon identifier for the UI Action button.
    /// </summary>
    public string? ButtonIcon { get; set; }

    /// <summary>
    /// CASL permission or policy claim required to execute this transition.
    /// </summary>
    public string? RequiredPermission { get; set; }

    /// <summary>
    /// Indicates whether the user must provide justification comments when executing this transition.
    /// </summary>
    public bool RequiresComments { get; set; } = false;

    /// <summary>
    /// Status code automatically pushed to the parent entity upon transition execution.
    /// </summary>
    public string? NewEntityStatusCode { get; set; }
}

/// <summary>
/// Execution tracking instance of a workflow machine for a specific entity record.
/// </summary>
public class WorkflowInstance : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent WorkflowDefinition.
    /// </summary>
    public int WorkflowDefinitionId { get; set; }

    /// <summary>
    /// Navigational reference to the parent WorkflowDefinition.
    /// </summary>
    public WorkflowDefinition? WorkflowDefinition { get; set; }

    /// <summary>
    /// Primary key integer value of the target entity record.
    /// </summary>
    public int EntityId { get; set; }

    /// <summary>
    /// Human-readable title or description of the target record.
    /// </summary>
    public string EntityTitle { get; set; } = string.Empty;

    /// <summary>
    /// Business reference number of the target record (e.g. WSP-2026-001).
    /// </summary>
    public string EntityReferenceNumber { get; set; } = string.Empty;

    /// <summary>
    /// Foreign key referencing the current active WorkflowState.
    /// </summary>
    public int CurrentWorkflowStateId { get; set; }

    /// <summary>
    /// Navigational reference to the current active WorkflowState.
    /// </summary>
    public WorkflowState? CurrentWorkflowState { get; set; }

    /// <summary>
    /// User identifier who initiated the workflow lifecycle.
    /// </summary>
    public string InitiatorUserId { get; set; } = string.Empty;

    /// <summary>
    /// Full display name of the initiating user.
    /// </summary>
    public string InitiatorName { get; set; } = string.Empty;

    /// <summary>
    /// Timestamp when the workflow was started.
    /// </summary>
    public DateTime InitiatedDate { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Timestamp when the workflow reached a terminal state.
    /// </summary>
    public DateTime? CompletedDate { get; set; }

    /// <summary>
    /// Indicates whether the workflow instance is closed.
    /// </summary>
    public bool IsCompleted { get; set; } = false;

    /// <summary>
    /// Actionable review tasks generated by this workflow instance.
    /// </summary>
    public ICollection<WorkflowTask> Tasks { get; set; } = new List<WorkflowTask>();

    /// <summary>
    /// Immutable history log of all transitions executed.
    /// </summary>
    public ICollection<WorkflowHistory> History { get; set; } = new List<WorkflowHistory>();

    /// <summary>
    /// Alias navigation for EF Core and Service layer queries.
    /// </summary>
    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public ICollection<WorkflowHistory> Histories { get => History; set => History = value; }

    /// <summary>
    /// Notification alerts dispatched during workflow advancement.
    /// </summary>
    public ICollection<WorkflowNotification> Notifications { get; set; } = new List<WorkflowNotification>();
}

/// <summary>
/// Task assigned to a specific role or user requiring review, inspection, or verification action.
/// </summary>
public class WorkflowTask : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent WorkflowInstance.
    /// </summary>
    public int WorkflowInstanceId { get; set; }

    /// <summary>
    /// Navigational reference to the parent WorkflowInstance.
    /// </summary>
    public WorkflowInstance? WorkflowInstance { get; set; }

    /// <summary>
    /// Concise summary title of the assigned task.
    /// </summary>
    public string TaskTitle { get; set; } = string.Empty;

    /// <summary>
    /// Detailed instructions and checklist requirements.
    /// </summary>
    public string TaskDescription { get; set; } = string.Empty;

    /// <summary>
    /// Target group role eligible to claim this task.
    /// </summary>
    public string? AssignedGroupRole { get; set; }

    /// <summary>
    /// User identifier of the assignee who claimed or was assigned the task.
    /// </summary>
    public string? AssignedUserId { get; set; }

    /// <summary>
    /// Full display name of the assignee.
    /// </summary>
    public string? AssignedUserName { get; set; }

    /// <summary>
    /// Task status code (e.g. Open, Claimed, Completed, Cancelled).
    /// </summary>
    public string TaskStatus { get; set; } = "Open";

    /// <summary>
    /// Task urgency level (e.g. Low, Normal, High, Urgent).
    /// </summary>
    public string Priority { get; set; } = "Normal";

    /// <summary>
    /// Target completion deadline based on SLA governance.
    /// </summary>
    public DateTime DueDate { get; set; } = DateTime.UtcNow.AddDays(7);

    /// <summary>
    /// Timestamp when the user claimed ownership of the task.
    /// </summary>
    public DateTime? ClaimedDate { get; set; }

    /// <summary>
    /// Timestamp when the task was finalized.
    /// </summary>
    public DateTime? CompletedDate { get; set; }

    /// <summary>
    /// Stacked master-detail deep link route (e.g. /wsp/1, /employers/10).
    /// </summary>
    public string TargetRoute { get; set; } = string.Empty;
}

/// <summary>
/// Immutable audit snapshot recording every workflow transition execution and decision comments.
/// </summary>
public class WorkflowHistory : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent WorkflowInstance.
    /// </summary>
    public int WorkflowInstanceId { get; set; }

    /// <summary>
    /// Navigational reference to the parent WorkflowInstance.
    /// </summary>
    public WorkflowInstance? WorkflowInstance { get; set; }

    /// <summary>
    /// Foreign key referencing the state prior to transition.
    /// </summary>
    public int? FromStateId { get; set; }

    /// <summary>
    /// Navigational reference to the previous state.
    /// </summary>
    public WorkflowState? FromState { get; set; }

    /// <summary>
    /// Foreign key referencing the destination state.
    /// </summary>
    public int ToStateId { get; set; }

    /// <summary>
    /// Navigational reference to the destination state.
    /// </summary>
    public WorkflowState? ToState { get; set; }

    /// <summary>
    /// Name of the transition action executed.
    /// </summary>
    public string ActionName { get; set; } = string.Empty;

    /// <summary>
    /// User identifier of the actor who triggered the transition.
    /// </summary>
    public string ActorUserId { get; set; } = string.Empty;

    /// <summary>
    /// Full display name of the actor.
    /// </summary>
    public string ActorName { get; set; } = string.Empty;

    /// <summary>
    /// System role assumed by the actor at time of action.
    /// </summary>
    public string ActorRole { get; set; } = string.Empty;

    /// <summary>
    /// UTC timestamp when the transition was executed.
    /// </summary>
    public DateTime ActionDate { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Mandatory or optional decision justification comments recorded by the actor.
    /// </summary>
    public string? Comments { get; set; }
}

/// <summary>
/// User notification alert dispatched upon workflow advancement or assignment.
/// </summary>
public class WorkflowNotification : BaseEntity
{
    /// <summary>
    /// Optional foreign key referencing the associated WorkflowInstance.
    /// </summary>
    public int? WorkflowInstanceId { get; set; }

    /// <summary>
    /// Navigational reference to the associated WorkflowInstance.
    /// </summary>
    public WorkflowInstance? WorkflowInstance { get; set; }

    /// <summary>
    /// User identifier of the notification recipient.
    /// </summary>
    public string RecipientUserId { get; set; } = string.Empty;

    /// <summary>
    /// Headline title of the notification alert.
    /// </summary>
    public string Title { get; set; } = string.Empty;

    /// <summary>
    /// HTML / rich message body.
    /// </summary>
    public string MessageHtml { get; set; } = string.Empty;

    /// <summary>
    /// Navigation target route for the notification click action.
    /// </summary>
    public string TargetRoute { get; set; } = string.Empty;

    /// <summary>
    /// Indicates whether the recipient has viewed the notification.
    /// </summary>
    public bool IsRead { get; set; } = false;

    /// <summary>
    /// Timestamp when the notification was created.
    /// </summary>
    public DateTime CreatedDate { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Timestamp when the recipient marked the alert as read.
    /// </summary>
    public DateTime? ReadDate { get; set; }
}

/// <summary>
/// SHA-256 integrity-verified digital document evidence stored in the Document Vault.
/// </summary>
public class DocumentMetadata : BaseEntity
{
    /// <summary>
    /// Target domain entity name linked to this document (e.g. Organisation, CompanyLearner, WorkplaceApproval, WspSubmission).
    /// </summary>
    public string TargetEntityName { get; set; } = string.Empty;

    /// <summary>
    /// Primary key integer value of the target entity record.
    /// </summary>
    public int TargetEntityId { get; set; }

    /// <summary>
    /// Document taxonomy type code (e.g. RSA_ID, PROOF_OF_BANKING, ACCREDITATION_CERT, TOOL_LIST_EVIDENCE, WSP_SIGNOFF).
    /// </summary>
    public string DocumentTypeCode { get; set; } = string.Empty;

    /// <summary>
    /// Display name of the document category.
    /// </summary>
    public string DocumentTypeName { get; set; } = string.Empty;

    /// <summary>
    /// Original file name of uploaded evidence.
    /// </summary>
    public string FileName { get; set; } = string.Empty;

    /// <summary>
    /// Storage blob URI or relative storage path.
    /// </summary>
    public string StorageUri { get; set; } = string.Empty;

    /// <summary>
    /// File size in bytes.
    /// </summary>
    public long FileSizeBytes { get; set; }

    /// <summary>
    /// MIME content type (e.g. application/pdf, image/png).
    /// </summary>
    public string ContentType { get; set; } = "application/pdf";

    /// <summary>
    /// Cryptographic SHA-256 digital fingerprint hash for audit tampering protection.
    /// </summary>
    public string Sha256Hash { get; set; } = string.Empty;

    /// <summary>
    /// User identifier of the uploader.
    /// </summary>
    public string UploadedByUserId { get; set; } = string.Empty;

    /// <summary>
    /// Full display name of the uploader.
    /// </summary>
    public string UploadedByUserName { get; set; } = string.Empty;

    /// <summary>
    /// Timestamp when the file was uploaded.
    /// </summary>
    public DateTime UploadDate { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Indicates whether the document has been verified by an authorized officer.
    /// </summary>
    public bool IsVerified { get; set; } = false;

    /// <summary>
    /// Document verification remarks or rejection reasons.
    /// </summary>
    public string? VerificationNotes { get; set; }
}

/// <summary>
/// Governance rule defining mandatory document evidence required before workflow gate advancement.
/// </summary>
public class DocumentRequirementRule : BaseEntity
{
    /// <summary>
    /// Workflow process code (e.g. PROVIDER, WSP, DG, WPAPP, LRN, TRADETEST).
    /// </summary>
    public string WorkflowProcessCode { get; set; } = string.Empty;

    /// <summary>
    /// Required document type code.
    /// </summary>
    public string DocumentTypeCode { get; set; } = string.Empty;

    /// <summary>
    /// Display name of the required document.
    /// </summary>
    public string DocumentTypeName { get; set; } = string.Empty;

    /// <summary>
    /// Descriptive requirement context and guidelines.
    /// </summary>
    public string Description { get; set; } = string.Empty;

    /// <summary>
    /// Indicates whether this document is strictly mandatory to advance.
    /// </summary>
    public bool IsMandatory { get; set; } = true;

    /// <summary>
    /// Foreign key referencing the WorkflowState where this document must be verified before proceeding.
    /// </summary>
    public int RequiredAtStateId { get; set; }
}
