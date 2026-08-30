using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

public class LearnerTradeTest : BaseEntity
{
    public int CompanyLearnerId { get; set; }
    public CompanyLearner? CompanyLearner { get; set; }

    public string TestCenterName { get; set; } = string.Empty;
    public string TradeTitle { get; set; } = string.Empty; // Diesel Mechanic, Boilermaker, Fitter & Turner, Electrician, Welder
    public int AttemptNumber { get; set; } = 1;

    public DateTime TradeTestDate { get; set; } = DateTime.UtcNow;
    public string ResultStatusCode { get; set; } = "Scheduled"; // Scheduled, Competent, NotYetCompetent, Absent

    public int? AssessorPersonId { get; set; }
    public Person? AssessorPerson { get; set; }

    public int? ModeratorPersonId { get; set; }
    public Person? ModeratorPerson { get; set; }

    public string? SerialCertificateNumber { get; set; }
    public DateTime? CertificateIssueDate { get; set; }
    public string? Remarks { get; set; }
}
