using Microsoft.EntityFrameworkCore;
using Nsdms.Domain.Entities;

using Nsdms.Application.Common;

namespace Nsdms.Infrastructure.Data;

public static class WorkflowDefinitionSeeder
{
    public static async Task SeedWorkflowDefinitionsAsync(INsdmsDbContext context)
    {
        // 1. PROVIDER: Training Provider Accreditation
        if (!await context.WorkflowDefinitions.AnyAsync(d => d.Code == "PROVIDER"))
        {
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
            var provS_Query = new WorkflowState { WorkflowDefinitionId = providerDef.Id, StateCode = "QUERIED", StateName = "Desk Review Queried (RFI)", StepOrder = 3, AllowedGroupRole = "Provider" };
            var provS3 = new WorkflowState { WorkflowDefinitionId = providerDef.Id, StateCode = "SITE_AUDIT", StateName = "Site Audit Scheduled", StepOrder = 4, AllowedGroupRole = "Client Liaison Officer (CLO)" };
            var provS4 = new WorkflowState { WorkflowDefinitionId = providerDef.Id, StateCode = "ETQA_COMMITTEE", StateName = "Pending Committee Approval", StepOrder = 5, AllowedGroupRole = "Quality Assurance Manager" };
            var provS5 = new WorkflowState { WorkflowDefinitionId = providerDef.Id, StateCode = "ACCREDITED", StateName = "Accredited", StepOrder = 6, IsTerminal = true };
            var provS6 = new WorkflowState { WorkflowDefinitionId = providerDef.Id, StateCode = "REJECTED", StateName = "Accreditation Rejected", StepOrder = 7, IsTerminal = true };

            context.WorkflowStates.AddRange(provS1, provS2, provS_Query, provS3, provS4, provS5, provS6);
            await context.SaveChangesAsync();

            context.WorkflowTransitions.AddRange(
                new WorkflowTransition { WorkflowDefinitionId = providerDef.Id, FromStateId = provS1.Id, ToStateId = provS2.Id, ActionName = "Submit for Desktop Review", ButtonColor = "#1e40af", ButtonIcon = "Send", RequiredPermission = "Etqa:Submit", NewEntityStatusCode = "UNDER_REVIEW" },
                new WorkflowTransition { WorkflowDefinitionId = providerDef.Id, FromStateId = provS2.Id, ToStateId = provS_Query.Id, ActionName = "Issue RFI / Query Documentation", ButtonColor = "#f59e0b", ButtonIcon = "HelpOutline", RequiresComments = true, RequiredPermission = "Etqa:Query", NewEntityStatusCode = "QUERIED" },
                new WorkflowTransition { WorkflowDefinitionId = providerDef.Id, FromStateId = provS_Query.Id, ToStateId = provS2.Id, ActionName = "Resubmit Supporting Documents", ButtonColor = "#059669", ButtonIcon = "Send", RequiredPermission = "Etqa:Remediate", NewEntityStatusCode = "REMEDIATED" },
                new WorkflowTransition { WorkflowDefinitionId = providerDef.Id, FromStateId = provS2.Id, ToStateId = provS3.Id, ActionName = "Schedule Site Audit", ButtonColor = "#0284c7", ButtonIcon = "Event", RequiredPermission = "Etqa:Verify", NewEntityStatusCode = "AUDIT_SCHEDULED" },
                new WorkflowTransition { WorkflowDefinitionId = providerDef.Id, FromStateId = provS3.Id, ToStateId = provS4.Id, ActionName = "Recommend for Committee", ButtonColor = "#059669", ButtonIcon = "ThumbUp", RequiredPermission = "Etqa:Moderate", NewEntityStatusCode = "PENDING_COMMITTEE" },
                new WorkflowTransition { WorkflowDefinitionId = providerDef.Id, FromStateId = provS4.Id, ToStateId = provS5.Id, ActionName = "Grant Accreditation", ButtonColor = "#16a34a", ButtonIcon = "Verified", RequiredPermission = "Etqa:Approve", NewEntityStatusCode = "ACCREDITED" },
                new WorkflowTransition { WorkflowDefinitionId = providerDef.Id, FromStateId = provS4.Id, ToStateId = provS6.Id, ActionName = "Reject Accreditation", ButtonColor = "#dc2626", ButtonIcon = "Cancel", RequiredPermission = "Etqa:Reject", RequiresComments = true, NewEntityStatusCode = "REJECTED" }
            );
            await context.SaveChangesAsync();
        }

        // 2. WSP: Workplace Skills Plan Submission
        if (!await context.WorkflowDefinitions.AnyAsync(d => d.Code == "WSP"))
        {
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
            var wspS_Query = new WorkflowState { WorkflowDefinitionId = wspDef.Id, StateCode = "QUERIED", StateName = "Queried / Rework Required (RFI)", StepOrder = 5, AllowedGroupRole = "Primary SDF" };
            var wspS5 = new WorkflowState { WorkflowDefinitionId = wspDef.Id, StateCode = "APPROVED", StateName = "Approved (Mandatory Grant Awarded)", StepOrder = 6, IsTerminal = true };
            var wspS6 = new WorkflowState { WorkflowDefinitionId = wspDef.Id, StateCode = "REJECTED", StateName = "WSP Rejected", StepOrder = 7, IsTerminal = true };

            context.WorkflowStates.AddRange(wspS1, wspS2, wspS3, wspS4, wspS_Query, wspS5, wspS6);
            await context.SaveChangesAsync();

            context.WorkflowTransitions.AddRange(
                new WorkflowTransition { WorkflowDefinitionId = wspDef.Id, FromStateId = wspS1.Id, ToStateId = wspS2.Id, ActionName = "Sign and Submit (SDF)", ButtonColor = "#1e40af", ButtonIcon = "Draw", RequiredPermission = "Wsp:Sign", NewEntityStatusCode = "SDF_SIGNED" },
                new WorkflowTransition { WorkflowDefinitionId = wspDef.Id, FromStateId = wspS2.Id, ToStateId = wspS3.Id, ActionName = "Labour Endorsement", ButtonColor = "#0284c7", ButtonIcon = "Group", RequiredPermission = "Wsp:Endorse", NewEntityStatusCode = "LABOUR_SIGNED" },
                new WorkflowTransition { WorkflowDefinitionId = wspDef.Id, FromStateId = wspS3.Id, ToStateId = wspS4.Id, ActionName = "Submit to MerSETA", ButtonColor = "#059669", ButtonIcon = "Send", RequiredPermission = "Wsp:Submit", NewEntityStatusCode = "SUBMITTED" },
                new WorkflowTransition { WorkflowDefinitionId = wspDef.Id, FromStateId = wspS4.Id, ToStateId = wspS_Query.Id, ActionName = "Query WSP Submission (RFI)", ButtonColor = "#f59e0b", ButtonIcon = "HelpOutline", RequiresComments = true, RequiredPermission = "Wsp:Query", NewEntityStatusCode = "QUERIED" },
                new WorkflowTransition { WorkflowDefinitionId = wspDef.Id, FromStateId = wspS_Query.Id, ToStateId = wspS4.Id, ActionName = "Resubmit Remediated WSP", ButtonColor = "#059669", ButtonIcon = "Send", RequiredPermission = "Wsp:Remediate", NewEntityStatusCode = "REMEDIATED" },
                new WorkflowTransition { WorkflowDefinitionId = wspDef.Id, FromStateId = wspS4.Id, ToStateId = wspS5.Id, ActionName = "Approve WSP & Grant", ButtonColor = "#16a34a", ButtonIcon = "CheckCircle", RequiredPermission = "Wsp:Approve", NewEntityStatusCode = "APPROVED" },
                new WorkflowTransition { WorkflowDefinitionId = wspDef.Id, FromStateId = wspS4.Id, ToStateId = wspS6.Id, ActionName = "Reject WSP Submission", ButtonColor = "#dc2626", ButtonIcon = "Block", RequiredPermission = "Wsp:Reject", RequiresComments = true, NewEntityStatusCode = "REJECTED" }
            );
            await context.SaveChangesAsync();
        }

        // 3. DG: Discretionary Grant Application
        if (!await context.WorkflowDefinitions.AnyAsync(d => d.Code == "DG"))
        {
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
            var dgS_Query = new WorkflowState { WorkflowDefinitionId = dgDef.Id, StateCode = "QUERIED", StateName = "Application Queried (RFI)", StepOrder = 3, AllowedGroupRole = "Primary SDF" };
            var dgS3 = new WorkflowState { WorkflowDefinitionId = dgDef.Id, StateCode = "COMMITTEE_EVAL", StateName = "Review Committee Adjudication", StepOrder = 4, AllowedGroupRole = "Review Committee Member" };
            var dgS4 = new WorkflowState { WorkflowDefinitionId = dgDef.Id, StateCode = "APPROVED", StateName = "Grant Awarded", StepOrder = 5, IsTerminal = true };
            var dgS5 = new WorkflowState { WorkflowDefinitionId = dgDef.Id, StateCode = "REJECTED", StateName = "Grant Declined", StepOrder = 6, IsTerminal = true };

            context.WorkflowStates.AddRange(dgS1, dgS2, dgS_Query, dgS3, dgS4, dgS5);
            await context.SaveChangesAsync();

            context.WorkflowTransitions.AddRange(
                new WorkflowTransition { WorkflowDefinitionId = dgDef.Id, FromStateId = dgS1.Id, ToStateId = dgS2.Id, ActionName = "Start Technical Evaluation", ButtonColor = "#1e40af", ButtonIcon = "Engineering", RequiredPermission = "Grants:Verify", NewEntityStatusCode = "EVALUATING" },
                new WorkflowTransition { WorkflowDefinitionId = dgDef.Id, FromStateId = dgS2.Id, ToStateId = dgS_Query.Id, ActionName = "Query Grant Application (RFI)", ButtonColor = "#f59e0b", ButtonIcon = "HelpOutline", RequiresComments = true, RequiredPermission = "Grants:Query", NewEntityStatusCode = "QUERIED" },
                new WorkflowTransition { WorkflowDefinitionId = dgDef.Id, FromStateId = dgS_Query.Id, ToStateId = dgS2.Id, ActionName = "Resubmit Remediated Application", ButtonColor = "#059669", ButtonIcon = "Send", RequiredPermission = "Grants:Remediate", NewEntityStatusCode = "REMEDIATED" },
                new WorkflowTransition { WorkflowDefinitionId = dgDef.Id, FromStateId = dgS2.Id, ToStateId = dgS3.Id, ActionName = "Recommend to Review Committee", ButtonColor = "#0284c7", ButtonIcon = "HowToVote", RequiredPermission = "Grants:Verify", NewEntityStatusCode = "PENDING_COMMITTEE" },
                new WorkflowTransition { WorkflowDefinitionId = dgDef.Id, FromStateId = dgS3.Id, ToStateId = dgS4.Id, ActionName = "Final Grant Approval", ButtonColor = "#16a34a", ButtonIcon = "DoneAll", RequiredPermission = "Grants:Approve", NewEntityStatusCode = "APPROVED" },
                new WorkflowTransition { WorkflowDefinitionId = dgDef.Id, FromStateId = dgS3.Id, ToStateId = dgS5.Id, ActionName = "Decline Grant Application", ButtonColor = "#dc2626", ButtonIcon = "Close", RequiredPermission = "Grants:Reject", RequiresComments = true, NewEntityStatusCode = "REJECTED" }
            );
            await context.SaveChangesAsync();
        }

        // 4. WPAPP: Workplace Approval
        if (!await context.WorkflowDefinitions.AnyAsync(d => d.Code == "WPAPP"))
        {
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
                new WorkflowTransition { WorkflowDefinitionId = wpDef.Id, FromStateId = wpS1.Id, ToStateId = wpS2.Id, ActionName = "Schedule Physical Inspection", ButtonColor = "#1e40af", ButtonIcon = "Schedule", RequiredPermission = "Workplace:Verify", NewEntityStatusCode = "AUDIT_SCHEDULED" },
                new WorkflowTransition { WorkflowDefinitionId = wpDef.Id, FromStateId = wpS2.Id, ToStateId = wpS3.Id, ActionName = "Complete Site Verification", ButtonColor = "#0284c7", ButtonIcon = "FactCheck", RequiredPermission = "Workplace:Verify", NewEntityStatusCode = "AUDITED" },
                new WorkflowTransition { WorkflowDefinitionId = wpDef.Id, FromStateId = wpS3.Id, ToStateId = wpS4.Id, ActionName = "Approve Workplace Accreditation", ButtonColor = "#16a34a", ButtonIcon = "Verified", RequiredPermission = "Workplace:Approve", NewEntityStatusCode = "APPROVED" }
            );
            await context.SaveChangesAsync();
        }

