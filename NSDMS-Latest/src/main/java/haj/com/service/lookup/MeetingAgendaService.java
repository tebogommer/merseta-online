package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.MeetingAgendaDAO;
import haj.com.entity.ReviewCommitteeMeeting;
import haj.com.entity.lookup.MeetingAgenda;
import haj.com.framework.AbstractService;
import haj.com.service.ReviewCommitteeMeetingService;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class MeetingAgendaService extends AbstractService {
	/** The dao. */
	private MeetingAgendaDAO dao = new MeetingAgendaDAO();
	private ReviewCommitteeMeetingService reviewCommitteeMeetingService = new ReviewCommitteeMeetingService();

	/**
	 * Get all MeetingAgenda
 	 * @author TechFinium 
 	 * @see   MeetingAgenda
 	 * @return a list of {@link haj.com.entity.MeetingAgenda}
	 * @throws Exception the exception
 	 */
	public List<MeetingAgenda> allMeetingAgenda(ReviewCommitteeMeeting reviewCommitteeMeeting) throws Exception {
	  	return populateAdditionalInformationList(dao.allMeetingAgenda(),reviewCommitteeMeeting);
		//return dao.allMeetingAgenda();
	}


	/**
	 * Create or update MeetingAgenda.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     MeetingAgenda
	 */
	public void create(MeetingAgenda entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  MeetingAgenda.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    MeetingAgenda
	 */
	public void update(MeetingAgenda entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  MeetingAgenda.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    MeetingAgenda
	 */
	public void delete(MeetingAgenda entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.MeetingAgenda}
	 * @throws Exception the exception
	 * @see    MeetingAgenda
	 */
	public MeetingAgenda findByKey(long id) throws Exception {
       return populateAdditionalInformation(dao.findByKey(id),null);
	}

	/**
	 * Find MeetingAgenda by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.MeetingAgenda}
	 * @throws Exception the exception
	 * @see    MeetingAgenda
	 */
	public List<MeetingAgenda> findByName(String desc) throws Exception {
		return populateAdditionalInformationList(dao.findByName(desc),null);
	}
	
	/**
	 * Lazy load MeetingAgenda
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.MeetingAgenda}
	 * @throws Exception the exception
	 */
	public List<MeetingAgenda> allMeetingAgenda(int first, int pageSize) throws Exception {
		return populateAdditionalInformationList(dao.allMeetingAgenda(Long.valueOf(first), pageSize),null);
	}
		
	
	/**
	 * Get count of MeetingAgenda for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the MeetingAgenda
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(MeetingAgenda.class);
	}
	
    /**
     * Lazy load MeetingAgenda with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 MeetingAgenda class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.MeetingAgenda} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<MeetingAgenda> allMeetingAgenda(Class<MeetingAgenda> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		//return populateAdditionalInformationList(( List<MeetingAgenda>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters));
		return (List<MeetingAgenda>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of MeetingAgenda for lazy load with filters
     * @author TechFinium 
     * @param entity MeetingAgenda class
     * @param filters the filters
     * @return Number of rows in the MeetingAgenda entity
     * @throws Exception the exception     
     */	
	public int count(Class<MeetingAgenda> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
    public List<MeetingAgenda> populateAdditionalInformationList(List<MeetingAgenda> meetingScheduleList,ReviewCommitteeMeeting reviewCommitteeMeeting)throws Exception{
    	for (MeetingAgenda meetingAgenda : meetingScheduleList) {
    		populateAdditionalInformation(meetingAgenda,reviewCommitteeMeeting);
		}
    	
    	return meetingScheduleList;
    }
    
    public MeetingAgenda populateAdditionalInformation(MeetingAgenda meetingSchedule,ReviewCommitteeMeeting reviewCommitteeMeeting) throws Exception {
    	String meetingNum = reviewCommitteeMeetingService.getLastDesionNumber(meetingSchedule, reviewCommitteeMeeting);
    	meetingSchedule.setMeetingNumber(meetingNum);
    	return meetingSchedule;
    }
}
