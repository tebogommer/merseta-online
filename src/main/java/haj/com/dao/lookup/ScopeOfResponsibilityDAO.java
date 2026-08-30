package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.ScopeOfResponsibility;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class ScopeOfResponsibilityDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ScopeOfResponsibility
 	 * @author TechFinium 
 	 * @see    ScopeOfResponsibility
 	 * @return a list of {@link haj.com.entity.ScopeOfResponsibility}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ScopeOfResponsibility> allScopeOfResponsibility() throws Exception {
		return (List<ScopeOfResponsibility>)super.getList("select o from ScopeOfResponsibility o");
	}

	/**
	 * Get all ScopeOfResponsibility between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    ScopeOfResponsibility
 	 * @return a list of {@link haj.com.entity.ScopeOfResponsibility}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ScopeOfResponsibility> allScopeOfResponsibility(Long from, int noRows) throws Exception {
	 	String hql = "select o from ScopeOfResponsibility o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<ScopeOfResponsibility>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    ScopeOfResponsibility
 	 * @return a {@link haj.com.entity.ScopeOfResponsibility}
 	 * @throws Exception global exception
 	 */
	public ScopeOfResponsibility findByKey(Long id) throws Exception {
	 	String hql = "select o from ScopeOfResponsibility o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (ScopeOfResponsibility)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ScopeOfResponsibility by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    ScopeOfResponsibility
  	 * @return a list of {@link haj.com.entity.ScopeOfResponsibility}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ScopeOfResponsibility> findByName(String description) throws Exception {
	 	String hql = "select o from ScopeOfResponsibility o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ScopeOfResponsibility>)super.getList(hql, parameters);
	}
}

