using System.ComponentModel.DataAnnotations.Schema;
using Nsdms.Domain.Common;

namespace Nsdms.Domain.Lookups;

/// <summary>
/// Managed statutory and operational rejection reasons categorized by document type.
/// Allows reviewing officers to select single or multiple standardized reasons when rejecting evidence attachments.
/// </summary>
[Table("DocumentRejectionReasonType", Schema = "lookup")]
public class DocumentRejectionReasonType : BaseLookupType
{
    /// <summary>
    /// Document category code to which this rejection reason applies (e.g. "ALL", "ID_DOCUMENT", "QUALIFICATION_CERT", "BANK_CONFIRMATION", "SITE_PHOTO", "SIGNED_MOA").
    /// If set to "ALL", the reason is available across all document categories.
    /// </summary>
    public string DocumentCategoryCode { get; set; } = "ALL";

    /// <summary>
    /// Sequence display order for UI presentation in multi-select dropdowns and chip lists.
    /// </summary>
    public int DisplayOrder { get; set; } = 0;
}
