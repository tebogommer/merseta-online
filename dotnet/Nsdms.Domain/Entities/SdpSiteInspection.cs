using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Structured physical site inspection checklist for accredited Skills Development Providers (SDP)
/// (Ref: Form ETQ-TP-012 SDP Site Inspection Audit Checklist and Annexure 10.2 ETQ-TP-054).
/// Captures workshop square meterage, health & safety compliance, machine guarding, and tool ratio scoring.
/// </summary>
public class SdpSiteInspection : BaseEntity
{
    public int TrainingProviderId { get; set; }
    public TrainingProvider? TrainingProvider { get; set; }

    public DateTime InspectionDate { get; set; } = DateTime.UtcNow;

    public int? InspectorPersonId { get; set; }
    public Person? InspectorPerson { get; set; }

    /// <summary>
    /// Inspection mode: PhysicalOnSite, DesktopAudit
    /// </summary>
    public string InspectionType { get; set; } = "PhysicalOnSite";

    public decimal? WorkshopSquareMeters { get; set; }
    public decimal? ClassroomSquareMeters { get; set; }

    public bool HealthAndSafetyCompliant { get; set; } = true;
    public bool MachineGuardingCompliant { get; set; } = true;
    public bool FireSafetyCompliant { get; set; } = true;
    public bool AblutionFacilitiesCompliant { get; set; } = true;

    /// <summary>
    /// Calculated percentage score for workshop tools and learner equipment ratios (0 to 100).
    /// </summary>
    public decimal? ToolRatioScore { get; set; } = 100m;

    /// <summary>
    /// Audit recommendation: Recommended, NotRecommended, ConditionalApproval
    /// </summary>
    public string OverallRecommendation { get; set; } = "Recommended";

    public string? ConditionNotes { get; set; }

    public string? InspectionReportDocumentRef { get; set; }

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public int? MaxSimultaneousLearnerCapacity { get; set; } = 25;

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public bool FireExtinguisherCompliant { get => FireSafetyCompliant; set => FireSafetyCompliant = value; }

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public bool AblutionFacilityCompliant { get => AblutionFacilitiesCompliant; set => AblutionFacilitiesCompliant = value; }

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public bool ToolStorageSecure { get; set; } = true;

    public bool IsActive { get; set; } = true;
}
