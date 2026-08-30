package haj.com.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.CompanyLearnersTradeTest;
import haj.com.entity.Doc;
import haj.com.entity.Learners;
import haj.com.entity.LearnershipDevelopmentRegistration;
import haj.com.entity.QdfCompany;
import haj.com.entity.QdfCompanyUsers;
import haj.com.entity.QualificationsCurriculumDevelopment;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.QCDTemplateTypeEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.DocService;
import haj.com.service.QdfCompanyService;
import haj.com.service.QdfCompanyUsersService;
import haj.com.service.QualificationsCurriculumDevelopmentService;
import haj.com.service.TasksService;
import haj.com.service.UsersService;
import haj.com.service.lookup.QualificationService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "qualificationsCurriculumDevelopmentUI")
@ViewScoped
public class QualificationsCurriculumDevelopmentUI extends AbstractUI {

	/** Entity Level */
	private QualificationsCurriculumDevelopment qualificationscurriculumdevelopment = null;
	private Qualification selectedRealignmentQualification = null;
	private Qualification selectedReviewQualification = null;
	private Users trainingProviderUser;
	private Company company = null;
	private Company selectedCompany;
	private Doc doc;
	private Users createUser;

	/** Service Level */
	private QualificationsCurriculumDevelopmentService service = new QualificationsCurriculumDevelopmentService();
	private QualificationService qualificationService = new QualificationService();
	private CompanyService companyService = new CompanyService();
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	private UsersService usersService = new UsersService();
	private TasksService taskService = new TasksService();

	/** Array Lists */
	private List<QualificationsCurriculumDevelopment> qualificationscurriculumdevelopmentList = null;
	private List<QualificationsCurriculumDevelopment> qualificationscurriculumdevelopmentfilteredList = null;
	private List<Qualification> qualifications = new ArrayList<>();
	private List<Company> companies = null;

	private List<QualificationsCurriculumDevelopment> selectedCompQuallDevtList = new ArrayList<>();

	/** The Selected Company */
	private Company selectedQualDevCompany = new Company();

	/** Data Model Lists */
	private LazyDataModel<QualificationsCurriculumDevelopment> dataModel;

	/** Data company Model Lists */
	private LazyDataModel<Company> companyDataModel;

	/** The selectedQualDevelopment */
	private QualificationsCurriculumDevelopment selectedQualDevelopment = new QualificationsCurriculumDevelopment();

	/** Boolean */
	private Boolean docUploadUser;

	private ArrayList<RejectReasons> selectedRejectReason = new ArrayList<>();
	private List<RejectReasons> rejectReason = new ArrayList<>();

	private QdfCompany qdfCompany;
	private List<QdfCompany> qdfCompanyList;
	private QdfCompanyService qdfCompanyService = new QdfCompanyService();
	
	private List<QdfCompanyUsers> qdfUsersList;
	private QdfCompanyUsersService qdfCompanyUsersService = new QdfCompanyUsersService();

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
	 * Initialize method to get all QualificationsCurriculumDevelopment and prepare
	 * a for a create of a new QualificationsCurriculumDevelopment
	 * 
	 * @author TechFinium
	 * @see QualificationsCurriculumDevelopment
	 * @throws Exception the exception
	 */
	private void runInit() throws Exception {
		prepareQualDevCompanyfor();
		if (super.getParameter("id", false) != null) {
			if (getSessionUI().getTask() != null && (getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.QUALIFICATIONS_CURRICULUM_DEVELOPMENT)) {
				getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
				this.qualificationscurriculumdevelopment = service.findByKey(getSessionUI().getTask().getTargetKey());
				service.prepareNewRegistration(getSessionUI().getTask().getWorkflowProcess(), qualificationscurriculumdevelopment, getSessionUI().getTask().getProcessRole());
				if (getSessionUI().getTask().getProcessRole() == null) {
					service.prepareQualificationsCurriculumDevelopmentDocs(ConfigDocProcessEnum.QUALIFICATIONS_CURRICULUM_DEVELOPMENT, qualificationscurriculumdevelopment, qualificationscurriculumdevelopment, null);
				}
				if (qualificationscurriculumdevelopment.getCompany() != null) {
					selectedCompany = companyService.findByKey(qualificationscurriculumdevelopment.getCompany().getId());
				}

				if (this.qualificationscurriculumdevelopment.getTemplateType() == QCDTemplateTypeEnum.Review) {
					selectedReviewQualification = qualificationService.findByKey(Long.parseLong(qualificationscurriculumdevelopment.getReviewQualificationIdList()));
				} else if (this.qualificationscurriculumdevelopment.getTemplateType() == QCDTemplateTypeEnum.ReAlignment) {

					selectedRealignmentQualification = qualificationService.findByKey(Long.parseLong(qualificationscurriculumdevelopment.getReAlignmentQualificationIdList()));
				}
				if (qualificationscurriculumdevelopment.getStatus() == ApprovalEnum.Rejected) {
					populateRejectReasons();
				}
				setTheQualificatonItemsIntoTheRelevantLists();
			} else {
				// prepareNew();
				qualificationscurriculumdevelopmentInfo();
				qualificationDevInfo();
			}
		} else {
			// prepareNew();
			qualificationscurriculumdevelopmentInfo();
			qualificationDevInfo();
		}
	}

