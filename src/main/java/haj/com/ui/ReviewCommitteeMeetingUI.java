package haj.com.ui;


import javax.annotation.PostConstruct;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import haj.com.framework.AbstractUI;
import haj.com.service.ReviewCommitteeMeetingService;
import haj.com.service.ReviewCommitteeMeetingUsersService;
import haj.com.service.ReviewCommitteeMeetingAgendaService;
import haj.com.service.lookup.MeetingAgendaService;
import haj.com.utils.GenericUtility;

import javax.faces.model.SelectItem;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.SortOrder;
import java.util.Map;

import haj.com.entity.ReviewCommitteeMeeting;
import haj.com.entity.ReviewCommitteeMeetingAgenda;
import haj.com.entity.Users;
import haj.com.entity.lookup.MeetingAgenda;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "reviewCommitteeMeetingUI")
@ViewScoped
public class ReviewCommitteeMeetingUI extends AbstractUI {

    private ScheduleModel lazyEventModel;
    private ScheduleEvent event = new DefaultScheduleEvent();
    
	private ReviewCommitteeMeetingService service = new ReviewCommitteeMeetingService();
	private List<ReviewCommitteeMeeting> reviewCommitteeMeetingList = null;
	private List<ReviewCommitteeMeeting> reviewCommitteeMeetingfilteredList = null;
	private ReviewCommitteeMeeting reviewCommitteeMeeting = null;
	private LazyDataModel<ReviewCommitteeMeeting> dataModel; 
	private List<MeetingAgenda> meetingAgendaList=new ArrayList<>();
	private List<MeetingAgenda> selectedMeetingAgendaList=new ArrayList<>();
	private MeetingAgendaService meetingAgendaService= new MeetingAgendaService();
    private ReviewCommitteeMeetingAgendaService reviewCommitteeMeetingAgendaService=new ReviewCommitteeMeetingAgendaService();
    private ArrayList<Users> meetingUsersList=new ArrayList<>();
    private Users meetingUser;
    private ReviewCommitteeMeetingUsersService reviewCommitteeMeetingUsersService=new ReviewCommitteeMeetingUsersService();
    
    private String meetingMessage="";
	
	/** The Decision Number syntax 
	 * for Skills Development Provider. */
	public String SDP_DECISION_NUM = "ETQA/17/9999/7";
	
	/** The Decision Number syntax for 
	 * Assessor/Moderator Registration Approval. */
	public String SDP_WITHDRAWAL_DECISION_NUM = "ETQA/17/9999/8";
	
	/** The Decision Number syntax 
	 * for Skills Development Provider. */
	public String AM_DECISION_NUM = "ETQA/17/9999/9";
	
	/** The Decision Number syntax for 
	 * Contract Rescission/Learnership. */
	public String CRL_DECISION_NUM = "ETQA/17/9999/10";
	
	/** Decision Number syntax for 
	 * Skills Programme/Set Registration. */
	public String SP_SR_DECISION_NUM = "ETQA/17/9999/11";
	
