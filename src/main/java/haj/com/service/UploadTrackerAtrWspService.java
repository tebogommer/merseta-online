package haj.com.service;

import java.util.List;

import haj.com.dao.UploadTrackerAtrWspDAO;
import haj.com.entity.UploadTrackerAtrWsp;
import haj.com.framework.AbstractService;
import java.util.Map;

import javax.persistence.Column;

import org.primefaces.model.SortOrder;

public class UploadTrackerAtrWspService extends AbstractService {
	/** The dao. */
	private UploadTrackerAtrWspDAO dao = new UploadTrackerAtrWspDAO();
	
	private static UploadTrackerAtrWspService uploadTrackerAtrWspService;
	public static synchronized UploadTrackerAtrWspService instance() {
		if (uploadTrackerAtrWspService == null) {
			uploadTrackerAtrWspService = new UploadTrackerAtrWspService();
		}
		return uploadTrackerAtrWspService;
	}

	/**
	 * Get all UploadTrackerAtrWsp
 	 * @author TechFinium 
 	 * @see   UploadTrackerAtrWsp
 	 * @return a list of {@link haj.com.entity.UploadTrackerAtrWsp}
	 * @throws Exception the exception
 	 */
	public List<UploadTrackerAtrWsp> allUploadTrackerAtrWsp() throws Exception {
	  	return dao.allUploadTrackerAtrWsp();
	}


	/**
	 * Create or update UploadTrackerAtrWsp.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     UploadTrackerAtrWsp
	 */
	public void create(UploadTrackerAtrWsp entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  UploadTrackerAtrWsp.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UploadTrackerAtrWsp
	 */
	public void update(UploadTrackerAtrWsp entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  UploadTrackerAtrWsp.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UploadTrackerAtrWsp
	 */
	public void delete(UploadTrackerAtrWsp entity) throws Exception  {
		this.dao.delete(entity);
	}
	
	public void createEntry(String section, Integer amount, String action, Long wspId, Long userId){
		try {
			UploadTrackerAtrWsp entry = new UploadTrackerAtrWsp();
			entry.setSection(section);
			entry.setAmount(amount);
			entry.setAction(action);
			entry.setWspId(wspId);
			entry.setUserId(userId);
			create(entry);
		} catch (Exception e) {
			logger.fatal(e);
		}
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.UploadTrackerAtrWsp}
	 * @throws Exception the exception
	 * @see    UploadTrackerAtrWsp
	 */
	public UploadTrackerAtrWsp findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find UploadTrackerAtrWsp by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.UploadTrackerAtrWsp}
	 * @throws Exception the exception
	 * @see    UploadTrackerAtrWsp
	 */
	public List<UploadTrackerAtrWsp> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load UploadTrackerAtrWsp
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.UploadTrackerAtrWsp}
	 * @throws Exception the exception
	 */
	public List<UploadTrackerAtrWsp> allUploadTrackerAtrWsp(int first, int pageSize) throws Exception {
		return dao.allUploadTrackerAtrWsp(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of UploadTrackerAtrWsp for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the UploadTrackerAtrWsp
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(UploadTrackerAtrWsp.class);
	}
	
    /**
     * Lazy load UploadTrackerAtrWsp with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 UploadTrackerAtrWsp class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.UploadTrackerAtrWsp} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<UploadTrackerAtrWsp> allUploadTrackerAtrWsp(Class<UploadTrackerAtrWsp> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<UploadTrackerAtrWsp>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of UploadTrackerAtrWsp for lazy load with filters
     * @author TechFinium 
     * @param entity UploadTrackerAtrWsp class
     * @param filters the filters
     * @return Number of rows in the UploadTrackerAtrWsp entity
     * @throws Exception the exception     
     */	
	public int count(Class<UploadTrackerAtrWsp> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