        // 5. LRN: Learner Agreement Registration
        if (!await context.WorkflowDefinitions.AnyAsync(d => d.Code == "LRN"))
        {
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
                new WorkflowTransition { WorkflowDefinitionId = lrnDef.Id, FromStateId = lrnS1.Id, ToStateId = lrnS2.Id, ActionName = "Submit for QA Verification", ButtonColor = "#1e40af", ButtonIcon = "Send", RequiredPermission = "Learners:Submit", NewEntityStatusCode = "PENDING_QA" },
                new WorkflowTransition { WorkflowDefinitionId = lrnDef.Id, FromStateId = lrnS2.Id, ToStateId = lrnS3.Id, ActionName = "Approve & Register Contract", ButtonColor = "#16a34a", ButtonIcon = "CheckCircle", RequiredPermission = "Learners:Approve", NewEntityStatusCode = "REGISTERED" }
            );
            await context.SaveChangesAsync();
        }

        // 6. TRADETEST: Trade Test Scheduling & Certification
        if (!await context.WorkflowDefinitions.AnyAsync(d => d.Code == "TRADETEST"))
        {
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
                new WorkflowTransition { WorkflowDefinitionId = ttDef.Id, FromStateId = ttS1.Id, ToStateId = ttS2.Id, ActionName = "Assign Center & Schedule", ButtonColor = "#1e40af", ButtonIcon = "EventAvailable", RequiredPermission = "Learners:Moderate", NewEntityStatusCode = "TradeCenterAllocated" },
                new WorkflowTransition { WorkflowDefinitionId = ttDef.Id, FromStateId = ttS2.Id, ToStateId = ttS3.Id, ActionName = "Record Assessment Results", ButtonColor = "#0284c7", ButtonIcon = "Grading", RequiredPermission = "Learners:Moderate", NewEntityStatusCode = "Assessing" },
                new WorkflowTransition { WorkflowDefinitionId = ttDef.Id, FromStateId = ttS3.Id, ToStateId = ttS4.Id, ActionName = "Issue Artisan Certificate", ButtonColor = "#16a34a", ButtonIcon = "WorkspacePremium", RequiredPermission = "Learners:Certificate", NewEntityStatusCode = "Certified" }
            );
            await context.SaveChangesAsync();
        }

        // 7. GRANT_MOA: Memorandum of Agreement & Milestone Tranches
        if (!await context.WorkflowDefinitions.AnyAsync(d => d.Code == "GRANT_MOA" || d.Code == "MOA"))
        {
            var moaDef = new WorkflowDefinition
            {
                Code = "GRANT_MOA",
                Name = "Grant Memorandum of Agreement",
                TargetEntityName = "GrantMoa",
                KeyFieldName = "Id",
                IsActive = true
            };
            context.WorkflowDefinitions.Add(moaDef);
            await context.SaveChangesAsync();

            var moaS1 = new WorkflowState { WorkflowDefinitionId = moaDef.Id, StateCode = "DRAFT", StateName = "Draft MOA Contract", StepOrder = 1, IsInitial = true, AllowedGroupRole = "Finance Manager" };
            var moaS2 = new WorkflowState { WorkflowDefinitionId = moaDef.Id, StateCode = "PENDING_EMPLOYER_SIGN", StateName = "Awaiting Employer Signoff", StepOrder = 2, AllowedGroupRole = "Primary SDF" };
            var moaS3 = new WorkflowState { WorkflowDefinitionId = moaDef.Id, StateCode = "PENDING_SETA_SIGN", StateName = "Awaiting Executive Counter-Sign", StepOrder = 3, AllowedGroupRole = "Chief Financial Officer" };
            var moaS4 = new WorkflowState { WorkflowDefinitionId = moaDef.Id, StateCode = "ACTIVE", StateName = "Active Contract / Committed", StepOrder = 4, AllowedGroupRole = "Finance Manager" };
            var moaS5 = new WorkflowState { WorkflowDefinitionId = moaDef.Id, StateCode = "COMPLETED", StateName = "Contract Finalized & Closed", StepOrder = 5, IsTerminal = true };
            var moaS6 = new WorkflowState { WorkflowDefinitionId = moaDef.Id, StateCode = "TERMINATED", StateName = "Contract Terminated", StepOrder = 6, IsTerminal = true };

            context.WorkflowStates.AddRange(moaS1, moaS2, moaS3, moaS4, moaS5, moaS6);
            await context.SaveChangesAsync();

            context.WorkflowTransitions.AddRange(
                new WorkflowTransition { WorkflowDefinitionId = moaDef.Id, FromStateId = moaS1.Id, ToStateId = moaS2.Id, ActionName = "Submit for Employer Signature", ButtonColor = "#1e40af", ButtonIcon = "Send", RequiredPermission = "Grants:Sign", NewEntityStatusCode = "Pending Signature" },
                new WorkflowTransition { WorkflowDefinitionId = moaDef.Id, FromStateId = moaS2.Id, ToStateId = moaS3.Id, ActionName = "Record Employer Signature", ButtonColor = "#0284c7", ButtonIcon = "Draw", RequiredPermission = "Grants:Sign", NewEntityStatusCode = "Pending Signature" },
                new WorkflowTransition { WorkflowDefinitionId = moaDef.Id, FromStateId = moaS3.Id, ToStateId = moaS4.Id, ActionName = "Execute Seta Signoff", ButtonColor = "#16a34a", ButtonIcon = "Verified", RequiredPermission = "Grants:Approve", NewEntityStatusCode = "Active" },
                new WorkflowTransition { WorkflowDefinitionId = moaDef.Id, FromStateId = moaS4.Id, ToStateId = moaS5.Id, ActionName = "Closeout & Finalize MOA", ButtonColor = "#059669", ButtonIcon = "DoneAll", RequiredPermission = "Grants:Manage", NewEntityStatusCode = "Completed" },
                new WorkflowTransition { WorkflowDefinitionId = moaDef.Id, FromStateId = moaS4.Id, ToStateId = moaS6.Id, ActionName = "Terminate Contract", ButtonColor = "#dc2626", ButtonIcon = "Cancel", RequiredPermission = "Grants:Manage", RequiresComments = true, NewEntityStatusCode = "Terminated" }
            );
            await context.SaveChangesAsync();
        }

        // 8. MONITORING: Workplace Monitoring & Site Inspections
        if (!await context.WorkflowDefinitions.AnyAsync(d => d.Code == "MONITORING"))
        {
            var monDef = new WorkflowDefinition
            {
                Code = "MONITORING",
                Name = "Workplace Monitoring & Audit Inspections",
                TargetEntityName = "WorkplaceMonitoring",
                KeyFieldName = "Id",
                IsActive = true
            };
            context.WorkflowDefinitions.Add(monDef);
            await context.SaveChangesAsync();

            var monS1 = new WorkflowState { WorkflowDefinitionId = monDef.Id, StateCode = "DRAFT", StateName = "Draft Audit Plan", StepOrder = 1, IsInitial = true, AllowedGroupRole = "Client Liaison Officer (CLO)" };
            var monS2 = new WorkflowState { WorkflowDefinitionId = monDef.Id, StateCode = "SCHEDULED", StateName = "Site Visit Scheduled", StepOrder = 2, AllowedGroupRole = "Client Liaison Officer (CLO)" };
            var monS3 = new WorkflowState { WorkflowDefinitionId = monDef.Id, StateCode = "AUDITED", StateName = "On-Site Audit Completed", StepOrder = 3, AllowedGroupRole = "Client Liaison Officer (CLO)" };
            var monS4 = new WorkflowState { WorkflowDefinitionId = monDef.Id, StateCode = "NON_COMPLIANT", StateName = "Non-Compliance Action Plan Issued", StepOrder = 4, AllowedGroupRole = "Primary SDF" };
            var monS5 = new WorkflowState { WorkflowDefinitionId = monDef.Id, StateCode = "APPROVED", StateName = "Audit Approved & Closed", StepOrder = 5, IsTerminal = true };

            context.WorkflowStates.AddRange(monS1, monS2, monS3, monS4, monS5);
            await context.SaveChangesAsync();

            context.WorkflowTransitions.AddRange(
                new WorkflowTransition { WorkflowDefinitionId = monDef.Id, FromStateId = monS1.Id, ToStateId = monS2.Id, ActionName = "Schedule Monitoring Visit", ButtonColor = "#1e40af", ButtonIcon = "Event", RequiredPermission = "Compliance:Verify", NewEntityStatusCode = "Scheduled" },
                new WorkflowTransition { WorkflowDefinitionId = monDef.Id, FromStateId = monS2.Id, ToStateId = monS3.Id, ActionName = "Complete Site Inspection", ButtonColor = "#0284c7", ButtonIcon = "FactCheck", RequiredPermission = "Compliance:Verify", NewEntityStatusCode = "Audited" },
                new WorkflowTransition { WorkflowDefinitionId = monDef.Id, FromStateId = monS3.Id, ToStateId = monS4.Id, ActionName = "Flag Non-Compliance (CAP)", ButtonColor = "#f59e0b", ButtonIcon = "Warning", RequiredPermission = "Compliance:Verify", RequiresComments = true, NewEntityStatusCode = "NonComplianceIdentified" },
                new WorkflowTransition { WorkflowDefinitionId = monDef.Id, FromStateId = monS4.Id, ToStateId = monS3.Id, ActionName = "Remediate & Resubmit CAP", ButtonColor = "#059669", ButtonIcon = "Send", RequiredPermission = "Compliance:Submit", NewEntityStatusCode = "PendingApproval" },
                new WorkflowTransition { WorkflowDefinitionId = monDef.Id, FromStateId = monS3.Id, ToStateId = monS5.Id, ActionName = "Approve & Finalize Audit", ButtonColor = "#16a34a", ButtonIcon = "CheckCircle", RequiredPermission = "Compliance:Manage", NewEntityStatusCode = "Approved" }
            );
            await context.SaveChangesAsync();
        }

        // 9. BANKING: Banking Details Dual-Signoff Workbench
        if (!await context.WorkflowDefinitions.AnyAsync(d => d.Code == "BANKING"))
        {
            var bankDef = new WorkflowDefinition
            {
                Code = "BANKING",
                Name = "Banking Details Dual-Signoff",
                TargetEntityName = "BankingDetails",
                KeyFieldName = "Id",
                IsActive = true
            };
            context.WorkflowDefinitions.Add(bankDef);
            await context.SaveChangesAsync();

            var bankS1 = new WorkflowState { WorkflowDefinitionId = bankDef.Id, StateCode = "DRAFT", StateName = "Draft Banking Submission", StepOrder = 1, IsInitial = true, AllowedGroupRole = "Primary SDF" };
            var bankS2 = new WorkflowState { WorkflowDefinitionId = bankDef.Id, StateCode = "VERIFICATION", StateName = "Under Verification (CLO / Finance)", StepOrder = 2, AllowedGroupRole = "Client Liaison Officer (CLO)" };
            var bankS3 = new WorkflowState { WorkflowDefinitionId = bankDef.Id, StateCode = "FINANCE_APPROVAL", StateName = "Pending Finance Manager Approval", StepOrder = 3, AllowedGroupRole = "Finance Manager" };
            var bankS4 = new WorkflowState { WorkflowDefinitionId = bankDef.Id, StateCode = "APPROVED", StateName = "ERP Active & Verified", StepOrder = 4, IsTerminal = true };
            var bankS5 = new WorkflowState { WorkflowDefinitionId = bankDef.Id, StateCode = "REJECTED", StateName = "Banking Details Rejected", StepOrder = 5, IsTerminal = true };

            context.WorkflowStates.AddRange(bankS1, bankS2, bankS3, bankS4, bankS5);
            await context.SaveChangesAsync();

            context.WorkflowTransitions.AddRange(
                new WorkflowTransition { WorkflowDefinitionId = bankDef.Id, FromStateId = bankS1.Id, ToStateId = bankS2.Id, ActionName = "Submit for Verification", ButtonColor = "#1e40af", ButtonIcon = "Send", RequiredPermission = "Finance:Verify", NewEntityStatusCode = "PendingVerification" },
                new WorkflowTransition { WorkflowDefinitionId = bankDef.Id, FromStateId = bankS2.Id, ToStateId = bankS3.Id, ActionName = "First Sign-off (Verification Officer)", ButtonColor = "#0284c7", ButtonIcon = "Check", RequiredPermission = "Finance:Verify", NewEntityStatusCode = "FirstSignoffApproved" },
                new WorkflowTransition { WorkflowDefinitionId = bankDef.Id, FromStateId = bankS3.Id, ToStateId = bankS4.Id, ActionName = "Second Sign-off & Activate ERP", ButtonColor = "#16a34a", ButtonIcon = "VerifiedUser", RequiredPermission = "Finance:Approve", NewEntityStatusCode = "FullyApproved" },
                new WorkflowTransition { WorkflowDefinitionId = bankDef.Id, FromStateId = bankS2.Id, ToStateId = bankS5.Id, ActionName = "Reject Banking Details", ButtonColor = "#dc2626", ButtonIcon = "Cancel", RequiredPermission = "Finance:Reject", RequiresComments = true, NewEntityStatusCode = "Rejected" },
                new WorkflowTransition { WorkflowDefinitionId = bankDef.Id, FromStateId = bankS3.Id, ToStateId = bankS5.Id, ActionName = "Decline at Finance Review", ButtonColor = "#dc2626", ButtonIcon = "Cancel", RequiredPermission = "Finance:Reject", RequiresComments = true, NewEntityStatusCode = "Rejected" }
            );
            await context.SaveChangesAsync();
        }

        // 10. SCOPE: Assessor / Moderator Scope Extension
        if (!await context.WorkflowDefinitions.AnyAsync(d => d.Code == "SCOPE"))
        {
            var scopeDef = new WorkflowDefinition
            {
                Code = "SCOPE",
                Name = "Scope Extension Accreditation",
                TargetEntityName = "ExtensionOfScope",
                KeyFieldName = "Id",
                IsActive = true
            };
            context.WorkflowDefinitions.Add(scopeDef);
            await context.SaveChangesAsync();

            var scS1 = new WorkflowState { WorkflowDefinitionId = scopeDef.Id, StateCode = "DRAFT", StateName = "Draft Scope Application", StepOrder = 1, IsInitial = true, AllowedGroupRole = "Provider" };
            var scS2 = new WorkflowState { WorkflowDefinitionId = scopeDef.Id, StateCode = "INSPECTION", StateName = "Site Inspection Scheduled", StepOrder = 2, AllowedGroupRole = "Client Liaison Officer (CLO)" };
            var scS3 = new WorkflowState { WorkflowDefinitionId = scopeDef.Id, StateCode = "COMMITTEE_REVIEW", StateName = "Pending ETQA Committee Review", StepOrder = 3, AllowedGroupRole = "Quality Assurance Manager" };
            var scS4 = new WorkflowState { WorkflowDefinitionId = scopeDef.Id, StateCode = "APPROVED", StateName = "Scope Extension Approved", StepOrder = 4, IsTerminal = true };
            var scS5 = new WorkflowState { WorkflowDefinitionId = scopeDef.Id, StateCode = "REJECTED", StateName = "Scope Extension Rejected", StepOrder = 5, IsTerminal = true };

            context.WorkflowStates.AddRange(scS1, scS2, scS3, scS4, scS5);
            await context.SaveChangesAsync();

            context.WorkflowTransitions.AddRange(
                new WorkflowTransition { WorkflowDefinitionId = scopeDef.Id, FromStateId = scS1.Id, ToStateId = scS2.Id, ActionName = "Submit Scope Extension", ButtonColor = "#1e40af", ButtonIcon = "Send", RequiredPermission = "Etqa:Submit", NewEntityStatusCode = "Submitted" },
                new WorkflowTransition { WorkflowDefinitionId = scopeDef.Id, FromStateId = scS2.Id, ToStateId = scS3.Id, ActionName = "Record Site Inspection Passed", ButtonColor = "#0284c7", ButtonIcon = "Checklist", RequiredPermission = "Etqa:Verify", NewEntityStatusCode = "CommitteeReview" },
                new WorkflowTransition { WorkflowDefinitionId = scopeDef.Id, FromStateId = scS3.Id, ToStateId = scS4.Id, ActionName = "Approve Scope Extension", ButtonColor = "#16a34a", ButtonIcon = "CheckCircle", RequiredPermission = "Etqa:Approve", NewEntityStatusCode = "Approved" },
                new WorkflowTransition { WorkflowDefinitionId = scopeDef.Id, FromStateId = scS3.Id, ToStateId = scS5.Id, ActionName = "Reject Scope Extension", ButtonColor = "#dc2626", ButtonIcon = "Cancel", RequiredPermission = "Etqa:Reject", RequiresComments = true, NewEntityStatusCode = "Rejected" }
            );
            await context.SaveChangesAsync();
        }

        // 11. NONSETA: Non-SETA Verification for merSETA Articulation
        if (!await context.WorkflowDefinitions.AnyAsync(d => d.Code == "NONSETA"))
        {
            var nonSetaDef = new WorkflowDefinition
            {
                Code = "NONSETA",
                Name = "Non-SETA Articulation Verification",
                TargetEntityName = "NonSetaVerification",
                KeyFieldName = "Id",
                IsActive = true
            };
            context.WorkflowDefinitions.Add(nonSetaDef);
            await context.SaveChangesAsync();

            var nsS1 = new WorkflowState { WorkflowDefinitionId = nonSetaDef.Id, StateCode = "DRAFT", StateName = "Draft Verification", StepOrder = 1, IsInitial = true, AllowedGroupRole = "Provider" };
            var nsS2 = new WorkflowState { WorkflowDefinitionId = nonSetaDef.Id, StateCode = "PENDING_VERIF", StateName = "Under merSETA Verification", StepOrder = 2, AllowedGroupRole = "Quality Assurance Manager" };
            var nsS3 = new WorkflowState { WorkflowDefinitionId = nonSetaDef.Id, StateCode = "ENDORSED", StateName = "Endorsed for merSETA Articulation", StepOrder = 3, IsTerminal = true };
            var nsS4 = new WorkflowState { WorkflowDefinitionId = nonSetaDef.Id, StateCode = "REJECTED", StateName = "Verification Rejected", StepOrder = 4, IsTerminal = true };

            context.WorkflowStates.AddRange(nsS1, nsS2, nsS3, nsS4);
            await context.SaveChangesAsync();

            context.WorkflowTransitions.AddRange(
                new WorkflowTransition { WorkflowDefinitionId = nonSetaDef.Id, FromStateId = nsS1.Id, ToStateId = nsS2.Id, ActionName = "Submit for merSETA Verification", ButtonColor = "#1e40af", ButtonIcon = "Send", RequiredPermission = "Etqa:Submit", NewEntityStatusCode = "PendingVerification" },
                new WorkflowTransition { WorkflowDefinitionId = nonSetaDef.Id, FromStateId = nsS2.Id, ToStateId = nsS3.Id, ActionName = "Endorse for merSETA Articulation", ButtonColor = "#16a34a", ButtonIcon = "CheckCircle", RequiredPermission = "Etqa:Approve", NewEntityStatusCode = "EndorsedByMerSeta" },
                new WorkflowTransition { WorkflowDefinitionId = nonSetaDef.Id, FromStateId = nsS2.Id, ToStateId = nsS4.Id, ActionName = "Reject Verification", ButtonColor = "#dc2626", ButtonIcon = "Cancel", RequiredPermission = "Etqa:Reject", RequiresComments = true, NewEntityStatusCode = "Rejected" }
            );
            await context.SaveChangesAsync();
        }

        // 12. PIP: Project Implementation Plan
        if (!await context.WorkflowDefinitions.AnyAsync(d => d.Code == "PIP"))
        {
            var pipDef = new WorkflowDefinition
            {
                Code = "PIP",
                Name = "Project Implementation Plan",
                TargetEntityName = "ProjectImplementationPlan",
                KeyFieldName = "Id",
                IsActive = true
            };
            context.WorkflowDefinitions.Add(pipDef);
            await context.SaveChangesAsync();

            var pipS1 = new WorkflowState { WorkflowDefinitionId = pipDef.Id, StateCode = "DRAFT", StateName = "Draft Implementation Plan", StepOrder = 1, IsInitial = true, AllowedGroupRole = "Primary SDF" };
            var pipS2 = new WorkflowState { WorkflowDefinitionId = pipDef.Id, StateCode = "UNDER_REVIEW", StateName = "Under Technical Review", StepOrder = 2, AllowedGroupRole = "Client Liaison Officer (CLO)" };
            var pipS3 = new WorkflowState { WorkflowDefinitionId = pipDef.Id, StateCode = "APPROVED", StateName = "Active Contracts Signed", StepOrder = 3, IsTerminal = true };
            var pipS4 = new WorkflowState { WorkflowDefinitionId = pipDef.Id, StateCode = "REJECTED", StateName = "PIP Rejected", StepOrder = 4, IsTerminal = true };

            context.WorkflowStates.AddRange(pipS1, pipS2, pipS3, pipS4);
            await context.SaveChangesAsync();

            context.WorkflowTransitions.AddRange(
                new WorkflowTransition { WorkflowDefinitionId = pipDef.Id, FromStateId = pipS1.Id, ToStateId = pipS2.Id, ActionName = "Submit PIP for Review", ButtonColor = "#1e40af", ButtonIcon = "Send", RequiredPermission = "Grants:Submit", NewEntityStatusCode = "Submitted" },
                new WorkflowTransition { WorkflowDefinitionId = pipDef.Id, FromStateId = pipS2.Id, ToStateId = pipS3.Id, ActionName = "Sign Off Contracts & Activate", ButtonColor = "#16a34a", ButtonIcon = "Draw", RequiredPermission = "Grants:Approve", NewEntityStatusCode = "ActiveContractsSigned" },
                new WorkflowTransition { WorkflowDefinitionId = pipDef.Id, FromStateId = pipS2.Id, ToStateId = pipS4.Id, ActionName = "Reject PIP Plan", ButtonColor = "#dc2626", ButtonIcon = "Cancel", RequiredPermission = "Grants:Reject", RequiresComments = true, NewEntityStatusCode = "Rejected" }
            );
            await context.SaveChangesAsync();
        }

        // 13. EISA: Summative Assessment & Statement of Results
        if (!await context.WorkflowDefinitions.AnyAsync(d => d.Code == "EISA"))
        {
            var eisaDef = new WorkflowDefinition
            {
                Code = "EISA",
                Name = "Summative Assessment & SOR",
                TargetEntityName = "SummativeAssessmentReport",
                KeyFieldName = "Id",
                IsActive = true
            };
            context.WorkflowDefinitions.Add(eisaDef);
            await context.SaveChangesAsync();

            var eiS1 = new WorkflowState { WorkflowDefinitionId = eisaDef.Id, StateCode = "DRAFT", StateName = "Draft Assessment", StepOrder = 1, IsInitial = true, AllowedGroupRole = "Assessor" };
            var eiS2 = new WorkflowState { WorkflowDefinitionId = eisaDef.Id, StateCode = "ASSESSED", StateName = "Credits Captured & Assessed", StepOrder = 2, AllowedGroupRole = "Assessor" };
            var eiS3 = new WorkflowState { WorkflowDefinitionId = eisaDef.Id, StateCode = "INTERNAL_MODERATED", StateName = "Internal Moderation Completed", StepOrder = 3, AllowedGroupRole = "Moderator" };
            var eiS4 = new WorkflowState { WorkflowDefinitionId = eisaDef.Id, StateCode = "CREDITS_APPROVED", StateName = "ETQA External Approval", StepOrder = 4, AllowedGroupRole = "Quality Assurance Manager" };
            var eiS5 = new WorkflowState { WorkflowDefinitionId = eisaDef.Id, StateCode = "SOR_ISSUED", StateName = "Statement of Results Issued", StepOrder = 5, IsTerminal = true };

            context.WorkflowStates.AddRange(eiS1, eiS2, eiS3, eiS4, eiS5);
            await context.SaveChangesAsync();

            context.WorkflowTransitions.AddRange(
                new WorkflowTransition { WorkflowDefinitionId = eisaDef.Id, FromStateId = eiS1.Id, ToStateId = eiS2.Id, ActionName = "Save & Capture Credits", ButtonColor = "#1e40af", ButtonIcon = "Save", RequiredPermission = "Learners:Submit", NewEntityStatusCode = "Assessed" },
                new WorkflowTransition { WorkflowDefinitionId = eisaDef.Id, FromStateId = eiS2.Id, ToStateId = eiS3.Id, ActionName = "Internal Moderation", ButtonColor = "#0284c7", ButtonIcon = "VerifiedUser", RequiredPermission = "Learners:Moderate", NewEntityStatusCode = "InternalModerated" },
                new WorkflowTransition { WorkflowDefinitionId = eisaDef.Id, FromStateId = eiS3.Id, ToStateId = eiS4.Id, ActionName = "ETQA External Approval", ButtonColor = "#059669", ButtonIcon = "Approval", RequiredPermission = "Learners:Approve", NewEntityStatusCode = "CreditsApproved" },
                new WorkflowTransition { WorkflowDefinitionId = eisaDef.Id, FromStateId = eiS4.Id, ToStateId = eiS5.Id, ActionName = "Issue Statement of Results (SOR)", ButtonColor = "#16a34a", ButtonIcon = "WorkspacePremium", RequiredPermission = "Learners:Certificate", NewEntityStatusCode = "SorIssued" }
            );
            await context.SaveChangesAsync();
        }

        // 14. VARIATION: Contract Addenda & Variations
        if (!await context.WorkflowDefinitions.AnyAsync(d => d.Code == "VARIATION"))
        {
            var varDef = new WorkflowDefinition
            {
                Code = "VARIATION",
                Name = "Contract Addenda & Variations",
                TargetEntityName = "ContractAddenda",
                KeyFieldName = "Id",
                IsActive = true
            };
            context.WorkflowDefinitions.Add(varDef);
            await context.SaveChangesAsync();

            var vS1 = new WorkflowState { WorkflowDefinitionId = varDef.Id, StateCode = "DRAFT", StateName = "Draft Addenda", StepOrder = 1, IsInitial = true, AllowedGroupRole = "Finance Manager" };
            var vS2 = new WorkflowState { WorkflowDefinitionId = varDef.Id, StateCode = "SUBMITTED", StateName = "Submitted for Executive Review", StepOrder = 2, AllowedGroupRole = "Finance Manager" };
            var vS3 = new WorkflowState { WorkflowDefinitionId = varDef.Id, StateCode = "APPROVED", StateName = "Executive Approved & Applied", StepOrder = 3, IsTerminal = true };
            var vS4 = new WorkflowState { WorkflowDefinitionId = varDef.Id, StateCode = "REJECTED", StateName = "Variation Declined", StepOrder = 4, IsTerminal = true };

            context.WorkflowStates.AddRange(vS1, vS2, vS3, vS4);
            await context.SaveChangesAsync();

            context.WorkflowTransitions.AddRange(
                new WorkflowTransition { WorkflowDefinitionId = varDef.Id, FromStateId = vS1.Id, ToStateId = vS2.Id, ActionName = "Submit Addenda for Review", ButtonColor = "#1e40af", ButtonIcon = "Send", RequiredPermission = "Grants:Manage", NewEntityStatusCode = "SubmittedForReview" },
                new WorkflowTransition { WorkflowDefinitionId = varDef.Id, FromStateId = vS2.Id, ToStateId = vS3.Id, ActionName = "Approve Addenda (Executive Signoff)", ButtonColor = "#16a34a", ButtonIcon = "CheckCircle", RequiredPermission = "Grants:Approve", NewEntityStatusCode = "ExecutiveApproved" },
                new WorkflowTransition { WorkflowDefinitionId = varDef.Id, FromStateId = vS2.Id, ToStateId = vS4.Id, ActionName = "Decline Addenda", ButtonColor = "#dc2626", ButtonIcon = "Cancel", RequiredPermission = "Grants:Reject", RequiresComments = true, NewEntityStatusCode = "Rejected" }
            );
            await context.SaveChangesAsync();
        }

        // Seed Sample Active Workflow Instances and Tasks
        var toyotaWsp = await context.WspSubmissions.FirstOrDefaultAsync(w => w.FinYear == 2026);
        if (toyotaWsp != null && !await context.WorkflowInstances.AnyAsync(i => i.WorkflowDefinition!.Code == "WSP" && i.EntityId == toyotaWsp.Id))
        {
            var wspDef = await context.WorkflowDefinitions.FirstAsync(d => d.Code == "WSP");
            var wspS1 = await context.WorkflowStates.FirstAsync(s => s.WorkflowDefinitionId == wspDef.Id && s.StateCode == "DRAFT");
            var wspS2 = await context.WorkflowStates.FirstAsync(s => s.WorkflowDefinitionId == wspDef.Id && s.StateCode == "SDF_SIGNED");
            var wspS3 = await context.WorkflowStates.FirstAsync(s => s.WorkflowDefinitionId == wspDef.Id && s.StateCode == "LABOUR_SIGNED");
            var wspS4 = await context.WorkflowStates.FirstAsync(s => s.WorkflowDefinitionId == wspDef.Id && s.StateCode == "CLO_REVIEW");

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
            await context.SaveChangesAsync();
        }

        // Seed Sample Learner Workflow Instance
        var sampleLearner = await context.CompanyLearners.FirstOrDefaultAsync();
        if (sampleLearner != null && !await context.WorkflowInstances.AnyAsync(i => i.WorkflowDefinition!.Code == "LRN" && i.EntityId == sampleLearner.Id))
        {
            var lrnDef = await context.WorkflowDefinitions.FirstAsync(d => d.Code == "LRN");
            var lrnS2 = await context.WorkflowStates.FirstAsync(s => s.WorkflowDefinitionId == lrnDef.Id && s.StateCode == "QA_CHECK");

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
            await context.SaveChangesAsync();
        }
    }
}
