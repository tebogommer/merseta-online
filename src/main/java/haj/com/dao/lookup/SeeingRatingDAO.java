package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.SeeingRating;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class SeeingRatingDAO.
 */
public class SeeingRatingDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SeeingRating.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.SeeingRating}
	 * @throws Exception global exception
	 * @see    SeeingRating
	 */
	@SuppressWarnings("unchecked")
	public List<SeeingRating> allSeeingRating() throws Exception {
		return (List<SeeingRating>)super.getList("select o from SeeingRating o");
	}

	/**
	 * Get all SeeingRating between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.SeeingRating}
	 * @throws Exception global exception
	 * @see    SeeingRating
	 */
	@SuppressWarnings("unchecked")
	public List<SeeingRating> allSeeingRating(Long from, int noRows) throws Exception {
	 	String hql = "select o from SeeingRating o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SeeingRating>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SeeingRating}
	 * @throws Exception global exception
	 * @see    SeeingRating
	 */
	public SeeingRating findByKey(Long id) throws Exception {
	 	String hql = "select o from SeeingRating o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SeeingRating)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SeeingRating by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.SeeingRating}
	 * @throws Exception global exception
	 * @see    SeeingRating
	 */
	@SuppressWarnings("unchecked")
	public List<SeeingRating> findByName(String description) throws Exception {
	 	String hql = "select o from SeeingRating o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SeeingRating>)super.getList(hql, parameters);
	}
	
	/**
	 * Find unique code.
	 *
	 * @param seeingRating the seeing rating
	 * @return the seeing rating
	 * @throws Exception the exception
	 */
	public SeeingRating findUniqueCode(SeeingRating seeingRating) throws Exception {
	 	String hql = "select o from SeeingRating o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (seeingRating.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", seeingRating.getId());
	 	}
	   
	    parameters.put("code", seeingRating.getCode());
		return (SeeingRating)super.getUniqueResult(hql, parameters);
	}
}

