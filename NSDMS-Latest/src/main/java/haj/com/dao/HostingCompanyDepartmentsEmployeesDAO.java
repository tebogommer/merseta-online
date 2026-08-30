package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.HostingCompanyDepartmentsChatEmployees;
import haj.com.entity.HostingCompanyDepartmentsEmployees;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.lookup.Roles;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyDepartmentsEmployeesDAO.
 */
public class HostingCompanyDepartmentsEmployeesDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all HostingCompanyDepartmentsEmployees.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.HostingCompanyDepartmentsEmployees}
	 * @throws Exception global exception
	 * @see    HostingCompanyDepartmentsEmployees
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyDepartmentsEmployees> allHostingCompanyDepartmentsEmployees() throws Exception {
		return (List<HostingCompanyDepartmentsEmployees>)super.getList("select o from HostingCompanyDepartmentsEmployees o");
	}

	/**
	 * Get all HostingCompanyDepartmentsEmployees between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.HostingCompanyDepartmentsEmployees}
	 * @throws Exception global exception
	 * @see    HostingCompanyDepartmentsEmployees
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyDepartmentsEmployees> allHostingCompanyDepartmentsEmployees(Long from, int noRows) throws Exception {
	 	String hql = "select o from HostingCompanyDepartmentsEmployees o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<HostingCompanyDepartmentsEmployees>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.HostingCompanyDepartmentsEmployees}
	 * @throws Exception global exception
	 * @see    HostingCompanyDepartmentsEmployees
	 */
	public HostingCompanyDepartmentsEmployees findByKey(Long id) throws Exception {
	 	String hql = "select o from HostingCompanyDepartmentsEmployees o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (HostingCompanyDepartmentsEmployees)super.getUniqueResult(hql, parameters);
	}


	public HostingCompanyDepartmentsEmployees findByDeptAndHostingCompanyEmployees(Long hostingCompanyDepartmentsId, Long hostingCompanyEmployeesId ) throws Exception {
	 	String hql = "select o from HostingCompanyDepartmentsEmployees o where o.hostingCompanyDepartments.id = :hostingCompanyDepartmentsId  and o.hostingCompanyEmployees.id = :hostingCompanyEmployeesId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyDepartmentsId", hostingCompanyDepartmentsId);
	    parameters.put("hostingCompanyEmployeesId", hostingCompanyEmployeesId);
		return (HostingCompanyDepartmentsEmployees)super.getUniqueResult(hql, parameters);
	}
	/**
	 * Find HostingCompanyDepartmentsEmployees by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.HostingCompanyDepartmentsEmployees}
	 * @throws Exception global exception
	 * @see    HostingCompanyDepartmentsEmployees
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyDepartmentsEmployees> findByName(String description) throws Exception {
	 	String hql = "select o from HostingCompanyDepartmentsEmployees o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<HostingCompanyDepartmentsEmployees>)super.getList(hql, parameters);
	}

	/**
	 * Find by department.
	 *
	 * @param id the id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyDepartmentsEmployees> findByDepartment(Long id) throws Exception {
	 	String hql = "select o from HostingCompanyDepartmentsEmployees o where o.hostingCompanyDepartments.id = :id" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (List<HostingCompanyDepartmentsEmployees>)super.getList(hql, parameters);
	}

	/**
	 * Find all not used employees.
	 *
	 * @param hostingCompanyId the hosting company id
	 * @param departmentId the department id
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyEmployees> findAllNotUsedEmployees(Long hostingCompanyId, Long departmentId) {
	 	String hql = "select o from HostingCompanyEmployees o "
	 			+ " where o.hostingCompany.id = :hostingCompanyId "
	 			+ " and o.id not in (select x.hostingCompanyEmployees.id from HostingCompanyDepartmentsEmployees x where x.id = :departmentId )";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyId", hostingCompanyId);
	    parameters.put("departmentId", departmentId);
		return (List<HostingCompanyEmployees>)super.getList(hql, parameters);
	}

	/**
	 * Find employees by role.
	 *
	 * @param hostingCompanyId the hosting company id
	 * @param roleId the role id
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyEmployees> findEmployeesByRole(Long hostingCompanyId, Long roleId) {
	 	String hql = "select o.hostingCompanyEmployees from HostingCompanyDepartmentsEmployees o "
	 			+ " where o.hostingCompanyDepartments.hostingCompany.id = :hostingCompanyId "
	 			+ " and o.role.id = :roleId "
	 			+ " order by o.role desc ";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyId", hostingCompanyId);
	    parameters.put("roleId", roleId);
		return (List<HostingCompanyEmployees>)super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<HostingCompanyEmployees> findEmployeeByRole(Long hostingCompanyId, Long roleId) {
	 	String hql = "select o from HostingCompanyEmployees o "
	 			+ " where o.hostingCompany.id = :hostingCompanyId "
	 			+ " and o.jobTitle.roles.id = :roleId "
	 			+ " order by o.jobTitle.roles desc ";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyId", hostingCompanyId);
	    parameters.put("roleId", roleId);
		return (List<HostingCompanyEmployees>)super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<HostingCompanyDepartmentsEmployees> findDepartmentsByUser(Long userId) {
	 	String hql = "select o from HostingCompanyDepartmentsEmployees o "
	 			+ " where o.hostingCompanyEmployees.users.id = :userId "
	 			+ " order by o.role desc ";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("userId", userId);
		return (List<HostingCompanyDepartmentsEmployees>)super.getList(hql, parameters);
	}

	/**
	 * Find all roless for managers.
	 *
	 * @param hostingCompanyId the hosting company id
	 * @param userId the user id
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List<Roles> findAllRolessForManagers(Long hostingCompanyId, Long userId) {
	 	String hql = "select o.role from HostingCompanyDepartmentsEmployees o "
	 			+ " where o.hostingCompanyDepartments.hostingCompany.id = :hostingCompanyId "
	 			+ " and o.hostingCompanyEmployees.users.id = :userId "
	 			+ " and o.role is not null "
	 			+ " order by o.role desc ";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyId", hostingCompanyId);
	    parameters.put("userId", userId);
		return (List<Roles>)super.getList(hql, parameters);
	}

	public HostingCompanyDepartmentsChatEmployees findByDeptAndHostingCompanyChatEmployees(Long hostingCompanyDepartmentsId, Long hostingCompanyEmployeesId ) throws Exception {
	 	String hql = "select o from HostingCompanyDepartmentsChatEmployees o where o.hostingCompanyDepartments.id = :hostingCompanyDepartmentsId  and o.hostingCompanyEmployees.id = :hostingCompanyEmployeesId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyDepartmentsId", hostingCompanyDepartmentsId);
	    parameters.put("hostingCompanyEmployeesId", hostingCompanyEmployeesId);
		return (HostingCompanyDepartmentsChatEmployees)super.getUniqueResult(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<HostingCompanyEmployees> findAllNotUsedChatEmployees(Long hostingCompanyId, Long departmentId) {
	 	String hql = "select o from HostingCompanyEmployees o "
	 			+ " where o.hostingCompany.id = :hostingCompanyId "
	 			+ " and o.id not in (select x.hostingCompanyEmployees.id from HostingCompanyDepartmentsChatEmployees x where x.id = :departmentId )";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyId", hostingCompanyId);
	    parameters.put("departmentId", departmentId);
		return (List<HostingCompanyEmployees>)super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<HostingCompanyDepartmentsChatEmployees> findByChatmEmpDepartment(Long id) throws Exception {
	 	String hql = "select o from HostingCompanyDepartmentsChatEmployees o where o.hostingCompanyDepartments.id = :id" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (List<HostingCompanyDepartmentsChatEmployees>)super.getList(hql, parameters);
	}

}

