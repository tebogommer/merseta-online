package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.InstitutionTypeDAO;
import haj.com.entity.lookup.InstitutionType;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class InstitutionTypeService.
 */
public class InstitutionTypeService extends AbstractService {
	/** The dao. */
	private InstitutionTypeDAO dao = new InstitutionTypeDAO();

	/**
	 * Get all InstitutionType.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.InstitutionType}
	 * @throws Exception the exception
	 * @see   InstitutionType
	 */
	public List<InstitutionType> allInstitutionType() throws Exception {
	  	return dao.allInstitutionType();
	}


	/**
	 * Create or update InstitutionType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     InstitutionType
	 */
	public void create(InstitutionType entity) throws Exception  {
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  InstitutionType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    InstitutionType
	 */
	public void update(InstitutionType entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  InstitutionType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    InstitutionType
	 */
	public void delete(InstitutionType entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.InstitutionType}
	 * @throws Exception the exception
	 * @see    InstitutionType
	 */
	public InstitutionType findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find InstitutionType by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.InstitutionType}
	 * @throws Exception the exception
	 * @see    InstitutionType
	 */
	public List<InstitutionType> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load InstitutionType.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.InstitutionType}
	 * @throws Exception the exception
	 */
	public List<InstitutionType> allInstitutionType(int first, int pageSize) throws Exception {
		return dao.allInstitutionType(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of InstitutionType for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the InstitutionType
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(InstitutionType.class);
	}
	
    /**
     * Lazy load InstitutionType with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 InstitutionType class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.InstitutionType} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<InstitutionType> allInstitutionType(Class<InstitutionType> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<InstitutionType>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of InstitutionType for lazy load with filters.
     *
     * @author TechFinium
     * @param entity InstitutionType class
     * @param filters the filters
     * @return Number of rows in the InstitutionType entity
     * @throws Exception the exception
     */	
	public int count(Class<InstitutionType> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
