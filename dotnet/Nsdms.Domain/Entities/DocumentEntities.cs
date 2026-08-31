using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Universal enterprise document template for statutory letters, certificates, agreements, and notices.
/// </summary>
public class DocumentTemplate : BaseEntity
{
    /// <summary>
    /// Unique template identifier code (e.g. WSP-APPROVAL-STD, TRADE-CERT-STD, ETQA-ACCRED-STD).
    /// </summary>
    public string TemplateCode { get; set; } = string.Empty;

    /// <summary>
    /// Human-readable title of the document template.
    /// </summary>
    public string TemplateTitle { get; set; } = string.Empty;

    /// <summary>
    /// Document category (e.g. MandatoryGrant, DiscretionaryGrant, TradeTest, EtqaAccreditation, LearnerContract, FinanceAudit).
    /// </summary>
    public string DocumentCategory { get; set; } = "General";

    /// <summary>
    /// Specific document type classification code (e.g. WspApprovalLetter, WspRejectionLetter, TradeTestCertificate, StatementOfResults, AccreditationCertificate).
    /// </summary>
    public string DocumentTypeCode { get; set; } = string.Empty;

    /// <summary>
    /// Financial/statutory scheme year this template is active for (e.g. 2026).
    /// </summary>
    public int FinancialYear { get; set; }

    /// <summary>
    /// Target entity legal classification filter (e.g. All, Employer, Provider, Learner, Assessor).
    /// </summary>
    public string TargetEntityType { get; set; } = "All";

    /// <summary>
    /// Semantic policy version number (e.g. 1.0.0, 2.1.0).
    /// </summary>
    public string VersionNumber { get; set; } = "1.0.0";

    /// <summary>
    /// Governance approval lifecycle status (Draft, UnderReview, Approved, Sunset).
    /// </summary>
    public string ApprovalStatus { get; set; } = "Draft";

    /// <summary>
    /// Effective starting date for this template version.
    /// </summary>
    public DateTime EffectiveFrom { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Optional expiration / sunset date for this template version.
    /// </summary>
    public DateTime? EffectiveTo { get; set; }

    /// <summary>
    /// Whether this template is active for new document generation.
    /// </summary>
    public bool IsActive { get; set; } = true;

    /// <summary>
    /// Optional custom header branding banner URL or resource path.
    /// </summary>
    public string? HeaderBannerUrl { get; set; }

    /// <summary>
    /// Statutory footer disclaimer text.
    /// </summary>
    public string? FooterDisclaimerText { get; set; }

    /// <summary>
    /// Governance/legal officer who approved this template.
    /// </summary>
    public string? ApprovedBy { get; set; }

    /// <summary>
    /// Timestamp when governance approval was executed.
    /// </summary>
    public DateTime? ApprovedAt { get; set; }

    /// <summary>
    /// Ordered sections composing this document template.
    /// </summary>
    public ICollection<DocumentTemplateSection> Sections { get; set; } = new List<DocumentTemplateSection>();

    /// <summary>
    /// Historical execution snapshots produced from this template.
    /// </summary>
    public ICollection<DocumentSnapshot> Snapshots { get; set; } = new List<DocumentSnapshot>();
}

/// <summary>
/// Reusable atomic document clause or statutory boilerplate paragraph.
/// </summary>
public class DocumentClause : BaseEntity
{
    /// <summary>
    /// Unique clause reference code (e.g. CL-WSP-APPROVE-BODY, CL-TRADE-COMPETENT, CL-POPIA-NOTICE).
    /// </summary>
    public string ClauseCode { get; set; } = string.Empty;

    /// <summary>
    /// Human-readable title of the clause.
    /// </summary>
    public string ClauseTitle { get; set; } = string.Empty;

    /// <summary>
    /// Classification category matching document domains (e.g. MandatoryGrant, TradeTest, EtqaAccreditation, Compliance, Signatures).
    /// </summary>
    public string Category { get; set; } = "General";

    /// <summary>
    /// Rich Markdown content containing dynamic evaluation tokens (e.g. {{RecipientName}}, {{CertificateNumber}}, {{IssuedDate}}).
    /// </summary>
    public string ClauseContent { get; set; } = string.Empty;

    /// <summary>
    /// Indicates if this clause is legally mandatory across all template variants.
    /// </summary>
    public bool IsMandatory { get; set; } = true;

    /// <summary>
    /// Whether this clause is active for inclusion.
    /// </summary>
    public bool IsActive { get; set; } = true;

    /// <summary>
    /// Template section associations.
    /// </summary>
    public ICollection<DocumentTemplateSection> TemplateSections { get; set; } = new List<DocumentTemplateSection>();
}

/// <summary>
/// Ordered section mapping a reusable clause to a document template.
/// </summary>
public class DocumentTemplateSection : BaseEntity
{
    /// <summary>
    /// Foreign key referencing the parent DocumentTemplate.
    /// </summary>
    public int DocumentTemplateId { get; set; }

    /// <summary>
    /// Navigational reference to the parent DocumentTemplate.
    /// </summary>
    public DocumentTemplate? DocumentTemplate { get; set; }

    /// <summary>
    /// Foreign key referencing the assigned DocumentClause.
    /// </summary>
    public int DocumentClauseId { get; set; }

