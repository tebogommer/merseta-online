package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyEmployerWA2UnitStandard;
import haj.com.entity.lookup.LegacyEmployerWA2UnitStandard;

public class LegacyEmployerWA2UnitStandardDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyEmployerWA2UnitStandard
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2UnitStandard
	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2UnitStandard}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2UnitStandard> allLegacyEmployerWA2UnitStandard() throws Exception {
		return (List<LegacyEmployerWA2UnitStandard>) super.getList("select o from LegacyEmployerWA2UnitStandard o");
	}

	/**
	 * Get all LegacyEmployerWA2UnitStandard between from and noRows
	 * 
	 * @author TechFinium
	 * @param from   the from
	 * @param noRows the no rows
	 * @see LegacyEmployerWA2UnitStandard
	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2UnitStandard}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2UnitStandard> allLegacyEmployerWA2UnitStandard(Long from, int noRows)
			throws Exception {
		String hql = "select o from LegacyEmployerWA2UnitStandard o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<LegacyEmployerWA2UnitStandard>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id the id
	 * @see LegacyEmployerWA2UnitStandard
	 * @return a {@link haj.com.entity.LegacyEmployerWA2UnitStandard}
	 * @throws Exception global exception
	 */
	public LegacyEmployerWA2UnitStandard findByKey(Long id) throws Exception {
		String hql = "select o from LegacyEmployerWA2UnitStandard o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (LegacyEmployerWA2UnitStandard) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyEmployerWA2UnitStandard by description
	 * 
	 * @author TechFinium
	 * @param description the description
	 * @see LegacyEmployerWA2UnitStandard
	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2UnitStandard}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2UnitStandard> findByName(String description) throws Exception {
		String hql = "select o from LegacyEmployerWA2UnitStandard o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<LegacyEmployerWA2UnitStandard>) super.getList(hql, parameters);
	}

	public Integer countAllResults() throws Exception {
		return ((Long) super.getUniqueResult("select count(o) from LegacyEmployerWA2UnitStandard o")).intValue();
	}
	
	public Integer countAllLegacyEmployerWA2UnitStandardNotProcessed() throws Exception {
		String hql = "select count(o) from LegacyEmployerWA2UnitStandard o where o.processed = false";
		return ((Long) super.getUniqueResult(hql)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2UnitStandard> allLegacyEmployerWA2UnitStandardNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyEmployerWA2UnitStandard o where o.processed = false";
		return (List<LegacyEmployerWA2UnitStandard>) super.getList(hql, numberOfEntries);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2UnitStandard> findByLevy(String linkedSdl) throws Exception {
		String hql = "select o from LegacyEmployerWA2UnitStandard o where o.linkedSdl = :linkedSdl ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("linkedSdl", linkedSdl);
		return (List<LegacyEmployerWA2UnitStandard>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2UnitStandard> findDistinctByLevy(String linkedSdl) throws Exception {
		String hql = "select distinct(o) from LegacyEmployerWA2UnitStandard o where o.linkedSdl = :linkedSdl ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("linkedSdl", linkedSdl);
		return (List<LegacyEmployerWA2UnitStandard>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2UnitStandard> findDistinctByLevy(String linkedSdl, String sdlNo) throws Exception {
		String hql = "select distinct(o) from LegacyEmployerWA2UnitStandard o where o.linkedSdl = :linkedSdl and o.sdlNo = :sdlNo";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("linkedSdl", linkedSdl);
		parameters.put("sdlNo", sdlNo);
		return (List<LegacyEmployerWA2UnitStandard>) super.getList(hql, parameters);
	}
}
