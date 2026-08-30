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

import haj.com.constants.HAJConstants;
import haj.com.entity.CompanyLearnersTradeTest;
import haj.com.entity.CompanyTradeTestEmployer;
import haj.com.entity.DetailsOfExperienceArpl;
import haj.com.entity.DetailsOfTrainingArpl;
import haj.com.entity.Doc;
import haj.com.entity.NambDecisionHistory;
import haj.com.entity.Signoff;
import haj.com.entity.TradeTestTaskResult;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.TradeTestTypeEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyLearnersTradeTestService;
import haj.com.service.CompanyTradeTestEmployerService;
import haj.com.service.DetailsOfExperienceArplService;
import haj.com.service.DetailsOfTrainingArplService;
import haj.com.service.DocService;
import haj.com.service.NambDecisionHistoryService;
import haj.com.service.SignoffService;
import haj.com.service.TasksService;
import haj.com.service.TradeTestTaskResultService;
import haj.com.service.lookup.RejectReasonsService;

@ManagedBean(name = "tradeTestNambApprovalUI")
@ViewScoped
public class TradeTestNambApprovalUI extends AbstractUI {
	
	/* Entity Levels */
	private CompanyLearnersTradeTest companyLearnersTradeTest = null;
	private CompanyTradeTestEmployer companyTradeTestEmployer = null;
	private Doc doc;

	/* Service Levels */
	private CompanyLearnersTradeTestService companyLearnersTradeTestService = new CompanyLearnersTradeTestService();
	private DetailsOfExperienceArplService detailsOfExperienceArplService = new DetailsOfExperienceArplService();
	private DetailsOfTrainingArplService detailsOfTrainingArplService = new DetailsOfTrainingArplService();
	private CompanyTradeTestEmployerService companyTradeTestEmployerService = new CompanyTradeTestEmployerService();
	private TradeTestTaskResultService tradeTestTaskResultService = new TradeTestTaskResultService();
	private SignoffService signoffService = new SignoffService();
	private RejectReasonsService rejectReasonsService = new RejectReasonsService();

	/* Lazy Data Models */
	private LazyDataModel<DetailsOfExperienceArpl> dataModelDetailsOfExperienceArpl;
	private LazyDataModel<DetailsOfTrainingArpl> dataModelDetailsOfTrainingArpl;
	private LazyDataModel<TradeTestTaskResult> dataModelTradeTestTaskResult;
	
	/* Array Lists */
	private List<Signoff> signOffLists = new ArrayList<>();
	private List<RejectReasons> rejectionReasons = new ArrayList<>();
	
	/* Vars */
	private final String TELPHONE_FORMAT = HAJConstants.TELPHONE_FORMAT;
	private final String CELLPHONE_FORMAT = HAJConstants.CELLPHONE_FORMAT;
	private final String FAX_NUMBER_FORMAT = HAJConstants.FAX_NUMBER_FORMAT;
	private String setmisValidiationError = "";
	
