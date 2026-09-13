using System.Text.Json;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

/// <summary>
/// Domain service implementing Module 18: Interest and Conflict of Interest (COI) Management.
/// Enforces statutory corporate governance capture, insider recusal, cross-organisation grant syndicate detection,
/// and audited conflict clearance under PFMA Section 50/51 and King IV.
/// </summary>
public class ConflictManagementService : IConflictManagementService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly ISystemConfigurationService _configService;
    private readonly AuditService _auditService;

    public ConflictManagementService(
        INsdmsDbContextFactory contextFactory,
        ISystemConfigurationService configService,
        AuditService auditService)
    {
        _contextFactory = contextFactory;
        _configService = configService;
        _auditService = auditService;
    }

    #region Organisation Governance Members
    public async Task<List<OrganisationGovernanceMember>> GetGovernanceMembersAsync(int organisationId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.OrganisationGovernanceMembers
            .Include(m => m.Person)
            .Include(m => m.ShareholderOrganisation)
            .Where(m => m.OrganisationId == organisationId)
            .OrderByDescending(m => m.IsActive)
            .ThenByDescending(m => m.ShareholdingPercentage)
            .ToListAsync();
    }

    public async Task<OrganisationGovernanceMember?> GetGovernanceMemberByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.OrganisationGovernanceMembers
            .Include(m => m.Person)
            .Include(m => m.ShareholderOrganisation)
            .Include(m => m.Organisation)
            .FirstOrDefaultAsync(m => m.Id == id);
    }

    public async Task<OrganisationGovernanceMember> AddGovernanceMemberAsync(OrganisationGovernanceMember member, string currentUsername)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        member.CreatedAt = DateTime.UtcNow;
        member.CreatedBy = currentUsername;

        db.OrganisationGovernanceMembers.Add(member);
        await db.SaveChangesAsync();

        await _auditService.LogActionAsync(
            "OrganisationGovernanceMember",
            member.Id,
            "AddGovernanceMember",
            currentUsername,
            null,
            new { member.OrganisationId, member.PersonId, member.ShareholderOrganisationId, member.MemberType, member.GovernanceRoleCode, member.ShareholdingPercentage });

        // Trigger automatic conflict evaluation for the organisation
        await EvaluateOrganisationConflictsAsync(member.OrganisationId, currentUsername);

        return member;
    }

    public async Task<OrganisationGovernanceMember> UpdateGovernanceMemberAsync(OrganisationGovernanceMember member, string currentUsername)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.OrganisationGovernanceMembers.FirstOrDefaultAsync(m => m.Id == member.Id);
        if (existing == null)
            throw new InvalidOperationException($"Governance member with ID {member.Id} not found.");

        var beforeState = new { existing.GovernanceRoleCode, existing.ShareholdingPercentage, existing.HasVotingRights, existing.IsActive };

        existing.PersonId = member.PersonId;
        existing.ShareholderOrganisationId = member.ShareholderOrganisationId;
        existing.MemberType = member.MemberType;
        existing.CorporateEntityName = member.CorporateEntityName;
        existing.CorporateRegistrationNumber = member.CorporateRegistrationNumber;
        existing.DirectorCategory = member.DirectorCategory;
        existing.GovernanceRoleCode = member.GovernanceRoleCode;
        existing.ShareholdingPercentage = member.ShareholdingPercentage;
        existing.HasVotingRights = member.HasVotingRights;
        existing.AppointmentDate = member.AppointmentDate;
        existing.ResignationDate = member.ResignationDate;
        existing.CipcRegistered = member.CipcRegistered;
        existing.IdVerified = member.IdVerified;
        existing.IsActive = member.IsActive;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        await db.SaveChangesAsync();

        await _auditService.LogActionAsync(
            "OrganisationGovernanceMember",
            existing.Id,
            "UpdateGovernanceMember",
            currentUsername,
            beforeState,
            new { existing.GovernanceRoleCode, existing.ShareholdingPercentage, existing.HasVotingRights, existing.IsActive });

        await EvaluateOrganisationConflictsAsync(existing.OrganisationId, currentUsername);

        return existing;
    }

    public async Task<bool> DeleteGovernanceMemberAsync(int memberId, string currentUsername)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var member = await db.OrganisationGovernanceMembers.FirstOrDefaultAsync(m => m.Id == memberId);
        if (member == null) return false;

        var orgId = member.OrganisationId;
        db.OrganisationGovernanceMembers.Remove(member);
        await db.SaveChangesAsync();

        await _auditService.LogActionAsync(
            "OrganisationGovernanceMember",
            memberId,
            "DeleteGovernanceMember",
            currentUsername,
            new { OrganisationId = orgId, member.PersonId, member.GovernanceRoleCode },
            null);

        await EvaluateOrganisationConflictsAsync(orgId, currentUsername);
        return true;
    }

    public async Task<GovernanceValidationResult> ValidateOrganisationGovernanceComplianceAsync(int organisationId, bool isAccreditationContext = false)
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var threshold = await _configService.GetValueAsync<decimal>("Governance:MinimumShareholdingDisclosureThresholdPercent", 5.0m);
        var enforceDisclosure = await _configService.GetValueAsync<bool>("Governance:EnforceShareholdingDisclosure", true);

        var members = await db.OrganisationGovernanceMembers
            .Include(m => m.Person)
            .Where(m => m.OrganisationId == organisationId && m.IsActive)
            .ToListAsync();

        var result = new GovernanceValidationResult
        {
            MinimumThresholdPercentEnforced = threshold,
            IsAccreditationContext = isAccreditationContext
        };

        var directors = members.Where(m => m.GovernanceRoleCode == "DIRECTOR" ||
                                           m.GovernanceRoleCode == "MANAGING_DIRECTOR" ||
                                           m.GovernanceRoleCode == "PARTNER" ||
                                           m.GovernanceRoleCode == "TRUSTEE" ||
                                           m.GovernanceRoleCode == "BOARD_MEMBER" ||
                                           m.GovernanceRoleCode.StartsWith("DIRECTOR_")).ToList();

        var shareholders = members.Where(m => m.GovernanceRoleCode == "SHAREHOLDER" || m.ShareholdingPercentage > 0).ToList();

        result.ActiveDirectorCount = directors.Count;
        result.ActiveExecutiveDirectorCount = directors.Count(d => d.DirectorCategory == "EXECUTIVE" || d.GovernanceRoleCode == "MANAGING_DIRECTOR");
        result.ActiveNonExecutiveDirectorCount = directors.Count(d => d.DirectorCategory == "NON_EXECUTIVE" || (d.DirectorCategory != "EXECUTIVE" && d.GovernanceRoleCode != "MANAGING_DIRECTOR"));
        result.ActiveShareholderCount = shareholders.Count;
        result.CorporateShareholderCount = shareholders.Count(s => s.MemberType == "CORPORATE_ENTITY");
        result.TotalShareholdingPercentageDeclared = shareholders.Sum(s => s.ShareholdingPercentage);

        if (directors.Count == 0)
        {
            result.Violations.Add("At least one active Director, Partner, Trustee, or Board Member must be captured.");
        }

        if (enforceDisclosure)
        {
            if (shareholders.Count == 0 && result.TotalShareholdingPercentageDeclared == 0.00m)
            {
                result.Violations.Add($"Beneficial shareholding disclosure is mandatory. Capturing shareholders holding >= {threshold:F1}% is required.");
            }

            foreach (var sh in shareholders.Where(s => s.ShareholdingPercentage >= threshold))
            {
                if (sh.MemberType == "CORPORATE_ENTITY")
                {
                    if (sh.ShareholderOrganisationId == null && string.IsNullOrWhiteSpace(sh.CorporateEntityName))
                    {
                        result.Violations.Add($"Corporate Shareholder holding {sh.ShareholdingPercentage:F2}% requires a registered entity name or CIPC registration number.");
                    }
                }
                else
                {
                    if (sh.Person == null || (string.IsNullOrWhiteSpace(sh.Person.RsaIdNumber) && string.IsNullOrWhiteSpace(sh.Person.PassportNumber)))
                    {
                        result.Violations.Add($"Shareholder holding {sh.ShareholdingPercentage:F2}% requires a verified RSA ID or Passport Number.");
                    }
                }
            }

            if (isAccreditationContext)
            {
                // Skills Development Provider Accreditation specific governance checks
                if (directors.Count == 0)
                {
                    result.Violations.Add("Accreditation application rejected: Provider must have at least one verified executive director registered.");
                }

                if (shareholders.Any(s => !s.CipcRegistered))
                {
                    result.Violations.Add("Accreditation requirement: All controlling shareholders must be verified against CIPC beneficial ownership records.");
                }
            }
        }

        result.IsCompliant = result.Violations.Count == 0;
        return result;
    }
    #endregion

    #region Institutional Insiders
    public async Task<List<InstitutionalAffiliation>> GetInstitutionalAffiliationsAsync(bool activeOnly = true)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.InstitutionalAffiliations.Include(a => a.Person).AsQueryable();

        if (activeOnly)
        {
            query = query.Where(a => a.IsActive);
        }

        return await query
            .OrderBy(a => a.AffiliationTypeCode)
            .ThenBy(a => a.Person != null ? a.Person.LastName : "")
            .ToListAsync();
    }

    public async Task<InstitutionalAffiliation?> GetInstitutionalAffiliationByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.InstitutionalAffiliations
            .Include(a => a.Person)
            .FirstOrDefaultAsync(a => a.Id == id);
    }

    public async Task<InstitutionalAffiliation> AddInstitutionalAffiliationAsync(InstitutionalAffiliation affiliation, string currentUsername)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        affiliation.CreatedAt = DateTime.UtcNow;
        affiliation.CreatedBy = currentUsername;

        db.InstitutionalAffiliations.Add(affiliation);
        await db.SaveChangesAsync();

        await _auditService.LogActionAsync(
            "InstitutionalAffiliation",
            affiliation.Id,
            "AddInstitutionalAffiliation",
            currentUsername,
            null,
            new { affiliation.PersonId, affiliation.AffiliationTypeCode, affiliation.DepartmentOrCommittee, affiliation.Designation });

        return affiliation;
    }

    public async Task<InstitutionalAffiliation> UpdateInstitutionalAffiliationAsync(InstitutionalAffiliation affiliation, string currentUsername)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.InstitutionalAffiliations.FirstOrDefaultAsync(a => a.Id == affiliation.Id);
        if (existing == null)
            throw new InvalidOperationException($"Institutional affiliation with ID {affiliation.Id} not found.");

        existing.AffiliationTypeCode = affiliation.AffiliationTypeCode;
        existing.DepartmentOrCommittee = affiliation.DepartmentOrCommittee;
        existing.Designation = affiliation.Designation;
        existing.EmployeeNumber = affiliation.EmployeeNumber;
        existing.IsIndependentMember = affiliation.IsIndependentMember;
        existing.TermStartDate = affiliation.TermStartDate;
        existing.TermEndDate = affiliation.TermEndDate;
        existing.IsActive = affiliation.IsActive;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        await db.SaveChangesAsync();

        await _auditService.LogActionAsync(
            "InstitutionalAffiliation",
            existing.Id,
            "UpdateInstitutionalAffiliation",
            currentUsername,
            null,
            new { existing.AffiliationTypeCode, existing.DepartmentOrCommittee, existing.Designation, existing.IsActive });

        return existing;
    }

    public async Task<bool> DeactivateInstitutionalAffiliationAsync(int id, string currentUsername)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.InstitutionalAffiliations.FirstOrDefaultAsync(a => a.Id == id);
        if (existing == null) return false;

        existing.IsActive = false;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;
        await db.SaveChangesAsync();

        await _auditService.LogActionAsync(
            "InstitutionalAffiliation",
            id,
            "DeactivateInstitutionalAffiliation",
            currentUsername,
            null,
            new { Deactivated = true });

        return true;
    }
    #endregion

    #region Declarations of Interest
    public async Task<List<InterestDeclaration>> GetDeclarationsAsync(string? schemeYear = null, int? personId = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.InterestDeclarations
            .Include(d => d.Person)
            .Include(d => d.Items)
            .AsQueryable();

        if (!string.IsNullOrWhiteSpace(schemeYear))
        {
            query = query.Where(d => d.DeclarationPeriodYear == schemeYear);
        }

        if (personId.HasValue)
        {
            query = query.Where(d => d.PersonId == personId.Value);
        }

        return await query.OrderByDescending(d => d.CreatedAt).ToListAsync();
    }

    public async Task<InterestDeclaration?> GetDeclarationByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.InterestDeclarations
            .Include(d => d.Person)
            .Include(d => d.FinancialYear)
            .Include(d => d.Items)
            .FirstOrDefaultAsync(d => d.Id == id);
    }

    public async Task<InterestDeclaration> SubmitDeclarationAsync(InterestDeclaration declaration, List<InterestDeclarationItem> items, string currentUsername)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        declaration.CreatedAt = DateTime.UtcNow;
        declaration.CreatedBy = currentUsername;
        declaration.StatusCode = declaration.HasConflictsToDeclare ? "FLAGGED_CONFLICT" : "SUBMITTED";

        // Generate SHA-256 digital security seal
        var payload = $"{declaration.PersonId}_{declaration.DeclarationPeriodYear}_{DateTime.UtcNow:yyyyMMddHHmmss}";
        using var sha = System.Security.Cryptography.SHA256.Create();
        var hashBytes = sha.ComputeHash(System.Text.Encoding.UTF8.GetBytes(payload));
        declaration.DigitalSignatureSeal = Convert.ToHexString(hashBytes);

        db.InterestDeclarations.Add(declaration);
        await db.SaveChangesAsync();

        foreach (var item in items)
        {
            item.InterestDeclarationId = declaration.Id;
            item.CreatedAt = DateTime.UtcNow;
            item.CreatedBy = currentUsername;
            db.InterestDeclarationItems.Add(item);
        }

        await db.SaveChangesAsync();

        await _auditService.LogActionAsync(
            "InterestDeclaration",
            declaration.Id,
            "SubmitDeclaration",
            currentUsername,
            null,
            new { declaration.PersonId, declaration.DeclarationPeriodYear, declaration.HasConflictsToDeclare, ItemCount = items.Count });

        return declaration;
    }

    public async Task<InterestDeclaration> CertifyDeclarationAsync(int id, string currentUsername)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var declaration = await db.InterestDeclarations.Include(d => d.Items).FirstOrDefaultAsync(d => d.Id == id);
        if (declaration == null)
            throw new InvalidOperationException($"Declaration with ID {id} not found.");

        declaration.StatusCode = "CERTIFIED";
        declaration.CertifiedAt = DateTime.UtcNow;
        declaration.CertifiedByUserId = currentUsername;
        declaration.ModifiedAt = DateTime.UtcNow;
        declaration.ModifiedBy = currentUsername;

        await db.SaveChangesAsync();

        await _auditService.LogActionAsync(
            "InterestDeclaration",
            declaration.Id,
            "CertifyDeclaration",
            currentUsername,
            null,
            new { Certified = true, declaration.CertifiedAt });

        return declaration;
    }
    #endregion

    #region Conflict Evaluation & Detection Engine
    public async Task<List<ConflictFlag>> EvaluateOrganisationConflictsAsync(int organisationId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var flagsRaised = new List<ConflictFlag>();

        var org = await db.Organisations
            .Include(o => o.GovernanceMembers)
                .ThenInclude(m => m.Person)
            .Include(o => o.GrantApplications)
            .FirstOrDefaultAsync(o => o.Id == organisationId);

        if (org == null) return flagsRaised;

        var activeGovernanceMembers = org.GovernanceMembers.Where(m => m.IsActive).ToList();
        var activeInsiders = await db.InstitutionalAffiliations
            .Include(a => a.Person)
            .Where(a => a.IsActive)
            .ToListAsync();

        var currentSchemeYear = await _configService.GetValueAsync("Governance:CurrentSchemeYear", DateTime.UtcNow.Year.ToString());

        foreach (var member in activeGovernanceMembers)
        {
            if (member.Person == null || !member.PersonId.HasValue) continue;
            var personId = member.PersonId.Value;

            // 1. Check for Insider Affiliation (Employee / Accounting Authority Member / Independent Specialist)
            var matchingAffiliation = activeInsiders.FirstOrDefault(a => a.PersonId == personId);
            if (matchingAffiliation != null)
            {
                var existingFlag = await db.ConflictFlags.FirstOrDefaultAsync(f =>
                    f.TargetOrganisationId == organisationId &&
                    f.PersonId == personId &&
                    f.ConflictCategoryCode == "INSIDER_AFFILIATION" &&
                    (f.ResolutionStatusCode == "OPEN" || f.ResolutionStatusCode == "UNDER_INVESTIGATION"));

                if (existingFlag == null)
                {
                    var flag = new ConflictFlag
                    {
                        TargetOrganisationId = organisationId,
                        PersonId = personId,
                        SeverityCode = "RED_CRITICAL",
                        ConflictCategoryCode = "INSIDER_AFFILIATION",
                        Title = $"Insider Conflict: {matchingAffiliation.Designation} linked to {org.CompanyName}",
                        Description = $"Person {member.Person.FullName} (RSA ID: {member.Person.RsaIdNumber}) is an active merSETA {matchingAffiliation.AffiliationTypeCode} in {matchingAffiliation.DepartmentOrCommittee} and holds {member.GovernanceRoleCode} ({member.ShareholdingPercentage:F2}% shareholding) in {org.CompanyName}. Strict PFMA Section 50/51 prohibition.",
                        DetectedAt = DateTime.UtcNow,
                        ResolutionStatusCode = "OPEN",
                        CreatedAt = DateTime.UtcNow,
                        CreatedBy = currentUsername
                    };
                    db.ConflictFlags.Add(flag);
                    flagsRaised.Add(flag);
                }
            }

            // 2. Check for Multi-Organisation Grant Syndicate
            // Does this person hold directorships or shares in other organisations that have active Grant Applications?
            var otherMemberships = await db.OrganisationGovernanceMembers
                .Include(m => m.Organisation)
                    .ThenInclude(o => o!.GrantApplications)
                .Where(m => m.PersonId == personId && m.OrganisationId != organisationId && m.IsActive)
                .ToListAsync();

            var otherGrantOrgs = otherMemberships
                .Where(m => m.Organisation != null && m.Organisation.GrantApplications.Any(g => g.ApplicationNumber.Contains(currentSchemeYear) || g.CreatedAt.Year == DateTime.UtcNow.Year))
                .ToList();

            if (otherGrantOrgs.Count > 0)
            {
                var existingSyndicateFlag = await db.ConflictFlags.FirstOrDefaultAsync(f =>
                    f.TargetOrganisationId == organisationId &&
                    f.PersonId == personId &&
                    f.ConflictCategoryCode == "MULTI_ORGANISATION_GRANT_SYNDICATE" &&
                    (f.ResolutionStatusCode == "OPEN" || f.ResolutionStatusCode == "UNDER_INVESTIGATION"));

                if (existingSyndicateFlag == null)
                {
                    var otherNames = string.Join(", ", otherGrantOrgs.Select(o => o.Organisation!.CompanyName));
                    var flag = new ConflictFlag
                    {
                        TargetOrganisationId = organisationId,
                        PersonId = personId,
                        SeverityCode = "AMBER_ELEVATED",
                        ConflictCategoryCode = "MULTI_ORGANISATION_GRANT_SYNDICATE",
                        Title = $"Multi-Organisation Grant Syndicate: {member.Person.FullName}",
                        Description = $"Person {member.Person.FullName} (RSA ID: {member.Person.RsaIdNumber}) holds active directorship or beneficial ownership in {org.CompanyName} and {otherGrantOrgs.Count} other grant-applying entities ({otherNames}) in scheme year {currentSchemeYear}.",
                        DetectedAt = DateTime.UtcNow,
                        ResolutionStatusCode = "OPEN",
                        CreatedAt = DateTime.UtcNow,
                        CreatedBy = currentUsername
                    };
                    db.ConflictFlags.Add(flag);
                    flagsRaised.Add(flag);
                }
            }

            // 3. Check for Overdue Annual Declaration of Interest
            var hasDeclaration = await db.InterestDeclarations.AnyAsync(d =>
                d.PersonId == personId &&
                d.DeclarationPeriodYear == currentSchemeYear &&
                (d.StatusCode == "SUBMITTED" || d.StatusCode == "CERTIFIED" || d.StatusCode == "FLAGGED_CONFLICT"));

            if (!hasDeclaration && matchingAffiliation != null)
            {
                var existingOverdueFlag = await db.ConflictFlags.FirstOrDefaultAsync(f =>
                    f.PersonId == personId &&
                    f.ConflictCategoryCode == "OVERDUE_DECLARATION" &&
                    (f.ResolutionStatusCode == "OPEN" || f.ResolutionStatusCode == "UNDER_INVESTIGATION"));

                if (existingOverdueFlag == null)
                {
                    var flag = new ConflictFlag
                    {
                        TargetOrganisationId = organisationId,
                        PersonId = personId,
                        SeverityCode = "YELLOW_ADVISORY",
                        ConflictCategoryCode = "OVERDUE_DECLARATION",
                        Title = $"Overdue Annual Declaration of Interest: {member.Person.FullName}",
                        Description = $"Insider / Director {member.Person.FullName} has not filed the mandatory annual e-DOI for compliance period {currentSchemeYear}.",
                        DetectedAt = DateTime.UtcNow,
                        ResolutionStatusCode = "OPEN",
                        CreatedAt = DateTime.UtcNow,
                        CreatedBy = currentUsername
                    };
                    db.ConflictFlags.Add(flag);
                    flagsRaised.Add(flag);
                }
            }
        }

        // 4. Check Governance Compliance & Thresholds
        var compliance = await ValidateOrganisationGovernanceComplianceAsync(organisationId);
        if (!compliance.IsCompliant)
        {
            var existingGovFlag = await db.ConflictFlags.FirstOrDefaultAsync(f =>
                f.TargetOrganisationId == organisationId &&
                f.ConflictCategoryCode == "ACCREDITATION_GOVERNANCE_BREACH" &&
                (f.ResolutionStatusCode == "OPEN" || f.ResolutionStatusCode == "UNDER_INVESTIGATION"));

            if (existingGovFlag == null)
            {
                var flag = new ConflictFlag
                {
                    TargetOrganisationId = organisationId,
                    PersonId = activeGovernanceMembers.FirstOrDefault()?.PersonId ?? 0,
                    SeverityCode = "AMBER_ELEVATED",
                    ConflictCategoryCode = "ACCREDITATION_GOVERNANCE_BREACH",
                    Title = $"Incomplete Corporate Governance Disclosures: {org.CompanyName}",
                    Description = $"Organisation does not satisfy minimum disclosure requirements: {string.Join("; ", compliance.Violations)}",
                    DetectedAt = DateTime.UtcNow,
                    ResolutionStatusCode = "OPEN",
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = currentUsername
                };
                if (flag.PersonId > 0)
                {
                    db.ConflictFlags.Add(flag);
                    flagsRaised.Add(flag);
                }
            }
        }

        if (flagsRaised.Count > 0)
        {
            await db.SaveChangesAsync();

            foreach (var f in flagsRaised)
            {
                await _auditService.LogActionAsync(
                    "ConflictFlag",
                    f.Id,
                    "RaiseConflictFlag",
                    currentUsername,
                    null,
                    new { f.TargetOrganisationId, f.PersonId, f.SeverityCode, f.ConflictCategoryCode, f.Title });
            }
        }

        return flagsRaised;
    }

    public async Task<List<ConflictFlag>> EvaluateGrantApplicationConflictsAsync(int grantApplicationId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var app = await db.GrantApplications.FirstOrDefaultAsync(g => g.Id == grantApplicationId);
        if (app == null) return new List<ConflictFlag>();

        var flags = await EvaluateOrganisationConflictsAsync(app.OrganisationId, currentUsername);
        foreach (var f in flags)
        {
            f.TargetGrantApplicationId = grantApplicationId;
        }
        if (flags.Count > 0)
        {
            await db.SaveChangesAsync();
        }
        return flags;
    }

    public async Task<List<ConflictFlag>> EvaluateAccreditationConflictsAsync(int trainingProviderId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var provider = await db.TrainingProviders.FirstOrDefaultAsync(t => t.Id == trainingProviderId);
        if (provider == null) return new List<ConflictFlag>();

        var flags = await EvaluateOrganisationConflictsAsync(provider.OrganisationId, currentUsername);
        foreach (var f in flags)
        {
            f.TargetTrainingProviderId = trainingProviderId;
        }
        if (flags.Count > 0)
        {
            await db.SaveChangesAsync();
        }
        return flags;
    }
    #endregion

    #region Conflict Flags & Configurable Clearance
    public async Task<List<ConflictFlag>> GetConflictFlagsAsync(string? severity = null, string? status = null, int? organisationId = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.ConflictFlags
            .Include(f => f.Person)
            .Include(f => f.TargetOrganisation)
            .Include(f => f.TargetGrantApplication)
            .Include(f => f.TargetTrainingProvider)
            .AsQueryable();

        if (!string.IsNullOrWhiteSpace(severity))
        {
            query = query.Where(f => f.SeverityCode == severity);
        }

        if (!string.IsNullOrWhiteSpace(status))
        {
            query = query.Where(f => f.ResolutionStatusCode == status);
        }

        if (organisationId.HasValue)
        {
            query = query.Where(f => f.TargetOrganisationId == organisationId.Value);
        }

        return await query.OrderByDescending(f => f.DetectedAt).ToListAsync();
    }

    public async Task<ConflictFlag?> GetConflictFlagByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.ConflictFlags
            .Include(f => f.Person)
            .Include(f => f.TargetOrganisation)
            .Include(f => f.TargetGrantApplication)
            .Include(f => f.TargetTrainingProvider)
            .FirstOrDefaultAsync(f => f.Id == id);
    }

    public async Task<List<string>> GetAuthorizedClearanceRolesAsync()
    {
        var raw = await _configService.GetValueAsync("Governance:ConflictClearanceAuthority", "RiskAndComplianceManager,InternalAudit,Ceo,AccountingAuthority");
        return (raw ?? "RiskAndComplianceManager,InternalAudit,Ceo,AccountingAuthority")
            .Split(',', StringSplitOptions.RemoveEmptyEntries | StringSplitOptions.TrimEntries)
            .ToList();
    }

    public async Task<ConflictFlag> ResolveConflictFlagAsync(
        int flagId,
        string resolutionStatus,
        string resolutionNotes,
        string clearanceRole,
        string currentUsername)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var flag = await db.ConflictFlags.FirstOrDefaultAsync(f => f.Id == flagId);
        if (flag == null)
            throw new InvalidOperationException($"Conflict flag with ID {flagId} not found.");

        // Configurable clearance role authorization check
        var authorizedRoles = await GetAuthorizedClearanceRolesAsync();
        var isAuthorized = authorizedRoles.Any(r => string.Equals(r, clearanceRole, StringComparison.OrdinalIgnoreCase)) ||
                           string.Equals(currentUsername, "SYSTEM", StringComparison.OrdinalIgnoreCase);

        if (!isAuthorized)
        {
            throw new UnauthorizedAccessException(
                $"Role '{clearanceRole}' is not configured as an authorized statutory clearance authority. Authorized roles: {string.Join(", ", authorizedRoles)}.");
        }

        var beforeState = new { flag.ResolutionStatusCode, flag.ResolutionNotes, flag.ClearedByUserId, flag.ClearedAt };

        flag.ResolutionStatusCode = resolutionStatus;
        flag.ResolutionNotes = resolutionNotes;
        flag.ClearanceAuthorityRole = clearanceRole;
        flag.ClearedByUserId = currentUsername;
        flag.ClearedAt = DateTime.UtcNow;
        flag.ModifiedAt = DateTime.UtcNow;
        flag.ModifiedBy = currentUsername;

        await db.SaveChangesAsync();

        await _auditService.LogActionAsync(
            "ConflictFlag",
            flag.Id,
            "ResolveConflictFlag",
            currentUsername,
            beforeState,
            new { flag.ResolutionStatusCode, flag.ResolutionNotes, flag.ClearanceAuthorityRole, flag.ClearedByUserId, flag.ClearedAt });

        return flag;
    }
    #endregion

    #region Dashboard & Statutory Reports
    public async Task<ConflictDashboardMetricsDto> GetDashboardMetricsAsync()
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var flags = await db.ConflictFlags
            .Include(f => f.Person)
            .Include(f => f.TargetOrganisation)
            .ToListAsync();

        var threshold = await _configService.GetValueAsync<decimal>("Governance:MinimumShareholdingDisclosureThresholdPercent", 5.0m);
        var clearanceRoles = await GetAuthorizedClearanceRolesAsync();

        var currentSchemeYear = await _configService.GetValueAsync("Governance:CurrentSchemeYear", DateTime.UtcNow.Year.ToString());
        var pendingDeclarations = await db.InstitutionalAffiliations
            .Where(a => a.IsActive && !db.InterestDeclarations.Any(d => d.PersonId == a.PersonId && d.DeclarationPeriodYear == currentSchemeYear))
            .CountAsync();

        // Compute top shared directors across multiple organisations
        var activeMembers = await db.OrganisationGovernanceMembers
            .Include(m => m.Person)
            .Include(m => m.Organisation)
                .ThenInclude(o => o!.GrantApplications)
            .Where(m => m.IsActive && m.PersonId.HasValue)
            .ToListAsync();

        var sharedDirectorGroups = activeMembers
            .GroupBy(m => m.PersonId!.Value)
            .Where(g => g.Select(m => m.OrganisationId).Distinct().Count() > 1)
            .ToList();

        var topShared = new List<TopSharedDirectorDto>();
        foreach (var group in sharedDirectorGroups)
        {
            var first = group.First();
            if (first.Person == null || !first.PersonId.HasValue) continue;

            var orgs = group.Select(m => m.Organisation).Where(o => o != null).Distinct().ToList();
            var allGrants = orgs.SelectMany(o => o!.GrantApplications).ToList();

            topShared.Add(new TopSharedDirectorDto
            {
                PersonId = first.PersonId.Value,
                FullName = first.Person.FullName,
                RsaIdNumber = first.Person.RsaIdNumber ?? "",
                LinkedOrganisationCount = orgs.Count,
                ActiveGrantApplicationCount = allGrants.Count,
                TotalRequestedGrantAmount = allGrants.Sum(g => g.RequestedAmount),
                OrganisationNames = orgs.Select(o => o!.CompanyName).ToList()
            });
        }

        // Chamber distribution
        var chamberStats = await db.Organisations
            .Where(o => db.ConflictFlags.Any(f => f.TargetOrganisationId == o.Id && (f.ResolutionStatusCode == "OPEN" || f.ResolutionStatusCode == "UNDER_INVESTIGATION")))
            .GroupBy(o => o.ChamberCode ?? "OTHER")
            .Select(g => new ChamberConflictCountDto
            {
                ChamberCode = g.Key,
                ChamberName = g.Key,
                ConflictCount = g.Count()
            })
            .ToListAsync();

        return new ConflictDashboardMetricsDto
        {
            TotalActiveFlags = flags.Count(f => f.ResolutionStatusCode == "OPEN" || f.ResolutionStatusCode == "UNDER_INVESTIGATION"),
            RedCriticalCount = flags.Count(f => f.SeverityCode == "RED_CRITICAL" && (f.ResolutionStatusCode == "OPEN" || f.ResolutionStatusCode == "UNDER_INVESTIGATION")),
            AmberElevatedCount = flags.Count(f => f.SeverityCode == "AMBER_ELEVATED" && (f.ResolutionStatusCode == "OPEN" || f.ResolutionStatusCode == "UNDER_INVESTIGATION")),
            YellowAdvisoryCount = flags.Count(f => f.SeverityCode == "YELLOW_ADVISORY" && (f.ResolutionStatusCode == "OPEN" || f.ResolutionStatusCode == "UNDER_INVESTIGATION")),
            ClearedFlagsCount = flags.Count(f => f.ResolutionStatusCode == "CLEARED_WITH_JUSTIFICATION"),
            MultiEntitySyndicateCount = topShared.Count,
            OutstandingDeclarationsCount = pendingDeclarations,
            ConfiguredShareholdingThresholdPercent = threshold,
            ConfiguredClearanceRoles = clearanceRoles,
            TopSharedDirectors = topShared.OrderByDescending(t => t.LinkedOrganisationCount).Take(10).ToList(),
            ChamberDistribution = chamberStats,
            RecentFlags = flags.OrderByDescending(f => f.DetectedAt).Take(8).ToList()
        };
    }

    public async Task<List<AgsConflictReportItemDto>> GenerateAgsConflictReportAsync(string schemeYear)
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var flags = await db.ConflictFlags
            .Include(f => f.Person)
                .ThenInclude(p => p!.InstitutionalAffiliations)
            .Include(f => f.TargetOrganisation)
                .ThenInclude(o => o!.GovernanceMembers)
            .OrderByDescending(f => f.DetectedAt)
            .ToListAsync();

        var result = new List<AgsConflictReportItemDto>();
        foreach (var f in flags)
        {
            var person = f.Person;
            var org = f.TargetOrganisation;
            var affiliation = person?.InstitutionalAffiliations.FirstOrDefault(a => a.IsActive);
            var govMember = org?.GovernanceMembers.FirstOrDefault(m => m.PersonId == f.PersonId);

            result.Add(new AgsConflictReportItemDto
            {
                ConflictFlagId = f.Id,
                ReferenceNumber = $"FLG-{f.Id:D5}",
                SeverityCode = f.SeverityCode,
                ConflictCategoryCode = f.ConflictCategoryCode,
                PersonFullName = person?.FullName ?? "Unknown",
                PersonRsaId = person?.RsaIdNumber ?? "",
                InstitutionalAffiliation = affiliation != null ? $"{affiliation.AffiliationTypeCode} ({affiliation.DepartmentOrCommittee})" : "None (External)",
                OrganisationName = org?.CompanyName ?? "N/A",
                OrganisationSdl = org?.SdlNumber ?? "N/A",
                GovernanceRole = govMember?.GovernanceRoleCode ?? "N/A",
                ShareholdingPercentage = govMember?.ShareholdingPercentage ?? 0.00m,
                ResolutionStatus = f.ResolutionStatusCode,
                ClearanceRole = f.ClearanceAuthorityRole,
                ClearedBy = f.ClearedByUserId,
                ClearedDate = f.ClearedAt
            });
        }

        return result;
    }

    public async Task<List<MultiEntityGrantExposureDto>> GenerateMultiEntityGrantExposureReportAsync(string schemeYear)
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var activeMembers = await db.OrganisationGovernanceMembers
            .Include(m => m.Person)
            .Include(m => m.Organisation)
                .ThenInclude(o => o!.GrantApplications)
            .Where(m => m.IsActive && m.PersonId.HasValue)
            .ToListAsync();

        var sharedMembers = activeMembers
            .GroupBy(m => m.PersonId!.Value)
            .Where(g => g.Select(m => m.OrganisationId).Distinct().Count() > 1)
            .ToList();

        var result = new List<MultiEntityGrantExposureDto>();
        foreach (var group in sharedMembers)
        {
            var first = group.First();
            if (first.Person == null || !first.PersonId.HasValue) continue;

            var dto = new MultiEntityGrantExposureDto
            {
                PersonId = first.PersonId.Value,
                FullName = first.Person.FullName,
                RsaIdNumber = first.Person.RsaIdNumber ?? "",
                TotalLinkedEntities = group.Select(m => m.OrganisationId).Distinct().Count()
            };

            foreach (var m in group)
            {
                if (m.Organisation == null) continue;
                var grants = m.Organisation.GrantApplications.ToList();

                foreach (var g in grants)
                {
                    dto.Entities.Add(new LinkedEntityGrantItemDto
                    {
                        OrganisationId = m.OrganisationId,
                        CompanyName = m.Organisation.CompanyName,
                        SdlNumber = m.Organisation.SdlNumber,
                        RoleInEntity = m.GovernanceRoleCode,
                        ShareholdingPercentage = m.ShareholdingPercentage,
                        ApplicationNumber = g.ApplicationNumber,
                        GrantStatusCode = g.StatusCode,
                        RequestedAmount = g.RequestedAmount
                    });
                }
            }

            dto.TotalGrantApplications = dto.Entities.Count;
            dto.TotalRequestedAmount = dto.Entities.Sum(e => e.RequestedAmount);
            result.Add(dto);
        }

        return result.OrderByDescending(r => r.TotalRequestedAmount).ToList();
    }
    #endregion
}
