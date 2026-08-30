package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.ToolListCategory;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class ToolListCategoryDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ToolListCategory
 	 * @author TechFinium 
 	 * @see    ToolListCategory
 	 * @return a list of {@link haj.com.entity.ToolListCategory}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ToolListCategory> allToolListCategory() throws Exception {
		return (List<ToolListCategory>)super.getList("select o from ToolListCategory o");
	}

	/**
	 * Get all ToolListCategory between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    ToolListCategory
 	 * @return a list of {@link haj.com.entity.ToolListCategory}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ToolListCategory> allToolListCategory(Long from, int noRows) throws Exception {
	 	String hql = "select o from ToolListCategory o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<ToolListCategory>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    ToolListCategory
 	 * @return a {@link haj.com.entity.ToolListCategory}
 	 * @throws Exception global exception
 	 */
	public ToolListCategory findByKey(Long id) throws Exception {
	 	String hql = "select o from ToolListCategory o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (ToolListCategory)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ToolListCategory by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    ToolListCategory
  	 * @return a list of {@link haj.com.entity.ToolListCategory}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ToolListCategory> findByName(String description) throws Exception {
	 	String hql = "select o from ToolListCategory o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ToolListCategory>)super.getList(hql, parameters);
	}
}

