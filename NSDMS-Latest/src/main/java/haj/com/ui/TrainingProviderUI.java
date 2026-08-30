package haj.com.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.Address;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.AssessorModeratorCompanySites;
import haj.com.entity.Company;
import haj.com.entity.CompanyQualifications;
import haj.com.entity.CompanyUnitStandard;
import haj.com.entity.Doc;
import haj.com.entity.NonSetaCompany;
import haj.com.entity.SDPCompany;
import haj.com.entity.SitesSme;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingProviderDocParent;
import haj.com.entity.TrainingProviderSkillsProgramme;
import haj.com.entity.TrainingProviderSkillsSet;
import haj.com.entity.TrainingSite;
import haj.com.entity.Users;
import haj.com.entity.enums.AccreditationApplicationTypeEnum;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AssessorModType;
import haj.com.entity.enums.AssessorModeratorAppTypeEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.SDPApplicationType;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.AccreditationStatus;
import haj.com.entity.lookup.Designation;
import haj.com.entity.lookup.DisabilityRating;
import haj.com.entity.lookup.Etqa;
import haj.com.entity.lookup.LegacyProviderAccreditation;
import haj.com.entity.lookup.LegacyProviderLearnership;
import haj.com.entity.lookup.LegacyProviderQualification;
import haj.com.entity.lookup.LegacyProviderSkillsProgramme;
import haj.com.entity.lookup.LegacyProviderUnitStandard;
import haj.com.entity.lookup.ProviderClass;
import haj.com.entity.lookup.ProviderType;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;
import haj.com.service.AssessorModeratorApplicationService;
import haj.com.service.AssessorModeratorCompanySitesService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.HostingCompanyService;
import haj.com.service.SDPCompanyService;
import haj.com.service.TrainingProviderApplicationService;
import haj.com.service.TrainingSiteService;
import haj.com.service.UnitStandardsService;
import haj.com.service.UsersService;
import haj.com.service.lookup.AccreditationStatusService;
import haj.com.service.lookup.DesignationService;
import haj.com.service.lookup.DisabilityRatingService;
import haj.com.service.lookup.EtqaService;
import haj.com.service.lookup.LegacyProviderLearnershipService;
import haj.com.service.lookup.LegacyProviderQualificationService;
import haj.com.service.lookup.LegacyProviderSkillsProgrammeService;
import haj.com.service.lookup.LegacyProviderUnitStandardService;
import haj.com.service.lookup.ProviderClassService;
import haj.com.service.lookup.ProviderStatusService;
import haj.com.service.lookup.ProviderTypeService;
import haj.com.service.lookup.QualificationService;
import haj.com.service.lookup.SaqaUsQualificationService;
import haj.com.service.lookup.SdpTypeService;
import haj.com.service.lookup.SkillsProgramService;
import haj.com.service.lookup.SkillsSetService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class TrainingProviderUI.
 */
@ManagedBean(name = "trainingproviderUI")
@ViewScoped
public class TrainingProviderUI extends AbstractUI {

	/** The service. */
	private UsersService service = new UsersService();

	/** The company list. */
	private List<Company> companyList = null;

	/** The company. */
	private Company company;

	/** The NonSetaCompany */
	private NonSetaCompany nonSetaCompany = new NonSetaCompany();

	/** The form user. */
	private Users formUser;

	/** The TP contact person. */
	private Users contactPerson;

	/** The TP contact person. */
	private Users tempContactPerson;

	/** The TP contact person. */
	private ArrayList<Users> contactPersonList = new ArrayList<Users>();

	/** The TP contact person. */
	private Users facilitatorAssessor;

	private Users tempFacilitatorAssessor;

	/** The TP contact person. */
	private ArrayList<Users> facilitatorAssessorList = new ArrayList<Users>();

	/** The passport number. */
	private boolean passportNumber = false;

	/** The company service. */
	private CompanyService companyService = new CompanyService(getResourceBundle());

	/** The company users service. */
	private CompanyUsersService companyUsersService = new CompanyUsersService();

	/** The qualification service. */
	private QualificationService qualificationService = new QualificationService();

	/** The unit standards service. */
	private UnitStandardsService unitStandardsService = new UnitStandardsService();

	/** The doc. */
	private Doc doc;

	/** The qualification list. */
	private List<Qualification> qualificationList;

	/** The qualification. */
	private Qualification qualification;

	/** The qualification. */
	private Qualification learningProgrammeQualification;

	/** The qualification. */
	private List<Qualification> learningProgrammeQualificationList;

	/** The hosting company service. */
	private HostingCompanyService hostingCompanyService = new HostingCompanyService();

	/** The unit standards. */
	private List<UnitStandards> unitStandards;

	/** The unit standard. */
	private UnitStandards unitStandard;

	/** The search user passport or id UI. */
	@ManagedProperty(value = "#{searchUserPassportOrIdUI}")
	private SearchUserPassportOrIdUI searchUserPassportOrIdUI;

	/** The search company UI. */
	@ManagedProperty(value = "#{searchCompanyUI}")
	private SearchCompanyUI searchCompanyUI;

	private TrainingProviderApplication trainingProviderApplication = new TrainingProviderApplication();

	private UsersService usersService = new UsersService();

	/** First time applicants boolean flag */
	private boolean firstTimeApplicant;

	/** already accredited boolean flag */
	private boolean alreadyAccredited;

	/** Training Provider Postal Address */
	private Address tpPostalAddress = new Address();

	/** Training Provider Residential Address */
	private Address tpResidentialAddress = new Address();

	/** Training Provider Postal Address */
	private Address tpNonSetaPostalAddress = new Address();

	/** Training Provider Residential Address */
	private Address tpNonSetaResidentialAddress = new Address();

	private boolean copyAddress;

	/** Training provider Skills Program */
	private SkillsProgram skillsProgram;

	private boolean searchContactPerson;

	private boolean doneContactPerson;

	private boolean doneSMEReg;

	private SitesSme sitesSme;

	private List<SitesSme> sitesSmes;

	private AddressService addressService = new AddressService();

	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();

	private SkillsProgramService skillsProgramService = new SkillsProgramService();

	private DesignationService designationService = new DesignationService();

	private ArrayList<SkillsProgram> skillsProgramList = new ArrayList<>();

	private ArrayList<SkillsSet> skillsSetList = new ArrayList<>();
	private SkillsSet skillsSet;

	private YesNoEnum levyNonLevySDP;

	private SDPApplicationType employerType;

	private String urlAction = "registertrainingprovider.jsf?faces-redirect=true&app-type=####";

	private boolean skip;
	private String docNode;
	private EtqaService etqaService = new EtqaService();
	private ProviderStatusService providerStatusService = new ProviderStatusService();
	
	private Boolean validiationPassed = true;
	
	private String validiationErrorsLegacySdp = "";

	private List<CompanyUnitStandard> companyUnitStandardList = new ArrayList<>();
	// companyService.createTPAndCompanyAndSendTask(unitStandards,
	// qualificationList, company, formUser, true,
	// ConfigDocProcessEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL,
	// hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA),trainingProviderApplication,tpResidentialAddress,
	// tpPostalAddress,contactPersons,skillsProgramList,skillsSetList);

	/* Qualification Documents Parent */
	TrainingProviderDocParent docParent;
	List<TrainingProviderDocParent> docParentList = new ArrayList<>();
	List<CompanyQualifications> companyQualificationsList = new ArrayList<>();
	List<TrainingProviderSkillsProgramme> tpSkillsProgramme = new ArrayList<>();
	List<TrainingProviderSkillsSet> tpSkillsSetList = new ArrayList<>();

	/** The Constant telephone format. */
	public String TELPHONE_FORMAT = HAJConstants.TELPHONE_FORMAT;
	/** The Constant cell phone format. */
	public String CELLPHONE_FORMAT = HAJConstants.CELLPHONE_FORMAT;
	/** The Constant FAX number format. */
	public String FAX_NUMBER_FORMAT = HAJConstants.FAX_NUMBER_FORMAT;

	private String docFor;

	private List<SkillsProgram> skillsProgramByUnitStandardsList = new ArrayList<>();
	private int resultsForSkillsProgramFound = 0;

	private Long maxFileSize = HAJConstants.MAX_FILE_SIZE;

	private LegacyProviderQualificationService legacyProviderQualificationService = new LegacyProviderQualificationService();
	private List<LegacyProviderQualification> legacyProviderQualificationList = new ArrayList<>();

	private LegacyProviderLearnershipService legacyProviderLearnershipService = new LegacyProviderLearnershipService();
	private List<LegacyProviderLearnership> legacyProviderLearnershipList = new ArrayList<>();

	private LegacyProviderSkillsProgrammeService legacyProviderSkillsProgrammeService = new LegacyProviderSkillsProgrammeService();
	private List<LegacyProviderSkillsProgramme> legacyProviderSkillsProgrammeList = new ArrayList<>();
	private List<LegacyProviderSkillsProgramme> legacyProviderSkillsProgrammeSkillsSetList = new ArrayList<>();

	private LegacyProviderUnitStandardService legacyProviderUnitStandardService = new LegacyProviderUnitStandardService();
	private List<LegacyProviderUnitStandard> legacyProviderUnitStandardList = new ArrayList<>();

	private Boolean qualUplaoaded;
	private Boolean usUplaoaded;
	private Boolean spUplaoaded;
	private Boolean ssUplaoaded;
	private Boolean doneQualification;
	private Boolean showSubmit;
	private Boolean edit;
	
	private String validiationErrors = "";
	
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
	
	private Boolean primarySdpLinked = false;
	private Boolean applicationForSiteDisplay = false;
	private Boolean linkSiteToApplication = false;
	private Boolean newSite = false;
	
	private String setmisValidiationHoldingCompany = "";
	private TrainingSite assignedTrainingSite = null;
	private TrainingSiteService trainingSiteService = new TrainingSiteService();
	private LazyDataModel<TrainingSite> trainingSiteDataModel;
	private String setmisValidiationSite = "";
	
	private SDPCompany newSdpCompany = null;
	private SDPCompanyService sdpCompanyService = new SDPCompanyService();
	private LazyDataModel<SDPCompany> sdpCompanyDataModel;
	private List<SDPCompany> newSDPCompanyList = new ArrayList<>();
	private String setmisValidiationSdp = "";
	
	private AssessorModeratorCompanySites newAssModLink = null;
	private AssessorModeratorCompanySitesService assessorModeratorCompanySitesService = new AssessorModeratorCompanySitesService();
	private LazyDataModel<AssessorModeratorCompanySites> assessorModeratorCompanySitesDataModel;
	private List<AssessorModeratorCompanySites> newAssessorModeratorCompanySitesList = new ArrayList<>();
	
	
	
	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		try {
			runInit();
			// TrainingProviderApplicationService trainingProviderApplicationService = new
			// TrainingProviderApplicationService();
			// trainingProviderApplicationService.allTrainingProviderApplication();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void searchNotDonde() {
		company = null;
		nonSetaCompany = new NonSetaCompany();
		tpPostalAddress = new Address();
		tpResidentialAddress = new Address();
		clearQualificationDetails();
		applicationForSiteDisplay = false;
		newSite = false;
		linkSiteToApplication = false;
		assignedTrainingSite = null;
		primarySdpLinked = false;
		setmisValidiationSite = "";
		setmisValidiationSdp = "";
		newSdpCompany = null;
		newSDPCompanyList.clear();
		newAssessorModeratorCompanySitesList.clear();
	}

	public void clearQualificationDetails() {
		qualificationList.clear();
		unitStandards.clear();
		skillsProgramList.clear();
		skillsSetList.clear();
		trainingProviderApplication.setUseSkillProgrammeRoute(null);
		trainingProviderApplication.setSkillsProgram(null);
		companyQualificationsList = new ArrayList<>();
		companyUnitStandardList = new ArrayList<>();
		tpSkillsProgramme = new ArrayList<>();
		tpSkillsSetList = new ArrayList<>();

	}

