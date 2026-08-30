package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.AddressHistory;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class AddressHistoryDAO.
 */
public class AddressHistoryDAO extends AbstractDAO {
	
	private static final String leftJoins = " " 
			+ " left join fetch o.municipality mp left join fetch o.town town"
			+ " ";

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
	 * Get all AddressHistory.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.AddressHistory}
	 * @throws Exception
	 *             the exception
	 * @see AddressHistory
	 */
	@SuppressWarnings("unchecked")
	public List<AddressHistory> allAddressHistory() throws Exception {
		return (List<AddressHistory>) super.getList("select o from AddressHistory o ");
	}

	/**
	 * All AddressHistory.
	 *
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @return a list of {@link haj.com.entity.AddressHistory}
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<AddressHistory> allAddressHistory(Long from, int noRows) throws Exception {
		String hql = "select o from AddressHistory o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<AddressHistory>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.AddressHistory}
	 * @throws Exception
	 *             the exception
	 * @see AddressHistory
	 */
	public AddressHistory findByKey(Long id) throws Exception {
		String hql = "select o from AddressHistory o "+leftJoins+" where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (AddressHistory) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find by hosting company id.
	 *
	 * @param id the id
	 * @return the AddressHistory
	 * @throws Exception the exception
	 */
	public AddressHistory findByHostingCompanyId(long id) throws Exception {
		String hql = "select o from AddressHistory o  left join fetch o.hostingCompany left join fetch o.municipality h where o.hostingCompany.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (AddressHistory) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find AddressHistory by description.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a list of {@link haj.com.entity.AddressHistory}
	 * @throws Exception
	 *             the exception
	 * @see AddressHistory
	 */
	@SuppressWarnings("unchecked")
	public List<AddressHistory> findByName(String description) throws Exception {
		String hql = "select o from AddressHistory o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<AddressHistory>) super.getList(hql, parameters);
	}

	/**
	 * Find by company.
	 *
	 * @param id
	 *            the id
	 * @return a list of {@link haj.com.entity.AddressHistory}
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<AddressHistory> findByCompany(Long id) throws Exception {
		String hql = "select o from AddressHistory o where o.company.id = :id order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (List<AddressHistory>) super.getList(hql, parameters);
	}

	/**
	 * Find by user.
	 *
	 * @param id
	 *            the id
	 * @return a list of {@link haj.com.entity.AddressHistory}
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<AddressHistory> findByUser(Long id) throws Exception {
		String hql = "select o from AddressHistory o where o.user.id = :id order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (List<AddressHistory>) super.getList(hql, parameters);
	}
	
	/**
	 * Find by forAddress.
	 *
	 * @param forAddressID
	 *            the forAddressID
	 * @return a list of {@link haj.com.entity.AddressHistory}
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<AddressHistory> findByForAddress(Long forAddressID) throws Exception {
		String hql = "select o from AddressHistory o" +leftJoins+ "where o.forAddress.id = :forAddressID order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("forAddressID", forAddressID);
		return (List<AddressHistory>) super.getList(hql, parameters);
	}
	
	public Integer countByForAddress(Long forAddressID) throws Exception {
		String hql = "select count(o) from AddressHistory o where o.forAddress.id = :forAddressID order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("forAddressID", forAddressID);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
}
