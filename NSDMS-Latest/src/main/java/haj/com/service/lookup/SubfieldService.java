package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.SubfieldDAO;
import haj.com.entity.lookup.Subfield;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class SubfieldService.
 */
public class SubfieldService extends AbstractService {
	/** The dao. */
	private SubfieldDAO dao = new SubfieldDAO();

	/**
	 * Get all Subfield.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Subfield}
	 * @throws Exception the exception
	 * @see   Subfield
	 */
	public List<Subfield> allSubfield() throws Exception {
	  	return dao.allSubfield();
	}


	/**
	 * Create or update Subfield.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Subfield
	 */
	public void create(Subfield entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  Subfield.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Subfield
	 */
	public void update(Subfield entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Subfield.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Subfield
	 */
	public void delete(Subfield entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Subfield}
	 * @throws Exception the exception
	 * @see    Subfield
	 */
	public Subfield findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Subfield by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Subfield}
	 * @throws Exception the exception
	 * @see    Subfield
	 */
	public List<Subfield> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load Subfield.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Subfield}
	 * @throws Exception the exception
	 */
	public List<Subfield> allSubfield(int first, int pageSize) throws Exception {
		return dao.allSubfield(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Subfield for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Subfield
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Subfield.class);
	}
	
    /**
     * Lazy load Subfield with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 Subfield class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Subfield} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Subfield> allSubfield(Class<Subfield> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Subfield>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of Subfield for lazy load with filters.
     *
     * @author TechFinium
     * @param entity Subfield class
     * @param filters the filters
     * @return Number of rows in the Subfield entity
     * @throws Exception the exception
     */	
	public int count(Class<Subfield> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public List<Subfield> findByDescriptionList(String description) throws Exception {
		return dao.findByDescriptionList(description);
	}
	
	public Subfield findByDescription(String description) throws Exception {
		return dao.findByDescription(description);
	}
}
