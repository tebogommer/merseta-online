package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.FrequentlyAskedQuestionsDAO;
import haj.com.entity.FrequentlyAskedQuestions;
import haj.com.framework.AbstractService;

public class FrequentlyAskedQuestionsService extends AbstractService {
	/** The dao. */
	private FrequentlyAskedQuestionsDAO dao = new FrequentlyAskedQuestionsDAO();

	/**
	 * Get all FrequentlyAskedQuestions
 	 * @author TechFinium 
 	 * @see   FrequentlyAskedQuestions
 	 * @return a list of {@link haj.com.entity.FrequentlyAskedQuestions}
	 * @throws Exception the exception
 	 */
	public List<FrequentlyAskedQuestions> allFrequentlyAskedQuestions() throws Exception {
	  	return dao.allFrequentlyAskedQuestions();
	}


	/**
	 * Create or update FrequentlyAskedQuestions.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     FrequentlyAskedQuestions
	 */
	public void create(FrequentlyAskedQuestions entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  FrequentlyAskedQuestions.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    FrequentlyAskedQuestions
	 */
	public void update(FrequentlyAskedQuestions entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  FrequentlyAskedQuestions.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    FrequentlyAskedQuestions
	 */
	public void delete(FrequentlyAskedQuestions entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.FrequentlyAskedQuestions}
	 * @throws Exception the exception
	 * @see    FrequentlyAskedQuestions
	 */
	public FrequentlyAskedQuestions findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find FrequentlyAskedQuestions by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.FrequentlyAskedQuestions}
	 * @throws Exception the exception
	 * @see    FrequentlyAskedQuestions
	 */
	public List<FrequentlyAskedQuestions> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load FrequentlyAskedQuestions
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.FrequentlyAskedQuestions}
	 * @throws Exception the exception
	 */
	public List<FrequentlyAskedQuestions> allFrequentlyAskedQuestions(int first, int pageSize) throws Exception {
		return dao.allFrequentlyAskedQuestions(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of FrequentlyAskedQuestions for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the FrequentlyAskedQuestions
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(FrequentlyAskedQuestions.class);
	}
	
    /**
     * Lazy load FrequentlyAskedQuestions with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 FrequentlyAskedQuestions class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.FrequentlyAskedQuestions} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<FrequentlyAskedQuestions> allFrequentlyAskedQuestions(Class<FrequentlyAskedQuestions> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<FrequentlyAskedQuestions>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of FrequentlyAskedQuestions for lazy load with filters
     * @author TechFinium 
     * @param entity FrequentlyAskedQuestions class
     * @param filters the filters
     * @return Number of rows in the FrequentlyAskedQuestions entity
     * @throws Exception the exception     
     */	
	public int count(Class<FrequentlyAskedQuestions> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/**
	 * Find active FAQ.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<FrequentlyAskedQuestions> findActiveFAQ() throws Exception {
		return dao.findActiveFAQ();
	}
	
	/**
	 * Find submitted FAQ.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<FrequentlyAskedQuestions> allSubmittedFAQ() throws Exception {
		return dao.allSubmittedFAQ();
	}
	
	/**
	 * Find submitted FAQ.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<FrequentlyAskedQuestions> allFAQ() throws Exception {
		return dao.allFAQ();
	}
}
