package haj.com.service;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import haj.com.dao.AssessorModReRegistrationDAO;
import haj.com.entity.AssessorModReRegistration;
import haj.com.entity.ReviewCommitteeMeetingAgenda;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class AssessorModReRegistrationService extends AbstractService {
	/** The dao. */
	private AssessorModReRegistrationDAO dao = new AssessorModReRegistrationDAO();

	/**
	 * Get all AssessorModReRegistration
 	 * @author TechFinium 
 	 * @see   AssessorModReRegistration
 	 * @return a list of {@link haj.com.entity.AssessorModReRegistration}
	 * @throws Exception the exception
 	 */
	public List<AssessorModReRegistration> allAssessorModReRegistration() throws Exception {
	  	return dao.allAssessorModReRegistration();
	}


	/**
	 * Create or update AssessorModReRegistration.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     AssessorModReRegistration
	 */
	public void create(AssessorModReRegistration entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  AssessorModReRegistration.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    AssessorModReRegistration
	 */
	public void update(AssessorModReRegistration entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  AssessorModReRegistration.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    AssessorModReRegistration
	 */
	public void delete(AssessorModReRegistration entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.AssessorModReRegistration}
	 * @throws Exception the exception
	 * @see    AssessorModReRegistration
	 */
	public AssessorModReRegistration findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find AssessorModReRegistration by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.AssessorModReRegistration}
	 * @throws Exception the exception
	 * @see    AssessorModReRegistration
	 */
	public List<AssessorModReRegistration> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load AssessorModReRegistration
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.AssessorModReRegistration}
	 * @throws Exception the exception
	 */
	public List<AssessorModReRegistration> allAssessorModReRegistration(int first, int pageSize) throws Exception {
		return dao.allAssessorModReRegistration(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of AssessorModReRegistration for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the AssessorModReRegistration
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(AssessorModReRegistration.class);
	}
	
    /**
     * Lazy load AssessorModReRegistration with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 AssessorModReRegistration class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.AssessorModReRegistration} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<AssessorModReRegistration> allAssessorModReRegistration(Class<AssessorModReRegistration> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<AssessorModReRegistration>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of AssessorModReRegistration for lazy load with filters
     * @author TechFinium 
     * @param entity AssessorModReRegistration class
     * @param filters the filters
     * @return Number of rows in the AssessorModReRegistration entity
     * @throws Exception the exception     
     */	
	public int count(Class<AssessorModReRegistration> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModReRegistration> findByAssessorModeratorApplication(Long id) throws Exception {
	 	return dao.findByAssessorModeratorApplication(id);
	}
	
	@SuppressWarnings("unchecked")
	public int countByReviewCommitteeMeetingAgenda(ReviewCommitteeMeetingAgenda reviewCommitteeMeetingAgenda) throws Exception {
		String hql = "select count(o) from AssessorModReRegistration o where o.reviewCommitteeMeetingAgenda.id =:id or o.previousReviewCommitteeMeetingAgenda.id =:id";
		Map<String, Object> filters=new HashMap<>();
		filters.put("id", reviewCommitteeMeetingAgenda.getId());
		return dao.countWhere(filters, hql);

	}
}
