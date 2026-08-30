using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Registered SAQA unit standard accredited for delivery by a Training Provider.
/// </summary>
public class TrainingProviderUnitStandard : BaseEntity
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
    /// SAQA Registered Unit Standard ID number.
    /// </summary>
    public int UnitStandardId { get; set; }

    /// <summary>
    /// Official title of the registered unit standard.
    /// </summary>
    public string UnitStandardTitle { get; set; } = string.Empty;

    /// <summary>
    /// National Qualifications Framework (NQF) level descriptor.
    /// </summary>
    public int? NqfLevel { get; set; }

    /// <summary>
    /// SAQA credit value assigned to the unit standard.
    /// </summary>
    public int Credits { get; set; }
}
