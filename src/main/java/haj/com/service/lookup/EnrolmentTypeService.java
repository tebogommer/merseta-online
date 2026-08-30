package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.EnrolmentTypeDAO;
import haj.com.entity.lookup.EnrolmentType;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class EnrolmentTypeService.
 */
public class EnrolmentTypeService extends AbstractService {
	/** The dao. */
	private EnrolmentTypeDAO dao = new EnrolmentTypeDAO();

	/**
	 * Get all EnrolmentType.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.EnrolmentType}
	 * @throws Exception the exception
	 * @see   EnrolmentType
	 */
	public List<EnrolmentType> allEnrolmentType() throws Exception {
	  	return dao.allEnrolmentType();
	}


	/**
	 * Create or update EnrolmentType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     EnrolmentType
	 */
	public void create(EnrolmentType entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  EnrolmentType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    EnrolmentType
	 */
	public void update(EnrolmentType entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  EnrolmentType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    EnrolmentType
	 */
	public void delete(EnrolmentType entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.EnrolmentType}
	 * @throws Exception the exception
	 * @see    EnrolmentType
	 */
	public EnrolmentType findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find EnrolmentType by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.EnrolmentType}
	 * @throws Exception the exception
	 * @see    EnrolmentType
	 */
	public List<EnrolmentType> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load EnrolmentType.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.EnrolmentType}
	 * @throws Exception the exception
	 */
	public List<EnrolmentType> allEnrolmentType(int first, int pageSize) throws Exception {
		return dao.allEnrolmentType(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of EnrolmentType for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the EnrolmentType
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(EnrolmentType.class);
	}
	
    /**
     * Lazy load EnrolmentType with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 EnrolmentType class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.EnrolmentType} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<EnrolmentType> allEnrolmentType(Class<EnrolmentType> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<EnrolmentType>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of EnrolmentType for lazy load with filters.
     *
     * @author TechFinium
     * @param entity EnrolmentType class
     * @param filters the filters
     * @return Number of rows in the EnrolmentType entity
     * @throws Exception the exception
     */	
	public int count(Class<EnrolmentType> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
