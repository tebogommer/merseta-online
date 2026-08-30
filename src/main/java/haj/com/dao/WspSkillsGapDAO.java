package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.WspSkillsGap;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.report.bean.MainSkillsGapReport;

public class WspSkillsGapDAO extends AbstractDAO {

	/** The Constant leftJoins. */
	private static final String leftJoins = " " + "left join fetch o.wsp wsgWsp " + " ";

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WspSkillsGap
	 * 
	 * @author TechFinium
	 * @see WspSkillsGap
	 * @return a list of {@link haj.com.entity.WspSkillsGap}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<WspSkillsGap> allWspSkillsGap() throws Exception {
		return (List<WspSkillsGap>) super.getList("select o from WspSkillsGap o");
	}

	/**
	 * Get all WspSkillsGap between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see WspSkillsGap
	 * @return a list of {@link haj.com.entity.WspSkillsGap}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<WspSkillsGap> allWspSkillsGap(Long from, int noRows) throws Exception {
		String hql = "select o from WspSkillsGap o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<WspSkillsGap>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see WspSkillsGap
	 * @return a {@link haj.com.entity.WspSkillsGap}
	 * @throws Exception
	 *             global exception
	 */
	public WspSkillsGap findByKey(Long id) throws Exception {
		String hql = "select o from WspSkillsGap o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (WspSkillsGap) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WspSkillsGap by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see WspSkillsGap
	 * @return a list of {@link haj.com.entity.WspSkillsGap}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<WspSkillsGap> findByName(String description) throws Exception {
		String hql = "select o from WspSkillsGap o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<WspSkillsGap>) super.getList(hql, parameters);
	}

	/**
	 * locates all WspSkillsGap where WSP is null
	 * 
	 * @return the list
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<WspSkillsGap> findByWspNull() throws Exception {
		return (List<WspSkillsGap>) super.getList("select o from WspSkillsGap o where o.wsp is null");
	}

	/**
	 * Locates List of WspSkillsGap by WSP id
	 * 
	 * @param wspId
	 * @return the list
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<WspSkillsGap> findByWsp(Long wspId) throws Exception {
		String hql = "select o from WspSkillsGap o" + leftJoins + " where o.wsp.id = :wspId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		return (List<WspSkillsGap>) super.getList(hql, parameters);
	}

	/**
	 * Locates List of WspSkillsGap by WSP id and by section number. Only currently
	 * section 2 and 3.
	 * 
	 * @param wspId
	 * @return the list
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<WspSkillsGap> findByWspAndSection(Long wspId, Integer sectionNumber) throws Exception {
		String hql = "select o from WspSkillsGap o" + leftJoins + " where o.wsp.id = :wspId and o.skillsGapSection = :sectionNumber";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		parameters.put("sectionNumber", sectionNumber);
		return (List<WspSkillsGap>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<MainSkillsGapReport> findTotalAndSection(Integer sectionNumber) throws Exception {
		String hql = "select new haj.com.report.bean.MainSkillsGapReport(o.rowDescription, "
				+ "SUM(CASE WHEN o.managerSelection = 1 THEN 1 ELSE 0 END), "
				+ "SUM(CASE WHEN o.professionalsSelection = 1 THEN 1 ELSE 0 END), "
				+ "SUM(CASE WHEN o.techniciansAssociateProfessionalsSelection = 1 THEN 1 ELSE 0 END), "
				+ "SUM(CASE WHEN o.clericalSupportWorkersSelection = 1 THEN 1 ELSE 0 END), "
				+ "SUM(CASE WHEN o.serviceSalesWorkersSelection = 1 THEN 1 ELSE 0 END), "
				+ "SUM(CASE WHEN o.skilledTradesWorkersSelection = 1 THEN 1 ELSE 0 END), "
				+ "SUM(CASE WHEN o.plantMachineOperatorsAssemblersSelection = 1 THEN 1 ELSE 0 END), "
				+ "SUM(CASE WHEN o.elementaryWorkersSelection = 1 THEN 1 ELSE 0 END)"
				+ ") "
				+ "from WspSkillsGap o where o.skillsGapSection = :sectionNumber group by o.rowDescription";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("sectionNumber", sectionNumber);
		return (List<MainSkillsGapReport>) super.getList(hql, parameters);
	}
}
