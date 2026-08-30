package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.AppraisalCategoriesDAO;
import haj.com.entity.lookup.AppraisalCategories;
import haj.com.framework.AbstractService;

public class AppraisalCategoriesService extends AbstractService {
	/** The dao. */
	private AppraisalCategoriesDAO dao = new AppraisalCategoriesDAO();

	/**
	 * Get all AppraisalCategories
 	 * @author TechFinium 
 	 * @see   AppraisalCategories
 	 * @return a list of {@link haj.com.entity.AppraisalCategories}
	 * @throws Exception the exception
 	 */
	public List<AppraisalCategories> allAppraisalCategories() throws Exception {
	  	return dao.allAppraisalCategories();
	}


	/**
	 * Create or update AppraisalCategories.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     AppraisalCategories
	 */
	public void create(AppraisalCategories entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  AppraisalCategories.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    AppraisalCategories
	 */
	public void update(AppraisalCategories entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  AppraisalCategories.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    AppraisalCategories
	 */
	public void delete(AppraisalCategories entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.AppraisalCategories}
	 * @throws Exception the exception
	 * @see    AppraisalCategories
	 */
	public AppraisalCategories findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find AppraisalCategories by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.AppraisalCategories}
	 * @throws Exception the exception
	 * @see    AppraisalCategories
	 */
	public List<AppraisalCategories> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load AppraisalCategories
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.AppraisalCategories}
	 * @throws Exception the exception
	 */
	public List<AppraisalCategories> allAppraisalCategories(int first, int pageSize) throws Exception {
		return dao.allAppraisalCategories(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of AppraisalCategories for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the AppraisalCategories
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(AppraisalCategories.class);
	}
	
    /**
     * Lazy load AppraisalCategories with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 AppraisalCategories class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.AppraisalCategories} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<AppraisalCategories> allAppraisalCategories(Class<AppraisalCategories> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<AppraisalCategories>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of AppraisalCategories for lazy load with filters
     * @author TechFinium 
     * @param entity AppraisalCategories class
     * @param filters the filters
     * @return Number of rows in the AppraisalCategories entity
     * @throws Exception the exception     
     */	
	public int count(Class<AppraisalCategories> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
