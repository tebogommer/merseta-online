package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.ReviewCommitteeMeetingAgenda;

public class ReviewCommitteeMeetingAgendaDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ReviewCommitteeMeetingAgenda
 	 * @author TechFinium 
 	 * @see    ReviewCommitteeMeetingAgenda
 	 * @return a list of {@link haj.com.entity.ReviewCommitteeMeetingAgenda}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ReviewCommitteeMeetingAgenda> allReviewCommitteeMeetingAgenda() throws Exception {
		return (List<ReviewCommitteeMeetingAgenda>)super.getList("select o from ReviewCommitteeMeetingAgenda o where o.deleted = false");
	}

	/**
	 * Get all ReviewCommitteeMeetingAgenda between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    ReviewCommitteeMeetingAgenda
 	 * @return a list of {@link haj.com.entity.ReviewCommitteeMeetingAgenda}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ReviewCommitteeMeetingAgenda> allReviewCommitteeMeetingAgenda(Long from, int noRows) throws Exception {
	 	String hql = "select o from ReviewCommitteeMeetingAgenda o where o.deleted = false" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<ReviewCommitteeMeetingAgenda>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    ReviewCommitteeMeetingAgenda
 	 * @return a {@link haj.com.entity.ReviewCommitteeMeetingAgenda}
 	 * @throws Exception global exception
 	 */
	public ReviewCommitteeMeetingAgenda findByKey(Long id) throws Exception {
	 	String hql = "select o from ReviewCommitteeMeetingAgenda o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (ReviewCommitteeMeetingAgenda)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ReviewCommitteeMeetingAgenda by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    ReviewCommitteeMeetingAgenda
  	 * @return a list of {@link haj.com.entity.ReviewCommitteeMeetingAgenda}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ReviewCommitteeMeetingAgenda> findByName(String description) throws Exception {
	 	String hql = "select o from ReviewCommitteeMeetingAgenda o where o.description like  :description and o.deleted = false order by o.description " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ReviewCommitteeMeetingAgenda>)super.getList(hql, parameters);
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
	 	String hql = "select o from ReviewCommitteeMeetingAgenda o where o.reviewCommitteeMeeting.id = :id and o.deleted = false order by o.createDate " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (List<ReviewCommitteeMeetingAgenda>)super.getList(hql, parameters);
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
	 	String hql = "select o from ReviewCommitteeMeetingAgenda o where o.meetingAgenda.id = :id and o.deleted = false order by o.createDate desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (List<ReviewCommitteeMeetingAgenda>)super.getList(hql, parameters);
	}
	
	
	@SuppressWarnings("unchecked")
	public ReviewCommitteeMeetingAgenda findByMeetingAgendaAndReviewCommitteeMeeting(Long  meetingAgendaID,Long reviewCommitteeMeetingID) throws Exception {
	 	String hql = "select o from ReviewCommitteeMeetingAgenda o where o.meetingAgenda.id = :id and o.reviewCommitteeMeeting.id =:reviewCommitteeMeetingID and o.deleted = false " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", meetingAgendaID);
	    parameters.put("reviewCommitteeMeetingID", reviewCommitteeMeetingID);
		return (ReviewCommitteeMeetingAgenda)super.getUniqueResult(hql, parameters);
	}
}

