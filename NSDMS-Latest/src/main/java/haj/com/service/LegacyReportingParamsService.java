package haj.com.service;

import java.util.List;

import haj.com.dao.LegacyReportingParamsDAO;
import haj.com.entity.LegacyReportingParams;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class LegacyReportingParamsService extends AbstractService {
	/** The dao. */
	private LegacyReportingParamsDAO dao = new LegacyReportingParamsDAO();

	/**
	 * Get all LegacyReportingParams
 	 * @author TechFinium 
 	 * @see   LegacyReportingParams
 	 * @return a list of {@link haj.com.entity.LegacyReportingParams}
	 * @throws Exception the exception
 	 */
	public List<LegacyReportingParams> allLegacyReportingParams() throws Exception {
	  	return dao.allLegacyReportingParams();
	}


	/**
	 * Create or update LegacyReportingParams.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     LegacyReportingParams
	 */
	public void create(LegacyReportingParams entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  LegacyReportingParams.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LegacyReportingParams
	 */
	public void update(LegacyReportingParams entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  LegacyReportingParams.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LegacyReportingParams
	 */
	public void delete(LegacyReportingParams entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LegacyReportingParams}
	 * @throws Exception the exception
	 * @see    LegacyReportingParams
	 */
	public LegacyReportingParams findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find LegacyReportingParams by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LegacyReportingParams}
	 * @throws Exception the exception
	 * @see    LegacyReportingParams
	 */
	public List<LegacyReportingParams> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load LegacyReportingParams
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyReportingParams}
	 * @throws Exception the exception
	 */
	public List<LegacyReportingParams> allLegacyReportingParams(int first, int pageSize) throws Exception {
		return dao.allLegacyReportingParams(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of LegacyReportingParams for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the LegacyReportingParams
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(LegacyReportingParams.class);
	}
	
    /**
     * Lazy load LegacyReportingParams with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 LegacyReportingParams class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.LegacyReportingParams} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<LegacyReportingParams> allLegacyReportingParams(Class<LegacyReportingParams> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<LegacyReportingParams>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of LegacyReportingParams for lazy load with filters
     * @author TechFinium 
     * @param entity LegacyReportingParams class
     * @param filters the filters
     * @return Number of rows in the LegacyReportingParams entity
     * @throws Exception the exception     
     */	
	public int count(Class<LegacyReportingParams> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
