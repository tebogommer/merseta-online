using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class ReviewCommitteeService : IReviewCommitteeService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly AuditService _audit;

    public ReviewCommitteeService(INsdmsDbContextFactory contextFactory, AuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    public async Task<ReviewCommitteeMeeting> ScheduleMeetingAsync(ReviewCommitteeMeeting meeting, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        meeting.MeetingNumber = $"RCM-{DateTime.UtcNow:yyyyMMdd}-{Guid.NewGuid().ToString()[..4].ToUpper()}";
        meeting.StatusCode = "Scheduled";
        meeting.CreatedAt = DateTime.UtcNow;
        meeting.CreatedBy = currentUsername;

        db.ReviewCommitteeMeetings.Add(meeting);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "ReviewCommitteeMeeting", meeting.Id, "ScheduleMeeting", currentUsername, null, meeting);
        await db.SaveChangesAsync();

        return meeting;
    }

    public async Task<ReviewCommitteeMeetingAgenda> AddAgendaItemAsync(ReviewCommitteeMeetingAgenda agenda, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        agenda.DecisionCode = "Pending";
        agenda.CreatedAt = DateTime.UtcNow;
        agenda.CreatedBy = currentUsername;

        db.ReviewCommitteeMeetingAgendas.Add(agenda);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "ReviewCommitteeMeetingAgenda", agenda.Id, "AddAgendaItem", currentUsername, null, agenda);
        await db.SaveChangesAsync();

        return agenda;
    }

    public async Task<ReviewCommitteeMeetingMember> AddMemberAsync(ReviewCommitteeMeetingMember member, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        member.CreatedAt = DateTime.UtcNow;
        member.CreatedBy = currentUsername;

        db.ReviewCommitteeMeetingMembers.Add(member);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "ReviewCommitteeMeetingMember", member.Id, "AddMember", currentUsername, null, member);
        await db.SaveChangesAsync();

        return member;
    }

    public async Task<ReviewCommitteeMeetingAgenda> RecordAgendaDecisionAsync(
        int agendaId,
        string decisionCode,
        string decisionNotes,
        int yesVotes,
        int noVotes,
        int abstainVotes,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var agenda = await db.ReviewCommitteeMeetingAgendas.FindAsync(agendaId);
        if (agenda == null) throw new KeyNotFoundException($"ReviewCommitteeMeetingAgenda with ID {agendaId} not found.");

        var before = new { agenda.DecisionCode, agenda.VotedYesCount, agenda.VotedNoCount, agenda.AbstainCount };
        agenda.DecisionCode = decisionCode;
        agenda.DecisionNotes = decisionNotes;
        agenda.VotedYesCount = yesVotes;
        agenda.VotedNoCount = noVotes;
        agenda.AbstainCount = abstainVotes;
        agenda.ModifiedAt = DateTime.UtcNow;
        agenda.ModifiedBy = currentUsername;

        _audit.LogAction(db, "ReviewCommitteeMeetingAgenda", agenda.Id, "RecordAgendaDecision", currentUsername, before, agenda);
        await db.SaveChangesAsync();

        return agenda;
    }

    public async Task<ReviewCommitteeMeeting> ConcludeMeetingAsync(int meetingId, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var meeting = await db.ReviewCommitteeMeetings.FindAsync(meetingId);
        if (meeting == null) throw new KeyNotFoundException($"ReviewCommitteeMeeting with ID {meetingId} not found.");

        var before = new { meeting.StatusCode };
        meeting.StatusCode = "Concluded";
        meeting.ModifiedAt = DateTime.UtcNow;
        meeting.ModifiedBy = currentUsername;

        _audit.LogAction(db, "ReviewCommitteeMeeting", meeting.Id, "ConcludeMeeting", currentUsername, before, meeting);
        await db.SaveChangesAsync();

        return meeting;
    }

    public async Task<ReviewCommitteeMeeting?> GetMeetingByIdAsync(int meetingId)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.ReviewCommitteeMeetings
            .Include(m => m.Agendas.OrderBy(a => a.ItemNumber))
            .Include(m => m.Members)
                .ThenInclude(mem => mem.Person)
            .FirstOrDefaultAsync(m => m.Id == meetingId);
    }

    public async Task<List<ReviewCommitteeMeeting>> GetAllMeetingsAsync(string? meetingTypeCode = null, string? statusCode = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.ReviewCommitteeMeetings
            .Include(m => m.Agendas)
            .Include(m => m.Members)
            .AsQueryable();

        if (!string.IsNullOrWhiteSpace(meetingTypeCode))
        {
            query = query.Where(m => m.MeetingTypeCode == meetingTypeCode);
        }

        if (!string.IsNullOrWhiteSpace(statusCode))
        {
            query = query.Where(m => m.StatusCode == statusCode);
        }

        return await query.OrderByDescending(m => m.FromDateTime).ToListAsync();
    }

    public async Task<AssessorModeratorApplication> SubmitAssessorModeratorApplicationAsync(AssessorModeratorApplication application, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        application.ApplicationNumber = $"AMA-{DateTime.UtcNow:yyyyMM}-{Guid.NewGuid().ToString()[..4].ToUpper()}";
        application.StatusCode = "RecommendedForCommittee";
        application.CreatedAt = DateTime.UtcNow;
        application.CreatedBy = currentUsername;

        db.AssessorModeratorApplications.Add(application);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "AssessorModeratorApplication", application.Id, "SubmitAssessorModeratorApplication", currentUsername, null, application);
        await db.SaveChangesAsync();

        return application;
    }

    public async Task<AssessorModeratorApplication> AdjudicateAssessorModeratorApplicationAsync(int applicationId, bool isApproved, string? certificateNumber, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var app = await db.AssessorModeratorApplications.FindAsync(applicationId);
        if (app == null) throw new KeyNotFoundException($"AssessorModeratorApplication with ID {applicationId} not found.");

        var before = new { app.StatusCode, app.CertificateNumber };
        app.StatusCode = isApproved ? "Approved" : "Rejected";
        if (isApproved)
        {
            app.CertificateNumber = certificateNumber ?? $"CERT-ETQA-{DateTime.UtcNow:yyyy}-{Guid.NewGuid().ToString()[..4].ToUpper()}";
            app.ExpiryDate = DateTime.UtcNow.AddYears(3);
        }
        app.ModifiedAt = DateTime.UtcNow;
        app.ModifiedBy = currentUsername;

        _audit.LogAction(db, "AssessorModeratorApplication", app.Id, "AdjudicateAssessorModeratorApplication", currentUsername, before, app);
        await db.SaveChangesAsync();

        return app;
    }

    public async Task<SdpScopeExtensionApplication> SubmitSdpScopeExtensionAsync(SdpScopeExtensionApplication application, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        application.ApplicationNumber = $"SDP-SCOPE-{DateTime.UtcNow:yyyyMM}-{Guid.NewGuid().ToString()[..4].ToUpper()}";
        application.StatusCode = "CommitteeReview";
        application.CreatedAt = DateTime.UtcNow;
        application.CreatedBy = currentUsername;

        db.SdpScopeExtensionApplications.Add(application);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "SdpScopeExtensionApplication", application.Id, "SubmitSdpScopeExtension", currentUsername, null, application);
        await db.SaveChangesAsync();

        return application;
    }

    public async Task<SdpScopeExtensionApplication> AdjudicateSdpScopeExtensionAsync(int applicationId, bool isApproved, string recommendationNotes, string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var app = await db.SdpScopeExtensionApplications.FindAsync(applicationId);
        if (app == null) throw new KeyNotFoundException($"SdpScopeExtensionApplication with ID {applicationId} not found.");

        var before = new { app.StatusCode, app.RecommendationNotes };
        app.StatusCode = isApproved ? "Approved" : "Rejected";
        app.RecommendationNotes = recommendationNotes;
        app.ModifiedAt = DateTime.UtcNow;
        app.ModifiedBy = currentUsername;

        _audit.LogAction(db, "SdpScopeExtensionApplication", app.Id, "AdjudicateSdpScopeExtension", currentUsername, before, app);
        await db.SaveChangesAsync();

        return app;
    }
}
