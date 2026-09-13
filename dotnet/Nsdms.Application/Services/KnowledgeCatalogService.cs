using System.IO.Compression;
using System.Text;
using System.Text.Json;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Models;
using Nsdms.Application.DTOs;
using Nsdms.Application.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

/// <summary>
/// Application service implementing the Open Knowledge Format (OKF v0.2) Catalog and Governance Engine.
/// Manages concept documents, provenance sources, verification events, cross-linking, and bundle export.
/// </summary>
public class KnowledgeCatalogService : IKnowledgeCatalogService
{
    private readonly INsdmsDbContextFactory _dbFactory;
    private readonly IAuditService _auditService;
    private readonly IOkfFrontmatterParser _parser;
    private readonly ILogger<KnowledgeCatalogService> _logger;

    public KnowledgeCatalogService(
        INsdmsDbContextFactory dbFactory,
        IAuditService auditService,
        IOkfFrontmatterParser parser,
        ILogger<KnowledgeCatalogService> logger)
    {
        _dbFactory = dbFactory;
        _auditService = auditService;
        _parser = parser;
        _logger = logger;
    }

    public async Task<PagedResult<ConceptSummaryDto>> GetConceptsAsync(ConceptFilterDto filter, CancellationToken ct = default)
    {
        using var db = await _dbFactory.CreateDbContextAsync();

        var query = db.ConceptDocuments
            .AsNoTracking()
            .Include(c => c.Tags)
            .Include(c => c.Computation)
            .Where(c => c.IsActive);

        if (!string.IsNullOrWhiteSpace(filter.SearchTerm))
        {
            var term = filter.SearchTerm.Trim().ToLower();
            query = query.Where(c => 
                c.ConceptId.ToLower().Contains(term) ||
                c.Title.ToLower().Contains(term) ||
                (c.SummaryDescription != null && c.SummaryDescription.ToLower().Contains(term)));
        }

        if (!string.IsNullOrWhiteSpace(filter.ConceptType))
        {
            query = query.Where(c => c.ConceptType == filter.ConceptType);
        }

        if (filter.TrustTier.HasValue)
        {
            query = query.Where(c => c.DerivedTrustTier == filter.TrustTier.Value);
        }

        if (filter.Status.HasValue)
        {
            query = query.Where(c => c.LifecycleStatus == filter.Status.Value);
        }

        if (filter.IsStale.HasValue)
        {
            var now = DateTime.UtcNow;
            if (filter.IsStale.Value)
            {
                query = query.Where(c => c.StaleAfter.HasValue && c.StaleAfter.Value <= now);
            }
            else
            {
                query = query.Where(c => !c.StaleAfter.HasValue || c.StaleAfter.Value > now);
            }
        }

        if (!string.IsNullOrWhiteSpace(filter.Tag))
        {
            query = query.Where(c => c.Tags.Any(t => t.TagName == filter.Tag));
        }

        var totalCount = await query.CountAsync(ct);

        var items = await query
            .OrderByDescending(c => c.DerivedTrustTier)
            .ThenBy(c => c.ConceptId)
            .Skip((filter.PageNumber - 1) * filter.PageSize)
            .Take(filter.PageSize)
            .Select(c => new ConceptSummaryDto
            {
                Id = c.Id,
                ConceptId = c.ConceptId,
                Title = c.Title,
                ConceptType = c.ConceptType,
                SummaryDescription = c.SummaryDescription,
                DerivedTrustTier = c.DerivedTrustTier,
                LifecycleStatus = c.LifecycleStatus,
                StaleAfter = c.StaleAfter,
                GeneratedAt = c.GeneratedAt,
                GeneratedByActor = c.GeneratedByActor,
                Tags = c.Tags.Select(t => t.TagName).ToList(),
                HasComputation = c.Computation != null
            })
            .ToListAsync(ct);

        return new PagedResult<ConceptSummaryDto>
        {
            Items = items,
            TotalCount = totalCount,
            PageIndex = filter.PageNumber - 1,
            PageSize = filter.PageSize
        };
    }

