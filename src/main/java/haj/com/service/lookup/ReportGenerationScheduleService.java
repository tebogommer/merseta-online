package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.ReportGenerationScheduleDAO;
import haj.com.entity.lookup.ReportGenerationSchedule;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class ReportGenerationScheduleService extends AbstractService {
	/** The dao. */
	private ReportGenerationScheduleDAO dao = new ReportGenerationScheduleDAO();

	/**
	 * Get all ReportGenerationSchedule
 	 * @author TechFinium 
 	 * @see   ReportGenerationSchedule
 	 * @return a list of {@link haj.com.entity.ReportGenerationSchedule}
	 * @throws Exception the exception
 	 */
	public List<ReportGenerationSchedule> allReportGenerationSchedule() throws Exception {
	  	return dao.allReportGenerationSchedule();
	}


	/**
	 * Create or update ReportGenerationSchedule.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     ReportGenerationSchedule
	 */
	public void create(ReportGenerationSchedule entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  ReportGenerationSchedule.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ReportGenerationSchedule
	 */
	public void update(ReportGenerationSchedule entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  ReportGenerationSchedule.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ReportGenerationSchedule
	 */
	public void delete(ReportGenerationSchedule entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ReportGenerationSchedule}
	 * @throws Exception the exception
	 * @see    ReportGenerationSchedule
	 */
	public ReportGenerationSchedule findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find ReportGenerationSchedule by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.ReportGenerationSchedule}
	 * @throws Exception the exception
	 * @see    ReportGenerationSchedule
	 */
	public List<ReportGenerationSchedule> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load ReportGenerationSchedule
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.ReportGenerationSchedule}
	 * @throws Exception the exception
	 */
	public List<ReportGenerationSchedule> allReportGenerationSchedule(int first, int pageSize) throws Exception {
		return dao.allReportGenerationSchedule(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of ReportGenerationSchedule for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the ReportGenerationSchedule
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(ReportGenerationSchedule.class);
	}
	
    /**
     * Lazy load ReportGenerationSchedule with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 ReportGenerationSchedule class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.ReportGenerationSchedule} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<ReportGenerationSchedule> allReportGenerationSchedule(Class<ReportGenerationSchedule> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<ReportGenerationSchedule>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of ReportGenerationSchedule for lazy load with filters
     * @author TechFinium 
     * @param entity ReportGenerationSchedule class
     * @param filters the filters
     * @return Number of rows in the ReportGenerationSchedule entity
     * @throws Exception the exception     
     */	
	public int count(Class<ReportGenerationSchedule> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