	/**The users*/
	private Users user;

   
	
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all ReviewCommitteeMeeting and prepare a for a create of a new ReviewCommitteeMeeting
 	 * @author TechFinium 
 	 * @see    ReviewCommitteeMeeting
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		reviewCommitteeMeetingInfo();
		initializeScheduleInfo();
		//meetingAgendaList=resolveMeetingNumber(meetingAgendaService.allMeetingAgenda());
		meetingAgendaList = meetingAgendaService.allMeetingAgenda(null);
		reviewCommitteeMeetingList=service.allActiveReviewCommitteeMeeting();
		//reviewCommitteeMeetingList=service.allReviewCommitteeMeeting();
	}
	
	/**
	 * return boolean flag to Displays or hide 
	 * Action buttons(Approve or Reject)
	 * base on Meeting date
	 * 
	 * */
	
	public boolean showActionButton(ReviewCommitteeMeeting reviewCommitteeMeeting)
	{
		boolean show=false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			String strDate1=sdf.format(reviewCommitteeMeeting.getFromDateTime());
			String strDate2=sdf.format(getNow());
			
			Date meetingDate=sdf.parse(strDate1);
			Date currentDate=sdf.parse(strDate2);
			
			
			if (meetingDate.after(currentDate)) {
				show=false;
				//dd MMMM yyyy HH:mm
				meetingMessage="The meeting for this application is due to be held on "+ new SimpleDateFormat("dd MMMM yyyy").format(reviewCommitteeMeeting.getFromDateTime());
			}

			if (meetingDate.before(currentDate)) {
				show=true;
				meetingMessage="The meeting for this application was due to be held on "+ new SimpleDateFormat("dd MMMM yyyy").format(reviewCommitteeMeeting.getFromDateTime());
			    
			}

			if (meetingDate.equals(currentDate)) {
				show=true;
				meetingMessage="";
			}
		} catch (ParseException e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
		
		
		return show;
		
	}
	
	public void addUser()
	{
		try {
			if(meetingUser !=null)
			{
				if(!meetingUsersList.contains(meetingUser))
				{
					meetingUsersList.add(meetingUser);
					meetingUser=null;
				}
				else
				{
					meetingUser=null;
					throw new Exception("User already added to the meeting");
				}
			}
			else
			{
				throw new Exception("Please selct user to add");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	

	/**
	 * Get all ReviewCommitteeMeeting for data table
 	 * @author TechFinium 
 	 * @see    ReviewCommitteeMeeting
 	 */
	public void reviewCommitteeMeetingInfo() {
			//dataModel = new ReviewCommitteeMeetingDatamodel();
	}

	/**
	 * Insert ReviewCommitteeMeeting into database
 	 * @author TechFinium 
 	 * @see    ReviewCommitteeMeeting
 	 */
	public void reviewCommitteeMeetingInsert() {
		try {
				 service.create(this.reviewCommitteeMeeting);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 reviewCommitteeMeetingInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update ReviewCommitteeMeeting in database
 	 * @author TechFinium 
 	 * @see    ReviewCommitteeMeeting
 	 */
    public void reviewCommitteeMeetingUpdate() {
		try {
				 service.update(this.reviewCommitteeMeeting);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 reviewCommitteeMeetingInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete ReviewCommitteeMeeting from database
 	 * @author TechFinium 
 	 * @see    ReviewCommitteeMeeting
 	 */
	public void reviewCommitteeMeetingDelete() {
		try {
			 service.deleteReviewCommitteeMeeting(this.reviewCommitteeMeeting);
			  prepareNew();
			 reviewCommitteeMeetingInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	

	
	/**
	 * Create new instance of ReviewCommitteeMeeting 
 	 * @author TechFinium 
 	 * @see    ReviewCommitteeMeeting
 	 */
	public void prepareNew() {
		try {
			reviewCommitteeMeeting = new ReviewCommitteeMeeting();
			selectedMeetingAgendaList.clear();
			meetingAgendaList = meetingAgendaService.allMeetingAgenda(null);
			meetingUsersList=new ArrayList<>();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepareUpdate() {
		try {
			reviewCommitteeMeeting = new ReviewCommitteeMeeting();
			selectedMeetingAgendaList.clear();
			meetingUsersList=new ArrayList<>();
			meetingAgendaList.clear();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	
	  
    public ArrayList<ReviewCommitteeMeeting> getTPReviewCommitteeMeetingList(Date siteVisitDate) {
    	ArrayList<ReviewCommitteeMeeting> list=new ArrayList<>();
    	if(siteVisitDate !=null)
    	{
			try {
				list = (ArrayList<ReviewCommitteeMeeting>) service.allActiveReviewCommitteeMeeting(siteVisitDate);
			} catch (Exception e) {
				addErrorMessage(e.getMessage(), e);
				e.printStackTrace();
			}
    	}
    	return list;
	}

/*
    public List<SelectItem> getReviewCommitteeMeetingDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	reviewCommitteeMeetingInfo();
    	for (ReviewCommitteeMeeting ug : reviewCommitteeMeetingList) {
    		// l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc()));
		}
    	return l;
    }
  */
  
    /**
     * Complete.
     *
     * @param desc the desc
     * @return the list
     */  
    public List<ReviewCommitteeMeeting> complete(String desc) {
		List<ReviewCommitteeMeeting> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		}
		catch (Exception e) {
		addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
    
    public List<ReviewCommitteeMeeting> getReviewCommitteeMeetingList() {
		return reviewCommitteeMeetingList;
	}
	public void setReviewCommitteeMeetingList(List<ReviewCommitteeMeeting> reviewCommitteeMeetingList) {
		this.reviewCommitteeMeetingList = reviewCommitteeMeetingList;
	}
	public ReviewCommitteeMeeting getReviewCommitteeMeeting() {
		return reviewCommitteeMeeting;
	}
	public void setReviewCommitteeMeeting(ReviewCommitteeMeeting reviewCommitteeMeeting) {
		this.reviewCommitteeMeeting = reviewCommitteeMeeting;
	}

    public List<ReviewCommitteeMeeting> getReviewCommitteeMeetingfilteredList() {
		return reviewCommitteeMeetingfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param reviewCommitteeMeetingfilteredList the new reviewCommitteeMeetingfilteredList list
 	 * @see    ReviewCommitteeMeeting
 	 */	
	public void setReviewCommitteeMeetingfilteredList(List<ReviewCommitteeMeeting> reviewCommitteeMeetingfilteredList) {
		this.reviewCommitteeMeetingfilteredList = reviewCommitteeMeetingfilteredList;
	}

	
	public LazyDataModel<ReviewCommitteeMeeting> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<ReviewCommitteeMeeting> dataModel) {
		this.dataModel = dataModel;
	}
	
	
	
	@SuppressWarnings("serial")
	public void initializeScheduleInfo()
	{
        lazyEventModel = new LazyScheduleModel() {
            @Override
            public void loadEvents(Date start, Date end) {
            	
            	
            	try {
            		
            		if(start !=null && end !=null)
            		{
						List<ReviewCommitteeMeeting> scheduleList=service.findByStartAndEndDate(start, end);
						for(ReviewCommitteeMeeting meeting:scheduleList)
						{
							 ScheduleEvent theEvent = new DefaultScheduleEvent(meeting.getTitle(), meeting.getFromDateTime(), meeting.getToDateTime(), meeting);
							 theEvent.setId(String.valueOf(meeting.getId()));
							 addEvent(theEvent);
						}
            		}
				} catch (Exception e) {
					e.printStackTrace();
					addErrorMessage(e.getMessage(), e);
				}
                
            }   
        };
	}
	
	
   
    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }
 
     
    public ScheduleEvent getEvent() {
        return event;
    }
 
    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }
     
    public void addEvent(ActionEvent actionEvent) {
        try {
        	validateStartAndEnddate();
        	if(meetingUsersList.size()<=0)throw new Exception("Please add at least one user");
        	if(selectedMeetingAgendaList.size()<=0)throw new Exception("Please select at least one meeting agenda");
        	reviewCommitteeMeeting.setUser(getSessionUI().getActiveUser());
			reviewCommitteeMeeting.setFromDateTime(event.getStartDate());
			reviewCommitteeMeeting.setToDateTime(event.getEndDate());
			reviewCommitteeMeeting.setTitle(event.getTitle());
			String mssg=service.addReviewCommitteeMeeting(reviewCommitteeMeeting,selectedMeetingAgendaList,meetingUsersList,getSessionUI().getActiveUser());
			event = new DefaultScheduleEvent();
			if(mssg.isEmpty() || mssg==null || mssg.length()<=0){
				addInfoMessage(super.getEntryLanguage("update.successful"));
				super.runClientSideExecute("PF('eventDialog').hide()");
				onCloseDiolog();
				prepareNew();
			}
			else{
				addWarningMessage(mssg);
				super.runClientSideUpdate("eventDialog");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
    }
    
    public void onCloseDiolog()
    {
    	try {
    		
    		initializeScheduleInfo();
			super.runClientSideUpdate("pgMainShedule");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
    }
    
	public void validateStartAndEnddate() throws Exception
	{
		if(event.getStartDate()!=null && event.getEndDate() !=null)
		{
			 if(event.getStartDate().after(event.getEndDate())){
				 throw new Exception("Invalid date/time. The end date/time must be after the start date/time.");
		      }
			 if(event.getStartDate().equals(event.getEndDate())){
				 throw new Exception("Invalid date/time. The end date/time must be after the start date/time.");
		      }
		}
	}
     
    public void onEventSelect(SelectEvent selectEvent) {
    	try {
    		prepareUpdate();
			event = (ScheduleEvent) selectEvent.getObject();
			reviewCommitteeMeeting =(ReviewCommitteeMeeting) event.getData();
			List<ReviewCommitteeMeetingAgenda> rcmList=reviewCommitteeMeetingAgendaService.findByReviewCommitteeMeeting(reviewCommitteeMeeting.getId());
			for(ReviewCommitteeMeetingAgenda rcmst:rcmList)
			{
				MeetingAgenda mt=rcmst.getMeetingAgenda();
				mt.setMeetingNumber(rcmst.getDecisionNumber());
				selectedMeetingAgendaList.add(mt);
			}
			
			ArrayList<MeetingAgenda> newMeetingAgendaList= new ArrayList<>();
			meetingAgendaList = meetingAgendaService.allMeetingAgenda(reviewCommitteeMeeting);
			for (MeetingAgenda mt : meetingAgendaList) {
				boolean adMt=true;;
				for(MeetingAgenda mt2:selectedMeetingAgendaList)
				{
					if(mt.getId().longValue() == mt2.getId().longValue())
					{
						adMt=false;
						newMeetingAgendaList.add(mt2);
						break;
					}
					
				}
				if(adMt)
				{
					newMeetingAgendaList.add(mt);
				}
			}
			
			meetingAgendaList.clear();
			meetingAgendaList=newMeetingAgendaList;
			//Get Meeting users
			meetingUsersList.clear();
			meetingUsersList=reviewCommitteeMeetingUsersService.findUsersByReviewCommitteeMeeting(reviewCommitteeMeeting.getId());
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
    }
     
    public void onDateSelect(SelectEvent selectEvent) {
    	try {
			prepareNew();
			event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
    }
    
    public ArrayList<MeetingAgenda> resolveMeetingNumber(List<MeetingAgenda> list,ReviewCommitteeMeeting reviewCommitteeMeeting) throws Exception
    {
    	ArrayList<MeetingAgenda> newList=new ArrayList<>();
    	for(MeetingAgenda m:list)
    	{
    		populateAdditionalInformation(m,reviewCommitteeMeeting);
    		newList.add(m);
    	}
    	
    	return newList;
    }
    
    public List<MeetingAgenda> populateAdditionalInformationList(List<MeetingAgenda> meetingScheduleList,ReviewCommitteeMeeting reviewCommitteeMeeting)throws Exception{
    	for (MeetingAgenda meetingAgenda : meetingScheduleList) {
    		populateAdditionalInformation(meetingAgenda,reviewCommitteeMeeting);
		}
    	
    	return meetingScheduleList;
    }
    
    public MeetingAgenda populateAdditionalInformation(MeetingAgenda meetingSchedule,ReviewCommitteeMeeting reviewCommitteeMeeting) throws Exception {
    	String meetingNum=service.getLastDesionNumber(meetingSchedule,reviewCommitteeMeeting);
    	meetingSchedule.setMeetingNumber(meetingNum);
    	return meetingSchedule;
    }
     
    public void onEventMove(ScheduleEntryMoveEvent event) {
    	
        try {
        	prepareNew();
			reviewCommitteeMeeting=(ReviewCommitteeMeeting) event.getScheduleEvent().getData();
			Date newDate=reviewCommitteeMeeting.getFromDateTime();
			if( event.getDayDelta()>0){
				newDate=GenericUtility.addDaysToDate(reviewCommitteeMeeting.getFromDateTime(), event.getDayDelta());
			}
			else{
				newDate=GenericUtility.deductDaysFromDate(reviewCommitteeMeeting.getFromDateTime(), event.getDayDelta());
			}
			
			if(event.getMinuteDelta()>0){
				 newDate=GenericUtility.addMiniutesToDate(reviewCommitteeMeeting.getFromDateTime(), event.getMinuteDelta());
			}
			else{
				newDate=GenericUtility.deductMinutesFromDate(reviewCommitteeMeeting.getFromDateTime(), event.getMinuteDelta());
			}
      
			reviewCommitteeMeeting.setFromDateTime(newDate);
			service.update(reviewCommitteeMeeting);
			service.meetingDateUpdateEmailNotification(reviewCommitteeMeeting,getSessionUI().getActiveUser());
			initializeScheduleInfo();
			prepareNew();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
    }
     
    public void onEventResize(ScheduleEntryResizeEvent event) {
    	
        try {
        	    prepareNew();
     			reviewCommitteeMeeting=(ReviewCommitteeMeeting) event.getScheduleEvent().getData();
     			Date newDate=reviewCommitteeMeeting.getFromDateTime();
     			if( event.getDayDelta()>0){
     				newDate=GenericUtility.addDaysToDate(reviewCommitteeMeeting.getFromDateTime(), event.getDayDelta());
     			}
     			else{
     				newDate=GenericUtility.deductDaysFromDate(reviewCommitteeMeeting.getFromDateTime(), event.getDayDelta());
     			}
     			
     			if(event.getMinuteDelta()>0){
     				 newDate=GenericUtility.addMiniutesToDate(reviewCommitteeMeeting.getFromDateTime(), event.getMinuteDelta());
     			}
     			else{
     				newDate=GenericUtility.deductMinutesFromDate(reviewCommitteeMeeting.getFromDateTime(), event.getMinuteDelta());
     			}
           
     			reviewCommitteeMeeting.setFromDateTime(newDate);
     			service.update(reviewCommitteeMeeting);
     			initializeScheduleInfo();
     			prepareNew();
     		} catch (Exception e) {
     			e.printStackTrace();
     			addErrorMessage(e.getMessage(), e);
     		}
    }
    
    public String getSDP_DECISION_NUM() {
		return SDP_DECISION_NUM;
	}

	public void setSDP_DECISION_NUM(String sDP_DECISION_NUM) {
		SDP_DECISION_NUM = sDP_DECISION_NUM;
	}

	public String getSDP_WITHDRAWAL_DECISION_NUM() {
		return SDP_WITHDRAWAL_DECISION_NUM;
	}

	public void setSDP_WITHDRAWAL_DECISION_NUM(String sDP_WITHDRAWAL_DECISION_NUM) {
		SDP_WITHDRAWAL_DECISION_NUM = sDP_WITHDRAWAL_DECISION_NUM;
	}

	public String getAM_DECISION_NUM() {
		return AM_DECISION_NUM;
	}

	public void setAM_DECISION_NUM(String aM_DECISION_NUM) {
		AM_DECISION_NUM = aM_DECISION_NUM;
	}

	public String getCRL_DECISION_NUM() {
		return CRL_DECISION_NUM;
	}

	public void setCRL_DECISION_NUM(String cRL_DECISION_NUM) {
		CRL_DECISION_NUM = cRL_DECISION_NUM;
	}

	public String getSP_SR_DECISION_NUM() {
		return SP_SR_DECISION_NUM;
	}

	public void setSP_SR_DECISION_NUM(String sP_SR_DECISION_NUM) {
		SP_SR_DECISION_NUM = sP_SR_DECISION_NUM;
	}

	public List<MeetingAgenda> getMeetingAgendaList() {
		return meetingAgendaList;
	}

	public void setMeetingAgendaList(List<MeetingAgenda> meetingAgendaList) {
		this.meetingAgendaList = meetingAgendaList;
	}

	public List<MeetingAgenda> getSelectMeetingAgendaList() {
		return selectedMeetingAgendaList;
	}

	public void setSelectMeetingAgendaList(List<MeetingAgenda> selectMeetingAgendaList) {
		this.selectedMeetingAgendaList = selectMeetingAgendaList;
	}

	/**
	 * @return the meetingUsersList
	 */
	public ArrayList<Users> getMeetingUsersList() {
		return meetingUsersList;
	}

	/**
	 * @param meetingUsersList the meetingUsersList to set
	 */
	public void setMeetingUsersList(ArrayList<Users> meetingUsersList) {
		this.meetingUsersList = meetingUsersList;
	}

	/**
	 * @return the meetingUser
	 */
	public Users getMeetingUser() {
		return meetingUser;
	}

	/**
	 * @param meetingUser the meetingUser to set
	 */
	public void setMeetingUser(Users meetingUser) {
		this.meetingUser = meetingUser;
	}

	public String getMeetingMessage() {
		return meetingMessage;
	}

	public void setMeetingMessage(String meetingMessage) {
		this.meetingMessage = meetingMessage;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
     
   
	
}
