package haj.com.service;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import haj.com.constants.HAJConstants;
import haj.com.dao.ReviewCommitteeMeetingDAO;
import haj.com.entity.Company;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.RejectReasonsChild;
import haj.com.entity.ReviewCommitteeMeeting;
import haj.com.entity.ReviewCommitteeMeetingAgenda;
import haj.com.entity.ReviewCommitteeMeetingUsers;
import haj.com.entity.Users;
import haj.com.entity.enums.MeetingTypeEnum;
import haj.com.entity.lookup.MeetingAgenda;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.MeetingAgendaService;
import haj.com.utils.GenericUtility;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class ReviewCommitteeMeetingService extends AbstractService {
	/** The dao. */
	private ReviewCommitteeMeetingDAO dao = new ReviewCommitteeMeetingDAO();
	ReviewCommitteeMeetingUsersService committeeMeetingUsersService=new ReviewCommitteeMeetingUsersService();

	/**
	 * Get all ReviewCommitteeMeeting
 	 * @author TechFinium 
 	 * @see   ReviewCommitteeMeeting
 	 * @return a list of {@link haj.com.entity.ReviewCommitteeMeeting}
	 * @throws Exception the exception
 	 */
	public List<ReviewCommitteeMeeting> allReviewCommitteeMeeting() throws Exception {
	  	return dao.allReviewCommitteeMeeting();
	}


	/**
	 * Create or update ReviewCommitteeMeeting.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     ReviewCommitteeMeeting
	 */
	public void create(ReviewCommitteeMeeting entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}
	
	/**
	 * Delete ReviewCommitteeMeeting.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     ReviewCommitteeMeeting
	 */
	public void deleteReviewCommitteeMeeting(ReviewCommitteeMeeting entity) throws Exception  {
		   //Deleting all ReviewCommitteeMeetingAgenda for ReviewCommitteeMeeting
		   ReviewCommitteeMeetingAgendaService reviewCommitteeMeetingAgendaService=new ReviewCommitteeMeetingAgendaService();
		   List<ReviewCommitteeMeetingAgenda> listToDelete=reviewCommitteeMeetingAgendaService.findByReviewCommitteeMeeting(entity.getId());
		   for( ReviewCommitteeMeetingAgenda rcms: listToDelete)
		   {
			   reviewCommitteeMeetingAgendaService.delete(rcms);
		   }
		   this.dao.delete(entity);
	}
	
	/**
	 * Create or update ReviewCommitteeMeeting.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     ReviewCommitteeMeeting
	 */
	public String addReviewCommitteeMeeting(ReviewCommitteeMeeting entity, List<MeetingAgenda> selectMeetingAgendaList,ArrayList<Users> usersList,Users currentUser ) throws Exception  {
	   List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
	   String mssg="";
	   if (entity.getId() ==null)
	   {
			 ReviewCommitteeMeeting lastReviewCommitteeMeeting=findLastReviewCommitteeMeeting();
			 Long lastNum=1L;
			 if(lastReviewCommitteeMeeting !=null)
			 {
				 if(lastReviewCommitteeMeeting.getMeetingNumber() !=null)
				 {
					 lastNum=lastReviewCommitteeMeeting.getMeetingNumber().longValue()+1;
				 }
				 else
				 {
					 lastNum=1L;
				 }
				
			 }
			 entity.setMeetingNumber(lastNum);
			 this.dao.create(entity);
			 for( MeetingAgenda mst: selectMeetingAgendaList)
			 {
				 dataEntities.add(new ReviewCommitteeMeetingAgenda(entity, mst, mst.getMeetingNumber()));
			 }
			 //Adding users
			 for(Users user:usersList)
			 {
				 ReviewCommitteeMeetingUsers ReviewCommitteeMeetingUsers=new ReviewCommitteeMeetingUsers(user, entity);
				 dataEntities.add(ReviewCommitteeMeetingUsers);
			 }
			 dao.createBatch(dataEntities);
			 newMeetingEmailNotification(entity, usersList,selectMeetingAgendaList,currentUser);
	   }
	   else{
		  
		    mssg=preparReviewCommMeetingUpdate(entity, selectMeetingAgendaList);
		   //Creating new ReviewCommitteeMeetingAgenda and update ReviewCommitteeMeeting
		   for(MeetingAgenda mst: selectMeetingAgendaList){
				 dataEntities.add(new ReviewCommitteeMeetingAgenda(entity, mst, mst.getMeetingNumber()));
		   }
		  
		   //Deleting All ReviewCommitteeMeetingUsers
		   ArrayList<ReviewCommitteeMeetingUsers> reviewCommitteeMeetingUsersToDelete=(ArrayList<ReviewCommitteeMeetingUsers>) committeeMeetingUsersService.findByReviewCommitteeMeeting(entity.getId());
		   for(ReviewCommitteeMeetingUsers rcmu:reviewCommitteeMeetingUsersToDelete){
			   committeeMeetingUsersService.delete(rcmu);
		   }
		   //Creating new ReviewCommitteeMeetingUsers
		   for(Users user:usersList){
				 ReviewCommitteeMeetingUsers ReviewCommitteeMeetingUsers=new ReviewCommitteeMeetingUsers(user, entity);
				 dataEntities.add(ReviewCommitteeMeetingUsers);
		   }
		   if(dataEntities.size()>0)
		   {
			   dao.createBatch(dataEntities);
		   }
		   this.dao.update(entity);
		   meetingUpdateEmailNotification(entity, usersList,selectMeetingAgendaList,currentUser);
		}
	   return mssg;
	}
	
	public String preparReviewCommMeetingUpdate(ReviewCommitteeMeeting entity, List<MeetingAgenda> selectMeetingAgendaList) throws Exception
	{
		   String mssg="";
		   ReviewCommitteeMeetingAgendaService reviewCommitteeMeetingAgendaService=new ReviewCommitteeMeetingAgendaService();
		   List<ReviewCommitteeMeetingAgenda> allMeetingagendaList=reviewCommitteeMeetingAgendaService.findByReviewCommitteeMeeting(entity.getId());
		   List<ReviewCommitteeMeetingAgenda> toBeDeleted=new ArrayList<>();
		   List<MeetingAgenda> tempSelected=new ArrayList<>();
		   tempSelected.addAll(selectMeetingAgendaList);
		   for( ReviewCommitteeMeetingAgenda rcms: allMeetingagendaList){
			   if(!selectMeetingAgendaList.contains(rcms.getMeetingAgenda())){
				   toBeDeleted.add(rcms);
			   }
		   }
		   
		   for( MeetingAgenda rm: selectMeetingAgendaList){
			   for( ReviewCommitteeMeetingAgenda rcms: allMeetingagendaList){
				   if(rcms.getMeetingAgenda().getId().equals(rm.getId()))
				   {
					   tempSelected.remove(rm);
					   break;
				   }
			   }
		   }
		   
		   //Deleting all ReviewCommitteeMeetingAgenda
		   List<ReviewCommitteeMeetingAgenda> deleteList = new ArrayList<>();
		   for(ReviewCommitteeMeetingAgenda rcms: toBeDeleted){
			   boolean isUsed=checkIfMeetingAgendaIsInUse(rcms);
			   if(!isUsed){
				   rcms.setDeleted(true);
				   deleteList.add(rcms);
			   }
			   else{
				   mssg +="Meeting agenda "+rcms.getMeetingAgenda().getDescription()+" cannot be removed because it is in use.\n\n";
			   }
		   }
		   
		   List<IDataEntity> deleteBetch = new ArrayList<IDataEntity>();
		   deleteBetch.addAll(deleteList);
		   if(deleteBetch.size()>0) {
			   dao.updateBatch(deleteBetch); 
		   }
		   selectMeetingAgendaList.clear();
		   selectMeetingAgendaList.addAll(tempSelected);
		   return mssg;
		
	}
	
	public Boolean checkIfMeetingAgendaIsInUse(ReviewCommitteeMeetingAgenda rcms) throws Exception
	{
		boolean isUsed=false;
		TrainingProviderApplicationService tpService=new TrainingProviderApplicationService();
	    AssessorModeratorApplicationService amService=new AssessorModeratorApplicationService();
	    AssessorModExtensionOfScopeService amExOfScopeService=new AssessorModExtensionOfScopeService();
	    AssessorModReRegistrationService amReRegService=new AssessorModReRegistrationService();
	    CompanyLearnersTerminationService ltService=new CompanyLearnersTerminationService();
	    SDPExtensionOfScopeService sdpExOfScopeService=new SDPExtensionOfScopeService();
	    if(tpService.countByReviewCommitteeMeetingAgenda(rcms)>0 || amService.countByReviewCommitteeMeetingAgenda(rcms)>0 ||
	    amExOfScopeService.countByReviewCommitteeMeetingAgenda(rcms)>0 || amReRegService.countByReviewCommitteeMeetingAgenda(rcms)>0||
	    ltService.countByReviewCommitteeMeetingAgenda(rcms)>0 || sdpExOfScopeService.countByReviewCommitteeMeetingAgenda(rcms)>0)
	    {
	    	isUsed=true;
	    }
		return isUsed;
	}
	
	public void newMeetingEmailNotification(ReviewCommitteeMeeting entity,ArrayList<Users> usersList,List<MeetingAgenda> selectMeetingAgendaLis,Users currentUser) throws Exception {
		String from=HAJConstants.sdf3.format(entity.getFromDateTime());
		String to=HAJConstants.sdf3.format(entity.getToDateTime());
		String agendas=convertToHTML(selectMeetingAgendaLis);
		for(Users user:usersList)
		{
			String title="";
			if(user.getTitle() !=null){
				title=user.getTitle().getDescription();
			}
			String subject = "ETQA Review Committee Meeting: ".toUpperCase()+entity.getTitle().toUpperCase();
			String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+title+" "+user.getFirstName()+" "+user.getLastName()+",</p>"
					
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					+ "Please be informed that you are invited to attend an ETQA Review Committee meeting as follows:<br/><br/>"
					+ "<b>Date:</b> "
					+ "From: "+from+", To: "+to+"<br/>"
					+ "<b>Venue: </b>"+entity.getVenue()+"<br/>"
					+ "<b>Agenda: </b><br/>"
					+ agendas
					+ "</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					+ "Yours sincerely,"
					+ "</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					+  currentUser.getFirstName()+" "+currentUser.getLastName()
					+ "</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					+ "<b>"+getUserJobTitle(currentUser)+"</b>"
					+ "</p>";
			GenericUtility.sendMail(user.getEmail(), subject, mssg, null);
			//GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "DG_Application_Withdrawal.pdf", "pdf", null);
		}

	}
	
	public void meetingUpdateEmailNotification(ReviewCommitteeMeeting entity,ArrayList<Users> usersList,List<MeetingAgenda> selectMeetingAgendaLis,Users currentUser) throws Exception {
		String from=HAJConstants.sdf3.format(entity.getFromDateTime());
		String to=HAJConstants.sdf3.format(entity.getToDateTime());
		String agendas=convertToHTML(selectMeetingAgendaLis);
		for(Users user:usersList)
		{
			String title="";
			if(user.getTitle() !=null){
				title=user.getTitle().getDescription();
			}
			String subject = "ETQA Review Committee Meeting Update ".toUpperCase();
			String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+title+" "+user.getFirstName()+"  "+user.getLastName()+",</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					+ "Please be informed that the ETQA Review Committee Meeting ("+entity.getTitle()+") date has been updated. The new meeting details are as follows: <br/><br/>"
					+ "<b>Date:</b> "
					+ "From: "+from+", To: "+to+"<br/>"
					+ "<b>Venue: </b>"+entity.getVenue()+"<br/>"
					+ "<b>Agenda: </b>"
					+ agendas
					+ "</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					+ "Yours sincerely,"
					+ "</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					+  currentUser.getFirstName()+" "+currentUser.getLastName()
					+ "</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					+ "<b>"+getUserJobTitle(currentUser)+"</b>"
					+ "</p>";
			GenericUtility.sendMail(user.getEmail(), subject, mssg, null);
			//GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "DG_Application_Withdrawal.pdf", "pdf", null);
		}

	}
	
	public void meetingDateUpdateEmailNotification(ReviewCommitteeMeeting entity,Users currentUser) throws Exception {
		MeetingAgendaService meetingAgendaService= new MeetingAgendaService();
		ReviewCommitteeMeetingUsersService reviewCommitteeMeetingUsersService=new ReviewCommitteeMeetingUsersService();
		String from=HAJConstants.sdf3.format(entity.getFromDateTime());
		String to=HAJConstants.sdf3.format(entity.getToDateTime());
		List<MeetingAgenda> selectMeetingAgendaLis=meetingAgendaService.allMeetingAgenda(entity);;
		String agendas=convertToHTML(selectMeetingAgendaLis);
		ArrayList<Users> usersList=reviewCommitteeMeetingUsersService.findUsersByReviewCommitteeMeeting(entity.getId());
		for(Users user:usersList)
		{
			String title="";
			if(user.getTitle() !=null){
				title=user.getTitle().getDescription();
			}
			String subject = "ETQA Review Committee Meeting Update ".toUpperCase();
			String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+title+" "+user.getLastName()+" "+user.getFirstName()+",</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					+ "Please be informed that ETQA Review Committee Meeting ("+entity.getTitle()+") date as been updated. The new meeting details are as follows: <br/><br/>"
					+ "<b>Date:</b> "
					+ "From: "+from+", To: "+to+"<br/>"
					+ "<b>Venue: </b>"+entity.getVenue()+"<br/>"
					+ "<b>Agenda: </b>"
					+ agendas
					+ "</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					+ "Yours sincerely,"
					+ "</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					+  currentUser.getFirstName()+" "+currentUser.getLastName()
					+ "</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					+ "<b>"+getUserJobTitle(currentUser)+"</b>"
					+ "</p>";
			GenericUtility.sendMail(user.getEmail(), subject, mssg, null);
			//GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "DG_Application_Withdrawal.pdf", "pdf", null);
		}

	}
	
	
	
	public String getUserJobTitle(Users user) throws Exception
	{
		String jobTitle="";
		HostingCompanyEmployeesService hces=new HostingCompanyEmployeesService();
		HostingCompanyEmployees hce=hces.findByUserReturnHostCompany(user.getId());
		if(hce !=null && hce.getId()!=null){
			if(hce !=null){
				jobTitle=hce.getJobTitle().getDescription();
			}
		}
		return jobTitle;
	}
	
	public String convertToHTML(List<MeetingAgenda> selectMeetingAgendaLis) {
		String sb ="<ul style=\"font-size:11.0pt;\";font-family:\"Arial\">";
		for (MeetingAgenda ma : selectMeetingAgendaLis) {
			sb += "<li>" +ma.getDescription() +" ("+ma.getMeetingNumber()+")"+ "</li>";
		}
		sb += "</ul>";
		return sb;
	}


	/**
	 * Update  ReviewCommitteeMeeting.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ReviewCommitteeMeeting
	 */
	public void update(ReviewCommitteeMeeting entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  ReviewCommitteeMeeting.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ReviewCommitteeMeeting
	 */
	public void delete(ReviewCommitteeMeeting entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ReviewCommitteeMeeting}
	 * @throws Exception the exception
	 * @see    ReviewCommitteeMeeting
	 */
	public ReviewCommitteeMeeting findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find ReviewCommitteeMeeting by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.ReviewCommitteeMeeting}
	 * @throws Exception the exception
	 * @see    ReviewCommitteeMeeting
	 */
	public List<ReviewCommitteeMeeting> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load ReviewCommitteeMeeting
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.ReviewCommitteeMeeting}
	 * @throws Exception the exception
	 */
	public List<ReviewCommitteeMeeting> allReviewCommitteeMeeting(int first, int pageSize) throws Exception {
		return dao.allReviewCommitteeMeeting(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of ReviewCommitteeMeeting for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the ReviewCommitteeMeeting
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(ReviewCommitteeMeeting.class);
	}
	
    /**
     * Lazy load ReviewCommitteeMeeting with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 ReviewCommitteeMeeting class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.ReviewCommitteeMeeting} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<ReviewCommitteeMeeting> allReviewCommitteeMeeting(Class<ReviewCommitteeMeeting> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<ReviewCommitteeMeeting>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of ReviewCommitteeMeeting for lazy load with filters
     * @author TechFinium 
     * @param entity ReviewCommitteeMeeting class
     * @param filters the filters
     * @return Number of rows in the ReviewCommitteeMeeting entity
     * @throws Exception the exception     
     */	
	public int count(Class<ReviewCommitteeMeeting> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
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
		
		return dao.findByStartAndEndDate(fromDateTime, toDateTime);
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
		return dao.findByMeetingType(meetingType);
	}
	
	@SuppressWarnings("unchecked")
	public List<ReviewCommitteeMeeting> allActiveReviewCommitteeMeeting() throws Exception {
		return dao.allActiveReviewCommitteeMeeting();
	}
	
	@SuppressWarnings("unchecked")
	public List<ReviewCommitteeMeeting> allActiveReviewCommitteeMeeting(Date siteVisiteDate) throws Exception {
		return dao.allActiveReviewCommitteeMeeting(siteVisiteDate);
	}
	
	/**
	 * Find ReviewCommitteeMeeting by meetingType
 	 * @author TechFinium 
 	 * @param meetingType the meetingType 
 	 * @see    ReviewCommitteeMeeting
  	 * @return ReviewCommitteeMeeting
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public ReviewCommitteeMeeting findByLastMeetingType(MeetingTypeEnum meetingType) throws Exception {
		
		ReviewCommitteeMeeting reviewCommitteeMeeting=null;
		List<ReviewCommitteeMeeting> list= dao.findByMeetingType(meetingType);
		if(list.size()>0)
		{
			reviewCommitteeMeeting=list.get(0);
		}
		return reviewCommitteeMeeting;
	}
	
	public ReviewCommitteeMeeting findLastReviewCommitteeMeeting() throws Exception {
		return dao.findLastReviewCommitteeMeeting();
	}
	
	/**
	 * Generates decision number base
	 * */
	public String getLastDesionNumber(MeetingAgenda meetingType,ReviewCommitteeMeeting reviewCommitteeMeeting) throws Exception
	{
		String decisionNumSyntax="";
		DateFormat df = new SimpleDateFormat("yy"); 
		if(reviewCommitteeMeeting==null || reviewCommitteeMeeting.getMeetingNumber()==null)
		{
			
			String year = df.format(Calendar.getInstance().getTime());
			
			ReviewCommitteeMeeting lastReviewCommitteeMeeting=findLastReviewCommitteeMeeting();
			String lastnum="01";
			
			if(lastReviewCommitteeMeeting !=null){
				if(lastReviewCommitteeMeeting.getMeetingNumber() !=null)
				{
					lastnum=String.valueOf(lastReviewCommitteeMeeting.getMeetingNumber().longValue()+1);
				}
				else
				{
					lastnum="01";
				}
				
			}
			if(lastnum.length()<2){lastnum="0"+lastnum;}
			//ETQA/YY/XXXX/99
			decisionNumSyntax=meetingType.getDecisionNumberSyntax();
			decisionNumSyntax=decisionNumSyntax.replace("YY", year);
			decisionNumSyntax=decisionNumSyntax.replace("XXXX", lastnum);
		}
		else
		{
			String lastnum=String.valueOf(reviewCommitteeMeeting.getMeetingNumber());
			if(lastnum.length()<2){lastnum="0"+lastnum;}
			String year = df.format(reviewCommitteeMeeting.getCreateDate());
			decisionNumSyntax=meetingType.getDecisionNumberSyntax();
			decisionNumSyntax=decisionNumSyntax.replace("YY", year);
			decisionNumSyntax=decisionNumSyntax.replace("XXXX", lastnum);
		}
		return decisionNumSyntax;
	}
}
