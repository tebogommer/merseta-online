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

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.Doc;
import haj.com.entity.QdfCompany;
import haj.com.entity.QdfCompanyUsers;
import haj.com.entity.ScheduledEvent;
import haj.com.entity.Users;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DocService;
import haj.com.service.QdfCompanyService;
import haj.com.service.QdfCompanyUsersService;
import haj.com.service.RegisterService;
import haj.com.service.ScheduledEventService;
import haj.com.service.TaskUsersService;
import haj.com.service.TasksService;
import haj.com.service.UsersService;
import haj.com.service.lookup.DateChangeReasonsService;
import haj.com.service.lookup.NationalityService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "qdfCompanyUI")
@ViewScoped
public class QdfCompanyUI extends AbstractUI {

	/** The form user. */
	private Users formUser;
	private boolean startFromNew;
	/** The service. */
	private RegisterService registerService = new RegisterService();
	private UsersService usersService = new UsersService();
	private TasksService tasksService = new TasksService();
	private QdfCompanyUsersService service = new QdfCompanyUsersService();

	/** The search user passport or id UI. */
	@ManagedProperty(value = "#{searchUserPassportOrIdUI}")
	private SearchUserPassportOrIdUI searchUserPassportOrIdUI;

	/** The search company UI. */
	@ManagedProperty(value = "#{searchCompanyUI}")
	private SearchCompanyUI searchCompanyUI;

	/** Entity Level */
	private QdfCompany qdfCompany = null;
	private QdfCompany selectedQdfCompany = null;
	private QdfCompanyUsers selectedQdfCompanyUser = null;

	/** Data Qdf Company Model Lists */
	private LazyDataModel<QdfCompany> qdfCompanyDataModel;

	/** Service Level */
	private QdfCompanyService qdfCompanyService = null;

	/** Date Values */
	private Date today = GenericUtility.getStartOfDay(new Date());

	private List<Users> users;
	private List<Users> selectedUsers;
	private Users createUser;
	private Doc doc = null;
	private ScheduledEvent scheduledEvent;
	private ScheduledEventService scheduledEventService = new ScheduledEventService();

	private Date todayDate;
	// private Doc doc;
	List<Doc> docs = new ArrayList<>();

	private Long maxFileSize = HAJConstants.MAX_FILE_SIZE;

	/** maximum size of first name */
	private Long MAX_FIRST_NAME_SIZE = HAJConstants.MAX_FIRST_NAME_SIZE;

	/** maximum size of last name */
	private Long MAX_LAST_NAME_SIZE = HAJConstants.MAX_LAST_NAME_SIZE;

	/** maximum size of email */
	private Long MAX_EMAIL_SIZE = HAJConstants.MAX_EMAIL_SIZE;

	/** Telephone number format */
	private final String TELPHONE_FORMAT = HAJConstants.TELPHONE_FORMAT;

	/** Cell number format */
	private final String CELLPHONE_FORMAT = HAJConstants.CELLPHONE_FORMAT;

	/** maximum size of fax number */
	private Long MAX_FAX_NUMBER = HAJConstants.MAX_FAX_NUMBER;

	/** The maximum size all for company name */
	private Long MAX_COMPANY_NAME_SIZE = HAJConstants.MAX_COMPANY_NAME_SIZE;

	/** The Constant company registration number format. */
	private String companyRegistrationNumberFormat = HAJConstants.companyRegistrationNumberFormat;

	/** The Constant allow FAX number format. */
	private String FAX_NUMBER_FORMAT = HAJConstants.FAX_NUMBER_FORMAT;

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
	 * @throws Exception the exception
	 */
	private void runInit() throws Exception {
		getSearchUserPassportOrIdUI().setObject(this);
		getSearchCompanyUI().setObject(this);
		prepareNew();
		populateServiceLayers();
		qdfCompanyInfo();
		this.selectedQdfCompany = new QdfCompany();
		prepDoc();
	}

