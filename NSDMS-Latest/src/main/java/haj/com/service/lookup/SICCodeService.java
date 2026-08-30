package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.SICCodeDAO;
import haj.com.entity.lookup.SICCode;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class SICCodeService.
 */
public class SICCodeService extends AbstractService {
	/** The dao. */
	private SICCodeDAO dao = new SICCodeDAO();

	/**
	 * Get all SICCode.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.SICCode}
	 * @throws Exception the exception
	 * @see   SICCode
	 */
	public List<SICCode> allSICCode() throws Exception {
	  	return dao.allSICCode();
	}


	/**
	 * Create or update SICCode.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SICCode
	 */
	public void create(SICCode entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SICCode.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SICCode
	 */
	public void update(SICCode entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SICCode.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SICCode
	 */
	public void delete(SICCode entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SICCode}
	 * @throws Exception the exception
	 * @see    SICCode
	 */
	public SICCode findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SICCode by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SICCode}
	 * @throws Exception the exception
	 * @see    SICCode
	 */
	public List<SICCode> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SICCode.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SICCode}
	 * @throws Exception the exception
	 */
	public List<SICCode> allSICCode(int first, int pageSize) throws Exception {
		return dao.allSICCode(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SICCode for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the SICCode
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SICCode.class);
	}
	
    /**
     * Lazy load SICCode with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 SICCode class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SICCode} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SICCode> allSICCode(Class<SICCode> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SICCode>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SICCode for lazy load with filters.
     *
     * @author TechFinium
     * @param entity SICCode class
     * @param filters the filters
     * @return Number of rows in the SICCode entity
     * @throws Exception the exception
     */	
	public int count(Class<SICCode> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
    public SICCode findByCode(String code) throws Exception { 
    	  return dao.findByCode(code);
    }


	public SICCode findByCode(Map<String, SICCode> m, String code) throws Exception {
		if (m==null) return findByCode(code);
		else {
			if (m.containsKey(code)) return m.get(code);
			else {
				SICCode sc = findByCode(code);
				m.put(code, sc);
				return sc;
			}
		}
	}
}
