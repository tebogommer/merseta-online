package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.Tool;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class ToolDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Tool
 	 * @author TechFinium 
 	 * @see    Tool
 	 * @return a list of {@link haj.com.entity.Tool}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Tool> allTool() throws Exception {
		return (List<Tool>)super.getList("select o from Tool o");
	}

	/**
	 * Get all Tool between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    Tool
 	 * @return a list of {@link haj.com.entity.Tool}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Tool> allTool(Long from, int noRows) throws Exception {
	 	String hql = "select o from Tool o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Tool>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    Tool
 	 * @return a {@link haj.com.entity.Tool}
 	 * @throws Exception global exception
 	 */
	public Tool findByKey(Long id) throws Exception {
	 	String hql = "select o from Tool o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Tool)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Tool by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    Tool
  	 * @return a list of {@link haj.com.entity.Tool}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Tool> findByName(String description) throws Exception {
	 	String hql = "select o from Tool o where o.description like  :description order by o.description " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Tool>)super.getList(hql, parameters);
	}
}

