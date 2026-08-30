package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.Department;

public class DepartmentDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Department
 	 * @author TechFinium 
 	 * @see    Department
 	 * @return a list of {@link haj.com.entity.Department}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Department> allDepartment() throws Exception {
		return (List<Department>)super.getList("select o from Department o");
	}

	/**
	 * Get all Department between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    Department
 	 * @return a list of {@link haj.com.entity.Department}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Department> allDepartment(Long from, int noRows) throws Exception {
	 	String hql = "select o from Department o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Department>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    Department
 	 * @return a {@link haj.com.entity.Department}
 	 * @throws Exception global exception
 	 */
	public Department findByKey(Long id) throws Exception {
	 	String hql = "select o from Department o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Department)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Department by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    Department
  	 * @return a list of {@link haj.com.entity.Department}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Department> findByName(String description) throws Exception {
	 	String hql = "select o from Department o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Department>)super.getList(hql, parameters);
	}
}

