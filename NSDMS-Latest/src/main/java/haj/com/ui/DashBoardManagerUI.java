package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Blank;
import haj.com.entity.HostingCompanyDepartmentProcess;
import haj.com.entity.HostingCompanyDepartments;
import haj.com.entity.WspSignoff;
import haj.com.entity.enums.TaskStatusEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DashBoardService;
import haj.com.service.WspSignoffService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class DashBoardManagerUI.
 */
@ManagedBean(name = "dashBoardManagerUI")
@ViewScoped
public class DashBoardManagerUI extends AbstractUI {

	/** The open tasks. */
	private Long openTasks;

	/** The underway tasks. */
	private Long underwayTasks;

	/** The completed tasks. */
	private Long completedTasks;

	/** The departments. */
	private List<HostingCompanyDepartments> departments;

	private WspSignoffService wspService = new WspSignoffService();
	private LazyDataModel<WspSignoff> wsps;
	private LazyDataModel<WspSignoff> wspsByFinYear;
	private List<Integer> finYearByGrantList;
	private Integer selectedFinYear;

	/** The pr. */
	private String pr;

	/** The open data. */
	private String openData;

	/** The underway data. */
	private String underwayData;

	/** The completed data. */
	private String completedData;

	/** The heading. */
	private String heading;
	
	private boolean dataForGraphs = false;

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
	 * Initialize method to get all Blank and prepare a for a create of a new Blank.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Blank
	 */
	private void runInit() throws Exception {
		List<Long> processes = new ArrayList<Long>();
		DashBoardService.instance().processUsersDepartment(getSessionUI().getActiveUser(), processes);
		String inclause = GenericUtility.initNumericSqlInClause(processes.toString());
		if (inclause.equals("()")) {
			this.openTasks =  (long) 0;
			this.underwayTasks = (long) 0;
			this.completedTasks = (long) 0;
		}else {
			this.openTasks = DashBoardService.instance().countTasks(TaskStatusEnum.NotStarted, inclause);
			this.underwayTasks = DashBoardService.instance().countTasks(TaskStatusEnum.Underway, inclause);
			this.completedTasks = DashBoardService.instance().completedTaskForThisMonth(inclause);
		}
		
		// wsps = wspService.findCompletedSignOffs();
		wspInfo();
		populateFinYearWspReport();
		departments = DashBoardService.instance().processGraphDataPerManagerDepartment(getSessionUI().getActiveUser());
		String dm = "'#name#',";
		pr = "[";
		// [3, 4, 4, 2]
		openData = "[";
		underwayData = "[";
		completedData = "[";
		for (HostingCompanyDepartments hcd : departments) {
			heading = "'Task per process for department " + hcd.getDepartment().trim() + "'";
			for (HostingCompanyDepartmentProcess proc : hcd.getHostingCompanyDepartmentProcesses()) {
				dataForGraphs = true;
				pr = pr + (dm.replace("#name#", super.getEntryLanguage(proc.getHostingCompanyProcess().getWorkflowProcess().getRegistrationName().trim())));
				openData = openData + proc.getOpenTasks() + ",";
				underwayData = underwayData + proc.getUnderwayTasks() + ",";
				completedData = completedData + proc.getCompletedTasks() + ",";
			}
			break;
		}
		if (pr.contains(",")) {
			pr = pr.substring(0, pr.lastIndexOf(',')) + "]";
		} else {
			pr = "[0]";
		}
		if (openData.contains(",")) {
			openData = openData.substring(0, openData.lastIndexOf(',')) + "]";
		} else {
			openData = "[0]";
		}
		if (underwayData.contains(",")) {
			underwayData = underwayData.substring(0, underwayData.lastIndexOf(',')) + "]";
		} else {
			underwayData = "[0]";
		}
		if (completedData.contains(",")) {
			completedData = completedData.substring(0, completedData.lastIndexOf(',')) + "]";
		} else {
			completedData = "[0]";
		}
		/*
		 * departments.forEach(a->{ System.out.println(a.getDepartment());
		 * a.getHostingCompanyDepartmentProcesses().forEach(b->{
		 * System.out.println("\t"+b.getHostingCompanyProcess().getWorkflowProcess()
		 * +" openTask:"+b.getOpenTasks() + " underway:" + b.getUnderwayTasks() +
		 * " completed:" + b.getCompletedTasks()); }); });
		 */
	}

