package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.AlternativeIdTypeDAO;
import haj.com.entity.lookup.AlternativeIdType;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class AlternativeIdTypeService.
 */
public class AlternativeIdTypeService extends AbstractService {
	/** The dao. */
	private AlternativeIdTypeDAO dao = new AlternativeIdTypeDAO();

	/**
	 * Get all AlternativeIdType.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.AlternativeIdType}
	 * @throws Exception the exception
	 * @see   AlternativeIdType
	 */
	public List<AlternativeIdType> allAlternativeIdType() throws Exception {
	  	return dao.allAlternativeIdType();
	}


	/**
	 * Create or update AlternativeIdType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     AlternativeIdType
	 */
	public void create(AlternativeIdType entity) throws Exception  {
	 
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		
		if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  AlternativeIdType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    AlternativeIdType
	 */
	public void update(AlternativeIdType entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  AlternativeIdType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    AlternativeIdType
	 */
	public void delete(AlternativeIdType entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.AlternativeIdType}
	 * @throws Exception the exception
	 * @see    AlternativeIdType
	 */
	public AlternativeIdType findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find AlternativeIdType by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.AlternativeIdType}
	 * @throws Exception the exception
	 * @see    AlternativeIdType
	 */
	public List<AlternativeIdType> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load AlternativeIdType.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.AlternativeIdType}
	 * @throws Exception the exception
	 */
	public List<AlternativeIdType> allAlternativeIdType(int first, int pageSize) throws Exception {
		return dao.allAlternativeIdType(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of AlternativeIdType for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the AlternativeIdType
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(AlternativeIdType.class);
	}
	
    /**
     * Lazy load AlternativeIdType with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 AlternativeIdType class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.AlternativeIdType} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<AlternativeIdType> allAlternativeIdType(Class<AlternativeIdType> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<AlternativeIdType>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of AlternativeIdType for lazy load with filters.
     *
     * @author TechFinium
     * @param entity AlternativeIdType class
     * @param filters the filters
     * @return Number of rows in the AlternativeIdType entity
     * @throws Exception the exception
     */	
	public int count(Class<AlternativeIdType> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