	public void storeSupportingDoc(FileUploadEvent event) {
		try {
			Doc doc = new Doc();
			docParent = new TrainingProviderDocParent();
			doc.setNote(docNode);
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(getSessionUI().getActiveUser());
			docParent.setDoc(doc);
			saveDocument();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void addTPSkillsSet() {
		ssUplaoaded = true;
		tpSkillsSetList.clear();
		try {
			if (docParent.getDoc() == null || docParent.getDoc().getData() == null) {
				throw new Exception("Upload suporting documents");
			}
			docParentList.add(docParent);
			for (SkillsSet ss : skillsSetList) {
				TrainingProviderSkillsSet tpSS = new TrainingProviderSkillsSet();
				tpSS.setSkillsSet(ss);
				tpSS.setTrainingProviderDocParent(docParent);
				tpSkillsSetList.add(tpSS);
			}
			docParent = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void addTPSkillsProgreme() {
		spUplaoaded = true;
		tpSkillsProgramme.clear();
		try {
			if (docParent.getDoc() == null || docParent.getDoc().getData() == null) {
				throw new Exception("Upload suporting documents");
			}
			docParentList.add(docParent);
			for (SkillsProgram sp : skillsProgramList) {
				TrainingProviderSkillsProgramme stSP = new TrainingProviderSkillsProgramme();
				stSP.setSkillsProgram(sp);
				stSP.setTrainingProviderDocParent(docParent);
				tpSkillsProgramme.add(stSP);
			}
			docParent = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void addCmpanyUnitStandardBetch() {
		usUplaoaded = true;
		companyUnitStandardList.clear();
		try {
			if (docParent.getDoc() == null || docParent.getDoc().getData() == null) {
				throw new Exception("Upload suporting documents");
			}
			docParentList.add(docParent);
			for (UnitStandards us : unitStandards) {
				CompanyUnitStandard compUS = new CompanyUnitStandard();
				compUS.setUnitStandard(us);
				compUS.setTrainingProviderDocParent(docParent);
				if (us.getQualification() != null) {
					compUS.setForQualification(us.getQualification());
				}
				companyUnitStandardList.add(compUS);
			}
			docParent = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void addQualificationBetch() {
		qualUplaoaded = true;
		companyQualificationsList.clear();
		try {
			if (docParent.getDoc() == null || docParent.getDoc().getData() == null) {
				throw new Exception("Upload suporting documents");
			}
			docParentList.add(docParent);
			for (Qualification qual : qualificationList) {
				CompanyQualifications companyQualifications = new CompanyQualifications();
				companyQualifications.setQualification(qual);
				companyQualifications.setTrainingProviderDocParent(docParent);
				companyQualificationsList.add(companyQualifications);
			}
			docParent = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void saveDocument() {
		if (docFor.equalsIgnoreCase("Qualifications")) {
			addQualificationBetch();
		} else if (docFor.equalsIgnoreCase("Unit Standards")) {
			addCmpanyUnitStandardBetch();
		} else if (docFor.equalsIgnoreCase("Skills Programme")) {
			addTPSkillsProgreme();
		} else if (docFor.equalsIgnoreCase("Skills Set")) {
			addTPSkillsSet();
		}
	}

	/* Training provider Application Wizard */

	public String onFlowProcess(FlowEvent event) {
		String nextSep = event.getNewStep();
		String oldStep = event.getOldStep();
		try {

			if (nextSep.equalsIgnoreCase("companyDetails")) {
				checkFeildsProvided();
			} else if (nextSep.equalsIgnoreCase("contactPersons")) {
				doneCompanyBit();
			} else if (nextSep.equalsIgnoreCase("assAndMod")) {
				doneAddingContactPerson();
			} else if (nextSep.equalsIgnoreCase("qualificationDetails")) {
				doneSMERegContinue();
			}
			if (skip) {
				skip = false;
				return "confirm";
			} else {
				return nextSep;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
			return oldStep;
		}
	}

	/* End Of Training provider Application Wizard */

	/**
	 * Initialize method to get all Company and prepare a for a create of a new
	 * Company.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see Company
	 */
	private void runInit() throws Exception {
		getSearchUserPassportOrIdUI().setObject(this);
		getSearchCompanyUI().setObject(this);
		prepareNew();
		companyInfo();
		searchContactPerson = false;
		if (super.getParameter("app-type", false) != null) {
			if (super.getParameter("app-type", false).equals("0")) {
				employerType = SDPApplicationType.LevyPayingEntity;
			} else if (super.getParameter("app-type", false).equals("1")) {
				employerType = SDPApplicationType.NonLevyPayingEntity;
			}
		}
	}

	public void prepareAction() {
		int index = 0;
		if (employerType != null) {
			index = employerType.ordinal();
		}
		urlAction = urlAction.replace("####", "" + index);
	}

	public void addSiteSME() {
		try {
			validateSiteSME();
			sitesSmes.add(sitesSme);
			sitesSme = new SitesSme();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void doneSMERegContinue() {
		try {
			doneSMEReg = true;
			if (trainingProviderApplication.getLegacyProviderAccreditation() != null) {
				loardQualification();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void loardQualification() throws Exception {
		legacyProviderQualificationList = legacyProviderQualificationService.findByAccreditationNoAndQualNotNull(trainingProviderApplication.getLegacyProviderAccreditation().getAccreditationNo());
		legacyProviderLearnershipList = legacyProviderLearnershipService.findByAccreditationNoAndLearnershipIsNotNull(trainingProviderApplication.getLegacyProviderAccreditation().getAccreditationNo());
		legacyProviderSkillsProgrammeList = legacyProviderSkillsProgrammeService.findByAccreditationNoAndSkillProgrameIsNotNull(trainingProviderApplication.getLegacyProviderAccreditation().getAccreditationNo());
		legacyProviderSkillsProgrammeSkillsSetList = legacyProviderSkillsProgrammeService.findByAccreditationNoAndSkillSetIsNotNull(trainingProviderApplication.getLegacyProviderAccreditation().getAccreditationNo());
		legacyProviderUnitStandardList = legacyProviderUnitStandardService.findByAccreditationNoAndUnitStandardIsNotNull(trainingProviderApplication.getLegacyProviderAccreditation().getAccreditationNo());
		showSubmit = false;
		if (legacyProviderQualificationList.size() > 0 || legacyProviderUnitStandardList.size() > 0 || legacyProviderSkillsProgrammeList.size() > 0 || legacyProviderLearnershipList.size() > 0 || legacyProviderSkillsProgrammeSkillsSetList.size() > 0) {
			Boolean atLeastOneQualToBeProcessed = false;
			for (LegacyProviderQualification qual : legacyProviderQualificationList) {
				if (qual.getQualificationExpired() != null && qual.getQualificationExpired() == false) {
					atLeastOneQualToBeProcessed = true;
					break;
				}
			}
			if (!atLeastOneQualToBeProcessed) {
				for (LegacyProviderUnitStandard qual : legacyProviderUnitStandardList) {
					if (qual.getUnitStandardExpired() != null && qual.getUnitStandardExpired() == false) {
						atLeastOneQualToBeProcessed = true;
						break;
					}
				}
			}
			if (!atLeastOneQualToBeProcessed) {
				for (LegacyProviderSkillsProgramme qual : legacyProviderSkillsProgrammeList) {
					if (qual.getQualificationExpired() != null && qual.getQualificationExpired() == false) {
						atLeastOneQualToBeProcessed = true;
						break;
					}
				}
			}
			if (!atLeastOneQualToBeProcessed) {
				for (LegacyProviderSkillsProgramme qual : legacyProviderSkillsProgrammeSkillsSetList) {
					if (qual.getQualificationExpired() != null && qual.getQualificationExpired() == false) {
						atLeastOneQualToBeProcessed = true;
						break;
					}
				}
			}
			if (!atLeastOneQualToBeProcessed) {
				for (LegacyProviderLearnership qual : legacyProviderLearnershipList) {
					if (qual.getQualificationExpired() != null && qual.getQualificationExpired() == false) {
						atLeastOneQualToBeProcessed = true;
						break;
					}
				}
			}
			if (!atLeastOneQualToBeProcessed) {
				showSubmit = false;
				throw new Exception("Your application cannot be processed because all your qualifications have expired");
			}
			showSubmit = true;
		} else {
			showSubmit = false;
			throw new Exception("No Qualifications details found, please conatct support");
		}
	}

	public void removeSME() {
		try {
			sitesSmes.remove(sitesSme);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void clearSiteSME() {
		sitesSme = new SitesSme();
	}

	public void validateSiteSME() throws Exception {
		for (SitesSme sme : sitesSmes) {
			if (sme.getIdentityNumber().equals(sitesSme.getIdentityNumber())) {
				clearSiteSME();
				throw new Exception("Site SME with Identity Number " + sitesSme.getIdentityNumber() + " has been added aready");
			}
		}

	}

	/**
	 * Get all Company for data table.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see Company
	 */
	public void companyInfo() throws Exception {

	}

	// facilitatorAssessor;facilitatorAssessorList

	public void clearFacilitatorAssessor() {
		facilitatorAssessor = new Users();
		newAssModLink = null;
	}

	public void addFacilitatorAssessor() {
		try {
			tempFacilitatorAssessor = null;
			if (facilitatorAssessor != null) {
				// trainingProviderApplicationService.avialabilityOfFacilitorAssModValidation(facilitatorAssessor);
				if (addUsersValidation()) {
					facilitatorAssessorList.add(facilitatorAssessor);
					clearFacilitatorAssessor();
				} else {
					throw new Exception("Contact person already added to the list");
				}
			} else {
				throw new Exception("Please add contact person");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void addFacilitatorAssessorVersionTwo() {
		try {
			// check if user already assigned 
			if (assignedTrainingSite == null) {
				if (assessorModeratorCompanySitesService.countByUserHoldingCompanyAssModType(newAssModLink.getAssessorModerator().getId(), company.getId(), newAssModLink.getAssessorModType()) > 0) {
					throw new Exception("User already assigned to company with type. Please select a different user or type.");
				}
			} else {
				if (assignedTrainingSite.getId() != null) {
					if (assessorModeratorCompanySitesService.countByUserTrainingSiteAssModType(newAssModLink.getAssessorModerator().getId(), assignedTrainingSite.getId(), newAssModLink.getAssessorModType()) > 0) {
						throw new Exception("User already assigned to company with type. Please select a different user or type.");
					}
				}
			}
			
			// check if user already assigned to list
			boolean alreadInList = false;
			if (!newAssessorModeratorCompanySitesList.isEmpty()) {
				for (AssessorModeratorCompanySites assessorModeratorCompanySites : newAssessorModeratorCompanySitesList) {
					if (assessorModeratorCompanySites.getAssessorModerator().getId().equals(newAssModLink.getAssessorModerator().getId())) {
						if (assessorModeratorCompanySites.getAssessorModType() == newAssModLink.getAssessorModType()) {
							alreadInList = true;
							break;
						}
					}
				}
			}
			
			if (alreadInList) {
				throw new Exception("User already assigned. Please select a different user or application type.");
			}
			newAssessorModeratorCompanySitesList.add(newAssModLink);
			addInfoMessage("Action Complete");
			clearFacilitatorAssessor();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public boolean addUsersValidation() {
		boolean add = true;
		if (facilitatorAssessorList.contains(facilitatorAssessor)) {
			for (Users u : facilitatorAssessorList) {
				if (u.getId().equals(facilitatorAssessor.getId())) {

					if (u.getAssessorModType() == facilitatorAssessor.getAssessorModType()) {
						add = false;
						break;
					}
				}

			}
		}
		return add;

	}

	public void prepareUpdateFacilitatorAssessor() {
		if (tempFacilitatorAssessor != null) {
			facilitatorAssessorList.add(tempFacilitatorAssessor);
		}
		facilitatorAssessorList.remove(facilitatorAssessor);
		tempFacilitatorAssessor = facilitatorAssessor;
	}

	public void removeFacilitatorAssessor() {
		try {
			facilitatorAssessorList.remove(facilitatorAssessor);
			clearFacilitatorAssessor();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void removeFacilitatorAssessorVersionTwo() {
		try {
			newAssessorModeratorCompanySitesList.remove(newAssModLink);
			clearFacilitatorAssessor();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void clearContactPerson() {
		newSdpCompany = null;
		contactPerson = new Users();
	}

	public void resetAddContactPerson() {
		contactPerson = new Users();
		doneContactPerson = false;
		doneSMEReg = false;
		newSdpCompany = null;
	}

	public void resetAddFacilitatorAssessor() {
		facilitatorAssessor = new Users();
		doneContactPerson = true;
		doneSMEReg = false;
		newAssModLink = null;
	}

	public void addContactPerson() {
		try {
			if (Boolean.TRUE.equals(userErrors(contactPerson) == null)) {
				tempContactPerson = null;
				if (contactPerson != null) {
					addUserEmailAndIDValidation(contactPerson, contactPersonList);
					if (contactPerson.getId() == null) {
						contactPersonList.add(contactPerson);
						clearContactPerson();
					} else {
						if (!contactPersonList.contains(contactPerson)) {
							contactPersonList.add(contactPerson);
							clearContactPerson();
						} else {
							throw new Exception("Contact person already added to the list");
						}
					}
				} else {
					throw new Exception("Please add contact person");
				}
			} else {
				addErrorMessage("Please correct Contact Person Information where applicable");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void addNewSdpLink() {
		try {
			// check if user already assigned. SAVED DATA 
			if (newSdpCompany.getSdp().getId() != null) {
				if (assignedTrainingSite != null) {
					if (assignedTrainingSite.getId() != null) {
						// check on site level 
						if (sdpCompanyService.countUserAssignedByTrainingSiteId(assignedTrainingSite.getId(), newSdpCompany.getSdp().getId()) > 0) {
							throw new Exception("User already assigned as company contact. Please provide a different user.");
						}
					}
				} else {
					if (sdpCompanyService.countUserAssignedByHoldingCompany(company.getId(), newSdpCompany.getSdp().getId()) > 0) {
						throw new Exception("User already assigned as company contact. Please provide a different user.");
					}
				}
			}
			// check if designation already used.
			if (assignedTrainingSite != null) {
				if (assignedTrainingSite.getId() != null) {
					// check on site level 
					if (sdpCompanyService.countSdpTypeByTrainingSiteId(assignedTrainingSite.getId(), newSdpCompany.getSdpType().getId()) > 0) {
						throw new Exception("Designation: " + newSdpCompany.getSdpType().getDescription() + " already assigned, please rectify");
					}
				}
			} else {
				if (sdpCompanyService.countSdpTypeByHoldingCompany(company.getId(), newSdpCompany.getSdpType().getId()) > 0) {
					throw new Exception("Designation: " + newSdpCompany.getSdpType().getDescription() + " already assigned, please rectify");
				}
			}
			if (Boolean.TRUE.equals(userErrors(newSdpCompany.getSdp()) == null)) {
				tempContactPerson = null;
				if (newSdpCompany.getSdp() != null) {
					// validate user email and if already in list
					validiateUserInfoSdpCompany(newSdpCompany, newSDPCompanyList);
					newSDPCompanyList.add(newSdpCompany);
					clearContactPerson();
					addInfoMessage("Add Complete");
				} else {
					throw new Exception("Please add contact person");
				}
			} else {
				addErrorMessage("Please correct Contact Person Information where applicable");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void removedSdpAssigned(){
		try {
			if (newSdpCompany != null) {
				newSDPCompanyList.remove(newSdpCompany);
				clearContactPerson();
				addWarningMessage("Contact Removed");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void addUserEmailAndIDValidation(Users user, ArrayList<Users> usersList) throws Exception {
		for (Users u : usersList) {
			usersService.validateEmailAddressByUser(user);
			if (user.getEmail().equalsIgnoreCase(u.getEmail())) {
				throw new Exception("Duplicate emails for " + user.getFirstName() + " " + user.getLastName() + " and " + u.getFirstName() + " " + u.getLastName() + ", please rectify");
			} else if (u.getRsaIDNumber() != null && !u.getRsaIDNumber().isEmpty() && user.getRsaIDNumber() != null && !user.getRsaIDNumber().isEmpty()) {
				if (u.getRsaIDNumber().equalsIgnoreCase(user.getRsaIDNumber())) {
					throw new Exception("Duplicate RSA ID numbers for " + user.getFirstName() + " " + user.getLastName() + " and " + u.getFirstName() + " " + u.getLastName() + ", please rectify");
				}

			} else if (u.getPassportNumber() != null && !u.getPassportNumber().isEmpty() && user.getPassportNumber() != null && !user.getPassportNumber().isEmpty()) {
				if (u.getPassportNumber().equalsIgnoreCase(user.getPassportNumber())) {
					throw new Exception("Duplicate Passport numbers for " + user.getFirstName() + " " + user.getLastName() + " and " + u.getFirstName() + " " + u.getLastName() + ", please rectify");
				}
			}
		}
	}
	
	public void validiateUserInfoSdpCompany(SDPCompany newSdpUser, List<SDPCompany> sdpCompanyList) throws Exception {
		for (SDPCompany sdpCompany : sdpCompanyList) {
			Users u = sdpCompany.getSdp();
			usersService.validateEmailAddressByUser(newSdpUser.getSdp());
			if (newSdpUser.getSdp().getEmail().equalsIgnoreCase(u.getEmail())) {
				throw new Exception("Duplicate emails for " + newSdpUser.getSdp().getFirstName() + " " + newSdpUser.getSdp().getLastName() + " and " + u.getFirstName() + " " + u.getLastName() + ", please rectify");
			} else if (u.getRsaIDNumber() != null && !u.getRsaIDNumber().isEmpty() && newSdpUser.getSdp().getRsaIDNumber() != null && !newSdpUser.getSdp().getRsaIDNumber().isEmpty()) {
				if (u.getRsaIDNumber().equalsIgnoreCase(newSdpUser.getSdp().getRsaIDNumber())) {
					throw new Exception("Duplicate RSA ID numbers for " + newSdpUser.getSdp().getFirstName() + " " + newSdpUser.getSdp().getLastName() + " and " + u.getFirstName() + " " + u.getLastName() + ", please rectify");
				}

			} else if (u.getPassportNumber() != null && !u.getPassportNumber().isEmpty() && newSdpUser.getSdp().getPassportNumber() != null && !newSdpUser.getSdp().getPassportNumber().isEmpty()) {
				if (u.getPassportNumber().equalsIgnoreCase(newSdpUser.getSdp().getPassportNumber())) {
					throw new Exception("Duplicate Passport numbers for " + newSdpUser.getSdp().getFirstName() + " " + newSdpUser.getSdp().getLastName() + " and " + u.getFirstName() + " " + u.getLastName() + ", please rectify");
				}
			}
			// check designation assigned once
			if (newSdpUser.getSdpType().getId().equals(sdpCompany.getSdpType().getId())) {
				throw new Exception("Designation already assigned: " + sdpCompany.getSdpType().getDescription() + ", please rectify");
			}
			// check if user has only been added once
			if (newSdpUser.getSdp().getId() != null && sdpCompany.getSdp().getId() != null) {
				if (newSdpUser.getSdp().getId().equals(sdpCompany.getSdp().getId())) {
					throw new Exception("User: " + newSdpUser.getSdp().getFirstName() + " " + newSdpUser.getSdp().getLastName() + " already assigned to list, please rectify");
				}
			}
		}
	}

	public void removeContactPerson() {
		try {
			contactPersonList.remove(contactPerson);
			clearContactPerson();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void prepareUpdateContactPerson() {
		if (tempContactPerson != null) {
			contactPersonList.add(tempContactPerson);
		}
		contactPersonList.remove(contactPerson);
		tempContactPerson = contactPerson;
	}

	public void doneAddingContactPerson() throws Exception {
		
		
		
		if (contactPersonList.size() <= 1) {
			throw new Exception("Please provide at least 2 contact persons");
		}
		doneContactPerson = true;
		doneSMEReg = false;

	}
	
	public void doneAddingContactPersonV2() throws Exception {
		
		try {
			int minUsersAllowed = 2;
			int usersAssigned = 0;
			
			// count 
			if (assignedTrainingSite == null) {
//				usersAssigned += sdpCompanyService.countSdpsByHoldingCompany(company.getId());
				usersAssigned += sdpCompanyService.countSdpsByHoldingCompanyApprovalStatusAndNotSdpType(company.getId(), HAJConstants.PRIMARY_SDP_ID, ApprovalEnum.Approved);
			} else {
				if (assignedTrainingSite.getId() != null) {
//					usersAssigned += sdpCompanyService.countSdpsByTrainingSiteId(assignedTrainingSite.getId());
					usersAssigned += sdpCompanyService.countSdpsByTrainingSiteIdApprovalAndNotSdpType(assignedTrainingSite.getId(), HAJConstants.PRIMARY_SDP_ID, ApprovalEnum.Approved);
				}
			}
			
			if (newSDPCompanyList != null && !newSDPCompanyList.isEmpty()) {
				for (SDPCompany newsdp : newSDPCompanyList) {
					if (newsdp.getSdpType() != null && newsdp.getSdpType().getId() != null && !newsdp.getSdpType().getId().equals(HAJConstants.PRIMARY_SDP_ID)) {
						usersAssigned++;
					}
				}
			}
			
			if (usersAssigned <  minUsersAllowed) {
				throw new Exception("Please provide at least 2 secondary contact persons (exclduing Primary SDP designation)");
			}
			
			doneContactPerson = true;
			doneSMEReg = false;
			assessorModeratorCompanySitesDataModelInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		
	}
	
	public void assessorModeratorCompanySitesDataModelInfo() {
		assessorModeratorCompanySitesDataModel = new LazyDataModel<AssessorModeratorCompanySites>() {
			private static final long serialVersionUID = 1L;
			private List<AssessorModeratorCompanySites> retorno = new ArrayList<>();
			@Override
			public List<AssessorModeratorCompanySites> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (assignedTrainingSite != null) {
						if (assignedTrainingSite.getId() != null) {
							retorno = assessorModeratorCompanySitesService.allAssessorModeratorLinkedToTrainingSite(first, pageSize, sortField, sortOrder, filters, assignedTrainingSite.getId());
							assessorModeratorCompanySitesDataModel.setRowCount(assessorModeratorCompanySitesService.countAllAssessorModeratorLinkedToTrainingSite(filters));
						}
					} else {
						retorno = assessorModeratorCompanySitesService.allAssessorModeratorLinkedToHoldingCompany(first, pageSize, sortField, sortOrder, filters, company.getId());
						assessorModeratorCompanySitesDataModel.setRowCount(assessorModeratorCompanySitesService.countAllAssessorModeratorLinkedToHoldingCompany(filters));
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(AssessorModeratorCompanySites obj) {
				return obj.getId();
			}

			@Override
			public AssessorModeratorCompanySites getRowData(String rowKey) {
				for (AssessorModeratorCompanySites obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	public ArrayList<SkillsProgram> getSelectItemsSkillsProgram() {
		ArrayList<SkillsProgram> list = null;
		try {
			list = skillsProgramService.findByQualifications((ArrayList<Qualification>) qualificationList);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}

		if (list == null || list.size() == 0) {
			trainingProviderApplication.setUseSkillProgrammeRoute(YesNoEnum.No);
			addWarningMessage("No Skills Programs available for qualification select");
		}
		return list;
	}

	public void populateSkillsProgramByUnitStandardsList() {
		try {
			if (unitStandards != null && unitStandards.size() != 0) {
				resultsForSkillsProgramFound = skillsProgramService.countByUnitStandardIdAssigned(unitStandards);
			} else {
				resultsForSkillsProgramFound = 0;
				trainingProviderApplication.setUseSkillProgrammeRoute(YesNoEnum.No);
				trainingProviderApplication.setSkillsProgram(null);
			}
			if (trainingProviderApplication.getUseSkillProgrammeRoute() != null && trainingProviderApplication.getUseSkillProgrammeRoute() == YesNoEnum.Yes) {
				if (unitStandards != null && unitStandards.size() != 0) {
					skillsProgramByUnitStandardsList = skillsProgramService.findByUnitStandardIdAssigned(unitStandards);
				} else {
					addErrorMessage("Select & add unit standards above to view skills programmes you can select");
				}
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Insert trainingprovider into database.
	 *
	 * @author TechFinium
	 * @see Company
	 */
	public void createtrainingprovider() {
		try {
			validiationErrors = "";
			validateUserEmail();
			if (company.getId() != null && formUser.getId() != null)
				if (trainingProviderApplication.getCodeOfConductAccepted() == null || !trainingProviderApplication.getCodeOfConductAccepted()) {
					throw new Exception("Please accept Code Of Conduct");
				}
			if (copyAddress) {
				AddressService.instance().copyFromToAddress(tpResidentialAddress, tpPostalAddress);
				AddressService.instance().copyFromToAddress(tpNonSetaResidentialAddress, tpNonSetaPostalAddress);
			}

			ArrayList<Users> contactPersons = new ArrayList<>();
			if (contactPersonList != null && contactPersonList.size() > 0) {
				contactPersons = contactPersonList;
			}
			if (facilitatorAssessorList != null && facilitatorAssessorList.size() > 0) {
				contactPersons.addAll(facilitatorAssessorList);
			}

			if (trainingProviderApplication != null && trainingProviderApplication.getLegacyProviderAccreditation() == null) {
				/** New Training Provider Application */
				if (trainingProviderApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL) {
					if (company.getId() == null) {
						company.setNonSetaCompany(true);
					}
					companyService.createTPAndCompanyAndSendTaskVersionTwo(unitStandards, qualificationList, company, assignedTrainingSite, newSDPCompanyList, newAssessorModeratorCompanySitesList, formUser, true, ConfigDocProcessEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), trainingProviderApplication, tpResidentialAddress, tpPostalAddress, contactPersons, skillsProgramList, skillsSetList);
				} else if (trainingProviderApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.Non_MerSETA_Focused_Provider) {
					validateSupportingDocs();
					if (company.getId() == null) {
						company.setNonSetaCompany(true);
					}
					companyService.createNonSetaTPSendTaskVersionTwo(companyUnitStandardList, companyQualificationsList, company, formUser, newSDPCompanyList, newAssessorModeratorCompanySitesList, true, ConfigDocProcessEnum.NON_SETA_PROVIDERS, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), trainingProviderApplication, tpResidentialAddress, tpPostalAddress, contactPersons, tpSkillsProgramme, tpSkillsSetList, docParentList);
				} else if ((trainingProviderApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.QCTOSkillsDevelopmentProvider || trainingProviderApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.QCTOTradeTestCentre)) {
					validateSupportingDocs();
					if (company.getId() == null || company.getNonSetaCompany()) {
						company.setNonSetaCompany(true);
						companyService.createNonSetaTPSendTaskVersionTwo(companyUnitStandardList, companyQualificationsList, company, formUser, newSDPCompanyList, newAssessorModeratorCompanySitesList, true, ConfigDocProcessEnum.NON_SETA_PROVIDERS, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), trainingProviderApplication, tpResidentialAddress, tpPostalAddress, contactPersons, tpSkillsProgramme, tpSkillsSetList, docParentList);
					} else {
						validateSupportingDocs();
						companyService.createNonSetaTPSendTaskVersionTwo(companyUnitStandardList, companyQualificationsList, company, formUser, newSDPCompanyList, newAssessorModeratorCompanySitesList, true, ConfigDocProcessEnum.NON_SETA_PROVIDERS, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), trainingProviderApplication, tpResidentialAddress, tpPostalAddress, contactPersons, tpSkillsProgramme, tpSkillsSetList, docParentList);
					}
				} else {
					if (company.getId() == null) {
						company.setNonSetaCompany(true);
					}
//					companyService.createTPAndCompanyAndSendTask(unitStandards, qualificationList, company, formUser, true, ConfigDocProcessEnum.TP, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), trainingProviderApplication, tpResidentialAddress, tpPostalAddress, contactPersons, skillsProgramList, skillsSetList);
					companyService.createTPAndCompanyAndSendTaskVersionTwo(unitStandards, qualificationList, company, assignedTrainingSite, newSDPCompanyList, newAssessorModeratorCompanySitesList, formUser, true, ConfigDocProcessEnum.TP, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), trainingProviderApplication, tpResidentialAddress, tpPostalAddress, contactPersons, skillsProgramList, skillsSetList);
				}
			} else {
				/** Legacy Training Provider Application */
				companyService.createLegacyTPSendTaskVersionTwo(newSDPCompanyList, newAssessorModeratorCompanySitesList, legacyProviderQualificationList, legacyProviderLearnershipList, legacyProviderSkillsProgrammeList, legacyProviderSkillsProgrammeSkillsSetList, legacyProviderUnitStandardList, company, formUser, true, ConfigDocProcessEnum.SDP_LEGACY_APPLICATION, trainingProviderApplication, tpResidentialAddress, tpPostalAddress, contactPersons);
			}

			addInfoMessage(super.getEntryLanguage("your.regsitration.is.being.processed"));
			firstTimeApplicant = true;
			alreadyAccredited = false;
			showSubmit = false;
			prepareNew();
			companyInfo();
		} catch (javax.validation.ConstraintViolationException e) {
			validiationErrors = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Exception, please refer to the error messages.");
		} catch (ValidationException e) {
			e.printStackTrace();
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			e.printStackTrace();
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/** Checks if supporting documents are uploaded */
	public void validateSupportingDocs() throws Exception {
		if (qualificationList != null && qualificationList.size() > 0 && (qualUplaoaded == null || !qualUplaoaded)) {
			throw new Exception("Please upload supporting document for qualification(s)");
		} else if (unitStandards != null && unitStandards.size() > 0 && (usUplaoaded == null || !usUplaoaded)) {
			throw new Exception("Please upload supporting document for Unit Standard(s) ");
		} else if (skillsProgramList != null && skillsProgramList.size() > 0 && (spUplaoaded == null || !spUplaoaded)) {
			throw new Exception("Please upload supporting document for Skills Programme(s) ");
		} else if (skillsSetList != null && skillsSetList.size() > 0 && (ssUplaoaded == null && !ssUplaoaded)) {
			throw new Exception("Please upload  supporting document for Skills SetList(s)");
		}
	}

	/**
	 * Update trainingprovider in database.
	 *
	 * @author TechFinium
	 * @see Company
	 */
	public void trainingproviderUpdate() {
		try {
			service.update(this.formUser);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			companyInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Check If User has applied as Training Provider before
	 **/
	public void checkIfTPIsAradyRegistered(Users users) {
		try {

			if (users.getId() != null) {
				TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
				ArrayList<TrainingProviderApplication> tpList = (ArrayList<TrainingProviderApplication>) trainingProviderApplicationService.findByUser(users);
				if (tpList != null && tpList.size() > 0) {
					firstTimeApplicant = false;
				} else {
					firstTimeApplicant = true;
				}
				// Check if user already accredited
				alreadyAccredited = false;
				for (TrainingProviderApplication tpApp : tpList) {
					if (tpApp.getAccreditationNumber() != null) {
						alreadyAccredited = true;
						break;
					}
				}
			} else {
				alreadyAccredited = false;
				firstTimeApplicant = true;
			}

		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<SelectItem> getFilteredApplicationType() {
		List<SelectItem> l = new ArrayList<SelectItem>();
		for (AccreditationApplicationTypeEnum val : AccreditationApplicationTypeEnum.values()) {
			if (val == AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL) {
				if (firstTimeApplicant) {
					l.add(new SelectItem(val, val.getFriendlyName()));
				}
			}
			if (val == AccreditationApplicationTypeEnum.EXTENSIONOFSCOPE) {
				if (alreadyAccredited) {
					l.add(new SelectItem(val, val.getFriendlyName()));
				}
			} else if (val != AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL && val != AccreditationApplicationTypeEnum.EXTENSIONOFSCOPE) {
				l.add(new SelectItem(val, val.getFriendlyName()));
			}
		}
		return l;
	}

	public void validateUserEmail() throws Exception {

		if (formUser.getId() != null) {
			usersService.isMailUsed(formUser.getEmail(), formUser);
		} else {
			usersService.checkEmailUsedEmailOnly(formUser.getEmail());
		}
	}

	/**
	 * Delete trainingprovider from database.
	 *
	 * @author TechFinium
	 * @see Company
	 */
	public void trainingproviderDelete() {
		try {
			service.delete(this.formUser);
			prepareNew();
			companyInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of trainingprovider.
	 *
	 * @author TechFinium
	 * @see Company
	 */
	public void prepareNew() {
		trainingProviderApplication = new TrainingProviderApplication();
		this.formUser = null;
		this.company = null;
		this.formUser = new Users();
		formUser.setAdmin(false);
		formUser.setDoneSearch(false);
		formUser.setRegFieldsDone(false);
		prepareNewQualification();
		this.qualificationList = new ArrayList<>();
		this.unitStandards = new ArrayList<>();
		this.skillsProgramList = new ArrayList<>();
		this.skillsSetList = new ArrayList<>();
		copyAddress = false;
		sitesSme = new SitesSme();
		sitesSmes = new ArrayList<>();
		contactPersonList = new ArrayList<>();
		facilitatorAssessorList = new ArrayList<>();
		searchContactPerson = false;
		doneContactPerson = false;
		doneSMEReg = false;
		learningProgrammeQualification = new Qualification();
		qualUplaoaded = false;
		usUplaoaded = false;
		spUplaoaded = false;
		ssUplaoaded = false;
		tpPostalAddress = new Address();
		tpResidentialAddress = new Address();
		doneQualification = false;
		edit = true;

	}

	/**
	 * Adds the Skills Program to list.
	 */
	public void addSkillsSetToList() {
		try {
			if (skillsSet == null) {
				throw new Exception("Please select Skills Set");
			}
			if (skillsSet != null && !skillsSetList.contains(skillsSet)) {
				if (ssUplaoaded != null && ssUplaoaded) {
					if (tpSkillsSetList != null && tpSkillsSetList.size() > 0) {
						if (tpSkillsSetList.get(0).getTrainingProviderDocParent() != null) {
							TrainingProviderSkillsSet tpSS = new TrainingProviderSkillsSet();
							tpSS.setSkillsSet(skillsSet);
							tpSS.setTrainingProviderDocParent(tpSkillsSetList.get(0).getTrainingProviderDocParent());
							tpSkillsSetList.add(tpSS);
						}
					}

				}
				this.skillsSetList.add(skillsSet);
				prepareNewSkillsSet();
				resetDoneQualification();
			} else {
				addWarningMessage(super.getEntryLanguage("skills.set.already.in.the.list"));
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Removes the Skills Program from list.
	 */
	public void removeSkillsSetFromList() {
		try {
			skillsSetList.remove(skillsSet);
			prepareNewSkillsSet();
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}

	public void prepareNewSkillsSet() {
		skillsSet = new SkillsSet();
	}

	/**
	 * Prepare new qualification.
	 */
	public void prepareNewQualification() {
		this.qualification = new Qualification();
		this.unitStandard = new UnitStandards();
	}

	/**
	 * Adds the Skills Program to list.
	 */
	public void addSkillsProgramToList() {
		try {
			if (skillsProgram == null) {
				throw new Exception("Please select Skills Programme");
			}
			if (skillsProgram != null && !skillsProgramList.contains(skillsProgram)) {
				if (spUplaoaded != null && spUplaoaded) {
					if (tpSkillsProgramme != null && tpSkillsProgramme.size() > 0) {
						if (tpSkillsProgramme.get(0).getTrainingProviderDocParent() != null) {
							TrainingProviderSkillsProgramme stSP = new TrainingProviderSkillsProgramme();
							stSP.setSkillsProgram(skillsProgram);
							stSP.setTrainingProviderDocParent(tpSkillsProgramme.get(0).getTrainingProviderDocParent());
							tpSkillsProgramme.add(stSP);
						}
					}

				}
				this.skillsProgramList.add(skillsProgram);
				prepareNewSkillsProgram();
				resetDoneQualification();
			} else {
				addWarningMessage(super.getEntryLanguage("skills.program.already.in.the.list"));
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Removes the Skills Program from list.
	 */
	public void removeSkillsProgramFromList() {
		try {
			skillsProgramList.remove(skillsProgram);
			prepareNewSkillsProgram();
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}

	public void prepareNewSkillsProgram() {
		skillsProgram = new SkillsProgram();
	}

	/**
	 * Validate user from selecting US from that belong to selected qualification
	 */
	public void usQualificationValidation() {
		try {
			SaqaUsQualificationService saqaUsQualificationService = new SaqaUsQualificationService();
			for (Qualification qual : qualificationList) {
				if ((saqaUsQualificationService.findByQualAndUS(qual.getId(), unitStandard.getCode())).size() > 0) {
					unitStandard = null;
					throw new Exception("Unit Standard forms part of the qualification(" + qual.getDescription() + ") selected above");
				}
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	/**
	 * Adds the qualification to list.
	 */
	public void addQualificationToList() {
		try {
			if (qualification == null) {
				throw new Exception("Please select qualification");
			}
			if (qualification != null && !qualificationList.contains(qualification)) {
				if (qualUplaoaded != null && qualUplaoaded) {
					if (companyQualificationsList != null && companyQualificationsList.size() > 0) {
						if (companyQualificationsList.get(0).getTrainingProviderDocParent() != null) {
							CompanyQualifications companyQualifications = new CompanyQualifications();
							companyQualifications.setQualification(qualification);
							companyQualifications.setTrainingProviderDocParent(companyQualificationsList.get(0).getTrainingProviderDocParent());
							companyQualificationsList.add(companyQualifications);
						}
					}

				}
				this.qualificationList.add(qualification);
				if (trainingProviderApplication != null && trainingProviderApplication.getAccreditationApplicationTypeEnum() != AccreditationApplicationTypeEnum.QCTOTradeTestCentre) {
					addSKillsProgrammes();
					addSKillsSet();
					addUnitStandards();
				}
				prepareNewQualification();
				resetDoneQualification();
			} else {
				addWarningMessage(super.getEntryLanguage("qualification.aready.in.the.list"));
			}
			trainingProviderApplication.setSkillsProgram(null);
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Removes the qualification from list.
	 */
	public void removeQualificationFromList() {
		try {
			qualificationList.remove(qualification);
			removeSKillsProgrammes(qualification);
			removeSKillsSet(qualification);
			removeUnitStandards(qualification);
			prepareNewQualification();
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Adds the unit to list.
	 */
	public void addUnitToList() {
		try {
			if (unitStandard == null) {
				throw new Exception("Please select Unit Standard");
			}
			if (unitStandard != null && !unitStandards.contains(unitStandard)) {
				if (usUplaoaded != null && usUplaoaded) {
					if (companyUnitStandardList != null && companyUnitStandardList.size() > 0) {
						if (companyUnitStandardList.get(0).getTrainingProviderDocParent() != null) {
							CompanyUnitStandard compUS = new CompanyUnitStandard();
							compUS.setUnitStandard(unitStandard);
							compUS.setTrainingProviderDocParent(companyUnitStandardList.get(0).getTrainingProviderDocParent());
							companyUnitStandardList.add(compUS);
						}
					}

				}
				this.unitStandards.add(unitStandard);
				prepareNewQualification();
				populateSkillsProgramByUnitStandardsList();
				resetDoneQualification();
			} else {
				addWarningMessage(super.getEntryLanguage("us.aready.in.the.list"));
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Removes the unit from list.
	 */
	public void removeUnitFromList() {
		try {
			unitStandards.remove(unitStandard);
			prepareNewQualification();
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Gets the company list.
	 *
	 * @return the company list
	 */
	public List<Company> getCompanyList() {
		return companyList;
	}

	/**
	 * Sets the company list.
	 *
	 * @param companyList the new company list
	 */
	public void setCompanyList(List<Company> companyList) {
		this.companyList = companyList;
	}

	/**
	 * Gets the form user.
	 *
	 * @return the form user
	 */
	public Users getFormUser() {
		return formUser;
	}

	/**
	 * Sets the form user.
	 *
	 * @param formUser the new form user
	 */
	public void setFormUser(Users formUser) {
		this.formUser = formUser;
	}

	/**
	 * Checks if is passport number.
	 *
	 * @return true, if is passport number
	 */
	public boolean isPassportNumber() {
		return passportNumber;
	}

	/**
	 * Sets the passport number.
	 *
	 * @param passportNumber the new passport number
	 */
	public void setPassportNumber(boolean passportNumber) {
		this.passportNumber = passportNumber;
	}

	/**
	 * Gets the company.
	 *
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Sets the company.
	 *
	 * @param company the new company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * Gets the search user passport or id UI.
	 *
	 * @return the search user passport or id UI
	 */
	public SearchUserPassportOrIdUI getSearchUserPassportOrIdUI() {
		return searchUserPassportOrIdUI;
	}

	/**
	 * Sets the search user passport or id UI.
	 *
	 * @param searchUserPassportOrIdUI the new search user passport or id UI
	 */
	public void setSearchUserPassportOrIdUI(SearchUserPassportOrIdUI searchUserPassportOrIdUI) {
		this.searchUserPassportOrIdUI = searchUserPassportOrIdUI;
	}

	/**
	 * This gets a return from AbstractUIInterface.
	 *
	 * @param object the object
	 */
	public void callBackMethod(Object object) {
		try {
			if (object instanceof Users) {
				if (searchContactPerson == false) {
					this.formUser = (Users) object;
					if (formUser.getId() != null) {
						if (hostingCompanyEmployeesService.findByUserCount(formUser.getId(), HAJConstants.HOSTING_MERSETA) > 0) {
							formUser = null;
							throw new Exception("Employees cannot be assigned.");
						} else {
							searchContactPerson = true;
							formUser.setRecieveEmailTaskNotification(true);
							formUser.setRegFieldsDone(false);
							checkIfTPIsAradyRegistered(formUser);
							if (formUser.getAcceptPopi() == null) {
								formUser.setAcceptPopi(true);
							}
						}
					} else {
						searchContactPerson = true;
						formUser.setRecieveEmailTaskNotification(true);
						formUser.setRegFieldsDone(false);
						checkIfTPIsAradyRegistered(formUser);
						if (formUser.getAcceptPopi() == null) {
							formUser.setAcceptPopi(true);
						}
					}
					// trainingProviderApplication = new
					// TrainingProviderApplication(formUser);
				} else {
					// Adding contact person
					if (!doneContactPerson) {
						this.contactPerson = (Users) object;
						if (contactPerson.getId() != null) {
							if (hostingCompanyEmployeesService.findByUserCount(contactPerson.getId(), HAJConstants.HOSTING_MERSETA) > 0) {
								contactPerson = null;
								throw new Exception("Employees cannot be assigned.");
							} else {
								contactPerson.setRecieveEmailTaskNotification(true);
								if (contactPerson.getAcceptPopi() == null) {
									contactPerson.setAcceptPopi(true);
								}
								usersService.identifyFieldAlteration(contactPerson);
								newSdpCompany = new SDPCompany(company, contactPerson);
								if (assignedTrainingSite != null) {
									newSdpCompany.setTrainingSite(assignedTrainingSite);
								}
								newSdpCompany.setCanAlter(true);
							}
						} else {
							contactPerson.setRecieveEmailTaskNotification(true);
							if (contactPerson.getAcceptPopi() == null) {
								contactPerson.setAcceptPopi(true);
							}
							usersService.identifyFieldAlteration(contactPerson);
							newSdpCompany = new SDPCompany(company, contactPerson);
							if (assignedTrainingSite != null) {
								newSdpCompany.setTrainingSite(assignedTrainingSite);
							}
							newSdpCompany.setCanAlter(true);
						}
					} else {
						try {
							this.facilitatorAssessor = (Users) object;
							if (facilitatorAssessor.getId() != null) {
								if (hostingCompanyEmployeesService.findByUserCount(facilitatorAssessor.getId(), HAJConstants.HOSTING_MERSETA) > 0) {
									facilitatorAssessor = null;
									throw new Exception("Employees cannot be assigned.");
								} else {
									facilitatorAssessor.setRecieveEmailTaskNotification(true);
									usersService.identifyFieldAlteration(facilitatorAssessor);
									if (facilitatorAssessor.getAcceptPopi() == null) {
										facilitatorAssessor.setAcceptPopi(true);
									}
									trainingProviderApplicationService.avialabilityOfFacilitorAssModValidation(facilitatorAssessor);
									newAssModLink = new AssessorModeratorCompanySites(company, facilitatorAssessor);
									if (assignedTrainingSite != null) {
										newAssModLink.setTrainingSite(assignedTrainingSite);
									}
									newAssModLink.setCanAlter(true);
								}
							} else {
								facilitatorAssessor.setRecieveEmailTaskNotification(true);
								if (facilitatorAssessor.getAcceptPopi() == null) {
									facilitatorAssessor.setAcceptPopi(true);
								}
								usersService.identifyFieldAlteration(facilitatorAssessor);
								trainingProviderApplicationService.avialabilityOfFacilitorAssModValidation(facilitatorAssessor);
							}
						} catch (Exception e) {
							clearFacilitatorAssessor();
							addErrorMessage(e.getMessage(), e);
							e.printStackTrace();
						}
					}
				}
			} else if (object instanceof Company) {
				this.company = (Company) object;
				// Company User Validation
				if (company.getId() != null && formUser.getId() != null) {
					try {
					    /**
						 * @author jonathanbowett 
						 * 16 October 2020
						 * Removed validation check for application. Moved and enhanced on company information submission
						 */
//						trainingProviderApplicationService.validateCompanyUser(formUser, company, trainingProviderApplication.getAccreditationApplicationTypeEnum());
					} catch (Exception e) {
						company = null;
						addErrorMessage(e.getMessage(), e);
						e.printStackTrace();
					}
				}
				applicationForSiteDisplay = false;
				newSite = false;
				linkSiteToApplication = false;
				trainingProviderApplication.setTrainingSite(null);
				primarySdpLinked = false;
				setmisValidiationSite = "";
				setmisValidiationSdp = "";
				assignedTrainingSite = null;
				if (company != null) {
					// check if involves sites
					if (trainingProviderApplication != null && trainingProviderApplication.getAccreditationApplicationTypeEnum() != null && (
							trainingProviderApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL
							||
							trainingProviderApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL
							)) {
						// allows site information alteration / assignment
						applicationForSiteDisplay = true;
						trainingSiteDataModelInfo();
					}
					levyNumberRegnumberValidation();
					checkRequiredWorkflow();
					trainingProviderApplication.setCompany(company);
					if (company.getResidentialAddress() != null) {
						tpResidentialAddress = AddressService.instance().findByKey(company.getResidentialAddress().getId());
					}
					if (company.getPostalAddress() != null) {
						tpPostalAddress = AddressService.instance().findByKey(company.getPostalAddress().getId());
					}
//					if (company.getAccreditationNumber() == null && trainingProviderApplication.getAccreditationNumber() != null) {
//						company.setAccreditationNumber(trainingProviderApplication.getAccreditationNumber());
//					}
				}
			} else if (object instanceof LegacyProviderAccreditation) {
				applicationForSiteDisplay = false;
				newSite = false;
				linkSiteToApplication = false;
				trainingProviderApplication.setTrainingSite(null);
				assignedTrainingSite = null;
				primarySdpLinked = false;
				setmisValidiationSite = "";
				setmisValidiationSdp = "";
				LegacyProviderAccreditation lpa = (LegacyProviderAccreditation) object;
				trainingProviderApplication.setLegacyProviderAccreditation(null);
				trainingProviderApplication.setLegacyProviderAccreditation(lpa);
				this.company = lpa.getCompany();
				// Populating TrainingProviderApplication
				populateTPLegacyData(lpa);
				// Company User Validation
				if (company.getId() != null && formUser.getId() != null) {
					try {
						/**
						 * @author jonathanbowett 
						 * 16 October 2020
						 * Removed validation check for application. Moved and enhanced on company information submission
						 */
//						trainingProviderApplicationService.validateCompanyUser(formUser, company, trainingProviderApplication.getAccreditationApplicationTypeEnum());
					} catch (Exception e) {
						company = null;
						addErrorMessage(e.getMessage(), e);
						e.printStackTrace();
					}
				}
				if (company != null) {
					if (trainingProviderApplication != null && trainingProviderApplication.getAccreditationApplicationTypeEnum() != null && (
							trainingProviderApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL
							||
							trainingProviderApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL
							)) {
						// allows site information alteration / assignment
						applicationForSiteDisplay = true;
						trainingSiteDataModelInfo();
					}
					levyNumberRegnumberValidation();
					checkRequiredWorkflowForLegacyData();
					trainingProviderApplication.setCompany(company);
					tpResidentialAddress = new Address();
					if (company.getResidentialAddress() != null) {
						tpResidentialAddress = AddressService.instance().findByKey(company.getResidentialAddress().getId());
					}
					tpResidentialAddress.setAddessInfoLocked(false);
					AddressService.instance().determainAlterationsByConfigProcess(tpResidentialAddress, ConfigDocProcessEnum.SDP_LEGACY_APPLICATION, false);
					
					tpPostalAddress = new Address();
					if (company.getPostalAddress() != null) {
						tpPostalAddress = AddressService.instance().findByKey(company.getPostalAddress().getId());
					}
					tpPostalAddress.setAddessInfoLocked(false);
					AddressService.instance().determainAlterationsByConfigProcess(tpPostalAddress, ConfigDocProcessEnum.SDP_LEGACY_APPLICATION, true);
					// set validation needs to be run
					validiationPassed = false; 
					validiationErrorsLegacySdp = "";
//					if (company.getAccreditationNumber() == null && trainingProviderApplication.getAccreditationNumber() != null) {
//						company.setAccreditationNumber(trainingProviderApplication.getAccreditationNumber());
//					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void selectIfTrainingSiteAssignment(){
		try {
			if (linkSiteToApplication) {
				trainingSiteDataModelInfo();
			} else {
				newSite = false;
				assignedTrainingSite = null;
				trainingProviderApplication.setTrainingSite(null);
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void clearSelectionOfSite(){
		try {
			newSite = false;
			assignedTrainingSite = null;
			primarySdpLinked = false;
			setmisValidiationSite = "";
			addWarningMessage("Selection Cleared.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void trainingSiteDataModelInfo() {
		trainingSiteDataModel = new LazyDataModel<TrainingSite>() {
			private static final long serialVersionUID = 1L;
			private List<TrainingSite> retorno = new ArrayList<>();
			@Override
			public List<TrainingSite> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (company != null && company.getId() != null) {
						retorno = trainingSiteService.allTrainingSiteLinkedToCompanyResolveRegionData(first, pageSize, sortField, sortOrder, filters, company.getId());
						trainingSiteDataModel.setRowCount(trainingSiteService.countAllTrainingSiteLinkedToCompany(filters));
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(TrainingSite obj) {
				return obj.getId();
			}
			@Override
			public TrainingSite getRowData(String rowKey) {
				for (TrainingSite obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void selectSite(){
		try {
			// check If SDP Assigned to site
			if (sdpCompanyService.countSdpTypeByTrainingSiteId(assignedTrainingSite.getId(), HAJConstants.PRIMARY_SDP_ID) == 0) {
				primarySdpLinked = false;
			} else {
				primarySdpLinked = true;
			}
			addInfoMessage("Site Selected");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepNewSite(){
		try {
			newSite = true;
			assignedTrainingSite = new TrainingSite();
			assignedTrainingSite.setCompany(company);
			assignedTrainingSite.setResidentialAddress(new Address());
			assignedTrainingSite.setPostalAddress(new Address());
			assignedTrainingSite.getPostalAddress().setSameAddress(false);
			primarySdpLinked = false;
			setmisValidiationSite = "";
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void populateTPLegacyData(LegacyProviderAccreditation lpa) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		
		// clear data
		trainingProviderApplication.setStartDate(null);
		trainingProviderApplication.setExpiriyDate(null);
		trainingProviderApplication.setApprovedDate(null);
		trainingProviderApplication.setProviderClass(null);
		trainingProviderApplication.setProviderType(null);
		trainingProviderApplication.setAccreditationStatus(null);
		trainingProviderApplication.setEtqa(null);
		trainingProviderApplication.setAccreditationApplicationTypeEnum(null);
		trainingProviderApplication.setAccreditationNumber(null);
		trainingProviderApplication.setTrainingAssessment(false);
		trainingProviderApplication.setAssessmentOnly(false);
		
		if (lpa.getAccreditationStartDate() != null && !lpa.getAccreditationStartDate().isEmpty() && !lpa.getAccreditationStartDate().equals("NULL") && !lpa.getAccreditationStartDate().equals("")) {
			trainingProviderApplication.setStartDate(formatter.parse(lpa.getAccreditationStartDate()));
		}
		
		if (lpa.getAccreditationEndDate() != null && !lpa.getAccreditationEndDate().isEmpty() && !lpa.getAccreditationEndDate().equals("NULL") && !lpa.getAccreditationEndDate().equals("")) {
			trainingProviderApplication.setExpiriyDate(formatter.parse(lpa.getAccreditationEndDate()));
		}

		if (lpa.getProviderStatusEffectiveDate() != null && !lpa.getProviderStatusEffectiveDate().isEmpty() && !lpa.getProviderStatusEffectiveDate().equals("NULL") && !lpa.getProviderStatusEffectiveDate().equals("")) {
			trainingProviderApplication.setApprovedDate(formatter.parse(lpa.getProviderStatusEffectiveDate()));
		}

		if (lpa.getProviderClass() != null && !lpa.getProviderClass().isEmpty() && !lpa.getProviderClass().equals("NULL") && !lpa.getProviderClass().equals("")) {
			ProviderClassService providerClassService = new ProviderClassService();
			List<ProviderClass> pcList = providerClassService.findByDescription(lpa.getProviderClass());
			if (pcList != null && pcList.size() > 0) {
				trainingProviderApplication.setProviderClass(pcList.get(0));
			}
		}

		if (lpa.getProviderType() != null && !lpa.getProviderType().isEmpty() && !lpa.getProviderType().equals("NULL") && !lpa.getProviderType().equals("")) {
			ProviderTypeService providerTypeService = new ProviderTypeService();
			List<ProviderType> ptList = providerTypeService.findByDescription(lpa.getProviderType());
			if (ptList != null && ptList.size() > 0) {
				trainingProviderApplication.setProviderType(ptList.get(0));
			}
		}

		if (lpa.getProviderStatus() != null && !lpa.getProviderStatus().isEmpty() && !lpa.getProviderStatus().equals("NULL") && !lpa.getProviderStatus().equals("")) {
			AccreditationStatusService accreditationStatusService = new AccreditationStatusService();
			List<AccreditationStatus> list = accreditationStatusService.findByDescription(lpa.getProviderType());
			if (list != null && list.size() > 0) {
				trainingProviderApplication.setAccreditationStatus(list.get(0));
			}
		}
		if (lpa.getProviderStatus() != null) {
			if (lpa.getProviderStatus().trim().equalsIgnoreCase("Accredited - Full")) {
				EtqaService etqaService = new EtqaService();
				Etqa etqa = etqaService.findByCode(HAJConstants.HOSTING_MERSETA_ETQA);
				trainingProviderApplication.setEtqa(etqa);
				trainingProviderApplication.setAccreditationApplicationTypeEnum(AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL);
			} else if (lpa.getProviderStatus().trim().equalsIgnoreCase("Programme Approval")) {
				trainingProviderApplication.setAccreditationApplicationTypeEnum(AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL);
			} else if (lpa.getProviderStatus().trim().equalsIgnoreCase("DTTC")) {
				trainingProviderApplication.setAccreditationApplicationTypeEnum(AccreditationApplicationTypeEnum.QCTOTradeTestCentre);
			}
		}

		if (lpa.getAccreditationNo() != null && !lpa.getAccreditationNo().isEmpty() && !lpa.getAccreditationNo().equals("NULL") && !lpa.getAccreditationNo().equals("")) {
			trainingProviderApplication.setAccreditationNumber(lpa.getAccreditationNo());
		}

	}

	public List<SelectItem> getAssessorModTypeDD() {
		List<SelectItem> l = null;
		try {
			l = new ArrayList<SelectItem>();
			AssessorModeratorApplicationService assessorModeratorApplicationService = new AssessorModeratorApplicationService();
			List<AssessorModeratorApplication> amApplicationList = assessorModeratorApplicationService.findByApprovedUserApplications(facilitatorAssessor);

			for (AssessorModeratorApplication amApp : amApplicationList) {
				if (amApp.getApplicationType() == AssessorModeratorAppTypeEnum.NewAssessorRegistration || amApp.getApplicationType() == AssessorModeratorAppTypeEnum.AssessorExtensionOfScope || amApp.getApplicationType() == AssessorModeratorAppTypeEnum.AssessorReRegistration) {
					AssessorModType val = AssessorModType.Assessor;
					l.add(new SelectItem(val, val.getFriendlyName()));
				} else {
					AssessorModType val = AssessorModType.Moderator;
					l.add(new SelectItem(val, val.getFriendlyName()));
				}
			}
			if (l.size() == 1) {
				facilitatorAssessor.setAssessorModType((AssessorModType) l.get(0).getValue());
			}

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
		return l;
	}
	
	public List<SelectItem> getAssessorModTypeDDVersionTwo() {
		List<SelectItem> l = null;
		try {
			l = new ArrayList<SelectItem>();
			AssessorModeratorApplicationService assessorModeratorApplicationService = new AssessorModeratorApplicationService();
			List<AssessorModeratorApplication> amApplicationList = assessorModeratorApplicationService.findByApprovedUserApplications(newAssModLink.getAssessorModerator());

			for (AssessorModeratorApplication amApp : amApplicationList) {
				if (amApp.getApplicationType() == AssessorModeratorAppTypeEnum.NewAssessorRegistration || amApp.getApplicationType() == AssessorModeratorAppTypeEnum.AssessorExtensionOfScope || amApp.getApplicationType() == AssessorModeratorAppTypeEnum.AssessorReRegistration) {
					AssessorModType val = AssessorModType.Assessor;
					l.add(new SelectItem(val, val.getFriendlyName()));
				} else {
					AssessorModType val = AssessorModType.Moderator;
					l.add(new SelectItem(val, val.getFriendlyName()));
				}
			}
			if (l.size() == 1) {
				facilitatorAssessor.setAssessorModType((AssessorModType) l.get(0).getValue());
			}

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
		return l;
	}

	public void checkIfIsAssOrMod() {
		try {
			trainingProviderApplicationService.checkIfIsAssOrMod(facilitatorAssessor);
		} catch (Exception e) {
			facilitatorAssessor.setDesignation(null);
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void checkIfIsAssOrModVersionTwo() {
		try {
			trainingProviderApplicationService.checkIfIsAssOrMod(newAssModLink.getAssessorModerator());
		} catch (Exception e) {
			facilitatorAssessor.setDesignation(null);
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void checkRequiredWorkflow() {
		try {

			if (formUser != null && company == null) {
				if (trainingProviderApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL) {
					companyService.prepareNewRegistrationType(ConfigDocProcessEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL, null, this.formUser, CompanyUserTypeEnum.User);
				} else {
					companyService.prepareNewRegistrationType(ConfigDocProcessEnum.TP, null, this.formUser, CompanyUserTypeEnum.User);

				}
			}
			if (company != null) {
				if (trainingProviderApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL) {
					companyService.prepareNewRegistrationType(ConfigDocProcessEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL, this.company, null, CompanyUserTypeEnum.Company);
				} else if (trainingProviderApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.QCTOSkillsDevelopmentProvider || trainingProviderApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.QCTOTradeTestCentre || trainingProviderApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.Non_MerSETA_Focused_Provider) {
					companyService.prepareNewRegistrationType(ConfigDocProcessEnum.NON_SETA_PROVIDERS, this.company, null, CompanyUserTypeEnum.Company);
				} else {
					companyService.prepareNewRegistrationType(ConfigDocProcessEnum.TP, this.company, null, CompanyUserTypeEnum.Company);
				}
			}

			clearTpdetails();

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}

	}

	public void checkRequiredWorkflowForLegacyData() {
		try {

			if (formUser != null) {
				companyService.prepareNewRegistrationType(ConfigDocProcessEnum.SDP_LEGACY_APPLICATION, null, this.formUser, CompanyUserTypeEnum.User);
			}
			if (company != null) {
				companyService.prepareNewRegistrationType(ConfigDocProcessEnum.SDP_LEGACY_APPLICATION, this.company, null, CompanyUserTypeEnum.Company);
			}

			clearTpdetails();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}

	}

	public void onApplicationTypeChange() {
		try {
			if (company != null) {
				company = null;
			}
			if (formUser != null) {
				this.formUser.setRegFieldsDone(false);
			}
			if (trainingProviderApplication != null) {
				trainingProviderApplication.setAccreditationNumber(null);
				trainingProviderApplication.setStartDate(null);
				trainingProviderApplication.setExpiriyDate(null);
				trainingProviderApplication.setEtqa(null);
				trainingProviderApplication.setProviderClass(null);
				trainingProviderApplication.setProviderStatus(null);
				trainingProviderApplication.setAccreditationStatus(null);
				if (trainingProviderApplication.getAccreditationApplicationTypeEnum() != null && trainingProviderApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL) {
					// default to hosting company ETQA
					trainingProviderApplication.setEtqa(etqaService.findByCode(HAJConstants.HOSTING_MERSETA_ETQA));
					trainingProviderApplication.setProviderStatus(providerStatusService.findByCode("506"));
				}
			}
			clearTpdetails();
			checkRequiredWorkflow();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void onLegacyApplicationTypeChange() {
		try {
			clearTpdetails();
			checkRequiredWorkflow();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void clearTpdetails() {
		if (qualificationList != null) {
			qualificationList.clear();
		}
		if (unitStandards != null) {
			unitStandards.clear();
		}
		if (skillsProgramList != null) {
			skillsProgramList.clear();
		}

		if (companyQualificationsList != null) {
			companyQualificationsList.clear();
		}
		if (tpSkillsProgramme != null) {
			tpSkillsProgramme.clear();
		}
		if (companyUnitStandardList != null) {
			companyUnitStandardList.clear();
		}
		if (tpSkillsSetList != null) {
			tpSkillsSetList.clear();
		}

	}

	public void checkRequiredWorkflowForMersetaExternalAccre() {
		try {
			if (company == null) {
				company = new Company();
			}
			if (company.getDocs() == null || company.getDocs().size() < 0) {
				companyService.prepareNewRegistrationType(ConfigDocProcessEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL, this.company, null, CompanyUserTypeEnum.Company);
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * On tab change.
	 *
	 * @param event the event
	 */
	public void onTabChange(TabChangeEvent event) {

	}

	/**
	 * Gets the search company UI.
	 *
	 * @return the search company UI
	 */
	public SearchCompanyUI getSearchCompanyUI() {
		return searchCompanyUI;
	}

	/**
	 * Sets the search company UI.
	 *
	 * @param searchCompanyUI the new search company UI
	 */
	public void setSearchCompanyUI(SearchCompanyUI searchCompanyUI) {
		this.searchCompanyUI = searchCompanyUI;
	}

	/**
	 * Gets the doc.
	 *
	 * @return the doc
	 */
	public Doc getDoc() {
		return doc;
	}

	/**
	 * Sets the doc.
	 *
	 * @param doc the new doc
	 */
	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	/**
	 * Store file.
	 *
	 * @param event the event
	 */
	public void storeFile(FileUploadEvent event) {
		doc.setCompany(company);
		doc.setData(event.getFile().getContents());
		doc.setOriginalFname(event.getFile().getFileName());
		doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
		super.runClientSideExecute("PF('dlgUpload').hide()");
	}

	/**
	 * Store file user.
	 *
	 * @param event the event
	 */
	public void storeFileUser(FileUploadEvent event) {
		doc.setData(event.getFile().getContents());
		doc.setOriginalFname(event.getFile().getFileName());
		doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
		super.runClientSideExecute("PF('dlgUploadUser').hide()");
	}

	/**
	 * Prep doc.
	 *
	 * @param index the index
	 */
	public void prepDoc(int index) {
		this.doc = this.company.getDocs().get(index);
	}

	/**
	 * JMB 20 08 2018
	 * 
	 * No longer required
	 * 
	 * Check to swap values
	 * 
	 * @see TrainingProviderApplication
	 * @see trainingProviderApplication.getTrainingAssessment()
	 */
	public void switchValuesTrainingAssessment() {
		try {
			if (trainingProviderApplication.getTrainingAssessment()) {
				trainingProviderApplication.setAssessmentOnly(false);
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * JMB 20 08 2018
	 * 
	 * No longer required
	 * 
	 * Check to swap values
	 * 
	 * @see TrainingProviderApplication
	 * @see trainingProviderApplication.getAssessmentOnly()
	 */
	public void switchValuesAssessmentOnly() {
		try {
			if (trainingProviderApplication.getAssessmentOnly()) {
				trainingProviderApplication.setTrainingAssessment(false);
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * JMB 20 08 2018
	 * 
	 * Added check to ensure either TrainingAssessment or AssessmentOnly is
	 * provided/true
	 * 
	 * @throws Exception
	 * 
	 * @see TrainingProviderApplication
	 */
	public void checkFeildsProvided() {

		try {

			if (Boolean.TRUE.equals(userErrors(formUser) == null)) {
				companyService.preUserRegisterChecks(formUser);
				if ((trainingProviderApplication.getTrainingAssessment() == null || !trainingProviderApplication.getTrainingAssessment()) && (trainingProviderApplication.getAssessmentOnly() == null || !trainingProviderApplication.getAssessmentOnly())) {
					// throw new Exception("Provide Whether: Training and Assessment or Assessment
					// Only");
				}
				// Validate start and end date
				validateStartAndEndDate();
				// Check if Email exist
				validateUserEmail();

				doneUserBit();
				// auto run
				if (trainingProviderApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.EXTENSIONOFSCOPE) {
					getSearchCompanyUI().setSearchAccNumber(true);
					getSearchCompanyUI().setSearchEntityId(false);
					getSearchCompanyUI().setCriteria(trainingProviderApplication.getAccreditationNumber().trim());
					getSearchCompanyUI().findByAccreditationNumberTrainingProviderRegistartion();
				}
				// addInfoMessage("Complete Company Registration Information Below");
			} else {
				formUser.setRegFieldsDone(false);
				addErrorMessage("Please correct User Information where applicable");
			}

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void legacyCheckFeildsProvided() {

		try {
			if (Boolean.TRUE.equals(userErrors(formUser) == null)) {
				validateUserEmail();
				doneUserBit();
			} else {
				addErrorMessage("Please correct User Information where applicable");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void validateStartAndEndDate() throws Exception {
		if (trainingProviderApplication.getStartDate() != null && trainingProviderApplication.getExpiriyDate() != null) {
			int days = GenericUtility.getDaysBetweenDates(trainingProviderApplication.getStartDate(), trainingProviderApplication.getExpiriyDate());
			if (days < 1) {
				throw new Exception("Invalid expiriy date, Your expiriy date must be greater than your start date");
			}
		}
	}

	public void acceptCodeOfConduct() {
		trainingProviderApplication.setCodeOfConductAcceptDate(new Date());
	}

	/**
	 * Done user bit.
	 */
	public void doneUserBit() {
		edit = false;
		this.formUser.setRegFieldsDone(true);
	}

	public void editUserInfo() {
		edit = false;
		if (formUser == null || formUser.getId() == null) {
			edit = true;
		}
		this.formUser.setRegFieldsDone(false);
	}

	/**
	 * Done company bit.
	 */
	public void doneCompanyBit() {
		try {
//			companyService.validateRegistartionNumberByCompany(company, company.getCompanyRegistrationNumber());
			
			// not required users can not create companies anymore.
//			if (company.getLevyNumber() != null && !company.getLevyNumber().trim().isEmpty()) {
//				companyService.validateLevyNumberByCompany(company, company.getLevyNumber());
//			}
			
			String sectionsWithErrors = "";
			boolean passedValidiations = true;
			
			// check holding company information
			setmisValidiationHoldingCompany = "";
			setmisValidiationHoldingCompany = companyService.validiateCompanyInformation(company).toString();
			if (!setmisValidiationHoldingCompany.trim().isEmpty()) {
				sectionsWithErrors = "Holding Company Information";
				passedValidiations = false;
			}
			
			// Training Site Validations
			setmisValidiationSite = "";
			if (trainingProviderApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL
					||
					trainingProviderApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL) {
				if (linkSiteToApplication) {
					if (assignedTrainingSite == null) {
						sectionsWithErrors += " Training Site Information";
						setmisValidiationSite = "Assign Site Before Proceed or remove selection of linking site to application.";
						passedValidiations = false;
					}else {
						if (assignedTrainingSite.getId() != null && !primarySdpLinked) {
							setmisValidiationSite = trainingSiteService.validiateSiteInformation(assignedTrainingSite).toString();
						}else if(assignedTrainingSite.getId() == null) {
							setmisValidiationSite = trainingSiteService.validiateSiteInformation(assignedTrainingSite).toString();
						}
						if (!setmisValidiationSite.trim().isEmpty()) {
							sectionsWithErrors += " Training Site Information";
							passedValidiations = false;
						}
					}
				} else {
					trainingProviderApplication.setTrainingSite(null);
					assignedTrainingSite = null;
					newSite = false;
					trainingProviderApplication.setTrainingSite(null);
					primarySdpLinked = false;
				}
			} else {
				applicationForSiteDisplay = false;
				newSite = false;
				linkSiteToApplication = false;
				trainingProviderApplication.setTrainingSite(null);
				primarySdpLinked = false;
			}
			
			if (!passedValidiations) {
				addErrorMessage("Validiation Exceptions. Please refer to sections for futher information: " + sectionsWithErrors);
			} else {
				// validate that no existing application linked to company & site or just company
				if (trainingProviderApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL
						||
						trainingProviderApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL) {
					if (linkSiteToApplication && assignedTrainingSite.getId() != null) {
						// check on site level
						if (trainingProviderApplicationService.countByCompanyIdSiteIdApplicationTypeAndStatusList(
								trainingProviderApplication.getAccreditationApplicationTypeEnum(), company.getId(), assignedTrainingSite.getId(), ApprovalEnum.getOpenStatusForTrainingProviderApplications()) != 0) {
							throw new Exception("Unable to proceed. Currently an open: "+ trainingProviderApplication.getAccreditationApplicationTypeEnum().getFriendlyName() + " against the company and site selected.");
						}
					} else if (assignedTrainingSite == null) {
						// check on holding company level
						if (trainingProviderApplicationService.countProviderApllicationsByOpenStatusApplicationTypeAndCompanyIdWithNoSiteAssigned(
								trainingProviderApplication.getAccreditationApplicationTypeEnum(), company.getId(), ApprovalEnum.getOpenStatusForTrainingProviderApplications()) != 0) {
							throw new Exception("Unable to proceed. Currently an open "+ trainingProviderApplication.getAccreditationApplicationTypeEnum().getFriendlyName() + " against the company.");
						}
					}
				}
				this.company.setRegDone(true);
				if (company.getDocs() != null) {
					company.getDocs().forEach(doc -> {
						if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
							this.company.setRegDone(false);
							addErrorMessage("Upload <i>" + doc.getConfigDoc().getName() + "</i> for <i>" + company.getCompanyName() + "</i><br/>");
						}
					});
				}
				sdpCompanyDataModelInfo();
				prepareContactPersons();
				addPrimaySdpVersionTwo();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void sdpCompanyDataModelInfo() {
		sdpCompanyDataModel = new LazyDataModel<SDPCompany>() {
			private static final long serialVersionUID = 1L;
			private List<SDPCompany> retorno = new ArrayList<>();
			@Override
			public List<SDPCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (assignedTrainingSite != null) {
						if (assignedTrainingSite.getId() != null) {
							retorno = sdpCompanyService.allSdpLinkedToTrainingSite(first, pageSize, sortField, sortOrder, filters, assignedTrainingSite.getId());
							sdpCompanyDataModel.setRowCount(sdpCompanyService.countAllSdpLinkedToTrainingSite(filters));
						}
					} else {
						retorno = sdpCompanyService.allSdpLinkedToHoldingCompany(first, pageSize, sortField, sortOrder, filters, company.getId());
						sdpCompanyDataModel.setRowCount(sdpCompanyService.countAllSdpLinkedToHoldingCompany(filters));
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SDPCompany obj) {
				return obj.getId();
			}

			@Override
			public SDPCompany getRowData(String rowKey) {
				for (SDPCompany obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	public void doneLegacyCompanyBit() {
		try {
//			companyService.validateRegistartionNumberByCompany(company, company.getCompanyRegistrationNumber());
			if (company.getLevyNumber() != null && !company.getLevyNumber().trim().isEmpty()) {
				companyService.validateLevyNumberByCompany(company, company.getLevyNumber());
			}
			if (Boolean.TRUE.equals(companyErrors(company) == null) && Boolean.TRUE.equals(addressErrors(tpResidentialAddress) == null) && postalAddressErrors(tpPostalAddress) == null) {
				if ((trainingProviderApplication.getTrainingAssessment() == null || !trainingProviderApplication.getTrainingAssessment()) && (trainingProviderApplication.getAssessmentOnly() == null || !trainingProviderApplication.getAssessmentOnly())) {
					throw new Exception("Provide Whether: Training and Assessment or Assessment Only");
				}
				// Validate start and end date
				validateStartAndEndDate();

				this.company.setRegDone(true);
				this.company.setRegDone(true);
				if (company.getDocs() != null) {
					company.getDocs().forEach(doc -> {
						if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
							this.company.setRegDone(false);
							this.company.setRegDone(false);
							addErrorMessage("Upload <i>" + doc.getConfigDoc().getName() + "</i> for <i>" + company.getCompanyName() + "</i><br/>");
						}
					});
				}
				companyService.preUserRegisterChecks(formUser);
				prepareContactPersons();
				addPrimaySDP();
			} else {
				if (Boolean.TRUE.equals(companyErrors(company) == null)) {
					addErrorMessage("Please correct Company Information where applicable");
				}
				if (Boolean.FALSE.equals(addressErrors(tpResidentialAddress) == null)) {
					addErrorMessage("Please correct Residential Address Information where applicable");
				}
				if (Boolean.FALSE.equals(postalAddressErrors(tpPostalAddress) == null)) {
					addErrorMessage("Please correct Postal Address Information where applicable");
				}
			}

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}

	}
	
	public void runLegacySdpValidations() {
		try {
			runCompanyValidiationsLegacySdp();
			runAccrediciationChecks();
			if (validiationPassed) {
				addInfoMessage("Validation Passed, you may proceed with the registration.");
			} else {
				addErrorMessage("Validation Exception, please Refer to the messages below.");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void runAccrediciationChecks() throws Exception{
		if (trainingProviderApplication.getAccreditationApplicationTypeEnum() != null && company != null && company.getId() != null && (trainingProviderApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL || trainingProviderApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL) ) {
			// check for any open or dereigeterd if count greater than 0 must not be allowed to proceed.
//			if (trainingProviderApplicationService.countProviderApllicationsByOpenStatusApplicationTypeAndCompanyId(trainingProviderApplication.getAccreditationApplicationTypeEnum(), company.getId(), ApprovalEnum.getOpenStatusForTrainingProviderApplicationsForLegacy()) > 0){
//				validiationPassed = false;
//				validiationErrorsLegacySdp += "<b>Application Validation Error:</b> <br/> Company linked already has a registered application:  " + trainingProviderApplication.getAccreditationApplicationTypeEnum().getFriendlyName() + ". Please contact support!";
//			}
			// version two
			if (trainingProviderApplicationService.countProviderApllicationsByOpenStatusApplicationTypeAndCompanyIdWithNoSiteAssigned(trainingProviderApplication.getAccreditationApplicationTypeEnum(), company.getId(), ApprovalEnum.getOpenStatusForTrainingProviderApplications()) != 0) {
				validiationPassed = false;
				validiationErrorsLegacySdp += "<b>Application Validation Error:</b> <br/> Company linked already has a registered application:  " + trainingProviderApplication.getAccreditationApplicationTypeEnum().getFriendlyName() + ". Please contact support!";
			}
		}
	}

	public void runCompanyValidiationsLegacySdp() throws Exception {
		validiationErrorsLegacySdp = "";
		validiationPassed = false;
		
		if (companyService == null) {
			companyService = new CompanyService();
		}
		
		String companyErrors = companyErrorsIgnoreAddress(company);
		String resAddErrors = addressErrorsNoStartMessage(tpResidentialAddress);
		String postAddErrors = postalAddressErrorsNoStartMessage(tpPostalAddress);
//		String regNumberInUse = companyService.validateRegistartionNumberByCompanyReturnError(company, company.getCompanyRegistrationNumber());
		String levyNumberInUse = companyService.validateLevyNumberByCompanyReturnError(company, company.getLevyNumber());
	
		// company validation
		if (companyErrors != null && !companyErrors.isEmpty()) {
			validiationErrorsLegacySdp += "<b>Company Information Validation Exception:</b> <br/>";
			validiationErrorsLegacySdp += companyErrors;
		}
		companyErrors = null;
		
		// Physical address validation
		if (resAddErrors != null && !resAddErrors.isEmpty()) {
			validiationErrorsLegacySdp += "<b>Physical Address Information Validation Exception:</b> <br/>";
			validiationErrorsLegacySdp += resAddErrors;
		}
		resAddErrors = null;
		
		// Postal address validation
		if (postAddErrors != null && !postAddErrors.isEmpty()) {
			validiationErrorsLegacySdp += "<b>Postal Address Information Validation Exception:</b> <br/>";
			validiationErrorsLegacySdp += postAddErrors;
		}
		postAddErrors = null;
		
		// Company Registration Number Validation
//		if (regNumberInUse != null && !regNumberInUse.isEmpty()) {
//			validiationErrorsLegacySdp += "<b>Company Registration Number Validation Error:</b> <br/>";
//			validiationErrorsLegacySdp += regNumberInUse;
//		}
//		regNumberInUse = null;
		
		// Levy Number Validation
		if (levyNumberInUse != null && !levyNumberInUse.isEmpty()) {
			validiationErrorsLegacySdp += "<b>Company SDL Number Validation Error:</b> <br/> Levy / SDL number already in use! <b> Contact support with Levy / SDL number along with accreditation number used for legacy registration. </b>";
		}
		levyNumberInUse = null;
		
		validiationErrorsLegacySdp = validiationErrorsLegacySdp.trim();
		if (validiationErrorsLegacySdp.isEmpty()) {
			validiationPassed = true;
		} else {
			validiationPassed = false;
		}
	}
	
	public void doneLegacyCompanyBitForExistingLegacyCompanies() {
		try {
			// data validation
			runCompanyValidiationsLegacySdp();
			
			// doc validation
			boolean allDocsUploaded = true;
			if (company.getDocs() != null) {
				for (Doc doc : company.getDocs()) {
					if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
						this.company.setRegDone(false);
						allDocsUploaded = false;
						addErrorMessage("Upload <i>" + doc.getConfigDoc().getName() + "</i> for <i>" + company.getCompanyName() + "</i><br/>");
					}
				}
			}
			
			// Validate start and end date
			validateStartAndEndDate();
			
			// user docs validation
			companyService.preUserRegisterChecks(formUser);
			
			if (validiationPassed && allDocsUploaded) {
				if ((trainingProviderApplication.getTrainingAssessment() == null || !trainingProviderApplication.getTrainingAssessment()) && (trainingProviderApplication.getAssessmentOnly() == null || !trainingProviderApplication.getAssessmentOnly())) {
					throw new Exception("Provide Whether: Training and Assessment or Assessment Only");
				}
				this.company.setRegDone(true);
				// lock info from updates
				company.setLockInfoUpdate(true);
				tpResidentialAddress.setAddessInfoLocked(true);
				tpPostalAddress.setAddessInfoLocked(true);
//				prepareContactPersons();
//				addPrimaySDP();
				
				sdpCompanyDataModelInfo();
				prepareContactPersons();
				addPrimaySdpVersionTwo();
				
				super.runClientSideUpdate("companyInsForm");
			} else {
				addErrorMessage("Validation Exception, please Refer to the messages below.");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void levyNumberRegnumberValidation() throws Exception {
		if (trainingProviderApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.Non_MerSETA_Focused_Provider) {
//			if (company != null && company.getId() == null &&  company.getCompanyRegistrationNumber() != null) {
//				List<Company> compList = companyService.findByNameOrRegNumberNoResolve(company.getCompanyRegistrationNumber());
//				if (compList != null && compList.size() > 0) {
//					company = null;
//					throw new Exception("Company Registration Number aready exist in merSETA database");
//				}
//			}
			if (company != null && company.getId() == null && company.getLevyNumber() != null) {
				Company comp = companyService.findByLevyNumberNoResolve(company.getLevyNumber());
				if (comp != null) {
					company = null;
					throw new Exception("SDL number aready exist in merSETA database");
				}
			}
		}
	}

	public void doneQualificationDetails() {
		try {
			if (qualificationList.size() > 0 || unitStandards.size() > 0 || skillsProgramList.size() > 0 || skillsSetList.size() > 0) {
				doneQualification = true;
			} else {
				throw new ValidationException("Please provide qualification details");
			}
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}

	}

	public void resetDoneQualification() {
		doneQualification = false;
		super.runClientSideUpdate("companyInsForm");
	}

	public void prepareContactPersons() {
		try {
			contactPersonList.clear();
			facilitatorAssessorList.clear();
			
			// removed as not required at the moment
//			if (company != null && company.getId() != null) {
//				List<CompanyUsers> cpList = companyUsersService.findTPContact(company.getId(), ConfigDocProcessEnum.TP);
//				List<CompanyUsers> asessorModList = companyUsersService.findTPAssessorMod(company.getId(), ConfigDocProcessEnum.TP);
//				if (cpList != null && cpList.size() > 0) {
//					for (CompanyUsers cu : cpList) {
//						Users user = cu.getUser();
//						user.setDesignation(cu.getDesignation());
//						contactPersonList.add(user);
//					}
//				}
//				
//				if (cpList == null || cpList.isEmpty()) {
//					
//				}
//
//				if (asessorModList != null && asessorModList.size() > 0) {
//					for (CompanyUsers cu : asessorModList) {
//						Users user = cu.getUser();
//						user.setAssessorModType(cu.getAssessorModType());
//						facilitatorAssessorList.add(user);
//					}
//				}
//			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void validatelatitudeminutes() throws Exception {
		// Latitude Degree
		if (tpResidentialAddress.getLatitudeDegrees() <= 0) {
			throw new Exception("Latitude Degrees must be a negative value");
		} else if (tpResidentialAddress.getLatitudeDegrees() > -22) {
			throw new Exception("Latitude Degrees may not be greater than -22");
		} else if (tpResidentialAddress.getLatitudeDegrees() < -35) {
			throw new Exception("Latitude Degrees may not have a value less than -35");
		}

		// Latitude Minutes
		if (tpResidentialAddress.getLatitudeMinutes() != Math.ceil(tpResidentialAddress.getLatitudeMinutes())) {
			throw new Exception("Latitude Minutes may only contain whole numbers");
		} else {
			String input = String.valueOf(tpResidentialAddress.getLatitudeMinutes());
			String[] element = String.valueOf(tpResidentialAddress.getLatitudeMinutes()).split("");
			int count = 0;
			for (int i = 0; i < input.length() && element[i].equals("0"); i++) {
				count++;
			}
			if (count != 2 || tpResidentialAddress.getLatitudeMinutes() > 59) {
				throw new Exception("Latitude Minutes must have a length of exactly 2 (leading zeros) and may not be greater than 59");
			}
		}

		// Latitude Seconds
		if (tpResidentialAddress.getLatitudeSeconds() <= -1) {
			throw new Exception("Latitude Seconds may only contain characters 1234567890");
		} else if (String.valueOf(tpResidentialAddress.getLatitudeSeconds()).length() > 6) {
			throw new Exception("Latitude Seconds must have a length of exactly 6 (nn.nnn)");
		} else if (tpResidentialAddress.getLatitudeSeconds() > 59.999) {
			throw new Exception("Latitude Seconds may not be greater than 59.999");
		}

		// ***************************************************

		// Longitude Degree
		if (tpResidentialAddress.getLongitudeDegrees() != Math.ceil(tpResidentialAddress.getLongitudeDegrees())) {
			throw new Exception("Longitude Degree may only contain whole numbers");
		} else if (tpResidentialAddress.getLongitudeDegrees() > 33 || tpResidentialAddress.getLongitudeDegrees() < 16) {
			throw new Exception("Longitude Degrees may not be greater than 33 and may not have a value less than 16");
		}

		// Longitude Minutes
		if (tpResidentialAddress.getLongitudeMinutes() != Math.ceil(tpResidentialAddress.getLongitudeMinutes())) {
			throw new Exception("Longitude Minutes may only contain whole numbers");
		} else {
			String input = String.valueOf(tpResidentialAddress.getLongitudeMinutes());
			String[] element = String.valueOf(tpResidentialAddress.getLongitudeMinutes()).split("");
			int count = 0;
			for (int i = 0; i < input.length() && element[i].equals("0"); i++) {
				count++;
			}
			if (count != 2 || tpResidentialAddress.getLongitudeMinutes() > 59) {
				throw new Exception("Longitude Minutes must have a length of exactly 2 (leading zeros) and may not be greater than 59");
			}
		}

		// Longitude Seconds
		if (tpResidentialAddress.getLatitudeSeconds() <= -1) {
			throw new Exception("Longitude Seconds may only contain characters 1234567890");
		} else if (String.valueOf(tpResidentialAddress.getLatitudeSeconds()).length() > 6) {
			throw new Exception("Latitude Seconds must have a length of exactly 6 (nn.nnn)");
		} else if (tpResidentialAddress.getLatitudeSeconds() > 59.999) {
			throw new Exception("Latitude Seconds may not be greater than 59.999");
		}

	}

	private void addPrimaySDP() throws Exception {
		boolean addPrimarySDP = true;
		Designation designation = designationService.findByCode("PRI_SDP");
		if (designation == null) {
			throw new Exception("No Primary Designation found in the lookup table. Designation code to be added: PRI_SDP");
		}
		for (Users u : contactPersonList) {
			if (u.getDesignation() != null && u.getDesignation().equals(designation)) {
				addPrimarySDP = false;
				break;
			}
		}
		if (addPrimarySDP) {
			if (!contactPersonList.contains(formUser)) {
				formUser.setDesignation(designation);
				contactPersonList.add(formUser);
			}
		}
	}
	
	private void addPrimaySdpVersionTwo() throws Exception {
		newSdpCompany = null;
		newSDPCompanyList.clear();
		newAssessorModeratorCompanySitesList.clear();
		newAssModLink = null;
		boolean addSessionUserAsPrimary = false;
		boolean addSessionUserAsSecondary = false;
		boolean userAssignedToCompany = false;
		
		// check if user already assigned to company
		if (formUser.getId() != null) {
			if (assignedTrainingSite == null) {
				if (sdpCompanyService.countUserAssignedByHoldingCompany( company.getId(), formUser.getId()) > 0) {
					userAssignedToCompany = true;
				}
			} else {
				if (assignedTrainingSite.getId() != null) {
					if (sdpCompanyService.countUserAssignedByTrainingSiteId( assignedTrainingSite.getId(), formUser.getId()) > 0) {
						userAssignedToCompany = true;
					}
				}
			}
		}
		
		if (!userAssignedToCompany) {
			if (assignedTrainingSite == null) {
				if (sdpCompanyService.countSdpTypeByHoldingCompany( company.getId(),HAJConstants.PRIMARY_SDP_ID) == 0) {
					addSessionUserAsPrimary = true;
				}
			} else {
				if (assignedTrainingSite.getId() == null) {
					addSessionUserAsPrimary = true;
				} else {
					if (sdpCompanyService.countSdpTypeByTrainingSiteId( assignedTrainingSite.getId(),HAJConstants.PRIMARY_SDP_ID) == 0) {
						addSessionUserAsPrimary = true;
					}
				}
			}
			if (addSessionUserAsPrimary) {
				SDPCompany primarySdp = new SDPCompany(company, formUser, SdpTypeService.instance().findByKey(HAJConstants.PRIMARY_SDP_ID));
				if (assignedTrainingSite != null) {
					primarySdp.setTrainingSite(assignedTrainingSite);
				}
				primarySdp.setCanAlter(false);
				newSDPCompanyList.add(primarySdp);
			} else {
				// SECONDARY_SDP_ID Contact
				if (assignedTrainingSite == null) {
					if (sdpCompanyService.countSdpTypeByHoldingCompany( company.getId(),HAJConstants.SECONDARY_SDP_ID) == 0) {
						addSessionUserAsSecondary = true;
					}
				} else {
					if (assignedTrainingSite.getId() == null) {
						addSessionUserAsSecondary = true;
					} else {
						if (sdpCompanyService.countSdpTypeByTrainingSiteId( assignedTrainingSite.getId(),HAJConstants.SECONDARY_SDP_ID) == 0) {
							addSessionUserAsSecondary = true;
						}
					}
				}
				if (addSessionUserAsSecondary) {
					SDPCompany secondarySdp = new SDPCompany(company, formUser, SdpTypeService.instance().findByKey(HAJConstants.SECONDARY_SDP_ID));
					if (assignedTrainingSite != null) {
						secondarySdp.setTrainingSite(assignedTrainingSite);
					}
					secondarySdp.setCanAlter(false);
					newSDPCompanyList.add(secondarySdp);
				}
			}
		}
	}

	/**
	 * Complete qualification.
	 *
	 * @param desc the desc
	 * @return the list
	 */
	public List<Qualification> completeQualification(String desc) {
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

	public void clearPostal() {
		if (!copyAddress) {
			AddressService.instance().clearAddress(tpPostalAddress);
			AddressService.instance().clearAddress(tpNonSetaPostalAddress);
		}
	}
	
	public void clearPostalTrainingSite() {
		if (assignedTrainingSite != null && assignedTrainingSite.getPostalAddress() != null && assignedTrainingSite.getPostalAddress().getSameAddress() != null && assignedTrainingSite.getPostalAddress().getSameAddress()) {
			AddressService.instance().clearAddress(assignedTrainingSite.getPostalAddress());
		}
	}

	/**
	 * Complete unit.
	 *
	 * @param desc the desc
	 * @return the list
	 */
	public List<UnitStandards> completeUnit(String desc) {
		List<UnitStandards> l = null;
		try {
			l = unitStandardsService.findByTitleAndBeforeLastEnrolmentDate(desc, getNow());
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
			if (qualificationList != null && qualificationList.size() != 0) {
				l = unitStandardsService.findByNameAndQualificationList(desc, qualificationList);
			} else {
				l = unitStandardsService.findByName(desc);
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Qualification> completeLeaningProgrameQualification(String desc) {
		QualificationService qualificationService = new QualificationService();
		List<Qualification> l = null;
		try {
			if (learningProgrammeQualification != null) {
				l = qualificationService.findLearningProgrammeByQual(desc, learningProgrammeQualification);
			}

		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public void addSKillsProgrammes() {
		SkillsProgramService unitStandardsService = new SkillsProgramService();
		List<SkillsProgram> l = null;
		try {
			if (qualificationList != null && qualificationList.size() != 0) {
				l = unitStandardsService.findByQualificationList(qualificationList);
				for (SkillsProgram sp : l) {
					if (!skillsProgramList.contains(sp)) {
						sp.setCannotRemove(true);
						skillsProgramList.add(sp);
					}
				}
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void addSKillsSet() {
		SkillsSetService skillsSetService = new SkillsSetService();
		List<SkillsSet> l = null;
		try {
			if (qualificationList != null && qualificationList.size() != 0) {
				l = skillsSetService.findByQualificationList(qualificationList);
				for (SkillsSet ss : l) {
					if (!skillsSetList.contains(ss)) {
						ss.setCannotRemove(true);
						skillsSetList.add(ss);
					}
				}
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void addUnitStandards() {
		UnitStandardsService unitStandardsService = new UnitStandardsService();
		List<UnitStandards> l = null;
		try {
			if (qualificationList != null && qualificationList.size() != 0) {
				l = unitStandardsService.findByQualificationList(qualificationList);
				for (UnitStandards us : l) {
					if (!unitStandards.contains(us)) {
						us.setCannotRemove(true);
						unitStandards.add(us);
					}
				}
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void removeSKillsProgrammes(Qualification qualification) {
		List<SkillsProgram> tempList = new ArrayList<>();
		tempList.addAll(skillsProgramList);
		for (SkillsProgram sp : skillsProgramList) {
			if (sp.getQualification().equals(qualification)) {
				tempList.remove(sp);
			}
		}
		skillsProgramList.clear();
		skillsProgramList = (ArrayList<SkillsProgram>) tempList;
	}

	public void removeSKillsSet(Qualification qualification) {
		List<SkillsSet> tempList = new ArrayList<>();
		tempList.addAll(skillsSetList);
		for (SkillsSet ss : skillsSetList) {
			if (ss.getQualification().equals(qualification)) {
				tempList.remove(ss);
			}
		}
		skillsSetList.clear();
		skillsSetList = (ArrayList<SkillsSet>) tempList;
	}

	public void removeUnitStandards(Qualification qualification) {
		List<UnitStandards> tempList = new ArrayList<>();
		tempList.addAll(unitStandards);
		for (UnitStandards us : unitStandards) {
			if (us.getQualification().equals(qualification)) {
				tempList.remove(us);
			}
		}
		unitStandards.clear();
		unitStandards = (ArrayList<UnitStandards>) tempList;
	}

	/**
	 * Gets the select items titles.
	 *
	 * @return the select items titles
	 */
	public List<DisabilityRating> getSelectItemsDisabilityRating() {
		DisabilityRatingService disabilityRatingService = new DisabilityRatingService();
		List<DisabilityRating> l = null;
		try {
			if (formUser.getDisabilityStatus() != null) {
				l = disabilityRatingService.findByDisability(formUser.getDisabilityStatus().getId());
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public void clearDisabilityRating() {
		try {
			formUser.setDisabilityRating(null);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public String userErrors(Users user) {
		if (user != null) {
			String errors = usersService.validiateUserInformation(user).toString();
			if (Boolean.FALSE.equals(errors.isEmpty())) {
				return errors;
			}
		}
		return null;
	}

	public String companyErrors(Company company) {
		if (company != null) {
			String errors = companyService.validiateCompanyInformation(company).toString();
			if (Boolean.FALSE.equals(errors.isEmpty())) {
				return errors;
			}
		}
		return null;
	}
	
	public String companyErrorsIgnoreAddress(Company company) {
		if (company != null) {
			String errors = companyService.validiateCompanyInformationIgnoreAddresses(company).toString();
			if (Boolean.FALSE.equals(errors.isEmpty())) {
				return errors;
			}
		}
		return null;
	}

	public String addressErrors(Address address) {
		if (address != null) {
			String errors = addressService.validiateAddressInformation(address).toString();
			if (Boolean.FALSE.equals(errors.isEmpty())) {
				return "Physical Address " + errors;
			}
		}
		return null;
	}
	
	public String addressErrorsNoStartMessage(Address address) {
		if (address != null) {
			String errors = addressService.validiateAddressInformation(address).toString();
			if (Boolean.FALSE.equals(errors.isEmpty())) {
				return errors;
			}
		}
		return null;
	}

	public String postalAddressErrors(Address address) {
		if (address != null) {
			String errors = addressService.validiateAddressInformation(address).toString();
			if (Boolean.FALSE.equals(errors.isEmpty())) {
				return "Postal Address " + errors;
			}
		}
		return null;
	}
	
	public String postalAddressErrorsNoStartMessage(Address address) {
		if (address != null) {
			String errors = addressService.validiateAddressInformation(address).toString();
			if (Boolean.FALSE.equals(errors.isEmpty())) {
				return errors;
			}
		}
		return null;
	}

	/**
	 * Gets the qualification list.
	 *
	 * @return the qualification list
	 */
	public List<Qualification> getQualificationList() {
		return qualificationList;
	}

	/**
	 * Sets the qualification list.
	 *
	 * @param qualificationList the new qualification list
	 */
	public void setQualificationList(List<Qualification> qualificationList) {
		this.qualificationList = qualificationList;
	}

	/**
	 * Gets the qualification.
	 *
	 * @return the qualification
	 */
	public Qualification getQualification() {
		return qualification;
	}

	/**
	 * Sets the qualification.
	 *
	 * @param qualification the new qualification
	 */
	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	/**
	 * Gets the unit standards.
	 *
	 * @return the unit standards
	 */
	public List<UnitStandards> getUnitStandards() {
		return unitStandards;
	}

	/**
	 * Sets the unit standards.
	 *
	 * @param unitStandards the new unit standards
	 */
	public void setUnitStandards(List<UnitStandards> unitStandards) {
		this.unitStandards = unitStandards;
	}

	/**
	 * Gets the unit standard.
	 *
	 * @return the unit standard
	 */
	public UnitStandards getUnitStandard() {
		return unitStandard;
	}

	/**
	 * Sets the unit standard.
	 *
	 * @param unitStandard the new unit standard
	 */
	public void setUnitStandard(UnitStandards unitStandard) {
		this.unitStandard = unitStandard;
	}

	public TrainingProviderApplication getTrainingProviderApplication() {
		return trainingProviderApplication;
	}

	public void setTrainingProviderApplication(TrainingProviderApplication trainingProviderApplication) {
		this.trainingProviderApplication = trainingProviderApplication;
	}

	/**
	 * @return the firstTimeApplicant
	 */
	public boolean isFirstTimeApplicant() {
		return firstTimeApplicant;
	}

	/**
	 * @param firstTimeApplicant the firstTimeApplicant to set
	 */
	public void setFirstTimeApplicant(boolean firstTimeApplicant) {
		this.firstTimeApplicant = firstTimeApplicant;
	}

	/**
	 * @return the alreadyAccredited
	 */
	public boolean isAlreadyAccredited() {
		return alreadyAccredited;
	}

	/**
	 * @param alreadyAccredited the alreadyAccredited to set
	 */
	public void setAlreadyAccredited(boolean alreadyAccredited) {
		this.alreadyAccredited = alreadyAccredited;
	}

	/**
	 * @return the tpPostalAddress
	 */
	public Address getTpPostalAddress() {
		return tpPostalAddress;
	}

	/**
	 * @param tpPostalAddress the tpPostalAddress to set
	 */
	public void setTpPostalAddress(Address tpPostalAddress) {
		this.tpPostalAddress = tpPostalAddress;
	}

	/**
	 * @return the tpResidentialAddress
	 */
	public Address getTpResidentialAddress() {
		return tpResidentialAddress;
	}

	/**
	 * @param tpResidentialAddress the tpResidentialAddress to set
	 */
	public void setTpResidentialAddress(Address tpResidentialAddress) {
		this.tpResidentialAddress = tpResidentialAddress;
	}

	public boolean isCopyAddress() {
		return copyAddress;
	}

	public void setCopyAddress(boolean copyAddress) {
		this.copyAddress = copyAddress;
	}

	/**
	 * @return the skillsProgram
	 */
	public SkillsProgram getSkillsProgram() {
		return skillsProgram;
	}

	/**
	 * @param skillsProgram the skillsProgram to set
	 */
	public void setSkillsProgram(SkillsProgram skillsProgram) {
		this.skillsProgram = skillsProgram;
	}

	/**
	 * @return the contactPerson
	 */
	public Users getContactPerson() {
		return contactPerson;
	}

	/**
	 * @param contactPerson the contactPerson to set
	 */
	public void setContactPerson(Users contactPerson) {
		this.contactPerson = contactPerson;
	}

	/**
	 * @return the contactPersonList
	 */
	public ArrayList<Users> getContactPersonList() {
		return contactPersonList;
	}

	/**
	 * @param contactPersonList the contactPersonList to set
	 */
	public void setContactPersonList(ArrayList<Users> contactPersonList) {
		this.contactPersonList = contactPersonList;
	}

	/**
	 * @return the searchContactPerson
	 */
	public boolean isSearchContactPerson() {
		return searchContactPerson;
	}

	/**
	 * @param searchContactPerson the searchContactPerson to set
	 */
	public void setSearchContactPerson(boolean searchContactPerson) {
		this.searchContactPerson = searchContactPerson;
	}

	/**
	 * @return the doneContactPerson
	 */
	public boolean isDoneContactPerson() {
		return doneContactPerson;
	}

	/**
	 * @param doneContactPerson the doneContactPerson to set
	 */
	public void setDoneContactPerson(boolean doneContactPerson) {
		this.doneContactPerson = doneContactPerson;
	}

	/**
	 * @return the sitesSme
	 */
	public SitesSme getSitesSme() {
		return sitesSme;
	}

	/**
	 * @param sitesSme the sitesSme to set
	 */
	public void setSitesSme(SitesSme sitesSme) {
		this.sitesSme = sitesSme;
	}

	/**
	 * @return the sitesSmes
	 */
	public List<SitesSme> getSitesSmes() {
		return sitesSmes;
	}

	/**
	 * @param sitesSmes the sitesSmes to set
	 */
	public void setSitesSmes(List<SitesSme> sitesSmes) {
		this.sitesSmes = sitesSmes;
	}

	/**
	 * @return the doneSMEReg
	 */
	public boolean isDoneSMEReg() {
		return doneSMEReg;
	}

	/**
	 * @param doneSMEReg the doneSMEReg to set
	 */
	public void setDoneSMEReg(boolean doneSMEReg) {
		this.doneSMEReg = doneSMEReg;
	}

	/**
	 * @return the facilitatorAssessor
	 */
	public Users getFacilitatorAssessor() {
		return facilitatorAssessor;
	}

	/**
	 * @param facilitatorAssessor the facilitatorAssessor to set
	 */
	public void setFacilitatorAssessor(Users facilitatorAssessor) {
		this.facilitatorAssessor = facilitatorAssessor;
	}

	/**
	 * @return the facilitatorAssessorList
	 */
	public ArrayList<Users> getFacilitatorAssessorList() {
		return facilitatorAssessorList;
	}

	/**
	 * @param facilitatorAssessorList the facilitatorAssessorList to set
	 */
	public void setFacilitatorAssessorList(ArrayList<Users> facilitatorAssessorList) {
		this.facilitatorAssessorList = facilitatorAssessorList;
	}

	public ArrayList<SkillsProgram> getSkillsProgramList() {
		return skillsProgramList;
	}

	public void setSkillsProgramList(ArrayList<SkillsProgram> skillsProgramList) {
		this.skillsProgramList = skillsProgramList;
	}

	public YesNoEnum getLevyNonLevySDP() {
		return levyNonLevySDP;
	}

	public void setLevyNonLevySDP(YesNoEnum levyNonLevySDP) {
		this.levyNonLevySDP = levyNonLevySDP;
	}

	public SDPApplicationType getEmployerType() {
		return employerType;
	}

	public void setEmployerType(SDPApplicationType employerType) {
		this.employerType = employerType;
	}

	public String getUrlAction() {
		return urlAction;
	}

	public void setUrlAction(String urlAction) {
		this.urlAction = urlAction;
	}

	public Users getTempContactPerson() {
		return tempContactPerson;
	}

	public void setTempContactPerson(Users tempContactPerson) {
		this.tempContactPerson = tempContactPerson;
	}

	public Users getTempFacilitatorAssessor() {
		return tempFacilitatorAssessor;
	}

	public void setTempFacilitatorAssessor(Users tempFacilitatorAssessor) {
		this.tempFacilitatorAssessor = tempFacilitatorAssessor;
	}

	public ArrayList<SkillsSet> getSkillsSetList() {
		return skillsSetList;
	}

	public void setSkillsSetList(ArrayList<SkillsSet> skillsSetList) {
		this.skillsSetList = skillsSetList;
	}

	public SkillsSet getSkillsSet() {
		return skillsSet;
	}

	public void setSkillsSet(SkillsSet skillsSet) {
		this.skillsSet = skillsSet;
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public NonSetaCompany getNonSetaCompany() {
		return nonSetaCompany;
	}

	public void setNonSetaCompany(NonSetaCompany nonSetaCompany) {
		this.nonSetaCompany = nonSetaCompany;
	}

	public Address getTpNonSetaPostalAddress() {
		return tpNonSetaPostalAddress;
	}

	public void setTpNonSetaPostalAddress(Address tpNonSetaPostalAddress) {
		this.tpNonSetaPostalAddress = tpNonSetaPostalAddress;
	}

	public Address getTpNonSetaResidentialAddress() {
		return tpNonSetaResidentialAddress;
	}

	public void setTpNonSetaResidentialAddress(Address tpNonSetaResidentialAddress) {
		this.tpNonSetaResidentialAddress = tpNonSetaResidentialAddress;
	}

	public String getDocFor() {
		return docFor;
	}

	public void setDocFor(String docFor) {
		this.docFor = docFor;
	}

	public Boolean getQualUplaoaded() {
		return qualUplaoaded;
	}

	public void setQualUplaoaded(Boolean qualUplaoaded) {
		this.qualUplaoaded = qualUplaoaded;
	}

	public Boolean getUsUplaoaded() {
		return usUplaoaded;
	}

	public void setUsUplaoaded(Boolean usUplaoaded) {
		this.usUplaoaded = usUplaoaded;
	}

	public Boolean getSpUplaoaded() {
		return spUplaoaded;
	}

	public void setSpUplaoaded(Boolean spUplaoaded) {
		this.spUplaoaded = spUplaoaded;
	}

	public Boolean getSsUplaoaded() {
		return ssUplaoaded;
	}

	public void setSsUplaoaded(Boolean ssUplaoaded) {
		this.ssUplaoaded = ssUplaoaded;
	}

	public String getTELPHONE_FORMAT() {
		return TELPHONE_FORMAT;
	}

	public void setTELPHONE_FORMAT(String tELPHONE_FORMAT) {
		TELPHONE_FORMAT = tELPHONE_FORMAT;
	}

	public String getCELLPHONE_FORMAT() {
		return CELLPHONE_FORMAT;
	}

	public void setCELLPHONE_FORMAT(String cELLPHONE_FORMAT) {
		CELLPHONE_FORMAT = cELLPHONE_FORMAT;
	}

	public String getFAX_NUMBER_FORMAT() {
		return FAX_NUMBER_FORMAT;
	}

	public void setFAX_NUMBER_FORMAT(String fAX_NUMBER_FORMAT) {
		FAX_NUMBER_FORMAT = fAX_NUMBER_FORMAT;
	}

	public List<SkillsProgram> getSkillsProgramByUnitStandardsList() {
		return skillsProgramByUnitStandardsList;
	}

	public void setSkillsProgramByUnitStandardsList(List<SkillsProgram> skillsProgramByUnitStandardsList) {
		this.skillsProgramByUnitStandardsList = skillsProgramByUnitStandardsList;
	}

	public int getResultsForSkillsProgramFound() {
		return resultsForSkillsProgramFound;
	}

	public void setResultsForSkillsProgramFound(int resultsForSkillsProgramFound) {
		this.resultsForSkillsProgramFound = resultsForSkillsProgramFound;
	}

	public TrainingProviderDocParent getDocParent() {
		return docParent;
	}

	public void setDocParent(TrainingProviderDocParent docParent) {
		this.docParent = docParent;
	}

	public String getDocNode() {
		return docNode;
	}

	public void setDocNode(String docNode) {
		this.docNode = docNode;
	}

	public Long getMaxFileSize() {
		return maxFileSize;
	}

	public void setMaxFileSize(Long maxFileSize) {
		this.maxFileSize = maxFileSize;
	}

	public Qualification getLearningProgrammeQualification() {
		return learningProgrammeQualification;
	}

	public void setLearningProgrammeQualification(Qualification learningProgrammeQualification) {
		this.learningProgrammeQualification = learningProgrammeQualification;
	}

	public void clearLearningProgrammeQualification() {
		learningProgrammeQualification = new Qualification();
	}

	public void prepareLearningProgrammes() {
		QualificationService qualificationService = new QualificationService();
		try {
			if (learningProgrammeQualification != null) {
				learningProgrammeQualificationList = qualificationService.findLearningProgrammeByQual(null, learningProgrammeQualification);
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	public List<Qualification> getLearningProgrammeQualificationList() {
		return learningProgrammeQualificationList;
	}

	public void setLearningProgrammeQualificationList(List<Qualification> learningProgrammeQualificationList) {
		this.learningProgrammeQualificationList = learningProgrammeQualificationList;
	}

	public Boolean getDoneQualification() {
		return doneQualification;
	}

	public void setDoneQualification(Boolean doneQualification) {
		this.doneQualification = doneQualification;
	}

	public List<LegacyProviderQualification> getLegacyProviderQualificationList() {
		return legacyProviderQualificationList;
	}

	public void setLegacyProviderQualificationList(List<LegacyProviderQualification> legacyProviderQualificationList) {
		this.legacyProviderQualificationList = legacyProviderQualificationList;
	}

	public List<LegacyProviderLearnership> getLegacyProviderLearnershipList() {
		return legacyProviderLearnershipList;
	}

	public void setLegacyProviderLearnershipList(List<LegacyProviderLearnership> legacyProviderLearnershipList) {
		this.legacyProviderLearnershipList = legacyProviderLearnershipList;
	}

	public List<LegacyProviderUnitStandard> getLegacyProviderUnitStandardList() {
		return legacyProviderUnitStandardList;
	}

	public void setLegacyProviderUnitStandardList(List<LegacyProviderUnitStandard> legacyProviderUnitStandardList) {
		this.legacyProviderUnitStandardList = legacyProviderUnitStandardList;
	}

	public List<LegacyProviderSkillsProgramme> getLegacyProviderSkillsProgrammeList() {
		return legacyProviderSkillsProgrammeList;
	}

	public void setLegacyProviderSkillsProgrammeList(List<LegacyProviderSkillsProgramme> legacyProviderSkillsProgrammeList) {
		this.legacyProviderSkillsProgrammeList = legacyProviderSkillsProgrammeList;
	}

	public Boolean getShowSubmit() {
		return showSubmit;
	}

	public void setShowSubmit(Boolean showSubmit) {
		this.showSubmit = showSubmit;
	}

	public Boolean getEdit() {
		return edit;
	}

	public void setEdit(Boolean edit) {
		this.edit = edit;
	}

	public List<LegacyProviderSkillsProgramme> getLegacyProviderSkillsProgrammeSkillsSetList() {
		return legacyProviderSkillsProgrammeSkillsSetList;
	}

	public void setLegacyProviderSkillsProgrammeSkillsSetList(List<LegacyProviderSkillsProgramme> legacyProviderSkillsProgrammeSkillsSetList) {
		this.legacyProviderSkillsProgrammeSkillsSetList = legacyProviderSkillsProgrammeSkillsSetList;
	}

	public String getValidiationErrors() {
		return validiationErrors;
	}

	public void setValidiationErrors(String validiationErrors) {
		this.validiationErrors = validiationErrors;
	}

	public Boolean getValidiationPassed() {
		return validiationPassed;
	}

	public void setValidiationPassed(Boolean validiationPassed) {
		this.validiationPassed = validiationPassed;
	}

	public String getValidiationErrorsLegacySdp() {
		return validiationErrorsLegacySdp;
	}

	public void setValidiationErrorsLegacySdp(String validiationErrorsLegacySdp) {
		this.validiationErrorsLegacySdp = validiationErrorsLegacySdp;
	}

	public LazyDataModel<TrainingSite> getTrainingSiteDataModel() {
		return trainingSiteDataModel;
	}

	public void setTrainingSiteDataModel(LazyDataModel<TrainingSite> trainingSiteDataModel) {
		this.trainingSiteDataModel = trainingSiteDataModel;
	}

	public SDPCompany getNewSdpCompany() {
		return newSdpCompany;
	}

	public void setNewSdpCompany(SDPCompany newSdpCompany) {
		this.newSdpCompany = newSdpCompany;
	}

	public LazyDataModel<SDPCompany> getSdpCompanyDataModel() {
		return sdpCompanyDataModel;
	}

	public void setSdpCompanyDataModel(LazyDataModel<SDPCompany> sdpCompanyDataModel) {
		this.sdpCompanyDataModel = sdpCompanyDataModel;
	}

	public AssessorModeratorCompanySites getNewAssModLink() {
		return newAssModLink;
	}

	public void setNewAssModLink(AssessorModeratorCompanySites newAssModLink) {
		this.newAssModLink = newAssModLink;
	}

	public LazyDataModel<AssessorModeratorCompanySites> getAssessorModeratorCompanySitesDataModel() {
		return assessorModeratorCompanySitesDataModel;
	}

	public void setAssessorModeratorCompanySitesDataModel(
			LazyDataModel<AssessorModeratorCompanySites> assessorModeratorCompanySitesDataModel) {
		this.assessorModeratorCompanySitesDataModel = assessorModeratorCompanySitesDataModel;
	}

	public Boolean getApplicationForSiteDisplay() {
		return applicationForSiteDisplay;
	}

	public void setApplicationForSiteDisplay(Boolean applicationForSiteDisplay) {
		this.applicationForSiteDisplay = applicationForSiteDisplay;
	}

	public Boolean getNewSite() {
		return newSite;
	}

	public void setNewSite(Boolean newSite) {
		this.newSite = newSite;
	}

	public List<SDPCompany> getNewSDPCompanyList() {
		return newSDPCompanyList;
	}

	public void setNewSDPCompanyList(List<SDPCompany> newSDPCompanyList) {
		this.newSDPCompanyList = newSDPCompanyList;
	}

	public List<AssessorModeratorCompanySites> getNewAssessorModeratorCompanySitesList() {
		return newAssessorModeratorCompanySitesList;
	}

	public void setNewAssessorModeratorCompanySitesList(
			List<AssessorModeratorCompanySites> newAssessorModeratorCompanySitesList) {
		this.newAssessorModeratorCompanySitesList = newAssessorModeratorCompanySitesList;
	}

	public TrainingSite getAssignedTrainingSite() {
		return assignedTrainingSite;
	}

	public void setAssignedTrainingSite(TrainingSite assignedTrainingSite) {
		this.assignedTrainingSite = assignedTrainingSite;
	}

	public Boolean getLinkSiteToApplication() {
		return linkSiteToApplication;
	}

	public void setLinkSiteToApplication(Boolean linkSiteToApplication) {
		this.linkSiteToApplication = linkSiteToApplication;
	}

	public Boolean getPrimarySdpLinked() {
		return primarySdpLinked;
	}

	public void setPrimarySdpLinked(Boolean primarySdpLinked) {
		this.primarySdpLinked = primarySdpLinked;
	}

	public String getSetmisValidiationSite() {
		return setmisValidiationSite;
	}

	public void setSetmisValidiationSite(String setmisValidiationSite) {
		this.setmisValidiationSite = setmisValidiationSite;
	}

	public String getSetmisValidiationHoldingCompany() {
		return setmisValidiationHoldingCompany;
	}

	public void setSetmisValidiationHoldingCompany(String setmisValidiationHoldingCompany) {
		this.setmisValidiationHoldingCompany = setmisValidiationHoldingCompany;
	}

}
