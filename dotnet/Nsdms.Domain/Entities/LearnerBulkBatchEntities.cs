using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Represents a bulk intake batch for high-speed learner registrations submitted by an Employer or SDP.
/// Implements the ATM channel of the Dual-Channel Learner Registration Architecture.
/// </summary>
public class LearnerBulkBatch : BaseEntity
{
    public string BatchReference { get; set; } = string.Empty;
    public int OrganisationId { get; set; }
    public Organisation? Organisation { get; set; }
    public string OriginalFileName { get; set; } = string.Empty;
    public int TotalRows { get; set; }
    public int SuccessCount { get; set; }
    public int ErrorCount { get; set; }
    public int StpCount { get; set; }
    public string Status { get; set; } = "Staged";
    public string? DigitalSecuritySeal { get; set; }
    public ICollection<LearnerBulkBatchRow> Rows { get; set; } = new List<LearnerBulkBatchRow>();
}

/// <summary>
/// Individual candidate learner row staged within a LearnerBulkBatch.
/// Allows fractional processing and inline error correction.
/// </summary>
public class LearnerBulkBatchRow : BaseEntity
{
    public int LearnerBulkBatchId { get; set; }
    public LearnerBulkBatch? LearnerBulkBatch { get; set; }
    public int RowIndex { get; set; }

    // Staged Biographical Fields
    public string FirstName { get; set; } = string.Empty;
    public string? MiddleName { get; set; }
    public string LastName { get; set; } = string.Empty;
    public string RsaIdNumber { get; set; } = string.Empty;
    public string? PassportNumber { get; set; }
    public DateTime DateOfBirth { get; set; }
    public string GenderCode { get; set; } = string.Empty;
    public string EquityCode { get; set; } = string.Empty;
    public string? EmailAddress { get; set; }
    public string? PhoneNumber { get; set; }

    // Staged Programme Fields
    public string LearningProgrammeTypeCode { get; set; } = "01";
    public string? SaqaQualificationId { get; set; }
    public string? QualificationTitle { get; set; }
    public string? TradeCode { get; set; }
    public DateTime LearnerSignatureDate { get; set; }
    public DateTime? CommencementDate { get; set; }

    // Processing Outcome
    public bool IsValid { get; set; } = true;
    public string? ValidationErrors { get; set; }
    public bool IsStpEligible { get; set; } = false;
    public string? StpDecisionNotes { get; set; }
    public string Status { get; set; } = "Pending";
    public int? CompanyLearnerId { get; set; }
    public CompanyLearner? CompanyLearner { get; set; }
}
