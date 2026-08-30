package haj.com.service.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.ProviderClassDAO;
import haj.com.entity.lookup.ProviderClass;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class ProviderClassService.
 */
public class ProviderClassService extends AbstractService {
	/** The dao. */
	private ProviderClassDAO dao = new ProviderClassDAO();

	/**
	 * Get all ProviderClass.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.ProviderClass}
	 * @throws Exception the exception
	 * @see   ProviderClass
	 */
	public List<ProviderClass> allProviderClass() throws Exception {
	  	return dao.allProviderClass();
	}


	/**
	 * Create or update ProviderClass.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     ProviderClass
	 */
	public void create(ProviderClass entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  ProviderClass.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ProviderClass
	 */
	public void update(ProviderClass entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  ProviderClass.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ProviderClass
	 */
	public void delete(ProviderClass entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ProviderClass}
	 * @throws Exception the exception
	 * @see    ProviderClass
	 */
	public ProviderClass findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find ProviderClass by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.ProviderClass}
	 * @throws Exception the exception
	 * @see    ProviderClass
	 */
	public List<ProviderClass> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	@SuppressWarnings("unchecked")
	public List<ProviderClass> findByDescription(String description) throws Exception {
	 	return dao.findByDescription(description);
	}
	
	/**
	 * Lazy load ProviderClass.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.ProviderClass}
	 * @throws Exception the exception
	 */
	public List<ProviderClass> allProviderClass(int first, int pageSize) throws Exception {
		return dao.allProviderClass(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of ProviderClass for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the ProviderClass
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(ProviderClass.class);
	}
	
    /**
     * Lazy load ProviderClass with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 ProviderClass class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.ProviderClass} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<ProviderClass> allProviderClass(Class<ProviderClass> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<ProviderClass>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of ProviderClass for lazy load with filters.
     *
     * @author TechFinium
     * @param entity ProviderClass class
     * @param filters the filters
     * @return Number of rows in the ProviderClass entity
     * @throws Exception the exception
     */	
	public int count(Class<ProviderClass> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
    public List<ProviderClass> allProviderClassExUnknownAndInterrim() throws Exception {
        return dao.allProviderClassExUnknownAndInterrim();
    }

}
