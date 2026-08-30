package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.SetaDAO;
import haj.com.entity.lookup.Seta;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class SetaService.
 */
public class SetaService extends AbstractService {
	/** The dao. */
	private SetaDAO dao = new SetaDAO();

	/**
	 * Get all Seta.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Seta}
	 * @throws Exception the exception
	 * @see   Seta
	 */
	public List<Seta> allSeta() throws Exception {
	  	return dao.allSeta();
	}


	/**
	 * Create or update Seta.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Seta
	 */
	public void create(Seta entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		   if (entity.getId() ==null) {
			 entity.setCreateDate(new java.util.Date());
			 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  Seta.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Seta
	 */
	public void update(Seta entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Seta.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Seta
	 */
	public void delete(Seta entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Seta}
	 * @throws Exception the exception
	 * @see    Seta
	 */
	public Seta findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Seta by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Seta}
	 * @throws Exception the exception
	 * @see    Seta
	 */
	public List<Seta> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load Seta.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Seta}
	 * @throws Exception the exception
	 */
	public List<Seta> allSeta(int first, int pageSize) throws Exception {
		return dao.allSeta(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Seta for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Seta
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Seta.class);
	}
	
    /**
     * Lazy load Seta with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 Seta class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Seta} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Seta> allSeta(Class<Seta> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Seta>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of Seta for lazy load with filters.
     *
     * @author TechFinium
     * @param entity Seta class
     * @param filters the filters
     * @return Number of rows in the Seta entity
     * @throws Exception the exception
     */	
	public int count(Class<Seta> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the Seta
	 * @throws Exception the exception
	 */
	public Seta findBySetmisCode(String code) throws Exception { 
		if (StringUtils.isBlank(code)) return null;
		return dao.findBySetmisCode(code.trim());
	}
}
