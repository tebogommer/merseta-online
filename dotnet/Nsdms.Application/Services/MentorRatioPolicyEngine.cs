using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class MentorRatioPolicyEngine : IMentorRatioPolicyEngine
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _audit;
    private readonly ISystemConfigurationService _systemConfig;

    private const string GlobalConfigKey = "WorkplaceApproval.EnforceMentorRatios";

    public MentorRatioPolicyEngine(
        INsdmsDbContextFactory contextFactory,
        IAuditService audit,
        ISystemConfigurationService systemConfig)
    {
        _contextFactory = contextFactory;
        _audit = audit;
        _systemConfig = systemConfig;
    }

    public async Task<MentorRatioEvaluationResult> EvaluateWorkplaceApprovalCapacityAsync(int workplaceApprovalId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var approval = await db.WorkplaceApprovals
            .Include(w => w.Organisation)
            .Include(w => w.Mentors)
                .ThenInclude(m => m.Person)
            .FirstOrDefaultAsync(w => w.Id == workplaceApprovalId);

        if (approval == null)
        {
            throw new KeyNotFoundException($"WorkplaceApproval with ID {workplaceApprovalId} not found.");
        }

        var result = new MentorRatioEvaluationResult
        {
            WorkplaceApprovalId = approval.Id,
            WorkplaceApprovalNumber = string.IsNullOrWhiteSpace(approval.ApprovalNumber) ? $"WPA-{approval.Id:D5}" : approval.ApprovalNumber,
            OrganisationId = approval.OrganisationId,
            OrganisationName = approval.Organisation?.CompanyName ?? "Unknown Organisation"
        };

        // 1. Check Global System Configuration
        var globalEnforcement = await _systemConfig.GetValueAsync<bool>(GlobalConfigKey, true);
        if (!globalEnforcement)
        {
            result.IsEnforcementActive = false;
            result.EnforcementState = MentorRatioEnforcementState.GlobalDisabled;
            result.Status = MentorRatioComplianceStatus.Exempt;
            result.StatusMessage = "Global mentor-to-learner ratio enforcement is disabled in system configuration.";
            result.ExemptionReason = "System-wide Administrator Override / Migration Mode";
        }

        // 2. Check Organisation-level Exemption
        if (result.IsEnforcementActive && approval.Organisation != null && approval.Organisation.IsMentorRatioEnforced.HasValue && !approval.Organisation.IsMentorRatioEnforced.Value)
        {
            result.IsEnforcementActive = false;
            result.EnforcementState = MentorRatioEnforcementState.OrgExempt;
            result.Status = MentorRatioComplianceStatus.Exempt;
            result.ExemptionReason = approval.Organisation.MentorRatioExemptionReason ?? "Organisation granted statutory exemption from ratio enforcement.";
            result.StatusMessage = $"Host employer ({approval.Organisation.CompanyName}) is exempt from mentor ratio enforcement.";
        }

        // 3. Check Workplace Approval Scope Exemption
        if (result.IsEnforcementActive && approval.IsRatioEnforced.HasValue && !approval.IsRatioEnforced.Value)
        {
            result.IsEnforcementActive = false;
            result.EnforcementState = MentorRatioEnforcementState.WorkplaceExempt;
            result.Status = MentorRatioComplianceStatus.Exempt;
            result.ExemptionReason = approval.MentorRatioExemptionNotes ?? "Workplace Approval granted special dispensation exemption.";
            result.StatusMessage = "This workplace approval is specifically exempt from mentor ratio restrictions.";
        }

        // 4. Resolve Trade Ratio Policy
        TradeMentorRatioPolicy? tradePolicy = null;
        if (!string.IsNullOrWhiteSpace(approval.TradeCode))
        {
            tradePolicy = await db.TradeMentorRatioPolicies
                .FirstOrDefaultAsync(p => p.TradeCode == approval.TradeCode && p.IsActive);
        }

        if (tradePolicy == null && approval.SaqaQualificationId.HasValue)
        {
            tradePolicy = await db.TradeMentorRatioPolicies
                .FirstOrDefaultAsync(p => p.SaqaQualificationId == approval.SaqaQualificationId.Value && p.IsActive);
        }

        if (tradePolicy == null && !string.IsNullOrWhiteSpace(approval.QualificationTitle))
        {
            var titleLower = approval.QualificationTitle.ToLower();
            tradePolicy = await db.TradeMentorRatioPolicies
                .Where(p => p.IsActive)
                .FirstOrDefaultAsync(p => titleLower.Contains(p.TradeTitle.ToLower()));
        }

        // Fallback default policy if no match
        tradePolicy ??= await db.TradeMentorRatioPolicies.FirstOrDefaultAsync(p => p.TradeCode == "GENERIC") ?? new TradeMentorRatioPolicy
        {
            TradeCode = "GENERIC",
            TradeTitle = "General Engineering Trade",
            StandardRatio = 4,
            MaxAllowedRatio = 6,
            MinExperienceYearsRequired = 3,
            EnforceStrictly = true,
            IsActive = true
        };

        result.TradeCode = tradePolicy.TradeCode;
        result.TradeTitle = tradePolicy.TradeTitle;
        result.StandardTradeRatio = tradePolicy.StandardRatio;

        if (result.IsEnforcementActive && !tradePolicy.EnforceStrictly)
        {
            result.EnforcementState = MentorRatioEnforcementState.TradeAdvisoryOnly;
        }

        // 5. Evaluate Individual Mentor Capacities
        var mentorBreakdowns = new List<MentorCapacityBreakdownDto>();
        int totalCapacity = 0;
        int activeMentorsCount = 0;

        foreach (var mentor in approval.Mentors)
        {
            var mentorName = mentor.Person?.FullName ?? $"Person #{mentor.PersonId}";
            var isMentorActive = mentor.IsActive;
            var isExempt = mentor.IsRatioExempt || !mentor.IsRatioEnforced;

            int allocatedCap;
            string capSource;

            if (!isMentorActive)
            {
                allocatedCap = 0;
                capSource = "Inactive Mentor (0 slots)";
            }
            else
            {
                activeMentorsCount++;

                if (isExempt)
                {
                    allocatedCap = tradePolicy.StandardRatio;
                    capSource = "Exempt / Non-restricted";
                }
                else if (mentor.MaxLearnerCapacity.HasValue && mentor.MaxLearnerCapacity.Value > 0)
                {
                    allocatedCap = mentor.MaxLearnerCapacity.Value;
                    capSource = $"Mentor Override (1:{allocatedCap})";
                }
                else if (approval.CustomTradeRatio.HasValue && approval.CustomTradeRatio.Value > 0)
                {
                    allocatedCap = approval.CustomTradeRatio.Value;
                    capSource = $"Workplace Override (1:{allocatedCap})";
                }
                else if (approval.Organisation != null && approval.Organisation.CustomMentorRatioCap.HasValue && approval.Organisation.CustomMentorRatioCap.Value > 0)
                {
                    allocatedCap = approval.Organisation.CustomMentorRatioCap.Value;
                    capSource = $"Organisation Cap (1:{allocatedCap})";
                }
                else
                {
                    allocatedCap = tradePolicy.StandardRatio;
                    capSource = $"Trade Standard (1:{allocatedCap})";
                }

                totalCapacity += allocatedCap;
            }

            // Check mentor experience guidelines
            if (isMentorActive && mentor.YearsExperience < tradePolicy.MinExperienceYearsRequired)
            {
                result.Warnings.Add($"Mentor {mentorName} has {mentor.YearsExperience} years experience (Trade guideline recommends minimum {tradePolicy.MinExperienceYearsRequired} years).");
            }

            if (isMentorActive && !mentor.IsCertifiedArtisan)
            {
                result.Warnings.Add($"Mentor {mentorName} is not marked as a verified Red Seal Certified Artisan.");
            }

            mentorBreakdowns.Add(new MentorCapacityBreakdownDto(
                mentor.Id,
                mentor.PersonId,
                mentorName,
                mentor.Person?.RsaIdNumber,
                mentor.Designation,
                mentor.ArtisanTradeNumber,
                mentor.YearsExperience,
                mentor.IsCertifiedArtisan,
                mentor.IsActive,
                mentor.IsRatioExempt,
                mentor.IsRatioEnforced,
                allocatedCap,
                capSource
            ));
        }

        result.TotalActiveMentors = activeMentorsCount;
        result.EffectiveTotalCapacity = totalCapacity;
        result.MentorBreakdowns = mentorBreakdowns;

        // 6. Calculate Currently Placed Learners
        var placedLearnersCount = await db.CompanyLearners
            .Where(l => l.OrganisationId == approval.OrganisationId
                     && l.IsActive
                     && (l.EnrolmentStatusCode == "Registered" || l.EnrolmentStatusCode == "InProgress" || l.EnrolmentStatusCode == "Active")
                     && (l.QualificationTitle == approval.QualificationTitle || (approval.SaqaQualificationId.HasValue && l.SaqaQualificationId == approval.SaqaQualificationId.Value)))
            .CountAsync();

        result.TotalPlacedLearners = placedLearnersCount;
        result.AvailableSlots = Math.Max(0, totalCapacity - placedLearnersCount);
        result.UtilizationPercentage = totalCapacity > 0
            ? Math.Round(((double)placedLearnersCount / totalCapacity) * 100.0, 1)
            : (placedLearnersCount > 0 ? 100.0 : 0.0);

        // 7. Determine Final Status if not already exempt
        if (result.EnforcementState != MentorRatioEnforcementState.GlobalDisabled
         && result.EnforcementState != MentorRatioEnforcementState.OrgExempt
         && result.EnforcementState != MentorRatioEnforcementState.WorkplaceExempt)
        {
            if (activeMentorsCount == 0 && placedLearnersCount > 0)
            {
                result.Status = MentorRatioComplianceStatus.OverCapacity;
                result.StatusMessage = "No active certified mentors assigned to this workplace approval. Learner supervision is non-compliant.";
            }
            else if (placedLearnersCount > totalCapacity)
            {
                result.Status = MentorRatioComplianceStatus.OverCapacity;
                result.StatusMessage = $"Over capacity by {placedLearnersCount - totalCapacity} learner(s). Total apprentices ({placedLearnersCount}) exceed verified mentor capacity ({totalCapacity}).";
            }
            else if (result.UtilizationPercentage >= 80.0)
            {
                result.Status = MentorRatioComplianceStatus.NearCapacity;
                result.StatusMessage = $"Operating near capacity ({placedLearnersCount}/{totalCapacity} slots used, {result.UtilizationPercentage}%).";
            }
            else
            {
                result.Status = MentorRatioComplianceStatus.Compliant;
                result.StatusMessage = $"Compliant: {result.AvailableSlots} apprentice supervision slot(s) available.";
            }
        }

        return result;
    }

    public async Task<MentorRatioEvaluationResult> EvaluatePlacementFeasibilityAsync(int workplaceApprovalId, int additionalLearners = 1)
    {
        var current = await EvaluateWorkplaceApprovalCapacityAsync(workplaceApprovalId);

        if (!current.IsEnforcementActive)
        {
            return current;
        }

        var projectedTotal = current.TotalPlacedLearners + additionalLearners;
        current.TotalPlacedLearners = projectedTotal;
        current.AvailableSlots = Math.Max(0, current.EffectiveTotalCapacity - projectedTotal);
        current.UtilizationPercentage = current.EffectiveTotalCapacity > 0
            ? Math.Round((double)projectedTotal / current.EffectiveTotalCapacity * 100.0, 1)
            : (projectedTotal > 0 ? 999.0 : 0.0);

        if (projectedTotal > current.EffectiveTotalCapacity)
        {
            current.Status = MentorRatioComplianceStatus.OverCapacity;
            current.StatusMessage = $"Adding {additionalLearners} learner(s) will exceed mentor capacity ({projectedTotal}/{current.EffectiveTotalCapacity} slots).";
            current.Warnings.Add($"Deficit of {projectedTotal - current.EffectiveTotalCapacity} learner supervision slot(s). Additional certified artisans required.");
        }

        return current;
    }

    public async Task<List<TradeMentorRatioPolicy>> GetAllTradePoliciesAsync(bool activeOnly = false)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.TradeMentorRatioPolicies.AsQueryable();

        if (activeOnly)
        {
            query = query.Where(p => p.IsActive);
        }

        return await query.OrderBy(p => p.TradeTitle).ToListAsync();
    }

    public async Task<TradeMentorRatioPolicy?> GetTradePolicyByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.TradeMentorRatioPolicies.FirstOrDefaultAsync(p => p.Id == id);
    }

    public async Task<TradeMentorRatioPolicy?> GetTradePolicyByCodeAsync(string tradeCode)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.TradeMentorRatioPolicies.FirstOrDefaultAsync(p => p.TradeCode == tradeCode);
    }

    public async Task<TradeMentorRatioPolicy> SaveTradePolicyAsync(TradeMentorRatioPolicy policy, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        if (policy.Id == 0)
        {
            policy.CreatedAt = DateTime.UtcNow;
            policy.CreatedBy = currentUsername;

            db.TradeMentorRatioPolicies.Add(policy);
            await db.SaveChangesAsync();

            _audit.LogAction(db, "TradeMentorRatioPolicy", policy.Id, "Create", currentUsername, null, policy);
            await db.SaveChangesAsync();
            return policy;
        }

        var existing = await db.TradeMentorRatioPolicies.FindAsync(policy.Id);
        if (existing == null)
        {
            throw new KeyNotFoundException($"TradeMentorRatioPolicy with ID {policy.Id} not found.");
        }

        var beforeState = new
        {
            existing.TradeCode,
            existing.TradeTitle,
            existing.StandardRatio,
            existing.MaxAllowedRatio,
            existing.EnforceStrictly,
            existing.IsActive
        };

        existing.TradeCode = policy.TradeCode;
        existing.TradeTitle = policy.TradeTitle;
        existing.TradeOfoCode = policy.TradeOfoCode;
        existing.SaqaQualificationId = policy.SaqaQualificationId;
        existing.StandardRatio = policy.StandardRatio;
        existing.MaxAllowedRatio = policy.MaxAllowedRatio;
        existing.MinExperienceYearsRequired = policy.MinExperienceYearsRequired;
        existing.EnforceStrictly = policy.EnforceStrictly;
        existing.IsActive = policy.IsActive;
        existing.Notes = policy.Notes;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = currentUsername;

        _audit.LogAction(db, "TradeMentorRatioPolicy", existing.Id, "Update", currentUsername, beforeState, existing);
        await db.SaveChangesAsync();
        return existing;
    }

    public async Task<bool> DeleteTradePolicyAsync(int id, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var policy = await db.TradeMentorRatioPolicies.FindAsync(id);
        if (policy == null) return false;

        policy.IsActive = false;
        policy.ModifiedAt = DateTime.UtcNow;
        policy.ModifiedBy = currentUsername;

        _audit.LogAction(db, "TradeMentorRatioPolicy", id, "Deactivate", currentUsername, null, policy);
        await db.SaveChangesAsync();
        return true;
    }

    public async Task<bool> GetGlobalRatioEnforcementAsync()
    {
        return await _systemConfig.GetValueAsync<bool>(GlobalConfigKey, true);
    }

    public async Task<bool> SetGlobalRatioEnforcementAsync(bool isEnabled, string currentUsername = "SYSTEM")
    {
        await _systemConfig.SetConfigAsync(
            GlobalConfigKey,
            isEnabled.ToString().ToLower(),
            category: "WorkplaceApproval",
            description: "Master switch enabling statutory artisan mentor-to-learner ratio enforcement globally.",
            dataType: "Boolean",
            currentUsername: currentUsername);

        return true;
    }

    public async Task SetOrganisationRatioOverrideAsync(int organisationId, bool? isEnforced, string? exemptionReason, int? customCap, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var org = await db.Organisations.FindAsync(organisationId);
        if (org == null) throw new KeyNotFoundException($"Organisation with ID {organisationId} not found.");

        var beforeState = new { org.IsMentorRatioEnforced, org.MentorRatioExemptionReason, org.CustomMentorRatioCap };

        org.IsMentorRatioEnforced = isEnforced;
        org.MentorRatioExemptionReason = exemptionReason;
        org.CustomMentorRatioCap = customCap;
        org.ModifiedAt = DateTime.UtcNow;
        org.ModifiedBy = currentUsername;

        _audit.LogAction(db, "Organisation", org.Id, "SetMentorRatioOverride", currentUsername, beforeState, org);
        await db.SaveChangesAsync();
    }

    public async Task SetWorkplaceRatioOverrideAsync(int workplaceApprovalId, bool? isEnforced, int? customRatio, string? tradeCode, string? exemptionNotes, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var approval = await db.WorkplaceApprovals.FindAsync(workplaceApprovalId);
        if (approval == null) throw new KeyNotFoundException($"WorkplaceApproval with ID {workplaceApprovalId} not found.");

        var beforeState = new { approval.IsRatioEnforced, approval.CustomTradeRatio, approval.TradeCode, approval.MentorRatioExemptionNotes };

        approval.IsRatioEnforced = isEnforced;
        approval.CustomTradeRatio = customRatio;
        if (!string.IsNullOrWhiteSpace(tradeCode))
        {
            approval.TradeCode = tradeCode;
        }
        approval.MentorRatioExemptionNotes = exemptionNotes;
        approval.ModifiedAt = DateTime.UtcNow;
        approval.ModifiedBy = currentUsername;

        _audit.LogAction(db, "WorkplaceApproval", approval.Id, "SetWorkplaceRatioOverride", currentUsername, beforeState, approval);
        await db.SaveChangesAsync();
    }

    public async Task SetMentorCapacityOverrideAsync(int mentorId, int? maxCapacity, bool isExempt, bool isEnforced, string? notes, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var mentor = await db.WorkplaceApprovalMentors.FindAsync(mentorId);
        if (mentor == null) throw new KeyNotFoundException($"WorkplaceApprovalMentor with ID {mentorId} not found.");

        var beforeState = new { mentor.MaxLearnerCapacity, mentor.IsRatioExempt, mentor.IsRatioEnforced, mentor.Notes };

        mentor.MaxLearnerCapacity = maxCapacity;
        mentor.IsRatioExempt = isExempt;
        mentor.IsRatioEnforced = isEnforced;
        mentor.Notes = notes;
        mentor.ModifiedAt = DateTime.UtcNow;
        mentor.ModifiedBy = currentUsername;

        _audit.LogAction(db, "WorkplaceApprovalMentor", mentor.Id, "SetMentorCapacityOverride", currentUsername, beforeState, mentor);
        await db.SaveChangesAsync();
    }
}
