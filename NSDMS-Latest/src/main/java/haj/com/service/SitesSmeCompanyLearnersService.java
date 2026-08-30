package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.SitesSmeCompanyLearnersDAO;
import haj.com.entity.SitesSmeCompanyLearners;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class SitesSmeCompanyLearnersService.
 */
public class SitesSmeCompanyLearnersService extends AbstractService {
	/** The dao. */
	private SitesSmeCompanyLearnersDAO dao = new SitesSmeCompanyLearnersDAO();

	/**
	 * Get all SitesSmeCompanyLearners.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.SitesSmeCompanyLearners}
	 * @throws Exception the exception
	 * @see SitesSmeCompanyLearners
	 */
	public List<SitesSmeCompanyLearners> allSitesSmeCompanyLearners() throws Exception {
		return dao.allSitesSmeCompanyLearners();
	}

	/**
	 * Create or update SitesSmeCompanyLearners.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see SitesSmeCompanyLearners
	 */
	public void create(SitesSmeCompanyLearners entity) throws Exception {
		if (entity.getId() == null) {
			entity.setCreateDate(new java.util.Date());
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update SitesSmeCompanyLearners.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see SitesSmeCompanyLearners
	 */
	public void update(SitesSmeCompanyLearners entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete SitesSmeCompanyLearners.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see SitesSmeCompanyLearners
	 */
	public void delete(SitesSmeCompanyLearners entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SitesSmeCompanyLearners}
	 * @throws Exception the exception
	 * @see SitesSmeCompanyLearners
	 */
	public SitesSmeCompanyLearners findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find SitesSmeCompanyLearners by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SitesSmeCompanyLearners}
	 * @throws Exception the exception
	 * @see SitesSmeCompanyLearners
	 */
	public List<SitesSmeCompanyLearners> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load SitesSmeCompanyLearners.
	 *
	 * @param first    from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SitesSmeCompanyLearners}
	 * @throws Exception the exception
	 */
	public List<SitesSmeCompanyLearners> allSitesSmeCompanyLearners(int first, int pageSize) throws Exception {
		return dao.allSitesSmeCompanyLearners(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of SitesSmeCompanyLearners for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the SitesSmeCompanyLearners
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
		return dao.count(SitesSmeCompanyLearners.class);
	}

	/**
	 * Lazy load SitesSmeCompanyLearners with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1    SitesSmeCompanyLearners class
	 * @param first     the first
	 * @param pageSize  the page size
	 * @param sortField the sort field
	 * @param sortOrder the sort order
	 * @param filters   the filters
	 * @return a list of {@link haj.com.entity.SitesSmeCompanyLearners} containing data
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SitesSmeCompanyLearners> allSitesSmeCompanyLearners(Class<SitesSmeCompanyLearners> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<SitesSmeCompanyLearners>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of SitesSmeCompanyLearners for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity  SitesSmeCompanyLearners class
	 * @param filters the filters
	 * @return Number of rows in the SitesSmeCompanyLearners entity
	 * @throws Exception the exception
	 */
	public int count(Class<SitesSmeCompanyLearners> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}	
	
}