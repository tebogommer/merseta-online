import { PrismaClient } from "@prisma/client";
import { unstable_cache } from "next/cache";

const prisma = new PrismaClient();

/**
 * Normalized Lookup Service
 * Individual getters for cached, type-safe lookup descriptors.
 */

// REGISTRATION HELPER for unstable_cache
const revalidateTime = 3600;
const defaultTags = ["lookups"];

// BATCH 0 (Core)
export const getGenderTypes = unstable_cache(
  async () => (prisma as any).genderType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-gender"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getEquityTypes = unstable_cache(
  async () => (prisma as any).equityType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-equity"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getNationalityTypes = unstable_cache(
  async () => (prisma as any).nationalityType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-nationality"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getProvinceTypes = unstable_cache(
  async () => (prisma as any).provinceType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-province"],
  { revalidate: revalidateTime, tags: defaultTags }
);

// BATCH 6 - System Metadata & Statistical
export const getAppraisalCategoryCodeTypes = unstable_cache(async () => (prisma as any).appraisalCategoryCodeType?.findMany({ where: { active: true } } ) || [], ['appraisal-category-code-types'], { revalidate: 3600 })
export const getAppraisalChecklistTypes = unstable_cache(async () => (prisma as any).appraisalChecklistType?.findMany({ where: { active: true } } ) || [], ['appraisal-checklist-types'], { revalidate: 3600 })
export const getAppraisalsTypes = unstable_cache(async () => (prisma as any).appraisalsType?.findMany({ where: { active: true } } ) || [], ['appraisals-types'], { revalidate: 3600 })
export const getAuditorMonitorReviewTypes = unstable_cache(async () => (prisma as any).auditorMonitorReviewType?.findMany({ where: { active: true } } ) || [], ['auditor-monitor-review-types'], { revalidate: 3600 })
export const getDesignationStructureStatusTypes = unstable_cache(async () => (prisma as any).designationStructureStatusType?.findMany({ where: { active: true } } ) || [], ['designation-structure-status-types'], { revalidate: 3600 })
export const getEnrolmentStatusTypes = unstable_cache(async () => (prisma as any).enrolmentStatusType?.findMany({ where: { active: true } } ) || [], ['enrolment-status-types'], { revalidate: 3600 })
export const getFinYearQuartersLookUpTypes = unstable_cache(async () => (prisma as any).finYearQuartersLookUpType?.findMany({ where: { active: true } } ) || [], ['fin-year-quarters-lookup-types'], { revalidate: 3600 })
export const getGrantAmountTypes = unstable_cache(async () => (prisma as any).grantAmountType?.findMany({ where: { active: true } } ) || [], ['grant-amount-types'], { revalidate: 3600 })
export const getGrantOfoSelectionTypes = unstable_cache(async () => (prisma as any).grantOfoSelectionType?.findMany({ where: { active: true } } ) || [], ['grant-ofo-selection-types'], { revalidate: 3600 })
export const getInstitutionTypeTypes = unstable_cache(async () => (prisma as any).institutionTypeType?.findMany({ where: { active: true } } ) || [], ['institution-type-types'], { revalidate: 3600 })
export const getLearnerMentorRatioTypes = unstable_cache(async () => (prisma as any).learnerMentorRatioType?.findMany({ where: { active: true } } ) || [], ['learner-mentor-ratio-types'], { revalidate: 3600 })
export const getLearnerMonitoringSurveyTypes = unstable_cache(async () => (prisma as any).learnerMonitoringSurveyType?.findMany({ where: { active: true } } ) || [], ['learner-monitoring-survey-types'], { revalidate: 3600 })
export const getNoHardToFillVacanciesTypes = unstable_cache(async () => (prisma as any).noHardToFillVacanciesType?.findMany({ where: { active: true } } ) || [], ['no-hard-to-fill-vacancies-types'], { revalidate: 3600 })
export const getNonCreditBearingInterventionTitleTypes = unstable_cache(async () => (prisma as any).nonCreditBearingInterventionTitleType?.findMany({ where: { active: true } } ) || [], ['non-credit-bearing-intervention-title-types'], { revalidate: 3600 })
export const getQualificationTasksTypes = unstable_cache(async () => (prisma as any).qualificationTasksType?.findMany({ where: { active: true } } ) || [], ['qualification-tasks-types'], { revalidate: 3600 })
export const getQualificationToolKitTypes = unstable_cache(async () => (prisma as any).qualificationToolKitType?.findMany({ where: { active: true } } ) || [], ['qualification-tool-kit-types'], { revalidate: 3600 })
export const getQualificationToolListTypes = unstable_cache(async () => (prisma as any).qualificationToolListType?.findMany({ where: { active: true } } ) || [], ['qualification-tool-list-types'], { revalidate: 3600 })
export const getRatioTypes = unstable_cache(async () => (prisma as any).ratioType?.findMany({ where: { active: true } } ) || [], ['ratio-types'], { revalidate: 3600 })
export const getRefEntityTypes = unstable_cache(async () => (prisma as any).refEntityType?.findMany({ where: { active: true } } ) || [], ['ref-entity-types'], { revalidate: 3600 })
export const getReportGenerationPropertiesTypes = unstable_cache(async () => (prisma as any).reportGenerationPropertiesType?.findMany({ where: { active: true } } ) || [], ['report-generation-properties-types'], { revalidate: 3600 })
export const getSTATSSAAreaCodeTypes = unstable_cache(async () => (prisma as any).sTATSSAAreaCodeType?.findMany({ where: { active: true } } ) || [], ['statssa-area-code-types'], { revalidate: 3600 })
export const getSkillsIdentificationTypes = unstable_cache(async () => (prisma as any).skillsIdentificationType?.findMany({ where: { active: true } } ) || [], ['skills-identification-types'], { revalidate: 3600 })
export const getSkillsProgramTypes = unstable_cache(async () => (prisma as any).skillsProgramType?.findMany({ where: { active: true } } ) || [], ['skills-program-types'], { revalidate: 3600 })
export const getSkillsSetTypes = unstable_cache(async () => (prisma as any).skillsSetType?.findMany({ where: { active: true } } ) || [], ['skills-set-types'], { revalidate: 3600 })
export const getSocioeconomicStatusCodeTypes = unstable_cache(async () => (prisma as any).socioeconomicStatusCodeType?.findMany({ where: { active: true } } ) || [], ['socioeconomic-status-code-types'], { revalidate: 3600 })
export const getStakeholderRelationsTypes = unstable_cache(async () => (prisma as any).stakeholderRelationsType?.findMany({ where: { active: true } } ) || [], ['stakeholder-relations-types'], { revalidate: 3600 })
export const getStatusesTypes = unstable_cache(async () => (prisma as any).statusesType?.findMany({ where: { active: true } } ) || [], ['statuses-types'], { revalidate: 3600 })
export const getUnitStandardTypes = unstable_cache(async () => (prisma as any).unitStandardType?.findMany({ where: { active: true } } ) || [], ['unit-standard-types'], { revalidate: 3600 })
export const getUserResponsibilityTypes = unstable_cache(async () => (prisma as any).userResponsibilityType?.findMany({ where: { active: true } } ) || [], ['user-responsibility-types'], { revalidate: 3600 })
export const getWspHistoricDataTypes = unstable_cache(async () => (prisma as any).wspHistoricDataType?.findMany({ where: { active: true } } ) || [], ['wsp-historic-data-types'], { revalidate: 3600 })
export const getYoutubeVideoTypes = unstable_cache(async () => (prisma as any).youtubeVideoType?.findMany({ where: { active: true } } ) || [], ['youtube-video-types'], { revalidate: 3600 })
export const getArplTypes = unstable_cache(async () => (prisma as any).arplType?.findMany({ where: { active: true } } ) || [], ['arpl-types'], { revalidate: 3600 })
export const getGlossaryTypes = unstable_cache(async () => (prisma as any).glossaryType?.findMany({ where: { active: true } } ) || [], ['glossary-types'], { revalidate: 3600 })

