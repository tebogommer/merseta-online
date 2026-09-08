using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;
using System.Security.Cryptography;
using System.Text;

namespace Nsdms.Application.Services;

/// <summary>
/// Production implementation of the Relational Block-Based MoA Template &amp; Reusable Clause Engine (Option A).
/// </summary>
public class MoaTemplateEngineService : IMoaTemplateEngineService
{
    private readonly INsdmsDbContextFactory _factory;
    private readonly AuditService _audit;
    private readonly IPdfDocumentService _pdfService;

    public MoaTemplateEngineService(
        INsdmsDbContextFactory factory, 
        AuditService audit,
        IPdfDocumentService pdfService)
    {
        _factory = factory;
        _audit = audit;
        _pdfService = pdfService;
    }

    #region Clause Library Operations

    public async Task<List<MoaClause>> GetClausesAsync(string? category = null, bool activeOnly = false)
    {
        using var db = await _factory.CreateDbContextAsync();
        var query = db.MoaClauses.AsQueryable();

        if (!string.IsNullOrWhiteSpace(category) && category != "All")
        {
            query = query.Where(c => c.Category == category);
        }

        if (activeOnly)
        {
            query = query.Where(c => c.IsActive);
        }

        return await query.OrderBy(c => c.ClauseCode).ToListAsync();
    }

    public async Task<MoaClause?> GetClauseByIdAsync(int id)
    {
        using var db = await _factory.CreateDbContextAsync();
        return await db.MoaClauses
            .Include(c => c.TemplateSections)
                .ThenInclude(s => s.MoaTemplate)
            .FirstOrDefaultAsync(c => c.Id == id);
    }

    public async Task<MoaClause> CreateClauseAsync(MoaClause clause, string currentUsername)
    {
        using var db = await _factory.CreateDbContextAsync();
        clause.CreatedBy = currentUsername;
        clause.CreatedAt = DateTime.UtcNow;

        db.MoaClauses.Add(clause);
        await db.SaveChangesAsync();

        await _audit.LogAsync("MoaClause", clause.Id, "CreateClause", currentUsername, new { clause.ClauseCode, clause.ClauseTitle, clause.Category });
        return clause;
    }

    public async Task<MoaClause> UpdateClauseAsync(MoaClause clause, string currentUsername)
    {
        using var db = await _factory.CreateDbContextAsync();
        var existing = await db.MoaClauses.FindAsync(clause.Id)
            ?? throw new InvalidOperationException($"MoA Clause #{clause.Id} not found.");

        var before = new { existing.ClauseTitle, existing.Category, existing.IsMandatory, existing.IsActive };

        existing.ClauseTitle = clause.ClauseTitle;
        existing.Category = clause.Category;
        existing.ClauseContent = clause.ClauseContent;
        existing.IsMandatory = clause.IsMandatory;
        existing.IsActive = clause.IsActive;
        existing.ModifiedBy = currentUsername;
        existing.ModifiedAt = DateTime.UtcNow;

        await db.SaveChangesAsync();
        await _audit.LogActionAsync("MoaClause", existing.Id, "UpdateClause", currentUsername, before, new { existing.ClauseTitle, existing.Category, existing.IsMandatory, existing.IsActive });
        return existing;
    }

    public async Task<bool> DeleteClauseAsync(int id, string currentUsername)
    {
        using var db = await _factory.CreateDbContextAsync();
        var clause = await db.MoaClauses
            .Include(c => c.TemplateSections)
            .FirstOrDefaultAsync(c => c.Id == id);

        if (clause == null) return false;

        // If referenced in templates, soft-deactivate instead of hard delete to preserve historical integrity
        if (clause.TemplateSections.Any())
        {
            clause.IsActive = false;
            clause.ModifiedBy = currentUsername;
            clause.ModifiedAt = DateTime.UtcNow;
            await db.SaveChangesAsync();
            await _audit.LogAsync("MoaClause", clause.Id, "DeactivateClause", currentUsername, new { clause.ClauseCode, Reason = "Referenced in template sections" });
            return true;
        }

        db.MoaClauses.Remove(clause);
        await db.SaveChangesAsync();
        await _audit.LogAsync("MoaClause", id, "DeleteClause", currentUsername, new { clause.ClauseCode });
        return true;
    }

