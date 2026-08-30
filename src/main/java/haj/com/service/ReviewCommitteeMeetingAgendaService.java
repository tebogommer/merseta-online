package haj.com.service;

import java.util.Hashtable;
import java.util.List;

import haj.com.dao.ReviewCommitteeMeetingAgendaDAO;
import haj.com.entity.ReviewCommitteeMeetingAgenda;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class ReviewCommitteeMeetingAgendaService extends AbstractService {
	
	/** The dao. */
	private ReviewCommitteeMeetingAgendaDAO dao = new ReviewCommitteeMeetingAgendaDAO();

	/**
	 * Get all ReviewCommitteeMeetingAgenda
 	 * @author TechFinium 
 	 * @see   ReviewCommitteeMeetingAgenda
 	 * @return a list of {@link haj.com.entity.ReviewCommitteeMeetingAgenda}
	 * @throws Exception the exception
 	 */
	public List<ReviewCommitteeMeetingAgenda> allReviewCommitteeMeetingAgenda() throws Exception {
	  	return dao.allReviewCommitteeMeetingAgenda();
	}


	/**
	 * Create or update ReviewCommitteeMeetingAgenda.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     ReviewCommitteeMeetingAgenda
	 */
	public void create(ReviewCommitteeMeetingAgenda entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}
	
	public void deleteBatch(List<IDataEntity> entityList) throws Exception
	{
		dao.deleteBatch(entityList);
	}


	/**
	 * Update  ReviewCommitteeMeetingAgenda.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ReviewCommitteeMeetingAgenda
	 */
	public void update(ReviewCommitteeMeetingAgenda entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  ReviewCommitteeMeetingAgenda.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ReviewCommitteeMeetingAgenda
	 */
	public void delete(ReviewCommitteeMeetingAgenda entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ReviewCommitteeMeetingAgenda}
	 * @throws Exception the exception
	 * @see    ReviewCommitteeMeetingAgenda
	 */
	public ReviewCommitteeMeetingAgenda findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find ReviewCommitteeMeetingAgenda by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.ReviewCommitteeMeetingAgenda}
	 * @throws Exception the exception
	 * @see    ReviewCommitteeMeetingAgenda
	 */
	public List<ReviewCommitteeMeetingAgenda> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load ReviewCommitteeMeetingAgenda
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.ReviewCommitteeMeetingAgenda}
	 * @throws Exception the exception
	 */
	public List<ReviewCommitteeMeetingAgenda> allReviewCommitteeMeetingAgenda(int first, int pageSize) throws Exception {
		return dao.allReviewCommitteeMeetingAgenda(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of ReviewCommitteeMeetingAgenda for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the ReviewCommitteeMeetingAgenda
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(ReviewCommitteeMeetingAgenda.class);
	}
	
    /**
     * Lazy load ReviewCommitteeMeetingAgenda with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 ReviewCommitteeMeetingAgenda class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.ReviewCommitteeMeetingAgenda} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<ReviewCommitteeMeetingAgenda> allReviewCommitteeMeetingAgenda(Class<ReviewCommitteeMeetingAgenda> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<ReviewCommitteeMeetingAgenda>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of ReviewCommitteeMeetingAgenda for lazy load with filters
     * @author TechFinium 
     * @param entity ReviewCommitteeMeetingAgenda class
     * @param filters the filters
     * @return Number of rows in the ReviewCommitteeMeetingAgenda entity
     * @throws Exception the exception     
     */	
	public int count(Class<ReviewCommitteeMeetingAgenda> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/**
	 * Find ReviewCommitteeMeetingAgenda by ReviewCommitteeMettin
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    ReviewCommitteeMeetingAgenda
  	 * @return a list of {@link haj.com.entity.ReviewCommitteeMeetingAgenda}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ReviewCommitteeMeetingAgenda> findByReviewCommitteeMeeting(Long  id) throws Exception {
		return dao.findByReviewCommitteeMeeting(id);
	}
	
	/**
	 * Find ReviewCommitteeMeetingAgenda by meetingAgenda
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    ReviewCommitteeMeetingAgenda
  	 * @return a list of {@link haj.com.entity.ReviewCommitteeMeetingAgenda}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ReviewCommitteeMeetingAgenda> findByMeetingAgenda(Long  id) throws Exception {
		return dao.findByMeetingAgenda(id);
	}
	
	@SuppressWarnings("unchecked")
	public ReviewCommitteeMeetingAgenda findByMeetingAgendaAndReviewCommitteeMeeting(Long  meetingAgendaID,Long reviewCommitteeMeetingID) throws Exception {
	 	return dao.findByMeetingAgendaAndReviewCommitteeMeeting(meetingAgendaID, reviewCommitteeMeetingID);
	}
}
