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
import haj.com.entity.enums.AprlProgressEnum;
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
import haj.com.service.TradeTestTaskResultService;
import haj.com.service.lookup.RejectReasonsService;

@ManagedBean(name = "arplOrgDocHoldingAreaUI")
@ViewScoped
public class ArplOrgDocHoldingAreaUI extends AbstractUI {
	
	/* Entity Levels */
	private CompanyLearnersTradeTest companyLearnersTradeTest = null;
	private CompanyLearnersTradeTest viewCompanyLearnersTradeTest = null;
	private CompanyTradeTestEmployer companyTradeTestEmployer = null;
	private Doc doc;

	/* Service Levels */
	private CompanyLearnersTradeTestService companyLearnersTradeTestService = new CompanyLearnersTradeTestService();
	private DetailsOfExperienceArplService detailsOfExperienceArplService = new DetailsOfExperienceArplService();
	private DetailsOfTrainingArplService detailsOfTrainingArplService = new DetailsOfTrainingArplService();
	private CompanyTradeTestEmployerService companyTradeTestEmployerService = new CompanyTradeTestEmployerService();
	private TradeTestTaskResultService tradeTestTaskResultService = new TradeTestTaskResultService();
	private SignoffService signoffService = new SignoffService();
	private NambDecisionHistoryService nambDecisionHistoryService = new NambDecisionHistoryService();

	/* Lazy Data Models */
	private LazyDataModel<CompanyLearnersTradeTest> companyLearnersTradeTestDataModel;
	private LazyDataModel<DetailsOfExperienceArpl> dataModelDetailsOfExperienceArpl;
	private LazyDataModel<DetailsOfTrainingArpl> dataModelDetailsOfTrainingArpl;
	private LazyDataModel<TradeTestTaskResult> dataModelTradeTestTaskResult;
	private LazyDataModel<NambDecisionHistory> dataModelNambDecisionHistory;
	
	/* Array Lists */
	private List<CompanyLearnersTradeTest> companyLearnersTradeTestList;
	private List<Doc> supportingDocs;
	private List<Signoff> signOffLists = new ArrayList<>();
	private List<RejectReasons> selectedRejectionReasons = new ArrayList<>();
	private List<RejectReasons> rejectionReasonsSelection = new ArrayList<>();
	
	/* Vars */
	private int openCount = 0;
	private int avalibleTradeTestCenters = 0;
	private int rejectionNumber = 0;
	
	private Boolean checkRegionBool;
	private Boolean check;
	private Boolean updateDate = false;
	
	private boolean validationFailed = false;
	private boolean displayTradeTestCenter = false;
	private boolean displayLastInfo = false;
	private boolean actionArpl = false;
	