    #endregion

    #region Template Lifecycle & Composition Operations

    public async Task<List<MoaTemplate>> GetTemplatesAsync(int? financialYear = null, string? grantTypeCode = null)
    {
        using var db = await _factory.CreateDbContextAsync();
        var query = db.MoaTemplates
            .Include(t => t.Sections)
            .AsQueryable();

        if (financialYear.HasValue)
        {
            query = query.Where(t => t.FinancialYear == financialYear.Value);
        }

        if (!string.IsNullOrWhiteSpace(grantTypeCode) && grantTypeCode != "All")
        {
            query = query.Where(t => t.GrantTypeCode == grantTypeCode);
        }

        return await query.OrderByDescending(t => t.FinancialYear).ThenBy(t => t.TemplateCode).ToListAsync();
    }

    public async Task<MoaTemplate?> GetTemplateByIdAsync(int id)
    {
        using var db = await _factory.CreateDbContextAsync();
        return await db.MoaTemplates
            .Include(t => t.Sections.OrderBy(s => s.SequenceOrder))
                .ThenInclude(s => s.MoaClause)
            .Include(t => t.ExecutionSnapshots.OrderByDescending(s => s.FrozenAt))
            .FirstOrDefaultAsync(t => t.Id == id);
    }

    public async Task<MoaTemplate> CreateTemplateAsync(MoaTemplate template, string currentUsername)
    {
        using var db = await _factory.CreateDbContextAsync();
        template.CreatedBy = currentUsername;
        template.CreatedAt = DateTime.UtcNow;
        template.ApprovalStatus = "Draft";

        db.MoaTemplates.Add(template);
        await db.SaveChangesAsync();

        await _audit.LogAsync("MoaTemplate", template.Id, "CreateTemplate", currentUsername, new { template.TemplateCode, template.FinancialYear, template.GrantTypeCode });
        return template;
    }

    public async Task<MoaTemplate> UpdateTemplateAsync(MoaTemplate template, string currentUsername)
    {
        using var db = await _factory.CreateDbContextAsync();
        var existing = await db.MoaTemplates.FindAsync(template.Id)
            ?? throw new InvalidOperationException($"MoA Template #{template.Id} not found.");

        var before = new { existing.TemplateTitle, existing.FinancialYear, existing.GrantTypeCode, existing.LegalEntityType, existing.VersionNumber, existing.IsActive };

        existing.TemplateTitle = template.TemplateTitle;
        existing.FinancialYear = template.FinancialYear;
        existing.GrantTypeCode = template.GrantTypeCode;
        existing.LegalEntityType = template.LegalEntityType;
        existing.VersionNumber = template.VersionNumber;
        existing.EffectiveFrom = template.EffectiveFrom;
        existing.EffectiveTo = template.EffectiveTo;
        existing.IsActive = template.IsActive;
        existing.ModifiedBy = currentUsername;
        existing.ModifiedAt = DateTime.UtcNow;

        await db.SaveChangesAsync();
        await _audit.LogActionAsync("MoaTemplate", existing.Id, "UpdateTemplate", currentUsername, before, new { existing.TemplateTitle, existing.FinancialYear, existing.GrantTypeCode, existing.LegalEntityType, existing.VersionNumber, existing.IsActive });
        return existing;
    }

