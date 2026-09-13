using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Declares a typed, named parameter that an invoking agent or consumer may fill.
/// Agents can only supply parameter values; they cannot alter the computation template itself.
/// </summary>
public class ComputationParameter : BaseEntity
{
    public int ComputationId { get; set; }

    /// <summary>
    /// Name of the SQL parameter (e.g., '@FinancialYearId', '@OrganisationId').
    /// </summary>
    public string ParameterName { get; set; } = string.Empty;

    /// <summary>
    /// Type descriptor (e.g. 'int', 'nvarchar', 'decimal', 'date', 'bit').
    /// </summary>
    public string ParameterType { get; set; } = "nvarchar";

    /// <summary>
    /// Indicates whether the parameter is strictly required for execution.
    /// </summary>
    public bool IsRequired { get; set; } = true;

    /// <summary>
    /// Optional default parameter value.
    /// </summary>
    public string? DefaultValue { get; set; }

    /// <summary>
    /// Parameter description and binding instructions.
    /// </summary>
    public string? Description { get; set; }

    public AttestedComputation Computation { get; set; } = null!;
}
