package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.OfficeDAO;
import haj.com.entity.lookup.Office;
import haj.com.framework.AbstractService;

public class OfficeService extends AbstractService {
	/** The dao. */
	private OfficeDAO dao = new OfficeDAO();

	/**
	 * Get all Office
 	 * @author TechFinium 
 	 * @see   Office
 	 * @return a list of {@link haj.com.entity.Office}
	 * @throws Exception the exception
 	 */
	public List<Office> allOffice() throws Exception {
	  	return dao.allOffice();
	}


	/**
	 * Create or update Office.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Office
	 */
	public void create(Office entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  Office.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Office
	 */
	public void update(Office entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Office.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Office
	 */
	public void delete(Office entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Office}
	 * @throws Exception the exception
	 * @see    Office
	 */
	public Office findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Office by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Office}
	 * @throws Exception the exception
	 * @see    Office
	 */
	public List<Office> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load Office
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Office}
	 * @throws Exception the exception
	 */
	public List<Office> allOffice(int first, int pageSize) throws Exception {
		return dao.allOffice(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Office for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the Office
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Office.class);
	}
	
    /**
     * Lazy load Office with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 Office class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Office} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Office> allOffice(Class<Office> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Office>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	}
	
    /**
     * Get count of Office for lazy load with filters
     * @author TechFinium 
     * @param entity Office class
     * @param filters the filters
     * @return Number of rows in the Office entity
     * @throws Exception the exception     
     */	
	public int count(Class<Office> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/**
     * Lazy load Office with pagination, filter, hql, sorting
     * @author TechFinium 
     * @param class1 Office class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Office} containing data
     * @throws Exception the exception
     */	
	public List<Office> allOfficeHql(Class<Office> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<Office>)dao.allOfficeHql(class1, first, pageSize, sortField, sortOrder, filters);
	}
	
	/**
     * Get count of Office for lazy load with filters
     * @author TechFinium 
     * @param entity Office class
     * @param filters the filters
     * @return Number of rows in the Office entity
     * @throws Exception the exception     
     */	
	public int countHql(Class<Office> entity, Map<String, Object> filters) throws Exception {
		return dao.countHql(entity, filters);
	}
	
	public List<Office> allOfficesByRegionId(Long regionId) throws Exception {
		return dao.allOfficesByRegionId(regionId);
	}
}
