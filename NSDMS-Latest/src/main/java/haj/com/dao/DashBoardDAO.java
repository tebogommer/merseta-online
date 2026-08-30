package haj.com.dao;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.bean.WSPReport;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.TaskStatusEnum;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.entity.enums.WspTypeEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class DashBoardDAO.
 */
public class DashBoardDAO extends AbstractDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Count tasks for user with certain status.
	 *
	 * @param status
	 *            the status
	 * @param userId
	 *            the user id
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long countTaskByStatusAndUser(TaskStatusEnum status, Long userId) throws Exception {
		String hql = "select count(o) from Tasks o where o.taskStatus = :status and o.id in ( select b.task.id from TaskUsers b where b.user.id = :userId and b.task.id = o.id)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("status", status);
		parameters.put("userId", userId);
		return (Long) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Count task by status.
	 *
	 * @param status
	 *            the status
	 * @param inclause
	 *            the inclause
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long countTaskByStatus(TaskStatusEnum status, String inclause) throws Exception {
		String hql = "select count(o) from Tasks o where o.taskStatus = :status and o.hostingCompanyProcess.id in " + inclause;
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("status", status);
		return (Long) super.getUniqueResult(hql, parameters);
	}

	public Long countTaskByStatus(TaskStatusEnum status, boolean employee) throws Exception {
		String hql = "select count(o) from Tasks o where o.taskStatus = :status ";

		if (employee) hql += "and o.id in (select x.task.id from TaskUsers x where x.user.id in (select y.users.id from HostingCompanyEmployees y))";
		else hql += "and o.id in (select x.task.id from TaskUsers x where x.user.id not in (select y.users.id from HostingCompanyEmployees y))";

		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("status", status);
		return (Long) super.getUniqueResult(hql, parameters);
	}

	public Double avgTimeToCompleteTasksEmployees() throws Exception {
		String hql = "	 select avg(datediff(o.actionDate, o.createDate)) from Tasks o where o.taskStatus = :status and datediff(o.actionDate, o.createDate) is not null and o.actionUser.id in (select x.users.id from HostingCompanyEmployees x)  ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("status", TaskStatusEnum.Completed);
		return (Double) super.getUniqueResult(hql, parameters);
	}

	public Double avgTimeToActionTasksEmployees() throws Exception {
		String hql = "	 select avg(datediff(o.startDate, o.createDate)) from Tasks o where o.taskStatus in (:completed,:underway)  and datediff(o.startDate, o.createDate) is not null and o.actionUser.id in (select x.users.id from HostingCompanyEmployees x)  ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("completed", TaskStatusEnum.Completed);
		parameters.put("underway", TaskStatusEnum.Underway);
		return (Double) super.getUniqueResult(hql, parameters);
	}

	public Double avgTimeToCompleteTasksExternal() throws Exception {
		String hql = "	 select avg(datediff(o.actionDate, o.createDate)) from Tasks o where o.taskStatus = :status and datediff(o.actionDate, o.createDate) is not null and o.actionUser.id not in (select x.users.id from HostingCompanyEmployees x)  ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("status", TaskStatusEnum.Completed);
		return (Double) super.getUniqueResult(hql, parameters);
	}

	public Double avgTimeToActionTasksExternal() throws Exception {
		String hql = "	 select avg(datediff(o.startDate, o.createDate)) from Tasks o where o.taskStatus in (:completed,:underway)  and datediff(o.startDate, o.createDate) is not null and o.actionUser.id not in (select x.users.id from HostingCompanyEmployees x)  ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("completed", TaskStatusEnum.Completed);
		parameters.put("underway", TaskStatusEnum.Underway);
		return (Double) super.getUniqueResult(hql, parameters);
	}

	/*
	 * select avg(datediff(o.startDate, o.createDate)) from Tasks o where
	 * o.taskStatus in (1,2) and datediff(o.startDate, o.createDate) is not null and
	 * o.actionUser.id in (select x.users.id from HostingCompanyEmployees x)
	 * 
	 */

	/**
	 * Count task by status and user between dates.
	 *
	 * @param status
	 *            the status
	 * @param userId
	 *            the user id
	 * @param fromDate
	 *            the from date
	 * @param toDate
	 *            the to date
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long countTaskByStatusAndUserBetweenDates(TaskStatusEnum status, Long userId, Date fromDate, Date toDate) throws Exception {
		String hql = "select count(o) from Tasks o where o.taskStatus = :status and o.id in ( select b.task.id from TaskUsers b where b.user.id = :userId and b.task.id = o.id) and date(o.createDate) between :fromDate and :toDate";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("status", status);
		parameters.put("userId", userId);
		parameters.put("fromDate", fromDate);
		parameters.put("toDate", toDate);
		return (Long) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Count task by status between dates.
	 *
	 * @param status
	 *            the status
	 * @param inclause
	 *            the inclause
	 * @param fromDate
	 *            the from date
	 * @param toDate
	 *            the to date
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long countTaskByStatusBetweenDates(TaskStatusEnum status, String inclause, Date fromDate, Date toDate) throws Exception {
		String hql = "select count(o) from Tasks o where o.taskStatus = :status and o.hostingCompanyProcess.id in " + inclause + " and date(o.createDate) between :fromDate and :toDate";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("status", status);
		parameters.put("fromDate", fromDate);
		parameters.put("toDate", toDate);
		return (Long) super.getUniqueResult(hql, parameters);
	}

	/** The hql. */
	String hql = "select count(o) from haj.com.entity.Company o where o.companyStatus = 2";

	/**
	 * Count companies.
	 *
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long countCompanies() throws Exception {
		String hql = "select count(o) from haj.com.entity.Company o";
		return (Long) super.getUniqueResult(hql);
	}

	/**
	 * Count companies.
	 *
	 * @param status
	 *            the status
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long countCompanies(CompanyStatusEnum status) throws Exception {
		String hql = "select count(o) from haj.com.entity.Company o where o.companyStatus = :status";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("status", status);
		return (Long) super.getUniqueResult(hql, parameters);
	}

	public List<WSPReport> countWSP() throws Exception {
		String hql = "SELECT wspType, "
				+ "MAX(if(numEmp = 'small', num, 0)) as smallCount, "
				+ "MAX(if(numEmp = 'mid', num, 0)) as midCount, "
				+ "MAX(if(numEmp = 'large', num, 0)) as largeCount "
				+ "FROM ( select wspType , count(wsp) as num, numEmp from ( "
				+ "select a.wsp_id as wsp, case when c.numberOfEmployees <= 49 then 'small' "
				+ "when c.numberOfEmployees between 50 and 149 then 'mid' "
				+ "when c.numberOfEmployees >= 150 then 'large' "
				+ "else 'x' end as numEmp, "
				+ "'Mandatory Grant (ATR and WSP only)' as wspType "
				+ "from (select distinct wsp_id as wsp_id from mandatory_grant_detail where funding_id = 6) a "
				+ "left join (select distinct wsp_id as wsp_id from mandatory_grant_detail where funding_id = 7) b on b.wsp_id = a.wsp_id "
				+ "left join wsp w on a.wsp_id = w.id "
				+ "left join company c on w.company_id = c.id "
				+ "where b.wsp_id is null and w.wsp_status_enum not in (:pendingSignOffId, :pendingId,:submittedAfterDeadline, :na) "
				+ "union "
				+ "select a.wsp_id as wsp, case when c.numberOfEmployees <= 49 then 'small' "
				+ "when c.numberOfEmployees between 50 and 149 then 'mid' "
				+ "when c.numberOfEmployees >= 150 then 'large' "
				+ "else 'x' end as numEmp, "
				+ "if(c.non_levy_paying <> 1, 'Discretionary Grant Application (ATR, WSP & DG)', 'Discretionary Grant Application (non-levy paying entity)') as wspType "
				+ "from (select distinct wsp_id as wsp_id from mandatory_grant_detail where funding_id = 7) a "
				+ "left join wsp w on a.wsp_id = w.id  "
				+ "left join company c on w.company_id = c.id  "
				+ "where w.wsp_status_enum not in (:pendingSignOffId, :pendingId,:submittedAfterDeadline, :na)   "
				+ "union "
				+ "select a.wsp_id as wsp,	case when c.numberOfEmployees <= 49 then 'small' "
				+ "when c.numberOfEmployees between 50 and 149 then 'mid' "
				+ "when c.numberOfEmployees >= 150 then 'large' "
				+ "else 'x' end as numEmp, "
				+ "'TOTAL' as wspType "
				+ "from(select distinct wsp_id, funding_id from mandatory_grant_detail) a "
				+ "left join wsp w on a.wsp_id = w.id  "
				+ "left join company c on w.company_id = c.id "
				+ "where a.funding_id is not null and w.wsp_status_enum not in (:pendingSignOffId, :pendingId,:submittedAfterDeadline, :na) ) nums group by wspType, numEmp ) counts "
				+ "GROUP BY wspType";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("pendingId", WspStatusEnum.Draft.ordinal());
		parameters.put("pendingSignOffId", WspStatusEnum.PendingSignOff.ordinal());
		parameters.put("submittedAfterDeadline", WspStatusEnum.Pending.ordinal());
		parameters.put("na", WspStatusEnum.NA.ordinal());
		return (List<WSPReport>) super.nativeSelectSqlList(hql, WSPReport.class, parameters);
	}
	
	public List<WSPReport> countWSPByFinYear(Integer finYear) throws Exception {
		String hql = "SELECT wspType, "
				+ "MAX(if(numEmp = 'small', num, 0)) as smallCount, "
				+ "MAX(if(numEmp = 'mid', num, 0)) as midCount, "
				+ "MAX(if(numEmp = 'large', num, 0)) as largeCount "
				+ "FROM ( select wspType , count(wsp) as num, numEmp from ( "
				+ "select a.wsp_id as wsp, case when c.numberOfEmployees <= 49 then 'small' "
				+ "when c.numberOfEmployees between 50 and 149 then 'mid' "
				+ "when c.numberOfEmployees >= 150 then 'large' "
				+ "else 'x' end as numEmp, "
				+ "'Mandatory Grant (ATR and WSP only)' as wspType "
				+ "from (select distinct wsp_id as wsp_id from mandatory_grant_detail where funding_id = 6) a "
				+ "left join (select distinct wsp_id as wsp_id from mandatory_grant_detail where funding_id = 7) b on b.wsp_id = a.wsp_id "
				+ "left join wsp w on a.wsp_id = w.id "
				+ "left join company c on w.company_id = c.id "
				+ "where b.wsp_id is null and w.wsp_status_enum not in (:pendingSignOffId, :pendingId,:submittedAfterDeadline, :na) and w.fin_year = :finYear "
				+ "union "
				+ "select a.wsp_id as wsp, case when c.numberOfEmployees <= 49 then 'small' "
				+ "when c.numberOfEmployees between 50 and 149 then 'mid' "
				+ "when c.numberOfEmployees >= 150 then 'large' "
				+ "else 'x' end as numEmp, "
				+ "if(c.non_levy_paying <> 1, 'Discretionary Grant Application (ATR, WSP & DG)', 'Discretionary Grant Application (non-levy paying entity)') as wspType "
				+ "from (select distinct wsp_id as wsp_id from mandatory_grant_detail where funding_id = 7) a "
				+ "left join wsp w on a.wsp_id = w.id  "
				+ "left join company c on w.company_id = c.id  "
				+ "where w.wsp_status_enum not in (:pendingSignOffId, :pendingId,:submittedAfterDeadline, :na) and w.fin_year = :finYear  "
				+ "union "
				+ "select a.wsp_id as wsp,	case when c.numberOfEmployees <= 49 then 'small' "
				+ "when c.numberOfEmployees between 50 and 149 then 'mid' "
				+ "when c.numberOfEmployees >= 150 then 'large' "
				+ "else 'x' end as numEmp, "
				+ "'TOTAL' as wspType "
				+ "from(select distinct wsp_id, funding_id from mandatory_grant_detail) a "
				+ "left join wsp w on a.wsp_id = w.id  "
				+ "left join company c on w.company_id = c.id "
				+ "where a.funding_id is not null and w.fin_year = :finYear and w.wsp_status_enum not in (:pendingSignOffId, :pendingId,:submittedAfterDeadline, :na) ) nums group by wspType, numEmp ) counts "
				+ "GROUP BY wspType";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("pendingId", WspStatusEnum.Draft.ordinal());
		parameters.put("pendingSignOffId", WspStatusEnum.PendingSignOff.ordinal());
		parameters.put("submittedAfterDeadline", WspStatusEnum.Pending.ordinal());
		parameters.put("na", WspStatusEnum.NA.ordinal());
		parameters.put("finYear", finYear);
		return (List<WSPReport>) super.nativeSelectSqlList(hql, WSPReport.class, parameters);
	}

	public List<WSPReport> countWSP2() throws Exception {
		String hql = "SELECT wspType, MAX(if(numEmp = 'small', num, 0)) as smallCount, MAX(if(numEmp = 'mid', num, 0)) as midCount, " + 
				"MAX(if(numEmp = 'large', num, 0)) as largeCount, cast(sum(num) as UNSIGNED) as total FROM ( " + 
				"select wspType , count(c_id) as num, numEmp from( " + 
				"select distinct s.company_id as c_id, " + 
				"case when c.numberOfEmployees <= 49 then 'small' " + 
				"when c.numberOfEmployees between 50 and 149 then 'mid' " + 
				"when c.numberOfEmployees >= 150 then 'large' " + 
				"else 'x' end as numEmp, 'Total' as wspType " + 
				"from merseta.sdf_company s " + 
				"left join merseta.company c on s.company_id = c.id  " + 
				"left join merseta.wsp w on w.company_id = s.company_id " + 
				"where c.non_levy_paying is not null  " + 
				"and c.numberOfEmployees is not null  " + 
				" and (wsp_status_enum in (:pendingSignOffId, :pendingId) or wsp_status_enum is null) " + 
				") as base group by wspType, numEmp ) as counts GROUP BY wspType  " + 
				"union SELECT " + 
				"wspType, MAX(if(numEmp = 'small', num, 0)) as smallCount, MAX(if(numEmp = 'mid', num, 0)) as midCount, " + 
				"MAX(if(numEmp = 'large', num, 0)) as largeCount, cast( sum(num) as UNSIGNED) as total " + 
				"FROM ( select wspType , count(c_id) as num, numEmp from( select distinct c_id , numEmp " + 
				"	, case when w_id is null and non_levy_paying <> 1 then 'Levy-Paying Entities Not Yet Started Grant Application' " + 
				"		when w_id is not null and non_levy_paying <> 1 then 'Levy-Paying Entities Started Grant Application (Underway)' " + 
				"		when w_id is null and non_levy_paying = 1 then 'Non-Levy Paying Entities Not Yet Started Grant Application' " + 
				"		when w_id is not null and non_levy_paying = 1 then 'Non-Levy Paying Entities Started Grant Application (Underway)' " + 
				"		else 'x' end as wspType " + 
				"from (select s.company_id as c_id, " + 
				"	case when c.numberOfEmployees <= 49 then 'small' " + 
				"	when c.numberOfEmployees between 50 and 149 then 'mid' " + 
				"	when c.numberOfEmployees >= 150 then 'large' " + 
				"	else 'x' end as numEmp, " + 
				"	w.id as w_id, c.non_levy_paying " + 
				"from merseta.sdf_company s " + 
				"left join merseta.company c on s.company_id = c.id  " + 
				"left join merseta.wsp w on w.company_id = s.company_id " + 
				"where c.non_levy_paying is not null and c.numberOfEmployees is not null  and (wsp_status_enum in (:pendingSignOffId, :pendingId) or wsp_status_enum is null)) as base " + 
				") nums group by wspType, numEmp ) as counts GROUP BY wspType";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("pendingId", WspStatusEnum.Draft.ordinal());
		parameters.put("pendingSignOffId", WspStatusEnum.PendingSignOff.ordinal());
		return (List<WSPReport>) super.nativeSelectSqlList(hql, WSPReport.class,parameters);
	}
	
	public List<WSPReport> countWSP2ByFinYear(Integer finYear) throws Exception {
		String hql = "SELECT  wspType " + 
				"	, MAX(if(numEmp = 'small', num, 0)) as smallCount, MAX(if(numEmp = 'mid', num, 0)) as midCount " + 
				"	, MAX(if(numEmp = 'large', num, 0)) as largeCount, cast(sum(num) as UNSIGNED) as total " + 
				"FROM ( " + 
				"select wspType ,count(c_id) as num ,numEmp " + 
				"from ( " + 
				"select " + 
				"distinct s.company_id as c_id " + 
				", case " + 
				"when c.numberOfEmployees <= 49 then 'small' " + 
				"when c.numberOfEmployees between 50 and 149 then 'mid' " + 
				"when c.numberOfEmployees >= 150 then 'large' " + 
				"else 'x' end as numEmp " + 
				",'Total' as wspType " + 
				"from merseta.sdf_company s " + 
				"left join merseta.company c on s.company_id = c.id  " + 
				"left join merseta.wsp w on w.company_id = s.company_id " + 
				"where c.non_levy_paying is not null  " + 
				"and c.numberOfEmployees is not null  " + 
				"and ((wsp_status_enum in (:pendingId, :pendingSignOffId) or wsp_status_enum is null) and fin_year = :finYear)) " + 
				"as base group by wspType, numEmp) as counts GROUP BY wspType " + 
				"union " + 
				"SELECT " + 
				"wspType, " + 
				"MAX(if(numEmp = 'small', num, 0)) as smallCount, MAX(if(numEmp = 'mid', num, 0)) as midCount, " + 
				"MAX(if(numEmp = 'large', num, 0)) as largeCount, cast( sum(num) as UNSIGNED) as total " + 
				"FROM ( " + 
				"select wspType , count(c_id) as num, numEmp from( select distinct c_id , numEmp " + 
				", case " + 
				"when w_id is null and non_levy_paying <> 1 then 'Levy-Paying Entities Not Yet Started Grant Application' " + 
				"when w_id is not null and non_levy_paying <> 1 then 'Levy-Paying Entities Started Grant Application (Underway)' " + 
				"when w_id is null and non_levy_paying = 1 then 'Non-Levy Paying Entities Not Yet Started Grant Application' " + 
				"when w_id is not null and non_levy_paying = 1 then 'Non-Levy Paying Entities Started Grant Application (Underway)' " + 
				"else 'x' end as wspType " + 
				"from (select s.company_id as c_id, " + 
				"case " + 
				"when c.numberOfEmployees <= 49 then 'small' " + 
				"when c.numberOfEmployees between 50 and 149 then 'mid' " + 
				"when c.numberOfEmployees >= 150 then 'large' " + 
				"else 'x' end as numEmp, " + 
				"w.id as w_id, c.non_levy_paying " + 
				"from merseta.sdf_company s " + 
				"left join merseta.company c on s.company_id = c.id  " + 
				"left join merseta.wsp w on w.company_id = s.company_id " + 
				"where c.non_levy_paying is not null and c.numberOfEmployees is not null and ((wsp_status_enum in (:pendingId, :pendingSignOffId) or wsp_status_enum is null) and fin_year = :finYear)) as base " + 
				") nums group by wspType, numEmp ) as counts GROUP BY wspType;";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("pendingId", WspStatusEnum.Draft.ordinal());
		parameters.put("pendingSignOffId", WspStatusEnum.PendingSignOff.ordinal());
		parameters.put("finYear", finYear);
		return (List<WSPReport>) super.nativeSelectSqlList(hql, WSPReport.class,parameters);
	}
	 
	@SuppressWarnings("unchecked")
	public List<Integer> allDistinctFinYearsLastestThreeYears() throws Exception {
		String hql = "select distinct(o.finYear) from Wsp o where o.finYear <> null order by o.finYear desc";
		return (List<Integer>) super.getList(hql,3);
	}
	
	/**
	 * Generates Grant Status Reports based on company size 
	 * @param finYear
	 * @return List<WSPReport> the results
	 * @author TechFINIUM
	 * @throws Exception
	 */
	public List<WSPReport> grantStatusReportByCompanySizes(Integer finYear) throws Exception {
		String hql = "SELECT  " + 
				"	reportNumber, " + 
				"	description as wspType,  " + 
				"	MAX(if(numEmp = 'Small', num, 0)) as smallCount,  " + 
				"	MAX(if(numEmp = 'Med', num, 0)) as midCount, " + 
				"	MAX(if(numEmp = 'Large', num, 0)) as largeCount " +  
				"FROM ( " + 
				"	select reportNumber, description, count(id) as num, numEmp from (  " + 
				"			select w.id as id " + 
				"				, 1 as reportNumber " + 
				"				, case 	when c.numberOfEmployees <= 49 then 'Small'  " + 
				"					when c.numberOfEmployees between 50 and 149 then 'Med'  " + 
				"					when c.numberOfEmployees >= 150 then 'Large'  " + 
				"					else 'NA' end as numEmp   " + 
				"				, 'Discretionary Grant Application (ATR, WSP & DG)' as description  " + 
				"			from wsp w, company c " + 
				"			where w.company_id = c.id " + 
				"			and c.levy_number like 'L%' " + 
				"			and c.non_seta_company = false " + 
				"			and w.fin_year = :finYear " + 
				"			and w.wsp_status_enum not in (:pendingId,:pendingSignOffId,:submittedAfterDeadline) " + 
				"			and w.wsp_type in (:wspTypeBoth) " + 
				"		union " + 
				"			select w.id as id " + 
				"				, 2 as reportNumber " + 
				"				, case 	when c.numberOfEmployees <= 49 then 'Small'  " + 
				"					when c.numberOfEmployees between 50 and 149 then 'Med'  " + 
				"					when c.numberOfEmployees >= 150 then 'Large'  " + 
				"					else 'NA' end as numEmp   " + 
				"				, 'Mandatory Grant (ATR and WSP only)' as description  " + 
				"			from wsp w, company c " + 
				"			where w.company_id = c.id " + 
				"			and c.levy_number like 'L%' " + 
				"			and c.non_seta_company = false " + 
				"			and w.fin_year = :finYear " + 
				"			and w.wsp_status_enum not in (:pendingId,:pendingSignOffId,:submittedAfterDeadline) " + 
				"			and w.wsp_type in (:wspTypeMand) " + 
				"		union " + 
				"			select w.id as id " + 
				"				, 3 as reportNumber " + 
				"				, case 	when c.numberOfEmployees <= 49 then 'Small'  " + 
				"					when c.numberOfEmployees between 50 and 149 then 'Med'  " + 
				"					when c.numberOfEmployees >= 150 then 'Large'  " + 
				"					else 'NA' end as numEmp   " + 
				"				, 'Discretionary Grant Application (non-levy paying entity)' as description  " + 
				"			from wsp w, company c " + 
				"			where w.company_id = c.id " + 
				"			and c.levy_number like 'N%' " + 
				"			and c.non_seta_company = false " + 
				"			and w.fin_year = :finYear " + 
				"			and w.wsp_status_enum not in (:pendingId, :pendingSignOffId, :submittedAfterDeadline) " + 
				"			and w.wsp_type in (:wspTypeDisc, :wspTypeBoth) " + 
				"		union " + 
				"			select w.id as id " + 
				"				, 4 as reportNumber " + 
				"				, case 	when c.numberOfEmployees <= 49 then 'Small'  " + 
				"					when c.numberOfEmployees between 50 and 149 then 'Med'  " + 
				"					when c.numberOfEmployees >= 150 then 'Large'  " + 
				"					else 'NA' end as numEmp   " + 
				"				, 'Discretionary Grant Application (ATR, WSP & DG) submitted for recorded purposes' as description  " + 
				"			from wsp w, company c " + 
				"			where w.company_id = c.id " + 
				"			and c.levy_number like 'L%' " + 
				"			and c.non_seta_company = false " + 
				"			and w.fin_year = :finYear " + 
				"			and w.wsp_status_enum in (:submittedAfterDeadline) " + 
				"			and w.wsp_type in (:wspTypeBoth)	 " + 
				"		union " + 
				"			select w.id as id " + 
				"				, 5 as reportNumber " + 
				"				, case 	when c.numberOfEmployees <= 49 then 'Small'  " + 
				"					when c.numberOfEmployees between 50 and 149 then 'Med'  " + 
				"					when c.numberOfEmployees >= 150 then 'Large'  " + 
				"					else 'NA' end as numEmp   " + 
				"				, 'Mandatory Grant (ATR and WSP only) submitted for recorded purposes' as description  " + 
				"			from wsp w, company c " + 
				"			where w.company_id = c.id " + 
				"			and c.levy_number like 'L%' " + 
				"			and c.non_seta_company = false " + 
				"			and w.fin_year = :finYear " + 
				"			and w.wsp_status_enum in (:submittedAfterDeadline) " + 
				"			and w.wsp_type in (:wspTypeMand) " + 
				"		union " + 
				"			select w.id as id " + 
				"				, 6 as reportNumber " + 
				"				, case 	when c.numberOfEmployees <= 49 then 'Small'  " + 
				"					when c.numberOfEmployees between 50 and 149 then 'Med'  " + 
				"					when c.numberOfEmployees >= 150 then 'Large'  " + 
				"					else 'NA' end as numEmp   " + 
				"				, 'Discretionary Grant Application (non-levy paying entity) submitted for record purposes' as description  " + 
				"			from wsp w, company c " + 
				"			where w.company_id = c.id " + 
				"			and c.levy_number like 'N%' " + 
				"			and c.non_seta_company = false " + 
				"			and w.fin_year = :finYear " + 
				"			and w.wsp_status_enum in (:submittedAfterDeadline) " + 
				"			and w.wsp_type in (:wspTypeDisc, :wspTypeBoth) " + 
				"	) nums group by reportNumber, description, numEmp " + 
				") counts  " + 
				"group by reportNumber, description;";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		// Wsp statuses
		parameters.put("pendingId", WspStatusEnum.Draft.ordinal());
		parameters.put("pendingSignOffId", WspStatusEnum.PendingSignOff.ordinal());
		parameters.put("submittedAfterDeadline", WspStatusEnum.Pending.ordinal());
//		parameters.put("na", WspStatusEnum.NA.ordinal());
		// Wsp Types
		parameters.put("wspTypeMand", WspTypeEnum.Mandatory.ordinal());
		parameters.put("wspTypeDisc", WspTypeEnum.Discretionary.ordinal());
		parameters.put("wspTypeBoth", WspTypeEnum.Both.ordinal());
		// Fin Year of WSP
		parameters.put("finYear", finYear);
		return (List<WSPReport>) super.nativeSelectSqlList(hql, WSPReport.class, parameters);
	}
}