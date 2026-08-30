package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyBursary;
import haj.com.entity.lookup.LegacyExperiential;

public class LegacyExperientialDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyExperiential
	 * 
	 * @author TechFinium
	 * @see LegacyExperiential
	 * @return a list of {@link haj.com.entity.LegacyExperiential}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyExperiential> allLegacyExperiential() throws Exception {
		return (List<LegacyExperiential>) super.getList("select o from LegacyExperiential o");
	}

	/**
	 * Get all LegacyExperiential between from and noRows
	 * 
	 * @author TechFinium
	 * @param from   the from
	 * @param noRows the no rows
	 * @see LegacyExperiential
	 * @return a list of {@link haj.com.entity.LegacyExperiential}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyExperiential> allLegacyExperiential(Long from, int noRows) throws Exception {
		String hql = "select o from LegacyExperiential o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<LegacyExperiential>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id the id
	 * @see LegacyExperiential
	 * @return a {@link haj.com.entity.LegacyExperiential}
	 * @throws Exception global exception
	 */
	public LegacyExperiential findByKey(Long id) throws Exception {
		String hql = "select o from LegacyExperiential o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (LegacyExperiential) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyExperiential by description
	 * 
	 * @author TechFinium
	 * @param description the description
	 * @see LegacyExperiential
	 * @return a list of {@link haj.com.entity.LegacyExperiential}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyExperiential> findByName(String description) throws Exception {
		String hql = "select o from LegacyExperiential o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<LegacyExperiential>) super.getList(hql, parameters);
	}

	public Integer countAllResults() throws Exception {
		return ((Long) super.getUniqueResult("select count(o) from LegacyExperiential o")).intValue();
	}

	public Integer countAllLegacyExperientialNotProcessed() throws Exception {
		String hql = "select count(o) from LegacyExperiential o where o.processed = false";
		return ((Long) super.getUniqueResult(hql)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<LegacyExperiential> allLegacyExperientialNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyExperiential o where o.processed = false";
		return (List<LegacyExperiential>) super.getList(hql, numberOfEntries);
	}
}
