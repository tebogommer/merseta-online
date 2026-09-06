using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Physical training site or delivery campus belonging to an accredited Skills Development Provider (SDP).
/// </summary>
public class TrainingProviderCampus : BaseEntity
{
    public int TrainingProviderId { get; set; }
    public TrainingProvider? TrainingProvider { get; set; }

    public string CampusName { get; set; } = string.Empty;
    public string CampusCode { get; set; } = string.Empty;

    public string? PhysicalAddressLine1 { get; set; }
    public string? PhysicalAddressLine2 { get; set; }
    public string? City { get; set; }
    public string? ProvinceCode { get; set; }
    public string? PostalCode { get; set; }

    public string? ContactPersonName { get; set; }
    public string? ContactEmail { get; set; }
    public string? ContactPhone { get; set; }

    /// <summary>
    /// Statutory GPS Geocode coordinates (e.g. -26.2041, 28.0473) per SDP Application Use Case Table 24 Step D.
    /// </summary>
    public string? GpsCoordinates { get; set; }
    public decimal? Latitude { get; set; }
    public decimal? Longitude { get; set; }

    /// <summary>
    /// Local or District Municipality jurisdiction (Table 15 Attribute 24).
    /// </summary>
    public string? LocalMunicipality { get; set; }

    public bool IsPrimarySite { get; set; } = false;
    public string Status { get; set; } = "Active"; // Active, Suspended, Closed

    public ICollection<TrainingProviderAssessorLink> LinkedAssessors { get; set; } = new List<TrainingProviderAssessorLink>();
}

/// <summary>
/// Relational binding between an accredited ETQA Assessor/Moderator and an SDP delivery campus.
/// </summary>
public class TrainingProviderAssessorLink : BaseEntity
{
    public int TrainingProviderId { get; set; }
    public TrainingProvider? TrainingProvider { get; set; }

    public int? TrainingProviderCampusId { get; set; }
    public TrainingProviderCampus? TrainingProviderCampus { get; set; }

    public int EtqaAssessorId { get; set; }
    public EtqaAssessor? EtqaAssessor { get; set; }

    /// <summary>
    /// Role type: Assessor, Moderator, LeadAssessor
    /// </summary>
    public string RoleTypeCode { get; set; } = "Assessor";

    public DateTime StartDate { get; set; } = DateTime.UtcNow;
    public DateTime? EndDate { get; set; }

    /// <summary>
    /// Status: Active, Terminated, PendingVerification
    /// </summary>
    public string Status { get; set; } = "Active";

    public string? VerificationNotes { get; set; }
}
