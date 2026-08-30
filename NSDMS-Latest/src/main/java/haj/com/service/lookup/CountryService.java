package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.CountryDAO;
import haj.com.entity.lookup.Country;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class CountryService.
 */
public class CountryService extends AbstractService {
	/** The dao. */
	private CountryDAO dao = new CountryDAO();

	/**
	 * Get all Country.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Country}
	 * @throws Exception the exception
	 * @see   Country
	 */
	public List<Country> allCountry() throws Exception {
	  	return dao.allCountry();
	}


	/**
	 * Create or update Country.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Country
	 */
	public void create(Country entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  Country.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Country
	 */
	public void update(Country entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Country.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Country
	 */
	public void delete(Country entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Country}
	 * @throws Exception the exception
	 * @see    Country
	 */
	public Country findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Country by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Country}
	 * @throws Exception the exception
	 * @see    Country
	 */
	public List<Country> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load Country.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Country}
	 * @throws Exception the exception
	 */
	public List<Country> allCountry(int first, int pageSize) throws Exception {
		return dao.allCountry(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Country for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Country
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Country.class);
	}
	
    /**
     * Lazy load Country with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 Country class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Country} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Country> allCountry(Class<Country> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Country>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of Country for lazy load with filters.
     *
     * @author TechFinium
     * @param entity Country class
     * @param filters the filters
     * @return Number of rows in the Country entity
     * @throws Exception the exception
     */	
	public int count(Class<Country> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
