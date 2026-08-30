package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.ProvinceDAO;
import haj.com.entity.Province;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class ProvinceService.
 */
public class ProvinceService extends AbstractService {

	/** The dao. */
	private ProvinceDAO dao = new ProvinceDAO();

	/**
	 * Get all Province.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Province}
	 * @throws Exception the exception
	 * @see    Province
	 */
	public List<Province> allProvince() throws Exception {
	  	return dao.allProvince();
	}

	/**
	 * Create or update  Province.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Province
	 */
	public void create(Province entity) throws Exception  {
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}

	/**
	 * Update  Province.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Province
	 */
	public void update(Province entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Province.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Province
	 */
	public void delete(Province entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a Province object
	 * @throws Exception the exception
	 * @see    Province
	 */
	public Province findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Province by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Province}
	 * @throws Exception the exception
	 * @see    Province
	 */
	public List<Province> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load Province.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Province}
	 * @throws Exception the exception
	 */
	public List<Province> allProvince(int first, int pageSize) throws Exception {
		return dao.allProvince(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Count.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Province
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Province.class);
	}
	
    /**
     * All province.
     *
     * @author TechFinium
     * @param class1 Province class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return a list of {@link haj.com.entity.Province} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Province> allProvince(Class<Province> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from Province o ";
		return ( List<Province>)dao.sortAndFilter(first,pageSize,sortField,sortOrder,filters, hql);
	
	}
	
    /**
     * Count.
     *
     * @param entity Province class
     * @param filters the filters
     * @return Number of rows in the Province entity
     * @throws Exception the exception
     */	
	public int count(Class<Province> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public Province findByCode(String code) throws Exception {
		return dao.findByCode(code);
	}
}