	private void wspInfo() {
		// wsps = wspService.findCompletedSignOffs();
		wsps = new LazyDataModel<WspSignoff>() {
			private static final long serialVersionUID = 1L;
			private List<WspSignoff> retorno = new ArrayList<WspSignoff>();

			@Override
			public List<WspSignoff> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = wspService.findCompletedSignOffs(first, pageSize, sortField, sortOrder, filters);
					wsps.setRowCount(wspService.count(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(WspSignoff obj) {
				return obj.getId();
			}

			@Override
			public WspSignoff getRowData(String rowKey) {
				for (WspSignoff obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	public void populateFinYearWspReport(){
		try {
			if (finYearByGrantList == null || finYearByGrantList.size() == 0) {
				finYearByGrantList = DashBoardService.instance().allDistinctFinYearsLastestThreeYears();
			}
			if (selectedFinYear == null && finYearByGrantList != null && finYearByGrantList.size() != 0) {
				selectedFinYear = finYearByGrantList.get(0);
			}
			wspInfoByFinYear();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void wspInfoByFinYear(){
		wspsByFinYear = new LazyDataModel<WspSignoff>() {
			private static final long serialVersionUID = 1L;
			private List<WspSignoff> retorno = new ArrayList<WspSignoff>();
			@Override
			public List<WspSignoff> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (selectedFinYear != null) {
						retorno = wspService.findCompletedSignOffsByFinYear(first, pageSize, sortField, sortOrder, filters, selectedFinYear);
						wspsByFinYear.setRowCount(wspService.countCompletedSignOffsByFinYear(filters));
					} else {
						/* Fail Safe */
						retorno = wspService.findCompletedSignOffs(first, pageSize, sortField, sortOrder, filters);
						wspsByFinYear.setRowCount(wspService.count(filters));
					}
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
				}
				return retorno;
			}

			@Override
			public Object getRowKey(WspSignoff obj) {
				return obj.getId();
			}

			@Override
			public WspSignoff getRowData(String rowKey) {
				for (WspSignoff obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Gets the open tasks.
	 *
	 * @return the open tasks
	 */
	public Long getOpenTasks() {
		return openTasks;
	}

	/**
	 * Sets the open tasks.
	 *
	 * @param openTasks
	 *            the new open tasks
	 */
	public void setOpenTasks(Long openTasks) {
		this.openTasks = openTasks;
	}

	/**
	 * Gets the completed tasks.
	 *
	 * @return the completed tasks
	 */
	public Long getCompletedTasks() {
		return completedTasks;
	}

	/**
	 * Sets the completed tasks.
	 *
	 * @param completedTasks
	 *            the new completed tasks
	 */
	public void setCompletedTasks(Long completedTasks) {
		this.completedTasks = completedTasks;
	}

	/**
	 * Gets the underway tasks.
	 *
	 * @return the underway tasks
	 */
	public Long getUnderwayTasks() {
		return underwayTasks;
	}

	/**
	 * Sets the underway tasks.
	 *
	 * @param underwayTasks
	 *            the new underway tasks
	 */
	public void setUnderwayTasks(Long underwayTasks) {
		this.underwayTasks = underwayTasks;
	}

	/**
	 * Gets the departments.
	 *
	 * @return the departments
	 */
	public List<HostingCompanyDepartments> getDepartments() {
		return departments;
	}

	/**
	 * Sets the departments.
	 *
	 * @param departments
	 *            the new departments
	 */
	public void setDepartments(List<HostingCompanyDepartments> departments) {
		this.departments = departments;
	}

	/**
	 * Gets the pr.
	 *
	 * @return the pr
	 */
	public String getPr() {
		return pr;
	}

	/**
	 * Sets the pr.
	 *
	 * @param pr
	 *            the new pr
	 */
	public void setPr(String pr) {
		this.pr = pr;
	}

	/**
	 * Gets the open data.
	 *
	 * @return the open data
	 */
	public String getOpenData() {
		return openData;
	}

	/**
	 * Sets the open data.
	 *
	 * @param openData
	 *            the new open data
	 */
	public void setOpenData(String openData) {
		this.openData = openData;
	}

	/**
	 * Gets the completed data.
	 *
	 * @return the completed data
	 */
	public String getCompletedData() {
		return completedData;
	}

	/**
	 * Sets the completed data.
	 *
	 * @param completedData
	 *            the new completed data
	 */
	public void setCompletedData(String completedData) {
		this.completedData = completedData;
	}

	/**
	 * Gets the underway data.
	 *
	 * @return the underway data
	 */
	public String getUnderwayData() {
		return underwayData;
	}

	/**
	 * Sets the underway data.
	 *
	 * @param underwayData
	 *            the new underway data
	 */
	public void setUnderwayData(String underwayData) {
		this.underwayData = underwayData;
	}

	/**
	 * Gets the heading.
	 *
	 * @return the heading
	 */
	public String getHeading() {
		return heading;
	}

	/**
	 * Sets the heading.
	 *
	 * @param heading
	 *            the new heading
	 */
	public void setHeading(String heading) {
		this.heading = heading;
	}

	public LazyDataModel<WspSignoff> getWsps() {
		return wsps;
	}

	public void setWsps(LazyDataModel<WspSignoff> wsps) {
		this.wsps = wsps;
	}

	public boolean isDataForGraphs() {
		return dataForGraphs;
	}

	public void setDataForGraphs(boolean dataForGraphs) {
		this.dataForGraphs = dataForGraphs;
	}

	public LazyDataModel<WspSignoff> getWspsByFinYear() {
		return wspsByFinYear;
	}

	public void setWspsByFinYear(LazyDataModel<WspSignoff> wspsByFinYear) {
		this.wspsByFinYear = wspsByFinYear;
	}

	public List<Integer> getFinYearByGrantList() {
		return finYearByGrantList;
	}

	public void setFinYearByGrantList(List<Integer> finYearByGrantList) {
		this.finYearByGrantList = finYearByGrantList;
	}

	public Integer getSelectedFinYear() {
		return selectedFinYear;
	}

	public void setSelectedFinYear(Integer selectedFinYear) {
		this.selectedFinYear = selectedFinYear;
	}

}
