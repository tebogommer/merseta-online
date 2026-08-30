package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.ModulesTitleDAO;
import haj.com.entity.lookup.ModulesTitle;
import haj.com.framework.AbstractService;

public class ModulesTitleService extends AbstractService {
	/** The dao. */
	private ModulesTitleDAO dao = new ModulesTitleDAO();

	/**
	 * Get all ModulesTitle
 	 * @author TechFinium 
 	 * @see   ModulesTitle
 	 * @return a list of {@link haj.com.entity.ModulesTitle}
	 * @throws Exception the exception
 	 */
	public List<ModulesTitle> allModulesTitle() throws Exception {
	  	return dao.allModulesTitle();
	}


	/**
	 * Create or update ModulesTitle.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     ModulesTitle
	 */
	public void create(ModulesTitle entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  ModulesTitle.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ModulesTitle
	 */
	public void update(ModulesTitle entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  ModulesTitle.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ModulesTitle
	 */
	public void delete(ModulesTitle entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ModulesTitle}
	 * @throws Exception the exception
	 * @see    ModulesTitle
	 */
	public ModulesTitle findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find ModulesTitle by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.ModulesTitle}
	 * @throws Exception the exception
	 * @see    ModulesTitle
	 */
	public List<ModulesTitle> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load ModulesTitle
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.ModulesTitle}
	 * @throws Exception the exception
	 */
	public List<ModulesTitle> allModulesTitle(int first, int pageSize) throws Exception {
		return dao.allModulesTitle(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of ModulesTitle for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the ModulesTitle
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(ModulesTitle.class);
	}
	
    /**
     * Lazy load ModulesTitle with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 ModulesTitle class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.ModulesTitle} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<ModulesTitle> allModulesTitle(Class<ModulesTitle> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<ModulesTitle>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of ModulesTitle for lazy load with filters
     * @author TechFinium 
     * @param entity ModulesTitle class
     * @param filters the filters
     * @return Number of rows in the ModulesTitle entity
     * @throws Exception the exception     
     */	
	public int count(Class<ModulesTitle> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
