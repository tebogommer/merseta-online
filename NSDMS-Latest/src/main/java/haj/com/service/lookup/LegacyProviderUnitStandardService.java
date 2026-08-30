package haj.com.service.lookup;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.LegacyProviderUnitStandardDAO;
import haj.com.dao.lookup.ResolveByCodeLookupDAO;
import haj.com.entity.lookup.LegacyProviderUnitStandard;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class LegacyProviderUnitStandardService extends AbstractService {
	/** The dao. */
	private LegacyProviderUnitStandardDAO dao = new LegacyProviderUnitStandardDAO();

	private static LegacyProviderUnitStandardService legacyProviderUnitStandardService;

	public static synchronized LegacyProviderUnitStandardService instance() {
		if (legacyProviderUnitStandardService == null) {
			legacyProviderUnitStandardService = new LegacyProviderUnitStandardService();
		}
		return legacyProviderUnitStandardService;
	}
	
	/** Other Dao */
	private ResolveByCodeLookupDAO resolveByCodeLookupDAO = new ResolveByCodeLookupDAO();

	/**
	 * Get all LegacyProviderUnitStandard
	 * 
	 * @author TechFinium
	 * @see LegacyProviderUnitStandard
	 * @return a list of {@link haj.com.entity.LegacyProviderUnitStandard}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyProviderUnitStandard> allLegacyProviderUnitStandard() throws Exception {
		return dao.allLegacyProviderUnitStandard();
	}

	/**
	 * Create or update LegacyProviderUnitStandard.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyProviderUnitStandard
	 */
	public void create(LegacyProviderUnitStandard entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacyProviderUnitStandard.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyProviderUnitStandard
	 */
	public void update(LegacyProviderUnitStandard entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyProviderUnitStandard.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyProviderUnitStandard
	 */
	public void delete(LegacyProviderUnitStandard entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacyProviderUnitStandard}
	 * @throws Exception
	 *             the exception
	 * @see LegacyProviderUnitStandard
	 */
	public LegacyProviderUnitStandard findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyProviderUnitStandard by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacyProviderUnitStandard}
	 * @throws Exception
	 *             the exception
	 * @see LegacyProviderUnitStandard
	 */
	public List<LegacyProviderUnitStandard> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyProviderUnitStandard
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyProviderUnitStandard}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyProviderUnitStandard> allLegacyProviderUnitStandard(int first, int pageSize) throws Exception {
		return dao.allLegacyProviderUnitStandard(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyProviderUnitStandard for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyProviderUnitStandard
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyProviderUnitStandard.class);
	}

	/**
	 * Lazy load LegacyProviderUnitStandard with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacyProviderUnitStandard class
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
	 * @return a list of {@link haj.com.entity.LegacyProviderUnitStandard}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderUnitStandard> allLegacyProviderUnitStandard(Class<LegacyProviderUnitStandard> class1,
			int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters)
			throws Exception {
		return (List<LegacyProviderUnitStandard>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder,
				filters);
	}

	/**
	 * Get count of LegacyProviderUnitStandard for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacyProviderUnitStandard class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacyProviderUnitStandard entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacyProviderUnitStandard> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyProviderUnitStandard> allEntries = allLegacyProviderUnitStandard();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyProviderUnitStandard> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public List<LegacyProviderUnitStandard> findBySldNoAndUnitStandardIsNotNull(String sldNo) throws Exception {
		return resolveUSExpiryDate(dao.findBySldNoAndUnitStandardIsNotNull(sldNo));
	}

	public List<LegacyProviderUnitStandard> findByAccreditationNoAndUnitStandardIsNotNull(String accreditationNo) throws Exception {
		return resolveUSExpiryDate(dao.findByAccreditationNoAndUnitStandardIsNotNull(accreditationNo));
	}

	public List<LegacyProviderUnitStandard> resolveUSExpiryDate(List<LegacyProviderUnitStandard> list) {
		if (list != null && list.size() > 0) {
			for (LegacyProviderUnitStandard qual : list) {
				if (qual.getUnitStandard() != null && qual.getUnitStandard().getLastDateForEnrolment() != null) {
					if (qual.getUnitStandard().getLastDateForEnrolment().before(new Date())) {
						qual.setUnitStandardExpired(true);
					} else {
						qual.setUnitStandardExpired(false);
					}
				}
			}
		}
		return list;
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}
	
	public LegacyProviderUnitStandard runValidiationForSingleEntry(LegacyProviderUnitStandard entry) throws Exception{
		try {
			entry.setUnitStandard(resolveByCodeLookupDAO.findUnitStandards(entry.getSaqaUsId()));
		} catch (Exception e) {
		}
		try {
			entry.setLinkedAccreditationNumberOnProviderAccreditation(resolveByCodeLookupDAO.checkLegacyProviderAccreditation(entry.getAccreditationNo()));
		} catch (Exception e) {
		}
		try {
			if (entry.getUnitStandard() != null && entry.getUnitStandard().getLastDateForEnrolment() != null) {
				if (entry.getUnitStandard().getLastDateForEnrolment().before(new Date())) {
					entry.setUnitStandardExpired(true);
				} else {
					entry.setUnitStandardExpired(false);
				}
			}
		} catch (Exception e) {
		}
		return entry;
	}
}
