package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.DisabilityRating;

public class DisabilityRatingDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all DisabilityRating
 	 * @author TechFinium 
 	 * @see    DisabilityRating
 	 * @return a list of {@link haj.com.entity.DisabilityRating}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DisabilityRating> allDisabilityRating() throws Exception {
		return (List<DisabilityRating>)super.getList("select o from DisabilityRating o");
	}

	/**
	 * Get all DisabilityRating between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    DisabilityRating
 	 * @return a list of {@link haj.com.entity.DisabilityRating}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DisabilityRating> allDisabilityRating(Long from, int noRows) throws Exception {
	 	String hql = "select o from DisabilityRating o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<DisabilityRating>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    DisabilityRating
 	 * @return a {@link haj.com.entity.DisabilityRating}
 	 * @throws Exception global exception
 	 */
	public DisabilityRating findByKey(Long id) throws Exception {
	 	String hql = "select o from DisabilityRating o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (DisabilityRating)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find DisabilityRating by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    DisabilityRating
  	 * @return a list of {@link haj.com.entity.DisabilityRating}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DisabilityRating> findByName(String description) throws Exception {
	 	String hql = "select o from DisabilityRating o where o.description like  :description order by o.description " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<DisabilityRating>)super.getList(hql, parameters);
	}
	
	public List<DisabilityRating> findByDisability(Long id) throws Exception {
	 	String hql = "select o from DisabilityRating o where o.disabilityStatus.id = :id order by o.description " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id",id);
		return (List<DisabilityRating>)super.getList(hql, parameters);
	}
}

