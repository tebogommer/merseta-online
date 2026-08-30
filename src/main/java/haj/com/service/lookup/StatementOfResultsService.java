package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.StatementOfResultsDAO;
import haj.com.entity.lookup.StatementOfResults;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class StatementOfResultsService.
 */
public class StatementOfResultsService extends AbstractService {
	/** The dao. */
	private StatementOfResultsDAO dao = new StatementOfResultsDAO();

	/**
	 * Get all StatementOfResults.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.StatementOfResults}
	 * @throws Exception the exception
	 * @see   StatementOfResults
	 */
	public List<StatementOfResults> allStatementOfResults() throws Exception {
	  	return dao.allStatementOfResults();
	}


	/**
	 * Create or update StatementOfResults.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     StatementOfResults
	 */
	public void create(StatementOfResults entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  StatementOfResults.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    StatementOfResults
	 */
	public void update(StatementOfResults entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  StatementOfResults.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    StatementOfResults
	 */
	public void delete(StatementOfResults entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.StatementOfResults}
	 * @throws Exception the exception
	 * @see    StatementOfResults
	 */
	public StatementOfResults findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find StatementOfResults by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.StatementOfResults}
	 * @throws Exception the exception
	 * @see    StatementOfResults
	 */
	public List<StatementOfResults> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load StatementOfResults.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.StatementOfResults}
	 * @throws Exception the exception
	 */
	public List<StatementOfResults> allStatementOfResults(int first, int pageSize) throws Exception {
		return dao.allStatementOfResults(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of StatementOfResults for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the StatementOfResults
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(StatementOfResults.class);
	}
	
    /**
     * Lazy load StatementOfResults with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 StatementOfResults class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.StatementOfResults} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<StatementOfResults> allStatementOfResults(Class<StatementOfResults> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<StatementOfResults>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of StatementOfResults for lazy load with filters.
     *
     * @author TechFinium
     * @param entity StatementOfResults class
     * @param filters the filters
     * @return Number of rows in the StatementOfResults entity
     * @throws Exception the exception
     */	
	public int count(Class<StatementOfResults> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
