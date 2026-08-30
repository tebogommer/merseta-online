package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.ProcessRoles;
import haj.com.entity.QdfCompany;
import haj.com.entity.QualificationsCurriculumDevelopment;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.DocService;
import haj.com.service.QdfCompanyService;
import haj.com.service.TasksService;
import haj.com.service.UsersService;
import haj.com.service.lookup.RejectReasonsService;

@ManagedBean(name = "qdfCompanyRegistrationUI")
@ViewScoped
public class QdfCompanyRegistrationUI extends AbstractUI {

	/** Entity Level */
	private QdfCompany qdfCompany = null;
	private Company selectedCompany = null;
	private Users formUser;
	

	/** Service Level */
	private QdfCompanyService qdfCompanyService = null;
	private CompanyService companyService = null;
	private UsersService usersService = null;
	private Users createUser;
	private TasksService taskService = new TasksService();

	/** Booleans */
	private Boolean doneSearch = null;

	/** Managed Properties . */
	@ManagedProperty(value = "#{searchUserPassportOrIdUI}")
	private SearchUserPassportOrIdUI searchUserPassportOrIdUI;

	/** HAJ Config Properties */
	private Long maxFileSize = HAJConstants.MAX_FILE_SIZE;
	private Long MAX_FIRST_NAME_SIZE = HAJConstants.MAX_FIRST_NAME_SIZE;
	private Long MAX_LAST_NAME_SIZE = HAJConstants.MAX_LAST_NAME_SIZE;
	private Long MAX_EMAIL_SIZE = HAJConstants.MAX_EMAIL_SIZE;
	private final String TELPHONE_FORMAT = HAJConstants.TELPHONE_FORMAT;
	private final String CELLPHONE_FORMAT = HAJConstants.CELLPHONE_FORMAT;
	private Long MAX_FAX_NUMBER = HAJConstants.MAX_FAX_NUMBER;
	private Long MAX_COMPANY_NAME_SIZE = HAJConstants.MAX_COMPANY_NAME_SIZE;
	private String companyRegistrationNumberFormat = HAJConstants.companyRegistrationNumberFormat;
	private String FAX_NUMBER_FORMAT = HAJConstants.FAX_NUMBER_FORMAT;
	private Doc doc;
	
