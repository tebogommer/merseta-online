package haj.com.service;

import java.util.List;

import haj.com.dao.FailedArchiveEntriesDAO;
import haj.com.entity.FailedArchiveEntries;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class FailedArchiveEntriesService extends AbstractService {
	/** The dao. */
	private FailedArchiveEntriesDAO dao = new FailedArchiveEntriesDAO();
	
	private static FailedArchiveEntriesService failedArchiveEntriesService;
	public static synchronized FailedArchiveEntriesService instance() {
		if (failedArchiveEntriesService == null) {
			failedArchiveEntriesService = new FailedArchiveEntriesService();
		}
		return failedArchiveEntriesService;
	}

	/**
	 * Get all FailedArchiveEntries
 	 * @author TechFinium 
 	 * @see   FailedArchiveEntries
 	 * @return a list of {@link haj.com.entity.FailedArchiveEntries}
	 * @throws Exception the exception
 	 */
	public List<FailedArchiveEntries> allFailedArchiveEntries() throws Exception {
	  	return dao.allFailedArchiveEntries();
	}


	/**
	 * Create or update FailedArchiveEntries.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     FailedArchiveEntries
	 */
	public void create(FailedArchiveEntries entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  FailedArchiveEntries.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    FailedArchiveEntries
	 */
	public void update(FailedArchiveEntries entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  FailedArchiveEntries.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    FailedArchiveEntries
	 */
	public void delete(FailedArchiveEntries entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.FailedArchiveEntries}
	 * @throws Exception the exception
	 * @see    FailedArchiveEntries
	 */
	public FailedArchiveEntries findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find FailedArchiveEntries by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.FailedArchiveEntries}
	 * @throws Exception the exception
	 * @see    FailedArchiveEntries
	 */
	public List<FailedArchiveEntries> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load FailedArchiveEntries
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.FailedArchiveEntries}
	 * @throws Exception the exception
	 */
	public List<FailedArchiveEntries> allFailedArchiveEntries(int first, int pageSize) throws Exception {
		return dao.allFailedArchiveEntries(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of FailedArchiveEntries for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the FailedArchiveEntries
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(FailedArchiveEntries.class);
	}
	
    /**
     * Lazy load FailedArchiveEntries with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 FailedArchiveEntries class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.FailedArchiveEntries} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<FailedArchiveEntries> allFailedArchiveEntries(Class<FailedArchiveEntries> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<FailedArchiveEntries>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of FailedArchiveEntries for lazy load with filters
     * @author TechFinium 
     * @param entity FailedArchiveEntries class
     * @param filters the filters
     * @return Number of rows in the FailedArchiveEntries entity
     * @throws Exception the exception     
     */	
	public int count(Class<FailedArchiveEntries> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public void logFail(Long mandatoryGrantDetailId, String locationFailed, String exceptionToString) throws Exception{
		FailedArchiveEntries failedArchiveEntries = new FailedArchiveEntries();
		failedArchiveEntries.setMandatoryGrantDetailId(mandatoryGrantDetailId);
		failedArchiveEntries.setLocationFailed(locationFailed);
		failedArchiveEntries.setException(exceptionToString);
		dao.create(failedArchiveEntries);
	}
}