    public async Task<MoaTemplate> ApproveTemplateAsync(int templateId, string currentUsername)
    {
        using var db = await _factory.CreateDbContextAsync();
        var existing = await db.MoaTemplates.FindAsync(templateId)
            ?? throw new InvalidOperationException($"MoA Template #{templateId} not found.");

        existing.ApprovalStatus = "Approved";
        existing.IsActive = true;
        existing.ApprovedBy = currentUsername;
        existing.ApprovedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;
        existing.ModifiedAt = DateTime.UtcNow;

        await db.SaveChangesAsync();
        await _audit.LogAsync("MoaTemplate", existing.Id, "ApproveTemplate", currentUsername, new { existing.TemplateCode, existing.VersionNumber, existing.ApprovedBy, existing.ApprovedAt });
        return existing;
    }

    public async Task<MoaTemplate> AddSectionAsync(int templateId, int clauseId, string sectionNumber, string sectionTitle, int sequenceOrder, bool isMandatory, string currentUsername)
    {
        using var db = await _factory.CreateDbContextAsync();
        var template = await db.MoaTemplates.Include(t => t.Sections).FirstOrDefaultAsync(t => t.Id == templateId)
            ?? throw new InvalidOperationException($"MoA Template #{templateId} not found.");

        var clause = await db.MoaClauses.FindAsync(clauseId)
            ?? throw new InvalidOperationException($"MoA Clause #{clauseId} not found.");

        var section = new MoaTemplateSection
        {
            MoaTemplateId = templateId,
            MoaClauseId = clauseId,
            SectionNumber = string.IsNullOrWhiteSpace(sectionNumber) ? $"{template.Sections.Count + 1}.0" : sectionNumber,
            SectionTitle = string.IsNullOrWhiteSpace(sectionTitle) ? clause.ClauseTitle : sectionTitle,
            SequenceOrder = sequenceOrder > 0 ? sequenceOrder : (template.Sections.Count + 1) * 10,
            IsMandatory = isMandatory,
            CreatedBy = currentUsername,
            CreatedAt = DateTime.UtcNow
        };

        db.MoaTemplateSections.Add(section);
        await db.SaveChangesAsync();

        await _audit.LogAsync("MoaTemplateSection", section.Id, "AddSection", currentUsername, new { templateId, clauseId, section.SectionNumber, section.SequenceOrder });
        return (await GetTemplateByIdAsync(templateId))!;
    }

    public async Task<MoaTemplate> RemoveSectionAsync(int sectionId, string currentUsername)
    {
        using var db = await _factory.CreateDbContextAsync();
        var section = await db.MoaTemplateSections.FindAsync(sectionId)
            ?? throw new InvalidOperationException($"MoA Template Section #{sectionId} not found.");

        int templateId = section.MoaTemplateId;
        db.MoaTemplateSections.Remove(section);
        await db.SaveChangesAsync();

        await _audit.LogAsync("MoaTemplateSection", sectionId, "RemoveSection", currentUsername, new { templateId });
        return (await GetTemplateByIdAsync(templateId))!;
    }

    public async Task<MoaTemplate> ReorderSectionsAsync(int templateId, List<int> sectionIdsInOrder, string currentUsername)
    {
        using var db = await _factory.CreateDbContextAsync();
        var sections = await db.MoaTemplateSections.Where(s => s.MoaTemplateId == templateId).ToListAsync();

        for (int i = 0; i < sectionIdsInOrder.Count; i++)
        {
            var sec = sections.FirstOrDefault(s => s.Id == sectionIdsInOrder[i]);
            if (sec != null)
            {
                sec.SequenceOrder = (i + 1) * 10;
                sec.ModifiedBy = currentUsername;
                sec.ModifiedAt = DateTime.UtcNow;
            }
        }

        await db.SaveChangesAsync();
        await _audit.LogAsync("MoaTemplate", templateId, "ReorderSections", currentUsername, new { OrderedSectionIds = sectionIdsInOrder });
        return (await GetTemplateByIdAsync(templateId))!;
    }

    #endregion

    #region Dynamic Resolution & Assembly Engine

