package haj.com.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;

import haj.com.constants.HAJConstants;
import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.SDPCompany;
import haj.com.entity.SdpCompanyActions;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingSite;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.SdpCompanyActionListEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.DocService;
import haj.com.service.SDPCompanyService;
import haj.com.service.SdpCompanyActionsService;
import haj.com.service.TrainingProviderApplicationService;
import haj.com.service.TrainingSiteService;
import haj.com.service.UsersService;
import haj.com.service.lookup.SdpTypeService;

// TODO: Auto-generated Javadoc
/**
 * The Class PrimarySdpRelinkUI.
 */
@ManagedBean(name = "primarySdpRelinkUI")
@ViewScoped
public class PrimarySdpRelinkUI extends AbstractUI {

	/** The search user passport or id UI. */
	@ManagedProperty(value = "#{searchUserPassportOrIdUI}")
	private SearchUserPassportOrIdUI searchUserPassportOrIdUI;

	/** The search company UI. */
	@ManagedProperty(value = "#{searchCompanyUI}")
	private SearchCompanyUI searchCompanyUI;

	private SdpCompanyActions sdpCompanyActions = null;
	private TrainingProviderApplication trainingProviderApplication = null;
	private SDPCompany primarySdpLinked = null;
	private Company company;
	private TrainingSite trainingSite = null;
	private Users formUser;
	private Doc doc;
	
	private SdpCompanyActionsService sdpCompanyActionsService = new SdpCompanyActionsService();
	private SDPCompanyService sdpCompanyService = new SDPCompanyService();
	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
	private CompanyService companyService = new CompanyService(getResourceBundle());
	private UsersService usersService = new UsersService();
	private TrainingSiteService trainingSiteService = new TrainingSiteService();
	
	
	private List<Doc> docList = new ArrayList<>();
	
	private Boolean userSectionDone = false;
	private Boolean providerSelectionDone = false;
	
	private String userSetmisValidiations = "";
	private String accredNumberEntered = "";

	private Long maxFileSize = HAJConstants.MAX_FILE_SIZE;
	private Long MAX_FIRST_NAME_SIZE = HAJConstants.MAX_FIRST_NAME_SIZE;
	private Long MAX_LAST_NAME_SIZE = HAJConstants.MAX_LAST_NAME_SIZE;
	private Long MAX_EMAIL_SIZE = HAJConstants.MAX_EMAIL_SIZE;
	private final String TELPHONE_FORMAT = HAJConstants.TELPHONE_FORMAT;
	private final String CELLPHONE_FORMAT = HAJConstants.CELLPHONE_FORMAT;
	private Long MAX_FAX_NUMBER = HAJConstants.MAX_FAX_NUMBER;
	private Long MAX_COMPANY_NAME_SIZE = HAJConstants.MAX_COMPANY_NAME_SIZE;
	private String FAX_NUMBER_FORMAT = HAJConstants.FAX_NUMBER_FORMAT;
	
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
	 * Initialize method to get all Company and prepare a for a create of a new
	 * Company.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Company
	 */
	private void runInit() throws Exception {
		getSearchUserPassportOrIdUI().setObject(this);
		getSearchCompanyUI().setObject(this);
	}

