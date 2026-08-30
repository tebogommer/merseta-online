using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class MakerCheckerValidationResult
{
    public bool IsAllowed { get; set; }
    public bool IsSelfAction { get; set; }
    public bool IsSuperAdminOverride { get; set; }
    public string Message { get; set; } = string.Empty;
    public string? TaskCreator { get; set; }
}

public class TaskLeaseResult
{
    public bool IsSuccess { get; set; }
    public bool IsAlreadyClaimedByOther { get; set; }
    public string Message { get; set; } = string.Empty;
    public WorkflowTaskLease? Lease { get; set; }
}

public class FinancialThresholdValidationResult
{
    public bool IsAllowed { get; set; }
    public decimal MaxAllowedAmount { get; set; }
    public decimal RequestedAmount { get; set; }
    public bool RequiresBoardApproval { get; set; }
    public string ApprovalLevelName { get; set; } = string.Empty;
    public string Message { get; set; } = string.Empty;
}

public interface IWorkflowGovernanceService
{
    // Maker-Checker & Segregation of Duties (SoD)
    Task<MakerCheckerValidationResult> ValidateMakerCheckerAsync(int taskId, int actorUserId, bool isSuperAdminOverride = false, string? overrideReason = null);

    // Distributed Task Concurrency Leases
    Task<TaskLeaseResult> AcquireTaskLeaseAsync(int taskId, int userId, string userName, int leaseDurationMinutes = 30);
    Task<TaskLeaseResult> RenewTaskLeaseAsync(int taskId, int userId, int extensionMinutes = 30);
    Task<bool> ReleaseTaskLeaseAsync(int taskId, int userId);
    Task<WorkflowTaskLease?> GetActiveTaskLeaseAsync(int taskId);

    // Cryptographic Signoff Attestation (SHA-256)
    Task<WorkflowSignoffAttestation> RecordSignoffAttestationAsync(int? taskId, string entityName, int entityId, int signerUserId, string signerName, string signerRsaId, string signoffRole, string signoffAction, string payloadJson, string? notes = null);
    Task<List<WorkflowSignoffAttestation>> GetAttestationsForEntityAsync(string entityName, int entityId);
    string ComputePayloadSha256(string payloadJson);

    // Time-Bounded Delegations
    Task<List<WorkflowDelegation>> GetActiveDelegationsForUserAsync(int userId);
    Task<List<WorkflowDelegation>> GetAllDelegationsAsync();
    Task<WorkflowDelegation> CreateDelegationAsync(int delegatorUserId, string delegatorUserName, int delegateeUserId, string delegateeUserName, DateTime startDate, DateTime endDate, List<string> allowedModules, string reason, string createdBy);
    Task<bool> RevokeDelegationAsync(int delegationId, string revokedBy);
    Task<bool> IsUserDelegatedForModuleAsync(int delegateeUserId, int delegatorUserId, string moduleCode);

    // Tiered Financial Approval Thresholds
    Task<FinancialThresholdValidationResult> ValidateFinancialApprovalLimitAsync(string roleName, string moduleCode, decimal amount);
    Task<List<FinancialApprovalThreshold>> GetAllFinancialThresholdsAsync();
    Task<FinancialApprovalThreshold> SaveFinancialThresholdAsync(FinancialApprovalThreshold threshold, string savedBy);
}

