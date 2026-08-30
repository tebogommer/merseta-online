package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.ToolCategory;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class ToolCategoryDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ToolCategory
 	 * @author TechFinium 
 	 * @see    ToolCategory
 	 * @return a list of {@link haj.com.entity.ToolCategory}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ToolCategory> allToolCategory() throws Exception {
		return (List<ToolCategory>)super.getList("select o from ToolCategory o");
	}

	/**
	 * Get all ToolCategory between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    ToolCategory
 	 * @return a list of {@link haj.com.entity.ToolCategory}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ToolCategory> allToolCategory(Long from, int noRows) throws Exception {
	 	String hql = "select o from ToolCategory o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<ToolCategory>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    ToolCategory
 	 * @return a {@link haj.com.entity.ToolCategory}
 	 * @throws Exception global exception
 	 */
	public ToolCategory findByKey(Long id) throws Exception {
	 	String hql = "select o from ToolCategory o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (ToolCategory)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ToolCategory by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    ToolCategory
  	 * @return a list of {@link haj.com.entity.ToolCategory}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ToolCategory> findByName(String description) throws Exception {
	 	String hql = "select o from ToolCategory o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ToolCategory>)super.getList(hql, parameters);
	}
}