    public async Task<ConceptDetailDto?> GetConceptByIdAsync(int id, CancellationToken ct = default)
    {
        using var db = await _dbFactory.CreateDbContextAsync();

        var entity = await db.ConceptDocuments
            .AsNoTracking()
            .Include(c => c.Bundle)
            .Include(c => c.Tags)
            .Include(c => c.Sources)
            .Include(c => c.Verifications)
            .Include(c => c.Computation)
                .ThenInclude(comp => comp!.Parameters)
            .Include(c => c.Computation)
                .ThenInclude(comp => comp!.Executions.OrderByDescending(e => e.ExecutedAt).Take(10))
            .Include(c => c.OutgoingCrossLinks)
            .Include(c => c.IncomingCrossLinks)
            .FirstOrDefaultAsync(c => c.Id == id, ct);

        return entity == null ? null : MapToDetailDto(entity);
    }

    public async Task<ConceptDetailDto?> GetConceptByConceptIdAsync(string conceptId, CancellationToken ct = default)
    {
        using var db = await _dbFactory.CreateDbContextAsync();

        var entity = await db.ConceptDocuments
            .AsNoTracking()
            .Include(c => c.Bundle)
            .Include(c => c.Tags)
            .Include(c => c.Sources)
            .Include(c => c.Verifications)
            .Include(c => c.Computation)
                .ThenInclude(comp => comp!.Parameters)
            .Include(c => c.Computation)
                .ThenInclude(comp => comp!.Executions.OrderByDescending(e => e.ExecutedAt).Take(10))
            .Include(c => c.OutgoingCrossLinks)
            .Include(c => c.IncomingCrossLinks)
            .FirstOrDefaultAsync(c => c.ConceptId == conceptId, ct);

        return entity == null ? null : MapToDetailDto(entity);
    }

    public async Task<ConceptDetailDto> CreateConceptAsync(CreateConceptCommand command, string currentUser, CancellationToken ct = default)
    {
        using var db = await _dbFactory.CreateDbContextAsync();

        var existing = await db.ConceptDocuments.AnyAsync(c => c.ConceptId == command.ConceptId, ct);
        if (existing)
        {
            throw new InvalidOperationException($"A concept with identifier '{command.ConceptId}' already exists in the catalog.");
        }

        var concept = new ConceptDocument
        {
            BundleId = command.BundleId,
            ConceptId = command.ConceptId.Trim(),
            ConceptType = string.IsNullOrWhiteSpace(command.ConceptType) ? "Concept" : command.ConceptType.Trim(),
            Title = command.Title.Trim(),
            SummaryDescription = command.SummaryDescription?.Trim(),
            ResourceUri = command.ResourceUri?.Trim(),
            BodyMarkdown = command.BodyMarkdown,
            LifecycleStatus = command.LifecycleStatus,
            StaleAfter = command.StaleAfter,
            DerivedTrustTier = TrustTier.Unverified,
            GeneratedAt = DateTime.UtcNow,
            GeneratedByActor = string.IsNullOrWhiteSpace(command.GeneratedByActor) ? $"human:{currentUser}" : command.GeneratedByActor.Trim(),
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUser
        };

        db.ConceptDocuments.Add(concept);
        await db.SaveChangesAsync(ct);

        // Add tags
        if (command.Tags.Count > 0)
        {
            foreach (var tag in command.Tags.Distinct(StringComparer.OrdinalIgnoreCase))
            {
                db.ConceptTags.Add(new ConceptTag
                {
                    ConceptId = concept.Id,
                    TagName = tag.Trim(),
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = currentUser
                });
            }
        }

        // Add sources
        if (command.Sources.Count > 0)
        {
            foreach (var src in command.Sources)
            {
                db.ConceptSources.Add(new ConceptSource
                {
                    ConceptId = concept.Id,
                    SourceIdAlias = src.SourceIdAlias.Trim(),
                    ResourceUri = src.ResourceUri.Trim(),
                    Title = src.Title?.Trim(),
                    AuthorActor = src.AuthorActor?.Trim(),
                    UsageCount = src.UsageCount,
                    LastModifiedAt = src.LastModifiedAt,
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = currentUser
                });
            }
        }

        // Add Attested Computation if defined
        if (command.Computation != null)
        {
            var comp = new AttestedComputation
            {
                ConceptId = concept.Id,
                Runtime = command.Computation.Runtime ?? "tsql",
                ComputationSql = command.Computation.ComputationSql,
                ExternalScriptPath = command.Computation.ExternalScriptPath,
                ExecutorResource = command.Computation.ExecutorResource ?? "references/skills/run-tsql.md",
                AttesterResource = command.Computation.AttesterResource ?? "references/attesters/tsql-equality.cs",
                ReceiptSchemaJson = command.Computation.ReceiptSchemaJson ?? "[\"execution_id\", \"executed_sql\", \"rows_affected\", \"result_digest\"]",
                IsActive = true,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = currentUser
            };
            db.AttestedComputations.Add(comp);
            await db.SaveChangesAsync(ct);

            if (command.Computation.Parameters.Count > 0)
            {
                foreach (var p in command.Computation.Parameters)
                {
                    db.ComputationParameters.Add(new ComputationParameter
                    {
                        ComputationId = comp.Id,
                        ParameterName = p.ParameterName.Trim(),
                        ParameterType = p.ParameterType.Trim(),
                        IsRequired = p.IsRequired,
                        DefaultValue = p.DefaultValue?.Trim(),
                        Description = p.Description?.Trim(),
                        CreatedAt = DateTime.UtcNow,
                        CreatedBy = currentUser
                    });
                }
            }
        }

        await db.SaveChangesAsync(ct);

        // Audited Change Log
        await _auditService.LogActionAsync(
            "ConceptDocument",
            concept.Id,
            "CreateConcept",
            currentUser,
            new { ConceptId = concept.ConceptId, Title = concept.Title, Type = concept.ConceptType, Actor = concept.GeneratedByActor });

        _logger.LogInformation("Concept {ConceptId} (ID: {Id}) created by {User}.", concept.ConceptId, concept.Id, currentUser);

        return (await GetConceptByIdAsync(concept.Id, ct))!;
    }

