package haj.com.dao;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.bean.ATRReportSummayBean;
import haj.com.bean.DGApplicationSummaryBean;
import haj.com.bean.EmpEmploymentStatusBean;
import haj.com.bean.EmployeeEquityProfileBean;
import haj.com.bean.EmployeeProfileBean;
import haj.com.bean.GrantSspReportBean;
import haj.com.bean.PivotalTrainingReportBean;
import haj.com.bean.WSPReportSummayBean;
import haj.com.constants.HAJConstants;
import haj.com.entity.Company;
import haj.com.entity.DgAllocation;
import haj.com.entity.MandatoryGrant;
import haj.com.entity.MandatoryGrantDetail;
import haj.com.entity.MgVerificationDetails;
import haj.com.entity.ProjectImplementationPlan;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.enums.WspReportEnum;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.entity.enums.WspTypeEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class MandatoryGrantDetailDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all MandatoryGrantDetail
	 * 
	 * @author TechFinium
	 * @see MandatoryGrantDetail
	 * @return a list of {@link haj.com.entity.MandatoryGrantDetail}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetail> allMandatoryGrantDetail() throws Exception {
		return (List<MandatoryGrantDetail>) super.getList("select o from MandatoryGrantDetail o");
	}

	/**
	 * Get all MandatoryGrantDetail between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see MandatoryGrantDetail
	 * @return a list of {@link haj.com.entity.MandatoryGrantDetail}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetail> allMandatoryGrantDetail(Long from, int noRows) throws Exception {
		String hql = "select o from MandatoryGrantDetail o where o.imported = true ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<MandatoryGrantDetail>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	@SuppressWarnings("unchecked")
	public List<MgVerificationDetails> allMgVerificationDetails(Long wspID) throws Exception {
		String hql = "select o from MgVerificationDetails o where o.mandatoryGrantDetail.wsp.id = :wspID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wspID);
		return (List<MgVerificationDetails>) super.getList(hql, parameters);
	}

	public Long allMgVerificationDetailsCount(Long wspID) throws Exception {
		String hql = "select count(o) from MgVerificationDetails o where o.mandatoryGrantDetail.wsp.id = :wspID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wspID);
		return (Long) super.getUniqueResult(hql, parameters);
	}

	public Long allMgVerificationDetailsByWspIdCount(Long wspID) throws Exception {
		String hql = "select count(o) from MgVerificationDetails o where o.wsp.id = :wspID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wspID);
		return (Long) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see MandatoryGrantDetail
	 * @return a {@link haj.com.entity.MandatoryGrantDetail}
	 * @throws Exception
	 *             global exception
	 */

	public MandatoryGrantDetail findByKey(Long id) throws Exception {
		String hql = "select o from MandatoryGrantDetail o where o.id = :id and  o.imported = true ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (MandatoryGrantDetail) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find MandatoryGrantDetail by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see MandatoryGrantDetail
	 * @return a list of {@link haj.com.entity.MandatoryGrantDetail}
	 * @throws Exception
	 *             global exception
	 */

	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetail> findByName(String description) throws Exception {
		String hql = "select o from MandatoryGrantDetail o where o.description like  :description and  o.imported = true  order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<MandatoryGrantDetail>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetail> findByWSP(Wsp wsp, WspReportEnum wspReport) throws Exception {
		String hql = "select o from MandatoryGrantDetail o left join fetch o.ofoCodes oc left join fetch oc.ofoCodes where o.wsp.id = :wspID and o.wspReport =:wspReport and  o.imported = true ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("wspReport", wspReport);
		return (List<MandatoryGrantDetail>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetail> findByWSP(Wsp wsp) throws Exception {
		String hql = "select o from MandatoryGrantDetail o left join fetch o.ofoCodes oc left join fetch oc.ofoCodes where o.wsp.id = :wspID and o.imported = true ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		return (List<MandatoryGrantDetail>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetail> findByWSPByFundingAndReportType(Wsp wsp) throws Exception {
		String hql = "select o from MandatoryGrantDetail o " + "left join fetch o.ofoCodes oc "
				+ "left join fetch oc.ofoCodes " + "where o.wsp.id = :wspID " + "and o.imported = true "
				+ "and o.funding.id = :fundingID " + "and o.wspReport = :wspReport ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("fundingID", HAJConstants.MAN_FUNDING_ID);
		parameters.put("wspReport", WspReportEnum.WSP);
		return (List<MandatoryGrantDetail>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetail> findByWSPForMgverification(Wsp wsp, long finYear) throws Exception {

		String fundingCode = "SETAMG_Fun";
		String enrolmentStatusCode1 = "3";
		String enrolmentStatusCode2 = "4";
		String hql = "select o from MandatoryGrantDetail o left join fetch o.ofoCodes oc left join fetch oc.ofoCodes where o.wsp.id = :wspID and o.imported = true "
				+ "and o.wsp.id not in (select x.wsp.id from MgVerification x where x.finalFin > :finYear) "
				+ "and o.fundingCode =:fundingCode and  (o.enrolmentStatusCode =:enrolmentStatusCode1 or o.enrolmentStatusCode =:enrolmentStatusCode2)"
				+ "group by o.ofoCodes, o.interventionType";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("finYear", finYear);
		parameters.put("fundingCode", fundingCode);
		parameters.put("enrolmentStatusCode1", enrolmentStatusCode1);
		parameters.put("enrolmentStatusCode2", enrolmentStatusCode2);
		return (List<MandatoryGrantDetail>) super.getList(hql, parameters);
	}

	public long findByMandatoryGrantDetailCountOFO(Wsp wsp, long finYear) throws Exception {
		String fundingCode = "SETAMG_Fun";
		String enrolmentStatusCode1 = "3";
		String enrolmentStatusCode2 = "4";
		String hql = "select o from MandatoryGrantDetail o left join fetch o.ofoCodes oc left join fetch oc.ofoCodes where o.wsp.id = :wspID and o.imported = true "
				+ "and o.wsp.id not in (select x.wsp.id from MgVerification x where x.finalFin > :finYear) "
				+ "and o.fundingCode =:fundingCode and (o.enrolmentStatusCode =:enrolmentStatusCode1 or o.enrolmentStatusCode =:enrolmentStatusCode2)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("finYear", finYear);
		parameters.put("fundingCode", fundingCode);
		parameters.put("enrolmentStatusCode1", enrolmentStatusCode1);
		parameters.put("enrolmentStatusCode2", enrolmentStatusCode2);
		return (long) super.getUniqueResult(hql, parameters);
	}

	public long findByWSPCount(Wsp wsp) throws Exception {
		String hql = "select count(o) from MandatoryGrantDetail o where o.wsp.id = :wspID and o.imported = true ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		return (long) super.getUniqueResult(hql, parameters);
	}

	public long findByWSPByFuningAndReportTypeCount(Wsp wsp) throws Exception {
		String hql = "select count(o) from MandatoryGrantDetail o  " + "where o.wsp.id = :wspID "
				+ "and o.imported = true " + "and o.funding.id = :fundingID " + "and o.wspReport = :wspReport ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("fundingID", HAJConstants.MAN_FUNDING_ID);
		parameters.put("wspReport", WspReportEnum.WSP);
		return (long) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Locates count of MandatoryGrantDetail assigned to wsp by wsp id and
	 * WspReportEnum
	 * 
	 * @param wsp
	 * @param wspReport
	 * @see WspReportEnum
	 * @see Wsp
	 * @see MandatoryGrantDetail
	 * @return long
	 * @throws Exception
	 */
	public long findByWSPAndReportCount(Wsp wsp, WspReportEnum wspReport) throws Exception {
		String hql = "select count(o) from MandatoryGrantDetail o where o.wsp.id = :wspID and o.wspReport =:wspReport and o.imported = true ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("wspReport", wspReport);
		return (long) super.getUniqueResult(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetail> findByWsp(Class<MandatoryGrantDetail> entity, int startingAt, int pageSize,
			String sortField, SortOrder sortOrder, Map<String, Object> filters, Long wspid, WspReportEnum wspReport) {
		String hql = "select o from " + entity.getName()
				+ " o left join fetch o.ofoCodes oc left join fetch oc.ofoCodes where o.wsp.id = :wsp and o.wspReport =:wspReport and  o.imported = true ";
		filters.put("wsp", wspid);
		filters.put("wspReport", wspReport);
		return (List<MandatoryGrantDetail>) super.sortAndFilterWhere(startingAt, pageSize, sortField, sortOrder,
				filters, hql);
	}

	public long allMandatoryGrantDetailCount(Map<String, Object> filters, Long wspid, WspReportEnum wspReport)
			throws Exception {
		String hql = "select count(o) from MandatoryGrantDetail o  where o.wsp.id = :wsp and o.wspReport =:wspReport and  o.imported = true";
		filters.put("wsp", wspid);
		filters.put("wspReport", wspReport);
		return (long) super.getUniqueResult(hql, filters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetail> findByWspCsv(Class<MandatoryGrantDetail> entity, int startingAt, int pageSize,
			String sortField, SortOrder sortOrder, Map<String, Object> filters, Long wspid, WspReportEnum wspReport) {
		String hql = "select o from MandatoryGrantDetail o where o.wsp.id = :wspID and o.wspReport =:wspReport and  o.imported = false ";
		filters.put("wspID", wspid);
		filters.put("wspReport", wspReport);
		return (List<MandatoryGrantDetail>) super.sortAndFilterWhere(startingAt, pageSize, sortField, sortOrder,
				filters, hql);
	}

	public long allMandatoryGrantDetailCountCsv(Map<String, Object> filters, Long wspid, WspReportEnum wspReport)
			throws Exception {
		String hql = "select count(o) from MandatoryGrantDetail o where o.wsp.id = :wspID and o.wspReport =:wspReport and  o.imported = false ";
		filters.put("wspID", wspid);
		filters.put("wspReport", wspReport);
		return (long) super.getUniqueResult(hql, filters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetail> findByWSPCsv(Wsp wsp, WspReportEnum wspReport) throws Exception {
		String hql = "select o from MandatoryGrantDetail o where o.wsp.id = :wspID and o.wspReport =:wspReport and  o.imported = false ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("wspReport", wspReport);
		return (List<MandatoryGrantDetail>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetail> findByWSPCsvAll(Wsp wsp, WspReportEnum wspReport) throws Exception {
		String hql = "select o from MandatoryGrantDetail o where o.wsp.id = :wspID and o.wspReport =:wspReport";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("wspReport", wspReport);
		return (List<MandatoryGrantDetail>) super.getList(hql, parameters);
	}

	public long findByWSPCount(Wsp wsp, WspReportEnum wspReport) throws Exception {
		String hql = "select count(o) from MandatoryGrantDetail o where o.wsp.id = :wspID and o.wspReport =:wspReport and  o.imported = true ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("wspReport", wspReport);
		return (long) super.getUniqueResult(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetail> findByWSPPivotalPlan(Class<MandatoryGrantDetail> entity, int startingAt,
			int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Wsp wsp,
			WspReportEnum wspReport, Long nqfAlignedID) {
		String hql = "select o from " + entity.getName()
				+ " o left join fetch o.ofoCodes oc left join fetch oc.ofoCodes where o.wsp.id = :wsp and o.wspReport =:wspReport and o.nqfAligned.id = :nqfAligned and o.imported = true ";
		filters.put("wsp", wsp.getId());
		filters.put("wspReport", wspReport);
		filters.put("nqfAligned", nqfAlignedID);
		return (List<MandatoryGrantDetail>) super.sortAndFilterWhere(startingAt, pageSize, sortField, sortOrder,
				filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetail> atrSubmitedCheck(Wsp wsp, WspReportEnum wspReport) throws Exception {
		String hql = "select o from MandatoryGrantDetail o where o.wsp.id = :wspID and o.wspReport =:wspReport and  o.imported = true";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("wspReport", wspReport);
		return (List<MandatoryGrantDetail>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetail> wspPivotalPlanCheck(Wsp wsp, WspReportEnum wspReport, Long nqfAlignedID) {
		String hql = "select o from MandatoryGrantDetail o where o.wsp.id = :wsp and o.wspReport =:wspReport and o.nqfAligned.id = :nqfAligned and  o.imported = true";
		Map<String, Object> filters = new Hashtable<String, Object>();
		filters.put("wsp", wsp.getId());
		filters.put("wspReport", wspReport);
		filters.put("nqfAligned", nqfAlignedID);
		return (List<MandatoryGrantDetail>) super.getList(hql, filters);
	}

	public long allMandatoryGrantDetailCountWSPPivotalPlan(Map<String, Object> filters, Wsp wsp,
			WspReportEnum wspReport, Long nqfAlignedID) throws Exception {
		String hql = "select count(o) from MandatoryGrantDetail o  where o.wsp.id = :wsp and o.wspReport =:wspReport and o.nqfAligned.id = :nqfAligned and  o.imported = true ";
		filters.put("wsp", wsp.getId());
		filters.put("wspReport", wspReport);
		filters.put("nqfAligned", nqfAlignedID);
		return (long) super.getUniqueResult(hql, filters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetail> findByWSPPivotalPlan(Wsp wsp, WspReportEnum wspReport, Long nqfAlignedID)
			throws Exception {
		String hql = "select o from MandatoryGrantDetail o left join fetch o.ofoCodes oc left join fetch oc.ofoCodes where o.wsp.id = :wspID and o.wspReport =:wspReport and o.nqfAligned.id = :nqfAligned and  o.imported = true ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("wspReport", wspReport);
		parameters.put("nqfAligned", nqfAlignedID);
		return (List<MandatoryGrantDetail>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetail> findByWSPNotHosting(Wsp wsp, WspReportEnum wspReport, String etqaCode)
			throws Exception {
		String hql = "select o from MandatoryGrantDetail o left join fetch o.ofoCodes oc left join fetch oc.ofoCodes where o.wsp.id = :wspID and o.wspReport =:wspReport and o.etqa.code <> :etqaCode and o.etqa is not null and  o.imported = true  ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("wspReport", wspReport);
		parameters.put("etqaCode", etqaCode);
		return (List<MandatoryGrantDetail>) super.getList(hql, parameters);
	}

	public long findCountCreatedMandatoryGrant(Wsp wsp) throws Exception {
		String hql = "select count(o) from MandatoryGrant o where o.wsp.id = :wspId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wsp.getId());
		return (long) super.getUniqueResult(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetail> findByWSPNotHosting(Class<MandatoryGrantDetail> entity, int startingAt,
			int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long wspid,
			WspReportEnum wspReport, String etqaCode) {
		String hql = "select o from MandatoryGrantDetail o left join fetch o.ofoCodes oc left join fetch oc.ofoCodes where o.wsp.id = :wspID and o.wspReport =:wspReport and o.etqa.code <> :etqaCode and o.etqa is not null and  o.imported = true  ";
		filters.put("wspID", wspid);
		filters.put("wspReport", wspReport);
		filters.put("etqaCode", etqaCode);
		return (List<MandatoryGrantDetail>) super.sortAndFilterWhere(startingAt, pageSize, sortField, sortOrder,
				filters, hql);
	}

	public long findByWSPNotHostingCount(Long wspid, WspReportEnum wspReport, String etqaCode) throws Exception {
		Map<String, Object> filters = new Hashtable<String, Object>();
		String hql = "select count(o) from MandatoryGrantDetail o where o.wsp.id = :wspID and o.wspReport =:wspReport and o.etqa.code <> :etqaCode and o.etqa is not null and  o.imported = true  ";
		filters.put("wspID", wspid);
		filters.put("wspReport", wspReport);
		filters.put("etqaCode", etqaCode);
		return (long) super.getUniqueResult(hql, filters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetail> findByWSPHosting(WspReportEnum wspReport, String etqaCode) throws Exception {
		String hql = "select o from MandatoryGrantDetail o where o.wspReport =:wspReport and o.etqa.code = :etqaCode and o.etqa is not null and  o.imported = true ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspReport", wspReport);
		parameters.put("etqaCode", etqaCode);
		return (List<MandatoryGrantDetail>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetail> findByWSPHosting(Wsp wsp, WspReportEnum wspReport, long fundingID)
			throws Exception {
		String hql = "select o from MandatoryGrantDetail o left join fetch o.ofoCodes oc left join fetch oc.ofoCodes left join fetch o.etqa e where o.wsp.id = :wspID and o.wspReport =:wspReport and o.funding.id = :fundingID and  o.imported = true ";
		// String hql = "select o from MandatoryGrantDetail o where o.wsp.id =
		// :wspID
		// and o.wspReport =:wspReport and (o.etqa.code = :etqaCode or
		// o.funding.id =
		// :fundingID)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("wspReport", wspReport);
		parameters.put("fundingID", fundingID);
		return (List<MandatoryGrantDetail>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetail> findByCompanyFinYear(Company company, WspReportEnum wspReport, long fundingID,
			long finYear) throws Exception {
		String hql = "select o from MandatoryGrantDetail o left join fetch o.etqa e where o.wsp.companyID.id = :wspID and o.wsp.finYear = :finYear and o.wspReport =:wspReport and o.funding.id = :fundingID and  o.imported = true ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("wspReport", wspReport);
		parameters.put("fundingID", fundingID);
		parameters.put("finYear", finYear);
		return (List<MandatoryGrantDetail>) super.getList(hql, parameters);
	}

	public Double findByCompanyFinYearTotal(Company company, WspReportEnum wspReport, long fundingID, int finYear)
			throws Exception {
		String hql = "select sum(o.estimatedCost) from MandatoryGrantDetail o where o.wsp.company.id = :companyID and o.wsp.finYear = :finYear and o.wspReport =:wspReport and o.funding.id = :fundingID and  o.imported = true ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("wspReport", wspReport);
		parameters.put("fundingID", fundingID);
		parameters.put("finYear", finYear);
		return (Double) super.getUniqueResult(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<Wsp> findRandomCompanies(Integer finYear, int limit) {
		String hql = "select distinct(o.wsp) from MandatoryGrantDetail o where o.wsp.finYear =:finYear and o.wsp.id not in (select x.wsp.id from MgVerification x where x.finalFin > :finYear) and  o.imported = true and o.wsp.company is not null order by rand()  ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("finYear", finYear);
		return (List<Wsp>) super.getList(hql, parameters, limit);
	}

	@SuppressWarnings("unchecked")
	public List<Wsp> findRandomCompaniesForMgVerification(Integer finYear, int limit) {
		String fundingCode = "SETAMG_Fun";
		String enrolmentStatusCode1 = "3";
		String enrolmentStatusCode2 = "4";
		String hql = "select distinct(o.wsp) from MandatoryGrantDetail o where o.wsp.finYear =:finYear "
				+ "and o.wsp.id not in (select x.wsp.id from MgVerification x where x.finalFin > :finYear) "
				+ "and o.fundingCode =:fundingCode and  (o.enrolmentStatusCode =:enrolmentStatusCode1 or o.enrolmentStatusCode =:enrolmentStatusCode2)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("finYear", finYear);
		parameters.put("fundingCode", fundingCode);
		parameters.put("enrolmentStatusCode1", enrolmentStatusCode1);
		parameters.put("enrolmentStatusCode2", enrolmentStatusCode2);
		return (List<Wsp>) super.getList(hql, parameters, limit);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetail> findRandomMandatoryGrantDetailCompanies(Integer finYear, int limit) {
		long fundingID = 6;
		String hql = "select o from MandatoryGrantDetail o where o.funding.id = :fundingID and o.wsp.id not in (select x.wsp.id from MgVerification x)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("fundingID", fundingID);
		return (List<MandatoryGrantDetail>) super.getList(hql, parameters, limit);
	}

	@SuppressWarnings("unchecked")
	public List<Wsp> findCompaniesForVerification(Integer finYear) {
		String hql = "select distinct(o.wsp) from MandatoryGrantDetail o where o.wsp.finYear =:finYear and  o.imported = true ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("finYear", finYear);
		return (List<Wsp>) super.getList(hql, parameters);
	}

	public Long findMGForDeviation(WspReportEnum wspReport, Integer finYear, Long companyID) {
		String hql = "select count(o.id) from MandatoryGrantDetail o where o.wspReport =:wspReport and o.wsp.finYear = :finYear and o.wsp.company.id = :companyID and  o.imported = true ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("finYear", finYear);
		parameters.put("wspReport", wspReport);
		parameters.put("companyID", companyID);
		return (Long) super.getUniqueResult(hql, parameters);
	}

	public long countForYear(Long finYear) {
		String hql = "select count(x) from MgVerification x where x.finalFin = :finYear";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("finYear", finYear);
		return (long) super.getUniqueResult(hql, parameters);
	}
	
	public long countForWspYear(Long finYear) {
		String hql = "select count(x) from MgVerification x where x.wsp.finYear = :finYear";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("finYear", finYear.intValue());
		return (long) super.getUniqueResult(hql, parameters);
	}

	public long countDistinctWSP(Integer finYear) {
		String hql = "select count(distinct o.wsp) from MandatoryGrantDetail o where o.wsp.finYear =:finYear and o.wsp.id not in (select x.wsp.id from MgVerification x where x.finalFin > :finYear) and  o.imported = true";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("finYear", finYear);
		return (long) super.getUniqueResult(hql, parameters);
	}
	
	public long countDistinctWSPPassedDraft(Integer finYear) {
		String hql = "select count(distinct o.wsp) from MandatoryGrantDetail o where o.wsp.finYear =:finYear and o.wsp.wspStatusEnum <> :draftStatus and o.wsp.id not in (select x.wsp.id from MgVerification x where x.finalFin > :finYear) and  o.imported = true";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("finYear", finYear);
		parameters.put("draftStatus", WspStatusEnum.Draft);
		return (long) super.getUniqueResult(hql, parameters);
	}

	public int countDistinctWSPByClo(Integer finYear, Users clo) throws Exception {
		String hql = "select count(distinct o.wsp) from MandatoryGrantDetail o " 
				+ "where o.wsp.finYear = :finYear "
				+ "and o.wsp.id not in (select x.wsp.id from MgVerification x where x.finalFin > :finYear) "
				+ "and o.imported = true " 
				+ "and o.funding.id = :fundingID " 
				+ "and o.wspReport = :wspReport "
				+ "and o.wsp.company is not null "
				+ "and o.wsp.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :cloID)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("finYear", finYear);
		parameters.put("cloID", clo.getId());
		parameters.put("fundingID", HAJConstants.MAN_FUNDING_ID);
		parameters.put("wspReport", WspReportEnum.WSP);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public int countDistinctWSPByCloAndFinalYear(Integer finYear, Users clo, Long finalYear) throws Exception {
		String hql = "select count(distinct o.wsp) from MandatoryGrantDetail o " 
				+ "where o.wsp.finYear = :finYear "
				+ "and o.wsp.company is not null "
				+ "and o.wsp.wspStatusEnum <> :draftStatus "
				+ "and o.wsp.id not in (select x.wsp.id from MgVerification x) "
				+ "and o.wsp.company.id not in (select y.wsp.company.id from MgVerification y where y.finalFin = :finalYear) "
				+ "and o.imported = true " 
				+ "and o.funding.id = :fundingID " 
				+ "and o.wspReport = :wspReport "
				+ "and o.wsp.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :cloID)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("finYear", finYear);
		parameters.put("finalYear", finalYear);
		parameters.put("cloID", clo.getId());
		parameters.put("fundingID", HAJConstants.MAN_FUNDING_ID);
		parameters.put("wspReport", WspReportEnum.WSP);
		parameters.put("draftStatus", WspStatusEnum.Draft);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Wsp> findRandomCompaniesByClo(Integer finYear, int limit, Users clo) throws Exception {
		String hql = "select distinct(o.wsp) from MandatoryGrantDetail o " + "where o.wsp.finYear =:finYear "
				+ "and o.wsp.id not in (select x.wsp.id from MgVerification x where x.finalFin > :finYear) "
				// + "and o.wsp.company.id not in (select x.wsp.company.id from
				// MgVerification x where x.finalFin > :finYear) " should be
				// this, need to confirm
				+ "and o.imported = true " + "and o.funding.id = :fundingID " + "and o.wspReport = :wspReport "
				+ "and o.wsp.company is not null "
				+ "and o.wsp.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :cloID) "
				+ "order by rand()  ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("finYear", finYear);
		parameters.put("cloID", clo.getId());
		parameters.put("fundingID", HAJConstants.MAN_FUNDING_ID);
		parameters.put("wspReport", WspReportEnum.WSP);
		return (List<Wsp>) super.getList(hql, parameters, limit);
	}

	@SuppressWarnings("unchecked")
	public List<Wsp> findRandomCompaniesByCloAndFinalYear(Integer finYear, int limit, Users clo, Long finalYear) throws Exception {
		String hql = "select distinct(o.wsp) from MandatoryGrantDetail o " 
				+ "where o.wsp.finYear =:finYear "
				+ "and o.wsp.id not in (select x.wsp.id from MgVerification x where x.finalFin > :finYear) "
				+ "and o.wsp.company is not null "
				+ "and o.wsp.company.id not in (select y.wsp.company.id from MgVerification y where y.finalFin = :finalYear) "
				+ "and o.imported = true " 
				+ "and o.funding.id = :fundingID "
				+ "and o.enrolmentStatus.id in (:enrolmentStatusIdOne, :enrolmentStatusIdTwo) " 
				+ "and o.wspReport = :wspReport "
				+ "and o.wsp.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :cloID) "
				+ "order by rand()  ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("finYear", finYear);
		parameters.put("finalYear", finalYear);
		parameters.put("cloID", clo.getId());
		parameters.put("fundingID", HAJConstants.MAN_FUNDING_ID);
		parameters.put("wspReport", WspReportEnum.WSP);
		parameters.put("enrolmentStatusIdOne", HAJConstants.WSP_ENROLEMNT_STATUS_WSP_NEW);
		parameters.put("enrolmentStatusIdTwo", HAJConstants.WSP_ENROLEMNT_IN_PROG);
		return (List<Wsp>) super.getList(hql, parameters, limit);
	}

	// (select x.wsp.id from MgVerification x where x.finalFin > :finYear)

	// String hql = " select o.wsp.company from DgVerification o " + " where " +
	// " o.wsp.finYear = :FinYear " + " and "
	// + " o.wsp.company.residentialAddress.town.id in (select x.town.id from
	// RegionTown x where x.clo.users.id = :userID or x.crm.users.id =
	// :userID)";
	// filters.put("userID", u.getId());
	// filters.put("FinYear", finYear);

	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetail> findByWSPHosting(Class<MandatoryGrantDetail> entity, int startingAt, int pageSize,
			String sortField, SortOrder sortOrder, Map<String, Object> filters, Wsp wsp, WspReportEnum wspReport,
			long fundingID) throws Exception {
		String hql = "select o from MandatoryGrantDetail o left join fetch o.ofoCodes oc left join fetch oc.ofoCodes left join fetch o.etqa e where o.wsp.id = :wspID and o.wspReport =:wspReport and o.funding.id = :fundingID and  o.imported = true ";
		// String hql = "select o from MandatoryGrantDetail o where o.wsp.id =
		// :wspID
		// and o.wspReport =:wspReport and (o.etqa.code = :etqaCode or
		// o.funding.id =
		// :fundingID)";
		filters.put("wspID", wsp.getId());
		filters.put("wspReport", wspReport);
		filters.put("fundingID", fundingID);
		return (List<MandatoryGrantDetail>) super.sortAndFilterWhere(startingAt, pageSize, sortField, sortOrder,
				filters, hql);
	}

	public long findByWSPHostingCount(Wsp wsp, WspReportEnum wspReport, long fundingID) throws Exception {
		String hql = "select count(o) from MandatoryGrantDetail o where o.wsp.id = :wspID and o.wspReport =:wspReport and o.funding.id = :fundingID and  o.imported = true ";
		// String hql = "select o from MandatoryGrantDetail o where o.wsp.id =
		// :wspID
		// and o.wspReport =:wspReport and (o.etqa.code = :etqaCode or
		// o.funding.id =
		// :fundingID)";
		Map<String, Object> filters = new Hashtable<String, Object>();
		filters.put("wspID", wsp.getId());
		filters.put("wspReport", wspReport);
		filters.put("fundingID", fundingID);
		return (long) super.getUniqueResult(hql, filters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrant> findSummarizedData(Class<MandatoryGrantDetail> entity, int startingAt, int pageSize,
			String sortField, SortOrder sortOrder, Map<String, Object> filters, Wsp wsp, WspReportEnum wspReport,
			long fundingID) {
		/*
		 * select count(id), ofo_code_id, intervention_type_id,
		 * qualification_id, skills_set_id, skills_program_id from
		 * mandatory_grant_detail where wsp_id = 1 and wsp_report = 1 and
		 * funding_id = 7 and imported = true group by ofo_code_id,
		 * intervention_type_id, qualification_id, skills_set_id,
		 * skills_program_id
		 */

		String hql = "select new haj.com.entity.MandatoryGrant(count(o), o.interventionType, o.ofoCodes, o.wsp) "
				+ "from MandatoryGrantDetail o " + "left join o.ofoCodes oc " + "left join oc.ofoCodes "
				+ "where o.wsp.id = :wspID " + "and o.wspReport =:wspReport " + "and o.funding.id = :fundingID "
				+ "and o.imported = true " + "group by o.ofoCodes," + "o.interventionType," + "o.wsp";

		filters.put("wspID", wsp.getId());
		filters.put("wspReport", wspReport);
		filters.put("fundingID", fundingID);
		return (List<MandatoryGrant>) super.sortAndFilterWhere(startingAt, pageSize, sortField, sortOrder, filters,
				hql);

	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrant> findSummarizedData(Wsp wsp, WspReportEnum wspReport, long fundingID) {

		String hql = "select new haj.com.entity.MandatoryGrant(" + "count(o), " + "o.interventionType, "
				+ "o.ofoCodes, o.wsp, " + "o.nqfAligned, " + "o.qualification, " + "o.skillsProgram, " + "o.skillsSet,"
				+ "o.interventionLevel," + "o.nqfLevels," + "o.providerType,"
				+ "sum(case when o.disability is not null and o.disability.id <> :noDibilityID then 1 else 0 end) AS disibility"
				+ ") " + "from MandatoryGrantDetail o " + "left join o.ofoCodes oc " + "left join oc.ofoCodes "
				+ "left join o.nqfAligned " + "left join o.qualification " + "left join o.skillsProgram "
				+ "left join o.skillsSet " + "left join o.interventionLevel " + "left join o.nqfLevels "
				+ "left join o.providerType " + "where o.wsp.id = :wspID " + "and o.wspReport =:wspReport "
				+ "and o.funding.id = :fundingID " + "and o.imported = true " + "group by o.ofoCodes,"
				+ "o.interventionType," + "o.wsp," + "o.nqfAligned," + "o.qualification," + "o.skillsProgram,"
				+ "o.skillsSet," + "o.interventionLevel," + "o.nqfLevels," + "o.providerType";

		Map<String, Object> filters = new Hashtable<String, Object>();
		filters.put("wspID", wsp.getId());
		filters.put("wspReport", wspReport);
		filters.put("fundingID", fundingID);
		filters.put("noDibilityID", HAJConstants.NO_DISIBILITY);
		return (List<MandatoryGrant>) super.getList(hql, filters);

	}

	/**
	 * Version 2 of data generation Now includes
	 * nonCreditBearingIntervationTitle to be set
	 * 
	 * @param wsp
	 * @param wspReport
	 * @param fundingID
	 * @return List<MandatoryGrant>
	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrant> findSummarizedDataVersionTwo(Wsp wsp, WspReportEnum wspReport, long fundingID) {

		String hql = "select new haj.com.entity.MandatoryGrant(" + "count(o), " + "o.interventionType, "
				+ "o.ofoCodes, o.wsp, " + "o.nqfAligned, " + "o.qualification, " + "o.skillsProgram, " + "o.skillsSet,"
				+ "o.nonCreditBearingIntervationTitle," + "o.interventionLevel," + "o.nqfLevels," + "o.providerType,"
				+ "sum(case when o.disability is not null and o.disability.id <> :noDibilityID then 1 else 0 end) AS disibility"
				+ ") " + "from MandatoryGrantDetail o " + "left join o.ofoCodes oc " + "left join oc.ofoCodes "
				+ "left join o.nqfAligned " + "left join o.qualification " + "left join o.skillsProgram "
				+ "left join o.skillsSet " + "left join o.nonCreditBearingIntervationTitle "
				+ "left join o.interventionLevel " + "left join o.nqfLevels " + "left join o.providerType "
				+ "where o.wsp.id = :wspID " + "and o.wspReport =:wspReport " + "and o.funding.id = :fundingID "
				+ "and o.imported = true " + "group by o.ofoCodes," + "o.interventionType," + "o.wsp," + "o.nqfAligned,"
				+ "o.qualification," + "o.skillsProgram," + "o.skillsSet," + "o.nonCreditBearingIntervationTitle,"
				+ "o.interventionLevel," + "o.nqfLevels," + "o.providerType";

		Map<String, Object> filters = new Hashtable<String, Object>();
		filters.put("wspID", wsp.getId());
		filters.put("wspReport", wspReport);
		filters.put("fundingID", fundingID);
		filters.put("noDibilityID", HAJConstants.NO_DISIBILITY);
		return (List<MandatoryGrant>) super.getList(hql, filters);

	}

	/**
	 * Version 3 of data generation Now includes unit standards to be set
	 * 
	 * @param wsp
	 * @param wspReport
	 * @param fundingID
	 * @return List<MandatoryGrant>
	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrant> findSummarizedDataVersionThree(Wsp wsp, WspReportEnum wspReport, long fundingID) {

		String hql = "select new haj.com.entity.MandatoryGrant(" + "count(o), " + "o.interventionType, "
				+ "o.ofoCodes, o.wsp, " + "o.nqfAligned, " + "o.qualification, " + "o.skillsProgram, " + "o.skillsSet,"
				+ "o.nonCreditBearingIntervationTitle," + "o.unitStandard," + "o.interventionLevel," + "o.nqfLevels,"
				+ "o.providerType,"
				+ "sum(case when o.disability is not null and o.disability.id <> :noDibilityID then 1 else 0 end) AS disibility"
				+ ") " + "from MandatoryGrantDetail o " + "left join o.ofoCodes oc " + "left join oc.ofoCodes "
				+ "left join o.nqfAligned " + "left join o.qualification " + "left join o.skillsProgram "
				+ "left join o.skillsSet " + "left join o.nonCreditBearingIntervationTitle "
				+ "left join o.unitStandard " + "left join o.interventionLevel " + "left join o.nqfLevels "
				+ "left join o.providerType " + "where o.wsp.id = :wspID " + "and o.wspReport =:wspReport "
				+ "and o.funding.id = :fundingID " + "and o.imported = true " + "group by o.ofoCodes,"
				+ "o.interventionType," + "o.wsp," + "o.nqfAligned," + "o.qualification," + "o.skillsProgram,"
				+ "o.skillsSet," + "o.nonCreditBearingIntervationTitle," + "o.unitStandard," + "o.interventionLevel,"
				+ "o.nqfLevels," + "o.providerType";

		Map<String, Object> filters = new Hashtable<String, Object>();
		filters.put("wspID", wsp.getId());
		filters.put("wspReport", wspReport);
		filters.put("fundingID", fundingID);
		filters.put("noDibilityID", HAJConstants.NO_DISIBILITY);
		return (List<MandatoryGrant>) super.getList(hql, filters);

	}

	/**
	 * Version 4 of data generation Now includes min start date and max end date
	 * 
	 * @param wsp
	 * @param wspReport
	 * @param fundingID
	 * @return List<MandatoryGrant>
	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrant> findSummarizedDataVersionFour(Wsp wsp, WspReportEnum wspReport, long fundingID) {
		String hql = "select new haj.com.entity.MandatoryGrant(" + "count(o), " + "o.interventionType, "
				+ "o.ofoCodes, o.wsp, " + "o.nqfAligned, " + "o.qualification, " + "o.skillsProgram, " + "o.skillsSet,"
				+ "o.nonCreditBearingIntervationTitle," + "o.unitStandard," + "o.interventionLevel," + "o.nqfLevels,"
				+ "o.providerType," + "MIN(o.startDate)," + "MAX(o.endDate),"
				+ "sum(case when o.disability is not null and o.disability.id <> :noDibilityID then 1 else 0 end) AS disibility"
				+ ") " + "from MandatoryGrantDetail o " + "left join o.ofoCodes oc " + "left join oc.ofoCodes "
				+ "left join o.nqfAligned " + "left join o.qualification " + "left join o.skillsProgram "
				+ "left join o.skillsSet " + "left join o.nonCreditBearingIntervationTitle "
				+ "left join o.unitStandard " + "left join o.interventionLevel " + "left join o.nqfLevels "
				+ "left join o.providerType " + "where o.wsp.id = :wspID " + "and o.wspReport =:wspReport "
				+ "and o.funding.id = :fundingID " + "and o.imported = true " + "group by o.ofoCodes,"
				+ "o.interventionType," + "o.wsp," + "o.nqfAligned," + "o.qualification," + "o.skillsProgram,"
				+ "o.skillsSet," + "o.nonCreditBearingIntervationTitle," + "o.unitStandard," + "o.interventionLevel,"
				+ "o.nqfLevels," + "o.providerType";
		Map<String, Object> filters = new Hashtable<String, Object>();
		filters.put("wspID", wsp.getId());
		filters.put("wspReport", wspReport);
		filters.put("fundingID", fundingID);
		filters.put("noDibilityID", HAJConstants.NO_DISIBILITY);
		return (List<MandatoryGrant>) super.getList(hql, filters);
	}

	/**
	 * Version 5 of data generation Now includes validation check on enrollment
	 * status to be WSP - Planned (new training intervention)
	 * 
	 * @param wsp
	 * @param wspReport
	 * @param fundingID
	 * @return List<MandatoryGrant>
	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrant> findSummarizedDataVersionFive(Wsp wsp, WspReportEnum wspReport, long fundingID,
			long enrolmentStatusId) {
		String hql = "select new haj.com.entity.MandatoryGrant(" + "count(o), " + "o.interventionType, "
				+ "o.ofoCodes, o.wsp, " + "o.nqfAligned, " + "o.qualification, " + "o.skillsProgram, " + "o.skillsSet,"
				+ "o.nonCreditBearingIntervationTitle," + "o.unitStandard," + "o.interventionLevel," + "o.nqfLevels,"
				+ "o.providerType," + "MIN(o.startDate)," + "MAX(o.endDate),"
				+ "sum(case when o.disability is not null and o.disability.id <> :noDibilityID then 1 else 0 end) AS disibility"
				+ ") " + "from MandatoryGrantDetail o " + "left join o.ofoCodes oc " + "left join oc.ofoCodes "
				+ "left join o.nqfAligned " + "left join o.qualification " + "left join o.skillsProgram "
				+ "left join o.skillsSet " + "left join o.nonCreditBearingIntervationTitle "
				+ "left join o.unitStandard " + "left join o.interventionLevel " + "left join o.nqfLevels "
				+ "left join o.providerType " + "left join o.enrolmentStatus " + "where o.wsp.id = :wspID "
				+ "and o.wspReport =:wspReport " + "and o.funding.id = :fundingID " + "and o.imported = true "
				+ "and o.enrolmentStatus.id = :enrolmentStatusID " + "group by o.ofoCodes," + "o.interventionType,"
				+ "o.wsp," + "o.nqfAligned," + "o.qualification," + "o.skillsProgram," + "o.skillsSet,"
				+ "o.nonCreditBearingIntervationTitle," + "o.unitStandard," + "o.interventionLevel," + "o.nqfLevels,"
				+ "o.providerType";
		Map<String, Object> filters = new Hashtable<String, Object>();
		filters.put("wspID", wsp.getId());
		filters.put("wspReport", wspReport);
		filters.put("fundingID", fundingID);
		filters.put("noDibilityID", HAJConstants.NO_DISIBILITY);
		filters.put("enrolmentStatusID", enrolmentStatusId);
		return (List<MandatoryGrant>) super.getList(hql, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<MandatoryGrant> findSummarizedDataVersionSix(Wsp wsp, WspReportEnum wspReport, long fundingID, long enrolmentStatusId) {
		String hql = "select new haj.com.entity.MandatoryGrant(" + "count(o), " + "o.interventionType, "
				+ "o.ofoCodes, o.wsp, " + "o.nqfAligned, " + "o.qualification, " + "o.skillsProgram, " + "o.skillsSet,"
				+ "o.nonCreditBearingIntervationTitle," + "o.unitStandard," + "o.interventionLevel," + "o.nqfLevels,"
				+ "o.providerType," + "MIN(o.startDate)," + "MAX(o.endDate),"
				+ "sum(case when o.disability is not null and o.disability.id <> :noDibilityID then 1 else 0 end) AS disibility"
				+ ") " + "from MandatoryGrantDetail o " + "left join o.ofoCodes oc " + "left join oc.ofoCodes "
				+ "left join o.nqfAligned " + "left join o.qualification " + "left join o.skillsProgram "
				+ "left join o.skillsSet " + "left join o.nonCreditBearingIntervationTitle "
				+ "left join o.unitStandard " + "left join o.interventionLevel " + "left join o.nqfLevels "
				+ "left join o.providerType " + "left join o.enrolmentStatus " + "where o.wsp.id = :wspID "
				+ "and o.wspReport =:wspReport " + "and o.funding.id = :fundingID " + "and o.imported = true "
				+ " and ( "
				+ " (o.interventionType.id not in (:interventionTypeBurseryOneId, :interventionTypeBurseryTwoId , :interventionTypeBurseryThreeId) and o.enrolmentStatus.id = :enrolmentStatusID ) "
				+ " or "
				+ " (o.interventionType.id in (:interventionTypeBurseryOneId, :interventionTypeBurseryTwoId, :interventionTypeBurseryThreeId) and o.enrolmentStatus.id in (:enrolmentStatusID , :enrolmentStatusTwoID )) "
				+ " ) " 
				+ "group by o.ofoCodes," + "o.interventionType,"
				+ "o.wsp," + "o.nqfAligned," + "o.qualification," + "o.skillsProgram," + "o.skillsSet,"
				+ "o.nonCreditBearingIntervationTitle," + "o.unitStandard," + "o.interventionLevel," + "o.nqfLevels,"
				+ "o.providerType";
		Map<String, Object> filters = new Hashtable<String, Object>();
		filters.put("wspID", wsp.getId());
		filters.put("wspReport", wspReport);
		filters.put("fundingID", fundingID);
		filters.put("noDibilityID", HAJConstants.NO_DISIBILITY);
		filters.put("enrolmentStatusID", enrolmentStatusId);
		filters.put("enrolmentStatusTwoID", HAJConstants.WSP_ENROLEMNT_IN_PROG);
		filters.put("interventionTypeBurseryOneId", HAJConstants.INTERVENTION_BURSARIES_HET_ID);
		filters.put("interventionTypeBurseryTwoId", HAJConstants.INTERVENTION_BURSARIES_TVET_ID);
		filters.put("interventionTypeBurseryThreeId", HAJConstants.INTERVENTION_BURSARIES_UNEMPLOYED_ID);
		return (List<MandatoryGrant>) super.getList(hql, filters);
	}

	@SuppressWarnings("unchecked")
	public List<MgVerificationDetails> findSummarizedDataMgVerificationVersionOne(Wsp wsp, WspReportEnum wspReport, long fundingID, long enrolmentStatusIdOne, long enrolmentStatusIdTwo) {
		String hql = "select new haj.com.entity.MgVerificationDetails(" + "count(o), " + "o.interventionType, "
				+ "o.ofoCodes, o.enrolmentStatus, o.employmentStatus, o.wsp, " + "o.nqfAligned, " + "o.qualification, "
				+ "o.skillsProgram, " + "o.skillsSet," + "o.nonCreditBearingIntervationTitle," + "o.unitStandard,"
				+ "o.interventionLevel," + "o.nqfLevels," + "o.providerType," + "MIN(o.startDate)," + "MAX(o.endDate),"
				+ "sum(case when o.disability is not null and o.disability.id <> :noDibilityID then 1 else 0 end) AS disibility"
				+ ") " 
				+ "from MandatoryGrantDetail o " + "left join o.ofoCodes oc " + "left join oc.ofoCodes "
				+ "left join o.enrolmentStatus " + "left join o.nqfAligned " + "left join o.qualification "
				+ "left join o.skillsProgram " + "left join o.skillsSet "
				+ "left join o.nonCreditBearingIntervationTitle " + "left join o.unitStandard "
				+ "left join o.interventionLevel " + "left join o.nqfLevels " + "left join o.providerType "
				+ "left join o.enrolmentStatus " 
				+ "where o.wsp.id = :wspID " 
				+ "and o.wspReport =:wspReport "
				+ "and o.funding.id = :fundingID " 
				+ "and o.imported = true "
				+ "and o.enrolmentStatus.id in (:enrolmentStatusIdOne, :enrolmentStatusIdTwo) " 
				+ "group by o.ofoCodes,"
				+ "o.enrolmentStatus," + "o.employmentStatus," + "o.interventionType," + "o.wsp," + "o.nqfAligned,"
				+ "o.qualification," + "o.skillsProgram," + "o.skillsSet," + "o.nonCreditBearingIntervationTitle,"
				+ "o.unitStandard," + "o.interventionLevel," + "o.nqfLevels," + "o.providerType";
		Map<String, Object> filters = new Hashtable<String, Object>();
		filters.put("wspID", wsp.getId());
		filters.put("wspReport", wspReport);
		filters.put("fundingID", fundingID);
		filters.put("noDibilityID", HAJConstants.NO_DISIBILITY);
		filters.put("enrolmentStatusIdOne", enrolmentStatusIdOne);
		filters.put("enrolmentStatusIdTwo", enrolmentStatusIdTwo);
		return (List<MgVerificationDetails>) super.getList(hql, filters);
	}

	public long findSummarizedDataCount(Wsp wsp, WspReportEnum wspReport, long fundingID) {
		/*
		 * select count(id), ofo_code_id, intervention_type_id,
		 * qualification_id, skills_set_id, skills_program_id from
		 * mandatory_grant_detail where wsp_id = 1 and wsp_report = 1 and
		 * funding_id = 7 and imported = true group by ofo_code_id,
		 * intervention_type_id, qualification_id, skills_set_id,
		 * skills_program_id
		 */

		String hql = "select count(o)" + "from MandatoryGrantDetail o " + "where o.wsp.id = :wspID "
				+ "and o.wspReport =:wspReport " + "and o.funding.id = :fundingID " + "and o.imported = true "
				+ "group by o.ofoCodes," + "o.interventionType," + "o.wsp";

		Map<String, Object> filters = new Hashtable<String, Object>();
		filters.put("wspID", wsp.getId());
		filters.put("wspReport", wspReport);
		filters.put("fundingID", fundingID);
		List<Long> l = (List<Long>) super.getList(hql, filters);
		return (long) l.size();

	}

	/*
	 * @SuppressWarnings("unchecked") public List<EmployeeProfileBean>
	 * allEmployeeProfileBean(Wsp wsp, WspReportEnum wspReport, long fundingID)
	 * throws Exception {
	 * 
	 * String hql="select new haj.com.bean.EmployeeProfileBean(" + "o.ofoCodes,"
	 * +
	 * "sum(case when o.gender is not null and o.gender.id = :femaleID then 1 else 0 end) AS totalFemale,"
	 * +
	 * "sum(case when o.gender is not null and o.gender.id = :maleID then 1 else 0 end) AS totalMale,"
	 * +
	 * "sum(case when o.disability is not null and o.disability.id <> :noDibilityID and o.gender is not null and o.gender.id = :femaleID  then 1 else 0 end) AS totalFemaleDisability,"
	 * +
	 * "sum(case when o.disability is not null and o.disability.id <> :noDibilityID and o.gender is not null and o.gender.id = :maleID  then 1 else 0 end) AS totalMaleDisability,"
	 * +
	 * "sum(case when o.nationality is not null and o.nationality.id = :SAID then 1 else 0 end) AS totalSA,"
	 * +
	 * "sum(case when o.nationality is not null and o.nationality.id <> :SAID then 1 else 0 end) AS totalNonSA"
	 * + ") " + "from MandatoryGrantDetail o " + "left join o.ofoCodes oc " +
	 * "left join o.funding f " + "where o.wsp.id = :wspID " +
	 * "and o.wspReport =:wspReport " + "and o.imported = true " +
	 * "group by o.ofoCodes.id";
	 * 
	 * Map<String, Object> parameters = new Hashtable<String, Object>();
	 * parameters.put("wspID", wsp.getId()); parameters.put("wspReport",
	 * wspReport); parameters.put("noDibilityID", HAJConstants.NO_DISIBILITY);
	 * parameters.put("femaleID",2L); parameters.put("maleID",1L);
	 * parameters.put("SAID",1L); return (List<EmployeeProfileBean>)
	 * super.getList(hql, parameters); }
	 */

	@SuppressWarnings("unchecked")
	public List<EmployeeProfileBean> allEmployeeProfileBean(Company wsp) throws Exception {

		String hql = "select new haj.com.bean.EmployeeProfileBean(" + "o.ofoCode,"
				+ "sum(case when o.gender is not null and o.gender.id = :femaleID then 1 else 0 end) AS totalFemale,"
				+ "sum(case when o.gender is not null and o.gender.id = :maleID then 1 else 0 end) AS totalMale,"
				+ "sum(case when o.disability is not null and o.disability.id <> :noDibilityID and o.gender is not null and o.gender.id = :femaleID  then 1 else 0 end) AS totalFemaleDisability,"
				+ "sum(case when o.disability is not null and o.disability.id <> :noDibilityID and o.gender is not null and o.gender.id = :maleID  then 1 else 0 end) AS totalMaleDisability,"
				+ "sum(case when o.nationality is not null and o.nationality.id = :SAID then 1 else 0 end) AS totalSA,"
				+ "sum(case when o.nationality is not null and o.nationality.id <> :SAID then 1 else 0 end) AS totalNonSA"
				+ ") " + "from Employees o " + "left join o.ofoCode oc " + "where o.company.id = :wspID "
				+ "group by o.ofoCode.id";

		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("noDibilityID", HAJConstants.NO_DISIBILITY);
		parameters.put("femaleID", 2L);
		parameters.put("maleID", 1L);
		parameters.put("SAID", 1L);
		return (List<EmployeeProfileBean>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<EmployeeEquityProfileBean> allEmployeeEquityProfileBean(Company wsp) throws Exception {

		String hql = "select new haj.com.bean.EmployeeEquityProfileBean(" + "o.ofoCode,"
				+ "sum(case when o.equity is not null and o.equity.id = :AfricanBlackID and o.gender is not null and o.gender.id = :femaleID  then 1 else 0 end) AS totalBlackAfricaFemale,"
				+ "sum(case when o.equity is not null and o.equity.id = :ColouredID and o.gender is not null and o.gender.id = :femaleID  then 1 else 0 end) AS totalColouredFemale,"
				+ "sum(case when o.equity is not null and o.equity.id = :IndianID and o.gender is not null and o.gender.id = :femaleID  then 1 else 0 end) AS totalIndianAsianFemale,"
				+ "sum(case when o.equity is not null and o.equity.id = :WhiteID and o.gender is not null and o.gender.id = :femaleID  then 1 else 0 end) AS totalWhiteFemale,"
				+ "sum(case when o.equity is not null and o.equity.id = :OtherID and o.gender is not null and o.gender.id = :femaleID  then 1 else 0 end) AS totalFemaleOther,"
				+ "sum(case when o.equity is not null and o.equity.id = :AfricanBlackID and o.gender is not null and o.gender.id = :maleID  then 1 else 0 end) AS totalBlackAfricaMale,"
				+ "sum(case when o.equity is not null and o.equity.id = :ColouredID and o.gender is not null and o.gender.id = :maleID  then 1 else 0 end) AS totalColouredMale,"
				+ "sum(case when o.equity is not null and o.equity.id = :IndianID and o.gender is not null and o.gender.id = :maleID  then 1 else 0 end) AS totalIndianAsianMale,"
				+ "sum(case when o.equity is not null and o.equity.id = :WhiteID and o.gender is not null and o.gender.id = :maleID  then 1 else 0 end) AS totalWhiteMale,"
				+ "sum(case when o.equity is not null and o.equity.id = :OtherID and o.gender is not null and o.gender.id = :maleID  then 1 else 0 end) AS totalMaleOther,"
				+ "sum(case when o.gender is not null and o.gender.id = :femaleID then 1 else 0 end) AS totalFemale,"
				+ "sum(case when o.gender is not null and o.gender.id = :maleID then 1 else 0 end) AS totalMale" + ") "
				+ "from Employees o " + "left join o.ofoCode oc " + "left join o.gender g "
				+ "where o.company.id = :wspID " + "group by o.ofoCode.id";

		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("femaleID", 2L);
		parameters.put("maleID", 1L);

		parameters.put("AfricanBlackID", 1L);
		parameters.put("ColouredID", 2L);
		parameters.put("IndianID", 3L);
		parameters.put("WhiteID", 6L);
		parameters.put("OtherID", 4L);
		return (List<EmployeeEquityProfileBean>) super.getList(hql, parameters);
	}

	/*
	 * @SuppressWarnings("unchecked") public List<EmployeeEquityProfileBean>
	 * allEmployeeEquityProfileBean(Wsp wsp, WspReportEnum wspReport, long
	 * fundingID) throws Exception {
	 * 
	 * String hql="select new haj.com.bean.EmployeeEquityProfileBean(" +
	 * "o.ofoCodes," +
	 * "sum(case when o.equity is not null and o.equity.id = :AfricanBlackID and o.gender is not null and o.gender.id = :femaleID  then 1 else 0 end) AS totalBlackAfricaFemale,"
	 * +
	 * "sum(case when o.equity is not null and o.equity.id = :ColouredID and o.gender is not null and o.gender.id = :femaleID  then 1 else 0 end) AS totalColouredFemale,"
	 * +
	 * "sum(case when o.equity is not null and o.equity.id = :IndianID and o.gender is not null and o.gender.id = :femaleID  then 1 else 0 end) AS totalIndianAsianFemale,"
	 * +
	 * "sum(case when o.equity is not null and o.equity.id = :WhiteID and o.gender is not null and o.gender.id = :femaleID  then 1 else 0 end) AS totalWhiteFemale,"
	 * +
	 * "sum(case when o.equity is not null and o.equity.id = :OtherID and o.gender is not null and o.gender.id = :femaleID  then 1 else 0 end) AS totalFemaleOther,"
	 * +
	 * "sum(case when o.equity is not null and o.equity.id = :AfricanBlackID and o.gender is not null and o.gender.id = :maleID  then 1 else 0 end) AS totalBlackAfricaMale,"
	 * +
	 * "sum(case when o.equity is not null and o.equity.id = :ColouredID and o.gender is not null and o.gender.id = :maleID  then 1 else 0 end) AS totalColouredMale,"
	 * +
	 * "sum(case when o.equity is not null and o.equity.id = :IndianID and o.gender is not null and o.gender.id = :maleID  then 1 else 0 end) AS totalIndianAsianMale,"
	 * +
	 * "sum(case when o.equity is not null and o.equity.id = :WhiteID and o.gender is not null and o.gender.id = :maleID  then 1 else 0 end) AS totalWhiteMale,"
	 * +
	 * "sum(case when o.equity is not null and o.equity.id = :OtherID and o.gender is not null and o.gender.id = :maleID  then 1 else 0 end) AS totalMaleOther,"
	 * +
	 * "sum(case when o.gender is not null and o.gender.id = :femaleID then 1 else 0 end) AS totalFemale,"
	 * +
	 * "sum(case when o.gender is not null and o.gender.id = :maleID then 1 else 0 end) AS totalMale"
	 * + ") " + "from MandatoryGrantDetail o " + "left join o.ofoCodes oc " +
	 * "left join oc.ofoCodes " + "left join o.funding f " +
	 * "left join o.gender g " + "where o.wsp.id = :wspID " +
	 * "and o.wspReport =:wspReport " + "and o.imported = true " +
	 * "group by o.ofoCodes.id";
	 * 
	 * Map<String, Object> parameters = new Hashtable<String, Object>();
	 * parameters.put("wspID", wsp.getId()); parameters.put("wspReport",
	 * wspReport); parameters.put("femaleID",2L); parameters.put("maleID",1L);
	 * 
	 * parameters.put("AfricanBlackID", 1L); parameters.put("ColouredID", 2L);
	 * parameters.put("IndianID",3L); parameters.put("WhiteID",6L);
	 * parameters.put("OtherID",4L); return (List<EmployeeEquityProfileBean>)
	 * super.getList(hql, parameters); }
	 */

	@SuppressWarnings("unchecked")
	public List<EmpEmploymentStatusBean> allEmpEmploymentStatusBean(Company wsp) throws Exception {

		String hql = "select new haj.com.bean.EmpEmploymentStatusBean(" + "o.ofoCode,"
				+ "sum(case when o.employmentType is not null and o.employmentType.id = :PermanentID then 1 else 0 end) AS totalPermanent,"
				+ "sum(case when o.employmentType is not null and o.employmentType.id = :ContractID then 1 else 0 end) AS totalContract,"
				+ "sum(case when o.employmentType is not null and o.employmentType.id = :UnemployedID then 1 else 0 end) AS totalUnemployed,"
				+ "sum(case when o.employmentType is not null and o.employmentType.id = :FECID then 1 else 0 end) AS totalFormerlyEmployedAtCompany"
				+ ") " + "from Employees o " + "left join o.ofoCode oc " + "left join o.employmentType et "
				+ "where o.company.id = :wspID " + "group by o.ofoCode.id";

		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("PermanentID", 6L);
		parameters.put("ContractID", 5L);
		parameters.put("UnemployedID", 7L);
		parameters.put("FECID", 8L);
		return (List<EmpEmploymentStatusBean>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<ATRReportSummayBean> allATRReportSummayBean(Wsp wsp, WspReportEnum wspReport, long fundingID)
			throws Exception {

		String hql = "select new haj.com.bean.ATRReportSummayBean(" + "o.ofoCodes,"
				+ "sum(case when o.gender is not null and o.gender.id = :femaleID then 1 else 0 end) AS totalATRFemaleTrained,"
				+ "sum(case when o.gender is not null and o.gender.id = :maleID then 1 else 0 end) AS totalATRMaleTrained,"
				+ "sum(case when o.disability is not null and o.disability.id <> :noDibilityID and o.gender is not null and o.gender.id = :femaleID then 1 else 0 end) AS totalATRFemaleDisabilityTrained,"
				+ "sum(case when o.disability is not null and o.disability.id <> :noDibilityID and o.gender is not null and o.gender.id = :maleID then 1 else 0 end) AS totalATRMaleDisabilityTrained,"
				+ "sum(case when o.nationality is not null and o.nationality.id = :SAID then 1 else 0 end) AS totalATRSATrained,"
				+ "sum(case when o.nationality is not null and o.nationality.id <> :SAID then 1 else 0 end) AS totalATRNonSATrained,"
				+ "sum(case when o.estimatedCost is not null then o.estimatedCost else 0 end) AS totalATREstimatedCost"
				+ ") " + "from MandatoryGrantDetail o " + "left join o.ofoCodes oc " + "left join oc.ofoCodes "
				+ "left join o.funding f " + "where o.wsp.id = :wspID " + "and o.wspReport =:wspReport "
				+ "and o.imported = true " + "group by o.ofoCodes.id";

		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("wspReport", wspReport);
		parameters.put("noDibilityID", HAJConstants.NO_DISIBILITY);
		parameters.put("femaleID", 2L);
		parameters.put("maleID", 1L);
		parameters.put("SAID", 1L);
		return (List<ATRReportSummayBean>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<WSPReportSummayBean> allWSPReportSummay(Wsp wsp, WspReportEnum wspReport, long fundingID)
			throws Exception {

		String hql = "select new haj.com.bean.WSPReportSummayBean(" + "o.ofoCodes,"
				+ "sum(case when o.gender is not null and o.gender.id = :femaleID then 1 else 0 end) AS totalWSPFemalePlanForTraining,"
				+ "sum(case when o.gender is not null and o.gender.id = :maleID then 1 else 0 end) AS totalWSPMalePlanForTraining,"
				+ "sum(case when o.disability is not null and o.disability.id <> :noDibilityID and o.gender is not null and o.gender.id = :femaleID then 1 else 0 end) AS totalWSPFemaleDisabilityPlanForTraining,"
				+ "sum(case when o.disability is not null and o.disability.id <> :noDibilityID and o.gender is not null and o.gender.id = :maleID then 1 else 0 end) AS totalWSPMaleDisabilityPlanForTraining,"
				+ "sum(case when o.nationality is not null and o.nationality.id = :SAID then 1 else 0 end) AS totalWSPSAPlanForTraining,"
				+ "sum(case when o.nationality is not null and o.nationality.id <> :SAID then 1 else 0 end) AS totalWSPNonSAPlanForTraining,"
				+ "sum(case when o.funding.id = :fundingMG then o.estimatedCost when o.funding.id = :fundingDG then o.interventionType.basicGrantAmount else 0 end) AS totalWSPEstimatedCost"
				+ ") " + "from MandatoryGrantDetail o " + "left join o.ofoCodes oc " + "left join oc.ofoCodes "
				+ "left join o.funding f " + "where o.wsp.id = :wspID " + "and o.wspReport =:wspReport "
				+ "and o.imported = true " + "group by o.ofoCodes.id";

		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("wspReport", wspReport);
		parameters.put("noDibilityID", HAJConstants.NO_DISIBILITY);
		parameters.put("femaleID", 2L);
		parameters.put("maleID", 1L);
		parameters.put("SAID", 1L);
		parameters.put("fundingMG", HAJConstants.MAN_FUNDING_ID);
		parameters.put("fundingDG", HAJConstants.DISC_FUNDING_ID);
		return (List<WSPReportSummayBean>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<PivotalTrainingReportBean> allPivotalTrainingReport(Wsp wsp, WspReportEnum pivotalReport,
			Long nqfAlignedID) throws Exception {

		String hql = "select new haj.com.bean.PivotalTrainingReportBean(" + "o.ofoCodes,"
				+ "sum(case when o.gender is not null and o.gender.id = :femaleID then 1 else 0 end) AS totalFemale,"
				+ "sum(case when o.gender is not null and o.gender.id = :maleID then 1 else 0 end) AS totalMale,"
				+ "sum(case when o.disability is not null and o.disability.id <> :noDibilityID and o.gender is not null and o.gender.id = :femaleID then 1 else 0 end) AS totalFemaleDisability,"
				+ "sum(case when o.disability is not null and o.disability.id <> :noDibilityID and o.gender is not null and o.gender.id = :maleID then 1 else 0 end) AS totalMaleDisability,"
				+ "sum(case when o.nationality is not null and o.nationality.id = :SAID then 1 else 0 end) AS totalSA,"
				+ "sum(case when o.nationality is not null and o.nationality.id <> :SAID then 1 else 0 end) AS totalNonSA,"
				+ "sum(case when o.estimatedCost is not null then o.estimatedCost else 0 end) AS totalEstimatedCost"
				+ ") " + "from MandatoryGrantDetail o " + "left join o.ofoCodes oc " + "left join oc.ofoCodes "
				+ "where o.wsp.id = :wspID " + "and o.wspReport =:wspReport " + "and o.nqfAligned.id = :nqfAligned "
				+ "and o.imported = true " + "group by o.ofoCodes.id";

		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("wspReport", pivotalReport);
		parameters.put("nqfAligned", nqfAlignedID);
		parameters.put("noDibilityID", HAJConstants.NO_DISIBILITY);
		parameters.put("femaleID", 2L);
		parameters.put("maleID", 1L);
		parameters.put("SAID", 1L);

		return (List<PivotalTrainingReportBean>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<PivotalTrainingReportBean> allPivotalTrainingReportWSP(Wsp wsp, WspReportEnum pivotalReport,
			Long nqfAlignedID) throws Exception {

		String hql = "select new haj.com.bean.PivotalTrainingReportBean(" + "o.ofoCodes,"
				+ "sum(case when o.gender is not null and o.gender.id = :femaleID then 1 else 0 end) AS totalFemale,"
				+ "sum(case when o.gender is not null and o.gender.id = :maleID then 1 else 0 end) AS totalMale,"
				+ "sum(case when o.disability is not null and o.disability.id <> :noDibilityID and o.gender is not null and o.gender.id = :femaleID then 1 else 0 end) AS totalFemaleDisability,"
				+ "sum(case when o.disability is not null and o.disability.id <> :noDibilityID and o.gender is not null and o.gender.id = :maleID then 1 else 0 end) AS totalMaleDisability,"
				+ "sum(case when o.nationality is not null and o.nationality.id = :SAID then 1 else 0 end) AS totalSA,"
				+ "sum(case when o.nationality is not null and o.nationality.id <> :SAID then 1 else 0 end) AS totalNonSA,"
				+ "sum(case when o.funding.id = :fundingMG then o.estimatedCost when o.funding.id = :fundingDG then o.interventionType.basicGrantAmount else 0 end) AS totalEstimatedCost"
				+ ") " + "from MandatoryGrantDetail o " + "left join o.ofoCodes oc " + "left join oc.ofoCodes "
				+ "where o.wsp.id = :wspID " + "and o.wspReport =:wspReport " + "and o.nqfAligned.id = :nqfAligned "
				+ "and o.imported = true " + "group by o.ofoCodes.id";

		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("wspReport", pivotalReport);
		parameters.put("nqfAligned", nqfAlignedID);
		parameters.put("noDibilityID", HAJConstants.NO_DISIBILITY);
		parameters.put("femaleID", 2L);
		parameters.put("maleID", 1L);
		parameters.put("SAID", 1L);
		parameters.put("fundingMG", HAJConstants.MAN_FUNDING_ID);
		parameters.put("fundingDG", HAJConstants.DISC_FUNDING_ID);

		return (List<PivotalTrainingReportBean>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<DGApplicationSummaryBean> allDGApplicationSummaryBean(Wsp wsp, WspReportEnum pivotalReport,
			Long fundingID) throws Exception {

		String hql = "select new haj.com.bean.DGApplicationSummaryBean(" + "o.ofoCodes,"
				+ "sum(case when o.gender is not null and o.gender.id = :femaleID then 1 else 0 end) AS totalFemale,"
				+ "sum(case when o.gender is not null and o.gender.id = :maleID then 1 else 0 end) AS totalMale,"
				+ "sum(case when o.disability is not null and o.disability.id <> :noDibilityID and o.gender is not null and o.gender.id = :femaleID then 1 else 0 end) AS totalFemaleDisability,"
				+ "sum(case when o.disability is not null and o.disability.id <> :noDibilityID and o.gender is not null and o.gender.id = :maleID then 1 else 0 end) AS totalMaleDisability,"
				+ "sum(case when o.nationality is not null and o.nationality.id = :SAID then 1 else 0 end) AS totalSA,"
				+ "sum(case when o.nationality is not null and o.nationality.id <> :SAID then 1 else 0 end) AS totalNonSA,"
				+ "sum(case when o.funding.id = :fundingMG then o.estimatedCost when o.funding.id = :fundingDG then o.interventionType.basicGrantAmount else 0 end) AS totalEstimatedCost"
				+ ") " + "from MandatoryGrantDetail o " + "left join o.ofoCodes oc " + "left join oc.ofoCodes "
				+ "left join o.funding f " + "where o.wsp.id = :wspID " + "and o.wspReport =:wspReport "
				+ "and o.funding.id = :fundingID " + "and o.imported = true " + "group by o.ofoCodes.id";

		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("wspReport", pivotalReport);
		parameters.put("fundingID", fundingID);
		parameters.put("noDibilityID", HAJConstants.NO_DISIBILITY);
		parameters.put("femaleID", 2L);
		parameters.put("maleID", 1L);
		parameters.put("SAID", 1L);
		parameters.put("fundingMG", HAJConstants.MAN_FUNDING_ID);
		parameters.put("fundingDG", HAJConstants.DISC_FUNDING_ID);
		return (List<DGApplicationSummaryBean>) super.getList(hql, parameters);
	}

	public List<ProjectImplementationPlan> findPIMReportData(Wsp wsp) throws Exception {
		String hql = "SELECT  " + "intervention_type.description AS interventionTypeDescription "
				+ ",count(*) as numberOfLearningInterventions "
				+ ",sum(case when disability_id is not null and disability_id <> 9 then 1 else 0 end) as learnersWithDisability "
				+ ",sum(case when disability_id is null or disability_id = 9 then 1 else 0 end) as learnersWithoutDisability "
				+ ",sum(case when disability_id is not null and disability_id <> 9 then 1 else 0 end) * COALESCE(intervention_type.disability_grant_amount,0) as learnersWithDisabilityAllowanceValue "
				+ ",sum(case when disability_id is null or disability_id = 9 then 1 else 0 end) * COALESCE(intervention_type.basic_grant_amount,0) as learnersWithoutDisabilityAllowanceValue	"
				+ ",sum(case when disability_id is not null and disability_id <> 9 then 1 else 0 end) * COALESCE(intervention_type.disability_grant_amount,0) + "
				+ "sum(case when disability_id is null or disability_id = 9 then 1 else 0 end) * COALESCE(intervention_type.basic_grant_amount,0) as totalFundingValue "
				+ "FROM mandatory_grant_detail  mgd " + "LEFT JOIN merseta.intervention_type AS intervention_type "
				+ "ON intervention_type.id=mgd.intervention_type_id " + "LEFT OUTER JOIN merseta.etqa etqa "
				+ "ON mgd.etqa_id=etqa.id " + "WHERE mgd.wsp_id = :wspID " + "AND mgd.wsp_report=:wspREport "
				+ "AND (etqa.code=:etqa OR  mgd.funding_id=:fundingID) " + "GROUP BY merseta.mgd.id";

		Map<String, Object> params = new Hashtable<String, Object>();
		params.put("fundingID", HAJConstants.DISC_FUNDING_ID);
		params.put("etqa", HAJConstants.HOSTING_MERSETA_ETQA);
		params.put("wspREport", WspReportEnum.WSP.ordinal());
		params.put("wspID", wsp.getId());

		return (List<ProjectImplementationPlan>) super.nativeSelectSqlList(hql, ProjectImplementationPlan.class,
				params);

	}

	public List<ProjectImplementationPlan> findProjectImplementationPlanData(Wsp wsp) throws Exception {
		String hql = "SELECT  " + "intervention_type.description AS interventionTypeDescription "
				+ ",count(*) as numberOfLearningInterventions "
				+ ",sum(case when disability_id is not null and disability_id <> 9 then 1 else 0 end) as learnersWithDisability "
				+ ",sum(case when disability_id is null or disability_id = 9 then 1 else 0 end) as learnersWithoutDisability "
				+ ",sum(case when disability_id is not null and disability_id <> 9 then 1 else 0 end) * COALESCE(intervention_type.disability_grant_amount,0) as learnersWithDisabilityAllowanceValue "
				+ ",sum(case when disability_id is null or disability_id = 9 then 1 else 0 end) * COALESCE(intervention_type.basic_grant_amount,0) as learnersWithoutDisabilityAllowanceValue	"
				+ ",sum(case when disability_id is not null and disability_id <> 9 then 1 else 0 end) * COALESCE(intervention_type.disability_grant_amount,0) + "
				+ "sum(case when disability_id is null or disability_id = 9 then 1 else 0 end) * COALESCE(intervention_type.basic_grant_amount,0) as totalFundingValue "
				+ "FROM mandatory_grant_detail  mgd " + "LEFT JOIN merseta.intervention_type AS intervention_type "
				+ "ON intervention_type.id=mgd.intervention_type_id " + "LEFT OUTER JOIN merseta.etqa etqa "
				+ "ON mgd.etqa_id=etqa.id " + "WHERE mgd.wsp_id = :wspID " + "AND mgd.wsp_report=:wspREport "
				+ "AND (etqa.code=:etqa OR  mgd.funding_id=:fundingID) "
				+ "GROUP BY merseta.mgd.intervention_type_id, merseta.intervention_type.basic_grant_amount, merseta.intervention_type.disability_grant_amount";

		Map<String, Object> params = new Hashtable<String, Object>();
		params.put("fundingID", HAJConstants.DISC_FUNDING_ID);
		params.put("etqa", HAJConstants.HOSTING_MERSETA_ETQA);
		params.put("wspREport", WspReportEnum.WSP.ordinal());
		params.put("wspID", wsp.getId());

		/*
		 * String hql = "SELECT  " +
		 * "intervention_type.description AS interventionTypeDescription " +
		 * ",max_possible_learners as numberOfLearningInterventions " +
		 * ",disabled_total_learners as learnersWithDisability " +
		 * ",(number_of_learners - disabled_total_learners) as learnersWithoutDisability "
		 * +
		 * ",(disabled_grant_amount-grant_amount) as learnersWithDisabilityAllowanceValue "
		 * +
		 * ",disabled_grant_amount as learnersWithoutDisabilityAllowanceValue "
		 * + ",award_amount as totalFundingValue " +
		 * "FROM dg_allocation  dg_allocation " +
		 * 
		 * "LEFT JOIN merseta.intervention_type AS intervention_type " +
		 * "ON intervention_type.id=dg_allocation.intervention_type_id " +
		 * 
		 * "WHERE dg_allocation.wsp_id = :wspID ";
		 * 
		 * Map<String, Object> params = new Hashtable<String, Object>();
		 * params.put("wspID", wsp.getId());
		 */

		return (List<ProjectImplementationPlan>) super.nativeSelectSqlList(hql, ProjectImplementationPlan.class,
				params);

	}

	public List<ProjectImplementationPlan> findProjectImplementationPlanDataUngroup(Wsp wsp) throws Exception {

		String hql = "SELECT  " + "intervention_type.description AS interventionTypeDescription "
				+ ",max_possible_learners as fullyFundedLearnerAwarded "
				+ ",co_funding_learners_awarded as co_funding_learners_awarded "
				+ ",total_award_amount as totalAwardAmount " +

				"FROM dg_allocation  dg_allocation " +

				"LEFT JOIN merseta.intervention_type AS intervention_type "
				+ "ON intervention_type.id=dg_allocation.intervention_type_id " +

				"LEFT JOIN merseta.dg_allocation_parent AS dg_allocation_parent "
				+ "ON dg_allocation.dg_allocation_parent_id = dg_allocation_parent.id " +

				"LEFT JOIN merseta.wsp AS wsp " + "ON dg_allocation_parent.wsp_id = wsp.id " +

				"WHERE dg_allocation_parent.wsp_id =:wspID ";

		Map<String, Object> params = new Hashtable<String, Object>();
		params.put("wspID", wsp.getId());

		return (List<ProjectImplementationPlan>) super.nativeSelectSqlList(hql, ProjectImplementationPlan.class,
				params);

	}

	@SuppressWarnings("unchecked")
	public List<DgAllocation> findDgAllocationPlanData(Long wspID) throws Exception {
		String hql = "select o from DgAllocation o left join fetch o.dgAllocationParent dgp left join fetch dgp.wsp where dgp.wsp.id = :wspID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wspID);
		return (List<DgAllocation>) super.getList(hql, parameters);
	}

	public List<DgAllocation> findDgAllocationPlanData(Wsp wsp) throws Exception {

		String hql = "SELECT o.  " + "intervention_type.description AS interventionTypeDescription "
				+ ",max_possible_learners as fullyFundedLearnerAwarded "
				+ ",co_funding_learners_awarded as co_funding_learners_awarded "
				+ ",total_award_amount as totalAwardAmount " +

				"FROM dg_allocation  dg_allocation " +

				"LEFT JOIN merseta.intervention_type AS intervention_type "
				+ "ON intervention_type.id=dg_allocation.intervention_type_id " +

				"LEFT JOIN merseta.dg_allocation_parent AS dg_allocation_parent "
				+ "ON dg_allocation.dg_allocation_parent_id = dg_allocation_parent.id " +

				"LEFT JOIN merseta.wsp AS wsp " + "ON dg_allocation_parent.wsp_id = wsp.id " +

				"WHERE dg_allocation_parent.wsp_id =:wspID ";

		Map<String, Object> params = new Hashtable<String, Object>();
		params.put("wspID", wsp.getId());

		return (List<DgAllocation>) super.nativeSelectSqlList(hql, DgAllocation.class, params);

	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetail> allMandatoryGrantDetailNotImportedBetweenDatesByWspStatus(Date fromDate,
			Date toDate, WspStatusEnum wspStatus, int numberOfEntries) throws Exception {
		String hql = "select o from MandatoryGrantDetail o where o.importError = true "
				+ "and o.wsp.wspStatusEnum = :status and o.importErrors <> null "
				+ "and o.id not in (select x.mandatoryGrantDetailId from FailedArchiveEntries x) "
				+ "and date(o.createDate) between date(:fromDate) and date(:toDate)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("status", wspStatus);
		parameters.put("fromDate", fromDate);
		parameters.put("toDate", toDate);
		return (List<MandatoryGrantDetail>) super.getList(hql, parameters, numberOfEntries);
	}

	public Integer countMandatoryGrantDetailNotImportedBetweenDatesByWspStatus(Date fromDate, Date toDate,
			WspStatusEnum wspStatus) throws Exception {
		String hql = "select count(o) from MandatoryGrantDetail o where o.importError = true "
				+ "and o.wsp.wspStatusEnum = :status and o.importErrors <> null "
				+ "and o.id not in (select x.mandatoryGrantDetailId from FailedArchiveEntries x) "
				+ "and date(o.createDate) between date(:fromDate) and date(:toDate)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("status", wspStatus);
		parameters.put("fromDate", fromDate);
		parameters.put("toDate", toDate);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetail> allMandatoryGrantDetailFailedImportedByWspId(Long wspId, int numberOfEntries)
			throws Exception {
		String hql = "select o from MandatoryGrantDetail o where o.importError = true "
				+ "and o.wsp.id = :wspId and o.importErrors <> null ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		return (List<MandatoryGrantDetail>) super.getList(hql, parameters, numberOfEntries);
	}

	public Integer countMandatoryGrantDetailFailedImportedByWspId(Long wspId) throws Exception {
		String hql = "select count(o) from MandatoryGrantDetail o where o.importError = true "
				+ "and o.wsp.id = :wspId and o.importErrors <> null ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public List<GrantSspReportBean> populateSspReportingByGrantYearAndStatusLists(Integer grantYear, List<WspStatusEnum> wspStatusList, List<WspReportEnum> wspReporteList) throws Exception {
		String sql = "select   " + 
				"	grantYear  " + 
				"	, grantStatus  " + 
				"	, entityId  " + 
				"	, companyName  " + 
				"	, tradingName  " + 
				"	, sicCode  " + 
				"	, sicCodeDescription  " + 
				"	, chamber  " + 
				"	, ofoCode  " + 
				"	, ofoCodeDescription  " + 
				"	, interventionType  " + 
				"	, funding  " + 
				"	, count(id) as amount  " + 
				"from (  " + 
				"    select  " + 
				"        mdg.id as id   " + 
				"        , w.fin_year as grantYear  " + 
				"        , case  " + 
				"            when w.wsp_status_enum = 0 then 'Not Submitted-In Progress'  " + 
				"            when w.wsp_status_enum = 1 then 'Pending Sign Off'  " + 
				"            when w.wsp_status_enum = 2 then 'Pending Approval'  " + 
				"            when w.wsp_status_enum = 3 then 'Dispute'  " + 
				"            when w.wsp_status_enum = 4 then 'Approved'  " + 
				"            when w.wsp_status_enum = 5 then 'Rejected'  " + 
				"            when w.wsp_status_enum = 6 then 'Reviewed Pending Approval'  " + 
				"            when w.wsp_status_enum = 7 then 'Reviewed Pending Final Decision'  " + 
				"            when w.wsp_status_enum = 8 then 'Received For Record Purposes'  " + 
				"            when w.wsp_status_enum = 9 then 'Opened For Review'  " + 
				"            when w.wsp_status_enum = 10 then 'NA'  " + 
				"            when w.wsp_status_enum = 11 then 'Withdrawn'  " + 
				"            when w.wsp_status_enum = 12 then 'Awaiting MANCO Approval'  " + 
				"            Else 'Unable to locate status'  " + 
				"            end as grantStatus  " + 
				"        , case   " + 
				"            when wch.levy_number is not null and wch.levy_number <> '' then wch.levy_number  " + 
				"            Else c.levy_number  " + 
				"            end as entityId  " + 
				"        , case   " + 
				"            when wch.company_name is not null and wch.company_name <> '' then wch.company_name  " + 
				"            Else c.company_name  " + 
				"            end as companyName  " + 
				"        , case   " + 
				"            when wch.trading_name is not null and wch.trading_name <> '' then wch.trading_name  " + 
				"            Else c.trading_name  " + 
				"            end as tradingName  " + 
				"        , case   " + 
				"            when chsc.code is not null and chsc.code <> '' then chsc.code  " + 
				"            Else sc.code  " + 
				"            end as sicCode  " + 
				"        , case   " + 
				"            when chsc.description is not null and chsc.description <> '' then chsc.description  " + 
				"            Else sc.description  " + 
				"            end as sicCodeDescription  " + 
				"        , case  " + 
				"            when chch.description is not null and chch.description <> '' then chch.description  " + 
				"            Else ch.description  " + 
				"            end as chamber  " + 
				"        , case   " + 
				"            when ofoP.ofo_code is not null and ofoP.ofo_code <> '' then ofoP.ofo_code  " + 
				"            else ofo.ofo_code  " + 
				"            end as ofoCode  " + 
				"        , case   " + 
				"            when ofoP.description is not null and ofoP.description <> '' then ofoP.description  " + 
				"            else ofo.description  " + 
				"            end as ofoCodeDescription  " + 
				"        , case  " + 
				"            when it.description is not null and it.description <> '' then it.description  " + 
				"            Else 'N/A'  " + 
				"            end as interventionType  " + 
				"        , case  " + 
				"            when f.description is not null and f.description <> '' then f.description  " + 
				"            Else 'N/A'  " + 
				"            end as funding  " + 
				"    from   " + 
				"        mandatory_grant_detail mdg   " + 
				"          " + 
				"    inner join wsp w on w.id = mdg.wsp_id  " + 
				"  " + 
				"    left join ofo_codes ofo on ofo.id = mdg.ofo_code_id  " + 
				"    left join ofo_codes ofoP on ofoP.id = ofo.ofo_codes_id  " + 
				"  " + 
				"    left join intervention_type it on it.id = mdg.intervention_type_id  " + 
				"    left join allocation_list al on al.ofo_codes_id = mdg.ofo_code_id  " + 
				"    left join funding f on f.id = mdg.funding_id  " + 
				"  " + 
				"    left join company c on c.id = w.company_id  " + 
				"    left join sic_code sc on sc.id = c.sic_code_id  " + 
				"    left join chamber ch on ch.id = sc.chamber_id  " + 
				"  " + 
				"    left join (    " + 
				"        select   " + 
				"            target_key    " + 
				"            , MAX(id) as lastest_id   " + 
				"        from  " + 
				"            wsp_company_main_history   " + 
				"        where  " + 
				"            target_class = 'haj.com.entity.Wsp'   " + 
				"        group by  " + 
				"            target_key   " + 
				"    ) x on x.target_key = w.id  " + 
				"    inner join wsp_company_history wch on wch.wsp_company_main_history_id = x.lastest_id  " + 
				"    left join sic_code chsc on chsc.id = wch.sic_code_id  " + 
				"    left join chamber chch on chch.id = chsc.chamber_id  " + 
				"  " + 
				"    where   " + 
				"        w.fin_year =  " + grantYear.toString() + "  ";
		if (!wspStatusList.isEmpty()) {
			sql +=  "        and w.wsp_status_enum in ("+populateTaskStatusEnumResult(wspStatusList)+")  ";
		}
		sql +=  "        and mdg.imported = true  " + 
				"        and mdg.import_error = false  "; 
		if (!wspReporteList.isEmpty()) {
			sql +=	"   and mdg.wsp_report in ("+populateWspReportResults(wspReporteList)+")  ";
		}
		sql +=	") as base  " + 
				"  " + 
				"group by   " + 
				"	grantYear  " + 
				"	, grantStatus  " + 
				"	, entityId  " + 
				"	, companyName  " + 
				"	, tradingName  " + 
				"	, sicCode  " + 
				"	, sicCodeDescription  " + 
				"	, chamber  " + 
				"	, ofoCode  " + 
				"	, ofoCodeDescription  " + 
				"	, interventionType  " + 
				"	, funding";
	
		return (List<GrantSspReportBean>) super.nativeSelectSqlList(sql, GrantSspReportBean.class);
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
	
	public String populateWspReportResults(List<WspReportEnum> entryList) throws Exception{
		StringBuilder result = new StringBuilder();
		int counter = 1;
		for (WspReportEnum entry : entryList) {
			result.append(entry.ordinal());
			if (entryList.size() != counter) {
				result.append(" , ");
			}
			counter ++;
		}
		return result.toString();
	}

}