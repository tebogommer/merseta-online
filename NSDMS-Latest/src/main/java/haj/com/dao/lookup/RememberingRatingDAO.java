package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.RememberingRating;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class RememberingRatingDAO.
 */
public class RememberingRatingDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all RememberingRating.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.RememberingRating}
	 * @throws Exception global exception
	 * @see    RememberingRating
	 */
	@SuppressWarnings("unchecked")
	public List<RememberingRating> allRememberingRating() throws Exception {
		return (List<RememberingRating>)super.getList("select o from RememberingRating o");
	}

	/**
	 * Get all RememberingRating between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.RememberingRating}
	 * @throws Exception global exception
	 * @see    RememberingRating
	 */
	@SuppressWarnings("unchecked")
	public List<RememberingRating> allRememberingRating(Long from, int noRows) throws Exception {
	 	String hql = "select o from RememberingRating o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<RememberingRating>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.RememberingRating}
	 * @throws Exception global exception
	 * @see    RememberingRating
	 */
	public RememberingRating findByKey(Long id) throws Exception {
	 	String hql = "select o from RememberingRating o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (RememberingRating)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find RememberingRating by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.RememberingRating}
	 * @throws Exception global exception
	 * @see    RememberingRating
	 */
	@SuppressWarnings("unchecked")
	public List<RememberingRating> findByName(String description) throws Exception {
	 	String hql = "select o from RememberingRating o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<RememberingRating>)super.getList(hql, parameters);
	}
	
	/**
	 * Find object by  code.
	 *
	 * @author TechFinium
	 * @param rememberingRating the remembering rating
	 * @return a {@link haj.com.entity.RememberingRating}
	 * @throws Exception global exception
	 * @see    RememberingRating
	 */
	public RememberingRating findUniqueCode(RememberingRating rememberingRating) throws Exception {
	 	String hql = "select o from RememberingRating o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (rememberingRating.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", rememberingRating.getId());
	 	}
	   
	    parameters.put("code", rememberingRating.getCode());
		return (RememberingRating)super.getUniqueResult(hql, parameters);
	}
}

