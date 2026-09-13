using System.Text;
using System.Text.RegularExpressions;
using Nsdms.Application.Common.Interfaces;

namespace Nsdms.Application.Services;

/// <summary>
/// Central catalog of statutory document placeholders with interpolation and syntax validation.
/// </summary>
public class DocumentPlaceholderRegistry : IDocumentPlaceholderRegistry
{
    private static readonly Regex TokenRegex = new(@"\{\{([A-Za-z0-9_\.\:]+)\}\}", RegexOptions.Compiled);
    private static readonly Regex MalformedTokenRegex = new(@"\{\{[^}]*$", RegexOptions.Compiled | RegexOptions.Multiline);

    private readonly List<DocumentPlaceholderItem> _placeholders = new()
    {
        // 1. Employer / Organisation
        new("Employer.Name", "Employer Legal Name", "Employer / Organisation", "Registered legal entity name of the organisation", "Apex Engineering Works (Pty) Ltd"),
        new("Employer.SdlNumber", "SDL Number", "Employer / Organisation", "Statutory Skills Development Levy registration number", "L998877665"),
        new("Employer.TradingName", "Trading Name", "Employer / Organisation", "Commercial trading name or division", "Apex Industrial Solutions"),
        new("Employer.RegistrationNumber", "CIPC Registration Number", "Employer / Organisation", "Company registration or NPO/trust registration reference", "2018/123456/07"),
        new("Employer.PhysicalAddress", "Physical Address", "Employer / Organisation", "Physical premises or factory operating address", "14 Power Street, Germiston, Gauteng"),
        new("Employer.PostalCode", "Postal Code", "Employer / Organisation", "Postal code", "1401"),
        new("ContactPerson.FullName", "Contact Person Full Name", "Employer / Organisation", "Designated corporate contact person or Skills Development Facilitator", "Johan van der Merwe"),
        new("ContactPerson.Email", "Contact Email", "Employer / Organisation", "Primary contact email address", "johan.vdm@apexeng.co.za"),
        new("ContactPerson.Phone", "Contact Phone", "Employer / Organisation", "Primary contact phone or mobile number", "+27 11 824 5000"),

        // 2. Mandatory Grant (WSP / ATR)
        new("Wsp.SchemeYear", "WSP Scheme Year", "Mandatory Grant (WSP)", "Statutory financial scheme year for the submission", DateTime.UtcNow.Year.ToString(), "MandatoryGrant"),
        new("Wsp.SubmissionDate", "Submission Date", "Mandatory Grant (WSP)", "Date when WSP/ATR was lodged by the employer", "28 April " + DateTime.UtcNow.Year, "MandatoryGrant"),
        new("Wsp.ApprovalDate", "Approval Date", "Mandatory Grant (WSP)", "Date when merSETA committee approved the submission", DateTime.UtcNow.ToString("dd MMMM yyyy"), "MandatoryGrant"),
        new("Wsp.RebateAmount", "Mandatory Grant Rebate Amount", "Mandatory Grant (WSP)", "Calculated 20% Mandatory Grant disbursement rebate allocation", "R 345,000.00", "MandatoryGrant"),
        new("Wsp.LevyAmount", "Annual Levy Contributed", "Mandatory Grant (WSP)", "Total statutory 1% skills levy contributed over 12 months", "R 2,156,250.00", "MandatoryGrant"),
        new("Wsp.ExtensionDueDate", "Extension Due Date", "Mandatory Grant (WSP)", "Granted statutory submission extension deadline (if applicable)", "31 May " + DateTime.UtcNow.Year, "MandatoryGrant"),

        // 3. Trade Test & ARPL (Artisan)
        new("Learner.FullName", "Learner Full Name", "Trade Test & ARPL", "Full names and surname of the trade test candidate or apprentice", "Sipho Khumalo", "TradeTest"),
        new("Learner.RsaIdNumber", "National ID Number", "Trade Test & ARPL", "13-digit RSA National ID or Passport number", "9501015082084", "TradeTest"),
        new("Trade.Title", "Designated Trade Title", "Trade Test & ARPL", "Official NAMB / QCTO designated trade description", "Mechanical Fitter", "TradeTest"),
        new("Trade.OfoCode", "Trade OFO Code", "Trade Test & ARPL", "Organising Framework for Occupations (OFO) code", "653303", "TradeTest"),
        new("TradeTest.SerialNumber", "Trade Test Serial Number", "Trade Test & ARPL", "Official regional QA issued certificate serial number", $"TT-{DateTime.UtcNow.Year}-00458", "TradeTest"),
        new("TradeTest.TestCenterName", "Accredited Test Centre", "Trade Test & ARPL", "Name of the accredited Trade Test Centre where assessment occurred", "merSETA Advanced Manufacturing Training Centre (Midrand)", "TradeTest"),
        new("TradeTest.AssessmentDate", "Assessment Date", "Trade Test & ARPL", "Date when the trade test evaluation was completed", DateTime.UtcNow.ToString("dd MMMM yyyy"), "TradeTest"),
        new("TradeTest.Outcome", "Competency Outcome", "Trade Test & ARPL", "Evaluation outcome (e.g. COMPETENT AS AN ARTISAN)", "COMPETENT AS AN ARTISAN", "TradeTest"),

        // 4. Skills Development Provider (SDP) Accreditation
        new("Provider.Name", "Provider Legal Name", "SDP Accreditation", "Accredited Skills Development Provider institution legal name", "Gauteng Advanced Technical Academy", "EtqaAccreditation"),
        new("Provider.AccreditationNumber", "Accreditation Number", "SDP Accreditation", "Unique ETQA / QCTO accreditation registration number", $"17-QA/ACC/0892/{DateTime.UtcNow:yy}", "EtqaAccreditation"),
        new("Accreditation.StartDate", "Accreditation Start Date", "SDP Accreditation", "Commencement date of accreditation period", "01 January " + DateTime.UtcNow.Year, "EtqaAccreditation"),
        new("Accreditation.ExpiryDate", "Accreditation Expiry Date", "SDP Accreditation", "Triennial accreditation renewal deadline", "31 December " + (DateTime.UtcNow.Year + 3), "EtqaAccreditation"),
        new("Accreditation.ScopeOfApproval", "Scope of Approval", "SDP Accreditation", "Registered qualifications or unit standard skills programmes accredited", "National Certificate: Mechanical Engineering (Fitting & Turning, NQF Level 4)", "EtqaAccreditation"),

        // 5. Discretionary Grant (DG) & Contracts
        new("Moa.ContractNumber", "DG MoA Contract Reference", "Discretionary Grant (DG)", "Official Discretionary Grant Memorandum of Agreement tracking reference", $"DG-{DateTime.UtcNow.Year}-MOA-0089", "DiscretionaryGrant"),
        new("Moa.ApprovedAmount", "Approved Grant Value", "Discretionary Grant (DG)", "Total approved contractual grant funding amount", "R 1,250,000.00", "DiscretionaryGrant"),
        new("Moa.ProjectTitle", "Project / Intervention Title", "Discretionary Grant (DG)", "Strategic project or PIVOTAL intervention title", "Artisan Apprenticeship Accelerated Development Initiative", "DiscretionaryGrant"),
        new("Moa.CommencementDate", "Contract Commencement Date", "Discretionary Grant (DG)", "Official agreement commencement date", "01 July " + DateTime.UtcNow.Year, "DiscretionaryGrant"),
        new("Moa.TerminationDate", "Contract Termination Date", "Discretionary Grant (DG)", "Agreement contractual close-out date", "30 June " + (DateTime.UtcNow.Year + 2), "DiscretionaryGrant"),

        // 6. Signatory & Legal Verification
        new("Signatory.Name", "Authorised Signatory Name", "Signatory & Legal", "Full name of the designated merSETA executive authority", "Disetlo Molapo"),
        new("Signatory.Title", "Authorised Signatory Title", "Signatory & Legal", "Executive designation of the signatory", "Chief Executive Officer / ETQA Senior Manager"),
        new("Verification.DocumentNumber", "Document Reference", "Signatory & Legal", "Unique document tracking identifier", $"DOC-{DateTime.UtcNow.Year}-STD-0849"),
        new("Verification.IssuedDate", "Issuance Date", "Signatory & Legal", "Date when the official outcome was issued", DateTime.UtcNow.ToString("dd MMMM yyyy")),
        new("Verification.DigitalSeal", "Digital Security Seal", "Signatory & Legal", "Truncated SHA-256 cryptographic security verification seal", "7f8a9b2c3d4e5f6a"),
        new("Verification.QrCode", "Verification QR Seal", "Signatory & Legal", "Visual 2D QR barcode linking to the public validation portal", "[2D Barcode Verification Seal]"),

        // 7. System & General
        new("System.CurrentDate", "Current Date", "System & General", "Current calendar date formatted (dd MMMM yyyy)", DateTime.UtcNow.ToString("dd MMMM yyyy")),
        new("System.CurrentYear", "Current Year", "System & General", "Current four-digit calendar year", DateTime.UtcNow.Year.ToString()),
        new("System.SetaName", "SETA Legal Name", "System & General", "Official statutory SETA name", "Manufacturing, Engineering and Related Services SETA (merSETA)"),

        // 8. Legacy Backwards-Compatibility Aliases
        new("RecipientName", "Recipient Name (Alias)", "Legacy Aliases", "Maps to Employer.Name or Learner.FullName", "Apex Engineering Works (Pty) Ltd"),
        new("RecipientIdentifier", "Recipient Identifier (Alias)", "Legacy Aliases", "Maps to SDL Number or RSA ID Number", "L998877665"),
        new("DocumentNumber", "Document Number (Alias)", "Legacy Aliases", "Maps to Verification.DocumentNumber", $"DOC-{DateTime.UtcNow.Year}-001"),
        new("IssuedDate", "Issued Date (Alias)", "Legacy Aliases", "Maps to Verification.IssuedDate", DateTime.UtcNow.ToString("dd MMMM yyyy")),
        new("TradeTitle", "Trade Title (Alias)", "Legacy Aliases", "Maps to Trade.Title", "Mechanical Fitter"),
        new("LearnerFullName", "Learner Name (Alias)", "Legacy Aliases", "Maps to Learner.FullName", "Sipho Khumalo"),
        new("LearnerIdNumber", "Learner ID (Alias)", "Legacy Aliases", "Maps to Learner.RsaIdNumber", "9501015082084"),
        new("CertificateNumber", "Certificate Number (Alias)", "Legacy Aliases", "Maps to TradeTest.SerialNumber", $"TT-{DateTime.UtcNow.Year}-00458"),
        new("AccreditationNumber", "Accreditation Number (Alias)", "Legacy Aliases", "Maps to Provider.AccreditationNumber", $"17-QA/ACC/0892/{DateTime.UtcNow:yy}"),
        new("RebateAmount", "Rebate Amount (Alias)", "Legacy Aliases", "Maps to Wsp.RebateAmount", "R 345,000.00"),
        new("SignatoryName", "Signatory Name (Alias)", "Legacy Aliases", "Maps to Signatory.Name", "Disetlo Molapo"),
        new("SignatoryTitle", "Signatory Title (Alias)", "Legacy Aliases", "Maps to Signatory.Title", "Chief Executive Officer / ETQA Senior Manager")
    };

