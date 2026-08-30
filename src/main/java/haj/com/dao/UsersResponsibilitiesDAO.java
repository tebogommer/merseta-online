package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.CompanyUsers;
import haj.com.entity.UsersResponsibilities;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class UsersResponsibilitiesDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all UsersResponsibilities
	 * 
	 * @author TechFinium
	 * @see UsersResponsibilities
	 * @return a list of {@link haj.com.entity.UsersResponsibilities}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<UsersResponsibilities> allUsersResponsibilities() throws Exception {
		return (List<UsersResponsibilities>) super.getList("select o from UsersResponsibilities o");
	}

	/**
	 * Get all UsersResponsibilities between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see UsersResponsibilities
	 * @return a list of {@link haj.com.entity.UsersResponsibilities}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<UsersResponsibilities> allUsersResponsibilities(Long from, int noRows) throws Exception {
		String hql = "select o from UsersResponsibilities o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<UsersResponsibilities>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see UsersResponsibilities
	 * @return a {@link haj.com.entity.UsersResponsibilities}
	 * @throws Exception
	 *             global exception
	 */
	public UsersResponsibilities findByKey(Long id) throws Exception {
		String hql = "select o from UsersResponsibilities o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (UsersResponsibilities) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find UsersResponsibilities by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see UsersResponsibilities
	 * @return a list of {@link haj.com.entity.UsersResponsibilities}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<UsersResponsibilities> findByName(String description) throws Exception {
		String hql = "select o from UsersResponsibilities o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<UsersResponsibilities>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<UsersResponsibilities> findByCompanyUser(CompanyUsers companyUsers) throws Exception {
		String hql = "select o from UsersResponsibilities o left join fetch o.userResponsibility where o.companyUsers.id = :companyUserId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyUserId", companyUsers.getId());
		return (List<UsersResponsibilities>) super.getList(hql, parameters);
	}
	
	
}
