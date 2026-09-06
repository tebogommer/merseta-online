using System.ComponentModel.DataAnnotations.Schema;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Trade test final competency evaluation and artisan certification attempt records (ARPL &amp; standard),
/// fully normalized with statutory columns required for SETMIS File 505 (Trade Test) reporting.
/// </summary>
public class LearnerTradeTest : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the registered CompanyLearner contract.
    /// </summary>
    public int CompanyLearnerId { get; set; }
    [NotMapped]
    public int EnrolmentId { get => CompanyLearnerId; set => CompanyLearnerId = value; }
    [NotMapped]
    public int LearnerEnrolmentId { get => CompanyLearnerId; set => CompanyLearnerId = value; }

    /// <summary>
    /// Navigational reference to the candidate CompanyLearner.
    /// </summary>
    public CompanyLearner? CompanyLearner { get; set; }

    /// <summary>
    /// Name and facility location of accredited Trade Test Center (TTC).
    /// </summary>
    public string? TestCenterName { get; set; } = string.Empty;

    /// <summary>
    /// Accredited Trade Test Centre (TTC) Provider Code as registered on NLRD/SETMIS (SETMIS File 505).
    /// </summary>
    public string? TradeTestCentreCode { get; set; } = string.Empty;

    /// <summary>
    /// Submitting ETQA ID of Trade Test Centre (references lookup.SetaType, default 17 for merSETA).
    /// </summary>
    public string? TradeTestCentreEtqaId { get; set; } = "17";

    /// <summary>
    /// Designated artisan trade title (e.g. Diesel Mechanic, Boilermaker, Fitter &amp; Turner, Electrician, Welder).
    /// </summary>
    public string? TradeTitle { get; set; } = string.Empty;

    /// <summary>
    /// OFO Trade Code corresponding to the trade test (SETMIS File 505).
    /// </summary>
    public string? TradeCode { get; set; }

    /// <summary>
    /// SAQA Qualification ID or OFO Trade Code corresponding to the trade test (SETMIS File 505).
    /// </summary>
    public string? QualificationId { get; set; } = string.Empty;

    /// <summary>
    /// Trade test examination attempt index number (e.g. 1, 2, 3) for SETMIS File 505.
    /// </summary>
    public int TradeTestNumber { get; set; } = 1;

    [NotMapped]
    public int AttemptNumber { get => TradeTestNumber; set => TradeTestNumber = value; }

    /// <summary>
    /// Examination date on which the trade test was conducted.
    /// </summary>
    public DateTime TradeTestDate { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Trade test competency outcome lookup code (references lookup.TradeTestResultType: 01 Competent, 02 Not Yet Competent).
    /// </summary>
    public string? TradeTestResultId { get; set; } = "01";

    /// <summary>
    /// Statutory assessment outcome reason code (references lookup.TradeTestResultReasonType: 01 Assessment Criteria Satisfied).
    /// </summary>
    public string? TradeTestResultReasonId { get; set; } = "01";

    /// <summary>
    /// Legacy assessment outcome status code (e.g. Scheduled, Competent, NotYetCompetent, Absent).
    /// </summary>
    public string? ResultStatusCode { get; set; } = "Scheduled";

    /// <summary>
    /// Registered ETQA Assessor registration number conducting examination (SETMIS File 505).
    /// </summary>
    public string? AssessorRegistrationNumber { get; set; }

    /// <summary>
    /// Submitting ETQA ID of the registered Assessor (default 17).
    /// </summary>
    public string? AssessorEtqaId { get; set; } = "17";

    /// <summary>
    /// Registered ETQA Moderator / NAMB Verifier registration number (SETMIS File 505).
    /// </summary>
    public string? ModeratorRegistrationNumber { get; set; }

    /// <summary>
    /// Submitting ETQA ID of the registered Moderator (default 17).
    /// </summary>
    public string? ModeratorEtqaId { get; set; } = "17";

    /// <summary>
    /// Foreign key referencing original Skills Development Training Provider (SDP).
    /// </summary>
    public int? TrainingProviderId { get; set; }

    /// <summary>
    /// Navigational reference to original training provider.
    /// </summary>
    public TrainingProvider? TrainingProvider { get; set; }

    /// <summary>
    /// Original training provider Submitting ETQA ID (default 17).
    /// </summary>
    public string? TrainingProviderEtqaId { get; set; } = "17";

    /// <summary>
    /// Foreign key referencing the examining trade test assessor Person.
    /// </summary>
    public int? AssessorPersonId { get; set; }

    /// <summary>
    /// Navigational reference to the examining assessor Person.
    /// </summary>
    public Person? AssessorPerson { get; set; }

    /// <summary>
    /// Foreign key referencing the verifying moderator Person.
    /// </summary>
    public int? ModeratorPersonId { get; set; }

    /// <summary>
    /// Navigational reference to the moderator Person.
    /// </summary>
    public Person? ModeratorPerson { get; set; }

    /// <summary>
    /// National Red Seal Artisan Trade Certificate serial number issued upon competency.
    /// </summary>
    public string? SerialCertificateNumber { get; set; }

    /// <summary>
    /// Date when the artisan qualification certificate was issued.
    /// </summary>
    public DateTime? CertificateIssueDate { get; set; }

    /// <summary>
    /// Assessment feedback and examiner moderation remarks.
    /// </summary>
    public string? Remarks { get; set; }

    /// <summary>
    /// Foreign key linking this statutory SETMIS File 505 record to the originating operational trade test application.
    /// </summary>
    public int? LearnerTradeTestApplicationId { get; set; }
    public LearnerTradeTestApplication? LearnerTradeTestApplication { get; set; }
}
