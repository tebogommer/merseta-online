package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.ChamberDAO;
import haj.com.entity.lookup.Chamber;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class ChamberService.
 */
public class ChamberService extends AbstractService {
	/** The dao. */
	private ChamberDAO dao = new ChamberDAO();

	/**
	 * Get all Chamber.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Chamber}
	 * @throws Exception the exception
	 * @see   Chamber
	 */
	public List<Chamber> allChamber() throws Exception {
	  	return dao.allChamber();
	}


	/**
	 * Create or update Chamber.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Chamber
	 */
	public void create(Chamber entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  Chamber.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Chamber
	 */
	public void update(Chamber entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Chamber.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Chamber
	 */
	public void delete(Chamber entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Chamber}
	 * @throws Exception the exception
	 * @see    Chamber
	 */
	public Chamber findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Chamber by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Chamber}
	 * @throws Exception the exception
	 * @see    Chamber
	 */
	public List<Chamber> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	public List<Chamber> findByNameWithoutNonMersetaChamber(String desc) throws Exception {
		return dao.findByNameWithoutNonMersetaChamber(desc);
	}
	
	
	
	
	/**
	 * Lazy load Chamber.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Chamber}
	 * @throws Exception the exception
	 */
	public List<Chamber> allChamber(int first, int pageSize) throws Exception {
		return dao.allChamber(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Chamber for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Chamber
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Chamber.class);
	}
	
    /**
     * Lazy load Chamber with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 Chamber class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Chamber} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Chamber> allChamber(Class<Chamber> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Chamber>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of Chamber for lazy load with filters.
     *
     * @author TechFinium
     * @param entity Chamber class
     * @param filters the filters
     * @return Number of rows in the Chamber entity
     * @throws Exception the exception
     */	
	public int count(Class<Chamber> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
