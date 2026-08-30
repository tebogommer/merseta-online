using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;
using System.Reflection;
using System.Xml.Linq;

namespace Nsdms.Infrastructure.Data;

/// <summary>
/// Provides extension methods to automatically reflect XML doc comments onto EF Core table and column metadata.
/// </summary>
public static class ModelBuilderDocumentationExtensions
{
    private static readonly Dictionary<string, string> XmlCommentsCache = new(StringComparer.OrdinalIgnoreCase);
    private static bool _isLoaded;
    private static readonly object LockObj = new();

    /// <summary>
    /// Scans assembly XML documentation files and configures .HasComment(...) on all EF Core entities and properties.
    /// </summary>
    public static ModelBuilder ApplyXmlDocumentation(this ModelBuilder modelBuilder)
    {
        EnsureXmlDocumentationLoaded();

        foreach (var entityType in modelBuilder.Model.GetEntityTypes())
        {
            var clrType = entityType.ClrType;
            if (clrType == null) continue;

            // 1. Table Comment
            var typeKey = $"T:{clrType.FullName}";
            var tableComment = GetComment(typeKey) ?? GetDefaultTableComment(clrType.Name);
            if (!string.IsNullOrWhiteSpace(tableComment))
            {
                entityType.SetComment(tableComment);
            }

            // 2. Column Comments
            var pk = entityType.FindPrimaryKey();
            var pkProps = pk?.Properties.Select(p => p.Name).ToHashSet() ?? new HashSet<string>();

            foreach (var property in entityType.GetProperties())
            {
                var propName = property.Name;
                var isPk = pkProps.Contains(propName);
                var isFk = property.IsForeignKey();

                // Check type property, then hierarchy base types (BaseEntity, BaseLookupType, etc.)
                var propComment = FindPropertyCommentInHierarchy(clrType, propName);

                if (string.IsNullOrWhiteSpace(propComment))
                {
                    propComment = GetDefaultPropertyComment(propName, isPk, isFk, property.ClrType);
                }

                if (!string.IsNullOrWhiteSpace(propComment))
                {
                    property.SetComment(propComment);
                }
            }
        }

        return modelBuilder;
    }

    private static string? FindPropertyCommentInHierarchy(Type clrType, string propName)
    {
        var current = clrType;
        while (current != null && current != typeof(object))
        {
            var propKey = $"P:{current.FullName}.{propName}";
            var comment = GetComment(propKey);
            if (!string.IsNullOrWhiteSpace(comment))
            {
                return comment;
            }

            current = current.BaseType;
        }

        return null;
    }

    private static string? GetComment(string memberKey)
    {
        if (XmlCommentsCache.TryGetValue(memberKey, out var comment))
        {
            return comment;
        }
        return null;
    }

