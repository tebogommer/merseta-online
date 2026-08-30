package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.HostingCompanyDepartments;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyDepartmentsDAO.
 */
public class HostingCompanyDepartmentsDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all HostingCompanyDepartments.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.HostingCompanyDepartments}
	 * @throws Exception global exception
	 * @see    HostingCompanyDepartments
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyDepartments> allHostingCompanyDepartments() throws Exception {
		return (List<HostingCompanyDepartments>)super.getList("select o from HostingCompanyDepartments o");
	}

	/**
	 * Get all HostingCompanyDepartments between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.HostingCompanyDepartments}
	 * @throws Exception global exception
	 * @see    HostingCompanyDepartments
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyDepartments> allHostingCompanyDepartments(Long from, int noRows) throws Exception {
	 	String hql = "select o from HostingCompanyDepartments o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<HostingCompanyDepartments>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.HostingCompanyDepartments}
	 * @throws Exception global exception
	 * @see    HostingCompanyDepartments
	 */
	public HostingCompanyDepartments findByKey(Long id) throws Exception {
	 	String hql = "select o from HostingCompanyDepartments o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (HostingCompanyDepartments)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find HostingCompanyDepartments by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.HostingCompanyDepartments}
	 * @throws Exception global exception
	 * @see    HostingCompanyDepartments
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyDepartments> findByName(String description) throws Exception {
	 	String hql = "select o from HostingCompanyDepartments o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<HostingCompanyDepartments>)super.getList(hql, parameters);
	}
	
	/**
	 * Find by hc.
	 *
	 * @param id the id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyDepartments> findByHc(Long id) throws Exception {
	 	String hql = "select o from HostingCompanyDepartments o where o.hostingCompany.id =:id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (List<HostingCompanyDepartments>)super.getList(hql, parameters);
	}
	
	/**
	 * Find by hc root.
	 *
	 * @param id the id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyDepartments> findByHcRoot(Long id) throws Exception {
	 	String hql = "select o from HostingCompanyDepartments o where o.hostingCompany.id =:id and o.parentDepartment is null" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (List<HostingCompanyDepartments>)super.getList(hql, parameters);
	}
	
	/**
	 * Find by parent.
	 *
	 * @param id the id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyDepartments> findByParent(Long id) throws Exception {
	 	String hql = "select o from HostingCompanyDepartments o where  o.parentDepartment.id = :id" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (List<HostingCompanyDepartments>)super.getList(hql, parameters);
	}
	
	
	/**
	 * Find users department.
	 *
	 * @param id the id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyDepartments> findUsersDepartment(Long id) throws Exception {
		String hql ="select  distinct(o.hostingCompanyDepartments) from HostingCompanyDepartmentsEmployees o " + 
				"where o.hostingCompanyEmployees.users.id = :id";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (List<HostingCompanyDepartments>)super.getList(hql, parameters);
	}

}

