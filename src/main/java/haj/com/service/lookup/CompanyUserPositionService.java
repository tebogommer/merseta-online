package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.CompanyUserPositionDAO;
import haj.com.entity.Company;
import haj.com.entity.lookup.CompanyUserPosition;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyUserPositionService.
 */
public class CompanyUserPositionService extends AbstractService {
	/** The dao. */
	private CompanyUserPositionDAO dao = new CompanyUserPositionDAO();

	/**
	 * Get all CompanyUserPosition.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.CompanyUserPosition}
	 * @throws Exception the exception
	 * @see   CompanyUserPosition
	 */
	public List<CompanyUserPosition> allCompanyUserPosition() throws Exception {
	  	return dao.allCompanyUserPosition();
	}
	
	public List<CompanyUserPosition> allCompanyUserPositionNotUsed(Company company) throws Exception {
		return dao.allCompanyUserPositionNotUsed(company);
	}


	/**
	 * Create or update CompanyUserPosition.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     CompanyUserPosition
	 */
	public void create(CompanyUserPosition entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
	    if (entity.getId() ==null) {
		  this.dao.create(entity);
	    }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  CompanyUserPosition.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    CompanyUserPosition
	 */
	public void update(CompanyUserPosition entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  CompanyUserPosition.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    CompanyUserPosition
	 */
	public void delete(CompanyUserPosition entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.CompanyUserPosition}
	 * @throws Exception the exception
	 * @see    CompanyUserPosition
	 */
	public CompanyUserPosition findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find CompanyUserPosition by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.CompanyUserPosition}
	 * @throws Exception the exception
	 * @see    CompanyUserPosition
	 */
	public List<CompanyUserPosition> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load CompanyUserPosition.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.CompanyUserPosition}
	 * @throws Exception the exception
	 */
	public List<CompanyUserPosition> allCompanyUserPosition(int first, int pageSize) throws Exception {
		return dao.allCompanyUserPosition(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of CompanyUserPosition for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the CompanyUserPosition
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(CompanyUserPosition.class);
	}
	
    /**
     * Lazy load CompanyUserPosition with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 CompanyUserPosition class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.CompanyUserPosition} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<CompanyUserPosition> allCompanyUserPosition(Class<CompanyUserPosition> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<CompanyUserPosition>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of CompanyUserPosition for lazy load with filters.
     *
     * @author TechFinium
     * @param entity CompanyUserPosition class
     * @param filters the filters
     * @return Number of rows in the CompanyUserPosition entity
     * @throws Exception the exception
     */	
	public int count(Class<CompanyUserPosition> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
