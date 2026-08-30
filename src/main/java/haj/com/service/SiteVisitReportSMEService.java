package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.SiteVisitReportSMEDAO;
import haj.com.entity.SiteVisitReport;
import haj.com.entity.SiteVisitReportSME;
import haj.com.framework.AbstractService;

public class SiteVisitReportSMEService extends AbstractService {
	/** The dao. */
	private SiteVisitReportSMEDAO dao = new SiteVisitReportSMEDAO();
	private CompanyService companyService;

	/**
	 * Get all SiteVisitReportSME
 	 * @author TechFinium 
 	 * @see   SiteVisitReportSME
 	 * @return a list of {@link haj.com.entity.SiteVisitReportSME}
	 * @throws Exception the exception
 	 */
	public List<SiteVisitReportSME> allSiteVisitReportSME() throws Exception {
	  	return dao.allSiteVisitReportSME();
	}


	/**
	 * Create or update SiteVisitReportSME.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SiteVisitReportSME
	 */
	public void create(SiteVisitReportSME entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}
	
	/**
	 * Create or update SiteVisitReportSME.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SiteVisitReportSME
	 */
	public void createOnly(SiteVisitReportSME entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
	}


	/**
	 * Update  SiteVisitReportSME.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SiteVisitReportSME
	 */
	public void update(SiteVisitReportSME entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SiteVisitReportSME.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SiteVisitReportSME
	 */
	public void delete(SiteVisitReportSME entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SiteVisitReportSME}
	 * @throws Exception the exception
	 * @see    SiteVisitReportSME
	 */
	public SiteVisitReportSME findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SiteVisitReportSME by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SiteVisitReportSME}
	 * @throws Exception the exception
	 * @see    SiteVisitReportSME
	 */
	public List<SiteVisitReportSME> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	public List<SiteVisitReportSME> findBySiteVisitReport(SiteVisitReport siteVisitReport) throws Exception {
		return dao.findBySiteVisitReport(siteVisitReport);
	}
	
	/**
	 * Lazy load SiteVisitReportSME
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SiteVisitReportSME}
	 * @throws Exception the exception
	 */
	public List<SiteVisitReportSME> allSiteVisitReportSME(int first, int pageSize) throws Exception {
		return dao.allSiteVisitReportSME(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SiteVisitReportSME for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the SiteVisitReportSME
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SiteVisitReportSME.class);
	}
	
    /**
     * Lazy load SiteVisitReportSME with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 SiteVisitReportSME class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SiteVisitReportSME} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SiteVisitReportSME> allSiteVisitReportSME(Class<SiteVisitReportSME> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SiteVisitReportSME>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SiteVisitReportSME for lazy load with filters
     * @author TechFinium 
     * @param entity SiteVisitReportSME class
     * @param filters the filters
     * @return Number of rows in the SiteVisitReportSME entity
     * @throws Exception the exception     
     */	
	public int count(Class<SiteVisitReportSME> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
}
