package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.bean.SSPListBean;
import haj.com.constants.HAJConstants;
import haj.com.entity.Wsp;
import haj.com.entity.WspSkillsRequirements;
import haj.com.entity.enums.InterventionTypeEnum;
import haj.com.entity.enums.WspReportEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class WspSkillsRequirementsDAO.
 */
public class WspSkillsRequirementsDAO extends AbstractDAO {

	/** The Constant leftNoVarJoins. */
	private static final String leftNoVarJoins = " left join fetch o.wsp wsp" + " left join fetch o.yesNo " + " left join fetch o.ofoCodes " + " left join fetch o.occupationCategory " + " left join fetch o.scarcityReason " + " left join fetch o.interventionType" + " left join fetch o.noHardToFillVacancies nhrfv " + " left join fetch o.vacancyReasons vr " + " left join fetch o.qualification q " + " left join fetch o.highestQualificationRequired hqr ";

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
	 * All wsp skills requirements.
	 *
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<WspSkillsRequirements> allWspSkillsRequirements() throws Exception {
		return (List<WspSkillsRequirements>) super.getList("select o from WspSkillsRequirements o");
	}

	/**
	 * All wsp skills requirements.
	 *
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<WspSkillsRequirements> allWspSkillsRequirements(Long from, int noRows) throws Exception {
		String hql = "select o from WspSkillsRequirements o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<WspSkillsRequirements>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find by key.
	 *
	 * @param id
	 *            the id
	 * @return the wsp skills requirements
	 * @throws Exception
	 *             the exception
	 */
	public WspSkillsRequirements findByKey(Long id) throws Exception {
		String hql = "select o from WspSkillsRequirements o " + leftNoVarJoins + "  where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (WspSkillsRequirements) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find by name.
	 *
	 * @param description
	 *            the description
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<WspSkillsRequirements> findByName(String description) throws Exception {
		String hql = "select o from WspSkillsRequirements o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<WspSkillsRequirements>) super.getList(hql, parameters);
	}

	/**
	 * Find by wsp.
	 *
	 * @param wspId
	 *            the wsp id
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<WspSkillsRequirements> findByWsp(Long wspId) throws Exception {
		String hql = "select o from WspSkillsRequirements o" + leftNoVarJoins + " where o.wsp.id = :wspId order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		return (List<WspSkillsRequirements>) super.getList(hql, parameters);

	}

	/**
	 * Lazy load WspSkillsRequirements with pagination, filter, sorting.
	 *
	 * @param entity
	 *            the entity
	 * @param startingAt
	 *            the starting at
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @param hql
	 *            the hql
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List<?> findByWsp(Class<WspSkillsRequirements> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long wspid) {
		String hql = "select o from " + entity.getName() + " o" + leftNoVarJoins + " where o.wsp.id = :wsp";
		filters.put("wsp", wspid);
		return (List<WspSkillsRequirements>) super.sortAndFilterWhere(startingAt, pageSize, sortField, sortOrder, filters, hql);
	}

	/**
	 * All WspSkillsRequirements from WSP count.
	 *
	 * @param filters
	 *            the filters
	 * @param Wsp
	 *            theWsp
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public long allWspSkillsRequirementsCount(Map<String, Object> filters, Wsp wsp) throws Exception {
		String hql = "select count(o) from WspSkillsRequirements o where o.wsp.id = :wsp";
		filters.put("wsp", wsp.getId());
		return (long) super.getUniqueResult(hql, filters);
	}

	@SuppressWarnings("unchecked")
	public List<WspSkillsRequirements> findByYear(int finYear, InterventionTypeEnum interventionTypeEnum) throws Exception {
		String namedQuery = "NQ_NATIVE_tradesAllocation";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("finYear", finYear);
		parameters.put("noDisability", HAJConstants.NO_DISIBILITY);
		parameters.put("dgFund", HAJConstants.DISC_FUNDING_ID);
		parameters.put("interventionTypeEnum", interventionTypeEnum.ordinal());
		parameters.put("wspReport", WspReportEnum.WSP.ordinal());
		return (List<WspSkillsRequirements>) getNQList(namedQuery, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<WspSkillsRequirements> findByYear(int finYear) throws Exception {
		String namedQuery = "NQ_NATIVE_all";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("finYear", finYear);
		parameters.put("noDisability", HAJConstants.NO_DISIBILITY);
		parameters.put("dgFund", HAJConstants.DISC_FUNDING_ID);
		parameters.put("wspReport", WspReportEnum.WSP.ordinal());
		return (List<WspSkillsRequirements>) getNQList(namedQuery, parameters);
	}
	
	public List<SSPListBean> findAllocationList(int finYear) throws Exception {
		String sql = "select  it.description as interventionType,  CONCAT('(', coalesce(ofo.ofo_code, ofo2.ofo_code), ') ',ofo.description) as ofoCode,  man_grant.total as pivitolApplicationTotal,  man_grant2.total as pivitolApplicationTotalPWD,  'Yes' as sspPriotiy,  al.total_allocation_for_year as totalAllocation from allocation_list al  left join ( select mg.ofo_code_id, mg.intervention_type_id, count(mg.id) AS total  "
				+ "from mandatory_grant_detail mg  left join wsp on mg.wsp_id = wsp.id  left join intervention_type on mg.intervention_type_id = intervention_type.id  where wsp.fin_year = :finYear  and mg.imported = true  and mg.funding_id = :dgFund  and mg.wsp_report = :wspReport   group by mg.ofo_code_id, mg.intervention_type_id ) AS man_grant  on al.intervention_type_id = man_grant.intervention_type_id  and al.ofo_codes_id = man_grant.ofo_code_id "
				+ "left join ( select mg.ofo_code_id, mg.intervention_type_id, count(mg.id) AS total  from mandatory_grant_detail mg  left join wsp on mg.wsp_id = wsp.id  left join intervention_type on mg.intervention_type_id = intervention_type.id  where wsp.fin_year = :finYear  and mg.imported = true  and mg.disability_id <> :noDisability and mg.funding_id = :dgFund  and mg.wsp_report = :wspReport   group by mg.ofo_code_id, mg.intervention_type_id ) AS man_grant2  "
				+ "on al.intervention_type_id = man_grant2.intervention_type_id  and al.ofo_codes_id = man_grant2.ofo_code_id left join ofo_codes ofo on al.ofo_codes_id = ofo.id left join ofo_codes ofo2 on ofo2.id = ofo.ofo_codes_id  left join intervention_type it on it.id = al.intervention_type_id  union select  it.description as interventionType,  CONCAT('(', coalesce(ofo.ofo_code, ofo2.ofo_code), ') ',ofo.description) as ofoCode,  "
				+ "man_grant.total as pivitolApplicationTotal,  man_grant2.total as pivitolApplicationTotalPWD,  'No' as sspPriotiy,  0 as totalAllocation from mandatory_grant al  left join ( select mg.ofo_code_id, mg.intervention_type_id, count(mg.id) AS total  from mandatory_grant_detail mg  left join wsp on mg.wsp_id = wsp.id  left join intervention_type on mg.intervention_type_id = intervention_type.id  where wsp.fin_year = :finYear  "
				+ "and mg.imported = true  and mg.funding_id = :dgFund  and mg.wsp_report = :wspReport   group by mg.ofo_code_id, mg.intervention_type_id ) AS man_grant  on al.intervention_type_id = man_grant.intervention_type_id  and al.ofo_code_id = man_grant.ofo_code_id left join ( select mg.ofo_code_id, mg.intervention_type_id, count(mg.id) AS total  from mandatory_grant_detail mg  left join wsp on mg.wsp_id = wsp.id  "
				+ "left join intervention_type on mg.intervention_type_id = intervention_type.id  where wsp.fin_year = :finYear  and mg.imported = true  and mg.disability_id <> :noDisability and mg.funding_id = :dgFund  and mg.wsp_report = :wspReport   group by mg.ofo_code_id, mg.intervention_type_id ) AS man_grant2  on al.intervention_type_id = man_grant2.intervention_type_id  and al.ofo_code_id = man_grant2.ofo_code_id left join ofo_codes ofo on al.ofo_code_id = ofo.id "
				+ "left join ofo_codes ofo2 on ofo2.id = ofo.ofo_codes_id  left join intervention_type it on it.id = al.intervention_type_id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("finYear", finYear);
		parameters.put("noDisability", HAJConstants.NO_DISIBILITY);
		parameters.put("dgFund", HAJConstants.DISC_FUNDING_ID);
		parameters.put("wspReport", WspReportEnum.WSP.ordinal());
		return (List<SSPListBean>) nativeSelectSqlList(sql, SSPListBean.class, parameters);

	}

	public int findByYearCount(Map<String, Object> filters, int finYear) throws Exception {
		String hql = "select count(o) from WspSkillsRequirements o where o.wsp.finYear = :finYear";
		filters.put("finYear", finYear);
		return super.countWhere(filters, hql);
	}

	/**
	 * Find by wsp count.
	 *
	 * @param wspId
	 *            the wsp id
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public long findByWspCount(Long wspId) throws Exception {
		String hql = "select count(o) from WspSkillsRequirements o where o.wsp.id = :wspId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		return (long) super.getUniqueResult(hql, parameters);
	}
}
