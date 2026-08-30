package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.ToolList;
import haj.com.entity.lookup.ToolListCategory;
import haj.com.entity.lookup.ToolListTools;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class ToolListDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ToolList
 	 * @author TechFinium 
 	 * @see    ToolList
 	 * @return a list of {@link haj.com.entity.ToolList}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ToolList> allToolList() throws Exception {
		return (List<ToolList>)super.getList("select o from ToolList o");
	}

	/**
	 * Get all ToolList between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    ToolList
 	 * @return a list of {@link haj.com.entity.ToolList}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ToolList> allToolList(Long from, int noRows) throws Exception {
	 	String hql = "select o from ToolList o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<ToolList>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    ToolList
 	 * @return a {@link haj.com.entity.ToolList}
 	 * @throws Exception global exception
 	 */
	public ToolList findByKey(Long id) throws Exception {
	 	String hql = "select o from ToolList o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (ToolList)super.getUniqueResult(hql, parameters);
	}


	@SuppressWarnings("unchecked")
	public List<ToolListCategory> allToolListCategory(ToolList toolList) throws Exception {
		String hql = "select o from ToolListCategory o where o.toolList.id = :toolListID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("toolListID", toolList.getId());
		return (List<ToolListCategory>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<ToolListTools> allToolListTools(ToolList toolList) throws Exception {
		String hql = "select o from ToolListTools o where o.toolList.id = :toolListID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("toolListID", toolList.getId());
		return (List<ToolListTools>) super.getList(hql, parameters);
	}
	
	/**
	 * Find ToolList by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    ToolList
  	 * @return a list of {@link haj.com.entity.ToolList}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ToolList> findByName(String description) throws Exception {
	 	String hql = "select o from ToolList o where o.description like  :description order by o.description " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ToolList>)super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<ToolList> findByToolList(Long toolListID) {
		String hql = "select o from ToolList o where o.id = :toolListID " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("toolListID", toolListID);
		return (List<ToolList>)super.getList(hql, parameters);
	}
}

