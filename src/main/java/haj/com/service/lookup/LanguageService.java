package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.LanguageDAO;
import haj.com.entity.lookup.Language;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class LanguageService.
 */
public class LanguageService extends AbstractService {
	/** The dao. */
	private LanguageDAO dao = new LanguageDAO();

	/**
	 * Get all Language.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Language}
	 * @throws Exception the exception
	 * @see   Language
	 */
	public List<Language> allLanguage() throws Exception {
	  	return dao.allLanguage();
	}


	/**
	 * Create or update Language.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Language
	 */
	public void create(Language entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  Language.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Language
	 */
	public void update(Language entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Language.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Language
	 */
	public void delete(Language entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Language}
	 * @throws Exception the exception
	 * @see    Language
	 */
	public Language findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Language by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Language}
	 * @throws Exception the exception
	 * @see    Language
	 */
	public List<Language> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load Language.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Language}
	 * @throws Exception the exception
	 */
	public List<Language> allLanguage(int first, int pageSize) throws Exception {
		return dao.allLanguage(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Language for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Language
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Language.class);
	}
	
    /**
     * Lazy load Language with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 Language class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Language} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Language> allLanguage(Class<Language> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Language>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of Language for lazy load with filters.
     *
     * @author TechFinium
     * @param entity Language class
     * @param filters the filters
     * @return Number of rows in the Language entity
     * @throws Exception the exception
     */	
	public int count(Class<Language> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
