package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.VacancyReasonsDAO;
import haj.com.entity.lookup.VacancyReasons;
import haj.com.framework.AbstractService;

public class VacancyReasonsService extends AbstractService {
	/** The dao. */
	private VacancyReasonsDAO dao = new VacancyReasonsDAO();

	/**
	 * Get all VacancyReasons
 	 * @author TechFinium 
 	 * @see   VacancyReasons
 	 * @return a list of {@link haj.com.entity.VacancyReasons}
	 * @throws Exception the exception
 	 */
	public List<VacancyReasons> allVacancyReasons() throws Exception {
	  	return dao.allVacancyReasons();
	}


	/**
	 * Create or update VacancyReasons.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     VacancyReasons
	 */
	public void create(VacancyReasons entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  VacancyReasons.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    VacancyReasons
	 */
	public void update(VacancyReasons entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  VacancyReasons.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    VacancyReasons
	 */
	public void delete(VacancyReasons entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.VacancyReasons}
	 * @throws Exception the exception
	 * @see    VacancyReasons
	 */
	public VacancyReasons findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find VacancyReasons by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.VacancyReasons}
	 * @throws Exception the exception
	 * @see    VacancyReasons
	 */
	public List<VacancyReasons> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load VacancyReasons
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.VacancyReasons}
	 * @throws Exception the exception
	 */
	public List<VacancyReasons> allVacancyReasons(int first, int pageSize) throws Exception {
		return dao.allVacancyReasons(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of VacancyReasons for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the VacancyReasons
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(VacancyReasons.class);
	}
	
    /**
     * Lazy load VacancyReasons with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 VacancyReasons class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.VacancyReasons} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<VacancyReasons> allVacancyReasons(Class<VacancyReasons> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<VacancyReasons>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of VacancyReasons for lazy load with filters
     * @author TechFinium 
     * @param entity VacancyReasons class
     * @param filters the filters
     * @return Number of rows in the VacancyReasons entity
     * @throws Exception the exception     
     */	
	public int count(Class<VacancyReasons> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
