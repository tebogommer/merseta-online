package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.AppraisalCategoryCodeDAO;
import haj.com.entity.lookup.AppraisalCategoryCode;
import haj.com.framework.AbstractService;

public class AppraisalCategoryCodeService extends AbstractService {
	/** The dao. */
	private AppraisalCategoryCodeDAO dao = new AppraisalCategoryCodeDAO();

	/**
	 * Get all AppraisalCategoryCode
 	 * @author TechFinium 
 	 * @see   AppraisalCategoryCode
 	 * @return a list of {@link haj.com.entity.AppraisalCategoryCode}
	 * @throws Exception the exception
 	 */
	public List<AppraisalCategoryCode> allAppraisalCategoryCode() throws Exception {
	  	return dao.allAppraisalCategoryCode();
	}


	/**
	 * Create or update AppraisalCategoryCode.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     AppraisalCategoryCode
	 */
	public void create(AppraisalCategoryCode entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  AppraisalCategoryCode.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    AppraisalCategoryCode
	 */
	public void update(AppraisalCategoryCode entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  AppraisalCategoryCode.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    AppraisalCategoryCode
	 */
	public void delete(AppraisalCategoryCode entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.AppraisalCategoryCode}
	 * @throws Exception the exception
	 * @see    AppraisalCategoryCode
	 */
	public AppraisalCategoryCode findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find AppraisalCategoryCode by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.AppraisalCategoryCode}
	 * @throws Exception the exception
	 * @see    AppraisalCategoryCode
	 */
	public List<AppraisalCategoryCode> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load AppraisalCategoryCode
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.AppraisalCategoryCode}
	 * @throws Exception the exception
	 */
	public List<AppraisalCategoryCode> allAppraisalCategoryCode(int first, int pageSize) throws Exception {
		return dao.allAppraisalCategoryCode(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of AppraisalCategoryCode for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the AppraisalCategoryCode
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(AppraisalCategoryCode.class);
	}
	
    /**
     * Lazy load AppraisalCategoryCode with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 AppraisalCategoryCode class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.AppraisalCategoryCode} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<AppraisalCategoryCode> allAppraisalCategoryCode(Class<AppraisalCategoryCode> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<AppraisalCategoryCode>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of AppraisalCategoryCode for lazy load with filters
     * @author TechFinium 
     * @param entity AppraisalCategoryCode class
     * @param filters the filters
     * @return Number of rows in the AppraisalCategoryCode entity
     * @throws Exception the exception     
     */	
	public int count(Class<AppraisalCategoryCode> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
