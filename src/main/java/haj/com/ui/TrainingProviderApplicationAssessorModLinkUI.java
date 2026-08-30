package haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.Address;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.Blank;
import haj.com.entity.ProviderApplicationTradeTestAssessorsLink;
import haj.com.entity.SDPCompany;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.Users;
import haj.com.entity.UsersDisability;
import haj.com.entity.UsersLanguage;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AssessorModeratorAppTypeEnum;
import haj.com.entity.enums.AssessorModeratorTypeEnum;
import haj.com.entity.enums.IdPassportEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.DisabilityRating;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SdpType;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;
import haj.com.service.AssessorModeratorApplicationService;
import haj.com.service.ProviderApplicationTradeTestAssessorsLinkService;
import haj.com.service.RegisterService;
import haj.com.service.SDPCompanyService;
import haj.com.service.TrainingProviderApplicationService;
import haj.com.service.UserQualificationsService;
import haj.com.service.UsersDisabilityService;
import haj.com.service.UsersLanguageService;
import haj.com.service.UsersService;
import haj.com.service.lookup.DisabilityRatingService;
import haj.com.service.lookup.EtqaService;
import haj.com.utils.GenericUtility;
import haj.com.validators.CheckID;

@ManagedBean(name = "trainingProviderApplicationAssessorModLinkUI")
@ViewScoped
public class TrainingProviderApplicationAssessorModLinkUI extends AbstractUI {

	/* Entity Level */
	private Users user = null;
	private Users userView = null;
	private SdpType sdpType = null;
	private SDPCompany sdpCompanyLink = null;
	private UsersLanguage selectedUserLanguage = null;
	private UsersDisability selectedUserDisability = null;
	private TrainingProviderApplication tpa = null;
	private Qualification selectedQualification = null;
	private AssessorModeratorApplication assessorModeratorApplication = null;
	private AssessorModeratorApplication assessorModeratorApplicationView = null;
	private ProviderApplicationTradeTestAssessorsLink providerApplicationTradeTestAssessorsLink = null;
	private ProviderApplicationTradeTestAssessorsLink providerApplicationTradeTestAssessorsLinkView = null;
	
	/* Service Levels */
	private EtqaService etqaService = new EtqaService();
	private UsersService usersService = new UsersService();
	private RegisterService registerService = new RegisterService();
	private SDPCompanyService sdpCompanyService = new SDPCompanyService();
	private UsersLanguageService usersLanguageService = new UsersLanguageService();
	private UsersDisabilityService usersDisabilityService = new UsersDisabilityService();
	private DisabilityRatingService disabilityRatingService = new DisabilityRatingService();
	private UserQualificationsService userQualificationsService = new UserQualificationsService();
	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
	private AssessorModeratorApplicationService assessorModeratorApplicationService = new AssessorModeratorApplicationService();
	private ProviderApplicationTradeTestAssessorsLinkService applicationTradeTestAssessorsLinkService = new ProviderApplicationTradeTestAssessorsLinkService();

	/* Lazy Data Models */
	private LazyDataModel<ProviderApplicationTradeTestAssessorsLink> ttcAssessorModLinkDataModel;
	
	/* Array Lists */
	private List<UsersLanguage> userLanguageList = new ArrayList<>();
	private List<Qualification> qualificationList = new ArrayList<>();
	private List<Qualification> qualificationListView = new ArrayList<>();
	private List<UsersDisability> userDisabilityList = new ArrayList<>();
	
	/* Vars */
	private AssessorModeratorAppTypeEnum applicationType;
	private boolean canCreate;
	private boolean canAlterApplicationInfo;
	private boolean canAlterApplicationCertificateNumber;
	private boolean samePostal;
	private boolean alterUserLanguages;
	private boolean alterUserDisability;
	private String setmisValidiation = "";
	
	/* User Search Information Start */
	private IdPassportEnum idpassport;
	@CheckID(message="Not a valid RSA ID number")
	private String idnumber;
	private String passportNumber;

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
	 * Initialize method to get all Blank and prepare a for a create of a new
	 * Blank.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Blank
	 */
	private void runInit() throws Exception {
	}

