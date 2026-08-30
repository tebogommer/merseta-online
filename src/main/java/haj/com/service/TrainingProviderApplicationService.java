package haj.com.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.model.SortOrder;

import haj.com.bean.AttachmentBean;
import haj.com.bean.QualificationUnitStandardBean;
import haj.com.bean.SiteVisitFacilitatorAssessorBean;
import haj.com.bean.SiteVisitQualUnitStandardsBean;
import haj.com.bean.SiteVisitUserBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.TrainingProviderApplicationDAO;
import haj.com.entity.Address;
import haj.com.entity.AddressHistory;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.ChangeReason;
import haj.com.entity.Company;
import haj.com.entity.CompanyQualifications;
import haj.com.entity.CompanyUnitStandard;
import haj.com.entity.CompanyUsers;
import haj.com.entity.DateChangeMultipleSelection;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.DocumentTracker;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.NonSetaCompany;
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
import haj.com.entity.TrainingSite;
import haj.com.entity.UserChangeRequest;
import haj.com.entity.UserQualifications;
import haj.com.entity.UserUnitStandard;
import haj.com.entity.Users;
import haj.com.entity.enums.AccreditationApplicationTypeEnum;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AssessorModType;
import haj.com.entity.enums.AssessorModeratorAppTypeEnum;
import haj.com.entity.enums.AssessorModeratorTypeEnum;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.DocumentTrackerEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.TrainingProviderFilterEnum;
import haj.com.entity.lookup.AccreditationStatus;
import haj.com.entity.lookup.AreaForImprovement;
import haj.com.entity.lookup.AuditorMonitorReview;
import haj.com.entity.lookup.DateChangeReasons;
import haj.com.entity.lookup.Etqa;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.SdpType;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractService;
import haj.com.framework.AbstractUI;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.AccreditationStatusService;
import haj.com.service.lookup.AuditorMonitorReviewService;
import haj.com.service.lookup.EtqaService;
import haj.com.service.lookup.ProviderStatusService;
import haj.com.service.lookup.RegionService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.SdpTypeService;
import haj.com.utils.GenericUtility;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class TrainingProviderApplicationService extends AbstractService {
	/** The company service. */
	
	String joins="left join fetch o.users u "
			+ "left join fetch o.accreditationStatus "
			+ "left join fetch o.providerType "
			+ "left join fetch o.providerClass "
			+ "left join fetch o.etqa "
			+ "left join fetch u.residentialAddress "
			+ "left join fetch u.postalAddress "
			+ "left join fetch u.disabled "
			+ "left join fetch u.gender "
			+ "left join fetch u.equity "
			+ "left join fetch u.disabilityStatus "
			+ "left join fetch u.nationality ";
	
	/** The dao. */
	private TrainingProviderApplicationDAO dao = new TrainingProviderApplicationDAO();
	
	private static TrainingProviderApplicationService trainingProviderApplicationService = null;
	public static synchronized TrainingProviderApplicationService instance() {
		if (trainingProviderApplicationService == null) {
			trainingProviderApplicationService = new TrainingProviderApplicationService();
		}
		return trainingProviderApplicationService;
	}
	
	private ReviewCommitteeMeetingAgendaService reviewCommitteeMeetingAgendaSevice=new ReviewCommitteeMeetingAgendaService();
	private ReviewCommitteeMeetingUsersService reviewCommitteeMeetingUsersService=new ReviewCommitteeMeetingUsersService();
	private CompanyService companyService;
	CompanyUsersService companyUsersService=new CompanyUsersService();
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();

	/**
	 * Get all TrainingProviderApplication
	 * 
	 * @author TechFinium
	 * @see TrainingProviderApplication
	 * @return a list of {@link haj.com.entity.TrainingProviderApplication}
	 * @throws Exception
	 *             the exception
	 */
	public List<TrainingProviderApplication> allTrainingProviderApplication() throws Exception {
		return dao.allTrainingProviderApplication();
	}

	/**
	 * Create or update TrainingProviderApplication.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see TrainingProviderApplication
	 */
	public void create(TrainingProviderApplication entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update TrainingProviderApplication.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see TrainingProviderApplication
	 */
	public void update(TrainingProviderApplication entity) throws Exception {
		this.dao.update(entity);
	}
	
	public void createDateChangeReasons(TrainingProviderApplication trainingProviderApplication, Users sessionUser, List<DateChangeReasons> changeReasonsList, ConfigDocProcessEnum config) throws Exception{
		if (changeReasonsList != null && changeReasonsList.size() != 0 && config != null) {
			for (DateChangeReasons reason : changeReasonsList) {
				DateChangeMultipleSelection dateChangeMultipleSelection = new DateChangeMultipleSelection(trainingProviderApplication.getId(), trainingProviderApplication.getClass().getName(), reason, sessionUser, config);
				dao.create(dateChangeMultipleSelection);
			}
		}
	}
	
	public void createDateChangeReasons(SDPExtensionOfScope sdpextensionofscope , Users sessionUser, List<DateChangeReasons> changeReasonsList, ConfigDocProcessEnum config) throws Exception{
		if (changeReasonsList != null && changeReasonsList.size() != 0 && config != null) {
			for (DateChangeReasons reason : changeReasonsList) {
				DateChangeMultipleSelection dateChangeMultipleSelection = new DateChangeMultipleSelection(sdpextensionofscope.getId(), sdpextensionofscope.getClass().getName(), reason, sessionUser, config);
				dao.create(dateChangeMultipleSelection);
			}
		}
	}

	/**
	 * Delete TrainingProviderApplication.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see TrainingProviderApplication
	 */
	public void delete(TrainingProviderApplication entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.TrainingProviderApplication}
	 * @throws Exception
	 *             the exception
	 * @see TrainingProviderApplication
	 */
	public TrainingProviderApplication findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}
	
	public List<TrainingProviderApplication> findByAccreditationNumber(String accreditationNumber) throws Exception {
		return dao.findByAccreditationNumber(accreditationNumber);
	}
	
	public TrainingProviderApplication findByCertificateNumberOrAccreditationNumberAndInStatus(String certificateNumber, List<ApprovalEnum> approvalStatusList) throws Exception {
		return dao.findByCertificateNumberOrAccreditationNumberAndInStatus(certificateNumber, approvalStatusList);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> findByApplicatitionStatus(ApprovalEnum approvalStatus) throws Exception {
		return dao.findByApplicatitionStatus(approvalStatus);
	}

	/**
	 * Find TrainingProviderApplication by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.TrainingProviderApplication}
	 * @throws Exception
	 *             the exception
	 * @see TrainingProviderApplication
	 */
	public List<TrainingProviderApplication> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load TrainingProviderApplication
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.TrainingProviderApplication}
	 * @throws Exception
	 *             the exception
	 */
	public List<TrainingProviderApplication> allTrainingProviderApplication(int first, int pageSize) throws Exception {
		return dao.allTrainingProviderApplication(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of TrainingProviderApplication for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the TrainingProviderApplication
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(TrainingProviderApplication.class);
	}

	/**
	 * Lazy load TrainingProviderApplication with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            TrainingProviderApplication class
	 * @param first
	 *            the first
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @return a list of {@link haj.com.entity.TrainingProviderApplication}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> allTrainingProviderApplication(Class<TrainingProviderApplication> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<TrainingProviderApplication>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}
	
	/**
	 * Lazy load TrainingProviderApplication with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            TrainingProviderApplication class
	 * @param first
	 *            the first
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @return a list of {@link haj.com.entity.TrainingProviderApplication}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> sortAndFilterWhere(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from TrainingProviderApplication o "+joins+" where o.approvalStatus =:approvalStatus";
		return resolveMettingusersAndAgendas((List<TrainingProviderApplication>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters,hql));
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> findUserTP(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from TrainingProviderApplication o "+joins+" where o.users.id =:userID";
		return (List<TrainingProviderApplication>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters,hql);
	}
	
	@SuppressWarnings("unchecked")
	public int countUuserTP(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderApplication o where o.users.id =:userID";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> allTrainingProviderApplicationByCompanyUser(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long userId) throws Exception {
		String hql = "select o from TrainingProviderApplication o "+joins+" where o.company.id in (select x.company.id from CompanyUsers x where x.user.id = :userId and x.companyUserType = :providerProcess)";
		filters.put("userId", userId);
		filters.put("providerProcess", ConfigDocProcessEnum.TP);
		return (List<TrainingProviderApplication>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters,hql);
	}
	
	public int countAllTrainingProviderApplicationByCompanyUser(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderApplication o where o.company.id in (select x.company.id from CompanyUsers x where x.user.id = :userId and x.companyUserType = :providerProcess)";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> allTrainingProviderApplicationByCompanyUserAndSdpDesignation(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long userId, Boolean sdpDesignation) throws Exception {
		String hql = "select o from TrainingProviderApplication o "+joins+" where o.company.id in (select x.company.id from CompanyUsers x where x.user.id = :userId and x.companyUserType = :providerProcess and x.designation.sdpDesignation = :sdpDesignation)";
		filters.put("userId", userId);
		filters.put("providerProcess", ConfigDocProcessEnum.TP);
		filters.put("sdpDesignation", sdpDesignation);
		return (List<TrainingProviderApplication>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters,hql);
	}
	
	public int countAllTrainingProviderApplicationByCompanyUserAndSdpDesignation(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderApplication o where o.company.id in (select x.company.id from CompanyUsers x where x.user.id = :userId and x.companyUserType = :providerProcess and x.designation.sdpDesignation = :sdpDesignation)";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public int countByReviewCommitteeMeetingAgenda(ReviewCommitteeMeetingAgenda reviewCommitteeMeetingAgenda) throws Exception {
		String hql = "select count(o) from TrainingProviderApplication o where o.reviewCommitteeMeetingAgenda.id =:id";
		Map<String, Object> filters=new HashMap<>();
		filters.put("id", reviewCommitteeMeetingAgenda.getId());
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> allTrainingProviderApplicationsByStatus(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, ApprovalEnum status) throws Exception {
		String hql = "select o from TrainingProviderApplication o "+joins+" where o.approvalStatus = :status ";
		filters.put("status", status);
		return (List<TrainingProviderApplication>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters,hql);
	}
	
	public int countAllTrainingProviderApplicationsByStatus(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderApplication o where o.approvalStatus = :status";
		return dao.countWhere(filters, hql);
	}
	
	public List<TrainingProviderApplication>  resolveMettingusersAndAgendas(List<TrainingProviderApplication> tpList) {
		for(TrainingProviderApplication tpApp:tpList) {
			try {
				tpApp.getReviewCommitteeMeeting().setReviewCommitteeMeetingUsersList((ArrayList<ReviewCommitteeMeetingUsers>) reviewCommitteeMeetingUsersService.findByReviewCommitteeMeeting(tpApp.getReviewCommitteeMeeting().getId()));
				tpApp.getReviewCommitteeMeeting().setReviewCommitteeMeetingAgendaList((ArrayList<ReviewCommitteeMeetingAgenda>) reviewCommitteeMeetingAgendaSevice.findByReviewCommitteeMeeting(tpApp.getReviewCommitteeMeeting().getId()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tpList;
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> allTrainingProviderApplicationByUserLinkedAsSdp(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long userId) throws Exception {
		String hql = "select o from TrainingProviderApplication o " + joins + 
				" where o.company.id is not null  " + 
				" and ( (    " + 
				"     o.trainingSite is null " + 
				"     and o.company.id in (  " + 
				"         select distinct x.company.id from SDPCompany x where x.trainingSite is null   " + 
				"         and x.sdp.id = :userId  " + 
				"     )  " + 
				" ) or (  " + 
				"     o.trainingSite is not null  " + 
				"     and o.trainingSite.id in (  " + 
				"         select distinct x.trainingSite.id from SDPCompany x where x.trainingSite is not null   " + 
				"         and x.sdp.id = :userId  " + 
				"     )  " + 
				" ) )";
		filters.put("userId", userId);
		return (List<TrainingProviderApplication>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters,hql);
	}
	
	public int countAllTrainingProviderApplicationByUserLinkedAsSdp(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderApplication o " + 
				"where o.company.id is not null  " + 
				"and ( (    " + 
				"    o.trainingSite is null " + 
				"    and o.company.id in (  " + 
				"        select distinct x.company.id from SDPCompany x where x.trainingSite is null   " + 
				"        and x.sdp.id = :userId  " + 
				"    )  " + 
				") or (  " + 
				"    o.trainingSite is not null  " + 
				"    and o.trainingSite.id in (  " + 
				"        select distinct x.trainingSite.id from SDPCompany x where x.trainingSite is not null   " + 
				"        and x.sdp.id = :userId  " + 
				"    )  " + 
				") )";
		return dao.countWhere(filters, hql);
	}

	/**
	 * Get count of TrainingProviderApplication for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            TrainingProviderApplication class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the TrainingProviderApplication entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<TrainingProviderApplication> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	public List<Company> locateTrainingProviderApplicationByStatusDateAndExpiry(ApprovalEnum status, Date date, List<AccreditationApplicationTypeEnum> accreditationApplicationTypeEnumList) throws Exception {
		return dao.locateTrainingProviderApplicationByStatusDateAndExpiry(status, date, accreditationApplicationTypeEnumList);
	}
	
	public List<Company> locateTrainingProviderApplicationByStatusDateAndExpiryAndRegionId(ApprovalEnum status, Date date, List<AccreditationApplicationTypeEnum> accreditationApplicationTypeEnumList, Long regionId) throws Exception {
		return dao.locateTrainingProviderApplicationByStatusDateAndExpiryAndRegionId(status, date, accreditationApplicationTypeEnumList, regionId);
	}

	public List<TrainingProviderApplication> findByCompany(Company company) throws Exception {
		return dao.findByCompany(company);
	}
	
	public boolean determainQaInvolvedWithReg(Company company, Users user) throws Exception {
		boolean involvedWithReg = false;
		
		List<TrainingProviderApplication> tpaList = findByCompanyAndStatus(company, ApprovalEnum.Approved);
		for (TrainingProviderApplication trainingProviderApplication : tpaList) {
			if (trainingProviderApplication.getQualityAssuranceUser() != null && trainingProviderApplication.getQualityAssuranceUser().getId().equals(user.getId())) {
				involvedWithReg = true;
				break;
			}
		}
		
		return involvedWithReg;
	}

	public List<TrainingProviderApplication> findByUserAndCompany(Users users, Company company) throws Exception {
		return dao.findByUserAndCompany(users, company);
	}
	
	public List<TrainingProviderApplication> findByUserAndCompanyAndApplicationtype(Users users, Company company,AccreditationApplicationTypeEnum accreditationApplicationTypeEnum) throws Exception {
		return dao.findByUserAndCompanyAndApplicationtype(users, company,accreditationApplicationTypeEnum);
	}
	
	public void validateCompanyUser(Users users, Company company, AccreditationApplicationTypeEnum accreditationApplicationTypeEnum) throws Exception {
		List<TrainingProviderApplication> list= findByUserAndCompany(users, company);
		if(list !=null && list.size()>0) {
			for(TrainingProviderApplication tp:list) {
				if(tp.getAccreditationApplicationTypeEnum()==AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL &&  accreditationApplicationTypeEnum==AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL && 
				(tp.getFinalRejected() ==null || (tp.getFinalRejected() !=null && tp.getFinalRejected()==false))) {
					String mssg = "Application cannot be processes as there is currently an application under review.";
					if(tp.getApprovalStatus()==ApprovalEnum.Approved) {
						 mssg="Application cannot be processes as there is currently an approved application.";
					}
					throw new Exception(mssg);
				}
				
//				if((tp.getFinalRejected() ==null || tp.getFinalRejected()==false) && tp.getAccreditationApplicationTypeEnum()==accreditationApplicationTypeEnum) {
//					String mssg = "Application cannot be processes as there is currently an application under review.";
//					if(tp.getApprovalStatus()==ApprovalEnum.Approved) {
//						mssg="Application cannot be processes as there is currently an approved application.";
//					}
//					throw new Exception(mssg);
//				}
			}
		}
		
	}
	
	public void validateProviderApplicationNewSubmission(Company company, TrainingSite site, AccreditationApplicationTypeEnum applicationType, boolean siteSelected) throws Exception {
		// check if primary or learning Program approval 
		if (applicationType != null && (applicationType == AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL ||applicationType == AccreditationApplicationTypeEnum. ACCREDITATIONAPPROVAL)) {
			if (siteSelected) {
				if (site != null && site.getId() != null) {
					if (countByCompanyIdSiteIdApplicationTypeAndStatusList(applicationType, company.getId(), site.getId(), ApprovalEnum.getOpenStatusForTrainingProviderApplications()) > 0) {
						// failed, open application linked to site and company
						throw new Exception("Application cannot be processed. Application type: "+applicationType.getFriendlyName()+" already registered on NSDMS for company and site selected.");
					}
				}
			} else {
				if (countProviderApllicationsByOpenStatusApplicationTypeAndCompanyIdWithNoSiteAssigned(applicationType, company.getId(), ApprovalEnum.getOpenStatusForTrainingProviderApplications()) > 0) {
					// failed, open application linked to company with no site assigned
					throw new Exception("Application cannot be processed. Application type: "+applicationType.getFriendlyName()+" already registered on NSDMS for company selected.");
				}
			}
			
		}
	}
	
	public List<TrainingProviderApplication> findByUser(Users users) throws Exception {
		return dao.findByUser(users);
	}
	
	public List<TrainingProviderApplication> findByCompanyAndStatus(Company company,ApprovalEnum approvalStatus) throws Exception{
		return dao.findByCompanyAndStatus(company, approvalStatus);
	}
	
	public void createSeniorManagerApprovalTask(TrainingProviderApplication tpApplication,Users formUser) throws Exception
	{
		ConfigDocProcessEnum configDocProcessEnum=ConfigDocProcessEnum.TP_ETQA_APPROVAL;
		if(tpApplication.getAccreditationApplicationTypeEnum()==AccreditationApplicationTypeEnum.REACCREDITATIONREAPPROVAL){
			SDPReAccreditationService sdpReAccreditationService=new SDPReAccreditationService();
			SDPReAccreditation sdpReAccreditation =sdpReAccreditationService.findByTrainingProviderApplication(tpApplication.getId()).get(0);
			
			configDocProcessEnum=ConfigDocProcessEnum.SDP_RE_ACC_ETQA_APPROVAL;
			tpApplication.setApprovalStatus(ApprovalEnum.ApprovedByETQA);
			update(tpApplication);
			String desc = AbstractUI.getEntryLanguage(configDocProcessEnum.getTaskDescription());
			TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, sdpReAccreditation.getId(), sdpReAccreditation.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, formUser.getFirstName(), MailTagsEnum.LastName, formUser.getLastName(), MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), null);
		
		}
		else
		{
			tpApplication.setApprovalStatus(ApprovalEnum.ApprovedByETQA);
			update(tpApplication);
			String desc = AbstractUI.getEntryLanguage(configDocProcessEnum.getTaskDescription());
			TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, tpApplication.getId(), tpApplication.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, formUser.getFirstName(), MailTagsEnum.LastName, formUser.getLastName(), MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), null);
		}
	}
	
	public void createSeniorManagerRejectionTask(TrainingProviderApplication tpApplication,Users formUser,List<RejectReasons> rejectReasons) throws Exception
	{
		ConfigDocProcessEnum configDocProcessEnum=ConfigDocProcessEnum.TP_ETQA_APPROVAL;
		if(tpApplication.getAccreditationApplicationTypeEnum()==AccreditationApplicationTypeEnum.REACCREDITATIONREAPPROVAL){
			SDPReAccreditationService sdpReAccreditationService=new SDPReAccreditationService();
			SDPReAccreditation sdpReAccreditation =sdpReAccreditationService.findByTrainingProviderApplication(tpApplication.getId()).get(0);
			
			configDocProcessEnum=ConfigDocProcessEnum.SDP_RE_ACC_ETQA_APPROVAL;
			tpApplication.setApprovalStatus(ApprovalEnum.RejectedByEQTA);
			update(tpApplication);
			String desc = AbstractUI.getEntryLanguage(configDocProcessEnum.getTaskDescription());
			TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, sdpReAccreditation.getId(), sdpReAccreditation.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, formUser.getFirstName(), MailTagsEnum.LastName, formUser.getLastName(), MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), null);
		
		}
		else
		{
			tpApplication.setApprovalStatus(ApprovalEnum.RejectedByEQTA);
			update(tpApplication);
			String desc = AbstractUI.getEntryLanguage(configDocProcessEnum.getTaskDescription());
			TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, tpApplication.getId(), tpApplication.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, formUser.getFirstName(), MailTagsEnum.LastName, formUser.getLastName(), MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), null);
		}
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(tpApplication.getId(), tpApplication.getClass().getName(), rejectReasons, formUser, ConfigDocProcessEnum.TP);
		for(RejectReasonMultipleSelection rrm:rrmList)
		{
			if(rrm !=null)
			{
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
			
		}
	}
	
	public void rejectionTaskForResubmition(TrainingProviderApplication tpApplication,Users formUser,List<RejectReasons> rejectReasons, Users users,ConfigDocProcessEnum configDocProcessEnum) throws Exception
	{
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(tpApplication.getId(), tpApplication.getClass().getName(), rejectReasons, formUser, configDocProcessEnum);
		for(RejectReasonMultipleSelection rrm:rrmList)
		{
			if(rrm !=null)
			{
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
			
		}
		String desc="Your SDP application has been rejected by ETQA Review Committee,please review and re-submit";
		tpApplication.setApprovalStatus(ApprovalEnum.PendingResubmition);
		tpApplication.setProviderStatus(ProviderStatusService.instance().findByCode("515"));
		tpApplication.setEtqaReviewCommitteeDate(null);
		tpApplication.setRecommendedDate(null);
		tpApplication.setRecommendedToReviewCommDate(null);
		tpApplication.setReviewCommitteeMeeting(null);
		tpApplication.setReviewCommitteeMeetingAgenda(null);
		tpApplication.setSiteVisitDate(null);
		tpApplication.setSiteVisitDone(null);
		tpApplication.setSiteVisitReportDate(null);
		clearSelfEvaluation(tpApplication);
		update(tpApplication);
		List<Users> userList=new ArrayList<>();
		userList.add(tpApplication.getUsers());
		TasksService.instance().findFirstInProcessAndCreateRejectTask(desc, users, tpApplication.getId(),  tpApplication.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), userList);
		//Sending Email
		sendResubmition60Dayrejection(tpApplication.getUsers(), rejectReasons, tpApplication);

	}
	
	public void clearSelfEvaluation(TrainingProviderApplication trainingProviderApplication) throws Exception
	{
		List<IDataEntity> dataEntities = new ArrayList<>();
		AuditorMonitorReviewService auditorMonitorReviewService = new AuditorMonitorReviewService();
		List<AuditorMonitorReview> auditorMonitorReviewDataModelList = auditorMonitorReviewService.findByTargetKeyAndClass(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
		if(auditorMonitorReviewDataModelList !=null && auditorMonitorReviewDataModelList.size()>0)
		{
			for(AuditorMonitorReview evaluation:auditorMonitorReviewDataModelList)
			{
				evaluation.setComment(null);
				evaluation.setEvidenceRequired(null);
				evaluation.setEvidenceRequiredEvaluatorOutcome(null);
				dataEntities.add(evaluation);
			}
			
			dao.updateBatch(dataEntities);
			
		}
		
	}
	
	/**
	 * Find Last Approved TrainingProviderApplication
 	 * @author TechFinium 
 	 */
	@SuppressWarnings("unchecked")
	public TrainingProviderApplication findLastApproved() throws Exception {
		return dao.findLastApproved();
	}
	
	public List<TrainingProviderApplication> findListLastApproved() throws Exception{
		return dao.findListLastApproved();
	}
	
	@SuppressWarnings("unchecked")
	public TrainingProviderApplication findByCertificateNumber(String certificateNumber) throws Exception {
		return dao.findByCertificateNumber(certificateNumber);
	}
	
	@SuppressWarnings("unchecked")
	public TrainingProviderApplication findByCertificateNumberOrAccreditationNumber(String certificateNumber,ApprovalEnum approvalStatus) throws Exception {
		return dao.findByCertificateNumberOrAccreditationNumber(certificateNumber, approvalStatus);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> findByCertificateNumberOrAccreditationNumberList(String certificateNumber,ApprovalEnum approvalStatus) throws Exception {
		return dao.findByCertificateNumberOrAccreditationNumberList(certificateNumber, approvalStatus);
	}
	
	
	public void approveTtApplicationAndSendCertificate(TrainingProviderApplication tpApplication,List<CompanyQualifications> companyQualifications,List<CompanyUnitStandard> unitStandards,ArrayList<TrainingProviderSkillsProgramme> skillsProgramList, Tasks task, List<TrainingProviderSkillsSet> tpSkillsSetList) throws Exception {
		tpApplication.setApprovalStatus(ApprovalEnum.Approved);
		tpApplication.setApprovedDate(new Date());
		String certificateNum="";
		
		AssessorModeratorApplicationService amService=new AssessorModeratorApplicationService();
		CompanyService companyService=new CompanyService();
	    certificateNum=amService.getAssessorTrainingProviderCertificateNum(null, tpApplication);
		tpApplication.setCertificateNumber(certificateNum);
		Company company=tpApplication.getCompany();
		if(company.getAccreditationNumber() !=null){
			company.setAccreditationNumber(certificateNum);
		}
		company.setCompanyStatus(CompanyStatusEnum.Active);
		companyService.upadeCompanyInfo(company);
		update(tpApplication);
		TasksService.instance().completeTask(task);
		
		//Setting user to be active
		UsersService userService=new UsersService();
		Users user=tpApplication.getUsers();
		user.setActive(true);
		userService.update(user);
		sendProFormaLetterRorFullAccreditationEmail(certificateNum, tpApplication, companyQualifications, unitStandards,skillsProgramList,tpSkillsSetList);
	}
	
	public void approveTtApplicationAndSendCertificateLegacyApproval(TrainingProviderApplication tpApplication,List<CompanyQualifications> companyQualifications,List<CompanyUnitStandard> unitStandards,ArrayList<TrainingProviderSkillsProgramme> skillsProgramList, Tasks task, List<TrainingProviderSkillsSet> tpSkillsSetList) throws Exception {
		tpApplication.setApprovalStatus(ApprovalEnum.Approved);
		tpApplication.setApprovedDate(new Date());
		String certificateNum="";
		
		AssessorModeratorApplicationService amService=new AssessorModeratorApplicationService();
		
		CompanyService companyService=new CompanyService();
	    if (tpApplication.getLegacyProviderAccreditation() != null) {
			tpApplication.setCertificateNumber(tpApplication.getAccreditationNumber());
		} else {
			certificateNum=amService.getAssessorTrainingProviderCertificateNum(null, tpApplication);
			tpApplication.setCertificateNumber(certificateNum);
		}
	    
		Company company=tpApplication.getCompany();
		if(company.getAccreditationNumber() !=null){
			company.setAccreditationNumber(certificateNum);
		}
		company.setCompanyStatus(CompanyStatusEnum.Active);
		companyService.upadeCompanyInfo(company);
		update(tpApplication);
		TasksService.instance().completeTask(task);
		
		//Setting user to be active
		UsersService userService=new UsersService();
		Users user=tpApplication.getUsers();
		user.setActive(true);
		userService.update(user);
		sendProFormaLetterRorFullAccreditationEmailLegacyProvider(certificateNum, tpApplication, companyQualifications, unitStandards,skillsProgramList,tpSkillsSetList);
	}
	
	public void approveNonTtApplicationAndSendCertificate(TrainingProviderApplication tpApplication,List<CompanyQualifications> companyQualifications,List<CompanyUnitStandard> unitStandards,List<TrainingProviderSkillsProgramme> skillsProgramList, Tasks task, List<TrainingProviderSkillsSet> tpSkillsSetList) throws Exception
	{
		tpApplication.setApprovalStatus(ApprovalEnum.Approved);
		tpApplication.setApprovedDate(new Date());
		if(tpApplication.getAccreditationNumber() ==null){
			CompanyService companyService=new CompanyService();
			Company company=tpApplication.getCompany();
			if(company.getAccreditationNumber()==null)
			{
				company.setAccreditationNumber(tpApplication.getAccreditationNumber());
				if (company.getCompanyStatus() == null || (company.getCompanyStatus() != CompanyStatusEnum.Active && company.getCompanyStatus() != CompanyStatusEnum.DeRegistered)) {
					company.setCompanyStatus(CompanyStatusEnum.Active);
				}
				companyService.upadeCompanyInfo(company);
			}
		}
		else{
			CompanyService companyService=new CompanyService();
			Company company=tpApplication.getCompany();
			if(company.getAccreditationNumber()==null)
			{
				if (company.getCompanyStatus() == null || (company.getCompanyStatus() != CompanyStatusEnum.Active && company.getCompanyStatus() != CompanyStatusEnum.DeRegistered)) {
					company.setCompanyStatus(CompanyStatusEnum.Active);
				}
				company.setAccreditationNumber(tpApplication.getAccreditationNumber());
				companyService.upadeCompanyInfo(company);
			}
		}
		update(tpApplication);
		TasksService.instance().completeTask(task);
		//Setting user to be active
		UsersService userService=new UsersService();
		Users user=tpApplication.getUsers();
		user.setActive(true);
		userService.update(user);
		//Sending Approval Email
		List<UnitStandards> unitStandardsList=new ArrayList<>();
		for(CompanyUnitStandard us:unitStandards){
			unitStandardsList.add(us.getUnitStandard());
		}
		List<Qualification> qualificationsList=new ArrayList<>();
		for(CompanyQualifications cq:companyQualifications){
			qualificationsList.add(cq.getQualification());
		}
		List<SkillsSet> skillsSetList=new ArrayList<>();
		for(TrainingProviderSkillsSet ss:tpSkillsSetList){
			skillsSetList.add(ss.getSkillsSet());
		}
		List<SkillsProgram> spList=new ArrayList<>();
		for(TrainingProviderSkillsProgramme sp:skillsProgramList){
			spList.add(sp.getSkillsProgram());
		}
		sendNonMerSetaApprovalEmail(unitStandardsList, qualificationsList,tpApplication.getUsers(), tpApplication, spList, skillsSetList);

	}
	
	public void approveTtApplicationAndSendCertificate(TrainingProviderApplication tpApplication,List<CompanyQualifications> companyQualifications,List<CompanyUnitStandard> unitStandards,ArrayList<TrainingProviderSkillsProgramme> skillsProgramList, Tasks task, List<TrainingProviderSkillsSet> tpSkillsSetList,SDPReAccreditation sdpReAccreditation) throws Exception {
		AssessorModeratorApplicationService amService=new AssessorModeratorApplicationService();
		CompanyService companyService=new CompanyService();
		SDPReAccreditationService sdpReAccreditationService=new SDPReAccreditationService();
		
		tpApplication.setApprovalStatus(ApprovalEnum.Approved);
		tpApplication.setProviderStatus(ProviderStatusService.instance().findByCode("511"));
		tpApplication.setApprovedDate(new Date());
		tpApplication.setAccreditationApplicationTypeEnum(sdpReAccreditation.getPrevioursAccreditationApplicationType());
		updateReSubmissionDates(tpApplication);
		sdpReAccreditation.setApprovalDate(new Date());
		sdpReAccreditation.setStatus(ApprovalEnum.Approved);
		sdpReAccreditationService.update(sdpReAccreditation);
		
		String certificateNum=amService.getAssessorTrainingProviderCertificateNum(null, tpApplication);
		tpApplication.setCertificateNumber(certificateNum);
		Company company=tpApplication.getCompany();
		company.setAccreditationNumber(certificateNum);
		if (company.getCompanyStatus() == null || (company.getCompanyStatus() != CompanyStatusEnum.Active && company.getCompanyStatus() != CompanyStatusEnum.DeRegistered)) {
			company.setCompanyStatus(CompanyStatusEnum.Active);
		}
		companyService.upadeCompanyInfo(company);
		update(tpApplication);
		TasksService.instance().completeTask(task);
		
		// Setting user to be active
		UsersService userService=new UsersService();
		Users user=tpApplication.getUsers();
		user.setActive(true);
		userService.update(user);
		sendProFormaLetterRorFullAccreditationEmail(certificateNum, tpApplication, companyQualifications, unitStandards,skillsProgramList,tpSkillsSetList);

	}
	
	private void updateReSubmissionDates(TrainingProviderApplication tpApplication) throws Exception{
		if (tpApplication != null && tpApplication.getAccreditationApplicationTypeEnum() != null)  {
			if (tpApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL || tpApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL) {
				// Main Acc, Learneirng Programme Acc - ETQA Dec. Date + 5 years
				Date newStartDate = (tpApplication.getApprovedDate() != null ? GenericUtility.getStartOfDay(tpApplication.getApprovedDate()) : GenericUtility.getStartOfDay(getSynchronizedDate()));
				Date newExpiryDate = GenericUtility.getStartOfDay(GenericUtility.addYearsToDate(newStartDate, 5));
				tpApplication.setStartDate(newStartDate);
				tpApplication.setExpiriyDate(newExpiryDate);
			}
		}
	}
	
	public void avialabilityOfFacilitorAssModValidation(Users facilitatorAssessor) throws Exception {
		if(facilitatorAssessor != null && facilitatorAssessor.getId() !=null) {
			//Checking if user is an Assessor or Moderator
		    AssessorModeratorApplicationService assessorModeratorApplicationService=new AssessorModeratorApplicationService();
		    // version one
//			List<AssessorModeratorApplication> amApplicationList=assessorModeratorApplicationService.findByApprovedUserApplications(facilitatorAssessor);
		    // version two
		    List<AssessorModeratorApplication> amApplicationList=assessorModeratorApplicationService.findApplicationByUserAndType(facilitatorAssessor, AssessorModeratorTypeEnum.MerSETA_AM);
			if(amApplicationList==null || amApplicationList.size()<=0) {
				facilitatorAssessor=null;
				throw new Exception("The select user is not an accredited  Assessor/Moderator");
			}
			assessorModeratorApplicationService=null;
		} else {
			facilitatorAssessor=null;
			throw new Exception("Only accredited Assessor or Moderator can be added");
		}
		
		
	}
	
	public void checkIfIsAssOrMod(Users facilitatorAssessor) throws Exception {
		boolean isApprovedAssessor=false;
		boolean isApprovedMod=false;
		if(facilitatorAssessor != null && facilitatorAssessor.getId() !=null)
		{
		    AssessorModeratorApplicationService assessorModeratorApplicationService=new AssessorModeratorApplicationService();
//			List<AssessorModeratorApplication> amApplicationList=assessorModeratorApplicationService.findByApprovedUserApplications(facilitatorAssessor);
		    List<AssessorModeratorApplication> amApplicationList=assessorModeratorApplicationService.findApplicationByUserAndType(facilitatorAssessor, AssessorModeratorTypeEnum.MerSETA_AM);
			for(AssessorModeratorApplication am:amApplicationList)
			{
				if((am.getApplicationType() ==AssessorModeratorAppTypeEnum.AssessorExtensionOfScope||
				am.getApplicationType()==AssessorModeratorAppTypeEnum.AssessorReRegistration||
				am.getApplicationType()==AssessorModeratorAppTypeEnum.NewAssessorRegistration )&& facilitatorAssessor.getAssessorModType()==AssessorModType.Assessor)
				{
					if(am.getStatus()==ApprovalEnum.Approved)
					{
						isApprovedAssessor=true;
						break;
					}
				}
				else if((am.getApplicationType() ==AssessorModeratorAppTypeEnum.ModeratorExtensionOfScope||
				am.getApplicationType()==AssessorModeratorAppTypeEnum.ModeratorReRegistration||
				am.getApplicationType()==AssessorModeratorAppTypeEnum.NewModeratorRegistration )&& facilitatorAssessor.getAssessorModType()==AssessorModType.Moderator)
				{

					if(am.getStatus()==ApprovalEnum.Approved)
					{
						isApprovedMod=true;
						break;
					}
				}
				else
				{

					if(am.getApplicationType() ==AssessorModeratorAppTypeEnum.AssessorExtensionOfScope||
					am.getApplicationType()==AssessorModeratorAppTypeEnum.AssessorReRegistration||
					am.getApplicationType()==AssessorModeratorAppTypeEnum.NewAssessorRegistration )
					{
						if(am.getStatus()==ApprovalEnum.Approved)
						{
							isApprovedAssessor=true;
						}
					}
					else if(am.getApplicationType() ==AssessorModeratorAppTypeEnum.ModeratorExtensionOfScope||
					am.getApplicationType()==AssessorModeratorAppTypeEnum.ModeratorReRegistration||
					am.getApplicationType()==AssessorModeratorAppTypeEnum.NewModeratorRegistration )
					{

						if(am.getStatus()==ApprovalEnum.Approved)
						{
							isApprovedMod=true;
						}
					}
				}
			}
			assessorModeratorApplicationService=null;
		}
		
		if(facilitatorAssessor.getAssessorModType()==AssessorModType.Assessor && !isApprovedAssessor)
		{
			String mssg="The select user is not an accredited "+facilitatorAssessor.getAssessorModType().getFriendlyName();
			facilitatorAssessor.setAssessorModType(null);
			throw new Exception(mssg);
		}
		else if(facilitatorAssessor.getAssessorModType()==AssessorModType.Moderator && !isApprovedMod)
		{
			String mssg="The select user is not an accredited "+facilitatorAssessor.getAssessorModType().getFriendlyName();
			facilitatorAssessor.setAssessorModType(null);
			throw new Exception(mssg);
		}
		else if(facilitatorAssessor.getAssessorModType()==AssessorModType.AssessorAndModerator && (!isApprovedMod ||  !isApprovedAssessor))
		{
			String mssg="The select user is not an accredited "+facilitatorAssessor.getAssessorModType().getFriendlyName();
			facilitatorAssessor.setAssessorModType(null);
			throw new Exception(mssg);
		}
		
	}
	
	/**TO BE UPDATED*/
	public void sendExtensionNotificationOfPrimaryFocusAsAnAccreditedProviderEmail( String certificateNumber,TrainingProviderApplication tpApplication,List<CompanyQualifications> companyQualifications, 
			List<CompanyUnitStandard> unitStandards,ArrayList<TrainingProviderSkillsProgramme> newSkillsProgramList,ArrayList<TrainingProviderSkillsSet> skillsSetList) throws Exception {
		
			String barcode="";
			if(tpApplication.getUsers().getRsaIDNumber() !=null){barcode=tpApplication.getUsers().getRsaIDNumber();}
			else{barcode=tpApplication.getUsers().getPassportNumber();}
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			AttachmentBean beanAttachment=new AttachmentBean();
			ArrayList<AttachmentBean> attachmentBeans=new ArrayList<>();
			
			//ETQ-TP-017-Pro-formaLetterforFullAccreditation
			JasperService.addLogo(params);
			params.put("company_id",tpApplication.getCompany().getId() );
			params.put("user_id",tpApplication.getUsers().getId() );
			params.put("accreditation_number",tpApplication.getCertificateNumber());
			params.put("date_of_rc", GenericUtility.sdf7.format(new Date()));
			params.put("call_centre",HAJConstants.MERSETA_CALL_CENTRE);
			JasperService.addImage(params, "certificate_signature.png", "certificate_signature");
			byte[] etqA17Bites = JasperService.instance().convertJasperReportToByte("ETQ-TP-017-Pro-formaLetterforFullAccreditation.jasper", params);
			beanAttachment.setExt("pdf");
			beanAttachment.setFile(etqA17Bites);
			beanAttachment.setFilename("Pro-forma_Letter_For_Full_Accreditation.pdf");
			attachmentBeans.add(beanAttachment);
			//Qualification/Unit Standards Attachment
			
			beanAttachment=new AttachmentBean();
			beanAttachment.setExt("pdf");
			beanAttachment.setFile(getTPETQTP011Bytes(tpApplication, companyQualifications, unitStandards,newSkillsProgramList,skillsSetList));
			beanAttachment.setFilename("Statement_Of_Qualifications_And_Or_UnitStandards.pdf");
			attachmentBeans.add(beanAttachment);
			
	
			String subject = "TRAINING PROVIDER APPLICATION";
			String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " +tpApplication.getUsers().getFirstName()+ " " + tpApplication.getUsers().getLastName()+ ",</p>"
	
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					+ "It is our pleasure to inform you that the merSETA "
					+ "Review Committee approved "+tpApplication.getCompany().getCompanyName()+"'s application "
					+ "for full accreditation as a provider on "+GenericUtility.sdf7.format(tpApplication.getReviewCommitteeMeeting().getFromDateTime())+" "
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
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Senior Manager: Quality Assurance & Partnerships</p>";
	
			//GenericUtility.sendMailWithAttachement(am.getUser().getEmail(), subject, mssg, bites, "Assessor_Certificate.pdf", "pdf", null);
			GenericUtility.sendMailWithAttachementTempWithLog(tpApplication.getUsers().getEmail(), subject, mssg, attachmentBeans, null,tpApplication.getClass().getName(),tpApplication.getId());
	
	}
	

	
/**
 * Download Pro-forma Letter for Full Accreditation 
 * */	
public void downloadETQTP017(TrainingProviderApplication tpApplication) throws Exception {
		
	/*if(tpApplication.getCertificateNumber()==null)
	{
		throw new Exception("Pro-forma Letter for Full Accreditation is not available for this application");
	}*/
	Map<String, Object> params = new HashMap<String, Object>();
	//ETQ-TP-017-Pro-formaLetterforFullAccreditation
	JasperService.addLogo(params);
	String accrNumber=tpApplication.getCertificateNumber();
	if(accrNumber ==null || accrNumber.isEmpty() || accrNumber.equals(""))
	{
		accrNumber=tpApplication.getAccreditationNumber();
	}
	params.put("company_id",tpApplication.getCompany().getId() );
	params.put("user_id",tpApplication.getUsers().getId() );
	params.put("accreditation_number",accrNumber);
	params.put("date_of_rc", GenericUtility.sdf7.format(tpApplication.getStartDate()));
	params.put("call_centre",HAJConstants.MERSETA_CALL_CENTRE);
	JasperService.addImage(params, "certificate_signature.png", "certificate_signature");
	String fileName=tpApplication.getCompany().getLevyNumber()+"_Pro_forma_Letter_For_Full_Accreditation.pdf";
	JasperService.instance().createReportFromDBtoServletOutputStream("ETQ-TP-017-Pro-formaLetterforFullAccreditation.jasper", params,fileName);
		
		
}

/**
 * Download Provider Certificate 
 * */	
public void downloadProviderCertificate(TrainingProviderApplication tpApplication) throws Exception {
		
	/*if(tpApplication.getCertificateNumber()==null)
	{
		throw new Exception(" Provider Certificate is not available for this application");
	}*/
	String barcode="";
	if(tpApplication.getUsers().getRsaIDNumber() !=null){barcode=tpApplication.getUsers().getRsaIDNumber();}
	else{barcode=tpApplication.getUsers().getPassportNumber();}
	Map<String, Object> params = new HashMap<String, Object>();
	//ETQ-TP-017-Pro-formaLetterforFullAccreditation
	params = new HashMap<String, Object>();
	params.put("certificate_number", tpApplication.getCertificateNumber());
	params.put("date_of_expiry", GenericUtility.sdf5.format(tpApplication.getExpiriyDate()));
	params.put("date_of_registration", GenericUtility.sdf5.format(tpApplication.getStartDate()));
	params.put("barcode",barcode);
	params.put("company_id",tpApplication.getCompany().getId() );
	JasperService.addImage(params, "top_bottom_boder.png", "top_bottom_border");
	JasperService.addImage(params, "left_right_boder.png", "left_right_border");
	JasperService.addImage(params, "certificate_signature.png", "certificate_signature");
	JasperService.addImage(params, "corner_image.png", "corner_image");
	JasperService.addImage(params, "logo2.png", "logo");
	String fileName=tpApplication.getCompany().getLevyNumber()+"_Provider_Certificate.pdf";
	JasperService.instance().createReportFromDBtoServletOutputStream("ProviderCertificate.jasper", params,fileName);
		
}

	/**
	 * Download Statement Of Qualifications
	 * */
	public void downloadETQTP011(TrainingProviderApplication tpApplication) throws Exception {
		
		String barcode="";
		if(tpApplication.getUsers().getRsaIDNumber() !=null){barcode=tpApplication.getUsers().getRsaIDNumber();}
		else{barcode=tpApplication.getUsers().getPassportNumber();}
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		JasperService.addLogo(params);
		String accreditation_number = "";
		if(tpApplication.getAccreditationNumber() != null){
			accreditation_number = tpApplication.getAccreditationNumber();
		}else if(tpApplication.getCertificateNumber() != null){
			accreditation_number = tpApplication.getCertificateNumber();
		}else if(tpApplication.getCompany().getAccreditationNumber() != null) {
			accreditation_number = tpApplication.getCompany().getAccreditationNumber();
		}
		params.put("accreditation_number",accreditation_number);
		params.put("tpApplication",tpApplication);
		params.put("company",tpApplication.getCompany());
		params.put("company_id",tpApplication.getCompany().getId() );
		params.put("user_id",tpApplication.getUsers().getId() );
		params.put("registration_number",tpApplication.getCertificateNumber());
		params.put("status",tpApplication.getApprovalStatus().getFriendlyName());
		params.put("barcode",barcode);
		params.put("date", GenericUtility.sdf7.format(new Date()));
		params.put("startdate", GenericUtility.sdf7.format(tpApplication.getStartDate()));
		params.put("enddate", GenericUtility.sdf7.format(tpApplication.getExpiriyDate()));
		params.put("approvalDate", GenericUtility.sdf7.format(tpApplication.getApprovedDate()));
		
		
		//List<CompanyQualifications> approvedQompanyQualifications=CompanyQualificationsService.instance().findByTargetClassAndTargetKeyAndAccept(tpApplication.getClass().getName(), tpApplication.getId(),true);
		List<CompanyUnitStandard> approvedUnitStandards=CompanyUnitStandardService.instance().findByTargetClassAndTargetKeyAndAccept(tpApplication.getClass().getName(), tpApplication.getId(),true);
		//List<TrainingProviderSkillsProgramme> approvedNewSkillsProgramList=TrainingProviderSkillsProgrammeService.instance().findByTargetClassAndTargetKeyAndAccept(tpApplication.getClass().getName(), tpApplication.getId(),true);
		//List<TrainingProviderSkillsSet> approvedSkillsSetList=TrainingProviderSkillsSetService.instance().findByTargetClassAndTargetKeyAndAccept(tpApplication.getClass().getName(), tpApplication.getId(),true);
		
		List<CompanyQualifications> approvedQompanyQualifications = CompanyQualificationsService.instance().findByTargetClassAndTargetKey(tpApplication.getClass().getName(), tpApplication.getId());
		//unitStandards = companyUnitStandardService.findByTargetClassAndTargetKey(tpApplication.getClass().getName(), tpApplication.getId());
		List<TrainingProviderSkillsProgramme> approvedNewSkillsProgramList = TrainingProviderSkillsProgrammeService.instance().findByTrainingProvider(tpApplication.getId());
		List<TrainingProviderSkillsSet> approvedSkillsSetList=TrainingProviderSkillsSetService.instance().findByTrainingProvider(tpApplication.getId());
		
		ArrayList<QualificationUnitStandardBean> qualificationUnitStandardList=new ArrayList<>();
		for(CompanyQualifications qual:approvedQompanyQualifications){
			if(qual.getAccept() !=null && qual.getAccept()) {
				qualificationUnitStandardList.add(new QualificationUnitStandardBean(String.valueOf(qual.getQualification().getCode()), qual.getQualification().getDescription(), GenericUtility.sdf7.format(tpApplication.getApprovedDate())));
			}	
		}
		 
		 for(CompanyUnitStandard us:approvedUnitStandards){
			 qualificationUnitStandardList.add(new QualificationUnitStandardBean(String.valueOf(us.getUnitStandard().getCode()), us.getUnitStandard().getTitle(), GenericUtility.sdf7.format(tpApplication.getApprovedDate())));
		 }
		 
		 for(TrainingProviderSkillsProgramme sp:approvedNewSkillsProgramList){
			 if(sp.getAccept() != null && sp.getAccept()) {
				 qualificationUnitStandardList.add(new QualificationUnitStandardBean(String.valueOf(sp.getSkillsProgram().getProgrammeID()), sp.getSkillsProgram().getDescription(), GenericUtility.sdf7.format(tpApplication.getApprovedDate())));
			 }
		}
		 
		 for(TrainingProviderSkillsSet ss:approvedSkillsSetList){
			 if(ss.getAccept() != null && ss.getAccept()) {
				 qualificationUnitStandardList.add(new QualificationUnitStandardBean(String.valueOf(ss.getSkillsSet().getProgrammeID()), ss.getSkillsSet().getDescription(), GenericUtility.sdf7.format(tpApplication.getApprovedDate())));
			 }
		}
		params.put("QualificationUnitStandardBeanDataSource", new JRBeanCollectionDataSource(qualificationUnitStandardList));
		String fileName=tpApplication.getCompany().getLevyNumber()+"_Statement_Of_Qualifications.pdf";
		JasperService.instance().createReportFromDBtoServletOutputStream("ETQ-TP-011-TPStatementOfQualificationsandorUnitStandards.jasper", params,fileName);
		
	}

	/**
	 * Download Accreditation Approval Application Form
	 * */
	public void downloadETQFM002A(TrainingProviderApplication tpApplication) throws Exception {
		/*if(tpApplication.getCertificateNumber()==null)
		{
			throw new Exception("Statement Of Qualifications is not available for this application");
		}*/
		List<CompanyQualifications> approvedQompanyQualifications=CompanyQualificationsService.instance().findByTargetClassAndTargetKeyAndAccept(tpApplication.getClass().getName(), tpApplication.getId(),true);
		List<CompanyUnitStandard> approvedUnitStandards=CompanyUnitStandardService.instance().findByTargetClassAndTargetKeyAndAccept(tpApplication.getClass().getName(), tpApplication.getId(),true);
		List<UnitStandards> unitStandardsList=new ArrayList<>(); 
		List<Qualification> qualificationsList=new ArrayList<>();
		List<SkillsProgram> skillsProgramList=new ArrayList<>();
		List<TrainingProviderSkillsProgramme> approvedNewSkillsProgramList=TrainingProviderSkillsProgrammeService.instance().findByTargetClassAndTargetKeyAndAccept(tpApplication.getClass().getName(), tpApplication.getId(),true);
		
		for(CompanyQualifications cq:approvedQompanyQualifications){
			qualificationsList.add(cq.getQualification());
		}
		for(CompanyUnitStandard cu:approvedUnitStandards){
			unitStandardsList.add(cu.getUnitStandard());
		}
		for(TrainingProviderSkillsProgramme tpSp:approvedNewSkillsProgramList){
			skillsProgramList.add(tpSp.getSkillsProgram());
		}
		//List<TrainingProviderSkillsSet> approvedSkillsSetList=TrainingProviderSkillsSetService.instance().findByTargetClassAndTargetKeyAndAccept(tpApplication.getClass().getName(), tpApplication.getId(),true);
		
		Map<String, Object> params = new HashMap<String, Object>();
		CompanyService companyService=new CompanyService();
		tpApplication=findByKey(tpApplication.getId());
		tpApplication.setCompany(companyService.findByKey(tpApplication.getCompany().getId()));
		JasperService.addLogo(params);
		CompanyUsersService companyUsersService=new CompanyUsersService();
		companyUsersService.findByCompanyType(tpApplication.getCompany().getId(),ConfigDocProcessEnum.TP);
		params.put("title", getTitle(tpApplication.getAccreditationApplicationTypeEnum()).toUpperCase());
		params.put("trainingProviderApplication",tpApplication);
		params.put("contactPersonDataSource",new  JRBeanCollectionDataSource(companyUsersService.findByCompanyType(tpApplication.getCompany().getId(),ConfigDocProcessEnum.TP)));
		params.put("qualificationDataSource",new JRBeanCollectionDataSource(qualificationsList));
		params.put("unitStandardDataSource",new JRBeanCollectionDataSource(unitStandardsList));
		params.put("skillsProgramDataSource",new JRBeanCollectionDataSource(skillsProgramList));
		String fileName=tpApplication.getCompany().getLevyNumber()+"_Accreditation_Approval_Application_Form.pdf";
		JasperService.instance().createReportFromDBtoServletOutputStream("ETQ-FM-002AccreditationApprovalApplicationForm.jasper", params,fileName);
		
		
	}
	
	public void sendProFormaLetterRorFullAccreditationEmailLegacyProvider( String certificateNumber,TrainingProviderApplication tpApplication,List<CompanyQualifications> companyQualifications, List<CompanyUnitStandard> unitStandards,List<TrainingProviderSkillsProgramme> newSkillsProgramList, List<TrainingProviderSkillsSet> skillsSetList) throws Exception {	
		String barcode="";
		if(tpApplication.getUsers().getRsaIDNumber() !=null){
			barcode=tpApplication.getUsers().getRsaIDNumber();
		} else{
			barcode=tpApplication.getUsers().getPassportNumber();
		}
		
		//Updating TP info
		tpApplication.setApprovedDate(new Date());
		if(tpApplication.getExpiriyDate()==null){tpApplication.setExpiriyDate(GenericUtility.addYearsToDate(new Date(), 5));}
		if(tpApplication.getStartDate()==null){tpApplication.setStartDate(new Date());}
		if(tpApplication.getEtqa()==null){
			EtqaService etqaService=new EtqaService();
			Etqa etqa=etqaService.findByCode(HAJConstants.HOSTING_MERSETA_ETQA);
			tpApplication.setEtqa(etqa);
		}
		if(tpApplication.getAccreditationStatus() == null ) {
			AccreditationStatusService accreditationStatusService=new AccreditationStatusService();
			AccreditationStatus status = accreditationStatusService.findByKey(1L);//Active
			if(status !=null){
				tpApplication.setAccreditationStatus(status);
			}
		}
		if(tpApplication.getAccreditationNumber()==null){tpApplication.setAccreditationNumber(certificateNumber);}
		update(tpApplication);
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		AttachmentBean beanAttachment=new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans=new ArrayList<>();
		
		//ETQ-TP-017-Pro-formaLetterforFullAccreditation
		JasperService.addLogo(params);
		String accNumber=tpApplication.getCertificateNumber();
		if(accNumber==null || accNumber.isEmpty() || accNumber.equals("")){
			accNumber=tpApplication.getAccreditationNumber();
		}
		params.put("company_id",tpApplication.getCompany().getId() );
		params.put("user_id",tpApplication.getUsers().getId() );
		params.put("accreditation_number",accNumber);
		params.put("date_of_rc", GenericUtility.sdf7.format(new Date()));
		params.put("call_centre",HAJConstants.MERSETA_CALL_CENTRE);
		
		List<CompanyQualifications> approvedQompanyQualifications=new ArrayList<>();
		List<CompanyUnitStandard> approvedUnitStandards=new ArrayList<>();
		ArrayList<TrainingProviderSkillsProgramme> approvedNewSkillsProgramList=new ArrayList<>();;
		List<TrainingProviderSkillsSet> approvedSkillsSetList=new ArrayList<>();
		
		for(CompanyQualifications qual:companyQualifications){
			if(qual.getAccept() !=null && qual.getAccept()){approvedQompanyQualifications.add(qual);}
		}
		for(CompanyUnitStandard qual:unitStandards){
			if(qual.getAccept() !=null &&  qual.getAccept()){approvedUnitStandards.add(qual);}
		}
		for(TrainingProviderSkillsProgramme qual:newSkillsProgramList){
			if(qual.getAccept() !=null &&  qual.getAccept()){approvedNewSkillsProgramList.add(qual);}
		}
		for(TrainingProviderSkillsSet qual:skillsSetList){
			if(qual.getAccept() !=null &&  qual.getAccept()){approvedSkillsSetList.add(qual);
			}
		}
		
		List<Qualification> qualificationsList=new ArrayList<>();
		for(CompanyQualifications cq :approvedQompanyQualifications){
			qualificationsList.add(cq.getQualification());
		}
		
		List<UnitStandards> unitStandardsList=new ArrayList<>();
		for(CompanyUnitStandard cus :approvedUnitStandards){
			unitStandardsList.add(cus.getUnitStandard());
		}
		
		List<SkillsProgram> skillsProgramList=new ArrayList<>();
		for(TrainingProviderSkillsProgramme sp :approvedNewSkillsProgramList){
			skillsProgramList.add(sp.getSkillsProgram());
		}
		
		List<SkillsSet> ssList =new ArrayList<>();
		for(TrainingProviderSkillsSet ss :skillsSetList){
			ssList.add(ss.getSkillsSet());
		}
		
		byte[] bites2 = createETQFM002AccreditationApprovalApplicationFormBytes(unitStandardsList, qualificationsList, tpApplication.getCompany(), tpApplication.getUsers(), tpApplication, skillsProgramList, ssList,getTitle(tpApplication.getAccreditationApplicationTypeEnum()));
		beanAttachment=new AttachmentBean();
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites2);
		beanAttachment.setFilename("Accreditation_Approval_Application_Form.pdf");
		attachmentBeans.add(beanAttachment);
		
		String subject = "SKILLS DEVELOPMENT PROVIDER REGISTRATION OUTCOME";
		String mssg = "";
		if(tpApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL){
			mssg = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #FIRST_NAME# #LAST_NAME# </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Please be advised that your application to register on the NSDMS as primary skills development provider (accreditation number: #ACCREDITATION_NUMBER#) has been approved. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> For any further information, please contact your Regional Office or Head Office. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, <br/> merSETA ETQA Administrator </p>";
		} else {
			mssg = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #FIRST_NAME# #LAST_NAME# </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Please be advised that your application to register on the NSDMS as learning programmes skills development provider (accreditation number: #ACCREDITATION_NUMBER#) has been approved. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> For any further information, please contact your Regional Office or Head Office. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, <br/> merSETA ETQA Administrator </p>";
		}
		mssg = mssg.replace("#FIRST_NAME#", tpApplication.getUsers().getFirstName().trim());
		mssg = mssg.replace("#LAST_NAME#", tpApplication.getUsers().getLastName().trim());
		mssg = mssg.replace("#ACCREDITATION_NUMBER#", tpApplication.getAccreditationNumber().trim());
		GenericUtility.sendMailWithAttachementTempWithLog(tpApplication.getUsers().getEmail(), subject, mssg, attachmentBeans, null,tpApplication.getClass().getName(),tpApplication.getId());
	}

	public void sendProFormaLetterRorFullAccreditationEmail( String certificateNumber,TrainingProviderApplication tpApplication,List<CompanyQualifications> companyQualifications, List<CompanyUnitStandard> unitStandards,List<TrainingProviderSkillsProgramme> newSkillsProgramList, List<TrainingProviderSkillsSet> skillsSetList) throws Exception {
		
		String barcode="";
		if(tpApplication.getUsers().getRsaIDNumber() !=null){
			barcode=tpApplication.getUsers().getRsaIDNumber();
		}
		else{
			barcode=tpApplication.getUsers().getPassportNumber();
		}
		
		//Updating TP info
		tpApplication.setApprovedDate(new Date());
		if(tpApplication.getExpiriyDate()==null){tpApplication.setExpiriyDate(GenericUtility.addYearsToDate(new Date(), 5));}
		if(tpApplication.getStartDate()==null){tpApplication.setStartDate(new Date());}
		if(tpApplication.getEtqa()==null){
			EtqaService etqaService=new EtqaService();
			Etqa etqa=etqaService.findByCode(HAJConstants.HOSTING_MERSETA_ETQA);
			tpApplication.setEtqa(etqa);
		}
		if(tpApplication.getAccreditationStatus()==null){
			AccreditationStatusService accreditationStatusService=new AccreditationStatusService();
			AccreditationStatus status=accreditationStatusService.findByKey(1L);//Active
			if(status !=null){
				tpApplication.setAccreditationStatus(status);
			}
		}
		if(tpApplication.getAccreditationNumber()==null){tpApplication.setAccreditationNumber(certificateNumber);}
		update(tpApplication);
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		AttachmentBean beanAttachment=new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans=new ArrayList<>();
		
		//ETQ-TP-017-Pro-formaLetterforFullAccreditation
		JasperService.addLogo(params);
		String accNumber=tpApplication.getCertificateNumber();
		if(accNumber==null || accNumber.isEmpty() || accNumber.equals("")){
			accNumber=tpApplication.getAccreditationNumber();
		}
		params.put("company_id",tpApplication.getCompany().getId() );
		params.put("user_id",tpApplication.getUsers().getId() );
		params.put("accreditation_number",accNumber);
		params.put("date_of_rc", GenericUtility.sdf7.format(new Date()));
		params.put("call_centre",HAJConstants.MERSETA_CALL_CENTRE);
		JasperService.addImage(params, "certificate_signature.png", "certificate_signature");
		byte[] etqA17Bites = JasperService.instance().convertJasperReportToByte("ETQ-TP-017-Pro-formaLetterforFullAccreditation.jasper", params);
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(etqA17Bites);
		beanAttachment.setFilename("Pro-forma_Letter_For_Full_Accreditation.pdf");
		attachmentBeans.add(beanAttachment);
		
		List<CompanyQualifications> approvedQompanyQualifications=new ArrayList<>();
		List<CompanyUnitStandard> approvedUnitStandards=new ArrayList<>();
		ArrayList<TrainingProviderSkillsProgramme> approvedNewSkillsProgramList=new ArrayList<>();;
		List<TrainingProviderSkillsSet> approvedSkillsSetList=new ArrayList<>();
		
		for(CompanyQualifications qual:companyQualifications){
			if(qual.getAccept() !=null && qual.getAccept()){approvedQompanyQualifications.add(qual);}
		}
		for(CompanyUnitStandard qual:unitStandards){
			if(qual.getAccept() !=null &&  qual.getAccept()){approvedUnitStandards.add(qual);}
		}
		for(TrainingProviderSkillsProgramme qual:newSkillsProgramList){
			if(qual.getAccept() !=null &&  qual.getAccept()){approvedNewSkillsProgramList.add(qual);}
		}
		for(TrainingProviderSkillsSet qual:skillsSetList){
			if(qual.getAccept() !=null &&  qual.getAccept()){approvedSkillsSetList.add(qual);
			}
		}
		//Qualification/Unit Standards Attachment
		if(tpApplication.getAccreditationApplicationTypeEnum() ==AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL)
		{
			beanAttachment=new AttachmentBean();
			beanAttachment.setExt("pdf");
			beanAttachment.setFile(getETQTP009LearningProgrammeApprovalBytes(tpApplication));
			beanAttachment.setFilename("ETQ-TP-009-ProgrammeApprovalReporting.pdf");
			attachmentBeans.add(beanAttachment);
		}
		else
		{
			beanAttachment=new AttachmentBean();
			beanAttachment.setExt("pdf");
			beanAttachment.setFile(getTPETQTP011Bytes(tpApplication, approvedQompanyQualifications, approvedUnitStandards,approvedNewSkillsProgramList,approvedSkillsSetList));
			beanAttachment.setFilename("Statement_Of_Qualifications_And_Or_UnitStandards.pdf");
			attachmentBeans.add(beanAttachment);
		}
		
		//Certificate Attachment
		/**We don't issue certificate for Learning Programme Approval */
		if(tpApplication.getAccreditationApplicationTypeEnum() ==AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL)
		{
			if(tpApplication.getAccreditationNumber()==null){
				tpApplication.setAccreditationNumber(certificateNumber);
			}
			if(tpApplication.getCertificateNumber()==null){tpApplication.setCertificateNumber(certificateNumber);}
			params = new HashMap<String, Object>();
			params.put("certificate_number", certificateNumber);
			params.put("date_of_expiry", GenericUtility.sdf5.format(GenericUtility.addYearsToDate(new Date(), 5)));
			params.put("date_of_registration", GenericUtility.sdf5.format(new Date()));
			params.put("barcode",barcode);
			params.put("company_id",tpApplication.getCompany().getId() );
			JasperService.addImage(params, "top_bottom_boder.png", "top_bottom_border");
			JasperService.addImage(params, "left_right_boder.png", "left_right_border");
			JasperService.addImage(params, "certificate_signature.png", "certificate_signature");
			JasperService.addImage(params, "corner_image.png", "corner_image");
			JasperService.addImage(params, "logo2.png", "logo");
			
			byte[] bites = JasperService.instance().convertJasperReportToByte("ProviderCertificate.jasper", params);
			beanAttachment=new AttachmentBean();
			beanAttachment.setExt("pdf");
			beanAttachment.setFile(bites);
			beanAttachment.setFilename("Provider_Certificate.pdf");
			attachmentBeans.add(beanAttachment);
		}
		
		List<Qualification> qualificationsList=new ArrayList<>();
		for(CompanyQualifications cq :approvedQompanyQualifications){
			qualificationsList.add(cq.getQualification());
		}
		
		List<UnitStandards> unitStandardsList=new ArrayList<>();
		for(CompanyUnitStandard cus :approvedUnitStandards){
			unitStandardsList.add(cus.getUnitStandard());
		}
		
		List<SkillsProgram> skillsProgramList=new ArrayList<>();
		for(TrainingProviderSkillsProgramme sp :approvedNewSkillsProgramList){
			skillsProgramList.add(sp.getSkillsProgram());
		}
		
		List<SkillsSet> ssList =new ArrayList<>();
		for(TrainingProviderSkillsSet ss :skillsSetList){
			ssList.add(ss.getSkillsSet());
		}
		
		byte[] bites2 = createETQFM002AccreditationApprovalApplicationFormBytes(unitStandardsList, qualificationsList, tpApplication.getCompany(), tpApplication.getUsers(), tpApplication, skillsProgramList, ssList,getTitle(tpApplication.getAccreditationApplicationTypeEnum()));
		beanAttachment=new AttachmentBean();
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites2);
		beanAttachment.setFilename("Accreditation_Approval_Application_Form.pdf");
		attachmentBeans.add(beanAttachment);
		
		String subject = "SKILLS DEVELOPMENT PROVIDER REGISTRATION OUTCOME";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " +tpApplication.getUsers().getFirstName()+ " " + tpApplication.getUsers().getLastName()+ ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "It is our pleasure to inform you that the merSETA "
				+ "Review Committee approved "+tpApplication.getCompany().getCompanyName()+"'s application "
				+ "for full accreditation as a provider on "+GenericUtility.sdf7.format(new Date())+" for "
				+ "the qualification/s and/or trade/s and/or unit standards "
				+ "listed on your statement of qualifications and unit standards."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please note that if your accreditation was for an area of "
				+ "specialisation within a qualification, this accreditation "
				+ "is for that area of specialisation only. The provider is "
				+ "therefore required to deliver strictly according to the "
				+ "registered NQF qualification with specific reference to "
				+ "the 'Qualification rules'."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "merSETA will continue to monitor the standard of "
				+ "your training through regular auditing of the "
				+ "implementation of your quality management system. "
				+ "You will be contacted in this regard."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Congratulations on your achievement and thank you for your high level of commitment and professionalism."
				+ "</p>"
                
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Senior Manager: Quality Assurance & Partnerships</p>";

		GenericUtility.sendMailWithAttachementTempWithLog(tpApplication.getUsers().getEmail(), subject, mssg, attachmentBeans, null,tpApplication.getClass().getName(),tpApplication.getId());
}


public byte[] getTPETQTP011Bytes(TrainingProviderApplication tpApplication,List<CompanyQualifications> companyQualifications, List<CompanyUnitStandard> unitStandards, ArrayList<TrainingProviderSkillsProgramme> newSkillsProgramList, List<TrainingProviderSkillsSet> skillsSetList) throws Exception
{
	String barcode="";
	if(tpApplication.getUsers().getRsaIDNumber() !=null){barcode=tpApplication.getUsers().getRsaIDNumber();}
	else{barcode=tpApplication.getUsers().getPassportNumber();}
	
	Map<String, Object> params = new HashMap<String, Object>();
	String regNum=tpApplication.getCertificateNumber();
	if(regNum==null || regNum.equals("") || regNum.isEmpty()){
		regNum=tpApplication.getAccreditationNumber();
	}
	JasperService.addLogo(params);
	String accreditation_number = "";
	if(tpApplication.getAccreditationNumber() != null){
		accreditation_number = tpApplication.getAccreditationNumber();
	}else if(tpApplication.getCertificateNumber() != null){
		accreditation_number = tpApplication.getCertificateNumber();
	}else if(tpApplication.getCompany().getAccreditationNumber() != null) {
		accreditation_number = tpApplication.getCompany().getAccreditationNumber();
	}
	params.put("accreditation_number",accreditation_number);
	params.put("tpApplication",tpApplication);
	params.put("company",tpApplication.getCompany());
	params.put("company_id",tpApplication.getCompany().getId() );
	params.put("user_id",tpApplication.getUsers().getId() );
	params.put("registration_number",regNum);
	params.put("status",tpApplication.getApprovalStatus().getFriendlyName());
	params.put("barcode",barcode);
	params.put("date", GenericUtility.sdf7.format(new Date()));
	params.put("approvalDate", GenericUtility.sdf7.format(tpApplication.getApprovedDate()));
	
	ArrayList<QualificationUnitStandardBean> qualificationUnitStandardList=new ArrayList<>();
	for(CompanyQualifications qual:companyQualifications){
		 qualificationUnitStandardList.add(new QualificationUnitStandardBean(String.valueOf(qual.getQualification().getCode()), qual.getQualification().getDescription(), GenericUtility.sdf7.format(new Date())));
	 }
	 
	 for(CompanyUnitStandard us:unitStandards){
		 qualificationUnitStandardList.add(new QualificationUnitStandardBean(String.valueOf(us.getUnitStandard().getCode()), us.getUnitStandard().getTitle(), GenericUtility.sdf7.format(new Date())));
	 }
	 
	 for(TrainingProviderSkillsProgramme sp:newSkillsProgramList){
		 qualificationUnitStandardList.add(new QualificationUnitStandardBean(String.valueOf(sp.getSkillsProgram().getProgrammeID()), sp.getSkillsProgram().getDescription(), GenericUtility.sdf7.format(new Date())));
	 }
	 
	 for(TrainingProviderSkillsSet ss:skillsSetList){
		 qualificationUnitStandardList.add(new QualificationUnitStandardBean(String.valueOf(ss.getSkillsSet().getProgrammeID()), ss.getSkillsSet().getDescription(), GenericUtility.sdf7.format(new Date())));
	 }
	 
	 
	params.put("QualificationUnitStandardBeanDataSource", new JRBeanCollectionDataSource(qualificationUnitStandardList));
	byte[] bites = JasperService.instance().convertJasperReportToByte("ETQ-TP-011-TPStatementOfQualificationsandorUnitStandards.jasper", params);
	
	
	return bites;

}
	
	public void sendAssessorCertificate( String certificateNumber,TrainingProviderApplication tpApplication,List<CompanyQualifications> companyQualifications, List<CompanyUnitStandard> unitStandards) throws Exception {
		
		String barcode="";
		if(tpApplication.getUsers().getRsaIDNumber() !=null)
		{
			barcode=tpApplication.getUsers().getRsaIDNumber();
		}
		else
		{
			barcode=tpApplication.getUsers().getPassportNumber();
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("certificate_number", certificateNumber);
		//Certificates are valid for 5 years from date of ETQA Review Committee Date for full accreditation
		params.put("date_of_expiry", GenericUtility.sdf5.format(GenericUtility.addYearsToDate(tpApplication.getEtqaReviewCommitteeDate(), 5)));
		params.put("date_of_registration", GenericUtility.sdf5.format(tpApplication.getCreateDate()));
		params.put("barcode",barcode);
		params.put("user_id", tpApplication.getUsers().getId());
		JasperService.addImage(params, "top_bottom_boder.png", "top_bottom_border");
		JasperService.addImage(params, "left_right_boder.png", "left_right_border");
		JasperService.addImage(params, "certificate_signature.png", "certificate_signature");
		JasperService.addImage(params, "corner_image.png", "corner_image");
		JasperService.addImage(params, "logo2.png", "logo");
		
		//Certificate Attachment
		AttachmentBean beanAttachment=new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans=new ArrayList<>();
		byte[] bites = JasperService.instance().convertJasperReportToByte("AssessorCertificate.jasper", params);
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename("AssessorCertificate.pdf");
		attachmentBeans.add(beanAttachment);
		
		//ETQ-TP-027-AssessorCertificateLetter Attachment
		params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		params.put("barcode",barcode);
		params.put("am_app_id", tpApplication.getId());
		byte[] bites2 = JasperService.instance().convertJasperReportToByte("ETQ-TP-027-AssessorCertificateLetter.jasper", params);
		beanAttachment=new AttachmentBean();
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites2);
		beanAttachment.setFilename("AssessorCertificateLetter.pdf");
		attachmentBeans.add(beanAttachment);
		
		//Qualification/Unit Standards Attachment
		params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		//params.put("company_id",company.getId() ); //To be fixed
		params.put("user_id",tpApplication.getUsers().getId() );
		params.put("registration_number",tpApplication.getCertificateNumber());
		params.put("status",tpApplication.getApprovalStatus().getFriendlyName());
		params.put("barcode",barcode);
		params.put("date", GenericUtility.sdf7.format(new Date()));
		
		ArrayList<QualificationUnitStandardBean> qualificationUnitStandardList=new ArrayList<>();
		//Creating QualificationUnitStandardBeanDataSource
		for(CompanyQualifications qual:companyQualifications){
			 qualificationUnitStandardList.add(new QualificationUnitStandardBean(String.valueOf(qual.getQualification().getCode()), qual.getQualification().getDescription(), GenericUtility.sdf7.format(tpApplication.getApprovedDate())));
		 }
		 
		 for(CompanyUnitStandard us:unitStandards){
			 qualificationUnitStandardList.add(new QualificationUnitStandardBean(String.valueOf(us.getUnitStandard().getCode()), us.getUnitStandard().getTitle(), GenericUtility.sdf7.format(tpApplication.getApprovedDate())));
		 }
		params.put("QualificationUnitStandardBeanDataSource", new JRBeanCollectionDataSource(qualificationUnitStandardList));
		byte[] bites3 = JasperService.instance().convertJasperReportToByte("ETQ-TP-007-StatementOfQualificationsandorUnitStandards.jasper", params);
		beanAttachment=new AttachmentBean();
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites3);
		beanAttachment.setFilename("Statement_Of_Qualifications_And_Or_UnitStandards.pdf");
		attachmentBeans.add(beanAttachment);
		

		String subject = "ASSESSOR ACCREDITATION CERTIFICATE";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " +tpApplication.getUsers().getFirstName()+ " " + tpApplication.getUsers().getLastName()+ ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				
				+ "The merSETA would like to congratulate you for having successfully "
				+ "been registered as an Assessor as per the attached Statement of "
				+ "Results of the Qualification(s) and/or Unit Statement(s)."
			
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"

				+ "You are requested to go through the merSETA Quality Assurance "
				+ "& Partnerships policies, particularly the registration of "
				+ "Assessor and the code of good conduct in the Assessor "
				+ "section to acclimatise yourself with them."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"

				+ "Looking forward to you having a fruitful relationship "
				+ "with the merSETA. Enclosed is your certificate."
			
				+ "</p>"
                
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">MerSETA Administration</p>";

		//GenericUtility.sendMailWithAttachement(am.getUser().getEmail(), subject, mssg, bites, "Assessor_Certificate.pdf", "pdf", null);
		GenericUtility.sendMailWithAttachementTempWithLog(tpApplication.getUsers().getEmail(), subject, mssg, attachmentBeans, null,tpApplication.getClass().getName(),tpApplication.getId());

}
	
	public void sendTPApplicationEmail(List<UnitStandards> unitStandardsList, List<Qualification> qualificationsList, Company company, Users formUser,TrainingProviderApplication trainingProviderApplication, ArrayList<SkillsProgram> skillsProgramList, ArrayList<SkillsSet> skillsSetList) throws Exception {
	
		byte[] bites = createETQFM002AccreditationApprovalApplicationFormBytes(unitStandardsList, qualificationsList, company, formUser, trainingProviderApplication, skillsProgramList, skillsSetList,getTitle(trainingProviderApplication.getAccreditationApplicationTypeEnum()));
		
		String subject = "ACKNOWLEDGEMENT OF SKILLS DEVELOPMENT PROVIDER APPLICATION";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+formUser.getTitle().getDescription()+" " +formUser.getFirstName() + " " + formUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The merSETA acknowledges receipt of a Skills Development "
				+ "Provider application for "+company.getCompanyName()+" ("+company.getLevyNumber()+") for the following qualification(s) / unit standard(s) / skills programme(s) / skills set(s):"
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
	
	public void sendNonMerSetaApprovalEmail(List<UnitStandards> unitStandardsList, List<Qualification> qualificationsList,
			Users formUser,TrainingProviderApplication trainingProviderApplication, List<SkillsProgram> skillsProgramList,List<SkillsSet> skillsSetList) throws Exception {
		AttachmentBean beanAttachment=new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans=new ArrayList<>();
		byte[] bites = createETQFM002AccreditationApprovalApplicationFormBytes(unitStandardsList, qualificationsList, trainingProviderApplication.getCompany(), trainingProviderApplication.getUsers(), trainingProviderApplication, skillsProgramList, skillsSetList,getTitle(trainingProviderApplication.getAccreditationApplicationTypeEnum()));
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
		String subject = "SKILLS DEVELOPMENT PROVIDER REGISTRATION OUTCOME";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+formUser.getTitle().getDescription()+" " +formUser.getFirstName() + " " + formUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We write to inform you that your application to register "+sdpName+" ("+accrNumber+") as a "+getTitle(trainingProviderApplication.getAccreditationApplicationTypeEnum())+" "
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
	
	public void sendNonMerSetaFinalRejectEmail(Users u, ArrayList<RejectReasons> selectedRejectReason,TrainingProviderApplication trainingProviderApplication) {
		String title ="";
		String rejectReason = convertToHTML(selectedRejectReason);
		if(u.getTitle()!=null) {
			title = u.getTitle().getDescription();
		}
		String welcome = "<br/>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #Title# #FirstName# #Surname#,</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "We write to inform you that your application to "
						+ "register as a "+getTitle(trainingProviderApplication.getAccreditationApplicationTypeEnum())+" "
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
		GenericUtility.sendMail(u.getEmail(), "SKILLS DEVELOPMENT PROVIDER REGISTRATION OUTCOME", welcome, null);
	}
	
	public void sendNonMerSetaAcknowledgementEmail(Users formUser,TrainingProviderApplication tpApplication) throws Exception {
		
		String accreditationNumber="N/A";
		if(tpApplication.getAccreditationNumber() !=null){
			accreditationNumber=tpApplication.getAccreditationNumber();
		}
		String subject = "ACKNOWLEDGEMENT OF APPLICATION TO REGISTER AS A SKILLS DEVELOPMENT PROVIDER ON THE NSDMS";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+formUser.getTitle().getDescription()+" " +formUser.getFirstName() + " " + formUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Your application dated "+HAJConstants.sdfDDMMYYYY2.format(new Date())+" to register as a "
				+ ""+getTitle(tpApplication.getAccreditationApplicationTypeEnum())+" ("+accreditationNumber+") "
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
	
	public byte[] createETQFM002AccreditationApprovalApplicationFormBytes(List<UnitStandards> unitStandardsList, List<Qualification> qualificationsList, Company company, Users formUser,TrainingProviderApplication trainingProviderApplication, List<SkillsProgram> skillsProgramList, List<SkillsSet> skillsSetList,String title) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		CompanyService companyService=new CompanyService();
		trainingProviderApplication=findByKey(trainingProviderApplication.getId());
		trainingProviderApplication.setCompany(companyService.findByKey(trainingProviderApplication.getCompany().getId()));
		JasperService.addLogo(params);
		CompanyUsersService companyUsersService=new CompanyUsersService();
		companyUsersService.findByCompanyType(company.getId(),ConfigDocProcessEnum.TP);
		params.put("title",title.toUpperCase());
		params.put("trainingProviderApplication",trainingProviderApplication);
		params.put("contactPersonDataSource",new  JRBeanCollectionDataSource(companyUsersService.findByCompanyType(company.getId(),ConfigDocProcessEnum.TP)));
		params.put("qualificationDataSource",new JRBeanCollectionDataSource(qualificationsList));
		params.put("unitStandardDataSource",new JRBeanCollectionDataSource(unitStandardsList));
		params.put("skillsProgramDataSource",new JRBeanCollectionDataSource(skillsProgramList));
		params.put("skillsSetDataSource",new JRBeanCollectionDataSource(skillsSetList));
		byte[] bites = JasperService.instance().convertJasperReportToByte("ETQ-FM-002AccreditationApprovalApplicationForm.jasper", params);
		
		return bites;

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
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> sortAndFilterWhereTPInfoWhereApproved(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from TrainingProviderApplication o where o.id is not null and o.accreditationApplicationTypeEnum = :applicationType and o.approvalStatus = :approvalStatus ";
		filters.put("applicationType", AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL);
		filters.put("approvalStatus", ApprovalEnum.Approved);
		return (List<TrainingProviderApplication>) dao.sortAndFilterWhereTPInfo(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countTPInfoWhereApproved(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderApplication o where o.id is not null and o.accreditationApplicationTypeEnum = :applicationType and o.approvalStatus = :approvalStatus ";
		return dao.countWhereTPInfo(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> sortAndFilterWhereTPWhereApproved(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from TrainingProviderApplication o where o.id is not null and o.approvalStatus = :approvalStatus ";
		filters.put("approvalStatus", ApprovalEnum.Approved);
		return (List<TrainingProviderApplication>) dao.sortAndFilterWhereTPInfo(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countTPWhereApproved(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderApplication o where o.id is not null and o.approvalStatus = :approvalStatus ";
		return dao.countWhereTPInfo(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> sortAndFilterWhereTPWhere(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from TrainingProviderApplication o where o.id is not null ";
		return (List<TrainingProviderApplication>) dao.sortAndFilterWhereTPInfo(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countTPWhere(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderApplication o where o.id is not null ";
		return dao.countWhereTPInfo(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> sortAndFilterWhereTPInfo(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from TrainingProviderApplication o where o.id is not null ";
		return (List<TrainingProviderApplication>) dao.sortAndFilterWhereTPInfo(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countWhereTPInfo(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderApplication o where o.id is not null ";
		return dao.countWhereTPInfo(filters, hql);
	}
	
	public String findCompanyAccreditation(Company company)  throws Exception {
		String accNum="N/A";
		List<TrainingProviderApplication> tpApplications=dao.findByCompanyAndStatus(company, ApprovalEnum.Approved);
		if(tpApplications.size()>0)
		{
			TrainingProviderApplication tp=tpApplications.get(0);
			if(!tp.getCertificateNumber().isEmpty())
			{
				accNum=tp.getCertificateNumber();
			}
			else if(!tp.getAccreditationNumber().isEmpty())
			{
				accNum=tp.getAccreditationNumber();
			}
		}
		return accNum;
	}
	
	public int countAllApprovedApplication() throws Exception {
		return dao.countAllApprovedApplication();
	}
	
	public void downloadSelfEvaluationForm(TrainingProviderApplication trainingproviderapplication,SDPExtensionOfScope sdpExtensionOfScope) throws Exception
	{
		AuditorMonitorReviewService auditorMonitorReviewService = new AuditorMonitorReviewService();
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService js=new JasperService();
		String fileNameID="";
		if(trainingproviderapplication !=null)
		{
			if(trainingproviderapplication.getUsers().getRsaIDNumber() !=null && !trainingproviderapplication.getUsers().getRsaIDNumber().isEmpty())
			{
				fileNameID= trainingproviderapplication.getUsers().getRsaIDNumber();
			}
			else
			{
				fileNameID=trainingproviderapplication.getUsers().getPassportNumber();
			}
			params.put("applicationType", trainingproviderapplication.getAccreditationApplicationTypeEnum().getFriendlyName());
			params.put("TrainingProviderApplication", trainingproviderapplication);
			ArrayList<AuditorMonitorReview> auditorMonitorReviewList=(ArrayList<AuditorMonitorReview>) auditorMonitorReviewService.findByTargetKeyAndClass(trainingproviderapplication.getClass().getName(), trainingproviderapplication.getId());
			params.put("AuditorMonitorReviewdataSource", new JRBeanCollectionDataSource(auditorMonitorReviewList));
		}
		else if(sdpExtensionOfScope !=null)
		{
			
			if(sdpExtensionOfScope.getUsers().getRsaIDNumber() !=null && !sdpExtensionOfScope.getUsers().getRsaIDNumber().isEmpty())
			{
				fileNameID= sdpExtensionOfScope.getUsers().getRsaIDNumber();
			}
			else
			{
				fileNameID=sdpExtensionOfScope.getUsers().getPassportNumber();
			}
			params.put("applicationType", "Extension Of Scope");
			params.put("TrainingProviderApplication", sdpExtensionOfScope.getTrainingProviderApplication());
			ArrayList<AuditorMonitorReview> auditorMonitorReviewList=(ArrayList<AuditorMonitorReview>) auditorMonitorReviewService.findByTargetKeyAndClass(sdpExtensionOfScope.getClass().getName(), sdpExtensionOfScope.getId());
			params.put("AuditorMonitorReviewdataSource", new JRBeanCollectionDataSource(auditorMonitorReviewList));
		}
		JasperService.addFormTemplateParams(params);
		params.put("footerDocTitle", "Self Evaluation Form".toUpperCase());
		params.put("footerDocNum", "");
		params.put("title", "Self Evaluation Form".toUpperCase());
		
		
		js.createReportFromDBtoServletOutputStream("TrainingProviderSelfEvaluationForm.jasper", params,fileNameID+"_SelfEvaluationForm.pdf");
		
	
	}

	/**
	 * Locates TrainingProviderApplication object by NonSetaCompany Object 
	 * 
	 * @param nonSetaCompany
	 * 		The NonSetaCompany Object
	 * @see nonSetaCompany
	 * @return TrainingProviderApplication
	 * @throws Exception
	 */
	public TrainingProviderApplication findByNonMersetaCompany(NonSetaCompany nonSetaCompany) throws Exception {
		return dao.findByNonMersetaCompanyId(nonSetaCompany.getId());
	}
	
	public Users findUserByCompany(Company company) throws Exception {
		return dao.findUserByCompany(company);
	}
	
	public Users findUsersByCompanyAndReturnUser(Company company) throws Exception {
		List<Users> returnList = findUsersByCompany(company);
		if (returnList.size() == 0) {
			return null;
		}else {
			return returnList.get(0);
		}
	}
	
	public List<Users> findUsersByCompany(Company company) throws Exception {
		return dao.findUsersByCompany(company);
	}
	
	public List<TrainingProviderApplication> findTrainingProviderApplicationStartDateBy() throws Exception {
		return dao.findTrainingProviderApplicationStartDateBy();
	}
	public List<Company> findAllTrainingProviders() throws Exception {
		return dao.findAllTrainingProviders();
	}
	
	public void sendETQTP004AccreditationSiteVisitReport(TrainingProviderApplication trainingProviderApplication,Boolean sendEmail,Users user, Boolean uploadEvidence) {
		try {
			HostingCompanyEmployeesService hostingCompanyEmployeesService=new HostingCompanyEmployeesService();
			CompanyQualificationsService companyQualificationsService = new CompanyQualificationsService();
			CompanyUnitStandardService companyUnitStandardService = new CompanyUnitStandardService();
			CompanyUsersService companyUsersService=new CompanyUsersService();
			Map<String, Object> params = new HashMap<String, Object>();
			/**merSETA Representative(s)*/
			HostingCompanyEmployees hcEmp=null;
			List<SiteVisitUserBean> merSETARepList=new ArrayList<>();
			if(trainingProviderApplication.getQualityAssuranceUser() !=null){
			    hcEmp= hostingCompanyEmployeesService.findByUserReturnHostCompany(trainingProviderApplication.getQualityAssuranceUser().getId());
				SiteVisitUserBean merSetaRepsiteVisitUserBean=new SiteVisitUserBean(hcEmp.getJobTitle().getDescription(), hcEmp.getUsers().getFirstName(), hcEmp.getUsers().getLastName());
				merSETARepList.add(merSetaRepsiteVisitUserBean);
			}
			/**Company Representative(s)*/
			List<SiteVisitUserBean> companyRepList=new ArrayList<>();
			List<CompanyUsers> companyUsersList=companyUsersService.findByUserAndCompanyAndType(trainingProviderApplication.getCompany().getId(), trainingProviderApplication.getUsers().getId(),ConfigDocProcessEnum.TP);
			if(companyUsersList !=null && companyUsersList.size()>0){
				CompanyUsers cu=companyUsersList.get(0);
				String title="N/A";
				if(cu.getDesignation() !=null){
					title=cu.getDesignation().getDescription();
				}else if(cu.getPosition() !=null){
					title=cu.getPosition().getDescription();
				}
				SiteVisitUserBean compRepsiteVisitUserBean=new SiteVisitUserBean(title, cu.getUser().getFirstName(), cu.getUser().getLastName());
				companyRepList.add(compRepsiteVisitUserBean);
			}
			/**Qualification And Unit Standards */
			List<Qualification> qualificationsList=new ArrayList<>();
			List<SiteVisitQualUnitStandardsBean> qualList=new ArrayList<>();
			List<SiteVisitQualUnitStandardsBean> approvedQualList=new ArrayList<>();
			List<CompanyQualifications> companyQualifications=companyQualificationsService.findByTargetClassAndTargetKey(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
			for(CompanyQualifications qual:companyQualifications){
				qualificationsList.add(qual.getQualification());
				SiteVisitQualUnitStandardsBean siteVisitQualUnitStandardsBean =new SiteVisitQualUnitStandardsBean(qual.getQualification().getCodeString(), qual.getQualification().getNqflevelg2description(), qual.getQualification().getDescription());
				qualList.add(siteVisitQualUnitStandardsBean);
				if(qual.getAccept() !=null && qual.getAccept()){
					approvedQualList.add(siteVisitQualUnitStandardsBean);
				}
			}
			List<UnitStandards> unitStandardsList=new ArrayList<>();
			List<SiteVisitQualUnitStandardsBean> usList=new ArrayList<>();
			List<SiteVisitQualUnitStandardsBean> approvedUSList=new ArrayList<>();
			List<CompanyUnitStandard> unitStandardsunitStandards = companyUnitStandardService.findByTargetClassAndTargetKey(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
			for(CompanyUnitStandard compUs:unitStandardsunitStandards){
				unitStandardsList.add(compUs.getUnitStandard());
				SiteVisitQualUnitStandardsBean siteVisitQualUnitStandardsBean =new SiteVisitQualUnitStandardsBean(compUs.getUnitStandard().getSaqadecisionnumber(), compUs.getUnitStandard().getNqflevelg2description(),compUs.getUnitStandard().getTitle());
				usList.add(siteVisitQualUnitStandardsBean);
				if(compUs.getAccept() !=null && compUs.getAccept()) {
					approvedUSList.add(siteVisitQualUnitStandardsBean);
				}
			}
			
			List<SkillsProgram> skillsProgramList=new ArrayList<>();
			TrainingProviderSkillsProgrammeService trainingProviderSkillsProgrammeService=new TrainingProviderSkillsProgrammeService();
			List<TrainingProviderSkillsProgramme> tpSkillsProgrammeList=trainingProviderSkillsProgrammeService.findByTrainingProvider(trainingProviderApplication.getId());
			for(TrainingProviderSkillsProgramme sp:tpSkillsProgrammeList)
			{
				skillsProgramList.add(sp.getSkillsProgram());
			}
			
			TrainingProviderSkillsSetService trainingProviderSkillsSetService=new TrainingProviderSkillsSetService();
			List<SkillsSet> skillsSetList=new ArrayList<>();
			List<TrainingProviderSkillsSet> tpSkillsSetList=trainingProviderSkillsSetService.findByTrainingProvider(trainingProviderApplication.getId());
			for(TrainingProviderSkillsSet ss:tpSkillsSetList)
			{
				skillsSetList.add(ss.getSkillsSet());
			}
			/**Availability Of Facilitators And Assessors*/
			List<SiteVisitFacilitatorAssessorBean> assessorList=new ArrayList<>();
			List<CompanyUsers> compAssessorList=companyUsersService.findTPAssessorMod(trainingProviderApplication.getCompany().getId(), ConfigDocProcessEnum.TP);
			for(CompanyUsers us:compAssessorList)
			{
				SiteVisitFacilitatorAssessorBean siteVisitFacilitatorAssessorBean=new SiteVisitFacilitatorAssessorBean(us.getUser().getFirstName() + " "+us.getUser().getLastName(),anIDNumber(us.getUser()) , getUserQualUSHTML(us.getUser()));
				assessorList.add(siteVisitFacilitatorAssessorBean);
			}
			List<SiteVisitFacilitatorAssessorBean> facilitatorList=new ArrayList<>();//Not In Use
			String comment=trainingProviderApplication.getSiteVisitComment() !=null?trainingProviderApplication.getSiteVisitComment(): "No Comments";
			String programDirectorFullName="N/A";
			if(hcEmp !=null){
				programDirectorFullName=hcEmp.getUsers().getFirstName()+" "+hcEmp.getUsers().getLastName();
			}
			boolean assessmentOnly=false;
			if(trainingProviderApplication.getAssessmentOnly()!=null && trainingProviderApplication.getAssessmentOnly()){
				assessmentOnly=true;
			}
			String compiledBy="";
			if(trainingProviderApplication.getQualityAssuranceUser() !=null){
				compiledBy=trainingProviderApplication.getQualityAssuranceUser().getFirstName()+" "+trainingProviderApplication.getQualityAssuranceUser().getLastName();
			}
			String recommendedBy="";
			String recommendedDate="";
			if(trainingProviderApplication.getSeniorManagerQualityAssurance() !=null){
				recommendedBy=trainingProviderApplication.getSeniorManagerQualityAssurance().getFirstName()+" "+trainingProviderApplication.getSeniorManagerQualityAssurance().getLastName();
				recommendedDate=HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getRecommendedDate());
			}
			String approvedReviewCommitteeBy="";
			String approvedReviewCommitteeDate="";
			if(trainingProviderApplication.getSeniorManagerQualityAssurance() !=null){
				approvedReviewCommitteeBy=trainingProviderApplication.getSeniorManagerQualityAssurance().getFirstName()+" "+trainingProviderApplication.getSeniorManagerQualityAssurance().getLastName();
				approvedReviewCommitteeDate=HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getRecommendedToReviewCommDate());
			}
			JasperService.addLogo(params);
			JasperService.addImage(params, "checkbox-marked.png", "checked_image");
			JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
			params.put("company_id", trainingProviderApplication.getCompany().getId());
			params.put("self_evaluation_review_date",HAJConstants.sdfDDMMYYYY2.format(new Date()));
			params.put("provider_name",trainingProviderApplication.getCompany().getCompanyName());
			params.put("merSETARepDataSource",new JRBeanCollectionDataSource(merSETARepList));
			params.put("companyRepDataSource",new JRBeanCollectionDataSource(companyRepList));
			params.put("qualDataSource",new JRBeanCollectionDataSource(qualList));
			params.put("usDataSource",new JRBeanCollectionDataSource(usList));
			params.put("approvedQualDataSouce",new JRBeanCollectionDataSource(approvedQualList));
			params.put("approvedUSDataset",new JRBeanCollectionDataSource(approvedUSList));
			params.put("assessorDataSource",new JRBeanCollectionDataSource(assessorList));
			params.put("facilitatorDataSource",new JRBeanCollectionDataSource(facilitatorList));
			
			params.put("siteVisitDate",HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getSiteVisitDate()));
			params.put("reportDate",HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getSiteVisitReportDate()));
			
			params.put("comment",comment);
			params.put("AssessmentOnly",assessmentOnly);
			params.put("programDirectorFullName",programDirectorFullName);
			params.put("areaForImprovement",getAreaForImprovement(trainingProviderApplication.getClass().getName(),trainingProviderApplication.getId()));
			params.put("compiledBy",compiledBy);
			params.put("compiledDate",HAJConstants.sdfDDMMYYYY2.format(new Date()));
			params.put("recommendedBy",recommendedBy);
			params.put("recommendedDate",recommendedDate);
			//Approved for Recommendation to Review Committee
			params.put("approvedReviewCommitteeBy",approvedReviewCommitteeBy);
			params.put("approvedReviewCommitteeDate",approvedReviewCommitteeDate);
			
			
			JasperService jasperService=new JasperService();
			if(!sendEmail)//Download
			{
				jasperService.createReportFromDBtoServletOutputStream("ETQ-TP-004-AccreditationSiteVisitReport.jasper", params,"AccreditationSiteVisitReport.pdf");
			}
			else{
				byte[] bites=null;
				if(!uploadEvidence){
					bites = JasperService.instance().convertJasperReportToByte("ETQ-TP-004-AccreditationSiteVisitReportConform.jasper", params);
				}
				else{
					bites = JasperService.instance().convertJasperReportToByte("ETQ-TP-004-AccreditationSiteVisitReport.jasper", params);
				}
				String subject = "SKILLS DEVELOPMENT PROVIDER ACCREDITATION SITE VISIT REPORT";
				String welcome=getSiteVisitEmailContent(uploadEvidence, user, trainingProviderApplication, qualificationsList, unitStandardsList, skillsProgramList, skillsSetList,false);
				GenericUtility.sendMailWithAttachement(user.getEmail(), subject, welcome, bites, "AccreditationSiteVisitReport.pdf", "pdf", null);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void sendExtentionOfScopeSiteVisitReport( SDPExtensionOfScope sdpextensionofscope,Boolean sendEmail,Users user, Boolean uploadEvidence) {
		try {
			HostingCompanyEmployeesService hostingCompanyEmployeesService=new HostingCompanyEmployeesService();
			CompanyQualificationsService companyQualificationsService = new CompanyQualificationsService();
			CompanyUnitStandardService companyUnitStandardService = new CompanyUnitStandardService();
			CompanyUsersService companyUsersService=new CompanyUsersService();
			Map<String, Object> params = new HashMap<String, Object>();
			/**merSETA Representative(s)*/
			HostingCompanyEmployees hcEmp=null;
			List<SiteVisitUserBean> merSETARepList=new ArrayList<>();
			if(sdpextensionofscope.getQualityAssuranceUser() !=null){
			    hcEmp= hostingCompanyEmployeesService.findByUserReturnHostCompany(sdpextensionofscope.getQualityAssuranceUser().getId());
				SiteVisitUserBean merSetaRepsiteVisitUserBean=new SiteVisitUserBean(hcEmp.getJobTitle().getDescription(), hcEmp.getUsers().getFirstName(), hcEmp.getUsers().getLastName());
				merSETARepList.add(merSetaRepsiteVisitUserBean);
			}
			/**Company Representative(s)*/
			List<SiteVisitUserBean> companyRepList=new ArrayList<>();
			List<CompanyUsers> companyUsersList=companyUsersService.findByUserAndCompanyAndType(sdpextensionofscope.getTrainingProviderApplication().getCompany().getId(), sdpextensionofscope.getUsers().getId(),ConfigDocProcessEnum.TP);
			if(companyUsersList !=null && companyUsersList.size()>0){
				CompanyUsers cu=companyUsersList.get(0);
				String title="N/A";
				if(cu.getDesignation() !=null){
					title=cu.getDesignation().getDescription();
				}else if(cu.getPosition() !=null){
					title=cu.getPosition().getDescription();
				}
				SiteVisitUserBean compRepsiteVisitUserBean=new SiteVisitUserBean(title, cu.getUser().getFirstName(), cu.getUser().getLastName());
				companyRepList.add(compRepsiteVisitUserBean);
			}
			/**Qualification And Unit Standards */
			List<Qualification> qualificationsList=new ArrayList<>();
			List<SiteVisitQualUnitStandardsBean> qualList=new ArrayList<>();
			List<SiteVisitQualUnitStandardsBean> approvedQualList=new ArrayList<>();
			List<CompanyQualifications> companyQualifications=companyQualificationsService.findByTargetClassAndTargetKey(sdpextensionofscope.getClass().getName(), sdpextensionofscope.getId());
			for(CompanyQualifications qual:companyQualifications){
				qualificationsList.add(qual.getQualification());
				SiteVisitQualUnitStandardsBean siteVisitQualUnitStandardsBean =new SiteVisitQualUnitStandardsBean(qual.getQualification().getCodeString(), qual.getQualification().getNqflevelg2description(), qual.getQualification().getDescription());
				qualList.add(siteVisitQualUnitStandardsBean);
				if(qual.getAccept() !=null && qual.getAccept()){
					approvedQualList.add(siteVisitQualUnitStandardsBean);
				}
			}
			List<UnitStandards> unitStandardsList=new ArrayList<>();
			List<SiteVisitQualUnitStandardsBean> usList=new ArrayList<>();
			List<SiteVisitQualUnitStandardsBean> approvedUSList=new ArrayList<>();
			List<CompanyUnitStandard> unitStandardsunitStandards = companyUnitStandardService.findByTargetClassAndTargetKey(sdpextensionofscope.getClass().getName(), sdpextensionofscope.getId());
			for(CompanyUnitStandard compUs:unitStandardsunitStandards){
				unitStandardsList.add(compUs.getUnitStandard());
				SiteVisitQualUnitStandardsBean siteVisitQualUnitStandardsBean =new SiteVisitQualUnitStandardsBean(compUs.getUnitStandard().getSaqadecisionnumber(), compUs.getUnitStandard().getNqflevelg2description(),compUs.getUnitStandard().getTitle());
				usList.add(siteVisitQualUnitStandardsBean);
				if(compUs.getAccept() !=null && compUs.getAccept()) {
					approvedUSList.add(siteVisitQualUnitStandardsBean);
				}
			}
			
			List<SkillsProgram> skillsProgramList=new ArrayList<>();
			TrainingProviderSkillsProgrammeService trainingProviderSkillsProgrammeService=new TrainingProviderSkillsProgrammeService();
			List<TrainingProviderSkillsProgramme> tpSkillsProgrammeList=trainingProviderSkillsProgrammeService.findByTargetClassAndTargetKey(sdpextensionofscope.getClass().getName(), sdpextensionofscope.getId());
			for(TrainingProviderSkillsProgramme sp:tpSkillsProgrammeList)
			{
				skillsProgramList.add(sp.getSkillsProgram());
			}
			
			TrainingProviderSkillsSetService trainingProviderSkillsSetService=new TrainingProviderSkillsSetService();
			List<SkillsSet> skillsSetList=new ArrayList<>();
			List<TrainingProviderSkillsSet> tpSkillsSetList=trainingProviderSkillsSetService.findByTargetClassAndTargetKey(sdpextensionofscope.getClass().getName(), sdpextensionofscope.getId());
			for(TrainingProviderSkillsSet ss:tpSkillsSetList)
			{
				skillsSetList.add(ss.getSkillsSet());
			}
			/**Availability Of Facilitators And Assessors*/
			List<SiteVisitFacilitatorAssessorBean> assessorList=new ArrayList<>();
			List<CompanyUsers> compAssessorList=companyUsersService.findTPAssessorMod(sdpextensionofscope.getTrainingProviderApplication().getCompany().getId(), ConfigDocProcessEnum.TP);
			for(CompanyUsers us:compAssessorList)
			{
				SiteVisitFacilitatorAssessorBean siteVisitFacilitatorAssessorBean=new SiteVisitFacilitatorAssessorBean(us.getUser().getFirstName() + " "+us.getUser().getLastName(),anIDNumber(us.getUser()) , getUserQualUSHTML(us.getUser()));
				assessorList.add(siteVisitFacilitatorAssessorBean);
			}
			List<SiteVisitFacilitatorAssessorBean> facilitatorList=new ArrayList<>();//Not In Use
			String comment=sdpextensionofscope.getSiteVisitComment() !=null?sdpextensionofscope.getSiteVisitComment(): "No Comments";
			String programDirectorFullName="N/A";
			if(hcEmp !=null){
				programDirectorFullName=hcEmp.getUsers().getFirstName()+" "+hcEmp.getUsers().getLastName();
			}
			boolean assessmentOnly=false;
			if(sdpextensionofscope.getTrainingProviderApplication().getAssessmentOnly()!=null && sdpextensionofscope.getTrainingProviderApplication().getAssessmentOnly()){
				assessmentOnly=true;
			}
			String compiledBy="";
			if(sdpextensionofscope.getQualityAssuranceUser() !=null){
				compiledBy=sdpextensionofscope.getQualityAssuranceUser().getFirstName()+" "+sdpextensionofscope.getQualityAssuranceUser().getLastName();
			}
			String recommendedBy="";
			String recommendedDate="";
			if(sdpextensionofscope.getSeniorManagerQualityAssurance() !=null){
				recommendedBy=sdpextensionofscope.getSeniorManagerQualityAssurance().getFirstName()+" "+sdpextensionofscope.getSeniorManagerQualityAssurance().getLastName();
				recommendedDate=HAJConstants.sdfDDMMYYYY2.format(sdpextensionofscope.getRecommendedDate());
			}
			String approvedReviewCommitteeBy="";
			String approvedReviewCommitteeDate="";
			if(sdpextensionofscope.getSeniorManagerQualityAssurance() !=null){
				approvedReviewCommitteeBy=sdpextensionofscope.getSeniorManagerQualityAssurance().getFirstName()+" "+sdpextensionofscope.getSeniorManagerQualityAssurance().getLastName();
				approvedReviewCommitteeDate=HAJConstants.sdfDDMMYYYY2.format(sdpextensionofscope.getRecommendedToReviewCommDate());
			}
			JasperService.addLogo(params);
			JasperService.addImage(params, "checkbox-marked.png", "checked_image");
			JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
			params.put("company_id", sdpextensionofscope.getTrainingProviderApplication().getCompany().getId());
			params.put("self_evaluation_review_date",HAJConstants.sdfDDMMYYYY2.format(new Date()));
			params.put("provider_name",sdpextensionofscope.getTrainingProviderApplication().getCompany().getCompanyName());
			params.put("merSETARepDataSource",new JRBeanCollectionDataSource(merSETARepList));
			params.put("companyRepDataSource",new JRBeanCollectionDataSource(companyRepList));
			params.put("qualDataSource",new JRBeanCollectionDataSource(qualList));
			params.put("usDataSource",new JRBeanCollectionDataSource(usList));
			params.put("approvedQualDataSouce",new JRBeanCollectionDataSource(approvedQualList));
			params.put("approvedUSDataset",new JRBeanCollectionDataSource(approvedUSList));
			params.put("assessorDataSource",new JRBeanCollectionDataSource(assessorList));
			params.put("facilitatorDataSource",new JRBeanCollectionDataSource(facilitatorList));
			
			params.put("siteVisitDate",HAJConstants.sdfDDMMYYYY2.format(sdpextensionofscope.getSiteVisitDate()));
			params.put("reportDate",HAJConstants.sdfDDMMYYYY2.format(sdpextensionofscope.getSiteVisitReportDate()));
			
			params.put("comment",comment);
			params.put("AssessmentOnly",assessmentOnly);
			params.put("programDirectorFullName",programDirectorFullName);
			params.put("areaForImprovement",getAreaForImprovement(sdpextensionofscope.getClass().getName(),sdpextensionofscope.getId()));
			params.put("compiledBy",compiledBy);
			params.put("compiledDate",HAJConstants.sdfDDMMYYYY2.format(new Date()));
			params.put("recommendedBy",recommendedBy);
			params.put("recommendedDate",recommendedDate);
			//Approved for Recommendation to Review Committee
			params.put("approvedReviewCommitteeBy",approvedReviewCommitteeBy);
			params.put("approvedReviewCommitteeDate",approvedReviewCommitteeDate);
			
			
			JasperService jasperService=new JasperService();
			if(!sendEmail)//Download
			{
				jasperService.createReportFromDBtoServletOutputStream("ETQ-TP-004-AccreditationSiteVisitReport.jasper", params,"AccreditationSiteVisitReport.pdf");
			}
			else{
				byte[] bites=null;
				if(!uploadEvidence){
					bites = JasperService.instance().convertJasperReportToByte("ETQ-TP-004-AccreditationSiteVisitReportConform.jasper", params);
				}
				else{
					bites = JasperService.instance().convertJasperReportToByte("ETQ-TP-004-AccreditationSiteVisitReport.jasper", params);
				}
				String subject = "SKILLS DEVELOPMENT PROVIDER EXTENSION OF SCOPE SITE VISIT REPORT";
				String welcome=getSiteVisitEmailContent(uploadEvidence, user, sdpextensionofscope.getTrainingProviderApplication(), qualificationsList, unitStandardsList, skillsProgramList, skillsSetList,true);
				GenericUtility.sendMailWithAttachement(user.getEmail(), subject, welcome, bites, "AccreditationSiteVisitReport.pdf", "pdf", null);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public String getSiteVisitEmailContent(Boolean uploadEvidence,Users user,TrainingProviderApplication trainingProviderApplication,
	List<Qualification> qualificationsList,List<UnitStandards> unitStandardsList,List<SkillsProgram> skillsProgramList,	List<SkillsSet> skillsSetList,Boolean extensionOfScope)
	{
		String title ="";
		if(user.getTitle()!=null) {
			title = user.getTitle().getDescription();
		}
		String welcome="";
		if(extensionOfScope==null || !extensionOfScope)
		{
			if(uploadEvidence)
			{
			    welcome = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #Title# #FirstName# #Surname#,</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "The merSETA hereby advises that a site visit was at "
						+ "the following location: #CompanyName# on #SiteVisitDate# as "
						+ "part of the skills development provider accreditation "
						+ "application against the following qualification(s)/unit "
						+ "standard(s)/skills programme(s)/skills set(s): "
						+ "</p>" 
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ convertToHTML(unitStandardsList, qualificationsList, skillsProgramList, skillsSetList)
						+ "</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "During the site visit the Quality Assuror identified areas "
						+ "where additional evidence is required. "
						+ "A copy of the monitoring site visit report is "
						+ "attached for your information. Please provide "
						+ "the required evidence where indicated."
						+ "</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Please do not hesitate to contact the merSETA Regional Office for any further assistance or clarification."
						+ "</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Yours sincerely,"
						+ "</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Manager: Quality Assurance"
						+ "</p>" 
						+ "<br/>";
						welcome = welcome.replaceAll("#Title#", title);
						welcome = welcome.replaceAll("#FirstName#", user.getFirstName());
						welcome = welcome.replaceAll("#Surname#", user.getLastName());
						welcome = welcome.replaceAll("#CompanyName#", trainingProviderApplication.getCompany().getCompanyName());
						welcome = welcome.replaceAll("#SiteVisitDate#",HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getSiteVisitDate()));
			}
			else
			{
			   welcome = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #Title# #FirstName# #Surname#,</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "The merSETA hereby advises that a site visit was "
						+ "at the following location: #CompanyName# on #SiteVisitDate# "
						+ "as part of the skills development provider accreditation "
						+ "application against the following qualification(s)/unit "
						+ "standard(s)/skills programme(s)/skills set(s): "
						+ "</p>" 
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ convertToHTML(unitStandardsList, qualificationsList, skillsProgramList, skillsSetList)
						+ "</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "A copy of the monitoring site visit report is "
						+ "attached for your information and records."
						+ "</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Please do not hesitate to contact the merSETA "
						+ "Regional Office for any further assistance or clarification."
						+ "</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Yours sincerely,"
						+ "</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Manager: Quality Assurance"
						+ "</p>" 
						+ "<br/>";
						welcome = welcome.replaceAll("#Title#", title);
						welcome = welcome.replaceAll("#FirstName#", user.getFirstName());
						welcome = welcome.replaceAll("#Surname#", user.getLastName());
						welcome = welcome.replaceAll("#CompanyName#", trainingProviderApplication.getCompany().getCompanyName());
						welcome = welcome.replaceAll("#SiteVisitDate#",HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getSiteVisitDate()));
			}
		
		}
		else
		{
			if(uploadEvidence)
			{
			    welcome = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #Title# #FirstName# #Surname#,</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "The merSETA hereby advises that a site visit was at "
						+ "the following location: #CompanyName# on #SiteVisitDate# as "
						+ "part of the skills development provider extension of scope "
						+ "application against the following qualification(s)/unit "
						+ "standard(s)/skills programme(s)/skills set(s): "
						+ "</p>" 
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ convertToHTML(unitStandardsList, qualificationsList, skillsProgramList, skillsSetList)
						+ "</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "During the site visit the Quality Assuror identified areas "
						+ "where additional evidence is required. "
						+ "A copy of the monitoring site visit report is "
						+ "attached for your information. Please provide "
						+ "the required evidence where indicated."
						+ "</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Please do not hesitate to contact the merSETA Regional Office for any further assistance or clarification."
						+ "</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Yours sincerely,"
						+ "</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Manager: Quality Assurance"
						+ "</p>" 
						+ "<br/>";
						welcome = welcome.replaceAll("#Title#", title);
						welcome = welcome.replaceAll("#FirstName#", user.getFirstName());
						welcome = welcome.replaceAll("#Surname#", user.getLastName());
						welcome = welcome.replaceAll("#CompanyName#", trainingProviderApplication.getCompany().getCompanyName());
						welcome = welcome.replaceAll("#SiteVisitDate#",HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getSiteVisitDate()));
			}
			else
			{
			   welcome = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #Title# #FirstName# #Surname#,</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "The merSETA hereby advises that a site visit was "
						+ "at the following location: #CompanyName# on #SiteVisitDate# "
						+ "as part of the skills development provider extension of scope "
						+ "application against the following qualification(s)/unit "
						+ "standard(s)/skills programme(s)/skills set(s): "
						+ "</p>" 
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ convertToHTML(unitStandardsList, qualificationsList, skillsProgramList, skillsSetList)
						+ "</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "A copy of the monitoring site visit report is "
						+ "attached for your information and records."
						+ "</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Please do not hesitate to contact the merSETA "
						+ "Regional Office for any further assistance or clarification."
						+ "</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Yours sincerely,"
						+ "</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Manager: Quality Assurance"
						+ "</p>" 
						+ "<br/>";
						welcome = welcome.replaceAll("#Title#", title);
						welcome = welcome.replaceAll("#FirstName#", user.getFirstName());
						welcome = welcome.replaceAll("#Surname#", user.getLastName());
						welcome = welcome.replaceAll("#CompanyName#", trainingProviderApplication.getCompany().getCompanyName());
						welcome = welcome.replaceAll("#SiteVisitDate#",HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getSiteVisitDate()));
			}
		}
		
		
		return welcome;
				
		
	}
	
	 public void sendFinalRejectEmail(Users u, ArrayList<RejectReasons> selectedRejectReason) {
		String title ="";
		String rejectReason = convertToHTML(selectedRejectReason);
		if(u.getTitle()!=null) {
			title = u.getTitle().getDescription();
		}
		String welcome = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #Title# #FirstName# #Surname#,</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "We regret to inform you that your Skills Development Provider Application has been rejected due to the following:"
						+ "</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"+rejectReason+"</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">merSETA Administration</p>" 
						+ "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		GenericUtility.sendMail(u.getEmail(), "SKILLS DEVELOPMENT PROVIDER APPLICATION OUTCOME", welcome, null);
	}
	 
	public void sendTPFinalRejectionBeforeETQA(Users u, List<RejectReasons> selectedRejectReason,TrainingProviderApplication trainingProviderApplication) throws Exception
	{
		if(trainingProviderApplication.getAccreditationApplicationTypeEnum()==AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL){
			sendPrimarySDPFinalRejectEmailBeforeETQA(u, selectedRejectReason, trainingProviderApplication);
		}
		else{
			sendLearningProgFinalRejectionBeforeETQA(u, selectedRejectReason, trainingProviderApplication);
		}
	}
 
	public void sendLearningProgFinalRejectionBeforeETQA(Users u, List<RejectReasons> selectedRejectReason,TrainingProviderApplication trainingProviderApplication) throws Exception {
		String title ="";
		String rejectReason = convertToHTML(selectedRejectReason);
		if(u.getTitle()!=null) {
			title = u.getTitle().getDescription();
		}
		String providerName="N/A";
		String companyRegNum="";
		String reviewCommitteeDate="N/A";
		String regionalOffice="N/A";
		if(trainingProviderApplication !=null && trainingProviderApplication.getReviewCommitteeMeeting()!=null)
		{
			reviewCommitteeDate=HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getReviewCommitteeMeeting().getFromDateTime());
		}
		if(trainingProviderApplication !=null && trainingProviderApplication.getCompany() !=null){
			providerName=trainingProviderApplication.getCompany().getCompanyName();
			companyRegNum=trainingProviderApplication.getCompany().getCompanyRegistrationNumber();
			RegionService regionService=new RegionService();
			if(trainingProviderApplication.getCompany().getResidentialAddress() !=null){
				RegionTown rt = RegionTownService.instance().findByTown(trainingProviderApplication.getCompany().getResidentialAddress().getTown());
				Region r = regionService.findByKey(rt.getRegion().getId());
				if(r !=null && r.getId() !=null){
					regionalOffice=r.getDescription();
				}
			}
		}
		String welcome = "<br/>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #Title# #FirstName# #Surname#,</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Please be advised that the application for Learning Programme accreditation "
						+ "for #ProviderName# (#CompanyRegNum#) has not been approved for the following reason(s):"
						+ "</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"+rejectReason+"</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Kindly contact the Regional Office - #RegionalOffice# for clarity/assistance."
						+ "</p>" 
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Senior Manager: Quality Assurance & Partnerships"
						+ "</p>" 
						+ "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#ProviderName#",providerName);
		welcome = welcome.replaceAll("#CompanyRegNum#",companyRegNum);
		welcome = welcome.replaceAll("#RegionalOffice#",regionalOffice);
		GenericUtility.sendMail(u.getEmail(), "SKILLS DEVELOPMENT PROVIDER APPLICATION OUTCOME", welcome, null);
	}
	 public void sendPrimarySDPFinalRejectEmailBeforeETQA(Users u, List<RejectReasons> selectedRejectReason,TrainingProviderApplication trainingProviderApplication) throws Exception {
			String title ="";
			String rejectReason = convertToHTML(selectedRejectReason);
			if(u.getTitle()!=null) {
				title = u.getTitle().getDescription();
			}
			String providerName="N/A";
			String companyRegNum="";
			String regionalOffice="N/A";
			
			if(trainingProviderApplication !=null && trainingProviderApplication.getCompany() !=null){
				providerName=trainingProviderApplication.getCompany().getCompanyName();
				companyRegNum=trainingProviderApplication.getCompany().getCompanyRegistrationNumber();
				RegionService regionService=new RegionService();
				if(trainingProviderApplication.getCompany().getResidentialAddress() !=null){
					RegionTown rt = RegionTownService.instance().findByTown(trainingProviderApplication.getCompany().getResidentialAddress().getTown());
					Region r = regionService.findByKey(rt.getRegion().getId());
					if(r !=null && r.getId() !=null){
						regionalOffice=r.getDescription();
					}
				}
			}
			String welcome = "<br/>"
							+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #Title# #FirstName# #Surname#,</p>" 
							+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
							+ "Please be advised that the accreditation application "
							+ "for #ProviderName# (#CompanyRegNum#) as a Primary Skills Development "
							+ "Provider has not been approved for the following reason(s):"
							+ "</p>" 
							+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"+rejectReason+"</p>"
							
							+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
							+ "Kindly contact the Regional Office - #RegionalOffice# for clarity/assistance."
							+ "</p>" 
							
							+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
							+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
							+ "Senior Manager: Quality Assurance & Partnerships"
							+ "</p>" 
							+ "<br/>";
			welcome = welcome.replaceAll("#Title#", title);
			welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
			welcome = welcome.replaceAll("#Surname#", u.getLastName());
			welcome = welcome.replaceAll("#ProviderName#",providerName);
			welcome = welcome.replaceAll("#CompanyRegNum#",companyRegNum);
			welcome = welcome.replaceAll("#RegionalOffice#",regionalOffice);
			GenericUtility.sendMail(u.getEmail(), "SKILLS DEVELOPMENT PROVIDER APPLICATION OUTCOME", welcome, null);
		}
	 
	public String convertToHTML(List<RejectReasons> rejectReasons){		
			String sb ="<ul  style=\"font-size:11.0pt;\";font-family:\"Arial\">"; 
			for (RejectReasons r: rejectReasons){
				sb +="<li>"+r.getDescription() +"</li>";
			}
			sb +="</ul>"; 
			return sb;
	}
	   
	private String getUserQualUSHTML(Users user) throws Exception {
		UserQualificationsService userQualificationsService=new UserQualificationsService();
		UserUnitStandardService userUnitStandardService=new UserUnitStandardService();
		List<UserQualifications> userQualList=userQualificationsService.findByUser(user);
		List<UserUnitStandard> userUSList=userUnitStandardService.findByUser(user);
		String sb ="";
		int count=1;
		for(UserQualifications userQual:userQualList)
		{
			sb+=""+count+"	"+"("+userQual.getQualification().getCodeString()+")"+""+userQual.getQualification().getDescription()+""
			+  "<br/><br/>";
			count++;
		}
		count++;
		for(UserUnitStandard us:userUSList)
		{
			sb+=""+count+"	"+"("+us.getUnitStandard().getSaqadecisionnumber()+")"+""+us.getUnitStandard().getTitle()+""
			+  "<br/><br/>";
			count++;
		}
		sb +="";
		return sb;
	}
	
	private String getAreaForImprovement(String targetClass,Long targetKey) throws Exception {
		AuditorMonitorReviewService auditorMonitorReviewService=new AuditorMonitorReviewService();
		List<AreaForImprovement> list=auditorMonitorReviewService.findAreaForImprovement(targetClass ,targetKey);
		String sb="N/A";
		if(list !=null && list.size()>0)
		{
		  sb ="<ul>"; 
			for (AreaForImprovement afi: list){
				sb +="<li>"+afi.getDescription()+"</li>";
			}
			sb +="</ul>"; 
		}
		return sb;
	}
	

	public void sendETQTP009LearningProgrammeApproval(TrainingProviderApplication trainingProviderApplication, Users user) throws Exception {
		byte[] bites = getETQTP009LearningProgrammeApprovalBytes(trainingProviderApplication);
		String subject = "Programme Approval Report".toUpperCase();
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+user.getTitle().getDescription()+" "+user.getFirstName()+" "+user.getLastName()+",</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please find the attached Programme Approval Report."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "<b>Manager: Quality Assurance</b>"
				+ "</p>";
		GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "ETQ-TP-009-ProgrammeApprovalReporting.pdf", "pdf", null);
		
	}
	
	public byte[] getETQTP009LearningProgrammeApprovalBytes(TrainingProviderApplication trainingProviderApplication) throws Exception {
		HostingCompanyEmployeesService hostingCompanyEmployeesService=new HostingCompanyEmployeesService();
		CompanyQualificationsService companyQualificationsService = new CompanyQualificationsService();
		CompanyUnitStandardService companyUnitStandardService = new CompanyUnitStandardService();
		CompanyUsersService companyUsersService=new CompanyUsersService();
		Map<String, Object> params = new HashMap<String, Object>();
		/**merSETA Representative(s)*/
		HostingCompanyEmployees hcEmp=null;
		List<SiteVisitUserBean> merSETARepList=new ArrayList<>();
		if(trainingProviderApplication.getQualityAssuranceUser() !=null){
		    hcEmp= hostingCompanyEmployeesService.findByUserReturnHostCompany(trainingProviderApplication.getQualityAssuranceUser().getId());
			SiteVisitUserBean merSetaRepsiteVisitUserBean=new SiteVisitUserBean(hcEmp.getJobTitle().getDescription(), hcEmp.getUsers().getFirstName(), hcEmp.getUsers().getLastName());
			merSETARepList.add(merSetaRepsiteVisitUserBean);
		}
		/**Company Representative(s)*/
		List<SiteVisitUserBean> companyRepList=new ArrayList<>();
		List<CompanyUsers> companyUsersList=companyUsersService.findByUserAndCompanyAndType(trainingProviderApplication.getCompany().getId(), trainingProviderApplication.getUsers().getId(),ConfigDocProcessEnum.TP);
		if(companyUsersList !=null && companyUsersList.size()>0){
			CompanyUsers cu=companyUsersList.get(0);
			String title="N/A";
			if(cu.getDesignation() !=null){
				title=cu.getDesignation().getDescription();
			}else if(cu.getPosition() !=null){
				title=cu.getPosition().getDescription();
			}
			SiteVisitUserBean compRepsiteVisitUserBean=new SiteVisitUserBean(title, cu.getUser().getFirstName(), cu.getUser().getLastName());
			companyRepList.add(compRepsiteVisitUserBean);
		}
		/**Qualification*/
		List<SiteVisitQualUnitStandardsBean> qualList=new ArrayList<>();
		List<SiteVisitQualUnitStandardsBean> approvedQualList=new ArrayList<>();
		List<CompanyQualifications> companyQualifications=companyQualificationsService.findByTargetClassAndTargetKey(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
		for(CompanyQualifications qual:companyQualifications){
			SiteVisitQualUnitStandardsBean siteVisitQualUnitStandardsBean =new SiteVisitQualUnitStandardsBean(qual.getQualification().getCodeString(), qual.getQualification().getNqflevelg2description(), qual.getQualification().getDescription());
			qualList.add(siteVisitQualUnitStandardsBean);
			if(qual.getAccept() !=null && qual.getAccept()){approvedQualList.add(siteVisitQualUnitStandardsBean);}
		}
		/**Unit Standards */
		List<SiteVisitQualUnitStandardsBean> usList=new ArrayList<>();
		List<SiteVisitQualUnitStandardsBean> approvedUSList=new ArrayList<>();
		List<CompanyUnitStandard> unitStandardsunitStandards = companyUnitStandardService.findByTargetClassAndTargetKey(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
		for(CompanyUnitStandard compUs:unitStandardsunitStandards){
			SiteVisitQualUnitStandardsBean siteVisitQualUnitStandardsBean =new SiteVisitQualUnitStandardsBean(String.valueOf(compUs.getUnitStandard().getCode()), compUs.getUnitStandard().getNqflevelg2description(),compUs.getUnitStandard().getTitle());
			usList.add(siteVisitQualUnitStandardsBean);
			if(compUs.getAccept() !=null && compUs.getAccept()) {approvedUSList.add(siteVisitQualUnitStandardsBean);}
		}
		/**Skills Programme */
		List<SiteVisitQualUnitStandardsBean> spList=new ArrayList<>();
		List<SiteVisitQualUnitStandardsBean> approvedSpList=new ArrayList<>();
		TrainingProviderSkillsProgrammeService trainingProviderSkillsProgrammeService=new TrainingProviderSkillsProgrammeService();
		List<TrainingProviderSkillsProgramme> tpSpList=trainingProviderSkillsProgrammeService.findByTrainingProvider(trainingProviderApplication.getId());
		for(TrainingProviderSkillsProgramme sp:tpSpList){
			SiteVisitQualUnitStandardsBean siteVisitQualUnitStandardsBean =new SiteVisitQualUnitStandardsBean(sp.getSkillsProgram().getProgrammeID(), "",sp.getSkillsProgram().getDescription());
			spList.add(siteVisitQualUnitStandardsBean);
			if(sp.getAccept() !=null && sp.getAccept()){
				approvedSpList.add(siteVisitQualUnitStandardsBean);
			}
		}
		/**Skills Set*/
		List<SiteVisitQualUnitStandardsBean> ssList=new ArrayList<>();
		List<SiteVisitQualUnitStandardsBean> approvedSsList=new ArrayList<>();
		TrainingProviderSkillsSetService trainingProviderSkillsSetService=new TrainingProviderSkillsSetService();
		List<TrainingProviderSkillsSet> tpSsList=trainingProviderSkillsSetService.findByTrainingProvider(trainingProviderApplication.getId());
		for(TrainingProviderSkillsSet ss:tpSsList){
			SiteVisitQualUnitStandardsBean siteVisitQualUnitStandardsBean =new SiteVisitQualUnitStandardsBean(ss.getSkillsSet().getProgrammeID(), "",ss.getSkillsSet().getDescription());
			ssList.add(siteVisitQualUnitStandardsBean);
			if(ss.getAccept() !=null && ss.getAccept()){
				approvedSsList.add(siteVisitQualUnitStandardsBean);
			}
		}
		/**Availability Of Facilitators And Assessors*/
		List<SiteVisitFacilitatorAssessorBean> assessorList=new ArrayList<>();
		List<CompanyUsers> compAssessorList=companyUsersService.findTPAssessorMod(trainingProviderApplication.getCompany().getId(), ConfigDocProcessEnum.TP);
		for(CompanyUsers us:compAssessorList)
		{
			SiteVisitFacilitatorAssessorBean siteVisitFacilitatorAssessorBean=new SiteVisitFacilitatorAssessorBean(us.getUser().getFirstName()+ " " +us.getUser().getLastName(),anIDNumber(us.getUser()) , getUserQualUSHTML(us.getUser()));
			assessorList.add(siteVisitFacilitatorAssessorBean);
		}
		List<SiteVisitFacilitatorAssessorBean> facilitatorList=new ArrayList<>();//Not In Use
		/**Seta Or ETQA Name*/
		String setaOrETQAName="merSETA";
		if(trainingProviderApplication.getEtqa() !=null){setaOrETQAName=trainingProviderApplication.getEtqa().getDescription();}
		/**Signature Info*/
		String compiledBy="";
		if(hcEmp !=null){compiledBy= hcEmp.getUsers().getFirstName()+" "+ hcEmp.getUsers().getLastName();}
		String compiledByDate="";
		if(trainingProviderApplication.getSiteVisitReportDate() !=null){
			compiledByDate=HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getSiteVisitReportDate());
		}
		String recommendedBy="";
		if(trainingProviderApplication.getManagerQualityAssurance() !=null){
			recommendedBy=trainingProviderApplication.getManagerQualityAssurance().getFirstName()+" "+trainingProviderApplication.getManagerQualityAssurance().getLastName();
		}
		String recommendedByDate="";
		if(trainingProviderApplication.getRecommendedDate() !=null){
			recommendedByDate=HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getRecommendedDate());
		}
		String recommendationToRCBy="";
		if(trainingProviderApplication.getSeniorManagerQualityAssurance() !=null){
			recommendationToRCBy=trainingProviderApplication.getSeniorManagerQualityAssurance().getFirstName()+" "+trainingProviderApplication.getSeniorManagerQualityAssurance().getLastName();
		}
		String recommendationToRCByDate="";
		if(trainingProviderApplication.getRecommendedToReviewCommDate() !=null){
			recommendationToRCByDate=HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getRecommendedToReviewCommDate());
		}
		JasperService.addLogo(params);
		params.put("company_id", trainingProviderApplication.getCompany().getId());
		if (trainingProviderApplication.getSiteVisitDate() != null) {
			params.put("date_of_visit",HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getSiteVisitDate()));
		} else {
			params.put("date_of_visit", "N/A");
		}
		params.put("merSETARepDataSource",new JRBeanCollectionDataSource(merSETARepList));
		params.put("companyRepDataSource",new JRBeanCollectionDataSource(companyRepList));
		params.put("qualDataSource",new JRBeanCollectionDataSource(qualList));
		params.put("usDataSource",new JRBeanCollectionDataSource(usList));
		params.put("approvedQualDataSouce",new JRBeanCollectionDataSource(approvedQualList)); 
		params.put("approvedUSDataset",new JRBeanCollectionDataSource(approvedUSList));
		params.put("assessorDataSource",new JRBeanCollectionDataSource(assessorList));
		params.put("facilitatorDataSource",new JRBeanCollectionDataSource(facilitatorList));
		
		params.put("spDataSource",new JRBeanCollectionDataSource(spList));
		params.put("ssDataSource",new JRBeanCollectionDataSource(ssList));
		params.put("approvedSsDataset",new JRBeanCollectionDataSource(approvedSsList));
		params.put("approvedSpDataSouce",new JRBeanCollectionDataSource(approvedSpList));
		
		params.put("SETA_OR_ETQA_NAME",setaOrETQAName);
		params.put("CompiledBy",compiledBy);
		params.put("CompiledByDate",compiledByDate);
		params.put("RecommendedBy",recommendedBy);
		params.put("RecommendedByDate",recommendedByDate);
		params.put("RecommendationToRCBy",recommendationToRCBy);
		params.put("RecommendationToRCByDate",recommendationToRCByDate);
		byte[] bites = JasperService.instance().convertJasperReportToByte("ETQ-TP-009-ProgrammeApprovalReporting.jasper", params);
		
		return bites;
		
	}

	
	

	public void updateReviewCommitteeDate(TrainingProviderApplication trainingproviderapplication,ReviewCommitteeMeeting reviewCommitteeMeeting,Users user) throws Exception {
		
		ReviewCommitteeMeetingAgendaService reviewCommitteeMeetingAgendaSevice=new ReviewCommitteeMeetingAgendaService();
		ScheduledEventService scheduledEventService=new ScheduledEventService();
		ReviewCommitteeMeetingUsersService reviewCommitteeMeetingUsersService=new ReviewCommitteeMeetingUsersService();
		if(reviewCommitteeMeeting !=null)
		{
			Long meetingAgendaID=HAJConstants.SDP_MEETING_SCHEDULE_TYPE_ID;//TrainingProviderApplication Approval ID
			ReviewCommitteeMeetingAgenda meetingAgender=reviewCommitteeMeetingAgendaSevice.findByMeetingAgendaAndReviewCommitteeMeeting(meetingAgendaID,reviewCommitteeMeeting.getId());
			if(meetingAgender !=null)
			{
				trainingproviderapplication.setReviewCommitteeMeetingAgenda(meetingAgender);
				trainingproviderapplication.setReviewCommitteeMeeting(reviewCommitteeMeeting);
				update(trainingproviderapplication);
			}
			else
			{
				throw new Exception("Please add TrainingProviderApplication Approval to the agenda of the selected Review Committee meeting");
			}
			//Adding meeting details to Events schedule
			List<Users> userList=reviewCommitteeMeetingUsersService.findUsersByReviewCommitteeMeeting(trainingproviderapplication.getReviewCommitteeMeeting().getId());
			scheduledEventService.addSheduleInfo(TrainingProviderApplication.class.getName(), trainingproviderapplication.getId(), user, userList, null, null, meetingAgender.getMeetingAgenda().getDescription(), trainingproviderapplication.getReviewCommitteeMeeting());
		}
		
	}
	
	public void sendETQTP050OutcomeMonitoringSiteEmail(Company company, Users formUser,String siteVisitDate) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		
		params.put("company_id",company.getId());
		params.put("user_id",formUser.getId());
		params.put("site_visit_date",siteVisitDate);
		byte[] bites = JasperService.instance().convertJasperReportToByte("ETQ-TP-050-OutcomeMonitoringSite.jasper", params);
		
		String subject = "OUTCOME OF THE MONITORING SITE VISIT";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+formUser.getTitle().getDescription()+" " +formUser.getFirstName() + " " + formUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "A monitoring site visit was conducted on "+siteVisitDate+". "
				+ "A copy of the monitoring site visit report is attached for your information."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "During the site visit the Quality Assuror identified areas where "+company.getCompanyName()+" "
				+ "must improve. A Quality Assuror will provide developmental support to "+company.getCompanyName()+" "
				+ "in order to rectify the areas as outlined in the Monitoring Report. The Quality Assuror will "
				+ "contact "+company.getCompanyName()+" in due course to set a suitable date and time for a developmental support site visit."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please do not hesitate to contact the merSETA Regional "
				+ "Office for any further assistance or clarification."
				+ "</p>"
			
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "<b>Manager: Quality Assurance</b>"
				+ "</p>";

		GenericUtility.sendMailWithAttachement(formUser.getEmail(), subject, mssg, bites, "Outcome_Of_Monitoring_Site_Visit.pdf", "pdf", null);

	}
	
	
	public void sendETQTP060ReAccreditationReport(TrainingProviderApplication trainingProviderApplication,boolean sendEmail, Users user) {
		
		try {
			
			HostingCompanyEmployeesService hostingCompanyEmployeesService=new HostingCompanyEmployeesService();
			CompanyQualificationsService companyQualificationsService = new CompanyQualificationsService();
			CompanyUnitStandardService companyUnitStandardService = new CompanyUnitStandardService();
			CompanyUsersService companyUsersService=new CompanyUsersService();
			Map<String, Object> params = new HashMap<String, Object>();
			/**merSETA Representative(s)*/
			HostingCompanyEmployees hcEmp=null;
			List<SiteVisitUserBean> merSETARepList=new ArrayList<>();
			if(trainingProviderApplication.getQualityAssuranceUser() !=null){
			    hcEmp= hostingCompanyEmployeesService.findByUserReturnHostCompany(trainingProviderApplication.getQualityAssuranceUser().getId());
				SiteVisitUserBean merSetaRepsiteVisitUserBean=new SiteVisitUserBean(hcEmp.getJobTitle().getDescription(), hcEmp.getUsers().getFirstName(), hcEmp.getUsers().getLastName());
				merSETARepList.add(merSetaRepsiteVisitUserBean);
			}
			/**Company Representative(s)*/
			List<SiteVisitUserBean> companyRepList=new ArrayList<>();
			List<CompanyUsers> companyUsersList=companyUsersService.findByUserAndCompanyAndType(trainingProviderApplication.getCompany().getId(), trainingProviderApplication.getUsers().getId(),ConfigDocProcessEnum.TP);
			if(companyUsersList !=null && companyUsersList.size()>0){
				CompanyUsers cu=companyUsersList.get(0);
				String title="N/A";
				if(cu.getDesignation() !=null){
					title=cu.getDesignation().getDescription();
				}else if(cu.getPosition() !=null){
					title=cu.getPosition().getDescription();
				}
				SiteVisitUserBean compRepsiteVisitUserBean=new SiteVisitUserBean(title, cu.getUser().getFirstName(), cu.getUser().getLastName());
				companyRepList.add(compRepsiteVisitUserBean);
			}
			/**Qualification And Unit Standards */
			List<SiteVisitQualUnitStandardsBean> approvedQualUsList=new ArrayList<>();
			List<CompanyQualifications> companyQualifications=companyQualificationsService.findByTargetClassAndTargetKey(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
			for(CompanyQualifications qual:companyQualifications){
				SiteVisitQualUnitStandardsBean siteVisitQualUnitStandardsBean =new SiteVisitQualUnitStandardsBean(qual.getQualification().getCodeString(), qual.getQualification().getNqflevelg2description(), qual.getQualification().getDescription());
				if(qual.getAccept() !=null && qual.getAccept()){approvedQualUsList.add(siteVisitQualUnitStandardsBean);}
			}
			
			List<CompanyUnitStandard> unitStandardsunitStandards = companyUnitStandardService.findByTargetClassAndTargetKey(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
			for(CompanyUnitStandard compUs:unitStandardsunitStandards){
				SiteVisitQualUnitStandardsBean siteVisitQualUnitStandardsBean =new SiteVisitQualUnitStandardsBean(compUs.getUnitStandard().getSaqadecisionnumber(), compUs.getUnitStandard().getNqflevelg2description(),compUs.getUnitStandard().getTitle());
				if(compUs.getAccept() !=null && compUs.getAccept()) {approvedQualUsList.add(siteVisitQualUnitStandardsBean);}
			}
			
			/**Availability Of Facilitators And Assessors*/
			List<SiteVisitFacilitatorAssessorBean> assessorList=new ArrayList<>();
			List<CompanyUsers> compAssessorList=companyUsersService.findTPAssessorMod(trainingProviderApplication.getCompany().getId(), ConfigDocProcessEnum.TP);
			for(CompanyUsers us:compAssessorList)
			{
				SiteVisitFacilitatorAssessorBean siteVisitFacilitatorAssessorBean=new SiteVisitFacilitatorAssessorBean(us.getUser().getFirstName()+ " " + us.getUser().getLastName(),anIDNumber(us.getUser()) , getUserQualUSHTML(us.getUser()));
				assessorList.add(siteVisitFacilitatorAssessorBean);
			}
			List<SiteVisitFacilitatorAssessorBean> facilitatorList=new ArrayList<>();//Not In Use
			/**Seta Or ETQA Name*/
			String setaOrETQAName="merSETA";
			if(trainingProviderApplication.getEtqa() !=null){setaOrETQAName=trainingProviderApplication.getEtqa().getDescription();}
			/**Signature Info*/
			String compiledBy="";
			if(hcEmp !=null){compiledBy= hcEmp.getUsers().getFirstName()+" "+ hcEmp.getUsers().getLastName();}
			String compiledByDate="";
			if(trainingProviderApplication.getSiteVisitReportDate() !=null){
				compiledByDate=HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getSiteVisitReportDate());
			}
			String recommendedBy="";
			if(trainingProviderApplication.getManagerQualityAssurance() !=null){
				recommendedBy=trainingProviderApplication.getManagerQualityAssurance().getFirstName()+" "+trainingProviderApplication.getManagerQualityAssurance().getLastName();
			}
			String recommendedByDate="";
			if(trainingProviderApplication.getRecommendedDate() !=null){
				recommendedByDate=HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getRecommendedDate());
			}
			String recommendationToRCBy="";
			if(trainingProviderApplication.getSeniorManagerQualityAssurance() !=null){
				recommendationToRCBy=trainingProviderApplication.getSeniorManagerQualityAssurance().getFirstName()+" "+trainingProviderApplication.getSeniorManagerQualityAssurance().getLastName();
			}
			String recommendationToRCByDate="";
			if(trainingProviderApplication.getRecommendedToReviewCommDate() !=null){
				recommendationToRCByDate=HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getRecommendedToReviewCommDate());
			}
			boolean assessmentOnly=false;
			if(trainingProviderApplication.getAssessmentOnly()){
				assessmentOnly=true;
			}
			JasperService.addLogo(params);
			JasperService.addImage(params, "checkbox-marked.png", "checked_image");
			JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
			/**
			<parameter name="approvedQualUSDataSouce" class="net.sf.jasperreports.engine.data.JRBeanCollectionDataSource"/>*/
			params.put("company_id", trainingProviderApplication.getCompany().getId());
			params.put("site_visit_date",HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getSiteVisitDate()));
			params.put("comments",trainingProviderApplication.getSiteVisitComment());
			params.put("AssessmentOnly",assessmentOnly);
			params.put("title","Re-Accreditation Site Visit Report");//RE-ACCREDITATION OF SECONDARY PROVIDER SITE VISIT REPORT
			params.put("merSETARepDataSource",new JRBeanCollectionDataSource(merSETARepList));
			params.put("companyRepDataSource",new JRBeanCollectionDataSource(companyRepList));
			params.put("approvedQualUSDataSouce",new JRBeanCollectionDataSource(approvedQualUsList));
			params.put("assessorDataSource",new JRBeanCollectionDataSource(assessorList));
			params.put("facilitatorDataSource",new JRBeanCollectionDataSource(facilitatorList));
			params.put("primary_etqa",setaOrETQAName);
			params.put("CompiledBy",compiledBy);
			params.put("CompiledByDate",compiledByDate);
			params.put("RecommendedBy",recommendedBy);
			params.put("RecommendedByDate",recommendedByDate);
			params.put("RecommendationToRCBy",recommendationToRCBy);
			params.put("RecommendationToRCByDate",recommendationToRCByDate);
			if(!sendEmail)//Download
			{
				JasperService.instance().createReportFromDBtoServletOutputStream("ETQ-TP-060-Re-accreditationReport.jasper", params,"Re_accreditationReport.pdf");
			}
			else
			{
				byte[] bites = JasperService.instance().convertJasperReportToByte("ETQ-TP-060-Re-accreditationReport.jasper", params);
				String subject = "Re-Accreditation Report".toUpperCase();
				String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+user.getTitle().getDescription()+" "+user.getFirstName()+" "+user.getLastName()+",</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Please find the Re-accreditation Report."
						+ "</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Yours sincerely,"
						+ "</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "<b>Manager: Quality Assurance</b>"
						+ "</p>";
				GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "ETQ-TP-060-Re-accreditationReport.pdf", "pdf", null);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
public void sendETQTP059ProviderExtensionOfScopeReport(TrainingProviderApplication trainingProviderApplication,boolean sendEmail, Users user) {
		
		try {
			
			HostingCompanyEmployeesService hostingCompanyEmployeesService=new HostingCompanyEmployeesService();
			CompanyQualificationsService companyQualificationsService = new CompanyQualificationsService();
			CompanyUnitStandardService companyUnitStandardService = new CompanyUnitStandardService();
			CompanyUsersService companyUsersService=new CompanyUsersService();
			Map<String, Object> params = new HashMap<String, Object>();
			/**merSETA Representative(s)*/
			HostingCompanyEmployees hcEmp=null;
			List<SiteVisitUserBean> merSETARepList=new ArrayList<>();
			if(trainingProviderApplication.getQualityAssuranceUser() !=null){
			    hcEmp= hostingCompanyEmployeesService.findByUserReturnHostCompany(trainingProviderApplication.getQualityAssuranceUser().getId());
				SiteVisitUserBean merSetaRepsiteVisitUserBean=new SiteVisitUserBean(hcEmp.getJobTitle().getDescription(), hcEmp.getUsers().getFirstName(), hcEmp.getUsers().getLastName());
				merSETARepList.add(merSetaRepsiteVisitUserBean);
			}
			/**Company Representative(s)*/
			List<SiteVisitUserBean> companyRepList=new ArrayList<>();
			List<CompanyUsers> companyUsersList=companyUsersService.findByUserAndCompanyAndType(trainingProviderApplication.getCompany().getId(), trainingProviderApplication.getUsers().getId(),ConfigDocProcessEnum.TP);
			if(companyUsersList !=null && companyUsersList.size()>0){
				CompanyUsers cu=companyUsersList.get(0);
				String title="N/A";
				if(cu.getDesignation() !=null){
					title=cu.getDesignation().getDescription();
				}else if(cu.getPosition() !=null){
					title=cu.getPosition().getDescription();
				}
				SiteVisitUserBean compRepsiteVisitUserBean=new SiteVisitUserBean(title, cu.getUser().getFirstName(), cu.getUser().getLastName());
				companyRepList.add(compRepsiteVisitUserBean);
			}
			/**Qualification And Unit Standards */
			List<SiteVisitQualUnitStandardsBean> approvedQualUsList=new ArrayList<>();
			List<CompanyQualifications> companyQualifications=companyQualificationsService.findByTargetClassAndTargetKey(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
			for(CompanyQualifications qual:companyQualifications){
				SiteVisitQualUnitStandardsBean siteVisitQualUnitStandardsBean =new SiteVisitQualUnitStandardsBean(qual.getQualification().getCodeString(), qual.getQualification().getNqflevelg2description(), qual.getQualification().getDescription());
				if(qual.getAccept() !=null && qual.getAccept()){approvedQualUsList.add(siteVisitQualUnitStandardsBean);}
			}
			
			List<CompanyUnitStandard> unitStandardsunitStandards = companyUnitStandardService.findByTargetClassAndTargetKey(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
			for(CompanyUnitStandard compUs:unitStandardsunitStandards){
				SiteVisitQualUnitStandardsBean siteVisitQualUnitStandardsBean =new SiteVisitQualUnitStandardsBean(compUs.getUnitStandard().getSaqadecisionnumber(), compUs.getUnitStandard().getNqflevelg2description(),compUs.getUnitStandard().getTitle());
				if(compUs.getAccept() !=null && compUs.getAccept()) {approvedQualUsList.add(siteVisitQualUnitStandardsBean);}
			}
			
			/**Availability Of Facilitators And Assessors*/
			List<SiteVisitFacilitatorAssessorBean> assessorList=new ArrayList<>();
			List<CompanyUsers> compAssessorList=companyUsersService.findTPAssessorMod(trainingProviderApplication.getCompany().getId(), ConfigDocProcessEnum.TP);
			for(CompanyUsers us:compAssessorList)
			{
				SiteVisitFacilitatorAssessorBean siteVisitFacilitatorAssessorBean=new SiteVisitFacilitatorAssessorBean(us.getUser().getFirstName()+" "+us.getUser().getLastName(),anIDNumber(us.getUser()) , getUserQualUSHTML(us.getUser()));
				assessorList.add(siteVisitFacilitatorAssessorBean);
			}
			List<SiteVisitFacilitatorAssessorBean> facilitatorList=new ArrayList<>();//Not In Use
			/**Seta Or ETQA Name*/
			String setaOrETQAName="merSETA";
			if(trainingProviderApplication.getEtqa() !=null){setaOrETQAName=trainingProviderApplication.getEtqa().getDescription();}
			/**Signature Info*/
			String compiledBy="";
			if(hcEmp !=null){compiledBy= hcEmp.getUsers().getFirstName()+" "+ hcEmp.getUsers().getLastName();}
			String compiledByDate="";
			if(trainingProviderApplication.getSiteVisitReportDate() !=null){
				compiledByDate=HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getSiteVisitReportDate());
			}
			String recommendedBy="";
			if(trainingProviderApplication.getManagerQualityAssurance() !=null){
				recommendedBy=trainingProviderApplication.getManagerQualityAssurance().getFirstName()+" "+trainingProviderApplication.getManagerQualityAssurance().getLastName();
			}
			String recommendedByDate="";
			if(trainingProviderApplication.getRecommendedDate() !=null){
				recommendedByDate=HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getRecommendedDate());
			}
			String recommendationToRCBy="";
			if(trainingProviderApplication.getSeniorManagerQualityAssurance() !=null){
				recommendationToRCBy=trainingProviderApplication.getSeniorManagerQualityAssurance().getFirstName()+" "+trainingProviderApplication.getSeniorManagerQualityAssurance().getLastName();
			}
			String recommendationToRCByDate="";
			if(trainingProviderApplication.getRecommendedToReviewCommDate() !=null){
				recommendationToRCByDate=HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getRecommendedToReviewCommDate());
			}
			boolean assessmentOnly=false;
			if(trainingProviderApplication.getAssessmentOnly()){
				assessmentOnly=true;
			}
			JasperService.addLogo(params);
			JasperService.addImage(params, "checkbox-marked.png", "checked_image");
			JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
			/**
			<parameter name="approvedQualUSDataSouce" class="net.sf.jasperreports.engine.data.JRBeanCollectionDataSource"/>*/
			params.put("company_id", trainingProviderApplication.getCompany().getId());
			params.put("site_visit_date",HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getSiteVisitDate()));
			params.put("comments",trainingProviderApplication.getSiteVisitComment());
			params.put("AssessmentOnly",assessmentOnly);
			params.put("title","Extension Of Scope Site Visit Report");//EXTENSION OF SCOPE OF PRIMARY PROVIDER SITE VISIT REPORT
			params.put("merSETARepDataSource",new JRBeanCollectionDataSource(merSETARepList));
			params.put("companyRepDataSource",new JRBeanCollectionDataSource(companyRepList));
			params.put("approvedQualUSDataSouce",new JRBeanCollectionDataSource(approvedQualUsList));
			params.put("assessorDataSource",new JRBeanCollectionDataSource(assessorList));
			params.put("facilitatorDataSource",new JRBeanCollectionDataSource(facilitatorList));
			params.put("primary_etqa",setaOrETQAName);
			params.put("CompiledBy",compiledBy);
			params.put("CompiledByDate",compiledByDate);
			params.put("RecommendedBy",recommendedBy);
			params.put("RecommendedByDate",recommendedByDate);
			params.put("RecommendationToRCBy",recommendationToRCBy);
			params.put("RecommendationToRCByDate",recommendationToRCByDate);
			if(!sendEmail)//Download
			{
				JasperService.instance().createReportFromDBtoServletOutputStream("ETQ-TP-059-ProviderExtensionOfScopeReport.jasper", params,"Provider_Extension_Of_ScopeReport.pdf");
			}
			else
			{
				byte[] bites = JasperService.instance().convertJasperReportToByte("ETQ-TP-059-ProviderExtensionOfScopeReport.jasper", params);
				String subject = "Re-Accreditation Report".toUpperCase();
				String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+user.getTitle().getDescription()+" "+user.getFirstName()+" "+user.getLastName()+",</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Please find the Re-accreditation Report."
						+ "</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Yours sincerely,"
						+ "</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "<b>Manager: Quality Assurance</b>"
						+ "</p>";
				GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "Provider_Extension_Of_ScopeReport.pdf", "pdf", null);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void sendSiteVisitEmailNotification(TrainingProviderApplication trainingProviderApplication,Users qaUser,List<UnitStandards> unitStandardsList, List<Qualification> qualificationsList,List<SkillsProgram> skillsProgramList, List<SkillsSet> skillsSetList) throws Exception {
		Users u=trainingProviderApplication.getUsers();
		CompanyService companyService=new CompanyService();
		Company company=companyService.findByKey(trainingProviderApplication.getCompany().getId());
		String title ="";
		String regionOffice="";
		if(u.getTitle()!=null) {
			title = u.getTitle().getDescription();
		}
		
		RegionTown rt = null;
		if (company.getResidentialAddress() != null && company.getResidentialAddress().getTown() != null) {
			rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
		}
		RegionService regionService = new RegionService();
		Region r = null;
		if (rt.getRegion() != null) {
			r = regionService.findByKey(rt.getRegion().getId());
			if(r!=null)
			{
				regionOffice=r.getDescription();
			}
		}
		String welcome = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #Title# #FirstName# #Surname#,</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "The merSETA hereby advises that a site visit "
						+ "has been scheduled to take place at the following location: #CompanyName# on #SiteVisitDate# "
						+ "as part of the skills development provider accreditation application against "
						+ "the following qualification(s)/unit standard(s)/skills programme(s)/skills set(s): "
						+ "</p>" 
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ convertToHTML(unitStandardsList, qualificationsList, skillsProgramList, skillsSetList)
						+ "</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Should there be a change in the scheduled date, "
						+ "please contact the Regional Office: #RegionOffice# before the visit to schedule a new date."
						+ "</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Please do not hesitate to contact the Regional office for further assistance."
						+ "</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Yours sincerely,"
						+ "</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Manager: Quality Assurance"
						+ "</p>" 
						+ "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#CompanyName#", trainingProviderApplication.getCompany().getCompanyName());
		welcome = welcome.replaceAll("#SiteVisitDate#",HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getSiteVisitDate()));
		welcome = welcome.replaceAll("#RegionOffice#", regionOffice);
		//welcome = welcome.replaceAll("#NameAndSurname#",qaUser.getFirstName()+" "+qaUser.getLastName());
		GenericUtility.sendMail(u.getEmail(), "SKILLS DEVELOPMENT PROVIDER SITE VISIT DATE", welcome, null);
		
	}
	
	public void sendExtensionOfScopeSiteVisitEmail(SDPExtensionOfScope sdpextensionofscope,Users qaUser,List<UnitStandards> unitStandardsList, List<Qualification> qualificationsList,List<SkillsProgram> skillsProgramList, List<SkillsSet> skillsSetList) throws Exception {
		Users u=sdpextensionofscope.getUsers();
		CompanyService companyService=new CompanyService();
		Company company=companyService.findByKey(sdpextensionofscope.getTrainingProviderApplication().getCompany().getId());
		String title ="";
		String regionOffice="";
		if(u.getTitle()!=null) {
			title = u.getTitle().getDescription();
		}
		
		RegionTown rt = null;
		if (company.getResidentialAddress() != null && company.getResidentialAddress().getTown() != null) {
			rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
		}
		RegionService regionService = new RegionService();
		Region r = null;
		if (rt.getRegion() != null) {
			r = regionService.findByKey(rt.getRegion().getId());
			if(r!=null)
			{
				regionOffice=r.getDescription();
			}
		}
		String welcome = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #Title# #FirstName# #Surname#,</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "The merSETA hereby advises that a site visit "
						+ "has been scheduled to take place at the following location: #CompanyName# on #SiteVisitDate# "
						+ "as part of the skills development provider extension of scope application against "
						+ "the following qualification(s)/unit standard(s)/skills programme(s)/skills set(s): "
						+ "</p>" 
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ convertToHTML(unitStandardsList, qualificationsList, skillsProgramList, skillsSetList)
						+ "</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Should there be a change in the scheduled date, "
						+ "please contact the Regional Office: #RegionOffice# before the visit to schedule a new date."
						+ "</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Please do not hesitate to contact the Regional office for further assistance."
						+ "</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Yours sincerely,"
						+ "</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Manager: Quality Assurance"
						+ "</p>" 
						+ "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#CompanyName#", company.getCompanyName());
		welcome = welcome.replaceAll("#SiteVisitDate#",HAJConstants.sdfDDMMYYYY2.format(sdpextensionofscope.getSiteVisitDate()));
		welcome = welcome.replaceAll("#RegionOffice#", regionOffice);
		//welcome = welcome.replaceAll("#NameAndSurname#",qaUser.getFirstName()+" "+qaUser.getLastName());
		GenericUtility.sendMail(u.getEmail(), "SKILLS DEVELOPMENT PROVIDER SITE VISIT DATE", welcome, null);
		
	}
	
	public void sendSiteVisitAmendedEmailNotification(TrainingProviderApplication trainingProviderApplication,Users qaUser,List<UnitStandards> unitStandardsList, List<Qualification> qualificationsList,List<SkillsProgram> skillsProgramList, List<SkillsSet> skillsSetList) throws Exception {
		Users u=trainingProviderApplication.getUsers();
		CompanyService companyService=new CompanyService();
		Company company=companyService.findByKey(trainingProviderApplication.getCompany().getId());
		String title ="";
		String regionOffice="";
		if(u.getTitle()!=null) {
			title = u.getTitle().getDescription();
		}
		
		RegionTown rt = null;
		if (company.getResidentialAddress() != null && company.getResidentialAddress().getTown() != null) {
			rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
		}
		RegionService regionService = new RegionService();
		Region r = null;
		if (rt.getRegion() != null) {
			r = regionService.findByKey(rt.getRegion().getId());
			if(r!=null)
			{
				regionOffice=r.getDescription();
			}
		}
		String welcome = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #Title# #FirstName# #Surname#,</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "The merSETA hereby advises that a site visit has "
						+ "been rescheduled to take place at the following location: #CompanyName# on #SiteVisitDate# "
						+ "as part of the skills development provider accreditation application against "
						+ "the following qualification(s)/unit standard(s)/skills programme(s)/skills set(s): "
						+ "</p>" 
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ convertToHTML(unitStandardsList, qualificationsList, skillsProgramList, skillsSetList)
						+ "</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Should there be a change in the scheduled date, "
						+ "please contact the Regional Office: #RegionOffice# before the visit to schedule a new date."
						+ "</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Please do not hesitate to contact the Regional office for further assistance."
						+ "</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Yours sincerely,"
						+ "</p>" 
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Manager: Quality Assurance"
						+ "</p>" 
						+ "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#CompanyName#", trainingProviderApplication.getCompany().getCompanyName());
		welcome = welcome.replaceAll("#SiteVisitDate#",HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getSiteVisitDate()));
		welcome = welcome.replaceAll("#RegionOffice#", regionOffice);
		//welcome = welcome.replaceAll("#NameAndSurname#",qaUser.getFirstName()+" "+qaUser.getLastName());
		GenericUtility.sendMail(u.getEmail(), "SKILLS DEVELOPMENT PROVIDER SITE VISIT DATE AMENDMENTS", welcome, null);
		
	}
	
	public void sendExtensionOfScopeSiteVisitAmendedEmail(SDPExtensionOfScope sdpextensionofscope,Users qaUser,List<UnitStandards> unitStandardsList, List<Qualification> qualificationsList,List<SkillsProgram> skillsProgramList, List<SkillsSet> skillsSetList) throws Exception {
		Users u=sdpextensionofscope.getUsers();
		CompanyService companyService=new CompanyService();
		Company company=companyService.findByKey(sdpextensionofscope.getTrainingProviderApplication().getCompany().getId());
		String title ="";
		String regionOffice="";
		if(u.getTitle()!=null) {
			title = u.getTitle().getDescription();
		}
		
		RegionTown rt = null;
		if (company.getResidentialAddress() != null && company.getResidentialAddress().getTown() != null) {
			rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
		}
		RegionService regionService = new RegionService();
		Region r = null;
		if (rt.getRegion() != null) {
			r = regionService.findByKey(rt.getRegion().getId());
			if(r!=null)
			{
				regionOffice=r.getDescription();
			}
		}
		String welcome = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #Title# #FirstName# #Surname#,</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "The merSETA hereby advises that a site visit has "
						+ "been rescheduled to take place at the following location: #CompanyName# on #SiteVisitDate# "
						+ "as part of the skills development provider extension of scope application against "
						+ "the following qualification(s)/unit standard(s)/skills programme(s)/skills set(s): "
						+ "</p>" 
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ convertToHTML(unitStandardsList, qualificationsList, skillsProgramList, skillsSetList)
						+ "</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Should there be a change in the scheduled date, "
						+ "please contact the Regional Office: #RegionOffice# before the visit to schedule a new date."
						+ "</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Please do not hesitate to contact the Regional office for further assistance."
						+ "</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Yours sincerely,"
						+ "</p>" 
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Manager: Quality Assurance"
						+ "</p>" 
						+ "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#CompanyName#", company.getCompanyName());
		welcome = welcome.replaceAll("#SiteVisitDate#",HAJConstants.sdfDDMMYYYY2.format(sdpextensionofscope.getSiteVisitDate()));
		welcome = welcome.replaceAll("#RegionOffice#", regionOffice);
		//welcome = welcome.replaceAll("#NameAndSurname#",qaUser.getFirstName()+" "+qaUser.getLastName());
		GenericUtility.sendMail(u.getEmail(), "SKILLS DEVELOPMENT PROVIDER SITE VISIT DATE AMENDMENTS", welcome, null);
		
	}

	public void completeNonSETATPApplication(TrainingProviderApplication trainingProviderApplication,Tasks task,Users user,
			List<CompanyUnitStandard> companyUnitStandardsList, List<CompanyQualifications> companyQualificationsList,
			List<TrainingProviderSkillsSet> tpSkillsSetList, List<TrainingProviderDocParent> docParentList, 
			List<TrainingProviderSkillsProgramme> tpSkillsProgramme) throws Exception {
		
		trainingProviderApplication.setApprovalStatus(ApprovalEnum.PendingApproval);
		update(trainingProviderApplication);
		
		
		List<IDataEntity> dataEntitiesCreate = new ArrayList<IDataEntity>();
		List<IDataEntity> dataEntitiesUpdate = new ArrayList<IDataEntity>();
		for (TrainingProviderDocParent docParent : docParentList) {
			dataEntitiesCreate.add(docParent);
			Doc doc = docParent.getDoc();
			doc.setVersionNo(1);
			doc.setUsers(user);
			doc.setTargetKey(docParent.getId());
			doc.setTargetClass(TrainingProviderDocParent.class.getName());
			dataEntitiesCreate.add(doc);
			dataEntitiesCreate.add(new DocByte(doc.getData(), doc));
			dataEntitiesCreate.add(new DocumentTracker(doc, user, new java.util.Date(), DocumentTrackerEnum.Upload));
		}
		if (dataEntitiesCreate.size() > 0) {
			dao.createBatch(dataEntitiesCreate);
		}
		
		dataEntitiesCreate = new ArrayList<IDataEntity>();
		// Creating qualifications
		for (CompanyQualifications cp : companyQualificationsList) {
			cp.setTargetClass(TrainingProviderApplication.class.getName());
			cp.setTargetKey(trainingProviderApplication.getId());
			cp.setCompany(trainingProviderApplication.getCompany());
			if(cp !=null && cp.getId()==null){
				dataEntitiesCreate.add(cp);
			}
			else{
				dataEntitiesUpdate.add(cp);
			}
			
		}
		// Creating unit standards
		for (CompanyUnitStandard us : companyUnitStandardsList) {
			us.setTargetClass(TrainingProviderApplication.class.getName());
			us.setTargetKey(trainingProviderApplication.getId());
			us.setCompany(trainingProviderApplication.getCompany());
			if(us !=null && us.getId()==null){
				dataEntitiesCreate.add(us);
			}
			else{
				dataEntitiesUpdate.add(us);
			}
			
		}
		
		// Creating Provider Skills Programme
		for (TrainingProviderSkillsProgramme tpSkillsProg : tpSkillsProgramme) {
			tpSkillsProg.setTrainingProviderApplication(trainingProviderApplication);
			if(tpSkillsProg !=null && tpSkillsProg.getId()==null){
				dataEntitiesCreate.add(tpSkillsProg);
			}
			else{
				dataEntitiesUpdate.add(tpSkillsProg);
			}
			
		}
		
		// Creating Provider Skills Set
		for (TrainingProviderSkillsSet tpSkillsSet : tpSkillsSetList) {
			tpSkillsSet.setTrainingProviderApplication(trainingProviderApplication);
			if(tpSkillsSet !=null && tpSkillsSet.getId()==null){
				dataEntitiesCreate.add(tpSkillsSet);
			}
			else{
				dataEntitiesUpdate.add(tpSkillsSet);
			}
		}
		if (dataEntitiesCreate.size() > 0) {
			dao.createBatch(dataEntitiesCreate);
		}
		if (dataEntitiesUpdate.size() > 0) {
			dao.updateBatch(dataEntitiesUpdate);
		}
		/** Creating task */
		String desc = ConfigDocProcessEnum.NON_SETA_PROVIDERS.getTaskDescription();
		TasksService.instance().findFirstInProcessAndCreateTask(desc, user, trainingProviderApplication.getId(), trainingProviderApplication.getClass().getName(), true,  ConfigDocProcessEnum.NON_SETA_PROVIDERS, MailDef.instance(MailEnum.Task, MailTagsEnum.Task,  ConfigDocProcessEnum.NON_SETA_PROVIDERS.getTaskDescription()), null);
		TasksService.instance().completeTask(task);
	}
	
	public List<Company> testTpApplicationByRegionId(Long regionID) throws Exception {
		return dao.testTpApplicationByRegionId(regionID);
	}


	public void downloadETQTP009(TrainingProviderApplication selectedTrainingProviderApplication) throws Exception {
		byte[] bytes=getETQTP009LearningProgrammeApprovalBytes(selectedTrainingProviderApplication);
		JasperService.instance().downloadFile(bytes, "ETQ-TP-009-ProgrammeApprovalReporting.pdf");
	}
	
	public void sendTPFinalRejection(Users u, List<RejectReasons> selectedRejectReason,TrainingProviderApplication trainingProviderApplication) throws Exception
	{
		if(trainingProviderApplication.getAccreditationApplicationTypeEnum()==AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL){
			sendPrimaryAccreditationFinalRejection(u, selectedRejectReason, trainingProviderApplication);
		}
		else{
			sendLearningProgrammeFinalRejection(u, selectedRejectReason, trainingProviderApplication);
		}
	}
	
	public void sendPrimaryAccreditationFinalRejection(Users u, List<RejectReasons> selectedRejectReason,TrainingProviderApplication trainingProviderApplication) throws Exception {
		String title ="";
		String rejectReason = convertToHTML(selectedRejectReason);
		if(u.getTitle()!=null) {
			title = u.getTitle().getDescription();
		}
		String providerName="N/A";
		String companyRegNum="";
		String reviewCommitteeDate="N/A";
		String regionalOffice="N/A";
		if(trainingProviderApplication !=null && trainingProviderApplication.getReviewCommitteeMeeting()!=null)
		{
			reviewCommitteeDate=HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getReviewCommitteeMeeting().getFromDateTime());
		}
		if(trainingProviderApplication !=null && trainingProviderApplication.getCompany() !=null){
			providerName=trainingProviderApplication.getCompany().getCompanyName();
			companyRegNum=trainingProviderApplication.getCompany().getCompanyRegistrationNumber();
			RegionService regionService=new RegionService();
			if(trainingProviderApplication.getCompany().getResidentialAddress() !=null){
				RegionTown rt = RegionTownService.instance().findByTown(trainingProviderApplication.getCompany().getResidentialAddress().getTown());
				Region r = regionService.findByKey(rt.getRegion().getId());
				if(r !=null && r.getId() !=null){
					regionalOffice=r.getDescription();
				}
			}
		}
		String welcome = "<br/>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #Title# #FirstName# #Surname#,</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Please be advised that the accreditation application "
						+ "for #ProviderName# (#CompanyRegNum#) as a Primary Skills Development "
						+ "Provider has not been approved by the merSETA Review Committee "
						+ "that considered the application on #ReviewCommitteeDate# for the following reason(s):"
						+ "</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"+rejectReason+"</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Kindly contact the Regional Office - #RegionalOffice# for clarity/assistance."
						+ "</p>" 
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Senior Manager: Quality Assurance & Partnerships"
						+ "</p>" 
						+ "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#ProviderName#",providerName);
		welcome = welcome.replaceAll("#CompanyRegNum#",companyRegNum);
		welcome = welcome.replaceAll("#ReviewCommitteeDate#",reviewCommitteeDate);
		welcome = welcome.replaceAll("#RegionalOffice#",regionalOffice);
		GenericUtility.sendMail(u.getEmail(), "SKILLS DEVELOPMENT PROVIDER APPLICATION OUTCOME", welcome, null);
	}
	
	public void sendLearningProgrammeFinalRejection(Users u, List<RejectReasons> selectedRejectReason,TrainingProviderApplication trainingProviderApplication) throws Exception {
		String title ="";
		String rejectReason = convertToHTML(selectedRejectReason);
		if(u.getTitle()!=null) {
			title = u.getTitle().getDescription();
		}
		String providerName="N/A";
		String companyRegNum="";
		String reviewCommitteeDate="N/A";
		String regionalOffice="N/A";
		if(trainingProviderApplication !=null && trainingProviderApplication.getReviewCommitteeMeeting()!=null)
		{
			reviewCommitteeDate=HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getReviewCommitteeMeeting().getFromDateTime());
		}
		if(trainingProviderApplication !=null && trainingProviderApplication.getCompany() !=null){
			providerName=trainingProviderApplication.getCompany().getCompanyName();
			companyRegNum=trainingProviderApplication.getCompany().getCompanyRegistrationNumber();
			RegionService regionService=new RegionService();
			if(trainingProviderApplication.getCompany().getResidentialAddress() !=null){
				RegionTown rt = RegionTownService.instance().findByTown(trainingProviderApplication.getCompany().getResidentialAddress().getTown());
				Region r = regionService.findByKey(rt.getRegion().getId());
				if(r !=null && r.getId() !=null){
					regionalOffice=r.getDescription();
				}
			}
		}
		String welcome = "<br/>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #Title# #FirstName# #Surname#,</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Please be advised that the application for Learning Programme accreditation "
						+ "for #ProviderName# (#CompanyRegNum#) has not been approved by the merSETA Review Committee "
						+ "that considered the application on #ReviewCommitteeDate# for the following reason(s):"
						+ "</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"+rejectReason+"</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Kindly contact the Regional Office - #RegionalOffice# for clarity/assistance."
						+ "</p>" 
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Senior Manager: Quality Assurance & Partnerships"
						+ "</p>" 
						+ "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#ProviderName#",providerName);
		welcome = welcome.replaceAll("#CompanyRegNum#",companyRegNum);
		welcome = welcome.replaceAll("#ReviewCommitteeDate#",reviewCommitteeDate);
		welcome = welcome.replaceAll("#RegionalOffice#",regionalOffice);
		GenericUtility.sendMail(u.getEmail(), "SKILLS DEVELOPMENT PROVIDER APPLICATION OUTCOME", welcome, null);
	}
	
	
	public void sendResubmition60Dayrejection(Users u, List<RejectReasons> selectedRejectReason,TrainingProviderApplication trainingProviderApplication) throws Exception
	{
		if(trainingProviderApplication.getAccreditationApplicationTypeEnum()==AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL){
			sendPrimaryAccreditation60Dayrejection(u, selectedRejectReason, trainingProviderApplication);
		}
		else{
			sendLearningProgramme60Dayrejection(u, selectedRejectReason, trainingProviderApplication);
		}
	}
	public void sendPrimaryAccreditation60Dayrejection(Users u, List<RejectReasons> selectedRejectReason,TrainingProviderApplication trainingProviderApplication) throws Exception {
		String title ="";
		String rejectReason = convertToHTML(selectedRejectReason);
		if(u.getTitle()!=null) {
			title = u.getTitle().getDescription();
		}
		String providerName="N/A";
		String companyRegNum="";
		String reviewCommitteeDate="N/A";
		String regionalOffice="N/A";
		if(trainingProviderApplication !=null && trainingProviderApplication.getReviewCommitteeMeeting()!=null)
		{
			reviewCommitteeDate=HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getReviewCommitteeMeeting().getFromDateTime());
		}
		if(trainingProviderApplication !=null && trainingProviderApplication.getCompany() !=null){
			providerName=trainingProviderApplication.getCompany().getCompanyName();
			companyRegNum=trainingProviderApplication.getCompany().getCompanyRegistrationNumber();
			RegionService regionService=new RegionService();
			if(trainingProviderApplication.getCompany().getResidentialAddress() !=null){
				RegionTown rt = RegionTownService.instance().findByTown(trainingProviderApplication.getCompany().getResidentialAddress().getTown());
				Region r = regionService.findByKey(rt.getRegion().getId());
				if(r !=null && r.getId() !=null){
					regionalOffice=r.getDescription();
				}
			}
		}
		String welcome = "<br/>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #Title# #FirstName# #Surname#,</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Please be advised that the accreditation application "
						+ "for #ProviderName# (#CompanyRegNum#) as a Primary Skills Development "
						+ "Provider has not been approved by the merSETA Review Committee "
						+ "that considered the application on #ReviewCommitteeDate# for the following reason(s):"
						+ "</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"+rejectReason+"</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Kindly be advised that you have been afforded the "
						+ "opportunity to address the issues that have been identified "
						+ "and re-submit the application within 60 working "
						+ "days from the date of receipt of this notification."
						+ "</p>" 
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Should you require assistance/support, kindly contact the Regional Office - #RegionalOffice#."
						+ "</p>" 
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Senior Manager: Quality Assurance & Partnerships"
						+ "</p>" 
						+ "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#ProviderName#",providerName);
		welcome = welcome.replaceAll("#CompanyRegNum#",companyRegNum);
		welcome = welcome.replaceAll("#ReviewCommitteeDate#",reviewCommitteeDate);
		welcome = welcome.replaceAll("#RegionalOffice#",regionalOffice);
		GenericUtility.sendMail(u.getEmail(), "SKILLS DEVELOPMENT PROVIDER APPLICATION OUTCOME", welcome, null);
	}
	
	public void sendLearningProgramme60Dayrejection(Users u,List<RejectReasons> selectedRejectReason,TrainingProviderApplication trainingProviderApplication) throws Exception {
		String title ="";
		String rejectReason = convertToHTML(selectedRejectReason);
		if(u.getTitle()!=null) {
			title = u.getTitle().getDescription();
		}
		String providerName="N/A";
		String companyRegNum="";
		String reviewCommitteeDate="N/A";
		String regionalOffice="N/A";
		if(trainingProviderApplication !=null && trainingProviderApplication.getReviewCommitteeMeeting()!=null)
		{
			reviewCommitteeDate=HAJConstants.sdfDDMMYYYY2.format(trainingProviderApplication.getReviewCommitteeMeeting().getFromDateTime());
		}
		if(trainingProviderApplication !=null && trainingProviderApplication.getCompany() !=null){
			providerName=trainingProviderApplication.getCompany().getCompanyName();
			companyRegNum=trainingProviderApplication.getCompany().getCompanyRegistrationNumber();
			RegionService regionService=new RegionService();
			if(trainingProviderApplication.getCompany().getResidentialAddress() !=null){
				RegionTown rt = RegionTownService.instance().findByTown(trainingProviderApplication.getCompany().getResidentialAddress().getTown());
				Region r = regionService.findByKey(rt.getRegion().getId());
				if(r !=null && r.getId() !=null){
					regionalOffice=r.getDescription();
				}
			}
		}
		String welcome = "<br/>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #Title# #FirstName# #Surname#,</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Please be advised that the application for Learning Programme accreditation for "
						+ "#ProviderName# (#CompanyRegNum#) has not been approved by "
						+ "the merSETA Review Committee that considered the application "
						+ "on #ReviewCommitteeDate# for the following reason(s):"
						+ "</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"+rejectReason+"</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Kindly be advised that you have been afforded the "
						+ "opportunity to address the issues that have been "
						+ "identified and re-submit the application within "
						+ "60 working days from the date of receipt of "
						+ "this notification."
						+ "</p>" 
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Should you require assistance/support, kindly contact the Regional Office - #RegionalOffice#."
						+ "</p>" 
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Senior Manager: Quality Assurance & Partnerships"
						+ "</p>" 
						+ "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#ProviderName#",providerName);
		welcome = welcome.replaceAll("#CompanyRegNum#",companyRegNum);
		welcome = welcome.replaceAll("#ReviewCommitteeDate#",reviewCommitteeDate);
		welcome = welcome.replaceAll("#RegionalOffice#",regionalOffice);
		GenericUtility.sendMail(u.getEmail(), "SKILLS DEVELOPMENT PROVIDER APPLICATION OUTCOME", welcome, null);
	}

	public void updateCompayAndCreateTask(TrainingProviderApplication trainingProviderApplication, Users users) throws Exception {
		CompanyService companyService = new CompanyService();
		companyService.updateAndCreateCompanyAndAddress(trainingProviderApplication.getCompany());
		trainingProviderApplication.setApprovalStatus(ApprovalEnum.PendingChangeApproval);
		update(trainingProviderApplication);
		String desc = "";
		TasksService.instance().findFirstInProcessAndCreateTask(desc, users, trainingProviderApplication.getId(), trainingProviderApplication.getClass().getName(), true, ConfigDocProcessEnum.SDP_COMPANY_CHANGE_APPROVAL, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
		acknowledgementCompanyChangeEmail(users);
	}
	
	public void acknowledgementCompanyChangeEmail(Users formUser) throws Exception {
		 SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
		String subject = "ACKNOWLEDGEMENT OF PROVIDER INFORMATION AMENDMENT REQUEST";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+formUser.getTitle().getDescription()+" " +formUser.getFirstName() + " " + formUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The request to amend information submitted on "+sdf.format(new Date())+" is hereby acknowledged."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please be advised that the review process may take up to five (5) "
				+ "working days and should any additional information be required, "
				+ "this will be communicated to you."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "merSETA Administration"
				+ "</p>";
		GenericUtility.sendMail(formUser.getEmail(), subject, mssg, null);

	}

	public void approveCompanyChangeTask(Company company, Tasks task, Users user,Users tpUser, TrainingProviderApplication trainingProviderApplication) throws Exception {
		CompanyService companyService = new CompanyService();
		company.setCompanyStatus(CompanyStatusEnum.Active);
		trainingProviderApplication.setApprovalStatus(ApprovalEnum.Approved);
		update(trainingProviderApplication);
		companyService.updateNoHistory(company);
		approveCompanyChangeEmail(tpUser, company);
		TasksService.instance().completeTask(task);
	}
	
	public void approveCompanyChangeEmail(Users formUser,Company company) throws Exception {
		String subject = "PROVIDER INFORMATION AMENDMENT OUTCOME: APPROVED";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+formUser.getTitle().getDescription()+" " +formUser.getFirstName() + " " + formUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The request to amend information was approved."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Kindly note that the updated information will now be used going forward. "
				+ "Please be advised that should you wish to amend the information again, "
				+ "you must complete the amendment request again."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Kindly contact the merSETA Head Office for additional information/assistance."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "merSETA Administration"
				+ "</p>";
		GenericUtility.sendMail(formUser.getEmail(), subject, mssg, null);

	}
	
	public void rejectCompanyChangeRequest(Company company, Tasks task, Users user,Users tpUser, ArrayList<RejectReasons> selectedRejectReason, Company prevCompany,TrainingProviderApplication tp) throws Exception {
		CompanyService companyService = new CompanyService();
		long id = company.getId();
		BeanUtils.copyProperties(company, prevCompany);
		company.setId(id);
		company.setCompanyStatus(CompanyStatusEnum.Active);
		companyService.updateNoHistory(company);
		
		Address ressAddress=company.getResidentialAddress();
		Address postAddress=company.getPostalAddress();
		
		List<AddressHistory> resAddressHistoryList=AddressHistoryService.instance().findByForAddress(company.getResidentialAddress().getId());
		List<AddressHistory> postAddressHistoryList=AddressHistoryService.instance().findByForAddress(company.getPostalAddress().getId());
		if(resAddressHistoryList !=null && resAddressHistoryList.size()>0){
			Long resAdreessID=company.getResidentialAddress().getId();
			BeanUtils.copyProperties(ressAddress, resAddressHistoryList.get(0));
			ressAddress.setId(resAdreessID);
			prevCompany.setResidentialAddress(ressAddress);
		}
		if(postAddressHistoryList !=null && postAddressHistoryList.size()>0){
			Long postAdreessID=company.getPostalAddress().getId();
			BeanUtils.copyProperties(postAddress, postAddressHistoryList.get(0));
			postAddress.setId(postAdreessID);
			prevCompany.setPostalAddress(postAddress);
		}
		
		AddressService.instance().update(ressAddress);
		AddressService.instance().update(postAddress);
		TasksService.instance().completeTask(task);
		
		// Creating RejectReason
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(company.getId(), company.getClass().getName(), selectedRejectReason, user, task.getWorkflowProcess());
		for(RejectReasonMultipleSelection rrm:rrmList){
			if(rrm !=null){
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		
		tp.setApprovalStatus(ApprovalEnum.Approved);
		update(tp);
		
		rejectCompanyChangeEmail(tpUser, company,selectedRejectReason);
		
	}

	public void  rejectCompanyChangeEmail(Users formUser,Company company, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		String subject = "PROVIDER INFORMATION AMENDMENT OUTCOME: NOT APPROVED";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+formUser.getTitle().getDescription()+" " +formUser.getFirstName() + " " + formUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The request to amend information was not approved for the following reason(s):"
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ ""+convertToHTML(selectedRejectReason)+""
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please be advised that should you wish to re-submit the request, "
				+ "you must complete the amendment details/information again."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Kindly contact the merSETA Head Office for additional information/assistance."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "merSETA Administration"
				+ "</p>";
		GenericUtility.sendMail(formUser.getEmail(), subject, mssg, null);

	}
	
	/* Provider Monitoring Filters for provider monitoring Start*/
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> providerMonitoringTrainingProviderApplications(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> parameters, List<AccreditationApplicationTypeEnum> accreditationApplicationTypeEnumList, Date date, ApprovalEnum status) throws Exception {
		String hql = "select o from TrainingProviderApplication o where o.approvalStatus = :approvedStatus and DATE(o.expiriyDate) > DATE(:date) ";
		if (accreditationApplicationTypeEnumList != null && accreditationApplicationTypeEnumList.size() != 0) {
			hql += " and ( ";
			int count = 1;
			for (AccreditationApplicationTypeEnum accreditationApplicationTypeEnum : accreditationApplicationTypeEnumList) {
				if (count == accreditationApplicationTypeEnumList.size()) {
					hql += " o.accreditationApplicationTypeEnum = :applicationType" + count + " ";
					parameters.put("applicationType" + count, accreditationApplicationTypeEnum);
				} else {
					hql += " o.accreditationApplicationTypeEnum = :applicationType" + count + "  or ";
					parameters.put("applicationType" + count, accreditationApplicationTypeEnum);
				}
				count++;
			}
			hql += " ) ";
		}
		parameters.put("approvedStatus", status);
		parameters.put("date", date);
		return populateAdditionalInformationMonitoringList((List<TrainingProviderApplication>) dao.sortAndFilterWhereTPInfo(first, pageSize, sortField, sortOrder, parameters, hql));
	}
	
	public int countProviderMonitoringTrainingProviderApplications(Map<String, Object> filters, List<AccreditationApplicationTypeEnum> accreditationApplicationTypeEnumList) {
		String hql = "select count(o) from TrainingProviderApplication o where o.approvalStatus = :approvedStatus and DATE(o.expiriyDate) > DATE(:date) ";
		if (accreditationApplicationTypeEnumList != null && accreditationApplicationTypeEnumList.size() != 0) {
			hql += " and ( ";
			int count = 1;
			for (AccreditationApplicationTypeEnum accreditationApplicationTypeEnum : accreditationApplicationTypeEnumList) {
				if (count == accreditationApplicationTypeEnumList.size()) {
					hql += " o.accreditationApplicationTypeEnum = :applicationType" + count + " ";
				} else {
					hql += " o.accreditationApplicationTypeEnum = :applicationType" + count + "  or ";
				}
				count++;
			}
			hql += " ) ";
		}
		return dao.countWhereTPInfo(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> providerMonitoringTrainingProviderApplicationsByRegion(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> parameters, Long regionId, List<AccreditationApplicationTypeEnum> accreditationApplicationTypeEnumList, Date date, ApprovalEnum status) throws Exception {
		String hql = "select o from TrainingProviderApplication o where o.approvalStatus = :approvedStatus and DATE(o.expiriyDate) > DATE(:date) ";
		if (accreditationApplicationTypeEnumList != null && accreditationApplicationTypeEnumList.size() != 0) {
			hql += " and ( ";
			int count = 1;
			for (AccreditationApplicationTypeEnum accreditationApplicationTypeEnum : accreditationApplicationTypeEnumList) {
				if (count == accreditationApplicationTypeEnumList.size()) {
					hql += " o.accreditationApplicationTypeEnum = :applicationType" + count + " ";
					parameters.put("applicationType" + count, accreditationApplicationTypeEnum);
				} else {
					hql += " o.accreditationApplicationTypeEnum = :applicationType" + count + "  or ";
					parameters.put("applicationType" + count, accreditationApplicationTypeEnum);
				}
				count++;
			}
			hql += " ) ";
		}
//		hql += " and o.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId) ";
		hql += " and ( "
				+ "(o.trainingSite is null and o.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId)) "
				+ " or "
				+ "(o.trainingSite is not null and o.trainingSite.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId)) "
				+ ")";
		parameters.put("regionId", regionId);
		parameters.put("approvedStatus", status);
		parameters.put("date", date);
		return populateAdditionalInformationMonitoringList((List<TrainingProviderApplication>) dao.sortAndFilterWhereTPInfo(first, pageSize, sortField, sortOrder, parameters, hql));
	}
	
	public int countProviderMonitoringTrainingProviderCompaniesByRegion(Map<String, Object> filters, List<AccreditationApplicationTypeEnum> accreditationApplicationTypeEnumList) {
		String hql = " select count(o) from TrainingProviderApplication o where o.approvalStatus = :approvedStatus and DATE(o.expiriyDate) > DATE(:date) ";
		if (accreditationApplicationTypeEnumList != null && accreditationApplicationTypeEnumList.size() != 0) {
			hql += " and ( ";
			int count = 1;
			for (AccreditationApplicationTypeEnum accreditationApplicationTypeEnum : accreditationApplicationTypeEnumList) {
				if (count == accreditationApplicationTypeEnumList.size()) {
					hql += " o.accreditationApplicationTypeEnum = :applicationType" + count + " ";
				} else {
					hql += " o.accreditationApplicationTypeEnum = :applicationType" + count + "  or ";
				}
				count++;
			}
			hql += " ) ";
		}
//		hql += " and o.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId) ";
		hql += " and ( "
				+ "(o.trainingSite is null and o.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId)) "
				+ " or "
				+ "(o.trainingSite is not null and o.trainingSite.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId)) "
				+ ")";
		return dao.countWhereTPInfo(filters, hql);
	}
	
	public List<TrainingProviderApplication> populateAdditionalInformationMonitoringList(List<TrainingProviderApplication> trainingProviderApplicationList) throws Exception{
		for (TrainingProviderApplication trainingProviderApplication : trainingProviderApplicationList) {
			populateAdditionalInformationMonitoring(trainingProviderApplication);
		}
		return trainingProviderApplicationList;
	}
	
	private TrainingProviderApplication populateAdditionalInformationMonitoring(TrainingProviderApplication trainingProviderApplication) throws Exception {
		if (trainingProviderApplication.getCompany() != null && trainingProviderApplication.getCompany().getId() != null) {
			if (companyService == null) {
				companyService = new CompanyService();
			}
			// populates region
			companyService.populateReportInfo(trainingProviderApplication.getCompany());
			
			// populate provider region site
			if (trainingProviderApplication.getTrainingSite() != null && trainingProviderApplication.getTrainingSite().getId() != null) {
				TrainingSiteService.instance().resolveTrainingSiteRegion(trainingProviderApplication.getTrainingSite());
			}
		}
		return trainingProviderApplication;
	}
	/* Provider Monitoring Filters for provider monitoring END*/
	
	/* Provider Audit Filters START */
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> providerAuditTrainingProviderApplications(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> parameters, List<AccreditationApplicationTypeEnum> accreditationApplicationTypeEnumList, Date date, ApprovalEnum status,TrainingProviderFilterEnum trainingProviderFilterEnum) throws Exception {
		String hql = "select o from TrainingProviderApplication o where o.approvalStatus = :approvedStatus and DATE(o.expiriyDate) > DATE(:date) ";
		if (accreditationApplicationTypeEnumList != null && accreditationApplicationTypeEnumList.size() != 0) {
			hql += " and ( ";
			int count = 1;
			for (AccreditationApplicationTypeEnum accreditationApplicationTypeEnum : accreditationApplicationTypeEnumList) {
				if (count == accreditationApplicationTypeEnumList.size()) {
					hql += " o.accreditationApplicationTypeEnum = :applicationType" + count + " ";
					parameters.put("applicationType" + count, accreditationApplicationTypeEnum);
				} else {
					hql += " o.accreditationApplicationTypeEnum = :applicationType" + count + "  or ";
					parameters.put("applicationType" + count, accreditationApplicationTypeEnum);
				}
				count++;
			}
			hql += " ) ";
		};
		switch (trainingProviderFilterEnum) {
		case NO_FILTER:
			// no action required	
			break;
		case EXPIRE_FILTER:
			// 12 months before expiry date
			hql += " and (DATE_FORMAT(o.expiriyDate, '%Y%m%d') - DATE_FORMAT(current_date(), '%Y%m%d')) > 0 and (DATE_FORMAT(o.expiriyDate, '%Y%m%d') - DATE_FORMAT(current_date(), '%Y%m%d')) < 10000";
			break;
		case NO_VISIT_FILTER:
			hql += " and ( (select count(x) from TrainingProviderMonitoring x where x.trainingProviderApplication.id = o.id and x.audit = false ) < 1 )";
//			hql += " and ( (DATE_FORMAT(o.startDate, '%Y%m%d') + 10000) < (select MIN(DATE_FORMAT(x.monitoringDate, '%Y%m%d')) from TrainingProviderMonitoring x where x.trainingProviderApplication.id = o.id ) )";
			break;
		case TWO_IN_FIVE_FILTER:
			hql += " and ( (select count(x) from TrainingProviderMonitoring x where x.trainingProviderApplication.id = o.id and x.audit = true ) < 2 )";
			break;
		default:
			break;
		}
		parameters.put("approvedStatus", status);
		parameters.put("date", date);
		return populateAdditionalInformationAuditList((List<TrainingProviderApplication>) dao.sortAndFilterWhereTPInfo(first, pageSize, sortField, sortOrder, parameters, hql));
	}
		
	public int countProviderAuditTrainingProviderApplications(Map<String, Object> filters, List<AccreditationApplicationTypeEnum> accreditationApplicationTypeEnumList, TrainingProviderFilterEnum trainingProviderFilterEnum) {
		String hql = "select count(o) from TrainingProviderApplication o where o.approvalStatus = :approvedStatus and DATE(o.expiriyDate) > DATE(:date) ";
		if (accreditationApplicationTypeEnumList != null && accreditationApplicationTypeEnumList.size() != 0) {
			hql += " and ( ";
			int count = 1;
			for (AccreditationApplicationTypeEnum accreditationApplicationTypeEnum : accreditationApplicationTypeEnumList) {
				if (count == accreditationApplicationTypeEnumList.size()) {
					hql += " o.accreditationApplicationTypeEnum = :applicationType" + count + " ";
				} else {
					hql += " o.accreditationApplicationTypeEnum = :applicationType" + count + "  or ";
				}
				count++;
			}
			hql += " ) ";
		};
		switch (trainingProviderFilterEnum) {
		case NO_FILTER:
			// no action required	
			break;
		case EXPIRE_FILTER:
			hql += " and (DATE_FORMAT(o.expiriyDate, '%Y%m%d') - DATE_FORMAT(current_date(), '%Y%m%d')) > 0 and (DATE_FORMAT(o.expiriyDate, '%Y%m%d') - DATE_FORMAT(current_date(), '%Y%m%d')) < 10000";
			break;
		case NO_VISIT_FILTER:
			hql += " and ( (select count(x) from TrainingProviderMonitoring x where x.trainingProviderApplication.id = o.id and x.audit = false ) < 1 )";
//			hql += " and ( (DATE_FORMAT(o.startDate, '%Y%m%d') + 10000) < (select MIN(DATE_FORMAT(x.monitoringDate, '%Y%m%d')) from TrainingProviderMonitoring x where x.trainingProviderApplication.id = o.id ) )";
			break;
		case TWO_IN_FIVE_FILTER:
			hql += " and ( (select count(x) from TrainingProviderMonitoring x where x.trainingProviderApplication.id = o.id and x.audit = true ) < 2 )";
			break;
		default:
			break;
		}
		return dao.countWhereTPInfo(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> providerAuditTrainingProviderApplicationsByRegion(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> parameters, Long regionId, List<AccreditationApplicationTypeEnum> accreditationApplicationTypeEnumList, Date date, ApprovalEnum status, TrainingProviderFilterEnum trainingProviderFilterEnum) throws Exception {
		String hql = "select o from TrainingProviderApplication o where o.approvalStatus = :approvedStatus and DATE(o.expiriyDate) > DATE(:date) ";
		if (accreditationApplicationTypeEnumList != null && accreditationApplicationTypeEnumList.size() != 0) {
			hql += " and ( ";
			int count = 1;
			for (AccreditationApplicationTypeEnum accreditationApplicationTypeEnum : accreditationApplicationTypeEnumList) {
				if (count == accreditationApplicationTypeEnumList.size()) {
					hql += " o.accreditationApplicationTypeEnum = :applicationType" + count + " ";
					parameters.put("applicationType" + count, accreditationApplicationTypeEnum);
				} else {
					hql += " o.accreditationApplicationTypeEnum = :applicationType" + count + "  or ";
					parameters.put("applicationType" + count, accreditationApplicationTypeEnum);
				}
				count++;
			}
			hql += " ) ";
		};
		hql += " and ( "
				+ "(o.trainingSite is null and o.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId)) "
				+ " or "
				+ "(o.trainingSite is not null and o.trainingSite.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId)) "
				+ ")";
		switch (trainingProviderFilterEnum) {
		case NO_FILTER:
			// no action required	
			break;
		case EXPIRE_FILTER:
			// 12 months before expiry date
			hql += " and (DATE_FORMAT(o.expiriyDate, '%Y%m%d') - DATE_FORMAT(current_date(), '%Y%m%d')) > 0 and (DATE_FORMAT(o.expiriyDate, '%Y%m%d') - DATE_FORMAT(current_date(), '%Y%m%d')) < 10000";
			break;
		case NO_VISIT_FILTER:
			hql += " and ( (select count(x) from TrainingProviderMonitoring x where x.trainingProviderApplication.id = o.id and x.audit = false ) < 1 )";
//			hql += " and ( (DATE_FORMAT(o.startDate, '%Y%m%d') + 10000) < (select MIN(DATE_FORMAT(x.monitoringDate, '%Y%m%d')) from TrainingProviderMonitoring x where x.trainingProviderApplication.id = o.id ) )";
			break;
		case TWO_IN_FIVE_FILTER:
			hql += " and ( (select count(x) from TrainingProviderMonitoring x where x.trainingProviderApplication.id = o.id and x.audit = true ) < 2 )";
			break;
		default:
			break;
		}
		parameters.put("approvedStatus", status);
		parameters.put("date", date);
		parameters.put("regionId", regionId);
		return populateAdditionalInformationMonitoringList((List<TrainingProviderApplication>) dao.sortAndFilterWhereTPInfo(first, pageSize, sortField, sortOrder, parameters, hql));
	}
	
	public int countProviderAuditTrainingProviderApplicationsByRegion(Map<String, Object> filters, List<AccreditationApplicationTypeEnum> accreditationApplicationTypeEnumList, TrainingProviderFilterEnum trainingProviderFilterEnum) {
		String hql = "select count(o) from TrainingProviderApplication o where o.approvalStatus = :approvedStatus and DATE(o.expiriyDate) > DATE(:date) ";
		if (accreditationApplicationTypeEnumList != null && accreditationApplicationTypeEnumList.size() != 0) {
			hql += " and ( ";
			int count = 1;
			for (AccreditationApplicationTypeEnum accreditationApplicationTypeEnum : accreditationApplicationTypeEnumList) {
				if (count == accreditationApplicationTypeEnumList.size()) {
					hql += " o.accreditationApplicationTypeEnum = :applicationType" + count + " ";
				} else {
					hql += " o.accreditationApplicationTypeEnum = :applicationType" + count + "  or ";
				}
				count++;
			}
			hql += " ) ";
		};
		hql += " and ( "
				+ "(o.trainingSite is null and o.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId)) "
				+ " or "
				+ "(o.trainingSite is not null and o.trainingSite.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId)) "
				+ ")";
		switch (trainingProviderFilterEnum) {
		case NO_FILTER:
			// no action required	
			break;
		case EXPIRE_FILTER:
			hql += " and (DATE_FORMAT(o.expiriyDate, '%Y%m%d') - DATE_FORMAT(current_date(), '%Y%m%d')) > 0 and (DATE_FORMAT(o.expiriyDate, '%Y%m%d') - DATE_FORMAT(current_date(), '%Y%m%d')) < 10000";
			break;
		case NO_VISIT_FILTER:
			hql += " and ( (select count(x) from TrainingProviderMonitoring x where x.trainingProviderApplication.id = o.id and x.audit = false ) < 1 )";
//			hql += " and ( (DATE_FORMAT(o.startDate, '%Y%m%d') + 10000) < (select MIN(DATE_FORMAT(x.monitoringDate, '%Y%m%d')) from TrainingProviderMonitoring x where x.trainingProviderApplication.id = o.id ) )";
			break;
		case TWO_IN_FIVE_FILTER:
			hql += " and ( (select count(x) from TrainingProviderMonitoring x where x.trainingProviderApplication.id = o.id and x.audit = true ) < 2 )";
			break;
		default:
			break;
		}
		return dao.countWhereTPInfo(filters, hql);
	}
	
	public List<TrainingProviderApplication> populateAdditionalInformationAuditList(List<TrainingProviderApplication> trainingProviderApplicationList) throws Exception{
		for (TrainingProviderApplication trainingProviderApplication : trainingProviderApplicationList) {
			populateAdditionalInformationAudit(trainingProviderApplication);
		}
		return trainingProviderApplicationList;
	}
	
	private TrainingProviderApplication populateAdditionalInformationAudit(TrainingProviderApplication trainingProviderApplication) throws Exception {
		if (trainingProviderApplication.getCompany() != null && trainingProviderApplication.getCompany().getId() != null) {
			if (companyService == null) {
				companyService = new CompanyService();
			}
			// populates region
			companyService.populateReportInfo(trainingProviderApplication.getCompany());
			
			// resolve training site region
			if (trainingProviderApplication.getTrainingSite() != null) {
				TrainingSiteService.instance().resolveTrainingSiteRegion(trainingProviderApplication.getTrainingSite());
			}
			
		}
		return trainingProviderApplication;
	}
	/* Provider Audit Filters END */

	public void submitNewContactPerson(TrainingProviderApplication selectedTrainingProviderApplication, Users user,Users currentUser, ChangeReason changeReason) throws Exception {
		CompanyUsers cu=new CompanyUsers();
		RegisterService registerService = new RegisterService();
		UsersService usersService=new UsersService();
		cu.setDesignation(user.getDesignation());
		if (user.getId() == null) {
			user.setExistingUser(false);
			registerService.register(user, true);
		} else {
			user.setExistingUser(true);
			usersService.update(user);
		}
		cu.setUser(user);
		cu.setTargetClass(selectedTrainingProviderApplication.getClass().getName());
		cu.setTargetKey(selectedTrainingProviderApplication.getId());
		cu.setApprovalStatus(ApprovalEnum.PendingApproval);
		cu.setCompanyUserType(ConfigDocProcessEnum.TP);
		cu.setCompany(selectedTrainingProviderApplication.getCompany());
		cu.setExistingUser(user.isExistingUser());
		companyUsersService.create(cu);
		selectedTrainingProviderApplication.setApprovalStatus(ApprovalEnum.PendingChangeApproval);
		update(selectedTrainingProviderApplication);
		ChangeReasonService.instance().createChangeReason(cu.getId(), cu.getClass().getName(),changeReason);
		String desc="";
		TasksService.instance().findFirstInProcessAndCreateTask(desc, currentUser, cu.getId(), cu.getClass().getName(), true, ConfigDocProcessEnum.NEW_SDP_CONTACT_PERSON, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
		
	}
	

	public void submitNewAssessorMod(TrainingProviderApplication selectedTrainingProviderApplication, Users user,Users currentUser, ChangeReason changeReason) throws Exception {
		CompanyUsers cu=new CompanyUsers();
		UsersService usersService=new UsersService();
		cu.setAssessorModType(user.getAssessorModType());
		if (user.getId() == null) {
			user.setExistingUser(false);
		} else {
			user.setExistingUser(true);
			usersService.update(user);
		}
		cu.setUser(user);
		cu.setTargetClass(selectedTrainingProviderApplication.getClass().getName());
		cu.setTargetKey(selectedTrainingProviderApplication.getId());
		cu.setApprovalStatus(ApprovalEnum.PendingApproval);
		cu.setCompanyUserType(ConfigDocProcessEnum.TP);
		cu.setCompany(selectedTrainingProviderApplication.getCompany());
		cu.setExistingUser(user.isExistingUser());
		companyUsersService.create(cu);
		selectedTrainingProviderApplication.setApprovalStatus(ApprovalEnum.PendingChangeApproval);
		update(selectedTrainingProviderApplication);
		ChangeReasonService.instance().createChangeReason(cu.getId(), cu.getClass().getName(),changeReason);
		String desc="";
		TasksService.instance().findFirstInProcessAndCreateTask(desc, currentUser, cu.getId(), cu.getClass().getName(), true, ConfigDocProcessEnum.NEW_SDP_ASSESSOR_MODERATOR, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
		//Sending Acknowledgement email 
		sendNewAssessorModAcknowledgementEmail(currentUser, user, cu.getAssessorModType().getFriendlyName());
	}
	
	public void submitContactPersonUpdate(TrainingProviderApplication selectedTrainingProviderApplication, Users user,CompanyUsers cu,Users currentUser, ChangeReason changeReason) throws Exception {
		
		CompanyUsersHistoryService companyUsersHistoryService = new CompanyUsersHistoryService();
		// Creating company user history
		companyUsersHistoryService.createHistory(cu.getId());
		UsersService usersService=new UsersService();
		//Creating User History
		UserChangeRequestService userChangeRequestService=new UserChangeRequestService();
	    Users oldUser=usersService.findByKey(user.getId());
	    usersService.updateUserValidation(oldUser, user);
	    
		UserChangeRequest userChangeRequest=new UserChangeRequest(oldUser);
		userChangeRequest.setTargetClass(cu.getClass().getName());
		userChangeRequest.setTargetKey(cu.getId());
		userChangeRequest.setApprovalStatus(ApprovalEnum.PendingApproval);
		userChangeRequestService.create(userChangeRequest);
		
		usersService.update(user);
		cu.setTargetClass(selectedTrainingProviderApplication.getClass().getName());
		cu.setTargetKey(selectedTrainingProviderApplication.getId());
		cu.setApprovalStatus(ApprovalEnum.PendingApproval);
		cu.setDesignation(user.getDesignation());
		companyUsersService.create(cu);
		
		selectedTrainingProviderApplication.setApprovalStatus(ApprovalEnum.PendingChangeApproval);
		update(selectedTrainingProviderApplication);
		
		ChangeReasonService.instance().createChangeReason(cu.getId(), cu.getClass().getName(),changeReason);
		
		String desc="";
		TasksService.instance().findFirstInProcessAndCreateTask(desc, currentUser, cu.getId(), cu.getClass().getName(), true, ConfigDocProcessEnum.UPDATE_SDP_CONTACT_PERSON, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
		//Sending Acknowledgement email 
		sendContactPersonUpdateAcknowledgementEmail(currentUser);
	}

	
	public void approveNewContactPersonTask(CompanyUsers companyUser, Tasks task, Users activeUser,TrainingProviderApplication trainingProviderApplication) throws Exception {
		CompanyUsersService companyUsersService=new CompanyUsersService();
		companyUser.setApprovalStatus(ApprovalEnum.Approved);
		companyUsersService.update(companyUser);
		trainingProviderApplication.setApprovalStatus(ApprovalEnum.Approved);
		update(trainingProviderApplication);
		TasksService.instance().completeTask(task);
		approveNewContactPersonEmail(trainingProviderApplication.getUsers(), companyUser.getUser());
		
	}
	
	public void approveNewAssessoModTask(CompanyUsers companyUser, Tasks task, Users activeUser,TrainingProviderApplication trainingProviderApplication) throws Exception {
		CompanyUsersService companyUsersService=new CompanyUsersService();
		companyUser.setApprovalStatus(ApprovalEnum.Approved);
		companyUsersService.update(companyUser);
		trainingProviderApplication.setApprovalStatus(ApprovalEnum.Approved);
		update(trainingProviderApplication);
		TasksService.instance().completeTask(task);
		approveNewAssessorModEmail(trainingProviderApplication.getUsers(), companyUser.getUser(),companyUser.getAssessorModType().getFriendlyName());
		
	}
	
	public void approveUpdateContactPersonTask(CompanyUsers companyUser, Tasks task, Users activeUser,TrainingProviderApplication trainingProviderApplication, UserChangeRequest userChangeRequest) throws Exception {
		CompanyUsersService companyUsersService=new CompanyUsersService();
		UserChangeRequestService userChangeRequestService=new UserChangeRequestService();
		companyUser.setApprovalStatus(ApprovalEnum.Approved);
		userChangeRequest.setApprovalStatus(ApprovalEnum.Approved);
		userChangeRequestService.update(userChangeRequest);
		companyUsersService.update(companyUser);
		trainingProviderApplication.setApprovalStatus(ApprovalEnum.Approved);
		update(trainingProviderApplication);
		TasksService.instance().completeTask(task);
		approveUpdateContactPersonEmail(trainingProviderApplication.getUsers(), companyUser.getUser());
		
	}
	
	public void approveDeleteContactPersonTask(CompanyUsers companyUser, Tasks task, Users activeUser,TrainingProviderApplication trainingProviderApplication) throws Exception {
		CompanyUsersService companyUsersService=new CompanyUsersService();
		companyUser.setApprovalStatus(ApprovalEnum.Approved);
		companyUsersService.delete(companyUser);
		trainingProviderApplication.setApprovalStatus(ApprovalEnum.Approved);
		update(trainingProviderApplication);
		TasksService.instance().completeTask(task);
		approveDeleteContactPersonEmail(trainingProviderApplication.getUsers(), companyUser.getUser());
		
	}
	
	public void approveDeleteAssessorModTask(CompanyUsers companyUser, Tasks task, Users activeUser,TrainingProviderApplication trainingProviderApplication) throws Exception {
		CompanyUsersService companyUsersService=new CompanyUsersService();
		companyUser.setApprovalStatus(ApprovalEnum.Approved);
		companyUsersService.delete(companyUser);
		trainingProviderApplication.setApprovalStatus(ApprovalEnum.Approved);
		update(trainingProviderApplication);
		TasksService.instance().completeTask(task);
		approveDeleteAssessorModEmail(trainingProviderApplication.getUsers(), companyUser.getUser(),companyUser.getAssessorModType().getFriendlyName());
		
	}
	
	public void rejectNewContactTask(CompanyUsers companyUser, Tasks task, Users activeUser,TrainingProviderApplication trainingProviderApplication, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		CompanyUsersService companyUsersService=new CompanyUsersService();
		companyUser.setApprovalStatus(ApprovalEnum.Rejected);
		trainingProviderApplication.setApprovalStatus(ApprovalEnum.Approved);
		update(trainingProviderApplication);
		companyUsersService.update(companyUser);
		TasksService.instance().completeTask(task);
		// Creating RejectReason
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyUser.getId(), companyUser.getClass().getName(), selectedRejectReason, activeUser, ConfigDocProcessEnum.NEW_SDP_CONTACT_PERSON);
		for(RejectReasonMultipleSelection rrm:rrmList){
			if(rrm !=null){
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		rejectNewContactPersonEmail(trainingProviderApplication.getUsers(), companyUser.getUser(),selectedRejectReason);
		
		//Removing Company User
		companyUsersService.delete(companyUser);
		
	}
	
	public void rejectNewAssessorModTask(CompanyUsers companyUser, Tasks task, Users activeUser,TrainingProviderApplication trainingProviderApplication, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		CompanyUsersService companyUsersService=new CompanyUsersService();
		companyUser.setApprovalStatus(ApprovalEnum.Rejected);
		trainingProviderApplication.setApprovalStatus(ApprovalEnum.Approved);
		update(trainingProviderApplication);
		companyUsersService.update(companyUser);
		TasksService.instance().completeTask(task);
		// Creating RejectReason
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyUser.getId(), companyUser.getClass().getName(), selectedRejectReason, activeUser, ConfigDocProcessEnum.NEW_SDP_CONTACT_PERSON);
		for(RejectReasonMultipleSelection rrm:rrmList){
			if(rrm !=null){
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		rejectNewAssessorModEmail(trainingProviderApplication.getUsers(), companyUser.getUser(),selectedRejectReason,companyUser.getAssessorModType().getFriendlyName());
		
		//Removing Company User
		companyUsersService.delete(companyUser);
		
	}
	
	
	
	public void rejectUpdateContactPersonTask(CompanyUsers companyUser, Tasks task, Users activeUser,TrainingProviderApplication trainingProviderApplication, ArrayList<RejectReasons> selectedRejectReason, UserChangeRequest userChangeRequest, CompanyUsers companyUsersHistory) throws Exception {
		CompanyUsersService companyUsersService=new CompanyUsersService();
		UsersService usersService=new UsersService();
		UserChangeRequestService userChangeRequestService=new UserChangeRequestService();
		Users contactPerson=companyUser.getUser();
		Long tempContactPersonID=contactPerson.getId();
		Long tempCompanyUserID=companyUser.getId();
		BeanUtils.copyProperties(contactPerson, userChangeRequest);
		contactPerson.setId(tempContactPersonID);
		BeanUtils.copyProperties(companyUser, companyUsersHistory);
		companyUser.setId(tempCompanyUserID);
		userChangeRequest.setApprovalStatus(ApprovalEnum.Approved);
		userChangeRequestService.update(userChangeRequest);
		usersService.update(contactPerson);
		companyUser.setApprovalStatus(ApprovalEnum.Approved);
		trainingProviderApplication.setApprovalStatus(ApprovalEnum.Approved);
		update(trainingProviderApplication);
		companyUsersService.update(companyUser);
		TasksService.instance().completeTask(task);
		// Creating RejectReason
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyUser.getId(), companyUser.getClass().getName(), selectedRejectReason, activeUser, ConfigDocProcessEnum.NEW_SDP_CONTACT_PERSON);
		for(RejectReasonMultipleSelection rrm:rrmList){
			if(rrm !=null){
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		rejectUpdateContactPersonEmail(trainingProviderApplication.getUsers(), companyUser.getUser(),selectedRejectReason);
		
	}
	
	public void rejectDeleteContactPersonTask(CompanyUsers companyUser, Tasks task, Users activeUser,TrainingProviderApplication trainingProviderApplication, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		CompanyUsersService companyUsersService=new CompanyUsersService();
		companyUser.setApprovalStatus(ApprovalEnum.Approved);
		trainingProviderApplication.setApprovalStatus(ApprovalEnum.Approved);
		update(trainingProviderApplication);
		companyUsersService.update(companyUser);
		TasksService.instance().completeTask(task);
		// Creating RejectReason
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyUser.getId(), companyUser.getClass().getName(), selectedRejectReason, activeUser, ConfigDocProcessEnum.NEW_SDP_CONTACT_PERSON);
		for(RejectReasonMultipleSelection rrm:rrmList){
			if(rrm !=null){
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		rejectDeleteContactPersonEmail(trainingProviderApplication.getUsers(), companyUser.getUser(),selectedRejectReason);
		
	}
	
	public void rejectDeleteAssessorModTask(CompanyUsers companyUser, Tasks task, Users activeUser,TrainingProviderApplication trainingProviderApplication, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		CompanyUsersService companyUsersService=new CompanyUsersService();
		companyUser.setApprovalStatus(ApprovalEnum.Approved);
		trainingProviderApplication.setApprovalStatus(ApprovalEnum.Approved);
		update(trainingProviderApplication);
		companyUsersService.update(companyUser);
		TasksService.instance().completeTask(task);
		// Creating RejectReason
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyUser.getId(), companyUser.getClass().getName(), selectedRejectReason, activeUser, ConfigDocProcessEnum.NEW_SDP_CONTACT_PERSON);
		for(RejectReasonMultipleSelection rrm:rrmList){
			if(rrm !=null){
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		rejectDeleteAssessorModEmail(trainingProviderApplication.getUsers(), companyUser.getUser(),selectedRejectReason,companyUser.getAssessorModType().getFriendlyName());
		
	}
	
	public void createRemoveSDPConatctPersonTask(TrainingProviderApplication selectedTrainingProviderApplication, Users activeUser, CompanyUsers cu, ChangeReason changeReason) throws Exception {
		cu.setTargetClass(selectedTrainingProviderApplication.getClass().getName());
		cu.setTargetKey(selectedTrainingProviderApplication.getId());
		cu.setApprovalStatus(ApprovalEnum.PendingApproval);
		companyUsersService.create(cu);
		selectedTrainingProviderApplication.setApprovalStatus(ApprovalEnum.PendingChangeApproval);
		update(selectedTrainingProviderApplication);
		ChangeReasonService.instance().createChangeReason(cu.getId(), cu.getClass().getName(),changeReason);
		String desc="";
		TasksService.instance().findFirstInProcessAndCreateTask(desc, activeUser, cu.getId(), cu.getClass().getName(), true, ConfigDocProcessEnum.REMOVE_SDP_CONTACT_PERSON, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
		//Sending Acknowledgement Email
		removeContactPersonAcknowledgementEmail(activeUser, cu.getUser());
	}
	
	public void createRemoveSDPAssessorModTask(TrainingProviderApplication selectedTrainingProviderApplication, Users activeUser, CompanyUsers cu, ChangeReason changeReason) throws Exception {
		cu.setTargetClass(selectedTrainingProviderApplication.getClass().getName());
		cu.setTargetKey(selectedTrainingProviderApplication.getId());
		cu.setApprovalStatus(ApprovalEnum.PendingApproval);
		companyUsersService.create(cu);
		selectedTrainingProviderApplication.setApprovalStatus(ApprovalEnum.PendingChangeApproval);
		update(selectedTrainingProviderApplication);
		ChangeReasonService.instance().createChangeReason(cu.getId(), cu.getClass().getName(),changeReason);
		String desc="";
		TasksService.instance().findFirstInProcessAndCreateTask(desc, activeUser, cu.getId(), cu.getClass().getName(), true, ConfigDocProcessEnum.REMOVE_SDP_ASSESSOR_MODERATOR, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
		//Sending Acknowledgement Email
		removeAssessorModAcknowledgementEmail(activeUser, cu.getUser(), cu.getAssessorModType().getFriendlyName());
	}
	
	public void  rejectDeleteContactPersonEmail(Users tpUser,Users contactPerson, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		String idNum="";
		if(contactPerson.getRsaIDNumber() !=null && !contactPerson.getRsaIDNumber().isEmpty() && !contactPerson.getRsaIDNumber().equals("")){
			idNum=contactPerson.getRsaIDNumber() ;
		}
		else {
			idNum=contactPerson.getPassportNumber();
		}
		String subject = "SKILLS DEVELOPMENT PROVIDER CONTACT PERSON DEREGISTRATION: NOT APPROVED";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+tpUser.getTitle().getDescription()+" " +tpUser.getFirstName() + " " + tpUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The request to deregister "+contactPerson.getFirstName()+" "+contactPerson.getLastName()+" ("+idNum+") as a contact person was not approved."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ ""+convertToHTML(selectedRejectReason)+""
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please be advised that should you wish to re-submit the request, you must complete the deregistration application again."
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Kindly contact the merSETA Head Office for additional information/assistance."
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "merSETA Administration"
				+ "</p>";
		GenericUtility.sendMail(tpUser.getEmail(), subject, mssg, null);

	}
	
	public void  rejectDeleteAssessorModEmail(Users tpUser,Users contactPerson, ArrayList<RejectReasons> selectedRejectReason,String appType) throws Exception {
		String idNum="";
		if(contactPerson.getRsaIDNumber() !=null && !contactPerson.getRsaIDNumber().isEmpty() && !contactPerson.getRsaIDNumber().equals("")){
			idNum=contactPerson.getRsaIDNumber() ;
		}
		else {
			idNum=contactPerson.getPassportNumber();
		}
		String subject = "REQUEST TO REMOVE "+appType.toUpperCase()+" FROM PROVIDER PROFILE: NOT APPROVED";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+tpUser.getTitle().getDescription()+" " +tpUser.getFirstName() + " " + tpUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The request to remove "+contactPerson.getFirstName()+" "+contactPerson.getLastName()+" ("+idNum+") "
				+ "from the "+appType+" profile was not approved for the following reason(s):"
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ ""+convertToHTML(selectedRejectReason)+""
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please be advised that should you wish to re-submit "
				+ "the request, you must complete the request again."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Kindly contact the merSETA Head Office for additional "
				+ "information/assistance."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "merSETA Administration"
				+ "</p>";
		GenericUtility.sendMail(tpUser.getEmail(), subject, mssg, null);

	}
	
	public void  rejectNewContactPersonEmail(Users tpUser,Users contactPerson, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		String subject = "Skills Development Provider New Contact Person Outcome".toUpperCase();
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+tpUser.getTitle().getDescription()+" " +tpUser.getFirstName() + " " + tpUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Your new contact person ("+ contactPerson.getFirstName()+" "+contactPerson.getLastName() + ") has been rejected on "
				+ "the merSETA NSDMS system for the following reason(s):"
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ ""+convertToHTML(selectedRejectReason)+""
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "merSETA Administration"
				+ "</p>";
		GenericUtility.sendMail(tpUser.getEmail(), subject, mssg, null);

	}
	
	public void  rejectNewAssessorModEmail(Users tpUser,Users contactPerson, ArrayList<RejectReasons> selectedRejectReason,String appType) throws Exception {
		String idNum="";
		if(contactPerson.getRsaIDNumber() !=null && !contactPerson.getRsaIDNumber().isEmpty() && !contactPerson.getRsaIDNumber().equals("")){
			idNum=contactPerson.getRsaIDNumber() ;
		}
		else {
			idNum=contactPerson.getPassportNumber();
		}
		String subject = "REQUEST TO ADD "+appType.toUpperCase()+" TO PROVIDER PROFILE: NOT APPROVED";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+tpUser.getTitle().getDescription()+" " +tpUser.getFirstName() + " " + tpUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The request to add "+contactPerson.getFirstName()+" "+contactPerson.getLastName()+" ("+idNum+") "
				+ "to the "+appType+" profile was not approved for the following reason(s):"
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ ""+convertToHTML(selectedRejectReason)+""
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please be advised that should you wish to re-submit the "
				+ "request, you must complete the request again."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Kindly contact the merSETA Head Office for additional information/assistance."
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "merSETA Administration"
				+ "</p>";
		GenericUtility.sendMail(tpUser.getEmail(), subject, mssg, null);

	}
	
	public void  rejectUpdateContactPersonEmail(Users tpUser,Users contactPerson, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		String subject = "SKILLS DEVELOPMENT PROVIDER CONTACT PERSON DETAILS AMENDMENT: NOT APPROVED";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+tpUser.getTitle().getDescription()+" " +tpUser.getFirstName() + " " + tpUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The request to amend information was not approved for the following reason(s):"
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ ""+convertToHTML(selectedRejectReason)+""
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please be advised that should you wish to re-submit the request, "
				+ "you must complete the amendment contact person details/information again."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Kindly contact the merSETA Head Office for additional information/assistance."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "merSETA Administration"
				+ "</p>";
		GenericUtility.sendMail(tpUser.getEmail(), subject, mssg, null);

	}
	
	
	public void  approveNewContactPersonEmail(Users tpUser,Users contactPerson) throws Exception {
		String subject = "Skills Development Provider New Contact Person Outcome".toUpperCase();
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+tpUser.getTitle().getDescription()+" " +tpUser.getFirstName() + " " + tpUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Your new contact person ("+ contactPerson.getFirstName()+" "+contactPerson.getLastName() + ") has been approved on the merSETA NSDMS system."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "merSETA Administration"
				+ "</p>";
		GenericUtility.sendMail(tpUser.getEmail(), subject, mssg, null);

	}
	
	public void  approveNewAssessorModEmail(Users tpUser,Users contactPerson,String appType) throws Exception {
		String idNum="";
		if(contactPerson.getRsaIDNumber() !=null && !contactPerson.getRsaIDNumber().isEmpty() && !contactPerson.getRsaIDNumber().equals("")){
			idNum=contactPerson.getRsaIDNumber() ;
		}
		else {
			idNum=contactPerson.getPassportNumber();
		}
		String subject = "REQUEST TO "+appType.toUpperCase()+" TO PROVIDER PROFILE OUTCOME: APPROVED";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+tpUser.getTitle().getDescription()+" " +tpUser.getFirstName() + " " + tpUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The request to add "+contactPerson.getFirstName()+" "+contactPerson.getLastName()+" ("+idNum+") to the "+appType+" profile was approved."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Kindly contact the merSETA Head Office for additional information/assistance."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "merSETA Administration"
				+ "</p>";
		GenericUtility.sendMail(tpUser.getEmail(), subject, mssg, null);

	}
	
	public void  approveUpdateContactPersonEmail(Users tpUser,Users contactPerson) throws Exception {
		String subject = "SKILLS DEVELOPMENT PROVIDER CONTACT PERSON DETAILS AMENDMENT: APPROVED";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+tpUser.getTitle().getDescription()+" " +tpUser.getFirstName() + " " + tpUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The request to amend details of a contact person was approved."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Kindly note that the updated information will now be used "
				+ "going forward. Please be advised that should you wish to "
				+ "amend the information again, you must complete "
				+ "the amendment request again."
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Kindly contact the merSETA Head Office for additional information/assistance."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "merSETA Administration"
				+ "</p>";
		GenericUtility.sendMail(tpUser.getEmail(), subject, mssg, null);

	}
	
	public void  approveDeleteContactPersonEmail(Users tpUser,Users contactPerson) throws Exception {
		String idNum="";
		if(contactPerson.getRsaIDNumber() !=null && !contactPerson.getRsaIDNumber().isEmpty() && !contactPerson.getRsaIDNumber().equals("")){
			idNum=contactPerson.getRsaIDNumber() ;
		}
		else {
			idNum=contactPerson.getPassportNumber();
		}
		String subject = "SKILLS DEVELOPMENT PROVIDER CONTACT PERSON DEREGISTRATION: APPROVED";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+tpUser.getTitle().getDescription()+" " +tpUser.getFirstName() + " " + tpUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The request to deregister "+contactPerson.getFirstName()+" "+contactPerson.getLastName()+" ("+idNum+") as a contact person was approved."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Kindly note that the deregistered contact person will no longer have access to the company profile. "
				+ "Please be advised that should you wish to re-register the user in the future, you will be able to do so."
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Kindly contact the merSETA Head Office for additional information/assistance."
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "merSETA Administration"
				+ "</p>";
		GenericUtility.sendMail(tpUser.getEmail(), subject, mssg, null);

	}
	

	public void  approveDeleteAssessorModEmail(Users tpUser,Users contactPerson,String appType) throws Exception {
		String idNum="";
		if(contactPerson.getRsaIDNumber() !=null && !contactPerson.getRsaIDNumber().isEmpty() && !contactPerson.getRsaIDNumber().equals("")){
			idNum=contactPerson.getRsaIDNumber() ;
		}
		else {
			idNum=contactPerson.getPassportNumber();
		}
		String subject = "REQUEST TO REMOVE "+appType.toUpperCase()+" FROM PROVIDER PROFILE OUTCOME: APPROVED";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+tpUser.getTitle().getDescription()+" " +tpUser.getFirstName() + " " + tpUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The request to remove "+contactPerson.getFirstName()+" "+contactPerson.getLastName()+" ("+idNum+") "
				+ "from the "+appType+" profile was approved."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Kindly contact the merSETA Head Office for additional information/assistance."
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "merSETA Administration"
				+ "</p>";
		GenericUtility.sendMail(tpUser.getEmail(), subject, mssg, null);

	}
	
	public void  sendContactPersonUpdateAcknowledgementEmail(Users tpUser) throws Exception {
		String subject = "ACKNOWLEDGEMENT OF REQUEST TO AMEND SKILLS DEVELOPMENT PROVIDER CONTACT PERSON DETAILS";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+tpUser.getTitle().getDescription()+" " +tpUser.getFirstName() + " " + tpUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The request to amend information submitted on "+HAJConstants.sdfDateMonthYear.format(new Date())+" is hereby acknowledged."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please be advised that the review process may take up to five (5) "
				+ "working days and should any additional information be required, "
				+ "this will be communicated to you."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "merSETA Administration"
				+ "</p>";
		GenericUtility.sendMail(tpUser.getEmail(), subject, mssg, null);

	}
	
	public void  removeAssessorModAcknowledgementEmail(Users tpUser,Users contactPerson,String appType) throws Exception {
		String idNum="";
		if(contactPerson.getRsaIDNumber() !=null && !contactPerson.getRsaIDNumber().isEmpty() && !contactPerson.getRsaIDNumber().equals("")){
			idNum=contactPerson.getRsaIDNumber() ;
		}
		else {
			idNum=contactPerson.getPassportNumber();
		}
		String subject = "REQUEST TO REMOVE "+appType.toUpperCase()+" FROM PROVIDER PROFILE";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+tpUser.getTitle().getDescription()+" " +tpUser.getFirstName() + " " + tpUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The request to remove "+contactPerson.getFirstName()+"  "+contactPerson.getLastName()+" ("+idNum+") from the "+appType+" "
				+ "profile submitted on "+HAJConstants.sdfDateMonthYear.format(new Date())+" is hereby acknowledged."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please be advised that the review process may take up to five (5) "
				+ "working days and should any additional information be required, "
				+ "this will be communicated to you."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "merSETA Administration"
				+ "</p>";
		GenericUtility.sendMail(tpUser.getEmail(), subject, mssg, null);

	}
	
	public void  removeContactPersonAcknowledgementEmail(Users tpUser,Users contactPerson) throws Exception {
		String idNum="";
		if(contactPerson.getRsaIDNumber() !=null && !contactPerson.getRsaIDNumber().isEmpty() && !contactPerson.getRsaIDNumber().equals("")){
			idNum=contactPerson.getRsaIDNumber() ;
		}
		else {
			idNum=contactPerson.getPassportNumber();
		}
		String subject = "ACKNOWLEDGEMENT OF REQUEST TO DEREGISTER A SKILLS DEVELOPMENT PROVIDER CONTACT PERSON";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+tpUser.getTitle().getDescription()+" " +tpUser.getFirstName() + " " + tpUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The request to deregister "+contactPerson.getFirstName()+" "+contactPerson.getLastName()+" ("+idNum+") "
				+ "as a contact person submitted on "+HAJConstants.sdfDateMonthYear.format(new Date())+" is hereby acknowledged."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please be advised that the review process may take up to five (5) "
				+ "working days and should any additional information be required, "
				+ "this will be communicated to you."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "merSETA Administration"
				+ "</p>";
		GenericUtility.sendMail(tpUser.getEmail(), subject, mssg, null);

	}
	
	public void  sendNewAssessorModAcknowledgementEmail(Users tpUser,Users contactPerson, String appType) throws Exception {
		String idNum="";
		if(contactPerson.getRsaIDNumber() !=null && !contactPerson.getRsaIDNumber().isEmpty() && !contactPerson.getRsaIDNumber().equals("")){
			idNum=contactPerson.getRsaIDNumber() ;
		}
		else {
			idNum=contactPerson.getPassportNumber();
		}
		String subject = "REQUEST TO ADD "+appType.toUpperCase()+" TO PROVIDER PROFILE";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+tpUser.getTitle().getDescription()+" " +tpUser.getFirstName() + " " + tpUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The request to add "+contactPerson.getFirstName()+" "+contactPerson.getLastName()+" (ID/passport number) "
				+ "to the "+appType+" profile submitted on "+HAJConstants.sdfDateMonthYear.format(new Date())+" is hereby acknowledged."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please be advised that the review process may take up to five (5) "
				+ "working days and should any additional information be required, "
				+ "this will be communicated to you."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "merSETA Administration"
				+ "</p>";
		GenericUtility.sendMail(tpUser.getEmail(), subject, mssg, null);

	}

	@SuppressWarnings("unchecked")
	public TrainingProviderApplication  findByCompanyAndStatusAndType(Company company,ApprovalEnum approvalStatus, AccreditationApplicationTypeEnum accreditationApplicationTypeEnum) throws Exception {	
		return dao.findByCompanyAndStatusAndType(company, approvalStatus, accreditationApplicationTypeEnum);
	}
	
	public List<TrainingProviderApplication>  findByCompanyAndStatusAndTypeList(Company company,ApprovalEnum approvalStatus, AccreditationApplicationTypeEnum accreditationApplicationTypeEnum) throws Exception {	
		return dao.findByCompanyAndStatusAndTypeList(company, approvalStatus, accreditationApplicationTypeEnum);
	}
	
	public void resendStatmentOfQualificationToAllApprovedApplications() throws Exception {
		List<TrainingProviderApplication> allProviderApplications = findAllApprovedApplications();
		for (TrainingProviderApplication trainingProviderApplication : allProviderApplications) {
			List<CompanyQualifications> approvedQompanyQualifications=CompanyQualificationsService.instance().findByTargetClassAndTargetKeyAndAccept(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId(),true);
			List<CompanyUnitStandard> approvedUnitStandards=CompanyUnitStandardService.instance().findByTargetClassAndTargetKeyAndAccept(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId(),true);
			List<TrainingProviderSkillsProgramme> approvedNewSkillsProgramList=TrainingProviderSkillsProgrammeService.instance().findByTargetClassAndTargetKeyAndAccept(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId(),true);
			List<TrainingProviderSkillsSet> approvedSkillsSetList=TrainingProviderSkillsSetService.instance().findByTargetClassAndTargetKeyAndAccept(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId(),true);
			boolean sendNotfication = true;
			
			if (approvedQompanyQualifications.isEmpty() && approvedUnitStandards.isEmpty() && approvedNewSkillsProgramList.isEmpty() && approvedSkillsSetList.isEmpty()) {
				sendNotfication = false;
			}
			approvedQompanyQualifications = null;
			approvedNewSkillsProgramList = null;
			approvedUnitStandards = null;
			approvedSkillsSetList = null;
			if (Boolean.TRUE.equals(sendNotfication)) {
				resendStatmentOfQualification(trainingProviderApplication);
			}
		}
		allProviderApplications = null;
	}
	
	public List<TrainingProviderApplication> findAllApprovedApplications() throws Exception {
		return dao.findAllApprovedApplications();
	}
	
	public void resendStatmentOfQualification(TrainingProviderApplication tpApplication) throws Exception {
		/*if(tpApplication.getCertificateNumber()==null)
		{
			throw new Exception("Statement Of Qualifications is not available for this application");
		}*/
		List<CompanyQualifications> approvedQompanyQualifications=CompanyQualificationsService.instance().findByTargetClassAndTargetKeyAndAccept(tpApplication.getClass().getName(), tpApplication.getId(),true);
		List<CompanyUnitStandard> approvedUnitStandards=CompanyUnitStandardService.instance().findByTargetClassAndTargetKeyAndAccept(tpApplication.getClass().getName(), tpApplication.getId(),true);
		List<TrainingProviderSkillsProgramme> approvedNewSkillsProgramList=TrainingProviderSkillsProgrammeService.instance().findByTargetClassAndTargetKeyAndAccept(tpApplication.getClass().getName(), tpApplication.getId(),true);
		List<TrainingProviderSkillsSet> approvedSkillsSetList=TrainingProviderSkillsSetService.instance().findByTargetClassAndTargetKeyAndAccept(tpApplication.getClass().getName(), tpApplication.getId(),true);
		
		String barcode="";
		if(tpApplication.getUsers().getRsaIDNumber() !=null){barcode=tpApplication.getUsers().getRsaIDNumber();}
		else{barcode=tpApplication.getUsers().getPassportNumber();}
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		JasperService.addLogo(params);
		Users users = new Users();
		List<Users> listUsers=hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.Senior_Manager_Quality_Assurance_and_Partnerships_ID);
		if(listUsers!=null && listUsers.size()>0) {
			users = listUsers.get(0) ;
		}
		JasperService.addFormTemplateParams(params);
		params.put("tpApplication",tpApplication);
		params.put("company",tpApplication.getCompany());
		params.put("users", users);		
		params.put("company_id",tpApplication.getCompany().getId() );
		params.put("user_id",tpApplication.getUsers().getId() );
		params.put("registration_number",tpApplication.getCertificateNumber());
		params.put("status",tpApplication.getApprovalStatus().getFriendlyName());
		params.put("barcode",barcode);
		params.put("date", GenericUtility.sdf7.format(new Date()));
		params.put("approvalDate", GenericUtility.sdf7.format(tpApplication.getApprovedDate()));
		
		ArrayList<QualificationUnitStandardBean> qualificationUnitStandardList=new ArrayList<>();
		for(CompanyQualifications qual:approvedQompanyQualifications){
			 qualificationUnitStandardList.add(new QualificationUnitStandardBean(String.valueOf(qual.getQualification().getCode()), qual.getQualification().getDescription(), GenericUtility.sdf7.format(tpApplication.getApprovedDate())));
		 }
		 
		 for(CompanyUnitStandard us:approvedUnitStandards){
			 qualificationUnitStandardList.add(new QualificationUnitStandardBean(String.valueOf(us.getUnitStandard().getCode()), us.getUnitStandard().getTitle(), GenericUtility.sdf7.format(tpApplication.getApprovedDate())));
		 }
		 
		 for(TrainingProviderSkillsProgramme sp:approvedNewSkillsProgramList){
			 qualificationUnitStandardList.add(new QualificationUnitStandardBean(String.valueOf(sp.getSkillsProgram().getProgrammeID()), sp.getSkillsProgram().getDescription(), GenericUtility.sdf7.format(tpApplication.getApprovedDate())));
		 }
		 
		 for(TrainingProviderSkillsSet ss:approvedSkillsSetList){
			 qualificationUnitStandardList.add(new QualificationUnitStandardBean(String.valueOf(ss.getSkillsSet().getProgrammeID()), ss.getSkillsSet().getDescription(), GenericUtility.sdf7.format(tpApplication.getApprovedDate())));
		 }
		params.put("QualificationUnitStandardBeanDataSource", new JRBeanCollectionDataSource(qualificationUnitStandardList));
		String fileName=tpApplication.getCompany().getLevyNumber()+"_Statement_Of_Qualifications.pdf";
		
		
		ArrayList<AttachmentBean> attachmentBeans=new ArrayList<>();
		
		// JasperService.instance().createReportFromDBtoServletOutputStream("ETQ-TP-011-TPStatementOfQualificationsandorUnitStandards.jasper", params,fileName);
	    byte[] bites = JasperService.instance().convertJasperReportToByte("ETQ-TP-011-StatementOfQualificationsandorUnitStandards.jasper", params);
	    
	    AttachmentBean beanAttachment=new AttachmentBean();
	    beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename("Statement_Of_Qualifications_And_Or_UnitStandards.pdf");
		attachmentBeans.add(beanAttachment);
	    
	    
	    String subject = "STATEMENT OF QUALIFICATIONS AND/OR UNIT STANDARDS";
	    String msg =  "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #FULL_NAME#, </p>"
	    		+ " <p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please be advised that there was a system error that may have resulted in missing/incorrect details on the Statement of Qualifications/Unit Standards document issued when your accreditation/registration process was completed.</p>"
	    		+ " <p style=\"font-size:11.0pt;\";font-family:\"Arial\">Kindly be advised that this has been resolved and the correct details are now reflected on the Statement of Qualifications/Unit Standards document. Please be advised that the document is also available under your respective profile.</p>"
	    		+ " <p style=\"font-size:11.0pt;\";font-family:\"Arial\">For further clarification/support, kindly contact your Regional Office.</p>"
	    		+ " <p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>"
	    		+ " <p style=\"font-size:11.0pt;\";font-family:\"Arial\">Senior Manager: Quality Assurance and Partnerships</p>";
	    String userFullName = "";
	    if (tpApplication.getUsers() != null && tpApplication.getUsers().getTitle() != null) {
	    	userFullName = tpApplication.getUsers().getTitle().getDescription() + " ";
		}
	    userFullName += tpApplication.getUsers().getFirstName().trim() + " " + tpApplication.getUsers().getLastName().trim();
	    msg = msg.replaceAll("#FULL_NAME#", userFullName);
	    GenericUtility.sendMailWithAttachementTempWithLog(tpApplication.getUsers().getEmail(), subject, msg, attachmentBeans, null);
		
	}
	
	public Integer countTrainingProviderApplicationByComapnyID(Long companyID) throws Exception {
		return dao.countTrainingProviderApplicationByComapnyID(companyID);
	}
	
	public List<TrainingProviderApplication> findByCompanyIdAndStatus(Long companyId, ApprovalEnum approvalStatus) throws Exception {
		return dao.findByCompanyIdAndStatus(companyId, approvalStatus);
	}
	
	/*
	 * Locates SDP type by company user
	 */
	public SdpType locateSdpTypeByApplicationAndSessionUser(TrainingProviderApplication tpa, Users sessionUser) throws Exception{
		if (tpa.getCompany() == null && tpa.getCompany().getId() == null) {
			throw new Exception("Unable to locate company assigned, contact support with accreditation number and user trying to access!");
		}
//		List<CompanyUsers> companyUserList = companyUsersService.findByCompanyIdProcessAndUserId(tpa.getCompany().getId(), ConfigDocProcessEnum.TP, sessionUser.getId());
		List<CompanyUsers> companyUserList = companyUsersService.findByCompanyIdProcessAndUserIdWithSdpDesignation(tpa.getCompany().getId(), ConfigDocProcessEnum.TP, sessionUser.getId(), true);
		if (companyUserList.isEmpty()) {
			return null;
		} else {
			for (CompanyUsers companyUsers : companyUserList) {
				if (companyUsers.getDesignation() != null && companyUsers.getDesignation().getId() != null && companyUsers.getDesignation().getSdpDesignation()) {
					return SdpTypeService.instance().findByDesignationId(companyUsers.getDesignation().getId());
				}
			}
			return null;
		}
	}
	
	public Integer countLegacyProviderApllicationsLinkedToOpenApplications(Long legacyProviderAccId, List<ApprovalEnum> statusList) throws Exception {
		return dao.countLegacyProviderApllicationsLinkedToOpenApplications(legacyProviderAccId, statusList);
	}
	
	//String hql = "select count(o) from CompanyQualifications o where o.targetKey = :targetKey and o.targetClass = :targetClass and o.qualification.id =:qualificationId and o.accept =:accept";
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> allTrainingProviderApplicationCompanyQualifications(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long qualificationID, ApprovalEnum approvalStatus) throws Exception {
		String hql = "select o from TrainingProviderApplication o where o.id in (select x.targetKey from CompanyQualifications x where x.accept = true and x.qualification.id = :qualificationID) and o.approvalStatus = :approvalStatus";
		filters.put("qualificationID", qualificationID);
		filters.put("approvalStatus", approvalStatus);
		return (List<TrainingProviderApplication>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters,hql);
	}
	
	public int countAllTrainingProviderApplicationCompanyQualifications(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderApplication o where o.id in (select x.targetKey from CompanyQualifications x where x.accept = true and x.qualification.id = :qualificationID) and o.approvalStatus = :approvalStatus";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> allTrainingProviderApplicationCompanyQualificationsOld(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long qualificationID, ApprovalEnum approvalStatus) throws Exception {
		String hql = "select o from TrainingProviderApplication o where o.company.id in (select x.company.id from CompanyQualifications x where x.accept = true and x.qualification.id = :qualificationID) and o.approvalStatus = :approvalStatus";
		filters.put("qualificationID", qualificationID);
		filters.put("approvalStatus", approvalStatus);
		return (List<TrainingProviderApplication>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters,hql);
	}
	
	public int countAllTrainingProviderApplicationCompanyQualificationsOld(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderApplication o where o.company.id in (select x.company.id from CompanyQualifications x where x.accept = true and x.qualification.id = :qualificationID) and o.approvalStatus = :approvalStatus";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> allTrainingProviderApplicationCompanyUnitStandard(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long unitStandardID, ApprovalEnum approvalStatus) throws Exception {
		String hql = "select DISTINCT(o) from TrainingProviderApplication o where o.company.id in (select x.company.id from CompanyUnitStandard x where x.accept = true and x.unitStandard.id = :unitStandardID) and o.approvalStatus = :approvalStatus";
		filters.put("unitStandardID", unitStandardID);
		filters.put("approvalStatus", approvalStatus);
		return (List<TrainingProviderApplication>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters,hql);
	}
	
	public int countAllTrainingProviderApplicationCompanyUnitStandard(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderApplication o where o.company.id in (select x.company.id from CompanyUnitStandard x where x.accept = true and x.unitStandard.id = :unitStandardID) and o.approvalStatus = :approvalStatus";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> allTrainingProviderApplicationQualification(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long qualificationID, Long unitStandardID, ApprovalEnum approvalStatus, boolean accept) throws Exception {
		String hql = "select DISTINCT(o) from TrainingProviderApplication o where o.approvalStatus = :approvalStatus and o.company.id in (select x.company.id from CompanyQualifications x where x.accept = :accept and x.qualification.id = :qualificationID) or o.company.id in (select x.company.id from CompanyUnitStandard x where x.accept = true and x.unitStandard.id = :unitStandardID)";
		filters.put("qualificationID", qualificationID);
		filters.put("unitStandardID", unitStandardID);
		filters.put("accept", accept);
		filters.put("approvalStatus", approvalStatus);
		return (List<TrainingProviderApplication>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters,hql);
	}
	
	public int countAllTrainingProviderApplicationQualification(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderApplication o where o.approvalStatus = :approvalStatus and o.company.id in (select x.company.id from CompanyQualifications x where x.accept = :accept and x.qualification.id = :qualificationID) or o.company.id in (select x.company.id from CompanyUnitStandard x where x.accept = true and x.unitStandard.id = :unitStandardID)";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> allTrainingProviderApplicationLearningProgramme(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long learningProgrammeID, Long qualificationID, Long unitStandardID, ApprovalEnum approvalStatus, boolean accept) throws Exception {
		String hql = "select DISTINCT(o) from TrainingProviderApplication o where o.approvalStatus = :approvalStatus and o.company.id in (select x.company.id from CompanyQualifications x where x.accept = :accept and (x.qualification.id = :learningProgrammeID or x.qualification.id = :qualificationID) or o.company.id in (select x.company.id from CompanyUnitStandard x where x.accept = true and x.unitStandard.id = :unitStandardID)";
		filters.put("learningProgrammeID", learningProgrammeID);
		filters.put("qualificationID", qualificationID);		
		filters.put("unitStandardID", unitStandardID);
		filters.put("accept", accept);
		filters.put("approvalStatus", approvalStatus);
		return (List<TrainingProviderApplication>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters,hql);
	}
	
	public int countAllTrainingProviderApplicationLearningProgramme(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderApplication o where o.approvalStatus = :approvalStatus and o.company.id in (select x.company.id from CompanyQualifications x where x.accept = :accept and (x.qualification.id = :learningProgrammeID or x.qualification.id = :qualificationID) or o.company.id in (select x.company.id from CompanyUnitStandard x where x.accept = true and x.unitStandard.id = :unitStandardID)";
		return dao.countWhere(filters, hql);
	}
		
	public TrainingProviderApplication findCompanyByStatusAndAccreditationNumber(ApprovalEnum approvalStatus, String accreditationNumber) throws Exception{
		return dao.findCompanyByStatusAndAccreditationNumber(approvalStatus, accreditationNumber);
	}
	
	public List<TrainingProviderApplication> allTrainingProviderApplicationUnitStandard(Long unitStandardID, Boolean accept) throws Exception {	
		return dao.allTrainingProviderApplicationUnitStandard(unitStandardID, accept);
	}
	
	public Integer countTrainingProviderApplicationUnitStandard(Long unitStandardID, Boolean accept) throws Exception {	
		return dao.countTrainingProviderApplicationUnitStandard(unitStandardID, accept);
	}
	
	public Integer countTrainingProviderApplicationUnitStandard(Long companyID,Long unitStandardID, Boolean accept, ApprovalEnum approvalStatus) throws Exception {	
		return dao.countTrainingProviderApplicationUnitStandard(companyID,unitStandardID, accept, approvalStatus);
	}
	
	public Integer countTrainingProviderApplicationUnitStandard(Long unitStandardID, Boolean accept, ApprovalEnum approvalStatus) throws Exception {	
		return dao.countTrainingProviderApplicationUnitStandard(unitStandardID, accept, approvalStatus);
	}
	
	public Integer countByTrainingProviderApplication(Long trainingProviderApplicationID,Long unitStandardID, Boolean accept, ApprovalEnum approvalStatus) throws Exception {	
		return dao.countByTrainingProviderApplication(trainingProviderApplicationID,unitStandardID, accept, approvalStatus);
	}
	
	public List<TrainingProviderApplication> allTrainingProviderApplicationQualifications(Long qualificationID, Boolean accept) throws Exception {
		return dao.allTrainingProviderApplicationQualifications(qualificationID, accept);
	}
	
	public List<TrainingProviderApplication> allTrainingProviderApplicationQualifications(Long qualificationID, Boolean accept, ApprovalEnum approvalStatus) throws Exception {
		return dao.allTrainingProviderApplicationQualifications(qualificationID, accept, approvalStatus);
	}
	
	public Integer countTrainingProviderApplicationQualifications(Long qualificationID, Boolean accept, ApprovalEnum approvalStatus) throws Exception {	
		return dao.countTrainingProviderApplicationQualifications(qualificationID, accept, approvalStatus);
	}
	
	public Integer countTrainingProviderApplicationQualifications(Long companyID,Long qualificationID, Boolean accept, ApprovalEnum approvalStatus) throws Exception {	
		return dao.countTrainingProviderApplicationQualifications(companyID, qualificationID, accept, approvalStatus);
	}
	
	public Integer countProviderApllicationsByOpenStatusApplicationTypeAndCompanyId(AccreditationApplicationTypeEnum applicationType, Long companyId, List<ApprovalEnum> statusList) throws Exception {
		return dao.countProviderApllicationsByOpenStatusApplicationTypeAndCompanyId(applicationType, companyId, statusList);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> allTrainingProviderApplicationByStatusAndLegacyLinked(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, List<ApprovalEnum> statusList) throws Exception {
		String hql = "select o from TrainingProviderApplication o where o.legacyProviderAccreditation is not null ";
	 	if (!statusList.isEmpty()) {
	    	hql += " and o.approvalStatus in ( ";
	    	Integer counter = 1;
	    	for (ApprovalEnum status : statusList) {
	    		if (counter == statusList.size()) {
					hql += ":status" + counter.toString();
				} else {
					hql += ":status" + counter.toString() + ",";
				}
	    		filters.put("status" + counter.toString(), status);
				counter++;
			}
	    	hql += " ) ";
		}
		return (List<TrainingProviderApplication>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters,hql);
	}
	
	public int countAllTrainingProviderApplicationByStatusAndLegacyLinked(Map<String, Object> filters, List<ApprovalEnum> statusList) throws Exception {
		String hql = "select count(o) from TrainingProviderApplication o where o.legacyProviderAccreditation is not null ";
		if (!statusList.isEmpty()) {
	    	hql += " and o.approvalStatus in ( ";
	    	Integer counter = 1;
	    	for (ApprovalEnum status : statusList) {
	    		if (counter == statusList.size()) {
					hql += ":status" + counter.toString();
				} else {
					hql += ":status" + counter.toString() + ",";
				}
				counter++;
			}
	    	hql += " ) ";
		}
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderApplication> allTrainingProviderApplicationByStatusAndLegacyLinkedAndApplicationTypeList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, List<ApprovalEnum> statusList, List<AccreditationApplicationTypeEnum> accreditationApplicationTypeList) throws Exception {
		String hql = "select o from TrainingProviderApplication o where o.legacyProviderAccreditation is not null and o.trainingSite is null ";
	 	if (!statusList.isEmpty()) {
	    	hql += " and o.approvalStatus in ( ";
	    	Integer counter = 1;
	    	for (ApprovalEnum status : statusList) {
	    		if (counter == statusList.size()) {
					hql += ":status" + counter.toString();
				} else {
					hql += ":status" + counter.toString() + ",";
				}
	    		filters.put("status" + counter.toString(), status);
				counter++;
			}
	    	hql += " ) ";
		}
	 	if (!accreditationApplicationTypeList.isEmpty()) {
	 		hql += " and o.accreditationApplicationTypeEnum in ( ";
	    	Integer counter = 1;
	    	for (AccreditationApplicationTypeEnum type : accreditationApplicationTypeList) {
	    		if (counter == accreditationApplicationTypeList.size()) {
					hql += ":appType" + counter.toString();
				} else {
					hql += ":appType" + counter.toString() + ",";
				}
	    		filters.put("appType" + counter.toString(), type);
				counter++;
			}
	    	hql += " ) ";
		}
		return (List<TrainingProviderApplication>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters,hql);
	}
	
	public int countAllTrainingProviderApplicationByStatusAndLegacyLinkedAndApplicationTypeList(Map<String, Object> filters, List<ApprovalEnum> statusList, List<AccreditationApplicationTypeEnum> accreditationApplicationTypeList) throws Exception {
		String hql = "select count(o) from TrainingProviderApplication o where o.legacyProviderAccreditation is not null and o.trainingSite is null ";
		if (!statusList.isEmpty()) {
	    	hql += " and o.approvalStatus in ( ";
	    	Integer counter = 1;
	    	for (ApprovalEnum status : statusList) {
	    		if (counter == statusList.size()) {
					hql += ":status" + counter.toString();
				} else {
					hql += ":status" + counter.toString() + ",";
				}
				counter++;
			}
	    	hql += " ) ";
		}
		if (!accreditationApplicationTypeList.isEmpty()) {
	 		hql += " and o.accreditationApplicationTypeEnum in ( ";
	    	Integer counter = 1;
	    	for (AccreditationApplicationTypeEnum type : accreditationApplicationTypeList) {
	    		if (counter == accreditationApplicationTypeList.size()) {
					hql += ":appType" + counter.toString();
				} else {
					hql += ":appType" + counter.toString() + ",";
				}
				counter++;
			}
	    	hql += " ) ";
		}
		return dao.countWhere(filters, hql);
	}
	
	private String anIDNumber(Users user) {
		if (user.getRsaIDNumber() != null && user.getRsaIDNumber() != "" && !user.getRsaIDNumber().isEmpty()) {
			return user.getRsaIDNumber();
		}else if (user.getPassportNumber() != null && user.getPassportNumber() != "" && !user.getPassportNumber().isEmpty()) {
			return user.getPassportNumber();
		}else {
			return "N/A";
		}
	}
	
	public void applicationDeAccreditationSchedule(Date today) throws Exception {
		List<IDataEntity> createList = new ArrayList<>();
		List<IDataEntity> updateList = new ArrayList<>();
		
		Date dayAfter = GenericUtility.getStartOfDay(GenericUtility.addDaysToDate(today, 1));
		
		List<TrainingProviderApplication> applicationList = findByApplicatitionStatus(ApprovalEnum.Approved);
		for (TrainingProviderApplication providerApplication : applicationList) {
			if (providerApplication.getExpiriyDate() != null && dayAfter.after(GenericUtility.getStartOfDay(providerApplication.getExpiriyDate()))) {
				providerApplication.setApprovalStatus(ApprovalEnum.DeAccredited);
				providerApplication.setProviderStatus(ProviderStatusService.instance().findByCode("512"));
				updateList.add(providerApplication);
				StringBuilder sb = new StringBuilder();
				sb.append("System Provider De-accreditation Business Rule For: Skills Development Provider. ");
				sb.append("Accreditation End Date: " + HAJConstants.sdfDateMonthYear.format(providerApplication.getExpiriyDate()) +". ");
				sb.append("Changes: status change from Approved to De-accredited on status change date: "+HAJConstants.sdf3.format(getSynchronizedDate())+". ");
				createList.add(ScheduleChangesLogService.instance().addNewEntryWithoutCreate(providerApplication.getCompany(), null, providerApplication.getClass().getName(), providerApplication.getId(), sb.toString(), false));
			}
		}
		
		if (!updateList.isEmpty() && !createList.isEmpty()) {
			dao.createAndUpdateBatch(createList, updateList);
		}
	}

	public List<TrainingProviderApplication> locateTrainingProviderApplicationByStatusDateAndAccreSearch(ApprovalEnum status, Date date, String desc) throws Exception {
		return dao.locateTrainingProviderApplicationByStatusDateAndAccreSearch(status, date, desc);
	}
	
	public Integer countProviderApllicationsByOpenStatusApplicationTypeAndCompanyIdWithNoSiteAssigned(AccreditationApplicationTypeEnum applicationType, Long companyId, List<ApprovalEnum> statusList) throws Exception {
		return dao.countProviderApllicationsByOpenStatusApplicationTypeAndCompanyIdWithNoSiteAssigned(applicationType, companyId, statusList);
	}
	
	public Integer countByCompanyIdSiteIdApplicationTypeAndStatusList(AccreditationApplicationTypeEnum applicationType, Long companyId, Long trainingSiteId, List<ApprovalEnum> statusList) throws Exception {
		return dao.countByCompanyIdSiteIdApplicationTypeAndStatusList(applicationType, companyId, trainingSiteId, statusList);
	}
	
}