	/**
	 * @throws Exception
	 */
	private void setTheQualificatonItemsIntoTheRelevantLists() throws Exception {
		String reviewQualificationsListIdString = this.qualificationscurriculumdevelopment.getReviewQualificationIdList();
		String reAlignmentQualificationsListIdString = this.qualificationscurriculumdevelopment.getReAlignmentQualificationIdList();
		if (!reviewQualificationsListIdString.trim().equals("")) {
			convertIdListIntoObjectsAndSetIntoTransientList(reviewQualificationsListIdString);
		}
		if (!reAlignmentQualificationsListIdString.trim().equals("")) {
			convertIdListIntoObjectsAndSetIntoTransientList(reAlignmentQualificationsListIdString);
		}
	}

	/**
	 * @param str
	 * @throws Exception
	 */
	private void convertIdListIntoObjectsAndSetIntoTransientList(String str) throws Exception {
		List<Qualification> list = new ArrayList<Qualification>();
		List<String> items = Arrays.asList(str.split("\\s*,\\s*"));
		for (String item : items) {
			Qualification theQualification = qualificationService.findByKey(new Long(item));
			list.add(theQualification);
		}
		if (qualificationscurriculumdevelopment.getTemplateType() == QCDTemplateTypeEnum.Review) {
			qualificationscurriculumdevelopment.setReviewQualificationList(list);
		} else if (qualificationscurriculumdevelopment.getTemplateType() == QCDTemplateTypeEnum.ReAlignment) {
			qualificationscurriculumdevelopment.setReAlignmentQualificationList(list);
		} else {
			throw new Exception("Incorrect template type captured as " + qualificationscurriculumdevelopment.getTemplateType());
		}
	}

	/** Prepare selectedQualDevCompany Information */
	public void prepareQualDevCompanyfor() {
		try {
			selectedQualDevelopment = new QualificationsCurriculumDevelopment();
			selectedCompQuallDevtList = service.allQualificationsCurriculumDevelopment();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.addErrorMessage(e.getMessage());
		}
	}

