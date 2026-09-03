namespace Nsdms.Application.Common;

/// <summary>
/// Result of extracting a single statutory flat file (SETMIS or NLRD).
/// </summary>
public class StatutoryFileExtractResult
{
    public string FileCode { get; set; } = string.Empty;
    public string FileTitle { get; set; } = string.Empty;
    public string FileName { get; set; } = string.Empty;
    public int RecordCount { get; set; }
    public int RecordLength { get; set; }
    public long FileSizeBytes { get; set; }
    public string ChecksumSha256 { get; set; } = string.Empty;
    public string Content { get; set; } = string.Empty;
    public byte[] ContentBytes { get; set; } = Array.Empty<byte>();
    public List<string> ValidationAnomalies { get; set; } = new();
}

/// <summary>
/// Summary descriptor of an extractable statutory file in the system.
/// </summary>
public class StatutoryBatchFileSummary
{
    public string FileCode { get; set; } = string.Empty;
    public string FileTitle { get; set; } = string.Empty;
    public string Standard { get; set; } = string.Empty; // "SETMIS" or "NLRD"
    public int RecordLength { get; set; }
    public int EstimatedRecordCount { get; set; }
    public string ExpectedFileNamePattern { get; set; } = string.Empty;
    public string Description { get; set; } = string.Empty;
}

/// <summary>
/// DTO representing a completed statutory package download.
/// </summary>
public class StatutoryZipArchiveResult
{
    public string ArchiveFileName { get; set; } = string.Empty;
    public byte[] ZipBytes { get; set; } = Array.Empty<byte>();
    public int TotalFilesCount { get; set; }
    public int TotalRecordsCount { get; set; }
    public string DigitalSecuritySeal { get; set; } = string.Empty;
}
