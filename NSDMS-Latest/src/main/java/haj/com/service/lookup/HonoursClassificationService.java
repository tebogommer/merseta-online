package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.HonoursClassificationDAO;
import haj.com.entity.lookup.HonoursClassification;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class HonoursClassificationService.
 */
public class HonoursClassificationService extends AbstractService {
	/** The dao. */
	private HonoursClassificationDAO dao = new HonoursClassificationDAO();

	/**
	 * Get all HonoursClassification.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.HonoursClassification}
	 * @throws Exception the exception
	 * @see   HonoursClassification
	 */
	public List<HonoursClassification> allHonoursClassification() throws Exception {
	  	return dao.allHonoursClassification();
	}


	/**
	 * Create or update HonoursClassification.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     HonoursClassification
	 */
	public void create(HonoursClassification entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		   if (entity.getId() ==null) {
			 entity.setCreateDate(new java.util.Date());
			 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  HonoursClassification.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    HonoursClassification
	 */
	public void update(HonoursClassification entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  HonoursClassification.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    HonoursClassification
	 */
	public void delete(HonoursClassification entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.HonoursClassification}
	 * @throws Exception the exception
	 * @see    HonoursClassification
	 */
	public HonoursClassification findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find HonoursClassification by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.HonoursClassification}
	 * @throws Exception the exception
	 * @see    HonoursClassification
	 */
	public List<HonoursClassification> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load HonoursClassification.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.HonoursClassification}
	 * @throws Exception the exception
	 */
	public List<HonoursClassification> allHonoursClassification(int first, int pageSize) throws Exception {
		return dao.allHonoursClassification(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of HonoursClassification for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the HonoursClassification
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(HonoursClassification.class);
	}
	
    /**
     * Lazy load HonoursClassification with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 HonoursClassification class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.HonoursClassification} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<HonoursClassification> allHonoursClassification(Class<HonoursClassification> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<HonoursClassification>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of HonoursClassification for lazy load with filters.
     *
     * @author TechFinium
     * @param entity HonoursClassification class
     * @param filters the filters
     * @return Number of rows in the HonoursClassification entity
     * @throws Exception the exception
     */	
	public int count(Class<HonoursClassification> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
