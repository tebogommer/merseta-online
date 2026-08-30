package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.constants.HAJConstants;
import haj.com.entity.Company;
import haj.com.entity.MunicipalityType;
import haj.com.entity.OfoCodes;
import haj.com.entity.ProcessRoles;
import haj.com.entity.Province;
import haj.com.entity.StrategicPriorities;
import haj.com.entity.Users;
import haj.com.entity.YesNoLookup;
import haj.com.entity.enums.AllocationChangeTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.GrantTypeEnum;
import haj.com.entity.enums.InterventionTypeEnum;
import haj.com.entity.enums.PlannedImplementedEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.formconfig.FormSectionQuestions;
import haj.com.entity.formconfig.FormTypeAnswers;
import haj.com.entity.lookup.AccreditationStatus;
import haj.com.entity.lookup.AetProgrammeLevel;
import haj.com.entity.lookup.AllocationChange;
import haj.com.entity.lookup.ApplicantType;
import haj.com.entity.lookup.AppraisalCategories;
import haj.com.entity.lookup.AppraisalChecklist;
import haj.com.entity.lookup.Aqp;
import haj.com.entity.lookup.Bank;
import haj.com.entity.lookup.Chamber;
import haj.com.entity.lookup.CompanyUserPosition;
import haj.com.entity.lookup.DGYear;
import haj.com.entity.lookup.DateChangeReasons;
import haj.com.entity.lookup.Department;
import haj.com.entity.lookup.DesignatedTrade;
import haj.com.entity.lookup.DesignatedTradeLevel;
import haj.com.entity.lookup.DesignatedTradeType;
import haj.com.entity.lookup.Designation;
import haj.com.entity.lookup.DeviationReason;
import haj.com.entity.lookup.DisabilityStatus;
import haj.com.entity.lookup.EnrolmentStatus;
import haj.com.entity.lookup.Equity;
import haj.com.entity.lookup.Etqa;
import haj.com.entity.lookup.FinancialYears;
import haj.com.entity.lookup.Funding;
import haj.com.entity.lookup.Gender;
import haj.com.entity.lookup.GrantAmount;
import haj.com.entity.lookup.HighestQualificationRequired;
import haj.com.entity.lookup.InstitutionType;
import haj.com.entity.lookup.InterventionLevel;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.JobTitle;
import haj.com.entity.lookup.Language;
import haj.com.entity.lookup.LearnerMentorRatio;
import haj.com.entity.lookup.MaritalStatus;
import haj.com.entity.lookup.ModulesCategory;
import haj.com.entity.lookup.ModulesTitle;
import haj.com.entity.lookup.NoHardToFillVacancies;
import haj.com.entity.lookup.NonCreditBearingIntervationTitle;
import haj.com.entity.lookup.NqfLevels;
import haj.com.entity.lookup.NsdpReportConfig;
import haj.com.entity.lookup.OccupationCategory;
import haj.com.entity.lookup.Office;
import haj.com.entity.lookup.OrganisationType;
import haj.com.entity.lookup.OrganisedLabourUnion;
import haj.com.entity.lookup.ProjectType;
import haj.com.entity.lookup.ProviderClass;
import haj.com.entity.lookup.ProviderStatus;
import haj.com.entity.lookup.ProviderType;
import haj.com.entity.lookup.PurposeOfSiteVisit;
import haj.com.entity.lookup.PurposeOfSiteVisitSupport;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.RelationshipToCompany;
import haj.com.entity.lookup.Roles;
import haj.com.entity.lookup.SDFType;
import haj.com.entity.lookup.ScarcityReason;
import haj.com.entity.lookup.ScopeOfResponsibility;
import haj.com.entity.lookup.SdpType;
import haj.com.entity.lookup.Seta;
import haj.com.entity.lookup.SizeOfCompany;
import haj.com.entity.lookup.SkillsGapTrackLookUp;
import haj.com.entity.lookup.SkillsIdentification;
import haj.com.entity.lookup.Title;
import haj.com.entity.lookup.ToolCategory;
import haj.com.entity.lookup.Town;
import haj.com.entity.lookup.Training;
import haj.com.entity.lookup.TrainingDeliveryMethod;
import haj.com.entity.lookup.TrainingReportedAtrPtr;
import haj.com.entity.lookup.UnionMembership;
import haj.com.entity.lookup.UnitStandards;
import haj.com.entity.lookup.UserResponsibility;
import haj.com.entity.lookup.VacancyReasons;
import haj.com.entity.lookup.Validity;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyUsersService;
import haj.com.service.FormTypeAnswersService;
import haj.com.service.GenderService;
import haj.com.service.MunicipalityTypeService;
import haj.com.service.NqfLevelsService;
import haj.com.service.OfoCodesService;
import haj.com.service.ProcessRolesService;
import haj.com.service.ProvinceService;
import haj.com.service.StrategicPrioritiesService;
import haj.com.service.UnitStandardsService;
import haj.com.service.YesNoLookupService;
import haj.com.service.lookup.AccreditationStatusService;
import haj.com.service.lookup.AetProgrammeLevelService;
import haj.com.service.lookup.AllocationChangeService;
import haj.com.service.lookup.ApplicantTypeService;
import haj.com.service.lookup.AppraisalCategoriesService;
import haj.com.service.lookup.AppraisalChecklistService;
import haj.com.service.lookup.AqpService;
import haj.com.service.lookup.BankService;
import haj.com.service.lookup.ChamberService;
import haj.com.service.lookup.CompanyUserPositionService;
import haj.com.service.lookup.DGYearService;
import haj.com.service.lookup.DateChangeReasonsService;
import haj.com.service.lookup.DepartmentService;
import haj.com.service.lookup.DesignatedTradeLevelService;
import haj.com.service.lookup.DesignatedTradeService;
import haj.com.service.lookup.DesignatedTradeTypeService;
import haj.com.service.lookup.DesignationService;
import haj.com.service.lookup.DeviationReasonService;
import haj.com.service.lookup.DisabilityStatusService;
import haj.com.service.lookup.EnrolmentStatusService;
import haj.com.service.lookup.EquityService;
import haj.com.service.lookup.EtqaService;
import haj.com.service.lookup.FinancialYearsService;
import haj.com.service.lookup.FundingService;
import haj.com.service.lookup.GrantAmountService;
import haj.com.service.lookup.HighestQualificationRequiredService;
import haj.com.service.lookup.InstitutionTypeService;
import haj.com.service.lookup.InterventionLevelService;
import haj.com.service.lookup.InterventionTypeService;
import haj.com.service.lookup.JobTitleService;
import haj.com.service.lookup.LanguageService;
import haj.com.service.lookup.LearnerMentorRatioService;
import haj.com.service.lookup.MaritalStatusService;
import haj.com.service.lookup.ModulesCategoryService;
import haj.com.service.lookup.ModulesTitleService;
import haj.com.service.lookup.NoHardToFillVacanciesService;
import haj.com.service.lookup.NonCreditBearingIntervationTitleService;
import haj.com.service.lookup.NsdpReportConfigService;
import haj.com.service.lookup.OccupationCategoryService;
import haj.com.service.lookup.OfficeService;
import haj.com.service.lookup.OrganisationTypeService;
import haj.com.service.lookup.OrganisedLabourUnionService;
import haj.com.service.lookup.ProjectTypeService;
import haj.com.service.lookup.ProviderClassService;
import haj.com.service.lookup.ProviderStatusService;
import haj.com.service.lookup.ProviderTypeService;
import haj.com.service.lookup.PurposeOfSiteVisitService;
import haj.com.service.lookup.PurposeOfSiteVisitSupportService;
import haj.com.service.lookup.RegionService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.service.lookup.RelationshipToCompanyService;
import haj.com.service.lookup.RolesService;
import haj.com.service.lookup.SDFTypeService;
import haj.com.service.lookup.ScarcityReasonService;
import haj.com.service.lookup.ScopeOfResponsibilityService;
import haj.com.service.lookup.SdpTypeService;
import haj.com.service.lookup.SetaService;
import haj.com.service.lookup.SizeOfCompanyService;
import haj.com.service.lookup.SkillsGapTrackLookUpService;
import haj.com.service.lookup.SkillsIdentificationService;
import haj.com.service.lookup.TitleService;
import haj.com.service.lookup.ToolCategoryService;
import haj.com.service.lookup.TownService;
import haj.com.service.lookup.TrainingDeliveryMethodService;
import haj.com.service.lookup.TrainingReportedAtrPtrService;
import haj.com.service.lookup.TrainingService;
import haj.com.service.lookup.UnionMembershipService;
import haj.com.service.lookup.UserResponsibilityService;
import haj.com.service.lookup.VacancyReasonsService;
import haj.com.service.lookup.ValidityService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class SelectItemsUI.
 */
