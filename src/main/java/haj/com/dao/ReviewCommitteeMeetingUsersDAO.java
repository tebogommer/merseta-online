package haj.com.dao;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.ReviewCommitteeMeetingUsers;
import haj.com.entity.Users;

public class ReviewCommitteeMeetingUsersDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ReviewCommitteeMeetingUsers
 	 * @author TechFinium 
 	 * @see    ReviewCommitteeMeetingUsers
 	 * @return a list of {@link haj.com.entity.ReviewCommitteeMeetingUsers}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ReviewCommitteeMeetingUsers> allReviewCommitteeMeetingUsers() throws Exception {
		return (List<ReviewCommitteeMeetingUsers>)super.getList("select o from ReviewCommitteeMeetingUsers o");
	}

	/**
	 * Get all ReviewCommitteeMeetingUsers between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    ReviewCommitteeMeetingUsers
 	 * @return a list of {@link haj.com.entity.ReviewCommitteeMeetingUsers}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ReviewCommitteeMeetingUsers> allReviewCommitteeMeetingUsers(Long from, int noRows) throws Exception {
	 	String hql = "select o from ReviewCommitteeMeetingUsers o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<ReviewCommitteeMeetingUsers>)super.getList(hql, parameters,from.intValue(),noRows);
	}
	
	@SuppressWarnings("unchecked")
	public List<ReviewCommitteeMeetingUsers> findByReviewCommitteeMeeting(Long id) throws Exception {
	 	String hql = "select o from ReviewCommitteeMeetingUsers o where o.reviewCommitteeMeeting.id =:id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id",id);
		return (List<ReviewCommitteeMeetingUsers>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> findUsersByReviewCommitteeMeeting(Long id) throws Exception {
	 	String hql = "select distinct o.user from ReviewCommitteeMeetingUsers o where o.reviewCommitteeMeeting.id =:id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id",id);
		return (List<Users>)super.getList(hql, parameters);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    ReviewCommitteeMeetingUsers
 	 * @return a {@link haj.com.entity.ReviewCommitteeMeetingUsers}
 	 * @throws Exception global exception
 	 */
	public ReviewCommitteeMeetingUsers findByKey(Long id) throws Exception {
	 	String hql = "select o from ReviewCommitteeMeetingUsers o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (ReviewCommitteeMeetingUsers)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ReviewCommitteeMeetingUsers by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    ReviewCommitteeMeetingUsers
  	 * @return a list of {@link haj.com.entity.ReviewCommitteeMeetingUsers}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ReviewCommitteeMeetingUsers> findByName(String description) throws Exception {
	 	String hql = "select o from ReviewCommitteeMeetingUsers o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ReviewCommitteeMeetingUsers>)super.getList(hql, parameters);
	}
}

