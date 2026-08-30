package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.SizeOfCompanyDAO;
import haj.com.entity.Company;
import haj.com.entity.lookup.SizeOfCompany;
import haj.com.framework.AbstractService;

public class SizeOfCompanyService extends AbstractService {
	/** The dao. */
	private SizeOfCompanyDAO dao = new SizeOfCompanyDAO();

	private static SizeOfCompanyService sizeOfCompanyService;

	public static synchronized SizeOfCompanyService instance() {
		if (sizeOfCompanyService == null) {
			sizeOfCompanyService = new SizeOfCompanyService();
		}
		return sizeOfCompanyService;
	}

	/**
	 * Get all SizeOfCompany
	 * 
	 * @author TechFinium
	 * @see SizeOfCompany
	 * @return a list of {@link haj.com.entity.SizeOfCompany}
	 * @throws Exception
	 *             the exception
	 */
	public List<SizeOfCompany> allSizeOfCompany() throws Exception {
		return dao.allSizeOfCompany();
	}

	/**
	 * Create or update SizeOfCompany.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SizeOfCompany
	 */
	public void create(SizeOfCompany entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update SizeOfCompany.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SizeOfCompany
	 */
	public void update(SizeOfCompany entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete SizeOfCompany.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SizeOfCompany
	 */
	public void delete(SizeOfCompany entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.SizeOfCompany}
	 * @throws Exception
	 *             the exception
	 * @see SizeOfCompany
	 */
	public SizeOfCompany findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find SizeOfCompany by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.SizeOfCompany}
	 * @throws Exception
	 *             the exception
	 * @see SizeOfCompany
	 */
	public List<SizeOfCompany> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load SizeOfCompany
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.SizeOfCompany}
	 * @throws Exception
	 *             the exception
	 */
	public List<SizeOfCompany> allSizeOfCompany(int first, int pageSize) throws Exception {
		return dao.allSizeOfCompany(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of SizeOfCompany for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the SizeOfCompany
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(SizeOfCompany.class);
	}

	/**
	 * Lazy load SizeOfCompany with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            SizeOfCompany class
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
	 * @return a list of {@link haj.com.entity.SizeOfCompany} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SizeOfCompany> allSizeOfCompany(Class<SizeOfCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<SizeOfCompany>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of SizeOfCompany for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            SizeOfCompany class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the SizeOfCompany entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<SizeOfCompany> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public SizeOfCompany findCompanySize(Company company) throws Exception {
		if (company != null && company.getNumberOfEmployees() == null)
			return null;
		return dao.findCompanySize(company.getNumberOfEmployees());
	}

}
