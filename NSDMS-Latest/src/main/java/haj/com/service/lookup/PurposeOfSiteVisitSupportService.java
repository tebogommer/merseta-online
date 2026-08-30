package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.PurposeOfSiteVisitSupportDAO;
import haj.com.entity.lookup.PurposeOfSiteVisitSupport;
import haj.com.framework.AbstractService;

public class PurposeOfSiteVisitSupportService extends AbstractService {
	/** The dao. */
	private PurposeOfSiteVisitSupportDAO dao = new PurposeOfSiteVisitSupportDAO();

	/**
	 * Get all PurposeOfSiteVisitSupport
 	 * @author TechFinium 
 	 * @see   PurposeOfSiteVisitSupport
 	 * @return a list of {@link haj.com.entity.PurposeOfSiteVisitSupport}
	 * @throws Exception the exception
 	 */
	public List<PurposeOfSiteVisitSupport> allPurposeOfSiteVisitSupport() throws Exception {
	  	return dao.allPurposeOfSiteVisitSupport();
	}


	/**
	 * Create or update PurposeOfSiteVisitSupport.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     PurposeOfSiteVisitSupport
	 */
	public void create(PurposeOfSiteVisitSupport entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  PurposeOfSiteVisitSupport.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    PurposeOfSiteVisitSupport
	 */
	public void update(PurposeOfSiteVisitSupport entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  PurposeOfSiteVisitSupport.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    PurposeOfSiteVisitSupport
	 */
	public void delete(PurposeOfSiteVisitSupport entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.PurposeOfSiteVisitSupport}
	 * @throws Exception the exception
	 * @see    PurposeOfSiteVisitSupport
	 */
	public PurposeOfSiteVisitSupport findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find PurposeOfSiteVisitSupport by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.PurposeOfSiteVisitSupport}
	 * @throws Exception the exception
	 * @see    PurposeOfSiteVisitSupport
	 */
	public List<PurposeOfSiteVisitSupport> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load PurposeOfSiteVisitSupport
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.PurposeOfSiteVisitSupport}
	 * @throws Exception the exception
	 */
	public List<PurposeOfSiteVisitSupport> allPurposeOfSiteVisitSupport(int first, int pageSize) throws Exception {
		return dao.allPurposeOfSiteVisitSupport(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of PurposeOfSiteVisitSupport for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the PurposeOfSiteVisitSupport
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(PurposeOfSiteVisitSupport.class);
	}
	
    /**
     * Lazy load PurposeOfSiteVisitSupport with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 PurposeOfSiteVisitSupport class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.PurposeOfSiteVisitSupport} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<PurposeOfSiteVisitSupport> allPurposeOfSiteVisitSupport(Class<PurposeOfSiteVisitSupport> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<PurposeOfSiteVisitSupport>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of PurposeOfSiteVisitSupport for lazy load with filters
     * @author TechFinium 
     * @param entity PurposeOfSiteVisitSupport class
     * @param filters the filters
     * @return Number of rows in the PurposeOfSiteVisitSupport entity
     * @throws Exception the exception     
     */	
	public int count(Class<PurposeOfSiteVisitSupport> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
