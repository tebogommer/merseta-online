package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.ImpactOfStaffTrainingDAO;
import haj.com.entity.ImpactOfStaffTraining;
import haj.com.entity.Wsp;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class ImpactOfStaffTrainingService.
 */
public class ImpactOfStaffTrainingService extends AbstractService {
	/** The dao. */
	private ImpactOfStaffTrainingDAO dao = new ImpactOfStaffTrainingDAO();

	/**
	 * Get all ImpactOfStaffTraining.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.ImpactOfStaffTraining}
	 * @throws Exception             the exception
	 * @see ImpactOfStaffTraining
	 */
	public List<ImpactOfStaffTraining> allImpactOfStaffTraining() throws Exception {
		return dao.allImpactOfStaffTraining();
	}

	/**
	 * Create or update ImpactOfStaffTraining.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ImpactOfStaffTraining
	 */
	public void create(ImpactOfStaffTraining entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update ImpactOfStaffTraining.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ImpactOfStaffTraining
	 */
	public void update(ImpactOfStaffTraining entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete ImpactOfStaffTraining.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ImpactOfStaffTraining
	 */
	public void delete(ImpactOfStaffTraining entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.ImpactOfStaffTraining}
	 * @throws Exception
	 *             the exception
	 * @see ImpactOfStaffTraining
	 */
	public ImpactOfStaffTraining findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find ImpactOfStaffTraining by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.ImpactOfStaffTraining}
	 * @throws Exception
	 *             the exception
	 * @see ImpactOfStaffTraining
	 */
	public List<ImpactOfStaffTraining> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load ImpactOfStaffTraining.
	 *
	 * @param first            from key
	 * @param pageSize            no of rows to fetch
	 * @return a list of {@link haj.com.entity.ImpactOfStaffTraining}
	 * @throws Exception             the exception
	 */
	public List<ImpactOfStaffTraining> allImpactOfStaffTraining(int first, int pageSize) throws Exception {
		return dao.allImpactOfStaffTraining(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of ImpactOfStaffTraining for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the ImpactOfStaffTraining
	 * @throws Exception             the exception
	 */
	public Long count() throws Exception {
		return dao.count(ImpactOfStaffTraining.class);
	}

	/**
	 * Lazy load ImpactOfStaffTraining with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1            ImpactOfStaffTraining class
	 * @param first            the first
	 * @param pageSize            the page size
	 * @param sortField            the sort field
	 * @param sortOrder            the sort order
	 * @param filters            the filters
	 * @return a list of {@link haj.com.entity.ImpactOfStaffTraining} containing
	 *         data
	 * @throws Exception             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<ImpactOfStaffTraining> allImpactOfStaffTraining(Class<ImpactOfStaffTraining> class1, int first,
			int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<ImpactOfStaffTraining>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of ImpactOfStaffTraining for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity            ImpactOfStaffTraining class
	 * @param filters            the filters
	 * @return Number of rows in the ImpactOfStaffTraining entity
	 * @throws Exception             the exception
	 */
	public int count(Class<ImpactOfStaffTraining> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/**
	 * Find by WSP.
	 *
	 * @param wsp the wsp
	 * @return the impact of staff training
	 * @throws Exception the exception
	 */
	public ImpactOfStaffTraining findByWSP(Wsp wsp) throws Exception {
		return dao.findByWSP(wsp);
	}

	/**
	 * Find by WSP count.
	 *
	 * @param wsp the wsp
	 * @return the long
	 * @throws Exception the exception
	 */
	public long findByWSPCount(Wsp wsp) throws Exception {
		return dao.findByWSPCount(wsp);
	}
}
