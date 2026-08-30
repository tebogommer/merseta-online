package haj.com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.primefaces.model.SortOrder;

import haj.com.bean.AttachmentBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.WorkplaceMonitoringSiteVisitDAO;
import haj.com.entity.ActiveContractDetail;
import haj.com.entity.ActiveContracts;
import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearnersTradeTest;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.ProcessRoles;
import haj.com.entity.SDFCompany;
import haj.com.entity.Signoff;
import haj.com.entity.Tasks;
import haj.com.entity.TempSignoff;
import haj.com.entity.Users;
import haj.com.entity.WorkplaceMonitoringActionPlan;
import haj.com.entity.WorkplaceMonitoringDiscretionaryGrantComplianceSurvey;
import haj.com.entity.WorkplaceMonitoringLearnerInduction;
import haj.com.entity.WorkplaceMonitoringLearnerPayments;
import haj.com.entity.WorkplaceMonitoringMitigationPlan;
import haj.com.entity.WorkplaceMonitoringSiteVisit;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.OpenClosedEnum;
import haj.com.entity.enums.TradeTestProgressEnum;
import haj.com.entity.enums.TrancheEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.Roles;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.service.lookup.RolesService;
import haj.com.utils.GenericUtility;

public class WorkplaceMonitoringSiteVisitService extends AbstractService {
	
	/** The dao. */
	private WorkplaceMonitoringSiteVisitDAO dao = new WorkplaceMonitoringSiteVisitDAO();
	
	/** service levels */
	private ConfigDocService 		configDocService 		= new ConfigDocService();
	private CompanyService 			companyService 			= new CompanyService();
	private SignoffService 			signoffService 			= new SignoffService();
	private UsersService 			usersService 			= new UsersService();
	private RejectReasonsService 	rejectReasonsService 	= new RejectReasonsService();
	private HostingCompanyService 	hostingCompanyService 	= new HostingCompanyService();
	private SDFCompanyService		sdfCompanyService		= new SDFCompanyService();
	private RolesService 			rolesService			= new RolesService();
	
	/* Workplace Monitoring Sercive levels */
	private ActiveContractsService											activeContractsService;
	private ActiveContractDetailService										activeContractDetailService;
	private WorkplaceMonitoringActionPlanService 							workplaceMonitoringActionPlanService;
	private WorkplaceMonitoringDgMonitoringService 							workplaceMonitoringDgMonitoringService;
	private WorkplaceMonitoringMitigationPlanService						workplaceMonitoringMitigationPlanService;
	private WorkplaceMonitoringLearnerPaymentsService 						workplaceMonitoringLearnerPaymentsService;
	private WorkplaceMonitoringLearnerInductionService 						workplaceMonitoringLearnerInductionService;
	private WorkplaceMonitoringDiscretionaryGrantComplianceSurveyService 	workplaceMonitoringDiscretionaryGrantComplianceSurveyService;
	
	

	/**
	 * Get all WorkplaceMonitoringSiteVisit
 	 * @author TechFinium 
 	 * @see   WorkplaceMonitoringSiteVisit
 	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringSiteVisit}
	 * @throws Exception the exception
 	 */
	public List<WorkplaceMonitoringSiteVisit> allWorkplaceMonitoringSiteVisit() throws Exception {
	  	return dao.allWorkplaceMonitoringSiteVisit();
	}


