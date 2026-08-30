using Nsdms.Domain.Entities;

namespace Nsdms.Application.Common.Interfaces;

public interface IWorkplaceMonitoringService
{
    Task<WorkplaceMonitoringSiteVisit> CreateSiteVisitAsync(WorkplaceMonitoringSiteVisit siteVisit, string currentUsername = "SYSTEM");
    Task<WorkplaceMonitoringSiteVisit> UpdateSiteVisitAsync(WorkplaceMonitoringSiteVisit siteVisit, string currentUsername = "SYSTEM");
    Task<WorkplaceMonitoringSiteVisit> SubmitForApprovalAsync(int siteVisitId, string currentUsername = "SYSTEM");
    Task<WorkplaceMonitoringSiteVisit> ApproveSiteVisitAsync(int siteVisitId, string comments, string currentUsername = "SYSTEM");
    Task<WorkplaceMonitoringSiteVisit> FlagNonComplianceAsync(int siteVisitId, string nonComplianceNotes, string currentUsername = "SYSTEM");
    Task<bool> DeleteSiteVisitAsync(int siteVisitId, string currentUsername = "SYSTEM");

    Task<WorkplaceMonitoringSiteVisit?> GetSiteVisitByIdAsync(int id);
    Task<List<WorkplaceMonitoringSiteVisit>> GetAllSiteVisitsAsync(int? organisationId = null, string? statusCode = null);

    Task<List<WorkplaceMonitoringComplianceSurvey>> SaveComplianceSurveysAsync(int siteVisitId, List<WorkplaceMonitoringComplianceSurvey> surveys, string currentUsername = "SYSTEM");
    Task<WorkplaceMonitoringActionPlan> AddActionPlanAsync(WorkplaceMonitoringActionPlan actionPlan, string currentUsername = "SYSTEM");
    Task<WorkplaceMonitoringActionPlan> ResolveActionPlanAsync(int actionPlanId, string resolutionNotes, string currentUsername = "SYSTEM");
    Task<WorkplaceMonitoringMitigationPlan> AddMitigationPlanAsync(WorkplaceMonitoringMitigationPlan mitigationPlan, string currentUsername = "SYSTEM");
    Task<WorkplaceMonitoringLearnerSurvey> AddLearnerSurveyAsync(WorkplaceMonitoringLearnerSurvey survey, string currentUsername = "SYSTEM");
}
