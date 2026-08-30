package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.STATSSAAreaCodeDAO;
import haj.com.entity.lookup.STATSSAAreaCode;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class STATSSAAreaCodeService.
 */
public class STATSSAAreaCodeService extends AbstractService {
	/** The dao. */
	private STATSSAAreaCodeDAO dao = new STATSSAAreaCodeDAO();

	/**
	 * Get all STATSSAAreaCode.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.STATSSAAreaCode}
	 * @throws Exception the exception
	 * @see   STATSSAAreaCode
	 */
	public List<STATSSAAreaCode> allSTATSSAAreaCode() throws Exception {
	  	return dao.allSTATSSAAreaCode();
	}


	/**
	 * Create or update STATSSAAreaCode.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     STATSSAAreaCode
	 */
	public void create(STATSSAAreaCode entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  STATSSAAreaCode.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    STATSSAAreaCode
	 */
	public void update(STATSSAAreaCode entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  STATSSAAreaCode.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    STATSSAAreaCode
	 */
	public void delete(STATSSAAreaCode entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.STATSSAAreaCode}
	 * @throws Exception the exception
	 * @see    STATSSAAreaCode
	 */
	public STATSSAAreaCode findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find STATSSAAreaCode by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.STATSSAAreaCode}
	 * @throws Exception the exception
	 * @see    STATSSAAreaCode
	 */
	public List<STATSSAAreaCode> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load STATSSAAreaCode.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.STATSSAAreaCode}
	 * @throws Exception the exception
	 */
	public List<STATSSAAreaCode> allSTATSSAAreaCode(int first, int pageSize) throws Exception {
		return dao.allSTATSSAAreaCode(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of STATSSAAreaCode for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the STATSSAAreaCode
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(STATSSAAreaCode.class);
	}
	
    /**
     * Lazy load STATSSAAreaCode with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 STATSSAAreaCode class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.STATSSAAreaCode} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<STATSSAAreaCode> allSTATSSAAreaCode(Class<STATSSAAreaCode> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<STATSSAAreaCode>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of STATSSAAreaCode for lazy load with filters.
     *
     * @author TechFinium
     * @param entity STATSSAAreaCode class
     * @param filters the filters
     * @return Number of rows in the STATSSAAreaCode entity
     * @throws Exception the exception
     */	
	public int count(Class<STATSSAAreaCode> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