    public async Task<ConceptDetailDto> UpdateConceptAsync(UpdateConceptCommand command, string currentUser, CancellationToken ct = default)
    {
        using var db = await _dbFactory.CreateDbContextAsync();

        var concept = await db.ConceptDocuments
            .Include(c => c.Tags)
            .Include(c => c.Sources)
            .Include(c => c.Verifications)
            .Include(c => c.Computation)
                .ThenInclude(comp => comp!.Parameters)
            .FirstOrDefaultAsync(c => c.Id == command.Id, ct);

        if (concept == null)
        {
            throw new KeyNotFoundException($"Concept with ID #{command.Id} not found.");
        }

        var beforeSnapshot = new
        {
            concept.Title,
            concept.SummaryDescription,
            concept.BodyMarkdown,
            concept.LifecycleStatus,
            concept.StaleAfter
        };

        concept.Title = command.Title.Trim();
        concept.SummaryDescription = command.SummaryDescription?.Trim();
        concept.ResourceUri = command.ResourceUri?.Trim();
        concept.BodyMarkdown = command.BodyMarkdown;
        concept.LifecycleStatus = command.LifecycleStatus;
        concept.StaleAfter = command.StaleAfter;
        concept.ModifiedAt = DateTime.UtcNow;
        concept.ModifiedBy = currentUser;

        // Synchronize tags
        db.ConceptTags.RemoveRange(concept.Tags);
        foreach (var tag in command.Tags.Distinct(StringComparer.OrdinalIgnoreCase))
        {
            db.ConceptTags.Add(new ConceptTag
            {
                ConceptId = concept.Id,
                TagName = tag.Trim(),
                CreatedAt = DateTime.UtcNow,
                CreatedBy = currentUser
            });
        }

        // Synchronize sources
        db.ConceptSources.RemoveRange(concept.Sources);
        foreach (var src in command.Sources)
        {
            db.ConceptSources.Add(new ConceptSource
            {
                ConceptId = concept.Id,
                SourceIdAlias = src.SourceIdAlias.Trim(),
                ResourceUri = src.ResourceUri.Trim(),
                Title = src.Title?.Trim(),
                AuthorActor = src.AuthorActor?.Trim(),
                UsageCount = src.UsageCount,
                LastModifiedAt = src.LastModifiedAt,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = currentUser
            });
        }

        // Synchronize computation
        if (command.Computation != null)
        {
            if (concept.Computation == null)
            {
                concept.Computation = new AttestedComputation
                {
                    ConceptId = concept.Id,
                    Runtime = command.Computation.Runtime ?? "tsql",
                    ComputationSql = command.Computation.ComputationSql,
                    ExternalScriptPath = command.Computation.ExternalScriptPath,
                    ExecutorResource = command.Computation.ExecutorResource ?? "references/skills/run-tsql.md",
                    AttesterResource = command.Computation.AttesterResource ?? "references/attesters/tsql-equality.cs",
                    ReceiptSchemaJson = command.Computation.ReceiptSchemaJson ?? "[\"execution_id\", \"executed_sql\", \"rows_affected\", \"result_digest\"]",
                    IsActive = true,
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = currentUser
                };
                db.AttestedComputations.Add(concept.Computation);
            }
            else
            {
                concept.Computation.Runtime = command.Computation.Runtime ?? "tsql";
                concept.Computation.ComputationSql = command.Computation.ComputationSql;
                concept.Computation.ExternalScriptPath = command.Computation.ExternalScriptPath;
                concept.Computation.ExecutorResource = command.Computation.ExecutorResource ?? "references/skills/run-tsql.md";
                concept.Computation.AttesterResource = command.Computation.AttesterResource ?? "references/attesters/tsql-equality.cs";
                concept.Computation.ReceiptSchemaJson = command.Computation.ReceiptSchemaJson ?? "[\"execution_id\", \"executed_sql\", \"rows_affected\", \"result_digest\"]";
                concept.Computation.ModifiedAt = DateTime.UtcNow;
                concept.Computation.ModifiedBy = currentUser;

                db.ComputationParameters.RemoveRange(concept.Computation.Parameters);
            }

            foreach (var p in command.Computation.Parameters)
            {
                db.ComputationParameters.Add(new ComputationParameter
                {
                    ComputationId = concept.Computation.Id,
                    ParameterName = p.ParameterName.Trim(),
                    ParameterType = p.ParameterType.Trim(),
                    IsRequired = p.IsRequired,
                    DefaultValue = p.DefaultValue?.Trim(),
                    Description = p.Description?.Trim(),
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = currentUser
                });
            }
        }

        concept.DerivedTrustTier = DeriveTrustTier(concept.Verifications);
        await db.SaveChangesAsync(ct);

        // Audited Change Log
        await _auditService.LogActionAsync(
            "ConceptDocument",
            concept.Id,
            "UpdateConcept",
            currentUser,
            new { Before = beforeSnapshot, After = new { concept.Title, concept.LifecycleStatus, concept.StaleAfter } });

        return (await GetConceptByIdAsync(concept.Id, ct))!;
    }

