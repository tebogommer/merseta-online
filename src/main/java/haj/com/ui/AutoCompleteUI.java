package haj.com.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.constants.HAJConstants;
import haj.com.entity.Company;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.Municipality;
import haj.com.entity.OfoCodes;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.lookup.AppraisalCategories;
import haj.com.entity.lookup.AppraisalCategoryCode;
import haj.com.entity.lookup.AreaForImprovement;
import haj.com.entity.lookup.Chamber;
import haj.com.entity.lookup.DesignatedTrade;
import haj.com.entity.lookup.DesignatedTradeLevel;
import haj.com.entity.lookup.DisabilityRating;
import haj.com.entity.lookup.DisabilityStatus;
import haj.com.entity.lookup.EmploymentType;
import haj.com.entity.lookup.Equity;
import haj.com.entity.lookup.Funding;
import haj.com.entity.lookup.Gender;
import haj.com.entity.lookup.Institution;
import haj.com.entity.lookup.InstitutionType;
import haj.com.entity.lookup.InterventionLevel;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.Language;
import haj.com.entity.lookup.Learnership;
import haj.com.entity.lookup.MaritalStatus;
import haj.com.entity.lookup.Nationality;
import haj.com.entity.lookup.NonCreditBearingIntervationTitle;
import haj.com.entity.lookup.NqfLevels;
import haj.com.entity.lookup.OccupationCategory;
import haj.com.entity.lookup.PreviousSchools;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.QualificationType;
import haj.com.entity.lookup.SICCode;
import haj.com.entity.lookup.STATSSAAreaCode;
import haj.com.entity.lookup.ScarcityReason;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.Tool;
import haj.com.entity.lookup.ToolCategory;
import haj.com.entity.lookup.ToolList;
import haj.com.entity.lookup.Town;
import haj.com.entity.lookup.TvetFetQualification;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;
import haj.com.service.CompanyLearnersService;
import haj.com.service.CompanyService;
import haj.com.service.DgVerificationService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.NqfLevelsService;
import haj.com.service.OfoCodesService;
import haj.com.service.UnitStandardsService;
import haj.com.service.UsersService;
import haj.com.service.lookup.AppraisalCategoriesService;
import haj.com.service.lookup.AppraisalCategoryCodeService;
import haj.com.service.lookup.AreaForImprovementService;
import haj.com.service.lookup.ChamberService;
import haj.com.service.lookup.DesignatedTradeLevelService;
import haj.com.service.lookup.DesignatedTradeService;
import haj.com.service.lookup.DisabilityRatingService;
import haj.com.service.lookup.DisabilityStatusService;
import haj.com.service.lookup.EmploymentTypeService;
import haj.com.service.lookup.EquityService;
import haj.com.service.lookup.FundingService;
import haj.com.service.lookup.GenderService;
import haj.com.service.lookup.InstitutionService;
import haj.com.service.lookup.InstitutionTypeService;
import haj.com.service.lookup.InterventionLevelService;
import haj.com.service.lookup.InterventionTypeService;
import haj.com.service.lookup.LanguageService;
import haj.com.service.lookup.LearnershipService;
import haj.com.service.lookup.MaritalStatusService;
import haj.com.service.lookup.NationalityService;
import haj.com.service.lookup.NonCreditBearingIntervationTitleService;
import haj.com.service.lookup.OccupationCategoryService;
import haj.com.service.lookup.PreviousSchoolsService;
import haj.com.service.lookup.QualificationService;
import haj.com.service.lookup.QualificationTypeService;
import haj.com.service.lookup.SICCodeService;
import haj.com.service.lookup.STATSSAAreaCodeService;
import haj.com.service.lookup.ScarcityReasonService;
import haj.com.service.lookup.SkillsProgramService;
import haj.com.service.lookup.SkillsSetService;
import haj.com.service.lookup.ToolCategoryService;
import haj.com.service.lookup.ToolListService;
import haj.com.service.lookup.ToolService;
import haj.com.service.lookup.TownService;
import haj.com.service.lookup.TvetFetQualificationService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class AutoCompleteUI.
 */
