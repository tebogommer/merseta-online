package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.ModulesCategoryDAO;
import haj.com.entity.lookup.ModulesCategory;
import haj.com.framework.AbstractService;

public class ModulesCategoryService extends AbstractService {
	/** The dao. */
	private ModulesCategoryDAO dao = new ModulesCategoryDAO();

	/**
	 * Get all ModulesCategory
 	 * @author TechFinium 
 	 * @see   ModulesCategory
 	 * @return a list of {@link haj.com.entity.ModulesCategory}
	 * @throws Exception the exception
 	 */
	public List<ModulesCategory> allModulesCategory() throws Exception {
	  	return dao.allModulesCategory();
	}


	/**
	 * Create or update ModulesCategory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     ModulesCategory
	 */
	public void create(ModulesCategory entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  ModulesCategory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ModulesCategory
	 */
	public void update(ModulesCategory entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  ModulesCategory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ModulesCategory
	 */
	public void delete(ModulesCategory entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ModulesCategory}
	 * @throws Exception the exception
	 * @see    ModulesCategory
	 */
	public ModulesCategory findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find ModulesCategory by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.ModulesCategory}
	 * @throws Exception the exception
	 * @see    ModulesCategory
	 */
	public List<ModulesCategory> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load ModulesCategory
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.ModulesCategory}
	 * @throws Exception the exception
	 */
	public List<ModulesCategory> allModulesCategory(int first, int pageSize) throws Exception {
		return dao.allModulesCategory(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of ModulesCategory for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the ModulesCategory
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(ModulesCategory.class);
	}
	
    /**
     * Lazy load ModulesCategory with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 ModulesCategory class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.ModulesCategory} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<ModulesCategory> allModulesCategory(Class<ModulesCategory> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<ModulesCategory>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of ModulesCategory for lazy load with filters
     * @author TechFinium 
     * @param entity ModulesCategory class
     * @param filters the filters
     * @return Number of rows in the ModulesCategory entity
     * @throws Exception the exception     
     */	
	public int count(Class<ModulesCategory> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