// LEGACY BRIDGE (For backwards compatibility during migration)
export const getAbetBandTypes = unstable_cache(
  async () => (prisma as any).abetBandType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-abet-band"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getAlternativeIdTypes = unstable_cache(
  async () => (prisma as any).alternativeIdType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-alternative-id"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getApplicantTypes = unstable_cache(
  async () => (prisma as any).applicantType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-applicant"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getBankTypes = unstable_cache(
  async () => (prisma as any).bankType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-bank"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getChamberTypes = unstable_cache(
  async () => (prisma as any).chamberType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-chamber"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getCitizenResidentStatusTypes = unstable_cache(
  async () => (prisma as any).citizenResidentStatusType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-citizen-resident-status"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getCountryTypes = unstable_cache(
  async () => (prisma as any).countryType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-country"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getDepartmentTypes = unstable_cache(
  async () => (prisma as any).departmentType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-department"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getDisabilityRatingTypes = unstable_cache(
  async () => (prisma as any).disabilityRatingType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-disability-rating"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getDisabilityStatusTypes = unstable_cache(
  async () => (prisma as any).disabilityStatusType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-disability-status"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getEconomicStatusTypes = unstable_cache(
  async () => (prisma as any).economicStatusType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-economic-status"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getEmploymentTypeTypes = unstable_cache(
  async () => (prisma as any).employmentTypeType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-employment-type"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getHighestQualificationRequiredTypes = unstable_cache(
  async () => (prisma as any).highestQualificationRequiredType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-highest-qual"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getLanguageTypes = unstable_cache(
  async () => (prisma as any).languageType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-language"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getMaritalStatusTypes = unstable_cache(
  async () => (prisma as any).maritalStatusType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-marital-status"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getSetaTypes = unstable_cache(
  async () => (prisma as any).setaType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-seta"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getSicCodeTypes = unstable_cache(
  async () => (prisma as any).sICCodeType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-sic-code"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getSizeOfCompanyTypes = unstable_cache(
  async () => (prisma as any).sizeOfCompanyType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-company-size"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getSocioeconomicStatusTypes = unstable_cache(
  async () => (prisma as any).socioeconomicStatusType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-socio-status"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getTitleTypes = unstable_cache(
  async () => (prisma as any).titleType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-title"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getTownTypes = unstable_cache(
  async () => (prisma as any).townType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-town"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getUrbanRuralTypes = unstable_cache(
  async () => (prisma as any).urbanRuralType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-urban-rural"],
  { revalidate: revalidateTime, tags: defaultTags }
);

