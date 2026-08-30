using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Validation;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

/// <summary>
/// Comprehensive batch pre-submission validation engine implementing all statutory rules
/// for DHET SETMIS (Files 100 to 505) and SAQA NLRD (Files 21 to 30 / Edu.Dex).
/// </summary>
public class StatutoryValidationService : IStatutoryValidationService
{
    private readonly INsdmsDbContextFactory _dbContextFactory;

    public StatutoryValidationService(INsdmsDbContextFactory dbContextFactory)
    {
        _dbContextFactory = dbContextFactory;
    }

    public async Task<StatutoryValidationReport> ValidateSetmisSubmissionBatchAsync(CancellationToken cancellationToken = default)
    {
        using var context = _dbContextFactory.CreateDbContext();
        var report = new StatutoryValidationReport
        {
            SpecificationStandard = "DHET SETMIS (Version 1.01)",
            EvaluationTimestamp = DateTime.UtcNow
        };

        // 1. Audit Providers (File 100)
        var providers = await context.TrainingProviders
            .AsNoTracking()
            .ToListAsync(cancellationToken);
        report.FileCounts["SETMIS_100 (Provider)"] = providers.Count;
        foreach (var p in providers)
        {
            report.Errors.AddRange(TrainingProviderDomainValidator.Validate(p, "SETMIS_100"));
        }

        // 2. Audit Employers / Organisations (File 200)
        var orgs = await context.Organisations
            .AsNoTracking()
            .ToListAsync(cancellationToken);
        report.FileCounts["SETMIS_200 (Organisation)"] = orgs.Count;
        foreach (var o in orgs)
        {
            report.Errors.AddRange(OrganisationDomainValidator.Validate(o, "SETMIS_200"));
        }

        // 3. Audit Persons (File 400)
        var persons = await context.People
            .AsNoTracking()
            .ToListAsync(cancellationToken);
        report.FileCounts["SETMIS_400 (Person)"] = persons.Count;
        foreach (var p in persons)
        {
            report.Errors.AddRange(PersonDomainValidator.Validate(p, "SETMIS_400"));
        }

        // 4. Audit ETQA Assessors & Moderators (File 401)
        var assessors = await context.EtqaAssessors
            .Include(a => a.Scopes)
            .AsNoTracking()
            .ToListAsync(cancellationToken);
        report.FileCounts["SETMIS_401 (Person Designation)"] = assessors.Count;
        foreach (var a in assessors)
        {
            report.Errors.AddRange(EtqaAssessorDomainValidator.Validate(a, "SETMIS_401"));
        }

        // 5. Audit Learner Agreements (Files 500, 501, 502)
        var learners = await context.CompanyLearners
            .Include(l => l.Person)
            .AsNoTracking()
            .ToListAsync(cancellationToken);
        report.FileCounts["SETMIS_500_501 (Learner Agreements)"] = learners.Count;
        foreach (var l in learners)
        {
            report.Errors.AddRange(CompanyLearnerDomainValidator.Validate(l, "SETMIS_500"));
        }

        // 6. Audit Unit Standard Assessments (File 503)
        var assessments = await context.LearnerAssessments
            .AsNoTracking()
            .ToListAsync(cancellationToken);
        report.FileCounts["SETMIS_503 (Unit Standard Assessments)"] = assessments.Count;
        foreach (var a in assessments)
        {
            if (a.UnitStandardId <= 0)
            {
                report.Errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = "SETMIS_503",
                    RecordId = a.Id,
                    EntityName = nameof(LearnerAssessment),
                    RecordDescriptor = $"Assessment #{a.Id}",
                    FieldName = nameof(a.UnitStandardId),
                    FieldValue = a.UnitStandardId.ToString(),
                    RuleCode = "SETMIS_503_02_UNIT_STD_REQUIRED",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = "SAQA Unit Standard ID is mandatory for unit standard achievement records.",
                    Remediation = "Assign a valid SAQA Unit Standard ID."
                });
            }
        }

        // 7. Audit Trade Tests (File 505)
        var tradeTests = await context.LearnerTradeTests
            .AsNoTracking()
            .ToListAsync(cancellationToken);
        report.FileCounts["SETMIS_505 (Trade Tests)"] = tradeTests.Count;
        foreach (var t in tradeTests)
        {
            if (string.IsNullOrWhiteSpace(t.TradeTestResultId))
            {
                report.Errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = "SETMIS_505",
                    RecordId = t.Id,
                    EntityName = nameof(LearnerTradeTest),
                    RecordDescriptor = $"Trade Test (ID: {t.Id})",
                    FieldName = nameof(t.TradeTestResultId),
                    FieldValue = null,
                    RuleCode = "SETMIS_505_07_RESULT_CODE_REQUIRED",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = "Trade Test Result status code is mandatory.",
                    Remediation = "Select result outcome (e.g. 01 Competent, 02 Not Yet Competent)."
                });
            }
        }

        // Calculate Totals
        report.TotalRecordsAudited = report.FileCounts.Values.Sum();
        var fatalRecordIds = report.Errors
            .Where(e => e.Severity == StatutoryValidationSeverity.Fatal)
            .Select(e => $"{e.EntityName}_{e.RecordId}")
            .Distinct()
            .Count();

        report.CompliantRecordsCount = Math.Max(0, report.TotalRecordsAudited - fatalRecordIds);

        return report;
    }

    public async Task<StatutoryValidationReport> ValidateNlrdSubmissionBatchAsync(CancellationToken cancellationToken = default)
    {
        using var context = _dbContextFactory.CreateDbContext();
        var report = new StatutoryValidationReport
        {
            SpecificationStandard = "SAQA NLRD (Release 2 / Edu.Dex)",
            EvaluationTimestamp = DateTime.UtcNow
        };

        // File 21: Providers
        var providers = await context.TrainingProviders.AsNoTracking().ToListAsync(cancellationToken);
        report.FileCounts["NLRD_21 (Providers)"] = providers.Count;
        foreach (var p in providers)
        {
            report.Errors.AddRange(TrainingProviderDomainValidator.Validate(p, "NLRD_21"));
        }

        // File 22: Legacy Qualifications
        var legacyQuals = await context.QualificationsCurriculumDevelopments.AsNoTracking().ToListAsync(cancellationToken);
        report.FileCounts["NLRD_22 (Qualifications)"] = legacyQuals.Count;
        foreach (var q in legacyQuals)
        {
            if (string.IsNullOrWhiteSpace(q.SaqaQualificationId) && string.IsNullOrWhiteSpace(q.QualificationTitle))
            {
                report.Errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = "NLRD_22",
                    RecordId = q.Id,
                    EntityName = nameof(QualificationsCurriculumDevelopment),
                    RecordDescriptor = $"Qual Dev App: {q.ApplicationNumber}",
                    FieldName = nameof(q.QualificationTitle),
                    FieldValue = null,
                    RuleCode = "NLRD_22_01_QUAL_TITLE_REQUIRED",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = "Qualification Title cannot be empty in NLRD File 22.",
                    Remediation = "Capture the SAQA Qualification Title."
                });
            }
        }

        // File 23: Legacy Courses
        var courses = await context.SkillsRegistrations.AsNoTracking().ToListAsync(cancellationToken);
        report.FileCounts["NLRD_23 (Courses)"] = courses.Count;
        foreach (var c in courses)
        {
            if (string.IsNullOrWhiteSpace(c.NonNqfIntervCode))
            {
                report.Errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = "NLRD_23",
                    RecordId = c.Id,
                    EntityName = nameof(SkillsRegistration),
                    RecordDescriptor = $"Skills Prog: {c.NonNqfIntervName}",
                    FieldName = nameof(c.NonNqfIntervCode),
                    FieldValue = null,
                    RuleCode = "NLRD_23_01_COURSE_CODE_REQUIRED",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = "Course / Skills Programme code cannot be empty.",
                    Remediation = "Assign a unique non-NQF intervention code."
                });
            }
        }

        // File 25: Person Information
        var persons = await context.People.AsNoTracking().ToListAsync(cancellationToken);
        report.FileCounts["NLRD_25 (Person Info)"] = persons.Count;
        foreach (var p in persons)
        {
            report.Errors.AddRange(PersonDomainValidator.Validate(p, "NLRD_25"));
        }

        // File 26: Person Designation
        var assessors = await context.EtqaAssessors.AsNoTracking().ToListAsync(cancellationToken);
        report.FileCounts["NLRD_26 (Designations)"] = assessors.Count;
        foreach (var a in assessors)
        {
            report.Errors.AddRange(EtqaAssessorDomainValidator.Validate(a, "NLRD_26"));
        }

        // File 28: Learnership Enrolments
        var learnerships = await context.CompanyLearners
            .Where(l => !string.IsNullOrEmpty(l.LearnershipId))
            .Include(l => l.Person)
            .AsNoTracking()
            .ToListAsync(cancellationToken);
        report.FileCounts["NLRD_28 (Learnerships)"] = learnerships.Count;
        foreach (var l in learnerships)
        {
            report.Errors.AddRange(CompanyLearnerDomainValidator.Validate(l, "NLRD_28"));
        }

        // File 29: Qualification Enrolments
        var qualifications = await context.CompanyLearners
            .Where(l => l.SaqaQualificationId.HasValue && l.SaqaQualificationId > 0)
            .Include(l => l.Person)
            .AsNoTracking()
            .ToListAsync(cancellationToken);
        report.FileCounts["NLRD_29 (Qualifications)"] = qualifications.Count;
        foreach (var l in qualifications)
        {
            report.Errors.AddRange(CompanyLearnerDomainValidator.Validate(l, "NLRD_29"));
        }

        // File 30: Unit Standard Enrolments
        var unitStandards = await context.LearnerAssessments.AsNoTracking().ToListAsync(cancellationToken);
        report.FileCounts["NLRD_30 (Unit Standards)"] = unitStandards.Count;
        foreach (var u in unitStandards)
        {
            if (u.UnitStandardId <= 0)
            {
                report.Errors.Add(new StatutoryValidationError
                {
                    FileIdentifier = "NLRD_30",
                    RecordId = u.Id,
                    EntityName = nameof(LearnerAssessment),
                    RecordDescriptor = $"Unit Standard #{u.Id}",
                    FieldName = nameof(u.UnitStandardId),
                    FieldValue = u.UnitStandardId.ToString(),
                    RuleCode = "NLRD_30_02_UNIT_STD_REQUIRED",
                    Severity = StatutoryValidationSeverity.Fatal,
                    Message = "Unit Standard ID must be greater than 0.",
                    Remediation = "Link valid SAQA Unit Standard ID."
                });
            }
        }

        // Calculate Totals
        report.TotalRecordsAudited = report.FileCounts.Values.Sum();
        var fatalRecordIds = report.Errors
            .Where(e => e.Severity == StatutoryValidationSeverity.Fatal)
            .Select(e => $"{e.EntityName}_{e.RecordId}")
            .Distinct()
            .Count();

        report.CompliantRecordsCount = Math.Max(0, report.TotalRecordsAudited - fatalRecordIds);

        return report;
    }
}
