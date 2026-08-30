package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyProviderLearnership;

public class LegacyProviderLearnershipDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyProviderLearnership
 	 * @author TechFinium 
 	 * @see    LegacyProviderLearnership
 	 * @return a list of {@link haj.com.entity.LegacyProviderLearnership}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderLearnership> allLegacyProviderLearnership() throws Exception {
		return (List<LegacyProviderLearnership>)super.getList("select o from LegacyProviderLearnership o");
	}

	/**
	 * Get all LegacyProviderLearnership between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyProviderLearnership
 	 * @return a list of {@link haj.com.entity.LegacyProviderLearnership}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderLearnership> allLegacyProviderLearnership(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyProviderLearnership o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyProviderLearnership>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyProviderLearnership
 	 * @return a {@link haj.com.entity.LegacyProviderLearnership}
 	 * @throws Exception global exception
 	 */
	public LegacyProviderLearnership findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyProviderLearnership o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyProviderLearnership)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyProviderLearnership by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyProviderLearnership
  	 * @return a list of {@link haj.com.entity.LegacyProviderLearnership}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderLearnership> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyProviderLearnership o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyProviderLearnership>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyProviderLearnership> findBySldNoAndLearnershipIsNotNull(String sldNo) throws Exception {
	 	String hql = "select o from LegacyProviderLearnership o where o.sldNo = :sldNo and o.learnership is not null " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("sldNo", sldNo);
		return (List<LegacyProviderLearnership>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyProviderLearnership> findByAccreditationNoAndLearnershipIsNotNull(String accreditationNo) throws Exception {
	 	String hql = "select o from LegacyProviderLearnership o where o.accreditationNo = :accreditationNo and o.learnership is not null " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("accreditationNo", accreditationNo);
		return (List<LegacyProviderLearnership>)super.getList(hql, parameters);
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyProviderLearnership o")).intValue();
	}
}

