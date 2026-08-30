package haj.com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import haj.com.bean.AttachmentBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.SDPExtensionOfScopeDAO;
import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.CompanyQualifications;
import haj.com.entity.CompanyUnitStandard;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.DocumentTracker;
import haj.com.entity.ProcessRoles;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.ReviewCommitteeMeeting;
import haj.com.entity.ReviewCommitteeMeetingAgenda;
import haj.com.entity.ReviewCommitteeMeetingUsers;
import haj.com.entity.SDPExtensionOfScope;
import haj.com.entity.SDPReAccreditation;
import haj.com.entity.Tasks;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingProviderDocParent;
import haj.com.entity.TrainingProviderSkillsProgramme;
import haj.com.entity.TrainingProviderSkillsSet;
import haj.com.entity.UserQualifications;
import haj.com.entity.UserUnitStandard;
import haj.com.entity.Users;
import haj.com.entity.enums.AccreditationApplicationTypeEnum;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.DocumentTrackerEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.AuditorMonitorReview;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractService;
import haj.com.framework.AbstractUI;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.AuditorMonitorReviewService;
import haj.com.service.lookup.RolesService;
import haj.com.service.lookup.TownService;
import haj.com.utils.GenericUtility;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class SDPExtensionOfScopeService extends AbstractService {
	/** The dao. */
	private SDPExtensionOfScopeDAO dao = new SDPExtensionOfScopeDAO();
	private TrainingProviderApplicationService trainingProviderApplicationService=new TrainingProviderApplicationService();
	private AuditorMonitorReviewService auditorMonitorReviewService = new AuditorMonitorReviewService();
	private ReviewCommitteeMeetingAgendaService reviewCommitteeMeetingAgendaSevice=new ReviewCommitteeMeetingAgendaService();
	private ReviewCommitteeMeetingUsersService reviewCommitteeMeetingUsersService=new ReviewCommitteeMeetingUsersService();
	String joins="left join fetch o.users u ";

	/**
	 * Get all SDPExtensionOfScope
 	 * @author TechFinium 
 	 * @see   SDPExtensionOfScope
 	 * @return a list of {@link haj.com.entity.SDPExtensionOfScope}
	 * @throws Exception the exception
 	 */
	public List<SDPExtensionOfScope> allSDPExtensionOfScope() throws Exception {
	  	return dao.allSDPExtensionOfScope();
	}


	/**
	 * Create or update SDPExtensionOfScope.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SDPExtensionOfScope
	 */
	public void create(SDPExtensionOfScope entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SDPExtensionOfScope.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SDPExtensionOfScope
	 */
	public void update(SDPExtensionOfScope entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SDPExtensionOfScope.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SDPExtensionOfScope
	 */
	public void delete(SDPExtensionOfScope entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SDPExtensionOfScope}
	 * @throws Exception the exception
	 * @see    SDPExtensionOfScope
	 */
	public SDPExtensionOfScope findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SDPExtensionOfScope by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SDPExtensionOfScope}
	 * @throws Exception the exception
	 * @see    SDPExtensionOfScope
	 */
	public List<SDPExtensionOfScope> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SDPExtensionOfScope
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SDPExtensionOfScope}
	 * @throws Exception the exception
	 */
	public List<SDPExtensionOfScope> allSDPExtensionOfScope(int first, int pageSize) throws Exception {
		return dao.allSDPExtensionOfScope(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SDPExtensionOfScope for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the SDPExtensionOfScope
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SDPExtensionOfScope.class);
	}
	
    /**
     * Lazy load SDPExtensionOfScope with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 SDPExtensionOfScope class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SDPExtensionOfScope} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SDPExtensionOfScope> allSDPExtensionOfScope(Class<SDPExtensionOfScope> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SDPExtensionOfScope>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SDPExtensionOfScope for lazy load with filters
     * @author TechFinium 
     * @param entity SDPExtensionOfScope class
     * @param filters the filters
     * @return Number of rows in the SDPExtensionOfScope entity
     * @throws Exception the exception     
     */	
	public int count(Class<SDPExtensionOfScope> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}

	public void requestNonMerSETAExtensionOfScope(Users users, SDPExtensionOfScope sdpextensionofscope,List<CompanyQualifications> companyQualifications, List<CompanyUnitStandard> companyUnitStandardsList, List<TrainingProviderSkillsProgramme> tpSkillsProgramme, List<TrainingProviderSkillsSet> tpSkillsSetList, List<TrainingProviderDocParent> docParentList)throws Exception  {
		List<IDataEntity> dataEntities = new ArrayList<>();
		TrainingProviderApplicationService trainingProviderApplicationService=new TrainingProviderApplicationService();
		TrainingProviderApplication trainingProviderApplication=sdpextensionofscope.getTrainingProviderApplication();
		sdpextensionofscope.setApprovalStatus(ApprovalEnum.PendingApproval);
		sdpextensionofscope.setPreviousApprovalStatus(trainingProviderApplication.getApprovalStatus());				
		sdpextensionofscope.setPreviousCodeOfConductAcceptDate(trainingProviderApplication.getCodeOfConductAcceptDate());
		trainingProviderApplication.setCodeOfConductAcceptDate(new Date());
		if (sdpextensionofscope.getTrainingProviderApplication() == null || sdpextensionofscope.getTrainingProviderApplication().getId() == null ) {
			throw new Exception("Unable to locate linked provider application. Contact support!");
		}
		create(sdpextensionofscope);
//		trainingProviderApplication.setApprovalStatus(ApprovalEnum.PendingApproval);
		trainingProviderApplicationService.update(trainingProviderApplication);
		
		for (TrainingProviderDocParent docParent : docParentList) {
			dataEntities.add(docParent);
			Doc doc = docParent.getDoc();
			doc.setVersionNo(1);
			doc.setUsers(users);
			doc.setTargetKey(docParent.getId());
			doc.setTargetClass(TrainingProviderDocParent.class.getName());
			dataEntities.add(doc);
			dataEntities.add(new DocByte(doc.getData(), doc));
			dataEntities.add(new DocumentTracker(doc, users, new java.util.Date(), DocumentTrackerEnum.Upload));
		}
		if (dataEntities.size() > 0) {
			dao.createBatch(dataEntities);
		}
		
		dataEntities = new ArrayList<IDataEntity>();
		// Creating qualifications
		for (CompanyQualifications cp : companyQualifications) {
			cp.setTargetClass(SDPExtensionOfScope.class.getName());
			cp.setTargetKey(sdpextensionofscope.getId());
			dataEntities.add(cp);
		}
		// Creating unit standards
		for (CompanyUnitStandard us : companyUnitStandardsList) {
			us.setTargetClass(SDPExtensionOfScope.class.getName());
			us.setTargetKey(sdpextensionofscope.getId());
			dataEntities.add(us);
		}
		// Creating Provider Skills Programme
		for (TrainingProviderSkillsProgramme tpSkillsProg : tpSkillsProgramme) {
			tpSkillsProg.setTargetClass(SDPExtensionOfScope.class.getName());
			tpSkillsProg.setTargetKey(sdpextensionofscope.getId());
			dataEntities.add(tpSkillsProg);
		}
		// Creating Provider Skills Set
		for (TrainingProviderSkillsSet tpSkillsSet : tpSkillsSetList) {
			tpSkillsSet.setTargetClass(SDPExtensionOfScope.class.getName());
			tpSkillsSet.setTargetKey(sdpextensionofscope.getId());
			dataEntities.add(tpSkillsSet);
		}
		
		// creates batch
		this.dao.createBatch(dataEntities);
		String description="";
		// Creating Task
		TasksService.instance().findFirstInProcessAndCreateTask(description, users, sdpextensionofscope.getId(), sdpextensionofscope.getClass().getName(), true, ConfigDocProcessEnum.NON_MERSETA_SCOPE_ADDITIONAL_QUALIFICATION,  MailDef.instance(MailEnum.Task, MailTagsEnum.Task, description), null);
		//Sending Acknowledgement email
		sendNonMerSetaExtensionOfScopeAcknowledgementEmail(users, trainingProviderApplication);
	}

	public void requestExtensionOfScope(Users users, SDPExtensionOfScope sdpextensionofscope, List<Qualification> qualificationList, List<UnitStandards> unitStandards, List<SkillsProgram> skillsProgramList, List<SkillsSet> skillsSetList) throws Exception {

		List<IDataEntity> dataEntities = new ArrayList<>();
		TrainingProviderApplicationService trainingProviderApplicationService=new TrainingProviderApplicationService();
		TrainingProviderApplication trainingProviderApplication=sdpextensionofscope.getTrainingProviderApplication();
//		List<Users> qaList=getQualityAssurorUsers(trainingProviderApplication.getCompany());
		List<Users> qaList=getQualityAssurorUsers(trainingProviderApplication);
		
		sdpextensionofscope.setApprovalStatus(ApprovalEnum.PendingApproval);
		sdpextensionofscope.setPreviousApprovalStatus(trainingProviderApplication.getApprovalStatus());				
		sdpextensionofscope.setPreviousCodeOfConductAcceptDate(trainingProviderApplication.getCodeOfConductAcceptDate());
		trainingProviderApplication.setCodeOfConductAcceptDate(new Date());
		if (sdpextensionofscope.getTrainingProviderApplication() == null || sdpextensionofscope.getTrainingProviderApplication().getId() == null) {
			throw new Exception("Unable to linked provider application tp request Contact Support!");
		}
		create(sdpextensionofscope);

//		trainingProviderApplication.setApprovalStatus(ApprovalEnum.PendingApproval);
		trainingProviderApplicationService.update(trainingProviderApplication);
		
		// Assigns qualifications
		for (Qualification qualification : qualificationList) {
			CompanyQualifications companyQualifications = new CompanyQualifications(qualification,sdpextensionofscope.getClass().getName(),sdpextensionofscope.getId());
			dataEntities.add(companyQualifications);
		}

		// Assigns unit standards
		for (UnitStandards unitStandard : unitStandards) {
			CompanyUnitStandard companyUnitStandard = new CompanyUnitStandard(unitStandard,sdpextensionofscope.getClass().getName(),sdpextensionofscope.getId());
			if(unitStandard.getQualification() !=null){
				companyUnitStandard.setForQualification(unitStandard.getQualification() );
			}
			dataEntities.add(companyUnitStandard);
		}
		// Assign Skills Program
		for(SkillsProgram sp:skillsProgramList){
			TrainingProviderSkillsProgramme tpSp=new TrainingProviderSkillsProgramme();
			tpSp.setTargetClass(sdpextensionofscope.getClass().getName());
			tpSp.setTargetKey(sdpextensionofscope.getId());
			tpSp.setSkillsProgram(sp);
			dataEntities.add(tpSp);
		}
		//Creating training Provider Skills Set
		for(SkillsSet ss:skillsSetList){
			TrainingProviderSkillsSet tpSs=new TrainingProviderSkillsSet();
			tpSs.setSkillsSet(ss);
			tpSs.setTargetClass(sdpextensionofscope.getClass().getName());
			tpSs.setTargetKey(sdpextensionofscope.getId());
			dataEntities.add(tpSs);
		}
		
		//Creating Self Evaluation
		auditorMonitorReviewService.prepSelfEvaluationExtensionOfScope(sdpextensionofscope.getClass().getName(), sdpextensionofscope.getId(), ConfigDocProcessEnum.SDPExtensionOfScope, trainingProviderApplication);
		// creates batch
		this.dao.createBatch(dataEntities);
		String description="";
		// Creating Task
		TasksService.instance().findFirstInProcessAndCreateTask(description, users, sdpextensionofscope.getId(), sdpextensionofscope.getClass().getName(), true, ConfigDocProcessEnum.SDPExtensionOfScope,  MailDef.instance(MailEnum.Task, MailTagsEnum.Task, description), qaList);
		//Sending Acknowledgement email
		sendTPExtensionOfScopeEmail(unitStandards, qualificationList, trainingProviderApplication.getCompany(), users, trainingProviderApplication, skillsProgramList, skillsSetList);
	}
	
	public List<Users> getQualityAssurorUsers(Company company) throws Exception
	{
		HostingCompanyEmployeesService hces = new HostingCompanyEmployeesService();
		TownService townService = new TownService();
		List<Users> qualityAssurorUsers = new ArrayList<>();
		Address address = null;
		if (company.getResidentialAddress().getId() != null) {
			address = AddressService.instance().findByKey(company.getResidentialAddress().getId());
		}
		if (address != null && address.getTown() != null) {
			qualityAssurorUsers = hces.locateHostingCompanyEmployeesByRegionAndRole(townService.findByKey(address.getTown().getId()), RolesService.instance().findByKey(HAJConstants.QUALITY_ASSUROR_ROLE_ID));
		}
		
		if(qualityAssurorUsers==null || qualityAssurorUsers.size()<1)
		{
			throw new Exception("We are unable to locate CLO for this company ("+company.getCompanyName()+"), Please contact support.");
		}
		
		return qualityAssurorUsers;
	}
	
	public List<Users> getQualityAssurorUsers(TrainingProviderApplication trainingProviderApplication) throws Exception
	{
		HostingCompanyEmployeesService hces = new HostingCompanyEmployeesService();
		TownService townService = new TownService();
		List<Users> qualityAssurorUsers = new ArrayList<>();
		Address address = null;
		if (trainingProviderApplication.getTrainingSite() != null && trainingProviderApplication.getTrainingSite().getId() != null && trainingProviderApplication.getTrainingSite().getResidentialAddress() != null && trainingProviderApplication.getTrainingSite().getResidentialAddress().getId() != null ) {
			address = AddressService.instance().findByKey(trainingProviderApplication.getTrainingSite().getResidentialAddress().getId());
		}else if (trainingProviderApplication.getCompany().getResidentialAddress().getId() != null) {
			address = AddressService.instance().findByKey(trainingProviderApplication.getCompany().getResidentialAddress().getId());
		}
		if (address != null && address.getTown() != null) {
			qualityAssurorUsers = hces.locateHostingCompanyEmployeesByRegionAndRole(townService.findByKey(address.getTown().getId()), RolesService.instance().findByKey(HAJConstants.QUALITY_ASSUROR_ROLE_ID));
		}
		
		if(qualityAssurorUsers==null || qualityAssurorUsers.size()<1)
		{
			throw new Exception("We are unable to locate CLO for this company ("+trainingProviderApplication.getCompany().getCompanyName()+"), Please contact support.");
		}
		
		return qualityAssurorUsers;
	}


	public void approveTask(Tasks task, Users user, SDPExtensionOfScope sdpextensionofscope, ReviewCommitteeMeeting reviewCommitteeMeeting,Company company) throws Exception {
		setTrainingProviderUsers(task, user, sdpextensionofscope);
		List<IDataEntity> iDataEntities = new ArrayList<>();
		if(task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalApproval) {
			sdpextensionofscope.setReviewCommitteeMeeting(reviewCommitteeMeeting);
			assignMeetingAgender(sdpextensionofscope, user);
			sdpextensionofscope.setApprovalStatus(ApprovalEnum.PendingCommitteeApproval);
			iDataEntities.add(sdpextensionofscope);
		} else if(sdpextensionofscope.getApprovalStatus()==ApprovalEnum.Rejected) {
			sdpextensionofscope.setApprovalStatus(ApprovalEnum.PendingApproval);
			iDataEntities.add(sdpextensionofscope);
		}
		dao.updateBatch(iDataEntities);
		if(task.getProcessRole().getRoleOrder()==1) {
			ArrayList<Users> usersList=new ArrayList<>(); 
			usersList.add(sdpextensionofscope.getUsers());
			completeTask("SDP Extension Of Scope", sdpextensionofscope.getId(), sdpextensionofscope.getClass().getName(), task, user, MailDef.instance(MailEnum.TPNewRequiresReview, MailTagsEnum.CompanyName, company.getCompanyName()),null, usersList);
		} else {
			completeTask("SDP Extension Of Scope", sdpextensionofscope.getId(), sdpextensionofscope.getClass().getName(), task, user, MailDef.instance(MailEnum.TPNewRequiresReview, MailTagsEnum.CompanyName, company.getCompanyName()), null);
		}
		
	}
	
	public void assignMeetingAgender( SDPExtensionOfScope sdpextensionofscope, Users user) throws Exception {
		ReviewCommitteeMeetingAgendaService reviewCommitteeMeetingAgendaSevice = new ReviewCommitteeMeetingAgendaService();
		ScheduledEventService scheduledEventService = new ScheduledEventService();
		ReviewCommitteeMeetingUsersService reviewCommitteeMeetingUsersService = new ReviewCommitteeMeetingUsersService();
		if (sdpextensionofscope.getReviewCommitteeMeeting() != null) {
			Long meetingAgendaID = HAJConstants.SDP_MEETING_SCHEDULE_TYPE_ID;// TrainingProviderApplication Approval ID
			ReviewCommitteeMeetingAgenda meetingAgender = reviewCommitteeMeetingAgendaSevice.findByMeetingAgendaAndReviewCommitteeMeeting(meetingAgendaID, sdpextensionofscope.getReviewCommitteeMeeting().getId());
			if (meetingAgender != null) {
				sdpextensionofscope.setReviewCommitteeMeetingAgenda(meetingAgender);
			} else {
				throw new Exception("Please add Training Provider Application Approval to the agenda of the selected Review Committee meeting");
			}
			// Adding meeting details to Events schedule
			List<Users> userList = reviewCommitteeMeetingUsersService.findUsersByReviewCommitteeMeeting(sdpextensionofscope.getReviewCommitteeMeeting().getId());
			scheduledEventService.addSheduleInfo(TrainingProviderApplication.class.getName(), sdpextensionofscope.getId(), user, userList, null, null, meetingAgender.getMeetingAgenda().getDescription(), sdpextensionofscope.getReviewCommitteeMeeting());
		}

	}
	
	public void completeTask(Tasks task, Users user, SDPExtensionOfScope sdpextensionofscope,ReviewCommitteeMeeting reviewCommitteeMeeting,Company company,List<AuditorMonitorReview> auditorMonitorReviews,TrainingProviderApplication trainingProviderApplication) throws Exception
	{
		/** Setting user who action the task */
		String desc="SDP Extension Of Scope";
		setTrainingProviderUsers(task, user, sdpextensionofscope);
		Boolean uploadEvidence=false;
		if (auditorMonitorReviews != null) this.dao.updateBatch((List<IDataEntity>) (List<?>) auditorMonitorReviews);
		if(task.getProcessRole().getRolePermission() == UserPermissionEnum.EditUpload)
		{
			List<Users> qualityAssurorUsers = new ArrayList<>();
			if (trainingProviderApplication != null && trainingProviderApplication.getCompany() != null) {
				qualityAssurorUsers =getQualityAssurorUsers(trainingProviderApplication);
			}else {
				qualityAssurorUsers =getQualityAssurorUsers(trainingProviderApplication.getCompany());
			}
			
			if(sdpextensionofscope.getApprovalStatus() == ApprovalEnum.PendingResubmition)
			{ 
				ProcessRolesService processRolesService= new ProcessRolesService();
				List<ProcessRoles> first = processRolesService.findNextProcessRoles(task.getProcessRole());
				if(first!=null && first.size()>0)
				{
					task.setProcessRole(first.get(0));
				}
			}
			TasksService.instance().findNextInProcessAndCreateTask(desc, user,sdpextensionofscope.getId(), sdpextensionofscope.getClass().getName(), true, task, null, qualityAssurorUsers);
		}
		else
		{
			if(sdpextensionofscope.getApprovalStatus() == ApprovalEnum.PendingResubmition)
			{ 
				ProcessRolesService processRolesService= new ProcessRolesService();
				List<ProcessRoles> first = processRolesService.findNextProcessRoles(task.getProcessRole());
				if(first!=null && first.size()>0)
				{
					task.setProcessRole(first.get(0));
				}
			}
			else if(task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalEdit){
				if (auditorMonitorReviewService.countByTargetClassKeyWhereOutcomeIsType(sdpextensionofscope.getClass().getName(), sdpextensionofscope.getId(), YesNoEnum.No) > 0) {
					uploadEvidence=true;
				}
//				for(AuditorMonitorReview selfEv: auditorMonitorReviews)
//				{
//					if(selfEv.getEvidenceRequiredEvaluatorOutcome() !=null && selfEv.getEvidenceRequiredEvaluatorOutcome()==YesNoEnum.No && (selfEv.getDocs()==null || selfEv.getDocs().size()<1))
//					{
//						uploadEvidence=true;
//						break;
//					}
//				}
				if(uploadEvidence){
					/**
					 * If Evidence Required,
					 * should get sent back to the SDP to upload evidence.
					 *  */
					ArrayList<Users> userList=new ArrayList<>();
					userList.add(sdpextensionofscope.getUsers());
					String description="";
					TasksService.instance().findPreviousInProcessAndCreateTask(description, user, sdpextensionofscope.getId(), sdpextensionofscope.getClass().getName(), true, task, null, userList);
				}
				else{
				
					TasksService.instance().findNextInProcessAndCreateTask(desc, user, sdpextensionofscope.getId(), sdpextensionofscope.getClass().getName(), true, task, null, null);
				}
			}
			else
			{
				TasksService.instance().findNextInProcessAndCreateTask(desc, user,sdpextensionofscope.getId(), sdpextensionofscope.getClass().getName(), true, task, null, null);
			}
			
		}
		
		/** Sending Site Visit Report */
		if (sdpextensionofscope.getSiteVisitDone() && sdpextensionofscope.getApprovalStatus() != ApprovalEnum.PendingResubmition) {
			trainingProviderApplicationService.sendExtentionOfScopeSiteVisitReport(sdpextensionofscope, true, trainingProviderApplication.getUsers(),uploadEvidence);
			/*if (trainingProviderApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL) {
				trainingProviderApplicationService.sendExtentionOfScopeSiteVisitReport(sdpextensionofscope, true, trainingProviderApplication.getUsers(),uploadEvidence);
			}*/

		}

		if (sdpextensionofscope.getApprovalStatus() == ApprovalEnum.Rejected || sdpextensionofscope.getApprovalStatus() == ApprovalEnum.PendingResubmition) {
			sdpextensionofscope.setApprovalStatus(ApprovalEnum.PendingApproval);
		   update(sdpextensionofscope);
		} else {
			update(sdpextensionofscope);
		}
		
	}
	
	
	public void setTrainingProviderUsers(Tasks task, Users user, SDPExtensionOfScope sdpextensionofscope) throws Exception {
		if (task.getProcessRole().getRoleOrder() == 1)// Quality Assuror
		{
			sdpextensionofscope.setQualityAssuranceUser(user);
		} else if (task.getProcessRole().getRoleOrder() == 3)// Manager: Quality Assurance
		{
			sdpextensionofscope.setManagerQualityAssurance(user);
			sdpextensionofscope.setRecommendedDate(new Date());
		} else if (task.getProcessRole().getRoleOrder() == 4)// Approved for Recommendation to Review Committee by(Senior Manager: Quality
															 // Assurance & Partnerships )
		{
			sdpextensionofscope.setSeniorManagerQualityAssurance(user);
			sdpextensionofscope.setRecommendedToReviewCommDate(new Date());
		}
		update(sdpextensionofscope);
	}
	
	public void rejectTask(String desc, Long targetKey, String targetClass, Tasks task, Users user, MailDef mailDef,ArrayList<RejectReasons> selectedRejectReason, SDPExtensionOfScope sdpextensionofscope) throws Exception {
		
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(targetKey, targetClass, selectedRejectReason, user, ConfigDocProcessEnum.SDPExtensionOfScope);
		for(RejectReasonMultipleSelection rrm:rrmList)
		{
			if(rrm !=null)
			{
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
			
		}
		List<Users>  userList=findReleventUsers(task, sdpextensionofscope);
		TasksService.instance().findPreviousInProcessAndCreateTask(desc, user, targetKey, targetClass, true, task, mailDef, userList);
		sdpextensionofscope.setApprovalStatus(ApprovalEnum.Rejected);
		update(sdpextensionofscope);
	}
	
	public List<Users> findReleventUsers(Tasks task,SDPExtensionOfScope sdpextensionofscope) throws Exception
	{
		List<Users> usersList=new ArrayList<>();
		if(task.getProcessRole().getRoleOrder().equals(1))//To Applicant
		{
			usersList.add(sdpextensionofscope.getUsers());
		}
		else if(task.getProcessRole().getRoleOrder().equals(2))//To Quality Assuror
		{
//			usersList=getQualityAssurorUsers(sdpextensionofscope.getTrainingProviderApplication().getCompany());
			usersList=getQualityAssurorUsers(sdpextensionofscope.getTrainingProviderApplication());
			
		}
		else if(task.getProcessRole().getRoleOrder().equals(3))//To Applicant
		{
			usersList.add(sdpextensionofscope.getUsers());
		}
		else if(task.getProcessRole().getRoleOrder().equals(4))//To Quality Assuror
		{
//			usersList=getQualityAssurorUsers(sdpextensionofscope.getTrainingProviderApplication().getCompany());
			usersList=getQualityAssurorUsers(sdpextensionofscope.getTrainingProviderApplication());
		}
		
		return usersList;
	}
	
	public void approveTtApplicationAndSendCertificate(SDPExtensionOfScope sdpextensionofscope,TrainingProviderApplication trainingProviderApplication,List<CompanyQualifications> companyQualifications,List<CompanyUnitStandard> unitStandards,ArrayList<TrainingProviderSkillsProgramme> newSkillsProgramList, Tasks task,ArrayList<TrainingProviderSkillsSet> skillsSetList) throws Exception
	{
		sdpextensionofscope.setApprovalStatus(ApprovalEnum.Approved);
		sdpextensionofscope.setApprovedDate(new Date());
		update(sdpextensionofscope);
		TasksService.instance().completeTask(task);
		sendProFormaLetterRorFullAccreditationEmail( trainingProviderApplication, companyQualifications, unitStandards,newSkillsProgramList,sdpextensionofscope,skillsSetList);

	}
	
	public void approveApplicationAndSendCertificate(SDPExtensionOfScope sdpextensionofscope,TrainingProviderApplication trainingProviderApplication,List<CompanyQualifications> companyQualifications,List<CompanyUnitStandard> unitStandards,ArrayList<TrainingProviderSkillsProgramme> newSkillsProgramListk,ArrayList<TrainingProviderSkillsSet> skillsSetList) throws Exception
	{
		List<IDataEntity> iDataEntities = new ArrayList<>();
		sdpextensionofscope.setApprovalStatus(ApprovalEnum.Approved);
		sdpextensionofscope.setApprovedDate(new Date());
		update(sdpextensionofscope);
		for(CompanyQualifications qual:companyQualifications)
		{
			qual.setCompany(sdpextensionofscope.getTrainingProviderApplication().getCompany());
			iDataEntities.add(qual);
		}
		for(CompanyUnitStandard us:unitStandards)
		{
			us.setCompany(sdpextensionofscope.getTrainingProviderApplication().getCompany());
			iDataEntities.add(us);
		}
		for(TrainingProviderSkillsProgramme sp:newSkillsProgramListk)
		{
			sp.setTrainingProviderApplication(trainingProviderApplication);
			iDataEntities.add(sp);
		}
		
		for(TrainingProviderSkillsSet sp:skillsSetList)
		{
			sp.setTrainingProviderApplication(trainingProviderApplication);
			iDataEntities.add(sp);
		}
		dao.updateBatch(iDataEntities);
		sendProFormaLetterRorFullAccreditationEmail(trainingProviderApplication, companyQualifications, unitStandards,newSkillsProgramListk,sdpextensionofscope,skillsSetList);

	}
	
	public void approveNonMerSETAExtensionOfScope(SDPExtensionOfScope sdpextensionofscope,List<CompanyQualifications> companyQualifications,List<CompanyUnitStandard> unitStandards,ArrayList<TrainingProviderSkillsProgramme> newSkillsProgramListk,ArrayList<TrainingProviderSkillsSet> skillsSetList,Users user,Tasks task) throws Exception
	{
		TrainingProviderApplicationService trainingProviderApplicationService=new TrainingProviderApplicationService();
		List<IDataEntity> iDataEntities = new ArrayList<>();
		sdpextensionofscope.setApprovalStatus(ApprovalEnum.Approved);
		sdpextensionofscope.setApprovedDate(new Date());
		update(sdpextensionofscope);
		TrainingProviderApplication trainingProviderApplication=sdpextensionofscope.getTrainingProviderApplication();
		trainingProviderApplication.setApprovalStatus(sdpextensionofscope.getPreviousApprovalStatus());
		trainingProviderApplicationService.update(trainingProviderApplication);
		List<Qualification> approvedQualificationsList=new ArrayList<>();
		for(CompanyQualifications qual:companyQualifications)
		{
			qual.setCompany(sdpextensionofscope.getTrainingProviderApplication().getCompany());
			qual.setTargetClass(trainingProviderApplication.getClass().getName());
			qual.setTargetKey(trainingProviderApplication.getId());
			iDataEntities.add(qual);
			if(qual.getAccept() !=null && qual.getAccept())
			{
				approvedQualificationsList.add(qual.getQualification());
			}
		}
		List<UnitStandards>approvedUnitStandardsList=new ArrayList<>();
		for(CompanyUnitStandard us:unitStandards)
		{
			us.setCompany(sdpextensionofscope.getTrainingProviderApplication().getCompany());
			us.setTargetClass(trainingProviderApplication.getClass().getName());
			us.setTargetKey(trainingProviderApplication.getId());
			iDataEntities.add(us);
			if(us.getAccept() !=null && us.getAccept())
			{
				approvedUnitStandardsList.add(us.getUnitStandard());
			}
		}
		List<SkillsProgram> approvedSpList=new ArrayList<>();
		for(TrainingProviderSkillsProgramme sp:newSkillsProgramListk)
		{
			sp.setTrainingProviderApplication(trainingProviderApplication);
			sp.setTargetClass(trainingProviderApplication.getClass().getName());
			sp.setTargetKey(trainingProviderApplication.getId());
			iDataEntities.add(sp);
			if(sp.getAccept() !=null && sp.getAccept()){
				approvedSpList.add(sp.getSkillsProgram());
			}
		}
		List<SkillsSet> approvedSsList=new ArrayList<>();
		for(TrainingProviderSkillsSet ss :skillsSetList)
		{
			ss.setTrainingProviderApplication(trainingProviderApplication);
			ss.setTargetClass(trainingProviderApplication.getClass().getName());
			ss.setTargetKey(trainingProviderApplication.getId());
			iDataEntities.add(ss);
			if(ss.getAccept() !=null && ss.getAccept()){
				approvedSsList.add(ss.getSkillsSet());
			}
		}
		dao.updateBatch(iDataEntities);
		TasksService.instance().completeTask(task);
		//Send Email Notification
		sendNonMerSetaExtensionOfScopeApprovalEmail(approvedUnitStandardsList, approvedQualificationsList, trainingProviderApplication.getUsers(), trainingProviderApplication, approvedSpList, approvedSsList);

	}
	
	public void extensionOfScopeETQAFinalApproval(SDPExtensionOfScope sdpextensionofscope,TrainingProviderApplication trainingProviderApplication,List<CompanyQualifications> companyQualifications,List<CompanyUnitStandard> unitStandards,ArrayList<TrainingProviderSkillsProgramme> newSkillsProgramListk,ArrayList<TrainingProviderSkillsSet> skillsSetList,Tasks task) throws Exception
	{
		List<IDataEntity> iDataEntities = new ArrayList<>();
		sdpextensionofscope.setApprovalStatus(ApprovalEnum.Approved);
		sdpextensionofscope.setApprovedDate(new Date());
		trainingProviderApplication.setApprovalStatus(sdpextensionofscope.getPreviousApprovalStatus());
		
		TrainingProviderApplicationService.instance().update(trainingProviderApplication);
		update(sdpextensionofscope);
		for(CompanyQualifications qual:companyQualifications)
		{
			qual.setCompany(sdpextensionofscope.getTrainingProviderApplication().getCompany());
			qual.setTargetClass(trainingProviderApplication.getClass().getName());
			qual.setTargetKey(trainingProviderApplication.getId());
			iDataEntities.add(qual);
		}
		for(CompanyUnitStandard us:unitStandards)
		{
			us.setCompany(sdpextensionofscope.getTrainingProviderApplication().getCompany());
			us.setTargetClass(trainingProviderApplication.getClass().getName());
			us.setTargetKey(trainingProviderApplication.getId());
			iDataEntities.add(us);
		}
		for(TrainingProviderSkillsProgramme sp:newSkillsProgramListk)
		{
			sp.setTrainingProviderApplication(trainingProviderApplication);
			sp.setTargetClass(trainingProviderApplication.getClass().getName());
			sp.setTargetKey(trainingProviderApplication.getId());
			iDataEntities.add(sp);
		}
		
		for(TrainingProviderSkillsSet sp:skillsSetList)
		{
			sp.setTrainingProviderApplication(trainingProviderApplication);
			sp.setTargetClass(trainingProviderApplication.getClass().getName());
			sp.setTargetKey(trainingProviderApplication.getId());
			iDataEntities.add(sp);
		}
		
		TasksService.instance().completeTask(task);
		dao.updateBatch(iDataEntities);
		sendProFormaLetterRorFullAccreditationEmail(trainingProviderApplication, companyQualifications, unitStandards,newSkillsProgramListk,sdpextensionofscope,skillsSetList);

	}
	
	public void extensionOfScopeETQAApproval(SDPExtensionOfScope sdpextensionofscope,Users formUser) throws Exception
	{
		ConfigDocProcessEnum configDocProcessEnum=ConfigDocProcessEnum.SDPExtensionOfScopeETQAApproval;
		sdpextensionofscope.setApprovalStatus(ApprovalEnum.ApprovedByETQA);
		update(sdpextensionofscope);
		String desc = AbstractUI.getEntryLanguage(configDocProcessEnum.getTaskDescription());
		TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, sdpextensionofscope.getId(), sdpextensionofscope.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, formUser.getFirstName(), MailTagsEnum.LastName, formUser.getLastName(), MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), null);
    }
	public void extensionOfScopeETQARejection(SDPExtensionOfScope sdpextensionofscope,Users formUser,List<RejectReasons> rejectReasons) throws Exception
	{
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(sdpextensionofscope.getId(), sdpextensionofscope.getClass().getName(), rejectReasons, formUser, ConfigDocProcessEnum.SDPExtensionOfScope);
		for(RejectReasonMultipleSelection rrm:rrmList){
			if(rrm !=null){
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		ConfigDocProcessEnum configDocProcessEnum=ConfigDocProcessEnum.SDPExtensionOfScopeETQAApproval;
		sdpextensionofscope.setApprovalStatus(ApprovalEnum.RejectedByEQTA);
		update(sdpextensionofscope);
		String desc = AbstractUI.getEntryLanguage(configDocProcessEnum.getTaskDescription());
		TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, sdpextensionofscope.getId(), sdpextensionofscope.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, formUser.getFirstName(), MailTagsEnum.LastName, formUser.getLastName(), MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), null);
    }
	
	public void finalRejection(SDPExtensionOfScope sdpextensionofscope, List<RejectReasons> rejectReason,Users user) throws Exception
	{
		TrainingProviderApplicationService trainingProviderApplicationService=new TrainingProviderApplicationService();
		TrainingProviderApplication tp=sdpextensionofscope.getTrainingProviderApplication();
		tp.setApprovalStatus(sdpextensionofscope.getPreviousApprovalStatus());
		trainingProviderApplicationService.update(tp);
		sdpextensionofscope.setApprovalStatus(ApprovalEnum.Rejected);
		update(sdpextensionofscope);
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(sdpextensionofscope.getId(), sdpextensionofscope.getClass().getName(), rejectReason, user, ConfigDocProcessEnum.SDPExtensionOfScope);
		for(RejectReasonMultipleSelection rrm:rrmList)
		{
			if(rrm !=null)
			{
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
			
		}
		//Email Notification
		sdpExtensionOfScopApplicationUnsuccessful(sdpextensionofscope.getUsers(), sdpextensionofscope,rejectReason);
	}
	
	public void nonMerSETAExtensionOfScopeRejection(SDPExtensionOfScope sdpextensionofscope, List<RejectReasons> rejectReason,Users user,Tasks task) throws Exception
	{
		TrainingProviderApplicationService trainingProviderApplicationService=new TrainingProviderApplicationService();
		TrainingProviderApplication tp=sdpextensionofscope.getTrainingProviderApplication();
		tp.setApprovalStatus(sdpextensionofscope.getPreviousApprovalStatus());
		trainingProviderApplicationService.update(tp);
		sdpextensionofscope.setApprovalStatus(ApprovalEnum.Rejected);
		update(sdpextensionofscope);
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(sdpextensionofscope.getId(), sdpextensionofscope.getClass().getName(), rejectReason, user, ConfigDocProcessEnum.SDPExtensionOfScope);
		for(RejectReasonMultipleSelection rrm:rrmList)
		{
			if(rrm !=null)
			{
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
			
		}
		TasksService.instance().completeTask(task);
		//Email Notification
		sendNonMerSetaExtensionOfscopeFinalRejectEmail(user, rejectReason, tp);
	}
	
	public void completeTask(String desc, Long targetKey, String targetClass, Tasks task, Users user, MailDef mailDef, List<AuditorMonitorReview> auditorMonitorReviews,ArrayList<Users> userList) throws Exception {
		if (auditorMonitorReviews != null) this.dao.updateBatch((List<IDataEntity>) (List<?>) auditorMonitorReviews);
		TasksService.instance().findNextInProcessAndCreateTask(desc, user, targetKey, targetClass, true, task, mailDef, userList);
	}
	
	public void completeTask(String desc, Long targetKey, String targetClass, Tasks task, Users user, MailDef mailDef, List<AuditorMonitorReview> auditorMonitorReviews) throws Exception {
		if (auditorMonitorReviews != null) this.dao.updateBatch((List<IDataEntity>) (List<?>) auditorMonitorReviews);
		TasksService.instance().findNextInProcessAndCreateTask(desc, user, targetKey, targetClass, true, task, mailDef, null);
	}
	
	public void sendProFormaLetterRorFullAccreditationEmail(TrainingProviderApplication tpApplication,List<CompanyQualifications> companyQualifications, List<CompanyUnitStandard> unitStandards,ArrayList<TrainingProviderSkillsProgramme> skillsProgramListk, SDPExtensionOfScope sdpextensionofscope,ArrayList<TrainingProviderSkillsSet> skillsSetList) throws Exception {
		
		
		AttachmentBean beanAttachment=new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans=new ArrayList<>();
		
		//Qualification/Unit Standards Attachment
		beanAttachment=new AttachmentBean();
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(trainingProviderApplicationService.getTPETQTP011Bytes(tpApplication, companyQualifications, unitStandards,skillsProgramListk,skillsSetList));
		beanAttachment.setFilename("Statement_Of_Qualifications_And_Or_UnitStandards.pdf");
		attachmentBeans.add(beanAttachment);

		String subject = "SDP Extension Of Scope".toUpperCase();
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " +sdpextensionofscope.getUsers().getFirstName()+ " " + sdpextensionofscope.getUsers().getLastName()+ ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "It is our pleasure to inform you that the merSETA "
				+ "Review Committee approved "+sdpextensionofscope.getTrainingProviderApplication().getCompany().getCompanyName()+"'s application "
				+ "for Extension Of Scope on "+GenericUtility.sdf7.format(sdpextensionofscope.getReviewCommitteeMeeting().getFromDateTime())+" "
				+ "for the qualification/s and/or trade/s and/or "
				+ "unit standards listed on your statement of "
				+ "qualifications and unit standards"
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
			    + "Please note that if your accreditation was for "
			    + "an area of specialisation within a qualification, "
			    + "this accreditation is for that area of "
			    + "specialisation only.  The provider is "
			    + "therefore required to deliver strictly "
			    + "according to the registered NQF "
			    + "qualification with specific "
			    + "reference to the 'Qualification rules'."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "merSETA will continue to monitor the standard of your "
				+ "training through regular auditing of the implementation "
				+ "of your quality management system. You will be contacted in this regard."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"

				+ "Congratulations on your achievement and thank you for your high level of commitment and professionalism."
			
				+ "</p>"
                
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Senior Manager: Assurance & Partnerships</p>";

		GenericUtility.sendMailWithAttachementTempWithLog(tpApplication.getUsers().getEmail(), subject, mssg, attachmentBeans, null,tpApplication.getClass().getName(),tpApplication.getId());

}


	public List<SDPExtensionOfScope> sortAndFilterWhere(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {String hql = "select o from SDPExtensionOfScope o "+joins+" where o.approvalStatus =:approvalStatus";
			return resolveData((List<SDPExtensionOfScope>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters,hql));
	}
	
	public List<SDPExtensionOfScope>  resolveData(List<SDPExtensionOfScope> tpList)
	{
		for(SDPExtensionOfScope tpApp:tpList)
		{
			try {
				tpApp.getReviewCommitteeMeeting().setReviewCommitteeMeetingUsersList((ArrayList<ReviewCommitteeMeetingUsers>) reviewCommitteeMeetingUsersService.findByReviewCommitteeMeeting(tpApp.getReviewCommitteeMeeting().getId()));
				tpApp.getReviewCommitteeMeeting().setReviewCommitteeMeetingAgendaList((ArrayList<ReviewCommitteeMeetingAgenda>) reviewCommitteeMeetingAgendaSevice.findByReviewCommitteeMeeting(tpApp.getReviewCommitteeMeeting().getId()));
				if (tpApp.getTrainingProviderApplication() != null && tpApp.getTrainingProviderApplication().getId() != null) {
					tpApp.setTrainingProviderApplication(trainingProviderApplicationService.findByKey(tpApp.getTrainingProviderApplication().getId() ));
				} else {
					tpApp.setTrainingProviderApplication(trainingProviderApplicationService.findByCertificateNumber(tpApp.getTrainingProviderApplication().getCompany().getAccreditationNumber()));
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return tpList;
	}
	
public void sdpExtensionOfScopApplicationUnsuccessful(Users user,SDPExtensionOfScope sdpextensionofscope, List<RejectReasons> rejectReasons) throws Exception {
		
		
		String subject = "SDP Extension Of Scope FOR: ".toUpperCase()+sdpextensionofscope.getTrainingProviderApplication().getCompany().getCompanyName()+" ("+sdpextensionofscope.getTrainingProviderApplication().getCompany().getLevyNumber()+")";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+user.getTitle().getDescription()+" "+user.getFirstName()+" "+user.getLastName()+",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We write to inform you that the merSETA Review Committee has considered the "
				+ "Extension Of Scope for your organisation "
				+ "submitted on "+HAJConstants.sdfDDMMYYYY2.format(sdpextensionofscope.getCreateDate())+". "
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We regret to advise that the outcome for the Extension Of Scope "
				+ "is <b>unsuccessful</b>. "
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "<b>Rejection Reason(s):</b><br/>"
				+ ""+convertToHTML(rejectReasons)+""
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "<b>The merSETA Team</b>"
				+ "</p>";
		GenericUtility.sendMail(user.getEmail(), subject, mssg, null);

	}

	public String convertToHTML(List<RejectReasons> rejectReasons){		
		String sb ="<ul>"; 
		for (RejectReasons r: rejectReasons){
			sb +="<li>"+r.getDescription() +"</li>";
		}
		sb +="</ul>"; 
		return sb;
	}
	
	public void sendNonSetaExtensionOfScopeEmail(List<UnitStandards> unitStandardsList, List<Qualification> qualificationsList, Company company, Users formUser,TrainingProviderApplication trainingProviderApplication, List<SkillsProgram> skillsProgramList, List<SkillsSet> skillsSetList) throws Exception {
		
		byte[] bites =TrainingProviderApplicationService.instance().createETQFM002AccreditationApprovalApplicationFormBytes(unitStandardsList, qualificationsList, company, formUser, trainingProviderApplication, skillsProgramList, skillsSetList,getTitle(trainingProviderApplication.getAccreditationApplicationTypeEnum())+" Extension Of Scope");
		
		String subject = "ACKNOWLEDGEMENT OF SKILLS DEVELOPMENT PROVIDER EXTENSION OF SCOPE";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+formUser.getTitle().getDescription()+" " +formUser.getFirstName() + " " + formUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The merSETA acknowledges receipt of a Skills Development "
				+ "Provider Extension Of Scope for "+company.getCompanyName()+" ("+company.getLevyNumber()+") for the following qualification(s) / unit standard(s) / skills programme(s) / skills set(s):"
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ convertToHTML(unitStandardsList, qualificationsList, skillsProgramList, skillsSetList)
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Kindly be advised that it may take up to five (5) "
				+ "working days to review the application.  "
				+ "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">merSETA Administration</p>";

		GenericUtility.sendMailWithAttachement(formUser.getEmail(), subject, mssg, bites, "Accreditation_Approval_Application_Form.pdf", "pdf", null);

	}
	
	public void sendTPExtensionOfScopeEmail(List<UnitStandards> unitStandardsList, List<Qualification> qualificationsList, Company company, Users formUser,TrainingProviderApplication trainingProviderApplication, List<SkillsProgram> skillsProgramList, List<SkillsSet> skillsSetList) throws Exception {
	
		byte[] bites =TrainingProviderApplicationService.instance().createETQFM002AccreditationApprovalApplicationFormBytes(unitStandardsList, qualificationsList, company, formUser, trainingProviderApplication, skillsProgramList, skillsSetList,getTitle(trainingProviderApplication.getAccreditationApplicationTypeEnum())+" Extension Of Scope");
		
		String subject = "ACKNOWLEDGEMENT OF SKILLS DEVELOPMENT PROVIDER EXTENSION OF SCOPE";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+formUser.getTitle().getDescription()+" " +formUser.getFirstName() + " " + formUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The merSETA acknowledges receipt of a Skills Development "
				+ "Provider Extension Of Scope for "+company.getCompanyName()+" ("+company.getLevyNumber()+") for the following qualification(s) / unit standard(s) / skills programme(s) / skills set(s):"
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ convertToHTML(unitStandardsList, qualificationsList, skillsProgramList, skillsSetList)
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Kindly be advised that it may take up to five (5) "
				+ "working days to review the application.  "
				+ "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">merSETA Administration</p>";

		GenericUtility.sendMailWithAttachement(formUser.getEmail(), subject, mssg, bites, "Accreditation_Approval_Application_Form.pdf", "pdf", null);

	}
	
	
	public String getTitle(AccreditationApplicationTypeEnum appType)
	{
		String title="";
		
		if(appType==AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL)
		{
			title="Primary Accreditation";
		}
		else if(appType==AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL)
		{
			title="Learning Programme Approval";
		}
		else if(appType==AccreditationApplicationTypeEnum.QCTOSkillsDevelopmentProvider)
		{
			title="QCTO Skills Development Provider";
		}
		else if(appType==AccreditationApplicationTypeEnum.QCTOTradeTestCentre)
		{
			title="QCTO Trade Test Centre Skills Development Provider";
		}
		else if(appType==AccreditationApplicationTypeEnum.Non_MerSETA_Focused_Provider)
		{
			title="Non-merSETA Scope Skills Development Provider";
		}
		else 
		{
			title=appType.getFriendlyName();
		}
			
		
		return title;
	}
	
	public String convertToHTML(List<UnitStandards> unitStandardsList, List<Qualification> qualificationsList,List<SkillsProgram> skillsProgramList, List<SkillsSet> skillsSetList){		
		String sb ="<ul style=\"font-size:11.0pt;\";font-family:\"Arial\">"; 
		
		
		for(UnitStandards obj:unitStandardsList)
		{
			sb +="<li>"+"("+obj.getCode()+") "+obj.getTitle()+""+ "</li>";
		}
		
		for(Qualification obj:qualificationsList)
		{
			sb +="<li>"+"("+obj.getCode()+") "+obj.getDescription()+""+ "</li>";
		}
		
		for(SkillsProgram obj:skillsProgramList)
		{
			sb +="<li>"+"("+obj.getProgrammeID()+") "+obj.getDescription()+""+ "</li>";
		}
		
		for(SkillsSet obj:skillsSetList)
		{
			sb +="<li>"+"("+obj.getProgrammeID()+") "+obj.getDescription()+""+ "</li>";
		}
		sb +="</ul>"; 
		return sb;
	}
	
	
	public void sendNonMerSetaExtensionOfScopeAcknowledgementEmail(Users formUser,TrainingProviderApplication tpApplication) throws Exception {
		
		String accreditationNumber="N/A";
		String sdpName="N/A";
		if(tpApplication.getCompany() !=null){
			sdpName=tpApplication.getCompany().getCompanyName();
		}
		if(tpApplication.getAccreditationNumber() !=null){
			accreditationNumber=tpApplication.getAccreditationNumber();
		}
		
		String subject = "ACKNOWLEDGEMENT OF SUBMISSION TO UPDATE QUALIFICATION ACCREDITATION DETAILS";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+formUser.getTitle().getDescription()+" " +formUser.getFirstName() + " " + formUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Your submission  dated "+HAJConstants.sdfDDMMYYYY2.format(new Date())+" to notify the merSETA of extension of scope details  for "+sdpName+" ("+accreditationNumber+") "
				+ " as a "+getTitle(tpApplication.getAccreditationApplicationTypeEnum())+" ("+accreditationNumber+") "
				+ "on the merSETA NSDMS is hereby acknowledged."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Your application will be evaluated by the merSETA and the "
				+ "process may take up to 7 working days. Should any additional "
				+ "information be required, this will be communicated to you."
				+ "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">ETQA  Administrator</p>";

		GenericUtility.sendMail(formUser.getEmail(), subject, mssg, null);

	}
	
	public void sendNonMerSetaExtensionOfscopeFinalRejectEmail(Users u, List<RejectReasons> selectedRejectReason,TrainingProviderApplication trainingProviderApplication) {
		String title ="";
		String rejectReason = convertToHTML(selectedRejectReason);
		if(u.getTitle()!=null) {
			title = u.getTitle().getDescription();
		}
		String accreditationNumber="N/A";
		String sdpName="N/A";
		if(trainingProviderApplication.getCompany() !=null){
			sdpName=trainingProviderApplication.getCompany().getCompanyName();
		}
		if(trainingProviderApplication.getAccreditationNumber() !=null){
			accreditationNumber=trainingProviderApplication.getAccreditationNumber();
		}
		String welcome = "<br/>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #Title# #FirstName# #Surname#,</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "We write to inform you that your application to "
						+ "notify the merSETA of extension of scope details for "+sdpName+" ("+accreditationNumber+") as a "+getTitle(trainingProviderApplication.getAccreditationApplicationTypeEnum())+" "
						+ "on the merSETA NSDMS has not been approved for the following reason(s):"
						+ "</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"+rejectReason+"</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "For any assistance/clarity, please contact the Regional Office."
						+ "</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Senior Manager: Quality Assurance & Partnerships"
						+ "</p>" 
						+ "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		GenericUtility.sendMail(u.getEmail(), "SKILLS DEVELOPMENT PROVIDER SUBMISSION TO UPDATE QUALIFICATION ACCREDITATION DETAILS OUTCOME", welcome, null);
	}
	
	public void sendNonMerSetaExtensionOfScopeApprovalEmail(List<UnitStandards> unitStandardsList, List<Qualification> qualificationsList,Users formUser,TrainingProviderApplication trainingProviderApplication, List<SkillsProgram> skillsProgramList,List<SkillsSet> skillsSetList) throws Exception {
		AttachmentBean beanAttachment=new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans=new ArrayList<>();
		byte[] bites =TrainingProviderApplicationService.instance().createETQFM002AccreditationApprovalApplicationFormBytes(unitStandardsList, qualificationsList, trainingProviderApplication.getCompany(), trainingProviderApplication.getUsers(), trainingProviderApplication, skillsProgramList, skillsSetList,getTitle(trainingProviderApplication.getAccreditationApplicationTypeEnum()));
		beanAttachment=new AttachmentBean();
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename("Accreditation_Approval_Application_Form.pdf");
		attachmentBeans.add(beanAttachment);
		String accrNumber="N/A";
		String sdpName="N/A";
		if(trainingProviderApplication.getCompany() !=null){
			sdpName=trainingProviderApplication.getCompany().getCompanyName();
		}
		if(trainingProviderApplication.getAccreditationNumber() !=null){
			accrNumber=trainingProviderApplication.getAccreditationNumber();
		}
		String subject = "SKILLS DEVELOPMENT PROVIDER SUBMISSION TO UPDATE QUALIFICATION ACCREDITATION DETAILS OUTCOME";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+formUser.getTitle().getDescription()+" " +formUser.getFirstName() + " " + formUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We write to inform you that your application to notify the merSETA of extension of scope details for "
				+ ""+sdpName+" ("+accrNumber+") as a "+getTitle(trainingProviderApplication.getAccreditationApplicationTypeEnum())+" "
				+ "on the merSETA NSDMS has been approved for the following qualification(s):"
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ convertToHTML(unitStandardsList, qualificationsList, skillsProgramList, skillsSetList)
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please note that if your accreditation was for an area of "
				+ "specialisation within a qualification, this accreditation is "
				+ "for that area of specialisation only. The provider is therefore "
				+ "required to deliver strictly according to the registered qualification."
				+ "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Senior Manager: Quality Assurance & Partnerships</p>";
		GenericUtility.sendMailWithAttachementTempWithLog(trainingProviderApplication.getUsers().getEmail(), subject, mssg, attachmentBeans, null,trainingProviderApplication.getClass().getName(),trainingProviderApplication.getId());

	}


	public int countByReviewCommitteeMeetingAgenda(ReviewCommitteeMeetingAgenda rcms)throws Exception {
		return dao.countByReviewCommitteeMeetingAgenda(rcms);
	}
	
	public int countOpenSDPExtensionOfScopeByProviderApplicationId(Long trainingProviderApplicationId, List<ApprovalEnum> openStatusList) throws Exception {
		return dao.countOpenSDPExtensionOfScopeByProviderApplicationId(trainingProviderApplicationId, openStatusList);
	}
}
