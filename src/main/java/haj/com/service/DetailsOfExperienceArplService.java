package haj.com.service;

import java.util.Hashtable;
import java.util.List;

import haj.com.dao.DetailsOfExperienceArplDAO;
import haj.com.entity.CompanyLearnersTradeTest;
import haj.com.entity.DetailsOfExperienceArpl;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class DetailsOfExperienceArplService extends AbstractService {
	/** The dao. */
	private DetailsOfExperienceArplDAO dao = new DetailsOfExperienceArplDAO();

	/**
	 * Get all DetailsOfExperienceArpl
 	 * @author TechFinium 
 	 * @see   DetailsOfExperienceArpl
 	 * @return a list of {@link haj.com.entity.DetailsOfExperienceArpl}
	 * @throws Exception the exception
 	 */
	public List<DetailsOfExperienceArpl> allDetailsOfExperienceArpl() throws Exception {
	  	return dao.allDetailsOfExperienceArpl();
	}


	/**
	 * Create or update DetailsOfExperienceArpl.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     DetailsOfExperienceArpl
	 */
	public void create(DetailsOfExperienceArpl entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  DetailsOfExperienceArpl.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DetailsOfExperienceArpl
	 */
	public void update(DetailsOfExperienceArpl entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  DetailsOfExperienceArpl.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DetailsOfExperienceArpl
	 */
	public void delete(DetailsOfExperienceArpl entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.DetailsOfExperienceArpl}
	 * @throws Exception the exception
	 * @see    DetailsOfExperienceArpl
	 */
	public DetailsOfExperienceArpl findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find DetailsOfExperienceArpl by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.DetailsOfExperienceArpl}
	 * @throws Exception the exception
	 * @see    DetailsOfExperienceArpl
	 */
	public List<DetailsOfExperienceArpl> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load DetailsOfExperienceArpl
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.DetailsOfExperienceArpl}
	 * @throws Exception the exception
	 */
	public List<DetailsOfExperienceArpl> allDetailsOfExperienceArpl(int first, int pageSize) throws Exception {
		return dao.allDetailsOfExperienceArpl(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of DetailsOfExperienceArpl for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the DetailsOfExperienceArpl
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(DetailsOfExperienceArpl.class);
	}
	
    /**
     * Lazy load DetailsOfExperienceArpl with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 DetailsOfExperienceArpl class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.DetailsOfExperienceArpl} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<DetailsOfExperienceArpl> allDetailsOfExperienceArpl(Class<DetailsOfExperienceArpl> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<DetailsOfExperienceArpl>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	}
	
    /**
     * Get count of DetailsOfExperienceArpl for lazy load with filters
     * @author TechFinium 
     * @param entity DetailsOfExperienceArpl class
     * @param filters the filters
     * @return Number of rows in the DetailsOfExperienceArpl entity
     * @throws Exception the exception     
     */	
	public int count(Class<DetailsOfExperienceArpl> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<DetailsOfExperienceArpl> allDetailsOfExperienceArplByTradeTestId(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, CompanyLearnersTradeTest companyLearnersTradeTest) throws Exception {
		String hql = "select o from DetailsOfExperienceArpl o left join fetch o.companyLearnersTradeTest cltt where o.companyLearnersTradeTest.id = :tradeTestId ";
		filters.put("tradeTestId", companyLearnersTradeTest.getId());
		return (List<DetailsOfExperienceArpl>)dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllDetailsOfExperienceArplByTradeTestId(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from DetailsOfExperienceArpl o where o.companyLearnersTradeTest.id = :tradeTestId ";
		return  dao.countWhere(filters, hql);
	}
	
	public int countByTradeTest(CompanyLearnersTradeTest companyLearnersTradeTest) throws Exception {
		return dao.countByTradeTest(companyLearnersTradeTest.getId());
	}
	
	public List<DetailsOfExperienceArpl> findByCompanyLearnersTradeTest(Long  treadeTestID) throws Exception {
	 return dao.findByCompanyLearnersTradeTest(treadeTestID);
	}
}
