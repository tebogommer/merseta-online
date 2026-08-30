package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.NonCreditBearingIntervationTitleDAO;
import haj.com.entity.lookup.NonCreditBearingIntervationTitle;
import haj.com.framework.AbstractService;

public class NonCreditBearingIntervationTitleService extends AbstractService {
	/** The dao. */
	private NonCreditBearingIntervationTitleDAO dao = new NonCreditBearingIntervationTitleDAO();
	
	private static NonCreditBearingIntervationTitleService nonCreditBearingIntervationTitleService;
	public static synchronized NonCreditBearingIntervationTitleService instance() {
		if (nonCreditBearingIntervationTitleService == null) {
			nonCreditBearingIntervationTitleService = new NonCreditBearingIntervationTitleService();
		}
		return nonCreditBearingIntervationTitleService;
	}

	/**
	 * Get all NonCreditBearingIntervationTitle
 	 * @author TechFinium 
 	 * @see   NonCreditBearingIntervationTitle
 	 * @return a list of {@link haj.com.entity.NonCreditBearingIntervationTitle}
	 * @throws Exception the exception
 	 */
	public List<NonCreditBearingIntervationTitle> allNonCreditBearingIntervationTitle() throws Exception {
	  	return dao.allNonCreditBearingIntervationTitle();
	}


	/**
	 * Create or update NonCreditBearingIntervationTitle.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     NonCreditBearingIntervationTitle
	 */
	public void create(NonCreditBearingIntervationTitle entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  NonCreditBearingIntervationTitle.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    NonCreditBearingIntervationTitle
	 */
	public void update(NonCreditBearingIntervationTitle entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  NonCreditBearingIntervationTitle.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    NonCreditBearingIntervationTitle
	 */
	public void delete(NonCreditBearingIntervationTitle entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.NonCreditBearingIntervationTitle}
	 * @throws Exception the exception
	 * @see    NonCreditBearingIntervationTitle
	 */
	public NonCreditBearingIntervationTitle findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find NonCreditBearingIntervationTitle by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.NonCreditBearingIntervationTitle}
	 * @throws Exception the exception
	 * @see    NonCreditBearingIntervationTitle
	 */
	public List<NonCreditBearingIntervationTitle> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load NonCreditBearingIntervationTitle
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.NonCreditBearingIntervationTitle}
	 * @throws Exception the exception
	 */
	public List<NonCreditBearingIntervationTitle> allNonCreditBearingIntervationTitle(int first, int pageSize) throws Exception {
		return dao.allNonCreditBearingIntervationTitle(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of NonCreditBearingIntervationTitle for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the NonCreditBearingIntervationTitle
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(NonCreditBearingIntervationTitle.class);
	}
	
    /**
     * Lazy load NonCreditBearingIntervationTitle with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 NonCreditBearingIntervationTitle class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.NonCreditBearingIntervationTitle} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<NonCreditBearingIntervationTitle> allNonCreditBearingIntervationTitle(Class<NonCreditBearingIntervationTitle> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<NonCreditBearingIntervationTitle>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of NonCreditBearingIntervationTitle for lazy load with filters
     * @author TechFinium 
     * @param entity NonCreditBearingIntervationTitle class
     * @param filters the filters
     * @return Number of rows in the NonCreditBearingIntervationTitle entity
     * @throws Exception the exception     
     */	
	public int count(Class<NonCreditBearingIntervationTitle> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
