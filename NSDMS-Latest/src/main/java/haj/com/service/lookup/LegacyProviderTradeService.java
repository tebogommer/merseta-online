package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.LegacyProviderTradeDAO;
import haj.com.entity.lookup.LegacyProviderTrade;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class LegacyProviderTradeService extends AbstractService {
	/** The dao. */
	private LegacyProviderTradeDAO dao = new LegacyProviderTradeDAO();

	/**
	 * Get all LegacyProviderTrade
	 * 
	 * @author TechFinium
	 * @see LegacyProviderTrade
	 * @return a list of {@link haj.com.entity.LegacyProviderTrade}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyProviderTrade> allLegacyProviderTrade() throws Exception {
		return dao.allLegacyProviderTrade();
	}

	/**
	 * Create or update LegacyProviderTrade.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyProviderTrade
	 */
	public void create(LegacyProviderTrade entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacyProviderTrade.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyProviderTrade
	 */
	public void update(LegacyProviderTrade entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyProviderTrade.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyProviderTrade
	 */
	public void delete(LegacyProviderTrade entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacyProviderTrade}
	 * @throws Exception
	 *             the exception
	 * @see LegacyProviderTrade
	 */
	public LegacyProviderTrade findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyProviderTrade by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacyProviderTrade}
	 * @throws Exception
	 *             the exception
	 * @see LegacyProviderTrade
	 */
	public List<LegacyProviderTrade> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyProviderTrade
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyProviderTrade}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyProviderTrade> allLegacyProviderTrade(int first, int pageSize) throws Exception {
		return dao.allLegacyProviderTrade(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyProviderTrade for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyProviderTrade
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyProviderTrade.class);
	}

	/**
	 * Lazy load LegacyProviderTrade with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacyProviderTrade class
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
	 * @return a list of {@link haj.com.entity.LegacyProviderTrade} containing
	 *         data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderTrade> allLegacyProviderTrade(Class<LegacyProviderTrade> class1, int first, int pageSize,
			String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<LegacyProviderTrade>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of LegacyProviderTrade for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacyProviderTrade class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacyProviderTrade entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacyProviderTrade> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyProviderTrade> allEntries = allLegacyProviderTrade();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyProviderTrade> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}
	
	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}
}
