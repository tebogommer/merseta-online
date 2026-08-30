package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.CompanyHostingCompanyDAO;
import haj.com.entity.Company;
import haj.com.entity.CompanyHostingCompany;
import haj.com.entity.HostingCompany;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyHostingCompanyService.
 */
public class CompanyHostingCompanyService extends AbstractService {
	/** The dao. */
	private CompanyHostingCompanyDAO dao = new CompanyHostingCompanyDAO();

	/**
	 * Get all CompanyHostingCompany.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.CompanyHostingCompany}
	 * @throws Exception the exception
	 * @see   CompanyHostingCompany
	 */
	public List<CompanyHostingCompany> allCompanyHostingCompany() throws Exception {
	  	return dao.allCompanyHostingCompany();
	}


	/**
	 * Create or update CompanyHostingCompany.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     CompanyHostingCompany
	 */
	public void create(CompanyHostingCompany entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  CompanyHostingCompany.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    CompanyHostingCompany
	 */
	public void update(CompanyHostingCompany entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  CompanyHostingCompany.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    CompanyHostingCompany
	 */
	public void delete(CompanyHostingCompany entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.CompanyHostingCompany}
	 * @throws Exception the exception
	 * @see    CompanyHostingCompany
	 */
	public CompanyHostingCompany findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find CompanyHostingCompany by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.CompanyHostingCompany}
	 * @throws Exception the exception
	 * @see    CompanyHostingCompany
	 */
	public List<CompanyHostingCompany> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load CompanyHostingCompany.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.CompanyHostingCompany}
	 * @throws Exception the exception
	 */
	public List<CompanyHostingCompany> allCompanyHostingCompany(int first, int pageSize) throws Exception {
		return dao.allCompanyHostingCompany(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of CompanyHostingCompany for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the CompanyHostingCompany
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(CompanyHostingCompany.class);
	}
	
    /**
     * Lazy load CompanyHostingCompany with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 CompanyHostingCompany class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.CompanyHostingCompany} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<CompanyHostingCompany> allCompanyHostingCompany(Class<CompanyHostingCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String sql = "Select o from CompanyHostingCompany o left join fetch o.hostingCompany left join fetch o.company";
		return ( List<CompanyHostingCompany>)dao.sortAndFilter(first,pageSize,sortField,sortOrder,filters, sql);
	
	}
	
    /**
     * Get count of CompanyHostingCompany for lazy load with filters.
     *
     * @author TechFinium
     * @param entity CompanyHostingCompany class
     * @param filters the filters
     * @return Number of rows in the CompanyHostingCompany entity
     * @throws Exception the exception
     */	
	public int count(Class<CompanyHostingCompany> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/**
	 * Find count by company and housting company.
	 *
	 * @param hostingCompany the hosting company
	 * @param company the company
	 * @return the long
	 * @throws Exception the exception
	 */
	public Long findCountByCompanyAndHoustingCompany(HostingCompany hostingCompany, Company company) throws Exception {
		return dao.findCountByCompanyAndHoustingCompany(hostingCompany, company);
	}
}
