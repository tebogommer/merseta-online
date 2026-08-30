package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.CommunicatingRating;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class CommunicatingRatingDAO.
 */
public class CommunicatingRatingDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all CommunicatingRating.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.CommunicatingRating}
	 * @throws Exception global exception
	 * @see    CommunicatingRating
	 */
	@SuppressWarnings("unchecked")
	public List<CommunicatingRating> allCommunicatingRating() throws Exception {
		return (List<CommunicatingRating>)super.getList("select o from CommunicatingRating o");
	}

	/**
	 * Get all CommunicatingRating between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.CommunicatingRating}
	 * @throws Exception global exception
	 * @see    CommunicatingRating
	 */
	@SuppressWarnings("unchecked")
	public List<CommunicatingRating> allCommunicatingRating(Long from, int noRows) throws Exception {
	 	String hql = "select o from CommunicatingRating o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<CommunicatingRating>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.CommunicatingRating}
	 * @throws Exception global exception
	 * @see    CommunicatingRating
	 */
	public CommunicatingRating findByKey(Long id) throws Exception {
	 	String hql = "select o from CommunicatingRating o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (CommunicatingRating)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find CommunicatingRating by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.CommunicatingRating}
	 * @throws Exception global exception
	 * @see    CommunicatingRating
	 */
	@SuppressWarnings("unchecked")
	public List<CommunicatingRating> findByName(String description) throws Exception {
	 	String hql = "select o from CommunicatingRating o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<CommunicatingRating>)super.getList(hql, parameters);
	}
	
	/**
	 * Find object by code.
	 *
	 * @author TechFinium
	 * @param communicatingRating the communicating rating
	 * @return a {@link haj.com.entity.CommunicatingRating}
	 * @throws Exception global exception
	 * @see    CommunicatingRating
	 */
	  public CommunicatingRating findUniqueCode(CommunicatingRating communicatingRating) throws Exception {
		 	String hql = "select o from CommunicatingRating o where o.code = :code " ;
		 	Map<String, Object> parameters = new Hashtable<String, Object>();
		 	if (communicatingRating.getId()!=null) {
		 		hql += " and o.id <> :id ";
		 		parameters.put("id", communicatingRating.getId());
		 	}
		   
		    parameters.put("code", communicatingRating.getCode());
			return (CommunicatingRating)super.getUniqueResult(hql, parameters);
		}
}

