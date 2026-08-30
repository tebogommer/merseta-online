package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.FLCDAO;
import haj.com.entity.lookup.FLC;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class FLCService.
 */
public class FLCService extends AbstractService {
	/** The dao. */
	private FLCDAO dao = new FLCDAO();

	/**
	 * Get all FLC.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.FLC}
	 * @throws Exception the exception
	 * @see   FLC
	 */
	public List<FLC> allFLC() throws Exception {
	  	return dao.allFLC();
	}


	/**
	 * Create or update FLC.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     FLC
	 */
	public void create(FLC entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  FLC.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    FLC
	 */
	public void update(FLC entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  FLC.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    FLC
	 */
	public void delete(FLC entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.FLC}
	 * @throws Exception the exception
	 * @see    FLC
	 */
	public FLC findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find FLC by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.FLC}
	 * @throws Exception the exception
	 * @see    FLC
	 */
	public List<FLC> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load FLC.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.FLC}
	 * @throws Exception the exception
	 */
	public List<FLC> allFLC(int first, int pageSize) throws Exception {
		return dao.allFLC(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of FLC for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the FLC
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(FLC.class);
	}
	
    /**
     * Lazy load FLC with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 FLC class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.FLC} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<FLC> allFLC(Class<FLC> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<FLC>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of FLC for lazy load with filters.
     *
     * @author TechFinium
     * @param entity FLC class
     * @param filters the filters
     * @return Number of rows in the FLC entity
     * @throws Exception the exception
     */	
	public int count(Class<FLC> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
