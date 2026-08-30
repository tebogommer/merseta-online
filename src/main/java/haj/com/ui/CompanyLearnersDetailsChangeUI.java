package  haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.Address;
import haj.com.entity.AddressChange;
import haj.com.entity.CompanyLearners;
import haj.com.entity.CompanyLearnersDetailsChange;
import haj.com.entity.Doc;
import haj.com.entity.UserChangeRequest;
import haj.com.entity.Users;
import haj.com.entity.UsersDisability;
import haj.com.entity.UsersLanguage;
import haj.com.entity.enums.AddressEnum;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.DisabilityRating;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.RejectReasons;
import haj.com.service.AddressChangeService;
import haj.com.service.AddressService;
import haj.com.service.CompanyLearnersDetailsChangeService;
import haj.com.service.CompanyLearnersService;
import haj.com.service.DocService;
import haj.com.service.TasksService;
import haj.com.service.UserChangeRequestService;
import haj.com.service.UsersDisabilityService;
import haj.com.service.UsersLanguageService;
import haj.com.service.UsersService;
import haj.com.service.lookup.DisabilityRatingService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.utils.GenericUtility;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "companylearnersdetailschangeUI")
@ViewScoped
public class CompanyLearnersDetailsChangeUI extends AbstractUI {
	/** The service. */
	private UsersDisabilityService usersDisabilityService = new UsersDisabilityService();
	private UsersService usersService = new UsersService();
	private UsersLanguageService usersLanguageService = new UsersLanguageService();
	private CompanyLearnersService companyLearnersService = new CompanyLearnersService();
	private CompanyLearnersDetailsChangeService service = new CompanyLearnersDetailsChangeService();
	private List<CompanyLearnersDetailsChange> companylearnersdetailschangeList = null;
	private List<CompanyLearnersDetailsChange> companylearnersdetailschangefilteredList = null;
	private CompanyLearnersDetailsChange companylearnersdetailschange = null;
	private LazyDataModel<CompanyLearnersDetailsChange> dataModel; 
	private UserChangeRequestService userChangeRequestService = new UserChangeRequestService();
	private AddressChangeService addressChangeService = new AddressChangeService();

	private CompanyLearners companylearners;
	private Doc doc;
	private Users user;
	private Users gaurdian;
	private boolean requireGaurdian = false;
	private Address residentialAddress;
	private Address postalAddress;
	private Address birthAddress;
	private Address legalGaurdianPostalAddress;
	private Address legalGaurdianResidentialAddress;
	private boolean copyAddress;
	private boolean copyAddress1;
	private boolean copyAddress2;
	private boolean homeLanguageSelected = false;
	private boolean disableFields = true;
	
	/** The User Language */
	private UsersLanguage usersLanguage = new UsersLanguage();
	private UsersDisability usersDisability = new UsersDisability();
	private List<UsersLanguage> usersLanguageList = new ArrayList<>();
	private List<UsersDisability> usersDisabilityList = new ArrayList<>();
	
	private List<RejectReasons> selectedRejectReason = new ArrayList<>();
	private List<RejectReasons> rejectReason = new ArrayList<>();
	
