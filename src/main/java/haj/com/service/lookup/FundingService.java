package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.FundingDAO;
import haj.com.entity.ProjectImplementationPlan;
import haj.com.entity.lookup.Funding;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class FundingService.
 */
public class FundingService extends AbstractService {
	/** The dao. */
	private FundingDAO dao = new FundingDAO();

	/**
	 * Get all Funding.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Funding}
	 * @throws Exception
	 *             the exception
	 * @see Funding
	 */
	public List<Funding> allFunding() throws Exception {
		return dao.allFunding();
	}

	public List<Funding> allFunding(Boolean appearOnWSP) throws Exception {
		return dao.allFunding(appearOnWSP);

	}

	/**
	 * Create or update Funding.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Funding
	 */
	public void create(Funding entity) throws Exception {
		if (dao.findUniqueCode(entity) != null)
			throw new Exception("Code must be unique");
		if (entity.getId() == null) {
			entity.setCreateDate(new java.util.Date());
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update Funding.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Funding
	 */
	public void update(Funding entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete Funding.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Funding
	 */
	public void delete(Funding entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.Funding}
	 * @throws Exception
	 *             the exception
	 * @see Funding
	 */
	public Funding findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find Funding by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.Funding}
	 * @throws Exception
	 *             the exception
	 * @see Funding
	 */
	public List<Funding> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load Funding.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.Funding}
	 * @throws Exception
	 *             the exception
	 */
	public List<Funding> allFunding(int first, int pageSize) throws Exception {
		return dao.allFunding(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of Funding for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Funding
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(Funding.class);
	}

	/**
	 * Lazy load Funding with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            Funding class
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
	 * @return a list of {@link haj.com.entity.Funding} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Funding> allFunding(Class<Funding> class1, int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<Funding>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of Funding for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            Funding class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the Funding entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<Funding> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/**
	 * Find by code.
	 *
	 * @param code
	 *            the code
	 * @return the funding
	 * @throws Exception
	 *             the exception
	 */
	public Funding findByCode(String code) throws Exception {
		if (StringUtils.isBlank(code))
			return null;
		return dao.findByCode(code.trim());
	}

	public List<Funding> findByExcludeMerSETAKey() throws Exception {
		// TODO Auto-generated method stub
		return dao.findByExcludeMerSETAKey();
	}
}
