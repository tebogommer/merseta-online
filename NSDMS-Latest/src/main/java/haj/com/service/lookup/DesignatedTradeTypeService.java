package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.DesignatedTradeTypeDAO;
import haj.com.entity.lookup.DesignatedTradeType;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class DesignatedTradeTypeService extends AbstractService {
	/** The dao. */
	private DesignatedTradeTypeDAO dao = new DesignatedTradeTypeDAO();

	/**
	 * Get all DesignatedTradeType
 	 * @author TechFinium 
 	 * @see   DesignatedTradeType
 	 * @return a list of {@link haj.com.entity.DesignatedTradeType}
	 * @throws Exception the exception
 	 */
	public List<DesignatedTradeType> allDesignatedTradeType() throws Exception {
	  	return dao.allDesignatedTradeType();
	}


	/**
	 * Create or update DesignatedTradeType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     DesignatedTradeType
	 */
	public void create(DesignatedTradeType entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  DesignatedTradeType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DesignatedTradeType
	 */
	public void update(DesignatedTradeType entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  DesignatedTradeType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DesignatedTradeType
	 */
	public void delete(DesignatedTradeType entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.DesignatedTradeType}
	 * @throws Exception the exception
	 * @see    DesignatedTradeType
	 */
	public DesignatedTradeType findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find DesignatedTradeType by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.DesignatedTradeType}
	 * @throws Exception the exception
	 * @see    DesignatedTradeType
	 */
	public List<DesignatedTradeType> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load DesignatedTradeType
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.DesignatedTradeType}
	 * @throws Exception the exception
	 */
	public List<DesignatedTradeType> allDesignatedTradeType(int first, int pageSize) throws Exception {
		return dao.allDesignatedTradeType(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of DesignatedTradeType for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the DesignatedTradeType
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(DesignatedTradeType.class);
	}
	
    /**
     * Lazy load DesignatedTradeType with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 DesignatedTradeType class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.DesignatedTradeType} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<DesignatedTradeType> allDesignatedTradeType(Class<DesignatedTradeType> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<DesignatedTradeType>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of DesignatedTradeType for lazy load with filters
     * @author TechFinium 
     * @param entity DesignatedTradeType class
     * @param filters the filters
     * @return Number of rows in the DesignatedTradeType entity
     * @throws Exception the exception     
     */	
	public int count(Class<DesignatedTradeType> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
