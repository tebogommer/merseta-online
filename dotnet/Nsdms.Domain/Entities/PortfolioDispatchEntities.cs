using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Temporal demarcation mapping a municipal town or local area to a merSETA Regional Office and Province.
/// Supports historical boundary shifts through EffectiveFrom and EffectiveTo dates.
/// </summary>
public class TerritoryDemarcation : BaseEntity
{
    /// <summary>
    /// Name of the geographic town or municipal area (e.g. "Johannesburg", "Durban", "Paarl").
    /// </summary>
    public string TownName { get; set; } = string.Empty;

    /// <summary>
    /// merSETA Regional Office code (e.g. "GAUTENG_SOUTH", "GAUTENG_NORTH", "WESTERN_CAPE", "KZN", "EASTERN_CAPE", "FREE_STATE_NC", "MPUMALANGA_LIMPOPO").
    /// </summary>
    public string RegionCode { get; set; } = string.Empty;

    /// <summary>
    /// Display name of the Regional Office (e.g. "Gauteng South Regional Office").
    /// </summary>
    public string RegionName { get; set; } = string.Empty;

    /// <summary>
    /// South African Province Code (references lookup.ProvinceType, e.g. "GP", "WC", "KZN", "EC").
    /// </summary>
    public string ProvinceCode { get; set; } = string.Empty;

    /// <summary>
    /// Optional Statistics South Africa spatial area code (references lookup.StatssaAreaCodeType).
    /// </summary>
    public string? StatssaAreaCode { get; set; }

    /// <summary>
    /// Date when this territorial demarcation became effective.
    /// </summary>
    public DateTime EffectiveFrom { get; set; } = new DateTime(2020, 1, 1, 0, 0, 0, DateTimeKind.Utc);

    /// <summary>
    /// Date when this demarcation was superseded by a boundary revision (null = currently active).
    /// </summary>
    public DateTime? EffectiveTo { get; set; }

    /// <summary>
    /// Administrative or gazette reference for this municipal demarcation boundary change.
    /// </summary>
    public string? BoundaryGazetteReference { get; set; }

    /// <summary>
    /// </summary>
    public string? PostalCodePrefix { get; set; }

    /// <summary>
    /// Municipality name or zone.
    /// </summary>
    public string? MunicipalityName { get; set; }

    /// <summary>
    /// Optional foreign key referencing the parent TerritoryZone.
    /// </summary>
    public int? ZoneId { get; set; }

    /// <summary>
    /// Navigational reference to the parent TerritoryZone.
    /// </summary>
    public TerritoryZone? Zone { get; set; }

    /// <summary>
    /// Machine code of the parent TerritoryZone.
    /// </summary>
    public string? ZoneCode { get; set; }

    /// <summary>
    /// Indicates whether this territory mapping is currently in active operational use.
    /// </summary>
    public bool IsActive { get; set; } = true;
}

/// <summary>
/// Sub-regional operational zone grouping contiguous towns or municipal areas within a merSETA Regional Office.
/// Allows regional coordinators to assign default relationship officers and balance caseloads.
/// </summary>
public class TerritoryZone : BaseEntity
{
    /// <summary>
    /// Unique machine code identifying the sub-regional zone (e.g. "ZONE_GP_EAST_RAND", "ZONE_KZN_SOUTH").
    /// </summary>
    public string ZoneCode { get; set; } = string.Empty;

    /// <summary>
    /// Descriptive display name of the operational zone (e.g. "East Rand Heavy Manufacturing Zone").
    /// </summary>
    public string ZoneName { get; set; } = string.Empty;

    /// <summary>
    /// merSETA Regional Office code (references lookup.RegionType, e.g. "GAUTENG_SOUTH").
    /// </summary>
    public string RegionCode { get; set; } = string.Empty;

    /// <summary>
    /// Display name of the Regional Office.
    /// </summary>
    public string RegionName { get; set; } = string.Empty;

