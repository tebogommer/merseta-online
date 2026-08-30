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
    Task<bool> RemoveProjectBudgetAsync(int budgetId, string currentUsername = "SYSTEM");
}

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

        if (string.IsNullOrWhiteSpace(application.StatusCode))
        {
            application.StatusCode = "Submitted";
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
            existing.StatusCode,
            existing.RequestedAmount,
            existing.ApprovedAmount
        };

        existing.ProjectTitle = application.ProjectTitle;
        existing.GrantTypeCode = application.GrantTypeCode;
        existing.StatusCode = application.StatusCode;
        existing.RequestedAmount = application.RequestedAmount;
        existing.ApprovedAmount = application.ApprovedAmount;
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
            query = query.Where(g => g.StatusCode == status);
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

        var beforeState = new { application.StatusCode, application.ApprovedAmount };

        application.StatusCode = "Approved";
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

        var beforeState = new { application.StatusCode };

        application.StatusCode = "Rejected";
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
            application.StatusCode
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
        var application = await db.GrantAppli