package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.TrainingImpactRating;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class TrainingImpactRatingDAO.
 */
public class TrainingImpactRatingDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all TrainingImpactRating.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.TrainingImpactRating}
	 * @throws Exception global exception
	 * @see    TrainingImpactRating
	 */
	@SuppressWarnings("unchecked")
	public List<TrainingImpactRating> allTrainingImpactRating() throws Exception {
		return (List<TrainingImpactRating>)super.getList("select o from TrainingImpactRating o");
	}

	/**
	 * Get all TrainingImpactRating between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.TrainingImpactRating}
	 * @throws Exception global exception
	 * @see    TrainingImpactRating
	 */
	@SuppressWarnings("unchecked")
	public List<TrainingImpactRating> allTrainingImpactRating(Long from, int noRows) throws Exception {
	 	String hql = "select o from TrainingImpactRating o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<TrainingImpactRating>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.TrainingImpactRating}
	 * @throws Exception global exception
	 * @see    TrainingImpactRating
	 */
	public TrainingImpactRating findByKey(Long id) throws Exception {
	 	String hql = "select o from TrainingImpactRating o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (TrainingImpactRating)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find TrainingImpactRating by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.TrainingImpactRating}
	 * @throws Exception global exception
	 * @see    TrainingImpactRating
	 */
	@SuppressWarnings("unchecked")
	public List<TrainingImpactRating> findByName(String description) throws Exception {
	 	String hql = "select o from TrainingImpactRating o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<TrainingImpactRating>)super.getList(hql, parameters);
	}
}

