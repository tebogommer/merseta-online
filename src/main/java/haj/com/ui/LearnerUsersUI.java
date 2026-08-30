package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearnerUsers;
import haj.com.entity.SDPCompany;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.Users;
import haj.com.entity.UsersDisability;
import haj.com.entity.UsersLanguage;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.DisabilityRating;
import haj.com.entity.lookup.LegacyApprenticeship;
import haj.com.entity.lookup.SdpType;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;
import haj.com.service.CompanyLearnerUsersService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.SDFCompanyService;
import haj.com.service.SDPCompanyService;
import haj.com.service.TrainingProviderApplicationService;
import haj.com.service.UsersDisabilityService;
import haj.com.service.UsersLanguageService;
import haj.com.service.UsersService;
import haj.com.service.lookup.DisabilityRatingService;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "learnerUsersUI")
@ViewScoped
public class LearnerUsersUI extends AbstractUI {
	
	private SDFCompanyService sDFCompanyService = new SDFCompanyService();

	/** The service. */
	private CompanyLearnerUsersService service = new CompanyLearnerUsersService();
	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
	private List<Company> companies = null;
	private Company selectedCompany;

	private CompanyUsersService companyUsersService = new CompanyUsersService();
	private boolean viewLearnerData = false;

	
	private TrainingProviderApplication selectedTrainingProvider;
	private Boolean primaryOrSecondarySDF;
	private Boolean companyContactRegisterLearner;
	private SdpType sdpType = null;
	private Boolean registerLearners = false;
	private LazyDataModel<CompanyLearnerUsers> dataModel;
	private CompanyLearnerUsers companyLearnerUsers;
	private boolean viewLearnerDetails = false;
	
	private SDPCompany sdpCompanyLink = null;
	private SDPCompanyService sdpCompanyService = new SDPCompanyService();
	
	
	private ArrayList<UsersLanguage> usersLanguageList    = new ArrayList<>();
	private List<UsersDisability>    usersDisabilityList  = new ArrayList<>();
	
	private UsersLanguage       usersLanguage                 = new UsersLanguage();
	private UsersDisability     usersDisability               = new UsersDisability();
	
	/** The entities. */
	private Company company;
	private Users   user;
	private Users   gaurdian;
	private boolean requireGaurdian = true;
	private boolean minor = false;
	private boolean editFields = false;
	private boolean disableFields = false;
	private boolean             homeLanguageSelected          = false;
	private Address residentialAddress;
	private Address postalAddress;
	private Address legalGaurdianPostalAddress;
	private Address legalGaurdianResidentialAddress;
	private CompanyService companyService = new CompanyService();
	private UsersLanguageService usersLanguageService = new UsersLanguageService();
	private UsersDisabilityService       usersDisabilityService       = new UsersDisabilityService();
	private TrainingProviderApplication trainingProviderApplication;
	
	/** The copy address. */
	private boolean copyAddress;
	private boolean copyAddress2;
	
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

	private void runInit() throws Exception {
		if (getSessionUI().getTask() != null ) {
			getSessionUI().setTask(null);
		}
		if (getSessionUI().isExternalParty()) {
			companies = companyUsersService.findByUserType(getSessionUI().getActiveUser(), ConfigDocProcessEnum.TP);
		} else {
			learnerUsersInfo();
		}
	}
	
	private void learnerUsersInfo() throws Exception {

	}

	public void checkLearnerInfo() {
		prepareNew();
		if (getSessionUI().isExternalParty()) {
			try {
				registerLearners = false;
				primaryOrSecondarySDF = sDFCompanyService.checkIfPrimarOrSecondaryCanRegisterLearners(getSessionUI().getActiveUser(), selectedCompany);
				
				if(!primaryOrSecondarySDF) {
					companyContactRegisterLearner = companyUsersService.checkIfCompanyContactCanRegisterLearner(getSessionUI().getActiveUser(), selectedCompany, ConfigDocProcessEnum.Learner);
					if(companyContactRegisterLearner) {
						registerLearners = true;
						learnersInfo();
					} else {
						addErrorMessage("Kindly be advised that you require the relevant authorisation to access this information");
					}
				} else {
					registerLearners = true;
					learnersInfo();
				}
			} catch (Exception e) {
				addErrorMessage(e.getMessage(), e);
				e.printStackTrace();
			}			
		}else {
			try {
				learnersInfo();
			} catch (Exception e) {
				addErrorMessage(e.getMessage(), e);
				e.printStackTrace();
			}
		}
	}
	
