using Nsdms.Domain.Entities;

namespace Nsdms.Application.Common.Interfaces;

public interface IReviewCommitteeService
{
    Task<ReviewCommitteeMeeting> ScheduleMeetingAsync(ReviewCommitteeMeeting meeting, string currentUsername = "SYSTEM");
    Task<ReviewCommitteeMeetingAgenda> AddAgendaItemAsync(ReviewCommitteeMeetingAgenda agenda, string currentUsername = "SYSTEM");
    Task<ReviewCommitteeMeetingMember> AddMemberAsync(ReviewCommitteeMeetingMember member, string currentUsername = "SYSTEM");
    Task<ReviewCommitteeMeetingAgenda> RecordAgendaDecisionAsync(int agendaId, string decisionCode, string decisionNotes, int yesVotes, int noVotes, int abstainVotes, string currentUsername = "SYSTEM");
    Task<ReviewCommitteeMeeting> ConcludeMeetingAsync(int meetingId, string currentUsername = "SYSTEM");
    Task<ReviewCommitteeMeeting?> GetMeetingByIdAsync(int meetingId);
    Task<List<ReviewCommitteeMeeting>> GetAllMeetingsAsync(string? meetingTypeCode = null, string? statusCode = null);

    Task<AssessorModeratorApplication> SubmitAssessorModeratorApplicationAsync(AssessorModeratorApplication application, string currentUsername = "SYSTEM");
    Task<AssessorModeratorApplication> AdjudicateAssessorModeratorApplicationAsync(int applicationId, bool isApproved, string? certificateNumber, string currentUsername = "SYSTEM");

    Task<SdpScopeExtensionApplication> SubmitSdpScopeExtensionAsync(SdpScopeExtensionApplication application, string currentUsername = "SYSTEM");
    Task<SdpScopeExtensionApplication> AdjudicateSdpScopeExtensionAsync(int applicationId, bool isApproved, string recommendationNotes, string currentUsername = "SYSTEM");
}
