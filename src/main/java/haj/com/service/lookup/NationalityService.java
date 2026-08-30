package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.NationalityDAO;
import haj.com.entity.lookup.Nationality;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class NationalityService.
 */
public class NationalityService extends AbstractService {
	/** The dao. */
	private NationalityDAO dao = new NationalityDAO();

	/**
	 * Get all Nationality.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Nationality}
	 * @throws Exception the exception
	 * @see   Nationality
	 */
	public List<Nationality> allNationality() throws Exception {
	  	return dao.allNationality();
	}


	/**
	 * Create or update Nationality.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Nationality
	 */
	public void create(Nationality entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		   if (entity.getId() ==null) {
			 entity.setCreateDate(new java.util.Date());
			 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  Nationality.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Nationality
	 */
	public void update(Nationality entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Nationality.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Nationality
	 */
	public void delete(Nationality entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Nationality}
	 * @throws Exception the exception
	 * @see    Nationality
	 */
	public Nationality findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Nationality by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Nationality}
	 * @throws Exception the exception
	 * @see    Nationality
	 */
	public List<Nationality> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	public List<Nationality> findByNameExcludeNonSelectable(String description,Boolean canSelect) throws Exception {
		return dao.findByNameExcludeNonSelectable(description,canSelect);
	}
	
	/**
	 * Lazy load Nationality.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Nationality}
	 * @throws Exception the exception
	 */
	public List<Nationality> allNationality(int first, int pageSize) throws Exception {
		return dao.allNationality(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Nationality for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Nationality
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Nationality.class);
	}
	
    /**
     * Lazy load Nationality with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 Nationality class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Nationality} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Nationality> allNationality(Class<Nationality> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Nationality>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of Nationality for lazy load with filters.
     *
     * @author TechFinium
     * @param entity Nationality class
     * @param filters the filters
     * @return Number of rows in the Nationality entity
     * @throws Exception the exception
     */	
	public int count(Class<Nationality> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the nationality
	 * @throws Exception the exception
	 */
	public Nationality findByCode(String code) throws Exception { 
		if (StringUtils.isBlank(code)) return null;
		return dao.findByCode(code.trim());
	}
}