	public void viewLinkInformation(){
		try {
			assessorModeratorApplicationView = assessorModeratorApplicationService.findByKey(providerApplicationTradeTestAssessorsLinkView.getAssessorModeratorApplication().getId());
			qualificationListView = userQualificationsService.findQualificationByAssessModeratorAppllicationWhereApproved(assessorModeratorApplicationView.getId(), true);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void selectApplicationForTtcAssessorModView() {
		try {
			providerApplicationTradeTestAssessorsLinkView = null;
			applicationType = null;
			populateSdpTypeTTC();
			validiateCanViewTtcInformation();
			ttcAssessorModLinkDataModelInfo();
			determainCanCreate();
			if (canCreate) {
				prepForNewEntry();
			}	
		} catch (Exception e) {
			tpa = null;
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void determainCanCreate() throws Exception{
		// default to false
		canCreate = false;
		
		// got permission to action
		if (sdpType != null && sdpType.getActionTradeTestCentreAssessors() != null && sdpType.getActionTradeTestCentreAssessors()) {
			// provider application is approved
			if (tpa != null && tpa.getApprovalStatus() != null && tpa.getApprovalStatus() == ApprovalEnum.Approved) {
				canCreate = true;
			}
		}
	}

	public void populateSdpTypeTTC() throws Exception {
		if (getSessionUI().isExternalParty()) {
			sdpType = null;
			sdpCompanyLink = null;
			// new sdp company link
			if (tpa != null ) {
				if (tpa.getTrainingSite() != null && tpa.getTrainingSite().getId() != null) {
					sdpCompanyLink = sdpCompanyService.findBySdpIdCompanyIdAndTrainingSiteIdReturnOneResult(getSessionUI().getActiveUser().getId(), tpa.getCompany().getId(), tpa.getTrainingSite().getId());
				} else {
					sdpCompanyLink = sdpCompanyService.findBySdpIdAndCompanyIdReturnOneResult(getSessionUI().getActiveUser().getId(), tpa.getCompany().getId());
				}
				if (sdpCompanyLink != null) {
					sdpType = sdpCompanyLink.getSdpType();
				}
			}
			// fail safe to use old version
			if (sdpType == null) {
				if (tpa != null && tpa.getCompany() != null && tpa.getId() != null) {
					sdpType = trainingProviderApplicationService.locateSdpTypeByApplicationAndSessionUser(tpa, getSessionUI().getActiveUser());
				}
			}
		}
	}

	public void validiateCanViewTtcInformation() throws Exception {
		if (getSessionUI().isExternalParty()) {
			if (sdpType == null || sdpType.getViewTradeTestCentreAssessors() == null || !sdpType.getViewTradeTestCentreAssessors()) {
				tpa = null;
				throw new Exception("You currently do not have access to view the information");
			}
		}
	}

	public void ttcAssessorModLinkDataModelInfo() {
		ttcAssessorModLinkDataModel = new LazyDataModel<ProviderApplicationTradeTestAssessorsLink>() {
			
			private static final long serialVersionUID = 1L;
			private List<ProviderApplicationTradeTestAssessorsLink> retorno = new ArrayList<>();
			
			@Override
			public List<ProviderApplicationTradeTestAssessorsLink> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = applicationTradeTestAssessorsLinkService.allByProviderApplicationResolveAssessorInformation(first, pageSize, sortField, sortOrder, filters, tpa.getId());
					ttcAssessorModLinkDataModel.setRowCount(applicationTradeTestAssessorsLinkService.countAllByProviderApplication(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			
			@Override
			public Object getRowKey(ProviderApplicationTradeTestAssessorsLink obj) {
				return obj.getId();
			}
			
			@Override
			public ProviderApplicationTradeTestAssessorsLink getRowData(String rowKey) {
				for (ProviderApplicationTradeTestAssessorsLink obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void onTabChange(TabChangeEvent event) {
		try {
			if (event != null && event.getTab() != null && event.getTab().getId() != null) {
				switch (event.getTab().getId()) {
				case "viewAllInfoTab":
					assessorModeratorApplicationView = null;
					providerApplicationTradeTestAssessorsLinkView = null;
					ttcAssessorModLinkDataModelInfo();
					break;
				case "createNewEntryTab":
					prepForNewEntry();
					break;
				default:
					break;
				}
			} else {
				ttcAssessorModLinkDataModelInfo();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepForNewEntry() {
		applicationType = null;
		clearCriteria();
		qualificationList.clear();
		userLanguageList.clear();
		userDisabilityList.clear();
		setmisValidiation = "";
		user = null;
		samePostal = false;
		newAssessorModeratorApplication();
		providerApplicationTradeTestAssessorsLink = new ProviderApplicationTradeTestAssessorsLink();			
	}

	/**
	 * 
	 */
	public void newAssessorModeratorApplication() {
		assessorModeratorApplication = new AssessorModeratorApplication();
		assessorModeratorApplication.setDuplicateApplication(false);
		assessorModeratorApplication.setAssessorModeratorType(AssessorModeratorTypeEnum.TTC_AM);
		assessorModeratorApplication.setStatus(ApprovalEnum.Approved);
		assessorModeratorApplication.setCreateUser(getSessionUI().getActiveUser());
		assessorModeratorApplication.setApplicationType(applicationType);
		try {
			assessorModeratorApplication.setEtqa(etqaService.findByCode(HAJConstants.QCTO_ETQA_CODE));
		} catch (Exception e) {
			logger.fatal(e);
		}
	}
	
	public void clearSearch(){
		try {
			prepForNewEntry();
			addInfoMessage("Clear Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void selectRegType(){
		clearCriteria();
		qualificationList.clear();
		setmisValidiation = "";
		user = null;
		samePostal = false;
		newAssessorModeratorApplication();
		providerApplicationTradeTestAssessorsLink = new ProviderApplicationTradeTestAssessorsLink();	
	}
	
	public void clearCriteria() {
		this.idnumber = "";
		this.passportNumber = "";
		this.idpassport = null;
	}
	
	public void findUserByIDOrPassport() throws Exception {
		switch (idpassport) {
		case RsaId:
			user = usersService.findByRsaIdJoinAddress(this.idnumber);
			break;
		case Passport:
			user = usersService.findByPassportNumberJoinAddress(this.passportNumber);
			break;
		default:
			user = new Users();
			break;
		}
		if (user == null) {
			user = new Users();
			user.setRsaIDNumber(idnumber);
			user.setPassportNumber(passportNumber);
			user.setExistingUser(false);
			user.setRegFieldsDone(false);
			user.setPostalAddress(new Address());
			user.setResidentialAddress(new Address());
			if (idpassport == IdPassportEnum.RsaId) {
				try {
					GenericUtility.calcIDData(user);
				} catch (Exception e) {
					addErrorMessage(e.getMessage(), e);
				}
			}
			alterUserLanguages = true;
			selectedUserLanguage = new UsersLanguage();
			alterUserDisability = true;
			selectedUserDisability = new UsersDisability();
		} else {
			user.setExistingUser(true);
			user.setRegFieldsDone(true);
			usersService.resolveUserAddresses(user);
			if (user.getPostalAddress() == null || user.getPostalAddress().getId() == null) {
				user.setPostalAddress(new Address());
			}
			if (user.getResidentialAddress() == null || user.getResidentialAddress().getId() == null) {
				user.setResidentialAddress(new Address());
			}
			
			alterUserLanguages = false;
			alterUserDisability = false;
			
			// populate user language list
			userLanguageList = usersLanguageService.findByUser(user);
			if (userLanguageList.isEmpty()) {
				alterUserLanguages = true;
				selectedUserLanguage = new UsersLanguage();
			}
			// populate user disability list
			userDisabilityList = usersDisabilityService.findByKeyUser(user);
			if (user.getDisability() != null) {
				if (user.getDisability() == YesNoEnum.Yes) {
					if (userDisabilityList.isEmpty()) {
						alterUserDisability = true;
						selectedUserDisability = new UsersDisability();
					}
				} 
			} else {
				alterUserDisability = true;
				selectedUserDisability = new UsersDisability();
			}
		}
		user.setDoneSearch(true);
		usersService.identifyFieldAlteration(user);
		if (user.getId() != null) {
			// check if user has an assessor mod application for ttc
			if (applicationType == AssessorModeratorAppTypeEnum.NewAssessorRegistration) {
				assessorModeratorApplication = assessorModeratorApplicationService.findByUserForAssessorModeratorTypeAndApplicationType(user.getId(), AssessorModeratorTypeEnum.TTC_AM, AssessorModeratorAppTypeEnum.getAssessorValuesTTC());
			} else if(applicationType == AssessorModeratorAppTypeEnum.NewModeratorRegistration) {
				assessorModeratorApplication = assessorModeratorApplicationService.findByUserForAssessorModeratorTypeAndApplicationType(user.getId(), AssessorModeratorTypeEnum.TTC_AM, AssessorModeratorAppTypeEnum.getModeratorValuesTTC());
			} else {
				assessorModeratorApplication = assessorModeratorApplicationService.findByUserForAssessorModeratorType(user.getId(), AssessorModeratorTypeEnum.TTC_AM);
			}
			if (assessorModeratorApplication == null || assessorModeratorApplication.getId() == null) {
				// can only alter if not application or end date is past today
				canAlterApplicationInfo = true;
				canAlterApplicationCertificateNumber = true;
				newAssessorModeratorApplication();
				assessorModeratorApplication.setUser(user);
			} else {
				// add checks to see if user already linked
				boolean alreadyLinked = (applicationTradeTestAssessorsLinkService.countByProviderApplicationAndAssessorModApp(tpa.getId(), assessorModeratorApplication.getId()) > 0);
				if (alreadyLinked && getNow().before(assessorModeratorApplication.getEndDate())) {
					addErrorMessage("User already linked to provider application! Please add a different user or registration type.");
					prepForNewEntry();
				} else {
					qualificationList = userQualificationsService.findQualificationByAssessModeratorAppllicationWhereApproved(assessorModeratorApplication.getId(), true);
					if (assessorModeratorApplication.getEndDate() != null && getNow().after(assessorModeratorApplication.getEndDate())) {
						canAlterApplicationCertificateNumber = true;
						if (assessorModeratorApplication.getApplicationType() == AssessorModeratorAppTypeEnum.NewAssessorRegistration) {
							assessorModeratorApplication.setApplicationType(AssessorModeratorAppTypeEnum.AssessorReRegistration);
							assessorModeratorApplication.setStatus(ApprovalEnum.Approved);
							canAlterApplicationCertificateNumber = false;
						} else if (assessorModeratorApplication.getApplicationType() == AssessorModeratorAppTypeEnum.NewModeratorRegistration) {
							assessorModeratorApplication.setApplicationType(AssessorModeratorAppTypeEnum.ModeratorReRegistration);
							assessorModeratorApplication.setStatus(ApprovalEnum.Approved);
							canAlterApplicationCertificateNumber = false;
						}
						canAlterApplicationInfo = true;
					} else {
						canAlterApplicationInfo = false;
						canAlterApplicationCertificateNumber = false;
					}
					clearCriteria();
				}
			}
		} else {
			// can alter the application information since new user
			canAlterApplicationInfo = true;
			canAlterApplicationCertificateNumber = true;
			newAssessorModeratorApplication();
			assessorModeratorApplication.setUser(user);
			assessorModeratorApplication.setApplicationType(applicationType);
			clearCriteria();
		}
	}
	
	public List<DisabilityRating> getSelectItemsUsersDisabilityRating() {
		List<DisabilityRating> l = null;
		try {
			if (this.selectedUserDisability.getDisabilityStatus() != null) {
				l = disabilityRatingService.findByDisability(this.selectedUserDisability.getDisabilityStatus().getId());
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	
	public void prepNewDisabilityOnSelection(){
		try {
			if (user.getDisability() != null && user.getDisability() == YesNoEnum.Yes) {
				selectedUserDisability = new UsersDisability();
				userDisabilityList.clear();
			} else {
				selectedUserDisability = null;
				userDisabilityList.clear();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	public void addDisabilityToList(){
		try {
			StringBuilder validiationError = new StringBuilder();
			
			if (selectedUserDisability.getDisabilityStatus() == null) {
				validiationError.append("Provide Disability. ");
			}
			
			if (selectedUserDisability.getDisabilityRating() == null) {
				validiationError.append("Provide Disability Rating. ");
			}
			
			if (validiationError.toString().isEmpty()) {
				
				boolean disabilityInList = false;
				for (UsersDisability inlist : userDisabilityList) {
					if (inlist.getDisabilityStatus().getId().equals(selectedUserDisability.getDisabilityStatus().getId())) {
						disabilityInList = true;
						break;
					}
				}
				
				if (!disabilityInList) {
					userDisabilityList.add(selectedUserDisability);
					selectedUserDisability = new UsersDisability();
					addInfoMessage("Action Complete");
				} else {
					addErrorMessage("Disability has already been added. Please select a different disability.");
				}
			} else {
				addErrorMessage("Unable to submit disability. Please attened to the following before subimtting: "+validiationError.toString());
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void removeDisability() {
		try {
			userDisabilityList.remove(selectedUserDisability);
			if (userDisabilityList.isEmpty()) {
				userDisabilityList.clear();
			}
			selectedUserDisability = new UsersDisability();
			addWarningMessage("Entry Removed");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void clearDisabilityToList(){
		try {
			userDisabilityList.clear();
			addWarningMessage("Disability List Cleared");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addUserLanguageToList(){
		try {
			StringBuilder validiationError = new StringBuilder();
			
			if (selectedUserLanguage.getLanguage() == null) {
				validiationError.append("Provide language. ");
			}
			
			if (selectedUserLanguage.getSpeark() == null) {
				validiationError.append("Provide if the user can speak the language. ");
			}
			
			if (selectedUserLanguage.getRead() == null) {
				validiationError.append("Provide if the user can read the language. ");
			}
			
			if (selectedUserLanguage.getWrite() == null) {
				validiationError.append("Provide if the user can write the language. ");
			}
			
			if (selectedUserLanguage.getHomeLanguage() == null) {
				validiationError.append("Provide if it's a home language for the user. ");
			}
			
			if (validiationError.toString().isEmpty()) {
				boolean languageInList = false;
				for (UsersLanguage inlist : userLanguageList) {
					if (inlist.getLanguage().getId().equals(selectedUserLanguage.getLanguage().getId())) {
						languageInList = true;
						break;
					}
				}
				if (!languageInList) {
					
					boolean homeLanguageError = false;
					if (selectedUserLanguage.getHomeLanguage()) {
						for (UsersLanguage entry : userLanguageList) {
							if (entry.getHomeLanguage()) {
								homeLanguageError = true;
								break;
							}
						}
					}
					
					if (homeLanguageError) {
						addErrorMessage("SETMIS Validiation Error: A home language has already been added. You can only have one home language assigned to the user.");
					} else {
						userLanguageList.add(selectedUserLanguage);
						selectedUserLanguage = new UsersLanguage();
						addInfoMessage("Action Complete");
					}
				} else {
					addErrorMessage("Language has already been added. Please select a different language.");
				}
			} else {
				addErrorMessage("Unable to submit user language. Please attened to the following before subimtting: "+validiationError.toString());
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void removeUserLanguage() {
		try {
			userLanguageList.remove(selectedUserLanguage);
			if (userLanguageList.isEmpty()) {
				userLanguageList.clear();
			}
			selectedUserLanguage = new UsersLanguage();
			addWarningMessage("Entry Removed");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addQualificationToList(){
		try {
			if (selectedQualification == null) {
				addErrorMessage("Please select a qualification before submitting");
			} else {
				boolean addToList = true;
				for (Qualification qualification : qualificationList) {
					if (qualification.getId().equals(selectedQualification.getId())) {
						addToList = false;
						break;
					}
				}
				if (addToList) {
					qualificationList.add(selectedQualification);
				} else {
					addWarningMessage("Qualification already added to the list");
				}
				selectedQualification = null;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void clearQualificationList(){
		try {
			qualificationList.clear();
			selectedQualification = null;
			addWarningMessage("Qualification List Cleared");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void removeQualification() {
		try {
//			qualificationList.removeIf(pr -> pr.getId().equals(selectedQualification.getId()));
			qualificationList.remove(selectedQualification);
			if (qualificationList.isEmpty()) {
				qualificationList.clear();
			}
			selectedQualification = null;
			addWarningMessage("Entry Removed");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void submitEntry() {
		try {
			// validate user email
			usersService.validateEmailAddressByUser(user);
			// Validate user data 
			setmisValidiation = "";
			StringBuilder allErrors = new StringBuilder();
			// Validate user info
			allErrors.append(usersService.validiateUserInformation(user));
			// Validate application info
			
			// Validate application info
			if (canAlterApplicationInfo && qualificationList.isEmpty()) {
				allErrors.append("Provide Atleast One Qualification before proceeding.");
				allErrors.append("<br/>");
			}
			if (canAlterApplicationInfo || canAlterApplicationCertificateNumber) {
				allErrors.append(assessorModeratorApplicationService.validiateSetmisInformation(assessorModeratorApplication));
				allErrors.append(assessorModeratorApplicationService.additionalValidiationChecks(assessorModeratorApplication));
				// check if end date after today 
			}
			
			if (alterUserLanguages) {
				if (userLanguageList.isEmpty()) {
					allErrors.append("Provide a minium of one language for the user before proceeding. <br/>");
				} else {
					boolean containsHomeLanguage = false;
					for (UsersLanguage lang : userLanguageList) {
						if (lang.getHomeLanguage() != null && lang.getHomeLanguage()) {
							containsHomeLanguage = true;
							break;
						}
					}
					if (!containsHomeLanguage) {
						allErrors.append("Provide a minium of one home language for the user before proceeding. <br/>");
					}
				}
			}
			
			if (alterUserDisability) {
				if (user.getDisability() != null) {
					if (user.getDisability() == YesNoEnum.Yes && userDisabilityList.isEmpty()) {
						allErrors.append("Provide a minium of one disbility for the user before proceeding. Indicated on user they have a disability.<br/>");
					}
				} else {
					allErrors.append("Provide if the user has a disablity.<br/>");
				}
			}
			
			if (!allErrors.toString().trim().isEmpty()) {
				setmisValidiation = allErrors.toString().trim();
				addWarningMessage("Validation Failed. Please refer to error messages before proceeding.");
			} else {

				Address residentialAddress = null;
				Address postalAddress = null;
				
				// register user
				if (user.getId() == null) {
					user.setAcceptPopi(false);
					user.setAdmin(false);
					// create address
					if (samePostal) {
						AddressService.instance().copyFromToAddress(user.getResidentialAddress(), user.getPostalAddress());
					}
					residentialAddress = user.getResidentialAddress();
					postalAddress = user.getPostalAddress();
					AddressService.instance().saveAddresses(residentialAddress, postalAddress);
					user.setResidentialAddress(residentialAddress);
					user.setPostalAddress(postalAddress);
					user = registerService.register(user, true);
				} else {
					
					// update address
					if (user.getPostalAddress().getId() == null && samePostal) {
						AddressService.instance().copyFromToAddress(user.getResidentialAddress(), user.getPostalAddress());
					}
					AddressService.instance().saveAddresses(user.getResidentialAddress(), user.getPostalAddress());
					// update user info 
					usersService.update(user);
				}
				
				if (alterUserLanguages) {
					usersLanguageService.createLinkToUser(userLanguageList, user, getSessionUI().getActiveUser());
				}
				
				if (alterUserDisability) {
					if (user.getDisability() == YesNoEnum.Yes) {
						usersDisabilityService.createLinkToUser(userDisabilityList, user, getSessionUI().getActiveUser());
					}
				}
				
				postalAddress = null;
				residentialAddress = null;
				
				if (canAlterApplicationInfo) {
					assessorModeratorApplication.setLastUpdateUser(getSessionUI().getActiveUser());
					assessorModeratorApplication.setLastUpdateDate(getNow());
					assessorModeratorApplicationService.create(assessorModeratorApplication);
					assessorModeratorApplicationService.createUpdateAssessorModQualificationsTradeTestUsers(assessorModeratorApplication, qualificationList, getSessionUI().getActiveUser());
				}
				
				// create link
				providerApplicationTradeTestAssessorsLink = applicationTradeTestAssessorsLinkService.findByProviderApplicationAndAssessorModApp(tpa.getId(), assessorModeratorApplication.getId());
				if (providerApplicationTradeTestAssessorsLink != null && providerApplicationTradeTestAssessorsLink.getId() != null) {
					providerApplicationTradeTestAssessorsLink.setUpdateDate(getNow());
					providerApplicationTradeTestAssessorsLink.setUpdateUser(getSessionUI().getActiveUser());	
				} else {
					providerApplicationTradeTestAssessorsLink = new ProviderApplicationTradeTestAssessorsLink();
					providerApplicationTradeTestAssessorsLink.setAssessorModeratorApplication(assessorModeratorApplication);
					providerApplicationTradeTestAssessorsLink.setTrainingProviderApplication(tpa);
					providerApplicationTradeTestAssessorsLink.setCreateUser(getSessionUI().getActiveUser());
				}
				applicationTradeTestAssessorsLinkService.create(providerApplicationTradeTestAssessorsLink);
				
				addInfoMessage("Action Complete");
				prepForNewEntry();
				ttcAssessorModLinkDataModelInfo();
			}
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiation = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Failed. Please refer to error messages before proceeding.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepNewQualificationScope(){
		try {
			qualificationList.clear();
			selectedQualification = null;
			runClientSideExecute("PF('assModScopeUpdDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void addQualificationToNewScopeList() {
		try {
			// already assigned to qualification
			if (assessorModeratorApplicationService.checkIfApplicationLinkedToQualification(assessorModeratorApplicationView, selectedQualification)) {
				selectedQualification = null;
				throw new Exception("Qualification already linked to application. Please select a different qualification. ");
			}
			// qualification in list already
			boolean alreadyInList = false;
			for (Qualification qualification : qualificationList) {
				if (qualification.getId().equals(selectedQualification.getId())) {
					alreadyInList = true;
					break;
				}
			}
			if (alreadyInList) {
				selectedQualification = null;
				throw new Exception("Qualification already in list, please select a different qualification. ");
			}
			
			qualificationList.add(selectedQualification);
			selectedQualification = null;
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addNewQualificationScope() {
		try {
			if (qualificationList.isEmpty()) {
				throw new Exception("Please assign atleast one qualification before proceeding.");
			}
			assessorModeratorApplicationService.addNewScopeQualifications(assessorModeratorApplicationView, qualificationList, getSessionUI().getActiveUser());
			selectedQualification = null;
			qualificationList.clear();
			runClientSideExecute("PF('assModScopeUpdDlg').hide()");
			addInfoMessage("Action Complete");
			viewLinkInformation();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void cancelNewScope(){
		try {
			selectedQualification = null;
			qualificationList.clear();
			runClientSideExecute("PF('assModScopeUpdDlg').hide()");
			addWarningMessage("Action Complete");
			viewLinkInformation();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	

	/* Getters and Setters */
	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public SdpType getSdpType() {
		return sdpType;
	}

	public void setSdpType(SdpType sdpType) {
		this.sdpType = sdpType;
	}

	public TrainingProviderApplication getTpa() {
		return tpa;
	}

	public void setTpa(TrainingProviderApplication tpa) {
		this.tpa = tpa;
	}

	public AssessorModeratorApplication getAssessorModeratorApplication() {
		return assessorModeratorApplication;
	}

	public void setAssessorModeratorApplication(AssessorModeratorApplication assessorModeratorApplication) {
		this.assessorModeratorApplication = assessorModeratorApplication;
	}

	public ProviderApplicationTradeTestAssessorsLink getProviderApplicationTradeTestAssessorsLink() {
		return providerApplicationTradeTestAssessorsLink;
	}

	public void setProviderApplicationTradeTestAssessorsLink(
			ProviderApplicationTradeTestAssessorsLink providerApplicationTradeTestAssessorsLink) {
		this.providerApplicationTradeTestAssessorsLink = providerApplicationTradeTestAssessorsLink;
	}

	public LazyDataModel<ProviderApplicationTradeTestAssessorsLink> getTtcAssessorModLinkDataModel() {
		return ttcAssessorModLinkDataModel;
	}

	public void setTtcAssessorModLinkDataModel(LazyDataModel<ProviderApplicationTradeTestAssessorsLink> ttcAssessorModLinkDataModel) {
		this.ttcAssessorModLinkDataModel = ttcAssessorModLinkDataModel;
	}

	public AssessorModeratorApplication getAssessorModeratorApplicationView() {
		return assessorModeratorApplicationView;
	}

	public void setAssessorModeratorApplicationView(AssessorModeratorApplication assessorModeratorApplicationView) {
		this.assessorModeratorApplicationView = assessorModeratorApplicationView;
	}

	public ProviderApplicationTradeTestAssessorsLink getProviderApplicationTradeTestAssessorsLinkView() {
		return providerApplicationTradeTestAssessorsLinkView;
	}

	public void setProviderApplicationTradeTestAssessorsLinkView(
			ProviderApplicationTradeTestAssessorsLink providerApplicationTradeTestAssessorsLinkView) {
		this.providerApplicationTradeTestAssessorsLinkView = providerApplicationTradeTestAssessorsLinkView;
	}

	public IdPassportEnum getIdpassport() {
		return idpassport;
	}

	public void setIdpassport(IdPassportEnum idpassport) {
		this.idpassport = idpassport;
	}

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public boolean isCanCreate() {
		return canCreate;
	}

	public void setCanCreate(boolean canCreate) {
		this.canCreate = canCreate;
	}

	public Users getUserView() {
		return userView;
	}

	public void setUserView(Users userView) {
		this.userView = userView;
	}

	public String getSetmisValidiation() {
		return setmisValidiation;
	}

	public void setSetmisValidiation(String setmisValidiation) {
		this.setmisValidiation = setmisValidiation;
	}

	public List<Qualification> getQualificationList() {
		return qualificationList;
	}

	public void setQualificationList(List<Qualification> qualificationList) {
		this.qualificationList = qualificationList;
	}

	public Qualification getSelectedQualification() {
		return selectedQualification;
	}

	public void setSelectedQualification(Qualification selectedQualification) {
		this.selectedQualification = selectedQualification;
	}

	public boolean isCanAlterApplicationInfo() {
		return canAlterApplicationInfo;
	}

	public void setCanAlterApplicationInfo(boolean canAlterApplicationInfo) {
		this.canAlterApplicationInfo = canAlterApplicationInfo;
	}

	public boolean isSamePostal() {
		return samePostal;
	}

	public void setSamePostal(boolean samePostal) {
		this.samePostal = samePostal;
	}

	public AssessorModeratorAppTypeEnum getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(AssessorModeratorAppTypeEnum applicationType) {
		this.applicationType = applicationType;
	}

	public boolean isCanAlterApplicationCertificateNumber() {
		return canAlterApplicationCertificateNumber;
	}

	public void setCanAlterApplicationCertificateNumber(boolean canAlterApplicationCertificateNumber) {
		this.canAlterApplicationCertificateNumber = canAlterApplicationCertificateNumber;
	}

	public List<Qualification> getQualificationListView() {
		return qualificationListView;
	}

	public void setQualificationListView(List<Qualification> qualificationListView) {
		this.qualificationListView = qualificationListView;
	}

	public boolean isAlterUserLanguages() {
		return alterUserLanguages;
	}

	public void setAlterUserLanguages(boolean alterUserLanguages) {
		this.alterUserLanguages = alterUserLanguages;
	}

	public boolean isAlterUserDisability() {
		return alterUserDisability;
	}

	public void setAlterUserDisability(boolean alterUserDisability) {
		this.alterUserDisability = alterUserDisability;
	}

	public List<UsersLanguage> getUserLanguageList() {
		return userLanguageList;
	}

	public void setUserLanguageList(List<UsersLanguage> userLanguageList) {
		this.userLanguageList = userLanguageList;
	}

	public List<UsersDisability> getUserDisabilityList() {
		return userDisabilityList;
	}

	public void setUserDisabilityList(List<UsersDisability> userDisabilityList) {
		this.userDisabilityList = userDisabilityList;
	}

	public UsersLanguage getSelectedUserLanguage() {
		return selectedUserLanguage;
	}

	public void setSelectedUserLanguage(UsersLanguage selectedUserLanguage) {
		this.selectedUserLanguage = selectedUserLanguage;
	}

	public UsersDisability getSelectedUserDisability() {
		return selectedUserDisability;
	}

	public void setSelectedUserDisability(UsersDisability selectedUserDisability) {
		this.selectedUserDisability = selectedUserDisability;
	}
	
	public Date getStartDateMaxDate(){
		return GenericUtility.getEndOfDay(getNow());
	}

	public SDPCompany getSdpCompanyLink() {
		return sdpCompanyLink;
	}

	public void setSdpCompanyLink(SDPCompany sdpCompanyLink) {
		this.sdpCompanyLink = sdpCompanyLink;
	}
}