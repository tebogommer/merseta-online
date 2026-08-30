package haj.com.service.lookup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.DGYearLearningProgramsDAO;
import haj.com.entity.lookup.DGYear;
import haj.com.entity.lookup.DGYearLearningPrograms;
import haj.com.entity.lookup.InterventionType;
import haj.com.framework.AbstractService;

public class DGYearLearningProgramsService extends AbstractService {
	/** The dao. */
	private DGYearLearningProgramsDAO dao = new DGYearLearningProgramsDAO();

	/**
	 * Get all DGYearLearningPrograms
	 * 
	 * @author TechFinium
	 * @see DGYearLearningPrograms
	 * @return a list of {@link haj.com.entity.DGYearLearningPrograms}
	 * @throws Exception
	 *             the exception
	 */
	public List<DGYearLearningPrograms> allDGYearLearningPrograms() throws Exception {
		return dao.allDGYearLearningPrograms();
	}

	/**
	 * Create or update DGYearLearningPrograms.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see DGYearLearningPrograms
	 */
	public void create(DGYearLearningPrograms entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update DGYearLearningPrograms.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see DGYearLearningPrograms
	 */
	public void update(DGYearLearningPrograms entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete DGYearLearningPrograms.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see DGYearLearningPrograms
	 */
	public void delete(DGYearLearningPrograms entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.DGYearLearningPrograms}
	 * @throws Exception
	 *             the exception
	 * @see DGYearLearningPrograms
	 */
	public DGYearLearningPrograms findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find DGYearLearningPrograms by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.DGYearLearningPrograms}
	 * @throws Exception
	 *             the exception
	 * @see DGYearLearningPrograms
	 */
	public List<DGYearLearningPrograms> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	public List<DGYearLearningPrograms> findByDgYear(DGYear dgYear) throws Exception {
		return dao.findByDgYear(dgYear);
	}
	
	public List<InterventionType> findInterventionsByDgYear(DGYear dgYear) throws Exception {
		return dao.findInterventionsByDgYear(dgYear);
	}

	/**
	 * Lazy load DGYearLearningPrograms
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.DGYearLearningPrograms}
	 * @throws Exception
	 *             the exception
	 */
	public List<DGYearLearningPrograms> allDGYearLearningPrograms(int first, int pageSize) throws Exception {
		return dao.allDGYearLearningPrograms(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of DGYearLearningPrograms for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the DGYearLearningPrograms
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(DGYearLearningPrograms.class);
	}

	/**
	 * Lazy load DGYearLearningPrograms with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            DGYearLearningPrograms class
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
	 * @return a list of {@link haj.com.entity.DGYearLearningPrograms} containing
	 *         data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<DGYearLearningPrograms> allDGYearLearningPrograms(Class<DGYearLearningPrograms> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<DGYearLearningPrograms>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of DGYearLearningPrograms for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            DGYearLearningPrograms class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the DGYearLearningPrograms entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<DGYearLearningPrograms> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public List<DGYearLearningPrograms> allDGYearLearningPrograms(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from DGYearLearningPrograms o where o.dgYear.id = :dgYearID";
		if (filters == null) filters = new HashMap<String, Object>();
		return (List<DGYearLearningPrograms>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int count(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from DGYearLearningPrograms o where o.dgYear.id = :dgYearID";
		if (filters == null) filters = new HashMap<String, Object>();
		return dao.countWhere(filters, hql);
	}
}
