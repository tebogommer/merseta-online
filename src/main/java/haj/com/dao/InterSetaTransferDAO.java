package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.InterSetaTransfer;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class InterSetaTransferDAO.
 */
public class InterSetaTransferDAO extends AbstractDAO {

	private static final String leftJoin = " " + "left join fetch o.company " + " ";

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
	 * Get all InterSetaTransfer.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.InterSetaTransfer}
	 * @throws Exception
	 *             global exception
	 * @see InterSetaTransfer
	 */
	@SuppressWarnings("unchecked")
	public List<InterSetaTransfer> allInterSetaTransfer() throws Exception {
		return (List<InterSetaTransfer>) super.getList("select o from InterSetaTransfer o");
	}

	/**
	 * Get all InterSetaTransfer between from and noRows.
	 *
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @return a list of {@link haj.com.entity.InterSetaTransfer}
	 * @throws Exception
	 *             global exception
	 * @see InterSetaTransfer
	 */
	@SuppressWarnings("unchecked")
	public List<InterSetaTransfer> allInterSetaTransfer(Long from, int noRows) throws Exception {
		String hql = "select o from InterSetaTransfer o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<InterSetaTransfer>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.InterSetaTransfer}
	 * @throws Exception
	 *             global exception
	 * @see InterSetaTransfer
	 */
	public InterSetaTransfer findByKey(Long id) throws Exception {
		String hql = "select o from InterSetaTransfer o left join fetch o.company c left join fetch o.users u where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (InterSetaTransfer) super.getUniqueResult(hql, parameters);
	}
	
	public InterSetaTransfer findByGuid(String id) throws Exception {
		String hql = "select o from InterSetaTransfer o left join fetch o.company c left join fetch o.users u where o.guid = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (InterSetaTransfer) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find InterSetaTransfer by description.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a list of {@link haj.com.entity.InterSetaTransfer}
	 * @throws Exception
	 *             global exception
	 * @see InterSetaTransfer
	 */
	@SuppressWarnings("unchecked")
	public List<InterSetaTransfer> findByName(String description) throws Exception {
		String hql = "select o from InterSetaTransfer o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<InterSetaTransfer>) super.getList(hql, parameters);
	}
}
