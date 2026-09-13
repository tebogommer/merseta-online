using Nsdms.Domain.Entities;

namespace Nsdms.Application.DTOs;

public record ConceptSummaryDto
{
    public int Id { get; init; }
    public string ConceptId { get; init; } = string.Empty;
    public string Title { get; init; } = string.Empty;
    public string ConceptType { get; init; } = string.Empty;
    public string? SummaryDescription { get; init; }
    public TrustTier DerivedTrustTier { get; init; }
    public ConceptLifecycleStatus LifecycleStatus { get; init; }
    public DateTime? StaleAfter { get; init; }
    public bool IsStale => StaleAfter.HasValue && DateTime.UtcNow >= StaleAfter.Value;
    public DateTime GeneratedAt { get; init; }
    public string GeneratedByActor { get; init; } = string.Empty;
    public List<string> Tags { get; init; } = new();
    public bool HasComputation { get; init; }
}

public record ConceptDetailDto
{
    public int Id { get; init; }
    public int BundleId { get; init; }
    public string BundleCode { get; init; } = string.Empty;
    public string BundleName { get; init; } = string.Empty;
    public string ConceptId { get; init; } = string.Empty;
    public string ConceptType { get; init; } = string.Empty;
    public string Title { get; init; } = string.Empty;
    public string? SummaryDescription { get; init; }
    public string? ResourceUri { get; init; }
    public string BodyMarkdown { get; init; } = string.Empty;
    public string RenderedHtmlBody { get; init; } = string.Empty;
    public ConceptLifecycleStatus LifecycleStatus { get; init; }
    public DateTime? StaleAfter { get; init; }
    public bool IsStale => StaleAfter.HasValue && DateTime.UtcNow >= StaleAfter.Value;
    public TrustTier DerivedTrustTier { get; init; }
    public DateTime GeneratedAt { get; init; }
    public string GeneratedByActor { get; init; } = string.Empty;
    public bool IsActive { get; init; }
    public DateTime CreatedAt { get; init; }
    public string? CreatedBy { get; init; }
    public DateTime? ModifiedAt { get; init; }
    public string? ModifiedBy { get; init; }

    public List<string> Tags { get; init; } = new();
    public List<ConceptSourceDto> Sources { get; init; } = new();
    public List<ConceptVerificationDto> Verifications { get; init; } = new();
    public AttestedComputationDto? Computation { get; init; }
    public List<ConceptCrossLinkDto> OutgoingCrossLinks { get; init; } = new();
    public List<ConceptCrossLinkDto> IncomingCrossLinks { get; init; } = new();
}

public record ConceptSourceDto
{
    public int Id { get; init; }
    public string SourceIdAlias { get; init; } = string.Empty;
    public string ResourceUri { get; init; } = string.Empty;
    public string? Title { get; init; }
    public string? AuthorActor { get; init; }
    public long? UsageCount { get; init; }
    public DateTime? LastModifiedAt { get; init; }
    public DateTime? UsageWindowStart { get; init; }
    public DateTime? UsageWindowEnd { get; init; }
}

public record ConceptVerificationDto
{
    public int Id { get; init; }
    public string VerifiedByActor { get; init; } = string.Empty;
    public string ActorType { get; init; } = "human";
    public DateTime VerifiedAt { get; init; }
    public string? Notes { get; init; }
}

public record AttestedComputationDto
{
    public int Id { get; init; }
    public int ConceptId { get; init; }
    public string Runtime { get; init; } = "tsql";
    public string? ComputationSql { get; init; }
    public string? ExternalScriptPath { get; init; }
    public string ExecutorResource { get; init; } = string.Empty;
    public string AttesterResource { get; init; } = string.Empty;
    public string ReceiptSchemaJson { get; init; } = string.Empty;
    public bool IsActive { get; init; }

    public List<ComputationParameterDto> Parameters { get; init; } = new();
    public List<ComputationExecutionAuditDto> RecentExecutions { get; init; } = new();
}

public record ComputationParameterDto
{
    public int Id { get; init; }
    public string ParameterName { get; init; } = string.Empty;
    public string ParameterType { get; init; } = "nvarchar";
    public bool IsRequired { get; init; } = true;
    public string? DefaultValue { get; init; }
    public string? Description { get; init; }
}

public record ComputationExecutionAuditDto
{
    public long Id { get; init; }
    public int ComputationId { get; init; }
    public string InvokedByActor { get; init; } = string.Empty;
    public string BoundParametersJson { get; init; } = "{}";
    public string ExecutedSqlDigest { get; init; } = string.Empty;
    public string ReceiptPayloadJson { get; init; } = "{}";
    public string AttestationVerdict { get; init; } = "Pass";
    public string? AttestationFailureReason { get; init; }
    public string VerificationReference { get; init; } = string.Empty;
    public int ExecutionDurationMs { get; init; }
    public DateTime ExecutedAt { get; init; }
}

