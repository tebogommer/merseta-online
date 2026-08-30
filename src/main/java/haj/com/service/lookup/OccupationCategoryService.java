package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.OccupationCategoryDAO;
import haj.com.entity.lookup.OccupationCategory;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class OccupationCategoryService.
 */
public class OccupationCategoryService extends AbstractService {
	/** The dao. */
	private OccupationCategoryDAO dao = new OccupationCategoryDAO();

	/**
	 * Get all OccupationCategory.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.OccupationCategory}
	 * @throws Exception the exception
	 * @see   OccupationCategory
	 */
	public List<OccupationCategory> allOccupationCategory() throws Exception {
	  	return dao.allOccupationCategory();
	}


	/**
	 * Create or update OccupationCategory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     OccupationCategory
	 */
	public void create(OccupationCategory entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		   if (entity.getId() ==null) {
			 entity.setCreateDate(new java.util.Date());
			 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  OccupationCategory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    OccupationCategory
	 */
	public void update(OccupationCategory entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  OccupationCategory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    OccupationCategory
	 */
	public void delete(OccupationCategory entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.OccupationCategory}
	 * @throws Exception the exception
	 * @see    OccupationCategory
	 */
	public OccupationCategory findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find OccupationCategory by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.OccupationCategory}
	 * @throws Exception the exception
	 * @see    OccupationCategory
	 */
	public List<OccupationCategory> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load OccupationCategory.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.OccupationCategory}
	 * @throws Exception the exception
	 */
	public List<OccupationCategory> allOccupationCategory(int first, int pageSize) throws Exception {
		return dao.allOccupationCategory(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of OccupationCategory for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the OccupationCategory
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(OccupationCategory.class);
	}
	
    /**
     * Lazy load OccupationCategory with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 OccupationCategory class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.OccupationCategory} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<OccupationCategory> allOccupationCategory(Class<OccupationCategory> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<OccupationCategory>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of OccupationCategory for lazy load with filters.
     *
     * @author TechFinium
     * @param entity OccupationCategory class
     * @param filters the filters
     * @return Number of rows in the OccupationCategory entity
     * @throws Exception the exception
     */	
	public int count(Class<OccupationCategory> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the occupation category
	 * @throws Exception the exception
	 */
	public OccupationCategory findByCode(String code) throws Exception { 
		if (StringUtils.isBlank(code)) return null;
		return dao.findByCode(code.trim());
	}
}
