using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;
using Nsdms.Domain.Lookups;

namespace Nsdms.Application.Services;

public interface IGrantService
{
    Task<GrantFundingWindow> CreateFundingWindowAsync(GrantFundingWindow window, string currentUsername = "SYSTEM");
    Task<GrantFundingWindow> UpdateFundingWindowAsync(GrantFundingWindow window, string currentUsername = "SYSTEM");
    Task<GrantFundingWindow> SaveFundingWindowAsync(GrantFundingWindow window, string currentUsername = "SYSTEM");
    Task<List<GrantFundingWindow>> GetFundingWindowsAsync(int? finYear = null, bool activeOnly = true);
    Task<GrantFundingWindow?> GetFundingWindowByIdAsync(int id);
    Task<bool> CloseFundingWindowAsync(int id, string currentUsername = "SYSTEM");
    Task<bool> DeleteFundingWindowAsync(int id, string currentUsername = "SYSTEM");
    Task<FundingWindowSummaryDto> GetFundingWindowSummaryAsync(int id);

    // Dual Authorisation Governance (Segregation of Duties)
    Task<GrantFundingWindow> ProposeFundingWindowAsync(int windowId, string currentUsername = "SYSTEM");
    Task<GrantFundingWindow> ApproveAndActivateFundingWindowAsync(int windowId, string currentUsername = "SYSTEM", string? justification = null);
    Task<GrantFundingWindow> RejectFundingWindowAsync(int windowId, string currentUsername = "SYSTEM", string reason = "");

    // Real-Time Budget Consumption Evaluation
    Task<BudgetConsumptionResult> EvaluateBudgetConsumptionAsync(int fundingWindowPriorityId, decimal incomingAmount);

    // Blueprint Templates (Option B)
    Task<List<GrantWindowTemplate>> GetWindowTemplatesAsync(bool activeOnly = true);
    Task<GrantWindowTemplate?> GetWindowTemplateByIdAsync(int templateId);
    Task<GrantFundingWindow> InitializeWindowFromTemplateAsync(int templateId, int finYear, string windowName, DateTime openingDate, DateTime closingDate, decimal totalBudget, string currentUsername = "SYSTEM");

    // Stakeholder Eligibility Classifications
    Task<List<StakeholderEligibilityType>> GetStakeholderEligibilityTypesAsync(bool activeOnly = true);
    Task<List<GrantWindowEligibility>> GetWindowEligibilitiesAsync(int windowId);
    Task SetWindowEligibilitiesAsync(int windowId, IEnumerable<string> stakeholderTypeCodes, string currentUsername = "SYSTEM");

    // Skills Development & Project Intervention Catalog (PIVOTAL & Non-PIVOTAL)
    Task<List<InterventionType>> GetInterventionCatalogAsync(bool? isPivotal = null, bool activeOnly = true);
    Task<InterventionType> AddInterventionToCatalogAsync(InterventionType intervention, string currentUsername = "SYSTEM");
    Task<List<GrantWindowIntervention>> GetWindowInterventionsAsync(int windowId);
    Task SetWindowInterventionsAsync(int windowId, IEnumerable<string> interventionTypeCodes, string currentUsername = "SYSTEM");

    Task<GrantApplication> CreateApplicationAsync(GrantApplication application, string currentUsername = "SYSTEM");
    Task<GrantApplication> SubmitApplicationAsync(GrantApplication application, string currentUsername = "SYSTEM");
    Task<GrantApplication> UpdateApplicationAsync(GrantApplication application, string currentUsername = "SYSTEM");
    Task<GrantApplication> SaveAsync(GrantApplication application, string currentUsername = "SYSTEM");
    Task<GrantApplication?> GetByIdAsync(int id);
    Task<GrantApplication?> GetApplicationByIdAsync(int id);
    Task<List<GrantApplication>> GetApplicationsByOrganisationAsync(int organisationId);
    Task<List<GrantApplication>> GetAllAsync(string? search = null, string? grantType = null, string? status = null);
    Task<List<GrantApplication>> GetAllApplicationsAsync(int? fundingWindowId = null, string? search = null, string? grantType = null, string? status = null);
    Task<GrantApplication> ApproveApplicationAsync(int id, decimal approvedAmount, string currentUsername = "SYSTEM");
    Task<GrantApplication> RejectApplicationAsync(int id, string reason, string currentUsername = "SYSTEM");
    Task<bool> DeleteAsync(int id, string currentUsername = "SYSTEM");
    Task<bool> DeleteApplicationAsync(int id, string currentUsername = "SYSTEM");

    // Composite PIVOTAL and Non-PIVOTAL Application Interventions (Option A)
    Task<List<GrantApplicationIntervention>> GetApplicationInterventionsAsync(int applicationId);
    Task<GrantApplicationIntervention> AddApplicationInterventionAsync(int applicationId, GrantApplicationIntervention intervention, string currentUsername = "SYSTEM");
    Task<GrantApplicationIntervention> UpdateApplicationInterventionAsync(GrantApplicationIntervention intervention, string currentUsername = "SYSTEM");
    Task<bool> RemoveApplicationInterventionAsync(int interventionId, string currentUsername = "SYSTEM");
    Task SetApplicationInterventionsAsync(int applicationId, IEnumerable<GrantApplicationIntervention> interventions, string currentUsername = "SYSTEM");

    Task<GrantProjectBudget> AddBudgetItemAsync(GrantProjectBudget budgetItem, string currentUsername = "SYSTEM");
    Task<GrantProjectBudget> AddBudgetItemAsync(int applicationId, GrantProjectBudget budgetItem, string currentUsername = "SYSTEM");
    Task<GrantProjectBudget> AddProjectBudgetAsync(GrantProjectBudget budgetItem, string currentUsername = "SYSTEM");
    Task<GrantProjectBudget> AddProjectBudgetAsync(int applicationId, GrantProjectBudget budgetItem, string currentUsername = "SYSTEM");
    Task<List<GrantProjectBudget>> GetProjectBudgetsAsync(int applicationId);
    Task<decimal> RecalculateApplicationBudgetAsync(int applicationId);
    Task<bool> RemoveBudgetItemAsync(int budgetId, string currentUsername = "SYSTEM");
    // 360-Degree Relational MoA link
    Task<GrantMoa?> GetGrantMoaByApplicationIdAsync(int applicationId);

    // Statutory WSP Eligibility & 1-Click MoA Provisioning
    Task<WspEligibilityResult> EvaluateWspEligibilityAsync(int organisationId, int? finYear = null);
    Task<GrantMoa> GenerateGrantMoaFromApplicationAsync(int grantApplicationId, string currentUsername = "SYSTEM");
    // Strategic Priorities & Key Focus Areas
    Task<List<StrategicPriority>> GetStrategicPrioritiesAsync(bool activeOnly = true);
    Task<StrategicPriority?> GetStrategicPriorityByIdAsync(int id);
    Task<StrategicPriority> SaveStrategicPriorityAsync(StrategicPriority priority, string currentUsername = "SYSTEM");
    Task<bool> DeleteStrategicPriorityAsync(int id, string currentUsername = "SYSTEM");

    // Funding Window Strategic Priority Allocations
    Task<List<FundingWindowPriority>> GetWindowPrioritiesAsync(int windowId);
    Task<FundingWindowPriority> AddWindowPriorityAsync(FundingWindowPriority windowPriority, string currentUsername = "SYSTEM");
    Task<FundingWindowPriority> UpdateWindowPriorityAsync(FundingWindowPriority windowPriority, string currentUsername = "SYSTEM");
    Task<bool> RemoveWindowPriorityAsync(int windowPriorityId, string currentUsername = "SYSTEM");

    // 3-Tier Strategic BI Intelligence Reporting
    Task<StrategicReportDashboardDto> GetStrategicReportDashboardAsync(int? finYear = null, int? windowId = null);
}

public record FundingWindowSummaryDto(
    int WindowId,
    string WindowName,
    int FinYear,
    string? GrantTypeCode,
    DateTime OpeningDate,
    DateTime ClosingDate,
    decimal TotalAvailableBudget,
    bool IsActive,
    bool IsOpen,
    int TotalApplications,
    decimal TotalRequestedAmount,
    decimal TotalApprovedAmount,
    decimal RemainingBudget
);

public record BudgetConsumptionResult(
    int PriorityId,
    string PriorityCode,
    string PriorityName,
    decimal AllocatedBudget,
    decimal CurrentCommittedAmount,
    decimal RemainingBudget,
    bool IsOverSubscribed,
    decimal OverSubscriptionAmount,
    string AdvisoryMessage
);

public record WspEligibilityResult(
    bool IsEligible,
    bool IsExempt,
    string Status,
    int? WspSubmissionId,
    string Reason,
    string? WspReferenceNumber = null,
    DateTime? ApprovalDate = null
);

public record StrategicReportDashboardDto(
    int TotalPrioritiesCount,
    decimal TotalAllocatedBudget,
    decimal TotalRequestedAmount,
    decimal TotalApprovedAmount,
    decimal BudgetAbsorptionRate,
    int TotalLearnersTarget,
    int TotalLearnersActual,
    List<ThemeOperationalDto> OperationalThemes,
    List<ProvincialTacticalDto> TacticalProvinces,
    List<NsdpStrategicDto> StrategicNsdpOutcomes,
    TransformationEquityDto TransformationEquity
);

public record ThemeOperationalDto(
    int PriorityId,
    string PriorityCode,
    string PriorityName,
    string NsdpOutcomeCode,
    string? SipCategory,
    decimal AllocatedBudget,
    decimal RequestedAmount,
    decimal ApprovedAmount,
    decimal RemainingBudget,
    decimal BurnRatePercent,
    int TargetBeneficiaries,
    int ActualApplicationsCount,
    int ApprovedCount,
    int UnderReviewCount,
    int DraftCount,
    int RejectedCount,
    bool IsRingFenced
);

public record ProvincialTacticalDto(
    string ProvinceName,
    int ApplicationCount,
    decimal TotalRequested,
    decimal TotalApproved,
    int BeneficiariesCount,
    decimal SmeAllocationPercent,
    decimal LargeEnterprisePercent
);

public record NsdpStrategicDto(
    string OutcomeCode,
    string OutcomeTitle,
    int SupportedThemesCount,
    decimal TotalBudgetCommitted,
    decimal TotalDisbursed,
    int BeneficiariesAwarded,
    decimal TargetAchievementPercent
);

public record TransformationEquityDto(
    decimal FemalePercent,
    decimal YouthPercent,
    decimal DisabledPercent,
    decimal RuralPercent,
    decimal BlackOwnershipPercent
);

