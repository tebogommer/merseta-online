package haj.com.ui;


import javax.annotation.PostConstruct;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import haj.com.framework.AbstractUI;
import haj.com.service.ScheduledEventService;
import haj.com.service.ScheduledEventUsersService;
import haj.com.service.WorkPlaceApprovalService;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import haj.com.entity.ReviewCommitteeMeeting;
import haj.com.entity.ScheduledEvent;
import haj.com.entity.WorkPlaceApproval;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "scheduledEvenViewtUI")
@ViewScoped
public class ScheduledEvenViewtUI extends AbstractUI {

    private ScheduleModel lazyEventModel;
    private ScheduleEvent event = new DefaultScheduleEvent();    
	private LazyDataModel<WorkPlaceApproval> dataModel; 
	ScheduledEventService scheduledEventService=new ScheduledEventService();
	ScheduledEventUsersService scheduledEventUsersService=new ScheduledEventUsersService();
	private ScheduledEvent scheduledEvent;
   
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
		reviewMeetingInfo();
	}
	
	/**
	 * Get all ReviewCommitteeMeeting for data table
 	 * @author TechFinium 
 	 * @see    ReviewCommitteeMeeting
 	 */
	public void reviewMeetingInfo() {
		  lazyEventModel = new LazyScheduleModel() {
	            @Override
	            public void loadEvents(Date start, Date end) {	
	            	try { 
						List<ScheduledEvent> scheduleList=scheduledEventUsersService.findEvents(getSessionUI().getActiveUser(),start,end);
						if(scheduleList !=null)
						{
							for(ScheduledEvent meeting: scheduleList)
							{
								Date fromDate=null;
								Date toDate=null;
								if(meeting.getReviewCommitteeMeeting() !=null)
								{
									fromDate=meeting.getReviewCommitteeMeeting().getFromDateTime();
									toDate=meeting.getReviewCommitteeMeeting().getToDateTime();
								}
								else
								{
									fromDate=meeting.getFromDateTime();
									toDate=meeting.getToDateTime();
								}
								
								ScheduleEvent theEvent = new DefaultScheduleEvent(meeting.getTitle(),fromDate,toDate, meeting);
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
		} catch (ParseException e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}		
		
		return show;		
	}
	
	public LazyDataModel<WorkPlaceApproval> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WorkPlaceApproval> dataModel) {
		this.dataModel = dataModel;
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
       
			event = new DefaultScheduleEvent();
			super.runClientSideExecute("PF('eventDialog').hide()");
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
    }
     
    public void onEventSelect(SelectEvent selectEvent) {
    	try {
    		event = (ScheduleEvent) selectEvent.getObject();
    		scheduledEvent = (ScheduledEvent) event.getData();			
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
    }
     
    public void onDateSelect(SelectEvent selectEvent) {
    	try {
    		//workPlaceApproval = null;
			event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
    }    

    public void onEventMove(ScheduleEntryMoveEvent event) {
    	
        try {

		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
    }
     
    public void onEventResize(ScheduleEntryResizeEvent event) {
    	
        try {
        	   
     		} catch (Exception e) {
     			e.printStackTrace();
     			addErrorMessage(e.getMessage(), e);
     		}
    }

	public ScheduledEvent getScheduledEvent() {
		return scheduledEvent;
	}

	public void setScheduledEvent(ScheduledEvent scheduledEvent) {
		this.scheduledEvent = scheduledEvent;
	}

	public void setLazyEventModel(ScheduleModel lazyEventModel) {
		this.lazyEventModel = lazyEventModel;
	}

}
