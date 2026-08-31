using System.Text;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class EnterpriseDocumentTemplateService : IEnterpriseDocumentTemplateService
{
    private readonly INsdmsDbContextFactory _factory;
    private readonly AuditService _audit;
    private readonly IPdfDocumentService _pdfService;

    public EnterpriseDocumentTemplateService(
        INsdmsDbContextFactory factory, 
        AuditService audit,
        IPdfDocumentService pdfService)
    {
        _factory = factory;
        _audit = audit;
        _pdfService = pdfService;
    }

    #region Clause Management

    public async Task<List<DocumentClause>> GetClausesAsync(string? category = null)
    {
        using var db = await _factory.CreateDbContextAsync();
        var query = db.DocumentClauses.AsNoTracking().AsQueryable();
        if (!string.IsNullOrWhiteSpace(category) && category != "All")
        {
            query = query.Where(c => c.Category == category);
        }
        return await query.OrderBy(c => c.Category).ThenBy(c => c.ClauseCode).ToListAsync();
    }

    public async Task<DocumentClause?> GetClauseByIdAsync(int id)
    {
        using var db = await _factory.CreateDbContextAsync();
        return await db.DocumentClauses
            .Include(c => c.TemplateSections)
                .ThenInclude(s => s.DocumentTemplate)
            .FirstOrDefaultAsync(c => c.Id == id);
    }

    public async Task<DocumentClause> CreateClauseAsync(DocumentClause clause, string actor)
    {
        using var db = await _factory.CreateDbContextAsync();
        clause.CreatedAt = DateTime.UtcNow;
        clause.CreatedBy = actor;

        db.DocumentClauses.Add(clause);
        await db.SaveChangesAsync();

        await _audit.LogAsync("DocumentClause", clause.Id, "CreateClause", $"Created document clause {clause.ClauseCode}", actor, clause);
        return clause;
    }

    public async Task<DocumentClause> UpdateClauseAsync(DocumentClause clause, string actor)
    {
        using var db = await _factory.CreateDbContextAsync();
        var existing = await db.DocumentClauses.FindAsync(clause.Id)
            ?? throw new InvalidOperationException($"Document clause #{clause.Id} not found.");

        existing.ClauseTitle = clause.ClauseTitle;
        existing.Category = clause.Category;
        existing.ClauseContent = clause.ClauseContent;
        existing.IsMandatory = clause.IsMandatory;
        existing.IsActive = clause.IsActive;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = actor;

        await db.SaveChangesAsync();
        await _audit.LogAsync("DocumentClause", existing.Id, "UpdateClause", $"Updated document clause {existing.ClauseCode}", actor, existing);
        return existing;
    }

    public async Task<bool> DeleteClauseAsync(int id, string actor)
    {
        using var db = await _factory.CreateDbContextAsync();
        var clause = await db.DocumentClauses
            .Include(c => c.TemplateSections)
            .FirstOrDefaultAsync(c => c.Id == id);

        if (clause == null) return false;

        if (clause.TemplateSections.Any())
        {
            // Soft-deactivate if referenced by active templates
            clause.IsActive = false;
            clause.ModifiedAt = DateTime.UtcNow;
            clause.ModifiedBy = actor;
            await db.SaveChangesAsync();
            await _audit.LogAsync("DocumentClause", clause.Id, "DeactivateClause", $"Soft-deactivated referenced document clause {clause.ClauseCode}", actor, clause);
            return true;
        }

        db.DocumentClauses.Remove(clause);
        await db.SaveChangesAsync();
        await _audit.LogAsync("DocumentClause", id, "DeleteClause", $"Deleted document clause {clause.ClauseCode}", actor, clause);
        return true;
    }

    #endregion

    #region Template Management

    public async Task<List<DocumentTemplate>> GetTemplatesAsync(string? category = null, int? financialYear = null)
    {
        using var db = await _factory.CreateDbContextAsync();
        var query = db.DocumentTemplates
            .Include(t => t.Sections)
            .AsNoTracking()
            .AsQueryable();

        if (!string.IsNullOrWhiteSpace(category) && category != "All")
        {
            query = query.Where(t => t.DocumentCategory == category);
        }

        if (financialYear.HasValue && financialYear.Value > 0)
        {
            query = query.Where(t => t.FinancialYear == financialYear.Value);
        }

        return await query.OrderByDescending(t => t.FinancialYear).ThenBy(t => t.TemplateCode).ToListAsync();
    }

    public async Task<DocumentTemplate?> GetTemplateByIdAsync(int id)
    {
        using var db = await _factory.CreateDbContextAsync();
        return await db.DocumentTemplates
            .Include(t => t.Sections.OrderBy(s => s.SequenceOrder))
                .ThenInclude(s => s.DocumentClause)
            .FirstOrDefaultAsync(t => t.Id == id);
    }

    public async Task<DocumentTemplate> CreateTemplateAsync(DocumentTemplate template, string actor)
    {
        using var db = await _factory.CreateDbContextAsync();
        template.CreatedAt = DateTime.UtcNow;
        template.CreatedBy = actor;
        template.ApprovalStatus = "Draft";

        db.DocumentTemplates.Add(template);
        await db.SaveChangesAsync();

        await _audit.LogAsync("DocumentTemplate", template.Id, "CreateTemplate", $"Created document template {template.TemplateCode}", actor, template);
        return template;
    }

    public async Task<DocumentTemplate> UpdateTemplateAsync(DocumentTemplate template, string actor)
    {
        using var db = await _factory.CreateDbContextAsync();
        var existing = await db.DocumentTemplates.FindAsync(template.Id)
            ?? throw new InvalidOperationException($"Document template #{template.Id} not found.");

        existing.TemplateTitle = template.TemplateTitle;
        existing.DocumentCategory = template.DocumentCategory;
        existing.DocumentTypeCode = template.DocumentTypeCode;
        existing.FinancialYear = template.FinancialYear;
        existing.TargetEntityType = template.TargetEntityType;
        existing.VersionNumber = template.VersionNumber;
        existing.EffectiveFrom = template.EffectiveFrom;
        existing.EffectiveTo = template.EffectiveTo;
        existing.IsActive = template.IsActive;
        existing.HeaderBannerUrl = template.HeaderBannerUrl;
        existing.FooterDisclaimerText = template.FooterDisclaimerText;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = actor;

        await db.SaveChangesAsync();
        await _audit.LogAsync("DocumentTemplate", existing.Id, "UpdateTemplate", $"Updated document template {existing.TemplateCode}", actor, existing);
        return existing;
    }

    public async Task<DocumentTemplate> ApproveTemplateAsync(int templateId, string actor)
    {
        using var db = await _factory.CreateDbContextAsync();
        var template = await db.DocumentTemplates.FindAsync(templateId)
            ?? throw new InvalidOperationException($"Document template #{templateId} not found.");

        template.ApprovalStatus = "Approved";
        template.ApprovedBy = actor;
        template.ApprovedAt = DateTime.UtcNow;
        template.IsActive = true;
        template.ModifiedAt = DateTime.UtcNow;
        template.ModifiedBy = actor;

        await db.SaveChangesAsync();
        await _audit.LogAsync("DocumentTemplate", template.Id, "ApproveTemplate", $"Approved document template {template.TemplateCode} v{template.VersionNumber}", actor, template);
        return template;
    }

    public async Task<bool> DeleteTemplateAsync(int id, string actor)
    {
        using var db = await _factory.CreateDbContextAsync();
        var template = await db.DocumentTemplates
            .Include(t => t.Snapshots)
            .FirstOrDefaultAsync(t => t.Id == id);

        if (template == null) return false;

        if (template.Snapshots.Any())
        {
            // If historical snapshots exist, archive rather than hard delete
            template.IsActive = false;
            template.ApprovalStatus = "Archived";
            template.ModifiedAt = DateTime.UtcNow;
            template.ModifiedBy = actor;
            await db.SaveChangesAsync();
            await _audit.LogAsync("DocumentTemplate", id, "ArchiveTemplate", $"Archived document template {template.TemplateCode}", actor, template);
            return true;
        }

        db.DocumentTemplates.Remove(template);
        await db.SaveChangesAsync();
        await _audit.LogAsync("DocumentTemplate", id, "DeleteTemplate", $"Deleted document template {template.TemplateCode}", actor, template);
        return true;
    }

    #endregion

    #region Section Composition

    public async Task<DocumentTemplate> AddSectionAsync(int templateId, int clauseId, string sectionNumber, string sectionTitle, int sequenceOrder, bool isMandatory, string actor)
    {
        using var db = await _factory.CreateDbContextAsync();
        var template = await db.DocumentTemplates
            .Include(t => t.Sections)
            .FirstOrDefaultAsync(t => t.Id == templateId)
            ?? throw new InvalidOperationException($"Document template #{templateId} not found.");

        var clause = await db.DocumentClauses.FindAsync(clauseId)
            ?? throw new InvalidOperationException($"Document clause #{clauseId} not found.");

        var section = new DocumentTemplateSection
        {
            DocumentTemplateId = templateId,
            DocumentClauseId = clauseId,
            SectionNumber = sectionNumber,
            SectionTitle = string.IsNullOrWhiteSpace(sectionTitle) ? clause.ClauseTitle : sectionTitle,
            SequenceOrder = sequenceOrder,
            IsMandatory = isMandatory,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = actor
        };

        db.DocumentTemplateSections.Add(section);
        await db.SaveChangesAsync();

        await _audit.LogAsync("DocumentTemplate", templateId, "AddSection", actor, section);
        return (await GetTemplateByIdAsync(templateId))!;
    }

    public async Task<DocumentTemplate> RemoveSectionAsync(int templateId, int sectionId, string actor)
    {
        using var db = await _factory.CreateDbContextAsync();
        var section = await db.DocumentTemplateSections.FirstOrDefaultAsync(s => s.Id == sectionId && s.DocumentTemplateId == templateId);
        if (section != null)
        {
            db.DocumentTemplateSections.Remove(section);
            await db.SaveChangesAsync();
            await _audit.LogAsync("DocumentTemplate", templateId, "RemoveSection", actor, section);
        }
        return (await GetTemplateByIdAsync(templateId))!;
    }

    public async Task<DocumentTemplate> ReorderSectionsAsync(int templateId, List<int> orderedSectionIds, string actor)
    {
        using var db = await _factory.CreateDbContextAsync();
        var sections = await db.DocumentTemplateSections
            .Where(s => s.DocumentTemplateId == templateId)
            .ToListAsync();

        for (int i = 0; i < orderedSectionIds.Count; i++)
        {
            var sec = sections.FirstOrDefault(s => s.Id == orderedSectionIds[i]);
            if (sec != null)
            {
                sec.SequenceOrder = (i + 1) * 10;
                sec.ModifiedAt = DateTime.UtcNow;
                sec.ModifiedBy = actor;
            }
        }

        await db.SaveChangesAsync();
        await _audit.LogAsync("DocumentTemplate", templateId, "ReorderSections", actor, orderedSectionIds);
        return (await GetTemplateByIdAsync(templateId))!;
    }

    #endregion

    #region Resolution & Assembly

    public async Task<DocumentTemplate?> ResolveTemplateAsync(string documentTypeCode, int financialYear, string targetEntityType = "All")
    {
        using var db = await _factory.CreateDbContextAsync();

        // 1. Exact match by DocumentTypeCode + FinancialYear + TargetEntityType
        var template = await db.DocumentTemplates
            .Include(t => t.Sections.OrderBy(s => s.SequenceOrder))
                .ThenInclude(s => s.DocumentClause)
            .Where(t => t.DocumentTypeCode == documentTypeCode 
                     && t.FinancialYear == financialYear 
                     && (t.TargetEntityType == targetEntityType || t.TargetEntityType == "All")
                     && t.IsActive 
                     && t.ApprovalStatus == "Approved")
            .OrderByDescending(t => t.VersionNumber)
            .FirstOrDefaultAsync();

        if (template != null) return template;

        // 2. Fallback: match by DocumentTypeCode regardless of year (latest approved version)
        template = await db.DocumentTemplates
            .Include(t => t.Sections.OrderBy(s => s.SequenceOrder))
                .ThenInclude(s => s.DocumentClause)
            .Where(t => t.DocumentTypeCode == documentTypeCode 
                     && t.IsActive 
                     && t.ApprovalStatus == "Approved")
            .OrderByDescending(t => t.FinancialYear)
            .ThenByDescending(t => t.VersionNumber)
            .FirstOrDefaultAsync();

        return template;
    }

    public async Task<string> AssembleDocumentTextAsync(int templateId, Dictionary<string, string> tokens)
    {
        using var db = await _factory.CreateDbContextAsync();
        var template = await db.DocumentTemplates
            .Include(t => t.Sections.OrderBy(s => s.SequenceOrder))
                .ThenInclude(s => s.DocumentClause)
            .FirstOrDefaultAsync(t => t.Id == templateId)
            ?? throw new InvalidOperationException($"Document template #{templateId} not found.");

        return BuildDocument(template, tokens);
    }

    public async Task<string> AssembleTemplatePreviewAsync(int templateId, Dictionary<string, string>? sampleTokens = null)
    {
        using var db = await _factory.CreateDbContextAsync();
        var template = await db.DocumentTemplates
            .Include(t => t.Sections.OrderBy(s => s.SequenceOrder))
                .ThenInclude(s => s.DocumentClause)
            .FirstOrDefaultAsync(t => t.Id == templateId)
            ?? throw new InvalidOperationException($"Document template #{templateId} not found.");

        var defaults = new Dictionary<string, string>
        {
            ["RecipientName"] = "Apex Engineering Works (Pty) Ltd",
            ["RecipientIdentifier"] = "L998877665",
            ["DocumentNumber"] = $"DOC-{template.FinancialYear}-SAMPLE-001",
            ["IssuedDate"] = DateTime.UtcNow.ToString("dd MMMM yyyy"),
            ["FinancialYear"] = template.FinancialYear.ToString(),
            ["TradeTitle"] = "Fitter and Turner (OFO 653306)",
            ["LearnerFullName"] = "Sipho Khumalo",
            ["LearnerIdNumber"] = "9501015082084",
            ["CertificateNumber"] = "TT-2026-00458",
            ["AccreditationNumber"] = "17-QA/ACC/0892/26",
            ["RebateAmount"] = "R 245,000.00",
            ["ClawbackAmount"] = "R 18,500.00",
            ["SignatoryName"] = "Disetlo Molapo",
            ["SignatoryTitle"] = "Chief Executive Officer / ETQA Senior Manager"
        };

        if (sampleTokens != null)
        {
            foreach (var kvp in sampleTokens)
            {
                defaults[kvp.Key] = kvp.Value;
            }
        }

        return BuildDocument(template, defaults);
    }

    public async Task<byte[]> GenerateSimulatedPdfAsync(int templateId, Dictionary<string, string>? sampleTokens = null, bool includeWatermark = true)
    {
        using var db = await _factory.CreateDbContextAsync();
        var template = await db.DocumentTemplates
            .Include(t => t.Sections.OrderBy(s => s.SequenceOrder))
                .ThenInclude(s => s.DocumentClause)
            .FirstOrDefaultAsync(t => t.Id == templateId)
            ?? throw new InvalidOperationException($"Document template #{templateId} not found.");

        var defaultProfiles = GetDefaultScenarioTokenProfiles();
        var tokens = new Dictionary<string, string>(defaultProfiles["LevyEmployer"]);

        if (sampleTokens != null)
        {
            foreach (var kvp in sampleTokens)
            {
                tokens[kvp.Key] = kvp.Value;
            }
        }

        return await _pdfService.GenerateSimulatedDocumentTemplatePdfAsync(template, tokens, includeWatermark);
    }

    public Dictionary<string, Dictionary<string, string>> GetDefaultScenarioTokenProfiles()
    {
        return new Dictionary<string, Dictionary<string, string>>
        {
            ["LevyEmployer"] = new()
            {
                ["ScenarioLabel"] = "Levy-Paying Corporate Employer",
                ["RecipientName"] = "Apex Engineering Works (Pty) Ltd",
                ["RecipientIdentifier"] = "L998877665",
                ["DocumentNumber"] = "DOC-2026-SDL-0849",
                ["IssuedDate"] = DateTime.UtcNow.ToString("dd MMMM yyyy"),
                ["FinancialYear"] = "2026",
                ["TradeTitle"] = "Mechanical Fitter (OFO 653303)",
                ["LearnerFullName"] = "Sipho Khumalo",
                ["LearnerIdNumber"] = "9501015082084",
                ["CertificateNumber"] = "TT-2026-00458",
                ["AccreditationNumber"] = "17-QA/ACC/0892/26",
                ["RebateAmount"] = "R 345,000.00",
                ["ClawbackAmount"] = "R 0.00",
                ["SignatoryName"] = "Disetlo Molapo",
                ["SignatoryTitle"] = "Chief Executive Officer / ETQA Senior Manager"
            },
            ["NonLevySme"] = new()
            {
                ["ScenarioLabel"] = "Non-Levy Paying SME / NGO",
                ["RecipientName"] = "Bambanani Skills Development Trust",
                ["RecipientIdentifier"] = "NGO-2026-081",
                ["DocumentNumber"] = "DOC-2026-NGO-0112",
                ["IssuedDate"] = DateTime.UtcNow.ToString("dd MMMM yyyy"),
                ["FinancialYear"] = "2026",
                ["TradeTitle"] = "Welder (OFO 651202)",
                ["LearnerFullName"] = "Nokuthula Dlamini",
                ["LearnerIdNumber"] = "9803150249081",
                ["CertificateNumber"] = "TT-2026-00892",
                ["AccreditationNumber"] = "17-QA/ACC/0944/26",
                ["RebateAmount"] = "R 180,000.00",
                ["ClawbackAmount"] = "R 0.00",
                ["SignatoryName"] = "Disetlo Molapo",
                ["SignatoryTitle"] = "Chief Executive Officer / ETQA Senior Manager"
            },
            ["ArtisanCandidate"] = new()
            {
                ["ScenarioLabel"] = "Artisan Trade Test Candidate",
                ["RecipientName"] = "Tebogo Mokoena",
                ["RecipientIdentifier"] = "9207185089087",
                ["DocumentNumber"] = "DOC-2026-CERT-9041",
                ["IssuedDate"] = DateTime.UtcNow.ToString("dd MMMM yyyy"),
                ["FinancialYear"] = "2026",
                ["TradeTitle"] = "Automotive Motor Mechanic (OFO 653101)",
                ["LearnerFullName"] = "Tebogo Mokoena",
                ["LearnerIdNumber"] = "9207185089087",
                ["CertificateNumber"] = "TT-2026-01258",
                ["AccreditationNumber"] = "17-QA/ACC/0892/26",
                ["RebateAmount"] = "R 0.00",
                ["ClawbackAmount"] = "R 0.00",
                ["SignatoryName"] = "Disetlo Molapo",
                ["SignatoryTitle"] = "Chief Executive Officer / ETQA Senior Manager"
            },
            ["AccreditedSdp"] = new()
            {
                ["ScenarioLabel"] = "Accredited Skills Development Provider (SDP)",
                ["RecipientName"] = "Gauteng Advanced Technical Academy",
                ["RecipientIdentifier"] = "SDP-2026-0055",
                ["DocumentNumber"] = "DOC-2026-SDP-0441",
                ["IssuedDate"] = DateTime.UtcNow.ToString("dd MMMM yyyy"),
                ["FinancialYear"] = "2026",
                ["TradeTitle"] = "Electrician (OFO 671101)",
                ["LearnerFullName"] = "N/A - Provider Level",
                ["LearnerIdNumber"] = "N/A",
                ["CertificateNumber"] = "ACC-2026-092",
                ["AccreditationNumber"] = "17-QA/ACC/0892/26",
                ["RebateAmount"] = "R 0.00",
                ["ClawbackAmount"] = "R 0.00",
                ["SignatoryName"] = "Disetlo Molapo",
                ["SignatoryTitle"] = "Chief Executive Officer / ETQA Senior Manager"
            }
        };
    }

    private static string BuildDocument(DocumentTemplate template, Dictionary<string, string> tokens)
    {
        var sb = new StringBuilder();
        sb.AppendLine($"# {template.TemplateTitle}");
        sb.AppendLine($"**Template Reference:** `{template.TemplateCode}` | **Version:** `{template.VersionNumber}` | **Year:** {template.FinancialYear}");
        sb.AppendLine("---");
        sb.AppendLine();

        foreach (var sec in template.Sections.OrderBy(s => s.SequenceOrder))
        {
            var clause = sec.DocumentClause;
            if (clause == null || !clause.IsActive) continue;

            if (!string.IsNullOrWhiteSpace(sec.SectionNumber) || !string.IsNullOrWhiteSpace(sec.SectionTitle))
            {
                sb.AppendLine($"### {sec.SectionNumber} {sec.SectionTitle}".Trim());
                sb.AppendLine();
            }

            string content = clause.ClauseContent;
            foreach (var kvp in tokens)
            {
                content = content.Replace($"{{{{{kvp.Key}}}}}", kvp.Value);
            }

            sb.AppendLine(content);
            sb.AppendLine();
        }

        if (!string.IsNullOrWhiteSpace(template.FooterDisclaimerText))
        {
            sb.AppendLine("---");
            sb.AppendLine($"*<small>{template.FooterDisclaimerText}</small>*");
        }

        return sb.ToString();
    }

    #endregion
}
