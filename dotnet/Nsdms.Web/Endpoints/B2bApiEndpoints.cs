using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text.Json;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Routing;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;

namespace Nsdms.Web.Endpoints;

/// <summary>
/// Minimal API route group for external B2B Machine-to-Machine (M2M) integration endpoints.
/// Supports WSP/ATR bulk intake, workforce roster sync, learner enrolments, DG claims, trade tests,
/// tamper-proof 2D barcode verification, and webhook event subscriptions.
/// </summary>
public static class B2bApiEndpoints
{
    public static IEndpointRouteBuilder MapB2bApiEndpoints(this IEndpointRouteBuilder app)
    {
        var api = app.MapGroup("/api/v1/b2b")
                     .WithTags("B2B Enterprise APIs");

        // -------------------------------------------------------------------------
        // 1. Mandatory Grant (WSP / ATR) Bulk Staging & Commit
        // -------------------------------------------------------------------------
        api.MapPost("/wsp/staging/upload", async (
            [FromHeader(Name = "X-Client-Id")] string? clientId,
            [FromHeader(Name = "X-Client-Secret")] string? clientSecret,
            [FromHeader(Name = "DPoP")] string? dpopHeader,
            [FromBody] WspBulkStagingUploadRequest request,
            IApiSecurityService securityService,
            INsdmsDbContextFactory dbFactory,
            IAuditService auditService) =>
        {
            var auth = await securityService.AuthenticateClientAsync(clientId ?? "", clientSecret ?? "", dpopHeader);
            if (!auth.Success || auth.Client == null)
                return Results.Json(new { error = auth.Error ?? "Unauthorized" }, statusCode: StatusCodes.Status401Unauthorized);

            if (!securityService.HasScope(auth.Client, "wsp:write"))
                return Results.Json(new { error = "Forbidden: missing scope 'wsp:write'" }, statusCode: StatusCodes.Status403Forbidden);

            if (request == null || request.Items == null || request.Items.Count == 0)
                return Results.BadRequest(new { error = "No training plan items provided." });

            using var db = await dbFactory.CreateDbContextAsync();
            var batchGuid = Guid.NewGuid();
            int schemeYear = request.FinYear ?? DateTime.UtcNow.Year;

            // Find or link active WSP submission
            var submission = await db.WspSubmissions
                .FirstOrDefaultAsync(w => w.OrganisationId == auth.Client.OrganisationId && w.FinYear == schemeYear);

            int submissionId = submission?.Id ?? 0;

            var batch = new WspBulkImportBatch
            {
                BatchGuid = batchGuid,
                BatchReference = $"BATCH-WSP-{schemeYear}-{batchGuid.ToString("N")[..6].ToUpperInvariant()}",
                OrganisationId = auth.Client.OrganisationId,
                WspSubmissionId = submissionId,
                SchemeYear = schemeYear,
                ReportType = "WSP",
                OriginalFileName = "API_PAYLOAD.json",
                BatchStatus = "Staged",
                TotalRowCount = request.Items.Count,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = auth.Client.ClientIdentifier
            };

            db.WspBulkImportBatches.Add(batch);
            await db.SaveChangesAsync();

            int validCount = 0;
            int exceptionCount = 0;
            var stagingRows = new List<WspBulkImportStaging>();

            int rowIndex = 1;
            foreach (var item in request.Items)
            {
                bool isValid = !string.IsNullOrWhiteSpace(item.OfoCode) && item.TotalBeneficiaries > 0;
                if (isValid) validCount++; else exceptionCount++;

                stagingRows.Add(new WspBulkImportStaging
                {
                    BatchId = batch.Id,
                    RowIndex = rowIndex++,
                    RawOfoCode = item.OfoCode,
                    RawQualificationCode = item.SaqaId,
                    RawEstimatedCost = item.EstimatedCost.ToString(),
                    RawBeneficiaryCount = item.TotalBeneficiaries.ToString(),
                    IsValid = isValid,
                    IsCommitted = false,
                    ValidationErrorCode = isValid ? null : "INVALID_BENEFICIARY_COUNT",
                    ValidationErrorDetails = isValid ? null : "Missing valid OFO Code or zero beneficiary count",
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = auth.Client.ClientIdentifier
                });
            }

            batch.ValidRowCount = validCount;
            batch.ErrorRowCount = exceptionCount;
            db.WspBulkImportStagings.AddRange(stagingRows);
            await db.SaveChangesAsync();

            await auditService.LogActionAsync(
                entityName: "WspBulkImportBatch",
                recordId: batch.Id,
                actionName: "B2bWspUpload",
                actor: auth.Client.ClientIdentifier,
                beforeState: null,
                afterState: new { batchGuid, total = stagingRows.Count, validCount, exceptionCount });

            return Results.Accepted($"/api/v1/b2b/wsp/batches/{batchGuid}/health", new
            {
                BatchId = batchGuid,
                BatchReference = batch.BatchReference,
                Status = "Staged",
                TotalRecords = stagingRows.Count,
                ValidRecords = validCount,
                ExceptionRecords = exceptionCount,
                HealthCheckUrl = $"/api/v1/b2b/wsp/batches/{batchGuid}/health",
                CommitUrl = $"/api/v1/b2b/wsp/batches/{batchGuid}/commit"
            });
        });

        api.MapGet("/wsp/batches/{batchId:guid}/health", async (
            Guid batchId,
            [FromHeader(Name = "X-Client-Id")] string? clientId,
            [FromHeader(Name = "X-Client-Secret")] string? clientSecret,
            IApiSecurityService securityService,
            INsdmsDbContextFactory dbFactory) =>
        {
            var auth = await securityService.AuthenticateClientAsync(clientId ?? "", clientSecret ?? "");
            if (!auth.Success || auth.Client == null)
                return Results.Json(new { error = auth.Error ?? "Unauthorized" }, statusCode: StatusCodes.Status401Unauthorized);

            using var db = await dbFactory.CreateDbContextAsync();
            var batch = await db.WspBulkImportBatches
                .Include(b => b.StagedRows)
                .FirstOrDefaultAsync(b => b.BatchGuid == batchId && b.OrganisationId == auth.Client.OrganisationId);

            if (batch == null)
                return Results.NotFound(new { error = "Batch not found or unauthorized." });

            return Results.Ok(new
            {
                BatchId = batch.BatchGuid,
                BatchReference = batch.BatchReference,
                Status = batch.BatchStatus,
                Total = batch.TotalRowCount,
                Valid = batch.ValidRowCount,
                Exceptions = batch.ErrorRowCount,
                Committed = batch.CommittedRowCount,
                ExceptionDetails = batch.StagedRows.Where(i => !i.IsValid)
                                        .Select(i => new { i.RowIndex, i.RawOfoCode, i.RawQualificationCode, i.ValidationErrorDetails })
                                        .Take(100)
            });
        });

        api.MapPost("/wsp/batches/{batchId:guid}/commit", async (
            Guid batchId,
            [FromHeader(Name = "X-Client-Id")] string? clientId,
            [FromHeader(Name = "X-Client-Secret")] string? clientSecret,
            IApiSecurityService securityService,
            INsdmsDbContextFactory dbFactory,
            IAuditService auditService) =>
        {
            var auth = await securityService.AuthenticateClientAsync(clientId ?? "", clientSecret ?? "");
            if (!auth.Success || auth.Client == null)
                return Results.Json(new { error = auth.Error ?? "Unauthorized" }, statusCode: StatusCodes.Status401Unauthorized);

            if (!securityService.HasScope(auth.Client, "wsp:write"))
                return Results.Json(new { error = "Forbidden: missing scope 'wsp:write'" }, statusCode: StatusCodes.Status403Forbidden);

            using var db = await dbFactory.CreateDbContextAsync();
            var batch = await db.WspBulkImportBatches
                .Include(b => b.StagedRows)
                .FirstOrDefaultAsync(b => b.BatchGuid == batchId && b.OrganisationId == auth.Client.OrganisationId);

            if (batch == null)
                return Results.NotFound(new { error = "Batch not found." });

            var uncommittedRows = batch.StagedRows.Where(s => s.IsValid && !s.IsCommitted).ToList();
            if (uncommittedRows.Count == 0)
                return Results.BadRequest(new { error = "No uncommitted valid records available to commit." });

            foreach (var row in uncommittedRows)
            {
                row.IsCommitted = true;
            }
            batch.CommittedRowCount += uncommittedRows.Count;
            batch.BatchStatus = "Committed";

            await db.SaveChangesAsync();

            await auditService.LogActionAsync(
                entityName: "WspBulkImportBatch",
                recordId: batch.Id,
                actionName: "B2bWspCommit",
                actor: auth.Client.ClientIdentifier,
                beforeState: null,
                afterState: new { batchId, committedCount = uncommittedRows.Count });

            return Results.Ok(new
            {
                BatchId = batchId,
                CommittedCount = uncommittedRows.Count,
                Message = "Staged training plans successfully committed to statutory WSP profile."
            });
        });

        // -------------------------------------------------------------------------
        // 2. Workforce Roster Delta Sync API
        // -------------------------------------------------------------------------
        api.MapPut("/workforce/sync", async (
            [FromHeader(Name = "X-Client-Id")] string? clientId,
            [FromHeader(Name = "X-Client-Secret")] string? clientSecret,
            [FromBody] WorkforceSyncRequest request,
            IApiSecurityService securityService,
            INsdmsDbContextFactory dbFactory,
            IAuditService auditService) =>
        {
            var auth = await securityService.AuthenticateClientAsync(clientId ?? "", clientSecret ?? "");
            if (!auth.Success || auth.Client == null)
                return Results.Json(new { error = auth.Error ?? "Unauthorized" }, statusCode: StatusCodes.Status401Unauthorized);

            if (!securityService.HasScope(auth.Client, "workforce:sync"))
                return Results.Json(new { error = "Forbidden: missing scope 'workforce:sync'" }, statusCode: StatusCodes.Status403Forbidden);

            if (request == null || request.Employees == null || request.Employees.Count == 0)
                return Results.BadRequest(new { error = "No employee records supplied." });

            using var db = await dbFactory.CreateDbContextAsync();
            int syncedCount = 0;

            foreach (var emp in request.Employees)
            {
                if (string.IsNullOrWhiteSpace(emp.NationalId) || string.IsNullOrWhiteSpace(emp.LastName))
                    continue;

                // Find or register natural person
                var person = await db.People.FirstOrDefaultAsync(p => p.RsaIdNumber == emp.NationalId);
                if (person == null)
                {
                    person = new Person
                    {
                        RsaIdNumber = emp.NationalId,
                        FirstName = emp.FirstName ?? "Employee",
                        LastName = emp.LastName,
                        CreatedAt = DateTime.UtcNow,
                        CreatedBy = auth.Client.ClientIdentifier
                    };
                    db.People.Add(person);
                    await db.SaveChangesAsync();
                }

                // Check OrganisationEmployee
                var orgEmp = await db.OrganisationEmployees
                    .FirstOrDefaultAsync(oe => oe.OrganisationId == auth.Client.OrganisationId && oe.PersonId == person.Id);

                if (orgEmp == null)
                {
                    orgEmp = new OrganisationEmployee
                    {
                        OrganisationId = auth.Client.OrganisationId,
                        PersonId = person.Id,
                        EmployeeNumber = emp.EmployeeNumber,
                        OfoCodeId = emp.OfoCode,
                        JobTitle = emp.JobTitle,
                        EmploymentStatusCode = emp.IsActive ? "ACTIVE" : "TERMINATED",
                        IsActive = emp.IsActive,
                        StartDate = emp.StartDate ?? DateTime.UtcNow,
                        CreatedAt = DateTime.UtcNow,
                        CreatedBy = auth.Client.ClientIdentifier
                    };
                    db.OrganisationEmployees.Add(orgEmp);
                }
                else
                {
                    orgEmp.EmployeeNumber = emp.EmployeeNumber ?? orgEmp.EmployeeNumber;
                    orgEmp.OfoCodeId = emp.OfoCode ?? orgEmp.OfoCodeId;
                    orgEmp.JobTitle = emp.JobTitle ?? orgEmp.JobTitle;
                    orgEmp.IsActive = emp.IsActive;
                    orgEmp.EmploymentStatusCode = emp.IsActive ? "ACTIVE" : "TERMINATED";
                    orgEmp.ModifiedAt = DateTime.UtcNow;
                    orgEmp.ModifiedBy = auth.Client.ClientIdentifier;
                }

                syncedCount++;
            }

            await db.SaveChangesAsync();

            await auditService.LogActionAsync(
                entityName: "OrganisationEmployee",
                recordId: auth.Client.OrganisationId,
                actionName: "B2bWorkforceSync",
                actor: auth.Client.ClientIdentifier,
                beforeState: null,
                afterState: new { syncedCount });

            return Results.Ok(new
            {
                Status = "Success",
                OrganisationId = auth.Client.OrganisationId,
                ProcessedCount = syncedCount,
                SyncedAtUtc = DateTime.UtcNow
            });
        });

        // -------------------------------------------------------------------------
        // 3. Learner Enrolment & Registration API (SDPs & Lead Employers)
        // -------------------------------------------------------------------------
        api.MapPost("/learners/enrolments", async (
            [FromHeader(Name = "X-Client-Id")] string? clientId,
            [FromHeader(Name = "X-Client-Secret")] string? clientSecret,
            [FromBody] LearnerEnrolmentRequest request,
            IApiSecurityService securityService,
            INsdmsDbContextFactory dbFactory,
            IAuditService auditService) =>
        {
            var auth = await securityService.AuthenticateClientAsync(clientId ?? "", clientSecret ?? "");
            if (!auth.Success || auth.Client == null)
                return Results.Json(new { error = auth.Error ?? "Unauthorized" }, statusCode: StatusCodes.Status401Unauthorized);

            if (!securityService.HasScope(auth.Client, "learners:register"))
                return Results.Json(new { error = "Forbidden: missing scope 'learners:register'" }, statusCode: StatusCodes.Status403Forbidden);

            if (string.IsNullOrWhiteSpace(request.NationalId) || string.IsNullOrWhiteSpace(request.LastName))
                return Results.BadRequest(new { error = "National ID and Last Name are required." });

            using var db = await dbFactory.CreateDbContextAsync();

            var person = await db.People.FirstOrDefaultAsync(p => p.RsaIdNumber == request.NationalId);
            if (person == null)
            {
                person = new Person
                {
                    RsaIdNumber = request.NationalId,
                    FirstName = request.FirstName,
                    LastName = request.LastName,
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = auth.Client.ClientIdentifier
                };
                db.People.Add(person);
                await db.SaveChangesAsync();
            }

            var agreementRef = $"AGR-{DateTime.UtcNow.Year}-{Guid.NewGuid().ToString("N")[..6].ToUpperInvariant()}";
            int? parsedSaqaId = int.TryParse(request.SaqaQualificationId, out int sid) ? sid : null;

            var learner = new CompanyLearner
            {
                LearnerContractNumber = agreementRef,
                OrganisationId = auth.Client.OrganisationId,
                PersonId = person.Id,
                SaqaQualificationId = parsedSaqaId,
                LearningProgrammeTypeCode = request.ProgrammeType == "Apprenticeship" ? "01" : "02",
                CommencementDate = request.StartDate ?? DateTime.UtcNow,
                ExpectedCompletionDate = request.EndDate ?? DateTime.UtcNow.AddYears(1),
                CreatedAt = DateTime.UtcNow,
                CreatedBy = auth.Client.ClientIdentifier
            };

            db.CompanyLearners.Add(learner);
            await db.SaveChangesAsync();

            await auditService.LogActionAsync(
                entityName: "CompanyLearner",
                recordId: learner.Id,
                actionName: "B2bLearnerEnrolment",
                actor: auth.Client.ClientIdentifier,
                beforeState: null,
                afterState: new { learner.LearnerContractNumber, person.RsaIdNumber, request.ProgrammeType });

            return Results.Created($"/api/v1/b2b/learners/{learner.Id}", new
            {
                EnrolmentId = learner.Id,
                AgreementNumber = agreementRef,
                Status = "Submitted",
                RegisteredAtUtc = DateTime.UtcNow
            });
        });

        // -------------------------------------------------------------------------
        // 4. Discretionary Grant (DG) Claims API (with Idempotency Key)
        // -------------------------------------------------------------------------
        api.MapPost("/grants/moa/{moaNumber}/claims", async (
            string moaNumber,
            [FromHeader(Name = "X-Client-Id")] string? clientId,
            [FromHeader(Name = "X-Client-Secret")] string? clientSecret,
            [FromHeader(Name = "Idempotency-Key")] string? idempotencyKey,
            [FromBody] DgClaimRequest request,
            IApiSecurityService securityService,
            INsdmsDbContextFactory dbFactory,
            IAuditService auditService) =>
        {
            var auth = await securityService.AuthenticateClientAsync(clientId ?? "", clientSecret ?? "");
            if (!auth.Success || auth.Client == null)
                return Results.Json(new { error = auth.Error ?? "Unauthorized" }, statusCode: StatusCodes.Status401Unauthorized);

            if (!securityService.HasScope(auth.Client, "claims:submit"))
                return Results.Json(new { error = "Forbidden: missing scope 'claims:submit'" }, statusCode: StatusCodes.Status403Forbidden);

            // Verify Idempotency
            if (!string.IsNullOrWhiteSpace(idempotencyKey))
            {
                var cached = await securityService.CheckIdempotencyAsync(idempotencyKey, auth.Client.ClientIdentifier, $"/api/v1/b2b/grants/moa/{moaNumber}/claims");
                if (cached.IsDuplicate && cached.CachedResponseJson != null)
                {
                    return Results.Content(cached.CachedResponseJson, "application/json", statusCode: cached.StatusCode);
                }
            }

            using var db = await dbFactory.CreateDbContextAsync();
            var moa = await db.GrantMoas
                .Include(m => m.GrantApplication)
                .FirstOrDefaultAsync(m => m.MoaNumber == moaNumber && m.GrantApplication!.OrganisationId == auth.Client.OrganisationId);

            if (moa == null)
                return Results.NotFound(new { error = $"MoA {moaNumber} not found for authenticated organisation." });

            var claimNumber = $"CLM-{DateTime.UtcNow.Year}-{Guid.NewGuid().ToString("N")[..6].ToUpperInvariant()}";
            var claim = new GrantPaymentClaim
            {
                ProjectImplementationPlanId = moa.GrantApplicationId,
                ClaimNumber = claimNumber,
                TrancheNumber = request.TrancheNumber,
                ClaimAmount = request.ClaimedAmount,
                DeliverableDescription = request.Remarks ?? $"Tranche {request.TrancheNumber} invoice {request.InvoiceNumber}",
                StatusCode = "PendingSubmission",
                CreatedAt = DateTime.UtcNow,
                CreatedBy = auth.Client.ClientIdentifier
            };

            db.GrantPaymentClaims.Add(claim);
            await db.SaveChangesAsync();

            var responseObj = new
            {
                ClaimId = claim.Id,
                ClaimNumber = claimNumber,
                MoaNumber = moaNumber,
                TrancheNumber = claim.TrancheNumber,
                ClaimedAmount = claim.ClaimAmount,
                Status = claim.StatusCode,
                SubmittedAtUtc = DateTime.UtcNow
            };

            var responseJson = JsonSerializer.Serialize(responseObj);
            if (!string.IsNullOrWhiteSpace(idempotencyKey))
            {
                await securityService.RecordIdempotencyAsync(
                    idempotencyKey,
                    auth.Client.ClientIdentifier,
                    $"/api/v1/b2b/grants/moa/{moaNumber}/claims",
                    StatusCodes.Status201Created,
                    responseJson);
            }

            await auditService.LogActionAsync(
                entityName: "GrantPaymentClaim",
                recordId: claim.Id,
                actionName: "B2bClaimSubmission",
                actor: auth.Client.ClientIdentifier,
                beforeState: null,
                afterState: new { claimNumber, moaNumber, claim.ClaimAmount, request.InvoiceNumber });

            return Results.Created($"/api/v1/b2b/grants/claims/{claim.Id}", responseObj);
        });

        // -------------------------------------------------------------------------
        // 5. Trade Test & ARPL Candidate Registration API
        // -------------------------------------------------------------------------
        api.MapPost("/trade-tests/applications", async (
            [FromHeader(Name = "X-Client-Id")] string? clientId,
            [FromHeader(Name = "X-Client-Secret")] string? clientSecret,
            [FromBody] TradeTestApplicationRequest request,
            IApiSecurityService securityService,
            INsdmsDbContextFactory dbFactory,
            IAuditService auditService) =>
        {
            var auth = await securityService.AuthenticateClientAsync(clientId ?? "", clientSecret ?? "");
            if (!auth.Success || auth.Client == null)
                return Results.Json(new { error = auth.Error ?? "Unauthorized" }, statusCode: StatusCodes.Status401Unauthorized);

            if (!securityService.HasScope(auth.Client, "trade_tests:write"))
                return Results.Json(new { error = "Forbidden: missing scope 'trade_tests:write'" }, statusCode: StatusCodes.Status403Forbidden);

            using var db = await dbFactory.CreateDbContextAsync();
            var appNumber = $"TT-APP-{DateTime.UtcNow.Year}-{Guid.NewGuid().ToString("N")[..6].ToUpperInvariant()}";

            var person = await db.People.FirstOrDefaultAsync(p => p.RsaIdNumber == request.CandidateIdNumber);
            if (person == null)
            {
                person = new Person
                {
                    RsaIdNumber = request.CandidateIdNumber,
                    FirstName = request.CandidateFirstName,
                    LastName = request.CandidateLastName,
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = auth.Client.ClientIdentifier
                };
                db.People.Add(person);
                await db.SaveChangesAsync();
            }

            var appRecord = new LearnerTradeTestApplication
            {
                ApplicationNumber = appNumber,
                PersonId = person.Id,
                OrganisationId = auth.Client.OrganisationId,
                TradeTitle = request.TradeTitle,
                TradeOfoCode = request.TradeCode,
                ClaRecommendationStatus = "PendingReview",
                AttemptNumber = 1,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = auth.Client.ClientIdentifier
            };

            db.LearnerTradeTestApplications.Add(appRecord);
            await db.SaveChangesAsync();

            await auditService.LogActionAsync(
                entityName: "LearnerTradeTestApplication",
                recordId: appRecord.Id,
                actionName: "B2bTradeTestRegister",
                actor: auth.Client.ClientIdentifier,
                beforeState: null,
                afterState: new { appNumber, request.TradeCode, candidate = request.CandidateIdNumber });

            return Results.Created($"/api/v1/b2b/trade-tests/{appRecord.Id}", new
            {
                ApplicationId = appRecord.Id,
                ApplicationNumber = appNumber,
                Status = "PendingReview",
                RegisteredAtUtc = DateTime.UtcNow
            });
        });

        // -------------------------------------------------------------------------
        // 6. Universal 2D Barcode & Certificate Verification API
        // -------------------------------------------------------------------------
        api.MapGet("/verify/certificates/{hash}", async (
            string hash,
            INsdmsDbContextFactory dbFactory) =>
        {
            if (string.IsNullOrWhiteSpace(hash) || hash.Length < 16)
                return Results.BadRequest(new { error = "Valid digital security seal hash required." });

            using var db = await dbFactory.CreateDbContextAsync();
            var snapshot = await db.DocumentSnapshots
                .FirstOrDefaultAsync(s => s.RenderedContentHash.StartsWith(hash));

            if (snapshot == null)
            {
                return Results.NotFound(new
                {
                    IsValid = false,
                    Message = "No statutory record matches this digital verification seal."
                });
            }

            return Results.Ok(new
            {
                IsValid = true,
                DocumentType = snapshot.DocumentTypeCode,
                DocumentReference = snapshot.DocumentSnapshotNumber,
                RecipientName = snapshot.RecipientName,
                RecipientIdentifier = snapshot.RecipientIdentifier,
                IssuedAtUtc = snapshot.IssuedAt,
                SecuritySealPrefix = snapshot.RenderedContentHash[..16] + "...",
                VerificationStatus = "Official - MerSETA Certified"
            });
        });

        // -------------------------------------------------------------------------
        // 7. Webhook Subscriptions Management API
        // -------------------------------------------------------------------------
        api.MapPost("/webhooks/subscriptions", async (
            [FromHeader(Name = "X-Client-Id")] string? clientId,
            [FromHeader(Name = "X-Client-Secret")] string? clientSecret,
            [FromBody] WebhookSubscriptionRequest request,
            IApiSecurityService securityService,
            INsdmsDbContextFactory dbFactory,
            IAuditService auditService) =>
        {
            var auth = await securityService.AuthenticateClientAsync(clientId ?? "", clientSecret ?? "");
            if (!auth.Success || auth.Client == null)
                return Results.Json(new { error = auth.Error ?? "Unauthorized" }, statusCode: StatusCodes.Status401Unauthorized);

            if (string.IsNullOrWhiteSpace(request.TargetUrl) || string.IsNullOrWhiteSpace(request.EventTopic))
                return Results.BadRequest(new { error = "TargetUrl and EventTopic are required." });

            var randomBytes = new byte[32];
            RandomNumberGenerator.Fill(randomBytes);
            var secretKey = Convert.ToHexString(randomBytes).ToLowerInvariant();

            using var db = await dbFactory.CreateDbContextAsync();
            var sub = new ApiWebhookSubscription
            {
                OrganisationId = auth.Client.OrganisationId,
                EventTopic = request.EventTopic,
                TargetUrl = request.TargetUrl,
                SecretKey = secretKey,
                IsActive = true,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = auth.Client.ClientIdentifier
            };

            db.ApiWebhookSubscriptions.Add(sub);
            await db.SaveChangesAsync();

            await auditService.LogActionAsync(
                entityName: "ApiWebhookSubscription",
                recordId: sub.Id,
                actionName: "CreateWebhookSubscription",
                actor: auth.Client.ClientIdentifier,
                beforeState: null,
                afterState: new { sub.Id, sub.EventTopic, sub.TargetUrl });

            return Results.Created($"/api/v1/b2b/webhooks/subscriptions/{sub.Id}", new
            {
                SubscriptionId = sub.Id,
                EventTopic = sub.EventTopic,
                TargetUrl = sub.TargetUrl,
                SecretKey = secretKey, // Returned once for HMAC signature verification
                Status = "Active"
            });
        });

        api.MapPost("/webhooks/subscriptions/{id:int}/test-ping", async (
            int id,
            [FromHeader(Name = "X-Client-Id")] string? clientId,
            [FromHeader(Name = "X-Client-Secret")] string? clientSecret,
            IApiSecurityService securityService,
            IWebhookDispatcherService webhookDispatcher,
            INsdmsDbContextFactory dbFactory) =>
        {
            var auth = await securityService.AuthenticateClientAsync(clientId ?? "", clientSecret ?? "");
            if (!auth.Success || auth.Client == null)
                return Results.Json(new { error = auth.Error ?? "Unauthorized" }, statusCode: StatusCodes.Status401Unauthorized);

            using var db = await dbFactory.CreateDbContextAsync();
            var sub = await db.ApiWebhookSubscriptions.FirstOrDefaultAsync(s => s.Id == id && s.OrganisationId == auth.Client.OrganisationId);
            if (sub == null)
                return Results.NotFound(new { error = "Webhook subscription not found." });

            await webhookDispatcher.DispatchEventAsync(sub.EventTopic, auth.Client.OrganisationId, new
            {
                PingMessage = "MerSETA NSDMS Webhook Test Ping",
                SentAtUtc = DateTime.UtcNow,
                SubscriptionId = sub.Id
            });

            return Results.Ok(new
            {
                Status = "Dispatched",
                Message = $"Test event for topic '{sub.EventTopic}' dispatched to {sub.TargetUrl}."
            });
        });

        return app;
    }
}

// Request DTOs
public record WspBulkStagingUploadRequest(int? FinYear, List<WspBulkStagingItemDto> Items);
public record WspBulkStagingItemDto(string OfoCode, string? SaqaId, string? QualificationTitle, int EmployedCount, int UnemployedCount, decimal EstimatedCost)
{
    public int TotalBeneficiaries => EmployedCount + UnemployedCount;
}
public record WorkforceSyncRequest(List<WorkforceEmployeeDto> Employees);
public record WorkforceEmployeeDto(string NationalId, string? FirstName, string LastName, string? EmployeeNumber, string? OfoCode, string? JobTitle, bool IsActive, DateTime? StartDate);
public record LearnerEnrolmentRequest(string NationalId, string? FirstName, string LastName, string? SaqaQualificationId, string? ProgrammeType, DateTime? StartDate, DateTime? EndDate);
public record DgClaimRequest(int TrancheNumber, decimal ClaimedAmount, string InvoiceNumber, DateTime? InvoiceDate, string? Remarks);
public record TradeTestApplicationRequest(string CandidateIdNumber, string CandidateFirstName, string CandidateLastName, string TradeTitle, string TradeCode);
public record WebhookSubscriptionRequest(string EventTopic, string TargetUrl);
