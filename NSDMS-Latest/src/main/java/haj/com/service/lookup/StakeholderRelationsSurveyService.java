package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.StakeholderRelationsSurveyDAO;
import haj.com.entity.Users;
import haj.com.entity.lookup.StakeholderRelations;
import haj.com.entity.lookup.StakeholderRelationsSurvey;
import haj.com.framework.AbstractService;
import haj.com.service.DgVerificationService;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class StakeholderRelationsSurveyService extends AbstractService {

	/** The dao. */
	private StakeholderRelationsSurveyDAO dao = new StakeholderRelationsSurveyDAO();

	/**
	 * Instance of the service level
	 */
	private static StakeholderRelationsSurveyService stakeholderRelationsSurveyService;

	public static synchronized StakeholderRelationsSurveyService instance() {
		if (stakeholderRelationsSurveyService == null) {
			stakeholderRelationsSurveyService = new StakeholderRelationsSurveyService();
		}
		return stakeholderRelationsSurveyService;
	}

	/**
	 * Get all StakeholderRelationsSurvey
	 * 
	 * @author TechFinium
	 * @see StakeholderRelationsSurvey
	 * @return a list of {@link haj.com.entity.StakeholderRelationsSurvey}
	 * @throws Exception
	 *             the exception
	 */
	public List<StakeholderRelationsSurvey> allStakeholderRelationsSurvey() throws Exception {
		return dao.allStakeholderRelationsSurvey();
	}

	/**
	 * Create or update StakeholderRelationsSurvey.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see StakeholderRelationsSurvey
	 */
	public void create(StakeholderRelationsSurvey entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update StakeholderRelationsSurvey.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see StakeholderRelationsSurvey
	 */
	public void update(StakeholderRelationsSurvey entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete StakeholderRelationsSurvey.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see StakeholderRelationsSurvey
	 */
	public void delete(StakeholderRelationsSurvey entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.StakeholderRelationsSurvey}
	 * @throws Exception
	 *             the exception
	 * @see StakeholderRelationsSurvey
	 */
	public StakeholderRelationsSurvey findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find StakeholderRelationsSurvey by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.StakeholderRelationsSurvey}
	 * @throws Exception
	 *             the exception
	 * @see StakeholderRelationsSurvey
	 */
	public List<StakeholderRelationsSurvey> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load StakeholderRelationsSurvey
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.StakeholderRelationsSurvey}
	 * @throws Exception
	 *             the exception
	 */
	public List<StakeholderRelationsSurvey> allStakeholderRelationsSurvey(int first, int pageSize) throws Exception {
		return dao.allStakeholderRelationsSurvey(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of StakeholderRelationsSurvey for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the StakeholderRelationsSurvey
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(StakeholderRelationsSurvey.class);
	}

	/**
	 * Lazy load StakeholderRelationsSurvey with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            StakeholderRelationsSurvey class
	 * @param first
	 *            the first
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @return a list of {@link haj.com.entity.StakeholderRelationsSurvey}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<StakeholderRelationsSurvey> allStakeholderRelationsSurvey(Class<StakeholderRelationsSurvey> class1,
			int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters)
			throws Exception {
		return (List<StakeholderRelationsSurvey>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder,
				filters);

	}

	/**
	 * Get count of StakeholderRelationsSurvey for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            StakeholderRelationsSurvey class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the StakeholderRelationsSurvey entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<StakeholderRelationsSurvey> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/**
	 * Locates list of StakeholderRelationsSurvey by StakeholderRelations id
	 * @see StakeholderRelations
	 * @param stakeholderRelations
	 * @return List<StakeholderRelationsSurvey
	 * @throws Exception
	 */
	public List<StakeholderRelationsSurvey> findByStakeholderRelations(StakeholderRelations stakeholderRelations) throws Exception{
		return dao.findByStakeholderRelations(stakeholderRelations.getId());
	}
	
	/**
	 * Locates list of StakeholderRelationsSurvey by StakeholderRelations id and user is not assigned
	 * @see StakeholderRelations
	 * @param stakeholderRelations
	 * @return List<StakeholderRelationsSurvey
	 * @throws Exception
	 */
	public List<StakeholderRelationsSurvey> findByStakeholderRelationsUserNotAssigned(StakeholderRelations stakeholderRelations) throws Exception{
		return dao.findByStakeholderRelationsUserNotAssigned(stakeholderRelations.getId());
	}
	
	/**
	 * Locates list of StakeholderRelationsSurvey by StakeholderRelations id and user id
	 * @see StakeholderRelations
	 * @see Users
	 * @param stakeholderRelations
	 * @param user
	 * @return List<StakeholderRelationsSurvey
	 * @throws Exception
	 */
	public List<StakeholderRelationsSurvey> findByStakeholderRelationsAndUser(StakeholderRelations stakeholderRelations, Users user) throws Exception{
		return dao.findByStakeholderRelationsAndUser(stakeholderRelations.getId(), user.getId());
	}

}
