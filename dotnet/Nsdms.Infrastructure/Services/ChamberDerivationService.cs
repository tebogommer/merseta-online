using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;

namespace Nsdms.Infrastructure.Services;

/// <summary>
/// Domain service implementing Chamber and Dynamics GP Vendor Class derivation rules,
/// organisation-level compliance auditing, and workflow action blocking.
/// </summary>
public class ChamberDerivationService : IChamberDerivationService
{
    private readonly INsdmsDbContextFactory _factory;
    private readonly IAuditService _audit;
    private readonly ILogger<ChamberDerivationService> _logger;

    public ChamberDerivationService(
        INsdmsDbContextFactory factory,
        IAuditService audit,
        ILogger<ChamberDerivationService> logger)
    {
        _factory = factory;
        _audit = audit;
        _logger = logger;
    }

    public async Task<ChamberDerivationResult> DeriveChamberAndVendorClassAsync(
        string? sicCode,
        string? organisationTypeCode = null,
        string? manualChamberCode = null,
        bool isManualOverride = false)
    {
        // 1. Manual Override takes highest precedence if explicitly authorized
        if (isManualOverride && !string.IsNullOrWhiteSpace(manualChamberCode))
        {
            var normalized = NormalizeChamberCode(manualChamberCode);
            var gpClass = MapChamberToGpVendorClass(normalized);
            return new ChamberDerivationResult(
                IsSuccess: true,
                ChamberCode: normalized,
                ChamberName: GetChamberDisplayName(normalized),
                GpVendorClass: gpClass,
                ResolutionSource: "MANUAL_OVERRIDE",
                WarningMessage: null
            );
        }

        // 2. Direct Statutory SIC Code Lookup
        if (!string.IsNullOrWhiteSpace(sicCode))
        {
            using var db = await _factory.CreateDbContextAsync();
            var cleanSic = sicCode.Trim();

            var sicRecord = await db.SicCodeTypes
                .AsNoTracking()
                .FirstOrDefaultAsync(s => s.Code == cleanSic && s.Active);

            if (sicRecord != null && !string.IsNullOrWhiteSpace(sicRecord.ChamberCode))
            {
                var normalized = NormalizeChamberCode(sicRecord.ChamberCode);
                var gpClass = MapChamberToGpVendorClass(normalized);
                return new ChamberDerivationResult(
                    IsSuccess: true,
                    ChamberCode: normalized,
                    ChamberName: GetChamberDisplayName(normalized),
                    GpVendorClass: gpClass,
                    ResolutionSource: "SIC_CODE",
                    WarningMessage: null
                );
            }

            // Heuristic prefix resolution for known merSETA 5-digit/3-digit ranges
            var heuristic = ResolveChamberBySicPrefix(cleanSic);
            if (heuristic != null)
            {
                var gpClass = MapChamberToGpVendorClass(heuristic);
                return new ChamberDerivationResult(
                    IsSuccess: true,
                    ChamberCode: heuristic,
                    ChamberName: GetChamberDisplayName(heuristic),
                    GpVendorClass: gpClass,
                    ResolutionSource: "SIC_HEURISTIC",
                    WarningMessage: null
                );
            }
        }

        // 3. Organisation Legal Constitution Derivation (Public entities, TVETs, NGOs map to SETA class)
        if (!string.IsNullOrWhiteSpace(organisationTypeCode))
        {
            var typeCodeUpper = organisationTypeCode.Trim().ToUpperInvariant();
            if (typeCodeUpper.Contains("TVET") ||
                typeCodeUpper.Contains("UNIVERSITY") ||
                typeCodeUpper.Contains("PUBLIC") ||
                typeCodeUpper.Contains("GOV") ||
                typeCodeUpper.Contains("SETA") ||
                typeCodeUpper.Contains("NGO") ||
                typeCodeUpper.Contains("NPO") ||
                typeCodeUpper.Contains("TRUST"))
            {
                return new ChamberDerivationResult(
                    IsSuccess: true,
                    ChamberCode: "SETA",
                    ChamberName: "SETA / Public & Educational Institutions",
                    GpVendorClass: "SETA",
                    ResolutionSource: "ORGANISATION_TYPE",
                    WarningMessage: null
                );
            }
        }

        // 4. Missing / Unmapped Chamber Failure
        return new ChamberDerivationResult(
            IsSuccess: false,
            ChamberCode: null,
            ChamberName: null,
            GpVendorClass: null,
            ResolutionSource: "UNMAPPED",
            WarningMessage: "Chamber cannot be determined. No valid SIC code mapping or manual override provided."
        );
    }

