using System.Security.Cryptography;
using System.Text;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class SummativeAssessmentAndModerationService : ISummativeAssessmentAndModerationService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly AuditService _audit;
    private readonly INotificationService? _notificationService;

    public SummativeAssessmentAndModerationService(
        INsdmsDbContextFactory contextFactory, 
        AuditService audit,
        INotificationService? notificationService = null)
    {
        _contextFactory = contextFactory;
        _audit = audit;
        _notificationService = notificationService;
    }

    public async Task<SummativeAssessmentReport> CreateSummativeAssessmentReportAsync(
        int companyLearnerId,
        string qualificationTitle,
        string? saqaQualId,
        int nqfLevel,
        string interventionTypeCode = "Learnership",
        string assessmentStageCode = "Completion",
        bool isFundedEmployer = true,
        int totalCreditsRequired = 120,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var learner = await db.CompanyLearners
            .Include(l => l.Person)
            .Include(l => l.Organisation)
            .Include(l => l.TrainingProvider)
            .FirstOrDefaultAsync(l => l.Id == companyLearnerId);

        if (learner == null)
        {
            throw new KeyNotFoundException($"CompanyLearner with ID {companyLearnerId} not found.");
        }

        var report = new SummativeAssessmentReport
        {
            CompanyLearnerId = companyLearnerId,
            PersonId = learner.PersonId,
            OrganisationId = learner.OrganisationId,
            TrainingProviderId = learner.TrainingProviderId,
            ReportNumber = $"SOR-REP-{DateTime.UtcNow.Year}-{Guid.NewGuid().ToString("N")[..6].ToUpper()}",
            QualificationTitle = qualificationTitle,
            SaqaQualificationId = saqaQualId,
            NqfLevel = nqfLevel,
            InterventionTypeCode = interventionTypeCode,
            AssessmentStageCode = assessmentStageCode,
            IsFundedEmployer = isFundedEmployer,
            TotalCreditsRequired = totalCreditsRequired,
            AssessmentDate = DateTime.UtcNow,
            StatusCode = "DraftHoldingRoom",
            CreditComplianceMet = false,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.SummativeAssessmentReports.Add(report);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "SummativeAssessmentReport", report.Id, "CreateSummativeAssessmentReport", currentUsername, null, report);
        await db.SaveChangesAsync();

        return report;
    }
    public async Task<SummativeAssessmentReport> RecordUnitStandardCreditsAsync(
        int reportId,
        int assessorPersonId,
        string assessorRegNumber,
        List<SummativeAssessmentUnitStandard> unitStandards,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var report = await db.SummativeAssessmentReports
            .Include(r => r.UnitStandardAssessments)
            .FirstOrDefaultAsync(r => r.Id == reportId);

        if (report == null)
        {
            throw new KeyNotFoundException($"SummativeAssessmentReport with ID {reportId} not found.");
        }

        if (report.InternalModeratorPersonId.HasValue && report.InternalModeratorPersonId.Value == assessorPersonId)
        {
            throw new InvalidOperationException("Maker-Checker Violation: An assessor cannot be assigned as the internal moderator on the same assessment.");
        }

        var assessor = await db.EtqaAssessors.FirstOrDefaultAsync(a => a.PersonId == assessorPersonId);
        if (assessor != null)
        {
            if (assessor.AssessmentAbilitySuspended)
            {
                throw new InvalidOperationException($"Assessor {assessor.RegistrationNumber} assessment abilities have been suspended.");
            }
            if (report.AssessmentDate < assessor.StartDate || (assessor.EndDate != default && report.AssessmentDate > assessor.EndDate))
            {
                throw new InvalidOperationException($"Assessment Date ({report.AssessmentDate:yyyy-MM-dd}) falls outside the assessor's valid accreditation window ({assessor.StartDate:yyyy-MM-dd} to {assessor.EndDate:yyyy-MM-dd}).");
            }
        }

        int totalEarned = 0;
        int mandatoryEarned = 0;
        int mandatoryRequired = 0;

        foreach (var us in unitStandards)
        {
            var existing = report.UnitStandardAssessments.FirstOrDefault(e => e.UnitStandardCode == us.UnitStandardCode);
            if (existing != null)
            {
                existing.CompetencyStatusCode = us.CompetencyStatusCode;
                existing.ScoreAchieved = us.ScoreAchieved;
                existing.AssessorPersonId = assessorPersonId;
                existing.UnitStandardTypeCode = us.UnitStandardTypeCode;
                existing.IsMandatory = us.IsMandatory;
                existing.IsNonMandatoryElective = us.IsNonMandatoryElective;
                existing.ModifiedAt = DateTime.UtcNow;
                existing.ModifiedBy = currentUsername;

                if (existing.IsMandatory || existing.UnitStandardTypeCode == "Core" || existing.UnitStandardTypeCode == "Fundamental")
                {
                    mandatoryRequired += existing.Credits;
                    if (existing.CompetencyStatusCode == "Competent")
                    {
                        mandatoryEarned += existing.Credits;
                    }
                }

                if (existing.CompetencyStatusCode == "Competent")
                {
                    totalEarned += existing.Credits;
                }
            }
            else
            {
                us.SummativeAssessmentReportId = reportId;
                us.AssessorPersonId = assessorPersonId;
                us.CreatedAt = DateTime.UtcNow;
                us.CreatedBy = currentUsername;

                if (us.IsMandatory || us.UnitStandardTypeCode == "Core" || us.UnitStandardTypeCode == "Fundamental")
                {
                    mandatoryRequired += us.Credits;
                    if (us.CompetencyStatusCode == "Competent")
                    {
                        mandatoryEarned += us.Credits;
                    }
                }

                if (us.CompetencyStatusCode == "Competent")
                {
                    totalEarned += us.Credits;
                }
                db.SummativeAssessmentUnitStandards.Add(us);
            }
        }

        if (report.AssessmentStageCode == "Progress" && report.IsFundedEmployer)
        {
            int min50Pct = report.TotalCreditsRequired / 2;
            if (totalEarned < min50Pct)
            {
                throw new InvalidOperationException($"Progress Moderation Violation: For MerSETA-funded programmes, minimum 50% credits ({min50Pct} credits) must be completed before submission. Current: {totalEarned} credits.");
            }
        }

        bool creditComplianceMet = mandatoryRequired > 0 && mandatoryEarned >= mandatoryRequired;

        var before = new { report.AssessorPersonId, report.TotalCreditsEarned, report.StatusCode, report.CreditComplianceMet };
        report.AssessorPersonId = assessorPersonId;
        report.AssessorRegistrationNumber = assessorRegNumber;
        report.TotalCreditsEarned = totalEarned;
        report.CreditComplianceMet = creditComplianceMet;
        report.StatusCode = "Assessed";
        report.ModifiedAt = DateTime.UtcNow;
        report.ModifiedBy = currentUsername;

        _audit.LogAction(db, "SummativeAssessmentReport", report.Id, "RecordAssessorCredits", currentUsername, before, report);
        await db.SaveChangesAsync();

        return report;
    }
    public async Task<SummativeAssessmentReport> PerformInternalModerationAsync(
        int reportId,
        int moderatorPersonId,
        string moderatorRegNumber,
        Dictionary<int, (string Outcome, string? Comments)> moderationDecisions,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var report = await db.SummativeAssessmentReports
            .Include(r => r.UnitStandardAssessments)
            .FirstOrDefaultAsync(r => r.Id == reportId);

        if (report == null)
        {
            throw new KeyNotFoundException($"SummativeAssessmentReport with ID {reportId} not found.");
        }

        if (report.AssessorPersonId.HasValue && report.AssessorPersonId.Value == moderatorPersonId)
        {
            throw new InvalidOperationException("Segregation of Duties Violation: An assessor cannot internally moderate assessment results they personally evaluated (Use Case 5.0).");
        }

        var moderator = await db.EtqaAssessors.FirstOrDefaultAsync(a => a.PersonId == moderatorPersonId);
        if (moderator != null)
        {
            if (moderator.AssessmentAbilitySuspended)
            {
                throw new InvalidOperationException($"Internal Moderator {moderator.RegistrationNumber} assessment abilities have been suspended.");
            }
            var modDate = DateTime.UtcNow;
            if (modDate < moderator.StartDate || (moderator.EndDate != default && modDate > moderator.EndDate))
            {
                throw new InvalidOperationException($"Moderation Date ({modDate:yyyy-MM-dd}) falls outside the moderator's valid registration period ({moderator.StartDate:yyyy-MM-dd} to {moderator.EndDate:yyyy-MM-dd}).");
            }
        }

        int newEarned = 0;
        foreach (var us in report.UnitStandardAssessments)
        {
            us.InternalModeratorPersonId = moderatorPersonId;
            if (moderationDecisions.TryGetValue(us.Id, out var dec))
            {
                us.IsModerated = true;
                us.ModerationOutcome = dec.Outcome;
                us.ModeratorComments = dec.Comments;

                if (dec.Outcome == "Overturned")
                {
                    us.CompetencyStatusCode = us.CompetencyStatusCode == "Competent" ? "NotYetCompetent" : "Competent";
                }
            }

            if (us.CompetencyStatusCode == "Competent")
            {
                newEarned += us.Credits;
            }
            us.ModifiedAt = DateTime.UtcNow;
            us.ModifiedBy = currentUsername;
        }

        var before = new { report.InternalModeratorPersonId, report.TotalCreditsEarned, report.StatusCode };
        report.InternalModeratorPersonId = moderatorPersonId;
        report.InternalModeratorRegistrationNumber = moderatorRegNumber;
        report.ModerationDate = DateTime.UtcNow;
        report.TotalCreditsEarned = newEarned;
        report.StatusCode = "InternalModerated";
        report.ModifiedAt = DateTime.UtcNow;
        report.ModifiedBy = currentUsername;

        _audit.LogAction(db, "SummativeAssessmentReport", report.Id, "PerformInternalModeration", currentUsername, before, report);
        await db.SaveChangesAsync();

        return report;
    }

    public async Task<List<SummativeAssessmentReport>> GetHoldingRoomReportsAsync(int? providerId = null, string? qualificationTitle = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.SummativeAssessmentReports
            .Include(r => r.Person)
            .Include(r => r.Organisation)
            .Include(r => r.TrainingProvider)
            .Include(r => r.CompanyLearner)
            .Where(r => r.AssessmentBatchId == null && (r.StatusCode == "DraftHoldingRoom" || r.StatusCode == "Assessed" || r.StatusCode == "InternalModerated"))
            .AsQueryable();

        if (providerId.HasValue && providerId.Value > 0)
        {
            query = query.Where(r => r.TrainingProviderId == providerId.Value);
        }

        if (!string.IsNullOrWhiteSpace(qualificationTitle))
        {
            query = query.Where(r => r.QualificationTitle.Contains(qualificationTitle));
        }

        return await query.OrderByDescending(r => r.CreatedAt).ToListAsync();
    }
    public async Task<AssessmentBatch> CreateAssessmentBatchAsync(
        int providerId,
        string qualificationTitle,
        string? saqaId,
        string stageCode,
        int samplePercentage,
        List<int> reportIds,
        string? internalReportDocRef,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        if (!reportIds.Any())
        {
            throw new InvalidOperationException("At least one learner assessment must be selected to create a moderation batch.");
        }

        var reports = await db.SummativeAssessmentReports
            .Include(r => r.Person)
            .Where(r => reportIds.Contains(r.Id))
            .ToListAsync();

        if (reports.Count != reportIds.Count)
        {
            throw new KeyNotFoundException("One or more selected assessment reports could not be found.");
        }

        var batchNumber = $"MOD-BATCH-{DateTime.UtcNow.Year}-{Guid.NewGuid().ToString("N")[..5].ToUpper()}";
        int total = reports.Count;
        int sampledCount = Math.Max(1, (int)Math.Ceiling(total * (samplePercentage / 100.0)));

        var sampledReports = reports.OrderBy(_ => Guid.NewGuid()).Take(sampledCount).ToList();
        var sampledIds = sampledReports.Select(s => s.Id).ToHashSet();

        var batch = new AssessmentBatch
        {
            BatchNumber = batchNumber,
            TrainingProviderId = providerId,
            QualificationTitle = qualificationTitle,
            SaqaQualificationId = saqaId,
            AssessmentStageCode = stageCode,
            SamplePercentage = samplePercentage,
            TotalLearnersCount = total,
            SampledLearnersCount = sampledCount,
            InternalModerationReportDocumentRef = internalReportDocRef,
            LastInternalModerationDate = reports.Max(r => r.ModerationDate) ?? DateTime.UtcNow,
            StatusCode = "InExternalModerationPool",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.AssessmentBatches.Add(batch);
        await db.SaveChangesAsync();

        foreach (var r in reports)
        {
            r.AssessmentBatchId = batch.Id;
            r.StatusCode = "InExternalModerationPool";
            r.ModifiedAt = DateTime.UtcNow;
            r.ModifiedBy = currentUsername;

            var link = new AssessmentBatchLearner
            {
                AssessmentBatchId = batch.Id,
                SummativeAssessmentReportId = r.Id,
                IsSelectedInSample = sampledIds.Contains(r.Id),
                LearnerOutcomeStatus = "Pending",
                CreatedAt = DateTime.UtcNow,
                CreatedBy = currentUsername
            };
            db.AssessmentBatchLearners.Add(link);
        }

        _audit.LogAction(db, "AssessmentBatch", batch.Id, "CreateAssessmentBatch", currentUsername, null, batch);
        await db.SaveChangesAsync();

        if (_notificationService != null)
        {
            await _notificationService.SendNotificationAsync(
                null,
                "QA",
                "New External Moderation Batch Ready",
                $"Moderation batch {batch.BatchNumber} for {qualificationTitle} ({total} learners, sample: {sampledCount}) submitted for verification.",
                $"/assessments/qa-workbench/{batch.Id}",
                "WorkflowTask",
                "Info",
                currentUsername);
        }

        return batch;
    }

    public async Task<List<AssessmentBatch>> GetAssessmentBatchesAsync(string? statusCode = null, int? providerId = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.AssessmentBatches
            .Include(b => b.TrainingProvider)
            .Include(b => b.BatchLearners)
                .ThenInclude(bl => bl.SummativeAssessmentReport)
                    .ThenInclude(r => r!.Person)
            .AsQueryable();

        if (!string.IsNullOrWhiteSpace(statusCode))
        {
            query = query.Where(b => b.StatusCode == statusCode);
        }

        if (providerId.HasValue && providerId.Value > 0)
        {
            query = query.Where(b => b.TrainingProviderId == providerId.Value);
        }

        return await query.OrderByDescending(b => b.CreatedAt).ToListAsync();
    }

    public async Task<AssessmentBatch?> GetAssessmentBatchByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.AssessmentBatches
            .Include(b => b.TrainingProvider)
            .Include(b => b.BatchLearners)
                .ThenInclude(bl => bl.SummativeAssessmentReport)
                    .ThenInclude(r => r!.Person)
            .Include(b => b.BatchLearners)
                .ThenInclude(bl => bl.SummativeAssessmentReport)
                    .ThenInclude(r => r!.UnitStandardAssessments)
            .Include(b => b.ModerationChecklists)
                .ThenInclude(m => m.ChecklistItems)
            .FirstOrDefaultAsync(b => b.Id == id);
    }

    public async Task<AssessmentBatch> ScheduleSiteVisitAsync(
        int batchId,
        DateTime visitDate,
        bool isSiteVisitRequired,
        string? comments,
        int? contactPersonId,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var batch = await db.AssessmentBatches
            .Include(b => b.TrainingProvider)
            .FirstOrDefaultAsync(b => b.Id == batchId);

        if (batch == null)
        {
            throw new KeyNotFoundException($"AssessmentBatch with ID {batchId} not found.");
        }

        var before = new { batch.ScheduledSiteVisitDate, batch.IsSiteVisitRequired, batch.StatusCode };
        batch.ScheduledSiteVisitDate = visitDate;
        batch.IsSiteVisitRequired = isSiteVisitRequired;
        batch.SiteVisitSchedulingComments = comments;
        batch.ContactPersonId = contactPersonId;
        batch.AssignedQaUserId = currentUsername;
        batch.StatusCode = "SiteVisitScheduled";
        batch.ModifiedAt = DateTime.UtcNow;
        batch.ModifiedBy = currentUsername;

        _audit.LogAction(db, "AssessmentBatch", batch.Id, "ScheduleSiteVisit", currentUsername, before, batch);
        await db.SaveChangesAsync();

        if (_notificationService != null)
        {
            await _notificationService.SendNotificationAsync(
                null,
                "SDF",
                "External Moderation Audit Scheduled",
                $"A {(isSiteVisitRequired ? "physical on-site visit" : "desktop evaluation")} has been scheduled for Batch {batch.BatchNumber} on {visitDate:yyyy-MM-dd}.",
                $"/assessments/batching/{batch.Id}",
                "SystemAlert",
                "Info",
                currentUsername);
        }

        return batch;
    }
    public async Task<AssessmentBatch> RecordExternalModerationOutcomeAsync(
        int batchId,
        bool isUpheld,
        string? primaryRejectionReason,
        string? vacsViolation,
        string? remarks,
        string? remedialAction,
        List<ModerationChecklistItemDto>? checklistItems,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        Microsoft.EntityFrameworkCore.Storage.IDbContextTransaction? transaction = null;
        if (db.Database.IsRelational())
        {
            transaction = await db.Database.BeginTransactionAsync();
        }

        var batch = await db.AssessmentBatches
            .Include(b => b.BatchLearners)
                .ThenInclude(bl => bl.SummativeAssessmentReport)
                    .ThenInclude(r => r!.CompanyLearner)
            .Include(b => b.BatchLearners)
                .ThenInclude(bl => bl.SummativeAssessmentReport)
                    .ThenInclude(r => r!.Person)
            .Include(b => b.BatchLearners)
                .ThenInclude(bl => bl.SummativeAssessmentReport)
                    .ThenInclude(r => r!.UnitStandardAssessments)
            .FirstOrDefaultAsync(b => b.Id == batchId);

        if (batch == null)
        {
            throw new KeyNotFoundException($"AssessmentBatch with ID {batchId} not found.");
        }

        var checklist = new ModerationChecklistEtqTp043
        {
            AssessmentBatchId = batch.Id,
            ValidationBatchNumber = batch.BatchNumber,
            QualityAssurorUserId = currentUsername,
            DateOfModeration = DateTime.UtcNow,
            StageOfModerationCode = batch.AssessmentStageCode,
            ValidationDecisionCode = isUpheld ? "Upheld" : "Rejected",
            PrimaryRejectionReasonCode = primaryRejectionReason,
            VacsPrincipleViolatedCode = vacsViolation,
            RejectionRemarks = remarks,
            RemedialActionRequired = remedialAction,
            TamperProofHashSha256 = Convert.ToHexString(SHA256.HashData(Encoding.UTF8.GetBytes($"{batch.BatchNumber}:{isUpheld}:{DateTime.UtcNow:O}"))).ToLowerInvariant(),
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        if (checklistItems != null)
        {
            foreach (var item in checklistItems)
            {
                checklist.ChecklistItems.Add(new ModerationChecklistItem
                {
                    SectionNumber = item.SectionNumber,
                    CriteriaTitle = item.CriteriaTitle,
                    EvidenceRequirements = item.EvidenceRequirements,
                    IsCompliant = item.IsCompliant,
                    Comments = item.Comments,
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = currentUsername
                });
            }
        }
        db.ModerationChecklists.Add(checklist);

        if (isUpheld)
        {
            batch.StatusCode = "Upheld";
            foreach (var bl in batch.BatchLearners)
            {
                bl.LearnerOutcomeStatus = "Upheld";
                bl.ModifiedAt = DateTime.UtcNow;
                bl.ModifiedBy = currentUsername;

                var rep = bl.SummativeAssessmentReport;
                if (rep != null)
                {
                    rep.StatusCode = "CreditsApproved";
                    rep.ExternalModeratorUserId = currentUsername;
                    rep.ExternalModeratorApprovalDate = DateTime.UtcNow;
                    rep.ExternalModeratorComments = remarks;
                    rep.ModifiedAt = DateTime.UtcNow;
                    rep.ModifiedBy = currentUsername;

                    if (batch.AssessmentStageCode == "Completion")
                    {
                        if (rep.CompanyLearner != null)
                        {
                            rep.CompanyLearner.EnrolmentStatusId = "02";
                            rep.CompanyLearner.EnrolmentStatusCode = "Completed";
                            rep.CompanyLearner.CompletionDate = batch.ScheduledSiteVisitDate ?? DateTime.UtcNow;

                            var certNo = StatutoryCertificateNumberGenerator.Generate(rep.Person?.RsaIdNumber, rep.Person?.DateOfBirth);
                            rep.CompanyLearner.CertificateNumber = certNo;

                            var cert = new LearnerCertificate
                            {
                                CompanyLearnerId = rep.CompanyLearnerId,
                                PersonId = rep.PersonId,
                                SummativeAssessmentReportId = rep.Id,
                                CertificateNumber = certNo,
                                QualificationTitle = rep.QualificationTitle,
                                SaqaQualificationId = rep.SaqaQualificationId,
                                NqfLevel = rep.NqfLevel,
                                IssueDate = DateTime.UtcNow,
                                TamperProofHashSha256 = Convert.ToHexString(SHA256.HashData(Encoding.UTF8.GetBytes($"{certNo}:{rep.PersonId}:{DateTime.UtcNow:O}"))).ToLowerInvariant(),
                                CreatedAt = DateTime.UtcNow,
                                CreatedBy = currentUsername
                            };
                            db.LearnerCertificates.Add(cert);
                        }

                        foreach (var us in rep.UnitStandardAssessments.Where(u => u.CompetencyStatusCode == "Competent"))
                        {
                            int? parsedUsId = int.TryParse(us.UnitStandardCode, out var parsed) ? parsed : null;
                            db.LearnerAssessments.Add(new LearnerAssessment
                            {
                                CompanyLearnerId = rep.CompanyLearnerId,
                                PersonId = rep.PersonId,
                                UnitStandardId = parsedUsId,
                                UnitStandardTitle = us.UnitStandardTitle,
                                QualificationTitle = rep.QualificationTitle ?? string.Empty,
                                OrganisationId = rep.CompanyLearner?.OrganisationId ?? 1,
                                AssessmentDate = us.AssessmentDate,
                                EnrolmentStatusId = "02",
                                AssessorRegistrationNumber = rep.AssessorRegistrationNumber ?? "SYSTEM",
                                AssessorEtqaId = "17",
                                CreatedAt = DateTime.UtcNow,
                                CreatedBy = currentUsername
                            });
                        }
                    }
                }
            }

            if (_notificationService != null)
            {
                await _notificationService.SendNotificationAsync(
                    null,
                    "SDF",
                    "Assessment & Moderation Outcomes Upheld",
                    $"Moderation outcomes for Batch {batch.BatchNumber} have been officially Upheld. Qualifications and Statements of Results are ready.",
                    $"/assessments/batching/{batch.Id}",
                    "SystemAlert",
                    "Success",
                    currentUsername);
            }
        }
        else
        {
            batch.StatusCode = "RejectedRemedialRequired";
            foreach (var bl in batch.BatchLearners)
            {
                bl.LearnerOutcomeStatus = "Rejected";
                bl.RejectionReasonCodes = $"{primaryRejectionReason}:{vacsViolation}";
                bl.ModifiedAt = DateTime.UtcNow;
                bl.ModifiedBy = currentUsername;

                if (bl.SummativeAssessmentReport != null)
                {
                    bl.SummativeAssessmentReport.StatusCode = "RejectedRemedialRequired";
                    bl.SummativeAssessmentReport.ModifiedAt = DateTime.UtcNow;
                    bl.SummativeAssessmentReport.ModifiedBy = currentUsername;
                }
            }

            if (_notificationService != null)
            {
                await _notificationService.SendNotificationAsync(
                    null,
                    "SDF",
                    "Assessment Moderation Rejected — Remedial Work Required",
                    $"Batch {batch.BatchNumber} rejected. Reason: {primaryRejectionReason} ({vacsViolation}). Remedial Action: {remedialAction}",
                    $"/assessments/batching/{batch.Id}",
                    "SystemAlert",
                    "Error",
                    currentUsername);
            }
        }

        batch.ModifiedAt = DateTime.UtcNow;
        batch.ModifiedBy = currentUsername;
        _audit.LogAction(db, "AssessmentBatch", batch.Id, "RecordExternalModerationOutcome", currentUsername, null, batch);
        await db.SaveChangesAsync();

        if (transaction != null)
        {
            await transaction.CommitAsync();
            await transaction.DisposeAsync();
        }

        return batch;
    }
    public async Task<SummativeAssessmentReport> PerformEtqaExternalModerationAsync(
        int reportId,
        bool isApproved,
        string? externalModeratorComments,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var report = await db.SummativeAssessmentReports.FirstOrDefaultAsync(r => r.Id == reportId);
        if (report == null)
        {
            throw new KeyNotFoundException($"SummativeAssessmentReport with ID {reportId} not found.");
        }

        var before = new { report.StatusCode, report.ExternalModeratorApprovalDate };
        report.ExternalModeratorUserId = currentUsername;
        report.ExternalModeratorApprovalDate = DateTime.UtcNow;
        report.ExternalModeratorComments = externalModeratorComments;
        report.StatusCode = isApproved ? "CreditsApproved" : "Rejected";
        report.ModifiedAt = DateTime.UtcNow;
        report.ModifiedBy = currentUsername;

        _audit.LogAction(db, "SummativeAssessmentReport", report.Id, "PerformEtqaExternalModeration", currentUsername, before, report);
        await db.SaveChangesAsync();

        return report;
    }

    public async Task<EisaAssessmentEntry> RecordEisaExamEntryAsync(
        int reportId,
        DateTime examDate,
        string centerName,
        string paperCode,
        decimal scoreAchieved,
        decimal totalScorePossible,
        string? qctoRefNumber,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var report = await db.SummativeAssessmentReports.FirstOrDefaultAsync(r => r.Id == reportId);
        if (report == null)
        {
            throw new KeyNotFoundException($"SummativeAssessmentReport with ID {reportId} not found.");
        }

        decimal pct = totalScorePossible > 0 ? Math.Round((scoreAchieved / totalScorePossible) * 100m, 2) : 0m;
        string outcome = pct >= 60m ? "Competent" : "NotYetCompetent";

        var eisa = new EisaAssessmentEntry
        {
            SummativeAssessmentReportId = reportId,
            EisaAssessmentDate = examDate,
            EisaCenterName = centerName,
            AssessmentPaperCode = paperCode,
            ScoreAchieved = scoreAchieved,
            TotalScorePossible = totalScorePossible,
            PercentageScore = pct,
            CompetencyStatusCode = outcome,
            QctoModerationReferenceNumber = qctoRefNumber ?? $"QCTO-{DateTime.UtcNow.Year}-{Guid.NewGuid().ToString("N")[..6].ToUpper()}",
            QctoSignOffDate = DateTime.UtcNow,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.EisaAssessmentEntries.Add(eisa);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "SummativeAssessmentReport", report.Id, "RecordEisaExamEntry", currentUsername, null, eisa);
        await db.SaveChangesAsync();

        return eisa;
    }

    public async Task<StatementOfResults> IssueStatementOfResultsAsync(
        int reportId,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var report = await db.SummativeAssessmentReports
            .Include(r => r.CompanyLearner)
            .Include(r => r.Person)
            .FirstOrDefaultAsync(r => r.Id == reportId);

        if (report == null)
        {
            throw new KeyNotFoundException($"SummativeAssessmentReport with ID {reportId} not found.");
        }

        if (report.StatusCode != "CreditsApproved")
        {
            throw new InvalidOperationException("Cannot issue Statement of Results before ETQA external moderation approval.");
        }

        var existingSor = await db.StatementOfResults.FirstOrDefaultAsync(s => s.SummativeAssessmentReportId == reportId);
        if (existingSor != null)
        {
            return existingSor;
        }

        var serialNumber = $"SOR-{DateTime.UtcNow.Year}-{Guid.NewGuid().ToString("N")[..8].ToUpper()}";
        var rawPayload = $"{serialNumber}:{report.CompanyLearnerId}:{report.PersonId}:{report.TotalCreditsEarned}:{DateTime.UtcNow:O}";
        var hashBytes = SHA256.HashData(Encoding.UTF8.GetBytes(rawPayload));
        var tamperHash = Convert.ToHexString(hashBytes).ToLowerInvariant();

        var sor = new StatementOfResults
        {
            SummativeAssessmentReportId = reportId,
            CompanyLearnerId = report.CompanyLearnerId,
            PersonId = report.PersonId,
            SorSerialNumber = serialNumber,
            DateIssued = DateTime.UtcNow,
            TotalCreditsCertified = report.TotalCreditsEarned,
            AchievementTypeCode = "FullAchievement",
            TamperProofHashSha256 = tamperHash,
            QrVerificationUrl = $"https://verify.merseta.org.za/sor/{tamperHash}",
            IssuedByUserId = currentUsername,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.StatementOfResults.Add(sor);

        var before = new { report.StatusCode };
        report.StatusCode = "SorIssued";
        report.ModifiedAt = DateTime.UtcNow;
        report.ModifiedBy = currentUsername;

        _audit.LogAction(db, "SummativeAssessmentReport", report.Id, "IssueStatementOfResults", currentUsername, before, sor);
        await db.SaveChangesAsync();

        return sor;
    }

    public async Task<StatementOfResults> IssueEarlyExitStatementOfResultsAsync(
        int reportId,
        string earlyExitReason,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var report = await db.SummativeAssessmentReports
            .Include(r => r.CompanyLearner)
            .Include(r => r.Person)
            .FirstOrDefaultAsync(r => r.Id == reportId);

        if (report == null)
        {
            throw new KeyNotFoundException($"SummativeAssessmentReport with ID {reportId} not found.");
        }

        var serialNumber = $"SOR-PARTIAL-{DateTime.UtcNow.Year}-{Guid.NewGuid().ToString("N")[..8].ToUpper()}";
        var rawPayload = $"{serialNumber}:{report.CompanyLearnerId}:{report.PersonId}:{report.TotalCreditsEarned}:{earlyExitReason}:{DateTime.UtcNow:O}";
        var hashBytes = SHA256.HashData(Encoding.UTF8.GetBytes(rawPayload));
        var tamperHash = Convert.ToHexString(hashBytes).ToLowerInvariant();

        var sor = new StatementOfResults
        {
            SummativeAssessmentReportId = reportId,
            CompanyLearnerId = report.CompanyLearnerId,
            PersonId = report.PersonId,
            SorSerialNumber = serialNumber,
            DateIssued = DateTime.UtcNow,
            TotalCreditsCertified = report.TotalCreditsEarned,
            AchievementTypeCode = "PartialAchievement",
            EarlyExitReasonCode = earlyExitReason,
            TamperProofHashSha256 = tamperHash,
            QrVerificationUrl = $"https://verify.merseta.org.za/sor/{tamperHash}",
            IssuedByUserId = currentUsername,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.StatementOfResults.Add(sor);
        report.EarlyExitReasonCode = earlyExitReason;
        report.StatusCode = "SorIssued";
        report.ModifiedAt = DateTime.UtcNow;
        report.ModifiedBy = currentUsername;

        _audit.LogAction(db, "SummativeAssessmentReport", report.Id, "IssueEarlyExitStatementOfResults", currentUsername, null, sor);
        await db.SaveChangesAsync();

        return sor;
    }
    public async Task<CertificatePrintingBatch> CreateCertificatePrintingBatchAsync(
        List<int> batchIds,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var batches = await db.AssessmentBatches
            .Include(b => b.TrainingProvider)
            .Include(b => b.BatchLearners)
                .ThenInclude(bl => bl.SummativeAssessmentReport)
                    .ThenInclude(r => r!.CompanyLearner)
            .Where(b => batchIds.Contains(b.Id) && b.StatusCode == "Upheld")
            .ToListAsync();

        if (!batches.Any())
        {
            throw new InvalidOperationException("No Upheld batches found matching the selected batch IDs.");
        }

        var reportIds = batches.SelectMany(b => b.BatchLearners).Select(bl => bl.SummativeAssessmentReportId).Distinct().ToList();

        var certificates = await db.LearnerCertificates
            .Where(c => reportIds.Contains(c.SummativeAssessmentReportId) && c.CertificatePrintingBatchId == null)
            .ToListAsync();

        if (!certificates.Any())
        {
            throw new InvalidOperationException("No unprinted certificates found for the selected batches.");
        }

        var printBatchNo = $"CPB-{DateTime.UtcNow.Year}-{Guid.NewGuid().ToString("N")[..5].ToUpper()}";
        var printBatch = new CertificatePrintingBatch
        {
            PrintingBatchNumber = printBatchNo,
            BatchGeneratedDate = DateTime.UtcNow,
            TotalCertificatesCount = certificates.Count,
            StatusCode = "QueuedForPrinting",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.CertificatePrintingBatches.Add(printBatch);
        await db.SaveChangesAsync();

        foreach (var cert in certificates)
        {
            cert.CertificatePrintingBatchId = printBatch.Id;
            cert.ModifiedAt = DateTime.UtcNow;
            cert.ModifiedBy = currentUsername;
        }

        var providers = batches.Select(b => b.TrainingProvider).Where(p => p != null).DistinctBy(p => p!.AccreditationNumber).ToList();
        foreach (var p in providers)
        {
            var letter = new DistributionLetter
            {
                CertificatePrintingBatchId = printBatch.Id,
                TrainingProviderId = p!.Id,
                ProviderAccreditationNumber = p.AccreditationNumber ?? "ACCR-TBD",
                LetterReferenceNumber = $"REL-{DateTime.UtcNow.Year}-{Guid.NewGuid().ToString("N")[..6].ToUpper()}",
                GeneratedDate = DateTime.UtcNow,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = currentUsername
            };
            db.DistributionLetters.Add(letter);
        }

        _audit.LogAction(db, "CertificatePrintingBatch", printBatch.Id, "CreateCertificatePrintingBatch", currentUsername, null, printBatch);
        await db.SaveChangesAsync();

        if (_notificationService != null)
        {
            await _notificationService.SendNotificationAsync(
                null,
                "CertificationOfficer",
                "Certificate Printing Task Ready",
                $"Printing Batch {printBatch.PrintingBatchNumber} ({certificates.Count} certificates) queued for printing and release letter distribution.",
                $"/assessments/printing-hub/{printBatch.Id}",
                "WorkflowTask",
                "Info",
                currentUsername);
        }

        return printBatch;
    }

    public async Task<List<CertificatePrintingBatch>> GetCertificatePrintingBatchesAsync()
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.CertificatePrintingBatches
            .Include(b => b.Certificates)
                .ThenInclude(c => c.Person)
            .Include(b => b.DistributionLetters)
                .ThenInclude(d => d.TrainingProvider)
            .OrderByDescending(b => b.CreatedAt)
            .ToListAsync();
    }

    public async Task<CertificatePrintingBatch?> GetCertificatePrintingBatchByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.CertificatePrintingBatches
            .Include(b => b.Certificates)
                .ThenInclude(c => c.Person)
            .Include(b => b.Certificates)
                .ThenInclude(c => c.DistributionEvents)
            .Include(b => b.DistributionLetters)
                .ThenInclude(d => d.TrainingProvider)
            .FirstOrDefaultAsync(b => b.Id == id);
    }

    public async Task<AssessmentCertificateDistributionEvent> LogCertificateDistributionAsync(
        int certificateId,
        string method,
        string? waybill,
        string recipientName,
        string? recipientId,
        string? notes,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var cert = await db.LearnerCertificates.FirstOrDefaultAsync(c => c.Id == certificateId);
        if (cert == null)
        {
            throw new KeyNotFoundException($"LearnerCertificate with ID {certificateId} not found.");
        }

        var dist = new AssessmentCertificateDistributionEvent
        {
            LearnerCertificateId = certificateId,
            DistributionMethodCode = method,
            WaybillOrTrackingNumber = waybill,
            DispatchedDate = DateTime.UtcNow,
            RecipientName = recipientName,
            RecipientIdNumber = recipientId,
            DispatchNotes = notes,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.AssessmentCertificateDistributionEvents.Add(dist);
        _audit.LogAction(db, "LearnerCertificate", cert.Id, "LogCertificateDistribution", currentUsername, null, dist);
        await db.SaveChangesAsync();

        return dist;
    }
    public async Task<ScannedCertificateAttachment> AttachScannedCertificateAsync(
        int certificateId,
        string storageKey,
        string fileName,
        long fileSize,
        string? ocrId,
        string? ocrCertNo,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var cert = await db.LearnerCertificates
            .Include(c => c.Person)
            .FirstOrDefaultAsync(c => c.Id == certificateId);

        if (cert == null)
        {
            throw new KeyNotFoundException($"LearnerCertificate with ID {certificateId} not found.");
        }

        bool match = (!string.IsNullOrWhiteSpace(ocrCertNo) && ocrCertNo == cert.CertificateNumber) ||
                     (!string.IsNullOrWhiteSpace(ocrId) && ocrId == cert.Person?.RsaIdNumber);

        var attachment = new ScannedCertificateAttachment
        {
            LearnerCertificateId = certificateId,
            PersonId = cert.PersonId,
            DocumentStorageKey = storageKey,
            FileName = fileName,
            FileSizeBytes = fileSize,
            ScannedByUserId = currentUsername,
            ScannedAt = DateTime.UtcNow,
            OcrExtractedIdNumber = ocrId,
            OcrExtractedCertificateNumber = ocrCertNo,
            IsVerifiedMatch = match,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.ScannedCertificateAttachments.Add(attachment);
        _audit.LogAction(db, "LearnerCertificate", cert.Id, "AttachScannedCertificate", currentUsername, null, attachment);
        await db.SaveChangesAsync();

        return attachment;
    }

    public async Task<LearnerCertificate> ReissueSpoiledCertificateAsync(
        int certificateId,
        string replacementReason,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var oldCert = await db.LearnerCertificates
            .Include(c => c.Person)
            .FirstOrDefaultAsync(c => c.Id == certificateId);

        if (oldCert == null)
        {
            throw new KeyNotFoundException($"LearnerCertificate with ID {certificateId} not found.");
        }

        oldCert.IsReprintOrReplacement = true;
        oldCert.ReplacementReason = replacementReason;
        oldCert.ModifiedAt = DateTime.UtcNow;
        oldCert.ModifiedBy = currentUsername;

        var newCertNo = StatutoryCertificateNumberGenerator.Generate(oldCert.Person?.RsaIdNumber, oldCert.Person?.DateOfBirth);

        var newCert = new LearnerCertificate
        {
            CompanyLearnerId = oldCert.CompanyLearnerId,
            PersonId = oldCert.PersonId,
            SummativeAssessmentReportId = oldCert.SummativeAssessmentReportId,
            CertificateNumber = newCertNo,
            QualificationTitle = oldCert.QualificationTitle,
            SaqaQualificationId = oldCert.SaqaQualificationId,
            NqfLevel = oldCert.NqfLevel,
            IssueDate = DateTime.UtcNow,
            IsReprintOrReplacement = true,
            ReplacementReason = $"Replaced spoiled certificate {oldCert.CertificateNumber}: {replacementReason}",
            TamperProofHashSha256 = Convert.ToHexString(SHA256.HashData(Encoding.UTF8.GetBytes($"{newCertNo}:{oldCert.PersonId}:{DateTime.UtcNow:O}"))).ToLowerInvariant(),
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.LearnerCertificates.Add(newCert);
        _audit.LogAction(db, "LearnerCertificate", oldCert.Id, "ReissueSpoiledCertificate", currentUsername, oldCert, newCert);
        await db.SaveChangesAsync();

        return newCert;
    }

    public async Task<SummativeAssessmentReport?> GetReportByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.SummativeAssessmentReports
            .Include(r => r.Person)
            .Include(r => r.Organisation)
            .Include(r => r.TrainingProvider)
            .Include(r => r.CompanyLearner)
            .Include(r => r.AssessmentBatch)
            .Include(r => r.AssessorPerson)
            .Include(r => r.InternalModeratorPerson)
            .Include(r => r.UnitStandardAssessments)
            .Include(r => r.EisaEntries)
            .Include(r => r.StatementOfResultsList)
            .FirstOrDefaultAsync(r => r.Id == id);
    }

    public async Task<List<SummativeAssessmentReport>> GetReportsAsync(string? statusCode = null, string? qualificationTitle = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.SummativeAssessmentReports
            .Include(r => r.Person)
            .Include(r => r.Organisation)
            .Include(r => r.TrainingProvider)
            .Include(r => r.AssessmentBatch)
            .AsQueryable();

        if (!string.IsNullOrWhiteSpace(statusCode))
        {
            query = query.Where(r => r.StatusCode == statusCode);
        }

        if (!string.IsNullOrWhiteSpace(qualificationTitle))
        {
            query = query.Where(r => r.QualificationTitle.Contains(qualificationTitle));
        }

        return await query.OrderByDescending(r => r.CreatedAt).ToListAsync();
    }

    public async Task<StatementOfResults?> VerifyStatementOfResultsAsync(string serialNumberOrHash)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.StatementOfResults
            .Include(s => s.Person)
            .Include(s => s.CompanyLearner)
            .Include(s => s.SummativeAssessmentReport)
            .FirstOrDefaultAsync(s => s.SorSerialNumber == serialNumberOrHash || s.TamperProofHashSha256 == serialNumberOrHash);
    }

    public async Task<List<TrainingProvider>> GetTrainingProvidersAsync()
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.TrainingProviders.OrderBy(p => p.ProviderName).ToListAsync();
    }

    public async Task<List<string>> GetQualificationsForProviderAsync(int providerId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var list = await db.CompanyLearners
            .Where(c => c.TrainingProviderId == providerId && !string.IsNullOrEmpty(c.QualificationTitle))
            .Select(c => c.QualificationTitle)
            .Distinct()
            .ToListAsync();

        if (list.Count == 0)
        {
            list = new() { "National Certificate: Mechanical Engineering (Fitting & Machining)", "National Certificate: Automotive Repair and Maintenance", "National Certificate: Welding Application and Practice" };
        }
        return list;
    }

    public async Task<List<CompanyLearner>> GetCompanyLearnersForProviderAsync(int providerId, string? qualificationTitle = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.CompanyLearners
            .Include(c => c.Person)
            .Include(c => c.Organisation)
            .Where(c => c.TrainingProviderId == providerId);

        if (!string.IsNullOrWhiteSpace(qualificationTitle))
        {
            query = query.Where(c => c.QualificationTitle == qualificationTitle);
        }

        return await query.Take(50).ToListAsync();
    }

    public async Task<List<EtqaAssessor>> GetAccreditedAssessorsAsync()
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.EtqaAssessors
            .Where(a => !a.AssessmentAbilitySuspended && (a.EtqaRole == "Assessor" || a.EtqaRole == "Both" || a.DesignationTypeId == "01"))
            .Include(a => a.Person)
            .Take(50)
            .ToListAsync();
    }

    public async Task<List<EtqaAssessor>> GetAccreditedModeratorsAsync()
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.EtqaAssessors
            .Where(a => !a.AssessmentAbilitySuspended && (a.EtqaRole == "Moderator" || a.EtqaRole == "Both" || a.DesignationTypeId == "02"))
            .Include(a => a.Person)
            .Take(50)
            .ToListAsync();
    }

    public async Task<List<Person>> GetContactPersonsForProviderAsync(int providerId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.OrganisationContacts
            .Where(c => c.OrganisationId == providerId)
            .Include(c => c.Person)
            .Select(c => c.Person!)
            .Where(p => p != null)
            .Take(50)
            .ToListAsync();
    }

    public async Task<AssessmentBatch> SubmitBatchToQaPoolAsync(int batchId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var batch = await db.AssessmentBatches.FirstOrDefaultAsync(b => b.Id == batchId)
            ?? throw new KeyNotFoundException($"AssessmentBatch with ID {batchId} not found.");

        batch.StatusCode = "InExternalModerationPool";
        batch.ModifiedAt = DateTime.UtcNow;
        batch.ModifiedBy = currentUsername;

        _audit.LogAction(db, "AssessmentBatch", batch.Id, "SubmitToQaPool", currentUsername, null, batch);
        await db.SaveChangesAsync();
        return batch;
    }

    public async Task<List<AssessmentBatch>> GetUpheldBatchesPendingPrintingAsync()
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.AssessmentBatches
            .Include(b => b.TrainingProvider)
            .Where(b => b.StatusCode == "Upheld" || b.StatusCode == "Finalised")
            .OrderByDescending(b => b.CreatedAt)
            .Take(50)
            .ToListAsync();
    }

    public async Task<CertificatePrintingBatch?> GetPrintingBatchDetailsAsync(int printingBatchId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.CertificatePrintingBatches
            .Include(b => b.Certificates)
                .ThenInclude(c => c.Person)
            .Include(b => b.DistributionLetters)
                .ThenInclude(d => d.TrainingProvider)
            .FirstOrDefaultAsync(b => b.Id == printingBatchId);
    }

    public async Task<List<ScannedCertificateAttachment>> GetScannedCertificatesAsync(int certificateId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.ScannedCertificateAttachments
            .Where(s => s.LearnerCertificateId == certificateId)
            .OrderByDescending(s => s.UploadedAt)
            .ToListAsync();
    }

    public async Task<(List<AssessmentBatchLearner> Learners, Dictionary<int, LearnerCertificate> Certificates, Dictionary<int, StatementOfResults> Sors)> GetBatchCredentialsDetailsAsync(int batchId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var learners = await db.AssessmentBatchLearners
            .Include(l => l.SummativeAssessmentReport)
                .ThenInclude(r => r!.Person)
            .Where(l => l.AssessmentBatchId == batchId)
            .ToListAsync();

        var reportIds = learners.Select(l => l.SummativeAssessmentReportId).ToList();

        var certList = await db.LearnerCertificates
            .Where(c => reportIds.Contains(c.SummativeAssessmentReportId))
            .ToListAsync();
        var certificates = certList.ToDictionary(c => c.SummativeAssessmentReportId);

        var sorList = await db.StatementOfResults
            .Where(s => reportIds.Contains(s.SummativeAssessmentReportId))
            .ToListAsync();
        var sors = sorList.ToDictionary(s => s.SummativeAssessmentReportId);

        return (learners, certificates, sors);
    }
}
