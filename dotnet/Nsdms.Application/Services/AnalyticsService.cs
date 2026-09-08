using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class AnalyticsService : IAnalyticsService
{
    private readonly INsdmsDbContextFactory _contextFactory;

    public AnalyticsService(INsdmsDbContextFactory contextFactory)
    {
        _contextFactory = contextFactory;
    }

    public async Task<List<SspChamberMetricDto>> GetSspChamberMetricsAsync()
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var chambers = await context.ChamberTypes.AsNoTracking().Where(c => c.Active).ToListAsync();

        var orgCountsByChamber = await context.Organisations.AsNoTracking()
            .Where(o => o.ChamberCode != null)
            .GroupBy(o => o.ChamberCode!)
            .Select(g => new { ChamberCode = g.Key, Count = g.Count() })
            .ToDictionaryAsync(x => x.ChamberCode, x => x.Count);

        var wspBudgetByChamber = await context.WspSubmissions.AsNoTracking()
            .Where(w => w.Organisation != null && w.Organisation.ChamberCode != null)
            .GroupBy(w => w.Organisation!.ChamberCode!)
            .Select(g => new { ChamberCode = g.Key, TotalBudget = g.Sum(w => w.PlannedTrainingBudget), EmployeeCount = g.Sum(w => w.EmployeeCount) })
            .ToDictionaryAsync(x => x.ChamberCode, x => new { x.TotalBudget, x.EmployeeCount });

        var learnerCountByChamber = await context.CompanyLearners.AsNoTracking()
            .Where(l => l.Organisation != null && l.Organisation.ChamberCode != null)
            .GroupBy(l => l.Organisation!.ChamberCode!)
            .Select(g => new { ChamberCode = g.Key, Count = g.Count() })
            .ToDictionaryAsync(x => x.ChamberCode, x => x.Count);

        var disbursedByChamber = await context.GrantTranchePayments.AsNoTracking()
            .Where(p => p.PaymentStatusCode == "Paid" && p.GrantMoaMilestone != null && p.GrantMoaMilestone.GrantMoa != null && p.GrantMoaMilestone.GrantMoa.GrantApplication != null && p.GrantMoaMilestone.GrantMoa.GrantApplication.Organisation != null && p.GrantMoaMilestone.GrantMoa.GrantApplication.Organisation.ChamberCode != null)
            .GroupBy(p => p.GrantMoaMilestone!.GrantMoa!.GrantApplication!.Organisation!.ChamberCode!)
            .Select(g => new { ChamberCode = g.Key, TotalDisbursed = g.Sum(p => p.ApprovedPaymentAmount) })
            .ToDictionaryAsync(x => x.ChamberCode, x => x.TotalDisbursed);

        var result = new List<SspChamberMetricDto>();

        foreach (var ch in chambers)
        {
            var empCount = orgCountsByChamber.TryGetValue(ch.Code, out var ec) ? ec : 0;
            wspBudgetByChamber.TryGetValue(ch.Code, out var wspData);
            var totalBudget = wspData?.TotalBudget ?? 0m;
            var empTotal = wspData?.EmployeeCount ?? 0;
            var lrnCount = learnerCountByChamber.TryGetValue(ch.Code, out var lc) ? lc : 0;
            var disbursed = disbursedByChamber.TryGetValue(ch.Code, out var da) ? da : 0m;

            result.Add(new SspChamberMetricDto
            {
                ChamberName = ch.Name,
                EmployerCount = empCount,
                LearnerCount = lrnCount,
                PlannedTrainingBudget = totalBudget,
                DisbursedGrantsAmount = disbursed
            });
        }

        return result;
    }

    public async Task<List<SspEquityMetricDto>> GetSspEquityMetricsAsync()
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var equityCounts = await context.CompanyLearners.AsNoTracking()
            .GroupBy(l => l.Person != null ? l.Person.EquityCode : null)
            .Select(g => new { Code = g.Key, Count = g.Count() })
            .ToListAsync();

        var total = Math.Max(1, equityCounts.Sum(x => x.Count));

        var africanCount = equityCounts.Where(x => x.Code == "1" || x.Code == "AFR" || x.Code == null).Sum(x => x.Count);
        var colouredCount = equityCounts.Where(x => x.Code == "2" || x.Code == "COL").Sum(x => x.Count);
        var indianCount = equityCounts.Where(x => x.Code == "3" || x.Code == "IND").Sum(x => x.Count);
        var whiteCount = equityCounts.Where(x => x.Code == "4" || x.Code == "WHI").Sum(x => x.Count);

        return new List<SspEquityMetricDto>
        {
            new() { DemographicGroup = "African", TotalLearners = africanCount, Percentage = Math.Round((africanCount * 100.0) / total, 1) },
            new() { DemographicGroup = "Coloured", TotalLearners = colouredCount, Percentage = Math.Round((colouredCount * 100.0) / total, 1) },
            new() { DemographicGroup = "Indian / Asian", TotalLearners = indianCount, Percentage = Math.Round((indianCount * 100.0) / total, 1) },
            new() { DemographicGroup = "White", TotalLearners = whiteCount, Percentage = Math.Round((whiteCount * 100.0) / total, 1) }
        };
    }

    public async Task<List<SspProvincialMetricDto>> GetSspProvincialMetricsAsync()
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var provinces = await context.ProvinceTypes.AsNoTracking().Where(p => p.Active).ToListAsync();

        var orgCountsByProv = await context.Organisations.AsNoTracking()
            .Where(o => o.ProvinceCode != null)
            .GroupBy(o => o.ProvinceCode!)
            .Select(g => new { ProvinceCode = g.Key, Count = g.Count() })
            .ToDictionaryAsync(x => x.ProvinceCode, x => x.Count);

        var provCountsByProv = await context.TrainingProviders.AsNoTracking()
            .Where(pr => pr.Organisation != null && pr.Organisation.ProvinceCode != null)
            .GroupBy(pr => pr.Organisation!.ProvinceCode!)
            .Select(g => new { ProvinceCode = g.Key, Count = g.Count() })
            .ToDictionaryAsync(x => x.ProvinceCode, x => x.Count);

        var lrnCountsByProv = await context.CompanyLearners.AsNoTracking()
            .Where(l => l.Organisation != null && l.Organisation.ProvinceCode != null)
            .GroupBy(l => l.Organisation!.ProvinceCode!)
            .Select(g => new { ProvinceCode = g.Key, Count = g.Count() })
            .ToDictionaryAsync(x => x.ProvinceCode, x => x.Count);

        var certCountsByProv = await context.LearnerTradeTests.AsNoTracking()
            .Where(t => t.ResultStatusCode == "Competent" && t.CompanyLearner != null && t.CompanyLearner.Organisation != null && t.CompanyLearner.Organisation.ProvinceCode != null)
            .GroupBy(t => t.CompanyLearner!.Organisation!.ProvinceCode!)
            .Select(g => new { ProvinceCode = g.Key, Count = g.Count() })
            .ToDictionaryAsync(x => x.ProvinceCode, x => x.Count);

        var result = new List<SspProvincialMetricDto>();

        foreach (var p in provinces)
        {
            result.Add(new SspProvincialMetricDto
            {
                ProvinceCode = p.Code,
                ProvinceName = p.Name,
                EmployerCount = orgCountsByProv.TryGetValue(p.Code, out var ec) ? ec : 0,
                ProviderCount = provCountsByProv.TryGetValue(p.Code, out var pc) ? pc : 0,
                ActiveLearnerCount = lrnCountsByProv.TryGetValue(p.Code, out var lc) ? lc : 0,
                CertifiedCount = certCountsByProv.TryGetValue(p.Code, out var cc) ? cc : 0
            });
        }

        return result;
    }

    public async Task<List<SspScarceSkillDto>> GetSspScarceSkillsAsync()
    {
        // Standard MerSETA Sector Skills Plan Scarce & Critical Occupations List
        return await Task.FromResult(new List<SspScarceSkillDto>
        {
            new() { OfoCode = "651202", OccupationTitle = "Welder / Coded Fabricator", ChamberName = "Metal & Engineering", ReportedNeedCount = 1450, EnrolledTrainingCount = 890, InterventionsRequired = "Apprenticeship & ARPL Trade Testing" },
            new() { OfoCode = "653101", OccupationTitle = "Automotive Motor Mechanic", ChamberName = "Automotive Manufacturing", ReportedNeedCount = 1200, EnrolledTrainingCount = 740, InterventionsRequired = "Dual System Apprenticeship (AOP)" },
            new() { OfoCode = "671101", OccupationTitle = "Electrician / Industrial Automation", ChamberName = "Electrical Infrastructure", ReportedNeedCount = 980, EnrolledTrainingCount = 610, InterventionsRequired = "QCTO Occupational Certificate" },
            new() { OfoCode = "651501", OccupationTitle = "Fitter and Turner / CNC Machinist", ChamberName = "Metal & Engineering", ReportedNeedCount = 850, EnrolledTrainingCount = 520, InterventionsRequired = "Advanced CNC Skills Programme" },
            new() { OfoCode = "653306", OccupationTitle = "Diesel / Heavy Equipment Mechanic", ChamberName = "Automotive & Heavy Industry", ReportedNeedCount = 780, EnrolledTrainingCount = 490, InterventionsRequired = "Section 28 ARPL / CBMT" },
            new() { OfoCode = "672105", OccupationTitle = "Plastics Moulding Technician", ChamberName = "Plastics Manufacturing", ReportedNeedCount = 540, EnrolledTrainingCount = 310, InterventionsRequired = "Specialized Extrusion Learnership" },
            new() { OfoCode = "682201", OccupationTitle = "Toolmaker / Patternmaker", ChamberName = "Metal Chamber", ReportedNeedCount = 460, EnrolledTrainingCount = 280, InterventionsRequired = "High-precision CAD/CAM Apprenticeship" }
        });
    }

    public async Task<(decimal TotalCommitted, decimal TotalDisbursed, decimal TotalRebates)> GetFinancialOverviewAsync()
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var totalCommitted = await context.GrantMoas.AsNoTracking().SumAsync(m => (decimal?)m.TotalContractValue) ?? 0m;
        var totalDisbursed = await context.GrantTranchePayments.AsNoTracking().Where(p => p.PaymentStatusCode == "Paid").SumAsync(p => (decimal?)p.ApprovedPaymentAmount) ?? 0m;
        var totalRebates = await context.MandatoryGrantDisbursements.AsNoTracking().Where(d => d.DisbursementStatusCode == "Paid").SumAsync(d => (decimal?)d.CalculatedRebateAmount) ?? 0m;

        return (totalCommitted, totalDisbursed, totalRebates);
    }

    public async Task<List<ChamberGrantFinancialSummaryDto>> GetChamberGrantFinancialSummaryAsync(string? schemeYear = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var chambers = await db.ChamberTypes.AsNoTracking().Where(c => c.Active).OrderBy(c => c.Name).ToListAsync();

        var orgs = await db.Organisations.AsNoTracking()
            .Select(o => new { o.Id, o.ChamberCode, o.SdlNumber })
            .ToListAsync();

        var levyQuery = db.LevyFileLines.AsNoTracking().AsQueryable();
        if (!string.IsNullOrWhiteSpace(schemeYear) && schemeYear != "All")
        {
            levyQuery = levyQuery.Where(l => l.SchemeYear == schemeYear || l.SchemeYear.StartsWith(schemeYear));
        }

        var levyAggsByChamber = await levyQuery
            .Where(l => l.ChamberCode != null)
            .GroupBy(l => l.ChamberCode!)
            .Select(g => new
            {
                ChamberCode = g.Key,
                TotalGross = g.Sum(l => l.TotalLevyAmount),
                Mandatory = g.Sum(l => l.MandatoryLevyAmount),
                Discretionary = g.Sum(l => l.DiscretionaryLevyAmount),
                Admin = g.Sum(l => l.AdminLevyAmount),
                Qcto = g.Sum(l => l.QctoLevyAmount)
            })
            .ToDictionaryAsync(x => x.ChamberCode);

        var dgCommittedByChamber = await db.GrantMoas.AsNoTracking()
            .Where(m => m.GrantApplication != null && m.GrantApplication.Organisation != null && m.GrantApplication.Organisation.ChamberCode != null)
            .GroupBy(m => m.GrantApplication!.Organisation!.ChamberCode!)
            .Select(g => new { ChamberCode = g.Key, Total = g.Sum(m => m.TotalContractValue) })
            .ToDictionaryAsync(x => x.ChamberCode, x => x.Total);

        var dgDisbursedByChamber = await db.GrantTranchePayments.AsNoTracking()
            .Where(p => p.PaymentStatusCode == "Paid" && p.GrantMoaMilestone != null && p.GrantMoaMilestone.GrantMoa != null && p.GrantMoaMilestone.GrantMoa.GrantApplication != null && p.GrantMoaMilestone.GrantMoa.GrantApplication.Organisation != null && p.GrantMoaMilestone.GrantMoa.GrantApplication.Organisation.ChamberCode != null)
            .GroupBy(p => p.GrantMoaMilestone!.GrantMoa!.GrantApplication!.Organisation!.ChamberCode!)
            .Select(g => new { ChamberCode = g.Key, Total = g.Sum(p => p.ApprovedPaymentAmount) })
            .ToDictionaryAsync(x => x.ChamberCode, x => x.Total);

        var mgPaidByChamber = await db.MandatoryGrantDisbursements.AsNoTracking()
            .Where(r => r.DisbursementStatusCode == "Paid" && r.Organisation != null && r.Organisation.ChamberCode != null)
            .GroupBy(r => r.Organisation!.ChamberCode!)
            .Select(g => new { ChamberCode = g.Key, Total = g.Sum(r => r.CalculatedRebateAmount) })
            .ToDictionaryAsync(x => x.ChamberCode, x => x.Total);

        var result = new List<ChamberGrantFinancialSummaryDto>();

        foreach (var ch in chambers)
        {
            var empCount = orgs.Count(o => o.ChamberCode == ch.Code);

            levyAggsByChamber.TryGetValue(ch.Code, out var levy);
            var totalGross = levy?.TotalGross ?? 0m;
            var mandatoryEnvelope = levy?.Mandatory ?? 0m;
            var discretionaryEnvelope = levy?.Discretionary ?? 0m;
            var adminPortion = levy?.Admin ?? 0m;
            var qctoPortion = levy?.Qcto ?? 0m;

            if (mandatoryEnvelope == 0 && totalGross > 0)
            {
                mandatoryEnvelope = totalGross * 0.20m;
                discretionaryEnvelope = totalGross * 0.495m;
                adminPortion = totalGross * 0.105m;
                qctoPortion = totalGross * 0.005m;
            }

            var dgCommitted = dgCommittedByChamber.TryGetValue(ch.Code, out var dgc) ? dgc : 0m;
            var dgDisbursed = dgDisbursedByChamber.TryGetValue(ch.Code, out var dgd) ? dgd : 0m;
            var mgPaid = mgPaidByChamber.TryGetValue(ch.Code, out var mgp) ? mgp : 0m;

            result.Add(new ChamberGrantFinancialSummaryDto
            {
                ChamberCode = ch.Code,
                ChamberName = ch.Name,
                EmployerCount = empCount,
                TotalGrossLevyCollected = totalGross,
                MandatoryGrantRebateTarget = mandatoryEnvelope,
                MandatoryGrantRebatesPaid = mgPaid,
                DiscretionaryGrantEnvelope = discretionaryEnvelope,
                DiscretionaryGrantCommitted = dgCommitted,
                DiscretionaryGrantDisbursed = dgDisbursed,
                AdministrationExpensePortion = adminPortion,
                QctoLevyPortion = qctoPortion
            });
        }

        return result;
    }

    public async Task<ExecutiveDashboardSummaryDto> GetExecutiveDashboardSummaryAsync()
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var empCount = await context.Organisations.AsNoTracking().CountAsync();
        var lrnCount = await context.CompanyLearners.AsNoTracking().CountAsync();
        var provCount = await context.TrainingProviders.AsNoTracking().CountAsync();
        var grantCount = await context.GrantApplications.AsNoTracking().CountAsync();

        var pendingGrants = await context.GrantApplications.AsNoTracking()
            .Include(g => g.Organisation)
            .Where(g => g.ApplicationStatusCode == "Pending" || g.ApplicationStatusCode == "Under Review" || g.ApplicationStatusCode == "Committee Review" || g.ApplicationStatusCode == "Submitted")
            .OrderByDescending(g => g.CreatedAt)
            .Take(10)
            .ToListAsync();

        var actionItems = pendingGrants.Select(g => new DashboardActionItemDto
        {
            EntityTitle = g.Organisation?.CompanyName ?? g.ProjectTitle,
            ReferenceNumber = string.IsNullOrWhiteSpace(g.ApplicationNumber) ? $"DG-2026-{g.Id:D4}" : g.ApplicationNumber,
            ProcessName = "Discretionary Grant",
            CurrentStage = g.ApplicationStatusCode ?? "Pending",
            DaysRemaining = Math.Max(-5, (int)(g.CreatedAt.AddDays(14) - DateTime.UtcNow).TotalDays),
            IsDueSoon = (g.CreatedAt.AddDays(14) - DateTime.UtcNow).TotalDays <= 3 && (g.CreatedAt.AddDays(14) - DateTime.UtcNow).TotalDays >= 0,
            IsOverdue = (g.CreatedAt.AddDays(14) - DateTime.UtcNow).TotalDays < 0,
            TargetUrl = $"/dg-grants/{g.Id}"
        }).ToList();

        return new ExecutiveDashboardSummaryDto
        {
            EmployerCount = empCount,
            LearnerCount = lrnCount,
            ProviderCount = provCount,
            GrantCount = grantCount,
            ActionItems = actionItems
        };
    }
}