public record ConceptCrossLinkDto
{
    public int Id { get; init; }
    public int SourceConceptId { get; init; }
    public string TargetConceptPath { get; init; } = string.Empty;
    public int? ResolvedTargetId { get; init; }
    public string? LinkText { get; init; }
}

public record ConceptFilterDto
{
    public string? SearchTerm { get; init; }
    public string? ConceptType { get; init; }
    public TrustTier? TrustTier { get; init; }
    public ConceptLifecycleStatus? Status { get; init; }
    public bool? IsStale { get; init; }
    public string? Tag { get; init; }
    public int PageNumber { get; init; } = 1;
    public int PageSize { get; init; } = 20;
}

public record CreateConceptCommand
{
    public int BundleId { get; init; }
    public string ConceptId { get; init; } = string.Empty;
    public string ConceptType { get; init; } = string.Empty;
    public string Title { get; init; } = string.Empty;
    public string? SummaryDescription { get; init; }
    public string? ResourceUri { get; init; }
    public string BodyMarkdown { get; init; } = string.Empty;
    public ConceptLifecycleStatus LifecycleStatus { get; init; } = ConceptLifecycleStatus.Draft;
    public DateTime? StaleAfter { get; init; }
    public string GeneratedByActor { get; init; } = "SYSTEM";
    public List<string> Tags { get; init; } = new();
    public List<CreateConceptSourceItem> Sources { get; init; } = new();

    // Optional computation definition
    public CreateComputationItem? Computation { get; init; }
}

public record CreateConceptSourceItem
{
    public string SourceIdAlias { get; init; } = string.Empty;
    public string ResourceUri { get; init; } = string.Empty;
    public string? Title { get; init; }
    public string? AuthorActor { get; init; }
    public long? UsageCount { get; init; }
    public DateTime? LastModifiedAt { get; init; }
}

public record CreateComputationItem
{
    public string Runtime { get; init; } = "tsql";
    public string? ComputationSql { get; init; }
    public string? ExternalScriptPath { get; init; }
    public string ExecutorResource { get; init; } = "references/skills/run-tsql.md";
    public string AttesterResource { get; init; } = "references/attesters/tsql-equality.cs";
    public string ReceiptSchemaJson { get; init; } = "[\"execution_id\", \"executed_sql\", \"rows_affected\", \"result_digest\"]";
    public List<CreateParameterItem> Parameters { get; init; } = new();
}

public record CreateParameterItem
{
    public string ParameterName { get; init; } = string.Empty;
    public string ParameterType { get; init; } = "int";
    public bool IsRequired { get; init; } = true;
    public string? DefaultValue { get; init; }
    public string? Description { get; init; }
}

public record UpdateConceptCommand
{
    public int Id { get; init; }
    public string Title { get; init; } = string.Empty;
    public string? SummaryDescription { get; init; }
    public string? ResourceUri { get; init; }
    public string BodyMarkdown { get; init; } = string.Empty;
    public ConceptLifecycleStatus LifecycleStatus { get; init; }
    public DateTime? StaleAfter { get; init; }
    public List<string> Tags { get; init; } = new();
    public List<CreateConceptSourceItem> Sources { get; init; } = new();
    public CreateComputationItem? Computation { get; init; }
}

public record RecordVerificationCommand
{
    public int ConceptId { get; init; }
    public string VerifiedByActor { get; init; } = string.Empty;
    public string ActorType { get; init; } = "human";
    public string? ReviewerName { get; init; }
    public string? ReviewerRole { get; init; }
    public string? VerificationProcess { get; init; }
    public bool Passed { get; init; } = true;
    public string? Notes { get; init; }
    public string? Actor { get; init; }
}

public record ExecuteComputationCommand
{
    public int ComputationId { get; init; }
    public Dictionary<string, object?> Parameters { get; init; } = new();
    public string InvokedByActor { get; init; } = "SYSTEM";
}

public record AttestationExecutionResultDto
{
    public bool Success { get; init; }
    public string Verdict { get; init; } = "Pass"; // 'Pass', 'Fail', 'Warning'
    public string? FailureReason { get; init; }
    public string VerificationReference { get; init; } = string.Empty;
    public int ExecutionDurationMs { get; init; }
    public int RowsAffected { get; init; }
    public List<Dictionary<string, object?>> Results { get; init; } = new();
    public string ExecutedSql { get; init; } = string.Empty;
    public string ExecutedSqlDigest { get; init; } = string.Empty;
    public string ReceiptPayloadJson { get; init; } = "{}";
}
