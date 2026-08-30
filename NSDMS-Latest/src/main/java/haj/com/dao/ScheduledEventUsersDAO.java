package haj.com.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.service.HostingCompanyDepartmentsService;
import haj.com.entity.ScheduledEvent;
import haj.com.entity.ScheduledEventUsers;
import haj.com.entity.Users;
import haj.com.entity.lookup.JobTitle;
import haj.com.entity.lookup.Roles;

public class ScheduledEventUsersDAO extends AbstractDAO  {

	private static final String leftJoin = " left join fetch o.scheduledEvent se left join fetch se.reviewCommitteeMeeting rcm ";
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ScheduledEventUsers
 	 * @author TechFinium 
 	 * @see    ScheduledEventUsers
 	 * @return a list of {@link haj.com.entity.ScheduledEventUsers}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ScheduledEventUsers> allScheduledEventUsers() throws Exception {
		return (List<ScheduledEventUsers>)super.getList("select o from ScheduledEventUsers o");
	}

	/**
	 * Get all ScheduledEventUsers between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    ScheduledEventUsers
 	 * @return a list of {@link haj.com.entity.ScheduledEventUsers}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ScheduledEventUsers> allScheduledEventUsers(Long from, int noRows) throws Exception {
	 	String hql = "select o from ScheduledEventUsers o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<ScheduledEventUsers>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    ScheduledEventUsers
 	 * @return a {@link haj.com.entity.ScheduledEventUsers}
 	 * @throws Exception global exception
 	 */
	public ScheduledEventUsers findByKey(Long id) throws Exception {
	 	String hql = "select o from ScheduledEventUsers o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (ScheduledEventUsers)super.getUniqueResult(hql, parameters);
	}
	
	

	/**
	 * Find ScheduledEventUsers by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    ScheduledEventUsers
  	 * @return a list of {@link haj.com.entity.ScheduledEventUsers}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ScheduledEventUsers> findByName(String description) throws Exception {
	 	String hql = "select o from ScheduledEventUsers o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ScheduledEventUsers>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<ScheduledEvent> findScheduledEventByJobTitle(JobTitle jobTitle,Date fromDateTime,Date toDateTime) throws Exception {
	 	String hql = "select disticnt(o.scheduledEvent) from ScheduledEventUsers o where o.jobTitle = :jobTitle and o.scheduledEvent.fromDateTime >= :fromDateTime and o.scheduledEvent.toDateTime <= :toDateTime " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("jobTitle", jobTitle);
	    parameters.put("fromDateTime", fromDateTime);
	    parameters.put("toDateTime", toDateTime);
		return (List<ScheduledEvent>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<ScheduledEvent> findScheduledEventByRole(Roles roles,Date fromDateTime,Date toDateTime) throws Exception {
	 	String hql = "select disticnt(o.scheduledEvent) from ScheduledEventUsers o where o.jobTitle.roles = :roles and o.scheduledEvent.fromDateTime >= :fromDateTime and o.scheduledEvent.toDateTime <= :toDateTime " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("roles", roles);
	    parameters.put("fromDateTime", fromDateTime);
	    parameters.put("toDateTime", toDateTime);
		return (List<ScheduledEvent>)super.getList(hql, parameters);
	}

	public List<ScheduledEvent> findEvents(Users users,Date fromDateTime,Date toDateTime) throws Exception {
		HostingCompanyDepartmentsService hostingCompanyDepartmentsService=new HostingCompanyDepartmentsService();
		List<ScheduledEvent> list=null;
		List<Users> usersList=hostingCompanyDepartmentsService.findEmployessUnderneath(users);
		 if(usersList !=null && usersList.size()>0)
		 { 
			String hql = "select o from ScheduledEventUsers o "
					+ "left join fetch o.scheduledEvent se "
					+ "left join fetch se.reviewCommitteeMeeting rcm where ";
			boolean addOr=false;
			for(Users u:usersList)
			{
				if(addOr){hql +=" or ";}
				hql +=" o.user.id ="+u.getId();
				addOr=true;
			}
			hql +=" and ((rcm.fromDateTime >= :fromDateTime and rcm.toDateTime <= :toDateTime) OR "
					+ "(o.scheduledEvent.fromDateTime >= :fromDateTime and o.scheduledEvent.toDateTime <= :toDateTime))";
		    Map<String, Object> parameters = new Hashtable<String, Object>();
		    parameters.put("fromDateTime", fromDateTime);
		    parameters.put("toDateTime", toDateTime);
		    List<ScheduledEventUsers> seList= (List<ScheduledEventUsers>)super.getList(hql, parameters);
		    if(seList !=null && seList.size()>0) {list=new ArrayList<>();}
		    for(ScheduledEventUsers se:seList)
		    {
		    	if(!list.contains(se.getScheduledEvent()))
		    	{
		    		list.add(se.getScheduledEvent());
		    	}
		    
		    }
		    
		    return list;
		    
		 }
		 else
		 {
			 return null;
		 }
		
	}

}

