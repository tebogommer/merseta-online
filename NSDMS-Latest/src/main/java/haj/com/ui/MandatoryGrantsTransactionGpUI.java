package haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.bean.MgTransactionsBean;
import haj.com.constants.HAJConstants;
import haj.com.entity.Blank;
import haj.com.entity.GpGrantBatchEntry;
import haj.com.entity.GpGrantBatchList;
import haj.com.entity.Tasks;
import haj.com.entity.enums.ReportPropertiesEnum;
import haj.com.entity.enums.WspTypeEnum;
import haj.com.entity.lookup.ReportGenerationProperties;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.sars.SarsLevyDetails;
import haj.com.service.GpGrantBatchEntryService;
import haj.com.service.GpGrantBatchListService;
import haj.com.service.SarsFilesService;
import haj.com.service.TasksService;
import haj.com.service.lookup.ReportGenerationPropertiesService;

@ManagedBean(name = "mandatoryGrantsTransactionGpUI")
@ViewScoped
public class MandatoryGrantsTransactionGpUI extends AbstractUI {

	/* Entity Levels */
	private GpGrantBatchList gpGrantBatchList = null;
	private GpGrantBatchEntry gpGrantBatchEntry = null;
	private MgTransactionsBean totalAvalibleEntries = null;
	private GpGrantBatchList gpGrantBatchListTaskView = null;

	/* Service Levels */
	private SarsFilesService sarsFilesService = new SarsFilesService();
	private GpGrantBatchListService gpGrantBatchListService = new GpGrantBatchListService();
	private GpGrantBatchEntryService gpGrantBatchEntryService = new GpGrantBatchEntryService();
	private ReportGenerationPropertiesService reportGenerationPropertiesService = new ReportGenerationPropertiesService();
	private TasksService tasksService = null;

	/* Array Lists */
	private List<SarsLevyDetails> sarsLevyDetails = new ArrayList<>();
	private LazyDataModel<Tasks> tasksDataModel;
	
	/* Vars */
	private Boolean includeSchemeYearFilter = false;
	private Integer schemeYearEntered;
	private Integer resultsSchemeYearSelected;
	private StringBuilder tasksTargetClass;
	private Long tasksTargetKey = null;
	
	/* Data Models */
	private LazyDataModel<GpGrantBatchList> dataModelGpGrantBatchList;
	private LazyDataModel<GpGrantBatchEntry> dataModelGpGrantBatchEntry;
	
	/* Dates */
	private Integer numberOfResults;
	private Date fromDate = null;
	private Date toDate = null;
	private Date resultsFromDate = null;
	private Date resultsToDate = null;
	private Date maxDate = null;
	
	private final String YEAR_FORMAT = HAJConstants.YEAR_FORMAT;

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
	 * @throws Exception
	 *             the exception
	 * @see Blank
	 */
	private void runInit() throws Exception {
		dataModelGpGrantBatchListInfo();
	}

