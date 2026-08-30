package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.SiteVisitReportDisputeDAO;
import haj.com.entity.SiteVisitReport;
import haj.com.entity.SiteVisitReportDispute;
import haj.com.framework.AbstractService;

public class SiteVisitReportDisputeService extends AbstractService {
	/** The dao. */
	private SiteVisitReportDisputeDAO dao = new SiteVisitReportDisputeDAO();

	/**
	 * Get all SiteVisitReportDispute
 	 * @author TechFinium 
 	 * @see   SiteVisitReportDispute
 	 * @return a list of {@link haj.com.entity.SiteVisitReportDispute}
	 * @throws Exception the exception
 	 */
	public List<SiteVisitReportDispute> allSiteVisitReportDispute() throws Exception {
	  	return dao.allSiteVisitReportDispute();
	}


	/**
	 * Create or update SiteVisitReportDispute.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SiteVisitReportDispute
	 */
	public void create(SiteVisitReportDispute entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SiteVisitReportDispute.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SiteVisitReportDispute
	 */
	public void update(SiteVisitReportDispute entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SiteVisitReportDispute.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SiteVisitReportDispute
	 */
	public void delete(SiteVisitReportDispute entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SiteVisitReportDispute}
	 * @throws Exception the exception
	 * @see    SiteVisitReportDispute
	 */
	public SiteVisitReportDispute findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}
	
	public List<SiteVisitReportDispute> findBySiteVisitReport(SiteVisitReport siteVisitReport) throws Exception {
		return dao.findBySiteVisitReport(siteVisitReport);
	}

	/**
	 * Find SiteVisitReportDispute by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SiteVisitReportDispute}
	 * @throws Exception the exception
	 * @see    SiteVisitReportDispute
	 */
	public List<SiteVisitReportDispute> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SiteVisitReportDispute
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SiteVisitReportDispute}
	 * @throws Exception the exception
	 */
	public List<SiteVisitReportDispute> allSiteVisitReportDispute(int first, int pageSize) throws Exception {
		return dao.allSiteVisitReportDispute(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SiteVisitReportDispute for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the SiteVisitReportDispute
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SiteVisitReportDispute.class);
	}
	
    /**
     * Lazy load SiteVisitReportDispute with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 SiteVisitReportDispute class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SiteVisitReportDispute} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SiteVisitReportDispute> allSiteVisitReportDispute(Class<SiteVisitReportDispute> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SiteVisitReportDispute>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SiteVisitReportDispute for lazy load with filters
     * @author TechFinium 
     * @param entity SiteVisitReportDispute class
     * @param filters the filters
     * @return Number of rows in the SiteVisitReportDispute entity
     * @throws Exception the exception     
     */	
	public int count(Class<SiteVisitReportDispute> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	
}