@ManagedBean(name = "autoCompleteUI")
@ViewScoped
public class AutoCompleteUI extends AbstractUI {

	/** System calendar pattern format. */
	public String CALENDER_PATTERN = "dd/MM/yyyy";
	
	/** System calendar pattern format. */
	public String CALENDER_PATTERN_DATE_AND_TIME = "dd/MM/yyyy HH:mm";
	
	/** System calendar pattern format. */
	public String CALENDER_PATTERN_DATE_AND_TIME_SECOND = "dd/MM/yyyy HH:mm:ss";
	
	private final String TELPHONE_FORMAT = HAJConstants.TELPHONE_FORMAT;
	private final String CELLPHONE_FORMAT = HAJConstants.CELLPHONE_FORMAT;
	private final String FAX_NUMBER_FORMAT = HAJConstants.FAX_NUMBER_FORMAT;

	
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
	 *
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {

	}

	public List<Company> completeCompany(String desc) {
		CompanyService equityService = new CompanyService();
		List<Company> l = null;
		try {
			l = equityService.findByNameOrLevyNum(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Company> completeActiveCompanyByNameOrLNum(String desc) {
		CompanyService equityService = new CompanyService();
		List<Company> l = null;
		try {
			l = equityService.findByActiveNameOrLevyNum(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<AreaForImprovement> completeAreaForImprovement(String desc) {
		AreaForImprovementService areaForImprovementService = new AreaForImprovementService();
		List<AreaForImprovement> l = null;
		try {
			l = areaForImprovementService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	
	
	public List<Wsp> completeCompanyWSP(String desc) {
		DgVerificationService equityService = new DgVerificationService();
		List<Wsp> l = null;
		try {
			l = equityService.findByWspByVerificationCompany(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Wsp> completeCompanyApprovedWSP(String desc) {
		DgVerificationService equityService = new DgVerificationService();
		List<Wsp> l = null;
		try {
			l = equityService.findByWspByVerificationCompanyWspApproved(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Wsp> completeCompanyApprovedWSPAndFinYear(String desc) {
		DgVerificationService equityService = new DgVerificationService();
		List<Wsp> l = null;
		try {
			l = equityService.findByWspByVerificationCompanyWspApprovedAndFinYear(desc, HAJConstants.DG_ALLOCATION_FOCUS_YEAR);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	//
	
	public List<Company> completeCompanyByNameOrRegNumber(String desc) {
		CompanyService equityService = new CompanyService();
		List<Company> l = null;
		try {
			l = equityService.findByNameOrRegNumber(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Complete equity.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Equity> completeEquity(String desc) {
		EquityService equityService = new EquityService();
		List<Equity> l = null;
		try {
			l = equityService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Complete equity without other and unknown.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Equity> completeEquityExcludeOtherAndUnkown(String desc) {
		EquityService equityService = new EquityService();
		List<Equity> l = null;
		try {
			l = equityService.findByNameEcludeOtherAndUnkown(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	/**
	 * Complete employment type.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<EmploymentType> completeEmploymentType(String desc) {
		EmploymentTypeService employmentTypeService = new EmploymentTypeService();
		List<EmploymentType> l = null;
		try {
			l = employmentTypeService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Complete nationality.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Nationality> completeNationality(String desc) {
		NationalityService nationalityService = new NationalityService();
		List<haj.com.entity.lookup.Nationality> l = null;
		try {
			l = nationalityService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Nationality> completeNationalityWithNonSelectable(String desc) {
		NationalityService nationalityService = new NationalityService();
		List<haj.com.entity.lookup.Nationality> l = null;
		try {
			l = nationalityService.findByNameExcludeNonSelectable(desc,true);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Complete nationality.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Language> completeLanguage(String desc) {
		LanguageService languageService = new LanguageService();
		List<haj.com.entity.lookup.Language> l = null;
		try {
			l = languageService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Complete gender.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Gender> completeGender(String desc) {
		GenderService genderService = new GenderService();
		List<haj.com.entity.lookup.Gender> l = null;
		try {
			l = genderService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Complete occupation category.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<OccupationCategory> completeOccupationCategory(String desc) {
		OccupationCategoryService occupationCategoryService = new OccupationCategoryService();
		List<OccupationCategory> l = null;
		try {
			l = occupationCategoryService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Complete scarcity reason.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<ScarcityReason> completeScarcityReason(String desc) {
		ScarcityReasonService scarcityReasonService = new ScarcityReasonService();
		List<ScarcityReason> l = null;
		try {
			l = scarcityReasonService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Complete intervention type.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<InterventionType> completeInterventionType(String desc) {
		InterventionTypeService interventionTypeService = new InterventionTypeService();
		List<InterventionType> l = null;
		try {
			l = interventionTypeService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Complete intervention level.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<InterventionLevel> completeInterventionLevel(String desc) {
		InterventionLevelService interventionLevelService = new InterventionLevelService();
		List<InterventionLevel> l = null;
		try {
			l = interventionLevelService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Complete fundings.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Funding> completeFundings(String desc) {
		FundingService fundingService = new FundingService();
		List<Funding> l = null;
		try {
			l = fundingService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Complete ofo codes.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<OfoCodes> completeOfoCodesTrade(String desc) {
		OfoCodesService ofoCodesService = new OfoCodesService();
		List<OfoCodes> l = null;
		try {
			l = ofoCodesService.findByTrade(desc, true);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<OfoCodes> completeOfoCodesTradeWpa(String desc) {
		OfoCodesService ofoCodesService = new OfoCodesService();
		List<OfoCodes> l = null;
		try {
			l = ofoCodesService.findByTradeWpaVersion(desc, true);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<OfoCodes> completeOfoCodes(String desc) {
		OfoCodesService ofoCodesService = new OfoCodesService();
		List<OfoCodes> l = null;
		try {
			l = ofoCodesService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<OfoCodes> completeOfoCodesAtr(String desc) {
		OfoCodesService ofoCodesService = new OfoCodesService();
		List<OfoCodes> l = null;
		try {
			l = ofoCodesService.findByNameAtr(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<OfoCodes> completeOfoCodesNoSpes(String desc) {
		OfoCodesService ofoCodesService = new OfoCodesService();
		List<OfoCodes> l = null;
		try {
			l = ofoCodesService.findByNameNoSpes(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<AppraisalCategories> completeAppraisalCategories(String desc) {
		AppraisalCategoriesService appraisalCategoriesYearService = new AppraisalCategoriesService();
		List<AppraisalCategories> l = null;
		try {
			l = appraisalCategoriesYearService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<AppraisalCategoryCode> completeAppraisalCategoryCode(String desc) {
		AppraisalCategoryCodeService appraisalCategoryCodeService = new AppraisalCategoryCodeService();
		List<AppraisalCategoryCode> l = null;
		try {
			l = appraisalCategoryCodeService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	/**
	 * Complete disability status.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<DisabilityStatus> completeDisabilityStatus(String desc) {
		DisabilityStatusService disabilityStatusService = new DisabilityStatusService();
		List<DisabilityStatus> l = new ArrayList<DisabilityStatus>();
		try {
			l = disabilityStatusService.findByName(desc);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<DisabilityStatus> completeDisabilityStatusCanSelect(String desc) {
		DisabilityStatusService disabilityStatusService = new DisabilityStatusService();
		List<DisabilityStatus> l = new ArrayList<DisabilityStatus>();
		try {
			l = disabilityStatusService.findByNameCanSelect(desc);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	/**
	 * Complete disability status exclude none.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<DisabilityStatus> completeDisabilityStatusExcludeNone(String desc) {
		DisabilityStatusService disabilityStatusService = new DisabilityStatusService();
		List<DisabilityStatus> l = new ArrayList<DisabilityStatus>();
		try {
			l = disabilityStatusService.completeDisabilityStatusExcludeNone(desc, 9L);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<DisabilityStatus> completeDisabilityCanSelect(String desc) {
		DisabilityStatusService disabilityStatusService = new DisabilityStatusService();
		List<DisabilityStatus> l = new ArrayList<DisabilityStatus>();
		try {
			l = disabilityStatusService.completeDisabilityCanSelect(desc, true);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<DisabilityRating> completeDisabilityRating(String desc) {
		DisabilityRatingService disabilityRatingService = new DisabilityRatingService();
		List<DisabilityRating> l = new ArrayList<DisabilityRating>();
		try {
			l = disabilityRatingService.findByName(desc);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Complete municipality.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Municipality> completeMunicipality(String desc) {
		AddressService addressService = new AddressService();
		List<Municipality> l = null;
		try {
			l = addressService.findMunicipalitiesAutocomplete(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Complete qualification type.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<QualificationType> completeQualificationType(String desc) {
		QualificationTypeService qualificationTypeService = new QualificationTypeService();
		List<QualificationType> l = null;
		try {
			l = qualificationTypeService.findQualificationTypeAutocomplete(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Learnership> completeLearnershipQualification(String desc) {
		LearnershipService learnershipPlanService = new LearnershipService();
		List<Learnership> l = null;
		try {
			l = learnershipPlanService.findAllQualification(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Learnership> completeLearnershipQualificationLastDateOfEnrollment(String desc) {
		LearnershipService learnershipPlanService = new LearnershipService();
		List<Learnership> l = null;
		try {
			l = learnershipPlanService.completeLearnershipQualificationLastDateOfEnrollment(desc,GenericUtility.getStartOfDay(getNow()));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	
	public List<Learnership> completeLearnerships(String desc) {
		LearnershipService learnershipPlanService = new LearnershipService();
		List<Learnership> l = null;
		try {
			l = learnershipPlanService.findAllLearnership(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Complete qualification.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Qualification> completeQualification(String desc) {
		QualificationService qualificationService = new QualificationService();
		List<Qualification> l = null;
		try {
			l = qualificationService.findQualificationAutocomplete(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<PreviousSchools> completePreviousSchools(String desc) {
		PreviousSchoolsService previousSchoolsService = new PreviousSchoolsService();
		List<PreviousSchools> l = null;
		try {
			l = previousSchoolsService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Qualification> completeMersetaQualification(String desc) {
		QualificationService qualificationService = new QualificationService();
		List<Qualification> l = null;
		try {
			l = qualificationService.findMersetaQualificationAutocomplete(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Qualification> completeQualificationDesignation(String desc) {
		QualificationService qualificationService = new QualificationService();
		List<Qualification> l = null;
		try {
			l = qualificationService.findQualificationAutocompleteDesignation(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<DesignatedTrade> completeDesignatedTrade(String desc) {
		DesignatedTradeService qualificationService = new DesignatedTradeService();
		List<DesignatedTrade> l = null;
		try {
			l = qualificationService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	/**
	 * Complete qualification.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Qualification> completeQualificationReview(String desc) {
		QualificationService qualificationService = new QualificationService();
		List<Qualification> l = null;
		try {
			l = qualificationService.findQualificationAutocompleteReview(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	/**
	 * Complete qualification.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Qualification> completeQualificationReAlignment(String desc) {
		QualificationService qualificationService = new QualificationService();
		List<Qualification> l = null;
		try {
			l = qualificationService.findQualificationAutocompleteReAlignment(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	

	/**
	 * Complete chamber.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Chamber> completeChamber(String desc) {
		ChamberService chamberService = new ChamberService();
		List<Chamber> l = null;
		try {
			l = chamberService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Chamber> completeChamberWithoutNonMersetaChamber(String desc) {
		ChamberService chamberService = new ChamberService();
		List<Chamber> l = null;
		try {
			l = chamberService.findByNameWithoutNonMersetaChamber(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	/**
	 * Complete SIC.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<SICCode> completeSIC(String desc) {
		SICCodeService sicCodeService = new SICCodeService();
		List<SICCode> l = null;
		try {
			l = sicCodeService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Complete institution type.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<InstitutionType> completeInstitutionType(String desc) {
		InstitutionTypeService institutionTypeService = new InstitutionTypeService();
		List<InstitutionType> l = null;
		try {
			l = institutionTypeService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Complete nqf levels.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<NqfLevels> completeNqfLevels(String desc) {
		NqfLevelsService nqfLevelsService = new NqfLevelsService();
		List<NqfLevels> l = null;
		try {
			l = nqfLevelsService.findNqfLevelsAutocomplete(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Complete unit standards.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<UnitStandards> completeUnitStandards(String desc) {
		UnitStandardsService unitStandardsService = new UnitStandardsService();
		List<UnitStandards> l = null;
		try {
			l = unitStandardsService.findByTitle(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<HostingCompanyEmployees> completeEmployee(String desc) {
		HostingCompanyEmployeesService unitStandardsService = new HostingCompanyEmployeesService();
		List<HostingCompanyEmployees> l = null;
		try {
			l = unitStandardsService.searchEmployee(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Users> completeUser(String desc) {
		UsersService unitStandardsService = new UsersService();
		List<Users> l = null;
		try {
			l = unitStandardsService.searchUser(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Users> completeHostingCompUser(String desc) {
		HostingCompanyEmployeesService service = new HostingCompanyEmployeesService();
		List<Users> l = null;
		try {
			l = service.searchUser(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Users> completeUserNotEmployee(String desc) {
		UsersService unitStandardsService = new UsersService();
		List<Users> l = null;
		try {
			l = unitStandardsService.searchUserNotEmployee(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Town> completeTown(String desc) {
		TownService unitStandardsService = new TownService();
		List<Town> l = null;
		try {
			l = unitStandardsService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<SkillsSet> completeSkillsSet(String desc) {
		SkillsSetService unitStandardsService = new SkillsSetService();
		List<SkillsSet> l = null;
		try {
			l = unitStandardsService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<SkillsSet> completeSkillsSetLinkedToMerseta(String desc) {
		SkillsSetService unitStandardsService = new SkillsSetService();
		List<SkillsSet> l = null;
		try {
			l = unitStandardsService.findByNameAndLinkedToSeta(desc, HAJConstants.HOSTING_MERSETA_ETQA);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<SkillsSet> completeSkillsSetNotLinkedToSeta(String desc) {
		SkillsSetService unitStandardsService = new SkillsSetService();
		List<SkillsSet> l = null;
		try {
			l = unitStandardsService.findByNameAndNotLinkedToSeta(desc, HAJConstants.HOSTING_MERSETA_ETQA);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<NonCreditBearingIntervationTitle> completeNonCreditBearingIntervationTitle(String desc) {
		NonCreditBearingIntervationTitleService nonCreditBearingIntervationTitleService = new NonCreditBearingIntervationTitleService();
		List<NonCreditBearingIntervationTitle> l = null;
		try {
			l = nonCreditBearingIntervationTitleService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<SkillsSet> completeSkillsSetNONMERSETA(String desc) {
		SkillsSetService unitStandardsService = new SkillsSetService();
		List<SkillsSet> l = null;
		try {
			l = unitStandardsService.findByNameNONMERSETA(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<SkillsProgram> completeSkillsProgram(String desc) {
		SkillsProgramService unitStandardsService = new SkillsProgramService();
		List<SkillsProgram> l = null;
		try {
			l = unitStandardsService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<SkillsProgram> completeSkillsProgramLinkedToMerseta(String desc) {
		SkillsProgramService unitStandardsService = new SkillsProgramService();
		List<SkillsProgram> l = null;
		try {
			l = unitStandardsService.findByNameAndETQA(desc, HAJConstants.HOSTING_MERSETA_ETQA);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<SkillsProgram> completeSkillsProgramNotLinkedToEtqa(String desc) {
		SkillsProgramService unitStandardsService = new SkillsProgramService();
		List<SkillsProgram> l = null;
		try {
			l = unitStandardsService.findByNameAndNotETQA(desc, HAJConstants.HOSTING_MERSETA_ETQA);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<SkillsProgram> completeNonMERSETASkillsProgram(String desc) {
		SkillsProgramService unitStandardsService = new SkillsProgramService();
		List<SkillsProgram> l = null;
		try {
			l = unitStandardsService.findByNameNonMERSETA(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Tool> completeTool(String desc) {
		ToolService unitStandardsService = new ToolService();
		List<Tool> l = null;
		try {
			l = unitStandardsService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Learnership> completeLearnership(String desc) {
		LearnershipService unitStandardsService = new LearnershipService();
		List<Learnership> l = null;
		try {
			l = unitStandardsService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<ToolCategory> completeToolCategory(String desc) {
		ToolCategoryService toolCatService = new ToolCategoryService();
		List<ToolCategory> l = null;
		try {
			l = toolCatService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Users> completeCompanyLearners(String desc) {
		CompanyLearnersService companyLearnersService = new CompanyLearnersService();
		List<Users> l = null;
		try {
			l = companyLearnersService.findRsaIdOrName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Qualification> completeHostingCompanyQualification(String desc) {
		QualificationService qualificationService = new QualificationService();
		List<Qualification> l = null;
		try {
			l = qualificationService.findMersetaQualificationAutocomplete(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Qualification> completeNonMerSETAQualification(String desc) {
		QualificationService qualificationService = new QualificationService();
		List<Qualification> l = null;
		try {
			l = qualificationService.findNonMersetaQualificationAutocomplete(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Qualification> completeNonMerSETAQualificationWithTrades(String desc) {
		QualificationService qualificationService = new QualificationService();
		List<Qualification> l = null;
		try {
			l = qualificationService.findNonMersetaQualificationAutocompleteWithTrades(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Qualification> findNonMersetaQualificationAutocompleteWithNoTrades(String desc) {
		QualificationService qualificationService = new QualificationService();
		List<Qualification> l = null;
		try {
			l = qualificationService.findNonMersetaQualificationAutocompleteWithNoTrades(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Qualification> completeQualificationAutocompleteExcludingTrades(String desc) {
		QualificationService qualificationService = new QualificationService();
		List<Qualification> l = null;
		try {
			l = qualificationService.findQualificationAutocompleteExcludingTrades(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Qualification> completeQCTOQualification(String desc) {
		QualificationService qualificationService = new QualificationService();
		List<Qualification> l = null;
		try {
			l = qualificationService.findQCTOQualificationAutocompleteBeforeRegistrationDate(desc,GenericUtility.getStartOfDay(getNow()));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Qualification> completeQCTOTradeTestCentreQualification(String desc) {
		QualificationService qualificationService = new QualificationService();
		List<Qualification> l = null;
		try {
			l = qualificationService.findQCTOTradeTestCentreQualBeforeRegDate(desc,GenericUtility.getStartOfDay(getNow()));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Qualification> completeMersetaQualificationBeforeLastEnrolmentDate(String desc) {
		QualificationService qualificationService = new QualificationService();
		List<Qualification> l = null;
		try {
			l = qualificationService.findMersetaQualificationAutocompleteBeforeRegistrationDate(desc, GenericUtility.getStartOfDay(getNow()));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	/**
	 * Complete qualification.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Qualification> completeQualificationBeforeLastEnrolmentDate(String desc) {
		QualificationService qualificationService = new QualificationService();
		List<Qualification> l = null;
		try {
			l = qualificationService.findQualificationAutocompleteBeforeLastEnrolmentDate(desc, GenericUtility.getStartOfDay(getNow()));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Qualification> completeQualBeforeLastEnrolmentDateAndTrade(String desc) {
		QualificationService qualificationService = new QualificationService();
		List<Qualification> l = null;
		try {
			l = qualificationService.findQualBeforeLastEnrolmentDateAndTrade(desc, GenericUtility.getStartOfDay(getNow()));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	
	
	/**
	 * Complete qualification.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Qualification> completeNonTradeQualBeforeLastEnrolmentDate(String desc) {
		QualificationService qualificationService = new QualificationService();
		List<Qualification> l = null;
		try {
			l = qualificationService.completeNonTradeQualBeforeLastEnrolmentDate(desc, GenericUtility.getStartOfDay(getNow()));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Qualification> completeQualificationEnrolmentDateAndIsTrade(String desc) {
		QualificationService qualificationService = new QualificationService();
		List<Qualification> l = null;
		try {
			l = qualificationService.completeQualificationEnrolmentDateAndIsTrade(desc, GenericUtility.getStartOfDay(getNow()));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Qualification> completeQualificationEnrolmentDateAndIsOccupationalCertificate(String desc) {
		QualificationService qualificationService = new QualificationService();
		List<Qualification> l = null;
		try {
			l = qualificationService.completeQualificationEnrolmentDateAndIsOccupationalCertificate(desc, GenericUtility.getStartOfDay(getNow()));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Qualification> completeQualificationTrade(String desc) {
		QualificationService qualificationService = new QualificationService();
		List<Qualification> l = null;
		try {
			l = qualificationService.completeQualificationTrade(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Qualification> completeQualificationTradeWPARequired(String desc) {
		QualificationService qualificationService = new QualificationService();
		List<Qualification> l = null;
		try {
			l = qualificationService.completeQualificationTradeWPARequired(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<ToolList> completeToolList(String desc) {
		ToolListService toolListService = new ToolListService();
		List<ToolList> l = null;
		try {
			l = toolListService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Qualification> completeQualificationLearningProgrammes(String desc) {
		QualificationService qualificationService = new QualificationService();
		List<Qualification> l = null;
		try {
			l = qualificationService.completeQualificationLearningProgrammes(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	
	public List<STATSSAAreaCode> completeSTATSSAAreaCode(String desc) {
		STATSSAAreaCodeService qualificationService = new STATSSAAreaCodeService();
		List<STATSSAAreaCode> l = null;
		try {
			l = qualificationService.findByName(desc);
		} catch (ValidationException e) {
			e.printStackTrace();
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<MaritalStatus> completeMaritalStatus(String desc) {
		MaritalStatusService maritalStatusService = new MaritalStatusService();
		List<MaritalStatus> l = null;
		try {
			l = maritalStatusService.findByNameAspopulated(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	/**
	 * Complete unit standards.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<UnitStandards> completeUnitStandardsBeforeLastEnrolmentDate(String desc) {
		UnitStandardsService unitStandardsService = new UnitStandardsService();
		List<UnitStandards> l = null;
		try {
			l = unitStandardsService.findByTitleAndBeforeLastEnrolmentDate(desc, GenericUtility.getStartOfDay(getNow()));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	
	/**
	 * Complete qualification.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<DesignatedTradeLevel> completeDesignatedTradeLevel(String desc) {
		List<DesignatedTradeLevel> l = null;
		try {
			l = DesignatedTradeLevelService.instance().findDesignatedTradeLevelAutocomplete(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Institution> completeInstitution(String desc) {
		InstitutionService institutionService = new InstitutionService();
		List<Institution> l = null;
		try {
			l = institutionService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<TvetFetQualification> completeTvetFetQualification(String desc) {
		TvetFetQualificationService tvetFetQualificationService = new TvetFetQualificationService();
		List<TvetFetQualification> l = null;
		try {
			l = tvetFetQualificationService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	

	public String getCALENDER_PATTERN() {
		return CALENDER_PATTERN;
	}

	public void setCALENDER_PATTERN(String cALENDER_PATTERN) {
		CALENDER_PATTERN = cALENDER_PATTERN;
	}

	public String getCALENDER_PATTERN_DATE_AND_TIME() {
		return CALENDER_PATTERN_DATE_AND_TIME;
	}

	public void setCALENDER_PATTERN_DATE_AND_TIME(String cALENDER_PATTERN_DATE_AND_TIME) {
		CALENDER_PATTERN_DATE_AND_TIME = cALENDER_PATTERN_DATE_AND_TIME;
	}

	public String getCALENDER_PATTERN_DATE_AND_TIME_SECOND() {
		return CALENDER_PATTERN_DATE_AND_TIME_SECOND;
	}

	public void setCALENDER_PATTERN_DATE_AND_TIME_SECOND(String cALENDER_PATTERN_DATE_AND_TIME_SECOND) {
		CALENDER_PATTERN_DATE_AND_TIME_SECOND = cALENDER_PATTERN_DATE_AND_TIME_SECOND;
	}

	public String getTELPHONE_FORMAT() {
		return TELPHONE_FORMAT;
	}

	public String getCELLPHONE_FORMAT() {
		return CELLPHONE_FORMAT;
	}

	public String getFAX_NUMBER_FORMAT() {
		return FAX_NUMBER_FORMAT;
	}

	
}
