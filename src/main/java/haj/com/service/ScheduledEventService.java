package haj.com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import haj.com.dao.ScheduledEventDAO;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.ProcessRoles;
import haj.com.entity.QdfCompany;
import haj.com.entity.ReviewCommitteeMeeting;
import haj.com.entity.ScheduledEvent;
import haj.com.entity.ScheduledEventUsers;
import haj.com.entity.Users;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class ScheduledEventService extends AbstractService {
	/** The dao. */
	private ScheduledEventDAO dao = new ScheduledEventDAO();

	/**
	 * Get all ScheduledEvent
 	 * @author TechFinium 
 	 * @see   ScheduledEvent
 	 * @return a list of {@link haj.com.entity.ScheduledEvent}
	 * @throws Exception the exception
 	 */
	public List<ScheduledEvent> allScheduledEvent() throws Exception {
	  	return dao.allScheduledEvent();
	}


	/**
	 * Create or update ScheduledEvent.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     ScheduledEvent
	 */
	public void create(ScheduledEvent entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  ScheduledEvent.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ScheduledEvent
	 */
	public void update(ScheduledEvent entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  ScheduledEvent.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ScheduledEvent
	 */
	public void delete(ScheduledEvent entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ScheduledEvent}
	 * @throws Exception the exception
	 * @see    ScheduledEvent
	 */
	public ScheduledEvent findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find ScheduledEvent by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.ScheduledEvent}
	 * @throws Exception the exception
	 * @see    ScheduledEvent
	 */
	public List<ScheduledEvent> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load ScheduledEvent
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.ScheduledEvent}
	 * @throws Exception the exception
	 */
	public List<ScheduledEvent> allScheduledEvent(int first, int pageSize) throws Exception {
		return dao.allScheduledEvent(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of ScheduledEvent for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the ScheduledEvent
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(ScheduledEvent.class);
	}
	
    /**
     * Lazy load ScheduledEvent with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 ScheduledEvent class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.ScheduledEvent} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<ScheduledEvent> allScheduledEvent(Class<ScheduledEvent> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<ScheduledEvent>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of ScheduledEvent for lazy load with filters
     * @author TechFinium 
     * @param entity ScheduledEvent class
     * @param filters the filters
     * @return Number of rows in the ScheduledEvent entity
     * @throws Exception the exception     
     */	
	
	public int count(Class<ScheduledEvent> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}


	public ScheduledEvent findByTargetKeyAndTargetClass(Long targetKey, String targetClass) throws Exception {
		return resolveDocs(dao.findByTargetKeyAndTargetClass(targetKey,targetClass));
	}
	
	public ScheduledEvent findByTargetKeyAndTargetClassReturnOneResult(Long targetKey, String targetClass) throws Exception{
		List<ScheduledEvent> list = findByTargetKeyAndTargetClassList(targetKey, targetClass);
		if (list != null && list.size() != 0) {
			return resolveDocs(list.get(0));
		}else {
			return null;
		}
		 
	}
	
	public List<ScheduledEvent> findByTargetKeyAndTargetClassList(Long targetKey, String targetClass) throws Exception{
		return dao.findByTargetKeyAndTargetClassList(targetKey, targetClass);
	}


	private ScheduledEvent resolveDocs(ScheduledEvent scheduledEvent) throws Exception {
		populadeDocs(scheduledEvent);
		return scheduledEvent;
	}
	
	public void populadeDocs(ScheduledEvent entityDoc) throws Exception {
		if (entityDoc!=null && entityDoc.getId() != null) {
			entityDoc.setDocs(DocService.instance().searchByTargetKeyAndClass(ScheduledEvent.class.getName(), entityDoc.getId()));			
		}
	}
	
	/**
	 * The first set of the Work Place Approval Visit Date
	 * @param workplaceapproval
	 * @throws Exception
	 */
	public void addSheduleInfo(String targetClass,Long targetKey, Users sessionUser, List<Users> selectedUsers,Date fromDateTime,Date toDateTime,String title,ReviewCommitteeMeeting reviewCommitteeMeeting) throws Exception{		
		List<IDataEntity> dataEntities = new ArrayList<>();
		ScheduledEvent scheduledEvent= new ScheduledEvent();
		HostingCompanyEmployeesService hostingCompanyEmployeesService=new HostingCompanyEmployeesService();
		scheduledEvent.setTargetKey(targetKey);
		scheduledEvent.setTargetClass(targetClass);
		scheduledEvent.setUser(sessionUser);
		scheduledEvent.setFromDateTime(fromDateTime);
		scheduledEvent.setToDateTime(toDateTime);
		scheduledEvent.setTitle(title);
		if(reviewCommitteeMeeting !=null)
		{
			scheduledEvent.setReviewCommitteeMeeting(reviewCommitteeMeeting);
		}
		dataEntities.add(scheduledEvent);
		for (Users user : selectedUsers){
			HostingCompanyEmployees hce=hostingCompanyEmployeesService.findByUserReturnHostCompany(user.getId());
			ScheduledEventUsers scheduledEventUsers=new ScheduledEventUsers(scheduledEvent, user);
			if(hce !=null)
			{
				scheduledEventUsers.setJobTitle(hce.getJobTitle());
			}
			dataEntities.add(scheduledEventUsers);
		}
		if(selectedUsers==null || selectedUsers.size()<1)
		{
			HostingCompanyEmployees hce=hostingCompanyEmployeesService.findByUserReturnHostCompany(sessionUser.getId());
			ScheduledEventUsers scheduledEventUsers=new ScheduledEventUsers(scheduledEvent, sessionUser);
			if(hce !=null)
			{
				scheduledEventUsers.setJobTitle(hce.getJobTitle());
			}
			dataEntities.add(scheduledEventUsers);
		}
		dao.createBatch(dataEntities);		
	
	}
}
