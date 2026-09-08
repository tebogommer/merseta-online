namespace Nsdms.Domain.Entities;

/// <summary>
/// Canonical constants representing the 5 statutory accreditation intake streams per merSETA / QCTO governance:
/// 1. PrimaryAccreditation: merSETA is the Primary ETQA (Full 5-year cycle, merSETA certificate ETQ-TP-002).
/// 2. ProgrammeApproval: Secondary scope from another SETA (e.g. CHIETA, Services SETA).
/// 3. QctoSkillsDevelopmentProvider: Occupational qualifications accredited directly by QCTO.
/// 4. QctoTradeTestCentre: Trade test centre registered with NAMB / QCTO.
/// 5. NonMerSetaScope: Non-merSETA occupational scope.
/// </summary>
public static class AccreditationStreamType
{
    public const string PrimaryAccreditation = "PrimaryAccreditation";
    public const string ProgrammeApproval = "ProgrammeApproval";
    public const string QctoSkillsDevelopmentProvider = "QctoSkillsDevelopmentProvider";
    public const string QctoTradeTestCentre = "QctoTradeTestCentre";
    public const string NonMerSetaScope = "NonMerSetaScope";

    public static readonly string[] AllStreams =
    [
        PrimaryAccreditation,
        ProgrammeApproval,
        QctoSkillsDevelopmentProvider,
        QctoTradeTestCentre,
        NonMerSetaScope
    ];

    public static bool IsQctoOrSecondary(string? stream) =>
        stream is QctoSkillsDevelopmentProvider or QctoTradeTestCentre or ProgrammeApproval or NonMerSetaScope;

    public static bool IsPrimary(string? stream) =>
        string.IsNullOrWhiteSpace(stream) || stream == PrimaryAccreditation || stream.StartsWith("Primary", StringComparison.OrdinalIgnoreCase);
}
