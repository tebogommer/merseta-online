package haj.com.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import haj.com.bean.MISReportBean;
import haj.com.bean.WSPReport;
import haj.com.dao.DashBoardDAO;
import haj.com.dao.MISReportDataDAO;
import haj.com.entity.HostingCompanyDepartmentProcess;
import haj.com.entity.HostingCompanyDepartments;
import haj.com.entity.HostingCompanyProcess;
import haj.com.entity.Users;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.TaskStatusEnum;
import haj.com.framework.AbstractService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class DashBoardService.
 */
public class DashBoardService extends AbstractService {

	/** The dao. */
	private DashBoardDAO dao = new DashBoardDAO();

	private MISReportDataDAO dao2 = new MISReportDataDAO();

	/** The hosting company departments service. */
	private HostingCompanyDepartmentsService hostingCompanyDepartmentsService = new HostingCompanyDepartmentsService();

	/** The hosting company process service. */
	private HostingCompanyProcessService hostingCompanyProcessService = new HostingCompanyProcessService();

	/** The hosting company department process service. */
	private HostingCompanyDepartmentProcessService hostingCompanyDepartmentProcessService = new HostingCompanyDepartmentProcessService();

	/** The service. */
	private static DashBoardService service = null;

	/**
	 * Instance.
	 *
	 * @return the dash board service
	 */
	public static synchronized DashBoardService instance() {
		if (service == null) {
			service = new DashBoardService();
		}
		return service;
	}

	/**
	 * Count task for user.
	 *
	 * @param status
	 *            the status
	 * @param user
	 *            the user
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long countTaskForUser(TaskStatusEnum status, Users user) throws Exception {
		return dao.countTaskByStatusAndUser(status, user.getId());
	}

	/**
	 * Count tasks.
	 *
	 * @param status
	 *            the status
	 * @param inclause
	 *            the inclause
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long countTasks(TaskStatusEnum status, String inclause) throws Exception {
		return dao.countTaskByStatus(status, inclause);
	}

	public Long countTasks(TaskStatusEnum status, boolean employee) throws Exception {
		return dao.countTaskByStatus(status, employee);
	}

	/**
	 * Completed task for this month for user.
	 *
	 * @param user
	 *            the user
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long completedTaskForThisMonthForUser(Users user) throws Exception {
		Date date = new Date();
		return countTaskByStatusAndUserBetweenDates(TaskStatusEnum.Completed, user, GenericUtility.getFirstDayOfMonth(date), GenericUtility.getLasttDayOfMonth(date));
	}

	/**
	 * Completed task for this month.
	 *
	 * @param inclause
	 *            the inclause
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long completedTaskForThisMonth(String inclause) throws Exception {
		Date date = new Date();
		return dao.countTaskByStatusBetweenDates(TaskStatusEnum.Completed, inclause, GenericUtility.getFirstDayOfMonth(date), GenericUtility.getLasttDayOfMonth(date));
	}

	/**
	 * Count task by status and user between dates.
	 *
	 * @param status
	 *            the status
	 * @param user
	 *            the user
	 * @param fromDate
	 *            the from date
	 * @param toDate
	 *            the to date
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long countTaskByStatusAndUserBetweenDates(TaskStatusEnum status, Users user, Date fromDate, Date toDate) throws Exception {
		return dao.countTaskByStatusAndUserBetweenDates(status, user.getId(), fromDate, toDate);
	}

	/**
	 * Process graph data per manager department.
	 *
	 * @param user
	 *            the user
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<HostingCompanyDepartments> processGraphDataPerManagerDepartment(Users user) throws Exception {
		List<HostingCompanyDepartments> departments = hostingCompanyDepartmentsService.findUsersDepartment(user);
		List<HostingCompanyDepartments> temp = new ArrayList<HostingCompanyDepartments>();
		temp.addAll(departments);
		for (HostingCompanyDepartments hcd : departments) {

			hcd.setHostingCompanyDepartmentProcesses(hostingCompanyDepartmentProcessService.findByDepartment(hcd));
			for (HostingCompanyDepartmentProcess hostingCompanyDepartmentProcess : hcd.getHostingCompanyDepartmentProcesses()) {
				List<Long> processes = new ArrayList<Long>();
				processes.add(hostingCompanyDepartmentProcess.getHostingCompanyProcess().getId());
				findRecursive(hostingCompanyDepartmentProcess.getHostingCompanyProcess(), processes);
				String inclause = GenericUtility.initNumericSqlInClause(processes.toString());
				hostingCompanyDepartmentProcess.setOpenTasks(countTasks(TaskStatusEnum.NotStarted, inclause));
				hostingCompanyDepartmentProcess.setUnderwayTasks(countTasks(TaskStatusEnum.Underway, inclause));
				hostingCompanyDepartmentProcess.setCompletedTasks(countTasks(TaskStatusEnum.Completed, inclause));
			}
			processChildDepartments(departments, temp, hcd);
		}
		return temp;
	}

	/**
	 * Process child departments.
	 *
	 * @param departments
	 *            the departments
	 * @param temp
	 *            the temp
	 * @param parentDepartments
	 *            the parent departments
	 * @throws Exception
	 *             the exception
	 */
	private void processChildDepartments(List<HostingCompanyDepartments> departments, List<HostingCompanyDepartments> temp, HostingCompanyDepartments parentDepartments) throws Exception {
		List<HostingCompanyDepartments> children = hostingCompanyDepartmentsService.findByParent(parentDepartments);
		for (HostingCompanyDepartments hcd : children) {
			hcd.setHostingCompanyDepartmentProcesses(hostingCompanyDepartmentProcessService.findByDepartment(hcd));
			for (HostingCompanyDepartmentProcess hostingCompanyDepartmentProcess : hcd.getHostingCompanyDepartmentProcesses()) {
				List<Long> processes = new ArrayList<Long>();
				processes.add(hostingCompanyDepartmentProcess.getHostingCompanyProcess().getId());
				findRecursive(hostingCompanyDepartmentProcess.getHostingCompanyProcess(), processes);
				String inclause = GenericUtility.initNumericSqlInClause(processes.toString());
				hostingCompanyDepartmentProcess.setOpenTasks(countTasks(TaskStatusEnum.NotStarted, inclause));
				hostingCompanyDepartmentProcess.setUnderwayTasks(countTasks(TaskStatusEnum.Underway, inclause));
				hostingCompanyDepartmentProcess.setCompletedTasks(countTasks(TaskStatusEnum.Completed, inclause));
			}
			temp.add(hcd);
			if (hostingCompanyDepartmentsService.findByParent(hcd).size() > 0) {
				processChildDepartments(departments, temp, hcd);
			}
		}

	}

