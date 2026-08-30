using System.ComponentModel.DataAnnotations.Schema;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Core demographic profile storing individual identity, SA ID/Passport, Washington Group functioning,
/// POPIA compliance, educational background, and contact details for SETMIS statutory reporting.
/// </summary>
public class Person : BaseEntity
{
    /// <summary>
    /// Honorific title (e.g. Mr, Mrs, Ms, Dr, Prof).
    /// </summary>
    public string? Title { get; set; }

    /// <summary>
    /// Legal first / given name(s).
    /// </summary>
    public string FirstName { get; set; } = string.Empty;

    /// <summary>
    /// Middle or secondary given name(s).
    /// </summary>
    public string? MiddleName { get; set; }

    /// <summary>
    /// Legal family surname.
    /// </summary>
    public string LastName { get; set; } = string.Empty;

    /// <summary>
    /// 13-digit South African National Identity Number.
    /// </summary>
    public string RsaIdNumber { get; set; } = string.Empty;

    /// <summary>
    /// Foreign passport number or alternate identification number for non-South African citizens.
    /// </summary>
    public string? PassportNumber { get; set; }

    /// <summary>
    /// SETMIS Alternate Identification Type Code (references lookup.AlternateIdType, e.g. 527 Passport, 540 Birth Certificate, 537 Work Permit).
    /// </summary>
    public string? AlternateIdTypeId { get; set; } = "527";

    /// <summary>
    /// Date of birth (auto-derived from RSA ID when available).
    /// </summary>
    public DateTime? DateOfBirth { get; set; }

    /// <summary>
    /// Gender description (e.g. Male, Female, Other).
    /// </summary>
    public string? Gender { get; set; }

    /// <summary>
    /// Statutory gender lookup code (references lookup.GenderType: F, M).
    /// </summary>
    public string? GenderCode { get; set; }

    /// <summary>
    /// Indicates whether the individual is a South African citizen.
    /// </summary>
    public bool? IsSouthAfricanCitizen { get; set; }

    /// <summary>
    /// Citizen status lookup code (references lookup.CitizenStatusType: SA, PR, D, O, U).
    /// </summary>
    public string? CitizenStatusCode { get; set; }

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
    /// South African province code of primary residence (references lookup.ProvinceType: GP, KZN, WC, EC, FS, MP, NW, NC, LP).
    /// </summary>
    public string? ProvinceCode { get; set; }

    #region Washington Group Functioning Disability Ratings (SETMIS File 400)
    /// <summary>
    /// Washington Group Seeing functional difficulty rating (references lookup.SeeingRatingType: 01 None to 06 Cannot determine).
    /// </summary>
    public string? SeeingRatingId { get; set; } = "01";

    /// <summary>
    /// Washington Group Hearing functional difficulty rating (references lookup.HearingRatingType: 01 None to 06 Cannot determine).
    /// </summary>
    public string? HearingRatingId { get; set; } = "01";

    /// <summary>
    /// Washington Group Mobility / Walking functional difficulty rating (references lookup.WalkingRatingType: 01 None to 06 Cannot determine).
    /// </summary>
    public string? WalkingRatingId { get; set; } = "01";

    /// <summary>
    /// Washington Group Memory / Cognitive functional difficulty rating (references lookup.RememberingRatingType: 01 None to 06 Cannot determine).
    /// </summary>
    public string? RememberingRatingId { get; set; } = "01";

    /// <summary>
    /// Washington Group Communication functional difficulty rating (references lookup.CommunicatingRatingType: 01 None to 06 Cannot determine).
    /// </summary>
    public string? CommunicatingRatingId { get; set; } = "01";

    /// <summary>
    /// Washington Group Self-Care functional difficulty rating (references lookup.SelfCareRatingType: 01 None to 06 Cannot determine).
    /// </summary>
    public string? SelfCareRatingId { get; set; } = "01";
    #endregion

    #region Education & Location Metadata (SETMIS File 400)
    /// <summary>
    /// Department of Basic Education EMIS (Education Management Information System) School Registration Number.
    /// </summary>
    public string? LastSchoolEmisNumber { get; set; }

    /// <summary>
    /// Year in which individual exited / matriculated from last school attended (YYYY format).
    /// </summary>
    public string? LastSchoolYear { get; set; }

    /// <summary>
    /// Statistics South Africa Spatial Area Code (references lookup.StatssaAreaCodeType).
    /// </summary>
    public string? StatssaAreaCode { get; set; }
    #endregion

    #region POPIA Compliance & Consent (SETMIS File 400)
    /// <summary>
    /// Protection of Personal Information Act Statutory Consent Status (references lookup.PopiActStatusType: 01 Agreed, 02 Declined, 98 Unknown).
    /// </summary>
    public string? PopiActStatusId { get; set; } = "01";

    /// <summary>
    /// Date when POPIA data processing consent was recorded.
    /// </summary>
    public DateTime? PopiActConsentDate { get; set; } = DateTime.UtcNow;
    #endregion

    #region Transition & Historical Tracking (SETMIS File 400)
    /// <summary>
    /// Previous legal family surname before marriage / legal change.
    /// </summary>
    public string? PreviousLastName { get; set; }

    /// <summary>
    /// Previous alternate identity number.
    /// </summary>
    public string? PreviousAlternateId { get; set; }

    /// <summary>
    /// Previous alternate identity type code.
    /// </summary>
    public string? PreviousAlternateIdTypeId { get; set; }

    /// <summary>
    /// Previous Skills Development Provider code associated with this individual.
    /// </summary>
    public string? PreviousProviderCode { get; set; }

    /// <summary>
    /// Previous Submitting ETQA ID associated with this individual.
    /// </summary>
    public string? PreviousProviderEtqaId { get; set; }
    #endregion

    #region Contact & Address Information
    /// <summary>
    /// Primary email contact address.
    /// </summary>
    public string Email { get; set; } = string.Empty;

    [NotMapped]
    public string EmailAddress { get => Email; set => Email = value; }

    /// <summary>
    /// Primary telephone contact number.
    /// </summary>
    public string PhoneNumber { get; set; } = string.Empty;

    /// <summary>
    /// Mobile / cellular phone number.
    /// </summary>
    public string? CellNumber { get; set; }

    [NotMapped]
    public string? CellPhoneNumber { get => CellNumber; set => CellNumber = value; }

    /// <summary>
    /// Facsimile contact number.
    /// </summary>
    public string? FaxNumber { get; set; }

    /// <summary>
    /// Physical residential street address line 1.
    /// </summary>
    public string? PhysicalAddress { get; set; }

    /// <summary>
    /// Physical residential address postal code.
    /// </summary>
    public string? PhysicalAddressPostalCode { get; set; }

    /// <summary>
    /// Postal delivery address.
    /// </summary>
    public string? PostalAddress { get; set; }

    /// <summary>
    /// Postal address delivery code.
    /// </summary>
    public string? PostalAddressPostalCode { get; set; }
    #endregion

    /// <summary>
    /// Indicates whether the person record is active.
    /// </summary>
    public bool IsActive { get; set; } = true;

    /// <summary>
    /// Computed full display name (FirstName + LastName).
    /// </summary>
    public string FullName => $"{FirstName} {LastName}".Trim();
}
