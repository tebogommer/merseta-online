package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.MgVerificationCompletedDAO;
import haj.com.entity.MgVerificationCompleted;
import haj.com.framework.AbstractService;

public class MgVerificationCompletedService extends AbstractService {
	/** The dao. */
	private MgVerificationCompletedDAO dao = new MgVerificationCompletedDAO();

	/**
	 * Get all MgVerificationCompleted
 	 * @author TechFinium 
 	 * @see   MgVerificationCompleted
 	 * @return a list of {@link haj.com.entity.MgVerificationCompleted}
	 * @throws Exception the exception
 	 */
	public List<MgVerificationCompleted> allMgVerificationCompleted() throws Exception {
	  	return dao.allMgVerificationCompleted();
	}


	/**
	 * Create or update MgVerificationCompleted.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     MgVerificationCompleted
	 */
	public void create(MgVerificationCompleted entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  MgVerificationCompleted.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    MgVerificationCompleted
	 */
	public void update(MgVerificationCompleted entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  MgVerificationCompleted.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    MgVerificationCompleted
	 */
	public void delete(MgVerificationCompleted entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.MgVerificationCompleted}
	 * @throws Exception the exception
	 * @see    MgVerificationCompleted
	 */
	public MgVerificationCompleted findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find MgVerificationCompleted by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.MgVerificationCompleted}
	 * @throws Exception the exception
	 * @see    MgVerificationCompleted
	 */
	public List<MgVerificationCompleted> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load MgVerificationCompleted
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.MgVerificationCompleted}
	 * @throws Exception the exception
	 */
	public List<MgVerificationCompleted> allMgVerificationCompleted(int first, int pageSize) throws Exception {
		return dao.allMgVerificationCompleted(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of MgVerificationCompleted for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the MgVerificationCompleted
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(MgVerificationCompleted.class);
	}
	
    /**
     * Lazy load MgVerificationCompleted with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 MgVerificationCompleted class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.MgVerificationCompleted} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<MgVerificationCompleted> allMgVerificationCompleted(Class<MgVerificationCompleted> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<MgVerificationCompleted>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of MgVerificationCompleted for lazy load with filters
     * @author TechFinium 
     * @param entity MgVerificationCompleted class
     * @param filters the filters
     * @return Number of rows in the MgVerificationCompleted entity
     * @throws Exception the exception     
     */	
	public int count(Class<MgVerificationCompleted> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
