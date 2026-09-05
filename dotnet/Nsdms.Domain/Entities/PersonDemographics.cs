using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Vertically partitioned satellite entity storing demographic, language, equity, and statutory POPIA consent metadata.
/// </summary>
public class PersonDemographics : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent Person record.
    /// </summary>
    public int PersonId { get; set; }

    /// <summary>
    /// Navigational reference to the parent Person record.
    /// </summary>
    public Person? Person { get; set; }

    /// <summary>
    /// BBBEE / SETMIS statutory equity classification code (references lookup.EquityType: BA, BC, BI, WH, OTH).
    /// </summary>
    public string? EquityCode { get; set; }

    /// <summary>
    /// Legacy disability classification lookup code (references lookup.DisabilityType).
    /// </summary>
    public string? DisabilityCode { get; set; }

    /// <summary>
    /// Country nationality lookup code (references lookup.NationalityType: SA, SDC, NAM, ZIM, etc.).
    /// </summary>
    public string? NationalityCode { get; set; }

    /// <summary>
    /// Home / native language classification code (references lookup.HomeLanguageType: ENG, AFR, ZUL, XHO, SASL, etc.).
    /// </summary>
    public string? HomeLanguageCode { get; set; }

    /// <summary>
    /// Citizen status lookup code (references lookup.CitizenStatusType: SA, PR, D, O, U).
    /// </summary>
    public string? CitizenStatusCode { get; set; }

    /// <summary>
    /// Protection of Personal Information Act Statutory Consent Status (references lookup.PopiActStatusType: 01 Agreed, 02 Declined, 98 Unknown).
    /// </summary>
    public string? PopiActStatusId { get; set; } = "01";

    /// <summary>
    /// Date when POPIA data processing consent was recorded.
    /// </summary>
    public DateTime? PopiActConsentDate { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Department of Basic Education EMIS School Registration Number.
    /// </summary>
    public string? LastSchoolEmisNumber { get; set; }

    /// <summary>
    /// Year in which individual exited / matriculated from last school attended (YYYY format).
    /// </summary>
    public string? LastSchoolYear { get; set; }
}
