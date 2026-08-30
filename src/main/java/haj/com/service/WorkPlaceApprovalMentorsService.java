package haj.com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.bean.AttachmentBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.WorkPlaceApprovalMentorsDAO;
import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.OfoCodes;
import haj.com.entity.SDFCompany;
import haj.com.entity.Sites;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.WorkPlaceApproval;
import haj.com.entity.WorkPlaceApprovalMentors;
import haj.com.entity.WorkPlaceApprovalSites;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.Roles;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.RegionService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RolesService;
import haj.com.utils.GenericUtility;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkPlaceApprovalMentorsService.
 */
public class WorkPlaceApprovalMentorsService extends AbstractService {
	/** The dao. */
	private WorkPlaceApprovalMentorsDAO dao = new WorkPlaceApprovalMentorsDAO();
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
	private RolesService rolesService = new RolesService();
	private RegionService regionService;
	private WorkPlaceApprovalService workPlaceApprovalService = new WorkPlaceApprovalService();
	/**
	 * Get all WorkPlaceApprovalMentors.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.WorkPlaceApprovalMentors}
	 * @throws Exception the exception
	 * @see   WorkPlaceApprovalMentors
	 */
	public List<WorkPlaceApprovalMentors> allWorkPlaceApprovalMentors() throws Exception {
	  	return dao.allWorkPlaceApprovalMentors();
	}