    private static void EnsureXmlDocumentationLoaded()
    {
        if (_isLoaded) return;

        lock (LockObj)
        {
            if (_isLoaded) return;

            try
            {
                var candidateDirs = new HashSet<string>(StringComparer.OrdinalIgnoreCase)
                {
                    AppContext.BaseDirectory,
                    Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location) ?? "",
                    Directory.GetCurrentDirectory(),
                    Path.Combine(Directory.GetCurrentDirectory(), "bin", "Debug", "net10.0"),
                    Path.Combine(AppContext.BaseDirectory, "..", "..", "..", "..", "Nsdms.Domain", "bin", "Debug", "net10.0")
                };

                foreach (var dir in candidateDirs)
                {
                    if (string.IsNullOrWhiteSpace(dir) || !Directory.Exists(dir)) continue;

                    var xmlFiles = Directory.GetFiles(dir, "Nsdms.*.xml", SearchOption.TopDirectoryOnly);
                    foreach (var xmlFile in xmlFiles)
                    {
                        LoadXmlFile(xmlFile);
                    }
                }
            }
            catch
            {
                // Fallback gracefully to default programmatic descriptions if XML file is inaccessible
            }
            finally
            {
                _isLoaded = true;
            }
        }
    }

    private static void LoadXmlFile(string filePath)
    {
        try
        {
            var doc = XDocument.Load(filePath);
            var members = doc.Root?.Element("members")?.Elements("member");
            if (members == null) return;

            foreach (var member in members)
            {
                var name = member.Attribute("name")?.Value;
                var summary = member.Element("summary")?.Value;

                if (!string.IsNullOrWhiteSpace(name) && !string.IsNullOrWhiteSpace(summary))
                {
                    var cleanSummary = string.Join(" ", summary
                        .Split(new[] { '\r', '\n', '\t' }, StringSplitOptions.RemoveEmptyEntries)
                        .Select(s => s.Trim())
                        .Where(s => !string.IsNullOrEmpty(s)));

                    XmlCommentsCache[name] = cleanSummary;
                }
            }
        }
        catch
        {
            // Ignore unparseable files
        }
    }

    private static string GetDefaultTableComment(string entityName) => entityName switch
    {
        "Organisation" => "Registered employers, host workplaces, and legal enterprise entities under MerSETA jurisdiction.",
        "Person" => "Individual identity demographic profile (RSA ID, demographics, contact info).",
        "TrainingProvider" => "Accredited Skills Development Providers (SDP) offering registered occupational qualifications.",
        "WspSubmission" => "Workplace Skills Plan (WSP) & Annual Training Report (ATR) submissions for mandatory grants.",
        "GrantApplication" => "Discretionary Grant project funding applications submitted during open funding windows.",
        "GrantMoa" => "Memorandum of Agreement contractual legal commitments for approved discretionary grants.",
        "GrantMoaMilestone" => "Tranche delivery milestones (Inception, Midterm, Final, Closeout) for MOA contracts.",
        "GrantTranchePayment" => "Tranche tax invoice requisitions, finance dual-authorization, and EFT payout batches.",
        "MandatoryGrantDisbursement" => "Statutory 20% Skills Development Levy rebate calculation ledger for compliant employers.",
        "InterSetaTransfer" => "Chamber and SIC code employer migrations with counterpart SETAs and DHET approvals.",
        "EtqaAssessor" => "Registered ETQA assessors and moderators with registered unit standard / qualification scopes.",
        "WorkplaceApproval" => "Workplace site inspection approval records with mentor-to-learner ratio allocations.",
        "CompanyLearner" => "Registered apprentices, learnerships, and skills programme learners.",
        "LearnerTradeTest" => "Trade test serial certifications, ARPL evaluations, and competency awards.",
        "WorkflowDefinition" => "Universal workflow process blueprint defining lifecycle states, gates, and transitions.",
        "WorkflowInstance" => "Universal state machine execution instances for long-running approval lifecycles.",
        "WorkflowTask" => "Actionable review, inspection, and verification tasks assigned to SETA roles.",
        "DocumentMetadata" => "SHA-256 integrity hashed digital evidence files in the Document Vault.",
        "AuditLog" => "Immutable operational audit trail capturing user, action, timestamp, and JSON before/after snapshots.",
        _ => entityName.EndsWith("Type")
            ? $"Standard lookup reference domain table for {entityName} codes."
            : $"System entity for {entityName} data governance."
    };

    private static string GetDefaultPropertyComment(string propName, bool isPk, bool isFk, Type clrType)
    {
        if (isPk) return "Auto-generated integer primary key identifier.";
        if (isFk) return $"Foreign key relational reference to parent entity.";

        return propName switch
        {
            "Id" => "Auto-generated integer primary key identifier.",
            "Code" => "Unique alphanumeric code identifier acting as primary key.",
            "Name" => "Display title / name of the record.",
            "Description" => "Detailed description and contextual notes.",
            "Active" or "IsActive" => "Indicates whether the record is active and operational.",
            "CreatedAt" => "UTC timestamp when the record was initially created.",
            "CreatedBy" => "User identifier or system process that created the record.",
            "ModifiedAt" => "UTC timestamp when the record was last updated.",
            "ModifiedBy" => "User identifier or system process that last updated the record.",
            "MoaNumber" => "Unique legal MOA contract reference number.",
            "SdlNumber" => "SARS Skills Development Levy registration number (e.g. L123456789).",
            "RsaIdNumber" => "13-digit South African National Identity Number.",
            "TotalContractValue" => "Total committed monetary value in South African Rands (ZAR).",
            "TranchePercentage" => "Deliverable payment percentage (e.g. 30%, 20%).",
            "TrancheAmount" => "Calculated monetary amount for this delivery milestone in ZAR.",
            "CalculatedRebateAmount" => "Calculated 20% statutory levy rebate amount in ZAR.",
            "Status" or "StatusCode" or "MoaStatusCode" => "Current lifecycle state code in the workflow engine.",
            "BatchNumber" => "Financial transaction batch grouping reference.",
            "RegistrationDate" => "Official registration date of the record.",
            "Email" => "Primary email address.",
            "PhoneNumber" => "Primary contact telephone number.",
            _ => $"Domain property for {propName}."
        };
    }
}
