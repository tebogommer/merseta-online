package haj.com.service;

import java.util.List;

import haj.com.dao.JasperDownloadTrackerDAO;
import haj.com.entity.JasperDownloadTracker;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class JasperDownloadTrackerService extends AbstractService {
	/** The dao. */
	private JasperDownloadTrackerDAO dao = new JasperDownloadTrackerDAO();
	
	private static JasperDownloadTrackerService jasperDownloadTrackerService;
	public static synchronized JasperDownloadTrackerService instance() {
		if (jasperDownloadTrackerService == null) {
			jasperDownloadTrackerService = new JasperDownloadTrackerService();
		}
		return jasperDownloadTrackerService;
	}

	/**
	 * Get all JasperDownloadTracker
 	 * @author TechFinium 
 	 * @see   JasperDownloadTracker
 	 * @return a list of {@link haj.com.entity.JasperDownloadTracker}
	 * @throws Exception the exception
 	 */
	public List<JasperDownloadTracker> allJasperDownloadTracker() throws Exception {
	  	return dao.allJasperDownloadTracker();
	}


	/**
	 * Create or update JasperDownloadTracker.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     JasperDownloadTracker
	 */
	public void create(JasperDownloadTracker entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  JasperDownloadTracker.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    JasperDownloadTracker
	 */
	public void update(JasperDownloadTracker entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  JasperDownloadTracker.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    JasperDownloadTracker
	 */
	public void delete(JasperDownloadTracker entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.JasperDownloadTracker}
	 * @throws Exception the exception
	 * @see    JasperDownloadTracker
	 */
	public JasperDownloadTracker findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find JasperDownloadTracker by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.JasperDownloadTracker}
	 * @throws Exception the exception
	 * @see    JasperDownloadTracker
	 */
	public List<JasperDownloadTracker> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load JasperDownloadTracker
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.JasperDownloadTracker}
	 * @throws Exception the exception
	 */
	public List<JasperDownloadTracker> allJasperDownloadTracker(int first, int pageSize) throws Exception {
		return dao.allJasperDownloadTracker(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of JasperDownloadTracker for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the JasperDownloadTracker
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(JasperDownloadTracker.class);
	}
	
    /**
     * Lazy load JasperDownloadTracker with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 JasperDownloadTracker class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.JasperDownloadTracker} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<JasperDownloadTracker> allJasperDownloadTracker(Class<JasperDownloadTracker> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<JasperDownloadTracker>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of JasperDownloadTracker for lazy load with filters
     * @author TechFinium 
     * @param entity JasperDownloadTracker class
     * @param filters the filters
     * @return Number of rows in the JasperDownloadTracker entity
     * @throws Exception the exception     
     */	
	public int count(Class<JasperDownloadTracker> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
