package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.bean.MISReportBean;
import haj.com.bean.WSPReport;
import haj.com.bean.WSPReportListByYear;
import haj.com.entity.Blank;
import haj.com.entity.HostingCompanyDepartmentProcess;
import haj.com.entity.HostingCompanyDepartments;
import haj.com.entity.SDFCompany;
import haj.com.entity.WspSignoff;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.TaskStatusEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DashBoardService;
import haj.com.service.SDFCompanyService;
import haj.com.service.WspSignoffService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class DashBoardSeniorManagerUI.
 */
@ManagedBean(name = "dashBoardExecutiveManagerUI")

@ViewScoped
public class DashBoardExecutiveManagerUI extends AbstractUI {

	/** The open tasks. */
	private Long openTasks;

	/** The underway tasks. */
	private Long underwayTasks;

	/** The completed tasks. */
	private Long completedTasks;
	private List<Integer> finYearByGrantList;
	private List<WSPReportListByYear> wspReportByYear;
	private List<WSPReportListByYear> wspReportByYear2;
	private List<WSPReport> wspReports;
	private List<WSPReport> wspReports2;

	/** The departments. */
	private List<HostingCompanyDepartments> departments;

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

	/** The total companies. */
	private Long totalCompanies;

	/** The pending companies. */
	private Long pendingCompanies;

	/** The active companies. */
	private Long activeCompanies;

	private WspSignoffService wspSignOffService = new WspSignoffService();
	private LazyDataModel<WspSignoff> wsps;
	private LazyDataModel<WspSignoff> wspsByFinYear;

	/** The in active. */
	private Long inActive;
	private Long approved;
	private Long rejected;

	private List<MISReportBean> findEmpPerProv;
	private List<MISReportBean> findEmpPerCity;
	private List<MISReportBean> findSDFPerEmploy;
	private List<MISReportBean> findSDFPerArea;
	private List<MISReportBean> findSDFPerProvince;
	private List<MISReportBean> findSDFPerProfile;
	private List<MISReportBean> findSDFPerRelationship;

	private List<MISReportBean> leviesByChamber;
	private List<MISReportBean> leviesBySize;
	private List<MISReportBean> leviesByChamberAndSize;

	private List<MISReportBean> regCompaniesVsSarsCompanies;
	private LazyDataModel<SDFCompany> wsps2;
	private LazyDataModel<SDFCompany> wsps2ByFinYear;
	private SDFCompanyService sdfCompanyService = new SDFCompanyService();

	private String provData;
	
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

		/*
		 * new Thread(new Runnable() {
		 * 
		 * @Override public void run() {
		 * 
		 * try { regCompaniesVsSarsCompanies = DashBoardService.instance().
		 * totalNumberOfCompaniesRegisteredOnNsdmsInRelationToTotalLevyPayingCompaniesByChamber
		 * (); leviesByChamber = DashBoardService.instance().
		 * totalLevyContributionOfTheCompaniesThatHaveBeenRegisteredOnTheNsdmsByChamber(
		 * ); leviesBySize = DashBoardService.instance().
		 * totalLevyContributionOfTheCompaniesThatHaveBeenRegisteredOnTheNsdmsBySizeOfCompany
		 * (); leviesByChamberAndSize = DashBoardService.instance().
		 * totalLevyContributionOfTheCompaniesThatHaveBeenRegisteredOnTheNsdmsBySizeOfCompanyAndChamber
		 * ();
		 * 
		 * } catch (Exception e) { logger.fatal(e); }
		 * 
		 * } }).start();
		 */