	/**
	 * Process users department.
	 *
	 * @param user
	 *            the user
	 * @param processes
	 *            the processes
	 * @throws Exception
	 *             the exception
	 */
	public void processUsersDepartment(Users user, List<Long> processes) throws Exception {
		List<HostingCompanyDepartments> departments = hostingCompanyDepartmentsService.findUsersDepartment(user);
		for (HostingCompanyDepartments hcd : departments) {
			processDepartment(hcd, processes);
			buildDepartmentTree(hcd, processes);
		}
	}

	/**
	 * Builds the department tree.
	 *
	 * @param parentDepartments
	 *            the parent departments
	 * @param processes
	 *            the processes
	 * @throws Exception
	 *             the exception
	 */
	private void buildDepartmentTree(HostingCompanyDepartments parentDepartments, List<Long> processes) throws Exception {
		List<HostingCompanyDepartments> departments = hostingCompanyDepartmentsService.findByParent(parentDepartments);
		for (HostingCompanyDepartments hcd : departments) {
			processDepartment(hcd, processes);
			if (hostingCompanyDepartmentsService.findByParent(hcd).size() > 0) {
				buildDepartmentTree(hcd, processes);
			}
		}

	}

	/**
	 * Process department.
	 *
	 * @param hostingCompanyDepartments
	 *            the hosting company departments
	 * @param processes
	 *            the processes
	 */
	public void processDepartment(HostingCompanyDepartments hostingCompanyDepartments, List<Long> processes) {

		try {
			List<HostingCompanyDepartmentProcess> hostingCompanyDepartmentProcesses = hostingCompanyDepartmentProcessService.findByDepartment(hostingCompanyDepartments);
			for (HostingCompanyDepartmentProcess hostingCompanyDepartmentProcess : hostingCompanyDepartmentProcesses) {
				processes.add(hostingCompanyDepartmentProcess.getHostingCompanyProcess().getId());
				findRecursive(hostingCompanyDepartmentProcess.getHostingCompanyProcess(), processes);
			}
		} catch (Exception e) {
			logger.fatal(e);
		}
	}

