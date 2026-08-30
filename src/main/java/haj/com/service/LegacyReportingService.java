package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.LegacyReportingDAO;
import haj.com.entity.LegacyReporting;
import haj.com.entity.LegacyReportingParams;
import haj.com.framework.AbstractService;

public class LegacyReportingService extends AbstractService {
	/** The dao. */
	private LegacyReportingDAO dao = new LegacyReportingDAO();

	/**
	 * Get all LegacyReporting
	 * 
	 * @author TechFinium
	 * @see LegacyReporting
	 * @return a list of {@link haj.com.entity.LegacyReporting}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyReporting> allLegacyReporting() throws Exception {
		return dao.allLegacyReporting();
	}

	/**
	 * Create or update LegacyReporting.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyReporting
	 */
	public void create(LegacyReporting entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update LegacyReporting.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyReporting
	 */
	public void update(LegacyReporting entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyReporting.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyReporting
	 */
	public void delete(LegacyReporting entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacyReporting}
	 * @throws Exception
	 *             the exception
	 * @see LegacyReporting
	 */
	public LegacyReporting findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyReporting by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacyReporting}
	 * @throws Exception
	 *             the exception
	 * @see LegacyReporting
	 */
	public List<LegacyReporting> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyReporting
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyReporting}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyReporting> allLegacyReporting(int first, int pageSize) throws Exception {
		return dao.allLegacyReporting(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyReporting for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyReporting
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyReporting.class);
	}

	/**
	 * Lazy load LegacyReporting with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacyReporting class
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
	 * @return a list of {@link haj.com.entity.LegacyReporting} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyReporting> allLegacyReporting(Class<LegacyReporting> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<LegacyReporting>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of LegacyReporting for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacyReporting class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacyReporting entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacyReporting> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public List<LegacyReporting> findByClassName(String className) throws Exception {
		return resolveParams(dao.findByClassName(className));
	}

	private List<LegacyReporting> resolveParams(List<LegacyReporting> legacyReportings) throws Exception {
		for (LegacyReporting legacyReporting : legacyReportings) {
			legacyReporting.setLegacyReportingParams(findParams(legacyReporting));
		}
		return legacyReportings;
	}

	public List<LegacyReportingParams> findParams(LegacyReporting legacyReporting) throws Exception {
		return dao.findParams(legacyReporting);
	}
}
