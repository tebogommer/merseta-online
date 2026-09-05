using System.Text.Json;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;

namespace Nsdms.Infrastructure.Services;

public class WizardDraftService : IWizardDraftService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly ILogger<WizardDraftService> _logger;
    private static readonly JsonSerializerOptions JsonOptions = new()
    {
        PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
        PropertyNameCaseInsensitive = true,
        WriteIndented = false
    };

    public WizardDraftService(INsdmsDbContextFactory contextFactory, ILogger<WizardDraftService> logger)
    {
        _contextFactory = contextFactory;
        _logger = logger;
    }

    public async Task<WizardDraftSession> SaveDraftAsync<TModel>(
        string candidateKey,
        string wizardTitle,
        string route,
        string userId,
        int? organisationId,
        int currentStepIndex,
        int totalStepCount,
        TModel model,
        string? existingDraftKey = null,
        CancellationToken cancellationToken = default
    ) where TModel : class
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var json = JsonSerializer.Serialize(model, JsonOptions);

        WizardDraftSession? session = null;
        if (!string.IsNullOrWhiteSpace(existingDraftKey))
        {
            session = await db.WizardDraftSessions
                .FirstOrDefaultAsync(s => s.DraftKey == existingDraftKey && s.IsActive, cancellationToken);
        }

        if (session == null)
        {
            session = await db.WizardDraftSessions
                .FirstOrDefaultAsync(s => s.CandidateKey == candidateKey 
                                       && s.UserId == userId 
                                       && s.OrganisationId == organisationId 
                                       && s.IsActive 
                                       && s.Status == "Active", cancellationToken);
        }

        if (session != null)
        {
            session.CurrentStepIndex = currentStepIndex;
            session.CompletedStepCount = Math.Max(session.CompletedStepCount, currentStepIndex);
            session.TotalStepCount = totalStepCount;
            session.DraftModelJson = json;
            session.ExpiresAtUtc = DateTime.UtcNow.AddDays(30);
            session.ModifiedAt = DateTime.UtcNow;
            session.ModifiedBy = userId;
        }
        else
        {
            var key = !string.IsNullOrWhiteSpace(existingDraftKey)
                ? existingDraftKey
                : $"DRAFT-{candidateKey.ToUpperInvariant()}-{Guid.NewGuid().ToString("N")[..8].ToUpperInvariant()}";

            session = new WizardDraftSession
            {
                DraftKey = key,
                CandidateKey = candidateKey,
                WizardTitle = wizardTitle,
                Route = route,
                UserId = userId,
                OrganisationId = organisationId,
                CurrentStepIndex = currentStepIndex,
                CompletedStepCount = currentStepIndex,
                TotalStepCount = totalStepCount,
                DraftModelJson = json,
                Status = "Active",
                ExpiresAtUtc = DateTime.UtcNow.AddDays(30),
                IsActive = true,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = userId
            };
            db.WizardDraftSessions.Add(session);
        }

        await db.SaveChangesAsync(cancellationToken);
        _logger.LogInformation("Saved wizard draft {DraftKey} for candidate {CandidateKey} at step {StepIndex}",
            session.DraftKey, candidateKey, currentStepIndex);

        return session;
    }

    public async Task<WizardDraftSession?> GetActiveDraftAsync(
        string candidateKey,
        string userId,
        int? organisationId = null,
        CancellationToken cancellationToken = default
    )
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.WizardDraftSessions
            .AsNoTracking()
            .Where(s => s.CandidateKey == candidateKey
                     && s.UserId == userId
                     && s.IsActive
                     && s.Status == "Active"
                     && s.ExpiresAtUtc > DateTime.UtcNow)
            .Where(s => organisationId == null || s.OrganisationId == organisationId)
            .OrderByDescending(s => s.ModifiedAt ?? s.CreatedAt)
            .FirstOrDefaultAsync(cancellationToken);
    }

    public TModel? DeserializeDraftModel<TModel>(WizardDraftSession session) where TModel : class
    {
        if (session == null || string.IsNullOrWhiteSpace(session.DraftModelJson))
        {
            return null;
        }

        try
        {
            return JsonSerializer.Deserialize<TModel>(session.DraftModelJson, JsonOptions);
        }
        catch (Exception ex)
        {
            _logger.LogWarning(ex, "Failed to deserialize wizard draft {DraftKey} into {ModelType}", session.DraftKey, typeof(TModel).Name);
            return null;
        }
    }

    public async Task<List<WizardDraftSummaryDto>> GetActiveDraftsForUserAsync(
        string userId,
        int? organisationId = null,
        CancellationToken cancellationToken = default
    )
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.WizardDraftSessions
            .AsNoTracking()
            .Where(s => s.UserId == userId && s.IsActive && s.Status == "Active" && s.ExpiresAtUtc > DateTime.UtcNow)
            .Where(s => organisationId == null || s.OrganisationId == organisationId)
            .OrderByDescending(s => s.ModifiedAt ?? s.CreatedAt)
            .Select(s => new WizardDraftSummaryDto(
                s.Id,
                s.DraftKey,
                s.CandidateKey,
                s.WizardTitle,
                s.Route,
                s.UserId,
                s.OrganisationId,
                s.CurrentStepIndex,
                s.CompletedStepCount,
                s.TotalStepCount,
                s.Status,
                s.ExpiresAtUtc,
                s.CreatedAt,
                s.ModifiedAt
            ))
            .ToListAsync(cancellationToken);
    }

    public async Task MarkAsSubmittedAsync(string draftKey, CancellationToken cancellationToken = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var session = await db.WizardDraftSessions.FirstOrDefaultAsync(s => s.DraftKey == draftKey, cancellationToken);
        if (session != null)
        {
            session.Status = "Submitted";
            session.IsActive = false;
            session.ModifiedAt = DateTime.UtcNow;
            await db.SaveChangesAsync(cancellationToken);
            _logger.LogInformation("Marked wizard draft {DraftKey} as Submitted", draftKey);
        }
    }

    public async Task DiscardDraftAsync(string draftKey, CancellationToken cancellationToken = default)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var session = await db.WizardDraftSessions.FirstOrDefaultAsync(s => s.DraftKey == draftKey, cancellationToken);
        if (session != null)
        {
            session.Status = "Discarded";
            session.IsActive = false;
            session.ModifiedAt = DateTime.UtcNow;
            await db.SaveChangesAsync(cancellationToken);
            _logger.LogInformation("Discarded wizard draft {DraftKey}", draftKey);
        }
    }
}
