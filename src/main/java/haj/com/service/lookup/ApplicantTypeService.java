package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.ApplicantTypeDAO;
import haj.com.entity.lookup.ApplicantType;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class ApplicantTypeService.
 */
public class ApplicantTypeService extends AbstractService {
	/** The dao. */
	private ApplicantTypeDAO dao = new ApplicantTypeDAO();

	/**
	 * Get all ApplicantType.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.ApplicantType}
	 * @throws Exception the exception
	 * @see   ApplicantType
	 */
	public List<ApplicantType> allApplicantType() throws Exception {
	  	return dao.allApplicantType();
	}


	/**
	 * Create or update ApplicantType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     ApplicantType
	 */
	public void create(ApplicantType entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  ApplicantType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ApplicantType
	 */
	public void update(ApplicantType entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  ApplicantType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ApplicantType
	 */
	public void delete(ApplicantType entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ApplicantType}
	 * @throws Exception the exception
	 * @see    ApplicantType
	 */
	public ApplicantType findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find ApplicantType by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.ApplicantType}
	 * @throws Exception the exception
	 * @see    ApplicantType
	 */
	public List<ApplicantType> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load ApplicantType.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.ApplicantType}
	 * @throws Exception the exception
	 */
	public List<ApplicantType> allApplicantType(int first, int pageSize) throws Exception {
		return dao.allApplicantType(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of ApplicantType for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the ApplicantType
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(ApplicantType.class);
	}
	
    /**
     * Lazy load ApplicantType with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 ApplicantType class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.ApplicantType} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<ApplicantType> allApplicantType(Class<ApplicantType> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<ApplicantType>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of ApplicantType for lazy load with filters.
     *
     * @author TechFinium
     * @param entity ApplicantType class
     * @param filters the filters
     * @return Number of rows in the ApplicantType entity
     * @throws Exception the exception
     */	
	public int count(Class<ApplicantType> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