    public async Task<ChamberValidationResult> ValidateChamberGovernanceAsync(int organisationId)
    {
        using var db = await _factory.CreateDbContextAsync();
        var org = await db.Organisations.AsNoTracking().FirstOrDefaultAsync(o => o.Id == organisationId);

        if (org == null)
        {
            return new ChamberValidationResult(
                IsCompliant: false,
                ChamberCode: null,
                GpVendorClass: null,
                CanSubmitGrantApplication: false,
                CanSubmitWsp: false,
                CanContractMoa: false,
                CanDisbursePayment: false,
                BlockingReason: $"Organisation #{organisationId} does not exist in the database."
            );
        }

        bool isMissing = org.HasMissingChamberMapping || 
                         string.IsNullOrWhiteSpace(org.ChamberCode) || 
                         string.IsNullOrWhiteSpace(org.GpVendorClass);

        if (isMissing)
        {
            return new ChamberValidationResult(
                IsCompliant: false,
                ChamberCode: org.ChamberCode,
                GpVendorClass: org.GpVendorClass,
                CanSubmitGrantApplication: false,
                CanSubmitWsp: false,
                CanContractMoa: false,
                CanDisbursePayment: false,
                BlockingReason: "Organisation is missing a verified merSETA Chamber and Dynamics GP Vendor Class mapping. All grant applications, WSP submissions, MoAs, and payment runs are blocked until a valid SIC code or manual chamber override is recorded."
            );
        }

        return new ChamberValidationResult(
            IsCompliant: true,
            ChamberCode: org.ChamberCode,
            GpVendorClass: org.GpVendorClass,
            CanSubmitGrantApplication: true,
            CanSubmitWsp: true,
            CanContractMoa: true,
            CanDisbursePayment: true,
            BlockingReason: null
        );
    }

    public async Task<Organisation> ApplyChamberDerivationAsync(int organisationId, string currentUsername = "SYSTEM")
    {
        using var db = await _factory.CreateDbContextAsync();
        var org = await db.Organisations.FindAsync(organisationId)
            ?? throw new KeyNotFoundException($"Organisation #{organisationId} not found.");

        var beforeSnapshot = new
        {
            org.ChamberCode,
            org.GpVendorClass,
            org.HasMissingChamberMapping,
            org.SicCode
        };

        var derivation = await DeriveChamberAndVendorClassAsync(
            org.SicCode,
            org.OrganisationTypeCode,
            org.ChamberCode,
            org.IsManualChamberOverride
        );

        if (derivation.IsSuccess)
        {
            org.ChamberCode = derivation.ChamberCode;
            org.GpVendorClass = derivation.GpVendorClass;
            org.HasMissingChamberMapping = false;
        }
        else
        {
            org.ChamberCode = null;
            org.GpVendorClass = null;
            org.HasMissingChamberMapping = true;
        }

        org.ModifiedAt = DateTime.UtcNow;
        org.ModifiedBy = currentUsername;

        _audit.LogAction(db, "Organisation", org.Id, "ApplyChamberDerivation", currentUsername, beforeSnapshot, new
        {
            org.ChamberCode,
            org.GpVendorClass,
            org.HasMissingChamberMapping,
            derivation.ResolutionSource
        });

        await db.SaveChangesAsync();
        return org;
    }

    public string MapChamberToGpVendorClass(string chamberCode)
    {
        var normalized = NormalizeChamberCode(chamberCode);
        return normalized switch
        {
            "AUTO" => "AUTO",
            "METAL" => "METAL",
            "MOTOR" => "MOTOR",
            "NEW_TYRE" => "NEW TYRE",
            "PLASTICS" => "PLASTICS",
            "SETA" => "SETA",
            _ => "SETA"
        };
    }

    private static string NormalizeChamberCode(string? chamberCode)
    {
        if (string.IsNullOrWhiteSpace(chamberCode)) return "SETA";
        var c = chamberCode.Trim().ToUpperInvariant();

        if (c.Contains("AUTO")) return "AUTO";
        if (c.Contains("METAL")) return "METAL";
        if (c.Contains("MOTOR")) return "MOTOR";
        if (c.Contains("TYRE") || c.Contains("TIRE")) return "NEW_TYRE";
        if (c.Contains("PLAST")) return "PLASTICS";
        if (c.Contains("SETA") || c.Contains("PUBLIC") || c.Contains("OTHER")) return "SETA";

        return c;
    }

    private static string GetChamberDisplayName(string chamberCode)
    {
        return chamberCode switch
        {
            "AUTO" => "Automotive Manufacturing Chamber",
            "METAL" => "Metal and Engineering Chamber",
            "MOTOR" => "Motor Retail & Component Manufacturing Chamber",
            "NEW_TYRE" => "New Tyre Manufacturing Chamber",
            "PLASTICS" => "Plastics Manufacturing Chamber",
            "SETA" => "SETA / Public & Educational Institutions",
            _ => chamberCode
        };
    }

    private static string? ResolveChamberBySicPrefix(string sicCode)
    {
        if (sicCode.Length < 3) return null;
        var prefix3 = sicCode[..3];

        return prefix3 switch
        {
            "381" or "382" or "383" or "385" or "386" => "METAL",
            "384" => "AUTO",
            "356" => "PLASTICS",
            "355" => "NEW_TYRE",
            "631" or "632" or "633" or "634" or "635" => "MOTOR",
            _ => null
        };
    }
}
