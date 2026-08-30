package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyDTTCTrade;
import haj.com.entity.lookup.LegacyExperiential;

public class LegacyDTTCTradeDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyDTTCTrade
 	 * @author TechFinium 
 	 * @see    LegacyDTTCTrade
 	 * @return a list of {@link haj.com.entity.LegacyDTTCTrade}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyDTTCTrade> allLegacyDTTCTrade() throws Exception {
		return (List<LegacyDTTCTrade>)super.getList("select o from LegacyDTTCTrade o");
	}

	/**
	 * Get all LegacyDTTCTrade between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyDTTCTrade
 	 * @return a list of {@link haj.com.entity.LegacyDTTCTrade}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyDTTCTrade> allLegacyDTTCTrade(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyDTTCTrade o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyDTTCTrade>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyDTTCTrade
 	 * @return a {@link haj.com.entity.LegacyDTTCTrade}
 	 * @throws Exception global exception
 	 */
	public LegacyDTTCTrade findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyDTTCTrade o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyDTTCTrade)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyDTTCTrade by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyDTTCTrade
  	 * @return a list of {@link haj.com.entity.LegacyDTTCTrade}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyDTTCTrade> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyDTTCTrade o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyDTTCTrade>)super.getList(hql, parameters);
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long) super.getUniqueResult("select count(o) from LegacyDTTCTrade o")).intValue();
	}

	public Integer countAllLegacyDTTCTradeNotProcessed() throws Exception {
		String hql = "select count(o) from LegacyDTTCTrade o where o.processed = false";
		return ((Long) super.getUniqueResult(hql)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<LegacyDTTCTrade> allLegacyDTTCTradeNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyExperiential o where o.processed = false";
		return (List<LegacyDTTCTrade>) super.getList(hql, numberOfEntries);
	}
}

