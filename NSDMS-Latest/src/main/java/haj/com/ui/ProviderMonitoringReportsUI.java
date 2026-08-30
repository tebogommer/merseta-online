package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.Signoff;
import haj.com.entity.TrainingProviderMonitoring;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.AuditorMonitorReview;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyUsersService;
import haj.com.service.DocService;
import haj.com.service.SignoffService;
import haj.com.service.TasksService;
import haj.com.service.TrainingProviderMonitoringService;
import haj.com.service.lookup.AuditorMonitorReviewService;

@ManagedBean(name = "providermonitoringreportsUI")
@ViewScoped
public class ProviderMonitoringReportsUI extends AbstractUI {

	/* Entity Level */
	private TrainingProviderMonitoring trainingprovidermonitoring = null;
	private AuditorMonitorReview auditorMonitorReview;
	private Company selectedCompany;
	private TrainingProviderMonitoring trainingprovidermonitoringReport = null;
	private Doc doc;

	/* Service Level */
	private TrainingProviderMonitoringService service = new TrainingProviderMonitoringService();
	private AuditorMonitorReviewService auditorMonitorReviewService = new AuditorMonitorReviewService();
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	private DocService docService = new DocService();
	private SignoffService signoffService = new SignoffService();

	/* Lazy Data Models */
	private LazyDataModel<TrainingProviderMonitoring> dataModel;
	private LazyDataModel<TrainingProviderMonitoring> dataModelNoIssues;
	private LazyDataModel<TrainingProviderMonitoring> dataModelWithdrawn;
	private LazyDataModel<AuditorMonitorReview> auditorMonitorReviewDataModel;
	private LazyDataModel<Company> companies;
	private LazyDataModel<AuditorMonitorReview> auditorMonitorReviewEvidanceRequiredDataModelList;

	/* Array Lists */
	private List<TrainingProviderMonitoring> trainingprovidermonitoringList = null;
	private List<TrainingProviderMonitoring> trainingprovidermonitoringfilteredList = null;
	private List<AuditorMonitorReview> auditorMonitorReviewDataModelList;
	private List<Signoff> signOffLists = new ArrayList<>();

	/* Booleans */
	private boolean displayReportEvidance = false;
	private boolean displayReportNoEvidanceRequired = false;
	private boolean displayWithdrawn = false;

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
	 * Initialize method to get all TrainingProviderMonitoring and prepare a for
	 * a create of a new TrainingProviderMonitoring
	 * 
	 * @author TechFinium
	 * @see TrainingProviderMonitoring
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {

		prepareNew();
		trainingprovidermonitoringInfo();

	}

