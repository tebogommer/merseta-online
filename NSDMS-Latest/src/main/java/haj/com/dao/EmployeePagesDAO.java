package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.EmployeePages;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class EmployeePagesDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all EmployeePages
 	 * @author TechFinium 
 	 * @see    EmployeePages
 	 * @return a list of {@link haj.com.entity.EmployeePages}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<EmployeePages> allEmployeePages() throws Exception {
		return (List<EmployeePages>)super.getList("select o from EmployeePages o");
	}

	/**
	 * Get all EmployeePages between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    EmployeePages
 	 * @return a list of {@link haj.com.entity.EmployeePages}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<EmployeePages> allEmployeePages(Long from, int noRows) throws Exception {
	 	String hql = "select o from EmployeePages o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<EmployeePages>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    EmployeePages
 	 * @return a {@link haj.com.entity.EmployeePages}
 	 * @throws Exception global exception
 	 */
	public EmployeePages findByKey(Long id) throws Exception {
	 	String hql = "select o from EmployeePages o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (EmployeePages)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find EmployeePages by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    EmployeePages
  	 * @return a list of {@link haj.com.entity.EmployeePages}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<EmployeePages> findByName(String description) throws Exception {
	 	String hql = "select o from EmployeePages o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<EmployeePages>)super.getList(hql, parameters);
	}
}

