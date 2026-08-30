package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.Region;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class RegionDAO.
 */
public class RegionDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Region.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Region}
	 * @throws Exception global exception
	 * @see    Region
	 */
	@SuppressWarnings("unchecked")
	public List<Region> allRegion() throws Exception {
		return (List<Region>)super.getList("select o from Region o");
	}

	/**
	 * Get all Region between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.Region}
	 * @throws Exception global exception
	 * @see    Region
	 */
	@SuppressWarnings("unchecked")
	public List<Region> allRegion(Long from, int noRows) throws Exception {
	 	String hql = "select o from Region o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Region>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Region}
	 * @throws Exception global exception
	 * @see    Region
	 */
	public Region findByKey(Long id) throws Exception {
	 	String hql = "select o from Region o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Region)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Region by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.Region}
	 * @throws Exception global exception
	 * @see    Region
	 */
	@SuppressWarnings("unchecked")
	public List<Region> findByName(String description) throws Exception {
	 	String hql = "select o from Region o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Region>)super.getList(hql, parameters);
	}
}

