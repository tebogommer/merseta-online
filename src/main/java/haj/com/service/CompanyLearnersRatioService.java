package haj.com.service;

import java.util.List;

import haj.com.dao.CompanyLearnersRatioDAO;
import haj.com.entity.CompanyLearnersRatio;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class CompanyLearnersRatioService extends AbstractService {
	/** The dao. */
	private CompanyLearnersRatioDAO dao = new CompanyLearnersRatioDAO();

	/**
	 * Get all CompanyLearnersRatio
 	 * @author TechFinium 
 	 * @see   CompanyLearnersRatio
 	 * @return a list of {@link haj.com.entity.CompanyLearnersRatio}
	 * @throws Exception the exception
 	 */
	public List<CompanyLearnersRatio> allCompanyLearnersRatio() throws Exception {
	  	return dao.allCompanyLearnersRatio();
	}


	/**
	 * Create or update CompanyLearnersRatio.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     CompanyLearnersRatio
	 */
	public void create(CompanyLearnersRatio entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  CompanyLearnersRatio.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    CompanyLearnersRatio
	 */
	public void update(CompanyLearnersRatio entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  CompanyLearnersRatio.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    CompanyLearnersRatio
	 */
	public void delete(CompanyLearnersRatio entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.CompanyLearnersRatio}
	 * @throws Exception the exception
	 * @see    CompanyLearnersRatio
	 */
	public CompanyLearnersRatio findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find CompanyLearnersRatio by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.CompanyLearnersRatio}
	 * @throws Exception the exception
	 * @see    CompanyLearnersRatio
	 */
	public List<CompanyLearnersRatio> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load CompanyLearnersRatio
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.CompanyLearnersRatio}
	 * @throws Exception the exception
	 */
	public List<CompanyLearnersRatio> allCompanyLearnersRatio(int first, int pageSize) throws Exception {
		return dao.allCompanyLearnersRatio(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of CompanyLearnersRatio for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the CompanyLearnersRatio
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(CompanyLearnersRatio.class);
	}
	
    /**
     * Lazy load CompanyLearnersRatio with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 CompanyLearnersRatio class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.CompanyLearnersRatio} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersRatio> allCompanyLearnersRatio(Class<CompanyLearnersRatio> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<CompanyLearnersRatio>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of CompanyLearnersRatio for lazy load with filters
     * @author TechFinium 
     * @param entity CompanyLearnersRatio class
     * @param filters the filters
     * @return Number of rows in the CompanyLearnersRatio entity
     * @throws Exception the exception     
     */	
	public int count(Class<CompanyLearnersRatio> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
