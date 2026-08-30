package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.NoHardToFillVacanciesDAO;
import haj.com.entity.lookup.NoHardToFillVacancies;
import haj.com.framework.AbstractService;

public class NoHardToFillVacanciesService extends AbstractService {
	/** The dao. */
	private NoHardToFillVacanciesDAO dao = new NoHardToFillVacanciesDAO();

	/**
	 * Get all NoHardToFillVacancies
 	 * @author TechFinium 
 	 * @see   NoHardToFillVacancies
 	 * @return a list of {@link haj.com.entity.NoHardToFillVacancies}
	 * @throws Exception the exception
 	 */
	public List<NoHardToFillVacancies> allNoHardToFillVacancies() throws Exception {
	  	return dao.allNoHardToFillVacancies();
	}


	/**
	 * Create or update NoHardToFillVacancies.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     NoHardToFillVacancies
	 */
	public void create(NoHardToFillVacancies entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  NoHardToFillVacancies.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    NoHardToFillVacancies
	 */
	public void update(NoHardToFillVacancies entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  NoHardToFillVacancies.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    NoHardToFillVacancies
	 */
	public void delete(NoHardToFillVacancies entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.NoHardToFillVacancies}
	 * @throws Exception the exception
	 * @see    NoHardToFillVacancies
	 */
	public NoHardToFillVacancies findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find NoHardToFillVacancies by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.NoHardToFillVacancies}
	 * @throws Exception the exception
	 * @see    NoHardToFillVacancies
	 */
	public List<NoHardToFillVacancies> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load NoHardToFillVacancies
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.NoHardToFillVacancies}
	 * @throws Exception the exception
	 */
	public List<NoHardToFillVacancies> allNoHardToFillVacancies(int first, int pageSize) throws Exception {
		return dao.allNoHardToFillVacancies(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of NoHardToFillVacancies for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the NoHardToFillVacancies
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(NoHardToFillVacancies.class);
	}
	
    /**
     * Lazy load NoHardToFillVacancies with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 NoHardToFillVacancies class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.NoHardToFillVacancies} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<NoHardToFillVacancies> allNoHardToFillVacancies(Class<NoHardToFillVacancies> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<NoHardToFillVacancies>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of NoHardToFillVacancies for lazy load with filters
     * @author TechFinium 
     * @param entity NoHardToFillVacancies class
     * @param filters the filters
     * @return Number of rows in the NoHardToFillVacancies entity
     * @throws Exception the exception     
     */	
	public int count(Class<NoHardToFillVacancies> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
