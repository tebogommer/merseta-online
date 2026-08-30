package haj.com.service;

import java.util.List;

import haj.com.dao.DhetReportingDAO;
import haj.com.entity.DhetReporting;
import haj.com.entity.enums.DhetFileNumberEnum;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class DhetReportingService extends AbstractService {
	/** The dao. */
	private DhetReportingDAO dao = new DhetReportingDAO();

	/**
	 * Get all DhetReporting
 	 * @author TechFinium 
 	 * @see   DhetReporting
 	 * @return a list of {@link haj.com.entity.DhetReporting}
	 * @throws Exception the exception
 	 */
	public List<DhetReporting> allDhetReporting() throws Exception {
	  	return dao.allDhetReporting();
	}


	/**
	 * Create or update DhetReporting.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     DhetReporting
	 */
	public void create(DhetReporting entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  DhetReporting.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DhetReporting
	 */
	public void update(DhetReporting entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  DhetReporting.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DhetReporting
	 */
	public void delete(DhetReporting entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.DhetReporting}
	 * @throws Exception the exception
	 * @see    DhetReporting
	 */
	public DhetReporting findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find DhetReporting by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.DhetReporting}
	 * @throws Exception the exception
	 * @see    DhetReporting
	 */
	public List<DhetReporting> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load DhetReporting
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.DhetReporting}
	 * @throws Exception the exception
	 */
	public List<DhetReporting> allDhetReporting(int first, int pageSize) throws Exception {
		return dao.allDhetReporting(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of DhetReporting for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the DhetReporting
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(DhetReporting.class);
	}
	
    /**
     * Lazy load DhetReporting with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 DhetReporting class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.DhetReporting} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<DhetReporting> allDhetReporting(Class<DhetReporting> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<DhetReporting>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of DhetReporting for lazy load with filters
     * @author TechFinium 
     * @param entity DhetReporting class
     * @param filters the filters
     * @return Number of rows in the DhetReporting entity
     * @throws Exception the exception     
     */	
	public int count(Class<DhetReporting> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public DhetReporting findByDhetFileNumberEnum(DhetFileNumberEnum dhetFileNumberEnum) throws Exception {
		return dao.findByDhetFileNumberEnum(dhetFileNumberEnum);
	}
	
	public String findByDhetFileNumberEnumReturnSql(DhetFileNumberEnum dhetFileNumberEnum) throws Exception{
		DhetReporting dhetReporting = findByDhetFileNumberEnum(dhetFileNumberEnum);
		if (dhetReporting != null && dhetReporting.getId() != null && dhetReporting.getNativeSql() != null && !dhetReporting.getNativeSql().isEmpty()) {
			return dhetReporting.getNativeSql().replaceAll("\n", " ");
		} else {
			throw new Exception("Unable to locate extract script. Contact Support!");
		}
	}
}
