package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.GrantOfoSelectionDAO;
import haj.com.entity.lookup.GrantOfoSelection;
import haj.com.framework.AbstractService;
import haj.com.service.RejectReasonMultipleSelectionService;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class GrantOfoSelectionService extends AbstractService {
	
	/** The dao. */
	private GrantOfoSelectionDAO dao = new GrantOfoSelectionDAO();
	
	/* Instance */
	private static GrantOfoSelectionService grantOfoSelectionService;
	public static synchronized GrantOfoSelectionService instance() {
		if (grantOfoSelectionService == null) {
			grantOfoSelectionService = new GrantOfoSelectionService();
		}
		return grantOfoSelectionService;
	}

	/**
	 * Get all GrantOfoSelection
 	 * @author TechFinium 
 	 * @see   GrantOfoSelection
 	 * @return a list of {@link haj.com.entity.GrantOfoSelection}
	 * @throws Exception the exception
 	 */
	public List<GrantOfoSelection> allGrantOfoSelection() throws Exception {
	  	return dao.allGrantOfoSelection();
	}


	/**
	 * Create or update GrantOfoSelection.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     GrantOfoSelection
	 */
	public void create(GrantOfoSelection entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  GrantOfoSelection.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    GrantOfoSelection
	 */
	public void update(GrantOfoSelection entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  GrantOfoSelection.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    GrantOfoSelection
	 */
	public void delete(GrantOfoSelection entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.GrantOfoSelection}
	 * @throws Exception the exception
	 * @see    GrantOfoSelection
	 */
	public GrantOfoSelection findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find GrantOfoSelection by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.GrantOfoSelection}
	 * @throws Exception the exception
	 * @see    GrantOfoSelection
	 */
	public List<GrantOfoSelection> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load GrantOfoSelection
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.GrantOfoSelection}
	 * @throws Exception the exception
	 */
	public List<GrantOfoSelection> allGrantOfoSelection(int first, int pageSize) throws Exception {
		return dao.allGrantOfoSelection(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of GrantOfoSelection for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the GrantOfoSelection
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(GrantOfoSelection.class);
	}
	
    /**
     * Lazy load GrantOfoSelection with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 GrantOfoSelection class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.GrantOfoSelection} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<GrantOfoSelection> allGrantOfoSelection(Class<GrantOfoSelection> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<GrantOfoSelection>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of GrantOfoSelection for lazy load with filters
     * @author TechFinium 
     * @param entity GrantOfoSelection class
     * @param filters the filters
     * @return Number of rows in the GrantOfoSelection entity
     * @throws Exception the exception     
     */	
	public int count(Class<GrantOfoSelection> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public GrantOfoSelection findByGrantYear(Integer grantYear) throws Exception {
		return dao.findByGrantYear(grantYear);
	}
	
	public List<GrantOfoSelection> findByGrantYearList(Integer grantYear) throws Exception {
		return dao.findByGrantYearList(grantYear);
	}
	
	public GrantOfoSelection findByGrantYearListReturnFirst(Integer grantYear) throws Exception {
		List<GrantOfoSelection> list = dao.findByGrantYearList(grantYear);
		if (list.isEmpty()) {
			return null;
		}else {
			return list.get(0);
		}
	}
}
