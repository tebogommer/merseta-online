using Nsdms.Domain.Common;
using System.ComponentModel.DataAnnotations.Schema;

namespace Nsdms.Domain.Lookups;

// Core & Demographics Lookups
[Table("GenderType", Schema = "lookup")]
public class GenderType : BaseLookupType { }

[Table("EquityType", Schema = "lookup")]
public class EquityType : BaseLookupType { }

[Table("CitizenStatusType", Schema = "lookup")]
public class CitizenStatusType : BaseLookupType { }

[Table("NationalityType", Schema = "lookup")]
public class NationalityType : BaseLookupType { }

[Table("HomeLanguageType", Schema = "lookup")]
public class HomeLanguageType : BaseLookupType { }

[Table("ProvinceType", Schema = "lookup")]
public class ProvinceType : BaseLookupType { }

[Table("DisabilityType", Schema = "lookup")]
public class DisabilityType : BaseLookupType { }

// Organisation & Sector Lookups
[Table("CategoryType", Schema = "lookup")]
public class CategoryType : BaseLookupType { }

[Table("OrganisationType", Schema = "lookup")]
public class OrganisationType : BaseLookupType { }

[Table("CompanySizeType", Schema = "lookup")]
public class CompanySizeType : BaseLookupType { }

[Table("SectorType", Schema = "lookup")]
public class SectorType : BaseLookupType { }

[Table("ChamberType", Schema = "lookup")]
public class ChamberType : BaseLookupType { }

[Table("SicCodeType", Schema = "lookup")]
public class SicCodeType : BaseLookupType { }

[Table("StatusType", Schema = "lookup")]
public class StatusType : BaseLookupType { }

// Learning & ETQA Lookups
[Table("LearningProgrammeType", Schema = "lookup")]
public class LearningProgrammeType : BaseLookupType { }

[Table("EnrolmentType", Schema = "lookup")]
public class EnrolmentType : BaseLookupType { }

[Table("EnrolmentStatusType", Schema = "lookup")]
public class EnrolmentStatusType : BaseLookupType { }

[Table("ProviderType", Schema = "lookup")]
public class ProviderType : BaseLookupType { }

[Table("ProviderStatusType", Schema = "lookup")]
public class ProviderStatusType : BaseLookupType { }

[Table("LearnerEvidenceType", Schema = "lookup")]
public class LearnerEvidenceType : BaseLookupType { }

// Grants & Finance Lookups
[Table("GrantTypeType", Schema = "lookup")]
public class GrantTypeType : BaseLookupType { }

[Table("InterventionType", Schema = "lookup")]
public class InterventionType : BaseLookupType { }

[Table("OfoCodeType", Schema = "lookup")]
public class OfoCodeType : BaseLookupType { }

// Visits & Governance Lookups
[Table("VisitTypeType", Schema = "lookup")]
public class VisitTypeType : BaseLookupType { }

[Table("SiteVisitApprovalStatusType", Schema = "lookup")]
public class SiteVisitApprovalStatusType : BaseLookupType { }

[Table("EmployerApprovalStatusType", Schema = "lookup")]
public class EmployerApprovalStatusType : BaseLookupType { }
