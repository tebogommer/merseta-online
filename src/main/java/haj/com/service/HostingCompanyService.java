package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.HostingCompanyDAO;
import haj.com.entity.HostingCompany;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyService.
 */
public class HostingCompanyService extends AbstractService {
	/** The dao. */
	private HostingCompanyDAO dao = new HostingCompanyDAO();

	private static HostingCompanyService hostingCompanyService = null;

	/**
	 * Instance.
	 *
	 * @return the doc service
	 */
	public static synchronized HostingCompanyService instance() {
		if (hostingCompanyService == null) {
			hostingCompanyService = new HostingCompanyService();
		}
		return hostingCompanyService;
	}

	/**
	 * Get all HostingCompany.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.HostingCompany}
	 * @throws Exception
	 *             the exception
	 * @see HostingCompany
	 */
	public List<HostingCompany> allHostingCompany() throws Exception {
		return dao.allHostingCompany();
	}

	/**
	 * All hosting company first.
	 *
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<HostingCompany> allHostingCompanyFirst() throws Exception {
		return dao.allHostingCompanyFirst();
	}

	/**
	 * Create or update HostingCompany.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see HostingCompany
	 */
	public void create(HostingCompany entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update HostingCompany.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see HostingCompany
	 */
	public void update(HostingCompany entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete HostingCompany.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see HostingCompany
	 */
	public void delete(HostingCompany entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.HostingCompany}
	 * @throws Exception
	 *             the exception
	 * @see HostingCompany
	 */
	public HostingCompany findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find HostingCompany by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.HostingCompany}
	 * @throws Exception
	 *             the exception
	 * @see HostingCompany
	 */
	public List<HostingCompany> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load HostingCompany.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.HostingCompany}
	 * @throws Exception
	 *             the exception
	 */
	public List<HostingCompany> allHostingCompany(int first, int pageSize) throws Exception {
		return dao.allHostingCompany(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of HostingCompany for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the HostingCompany
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(HostingCompany.class);
	}

	/**
	 * Lazy load HostingCompany with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            HostingCompany class
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
	 * @return a list of {@link haj.com.entity.HostingCompany} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompany> allHostingCompany(Class<HostingCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<HostingCompany>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of HostingCompany for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            HostingCompany class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the HostingCompany entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<HostingCompany> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
}
