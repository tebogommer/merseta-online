package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.GenderDAO;
import haj.com.entity.lookup.Gender;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class GenderService.
 */
public class GenderService extends AbstractService {
	/** The dao. */
	private GenderDAO dao = new GenderDAO();

	/**
	 * Get all Gender.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.lookup.Gender}
	 * @throws Exception             the exception
	 * @see Gender
	 */
	public List<Gender> allGender() throws Exception {
		return dao.allGender();
	}

	/**
	 * Create or update Gender.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Gender
	 */
	public void create(Gender entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update Gender.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Gender
	 */
	public void update(Gender entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete Gender.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Gender
	 */
	public void delete(Gender entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.lookup.Gender}
	 * @throws Exception
	 *             the exception
	 * @see Gender
	 */
	public Gender findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find Gender by GenderName.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.lookup.Gender}
	 * @throws Exception
	 *             the exception
	 * @see Gender
	 */
	public List<Gender> findByGenderName(String desc) throws Exception {
		return dao.findByGenderName(desc);
	}
	
	
	public Gender findByGenderNameOneResult(String desc) throws Exception {
		List<Gender> results = dao.findByGenderName(desc);
		if (results.isEmpty()) {
			return null;
		} else {
			return results.get(0);
		}
	}

	/**
	 * Lazy load Gender.
	 *
	 * @param first            from key
	 * @param pageSize            no of rows to fetch
	 * @return a list of {@link haj.com.entity.lookup.Gender}
	 * @throws Exception             the exception
	 */
	public List<Gender> allGender(int first, int pageSize) throws Exception {
		return dao.allGender(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of Gender for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Gender
	 * @throws Exception             the exception
	 */
	public Long count() throws Exception {
		return dao.count(Gender.class);
	}

	/**
	 * Lazy load Gender with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1            Gender class
	 * @param first            the first
	 * @param pageSize            the page size
	 * @param sortField            the sort field
	 * @param sortOrder            the sort order
	 * @param filters            the filters
	 * @return a list of {@link haj.com.entity.lookup.Gender} containing data
	 * @throws Exception             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Gender> allGender(Class<Gender> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<Gender>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of Gender for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity            Gender class
	 * @param filters            the filters
	 * @return Number of rows in the Gender entity
	 * @throws Exception             the exception
	 */
	public int count(Class<Gender> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the gender
	 * @throws Exception the exception
	 */
	public Gender findByCode(String code) throws Exception { 
		return dao.findByCode(code);
	}
}
