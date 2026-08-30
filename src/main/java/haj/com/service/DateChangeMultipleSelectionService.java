package haj.com.service;

import java.util.ArrayList;
import java.util.List;

import haj.com.dao.DateChangeMultipleSelectionDAO;
import haj.com.entity.DateChangeMultipleSelection;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.Users;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.DateChangeReasons;
import haj.com.entity.lookup.RejectReasons;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class DateChangeMultipleSelectionService extends AbstractService {
	/** The dao. */
	private DateChangeMultipleSelectionDAO dao = new DateChangeMultipleSelectionDAO();
	
	/** Instance of service level */
	private static DateChangeMultipleSelectionService dateChangeMultipleSelectionService;
	public static synchronized DateChangeMultipleSelectionService instance() {
		if (dateChangeMultipleSelectionService == null) {
			dateChangeMultipleSelectionService = new DateChangeMultipleSelectionService();
		}
		return dateChangeMultipleSelectionService;
	}

	/**
	 * Get all DateChangeMultipleSelection
 	 * @author TechFinium 
 	 * @see   DateChangeMultipleSelection
 	 * @return a list of {@link haj.com.entity.DateChangeMultipleSelection}
	 * @throws Exception the exception
 	 */
	public List<DateChangeMultipleSelection> allDateChangeMultipleSelection() throws Exception {
	  	return dao.allDateChangeMultipleSelection();
	}


	/**
	 * Create or update DateChangeMultipleSelection.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     DateChangeMultipleSelection
	 */
	public void create(DateChangeMultipleSelection entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  DateChangeMultipleSelection.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DateChangeMultipleSelection
	 */
	public void update(DateChangeMultipleSelection entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  DateChangeMultipleSelection.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DateChangeMultipleSelection
	 */
	public void delete(DateChangeMultipleSelection entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.DateChangeMultipleSelection}
	 * @throws Exception the exception
	 * @see    DateChangeMultipleSelection
	 */
	public DateChangeMultipleSelection findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find DateChangeMultipleSelection by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.DateChangeMultipleSelection}
	 * @throws Exception the exception
	 * @see    DateChangeMultipleSelection
	 */
	public List<DateChangeMultipleSelection> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load DateChangeMultipleSelection
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.DateChangeMultipleSelection}
	 * @throws Exception the exception
	 */
	public List<DateChangeMultipleSelection> allDateChangeMultipleSelection(int first, int pageSize) throws Exception {
		return dao.allDateChangeMultipleSelection(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of DateChangeMultipleSelection for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the DateChangeMultipleSelection
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(DateChangeMultipleSelection.class);
	}
	
    /**
     * Lazy load DateChangeMultipleSelection with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 DateChangeMultipleSelection class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.DateChangeMultipleSelection} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<DateChangeMultipleSelection> allDateChangeMultipleSelection(Class<DateChangeMultipleSelection> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<DateChangeMultipleSelection>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of DateChangeMultipleSelection for lazy load with filters
     * @author TechFinium 
     * @param entity DateChangeMultipleSelection class
     * @param filters the filters
     * @return Number of rows in the DateChangeMultipleSelection entity
     * @throws Exception the exception     
     */	
	public int count(Class<DateChangeMultipleSelection> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public List<DateChangeMultipleSelection> findByTargetKeyAndClassName(Long targetKey, String className) throws Exception {
		return dao.findByTargetKeyAndClassName(targetKey, className);
	}
	
	public List<DateChangeMultipleSelection> findByTargetKeyClassNameAndProcess(Long targetKey, String className, ConfigDocProcessEnum process) throws Exception {
		return dao.findByTargetKeyClassNameAndProcess(targetKey, className, process);
	}

	/**
	 * Removes old links and creates new links for rejected reasons
	 * @param id
	 * @param name
	 * @param dateChangeReasonsList
	 * @return entries
	 */
	public List<DateChangeMultipleSelection> removeCreateLinks(Long targetKey, String className, List<DateChangeReasons> dateChangeReasonsList, Users sessionUser, ConfigDocProcessEnum process) throws Exception{
		// delete all old entries by target key and class name
		clearEntries(targetKey, className, process);
		List<DateChangeMultipleSelection> entries = new ArrayList<DateChangeMultipleSelection>();
		for (DateChangeReasons reason : dateChangeReasonsList) {
			DateChangeMultipleSelection rms = new DateChangeMultipleSelection(targetKey, className, reason, sessionUser, process);
			entries.add(rms);
		}
		return entries;
	}

	public void clearEntries(Long targetKey, String className,ConfigDocProcessEnum process) throws Exception {
		List<IDataEntity> entityList = new ArrayList<IDataEntity>();
		entityList.addAll(findByTargetKeyClassNameAndProcess(targetKey, className, process));
		if (entityList.size() != 0) {
			dao.deleteBatch(entityList);
		}
		entityList = null;
	}
}
