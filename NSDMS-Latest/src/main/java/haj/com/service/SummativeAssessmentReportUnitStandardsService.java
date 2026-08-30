package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.SummativeAssessmentReportUnitStandardsDAO;
import haj.com.entity.SummativeAssessmentReport;
import haj.com.entity.SummativeAssessmentReportUnitStandards;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractService;

public class SummativeAssessmentReportUnitStandardsService extends AbstractService {
	/** The dao. */
	private SummativeAssessmentReportUnitStandardsDAO dao = new SummativeAssessmentReportUnitStandardsDAO();

	/**
	 * Get all SummativeAssessmentReportUnitStandards
 	 * @author TechFinium 
 	 * @see   SummativeAssessmentReportUnitStandards
 	 * @return a list of {@link haj.com.entity.SummativeAssessmentReportUnitStandards}
	 * @throws Exception the exception
 	 */
	public List<SummativeAssessmentReportUnitStandards> allSummativeAssessmentReportUnitStandards() throws Exception {
	  	return dao.allSummativeAssessmentReportUnitStandards();
	}


	/**
	 * Create or update SummativeAssessmentReportUnitStandards.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SummativeAssessmentReportUnitStandards
	 */
	public void create(SummativeAssessmentReportUnitStandards entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SummativeAssessmentReportUnitStandards.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SummativeAssessmentReportUnitStandards
	 */
	public void update(SummativeAssessmentReportUnitStandards entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SummativeAssessmentReportUnitStandards.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SummativeAssessmentReportUnitStandards
	 */
	public void delete(SummativeAssessmentReportUnitStandards entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SummativeAssessmentReportUnitStandards}
	 * @throws Exception the exception
	 * @see    SummativeAssessmentReportUnitStandards
	 */
	public SummativeAssessmentReportUnitStandards findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SummativeAssessmentReportUnitStandards by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SummativeAssessmentReportUnitStandards}
	 * @throws Exception the exception
	 * @see    SummativeAssessmentReportUnitStandards
	 */
	public List<SummativeAssessmentReportUnitStandards> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	public List<SummativeAssessmentReportUnitStandards> findBySummativeAssessmentReport(Long summativeAssessmentReportId) throws Exception {
		return dao.findBySummativeAssessmentReport(summativeAssessmentReportId);
	}
	
	/**
	 * Lazy load SummativeAssessmentReportUnitStandards
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SummativeAssessmentReportUnitStandards}
	 * @throws Exception the exception
	 */
	public List<SummativeAssessmentReportUnitStandards> allSummativeAssessmentReportUnitStandards(int first, int pageSize) throws Exception {
		return dao.allSummativeAssessmentReportUnitStandards(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SummativeAssessmentReportUnitStandards for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the SummativeAssessmentReportUnitStandards
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SummativeAssessmentReportUnitStandards.class);
	}
	
    /**
     * Lazy load SummativeAssessmentReportUnitStandards with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 SummativeAssessmentReportUnitStandards class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SummativeAssessmentReportUnitStandards} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SummativeAssessmentReportUnitStandards> allSummativeAssessmentReportUnitStandards(Class<SummativeAssessmentReportUnitStandards> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SummativeAssessmentReportUnitStandards>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SummativeAssessmentReportUnitStandards for lazy load with filters
     * @author TechFinium 
     * @param entity SummativeAssessmentReportUnitStandards class
     * @param filters the filters
     * @return Number of rows in the SummativeAssessmentReportUnitStandards entity
     * @throws Exception the exception     
     */	
	public int count(Class<SummativeAssessmentReportUnitStandards> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public Long countUnitStandards(UnitStandards UnitStandards, SummativeAssessmentReport summativeAssessmentReport) throws Exception {
	       return dao.countUnitStandards(UnitStandards.getId(), summativeAssessmentReport.getId());
	}
}
