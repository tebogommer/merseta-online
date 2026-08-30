package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;

import haj.com.entity.Address;
import haj.com.entity.Blank;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearnerUsers;
import haj.com.entity.Doc;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.Users;
import haj.com.entity.UsersDisability;
import haj.com.entity.UsersLanguage;
import haj.com.entity.enums.CreatedByEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.DisabilityRating;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;
import haj.com.service.CompanyLearnerUsersService;
import haj.com.service.CompanyService;
import haj.com.service.DocService;
import haj.com.service.TrainingProviderApplicationService;
import haj.com.service.UsersDisabilityService;
import haj.com.service.UsersLanguageService;
import haj.com.service.UsersService;
import haj.com.service.lookup.DisabilityRatingService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankUI.
 */
@ManagedBean(name = "companyLearnerUsersUI")
@ViewScoped
public class CompanyLearnerUsersUI extends AbstractUI {
	/** The search user passport or id UI. */
	@ManagedProperty(value = "#{searchUserPassportOrIdUI}")
	private SearchUserPassportOrIdUI searchUserPassportOrIdUI;
	
	/** The service. */
	private CompanyLearnerUsersService service = new CompanyLearnerUsersService();
	private CompanyService companyService = new CompanyService();
	private UsersLanguageService usersLanguageService = new UsersLanguageService();
	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
	private UsersDisabilityService       usersDisabilityService       = new UsersDisabilityService();
	
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
	/** The doc. */
	private Doc doc;
	/** Addresses. */
	private Address residentialAddress;
	private Address postalAddress;
	private Address legalGaurdianPostalAddress;
	private Address legalGaurdianResidentialAddress;
	
	/** The copy address. */
	private boolean copyAddress;
	private boolean copyAddress2;
	private boolean disableFields = false;
	private boolean             homeLanguageSelected          = false;
	private TrainingProviderApplication trainingProviderApplication;
	private CompanyLearnerUsers companyLearnerUsers;
	private boolean editFields = false;
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
	 * Initialize method to get all Blank and prepare a for a create of a new Blank.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    Blank
	 */
	private void runInit() throws Exception {
		searchSetObject();
		this.legalGaurdianPostalAddress = new Address();
		this.legalGaurdianResidentialAddress = new Address();
		this.residentialAddress = new Address();
		this.postalAddress = new Address();
		if (super.getParameter("employerId", true) != null) {
			this.company = companyService.findByKey((Long) super.getParameter("employerId", true));
			companyService.resolveCompanyAddresses(company);
			companyLearnerUsers = new CompanyLearnerUsers();
			companyLearnerUsers.setCompany(company);
			if(getSessionUI().isEmployee()) {
				companyLearnerUsers.setCreatedByEnum(CreatedByEnum.merSETA);
			}else {
				companyLearnerUsers.setCreatedByEnum(CreatedByEnum.sdf);
			}
		}else if (super.getParameter("selectedTrainingProviderId", true) != null) {
			this.trainingProviderApplication=trainingProviderApplicationService.findByKey((Long) super.getParameter("selectedTrainingProviderId", true));
			this.company = this.trainingProviderApplication.getCompany(); 
			companyService.resolveCompanyAddresses(company);
			companyLearnerUsers = new CompanyLearnerUsers();
			companyLearnerUsers.setCompany(company);
			companyLearnerUsers.setTrainingProviderApplication(trainingProviderApplication);
			if(getSessionUI().isEmployee()) {
				companyLearnerUsers.setCreatedByEnum(CreatedByEnum.merSETA);
			}else {
				companyLearnerUsers.setCreatedByEnum(CreatedByEnum.sdp);
			}
		}else if (super.getParameter("companylearneruser", true) != null) {
			companyLearnerUsers = service.findByKey((Long) super.getParameter("companylearneruser", true));
			this.company = companyLearnerUsers.getCompany();
			companyService.resolveCompanyAddresses(company);
			trainingProviderApplication = companyLearnerUsers.getTrainingProviderApplication();
			this.user = companyLearnerUsers.getUser();
			resolveAddresses();
			loadUserLanguage();
			loadUsersDisability();
		}
	}
	
