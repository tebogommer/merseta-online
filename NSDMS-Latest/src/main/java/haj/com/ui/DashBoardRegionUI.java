package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.bean.WSPReport;
import haj.com.bean.WSPReportListByYear;
import haj.com.entity.Blank;
import haj.com.entity.Company;
import haj.com.entity.SDFCompany;
import haj.com.entity.WspSignoff;
import haj.com.entity.enums.TaskStatusEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.DashBoardService;
import haj.com.service.SDFCompanyService;
import haj.com.service.WspSignoffService;

// TODO: Auto-generated Javadoc
/**
 * The Class DashBoardUI.
 */
@ManagedBean(name = "dashBoardRegionUI")
@ViewScoped
public class DashBoardRegionUI extends AbstractUI {

	/** The open tasks. */
	private Long openTasks;

	/** The underway tasks. */
	private Long underwayTasks;

	/** The completed tasks. */
	private Long completedTasks;

	private WspSignoffService wspService = new WspSignoffService();
	private LazyDataModel<WspSignoff> wsps;
	private LazyDataModel<WspSignoff> wspsByFinYear;
	private LazyDataModel<SDFCompany> wsps2;
	private LazyDataModel<SDFCompany> wsps2ByFinYear;
	private SDFCompanyService sdfCompanyService = new SDFCompanyService();
	
	private List<Integer> finYearByGrantList;
	private List<WSPReportListByYear> wspReportByYear;
	private List<WSPReportListByYear> wspReportByYear2;
	private List<WSPReport> wspReports;
	private List<WSPReport> wspReports2;
	private LazyDataModel<Company> dataModel;
	private CompanyService companyService = new CompanyService();
	
	private Integer selectedFinYear;

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

		this.openTasks = DashBoardService.instance().countTaskForUser(TaskStatusEnum.NotStarted, getSessionUI().getActiveUser());

		this.underwayTasks = DashBoardService.instance().countTaskForUser(TaskStatusEnum.Underway, getSessionUI().getActiveUser());

