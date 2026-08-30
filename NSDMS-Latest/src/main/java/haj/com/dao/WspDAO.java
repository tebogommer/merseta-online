package haj.com.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.bean.CounterBean;
import haj.com.bean.GrantsSubmissionReportBean;
import haj.com.bean.WspCompanyReportBean;
import haj.com.bean.WspFirmSubmissionBean;
import haj.com.constants.HAJConstants;
import haj.com.entity.Company;
import haj.com.entity.Wsp;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanySizeEnum;
import haj.com.entity.enums.WspReportEnum;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.entity.enums.WspTypeEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.report.bean.ByChamberReportBean;

// TODO: Auto-generated Javadoc
/**
 * The Class WspDAO.
 */
public class WspDAO extends AbstractDAO {

	/** The Constant leftJoins. */
	private static final String leftJoins = " left join fetch o.companyContact cc left join fetch o.projectManager pm left join fetch o.projectOwner po left join fetch o.company c left join fetch c.residentialAddress ra left join fetch ra.municipality mra left join fetch c.postalAddress pa left join fetch pa.municipality mpa left join fetch c.registeredAddress rad left join fetch rad.municipality mrad " + " left join fetch o.trainingReportedAtrPtr trap left join fetch o.skillsGapTrackSelection sgts ";

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
	 * Get all Wsp.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Wsp}
	 * @throws Exception
	 *             global exception
	 * @see Wsp
	 */
	@SuppressWarnings("unchecked")
	public List<Wsp> allWsp() throws Exception {
		return (List<Wsp>) super.getList("select o from Wsp o");
	}

	/**
	 * Get all Wsp between from and noRows.
	 *
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @return a list of {@link haj.com.entity.Wsp}
	 * @throws Exception
	 *             global exception
	 * @see Wsp
	 */
	@SuppressWarnings("unchecked")
	public List<Wsp> allWsp(Long from, int noRows) throws Exception {
		String hql = "select o from Wsp o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<Wsp>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find Wsp by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.Wsp}
	 * @throws Exception
	 *             global exception
	 * @see Wsp
	 */
	public Wsp artPercentageCheck(Long id) throws Exception {
		String hql = "select o from Wsp o where o.id = :id and o.percentagePayrollSpent is not null and o.percentageTrainingCostSpentTraining is not null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (Wsp) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.Wsp}
	 * @throws Exception
	 *             global exception
	 * @see Wsp
	 */
	public Wsp findByKey(Long id) throws Exception {
		String hql = "select o from Wsp o" + leftJoins + " where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (Wsp) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Wsp by description.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a list of {@link haj.com.entity.Wsp}
	 * @throws Exception
	 *             global exception
	 * @see Wsp
	 */
	@SuppressWarnings("unchecked")
	public List<Wsp> findByName(String description) throws Exception {
		String hql = "select o from Wsp o where o.wspGuid like  :description order by o.wspGuid";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<Wsp>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Wsp> findByYearRange(Integer fromYear,Integer toYear) throws Exception {
		 String hql = "select o from Wsp o "+leftJoins+" where o.finYear >= :fromYear and o.finYear <= :toYear";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("fromYear", fromYear);
		parameters.put("toYear", toYear);
		return (List<Wsp>) super.getList(hql, parameters);
	}
	
	

	/**
	 * Find by company.
	 *
	 * @param companyId
	 *            the company id
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Wsp> findByCompany(Long companyId) throws Exception {
		String hql = "select o from Wsp o" + leftJoins + " where o.company.id = :companyId order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		return (List<Wsp>) super.getList(hql, parameters);
	}
	
	/**
	 * Find by company and Financial Year.
	 *
	 * @param companyId
	 *            the company id
	 * @param finYear
	 *            the finYear
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Wsp> findByCompanyAndFinancialYear(Long companyId, int finYear) throws Exception {
		String hql = "select o from Wsp o" + leftJoins + " where o.company.id = :companyId and o.finYear = :finYear order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("finYear", finYear);
		return (List<Wsp>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Wsp> findByFinancialYear(int finYear) throws Exception {
		String hql = "select o from Wsp o" + leftJoins + " where o.finYear = :finYear order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("finYear", finYear);
		return (List<Wsp>) super.getList(hql, parameters);
	}
	
	/**
	 * Count check for wsp by company id and fin year against wsp
	 * @see Company
	 * @see Wsp
	 * @param companyId
	 * @param finYear
	 * @return int
	 * @throws Exception
	 * @author jonathanbowett
	 */
	public int countByCompanyAndFinYear(Long companyId, int finYear) throws Exception {
		String hql = "select count(o) from Wsp o where o.company.id = :companyId and o.finYear = :finYear and o.wspType in (:mandatoryGrant, :discretionaryGrant, :both) ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("finYear", finYear);
		parameters.put("mandatoryGrant", WspTypeEnum.Mandatory);
		parameters.put("discretionaryGrant", WspTypeEnum.Discretionary);
		parameters.put("both", WspTypeEnum.Both);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public int countByCompanyAndFinYearAndWspApprovedRejectedStatus(Long companyId, int finYear) throws Exception {
		String hql = "select count(o) from Wsp o where o.company.id = :companyId and o.finYear = :finYear and o.wspStatusEnum in (:approvedStatus, :rejectedStatus) ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("finYear", finYear);
		parameters.put("approvedStatus", WspStatusEnum.Approved);
		parameters.put("rejectedStatus", WspStatusEnum.Rejected);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	public List<Wsp> findSubmittedWSP() throws Exception {
		String hql = "select o from Wsp o" + leftJoins + " where o.wspStatusEnum not in (:pendingSignOffId, :pendingId)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("pendingId", WspStatusEnum.Draft);
		parameters.put("pendingSignOffId", WspStatusEnum.PendingSignOff);
		return (List<Wsp>) super.getList(hql, parameters);
	}

	/**
	 * Find by guid.
	 *
	 * @param guid
	 *            the guid
	 * @return the wsp
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public Wsp findByGuid(String guid) throws Exception {
		String hql = "select o from Wsp o" + leftJoins + " where o.wspGuid LIKE :guid order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("guid", guid);
		return (Wsp) super.getUniqueResult(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<Wsp> allMandatoryGrantWsp(WspReportEnum wspReport, long fundingID, int finYear) throws Exception {
		String hql = "select distinct(o.wsp) from MandatoryGrantDetail o where o.wspReport =:wspReport and o.funding.id = :fundingID and o.wsp.finYear = :finYear order by o.wsp.finYear desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspReport", wspReport);
		parameters.put("fundingID", fundingID);
		parameters.put("finYear", finYear);
		return (List<Wsp>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Wsp> findByStatus(WspStatusEnum statusEnum, int finYear) throws Exception {
		String hql = "select o from Wsp o" + leftJoins + " where o.wspStatusEnum = :pendingSignOff and o.finYear = :finYear order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("pendingSignOff", statusEnum);
		parameters.put("finYear", finYear);
		return (List<Wsp>) super.getList(hql, parameters);
	}
	
	/**
	 * Locates distinct financial years against wsp.
	 * where there is a status against the wsp
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> findDictinctFinYears() throws Exception {
		String hql = "select distinct(o.finYear) from Wsp o where o.wspStatusEnum is not null and o.finYear is not null order by o.finYear";
		return (List<Integer>) super.getList(hql);
	}
	
	/**
	 * Counts Wsp by status where company is not null and optional: fin year
	 * @param status
	 * @param finYear
	 * @return Integer
	 * @throws Exception
	 */
	public Integer countWspByStatusAndFinYear(WspStatusEnum status, List<Integer> finYear) throws Exception {
		String hql = "select count(o) from Wsp o where o.company is not null and o.wspStatusEnum = :status ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("status", status);
		if (finYear != null) {
			int count = 1;
			for (Integer year : finYear) {
				if (count == finYear.size()) {
					hql += "o.finYear = :finYear" + count;
				}else {
					hql += "o.finYear = :finYear" + count + " and ";
				}
				parameters.put("finYear"+ count, year);
				count++;
			}
		}
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countWspByStatusAndFinYear(WspStatusEnum status, Integer finYear) throws Exception {
		String hql = "select count(o) from Wsp o where o.company is not null and o.wspStatusEnum = :status and o.finYear = :finYear";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("status", status);
		parameters.put("finYear", finYear);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	/**
	 * Counts Wsp by status where company is not null and optional: fin year
	 * @param finYear
	 * @return Integer
	 * @throws Exception
	 */
	public Integer countAllDgFinYear(List<Integer> finYear) throws Exception {
		String hql = "select count(o) from Wsp o where o.company is not null ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		if (finYear != null) {
			int count = 1;
			hql += " and ";
			for (Integer year : finYear) {
				if (count == finYear.size()) {
					hql += "o.finYear = :finYear" + count;
				}else {
					hql += "o.finYear = :finYear" + count + " and ";
				}
				parameters.put("finYear"+ count, year);
				count++;
			}
		}
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countAllDgFinYear(Integer finYear) throws Exception {
		String hql = "select count(o) from Wsp o where o.company is not null and o.finYear = :finYear";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("finYear" , finYear);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<Wsp> findWspAwaitingDescionBySdfRejectionAppeal() throws Exception {
		String hql = "select o from Wsp o" + leftJoins + " where o.withSdf = :withSdf and o.sdfAppealedGrant = :sdfAppealedGrant and o.grantRejected = :grantRejected";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("withSdf", true);
		parameters.put("sdfAppealedGrant", false);
		parameters.put("grantRejected", true);
		return (List<Wsp>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Wsp> findWspAwaitingMancoApprovalByStatus(WspStatusEnum wspStatusEnum) throws Exception {
		String hql = "select o from Wsp o" + leftJoins + " where o.wspStatusEnum = :wspStatus";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspStatus", wspStatusEnum);
		return (List<Wsp>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Wsp> findWspAwaitingMancoApproval() throws Exception {
		String hql = "select o from Wsp o" + leftJoins + " where o.mancoApprovalRequired = :approvalRequired and o.mancoDecisionReached = :decisionReached";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("approvalRequired", true);
		parameters.put("decisionReached", false);
		return (List<Wsp>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Wsp> findWspAwaitingMancoApprovalByFinYear(Integer finYear) throws Exception {
		String hql = "select o from Wsp o" + leftJoins + " where o.mancoApprovalRequired = :approvalRequired and o.mancoDecisionReached = :decisionReached and o.finYear = :finYear";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("approvalRequired", true);
		parameters.put("decisionReached", false);
		parameters.put("finYear", finYear);
		return (List<Wsp>) super.getList(hql, parameters);
	}
	
	/*
	 * For One off fix do not use again
	 * Locates approved WSP by fin year where DG verification generated but with no entries
	 */
	public List<ByChamberReportBean> locateWspIDsThatNeedGenerationQuickFix() throws Exception {
		String sql = "select " + 
				"	w.id as idPassed " + 
				"from wsp w " + 
				"where  " + 
				"w.fin_year = 2020 " + 
				"and w.wsp_status_enum = 4 " + 
				"and w.id in (select wsp_id from dg_verification) " + 
				"and w.id not in (select wsp_id from mandatory_grant)";
		return (List<ByChamberReportBean>)super.nativeSelectSqlList(sql, ByChamberReportBean.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Wsp> findWspbyFinYearApprovedAndApprovedVerification(Integer finYear) throws Exception {
		String hql = "select o from Wsp o where "
				+ "o.finYear = :finYear "
				+ "and o.id in (select x.wsp.id from DgVerification x where x.status = :verificationStatus)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("finYear", finYear);
		parameters.put("verificationStatus", ApprovalEnum.Approved);
		return (List<Wsp>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Wsp> findWspbyFinYearAndInVerification(Integer finYear) throws Exception {
		String hql = "select o from Wsp o where "
				+ "o.finYear = :finYear "
				+ "and o.id in (select x.wsp.id from DgVerification x )";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("finYear", finYear);
		return (List<Wsp>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Wsp> findWspByStatusAndFinYear(WspStatusEnum wspStatus, Integer finYear) throws Exception {
		String hql = "select o from Wsp o" + leftJoins + " where o.wspStatusEnum = :wspStatus and o.finYear = :finYear";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspStatus", wspStatus);
		parameters.put("finYear", finYear);
		return (List<Wsp>) super.getList(hql, parameters);
	}
	
	
	// Updated for JIRA #1330 - added grantSignOffDate and sdfType
	public List<WspCompanyReportBean> populateGrantSubmissionReportByCompany(List<WspStatusEnum> statusList) throws Exception {		
		String sql = "select  " + 
				"	c.levy_number as entityId  " + 
				"	, c.company_name as companyName  " + 
				"	, c.trading_name as tradingName  " + 
				"	, w.fin_year as grantYear  " + 
				"	, wso.sign_off_date as grantSignOffDate  " + 
				"	, case   " + 
				"		when w.wsp_type = '0' then 'Mandatory'  " + 
				"		when w.wsp_type = '1' then 'Discretionary'  " + 
				"		when w.wsp_type = '2' then 'Mandatory and Discretionary'  " + 
				"		when w.wsp_type = '3' then 'Inter-SETA Transfer Mandatory Grant'  " + 
				"		when w.wsp_type = '4' then 'Legacy Linked Company Grant Application'		  " + 
				"		Else 'Unable To Locate Grant Type'  " + 
				"		end as grantType  " + 
				"	 , case  " + 
				"		when w.wsp_status_enum = '0' then 'Not Submitted-In Progress'   " + 
				"		when w.wsp_status_enum = '1' then 'Pending Sign Off'   " + 
				"		when w.wsp_status_enum = '2' then 'Pending Approval'   " + 
				"		when w.wsp_status_enum = '3' then 'Dispute'   " + 
				"		when w.wsp_status_enum = '4' then 'Approved'   " + 
				"		when w.wsp_status_enum = '5' then 'Rejected'   " + 
				"		when w.wsp_status_enum = '6' then 'Reviewed Pending Approval'  " + 
				"		when w.wsp_status_enum = '7' then 'Reviewed Pending Final Decision'  " + 
				"		when w.wsp_status_enum = '8' then 'Received For Record Purposes'  " + 
				"		when w.wsp_status_enum = '9' then 'Opened For Review'  " + 
				"		when w.wsp_status_enum = '10' then 'NA'  " + 
				"		when w.wsp_status_enum = '11' then 'Withdrawn'  " + 
				"		when w.wsp_status_enum = '12' then 'Awaiting MANCO Approval'  " + 
				"		else 'N/A'  " + 
				"		end as grantStatus  " + 
				"	 , case  " + 
				"		when wso.sdf_type_id = '3' then 'Labour/Union SDF'   " + 
				"		when wso.sdf_type_id = '4' then 'Employee SDF'   " + 
				"		else 'N/A'  " + 
				"		end as sdfType  " + 
				"    , p.province_desc as province  " + 
				"    , r.description as region  " + 
				"      " + 
				"from   " + 
				"	wsp w  " + 
				"	  " + 
				"left join company c on c.id = w.company_id  " + 
				"left join address resAdd on resAdd.id = c.residential_address_id  " + 
				"left join municipality mp on mp.id = resAdd.municipality_id  " + 
				"left join province p on mp.provinces_idprovinces = p.id  " + 
				"left join region_town rt on rt.town_id = resAdd.town_id  " + 
				"left join region r on r.id = rt.region_id  " + 
				"left join wsp_signoff wso on wso.wsp_id = w.id  " + 
				"  " + 
				"where  " + 
				"	w.company_id is not null  " + 
				"	and w.wsp_type is not null  " + 
				"	and w.fin_year is not null  " + 
				"	and wso.sdf_type_id in (3,4)  " + 
				"	and w.wsp_status_enum in ("+populateTaskStatusEnumResult(statusList)+")  " + 
				"order by c.levy_number";
		return (List<WspCompanyReportBean>) super.nativeSelectSqlList(sql, WspCompanyReportBean.class);
	}
	
	public List<WspCompanyReportBean> populateGrantSubmissionReportByCompanySummary(List<WspStatusEnum> statusList) throws Exception {		
		String sql = "select  " + 
				"  " + 
				"	c.levy_number as entityId  " + 
				"	, c.company_name as companyName  " + 
				"	, c.trading_name as tradingName  " + 
				"    , p.province_desc as province  " + 
				"    , r.description as region  " + 
				"    , count(w.id) as countSubmissions  " + 
				"      " + 
				"from   " + 
				"	wsp w  " + 
				"	  " + 
				"left join company c on c.id = w.company_id  " + 
				"left join address resAdd on resAdd.id = c.residential_address_id  " + 
				"left join municipality mp on mp.id = resAdd.municipality_id  " + 
				"left join province p on mp.provinces_idprovinces = p.id  " + 
				"left join region_town rt on rt.town_id = resAdd.town_id  " + 
				"left join region r on r.id = rt.region_id  " + 
				"  " + 
				"where  " + 
				"	w.company_id is not null  " + 
				"	and w.wsp_type is not null  " + 
				"	and w.fin_year is not null  " + 
				"	and w.wsp_status_enum in ("+populateTaskStatusEnumResult(statusList)+")  " + 
				"	  " + 
				"group by c.levy_number, c.company_name, c.trading_name, p.province_desc, r.description  " + 
				"  " + 
				"order by c.levy_number";
		return (List<WspCompanyReportBean>) super.nativeSelectSqlList(sql, WspCompanyReportBean.class);
	}
	
	public List<CounterBean> countGrantApprovalsByGrantYearAndCompanySize(Integer grantYear, CompanySizeEnum companySizeEnum, Date fromDate, Date toDate, List<WspStatusEnum> wspStatusList, List<WspTypeEnum> wspTypeList) throws Exception {
		String sql = "select     " +  
				"	count(w.id) as counter    " + 
				"from     " + 
				"	wsp w    " + 
				"inner join (    " + 
				"	select     " + 
				"		target_key    " + 
				"		, MAX(id) as lastest_id     " + 
				"	from     " + 
				"		wsp_company_main_history    " + 
				"	where    " + 
				"		target_class = 'haj.com.entity.Wsp'    " + 
				"	group by     " + 
				"		target_key    " + 
				") x on x.target_key = w.id    " + 
				"inner join wsp_company_history wch on wch.wsp_company_main_history_id = x.lastest_id    " + 
				"where    " + 
				"	w.fin_year = " + grantYear + "  ";
		if (!wspTypeList.isEmpty()) {
			sql += " and w.wsp_type in ("+populateWspTypeResults(wspTypeList)+")  ";
		}
		if (!wspStatusList.isEmpty()) {
			sql += " and w.wsp_status_enum in ("+populateTaskStatusEnumResult(wspStatusList)+")  ";
		}
		if (fromDate != null && toDate != null) {
//			sql += " and w.approved_date Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE)  ";
			sql +=  " and w.approved_date >= STR_TO_DATE('"+HAJConstants.sdf4.format(fromDate)+"', '%Y-%m-%d %H:%i:%s')   and   w.approved_date <= STR_TO_DATE('"+HAJConstants.sdf4.format(toDate)+"', '%Y-%m-%d %H:%i:%s') ";
		}
		if (companySizeEnum != null) {
			switch (companySizeEnum) {
			case Small:
				sql += "  and ( wch.numberOfEmployees is null or wch.numberOfEmployees < 50 ) ";
				break;
			case Medium:
				sql += "  and wch.numberOfEmployees > 49 and wch.numberOfEmployees < 150  ";
				break;
			case Large:
				sql += "  and wch.numberOfEmployees > 149  ";
				break;
			default:
				// fail safe
				sql += "  and ( wch.numberOfEmployees is null or wch.numberOfEmployees < 50 )  ";
				break;
			}
		}
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class);
	}
	
	public List<WspFirmSubmissionBean> populateGrantSubmissionNsdpReport(Integer grantYear, Date finYearStartDate, Date finYearEndDate, Date quarterStartDate, Date quarterEndDate
			, List<WspStatusEnum> wspStatusList, List<WspTypeEnum> wspTypeList, CompanySizeEnum companySizeEnum) throws Exception {
		String sql = "select   " + 
				"	wch.company_name as companyName  " + 
				"	, wch.levy_number as sdlNumber  " + 
				"	, w.submission_date as grantSubmissionDate  " + 
				"	, w.approved_date as grantApprovalDate  " + 
				"	, wch.numberOfEmployees as numberOfEmployeesOnComp  " + 
				"	, case  " + 
				"		when amount.gp_amount_paid is null then null  " + 
				"		else amount.gp_amount_paid  " + 
				"		end as totalMgPaid  " + 
				"	, wspAtr.count as numberOfAtr  " + 
				"	, empHist.totalEmployeesCount as totalEmployeesHistory  " + 
				"	, empUnempoyedHist.totalEmployeesCount as totalUnemployedEmployeesHistory  " + 
				"from   " + 
				"	wsp w  " + 
				"inner join (  " + 
				"	select   " + 
				"		target_key  " + 
				"		, MAX(id) as lastest_id   " + 
				"	from   " + 
				"		wsp_company_main_history  " + 
				"	where  " + 
				"		target_class = 'haj.com.entity.Wsp'  " + 
				"	group by   " + 
				"		target_key  " + 
				") x on x.target_key = w.id  " + 
				"inner join wsp_company_history wch on wch.wsp_company_main_history_id = x.lastest_id  " + 
				"left join (  " + 
				"	select   " + 
				"		gp.levy_number as gp_levy_number,  " + 
				"		sum(gp.mandatory_levy) as gp_amount_paid  " + 
				"	from   " + 
				"		gp_grant_batch_entry gp  " + 
				"	inner join   " + 
				"		gp_grant_batch_list gpl on gpl.id = gp.gp_grant_batch_list_id   " + 
				"		and gpl.wsp_type in (0)  " + 
				"		and gpl.approval_enum in (0)  " + 
				"	where  " + 
				"		gp.loaded_to_gp = true  " + 
				"		and gpl.date_final_approved >= STR_TO_DATE('"+HAJConstants.sdf4.format(finYearStartDate)+"', '%Y-%m-%d %H:%i:%s')	  " + 
				"		and gpl.date_final_approved <= STR_TO_DATE('"+HAJConstants.sdf4.format(finYearEndDate)+"', '%Y-%m-%d %H:%i:%s')	  " + 
				"	group by   " + 
				"		gp.levy_number  " + 
				") amount on amount.gp_levy_number = wch.levy_number  " + 
				"left join (  " + 
				"	select   " + 
				"		wsp_company_main_history_id as histId  " + 
				"		, count(*) as totalEmployeesCount  " + 
				"	from   " + 
				"		wsp_company_employees_history  " + 
				"	group by   " + 
				"		wsp_company_main_history_id  " + 
				") empHist on empHist.histId =  x.lastest_id  " + 
				"left join (  " + 
				"	select   " + 
				"		wche.wsp_company_main_history_id as histId  " + 
				"		, count(wche.id) as totalEmployeesCount  " + 
				"	from   " + 
				"		wsp_company_employees_history wche  " + 
				"	inner join employment_type et on et.id = wche.employment_type_id  " + 
				"	where  " + 
				"		et.employment_status = 1  " + 
				"	group by   " + 
				"		wche.wsp_company_main_history_id  " + 
				") empUnempoyedHist on empUnempoyedHist.histId =  x.lastest_id    " + 
				"left join (  " + 
				"	select   " + 
				"		w.id as wspId  " + 
				"		, count(mgd.id) as count  " + 
				"	from   " + 
				"		mandatory_grant_detail mgd  " + 
				"	inner join   " + 
				"		wsp w on w.id = mgd.wsp_id   " + 
				"		and w.fin_year = " + grantYear;
		if (!wspTypeList.isEmpty()) {
			sql += " and w.wsp_type in ("+populateWspTypeResults(wspTypeList)+")  ";
		}
		if (!wspStatusList.isEmpty()) {
			sql += " and w.wsp_status_enum in ("+populateTaskStatusEnumResult(wspStatusList)+")  ";
		}
		sql +=  "		and w.approved_date >= STR_TO_DATE('"+HAJConstants.sdf4.format(quarterStartDate)+"', '%Y-%m-%d %H:%i:%s')	  " + 
				"		and w.approved_date <= STR_TO_DATE('"+HAJConstants.sdf4.format(quarterEndDate)+"', '%Y-%m-%d %H:%i:%s')	  " + 
				"	where  " + 
				"		mgd.wsp_report = 3   " + 
				"		and mgd.funding_id = 6  " + 
				"		and mgd.imported = true  " + 
				"	group by w.id  " + 
				") wspAtr on wspAtr.wspId = w.id  " + 
				"where  " + 
				"	w.fin_year = " + grantYear;
		if (!wspTypeList.isEmpty()) {
			sql += " and w.wsp_type in ("+populateWspTypeResults(wspTypeList)+")  ";
		}
		if (!wspStatusList.isEmpty()) {
			sql += " and w.wsp_status_enum in ("+populateTaskStatusEnumResult(wspStatusList)+")  ";
		}
		sql +=  "	and w.approved_date >= STR_TO_DATE('"+HAJConstants.sdf4.format(quarterStartDate)+"', '%Y-%m-%d %H:%i:%s')	  " + 
				"	and w.approved_date <= STR_TO_DATE('"+HAJConstants.sdf4.format(quarterEndDate)+"', '%Y-%m-%d %H:%i:%s')	  " ;
		if (companySizeEnum != null) {
			switch (companySizeEnum) {
			case Small:
				sql += "  and wch.numberOfEmployees < 50  ";
				break;
			case Medium:
				sql += "  and wch.numberOfEmployees > 49 and wch.numberOfEmployees < 150  ";
				break;
			case Large:
				sql += "  and wch.numberOfEmployees > 149  ";
				break;
			default:
				// fail safe
				sql += "  and wch.numberOfEmployees < 50  ";
				break;
			}
		}
		sql += 	"   order by  " + 
				"	wch.levy_number";
		return (List<WspFirmSubmissionBean>) super.nativeSelectSqlList(sql, WspFirmSubmissionBean.class);
	}
	
	public List<GrantsSubmissionReportBean> populateGrantsSubmissionReportByGrantYear(Integer grantYear) throws Exception {
		String sql = "select     " + 
				"	case   " + 
				"		when wch.id is not null and wch.levy_number is not null then wch.levy_number   " + 
				"		ELSE c.levy_number   " + 
				"	end as entityId    " + 
				"	, case   " + 
				"		when wch.id is not null and wch.company_name is not null then wch.company_name   " + 
				"		ELSE c.company_name   " + 
				"	end as companyName   " + 
				"	, case   " + 
				"		when wch.id is not null and wch.trading_name is not null then wch.trading_name   " + 
				"		ELSE c.trading_name   " + 
				"	end as tradingName   " + 
				"	, case    " + 
				"		when sch.id is not null and sch.code is not null then sch.code   " + 
				"		else sc.code   " + 
				"	end as sicCode   " + 
				"	, case    " + 
				"		when sch.id is not null and sch.description is not null then sch.description   " + 
				"		else sc.description   " + 
				"	end as sicCodeDesc   " + 
				"	, w.fin_year as grantYear   " + 
				"	, case   " + 
				"		when w.wsp_type = 0 then 'Mandatory'   " + 
				"		when w.wsp_type = 1 then 'Discretionary'   " + 
				"		when w.wsp_type = 2 then 'Mandatory and Discretionary'   " + 
				"		when w.wsp_type = 3 then 'Inter-SETA Transfer Mandatory Grant'   " + 
				"		when w.wsp_type = 4 then 'Legacy Linked Company Grant Application'   " + 
				"	end as wspType   " + 
				"	, case    " + 
				"		when ph.id is not null and ph.province_desc is not null then ph.province_desc   " + 
				"		else p.province_desc   " + 
				"	end as province   " + 
				"	, case   " + 
				"		when rh.id is not null and rh.description is not null then rh.description   " + 
				"		else r.description   " + 
				"	end as region   " + 
				"	, case   " + 
				"		when wch.id is not null and wch.numberOfEmployees is not null then wch.numberOfEmployees   " + 
				"		ELSE c.numberOfEmployees   " + 
				"	end as numberOfEmployees   " + 
				"	, case   " + 
				"		when wch.id is not null and wch.numberOfEmployees is not null then   " + 
				"			case   " + 
				"				when wch.numberOfEmployees < 50 then 'Small'   " + 
				"				when wch.numberOfEmployees < 150 and wch.numberOfEmployees > 49 then 'Medium'   " + 
				"				when wch.numberOfEmployees > 149 then 'Large'   " + 
				"				Else 'Small'   " + 
				"			end   " + 
				"		Else   " + 
				"			case   " + 
				"				when c.numberOfEmployees < 50 then 'Small'   " + 
				"				when c.numberOfEmployees < 150 and c.numberOfEmployees > 49 then 'Medium'   " + 
				"				when c.numberOfEmployees > 149 then 'Large'   " + 
				"				Else 'Small'   " + 
				"			end   " + 
				"	end as companySize   " + 
				"	, case   " + 
				"		when wch.id is not null then   " + 
				"			case   " + 
				"				when wch.majority_union_id is null then 'No'   " + 
				"				when wch.majority_union_id is not null then 'Yes'   " + 
				"				else 'N/A'   " + 
				"			end   " + 
				"		Else   " + 
				"			case   " + 
				"				when c.majority_union_id is null then 'No'   " + 
				"				when c.majority_union_id is not null then 'Yes'   " + 
				"				else 'N/A'   " + 
				"			end   " + 
				"	end as unionAssigned   " + 
				"	, case   " + 
				"		when wch.id is not null then   " + 
				"			case   " + 
				"				when wch.majority_union_id is null then 'N/A'   " + 
				"				when wch.majority_union_id is not null and oluh.id is not null then oluh.description   " + 
				"				else 'N/A'   " + 
				"			end   " + 
				"		Else   " + 
				"			case   " + 
				"				when c.majority_union_id is null then 'N/A'   " + 
				"				when c.majority_union_id is not null and olu.id is not null then olu.description   " + 
				"				else 'N/A'   " + 
				"			end   " + 
				"	end as unionName   " + 
				"	, w.submission_date as submissionDate   " + 
				"	, case   " + 
				"		when extensionRequestCounter.wspId is not null then 'Yes'   " + 
				"		else 'No'    " + 
				"	end as extensionRequestFound   " + 
				"	, w.approved_date as approvedDate   " + 
				"	, case   " + 
				"		when w.wsp_status_enum = 0 then 'Not Submitted-In Progress'    " + 
				"		when w.wsp_status_enum = 1 then 'Pending Sign Off'    " + 
				"		when w.wsp_status_enum = 2 then 'Pending Approval'    " + 
				"		when w.wsp_status_enum = 3 then 'Dispute'    " + 
				"		when w.wsp_status_enum = 4 then 'Approved'    " + 
				"		when w.wsp_status_enum = 5 then 'Rejected'    " + 
				"		when w.wsp_status_enum = 6 then 'Reviewed Pending Approval'   " + 
				"		when w.wsp_status_enum = 7 then 'Reviewed Pending Final Decision'   " + 
				"		when w.wsp_status_enum = 8 then 'Received For Record Purposes'   " + 
				"		when w.wsp_status_enum = 9 then 'Opened For Review'   " + 
				"		when w.wsp_status_enum = 10 then 'NA'   " + 
				"		when w.wsp_status_enum = 11 then 'Withdrawn'   " + 
				"		when w.wsp_status_enum = 11 then 'Awaiting MANCO Approval'   " + 
				"	end as wspStatus   " + 
				"	, case   " + 
				"		when w.wsp_status_enum = 5    " + 
				"			then (   " + 
				"				select    " + 
				"					GROUP_CONCAT(description)   " + 
				"				from    " + 
				"					reject_reasons   " + 
				"				where    " + 
				"					id in (   " + 
				"						select   " + 
				"							reject_reason_id   " + 
				"						from    " + 
				"							reject_reason_multiple_selection   " + 
				"						where   " + 
				"							target_key = w.id   " + 
				"							and target_class = 'haj.com.entity.Wsp'   " + 
				"							and for_process = '5'   " + 
				"					)   " + 
				"			)			   " + 
				"		else 'N/A'   " + 
				"	end as rejectReasons   " + 
				"From    " + 
				"	wsp w   " + 
				"left join (   " + 
				"	select    " + 
				"		target_key   " + 
				"		, MAX(id) as lastest_id    " + 
				"	from    " + 
				"		wsp_company_main_history   " + 
				"	where   " + 
				"		target_class = 'haj.com.entity.Wsp'   " + 
				"	group by    " + 
				"		target_key   " + 
				") x on x.target_key = w.id   " + 
				"left join wsp_company_history wch on wch.wsp_company_main_history_id = x.lastest_id   " + 
				"left join organised_labour_union oluh on oluh.id = wch.majority_union_id   " + 
				"left join sic_code sch on sch.id = wch.sic_code_id   " + 
				"left join chamber chamh on chamh.id = sch.chamber_id   " + 
				"left join organisation_type oth on oth.id = wch.organisation_type_id   " + 
				"left join wsp_company_address_history resAddHist on resAddHist.id = wch.wsp_company_address_history_residential_id   " + 
				"left join region_town rth on rth.town_id = resAddHist.town_id   " + 
				"left join region rh on rh.id = rth.region_id   " + 
				"left join towns th on th.id = rth.town_id   " + 
				"left join municipality mh on mh.id = resAddHist.municipality_id   " + 
				"left join province ph on ph.id = mh.provinces_idprovinces   " + 
				"   " + 
				"inner join company c on c.id = w.company_id   " + 
				"left join organised_labour_union olu on olu.id = c.majority_union_id   " + 
				"left join sic_code sc on sc.id = c.sic_code_id   " + 
				"left join chamber cham on cham.id = sc.chamber_id   " + 
				"left join organisation_type ot on ot.id = c.organisation_type_id   " + 
				"left join address resAdd on resAdd.id = c.residential_address_id   " + 
				"left join region_town rt on rt.town_id = resAdd.town_id   " + 
				"left join region r on r.id = rt.region_id   " + 
				"left join towns t on t.id = rt.town_id   " + 
				"left join municipality m on m.id = resAdd.municipality_id   " + 
				"left join province p on p.id = m.provinces_idprovinces   " + 
				"   " + 
				"left join (   " + 
				"	select    " + 
				"		wsp_id as wspId   " + 
				"		, count(*) as counter    " + 
				"	from    " + 
				"		extension_request    " + 
				"	where    " + 
				"		status = 0    " + 
				"		and wsp_id is not null    " + 
				"	group by    " + 
				"		wsp_id	   " + 
				") extensionRequestCounter on extensionRequestCounter.wspId = w.id   " + 
				"   " + 
				"where   " + 
				"	w.fin_year = " + grantYear.toString() ;
		return (List<GrantsSubmissionReportBean>) super.nativeSelectSqlList(sql, GrantsSubmissionReportBean.class);
	}
	
	public String populateTaskStatusEnumResult(List<WspStatusEnum> entryList) throws Exception{
		StringBuilder result = new StringBuilder();
		int counter = 1;
		for (WspStatusEnum entry : entryList) {
			result.append(entry.ordinal());
			if (entryList.size() != counter) {
				result.append(" , ");
			}
			counter ++;
		}
		return result.toString();
	}
	
	public String populateWspTypeResults(List<WspTypeEnum> entryList) throws Exception{
		StringBuilder result = new StringBuilder();
		int counter = 1;
		for (WspTypeEnum entry : entryList) {
			result.append(entry.ordinal());
			if (entryList.size() != counter) {
				result.append(" , ");
			}
			counter ++;
		}
		return result.toString();
	}
	


	@SuppressWarnings("unchecked")
	public List<Wsp> findByCompany(Long companyId, WspTypeEnum wspTypeEnum) throws Exception {
		String              hql        = "select o from Wsp o" + leftJoins + " where o.company.id = :companyId and o.wspType = :wspTypeEnum order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("wspTypeEnum", wspTypeEnum);
		return (List<Wsp>) super.getList(hql, parameters);
	}


	
	@SuppressWarnings("unchecked")
	public List<Wsp> findByStatus(WspStatusEnum statusEnum) throws Exception {
		String hql = "select o from Wsp o" + leftJoins + " where o.wspStatusEnum = :pendingSignOff order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("pendingSignOff", statusEnum);
		return (List<Wsp>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Wsp> allWspByStatusWithRecommendation(WspStatusEnum wspStatusEnum) throws Exception {
		String hql = "select o from Wsp o" + leftJoins + " where o.wspStatusEnum = :pendingSignOff and o.holdingRoomStatusEnum is not null order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("pendingSignOff", wspStatusEnum);
		return (List<Wsp>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Wsp> allWspByStatus(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from Wsp o" + leftJoins + " where o.wspStatusEnum = :pendingSignOff and o.holdingRoomStatusEnum is not null order by o.createDate desc ";
		return (List<Wsp>) sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countWspByStatus(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Wsp o where o.wspStatusEnum = :pendingSignOff and o.holdingRoomStatusEnum is not null order by o.createDate desc ";
		return countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<Wsp> allWspByStatusNotRecommended(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from Wsp o" + leftJoins + " where o.wspStatusEnum = :pendingSignOff and o.holdingRoomStatusEnum is null and o.projectOwner is null order by o.createDate desc ";
		return (List<Wsp>) sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countWspByStatusNotRecommended(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Wsp o where o.wspStatusEnum = :pendingSignOff and o.holdingRoomStatusEnum is null and o.projectOwner is null order by o.createDate desc ";
		return countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<Wsp> allWspByStatusProjectOwner(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from Wsp o" + leftJoins + " where o.wspStatusEnum = :pendingSignOff and o.projectOwner.id = :projectOwnerID and o.holdingRoomStatusEnum is null order by o.createDate desc ";
		return (List<Wsp>) sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countWspByStatusProjectOwner(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Wsp o where o.wspStatusEnum = :pendingSignOff and o.projectOwner.id = :projectOwnerID and o.holdingRoomStatusEnum is null order by o.createDate desc ";
		return countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<Wsp> allWspProjectOwner(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from Wsp o" + leftJoins + " where o.projectOwner.id = :projectOwnerID order by o.createDate desc ";
		return (List<Wsp>) sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countWspProjectOwner(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Wsp o where o.projectOwner.id = :projectOwnerID order by o.createDate desc ";
		return countWhere(filters, hql);
	}
}