    public IReadOnlyList<DocumentPlaceholderItem> GetAllPlaceholders() => _placeholders.AsReadOnly();

    public IReadOnlyList<DocumentPlaceholderItem> GetPlaceholdersForCategory(string? documentCategory)
    {
        if (string.IsNullOrWhiteSpace(documentCategory) || documentCategory.Equals("All", StringComparison.OrdinalIgnoreCase))
        {
            return _placeholders.Where(p => p.Category != "Legacy Aliases").ToList().AsReadOnly();
        }

        return _placeholders.Where(p => 
            p.Category != "Legacy Aliases" && 
            (p.ApplicableCategory.Equals("All", StringComparison.OrdinalIgnoreCase) ||
             p.ApplicableCategory.Equals(documentCategory, StringComparison.OrdinalIgnoreCase)))
            .ToList()
            .AsReadOnly();
    }

    public IReadOnlyList<string> GetCategories()
    {
        return _placeholders
            .Where(p => p.Category != "Legacy Aliases")
            .Select(p => p.Category)
            .Distinct()
            .ToList()
            .AsReadOnly();
    }

    public string Interpolate(string templateContent, IDictionary<string, string>? tokens)
    {
        if (string.IsNullOrEmpty(templateContent)) return string.Empty;

        var merged = GetSampleTokenValues();
        if (tokens != null)
        {
            foreach (var kvp in tokens)
            {
                merged[kvp.Key] = kvp.Value;
                // Also synchronize dotted and flat aliases
                SyncAliases(merged, kvp.Key, kvp.Value);
            }
        }

        var sb = new StringBuilder(templateContent);
        foreach (var kvp in merged)
        {
            sb.Replace($"{{{{{kvp.Key}}}}}", kvp.Value ?? string.Empty);
        }

        return sb.ToString();
    }