// BATCH 2 (ETQA & Training Core)
export const getAccreditationTypes = unstable_cache(
  async () => (prisma as any).accreditationType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-accreditation"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getAetProgrammeLevelTypes = unstable_cache(
  async () => (prisma as any).aetProgrammeLevelType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-aet-programme-level"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getAqpTypes = unstable_cache(
  async () => (prisma as any).aqpType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-aqp"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getEtqaTypes = unstable_cache(
  async () => (prisma as any).etqaType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-etqa"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getInterventionLevelTypes = unstable_cache(
  async () => (prisma as any).interventionLevelType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-intervention-level"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getInterventionTitleTypes = unstable_cache(
  async () => (prisma as any).interventionTitleType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-intervention-title"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getLearnerAchievementStatusTypes = unstable_cache(
  async () => (prisma as any).learnerAchievementStatusType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-learner-achievement-status"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getLearnerAchievementTypeTypes = unstable_cache(
  async () => (prisma as any).learnerAchievementTypeType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-learner-achievement-type"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getNQFAlignmentTypes = unstable_cache(
  async () => (prisma as any).nQFAlignmentType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-nqf-alignment"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getNqfLevelsTypes = unstable_cache(
  async () => (prisma as any).nqfLevelsType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-nqf-levels"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getProviderAccredStatusTypes = unstable_cache(
  async () => (prisma as any).providerAccredStatusType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-provider-accred-status"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getProviderStatusTypes = unstable_cache(
  async () => (prisma as any).providerStatusType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-provider-status"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getRejectReasonsTypes = unstable_cache(
  async () => (prisma as any).rejectReasonsType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-reject-reasons"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getWithdrawReasonsTypes = unstable_cache(
  async () => (prisma as any).withdrawReasonsType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-withdraw-reasons"],
  { revalidate: revalidateTime, tags: defaultTags }
);

// BATCH 3 (Grants & Workforce)
export const getDGProjectTypes = unstable_cache(
  async () => (prisma as any).dGProjectType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-dg-project"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getDGYearTypes = unstable_cache(
  async () => (prisma as any).dGYearType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-dg-year"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getDesignationTypes = unstable_cache(
  async () => (prisma as any).designationType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-designation"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getFinancialYearsTypes = unstable_cache(
  async () => (prisma as any).financialYearsType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-financial-years"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getFundingTypes = unstable_cache(
  async () => (prisma as any).fundingType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-funding"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getOccupationCategoryTypes = unstable_cache(
  async () => (prisma as any).occupationCategoryType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-occupation-category"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getOfoTypes = unstable_cache(
  async () => (prisma as any).ofoType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-ofo"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getOrganisedLabourUnionTypes = unstable_cache(
  async () => (prisma as any).organisedLabourUnionType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-labour-union"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getRelationshipToCompanyTypes = unstable_cache(
  async () => (prisma as any).relationshipToCompanyType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-rel-company"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getScarcityReasonTypes = unstable_cache(
  async () => (prisma as any).scarcityReasonType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-scarcity-reason"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getScopeOfResponsibilityTypes = unstable_cache(
  async () => (prisma as any).scopeOfResponsibilityType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-scope-resp"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getVacancyReasonsTypes = unstable_cache(
  async () => (prisma as any).vacancyReasonsType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-vacancy-reason"],
  { revalidate: revalidateTime, tags: defaultTags }
);

