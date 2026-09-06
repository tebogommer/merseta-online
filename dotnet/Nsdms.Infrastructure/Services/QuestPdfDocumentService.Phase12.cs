using System;
using System.IO;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;
using QuestPDF.Fluent;
using QuestPDF.Helpers;
using QuestPDF.Infrastructure;

namespace Nsdms.Infrastructure.Services;

public partial class QuestPdfDocumentService : IPdfDocumentService
{
    #region Phase 12: ETQ-TP-012 Official Relationship Officer Letter of Introduction

    public async Task<byte[]> GenerateLetterOfIntroductionPdfAsync(int organisationId, int portfolioId)
    {
        await using var db = await _contextFactory.CreateDbContextAsync();

        var org = await db.Organisations
            .FirstOrDefaultAsync(o => o.Id == organisationId)
            ?? throw new KeyNotFoundException($"Organisation with ID {organisationId} was not found.");

        var portfolio = await db.OrganisationPortfolios
            .FirstOrDefaultAsync(p => p.Id == portfolioId)
            ?? throw new KeyNotFoundException($"OrganisationPortfolio with ID {portfolioId} was not found.");

        var demarcation = await db.TerritoryDemarcations
            .Include(d => d.Zone)
            .FirstOrDefaultAsync(d => d.TownName.ToLower() == (org.PostalAddressPostalCode ?? "").ToLower() ||
                                      (org.PhysicalAddress != null && org.PhysicalAddress.ToLower().Contains(d.TownName.ToLower())));

        var zoneName = demarcation?.Zone?.ZoneName ?? "Sub-Regional Operational Zone";
        var regionName = portfolio.ManagingRegionCode switch
        {
            "GAUTENG_SOUTH" => "Gauteng South Regional Office (Johannesburg)",
            "GAUTENG_NORTH" => "Gauteng North Regional Office (Pretoria)",
            "KZN" => "KwaZulu-Natal Regional Office (Durban)",
            "WESTERN_CAPE" => "Western Cape Regional Office (Cape Town)",
            "EASTERN_CAPE" => "Eastern Cape Regional Office (Gqeberha)",
            "FREE_STATE_NC" => "Free State & Northern Cape Regional Office (Bloemfontein)",
            "MPUMALANGA_LIMPOPO" => "Mpumalanga & Limpopo Regional Office (Witbank)",
            _ => "merSETA Regional Operations Office"
        };

        var setaName = await _config.GetValueAsync("General.SetaName", "Manufacturing, Engineering and Related Services SETA (merSETA)");
        var baseUrl = await _config.GetValueAsync("System.BaseUrl", "https://nsdms.merseta.org.za");
        var verifyUrl = $"{baseUrl.TrimEnd('/')}/verify/officer/{portfolio.RelationshipOfficerUserId}";
        var qrBytes = GenerateQrBytes(verifyUrl);

        var timestamp = DateTime.UtcNow;
        var securitySeed = $"{org.SdlNumber}:{portfolio.RelationshipOfficerUserId}:{portfolio.Id}:{timestamp:O}";
        var hashBytes = SHA256.HashData(Encoding.UTF8.GetBytes(securitySeed));
        var securitySeal = Convert.ToHexString(hashBytes).ToLowerInvariant();

        var doc = Document.Create(container =>
        {
            container.Page(page =>
            {
                page.Size(PageSizes.A4);
                page.Margin(1.5f, Unit.Centimetre);
                page.PageColor(Colors.White);
                page.DefaultTextStyle(x => x.FontSize(10).FontFamily("Arial"));

                // Header
                page.Header().Column(col =>
                {
                    col.Item().Row(r =>
                    {
                        r.RelativeItem(3).Column(c =>
                        {
                            c.Item().Text("REPUBLIC OF SOUTH AFRICA").Bold().FontSize(10).FontColor(Colors.Grey.Darken3);
                            c.Item().Text(setaName).Bold().FontSize(12).FontColor(Colors.Blue.Darken3);
                            c.Item().Text("CLIENT SERVICE & STAKEHOLDER ENGAGEMENT DIVISION").FontSize(9).FontColor(Colors.Grey.Darken2);
                            c.Item().PaddingTop(2).Text("ETQ-TP-012: OFFICIAL RELATIONSHIP OFFICER INTRODUCTION").Bold().FontSize(14).FontColor(Colors.Black);
                        });
                        r.RelativeItem(1).AlignRight().Column(c =>
                        {
                            c.Item().Width(52).Height(52).Image(qrBytes);
                            c.Item().AlignCenter().Text("Scan to Verify").FontSize(7).FontColor(Colors.Blue.Darken2);
                        });
                    });
                    col.Item().PaddingTop(6).LineHorizontal(1.5f).LineColor(Colors.Blue.Darken2);
                });

                // Body Content
                page.Content().PaddingVertical(14).Column(col =>
                {
                    col.Spacing(12);

                    // Date & Notice Reference Box
                    col.Item().Row(r =>
                    {
                        r.RelativeItem().Text($"Date Issued: {timestamp:dd MMMM yyyy}").FontSize(9);
                        r.RelativeItem().AlignRight().Text($"Ref: INTRO-{portfolio.Id:D5}-{timestamp:yyyy}").Bold().FontSize(9);
                    });

                    // Addressee
                    col.Item().Background(Colors.Grey.Lighten4).Padding(10).Column(c =>
                    {
                        c.Spacing(3);
                        c.Item().Text("TO: THE ACCOUNTING AUTHORITY & SKILLS DEVELOPMENT FACILITATOR (SDF)").Bold().FontSize(10).FontColor(Colors.Blue.Darken3);
                        c.Item().Text($"Organisation Name: {org.LegalName}").Bold();
                        if (!string.IsNullOrWhiteSpace(org.TradingName) && org.TradingName != org.LegalName)
                        {
                            c.Item().Text($"Trading As: {org.TradingName}");
                        }
                        c.Item().Text($"Skills Development Levy (SDL) Number: {org.SdlNumber ?? "N/A"}").FontFamily("Courier New");
                        c.Item().Text($"Physical Address: {org.PhysicalAddress ?? "Registered merSETA Employer Facility"}");
                        c.Item().Text($"Demarcated Region & Zone: {regionName} • {zoneName}");
                    });

                    // Subject
                    col.Item().Text("SUBJECT: OFFICIAL INTRODUCTION OF YOUR DESIGNATED MERSETA RELATIONSHIP OFFICER").Bold().FontSize(11).Underline();

                    // Salutation & Intro
                    col.Item().Text("Dear Stakeholder / Skills Development Facilitator,");
                    col.Item().Text("The Manufacturing, Engineering and Related Services SETA (merSETA) is committed to providing responsive, professional, and efficient client liaison services to all levy-paying and registered stakeholder organisations. We are pleased to formally introduce your designated merSETA Relationship Officer who has been allocated to oversee and facilitate all skills development initiatives, discretionary grant allocations, workplace approvals, and statutory reporting for your organisation.");

                    // Designated Officer Credentials Box
                    col.Item().Border(1).BorderColor(Colors.Blue.Darken1).Padding(10).Column(c =>
                    {
                        c.Spacing(4);
                        c.Item().Text("DESIGNATED MERSETA RELATIONSHIP OFFICER DETAILS").Bold().FontSize(10).FontColor(Colors.Blue.Darken3);
                        c.Item().Row(r =>
                        {
                            r.RelativeItem(2).Text(t => { t.Span("Full Name: ").Bold(); t.Span(portfolio.RelationshipOfficerName); });
                            r.RelativeItem(2).Text(t => { t.Span("Official User ID: ").Bold(); t.Span(portfolio.RelationshipOfficerUserId); });
                        });
                        c.Item().Row(r =>
                        {
                            r.RelativeItem(2).Text(t => { t.Span("Designation / Role: ").Bold(); t.Span(portfolio.PortfolioRoleCode); });
                            r.RelativeItem(2).Text(t => { t.Span("Direct Email: ").Bold(); t.Span(portfolio.RelationshipOfficerEmail); });
                        });
                        c.Item().Row(r =>
                        {
                            r.RelativeItem(2).Text(t => { t.Span("Allocation Mode: ").Bold(); t.Span(portfolio.IsCrossRegionalAssignment ? "Cross-Regional Strategic Account" : "Territorial Zone Allocation"); });
                            r.RelativeItem(2).Text(t => { t.Span("Effective Date: ").Bold(); t.Span($"{portfolio.EffectiveFrom:dd MMMM yyyy}"); });
                        });
                    });

                    // Scope of Support
                    col.Item().Column(c =>
                    {
                        c.Spacing(3);
                        c.Item().Text("Scope of Advisory & Liaison Services Covered:").Bold().FontSize(10);
                        c.Item().Text("• Workplace Approval (WPA) on-site and desktop verification audits.");
                        c.Item().Text("• Workplace Skills Plan and Annual Training Report (WSP/ATR) mandatory grant support.");
                        c.Item().Text("• Discretionary Grant (DG) Project Implementation Plan (PIP) milestone monitoring & tranche claims.");
                        c.Item().Text("• Learner agreement registrations, tripartite learnerships, and apprentice ratio policy advisory.");
                        c.Item().Text("• Trade test preparation and artisan recognition of prior learning (ARPL) assistance.");
                    });

                    // Escalation Box
                    col.Item().Background(Colors.Grey.Lighten5).Border(0.5f).BorderColor(Colors.Grey.Lighten2).Padding(8).Column(c =>
                    {
                        c.Spacing(2);
                        c.Item().Text("Regional Office Escalation & Governance Contacts").Bold().FontSize(9).FontColor(Colors.Grey.Darken3);
                        c.Item().Text($"Managing Regional Office: {regionName}").FontSize(8.5f);
                        c.Item().Text("Client Liaison Coordinator (CLC): Regional Coordination Unit • coordination@merseta.org.za").FontSize(8.5f);
                        c.Item().Text("Client Relations Manager (CRM): Regional Management Office • crm@merseta.org.za").FontSize(8.5f);
                        c.Item().Text("National Helpdesk: 010 219 3000 • Website: www.merseta.org.za").FontSize(8.5f);
                    });

                    // Signatures
                    col.Item().PaddingTop(10).Row(r =>
                    {
                        r.RelativeItem().Column(c =>
                        {
                            c.Item().Text("Issued By:").FontSize(9);
                            c.Item().Text("Client Liaison Coordination Unit").Bold().FontSize(9);
                            c.Item().Text("Operations & Regional Services").FontSize(8.5f);
                        });
                        r.RelativeItem().AlignRight().Column(c =>
                        {
                            c.Item().Text("Approved By:").FontSize(9);
                            c.Item().Text("Regional Client Relations Manager (CRM)").Bold().FontSize(9);
                            c.Item().Text("merSETA Regional Operations").FontSize(8.5f);
                        });
                    });
                });

                // Footer with Digital Security Seal
                page.Footer().Column(col =>
                {
                    col.Item().LineHorizontal(1).LineColor(Colors.Grey.Lighten2);
                    col.Item().PaddingTop(3).Row(r =>
                    {
                        r.RelativeItem(3).Column(c =>
                        {
                            c.Item().Text($"Digital Security Seal: {securitySeal}").FontSize(6.5f).FontFamily("Courier New").FontColor(Colors.Grey.Darken1);
                            c.Item().Text("Controlled Statutory Document • merSETA NSDMS System-Generated Notice • Non-Repudiable Audit Sealed").FontSize(6.5f).FontColor(Colors.Grey.Darken1);
                        });
                        r.RelativeItem(1).AlignRight().Text(x =>
                        {
                            x.Span("Page ");
                            x.CurrentPageNumber();
                            x.Span(" of ");
                            x.TotalPages();
                        });
                    });
                });
            });
        });

        return doc.GeneratePdf();
    }

    #endregion
}
