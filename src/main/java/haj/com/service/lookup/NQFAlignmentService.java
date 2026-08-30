package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.NQFAlignmentDAO;
import haj.com.entity.lookup.NQFAlignment;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class NQFAlignmentService.
 */
public class NQFAlignmentService extends AbstractService {
	/** The dao. */
	private NQFAlignmentDAO dao = new NQFAlignmentDAO();

	/**
	 * Get all NQFAlignment.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.NQFAlignment}
	 * @throws Exception the exception
	 * @see   NQFAlignment
	 */
	public List<NQFAlignment> allNQFAlignment() throws Exception {
	  	return dao.allNQFAlignment();
	}


	/**
	 * Create or update NQFAlignment.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     NQFAlignment
	 */
	public void create(NQFAlignment entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  NQFAlignment.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    NQFAlignment
	 */
	public void update(NQFAlignment entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  NQFAlignment.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    NQFAlignment
	 */
	public void delete(NQFAlignment entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.NQFAlignment}
	 * @throws Exception the exception
	 * @see    NQFAlignment
	 */
	public NQFAlignment findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find NQFAlignment by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.NQFAlignment}
	 * @throws Exception the exception
	 * @see    NQFAlignment
	 */
	public List<NQFAlignment> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load NQFAlignment.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.NQFAlignment}
	 * @throws Exception the exception
	 */
	public List<NQFAlignment> allNQFAlignment(int first, int pageSize) throws Exception {
		return dao.allNQFAlignment(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of NQFAlignment for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the NQFAlignment
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(NQFAlignment.class);
	}
	
    /**
     * Lazy load NQFAlignment with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 NQFAlignment class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.NQFAlignment} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<NQFAlignment> allNQFAlignment(Class<NQFAlignment> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<NQFAlignment>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of NQFAlignment for lazy load with filters.
     *
     * @author TechFinium
     * @param entity NQFAlignment class
     * @param filters the filters
     * @return Number of rows in the NQFAlignment entity
     * @throws Exception the exception
     */	
	public int count(Class<NQFAlignment> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