    public async Task<MoaTemplate?> ResolveTemplateAsync(int financialYear, string grantTypeCode, string? entityType = null)
    {
        using var db = await _factory.CreateDbContextAsync();

        // 1. Exact Match on (FinancialYear, GrantTypeCode, LegalEntityType, IsActive, Approved)
        var template = await db.MoaTemplates
            .Include(t => t.Sections.OrderBy(s => s.SequenceOrder))
                .ThenInclude(s => s.MoaClause)
            .Where(t => t.FinancialYear == financialYear
                && t.GrantTypeCode == grantTypeCode
                && (string.IsNullOrWhiteSpace(entityType) || t.LegalEntityType == entityType || t.LegalEntityType == "All")
                && t.IsActive
                && t.ApprovalStatus == "Approved")
            .OrderByDescending(t => t.VersionNumber)
            .FirstOrDefaultAsync();

        if (template != null) return template;

        // 2. Fallback to same GrantTypeCode and Active
        template = await db.MoaTemplates
            .Include(t => t.Sections.OrderBy(s => s.SequenceOrder))
                .ThenInclude(s => s.MoaClause)
            .Where(t => t.GrantTypeCode == grantTypeCode && t.IsActive && t.ApprovalStatus == "Approved")
            .OrderByDescending(t => t.FinancialYear)
            .ThenByDescending(t => t.VersionNumber)
            .FirstOrDefaultAsync();

        if (template != null) return template;

        // 3. Fallback to standard 2026/any active template
        return await db.MoaTemplates
            .Include(t => t.Sections.OrderBy(s => s.SequenceOrder))
                .ThenInclude(s => s.MoaClause)
            .Where(t => t.IsActive)
            .OrderByDescending(t => t.FinancialYear)
            .FirstOrDefaultAsync();
    }

    public async Task<string> AssembleMoaMarkdownAsync(int grantMoaId, int? templateId = null)
    {
        using var db = await _factory.CreateDbContextAsync();
        var moa = await db.GrantMoas
            .Include(m => m.GrantApplication)
                .ThenInclude(a => a!.Organisation)
            .Include(m => m.Milestones)
            .FirstOrDefaultAsync(m => m.Id == grantMoaId)
            ?? throw new InvalidOperationException($"Grant MOA #{grantMoaId} not found.");

        MoaTemplate? template = null;
        if (templateId.HasValue)
        {
            template = await db.MoaTemplates
                .Include(t => t.Sections.OrderBy(s => s.SequenceOrder))
                    .ThenInclude(s => s.MoaClause)
                .FirstOrDefaultAsync(t => t.Id == templateId.Value);
        }

        if (template == null && moa.MoaTemplateId.HasValue)
        {
            template = await db.MoaTemplates
                .Include(t => t.Sections.OrderBy(s => s.SequenceOrder))
                    .ThenInclude(s => s.MoaClause)
                .FirstOrDefaultAsync(t => t.Id == moa.MoaTemplateId.Value);
        }

        if (template == null)
        {
            int finYear = moa.GrantApplication?.ApplicationDate.Year ?? moa.ContractStartDate.Year;
            template = await ResolveTemplateAsync(finYear, "DiscretionaryGrant");
        }

        if (template == null)
        {
            return $"# Memorandum of Agreement - {moa.MoaNumber}\n\n*No active template found in system.*";
        }

        return BuildDocumentText(template, moa);
    }