	public void qualificationDevInfo() {
		companyDataModel = new LazyDataModel<Company>() {
			private static final long serialVersionUID = 1L;
			private List<Company> retorno = new ArrayList<Company>();

			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allQualificationAsCompany(first, pageSize, sortField, sortOrder, filters);
					companyDataModel.setRowCount(service.countQualificationAsCompany(QualificationsCurriculumDevelopment.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Company obj) {
				return obj.getId();
			}

			@Override
			public Company getRowData(String rowKey) {
				for (Company obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * 
	 */
	public void addQualificationToRealignmentTable(SelectEvent event) {
		if (qualificationscurriculumdevelopment.getReAlignmentQualificationList() == null) {
			qualificationscurriculumdevelopment.setReAlignmentQualificationList(new ArrayList() {
			});
		}
		qualificationscurriculumdevelopment.getReAlignmentQualificationList().add(this.getSelectedRealignmentQualification());
	}

	/**
	 * 
	 */
	public void removeFromQualificationToRealignmentTable() {
		qualificationscurriculumdevelopment.getReAlignmentQualificationList().remove(this.getSelectedRealignmentQualification());
	}

	/**
	 * 
	 */
	public void addQualificationToReviewTable(SelectEvent event) {
		if (qualificationscurriculumdevelopment.getReviewQualificationList() == null) {
			qualificationscurriculumdevelopment.setReviewQualificationList(new ArrayList() {
			});
		}
		qualificationscurriculumdevelopment.getReviewQualificationList().add(this.getSelectedReviewQualification());
	}

	/**
	 * 
	 */
	public void removeFromQualificationToReviewTable() {
		qualificationscurriculumdevelopment.getReviewQualificationList().remove(this.getSelectedReviewQualification());
	}

	/**
	 * 
	 */
	public List<Qualification> completeMethodQualification(String searchString) {
		List<Qualification> qualifications = null;
		try {
			qualifications = qualificationService.findByName(searchString);
		} catch (Exception exception) {
			this.addErrorMessage(exception.getMessage());
		}
		return qualifications;
	}

	/**
	 * Get all QualificationsCurriculumDevelopment for data table
	 * 
	 * @author TechFinium
	 * @see QualificationsCurriculumDevelopment
	 */
	public void qualificationscurriculumdevelopmentInfo() {
		try {
			companies = companyUsersService.findByUserType(getSessionUI().getActiveUser(), ConfigDocProcessEnum.TP);
			for (Company company : companies) {
				company.setQualificationsCurriculumDevelopmentList(service.findByCompany(company));
			}
		} catch (Exception e) {
			this.addErrorMessage(e.getMessage());
		}
	}

	/**
	 * 
	 */
	public void prepNewQualificationsCurriculumDevelopment() {
		this.qualificationscurriculumdevelopment = new QualificationsCurriculumDevelopment();
		this.qualificationscurriculumdevelopment.setCreateDate(new Date(System.currentTimeMillis()));
		this.qualificationscurriculumdevelopment.setCreateUser(this.getSessionUI().getActiveUser());
		if (selectedCompany != null && selectedCompany.getId() != null) {
			this.qualificationscurriculumdevelopment.setCompany(selectedCompany);
		}

		if (qualificationscurriculumdevelopment.getReAlignmentQualificationList() == null) {
			qualificationscurriculumdevelopment.setReAlignmentQualificationList(new ArrayList() {
			});
		}
		if (qualificationscurriculumdevelopment.getReviewQualificationList() == null) {
			qualificationscurriculumdevelopment.setReviewQualificationList(new ArrayList() {
			});
		}

		try {
			service.prepareNewDocs(qualificationscurriculumdevelopment);
			service.prepareNewLearnershipDevelopmentRegistrationDocs(ConfigDocProcessEnum.QUALIFICATIONS_CURRICULUM_DEVELOPMENT, qualificationscurriculumdevelopment, qualificationscurriculumdevelopment, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cancelSubmit() {
		qualificationscurriculumdevelopment = null;
	}

	/**
	 * Insert QualificationsCurriculumDevelopment into database
	 * 
	 * @author TechFinium
	 * @see QualificationsCurriculumDevelopment
	 */
	public void qualificationscurriculumdevelopmentInsert() {
		try {
			service.create(this.qualificationscurriculumdevelopment);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			qualificationscurriculumdevelopmentInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update QualificationsCurriculumDevelopment in database
	 * 
	 * @author TechFinium
	 * @see QualificationsCurriculumDevelopment
	 */
	public void qualificationscurriculumdevelopmentUpdate() {
		try {
			service.update(this.qualificationscurriculumdevelopment);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			qualificationscurriculumdevelopmentInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete QualificationsCurriculumDevelopment from database
	 * 
	 * @author TechFinium
	 * @see QualificationsCurriculumDevelopment
	 */
	public void qualificationscurriculumdevelopmentDelete() {
		try {
			service.delete(this.qualificationscurriculumdevelopment);
			prepareNew();
			qualificationscurriculumdevelopmentInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Converts the transient object populated into a string list for storing to the
	 * database
	 */
	private void convertTransListsIntoStrings() {
		try {
			String realignmentQualification = GenericUtility.convertListIntoSeperatedString(qualificationscurriculumdevelopment.getReAlignmentQualificationList(), ",");
			String reviewQualification = GenericUtility.convertListIntoSeperatedString(qualificationscurriculumdevelopment.getReviewQualificationList(), ",");
			qualificationscurriculumdevelopment.setReAlignmentQualificationIdList(realignmentQualification);
			qualificationscurriculumdevelopment.setReviewQualificationIdList(reviewQualification);
		} catch (Exception exception) {
			this.addErrorMessage(exception.getMessage());
		}
	}

	// ******************************************************************************************************************************************************
	// //

	public void submitQualificationsCurriculumDevelopment() {
		try {
			convertTransListsIntoStrings();
			service.submitQualificationsCurriculumDevelopment(qualificationscurriculumdevelopment, getSessionUI().getActiveUser());
			this.qualificationscurriculumdevelopment = null;
			addInfoMessage("Your application has been sent for review");
			qualificationscurriculumdevelopmentInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void completeQualificationsCurriculumDevelopment() {
		try {
			service.completeQualificationsCurriculumDevelopment(qualificationscurriculumdevelopment, getSessionUI().getActiveUser(), getSessionUI().getTask());
			addInfoMessage(super.getEntryLanguage("update.successful"));
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void reSubmitQualificationsCurriculumDevelopment() {
		try {
			service.reSubmitQualificationsCurriculumDevelopment(qualificationscurriculumdevelopment, getSessionUI().getActiveUser(), getSessionUI().getTask());
			addInfoMessage(super.getEntryLanguage("update.successful"));
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveQualificationsCurriculumDevelopment() {
		try {
			createUser = qualificationscurriculumdevelopment.getCreateUser();
			// createUser = taskService.findUserByCompany(selectedCompany);
			service.approveQualificationsCurriculumDevelopment(qualificationscurriculumdevelopment, getSessionUI().getActiveUser(), getSessionUI().getTask(), createUser);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalApproveQualificationsCurriculumDevelopment() {
		try {
			// createUser = taskService.findUserByCompany(selectedCompany);
			createUser = qualificationscurriculumdevelopment.getCreateUser();
			service.finalApproveQualificationsCurriculumDevelopment(qualificationscurriculumdevelopment, getSessionUI().getActiveUser(), getSessionUI().getTask(), createUser);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void completeUploadQualificationsCurriculumDevelopment() {
		try {
			// createUser = taskService.findUserByCompany(selectedCompany);
			createUser = qualificationscurriculumdevelopment.getCreateUser();
			service.completeUploadQualificationsCurriculumDevelopment(qualificationscurriculumdevelopment, getSessionUI().getActiveUser(), getSessionUI().getTask(), createUser);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectQualificationsCurriculumDevelopment() {
		try {
			if (selectedRejectReason.size() == 0) {
				throw new Exception("Please select a reject reason");
			}

			// createUser = taskService.findUserByCompany(selectedCompany);
			createUser = qualificationscurriculumdevelopment.getCreateUser();
			service.rejectQualificationsCurriculumDevelopment(qualificationscurriculumdevelopment, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason, createUser);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectQualificationsCurriculumDevelopmentToApplicant() {
		try {
			if (selectedRejectReason.size() == 0) {
				throw new Exception("Please select a reject reason");
			}
			service.rejectQualificationsCurriculumDevelopmentToApplicant(qualificationscurriculumdevelopment, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalRejectQualificationsCurriculumDevelopment() {
		try {
			if (selectedRejectReason.size() == 0) {
				throw new Exception("Please select a reject reason");
			}
			// createUser = taskService.findUserByCompany(selectedCompany);
			createUser = qualificationscurriculumdevelopment.getCreateUser();
			service.finalRejectQualificationsCurriculumDevelopment(qualificationscurriculumdevelopment, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason, createUser);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Store file.
	 *
	 * @param event the event
	 */
	public void storeFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			if (doc.getId() == null) {
				// new document
				if (!docUploadUser) {
					// if document is for company
					companyService.documentUpload(doc.getCompany(), getSessionUI().getActiveUser());
				} else {
					// if document is for user
					doc.setCompany(null);
					doc.setForUsers(trainingProviderUser);
					usersService.documentUpload(trainingProviderUser, getSessionUI().getActiveUser());
				}
			} else {
				if (!docUploadUser) {
					// if document is for company
					companyService.uploadNewVersion(doc, getSessionUI().getActiveUser());
				} else {
					// if document is for user
					usersService.uploadNewVersion(doc, getSessionUI().getActiveUser());
				}
			}
			addInfoMessage(super.getEntryLanguage("upload.successful"));
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Handles document upload against LearnershipDevelopmentRegistration
	 * 
	 * @param event
	 */
	public void storeNewFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			} else {
				doc.setTargetKey(qualificationscurriculumdevelopment.getId());
				doc.setTargetClass(QualificationsCurriculumDevelopment.class.getName());
				if (doc.getId() == null)
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void storeNewFileInMemory(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	// ******************************************************************************************************************************************************
	// //

	/**
	 * Create new instance of QualificationsCurriculumDevelopment
	 * 
	 * @author TechFinium
	 * @see QualificationsCurriculumDevelopment
	 */
	public void prepareNew() {
		qualificationscurriculumdevelopment = new QualificationsCurriculumDevelopment();
		try {
			service.prepareNewDocs(qualificationscurriculumdevelopment, ConfigDocProcessEnum.QUALIFICATIONS_CURRICULUM_DEVELOPMENT, CompanyUserTypeEnum.Company);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Complete.
	 *
	 * @param desc the desc
	 * @return the list
	 */
	public List<QualificationsCurriculumDevelopment> complete(String desc) {
		List<QualificationsCurriculumDevelopment> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.QUALIFICATIONS_CURRICULUM_DEVELOPMENT);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	private void populateRejectReasons() {
		RejectReasonsService rs = new RejectReasonsService();
		try {
			rejectReason = rs.locateReasonsSelectedByTargetKeyClassAndProcess(qualificationscurriculumdevelopment.getId(), QualificationsCurriculumDevelopment.class.getName(), ConfigDocProcessEnum.QUALIFICATIONS_CURRICULUM_DEVELOPMENT);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<QualificationsCurriculumDevelopment> userQualificationsCurriculumDevelopment() {
		List<QualificationsCurriculumDevelopment> list = null;
		try {
			list = service.findByUser(getSessionUI().getActiveUser().getId());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}

		return list;
	}

	public void listenerMethod() {
		try {
			qdfCompanyList = new ArrayList<>();
			qdfCompanyList = qdfCompanyService.findByQualificationCurriculumDevelopentID(this.selectedQualDevelopment);
			/*
			 * if (!qdfCompanyList.isEmpty()) {
			 * this.qdfCompany.setAdditionalDocs(DocService.instance().
			 * searchByTargetKeyAndClassNoConfigDoc(qdfCompany.getClass().getName(),
			 * qdfCompany.getId())); }
			 */		
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void findDcuments(QdfCompany selectedQdfCompany) {
		try {
			this.qdfCompany = new QdfCompany();
			this.qdfCompany = selectedQdfCompany;
			this.qdfCompany.setAdditionalDocs(DocService.instance().searchByTargetKeyAndClassNoConfigDoc(this.qdfCompany.getClass().getName(), this.qdfCompany.getId()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void findQdfUsers(QdfCompany selectedQdfCompany) {
		try {
			this.qdfUsersList = new ArrayList<>();
			this.qdfUsersList = qdfCompanyUsersService.findByQdfCompany(selectedQdfCompany);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public QualificationsCurriculumDevelopment getQualificationscurriculumdevelopment() {
		return qualificationscurriculumdevelopment;
	}

	public void setQualificationscurriculumdevelopment(QualificationsCurriculumDevelopment qualificationscurriculumdevelopment) {
		this.qualificationscurriculumdevelopment = qualificationscurriculumdevelopment;
	}

	public List<QualificationsCurriculumDevelopment> getQualificationsCurriculumDevelopmentfilteredList() {
		return qualificationscurriculumdevelopmentfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param qualificationscurriculumdevelopmentfilteredList the new
	 *                                                        qualificationscurriculumdevelopmentfilteredList
	 *                                                        list
	 * @see QualificationsCurriculumDevelopment
	 */
	public void setQualificationsCurriculumDevelopmentfilteredList(List<QualificationsCurriculumDevelopment> qualificationscurriculumdevelopmentfilteredList) {
		this.qualificationscurriculumdevelopmentfilteredList = qualificationscurriculumdevelopmentfilteredList;
	}

	public LazyDataModel<QualificationsCurriculumDevelopment> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<QualificationsCurriculumDevelopment> dataModel) {
		this.dataModel = dataModel;
	}

	public QualificationsCurriculumDevelopmentService getService() {
		return service;
	}

	public void setService(QualificationsCurriculumDevelopmentService service) {
		this.service = service;
	}

	public List<QualificationsCurriculumDevelopment> getQualificationscurriculumdevelopmentList() {
		return qualificationscurriculumdevelopmentList;
	}

	public void setQualificationscurriculumdevelopmentList(List<QualificationsCurriculumDevelopment> qualificationscurriculumdevelopmentList) {
		this.qualificationscurriculumdevelopmentList = qualificationscurriculumdevelopmentList;
	}

	public List<QualificationsCurriculumDevelopment> getQualificationscurriculumdevelopmentfilteredList() {
		return qualificationscurriculumdevelopmentfilteredList;
	}

	public void setQualificationscurriculumdevelopmentfilteredList(List<QualificationsCurriculumDevelopment> qualificationscurriculumdevelopmentfilteredList) {
		this.qualificationscurriculumdevelopmentfilteredList = qualificationscurriculumdevelopmentfilteredList;
	}

	public List<Qualification> getQualifications() {
		return qualifications;
	}

	public void setQualifications(List<Qualification> qualifications) {
		this.qualifications = qualifications;
	}

	public QualificationService getQualificationService() {
		return qualificationService;
	}

	public void setQualificationService(QualificationService qualificationService) {
		this.qualificationService = qualificationService;
	}

	public Qualification getSelectedRealignmentQualification() {
		return selectedRealignmentQualification;
	}

	public void setSelectedRealignmentQualification(Qualification selectedRealignmentQualification) {
		this.selectedRealignmentQualification = selectedRealignmentQualification;
	}

	public Qualification getSelectedReviewQualification() {
		return selectedReviewQualification;
	}

	public void setSelectedReviewQualification(Qualification selectedReviewQualification) {
		this.selectedReviewQualification = selectedReviewQualification;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public Boolean getDocUploadUser() {
		return docUploadUser;
	}

	public void setDocUploadUser(Boolean docUploadUser) {
		this.docUploadUser = docUploadUser;
	}

	public Users getTrainingProviderUser() {
		return trainingProviderUser;
	}

	public void setTrainingProviderUser(Users trainingProviderUser) {
		this.trainingProviderUser = trainingProviderUser;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public CompanyUsersService getCompanyUsersService() {
		return companyUsersService;
	}

	public void setCompanyUsersService(CompanyUsersService companyUsersService) {
		this.companyUsersService = companyUsersService;
	}

	public UsersService getUsersService() {
		return usersService;
	}

	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	/**
	 * @return the companyDataModel
	 */
	public LazyDataModel<Company> getCompanyDataModel() {
		return companyDataModel;
	}

	/**
	 * @param companyDataModel the companyDataModel to set
	 */
	public void setCompanyDataModel(LazyDataModel<Company> companyDataModel) {
		this.companyDataModel = companyDataModel;
	}

	/**
	 * @return the selectedQualDevCompany
	 */
	public Company getSelectedQualDevCompany() {
		return selectedQualDevCompany;
	}

	/**
	 * @param selectedQualDevCompany the selectedQualDevCompany to set
	 */
	public void setSelectedQualDevCompany(Company selectedQualDevCompany) {
		this.selectedQualDevCompany = selectedQualDevCompany;
	}

	/**
	 * @return the selectedCompQuallDevtList
	 */
	public List<QualificationsCurriculumDevelopment> getSelectedCompQuallDevtList() {
		return selectedCompQuallDevtList;
	}

	/**
	 * @param selectedCompQuallDevtList the selectedCompQuallDevtList to set
	 */
	public void setSelectedCompQuallDevtList(List<QualificationsCurriculumDevelopment> selectedCompQuallDevtList) {
		this.selectedCompQuallDevtList = selectedCompQuallDevtList;
	}

	/**
	 * @return the selectedQualDevelopment
	 */
	public QualificationsCurriculumDevelopment getSelectedQualDevelopment() {
		return selectedQualDevelopment;
	}

	/**
	 * @param selectedQualDevelopment the selectedQualDevelopment to set
	 */
	public void setSelectedQualDevelopment(QualificationsCurriculumDevelopment selectedQualDevelopment) {
		this.selectedQualDevelopment = selectedQualDevelopment;
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

	public QdfCompany getQdfCompany() {
		return qdfCompany;
	}

	public void setQdfCompany(QdfCompany qdfCompany) {
		this.qdfCompany = qdfCompany;
	}

	public List<QdfCompany> getQdfCompanyList() {
		return qdfCompanyList;
	}

	public void setQdfCompanyList(List<QdfCompany> qdfCompanyList) {
		this.qdfCompanyList = qdfCompanyList;
	}

	public List<QdfCompanyUsers> getQdfUsersList() {
		return qdfUsersList;
	}

	public void setQdfUsersList(List<QdfCompanyUsers> qdfUsersList) {
		this.qdfUsersList = qdfUsersList;
	}

}
