package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.TradeTestCentersHistoricDAO;
import haj.com.entity.lookup.TradeTestCentersHistoric;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class TradeTestCentersHistoricService extends AbstractService {
	/** The dao. */
	private TradeTestCentersHistoricDAO dao = new TradeTestCentersHistoricDAO();

	/**
	 * Get all TradeTestCentersHistoric
 	 * @author TechFinium 
 	 * @see   TradeTestCentersHistoric
 	 * @return a list of {@link haj.com.entity.TradeTestCentersHistoric}
	 * @throws Exception the exception
 	 */
	public List<TradeTestCentersHistoric> allTradeTestCentersHistoric() throws Exception {
	  	return dao.allTradeTestCentersHistoric();
	}


	/**
	 * Create or update TradeTestCentersHistoric.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     TradeTestCentersHistoric
	 */
	public void create(TradeTestCentersHistoric entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  TradeTestCentersHistoric.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TradeTestCentersHistoric
	 */
	public void update(TradeTestCentersHistoric entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  TradeTestCentersHistoric.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TradeTestCentersHistoric
	 */
	public void delete(TradeTestCentersHistoric entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.TradeTestCentersHistoric}
	 * @throws Exception the exception
	 * @see    TradeTestCentersHistoric
	 */
	public TradeTestCentersHistoric findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find TradeTestCentersHistoric by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.TradeTestCentersHistoric}
	 * @throws Exception the exception
	 * @see    TradeTestCentersHistoric
	 */
	public List<TradeTestCentersHistoric> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load TradeTestCentersHistoric
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.TradeTestCentersHistoric}
	 * @throws Exception the exception
	 */
	public List<TradeTestCentersHistoric> allTradeTestCentersHistoric(int first, int pageSize) throws Exception {
		return dao.allTradeTestCentersHistoric(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of TradeTestCentersHistoric for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the TradeTestCentersHistoric
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(TradeTestCentersHistoric.class);
	}
	
    /**
     * Lazy load TradeTestCentersHistoric with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 TradeTestCentersHistoric class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.TradeTestCentersHistoric} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<TradeTestCentersHistoric> allTradeTestCentersHistoric(Class<TradeTestCentersHistoric> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<TradeTestCentersHistoric>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of TradeTestCentersHistoric for lazy load with filters
     * @author TechFinium 
     * @param entity TradeTestCentersHistoric class
     * @param filters the filters
     * @return Number of rows in the TradeTestCentersHistoric entity
     * @throws Exception the exception     
     */	
	public int count(Class<TradeTestCentersHistoric> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
