using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Idempotent database schema migrator for Enterprise Document Template Studio:
/// 1. Extends [dbo].[DocumentTemplate] with TemplateBodyHtml, ParentTemplateId, and VersionNotes.
/// 2. Establishes self-referencing foreign key for template version lineages.
/// 3. Reconfigures unique indexes to allow multiple versions per template family, while enforcing
///    the strict invariant that exactly one version can be active and approved at a time.
/// 4. Populates default rich HTML template content with dynamic tokens for statutory documents.
/// </summary>
public static class Phase50DocumentTemplateVersioningAndHtmlStudioMigrator
{
    public static async Task MigrateAsync(IServiceProvider serviceProvider)
    {
        using var scope = serviceProvider.CreateScope();
        var context = scope.ServiceProvider.GetRequiredService<NsdmsDbContext>();
        var logger = scope.ServiceProvider.GetService<ILogger<NsdmsDbContext>>();

        if (context.Database.IsSqlServer())
        {
            const string ddlSql = @"
                IF EXISTS (SELECT * FROM sys.tables WHERE name = 'DocumentTemplate')
                BEGIN
                    -- 1. Add TemplateBodyHtml column
                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('DocumentTemplate') AND name = 'TemplateBodyHtml')
                        ALTER TABLE [dbo].[DocumentTemplate] ADD [TemplateBodyHtml] NVARCHAR(MAX) NULL;

                    -- 2. Add ParentTemplateId column
                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('DocumentTemplate') AND name = 'ParentTemplateId')
                        ALTER TABLE [dbo].[DocumentTemplate] ADD [ParentTemplateId] INT NULL;