	/**
	 * Find recursive.
	 *
	 * @param hostingCompanyProcess
	 *            the hosting company process
	 * @param processes
	 *            the processes
	 * @throws Exception
	 *             the exception
	 */
	public void findRecursive(HostingCompanyProcess hostingCompanyProcess, List<Long> processes) throws Exception {
		hostingCompanyProcess = hostingCompanyProcessService.findByKey(hostingCompanyProcess.getId());
		if (hostingCompanyProcess.getNextProcess() != null) {
			processes.add(hostingCompanyProcess.getNextProcess().getId());
			findRecursive(hostingCompanyProcess.getNextProcess(), processes);
		}
	}

	/**
	 * Count companies.
	 *
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long countCompanies() throws Exception {
		return dao.countCompanies();
	}

	public List<WSPReport> countWSP() throws Exception {
		return dao.countWSP();
	}
	
	public List<WSPReport> countWSPByFinYear(Integer finYear) throws Exception {
		return dao.countWSPByFinYear(finYear);
	}
	
	public List<WSPReport> grantStatusReportByCompanySizes(Integer finYear) throws Exception {
		return dao.grantStatusReportByCompanySizes(finYear);
	}
	
	public List<WSPReport> generateReportGrantStatusReportByCompanySizes(Integer finYear) throws Exception {
		
		List<WSPReport> report = grantStatusReportByCompanySizes(finYear);
		
		// Total for APP target purpose - 1+2+3
		WSPReport totalApp = new WSPReport("Total for APP target purpose");
		
		// Grand total of all submissions – 1+2+3+4+5+6
		WSPReport totalForEntries = new WSPReport("Grand total of all submissions");
		
		for (WSPReport entry : report) {
			
			// sets total for row
			entry.setTotal(entry.getSmallCount().add(entry.getMidCount()));
			entry.setTotal(entry.getTotal().add(entry.getLargeCount()));
			
			/*
			 *  Calculating Total for APP (report number 1, 2 and 3)
			 */
			
			if (entry.getReportNumber() != null && ( entry.getReportNumber().equals(BigInteger.valueOf(1l)) || entry.getReportNumber().equals(BigInteger.valueOf(2l)) || entry.getReportNumber().equals(BigInteger.valueOf(3l)) ) ) {
				// small companies
				totalApp.setSmallCount(totalApp.getSmallCount().add((entry.getSmallCount() == null) ? BigInteger.valueOf(0l) : entry.getSmallCount()));
				// small companies
				totalApp.setMidCount(totalApp.getMidCount().add((entry.getMidCount() == null) ? BigInteger.valueOf(0l) : entry.getMidCount()));
				// small companies
				totalApp.setLargeCount(totalApp.getLargeCount().add((entry.getLargeCount() == null) ? BigInteger.valueOf(0l) : entry.getLargeCount()));
				// sets total for row
				totalApp.setTotal(totalApp.getTotal().add((entry.getTotal() == null) ? BigInteger.valueOf(0l) : entry.getTotal()));
			}
			
			/*
			 *  Calculating grand total of all submissions – 1+2+3+4+5+6
			 */
			