    public async Task<string> AssembleTemplatePreviewAsync(int templateId, int? sampleGrantMoaId = null)
    {
        using var db = await _factory.CreateDbContextAsync();
        var template = await db.MoaTemplates
            .Include(t => t.Sections.OrderBy(s => s.SequenceOrder))
                .ThenInclude(s => s.MoaClause)
            .FirstOrDefaultAsync(t => t.Id == templateId)
            ?? throw new InvalidOperationException($"MoA Template #{templateId} not found.");

        GrantMoa? moa = null;
        if (sampleGrantMoaId.HasValue)
        {
            moa = await db.GrantMoas
                .Include(m => m.GrantApplication)
                    .ThenInclude(a => a!.Organisation)
                .Include(m => m.Milestones)
                .FirstOrDefaultAsync(m => m.Id == sampleGrantMoaId.Value);
        }

        if (moa == null)
        {
            moa = await db.GrantMoas
                .Include(m => m.GrantApplication)
                    .ThenInclude(a => a!.Organisation)
                .Include(m => m.Milestones)
                .FirstOrDefaultAsync();
        }

        if (moa == null)
        {
            // Synthesize mock record for live UI preview if DB has no GrantMoa
            moa = new GrantMoa
            {
                Id = 999,
                MoaNumber = $"MOA-{template.FinancialYear}-DG-MOCK",
                ContractStartDate = new DateTime(template.FinancialYear, 4, 1),
                ContractEndDate = new DateTime(template.FinancialYear + 1, 3, 31),
                TotalContractValue = 750000.00m,
                MoaStatusCode = "Draft",
                GrantApplication = new GrantApplication
                {
                    ProjectTitle = "Sample Skills Development & Artisan Training Initiative",
                    ApplicationDate = new DateTime(template.FinancialYear, 4, 1),
                    Organisation = new Organisation
                    {
                        CompanyName = "Acme Manufacturing & Engineering (Pty) Ltd",
                        TradingName = "Acme Engineering",
                        SdlNumber = "L123456789"
                    }
                },
                Milestones = new List<GrantMoaMilestone>
                {
                    new GrantMoaMilestone { MilestoneNumber = 1, MilestoneTitle = "Inception & Learner Contracting (Annexure D)", TranchePercentage = 30, TrancheAmount = 225000, MilestoneStatusCode = "Pending", TargetDueDate = new DateTime(template.FinancialYear, 6, 30) },
                    new GrantMoaMilestone { MilestoneNumber = 2, MilestoneTitle = "Midterm Workplace Logbook Verification", TranchePercentage = 30, TrancheAmount = 225000, MilestoneStatusCode = "Pending", TargetDueDate = new DateTime(template.FinancialYear, 9, 30) },
                    new GrantMoaMilestone { MilestoneNumber = 3, MilestoneTitle = "Summative Assessment & Final Closeout", TranchePercentage = 40, TrancheAmount = 300000, MilestoneStatusCode = "Pending", TargetDueDate = new DateTime(template.FinancialYear + 1, 3, 31) }
                }
            };
        }

        return BuildDocumentText(template, moa);
    }

    private static string BuildDocumentText(MoaTemplate template, GrantMoa moa)
    {
        var sb = new StringBuilder();

        sb.AppendLine($"# {template.TemplateTitle}");
        sb.AppendLine($"**Template Reference:** `{template.TemplateCode}` | **Version:** `{template.VersionNumber}` | **Effective Year:** {template.FinancialYear}");
        sb.AppendLine("---");
        sb.AppendLine();

        foreach (var section in template.Sections.OrderBy(s => s.SequenceOrder))
        {
            var clause = section.MoaClause;
            if (clause == null || !clause.IsActive) continue;

            sb.AppendLine($"### {section.SectionNumber} {section.SectionTitle}");
            sb.AppendLine();

            string interpolatedContent = InterpolateTokens(clause.ClauseContent, moa, template);
            sb.AppendLine(interpolatedContent);
            sb.AppendLine();
        }

        return sb.ToString();
    }