	/**
	 * Create or update WorkplaceMonitoringSiteVisit.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     WorkplaceMonitoringSiteVisit
	 */
	public void create(WorkplaceMonitoringSiteVisit entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  WorkplaceMonitoringSiteVisit.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringSiteVisit
	 */
	public void update(WorkplaceMonitoringSiteVisit entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  WorkplaceMonitoringSiteVisit.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringSiteVisit
	 */
	public void delete(WorkplaceMonitoringSiteVisit entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WorkplaceMonitoringSiteVisit}
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringSiteVisit
	 */
	public WorkplaceMonitoringSiteVisit findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find WorkplaceMonitoringSiteVisit by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringSiteVisit}
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringSiteVisit
	 */
	public List<WorkplaceMonitoringSiteVisit> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load WorkplaceMonitoringSiteVisit
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringSiteVisit}
	 * @throws Exception the exception
	 */
	public List<WorkplaceMonitoringSiteVisit> allWorkplaceMonitoringSiteVisit(int first, int pageSize) throws Exception {
		return dao.allWorkplaceMonitoringSiteVisit(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of WorkplaceMonitoringSiteVisit for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the WorkplaceMonitoringSiteVisit
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(WorkplaceMonitoringSiteVisit.class);
	}
	
    /**
     * Lazy load WorkplaceMonitoringSiteVisit with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 WorkplaceMonitoringSiteVisit class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.WorkplaceMonitoringSiteVisit} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringSiteVisit> allWorkplaceMonitoringSiteVisit(Class<WorkplaceMonitoringSiteVisit> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<WorkplaceMonitoringSiteVisit>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	}
	
	public void prepareDocumentsForWorkplaceMonitoringSiteVisit(ConfigDocProcessEnum configDocProcess, WorkplaceMonitoringSiteVisit entity, CompanyUserTypeEnum companyUserType) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessNotUploaded(entity.getClass().getName(), entity.getId(), configDocProcess, companyUserType);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = ConfigDocService.instance().findByProcess(configDocProcess, companyUserType);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}
	
	public void prepareDocumentsForWorkplaceMonitoringSiteVisitByProcess(ConfigDocProcessEnum configDocProcess, WorkplaceMonitoringSiteVisit entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null && processRoles != null ) {
			if (processRoles.getCompanyUserType() == null) entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			else entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), processRoles));
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), processRoles);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessA(configDocProcess);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}
	
    /**
     * Get count of WorkplaceMonitoringSiteVisit for lazy load with filters
     * @author TechFinium 
     * @param entity WorkplaceMonitoringSiteVisit class
     * @param filters the filters
     * @return Number of rows in the WorkplaceMonitoringSiteVisit entity
     * @throws Exception the exception     
     */	
	public int count(Class<WorkplaceMonitoringSiteVisit> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	// Awaiting Initiation by CLO / CRM
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringSiteVisit> allWorkplaceMonitoringSiteVisitAwaitingInitiationByCloCrmUser(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long cloCrmId, Boolean awaitingInitiation) throws Exception {
		String hql = "select o from WorkplaceMonitoringSiteVisit o where o.awaitingInitiation = :awaitingInitiation and o.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :cloCrmId or x.crm.users.id = :cloCrmId)";
		filters.put("cloCrmId", cloCrmId);
		filters.put("awaitingInitiation", awaitingInitiation);
		return (List<WorkplaceMonitoringSiteVisit>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql);
	}
	
	public int countAllWorkplaceMonitoringSiteVisitAwaitingInitiationByCloCrmUser(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WorkplaceMonitoringSiteVisit o where o.awaitingInitiation = :awaitingInitiation and o.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :cloCrmId or x.crm.users.id = :cloCrmId)";
		return dao.countWhere(filters, hql);
	}
	
	// Awaiting Initiation
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringSiteVisit> allWorkplaceMonitoringSiteVisitAwaitingInitiation(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Boolean awaitingInitiation) throws Exception {
		String hql = "select o from WorkplaceMonitoringSiteVisit o where o.awaitingInitiation = :awaitingInitiation";
		filters.put("awaitingInitiation", awaitingInitiation);
		return (List<WorkplaceMonitoringSiteVisit>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql);
	}
	
	public int countAllWorkplaceMonitoringSiteVisitAwaitingInitiation(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WorkplaceMonitoringSiteVisit o where o.awaitingInitiation = :awaitingInitiation";
		return dao.countWhere(filters, hql);
	}
	
	//  Open non compliance Workplace Monitoring Site Visits
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringSiteVisit> allWorkplaceMonitoringSiteVisitByOpenWhereNonComplianceIdentified(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, ApprovalEnum status) throws Exception {
		String hql = "select o from WorkplaceMonitoringSiteVisit o where o.status = :status";
		filters.put("status", status);
		return (List<WorkplaceMonitoringSiteVisit>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql);
	}
	
	public int countAllWorkplaceMonitoringSiteVisitByOpenWhereNonComplianceIdentified(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WorkplaceMonitoringSiteVisit o where o.status = :status";
		return dao.countWhere(filters, hql);
	}
	
	// find by Status non compliance wpm
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringSiteVisit> allWorkplaceMonitoringSiteVisitByStatusWhereNonComplianceIdentified(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Boolean identifiedNonCompliance, ApprovalEnum status) throws Exception {
		String hql = "select o from WorkplaceMonitoringSiteVisit o where o.status = :status and o.nonCompliancesIdentified = :identifiedNonCompliance";
		filters.put("status", status);
		filters.put("identifiedNonCompliance", identifiedNonCompliance);
		return (List<WorkplaceMonitoringSiteVisit>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql);
	}
	
	public int countAllWorkplaceMonitoringSiteVisitByStatusWhereNonComplianceIdentified(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WorkplaceMonitoringSiteVisit o where o.status = :status and o.nonCompliancesIdentified = :identifiedNonCompliance";
		return dao.countWhere(filters, hql);
	}
	
	// find all non compliance wpm
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringSiteVisit> allWorkplaceMonitoringSiteVisitByNonComplianceIdentified(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Boolean identifiedNonCompliance) throws Exception {
		String hql = "select o from WorkplaceMonitoringSiteVisit o where o.nonCompliancesIdentified = :identifiedNonCompliance";
		filters.put("identifiedNonCompliance", identifiedNonCompliance);
		return (List<WorkplaceMonitoringSiteVisit>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql);
	}
	
	public int countAllWorkplaceMonitoringSiteVisitByNonComplianceIdentified(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WorkplaceMonitoringSiteVisit o where o.nonCompliancesIdentified = :identifiedNonCompliance";
		return dao.countWhere(filters, hql);
	}
	
	// find by CLO / CRM user Where non compliance wpm
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringSiteVisit> allWorkplaceMonitoringSiteVisitByCloCrmUserWhereNonComplianceIdentified(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long cloCrmId, Boolean identifiedNonCompliance) throws Exception {
		String hql = "select o from WorkplaceMonitoringSiteVisit o where o.nonCompliancesIdentified = :identifiedNonCompliance and o.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :cloCrmId or x.crm.users.id = :cloCrmId)";
		filters.put("cloCrmId", cloCrmId);
		filters.put("identifiedNonCompliance", identifiedNonCompliance);
		return (List<WorkplaceMonitoringSiteVisit>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql);
	}
	
	public int countAllWorkplaceMonitoringSiteVisitByCloCrmUserWhereNonComplianceIdentified(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WorkplaceMonitoringSiteVisit o where o.nonCompliancesIdentified = :identifiedNonCompliance and o.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :cloCrmId or x.crm.users.id = :cloCrmId)";
		return dao.countWhere(filters, hql);
	}
	
	// find by CLO / CRM user Where Status non compliance wpm
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringSiteVisit> allWorkplaceMonitoringSiteVisitByCloCrmUserStatusWhereNonComplianceIdentified(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long cloCrmId, Boolean identifiedNonCompliance, ApprovalEnum status) throws Exception {
		String hql = "select o from WorkplaceMonitoringSiteVisit o where o.status = :status and o.nonCompliancesIdentified = :identifiedNonCompliance and o.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :cloCrmId or x.crm.users.id = :cloCrmId)";
		filters.put("cloCrmId", cloCrmId);
		filters.put("status", status);
		filters.put("identifiedNonCompliance", identifiedNonCompliance);
		return (List<WorkplaceMonitoringSiteVisit>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql);
	}
	