    public async Task<bool> DeleteConceptAsync(int id, string currentUser, CancellationToken ct = default)
    {
        using var db = await _dbFactory.CreateDbContextAsync();

        var concept = await db.ConceptDocuments.FirstOrDefaultAsync(c => c.Id == id, ct);
        if (concept == null) return false;

        concept.IsActive = false;
        concept.ModifiedAt = DateTime.UtcNow;
        concept.ModifiedBy = currentUser;

        await db.SaveChangesAsync(ct);

        await _auditService.LogActionAsync(
            "ConceptDocument",
            concept.Id,
            "DeleteConcept",
            currentUser,
            new { ConceptId = concept.ConceptId, Title = concept.Title });

        _logger.LogInformation("Concept {ConceptId} marked inactive by {User}.", concept.ConceptId, currentUser);
        return true;
    }

    public async Task<bool> RecordVerificationAsync(int conceptId, RecordVerificationCommand command, string currentUser, CancellationToken ct = default)
    {
        using var db = await _dbFactory.CreateDbContextAsync();

        var concept = await db.ConceptDocuments
            .Include(c => c.Verifications)
            .FirstOrDefaultAsync(c => c.Id == conceptId, ct);

        if (concept == null)
        {
            throw new KeyNotFoundException($"Concept with ID #{conceptId} not found.");
        }

        var verifierActor = !string.IsNullOrWhiteSpace(command.VerifiedByActor)
            ? command.VerifiedByActor.Trim()
            : (!string.IsNullOrWhiteSpace(command.ReviewerName) ? $"human:{command.ReviewerName.Trim()}" : $"human:{currentUser}");

        // Enforce PFMA Dual Authorisation Governance (BR-OKF-005):
        // Proposer / author cannot verify their own work
        if (concept.GeneratedByActor.Equals(verifierActor, StringComparison.OrdinalIgnoreCase))
        {
            throw new InvalidOperationException("Dual Authorisation Governance Violation: The proposing author cannot verify their own concept.");
        }

        var notesCombined = command.Notes?.Trim();
        if (!string.IsNullOrWhiteSpace(command.ReviewerRole) || !string.IsNullOrWhiteSpace(command.VerificationProcess))
        {
            var roleProcessPrefix = $"[Role: {command.ReviewerRole ?? "Reviewer"}] [Process: {command.VerificationProcess ?? "Governance Review"}]";
            notesCombined = string.IsNullOrWhiteSpace(notesCombined) ? roleProcessPrefix : $"{roleProcessPrefix} {notesCombined}";
        }

        var verificationEvent = new ConceptVerificationEvent
        {
            ConceptId = concept.Id,
            VerifiedByActor = verifierActor,
            ActorType = string.IsNullOrWhiteSpace(command.ActorType) ? "human" : command.ActorType.Trim().ToLower(),
            VerifiedAt = DateTime.UtcNow,
            Notes = notesCombined,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUser
        };

        db.ConceptVerificationEvents.Add(verificationEvent);
        concept.Verifications.Add(verificationEvent);

        // Recalculate derived Trust Tier (BR-OKF-001)
        concept.DerivedTrustTier = DeriveTrustTier(concept.Verifications);
        concept.ModifiedAt = DateTime.UtcNow;
        concept.ModifiedBy = currentUser;

        await db.SaveChangesAsync(ct);

        await _auditService.LogActionAsync(
            "ConceptDocument",
            concept.Id,
            "RecordVerification",
            currentUser,
            new 
            { 
                Verifier = verifierActor, 
                ActorType = verificationEvent.ActorType, 
                NewTrustTier = concept.DerivedTrustTier.ToString(),
                Notes = verificationEvent.Notes 
            });

        _logger.LogInformation("Recorded verification for {ConceptId} by {Verifier}. New Trust Tier: {Tier}.", 
            concept.ConceptId, verifierActor, concept.DerivedTrustTier);

        return true;
    }

