package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.FinancialYearsDAO;
import haj.com.entity.lookup.FinancialYears;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class FinancialYearsService extends AbstractService {
	/** The dao. */
	private FinancialYearsDAO dao = new FinancialYearsDAO();

	/**
	 * Get all FinancialYears
 	 * @author TechFinium 
 	 * @see   FinancialYears
 	 * @return a list of {@link haj.com.entity.FinancialYears}
	 * @throws Exception the exception
 	 */
	public List<FinancialYears> allFinancialYears() throws Exception {
	  	return dao.allFinancialYears();
	}


	/**
	 * Create or update FinancialYears.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     FinancialYears
	 */
	public void create(FinancialYears entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  FinancialYears.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    FinancialYears
	 */
	public void update(FinancialYears entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  FinancialYears.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    FinancialYears
	 */
	public void delete(FinancialYears entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.FinancialYears}
	 * @throws Exception the exception
	 * @see    FinancialYears
	 */
	public FinancialYears findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find FinancialYears by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.FinancialYears}
	 * @throws Exception the exception
	 * @see    FinancialYears
	 */
	public List<FinancialYears> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load FinancialYears
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.FinancialYears}
	 * @throws Exception the exception
	 */
	public List<FinancialYears> allFinancialYears(int first, int pageSize) throws Exception {
		return dao.allFinancialYears(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of FinancialYears for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the FinancialYears
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(FinancialYears.class);
	}
	
    /**
     * Lazy load FinancialYears with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 FinancialYears class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.FinancialYears} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<FinancialYears> allFinancialYears(Class<FinancialYears> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<FinancialYears>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of FinancialYears for lazy load with filters
     * @author TechFinium 
     * @param entity FinancialYears class
     * @param filters the filters
     * @return Number of rows in the FinancialYears entity
     * @throws Exception the exception     
     */	
	public int count(Class<FinancialYears> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public List<FinancialYears> findByStartYear(Integer finYear) throws Exception {
		return dao.findByStartYear(finYear);
	}
	
	public FinancialYears findByStartYearReturnFirst(Integer finYear) throws Exception {
		List<FinancialYears> list = findByStartYear(finYear);
		if (list.isEmpty()) {
			return null;
		}else {
			return list.get(0);
		}
	}
}
