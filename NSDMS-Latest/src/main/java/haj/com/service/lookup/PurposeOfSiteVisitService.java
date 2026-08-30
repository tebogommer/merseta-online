package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.PurposeOfSiteVisitDAO;
import haj.com.entity.WorkplaceMonitoring;
import haj.com.entity.WorkplaceMonitoringPurposeOfSiteVisit;
import haj.com.entity.lookup.PurposeOfSiteVisit;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class PurposeOfSiteVisitService extends AbstractService {
	/** The dao. */
	private PurposeOfSiteVisitDAO dao = new PurposeOfSiteVisitDAO();

	/**
	 * Get all PurposeOfSiteVisit
 	 * @author TechFinium 
 	 * @see   PurposeOfSiteVisit
 	 * @return a list of {@link haj.com.entity.PurposeOfSiteVisit}
	 * @throws Exception the exception
 	 */
	public List<PurposeOfSiteVisit> allPurposeOfSiteVisit() throws Exception {
	  	return dao.allPurposeOfSiteVisit();
	}
	
	public List<PurposeOfSiteVisit> allPurposeOfSiteVisitByParent(PurposeOfSiteVisit purposeOfSiteVisit, String instatement) throws Exception {
		return dao.allPurposeOfSiteVisitByParent(purposeOfSiteVisit, instatement);
	}


	/**
	 * Create or update PurposeOfSiteVisit.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     PurposeOfSiteVisit
	 */
	public void create(PurposeOfSiteVisit entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  PurposeOfSiteVisit.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    PurposeOfSiteVisit
	 */
	public void update(PurposeOfSiteVisit entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  PurposeOfSiteVisit.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    PurposeOfSiteVisit
	 */
	public void delete(PurposeOfSiteVisit entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.PurposeOfSiteVisit}
	 * @throws Exception the exception
	 * @see    PurposeOfSiteVisit
	 */
	public PurposeOfSiteVisit findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find PurposeOfSiteVisit by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.PurposeOfSiteVisit}
	 * @throws Exception the exception
	 * @see    PurposeOfSiteVisit
	 */
	public List<PurposeOfSiteVisit> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	public List<PurposeOfSiteVisit> findByWorkplaceMonitoring(WorkplaceMonitoring workplaceMonitoring) throws Exception {
		return dao.findByWorkplaceMonitoring(workplaceMonitoring);
	}
	
	public List<WorkplaceMonitoringPurposeOfSiteVisit> findWorkplaceMonitoringPurposeOfSiteVisit(WorkplaceMonitoring workplaceMonitoring) throws Exception {
		return dao.findWorkplaceMonitoringPurposeOfSiteVisit(workplaceMonitoring);
	}
	
	/**
	 * Lazy load PurposeOfSiteVisit
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.PurposeOfSiteVisit}
	 * @throws Exception the exception
	 */
	public List<PurposeOfSiteVisit> allPurposeOfSiteVisit(int first, int pageSize) throws Exception {
		return dao.allPurposeOfSiteVisit(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of PurposeOfSiteVisit for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the PurposeOfSiteVisit
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(PurposeOfSiteVisit.class);
	}
	
    /**
     * Lazy load PurposeOfSiteVisit with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 PurposeOfSiteVisit class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.PurposeOfSiteVisit} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<PurposeOfSiteVisit> allPurposeOfSiteVisit(Class<PurposeOfSiteVisit> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<PurposeOfSiteVisit>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of PurposeOfSiteVisit for lazy load with filters
     * @author TechFinium 
     * @param entity PurposeOfSiteVisit class
     * @param filters the filters
     * @return Number of rows in the PurposeOfSiteVisit entity
     * @throws Exception the exception     
     */	
	public int count(Class<PurposeOfSiteVisit> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
