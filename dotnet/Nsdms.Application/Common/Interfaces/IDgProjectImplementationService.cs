using Nsdms.Domain.Entities;

namespace Nsdms.Application.Common.Interfaces;

public interface IDgProjectImplementationService
{
    Task<ProjectImplementationPlan> CreatePipAsync(ProjectImplementationPlan pip, List<PipLearnerAllocation> allocations, string currentUsername = "SYSTEM");
    Task<ProjectImplementationPlan> SignOffContractsAsync(int pipId, string currentUsername = "SYSTEM");
    Task<ProjectImplementationPlan?> GetPipByIdAsync(int pipId);
    Task<List<ProjectImplementationPlan>> GetAllPipsAsync(int? organisationId = null, string? statusCode = null);

    Task<GrantPaymentClaim> SubmitPaymentClaimAsync(GrantPaymentClaim claim, string currentUsername = "SYSTEM");
    Task<GrantPaymentClaim> ApproveAndDisburseClaimAsync(int claimId, string currentUsername = "SYSTEM");
}
