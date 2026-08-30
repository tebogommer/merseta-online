package haj.com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import haj.com.dao.AssessorModExtensionOfScopeDAO;
import haj.com.entity.AssessorModExtensionOfScope;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.ReviewCommitteeMeetingAgenda;
import haj.com.entity.ReviewCommitteeMeetingUsers;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class AssessorModExtensionOfScopeService extends AbstractService {
	/** The dao. */
	private AssessorModExtensionOfScopeDAO dao = new AssessorModExtensionOfScopeDAO();
	private ReviewCommitteeMeetingAgendaService reviewCommitteeMeetingAgendaSevice=new ReviewCommitteeMeetingAgendaService();
	ReviewCommitteeMeetingUsersService reviewCommitteeMeetingUsersService=new ReviewCommitteeMeetingUsersService();

	/**
	 * Get all AssessorModExtensionOfScope
 	 * @author TechFinium 
 	 * @see   AssessorModExtensionOfScope
 	 * @return a list of {@link haj.com.entity.AssessorModExtensionOfScope}
	 * @throws Exception the exception
 	 */
	public List<AssessorModExtensionOfScope> allAssessorModExtensionOfScope() throws Exception {
	  	return dao.allAssessorModExtensionOfScope();
	}


	/**
	 * Create or update AssessorModExtensionOfScope.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     AssessorModExtensionOfScope
	 */
	public void create(AssessorModExtensionOfScope entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  AssessorModExtensionOfScope.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    AssessorModExtensionOfScope
	 */
	public void update(AssessorModExtensionOfScope entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  AssessorModExtensionOfScope.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    AssessorModExtensionOfScope
	 */
	public void delete(AssessorModExtensionOfScope entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.AssessorModExtensionOfScope}
	 * @throws Exception the exception
	 * @see    AssessorModExtensionOfScope
	 */
	public AssessorModExtensionOfScope findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find AssessorModExtensionOfScope by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.AssessorModExtensionOfScope}
	 * @throws Exception the exception
	 * @see    AssessorModExtensionOfScope
	 */
	public List<AssessorModExtensionOfScope> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load AssessorModExtensionOfScope
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.AssessorModExtensionOfScope}
	 * @throws Exception the exception
	 */
	public List<AssessorModExtensionOfScope> allAssessorModExtensionOfScope(int first, int pageSize) throws Exception {
		return dao.allAssessorModExtensionOfScope(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of AssessorModExtensionOfScope for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the AssessorModExtensionOfScope
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(AssessorModExtensionOfScope.class);
	}
	
    /**
     * Lazy load AssessorModExtensionOfScope with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 AssessorModExtensionOfScope class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.AssessorModExtensionOfScope} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<AssessorModExtensionOfScope> allAssessorModExtensionOfScope(Class<AssessorModExtensionOfScope> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<AssessorModExtensionOfScope>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of AssessorModExtensionOfScope for lazy load with filters
     * @author TechFinium 
     * @param entity AssessorModExtensionOfScope class
     * @param filters the filters
     * @return Number of rows in the AssessorModExtensionOfScope entity
     * @throws Exception the exception     
     */	
	public int count(Class<AssessorModExtensionOfScope> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModExtensionOfScope> sortAndFilterWhere(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		//String hql = "select o from AssessorModExtensionOfScope o "+joins+" where o.status =:status";
		String hql = "select o from AssessorModExtensionOfScope o where o.status =:status";
		return resolveMettingusersAndAgendas((List<AssessorModExtensionOfScope>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters,hql));

	}
	
	public List<AssessorModExtensionOfScope>  resolveMettingusersAndAgendas(List<AssessorModExtensionOfScope> amList)
	{
		for(AssessorModExtensionOfScope amApp:amList)
		{
			try {
				amApp.getAssessorModeratorApplication().getReviewCommitteeMeeting().setReviewCommitteeMeetingUsersList((ArrayList<ReviewCommitteeMeetingUsers>) reviewCommitteeMeetingUsersService.findByReviewCommitteeMeeting(amApp.getAssessorModeratorApplication().getReviewCommitteeMeeting().getId()));
				amApp.getAssessorModeratorApplication().getReviewCommitteeMeeting().setReviewCommitteeMeetingAgendaList((ArrayList<ReviewCommitteeMeetingAgenda>) reviewCommitteeMeetingAgendaSevice.findByReviewCommitteeMeeting(amApp.getAssessorModeratorApplication().getReviewCommitteeMeeting().getId()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return amList;
	}


	public List<AssessorModExtensionOfScope> findByAssessorModeratorApplication(Long id) throws Exception {
		return  dao.findByAssessorModeratorApplication(id);
	}
	
	@SuppressWarnings("unchecked")
	public int countByReviewCommitteeMeetingAgenda(ReviewCommitteeMeetingAgenda reviewCommitteeMeetingAgenda) throws Exception {
		String hql = "select count(o) from AssessorModExtensionOfScope o where o.reviewCommitteeMeetingAgenda.id =:id or o.previousReviewCommitteeMeetingAgenda.id =:id";
		Map<String, Object> filters=new HashMap<>();
		filters.put("id", reviewCommitteeMeetingAgenda.getId());
		return dao.countWhere(filters, hql);

	}
}