    public TrustTier DeriveTrustTier(IEnumerable<ConceptVerificationEvent> verifications)
    {
        var list = verifications.ToList();
        if (list.Count == 0) return TrustTier.Unverified;

        // If any human verifier exists => HumanReviewed (Tier 2)
        if (list.Any(v => v.ActorType.Equals("human", StringComparison.OrdinalIgnoreCase) || 
                          v.VerifiedByActor.StartsWith("human:", StringComparison.OrdinalIgnoreCase)))
        {
            return TrustTier.HumanReviewed;
        }

        // If only processes or machines verified => MachineConfirmed (Tier 1)
        return TrustTier.MachineConfirmed;
    }

    public async Task<List<KnowledgeBundle>> GetBundlesAsync(CancellationToken ct = default)
    {
        using var db = await _dbFactory.CreateDbContextAsync();
        return await db.KnowledgeBundles.AsNoTracking().Where(b => b.IsActive).OrderBy(b => b.DisplayName).ToListAsync(ct);
    }

    public async Task<byte[]> ExportBundleArchiveAsync(int bundleId, CancellationToken ct = default)
    {
        using var db = await _dbFactory.CreateDbContextAsync();

        var bundle = await db.KnowledgeBundles
            .Include(b => b.Concepts)
                .ThenInclude(c => c.Tags)
            .Include(b => b.Concepts)
                .ThenInclude(c => c.Sources)
            .Include(b => b.Concepts)
                .ThenInclude(c => c.Verifications)
            .Include(b => b.Concepts)
                .ThenInclude(c => c.Computation)
                    .ThenInclude(comp => comp!.Parameters)
            .FirstOrDefaultAsync(b => b.Id == bundleId, ct);

        if (bundle == null)
        {
            throw new KeyNotFoundException($"Bundle #{bundleId} not found.");
        }

        using var memoryStream = new MemoryStream();
        using (var archive = new ZipArchive(memoryStream, ZipArchiveMode.Create, true))
        {
            // 0. bundle.yaml
            var bundleYamlEntry = archive.CreateEntry("bundle.yaml");
            using (var writer = new StreamWriter(bundleYamlEntry.Open(), Encoding.UTF8))
            {
                writer.WriteLine($"bundle_code: \"{bundle.BundleCode}\"");
                writer.WriteLine($"display_name: \"{bundle.DisplayName}\"");
                writer.WriteLine($"okf_version: \"{bundle.OkfVersion}\"");
                if (!string.IsNullOrEmpty(bundle.Description))
                {
                    writer.WriteLine($"description: \"{bundle.Description}\"");
                }
            }

            // 1. Root index.md
            var indexEntry = archive.CreateEntry("index.md");
            using (var writer = new StreamWriter(indexEntry.Open(), Encoding.UTF8))
            {
                writer.WriteLine("---");
                writer.WriteLine($"okf_version: \"{bundle.OkfVersion}\"");
                writer.WriteLine("---");
                writer.WriteLine();
                writer.WriteLine($"# {bundle.DisplayName}");
                writer.WriteLine();
                writer.WriteLine(bundle.Description ?? "Open Knowledge Format bundle.");
                writer.WriteLine();
                writer.WriteLine("## Concepts");
                writer.WriteLine();

                foreach (var c in bundle.Concepts.Where(c => c.IsActive).OrderBy(c => c.ConceptId))
                {
                    writer.WriteLine($"* [{c.Title}]({c.ConceptId}.md) - {c.SummaryDescription ?? c.ConceptType} (Trust: {c.DerivedTrustTier})");
                }
            }

            // 2. Root log.md
            var logEntry = archive.CreateEntry("log.md");
            using (var writer = new StreamWriter(logEntry.Open(), Encoding.UTF8))
            {
                writer.WriteLine("# Knowledge Bundle Update Log");
                writer.WriteLine();
                writer.WriteLine($"## {DateTime.UtcNow:yyyy-MM-dd}");
                writer.WriteLine($"* **Export**: Generated bundle snapshot with {bundle.Concepts.Count} concepts.");
            }

            // 3. Individual concept markdown files
            foreach (var concept in bundle.Concepts.Where(c => c.IsActive))
            {
                var relativePath = $"{concept.ConceptId}.md";
                var fileEntry = archive.CreateEntry(relativePath);

                var parsed = new ParsedOkfDocument
                {
                    ConceptType = concept.ConceptType,
                    Title = concept.Title,
                    Description = concept.SummaryDescription,
                    Resource = concept.ResourceUri,
                    Tags = concept.Tags.Select(t => t.TagName).ToList(),
                    Status = concept.LifecycleStatus.ToString().ToLower(),
                    StaleAfter = concept.StaleAfter,
                    GeneratedBy = concept.GeneratedByActor,
                    GeneratedAt = concept.GeneratedAt,
                    Verifications = concept.Verifications.Select(v => new ParsedOkfVerification
                    {
                        By = v.VerifiedByActor,
                        At = v.VerifiedAt,
                        Notes = v.Notes
                    }).ToList(),
                    Sources = concept.Sources.Select(s => new ParsedOkfSource
                    {
                        Id = s.SourceIdAlias,
                        Resource = s.ResourceUri,
                        Title = s.Title,
                        Author = s.AuthorActor,
                        UsageCount = s.UsageCount,
                        LastModified = s.LastModifiedAt
                    }).ToList(),
                    Computation = concept.Computation == null ? null : new ParsedOkfComputation
                    {
                        Runtime = concept.Computation.Runtime,
                        ComputationPath = concept.Computation.ExternalScriptPath,
                        InlineComputation = concept.Computation.ComputationSql,
                        ExecutorResource = concept.Computation.ExecutorResource,
                        AttesterResource = concept.Computation.AttesterResource,
                        ReceiptKeys = JsonSerializer.Deserialize<List<string>>(concept.Computation.ReceiptSchemaJson) ?? new(),
                        Parameters = concept.Computation.Parameters.Select(p => new ParsedOkfParameter
                        {
                            Name = p.ParameterName,
                            Type = p.ParameterType,
                            Required = p.IsRequired,
                            DefaultValue = p.DefaultValue
                        }).ToList()
                    },
                    BodyMarkdown = concept.BodyMarkdown
                };

                var serialized = _parser.SerializeDocument(parsed);
                using var writer = new StreamWriter(fileEntry.Open(), Encoding.UTF8);
                writer.Write(serialized);
            }
        }

        return memoryStream.ToArray();
    }

