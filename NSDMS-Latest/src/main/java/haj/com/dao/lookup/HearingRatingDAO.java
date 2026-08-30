package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.AbetBand;
import haj.com.entity.lookup.HearingRating;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class HearingRatingDAO.
 */
public class HearingRatingDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all HearingRating.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.HearingRating}
	 * @throws Exception global exception
	 * @see    HearingRating
	 */
	@SuppressWarnings("unchecked")
	public List<HearingRating> allHearingRating() throws Exception {
		return (List<HearingRating>)super.getList("select o from HearingRating o");
	}

	/**
	 * Get all HearingRating between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.HearingRating}
	 * @throws Exception global exception
	 * @see    HearingRating
	 */
	@SuppressWarnings("unchecked")
	public List<HearingRating> allHearingRating(Long from, int noRows) throws Exception {
	 	String hql = "select o from HearingRating o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<HearingRating>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.HearingRating}
	 * @throws Exception global exception
	 * @see    HearingRating
	 */
	public HearingRating findByKey(Long id) throws Exception {
	 	String hql = "select o from HearingRating o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (HearingRating)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find HearingRating by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.HearingRating}
	 * @throws Exception global exception
	 * @see    HearingRating
	 */
	@SuppressWarnings("unchecked")
	public List<HearingRating> findByName(String description) throws Exception {
	 	String hql = "select o from HearingRating o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<HearingRating>)super.getList(hql, parameters);
	}
	
	/**
	 * Find object by primary code.
	 *
	 * @author TechFinium
	 * @param hearingRating the hearing rating
	 * @return a {@link haj.com.entity.HearingRating}
	 * @throws Exception global exception
	 * @see    AbetBand
	 */
	public HearingRating findUniqueCode(HearingRating hearingRating) throws Exception {
	 	String hql = "select o from HearingRating o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (hearingRating.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", hearingRating.getId());
	 	}
	   
	    parameters.put("code", hearingRating.getCode());
		return (HearingRating)super.getUniqueResult(hql, parameters);
	}
}

