package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.EmploymentTypeDAO;
import haj.com.entity.lookup.EmploymentType;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class EmploymentTypeService.
 */
public class EmploymentTypeService extends AbstractService {
	/** The dao. */
	private EmploymentTypeDAO dao = new EmploymentTypeDAO();

	/**
	 * Get all EmploymentType.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.EmploymentType}
	 * @throws Exception the exception
	 * @see   EmploymentType
	 */
	public List<EmploymentType> allEmploymentType() throws Exception {
	  	return dao.allEmploymentType();
	}


	/**
	 * Create or update EmploymentType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     EmploymentType
	 */
	public void create(EmploymentType entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		 if (entity.getId() ==null) {
			 entity.setCreateDate(new java.util.Date());
			 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}

	/**
	 * Update  EmploymentType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    EmploymentType
	 */
	public void update(EmploymentType entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  EmploymentType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    EmploymentType
	 */
	public void delete(EmploymentType entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.EmploymentType}
	 * @throws Exception the exception
	 * @see    EmploymentType
	 */
	public EmploymentType findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find EmploymentType by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.EmploymentType}
	 * @throws Exception the exception
	 * @see    EmploymentType
	 */
	public List<EmploymentType> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load EmploymentType.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.EmploymentType}
	 * @throws Exception the exception
	 */
	public List<EmploymentType> allEmploymentType(int first, int pageSize) throws Exception {
		return dao.allEmploymentType(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of EmploymentType for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the EmploymentType
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(EmploymentType.class);
	}
	
    /**
     * Lazy load EmploymentType with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 EmploymentType class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.EmploymentType} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<EmploymentType> allEmploymentType(Class<EmploymentType> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<EmploymentType>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of EmploymentType for lazy load with filters.
     *
     * @author TechFinium
     * @param entity EmploymentType class
     * @param filters the filters
     * @return Number of rows in the EmploymentType entity
     * @throws Exception the exception
     */	
	public int count(Class<EmploymentType> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the employment type
	 * @throws Exception the exception
	 */
	public EmploymentType findByCode(String code) throws Exception { 
		if (StringUtils.isBlank(code)) return null;
		return dao.findByCode(code.trim());
	}
}