    /// <summary>
    /// Primary province code (references lookup.ProvinceType).
    /// </summary>
    public string ProvinceCode { get; set; } = string.Empty;

    /// <summary>
    /// User identifier of the default primary Client Liaison Officer (CLO) assigned to this zone.
    /// </summary>
    public string? DefaultOfficerUserId { get; set; }

    /// <summary>
    /// Display name of the default primary officer.
    /// </summary>
    public string? DefaultOfficerName { get; set; }

    /// <summary>
    /// Direct email address of the default primary officer.
    /// </summary>
    public string? DefaultOfficerEmail { get; set; }

    /// <summary>
    /// Operational description or industrial focus of this zone (e.g. "Automotive Assembly & Component Manufacturing").
    /// </summary>
    public string? Description { get; set; }

    /// <summary>
    /// Indicates whether this zone is actively in operational use.
    /// </summary>
    public bool IsActive { get; set; } = true;

    /// <summary>
    /// Collection of towns and municipal demarcations mapped to this operational zone.
    /// </summary>
    public ICollection<TerritoryDemarcation> Demarcations { get; set; } = new List<TerritoryDemarcation>();
}

/// <summary>
/// Functional capability credential assigned to a staff member or contractor.
/// Decouples operational task assignments from rigid organizational job titles.
/// </summary>
public class StaffCapability : BaseEntity
{
    /// <summary>
    /// User identifier of the staff member or accredited contractor (references ApplicationUser.Id).
    /// </summary>
    public string UserId { get; set; } = string.Empty;

    /// <summary>
    /// Staff member display name.
    /// </summary>
    public string StaffName { get; set; } = string.Empty;

    /// <summary>
    /// Staff member work email address.
    /// </summary>
    public string Email { get; set; } = string.Empty;

    /// <summary>
    /// Functional capability code:
    /// CAP_WORKPLACE_AUDIT, CAP_GRANT_VERIFICATION, CAP_ARPL_ASSESSMENT,
    /// CAP_DISPATCH_COORDINATION, CAP_PORTFOLIO_MANAGEMENT, CAP_QUALITY_ASSURANCE.
    /// </summary>
    public string CapabilityCode { get; set; } = string.Empty;

    /// <summary>
    /// Descriptive name of the capability credential.
    /// </summary>
    public string CapabilityName { get; set; } = string.Empty;

    /// <summary>
    /// Regional Office base where the staff member is stationed.
    /// </summary>
    public string StationedRegionCode { get; set; } = string.Empty;

    /// <summary>
    /// Current organizational role or employment classification (e.g. "CLO", "QA Assuror", "External Evaluator", "CLC Coordinator").
    /// </summary>
    public string EmploymentRole { get; set; } = "Officer";

    /// <summary>
    /// Date when this capability was accredited or granted.
    /// </summary>
    public DateTime CertifiedDate { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Optional expiration date of this capability or accreditation certification.
    /// </summary>
    public DateTime? ExpiryDate { get; set; }

    /// <summary>
    /// Indicates whether this capability is currently active and eligible for task dispatch.
    /// </summary>
    public bool IsActive { get; set; } = true;
}

/// <summary>
/// Dynamic Account Management Portfolio allocating an Employer Organisation to a dedicated Relationship Officer.
/// Natively supports cross-regional appointments (e.g. national key accounts, specialized industry groups).
/// </summary>
public class OrganisationPortfolio : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the Employer Organisation.
    /// </summary>
    public int OrganisationId { get; set; }

    /// <summary>
    /// Navigational reference to the Organisation.
    /// </summary>
    public Organisation? Organisation { get; set; }

    /// <summary>
    /// User identifier of the dedicated Relationship Officer in charge of this account.
    /// </summary>
    public string RelationshipOfficerUserId { get; set; } = string.Empty;

    /// <summary>
    /// Display name of the assigned Relationship Officer.
    /// </summary>
    public string RelationshipOfficerName { get; set; } = string.Empty;

    /// <summary>
    /// Email of the assigned Relationship Officer.
    /// </summary>
    public string RelationshipOfficerEmail { get; set; } = string.Empty;