	/**
	 * Create or update WorkPlaceApprovalMentors.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     WorkPlaceApprovalMentors
	 */
	public void create(WorkPlaceApprovalMentors entity) throws Exception  {
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  WorkPlaceApprovalMentors.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WorkPlaceApprovalMentors
	 */
	public void update(WorkPlaceApprovalMentors entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  WorkPlaceApprovalMentors.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WorkPlaceApprovalMentors
	 */
	public void delete(WorkPlaceApprovalMentors entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WorkPlaceApprovalMentors}
	 * @throws Exception the exception
	 * @see    WorkPlaceApprovalMentors
	 */
	public WorkPlaceApprovalMentors findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find WorkPlaceApprovalMentors by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.WorkPlaceApprovalMentors}
	 * @throws Exception the exception
	 * @see    WorkPlaceApprovalMentors
	 */
	public List<WorkPlaceApprovalMentors> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load WorkPlaceApprovalMentors.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.WorkPlaceApprovalMentors}
	 * @throws Exception the exception
	 */
	public List<WorkPlaceApprovalMentors> allWorkPlaceApprovalMentors(int first, int pageSize) throws Exception {
		return dao.allWorkPlaceApprovalMentors(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of WorkPlaceApprovalMentors for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the WorkPlaceApprovalMentors
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(WorkPlaceApprovalMentors.class);
	}
	
    /**
     * Lazy load WorkPlaceApprovalMentors with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 WorkPlaceApprovalMentors class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.WorkPlaceApprovalMentors} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApprovalMentors> allWorkPlaceApprovalMentors(Class<WorkPlaceApprovalMentors> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<WorkPlaceApprovalMentors>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of WorkPlaceApprovalMentors for lazy load with filters.
     *
     * @author TechFinium
     * @param entity WorkPlaceApprovalMentors class
     * @param filters the filters
     * @return Number of rows in the WorkPlaceApprovalMentors entity
     * @throws Exception the exception
     */	
	public int count(Class<WorkPlaceApprovalMentors> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}

	public void approveRegistration(WorkPlaceApprovalMentors registration, Users user, Tasks tasks) throws Exception {
		registration.setApprovalEnum(ApprovalEnum.PendingFinalApproval);
		registration.setWithClientCompany(false);
		create(registration);
		TasksService.instance().findNextInProcessAndCreateTask("", user, registration.getId(), WorkPlaceApproval.class.getName(), true, tasks, null, null);
	}

	public void finalApproveRegistration(WorkPlaceApprovalMentors registration, Users activeUser, Tasks task) throws Exception {
		registration.setApprovalEnum(ApprovalEnum.Approved);
		registration.setApprovalDate(getSynchronizedDate());
		create(registration);	
		List<IDataEntity> updateBatch = new ArrayList<>();
		List<WorkPlaceApprovalSites>workPlaceApprovalSites = workPlaceApprovalService.findSitesByWorkPlaceApprovalMentors(registration);
		for(WorkPlaceApprovalSites w:workPlaceApprovalSites) {
			w.setApprovalEnum(ApprovalEnum.Approved);
			updateBatch.add(w);
		}
		dao.updateBatch(updateBatch);
		TasksService.instance().completeTask(task);
		sendApprovalEmail(registration, registration.getCreateUser());
	}
	
	public void rejectRegistration(WorkPlaceApprovalMentors registration, Users user, Tasks tasks, List<RejectReasons> rejectReasonsList) throws Exception {
		List<Users>list = new ArrayList<>();
		list.add(registration.getCreateUser());
		registration.setApprovalEnum(ApprovalEnum.Rejected);
		create(registration);
		
		List<IDataEntity> updateBatch = new ArrayList<>();
		List<WorkPlaceApprovalSites>workPlaceApprovalSites = workPlaceApprovalService.findSitesByWorkPlaceApprovalMentors(registration);
		for(WorkPlaceApprovalSites w:workPlaceApprovalSites) {
			w.setApprovalEnum(ApprovalEnum.Rejected);
			updateBatch.add(w);
		}
		dao.updateBatch(updateBatch);
		
		RejectReasonMultipleSelectionService.instance().removeCreateLinks(registration.getId(), WorkPlaceApproval.class.getName(), rejectReasonsList, user, ConfigDocProcessEnum.WORKPLACE_APPROVAL);
		TasksService.instance().findPreviousInProcessAndCreateTask("", user, registration.getId(), WorkPlaceApprovalMentors.class.getName(), true, tasks, null, list);
		sendRejectEmail(registration, registration.getCreateUser(), rejectReasonsList);
	}
	
	public void finalRejectRegistration(WorkPlaceApprovalMentors registration, Users user, Tasks tasks, List<RejectReasons> rejectReasonsList) throws Exception {
		registration.setApprovalEnum(ApprovalEnum.Rejected);
		registration.setApprovalDate(getSynchronizedDate());
		create(registration);		
		TasksService.instance().completeTask(tasks);
		RejectReasonMultipleSelectionService.instance().removeCreateLinks(registration.getId(), WorkPlaceApproval.class.getName(), rejectReasonsList, user, ConfigDocProcessEnum.WORKPLACE_APPROVAL);
		//sendRejectEmail(registration, user, tasks, rejectReasonsList);
		
	}
	
	public void createWorkplaceApproval(WorkPlaceApproval workPlaceApproval, Users user) throws Exception {
		WorkPlaceApprovalMentors workPlaceApprovalMentors = new WorkPlaceApprovalMentors();
		workPlaceApprovalMentors.setApprovalEnum(ApprovalEnum.PendingApproval);
		workPlaceApprovalMentors.setWithClientCompany(true);
		workPlaceApprovalMentors.setWorkPlaceApproval(workPlaceApproval);
		workPlaceApprovalMentors.setCreateUser(user);
		create(workPlaceApprovalMentors);
		String description = "Adding mentors to workplace approval application for ";	
		description += " has been submitted by " + workPlaceApproval.getCompany().getCompanyName() + " (" + workPlaceApproval.getCompany().getLevyNumber() + "). Please go to Outstanding Tasks on the Dashboard to complete the application.";
		List<Users>list = locateClintUsersForTasks(workPlaceApproval.getCompany());
		TasksService.instance().findFirstInProcessAndCreateTask(description, user, workPlaceApprovalMentors.getId(), WorkPlaceApprovalMentors.class.getName(), true, ConfigDocProcessEnum.WORKPLACE_APPROVAL_MENTORS, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName(), MailTagsEnum.Task, description), list);
		//TasksService.instance().createTaskUser(locateClintUsersForTasks(workPlaceApproval.getCompany()), WorkPlaceApprovalMentors.class.getName(), workPlaceApprovalMentors.getId(), taskDescription, user, true, true, null, ConfigDocProcessEnum.WORKPLACE_APPROVAL_MENTORS, false);
	}
	
