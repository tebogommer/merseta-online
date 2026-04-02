/**
 * Business-critical enums for the MerSETA NSDMS application.
 * These are "Code-First" enums that dictate application logic and workflow transitions.
 * Unlike Taxonomy Lookups (Prisma), these should NOT be modified by users via the UI.
 */

export enum ApprovalStatus {
  APPROVED = "Approved",
  REJECTED = "Rejected",
  WAITING_FOR_MANAGER = "Pending Manager Approval",
  PENDING_APPROVAL = "Pending Approval",
  PENDING_SIGN_OFF = "Pending Sign Off",
  COMPLETED = "Completed",
  PENDING_ACCEPT_CODE_OF_CONDUCT = "Pending accept code of conduct",
  AWAITING_DHET = "Awaiting DHET",
  PENDING_FINAL_APPROVAL = "Pending Final Approval",
  WITHDRAWN = "Withdrawn",
  NA = "N/A",
  RECOMMENDED = "Recommended",
  APPEALED = "Appealed",
  PENDING_COMMITTEE_APPROVAL = "Pending Committee Approval",
  APPROVED_BY_ETQA = "Approved By ETQA Review Committee",
  REJECTED_BY_ETQA = "Rejected By ETQA Review Commitee",
  HIGHER_ALLOCATION_REQUEST = "Requested Higher Allocation",
  ACCEPTED_MOA = "Accepted MOA",
  REQUESTED_CHANGE = "Requested Change",
  REJECTED_BY_MANCO = "Rejected By MANCO Review",
  REJECTED_BY_LEARNER_REVIEW = "Rejected By Learner Review Committee",
  QUALIFICATION_OBTAINED = "Qualification Obtained",
  DEACTIVATED = "Deactivated",
  TERMINATE_PROJECT_WAITING_FOR_MANAGER = "Project Terminated Manager Approval",
  SUSPEND_PROJECT = "Suspend Project",
  PROJECT_TERMINATED = "Project Terminated",
  PRE_APPROVED = "Pending Review Approval",
  UPHOLD = "Uphold",
  PENDING_RESUBMISSION = "Pending Resubmission",
  AWAITING_NAMB = "Awaiting NAMB",
  PENDING_WITHDRAWAL = "Pending Withdrawal",
  PENDING_INVESTIGATION = "Pending Investigation",
  PENDING_CHANGE_APPROVAL = "Pending Change Approval",
  NOT_COMPETENT = "Not Competent",
  DE_ACCREDITED = "De-Accredited",
  DE_REGISTERED = "De-Registered",
  PENDING_COMPLIANCE_ISSUES = "Pending Non-Compliance Issues",
  EXPIRED = "Expired",
  DUPLICATE = "Duplicate",
}

export enum CompanyStatus {
  PENDING = "Awaiting merSETA Approval",
  ACTIVE = "Active",
  IN_ACTIVE = "In-Active",
  REJECTED = "Rejected",
  APPROVED = "Approved",
  PENDING_CHANGE_APPROVAL = "Pending Change Approval",
  NON_MERSETA_COMPANY = "Non-merSETA Company",
  DE_REGISTERED = "Deregistered",
  PENDING_REPLACEMENT = "Pending Replacement",
}

export enum AccreditationApplicationType {
  ACCREDITATION_APPROVAL = "Primary Accreditation (accreditation for merSETA scope qualification/s)",
  REACCREDITATION_REAPPROVAL = "Re-Accreditation or Re-Approval",
  EXTENSION_OF_SCOPE = "Extension of Accreditation (merSETA Providers)",
  NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL = "Learning Programme Approval (non-merSETA primary provider)",
  QCTO_SDP = "QCTO Skills Development Provider",
  QCTO_TRADE_TEST_CENTRE = "QCTO Trade Test Centre",
  NON_MERSETA_FOCUSED_PROVIDER = "Non-merSETA Scope Provider",
  TRAINING_AND_ASSESSMENT_SITE = "Training and Assessment OR Assessment Only Site",
  EOS_RA_LP = "Extension of Scope, Re-accreditation/Re-approval or Learning Programme Approval",
}

export enum AllocationChangeType {
  REQUEST_CHANGE = "Request Change",
  REQUEST_HIGHER_ALLOCATION = "Request Higher Allocation",
}

// Map the display names for UI consumption
export const ApprovalStatusLabels: Record<ApprovalStatus, string> = Object.fromEntries(
  Object.entries(ApprovalStatus).map(([key, value]) => [value, value])
) as Record<ApprovalStatus, string>;

export const CompanyStatusLabels: Record<CompanyStatus, string> = Object.fromEntries(
  Object.entries(CompanyStatus).map(([key, value]) => [value, value])
) as Record<CompanyStatus, string>;

export const AccreditationApplicationTypeLabels: Record<AccreditationApplicationType, string> = Object.fromEntries(
  Object.entries(AccreditationApplicationType).map(([key, value]) => [value, value])
) as Record<AccreditationApplicationType, string>;

export const AllocationChangeTypeLabels: Record<AllocationChangeType, string> = Object.fromEntries(
  Object.entries(AllocationChangeType).map(([key, value]) => [value, value])
) as Record<AllocationChangeType, string>;

