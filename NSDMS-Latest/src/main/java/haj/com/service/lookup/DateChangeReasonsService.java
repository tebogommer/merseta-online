package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.DateChangeReasonsDAO;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.DateChangeReasons;
import haj.com.entity.lookup.RejectReasons;
import haj.com.framework.AbstractService;
import haj.com.service.DateChangeMultipleSelectionService;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class DateChangeReasonsService extends AbstractService {
	/** The dao. */
	private DateChangeReasonsDAO dao = new DateChangeReasonsDAO();
	
	/** Instance of service level */
	private static DateChangeReasonsService dateChangeReasonsService;
	public static synchronized DateChangeReasonsService instance() {
		if (dateChangeReasonsService == null) {
			dateChangeReasonsService = new DateChangeReasonsService();
		}
		return dateChangeReasonsService;
	}

	/**
	 * Get all DateChangeReasons
 	 * @author TechFinium 
 	 * @see   DateChangeReasons
 	 * @return a list of {@link haj.com.entity.DateChangeReasons}
	 * @throws Exception the exception
 	 */
	public List<DateChangeReasons> allDateChangeReasons() throws Exception {
	  	return dao.allDateChangeReasons();
	}


	/**
	 * Create or update DateChangeReasons.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     DateChangeReasons
	 */
	public void create(DateChangeReasons entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  DateChangeReasons.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DateChangeReasons
	 */
	public void update(DateChangeReasons entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  DateChangeReasons.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DateChangeReasons
	 */
	public void delete(DateChangeReasons entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.DateChangeReasons}
	 * @throws Exception the exception
	 * @see    DateChangeReasons
	 */
	public DateChangeReasons findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find DateChangeReasons by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.DateChangeReasons}
	 * @throws Exception the exception
	 * @see    DateChangeReasons
	 */
	public List<DateChangeReasons> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load DateChangeReasons
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.DateChangeReasons}
	 * @throws Exception the exception
	 */
	public List<DateChangeReasons> allDateChangeReasons(int first, int pageSize) throws Exception {
		return dao.allDateChangeReasons(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of DateChangeReasons for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the DateChangeReasons
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(DateChangeReasons.class);
	}
	
    /**
     * Lazy load DateChangeReasons with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 DateChangeReasons class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.DateChangeReasons} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<DateChangeReasons> allDateChangeReasons(Class<DateChangeReasons> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<DateChangeReasons>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of DateChangeReasons for lazy load with filters
     * @author TechFinium 
     * @param entity DateChangeReasons class
     * @param filters the filters
     * @return Number of rows in the DateChangeReasons entity
     * @throws Exception the exception     
     */	
	public int count(Class<DateChangeReasons> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/**
	 * Find by process.
	 *
	 * @param process
	 *            the process
	 * @return the list
	 */
	public List<DateChangeReasons> findByProcess(ConfigDocProcessEnum process) {
		return dao.findByProcess(process);
	}

	/**
	 * 
	 * 
	 * @param process
	 * @param booleanValue
	 * @return throws Exception
	 */
	public List<DateChangeReasons> findByProcessAndBooleanSelection(ConfigDocProcessEnum process, Boolean booleanValue)
			throws Exception {
		return dao.findByProcessAndBooleanSelection(process, booleanValue);
	}

	public List<DateChangeReasons> locateReasonsSelectedByTargetKeyAndClass(Long targetKey, String targetClass)
			throws Exception {
		return dao.findLinkedByMultipleSelection(targetKey, targetClass);
	}

	public List<DateChangeReasons> locateReasonsSelectedByTargetKeyClassAndProcess(Long targetKey, String targetClass,
			ConfigDocProcessEnum process) throws Exception {
		return dao.locateReasonsSelectedByTargetKeyClassAndProcess(targetKey, targetClass, process);
	}

	public long countReasonsSelectedByTargetKeyClassAndProcess(Long targetKey, String targetClass,
			ConfigDocProcessEnum process) throws Exception {
		return dao.countReasonsSelectedByTargetKeyClassAndProcess(targetKey, targetClass, process);
	}
}