    private ConceptDetailDto MapToDetailDto(ConceptDocument entity)
    {
        var htmlBody = _parser.RenderToHtml(entity.BodyMarkdown);

        return new ConceptDetailDto
        {
            Id = entity.Id,
            BundleId = entity.BundleId,
            BundleCode = entity.Bundle?.BundleCode ?? string.Empty,
            BundleName = entity.Bundle?.DisplayName ?? string.Empty,
            ConceptId = entity.ConceptId,
            ConceptType = entity.ConceptType,
            Title = entity.Title,
            SummaryDescription = entity.SummaryDescription,
            ResourceUri = entity.ResourceUri,
            BodyMarkdown = entity.BodyMarkdown,
            RenderedHtmlBody = htmlBody,
            LifecycleStatus = entity.LifecycleStatus,
            StaleAfter = entity.StaleAfter,
            DerivedTrustTier = entity.DerivedTrustTier,
            GeneratedAt = entity.GeneratedAt,
            GeneratedByActor = entity.GeneratedByActor,
            IsActive = entity.IsActive,
            CreatedAt = entity.CreatedAt,
            CreatedBy = entity.CreatedBy,
            ModifiedAt = entity.ModifiedAt,
            ModifiedBy = entity.ModifiedBy,
            Tags = entity.Tags.Select(t => t.TagName).ToList(),
            Sources = entity.Sources.Select(s => new ConceptSourceDto
            {
                Id = s.Id,
                SourceIdAlias = s.SourceIdAlias,
                ResourceUri = s.ResourceUri,
                Title = s.Title,
                AuthorActor = s.AuthorActor,
                UsageCount = s.UsageCount,
                LastModifiedAt = s.LastModifiedAt,
                UsageWindowStart = s.UsageWindowStart,
                UsageWindowEnd = s.UsageWindowEnd
            }).ToList(),
            Verifications = entity.Verifications.Select(v => new ConceptVerificationDto
            {
                Id = v.Id,
                VerifiedByActor = v.VerifiedByActor,
                ActorType = v.ActorType,
                VerifiedAt = v.VerifiedAt,
                Notes = v.Notes
            }).ToList(),
            Computation = entity.Computation == null ? null : new AttestedComputationDto
            {
                Id = entity.Computation.Id,
                ConceptId = entity.Computation.ConceptId,
                Runtime = entity.Computation.Runtime,
                ComputationSql = entity.Computation.ComputationSql,
                ExternalScriptPath = entity.Computation.ExternalScriptPath,
                ExecutorResource = entity.Computation.ExecutorResource,
                AttesterResource = entity.Computation.AttesterResource,
                ReceiptSchemaJson = entity.Computation.ReceiptSchemaJson,
                IsActive = entity.Computation.IsActive,
                Parameters = entity.Computation.Parameters.Select(p => new ComputationParameterDto
                {
                    Id = p.Id,
                    ParameterName = p.ParameterName,
                    ParameterType = p.ParameterType,
                    IsRequired = p.IsRequired,
                    DefaultValue = p.DefaultValue,
                    Description = p.Description
                }).ToList(),
                RecentExecutions = entity.Computation.Executions.Select(e => new ComputationExecutionAuditDto
                {
                    Id = e.Id,
                    ComputationId = e.ComputationId,
                    InvokedByActor = e.InvokedByActor,
                    BoundParametersJson = e.BoundParametersJson,
                    ExecutedSqlDigest = e.ExecutedSqlDigest,
                    ReceiptPayloadJson = e.ReceiptPayloadJson,
                    AttestationVerdict = e.AttestationVerdict,
                    AttestationFailureReason = e.AttestationFailureReason,
                    VerificationReference = e.VerificationReference,
                    ExecutionDurationMs = e.ExecutionDurationMs,
                    ExecutedAt = e.ExecutedAt
                }).ToList()
            },
            OutgoingCrossLinks = entity.OutgoingCrossLinks.Select(l => new ConceptCrossLinkDto
            {
                Id = l.Id,
                SourceConceptId = l.SourceConceptId,
                TargetConceptPath = l.TargetConceptPath,
                ResolvedTargetId = l.ResolvedTargetId,
                LinkText = l.LinkText
            }).ToList(),
            IncomingCrossLinks = entity.IncomingCrossLinks.Select(l => new ConceptCrossLinkDto
            {
                Id = l.Id,
                SourceConceptId = l.SourceConceptId,
                TargetConceptPath = l.TargetConceptPath,
                ResolvedTargetId = l.ResolvedTargetId,
                LinkText = l.LinkText
            }).ToList()
        };
    }
}