    public List<string> ExtractTokens(string templateContent)
    {
        if (string.IsNullOrEmpty(templateContent)) return new List<string>();

        var matches = TokenRegex.Matches(templateContent);
        var list = new List<string>();
        foreach (Match m in matches)
        {
            if (m.Groups.Count > 1)
            {
                var token = m.Groups[1].Value.Trim();
                if (!list.Contains(token))
                {
                    list.Add(token);
                }
            }
        }
        return list;
    }

    public TemplateTokenValidationResult ValidateTemplate(string templateContent, string? documentCategory = null)
    {
        if (string.IsNullOrEmpty(templateContent))
        {
            return new TemplateTokenValidationResult(true, new(), new(), new());
        }

        var used = ExtractTokens(templateContent);
        var knownKeys = _placeholders.Select(p => p.Key).ToHashSet(StringComparer.OrdinalIgnoreCase);

        var unknown = used.Where(k => !knownKeys.Contains(k)).ToList();

        var malformed = new List<string>();
        var malformedMatches = MalformedTokenRegex.Matches(templateContent);
        foreach (Match m in malformedMatches)
        {
            if (!TokenRegex.IsMatch(m.Value))
            {
                malformed.Add(m.Value.Trim());
            }
        }

        return new TemplateTokenValidationResult(
            IsValid: unknown.Count == 0 && malformed.Count == 0,
            UsedTokens: used,
            UnknownTokens: unknown,
            MalformedTokens: malformed
        );
    }