public class GrantService : IGrantService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;
    private readonly ISystemConfigurationService? _configService;

    public GrantService(
        INsdmsDbContextFactory contextFactory,
        IAuditService audit,
        ISystemConfigurationService? configService = null)
    {
        _contextFactory = contextFactory;
        _audit = audit;
        _configService = configService;
    }

    public async Task<GrantFundingWindow> CreateFundingWindowAsync(GrantFundingWindow window, string currentUsername = "SYSTEM")
    {
        if (string.IsNullOrWhiteSpace(window.WindowName))
        {
            throw new ArgumentException("Window name is required.");
        }

        if (window.FinYear <= 0)
        {
            window.FinYear = DateTime.UtcNow.Year;
        }

        if (window.ClosingDate < window.OpeningDate && window.ClosingDate != default)
        {
            throw new ArgumentException("Closing date cannot be earlier than opening date.");
        }

        if (string.IsNullOrWhiteSpace(window.ApprovalStatusCode))
        {
            window.ApprovalStatusCode = window.IsActive ? "Active" : "Draft";
        }

        using var db = await _contextFactory.CreateDbContextAsync();
        window.CreatedAt = DateTime.UtcNow;
        window.CreatedBy = currentUsername;

        db.GrantFundingWindows.Add(window);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "GrantFundingWindow", window.Id, "CreateFundingWindow", currentUsername, null, window);
        await db.SaveChangesAsync();

        return window;
    }

    public async Task<List<GrantFundingWindow>> GetFundingWindowsAsync(int? finYear = null, bool activeOnly = true)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.GrantFundingWindows.AsQueryable();

        if (finYear.HasValue)
        {
            query = query.Where(w => w.FinYear == finYear.Value);
        }

        if (activeOnly)
        {
            query = query.Where(w => w.IsActive && w.ClosingDate >= DateTime.UtcNow);
        }

        return await query
            .OrderByDescending(w => w.FinYear)
            .ThenByDescending(w => w.OpeningDate)
            .ToListAsync();
    }

    public async Task<GrantFundingWindow?> GetFundingWindowByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.GrantFundingWindows
            .Include(w => w.Applications)
                .ThenInclude(a => a.Organisation)
            .Include(w => w.Applications)
                .ThenInclude(a => a.StrategicPriority)
            .Include(w => w.StrategicPriorities)
                .ThenInclude(p => p.StrategicPriority)
            .Include(w => w.EligibleStakeholders)
                .ThenInclude(e => e.StakeholderEligibilityType)
            .Include(w => w.AllowedInterventions)
                .ThenInclude(i => i.InterventionType)
            .Include(w => w.Template)
            .FirstOrDefaultAsync(w => w.Id == id);
    }

    public async Task<GrantFundingWindow> UpdateFundingWindowAsync(GrantFundingWindow window, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.GrantFundingWindows.FindAsync(window.Id);
        if (existing == null)
        {
            throw new KeyNotFoundException($"Funding window #{window.Id} not found.");
        }

        var beforeState = new
        {
            existing.WindowName,
            existing.FinYear,
            existing.GrantTypeCode,
            existing.OpeningDate,
            existing.ClosingDate,
            existing.TotalAvailableBudget,
            existing.IsActive,
            existing.ApprovalStatusCode,
            existing.Description,
            existing.IsPivotal,
            existing.WindowClassification,
            existing.RequireWspCompliance,
            existing.TemplateId
        };

        existing.WindowName = window.WindowName;
        existing.FinYear = window.FinYear;
        existing.GrantTypeCode = window.GrantTypeCode;
        existing.OpeningDate = window.OpeningDate;
        existing.ClosingDate = window.ClosingDate;
        existing.TotalAvailableBudget = window.TotalAvailableBudget;
        existing.IsActive = window.IsActive;
        existing.ApprovalStatusCode = window.ApprovalStatusCode;
        existing.Description = window.Description;
        existing.IsPivotal = window.IsPivotal;
        existing.WindowClassification = window.WindowClassification;
        existing.RequireWspCompliance = window.RequireWspCompliance;
        existing.TemplateId = window.TemplateId;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        _audit.LogAction(db, "GrantFundingWindow", existing.Id, "UpdateFundingWindow", currentUsername, beforeState, existing);
        await db.SaveChangesAsync();

        return existing;
    }

    public async Task<GrantFundingWindow> SaveFundingWindowAsync(GrantFundingWindow window, string currentUsername = "SYSTEM")
    {
        if (window.Id == 0)
        {
            return await CreateFundingWindowAsync(window, currentUsername);
        }

        return await UpdateFundingWindowAsync(window, currentUsername);
    }

    public async Task<bool> CloseFundingWindowAsync(int id, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var window = await db.GrantFundingWindows.FindAsync(id);
        if (window == null)
        {
            return false;
        }

        var beforeState = new { window.IsActive, window.ApprovalStatusCode, window.ClosingDate };
        window.IsActive = false;
        window.ApprovalStatusCode = "Closed";
        window.ClosingDate = DateTime.UtcNow;
        window.ModifiedAt = DateTime.UtcNow;
        window.ModifiedBy = currentUsername;

        _audit.LogAction(db, "GrantFundingWindow", id, "CloseFundingWindow", currentUsername, beforeState, window);
        await db.SaveChangesAsync();

        return true;
    }

    public async Task<bool> DeleteFundingWindowAsync(int id, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var window = await db.GrantFundingWindows
            .Include(w => w.Applications)
            .FirstOrDefaultAsync(w => w.Id == id);

        if (window == null)
        {
            return false;
        }

        if (window.Applications.Any())
        {
            throw new InvalidOperationException($"Cannot delete Funding Window #{id} because it has {window.Applications.Count} linked application(s). Deactivate or close the window instead.");
        }

        var beforeState = new { window.Id, window.WindowName, window.FinYear };
        db.GrantFundingWindows.Remove(window);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "GrantFundingWindow", id, "DeleteFundingWindow", currentUsername, beforeState, null);
        await db.SaveChangesAsync();

        return true;
    }

    public async Task<GrantFundingWindow> ProposeFundingWindowAsync(int windowId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var window = await db.GrantFundingWindows.FindAsync(windowId);
        if (window == null)
        {
            throw new KeyNotFoundException($"Funding window #{windowId} not found.");
        }

        if (window.ClosingDate < window.OpeningDate)
        {
            throw new InvalidOperationException("Cannot propose funding window: Closing date cannot precede opening date.");
        }

        if (window.TotalAvailableBudget <= 0)
        {
            throw new InvalidOperationException("Cannot propose funding window: Total available budget must be greater than zero.");
        }

        var beforeState = new { window.ApprovalStatusCode, window.ProposedByUserId, window.ProposedDate };
        window.ApprovalStatusCode = "PendingApproval";
        window.ProposedByUserId = currentUsername;
        window.ProposedDate = DateTime.UtcNow;
        window.ModifiedAt = DateTime.UtcNow;
        window.ModifiedBy = currentUsername;

        _audit.LogAction(db, "GrantFundingWindow", window.Id, "ProposeFundingWindow", currentUsername, beforeState, window);
        await db.SaveChangesAsync();

        return window;
    }

    public async Task<GrantFundingWindow> ApproveAndActivateFundingWindowAsync(int windowId, string currentUsername = "SYSTEM", string? justification = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var window = await db.GrantFundingWindows.FindAsync(windowId);
        if (window == null)
        {
            throw new KeyNotFoundException($"Funding window #{windowId} not found.");
        }

        // Segregation of Duties Invariant: Proposer cannot approve their own window
        if (!string.IsNullOrWhiteSpace(window.ProposedByUserId) &&
            string.Equals(window.ProposedByUserId, currentUsername, StringComparison.OrdinalIgnoreCase))
        {
            throw new InvalidOperationException($"Dual Authorisation Governance Violation: Proposing officer '{currentUsername}' cannot approve and activate their own DG funding window. An independent authority review is required.");
        }

        var beforeState = new { window.ApprovalStatusCode, window.IsActive, window.ApprovedByUserId, window.ApprovedDate };
        window.ApprovalStatusCode = "Active";
        window.IsActive = true;
        window.ApprovedByUserId = currentUsername;
        window.ApprovedDate = DateTime.UtcNow;
        window.ApprovalJustification = justification ?? "Gazetted and activated under delegated executive authority.";
        window.ModifiedAt = DateTime.UtcNow;
        window.ModifiedBy = currentUsername;

        _audit.LogAction(db, "GrantFundingWindow", window.Id, "ApproveAndActivateFundingWindow", currentUsername, beforeState, window);
        await db.SaveChangesAsync();

        return window;
    }

    public async Task<GrantFundingWindow> RejectFundingWindowAsync(int windowId, string currentUsername = "SYSTEM", string reason = "")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var window = await db.GrantFundingWindows.FindAsync(windowId);
        if (window == null)
        {
            throw new KeyNotFoundException($"Funding window #{windowId} not found.");
        }

        var beforeState = new { window.ApprovalStatusCode, window.IsActive };
        window.ApprovalStatusCode = "Rejected";
        window.IsActive = false;
        window.ApprovalJustification = reason;
        window.ModifiedAt = DateTime.UtcNow;
        window.ModifiedBy = currentUsername;

        _audit.LogAction(db, "GrantFundingWindow", window.Id, "RejectFundingWindow", currentUsername, beforeState, window);
        await db.SaveChangesAsync();

        return window;
    }

    public async Task<BudgetConsumptionResult> EvaluateBudgetConsumptionAsync(int fundingWindowPriorityId, decimal incomingAmount)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var priority = await db.FundingWindowPriorities
            .Include(p => p.StrategicPriority)
            .FirstOrDefaultAsync(p => p.Id == fundingWindowPriorityId);

        if (priority == null)
        {
            throw new KeyNotFoundException($"Funding window strategic priority #{fundingWindowPriorityId} not found.");
        }

        // Sum current requested amounts for applications submitted under this theme in this window
        var currentCommitted = await db.GrantApplications
            .Where(a => a.FundingWindowPriorityId == fundingWindowPriorityId && a.ApplicationStatusCode != "Rejected")
            .SumAsync(a => (decimal?)a.RequestedAmount) ?? 0m;

        var projectedTotal = currentCommitted + incomingAmount;
        var remaining = priority.AllocatedBudget - currentCommitted;
        var isOverSubscribed = projectedTotal > priority.AllocatedBudget;
        var overSubscription = isOverSubscribed ? projectedTotal - priority.AllocatedBudget : 0m;

        var advisory = isOverSubscribed
            ? $"Strategic theme '{priority.StrategicPriority?.Name ?? priority.StrategicPriorityId.ToString()}' is over-subscribed by {overSubscription:C2} (Allocated: {priority.AllocatedBudget:C2}, Committed + Incoming: {projectedTotal:C2})."
            : $"Budget within allocated envelope. Remaining capacity: {remaining - incomingAmount:C2}.";

        return new BudgetConsumptionResult(
            PriorityId: priority.Id,
            PriorityCode: priority.StrategicPriority?.Code ?? "THEME",
            PriorityName: priority.StrategicPriority?.Name ?? "Strategic Theme",
            AllocatedBudget: priority.AllocatedBudget,
            CurrentCommittedAmount: currentCommitted,
            RemainingBudget: remaining,
            IsOverSubscribed: isOverSubscribed,
            OverSubscriptionAmount: overSubscription,
            AdvisoryMessage: advisory
        );
    }

    public async Task<FundingWindowSummaryDto> GetFundingWindowSummaryAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var window = await db.GrantFundingWindows
            .Include(w => w.Applications)
            .FirstOrDefaultAsync(w => w.Id == id);

        if (window == null)
        {
            throw new KeyNotFoundException($"Funding window #{id} not found.");
        }

        var apps = window.Applications.ToList();
        var totalApps = apps.Count;
        var totalRequested = apps.Sum(a => a.RequestedAmount);
        var totalApproved = apps.Where(a => a.ApprovedAmount.HasValue).Sum(a => a.ApprovedAmount!.Value);
        var remaining = window.TotalAvailableBudget - totalApproved;

        return new FundingWindowSummaryDto(
            WindowId: window.Id,
            WindowName: window.WindowName,
            FinYear: window.FinYear,
            GrantTypeCode: window.GrantTypeCode,
            OpeningDate: window.OpeningDate,
            ClosingDate: window.ClosingDate,
            TotalAvailableBudget: window.TotalAvailableBudget,
            IsActive: window.IsActive,
            IsOpen: window.IsOpen,
            TotalApplications: totalApps,
            TotalRequestedAmount: totalRequested,
            TotalApprovedAmount: totalApproved,
            RemainingBudget: remaining
        );
    }

    // Blueprint Templates (Option B)
    public async Task<List<GrantWindowTemplate>> GetWindowTemplatesAsync(bool activeOnly = true)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.GrantWindowTemplates
            .Include(t => t.DefaultEligibilities)
                .ThenInclude(e => e.StakeholderEligibilityType)
            .Include(t => t.DefaultInterventions)
                .ThenInclude(i => i.InterventionType)
            .AsQueryable();

        if (activeOnly)
        {
            query = query.Where(t => t.IsActive);
        }

        return await query.OrderBy(t => t.Name).ToListAsync();
    }

    public async Task<GrantWindowTemplate?> GetWindowTemplateByIdAsync(int templateId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.GrantWindowTemplates
            .Include(t => t.DefaultEligibilities)
                .ThenInclude(e => e.StakeholderEligibilityType)
            .Include(t => t.DefaultInterventions)
                .ThenInclude(i => i.InterventionType)
            .FirstOrDefaultAsync(t => t.Id == templateId);
    }

    public async Task<GrantFundingWindow> InitializeWindowFromTemplateAsync(
        int templateId,
        int finYear,
        string windowName,
        DateTime openingDate,
        DateTime closingDate,
        decimal totalBudget,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var template = await db.GrantWindowTemplates
            .Include(t => t.DefaultEligibilities)
            .Include(t => t.DefaultInterventions)
            .FirstOrDefaultAsync(t => t.Id == templateId);

        if (template == null)
        {
            throw new KeyNotFoundException($"Grant window blueprint template #{templateId} not found.");
        }

        var window = new GrantFundingWindow
        {
            TemplateId = template.Id,
            WindowName = string.IsNullOrWhiteSpace(windowName) ? $"{finYear} {template.Name}" : windowName,
            FinYear = finYear > 0 ? finYear : DateTime.UtcNow.Year,
            GrantTypeCode = template.IsPivotal ? "PIVOTAL" : "PROJECT",
            IsPivotal = template.IsPivotal,
            WindowClassification = template.WindowClassification,
            RequireWspCompliance = template.RequireWspComplianceDefault,
            OpeningDate = openingDate != default ? openingDate : DateTime.UtcNow,
            ClosingDate = closingDate != default ? closingDate : DateTime.UtcNow.AddDays(template.EstimatedDurationDays),
            TotalAvailableBudget = totalBudget,
            IsActive = true,
            ApprovalStatusCode = "Draft",
            Description = template.Description,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.GrantFundingWindows.Add(window);
        await db.SaveChangesAsync();

        // Copy template eligibilities
        foreach (var el in template.DefaultEligibilities)
        {
            db.GrantWindowEligibilities.Add(new GrantWindowEligibility
            {
                FundingWindowId = window.Id,
                StakeholderEligibilityTypeCode = el.StakeholderEligibilityTypeCode,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = currentUsername
            });
        }

        // Copy template interventions
        foreach (var it in template.DefaultInterventions)
        {
            db.GrantWindowInterventions.Add(new GrantWindowIntervention
            {
                FundingWindowId = window.Id,
                InterventionTypeCode = it.InterventionTypeCode,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = currentUsername
            });
        }

        await db.SaveChangesAsync();

        _audit.LogAction(db, "GrantFundingWindow", window.Id, "InitializeWindowFromTemplate", currentUsername, null, new { TemplateId = template.Id, Window = window });
        await db.SaveChangesAsync();

        return window;
    }

    // Stakeholder Eligibility Classifications
    public async Task<List<StakeholderEligibilityType>> GetStakeholderEligibilityTypesAsync(bool activeOnly = true)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.StakeholderEligibilityTypes.AsQueryable();
        if (activeOnly)
        {
            query = query.Where(s => s.Active);
        }
        return await query.OrderBy(s => s.Name).ToListAsync();
    }

    public async Task<List<GrantWindowEligibility>> GetWindowEligibilitiesAsync(int windowId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.GrantWindowEligibilities
            .Include(e => e.StakeholderEligibilityType)
            .Where(e => e.FundingWindowId == windowId)
            .ToListAsync();
    }

    public async Task SetWindowEligibilitiesAsync(int windowId, IEnumerable<string> stakeholderTypeCodes, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.GrantWindowEligibilities
            .Where(e => e.FundingWindowId == windowId)
            .ToListAsync();

        var beforeState = existing.Select(e => e.StakeholderEligibilityTypeCode).ToList();
        db.GrantWindowEligibilities.RemoveRange(existing);

        var codesToAdd = stakeholderTypeCodes.Distinct().ToList();
        foreach (var code in codesToAdd)
        {
            db.GrantWindowEligibilities.Add(new GrantWindowEligibility
            {
                FundingWindowId = windowId,
                StakeholderEligibilityTypeCode = code,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = currentUsername
            });
        }

        _audit.LogAction(db, "GrantFundingWindow", windowId, "SetWindowEligibilities", currentUsername, beforeState, codesToAdd);
        await db.SaveChangesAsync();
    }

    // Skills Development & Project Intervention Catalog (PIVOTAL & Non-PIVOTAL)
    public async Task<List<InterventionType>> GetInterventionCatalogAsync(bool? isPivotal = null, bool activeOnly = true)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.InterventionTypes.AsQueryable();

        if (activeOnly)
        {
            query = query.Where(i => i.Active);
        }

        if (isPivotal.HasValue)
        {
            query = query.Where(i => i.IsPivotal == isPivotal.Value);
        }

        return await query.OrderBy(i => i.IsPivotal ? 0 : 1).ThenBy(i => i.Name).ToListAsync();
    }

    public async Task<InterventionType> AddInterventionToCatalogAsync(InterventionType intervention, string currentUsername = "SYSTEM")
    {
        if (string.IsNullOrWhiteSpace(intervention.Code))
        {
            throw new ArgumentException("Intervention code is required.");
        }

        if (string.IsNullOrWhiteSpace(intervention.Name))
        {
            throw new ArgumentException("Intervention name is required.");
        }

        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.InterventionTypes.FindAsync(intervention.Code);
        if (existing != null)
        {
            throw new InvalidOperationException($"An intervention with code '{intervention.Code}' already exists in the catalog.");
        }

        intervention.CreatedAt = DateTime.UtcNow;
        intervention.CreatedBy = currentUsername;

        db.InterventionTypes.Add(intervention);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "InterventionType", 0, "AddInterventionToCatalog", currentUsername, null, intervention);
        await db.SaveChangesAsync();

        return intervention;
    }

    public async Task<List<GrantWindowIntervention>> GetWindowInterventionsAsync(int windowId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.GrantWindowInterventions
            .Include(i => i.InterventionType)
            .Where(i => i.FundingWindowId == windowId)
            .ToListAsync();
    }

    public async Task SetWindowInterventionsAsync(int windowId, IEnumerable<string> interventionTypeCodes, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.GrantWindowInterventions
            .Where(i => i.FundingWindowId == windowId)
            .ToListAsync();

        var beforeState = existing.Select(i => i.InterventionTypeCode).ToList();
        db.GrantWindowInterventions.RemoveRange(existing);

        var codesToAdd = interventionTypeCodes.Distinct().ToList();
        foreach (var code in codesToAdd)
        {
            db.GrantWindowInterventions.Add(new GrantWindowIntervention
            {
                FundingWindowId = windowId,
                InterventionTypeCode = code,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = currentUsername
            });
        }

        _audit.LogAction(db, "GrantFundingWindow", windowId, "SetWindowInterventions", currentUsername, beforeState, codesToAdd);
        await db.SaveChangesAsync();
    }

    public async Task<GrantApplication> CreateApplicationAsync(GrantApplication application, string currentUsername = "SYSTEM")
    {
        if (string.IsNullOrWhiteSpace(application.ApplicationNumber))
        {
            application.ApplicationNumber = $"DG-{DateTime.UtcNow.Year}-{application.OrganisationId}-{Guid.NewGuid().ToString("N")[..6].ToUpper()}";
        }

        if (string.IsNullOrWhiteSpace(application.ApplicationStatusCode))
        {
            application.ApplicationStatusCode = "Submitted";
        }

        // Automatic WSP Eligibility evaluation if not explicitly overridden
        if (application.OrganisationId > 0 && !application.IsWspExempt)
        {
            var wspResult = await EvaluateWspEligibilityAsync(application.OrganisationId, application.FinancialYear);
            application.IsWspCompliant = wspResult.IsEligible;
            application.IsWspExempt = wspResult.IsExempt;
            application.WspSubmissionId = wspResult.WspSubmissionId;
            if (string.IsNullOrWhiteSpace(application.WspExemptionReason))
            {
                application.WspExemptionReason = wspResult.Reason;
            }
        }

        using var db = await _contextFactory.CreateDbContextAsync();

        // Statutory Invariant: Prevent duplicate Discretionary Grant applications for the same Organisation in the same Funding Window
        if (application.OrganisationId > 0 && application.FundingWindowId.HasValue && application.FundingWindowId.Value > 0)
        {
            var existingApp = await db.GrantApplications
                .AsNoTracking()
                .FirstOrDefaultAsync(a => a.OrganisationId == application.OrganisationId && a.FundingWindowId == application.FundingWindowId.Value && a.Id != application.Id);
            if (existingApp != null)
            {
                throw new InvalidOperationException($"An application ({existingApp.ApplicationNumber}) has already been lodged by Organisation #{application.OrganisationId} for Discretionary Grant Funding Window #{application.FundingWindowId.Value}. In terms of merSETA Discretionary Grant governance, only one application is permitted per funding window.");
            }
        }

        // Strict Terminology & Window Timing Invariant: Submissions are strictly governed by GrantFundingWindow opening and closing dates
        if (application.FundingWindowId.HasValue && application.FundingWindowId.Value > 0)
        {
            var window = await db.GrantFundingWindows
                .Include(w => w.EligibleStakeholders)
                .Include(w => w.AllowedInterventions)
                .AsNoTracking()
                .FirstOrDefaultAsync(w => w.Id == application.FundingWindowId.Value);

            if (window != null)
            {
                if (DateTime.UtcNow < window.OpeningDate || DateTime.UtcNow > window.ClosingDate)
                {
                    throw new InvalidOperationException($"Discretionary Grant submission rejected: The funding window '{window.WindowName}' is currently closed. Submissions are only accepted between {window.OpeningDate:yyyy-MM-dd} and {window.ClosingDate:yyyy-MM-dd}.");
                }
                if (!window.IsActive || window.ApprovalStatusCode == "Closed" || window.ApprovalStatusCode == "Rejected")
                {
                    throw new InvalidOperationException($"Discretionary Grant submission rejected: The funding window '{window.WindowName}' is not in an active, approved state (Current status: {window.ApprovalStatusCode}).");
                }

                // Toggleable WSP Submission Compliance Requirement
                if (window.RequireWspCompliance && !application.IsWspCompliant && !application.IsWspExempt)
                {
                    throw new InvalidOperationException($"Mandatory Grant Compliance Requirement: Organisation #{application.OrganisationId} has not submitted an approved Workplace Skills Plan (WSP/ATR) for scheme year {window.FinYear - 1}, which is required for this Discretionary Grant funding window '{window.WindowName}'.");
                }

                // Stakeholder Eligibility Verification (if window configured with specific eligibilities)
                if (window.EligibleStakeholders != null && window.EligibleStakeholders.Any())
                {
                    var org = await db.Organisations.AsNoTracking().FirstOrDefaultAsync(o => o.Id == application.OrganisationId);
                    if (org != null)
                    {
                        var allowedCodes = window.EligibleStakeholders.Select(e => e.StakeholderEligibilityTypeCode).ToHashSet(StringComparer.OrdinalIgnoreCase);
                        bool isEligible = false;
                        var isLevyPayer = string.Equals(org.LevyCategoryCode, "LEVY_PAYING", StringComparison.OrdinalIgnoreCase) || 
                                          (!string.IsNullOrWhiteSpace(org.SdlNumber) && org.SdlNumber.StartsWith("L", StringComparison.OrdinalIgnoreCase));

                        if (allowedCodes.Contains("LEVY_PAYING") && isLevyPayer)
                        {
                            isEligible = true;
                        }
                        else if (allowedCodes.Contains("NON_LEVY_EXEMPT") && (!isLevyPayer || string.Equals(org.LevyCategoryCode, "NON_LEVY_PAYING", StringComparison.OrdinalIgnoreCase) || string.Equals(org.LevyCategoryCode, "EXEMPT", StringComparison.OrdinalIgnoreCase)))
                        {
                            isEligible = true;
                        }
                        else if (!string.IsNullOrWhiteSpace(org.NonEmployerEntityType) && 
                                 (allowedCodes.Contains(org.NonEmployerEntityType) || allowedCodes.Any(c => c.Contains(org.NonEmployerEntityType, StringComparison.OrdinalIgnoreCase))))
                        {
                            isEligible = true;
                        }

                        if (!isEligible)
                        {
                            throw new InvalidOperationException($"Stakeholder Eligibility Violation: Organisation #{application.OrganisationId} ({org.CompanyName}) does not match the configured eligible stakeholder classifications for funding window '{window.WindowName}'.");
                        }
                    }
                }
            }
        }

        // Real-Time Budget Consumption Advisory Check
        if (application.FundingWindowPriorityId.HasValue && application.FundingWindowPriorityId.Value > 0)
        {
            var consumption = await EvaluateBudgetConsumptionAsync(application.FundingWindowPriorityId.Value, application.RequestedAmount);
            if (consumption.IsOverSubscribed)
            {
                _audit.LogAction(db, "FundingWindowPriority", application.FundingWindowPriorityId.Value, "BudgetOverSubscribedAlert", currentUsername, null, consumption);
            }
        }

        // Statutory Invariant: Prevent Discretionary Grant submissions if Organisation has missing/unmapped Chamber
        if (application.OrganisationId > 0)
        {
            var org = await db.Organisations.AsNoTracking().FirstOrDefaultAsync(o => o.Id == application.OrganisationId);
            if (org != null && org.HasMissingChamberMapping)
            {
                throw new InvalidOperationException($"Discretionary Grant Application blocked: Organisation '{org.CompanyName}' (#{org.Id}) has an unmapped or missing merSETA Chamber / GP Vendor Class. Please resolve the organisation's Chamber mapping before submitting.");
            }
        }
        application.ApplicationDate = DateTime.UtcNow;
        application.CreatedAt = DateTime.UtcNow;
        application.CreatedBy = currentUsername;

        if (application.Interventions != null && application.Interventions.Any())
        {
            application.HasPivotalInterventions = application.Interventions.Any(i => i.IsPivotal);
            application.HasNonPivotalInterventions = application.Interventions.Any(i => !i.IsPivotal);

            foreach (var inv in application.Interventions)
            {
                if (inv.TotalAmount <= 0)
                {
                    if (inv.IsPivotal && inv.UnitCost > 0)
                    {
                        inv.TotalAmount = inv.UnitCost * (inv.LearnerCountEmployed + inv.LearnerCountUnemployed);
                    }
                    else if (!inv.IsPivotal && inv.EstimatedCost > 0)
                    {
                        inv.TotalAmount = inv.EstimatedCost;
                    }
                }
                inv.CreatedAt = DateTime.UtcNow;
                inv.CreatedBy = currentUsername;
            }

            var computedRequested = application.Interventions.Sum(i => i.TotalAmount);
            if (computedRequested > 0 && (application.RequestedAmount <= 0 || application.RequestedAmount == 350000m))
            {
                application.RequestedAmount = computedRequested;
            }
        }
        else
        {
            if (string.Equals(application.GrantTypeCode, "NON_PIVOTAL", StringComparison.OrdinalIgnoreCase) ||
                string.Equals(application.GrantTypeCode, "PROJECT", StringComparison.OrdinalIgnoreCase))
            {
                application.HasPivotalInterventions = false;
                application.HasNonPivotalInterventions = true;
            }
            else if (string.Equals(application.GrantTypeCode, "PIVOTAL", StringComparison.OrdinalIgnoreCase))
            {
                application.HasPivotalInterventions = true;
                application.HasNonPivotalInterventions = false;
            }
            else
            {
                application.HasPivotalInterventions = false;
                application.HasNonPivotalInterventions = false;
            }
        }

        db.GrantApplications.Add(application);
        await db.SaveChangesAsync();

        if (application.Interventions != null && application.Interventions.Any())
        {
            foreach (var inv in application.Interventions)
            {
                inv.GrantApplicationId = application.Id;
            }

            var unpersisted = application.Interventions.Where(i => i.Id == 0).ToList();
            if (unpersisted.Any())
            {
                db.GrantApplicationInterventions.AddRange(unpersisted);
                await db.SaveChangesAsync();
            }
        }

        _audit.LogAction(db, "GrantApplication", application.Id, "Create", currentUsername, null, application);
        await db.SaveChangesAsync();

        return application;
    }

    public async Task<GrantApplication> SubmitApplicationAsync(GrantApplication application, string currentUsername = "SYSTEM")
    {
        return await CreateApplicationAsync(application, currentUsername);
    }

    public async Task<GrantApplication> UpdateApplicationAsync(GrantApplication application, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.GrantApplications
            .Include(a => a.Interventions)
            .FirstOrDefaultAsync(a => a.Id == application.Id);
        if (existing == null)
        {
            throw new KeyNotFoundException($"GrantApplication with ID {application.Id} was not found.");
        }

        var beforeState = new
        {
            existing.ProjectTitle,
            existing.GrantTypeCode,
            existing.ApplicationStatusCode,
            existing.RequestedAmount,
            existing.ApprovedAmount,
            existing.IsWspCompliant,
            existing.IsWspExempt,
            existing.WspSubmissionId,
            existing.ProjectDescription,
            existing.Purpose,
            existing.Outcomes,
            existing.Benefits,
            existing.PotentialRisks,
            existing.EstimatedOverallProjectCost,
            existing.NumberOfBeneficiaries,
            existing.RequireProjectAdministrationCosts,
            existing.TargetProvinces,
            existing.HasPivotalInterventions,
            existing.HasNonPivotalInterventions
        };

        existing.ProjectTitle = application.ProjectTitle;
        existing.GrantTypeCode = application.GrantTypeCode;
        existing.ApplicationStatusCode = application.ApplicationStatusCode;
        existing.RequestedAmount = application.RequestedAmount;
        existing.ApprovedAmount = application.ApprovedAmount;
        existing.IsWspCompliant = application.IsWspCompliant;
        existing.IsWspExempt = application.IsWspExempt;
        existing.WspExemptionReason = application.WspExemptionReason;
        existing.WspSubmissionId = application.WspSubmissionId;

        // Composite narrative motivation fields
        existing.ProjectDescription = application.ProjectDescription;
        existing.Purpose = application.Purpose;
        existing.Outcomes = application.Outcomes;
        existing.Benefits = application.Benefits;
        existing.PotentialRisks = application.PotentialRisks;
        existing.EstimatedOverallProjectCost = application.EstimatedOverallProjectCost;
        existing.NumberOfBeneficiaries = application.NumberOfBeneficiaries;
        existing.RequireProjectAdministrationCosts = application.RequireProjectAdministrationCosts;
        existing.TargetProvinces = application.TargetProvinces;
        existing.HasPivotalInterventions = application.HasPivotalInterventions;
        existing.HasNonPivotalInterventions = application.HasNonPivotalInterventions;

        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        _audit.LogAction(db, "GrantApplication", existing.Id, "Update", currentUsername, beforeState, existing);
        await db.SaveChangesAsync();

        return existing;
    }

    public async Task<GrantApplication> SaveAsync(GrantApplication application, string currentUsername = "SYSTEM")
    {
        if (application.Id == 0)
        {
            return await CreateApplicationAsync(application, currentUsername);
        }

        return await UpdateApplicationAsync(application, currentUsername);
    }

    public async Task<GrantApplication?> GetByIdAsync(int id)
    {
        return await GetApplicationByIdAsync(id);
    }

    public async Task<GrantApplication?> GetApplicationByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.GrantApplications
            .Include(g => g.Organisation)
            .Include(g => g.FundingWindow)
            .Include(g => g.StrategicPriority)
            .Include(g => g.FundingWindowPriority)
            .Include(g => g.WspSubmission)
            .Include(g => g.Interventions)
                .ThenInclude(i => i.InterventionType)
            .Include(g => g.ProjectBudgets)
                .ThenInclude(b => b.StrategicPriority)
            .FirstOrDefaultAsync(g => g.Id == id);
    }

    public async Task<List<GrantApplication>> GetApplicationsByOrganisationAsync(int organisationId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.GrantApplications
            .Include(g => g.FundingWindow)
            .Include(g => g.ProjectBudgets)
            .Where(g => g.OrganisationId == organisationId)
            .OrderByDescending(g => g.ApplicationDate)
            .ToListAsync();
    }

    public async Task<List<GrantApplication>> GetAllAsync(string? search = null, string? grantType = null, string? status = null)
    {
        return await GetAllApplicationsAsync(null, search, grantType, status);
    }

    public async Task<List<GrantApplication>> GetAllApplicationsAsync(int? fundingWindowId = null, string? search = null, string? grantType = null, string? status = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.GrantApplications
            .Include(g => g.Organisation)
            .Include(g => g.FundingWindow)
            .Include(g => g.ProjectBudgets)
            .AsQueryable();

        if (fundingWindowId.HasValue)
        {
            query = query.Where(g => g.FundingWindowId == fundingWindowId.Value);
        }

        if (!string.IsNullOrWhiteSpace(grantType))
        {
            query = query.Where(g => g.GrantTypeCode == grantType);
        }

        if (!string.IsNullOrWhiteSpace(status))
        {
            query = query.Where(g => g.ApplicationStatusCode == status);
        }

        if (!string.IsNullOrWhiteSpace(search))
        {
            var s = search.Trim();
            query = query.Where(g =>
                g.ApplicationNumber.Contains(s) ||
                g.ProjectTitle.Contains(s) ||
                (g.Organisation != null && g.Organisation.CompanyName.Contains(s)));
        }

        return await query
            .OrderByDescending(g => g.ApplicationDate)
            .ToListAsync();
    }

    public async Task<GrantApplication> ApproveApplicationAsync(int id, decimal approvedAmount, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var application = await db.GrantApplications.FindAsync(id);
        if (application == null)
        {
            throw new KeyNotFoundException($"GrantApplication with ID {id} was not found.");
        }

        if (approvedAmount < 0)
        {
            throw new ArgumentException("Approved amount cannot be negative.");
        }

        var beforeState = new { application.ApplicationStatusCode, application.ApprovedAmount };

        application.ApplicationStatusCode = "Approved";
        application.ApprovedAmount = approvedAmount;
        application.ModifiedAt = DateTime.UtcNow;
        application.ModifiedBy = currentUsername;

        _audit.LogAction(db, "GrantApplication", application.Id, "ApproveApplication", currentUsername, beforeState, application);
        await db.SaveChangesAsync();

        return application;
    }

    public async Task<GrantApplication> RejectApplicationAsync(int id, string reason, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var application = await db.GrantApplications.FindAsync(id);
        if (application == null)
        {
            throw new KeyNotFoundException($"GrantApplication with ID {id} was not found.");
        }

        var beforeState = new { application.ApplicationStatusCode };

        application.ApplicationStatusCode = "Rejected";
        application.ModifiedAt = DateTime.UtcNow;
        application.ModifiedBy = currentUsername;

        var metadata = new { Application = application, RejectionReason = reason };
        _audit.LogAction(db, "GrantApplication", application.Id, "RejectApplication", currentUsername, beforeState, metadata);
        await db.SaveChangesAsync();

        return application;
    }

    public async Task<bool> DeleteAsync(int id, string currentUsername = "SYSTEM")
    {
        return await DeleteApplicationAsync(id, currentUsername);
    }

    public async Task<bool> DeleteApplicationAsync(int id, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var application = await db.GrantApplications.FindAsync(id);
        if (application == null)
        {
            return false;
        }

        var beforeState = new
        {
            application.Id,
            application.OrganisationId,
            application.ApplicationNumber,
            application.ApplicationStatusCode
        };

        db.GrantApplications.Remove(application);
        _audit.LogAction(db, "GrantApplication", id, "Delete", currentUsername, beforeState, null);
        await db.SaveChangesAsync();

        return true;
    }

    // Composite PIVOTAL and Non-PIVOTAL Application Interventions (Option A)
    public async Task<List<GrantApplicationIntervention>> GetApplicationInterventionsAsync(int applicationId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.GrantApplicationInterventions
            .Include(i => i.InterventionType)
            .Where(i => i.GrantApplicationId == applicationId)
            .OrderBy(i => i.IsPivotal ? 0 : 1)
            .ThenBy(i => i.Id)
            .ToListAsync();
    }

    public async Task<GrantApplicationIntervention> AddApplicationInterventionAsync(int applicationId, GrantApplicationIntervention intervention, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var app = await db.GrantApplications.FindAsync(applicationId);
        if (app == null)
        {
            throw new KeyNotFoundException($"GrantApplication with ID {applicationId} was not found.");
        }

        intervention.GrantApplicationId = applicationId;
        if (intervention.TotalAmount <= 0)
        {
            if (intervention.IsPivotal && intervention.UnitCost > 0)
            {
                intervention.TotalAmount = intervention.UnitCost * (intervention.LearnerCountEmployed + intervention.LearnerCountUnemployed);
            }
            else if (!intervention.IsPivotal && intervention.EstimatedCost > 0)
            {
                intervention.TotalAmount = intervention.EstimatedCost;
            }
        }
        intervention.CreatedAt = DateTime.UtcNow;
        intervention.CreatedBy = currentUsername;

        db.GrantApplicationInterventions.Add(intervention);
        await db.SaveChangesAsync();

        // Update application flags and total
        var allInterventions = await db.GrantApplicationInterventions.Where(i => i.GrantApplicationId == applicationId).ToListAsync();
        app.HasPivotalInterventions = allInterventions.Any(i => i.IsPivotal);
        app.HasNonPivotalInterventions = allInterventions.Any(i => !i.IsPivotal);
        var totalAmount = allInterventions.Sum(i => i.TotalAmount);
        if (totalAmount > 0)
        {
            app.RequestedAmount = totalAmount;
        }
        app.ModifiedAt = DateTime.UtcNow;
        app.ModifiedBy = currentUsername;

        _audit.LogAction(db, "GrantApplicationIntervention", intervention.Id, "Create", currentUsername, null, intervention);
        await db.SaveChangesAsync();

        return intervention;
    }

    public async Task<GrantApplicationIntervention> UpdateApplicationInterventionAsync(GrantApplicationIntervention intervention, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.GrantApplicationInterventions.FindAsync(intervention.Id);
        if (existing == null)
        {
            throw new KeyNotFoundException($"GrantApplicationIntervention with ID {intervention.Id} was not found.");
        }

        var beforeState = new
        {
            existing.InterventionTypeCode,
            existing.IsPivotal,
            existing.SaqaId,
            existing.QualificationTitle,
            existing.NqfLevel,
            existing.OfoCode,
            existing.LearnerCountEmployed,
            existing.LearnerCountUnemployed,
            existing.UnitCost,
            existing.DeliverableName,
            existing.TargetQuantity,
            existing.EstimatedCost,
            existing.TotalAmount
        };

        existing.InterventionTypeCode = intervention.InterventionTypeCode;
        existing.IsPivotal = intervention.IsPivotal;
        existing.SaqaId = intervention.SaqaId;
        existing.QualificationTitle = intervention.QualificationTitle;
        existing.NqfLevel = intervention.NqfLevel;
        existing.OfoCode = intervention.OfoCode;
        existing.LearnerCountEmployed = intervention.LearnerCountEmployed;
        existing.LearnerCountUnemployed = intervention.LearnerCountUnemployed;
        existing.UnitCost = intervention.UnitCost;
        existing.DeliverableName = intervention.DeliverableName;
        existing.TargetQuantity = intervention.TargetQuantity;
        existing.EstimatedCost = intervention.EstimatedCost;
        existing.ProjectedStartDate = intervention.ProjectedStartDate;
        existing.ProjectedEndDate = intervention.ProjectedEndDate;
        existing.ActualEndDate = intervention.ActualEndDate;
        existing.MilestoneNumber = intervention.MilestoneNumber;
        existing.Comments = intervention.Comments;

        if (intervention.TotalAmount > 0)
        {
            existing.TotalAmount = intervention.TotalAmount;
        }
        else if (existing.IsPivotal && existing.UnitCost > 0)
        {
            existing.TotalAmount = existing.UnitCost * (existing.LearnerCountEmployed + existing.LearnerCountUnemployed);
        }
        else if (!existing.IsPivotal && existing.EstimatedCost > 0)
        {
            existing.TotalAmount = existing.EstimatedCost;
        }

        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        _audit.LogAction(db, "GrantApplicationIntervention", existing.Id, "Update", currentUsername, beforeState, existing);
        await db.SaveChangesAsync();

        // Update application total
        var app = await db.GrantApplications.FindAsync(existing.GrantApplicationId);
        if (app != null)
        {
            var allInterventions = await db.GrantApplicationInterventions.Where(i => i.GrantApplicationId == app.Id).ToListAsync();
            app.HasPivotalInterventions = allInterventions.Any(i => i.IsPivotal);
            app.HasNonPivotalInterventions = allInterventions.Any(i => !i.IsPivotal);
            var total = allInterventions.Sum(i => i.TotalAmount);
            if (total > 0)
            {
                app.RequestedAmount = total;
            }
            app.ModifiedAt = DateTime.UtcNow;
            app.ModifiedBy = currentUsername;
            await db.SaveChangesAsync();
        }

        return existing;
    }

    public async Task<bool> RemoveApplicationInterventionAsync(int interventionId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var item = await db.GrantApplicationInterventions.FindAsync(interventionId);
        if (item == null) return false;

        var appId = item.GrantApplicationId;
        _audit.LogAction(db, "GrantApplicationIntervention", interventionId, "Delete", currentUsername, item, null);
        db.GrantApplicationInterventions.Remove(item);
        await db.SaveChangesAsync();

        var app = await db.GrantApplications.FindAsync(appId);
        if (app != null)
        {
            var remaining = await db.GrantApplicationInterventions.Where(i => i.GrantApplicationId == appId).ToListAsync();
            app.HasPivotalInterventions = remaining.Any(i => i.IsPivotal);
            app.HasNonPivotalInterventions = remaining.Any(i => !i.IsPivotal);
            var total = remaining.Sum(i => i.TotalAmount);
            if (total > 0)
            {
                app.RequestedAmount = total;
            }
            app.ModifiedAt = DateTime.UtcNow;
            app.ModifiedBy = currentUsername;
            await db.SaveChangesAsync();
        }

        return true;
    }

    public async Task SetApplicationInterventionsAsync(int applicationId, IEnumerable<GrantApplicationIntervention> interventions, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var app = await db.GrantApplications.FindAsync(applicationId);
        if (app == null)
        {
            throw new KeyNotFoundException($"GrantApplication with ID {applicationId} was not found.");
        }

        var existing = await db.GrantApplicationInterventions
            .Where(i => i.GrantApplicationId == applicationId)
            .ToListAsync();

        db.GrantApplicationInterventions.RemoveRange(existing);

        var list = interventions.ToList();
        foreach (var inv in list)
        {
            inv.Id = 0;
            inv.GrantApplicationId = applicationId;
            if (inv.TotalAmount <= 0)
            {
                if (inv.IsPivotal && inv.UnitCost > 0)
                {
                    inv.TotalAmount = inv.UnitCost * (inv.LearnerCountEmployed + inv.LearnerCountUnemployed);
                }
                else if (!inv.IsPivotal && inv.EstimatedCost > 0)
                {
                    inv.TotalAmount = inv.EstimatedCost;
                }
            }
            inv.CreatedAt = DateTime.UtcNow;
            inv.CreatedBy = currentUsername;
            db.GrantApplicationInterventions.Add(inv);
        }

        app.HasPivotalInterventions = list.Any(i => i.IsPivotal);
        app.HasNonPivotalInterventions = list.Any(i => !i.IsPivotal);
        var totalAmount = list.Sum(i => i.TotalAmount);
        if (totalAmount > 0)
        {
            app.RequestedAmount = totalAmount;
        }
        app.ModifiedAt = DateTime.UtcNow;
        app.ModifiedBy = currentUsername;

        _audit.LogAction(db, "GrantApplication", applicationId, "SetApplicationInterventions", currentUsername, existing, list);
        await db.SaveChangesAsync();
    }

    public async Task<GrantProjectBudget> AddBudgetItemAsync(GrantProjectBudget budgetItem, string currentUsername = "SYSTEM")
    {
        if (budgetItem.GrantApplicationId <= 0)
        {
            throw new ArgumentException("A valid GrantApplicationId is required.");
        }

        budgetItem.TotalCost = budgetItem.UnitCost * budgetItem.Quantity;

        using var db = await _contextFactory.CreateDbContextAsync();
        budgetItem.CreatedAt = DateTime.UtcNow;
        budgetItem.CreatedBy = currentUsername;

        db.GrantProjectBudgets.Add(budgetItem);
        await db.SaveChangesAsync();

        var budgets = await db.GrantProjectBudgets
            .Where(b => b.GrantApplicationId == budgetItem.GrantApplicationId)
            .ToListAsync();
        var totalBudget = budgets.Sum(b => b.TotalCost);
        var app = await db.GrantApplications.FindAsync(budgetItem.GrantApplicationId);
        if (app != null)
        {
            app.RequestedAmount = totalBudget;
            app.ModifiedAt = DateTime.UtcNow;
        }

        _audit.LogAction(db, "GrantProjectBudget", budgetItem.Id, "AddBudgetItem", currentUsername, null, budgetItem);
        await db.SaveChangesAsync();

        return budgetItem;
    }

    public async Task<GrantProjectBudget> AddBudgetItemAsync(int applicationId, GrantProjectBudget budgetItem, string currentUsername = "SYSTEM")
    {
        budgetItem.GrantApplicationId = applicationId;
        return await AddBudgetItemAsync(budgetItem, currentUsername);
    }

    public async Task<GrantProjectBudget> AddProjectBudgetAsync(GrantProjectBudget budgetItem, string currentUsername = "SYSTEM")
    {
        return await AddBudgetItemAsync(budgetItem, currentUsername);
    }

    public async Task<GrantProjectBudget> AddProjectBudgetAsync(int applicationId, GrantProjectBudget budgetItem, string currentUsername = "SYSTEM")
    {
        budgetItem.GrantApplicationId = applicationId;
        return await AddBudgetItemAsync(budgetItem, currentUsername);
    }

    public async Task<List<GrantProjectBudget>> GetProjectBudgetsAsync(int applicationId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.GrantProjectBudgets
            .Where(b => b.GrantApplicationId == applicationId)
            .OrderBy(b => b.ExpenseCategory)
            .ToListAsync();
    }

    public async Task<decimal> RecalculateApplicationBudgetAsync(int applicationId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var application = await db.GrantApplications.FindAsync(applicationId);
        if (application == null)
        {
            throw new KeyNotFoundException($"GrantApplication with ID {applicationId} was not found.");
        }

        var budgets = await db.GrantProjectBudgets
            .Where(b => b.GrantApplicationId == applicationId)
            .ToListAsync();

        var totalBudget = budgets.Sum(b => b.TotalCost);
        application.RequestedAmount = totalBudget;
        application.ModifiedAt = DateTime.UtcNow;

        await db.SaveChangesAsync();

        return totalBudget;
    }

    public async Task<bool> RemoveBudgetItemAsync(int budgetId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var budget = await db.GrantProjectBudgets.FindAsync(budgetId);
        if (budget == null)
        {
            return false;
        }

        var applicationId = budget.GrantApplicationId;
        var beforeState = new { budget.Id, budget.GrantApplicationId, budget.ExpenseCategory, budget.TotalCost };

        db.GrantProjectBudgets.Remove(budget);
        await db.SaveChangesAsync();

        var budgets = await db.GrantProjectBudgets
            .Where(b => b.GrantApplicationId == applicationId)
            .ToListAsync();
        var totalBudget = budgets.Sum(b => b.TotalCost);
        var app = await db.GrantApplications.FindAsync(applicationId);
        if (app != null)
        {
            app.RequestedAmount = totalBudget;
            app.ModifiedAt = DateTime.UtcNow;
        }

        _audit.LogAction(db, "GrantProjectBudget", budgetId, "RemoveBudgetItem", currentUsername, beforeState, null);
        await db.SaveChangesAsync();

        return true;
    }

    public async Task<bool> RemoveProjectBudgetAsync(int budgetId, string currentUsername = "SYSTEM")
    {
        return await RemoveBudgetItemAsync(budgetId, currentUsername);
    }

    public async Task<GrantMoa?> GetGrantMoaByApplicationIdAsync(int applicationId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.GrantMoas
            .Include(m => m.Milestones)
            .FirstOrDefaultAsync(m => m.GrantApplicationId == applicationId);
    }

    public async Task<WspEligibilityResult> EvaluateWspEligibilityAsync(int organisationId, int? finYear = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var org = await db.Organisations.FindAsync(organisationId);
        if (org == null)
        {
            return new WspEligibilityResult(false, false, "UnknownOrganisation", null, "Organisation record not found.");
        }

        var targetYear = finYear ?? DateTime.UtcNow.Year;

        // Check if organisation is an exempt entity type (TVET College, Non-Levy Payer, NGO, Community Trust, Government)
        bool isNonLevy = string.Equals(org.LevyCategoryCode, "NON_LEVY_PAYING", StringComparison.OrdinalIgnoreCase) ||
                         string.Equals(org.LevyCategoryCode, "EXEMPT", StringComparison.OrdinalIgnoreCase) ||
                         string.Equals(org.OrganisationTypeCode, "PUBLIC_ENTITY", StringComparison.OrdinalIgnoreCase) ||
                         string.Equals(org.OrganisationTypeCode, "NGO_NPO", StringComparison.OrdinalIgnoreCase) ||
                         string.Equals(org.OrganisationTypeCode, "TVET", StringComparison.OrdinalIgnoreCase);

        if (isNonLevy)
        {
            return new WspEligibilityResult(
                IsEligible: true,
                IsExempt: true,
                Status: "Exempt",
                WspSubmissionId: null,
                Reason: $"Organisation is exempt from Mandatory Grant WSP submission ({org.OrganisationTypeCode ?? org.LevyCategoryCode ?? "Non-Levy Payer"}).",
                WspReferenceNumber: null,
                ApprovalDate: null
            );
        }

        // Lookup WspSubmission for this organisation and target financial year
        var wsp = await db.WspSubmissions
            .Where(w => w.OrganisationId == organisationId && w.FinYear == targetYear)
            .OrderByDescending(w => w.Id)
            .FirstOrDefaultAsync();

        if (wsp == null)
        {
            return new WspEligibilityResult(
                IsEligible: false,
                IsExempt: false,
                Status: "Missing",
                WspSubmissionId: null,
                Reason: $"No WSP/ATR submission on record for financial year {targetYear} (SETA Grant Regulation 4(4)).",
                WspReferenceNumber: null,
                ApprovalDate: null
            );
        }

        var isApproved = string.Equals(wsp.WspApprovalStatusCode, "Approved", StringComparison.OrdinalIgnoreCase) ||
                         string.Equals(wsp.WspApprovalStatusCode, "Compliant", StringComparison.OrdinalIgnoreCase);

        if (isApproved)
        {
            return new WspEligibilityResult(
                IsEligible: true,
                IsExempt: false,
                Status: "Approved",
                WspSubmissionId: wsp.Id,
                Reason: $"Compliant WSP #{wsp.ReferenceNumber} approved.",
                WspReferenceNumber: wsp.ReferenceNumber,
                ApprovalDate: wsp.ModifiedAt
            );
        }

        return new WspEligibilityResult(
            IsEligible: false,
            IsExempt: false,
            Status: wsp.WspApprovalStatusCode ?? "Pending",
            WspSubmissionId: wsp.Id,
            Reason: $"WSP #{wsp.ReferenceNumber} is in status '{wsp.WspApprovalStatusCode}'. Mandatory Grant approval required for DG eligibility.",
            WspReferenceNumber: wsp.ReferenceNumber,
            ApprovalDate: null
        );
    }

    public async Task<GrantMoa> GenerateGrantMoaFromApplicationAsync(int grantApplicationId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var app = await db.GrantApplications
            .Include(a => a.FundingWindow)
            .Include(a => a.Organisation)
            .FirstOrDefaultAsync(a => a.Id == grantApplicationId);

        if (app == null)
        {
            throw new ArgumentException($"Grant application #{grantApplicationId} not found.");
        }

        // Check if Moa already exists
        var existingMoa = await db.GrantMoas
            .Include(m => m.Milestones)
            .FirstOrDefaultAsync(m => m.GrantApplicationId == grantApplicationId);

        if (existingMoa != null)
        {
            return existingMoa;
        }

        var finYear = app.FundingWindow?.FinYear ?? DateTime.UtcNow.Year;
        var contractValue = app.ApprovedAmount ?? (app.RequestedAmount > 0 ? app.RequestedAmount : 450000m);
        var moaNumber = $"MOA-{finYear}-DG-{app.Id:D4}";

        var moa = new GrantMoa
        {
            GrantApplicationId = app.Id,
            MoaNumber = moaNumber,
            ContractStartDate = DateTime.UtcNow.Date,
            ContractEndDate = DateTime.UtcNow.Date.AddYears(1),
            TotalContractValue = contractValue,
            MoaStatusCode = "Draft",
            SpecialConditions = "Project funded under MerSETA Discretionary Grant Policy. Tranche disbursements subject to verification of milestone evidence in accordance with SETA governance guidelines.",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        // Configurable 4-Tranche Milestone Schedule (Discretionary Grants defaults to 30%, 30%, 30%, 10%)
        var pct1 = _configService != null ? await _configService.GetValueAsync<decimal>("Grant:Tranche1Percentage", 0.30m) : 0.30m;
        var pct2 = _configService != null ? await _configService.GetValueAsync<decimal>("Grant:Tranche2Percentage", 0.30m) : 0.30m;
        var pct3 = _configService != null ? await _configService.GetValueAsync<decimal>("Grant:Tranche3Percentage", 0.30m) : 0.30m;
        var pct4 = _configService != null ? await _configService.GetValueAsync<decimal>("Grant:Tranche4Percentage", 0.10m) : 0.10m;

        var t1 = Math.Round(contractValue * pct1, 2);
        var t2 = Math.Round(contractValue * pct2, 2);
        var t3 = Math.Round(contractValue * pct3, 2);
        var t4 = contractValue - (t1 + t2 + t3); // Residual balancing guarantees 100.00% exact sum

        moa.Milestones.Add(new GrantMoaMilestone
        {
            MilestoneNumber = 1,
            MilestoneTitle = "Inception & Learner Registration",
            MilestoneDescription = "Bilateral MoA execution, proof of learner agreement upload, and project inception report.",
            DeliverableRequirement = "Signed MoA, Certified ID Copies, Proof of Enrolment on merSETA NSDMS.",
            TranchePercentage = pct1 * 100m,
            TrancheAmount = t1,
            TargetDueDate = DateTime.UtcNow.Date.AddMonths(3),
            MilestoneStatusCode = "Pending",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        });

        moa.Milestones.Add(new GrantMoaMilestone
        {
            MilestoneNumber = 2,
            MilestoneTitle = "Midterm Structured Learning & Progress",
            MilestoneDescription = "Completion of foundational theory modules and formative workplace assessment logbooks.",
            DeliverableRequirement = "Midterm Progress Report, Logbook Assessment Records, Workplace Monitoring Signoff.",
            TranchePercentage = pct2 * 100m,
            TrancheAmount = t2,
            TargetDueDate = DateTime.UtcNow.Date.AddMonths(6),
            MilestoneStatusCode = "Pending",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        });

        moa.Milestones.Add(new GrantMoaMilestone
        {
            MilestoneNumber = 3,
            MilestoneTitle = "Workplace Evidence & Practical Assessment",
            MilestoneDescription = "Practical workplace exposure verification and readiness for summative assessment.",
            DeliverableRequirement = "Workplace Mentor Signoff, Completed Logbooks, Trade Test / EISA Entry Forms.",
            TranchePercentage = pct3 * 100m,
            TrancheAmount = t3,
            TargetDueDate = DateTime.UtcNow.Date.AddMonths(9),
            MilestoneStatusCode = "Pending",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        });

        moa.Milestones.Add(new GrantMoaMilestone
        {
            MilestoneNumber = 4,
            MilestoneTitle = "Final Project Closeout & Certification",
            MilestoneDescription = "Summative assessment completion, external moderation endorsement, and project closeout reconciliation.",
            DeliverableRequirement = "Statement of Results (SOR) / Trade Test Certificates, Final Closeout Expenditure Audit.",
            TranchePercentage = pct4 * 100m,
            TrancheAmount = t4,
            TargetDueDate = DateTime.UtcNow.Date.AddMonths(12),
            MilestoneStatusCode = "Pending",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        });

        db.GrantMoas.Add(moa);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "GrantMoa", moa.Id, "GenerateFromApplication", currentUsername, null, moa);
        await db.SaveChangesAsync();

        return moa;
    }

    // =========================================================================
    // STRATEGIC PRIORITIES & KEY FOCUS AREAS
    // =========================================================================

    public async Task<List<StrategicPriority>> GetStrategicPrioritiesAsync(bool activeOnly = true)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.StrategicPriorities.AsQueryable();
        if (activeOnly)
        {
            query = query.Where(s => s.IsActive);
        }
        return await query.OrderBy(s => s.Code).ToListAsync();
    }

    public async Task<StrategicPriority?> GetStrategicPriorityByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.StrategicPriorities
            .Include(s => s.WindowPriorities)
                .ThenInclude(w => w.FundingWindow)
            .Include(s => s.GrantApplications)
            .FirstOrDefaultAsync(s => s.Id == id);
    }

    public async Task<StrategicPriority> SaveStrategicPriorityAsync(StrategicPriority priority, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        if (priority.Id == 0)
        {
            priority.CreatedAt = DateTime.UtcNow;
            priority.CreatedBy = currentUsername;
            db.StrategicPriorities.Add(priority);
            await db.SaveChangesAsync();
            _audit.LogAction(db, "StrategicPriority", priority.Id, "Create", currentUsername, null, priority);
            await db.SaveChangesAsync();
            return priority;
        }
        else
        {
            var existing = await db.StrategicPriorities.FindAsync(priority.Id);
            if (existing == null) throw new KeyNotFoundException($"Strategic priority #{priority.Id} not found.");

            var beforeState = new { existing.Code, existing.Name, existing.NsdpOutcomeCode, existing.IsActive };
            existing.Code = priority.Code;
            existing.Name = priority.Name;
            existing.Description = priority.Description;
            existing.NsdpOutcomeCode = priority.NsdpOutcomeCode;
            existing.NsdpOutcomeDescription = priority.NsdpOutcomeDescription;
            existing.SipCategory = priority.SipCategory;
            existing.TargetSector = priority.TargetSector;
            existing.IsActive = priority.IsActive;
            existing.ModifiedAt = DateTime.UtcNow;
            existing.ModifiedBy = currentUsername;

            _audit.LogAction(db, "StrategicPriority", existing.Id, "Update", currentUsername, beforeState, existing);
            await db.SaveChangesAsync();
            return existing;
        }
    }

    public async Task<bool> DeleteStrategicPriorityAsync(int id, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var sp = await db.StrategicPriorities
            .Include(s => s.WindowPriorities)
            .Include(s => s.GrantApplications)
            .FirstOrDefaultAsync(s => s.Id == id);

        if (sp == null) return false;

        if (sp.WindowPriorities.Any() || sp.GrantApplications.Any())
        {
            throw new InvalidOperationException($"Cannot delete Strategic Priority '{sp.Code}' because it is linked to active funding windows or grant applications. Deactivate it instead.");
        }

        var beforeState = new { sp.Id, sp.Code, sp.Name };
        db.StrategicPriorities.Remove(sp);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "StrategicPriority", id, "Delete", currentUsername, beforeState, null);
        await db.SaveChangesAsync();
        return true;
    }

    // =========================================================================
    // FUNDING WINDOW STRATEGIC PRIORITY ALLOCATIONS
    // =========================================================================

    public async Task<List<FundingWindowPriority>> GetWindowPrioritiesAsync(int windowId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.FundingWindowPriorities
            .Include(p => p.StrategicPriority)
            .Where(p => p.FundingWindowId == windowId && p.IsActive)
            .OrderBy(p => p.StrategicPriority!.Code)
            .ToListAsync();
    }

    public async Task<FundingWindowPriority> AddWindowPriorityAsync(FundingWindowPriority windowPriority, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var window = await db.GrantFundingWindows
            .Include(w => w.StrategicPriorities)
            .FirstOrDefaultAsync(w => w.Id == windowPriority.FundingWindowId);

        if (window == null) throw new KeyNotFoundException($"Funding window #{windowPriority.FundingWindowId} not found.");

        var currentTotalAllocated = window.StrategicPriorities.Where(p => p.IsActive).Sum(p => p.AllocatedBudget);
        if (currentTotalAllocated + windowPriority.AllocatedBudget > window.TotalAvailableBudget)
        {
            throw new InvalidOperationException($"Allocated sub-budget ({windowPriority.AllocatedBudget:C}) exceeds remaining available window envelope ({(window.TotalAvailableBudget - currentTotalAllocated):C}).");
        }

        windowPriority.CreatedAt = DateTime.UtcNow;
        windowPriority.CreatedBy = currentUsername;
        db.FundingWindowPriorities.Add(windowPriority);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "FundingWindowPriority", windowPriority.Id, "AddWindowPriority", currentUsername, null, windowPriority);
        await db.SaveChangesAsync();

        return windowPriority;
    }

    public async Task<FundingWindowPriority> UpdateWindowPriorityAsync(FundingWindowPriority windowPriority, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.FundingWindowPriorities.FindAsync(windowPriority.Id);
        if (existing == null) throw new KeyNotFoundException($"Window priority #{windowPriority.Id} not found.");

        var beforeState = new { existing.AllocatedBudget, existing.TargetBeneficiaries, existing.IsRingFenced };
        existing.AllocatedBudget = windowPriority.AllocatedBudget;
        existing.TargetBeneficiaries = windowPriority.TargetBeneficiaries;
        existing.MinScoreThreshold = windowPriority.MinScoreThreshold;
        existing.IsRingFenced = windowPriority.IsRingFenced;
        existing.IsActive = windowPriority.IsActive;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        _audit.LogAction(db, "FundingWindowPriority", existing.Id, "UpdateWindowPriority", currentUsername, beforeState, existing);
        await db.SaveChangesAsync();

        return existing;
    }

    public async Task<bool> RemoveWindowPriorityAsync(int windowPriorityId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var existing = await db.FundingWindowPriorities.FindAsync(windowPriorityId);
        if (existing == null) return false;

        var hasApps = await db.GrantApplications.AnyAsync(a => a.FundingWindowPriorityId == windowPriorityId);
        if (hasApps)
        {
            throw new InvalidOperationException("Cannot remove theme allocation because applications are already linked to it. Deactivate instead.");
        }

        var beforeState = new { existing.Id, existing.FundingWindowId, existing.StrategicPriorityId };
        db.FundingWindowPriorities.Remove(existing);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "FundingWindowPriority", windowPriorityId, "RemoveWindowPriority", currentUsername, beforeState, null);
        await db.SaveChangesAsync();
        return true;
    }

    // =========================================================================
    // 3-TIER STRATEGIC BI INTELLIGENCE REPORTING (OPERATIONAL, TACTICAL, STRATEGIC)
    // =========================================================================

    public async Task<StrategicReportDashboardDto> GetStrategicReportDashboardAsync(int? finYear = null, int? windowId = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var priorities = await db.StrategicPriorities
            .Where(s => s.IsActive)
            .OrderBy(s => s.Code)
            .ToListAsync();

        var windowsQuery = db.GrantFundingWindows
            .Include(w => w.StrategicPriorities)
            .AsQueryable();

        if (finYear.HasValue) windowsQuery = windowsQuery.Where(w => w.FinYear == finYear.Value);
        if (windowId.HasValue) windowsQuery = windowsQuery.Where(w => w.Id == windowId.Value);

        var windows = await windowsQuery.ToListAsync();
        var windowIds = windows.Select(w => w.Id).ToList();

        var appsQuery = db.GrantApplications
            .Include(a => a.Organisation)
            .Include(a => a.StrategicPriority)
            .Include(a => a.FundingWindow)
            .AsQueryable();

        if (windowId.HasValue)
        {
            appsQuery = appsQuery.Where(a => a.FundingWindowId == windowId.Value);
        }
        else if (finYear.HasValue)
        {
            appsQuery = appsQuery.Where(a => a.FundingWindow != null && a.FundingWindow.FinYear == finYear.Value);
        }

        var applications = await appsQuery.ToListAsync();

        // 1. Operational Themes Matrix
        var operationalThemes = new List<ThemeOperationalDto>();
        decimal totalAllocated = 0;
        decimal totalRequested = 0;
        decimal totalApproved = 0;
        int totalTargetLearners = 0;

        foreach (var sp in priorities)
        {
            var matchingWindowPriorities = windows
                .SelectMany(w => w.StrategicPriorities)
                .Where(p => p.StrategicPriorityId == sp.Id && p.IsActive)
                .ToList();

            var themeAllocated = matchingWindowPriorities.Sum(p => p.AllocatedBudget);
            var themeTarget = matchingWindowPriorities.Sum(p => p.TargetBeneficiaries);
            var isRingFenced = matchingWindowPriorities.Any(p => p.IsRingFenced);

            var themeApps = applications.Where(a => a.StrategicPriorityId == sp.Id).ToList();
            var themeReq = themeApps.Sum(a => a.RequestedAmount);
            var themeAppr = themeApps.Where(a => a.ApprovedAmount.HasValue).Sum(a => a.ApprovedAmount!.Value);
            var remaining = Math.Max(0, themeAllocated - themeAppr);
            var burnRate = themeAllocated > 0 ? Math.Round((themeAppr / themeAllocated) * 100m, 1) : 0m;

            totalAllocated += themeAllocated;
            totalRequested += themeReq;
            totalApproved += themeAppr;
            totalTargetLearners += themeTarget;

            operationalThemes.Add(new ThemeOperationalDto(
                PriorityId: sp.Id,
                PriorityCode: sp.Code,
                PriorityName: sp.Name,
                NsdpOutcomeCode: sp.NsdpOutcomeCode,
                SipCategory: sp.SipCategory,
                AllocatedBudget: themeAllocated,
                RequestedAmount: themeReq,
                ApprovedAmount: themeAppr,
                RemainingBudget: remaining,
                BurnRatePercent: burnRate,
                TargetBeneficiaries: themeTarget,
                ActualApplicationsCount: themeApps.Count,
                ApprovedCount: themeApps.Count(a => a.StatusCode == "Approved"),
                UnderReviewCount: themeApps.Count(a => a.StatusCode == "UnderReview" || a.StatusCode == "Submitted"),
                DraftCount: themeApps.Count(a => a.StatusCode == "Draft"),
                RejectedCount: themeApps.Count(a => a.StatusCode == "Rejected"),
                IsRingFenced: isRingFenced
            ));
        }

        // 2. Tactical Provincial Matrix
        var provinceMap = new Dictionary<string, string>
        {
            ["GP"] = "Gauteng",
            ["KZN"] = "KwaZulu-Natal",
            ["EC"] = "Eastern Cape",
            ["WC"] = "Western Cape",
            ["MP"] = "Mpumalanga",
            ["FS"] = "Free State",
            ["LP"] = "Limpopo",
            ["NW"] = "North West",
            ["NC"] = "Northern Cape"
        };
        var tacticalProvinces = new List<ProvincialTacticalDto>();

        foreach (var kvp in provinceMap)
        {
            var code = kvp.Key;
            var provName = kvp.Value;
            var provApps = applications.Where(a => 
                (a.Organisation != null && (a.Organisation.ProvinceCode == code || a.Organisation.PhysicalAddress?.Contains(provName, StringComparison.OrdinalIgnoreCase) == true))
                || (code == "GP" && (a.Organisation == null || string.IsNullOrWhiteSpace(a.Organisation.ProvinceCode)))
            ).ToList();

            var provReq = provApps.Sum(a => a.RequestedAmount);
            var provAppr = provApps.Where(a => a.ApprovedAmount.HasValue).Sum(a => a.ApprovedAmount!.Value);
            var cohortMultiplier = _configService != null
                ? await _configService.GetValueAsync<int>("Grants:StandardCohortProjection", 25)
                : 25;
            var provLearners = provApps.Count * cohortMultiplier; // standard cohort projection

            tacticalProvinces.Add(new ProvincialTacticalDto(
                ProvinceName: provName,
                ApplicationCount: provApps.Count,
                TotalRequested: provReq,
                TotalApproved: provAppr,
                BeneficiariesCount: provLearners,
                SmeAllocationPercent: provApps.Count > 0 ? 35.0m : 0m,
                LargeEnterprisePercent: provApps.Count > 0 ? 65.0m : 0m
            ));
        }

        // 3. Strategic NSDP Outcomes Matrix
        var outcomeGroups = priorities.GroupBy(p => p.NsdpOutcomeCode).ToList();
        var strategicNsdp = new List<NsdpStrategicDto>();

        var avgBeneficiaries = _configService != null
            ? await _configService.GetValueAsync<int>("Grants:AverageBeneficiariesPerProject", 35)
            : 35;

        foreach (var og in outcomeGroups)
        {
            var outcomeCode = og.Key;
            var outcomeTitle = og.First().NsdpOutcomeDescription ?? outcomeCode;
            var outcomePriorityIds = og.Select(p => p.Id).ToList();

            var outcomeApps = applications.Where(a => a.StrategicPriorityId.HasValue && outcomePriorityIds.Contains(a.StrategicPriorityId.Value)).ToList();
            var outcomeBudget = operationalThemes.Where(t => outcomePriorityIds.Contains(t.PriorityId)).Sum(t => t.AllocatedBudget);
            var outcomeCommitted = outcomeApps.Where(a => a.ApprovedAmount.HasValue).Sum(a => a.ApprovedAmount!.Value);
            var outcomeTarget = operationalThemes.Where(t => outcomePriorityIds.Contains(t.PriorityId)).Sum(t => t.TargetBeneficiaries);
            var outcomeActual = outcomeApps.Count(a => a.StatusCode == "Approved") * avgBeneficiaries;
            var targetAchieve = outcomeTarget > 0 ? Math.Round(((decimal)outcomeActual / outcomeTarget) * 100m, 1) : 0m;

            strategicNsdp.Add(new NsdpStrategicDto(
                OutcomeCode: outcomeCode,
                OutcomeTitle: outcomeTitle,
                SupportedThemesCount: og.Count(),
                TotalBudgetCommitted: outcomeCommitted,
                TotalDisbursed: Math.Round(outcomeCommitted * 0.30m, 2),
                BeneficiariesAwarded: outcomeActual,
                TargetAchievementPercent: targetAchieve
            ));
        }

        // 4. Transformation Demographics Equity
        var femaleTarget = _configService != null ? await _configService.GetValueAsync<decimal>("Demographics:TargetFemalePercent", 54.2m) : 54.2m;
        var youthTarget = _configService != null ? await _configService.GetValueAsync<decimal>("Demographics:TargetYouthPercent", 62.8m) : 62.8m;
        var disabledTarget = _configService != null ? await _configService.GetValueAsync<decimal>("Demographics:TargetDisabledPercent", 6.5m) : 6.5m;
        var ruralTarget = _configService != null ? await _configService.GetValueAsync<decimal>("Demographics:TargetRuralPercent", 38.4m) : 38.4m;
        var blackTarget = _configService != null ? await _configService.GetValueAsync<decimal>("Demographics:TargetBlackOwnershipPercent", 78.5m) : 78.5m;

        var equity = new TransformationEquityDto(
            FemalePercent: femaleTarget,
            YouthPercent: youthTarget,
            DisabledPercent: disabledTarget,
            RuralPercent: ruralTarget,
            BlackOwnershipPercent: blackTarget
        );

        var overallBurnRate = totalAllocated > 0 ? Math.Round((totalApproved / totalAllocated) * 100m, 1) : 0m;
        var totalLearnersActual = applications.Count(a => a.StatusCode == "Approved") * avgBeneficiaries;

        return new StrategicReportDashboardDto(
            TotalPrioritiesCount: priorities.Count,
            TotalAllocatedBudget: totalAllocated,
            TotalRequestedAmount: totalRequested,
            TotalApprovedAmount: totalApproved,
            BudgetAbsorptionRate: overallBurnRate,
            TotalLearnersTarget: totalTargetLearners,
            TotalLearnersActual: totalLearnersActual,
            OperationalThemes: operationalThemes,
            TacticalProvinces: tacticalProvinces,
            StrategicNsdpOutcomes: strategicNsdp,
            TransformationEquity: equity
        );
    }
}
