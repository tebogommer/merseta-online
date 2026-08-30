package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.TrainingImpactRatingDAO;
import haj.com.entity.lookup.TrainingImpactRating;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class TrainingImpactRatingService.
 */
public class TrainingImpactRatingService extends AbstractService {
	/** The dao. */
	private TrainingImpactRatingDAO dao = new TrainingImpactRatingDAO();

	/**
	 * Get all TrainingImpactRating.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.TrainingImpactRating}
	 * @throws Exception the exception
	 * @see   TrainingImpactRating
	 */
	public List<TrainingImpactRating> allTrainingImpactRating() throws Exception {
	  	return dao.allTrainingImpactRating();
	}


	/**
	 * Create or update TrainingImpactRating.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     TrainingImpactRating
	 */
	public void create(TrainingImpactRating entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  TrainingImpactRating.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TrainingImpactRating
	 */
	public void update(TrainingImpactRating entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  TrainingImpactRating.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TrainingImpactRating
	 */
	public void delete(TrainingImpactRating entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.TrainingImpactRating}
	 * @throws Exception the exception
	 * @see    TrainingImpactRating
	 */
	public TrainingImpactRating findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find TrainingImpactRating by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.TrainingImpactRating}
	 * @throws Exception the exception
	 * @see    TrainingImpactRating
	 */
	public List<TrainingImpactRating> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load TrainingImpactRating.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.TrainingImpactRating}
	 * @throws Exception the exception
	 */
	public List<TrainingImpactRating> allTrainingImpactRating(int first, int pageSize) throws Exception {
		return dao.allTrainingImpactRating(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of TrainingImpactRating for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the TrainingImpactRating
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(TrainingImpactRating.class);
	}
	
    /**
     * Lazy load TrainingImpactRating with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 TrainingImpactRating class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.TrainingImpactRating} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<TrainingImpactRating> allTrainingImpactRating(Class<TrainingImpactRating> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<TrainingImpactRating>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of TrainingImpactRating for lazy load with filters.
     *
     * @author TechFinium
     * @param entity TrainingImpactRating class
     * @param filters the filters
     * @return Number of rows in the TrainingImpactRating entity
     * @throws Exception the exception
     */	
	public int count(Class<TrainingImpactRating> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
