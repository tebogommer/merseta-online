package haj.com.dao.lookup;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.GrantOfoSelection;

public class GrantOfoSelectionDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all GrantOfoSelection
 	 * @author TechFinium 
 	 * @see    GrantOfoSelection
 	 * @return a list of {@link haj.com.entity.GrantOfoSelection}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<GrantOfoSelection> allGrantOfoSelection() throws Exception {
		return (List<GrantOfoSelection>)super.getList("select o from GrantOfoSelection o");
	}

	/**
	 * Get all GrantOfoSelection between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    GrantOfoSelection
 	 * @return a list of {@link haj.com.entity.GrantOfoSelection}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<GrantOfoSelection> allGrantOfoSelection(Long from, int noRows) throws Exception {
	 	String hql = "select o from GrantOfoSelection o " ;
	    Map<String, Object> parameters = new HashMap<>();
	    
		return (List<GrantOfoSelection>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    GrantOfoSelection
 	 * @return a {@link haj.com.entity.GrantOfoSelection}
 	 * @throws Exception global exception
 	 */
	public GrantOfoSelection findByKey(Long id) throws Exception {
	 	String hql = "select o from GrantOfoSelection o where o.id = :id " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("id", id);
		return (GrantOfoSelection)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find GrantOfoSelection by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    GrantOfoSelection
  	 * @return a list of {@link haj.com.entity.GrantOfoSelection}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<GrantOfoSelection> findByName(String description) throws Exception {
	 	String hql = "select o from GrantOfoSelection o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<GrantOfoSelection>)super.getList(hql, parameters);
	}
	
	public GrantOfoSelection findByGrantYear(Integer grantYear) throws Exception {
	 	String hql = "select o from GrantOfoSelection o where o.grantYear = :grantYear " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("grantYear", grantYear);
		return (GrantOfoSelection)super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<GrantOfoSelection> findByGrantYearList(Integer grantYear) throws Exception {
		String hql = "select o from GrantOfoSelection o where o.grantYear = :grantYear " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("grantYear", grantYear);
		return (List<GrantOfoSelection>)super.getList(hql, parameters);
	}
}

