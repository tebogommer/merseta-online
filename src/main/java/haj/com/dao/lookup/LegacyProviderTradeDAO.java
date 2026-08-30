package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyProviderTrade;

public class LegacyProviderTradeDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyProviderTrade
 	 * @author TechFinium 
 	 * @see    LegacyProviderTrade
 	 * @return a list of {@link haj.com.entity.LegacyProviderTrade}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderTrade> allLegacyProviderTrade() throws Exception {
		return (List<LegacyProviderTrade>)super.getList("select o from LegacyProviderTrade o");
	}

	/**
	 * Get all LegacyProviderTrade between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyProviderTrade
 	 * @return a list of {@link haj.com.entity.LegacyProviderTrade}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderTrade> allLegacyProviderTrade(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyProviderTrade o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyProviderTrade>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyProviderTrade
 	 * @return a {@link haj.com.entity.LegacyProviderTrade}
 	 * @throws Exception global exception
 	 */
	public LegacyProviderTrade findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyProviderTrade o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyProviderTrade)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyProviderTrade by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyProviderTrade
  	 * @return a list of {@link haj.com.entity.LegacyProviderTrade}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderTrade> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyProviderTrade o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyProviderTrade>)super.getList(hql, parameters);
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyProviderTrade o")).intValue();
	}
}

