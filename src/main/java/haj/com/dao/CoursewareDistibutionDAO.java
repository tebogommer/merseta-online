package haj.com.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.CoursewareDistibution;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.Modules;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class CoursewareDistibutionDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all CoursewareDistibution
	 * 
	 * @author TechFinium
	 * @see CoursewareDistibution
	 * @return a list of {@link haj.com.entity.CoursewareDistibution}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<CoursewareDistibution> allCoursewareDistibution() throws Exception {
		return (List<CoursewareDistibution>) super.getList("select o from CoursewareDistibution o");
	}

	/**
	 * Get all CoursewareDistibution between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see CoursewareDistibution
	 * @return a list of {@link haj.com.entity.CoursewareDistibution}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<CoursewareDistibution> allCoursewareDistibution(Long from, int noRows) throws Exception {
		String hql = "select o from CoursewareDistibution o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<CoursewareDistibution>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	@SuppressWarnings("unchecked")
	public List<CoursewareDistibution> allCompanyDashboard(Class<CoursewareDistibution> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company company) throws Exception {
		String hql = "select o from CoursewareDistibution o where o.company.id = :companyID and o.approvalEnum = :status";
		filters.put("companyID", company.getId());
		filters.put("status", ApprovalEnum.Approved);
		return (List<CoursewareDistibution>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	

	public int countCompanyDashboard(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CoursewareDistibution o where o.company.id = :companyID and o.approvalEnum = :status";
		return countWhere(filters, hql);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CoursewareDistibution> allCompanyCourseware(Class<CoursewareDistibution> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from CoursewareDistibution o where o.company.id = :companyID";
		return (List<CoursewareDistibution>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<CoursewareDistibution> coursewareDistibutionbyYearAndMonth(Class<CoursewareDistibution> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String searchType) throws Exception {
		
		String hql = "select o from CoursewareDistibution o where month(createDate) =:month and year(createDate) =:year";
		if(searchType.equalsIgnoreCase("MON")){
			hql = "select o from CoursewareDistibution o where month(createDate) =:month";
		}
		else if(searchType.equalsIgnoreCase("YR")){
			hql = "select o from CoursewareDistibution o where year(createDate) =:year";
		}
		return (List<CoursewareDistibution>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countCoursewareDistibutionbyYearAndMonth(Map<String, Object> filters, String searchType) throws Exception {
		String hql = "select count(o) from CoursewareDistibution o where month(createDate) =:month and year(createDate) =:year";
		if(searchType.equalsIgnoreCase("MON")){
			hql = "select count(o) from CoursewareDistibution o where month(createDate) =:month";
		}
		else if(searchType.equalsIgnoreCase("YR")){
			hql = "select count(o) from CoursewareDistibution o where year(createDate) =:year";
		}
		
		return countWhere(filters, hql);
	}
	

	public int countAllCompanyCourseware(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CoursewareDistibution o where o.company.id = :companyID";
		return countWhere(filters, hql);
	}


	@SuppressWarnings("unchecked")
	public List<CoursewareDistibution> allCompany(Class<CoursewareDistibution> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company company) throws Exception {
		String hql = "select o from CoursewareDistibution o where o.company.id = :companyID and o.approvalEnum <> :status";
		filters.put("companyID", company.getId());
		filters.put("status", ApprovalEnum.Rejected);
		return (List<CoursewareDistibution>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<Modules> coursewareDistibutionModules(Class<Modules> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select distinct(o.modules) from CoursewareDistibution o where o.id is not null";
		return (List<Modules>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countCoursewareModules(Map<String, Object> filters) throws Exception {
		String hql = "select count(distinct o.modules) from CoursewareDistibution o where o.id is not null";
		return countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<Company> coursewareDistibutionReportByCompany(Class<CoursewareDistibution> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select distinct(o.company) from CoursewareDistibution o where o.id is not null";
		return (List<Company>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countCoursewareByCompany(Map<String, Object> filters) throws Exception {
		String hql = "select count(distinct o.company) from CoursewareDistibution o where o.id is not null";
		return countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<CoursewareDistibution> allCompanyByStatus(Class<CoursewareDistibution> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company company, ApprovalEnum status) throws Exception {
		String hql = "select o from CoursewareDistibution o where o.company.id = :companyID and o.approvalEnum = :status";
		filters.put("companyID", company.getId());
		filters.put("status", status);
		return (List<CoursewareDistibution>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<CoursewareDistibution> allCoursewareDistibutionAprvedNotApproved(Class<CoursewareDistibution> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company company) throws Exception {
		String hql = "select o from CoursewareDistibution o where o.company.id = :companyID and o.approvalEnum <> :status";
		filters.put("companyID", company.getId());
		filters.put("status", ApprovalEnum.Rejected);
		return (List<CoursewareDistibution>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<CoursewareDistibution> allCoursewareDistibutionForReport(Class<CoursewareDistibution> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from CoursewareDistibution o";
		return (List<CoursewareDistibution>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	
	public int countCompanyReject(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CoursewareDistibution o where o.company.id = :companyID and o.approvalEnum = :status";
		return countWhere(filters, hql);
	}
	
	public int countAll(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CoursewareDistibution o";
		return countWhere(filters, hql);
	}
	
	public int countCompany(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CoursewareDistibution o where o.company.id = :companyID and o.approvalEnum <> :status";
		return countWhere(filters, hql);
	}


	@SuppressWarnings("unchecked")
	public List<CoursewareDistibution> allCoursewareByUser(Class<CoursewareDistibution> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users user, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
//		String hql = "select o from CompanyUsers o" + leftJoinsNoVar + "where o.company.id = :companyId and o.companyUserType = :configDocProcessEnum";
		String hql = "select o from CoursewareDistibution o where o.company.id in (select x.company.id from CompanyUsers x where x.user.id = :userid and x.companyUserType = :configDocProcessEnum)";
		filters.put("userid", user.getId());
		filters.put("configDocProcessEnum", configDocProcessEnum);
		return (List<CoursewareDistibution>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	

	public int countAllCoursewareByUser(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CoursewareDistibution o where o.company.id in (select x.company.id from CompanyUsers x where x.user.id = :userid and x.companyUserType = :configDocProcessEnum)";
		return countWhere(filters, hql);
	}
	

	@SuppressWarnings("unchecked")
	public List<CoursewareDistibution> allCompany(Company company) throws Exception {
		String hql = "select o from CoursewareDistibution o where o.company.id = :companyID and o.approvalEnum <> :status";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("status", ApprovalEnum.Rejected);
		return (List<CoursewareDistibution>) super.getList(hql, parameters);
	}


	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see CoursewareDistibution
	 * @return a {@link haj.com.entity.CoursewareDistibution}
	 * @throws Exception
	 *             global exception
	 */
	public CoursewareDistibution findByKey(Long id) throws Exception {
		String hql = "select o from CoursewareDistibution o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (CoursewareDistibution) super.getUniqueResult(hql, parameters);
	}
	
	public CoursewareDistibution findByCompanyAndModule(Long companyID, Long moduleID) throws Exception {
		String hql = "select o from CoursewareDistibution o where o.company.id = :companyID AND  o.modules.id = :moduleID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", companyID);
		parameters.put("moduleID", moduleID);
		return (CoursewareDistibution) super.getUniqueResult(hql, parameters);
	}
	
	

	/**
	 * Find CoursewareDistibution by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see CoursewareDistibution
	 * @return a list of {@link haj.com.entity.CoursewareDistibution}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<CoursewareDistibution> findByName(String description) throws Exception {
		String hql = "select o from CoursewareDistibution o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<CoursewareDistibution>) super.getList(hql, parameters);
	}
	
	/** Reporting Start */
	
	/**
	 * Results count of results based on provided search criteria 
	 * @param dateFilter
	 * @param fromDate
	 * @param toDate
	 * @param filterByStatus
	 * @param statusSelectedList
	 * @return int the count of results
	 * @throws Exception
	 */
	public int checkForResultsForFilterReport(boolean dateFilter, Date fromDate, Date toDate, boolean filterByStatus, List<ApprovalEnum> statusSelectedList) throws Exception {
		String hql = "select count(o) from CoursewareDistibution o ";
		
		Map<String, Object> parameters = new Hashtable<String, Object>();
		if (dateFilter) {
			hql += " where date(o.createDate) between date(:fromDate) and date(:toDate) ";
			parameters.put("fromDate", fromDate);
			parameters.put("toDate", toDate);
		}
		
		if (filterByStatus) {
			if (dateFilter) {
				hql += " and ";
			} else {
				hql += " where ";
			}
			hql += " ( ";
			int count = 1;
			for (ApprovalEnum approvalEnum : statusSelectedList) {
				if (count == statusSelectedList.size()) {
					hql += " o.approvalEnum = :selectedStatus" + count + " ";
				} else {
					hql += " o.approvalEnum = :selectedStatus" + count + " or ";
				}
				parameters.put("selectedStatus" + count, approvalEnum);
				count++;
			}
			hql += " ) ";
		}
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	
	
	public List<?> mostRequestedCourseware(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String hql) {		
		
		if (filters != null) {
			// boolean ft = true;
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (!hql.contains(entry.getKey())) {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}

		if (sortField != null) {

			switch (sortOrder) {
				case ASCENDING:
					hql += " order by o." + sortField + " asc ";
					break;
				case DESCENDING:
					hql += " order by o." + sortField + " desc ";
					break;
				default:
					break;
			}
		}
	
		return getList(hql, filters, startingAt, pageSize);
	}
	
	
	public int countModulesWhere(Map<String, Object> filters)
	{
		String hql="select count(o) from CoursewareDistibution o where o.modules = :modules ";
		return countWhere(filters, hql);
	}
	
	public int countCompanyWhere(Map<String, Object> filters)
	{
		String hql="select count(o) from CoursewareDistibution o where o.company = :company ";
		return countWhere(filters, hql);
	}
	/** Reporting End */
}