public class WorkflowGovernanceService : IWorkflowGovernanceService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly ILogger<WorkflowGovernanceService> _logger;

    public WorkflowGovernanceService(
        INsdmsDbContextFactory contextFactory,
        ILogger<WorkflowGovernanceService> logger)
    {
        _contextFactory = contextFactory;
        _logger = logger;
    }

    #region Maker-Checker & Segregation of Duties

    public async Task<MakerCheckerValidationResult> ValidateMakerCheckerAsync(
        int taskId,
        int actorUserId,
        bool isSuperAdminOverride = false,
        string? overrideReason = null)
    {
        await using var db = _contextFactory.CreateDbContext();
        var task = await db.WorkflowTasks.FirstOrDefaultAsync(t => t.Id == taskId);
        if (task == null)
        {
            return new MakerCheckerValidationResult
            {
                IsAllowed = true,
                Message = "Task not found; default pass."
            };
        }

        // Check if actor is the same as the creator
        bool isSelfAction = false;
        if (int.TryParse(task.CreatedBy, out int creatorId) && creatorId == actorUserId)
        {
            isSelfAction = true;
        }
        else if (string.Equals(task.CreatedBy, actorUserId.ToString(), StringComparison.OrdinalIgnoreCase))
        {
            isSelfAction = true;
        }

        if (!isSelfAction)
        {
            return new MakerCheckerValidationResult
            {
                IsAllowed = true,
                IsSelfAction = false,
                TaskCreator = task.CreatedBy,
                Message = "Maker-Checker verified: Approver is distinct from Task Creator."
            };
        }

        // It is a self-action (Maker == Checker)
        if (isSuperAdminOverride)
        {
            if (string.IsNullOrWhiteSpace(overrideReason))
            {
                return new MakerCheckerValidationResult
                {
                    IsAllowed = false,
                    IsSelfAction = true,
                    TaskCreator = task.CreatedBy,
                    Message = "Maker-Checker Violation: SuperAdmin override requires a mandatory justification reason for the audit trail."
                };
            }

            // Double write audit log for the override
            var auditLog = new AuditLog
            {
                EntityName = "WorkflowTask",
                RecordId = taskId,
                ActionName = "SUPERADMIN_MAKER_CHECKER_OVERRIDE",
                Actor = actorUserId.ToString(),
                Timestamp = DateTime.UtcNow,
                MetadataJson = JsonSerializer.Serialize(new
                {
                    taskId,
                    actorUserId,
                    taskCreator = task.CreatedBy,
                    overrideReason,
                    warning = "PFMA Segregation of Duties overridden by SuperAdmin with mandatory justification."
                })
            };

            db.AuditLogs.Add(auditLog);
            await db.SaveChangesAsync();

            _logger.LogWarning("Maker-Checker OVERRIDE recorded for Task {TaskId} by Actor {ActorId}. Reason: {Reason}",
                taskId, actorUserId, overrideReason);

            return new MakerCheckerValidationResult
            {
                IsAllowed = true,
                IsSelfAction = true,
                IsSuperAdminOverride = true,
                TaskCreator = task.CreatedBy,
                Message = $"SuperAdmin override accepted with audit justification: {overrideReason}"
            };
        }

        return new MakerCheckerValidationResult
        {
            IsAllowed = false,
            IsSelfAction = true,
            TaskCreator = task.CreatedBy,
            Message = "Segregation of Duties Violation: You cannot approve or sign off a workflow item that you initiated or created. Another authorized officer must review this record."
        };
    }

    #endregion

    #region Distributed Task Concurrency Leases

    public async Task<TaskLeaseResult> AcquireTaskLeaseAsync(
        int taskId,
        int userId,
        string userName,
        int leaseDurationMinutes = 30)
    {
        await using var db = _contextFactory.CreateDbContext();
        var now = DateTime.UtcNow;

        // Check if there is an active unexpired lease by someone else
        var existingLease = await db.WorkflowTaskLeases
            .Where(l => l.WorkflowTaskId == taskId && !l.IsReleased && l.LeaseExpiryTime > now)
            .OrderByDescending(l => l.LeaseExpiryTime)
            .FirstOrDefaultAsync();

        if (existingLease != null)
        {
            if (existingLease.ClaimedByUserId == userId)
            {
                // Extend the existing lease for this user
                existingLease.LeaseExpiryTime = now.AddMinutes(leaseDurationMinutes);
                existingLease.LastHeartbeatTime = now;
                existingLease.ModifiedAt = now;
                existingLease.ModifiedBy = userName;
                await db.SaveChangesAsync();

                return new TaskLeaseResult
                {
                    IsSuccess = true,
                    Lease = existingLease,
                    Message = "Task lock refreshed."
                };
            }

            // Claimed by another active user
            return new TaskLeaseResult
            {
                IsSuccess = false,
                IsAlreadyClaimedByOther = true,
                Lease = existingLease,
                Message = $"Task is currently locked by {existingLease.ClaimedByUserName} until {existingLease.LeaseExpiryTime.ToLocalTime():HH:mm:ss}."
            };
        }

        // Create new lease
        var newLease = new WorkflowTaskLease
        {
            WorkflowTaskId = taskId,
            ClaimedByUserId = userId,
            ClaimedByUserName = userName,
            LeaseStartTime = now,
            LeaseExpiryTime = now.AddMinutes(leaseDurationMinutes),
            LastHeartbeatTime = now,
            IsReleased = false,
            CreatedBy = userName,
            CreatedAt = now
        };

        db.WorkflowTaskLeases.Add(newLease);
        await db.SaveChangesAsync();

        return new TaskLeaseResult
        {
            IsSuccess = true,
            Lease = newLease,
            Message = $"Task lock successfully acquired for {leaseDurationMinutes} minutes."
        };
    }

    public async Task<TaskLeaseResult> RenewTaskLeaseAsync(int taskId, int userId, int extensionMinutes = 30)
    {
        await using var db = _contextFactory.CreateDbContext();
        var now = DateTime.UtcNow;

        var lease = await db.WorkflowTaskLeases
            .Where(l => l.WorkflowTaskId == taskId && l.ClaimedByUserId == userId && !l.IsReleased)
            .OrderByDescending(l => l.LeaseExpiryTime)
            .FirstOrDefaultAsync();

        if (lease == null)
        {
            return new TaskLeaseResult
            {
                IsSuccess = false,
                Message = "No active lock lease found for renewal."
            };
        }

        lease.LeaseExpiryTime = now.AddMinutes(extensionMinutes);
        lease.LastHeartbeatTime = now;
        lease.ModifiedAt = now;
        await db.SaveChangesAsync();

        return new TaskLeaseResult
        {
            IsSuccess = true,
            Lease = lease,
            Message = "Lock renewed."
        };
    }

    public async Task<bool> ReleaseTaskLeaseAsync(int taskId, int userId)
    {
        await using var db = _contextFactory.CreateDbContext();
        var leases = await db.WorkflowTaskLeases
            .Where(l => l.WorkflowTaskId == taskId && l.ClaimedByUserId == userId && !l.IsReleased)
            .ToListAsync();

        if (leases.Count == 0) return false;

        foreach (var l in leases)
        {
            l.IsReleased = true;
            l.ModifiedAt = DateTime.UtcNow;
        }

        await db.SaveChangesAsync();
        return true;
    }

    public async Task<WorkflowTaskLease?> GetActiveTaskLeaseAsync(int taskId)
    {
        await using var db = _contextFactory.CreateDbContext();
        var now = DateTime.UtcNow;

        return await db.WorkflowTaskLeases
            .Where(l => l.WorkflowTaskId == taskId && !l.IsReleased && l.LeaseExpiryTime > now)
            .OrderByDescending(l => l.LeaseExpiryTime)
            .FirstOrDefaultAsync();
    }

    #endregion

    #region Cryptographic Signoff Attestation

    public string ComputePayloadSha256(string payloadJson)
    {
        if (string.IsNullOrEmpty(payloadJson))
            payloadJson = "{}";

        using var sha256 = SHA256.Create();
        var bytes = Encoding.UTF8.GetBytes(payloadJson);
        var hashBytes = sha256.ComputeHash(bytes);
        return Convert.ToHexString(hashBytes).ToLowerInvariant();
    }

    public async Task<WorkflowSignoffAttestation> RecordSignoffAttestationAsync(
        int? taskId,
        string entityName,
        int entityId,
        int signerUserId,
        string signerName,
        string signerRsaId,
        string signoffRole,
        string signoffAction,
        string payloadJson,
        string? notes = null)
    {
        await using var db = _contextFactory.CreateDbContext();
        var checksum = ComputePayloadSha256(payloadJson);
        var now = DateTime.UtcNow;

        var attestation = new WorkflowSignoffAttestation
        {
            WorkflowTaskId = taskId,
            EntityName = entityName,
            EntityId = entityId,
            SignerUserId = signerUserId,
            SignerName = signerName,
            SignerRsaId = signerRsaId,
            SignoffRole = signoffRole,
            SignoffAction = signoffAction,
            DocumentSha256Checksum = checksum,
            AttestationNotes = notes ?? string.Empty,
            SignedAt = now,
            CreatedAt = now,
            CreatedBy = signerName
        };

        db.WorkflowSignoffAttestations.Add(attestation);

        // Also double write to AuditLog with checksum
        var audit = new AuditLog
        {
            EntityName = entityName,
            RecordId = entityId,
            ActionName = $"SIGNOFF_{signoffAction.ToUpperInvariant()}",
            Actor = signerName,
            Timestamp = now,
            MetadataJson = JsonSerializer.Serialize(new
            {
                taskId,
                signerUserId,
                signerName,
                signerRsaId,
                signoffRole,
                signoffAction,
                documentSha256 = checksum,
                notes
            })
        };

        db.AuditLogs.Add(audit);
        await db.SaveChangesAsync();

        _logger.LogInformation("Cryptographic signoff attestation recorded for {Entity}:{EntityId} (Checksum: {Checksum})",
            entityName, entityId, checksum);

        return attestation;
    }

    public async Task<List<WorkflowSignoffAttestation>> GetAttestationsForEntityAsync(string entityName, int entityId)
    {
        await using var db = _contextFactory.CreateDbContext();
        return await db.WorkflowSignoffAttestations
            .Where(a => a.EntityName == entityName && a.EntityId == entityId)
            .OrderByDescending(a => a.SignedAt)
            .ToListAsync();
    }

    #endregion

    #region Time-Bounded Delegations

    public async Task<List<WorkflowDelegation>> GetActiveDelegationsForUserAsync(int userId)
    {
        await using var db = _contextFactory.CreateDbContext();
        var now = DateTime.UtcNow;

        return await db.WorkflowDelegations
            .Where(d => d.DelegateeUserId == userId && d.IsActive && d.StartDate <= now && d.EndDate >= now)
            .OrderByDescending(d => d.CreatedAt)
            .ToListAsync();
    }

    public async Task<List<WorkflowDelegation>> GetAllDelegationsAsync()
    {
        await using var db = _contextFactory.CreateDbContext();
        return await db.WorkflowDelegations
            .OrderByDescending(d => d.CreatedAt)
            .ToListAsync();
    }

    public async Task<WorkflowDelegation> CreateDelegationAsync(
        int delegatorUserId,
        string delegatorUserName,
        int delegateeUserId,
        string delegateeUserName,
        DateTime startDate,
        DateTime endDate,
        List<string> allowedModules,
        string reason,
        string createdBy)
    {
        await using var db = _contextFactory.CreateDbContext();
        var now = DateTime.UtcNow;

        var delegation = new WorkflowDelegation
        {
            DelegatorUserId = delegatorUserId,
            DelegatorUserName = delegatorUserName,
            DelegateeUserId = delegateeUserId,
            DelegateeUserName = delegateeUserName,
            StartDate = startDate,
            EndDate = endDate,
            AllowedModulesJson = JsonSerializer.Serialize(allowedModules.Count > 0 ? allowedModules : new List<string> { "*" }),
            Reason = reason,
            IsActive = true,
            CreatedAt = now,
            CreatedBy = createdBy
        };

        db.WorkflowDelegations.Add(delegation);

        // Double write to AuditLog
        var audit = new AuditLog
        {
            EntityName = "WorkflowDelegation",
            RecordId = delegation.Id,
            ActionName = "DELEGATION_CREATED",
            Actor = createdBy,
            Timestamp = now,
            MetadataJson = JsonSerializer.Serialize(new
            {
                delegatorUserId,
                delegatorUserName,
                delegateeUserId,
                delegateeUserName,
                startDate,
                endDate,
                allowedModules,
                reason
            })
        };

        db.AuditLogs.Add(audit);
        await db.SaveChangesAsync();

        return delegation;
    }

    public async Task<bool> RevokeDelegationAsync(int delegationId, string revokedBy)
    {
        await using var db = _contextFactory.CreateDbContext();
        var delegation = await db.WorkflowDelegations.FirstOrDefaultAsync(d => d.Id == delegationId);
        if (delegation == null) return false;

        delegation.IsActive = false;
        delegation.ModifiedAt = DateTime.UtcNow;
        delegation.ModifiedBy = revokedBy;

        var audit = new AuditLog
        {
            EntityName = "WorkflowDelegation",
            RecordId = delegationId,
            ActionName = "DELEGATION_REVOKED",
            Actor = revokedBy,
            Timestamp = DateTime.UtcNow,
            MetadataJson = JsonSerializer.Serialize(new
            {
                delegationId,
                delegatorUserId = delegation.DelegatorUserId,
                delegateeUserId = delegation.DelegateeUserId,
                revokedBy
            })
        };

        db.AuditLogs.Add(audit);
        await db.SaveChangesAsync();

        return true;
    }

    public async Task<bool> IsUserDelegatedForModuleAsync(int delegateeUserId, int delegatorUserId, string moduleCode)
    {
        await using var db = _contextFactory.CreateDbContext();
        var now = DateTime.UtcNow;

        var delegation = await db.WorkflowDelegations
            .Where(d => d.DelegateeUserId == delegateeUserId &&
                        d.DelegatorUserId == delegatorUserId &&
                        d.IsActive &&
                        d.StartDate <= now &&
                        d.EndDate >= now)
            .FirstOrDefaultAsync();

        if (delegation == null) return false;

        try
        {
            var modules = JsonSerializer.Deserialize<List<string>>(delegation.AllowedModulesJson);
            if (modules == null || modules.Contains("*") || modules.Any(m => string.Equals(m, moduleCode, StringComparison.OrdinalIgnoreCase)))
            {
                return true;
            }
        }
        catch
        {
            return true;
        }

        return false;
    }

    #endregion

    #region Tiered Financial Approval Thresholds

    public async Task<FinancialThresholdValidationResult> ValidateFinancialApprovalLimitAsync(
        string roleName,
        string moduleCode,
        decimal amount)
    {
        await using var db = _contextFactory.CreateDbContext();

        var threshold = await db.FinancialApprovalThresholds
            .Where(t => t.RoleName == roleName && t.ModuleCode == moduleCode && t.IsActive)
            .OrderByDescending(t => t.MaxApprovalAmount)
            .FirstOrDefaultAsync();

        if (threshold == null)
        {
            // If no explicit limit configured, check if Admin/SuperAdmin
            if (string.Equals(roleName, "SuperAdmin", StringComparison.OrdinalIgnoreCase) ||
                string.Equals(roleName, "Admin", StringComparison.OrdinalIgnoreCase))
            {
                return new FinancialThresholdValidationResult
                {
                    IsAllowed = true,
                    MaxAllowedAmount = 999999999m,
                    RequestedAmount = amount,
                    RequiresBoardApproval = false,
                    ApprovalLevelName = "Executive Full Authority",
                    Message = "Executive unlimited approval authority."
                };
            }

            return new FinancialThresholdValidationResult
            {
                IsAllowed = false,
                MaxAllowedAmount = 0,
                RequestedAmount = amount,
                RequiresBoardApproval = false,
                ApprovalLevelName = "None",
                Message = $"No financial approval threshold is configured for role '{roleName}' on module '{moduleCode}'."
            };
        }

        if (amount <= threshold.MaxApprovalAmount)
        {
            return new FinancialThresholdValidationResult
            {
                IsAllowed = true,
                MaxAllowedAmount = threshold.MaxApprovalAmount,
                RequestedAmount = amount,
                RequiresBoardApproval = threshold.RequiresBoardApproval,
                ApprovalLevelName = threshold.ApprovalLevelName,
                Message = $"Approved within {threshold.ApprovalLevelName} limit of R{threshold.MaxApprovalAmount:N2}."
            };
        }

        return new FinancialThresholdValidationResult
        {
            IsAllowed = false,
            MaxAllowedAmount = threshold.MaxApprovalAmount,
            RequestedAmount = amount,
            RequiresBoardApproval = threshold.RequiresBoardApproval,
            ApprovalLevelName = threshold.ApprovalLevelName,
            Message = $"Requested amount of R{amount:N2} exceeds your {threshold.ApprovalLevelName} approval limit of R{threshold.MaxApprovalAmount:N2}. Escalation to higher authority required."
        };
    }

    public async Task<List<FinancialApprovalThreshold>> GetAllFinancialThresholdsAsync()
    {
        await using var db = _contextFactory.CreateDbContext();
        return await db.FinancialApprovalThresholds
            .OrderBy(t => t.ModuleCode)
            .ThenBy(t => t.MaxApprovalAmount)
            .ToListAsync();
    }

    public async Task<FinancialApprovalThreshold> SaveFinancialThresholdAsync(FinancialApprovalThreshold threshold, string savedBy)
    {
        await using var db = _contextFactory.CreateDbContext();
        var now = DateTime.UtcNow;

        if (threshold.Id == 0)
        {
            threshold.CreatedAt = now;
            threshold.CreatedBy = savedBy;
            db.FinancialApprovalThresholds.Add(threshold);
        }
        else
        {
            var existing = await db.FinancialApprovalThresholds.FirstOrDefaultAsync(t => t.Id == threshold.Id);
            if (existing != null)
            {
                existing.RoleName = threshold.RoleName;
                existing.ModuleCode = threshold.ModuleCode;
                existing.ApprovalLevelName = threshold.ApprovalLevelName;
                existing.MaxApprovalAmount = threshold.MaxApprovalAmount;
                existing.RequiresBoardApproval = threshold.RequiresBoardApproval;
                existing.Description = threshold.Description;
                existing.IsActive = threshold.IsActive;
                existing.ModifiedAt = now;
                existing.ModifiedBy = savedBy;
            }
        }

        var audit = new AuditLog
        {
            EntityName = "FinancialApprovalThreshold",
            RecordId = threshold.Id,
            ActionName = threshold.Id == 0 ? "THRESHOLD_CREATED" : "THRESHOLD_UPDATED",
            Actor = savedBy,
            Timestamp = now,
            MetadataJson = JsonSerializer.Serialize(threshold)
        };

        db.AuditLogs.Add(audit);
        await db.SaveChangesAsync();

        return threshold;
    }

    #endregion
}
