package haj.com.service.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.TitleDAO;
import haj.com.entity.lookup.Title;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class TitleService.
 */
public class TitleService extends AbstractService {
	/** The dao. */
	private TitleDAO dao = new TitleDAO();

	/**
	 * Get all Title.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Title}
	 * @throws Exception the exception
	 * @see   Title
	 */
	public List<Title> allTitle() throws Exception {
	  	return dao.allTitle();
	}


	/**
	 * Create or update Title.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Title
	 */
	public void create(Title entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  Title.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Title
	 */
	public void update(Title entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Title.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Title
	 */
	public void delete(Title entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Title}
	 * @throws Exception the exception
	 * @see    Title
	 */
	public Title findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Title by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Title}
	 * @throws Exception the exception
	 * @see    Title
	 */
	public List<Title> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	public Title findByDescription(String description) throws Exception {
	 	return dao.findByDescription(description);
	}
	
	/**
	 * Lazy load Title.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Title}
	 * @throws Exception the exception
	 */
	public List<Title> allTitle(int first, int pageSize) throws Exception {
		return dao.allTitle(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Title for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Title
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Title.class);
	}
	
    /**
     * Lazy load Title with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 Title class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Title} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Title> allTitle(Class<Title> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Title>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of Title for lazy load with filters.
     *
     * @author TechFinium
     * @param entity Title class
     * @param filters the filters
     * @return Number of rows in the Title entity
     * @throws Exception the exception
     */	
	public int count(Class<Title> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