	public void qdfCompanyInfo() {
		qdfCompanyDataModel = new LazyDataModel<QdfCompany>() {
			private static final long serialVersionUID = 1L;
			private List<QdfCompany> retorno = new ArrayList<QdfCompany>();

			@Override
			public List<QdfCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (getSessionUI().isExternalParty()) {
						filters.put("user", getSessionUI().getActiveUser());
					}
					retorno = qdfCompanyService.allQdfCompany(QdfCompany.class, first, pageSize, sortField, sortOrder, filters);
					for (QdfCompany qdfCompany : retorno) {
						qdfCompany.setQdfCompanyUsers(service.findByQdfCompany(qdfCompany));
						populateDate(qdfCompany);
					}
					qdfCompanyDataModel.setRowCount(qdfCompanyService.count(QdfCompany.class, filters));
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(QdfCompany obj) {
				return obj.getId();
			}

			@Override
			public QdfCompany getRowData(String rowKey) {
				for (QdfCompany obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	private void populateDate(QdfCompany entity) throws Exception {
		if (entity.getReviewDate() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String strDate1 = sdf.format(entity.getReviewDate());
			String strDate2 = sdf.format(getNow());
			Date meetingDate = sdf.parse(strDate1);
			Date currentDate = sdf.parse(strDate2);
			if (meetingDate.after(currentDate)) {
				entity.setShow(true);
			} else {
				entity.setShow(false);
			}
		} else {
			entity.setShow(false);
		}
	}

	public void setVisitDateOnWorkplaceApproval() {
		try {
			if (selectedUsers.size() == 0) {
				throw new Exception("Please select meeting members");
			}
			if (selectedQdfCompany.getReviewDate() == null) {
				throw new Exception("Please select Review Date");
			}

			qdfCompanyService.setFirstVisitDate(selectedQdfCompany, getSessionUI().getActiveUser(), selectedUsers);
			addInfoMessage("Qualification Development Engagement Set");
			runClientSideExecute("PF('dateSchedule').hide()");

			// caluclatePermissions();
		} catch (Exception e) {
			runClientSideExecute("PF('dateSchedule').hide()");
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void createSDF() {
		// storeClientInfo();
		try {
			if (formUser.getRsaIDNumber() != null && !formUser.getRsaIDNumber().isEmpty()) {
				formUser.setNationality(new NationalityService().findByKey(HAJConstants.SOUTH_AFRICAN_NATIONALITY));
			}

			// Users myUser = usersService.getUserByEmail(formUser.getEmail());
			/*
			 * if(usersService.getUserByEmail(formUser.getEmail())== null) {
			 */
			// registers user
			if (formUser != null && formUser.getId() == null) {
				usersService.checkEmailUsedEmailOnly(formUser.getEmail());
				formUser = registerService.register(formUser, true);
				service.create(new QdfCompanyUsers(selectedQdfCompany, formUser, true));
			} else if (formUser != null && formUser.getId() != null) {
//				usersService.checkEmailUsedEmailOnly(formUser.getEmail());
//				QdfCompanyUsers qu = service.findByUserAndQdfCompany(selectedQdfCompany, formUser);
				
				service.checkUserAssignedToQdfCompany(selectedQdfCompany, formUser);
				service.create(new QdfCompanyUsers(selectedQdfCompany, formUser, true));
			}
			addInfoMessage(formUser.getFirstName() + " " + formUser.getLastName() + " has been successfully added as part of the Qualifcation Development team");
			/*
			 * } else { addErrorMessage(super.getEntryLanguage("user.already.email")); }
			 */

			// read in list again cause not refreshing properly 
			
			
			// companyService.createCompanyAndSendTask(companyList, formUser, true,
			// ConfigDocProcessEnum.SDF,
			// hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), null, null);
			prepareNew();

			/*
			 * if (formUser.getId() != null) storeClientInfo(formUser); companyInfo();
			 */
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/*
	 * @Override public void callBackMethod(Object object) { try { if (object
	 * instanceof Users) { this.formUser = (Users) object; if (formUser.getId() !=
	 * null) { addWarningMessage(getEntryLanguage("user.already.exists")); formUser
	 * = null; } else {
	 * //companyService.prepareNewRegistrationType(ConfigDocProcessEnum.SDF, null,
	 * this.formUser, CompanyUserTypeEnum.User); } }
	 * 
	 * } catch (Exception e) { addErrorMessage(e.getMessage(), e); } }
	 */

	@Override
	public void callBackMethod(Object object) {
		try {
			if (object instanceof Users) {
				this.formUser = (Users) object;
				/*
				 * if (this.companylearners == null) { this.companylearners = new
				 * CompanyLearners(); }
				 */
				searchSetObject();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void searchSetObject() {
		getSearchCompanyUI().setObject(this);
		getSearchUserPassportOrIdUI().setObject(this);
	}

	public void prepareNew() {
		this.formUser = new Users();
		formUser.setAdmin(false);
		formUser.setDoneSearch(false);
		formUser.setRegFieldsDone(false);
		formUser.setAcceptPopi(true);
		formUser.setDoneSearch(false);

		if (startFromNew) {
			try {
				// companyService.prepareNewRegistrationType(ConfigDocProcessEnum.SDF, null,
				// this.formUser, CompanyUserTypeEnum.User);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void listenerMethod(QdfCompany selectedQdfCompany) {
		try {
			// scheduledEvent =
			// scheduledEventService.findByTargetKeyAndTargetClass(selectedQdfCompany.getId(),QdfCompany.class.getName());
			this.selectedQdfCompany.setAdditionalDocs(DocService.instance().searchByTargetKeyAndClassNoConfigDoc(selectedQdfCompany.getClass().getName(), selectedQdfCompany.getId()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public void prepVisitDateOnWorkplaceApproval() {
		try {
			users = (List<Users>) tasksService.findUserByQdfCompany(selectedQdfCompany.getId(), QdfCompany.class.getName(), ConfigDocProcessEnum.QDF_Registration);

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public void prepNewDoc() {
		try {
			doc = new Doc();
			// scheduledEvent =
			// scheduledEventService.findByTargetKeyAndTargetClassReturnOneResult(selectedQdfCompany.getId(),QdfCompany.class.getName());
			prepareNew();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public void delete() {
		try {
			selectedQdfCompanyUser.setSoftDelete(false);
			service.update(selectedQdfCompanyUser);
			addInfoMessage(super.getEntryLanguage("row.deleted"));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Populates new instances of service level objects
	 * 
	 * @throws Exception
	 */
	private void populateServiceLayers() throws Exception {
		qdfCompanyService = new QdfCompanyService();
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
				if (doc.getId() == null)
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void storeNewFile(FileUploadEvent event) {

		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			} else {
				doc.setTargetKey(selectedQdfCompany.getId());
				doc.setTargetClass(QdfCompany.class.getName());
				if (doc.getId() == null)
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			}
			this.selectedQdfCompany.setAdditionalDocs(DocService.instance().searchByTargetKeyAndClassNoConfigDoc(selectedQdfCompany.getClass().getName(), selectedQdfCompany.getId()));
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void storeNewFileRequired(FileUploadEvent event) {

		try {
			if (this.doc.getNote()== "" || this.doc.getNote() == null) {
				throw new Exception("Additional Document Detail Notes");
			}
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			} else {
				doc.setTargetKey(selectedQdfCompany.getId());
				doc.setTargetClass(QdfCompany.class.getName());
				if (doc.getId() == null)
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			}
			this.selectedQdfCompany.setAdditionalDocs(DocService.instance().searchByTargetKeyAndClassNoConfigDoc(selectedQdfCompany.getClass().getName(), selectedQdfCompany.getId()));
			super.runClientSideExecute("PF('dlgUpload2').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepDoc() {
		try {
			doc = new Doc();
			selectedQdfCompany.setAdditionalDocs(new ArrayList<>());
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

	public void doneUserBit() {
		this.formUser.setRegFieldsDone(true);
	}

	/** Actiosn End */

	/** Getters and setters */
	public QdfCompany getQdfCompany() {
		return qdfCompany;
	}

	public void setQdfCompany(QdfCompany qdfCompany) {
		this.qdfCompany = qdfCompany;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public Users getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Users createUser) {
		this.createUser = createUser;
	}

	public LazyDataModel<QdfCompany> getQdfCompanyDataModel() {
		return qdfCompanyDataModel;
	}

	public void setQdfCompanyDataModel(LazyDataModel<QdfCompany> qdfCompanyDataModel) {
		this.qdfCompanyDataModel = qdfCompanyDataModel;
	}

	public QdfCompany getSelectedQdfCompany() {
		return selectedQdfCompany;
	}

	public void setSelectedQdfCompany(QdfCompany selectedQdfCompany) {
		this.selectedQdfCompany = selectedQdfCompany;
	}

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}

	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}

	public List<Users> getSelectedUsers() {
		return selectedUsers;
	}

	public void setSelectedUsers(List<Users> selectedUsers) {
		this.selectedUsers = selectedUsers;
	}

	public ScheduledEvent getScheduledEvent() {
		return scheduledEvent;
	}

	public void setScheduledEvent(ScheduledEvent scheduledEvent) {
		this.scheduledEvent = scheduledEvent;
	}

	public Date getTodayDate() {
		return todayDate;
	}

	public void setTodayDate(Date todayDate) {
		this.todayDate = todayDate;
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

	public SearchCompanyUI getSearchCompanyUI() {
		return searchCompanyUI;
	}

	public void setSearchCompanyUI(SearchCompanyUI searchCompanyUI) {
		this.searchCompanyUI = searchCompanyUI;
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

	public QdfCompanyUsers getSelectedQdfCompanyUser() {
		return selectedQdfCompanyUser;
	}

	public void setSelectedQdfCompanyUser(QdfCompanyUsers selectedQdfCompanyUser) {
		this.selectedQdfCompanyUser = selectedQdfCompanyUser;
	}
}
