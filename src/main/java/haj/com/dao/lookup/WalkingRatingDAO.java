package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.WalkingRating;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class WalkingRatingDAO.
 */
public class WalkingRatingDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WalkingRating.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.WalkingRating}
	 * @throws Exception global exception
	 * @see    WalkingRating
	 */
	@SuppressWarnings("unchecked")
	public List<WalkingRating> allWalkingRating() throws Exception {
		return (List<WalkingRating>)super.getList("select o from WalkingRating o");
	}

	/**
	 * Get all WalkingRating between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.WalkingRating}
	 * @throws Exception global exception
	 * @see    WalkingRating
	 */
	@SuppressWarnings("unchecked")
	public List<WalkingRating> allWalkingRating(Long from, int noRows) throws Exception {
	 	String hql = "select o from WalkingRating o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<WalkingRating>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WalkingRating}
	 * @throws Exception global exception
	 * @see    WalkingRating
	 */
	public WalkingRating findByKey(Long id) throws Exception {
	 	String hql = "select o from WalkingRating o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (WalkingRating)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WalkingRating by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.WalkingRating}
	 * @throws Exception global exception
	 * @see    WalkingRating
	 */
	@SuppressWarnings("unchecked")
	public List<WalkingRating> findByName(String description) throws Exception {
	 	String hql = "select o from WalkingRating o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WalkingRating>)super.getList(hql, parameters);
	}
	

	/**
	 * Find WalkingRating by code.
	 *
	 * @author TechFinium
	 * @param walkingRating the walking rating
	 * @return a list of {@link haj.com.entity.WalkingRating}
	 * @throws Exception global exception
	 * @see    WalkingRating
	 */
	
    public WalkingRating findUniqueCode(WalkingRating walkingRating) throws Exception {
	 	String hql = "select o from WalkingRating o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (walkingRating.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", walkingRating.getId());
	 	}
	   
	    parameters.put("code", walkingRating.getCode());
		return (WalkingRating)super.getUniqueResult(hql, parameters);
	}
}

