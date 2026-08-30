package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.CategorizationDataDAO;
import haj.com.entity.CategorizationData;
import haj.com.entity.enums.CategorizationEnum;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class CategorizationDataService extends AbstractService {
	/** The dao. */
	private CategorizationDataDAO dao = new CategorizationDataDAO();
	
	private static CategorizationDataService categorizationDataService;
	public static synchronized CategorizationDataService instance() {
		if (categorizationDataService == null) {
			categorizationDataService = new CategorizationDataService();
		}
		return categorizationDataService;
	}

	/**
	 * Get all CategorizationData
	 * 
	 * @author TechFinium
	 * @see CategorizationData
	 * @return a list of {@link haj.com.entity.CategorizationData}
	 * @throws Exception
	 *             the exception
	 */
	public List<CategorizationData> allCategorizationData() throws Exception {
		return dao.allCategorizationData();
	}
	
	public CategorizationData findByCompany(Long companyID) throws Exception {
		return dao.findByCompany(companyID);
	}

	/**
	 * Create or update CategorizationData.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CategorizationData
	 */
	public void create(CategorizationData entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	public void save(List<CategorizationData> calculationData) throws Exception {
		for (CategorizationData categorizationData : calculationData) {
			categorizationData.setCategorization(CategorizationEnum.getIdPassportEnumByValue(categorizationData.getCompanyCategory()));
		}
		this.dao.createBatch((List<IDataEntity>) (List<?>) calculationData);
	}

	/**
	 * Update CategorizationData.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CategorizationData
	 */
	public void update(CategorizationData entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete CategorizationData.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CategorizationData
	 */
	public void delete(CategorizationData entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.CategorizationData}
	 * @throws Exception
	 *             the exception
	 * @see CategorizationData
	 */
	public CategorizationData findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find CategorizationData by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.CategorizationData}
	 * @throws Exception
	 *             the exception
	 * @see CategorizationData
	 */
	public List<CategorizationData> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load CategorizationData
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.CategorizationData}
	 * @throws Exception
	 *             the exception
	 */
	public List<CategorizationData> allCategorizationData(int first, int pageSize) throws Exception {
		return dao.allCategorizationData(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of CategorizationData for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the CategorizationData
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(CategorizationData.class);
	}

	/**
	 * Lazy load CategorizationData with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            CategorizationData class
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
	 * @return a list of {@link haj.com.entity.CategorizationData} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CategorizationData> allCategorizationData(Class<CategorizationData> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<CategorizationData>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of CategorizationData for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            CategorizationData class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the CategorizationData entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<CategorizationData> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	public List<CategorizationData> findByLNumber(String lNumber) throws Exception {
		return dao.findByLNumber(lNumber);
	}
	
	public CategorizationEnum returnCategorizationFound(String lNumber) throws Exception{
		List<CategorizationData> entriesFound = findByLNumber(lNumber);
		if (entriesFound != null && entriesFound.size() != 0 && entriesFound.get(0).getCategorization() != null) {
			return entriesFound.get(0).getCategorization();
		} else {
			return null;
		}
	}
}
