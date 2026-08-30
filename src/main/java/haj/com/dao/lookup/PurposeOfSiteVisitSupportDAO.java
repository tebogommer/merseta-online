package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.PurposeOfSiteVisitSupport;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class PurposeOfSiteVisitSupportDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all PurposeOfSiteVisitSupport
 	 * @author TechFinium 
 	 * @see    PurposeOfSiteVisitSupport
 	 * @return a list of {@link haj.com.entity.PurposeOfSiteVisitSupport}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<PurposeOfSiteVisitSupport> allPurposeOfSiteVisitSupport() throws Exception {
		return (List<PurposeOfSiteVisitSupport>)super.getList("select o from PurposeOfSiteVisitSupport o");
	}

	/**
	 * Get all PurposeOfSiteVisitSupport between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    PurposeOfSiteVisitSupport
 	 * @return a list of {@link haj.com.entity.PurposeOfSiteVisitSupport}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<PurposeOfSiteVisitSupport> allPurposeOfSiteVisitSupport(Long from, int noRows) throws Exception {
	 	String hql = "select o from PurposeOfSiteVisitSupport o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<PurposeOfSiteVisitSupport>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    PurposeOfSiteVisitSupport
 	 * @return a {@link haj.com.entity.PurposeOfSiteVisitSupport}
 	 * @throws Exception global exception
 	 */
	public PurposeOfSiteVisitSupport findByKey(Long id) throws Exception {
	 	String hql = "select o from PurposeOfSiteVisitSupport o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (PurposeOfSiteVisitSupport)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find PurposeOfSiteVisitSupport by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    PurposeOfSiteVisitSupport
  	 * @return a list of {@link haj.com.entity.PurposeOfSiteVisitSupport}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<PurposeOfSiteVisitSupport> findByName(String description) throws Exception {
	 	String hql = "select o from PurposeOfSiteVisitSupport o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<PurposeOfSiteVisitSupport>)super.getList(hql, parameters);
	}
}

