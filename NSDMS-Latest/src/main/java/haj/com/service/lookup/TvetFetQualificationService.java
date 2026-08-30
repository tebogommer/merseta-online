package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.TvetFetQualificationDAO;
import haj.com.entity.lookup.TvetFetQualification;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class TvetFetQualificationService.
 */
public class TvetFetQualificationService extends AbstractService {
	/** The dao. */
	private TvetFetQualificationDAO dao = new TvetFetQualificationDAO();

	/**
	 * Get all TvetFetQualification.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.TvetFetQualification}
	 * @throws Exception the exception
	 * @see   TvetFetQualification
	 */
	public List<TvetFetQualification> allTvetFetQualification() throws Exception {
	  	return dao.allTvetFetQualification();
	}


	/**
	 * Create or update TvetFetQualification.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     TvetFetQualification
	 */
	public void create(TvetFetQualification entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  TvetFetQualification.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TvetFetQualification
	 */
	public void update(TvetFetQualification entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  TvetFetQualification.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TvetFetQualification
	 */
	public void delete(TvetFetQualification entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.TvetFetQualification}
	 * @throws Exception the exception
	 * @see    TvetFetQualification
	 */
	public TvetFetQualification findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find TvetFetQualification by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.TvetFetQualification}
	 * @throws Exception the exception
	 * @see    TvetFetQualification
	 */
	public List<TvetFetQualification> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load TvetFetQualification.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.TvetFetQualification}
	 * @throws Exception the exception
	 */
	public List<TvetFetQualification> allTvetFetQualification(int first, int pageSize) throws Exception {
		return dao.allTvetFetQualification(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of TvetFetQualification for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the TvetFetQualification
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(TvetFetQualification.class);
	}
	
    /**
     * Lazy load TvetFetQualification with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 TvetFetQualification class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.TvetFetQualification} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<TvetFetQualification> allTvetFetQualification(Class<TvetFetQualification> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<TvetFetQualification>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of TvetFetQualification for lazy load with filters.
     *
     * @author TechFinium
     * @param entity TvetFetQualification class
     * @param filters the filters
     * @return Number of rows in the TvetFetQualification entity
     * @throws Exception the exception
     */	
	public int count(Class<TvetFetQualification> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
