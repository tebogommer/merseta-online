package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.DgVerificationSitesDAO;
import haj.com.entity.DgVerification;
import haj.com.entity.DgVerificationSites;
import haj.com.entity.Sites;
import haj.com.framework.AbstractService;

public class DgVerificationSitesService extends AbstractService {
	/** The dao. */
	private DgVerificationSitesDAO dao = new DgVerificationSitesDAO();

	/**
	 * Get all DgVerificationSites
 	 * @author TechFinium 
 	 * @see   DgVerificationSites
 	 * @return a list of {@link haj.com.entity.DgVerificationSites}
	 * @throws Exception the exception
 	 */
	public List<DgVerificationSites> allDgVerificationSites() throws Exception {
	  	return dao.allDgVerificationSites();
	}


	/**
	 * Create or update DgVerificationSites.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     DgVerificationSites
	 */
	public void create(DgVerificationSites entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  DgVerificationSites.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DgVerificationSites
	 */
	public void update(DgVerificationSites entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  DgVerificationSites.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DgVerificationSites
	 */
	public void delete(DgVerificationSites entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.DgVerificationSites}
	 * @throws Exception the exception
	 * @see    DgVerificationSites
	 */
	public DgVerificationSites findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find DgVerificationSites by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.DgVerificationSites}
	 * @throws Exception the exception
	 * @see    DgVerificationSites
	 */
	public List<DgVerificationSites> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load DgVerificationSites
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.DgVerificationSites}
	 * @throws Exception the exception
	 */
	public List<DgVerificationSites> allDgVerificationSites(int first, int pageSize) throws Exception {
		return dao.allDgVerificationSites(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of DgVerificationSites for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the DgVerificationSites
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(DgVerificationSites.class);
	}
	
    /**
     * Lazy load DgVerificationSites with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 DgVerificationSites class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.DgVerificationSites} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<DgVerificationSites> allDgVerificationSites(Class<DgVerificationSites> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<DgVerificationSites>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of DgVerificationSites for lazy load with filters
     * @author TechFinium 
     * @param entity DgVerificationSites class
     * @param filters the filters
     * @return Number of rows in the DgVerificationSites entity
     * @throws Exception the exception     
     */	
	public int count(Class<DgVerificationSites> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public List<DgVerificationSites> findDgAndSite(DgVerification dgVerification, Sites sites) throws Exception {
		return dao.findDgAndSite(dgVerification, sites);
	}
	
	public List<DgVerificationSites> findDgVerification(DgVerification dgVerification) throws Exception {
		return dao.findDgVerification(dgVerification);
	}
}
