package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import biweekly.parameter.Role;
import haj.com.dao.HostingCompanyEmployeesDAO;
import haj.com.entity.HostingCompany;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.ProcessRoles;
import haj.com.entity.Users;
import haj.com.entity.enums.IdPassportEnum;
import haj.com.entity.enums.UsersStatusEnum;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.Roles;
import haj.com.entity.lookup.Town;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.RegionTownService;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyEmployeesService.
 */
public class HostingCompanyEmployeesService extends AbstractService {
	/** The dao. */
	private HostingCompanyEmployeesDAO dao = new HostingCompanyEmployeesDAO();

	/**
	 * Get all HostingCompanyEmployees.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.HostingCompanyEmployees}
	 * @throws Exception             the exception
	 * @see HostingCompanyEmployees
	 */
	public List<HostingCompanyEmployees> allHostingCompanyEmployees() throws Exception {
		return dao.allHostingCompanyEmployees();
	}

	public Long findByUserCount(Long userId, Long hostingCompanyID) throws Exception {
		return dao.findByUserCount(userId, hostingCompanyID);
	}

	/**
	 * Create or update HostingCompanyEmployees.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see HostingCompanyEmployees
	 */
	public void create(HostingCompanyEmployees entity) throws Exception {
		if (entity.getId() == null) {
			entity.setActive(true);
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update HostingCompanyEmployees.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see HostingCompanyEmployees
	 */
	public void update(HostingCompanyEmployees entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete HostingCompanyEmployees.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see HostingCompanyEmployees
	 */
	public void delete(HostingCompanyEmployees entity) throws Exception {
		entity.setActive(false);
		this.dao.update(entity);
		// this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.HostingCompanyEmployees}
	 * @throws Exception
	 *             the exception
	 * @see HostingCompanyEmployees
	 */
	public HostingCompanyEmployees findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}
	
	/**
	 * Find by user return host company.
	 *
	 * @param userId the user id
	 * @return the hosting company employees
	 * @throws Exception the exception
	 */
	public HostingCompanyEmployees findByUserReturnHostCompany(Long userId) throws Exception {
		return dao.findByUserReturnHostCompany(userId);
	}
	
	/**
	 * Find by user return host company B.
	 *
	 * @param userId the user id
	 * @return the boolean
	 * @throws Exception the exception
	 */
	public Boolean findByUserReturnHostCompanyB(Long userId) throws Exception {
		return (dao.findByUserReturnHostCompanyB(userId).size() > 0);
	}
	
	public Boolean findByUserHostCompanyB(Long userId) throws Exception {
		return (dao.findByUserHostCompanyB(userId).size() > 0);
	}

	/**
	 * Find HostingCompanyEmployees by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.HostingCompanyEmployees}
	 * @throws Exception
	 *             the exception
	 * @see HostingCompanyEmployees
	 */
	public List<HostingCompanyEmployees> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	public List<HostingCompanyEmployees> searchEmployee(String description) throws Exception {
		return dao.searchEmployee(description);
	}

	/**
	 * Lazy load HostingCompanyEmployees.
	 *
	 * @param first            from key
	 * @param pageSize            no of rows to fetch
	 * @return a list of {@link haj.com.entity.HostingCompanyEmployees}
	 * @throws Exception             the exception
	 */
	public List<HostingCompanyEmployees> allHostingCompanyEmployees(int first, int pageSize) throws Exception {
		return dao.allHostingCompanyEmployees(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of HostingCompanyEmployees for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the HostingCompanyEmployees
	 * @throws Exception             the exception
	 */
	public Long count() throws Exception {
		return dao.count(HostingCompanyEmployees.class);
	}

	/**
	 * Lazy load HostingCompanyEmployees with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1            HostingCompanyEmployees class
	 * @param first            the first
	 * @param pageSize            the page size
	 * @param sortField            the sort field
	 * @param sortOrder            the sort order
	 * @param filters            the filters
	 * @return a list of {@link haj.com.entity.HostingCompanyEmployees}
	 *         containing data
	 * @throws Exception             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyEmployees> allHostingCompanyEmployees(Class<HostingCompanyEmployees> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from HostingCompanyEmployees o left join fetch o.hostingCompany left join fetch o.users";
		return (List<HostingCompanyEmployees>) dao.sortAndFilter(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> searchUser(String description) throws Exception {
		return dao.searchUser(description);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<HostingCompanyEmployees> allHostingCompanyEmployeesIgnoreCertainUsers(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from HostingCompanyEmployees o left join fetch o.hostingCompany left join fetch o.users where o.users.id <> 24 ";
		return (List<HostingCompanyEmployees>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllHostingCompanyEmployeesIgnoreCertainUsers(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from HostingCompanyEmployees o where o.users.id <> 24 ";
		return dao.countWhere(filters, hql);
	}
	
	
	/**
	 * Find hosting company employees not in role.
	 *
	 * @param processRoles the process roles
	 * @param hostingCompany the hosting company
	 * @return the list
	 */
	public List<HostingCompanyEmployees> findHostingCompanyEmployeesNotInRole(ProcessRoles processRoles, HostingCompany hostingCompany) {
		return dao.findHostingCompanyEmployeesNotInRole(processRoles, hostingCompany);
	}
	
	/**
	 * Find hosting company employees in role.
	 *
	 * @param processRoles the process roles
	 * @param hostingCompany the hosting company
	 * @return the list
	 */
	public List<HostingCompanyEmployees> findHostingCompanyEmployeesInRole(ProcessRoles processRoles, HostingCompany hostingCompany) {
		return dao.findHostingCompanyEmployeesInRole(processRoles, hostingCompany);
	}

	/**
	 * Get count of HostingCompanyEmployees for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity            HostingCompanyEmployees class
	 * @param filters            the filters
	 * @return Number of rows in the HostingCompanyEmployees entity
	 * @throws Exception             the exception
	 */
	public int count(Class<HostingCompanyEmployees> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	/**
	 * Find User by Job Title.
	 *
	 * @param JobTitle
	 *            the JobTitle
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List<Users> findUserByJobTitle(Long jt_id) {
		return dao.findUserByJobTitle(jt_id);
	}
	
	public void deactivateEmployee(HostingCompanyEmployees hcEmployee) throws Exception {
		UsersService usersService=new UsersService();
		Users user=hcEmployee.getUsers();
		user.setStatus(UsersStatusEnum.InActive);
		hcEmployee.setActive(false);
		create(hcEmployee);
		if(user.getRsaIDNumber() !=null)
		{
			user.setTempRasIdOrPassportNumber(user.getRsaIDNumber());
			user.setRsaIDNumber(null);
			
		}
		else if(user.getPassportNumber() !=null)
		{
			user.setTempRasIdOrPassportNumber(user.getPassportNumber());
			user.setPassportNumber(null);
		}
		usersService.update(user);
	}
	
	public void activateEmployee(HostingCompanyEmployees hcEmployee) throws Exception {
		UsersService usersService=new UsersService();
		if(hcEmployee.getUsers().getTempRasIdOrPassportNumber() !=null)
		{
			if(usersService.findByPassportNumber(hcEmployee.getUsers().getTempRasIdOrPassportNumber()) !=null)
			{
				throw new Exception("There is another user registered with this ID("+hcEmployee.getUsers().getTempRasIdOrPassportNumber()+"), please contact system admin to resolve this");
			}
		}
		Users user=hcEmployee.getUsers();
		user.setStatus(UsersStatusEnum.Active);
		hcEmployee.setActive(true);
		create(hcEmployee);
		if(user.getIdType()==IdPassportEnum.RsaId)
		{
			user.setRsaIDNumber(user.getTempRasIdOrPassportNumber());
			user.setTempRasIdOrPassportNumber(null);
			
		}
		else if(user.getIdType()==IdPassportEnum.Passport)
		{
			user.setPassportNumber(user.getTempRasIdOrPassportNumber());
			user.setTempRasIdOrPassportNumber(null);
		}
		else
		{
			if(user.getTempRasIdOrPassportNumber() !=null)
			{
				if(user.getTempRasIdOrPassportNumber().length()==13)
				{
					user.setRsaIDNumber(user.getTempRasIdOrPassportNumber());
					user.setTempRasIdOrPassportNumber(null);
				}
				else
				{
					user.setPassportNumber(user.getTempRasIdOrPassportNumber());
					user.setTempRasIdOrPassportNumber(null);
				}
			}
		}
		usersService.update(user);
	}
	
	public List<Users> locateHostingCompanyEmployeesByRegionAndRole(Town town, Roles roles) throws Exception{
		RegionTown rt = RegionTownService.instance().findByTown(town);
		return findUserByRegionAndRole(rt.getRegion(), roles);
	}
	
	public List<Users> findUserByRegionAndRole(Region region, Roles role) {
		return dao.findUserByRegionAndRole(region.getId(), role.getId());
	}
	
	public List<Users> locateHostingCompanyEmployeesByRegion(Town town) throws Exception{
		RegionTown rt = RegionTownService.instance().findByTown(town);
		return findUserByRegion(rt.getRegion());
	}
	
	public List<Users> findUserByRegion(Region region) {
		return dao.findUserByRegion(region.getId());
	}
	
	public List<Users> locateAllActiveCurrentCloUsers() throws Exception {
		return dao.locateAllActiveCurrentCloUsers();
	}
	
	public int countCloByUserId(Users user) throws Exception {
		return dao.countCloByUserId(user.getId());
	}
	
	public List<Users> locateAllActiveCurrentCrmUsers() throws Exception {
		return dao.locateAllActiveCurrentCrmUsers();
	}
	
	public int countCrmByUserId(Users user) throws Exception {
		return dao.countCrmByUserId(user.getId());
	}
	
	public List<Users> findByAllActiveUsersByRole(Long roleId) throws Exception {
		return dao.findByAllActiveUsersByRole(roleId);
	}
	
	public List<Users> findByAllActiveUsersByJobTitleId(Long jobTitleId) throws Exception {
		return dao.findByAllActiveUsersByJobTitleId(jobTitleId);
	}
}
