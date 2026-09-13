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
    private readonly IDocumentPlaceholderRegistry _placeholderRegistry;

    public EnterpriseDocumentTemplateService(
        INsdmsDbContextFactory factory, 
        AuditService audit,
        IPdfDocumentService pdfService,
        IDocumentPlaceholderRegistry? placeholderRegistry = null)
    {
        _factory = factory;
        _audit = audit;
        _pdfService = pdfService;
        _placeholderRegistry = placeholderRegistry ?? new DocumentPlaceholderRegistry();
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

    #region Template Management & Versioning

    public async Task<List<DocumentTemplate>> GetTemplatesAsync(string? category = null, int? financialYear = null)
    {
        using var db = await _factory.CreateDbContextAsync();
        var query = db.DocumentTemplates
            .Include(t => t.Sections)
            .Include(t => t.ParentTemplate)
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

        return await query.OrderByDescending(t => t.FinancialYear).ThenBy(t => t.TemplateCode).ThenByDescending(t => t.VersionNumber).ToListAsync();
    }

    public async Task<DocumentTemplate?> GetTemplateByIdAsync(int id)
    {
        using var db = await _factory.CreateDbContextAsync();
        return await db.DocumentTemplates
            .Include(t => t.ParentTemplate)
            .Include(t => t.ChildVersions)
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
        template.IsActive = false; // New versions start as inactive drafts

        db.DocumentTemplates.Add(template);
        await db.SaveChangesAsync();

        await _audit.LogAsync("DocumentTemplate", template.Id, "CreateTemplate", $"Created document template {template.TemplateCode} v{template.VersionNumber}", actor, template);
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
        existing.TemplateBodyHtml = template.TemplateBodyHtml;
        existing.VersionNotes = template.VersionNotes;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = actor;

        await db.SaveChangesAsync();
        await _audit.LogAsync("DocumentTemplate", existing.Id, "UpdateTemplate", $"Updated document template {existing.TemplateCode} v{existing.VersionNumber}", actor, existing);
        return existing;
    }

    public async Task<DocumentTemplate> ApproveTemplateAsync(int templateId, string actor)
    {
        return await ApproveAndActivateTemplateAsync(templateId, actor);
    }

    /// <summary>
    /// Enforces the Single Active Version Invariant:
    /// In an atomic transaction, activates the target template and supersedes all other active versions
    /// in the same template family.
    /// </summary>
    public async Task<DocumentTemplate> ApproveAndActivateTemplateAsync(int templateId, string actor)
    {
        using var db = await _factory.CreateDbContextAsync();
        Microsoft.EntityFrameworkCore.Storage.IDbContextTransaction? tx = null;
        if (db.Database.IsRelational())
        {
            tx = await db.Database.BeginTransactionAsync();
        }

        try
        {
            var template = await db.DocumentTemplates.FindAsync(templateId)
                ?? throw new InvalidOperationException($"Document template #{templateId} not found.");

            // 1. Supersede all other currently active templates in the same family (by TemplateCode)
            var activePeers = await db.DocumentTemplates
                .Where(t => t.TemplateCode == template.TemplateCode && t.Id != templateId && t.IsActive)
                .ToListAsync();

            foreach (var peer in activePeers)
            {
                peer.IsActive = false;
                peer.ApprovalStatus = "Superseded";
                peer.EffectiveTo = DateTime.UtcNow;
                peer.ModifiedAt = DateTime.UtcNow;
                peer.ModifiedBy = actor;
            }

            // 2. Activate target template
            template.ApprovalStatus = "Approved";
            template.ApprovedBy = actor;
            template.ApprovedAt = DateTime.UtcNow;
            template.IsActive = true;
            template.ModifiedAt = DateTime.UtcNow;
            template.ModifiedBy = actor;

            await db.SaveChangesAsync();
            if (tx != null)
            {
                await tx.CommitAsync();
            }

            await _audit.LogAsync("DocumentTemplate", template.Id, "ApproveAndActivateTemplate", 
                $"Approved and activated template {template.TemplateCode} v{template.VersionNumber}; superseded {activePeers.Count} predecessor active versions.", 
                actor, template);

            return template;
        }
        catch
        {
            if (tx != null)
            {
                await tx.RollbackAsync();
            }
            throw;
        }
        finally
        {
            tx?.Dispose();
        }
    }

    /// <summary>
    /// Creates a new draft revision derived from an existing template.
    /// Clones metadata, sections, and HTML body with a new version number.
    /// </summary>
    public async Task<DocumentTemplate> CreateNewVersionAsync(int sourceTemplateId, string newVersionNumber, string versionNotes, string actor)
    {
        using var db = await _factory.CreateDbContextAsync();
        var source = await db.DocumentTemplates
            .Include(t => t.Sections)
            .FirstOrDefaultAsync(t => t.Id == sourceTemplateId)
            ?? throw new InvalidOperationException($"Source template #{sourceTemplateId} not found.");

        // Validate uniqueness of version string for this template code
        bool versionExists = await db.DocumentTemplates.AnyAsync(t => t.TemplateCode == source.TemplateCode && t.VersionNumber == newVersionNumber);
        if (versionExists)
        {
            throw new InvalidOperationException($"Version '{newVersionNumber}' already exists for template '{source.TemplateCode}'.");
        }

        var revision = new DocumentTemplate
        {
            TemplateCode = source.TemplateCode,
            TemplateTitle = source.TemplateTitle,
            DocumentCategory = source.DocumentCategory,
            DocumentTypeCode = source.DocumentTypeCode,
            FinancialYear = source.FinancialYear,
            TargetEntityType = source.TargetEntityType,
            VersionNumber = newVersionNumber,
            ApprovalStatus = "Draft",
            IsActive = false,
            EffectiveFrom = DateTime.UtcNow,
            HeaderBannerUrl = source.HeaderBannerUrl,
            FooterDisclaimerText = source.FooterDisclaimerText,
            TemplateBodyHtml = source.TemplateBodyHtml,
            ParentTemplateId = source.Id,
            VersionNotes = versionNotes,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = actor
        };

        db.DocumentTemplates.Add(revision);
        await db.SaveChangesAsync();

        // Clone sections
        if (source.Sections.Any())
        {
            foreach (var sec in source.Sections.OrderBy(s => s.SequenceOrder))
            {
                db.DocumentTemplateSections.Add(new DocumentTemplateSection
                {
                    DocumentTemplateId = revision.Id,
                    DocumentClauseId = sec.DocumentClauseId,
                    SectionNumber = sec.SectionNumber,
                    SectionTitle = sec.SectionTitle,
                    SequenceOrder = sec.SequenceOrder,
                    IsMandatory = sec.IsMandatory,
                    ConditionRuleJson = sec.ConditionRuleJson,
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = actor
                });
            }
            await db.SaveChangesAsync();
        }

        await _audit.LogAsync("DocumentTemplate", revision.Id, "CreateNewVersion", 
            $"Created draft revision {revision.VersionNumber} of {revision.TemplateCode} derived from #{source.Id}", 
            actor, revision);

        return revision;
    }

    public async Task<List<DocumentTemplate>> GetTemplateVersionHistoryAsync(string templateCode)
    {
        using var db = await _factory.CreateDbContextAsync();
        return await db.DocumentTemplates
            .Include(t => t.ParentTemplate)
            .Include(t => t.Snapshots)
            .Where(t => t.TemplateCode == templateCode)
            .OrderByDescending(t => t.CreatedAt)
            .ToListAsync();
    }

    public async Task<int> GetDocumentIssuanceCountForTemplateAsync(int templateId)
    {
        using var db = await _factory.CreateDbContextAsync();
        return await db.DocumentSnapshots.CountAsync(s => s.DocumentTemplateId == templateId);
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
            await _audit.LogAsync("DocumentTemplate", id, "ArchiveTemplate", $"Archived document template {template.TemplateCode} v{template.VersionNumber}", actor, template);
            return true;
        }

        db.DocumentTemplates.Remove(template);
        await db.SaveChangesAsync();
        await _audit.LogAsync("DocumentTemplate", id, "DeleteTemplate", $"Deleted document template {template.TemplateCode} v{template.VersionNumber}", actor, template);
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
            SequenceOrder = sequenceOrder > 0 ? sequenceOrder : template.Sections.Count + 1,
            IsMandatory = isMandatory,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = actor
        };

        db.DocumentTemplateSections.Add(section);
        await db.SaveChangesAsync();

        await _audit.LogAsync("DocumentTemplate", templateId, "AddSection", $"Added clause {clause.ClauseCode} to template {template.TemplateCode}", actor, section);
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
            await _audit.LogAsync("DocumentTemplate", templateId, "RemoveSection", $"Removed section #{sectionId} from template #{templateId}", actor, section);
        }
        return (await GetTemplateByIdAsync(templateId))!;
    }

    public async Task<DocumentTemplate> ReorderSectionsAsync(int templateId, List<int> orderedSectionIds, string actor)
    {
        using var db = await _factory.CreateDbContextAsync();
        var sections = await db.DocumentTemplateSections.Where(s => s.DocumentTemplateId == templateId).ToListAsync();

        for (int i = 0; i < orderedSectionIds.Count; i++)
        {
            var sec = sections.FirstOrDefault(s => s.Id == orderedSectionIds[i]);
            if (sec != null)
            {
                sec.SequenceOrder = i + 1;
                sec.ModifiedAt = DateTime.UtcNow;
                sec.ModifiedBy = actor;
            }
        }

        await db.SaveChangesAsync();
        await _audit.LogAsync("DocumentTemplate", templateId, "ReorderSections", $"Reordered {orderedSectionIds.Count} sections in template #{templateId}", actor);
        return (await GetTemplateByIdAsync(templateId))!;
    }

    #endregion

    #region Resolution & Assembly

    public async Task<DocumentTemplate?> ResolveTemplateAsync(string documentTypeCode, int financialYear, string targetEntityType = "All")
    {
        using var db = await _factory.CreateDbContextAsync();

        // 1. Exact match by DocumentTypeCode + FinancialYear + TargetEntityType where IsActive = true and ApprovalStatus = 'Approved'
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

        if (!string.IsNullOrWhiteSpace(template.TemplateBodyHtml))
        {
            return _placeholderRegistry.Interpolate(template.TemplateBodyHtml, tokens);
        }

        return BuildDocument(template, tokens);
    }

    public async Task<string> AssembleDocumentHtmlAsync(int templateId, Dictionary<string, string>? tokens = null)
    {
        using var db = await _factory.CreateDbContextAsync();
        var template = await db.DocumentTemplates
            .Include(t => t.Sections.OrderBy(s => s.SequenceOrder))
                .ThenInclude(s => s.DocumentClause)
            .FirstOrDefaultAsync(t => t.Id == templateId)
            ?? throw new InvalidOperationException($"Document template #{templateId} not found.");

        var defaults = _placeholderRegistry.GetSampleTokenValues(template.DocumentCategory);
        if (tokens != null)
        {
            foreach (var kvp in tokens)
            {
                defaults[kvp.Key] = kvp.Value;
            }
        }

        if (!string.IsNullOrWhiteSpace(template.TemplateBodyHtml))
        {
            return _placeholderRegistry.Interpolate(template.TemplateBodyHtml, defaults);
        }

        // Fallback: wrap section-built text in clean HTML container
        var text = BuildDocument(template, defaults);
        return $"<pre style=\"white-space: pre-wrap; font-family: inherit; line-height: 1.6;\">{System.Net.WebUtility.HtmlEncode(text)}</pre>";
    }

    public async Task<string> AssembleTemplatePreviewAsync(int templateId, Dictionary<string, string>? sampleTokens = null)
    {
        return await AssembleDocumentHtmlAsync(templateId, sampleTokens);
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
        var currentYear = DateTime.UtcNow.Year.ToString();
        var shortYear = currentYear.Length >= 2 ? currentYear.Substring(currentYear.Length - 2) : currentYear;

        return new Dictionary<string, Dictionary<string, string>>
        {
            ["LevyEmployer"] = new()
            {
                ["ScenarioLabel"] = "Levy-Paying Corporate Employer",
                ["Employer.Name"] = "Apex Engineering Works (Pty) Ltd",
                ["RecipientName"] = "Apex Engineering Works (Pty) Ltd",
                ["Employer.SdlNumber"] = "L998877665",
                ["RecipientIdentifier"] = "L998877665",
                ["Employer.TradingName"] = "Apex Industrial Solutions",
                ["Employer.PhysicalAddress"] = "14 Power Street, Germiston, Gauteng",
                ["Employer.PostalCode"] = "1401",
                ["ContactPerson.FullName"] = "Johan van der Merwe",
                ["ContactPerson.Email"] = "johan.vdm@apexeng.co.za",
                ["ContactPerson.Phone"] = "+27 11 824 5000",
                ["DocumentNumber"] = $"DOC-{currentYear}-SDL-0849",
                ["Verification.DocumentNumber"] = $"DOC-{currentYear}-SDL-0849",
                ["IssuedDate"] = DateTime.UtcNow.ToString("dd MMMM yyyy"),
                ["Verification.IssuedDate"] = DateTime.UtcNow.ToString("dd MMMM yyyy"),
                ["FinancialYear"] = currentYear,
                ["Wsp.SchemeYear"] = currentYear,
                ["Wsp.SubmissionDate"] = "28 April " + currentYear,
                ["Wsp.ApprovalDate"] = DateTime.UtcNow.ToString("dd MMMM yyyy"),
                ["Wsp.RebateAmount"] = "R 345,000.00",
                ["RebateAmount"] = "R 345,000.00",
                ["Wsp.LevyAmount"] = "R 2,156,250.00",
                ["Wsp.ExtensionDueDate"] = "31 May " + currentYear,
                ["TradeTitle"] = "Mechanical Fitter (OFO 653303)",
                ["Trade.Title"] = "Mechanical Fitter",
                ["Trade.OfoCode"] = "653303",
                ["LearnerFullName"] = "Sipho Khumalo",
                ["Learner.FullName"] = "Sipho Khumalo",
                ["LearnerIdNumber"] = "9501015082084",
                ["Learner.RsaIdNumber"] = "9501015082084",
                ["CertificateNumber"] = $"TT-{currentYear}-00458",
                ["TradeTest.SerialNumber"] = $"TT-{currentYear}-00458",
                ["TradeTest.TestCenterName"] = "merSETA Advanced Manufacturing Training Centre",
                ["TradeTest.AssessmentDate"] = DateTime.UtcNow.ToString("dd MMMM yyyy"),
                ["TradeTest.Outcome"] = "COMPETENT AS AN ARTISAN",
                ["AccreditationNumber"] = $"17-QA/ACC/0892/{shortYear}",
                ["Provider.AccreditationNumber"] = $"17-QA/ACC/0892/{shortYear}",
                ["Provider.Name"] = "Gauteng Advanced Technical Academy",
                ["Accreditation.StartDate"] = "01 January " + currentYear,
                ["Accreditation.ExpiryDate"] = "31 December " + (DateTime.UtcNow.Year + 3),
                ["Accreditation.ScopeOfApproval"] = "National Certificate: Mechanical Engineering (NQF 4)",
                ["Moa.ContractNumber"] = $"DG-{currentYear}-MOA-0089",
                ["Moa.ApprovedAmount"] = "R 1,250,000.00",
                ["Moa.ProjectTitle"] = "Artisan Apprenticeship Accelerated Development Initiative",
                ["Moa.CommencementDate"] = "01 July " + currentYear,
                ["Moa.TerminationDate"] = "30 June " + (DateTime.UtcNow.Year + 2),
                ["ClawbackAmount"] = "R 0.00",
                ["SignatoryName"] = "Disetlo Molapo",
                ["Signatory.Name"] = "Disetlo Molapo",
                ["SignatoryTitle"] = "Chief Executive Officer / ETQA Senior Manager",
                ["Signatory.Title"] = "Chief Executive Officer / ETQA Senior Manager",
                ["Verification.DigitalSeal"] = "7f8a9b2c3d4e5f6a",
                ["System.CurrentDate"] = DateTime.UtcNow.ToString("dd MMMM yyyy"),
                ["System.CurrentYear"] = currentYear,
                ["System.SetaName"] = "Manufacturing, Engineering and Related Services SETA (merSETA)"
            },
            ["NonLevySme"] = new()
            {
                ["ScenarioLabel"] = "Non-Levy Paying SME / NGO",
                ["Employer.Name"] = "Bambanani Skills Development Trust",
                ["RecipientName"] = "Bambanani Skills Development Trust",
                ["Employer.SdlNumber"] = $"NGO-{currentYear}-081",
                ["RecipientIdentifier"] = $"NGO-{currentYear}-081",
                ["DocumentNumber"] = $"DOC-{currentYear}-NGO-0112",
                ["Verification.DocumentNumber"] = $"DOC-{currentYear}-NGO-0112",
                ["IssuedDate"] = DateTime.UtcNow.ToString("dd MMMM yyyy"),
                ["Verification.IssuedDate"] = DateTime.UtcNow.ToString("dd MMMM yyyy"),
                ["FinancialYear"] = currentYear,
                ["Wsp.SchemeYear"] = currentYear,
                ["TradeTitle"] = "Welder (OFO 651202)",
                ["Trade.Title"] = "Welder",
                ["Trade.OfoCode"] = "651202",
                ["LearnerFullName"] = "Nokuthula Dlamini",
                ["Learner.FullName"] = "Nokuthula Dlamini",
                ["LearnerIdNumber"] = "9803150249081",
                ["Learner.RsaIdNumber"] = "9803150249081",
                ["CertificateNumber"] = $"TT-{currentYear}-00892",
                ["TradeTest.SerialNumber"] = $"TT-{currentYear}-00892",
                ["AccreditationNumber"] = $"17-QA/ACC/0944/{shortYear}",
                ["RebateAmount"] = "R 180,000.00",
                ["Wsp.RebateAmount"] = "R 180,000.00",
                ["ClawbackAmount"] = "R 0.00",
                ["SignatoryName"] = "Disetlo Molapo",
                ["Signatory.Name"] = "Disetlo Molapo",
                ["SignatoryTitle"] = "Chief Executive Officer / ETQA Senior Manager",
                ["Signatory.Title"] = "Chief Executive Officer / ETQA Senior Manager",
                ["System.CurrentDate"] = DateTime.UtcNow.ToString("dd MMMM yyyy"),
                ["System.CurrentYear"] = currentYear,
                ["System.SetaName"] = "Manufacturing, Engineering and Related Services SETA (merSETA)"
            },
            ["ArtisanCandidate"] = new()
            {
                ["ScenarioLabel"] = "Artisan Trade Test Candidate",
                ["RecipientName"] = "Tebogo Mokoena",
                ["Learner.FullName"] = "Tebogo Mokoena",
                ["LearnerFullName"] = "Tebogo Mokoena",
                ["RecipientIdentifier"] = "9207185089087",
                ["Learner.RsaIdNumber"] = "9207185089087",
                ["LearnerIdNumber"] = "9207185089087",
                ["DocumentNumber"] = $"DOC-{currentYear}-CERT-9041",
                ["Verification.DocumentNumber"] = $"DOC-{currentYear}-CERT-9041",
                ["IssuedDate"] = DateTime.UtcNow.ToString("dd MMMM yyyy"),
                ["Verification.IssuedDate"] = DateTime.UtcNow.ToString("dd MMMM yyyy"),
                ["FinancialYear"] = currentYear,
                ["TradeTitle"] = "Automotive Motor Mechanic (OFO 653101)",
                ["Trade.Title"] = "Automotive Motor Mechanic",
                ["Trade.OfoCode"] = "653101",
                ["CertificateNumber"] = $"TT-{currentYear}-01258",
                ["TradeTest.SerialNumber"] = $"TT-{currentYear}-01258",
                ["TradeTest.TestCenterName"] = "merSETA Motor Vehicle Testing Centre",
                ["TradeTest.AssessmentDate"] = DateTime.UtcNow.ToString("dd MMMM yyyy"),
                ["TradeTest.Outcome"] = "COMPETENT AS AN ARTISAN",
                ["AccreditationNumber"] = $"17-QA/ACC/0892/{shortYear}",
                ["RebateAmount"] = "R 0.00",
                ["ClawbackAmount"] = "R 0.00",
                ["SignatoryName"] = "Disetlo Molapo",
                ["Signatory.Name"] = "Disetlo Molapo",
                ["SignatoryTitle"] = "Chief Executive Officer / ETQA Senior Manager",
                ["Signatory.Title"] = "Chief Executive Officer / ETQA Senior Manager",
                ["System.CurrentDate"] = DateTime.UtcNow.ToString("dd MMMM yyyy"),
                ["System.CurrentYear"] = currentYear,
                ["System.SetaName"] = "Manufacturing, Engineering and Related Services SETA (merSETA)"
            },
            ["AccreditedSdp"] = new()
            {
                ["ScenarioLabel"] = "Accredited Skills Development Provider (SDP)",
                ["RecipientName"] = "Gauteng Advanced Technical Academy",
                ["Provider.Name"] = "Gauteng Advanced Technical Academy",
                ["RecipientIdentifier"] = $"SDP-{currentYear}-0055",
                ["Provider.AccreditationNumber"] = $"17-QA/ACC/0892/{shortYear}",
                ["DocumentNumber"] = $"DOC-{currentYear}-SDP-0441",
                ["Verification.DocumentNumber"] = $"DOC-{currentYear}-SDP-0441",
                ["IssuedDate"] = DateTime.UtcNow.ToString("dd MMMM yyyy"),
                ["Verification.IssuedDate"] = DateTime.UtcNow.ToString("dd MMMM yyyy"),
                ["FinancialYear"] = currentYear,
                ["TradeTitle"] = "Electrician (OFO 671101)",
                ["Trade.Title"] = "Electrician",
                ["Trade.OfoCode"] = "671101",
                ["LearnerFullName"] = "N/A - Provider Level",
                ["LearnerIdNumber"] = "N/A",
                ["CertificateNumber"] = $"ACC-{currentYear}-092",
                ["AccreditationNumber"] = $"17-QA/ACC/0892/{shortYear}",
                ["Accreditation.StartDate"] = "01 January " + currentYear,
                ["Accreditation.ExpiryDate"] = "31 December " + (DateTime.UtcNow.Year + 3),
                ["Accreditation.ScopeOfApproval"] = "National Certificate: Electrical Engineering (NQF 4)",
                ["RebateAmount"] = "R 0.00",
                ["ClawbackAmount"] = "R 0.00",
                ["SignatoryName"] = "Disetlo Molapo",
                ["Signatory.Name"] = "Disetlo Molapo",
                ["SignatoryTitle"] = "Chief Executive Officer / ETQA Senior Manager",
                ["Signatory.Title"] = "Chief Executive Officer / ETQA Senior Manager",
                ["System.CurrentDate"] = DateTime.UtcNow.ToString("dd MMMM yyyy"),
                ["System.CurrentYear"] = currentYear,
                ["System.SetaName"] = "Manufacturing, Engineering and Related Services SETA (merSETA)"
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
