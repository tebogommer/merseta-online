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

import haj.com.entity.ActiveContractDetail;
import haj.com.entity.Blank;
import haj.com.entity.GpGrantBatchEntry;
import haj.com.entity.GpGrantBatchList;
import haj.com.entity.Tasks;
import haj.com.entity.enums.WspTypeEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.sars.SarsLevyDetails;
import haj.com.service.ActiveContractDetailService;
import haj.com.service.GpGrantBatchEntryService;
import haj.com.service.GpGrantBatchListService;
import haj.com.service.TasksService;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "discretionaryGrantsTransactionGpUI")
@ViewScoped
public class DiscretionaryGrantsTransactionGpUI extends AbstractUI {

	/* Entity Levels */
	private GpGrantBatchList gpGrantBatchList = null;
	private GpGrantBatchEntry gpGrantBatchEntry = null;
	private GpGrantBatchList gpGrantBatchListTaskView = null;

	/* Service Levels */
	private ActiveContractDetailService activeContractDetailService = new ActiveContractDetailService();
	private GpGrantBatchListService gpGrantBatchListService = new GpGrantBatchListService();
	private GpGrantBatchEntryService gpGrantBatchEntryService = new GpGrantBatchEntryService();
	private TasksService tasksService = null;

	/* Array Lists */
	private List<ActiveContractDetail> activeContractDetailList = new ArrayList<>();
	
	/* Data Models */
	private LazyDataModel<GpGrantBatchList> dataModelGpGrantBatchList;
	private LazyDataModel<GpGrantBatchEntry> dataModelGpGrantBatchEntry;
	private LazyDataModel<Tasks> tasksDataModel;
	
	private StringBuilder tasksTargetClass;
	private Long tasksTargetKey = null;
	
	/* Dates */
	private Date fromDate = null;
	private Date toDate = null;
	private Date maxDate = null;

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
					retorno = gpGrantBatchListService.allGpGrantBatchListByWspType(first, pageSize, sortField, sortOrder, filters, WspTypeEnum.Discretionary);
					dataModelGpGrantBatchList.setRowCount(gpGrantBatchListService.countAllGpGrantBatchListByWspType(filters));
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
				activeContractDetailList = activeContractDetailService.findNotProcessedForGp();
				if (activeContractDetailList.size() != 0) {
					addInfoMessage(activeContractDetailList.size() + " Results Found");
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
	
	public void clearInformation() {
		try {
			clearDates();
			clearSarsResults();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void clearDates() throws Exception {
		fromDate = null;
		toDate = null;
		maxDate = null;
	}

	private void clearSarsResults() {
		activeContractDetailList = new ArrayList<>();
	}
	
	public void generateNewBatch(){
		try {
			if (activeContractDetailList != null && activeContractDetailList.size() != 0) {
				gpGrantBatchListService.generateNewBatchListDG(activeContractDetailList, getSessionUI().getActiveUser(), fromDate, toDate, WspTypeEnum.Discretionary);
				gpGrantBatchList = null;
				clearInformation();
				addInfoMessage("Generation Complete");
			} else {
				addWarningMessage("Search For Results Before Generating");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
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
					retorno = gpGrantBatchEntryService.allGpGrantBatchEntryByBatchList(first, pageSize, sortField, sortOrder, filters, gpGrantBatchList);
					dataModelGpGrantBatchEntry.setRowCount(gpGrantBatchEntryService.countAllGpGrantBatchEntryByBatchList(filters));
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
	
	public void testBatchSendGP(){
		try {
			gpGrantBatchListService.testDgBatch(gpGrantBatchList);
			addInfoMessage("Action Complete");
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
	
	public void closeTaskView(){
		try {
			clearTasksSearchParams();
			runClientSideExecute("PF('tasksDlg').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
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

	public List<ActiveContractDetail> getActiveContractDetailList() {
		return activeContractDetailList;
	}

	public void setActiveContractDetailList(List<ActiveContractDetail> activeContractDetailList) {
		activeContractDetailList = activeContractDetailList;
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
}