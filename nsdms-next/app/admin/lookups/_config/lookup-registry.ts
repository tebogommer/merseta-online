export interface LookupDef {
  model: string;
  route: string;
  name: string;
  category: string;
}

export const LOOKUP_MODELS: LookupDef[] = [
  {
    "model": "CategoryType",
    "route": "category-type",
    "name": "Category Type",
    "category": "Uncategorized"
  },
  {
    "model": "InterventionType",
    "route": "intervention-type",
    "name": "Intervention Type",
    "category": "Uncategorized"
  },
  {
    "model": "QualificationType",
    "route": "qualification-type",
    "name": "Qualification Type",
    "category": "Uncategorized"
  },
  {
    "model": "GenderType",
    "route": "gender-type",
    "name": "Gender Type",
    "category": "Demographics"
  },
  {
    "model": "EquityType",
    "route": "equity-type",
    "name": "Equity Type",
    "category": "Demographics"
  },
  {
    "model": "NationalityType",
    "route": "nationality-type",
    "name": "Nationality Type",
    "category": "Demographics"
  },
  {
    "model": "ProvinceType",
    "route": "province-type",
    "name": "Province Type",
    "category": "Demographics"
  },
  {
    "model": "ProviderTypeType",
    "route": "provider-type-type",
    "name": "Provider Type Type",
    "category": "Institutional"
  },
  {
    "model": "ProviderClassType",
    "route": "provider-class-type",
    "name": "Provider Class Type",
    "category": "Institutional"
  },
  {
    "model": "AccreditationStatusType",
    "route": "accreditation-status-type",
    "name": "Accreditation Status Type",
    "category": "Institutional"
  },
  {
    "model": "AbetBandType",
    "route": "abet-band-type",
    "name": "Abet Band Type",
    "category": "Uncategorized"
  },
  {
    "model": "AlternativeIdType",
    "route": "alternative-id-type",
    "name": "Alternative Id Type",
    "category": "Uncategorized"
  },
  {
    "model": "ApplicantType",
    "route": "applicant-type",
    "name": "Applicant Type",
    "category": "Uncategorized"
  },
  {
    "model": "BankType",
    "route": "bank-type",
    "name": "Bank Type",
    "category": "Financial"
  },
  {
    "model": "ChamberType",
    "route": "chamber-type",
    "name": "Chamber Type",
    "category": "Institutional"
  },
  {
    "model": "CitizenResidentStatusType",
    "route": "citizen-resident-status-type",
    "name": "Citizen Resident Status Type",
    "category": "Demographics"
  },
  {
    "model": "CountryType",
    "route": "country-type",
    "name": "Country Type",
    "category": "Uncategorized"
  },
  {
    "model": "DepartmentType",
    "route": "department-type",
    "name": "Department Type",
    "category": "Institutional"
  },
  {
    "model": "DisabilityRatingType",
    "route": "disability-rating-type",
    "name": "Disability Rating Type",
    "category": "Uncategorized"
  },
  {
    "model": "DisabilityStatusType",
    "route": "disability-status-type",
    "name": "Disability Status Type",
    "category": "Demographics"
  },
  {
    "model": "EconomicStatusType",
    "route": "economic-status-type",
    "name": "Economic Status Type",
    "category": "Uncategorized"
  },
  {
    "model": "EmploymentTypeType",
    "route": "employment-type-type",
    "name": "Employment Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "HighestQualificationRequiredType",
    "route": "highest-qualification-required-type",
    "name": "Highest Qualification Required Type",
    "category": "Uncategorized"
  },
  {
    "model": "LanguageType",
    "route": "language-type",
    "name": "Language Type",
    "category": "Uncategorized"
  },
  {
    "model": "MaritalStatusType",
    "route": "marital-status-type",
    "name": "Marital Status Type",
    "category": "Uncategorized"
  },
  {
    "model": "SetaType",
    "route": "seta-type",
    "name": "Seta Type",
    "category": "Uncategorized"
  },
  {
    "model": "SICCodeType",
    "route": "siccode-type",
    "name": "SICCode Type",
    "category": "Uncategorized"
  },
  {
    "model": "SizeOfCompanyType",
    "route": "size-of-company-type",
    "name": "Size Of Company Type",
    "category": "Uncategorized"
  },
  {
    "model": "SocioeconomicStatusType",
    "route": "socioeconomic-status-type",
    "name": "Socioeconomic Status Type",
    "category": "Uncategorized"
  },
  {
    "model": "TitleType",
    "route": "title-type",
    "name": "Title Type",
    "category": "Uncategorized"
  },
  {
    "model": "TownType",
    "route": "town-type",
    "name": "Town Type",
    "category": "Uncategorized"
  },
  {
    "model": "UrbanRuralType",
    "route": "urban-rural-type",
    "name": "Urban Rural Type",
    "category": "Uncategorized"
  },
  {
    "model": "AccreditationType",
    "route": "accreditation-type",
    "name": "Accreditation Type",
    "category": "Uncategorized"
  },
  {
    "model": "AetProgrammeLevelType",
    "route": "aet-programme-level-type",
    "name": "Aet Programme Level Type",
    "category": "Uncategorized"
  },
  {
    "model": "AqpType",
    "route": "aqp-type",
    "name": "Aqp Type",
    "category": "Uncategorized"
  },
  {
    "model": "EtqaType",
    "route": "etqa-type",
    "name": "Etqa Type",
    "category": "Uncategorized"
  },
  {
    "model": "InterventionLevelType",
    "route": "intervention-level-type",
    "name": "Intervention Level Type",
    "category": "Uncategorized"
  },
  {
    "model": "InterventionTitleType",
    "route": "intervention-title-type",
    "name": "Intervention Title Type",
    "category": "Uncategorized"
  },
  {
    "model": "LearnerAchievementStatusType",
    "route": "learner-achievement-status-type",
    "name": "Learner Achievement Status Type",
    "category": "Uncategorized"
  },
  {
    "model": "LearnerAchievementTypeType",
    "route": "learner-achievement-type-type",
    "name": "Learner Achievement Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "NQFAlignmentType",
    "route": "nqfalignment-type",
    "name": "NQFAlignment Type",
    "category": "Uncategorized"
  },
  {
    "model": "NqfLevelsType",
    "route": "nqf-levels-type",
    "name": "Nqf Levels Type",
    "category": "Uncategorized"
  },
  {
    "model": "ProviderAccredStatusType",
    "route": "provider-accred-status-type",
    "name": "Provider Accred Status Type",
    "category": "Uncategorized"
  },
  {
    "model": "ProviderStatusType",
    "route": "provider-status-type",
    "name": "Provider Status Type",
    "category": "Uncategorized"
  },
  {
    "model": "RejectReasonsType",
    "route": "reject-reasons-type",
    "name": "Reject Reasons Type",
    "category": "Uncategorized"
  },
  {
    "model": "WithdrawReasonsType",
    "route": "withdraw-reasons-type",
    "name": "Withdraw Reasons Type",
    "category": "Uncategorized"
  },
  {
    "model": "DGProjectType",
    "route": "dgproject-type",
    "name": "DGProject Type",
    "category": "Uncategorized"
  },
  {
    "model": "DGYearType",
    "route": "dgyear-type",
    "name": "DGYear Type",
    "category": "Uncategorized"
  },
  {
    "model": "DGYearLearningProgramsType",
    "route": "dgyear-learning-programs-type",
    "name": "DGYear Learning Programs Type",
    "category": "Uncategorized"
  },
  {
    "model": "DesignationType",
    "route": "designation-type",
    "name": "Designation Type",
    "category": "Uncategorized"
  },
  {
    "model": "DesignationStructureStatusType",
    "route": "designation-structure-status-type",
    "name": "Designation Structure Status Type",
    "category": "Uncategorized"
  },
  {
    "model": "FinancialYearsType",
    "route": "financial-years-type",
    "name": "Financial Years Type",
    "category": "Uncategorized"
  },
  {
    "model": "FundingType",
    "route": "funding-type",
    "name": "Funding Type",
    "category": "Uncategorized"
  },
  {
    "model": "GrantAmountType",
    "route": "grant-amount-type",
    "name": "Grant Amount Type",
    "category": "Uncategorized"
  },
  {
    "model": "GrantOfoSelectionType",
    "route": "grant-ofo-selection-type",
    "name": "Grant Ofo Selection Type",
    "category": "Uncategorized"
  },
  {
    "model": "InstitutionType",
    "route": "institution-type",
    "name": "Institution Type",
    "category": "Uncategorized"
  },
  {
    "model": "InstitutionDomainType",
    "route": "institution-domain-type",
    "name": "Institution Domain Type",
    "category": "Uncategorized"
  },
  {
    "model": "MandatoryGrantEvaluationType",
    "route": "mandatory-grant-evaluation-type",
    "name": "Mandatory Grant Evaluation Type",
    "category": "Uncategorized"
  },
  {
    "model": "NoHardToFillVacanciesType",
    "route": "no-hard-to-fill-vacancies-type",
    "name": "No Hard To Fill Vacancies Type",
    "category": "Uncategorized"
  },
  {
    "model": "OccupationCategoryType",
    "route": "occupation-category-type",
    "name": "Occupation Category Type",
    "category": "Uncategorized"
  },
  {
    "model": "OfoType",
    "route": "ofo-type",
    "name": "Ofo Type",
    "category": "Uncategorized"
  },
  {
    "model": "OfoQualificationLinkType",
    "route": "ofo-qualification-link-type",
    "name": "Ofo Qualification Link Type",
    "category": "Uncategorized"
  },
  {
    "model": "OrganisedLabourUnionType",
    "route": "organised-labour-union-type",
    "name": "Organised Labour Union Type",
    "category": "Uncategorized"
  },
  {
    "model": "RelationshipToCompanyType",
    "route": "relationship-to-company-type",
    "name": "Relationship To Company Type",
    "category": "Uncategorized"
  },
  {
    "model": "ScarcityReasonType",
    "route": "scarcity-reason-type",
    "name": "Scarcity Reason Type",
    "category": "Uncategorized"
  },
  {
    "model": "ScopeOfResponsibilityType",
    "route": "scope-of-responsibility-type",
    "name": "Scope Of Responsibility Type",
    "category": "Uncategorized"
  },
  {
    "model": "SkillsIdentificationType",
    "route": "skills-identification-type",
    "name": "Skills Identification Type",
    "category": "Uncategorized"
  },
  {
    "model": "StakeholderRelationsType",
    "route": "stakeholder-relations-type",
    "name": "Stakeholder Relations Type",
    "category": "Uncategorized"
  },
  {
    "model": "SubdomainType",
    "route": "subdomain-type",
    "name": "Subdomain Type",
    "category": "Uncategorized"
  },
  {
    "model": "SubfieldType",
    "route": "subfield-type",
    "name": "Subfield Type",
    "category": "Uncategorized"
  },
  {
    "model": "UserResponsibilityType",
    "route": "user-responsibility-type",
    "name": "User Responsibility Type",
    "category": "Uncategorized"
  },
  {
    "model": "VacancyReasonsType",
    "route": "vacancy-reasons-type",
    "name": "Vacancy Reasons Type",
    "category": "Uncategorized"
  },
  {
    "model": "WspHistoricDataType",
    "route": "wsp-historic-data-type",
    "name": "Wsp Historic Data Type",
    "category": "Uncategorized"
  },
  {
    "model": "AddressChangeType",
    "route": "address-change-type",
    "name": "Address Change Type",
    "category": "Uncategorized"
  },
  {
    "model": "ApprovalStatusType",
    "route": "approval-status-type",
    "name": "Approval Status Type",
    "category": "Uncategorized"
  },
  {
    "model": "AssessorModType",
    "route": "assessor-mod-type",
    "name": "Assessor Mod Type",
    "category": "Uncategorized"
  },
  {
    "model": "ExtensionRequestReasonType",
    "route": "extension-request-reason-type",
    "name": "Extension Request Reason Type",
    "category": "Uncategorized"
  },
  {
    "model": "MailType",
    "route": "mail-type",
    "name": "Mail Type",
    "category": "Uncategorized"
  },
  {
    "model": "MeetingTypeType",
    "route": "meeting-type-type",
    "name": "Meeting Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "NoteType",
    "route": "note-type",
    "name": "Note Type",
    "category": "Uncategorized"
  },
  {
    "model": "PlacementTypeType",
    "route": "placement-type-type",
    "name": "Placement Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "PreparationTypeType",
    "route": "preparation-type-type",
    "name": "Preparation Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "QuestionTypeType",
    "route": "question-type-type",
    "name": "Question Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "RecommendationTypeType",
    "route": "recommendation-type-type",
    "name": "Recommendation Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "SessionTypeType",
    "route": "session-type-type",
    "name": "Session Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "SetupTypeType",
    "route": "setup-type-type",
    "name": "Setup Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "SkillTypeType",
    "route": "skill-type-type",
    "name": "Skill Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "StatusType",
    "route": "status-type",
    "name": "Status Type",
    "category": "Uncategorized"
  },
  {
    "model": "TrainingProviderTypeType",
    "route": "training-provider-type-type",
    "name": "Training Provider Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "WorkPlaceApprovalType",
    "route": "work-place-approval-type",
    "name": "Work Place Approval Type",
    "category": "Uncategorized"
  },
  {
    "model": "AllocationChangeType",
    "route": "allocation-change-type",
    "name": "Allocation Change Type",
    "category": "Uncategorized"
  },
  {
    "model": "AppraisalCategoriesType",
    "route": "appraisal-categories-type",
    "name": "Appraisal Categories Type",
    "category": "Uncategorized"
  },
  {
    "model": "AreaForImprovementType",
    "route": "area-for-improvement-type",
    "name": "Area For Improvement Type",
    "category": "Uncategorized"
  },
  {
    "model": "CommunicatingRatingType",
    "route": "communicating-rating-type",
    "name": "Communicating Rating Type",
    "category": "Uncategorized"
  },
  {
    "model": "CompanyUserPositionType",
    "route": "company-user-position-type",
    "name": "Company User Position Type",
    "category": "Uncategorized"
  },
  {
    "model": "DateChangeReasonsType",
    "route": "date-change-reasons-type",
    "name": "Date Change Reasons Type",
    "category": "Uncategorized"
  },
  {
    "model": "DesignatedTradeTypeType",
    "route": "designated-trade-type-type",
    "name": "Designated Trade Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "DeviationReasonType",
    "route": "deviation-reason-type",
    "name": "Deviation Reason Type",
    "category": "Uncategorized"
  },
  {
    "model": "EmployerApprovalStatusType",
    "route": "employer-approval-status-type",
    "name": "Employer Approval Status Type",
    "category": "Uncategorized"
  },
  {
    "model": "EnrolmentStatusReasonType",
    "route": "enrolment-status-reason-type",
    "name": "Enrolment Status Reason Type",
    "category": "Uncategorized"
  },
  {
    "model": "EnrolmentTypeType",
    "route": "enrolment-type-type",
    "name": "Enrolment Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "HearingRatingType",
    "route": "hearing-rating-type",
    "name": "Hearing Rating Type",
    "category": "Uncategorized"
  },
  {
    "model": "HonoursClassificationType",
    "route": "honours-classification-type",
    "name": "Honours Classification Type",
    "category": "Uncategorized"
  },
  {
    "model": "MailTemplatesType",
    "route": "mail-templates-type",
    "name": "Mail Templates Type",
    "category": "Uncategorized"
  },
  {
    "model": "MailingListType",
    "route": "mailing-list-type",
    "name": "Mailing List Type",
    "category": "Uncategorized"
  },
  {
    "model": "MeetingAgendaType",
    "route": "meeting-agenda-type",
    "name": "Meeting Agenda Type",
    "category": "Uncategorized"
  },
  {
    "model": "ModulesCategoryType",
    "route": "modules-category-type",
    "name": "Modules Category Type",
    "category": "Uncategorized"
  },
  {
    "model": "NextRefreshYearType",
    "route": "next-refresh-year-type",
    "name": "Next Refresh Year Type",
    "category": "Uncategorized"
  },
  {
    "model": "NonNqfIntervStatusType",
    "route": "non-nqf-interv-status-type",
    "name": "Non Nqf Interv Status Type",
    "category": "Uncategorized"
  },
  {
    "model": "NqfDesigStatusType",
    "route": "nqf-desig-status-type",
    "name": "Nqf Desig Status Type",
    "category": "Uncategorized"
  },
  {
    "model": "PopiActStatusType",
    "route": "popi-act-status-type",
    "name": "Popi Act Status Type",
    "category": "Uncategorized"
  },
  {
    "model": "PrioritisationScaleType",
    "route": "prioritisation-scale-type",
    "name": "Prioritisation Scale Type",
    "category": "Uncategorized"
  },
  {
    "model": "PurposeOfSiteVisitType",
    "route": "purpose-of-site-visit-type",
    "name": "Purpose Of Site Visit Type",
    "category": "Uncategorized"
  },
  {
    "model": "QualificationEntryRequirementType",
    "route": "qualification-entry-requirement-type",
    "name": "Qualification Entry Requirement Type",
    "category": "Uncategorized"
  },
  {
    "model": "RememberingRatingType",
    "route": "remembering-rating-type",
    "name": "Remembering Rating Type",
    "category": "Uncategorized"
  },
  {
    "model": "SDFTypeType",
    "route": "sdftype-type",
    "name": "SDFType Type",
    "category": "Uncategorized"
  },
  {
    "model": "SdpTypeType",
    "route": "sdp-type-type",
    "name": "Sdp Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "SeeingRatingType",
    "route": "seeing-rating-type",
    "name": "Seeing Rating Type",
    "category": "Uncategorized"
  },
  {
    "model": "SelfcaringRatingType",
    "route": "selfcaring-rating-type",
    "name": "Selfcaring Rating Type",
    "category": "Uncategorized"
  },
  {
    "model": "SkillsGapTrackLookUpType",
    "route": "skills-gap-track-look-up-type",
    "name": "Skills Gap Track Look Up Type",
    "category": "Uncategorized"
  },
  {
    "model": "StatementOfResultsType",
    "route": "statement-of-results-type",
    "name": "Statement Of Results Type",
    "category": "Uncategorized"
  },
  {
    "model": "TaskRejectionContentsType",
    "route": "task-rejection-contents-type",
    "name": "Task Rejection Contents Type",
    "category": "Uncategorized"
  },
  {
    "model": "ToolCategoryType",
    "route": "tool-category-type",
    "name": "Tool Category Type",
    "category": "Uncategorized"
  },
  {
    "model": "TradeTestResultType",
    "route": "trade-test-result-type",
    "name": "Trade Test Result Type",
    "category": "Uncategorized"
  },
  {
    "model": "TrainingDeliveryMethodType",
    "route": "training-delivery-method-type",
    "name": "Training Delivery Method Type",
    "category": "Uncategorized"
  },
  {
    "model": "TrainingImpactRatingType",
    "route": "training-impact-rating-type",
    "name": "Training Impact Rating Type",
    "category": "Uncategorized"
  },
  {
    "model": "UnionMembershipType",
    "route": "union-membership-type",
    "name": "Union Membership Type",
    "category": "Uncategorized"
  },
  {
    "model": "ValidityType",
    "route": "validity-type",
    "name": "Validity Type",
    "category": "Uncategorized"
  },
  {
    "model": "WalkingRatingType",
    "route": "walking-rating-type",
    "name": "Walking Rating Type",
    "category": "Uncategorized"
  },
  {
    "model": "AppraisalCategoryCodeType",
    "route": "appraisal-category-code-type",
    "name": "Appraisal Category Code Type",
    "category": "Uncategorized"
  },
  {
    "model": "AppraisalChecklistType",
    "route": "appraisal-checklist-type",
    "name": "Appraisal Checklist Type",
    "category": "Uncategorized"
  },
  {
    "model": "AppraisalsType",
    "route": "appraisals-type",
    "name": "Appraisals Type",
    "category": "Uncategorized"
  },
  {
    "model": "AuditorMonitorReviewType",
    "route": "auditor-monitor-review-type",
    "name": "Auditor Monitor Review Type",
    "category": "Uncategorized"
  },
  {
    "model": "EnrolmentStatusType",
    "route": "enrolment-status-type",
    "name": "Enrolment Status Type",
    "category": "Uncategorized"
  },
  {
    "model": "FinYearQuartersLookUpType",
    "route": "fin-year-quarters-look-up-type",
    "name": "Fin Year Quarters Look Up Type",
    "category": "Uncategorized"
  },
  {
    "model": "InstitutionTypeType",
    "route": "institution-type-type",
    "name": "Institution Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "LearnerMentorRatioType",
    "route": "learner-mentor-ratio-type",
    "name": "Learner Mentor Ratio Type",
    "category": "Uncategorized"
  },
  {
    "model": "LearnerMonitoringSurveyType",
    "route": "learner-monitoring-survey-type",
    "name": "Learner Monitoring Survey Type",
    "category": "Uncategorized"
  },
  {
    "model": "NonCreditBearingInterventionTitleType",
    "route": "non-credit-bearing-intervention-title-type",
    "name": "Non Credit Bearing Intervention Title Type",
    "category": "Uncategorized"
  },
  {
    "model": "QualificationTasksType",
    "route": "qualification-tasks-type",
    "name": "Qualification Tasks Type",
    "category": "Uncategorized"
  },
  {
    "model": "QualificationToolKitType",
    "route": "qualification-tool-kit-type",
    "name": "Qualification Tool Kit Type",
    "category": "Uncategorized"
  },
  {
    "model": "QualificationToolListType",
    "route": "qualification-tool-list-type",
    "name": "Qualification Tool List Type",
    "category": "Uncategorized"
  },
  {
    "model": "RatioType",
    "route": "ratio-type",
    "name": "Ratio Type",
    "category": "Uncategorized"
  },
  {
    "model": "RefEntityType",
    "route": "ref-entity-type",
    "name": "Ref Entity Type",
    "category": "Uncategorized"
  },
  {
    "model": "ReportGenerationPropertiesType",
    "route": "report-generation-properties-type",
    "name": "Report Generation Properties Type",
    "category": "Uncategorized"
  },
  {
    "model": "STATSSAAreaCodeType",
    "route": "statssaarea-code-type",
    "name": "STATSSAArea Code Type",
    "category": "Uncategorized"
  },
  {
    "model": "SkillsProgramType",
    "route": "skills-program-type",
    "name": "Skills Program Type",
    "category": "Uncategorized"
  },
  {
    "model": "SkillsSetType",
    "route": "skills-set-type",
    "name": "Skills Set Type",
    "category": "Uncategorized"
  },
  {
    "model": "SocioeconomicStatusCodeType",
    "route": "socioeconomic-status-code-type",
    "name": "Socioeconomic Status Code Type",
    "category": "Uncategorized"
  },
  {
    "model": "StatusesType",
    "route": "statuses-type",
    "name": "Statuses Type",
    "category": "Uncategorized"
  },
  {
    "model": "UnitStandardType",
    "route": "unit-standard-type",
    "name": "Unit Standard Type",
    "category": "Uncategorized"
  },
  {
    "model": "YoutubeVideoType",
    "route": "youtube-video-type",
    "name": "Youtube Video Type",
    "category": "Uncategorized"
  },
  {
    "model": "ArplType",
    "route": "arpl-type",
    "name": "Arpl Type",
    "category": "Uncategorized"
  },
  {
    "model": "GlossaryType",
    "route": "glossary-type",
    "name": "Glossary Type",
    "category": "Uncategorized"
  },
  {
    "model": "AccreditationApplicationTypeType",
    "route": "accreditation-application-type-type",
    "name": "Accreditation Application Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "ActionPlanValidiationTypeType",
    "route": "action-plan-validiation-type-type",
    "name": "Action Plan Validiation Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "AddressTypeType",
    "route": "address-type-type",
    "name": "Address Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "AgeGroupType",
    "route": "age-group-type",
    "name": "Age Group Type",
    "category": "Uncategorized"
  },
  {
    "model": "AllocationStatusType",
    "route": "allocation-status-type",
    "name": "Allocation Status Type",
    "category": "Uncategorized"
  },
  {
    "model": "AnswerTypeType",
    "route": "answer-type-type",
    "name": "Answer Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "ApprovalType",
    "route": "approval-type",
    "name": "Approval Type",
    "category": "Uncategorized"
  },
  {
    "model": "AprlProgressType",
    "route": "aprl-progress-type",
    "name": "Aprl Progress Type",
    "category": "Uncategorized"
  },
  {
    "model": "ArplDocRequirementType",
    "route": "arpl-doc-requirement-type",
    "name": "Arpl Doc Requirement Type",
    "category": "Uncategorized"
  },
  {
    "model": "AssessorModeratorAppTypeType",
    "route": "assessor-moderator-app-type-type",
    "name": "Assessor Moderator App Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "BugReportTypeType",
    "route": "bug-report-type-type",
    "name": "Bug Report Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "BulkApprovalType",
    "route": "bulk-approval-type",
    "name": "Bulk Approval Type",
    "category": "Uncategorized"
  },
  {
    "model": "CategorizationType",
    "route": "categorization-type",
    "name": "Categorization Type",
    "category": "Uncategorized"
  },
  {
    "model": "CeritificateCollectionType",
    "route": "ceritificate-collection-type",
    "name": "Ceritificate Collection Type",
    "category": "Uncategorized"
  },
  {
    "model": "CloRecommendationType",
    "route": "clo-recommendation-type",
    "name": "Clo Recommendation Type",
    "category": "Uncategorized"
  },
  {
    "model": "CollectionType",
    "route": "collection-type",
    "name": "Collection Type",
    "category": "Uncategorized"
  },
  {
    "model": "CompanyRegOrSDLType",
    "route": "company-reg-or-sdltype",
    "name": "Company Reg Or SDLType",
    "category": "Uncategorized"
  },
  {
    "model": "CompanyUserTypeType",
    "route": "company-user-type-type",
    "name": "Company User Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "CompetenceType",
    "route": "competence-type",
    "name": "Competence Type",
    "category": "Uncategorized"
  },
  {
    "model": "CompletedPlannedType",
    "route": "completed-planned-type",
    "name": "Completed Planned Type",
    "category": "Uncategorized"
  },
  {
    "model": "ConfigDocProcessType",
    "route": "config-doc-process-type",
    "name": "Config Doc Process Type",
    "category": "Uncategorized"
  },
  {
    "model": "ContractStatusType",
    "route": "contract-status-type",
    "name": "Contract Status Type",
    "category": "Uncategorized"
  },
  {
    "model": "CreatedByType",
    "route": "created-by-type",
    "name": "Created By Type",
    "category": "Uncategorized"
  },
  {
    "model": "DGWindowTypeType",
    "route": "dgwindow-type-type",
    "name": "DGWindow Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "DateScheduleType",
    "route": "date-schedule-type",
    "name": "Date Schedule Type",
    "category": "Uncategorized"
  },
  {
    "model": "DeliveryTypeType",
    "route": "delivery-type-type",
    "name": "Delivery Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "DhetFileNumberType",
    "route": "dhet-file-number-type",
    "name": "Dhet File Number Type",
    "category": "Uncategorized"
  },
  {
    "model": "DiscretionalWithdrawalAppealType",
    "route": "discretional-withdrawal-appeal-type",
    "name": "Discretional Withdrawal Appeal Type",
    "category": "Uncategorized"
  },
  {
    "model": "DocumentRequiredCheckType",
    "route": "document-required-check-type",
    "name": "Document Required Check Type",
    "category": "Uncategorized"
  },
  {
    "model": "DocumentTrackerType",
    "route": "document-tracker-type",
    "name": "Document Tracker Type",
    "category": "Uncategorized"
  },
  {
    "model": "DocumetFileTypeType",
    "route": "documet-file-type-type",
    "name": "Documet File Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "EmployedUnEmployedType",
    "route": "employed-un-employed-type",
    "name": "Employed Un Employed Type",
    "category": "Uncategorized"
  },
  {
    "model": "GenerateAddType",
    "route": "generate-add-type",
    "name": "Generate Add Type",
    "category": "Uncategorized"
  },
  {
    "model": "GpDocumentTypeType",
    "route": "gp-document-type-type",
    "name": "Gp Document Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "GrantTypeType",
    "route": "grant-type-type",
    "name": "Grant Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "HighestEducationType",
    "route": "highest-education-type",
    "name": "Highest Education Type",
    "category": "Uncategorized"
  },
  {
    "model": "HoldingRoomStatusType",
    "route": "holding-room-status-type",
    "name": "Holding Room Status Type",
    "category": "Uncategorized"
  },
  {
    "model": "IdPassportType",
    "route": "id-passport-type",
    "name": "Id Passport Type",
    "category": "Uncategorized"
  },
  {
    "model": "LearnerChangeTypeType",
    "route": "learner-change-type-type",
    "name": "Learner Change Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "LearnerCompletenceType",
    "route": "learner-completence-type",
    "name": "Learner Completence Type",
    "category": "Uncategorized"
  },
  {
    "model": "LearnerDocRequirementType",
    "route": "learner-doc-requirement-type",
    "name": "Learner Doc Requirement Type",
    "category": "Uncategorized"
  },
  {
    "model": "LearnerStatusType",
    "route": "learner-status-type",
    "name": "Learner Status Type",
    "category": "Uncategorized"
  },
  {
    "model": "LearnerTransferApprovalType",
    "route": "learner-transfer-approval-type",
    "name": "Learner Transfer Approval Type",
    "category": "Uncategorized"
  },
  {
    "model": "LearnerTransferTypeType",
    "route": "learner-transfer-type-type",
    "name": "Learner Transfer Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "LostTimeReasonType",
    "route": "lost-time-reason-type",
    "name": "Lost Time Reason Type",
    "category": "Uncategorized"
  },
  {
    "model": "MailTagType",
    "route": "mail-tag-type",
    "name": "Mail Tag Type",
    "category": "Uncategorized"
  },
  {
    "model": "MailingListTypeType",
    "route": "mailing-list-type-type",
    "name": "Mailing List Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "MoaTypeType",
    "route": "moa-type-type",
    "name": "Moa Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "ModerationType",
    "route": "moderation-type",
    "name": "Moderation Type",
    "category": "Uncategorized"
  },
  {
    "model": "NewExistingLearnershipType",
    "route": "new-existing-learnership-type",
    "name": "New Existing Learnership Type",
    "category": "Uncategorized"
  },
  {
    "model": "NewOrLegacyType",
    "route": "new-or-legacy-type",
    "name": "New Or Legacy Type",
    "category": "Uncategorized"
  },
  {
    "model": "NsdpReportRunTypeType",
    "route": "nsdp-report-run-type-type",
    "name": "Nsdp Report Run Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "OpenClosedType",
    "route": "open-closed-type",
    "name": "Open Closed Type",
    "category": "Uncategorized"
  },
  {
    "model": "PastFutureType",
    "route": "past-future-type",
    "name": "Past Future Type",
    "category": "Uncategorized"
  },
  {
    "model": "PercentageType",
    "route": "percentage-type",
    "name": "Percentage Type",
    "category": "Uncategorized"
  },
  {
    "model": "PivotNonPivotType",
    "route": "pivot-non-pivot-type",
    "name": "Pivot Non Pivot Type",
    "category": "Uncategorized"
  },
  {
    "model": "PlannedImplementedType",
    "route": "planned-implemented-type",
    "name": "Planned Implemented Type",
    "category": "Uncategorized"
  },
  {
    "model": "ProgressTypeType",
    "route": "progress-type-type",
    "name": "Progress Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "ProviderNameRegisTrationNumberType",
    "route": "provider-name-regis-tration-number-type",
    "name": "Provider Name Regis Tration Number Type",
    "category": "Uncategorized"
  },
  {
    "model": "ProviderSusActionsType",
    "route": "provider-sus-actions-type",
    "name": "Provider Sus Actions Type",
    "category": "Uncategorized"
  },
  {
    "model": "PublicPrivateType",
    "route": "public-private-type",
    "name": "Public Private Type",
    "category": "Uncategorized"
  },
  {
    "model": "QCDTemplateTypeType",
    "route": "qcdtemplate-type-type",
    "name": "QCDTemplate Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "QCTOFileTypeType",
    "route": "qctofile-type-type",
    "name": "QCTOFile Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "QmrEnteredCompletedType",
    "route": "qmr-entered-completed-type",
    "name": "Qmr Entered Completed Type",
    "category": "Uncategorized"
  },
  {
    "model": "QmrEquityType",
    "route": "qmr-equity-type",
    "name": "Qmr Equity Type",
    "category": "Uncategorized"
  },
  {
    "model": "QmrGenderType",
    "route": "qmr-gender-type",
    "name": "Qmr Gender Type",
    "category": "Uncategorized"
  },
  {
    "model": "QmrReportTypeType",
    "route": "qmr-report-type-type",
    "name": "Qmr Report Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "QmrTypeSelectionType",
    "route": "qmr-type-selection-type",
    "name": "Qmr Type Selection Type",
    "category": "Uncategorized"
  },
  {
    "model": "QualificationTypeSelectionType",
    "route": "qualification-type-selection-type",
    "name": "Qualification Type Selection Type",
    "category": "Uncategorized"
  },
  {
    "model": "RatingType",
    "route": "rating-type",
    "name": "Rating Type",
    "category": "Uncategorized"
  },
  {
    "model": "RelationTypeType",
    "route": "relation-type-type",
    "name": "Relation Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "ReportGenerationType",
    "route": "report-generation-type",
    "name": "Report Generation Type",
    "category": "Uncategorized"
  },
  {
    "model": "ReportPropertiesType",
    "route": "report-properties-type",
    "name": "Report Properties Type",
    "category": "Uncategorized"
  },
  {
    "model": "RsaCitizenTypeType",
    "route": "rsa-citizen-type-type",
    "name": "Rsa Citizen Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "SDPApplicationTypeType",
    "route": "sdpapplication-type-type",
    "name": "SDPApplication Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "SdfRegistartionDocType",
    "route": "sdf-registartion-doc-type",
    "name": "Sdf Registartion Doc Type",
    "category": "Uncategorized"
  },
  {
    "model": "SdpCompanyActionListType",
    "route": "sdp-company-action-list-type",
    "name": "Sdp Company Action List Type",
    "category": "Uncategorized"
  },
  {
    "model": "SetaIndustryFundedType",
    "route": "seta-industry-funded-type",
    "name": "Seta Industry Funded Type",
    "category": "Uncategorized"
  },
  {
    "model": "SetmisDisabilityType",
    "route": "setmis-disability-type",
    "name": "Setmis Disability Type",
    "category": "Uncategorized"
  },
  {
    "model": "SignoffByType",
    "route": "signoff-by-type",
    "name": "Signoff By Type",
    "category": "Uncategorized"
  },
  {
    "model": "SiteVisitReportStatusType",
    "route": "site-visit-report-status-type",
    "name": "Site Visit Report Status Type",
    "category": "Uncategorized"
  },
  {
    "model": "SiteVisitTypeType",
    "route": "site-visit-type-type",
    "name": "Site Visit Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "SkillsTypeType",
    "route": "skills-type-type",
    "name": "Skills Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "SmeTypeType",
    "route": "sme-type-type",
    "name": "Sme Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "SubmissionType",
    "route": "submission-type",
    "name": "Submission Type",
    "category": "Uncategorized"
  },
  {
    "model": "SupportTypeType",
    "route": "support-type-type",
    "name": "Support Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "TaskStatusAndTypeType",
    "route": "task-status-and-type-type",
    "name": "Task Status And Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "TaskStatusType",
    "route": "task-status-type",
    "name": "Task Status Type",
    "category": "Uncategorized"
  },
  {
    "model": "TemplateTypeType",
    "route": "template-type-type",
    "name": "Template Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "TradeTestProgressType",
    "route": "trade-test-progress-type",
    "name": "Trade Test Progress Type",
    "category": "Uncategorized"
  },
  {
    "model": "TradeTestProgressReportingType",
    "route": "trade-test-progress-reporting-type",
    "name": "Trade Test Progress Reporting Type",
    "category": "Uncategorized"
  },
  {
    "model": "TradeTestTypeType",
    "route": "trade-test-type-type",
    "name": "Trade Test Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "TradeTypeType",
    "route": "trade-type-type",
    "name": "Trade Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "TrainingProviderFilterType",
    "route": "training-provider-filter-type",
    "name": "Training Provider Filter Type",
    "category": "Uncategorized"
  },
  {
    "model": "TrancheType",
    "route": "tranche-type",
    "name": "Tranche Type",
    "category": "Uncategorized"
  },
  {
    "model": "TrancheRuleType",
    "route": "tranche-rule-type",
    "name": "Tranche Rule Type",
    "category": "Uncategorized"
  },
  {
    "model": "TransferDocumentsType",
    "route": "transfer-documents-type",
    "name": "Transfer Documents Type",
    "category": "Uncategorized"
  },
  {
    "model": "TransferProgressType",
    "route": "transfer-progress-type",
    "name": "Transfer Progress Type",
    "category": "Uncategorized"
  },
  {
    "model": "TransferRequestTypeType",
    "route": "transfer-request-type-type",
    "name": "Transfer Request Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "UnitStandardLinkTypeType",
    "route": "unit-standard-link-type-type",
    "name": "Unit Standard Link Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "UserPermissionType",
    "route": "user-permission-type",
    "name": "User Permission Type",
    "category": "Uncategorized"
  },
  {
    "model": "UserRoleType",
    "route": "user-role-type",
    "name": "User Role Type",
    "category": "Uncategorized"
  },
  {
    "model": "UsersStatusType",
    "route": "users-status-type",
    "name": "Users Status Type",
    "category": "Uncategorized"
  },
  {
    "model": "WSPSearchTypeType",
    "route": "wspsearch-type-type",
    "name": "WSPSearch Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "AllocationChangeTypeType",
    "route": "allocation-change-type-type",
    "name": "Allocation Change Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "ArplReportingType",
    "route": "arpl-reporting-type",
    "name": "Arpl Reporting Type",
    "category": "Uncategorized"
  },
  {
    "model": "AssessorModTypeType",
    "route": "assessor-mod-type-type",
    "name": "Assessor Mod Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "CompanySizeType",
    "route": "company-size-type",
    "name": "Company Size Type",
    "category": "Uncategorized"
  },
  {
    "model": "CompanyTypeType",
    "route": "company-type-type",
    "name": "Company Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "MunicipalityTypeType",
    "route": "municipality-type-type",
    "name": "Municipality Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "WorkplaceApprovalTypeType",
    "route": "workplace-approval-type-type",
    "name": "Workplace Approval Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "WorkplaceSurveyTypeType",
    "route": "workplace-survey-type-type",
    "name": "Workplace Survey Type Type",
    "category": "Uncategorized"
  },
  {
    "model": "WpaDocRequirementsType",
    "route": "wpa-doc-requirements-type",
    "name": "Wpa Doc Requirements Type",
    "category": "Uncategorized"
  },
  {
    "model": "WspDocRequirementsType",
    "route": "wsp-doc-requirements-type",
    "name": "Wsp Doc Requirements Type",
    "category": "Uncategorized"
  },
  {
    "model": "WspReopenLocationType",
    "route": "wsp-reopen-location-type",
    "name": "Wsp Reopen Location Type",
    "category": "Uncategorized"
  },
  {
    "model": "WspReportType",
    "route": "wsp-report-type",
    "name": "Wsp Report Type",
    "category": "Uncategorized"
  },
  {
    "model": "YesNoType",
    "route": "yes-no-type",
    "name": "Yes No Type",
    "category": "Uncategorized"
  }
];
