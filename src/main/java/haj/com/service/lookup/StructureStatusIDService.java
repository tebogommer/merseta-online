package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.StructureStatusIDDAO;
import haj.com.entity.lookup.StructureStatusID;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class StructureStatusIDService.
 */
public class StructureStatusIDService extends AbstractService {
	/** The dao. */
	private StructureStatusIDDAO dao = new StructureStatusIDDAO();

	/**
	 * Get all StructureStatusID.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.StructureStatusID}
	 * @throws Exception the exception
	 * @see   StructureStatusID
	 */
	public List<StructureStatusID> allStructureStatusID() throws Exception {
	  	return dao.allStructureStatusID();
	}


	/**
	 * Create or update StructureStatusID.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     StructureStatusID
	 */
	public void create(StructureStatusID entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  StructureStatusID.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    StructureStatusID
	 */
	public void update(StructureStatusID entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  StructureStatusID.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    StructureStatusID
	 */
	public void delete(StructureStatusID entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.StructureStatusID}
	 * @throws Exception the exception
	 * @see    StructureStatusID
	 */
	public StructureStatusID findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find StructureStatusID by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.StructureStatusID}
	 * @throws Exception the exception
	 * @see    StructureStatusID
	 */
	public List<StructureStatusID> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load StructureStatusID.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.StructureStatusID}
	 * @throws Exception the exception
	 */
	public List<StructureStatusID> allStructureStatusID(int first, int pageSize) throws Exception {
		return dao.allStructureStatusID(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of StructureStatusID for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the StructureStatusID
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(StructureStatusID.class);
	}
	
    /**
     * Lazy load StructureStatusID with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 StructureStatusID class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.StructureStatusID} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<StructureStatusID> allStructureStatusID(Class<StructureStatusID> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<StructureStatusID>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of StructureStatusID for lazy load with filters.
     *
     * @author TechFinium
     * @param entity StructureStatusID class
     * @param filters the filters
     * @return Number of rows in the StructureStatusID entity
     * @throws Exception the exception
     */	
	public int count(Class<StructureStatusID> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
