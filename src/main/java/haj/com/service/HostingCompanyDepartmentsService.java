package haj.com.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.primefaces.model.SortOrder;

import haj.com.dao.HostingCompanyDepartmentsDAO;
import haj.com.entity.HostingCompany;
import haj.com.entity.HostingCompanyDepartments;
import haj.com.entity.HostingCompanyDepartmentsChatEmployees;
import haj.com.entity.HostingCompanyDepartmentsEmployees;
import haj.com.entity.Users;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyDepartmentsService.
 */
public class HostingCompanyDepartmentsService extends AbstractService {
	/** The dao. */
	private HostingCompanyDepartmentsDAO dao = new HostingCompanyDepartmentsDAO();

	/** The hosting company department process service. */
	private HostingCompanyDepartmentProcessService hostingCompanyDepartmentProcessService = new HostingCompanyDepartmentProcessService();
	private HostingCompanyDepartmentsEmployeesService hostingCompanyDepartmentsEmployeesService = new HostingCompanyDepartmentsEmployeesService();

	/**
	 * Get all HostingCompanyDepartments.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.HostingCompanyDepartments}
	 * @throws Exception
	 *             the exception
	 * @see HostingCompanyDepartments
	 */
	public List<HostingCompanyDepartments> allHostingCompanyDepartments() throws Exception {
		return dao.allHostingCompanyDepartments();
	}

	/**
	 * Create or update HostingCompanyDepartments.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see HostingCompanyDepartments
	 */
	public void create(HostingCompanyDepartments entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else {
			if (entity.getParentDepartment() != null && entity.getId() == entity.getParentDepartment().getId()) throw new Exception("Department and Parent Department cant be the same");
			this.dao.update(entity);
		}
	}

	/**
	 * Creates the.
	 *
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 */
	public void create(IDataEntity entity) throws Exception {
		this.dao.create(entity);
	}

	/**
	 * Update HostingCompanyDepartments.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see HostingCompanyDepartments
	 */
	public void update(IDataEntity entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete HostingCompanyDepartments.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see HostingCompanyDepartments
	 */
	public void delete(IDataEntity entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.HostingCompanyDepartments}
	 * @throws Exception
	 *             the exception
	 * @see HostingCompanyDepartments
	 */
	public HostingCompanyDepartments findByKey(Long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find HostingCompanyDepartments by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.HostingCompanyDepartments}
	 * @throws Exception
	 *             the exception
	 * @see HostingCompanyDepartments
	 */
	public List<HostingCompanyDepartments> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load HostingCompanyDepartments.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.HostingCompanyDepartments}
	 * @throws Exception
	 *             the exception
	 */
	public List<HostingCompanyDepartments> allHostingCompanyDepartments(int first, int pageSize) throws Exception {
		return dao.allHostingCompanyDepartments(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of HostingCompanyDepartments for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the HostingCompanyDepartments
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(HostingCompanyDepartments.class);
	}

	/**
	 * Lazy load HostingCompanyDepartments with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            HostingCompanyDepartments class
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
	 * @return a list of {@link haj.com.entity.HostingCompanyDepartments} containing
	 *         data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyDepartments> allHostingCompanyDepartments(Class<HostingCompanyDepartments> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<HostingCompanyDepartments>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of HostingCompanyDepartments for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            HostingCompanyDepartments class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the HostingCompanyDepartments entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<HostingCompanyDepartments> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/**
	 * Find by hc.
	 *
	 * @param hostingCompany
	 *            the hosting company
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<HostingCompanyDepartments> findByHc(HostingCompany hostingCompany) throws Exception {
		return dao.findByHc(hostingCompany.getId());
	}

	/**
	 * Find users department.
	 *
	 * @param user
	 *            the user
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<HostingCompanyDepartments> findUsersDepartment(Users user) throws Exception {
		return resolveProcesses(dao.findUsersDepartment(user.getId()));
	}

	/**
	 * Resolve processes.
	 *
	 * @param departments
	 *            the departments
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	private List<HostingCompanyDepartments> resolveProcesses(List<HostingCompanyDepartments> departments) throws Exception {
		for (HostingCompanyDepartments dep : departments) {
			dep.setHostingCompanyDepartmentProcesses(hostingCompanyDepartmentProcessService.findByDepartment(dep));
		}
		return departments;
	}

	/**
	 * Find by hc root.
	 *
	 * @param hostingCompany
	 *            the hosting company
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<HostingCompanyDepartments> findByHcRoot(HostingCompany hostingCompany) throws Exception {
		return dao.findByHcRoot(hostingCompany.getId());
	}

	/**
	 * Find by parent.
	 *
	 * @param parentDepartments
	 *            the parent department
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<HostingCompanyDepartments> findByParent(HostingCompanyDepartments parentDepartments) throws Exception {
		return dao.findByParent(parentDepartments.getId());
	}

	public void createIfNotExist(HostingCompanyDepartmentsEmployees employee) throws Exception {
		if (hostingCompanyDepartmentsEmployeesService.findByDeptAndHostingCompanyEmployees(employee.getHostingCompanyDepartments(), employee.getHostingCompanyEmployees()) == null) {
			create(employee);
		}
	}

	public void createIfNotExist(HostingCompanyDepartmentsChatEmployees employee) throws Exception {
		if (hostingCompanyDepartmentsEmployeesService.findByDeptAndHostingCompanyChatEmployees(employee.getHostingCompanyDepartments(), employee.getHostingCompanyEmployees()) == null) {
			create(employee);
		}
	}

	public List<Users> findEmployessUnderneath(Users user) throws Exception {
		List<Users> users = new ArrayList<>();
		users.add(user);
		List<HostingCompanyDepartments> hostingCompanyDepartments = findUsersDepartment(user);
		for (HostingCompanyDepartments hostingCompanyDepartment : hostingCompanyDepartments) {
			findEmployessRecursive(hostingCompanyDepartment, users);
		}
		return users;
	}

	public void findEmployessRecursive(HostingCompanyDepartments rootNode, List<Users> users) throws Exception {
		Queue<HostingCompanyDepartments> queue = new LinkedList<>();
		queue.add(rootNode);
		while (!queue.isEmpty()) {
			HostingCompanyDepartments node = queue.remove();
			List<HostingCompanyDepartmentsEmployees> hostingCompanyDepartmentsEmployees = hostingCompanyDepartmentsEmployeesService.findByDepartment(node);
			for (HostingCompanyDepartmentsEmployees hostingCompanyDepartmentsEmployee : hostingCompanyDepartmentsEmployees) {
				if (!users.contains(hostingCompanyDepartmentsEmployee.getHostingCompanyEmployees().getUsers())) users.add(hostingCompanyDepartmentsEmployee.getHostingCompanyEmployees().getUsers());
			}
			List<HostingCompanyDepartments> children = findByParent(node);
			for (HostingCompanyDepartments hostingCompanyDepartments : children) {
				queue.add(hostingCompanyDepartments);
			}

		}
	}

}