@ManagedBean(name = "selectItemsUI")
@ViewScoped
public class SelectItemsUI extends AbstractUI {

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Run init.
	 * @throws Exception
	 * the exception
	 */
	private void runInit() throws Exception {

	}

	/**
	 * Gets the select items occupation category.
	 * @return the select items occupation category
	 */
	public List<OccupationCategory> getSelectItemsOccupationCategory() {
		OccupationCategoryService occupationCategoryService = new OccupationCategoryService();
		List<OccupationCategory>  l                         = null;
		try {
			l = occupationCategoryService.allOccupationCategory();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items intervention type.
	 * @return the select items intervention type
	 */
	public List<InterventionType> getSelectItemsInterventionType() {
		InterventionTypeService interventionTypeService = new InterventionTypeService();
		List<InterventionType>  l                       = null;
		try {
			l = interventionTypeService.allInterventionType();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<InterventionType> getSelectItemsInterventionTypeNoARPL() {
		InterventionTypeService interventionTypeService = new InterventionTypeService();
		List<InterventionType>  l                       = null;
		try {
			l = interventionTypeService.allInterventionTypeNoARPL();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<InterventionType> getSelectItemsInterventionTypeWspSelection() {
		InterventionTypeService interventionTypeService = new InterventionTypeService();
		List<InterventionType>  l                       = null;
		try {
			l = interventionTypeService.allInterventionTypeWspSelection();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<InterventionType> getSelectItemsInterventionTypeAtrSelection() {
		InterventionTypeService interventionTypeService = new InterventionTypeService();
		List<InterventionType>  l                       = null;
		try {
			l = interventionTypeService.allInterventionTypeAtrSelection();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<InterventionType> getSelectItemsInterventionTypeWithARPL() {
		InterventionTypeService interventionTypeService = new InterventionTypeService();
		List<InterventionType>  l                       = null;
		try {
			l = interventionTypeService.allInterventionTypeWithARPL();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<InterventionType> getSelectItemsInterventionTypeByNonMerseta() {
		InterventionTypeService interventionTypeService = new InterventionTypeService();
		List<InterventionType>  l                       = null;
		try {
			l = interventionTypeService.allInterventionTypeByNonMerseta(true);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<InterventionType> getSelectItemsInterventionTypeWithNoAccreditaionNoARPL() {
		InterventionTypeService interventionTypeService = new InterventionTypeService();
		List<InterventionType>  l                       = null;
		try {
			l = interventionTypeService.allInterventionTypeWithNoAccreditaionNoARPL(true);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<InterventionType> getSelectItemsInterventionTypeWithNoAccreditaion() {
		InterventionTypeService interventionTypeService = new InterventionTypeService();
		List<InterventionType>  l                       = null;
		try {
			l = interventionTypeService.allInterventionTypeWithNoAccreditaion(true);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<InterventionType> getSelectItemsInterventionTypeWithNoAccreditaionWithARPL() {
		InterventionTypeService interventionTypeService = new InterventionTypeService();
		List<InterventionType>  l                       = null;
		try {
			l = interventionTypeService.allInterventionTypeWithNoAccreditaionWithARPL(true);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items intervention type.
	 * @return the select items intervention type
	 */
	public List<InterventionType> getSelectItemsInterventionTypeLearnerShip() {
		InterventionTypeService interventionTypeService = new InterventionTypeService();
		List<InterventionType>  l                       = null;
		try {
			l = interventionTypeService.findByInterventionType(InterventionTypeEnum.Learnership);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items intervention type.
	 * @return the select items intervention type
	 */
	public List<DGYear> getSelectItemsDGYear() {
		DGYearService dGYearService = new DGYearService();
		List<DGYear>  l             = null;
		try {
			l = dGYearService.allDGYear();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<AppraisalCategories> getSelectItemsAppraisalCategories() {
		AppraisalCategoriesService appraisalCategoriesYearService = new AppraisalCategoriesService();
		List<AppraisalCategories>  l                              = null;
		try {
			l = appraisalCategoriesYearService.allAppraisalCategories();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<AppraisalChecklist> getSelectItemsAppraisalChecklist() {
		AppraisalChecklistService appraisalCategoriesYearService = new AppraisalChecklistService();
		List<AppraisalChecklist>  l                              = null;
		try {
			l = appraisalCategoriesYearService.allAppraisalChecklist();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items intervention type.
	 * @return the select items intervention type
	 */
	public List<ProjectType> getSelectItemsProjectType() {
		ProjectTypeService projectTagsService = new ProjectTypeService();
		List<ProjectType>  l                  = null;
		try {
			l = projectTagsService.allProjectTtype();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<InterventionType> getSelectItemsInterventionTypePivotal() {
		InterventionTypeService interventionTypeService = new InterventionTypeService();
		List<InterventionType>  l                       = null;
		try {
			l = interventionTypeService.allInterventionTypePivotal();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<InterventionType> getSelectItemsInterventionTypeNonPivotal() {
		InterventionTypeService interventionTypeService = new InterventionTypeService();
		List<InterventionType>  l                       = null;
		try {
			l = interventionTypeService.allInterventionTypeNonPivotal();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}


	/**
	 * Gets the select items intervention level.
	 * @return the select items intervention level
	 */
	public List<InterventionLevel> getSelectItemsInterventionLevel() {
		InterventionLevelService InterventionLevelService = new InterventionLevelService();
		List<InterventionLevel>  l                        = null;
		try {
			l = InterventionLevelService.allInterventionLevel();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items intervention type by their intervention type status: learnership.
	 * @return the select items intervention type
	 */
	public List<InterventionType> getSelectItemsInterventionTypeLearnershipType() {
		InterventionTypeService interventionTypeService = new InterventionTypeService();
		List<InterventionType>  l                       = null;
		try {
			l = interventionTypeService.findByInterventionTypeEnum(InterventionTypeEnum.Learnership);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<ApplicantType> getSelectItemsApplicantType() {
		ApplicantTypeService applicantTypeService = new ApplicantTypeService();
		List<ApplicantType>  l                    = null;
		try {
			l = applicantTypeService.allApplicantType();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items scarcity reason.
	 * @return the select items scarcity reason
	 */
	public List<ScarcityReason> getSelectItemsScarcityReason() {
		ScarcityReasonService scarcityReasonService = new ScarcityReasonService();
		List<ScarcityReason>  l                     = null;
		try {
			l = scarcityReasonService.allScarcityReason();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items gender.
	 * @return the select items gender
	 */
	public List<Gender> getSelectItemsGender() {
		GenderService genderService = new GenderService();
		List<Gender>  l             = null;
		try {
			l = genderService.allGender();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<MaritalStatus> getSelectItemsMaritalStatus() {
		MaritalStatusService maritalStatusService = new MaritalStatusService();
		List<MaritalStatus>  l                    = null;
		try {
			l = maritalStatusService.allMaritalStatus();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items yes no lookup.
	 * @return the select items yes no lookup
	 */
	public List<YesNoLookup> getSelectItemsYesNoLookup() {

		YesNoLookupService yesNoLookupService = new YesNoLookupService();
		List<YesNoLookup>  l                  = null;
		try {
			l = yesNoLookupService.allYesNoLookup();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items institution type.
	 * @return the select items institution type
	 */
	public List<InstitutionType> getSelectItemsInstitutionType() {
		InstitutionTypeService institutionTypeService = new InstitutionTypeService();
		List<InstitutionType>  l                      = null;
		try {
			l = institutionTypeService.allInstitutionType();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Select items SDF type.
	 * @return the list
	 */
	public List<SDFType> selectItemsSDFType() {
		SDFTypeService sdfTypeService = new SDFTypeService();
		List<SDFType>  l              = null;
		try {
			l = sdfTypeService.allSDFType();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items company user position.
	 * @return the select items company user position
	 */
	public List<CompanyUserPosition> getSelectItemsCompanyUserPosition() {
		CompanyUserPositionService companyUserPositionService = new CompanyUserPositionService();
		List<CompanyUserPosition>  l                          = null;
		try {
			l = companyUserPositionService.allCompanyUserPosition();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<UserResponsibility> getSelectItemsUserResponsibility() {
		UserResponsibilityService companyUserPositionService = new UserResponsibilityService();
		List<UserResponsibility>  l                          = null;
		try {
			l = companyUserPositionService.allUserResponsibility();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items OFO codes.
	 * @return the select items OFO codes
	 */
	public List<OfoCodes> getSelectItemsOFOCodes() {
		OfoCodesService ofoCodesService = new OfoCodesService();
		List<OfoCodes>  l               = null;
		try {
			l = ofoCodesService.allOfoCodes();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Aqp> getSelectItemsAqp() {
		AqpService ofoCodesService = new AqpService();
		List<Aqp>  l               = null;
		try {
			l = ofoCodesService.allAqp();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items titles.
	 * @return the select items titles
	 */
	public List<Title> getSelectItemsTitles() {
		TitleService titleService = new TitleService();
		List<Title>  l            = null;
		try {
			l = titleService.allTitle();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LearnerMentorRatio> getSelectItemsLearnerMentorRatio() {
		LearnerMentorRatioService learnerMentorRatioService = new LearnerMentorRatioService();
		List<LearnerMentorRatio>  l                         = null;
		try {
			l = learnerMentorRatioService.allLearnerMentorRatio();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items equity.
	 * @return the select items equity
	 */
	public List<Equity> getSelectItemsEquity() {
		EquityService equityService = new EquityService();
		List<Equity>  l             = null;
		try {
			l = equityService.allEquity();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items bank.
	 * @return the select items bank
	 */
	public List<Bank> getSelectItemsBank() {
		BankService bankService = new BankService();
		List<Bank>  l           = null;
		try {
			l = bankService.allBank();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Roles> getSelectItemsRoles() {
		RolesService bankService = new RolesService();
		List<Roles>  l           = null;
		try {
			l = bankService.allRoles();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items etqa.
	 * @return the select items etqa
	 */
	public List<Etqa> getSelectItemsEtqa() {
		EtqaService etqaService = new EtqaService();
		List<Etqa>  l           = null;
		try {
			l = etqaService.allEtqa();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items etqa.
	 * @return the select items etqa
	 */
	public List<Etqa> getSelectItemsEtqaRemoveMerSeta() {
		EtqaService etqaService = new EtqaService();
		List<Etqa>  l           = null;
		try {
			l = etqaService.allEtqaRemoveMerSeta();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items etqa.
	 * @return the select items etqa
	 */
	public List<Etqa> getSelectItemsQctoEtqa() {
		EtqaService etqaService = new EtqaService();
		List<Etqa>  l           = null;
		try {
			l = etqaService.allQctoEtqa();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items funding.
	 * @return the select items funding
	 */
	public List<Funding> getSelectItemsFunding() {
		FundingService fundingService = new FundingService();
		List<Funding>  l              = null;
		try {
			l = fundingService.allFunding();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items funding.
	 * @return the select items funding
	 */
	public List<Funding> getSelectItemsFundingWSP() {
		FundingService fundingService = new FundingService();
		List<Funding>  l              = null;
		try {
			l = fundingService.allFunding(true);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Funding> getSelectItemsFundingDisc() {
		FundingService fundingService = new FundingService();
		List<Funding>  l              = new ArrayList<>();
		try {
			l.add(fundingService.findByKey(HAJConstants.DISC_FUNDING_ID));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Funding> getSelectItemsFundingDiscWSP() {
		FundingService fundingService = new FundingService();
		List<Funding>  l              = new ArrayList<>();
		try {
			l.add(fundingService.findByKey(HAJConstants.DISC_FUNDING_ID));
			l.addAll(fundingService.allFunding(true));
			l = l.stream().distinct().collect(Collectors.toList());
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items disability.
	 * @return the select items disability
	 */
	public List<DisabilityStatus> getSelectItemsDisability() {
		DisabilityStatusService disabilityStatusService = new DisabilityStatusService();
		List<DisabilityStatus>  l                       = null;
		try {
			l = disabilityStatusService.allDisabilityStatus();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<NqfLevels> getSelectItemsNqfLevels() {
		NqfLevelsService levelsService = new NqfLevelsService();
		List<NqfLevels>  l             = null;
		try {
			l = levelsService.allNqfLevels();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<ProviderClass> getSelectItemsProviderClass() {
		ProviderClassService levelsService = new ProviderClassService();
		List<ProviderClass>  l             = null;
		try {
			l = levelsService.allProviderClass();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<ProviderType> getSelectItemsProviderTypeNotWSP() {
		ProviderTypeService levelsService = new ProviderTypeService();
		List<ProviderType>  l             = null;
		try {
			l = levelsService.allProviderTypeNotWSP();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<ProviderType> getSelectItemsProviderTypeForProviderRegistration() {
		ProviderTypeService levelsService = new ProviderTypeService();
		List<ProviderType>  l             = null;
		try {
			l = levelsService.allProviderTypeForProviderRegistration();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<ProviderType> getSelectItemsProviderType() {
		ProviderTypeService levelsService = new ProviderTypeService();
		List<ProviderType>  l             = null;
		try {
			l = levelsService.allProviderType();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Province> getSelectItemsProvince() {
		ProvinceService levelsService = new ProvinceService();
		List<Province>  l             = null;
		try {
			l = levelsService.allProvince();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<MunicipalityType> getSelectItemsMunicipalityType() {
		MunicipalityTypeService levelsService = new MunicipalityTypeService();
		List<MunicipalityType>  l             = null;
		try {
			l = levelsService.allMunicipalityType();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<TrainingDeliveryMethod> getSelectItemsTrainingDeliveryMethod() {
		TrainingDeliveryMethodService levelsService = new TrainingDeliveryMethodService();
		List<TrainingDeliveryMethod>  l             = null;
		try {
			l = levelsService.allTrainingDeliveryMethod();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<OrganisedLabourUnion> getSelectItemsOrganisedLabourUnion() {
		OrganisedLabourUnionService levelsService = new OrganisedLabourUnionService();
		List<OrganisedLabourUnion>  l             = null;
		try {
			l = levelsService.allOrganisedLabourUnion();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<GrantAmount> getSelectItemsGrantAmountDisability() {
		GrantAmountService levelsService = new GrantAmountService();
		List<GrantAmount>  l             = null;
		try {
			l = levelsService.allGrantAmount(GrantTypeEnum.DisabilityGrantAmount);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<GrantAmount> getSelectItemsGrantAmountBasic() {
		GrantAmountService levelsService = new GrantAmountService();
		List<GrantAmount>  l             = null;
		try {
			l = levelsService.allGrantAmount(GrantTypeEnum.GrantAmount);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Training> getSelectItemsPlannedTraining() {
		TrainingService service = new TrainingService();
		List<Training>  l       = null;
		try {
			l = service.allTraining(PlannedImplementedEnum.Planned);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Training> getSelectItemsImplementedTraining() {
		TrainingService service = new TrainingService();
		List<Training>  l       = null;
		try {
			l = service.allTraining(PlannedImplementedEnum.Implemented);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items skills gap track look up table.
	 * @return the select items skills gap track look up
	 */
	public List<SkillsGapTrackLookUp> getSkillsGapTrackLookUp() {
		SkillsGapTrackLookUpService skillsGapTrackLookUpService = new SkillsGapTrackLookUpService();
		List<SkillsGapTrackLookUp>  l                           = null;
		try {
			l = skillsGapTrackLookUpService.allSkillsGapTrackLookUp();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items VacancyReasons table.
	 * @return the select items VacancyReasons
	 */
	public List<VacancyReasons> getVacancyReasons() {
		VacancyReasonsService vacancyReasonsServiceService = new VacancyReasonsService();
		List<VacancyReasons>  l                            = null;
		try {
			l = vacancyReasonsServiceService.allVacancyReasons();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items NoHardToFillVacancies table.
	 * @return the select items NoHardToFillVacancies
	 */
	public List<NoHardToFillVacancies> getNoHardToFillVacancies() {
		NoHardToFillVacanciesService noHardToFillVacanciesService = new NoHardToFillVacanciesService();
		List<NoHardToFillVacancies>  l                            = null;
		try {
			l = noHardToFillVacanciesService.allNoHardToFillVacancies();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items TrainingReportedAtrPtr table.
	 * @return the select items TrainingReportedAtrPtr
	 */
	public List<TrainingReportedAtrPtr> getTrainingReportedAtrPtr() {
		TrainingReportedAtrPtrService trainingReportedAtrPtrService = new TrainingReportedAtrPtrService();
		List<TrainingReportedAtrPtr>  l                             = null;
		try {
			l = trainingReportedAtrPtrService.allTrainingReportedAtrPtr();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items TrainingReportedAtrPtr table.
	 * @return the select items TrainingReportedAtrPtr
	 */
	public List<Office> getOffice() {
		OfficeService trainingReportedAtrPtrService = new OfficeService();
		List<Office>  l                             = null;
		try {
			l = trainingReportedAtrPtrService.allOffice();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Town> getTown() {
		TownService trainingReportedAtrPtrService = new TownService();
		List<Town>  l                             = null;
		try {
			l = trainingReportedAtrPtrService.allTown();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<ScopeOfResponsibility> getScopeOfResponsibility() {
		ScopeOfResponsibilityService trainingReportedAtrPtrService = new ScopeOfResponsibilityService();
		List<ScopeOfResponsibility>  l                             = null;
		try {
			l = trainingReportedAtrPtrService.allScopeOfResponsibility();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<RelationshipToCompany> getRelationshipToCompany() {
		RelationshipToCompanyService trainingReportedAtrPtrService = new RelationshipToCompanyService();
		List<RelationshipToCompany>  l                             = null;
		try {
			l = trainingReportedAtrPtrService.allRelationshipToCompany();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<JobTitle> getJobTitle() {
		JobTitleService trainingReportedAtrPtrService = new JobTitleService();
		List<JobTitle>  l                             = null;
		try {
			l = trainingReportedAtrPtrService.allJobTitle();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items HighestQualificationRequired table.
	 * @return the select items HighestQualificationRequired
	 */
	public List<HighestQualificationRequired> getHighestQualificationRequired() {
		HighestQualificationRequiredService highestQualificationRequiredService = new HighestQualificationRequiredService();
		List<HighestQualificationRequired>  l                                   = null;
		try {
			l = highestQualificationRequiredService.allHighestQualificationRequired();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items OrganisationType table.
	 * @return the select items OrganisationType
	 */
	public List<OrganisationType> getOrganisationType() {
		OrganisationTypeService organisationTypeService = new OrganisationTypeService();
		List<OrganisationType>  l                       = null;
		try {
			l = organisationTypeService.allOrganisationType();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Region> getRegion() {
		RegionService organisationTypeService = new RegionService();
		List<Region>  l                       = null;
		try {
			l = organisationTypeService.allRegion();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the OrganisedLabourUnion.
	 * @return the OrganisedLabourUnion
	 */
	public List<OrganisedLabourUnion> getOrganisedLabourUnion() {
		OrganisedLabourUnionService organisedLabourUnionService = new OrganisedLabourUnionService();
		List<OrganisedLabourUnion>  l                           = null;
		try {
			l = organisedLabourUnionService.allOrganisedLabourUnion();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons>  l                    = null;
		try {
			l = rejectReasonsService.allRejectReasons();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<DateChangeReasons> getDateChangeReasons() {
		DateChangeReasonsService dateChangeReasonsService = new DateChangeReasonsService();
		List<DateChangeReasons>  l                        = null;
		try {
			l = dateChangeReasonsService.allDateChangeReasons();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Chamber> getChambers() {
		ChamberService rejectReasonsService = new ChamberService();
		List<Chamber>  l                    = null;
		try {
			l = rejectReasonsService.allChamber();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<EnrolmentStatus> getEnrolmentStatus() {
		EnrolmentStatusService rejectReasonsService = new EnrolmentStatusService();
		List<EnrolmentStatus>  l                    = null;
		try {
			l = rejectReasonsService.allEnrolmentStatus();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<EnrolmentStatus> getEnrolmentStatusATR() {
		EnrolmentStatusService rejectReasonsService = new EnrolmentStatusService();
		List<EnrolmentStatus>  l                    = null;
		try {
			l = rejectReasonsService.allEnrolmentStatusATR();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<EnrolmentStatus> getEnrolmentStatusWSP() {
		EnrolmentStatusService rejectReasonsService = new EnrolmentStatusService();
		List<EnrolmentStatus>  l                    = null;
		try {
			l = rejectReasonsService.allEnrolmentStatusWSP();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<UnitStandards> getUnitStandards() {
		UnitStandardsService unitStandardsService = new UnitStandardsService();
		List<UnitStandards>  l                    = null;
		try {
			l = unitStandardsService.allUnitStandards();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Locates UnitStandards where last enrollment date before current date
	 * @return
	 */
	public List<UnitStandards> getUnitStandardsBeforeLastEnrolmentDate() {
		UnitStandardsService unitStandardsService = new UnitStandardsService();
		List<UnitStandards>  l                    = null;
		try {
			l = unitStandardsService.findBeforeLastEnrolmentDate(GenericUtility.getStartOfDay(getNow()));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<ModulesCategory> getModulesCategory() {
		ModulesCategoryService rejectReasonsService = new ModulesCategoryService();
		List<ModulesCategory>  l                    = null;
		try {
			l = rejectReasonsService.allModulesCategory();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<ModulesTitle> getModulesTitle() {
		ModulesTitleService rejectReasonsService = new ModulesTitleService();
		List<ModulesTitle>  l                    = null;
		try {
			l = rejectReasonsService.allModulesTitle();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<SkillsIdentification> getSkillsIdentification() {
		SkillsIdentificationService rejectReasonsService = new SkillsIdentificationService();
		List<SkillsIdentification>  l                    = null;
		try {
			l = rejectReasonsService.allSkillsIdentification();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<DeviationReason> getDeviationReason() {
		DeviationReasonService deviationReasonService = new DeviationReasonService();
		List<DeviationReason>  l                      = null;
		try {
			l = deviationReasonService.allDeviationReason();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<ToolCategory> getToolCategory() {
		ToolCategoryService rejectReasonsService = new ToolCategoryService();
		List<ToolCategory>  l                    = null;
		try {
			l = rejectReasonsService.allToolCategory();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Language> getLanguage() {
		LanguageService rejectReasonsService = new LanguageService();
		List<Language>  l                    = null;
		try {
			l = rejectReasonsService.allLanguage();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<PurposeOfSiteVisit> getPurposeOfSiteVisit() {
		PurposeOfSiteVisitService lookupsService = new PurposeOfSiteVisitService();
		List<PurposeOfSiteVisit>  l              = null;
		try {
			l = lookupsService.allPurposeOfSiteVisit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<PurposeOfSiteVisitSupport> getPurposeOfSiteVisitSupport() {
		PurposeOfSiteVisitSupportService lookupsService = new PurposeOfSiteVisitSupportService();
		List<PurposeOfSiteVisitSupport>  l              = null;
		try {
			l = lookupsService.allPurposeOfSiteVisitSupport();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<FormTypeAnswers> getFormTypeAnswers(FormSectionQuestions formSectionQuestions) {
		FormTypeAnswersService lookupsService = new FormTypeAnswersService();
		List<FormTypeAnswers>  l              = null;
		try {
			l = lookupsService.findByQuestion(formSectionQuestions);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<AccreditationStatus> getAccreditationStatus() {
		AccreditationStatusService lookupsService = new AccreditationStatusService();
		List<AccreditationStatus>  l              = null;
		try {
			l = lookupsService.allNoLegacyAccreditationStatus();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<ProviderStatus> getProviderStatus() {
		ProviderStatusService lookupsService = new ProviderStatusService();
		List<ProviderStatus>  l              = null;
		try {
			l = lookupsService.allProviderStatus();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<UnionMembership> getUnionMembership() {
		UnionMembershipService lookupsService = new UnionMembershipService();
		List<UnionMembership>  l              = null;
		try {
			l = lookupsService.allUnionMembership();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<ProcessRoles> getProcessRoles(ConfigDocProcessEnum configDocProcessEnum) {
		ProcessRolesService lookupsService = new ProcessRolesService();
		List<ProcessRoles>  l              = new ArrayList<>();
		try {
			List<ProcessRoles> l2 = lookupsService.findByConfigDoc(configDocProcessEnum);
			for (ProcessRoles processRoles : l2) {
				if (processRoles.getRolePermission() == UserPermissionEnum.EditUpload || processRoles.getRolePermission() == UserPermissionEnum.Upload || processRoles.getRolePermission() == UserPermissionEnum.UploadCSV || processRoles.getRolePermission() == UserPermissionEnum.FinalUploadApproval || processRoles.getRolePermission() == UserPermissionEnum.FinalUpload || processRoles.getRolePermission() == UserPermissionEnum.UploadApprove
						|| processRoles.getRolePermission() == UserPermissionEnum.FinalEditUpload) {
					l.add(processRoles);
				}
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Validity> getValidity() {
		ValidityService lookupsService = new ValidityService();
		List<Validity>  l              = null;
		try {
			l = lookupsService.allValidity();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Department> getDepartment() {
		DepartmentService lookupsService = new DepartmentService();
		List<Department>  l              = null;
		try {
			l = lookupsService.allDepartment();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Users> findCompanyUsers(Company company) {
		CompanyUsersService lookupsService = new CompanyUsersService();
		List<Users>         l              = null;
		try {
			l = lookupsService.findUsersByCompany(company);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Users> findDistinctCompanyUsersPosistionPopulated(Company company) {
		CompanyUsersService lookupsService = new CompanyUsersService();
		List<Users>         l              = null;
		try {
			l = lookupsService.findDistinctUsersByCompanyPopulateRoles(company);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<AllocationChange> getAllocationChangeRequestChange() {
		AllocationChangeService lookupsService = new AllocationChangeService();
		List<AllocationChange>  l              = null;
		try {
			l = lookupsService.allAllocationChangeByType(AllocationChangeTypeEnum.RequestChange);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<AllocationChange> getAllocationChangeRequestHigherAllocation() {
		AllocationChangeService lookupsService = new AllocationChangeService();
		List<AllocationChange>  l              = null;
		try {
			l = lookupsService.allAllocationChangeByType(AllocationChangeTypeEnum.RequestHigherAllocation);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items designation
	 * @return the select items designation
	 */
	public List<Designation> getSelectedDesignation() {
		DesignationService designationService = new DesignationService();
		List<Designation>  l                  = null;
		try {
			l = designationService.allTPApplicationDesignation();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Designation> getSdpDesignations() {
		DesignationService designationService = new DesignationService();
		List<Designation>  l                  = null;
		try {
			l = designationService.findSdpDesignations(Boolean.TRUE);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the select items designation
	 * @return the select items designation
	 */
	public List<Designation> getSelectedDesignationExcludeCA() {
		DesignationService designationService = new DesignationService();
		List<Designation>  l                  = null;
		try {
			l = designationService.allDesignationExcludeCA();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<NonCreditBearingIntervationTitle> getSelectItemsNonCreditBearingIntervationTitle() {
		NonCreditBearingIntervationTitleService service = new NonCreditBearingIntervationTitleService();
		List<NonCreditBearingIntervationTitle>  l       = null;
		try {
			l = service.allNonCreditBearingIntervationTitle();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<ProviderClass> getSelectItemsProviderClassExUnknownAndInterrim() {
		ProviderClassService levelsService = new ProviderClassService();
		List<ProviderClass>  l             = null;
		try {
			l = levelsService.allProviderClassExUnknownAndInterrim();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<DesignatedTrade> getSelectedDesignatedTrade() {
		DesignatedTradeService designatetTradeService = new DesignatedTradeService();
		List<DesignatedTrade>  l                      = null;
		try {
			l = designatetTradeService.allDesignatedTrade();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<DesignatedTradeLevel> getSelectedDesignatedTradeLevel() {
		DesignatedTradeLevelService designatedTradeLevelService = new DesignatedTradeLevelService();
		List<DesignatedTradeLevel>  l                           = null;
		try {
			l = designatedTradeLevelService.allDesignatedTradeLevel();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<DesignatedTradeType> getSelectedDesignatedTradeType() {
		DesignatedTradeTypeService designatedTradeTypeService = new DesignatedTradeTypeService();
		List<DesignatedTradeType>  l                          = null;
		try {
			l = designatedTradeTypeService.allDesignatedTradeType();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Seta> getSeta() {
		SetaService setaService = new SetaService();
		List<Seta>  l           = null;
		try {
			l = setaService.allSeta();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<AetProgrammeLevel> getAetProgrammeLevel() {
		AetProgrammeLevelService aetProgrammeLevelService = new AetProgrammeLevelService();
		List<AetProgrammeLevel>  l                        = null;
		try {
			l = aetProgrammeLevelService.allAetProgrammeLevel();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<FinancialYears> getFinancialYears() {
		FinancialYearsService financialYearsService = new FinancialYearsService();
		List<FinancialYears>  l                     = null;
		try {
			l = financialYearsService.allFinancialYears();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<NsdpReportConfig> getNsdpReportConfig() {
		NsdpReportConfigService nsdpReportConfigService = new NsdpReportConfigService();
		List<NsdpReportConfig>  l                       = null;
		try {
			l = nsdpReportConfigService.allNsdpReportConfig();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<SdpType> getSdpTypeList() {
		SdpTypeService sdpTypeService = new SdpTypeService();
		List<SdpType>  l              = null;
		try {
			l = sdpTypeService.allSdpType();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<SdpType> getSdpTypeListExcludePrimary() {
		SdpTypeService sdpTypeService = new SdpTypeService();
		List<SdpType>  l              = null;
		try {
			l = new ArrayList<>();
			List<SdpType> all = sdpTypeService.allSdpType();
			for (SdpType sdpType : all) {
				if (!sdpType.getId().equals(HAJConstants.PRIMARY_SDP_ID)) {
					l.add(sdpType);
				}
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<SizeOfCompany> getSizeOfCompany() {
		SizeOfCompanyService lookupsService = new SizeOfCompanyService();
		List<SizeOfCompany>  l              = null;
		try {
			l = lookupsService.allSizeOfCompany();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<StrategicPriorities> getStrategicPriorities() {
		StrategicPrioritiesService lookupsService = new StrategicPrioritiesService();
		List<StrategicPriorities>  l              = null;
		try {
			l = lookupsService.allStrategicPriorities();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
}