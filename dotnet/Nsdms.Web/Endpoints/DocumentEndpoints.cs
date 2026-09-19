using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Routing;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;

namespace Nsdms.Web.Endpoints;

public static class DocumentEndpoints
{
    public static RouteGroupBuilder MapDocumentEndpoints(this IEndpointRouteBuilder app)
    {
        var group = app.MapGroup("/api/documents")
            .RequireAuthorization()
            .AddEndpointFilter<TenantOwnershipEndpointFilter>();

        group.MapGet("/moa/{id:int}/pdf", async (int id, IPdfDocumentService pdf) =>
        {
            try
            {
                var bytes = await pdf.GenerateGrantMoaContractPdfAsync(id);
                return Results.File(bytes, "application/pdf", $"GrantMoa_Contract_{id}.pdf");
            }
            catch (KeyNotFoundException)
            {
                return Results.NotFound(new { message = $"MoA contract #{id} not found." });
            }
        });

        group.MapGet("/tradetest/{id:int}/pdf", async (int id, IPdfDocumentService pdf) =>
        {
            try
            {
                var bytes = await pdf.GenerateTradeTestCertificatePdfAsync(id);
                return Results.File(bytes, "application/pdf", $"TradeTest_Artisan_Certificate_{id}.pdf");
            }
            catch (KeyNotFoundException)
            {
                return Results.NotFound(new { message = $"Trade test #{id} not found." });
            }
        });

        group.MapGet("/tradetest/{id:int}/form-pdf", async (int id, IPdfDocumentService pdf) =>
        {
            try
            {
                var bytes = await pdf.GenerateArplApplicationFormPdfAsync(id);
                return Results.File(bytes, "application/pdf", $"ARPL_Application_Form_ETQ_TP_ARPL_01_{id}.pdf");
            }
            catch (KeyNotFoundException)
            {
                return Results.NotFound(new { message = $"ARPL application #{id} not found." });
            }
        });

        group.MapGet("/wsp/{id:int}/pdf", async (int id, IPdfDocumentService pdf) =>
        {
            try
            {
                var bytes = await pdf.GenerateWspOutcomeLetterPdfAsync(id);
                return Results.File(bytes, "application/pdf", $"WSP_Outcome_Letter_{id}.pdf");
            }
            catch (KeyNotFoundException)
            {
                return Results.NotFound(new { message = $"WSP submission #{id} not found." });
            }
        });

        group.MapGet("/remittance/{id:int}/pdf", async (int id, IPdfDocumentService pdf) =>
        {
            try
            {
                var bytes = await pdf.GenerateMandatoryRebateRemittancePdfAsync(id);
                return Results.File(bytes, "application/pdf", $"Mandatory_Rebate_Remittance_{id}.pdf");
            }
            catch (KeyNotFoundException)
            {
                return Results.NotFound(new { message = $"Disbursement #{id} not found." });
            }
        });

        group.MapGet("/workplace-approval/{id:int}/letter-pdf", async (int id, IPdfDocumentService pdf) =>
        {
            try
            {
                var bytes = await pdf.GenerateWorkplaceApprovalLetterPdfAsync(id);
                return Results.File(bytes, "application/pdf", $"WorkplaceApproval_Outcome_Letter_ETQ_TP_003_{id}.pdf");
            }
            catch (KeyNotFoundException)
            {
                return Results.NotFound(new { message = $"Workplace approval #{id} not found." });
            }
        });

        group.MapGet("/workplace-approval/{id:int}/report-pdf", async (int id, IPdfDocumentService pdf) =>
        {
            try
            {
                var bytes = await pdf.GenerateWorkplaceApprovalReportPdfAsync(id);
                return Results.File(bytes, "application/pdf", $"WorkplaceApproval_Report_ETQ_TP_054_{id}.pdf");
            }
            catch (KeyNotFoundException)
            {
                return Results.NotFound(new { message = $"Workplace approval #{id} not found." });
            }
        });

        group.MapGet("/summative/{id:int}/results-pdf", async (int id, IPdfDocumentService pdf) =>
        {
            var bytes = await pdf.GenerateSummativeAssessmentResultsFormPdfAsync(id);
            return Results.File(bytes, "application/pdf", $"ETQ_FM_005_SummativeResults_{id}.pdf");
        });

        group.MapGet("/summative/batch/{id:int}/validation-report-pdf", async (int id, IPdfDocumentService pdf) =>
        {
            var bytes = await pdf.GenerateModerationValidationReportPdfAsync(id);
            return Results.File(bytes, "application/pdf", $"ETQ_TP_043_ModerationReport_Batch_{id}.pdf");
        });

        group.MapGet("/summative/certificate/{id:int}/pdf", async (int id, IPdfDocumentService pdf) =>
        {
            var bytes = await pdf.GenerateLearnerQualificationCertificatePdfAsync(id);
            return Results.File(bytes, "application/pdf", $"MerSETA_Certificate_{id}.pdf");
        });

        group.MapGet("/summative/batch/{id:int}/distribution-letter-pdf", async (int id, IPdfDocumentService pdf) =>
        {
            var bytes = await pdf.GenerateBatchDistributionLetterPdfAsync(id);
            return Results.File(bytes, "application/pdf", $"ETQ_LT_012_DistributionLetter_Batch_{id}.pdf");
        });

        group.MapGet("/summative/batch/{id:int}/consolidated-certificates-pdf", async (int id, IPdfDocumentService pdf) =>
        {
            var bytes = await pdf.GenerateBatchConsolidatedCertificatesPdfAsync(id);
            return Results.File(bytes, "application/pdf", $"MerSETA_Consolidated_Certificates_Batch_{id}.pdf");
        });

        group.MapGet("/templates/{id:int}/simulation-pdf", async (int id, IEnterpriseDocumentTemplateService templateService, string? scenario, HttpContext context) =>
        {
            var profiles = templateService.GetDefaultScenarioTokenProfiles();
            var selectedScenario = !string.IsNullOrEmpty(scenario) && profiles.ContainsKey(scenario) ? scenario : "LevyEmployer";
            var tokens = new Dictionary<string, string>(profiles[selectedScenario]);

            foreach (var query in context.Request.Query)
            {
                if (query.Key != "scenario" && query.Key != "t" && !string.IsNullOrWhiteSpace(query.Value))
                {
                    tokens[query.Key] = query.Value.ToString();
                }
            }

            var bytes = await templateService.GenerateSimulatedPdfAsync(id, tokens, includeWatermark: true);
            return Results.File(bytes, "application/pdf", $"Template_Simulation_{id}_{selectedScenario}.pdf");
        });

        group.MapGet("/moa-templates/{id:int}/simulation-pdf", async (int id, IMoaTemplateEngineService moaService, string? scenario, HttpContext context) =>
        {
            var profiles = moaService.GetDefaultScenarioTokenProfiles();
            var selectedScenario = !string.IsNullOrEmpty(scenario) && profiles.ContainsKey(scenario) ? scenario : "LevyEmployer";
            var tokens = new Dictionary<string, string>(profiles[selectedScenario]);

            foreach (var query in context.Request.Query)
            {
                if (query.Key != "scenario" && query.Key != "t" && !string.IsNullOrWhiteSpace(query.Value))
                {
                    tokens[query.Key] = query.Value.ToString();
                }
            }

            var bytes = await moaService.GenerateSimulatedPdfAsync(id, tokens, includeWatermark: true);
            return Results.File(bytes, "application/pdf", $"MoaTemplate_Simulation_{id}_{selectedScenario}.pdf");
        });

        group.MapGet("/attachments/{id:int}/download", async (int id, IFileStorageService storage) =>
        {
            var fileResult = await storage.GetFileAsync(id);
            if (fileResult == null)
            {
                return Results.NotFound(new { message = $"Document attachment #{id} not found or inaccessible." });
            }
            return Results.File(fileResult.Value.ContentStream, fileResult.Value.ContentType, fileResult.Value.FileName);
        });

        return group;
    }
}
