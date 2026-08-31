using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public interface IGrantService
{
    Task<GrantFundingWindow> CreateFundingWindowAsync(GrantFundingWindow window, string currentUsername = "SYSTEM");
    Task<List<GrantFundingWindow>> GetFundingWindowsAsync(int? finYear = null, bool activeOnly = true);
    Task<GrantFundingWindow?> GetFundingWindowByIdAsync(int id);
    Task<bool> CloseFundingWindowAsync(int id, string currentUsername = "SYSTEM");

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
}

public record WspEligibilityResult(
    bool IsEligible,
    bool IsExempt,
    string Status,
    int? WspSubmissionId,
    string Reason,
    string? WspReferenceNumber = null,
    DateTime? ApprovalDate = null
);

public class GrantService : IGrantService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;

    public GrantService(INsdmsDbContextFactory contextFactory, IAuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
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
        return await db.GrantFundingWindows.FindAsync(id);
    }

    public async Task<bool> CloseFundingWindowAsync(int id, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var window = await db.GrantFundingWindows.FindAsync(id);
        if (window == null)
        {
            return false;
        }

        var beforeState = new { window.IsActive, window.ClosingDate };
        window.IsActive = false;
        window.ClosingDate = DateTime.UtcNow;
        window.ModifiedAt = DateTime.UtcNow;
        window.ModifiedBy = currentUsername;

        _audit.LogAction(db, "GrantFundingWindow", id, "CloseFundingWindow", currentUsername, beforeState, window);
        await db.SaveChangesAsync();

        return true;
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
        application.ApplicationDate = DateTime.UtcNow;
        application.CreatedAt = DateTime.UtcNow;
        application.CreatedBy = currentUsername;

        db.GrantApplications.Add(application);
        await db.SaveChangesAsync();

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
        var existing = await db.GrantApplications.FindAsync(application.Id);
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
            existing.WspSubmissionId
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
            .Include(g => g.WspSubmission)
            .Include(g => g.ProjectBudgets)
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

        // Standard 4-Tranche Milestone Schedule
        moa.Milestones.Add(new GrantMoaMilestone
        {
            MilestoneNumber = 1,
            MilestoneTitle = "Inception & Learner Registration",
            MilestoneDescription = "Bilateral MoA execution, proof of learner agreement upload, and project inception report.",
            DeliverableRequirement = "Signed MoA, Certified ID Copies, Proof of Enrolment on merSETA NSDMS.",
            TranchePercentage = 30m,
            TrancheAmount = Math.Round(contractValue * 0.30m, 2),
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
            TranchePercentage = 30m,
            TrancheAmount = Math.Round(contractValue * 0.30m, 2),
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
            TranchePercentage = 30m,
            TrancheAmount = Math.Round(contractValue * 0.30m, 2),
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
            TranchePercentage = 10m,
            TrancheAmount = contractValue - (Math.Round(contractValue * 0.30m, 2) * 3),
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
}