			// small companies
			totalForEntries.setSmallCount(totalApp.getSmallCount().add((entry.getSmallCount() == null) ? BigInteger.valueOf(0l) : entry.getSmallCount()));
			// small companies
			totalForEntries.setMidCount(totalApp.getMidCount().add((entry.getMidCount() == null) ? BigInteger.valueOf(0l) : entry.getMidCount()));
			// small companies
			totalForEntries.setLargeCount(totalApp.getLargeCount().add((entry.getLargeCount() == null) ? BigInteger.valueOf(0l) : entry.getLargeCount()));
			// sets total for row
			totalForEntries.setTotal(totalForEntries.getTotal().add(entry.getTotal()));
		}
		report.add(totalApp);
		report.add(totalForEntries);
		
		return report;
	}
	
	public List<WSPReport> countWSP2() throws Exception {
		return dao.countWSP2();
	}
	
	public List<WSPReport> countWSP2ByFinYear(Integer finYear) throws Exception {
		return dao.countWSP2ByFinYear(finYear);
	}
	
	public List<Integer> allDistinctFinYearsLastestThreeYears() throws Exception {
		return dao.allDistinctFinYearsLastestThreeYears();
	}
	/**
	 *
	 */
	public Long countCompanies(CompanyStatusEnum status) throws Exception {
		return dao.countCompanies(status);
	}

	public List<MISReportBean> findEmpPerProv() throws Exception {
		return findApprovedOnes(dao2.findEmpPerProv());
	}

	public List<MISReportBean> findEmpPerProvNonLevyPaying() throws Exception {
		return findApprovedOnesNonLevyPaying(dao2.findEmpPerProvNonLevyPaying());
	}

	public List<MISReportBean> findEmpPerCity() throws Exception {
		return dao2.findEmpPerCity();
	}

	public List<MISReportBean> findSDFPerEmploy() throws Exception {
		return dao2.findSDFPerEmploy();
	}

	public List<MISReportBean> findEmpPerProvCode() throws Exception {
		return dao2.findEmpPerProvCode();
	}

	public List<MISReportBean> findEmpPerProvCodeNonLevyPaying() throws Exception {
		return dao2.findEmpPerProvCodeNonLevyPaying();
	}

	private List<MISReportBean> findApprovedOnes(List<MISReportBean> list) throws Exception {
		List<MISReportBean> l = findEmpPerProvCodeApproved();
		list.forEach(a -> {
			if (l.contains(a)) {
				a.setCount2(l.get(l.indexOf(a)).getCount());
			} else {
				a.setCount2(0l);
			}
		});
		return list;
	}

	private List<MISReportBean> findApprovedOnesNonLevyPaying(List<MISReportBean> list) throws Exception {
		List<MISReportBean> l = findEmpPerProvCodeApprovedNonLevyPaying();
		list.forEach(a -> {
			if (l.contains(a)) {
				a.setCount2(l.get(l.indexOf(a)).getCount());
			} else {
				a.setCount2(0l);
			}
		});
		return list;
	}

	public List<MISReportBean> findEmpPerProvCodeApproved() throws Exception {
		return dao2.findEmpPerProvCodeApproved();
	}

	public List<MISReportBean> findEmpPerProvCodeApprovedNonLevyPaying() throws Exception {
		return dao2.findEmpPerProvCodeApprovedNonLevyPaying();
	}

	public List<MISReportBean> findSDFPerArea() throws Exception {
		return dao2.findSDFPerArea();
	}

	public List<MISReportBean> findSDFPerProvince() throws Exception {
		return dao2.findSDFPerProvince();
	}

	public List<MISReportBean> findSDFPerProfile() throws Exception {
		return dao2.findSDFPerProfile();
	}

	public List<MISReportBean> findSDFPerRelationship() throws Exception {
		return dao2.findSDFPerRelationship();
	}

	public List<MISReportBean> sdfBiographical() throws Exception {
		return dao2.sdfBiographical();
	}

	/*
	 * 
	 * ['za-wc', 13],
	 * 
	 * ['za-mp', 18]
	 * 
	 */

	public String copmanyPerProvData(boolean levyPaying) throws Exception {
		String data = "";
		List<MISReportBean> l = findEmpPerProvCode();
		if (levyPaying) {
			l = findEmpPerProvCode();
		} else {
			l = findEmpPerProvCodeNonLevyPaying();
		}
		for (MISReportBean m : l) {
			switch (m.getDescription().trim()) {
				case "GP":
					data = data + "['za-gt'," + m.getCount() + "],";
					break;
				case "EC":
					data = data + "['za-ec'," + m.getCount() + "],";
					break;

				case "FS":
					data = data + "['za-fs'," + m.getCount() + "],";
					break;
				case "KZN":
					data = data + "['za-nl'," + m.getCount() + "],";
					break;
				case "LP":
					data = data + "['za-np'," + m.getCount() + "],";
					break;
				case "MP":
					data = data + "['za-mp'," + m.getCount() + "],";
					break;
				case "NC":
					data = data + "['za-nc'," + m.getCount() + "],";
					break;
				case "NW":
					data = data + "['za-nw'," + m.getCount() + "],";
					break;
				case "WC":
					data = data + "['za-wc'," + m.getCount() + "],";
					break;

				default:
					break;
			}
		}

		if (data.indexOf(',') > -1) {
			data = data.substring(0, data.lastIndexOf(','));
		}
		return data;

	}

	/**
	 * @return Double
	 * @throws Exception
	 */
	public Double avgTimeToCompleteTasksEmployees() throws Exception {
		return dao.avgTimeToCompleteTasksEmployees();
	}

	/**
	 * @return Double
	 * @throws Exception
	 */
	public Double avgTimeToActionTasksEmployees() throws Exception {
		return dao.avgTimeToActionTasksEmployees();
	}

	/**
	 * @return Double
	 * @throws Exception
	 */
	public Double avgTimeToCompleteTasksExternal() throws Exception {
		return dao.avgTimeToCompleteTasksExternal();
	}

	/**
	 * @return Double
	 * @throws Exception
	 */
	public Double avgTimeToActionTasksExternal() throws Exception {
		return dao.avgTimeToActionTasksExternal();
	}

	public List<MISReportBean> countByBusinessProcess() throws Exception {
		return dao2.countByBusinessProcess();
	}

	public List<MISReportBean> usersByType() throws Exception {
		return dao2.usersByType();
	}

	public Long countContacts() throws Exception {
		return dao2.countContacts();
	}

	public List<MISReportBean> countContactsByAvtiveInactive() throws Exception {
		return dao2.countContactsByAvtiveInactive();
	}

	public List<MISReportBean> totalLevyContributionOfTheCompaniesThatHaveBeenRegisteredOnTheNsdmsBySizeOfCompanyAndChamber() throws Exception {
		return doTotal(dao2.totalLevyContributionOfTheCompaniesThatHaveBeenRegisteredOnTheNsdmsBySizeOfCompanyAndChamber());
	}

	public List<MISReportBean> totalLevyContributionOfTheCompaniesThatHaveBeenRegisteredOnTheNsdmsByChamber() throws Exception {
		return doTotal(dao2.totalLevyContributionOfTheCompaniesThatHaveBeenRegisteredOnTheNsdmsByChamber());
	}

	public List<MISReportBean> totalLevyContributionOfTheCompaniesThatHaveBeenRegisteredOnTheNsdmsBySizeOfCompany() throws Exception {
		return doTotal(dao2.totalLevyContributionOfTheCompaniesThatHaveBeenRegisteredOnTheNsdmsBySizeOfCompany());
	}

	private List<MISReportBean> doTotal(List<MISReportBean> list) {
		if (list != null && list.size() > 0) {
			Long count = 0l;
			Long count2 = 0l;
			Double total = 0.0;
			for (MISReportBean a : list) {
				count += (a.getCount() == null ? 0l : a.getCount());
				count2 += (a.getCount2() == null ? 0l : a.getCount2());
				total += (a.getTotal() == null ? 0.0 : a.getTotal());
			}
			list.add(new MISReportBean("Total", count, total, count2));
		}
		return list;
	}

	public List<MISReportBean> totalNumberOfCompaniesRegisteredOnNsdmsInRelationToTotalLevyPayingCompaniesByChamberSARS() throws Exception {
		return dao2.totalNumberOfCompaniesRegisteredOnNsdmsInRelationToTotalLevyPayingCompaniesByChamberSARS();
	}

	public List<MISReportBean> totalNumberOfCompaniesRegisteredOnNsdmsInRelationToTotalLevyPayingCompaniesByChamber() throws Exception {
		List<MISReportBean> comps = dao2.totalNumberOfCompaniesRegisteredOnNsdmsInRelationToTotalLevyPayingCompaniesByChamber();
		List<MISReportBean> sars = totalNumberOfCompaniesRegisteredOnNsdmsInRelationToTotalLevyPayingCompaniesByChamberSARS();
		sars.forEach(a -> {
			if (comps.contains(a)) {
				a.setCount2((comps.get(comps.indexOf(a))).getCount());
			}
		});

		return doTotal(sars);
	}

}
