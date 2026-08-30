package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.InstitutionDAO;
import haj.com.entity.lookup.Institution;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class InstitutionService.
 */
public class InstitutionService extends AbstractService {
	/** The dao. */
	private InstitutionDAO dao = new InstitutionDAO();

	/**
	 * Get all Institution.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Institution}
	 * @throws Exception the exception
	 * @see   Institution
	 */
	public List<Institution> allInstitution() throws Exception {
	  	return dao.allInstitution();
	}


	/**
	 * Create or update Institution.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Institution
	 */
	public void create(Institution entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  Institution.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Institution
	 */
	public void update(Institution entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Institution.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Institution
	 */
	public void delete(Institution entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Institution}
	 * @throws Exception the exception
	 * @see    Institution
	 */
	public Institution findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Institution by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Institution}
	 * @throws Exception the exception
	 * @see    Institution
	 */
	public List<Institution> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load Institution.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Institution}
	 * @throws Exception the exception
	 */
	public List<Institution> allInstitution(int first, int pageSize) throws Exception {
		return dao.allInstitution(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Institution for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Institution
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Institution.class);
	}
	
    /**
     * Lazy load Institution with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 Institution class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Institution} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Institution> allInstitution(Class<Institution> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Institution>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of Institution for lazy load with filters.
     *
     * @author TechFinium
     * @param entity Institution class
     * @param filters the filters
     * @return Number of rows in the Institution entity
     * @throws Exception the exception
     */	
	public int count(Class<Institution> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