	private UserChangeRequest userChangeRequest;
	private AddressChange residentialAddressChange;
	private AddressChange postalAddressChange;
	private AddressChange birthAddressChange;
	
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
	 * Initialize method to get all CompanyLearnersDetailsChange and prepare a for a create of a new CompanyLearnersDetailsChange
 	 * @author TechFinium 
 	 * @see    CompanyLearnersDetailsChange
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		if (super.getParameter("id", false) != null) {
			if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.CompanyLearnerDetailesChange) {
				getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
				this.companylearnersdetailschange = service.findByKey(getSessionUI().getTask().getTargetKey());
				service.prepareNewRegistration(ConfigDocProcessEnum.CompanyLearnerDetailesChange, companylearnersdetailschange);
				this.companylearners = companylearnersdetailschange.getCompanyLearnersParent();
				this.user = companylearners.getUser();
				companyLearnersService.prepareNewRegistration(ConfigDocProcessEnum.Learner, companylearners);
				
				userChangeRequest = userChangeRequestService.findByUserAndStatus(this.companylearners.getUser().getId(), ApprovalEnum.PendingApproval).get(0);
				
				if(addressChangeService.findByUser(this.companylearners.getUser(), AddressEnum.Residential).size() > 0) {
					residentialAddressChange = addressChangeService.findByUser(this.companylearners.getUser(), AddressEnum.Residential).get(0);
				}
				
				if(addressChangeService.findByUser(this.companylearners.getUser(), AddressEnum.Postal).size()>0) {
					postalAddressChange = addressChangeService.findByUser(this.companylearners.getUser(), AddressEnum.Postal).get(0);
				}
				
				if(addressChangeService.findByUser(this.companylearners.getUser(), AddressEnum.Birth).size() >0) {
					birthAddressChange = addressChangeService.findByUser(this.companylearners.getUser(), AddressEnum.Birth).get(0);
				}
				
				//populateExistingLearner();
				if (companylearnersdetailschange.getStatus() == ApprovalEnum.Rejected) {
					populateRejectReasons();
				}
				if(getSessionUI().getTask()  != null && getSessionUI().getTask().getFirstInProcess() != null && getSessionUI().getTask().getFirstInProcess()) {
					disableFields = true;
				}else {
					disableFields = false;
				}
				
				if(userChangeRequest.getDisability() == YesNoEnum.Yes) {
					usersDisabilityList = usersDisabilityService.findByTargetClassAndKey(companylearnersdetailschange.getId() , companylearnersdetailschange.getClass().getName());
				}
				
				usersLanguageList = usersLanguageService.findByTargetClassAndKey(companylearnersdetailschange.getId() , companylearnersdetailschange.getClass().getName());
			}
		}else if (super.getParameter("companyLearnersId", true) != null) {
			prepareNew();
			this.companylearners = companyLearnersService.findByKey((Long) super.getParameter("companyLearnersId", true));
			companyLearnersService.prepareNewRegistration(ConfigDocProcessEnum.Learner, companylearners);
			if(!service.checkexitingUpdate(this.companylearners.getUser())) {
				addInfoMessage("There is a pending change for this learner");
				ajaxRedirectToDashboard();
			}
			
			service.copyUserChangeRequestInfo(userChangeRequest, this.companylearners.getUser());
			addressChangeService.copyAddressToAddressChange(companylearners.getUser().getResidentialAddress(), residentialAddressChange);
			addressChangeService.copyAddressToAddressChange(companylearners.getUser().getPostalAddress(), postalAddressChange);
			if(companylearners.getUser().getBirthAddress()!=null) {
				addressChangeService.copyAddressToAddressChange(companylearners.getUser().getBirthAddress(), birthAddressChange);
			}
			if(userChangeRequest.getDisability() == YesNoEnum.Yes) {
				usersDisabilityList = usersDisabilityService.findByKeyUser(this.companylearners.getUser());
			}			
			usersLanguageList = usersLanguageService.findByUser(this.companylearners.getUser());
			
			populateExistingLearner();
			disableFields = false;
		}else {
			prepareNew();
			companylearnersdetailschangeInfo();
		}
	}

	/**
	 * Get all CompanyLearnersDetailsChange for data table
 	 * @author TechFinium 
 	 * @see    CompanyLearnersDetailsChange
 	 */
	public void companylearnersdetailschangeInfo() {
			//dataModel = new CompanyLearnersDetailsChangeDatamodel();
	}
	
	

	/**
	 * Insert CompanyLearnersDetailsChange into database
 	 * @author TechFinium 
 	 * @see    CompanyLearnersDetailsChange
 	 */
	public void companylearnersdetailschangeInsert() {
		try {
				 service.create(this.companylearnersdetailschange);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 companylearnersdetailschangeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update CompanyLearnersDetailsChange in database
 	 * @author TechFinium 
 	 * @see    CompanyLearnersDetailsChange
 	 */
    public void companylearnersdetailschangeUpdate() {
		try {
				 service.update(this.companylearnersdetailschange);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 companylearnersdetailschangeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
    
	public void submitLearnerRegistration1() {
		try {
			service.submitLearnerUpdate(getSessionUI().getActiveUser(), user,  companylearners , requireGaurdian,  usersLanguageList, usersDisabilityList);	
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void submitLearnerRegistration() {
		try {
			service.submitLearnerUpdate(getSessionUI().getActiveUser(), userChangeRequest,  residentialAddressChange , postalAddressChange, birthAddressChange ,  usersLanguageList, usersDisabilityList, companylearners);	
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void createLearnerWorkflow() {
		try {
			//ompanyLearnersDetailsChange companyLearnersDetailsChange, Users user, List<UsersLanguage> usersLanguages, List<UsersDisability> usersDisabilityList
			
			service.createLearnerWorkflow(companylearnersdetailschange, getSessionUI().getActiveUser(), userChangeRequest,  usersLanguageList, usersDisabilityList, residentialAddressChange, postalAddressChange, birthAddressChange, getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void finalApproveWorkflow() {
		try {
			service.finalApproveWorkflow(companylearnersdetailschange, getSessionUI().getActiveUser(), userChangeRequest,  usersLanguageList, usersDisabilityList, residentialAddressChange, postalAddressChange, birthAddressChange, getSessionUI().getTask());
			ajaxRedirectToDashboard();

		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void rejectCompanyLearner() {
		try {
			if (selectedRejectReason.size() == 0) throw new Exception("Please select a reject reason");
			service.rejectCompanyLearner(companylearnersdetailschange, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void finalApproveCompanyLearners() {
		try {
			service.finalApproveWorkflow(companylearnersdetailschange, getSessionUI().getActiveUser(), userChangeRequest,  usersLanguageList, usersDisabilityList, residentialAddressChange, postalAddressChange, birthAddressChange, getSessionUI().getTask());
			ajaxRedirectToDashboard();			
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalRejectCompanyLearners() {
		try {
			if (selectedRejectReason.size() == 0) throw new Exception("Please select a reject reason");
			service.finalRejectCompanyLearners(companylearnersdetailschange, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.CompanyLearnerDetailesChange);			
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	private void populateRejectReasons() {
		RejectReasonsService rs= new RejectReasonsService();
		try {
			rejectReason = rs.locateReasonsSelectedByTargetKeyClassAndProcess(companylearnersdetailschange.getId(), CompanyLearnersDetailsChange.class.getName(), ConfigDocProcessEnum.CompanyLearnerDetailesChange);			
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}		
	}

	/**
	 * Delete CompanyLearnersDetailsChange from database
 	 * @author TechFinium 
 	 * @see    CompanyLearnersDetailsChange
 	 */
	public void companylearnersdetailschangeDelete() {
		try {
			 service.delete(this.companylearnersdetailschange);
			  prepareNew();
			 companylearnersdetailschangeInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addUsersDisability() {
		try {
			usersDisabilityPreCheck();
			if (usersDisability != null && usersDisability.getDisabilityStatus() != null && usersDisability.getDisabilityRating() != null) {
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
	
	public void prepareUsersDisabilityUpdate() {
		usersDisabilityList.remove(usersDisability);
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
	
	public void prepareLanguageUpdate() {
		usersLanguageList.remove(usersLanguage);
		if (usersLanguage.getHomeLanguage() != null && usersLanguage.getHomeLanguage()) {
			// uncomment to allow user to select only 1 home language
			homeLanguageSelected = false;
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
	
	/**
	 * Create new instance of CompanyLearnersDetailsChange 
 	 * @author TechFinium 
 	 * @see    CompanyLearnersDetailsChange
 	 */
	public void prepareNew() {
		userChangeRequest = new UserChangeRequest();
		residentialAddressChange = new AddressChange();
		postalAddressChange= new AddressChange();
		birthAddressChange= new AddressChange();
		companylearnersdetailschange = new CompanyLearnersDetailsChange();
	}
  
    /**
     * Complete.
     *
     * @param desc the desc
     * @return the list
     */  
    public List<CompanyLearnersDetailsChange> complete(String desc) {
		List<CompanyLearnersDetailsChange> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		}
		catch (Exception e) {
		addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
    
    public List<DisabilityRating> getSelectItemsDisabilityRating() {
		DisabilityRatingService disabilityRatingService = new DisabilityRatingService();
		List<DisabilityRating> l = null;
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
	
	private void populateExistingLearner() throws Exception {
		this.user = companylearners.getUser();	
		usersLanguageList = usersLanguageService.findByUser(this.user);
		usersDisabilityList = usersDisabilityService.findByKeyUser(this.user);
		setsearchComplete();
	}

	private void setsearchComplete() throws Exception {
		user.setExistingUser(true);
		user.setRegFieldsDone(true);

		if (this.user.getResidentialAddress() != null) {
			this.residentialAddress = this.user.getResidentialAddress();
		} else {
			this.residentialAddress = new Address();
		}
		if (this.user.getPostalAddress() != null) {
			this.postalAddress = this.user.getPostalAddress();
			if (this.postalAddress.getSameAddress() != null);
			this.copyAddress = this.postalAddress.getSameAddress();
		} else {
			this.postalAddress = new Address();
			this.copyAddress = false;
		}

		if (this.user.getBirthAddress() != null) {
			this.birthAddress = user.getBirthAddress();
			if (birthAddress.getSameAddress() != null) this.copyAddress1 = birthAddress.getSameAddress();
		} else {
			this.birthAddress = new Address();
			this.copyAddress1 = false;
		}
		checkRequireGaurdian();
	}
	
	public void checkRequireGaurdian() throws Exception {
		if (user.getMaried() == null || user.getMaried() == YesNoEnum.No) {
			requireGaurdian = this.user.getDateOfBirth() != null && GenericUtility.getAge(this.user.getDateOfBirth()) < 18;
			if (requireGaurdian) {
				this.gaurdian = new Users();
				if (user.getLegalGaurdian() == null) {
					this.user.setLegalGaurdian(gaurdian);
				} else if (user.getLegalGaurdian() != null) {
					if (this.user.getLegalGaurdian().getResidentialAddress() != null && this.user.getLegalGaurdian().getResidentialAddress().getId() != null) {
						this.user.getLegalGaurdian().setResidentialAddress(AddressService.instance().findByKey(this.user.getLegalGaurdian().getResidentialAddress().getId()));
						this.legalGaurdianResidentialAddress = this.user.getLegalGaurdian().getResidentialAddress();
					} else {
						this.user.getLegalGaurdian().setResidentialAddress(new Address());
					}

					if (this.user.getLegalGaurdian().getPostalAddress() != null && this.user.getLegalGaurdian().getPostalAddress().getId() != null) {
						this.user.getLegalGaurdian().setPostalAddress(AddressService.instance().findByKey(this.user.getLegalGaurdian().getPostalAddress().getId()));
						this.legalGaurdianPostalAddress = this.user.getLegalGaurdian().getPostalAddress();
					} else {
						this.user.getLegalGaurdian().setPostalAddress(new Address());
					}

					if (this.user.getLegalGaurdian().getPostalAddress() != null && this.user.getLegalGaurdian().getPostalAddress().getId() != null) {
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
    
	public void clearDisabilityRating() {
		try {
			this.user.setDisabilityRating(null);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void resetRequidGuidian() {
		if (requireGaurdian) {
			if (user.getMaried() == YesNoEnum.Yes) {
				requireGaurdian = false;
				user.setLegalGaurdian(null);
			}
		}
	}
	public List<DisabilityRating> getSelectItemsUsersDisabilityRating() {
		DisabilityRatingService disabilityRatingService = new DisabilityRatingService();
		List<DisabilityRating> l = null;
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
	
	/**
	 * Address Copy, Clear, Find.
	 */
	public void cpyAddresses() {
		try {
			if (this.copyAddress) {
				AddressService.instance().copyFromToAddress(this.residentialAddress, this.postalAddress);
			}

			if (this.copyAddress1) {
				AddressService.instance().copyFromToAddress(this.residentialAddress, this.birthAddress);
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
	
	public void clearPostal() {
		if (!this.copyAddress) {
			AddressService.instance().clearAddress(this.postalAddress);
		}
	}
	
	public void clearPostal1() {
		if (!this.copyAddress1) {
			AddressService.instance().clearAddress(this.birthAddress);
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
		this.companylearners.setInterventionType(new InterventionType());
		this.companylearners.setQualification(new Qualification());
		this.companylearners.setDocs(new ArrayList<Doc>());
		this.companylearners = new CompanyLearners();
		super.setParameter("companyLearnersId", null, true);
		super.ajaxRedirect("/pages/tp/learners.jsf");
	}
	
    public List<CompanyLearnersDetailsChange> getCompanyLearnersDetailsChangeList() {
		return companylearnersdetailschangeList;
	}
	public void setCompanyLearnersDetailsChangeList(List<CompanyLearnersDetailsChange> companylearnersdetailschangeList) {
		this.companylearnersdetailschangeList = companylearnersdetailschangeList;
	}
	public CompanyLearnersDetailsChange getCompanylearnersdetailschange() {
		return companylearnersdetailschange;
	}
	public void setCompanylearnersdetailschange(CompanyLearnersDetailsChange companylearnersdetailschange) {
		this.companylearnersdetailschange = companylearnersdetailschange;
	}

    public List<CompanyLearnersDetailsChange> getCompanyLearnersDetailsChangefilteredList() {
		return companylearnersdetailschangefilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param companylearnersdetailschangefilteredList the new companylearnersdetailschangefilteredList list
 	 * @see    CompanyLearnersDetailsChange
 	 */	
	public void setCompanyLearnersDetailsChangefilteredList(List<CompanyLearnersDetailsChange> companylearnersdetailschangefilteredList) {
		this.companylearnersdetailschangefilteredList = companylearnersdetailschangefilteredList;
	}

	
	public LazyDataModel<CompanyLearnersDetailsChange> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<CompanyLearnersDetailsChange> dataModel) {
		this.dataModel = dataModel;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public CompanyLearners getCompanylearners() {
		return companylearners;
	}

	public void setCompanylearners(CompanyLearners companylearners) {
		this.companylearners = companylearners;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Users getGaurdian() {
		return gaurdian;
	}

	public void setGaurdian(Users gaurdian) {
		this.gaurdian = gaurdian;
	}

	public boolean isRequireGaurdian() {
		return requireGaurdian;
	}

	public void setRequireGaurdian(boolean requireGaurdian) {
		this.requireGaurdian = requireGaurdian;
	}

	public Address getResidentialAddress() {
		return residentialAddress;
	}

	public void setResidentialAddress(Address residentialAddress) {
		this.residentialAddress = residentialAddress;
	}

	public Address getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(Address postalAddress) {
		this.postalAddress = postalAddress;
	}

	public Address getBirthAddress() {
		return birthAddress;
	}

	public void setBirthAddress(Address birthAddress) {
		this.birthAddress = birthAddress;
	}

	public boolean isCopyAddress() {
		return copyAddress;
	}

	public void setCopyAddress(boolean copyAddress) {
		this.copyAddress = copyAddress;
	}

	public boolean isCopyAddress1() {
		return copyAddress1;
	}

	public void setCopyAddress1(boolean copyAddress1) {
		this.copyAddress1 = copyAddress1;
	}

	public boolean isCopyAddress2() {
		return copyAddress2;
	}

	public void setCopyAddress2(boolean copyAddress2) {
		this.copyAddress2 = copyAddress2;
	}

	public Address getLegalGaurdianPostalAddress() {
		return legalGaurdianPostalAddress;
	}

	public void setLegalGaurdianPostalAddress(Address legalGaurdianPostalAddress) {
		this.legalGaurdianPostalAddress = legalGaurdianPostalAddress;
	}

	public Address getLegalGaurdianResidentialAddress() {
		return legalGaurdianResidentialAddress;
	}

	public void setLegalGaurdianResidentialAddress(Address legalGaurdianResidentialAddress) {
		this.legalGaurdianResidentialAddress = legalGaurdianResidentialAddress;
	}

	public UsersLanguage getUsersLanguage() {
		return usersLanguage;
	}

	public void setUsersLanguage(UsersLanguage usersLanguage) {
		this.usersLanguage = usersLanguage;
	}

	public UsersDisability getUsersDisability() {
		return usersDisability;
	}

	public void setUsersDisability(UsersDisability usersDisability) {
		this.usersDisability = usersDisability;
	}

	public List<UsersLanguage> getUsersLanguageList() {
		return usersLanguageList;
	}

	public void setUsersLanguageList(List<UsersLanguage> usersLanguageList) {
		this.usersLanguageList = usersLanguageList;
	}

	public List<UsersDisability> getUsersDisabilityList() {
		return usersDisabilityList;
	}

	public void setUsersDisabilityList(List<UsersDisability> usersDisabilityList) {
		this.usersDisabilityList = usersDisabilityList;
	}

	public boolean isHomeLanguageSelected() {
		return homeLanguageSelected;
	}

	public void setHomeLanguageSelected(boolean homeLanguageSelected) {
		this.homeLanguageSelected = homeLanguageSelected;
	}

	public List<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(List<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	public List<RejectReasons> getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(List<RejectReasons> rejectReason) {
		this.rejectReason = rejectReason;
	}

	public boolean isDisableFields() {
		return disableFields;
	}

	public void setDisableFields(boolean disableFields) {
		this.disableFields = disableFields;
	}

	public UserChangeRequest getUserChangeRequest() {
		return userChangeRequest;
	}

	public void setUserChangeRequest(UserChangeRequest userChangeRequest) {
		this.userChangeRequest = userChangeRequest;
	}

	public AddressChange getResidentialAddressChange() {
		return residentialAddressChange;
	}

	public void setResidentialAddressChange(AddressChange residentialAddressChange) {
		this.residentialAddressChange = residentialAddressChange;
	}

	public AddressChange getPostalAddressChange() {
		return postalAddressChange;
	}

	public void setPostalAddressChange(AddressChange postalAddressChange) {
		this.postalAddressChange = postalAddressChange;
	}

	public AddressChange getBirthAddressChange() {
		return birthAddressChange;
	}

	public void setBirthAddressChange(AddressChange birthAddressChange) {
		this.birthAddressChange = birthAddressChange;
	}	
}
