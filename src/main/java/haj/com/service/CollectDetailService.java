package haj.com.service;

import java.util.List;

import haj.com.dao.CollectDetailDAO;
import haj.com.entity.CollectDetail;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class CollectDetailService extends AbstractService {
	/** The dao. */
	private CollectDetailDAO dao = new CollectDetailDAO();

	/**
	 * Get all CollectDetail
 	 * @author TechFinium 
 	 * @see   CollectDetail
 	 * @return a list of {@link haj.com.entity.CollectDetail}
	 * @throws Exception the exception
 	 */
	public List<CollectDetail> allCollectDetail() throws Exception {
	  	return dao.allCollectDetail();
	}


	/**
	 * Create or update CollectDetail.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     CollectDetail
	 */
	public void create(CollectDetail entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  CollectDetail.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    CollectDetail
	 */
	public void update(CollectDetail entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  CollectDetail.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    CollectDetail
	 */
	public void delete(CollectDetail entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.CollectDetail}
	 * @throws Exception the exception
	 * @see    CollectDetail
	 */
	public CollectDetail findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find CollectDetail by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.CollectDetail}
	 * @throws Exception the exception
	 * @see    CollectDetail
	 */
	public List<CollectDetail> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load CollectDetail
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.CollectDetail}
	 * @throws Exception the exception
	 */
	public List<CollectDetail> allCollectDetail(int first, int pageSize) throws Exception {
		return dao.allCollectDetail(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of CollectDetail for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the CollectDetail
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(CollectDetail.class);
	}
	
    /**
     * Lazy load CollectDetail with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 CollectDetail class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.CollectDetail} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<CollectDetail> allCollectDetail(Class<CollectDetail> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<CollectDetail>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of CollectDetail for lazy load with filters
     * @author TechFinium 
     * @param entity CollectDetail class
     * @param filters the filters
     * @return Number of rows in the CollectDetail entity
     * @throws Exception the exception     
     */	
	public int count(Class<CollectDetail> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
