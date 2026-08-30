package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.SocioeconomicStatusCodeDAO;
import haj.com.entity.lookup.SocioeconomicStatusCode;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class SocioeconomicStatusCodeService.
 */
public class SocioeconomicStatusCodeService extends AbstractService {
	/** The dao. */
	private SocioeconomicStatusCodeDAO dao = new SocioeconomicStatusCodeDAO();

	/**
	 * Get all SocioeconomicStatusCode.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.SocioeconomicStatusCode}
	 * @throws Exception the exception
	 * @see   SocioeconomicStatusCode
	 */
	public List<SocioeconomicStatusCode> allSocioeconomicStatusCode() throws Exception {
	  	return dao.allSocioeconomicStatusCode();
	}


	/**
	 * Create or update SocioeconomicStatusCode.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SocioeconomicStatusCode
	 */
	public void create(SocioeconomicStatusCode entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SocioeconomicStatusCode.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SocioeconomicStatusCode
	 */
	public void update(SocioeconomicStatusCode entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SocioeconomicStatusCode.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SocioeconomicStatusCode
	 */
	public void delete(SocioeconomicStatusCode entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SocioeconomicStatusCode}
	 * @throws Exception the exception
	 * @see    SocioeconomicStatusCode
	 */
	public SocioeconomicStatusCode findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SocioeconomicStatusCode by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SocioeconomicStatusCode}
	 * @throws Exception the exception
	 * @see    SocioeconomicStatusCode
	 */
	public List<SocioeconomicStatusCode> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SocioeconomicStatusCode.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SocioeconomicStatusCode}
	 * @throws Exception the exception
	 */
	public List<SocioeconomicStatusCode> allSocioeconomicStatusCode(int first, int pageSize) throws Exception {
		return dao.allSocioeconomicStatusCode(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SocioeconomicStatusCode for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the SocioeconomicStatusCode
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SocioeconomicStatusCode.class);
	}
	
    /**
     * Lazy load SocioeconomicStatusCode with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 SocioeconomicStatusCode class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SocioeconomicStatusCode} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SocioeconomicStatusCode> allSocioeconomicStatusCode(Class<SocioeconomicStatusCode> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SocioeconomicStatusCode>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SocioeconomicStatusCode for lazy load with filters.
     *
     * @author TechFinium
     * @param entity SocioeconomicStatusCode class
     * @param filters the filters
     * @return Number of rows in the SocioeconomicStatusCode entity
     * @throws Exception the exception
     */	
	public int count(Class<SocioeconomicStatusCode> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