	public List<Users> locateClintUsersForTasks(Company company) throws Exception{
		// checks for SDF Users
		List<Users> users = new ArrayList<>();
		SDFCompanyService sdfCompanyService = new SDFCompanyService();
		users.addAll(sdfCompanyService.findPrimaryAndLabourSDFUsers(company));
		// add users with responsibly 
		CompanyUsersService companyUsersService = new CompanyUsersService();
		if (users.size() == 0) {
			users.addAll(companyUsersService.findUsersByCompanyNotInResponsibilityAndUserPosition(company, ConfigDocProcessEnum.WORKPLACE_APPROVAL, (long) 4));
		}
		// add training provider users
		if (users.size() == 0) {
			users.addAll(companyUsersService.findUsersByCompanyResponsibility(company, ConfigDocProcessEnum.TP));
		}
		companyUsersService = null;
		sdfCompanyService = null;
		return users;
	}


	public void completeRegistration(WorkPlaceApprovalMentors workPlaceApprovalMentors, Users activeUser, Tasks task) throws Exception{
		WorkPlaceApproval workPlaceApproval = workPlaceApprovalService.findByKey(workPlaceApprovalMentors.getWorkPlaceApproval().getId());
		List<Users> list = new ArrayList<>();
		Users u = getCLO(workPlaceApproval.getCompany());
		if(u == null) {
			throw new Exception("Region CLO not found");
		}
		
		List<IDataEntity> updateBatch = new ArrayList<>();
		List<WorkPlaceApprovalSites>workPlaceApprovalSites = workPlaceApprovalService.findSitesByWorkPlaceApprovalMentors(workPlaceApprovalMentors);
		for(WorkPlaceApprovalSites w:workPlaceApprovalSites) {
			w.setApprovalEnum(ApprovalEnum.PendingFinalApproval);
			updateBatch.add(w);
		}
		dao.updateBatch(updateBatch);
		
		list.add(u);
		workPlaceApprovalMentors.setWithClientCompany(false);
		workPlaceApprovalMentors.setApprovalEnum(ApprovalEnum.PendingFinalApproval);
		dao.update(workPlaceApprovalMentors);
		TasksService.instance().findNextInProcessAndCreateTask(activeUser, task, list, false);
		sendAcknoledgementEmail(workPlaceApprovalMentors, activeUser);
	}
	
	
	public List<Users> findRegionClinetServiceAdministrator(Company company) throws Exception {
		List<Users> list = new ArrayList<>();
		Roles roles = rolesService.findByKey(HAJConstants.ROLES_CLIENT_LIAISON_OFFICER_ID);
		list = hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegionAndRole(company.getResidentialAddress().getTown(), roles);
		return list;
	}
	
	public Users getCLO(Company company) throws Exception {
		Users cloUser = new Users();
		if(company.getResidentialAddress() != null && company.getResidentialAddress().getTown()!=null) {
			RegionTown rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
			cloUser = rt.getClo().getUsers();
		}
		return cloUser;
	}
	