    /// <summary>
    /// Navigational reference to the assigned DocumentClause.
    /// </summary>
    public DocumentClause? DocumentClause { get; set; }

    /// <summary>
    /// Custom section number or bullet label (e.g. "1.0", "Clause 4", "Annexure A").
    /// </summary>
    public string SectionNumber { get; set; } = string.Empty;

    /// <summary>
    /// Section heading title override.
    /// </summary>
    public string SectionTitle { get; set; } = string.Empty;

    /// <summary>
    /// Sorting sequence order within the document layout.
    /// </summary>
    public int SequenceOrder { get; set; }

    /// <summary>
    /// Whether this section is mandatory for this template.
    /// </summary>
    public bool IsMandatory { get; set; } = true;

    /// <summary>
    /// Optional conditional inclusion rule expression in JSON format.
    /// </summary>
    public string? ConditionRuleJson { get; set; }
}

/// <summary>
/// Immutable cryptographically frozen snapshot of any issued statutory document, certificate, or letter.
/// </summary>
public class DocumentSnapshot : BaseEntity
{
    /// <summary>
    /// Unique public tracking and verification reference (e.g. DOC-2026-TT-00123, DOC-2026-WSP-98765).
    /// </summary>
    public string DocumentSnapshotNumber { get; set; } = string.Empty;

    /// <summary>
    /// Document type classification code (e.g. TradeTestCertificate, WspApprovalLetter, AccreditationCertificate, RemittanceAdvice).
    /// </summary>
    public string DocumentTypeCode { get; set; } = string.Empty;

    /// <summary>
    /// Optional foreign key referencing the DocumentTemplate used at issuance.
    /// </summary>
    public int? DocumentTemplateId { get; set; }

    /// <summary>
    /// Navigational reference to the DocumentTemplate.
    /// </summary>
    public DocumentTemplate? DocumentTemplate { get; set; }

    /// <summary>
    /// Version number of the template captured at point of issuance.
    /// </summary>
    public string TemplateVersionNumber { get; set; } = "1.0.0";

    /// <summary>
    /// Identifier of the related entity (e.g. LearnerTradeTest.Id, WspSubmission.Id, TrainingProvider.Id, GrantMoa.Id).
    /// </summary>
    public int RelatedEntityId { get; set; }

    /// <summary>
    /// Name of the related entity table/domain (e.g. LearnerTradeTest, WspSubmission, TrainingProvider, GrantMoa, SarsLevyReconAudit).
    /// </summary>
    public string RelatedEntityType { get; set; } = string.Empty;

    /// <summary>
    /// Full legal name of the recipient individual or organisation.
    /// </summary>
    public string RecipientName { get; set; } = string.Empty;

    /// <summary>
    /// Recipient primary identifier (e.g. RSA ID Number, SDL Number, Accreditation Number).
    /// </summary>
    public string RecipientIdentifier { get; set; } = string.Empty;

    /// <summary>
    /// Cryptographic SHA-256 digital fingerprint hash of the assembled document content.
    /// </summary>
    public string RenderedContentHash { get; set; } = string.Empty;

    /// <summary>
    /// Fully assembled and interpolated document text captured at point of issuance.
    /// </summary>
    public string RenderedContent { get; set; } = string.Empty;

    /// <summary>
    /// Embedded Base64 QR code image payload for offline and optical verification.
    /// </summary>
    public string? VerificationQrBase64 { get; set; }

    /// <summary>
    /// Canonical public verification URI (e.g. https://nsdms.merseta.org.za/verify/document/a1b2c3d4...).
    /// </summary>
    public string VerificationUri { get; set; } = string.Empty;

    /// <summary>
    /// Storage location URI of the signed PDF artifact.
    /// </summary>
    public string? PdfStorageUri { get; set; }

    /// <summary>
    /// Official date and time when the document was frozen and issued.
    /// </summary>
    public DateTime IssuedAt { get; set; } = DateTime.UtcNow;

    /// <summary>
    /// Username or system process that authorized and issued the document.
    /// </summary>
    public string IssuedBy { get; set; } = "System";

    /// <summary>
    /// Full name of the designated authorized signatory (e.g. Chief Executive Officer, Senior Manager: ETQA).
    /// </summary>
    public string? SignatoryName { get; set; }

    /// <summary>
    /// Official title of the designated signatory.
    /// </summary>
    public string? SignatoryTitle { get; set; }

    /// <summary>
    /// Date when digital/electronic signature was affixed.
    /// </summary>
    public DateTime? SignatorySignedAt { get; set; }

    /// <summary>
    /// Total count of times this document has been scanned and verified via the public verification portal.
    /// </summary>
    public int VerificationScanCount { get; set; } = 0;

    /// <summary>
    /// Timestamp of the most recent verification lookup.
    /// </summary>
    public DateTime? LastVerifiedAt { get; set; }

    /// <summary>
    /// Whether this issued document has been revoked or superseded (e.g. due to fraudulent trade test or re-adjudication).
    /// </summary>
    public bool IsRevoked { get; set; } = false;

    /// <summary>
    /// Reason description if the document was revoked.
    /// </summary>
    public string? RevocationReason { get; set; }
}
