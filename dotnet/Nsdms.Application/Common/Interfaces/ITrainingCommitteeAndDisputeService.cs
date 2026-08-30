using Nsdms.Domain.Entities;

namespace Nsdms.Application.Common.Interfaces;

public interface ITrainingCommitteeAndDisputeService
{
    Task<TrainingCommittee> RegisterCommitteeAsync(TrainingCommittee committee, List<TrainingCommitteeMember> members, string currentUsername = "SYSTEM");
    Task<TrainingCommittee?> GetCommitteeByOrgAsync(int organisationId, int? financialYear = null);
    Task<List<TrainingCommittee>> GetAllCommitteesAsync(int? financialYear = null);

    Task<WspDispute> LogDisputeAsync(WspDispute dispute, string currentUsername = "SYSTEM");
    Task<WspDispute> ResolveDisputeAsync(int disputeId, string resolutionNotes, string currentUsername = "SYSTEM");
    Task<List<WspDispute>> GetAllDisputesAsync(int? organisationId = null, string? statusCode = null);

    Task<WspSkillsGap> RecordSkillsGapAsync(WspSkillsGap skillsGap, string currentUsername = "SYSTEM");
    Task<List<WspSkillsGap>> GetSkillsGapsByOrgAsync(int organisationId, int? financialYear = null);
}
