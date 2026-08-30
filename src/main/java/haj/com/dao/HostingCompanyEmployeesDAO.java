package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.HostingCompany;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.ProcessRoles;
import haj.com.entity.Users;
import haj.com.entity.enums.UsersStatusEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyEmployeesDAO.
 */
public class HostingCompanyEmployeesDAO extends AbstractDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all HostingCompanyEmployees.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.HostingCompanyEmployees}
	 * @throws Exception
	 *             global exception
	 * @see HostingCompanyEmployees
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyEmployees> allHostingCompanyEmployees() throws Exception {
		return (List<HostingCompanyEmployees>) super.getList("select o from HostingCompanyEmployees o");
	}

	/**
	 * Get all HostingCompanyEmployees between from and noRows.
	 *
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @return a list of {@link haj.com.entity.HostingCompanyEmployees}
	 * @throws Exception
	 *             global exception
	 * @see HostingCompanyEmployees
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyEmployees> allHostingCompanyEmployees(Long from, int noRows) throws Exception {
		String hql = "select o from HostingCompanyEmployees o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<HostingCompanyEmployees>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.HostingCompanyEmployees}
	 * @throws Exception
	 *             global exception
	 * @see HostingCompanyEmployees
	 */
	public HostingCompanyEmployees findByKey(Long id) throws Exception {
		String hql = "select o from HostingCompanyEmployees o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (HostingCompanyEmployees) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find by user return host company.
	 *
	 * @param userId
	 *            the user id
	 * @return the hosting company employees
	 * @throws Exception
	 *             the exception
	 */
	public HostingCompanyEmployees findByUserReturnHostCompany(Long userId) throws Exception {
		String hql = "select o from HostingCompanyEmployees o left join fetch o.hostingCompany left join fetch o.users where o.users.id = :userId and (o.users.admin = false or o.users.admin is null)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("userId", userId);
		return (HostingCompanyEmployees) super.getUniqueResult(hql, parameters);
	}
	

	@SuppressWarnings("unchecked")
	public List<Users> searchUser(String description) throws Exception {
		
		String hql = "select distinct o.users from HostingCompanyEmployees o "
					+ "where o.users.firstName like :description or "
					+ "o.users.lastName like :description "
					+ "order by o.users.firstName ";
		
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<Users>) super.getList(hql, parameters);
	}


	/**
	 * Find by user return host company B.
	 *
	 * @param userId
	 *            the user id
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyEmployees> findByUserReturnHostCompanyB(Long userId) throws Exception {
		String hql = "select o from HostingCompanyEmployees o left join fetch o.hostingCompany left join fetch o.users where o.users.id = :userId and (o.users.admin = false or o.users.admin is null)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("userId", userId);
		return (List<HostingCompanyEmployees>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<HostingCompanyEmployees> findByUserHostCompanyB(Long userId) throws Exception {
		String hql = "select o from HostingCompanyEmployees o left join fetch o.hostingCompany left join fetch o.users where o.users.id = :userId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("userId", userId);
		return (List<HostingCompanyEmployees>) super.getList(hql, parameters);
	}

	/**
	 * Find HostingCompanyEmployees by description.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a list of {@link haj.com.entity.HostingCompanyEmployees}
	 * @throws Exception
	 *             global exception
	 * @see HostingCompanyEmployees
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyEmployees> findByName(String description) throws Exception {
		String hql = "select o from HostingCompanyEmployees o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<HostingCompanyEmployees>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<HostingCompanyEmployees> searchEmployee(String description) throws Exception {
		String hql = "select o from HostingCompanyEmployees o where o.users.firstName like :description or o.users.lastName like :description order by o.users.firstName ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<HostingCompanyEmployees>) super.getList(hql, parameters);
	}

	public long findByUserCount(Long userId, Long hostingCompanyID) throws Exception {
		String hql = "select count(o) from HostingCompanyEmployees o where o.users.id = :userId and o.hostingCompany.id = :hostingCompanyID ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("userId", userId);
		parameters.put("hostingCompanyID", hostingCompanyID);
		return (long) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find hosting company employees not in role.
	 *
	 * @param processRoles
	 *            the process roles
	 * @param hostingCompany
	 *            the hosting company
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyEmployees> findHostingCompanyEmployeesNotInRole(ProcessRoles processRoles, HostingCompany hostingCompany) {
		String hql = "select o from HostingCompanyEmployees o left join fetch o.users where o.hostingCompany.id = :hostingCompanyId " + "and o.users.id not in (select h.users.id from UsersRole h where h.processRoles.id = :processRolesID)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("processRolesID", processRoles.getId());
		parameters.put("hostingCompanyId", hostingCompany.getId());
		return (List<HostingCompanyEmployees>) super.getList(hql, parameters);

	}

	/**
	 * Find hosting company employees in role.
	 *
	 * @param processRoles
	 *            the process roles
	 * @param hostingCompany
	 *            the hosting company
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyEmployees> findHostingCompanyEmployeesInRole(ProcessRoles processRoles, HostingCompany hostingCompany) {
		String hql = "select o from HostingCompanyEmployees o left join fetch o.users where o.hostingCompany.id = :hostingCompanyId " + "and o.users.id in (select h.users.id from UsersRole h where h.processRoles.id = :processRolesID)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("processRolesID", processRoles.getId());
		parameters.put("hostingCompanyId", hostingCompany.getId());
		return (List<HostingCompanyEmployees>) super.getList(hql, parameters);

	}
	
	/**
	 * Find User by Job Title.
	 *
	 * @param jt_id
	 *            the JobTitle ID
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List<Users> findUserByJobTitle(Long jt_id) {
		String hql = "select (o.users) from HostingCompanyEmployees o where o.jobTitle.id = :jobTitleId ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("jobTitleId", jt_id);
		return (List<Users>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> findUserByRegionAndRole(Long regionId, Long roleId) {
		Long newroleId = 8L;
		String hql = "select (o.users) from HostingCompanyEmployees o where o.jobTitle.roles.id = :roleId"
			//	+ "and o.jobTitle.region.id = :regionId "     //commented by vh
				+ " and o.users.status = :active ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
	//	parameters.put("regionId", regionId);
		parameters.put("roleId", roleId);
		parameters.put("active", UsersStatusEnum.Active);
		//System.out.println("findUserByRegionAndRole : region id : " + regionId + " - roleId : " + roleId + " - Query : " + hql);
		return (List<Users>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> findUserByRegion(Long regionId) {
		String hql = "select (o.users) from HostingCompanyEmployees o where " + " o.jobTitle.region.id = :regionId " + "and o.users.status = :active ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("regionId", regionId);
		parameters.put("active", UsersStatusEnum.Active);
		//System.out.println("findUserByRegionAndRole : region id : " + regionId + " - roleId : " + roleId + " - Query : " + hql);
		return (List<Users>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<HostingCompanyEmployees> findUsersForSarsReporting() {
		String hql = "select o " + 
			"from HostingCompanyEmployees o " + 
			"where o.hostingCompany.id = 1 " + 
			"and o.jobTitle.roles.id in (4,5) " + 
			"and (o.jobTitle.description like 'Chief%' or o.jobTitle.description like '%Financial%')";
		return (List<HostingCompanyEmployees>) super.getList(hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> locateAllActiveCurrentCloUsers() throws Exception {
		String hql = "select (o.users) from HostingCompanyEmployees o "
				+ "where o.users.status = :active " 
				+ "and o.id in (select x.clo.id from RegionTown x )";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("active", UsersStatusEnum.Active);
		return (List<Users>) super.getList(hql, parameters);
	}
	
	public int countCloByUserId(Long userId) throws Exception {
		String hql = "select count(o.users) from HostingCompanyEmployees o "
				+ "where o.users.status = :active and o.users.id = :userID " 
				+ "and o.id in (select x.clo.id from RegionTown x )";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("active", UsersStatusEnum.Active);
		parameters.put("userID", userId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> locateAllActiveCurrentCrmUsers() throws Exception {
		String hql = "select (o.users) from HostingCompanyEmployees o "
				+ "where o.users.status = :active " 
				+ "and o.id in (select x.crm.id from RegionTown x )";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("active", UsersStatusEnum.Active);
		return (List<Users>) super.getList(hql, parameters);
	}
	
	public int countCrmByUserId(Long userId) throws Exception {
		String hql = "select count(o.users) from HostingCompanyEmployees o "
				+ "where o.users.status = :active and o.users.id = :userID " 
				+ "and o.id in (select x.crm.id from RegionTown x )";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("active", UsersStatusEnum.Active);
		parameters.put("userID", userId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> findByAllActiveUsersByRole(Long roleId) throws Exception {
		String hql = "select (o.users) from HostingCompanyEmployees o "
				+ "where o.users.status = :active and o.active = :activeBoolean " 
				+ "and o.jobTitle.roles.id = :roleId ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("active", UsersStatusEnum.Active);
		parameters.put("activeBoolean", true);
		parameters.put("roleId", roleId);
		return (List<Users>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> findByAllActiveUsersByJobTitleId(Long jobTitleId) throws Exception {
		String hql = "select (o.users) from HostingCompanyEmployees o "
				+ "where o.users.status = :active and o.active = :activeBoolean " 
				+ "and o.jobTitle.id = :jobTitleId ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("active", UsersStatusEnum.Active);
		parameters.put("activeBoolean", true);
		parameters.put("jobTitleId", jobTitleId);
		return (List<Users>) super.getList(hql, parameters);
	}
}