	private ArrayList<RejectReasons> selectedRejectReason=new ArrayList<>();
	private List<RejectReasons> rejectReason=new ArrayList<>();

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
	 * Initialize method to get all QdfCompany
	 * 
	 * @author TechFinium
	 * @see QdfCompany
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		populateServiceLayers();
		
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.QDF_Registration) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));

			qdfCompany = qdfCompanyService.findByKey(getSessionUI().getTask().getTargetKey());
			if(qdfCompany.getCompany() !=null)
			{
				selectedCompany = companyService.findByKey(qdfCompany.getCompany().getId());
			}
			
			//qdfCompanyService.prepareNewRegistration(getSessionUI().getTask().getWorkflowProcess(), selectedCompany, qdfCompany, getSessionUI().getTask().getProcessRole());
			// companyService.prepareNewRegistrationType(ConfigDocProcessEnum.QDF_Registration,
			// selectedCompany, null, CompanyUserTypeEnum.Company);
			//qdfCompanyService.prepareNewRegistrationC(getSessionUI().getTask().getWorkflowProcess(), selectedCompany, getSessionUI().getTask().getProcessRole());
			
			if (qdfCompany.getUser() != null) {
				formUser = qdfCompany.getUser();
				//qdfCompanyService.prepareNewRegistration(getSessionUI().getTask().getWorkflowProcess(), formUser, getSessionUI().getTask().getProcessRole());	
				
				
				//formUser.setDocs(qdfCompanyService.prepareNewRegistration(QdfCompany.class.getName(), qdfCompany.getId(), ConfigDocProcessEnum.QDF_Registration, CompanyUserTypeEnum.User));
				
				 if (getSessionUI().getTask().getFirstInProcess() != null &&  getSessionUI().getTask().getFirstInProcess()) {
					 qdfCompanyService.prepareDocuments(qdfCompany, true,getSessionUI().getTask().getWorkflowProcess(), CompanyUserTypeEnum.User, getSessionUI().getTask());
				 }else {
					 //qdfCompanyService.prepareDocuments(qdfCompany, false,getSessionUI().getTask().getWorkflowProcess(), CompanyUserTypeEnum.User, getSessionUI().getTask());
					 qdfCompanyService.prepareNewRegistration(getSessionUI().getTask().getWorkflowProcess(), qdfCompany, getSessionUI().getTask().getProcessRole());
				 }
				
				formUser.setDoneSearch(true);
				if (getSessionUI().getTask().getProcessRole() != null && (getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.Edit || getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.EditUpload)) {
					formUser.setRegFieldsDone(false);
				} else {
					formUser.setRegFieldsDone(true);
				}
			} else {
				getSearchUserPassportOrIdUI().setObject(this);
				prepNewUser();
			}
			
			if(qdfCompany.getApprovalEnum() == ApprovalEnum.Rejected) {
				populateRejectReasons();
			}
		} else {
			// prevent user from vieiwng information if didnt eneter by task
			super.ajaxRedirectToDashboard();
		}
	}


	
	/**
	 * Populates new instances of service level objects
	 * 
	 * @throws Exception
	 */
	private void populateServiceLayers() throws Exception {
		qdfCompanyService = new QdfCompanyService();
		companyService = new CompanyService();
		usersService = new UsersService();
	}

	/**
	 * Preps new instance of user to be search / created
	 * 
	 * @throws Exception
	 */
	private void prepNewUser() throws Exception {
		this.formUser = new Users();
		formUser.setAdmin(false);
		formUser.setDoneSearch(false);
		formUser.setRegFieldsDone(false);
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
				this.formUser = (Users) object;
				formUser.setDocs(qdfCompanyService.prepareNewRegistration(QdfCompany.class.getName(), qdfCompany.getId(), ConfigDocProcessEnum.QDF_Registration, CompanyUserTypeEnum.User));
				qdfCompany.setUser(formUser);
				if (formUser.getId() != null) qdfCompanyService.update(qdfCompany);
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/** Actiosn Start */
	// view, edit, upload, editUpload permissions
	public void completeTask() {
		try {
			if (getSessionUI().getTask().getProcessRole() == null) {
				qdfCompanyService.completeToFirst(qdfCompany, getSessionUI().getActiveUser(), getSessionUI().getTask(), formUser);
			} else {
				qdfCompanyService.completeTask(qdfCompany, getSessionUI().getActiveUser(), getSessionUI().getTask(), formUser);
			}
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	// reject allowed
	public void rejectTask() {
		try {
			if (selectedRejectReason.size() == 0) {
				throw new Exception("Please select a reject reason");	
			}
			qdfCompanyService.rejectTask(qdfCompany, getSessionUI().getActiveUser(), getSessionUI().getTask(), formUser, selectedRejectReason);
			getSessionUI().setTask(null);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	// approve permissions
	public void approveTask() {
		try {
			if(formUser!=null && formUser.isDoneSearch()) {
				qdfCompanyService.approveTask(qdfCompany, getSessionUI().getActiveUser(), getSessionUI().getTask(), formUser);
				getSessionUI().setTask(null);
				addInfoMessage(super.getEntryLanguage("update.successful"));
				super.ajaxRedirectToDashboard();
			}else {
				addErrorMessage("Please select RSA ID/Passport Number");
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	// final approve permissions
	public void finalApproveTask() {
		try {
			//createUser = taskService.findUserByCompany(selectedCompany);
			createUser=qdfCompany.getQualificationsCurriculumDevelopment().getCreateUser();
			
			qdfCompanyService.finalApproveTask(qdfCompany, getSessionUI().getActiveUser(), getSessionUI().getTask(), formUser);
			getSessionUI().setTask(null);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	// final reject with permission
	public void finalRejectTask() {
		try {
			if (selectedRejectReason.size() == 0) {
				throw new Exception("Please select a reject reason");	
			}
			qdfCompanyService.finalRejectTask(qdfCompany, getSessionUI().getActiveUser(), getSessionUI().getTask(), formUser, selectedRejectReason);
			getSessionUI().setTask(null);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void storeFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			} else {
				doc.setTargetKey(qdfCompany.getId());
				doc.setTargetClass(QdfCompany.class.getName());
				if (doc.getId() == null) DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.QDF_Registration);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	private void populateRejectReasons() {
		RejectReasonsService rs= new RejectReasonsService();
		try {
			rejectReason = rs.locateReasonsSelectedByTargetKeyClassAndProcess(qdfCompany.getId(), QdfCompany.class.getName(), ConfigDocProcessEnum.QDF_Registration);			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}		
	}

	/** Actiosn End */

	/** Getters and setters */
	public QdfCompany getQdfCompany() {
		return qdfCompany;
	}

	public void setQdfCompany(QdfCompany qdfCompany) {
		this.qdfCompany = qdfCompany;
	}

	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public Boolean getDoneSearch() {
		return doneSearch;
	}

	public void setDoneSearch(Boolean doneSearch) {
		this.doneSearch = doneSearch;
	}

	public Users getFormUser() {
		return formUser;
	}

	public void setFormUser(Users formUser) {
		this.formUser = formUser;
	}

	public SearchUserPassportOrIdUI getSearchUserPassportOrIdUI() {
		return searchUserPassportOrIdUI;
	}

	public void setSearchUserPassportOrIdUI(SearchUserPassportOrIdUI searchUserPassportOrIdUI) {
		this.searchUserPassportOrIdUI = searchUserPassportOrIdUI;
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

	public String getCompanyRegistrationNumberFormat() {
		return companyRegistrationNumberFormat;
	}

	public void setCompanyRegistrationNumberFormat(String companyRegistrationNumberFormat) {
		this.companyRegistrationNumberFormat = companyRegistrationNumberFormat;
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

	public ArrayList<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(ArrayList<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	public List<RejectReasons> getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(List<RejectReasons> rejectReason) {
		this.rejectReason = rejectReason;
	}

	public Users getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Users createUser) {
		this.createUser = createUser;
	}

}