	/**
	 * This gets a return from AbstractUIInterface.
	 *
	 * @param object
	 *            the object
	 */
	public void callBackMethod(Object object) {
		try {
			if (object instanceof Users) {
				this.formUser = (Users) object;
				usersService.identifyFieldAlteration(formUser);
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void clearUserSearch(){
		formUser = null;
		trainingProviderApplication = null;
		primarySdpLinked = null;
		getSearchUserPassportOrIdUI().setObject(this);
		getSearchCompanyUI().setObject(this);
		userSectionDone = false;
		providerSelectionDone = false;
	}
	
	public void doneUserBit() {
		try {
			userSetmisValidiations = "";
			userSetmisValidiations = usersService.validiateUserInformation(formUser).toString();
			if (!userSetmisValidiations.trim().isEmpty()) {
				addErrorMessage("Validiation error. Please attend to the errors before proceeding.");
			} else {
				if (formUser.getId() == null) {
					// check if email in use
					if (usersService.countUsersByEmail(formUser.getEmail().trim()) > 0) {
						throw new Exception("Email address provided is already registered on the application. Provide a new email address or contact merSETA support.");
					}
				}
				userSectionDone = true;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void reopenFormUserSection(){
		userSectionDone = false;
		providerSelectionDone = false;
		trainingProviderApplication = null;
		trainingSite = null;
		company = null;
		primarySdpLinked = null;
	}
	
	public void failedProviderSearch(){
		trainingProviderApplication = null;
		trainingSite = null;
		company = null;
		primarySdpLinked = null;
		accredNumberEntered = "";
		sdpCompanyActions = null;
	}
	
	public void searchTrainingProviderApplication(){
		try {
			if (accredNumberEntered.trim().isEmpty()) {
				addErrorMessage("Provide application accreditation / certificate number before proceeding with search.");
			} else {
				trainingProviderApplication = trainingProviderApplicationService.findByCertificateNumberOrAccreditationNumberAndInStatus(accredNumberEntered.trim(), ApprovalEnum.getOpenStatusForTrainingProviderApplications());
				if (trainingProviderApplication == null) {
					throw new Exception("Unable to locate open / active application with:" + accredNumberEntered.trim() + " provided.");
				}
				company = companyService.findByKey(trainingProviderApplication.getCompany().getId());
				if (trainingProviderApplication.getTrainingSite() != null && trainingProviderApplication.getTrainingSite().getId() != null) {
					trainingSite = trainingSiteService.findByKey(trainingProviderApplication.getTrainingSite().getId());
				}
				
				// check if primary SDP not assigned
				int counterIfNewDesignationAssigned = 0;
				int counterInWorkflow = 0;
				int userAssignedToCompany = 0;
				int userInWorkflowForCompany = 0;
				if (trainingSite != null && trainingSite.getId() != null) {
					counterIfNewDesignationAssigned = sdpCompanyService.countSdpTypeByTrainingSiteId(trainingSite.getId(), SdpTypeService.instance().findByKey(HAJConstants.PRIMARY_SDP_ID).getId());
					counterInWorkflow = sdpCompanyActionsService.countByNewDesignationIdCompanyTrainingSiteAndOpenStatus(trainingSite.getId(), company.getId(), trainingSite.getId(), ApprovalEnum.getOpenStatusListForSdpCompanyActions());
					if (formUser.getId() != null) {
						userAssignedToCompany = sdpCompanyService.countUserAssignedByTrainingSiteId(trainingSite.getId(), formUser.getId());
						userInWorkflowForCompany = sdpCompanyActionsService.countByUserIdCompanyTrainingSiteAndOpenStatus(formUser.getId(), company.getId(), trainingSite.getId(), ApprovalEnum.getOpenStatusListForSdpCompanyActions());
					}
				} else {
					counterIfNewDesignationAssigned = sdpCompanyService.countSdpTypeByHoldingCompany(company.getId(), SdpTypeService.instance().findByKey(HAJConstants.PRIMARY_SDP_ID).getId());
					counterInWorkflow = sdpCompanyActionsService.countByNewDesignationIdCompanyTrainingSiteAndOpenStatus(SdpTypeService.instance().findByKey(HAJConstants.PRIMARY_SDP_ID).getId(), company.getId(), null, ApprovalEnum.getOpenStatusListForSdpCompanyActions());
					if (formUser.getId() != null) {
						userAssignedToCompany = sdpCompanyService.countUserAssignedByHoldingCompany(company.getId(), formUser.getId());
						userInWorkflowForCompany = sdpCompanyActionsService.countByUserIdCompanyTrainingSiteAndOpenStatus(formUser.getId(), company.getId(), null, ApprovalEnum.getOpenStatusListForSdpCompanyActions());
					}
				}
				if (counterIfNewDesignationAssigned > 0) {
					failedProviderSearch();
					throw new Exception("Designation already assigned to another SDP contact, Please select a different designation.");
				}
				if (counterInWorkflow > 0) {
					failedProviderSearch();
					throw new Exception("Designation already underway in approval process. Please select a different designation.");
				}
				if (userAssignedToCompany > 0) {
					failedProviderSearch();
					throw new Exception("SDP contact user is already assigned to the company. Please provide a different user.");
				}
				if (userInWorkflowForCompany > 0) {
					failedProviderSearch();
					throw new Exception("SDP contact user is underway in an approval process for the company. Please provide a different user.");
				}
				
				providerSelectionDone = true;
				addInfoMessage("Action Complete");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepNewActionDoc(){
		doc = new Doc();
	}
	
	public void prepActionUpload(){
		try {
			prepNewActionDoc();
			runClientSideExecute("PF('uploadSupportingDocDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void storeNewActionFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			}
			// add support doc
			if (docList == null) {
				docList = new ArrayList<>();
			}
			docList.add(doc);
			runClientSideExecute("PF('uploadSupportingDocDlg').hide()");
			addInfoMessage("Documents Uploaded, Awaiting Submission");
			prepNewActionDoc();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void submitSdpRemoval(){
		try {
			if (docList.isEmpty()) {
				throw new Exception("Provide atleast one supporting document before proceeding");
			}
			sdpCompanyActions = new SdpCompanyActions();
			sdpCompanyActions.setSdpCompanyAction(SdpCompanyActionListEnum.NewSDP);
			sdpCompanyActions.setTrainingProviderApplication(trainingProviderApplication);
			sdpCompanyActions.setCompany(company);
			sdpCompanyActions.setNewDesignation(SdpTypeService.instance().findByKey(HAJConstants.PRIMARY_SDP_ID));
			if (trainingSite != null && trainingSite.getId() != null) {
				sdpCompanyActions.setTrainingSite(trainingSite);
			}
			sdpCompanyActionsService.initiateWorkflow(sdpCompanyActions, formUser, null, trainingProviderApplication, formUser, docList);
			failedProviderSearch();
			clearUserSearch();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
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
	 * @param searchUserPassportOrIdUI
	 *            the new search user passport or id UI
	 */
	public void setSearchUserPassportOrIdUI(SearchUserPassportOrIdUI searchUserPassportOrIdUI) {
		this.searchUserPassportOrIdUI = searchUserPassportOrIdUI;
	}

	/**
	 * On tab change.
	 *
	 * @param event
	 *            the event
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
	 * @param searchCompanyUI
	 *            the new search company UI
	 */
	public void setSearchCompanyUI(SearchCompanyUI searchCompanyUI) {
		this.searchCompanyUI = searchCompanyUI;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Users getFormUser() {
		return formUser;
	}

	public void setFormUser(Users formUser) {
		this.formUser = formUser;
	}

	public Long getMaxFileSize() {
		return maxFileSize;
	}

	public void setMaxFileSize(Long maxFileSize) {
		this.maxFileSize = maxFileSize;
	}

	public Long getMAX_FIRST_NAME_SIZE() {
		return MAX_FIRST_NAME_SIZE;
	}

	public void setMAX_FIRST_NAME_SIZE(Long mAX_FIRST_NAME_SIZE) {
		MAX_FIRST_NAME_SIZE = mAX_FIRST_NAME_SIZE;
	}

	public Long getMAX_LAST_NAME_SIZE() {
		return MAX_LAST_NAME_SIZE;
	}

	public void setMAX_LAST_NAME_SIZE(Long mAX_LAST_NAME_SIZE) {
		MAX_LAST_NAME_SIZE = mAX_LAST_NAME_SIZE;
	}

	public Long getMAX_EMAIL_SIZE() {
		return MAX_EMAIL_SIZE;
	}

	public void setMAX_EMAIL_SIZE(Long mAX_EMAIL_SIZE) {
		MAX_EMAIL_SIZE = mAX_EMAIL_SIZE;
	}

	public Long getMAX_FAX_NUMBER() {
		return MAX_FAX_NUMBER;
	}

	public void setMAX_FAX_NUMBER(Long mAX_FAX_NUMBER) {
		MAX_FAX_NUMBER = mAX_FAX_NUMBER;
	}

	public Long getMAX_COMPANY_NAME_SIZE() {
		return MAX_COMPANY_NAME_SIZE;
	}

	public void setMAX_COMPANY_NAME_SIZE(Long mAX_COMPANY_NAME_SIZE) {
		MAX_COMPANY_NAME_SIZE = mAX_COMPANY_NAME_SIZE;
	}

	public String getFAX_NUMBER_FORMAT() {
		return FAX_NUMBER_FORMAT;
	}

	public void setFAX_NUMBER_FORMAT(String fAX_NUMBER_FORMAT) {
		FAX_NUMBER_FORMAT = fAX_NUMBER_FORMAT;
	}

	public String getTELPHONE_FORMAT() {
		return TELPHONE_FORMAT;
	}

	public String getCELLPHONE_FORMAT() {
		return CELLPHONE_FORMAT;
	}
	
	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public List<Doc> getDocList() {
		return docList;
	}

	public void setDocList(List<Doc> docList) {
		this.docList = docList;
	}

	public TrainingProviderApplication getTrainingProviderApplication() {
		return trainingProviderApplication;
	}

	public void setTrainingProviderApplication(TrainingProviderApplication trainingProviderApplication) {
		this.trainingProviderApplication = trainingProviderApplication;
	}

	public TrainingSite getTrainingSite() {
		return trainingSite;
	}

	public void setTrainingSite(TrainingSite trainingSite) {
		this.trainingSite = trainingSite;
	}

	public Boolean getUserSectionDone() {
		return userSectionDone;
	}

	public void setUserSectionDone(Boolean userSectionDone) {
		this.userSectionDone = userSectionDone;
	}

	public Boolean getProviderSelectionDone() {
		return providerSelectionDone;
	}

	public void setProviderSelectionDone(Boolean providerSelectionDone) {
		this.providerSelectionDone = providerSelectionDone;
	}

	public String getUserSetmisValidiations() {
		return userSetmisValidiations;
	}

	public void setUserSetmisValidiations(String userSetmisValidiations) {
		this.userSetmisValidiations = userSetmisValidiations;
	}

	public String getAccredNumberEntered() {
		return accredNumberEntered;
	}

	public void setAccredNumberEntered(String accredNumberEntered) {
		this.accredNumberEntered = accredNumberEntered;
	}
	
}