    private static string InterpolateTokens(string content, GrantMoa moa, MoaTemplate template)
    {
        var org = moa.GrantApplication?.Organisation;
        string orgName = org?.CompanyName ?? "The Grantee Organisation";
        string tradeName = org?.TradingName ?? orgName;
        string levyNum = org?.SdlNumber ?? "L000000000";
        string grantYear = moa.GrantApplication?.ApplicationDate.Year.ToString() ?? template.FinancialYear.ToString();
        string moaNum = string.IsNullOrWhiteSpace(moa.MoaNumber) ? $"MOA-{template.FinancialYear}-DG-0001" : moa.MoaNumber;
        string totalValue = moa.TotalContractValue.ToString("N2", System.Globalization.CultureInfo.InvariantCulture);
        string startDate = moa.ContractStartDate.ToString("dd MMMM yyyy");
        string endDate = moa.ContractEndDate.ToString("dd MMMM yyyy");
        string projectTitle = moa.GrantApplication?.ProjectTitle ?? "Discretionary Grant Skills Programme";
        string signatoryEmployer = moa.SignoffDateEmployer.HasValue ? $"Authorised Signatory (Signed {moa.SignoffDateEmployer:yyyy-MM-dd})" : "Authorised Representative";
        string signatorySeta = moa.SignoffDateSeta.HasValue ? $"merSETA Chief Executive Officer (Signed {moa.SignoffDateSeta:yyyy-MM-dd})" : "merSETA Chief Executive Officer";

        var sbTable = new StringBuilder();
        sbTable.AppendLine("| Tranche # | Deliverable Milestone | Tranche % | Amount (ZAR) | Target Due Date | Status |");
        sbTable.AppendLine("| :---: | :--- | :---: | :---: | :---: | :---: |");

        if (moa.Milestones != null && moa.Milestones.Any())
        {
            foreach (var m in moa.Milestones.OrderBy(x => x.MilestoneNumber))
            {
                sbTable.AppendLine($"| **Tranche {m.MilestoneNumber}** | {m.MilestoneTitle} | {m.TranchePercentage.ToString("N0", System.Globalization.CultureInfo.InvariantCulture)}% | R {m.TrancheAmount.ToString("N2", System.Globalization.CultureInfo.InvariantCulture)} | {m.TargetDueDate:yyyy-MM-dd} | `{m.MilestoneStatusCode}` |");
            }
        }
        else
        {
            sbTable.AppendLine("| 1 | Inception & Learner Registration | 100% | R " + totalValue + " | " + endDate + " | Pending |");
        }

        string tableMarkdown = sbTable.ToString().TrimEnd();

        return content
            .Replace("{{OrganisationName}}", orgName)
            .Replace("{{TradingName}}", tradeName)
            .Replace("{{LevyNumber}}", levyNum)
            .Replace("{{GrantYear}}", grantYear)
            .Replace("{{MoaNumber}}", moaNum)
            .Replace("{{TotalContractValue}}", totalValue)
            .Replace("{{ContractStartDate}}", startDate)
            .Replace("{{ContractEndDate}}", endDate)
            .Replace("{{ProjectTitle}}", projectTitle)
            .Replace("{{SignatoryEmployer}}", signatoryEmployer)
            .Replace("{{SignatorySeta}}", signatorySeta)
            .Replace("{{TrancheScheduleTable}}", tableMarkdown);
    }

    public async Task<byte[]> GenerateSimulatedPdfAsync(int templateId, Dictionary<string, string>? sampleTokens = null, bool includeWatermark = true)
    {
        using var db = await _factory.CreateDbContextAsync();
        var template = await db.MoaTemplates
            .Include(t => t.Sections.OrderBy(s => s.SequenceOrder))
                .ThenInclude(s => s.MoaClause)
            .FirstOrDefaultAsync(t => t.Id == templateId)
            ?? throw new InvalidOperationException($"MoA template #{templateId} not found.");

        var defaultProfiles = GetDefaultScenarioTokenProfiles();
        var tokens = new Dictionary<string, string>(defaultProfiles["LevyEmployer"]);

        if (sampleTokens != null)
        {
            foreach (var kvp in sampleTokens)
            {
                tokens[kvp.Key] = kvp.Value;
            }
        }

        return await _pdfService.GenerateSimulatedMoaTemplatePdfAsync(template, tokens, includeWatermark);
    }