    /// <summary>
    /// Functional capability role under which the officer manages this account (e.g. PRIMARY_CLO, KEY_ACCOUNT_MANAGER, SECTOR_SPECIALIST).
    /// </summary>
    public string PortfolioRoleCode { get; set; } = "PRIMARY_CLO";

    /// <summary>
    /// Regional Office responsible for this portfolio allocation.
    /// </summary>
    public string ManagingRegionCode { get; set; } = string.Empty;

    /// <summary>
    /// Indicates whether this assignment overrides standard territorial geography (e.g. officer based in Gauteng managing KZN plant).
    /// </summary>
    public bool IsCrossRegionalAssignment { get; set; } = false;

    /// <summary>
    /// Statutory or operational rationale for the assignment (e.g. "National Key Account", "Specialized Chamber Portfolio", "Conflict of Interest Rotation").
    /// </summary>
    public string AssignmentReason { get; set; } = "Territory Default";

    /// <summary>
    /// Start date of this active portfolio stewardship.
    /// </summary>
    public DateTime EffectiveFrom { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Date when this portfolio stewardship ended due to successor transfer or reassignment (null = currently active).
    /// </summary>
    public DateTime? EffectiveTo { get; set; }

    /// <summary>
    /// User identifier of the Regional Manager (CRM) or Executive who authorized this portfolio allocation.
    /// </summary>
    public string AssignedByUserId { get; set; } = "SYSTEM";

    /// <summary>
    /// Indicates whether this record represents the currently active portfolio stewardship.
    /// </summary>
    public bool IsActive { get; set; } = true;
}

/// <summary>
/// Field visit scheduling and dispatch assignment created by a Client Liaison Coordinator (CLC) or Regional Manager (CRM).
/// Connects a pending visit/monitoring event to a qualified officer holding verified capabilities.
/// </summary>
public class FieldDispatchAssignment : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the Employer Organisation.
    /// </summary>
    public int OrganisationId { get; set; }

    /// <summary>
    /// Navigational reference to the Organisation.
    /// </summary>
    public Organisation? Organisation { get; set; }

    /// <summary>
    /// Optional foreign key referencing the Visit record if already generated.
    /// </summary>
    public int? VisitId { get; set; }

    /// <summary>
    /// Navigational reference to the Visit.
    /// </summary>
    public Visit? Visit { get; set; }

    /// <summary>
    /// Mandatory Employer Contact Person who must be present during the scheduled on-site visit.
    /// </summary>
    public int ContactPersonId { get; set; }

    /// <summary>
    /// Navigational reference to the Contact Person.
    /// </summary>
    public Person? ContactPerson { get; set; }

    /// <summary>
    /// Scheduled date and time for the physical on-site or desktop verification.
    /// </summary>
    public DateTime ScheduledDate { get; set; }

    /// <summary>
    /// Activity classification code (e.g. WORKPLACE_APPROVAL, DG_MONITORING, WSP_VERIFICATION, ARPL_ASSESSMENT).
    /// </summary>
    public string ActivityTypeCode { get; set; } = "WORKPLACE_APPROVAL";

    /// <summary>
    /// Specific functional capability required of the inspecting officer (e.g. CAP_WORKPLACE_AUDIT, CAP_GRANT_VERIFICATION).
    /// </summary>
    public string RequiredCapabilityCode { get; set; } = "CAP_WORKPLACE_AUDIT";

    /// <summary>
    /// User identifier of the dispatched inspecting officer (CLO, QA Specialist, or External Evaluator).
    /// </summary>
    public string DispatchedOfficerUserId { get; set; } = string.Empty;

    /// <summary>
    /// Full display name of the dispatched officer.
    /// </summary>
    public string DispatchedOfficerName { get; set; } = string.Empty;

    /// <summary>
    /// User identifier of the Coordinator (CLC) who scheduled and dispatched this activity.
    /// </summary>
    public string ScheduledByCoordinatorUserId { get; set; } = string.Empty;