    public Dictionary<string, string> GetSampleTokenValues(string? documentCategory = null)
    {
        var dict = new Dictionary<string, string>(StringComparer.OrdinalIgnoreCase);
        foreach (var p in _placeholders)
        {
            dict[p.Key] = p.SampleValue;
        }
        return dict;
    }

    private static void SyncAliases(Dictionary<string, string> dict, string key, string value)
    {
        switch (key)
        {
            case "Employer.Name": dict["RecipientName"] = value; break;
            case "RecipientName": dict["Employer.Name"] = value; dict["Learner.FullName"] = value; break;
            case "Employer.SdlNumber": dict["RecipientIdentifier"] = value; break;
            case "RecipientIdentifier": dict["Employer.SdlNumber"] = value; dict["Learner.RsaIdNumber"] = value; break;
            case "Trade.Title": dict["TradeTitle"] = value; break;
            case "TradeTitle": dict["Trade.Title"] = value; break;
            case "Learner.FullName": dict["LearnerFullName"] = value; break;
            case "LearnerFullName": dict["Learner.FullName"] = value; break;
            case "Learner.RsaIdNumber": dict["LearnerIdNumber"] = value; break;
            case "LearnerIdNumber": dict["Learner.RsaIdNumber"] = value; break;
            case "TradeTest.SerialNumber": dict["CertificateNumber"] = value; break;
            case "CertificateNumber": dict["TradeTest.SerialNumber"] = value; break;
            case "Provider.AccreditationNumber": dict["AccreditationNumber"] = value; break;
            case "AccreditationNumber": dict["Provider.AccreditationNumber"] = value; break;
            case "Wsp.RebateAmount": dict["RebateAmount"] = value; break;
            case "RebateAmount": dict["Wsp.RebateAmount"] = value; break;
            case "Signatory.Name": dict["SignatoryName"] = value; break;
            case "SignatoryName": dict["Signatory.Name"] = value; break;
            case "Signatory.Title": dict["SignatoryTitle"] = value; break;
            case "SignatoryTitle": dict["Signatory.Title"] = value; break;
            case "Verification.DocumentNumber": dict["DocumentNumber"] = value; break;
            case "DocumentNumber": dict["Verification.DocumentNumber"] = value; break;
            case "Verification.IssuedDate": dict["IssuedDate"] = value; break;
            case "IssuedDate": dict["Verification.IssuedDate"] = value; break;
        }
    }
}