	private void learnersInfoForEmployer() {
		dataModel = new LazyDataModel<CompanyLearnerUsers>() {
			private static final long serialVersionUID = 1L;
			private List<CompanyLearnerUsers> retorno = new ArrayList<CompanyLearnerUsers>();

			@Override
			public List<CompanyLearnerUsers> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (selectedCompany != null) {
						filters.put("companyID", selectedCompany.getId());
						retorno = service.allLearners(first, pageSize, sortField, sortOrder, filters);						
						dataModel.setRowCount(service.countLearners(CompanyLearnerUsers.class, filters));
					}
				} catch (Exception e) {
					addErrorMessage(e.getMessage(), e);
					logger.fatal(e);
					e.printStackTrace();
				}
				return retorno;
			}

			@Override
			public Object getRowKey(CompanyLearnerUsers obj) {
				return obj.getId();
			}

			@Override
			public CompanyLearnerUsers getRowData(String rowKey) {
				for (CompanyLearnerUsers obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}

	private void prepareNew() {
		
	}
	
	public void prepareLearner() {
		try {
			if(companyLearnerUsers != null) {
				this.company = companyLearnerUsers.getCompany();
				companyService.resolveCompanyAddresses(company);
				trainingProviderApplication = companyLearnerUsers.getTrainingProviderApplication();
				this.user = companyLearnerUsers.getUser();
				resolveAddresses();
				loadUserLanguage();
				loadUsersDisability();
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);			
		}
	}
	
	/**
	 * Resolve addresses.
	 * @throws Exception 
	 */
	public void resolveAddresses() throws Exception {
		if (user.getId() != null) {
			if (this.user.getResidentialAddress() != null) {
				this.residentialAddress = this.user.getResidentialAddress();
			} else {
				this.residentialAddress = new Address();
			}
			if (this.user.getPostalAddress() != null) {
				this.postalAddress = AddressService.instance().findByKey(this.user.getPostalAddress().getId());
				if (postalAddress.getSameAddress() != null) {
					this.copyAddress = this.postalAddress.getSameAddress();
				}
			} else {
				this.postalAddress = new Address();
				this.copyAddress = false;
			}			
		}

	}
	
	public void loadUserLanguage() {
		try {
			if (user.getId() != null) {
				usersLanguageList = (ArrayList<UsersLanguage>) usersLanguageService.findByUser(user);
				for (UsersLanguage ul : usersLanguageList) {
					if (ul.getHomeLanguage() != null && ul.getHomeLanguage()) {
						homeLanguageSelected=true;
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void loadUsersDisability() {
		try {
			if (user.getId() != null) {
				usersDisabilityList = usersDisabilityService.findByKeyUser(this.user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void clearDisabilityRating() {
		try {
			this.user.setDisabilityRating(null);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public List<DisabilityRating> getSelectItemsUsersDisabilityRating() {
		DisabilityRatingService disabilityRatingService = new DisabilityRatingService();
		List<DisabilityRating>  l                       = null;
		try {
			if (this.usersDisability.getDisabilityStatus() != null) {
				l = disabilityRatingService.findByDisability(this.usersDisability.getDisabilityStatus().getId());
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public void addUsersDisability() {
		try {
			usersDisabilityPreCheck();
			if (usersDisability != null && usersDisability.getDisabilityStatus() != null) {
				usersDisabilityList.add(usersDisability);
				usersDisability = new UsersDisability();
			} else {
				addErrorMessage("Select a disability and disability rating");
			}

		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void usersDisabilityPreCheck() throws Exception {
		for (UsersDisability ud : usersDisabilityList) {
			if (ud != null && ud.getDisabilityStatus() != null && usersDisability != null && ud.getDisabilityStatus().getId() == usersDisability.getDisabilityStatus().getId()) {
				throw new Exception("Disability already exist on your disability list");
			}
		}
	}

	public void learnersInfo() throws Exception {
		/*if (registerLearners == null || !registerLearners) {
			populateSdpType();
			validiateCanViewInformation();
		}*/
		dataModel = new LazyDataModel<CompanyLearnerUsers>() {
			private static final long serialVersionUID = 1L;
			private List<CompanyLearnerUsers> retorno = new ArrayList<CompanyLearnerUsers>();

			@Override
			public List<CompanyLearnerUsers> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (selectedCompany != null) {
						filters.put("companyID", selectedCompany.getId());
						retorno = service.allLearnersByCompany(first, pageSize, sortField, sortOrder, filters);						
						dataModel.setRowCount(service.countLearnersByCompany(CompanyLearnerUsers.class, filters));
					}
				} catch (Exception e) {
					addErrorMessage(e.getMessage(), e);
					logger.fatal(e);
					e.printStackTrace();
				}
				return retorno;
			}

			@Override
			public Object getRowKey(CompanyLearnerUsers obj) {
				return obj.getId();
			}

			@Override
			public CompanyLearnerUsers getRowData(String rowKey) {
				for (CompanyLearnerUsers obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	
	}

	public void populateSdpType() throws Exception{
		if (getSessionUI().isExternalParty()) {
			sdpCompanyLink = null;
			sdpType = null;
			if (selectedTrainingProvider != null && selectedTrainingProvider.getCompany() != null && selectedTrainingProvider.getId() != null) {
				if (selectedTrainingProvider.getTrainingSite() != null && selectedTrainingProvider.getTrainingSite().getId() != null) {
					sdpCompanyLink = sdpCompanyService.findBySdpIdCompanyIdAndTrainingSiteIdReturnOneResult(getSessionUI().getActiveUser().getId(), selectedTrainingProvider.getCompany().getId(), selectedTrainingProvider.getTrainingSite().getId());
				} else {
					sdpCompanyLink = sdpCompanyService.findBySdpIdAndCompanyIdReturnOneResult(getSessionUI().getActiveUser().getId(), selectedTrainingProvider.getCompany().getId());
				}
				if (sdpCompanyLink != null) {
					sdpType = sdpCompanyLink.getSdpType();
					if (sdpType != null && sdpType.getRegisterLearners()) {
						registerLearners = true;
					}
				} else {
					// old version fall back
					sdpType = trainingProviderApplicationService.locateSdpTypeByApplicationAndSessionUser(selectedTrainingProvider, getSessionUI().getActiveUser());
					if (sdpType != null && sdpType.getRegisterLearners()) {
						registerLearners = true;
					}
				}
			}
			// old version 
//			if (selectedTrainingProvider != null && selectedTrainingProvider.getCompany() != null && selectedTrainingProvider.getId() != null) {
//				sdpType = trainingProviderApplicationService.locateSdpTypeByApplicationAndSessionUser(selectedTrainingProvider, getSessionUI().getActiveUser());
//				if (sdpType != null && sdpType.getRegisterLearners()) {
//					registerLearners = true;
//				}
//			}
		} else {
			registerLearners = true;
		}
	}
	
	public void validiateCanViewInformation() throws Exception{
		if (getSessionUI().isExternalParty()) {
			if (sdpType == null || sdpType.getViewLearners() == null || !sdpType.getViewLearners()) {
				selectedTrainingProvider = null;
				throw new Exception("Kindly be advised that you require the relevant authorisation to access this information.");
			}
		} else {
			registerLearners = true;
		}
	}
	
	public void redirectToLearnerReg() {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getId() != null ) {
			getSessionUI().setTask(null);
		}
		super.setParameter("employerId", this.selectedCompany.getId(), true);		
		super.ajaxRedirect("/pages/companylearneruser.jsf");
	}
	
	public void redirectToLearner() {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getId() != null ) {
			getSessionUI().setTask(null);
		}
		super.setParameter("companylearneruser", this.companyLearnerUsers.getId(), true);		
		super.ajaxRedirect("/pages/companylearneruser.jsf");
	}
	
	public void redirectToLearnerRegTP() {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getId() != null ) {
			getSessionUI().setTask(null);
		}
		super.setParameter("selectedTrainingProviderId", this.selectedTrainingProvider.getId(), true);
		super.ajaxRedirect("/pages/companylearneruser.jsf");
	}
	
	public void onRowToggle(CompanyLearnerUsers companyLearnerUsers) {
		try {
			companyLearnerUsers.getUser().setUsersDisabilityList(UsersDisabilityService.instance().findByKeyUser(companyLearnerUsers.getUser()));
			companyLearnerUsers.getUser().setUsersLanguageList(UsersLanguageService.instance().findByUser(companyLearnerUsers.getUser()));
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e);
		}
	}
	
	public void viewCompanyLearnerDetails() {
		try {
			companyLearnerUsers.getUser().setUsersDisabilityList(UsersDisabilityService.instance().findByKeyUser(companyLearnerUsers.getUser()));
			companyLearnerUsers.getUser().setUsersLanguageList(UsersLanguageService.instance().findByUser(companyLearnerUsers.getUser()));
			viewLearnerDetails = true;
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);			
		}
	}
	
	public void checkMinor() {
		minor = this.user.getDateOfBirth() != null && GenericUtility.getAge(this.user.getDateOfBirth()) < 18;
	}
	
	public void checkRequireGaurdian() throws Exception {
		if (user.getMaried() == null || user.getMaried() == YesNoEnum.No) {
			requireGaurdian = this.user.getDateOfBirth() != null && GenericUtility.getAge(this.user.getDateOfBirth()) < 18;
			if (requireGaurdian) {				
				if (user.getLegalGaurdian() == null) {
					this.gaurdian = new Users();
					this.user.setLegalGaurdian(gaurdian);
					this.user.getLegalGaurdian().setDoneSearch(false);
				} else if (user.getLegalGaurdian() != null) {
					this.gaurdian = user.getLegalGaurdian();
					this.user.setLegalGaurdian(gaurdian);
					this.user.getLegalGaurdian().setDoneSearch(true);
					if (this.user.getLegalGaurdian().getResidentialAddress() != null && this.user.getLegalGaurdian().getResidentialAddress().getId() != null) {
						this.legalGaurdianResidentialAddress = AddressService.instance().findByKey(this.user.getLegalGaurdian().getResidentialAddress().getId());
					} else {
						this.user.getLegalGaurdian().setResidentialAddress(new Address());
					}

					if (this.user.getLegalGaurdian().getPostalAddress() != null && this.user.getLegalGaurdian().getPostalAddress().getId() != null) {
						this.legalGaurdianPostalAddress = AddressService.instance().findByKey(this.user.getLegalGaurdian().getPostalAddress().getId());
					} else {
						this.user.getLegalGaurdian().setPostalAddress(new Address());
					}

					if (legalGaurdianPostalAddress != null && legalGaurdianPostalAddress.getId() != null) {
						this.legalGaurdianPostalAddress = this.user.getLegalGaurdian().getPostalAddress();
						this.copyAddress2 = this.legalGaurdianPostalAddress.getSameAddress() != null ? this.legalGaurdianPostalAddress.getSameAddress() : true;
					} else {
						this.legalGaurdianPostalAddress = new Address();
						this.copyAddress2 = false;
					}
					
				}
			}
		} else {
			requireGaurdian = false;
		}
	}
	
	public void updateLearnerInformation() {
		UsersService usersService = new UsersService();
		try {
			usersService.update(user);
			cpyAddresses();
			AddressService.instance().saveAddresses(residentialAddress, postalAddress);
			service.update(companyLearnerUsers);	
			usersLanguageService.createLinkToUser(usersLanguageList, user, getSessionUI().getActiveUser());
			if(this.user.getDisability() == YesNoEnum.Yes) {
				if(usersDisabilityList.size() == 0) {
					throw new Exception("Please add atleast one disability");
				}else {
					usersDisabilityService.createLinkToUser(usersDisabilityList, user, getSessionUI().getActiveUser());
				}
			}
			addInfoMessage("Learner has been updated");
			clearCurrentLearner();
		} catch (javax.validation.ConstraintViolationException e) {
			extractValidationErrors(e);
		}catch (ValidationException e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Address Copy, Clear, Find.
	 */
	public void cpyAddresses() {
		try {
			if (this.copyAddress) {
				AddressService.instance().copyFromToAddress(this.residentialAddress, this.postalAddress);
			}
			if (this.copyAddress2) {
				AddressService.instance().copyFromToAddress(this.legalGaurdianResidentialAddress, this.legalGaurdianPostalAddress);
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		this.postalAddress.setSameAddress(this.copyAddress);
	}
		
	public void prepareLanguageUpdate() {
		usersLanguageList.remove(usersLanguage);
		if (usersLanguage.getHomeLanguage() != null && usersLanguage.getHomeLanguage()) {
			// uncomment to allow user to select only 1 home language
			homeLanguageSelected = false;
		}
	}
	
	public void addLanguage() {
		try {

			languagePreCheck();
			if (usersLanguage != null && usersLanguage.getRead() != null) {
				usersLanguageList.add(usersLanguage);
				if (usersLanguage.getHomeLanguage() != null && usersLanguage.getHomeLanguage()) {
					// uncomment to allow user to select only 1 home language
					homeLanguageSelected = true;
				}
				usersLanguage = new UsersLanguage();
			} else {
				addErrorMessage("Select a language or speak, read and write");
			}

		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void languagePreCheck() throws Exception {
		if(usersLanguage != null && usersLanguage.getLanguage() != null) {
			for (UsersLanguage ul : usersLanguageList) {
				if (ul != null && ul.getLanguage() != null && usersLanguage != null && usersLanguage.getId() != null && usersLanguage.getRead() != null  && ul.getLanguage().getId() == usersLanguage.getLanguage().getId()) {
					throw new Exception("Language already exist on your language list");
				}
			}
		}else {
			throw new Exception("Select a language");
		}
	}
	
	public void prepareUsersDisabilityUpdate() {
		usersDisabilityList.remove(usersDisability);
	}
	
	public void removeUsersDisabilityFromList() {
		usersDisabilityList.remove(usersDisability);
		if (usersDisability.getId() != null) {
			try {
				usersDisabilityService.delete(usersDisability);
			} catch (Exception e) {
				e.printStackTrace();
				addErrorMessage(e.getMessage(), e);
			}
		}
		usersDisability = new UsersDisability();
	}
	
	public void removeLanguageFromList() {
		usersLanguageList.remove(usersLanguage);
		if (usersLanguage.getId() != null) {
			try {
				usersLanguageService.delete(usersLanguage);
			} catch (Exception e) {
				e.printStackTrace();
				addErrorMessage(e.getMessage(), e);
			}
		}
		if(usersLanguageList.size()==0) {
			homeLanguageSelected = false;
		}
		usersLanguage = new UsersLanguage();
	}
	
	/**
	 * Clear postal.
	 */
	public void clearPostal() {
		if (!this.copyAddress) {
			AddressService.instance().clearAddress(this.postalAddress);
		}
	}
	
	public void clearPostal2() {
		if (!this.copyAddress2) {
			AddressService.instance().clearAddress(this.legalGaurdianPostalAddress);
		}
	}
	/**
	 * Clear current learner.
	 */
	public void clearCurrentLearner() {
		this.user = null;
		this.usersLanguage = new UsersLanguage();
		this.usersDisability = new UsersDisability();
		this.usersDisabilityList = new ArrayList<>();
		this.usersLanguageList = new ArrayList<>();
		this.postalAddress = new Address();
		this.residentialAddress = new Address();
		copyAddress = false;
	}
	
	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public boolean isViewLearnerData() {
		return viewLearnerData;
	}

	public TrainingProviderApplication getSelectedTrainingProvider() {
		return selectedTrainingProvider;
	}

	public Boolean getPrimaryOrSecondarySDF() {
		return primaryOrSecondarySDF;
	}

	public Boolean getRegisterLearners() {
		return registerLearners;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public void setViewLearnerData(boolean viewLearnerData) {
		this.viewLearnerData = viewLearnerData;
	}

	public void setSelectedTrainingProvider(TrainingProviderApplication selectedTrainingProvider) {
		this.selectedTrainingProvider = selectedTrainingProvider;
	}

	public void setPrimaryOrSecondarySDF(Boolean primaryOrSecondarySDF) {
		this.primaryOrSecondarySDF = primaryOrSecondarySDF;
	}

	public void setRegisterLearners(Boolean registerLearners) {
		this.registerLearners = registerLearners;
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public LazyDataModel<CompanyLearnerUsers> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<CompanyLearnerUsers> dataModel) {
		this.dataModel = dataModel;
	}

	public CompanyLearnerUsers getCompanyLearnerUsers() {
		return companyLearnerUsers;
	}

	public void setCompanyLearnerUsers(CompanyLearnerUsers companyLearnerUsers) {
		this.companyLearnerUsers = companyLearnerUsers;
	}

	public boolean isViewLearnerDetails() {
		return viewLearnerDetails;
	}

	public void setViewLearnerDetails(boolean viewLearnerDetails) {
		this.viewLearnerDetails = viewLearnerDetails;
	}

	public SDPCompany getSdpCompanyLink() {
		return sdpCompanyLink;
	}

	public void setSdpCompanyLink(SDPCompany sdpCompanyLink) {
		this.sdpCompanyLink = sdpCompanyLink;
	}

	public SdpType getSdpType() {
		return sdpType;
	}

	public ArrayList<UsersLanguage> getUsersLanguageList() {
		return usersLanguageList;
	}

	public List<UsersDisability> getUsersDisabilityList() {
		return usersDisabilityList;
	}

	public UsersLanguage getUsersLanguage() {
		return usersLanguage;
	}

	public UsersDisability getUsersDisability() {
		return usersDisability;
	}

	public Company getCompany() {
		return company;
	}

	public Users getUser() {
		return user;
	}

	public Users getGaurdian() {
		return gaurdian;
	}

	public boolean isRequireGaurdian() {
		return requireGaurdian;
	}

	public boolean isMinor() {
		return minor;
	}

	public boolean isEditFields() {
		return editFields;
	}

	public void setSdpType(SdpType sdpType) {
		this.sdpType = sdpType;
	}

	public void setUsersLanguageList(ArrayList<UsersLanguage> usersLanguageList) {
		this.usersLanguageList = usersLanguageList;
	}

	public void setUsersDisabilityList(List<UsersDisability> usersDisabilityList) {
		this.usersDisabilityList = usersDisabilityList;
	}

	public void setUsersLanguage(UsersLanguage usersLanguage) {
		this.usersLanguage = usersLanguage;
	}

	public void setUsersDisability(UsersDisability usersDisability) {
		this.usersDisability = usersDisability;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public void setGaurdian(Users gaurdian) {
		this.gaurdian = gaurdian;
	}

	public void setRequireGaurdian(boolean requireGaurdian) {
		this.requireGaurdian = requireGaurdian;
	}

	public void setMinor(boolean minor) {
		this.minor = minor;
	}

	public void setEditFields(boolean editFields) {
		this.editFields = editFields;
	}

	public boolean isDisableFields() {
		return disableFields;
	}

	public void setDisableFields(boolean disableFields) {
		this.disableFields = disableFields;
	}

	public Address getResidentialAddress() {
		return residentialAddress;
	}

	public Address getPostalAddress() {
		return postalAddress;
	}

	public Address getLegalGaurdianPostalAddress() {
		return legalGaurdianPostalAddress;
	}

	public Address getLegalGaurdianResidentialAddress() {
		return legalGaurdianResidentialAddress;
	}

	public boolean isCopyAddress() {
		return copyAddress;
	}

	public boolean isCopyAddress2() {
		return copyAddress2;
	}

	public void setResidentialAddress(Address residentialAddress) {
		this.residentialAddress = residentialAddress;
	}

	public void setPostalAddress(Address postalAddress) {
		this.postalAddress = postalAddress;
	}

	public void setLegalGaurdianPostalAddress(Address legalGaurdianPostalAddress) {
		this.legalGaurdianPostalAddress = legalGaurdianPostalAddress;
	}

	public void setLegalGaurdianResidentialAddress(Address legalGaurdianResidentialAddress) {
		this.legalGaurdianResidentialAddress = legalGaurdianResidentialAddress;
	}

	public void setCopyAddress(boolean copyAddress) {
		this.copyAddress = copyAddress;
	}

	public void setCopyAddress2(boolean copyAddress2) {
		this.copyAddress2 = copyAddress2;
	}

	public boolean isHomeLanguageSelected() {
		return homeLanguageSelected;
	}

	public TrainingProviderApplication getTrainingProviderApplication() {
		return trainingProviderApplication;
	}

	public void setHomeLanguageSelected(boolean homeLanguageSelected) {
		this.homeLanguageSelected = homeLanguageSelected;
	}

	public void setTrainingProviderApplication(TrainingProviderApplication trainingProviderApplication) {
		this.trainingProviderApplication = trainingProviderApplication;
	}
}
