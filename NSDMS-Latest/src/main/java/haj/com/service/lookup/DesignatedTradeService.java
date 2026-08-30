package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.DesignatedTradeDAO;
import haj.com.entity.lookup.DesignatedTrade;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class DesignatedTradeService extends AbstractService {
	/** The dao. */
	private DesignatedTradeDAO dao = new DesignatedTradeDAO();

	/**
	 * Get all DesignatedTrade
 	 * @author TechFinium 
 	 * @see   DesignatedTrade
 	 * @return a list of {@link haj.com.entity.DesignatedTrade}
	 * @throws Exception the exception
 	 */
	public List<DesignatedTrade> allDesignatedTrade() throws Exception {
	  	return dao.allDesignatedTrade();
	}


	/**
	 * Create or update DesignatedTrade.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     DesignatedTrade
	 */
	public void create(DesignatedTrade entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  DesignatedTrade.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DesignatedTrade
	 */
	public void update(DesignatedTrade entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  DesignatedTrade.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DesignatedTrade
	 */
	public void delete(DesignatedTrade entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.DesignatedTrade}
	 * @throws Exception the exception
	 * @see    DesignatedTrade
	 */
	public DesignatedTrade findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find DesignatedTrade by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.DesignatedTrade}
	 * @throws Exception the exception
	 * @see    DesignatedTrade
	 */
	public List<DesignatedTrade> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load DesignatedTrade
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.DesignatedTrade}
	 * @throws Exception the exception
	 */
	public List<DesignatedTrade> allDesignatedTrade(int first, int pageSize) throws Exception {
		return dao.allDesignatedTrade(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of DesignatedTrade for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the DesignatedTrade
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(DesignatedTrade.class);
	}
	
    /**
     * Lazy load DesignatedTrade with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 DesignatedTrade class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.DesignatedTrade} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<DesignatedTrade> allDesignatedTrade(Class<DesignatedTrade> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<DesignatedTrade>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of DesignatedTrade for lazy load with filters
     * @author TechFinium 
     * @param entity DesignatedTrade class
     * @param filters the filters
     * @return Number of rows in the DesignatedTrade entity
     * @throws Exception the exception     
     */	
	public int count(Class<DesignatedTrade> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
