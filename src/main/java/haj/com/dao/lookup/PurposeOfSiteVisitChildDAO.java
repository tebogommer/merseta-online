package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.PurposeOfSiteVisitChild;

public class PurposeOfSiteVisitChildDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all PurposeOfSiteVisitChild
 	 * @author TechFinium 
 	 * @see    PurposeOfSiteVisitChild
 	 * @return a list of {@link haj.com.entity.PurposeOfSiteVisitChild}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<PurposeOfSiteVisitChild> allPurposeOfSiteVisitChild() throws Exception {
		return (List<PurposeOfSiteVisitChild>)super.getList("select o from PurposeOfSiteVisitChild o");
	}

	/**
	 * Get all PurposeOfSiteVisitChild between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    PurposeOfSiteVisitChild
 	 * @return a list of {@link haj.com.entity.PurposeOfSiteVisitChild}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<PurposeOfSiteVisitChild> allPurposeOfSiteVisitChild(Long from, int noRows) throws Exception {
	 	String hql = "select o from PurposeOfSiteVisitChild o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<PurposeOfSiteVisitChild>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    PurposeOfSiteVisitChild
 	 * @return a {@link haj.com.entity.PurposeOfSiteVisitChild}
 	 * @throws Exception global exception
 	 */
	public PurposeOfSiteVisitChild findByKey(Long id) throws Exception {
	 	String hql = "select o from PurposeOfSiteVisitChild o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (PurposeOfSiteVisitChild)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find PurposeOfSiteVisitChild by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    PurposeOfSiteVisitChild
  	 * @return a list of {@link haj.com.entity.PurposeOfSiteVisitChild}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<PurposeOfSiteVisitChild> findByName(String description) throws Exception {
	 	String hql = "select o from PurposeOfSiteVisitChild o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<PurposeOfSiteVisitChild>)super.getList(hql, parameters);
	}
}

