package haj.com.dao;


import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.ReviewCommitteeMeeting;
import haj.com.entity.enums.MeetingTypeEnum;

public class ReviewCommitteeMeetingDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ReviewCommitteeMeeting
 	 * @author TechFinium 
 	 * @see    ReviewCommitteeMeeting
 	 * @return a list of {@link haj.com.entity.ReviewCommitteeMeeting}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ReviewCommitteeMeeting> allReviewCommitteeMeeting() throws Exception {
		return (List<ReviewCommitteeMeeting>)super.getList("select o from ReviewCommitteeMeeting o");
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ReviewCommitteeMeeting> allActiveReviewCommitteeMeeting() throws Exception {
		Date currentDate=new Date();
	 	String hql = "select o from ReviewCommitteeMeeting o where date(o.fromDateTime) >= date(:currentDate) order by o.fromDateTime " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("currentDate", currentDate);
		return (List<ReviewCommitteeMeeting>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<ReviewCommitteeMeeting> allActiveReviewCommitteeMeeting(Date siteVisiteDate) throws Exception {
		/*Date currentDate=new Date();
	 	String hql = "select o from ReviewCommitteeMeeting o where date(o.fromDateTime) >= date(:currentDate) and date(o.fromDateTime) >= date(:siteVisiteDate) order by o.fromDateTime " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("currentDate", currentDate);
	    parameters.put("siteVisiteDate", siteVisiteDate);
		return (List<ReviewCommitteeMeeting>)super.getList(hql, parameters);*/
		
		Date currentDate=new Date();
	 	String hql = "select o from ReviewCommitteeMeeting o where date(o.fromDateTime) >= date(:currentDate) order by o.fromDateTime " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("currentDate", currentDate);
		return (List<ReviewCommitteeMeeting>)super.getList(hql, parameters);
	}


	/**
	 * Get all ReviewCommitteeMeeting between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    ReviewCommitteeMeeting
 	 * @return a list of {@link haj.com.entity.ReviewCommitteeMeeting}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ReviewCommitteeMeeting> allReviewCommitteeMeeting(Long from, int noRows) throws Exception {
	 	String hql = "select o from ReviewCommitteeMeeting o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<ReviewCommitteeMeeting>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    ReviewCommitteeMeeting
 	 * @return a {@link haj.com.entity.ReviewCommitteeMeeting}
 	 * @throws Exception global exception
 	 */
	public ReviewCommitteeMeeting findByKey(Long id) throws Exception {
	 	String hql = "select o from ReviewCommitteeMeeting o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (ReviewCommitteeMeeting)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ReviewCommitteeMeeting by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    ReviewCommitteeMeeting
  	 * @return a list of {@link haj.com.entity.ReviewCommitteeMeeting}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ReviewCommitteeMeeting> findByName(String description) throws Exception {
	 	String hql = "select o from ReviewCommitteeMeeting o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ReviewCommitteeMeeting>)super.getList(hql, parameters);
	}
	
	/**
	 * Find ReviewCommitteeMeeting by fromDateTime and toDateTime
 	 * @author TechFinium 
 	 * @param description the fromDateTime 
 	 * @param description the toDateTime 
 	 * @see    ReviewCommitteeMeeting
  	 * @return a list of {@link haj.com.entity.ReviewCommitteeMeeting}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ReviewCommitteeMeeting> findByStartAndEndDate(Date fromDateTime,Date toDateTime) throws Exception {
	 	String hql = "select o from ReviewCommitteeMeeting o where o.fromDateTime >= :fromDateTime and o.toDateTime <= :toDateTime " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("fromDateTime", fromDateTime);
	    parameters.put("toDateTime", toDateTime);
		return (List<ReviewCommitteeMeeting>)super.getList(hql, parameters);
	}
	
	/**
	 * Find ReviewCommitteeMeeting by meetingType
 	 * @author TechFinium 
 	 * @param meetingType the meetingType 
 	 * @see    ReviewCommitteeMeeting
  	 * @return a list of {@link haj.com.entity.ReviewCommitteeMeeting}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ReviewCommitteeMeeting> findByMeetingType(MeetingTypeEnum meetingType) throws Exception {
	 	String hql = "select o from ReviewCommitteeMeeting o where o.meetingType = :meetingType order by o.createDate " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("meetingType", meetingType);
		return (List<ReviewCommitteeMeeting>)super.getList(hql, parameters);
	}
	
	public ReviewCommitteeMeeting findLastReviewCommitteeMeeting() throws Exception {
	 	String hql = "select o from ReviewCommitteeMeeting o order by o.createDate desc" ;
		return (ReviewCommitteeMeeting)super.oneRow(hql);
	}
	
	
}

