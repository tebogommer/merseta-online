package haj.com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.RejectReasonMultipleSelectionHistory;
import haj.com.entity.Tasks;
import haj.com.entity.Wsp;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.RejectReasonMultipleSelectionService;
import haj.com.service.TasksService;
import haj.com.service.WspService;
import haj.com.service.lookup.RejectReasonsService;

// TODO: Auto-generated Javadoc
/**
 * The Class TasksUI.
 */
@ManagedBean(name = "adminWspTasksUI")
@ViewScoped
public class AdminWspTasksUI extends AbstractUI {

	/** The tasksfiltered list. */
	private List<Tasks> tasksfilteredList = null;
	/** The data model. */
	private LazyDataModel<Tasks> dataModel;
	private ConfigDocProcessEnum configDocProcessEnum;
	private WspService wspService = new WspService();
	RejectReasonsService rs= new RejectReasonsService();
	private List<Integer> financialYears;
	private Integer selectedYear;

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
	 * Initialize method to get all Tasks and prepare a for a create of a new Tasks.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Tasks
	 */
	private void runInit() throws Exception {
		populatesDistinctFinancialYears();
		tasksInfo();
	}

	/**
	 * Get all Tasks for data table.
	 *
	 * @author TechFinium
	 * @see Tasks
	 */
	public void tasksInfo() {

		dataModel = new LazyDataModel<Tasks>() {

			private static final long serialVersionUID = 1L;
			private List<Tasks> retorno = new ArrayList<Tasks>();

			@Override
			public List<Tasks> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					
					if(filters == null) {
						filters = new HashMap<>();
					}

					filters.put("targetClass", "haj.com.entity.Wsp");
					filters.put("firstInProcess", true);
					filters.put("workflowProcess", ConfigDocProcessEnum.WSP);
					if(selectedYear != null) {
						retorno = TasksService.instance().allAdminTasksForWsp(Tasks.class, first, pageSize, sortField, sortOrder, filters, selectedYear);
						for(Tasks task:retorno) {
							task.setWsp(wspService.findByKey(task.getTargetKey()));
							populatePreviousRejectReasons(task);
							if(task.getWsp() != null && task.getWsp().getWspStatusEnum()!=null && task.getWsp().getWspStatusEnum() == WspStatusEnum.Rejected) {
								populateRejectReasons(task.getWsp());
							}else if(task.getWsp()!= null  && task.getWsp().getWspStatusEnum()!=null && task.getWsp().getWspStatusEnum() != WspStatusEnum.Rejected) {
								task.getWsp().setRejectionReasons("N/A");
							}
						}
						dataModel.setRowCount(TasksService.instance().countAdminTasksForWsp(Tasks.class, filters, selectedYear));	
						
					}else {
						retorno = TasksService.instance().allAdminTasksForWsp(Tasks.class, first, pageSize, sortField, sortOrder, filters);
						for(Tasks task:retorno) {
							task.setWsp(wspService.findByKey(task.getTargetKey()));
							populatePreviousRejectReasons(task);
							if(task.getWsp() != null && task.getWsp().getWspStatusEnum()!=null && task.getWsp().getWspStatusEnum() == WspStatusEnum.Rejected) {
								populateRejectReasons(task.getWsp());
							}else if(task.getWsp()!= null  && task.getWsp().getWspStatusEnum()!=null && task.getWsp().getWspStatusEnum() != WspStatusEnum.Rejected) {
								task.getWsp().setRejectionReasons("N/A");
							}
						}
						dataModel.setRowCount(TasksService.instance().countAdminTasksForWsp(Tasks.class, filters));	
					}
				} catch (Exception e) {
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
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}
	
	private void populateRejectReasons(Wsp wsp) {
		
		try {
			List<RejectReasons> rrList = rs.locateReasonsSelectedByTargetKeyClassAndProcess(wsp.getId(), Wsp.class.getName(),ConfigDocProcessEnum.WSP);
			wsp.setRejectionReasons(rejectReasonString(rrList));			
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}		
	}
	
	private void populatePreviousRejectReasons(Tasks task) {
		try {
			List<RejectReasonMultipleSelectionHistory> list= RejectReasonMultipleSelectionService.instance().findHistoryByTargetKeyClassNameAndProcess(task.getTargetKey(), task.getTargetClass(), task.getWorkflowProcess());
			List<RejectReasons> rrList = new ArrayList<>();
			for(RejectReasonMultipleSelectionHistory rjr: list) {
				rrList.add(rjr.getRejectReason());
			}
			task.setCss(rejectReasonString(rrList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String rejectReasonString(List<RejectReasons> rrList ) {
		String results = "";
		int count = 1;
		for (RejectReasons rejectReasons : rrList) {
			if (count == rrList.size()) {
				results+= rejectReasons.getDescription();
			}else {
				results+= rejectReasons.getDescription() + ", ";
			}
			count++;
		}
		if (results != "") {
			return results;
		} else {
			return "None Found";
		}
	}

	public void prepReportWspByFinancialYearAndStatus() throws Exception {
		populatesDistinctFinancialYears();
	}
	
	public void populatesDistinctFinancialYears() throws Exception {
		financialYears = wspService.findDictinctFinYears();
		if (financialYears.size() != 0) {
			selectedYear = financialYears.get(0);
		}
	}
	
	public void clearWSPSearchHistory() {
		selectedYear = null;
	}
	
	public void populateWspByFinancialYearAndStatus() {
		try {
	
			if (selectedYear == null) {
				throw new Exception("Select Financial Year Before Proceeding");
			}
			tasksInfo();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	/**
	 * Gets the tasksfiltered list.
	 *
	 * @return the tasksfiltered list
	 */
	public List<Tasks> getTasksfilteredList() {
		return tasksfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param tasksfilteredList
	 *            the new tasksfilteredList list
	 * @see Tasks
	 */
	public void setTasksfilteredList(List<Tasks> tasksfilteredList) {
		this.tasksfilteredList = tasksfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<Tasks> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel
	 *            the new data model
	 */
	public void setDataModel(LazyDataModel<Tasks> dataModel) {
		this.dataModel = dataModel;
	}

	public ConfigDocProcessEnum getConfigDocProcessEnum() {
		return configDocProcessEnum;
	}

	public void setConfigDocProcessEnum(ConfigDocProcessEnum configDocProcessEnum) {
		this.configDocProcessEnum = configDocProcessEnum;
	}

	public List<Integer> getFinancialYears() {
		return financialYears;
	}

	public void setFinancialYears(List<Integer> financialYears) {
		this.financialYears = financialYears;
	}

	public Integer getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(Integer selectedYear) {
		this.selectedYear = selectedYear;
	}

}
