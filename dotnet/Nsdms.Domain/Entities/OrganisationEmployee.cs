using Nsdms.Domain.Common;
using Nsdms.Domain.Lookups;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Represents an active or historical employee record within an employer organisation's roster (Option B: The Living Employer Roster).
/// Enables 1-Click WSP/ATR statutory report auto-harvesting with full demographics, occupational categorisation, and OFO alignment.
/// </summary>
public class OrganisationEmployee : BaseEntity
{
    /// <summary>
    /// Foreign key identifier referencing the employing Organisation.
    /// </summary>
    public int OrganisationId { get; set; }

    /// <summary>
    /// Foreign key identifier referencing the natural Person (contains RSA ID, First Name, Last Name, Gender, Equity, Disability).
    /// </summary>
    public int PersonId { get; set; }

    /// <summary>
    /// Optional foreign key identifier referencing the specific employer branch or operational site.
    /// </summary>
    public int? OrganisationSiteId { get; set; }

    /// <summary>
    /// Internal employer payroll / employee staff identification number.
    /// </summary>
    public string? EmployeeNumber { get; set; }

    /// <summary>
    /// Occupational job title or position description within the organisation.
    /// </summary>
    public string? JobTitle { get; set; }

    /// <summary>
    /// Statutory Organising Framework for Occupations (OFO) code (references lookup.OfoCodeType.Code, e.g. 653101, 121901).
    /// </summary>
    public string? OfoCodeId { get; set; }

    /// <summary>
    /// Employment contract type (e.g. PERMANENT, CONTRACT, TEMPORARY, SEASONAL).
    /// </summary>
    public string? EmploymentTypeCode { get; set; }

    /// <summary>
    /// Employment status standing (e.g. ACTIVE, RESIGNED, RETIRED, TERMINATED, DECEASED).
    /// </summary>
    public string? EmploymentStatusCode { get; set; }

    /// <summary>
    /// Statutory Employment Equity / WSP occupational category code (e.g. MANAGERS, PROFESSIONALS, TECHNICIANS, CLERICAL, SERVICE_SALES, SKILLED_CRAFT, PLANT_OPERATORS, ELEMENTARY).
    /// </summary>
    public string? OccupationalCategoryCode { get; set; }

    /// <summary>
    /// Date when employment commenced with the organisation.
    /// </summary>
    public DateTime? StartDate { get; set; }

    /// <summary>
    /// Date when employment terminated or concluded (if applicable).
    /// </summary>
    public DateTime? EndDate { get; set; }

    /// <summary>
    /// Indicates whether the employee is currently active and included in live headcount rosters.
    /// </summary>
    public bool IsActive { get; set; } = true;

    // Navigation properties
    public Organisation? Organisation { get; set; }
    public Person? Person { get; set; }
    public OrganisationSite? OrganisationSite { get; set; }
    public OfoCodeType? OfoCode { get; set; }
}