// BATCH 4 (WSP & ATR)
export const getAddressChangeTypes = unstable_cache(
  async () => (prisma as any).addressChangeType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-address-change"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getApprovalStatusTypes = unstable_cache(
  async () => (prisma as any).approvalStatusType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-approval-status"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getAssessorModTypes = unstable_cache(
  async () => (prisma as any).assessorModType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-assessor-mod"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getExtensionRequestReasonTypes = unstable_cache(
  async () => (prisma as any).extensionRequestReasonType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-ext-reason"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getMailTypes = unstable_cache(
  async () => (prisma as any).mailType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-mail"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getMeetingTypeTypes = unstable_cache(
  async () => (prisma as any).meetingTypeType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-meeting"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getNoteTypes = unstable_cache(
  async () => (prisma as any).noteType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-note"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getPlacementTypeTypes = unstable_cache(
  async () => (prisma as any).placementTypeType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-placement"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getPreparationTypeTypes = unstable_cache(
  async () => (prisma as any).preparationTypeType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-preparation"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getQuestionTypeTypes = unstable_cache(
  async () => (prisma as any).questionTypeType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-question"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getRecommendationTypeTypes = unstable_cache(
  async () => (prisma as any).recommendationTypeType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-recommendation"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getSessionTypeTypes = unstable_cache(
  async () => (prisma as any).sessionTypeType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-session"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getSetupTypeTypes = unstable_cache(
  async () => (prisma as any).setupTypeType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-setup"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getSkillTypeTypes = unstable_cache(
  async () => (prisma as any).skillTypeType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-skill-type"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getStatusTypes = unstable_cache(
  async () => (prisma as any).statusType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-status"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getTrainingProviderTypeTypes = unstable_cache(
  async () => (prisma as any).trainingProviderTypeType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-tp-type"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getWorkPlaceApprovalTypes = unstable_cache(
  async () => (prisma as any).workPlaceApprovalType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-wpa"],
  { revalidate: revalidateTime, tags: defaultTags }
);

