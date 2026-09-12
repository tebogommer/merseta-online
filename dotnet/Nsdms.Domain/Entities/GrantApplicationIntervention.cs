using Nsdms.Domain.Common;
using Nsdms.Domain.Lookups;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Structured intervention line item attached to a Discretionary Grant Application.
/// Supports both PIVOTAL structured training plans (accredited qualifications/unit standards)
/// and Non-PIVOTAL project implementation deliverables (milestones/equipment/bursaries).
/// </summary>
public class GrantApplicationIntervention : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent GrantApplication.
    /// </summary>
    public int GrantApplicationId { get; set; }
    public GrantApplication? GrantApplication { get; set; }

    /// <summary>
    /// Foreign key referencing the catalog InterventionType code.
    /// </summary>
    public string InterventionTypeCode { get; set; } = string.Empty;
    public InterventionType? InterventionType { get; set; }

    /// <summary>
    /// Distinguishes PIVOTAL (accredited learning programme) from Non-PIVOTAL (strategic project deliverable).
    /// </summary>
    public bool IsPivotal { get; set; } = true;

    // =========================================================================
    // PIVOTAL Structured Training Plan Fields
    // =========================================================================

    /// <summary>
    /// SAQA ID / Qualification Registration Code (e.g. 58241).
    /// </summary>
    public string? SaqaId { get; set; }

    /// <summary>
    /// Registered title of the qualification, learnership, or skills programme.
    /// </summary>
    public string? QualificationTitle { get; set; }

    /// <summary>
    /// NQF Level descriptor (e.g. NQF Level 2, 3, 4, 5, 6, 7).
    /// </summary>
    public string? NqfLevel { get; set; }

    /// <summary>
    /// Organising Framework for Occupations (OFO) Code (e.g. 651202 - Welder).
    /// </summary>
    public string? OfoCode { get; set; }

    /// <summary>
    /// Target headcount of employed (Section 18.1) learners.
    /// </summary>
    public int LearnerCountEmployed { get; set; }

    /// <summary>
    /// Target headcount of unemployed (Section 18.2) learners.
    /// </summary>
    public int LearnerCountUnemployed { get; set; }

    /// <summary>
    /// Total planned learner headcount for this intervention.
    /// </summary>
    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public int TotalLearnerCount => LearnerCountEmployed + LearnerCountUnemployed;

    /// <summary>
    /// Benchmark or agreed unit cost per learner in ZAR.
    /// </summary>
    public decimal UnitCost { get; set; }

    // =========================================================================
    // Non-PIVOTAL / Strategic Project Deliverable Milestone Fields
    // =========================================================================

    /// <summary>
    /// Deliverable title or milestone description.
    /// </summary>
    public string? DeliverableName { get; set; }

    /// <summary>
    /// Target quantity of items, sites, workshops, or equipment units.
    /// </summary>
    public int? TargetQuantity { get; set; }

    /// <summary>
    /// Estimated milestone cost / deliverable award amount in ZAR.
    /// </summary>
    public decimal EstimatedCost { get; set; }

    /// <summary>
    /// Projected deliverable or cohort recruitment start date.
    /// </summary>
    public DateTime? ProjectedStartDate { get; set; }

    /// <summary>
    /// Projected deliverable or training completion end date.
    /// </summary>
    public DateTime? ProjectedEndDate { get; set; }

    /// <summary>
    /// Actual completion or signoff date.
    /// </summary>
    public DateTime? ActualEndDate { get; set; }

    /// <summary>
    /// Payment tranche award percentage or milestone sequence number.
    /// </summary>
    public int? MilestoneNumber { get; set; }

    // =========================================================================
    // Financial Rollup & Operational Notes
    // =========================================================================

    /// <summary>
    /// Total financial amount requested or allocated for this line item in ZAR.
    /// </summary>
    public decimal TotalAmount { get; set; }

    /// <summary>
    /// Implementation comments, location details, or justification notes.
    /// </summary>
    public string? Comments { get; set; }
}
