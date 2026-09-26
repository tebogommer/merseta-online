namespace Nsdms.Application.Common.ExternalToolHooks;

/// <summary>
/// Status and health check telemetry for external security tools and enterprise integrations.
/// </summary>
public class ExternalToolStatus
{
    public bool IsConfigured { get; set; }
    public string Provider { get; set; } = string.Empty;
    public string StatusMessage { get; set; } = string.Empty;
    public DateTime LastCheckedUtc { get; set; } = DateTime.UtcNow;
    public bool IsHealthy { get; set; }
}

/// <summary>
/// Structured security telemetry event destined for external SIEM forwarder (Splunk, Microsoft Sentinel, Wazuh).
/// </summary>
public class SiemSecurityEvent
{
    public string EventId { get; set; } = Guid.NewGuid().ToString("N");
    public DateTime TimestampUtc { get; set; } = DateTime.UtcNow;
    public string Severity { get; set; } = "INFO"; // INFO, WARNING, ERROR, CRITICAL
    public string Category { get; set; } = "SecurityAudit";
    public string ActionName { get; set; } = string.Empty;
    public string Actor { get; set; } = string.Empty;
    public string? ClientIpAddress { get; set; }
    public string? TargetEntity { get; set; }
    public string? TargetRecordId { get; set; }
    public string? Description { get; set; }
    public string? MetadataJson { get; set; }
}

/// <summary>
/// Result of an MFA challenge initiation via Entra ID, Okta, or TOTP provider.
/// </summary>
public class MfaChallengeResult
{
    public bool Success { get; set; }
    public string? ChallengeId { get; set; }
    public string? Channel { get; set; } // AppNotification, SMS, Email, TOTP
    public string? Message { get; set; }
    public DateTime? ExpiresAtUtc { get; set; }
}

/// <summary>
/// Represents a security vulnerability or dependency finding ingested from Snyk, Dependabot, or SonarQube.
/// </summary>
public class VulnerabilityItem
{
    public string VulnerabilityId { get; set; } = string.Empty;
    public string PackageOrComponent { get; set; } = string.Empty;
    public string Severity { get; set; } = "Medium"; // Low, Medium, High, Critical
    public string Title { get; set; } = string.Empty;
    public string? Description { get; set; }
    public string? Remediation { get; set; }
    public DateTime DiscoveredAtUtc { get; set; } = DateTime.UtcNow;
}

/// <summary>
/// Structured payload representing an external vulnerability scan report batch.
/// </summary>
public class VulnerabilityScanPayload
{
    public string ScannerName { get; set; } = string.Empty;
    public string ScanId { get; set; } = string.Empty;
    public DateTime ScanTimestampUtc { get; set; } = DateTime.UtcNow;
    public List<VulnerabilityItem> Vulnerabilities { get; set; } = new();
}

/// <summary>
/// Status and aggregate findings of ingested vulnerability assessments.
/// </summary>
public class VulnerabilityReportResult
{
    public bool Success { get; set; }
    public string Scanner { get; set; } = string.Empty;
    public DateTime LastScanUtc { get; set; } = DateTime.UtcNow;
    public int TotalVulnerabilities { get; set; }
    public int CriticalCount { get; set; }
    public int HighCount { get; set; }
    public int MediumCount { get; set; }
    public int LowCount { get; set; }
    public List<VulnerabilityItem> Findings { get; set; } = new();
}

/// <summary>
/// Verification status of backup jobs (Azure Backup, Veeam) for business continuity and disaster recovery compliance.
/// </summary>
public class BackupVerificationStatus
{
    public bool IsVerified { get; set; }
    public string BackupTarget { get; set; } = string.Empty;
    public DateTime? LatestBackupUtc { get; set; }
    public TimeSpan? AgeSinceLastBackup { get; set; }
    public string RecoveryPointId { get; set; } = string.Empty;
    public string Status { get; set; } = string.Empty; // Succeeded, Failed, InProgress, Standby
    public string VerificationMessage { get; set; } = string.Empty;
}

/// <summary>
/// Request to log or verify an ITSM Change Advisory Board (CAB) ticket in Jira Service Management or ServiceNow.
/// </summary>
public class ItsmChangeTicketRequest
{
    public string Summary { get; set; } = string.Empty;
    public string Description { get; set; } = string.Empty;
    public string RiskLevel { get; set; } = "Normal"; // Standard, Normal, Emergency
    public string RequestedBy { get; set; } = string.Empty;
    public string ChangeCategory { get; set; } = "DatabaseMigration";
}

/// <summary>
/// Status of an ITSM change ticket verification check.
/// </summary>
public class ItsmTicketStatus
{
    public string TicketReference { get; set; } = string.Empty;
    public bool IsAuthorized { get; set; }
    public string ApprovalStatus { get; set; } = "Pending"; // Approved, Rejected, Pending, Standby
    public string? CabApprover { get; set; }
    public DateTime? ApprovedAtUtc { get; set; }
    public string Message { get; set; } = string.Empty;
}
