package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.EconomicStatusDAO;
import haj.com.entity.lookup.EconomicStatus;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class EconomicStatusService.
 */
public class EconomicStatusService extends AbstractService {
	/** The dao. */
	private EconomicStatusDAO dao = new EconomicStatusDAO();

	/**
	 * Get all EconomicStatus.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.EconomicStatus}
	 * @throws Exception the exception
	 * @see   EconomicStatus
	 */
	public List<EconomicStatus> allEconomicStatus() throws Exception {
	  	return dao.allEconomicStatus();
	}


	/**
	 * Create or update EconomicStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     EconomicStatus
	 */
	public void create(EconomicStatus entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  EconomicStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    EconomicStatus
	 */
	public void update(EconomicStatus entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  EconomicStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    EconomicStatus
	 */
	public void delete(EconomicStatus entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.EconomicStatus}
	 * @throws Exception the exception
	 * @see    EconomicStatus
	 */
	public EconomicStatus findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find EconomicStatus by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.EconomicStatus}
	 * @throws Exception the exception
	 * @see    EconomicStatus
	 */
	public List<EconomicStatus> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load EconomicStatus.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.EconomicStatus}
	 * @throws Exception the exception
	 */
	public List<EconomicStatus> allEconomicStatus(int first, int pageSize) throws Exception {
		return dao.allEconomicStatus(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of EconomicStatus for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the EconomicStatus
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(EconomicStatus.class);
	}
	
    /**
     * Lazy load EconomicStatus with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 EconomicStatus class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.EconomicStatus} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<EconomicStatus> allEconomicStatus(Class<EconomicStatus> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<EconomicStatus>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of EconomicStatus for lazy load with filters.
     *
     * @author TechFinium
     * @param entity EconomicStatus class
     * @param filters the filters
     * @return Number of rows in the EconomicStatus entity
     * @throws Exception the exception
     */	
	public int count(Class<EconomicStatus> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the economic status
	 * @throws Exception the exception
	 */
	public EconomicStatus findByCode(String code) throws Exception { 
		return dao.findByCode(code);
	}
}
