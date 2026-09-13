namespace Nsdms.Application.Interfaces;

public record ParsedOkfDocument
{
    public string ConceptType { get; init; } = string.Empty;
    public string? Title { get; init; }
    public string? Description { get; init; }
    public string? Resource { get; init; }
    public List<string> Tags { get; init; } = new();
    public string Status { get; init; } = "stable";
    public DateTime? StaleAfter { get; init; }
    public string? GeneratedBy { get; init; }
    public DateTime? GeneratedAt { get; init; }
    public List<ParsedOkfVerification> Verifications { get; init; } = new();
    public List<ParsedOkfSource> Sources { get; init; } = new();
    public ParsedOkfComputation? Computation { get; init; }
    public string BodyMarkdown { get; init; } = string.Empty;
    public List<string> FootnoteReferenceIds { get; init; } = new();
    public List<string> InternalCrossLinks { get; init; } = new();
    public Dictionary<string, object?> RawFrontmatter { get; init; } = new();
}

public record ParsedOkfVerification
{
    public string By { get; init; } = string.Empty;
    public DateTime At { get; init; }
    public string? Notes { get; init; }
}

public record ParsedOkfSource
{
    public string? Id { get; init; }
    public string Resource { get; init; } = string.Empty;
    public string? Title { get; init; }
    public string? Author { get; init; }
    public long? UsageCount { get; init; }
    public DateTime? LastModified { get; init; }
}

public record ParsedOkfComputation
{
    public string Runtime { get; init; } = "tsql";
    public string? ComputationPath { get; init; }
    public string? InlineComputation { get; init; }
    public string ExecutorResource { get; init; } = string.Empty;
    public string AttesterResource { get; init; } = string.Empty;
    public List<string> ReceiptKeys { get; init; } = new();
    public List<ParsedOkfParameter> Parameters { get; init; } = new();
}

public record ParsedOkfParameter
{
    public string Name { get; init; } = string.Empty;
    public string Type { get; init; } = "nvarchar";
    public bool Required { get; init; } = true;
    public string? DefaultValue { get; init; }
}

public interface IOkfFrontmatterParser
{
    ParsedOkfDocument ParseDocument(string rawContent);
    string RenderToHtml(string markdownBody);
    string SerializeDocument(ParsedOkfDocument doc);
}
