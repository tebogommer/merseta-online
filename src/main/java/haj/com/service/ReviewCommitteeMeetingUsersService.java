package haj.com.service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import haj.com.dao.ReviewCommitteeMeetingUsersDAO;
import haj.com.entity.ReviewCommitteeMeetingUsers;
import haj.com.entity.Users;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class ReviewCommitteeMeetingUsersService extends AbstractService {
	/** The dao. */
	private ReviewCommitteeMeetingUsersDAO dao = new ReviewCommitteeMeetingUsersDAO();

	/**
	 * Get all ReviewCommitteeMeetingUsers
 	 * @author TechFinium 
 	 * @see   ReviewCommitteeMeetingUsers
 	 * @return a list of {@link haj.com.entity.ReviewCommitteeMeetingUsers}
	 * @throws Exception the exception
 	 */
	public List<ReviewCommitteeMeetingUsers> allReviewCommitteeMeetingUsers() throws Exception {
	  	return dao.allReviewCommitteeMeetingUsers();
	}


	/**
	 * Create or update ReviewCommitteeMeetingUsers.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     ReviewCommitteeMeetingUsers
	 */
	public void create(ReviewCommitteeMeetingUsers entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  ReviewCommitteeMeetingUsers.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ReviewCommitteeMeetingUsers
	 */
	public void update(ReviewCommitteeMeetingUsers entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  ReviewCommitteeMeetingUsers.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ReviewCommitteeMeetingUsers
	 */
	public void delete(ReviewCommitteeMeetingUsers entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ReviewCommitteeMeetingUsers}
	 * @throws Exception the exception
	 * @see    ReviewCommitteeMeetingUsers
	 */
	public ReviewCommitteeMeetingUsers findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find ReviewCommitteeMeetingUsers by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.ReviewCommitteeMeetingUsers}
	 * @throws Exception the exception
	 * @see    ReviewCommitteeMeetingUsers
	 */
	public List<ReviewCommitteeMeetingUsers> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load ReviewCommitteeMeetingUsers
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.ReviewCommitteeMeetingUsers}
	 * @throws Exception the exception
	 */
	public List<ReviewCommitteeMeetingUsers> allReviewCommitteeMeetingUsers(int first, int pageSize) throws Exception {
		return dao.allReviewCommitteeMeetingUsers(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of ReviewCommitteeMeetingUsers for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the ReviewCommitteeMeetingUsers
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(ReviewCommitteeMeetingUsers.class);
	}
	
    /**
     * Lazy load ReviewCommitteeMeetingUsers with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 ReviewCommitteeMeetingUsers class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.ReviewCommitteeMeetingUsers} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<ReviewCommitteeMeetingUsers> allReviewCommitteeMeetingUsers(Class<ReviewCommitteeMeetingUsers> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<ReviewCommitteeMeetingUsers>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of ReviewCommitteeMeetingUsers for lazy load with filters
     * @author TechFinium 
     * @param entity ReviewCommitteeMeetingUsers class
     * @param filters the filters
     * @return Number of rows in the ReviewCommitteeMeetingUsers entity
     * @throws Exception the exception     
     */	
	public int count(Class<ReviewCommitteeMeetingUsers> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<ReviewCommitteeMeetingUsers> findByReviewCommitteeMeeting(Long id) throws Exception {
	 	return dao.findByReviewCommitteeMeeting(id);
	}
	
	/*public ArrayList<Users> findUsersByReviewCommitteeMeeting(Long id)  throws Exception 
	{
		List<ReviewCommitteeMeetingUsers> meetingList=findByReviewCommitteeMeeting(id);
		ArrayList<Users> userList=new ArrayList<>();
		if(meetingList !=null && meetingList.size()>0)
		{
			for(ReviewCommitteeMeetingUsers rcmu:meetingList)
			{
				userList.add(rcmu.getUser());
			}
		}
		
		return userList;
	}*/
	
	@SuppressWarnings("unchecked")
	public ArrayList<Users> findUsersByReviewCommitteeMeeting(Long id) throws Exception {
	 	return (ArrayList<Users>) dao.findUsersByReviewCommitteeMeeting(id);
	}
}