using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Specific SAQA qualification registration scope granted to an ETQA Assessor or Moderator.
/// </summary>
public class AssessorModeratorScope : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent EtqaAssessor.
    /// </summary>
    public int EtqaAssessorId { get; set; }

    /// <summary>
    /// Navigational reference to the parent EtqaAssessor.
    /// </summary>
    public EtqaAssessor? EtqaAssessor { get; set; }

    /// <summary>
    /// SAQA Registered Qualification ID code.
    /// </summary>
    public int SaqaQualificationId { get; set; }

    /// <summary>
    /// Title of the registered qualification in scope.
    /// </summary>
    public string QualificationTitle { get; set; } = string.Empty;

    /// <summary>
    /// Registration status of this specific scope (e.g. Registered, Expired, Suspended).
    /// </summary>
    public string? RegistrationStatusCode { get; set; }

    /// <summary>
    /// Validity expiration date of this qualification scope.
    /// </summary>
    public DateTime? ExpiryDate { get; set; }

    /// <summary>
    /// Approved constituent unit standards associated with this qualification scope.
    /// </summary>
    public ICollection<AssessorUnitStandardScope> UnitStandards { get; set; } = new List<AssessorUnitStandardScope>();
}