    /// <summary>
    /// Coordinator display name.
    /// </summary>
    public string ScheduledByCoordinatorName { get; set; } = string.Empty;

    /// <summary>
    /// Dispatch lifecycle status: PendingDispatch, Dispatched, ConfirmedByOfficer, Completed, Rescheduled, Cancelled.
    /// </summary>
    public string DispatchStatus { get; set; } = "Dispatched";

    /// <summary>
    /// Priority level for dispatch: Normal, Urgent, CriticalSla.
    /// </summary>
    public string Priority { get; set; } = "Normal";

    /// <summary>
    /// Scheduling instructions, site access notes, and special verification guidelines.
    /// </summary>
    public string? CoordinatorNotes { get; set; }

    /// <summary>
    /// Feedback or acceptance notes recorded by the dispatched officer.
    /// </summary>
    public string? OfficerAcceptanceNotes { get; set; }

    /// <summary>
    /// Date when the dispatched activity was completed on-site.
    /// </summary>
    public DateTime? CompletedDate { get; set; }

    /// <summary>
    /// Lifecycle status code alias.
    /// </summary>
    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string DispatchStatusCode { get => DispatchStatus; set => DispatchStatus = value; }

    /// <summary>
    /// Stationed region code alias.
    /// </summary>
    public string? StationedRegionCode { get; set; }
}

/// <summary>
/// Non-repudiable audit ledger recording staff portfolio handovers, successor transitions, and bulk open task reassignments.
/// Preserves historic work provenance while maintaining operational continuity.
/// </summary>
public class PortfolioHandoffLog : BaseEntity
{
    /// <summary>
    /// User identifier of the predecessor officer departing or releasing the portfolio.
    /// </summary>
    public string FromOfficerUserId { get; set; } = string.Empty;

    /// <summary>
    /// Display name of the predecessor officer.
    /// </summary>
    public string FromOfficerName { get; set; } = string.Empty;

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string PredecessorOfficerName { get => FromOfficerName; set => FromOfficerName = value; }

    /// <summary>
    /// User identifier of the successor officer assuming the portfolio.
    /// </summary>
    public string ToOfficerUserId { get; set; } = string.Empty;

    /// <summary>
    /// Display name of the successor officer.
    /// </summary>
    public string ToOfficerName { get; set; } = string.Empty;

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string SuccessorOfficerName { get => ToOfficerName; set => ToOfficerName = value; }

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public int OpenTasksTransferredCount { get => ReassignedTasksCount; set => ReassignedTasksCount = value; }

    /// <summary>
    /// Foreign key referencing the Employer Organisation transferred.
    /// </summary>
    public int OrganisationId { get; set; }

    /// <summary>
    /// Navigational reference to the Organisation.
    /// </summary>
    public Organisation? Organisation { get; set; }

    /// <summary>
    /// User identifier of the Regional Manager (CRM) or Executive who executed the handoff.
    /// </summary>
    public string AuthorizedByUserId { get; set; } = string.Empty;

    /// <summary>
    /// Display name of the authorizing manager.
    /// </summary>
    public string AuthorizedByName { get; set; } = string.Empty;

    /// <summary>
    /// Formal business rationale for the handover (e.g. "Staff Relocation to KZN", "Maternity Leave Succession", "Annual Workload Rebalancing").
    /// </summary>
    public string HandoffReason { get; set; } = string.Empty;

    /// <summary>
    /// Number of active open WorkflowTasks that were automatically transferred to the successor.
    /// </summary>
    public int ReassignedTasksCount { get; set; } = 0;

    /// <summary>
    /// JSON list of WorkflowTask IDs that were reassigned as part of this handoff transaction.
    /// </summary>
    public string ReassignedTaskIdsJson { get; set; } = "[]";

    /// <summary>
    /// Immutable digital security verification reference for this handover transaction.
    /// </summary>
    public string SecuritySealHash { get; set; } = string.Empty;
}
