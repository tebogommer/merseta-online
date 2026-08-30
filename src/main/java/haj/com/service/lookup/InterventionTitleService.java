package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.InterventionTitleDAO;
import haj.com.entity.lookup.InterventionTitle;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class InterventionTitleService.
 */
public class InterventionTitleService extends AbstractService {
	/** The dao. */
	private InterventionTitleDAO dao = new InterventionTitleDAO();

	/**
	 * Get all InterventionTitle.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.InterventionTitle}
	 * @throws Exception the exception
	 * @see   InterventionTitle
	 */
	public List<InterventionTitle> allInterventionTitle() throws Exception {
	  	return dao.allInterventionTitle();
	}


	/**
	 * Create or update InterventionTitle.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     InterventionTitle
	 */
	public void create(InterventionTitle entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  InterventionTitle.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    InterventionTitle
	 */
	public void update(InterventionTitle entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  InterventionTitle.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    InterventionTitle
	 */
	public void delete(InterventionTitle entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.InterventionTitle}
	 * @throws Exception the exception
	 * @see    InterventionTitle
	 */
	public InterventionTitle findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find InterventionTitle by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.InterventionTitle}
	 * @throws Exception the exception
	 * @see    InterventionTitle
	 */
	public List<InterventionTitle> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load InterventionTitle.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.InterventionTitle}
	 * @throws Exception the exception
	 */
	public List<InterventionTitle> allInterventionTitle(int first, int pageSize) throws Exception {
		return dao.allInterventionTitle(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of InterventionTitle for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the InterventionTitle
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(InterventionTitle.class);
	}
	
    /**
     * Lazy load InterventionTitle with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 InterventionTitle class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.InterventionTitle} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<InterventionTitle> allInterventionTitle(Class<InterventionTitle> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<InterventionTitle>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of InterventionTitle for lazy load with filters.
     *
     * @author TechFinium
     * @param entity InterventionTitle class
     * @param filters the filters
     * @return Number of rows in the InterventionTitle entity
     * @throws Exception the exception
     */	
	public int count(Class<InterventionTitle> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the intervention type
	 * @throws Exception the exception
	 */
	public InterventionTitle findByCode(String code) throws Exception { 
	if (StringUtils.isBlank(code)) return null;
	return dao.findByCode(code.trim());
}
	
}