// BATCH 5 (Academic & Workflow Metadata)
export const getAllocationChangeTypes = unstable_cache(
  async () => (prisma as any).allocationChangeType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-allocation-change"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getAppraisalCategoriesTypes = unstable_cache(
  async () => (prisma as any).appraisalCategoriesType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-appraisal-categories"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getAreaForImprovementTypes = unstable_cache(
  async () => (prisma as any).areaForImprovementType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-area-improvement"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getCommunicatingRatingTypes = unstable_cache(
  async () => (prisma as any).communicatingRatingType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-comm-rating"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getCompanyUserPositionTypes = unstable_cache(
  async () => (prisma as any).companyUserPositionType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-user-position"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getDateChangeReasonsTypes = unstable_cache(
  async () => (prisma as any).dateChangeReasonsType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-date-change-reason"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getDesignatedTradeTypeTypes = unstable_cache(
  async () => (prisma as any).designatedTradeTypeType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-trade-type"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getDeviationReasonTypes = unstable_cache(
  async () => (prisma as any).deviationReasonType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-deviation-reason"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getEmployerApprovalStatusTypes = unstable_cache(
  async () => (prisma as any).employerApprovalStatusType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-emp-approval"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getEnrolmentStatusReasonTypes = unstable_cache(
  async () => (prisma as any).enrolmentStatusReasonType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-enrol-status-reason"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getEnrolmentTypeTypes = unstable_cache(
  async () => (prisma as any).enrolmentTypeType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-enrol-type"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getHearingRatingTypes = unstable_cache(
  async () => (prisma as any).hearingRatingType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-hearing-rating"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getHonoursClassificationTypes = unstable_cache(
  async () => (prisma as any).honoursClassificationType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-honours-class"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getMailTemplatesTypes = unstable_cache(
  async () => (prisma as any).mailTemplatesType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-mail-templates"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getMailingListTypes = unstable_cache(
  async () => (prisma as any).mailingListType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-mailing-list"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getMeetingAgendaTypes = unstable_cache(
  async () => (prisma as any).meetingAgendaType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-meeting-agenda"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getModulesCategoryTypes = unstable_cache(
  async () => (prisma as any).modulesCategoryType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-modules-category"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getNextRefreshYearTypes = unstable_cache(
  async () => (prisma as any).nextRefreshYearType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-refresh-year"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getNonNqfIntervStatusTypes = unstable_cache(
  async () => (prisma as any).nonNqfIntervStatusType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-non-nqf-status"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getNqfDesigStatusTypes = unstable_cache(
  async () => (prisma as any).nqfDesigStatusType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-nqf-desig"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getPopiActStatusTypes = unstable_cache(
  async () => (prisma as any).popiActStatusType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-popi-status"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getPrioritisationScaleTypes = unstable_cache(
  async () => (prisma as any).prioritisationScaleType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-prio-scale"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getPurposeOfSiteVisitTypes = unstable_cache(
  async () => (prisma as any).purposeOfSiteVisitType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-visit-purpose"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getQualificationEntryRequirementTypes = unstable_cache(
  async () => (prisma as any).qualificationEntryRequirementType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-qual-entry"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getRememberingRatingTypes = unstable_cache(
  async () => (prisma as any).rememberingRatingType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-remember-rating"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getSDFTypeTypes = unstable_cache(
  async () => (prisma as any).sDFTypeType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-sdf-type"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getSdpTypeTypes = unstable_cache(
  async () => (prisma as any).sdpTypeType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-sdp-type"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getSeeingRatingTypes = unstable_cache(
  async () => (prisma as any).seeingRatingType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-seeing-rating"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getSelfcaringRatingTypes = unstable_cache(
  async () => (prisma as any).selfcaringRatingType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-selfcare-rating"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getSkillsGapTrackLookUpTypes = unstable_cache(
  async () => (prisma as any).skillsGapTrackLookUpType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-skill-gap"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getStatementOfResultsTypes = unstable_cache(
  async () => (prisma as any).statementOfResultsType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-sor-type"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getTaskRejectionContentsTypes = unstable_cache(
  async () => (prisma as any).taskRejectionContentsType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-task-reject"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getToolCategoryTypes = unstable_cache(
  async () => (prisma as any).toolCategoryType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-tool-category"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getTradeTestResultTypes = unstable_cache(
  async () => (prisma as any).tradeTestResultType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-trade-result"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getTrainingDeliveryMethodTypes = unstable_cache(
  async () => (prisma as any).trainingDeliveryMethodType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-delivery-method"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getTrainingImpactRatingTypes = unstable_cache(
  async () => (prisma as any).trainingImpactRatingType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-impact-rating"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getUnionMembershipTypes = unstable_cache(
  async () => (prisma as any).unionMembershipType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-union-membership"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getValidityTypes = unstable_cache(
  async () => (prisma as any).validityType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-validity"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getWalkingRatingTypes = unstable_cache(
  async () => (prisma as any).walkingRatingType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-walking-rating"],
  { revalidate: revalidateTime, tags: defaultTags }
);

// PROVIDER MODELS
export const getProviderTypeTypes = unstable_cache(
  async () => (prisma as any).providerTypeType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-provider-type"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getProviderClassTypes = unstable_cache(
  async () => (prisma as any).providerClassType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-provider-class"],
  { revalidate: revalidateTime, tags: defaultTags }
);

export const getAccreditationStatusTypes = unstable_cache(
  async () => (prisma as any).accreditationStatusType?.findMany({ where: { active: true }, orderBy: { name: "asc" } } ) || [],
  ["lookup-accreditation-status"],
  { revalidate: revalidateTime, tags: defaultTags }
);

/**
 * Migration Helper: Retrieves lookups of a specific "type" by hitting the correct table.
 * @deprecated Use specific getters instead.
 */
export async function getLookupsByType(type: string) {
  switch (type.toUpperCase()) {
    case "GENDER": return await getGenderTypes();
    case "EQUITY": return await getEquityTypes();
    case "NATIONALITY": return await getNationalityTypes();
    case "PROVINCE": return await getProvinceTypes();
    case "PROVIDER_TYPE": return await getProviderTypeTypes();
    case "PROVIDER_CLASS": return await getProviderClassTypes();
    case "TITLE": return await getTitleTypes();
    case "MARITAL_STATUS": return await getMaritalStatusTypes();
    case "LANGUAGE": return await getLanguageTypes();
    case "TOWN": return await getTownTypes();
    case "SETA": return await getSetaTypes();
    case "SIC_CODE": return await getSicCodeTypes();
    case "BANK": return await getBankTypes();
    case "FUNDING": return await getFundingTypes();
    case "OFO": return await getOfoTypes();
    default: return [];
  }
}

/**
 * Helper to fetch common system lookups in bulk.
 */
export async function getCommonLookups() {
  const [genders, nationalities, provinces, equities, titles] = await Promise.all([
    getGenderTypes(),
    getNationalityTypes(),
    getProvinceTypes(),
    getEquityTypes(),
    getTitleTypes(),
  ]);

  return { genders, nationalities, provinces, equities, titles };
}
