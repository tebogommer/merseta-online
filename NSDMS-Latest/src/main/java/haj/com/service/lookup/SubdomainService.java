package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.SubdomainDAO;
import haj.com.entity.lookup.Subdomain;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class SubdomainService.
 */
public class SubdomainService extends AbstractService {
	/** The dao. */
	private SubdomainDAO dao = new SubdomainDAO();

	/**
	 * Get all Subdomain.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Subdomain}
	 * @throws Exception the exception
	 * @see   Subdomain
	 */
	public List<Subdomain> allSubdomain() throws Exception {
	  	return dao.allSubdomain();
	}


	/**
	 * Create or update Subdomain.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Subdomain
	 */
	public void create(Subdomain entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  Subdomain.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Subdomain
	 */
	public void update(Subdomain entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Subdomain.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Subdomain
	 */
	public void delete(Subdomain entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Subdomain}
	 * @throws Exception the exception
	 * @see    Subdomain
	 */
	public Subdomain findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Subdomain by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Subdomain}
	 * @throws Exception the exception
	 * @see    Subdomain
	 */
	public List<Subdomain> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load Subdomain.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Subdomain}
	 * @throws Exception the exception
	 */
	public List<Subdomain> allSubdomain(int first, int pageSize) throws Exception {
		return dao.allSubdomain(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Subdomain for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Subdomain
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Subdomain.class);
	}
	
    /**
     * Lazy load Subdomain with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 Subdomain class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Subdomain} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Subdomain> allSubdomain(Class<Subdomain> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Subdomain>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of Subdomain for lazy load with filters.
     *
     * @author TechFinium
     * @param entity Subdomain class
     * @param filters the filters
     * @return Number of rows in the Subdomain entity
     * @throws Exception the exception
     */	
	public int count(Class<Subdomain> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
