using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Formal change request for amending an active registered CompanyLearner contract or demographic details.
/// </summary>
public class CompanyLearnerChangeRequest : BaseEntity
{
    public int CompanyLearnerId { get; set; }
    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public int EnrolmentId { get => CompanyLearnerId; set => CompanyLearnerId = value; }
    public CompanyLearner? CompanyLearner { get; set; }

    /// <summary>
    /// Type of amendment: DemographicCorrection, QualificationAmendment, ProviderChange, ContractDateExtension, StipendAdjustment.
    /// </summary>
    public string ChangeTypeCode { get; set; } = "DemographicCorrection";

    /// <summary>
    /// Serialized JSON snapshot of the learner record state prior to amendment.
    /// </summary>
    public string CurrentValuesSnapshotJson { get; set; } = "{}";

    /// <summary>
    /// Serialized JSON containing the proposed field values.
    /// </summary>
    public string RequestedValuesJson { get; set; } = "{}";

    /// <summary>
    /// Applicant or employer justification for the requested changes.
    /// </summary>
    public string JustificationReason { get; set; } = string.Empty;

    /// <summary>
    /// Status code: Pending, Approved, Rejected.
    /// </summary>
    public string ChangeStatusCode { get; set; } = "Pending";

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string StatusCode { get => ChangeStatusCode; set => ChangeStatusCode = value; }

    public string? ReviewerComments { get; set; }
    public string? ReviewedByUserId { get; set; }
    public DateTime? ReviewDate { get; set; }

    public string? ApprovedByUserId { get; set; }
    public DateTime? ApprovalDate { get; set; }
}