	public void sendAcknoledgementEmail(WorkPlaceApprovalMentors registration, Users u) throws Exception {	
		WorkPlaceApproval workPlaceApproval = workPlaceApprovalService.findByKey(registration.getWorkPlaceApproval().getId());
		List<WorkPlaceApprovalSites> workPlaceApprovalSites=workPlaceApprovalService.findSitesByWorkPlaceApprovalMentors(registration, null, null);
		String sitename = "";	
		if(workPlaceApproval.getSites() == null){
			sitename= workPlaceApproval.getCompany().getCompanyName();
		}else {
			sitename = workPlaceApproval.getSites().getCompanyName();
		}
		String title ="";
		if(u.getTitle()!=null) {
			title = u.getTitle().getDescription();
		}
		String qualifications = "";
		if(workPlaceApproval.getQualification() !=null){
			qualifications = convertSmeQualificationToHTML(workPlaceApproval.getQualification());
		}
		String mentors = convertMentorsToHTML(workPlaceApprovalSites);
	
		String welcome = "<p>Dear #title# #FirstName# #Surname#,</p>" 
						+ "<p>ACKNOWLEDGEMENT OF REQUEST TO LOAD ADDITIONAL MENTOR(S) TO APPROVED WORKPLACE APPROVAL FOR #COMPANYNAME# (#ENTITYID#): #SITENAME#.</p>" 
						+ "<p>The merSETA acknowledges receipt of the request to load additional mentor(s): </p>"
						+ mentors
						+ "<p>against the following qualification(s): </p>"
						+ qualifications
						+ "<p>Kindly be advised that it may take up to five (5) working days to process the application. </p>"
						+ "<p>Yours sincerely,</p>" 
						+ "<p>merSETA Client Services</p>" 
						+ "<br/>";
		welcome = welcome.replaceAll("#title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#COMPANYNAME#", workPlaceApproval.getCompany().getCompanyName());	
		welcome = welcome.replaceAll("#ENTITYID#", workPlaceApproval.getCompany().getLevyNumber());
		welcome = welcome.replaceAll("#SITENAME#", sitename);				
		GenericUtility.sendMail(u.getEmail(), "ACKNOWLEDGEMENT OF REQUEST TO LOAD ADDITIONAL MENTOR(S) TO APPROVED WORKPLACE APPROVAL", welcome, null);
	}
	
	public void sendApprovalEmail(WorkPlaceApprovalMentors registration, Users u) throws Exception {	
		WorkPlaceApproval workPlaceApproval = workPlaceApprovalService.findByKey(registration.getWorkPlaceApproval().getId());
		List<WorkPlaceApprovalSites> workPlaceApprovalSites=workPlaceApprovalService.findSitesByWorkPlaceApprovalMentors(registration, null, null);
		RegionTown rt = RegionTownService.instance().findByTown(workPlaceApproval.getCompany().getResidentialAddress().getTown());
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = regionService.findByKey(rt.getRegion().getId());
		String sitename = "";	
		if(workPlaceApproval.getSites() == null){
			sitename= workPlaceApproval.getCompany().getCompanyName();
		}else {
			sitename = workPlaceApproval.getSites().getCompanyName();
		}
		String title ="";
		if(u.getTitle()!=null) {
			title = u.getTitle().getDescription();
		}
		String qualifications = "";
		if(workPlaceApproval.getQualification() !=null){
			qualifications = convertSmeQualificationToHTML(workPlaceApproval.getQualification());
		}
		String mentors = convertMentorsToHTML(workPlaceApprovalSites);
	
		String welcome = "<p>Dear #title# #FirstName# #Surname#,</p>" 
						+ "<p>APPROVAL OF REQUEST TO LOAD ADDITIONAL MENTOR(S) FOR #COMPANYNAME# (#ENTITYID#): #SITENAME#.</p>" 
						+ "<p>Please be advised that the merSETA has approved the request to load the additional mentor(s) </p>"
						+ mentors
						+ "<p>for the following qualification(s):</p>"
						+ qualifications
						+ "<p>Please do not hesitate to contact the merSETA #RegionalOfficeName# Office for further assistance</p>"
						+ "<p>Yours sincerely,</p>" 
						+ "<p>merSETA Client Services</p>" 
						+ "<br/>";
		welcome = welcome.replaceAll("#title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#COMPANYNAME#", workPlaceApproval.getCompany().getCompanyName());	
		welcome = welcome.replaceAll("#ENTITYID#", workPlaceApproval.getCompany().getLevyNumber());
		welcome = welcome.replaceAll("#SITENAME#", sitename);	
		welcome = welcome.replaceAll("#RegionalOfficeName#", r.getDescription());
		GenericUtility.sendMail(u.getEmail(), "APPROVAL OF REQUEST TO LOAD ADDITIONAL MENTOR(S)", welcome, null);
		
	}
	
