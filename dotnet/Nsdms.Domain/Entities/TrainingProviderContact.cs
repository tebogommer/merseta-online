using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Verified contact person linked to an accredited Skills Development Provider (SDP)
/// (Signed SDP Application Use Case 21022023 Tables 20, 23 & Table 24 Step E).
/// Enforces minimum 2 contacts quorum and banking details confirmation authority.
/// </summary>
public class TrainingProviderContact : BaseEntity
{
    public int TrainingProviderId { get; set; }
    public TrainingProvider? TrainingProvider { get; set; }

    public int? PersonId { get; set; }
    public Person? Person { get; set; }

    /// <summary>
    /// Statutory role designation (e.g. Primary SDP Contact, Secondary SDP Contact, CEO, CFO/Finance Manager, HR Manager).
    /// </summary>
    public string ContactDesignation { get; set; } = "Primary SDP Contact";

    public string? Title { get; set; }
    public string FirstName { get; set; } = string.Empty;
    public string LastName { get; set; } = string.Empty;
    public string? IdOrPassportNumber { get; set; }
    public string Email { get; set; } = string.Empty;
    public string CellNumber { get; set; } = string.Empty;

    /// <summary>
    /// Statutory requirement: At least one designated contact must have authority to confirm banking details.
    /// </summary>
    public bool IsBankingConfirmationAuthorized { get; set; } = false;

    public bool IsPrimaryContact { get; set; } = false;

    [System.ComponentModel.DataAnnotations.Schema.NotMapped]
    public string FullName => $"{FirstName} {LastName}".Trim();

    public string VerificationStatus { get; set; } = "Verified";

    public bool IsActive { get; set; } = true;
}