	private boolean canEdit = false;
	private boolean canUpload = false;

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
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.TradeTestNambApproval) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			if (!TasksService.instance().taskOpenBasedOnStatus(getSessionUI().getTask().getTaskStatus())) {
				ajaxRedirectToDashboard();
			} else {
				populateServiceLevels();
				determainUserActions();
				companyLearnersTradeTest = companyLearnersTradeTestService.findByKey(getSessionUI().getTask().getTargetKey());
				companyLearnersTradeTestService.populateDocsByProcessForArplNambApproval(companyLearnersTradeTest);
				if (companyLearnersTradeTest != null && companyLearnersTradeTest.getEmployer() != null && companyLearnersTradeTest.getEmployer().getId() != null) {
					companyTradeTestEmployer = companyTradeTestEmployerService.findByKey(companyLearnersTradeTest.getEmployer().getId());
				}
				signOffLists = signoffService.findByTargetKeyAndClassAndLastest(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), true);
				dataModelDetailsOfExperienceArplInfo();
				dataModelDetailsOfTrainingArplInfo();
				dataModelTradeTestTaskResultInfo();
				populateRejectionReasons();
			}
		} else {
			ajaxRedirectToDashboard();
		}
	}
	
	private void populateServiceLevels() {
	}
	
	public void determainUserActions() throws Exception {
		canEdit = TasksService.instance().canEditBasedOnRolePermission(getSessionUI().getTask().getProcessRole().getRolePermission());
		canUpload = TasksService.instance().canUploadBasedOnRolePermission(getSessionUI().getTask().getProcessRole().getRolePermission());
	}

	private void populateRejectionReasons() throws Exception{
		rejectionReasons = rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), ConfigDocProcessEnum.TradeTestNambApproval);
	}
	
	public void dataModelDetailsOfExperienceArplInfo() {
		dataModelDetailsOfExperienceArpl = new LazyDataModel<DetailsOfExperienceArpl>() {
			private static final long serialVersionUID = 1L;
			private List<DetailsOfExperienceArpl> retorno = new ArrayList<DetailsOfExperienceArpl>();
			@Override
			public List<DetailsOfExperienceArpl> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = detailsOfExperienceArplService.allDetailsOfExperienceArplByTradeTestId(first, pageSize, sortField, sortOrder, filters, companyLearnersTradeTest);
					dataModelDetailsOfExperienceArpl.setRowCount(detailsOfExperienceArplService.countAllDetailsOfExperienceArplByTradeTestId(filters));
				} catch (Exception e) {
					logger.fatal(e.getMessage(), e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(DetailsOfExperienceArpl obj) {
				return obj.getId();
			}
			@Override
			public DetailsOfExperienceArpl getRowData(String rowKey) {
				for (DetailsOfExperienceArpl obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void dataModelDetailsOfTrainingArplInfo() {
		dataModelDetailsOfTrainingArpl = new LazyDataModel<DetailsOfTrainingArpl>() {
			private static final long serialVersionUID = 1L;
			private List<DetailsOfTrainingArpl> retorno = new ArrayList<DetailsOfTrainingArpl>();
			@Override
			public List<DetailsOfTrainingArpl> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = detailsOfTrainingArplService.allDetailsOfTrainingArplByTradeTestId(first, pageSize, sortField, sortOrder, filters, companyLearnersTradeTest);
					dataModelDetailsOfTrainingArpl.setRowCount(detailsOfTrainingArplService.countAllDetailsOfTrainingArplByTradeTestId(filters));
				} catch (Exception e) {
					logger.fatal(e.getMessage(), e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(DetailsOfTrainingArpl obj) {
				return obj.getId();
			}

			@Override
			public DetailsOfTrainingArpl getRowData(String rowKey) {
				for (DetailsOfTrainingArpl obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void dataModelTradeTestTaskResultInfo() {
		dataModelTradeTestTaskResult = new LazyDataModel<TradeTestTaskResult>() {
			private static final long serialVersionUID = 1L;
			private List<TradeTestTaskResult> retorno = new ArrayList<TradeTestTaskResult>();

			@Override
			public List<TradeTestTaskResult> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = tradeTestTaskResultService.allTradeTestTaskResultByTradeTestId(first, pageSize, sortField, sortOrder, filters, companyLearnersTradeTest );
					dataModelTradeTestTaskResult.setRowCount(tradeTestTaskResultService.countAllTradeTestTaskResultByTradeTestId(filters));
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(TradeTestTaskResult obj) {
				return obj.getId();
			}

			@Override
			public TradeTestTaskResult getRowData(String rowKey) {
				for (TradeTestTaskResult obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void storeFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(getSessionUI().getActiveUser());
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			} else {
				doc.setTargetKey(companyLearnersTradeTest.getId());
				doc.setTargetClass(CompanyLearnersTradeTest.class.getName());
				if (doc.getId() == null)
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void completeWorkflow(){
		try {
//			companyLearnersTradeTestService.completeDocUploadTask(companyLearnersTradeTest, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void completeTaskUpload(){
		try {
			companyLearnersTradeTestService.completeTaskTradeTestNambApproval(companyLearnersTradeTest, getSessionUI().getActiveUser(), getSessionUI().getTask(), canUpload, canEdit);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void completeTaskEdit(){
		try {
			if (getSessionUI().getTask().getFirstInProcess() != null && getSessionUI().getTask().getFirstInProcess()) {
				setmisValidiationError = "";
				StringBuilder errors = companyLearnersTradeTestService.validiateTradeTestInformation(companyLearnersTradeTest);
				if (errors.toString().isEmpty()) {
					companyLearnersTradeTestService.completeTaskTradeTestNambApproval(companyLearnersTradeTest, getSessionUI().getActiveUser(), getSessionUI().getTask(), canUpload, canEdit);
					ajaxRedirectToDashboard();
				} else{
					addErrorMessage("Validiation Error. Please Refer to error message.");
					setmisValidiationError = errors.toString();
				}
			} else {
				companyLearnersTradeTestService.completeTaskTradeTestNambApproval(companyLearnersTradeTest, getSessionUI().getActiveUser(), getSessionUI().getTask(), canUpload, canEdit);
				ajaxRedirectToDashboard();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void completeTaskFinalEditAndUpload(){
		try {
			companyLearnersTradeTestService.finalApproveCertificateCollection(companyLearnersTradeTest, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/* Getters and Setters */
	public boolean isCanEdit() {
		return canEdit;
	}

	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}

	public boolean isCanUpload() {
		return canUpload;
	}

	public void setCanUpload(boolean canUpload) {
		this.canUpload = canUpload;
	}

	public CompanyLearnersTradeTest getCompanyLearnersTradeTest() {
		return companyLearnersTradeTest;
	}

	public void setCompanyLearnersTradeTest(CompanyLearnersTradeTest companyLearnersTradeTest) {
		this.companyLearnersTradeTest = companyLearnersTradeTest;
	}

	public CompanyTradeTestEmployer getCompanyTradeTestEmployer() {
		return companyTradeTestEmployer;
	}

	public void setCompanyTradeTestEmployer(CompanyTradeTestEmployer companyTradeTestEmployer) {
		this.companyTradeTestEmployer = companyTradeTestEmployer;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public LazyDataModel<DetailsOfExperienceArpl> getDataModelDetailsOfExperienceArpl() {
		return dataModelDetailsOfExperienceArpl;
	}

	public void setDataModelDetailsOfExperienceArpl(
			LazyDataModel<DetailsOfExperienceArpl> dataModelDetailsOfExperienceArpl) {
		this.dataModelDetailsOfExperienceArpl = dataModelDetailsOfExperienceArpl;
	}

	public LazyDataModel<DetailsOfTrainingArpl> getDataModelDetailsOfTrainingArpl() {
		return dataModelDetailsOfTrainingArpl;
	}

	public void setDataModelDetailsOfTrainingArpl(LazyDataModel<DetailsOfTrainingArpl> dataModelDetailsOfTrainingArpl) {
		this.dataModelDetailsOfTrainingArpl = dataModelDetailsOfTrainingArpl;
	}

	public LazyDataModel<TradeTestTaskResult> getDataModelTradeTestTaskResult() {
		return dataModelTradeTestTaskResult;
	}

	public void setDataModelTradeTestTaskResult(LazyDataModel<TradeTestTaskResult> dataModelTradeTestTaskResult) {
		this.dataModelTradeTestTaskResult = dataModelTradeTestTaskResult;
	}

	public List<Signoff> getSignOffLists() {
		return signOffLists;
	}

	public void setSignOffLists(List<Signoff> signOffLists) {
		this.signOffLists = signOffLists;
	}

	public List<RejectReasons> getRejectionReasons() {
		return rejectionReasons;
	}

	public void setRejectionReasons(List<RejectReasons> rejectionReasons) {
		this.rejectionReasons = rejectionReasons;
	}

	public String getTELPHONE_FORMAT() {
		return TELPHONE_FORMAT;
	}

	public String getCELLPHONE_FORMAT() {
		return CELLPHONE_FORMAT;
	}

	public String getFAX_NUMBER_FORMAT() {
		return FAX_NUMBER_FORMAT;
	}

	public String getSetmisValidiationError() {
		return setmisValidiationError;
	}

	public void setSetmisValidiationError(String setmisValidiationError) {
		this.setmisValidiationError = setmisValidiationError;
	}

	
}
