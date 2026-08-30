package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.Glosssary;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class GlosssaryDAO.
 */
public class GlosssaryDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Glosssary.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Glosssary}
	 * @throws Exception global exception
	 * @see    Glosssary
	 */
	@SuppressWarnings("unchecked")
	public List<Glosssary> allGlosssary() throws Exception {
		return (List<Glosssary>)super.getList("select o from Glosssary o");
	}

	/**
	 * Get all Glosssary between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.Glosssary}
	 * @throws Exception global exception
	 * @see    Glosssary
	 */
	@SuppressWarnings("unchecked")
	public List<Glosssary> allGlosssary(Long from, int noRows) throws Exception {
	 	String hql = "select o from Glosssary o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Glosssary>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Glosssary}
	 * @throws Exception global exception
	 * @see    Glosssary
	 */
	public Glosssary findByKey(Long id) throws Exception {
	 	String hql = "select o from Glosssary o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Glosssary)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Glosssary by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.Glosssary}
	 * @throws Exception global exception
	 * @see    Glosssary
	 */
	@SuppressWarnings("unchecked")
	public List<Glosssary> findByName(String description) throws Exception {
	 	String hql = "select o from Glosssary o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Glosssary>)super.getList(hql, parameters);
	}
}

