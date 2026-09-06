using System.ComponentModel.DataAnnotations.Schema;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Physical training site or delivery location belonging to an accredited Skills Development Provider (SDP).
/// (Statutory nomenclature: Delivery Site / Site).
/// </summary>
public class TrainingProviderCampus : BaseEntity
{
    public int TrainingProviderId { get; set; }
    public TrainingProvider? TrainingProvider { get; set; }

    public string CampusName { get; set; } = string.Empty;
    public string CampusCode { get; set; } = string.Empty;

    // Statutory aliases for Delivery Site nomenclature
    [NotMapped]
    public string SiteName { get => CampusName; set => CampusName = value; }

    [NotMapped]
    public string SiteCode { get => CampusCode; set => CampusCode = value; }

    [NotMapped]
    public string? SiteContactPersonName { get => ContactPersonName; set => ContactPersonName = value; }

    [NotMapped]
    public string? SiteContactEmail { get => ContactEmail; set => ContactEmail = value; }

    [NotMapped]
    public string? SiteContactPhone { get => ContactPhone; set => ContactPhone = value; }

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
    /// Status: Active, Terminated, PendingVerification, PendingSla
    /// </summary>
    public string Status { get; set; } = "Active";

    public string? VerificationNotes { get; set; }

    /// <summary>
    /// Statutory SLA document reference signed between SDP and practitioner (Table 24 Step E & Assessor BR4).
    /// </summary>
    public string? SlaDocumentRef { get; set; }

    public bool SignedByPrincipal { get; set; } = false;

    public bool SignedByPractitioner { get; set; } = false;

    public DateTime? SlaEffectiveDate { get; set; }

    public DateTime? SlaExpiryDate { get; set; }
}
