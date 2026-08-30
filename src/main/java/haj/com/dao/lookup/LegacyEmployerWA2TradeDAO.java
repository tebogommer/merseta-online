package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyEmployerWA2Trade;
import haj.com.entity.lookup.LegacyExperiential;

public class LegacyEmployerWA2TradeDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyEmployerWA2Trade
 	 * @author TechFinium 
 	 * @see    LegacyEmployerWA2Trade
 	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2Trade}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2Trade> allLegacyEmployerWA2Trade() throws Exception {
		return (List<LegacyEmployerWA2Trade>)super.getList("select o from LegacyEmployerWA2Trade o");
	}

	/**
	 * Get all LegacyEmployerWA2Trade between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyEmployerWA2Trade
 	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2Trade}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2Trade> allLegacyEmployerWA2Trade(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyEmployerWA2Trade o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyEmployerWA2Trade>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyEmployerWA2Trade
 	 * @return a {@link haj.com.entity.LegacyEmployerWA2Trade}
 	 * @throws Exception global exception
 	 */
	public LegacyEmployerWA2Trade findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyEmployerWA2Trade o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyEmployerWA2Trade)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyEmployerWA2Trade by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyEmployerWA2Trade
  	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2Trade}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2Trade> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyEmployerWA2Trade o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyEmployerWA2Trade>)super.getList(hql, parameters);
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyEmployerWA2Trade o")).intValue();
	}
	
	public Integer countAllLegacyEmployerWA2TradeNotProcessed() throws Exception {
		String hql = "select count(o) from LegacyEmployerWA2Trade o where o.processed = false";
		return ((Long) super.getUniqueResult(hql)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2Trade> allLegacyEmployerWA2TradeNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyEmployerWA2Trade o where o.processed = false";
		return (List<LegacyEmployerWA2Trade>) super.getList(hql, numberOfEntries);
	}

	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2Trade> findByLevy(String linkedSdl) {
		String hql = "select o from LegacyEmployerWA2Trade o where o.linkedSdl = :linkedSdl " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("linkedSdl", linkedSdl);
		return (List<LegacyEmployerWA2Trade>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2Trade> findDistinctByLevy(String linkedSdl) {
		String hql = "select distinct(o) from LegacyEmployerWA2Trade o where o.linkedSdl = :linkedSdl " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("linkedSdl", linkedSdl);
		return (List<LegacyEmployerWA2Trade>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2Trade> findDistinctByLevy(String linkedSdl, String sdlNo) {
		String hql = "select distinct(o) from LegacyEmployerWA2Trade o where o.linkedSdl = :linkedSdl and o.sdlNo = :sdlNo" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("linkedSdl", linkedSdl);
	    parameters.put("sdlNo", sdlNo);
		return (List<LegacyEmployerWA2Trade>)super.getList(hql, parameters);
	}
}

