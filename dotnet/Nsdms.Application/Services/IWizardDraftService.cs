using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

/// <summary>
/// Summary DTO representing an in-progress wizard draft session.
/// </summary>
public record WizardDraftSummaryDto(
    int Id,
    string DraftKey,
    string CandidateKey,
    string WizardTitle,
    string Route,
    string UserId,
    int? OrganisationId,
    int CurrentStepIndex,
    int CompletedStepCount,
    int TotalStepCount,
    string Status,
    DateTime ExpiresAtUtc,
    DateTime CreatedAt,
    DateTime? ModifiedAt
);

/// <summary>
/// Contract governing persistence and resumption of multi-step wizard drafts across all 9 statutory candidates.
/// </summary>
public interface IWizardDraftService
{
    /// <summary>
    /// Saves or updates an in-progress wizard draft session.
    /// </summary>
    Task<WizardDraftSession> SaveDraftAsync<TModel>(
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
    ) where TModel : class;

    /// <summary>
    /// Retrieves the most recent active draft for a specific candidate wizard and user.
    /// </summary>
    Task<WizardDraftSession?> GetActiveDraftAsync(
        string candidateKey,
        string userId,
        int? organisationId = null,
        CancellationToken cancellationToken = default
    );

    /// <summary>
    /// Deserializes the draft model into a strongly-typed model object.
    /// </summary>
    TModel? DeserializeDraftModel<TModel>(WizardDraftSession session) where TModel : class;

    /// <summary>
    /// Retrieves all active drafts for a user, optionally filtered by organisation context.
    /// </summary>
    Task<List<WizardDraftSummaryDto>> GetActiveDraftsForUserAsync(
        string userId,
        int? organisationId = null,
        CancellationToken cancellationToken = default
    );

    /// <summary>
    /// Marks a draft session as completed/submitted once the wizard finishes successfully.
    /// </summary>
    Task MarkAsSubmittedAsync(string draftKey, CancellationToken cancellationToken = default);

    /// <summary>
    /// Discards an in-progress draft so it won''t prompt the user again.
    /// </summary>
    Task DiscardDraftAsync(string draftKey, CancellationToken cancellationToken = default);
}