	public void submitLearnerRegistration() {
		try {		
			cpyAddresses();
			this.user.setResidentialAddress(this.residentialAddress);
			this.user.setPostalAddress(this.postalAddress);
			if (requireGaurdian) {
				this.user.getLegalGaurdian().setResidentialAddress(legalGaurdianResidentialAddress);
				this.user.getLegalGaurdian().setPostalAddress(legalGaurdianPostalAddress);
			}
			if (company == null || company.getId() == null) {
				throw new Exception("Please provide your training company");
			}
			
			
			if(usersLanguageList.size() > 0) {
				boolean homelang = false;
				
				for(UsersLanguage ul:usersLanguageList) {
					if(ul.getHomeLanguage() != null && ul.getHomeLanguage()) {
						homelang = true;
						homeLanguageSelected= true;
						break;
					}
				}
				if(!homeLanguageSelected) {
					throw new Exception("Please select home language");
				}
				if(!homelang) {
					throw new Exception("Please select home language");
				}
			}else {
				throw new Exception("Please add atleast one language");
			}
			
			if(this.user.getDisability() == YesNoEnum.Yes) {
				if(usersDisabilityList.size() == 0) {
					throw new Exception("Please add atleast one disability");
				}
			}
			
			service.createNewLearner(getSessionUI().getActiveUser(), this.user, companyLearnerUsers ,usersLanguageList, usersDisabilityList, requireGaurdian);
			addInfoMessage("Learner has been added");
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
	
	/**
	 * This gets a return from AbstractUIInterface.
	 *
	 * @param object
	 *            the object
	 */
	@Override
	public void callBackMethod(Object object) {
		try {
			if (object instanceof Users) {
				if (user == null) {
					this.user = (Users) object;
						if(user.getRsaIDNumber() != null && user.getValidationStatus() != null && !user.getValidationStatus().matches("Failure")) {
							if(user.getDeceasedStatus().matches("Alive")) {
								if (this.user != null && this.user.getId() != null) {
									
								}
								String name = this.user .getFirstName();
								if (name.contains(" ")) {
									String[] splited = name.split(" ");
									this.user.setFirstName(splited[0]);
									String midName = splited[1];
									//this.user.setMiddleName(splited[1]);									
									if(splited.length > 1) {
										for (int i = 2; i < splited.length; i++) {
											midName += " " + splited[i];
								        }
									}
									this.user.setMiddleName(midName);
								}
							}else {
								clearCurrentLearner();
								throw new Exception("User with this ID Number is deceased as per Department of Home Affairs records");
							}
						}else if (user.getPassportNumber() != null ){
							if (this.user != null && this.user.getId() != null) {								
							
							}else {					
								//clearCurrentLearner();						
								//throw new Exception("User with this ID Number does not exist");
							}
						}
					searchSetObject();
					prepareUserAfterSearch();
				}else {
					user.setLegalGaurdian((Users) object);
					user.getLegalGaurdian().setDoneSearch(true);
					resolveLegalGaurdianAddress();
				} 
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
		
	public void prepareUserAfterSearch() throws Exception {	
		checkMinor();
		checkRequireGaurdian();
		resolveAddresses();
		loadUserLanguage();
		loadUsersDisability();
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
	public void resolveLegalGaurdianAddress() throws Exception {
		if (user.getLegalGaurdian().getId() != null) {
			if (this.user.getLegalGaurdian().getResidentialAddress() != null) {
				legalGaurdianResidentialAddress= this.user.getLegalGaurdian().getResidentialAddress();
			} else {
				legalGaurdianResidentialAddress= new Address();
			}
			if (this.user.getLegalGaurdian().getPostalAddress() != null) {
				legalGaurdianPostalAddress=AddressService.instance().findByKey(this.user.getLegalGaurdian().getPostalAddress().getId());
				if (legalGaurdianPostalAddress.getSameAddress() != null) {
					this.copyAddress2 = this.user.getLegalGaurdian().getPostalAddress().getSameAddress();
				}
			} else {
				legalGaurdianPostalAddress = new Address();
				this.copyAddress2 = false;
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
	
	public void prepareLanguageUpdate() {
		usersLanguageList.remove(usersLanguage);
		if (usersLanguage.getHomeLanguage() != null && usersLanguage.getHomeLanguage()) {
			// uncomment to allow user to select only 1 home language
			homeLanguageSelected = false;
		}
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
		searchSetObject();
	}
	/**
	 * Search set object.
	 */
	public void searchSetObject() {
		getSearchUserPassportOrIdUI().setObject(this);
	}

	/**
	 * Store file.
	 *
	 * @param event
	 *            the event
	 */
	public void storeFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(getSessionUI().getActiveUser());
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
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
	
	public List<DisabilityRating> getSelectItemsDisabilityRating() {
		DisabilityRatingService disabilityRatingService = new DisabilityRatingService();
		List<DisabilityRating>  l                       = null;
		try {
			if (this.user.getDisabilityStatus() != null) {
				l = disabilityRatingService.findByDisability(this.user.getDisabilityStatus().getId());
			} else if (this.usersDisability.getDisabilityStatus() != null) {
				l = disabilityRatingService.findByDisability(this.usersDisability.getDisabilityStatus().getId());
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
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
	
	public void clearDisabilityRating() {
		try {
			this.user.setDisabilityRating(null);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
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

	public void usersDisabilityPreCheck() throws Exception {
		for (UsersDisability ud : usersDisabilityList) {
			if (ud != null && ud.getDisabilityStatus() != null && usersDisability != null && ud.getDisabilityStatus().getId() == usersDisability.getDisabilityStatus().getId()) {
				throw new Exception("Disability already exist on your disability list");
			}
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
	
	public void checkRequireGaurdian() throws Exception {
		if (user.getMaried() == null || user.getMaried() == YesNoEnum.No) {
			requireGaurdian = this.user.getDateOfBirth() != null && GenericUtility.getAge(this.user.getDateOfBirth()) < 18;
			if (requireGaurdian) {				
				if (user.getLegalGaurdian() == null) {
					this.gaurdian = new Users();
					this.user.setLegalGaurdian(gaurdian);
					this.user.getLegalGaurdian().setDoneSearch(false);
					searchSetObject();
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
			searchSetObject();
		} else {
			requireGaurdian = false;
		}
	}
	
	public void checkMinor() {
		minor = this.user.getDateOfBirth() != null && GenericUtility.getAge(this.user.getDateOfBirth()) < 18;
	}
	
	public SearchUserPassportOrIdUI getSearchUserPassportOrIdUI() {
		return searchUserPassportOrIdUI;
	}

	public ArrayList<UsersLanguage> getUsersLanguageList() {
		return usersLanguageList;
	}

	public List<UsersDisability> getUsersDisabilityList() {
		return usersDisabilityList;
	}

	public Company getCompany() {
		return company;
	}

	public Users getUser() {
		return user;
	}

	public Address getResidentialAddress() {
		return residentialAddress;
	}

	public Address getPostalAddress() {
		return postalAddress;
	}

	public boolean isCopyAddress() {
		return copyAddress;
	}

	public TrainingProviderApplication getTrainingProviderApplication() {
		return trainingProviderApplication;
	}

	public void setSearchUserPassportOrIdUI(SearchUserPassportOrIdUI searchUserPassportOrIdUI) {
		this.searchUserPassportOrIdUI = searchUserPassportOrIdUI;
	}

	public void setUsersLanguageList(ArrayList<UsersLanguage> usersLanguageList) {
		this.usersLanguageList = usersLanguageList;
	}

	public void setUsersDisabilityList(List<UsersDisability> usersDisabilityList) {
		this.usersDisabilityList = usersDisabilityList;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public void setResidentialAddress(Address residentialAddress) {
		this.residentialAddress = residentialAddress;
	}

	public void setPostalAddress(Address postalAddress) {
		this.postalAddress = postalAddress;
	}

	public void setCopyAddress(boolean copyAddress) {
		this.copyAddress = copyAddress;
	}

	public void setTrainingProviderApplication(TrainingProviderApplication trainingProviderApplication) {
		this.trainingProviderApplication = trainingProviderApplication;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public UsersLanguage getUsersLanguage() {
		return usersLanguage;
	}

	public UsersDisability getUsersDisability() {
		return usersDisability;
	}

	public void setUsersLanguage(UsersLanguage usersLanguage) {
		this.usersLanguage = usersLanguage;
	}

	public void setUsersDisability(UsersDisability usersDisability) {
		this.usersDisability = usersDisability;
	}

	public boolean isDisableFields() {
		return disableFields;
	}

	public void setDisableFields(boolean disableFields) {
		this.disableFields = disableFields;
	}

	public boolean isHomeLanguageSelected() {
		return homeLanguageSelected;
	}

	public void setHomeLanguageSelected(boolean homeLanguageSelected) {
		this.homeLanguageSelected = homeLanguageSelected;
	}

	public Users getGaurdian() {
		return gaurdian;
	}

	public boolean isRequireGaurdian() {
		return requireGaurdian;
	}

	public void setGaurdian(Users gaurdian) {
		this.gaurdian = gaurdian;
	}

	public void setRequireGaurdian(boolean requireGaurdian) {
		this.requireGaurdian = requireGaurdian;
	}

	public Address getLegalGaurdianPostalAddress() {
		return legalGaurdianPostalAddress;
	}

	public Address getLegalGaurdianResidentialAddress() {
		return legalGaurdianResidentialAddress;
	}

	public boolean isCopyAddress2() {
		return copyAddress2;
	}

	public void setLegalGaurdianPostalAddress(Address legalGaurdianPostalAddress) {
		this.legalGaurdianPostalAddress = legalGaurdianPostalAddress;
	}

	public void setLegalGaurdianResidentialAddress(Address legalGaurdianResidentialAddress) {
		this.legalGaurdianResidentialAddress = legalGaurdianResidentialAddress;
	}

	public void setCopyAddress2(boolean copyAddress2) {
		this.copyAddress2 = copyAddress2;
	}

	public boolean isMinor() {
		return minor;
	}

	public void setMinor(boolean minor) {
		this.minor = minor;
	}

	public boolean isEditFields() {
		return editFields;
	}

	public void setEditFields(boolean editFields) {
		this.editFields = editFields;
	}

	public CompanyLearnerUsers getCompanyLearnerUsers() {
		return companyLearnerUsers;
	}

	public void setCompanyLearnerUsers(CompanyLearnerUsers companyLearnerUsers) {
		this.companyLearnerUsers = companyLearnerUsers;
	}
}
