package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.PreviousSchoolsDAO;
import haj.com.entity.lookup.LegacyBursary;
import haj.com.entity.lookup.PreviousSchools;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class PreviousSchoolsService extends AbstractService {
	/** The dao. */
	private PreviousSchoolsDAO dao = new PreviousSchoolsDAO();

	/**
	 * Get all PreviousSchools
	 * 
	 * @author TechFinium
	 * @see PreviousSchools
	 * @return a list of {@link haj.com.entity.PreviousSchools}
	 * @throws Exception the exception
	 */
	public List<PreviousSchools> allPreviousSchools() throws Exception {
		return dao.allPreviousSchools();
	}

	/**
	 * Create or update PreviousSchools.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see PreviousSchools
	 */
	public void create(PreviousSchools entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update PreviousSchools.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see PreviousSchools
	 */
	public void update(PreviousSchools entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete PreviousSchools.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see PreviousSchools
	 */
	public void delete(PreviousSchools entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.PreviousSchools}
	 * @throws Exception the exception
	 * @see PreviousSchools
	 */
	public PreviousSchools findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find PreviousSchools by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.PreviousSchools}
	 * @throws Exception the exception
	 * @see PreviousSchools
	 */
	public List<PreviousSchools> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load PreviousSchools
	 * 
	 * @param first    from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.PreviousSchools}
	 * @throws Exception the exception
	 */
	public List<PreviousSchools> allPreviousSchools(int first, int pageSize) throws Exception {
		return dao.allPreviousSchools(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of PreviousSchools for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the PreviousSchools
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
		return dao.count(PreviousSchools.class);
	}

	/**
	 * Lazy load PreviousSchools with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1    PreviousSchools class
	 * @param first     the first
	 * @param pageSize  the page size
	 * @param sortField the sort field
	 * @param sortOrder the sort order
	 * @param filters   the filters
	 * @return a list of {@link haj.com.entity.PreviousSchools} containing data
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<PreviousSchools> allPreviousSchools(Class<PreviousSchools> class1, int first, int pageSize,
			String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<PreviousSchools>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of PreviousSchools for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity  PreviousSchools class
	 * @param filters the filters
	 * @return Number of rows in the PreviousSchools entity
	 * @throws Exception the exception
	 */
	public int count(Class<PreviousSchools> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<PreviousSchools> allEntries = allPreviousSchools();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<PreviousSchools> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}
}
