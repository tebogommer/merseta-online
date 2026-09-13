namespace Nsdms.Application.Common.Interfaces;

/// <summary>
/// Metadata definition for a dynamic document placeholder token.
/// </summary>
public record DocumentPlaceholderItem(
    string Key,
    string DisplayName,
    string Category,
    string Description,
    string SampleValue,
    string ApplicableCategory = "All"
)
{
    /// <summary>
    /// Formatted syntax representation (e.g. {{Employer.Name}}).
    /// </summary>
    public string Token => $"{{{{{Key}}}}}";
}

/// <summary>
/// Result summary of template placeholder validation.
/// </summary>
public record TemplateTokenValidationResult(
    bool IsValid,
    List<string> UsedTokens,
    List<string> UnknownTokens,
    List<string> MalformedTokens
);

/// <summary>
/// Universal registry and resolution engine for document placeholders across all statutory modules.
/// </summary>
public interface IDocumentPlaceholderRegistry
{
    /// <summary>
    /// Retrieves all registered placeholders available across the system.
    /// </summary>
    IReadOnlyList<DocumentPlaceholderItem> GetAllPlaceholders();

    /// <summary>
    /// Retrieves placeholders applicable to a specific document category (e.g. MandatoryGrant, TradeTest, EtqaAccreditation, DiscretionaryGrant).
    /// </summary>
    IReadOnlyList<DocumentPlaceholderItem> GetPlaceholdersForCategory(string? documentCategory);

    /// <summary>
    /// Retrieves distinct domain categories for organizing placeholder palettes.
    /// </summary>
    IReadOnlyList<string> GetCategories();

    /// <summary>
    /// Interpolates placeholder tokens within template text using supplied or scenario values.
    /// </summary>
    string Interpolate(string templateContent, IDictionary<string, string>? tokens);

    /// <summary>
    /// Extracts all tokens enclosed in {{...}} from template text.
    /// </summary>
    List<string> ExtractTokens(string templateContent);

    /// <summary>
    /// Validates template content for unknown or malformed tokens.
    /// </summary>
    TemplateTokenValidationResult ValidateTemplate(string templateContent, string? documentCategory = null);

    /// <summary>
    /// Generates sample token values populated for a specific document category.
    /// </summary>
    Dictionary<string, string> GetSampleTokenValues(string? documentCategory = null);
}
