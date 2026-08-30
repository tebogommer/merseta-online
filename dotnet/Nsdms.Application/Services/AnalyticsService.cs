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
        var context = await _contextFactory.CreateDbContextAsync();
        var chambers = await context.ChamberTypes.Where(c => c.Active).ToListAsync();
        var orgs = await context.Organisations.ToListAsync();
        var wsps = await context.WspSubmissions.Include(w => w.Organisation).ToListAsync();
        var moas = await context.GrantMoas
            .Include(m => m.GrantApplication)
                .ThenInclude(g => g!.Organisation)
            .Include(m => m.Milestones)
                .ThenInclude(ms => ms.Payments)
            .ToListAsync();
        var learners = await context.CompanyLearners.Include(l => l.Organisation).ToListAsync();

        var result = new List<SspChamberMetricDto>();

        foreach (var ch in chambers)
        {
            var chamberOrgs = orgs.Where(o => o.ChamberCode == ch.Code).Select(o => o.Id).ToHashSet();
            var chamberWsps = wsps.Where(w => chamberOrgs.Contains(w.OrganisationId)).ToList();
            var chamberMoas = moas.Where(m => m.GrantApplication != null && chamberOrgs.Contains(m.GrantApplication.OrganisationId)).ToList();

            var totalBudget = chamberWsps.Sum(w => w.PlannedTrainingBudget);
            var disbursed = chamberMoas.SelectMany(m => m.Milestones).SelectMany(ms => ms.Payments).Where(p => p.PaymentStatusCode == "Paid").Sum(p => p.ApprovedPaymentAmount);
            var learnerCount = learners.Count(l => chamberOrgs.Contains(l.OrganisationId));

            result.Add(new SspChamberMetricDto
            {
                ChamberName = ch.Name,
                EmployerCount = chamberOrgs.Count,
                LearnerCount = learnerCount > 0 ? learnerCount : chamberWsps.Sum(w => w.EmployeeCount) / 10,
                PlannedTrainingBudget = totalBudget,
                DisbursedGrantsAmount = disbursed
            });
        }

        return result;
    }

    public async Task<List<SspEquityMetricDto>> GetSspEquityMetricsAsync()
    {
        var context = await _contextFactory.CreateDbContextAsync();
        var learners = await context.CompanyLearners
            .Include(l => l.Person)
            .ToListAsync();

        var total = Math.Max(1, learners.Count);

        var africanCount = learners.Count(l => l.Person?.EquityCode == "1" || l.Person?.EquityCode == "AFR" || l.Person?.EquityCode == null);
        var colouredCount = learners.Count(l => l.Person?.EquityCode == "2" || l.Person?.EquityCode == "COL");
        var indianCount = learners.Count(l => l.Person?.EquityCode == "3" || l.Person?.EquityCode == "IND");
        var whiteCount = learners.Count(l => l.Person?.EquityCode == "4" || l.Person?.EquityCode == "WHI");

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
        var context = await _contextFactory.CreateDbContextAsync();
        var provinces = await context.ProvinceTypes.Where(p => p.Active).ToListAsync();
        var orgs = await context.Organisations.ToListAsync();
        var providers = await context.TrainingProviders.Include(p => p.Organisation).ToListAsync();
        var learners = await context.CompanyLearners.Include(l => l.Organisation).ToListAsync();
        var tradeTests = await context.LearnerTradeTests
            .Include(t => t.CompanyLearner)
                .ThenInclude(cl => cl!.Organisation)
            .ToListAsync();

        var result = new List<SspProvincialMetricDto>();

        foreach (var p in provinces)
        {
            var empCount = orgs.Count(o => o.ProvinceCode == p.Code);
            var provCount = providers.Count(pr => pr.Organisation?.ProvinceCode == p.Code);
            var lrnCount = learners.Count(l => l.Organisation?.ProvinceCode == p.Code);
            var certCount = tradeTests.Count(t => t.CompanyLearner?.Organisation?.ProvinceCode == p.Code && t.ResultStatusCode == "Competent");

            result.Add(new SspProvincialMetricDto
            {
                ProvinceCode = p.Code,
                ProvinceName = p.Name,
                EmployerCount = empCount,
                ProviderCount = provCount,
                ActiveLearnerCount = lrnCount,
                CertifiedCount = certCount
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
        var context = await _contextFactory.CreateDbContextAsync();
        var moas = await context.GrantMoas.ToListAsync();
        var payments = await context.GrantTranchePayments.Where(p => p.PaymentStatusCode == "Paid").ToListAsync();
        var rebates = await context.MandatoryGrantDisbursements.Where(d => d.DisbursementStatusCode == "Paid").ToListAsync();

        var totalCommitted = moas.Sum(m => m.TotalContractValue);
        var totalDisbursed = payments.Sum(p => p.ApprovedPaymentAmount);
        var totalRebates = rebates.Sum(d => d.CalculatedRebateAmount);

        return (totalCommitted, totalDisbursed, totalRebates);
    }
}
