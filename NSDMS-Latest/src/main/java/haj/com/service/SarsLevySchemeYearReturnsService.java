package haj.com.service;

import java.util.List;

import haj.com.dao.SarsLevySchemeYearReturnsDAO;
import haj.com.entity.SarsLevyDetailCalculation;
import haj.com.entity.SarsLevySchemeYearReturns;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class SarsLevySchemeYearReturnsService extends AbstractService {
	/** The dao. */
	private SarsLevySchemeYearReturnsDAO dao = new SarsLevySchemeYearReturnsDAO();

	/**
	 * Get all SarsLevySchemeYearReturns
 	 * @author TechFinium 
 	 * @see   SarsLevySchemeYearReturns
 	 * @return a list of {@link haj.com.entity.SarsLevySchemeYearReturns}
	 * @throws Exception the exception
 	 */
	public List<SarsLevySchemeYearReturns> allSarsLevySchemeYearReturns() throws Exception {
	  	return dao.allSarsLevySchemeYearReturns();
	}


	/**
	 * Create or update SarsLevySchemeYearReturns.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SarsLevySchemeYearReturns
	 */
	public void create(SarsLevySchemeYearReturns entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SarsLevySchemeYearReturns.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SarsLevySchemeYearReturns
	 */
	public void update(SarsLevySchemeYearReturns entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SarsLevySchemeYearReturns.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SarsLevySchemeYearReturns
	 */
	public void delete(SarsLevySchemeYearReturns entity) throws Exception  {
		this.dao.delete(entity);
	}
	
	public Integer countByYear(Integer year) throws Exception {
		return dao.countByYear(year);
	}
	
	public Integer countByYearExcludingId(Integer year, Long id) throws Exception {
		return dao.countByYearExcludingId(year, id);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SarsLevySchemeYearReturns}
	 * @throws Exception the exception
	 * @see    SarsLevySchemeYearReturns
	 */
	public SarsLevySchemeYearReturns findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SarsLevySchemeYearReturns by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SarsLevySchemeYearReturns}
	 * @throws Exception the exception
	 * @see    SarsLevySchemeYearReturns
	 */
	public List<SarsLevySchemeYearReturns> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SarsLevySchemeYearReturns
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SarsLevySchemeYearReturns}
	 * @throws Exception the exception
	 */
	public List<SarsLevySchemeYearReturns> allSarsLevySchemeYearReturns(int first, int pageSize) throws Exception {
		return dao.allSarsLevySchemeYearReturns(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SarsLevySchemeYearReturns for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the SarsLevySchemeYearReturns
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SarsLevySchemeYearReturns.class);
	}
	
    /**
     * Lazy load SarsLevySchemeYearReturns with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 SarsLevySchemeYearReturns class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SarsLevySchemeYearReturns} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SarsLevySchemeYearReturns> allSarsLevySchemeYearReturns(Class<SarsLevySchemeYearReturns> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SarsLevySchemeYearReturns>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SarsLevySchemeYearReturns for lazy load with filters
     * @author TechFinium 
     * @param entity SarsLevySchemeYearReturns class
     * @param filters the filters
     * @return Number of rows in the SarsLevySchemeYearReturns entity
     * @throws Exception the exception     
     */	
	public int count(Class<SarsLevySchemeYearReturns> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public void validiateInformation(SarsLevySchemeYearReturns sarsLevySchemeYearReturns) throws Exception {	
		// check if all information provided
		if (sarsLevySchemeYearReturns.getForSchemeYear() == null) {
			throw new Exception("Provide Year Before Proceeding");
		}
		
		// check if year already assigned
		if (sarsLevySchemeYearReturns.getId() != null) {
			if (countByYearExcludingId(sarsLevySchemeYearReturns.getForSchemeYear(), sarsLevySchemeYearReturns.getId()) > 0) {
				throw new Exception("Year already assigned, please provide a different year");
			}
		} else {
			if (countByYear(sarsLevySchemeYearReturns.getForSchemeYear()) > 0) {
				throw new Exception("Year already assigned, please provide a different year");
			}
		}
	}
}
