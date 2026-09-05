using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Captures legal parent or guardian details for minor learners (< 18 years old)
/// entering into Work-Based Learning Programme Agreements per the Skills Development Act.
/// </summary>
public class PersonGuardian : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the minor learner's Person record.
    /// </summary>
    public int PersonId { get; set; }

    /// <summary>
    /// Navigational reference to the learner Person record.
    /// </summary>
    public Person? Person { get; set; }

    /// <summary>
    /// Full legal given names and surname of the parent or guardian.
    /// </summary>
    public string GuardianFullName { get; set; } = string.Empty;

    /// <summary>
    /// South African National ID (13 digits) or foreign passport number of the guardian.
    /// </summary>
    public string GuardianIdNumber { get; set; } = string.Empty;

    /// <summary>
    /// Relationship to minor learner (e.g. Parent, LegalGuardian, FosterParent, Sponsor).
    /// </summary>
    public string RelationshipTypeId { get; set; } = "Parent";

    /// <summary>
    /// Statutory contact telephone / cell number (10 digits starting with 0).
    /// </summary>
    public string ContactNumber { get; set; } = string.Empty;

    /// <summary>
    /// Email contact address of the guardian.
    /// </summary>
    public string? EmailAddress { get; set; }

    /// <summary>
    /// Residential physical street address of the guardian.
    /// </summary>
    public string? PhysicalAddress { get; set; }

    /// <summary>
    /// Residential postal code.
    /// </summary>
    public string? PostalCode { get; set; }

    /// <summary>
    /// Flag indicating whether the parent/guardian relationship has ceased
    /// due to the learner attaining 18 years of age.
    /// </summary>
    public bool CeasedAtAge18 { get; set; } = false;

    /// <summary>
    /// Date when the guardian signed the agreement.
    /// </summary>
    public DateTime? SignatureDate { get; set; }

    /// <summary>
    /// Digital signature OTP token or verification reference.
    /// </summary>
    public string? SignatureSeal { get; set; }

    /// <summary>
    /// Active standing of the guardian record.
    /// </summary>
    public bool IsActive { get; set; } = true;
}