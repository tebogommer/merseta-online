package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.ProviderTypeDAO;
import haj.com.entity.lookup.ProviderType;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class ProviderTypeService.
 */
public class ProviderTypeService extends AbstractService {
	/** The dao. */
	private ProviderTypeDAO dao = new ProviderTypeDAO();

	/**
	 * Get all ProviderType.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.ProviderType}
	 * @throws Exception the exception
	 * @see   ProviderType
	 */
	public List<ProviderType> allProviderType() throws Exception {
	  	return dao.allProviderType();
	}

	public List<ProviderType> allProviderTypeNotWSP() throws Exception {
		return dao.allProviderTypeNotWSP();
	}
	
	public List<ProviderType> allProviderTypeForProviderRegistration() throws Exception {
		return dao.allProviderTypeForProviderRegistration();
	}

	/**
	 * Create or update ProviderType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     ProviderType
	 */
	public void create(ProviderType entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		   if (entity.getId() ==null) {
			 entity.setCreateDate(new java.util.Date());
			 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  ProviderType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ProviderType
	 */
	public void update(ProviderType entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  ProviderType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ProviderType
	 */
	public void delete(ProviderType entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ProviderType}
	 * @throws Exception the exception
	 * @see    ProviderType
	 */
	public ProviderType findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find ProviderType by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.ProviderType}
	 * @throws Exception the exception
	 * @see    ProviderType
	 */
	public List<ProviderType> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	public List<ProviderType> findByDescription(String desc) throws Exception {
		return dao.findByDescription(desc);
	}
	
	/**
	 * Lazy load ProviderType.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.ProviderType}
	 * @throws Exception the exception
	 */
	public List<ProviderType> allProviderType(int first, int pageSize) throws Exception {
		return dao.allProviderType(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of ProviderType for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the ProviderType
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(ProviderType.class);
	}
	
    /**
     * Lazy load ProviderType with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 ProviderType class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.ProviderType} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<ProviderType> allProviderType(Class<ProviderType> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<ProviderType>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of ProviderType for lazy load with filters.
     *
     * @author TechFinium
     * @param entity ProviderType class
     * @param filters the filters
     * @return Number of rows in the ProviderType entity
     * @throws Exception the exception
     */	
	public int count(Class<ProviderType> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
