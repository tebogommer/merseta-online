using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Registered SAQA qualification delivery scope accredited to a Training Provider.
/// </summary>
public class TrainingProviderQualification : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent TrainingProvider.
    /// </summary>
    public int TrainingProviderId { get; set; }

    /// <summary>
    /// Navigational reference to the parent TrainingProvider.
    /// </summary>
    public TrainingProvider? TrainingProvider { get; set; }

    /// <summary>
    /// SAQA Registered Qualification ID code.
    /// </summary>
    public int SaqaQualificationId { get; set; }

    /// <summary>
    /// Official title of the registered qualification.
    /// </summary>
    public string QualificationTitle { get; set; } = string.Empty;

    /// <summary>
    /// National Qualifications Framework (NQF) level descriptor (e.g. 2, 3, 4, 5).
    /// </summary>
    public int? NqfLevel { get; set; }

    /// <summary>
    /// Accreditation standing code for this specific qualification scope.
    /// </summary>
    public string? AccreditationStatusCode { get; set; }

    /// <summary>
    /// Scope expiration date.
    /// </summary>
    public DateTime? ExpiryDate { get; set; }
}
