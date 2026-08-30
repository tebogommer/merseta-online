package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.LegacyProviderApplicationSiteAllocation;

public class LegacyProviderApplicationSiteAllocationDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyProviderApplicationSiteAllocation
 	 * @author TechFinium 
 	 * @see    LegacyProviderApplicationSiteAllocation
 	 * @return a list of {@link haj.com.entity.LegacyProviderApplicationSiteAllocation}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderApplicationSiteAllocation> allLegacyProviderApplicationSiteAllocation() throws Exception {
		return (List<LegacyProviderApplicationSiteAllocation>)super.getList("select o from LegacyProviderApplicationSiteAllocation o");
	}

	/**
	 * Get all LegacyProviderApplicationSiteAllocation between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyProviderApplicationSiteAllocation
 	 * @return a list of {@link haj.com.entity.LegacyProviderApplicationSiteAllocation}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderApplicationSiteAllocation> allLegacyProviderApplicationSiteAllocation(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyProviderApplicationSiteAllocation o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyProviderApplicationSiteAllocation>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyProviderApplicationSiteAllocation
 	 * @return a {@link haj.com.entity.LegacyProviderApplicationSiteAllocation}
 	 * @throws Exception global exception
 	 */
	public LegacyProviderApplicationSiteAllocation findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyProviderApplicationSiteAllocation o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyProviderApplicationSiteAllocation)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyProviderApplicationSiteAllocation by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyProviderApplicationSiteAllocation
  	 * @return a list of {@link haj.com.entity.LegacyProviderApplicationSiteAllocation}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderApplicationSiteAllocation> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyProviderApplicationSiteAllocation o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyProviderApplicationSiteAllocation>)super.getList(hql, parameters);
	}
}