                    -- 3. Add VersionNotes column
                    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('DocumentTemplate') AND name = 'VersionNotes')
                        ALTER TABLE [dbo].[DocumentTemplate] ADD [VersionNotes] NVARCHAR(1000) NULL;

                    -- 4. Foreign Key for ParentTemplateId self-reference
                    IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_DocumentTemplate_ParentTemplate')
                    BEGIN
                        ALTER TABLE [dbo].[DocumentTemplate]
                        ADD CONSTRAINT [FK_DocumentTemplate_ParentTemplate]
                        FOREIGN KEY ([ParentTemplateId]) REFERENCES [dbo].[DocumentTemplate] ([Id]);
                    END;

                    -- 5. Drop old single-column unique index if it exists
                    IF EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_DocumentTemplate_TemplateCode' AND object_id = OBJECT_ID('DocumentTemplate'))
                    BEGIN
                        DROP INDEX [IX_DocumentTemplate_TemplateCode] ON [dbo].[DocumentTemplate];
                    END;

                    -- 6. Create composite unique index on (TemplateCode, VersionNumber)
                    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_DocumentTemplate_Code_Version' AND object_id = OBJECT_ID('DocumentTemplate'))
                    BEGIN
                        CREATE UNIQUE INDEX [IX_DocumentTemplate_Code_Version]
                        ON [dbo].[DocumentTemplate] ([TemplateCode], [VersionNumber]);
                    END;

                    -- 7. Create filtered unique index enforcing Single Active Version invariant
                    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_DocumentTemplate_ActiveFamily' AND object_id = OBJECT_ID('DocumentTemplate'))
                    BEGIN
                        CREATE UNIQUE INDEX [IX_DocumentTemplate_ActiveFamily]
                        ON [dbo].[DocumentTemplate] ([TemplateCode])
                        WHERE [IsActive] = 1 AND [ApprovalStatus] = 'Approved';
                    END;

                    -- 8. Index on ParentTemplateId for lineage lookups
                    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_DocumentTemplate_ParentTemplateId' AND object_id = OBJECT_ID('DocumentTemplate'))
                    BEGIN
                        CREATE NONCLUSTERED INDEX [IX_DocumentTemplate_ParentTemplateId]
                        ON [dbo].[DocumentTemplate] ([ParentTemplateId]);
                    END;
                END;
            ";

            await context.Database.ExecuteSqlRawAsync(ddlSql);
            logger?.LogInformation("Phase 50 Document Template Versioning & HTML Studio DDL migration applied successfully.");
        }

        await SeedDefaultHtmlTemplatesAsync(context, logger);
    }

    private static async Task SeedDefaultHtmlTemplatesAsync(NsdmsDbContext db, ILogger? logger)
    {
        var wspApproval = await db.DocumentTemplates.FirstOrDefaultAsync(t => t.TemplateCode == "WSP-APPROVAL-STD");
        if (wspApproval != null && string.IsNullOrWhiteSpace(wspApproval.TemplateBodyHtml))
        {
            wspApproval.TemplateBodyHtml = @"<h2>WORKPLACE SKILLS PLAN (WSP) &amp; ANNUAL TRAINING REPORT (ATR) APPROVAL NOTICE</h2>
<p>Dear <strong>{{Employer.Name}}</strong> (SDL: <code>{{Employer.SdlNumber}}</code>),</p>
<p>The <strong>Manufacturing, Engineering and Related Services SETA (merSETA)</strong> is pleased to advise that your Workplace Skills Plan (WSP) and Annual Training Report (ATR) submission for the <strong>{{Wsp.SchemeYear}}</strong> statutory scheme year has been formally evaluated, found compliant with the Skills Development Act 97 of 1998, and <strong>APPROVED</strong>.</p>

<table border=""1"" cellpadding=""6"" cellspacing=""0"" style=""width:100%; border-collapse: collapse; margin: 15px 0;"">
  <thead>
    <tr style=""background-color: #f0f4f8;"">
      <th style=""text-align:left;"">Statutory Evaluation Metric</th>
      <th style=""text-align:left;"">Outcome / Value</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>Organisation Legal Name</td>
      <td><strong>{{Employer.Name}}</strong></td>
    </tr>
    <tr>
      <td>Skills Development Levy (SDL) Number</td>
      <td><code>{{Employer.SdlNumber}}</code></td>
    </tr>
    <tr>
      <td>Statutory Scheme Year</td>
      <td>{{Wsp.SchemeYear}}</td>
    </tr>
    <tr>
      <td>Approval Date</td>
      <td>{{Wsp.ApprovalDate}}</td>
    </tr>
    <tr>
      <td>Mandatory Grant Disbursement Rebate Rate</td>
      <td><strong>20% of 80% Levy Contributed</strong></td>
    </tr>
    <tr>
      <td>Approved Mandatory Grant Estimated Allocation</td>
      <td><strong>{{Wsp.RebateAmount}}</strong></td>
    </tr>
  </tbody>
</table>

<h3>Next Steps &amp; Financial Disbursement</h3>
<ul>
  <li>Your Mandatory Grant (MG) rebate will be disbursed in accordance with the gazetted SETA Grant Regulations.</li>
  <li>Disbursement is subject to active SARS levy receipt confirmation.</li>
  <li>Please ensure your banking details on the merSETA NSDMS portal remain verified and current.</li>
</ul>

<p>For any queries regarding this approval notice, please contact your designated Client Liaison Officer (CLO) or email <code>wsp@merseta.org.za</code>.</p>";
            wspApproval.VersionNotes ??= "Initial statutory baseline template with rich HTML layout.";
        }

        var tradeCert = await db.DocumentTemplates.FirstOrDefaultAsync(t => t.TemplateCode == "TRADE-CERT-STD");
        if (tradeCert != null && string.IsNullOrWhiteSpace(tradeCert.TemplateBodyHtml))
        {
            tradeCert.TemplateBodyHtml = @"<h2 style=""text-align:center; color:#0d47a1;"">REPUBLIC OF SOUTH AFRICA</h2>
<h3 style=""text-align:center;"">NATIONAL ARTISAN TRADES ASSESSMENT CERTIFICATE</h3>
<p style=""text-align:center;""><em>Issued in terms of Section 26D of the Skills Development Act, 1998 (Act No. 97 of 1998)</em></p>

<div style=""border: 2px solid #0d47a1; padding: 15px; margin: 20px 0; background-color: #f9fbfd;"">
  <p style=""text-align:center; font-size: 1.1em;"">This is to certify that</p>
  <h2 style=""text-align:center; color:#1565c0;"">{{Learner.FullName}}</h2>
  <p style=""text-align:center;"">National ID / Passport: <strong>{{Learner.RsaIdNumber}}</strong></p>
  <p style=""text-align:center;"">has successfully undergone assessment and trade testing and has been found</p>
  <h3 style=""text-align:center; color:#2e7d32;"">COMPETENT AS AN ARTISAN</h3>
  <p style=""text-align:center;"">In the Designated Trade: <strong>{{Trade.Title}}</strong> (OFO: {{Trade.OfoCode}})</p>
</div>

<table border=""0"" cellpadding=""6"" cellspacing=""0"" style=""width:100%; margin-top: 20px;"">
  <tr>
    <td style=""width:50%;"">
      <p><strong>Certificate Number:</strong> {{TradeTest.SerialNumber}}</p>
      <p><strong>Assessment Date:</strong> {{TradeTest.AssessmentDate}}</p>
      <p><strong>Accredited Test Centre:</strong> {{TradeTest.TestCenterName}}</p>
    </td>
    <td style=""width:50%; text-align:right;"">
      <p><strong>Authorised Signatory:</strong></p>
      <p style=""margin-top: 30px;"">___________________________________</p>
      <p><strong>{{Signatory.Name}}</strong></p>
      <p><em>{{Signatory.Title}}</em></p>
    </td>
  </tr>
</table>";
            tradeCert.VersionNotes ??= "Initial statutory baseline certificate with rich HTML layout.";
        }

        var wspReject = await db.DocumentTemplates.FirstOrDefaultAsync(t => t.TemplateCode == "WSP-REJECT-STD");
        if (wspReject != null && string.IsNullOrWhiteSpace(wspReject.TemplateBodyHtml))
        {
            wspReject.TemplateBodyHtml = @"<h2>WORKPLACE SKILLS PLAN (WSP) NON-COMPLIANCE NOTIFICATION</h2>
<p>Dear <strong>{{Employer.Name}}</strong> (SDL: <code>{{Employer.SdlNumber}}</code>),</p>
<p>The <strong>merSETA</strong> has concluded the evaluation of your Workplace Skills Plan (WSP) and Annual Training Report (ATR) for the <strong>{{Wsp.SchemeYear}}</strong> scheme year.</p>

<p>We regret to inform you that your submission does not meet statutory requirements and has been marked as <strong>NON-COMPLIANT</strong> due to outstanding compliance criteria.</p>

<div style=""background-color: #fbe9e7; border-left: 4px solid #d32f2f; padding: 12px; margin: 15px 0;"">
  <strong>Key Reasons for Rejection:</strong>
  <ul>
    <li>Mandatory tripartite or SDF sign-off missing or invalid.</li>
    <li>Proof of training implementation inconsistent with SARS levy contribution.</li>
    <li>Statutory submission deadline elapsed without approved extension.</li>
  </ul>
</div>

<p>In terms of Regulation 4(4) of the SETA Grant Regulations, you may appeal this decision within 14 business days of receipt of this notice by submitting a formal appeal via the NSDMS portal.</p>";
            wspReject.VersionNotes ??= "Initial non-compliance notice template.";
        }

        var etqaAccred = await db.DocumentTemplates.FirstOrDefaultAsync(t => t.TemplateCode == "ETQA-ACCRED-STD");
        if (etqaAccred != null && string.IsNullOrWhiteSpace(etqaAccred.TemplateBodyHtml))
        {
            etqaAccred.TemplateBodyHtml = @"<h2 style=""text-align:center; color:#0d47a1;"">SKILLS DEVELOPMENT PROVIDER (SDP) ACCREDITATION CERTIFICATE</h2>
<p style=""text-align:center;"">This certifies that</p>
<h2 style=""text-align:center; color:#1565c0;"">{{Provider.Name}}</h2>
<p style=""text-align:center;"">Accreditation Number: <strong>{{Provider.AccreditationNumber}}</strong></p>
<p style=""text-align:center;"">Has been evaluated by the ETQA Committee of merSETA and granted institutional accreditation to offer accredited learning programmes in accordance with QCTO delegated authority.</p>

<table border=""1"" cellpadding=""6"" cellspacing=""0"" style=""width:100%; border-collapse: collapse; margin: 20px 0;"">
  <tr style=""background-color: #f0f4f8;"">
    <th>Accreditation Period</th>
    <th>Scope of Approval</th>
  </tr>
  <tr>
    <td><strong>{{Accreditation.StartDate}}</strong> to <strong>{{Accreditation.ExpiryDate}}</strong></td>
    <td>{{Accreditation.ScopeOfApproval}}</td>
  </tr>
</table>

<p>This accreditation is subject to ongoing compliance audits, triennial re-registration, and adherence to merSETA quality management policies.</p>";
            etqaAccred.VersionNotes ??= "Initial SDP accreditation certificate template.";
        }

        var dgMoa = await db.DocumentTemplates.FirstOrDefaultAsync(t => t.TemplateCode == "DG-MOA-STD");
        if (dgMoa != null && string.IsNullOrWhiteSpace(dgMoa.TemplateBodyHtml))
        {
            dgMoa.TemplateBodyHtml = @"<h2>MEMORANDUM OF AGREEMENT (MoA): DISCRETIONARY GRANT ALLOCATION</h2>
<p>Between <strong>Manufacturing, Engineering and Related Services SETA (merSETA)</strong> and <strong>{{Employer.Name}}</strong> (SDL: <code>{{Employer.SdlNumber}}</code>).</p>

<p>WHEREAS the merSETA has allocated discretionary grant funding in support of national skills development targets under Contract Reference <strong>{{Moa.ContractNumber}}</strong>.</p>

<table border=""1"" cellpadding=""6"" cellspacing=""0"" style=""width:100%; border-collapse: collapse; margin: 15px 0;"">
  <thead>
    <tr style=""background-color: #f0f4f8;"">
      <th>Contract Parameter</th>
      <th>Approved Agreement Value</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>Contract Reference Number</td>
      <td><strong>{{Moa.ContractNumber}}</strong></td>
    </tr>
    <tr>
      <td>Approved Discretionary Grant Total</td>
      <td><strong>{{Moa.ApprovedAmount}}</strong></td>
    </tr>
    <tr>
      <td>Approved Intervention / Project</td>
      <td>{{Moa.ProjectTitle}}</td>
    </tr>
    <tr>
      <td>Commencement Date</td>
      <td>{{Moa.CommencementDate}}</td>
    </tr>
    <tr>
      <td>Target Completion Date</td>
      <td>{{Moa.TerminationDate}}</td>
    </tr>
  </tbody>
</table>

<h3>General Terms &amp; Conditions</h3>
<ul>
  <li>Tranche disbursements shall be executed upon milestone deliverable validation and proof of learner registration.</li>
  <li>All financial claims are subject to PFMA controls and independent audit inspection.</li>
  <li>This agreement is legally binding once signed by both authorised representatives.</li>
</ul>";
            dgMoa.VersionNotes ??= "Initial Discretionary Grant MoA baseline template.";
        }

        await db.SaveChangesAsync();
        logger?.LogInformation("Default rich HTML document templates seeded successfully.");
    }
}
