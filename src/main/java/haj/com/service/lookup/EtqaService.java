package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.EtqaDAO;
import haj.com.entity.lookup.Etqa;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class EtqaService.
 */
public class EtqaService extends AbstractService {
	/** The dao. */
	private EtqaDAO dao = new EtqaDAO();

	/**
	 * Get all Etqa.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Etqa}
	 * @throws Exception the exception
	 * @see   Etqa
	 */
	public List<Etqa> allEtqa() throws Exception {
	  	return dao.allEtqa();
	}
	
	
	public List<Etqa> allEtqaRemoveMerSeta() throws Exception {
	  	return dao.allEtqaRemoveMerSeta();
	}
	
	public List<Etqa> allQctoEtqa() throws Exception {
	  	return dao.allQctoEtqa();
	}



	/**
	 * Create or update Etqa.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Etqa
	 */
	public void create(Etqa entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  Etqa.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Etqa
	 */
	public void update(Etqa entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Etqa.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Etqa
	 */
	public void delete(Etqa entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Etqa}
	 * @throws Exception the exception
	 * @see    Etqa
	 */
	public Etqa findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Etqa by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Etqa}
	 * @throws Exception the exception
	 * @see    Etqa
	 */
	public List<Etqa> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load Etqa.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Etqa}
	 * @throws Exception the exception
	 */
	public List<Etqa> allEtqa(int first, int pageSize) throws Exception {
		return dao.allEtqa(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Etqa for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Etqa
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Etqa.class);
	}
	
    /**
     * Lazy load Etqa with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 Etqa class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Etqa} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Etqa> allEtqa(Class<Etqa> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Etqa>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of Etqa for lazy load with filters.
     *
     * @author TechFinium
     * @param entity Etqa class
     * @param filters the filters
     * @return Number of rows in the Etqa entity
     * @throws Exception the exception
     */	
	public int count(Class<Etqa> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the etqa
	 * @throws Exception the exception
	 */
	public Etqa findByCode(String code) throws Exception { 
		if (StringUtils.isBlank(code)) return null;
		return dao.findByCode(code.trim());
	}
}
