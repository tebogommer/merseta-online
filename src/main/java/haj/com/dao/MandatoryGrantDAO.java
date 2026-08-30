package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Company;
import haj.com.entity.MandatoryGrant;
import haj.com.entity.Wsp;
import haj.com.entity.enums.WspReportEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class MandatoryGrantDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all MandatoryGrant
	 * 
	 * @author TechFinium
	 * @see MandatoryGrant
	 * @return a list of {@link haj.com.entity.MandatoryGrant}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrant> allMandatoryGrant() throws Exception {
		return (List<MandatoryGrant>) super.getList("select o from MandatoryGrant o");
	}

	/**
	 * Get all MandatoryGrant between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see MandatoryGrant
	 * @return a list of {@link haj.com.entity.MandatoryGrant}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrant> allMandatoryGrant(Long from, int noRows) throws Exception {
		String hql = "select o from MandatoryGrant o where o.imported = true ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<MandatoryGrant>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see MandatoryGrant
	 * @return a {@link haj.com.entity.MandatoryGrant}
	 * @throws Exception
	 *             global exception
	 */
	public MandatoryGrant findByKey(Long id) throws Exception {
		String hql = "select o from MandatoryGrant o where o.id = :id and  o.imported = true ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (MandatoryGrant) super.getUniqueResult(hql, parameters);
	}
	
	public MandatoryGrant findByKeyExcludeImport(Long id) throws Exception {
		String hql = "select o from MandatoryGrant o where o.id = :id";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (MandatoryGrant) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find MandatoryGrant by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see MandatoryGrant
	 * @return a list of {@link haj.com.entity.MandatoryGrant}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrant> findByName(String description) throws Exception {
		String hql = "select o from MandatoryGrant o where o.description like  :description and  o.imported = true  order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<MandatoryGrant>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrant> findByWSP(Wsp wsp, WspReportEnum wspReport) throws Exception {
		String hql = "select o from MandatoryGrant o where o.wsp.id = :wspID and o.wspReport =:wspReport and  o.imported = true ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("wspReport", wspReport);
		return (List<MandatoryGrant>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<MandatoryGrant> findByWSPDgVerification(Wsp wsp) throws Exception {
		String hql = "select o from MandatoryGrant o where o.wsp.id = :wspID and  o.imported = true ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		return (List<MandatoryGrant>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<MandatoryGrant> findByWSPForDgVerificationIgnoreImport(Wsp wsp) throws Exception {
		String hql = "select o from MandatoryGrant o where o.wsp.id = :wspID ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		return (List<MandatoryGrant>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrant> findByWSPCsv(Wsp wsp, WspReportEnum wspReport) throws Exception {
		String hql = "select o from MandatoryGrant o where o.wsp.id = :wspID and o.wspReport =:wspReport and  o.imported = false ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("wspReport", wspReport);
		return (List<MandatoryGrant>) super.getList(hql, parameters);
	}

	public long findByWSPCount(Wsp wsp, WspReportEnum wspReport) throws Exception {
		String hql = "select count(o) from MandatoryGrantDetail o where o.wsp.id = :wspID and o.wspReport =:wspReport and  o.imported = true ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("wspReport", wspReport);
		return (long) super.getUniqueResult(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrant> findByWSPPivotalPlan(Wsp wsp, WspReportEnum wspReport, Long nqfAlignedID) throws Exception {
		String hql = "select o from MandatoryGrant o where o.wsp.id = :wspID and o.wspReport =:wspReport and o.nqfAligned.id = :nqfAligned and  o.imported = true ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("wspReport", wspReport);
		parameters.put("nqfAligned", nqfAlignedID);
		return (List<MandatoryGrant>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrant> findByWSPNotHosting(Wsp wsp, WspReportEnum wspReport, String etqaCode) throws Exception {
		String hql = "select o from MandatoryGrant o where o.wsp.id = :wspID and o.wspReport =:wspReport and o.etqa.code <> :etqaCode and o.etqa is not null and  o.imported = true  ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("wspReport", wspReport);
		parameters.put("etqaCode", etqaCode);
		return (List<MandatoryGrant>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrant> findByWSPHosting(WspReportEnum wspReport, String etqaCode) throws Exception {
		String hql = "select o from MandatoryGrant o where o.wspReport =:wspReport and o.etqa.code = :etqaCode and o.etqa is not null and  o.imported = true ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspReport", wspReport);
		parameters.put("etqaCode", etqaCode);
		return (List<MandatoryGrant>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrant> findByWSPHosting(Wsp wsp, WspReportEnum wspReport, long fundingID) throws Exception {
		String hql = "select o from MandatoryGrant o left join fetch o.etqa e where o.wsp.id = :wspID and o.wspReport =:wspReport and o.funding.id = :fundingID and  o.imported = true ";
		// String hql = "select o from MandatoryGrant o where o.wsp.id = :wspID and
		// o.wspReport =:wspReport and (o.etqa.code = :etqaCode or o.funding.id =
		// :fundingID)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("wspReport", wspReport);
		parameters.put("fundingID", fundingID);
		return (List<MandatoryGrant>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrant> findByCompanyFinYear(Company company, WspReportEnum wspReport, long fundingID, long finYear) throws Exception {
		String hql = "select o from MandatoryGrant o left join fetch o.etqa e where o.wsp.companyID.id = :wspID and o.wsp.finYear = :finYear and o.wspReport =:wspReport and o.funding.id = :fundingID and  o.imported = true ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("wspReport", wspReport);
		parameters.put("fundingID", fundingID);
		parameters.put("finYear", finYear);
		return (List<MandatoryGrant>) super.getList(hql, parameters);
	}

	public Double findByCompanyFinYearTotal(Company company, WspReportEnum wspReport, long fundingID, int finYear) throws Exception {
		String hql = "select sum(o.estimatedCost) from MandatoryGrant o where o.wsp.company.id = :companyID and o.wsp.finYear = :finYear and o.wspReport =:wspReport and o.funding.id = :fundingID and  o.imported = true ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("wspReport", wspReport);
		parameters.put("fundingID", fundingID);
		parameters.put("finYear", finYear);
		return (Double) super.getUniqueResult(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<Wsp> findRandomCompanies(Integer finYear, int limit) {
		String hql = "select distinct(o.wsp) from MandatoryGrantDetail o "
					+ "where o.wsp.finYear =:finYear "
					+ "and o.wsp.id not in ("
					
					+ "select x.wsp.id "
					+ "from MgVerification x "
					+ "where x.finalFin > :finYear"
					
					+ ") and  o.imported = true "
					+ "order by rand()  ";
		
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("finYear", finYear);
		return (List<Wsp>) super.getList(hql, parameters, limit);
	}
	
	@SuppressWarnings("unchecked")
	public List<Company> findRandomCompaniesSDF(Integer finYear, int limit) {
		
		String hql = "select distinct(o.company) from SDFCompany o "
					+ "where o.company.id not in ("
					+ "select x.wsp.company.id "
					+ "from MgVerification x "
					+ "where x.finalFin > :finYear"
					+ ") and  o.imported = true "
					+ "order by rand()  ";
		
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("finYear", finYear);
		return (List<Company>) super.getList(hql, parameters, limit);
		
	}

	@SuppressWarnings("unchecked")
	public List<Wsp> findCompaniesForVerification(Integer finYear) {
		String hql = "select distinct(o.wsp) from MandatoryGrant o where o.wsp.finYear =:finYear and  o.imported = true ";
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

	public long countDistinctWSP(Integer finYear) {
		String hql = "select count(distinct o.wsp) from MandatoryGrantDetail o where o.wsp.finYear =:finYear and o.wsp.id not in (select x.wsp.id from MgVerification x where x.finalFin > :finYear) and  o.imported = true";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("finYear", finYear);
		return (long) super.getUniqueResult(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrant> findByWSP(Wsp wsp) throws Exception {
		String hql = "select o from MandatoryGrant o left join fetch o.ofoCodes oc left join fetch oc.ofoCodes where o.wsp.id = :wspID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		return (List<MandatoryGrant>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrant> allMandatoryGrant(int finYear) {
		String hql = "select o from MandatoryGrant o where o.wsp.finYear =:finYear order by o.wsp.id desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("finYear", finYear);
		return (List<MandatoryGrant>) super.getList(hql, parameters);
	}


	@SuppressWarnings("unchecked")
	public List<MandatoryGrant> allMandatoryGrant(Wsp wsp) {
		String hql = "select o from MandatoryGrant o where o.wsp.id =:wspId order by o.interventionType.prioritisationScale asc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wsp.getId());
		return (List<MandatoryGrant>) super.getList(hql, parameters);
	}
	
	public Integer countMandatoryGrantByWspId(Long wspId) throws Exception{
		String hql = "select count(o) from MandatoryGrant o where o.wsp.id = :wspId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	

	
	public int countByWSP(Wsp wsp, WspReportEnum wspReport) throws Exception {
		String hql = "select count(o) from MandatoryGrant o where o.wsp.id = :wspID and o.wspReport =:wspReport and  o.imported = true ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("wspID", wsp.getId());
		parameters.put("wspReport", wspReport);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
}