		// totalCompanies = DashBoardService.instance().countCompanies();
		// pendingCompanies =
		// DashBoardService.instance().countCompanies(CompanyStatusEnum.Pending);
		populateWspReport();
		populateWspReport2();
		populateFinYearWspReport();
		wspInfo();
		if (!getSessionUI().isAdmin()) {
			activeCompanies = DashBoardService.instance().countCompanies(CompanyStatusEnum.Active);
			inActive = DashBoardService.instance().countCompanies(CompanyStatusEnum.InActive);
			approved = DashBoardService.instance().countCompanies(CompanyStatusEnum.Approved);
			rejected = DashBoardService.instance().countCompanies(CompanyStatusEnum.Rejected);

			findEmpPerProv = DashBoardService.instance().findEmpPerProv();
			findEmpPerCity = DashBoardService.instance().findEmpPerCity();
			findSDFPerEmploy = DashBoardService.instance().findSDFPerEmploy();
			findSDFPerArea = DashBoardService.instance().findSDFPerArea();
			findSDFPerProvince = DashBoardService.instance().findSDFPerProvince();
			findSDFPerProfile = DashBoardService.instance().findSDFPerProfile();
			findSDFPerRelationship = DashBoardService.instance().findSDFPerRelationship();

			List<Long> processes = new ArrayList<Long>();
			DashBoardService.instance().processUsersDepartment(getSessionUI().getActiveUser(), processes);
			if (processes.size() > 0) {
				String inclause = GenericUtility.initNumericSqlInClause(processes.toString());
				this.openTasks = DashBoardService.instance().countTasks(TaskStatusEnum.NotStarted, inclause);
				this.underwayTasks = DashBoardService.instance().countTasks(TaskStatusEnum.Underway, inclause);
				this.completedTasks = DashBoardService.instance().completedTaskForThisMonth(inclause);
			}

			departments = DashBoardService.instance().processGraphDataPerManagerDepartment(getSessionUI().getActiveUser());
			String dm = "'#name#',";
			List<String> process = new ArrayList<>();
			pr = "[";
			// [3, 4, 4, 2]
			openData = "[";
			underwayData = "[";
			completedData = "[";
			boolean ft = true;
			for (HostingCompanyDepartments hcd : departments) {
				if (ft) {
					heading = "'Task per process for department " + hcd.getDepartment().trim() + "'";
					ft = false;
				}
				for (HostingCompanyDepartmentProcess proc : hcd.getHostingCompanyDepartmentProcesses()) {
					if (!process.contains(proc.getHostingCompanyProcess().getWorkflowProcess().getRegistrationName().trim())) {
						pr = pr + (dm.replace("#name#", super.getEntryLanguage(proc.getHostingCompanyProcess().getWorkflowProcess().getRegistrationName().trim())));
						openData = openData + proc.getOpenTasks() + ",";
						underwayData = underwayData + proc.getUnderwayTasks() + ",";
						completedData = completedData + proc.getCompletedTasks() + ",";
						process.add(proc.getHostingCompanyProcess().getWorkflowProcess().getRegistrationName().trim());
					}
				}

			}
			if (pr.lastIndexOf(',') > -1) pr = pr.substring(0, pr.lastIndexOf(',')) + "]";
			if (openData.lastIndexOf(',') > -1) openData = openData.substring(0, openData.lastIndexOf(',')) + "]";
			if (underwayData.lastIndexOf(',') > -1) underwayData = underwayData.substring(0, underwayData.lastIndexOf(',')) + "]";
			if (completedData.lastIndexOf(',') > -1) completedData = completedData.substring(0, completedData.lastIndexOf(',')) + "]";

			/*
			 * departments.forEach(a->{ System.out.println(a.getDepartment());
			 * a.getHostingCompanyDepartmentProcesses().forEach(b->{
			 * System.out.println("\t"+b.getHostingCompanyProcess().getWorkflowProcess()
			 * +" openTask:"+b.getOpenTasks() + " underway:" + b.getUnderwayTasks() +
			 * " completed:" + b.getCompletedTasks()); }); });
			 */

			this.provData = DashBoardService.instance().copmanyPerProvData(true);
			leviesByChamber = null;
			leviesBySize = null;
			leviesByChamberAndSize = null;
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
						retorno = wspSignOffService.findCompletedSignOffsByFinYear(first, pageSize, sortField, sortOrder, filters, selectedFinYear);
						wspsByFinYear.setRowCount(wspSignOffService.countCompletedSignOffsByFinYear(filters));
					} else {
						/* Fail Safe */
						retorno = wspSignOffService.findCompletedSignOffs(first, pageSize, sortField, sortOrder, filters);
						wspsByFinYear.setRowCount(wspSignOffService.count(filters));
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
						retorno = sdfCompanyService.findNotCompletedWspByFinYear(SDFCompany.class, first, pageSize, sortField, sortOrder, filters, selectedFinYear);
						wsps2ByFinYear.setRowCount(sdfCompanyService.countNotCompletedWspByFinYear(filters, selectedFinYear).intValue());	
					}else {
						/* Fail Safe */
						retorno = sdfCompanyService.findNotCompletedWsp(SDFCompany.class, first, pageSize, sortField, sortOrder, filters);
						wsps2ByFinYear.setRowCount(sdfCompanyService.countNotCompletedWsp(filters).intValue());	
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

	private void wspInfo() {
		// wsps = wspService.findCompletedSignOffs();
		wsps = new LazyDataModel<WspSignoff>() {
			private static final long serialVersionUID = 1L;
			private List<WspSignoff> retorno = new ArrayList<WspSignoff>();

			@Override
			public List<WspSignoff> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = wspSignOffService.findCompletedSignOffs(first, pageSize, sortField, sortOrder, filters);
					wsps.setRowCount(wspSignOffService.count(filters));
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
		wsps2 = new LazyDataModel<SDFCompany>() {
			private static final long serialVersionUID = 1L;
			private List<SDFCompany> retorno = new ArrayList<SDFCompany>();

			@Override
			public List<SDFCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = sdfCompanyService.findNotCompletedWsp(SDFCompany.class, first, pageSize, sortField, sortOrder, filters);
					wsps2.setRowCount(sdfCompanyService.countNotCompletedWsp(filters).intValue());
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

	/**
	 * Gets the pending companies.
	 *
	 * @return the pending companies
	 */
	public Long getPendingCompanies() {
		return pendingCompanies;
	}

	/**
	 * Sets the pending companies.
	 *
	 * @param pendingCompanies
	 *            the new pending companies
	 */
	public void setPendingCompanies(Long pendingCompanies) {
		this.pendingCompanies = pendingCompanies;
	}

	/**
	 * Gets the active companies.
	 *
	 * @return the active companies
	 */
	public Long getActiveCompanies() {
		return activeCompanies;
	}

	/**
	 * Sets the active companies.
	 *
	 * @param activeCompanies
	 *            the new active companies
	 */
	public void setActiveCompanies(Long activeCompanies) {
		this.activeCompanies = activeCompanies;
	}

	/**
	 * Gets the in active.
	 *
	 * @return the in active
	 */
	public Long getInActive() {
		return inActive;
	}

	/**
	 * Sets the in active.
	 *
	 * @param inActive
	 *            the new in active
	 */
	public void setInActive(Long inActive) {
		this.inActive = inActive;
	}

	/**
	 * Gets the total companies.
	 *
	 * @return the total companies
	 */
	public Long getTotalCompanies() {
		return totalCompanies;
	}

	/**
	 * Sets the total companies.
	 *
	 * @param totalCompanies
	 *            the new total companies
	 */
	public void setTotalCompanies(Long totalCompanies) {
		this.totalCompanies = totalCompanies;
	}

	public List<MISReportBean> getFindEmpPerProv() {

		return findEmpPerProv;
	}

	public void setFindEmpPerProv(List<MISReportBean> findEmpPerProv) {
		this.findEmpPerProv = findEmpPerProv;
	}

	public List<MISReportBean> getFindEmpPerCity() {
		return findEmpPerCity;
	}

	public void setFindEmpPerCity(List<MISReportBean> findEmpPerCity) {
		this.findEmpPerCity = findEmpPerCity;
	}

	public List<MISReportBean> getFindSDFPerEmploy() {
		return findSDFPerEmploy;
	}

	public void setFindSDFPerEmploy(List<MISReportBean> findSDFPerEmploy) {
		this.findSDFPerEmploy = findSDFPerEmploy;
	}

	public Long getRejected() {
		return rejected;
	}

	public void setRejected(Long rejected) {
		this.rejected = rejected;
	}

	public Long getApproved() {
		return approved;
	}

	public void setApproved(Long approved) {
		this.approved = approved;
	}

	public String getProvData() {
		return provData;
	}

	public void setProvData(String provData) {
		this.provData = provData;
	}

	public List<MISReportBean> getFindSDFPerArea() {
		return findSDFPerArea;
	}

	public void setFindSDFPerArea(List<MISReportBean> findSDFPerArea) {
		this.findSDFPerArea = findSDFPerArea;
	}

	public List<MISReportBean> getFindSDFPerProvince() {
		return findSDFPerProvince;
	}

	public void setFindSDFPerProvince(List<MISReportBean> findSDFPerProvince) {
		this.findSDFPerProvince = findSDFPerProvince;
	}

	public List<MISReportBean> getFindSDFPerProfile() {
		return findSDFPerProfile;
	}

	public void setFindSDFPerProfile(List<MISReportBean> findSDFPerProfile) {
		this.findSDFPerProfile = findSDFPerProfile;
	}

	public List<MISReportBean> getFindSDFPerRelationship() {
		return findSDFPerRelationship;
	}

	public void setFindSDFPerRelationship(List<MISReportBean> findSDFPerRelationship) {
		this.findSDFPerRelationship = findSDFPerRelationship;
	}

	public List<MISReportBean> getLeviesByChamberAndSize() {
		return leviesByChamberAndSize;
	}

	public void setLeviesByChamberAndSize(List<MISReportBean> leviesByChamberAndSize) {
		this.leviesByChamberAndSize = leviesByChamberAndSize;
	}

	public List<MISReportBean> getLeviesByChamber() {
		return leviesByChamber;
	}

	public void setLeviesByChamber(List<MISReportBean> leviesByChamber) {
		this.leviesByChamber = leviesByChamber;
	}

	public List<MISReportBean> getLeviesBySize() {
		return leviesBySize;
	}

	public void setLeviesBySize(List<MISReportBean> leviesBySize) {
		this.leviesBySize = leviesBySize;
	}

	public List<MISReportBean> getRegCompaniesVsSarsCompanies() {
		return regCompaniesVsSarsCompanies;
	}

	public void setRegCompaniesVsSarsCompanies(List<MISReportBean> regCompaniesVsSarsCompanies) {
		this.regCompaniesVsSarsCompanies = regCompaniesVsSarsCompanies;
	}

	public List<WSPReport> getWspReports() {
		return wspReports;
	}

	public void setWspReports(List<WSPReport> wspReports) {
		this.wspReports = wspReports;
	}

	public LazyDataModel<WspSignoff> getWsps() {
		return wsps;
	}

	public void setWsps(LazyDataModel<WspSignoff> wsps) {
		this.wsps = wsps;
	}

	public List<WSPReport> getWspReports2() {
		return wspReports2;
	}

	public void setWspReports2(List<WSPReport> wspReports2) {
		this.wspReports2 = wspReports2;
	}

	public LazyDataModel<SDFCompany> getWsps2() {
		return wsps2;
	}

	public void setWsps2(LazyDataModel<SDFCompany> wsps2) {
		this.wsps2 = wsps2;
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

	public LazyDataModel<WspSignoff> getWspsByFinYear() {
		return wspsByFinYear;
	}

	public void setWspsByFinYear(LazyDataModel<WspSignoff> wspsByFinYear) {
		this.wspsByFinYear = wspsByFinYear;
	}

	public int getSelectedFinYear() {
		return selectedFinYear;
	}

	public void setSelectedFinYear(int selectedFinYear) {
		this.selectedFinYear = selectedFinYear;
	}

	public void setSelectedFinYear(Integer selectedFinYear) {
		this.selectedFinYear = selectedFinYear;
	}

	public LazyDataModel<SDFCompany> getWsps2ByFinYear() {
		return wsps2ByFinYear;
	}

	public void setWsps2ByFinYear(LazyDataModel<SDFCompany> wsps2ByFinYear) {
		this.wsps2ByFinYear = wsps2ByFinYear;
	}

	public List<Integer> getFinYearByGrantList() {
		return finYearByGrantList;
	}

	public void setFinYearByGrantList(List<Integer> finYearByGrantList) {
		this.finYearByGrantList = finYearByGrantList;
	}
}
