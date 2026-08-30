package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.HostingCompanyDepartmentsEmployeesDAO;
import haj.com.entity.HostingCompany;
import haj.com.entity.HostingCompanyDepartments;
import haj.com.entity.HostingCompanyDepartmentsChatEmployees;
import haj.com.entity.HostingCompanyDepartmentsEmployees;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.Users;
import haj.com.entity.lookup.Roles;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyDepartmentsEmployeesService.
 */
public class HostingCompanyDepartmentsEmployeesService extends AbstractService {
	/** The dao. */
	private HostingCompanyDepartmentsEmployeesDAO dao = new HostingCompanyDepartmentsEmployeesDAO();

	/**
	 * Get all HostingCompanyDepartmentsEmployees.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.HostingCompanyDepartmentsEmployees}
	 * @throws Exception
	 *             the exception
	 * @see HostingCompanyDepartmentsEmployees
	 */
	public List<HostingCompanyDepartmentsEmployees> allHostingCompanyDepartmentsEmployees() throws Exception {
		return dao.allHostingCompanyDepartmentsEmployees();
	}

	/**
	 * Create or update HostingCompanyDepartmentsEmployees.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see HostingCompanyDepartmentsEmployees
	 */
	public void create(HostingCompanyDepartmentsEmployees entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update HostingCompanyDepartmentsEmployees.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see HostingCompanyDepartmentsEmployees
	 */
	public void update(HostingCompanyDepartmentsEmployees entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete HostingCompanyDepartmentsEmployees.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see HostingCompanyDepartmentsEmployees
	 */
	public void delete(HostingCompanyDepartmentsEmployees entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.HostingCompanyDepartmentsEmployees}
	 * @throws Exception
	 *             the exception
	 * @see HostingCompanyDepartmentsEmployees
	 */
	public HostingCompanyDepartmentsEmployees findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find HostingCompanyDepartmentsEmployees by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.HostingCompanyDepartmentsEmployees}
	 * @throws Exception
	 *             the exception
	 * @see HostingCompanyDepartmentsEmployees
	 */
	public List<HostingCompanyDepartmentsEmployees> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load HostingCompanyDepartmentsEmployees.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.HostingCompanyDepartmentsEmployees}
	 * @throws Exception
	 *             the exception
	 */
	public List<HostingCompanyDepartmentsEmployees> allHostingCompanyDepartmentsEmployees(int first, int pageSize) throws Exception {
		return dao.allHostingCompanyDepartmentsEmployees(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of HostingCompanyDepartmentsEmployees for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the HostingCompanyDepartmentsEmployees
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(HostingCompanyDepartmentsEmployees.class);
	}

	/**
	 * Lazy load HostingCompanyDepartmentsEmployees with pagination, filter,
	 * sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            HostingCompanyDepartmentsEmployees class
	 * @param first
	 *            the first
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @return a list of {@link haj.com.entity.HostingCompanyDepartmentsEmployees}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyDepartmentsEmployees> allHostingCompanyDepartmentsEmployees(Class<HostingCompanyDepartmentsEmployees> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<HostingCompanyDepartmentsEmployees>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of HostingCompanyDepartmentsEmployees for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            HostingCompanyDepartmentsEmployees class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the HostingCompanyDepartmentsEmployees entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<HostingCompanyDepartmentsEmployees> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/**
	 * Find by department.
	 *
	 * @param hostingCompanyDepartments
	 *            the hosting company departments
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<HostingCompanyDepartmentsEmployees> findByDepartment(HostingCompanyDepartments hostingCompanyDepartments) throws Exception {
		return dao.findByDepartment(hostingCompanyDepartments.getId());
	}

	/**
	 * Find all not used employees.
	 *
	 * @param hostingcompanydepartments
	 *            the hostingcompanydepartments
	 * @return the list
	 */
	public List<HostingCompanyEmployees> findAllNotUsedEmployees(HostingCompanyDepartments hostingcompanydepartments) {

		return dao.findAllNotUsedEmployees(hostingcompanydepartments.getHostingCompany().getId(), hostingcompanydepartments.getId());
	}

	/**
	 * Find all roless for managers.
	 *
	 * @param hostingCompany
	 *            the hosting company
	 * @param user
	 *            the user
	 * @return the list
	 */
	public List<Roles> findAllRolessForManagers(HostingCompany hostingCompany, Users user) {
		return dao.findAllRolessForManagers(hostingCompany.getId(), user.getId());
	}

	/**
	 * Find employees by role.
	 *
	 * @param hostingCompany
	 *            the hosting company
	 * @param role
	 *            the role
	 * @return the list
	 */
	public List<HostingCompanyEmployees> findEmployeesByRole(HostingCompany hostingCompany, Roles role) {
		return dao.findEmployeesByRole(hostingCompany.getId(), role.getId());
	}

	public List<HostingCompanyEmployees> findEmployeeByRole(HostingCompany hostingCompany, Roles role) {
		return dao.findEmployeeByRole(hostingCompany.getId(), role.getId());
	}

	public List<HostingCompanyDepartmentsEmployees> findDepartmentsByUser(Users user) {
		return dao.findDepartmentsByUser(user.getId());
	}

	public HostingCompanyDepartmentsEmployees findByDeptAndHostingCompanyEmployees(HostingCompanyDepartments hostingCompanyDepartments, HostingCompanyEmployees hostingCompanyEmployees) throws Exception {
		return dao.findByDeptAndHostingCompanyEmployees(hostingCompanyDepartments.getId(), hostingCompanyEmployees.getId());
	}

	public HostingCompanyDepartmentsChatEmployees findByDeptAndHostingCompanyChatEmployees(HostingCompanyDepartments hostingCompanyDepartments, HostingCompanyEmployees hostingCompanyEmployees) throws Exception {
		return dao.findByDeptAndHostingCompanyChatEmployees(hostingCompanyDepartments.getId(), hostingCompanyEmployees.getId());
	}

	public List<HostingCompanyEmployees> findAllNotUsedChatEmployees(HostingCompanyDepartments hostingcompanydepartments) {
		return dao.findAllNotUsedChatEmployees(hostingcompanydepartments.getHostingCompany().getId(), hostingcompanydepartments.getId());
	}

	public List<HostingCompanyDepartmentsChatEmployees> findByChatmEmpDepartment(HostingCompanyDepartments hostingCompanyDepartments) throws Exception {
		return dao.findByChatmEmpDepartment(hostingCompanyDepartments.getId());
	}
}
