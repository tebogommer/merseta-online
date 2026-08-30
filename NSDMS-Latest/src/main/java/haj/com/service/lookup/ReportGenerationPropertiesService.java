package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.ReportGenerationPropertiesDAO;
import haj.com.entity.enums.ReportPropertiesEnum;
import haj.com.entity.lookup.ReportGenerationProperties;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class ReportGenerationPropertiesService extends AbstractService {
	
	/** The dao. */
	private ReportGenerationPropertiesDAO dao = new ReportGenerationPropertiesDAO();

	/**
	 * Get all ReportGenerationProperties
 	 * @author TechFinium 
 	 * @see   ReportGenerationProperties
 	 * @return a list of {@link haj.com.entity.ReportGenerationProperties}
	 * @throws Exception the exception
 	 */
	public List<ReportGenerationProperties> allReportGenerationProperties() throws Exception {
	  	return dao.allReportGenerationProperties();
	}


	/**
	 * Create or update ReportGenerationProperties.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     ReportGenerationProperties
	 */
	public void create(ReportGenerationProperties entity) throws Exception  {
		checkIfPropertyUsed(entity);
		if (entity.getId() ==null) {
			this.dao.create(entity);
		} 
		else
			this.dao.update(entity);
	}


	/**
	 * Update  ReportGenerationProperties.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ReportGenerationProperties
	 */
	public void update(ReportGenerationProperties entity) throws Exception  {
		checkIfPropertyUsed(entity);
		this.dao.update(entity);
	}

	/**
	 * Delete  ReportGenerationProperties.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ReportGenerationProperties
	 */
	public void delete(ReportGenerationProperties entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ReportGenerationProperties}
	 * @throws Exception the exception
	 * @see    ReportGenerationProperties
	 */
	public ReportGenerationProperties findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find ReportGenerationProperties by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.ReportGenerationProperties}
	 * @throws Exception the exception
	 * @see    ReportGenerationProperties
	 */
	public List<ReportGenerationProperties> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load ReportGenerationProperties
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.ReportGenerationProperties}
	 * @throws Exception the exception
	 */
	public List<ReportGenerationProperties> allReportGenerationProperties(int first, int pageSize) throws Exception {
		return dao.allReportGenerationProperties(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of ReportGenerationProperties for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the ReportGenerationProperties
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(ReportGenerationProperties.class);
	}
	
    /**
     * Lazy load ReportGenerationProperties with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 ReportGenerationProperties class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.ReportGenerationProperties} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<ReportGenerationProperties> allReportGenerationProperties(Class<ReportGenerationProperties> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<ReportGenerationProperties>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of ReportGenerationProperties for lazy load with filters
     * @author TechFinium 
     * @param entity ReportGenerationProperties class
     * @param filters the filters
     * @return Number of rows in the ReportGenerationProperties entity
     * @throws Exception the exception     
     */	
	public int count(Class<ReportGenerationProperties> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	public ReportGenerationProperties findByReportProperty(ReportPropertiesEnum reportPropertiesEnum) throws Exception {
		return dao.findByReportProperty(reportPropertiesEnum);
	}
	
	public Integer countByReportProperty(ReportPropertiesEnum reportPropertiesEnum) throws Exception {
		return dao.countByReportProperty(reportPropertiesEnum);
	}
	
	public Integer countByReportPropertyAndNotId(ReportPropertiesEnum reportPropertiesEnum, Long id) throws Exception {
		return dao.countByReportPropertyAndNotId(reportPropertiesEnum, id);
	}
	
	public void checkIfPropertyUsed(ReportGenerationProperties reportGenerationProperties) throws Exception{
		if (reportGenerationProperties != null) {
			if (reportGenerationProperties.getReportProperty() == null) {
				throw new Exception("Assign a property before proceeding.");
			}
			if (reportGenerationProperties.getId() != null) {
				if (countByReportPropertyAndNotId(reportGenerationProperties.getReportProperty(),reportGenerationProperties.getId()) != 0) {
					throw new Exception("Property alredy assigned. Please assign a different property.");
				}
			} else {
				if (countByReportProperty(reportGenerationProperties.getReportProperty()) != 0) {
					throw new Exception("Property alredy assigned. Please assign a different property.");
				}
			}
		}
	}
	
	public void createBatch(List<IDataEntity> entityList) throws Exception {
		dao.createBatch(entityList);
	}
	
}