	private String validationErrors = "";
	private final String TELPHONE_FORMAT = HAJConstants.TELPHONE_FORMAT;
	private final String CELLPHONE_FORMAT = HAJConstants.CELLPHONE_FORMAT;
	private final String FAX_NUMBER_FORMAT = HAJConstants.FAX_NUMBER_FORMAT;
	
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
		populateServiceLevels();
		companyLearnersTradeTestDataModelInfo();
		check = false;
	}

	private void populateServiceLevels() {
	}

	public void companyLearnersTradeTestDataModelInfo() {
		companyLearnersTradeTestDataModel = new LazyDataModel<CompanyLearnersTradeTest>() {
			private static final long serialVersionUID = 1L;
			private List<CompanyLearnersTradeTest> retorno = new ArrayList<CompanyLearnersTradeTest>();
			@Override
			public List<CompanyLearnersTradeTest> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = companyLearnersTradeTestService.allCompanyLearnersTradeTestByWithTypeAndOnHold(first, pageSize, sortField, sortOrder, filters, TradeTestTypeEnum.ARPL, AprlProgressEnum.WithMersetaTwo, true);
					companyLearnersTradeTestDataModel.setRowCount(companyLearnersTradeTestService.countAllCompanyLearnersTradeTestByWithTypeAndOnHold(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(CompanyLearnersTradeTest obj) {
				return obj.getId();
			}
			@Override
			public CompanyLearnersTradeTest getRowData(String rowKey) {
				for (CompanyLearnersTradeTest obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void testSendingToAdmin(){
		try {
			companyLearnersTradeTestService.sendArplTradeTestToAdmin(companyLearnersTradeTest, getSessionUI().getActiveUser());
			companyLearnersTradeTest = null;
			companyLearnersTradeTestDataModelInfo();
			addInfoMessage("Action Completed");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepUpload(){
		try {
			prepNewDoc();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepNewDoc() throws Exception{
		doc = new Doc();
	}
	
	/**
	 * Store new file
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
			}
			// add support doc
			if (supportingDocs == null) {
				supportingDocs = new ArrayList<>();
			}
			supportingDocs.add(doc);
			runClientSideExecute("PF('uploadSupportingDocDlg').hide()");
			addInfoMessage("Documents Uploaded, Awaiting Submission");
			prepNewDoc();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void removeSupportingDoc(){
		try {
			if (supportingDocs != null && supportingDocs.size() != 0) {
				supportingDocs.remove(doc);
				addWarningMessage("Action Complete");
				prepNewDoc();
			} else {
				addWarningMessage("Action Complete");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void sendToAssignedClientAdminToProcesses(){
		try {
			updateDate = false;
			actionArpl = false;
			companyLearnersTradeTestService.reopenFromHoldingAreaWithMerSETAThree(companyLearnersTradeTest, getSessionUI().getActiveUser());
			addInfoMessage("Task Sent To Assigned Region Client Services Administrator.");
			companyLearnersTradeTestDataModelInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepUpdateOfReviewDate(){
		try {
			updateDate = true;
			actionArpl = true;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void cancelUpdateOfReviewDate(){
		try {
			updateDate = false;
			actionArpl = false;
			companyLearnersTradeTest = null;
			viewCompanyLearnersTradeTest = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateReviewDate(){
		try {
			companyLearnersTradeTestService.update(companyLearnersTradeTest);
			companyLearnersTradeTest = null;
			viewCompanyLearnersTradeTest = null;
			updateDate = false;
			actionArpl = false;
			addInfoMessage("Date Updated");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/* ARPL VIEW START */
	public void prepViewArpl(){
		try {
			updateDate = false;
			actionArpl = false;
			companyLearnersTradeTest = null;
			viewCompanyLearnersTradeTest =  companyLearnersTradeTestService.populateDocsByTargetClassAndKey(companyLearnersTradeTestService.findByKey(viewCompanyLearnersTradeTest.getId()));
			dataModelDetailsOfExperienceArplInfo();
			dataModelDetailsOfTrainingArplInfo();
			if (viewCompanyLearnersTradeTest.getEmployer() != null && viewCompanyLearnersTradeTest.getEmployer().getId() != null) {
				companyTradeTestEmployer = companyTradeTestEmployerService.findByKey(viewCompanyLearnersTradeTest.getEmployer().getId());
			}
			populateSignOffs();
			dataModelTradeTestTaskResultInfo();
			dataModelNambDecisionHistoryInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void populateSignOffs() throws Exception{
		if (viewCompanyLearnersTradeTest != null && viewCompanyLearnersTradeTest.getId() != null) {
			signOffLists = signoffService.findByTargetKeyAndClassAndLastest(viewCompanyLearnersTradeTest.getId(), viewCompanyLearnersTradeTest.getClass().getName(), true);
		}
	}
	
	public void dataModelDetailsOfExperienceArplInfo() {
		dataModelDetailsOfExperienceArpl = new LazyDataModel<DetailsOfExperienceArpl>() {
			private static final long serialVersionUID = 1L;
			private List<DetailsOfExperienceArpl> retorno = new ArrayList<DetailsOfExperienceArpl>();
			@Override
			public List<DetailsOfExperienceArpl> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = detailsOfExperienceArplService.allDetailsOfExperienceArplByTradeTestId(first, pageSize, sortField, sortOrder, filters, viewCompanyLearnersTradeTest);
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
					retorno = detailsOfTrainingArplService.allDetailsOfTrainingArplByTradeTestId(first, pageSize, sortField, sortOrder, filters, viewCompanyLearnersTradeTest);
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
					retorno = tradeTestTaskResultService.allTradeTestTaskResultByTradeTestId(first, pageSize, sortField, sortOrder, filters, viewCompanyLearnersTradeTest);
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
	
	public void dataModelNambDecisionHistoryInfo() {
		dataModelNambDecisionHistory = new LazyDataModel<NambDecisionHistory>() {
			private static final long serialVersionUID = 1L;
			private List<NambDecisionHistory> retorno = new ArrayList<NambDecisionHistory>();
			@Override
			public List<NambDecisionHistory> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = nambDecisionHistoryService.allNambDecisionHistoryByTargetClassAndKey(first, pageSize, sortField, sortOrder, filters, viewCompanyLearnersTradeTest.getId(), viewCompanyLearnersTradeTest.getClass().getName());
					dataModelNambDecisionHistory.setRowCount(nambDecisionHistoryService.countAllNambDecisionHistoryByTargetClassAndKey(filters));
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(NambDecisionHistory obj) {
				return obj.getId();
			}

			@Override
			public NambDecisionHistory getRowData(String rowKey) {
				for (NambDecisionHistory obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void closeViewArpl(){
		try {
			actionArpl = false;
			viewCompanyLearnersTradeTest = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	/* ARPL VIEW END */

	/* Getters and setetrs */
	public CompanyLearnersTradeTest getCompanyLearnersTradeTest() {
		return companyLearnersTradeTest;
	}

	public void setCompanyLearnersTradeTest(CompanyLearnersTradeTest companyLearnersTradeTest) {
		this.companyLearnersTradeTest = companyLearnersTradeTest;
	}

	public CompanyLearnersTradeTestService getCompanyLearnersTradeTestService() {
		return companyLearnersTradeTestService;
	}

	public void setCompanyLearnersTradeTestService(CompanyLearnersTradeTestService companyLearnersTradeTestService) {
		this.companyLearnersTradeTestService = companyLearnersTradeTestService;
	}

	public LazyDataModel<CompanyLearnersTradeTest> getCompanyLearnersTradeTestDataModel() {
		return companyLearnersTradeTestDataModel;
	}

	public void setCompanyLearnersTradeTestDataModel(
			LazyDataModel<CompanyLearnersTradeTest> companyLearnersTradeTestDataModel) {
		this.companyLearnersTradeTestDataModel = companyLearnersTradeTestDataModel;
	}

	public int getOpenCount() {
		return openCount;
	}

	public void setOpenCount(int openCount) {
		this.openCount = openCount;
	}

	public CompanyLearnersTradeTest getViewCompanyLearnersTradeTest() {
		return viewCompanyLearnersTradeTest;
	}

	public void setViewCompanyLearnersTradeTest(CompanyLearnersTradeTest viewCompanyLearnersTradeTest) {
		this.viewCompanyLearnersTradeTest = viewCompanyLearnersTradeTest;
	}

	public Boolean getCheckRegionBool() {
		return checkRegionBool;
	}

	public void setCheckRegionBool(Boolean checkRegionBool) {
		this.checkRegionBool = checkRegionBool;
	}

	public List<CompanyLearnersTradeTest> getCompanyLearnersTradeTestList() {
		return companyLearnersTradeTestList;
	}

	public void setCompanyLearnersTradeTestList(List<CompanyLearnersTradeTest> companyLearnersTradeTestList) {
		this.companyLearnersTradeTestList = companyLearnersTradeTestList;
	}

	public Boolean getCheck() {
		return check;
	}

	public void setCheck(Boolean check) {
		this.check = check;
	}

	public boolean isDisplayTradeTestCenter() {
		return displayTradeTestCenter;
	}

	public void setDisplayTradeTestCenter(boolean displayTradeTestCenter) {
		this.displayTradeTestCenter = displayTradeTestCenter;
	}

	public boolean isDisplayLastInfo() {
		return displayLastInfo;
	}

	public void setDisplayLastInfo(boolean displayLastInfo) {
		this.displayLastInfo = displayLastInfo;
	}

	public int getAvalibleTradeTestCenters() {
		return avalibleTradeTestCenters;
	}

	public void setAvalibleTradeTestCenters(int avalibleTradeTestCenters) {
		this.avalibleTradeTestCenters = avalibleTradeTestCenters;
	}

	public boolean isValidationFailed() {
		return validationFailed;
	}

	public void setValidationFailed(boolean validationFailed) {
		this.validationFailed = validationFailed;
	}

	public String getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(String validationErrors) {
		this.validationErrors = validationErrors;
	}

	public boolean isActionArpl() {
		return actionArpl;
	}

	public void setActionArpl(boolean actionArpl) {
		this.actionArpl = actionArpl;
	}

	public List<Doc> getSupportingDocs() {
		return supportingDocs;
	}

	public void setSupportingDocs(List<Doc> supportingDocs) {
		this.supportingDocs = supportingDocs;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
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

	public CompanyTradeTestEmployer getCompanyTradeTestEmployer() {
		return companyTradeTestEmployer;
	}

	public void setCompanyTradeTestEmployer(CompanyTradeTestEmployer companyTradeTestEmployer) {
		this.companyTradeTestEmployer = companyTradeTestEmployer;
	}

	public List<Signoff> getSignOffLists() {
		return signOffLists;
	}

	public void setSignOffLists(List<Signoff> signOffLists) {
		this.signOffLists = signOffLists;
	}

	public LazyDataModel<NambDecisionHistory> getDataModelNambDecisionHistory() {
		return dataModelNambDecisionHistory;
	}

	public void setDataModelNambDecisionHistory(LazyDataModel<NambDecisionHistory> dataModelNambDecisionHistory) {
		this.dataModelNambDecisionHistory = dataModelNambDecisionHistory;
	}

	public List<RejectReasons> getSelectedRejectionReasons() {
		return selectedRejectionReasons;
	}

	public void setSelectedRejectionReasons(List<RejectReasons> selectedRejectionReasons) {
		this.selectedRejectionReasons = selectedRejectionReasons;
	}

	public List<RejectReasons> getRejectionReasonsSelection() {
		return rejectionReasonsSelection;
	}

	public void setRejectionReasonsSelection(List<RejectReasons> rejectionReasonsSelection) {
		this.rejectionReasonsSelection = rejectionReasonsSelection;
	}

	public int getRejectionNumber() {
		return rejectionNumber;
	}

	public void setRejectionNumber(int rejectionNumber) {
		this.rejectionNumber = rejectionNumber;
	}

	public Boolean getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Boolean updateDate) {
		this.updateDate = updateDate;
	}


}
