package haj.com.service.lookup;

import java.util.Hashtable;
import java.util.List;

import haj.com.dao.lookup.WspHistoricDataDAO;
import haj.com.entity.Wsp;
import haj.com.entity.lookup.WspHistoricData;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class WspHistoricDataService extends AbstractService {
	/** The dao. */
	private WspHistoricDataDAO dao = new WspHistoricDataDAO();

	/**
	 * Get all WspHistoricData
 	 * @author TechFinium 
 	 * @see   WspHistoricData
 	 * @return a list of {@link haj.com.entity.WspHistoricData}
	 * @throws Exception the exception
 	 */
	public List<WspHistoricData> allWspHistoricData() throws Exception {
	  	return dao.allWspHistoricData();
	}


	/**
	 * Create or update WspHistoricData.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     WspHistoricData
	 */
	public void create(WspHistoricData entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  WspHistoricData.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WspHistoricData
	 */
	public void update(WspHistoricData entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  WspHistoricData.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WspHistoricData
	 */
	public void delete(WspHistoricData entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WspHistoricData}
	 * @throws Exception the exception
	 * @see    WspHistoricData
	 */
	public WspHistoricData findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find WspHistoricData by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.WspHistoricData}
	 * @throws Exception the exception
	 * @see    WspHistoricData
	 */
	public List<WspHistoricData> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	public List<WspHistoricData> findByLevyNumber(String lNumber) throws Exception {
		return dao.findByLevyNumber(lNumber);
	}
	
	public List<WspHistoricData> findByFinancialYear(Integer year) throws Exception {
		return dao.findByFinancialYear(year);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspHistoricData> findByYearRange(Integer fromYear,Integer toYear) throws Exception {
		return dao.findByYearRange(fromYear,toYear);
	}
	

	
	
	
	
	
	
	/**
	 * Lazy load WspHistoricData
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.WspHistoricData}
	 * @throws Exception the exception
	 */
	public List<WspHistoricData> allWspHistoricData(int first, int pageSize) throws Exception {
		return dao.allWspHistoricData(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of WspHistoricData for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the WspHistoricData
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(WspHistoricData.class);
	}
	
    /**
     * Lazy load WspHistoricData with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 WspHistoricData class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.WspHistoricData} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<WspHistoricData> allWspHistoricData(Class<WspHistoricData> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<WspHistoricData>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of WspHistoricData for lazy load with filters
     * @author TechFinium 
     * @param entity WspHistoricData class
     * @param filters the filters
     * @return Number of rows in the WspHistoricData entity
     * @throws Exception the exception     
     */	
	public int count(Class<WspHistoricData> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
