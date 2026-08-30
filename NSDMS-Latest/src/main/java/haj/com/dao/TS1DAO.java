package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.datatakeon.TS1;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class TS1DAO.
 */
public class TS1DAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all TS1.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.datatakeon.TS1}
	 * @throws Exception global exception
	 * @see    TS1
	 */
	@SuppressWarnings("unchecked")
	public List<TS1> allTS1() throws Exception {
		return (List<TS1>)super.getList("select o from TS1 o");
	}

	/**
	 * Get all TS1 between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.datatakeon.TS1}
	 * @throws Exception global exception
	 * @see    TS1
	 */
	@SuppressWarnings("unchecked")
	public List<TS1> allTS1(Long from, int noRows) throws Exception {
	 	String hql = "select o from TS1 o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<TS1>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.datatakeon.TS1}
	 * @throws Exception global exception
	 * @see    TS1
	 */
	public TS1 findByKey(Long id) throws Exception {
	 	String hql = "select o from TS1 o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (TS1)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find TS1 by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.datatakeon.TS1}
	 * @throws Exception global exception
	 * @see    TS1
	 */
	@SuppressWarnings("unchecked")
	public List<TS1> findByName(String description) throws Exception {
	 	String hql = "select o from TS1 o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<TS1>)super.getList(hql, parameters);
	}
}

