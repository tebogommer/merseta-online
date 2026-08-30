namespace Nsdms.Application.Validation;

public enum StatutoryValidationSeverity
{
    Warning = 1,
    Fatal = 2
}

public class StatutoryValidationError
{
    public string FileIdentifier { get; set; } = string.Empty;
    public int RecordId { get; set; }
    public string EntityName { get; set; } = string.Empty;
    public string RecordDescriptor { get; set; } = string.Empty;
    public string FieldName { get; set; } = string.Empty;
    public string? FieldValue { get; set; }
    public string RuleCode { get; set; } = string.Empty;
    public StatutoryValidationSeverity Severity { get; set; } = StatutoryValidationSeverity.Fatal;
    public string Message { get; set; } = string.Empty;
    public string Remediation { get; set; } = string.Empty;
}

public class StatutoryValidationReport
{
    public string SpecificationStandard { get; set; } = string.Empty; // "DHET SETMIS" or "SAQA NLRD"
    public DateTime EvaluationTimestamp { get; set; } = DateTime.UtcNow;
    public int TotalRecordsAudited { get; set; }
    public int CompliantRecordsCount { get; set; }
    public int FatalErrorsCount => Errors.Count(e => e.Severity == StatutoryValidationSeverity.Fatal);
    public int WarningsCount => Errors.Count(e => e.Severity == StatutoryValidationSeverity.Warning);
    public decimal CompliancePercentage => TotalRecordsAudited == 0 ? 100m : Math.Round((decimal)CompliantRecordsCount / TotalRecordsAudited * 100m, 2);

    public Dictionary<string, int> FileCounts { get; set; } = new();
    public List<StatutoryValidationError> Errors { get; set; } = new();
}