	/** Reporting End */
	public void sendRejectEmail(WorkPlaceApprovalMentors registration, Users u, List<RejectReasons> rejectReasonsList) throws Exception{
		
		WorkPlaceApproval workPlaceApproval = workPlaceApprovalService.findByKey(registration.getWorkPlaceApproval().getId());
		List<WorkPlaceApprovalSites> workPlaceApprovalSites=workPlaceApprovalService.findSitesByWorkPlaceApprovalMentors(registration, null, null);
		RegionTown rt = RegionTownService.instance().findByTown(workPlaceApproval.getCompany().getResidentialAddress().getTown());
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = regionService.findByKey(rt.getRegion().getId());
		String sitename = "";	
		if(workPlaceApproval.getSites() == null){
			sitename= workPlaceApproval.getCompany().getCompanyName();
		}else {
			sitename = workPlaceApproval.getSites().getCompanyName();
		}
		String title ="";
		if(u.getTitle()!=null) {
			title = u.getTitle().getDescription();
		}
		String qualifications = "";
		if(workPlaceApproval.getQualification() !=null){
			qualifications = convertSmeQualificationToHTML(workPlaceApproval.getQualification());
		}
		String mentors = convertMentorsToHTML(workPlaceApprovalSites);
	
		String welcome = "<p>Dear #title# #FirstName# #Surname#,</p>" 
						+ "<p>NON-APPROVAL OF REQUEST TO LOAD ADDITIONAL MENTOR(S) FOR #COMPANYNAME# (#ENTITYID#): #SITENAME#.</p>" 
						+ "<p>Please be advised that the merSETA has not approved the request to load the additional mentor(s) </p>"
						+ mentors
						+ "<p>against the following qualification(s): </p>"
						+ qualifications
						+ "<p>Please do not hesitate to contact the merSETA #RegionalOfficeName# Office for further assistance</p>"
						+ "<p>Yours sincerely,</p>" 
						+ "<p>merSETA Client Services</p>" 
						+ "<br/>";
		welcome = welcome.replaceAll("#title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#COMPANYNAME#", workPlaceApproval.getCompany().getCompanyName());	
		welcome = welcome.replaceAll("#ENTITYID#", workPlaceApproval.getCompany().getLevyNumber());
		welcome = welcome.replaceAll("#SITENAME#", sitename);	
		welcome = welcome.replaceAll("#RegionalOfficeName#", r.getDescription());
		GenericUtility.sendMail(u.getEmail(), "NON-APPROVAL OF REQUEST TO LOAD ADDITIONAL MENTOR(S)", welcome, null);
	}
	
	private String convertSmeQualificationToHTML(Qualification qualification) {
		String ofocode = "";
		if(qualification!=null && qualification.getCode() != null) {
			ofocode = qualification.getCode().toString();
		}
		String sb ="<ul>"+"<li>(" +ofocode+")  "+qualification.getDescription() +"</li>"+"</ul>"; 
		return sb;
	}
	
	private String convertMentorsToHTML(List<WorkPlaceApprovalSites> workPlaceApprovalSites) {
		String sb ="<ul>"; 
		for (WorkPlaceApprovalSites r: workPlaceApprovalSites){
			sb +="<li>"+r.getSitesSme().getFirstName() + " "+r.getSitesSme().getLastName()+" ("+r.getSitesSme().getIdentityNumber() +")</li>";
		}
		sb +="</ul>"; 
		return sb;
	}
	
	private String convertOfoCodeToHTML(OfoCodes qualification) {
		String ofocode = "";
		if(qualification!=null && qualification.getOfoCode() != null) {
			ofocode = qualification.getOfoCode();
		}
		String sb ="<ul>"+"<li>(" +ofocode+")  "+qualification.getDescription() +"</li>"+"</ul>"; 
		return sb;
	}

	public String convertToHTML(List<RejectReasons> rejectReasons){		
		String sb ="<ul>"; 
		for (RejectReasons r: rejectReasons){
			sb +="<li>"+r.getDescription() +"</li>";
		}
		sb +="</ul>"; 
		return sb;
	}
}