	public int countAllWorkplaceMonitoringSiteVisitByCloCrmUserStatusWhereNonComplianceIdentified(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WorkplaceMonitoringSiteVisit o where o.status = :status and o.nonCompliancesIdentified = :identifiedNonCompliance and o.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :cloCrmId or x.crm.users.id = :cloCrmId)";
		return dao.countWhere(filters, hql);
	}
	
	// find by CLO / CRM user Where Open non compliance
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringSiteVisit> allWorkplaceMonitoringSiteVisitByCloCrmUserOpenWhereNonComplianceIdentified(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long cloCrmId, ApprovalEnum status) throws Exception {
		String hql = "select o from WorkplaceMonitoringSiteVisit o where o.status = :status and o.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :cloCrmId or x.crm.users.id = :cloCrmId)";
		filters.put("cloCrmId", cloCrmId);
		filters.put("status", status);
		return (List<WorkplaceMonitoringSiteVisit>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql);
	}
	
	public int countAllWorkplaceMonitoringSiteVisitByCloCrmUserOpenWhereNonComplianceIdentified(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WorkplaceMonitoringSiteVisit o where o.status = :status and o.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :cloCrmId or x.crm.users.id = :cloCrmId)";
		return dao.countWhere(filters, hql);
	}
	
	// find by CLO / CRM user
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringSiteVisit> allWorkplaceMonitoringSiteVisitByCloCrmUser(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long cloCrmId) throws Exception {
		String hql = "select o from WorkplaceMonitoringSiteVisit o where o.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :cloCrmId or x.crm.users.id = :cloCrmId)";
		filters.put("cloCrmId", cloCrmId);
		return (List<WorkplaceMonitoringSiteVisit>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql);
	}
	
	public int countAllWorkplaceMonitoringSiteVisitByCloCrmUser(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WorkplaceMonitoringSiteVisit o where o.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :cloCrmId or x.crm.users.id = :cloCrmId)";
		return dao.countWhere(filters, hql);
	}
	
	// find by company id
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringSiteVisit> allWorkplaceMonitoringSiteVisitByCompanyId(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long companyId) throws Exception {
		String hql = "select o from WorkplaceMonitoringSiteVisit o where o.company.id = :companyId";
		filters.put("companyId", companyId);
		return (List<WorkplaceMonitoringSiteVisit>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql);
	}
	
	public int countAllWorkplaceMonitoringSiteVisitByCompanyId(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WorkplaceMonitoringSiteVisit o where o.company.id = :companyId";
		return dao.countWhere(filters, hql);
	}
	
	// find by region ID
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringSiteVisit> allWorkplaceMonitoringSiteVisitByRegionId(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long regionId) throws Exception {
		String hql = "select o from WorkplaceMonitoringSiteVisit o where o.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId)";
		filters.put("regionId", regionId);
		return (List<WorkplaceMonitoringSiteVisit>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql);
	}
	
	public int countAllWorkplaceMonitoringSiteVisitByRegionId(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WorkplaceMonitoringSiteVisit o where o.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId)";
		return dao.countWhere(filters, hql);
	}
	
	private List<WorkplaceMonitoringSiteVisit> populateAdditionalInformationList(List<WorkplaceMonitoringSiteVisit> list) throws Exception {
		for (WorkplaceMonitoringSiteVisit entity : list) {
			populateAdditionalInformation(entity);
		}
		return list;
	}

	private WorkplaceMonitoringSiteVisit populateAdditionalInformation(WorkplaceMonitoringSiteVisit entity) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
		}
		return entity;
	}
	
	public Integer countSiteVisitByCompanyId(Company company) throws Exception {
		return dao.countSiteVisitByCompanyId(company.getId());
	}
	
	public Integer countOpenSiteVisitsByComapnyId(Company company, List<ApprovalEnum> statusList) throws Exception {
		return dao.countOpenSiteVisitsByComapnyId(company.getId(), statusList);
	}
	
	public Boolean canInititaeNewMonitoringSiteVisit(Company company, Users sessionUser) throws Exception {
		Boolean canCreate = true;
		
		// check if user assigned as Clo user
		Users cloUserAssigned = getCLO(company);
		if (cloUserAssigned == null || cloUserAssigned.getId() == null || !cloUserAssigned.getId().equals(sessionUser.getId())) {
			canCreate = false;
		}
		
		// check if any open workplace monitoring
		if (countOpenSiteVisitsByComapnyId(company, ApprovalEnum.getOpenStatusWorkplaceMonitoring()) > 0) {
			canCreate = false;
		}
		
		return canCreate;
	}
	
	public void prepareNewRegistrationWithConfig(WorkplaceMonitoringSiteVisit entity, ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			List<ConfigDoc> l = configDocService.findByProcessNotUploaded(entity.getClass().getName(), entity.getId(), configDocProcess, companyUserType);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = configDocService.findByProcess(configDocProcess, companyUserType);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}
	
	public void createWorkplaceMonitoringSiteVisit(Company company, Users sessionUser, Date date, Users assignedCloUser, Users asignedCrmUser) throws Exception {
		WorkplaceMonitoringSiteVisit entity = new WorkplaceMonitoringSiteVisit();
		entity.setStatus(ApprovalEnum.PendingApproval);
		entity.setCompany(company);
		entity.setAwaitingInitiation(true);
		entity.setCreateUser(sessionUser);
		entity.setMonitoringDate(date);
		create(entity);
		if (GenericUtility.getStartOfDay(date).equals(GenericUtility.getStartOfDay(getSynchronizedDate()))) {
			initiateWorkplaceMonitoring(entity, sessionUser, assignedCloUser, asignedCrmUser);
		}
	}
	
	public void updateMonitoringDate(WorkplaceMonitoringSiteVisit entity, Users sessionUser, Date newDate) throws Exception {
		entity.setMonitoringDate(newDate);
		update(entity);
	}
	
	public void initiateWorkplaceMonitoring(WorkplaceMonitoringSiteVisit entity, Users sessionUser, Users assignedCloUser, Users asignedCrmUser) throws Exception {
		List<IDataEntity> createList = new ArrayList<>();
		List<IDataEntity> updateList = new ArrayList<>();
		if (assignedCloUser != null && assignedCloUser.getId() != null) {
			entity.setManagerAssignedCloUser(assignedCloUser);
		}
		if (asignedCrmUser != null && asignedCrmUser.getId() != null) {
			entity.setManagerAssignedCrmUser(asignedCrmUser);
		}
		entity.setAwaitingInitiation(false);
		updateList.add(entity);
		
		// get CLO for task
		List<Users> cloUserList = new ArrayList<>();
		if (assignedCloUser != null && assignedCloUser.getId() != null) {
			cloUserList.add(assignedCloUser);
		} else {
			if (getCLO(entity.getCompany()) == null || getCLO(entity.getCompany()).getId() == null) {
				throw new Exception("Unable to locate");
			} else {
				cloUserList.add(getCLO(entity.getCompany()));
			}
		}
		
		// create rest of items linked to workplace monitoring
		
		// action plan
		if (workplaceMonitoringActionPlanService == null) {
			workplaceMonitoringActionPlanService = new WorkplaceMonitoringActionPlanService();
		}
		List<WorkplaceMonitoringActionPlan> workplaceMonitoringActionPlanList = workplaceMonitoringActionPlanService.returnWorkplaceMonitoringActionPlanListToBeCreated(entity, sessionUser);
		if (!workplaceMonitoringActionPlanList.isEmpty()) {
			createList.addAll(workplaceMonitoringActionPlanList);
		}
		workplaceMonitoringActionPlanList = null;
		
		// Survey 
		if (workplaceMonitoringDiscretionaryGrantComplianceSurveyService == null) {
			workplaceMonitoringDiscretionaryGrantComplianceSurveyService = new WorkplaceMonitoringDiscretionaryGrantComplianceSurveyService();
		}
		List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> surveyList = workplaceMonitoringDiscretionaryGrantComplianceSurveyService.returnWorkplaceMonitoringDiscretionaryGrantComplianceSurveyToBeCreated(entity, sessionUser);
		if (!surveyList.isEmpty()) {
			createList.addAll(surveyList);
		}
		surveyList = null;
		
		// learner induction
		if (workplaceMonitoringLearnerInductionService == null) {
			workplaceMonitoringLearnerInductionService = new WorkplaceMonitoringLearnerInductionService();
		}
		List<WorkplaceMonitoringLearnerInduction> learnerInductionList = workplaceMonitoringLearnerInductionService.returnLearnerInductionForWorkplaceMonitoringSiteVisitToBeCreated(entity, sessionUser);
		if (!learnerInductionList.isEmpty()) {
			createList.addAll(learnerInductionList);
		}
		learnerInductionList = null;
		
		// creates DG monitoring
		if (workplaceMonitoringDgMonitoringService == null) {
			workplaceMonitoringDgMonitoringService = new WorkplaceMonitoringDgMonitoringService();
		}
		if (activeContractsService == null) {
			activeContractsService = new ActiveContractsService();
		}
		List<ActiveContracts> allActiveContractsList = activeContractsService.findByCompany(entity.getCompany());
		for (ActiveContracts activeContract : allActiveContractsList) {
			createList.add(workplaceMonitoringDgMonitoringService.createNewWorkplaceMonitoringDgMonitoring(activeContract, entity.getClass().getName(), entity.getId(), sessionUser));
		}
		
		// create all learner payments and checks
		if (workplaceMonitoringLearnerPaymentsService == null) {
			workplaceMonitoringLearnerPaymentsService = new WorkplaceMonitoringLearnerPaymentsService();
		}
		List<WorkplaceMonitoringLearnerPayments> learnerPaymentList = workplaceMonitoringLearnerPaymentsService.createNewWorkplaceMonitoringLearnerPayments(entity.getCompany(),entity.getClass().getName(), entity.getId(), sessionUser);
		if (learnerPaymentList != null && !learnerPaymentList.isEmpty()) {
			createList.addAll(learnerPaymentList);
		}
		
		// create new links to WPM
		if (!createList.isEmpty()) {
			dao.createBatch(createList);
		}
		
		// Update WPM for tasks
		if (!updateList.isEmpty()) {
			dao.updateBatch(updateList);
		}
		
		generateSignOffListWorkplaceMonitoring(entity);
		
		if (sessionUser != null && sessionUser.getId() != null) {
			TasksService.instance().findFirstInProcessAndCreateTask("", sessionUser,  entity.getId(), entity.getClass().getName(), false, ConfigDocProcessEnum.WorkplaceMonitoringSiteVisit, null, cloUserList);
		} else {
			TasksService.instance().findFirstInProcessAndCreateTask("", null,  entity.getId(), entity.getClass().getName(), false, ConfigDocProcessEnum.WorkplaceMonitoringSiteVisit, null, cloUserList);
		}
	}
	
	public Users getCLO(Company company) throws Exception {
		RegionTown rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
		if (rt != null && rt.getClo() != null && rt.getClo().getUsers() != null) {
			return rt.getClo().getUsers();
		}else {
			return null;
		}
	}
	
	public Users getCRM(Company company) throws Exception {
		RegionTown rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
		if (rt != null && rt.getCrm() != null && rt.getCrm().getUsers() != null) {
			return rt.getCrm().getUsers();
		}else {
			return null;
		}
	}
	
	public void completeWorkplaceMonitoringSiteVisit(Users sessionUser, WorkplaceMonitoringSiteVisit entity, Tasks tasks) throws Exception {
		TasksService.instance().findNextInProcessAndCreateTask(sessionUser, tasks, null, false);
	}

	public void validateSignOffusersAssigned( WorkplaceMonitoringSiteVisit entity) throws Exception{
		Boolean allUsersAssigned = true;
		List<Signoff> signOffUsers = signoffService.findByTargetKeyAndClass(entity.getId(), entity.getClass().getName());
		for (Signoff signoff : signOffUsers) {
			if (signoff.getUser() == null) {
				if (signoff.getTempSignoff() == null) {
					allUsersAssigned = false;
					break;
				}
			}
		}
		if (!allUsersAssigned) {
			throw new Exception("Please ensure all users assigned to sign off");
		}
	}
	
	public void generateSignOffListWorkplaceMonitoring(WorkplaceMonitoringSiteVisit entity) throws Exception {
		List<IDataEntity> createList = new ArrayList<>();
		List<SDFCompany> sdfCompany = sdfCompanyService.findByCompany(entity.getCompany());
		Users primarySdfUser = null;
		Users employeeSdfUser = null;
		for (SDFCompany sdf : sdfCompany) {
			if (sdf.getSdfType().getId().equals(HAJConstants.PRIMARY_ID)) {
				primarySdfUser = sdf.getSdf();
			}
			if (sdf.getSdfType().getId().equals(HAJConstants.EMP_ID)) {
				employeeSdfUser = sdf.getSdf();
			}
		}
		
		if (primarySdfUser != null && primarySdfUser.getId() != null) {
			createList.add(SignoffService.instance().newSignOffToBeCreated(primarySdfUser, "COMPANY REPRESENTATIVE", false, entity.getClass().getName(), entity.getId(), null, false, true));
		} else {
			createList.add(SignoffService.instance().newSignOffToBeCreated(null, "COMPANY REPRESENTATIVE", false, entity.getClass().getName(), entity.getId(), null, false, true));
		}
		
		if (employeeSdfUser != null && employeeSdfUser.getId() != null) {
			createList.add(SignoffService.instance().newSignOffToBeCreated(employeeSdfUser, "EMPLOYEE REPRESENTATIVE", false, entity.getClass().getName(), entity.getId(), null, false, false));
		}
		
		if (!createList.isEmpty()) {
			dao.createBatch(createList);
		}
	}
	
	public void sendWorkplaceMonitoringSiteVisitForSignOff(Users sessionUser, WorkplaceMonitoringSiteVisit entity, Tasks tasks) throws Exception {
		
		List<Users> taskUsers = new ArrayList<>();
		List<Signoff> signOffUsersNsdms = signoffService.findByTargetKeyAndClassNsdmsUsersLastest(entity.getId(), entity.getClass().getName(), true);
		for (Signoff signoff : signOffUsersNsdms) {
			taskUsers.add(signoff.getUser());
		}
		
		List<Signoff> signOffUsersTemp = signoffService.findByTargetKeyAndClassTempUsersLastest(entity.getId(), entity.getClass().getName(), true);
		for (Signoff tempSignoff : signOffUsersTemp) {
			sendNotificationTempSignoff(entity, tempSignoff);
		}
		
		entity.setStatus(ApprovalEnum.PendingSignOff);
		entity.setSignOffState(true);
		entity.setCloUser(sessionUser);
		update(entity);
		
		TasksService.instance().findNextInProcessAndCreateTask(sessionUser, tasks, taskUsers, true);
	}
	
	/*
	 * Updates the temp sign off user by CLO 
	 */
	public void updateTempSignOff(WorkplaceMonitoringSiteVisit entity, Signoff signoff, Users sessionUser) throws Exception{
		Boolean generateTask = false;
		Boolean sendTempNotification = false;
		List<IDataEntity> updatelist = new ArrayList<>();
		List<IDataEntity> createList = new ArrayList<>();
		List<Users> taskUser = new ArrayList<>();
		
		// sets who made the change
		signoff.setChangeUser(sessionUser);
		signoff.setDateSignOffUserChanged(getSynchronizedDate());
		
		// based on if internal or external user applies the changes
		if (signoff.getExternalUserSignoff() != null && signoff.getExternalUserSignoff()) {
			sendTempNotification = true;
			generateTask = false;
			if (signoff.getTempSignoff().getId() == null) {
				TempSignoff tso = signoff.getTempSignoff();
				createList.add(tso);
				signoff.setTempSignoff(tso);
				updatelist.add(signoff);
			} else {
				updatelist.add(signoff.getTempSignoff());
				updatelist.add(signoff);
			}
		} else {
			signoff.setTempSignoff(null);
			sendTempNotification = false;
			generateTask = true;
			updatelist.add(signoff);
		}
		
		// create list
		if (!createList.isEmpty()) {
			dao.createBatch(createList);
		}
		// update list
		if (!updatelist.isEmpty()) {
			dao.updateBatch(updatelist);
		}
		
		// send notification to external user
		if (sendTempNotification) {
			signoff = signoffService.findByKey(signoff.getId());
			sendNotificationTempSignoff(entity, signoff);
		}
		
		// generates task for user assigned
		if (generateTask) {
			if (!taskUser.isEmpty()) {
				TasksService.instance().findByPositionAndCreateTaskWithUsersIgnoringCompanyUserTypeCheck(1, "", null, entity.getId(), entity.getClass().getName(), true, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), ConfigDocProcessEnum.WorkplaceMonitoringSiteVisit, null, "", true, taskUser);
			}
		}
	}
	
	public void sendNotificationTempSignoff(WorkplaceMonitoringSiteVisit entity, Signoff signoff) throws Exception {
		String subject = "NSDMS: External Sign off";
		String message = "Click <a href=\"" + HAJConstants.PL_LINK + "externalSignoffPage.jsf?uuid=" + signoff.getGuid().trim() + "\">here</a> to sign off";
		GenericUtility.sendMail(signoff.getTempSignoff().getEmail(), subject, message, null);
	}
	
	public void reSendNotificationTempSignoff(WorkplaceMonitoringSiteVisit entity, Signoff signoff) throws Exception {
		
		signoff = SignoffService.instance().findByKey(signoff.getId());
		sendNotificationTempSignoff(entity, signoff);
	}
	
	public void rejectTask(WorkplaceMonitoringSiteVisit entity, Users sessionUser, Tasks task, List<RejectReasons> selectedRejectReasonList) throws Exception {
		if (task != null && task.getId() != null) {
			RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDbWithTaskId(entity.getId(), entity.getClass().getName(), selectedRejectReasonList, sessionUser, ConfigDocProcessEnum.WorkplaceMonitoringSiteVisit, task.getId());
		} else {
			RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(entity.getId(), entity.getClass().getName(), selectedRejectReasonList, sessionUser, ConfigDocProcessEnum.WorkplaceMonitoringSiteVisit);
		}
		TasksService.instance().findPreviousInProcessAndCreateTask("", sessionUser, entity.getId(), entity.getClass().getName(), true, task, MailDef.instance(MailEnum.Task), null);	
	}
	
	public void signOffWorkplaceMonitoring(WorkplaceMonitoringSiteVisit workplaceMonitoringSiteVisit, List<Signoff> signOffList, Users sessionUser, Tasks tasks) throws Exception {
		List<IDataEntity> updateList = new ArrayList<>();
		Boolean outstanidngSignoffs = false;
		for (Signoff signoff : signOffList) {

			if (signoff.getUser() != null && signoff.getUser().equals(sessionUser)) {
				if (signoff.getAccept() == null || !signoff.getAccept()) {
					throw new Exception("Please accept the sign off before proceeding.");
				}
				signoff.setSignOffDate(getSynchronizedDate());
			}

			updateList.add(signoff);
			if ((signoff.getAccept() == null || !signoff.getAccept()) || (signoff.getAccept() != null && signoff.getAccept() &&  signoff.getSignOffDate() == null)) {
				outstanidngSignoffs = true;
			}
		}
		
		if (!updateList.isEmpty()) {
			dao.updateBatch(updateList);
		}
		
		if (!outstanidngSignoffs) {
			workplaceMonitoringSiteVisit.setStatus(ApprovalEnum.PendingApproval);
			workplaceMonitoringSiteVisit.setSignOffState(false);
			update(workplaceMonitoringSiteVisit);
			List<Users> usersAssigned = new ArrayList<>();
			if (workplaceMonitoringSiteVisit.getManagerAssignedCloUser() != null && workplaceMonitoringSiteVisit.getManagerAssignedCloUser().getId() != null) {
				usersAssigned.add(workplaceMonitoringSiteVisit.getManagerAssignedCloUser());
			}else {
				usersAssigned.add(getCLO(workplaceMonitoringSiteVisit.getCompany()));
			}
			TasksService.instance().findNextInProcessAndCreateTask(sessionUser, tasks, usersAssigned, false);
		}else {
			TasksService.instance().completeTask(tasks);
		}
	}
	
	public void signOffWorkplaceMonitoringExternal(Signoff signoffPassed) throws Exception {
		List<IDataEntity> updateList = new ArrayList<>();
		Boolean outstanidngSignoffs = false;
		if (signoffPassed.getAccept() == null || !signoffPassed.getAccept()) {
			throw new Exception("Please accept the sign off before proceeding.");
		}
		signoffPassed.setAccept(true);
		signoffPassed.setSignOffDate(getSynchronizedDate());
		updateList.add(signoffPassed);
		if (!updateList.isEmpty()) {
			dao.updateBatch(updateList);
		}
		List<Signoff> signoffList = signoffService.findByTargetKeyAndClassAndLastest(signoffPassed.getTargetKey(), signoffPassed.getTargetClass(), true);
		for (Signoff signoff : signoffList) {
			if ((signoff.getAccept() == null || !signoff.getAccept()) || (signoff.getAccept() != null && signoff.getAccept() &&  signoff.getSignOffDate() == null)) {
				outstanidngSignoffs = true;
			}
		}

		if (!outstanidngSignoffs) {
			WorkplaceMonitoringSiteVisit workplaceMonitoringSiteVisit = dao.findByKey(signoffPassed.getTargetKey());
			workplaceMonitoringSiteVisit.setStatus(ApprovalEnum.PendingApproval);
			workplaceMonitoringSiteVisit.setSignOffState(false);
			update(workplaceMonitoringSiteVisit);
			List<Users> usersAssigned = new ArrayList<>();
			if (workplaceMonitoringSiteVisit.getManagerAssignedCloUser() != null && workplaceMonitoringSiteVisit.getManagerAssignedCloUser().getId() != null) {
				usersAssigned.add(workplaceMonitoringSiteVisit.getManagerAssignedCloUser());
			}else {
				usersAssigned.add(getCLO(workplaceMonitoringSiteVisit.getCompany()));
			}
			TasksService.instance().findByPositionAndCreateTaskWithUsersIgnoringCompanyUserTypeCheck(2, "", null, workplaceMonitoringSiteVisit.getId(), workplaceMonitoringSiteVisit.getClass().getName(), true, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), ConfigDocProcessEnum.WorkplaceMonitoringSiteVisit, null, "", true, usersAssigned);
		}
	}
	
	public void completeCloSignOff(WorkplaceMonitoringSiteVisit entity, Users sessionUser, Tasks task)  throws Exception {
		List<Users> taskUsers = new ArrayList<>();
		Users crmUser = null;
		if (entity.getManagerAssignedCrmUser() != null && entity.getManagerAssignedCrmUser().getId() != null) {
			crmUser = entity.getManagerAssignedCrmUser();
		} else {
			crmUser = getCRM(entity.getCompany());
		}
		if (crmUser == null || crmUser.getId() == null) {
			throw new Exception("Unable to locate CRM user, contact support!");
		}
		taskUsers.add(crmUser);
		
		entity.setStatus(ApprovalEnum.PendingFinalApproval);
		entity.setCloUser(sessionUser);
		update(entity);
		
		// create sign off
		SignoffService.instance().createSignOff(sessionUser, "merSETA REPRESENTATIVE", true, entity.getClass().getName(), entity.getId(), HAJConstants.ROLES_CLIENT_LIAISON_OFFICER_ID, false, false);
		// clear rejection reasons
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDbWithTaskId(entity.getId(), entity.getClass().getName(), new ArrayList<>(), null, task.getWorkflowProcess(), null);
		// sends task to CRM
		TasksService.instance().findNextInProcessAndCreateTask(sessionUser, task, taskUsers, false);
	}
	
	public void rejectToClo(WorkplaceMonitoringSiteVisit entity, Users user, Tasks tasks, List<RejectReasons> rejectReasonsList) throws Exception {
		List<Users> taskUsers = new ArrayList<>();
		Users cloUser = null;
		if( entity.getManagerAssignedCloUser() != null && entity.getManagerAssignedCloUser().getId() != null){
			cloUser = entity.getManagerAssignedCloUser();
		}else {
			cloUser = getCLO(entity.getCompany());
		}
		if (cloUser == null || cloUser.getId() == null) {
			throw new Exception("Unable to locate CLO assigned to company, contact support!");
		}
		taskUsers.add(cloUser);
		entity.setStatus(ApprovalEnum.PendingApproval);
		update(entity);
		
		// create rejection reasons
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDbWithTaskId(entity.getId(), entity.getClass().getName(), rejectReasonsList, user, tasks.getWorkflowProcess(), tasks.getId());
		// send task back to CLO
		TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, taskUsers);
		
		List<Signoff> cloSignOff = SignoffService.instance().findByTargetKeyAndClassAndLastestAndRole(entity.getId(), entity.getClass().getName(), true, HAJConstants.ROLES_CLIENT_LIAISON_OFFICER_ID);
		for (Signoff signoff : cloSignOff) {
			if (signoff.getUser() != null && signoff.getUser().getId() != null && signoff.getUser().getId().equals(cloUser.getId())) {
				signoff.setLastestSignoff(false);
				SignoffService.instance().update(signoff);
				break;
			}
		}
	}
	
	public void finalApproveWorkplaceMonitoringComplianceIssues(WorkplaceMonitoringSiteVisit entity, Users sessionUser, Tasks tasks) throws Exception{
		entity.setCrmUser(sessionUser);
		entity.setStatus(ApprovalEnum.PendingComplianceIssues);
		update(entity);
		SignoffService.instance().createSignOff(sessionUser, "MERSETA: Client Liaison Officer", true, entity.getClass().getName(), entity.getId(), HAJConstants.ROLES_CLIENT_RELATIONS_MANAGER_ID, false, false);
		TasksService.instance().completeTask(tasks);
		// do whatever is required for the non compliance issues
	}
	
	/*
	 * CRM Final Approval
	 */
	public void finalApproveWorkplaceMonitoring(WorkplaceMonitoringSiteVisit entity, Users sessionUser, Tasks tasks) throws Exception{
		entity.setCrmUser(sessionUser);
		entity.setApprovedDate(getSynchronizedDate());
		entity.setApprovedUser(sessionUser);
		// identify if any compliance issues
		if (workplaceMonitoringMitigationPlanService == null) {
			workplaceMonitoringMitigationPlanService = new WorkplaceMonitoringMitigationPlanService();
		}
//		if (workplaceMonitoringMitigationPlanService.countNonCompliantAndOpenMitigationPlanBySiteVisitId(entity.getId(), YesNoEnum.Yes, OpenClosedEnum.Open) > 0) {
		if (workplaceMonitoringMitigationPlanService.countOpenMitigationPlanBySiteVisitId(entity.getId(), OpenClosedEnum.Open) > 0) {
			entity.setNonComplianceHoldingArea(true);
			entity.setNonCompliancesIdentified(true);
			entity.setStatus(ApprovalEnum.PendingComplianceIssues);
			update(entity);
			sendNotificationWorkplaceMonitoringSiteVisit(3, entity, null);
			updateComplianceIssuesWhereCanAction(entity);
		} else {
			entity.setStatus(ApprovalEnum.Approved);
			update(entity);
			sendNotificationWorkplaceMonitoringSiteVisit(4, entity, null);
			finalApproveProcess(entity, sessionUser);
		}
		SignoffService.instance().createSignOff(sessionUser, "MERSETA: Client Relations Manager", true, entity.getClass().getName(), entity.getId(), HAJConstants.ROLES_CLIENT_RELATIONS_MANAGER_ID, false, false);
		TasksService.instance().completeTask(tasks);
	}
	
	private void updateComplianceIssuesWhereCanAction(WorkplaceMonitoringSiteVisit entity) throws Exception {
		List<IDataEntity> updateList = new ArrayList<>();
		if (workplaceMonitoringMitigationPlanService == null) {
			workplaceMonitoringMitigationPlanService = new WorkplaceMonitoringMitigationPlanService();
		}
		List<WorkplaceMonitoringMitigationPlan> allMitigationPlanBySiteVisitList = workplaceMonitoringMitigationPlanService.findByWorkplaceMonitoringSiteVisitId(entity.getId());
		for (WorkplaceMonitoringMitigationPlan plan : allMitigationPlanBySiteVisitList) {
			if (plan.getSoftDeleted() != null && plan.getSoftDeleted()) {
				plan.setCanAction(false);
			} else {
				if (plan.getOpenClosedEnum() == OpenClosedEnum.Open) {
					plan.setCanAction(true);
				} else {
					plan.setCanAction(false);
				}
			}
			updateList.add(plan);
		}
		if (!updateList.isEmpty()) {
			dao.updateBatch(updateList);
		}
	}

	private void finalApproveProcess(WorkplaceMonitoringSiteVisit entity, Users sessionUser) throws Exception{
		// send active contracts for termination
		// generate Tranche payments
		generateTranchPaymentsForEntriesToBeProcessed(entity, sessionUser);
		// send site visit report
		sendNotificationWorkplaceMonitoringSiteVisit(6, entity, null);
	}
	
	private void generateTranchPaymentsForEntriesToBeProcessed(WorkplaceMonitoringSiteVisit entity, Users sessionUser) throws Exception{
		if (workplaceMonitoringLearnerPaymentsService == null) {
			workplaceMonitoringLearnerPaymentsService = new WorkplaceMonitoringLearnerPaymentsService();
		}
		List<IDataEntity> updateList = new ArrayList<>();
		List<WorkplaceMonitoringLearnerPayments> entryList =  workplaceMonitoringLearnerPaymentsService.findByTargetClassAndKeyWithDataResolved(entity.getClass().getName(), entity.getId());
		for (WorkplaceMonitoringLearnerPayments wmlp : entryList) {
			// check if payment was approved by CLO
			if (wmlp.getPayTranchPayment() != null && wmlp.getPayTranchPayment()) {
				if (activeContractDetailService == null) {
					activeContractDetailService = new ActiveContractDetailService();
				}
				if (wmlp.getProjectImplementationPlanLearners().getProjectImplementationPlan().getInterventionType().getTranchintervals() != null && wmlp.getProjectImplementationPlanLearners().getProjectImplementationPlan().getInterventionType().getTranchintervals() == 3) {
					activeContractDetailService.addTranchePaymentDetail(wmlp.getProjectImplementationPlanLearners(), wmlp.getProjectImplementationPlanLearners().getCompanyLearners(), sessionUser, 0.5, TrancheEnum.TRANCHE_THREE, true);
					wmlp.getProjectImplementationPlanLearners().setAllPaymentsCompleted(true);
					updateList.add(wmlp.getProjectImplementationPlanLearners());
				} else {
					if (wmlp.getProjectImplementationPlanLearners().getNextTranchPayment() == TrancheEnum.TRANCHE_THREE) {
						activeContractDetailService.addTranchePaymentDetail(wmlp.getProjectImplementationPlanLearners(), wmlp.getProjectImplementationPlanLearners().getCompanyLearners(), sessionUser, 0.25, TrancheEnum.TRANCHE_THREE, true);
						wmlp.getProjectImplementationPlanLearners().setAllPaymentsCompleted(false);
						wmlp.getProjectImplementationPlanLearners().setNextTranchPayment(TrancheEnum.TRANCHE_FOUR);
						updateList.add(wmlp.getProjectImplementationPlanLearners());
					} else {
						activeContractDetailService.addTranchePaymentDetail(wmlp.getProjectImplementationPlanLearners(), wmlp.getProjectImplementationPlanLearners().getCompanyLearners(), sessionUser, 0.25, TrancheEnum.TRANCHE_FOUR, true);
						wmlp.getProjectImplementationPlanLearners().setAllPaymentsCompleted(true);
						updateList.add(wmlp.getProjectImplementationPlanLearners());
					}
				}
			}
		}
		if (!updateList.isEmpty()) {
			dao.updateBatch(updateList);
		}
	}


	public void submitNonComplianceForApproval(WorkplaceMonitoringSiteVisit entity, Users sessionUser) throws Exception{
		List<Users> crmList = new ArrayList<>();
		if (entity.getManagerAssignedCrmUser() != null && entity.getManagerAssignedCrmUser().getId() != null) {
			crmList.add(entity.getManagerAssignedCrmUser());
		} else {
			crmList.add(getCRM(entity.getCompany()));
		}
		if (crmList.isEmpty()) {
			throw new Exception("Unable to locate CRM for the process, contact support!");
		}
		entity.setNonComplianceSubmittedDate(getSynchronizedDate());
		entity.setNonComplianceSubmittedUser(sessionUser);
		entity.setStatus(ApprovalEnum.PendingFinalApproval);
		entity.setNonComplianceHoldingArea(false);
		update(entity);
//		TasksService.instance().completeTaskByTargetKey(entity.getClass().getName(), entity.getId());
		TasksService.instance().findFirstInProcessAndCreateTask("", sessionUser,  entity.getId(), entity.getClass().getName(), false, ConfigDocProcessEnum.WorkplaceMonitoringNonComplianceApproval, null, crmList);
	}
	
	public void finalRejectWorkplaceMonitoring(WorkplaceMonitoringSiteVisit entity, Users sessionUser, List<RejectReasons> rejectReasonsList, Tasks tasks, Boolean complianceRejection) throws Exception{
		updateComplianceIssuesWhereCanAction(entity);
		int emailIdentifier = 0;
		entity.setStatus(ApprovalEnum.Rejected);
		entity.setNonCompliancesIdentified(complianceRejection);
		if (complianceRejection != null && complianceRejection) {
			entity.setNonComplianceApprovalDate(getSynchronizedDate());
			entity.setNonComplianceApprovalUser(sessionUser);
			emailIdentifier = 9;
		} else {
			entity.setApprovedDate(getSynchronizedDate());
			entity.setApprovedUser(sessionUser);
			entity.setNonComplianceHoldingArea(false);
			emailIdentifier = 8;
		}
		update(entity);
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDbWithTaskId(entity.getId(), entity.getClass().getName(), rejectReasonsList, sessionUser, tasks.getWorkflowProcess(), tasks.getId());
		TasksService.instance().completeTask(tasks);
		sendNotificationWorkplaceMonitoringSiteVisit(emailIdentifier, entity, null);
	}
	
	public void rejectToHoldingAreaNonCompliance(WorkplaceMonitoringSiteVisit entity, Users sessionUser, List<RejectReasons> rejectReasonsList, Tasks tasks) throws Exception{
		updateComplianceIssuesWhereCanAction(entity);
		entity.setStatus(ApprovalEnum.PendingComplianceIssues);
		entity.setNonComplianceHoldingArea(true);
		update(entity);
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDbWithTaskId(entity.getId(), entity.getClass().getName(), rejectReasonsList, sessionUser, tasks.getWorkflowProcess(), tasks.getId());
		TasksService.instance().completeTask(tasks);
		sendNotificationWorkplaceMonitoringSiteVisit(7, entity, null);
	}
	
	public void validiationBeforeFinalApproval(WorkplaceMonitoringSiteVisit entity) throws Exception{
		if (workplaceMonitoringMitigationPlanService == null) {
			workplaceMonitoringMitigationPlanService = new WorkplaceMonitoringMitigationPlanService();
		}
		if (workplaceMonitoringMitigationPlanService.countOpenMitigationPlanBySiteVisitId(entity.getId(), OpenClosedEnum.Open) > 0) {
			throw new Exception("Please close all mitigation plans before proceeding with final approval.");
		}
	}
	
	public void finalApproveNonComplianceApproval(WorkplaceMonitoringSiteVisit entity, Users sessionUser, Tasks tasks) throws Exception{
		updateComplianceIssuesWhereCanAction(entity);
		entity.setStatus(ApprovalEnum.Approved);
		entity.setNonComplianceApprovalDate(getSynchronizedDate());
		entity.setNonComplianceApprovalUser(sessionUser);
		update(entity);
		sendNotificationWorkplaceMonitoringSiteVisit(5, entity, null);
		TasksService.instance().completeTask(tasks);
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDbWithTaskId(entity.getId(), entity.getClass().getName(), new ArrayList<>(), sessionUser, tasks.getWorkflowProcess(), tasks.getId());
		finalApproveProcess(entity, sessionUser);
	}


	/*
	 * Sends notification based on notification Identifier
	 * 1. Initiated Work place Monitoring Site Visit
	 * 2. Change of date for Work place Monitoring Site Visit
	 * 3. Approved Work place Monitoring Site Visit no compliance issues
	 * 4. Approved Work place Monitoring Site Visit with compliance issues
	 * 5. All Work place Monitoring Site Visit non-compliance issues resolved
	 * 6. Site visit report
	 * 7. Rejected back to holding area for non-compliance issues
	 * 8. WPM final rejected ignoring non-compliance issues
	 * 9. WPM final rejection with non-compliance issues
	 * 
	 */
	private void sendNotificationWorkplaceMonitoringSiteVisit(Integer notificationIdentifier, WorkplaceMonitoringSiteVisit entity, List<RejectReasons> rejectReasonsList) throws Exception{
		if (usersService == null) {
			usersService = new UsersService();
		}
		boolean sendNotification = false;
		boolean withAttachment = false;
		String subject = "";
		String message = "";
		List<Users> notificationRecivers = new ArrayList<>();
		List<Users> employeeList = new ArrayList<>();
		List<AttachmentBean> attachmentBeans = new ArrayList<>();
		switch (notificationIdentifier) {
		case 1:
			subject = "";
			message = "";		
			break;
		case 2:
			subject = "";
			message = "";
			break;
		case 3:
			subject = "";
			message = "";
			break;
		case 4:
			subject = "";
			message = "";
			break;
		case 5:
			subject = "";
			message = "";
			break;
		case 6:
			subject = "";
			message = "";
			break;
		case 7:
			subject = "";
			message = "";
			break;
		case 8:
			subject = "";
			message = "";
			break;
		case 9:
			subject = "";
			message = "";
			break;
		default:
			sendNotification = false;
			break;
		}	
		if (sendNotification) {
			message = replaceStringsWorkplaceMonitoringSiteVisit(message, entity);
			notifyUsers(subject, message, notificationRecivers, withAttachment, attachmentBeans);
		}
	}
	
	public List<Users> locatePrimaryAndEmployeeSdfUsers(Company company) throws Exception{
		
		return null;
	}
	
	public List<Users> locateSdpUsers(Company company) throws Exception{
		
		return null;
	}
	
	public String replaceStringsWorkplaceMonitoringSiteVisit(String msg, WorkplaceMonitoringSiteVisit entity) throws Exception {
		/*
		 * CLO Information
		 * #CLO_FULL_INFORMATION#
		 */
		Users cloUser = null;
		if (entity != null && entity.getCompany() != null && entity.getCompany().getId() != null && entity.getCompany().getResidentialAddress() != null && entity.getCompany().getResidentialAddress().getId() != null) {
			if (entity.getManagerAssignedCloUser() != null && entity.getManagerAssignedCloUser().getId() != null) {
				cloUser = entity.getManagerAssignedCloUser();
			} else {
				cloUser = getCLO(entity.getCompany());
			}
		}
		msg = msg.replace("#CLO_FULL_INFORMATION#", ( (cloUser != null && cloUser.getId() != null) ?  cloUser.getFirstName().trim() + " " + cloUser.getLastName().trim() + " (" + cloUser.getEmail().trim() + ")" : "#UNABLE TO CLO INFORMATION#" ) );
		
		/*
		 * CRM Information
		 * #CRM_FULL_INFORMATION#
		 */
		Users crmUser = null;
		if (entity != null && entity.getCompany() != null && entity.getCompany().getId() != null && entity.getCompany().getResidentialAddress() != null && entity.getCompany().getResidentialAddress().getId() != null) {
			if (entity.getManagerAssignedCrmUser() != null && entity.getManagerAssignedCrmUser().getId() != null) {
				crmUser = entity.getManagerAssignedCrmUser();
			} else {
				crmUser = getCRM(entity.getCompany());
			}
		}
		msg = msg.replace("#CRM_FULL_INFORMATION#", ( (crmUser != null && crmUser.getId() != null) ?  crmUser.getFirstName().trim() + " " + crmUser.getLastName().trim() + " (" + crmUser.getEmail().trim() + ")" : "#UNABLE TO CRM INFORMATION#" ) );
		
		/*
		 * Region Off Description
		 * #REGIONAL_OFFICE_DESCRIPTION#
		 */
		Address address = null;
		if (entity.getCompany().getResidentialAddress() != null && entity.getCompany().getResidentialAddress().getId() != null) {
			address = AddressService.instance().findByKey(entity.getCompany().getResidentialAddress().getId());
		}
		
		RegionTown rt = null;
		if (address != null && address.getId() != null && address.getTown() != null) {
			rt = RegionTownService.instance().findByTown(address.getTown());
		}
		msg = msg.replace("#REGIONAL_OFFICE_DESCRIPTION#", ( (rt != null && rt.getId() != null && rt.getRegion() != null) ?  rt.getRegion().getDescription().trim() : "#UNABLE TO LOCATE REGION#" ) );
		
		/*
		 * Site Visit Date
		 * Tags: #SITE_VISIT_DATE#
		 */
		msg = msg.replace("#SITE_VISIT_DATE#", ( (entity != null && entity.getId() != null && entity.getMonitoringDate() != null) ?  HAJConstants.sdfDDMMYYYY2.format(entity.getMonitoringDate()) : "#UNABLE TO LOCATE SITE VISIT DATE#" ) );
		return msg;
	}
	
	public void notifyUsers(String subject, String message, List<Users> notificationRecivers, boolean withAttachment, List<AttachmentBean> attachmentBeans) {
		for (Users users : notificationRecivers) {
			String fullName = "";
			if (users.getTitle() != null && users.getTitle().getDescription() != null) {
				fullName = users.getTitle().getDescription() + " ";
			}
			fullName += users.getFirstName().trim() + " " + users.getLastName().trim();
			if (withAttachment) {
				GenericUtility.sendMailWithAttachementTempWithLog(users.getEmail(), subject, message.replace("#RECEIVER_FULL_NAME#", fullName), attachmentBeans, null);
			}else {
				GenericUtility.sendMail(users.getEmail(), subject, message.replace("#RECEIVER_FULL_NAME#", fullName), null);
			}
		}
	}
	
}