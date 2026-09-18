using Nsdms.Application.Common;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class OrganisationComplianceEngine : IOrganisationComplianceEngine
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;

    public OrganisationComplianceEngine(INsdmsDbContextFactory contextFactory, IAuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    public async Task<OrganisationComplianceRadarReport> EvaluateOrganisationComplianceAsync(int organisationId, CancellationToken cancellationToken = default)
    {
        await using var context = await _contextFactory.CreateDbContextAsync(cancellationToken);

        var org = await context.Organisations
            .AsNoTracking()
            .FirstOrDefaultAsync(o => o.Id == organisationId, cancellationToken);

        if (org == null)
        {
            return new OrganisationComplianceRadarReport
            {
                OrganisationId = organisationId,
                OrganisationName = "Unknown",
                OverallGrade = "Record Not Found",
                OverallBadgeColor = "Error"
            };
        }

        // Fetch related statutory data concurrently
        var wspList = await context.WspSubmissions
            .AsNoTracking()
            .Where(w => w.OrganisationId == organisationId)
            .OrderByDescending(w => w.FinYear)
            .Take(5)
            .ToListAsync(cancellationToken);

        var extensionRequests = await context.WspExtensionRequests
            .AsNoTracking()
            .Where(e => e.OrganisationId == organisationId)
            .OrderByDescending(e => e.CreatedAt)
            .Take(5)
            .ToListAsync(cancellationToken);

        var governanceMembers = await context.OrganisationGovernanceMembers
            .AsNoTracking()
            .Where(g => g.OrganisationId == organisationId && g.IsActive)
            .ToListAsync(cancellationToken);

        var wpaList = await context.WorkplaceApprovals
            .AsNoTracking()
            .Where(w => w.OrganisationId == organisationId)
            .ToListAsync(cancellationToken);

        var govPersonIds = governanceMembers.Where(g => g.PersonId.HasValue).Select(g => g.PersonId!.Value).ToList();

        var conflictFlags = await context.ConflictFlags
            .AsNoTracking()
            .Where(c => c.TargetOrganisationId == organisationId || 
                       (govPersonIds.Contains(c.PersonId) && c.ResolutionStatusCode != "CLEARED_WITH_JUSTIFICATION" && c.ResolutionStatusCode != "RESOLVED"))
            .ToListAsync(cancellationToken);

        var hasLevyLines = await context.LevyFileLines
            .AsNoTracking()
            .AnyAsync(l => l.SdlNumber == org.SdlNumber, cancellationToken);

        var committeeParity = await EvaluateTrainingCommitteeParityAsync(organisationId, cancellationToken);

        var report = new OrganisationComplianceRadarReport
        {
            OrganisationId = org.Id,
            OrganisationName = org.CompanyName,
            SdlNumber = org.SdlNumber,
            EvaluationDate = DateTime.UtcNow
        };

        // -------------------------------------------------------------
        // Pillar 1: WSP / ATR Statutory Submission & Approval (20%)
        // -------------------------------------------------------------
        var p1 = new CompliancePillarResult
        {
            PillarType = StatutoryPillarType.WspAtrCompliance,
            PillarName = "WSP / ATR Statutory Submission",
            StatutoryCode = "SDA-REG4",
            Weight = 20m,
            TargetTabIndex = 5,
            BadgeIcon = "Assignment"
        };

        var approvedWsp = wspList.FirstOrDefault(w => w.StatusCode == "Approved" || w.StatusCode == "SYS_APPROVED");
        var inReviewWsp = wspList.FirstOrDefault(w => w.StatusCode == "SUBMITTED" || w.StatusCode == "UNDER_REVIEW" || w.StatusCode == "RECOMMENDED");
        var approvedExt = extensionRequests.FirstOrDefault(e => e.ApprovalStatusCode == "Approved");

        if (approvedWsp != null)
        {
            p1.Score = 100m;
            p1.Status = CompliancePillarStatus.Compliant;
            p1.Summary = $"Approved WSP/ATR on file (FY {approvedWsp.FinYear})";
            p1.Details.Add($"Reference: {approvedWsp.ReferenceNumber ?? "WSP-" + approvedWsp.Id}");
            p1.Details.Add("Statutory mandatory grant rebate requirement satisfied");
        }
        else if (inReviewWsp != null)
        {
            p1.Score = 70m;
            p1.Status = CompliancePillarStatus.PartiallyCompliant;
            p1.Summary = $"WSP/ATR submission under review (FY {inReviewWsp.FinYear})";
            p1.ActionRequired = "Review in progress by merSETA Client Liaison Officer";
            p1.Details.Add($"Current status: {inReviewWsp.StatusCode}");
        }
        else if (approvedExt != null)
        {
            p1.Score = 60m;
            p1.Status = CompliancePillarStatus.PartiallyCompliant;
            p1.Summary = "Statutory WSP submission extension approved";
            p1.ActionRequired = $"Submit finalized WSP before extended deadline ({approvedExt.RequestedExtensionDate:dd MMM yyyy})";
            p1.Details.Add($"Extension Reference: {approvedExt.ApplicationReference ?? "EXT-" + approvedExt.Id}");
        }
        else
        {
            p1.Score = 0m;
            p1.Status = CompliancePillarStatus.NonCompliant;
            p1.Summary = "No approved WSP / ATR for current cycle";
            p1.ActionRequired = "Submit annual Workplace Skills Plan (WSP) or apply for an extension";
            p1.Details.Add("Mandatory grant rebate locked until approved WSP submitted");
        }
        report.Pillars.Add(p1);

        // -------------------------------------------------------------
        // Pillar 2: SARS Monthly Levy Contributions & Status (15%)
        // -------------------------------------------------------------
        var p2 = new CompliancePillarResult
        {
            PillarType = StatutoryPillarType.SarsLevyContributions,
            PillarName = "SARS Levy Contributions",
            StatutoryCode = "SDLA-ACT97",
            Weight = 15m,
            TargetTabIndex = 5,
            BadgeIcon = "Paid"
        };

        var isExempt = string.Equals(org.LevyCategoryCode, "EXEMPT", StringComparison.OrdinalIgnoreCase) ||
                       string.Equals(org.LevyCategoryCode, "NON_LEVY_PAYING", StringComparison.OrdinalIgnoreCase) ||
                       org.IsNonEmployerEntity;

        if (isExempt)
        {
            p2.Score = 100m;
            p2.Status = CompliancePillarStatus.Compliant;
            p2.Summary = "Statutory levy-exempt status recognized";
            p2.Details.Add($"Classification: {org.LevyCategoryCode ?? "Non-Levy Paying Partner"}");
        }
        else if (hasLevyLines)
        {
            p2.Score = 100m;
            p2.Status = CompliancePillarStatus.Compliant;
            p2.Summary = "SARS monthly 1% Skills Development Levy active & reconciled";
            p2.Details.Add("Monthly contributions verified against national SARS data extracts");
        }
        else
        {
            p2.Score = 40m;
            p2.Status = CompliancePillarStatus.PartiallyCompliant;
            p2.Summary = "Pending SARS levy data file reconciliation";
            p2.ActionRequired = "Ensure employer payroll SDL payments reflect on SARS monthly return";
            p2.Details.Add("No matching levy file line found in recent staging batches");
        }
        report.Pillars.Add(p2);

        // -------------------------------------------------------------
        // Pillar 3: Training Committee 50/50 Parity (15%)
        // -------------------------------------------------------------
        var p3 = new CompliancePillarResult
        {
            PillarType = StatutoryPillarType.TrainingCommitteeParity,
            PillarName = "Training Committee 50/50 Parity",
            StatutoryCode = "SDA-SEC10",
            Weight = 15m,
            TargetTabIndex = 1,
            BadgeIcon = "Groups"
        };

        if (!committeeParity.IsMandatory)
        {
            p3.Score = 100m;
            p3.Status = CompliancePillarStatus.NotApplicable;
            p3.Summary = "SMME Exemption (<50 employees)";
            p3.Details.Add("Training Committee is statutory optional for employers with under 50 staff");
        }
        else if (committeeParity.IsCompliant)
        {
            p3.Score = 100m;
            p3.Status = CompliancePillarStatus.Compliant;
            p3.Summary = $"Statutory 50/50 Parity Achieved ({committeeParity.ManagementMembersCount} Management / {committeeParity.LabourMembersCount} Labour)";
            p3.Details.Add($"Total Appointed Members: {committeeParity.TotalMembers}");
            p3.Details.Add($"Quorum Compliance: {(committeeParity.IsQuorumMet ? "Satisfied" : "Pending Meeting Review")}");
        }
        else if (committeeParity.TotalMembers > 0)
        {
            p3.Score = 50m;
            p3.Status = CompliancePillarStatus.PartiallyCompliant;
            p3.Summary = $"Imbalanced Representation ({committeeParity.ManagementPercentage}% Management vs {committeeParity.LabourPercentage}% Labour)";
            p3.ActionRequired = committeeParity.Recommendation;
            p3.Details.Add($"Current Parity Ratio: {committeeParity.ParityRatio:P0}");
        }
        else
        {
            p3.Score = 0m;
            p3.Status = CompliancePillarStatus.NonCompliant;
            p3.Summary = "Mandatory Training Committee not constituted";
            p3.ActionRequired = "Constitute Training Committee with equal Management and Union/Labour representatives";
            p3.Details.Add("Required for employers with 50+ employees under SDA Section 10(2)");
        }
        report.Pillars.Add(p3);

        // -------------------------------------------------------------
        // Pillar 4: Governance & Beneficial Ownership (15%)
        // -------------------------------------------------------------
        var p4 = new CompliancePillarResult
        {
            PillarType = StatutoryPillarType.GovernanceAndOwnership,
            PillarName = "Governance & Beneficial Ownership",
            StatutoryCode = "PFMA-CIPC",
            Weight = 15m,
            TargetTabIndex = 2,
            BadgeIcon = "AccountBalance"
        };

        var directors = governanceMembers.Where(g => g.GovernanceRoleCode.Contains("DIRECTOR", StringComparison.OrdinalIgnoreCase) || 
                                                    !string.IsNullOrWhiteSpace(g.DirectorCategory)).ToList();
        var shareholders = governanceMembers.Where(g => g.MemberType == "CORPORATE_ENTITY" || g.ShareholdingPercentage > 0).ToList();

        if (directors.Count > 0 && (shareholders.Count > 0 || org.OrganisationTypeCode == "PUBLIC_ENTITY" || org.OrganisationTypeCode == "NGO_NPO"))
        {
            p4.Score = 100m;
            p4.Status = CompliancePillarStatus.Compliant;
            p4.Summary = $"Verified Governance: {directors.Count} Directors & {shareholders.Count} Beneficial Shareholders";
            p4.Details.Add("CIPC directorships and institutional shareholding registered");
        }
        else if (directors.Count > 0)
        {
            p4.Score = 70m;
            p4.Status = CompliancePillarStatus.PartiallyCompliant;
            p4.Summary = $"{directors.Count} Director(s) registered; Shareholder register incomplete";
            p4.ActionRequired = "Disclose beneficial ownership and equity shareholding";
            p4.Details.Add("Beneficial ownership required for Discretionary Grant anti-syndicate verification");
        }
        else
        {
            p4.Score = 0m;
            p4.Status = CompliancePillarStatus.NonCompliant;
            p4.Summary = "No active Directors, Trustees, or Executives registered";
            p4.ActionRequired = "Register active CIPC Directors and beneficial owners";
            p4.Details.Add("PFMA compliance requires verified corporate leadership disclosures");
        }
        report.Pillars.Add(p4);

        // -------------------------------------------------------------
        // Pillar 5: Workplace Approval & Mentor Ratios (15%)
        // -------------------------------------------------------------
        var p5 = new CompliancePillarResult
        {
            PillarType = StatutoryPillarType.WorkplaceApprovalAndMentors,
            PillarName = "Workplace Approvals & Mentors",
            StatutoryCode = "QCTO-WPA",
            Weight = 15m,
            TargetTabIndex = 7,
            BadgeIcon = "FactCheck"
        };

        var activeWpa = wpaList.FirstOrDefault(w => string.Equals(w.ApprovalStatusCode, "Approved", StringComparison.OrdinalIgnoreCase) ||
                                                    string.Equals(w.ApprovalStatusCode, "ACTIVE", StringComparison.OrdinalIgnoreCase));
        var pendingWpa = wpaList.FirstOrDefault(w => string.Equals(w.ApprovalStatusCode, "Pending", StringComparison.OrdinalIgnoreCase) ||
                                                     string.Equals(w.ApprovalStatusCode, "SUBMITTED", StringComparison.OrdinalIgnoreCase));

        if (activeWpa != null)
        {
            p5.Score = 100m;
            p5.Status = CompliancePillarStatus.Compliant;
            p5.Summary = $"Active Workplace Approval ({activeWpa.ApprovalNumber})";
            p5.Details.Add($"Accredited Trade/Qualification: {activeWpa.QualificationTitle}");
            p5.Details.Add("Eligible to host apprentice and learnership practical components");
        }
        else if (pendingWpa != null)
        {
            p5.Score = 50m;
            p5.Status = CompliancePillarStatus.PartiallyCompliant;
            p5.Summary = "Workplace approval application pending site inspection";
            p5.ActionRequired = "Complete physical site inspection with Regional Quality Assurance Officer";
            p5.Details.Add($"Application Reference: {pendingWpa.ApprovalNumber}");
        }
        else
        {
            p5.Score = 0m;
            p5.Status = CompliancePillarStatus.NonCompliant;
            p5.Summary = "No active Workplace Approval (WPA)";
            p5.ActionRequired = "Apply for Workplace Approval to host artisan apprentices and learnerships";
            p5.Details.Add("Workplace approval required before enrolling funded learners");
        }
        report.Pillars.Add(p5);

        // -------------------------------------------------------------
        // Pillar 6: Conflict of Interest Standing (10%)
        // -------------------------------------------------------------
        var p6 = new CompliancePillarResult
        {
            PillarType = StatutoryPillarType.ConflictOfInterestStanding,
            PillarName = "Conflict of Interest Standing",
            StatutoryCode = "PFMA-SEC50",
            Weight = 10m,
            TargetTabIndex = 2,
            BadgeIcon = "Security"
        };

        var criticalConflict = conflictFlags.FirstOrDefault(c => c.SeverityCode == "RED_CRITICAL");
        var elevatedConflict = conflictFlags.FirstOrDefault(c => c.SeverityCode == "AMBER_ELEVATED" || c.SeverityCode == "YELLOW_ADVISORY");

        if (criticalConflict != null)
        {
            p6.Score = 0m;
            p6.Status = CompliancePillarStatus.NonCompliant;
            p6.Summary = $"Critical statutory conflict flag: {criticalConflict.Title}";
            p6.ActionRequired = "Escalate to merSETA Risk & Compliance for formal recusal / clearance";
            p6.Details.Add(criticalConflict.Description);
        }
        else if (elevatedConflict != null)
        {
            p6.Score = 60m;
            p6.Status = CompliancePillarStatus.PartiallyCompliant;
            p6.Summary = $"Advisory conflict flag under compliance review: {elevatedConflict.Title}";
            p6.ActionRequired = "Awaiting committee review and justification upload";
            p6.Details.Add(elevatedConflict.Description);
        }
        else
        {
            p6.Score = 100m;
            p6.Status = CompliancePillarStatus.Compliant;
            p6.Summary = "Clean statutory standing (Zero active conflict flags)";
            p6.Details.Add("No insider affiliations or unmanaged multi-entity syndicates detected");
        }
        report.Pillars.Add(p6);

        // -------------------------------------------------------------
        // Pillar 7: Banking Details Security (10%)
        // -------------------------------------------------------------
        var p7 = new CompliancePillarResult
        {
            PillarType = StatutoryPillarType.BankingDetailsSecurity,
            PillarName = "Banking Details Security",
            StatutoryCode = "BANKSERV-AVS",
            Weight = 10m,
            TargetTabIndex = 0,
            BadgeIcon = "AccountBalanceWallet"
        };

        if (org.BankingDetailsVerified && !string.IsNullOrWhiteSpace(org.BankAccountNumber))
        {
            p7.Score = 100m;
            p7.Status = CompliancePillarStatus.Compliant;
            p7.Summary = $"Bank account verified via Bankserv AVS ({org.BankName ?? "Commercial Bank"})";
            p7.Details.Add($"Account: •••• {(!string.IsNullOrEmpty(org.BankAccountNumber) && org.BankAccountNumber.Length > 4 ? org.BankAccountNumber[^4..] : "****")}");
            p7.Details.Add("Cleared for automated EFT disbursement of mandatory and discretionary grants");
        }
        else if (!string.IsNullOrWhiteSpace(org.BankAccountNumber))
        {
            p7.Score = 50m;
            p7.Status = CompliancePillarStatus.PartiallyCompliant;
            p7.Summary = "Bank account on file; Pending AVS verification";
            p7.ActionRequired = "Submit stamped bank confirmation letter for Finance authorization";
            p7.Details.Add("Account details submitted but not yet verified against Bankserv");
        }
        else
        {
            p7.Score = 0m;
            p7.Status = CompliancePillarStatus.NonCompliant;
            p7.Summary = "No banking details recorded";
            p7.ActionRequired = "Provide commercial banking details to receive grant disbursement rebates";
            p7.Details.Add("Disbursements strictly blocked until banking details verified");
        }
        report.Pillars.Add(p7);

        // -------------------------------------------------------------
        // Composite Overall Score & Assessment
        // -------------------------------------------------------------
        report.OverallScore = Math.Round(report.Pillars.Sum(p => p.WeightedScore), 1);

        if (report.OverallScore >= 85m)
        {
            report.OverallGrade = "Gold Standard (Full Statutory Compliance)";
            report.OverallBadgeColor = "Success";
        }
        else if (report.OverallScore >= 65m)
        {
            report.OverallGrade = "Good Standing (Minor Action Items)";
            report.OverallBadgeColor = "Info";
        }
        else if (report.OverallScore >= 50m)
        {
            report.OverallGrade = "Restricted (Action Required)";
            report.OverallBadgeColor = "Warning";
        }
        else
        {
            report.OverallGrade = "Non-Compliant (High Risk)";
            report.OverallBadgeColor = "Error";
        }

        // Collect critical action items
        foreach (var pillar in report.Pillars)
        {
            if (!string.IsNullOrWhiteSpace(pillar.ActionRequired))
            {
                report.CriticalActionItems.Add($"[{pillar.PillarName}] {pillar.ActionRequired}");
            }
        }

        // Eligibility gates
        report.IsEligibleForDgGrants = p1.Score >= 60m && p2.Score >= 40m && p6.Score >= 60m && p7.Score == 100m;
        report.IsEligibleForLearnerRegistrations = p5.Score >= 50m && p7.Score > 0m;

        return report;
    }

    public async Task<TrainingCommitteeParityReport> EvaluateTrainingCommitteeParityAsync(int organisationId, CancellationToken cancellationToken = default)
    {
        await using var context = await _contextFactory.CreateDbContextAsync(cancellationToken);

        var org = await context.Organisations
            .AsNoTracking()
            .FirstOrDefaultAsync(o => o.Id == organisationId, cancellationToken);

        var committee = await context.TrainingCommittees
            .Include(c => c.Members)
            .AsNoTracking()
            .FirstOrDefaultAsync(c => c.OrganisationId == organisationId && c.CommitteeStatusCode == "Active", cancellationToken);

        var sizeCode = org?.CompanySizeCode?.ToUpperInvariant() ?? "UNKNOWN";
        bool isSmme = sizeCode == "MICRO" || sizeCode == "SMALL" || (org?.IsNonEmployerEntity ?? false);

        var report = new TrainingCommitteeParityReport
        {
            OrganisationId = organisationId,
            IsMandatory = !isSmme,
            CompanySize = sizeCode
        };

        if (committee == null || committee.Members.Count == 0)
        {
            report.TotalMembers = 0;
            report.ManagementMembersCount = 0;
            report.LabourMembersCount = 0;
            report.IsQuorumMet = false;

            if (!report.IsMandatory)
            {
                report.IsCompliant = true;
                report.StatusText = "Exempt (SMME <50 staff)";
                report.StatusSeverity = "Info";
                report.Recommendation = "Training Committee is optional for SMMEs under SDA Section 10(2).";
            }
            else
            {
                report.IsCompliant = false;
                report.StatusText = "Committee Missing (Statutory Non-Compliance)";
                report.StatusSeverity = "Error";
                report.Recommendation = "Employers with 50+ employees must constitute a joint Training Committee.";
            }
            return report;
        }

        var activeMembers = committee.Members.Where(m => m.IsActive).ToList();
        report.TotalMembers = activeMembers.Count;

        report.ManagementMembersCount = activeMembers.Count(m =>
            string.Equals(m.MemberRoleCode, "EmployerRepresentative", StringComparison.OrdinalIgnoreCase) ||
            string.Equals(m.MemberRoleCode, "Chairperson", StringComparison.OrdinalIgnoreCase) ||
            (m.Constituency?.Contains("Management", StringComparison.OrdinalIgnoreCase) ?? false));

        report.LabourMembersCount = activeMembers.Count(m =>
            string.Equals(m.MemberRoleCode, "UnionRepresentative", StringComparison.OrdinalIgnoreCase) ||
            string.Equals(m.MemberRoleCode, "SdfSecretary", StringComparison.OrdinalIgnoreCase) ||
            (m.Constituency?.Contains("NUMSA", StringComparison.OrdinalIgnoreCase) ?? false) ||
            (m.Constituency?.Contains("Union", StringComparison.OrdinalIgnoreCase) ?? false) ||
            (m.Constituency?.Contains("Labour", StringComparison.OrdinalIgnoreCase) ?? false) ||
            (m.Constituency?.Contains("Solidarity", StringComparison.OrdinalIgnoreCase) ?? false));

        // In case members are categorised generically without matching above, distribute unclassified
        int unclassified = report.TotalMembers - (report.ManagementMembersCount + report.LabourMembersCount);
        if (unclassified > 0)
        {
            // Attribute to labour by default if labour is lower, else management
            if (report.LabourMembersCount <= report.ManagementMembersCount)
                report.LabourMembersCount += unclassified;
            else
                report.ManagementMembersCount += unclassified;
        }

        report.IsQuorumMet = committee.ConstitutionalQuorumMet && report.TotalMembers >= 2;

        if (!report.IsMandatory)
        {
            report.IsCompliant = true;
            report.StatusText = "Voluntary Committee Constituted";
            report.StatusSeverity = "Success";
            report.Recommendation = "SMME has voluntarily constituted a Training Committee.";
        }
        else if (report.ManagementMembersCount > 0 && report.LabourMembersCount > 0 && report.ParityRatio >= 0.8m)
        {
            report.IsCompliant = true;
            report.StatusText = "Compliant (Balanced 50/50 Representation)";
            report.StatusSeverity = "Success";
            report.Recommendation = $"Statutory parity satisfied ({report.ManagementMembersCount} Management / {report.LabourMembersCount} Labour).";
        }
        else if (report.ManagementMembersCount > 0 && report.LabourMembersCount > 0)
        {
            report.IsCompliant = false;
            report.StatusText = "Imbalanced Representation";
            report.StatusSeverity = "Warning";
            var lowerGroup = report.ManagementMembersCount < report.LabourMembersCount ? "Management" : "Labour/Union";
            report.Recommendation = $"Appoint additional {lowerGroup} representatives to achieve 50/50 balance.";
        }
        else
        {
            report.IsCompliant = false;
            report.StatusText = "Deficient Representation";
            report.StatusSeverity = "Error";
            var missingGroup = report.ManagementMembersCount == 0 ? "Management" : "Labour/Union";
            report.Recommendation = $"Committee lacks {missingGroup} representation. Appoint representatives immediately.";
        }

        return report;
    }
}
