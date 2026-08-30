using Microsoft.EntityFrameworkCore;
using Nsdms.Domain.Entities;

using Nsdms.Application.Common;

namespace Nsdms.Infrastructure.Data;

public static class WorkflowDefinitionSeeder
{
    public static async Task SeedWorkflowDefinitionsAsync(INsdmsDbContext context)
    {
        if (await context.WorkflowDefinitions.AnyAsync())
        {
            return;
        }

        // 1. PROVIDER: Training Provider Accreditation
        var providerDef = new WorkflowDefinition
        {
            Code = "PROVIDER",
            Name = "Training Provider Accreditation",
            TargetEntityName = "TrainingProvider",
            KeyFieldName = "Id",
            IsActive = true
        };
        context.WorkflowDefinitions.Add(providerDef);
        await context.SaveChangesAsync();

        var provS1 = new WorkflowState { WorkflowDefinitionId = providerDef.Id, StateCode = "DRAFT", StateName = "Draft Application", StepOrder = 1, IsInitial = true, AllowedGroupRole = "Provider" };
        var provS2 = new WorkflowState { WorkflowDefinitionId = providerDef.Id, StateCode = "DESK_REVIEW", StateName = "Under Desktop Review", StepOrder = 2, AllowedGroupRole = "Client Liaison Officer (CLO)" };
        var provS3 = new WorkflowState { WorkflowDefinitionId = providerDef.Id, StateCode = "SITE_AUDIT", StateName = "Site Audit Scheduled", StepOrder = 3, AllowedGroupRole = "Client Liaison Officer (CLO)" };
        var provS4 = new WorkflowState { WorkflowDefinitionId = providerDef.Id, StateCode = "ETQA_COMMITTEE", StateName = "Pending Committee Approval", StepOrder = 4, AllowedGroupRole = "Quality Assurance Manager" };
        var provS5 = new WorkflowState { WorkflowDefinitionId = providerDef.Id, StateCode = "ACCREDITED", StateName = "Accredited", StepOrder = 5, IsTerminal = true };
        var provS6 = new WorkflowState { WorkflowDefinitionId = providerDef.Id, StateCode = "REJECTED", StateName = "Accreditation Rejected", StepOrder = 6, IsTerminal = true };

        context.WorkflowStates.AddRange(provS1, provS2, provS3, provS4, provS5, provS6);
        await context.SaveChangesAsync();

        context.WorkflowTransitions.AddRange(
            new WorkflowTransition { WorkflowDefinitionId = providerDef.Id, FromStateId = provS1.Id, ToStateId = provS2.Id, ActionName = "Submit for Desktop Review", ButtonColor = "#1e40af", ButtonIcon = "Send", NewEntityStatusCode = "UNDER_REVIEW" },
            new WorkflowTransition { WorkflowDefinitionId = providerDef.Id, FromStateId = provS2.Id, ToStateId = provS3.Id, ActionName = "Schedule Site Audit", ButtonColor = "#0284c7", ButtonIcon = "Event", RequiredPermission = "SCHEDULE_SITE_VISIT", NewEntityStatusCode = "AUDIT_SCHEDULED" },
            new WorkflowTransition { WorkflowDefinitionId = providerDef.Id, FromStateId = provS3.Id, ToStateId = provS4.Id, ActionName = "Recommend for Committee", ButtonColor = "#059669", ButtonIcon = "ThumbUp", RequiredPermission = "RECOMMEND_PROVIDER", NewEntityStatusCode = "PENDING_COMMITTEE" },
            new WorkflowTransition { WorkflowDefinitionId = providerDef.Id, FromStateId = provS4.Id, ToStateId = provS5.Id, ActionName = "Grant Accreditation", ButtonColor = "#16a34a", ButtonIcon = "Verified", RequiredPermission = "ACCREDIT_PROVIDER", NewEntityStatusCode = "ACCREDITED" },
            new WorkflowTransition { WorkflowDefinitionId = providerDef.Id, FromStateId = provS4.Id, ToStateId = provS6.Id, ActionName = "Reject Accreditation", ButtonColor = "#dc2626", ButtonIcon = "Cancel", RequiredPermission = "REVOKE_PROVIDER_ACCREDITATION", RequiresComments = true, NewEntityStatusCode = "REJECTED" }
        );

        // 2. WSP: Workplace Skills Plan Submission
        var wspDef = new WorkflowDefinition
        {
            Code = "WSP",
            Name = "Workplace Skills Plan Submission",
            TargetEntityName = "WspSubmission",
            KeyFieldName = "Id",
            IsActive = true
        };
        context.WorkflowDefinitions.Add(wspDef);
        await context.SaveChangesAsync();

        var wspS1 = new WorkflowState { WorkflowDefinitionId = wspDef.Id, StateCode = "DRAFT", StateName = "Draft WSP", StepOrder = 1, IsInitial = true, AllowedGroupRole = "Primary SDF" };
        var wspS2 = new WorkflowState { WorkflowDefinitionId = wspDef.Id, StateCode = "SDF_SIGNED", StateName = "Signed by SDF", StepOrder = 2, AllowedGroupRole = "Primary SDF" };
        var wspS3 = new WorkflowState { WorkflowDefinitionId = wspDef.Id, StateCode = "LABOUR_SIGNED", StateName = "Signed by Organised Labour", StepOrder = 3, AllowedGroupRole = "Organised Labour" };
        var wspS4 = new WorkflowState { WorkflowDefinitionId = wspDef.Id, StateCode = "CLO_REVIEW", StateName = "Under CLO Review", StepOrder = 4, AllowedGroupRole = "Client Liaison Officer (CLO)" };
        var wspS5 = new WorkflowState { WorkflowDefinitionId = wspDef.Id, StateCode = "APPROVED", StateName = "Approved (Mandatory Grant Awarded)", StepOrder = 5, IsTerminal = true };
        var wspS6 = new WorkflowState { WorkflowDefinitionId = wspDef.Id, StateCode = "REJECTED", StateName = "WSP Rejected", StepOrder = 6, IsTerminal = true };

        context.WorkflowStates.AddRange(wspS1, wspS2, wspS3, wspS4, wspS5, wspS6);
        await context.SaveChangesAsync();

        context.WorkflowTransitions.AddRange(
            new WorkflowTransition { WorkflowDefinitionId = wspDef.Id, FromStateId = wspS1.Id, ToStateId = wspS2.Id, ActionName = "Sign and Submit (SDF)", ButtonColor = "#1e40af", ButtonIcon = "Draw", NewEntityStatusCode = "SDF_SIGNED" },
            new WorkflowTransition { WorkflowDefinitionId = wspDef.Id, FromStateId = wspS2.Id, ToStateId = wspS3.Id, ActionName = "Labour Endorsement", ButtonColor = "#0284c7", ButtonIcon = "Group", NewEntityStatusCode = "LABOUR_SIGNED" },
            new WorkflowTransition { WorkflowDefinitionId = wspDef.Id, FromStateId = wspS3.Id, ToStateId = wspS4.Id, ActionName = "Submit to MerSETA", ButtonColor = "#059669", ButtonIcon = "Send", NewEntityStatusCode = "SUBMITTED" },
            new WorkflowTransition { WorkflowDefinitionId = wspDef.Id, FromStateId = wspS4.Id, ToStateId = wspS5.Id, ActionName = "Approve WSP & Grant", ButtonColor = "#16a34a", ButtonIcon = "CheckCircle", RequiredPermission = "APPROVE_WSP", NewEntityStatusCode = "APPROVED" },
            new WorkflowTransition { WorkflowDefinitionId = wspDef.Id, FromStateId = wspS4.Id, ToStateId = wspS6.Id, ActionName = "Reject WSP Submission", ButtonColor = "#dc2626", ButtonIcon = "Block", RequiredPermission = "REJECT_WSP", RequiresComments = true, NewEntityStatusCode = "REJECTED" }
        );

        // 3. DG: Discretionary Grant Application
        var dgDef = new WorkflowDefinition
        {
            Code = "DG",
            Name = "Discretionary Grant Application",
            TargetEntityName = "GrantApplication",
            KeyFieldName = "Id",
            IsActive = true
        };
        context.WorkflowDefinitions.Add(dgDef);
        await context.SaveChangesAsync();

        var dgS1 = new WorkflowState { WorkflowDefinitionId = dgDef.Id, StateCode = "APPLIED", StateName = "Application Submitted", StepOrder = 1, IsInitial = true, AllowedGroupRole = "Discretionary Grant Committee" };
        var dgS2 = new WorkflowState { WorkflowDefinitionId = dgDef.Id, StateCode = "TECH_EVAL", StateName = "Technical Evaluation", StepOrder = 2, AllowedGroupRole = "Client Liaison Officer (CLO)" };
        var dgS3 = new WorkflowState { WorkflowDefinitionId = dgDef.Id, StateCode = "COMMITTEE_EVAL", StateName = "Review Committee Adjudication", StepOrder = 3, AllowedGroupRole = "Review Committee Member" };
        var dgS4 = new WorkflowState { WorkflowDefinitionId = dgDef.Id, StateCode = "APPROVED", StateName = "Grant Awarded", StepOrder = 4, IsTerminal = true };
        var dgS5 = new WorkflowState { WorkflowDefinitionId = dgDef.Id, StateCode = "REJECTED", StateName = "Grant Declined", StepOrder = 5, IsTerminal = true };

        context.WorkflowStates.AddRange(dgS1, dgS2, dgS3, dgS4, dgS5);
        await context.SaveChangesAsync();

        context.WorkflowTransitions.AddRange(
            new WorkflowTransition { WorkflowDefinitionId = dgDef.Id, FromStateId = dgS1.Id, ToStateId = dgS2.Id, ActionName = "Start Technical Evaluation", ButtonColor = "#1e40af", ButtonIcon = "Engineering", NewEntityStatusCode = "EVALUATING" },
            new WorkflowTransition { WorkflowDefinitionId = dgDef.Id, FromStateId = dgS2.Id, ToStateId = dgS3.Id, ActionName = "Recommend to Review Committee", ButtonColor = "#0284c7", ButtonIcon = "HowToVote", RequiredPermission = "RECOMMEND_DG_BUDGET", NewEntityStatusCode = "PENDING_COMMITTEE" },
            new WorkflowTransition { WorkflowDefinitionId = dgDef.Id, FromStateId = dgS3.Id, ToStateId = dgS4.Id, ActionName = "Final Grant Approval", ButtonColor = "#16a34a", ButtonIcon = "DoneAll", RequiredPermission = "APPROVE_DG_BUDGET", NewEntityStatusCode = "APPROVED" },
            new WorkflowTransition { WorkflowDefinitionId = dgDef.Id, FromStateId = dgS3.Id, ToStateId = dgS5.Id, ActionName = "Decline Grant Application", ButtonColor = "#dc2626", ButtonIcon = "Close", RequiresComments = true, NewEntityStatusCode = "REJECTED" }
        );

        // 4. WPAPP: Workplace Approval
        var wpDef = new WorkflowDefinition
        {
            Code = "WPAPP",
            Name = "Workplace Approval & Site Audit",
            TargetEntityName = "WorkplaceApproval",
            KeyFieldName = "Id",
            IsActive = true
        };
        context.WorkflowDefinitions.Add(wpDef);
        await context.SaveChangesAsync();

        var wpS1 = new WorkflowState { WorkflowDefinitionId = wpDef.Id, StateCode = "DRAFT", StateName = "Draft Workplace Request", StepOrder = 1, IsInitial = true };
        var wpS2 = new WorkflowState { WorkflowDefinitionId = wpDef.Id, StateCode = "AUDIT_SCHEDULED", StateName = "Site Audit Scheduled", StepOrder = 2, AllowedGroupRole = "Client Liaison Officer (CLO)" };
        var wpS3 = new WorkflowState { WorkflowDefinitionId = wpDef.Id, StateCode = "AUDITED", StateName = "Audit Completed (Tools & Mentors Verified)", StepOrder = 3, AllowedGroupRole = "Client Liaison Officer (CLO)" };
        var wpS4 = new WorkflowState { WorkflowDefinitionId = wpDef.Id, StateCode = "APPROVED", StateName = "Workplace Approved", StepOrder = 4, IsTerminal = true };

        context.WorkflowStates.AddRange(wpS1, wpS2, wpS3, wpS4);
        await context.SaveChangesAsync();

        context.WorkflowTransitions.AddRange(
            new WorkflowTransition { WorkflowDefinitionId = wpDef.Id, FromStateId = wpS1.Id, ToStateId = wpS2.Id, ActionName = "Schedule Physical Inspection", ButtonColor = "#1e40af", ButtonIcon = "Schedule", RequiredPermission = "SCHEDULE_SITE_VISIT", NewEntityStatusCode = "AUDIT_SCHEDULED" },
            new WorkflowTransition { WorkflowDefinitionId = wpDef.Id, FromStateId = wpS2.Id, ToStateId = wpS3.Id, ActionName = "Complete Site Verification", ButtonColor = "#0284c7", ButtonIcon = "FactCheck", NewEntityStatusCode = "AUDITED" },
            new WorkflowTransition { WorkflowDefinitionId = wpDef.Id, FromStateId = wpS3.Id, ToStateId = wpS4.Id, ActionName = "Approve Workplace Accreditation", ButtonColor = "#16a34a", ButtonIcon = "Verified", RequiredPermission = "APPROVE_WORKPLACE", NewEntityStatusCode = "APPROVED" }
        );

        // 5. LRN: Learner Agreement Registration
        var lrnDef = new WorkflowDefinition
        {
            Code = "LRN",
            Name = "Learner Agreement Registration",
            TargetEntityName = "CompanyLearner",
            KeyFieldName = "Id",
            IsActive = true
        };
        context.WorkflowDefinitions.Add(lrnDef);
        await context.SaveChangesAsync();

        var lrnS1 = new WorkflowState { WorkflowDefinitionId = lrnDef.Id, StateCode = "DRAFT", StateName = "Draft Contract", StepOrder = 1, IsInitial = true };
        var lrnS2 = new WorkflowState { WorkflowDefinitionId = lrnDef.Id, StateCode = "QA_CHECK", StateName = "Quality Assurance Verification", StepOrder = 2, AllowedGroupRole = "Client Liaison Officer (CLO)" };
        var lrnS3 = new WorkflowState { WorkflowDefinitionId = lrnDef.Id, StateCode = "REGISTERED", StateName = "Contract Registered", StepOrder = 3, IsTerminal = true };

        context.WorkflowStates.AddRange(lrnS1, lrnS2, lrnS3);
        await context.SaveChangesAsync();

        context.WorkflowTransitions.AddRange(
            new WorkflowTransition { WorkflowDefinitionId = lrnDef.Id, FromStateId = lrnS1.Id, ToStateId = lrnS2.Id, ActionName = "Submit for QA Verification", ButtonColor = "#1e40af", ButtonIcon = "Send", NewEntityStatusCode = "PENDING_QA" },
            new WorkflowTransition { WorkflowDefinitionId = lrnDef.Id, FromStateId = lrnS2.Id, ToStateId = lrnS3.Id, ActionName = "Approve & Register Contract", ButtonColor = "#16a34a", ButtonIcon = "CheckCircle", RequiredPermission = "APPROVE_LEARNER", NewEntityStatusCode = "REGISTERED" }
        );

        // 6. TRADETEST: Trade Test Scheduling & Certification
        var ttDef = new WorkflowDefinition
        {
            Code = "TRADETEST",
            Name = "Trade Test Scheduling & Certification",
            TargetEntityName = "LearnerTradeTest",
            KeyFieldName = "Id",
            IsActive = true
        };
        context.WorkflowDefinitions.Add(ttDef);
        await context.SaveChangesAsync();

        var ttS1 = new WorkflowState { WorkflowDefinitionId = ttDef.Id, StateCode = "APPLIED", StateName = "Trade Test Application", StepOrder = 1, IsInitial = true };
        var ttS2 = new WorkflowState { WorkflowDefinitionId = ttDef.Id, StateCode = "SCHEDULED", StateName = "Test Scheduled at Accredited Center", StepOrder = 2, AllowedGroupRole = "Quality Assurance Manager" };
        var ttS3 = new WorkflowState { WorkflowDefinitionId = ttDef.Id, StateCode = "ASSESSED", StateName = "Assessment Moderated & Completed", StepOrder = 3, AllowedGroupRole = "Quality Assurance Manager" };
        var ttS4 = new WorkflowState { WorkflowDefinitionId = ttDef.Id, StateCode = "CERTIFIED", StateName = "Serial Certificate Issued", StepOrder = 4, IsTerminal = true };

        context.WorkflowStates.AddRange(ttS1, ttS2, ttS3, ttS4);
        await context.SaveChangesAsync();

        context.WorkflowTransitions.AddRange(
            new WorkflowTransition { WorkflowDefinitionId = ttDef.Id, FromStateId = ttS1.Id, ToStateId = ttS2.Id, ActionName = "Assign Center & Schedule", ButtonColor = "#1e40af", ButtonIcon = "EventAvailable", NewEntityStatusCode = "SCHEDULED" },
            new WorkflowTransition { WorkflowDefinitionId = ttDef.Id, FromStateId = ttS2.Id, ToStateId = ttS3.Id, ActionName = "Record Assessment Results", ButtonColor = "#0284c7", ButtonIcon = "Grading", NewEntityStatusCode = "ASSESSED" },
            new WorkflowTransition { WorkflowDefinitionId = ttDef.Id, FromStateId = ttS3.Id, ToStateId = ttS4.Id, ActionName = "Issue Artisan Certificate", ButtonColor = "#16a34a", ButtonIcon = "WorkspacePremium", RequiredPermission = "GENERATE_ARTISAN_CERTIFICATE", NewEntityStatusCode = "CERTIFIED" }
        );

        // Seed Sample Active Workflow Instances and Tasks
        var toyotaWsp = await context.WspSubmissions.FirstOrDefaultAsync(w => w.FinYear == 2026);
        if (toyotaWsp != null)
        {
            var wspInstance = new WorkflowInstance
            {
                WorkflowDefinitionId = wspDef.Id,
                EntityId = toyotaWsp.Id,
                EntityTitle = "Toyota SA - Mandatory Grant WSP (2026/27)",
                EntityReferenceNumber = toyotaWsp.ReferenceNumber,
                CurrentWorkflowStateId = wspS4.Id, // Under CLO Review
                InitiatorUserId = "admin@merseta.org.za",
                InitiatorName = "Tebogo Moepi (Primary SDF)",
                InitiatedDate = DateTime.UtcNow.AddDays(-3)
            };
            context.WorkflowInstances.Add(wspInstance);
            await context.SaveChangesAsync();

            var wspTask = new WorkflowTask
            {
                WorkflowInstanceId = wspInstance.Id,
                TaskTitle = "Review WSP Submission & Verify OFO Summary",
                TaskDescription = "Inspect OFO employment equity summaries, training plan cost items, and confirm compliance for Mandatory Grant disbursement.",
                AssignedGroupRole = "Client Liaison Officer (CLO)",
                TaskStatus = "Open",
                Priority = "High",
                DueDate = DateTime.UtcNow.AddDays(4),
                TargetRoute = $"/wsp/{toyotaWsp.Id}"
            };
            context.WorkflowTasks.Add(wspTask);

            context.WorkflowHistories.AddRange(
                new WorkflowHistory
                {
                    WorkflowInstanceId = wspInstance.Id,
                    FromStateId = wspS1.Id,
                    ToStateId = wspS2.Id,
                    ActionName = "Sign and Submit (SDF)",
                    ActorUserId = "sdf@toyota.co.za",
                    ActorName = "SDF Toyota",
                    ActorRole = "Primary SDF",
                    ActionDate = DateTime.UtcNow.AddDays(-3),
                    Comments = "WSP finalized and signed off by employer management."
                },
                new WorkflowHistory
                {
                    WorkflowInstanceId = wspInstance.Id,
                    FromStateId = wspS2.Id,
                    ToStateId = wspS3.Id,
                    ActionName = "Labour Endorsement",
                    ActorUserId = "labour@numsa.org.za",
                    ActorName = "NUMSA Shop Steward",
                    ActorRole = "Organised Labour",
                    ActionDate = DateTime.UtcNow.AddDays(-2),
                    Comments = "Organised labour consensus reached. Endorsement signed."
                },
                new WorkflowHistory
                {
                    WorkflowInstanceId = wspInstance.Id,
                    FromStateId = wspS3.Id,
                    ToStateId = wspS4.Id,
                    ActionName = "Submit to MerSETA",
                    ActorUserId = "sdf@toyota.co.za",
                    ActorName = "SDF Toyota",
                    ActorRole = "Primary SDF",
                    ActionDate = DateTime.UtcNow.AddDays(-1),
                    Comments = "Submitted for official MerSETA evaluation."
                }
            );
        }

        // Seed Sample Learner Workflow Instance
        var sampleLearner = await context.CompanyLearners.FirstOrDefaultAsync();
        if (sampleLearner != null)
        {
            var lrnInstance = new WorkflowInstance
            {
                WorkflowDefinitionId = lrnDef.Id,
                EntityId = sampleLearner.Id,
                EntityTitle = $"Learner Registration: {sampleLearner.QualificationTitle}",
                EntityReferenceNumber = sampleLearner.LearnerContractNumber,
                CurrentWorkflowStateId = lrnS2.Id, // Under QA Review
                InitiatorUserId = "admin@merseta.org.za",
                InitiatorName = "Denel Dynamics Training Dept",
                InitiatedDate = DateTime.UtcNow.AddDays(-5)
            };
            context.WorkflowInstances.Add(lrnInstance);
            await context.SaveChangesAsync();

            context.WorkflowTasks.Add(new WorkflowTask
            {
                WorkflowInstanceId = lrnInstance.Id,
                TaskTitle = "Verify Apprentice Contract & RSA ID Verification",
                TaskDescription = "Check apprentice biometric details, RSA ID validation, and provider accreditation alignment.",
                AssignedGroupRole = "Client Liaison Officer (CLO)",
                TaskStatus = "Open",
                Priority = "Normal",
                DueDate = DateTime.UtcNow.AddDays(2),
                TargetRoute = $"/learners/{sampleLearner.Id}"
            });
        }

        await context.SaveChangesAsync();
    }
}
