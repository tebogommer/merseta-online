package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.LearnerMonitoringSurveyDAO;
import haj.com.entity.WorkplaceMonitoring;
import haj.com.entity.enums.WorkplaceSurveyType;
import haj.com.entity.lookup.LearnerMonitoringSurvey;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class LearnerMonitoringSurveyService extends AbstractService {
	/** The dao. */
	private LearnerMonitoringSurveyDAO dao = new LearnerMonitoringSurveyDAO();

	/**
	 * Get all LearnerMonitoringSurvey
	 * 
	 * @author TechFinium
	 * @see LearnerMonitoringSurvey
	 * @return a list of {@link haj.com.entity.LearnerMonitoringSurvey}
	 * @throws Exception
	 *             the exception
	 */
	public List<LearnerMonitoringSurvey> allLearnerMonitoringSurvey() throws Exception {
		return dao.allLearnerMonitoringSurvey();
	}

	/**
	 * Create or update LearnerMonitoringSurvey.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LearnerMonitoringSurvey
	 */
	public void create(LearnerMonitoringSurvey entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update LearnerMonitoringSurvey.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LearnerMonitoringSurvey
	 */
	public void update(LearnerMonitoringSurvey entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LearnerMonitoringSurvey.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LearnerMonitoringSurvey
	 */
	public void delete(LearnerMonitoringSurvey entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LearnerMonitoringSurvey}
	 * @throws Exception
	 *             the exception
	 * @see LearnerMonitoringSurvey
	 */
	public LearnerMonitoringSurvey findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LearnerMonitoringSurvey by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LearnerMonitoringSurvey}
	 * @throws Exception
	 *             the exception
	 * @see LearnerMonitoringSurvey
	 */
	public List<LearnerMonitoringSurvey> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LearnerMonitoringSurvey
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LearnerMonitoringSurvey}
	 * @throws Exception
	 *             the exception
	 */
	public List<LearnerMonitoringSurvey> allLearnerMonitoringSurvey(int first, int pageSize) throws Exception {
		return dao.allLearnerMonitoringSurvey(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LearnerMonitoringSurvey for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LearnerMonitoringSurvey
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LearnerMonitoringSurvey.class);
	}

	@SuppressWarnings("unchecked")
	public List<LearnerMonitoringSurvey> allLearnerMonitoringSurvey(Class<LearnerMonitoringSurvey> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<LearnerMonitoringSurvey>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	public int count(Class<LearnerMonitoringSurvey> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	public List<LearnerMonitoringSurvey> findByWorkplaceMonitoring(WorkplaceMonitoring workplaceMonitoring, WorkplaceSurveyType workplaceSurveyType) throws Exception {
		return dao.findByWorkplaceMonitoring(workplaceMonitoring, workplaceSurveyType);
	}
	
	public List<LearnerMonitoringSurvey> findLookups() throws Exception {
		return dao.findLookups();
	}

	@SuppressWarnings("unchecked")
	public List<LearnerMonitoringSurvey> allMainLearnerMonitoringSurvey(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from LearnerMonitoringSurvey o where o.workplaceMonitoring is null";
		return (List<LearnerMonitoringSurvey>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);

	}

	public int countMain(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from LearnerMonitoringSurvey o where o.workplaceMonitoring is null";
		return dao.countWhere(filters, hql);
	}

}
