using System.IO.Compression;
using System.Text;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging.Abstractions;
using Nsdms.Application.Common;
using Nsdms.Application.DTOs;
using Nsdms.Application.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Nsdms.Infrastructure.Services;
using Xunit;

namespace Nsdms.Tests;

/// <summary>
/// Comprehensive automated test suite for the Open Knowledge Format (OKF v0.2) specification.
/// Validates:
/// - Permissive YAML frontmatter parsing (§11) and citation extraction (§4)
/// - BR-OKF-001: Trust Tier derivation (Tier 0 Unverified -> Tier 1 Machine Confirmed -> Tier 2 Human Reviewed)
/// - BR-OKF-002: Temporal staleness boundaries and freshness clamps
/// - BR-OKF-003: Immutable computation parameter enforcement
/// - BR-OKF-004: Deterministic T-SQL attestation and Digital Security Seal receipt generation
/// - BR-OKF-005: Dual Authorisation Control / Segregation of Duties governance on human sign-offs
/// - Complete CRUD and OKF .zip bundle export
/// </summary>
public class KnowledgeCatalogTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, KnowledgeCatalogService catalogService, TsqlAttestationEngine attestationEngine, OkfFrontmatterParser parser) CreateTestContext()
    {
        var dbName = Guid.NewGuid().ToString();
        var factory = new TestDbContextFactory(dbName);
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var auditService = new AuditService(factory);
        var parser = new OkfFrontmatterParser();

        var catalogService = new KnowledgeCatalogService(
            factory,
            auditService,
            parser,
            NullLogger<KnowledgeCatalogService>.Instance);

        var attestationEngine = new TsqlAttestationEngine(
            factory,
            NullLogger<TsqlAttestationEngine>.Instance);

        return (factory, db, catalogService, attestationEngine, parser);
    }

    #region 1. OKF Frontmatter & Markdown Parser Tests (§4, §11)

    [Fact]
    public void ParseDocument_WithCompleteFrontmatter_ExtractsAllFieldsCorrectly()
    {
        // Arrange
        var parser = new OkfFrontmatterParser();
        var rawOkfMarkdown = @"---
concept_id: SETA-MANDATORY-GRANT-01
concept_type: StatutoryRegulation
title: Mandatory Grant 20% Calculation Rule
description: Calculates the statutory 20% Mandatory Grant levy rebate.
stale_after: 2027-04-30T23:59:59Z
generated_by: agent:statutory-codegen-v1
tags:
  - mandatory-grant
  - wsp-atr
  - sars-levy
sources:
  - id: SDA-1998
    resource: statutory://dhet.gov.za/sda-1998-reg4
    title: Skills Development Act Grant Regulations 4(1)
    author: DHET Minister
---
# Mandatory Grant 20% Calculation Rule

Under the Skills Development Act Grant Regulations, an employer who submits an approved WSP and ATR is entitled to a 20% rebate[^1].

[^1]: Skills Development Act No. 97 of 1998, Regulation 4(1).
";

        // Act
        var result = parser.ParseDocument(rawOkfMarkdown);

        // Assert
        Assert.NotNull(result);
        Assert.Equal("StatutoryRegulation", result.ConceptType);
        Assert.Equal("Mandatory Grant 20% Calculation Rule", result.Title);
        Assert.Equal("Calculates the statutory 20% Mandatory Grant levy rebate.", result.Description);
        Assert.Equal("agent:statutory-codegen-v1", result.GeneratedBy);
        Assert.Equal(3, result.Tags.Count);
        Assert.Contains("mandatory-grant", result.Tags);
        Assert.Single(result.Sources);
        Assert.Equal("SDA-1998", result.Sources[0].Id);
        Assert.NotNull(result.StaleAfter);
        Assert.Equal(2027, result.StaleAfter!.Value.Year);
        Assert.Equal(4, result.StaleAfter!.Value.Month);
        Assert.Single(result.FootnoteReferenceIds);
        Assert.Equal("1", result.FootnoteReferenceIds[0]);
        Assert.Contains("Under the Skills Development Act", result.BodyMarkdown);
    }

    [Fact]
    public void ParseDocument_WithoutFrontmatter_ExtractsBodyPermissively()
    {
        // Arrange (OKF §11 Conformance: permissive consumption of documents without frontmatter)
        var parser = new OkfFrontmatterParser();
        var rawMarkdown = "# Plain Document\n\nThis is a plain document without YAML frontmatter.";

        // Act
        var result = parser.ParseDocument(rawMarkdown);

        // Assert
        Assert.NotNull(result);
        Assert.Equal("Concept", result.ConceptType);
        Assert.Equal(rawMarkdown, result.BodyMarkdown);
        Assert.Empty(result.Tags);
        Assert.Empty(result.Sources);
    }

    [Fact]
    public void ParseDocument_WithCrossLinks_ExtractsRelativeReferences()
    {
        // Arrange
        var parser = new OkfFrontmatterParser();
        var rawMarkdown = @"---
title: Cross Link Test
---
See [Training Plan Specification](/skills/training-plans.md) and [WSP Submission Guidelines](/grants/wsp-guidelines.md).
";

        // Act
        var result = parser.ParseDocument(rawMarkdown);

        // Assert
        Assert.NotNull(result);
        Assert.Equal(2, result.InternalCrossLinks.Count);
        Assert.Equal("/skills/training-plans.md", result.InternalCrossLinks[0]);
        Assert.Equal("/grants/wsp-guidelines.md", result.InternalCrossLinks[1]);
    }

    [Fact]
    public void SerializeFrontmatter_GeneratesValidYaml()
    {
        // Arrange
        var parser = new OkfFrontmatterParser();
        var doc = new ParsedOkfDocument
        {
            ConceptType = "Policy",
            Title = "Test Policy Document",
            Description = "A test policy.",
            GeneratedBy = "human:admin",
            Tags = new List<string> { "tag1", "tag2" },
            BodyMarkdown = "## Policy Statement\n\nThis is the body."
        };

        // Act
        var serialized = parser.SerializeDocument(doc);

        // Assert
        Assert.StartsWith("---", serialized);
        Assert.Contains("title: Test Policy Document", serialized);
        Assert.Contains("tag1", serialized);
        Assert.Contains("## Policy Statement", serialized);
    }

    #endregion

    #region 2. BR-OKF-001: Trust Tier Derivation Tests

    [Fact]
    public void DeriveTrustTier_NoVerifications_ReturnsUnverified()
    {
        // Arrange
        var (_, _, catalogService, _, _) = CreateTestContext();
        var verifications = new List<ConceptVerificationEvent>();

        // Act
        var tier = catalogService.DeriveTrustTier(verifications);

        // Assert
        Assert.Equal(TrustTier.Unverified, tier);
    }

    [Fact]
    public void DeriveTrustTier_OnlyProcessVerifications_ReturnsMachineConfirmed()
    {
        // Arrange
        var (_, _, catalogService, _, _) = CreateTestContext();
        var verifications = new List<ConceptVerificationEvent>
        {
            new ConceptVerificationEvent
            {
                VerifiedByActor = "process:nightly-recon",
                ActorType = "process",
                VerifiedAt = DateTime.UtcNow
            }
        };

        // Act
        var tier = catalogService.DeriveTrustTier(verifications);

        // Assert
        Assert.Equal(TrustTier.MachineConfirmed, tier);
    }

    [Fact]
    public void DeriveTrustTier_HumanVerificationPresent_ReturnsHumanReviewed()
    {
        // Arrange
        var (_, _, catalogService, _, _) = CreateTestContext();
        var verifications = new List<ConceptVerificationEvent>
        {
            new ConceptVerificationEvent
            {
                VerifiedByActor = "process:nightly-recon",
                ActorType = "process",
                VerifiedAt = DateTime.UtcNow.AddDays(-5)
            },
            new ConceptVerificationEvent
            {
                VerifiedByActor = "human:officer_smith",
                ActorType = "human",
                VerifiedAt = DateTime.UtcNow
            }
        };

        // Act
        var tier = catalogService.DeriveTrustTier(verifications);

        // Assert
        Assert.Equal(TrustTier.HumanReviewed, tier);
    }

    #endregion

    #region 3. BR-OKF-002: Temporal Staleness Boundary Tests

    [Fact]
    public async Task GetConceptsAsync_PastStaleAfter_FlagsConceptAsStale()
    {
        // Arrange
        var (_, db, catalogService, _, _) = CreateTestContext();

        var bundle = new KnowledgeBundle
        {
            BundleCode = "merseta-governance",
            DisplayName = "merSETA Governance Bundle",
            FileSystemPath = "/bundles/merseta-governance",
            OkfVersion = "0.2",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.KnowledgeBundles.Add(bundle);
        await db.SaveChangesAsync();

        var staleConcept = new ConceptDocument
        {
            BundleId = bundle.Id,
            ConceptId = "STALE-001",
            ConceptType = "AnnualTarget",
            Title = "Expired 2024 Targets",
            BodyMarkdown = "Targets for the prior cycle.",
            StaleAfter = DateTime.UtcNow.AddDays(-10), // Expired 10 days ago
            DerivedTrustTier = TrustTier.HumanReviewed,
            GeneratedByActor = "human:planner",
            CreatedAt = DateTime.UtcNow.AddYears(-1),
            CreatedBy = "System"
        };
        db.ConceptDocuments.Add(staleConcept);
        await db.SaveChangesAsync();

        // Act
        var filter = new ConceptFilterDto { SearchTerm = "Expired" };
        var paged = await catalogService.GetConceptsAsync(filter);

        // Assert
        Assert.Single(paged.Items);
        var item = paged.Items[0];
        Assert.True(item.IsStale, "Concept with StaleAfter in the past must be flagged as stale (BR-OKF-002).");
    }

    [Fact]
    public async Task GetConceptsAsync_FutureStaleAfter_FlagsConceptAsNotStale()
    {
        // Arrange
        var (_, db, catalogService, _, _) = CreateTestContext();

        var bundle = new KnowledgeBundle
        {
            BundleCode = "merseta-active",
            DisplayName = "Active Bundle",
            FileSystemPath = "/bundles/merseta-active",
            OkfVersion = "0.2",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.KnowledgeBundles.Add(bundle);
        await db.SaveChangesAsync();

        var activeConcept = new ConceptDocument
        {
            BundleId = bundle.Id,
            ConceptId = "ACTIVE-001",
            ConceptType = "Policy",
            Title = "Current Policy",
            BodyMarkdown = "Policy currently in effect.",
            StaleAfter = DateTime.UtcNow.AddYears(1), // Fresh for 1 year
            DerivedTrustTier = TrustTier.HumanReviewed,
            GeneratedByActor = "human:manager",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.ConceptDocuments.Add(activeConcept);
        await db.SaveChangesAsync();

        // Act
        var filter = new ConceptFilterDto { SearchTerm = "Current Policy" };
        var paged = await catalogService.GetConceptsAsync(filter);

        // Assert
        Assert.Single(paged.Items);
        var item = paged.Items[0];
        Assert.False(item.IsStale, "Concept with StaleAfter in future must not be stale.");
    }

    #endregion

    #region 4. BR-OKF-003 & BR-OKF-004: T-SQL Attestation Engine Tests

    [Fact]
    public async Task ExecuteAndAttestAsync_MissingRequiredParameter_ThrowsArgumentException()
    {
        // Arrange
        var (_, db, _, attestationEngine, _) = CreateTestContext();

        var bundle = new KnowledgeBundle
        {
            BundleCode = "comp-bundle",
            DisplayName = "Computation Bundle",
            FileSystemPath = "/bundles/comp-bundle",
            OkfVersion = "0.2",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.KnowledgeBundles.Add(bundle);
        await db.SaveChangesAsync();

        var concept = new ConceptDocument
        {
            BundleId = bundle.Id,
            ConceptId = "COMP-001",
            ConceptType = "ComputationRule",
            Title = "Mandatory Rebate Engine",
            BodyMarkdown = "Calculates rebate.",
            GeneratedByActor = "human:analyst",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.ConceptDocuments.Add(concept);
        await db.SaveChangesAsync();

        var comp = new AttestedComputation
        {
            ConceptId = concept.Id,
            Runtime = "tsql",
            ComputationSql = "SELECT @LevyAmount * 0.20 AS Rebate;",
            ReceiptSchemaJson = "[\"execution_id\", \"result_digest\"]",
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.AttestedComputations.Add(comp);
        await db.SaveChangesAsync();

        db.ComputationParameters.Add(new ComputationParameter
        {
            ComputationId = comp.Id,
            ParameterName = "LevyAmount",
            ParameterType = "decimal",
            IsRequired = true,
            DefaultValue = null,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        });
        await db.SaveChangesAsync();

        // Act & Assert (empty parameters passed when LevyAmount is required)
        var emptyParams = new Dictionary<string, object?>();
        var ex = await Assert.ThrowsAsync<ArgumentException>(() =>
            attestationEngine.ExecuteAndAttestAsync(comp.Id, emptyParams, "tester"));

        Assert.Contains("LevyAmount", ex.Message);
    }

    [Fact]
    public async Task DryRunAttestationAsync_ValidParameters_ComputesDigitalSecuritySeal()
    {
        // Arrange
        var (_, db, _, attestationEngine, _) = CreateTestContext();

        var bundle = new KnowledgeBundle
        {
            BundleCode = "dry-run-bundle",
            DisplayName = "Dry Run Bundle",
            FileSystemPath = "/bundles/dry-run-bundle",
            OkfVersion = "0.2",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.KnowledgeBundles.Add(bundle);
        await db.SaveChangesAsync();

        var concept = new ConceptDocument
        {
            BundleId = bundle.Id,
            ConceptId = "DRY-001",
            ConceptType = "Formula",
            Title = "Dry Run Test Formula",
            BodyMarkdown = "Calculates test values.",
            GeneratedByActor = "human:analyst",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.ConceptDocuments.Add(concept);
        await db.SaveChangesAsync();

        var comp = new AttestedComputation
        {
            ConceptId = concept.Id,
            Runtime = "tsql",
            ComputationSql = "SELECT 100 * 0.20 AS Rebate;",
            ReceiptSchemaJson = "[\"execution_id\", \"result_digest\"]",
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.AttestedComputations.Add(comp);
        await db.SaveChangesAsync();

        // Act
        var result = await attestationEngine.DryRunAttestationAsync(comp.Id, new Dictionary<string, object?>());

        // Assert
        Assert.NotNull(result);
        Assert.True(result.Success);
        Assert.Equal("Pass", result.Verdict);
        Assert.False(string.IsNullOrWhiteSpace(result.ExecutedSqlDigest));
        Assert.False(string.IsNullOrWhiteSpace(result.VerificationReference));
        Assert.Equal(64, result.VerificationReference.Length);

        // Dry-run must NOT persist to ComputationExecutionAudit table
        var auditCount = await db.ComputationExecutionAudits.CountAsync();
        Assert.Equal(0, auditCount);
    }

    [Fact]
    public async Task ExecuteAndAttestAsync_PersistsAuditWithParametersAndReceipt()
    {
        // Arrange
        var (_, db, _, attestationEngine, _) = CreateTestContext();

        var bundle = new KnowledgeBundle
        {
            BundleCode = "audit-bundle",
            DisplayName = "Audit Bundle",
            FileSystemPath = "/bundles/audit-bundle",
            OkfVersion = "0.2",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.KnowledgeBundles.Add(bundle);
        await db.SaveChangesAsync();

        var concept = new ConceptDocument
        {
            BundleId = bundle.Id,
            ConceptId = "AUDIT-001",
            ConceptType = "StatutoryFormula",
            Title = "Audited Formula",
            BodyMarkdown = "Audited computation.",
            GeneratedByActor = "human:analyst",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.ConceptDocuments.Add(concept);
        await db.SaveChangesAsync();

        var comp = new AttestedComputation
        {
            ConceptId = concept.Id,
            Runtime = "tsql",
            ComputationSql = "SELECT @Amount AS GivenAmount;",
            ReceiptSchemaJson = "[\"execution_id\", \"rows_affected\", \"result_digest\"]",
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.AttestedComputations.Add(comp);
        await db.SaveChangesAsync();

        db.ComputationParameters.Add(new ComputationParameter
        {
            ComputationId = comp.Id,
            ParameterName = "Amount",
            ParameterType = "decimal",
            IsRequired = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        });
        await db.SaveChangesAsync();

        // Act
        var parameters = new Dictionary<string, object?> { { "Amount", 50000m } };
        var result = await attestationEngine.ExecuteAndAttestAsync(comp.Id, parameters, "human:reviewer_jane");

        // Assert
        Assert.NotNull(result);
        Assert.True(result.Success);
        Assert.Equal("Pass", result.Verdict);

        // Verify audit persistence
        var audits = await db.ComputationExecutionAudits.ToListAsync();
        Assert.Single(audits);
        var audit = audits[0];
        Assert.Equal(comp.Id, audit.ComputationId);
        Assert.Equal("human:reviewer_jane", audit.InvokedByActor);
        Assert.Contains("50000", audit.BoundParametersJson);
        Assert.Equal("Pass", audit.AttestationVerdict);
        Assert.False(string.IsNullOrWhiteSpace(audit.VerificationReference));
    }

    [Fact]
    public async Task ExecuteAndAttestAsync_StaleComputation_ThrowsInvalidOperationException()
    {
        // Arrange (BR-OKF-002: Temporal staleness boundary gates attestation execution)
        var (_, db, _, attestationEngine, _) = CreateTestContext();

        var bundle = new KnowledgeBundle
        {
            BundleCode = "stale-bundle",
            DisplayName = "Stale Bundle",
            FileSystemPath = "/bundles/stale-bundle",
            OkfVersion = "0.2",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.KnowledgeBundles.Add(bundle);
        await db.SaveChangesAsync();

        var concept = new ConceptDocument
        {
            BundleId = bundle.Id,
            ConceptId = "EXPIRED-001",
            ConceptType = "Formula",
            Title = "Expired Formula",
            BodyMarkdown = "Expired formula body.",
            StaleAfter = DateTime.UtcNow.AddDays(-5), // Already expired
            GeneratedByActor = "human:analyst",
            CreatedAt = DateTime.UtcNow.AddYears(-1),
            CreatedBy = "System"
        };
        db.ConceptDocuments.Add(concept);
        await db.SaveChangesAsync();

        var comp = new AttestedComputation
        {
            ConceptId = concept.Id,
            Runtime = "tsql",
            ComputationSql = "SELECT 1;",
            ReceiptSchemaJson = "[]",
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.AttestedComputations.Add(comp);
        await db.SaveChangesAsync();

        // Act & Assert
        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            attestationEngine.ExecuteAndAttestAsync(comp.Id, new Dictionary<string, object?>(), "agent:runner"));

        Assert.Contains("Freshness Violation", ex.Message, StringComparison.OrdinalIgnoreCase);
    }

    #endregion

    #region 5. BR-OKF-005: Dual Authorisation Control Governance Tests

    [Fact]
    public async Task RecordVerificationAsync_ProposingAuthorAttemptsSelfVerification_ThrowsInvalidOperationException()
    {
        // Arrange (Segregation of Duties / Proposer cannot review own work)
        var (_, db, catalogService, _, _) = CreateTestContext();

        var bundle = new KnowledgeBundle
        {
            BundleCode = "governance-bundle",
            DisplayName = "Governance Bundle",
            FileSystemPath = "/bundles/governance-bundle",
            OkfVersion = "0.2",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.KnowledgeBundles.Add(bundle);
        await db.SaveChangesAsync();

        var authorUsername = "clo_john_doe";
        var concept = new ConceptDocument
        {
            BundleId = bundle.Id,
            ConceptId = "GOV-001",
            ConceptType = "Policy",
            Title = "Workplace Approval Criteria",
            BodyMarkdown = "Criteria for workplace approvals.",
            GeneratedByActor = $"human:{authorUsername}",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = authorUsername
        };
        db.ConceptDocuments.Add(concept);
        await db.SaveChangesAsync();

        var command = new RecordVerificationCommand
        {
            ConceptId = concept.Id,
            ReviewerName = authorUsername, // Attempting self-review!
            ReviewerRole = "CLO",
            VerificationProcess = "Independent QA",
            Notes = "I verify my own submission."
        };

        // Act & Assert
        var ex = await Assert.ThrowsAsync<InvalidOperationException>(() =>
            catalogService.RecordVerificationAsync(concept.Id, command, authorUsername));

        Assert.Contains("Dual Authorisation", ex.Message);
    }

    [Fact]
    public async Task RecordVerificationAsync_IndependentReviewer_SucceedsAndPromotesTier()
    {
        // Arrange
        var (_, db, catalogService, _, _) = CreateTestContext();

        var bundle = new KnowledgeBundle
        {
            BundleCode = "dual-auth-bundle",
            DisplayName = "Dual Auth Bundle",
            FileSystemPath = "/bundles/dual-auth-bundle",
            OkfVersion = "0.2",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.KnowledgeBundles.Add(bundle);
        await db.SaveChangesAsync();

        var concept = new ConceptDocument
        {
            BundleId = bundle.Id,
            ConceptId = "GOV-002",
            ConceptType = "Standard",
            Title = "Independent Review Test",
            BodyMarkdown = "Concept pending human review.",
            DerivedTrustTier = TrustTier.Unverified,
            GeneratedByActor = "human:officer_alice",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "officer_alice"
        };
        db.ConceptDocuments.Add(concept);
        await db.SaveChangesAsync();

        var independentReviewer = "manager_bob";
        var command = new RecordVerificationCommand
        {
            ConceptId = concept.Id,
            ReviewerName = independentReviewer,
            ReviewerRole = "Senior Manager: Quality Assurance",
            VerificationProcess = "Statutory Compliance Audit 2026",
            Notes = "Verified against QCTO guidelines."
        };

        // Act
        var success = await catalogService.RecordVerificationAsync(concept.Id, command, independentReviewer);

        // Assert
        Assert.True(success);

        var updated = await catalogService.GetConceptByIdAsync(concept.Id);
        Assert.NotNull(updated);
        Assert.Equal(TrustTier.HumanReviewed, updated.DerivedTrustTier);
        Assert.Single(updated.Verifications);
        Assert.Equal($"human:{independentReviewer}", updated.Verifications[0].VerifiedByActor);
        Assert.Contains("Quality Assurance", updated.Verifications[0].Notes ?? string.Empty);
    }

    #endregion

    #region 6. CRUD & Bundle Archive Export Tests

    [Fact]
    public async Task CreateConceptAsync_WithTagsAndSourcesAndComputation_PersistsFullGraph()
    {
        // Arrange
        var (_, db, catalogService, _, _) = CreateTestContext();

        var bundle = new KnowledgeBundle
        {
            BundleCode = "crud-bundle",
            DisplayName = "CRUD Test Bundle",
            FileSystemPath = "/bundles/crud-bundle",
            OkfVersion = "0.2",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.KnowledgeBundles.Add(bundle);
        await db.SaveChangesAsync();

        var command = new CreateConceptCommand
        {
            BundleId = bundle.Id,
            ConceptId = "NEW-CONCEPT-01",
            ConceptType = "StandardOperatingProcedure",
            Title = "SDP Accreditation Procedure",
            SummaryDescription = "Step-by-step accreditation SOP.",
            BodyMarkdown = "## SOP Steps\n\n1. Initial Intake\n2. Site Visit\n3. Committee Review",
            GeneratedByActor = "human:officer_claire",
            Tags = new List<string> { "sdp", "accreditation", "quality-assurance" },
            Sources = new List<CreateConceptSourceItem>
            {
                new()
                {
                    SourceIdAlias = "QCTO-CRIT",
                    ResourceUri = "https://qcto.org.za/criteria-2025",
                    Title = "QCTO Accreditation Criteria"
                }
            },
            Computation = new CreateComputationItem
            {
                Runtime = "tsql",
                ComputationSql = "SELECT COUNT(*) FROM ProviderAccreditation WHERE Status == 'Approved';",
                Parameters = new List<CreateParameterItem>
                {
                    new()
                    {
                        ParameterName = "TargetYear",
                        ParameterType = "int",
                        IsRequired = true,
                        DefaultValue = "2026",
                        Description = "Year of audit"
                    }
                }
            }
        };

        // Act
        var created = await catalogService.CreateConceptAsync(command, "officer_claire");

        // Assert
        Assert.NotNull(created);
        Assert.True(created.Id > 0);
        Assert.Equal("NEW-CONCEPT-01", created.ConceptId);
        Assert.Equal(3, created.Tags.Count);
        Assert.Single(created.Sources);
        Assert.NotNull(created.Computation);
        Assert.Single(created.Computation.Parameters);
        Assert.Equal("TargetYear", created.Computation.Parameters[0].ParameterName);

        // Verify in database
        var dbConcept = await db.ConceptDocuments
            .Include(c => c.Tags)
            .Include(c => c.Sources)
            .Include(c => c.Computation)
            .ThenInclude(comp => comp!.Parameters)
            .FirstOrDefaultAsync(c => c.Id == created.Id);

        Assert.NotNull(dbConcept);
        Assert.Equal("NEW-CONCEPT-01", dbConcept.ConceptId);
        Assert.Equal(3, dbConcept.Tags.Count);
        Assert.Single(dbConcept.Sources);
        Assert.NotNull(dbConcept.Computation);
        Assert.Single(dbConcept.Computation.Parameters);
    }

    [Fact]
    public async Task DeleteConceptAsync_SoftDeletesConcept()
    {
        // Arrange
        var (_, db, catalogService, _, _) = CreateTestContext();

        var bundle = new KnowledgeBundle
        {
            BundleCode = "delete-bundle",
            DisplayName = "Delete Bundle",
            FileSystemPath = "/bundles/delete-bundle",
            OkfVersion = "0.2",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.KnowledgeBundles.Add(bundle);
        await db.SaveChangesAsync();

        var concept = new ConceptDocument
        {
            BundleId = bundle.Id,
            ConceptId = "TO-DELETE-01",
            ConceptType = "Draft",
            Title = "Draft to Delete",
            BodyMarkdown = "Temporary draft content.",
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.ConceptDocuments.Add(concept);
        await db.SaveChangesAsync();

        // Act
        var success = await catalogService.DeleteConceptAsync(concept.Id, "admin_user");

        // Assert
        Assert.True(success);

        var refreshed = await db.ConceptDocuments.AsNoTracking().FirstOrDefaultAsync(c => c.Id == concept.Id);
        Assert.NotNull(refreshed);
        Assert.False(refreshed.IsActive);
    }

    [Fact]
    public async Task ExportBundleArchiveAsync_GeneratesValidZipWithBundleYamlAndConceptFiles()
    {
        // Arrange
        var (_, db, catalogService, _, _) = CreateTestContext();

        var bundle = new KnowledgeBundle
        {
            BundleCode = "export-bundle-test",
            DisplayName = "Export Test Bundle",
            FileSystemPath = "/bundles/export-bundle-test",
            OkfVersion = "2.1.0",
            Description = "Bundle for export testing",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.KnowledgeBundles.Add(bundle);
        await db.SaveChangesAsync();

        var concept1 = new ConceptDocument
        {
            BundleId = bundle.Id,
            ConceptId = "EXP-CONCEPT-1",
            ConceptType = "Rule",
            Title = "Export Concept One",
            BodyMarkdown = "# Concept 1\nBody text one.",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        var concept2 = new ConceptDocument
        {
            BundleId = bundle.Id,
            ConceptId = "EXP-CONCEPT-2",
            ConceptType = "Policy",
            Title = "Export Concept Two",
            BodyMarkdown = "# Concept 2\nBody text two.",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = "System"
        };
        db.ConceptDocuments.AddRange(concept1, concept2);
        await db.SaveChangesAsync();

        // Act
        var zipBytes = await catalogService.ExportBundleArchiveAsync(bundle.Id);

        // Assert
        Assert.NotNull(zipBytes);
        Assert.NotEmpty(zipBytes);

        // Open as ZipArchive and verify files
        using var stream = new MemoryStream(zipBytes);
        using var archive = new ZipArchive(stream, ZipArchiveMode.Read);

        var bundleEntry = archive.GetEntry("bundle.yaml");
        Assert.NotNull(bundleEntry);

        using (var reader = new StreamReader(bundleEntry.Open()))
        {
            var content = await reader.ReadToEndAsync();
            Assert.Contains("export-bundle-test", content);
            Assert.Contains("2.1.0", content);
        }

        var concept1Entry = archive.GetEntry("EXP-CONCEPT-1.md") ?? archive.GetEntry("concepts/EXP-CONCEPT-1.md");
        Assert.NotNull(concept1Entry);

        var concept2Entry = archive.GetEntry("EXP-CONCEPT-2.md") ?? archive.GetEntry("concepts/EXP-CONCEPT-2.md");
        Assert.NotNull(concept2Entry);
    }

    #endregion
}
