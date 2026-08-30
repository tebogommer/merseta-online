using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Workplace site inspection approval for hosting apprentice and learnership training.
/// </summary>
public class WorkplaceApproval : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the host Employer Organisation.
    /// </summary>
    public int OrganisationId { get; set; }

    /// <summary>
    /// Navigational reference to the Employer Organisation.
    /// </summary>
    public Organisation? Organisation { get; set; }

    /// <summary>
    /// Optional foreign key referencing the specific branch or plant site approved.
    /// </summary>
    public int? OrganisationSiteId { get; set; }

    /// <summary>
    /// Navigational reference to the approved OrganisationSite.
    /// </summary>
    public OrganisationSite? OrganisationSite { get; set; }

    /// <summary>
    /// SAQA Registered Qualification ID code approved for practical workplace training.
    /// </summary>
    public int? SaqaQualificationId { get; set; }

    /// <summary>
    /// Title of the registered qualification approved for on-site hosting.
    /// </summary>
    public string QualificationTitle { get; set; } = string.Empty;

    /// <summary>
    /// Official workplace approval certificate reference number (e.g. WPA-2026-001).
    /// </summary>
    public string ApprovalNumber { get; set; } = string.Empty;

    /// <summary>
    /// Current approval lifecycle status code (e.g. Pending, Approved, Rejected, Expired).
    /// </summary>
    public string ApprovalStatusCode { get; set; } = "Pending";

    /// <summary>
    /// Date when the physical on-site audit inspection occurred.
    /// </summary>
    public DateTime? InspectionDate { get; set; }

    /// <summary>
    /// Official decision date granting workplace approval.
    /// </summary>
    public DateTime? ApprovalDate { get; set; }

    /// <summary>
    /// Validity expiration date of the workplace approval certificate.
    /// </summary>
    public DateTime? ExpiryDate { get; set; }

    /// <summary>
    /// Mandatory Foreign key referencing the designated Employer Contact Person present during the workplace visit/approval.
    /// </summary>
    public int? ContactPersonId { get; set; }

    /// <summary>
    /// Navigational reference to the designated Employer Contact Person.
    /// </summary>
    public Person? ContactPerson { get; set; }

    /// <summary>
    /// Foreign key referencing the MerSETA officer / assessor who performed the inspection.
    /// </summary>
    public int? AssessorPersonId { get; set; }

    /// <summary>
    /// Navigational reference to the inspecting assessor Person.
    /// </summary>
    public Person? AssessorPerson { get; set; }

    /// <summary>
    /// Official auditor recommendations, tool adjustments, or compliance notes.
    /// </summary>
    public string? Recommendations { get; set; }

    /// <summary>
    /// Indicates whether the workplace approval is currently active.
    /// </summary>
    public bool IsActive { get; set; } = true;

    /// <summary>
    /// Certified artisan mentors assigned to supervise learners at this site.
    /// </summary>
    public ICollection<WorkplaceApprovalMentor> Mentors { get; set; } = new List<WorkplaceApprovalMentor>();

    /// <summary>
    /// Mandatory physical equipment, tools, and safety apparatus compliance checklist.
    /// </summary>
    public ICollection<WorkplaceApprovalToolList> ToolItems { get; set; } = new List<WorkplaceApprovalToolList>();
}
