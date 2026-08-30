package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.ModulesUnitStandardsDAO;
import haj.com.entity.lookup.ModulesUnitStandards;
import haj.com.framework.AbstractService;

public class ModulesUnitStandardsService extends AbstractService {
	/** The dao. */
	private ModulesUnitStandardsDAO dao = new ModulesUnitStandardsDAO();

	/**
	 * Get all ModulesUnitStandards
 	 * @author TechFinium 
 	 * @see   ModulesUnitStandards
 	 * @return a list of {@link haj.com.entity.ModulesUnitStandards}
	 * @throws Exception the exception
 	 */
	public List<ModulesUnitStandards> allModulesUnitStandards() throws Exception {
	  	return dao.allModulesUnitStandards();
	}


	/**
	 * Create or update ModulesUnitStandards.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     ModulesUnitStandards
	 */
	public void create(ModulesUnitStandards entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  ModulesUnitStandards.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ModulesUnitStandards
	 */
	public void update(ModulesUnitStandards entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  ModulesUnitStandards.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ModulesUnitStandards
	 */
	public void delete(ModulesUnitStandards entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ModulesUnitStandards}
	 * @throws Exception the exception
	 * @see    ModulesUnitStandards
	 */
	public ModulesUnitStandards findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find ModulesUnitStandards by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.ModulesUnitStandards}
	 * @throws Exception the exception
	 * @see    ModulesUnitStandards
	 */
	public List<ModulesUnitStandards> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load ModulesUnitStandards
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.ModulesUnitStandards}
	 * @throws Exception the exception
	 */
	public List<ModulesUnitStandards> allModulesUnitStandards(int first, int pageSize) throws Exception {
		return dao.allModulesUnitStandards(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of ModulesUnitStandards for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the ModulesUnitStandards
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(ModulesUnitStandards.class);
	}
	
    /**
     * Lazy load ModulesUnitStandards with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 ModulesUnitStandards class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.ModulesUnitStandards} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<ModulesUnitStandards> allModulesUnitStandards(Class<ModulesUnitStandards> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<ModulesUnitStandards>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of ModulesUnitStandards for lazy load with filters
     * @author TechFinium 
     * @param entity ModulesUnitStandards class
     * @param filters the filters
     * @return Number of rows in the ModulesUnitStandards entity
     * @throws Exception the exception     
     */	
	public int count(Class<ModulesUnitStandards> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
