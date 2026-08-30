package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Address;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class AddressDAO.
 */
public class AddressDAO extends AbstractDAO {
	
	private static final String leftJoins = " " 
			+ "left join fetch o.municipality h left join fetch o.town"
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
	 * Get all Address.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Address}
	 * @throws Exception
	 *             the exception
	 * @see Address
	 */
	@SuppressWarnings("unchecked")
	public List<Address> allAddress() throws Exception {
		return (List<Address>) super.getList("select o from Address o ");
	}

	/**
	 * All address.
	 *
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @return a list of {@link haj.com.entity.Address}
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Address> allAddress(Long from, int noRows) throws Exception {
		String hql = "select o from Address o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<Address>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.Address}
	 * @throws Exception
	 *             the exception
	 * @see Address
	 */
	public Address findByKey(Long id) throws Exception {
		String hql = "select o from Address o "+leftJoins+" where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (Address) super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.Address}
	 * @throws Exception
	 *             the exception
	 * @see Address
	 */
	public Address findByKeyJoinDistric(Long id) throws Exception {
		String hql = "select o from Address o left join fetch o.municipality m left join fetch o.town left join fetch m.district d  where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (Address) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find by hosting company id.
	 *
	 * @param id the id
	 * @return the address
	 * @throws Exception the exception
	 */
	public Address findByHostingCompanyId(long id) throws Exception {
		String hql = "select o from Address o  left join fetch o.hostingCompany left join fetch o.municipality h where o.hostingCompany.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (Address) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Address by description.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a list of {@link haj.com.entity.Address}
	 * @throws Exception
	 *             the exception
	 * @see Address
	 */
	@SuppressWarnings("unchecked")
	public List<Address> findByName(String description) throws Exception {
		String hql = "select o from Address o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<Address>) super.getList(hql, parameters);
	}

	/**
	 * Find by company.
	 *
	 * @param id
	 *            the id
	 * @return a list of {@link haj.com.entity.Address}
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Address> findByCompany(Long id) throws Exception {
		String hql = "select o from Address o where o.company.id = :id order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (List<Address>) super.getList(hql, parameters);
	}

	/**
	 * Find by user.
	 *
	 * @param id
	 *            the id
	 * @return a list of {@link haj.com.entity.Address}
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Address> findByUser(Long id) throws Exception {
		String hql = "select o from Address o where o.user.id = :id order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (List<Address>) super.getList(hql, parameters);
	}
}
