package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.ValidityDAO;
import haj.com.entity.lookup.Validity;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class ValidityService extends AbstractService {
	/** The dao. */
	private ValidityDAO dao = new ValidityDAO();

	/**
	 * Get all Validity
 	 * @author TechFinium 
 	 * @see   Validity
 	 * @return a list of {@link haj.com.entity.Validity}
	 * @throws Exception the exception
 	 */
	public List<Validity> allValidity() throws Exception {
	  	return dao.allValidity();
	}


	/**
	 * Create or update Validity.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Validity
	 */
	public void create(Validity entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  Validity.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Validity
	 */
	public void update(Validity entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Validity.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Validity
	 */
	public void delete(Validity entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Validity}
	 * @throws Exception the exception
	 * @see    Validity
	 */
	public Validity findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Validity by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Validity}
	 * @throws Exception the exception
	 * @see    Validity
	 */
	public List<Validity> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load Validity
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Validity}
	 * @throws Exception the exception
	 */
	public List<Validity> allValidity(int first, int pageSize) throws Exception {
		return dao.allValidity(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Validity for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the Validity
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Validity.class);
	}
	
    /**
     * Lazy load Validity with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 Validity class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Validity} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Validity> allValidity(Class<Validity> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Validity>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of Validity for lazy load with filters
     * @author TechFinium 
     * @param entity Validity class
     * @param filters the filters
     * @return Number of rows in the Validity entity
     * @throws Exception the exception     
     */	
	public int count(Class<Validity> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
