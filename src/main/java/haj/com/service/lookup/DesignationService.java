package haj.com.service.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.DesignationDAO;
import haj.com.entity.lookup.Designation;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class DesignationService.
 */
public class DesignationService extends AbstractService {
	/** The dao. */
	private DesignationDAO dao = new DesignationDAO();

	/**
	 * Get all Designation.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Designation}
	 * @throws Exception the exception
	 * @see   Designation
	 */
	public List<Designation> allDesignation() throws Exception {
	  	return dao.allDesignation();
	}
	
	/**
	 * Get all Designation Exclude CA.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Designation}
	 * @throws Exception global exception
	 * @see    Designation
	 */
	@SuppressWarnings("unchecked")
	public List<Designation> allDesignationExcludeCA() throws Exception {
		return dao.allDesignationExcludeCA();
	}
	
	@SuppressWarnings("unchecked")
	public List<Designation> allTPApplicationDesignation() throws Exception {
		return dao.allTPApplicationDesignation();
	}
	
	public List<Designation> findSdpDesignations(Boolean sdpDesignation) throws Exception {
		return dao.findSdpDesignations(sdpDesignation);
	}


	/**
	 * Create or update Designation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Designation
	 */
	public void create(Designation entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  Designation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Designation
	 */
	public void update(Designation entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Designation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Designation
	 */
	public void delete(Designation entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Designation}
	 * @throws Exception the exception
	 * @see    Designation
	 */
	public Designation findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Designation by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Designation}
	 * @throws Exception the exception
	 * @see    Designation
	 */
	public List<Designation> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load Designation.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Designation}
	 * @throws Exception the exception
	 */
	public List<Designation> allDesignation(int first, int pageSize) throws Exception {
		return dao.allDesignation(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Designation for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Designation
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Designation.class);
	}
	
    /**
     * Lazy load Designation with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 Designation class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Designation} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Designation> allDesignation(Class<Designation> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Designation>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of Designation for lazy load with filters.
     *
     * @author TechFinium
     * @param entity Designation class
     * @param filters the filters
     * @return Number of rows in the Designation entity
     * @throws Exception the exception
     */	
	public int count(Class<Designation> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public Designation findByCode(String code) throws Exception {
	 return  dao.findByCode(code);
	
	}
	
	
}