    public Dictionary<string, Dictionary<string, string>> GetDefaultScenarioTokenProfiles()
    {
        var currentYear = DateTime.UtcNow.Year.ToString();

        return new Dictionary<string, Dictionary<string, string>>
        {
            ["LevyEmployer"] = new()
            {
                ["ScenarioLabel"] = "Discretionary Grant — Corporate Employer",
                ["EmployerName"] = "Apex Engineering Works (Pty) Ltd",
                ["SdlNumber"] = "L998877665",
                ["ProjectTitle"] = $"Apprenticeship Skills Development Programme {currentYear}",
                ["MoaNumber"] = $"MOA-{currentYear}-DG-00892",
                ["TotalContractValue"] = "R 450,000.00",
                ["ContractPeriod"] = $"{DateTime.UtcNow:yyyy-MM-dd} to {DateTime.UtcNow.AddYears(1):yyyy-MM-dd}",
                ["ContractStartDate"] = DateTime.UtcNow.ToString("dd MMMM yyyy"),
                ["ContractEndDate"] = DateTime.UtcNow.AddYears(1).ToString("dd MMMM yyyy")
            },
            ["NonLevySme"] = new()
            {
                ["ScenarioLabel"] = "Special Project — Non-Levy Community Trust",
                ["EmployerName"] = "Bambanani Skills Development Trust",
                ["SdlNumber"] = $"NGO-{currentYear}-081",
                ["ProjectTitle"] = "Community Artisan Upliftment & Welder Training",
                ["MoaNumber"] = $"MOA-{currentYear}-SP-00114",
                ["TotalContractValue"] = "R 180,000.00",
                ["ContractPeriod"] = $"{DateTime.UtcNow:yyyy-MM-dd} to {DateTime.UtcNow.AddMonths(18):yyyy-MM-dd}",
                ["ContractStartDate"] = DateTime.UtcNow.ToString("dd MMMM yyyy"),
                ["ContractEndDate"] = DateTime.UtcNow.AddMonths(18).ToString("dd MMMM yyyy")
            },
            ["TvetCollege"] = new()
            {
                ["ScenarioLabel"] = "Public TVET College Partnership",
                ["EmployerName"] = "Ekurhuleni East TVET College",
                ["SdlNumber"] = $"TVET-{currentYear}-004",
                ["ProjectTitle"] = "NCV Level 4 Engineering & Related Services Practical Workplace",
                ["MoaNumber"] = $"MOA-{currentYear}-TVET-00045",
                ["TotalContractValue"] = "R 1,200,000.00",
                ["ContractPeriod"] = $"{DateTime.UtcNow:yyyy-MM-dd} to {DateTime.UtcNow.AddYears(2):yyyy-MM-dd}",
                ["ContractStartDate"] = DateTime.UtcNow.ToString("dd MMMM yyyy"),
                ["ContractEndDate"] = DateTime.UtcNow.AddYears(2).ToString("dd MMMM yyyy")
            },
            ["BursaryCandidacy"] = new()
            {
                ["ScenarioLabel"] = "Bursary & Candidate Engineer Mentorship",
                ["EmployerName"] = "Transnet Engineering Division",
                ["SdlNumber"] = "L102938475",
                ["ProjectTitle"] = "Mechanical & Industrial Candidate Engineer Development Scheme",
                ["MoaNumber"] = $"MOA-{currentYear}-BUR-00301",
                ["TotalContractValue"] = "R 320,000.00",
                ["ContractPeriod"] = $"{DateTime.UtcNow:yyyy-MM-dd} to {DateTime.UtcNow.AddYears(3):yyyy-MM-dd}",
                ["ContractStartDate"] = DateTime.UtcNow.ToString("dd MMMM yyyy"),
                ["ContractEndDate"] = DateTime.UtcNow.AddYears(3).ToString("dd MMMM yyyy")
            }
        };
    }

    #endregion

    #region Cryptographic Freezing & Snapshot Non-Repudiation