	private void dataModelGpGrantBatchListInfo() throws Exception {
		dataModelGpGrantBatchList = new LazyDataModel<GpGrantBatchList>() {
			private static final long serialVersionUID = 1L;
			private List<GpGrantBatchList> retorno = new ArrayList<GpGrantBatchList>();
			@Override
			public List<GpGrantBatchList> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = gpGrantBatchListService.allGpGrantBatchListByWspTypeNoResolveData(first, pageSize, sortField, sortOrder, filters, WspTypeEnum.Mandatory);
					dataModelGpGrantBatchList.setRowCount(gpGrantBatchListService.countAllGpGrantBatchListByWspTypeNoResolveData(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(GpGrantBatchList obj) {
				return obj.getId();
			}
			@Override
			public GpGrantBatchList getRowData(String rowKey) {
				for (GpGrantBatchList obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void calculateMaxDateForFinYear() {
		try {
			// calc to go here
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Generates the results for the report
	 */
	public void generateResultsByDate() {
		try {
//			if (fromDate != null && toDate != null) {
				if (includeSchemeYearFilter) {
					if (schemeYearEntered == null) {
						throw new Exception("Provide: SARS DHET Scheme Year For Filter or de-select scheme year filter.");
					} else {
//						resultsFromDate = GenericUtility.getStartOfDay(fromDate);
//						resultsToDate = GenericUtility.getStartOfDay(toDate);
						resultsSchemeYearSelected = schemeYearEntered;
						// version 1
//						sarsLevyDetails = sarsFilesService.sarsLevyDetailBetweenDatesProvidedWspApprovedNotInBatchForMGTransferSchemeYearAndCanProcessForSarsFile(resultsFromDate, resultsToDate, resultsSchemeYearSelected);
						// version 2
//						sarsLevyDetails = sarsFilesService.sarsLevyDetailBetweenDatesProvidedWspApprovedNotInBatchForMGTransferSchemeYearAndCanProcessForSarsFileWithCheck(resultsFromDate, resultsToDate, resultsSchemeYearSelected);
						// version 3 with no date and filter on mandatory grant level
//						sarsLevyDetails = sarsFilesService.sarsLevyDetailProvidedWspApprovedNotInBatchForMGTransferSchemeYearAndCanProcessForSarsFileWithCheck(resultsSchemeYearSelected);
//						numberOfResults = sarsFilesService.countSarsLevyDetailProvidedWspApprovedNotInBatchForMGTransferSchemeYearAndCanProcessForSarsFileWithCheck(resultsSchemeYearSelected);
					}
				} else {
					resultsSchemeYearSelected = null;
					schemeYearEntered = null;
//					resultsFromDate = GenericUtility.getStartOfDay(fromDate);
//					resultsToDate = GenericUtility.getStartOfDay(toDate);
					// version one
//					sarsLevyDetails = sarsFilesService.sarsLevyDetailBetweenDatesProvidedWspApprovedAndNotInBatchForMGTransfer(GenericUtility.getStartOfDay(fromDate), GenericUtility.getEndOfDay(toDate));
					// version two
//					sarsLevyDetails = sarsFilesService.sarsLevyDetailBetweenDatesProvidedWspApprovedNotInBatchForMGTransferAndCanProcessForSarsFile(resultsFromDate, resultsToDate);
					// version 3
//					sarsLevyDetails = sarsFilesService.sarsLevyDetailBetweenDatesProvidedWspApprovedNotInBatchForMGTransferAndCanProcessForSarsFileWithMgCheck(resultsFromDate, resultsToDate);
					// version 4  with no date and filter on mandatory grant level
//					sarsLevyDetails = sarsFilesService.sarsLevyDetailProvidedWspApprovedNotInBatchForMGTransferAndCanProcessForSarsFileWithMgCheck();
//					numberOfResults = sarsFilesService.countSarsLevyDetailProvidedWspApprovedNotInBatchForMGTransferAndCanProcessForSarsFileWithMgCheck();
//					List<MgTransactionsBean> test = sarsFilesService.avalaibleEntriesForMgProcessing();
//					numberOfResults = test.size();
				}

				if (numberOfResults != 0) {
					addInfoMessage(numberOfResults + " Results Found");
				} else {
					addInfoMessage("No Results Found");
				}
//			} else {
//				addWarningMessage("Provide Dates before Proceeding");
//			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void countAvalaibleEntries(){
		try {
			if (includeSchemeYearFilter) {
				if (schemeYearEntered == null) {
					throw new Exception("Provide: SARS DHET Scheme Year For Filter or de-select scheme year filter.");
				} else {
					totalAvalibleEntries = sarsFilesService.countAvalaibleEntriesForProcessingBySchemeYear(schemeYearEntered).get(0);
					totalAvalibleEntries.setSchemeYearFilterApplied(true);
					totalAvalibleEntries.setSchemeYear(schemeYearEntered);
				}
			} else {
				totalAvalibleEntries = sarsFilesService.countAvalaibleEntriesForProcessing().get(0);
				totalAvalibleEntries.setSchemeYearFilterApplied(false);
				totalAvalibleEntries.setSchemeYear(null);
			}
			totalAvalibleEntries.setDateGenerated(getNow());
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
		
	}
	
	public void clearInformation() {
		try {
			clearDates();
			clearSarsResults();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void clearSchemeYear(){
		schemeYearEntered = null;
	}

	private void clearDates() throws Exception {
		fromDate = null;
		resultsFromDate = null;
		toDate = null;
		resultsToDate = null;
		maxDate = null;
		includeSchemeYearFilter = false;
		schemeYearEntered = null;
		resultsSchemeYearSelected = null;
		totalAvalibleEntries = null;
	}

	private void clearSarsResults() {
		sarsLevyDetails = new ArrayList<>();
	}
	
	public void generateNewBatch() {
		System.out.println("Started printing Inside generateNewBatch");
		boolean genUnderway = false;
		boolean canGen = false;
		try {
			// check if generation underway
			ReportGenerationProperties prop = reportGenerationPropertiesService.findByReportProperty(ReportPropertiesEnum.MG_Transactions);
			if (prop == null) {
				throw new Exception("Unable to locate generation property for: " + ReportPropertiesEnum.MG_Transactions.getFriendlyName() + ". Contact Support!");
			}
			if (prop.getGenerationUnderway()) {
				genUnderway = true;
				canGen = false;
			} else {
				prop.setGenerationUnderway(true);
				prop.setDateGenerationStarted(new Date());
				prop.setUserStartedGeneration(getSessionUI().getActiveUser());
				reportGenerationPropertiesService.create(prop);
				canGen = true;
				genUnderway = true;
			}
			if (genUnderway && !canGen) {
				throw new Exception("Generation Underway, please wait till previous generation complete or contact support.");
			}
			List<MgTransactionsBean> avalaibleEntries = new ArrayList<>();
			if (includeSchemeYearFilter) {
				if (schemeYearEntered == null) {
					throw new Exception("Provide: SARS DHET Scheme Year For Filter or de-select scheme year filter.");
				} else {
					avalaibleEntries = sarsFilesService.avalaibleEntriesForMgProcessingBySchemeYear(schemeYearEntered);
				}
			} else {
				avalaibleEntries = sarsFilesService.avalaibleEntriesForMgProcessing();
			}
			if (avalaibleEntries.isEmpty()) {
				addWarningMessage("No Avalible Entries To Generate");
			} else {
				resultsSchemeYearSelected = null;
				System.out.println("Inside MandatoryGrantsTransactionGpUI:: generateNewBatch:: includeSchemeYearFilter:: before calling generateMgTransactionsBatch():: resultsSchemeYearSelected:: "+resultsSchemeYearSelected);
				gpGrantBatchListService.generateMgTransactionsBatch(avalaibleEntries, getSessionUI().getActiveUser(), WspTypeEnum.Mandatory, resultsSchemeYearSelected);
				System.out.println("Inside MandatoryGrantsTransactionGpUI:: generateNewBatch:: includeSchemeYearFilter:: after calling generateMgTransactionsBatch()");
				avalaibleEntries.clear();
				avalaibleEntries = null;
				totalAvalibleEntries = null;
				gpGrantBatchList = null;
				clearInformation();
				addInfoMessage("Generation Complete");
			}
			prop = reportGenerationPropertiesService.findByReportProperty(ReportPropertiesEnum.MG_Transactions);
			prop.setGenerationUnderway(false);
			prop.setDateLastCompleted(new Date());
			reportGenerationPropertiesService.create(prop);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			try {
				if (genUnderway && canGen) {
					ReportGenerationProperties prop = reportGenerationPropertiesService.findByReportProperty(ReportPropertiesEnum.MG_Transactions);
					prop.setGenerationUnderway(false);
					prop.setDateLastCompleted(new Date());
					reportGenerationPropertiesService.create(prop);
				}
			} catch (Exception e2) {
				addErrorMessage(e.getMessage(), e);
			}
		}
	}
	
	public void selectBatchListToView(){
		try {
			dataModelGpGrantBatchEntryInfo();
			addInfoMessage("Batch List Selected");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void closeBatchListToView(){
		try {
			gpGrantBatchList = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void dataModelGpGrantBatchEntryInfo() throws Exception {
		 dataModelGpGrantBatchEntry = new LazyDataModel<GpGrantBatchEntry>() {
			private static final long serialVersionUID = 1L;
			private List<GpGrantBatchEntry> retorno = new ArrayList<GpGrantBatchEntry>();
			@Override
			public List<GpGrantBatchEntry> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = gpGrantBatchEntryService.allGpGrantBatchEntryByBatchListViewOnly(first, pageSize, sortField, sortOrder, filters, gpGrantBatchList);
					dataModelGpGrantBatchEntry.setRowCount(gpGrantBatchEntryService.countAllGpGrantBatchEntryByBatchListViewOnly(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(GpGrantBatchEntry obj) {
				return obj.getId();
			}
			@Override
			public GpGrantBatchEntry getRowData(String rowKey) {
				for (GpGrantBatchEntry obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void testBatchSend(){
		try {
			gpGrantBatchListService.testGPBatch(gpGrantBatchList);
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void viewActiveContractsTasks() {
		try {
			populateTaskService();
			clearTasksSearchParams();
			if (gpGrantBatchListTaskView != null && gpGrantBatchListTaskView.getId() != null) {
				tasksTargetClass.append(gpGrantBatchListTaskView.getClass().getName());
				tasksTargetKey = gpGrantBatchListTaskView.getId();
				gpGrantBatchListTaskView = null;
				runClientSideExecute("PF('tasksDlg').show()");
				tasksDataModelInfo();
			} else {
				addWarningMessage("Unable to find link for tasks view. Contact Support!");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void clearTasksSearchParams() {
		tasksTargetClass = new StringBuilder();
		tasksTargetKey = null;
	}
	
	private void populateTaskService() {
		if (tasksService == null) {
			tasksService = new TasksService();
		}
	}
	
	public void tasksDataModelInfo() {
		tasksDataModel = new LazyDataModel<Tasks>() {
			private static final long serialVersionUID = 1L;
			private List<Tasks> retorno = new ArrayList<Tasks>();

			@Override
			public List<Tasks> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (sortField == null || sortField.isEmpty()) {
						sortField = "createDate";
						sortOrder = SortOrder.DESCENDING;
					}
					if (tasksTargetKey != null && tasksTargetClass != null && !tasksTargetClass.toString().isEmpty()) {
						retorno = tasksService.allTasksByTargetClassAndKey(first, pageSize, sortField, sortOrder, filters, tasksTargetKey, tasksTargetClass.toString());
						tasksDataModel.setRowCount(tasksService.countAllTasksByTargetClassAndKey(filters));
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Tasks obj) {
				return obj.getId();
			}

			@Override
			public Tasks getRowData(String rowKey) {
				for (Tasks obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void closeTaskView(){
		try {
			clearTasksSearchParams();
			runClientSideExecute("PF('tasksDlg').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/* Getters and Setters */
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public List<SarsLevyDetails> getSarsLevyDetails() {
		return sarsLevyDetails;
	}

	public void setSarsLevyDetails(List<SarsLevyDetails> sarsLevyDetails) {
		this.sarsLevyDetails = sarsLevyDetails;
	}

	public GpGrantBatchList getGpGrantBatchList() {
		return gpGrantBatchList;
	}

	public void setGpGrantBatchList(GpGrantBatchList gpGrantBatchList) {
		this.gpGrantBatchList = gpGrantBatchList;
	}

	public GpGrantBatchEntry getGpGrantBatchEntry() {
		return gpGrantBatchEntry;
	}

	public void setGpGrantBatchEntry(GpGrantBatchEntry gpGrantBatchEntry) {
		this.gpGrantBatchEntry = gpGrantBatchEntry;
	}

	public LazyDataModel<GpGrantBatchList> getDataModelGpGrantBatchList() {
		return dataModelGpGrantBatchList;
	}

	public void setDataModelGpGrantBatchList(LazyDataModel<GpGrantBatchList> dataModelGpGrantBatchList) {
		this.dataModelGpGrantBatchList = dataModelGpGrantBatchList;
	}

	public LazyDataModel<GpGrantBatchEntry> getDataModelGpGrantBatchEntry() {
		return dataModelGpGrantBatchEntry;
	}

	public void setDataModelGpGrantBatchEntry(LazyDataModel<GpGrantBatchEntry> dataModelGpGrantBatchEntry) {
		this.dataModelGpGrantBatchEntry = dataModelGpGrantBatchEntry;
	}

	public Integer getSchemeYearEntered() {
		return schemeYearEntered;
	}

	public void setSchemeYearEntered(Integer schemeYearEntered) {
		this.schemeYearEntered = schemeYearEntered;
	}

	public Boolean getIncludeSchemeYearFilter() {
		return includeSchemeYearFilter;
	}

	public void setIncludeSchemeYearFilter(Boolean includeSchemeYearFilter) {
		this.includeSchemeYearFilter = includeSchemeYearFilter;
	}

	public String getYEAR_FORMAT() {
		return YEAR_FORMAT;
	}

	public Date getResultsfromDate() {
		return resultsFromDate;
	}

	public void setResultsfromDate(Date resultsfromDate) {
		this.resultsFromDate = resultsfromDate;
	}

	public Date getResultstoDate() {
		return resultsToDate;
	}

	public void setResultstoDate(Date resultstoDate) {
		this.resultsToDate = resultstoDate;
	}

	public Integer getResultsSchemeYearSelected() {
		return resultsSchemeYearSelected;
	}

	public void setResultsSchemeYearSelected(Integer resultsSchemeYearSelected) {
		this.resultsSchemeYearSelected = resultsSchemeYearSelected;
	}

	public Integer getNumberOfResults() {
		return numberOfResults;
	}

	public void setNumberOfResults(Integer numberOfResults) {
		this.numberOfResults = numberOfResults;
	}

	public MgTransactionsBean getTotalAvalibleEntries() {
		return totalAvalibleEntries;
	}

	public void setTotalAvalibleEntries(MgTransactionsBean totalAvalibleEntries) {
		this.totalAvalibleEntries = totalAvalibleEntries;
	}

	public LazyDataModel<Tasks> getTasksDataModel() {
		return tasksDataModel;
	}

	public void setTasksDataModel(LazyDataModel<Tasks> tasksDataModel) {
		this.tasksDataModel = tasksDataModel;
	}

	public GpGrantBatchList getGpGrantBatchListTaskView() {
		return gpGrantBatchListTaskView;
	}

	public void setGpGrantBatchListTaskView(GpGrantBatchList gpGrantBatchListTaskView) {
		this.gpGrantBatchListTaskView = gpGrantBatchListTaskView;
	}

	public Long getTasksTargetKey() {
		return tasksTargetKey;
	}

	public void setTasksTargetKey(Long tasksTargetKey) {
		this.tasksTargetKey = tasksTargetKey;
	}

	public StringBuilder getTasksTargetClass() {
		return tasksTargetClass;
	}

	public void setTasksTargetClass(StringBuilder tasksTargetClass) {
		this.tasksTargetClass = tasksTargetClass;
	}
}