package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.UsersTrainingProvider;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class UsersTrainingProviderDAO.
 */
public class UsersTrainingProviderDAO extends AbstractDAO {

	/** The Constant leftJoins. */
	private static final String leftJoins = " " + "left join fetch o.user left join fetch o.company" + " ";

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all UsersTrainingProvider.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.UsersTrainingProvider}
	 * @throws Exception             global exception
	 * @see UsersTrainingProvider
	 */
	@SuppressWarnings("unchecked")
	public List<UsersTrainingProvider> allUsersTrainingProvider() throws Exception {
		return (List<UsersTrainingProvider>) super.getList("select o from UsersTrainingProvider o" + leftJoins);
	}

	/**
	 * Get all UsersTrainingProvider between from and noRows.
	 *
	 * @author TechFinium
	 * @param from            the from
	 * @param noRows            the no rows
	 * @return a list of {@link haj.com.entity.UsersTrainingProvider}
	 * @throws Exception             global exception
	 * @see UsersTrainingProvider
	 */
	@SuppressWarnings("unchecked")
	public List<UsersTrainingProvider> allUsersTrainingProvider(Long from, int noRows) throws Exception {
		String hql = "select o from UsersTrainingProvider o" + leftJoins;
		Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<UsersTrainingProvider>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id            the id
	 * @return a {@link haj.com.entity.UsersTrainingProvider}
	 * @throws Exception             global exception
	 * @see UsersTrainingProvider
	 */
	public UsersTrainingProvider findByKey(Long id) throws Exception {
		String hql = "select o from UsersTrainingProvider o" + leftJoins + "where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (UsersTrainingProvider) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find by user company.
	 *
	 * @param userId the user id
	 * @param companyId the company id
	 * @return the users training provider
	 * @throws Exception the exception
	 */
	public UsersTrainingProvider findByUserCompany(Long userId, Long companyId) throws Exception {
		String hql = "select o from UsersTrainingProvider o" + leftJoins + "where o.user.id = :userId and o.company.id = :companyId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("userId", userId);
		parameters.put("companyId", companyId);
		return (UsersTrainingProvider) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find UsersTrainingProvider by description.
	 *
	 * @author TechFinium
	 * @param description            the description
	 * @return a list of {@link haj.com.entity.UsersTrainingProvider}
	 * @throws Exception             global exception
	 * @see UsersTrainingProvider
	 */
	@SuppressWarnings("unchecked")
	public List<UsersTrainingProvider> findByName(String description) throws Exception {
		String hql = "select o from UsersTrainingProvider o" + leftJoins + "where o.trainingDescription like  :description or o.trainingDurationDescription like  :description";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<UsersTrainingProvider>) super.getList(hql, parameters);
	}
}