    public async Task<MoaExecutionSnapshot> FreezeAndIssueMoaSnapshotAsync(int grantMoaId, string currentUsername)
    {
        using var db = await _factory.CreateDbContextAsync();
        var moa = await db.GrantMoas
            .Include(m => m.GrantApplication)
                .ThenInclude(a => a!.Organisation)
            .Include(m => m.Milestones)
            .FirstOrDefaultAsync(m => m.Id == grantMoaId)
            ?? throw new InvalidOperationException($"Grant MOA #{grantMoaId} not found.");

        int finYear = moa.GrantApplication?.ApplicationDate.Year ?? moa.ContractStartDate.Year;
        var template = await ResolveTemplateAsync(finYear, "DiscretionaryGrant")
            ?? throw new InvalidOperationException($"No approved MoA template found for financial year {finYear}.");

        // 1. Assemble dynamic Markdown text
        string assembledText = BuildDocumentText(template, moa);

        // 2. Compute SHA-256 digital fingerprint hash
        byte[] hashBytes = SHA256.HashData(Encoding.UTF8.GetBytes(assembledText));
        string sha256Hex = Convert.ToHexStringLower(hashBytes);

        // 3. Create frozen snapshot record
        var snapshot = new MoaExecutionSnapshot
        {
            GrantMoaId = grantMoaId,
            MoaTemplateId = template.Id,
            TemplateVersionNumber = template.VersionNumber,
            RenderedContentHash = sha256Hex,
            RenderedContent = assembledText,
            PdfStorageUri = $"/documents/moa/MOA_{moa.MoaNumber}_{DateTime.UtcNow:yyyyMMddHHmmss}.pdf",
            FrozenAt = DateTime.UtcNow,
            SignatoryEmployer = moa.SignoffDateEmployer.HasValue ? "Authorised Representative" : null,
            SignatorySeta = moa.SignoffDateSeta.HasValue ? "merSETA CEO" : null,
            CreatedBy = currentUsername,
            CreatedAt = DateTime.UtcNow
        };

        db.MoaExecutionSnapshots.Add(snapshot);

        // Link GrantMoa to this template
        moa.MoaTemplateId = template.Id;
        moa.SignoffDocumentUri = snapshot.PdfStorageUri;
        moa.ModifiedBy = currentUsername;
        moa.ModifiedAt = DateTime.UtcNow;

        await db.SaveChangesAsync();

        await _audit.LogAsync("MoaExecutionSnapshot", snapshot.Id, "FreezeMoaSnapshot", currentUsername, new
        {
            grantMoaId,
            moa.MoaNumber,
            template.TemplateCode,
            template.VersionNumber,
            snapshot.RenderedContentHash,
            snapshot.FrozenAt
        });

        return snapshot;
    }

    public async Task<MoaExecutionSnapshot?> GetLatestMoaSnapshotAsync(int grantMoaId)
    {
        using var db = await _factory.CreateDbContextAsync();
        return await db.MoaExecutionSnapshots
            .Include(s => s.MoaTemplate)
            .Where(s => s.GrantMoaId == grantMoaId)
            .OrderByDescending(s => s.FrozenAt)
            .FirstOrDefaultAsync();
    }

    public async Task<List<MoaExecutionSnapshot>> GetMoaSnapshotsAsync(int grantMoaId)
    {
        using var db = await _factory.CreateDbContextAsync();
        return await db.MoaExecutionSnapshots
            .Include(s => s.MoaTemplate)
            .Where(s => s.GrantMoaId == grantMoaId)
            .OrderByDescending(s => s.FrozenAt)
            .ToListAsync();
    }

    public async Task<bool> VerifySnapshotIntegrityAsync(int snapshotId)
    {
        using var db = await _factory.CreateDbContextAsync();
        var snapshot = await db.MoaExecutionSnapshots.FindAsync(snapshotId);
        if (snapshot == null || string.IsNullOrWhiteSpace(snapshot.RenderedContent)) return false;

        byte[] calculatedBytes = SHA256.HashData(Encoding.UTF8.GetBytes(snapshot.RenderedContent));
        string calculatedHex = Convert.ToHexStringLower(calculatedBytes);

        return string.Equals(calculatedHex, snapshot.RenderedContentHash, StringComparison.OrdinalIgnoreCase);
    }

    #endregion
}
