package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.SaqaUnitstandardDAO;
import haj.com.entity.lookup.SaqaUnitstandard;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class SaqaUnitstandardService.
 */
public class SaqaUnitstandardService extends AbstractService {
	/** The dao. */
	private SaqaUnitstandardDAO dao = new SaqaUnitstandardDAO();

	/**
	 * Get all SaqaUnitstandard.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.SaqaUnitstandard}
	 * @throws Exception the exception
	 * @see   SaqaUnitstandard
	 */
	public List<SaqaUnitstandard> allSaqaUnitstandard() throws Exception {
	  	return dao.allSaqaUnitstandard();
	}


	/**
	 * Create or update SaqaUnitstandard.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SaqaUnitstandard
	 */
	public void create(SaqaUnitstandard entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SaqaUnitstandard.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SaqaUnitstandard
	 */
	public void update(SaqaUnitstandard entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SaqaUnitstandard.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SaqaUnitstandard
	 */
	public void delete(SaqaUnitstandard entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SaqaUnitstandard}
	 * @throws Exception the exception
	 * @see    SaqaUnitstandard
	 */
	public SaqaUnitstandard findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SaqaUnitstandard by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SaqaUnitstandard}
	 * @throws Exception the exception
	 * @see    SaqaUnitstandard
	 */
	public List<SaqaUnitstandard> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load SaqaUnitstandard.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SaqaUnitstandard}
	 * @throws Exception the exception
	 */
	public List<SaqaUnitstandard> allSaqaUnitstandard(int first, int pageSize) throws Exception {
		return dao.allSaqaUnitstandard(Long.valueOf(first), pageSize);
	}


	/**
	 * Get count of SaqaUnitstandard for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the SaqaUnitstandard
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SaqaUnitstandard.class);
	}

    /**
     * Lazy load SaqaUnitstandard with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 SaqaUnitstandard class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SaqaUnitstandard} containing data
     * @throws Exception the exception
     */
	@SuppressWarnings("unchecked")
	public List<SaqaUnitstandard> allSaqaUnitstandard(Class<SaqaUnitstandard> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		if (filters.containsKey("unitstandardid")) {
			filters.put("unitstandardid", Integer.valueOf( (""+ filters.get("unitstandardid")).trim()));
		}
		return ( List<SaqaUnitstandard>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);

	}

    /**
     * Get count of SaqaUnitstandard for lazy load with filters.
     *
     * @author TechFinium
     * @param entity SaqaUnitstandard class
     * @param filters the filters
     * @return Number of rows in the SaqaUnitstandard entity
     * @throws Exception the exception
     */
	public int count(Class<SaqaUnitstandard> entity, Map<String, Object> filters) throws Exception {
		if (filters.containsKey("unitstandardid")) {
			filters.put("unitstandardid", Integer.valueOf( (""+ filters.get("unitstandardid")).trim()));
		}
		return  dao.count(entity, filters);
	}
	
	public SaqaUnitstandard findByNqfleveldescription(String nqfleveldescription) throws Exception {
		return dao.findByNqfleveldescription(nqfleveldescription);
	}
}
