using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

public class WorkplaceApproval : BaseEntity
{
    public int OrganisationId { get; set; }
    public Organisation? Organisation { get; set; }

    public int? OrganisationSiteId { get; set; }
    public OrganisationSite? OrganisationSite { get; set; }

    public int? SaqaQualificationId { get; set; }
    public string QualificationTitle { get; set; } = string.Empty;

    public string ApprovalNumber { get; set; } = string.Empty;
    public string ApprovalStatusCode { get; set; } = "Pending"; // Pending, Approved, Rejected, Expired

    public DateTime? InspectionDate { get; set; }
    public DateTime? ApprovalDate { get; set; }
    public DateTime? ExpiryDate { get; set; }

    public int? AssessorPersonId { get; set; }
    public Person? AssessorPerson { get; set; }

    public string? Recommendations { get; set; }
    public bool IsActive { get; set; } = true;

    public ICollection<WorkplaceApprovalMentor> Mentors { get; set; } = new List<WorkplaceApprovalMentor>();
    public ICollection<WorkplaceApprovalToolList> ToolItems { get; set; } = new List<WorkplaceApprovalToolList>();
}
