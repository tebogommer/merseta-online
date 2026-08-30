package haj.com.service.lookup;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.LegacyProviderLearnershipDAO;
import haj.com.entity.lookup.LegacyAssessorLearnership;
import haj.com.entity.lookup.LegacyProviderLearnership;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class LegacyProviderLearnershipService extends AbstractService {
	
	private static LegacyProviderLearnershipService legacyProviderLearnershipService;
	public static synchronized LegacyProviderLearnershipService instance() {
		if (legacyProviderLearnershipService == null) {
			legacyProviderLearnershipService = new LegacyProviderLearnershipService();
		}
		return legacyProviderLearnershipService;
	}
	
	/** The dao. */
	private LegacyProviderLearnershipDAO dao = new LegacyProviderLearnershipDAO();

	/**
	 * Get all LegacyProviderLearnership
	 * 
	 * @author TechFinium
	 * @see LegacyProviderLearnership
	 * @return a list of {@link haj.com.entity.LegacyProviderLearnership}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyProviderLearnership> allLegacyProviderLearnership() throws Exception {
		return dao.allLegacyProviderLearnership();
	}

	/**
	 * Create or update LegacyProviderLearnership.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyProviderLearnership
	 */
	public void create(LegacyProviderLearnership entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacyProviderLearnership.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyProviderLearnership
	 */
	public void update(LegacyProviderLearnership entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyProviderLearnership.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyProviderLearnership
	 */
	public void delete(LegacyProviderLearnership entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacyProviderLearnership}
	 * @throws Exception
	 *             the exception
	 * @see LegacyProviderLearnership
	 */
	public LegacyProviderLearnership findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyProviderLearnership by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacyProviderLearnership}
	 * @throws Exception
	 *             the exception
	 * @see LegacyProviderLearnership
	 */
	public List<LegacyProviderLearnership> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyProviderLearnership
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyProviderLearnership}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyProviderLearnership> allLegacyProviderLearnership(int first, int pageSize) throws Exception {
		return dao.allLegacyProviderLearnership(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyProviderLearnership for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyProviderLearnership
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyProviderLearnership.class);
	}

	/**
	 * Lazy load LegacyProviderLearnership with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacyProviderLearnership class
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
	 * @return a list of {@link haj.com.entity.LegacyProviderLearnership}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderLearnership> allLegacyProviderLearnership(Class<LegacyProviderLearnership> class1,
			int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters)
			throws Exception {
		return (List<LegacyProviderLearnership>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder,
				filters);

	}

	/**
	 * Get count of LegacyProviderLearnership for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacyProviderLearnership class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacyProviderLearnership entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacyProviderLearnership> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyProviderLearnership> allEntries = allLegacyProviderLearnership();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyProviderLearnership> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}
	
	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}
	
	public List<LegacyProviderLearnership> findBySldNoAndLearnershipIsNotNull(String sldNo) throws Exception {
	 	return resolveExpiryDate(dao.findBySldNoAndLearnershipIsNotNull(sldNo));
	}
	
	public List<LegacyProviderLearnership> findByAccreditationNoAndLearnershipIsNotNull(String accreditationNo) throws Exception {
	 	return resolveExpiryDate(dao.findByAccreditationNoAndLearnershipIsNotNull(accreditationNo));
	}
	public List<LegacyProviderLearnership> resolveExpiryDate(List<LegacyProviderLearnership> list){
		if(list !=null && list.size()>0)
		{
			for(LegacyProviderLearnership llp:list)
			{
				if(llp.getLearnership() !=null && llp.getLearnership().getQualification() !=null && llp.getLearnership().getQualification().getLastDateForEnrolment() !=null)
				{
					if(llp.getLearnership().getQualification().getLastDateForEnrolment().before(new Date()))
					{
						llp.setQualificationExpired(true);
					}
					else
					{
						llp.setQualificationExpired(false);
					}
				}
			}
		}
		
		return list;
	}
}
