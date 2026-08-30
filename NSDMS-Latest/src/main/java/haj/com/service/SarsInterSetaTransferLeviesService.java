package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.SarsInterSetaTransferLeviesDAO;
import haj.com.framework.AbstractService;
import haj.com.sars.SarsInterSetaTransferLevies;

public class SarsInterSetaTransferLeviesService extends AbstractService {
	/** The dao. */
	private SarsInterSetaTransferLeviesDAO dao = new SarsInterSetaTransferLeviesDAO();

	/**
	 * Get all SarsInterSetaTransferLevies
 	 * @author TechFinium 
 	 * @see   SarsInterSetaTransferLevies
 	 * @return a list of {@link haj.com.sars.SarsInterSetaTransferLevies}
	 * @throws Exception the exception
 	 */
	public List<SarsInterSetaTransferLevies> allSarsInterSetaTransferLevies() throws Exception {
	  	return dao.allSarsInterSetaTransferLevies();
	}


	/**
	 * Create or update SarsInterSetaTransferLevies.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SarsInterSetaTransferLevies
	 */
	public void create(SarsInterSetaTransferLevies entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SarsInterSetaTransferLevies.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SarsInterSetaTransferLevies
	 */
	public void update(SarsInterSetaTransferLevies entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SarsInterSetaTransferLevies.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SarsInterSetaTransferLevies
	 */
	public void delete(SarsInterSetaTransferLevies entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.sars.SarsInterSetaTransferLevies}
	 * @throws Exception the exception
	 * @see    SarsInterSetaTransferLevies
	 */
	public SarsInterSetaTransferLevies findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SarsInterSetaTransferLevies by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.sars.SarsInterSetaTransferLevies}
	 * @throws Exception the exception
	 * @see    SarsInterSetaTransferLevies
	 */
	public List<SarsInterSetaTransferLevies> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SarsInterSetaTransferLevies
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.sars.SarsInterSetaTransferLevies}
	 * @throws Exception the exception
	 */
	public List<SarsInterSetaTransferLevies> allSarsInterSetaTransferLevies(int first, int pageSize) throws Exception {
		return dao.allSarsInterSetaTransferLevies(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SarsInterSetaTransferLevies for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the SarsInterSetaTransferLevies
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SarsInterSetaTransferLevies.class);
	}
	
    /**
     * Lazy load SarsInterSetaTransferLevies with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 SarsInterSetaTransferLevies class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.sars.SarsInterSetaTransferLevies} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SarsInterSetaTransferLevies> allSarsInterSetaTransferLevies(Class<SarsInterSetaTransferLevies> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SarsInterSetaTransferLevies>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SarsInterSetaTransferLevies for lazy load with filters
     * @author TechFinium 
     * @param entity SarsInterSetaTransferLevies class
     * @param filters the filters
     * @return Number of rows in the SarsInterSetaTransferLevies entity
     * @throws Exception the exception     
     */	
	public int count(Class<SarsInterSetaTransferLevies> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
