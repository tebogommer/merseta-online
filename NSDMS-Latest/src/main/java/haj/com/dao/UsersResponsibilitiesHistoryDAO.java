package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.CompanyUsers;
import haj.com.entity.UsersResponsibilities;
import haj.com.entity.UsersResponsibilitiesHistory;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class UsersResponsibilitiesHistoryDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all UsersResponsibilitiesHistory
	 * 
	 * @author TechFinium
	 * @see UsersResponsibilitiesHistory
	 * @return a list of {@link haj.com.entity.UsersResponsibilitiesHistory}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<UsersResponsibilitiesHistory> allUsersResponsibilitiesHistory() throws Exception {
		return (List<UsersResponsibilitiesHistory>) super.getList("select o from UsersResponsibilitiesHistory o");
	}

	/**
	 * Get all UsersResponsibilitiesHistory between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see UsersResponsibilitiesHistory
	 * @return a list of {@link haj.com.entity.UsersResponsibilitiesHistory}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<UsersResponsibilitiesHistory> allUsersResponsibilitiesHistory(Long from, int noRows) throws Exception {
		String hql = "select o from UsersResponsibilitiesHistory o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<UsersResponsibilitiesHistory>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see UsersResponsibilities
	 * @return a {@link haj.com.entity.UsersResponsibilitiesHistory}
	 * @throws Exception
	 *             global exception
	 */
	public UsersResponsibilitiesHistory findByKey(Long id) throws Exception {
		String hql = "select o from UsersResponsibilitiesHistory o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (UsersResponsibilitiesHistory) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find UsersResponsibilitiesHistory by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see UsersResponsibilitiesHistory
	 * @return a list of {@link haj.com.entity.UsersResponsibilitiesHistory}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<UsersResponsibilitiesHistory> findByName(String description) throws Exception {
		String hql = "select o from UsersResponsibilitiesHistory o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<UsersResponsibilitiesHistory>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<UsersResponsibilitiesHistory> findByCompanyUser(CompanyUsers companyUsers) throws Exception {
		String hql = "select o from UsersResponsibilitiesHistory o left join fetch o.userResponsibility where o.companyUsers.id = :companyUserId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyUserId", companyUsers.getId());
		return (List<UsersResponsibilitiesHistory>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<UsersResponsibilitiesHistory> findByCompanyUserID(Long id) throws Exception {
		String hql = "select o from UsersResponsibilitiesHistory o left join fetch o.userResponsibility where o.companyUsers.id = :companyUserId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyUserId", id);
		return (List<UsersResponsibilitiesHistory>) super.getList(hql, parameters);
	}
}
