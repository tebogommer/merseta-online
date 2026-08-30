package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.SelfcaringRating;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class SelfcaringRatingDAO.
 */
public class SelfcaringRatingDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SelfcaringRating.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.SelfcaringRating}
	 * @throws Exception global exception
	 * @see    SelfcaringRating
	 */
	@SuppressWarnings("unchecked")
	public List<SelfcaringRating> allSelfcaringRating() throws Exception {
		return (List<SelfcaringRating>)super.getList("select o from SelfcaringRating o");
	}

	/**
	 * Get all SelfcaringRating between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.SelfcaringRating}
	 * @throws Exception global exception
	 * @see    SelfcaringRating
	 */
	@SuppressWarnings("unchecked")
	public List<SelfcaringRating> allSelfcaringRating(Long from, int noRows) throws Exception {
	 	String hql = "select o from SelfcaringRating o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SelfcaringRating>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SelfcaringRating}
	 * @throws Exception global exception
	 * @see    SelfcaringRating
	 */
	public SelfcaringRating findByKey(Long id) throws Exception {
	 	String hql = "select o from SelfcaringRating o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SelfcaringRating)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SelfcaringRating by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.SelfcaringRating}
	 * @throws Exception global exception
	 * @see    SelfcaringRating
	 */
	@SuppressWarnings("unchecked")
	public List<SelfcaringRating> findByName(String description) throws Exception {
	 	String hql = "select o from SelfcaringRating o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SelfcaringRating>)super.getList(hql, parameters);
	}
	
	/**
	 * Find object by  code.
	 *
	 * @author TechFinium
	 * @param selfcaringRating the selfcaring rating
	 * @return a {@link haj.com.entity.SelfcaringRating}
	 * @throws Exception global exception
	 * @see    SelfcaringRating
	 */
	public SelfcaringRating findUniqueCode(SelfcaringRating selfcaringRating) throws Exception {
	 	String hql = "select o from SelfcaringRating o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (selfcaringRating.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", selfcaringRating.getId());
	 	}
	   
	    parameters.put("code", selfcaringRating.getCode());
		return (SelfcaringRating)super.getUniqueResult(hql, parameters);
	}
}