		this.completedTasks = DashBoardService.instance().completedTaskForThisMonthForUser(getSessionUI().getActiveUser());
//		System.out.println(getSessionUI().getDashboard());
		if (getSessionUI().getDashboard() != null && !getSessionUI().getDashboard().equals("/pages/dashboard.jsf")) {
			populateWspReport();
			populateWspReport2();
			populateFinYearWspReport();
//			 wspReports = DashBoardService.instance().countWSP();
//			 wspReports2 = DashBoardService.instance().countWSP2();
			// wsps = wspService.findCompletedSignOffs(getSessionUI().getActiveUser());
			wspInfo();
			companyInfo();
		}
	}
	
	/**
	 * @throws Exception
	 */
	public void populateWspReport() throws Exception {
		if (finYearByGrantList == null || finYearByGrantList.size() == 0) {
			finYearByGrantList = DashBoardService.instance().allDistinctFinYearsLastestThreeYears();
		}
		wspReportByYear = new ArrayList<WSPReportListByYear>();
		
		for (Integer finYear : finYearByGrantList) {
			WSPReportListByYear result = new WSPReportListByYear();
			result.setFinYear(finYear);
			result.setWspReportList(DashBoardService.instance().countWSPByFinYear(finYear));
			wspReportByYear.add(result);
		}
		// old version 
//		wspReports = DashBoardService.instance().countWSP();
	}

	/**
	 * @throws Exception
	 */
	public void populateWspReport2() throws Exception {
		if (finYearByGrantList == null || finYearByGrantList.size() == 0) {
			finYearByGrantList = DashBoardService.instance().allDistinctFinYearsLastestThreeYears();
		}
		wspReportByYear2 = new ArrayList<WSPReportListByYear>();
		for (Integer finYear : finYearByGrantList) {
			WSPReportListByYear result = new WSPReportListByYear();
			result.setFinYear(finYear);
			result.setWspReportList(DashBoardService.instance().countWSP2ByFinYear(finYear));
			wspReportByYear2.add(result);
		}
		
		// old version
//		wspReports2 = DashBoardService.instance().countWSP2();
	}

	private void wspInfo() {
		wsps = new LazyDataModel<WspSignoff>() {
			private static final long serialVersionUID = 1L;
			private List<WspSignoff> retorno = new ArrayList<WspSignoff>();

			@Override
			public List<WspSignoff> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = wspService.findCompletedSignOffs(first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser());
					wsps.setRowCount(wspService.countRegion(filters));
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
		wsps2 = new LazyDataModel<SDFCompany>() {
			private static final long serialVersionUID = 1L;
			private List<SDFCompany> retorno = new ArrayList<SDFCompany>();

			@Override
			public List<SDFCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = sdfCompanyService.findNotCompletedWsp(SDFCompany.class, first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser());
					wsps2.setRowCount(sdfCompanyService.countNotCompletedWsp(filters, getSessionUI().getActiveUser()).intValue());
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SDFCompany obj) {
				return obj.getId();
			}

			@Override
			public SDFCompany getRowData(String rowKey) {
				for (SDFCompany obj : retorno) {
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
			wspTwoInfoByFinYear();
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
						retorno = wspService.findCompletedSignOffsByRegionUserByFinYear(first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser(), selectedFinYear);
						wspsByFinYear.setRowCount(wspService.countCompletedSignOffsByRegionUserByFinYear(filters));
					} else {
						/* Fail Safe */
						retorno = wspService.findCompletedSignOffs(first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser());
						wspsByFinYear.setRowCount(wspService.countRegion(filters));
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
	
	private void wspTwoInfoByFinYear(){
		wsps2ByFinYear = new LazyDataModel<SDFCompany>() {
			private static final long serialVersionUID = 1L;
			private List<SDFCompany> retorno = new ArrayList<SDFCompany>();

			@Override
			public List<SDFCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (selectedFinYear != null) {
						retorno = sdfCompanyService.findNotCompletedWspByUserRegionAndByFinYear(SDFCompany.class, first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser(), selectedFinYear);
						wsps2ByFinYear.setRowCount(sdfCompanyService.countNotCompletedWspByUserRegionAndByFinYear(filters, getSessionUI().getActiveUser(), selectedFinYear).intValue());	
					}else {
						/* Fail Safe */
						retorno = sdfCompanyService.findNotCompletedWsp(SDFCompany.class, first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser());
						wsps2ByFinYear.setRowCount(sdfCompanyService.countNotCompletedWsp(filters, getSessionUI().getActiveUser()).intValue());
					}
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SDFCompany obj) {
				return obj.getId();
			}

			@Override
			public SDFCompany getRowData(String rowKey) {
				for (SDFCompany obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}

	public void companyInfo() {

		dataModel = new LazyDataModel<Company>() {
			private static final long serialVersionUID = 1L;
			private List<Company> companyList = new ArrayList<>();

			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					if (sortField == null) {
						sortField = "companyStatus";
						sortOrder = SortOrder.DESCENDING;
					}
					companyList = companyService.allCompanyRegion(Company.class, first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser());
					dataModel.setRowCount(companyService.countRegion(Company.class, filters));

				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return companyList;
			}

			@Override
			public Object getRowKey(Company object) {
				// TODO Auto-generated method stub
				return object.getId();
			}

			@Override
			public Company getRowData(String rowKey) {
				for (Company obj : companyList) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}

	/**
	 * @param divName
	 * @throws Exception
	 */
	public void pageFocusRun(String divName) throws Exception {

		String js = "setTimeout(function() {$('html,body').animate({scrollTop : $('#" + divName + "').offset().top }, 1000); }, 0);";

		super.runClientSideExecute(js);

		divName = null;
	}

	public void populateAllSystemsTasksTable(String typeOfTask) {

		System.out.println("populateAllSystemsTasksTable ran with typeOfTask : " + typeOfTask + " !");
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

	public List<WSPReport> getWspReports() {
		return wspReports;
	}

	public void setWspReports(List<WSPReport> wspReports) {
		this.wspReports = wspReports;
	}

	public LazyDataModel<Company> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<Company> dataModel) {
		this.dataModel = dataModel;
	}

	public List<WSPReport> getWspReports2() {
		return wspReports2;
	}

	public void setWspReports2(List<WSPReport> wspReports2) {
		this.wspReports2 = wspReports2;
	}

	public LazyDataModel<WspSignoff> getWsps() {
		return wsps;
	}

	public void setWsps(LazyDataModel<WspSignoff> wsps) {
		this.wsps = wsps;
	}

	public LazyDataModel<SDFCompany> getWsps2() {
		return wsps2;
	}

	public void setWsps2(LazyDataModel<SDFCompany> wsps2) {
		this.wsps2 = wsps2;
	}

	public List<Integer> getFinYearByGrantList() {
		return finYearByGrantList;
	}

	public void setFinYearByGrantList(List<Integer> finYearByGrantList) {
		this.finYearByGrantList = finYearByGrantList;
	}

	public List<WSPReportListByYear> getWspReportByYear() {
		return wspReportByYear;
	}

	public void setWspReportByYear(List<WSPReportListByYear> wspReportByYear) {
		this.wspReportByYear = wspReportByYear;
	}

	public List<WSPReportListByYear> getWspReportByYear2() {
		return wspReportByYear2;
	}

	public void setWspReportByYear2(List<WSPReportListByYear> wspReportByYear2) {
		this.wspReportByYear2 = wspReportByYear2;
	}

	public Integer getSelectedFinYear() {
		return selectedFinYear;
	}

	public void setSelectedFinYear(Integer selectedFinYear) {
		this.selectedFinYear = selectedFinYear;
	}

	public LazyDataModel<WspSignoff> getWspsByFinYear() {
		return wspsByFinYear;
	}

	public void setWspsByFinYear(LazyDataModel<WspSignoff> wspsByFinYear) {
		this.wspsByFinYear = wspsByFinYear;
	}

	public LazyDataModel<SDFCompany> getWsps2ByFinYear() {
		return wsps2ByFinYear;
	}

	public void setWsps2ByFinYear(LazyDataModel<SDFCompany> wsps2ByFinYear) {
		this.wsps2ByFinYear = wsps2ByFinYear;
	}

}
