package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.MunicipalityTypeDAO;
import haj.com.entity.MunicipalityType;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class MunicipalityTypeService.
 */
public class MunicipalityTypeService extends AbstractService {

	/** The dao. */
	private MunicipalityTypeDAO dao = new MunicipalityTypeDAO();

	/**
	 * Get all MunicipalityType.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.MunicipalityType}
	 * @throws Exception the exception
	 * @see    MunicipalityType
	 */
	public List<MunicipalityType> allMunicipalityType() throws Exception {
	  	return dao.allMunicipalityType();
	}

	/**
	 * Create or update  MunicipalityType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    MunicipalityType
	 */
	public void create(MunicipalityType entity) throws Exception  {
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}

	/**
	 * Update  MunicipalityType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    MunicipalityType
	 */
	public void update(MunicipalityType entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  MunicipalityType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    MunicipalityType
	 */
	public void delete(MunicipalityType entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a MunicipalityType object
	 * @throws Exception the exception
	 * @see    MunicipalityType
	 */
	public MunicipalityType findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find MunicipalityType by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.MunicipalityType}
	 * @throws Exception the exception
	 * @see    MunicipalityType
	 */
	public List<MunicipalityType> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load MunicipalityType.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.MunicipalityType}
	 * @throws Exception the exception
	 */
	public List<MunicipalityType> allMunicipalityType(int first, int pageSize) throws Exception {
		return dao.allMunicipalityType(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Count.
	 *
	 * @author TechFinium
	 * @return Number of rows in the MunicipalityType
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(MunicipalityType.class);
	}
	
    /**
     * All municipality type.
     *
     * @author TechFinium
     * @param class1 MunicipalityType class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return a list of {@link haj.com.entity.MunicipalityType} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<MunicipalityType> allMunicipalityType(Class<MunicipalityType> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<MunicipalityType>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Count.
     *
     * @param entity MunicipalityType class
     * @param filters the filters
     * @return Number of rows in the MunicipalityType entity
     * @throws Exception the exception
     */	
	public int count(Class<MunicipalityType> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