	public void auditormonitorreviewInfo() {
		try {
			auditorMonitorReviewDataModelList = auditorMonitorReviewService.findByMonitor(trainingprovidermonitoring);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void prepareSelectTPM() {

		System.out.println("ROW SELECTED !");
	}

	private void companyInfo() {
		companies = new LazyDataModel<Company>() {

			private static final long serialVersionUID = 1L;
			private List<Company> retorno = new ArrayList<Company>();

			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					filters.put("companyUserType", ConfigDocProcessEnum.TP);
					retorno = companyUsersService.allTrainingProviders(first, pageSize, sortField, sortOrder, filters);
					companies.setRowCount(companyUsersService.countTrainingProviders(filters));
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
	 * Get all TrainingProviderMonitoring for data table
	 * 
	 * @author TechFinium
	 * @see TrainingProviderMonitoring
	 */
	public void trainingprovidermonitoringInfo() {
		dataModel = new LazyDataModel<TrainingProviderMonitoring>() {
			private static final long serialVersionUID = 1L;
			private List<TrainingProviderMonitoring> retorno = new ArrayList<TrainingProviderMonitoring>();

			@Override
			public List<TrainingProviderMonitoring> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					filters.put("evidenceRequired", YesNoEnum.No);
					filters.put("status", ApprovalEnum.Withdrawn);
					filters.put("targetClass", TrainingProviderMonitoring.class.getName());
					retorno = service.allAuditsMonitoringByEvidanceAcceptableAndNotWithdrawn(first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.countAllAuditsMonitoringByEvidanceAcceptableAndNotWithdrawn(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(TrainingProviderMonitoring obj) {
				return obj.getId();
			}

			@Override
			public TrainingProviderMonitoring getRowData(String rowKey) {
				for (TrainingProviderMonitoring obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};

		dataModelNoIssues = new LazyDataModel<TrainingProviderMonitoring>() {
			private static final long serialVersionUID = 1L;
			private List<TrainingProviderMonitoring> retorno = new ArrayList<TrainingProviderMonitoring>();

			@Override
			public List<TrainingProviderMonitoring> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					filters.put("evidenceRequired", YesNoEnum.Yes);
					filters.put("status", ApprovalEnum.Withdrawn);
					filters.put("targetClass", TrainingProviderMonitoring.class.getName());
					retorno = service.allAuditsMonitoringByEvidanceAcceptableAndNotWithdrawn(first, pageSize, sortField, sortOrder, filters);
					dataModelNoIssues.setRowCount(service.countAllAuditsMonitoringByEvidanceAcceptableAndNotWithdrawn(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(TrainingProviderMonitoring obj) {
				return obj.getId();
			}

			@Override
			public TrainingProviderMonitoring getRowData(String rowKey) {
				for (TrainingProviderMonitoring obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

		dataModelWithdrawn = new LazyDataModel<TrainingProviderMonitoring>() {
			private static final long serialVersionUID = 1L;
			private List<TrainingProviderMonitoring> retorno = new ArrayList<TrainingProviderMonitoring>();

			@Override
			public List<TrainingProviderMonitoring> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					filters.put("status", ApprovalEnum.Withdrawn);
					retorno = service.allAuditsMonitoringByStatus(first, pageSize, sortField, sortOrder, filters);
					dataModelWithdrawn.setRowCount(service.countAllAuditsMonitoringByStatus(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(TrainingProviderMonitoring obj) {
				return obj.getId();
			}

			@Override
			public TrainingProviderMonitoring getRowData(String rowKey) {
				for (TrainingProviderMonitoring obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert TrainingProviderMonitoring into database
	 * 
	 * @author TechFinium
	 * @see TrainingProviderMonitoring
	 */
	public void trainingprovidermonitoringInsert() {
		try {
			service.submitCompanyLearners(trainingprovidermonitoring, getSessionUI().getActiveUser(), null,
					getSessionUI().getTask());
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			trainingprovidermonitoringInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void completeCompanyLearners() {
		try {
			if (getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.EditUpload
					|| getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.Upload)
				service.completeCompanyLearners(trainingprovidermonitoring, getSessionUI().getActiveUser(),
						getSessionUI().getTask(), auditorMonitorReviewDataModelList);
			if (getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.Edit)
				service.completeCompanyLearnersNoDoc(trainingprovidermonitoring, getSessionUI().getActiveUser(),
						getSessionUI().getTask(), auditorMonitorReviewDataModelList);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void approveCompanyLearners() {
		try {
			service.approveCompanyLearners(trainingprovidermonitoring, getSessionUI().getActiveUser(),
					getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void rejectCompanyLearners() {
		try {
			service.rejectCompanyLearners(trainingprovidermonitoring, getSessionUI().getActiveUser(),
					getSessionUI().getTask(), null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void finalApproveCompanyLearners() {
		try {
			service.finalApproveCompanyLearners(trainingprovidermonitoring, getSessionUI().getActiveUser(),
					getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void finalRejectCompanyLearners() {
		try {
			service.finalRejectCompanyLearners(trainingprovidermonitoring, getSessionUI().getActiveUser(),
					getSessionUI().getTask(), null);
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void storeFile(FileUploadEvent event) {
		try {
			if (doc != null) {
				doc.setData(event.getFile().getContents());
				doc.setOriginalFname(event.getFile().getFileName());
				doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
				doc.setUsers(getSessionUI().getActiveUser());

				if (doc.getId() != null)
					DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			} else {
				doc = new Doc();
				doc.setData(event.getFile().getContents());
				doc.setOriginalFname(event.getFile().getFileName());
				doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
				doc.setUsers(getSessionUI().getActiveUser());
				doc.setTargetClass(auditorMonitorReview.getClass().getName());
				doc.setTargetKey(auditorMonitorReview.getId());
				if (doc.getId() == null)
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(),
							getSessionUI().getActiveUser());
				this.auditormonitorreviewInfo();
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update TrainingProviderMonitoring in database
	 * 
	 * @author TechFinium
	 * @see TrainingProviderMonitoring
	 */
	public void trainingprovidermonitoringUpdate() {
		try {
			service.update(this.trainingprovidermonitoring);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			trainingprovidermonitoringInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete TrainingProviderMonitoring from database
	 * 
	 * @author TechFinium
	 * @see TrainingProviderMonitoring
	 */
	public void trainingprovidermonitoringDelete() {
		try {
			service.delete(this.trainingprovidermonitoring);
			prepareNew();
			trainingprovidermonitoringInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of TrainingProviderMonitoring
	 * 
	 * @author TechFinium
	 * @see TrainingProviderMonitoring
	 */
	public void prepareNew() {
		trainingprovidermonitoring = new TrainingProviderMonitoring();
		trainingprovidermonitoring.setCompany(selectedCompany);
		trainingprovidermonitoring.setUser(getSessionUI().getActiveUser());
		trainingprovidermonitoring.setStatus(ApprovalEnum.NA);
	}

	/*
	 * public List<SelectItem> getTrainingProviderMonitoringDD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * trainingprovidermonitoringInfo(); for (TrainingProviderMonitoring ug :
	 * trainingprovidermonitoringList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<TrainingProviderMonitoring> complete(String desc) {
		List<TrainingProviderMonitoring> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public void downloadForm() {
		try {
			service.downloadMonitorAuditReport(trainingprovidermonitoringReport);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void populateEvidanceRequiredReport() {
		try {
			displayReportEvidance = true;
			displayReportNoEvidanceRequired = false;
			displayWithdrawn = false;
			auditorMonitorReviewEvidanceRequiredDataModelListInfo();
			populateSignOffList();
			addInfoMessage("Information Populated");
		} catch (Exception e) {
			displayReportEvidance = false;
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void populateEvidanceNotRequiredReport() {
		try {
			displayReportEvidance = false;
			displayReportNoEvidanceRequired = true;
			displayWithdrawn = false;
			auditorMonitorReviewEvidanceRequiredDataModelListInfo();
			populateSignOffList();
			addInfoMessage("Information Populated");
		} catch (Exception e) {
			displayReportNoEvidanceRequired = false;
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void populateWithdrawnReport() {
		try {
			displayReportEvidance = false;
			displayReportNoEvidanceRequired = false;
			displayWithdrawn = true;
			auditorMonitorReviewEvidanceRequiredDataModelListInfo();
			populateSignOffList();
			addInfoMessage("Information Populated");
		} catch (Exception e) {
			displayWithdrawn = false;
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void populateSignOffList() throws Exception {
		signOffLists = new ArrayList<>();
		if (trainingprovidermonitoringReport != null && trainingprovidermonitoringReport.getId() != null) {
			signOffLists = signoffService.findByTargetKeyAndClass(trainingprovidermonitoringReport.getId(),
					trainingprovidermonitoringReport.getClass().getName());
		}
	}

	private void auditorMonitorReviewEvidanceRequiredDataModelListInfo() {
		auditorMonitorReviewEvidanceRequiredDataModelList = new LazyDataModel<AuditorMonitorReview>() {
			private static final long serialVersionUID = 1L;
			private List<AuditorMonitorReview> retorno = new ArrayList<AuditorMonitorReview>();

			@Override
			public List<AuditorMonitorReview> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					filters.put("targetClass", trainingprovidermonitoringReport.getClass().getName());
					filters.put("targetKey", trainingprovidermonitoringReport.getId());
					retorno = auditorMonitorReviewService.allAuditorMonitorReviewByTargetClassAndKey(first, pageSize,
							sortField, sortOrder, filters);
					auditorMonitorReviewEvidanceRequiredDataModelList.setRowCount(
							auditorMonitorReviewService.countAllAuditorMonitorReviewByTargetClassAndKey(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(AuditorMonitorReview obj) {
				return obj.getId();
			}

			@Override
			public AuditorMonitorReview getRowData(String rowKey) {
				for (AuditorMonitorReview obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	public List<TrainingProviderMonitoring> getTrainingProviderMonitoringList() {
		return trainingprovidermonitoringList;
	}

	public void setTrainingProviderMonitoringList(List<TrainingProviderMonitoring> trainingprovidermonitoringList) {
		this.trainingprovidermonitoringList = trainingprovidermonitoringList;
	}

	public TrainingProviderMonitoring getTrainingprovidermonitoring() {
		return trainingprovidermonitoring;
	}

	public void setTrainingprovidermonitoring(TrainingProviderMonitoring trainingprovidermonitoring) {
		this.trainingprovidermonitoring = trainingprovidermonitoring;
	}

	public List<TrainingProviderMonitoring> getTrainingProviderMonitoringfilteredList() {
		return trainingprovidermonitoringfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param trainingprovidermonitoringfilteredList
	 *            the new trainingprovidermonitoringfilteredList list
	 * @see TrainingProviderMonitoring
	 */
	public void setTrainingProviderMonitoringfilteredList(
			List<TrainingProviderMonitoring> trainingprovidermonitoringfilteredList) {
		this.trainingprovidermonitoringfilteredList = trainingprovidermonitoringfilteredList;
	}

	public LazyDataModel<TrainingProviderMonitoring> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<TrainingProviderMonitoring> dataModel) {
		this.dataModel = dataModel;
	}

	public LazyDataModel<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(LazyDataModel<Company> companies) {
		this.companies = companies;
	}

	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public LazyDataModel<AuditorMonitorReview> getAuditorMonitorReviewDataModel() {
		return auditorMonitorReviewDataModel;
	}

	public void setAuditorMonitorReviewDataModel(LazyDataModel<AuditorMonitorReview> auditorMonitorReviewDataModel) {
		this.auditorMonitorReviewDataModel = auditorMonitorReviewDataModel;
	}

	public List<AuditorMonitorReview> getAuditorMonitorReviewDataModelList() {
		return auditorMonitorReviewDataModelList;
	}

	public void setAuditorMonitorReviewDataModelList(List<AuditorMonitorReview> auditorMonitorReviewDataModelList) {
		this.auditorMonitorReviewDataModelList = auditorMonitorReviewDataModelList;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public AuditorMonitorReview getAuditorMonitorReview() {
		return auditorMonitorReview;
	}

	public void setAuditorMonitorReview(AuditorMonitorReview auditorMonitorReview) {
		this.auditorMonitorReview = auditorMonitorReview;
	}

	/**
	 * @return the dataModelNoIssues
	 */
	public LazyDataModel<TrainingProviderMonitoring> getDataModelNoIssues() {
		return dataModelNoIssues;
	}

	/**
	 * @param dataModelNoIssues
	 *            the dataModelNoIssues to set
	 */
	public void setDataModelNoIssues(LazyDataModel<TrainingProviderMonitoring> dataModelNoIssues) {
		this.dataModelNoIssues = dataModelNoIssues;
	}

	public boolean isDisplayReportEvidance() {
		return displayReportEvidance;
	}

	public void setDisplayReportEvidance(boolean displayReportEvidance) {
		this.displayReportEvidance = displayReportEvidance;
	}

	public boolean isDisplayReportNoEvidanceRequired() {
		return displayReportNoEvidanceRequired;
	}

	public void setDisplayReportNoEvidanceRequired(boolean displayReportNoEvidanceRequired) {
		this.displayReportNoEvidanceRequired = displayReportNoEvidanceRequired;
	}

	public LazyDataModel<AuditorMonitorReview> getAuditorMonitorReviewEvidanceRequiredDataModelList() {
		return auditorMonitorReviewEvidanceRequiredDataModelList;
	}

	public void setAuditorMonitorReviewEvidanceRequiredDataModelList(
			LazyDataModel<AuditorMonitorReview> auditorMonitorReviewEvidanceRequiredDataModelList) {
		this.auditorMonitorReviewEvidanceRequiredDataModelList = auditorMonitorReviewEvidanceRequiredDataModelList;
	}

	public TrainingProviderMonitoring getTrainingprovidermonitoringReport() {
		return trainingprovidermonitoringReport;
	}

	public void setTrainingprovidermonitoringReport(TrainingProviderMonitoring trainingprovidermonitoringReport) {
		this.trainingprovidermonitoringReport = trainingprovidermonitoringReport;
	}

	public LazyDataModel<TrainingProviderMonitoring> getDataModelWithdrawn() {
		return dataModelWithdrawn;
	}

	public void setDataModelWithdrawn(LazyDataModel<TrainingProviderMonitoring> dataModelWithdrawn) {
		this.dataModelWithdrawn = dataModelWithdrawn;
	}

	public boolean isDisplayWithdrawn() {
		return displayWithdrawn;
	}

	public void setDisplayWithdrawn(boolean displayWithdrawn) {
		this.displayWithdrawn = displayWithdrawn;
	}

	public List<Signoff> getSignOffLists() {
		return signOffLists;
	}

	public void setSignOffLists(List<Signoff> signOffLists) {
		this.signOffLists = signOffLists;
	}

}
