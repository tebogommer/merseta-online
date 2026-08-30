package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.NqfLevelsDAO;
import haj.com.entity.lookup.NqfLevels;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class NqfLevelsService.
 */
public class NqfLevelsService extends AbstractService {

	/** The dao. */
	private NqfLevelsDAO dao = new NqfLevelsDAO();
	
	private static NqfLevelsService nqfLevelsService;
	
	public static synchronized NqfLevelsService instance() {
		if (nqfLevelsService == null) {
			nqfLevelsService = new NqfLevelsService();
		}
		return nqfLevelsService;
	}

	/**
	 * Get all NqfLevels.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.lookup.NqfLevels}
	 * @throws Exception the exception
	 * @see    NqfLevels
	 */
	public List<NqfLevels> allNqfLevels() throws Exception {
	  	return dao.allNqfLevels();
	}
	
	/**
	 * Find nqf levels autocomplete.
	 *
	 * @param desc the desc
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<NqfLevels> findNqfLevelsAutocomplete(String desc) throws Exception {
	  	return dao.findNqfLevelsAutocomplete(desc);
	}

	/**
	 * Create or update  NqfLevels.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    NqfLevels
	 */
	public void create(NqfLevels entity) throws Exception  {
	   
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		
		if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}

	/**
	 * Update  NqfLevels.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    NqfLevels
	 */
	public void update(NqfLevels entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  NqfLevels.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    NqfLevels
	 */
	public void delete(NqfLevels entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a NqfLevels object
	 * @throws Exception the exception
	 * @see    NqfLevels
	 */
	public NqfLevels findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find NqfLevels by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.lookup.NqfLevels}
	 * @throws Exception the exception
	 * @see    NqfLevels
	 */
	public List<NqfLevels> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load NqfLevels.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.lookup.NqfLevels}
	 * @throws Exception the exception
	 */
	public List<NqfLevels> allNqfLevels(int first, int pageSize) throws Exception {
		return dao.allNqfLevels(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Count.
	 *
	 * @author TechFinium
	 * @return Number of rows in the NqfLevels
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(NqfLevels.class);
	}
	
    /**
     * All nqf levels.
     *
     * @author TechFinium
     * @param class1 NqfLevels class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return a list of {@link haj.com.entity.lookup.NqfLevels} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<NqfLevels> allNqfLevels(Class<NqfLevels> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<NqfLevels>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Count.
     *
     * @param entity NqfLevels class
     * @param filters the filters
     * @return Number of rows in the NqfLevels entity
     * @throws Exception the exception
     */	
	public int count(Class<NqfLevels> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	
	/**
	 * Find by level.
	 *
	 * @param id the id
	 * @return the nqf levels
	 * @throws Exception the exception
	 */
	public NqfLevels findByLevel(Integer id) throws Exception { 
		if (id==null) return null;
		return dao.findByLevel(id);
	}
	
	public NqfLevels findBySaqaDescription(String saqaDescription) throws Exception {
		return dao.findBySaqaDescription(saqaDescription);
	}
	
	public NqfLevels findByCode(String code) throws Exception {
		return dao.findByCode(code);
	}
	
	public NqfLevels findByPre2009Description(String pre2009Description) throws Exception {
		return dao.findByPre2009Description(pre2009Description);
	}
}