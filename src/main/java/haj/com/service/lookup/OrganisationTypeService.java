package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.OrganisationTypeDAO;
import haj.com.entity.lookup.OrganisationType;
import haj.com.framework.AbstractService;

public class OrganisationTypeService extends AbstractService {
	/** The dao. */
	private OrganisationTypeDAO dao = new OrganisationTypeDAO();

	/**
	 * Get all OrganisationType
 	 * @author TechFinium 
 	 * @see   OrganisationType
 	 * @return a list of {@link haj.com.entity.OrganisationType}
	 * @throws Exception the exception
 	 */
	public List<OrganisationType> allOrganisationType() throws Exception {
	  	return dao.allOrganisationType();
	}


	/**
	 * Create or update OrganisationType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     OrganisationType
	 */
	public void create(OrganisationType entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  OrganisationType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    OrganisationType
	 */
	public void update(OrganisationType entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  OrganisationType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    OrganisationType
	 */
	public void delete(OrganisationType entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.OrganisationType}
	 * @throws Exception the exception
	 * @see    OrganisationType
	 */
	public OrganisationType findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find OrganisationType by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.OrganisationType}
	 * @throws Exception the exception
	 * @see    OrganisationType
	 */
	public List<OrganisationType> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load OrganisationType
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.OrganisationType}
	 * @throws Exception the exception
	 */
	public List<OrganisationType> allOrganisationType(int first, int pageSize) throws Exception {
		return dao.allOrganisationType(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of OrganisationType for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the OrganisationType
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(OrganisationType.class);
	}
	
    /**
     * Lazy load OrganisationType with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 OrganisationType class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.OrganisationType} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<OrganisationType> allOrganisationType(Class<OrganisationType> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<OrganisationType>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of OrganisationType for lazy load with filters
     * @author TechFinium 
     * @param entity OrganisationType class
     * @param filters the filters
     * @return Number of rows in the OrganisationType entity
     * @throws Exception the exception     
     */	
	public int count(Class<OrganisationType> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
