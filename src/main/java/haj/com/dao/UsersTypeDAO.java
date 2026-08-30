package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.UsersType;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class UsersTypeDAO.
 */
public class UsersTypeDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all UsersType.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.UsersType}
	 * @throws Exception global exception
	 * @see    UsersType
	 */
	@SuppressWarnings("unchecked")
	public List<UsersType> allUsersType() throws Exception {
		return (List<UsersType>)super.getList("select o from UsersType o");
	}

	/**
	 * Get all UsersType between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.UsersType}
	 * @throws Exception global exception
	 * @see    UsersType
	 */
	@SuppressWarnings("unchecked")
	public List<UsersType> allUsersType(Long from, int noRows) throws Exception {
	 	String hql = "select o from UsersType o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<UsersType>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.UsersType}
	 * @throws Exception global exception
	 * @see    UsersType
	 */
	public UsersType findByKey(Long id) throws Exception {
	 	String hql = "select o from UsersType o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (UsersType)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find UsersType by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.UsersType}
	 * @throws Exception global exception
	 * @see    UsersType
	 */
	@SuppressWarnings("unchecked")
	public List<UsersType> findByName(String description) throws Exception {
	 	String hql = "select o from UsersType o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<UsersType>)super.getList(hql, parameters);
	}
}

