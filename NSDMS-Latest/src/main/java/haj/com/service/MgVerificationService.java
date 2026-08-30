package haj.com.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.primefaces.model.SortOrder;

import haj.com.bean.AttachmentBean;
import haj.com.bean.MgVerificationReportBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.MgVerificationDAO;
import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.CompanyUsers;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.LearnershipDevelopmentRegistration;
import haj.com.entity.MgVerification;
import haj.com.entity.MgVerificationDetails;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.SDFCompany;
import haj.com.entity.Signoff;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.AuditorMonitorReviewService;
import haj.com.service.lookup.RegionService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.utils.GenericUtility;

public class MgVerificationService extends AbstractService {
	/** The dao. */
	private MgVerificationDAO dao = new MgVerificationDAO();
	private RegionService regionService = new RegionService();
	private SDFCompanyService sdfCompanyService = new SDFCompanyService();
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	private CompanyService companyService = new CompanyService();
	private UsersService usersService = new UsersService();
	private RejectReasonsService rejectReasonsService;
	
	private static final String leftJoins = "left join fetch o.residentialAddress left join fetch o.postalAddress left join fetch o.chamber left join fetch o.sicCode left join fetch o.institutionType";

	/**
	 * Get all MgVerification
	 * 
	 * @author TechFinium
	 * @see MgVerification
	 * @return a list of {@link haj.com.entity.MgVerification}
	 * @throws Exception
	 *             the exception
	 */
	public List<MgVerification> allMgVerification() throws Exception {
		return dao.allMgVerification();
	}

	/**
	 * Create or update MgVerification.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see MgVerification
	 */
	public void create(MgVerification entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	public void createWithSignOff(MgVerification mgVerification, Users user, Tasks task) throws Exception {
		mgVerification.setStatus(ApprovalEnum.PendingSignOff);
		create(mgVerification);
		List<Signoff> signoffs = mgVerification.getSignOffs().stream().filter(sign -> !sign.getUser().equals(user)).collect(Collectors.toList());
		mgVerification.getSignOffs().stream().filter(sign -> sign.getUser().equals(user)).forEach(sign -> {
			try {
				sign.setAccept(true);
				sign.setCompleted(true);
				dao.update(sign);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		TasksService.instance().createTask(signoffs, MgVerification.class.getName(), mgVerification.getId(), "Please sign off the MG Verication Form", user, true, true, task, ConfigDocProcessEnum.MG_VERIFICATION, MailDef.instance(MailEnum.Task, MailTagsEnum.CompanyName, mgVerification.getWsp().getCompany().getCompanyName()));
	}

	public void saveSignOff(MgVerification mgVerification, Users user, Tasks task) throws Exception {
		boolean changeComplete = true;
		for (Signoff sign : mgVerification.getSignOffs()) {
			if (sign.getAccept() == null || !sign.getAccept()) {
				changeComplete = false;
			}
			if (sign.getUser().equals(user)) {
				sign.setCompleted(true);
			}
			dao.update(sign);
		}
		if (changeComplete) {
			mgVerification.setStatus(ApprovalEnum.Completed);
		}
		create(mgVerification);

		TasksService.instance().completeTask(task);
	}

	/**
	 * Update MgVerification.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see MgVerification
	 */
	public void update(MgVerification entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete MgVerification.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see MgVerification
	 */
	public void delete(MgVerification entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.MgVerification}
	 * @throws Exception
	 *             the exception
	 * @see MgVerification
	 */
	public MgVerification findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find MgVerification by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.MgVerification}
	 * @throws Exception
	 *             the exception
	 * @see MgVerification
	 */
	public List<MgVerification> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load MgVerification
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.MgVerification}
	 * @throws Exception
	 *             the exception
	 */
	public List<MgVerification> allMgVerification(int first, int pageSize) throws Exception {
		return dao.allMgVerification(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of MgVerification for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the MgVerification
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(MgVerification.class);
	}

	/**
	 * Lazy load MgVerification with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            MgVerification class
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
	 * @return a list of {@link haj.com.entity.MgVerification} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<MgVerification> allMgVerification(Class<MgVerification> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateInformation((List<MgVerification>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters));
	}

	@SuppressWarnings("unchecked")
	public List<MgVerification> sortAndFilterSearch(Class<MgVerification> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateInformation((List<MgVerification>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters));
	}

	public List<MgVerification> rejectedMgVerification(Class<MgVerification> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateInformation((List<MgVerification>) dao.rejectedMgVerification(class1, first, pageSize, sortField, sortOrder, filters));

	}

	public int countRejectedSearch(Class<MgVerification> entity, Map<String, Object> filters) throws Exception {
		return dao.countRejectedSearch(entity, filters);
	}

	public List<MgVerification> mgVerificationByStatus(Class<MgVerification> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, ApprovalEnum status) throws Exception {
		return populateInformation((List<MgVerification>) dao.mgVerificationByStatus(class1, first, pageSize, sortField, sortOrder, filters, status));

	}

	public int countByStatus(Class<MgVerification> entity, Map<String, Object> filters, ApprovalEnum status) throws Exception {
		return dao.countByStatus(entity, filters, status);
	}

	@SuppressWarnings("unchecked")
	public List<MgVerification> sortAndFilterSearchStatus(Class<MgVerification> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, ApprovalEnum status) throws Exception {
		return populateInformation((List<MgVerification>) dao.sortAndFilterSearchStatus(class1, first, pageSize, sortField, sortOrder, filters, status));

	}

	public int countSearchStatus(Class<MgVerification> entity, Map<String, Object> filters, ApprovalEnum status) throws Exception {
		return dao.countSearchStatus(entity, filters, status);
	}
	
	@SuppressWarnings("unchecked")
	public List<MgVerification> sortAndFilterSearchMgVerification(Class<MgVerification> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateInformation((List<MgVerification>) dao.sortAndFilterMgVerification(class1, first, pageSize, sortField, sortOrder, filters));
	}

	public int countSearchMgVerification(Class<MgVerification> entity, Map<String, Object> filters) throws Exception {
		return dao.countSearchMgVerification(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<MgVerification> sortAndFilterMgVerificationByRegion(Class<MgVerification> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateInformation((List<MgVerification>) dao.sortAndFilterMgVerificationByRegion(class1, first, pageSize, sortField, sortOrder, filters));
	}

	public int countSearchMgVerificationByRegion(Class<MgVerification> entity, Map<String, Object> filters) throws Exception {
		return dao.countSearchMgVerificationByRegion(entity, filters);
	}
	/**
	 * Get count of MgVerification for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            MgVerification class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the MgVerification entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<MgVerification> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public int countSearch(Class<MgVerification> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<MgVerification> allMgVerification(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from MgVerification o where o.wsp.company.residentialAddress.town.id in (select f.town.id from RegionTown f where f.crm.users.id = :userID or f.clo.users.id = :userID) and o.submitedForVerification is null";
		if (filters == null) filters = new HashMap<String, Object>();
		return populateInformation((List<MgVerification>)dao.sortAndFilterMgVerificationS(MgVerification.class, first, pageSize, sortField, sortOrder, filters, hql));
		//return populateInformation((List<MgVerification>) dao.sortAndFilterWhereMgVerification(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int count(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from MgVerification o where o.wsp.company.residentialAddress.town.id in (select f.town.id from RegionTown f where f.crm.users.id = :userID or f.clo.users.id = :userID) and o.submitedForVerification is null";
		if (filters == null) filters = new HashMap<String, Object>();
		return dao.countSearchMgVerificationS(MgVerification.class, filters, hql);
		//return dao.countWhereMgVerification(filters, hql); 
	}
	
	@SuppressWarnings("unchecked")
	public List<MgVerification> allMgVerificationForManagersNotStarted(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from MgVerification o where o.id <> null and o.status is null";
		if (filters == null) filters = new HashMap<String, Object>();
		return populateInformation((List<MgVerification>) dao.sortAndFilterMgVerificationS(MgVerification.class, first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllMgVerificationForManagersNotStarted(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from MgVerification o where o.id <> null and o.status is null";
		if (filters == null) filters = new HashMap<String, Object>();
		return dao.countSearchMgVerificationS(MgVerification.class, filters, hql);
	}

	
	@SuppressWarnings("unchecked")
	public List<MgVerification> allMgVerificationForManagers(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from MgVerification o where o.id <> null";
		if (filters == null) filters = new HashMap<String, Object>();
		return populateInformation((List<MgVerification>) dao.sortAndFilterMgVerificationS(MgVerification.class, first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countForManagers(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from MgVerification o where o.id <> null";
		if (filters == null) filters = new HashMap<String, Object>();
		return dao.countSearchMgVerificationS(MgVerification.class, filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<MgVerification> allMgVerificationForUser(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from MgVerification o where o.id in (select f.mgVerification.id from Signoff f where f.user.id = :userID) and o.submitedForVerification is null";
		if (filters == null) filters = new HashMap<String, Object>();
		return populateInformation((List<MgVerification>)dao.sortAndFilterMgVerificationS(MgVerification.class, first, pageSize, sortField, sortOrder, filters, hql));
		//return populateInformation((List<MgVerification>) dao.sortAndFilterWhereMgVerification(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countForUser(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from MgVerification o where o.id in (select f.mgVerification.id from Signoff f where f.user.id = :userID) and o.submitedForVerification is null";
		if (filters == null) filters = new HashMap<String, Object>();
		return dao.countSearchMgVerificationS(MgVerification.class, filters, hql);
		//return dao.countWhereMgVerification(filters, hql); 
	}
	
	@SuppressWarnings("unchecked")
	public List<MgVerification> allMgVerificationByClo(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from MgVerification o where o.cloGeneratedFor.id = :userID ";
		return populateInformation((List<MgVerification>)dao.sortAndFilterMgVerificationS(MgVerification.class, first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllMgVerificationByClo(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from MgVerification o where o.cloGeneratedFor.id = :userID ";
		return dao.countSearchMgVerificationS(MgVerification.class, filters, hql); 
	}
	
	@SuppressWarnings("unchecked")
	public List<MgVerification> allMgVerificationByCloNotStarted(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from MgVerification o where o.cloGeneratedFor.id = :userID and o.status is null";
		return populateInformation((List<MgVerification>)dao.sortAndFilterMgVerificationS(MgVerification.class, first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllMgVerificationByCloNotStarted(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from MgVerification o where o.cloGeneratedFor.id = :userID and o.status is null";
		return dao.countSearchMgVerificationS(MgVerification.class, filters, hql); 
	}
	
	@SuppressWarnings("unchecked")
	public List<MgVerification> allMgVerificationByCrm(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from MgVerification o where o.wsp.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :userID or x.crm.users.id = :userID) ";
		return populateInformation((List<MgVerification>)dao.sortAndFilterMgVerificationS(MgVerification.class, first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllMgVerificationByCrm(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from MgVerification o where o.wsp.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :userID or x.crm.users.id = :userID) ";
		return dao.countSearchMgVerificationS(MgVerification.class, filters, hql); 
	}
	
	@SuppressWarnings("unchecked")
	public List<MgVerification> allMgVerificationByCrmNotStarted(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from MgVerification o where o.wsp.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :userID or x.crm.users.id = :userID) and o.status is null";
		return populateInformation((List<MgVerification>)dao.sortAndFilterMgVerificationS(MgVerification.class, first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllMgVerificationByCrmNotStarted(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from MgVerification o where o.wsp.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :userID or x.crm.users.id = :userID) and o.status is null";
		return dao.countSearchMgVerificationS(MgVerification.class, filters, hql); 
	}
	
	@SuppressWarnings("unchecked")
	public List<MgVerification> allMgVerificationForReport(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from MgVerification o where o.id in (select f.mgVerification.id from Signoff f where f.user.id = :userID)";
		if (filters == null) filters = new HashMap<String, Object>();
		return populateInformation((List<MgVerification>)dao.sortAndFilterMgVerificationS(MgVerification.class, first, pageSize, sortField, sortOrder, filters, hql));
		//return populateInformation((List<MgVerification>) dao.sortAndFilterWhereMgVerification(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countForUserReport(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from MgVerification o where o.id in (select f.mgVerification.id from Signoff f where f.user.id = :userID)";
		if (filters == null) filters = new HashMap<String, Object>();
		return dao.countSearchMgVerificationS(MgVerification.class, filters, hql);
		//return dao.countWhereMgVerification(filters, hql); 
	}
	
	/**
	 * Find object by WSP Id
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see MgVerification
	 * @return a {@link haj.com.entity.MgVerification}
	 * @throws Exception
	 *             global exception
	 */
	public MgVerification findByWspId(Wsp wsp) throws Exception {
		return dao.findByWspId(wsp.getId());
	}

	private List<MgVerification> populateInformation(List<MgVerification> list) throws Exception {
		for (MgVerification mgVerification : list) {
			populateAdditionalInfo(mgVerification);
		}
		return list;
	}

	private void populateAdditionalInfo(MgVerification mgVerification) throws Exception {
		RegionTown rt = RegionTownService.instance().findByTown(mgVerification.getWsp().getCompany().getResidentialAddress().getTown());
		mgVerification.setRegionTown(rt);
		Users user = getCLO(mgVerification.getWsp());
		mgVerification.setCloUser(user);
		if (mgVerification.getWsp() != null && mgVerification.getWsp().getFinYear() != null) {
			int previousYear = mgVerification.getWsp().getFinYear() - 1;
			mgVerification.setFinYearDisplay(previousYear + "/" + mgVerification.getWsp().getFinYear());
		}else {
			mgVerification.setFinYearDisplay("");
		}
		if (mgVerification.getStatus() != null && mgVerification.getStatus() == ApprovalEnum.Rejected) {
			if (rejectReasonsService == null) {
				rejectReasonsService = new RejectReasonsService();
			}
			mgVerification.setRejectionReasons(rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcessAndReturnInString(mgVerification.getId(), mgVerification.getClass().getName(), ConfigDocProcessEnum.MG_VERIFICATION));
		}
	}

	public Region getRegion(Wsp wsp) throws Exception {
		RegionService regionService = new RegionService();
		RegionTown rt = RegionTownService.instance().findByTown(wsp.getCompany().getResidentialAddress().getTown());
		Region r = regionService.findByKey(rt.getRegion().getId());
		return r;
	}

	public Users getCLO(Wsp wsp) throws Exception {
		RegionTown rt = RegionTownService.instance().findByTown(wsp.getCompany().getResidentialAddress().getTown());
		Users cloUser = rt.getClo().getUsers();
		return cloUser;
	}
	
	public Users getCRM(Wsp wsp) throws Exception {
		RegionTown rt = RegionTownService.instance().findByTown(wsp.getCompany().getResidentialAddress().getTown());
		Users crmUser = rt.getCrm().getUsers();
		return crmUser;
	}

	public void submitMgVerificationToSdf(Users sessionUser, MgVerification entity, Tasks tasks, boolean evidanceRequired) throws Exception {		
		List<Users> users = new ArrayList<>();
		SDFCompany sdf = sdfCompanyService.findPrimarySDF(entity.getWsp().getCompany());
		if (entity.getPrimaryUserSignOff() != null) {
			users.add(entity.getPrimaryUserSignOff());
		} else if (sdf != null) {
			users.add(sdf.getSdf());
		}
		
		if (users.size() != 0) {
			// confirm what dertermains evidance required for upload
			if (evidanceRequired) {
				validiationBeforeMailNotification(1, entity);
			}
			
			String description = "";
			entity.setSubmitedForVerification(true);
			entity.setStatus(ApprovalEnum.PendingFinalApproval);
			dao.update(entity);
			
			TasksService.instance().findFirstInProcessAndCreateTask(description, sessionUser, entity.getId(), MgVerification.class.getName(), true, ConfigDocProcessEnum.MG_VERIFICATION, null, users);
			
			if (evidanceRequired) {
				sendEmailNotification(2, entity, null);
			}
			
			//TasksService.instance().createTaskUser(users, MgVerification.class.getName(), entity.getId(), description, sessionUser, true, true, tasks, ConfigDocProcessEnum.MG_VERIFICATION, true);
			// TasksService.instance().completeTask(tasks);
		} else {
			throw new Exception("No Primary SDF Assiged for the company");
		}
	}

	/*
	 * new methods for mg verification tasks
	 */
	public void submitMgVerification(Users sessionUser, MgVerification entity, Tasks tasks) throws Exception {
		// adds new entry
		entity.setStatus(ApprovalEnum.PendingApproval);
		// batch create
		dao.update(entity);
		// creates the new task
		TasksService.instance().findFirstInProcessAndCreateTask("", sessionUser, entity.getId(), LearnershipDevelopmentRegistration.class.getName(), true, ConfigDocProcessEnum.MG_VERIFICATION, null, null);
		// TasksService.instance().completeTask(tasks);
	}

	public void completeMgVerification(Users sessionUser, MgVerification entity, Tasks tasks) throws Exception {
		// adds new entry
		entity.setStatus(ApprovalEnum.PendingApproval);
		// batch create
		dao.update(entity);
		// creates the new task
		TasksService.instance().findNextInProcessAndCreateTask("", sessionUser, entity.getId(), MgVerification.class.getName(), true, tasks, null, null);
		// TasksService.instance().completeTask(tasks);
	}

	
	
	public void uploadMGVerificationEvidence(Users sessionUser, MgVerification entity, Tasks tasks) throws Exception {
		List<Users> users = new ArrayList<>();
		if(getCLO(entity.getWsp()) != null) {
			users.add(getCLO(entity.getWsp()));
			entity.setStatus(ApprovalEnum.PendingSignOff);
			update(entity);
			RejectReasonMultipleSelectionService.instance().clearEntries(entity.getId(), MgVerification.class.getName(), ConfigDocProcessEnum.MG_VERIFICATION);
			TasksService.instance().findNextInProcessAndCreateTask("", sessionUser, entity.getId(), MgVerification.class.getName(), true, tasks, null, users);
		}else {
			throw new Exception("No CLO assigned for region");
		}	
	}

	public void cloCrmReviewMgVerificationEvidence(Users sessionUser, MgVerification entity, Tasks tasks) throws Exception {
		List<Users> users = new ArrayList<>();
		if(getCRM(entity.getWsp())!=null) {
			users.add(getCRM(entity.getWsp()));
			entity.setStatus(ApprovalEnum.PendingFinalApproval);
			update(entity);
			RejectReasonMultipleSelectionService.instance().clearEntries(entity.getId(), MgVerification.class.getName(), ConfigDocProcessEnum.MG_VERIFICATION);
			TasksService.instance().findNextInProcessAndCreateTask("", sessionUser, entity.getId(), MgVerification.class.getName(), true, tasks, null, users);
		}else {
			throw new Exception("No CRM assigned for region");
		}
	}

	public void finalApproveMgVerification(Users sessionUser, MgVerification mgVerification, Tasks tasks) throws Exception {
		mgVerification.setStatus(ApprovalEnum.Approved);
		mgVerification.setApprovedDate(getSynchronizedDate());
		update(mgVerification);
		TasksService.instance().completeTaskByTargetKey(mgVerification.getClass().getName(), mgVerification.getId());
		sendEmailNotification(3, mgVerification, null);
//		sendApprovalEmailAndLetter(sessionUser,  mgVerification);
	}

	public void finalApproveMgVerification(Users sessionUser, MgVerification mgVerification) throws Exception {
		mgVerification.setStatus(ApprovalEnum.Approved);
		mgVerification.setApprovedDate(getSynchronizedDate());
		update(mgVerification);
	}

	public void crmApproveMloamgVerificationEvidence(Users sessionUser, MgVerification entity, Tasks tasks) throws Exception {
		entity.setStatus(ApprovalEnum.PendingFinalApproval);
		update(entity);
		TasksService.instance().findNextInProcessAndCreateTask("", sessionUser, entity.getId(), MgVerification.class.getName(), true, tasks, null, null);
	}

	public void seniorManagerApproveMloamgVerificationEvidence(Users sessionUser, MgVerification entity, Tasks tasks) throws Exception {
		update(entity);
		TasksService.instance().findNextInProcessAndCreateTask("", sessionUser, entity.getId(), MgVerification.class.getName(), true, tasks, null, null);
	}

	public void finalApproveTask(MgVerification mgVerification, Users user, Tasks task) throws Exception {
		mgVerification.setStatus(ApprovalEnum.Approved);
		mgVerification.setApprovedDate(getSynchronizedDate());
		update(mgVerification);
		TasksService.instance().findNextInProcessAndCreateTask("", user, mgVerification.getId(), mgVerification.getClass().getName(), true, task, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName()), null);
	}

	public void rejectTask(MgVerification mgVerification, Users user, Tasks task, ArrayList<RejectReasons> selectedRejectReason, List<Signoff> signoffList) throws Exception {
		SignoffService signoffService = new SignoffService();
		mgVerification.setApprovedDate(getSynchronizedDate());
		update(mgVerification);
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(mgVerification.getId(), MgVerification.class.getName(), selectedRejectReason, user, ConfigDocProcessEnum.MG_VERIFICATION);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		Users crmUser = getCRM(mgVerification.getWsp());
		Users cloUser = getCLO(mgVerification.getWsp());
		List<Users> users = new ArrayList<>();
		if (crmUser != null && crmUser.getId().equals(user.getId())) {
			users.add(cloUser);
			for (Signoff signoff : signoffList) {
				if (signoff.getUser() != null && signoff.getUser().getId().equals(cloUser.getId())) {
					signoff.setAccept(false);
					signoff.setSignOffDate(null);
					signoffService.update(signoff);
					break;
				}
			}
		} else {
			Users userForTask = null;
			SDFCompany sdf = sdfCompanyService.findPrimarySDF(mgVerification.getWsp().getCompany());
			if (mgVerification.getPrimaryUserSignOff() != null) {
				users.add(mgVerification.getPrimaryUserSignOff());
				userForTask = mgVerification.getPrimaryUserSignOff();
			} else if (sdf != null) {
				users.add(sdf.getSdf());
				userForTask = sdf.getSdf();
			}
			
			for (Signoff signoff : signoffList) {
				if (signoff.getUser() != null && signoff.getUser().getId().equals(userForTask.getId())) {
					signoff.setAccept(false);
					signoff.setSignOffDate(null);
					signoffService.update(signoff);
					break;
				}
			}
			// version one
//			SDFCompany sdf = sdfCompanyService.findPrimarySDF(mgVerification.getWsp().getCompany());
//			if (sdf != null) {
//				users.add(sdf.getSdf());
//			}
		}
		TasksService.instance().findPreviousInProcessAndCreateTask("", user, mgVerification.getId(), mgVerification.getClass().getName(), true, task, MailDef.instance(MailEnum.Task), users);	
		signoffService = null;
	}

	public void finalRejectTask(MgVerification entity, Users user, Tasks task, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		entity.setStatus(ApprovalEnum.Rejected);
		entity.setApprovedDate(getSynchronizedDate());
		update(entity);
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(entity.getId(), MgVerification.class.getName(), selectedRejectReason, user, ConfigDocProcessEnum.MG_VERIFICATION);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		List<Users> users = new ArrayList<>();
		if(getCLO(entity.getWsp()) != null) {
			users.add(getCLO(entity.getWsp()));
		}
		//TasksService.instance().findPreviousInProcessAndCreateTask(user, task, null);
		TasksService.instance().completeTaskByTargetKey(entity.getClass().getName(), entity.getId());
		
		sendEmailNotification(4, entity, selectedRejectReason);
//		sendRejectEmailAndLetter(user, entity, selectedRejectReason);
		//sendRejectEmail(user, entity);
		//TasksService.instance().findFirstInProcessAndCreateTask("", user, entity.getId(), MgVerification.class.getName(), true, ConfigDocProcessEnum.MG_VERIFICATION, null, null);
		//TasksService.instance().completeTask(task);
	}

	public void finalReject(MgVerification mgVerification, Users user, Tasks task, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		mgVerification.setStatus(ApprovalEnum.RejectedByMANCO);
		mgVerification.setApprovedDate(getSynchronizedDate());
		update(mgVerification);
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(mgVerification.getId(), MgVerification.class.getName(), selectedRejectReason, user, ConfigDocProcessEnum.MG_VERIFICATION);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		// TasksService.instance().completeTask(task);
	}

	public void crmApproveTask(MgVerification mgVerification, Users user, Tasks task) throws Exception {
		mgVerification.setStatus(ApprovalEnum.PendingFinalApproval);
		// mgVerification.setCrmDecision(CloRecommendationEnum.Approval);
		// mgVerification.setCrmApprovalRejectionDate(getSynchronizedDate());
		// mgVerification.setWithSdf(true);
		update(mgVerification);
		TasksService.instance().findNextInProcessAndCreateTask("", user, mgVerification.getId(), mgVerification.getClass().getName(), true, task, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName()), null);
		notifyMainSdfAndContact(true, mgVerification, null);
	}

	private void notifyMainSdfAndContact(boolean approved, MgVerification mgVerification, List<RejectReasons> rejectReasons) throws Exception {
		List<Users> users = locateClientUsersNotification(mgVerification);

		RegionTown rt = RegionTownService.instance().findByTown(mgVerification.getWsp().getCompany().getResidentialAddress().getTown());
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = regionService.findByKey(rt.getRegion().getId());
		regionService = null;
		// locates company's region CRM and CLO
		Users crmUser = rt.getCrm().getUsers();
		Users cloUser = rt.getClo().getUsers();
		for (Users notifyUsers : users) {
			if (approved) {
				/*
				 * sendSuccessfulmgVerificationOutcomeEmail(notifyUsers,
				 * mgVerification.getWsp().getCompany(),
				 * mgVerification.getWsp().getFinYear().toString(), cloUser.getFirstName() + " "
				 * + cloUser.getLastName(), crmUser, r.getDescription());
				 */
			} else {
				/*
				 * sendDiscritionaryGrandRejectionEmail(notifyUsers,
				 * mgVerification.getWsp().getCompany(),
				 * mgVerification.getWsp().getFinYear().toString(), cloUser.getFirstName() + " "
				 * + cloUser.getLastName(), rejectReasons, crmUser, r.getDescription());
				 */
			}
		}
		users = null;
		crmUser = null;
		cloUser = null;
		// sdfCompanyService = null;
		r = null;
	}

	/**
	 * Locates the additional users to notify for the SDF notification
	 * 
	 * @param mgVerification
	 * @return List<Users>
	 * @throws Exception
	 */
	private List<Users> locateClientUsersNotification(MgVerification mgVerification) throws Exception {
		List<Users> users = new ArrayList<Users>();

		if (sdfCompanyService == null) {
			sdfCompanyService = new SDFCompanyService();
		}
		users.add(sdfCompanyService.findPrimarySDF(mgVerification.getWsp().getCompany()).getSdf());

		if (companyUsersService == null) {
			companyUsersService = new CompanyUsersService();
		}

		/*
		 * Send list HR Manager (if provided), Training Manager (if provided),
		 */
		// locate Labour/Union SDF
		List<SDFCompany> sdfList = sdfCompanyService.findByCompanyAndSdfType(mgVerification.getWsp().getCompany(), (long) 3);
		for (SDFCompany sdfCompany : sdfList) {
			users.add(sdfCompany.getSdf());
			break;
		}

		// locate Employee SDF
		sdfList = sdfCompanyService.findByCompanyAndSdfType(mgVerification.getWsp().getCompany(), (long) 4);
		for (SDFCompany sdfCompany : sdfList) {
			users.add(sdfCompany.getSdf());
			break;
		}
		sdfList = null;

		// HR manager
		List<CompanyUsers> cu = companyUsersService.findByCompanyResponsibilityAndUserPosition(mgVerification.getWsp().getCompany(), ConfigDocProcessEnum.DG_VERIFICATION, (long) 3);
		for (CompanyUsers companyUsers : cu) {
			users.add(companyUsers.getUser());
			break;
		}

		// Training Manager
		cu = companyUsersService.findByCompanyResponsibilityAndUserPosition(mgVerification.getWsp().getCompany(), ConfigDocProcessEnum.DG_VERIFICATION, (long) 4);
		for (CompanyUsers companyUsers : cu) {
			users.add(companyUsers.getUser());
			break;
		}
		return users;
	}

	/**
	 * Uploads docs uploaded against LearnershipDevelopmentRegistration throws
	 * Exception if doc data is empty for permissions: Upload or EditUpload Only
	 * time documents required if permissions is: FinalUploadApproval or FinalUpload
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void uploadDocuments(MgVerification entity, Tasks task, Users sessionUser) throws Exception {
		// check if all docs provided for correct permissions
		if (task != null && (task.getProcessRole().getRolePermission() == UserPermissionEnum.Upload || task.getProcessRole().getRolePermission() == UserPermissionEnum.EditUpload || task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalUploadApproval || task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalUpload || task.getProcessRole().getRolePermission() == UserPermissionEnum.UploadApprove)) {
			// prepareNewRegistration(task.getWorkflowProcess(), entity,
			// task.getProcessRole());
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) {
						doc.setData(docByte.getData());
					}
				}
			}

			// check if data not empty and creates document
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() == null && doc.getData() != null) {
					doc.setTargetKey(entity.getId());
					doc.setTargetClass(LearnershipDevelopmentRegistration.class.getName());
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), sessionUser);
				} else {
					// if(doc.getConfigDoc().getConfigDocProcess().getConfigDocProcessEnumByValue(1).compareTo(doc.getConfigDoc().getProcessRoles().getRoleOrder())
					// )
					if (doc.getData() != null) {
						if (doc.getId() == null) {
							doc.setTargetKey(entity.getId());
							doc.setTargetClass(LearnershipDevelopmentRegistration.class.getName());
							DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), sessionUser);
						}
					} else {
						throw new Exception("Please ensure all documents are uploaded");
					}
				}
			}
		}
	}
	
	public void sendApprovalEmailAndLetter(Users user, MgVerification entity) throws Exception {
		Region r = getRegion(entity.getWsp());
		Users sdfUsers = new Users();
		Address address  = new Address();
		SDFCompany sdf = sdfCompanyService.findPrimarySDF(entity.getWsp().getCompany());
		if(entity.getWsp() != null && entity.getWsp().getCompany() != null && entity.getWsp().getCompany().getResidentialAddress()!= null) {
			address = AddressService.instance().findByKey(entity.getWsp().getCompany().getResidentialAddress().getId());
		}
		if (sdf != null) {
			sdfUsers = sdf.getSdf();
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();
		AttachmentBean letter = new AttachmentBean();
		
		JasperService.addLogo(params);
		params.put("sdf", sdfUsers);
		params.put("mgVerification", entity);
		params.put("address", address);
		params.put("company", entity.getWsp().getCompany());
		params.put("wsp", entity.getWsp());
		params.put("crm_region", r.getDescription());
		params.put("crm", user);
		
		byte[] first_bites = JasperService.instance().convertJasperReportToByte("Notification-SuccessfulMGverificationOutcome.jasper", params);
		String fileName = "DiscretionaryGrantVerification.pdf";
		letter.setExt("pdf");
		letter.setFile(first_bites);
		letter.setFilename(fileName);
		attachmentBeans.add(letter);
		
		String subject = "Mandatory Grant Verfication Outcome";
		
		String welcome = "<p>Dear #NAME# #SURNAME#,</p>" 
				+ "<p>We wish to inform you that the merSETA has completed the discretionary grant application verification.</p>"
				+ "<p>Should you have any queries please do not hesitate to contact your Client Liaison Officer: #REGION#</p>" 	
				+ "<p>Yours sincerely,</p>" 
				+ "<p>CLIENT RELATIONS MANAGER</p>" 
				+ "<br/>";

		welcome = welcome.replaceAll("#NAME#", sdfUsers.getFirstName());
		welcome = welcome.replaceAll("#SURNAME#", sdfUsers.getLastName());
		welcome = welcome.replaceAll("#REGION#",  r.getDescription());
		
		GenericUtility.sendMailWithAttachementTempWithLog(sdfUsers.getEmail(), subject, welcome, attachmentBeans, null);		
	}
	
	public void sendRejectEmailAndLetter(Users user, MgVerification entity, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		Region r = getRegion(entity.getWsp());
		Users sdfUsers = new Users();
		Address address  = new Address();
		SDFCompany sdf = sdfCompanyService.findPrimarySDF(entity.getWsp().getCompany());
		if(entity.getWsp() != null && entity.getWsp().getCompany() != null && entity.getWsp().getCompany().getResidentialAddress()!= null) {
			address = AddressService.instance().findByKey(entity.getWsp().getCompany().getResidentialAddress().getId());
		}
		if (sdf != null) {
			sdfUsers = sdf.getSdf();
		}
		
		String stringRejectReason = convertToHTML(selectedRejectReason);
		Map<String, Object> params = new HashMap<String, Object>();
		
		ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();
		AttachmentBean letter = new AttachmentBean();
		
		JasperService.addLogo(params);
		params.put("sdf", sdfUsers);
		params.put("mgVerification", entity);
		params.put("stringRejectReason", stringRejectReason);
		params.put("address", address);
		params.put("company", entity.getWsp().getCompany());
		params.put("wsp", entity.getWsp());
		params.put("region", r.getDescription());
		params.put("crm", user);
		byte[] first_bites = JasperService.instance().convertJasperReportToByte("MandatoryGrantVerificationOutcomeUnsuccessful.jasper", params);
		
		String fileName = "DiscretionaryGrantVerification.pdf";
		letter.setExt("pdf");
		letter.setFile(first_bites);
		letter.setFilename(fileName);
		attachmentBeans.add(letter);
		
		String subject = "Mandatory Grant Verfication Outcome";
		
		String welcome = "<p>Dear #NAME# #SURNAME#,</p>" 
				+ "<p>We wish to inform you that the merSETA has completed the discretionary grant application verification.</p>"
				+ "<p>Should you have any queries please do not hesitate to contact your Client Liaison Officer: #REGION#</p>" 	
				+ "<p>Yours sincerely,</p>" 
				+ "<p>CLIENT RELATIONS MANAGER</p>" 
				+ "<br/>";

		welcome = welcome.replaceAll("#NAME#", sdfUsers.getFirstName());
		welcome = welcome.replaceAll("#SURNAME#", sdfUsers.getLastName());
		welcome = welcome.replaceAll("#REGION#",  r.getDescription());
		
		GenericUtility.sendMailWithAttachementTempWithLog(sdfUsers.getEmail(), subject, welcome, attachmentBeans, null);		
	}

	public void sendRejectEmail(Users user, MgVerification entity) throws Exception {
		Region r = getRegion(entity.getWsp());
		Users sdfUsers = new Users();
		SDFCompany sdf = sdfCompanyService.findPrimarySDF(entity.getWsp().getCompany());
		if (sdf != null) {
			sdfUsers = sdf.getSdf();
		}
		
		String subject = "DISCRETIONARY GRANT APPLICATION FOR: #COMPANY# (#ENTITY_ID#)";
		
		String welcome = "<p>#DATE#,</p>" 
				+ "<p>Dear #NAME# #SURNAME#,</p>" 
				+ "<p>Thank you for submitting a discretionary grant application for #YEAR#.</p>"
				+ "<p>We regret to advise that the application has not been successful. Should you wish to appeal the outcome, please submit an appeal on the NSDMS within 14 days of receipt of this notification.</p>" 	
				+ "<p>Should you have any queries please do not hesitate to contact your Client Liaison Officer: #CLIENTLIAISONOFFICER# for assistance.</p>" 
				+ "<p>Yours sincerely,</p>" 
				+ "<p>CLIENT RELATIONS MANAGER</p>" 
				+ "<br/>";
		welcome = welcome.replaceAll("#DATE#", user.getFirstName());
		welcome = welcome.replaceAll("#YEAR#", user.getFirstName());
		welcome = welcome.replaceAll("#NAME#", user.getFirstName());
		welcome = welcome.replaceAll("#SURNAME#", user.getLastName());		
		welcome = welcome.replaceAll("#COMPANY#", user.getFirstName());
		welcome = welcome.replaceAll("#ENTITY_ID#", user.getLastName());
		welcome = welcome.replaceAll("#CLIENTLIAISONOFFICER#", user.getLastName());
		
		GenericUtility.sendMail(sdfUsers.getEmail(), subject, welcome, null);
	}
	
	public String convertToHTML(List<RejectReasons> rejectReasons){		
		String sb ="<ul>"; 
		for (RejectReasons r: rejectReasons){
			sb +="<li>"+r.getDescription() +"</li>";
		}
		sb +="</ul>"; 
		return sb;
	}
	
	/**
	 * Sends email notification based on indicator
	 * Before using must confirm comapny has the following:
	 * 
	 * Wsp with company assigned
	 * Primary SDF
	 * 
	 * Otherwise will error
	 * 
	 * 1 - Notification on review date add / update
	 * 2 - Evidence required by SDF
	 * 3 - Approved MG verification
	 * 4 - Rejected MG verification
	 * 
	 * Can optimize will return at a later time 
	 * 
	 * @param indicator
	 * @throws Exception
	 */
	public void sendEmailNotification(int indicator, MgVerification mgVerification, List<RejectReasons> selectedRejectReason)throws Exception{
		switch (indicator) {
		case 1:
			// notification for review date amendment
			sendNotificationDateAmended(mgVerification);
			break;
		case 2:
			// notification for evidence required by SDF
			sendNotificationEvidence(mgVerification);
			break;
		case 3:
			// notification on approved MG Verification
			sendNotificationSuccesfulOutcome(mgVerification);
			break;
		case 4:
			// notification on rejected MG Verification
			sendNotificationUnsuccessfulOutcome(mgVerification, selectedRejectReason);
			break;
		default:
			break;
		}
	}
	
	/*
	 * Validation on objects and fields being used for notification
	 * indicator not applicable at the moment 
	 */
	public void validiationBeforeMailNotification(int indicator, MgVerification mgVerification) throws Exception{
		String errors = "";
		
		// Validate Company
		Company company = companyService.findByKey(mgVerification.getWsp().getCompany().getId());
		if (company == null) {
			
			errors = errors + " Unable to locate company from MG Verification ";
			
		} else {
			
			if (indicator == 3 || indicator == 4) {
				Address address = AddressService.instance().findByKey(company.getResidentialAddress().getId());
				if (address == null) {
					errors = errors + " Unable to locate address for company from MG Verification ";
				}		
				address = null;
			}
			
			// validiates primary SDF
			SDFCompany primarySdfLink =  sdfCompanyService.locateFirstPrimarySDF(company);
			if (primarySdfLink == null) {
				errors = errors + " Unable to locate primary SDF for company from MG Verification ";
			} else {
				Users primarySdfUser = usersService.findByKey(primarySdfLink.getSdf().getId());
				if (primarySdfUser == null) {
					errors = errors + " Unable to locate primary SDF for company from MG Verification ";
				}
				primarySdfUser = null;
			}
			
			// Validate region
			RegionTown companyRegion = RegionTownService.instance().findByTown(mgVerification.getWsp().getCompany().getResidentialAddress().getTown());
			if (companyRegion == null) {
				errors = errors + " Unable to locate Region For company from MG Verification ";
			} else {
				// Validate CLO CRM Users
				Users cloUser = usersService.findByKey(companyRegion.getClo().getUsers().getId());
				if (cloUser == null) {
					errors = errors + " Unable to locate CLO For company from MG Verification ";	
				}
				Users crmUser = usersService.findByKey(companyRegion.getCrm().getUsers().getId());
				if (crmUser == null) {
					errors = errors + " Unable to locate CRM For company from MG Verification ";	
				}
				
				// null objects
				cloUser = null;
				crmUser = null;
			}
			
			// null objects
			companyRegion = null;
			primarySdfLink = null;
		}
		
		// null objects
		company = null;
	
		if (!errors.equals("")) {
			GenericUtility.mailError("MG VERIFICATION MAIL VALIDIATION FAILED: MG ID: " + mgVerification.getId() + ". MAIL INDICATOR: " + indicator, "Validation for email notification failed. Reasons: " + errors);
			throw new Exception("Validation for notification failed. Reasons: " + errors);
			
		}
	}
	
	/**
	 * Sends notification to primary SDF and Company CLO about date amended
	 * 
	 * Required:
	 * Company assigned to MG verification
	 * Primary SDF assigned to company
	 * Address assigned to company
	 * CLO assigned to MG Verification
	 * CRM must be assigned to region
	 * 
	 * Confirm Users sending to
	 * 
	 * @param mgVerification
	 * @throws Exception
	 */
	public void sendNotificationDateAmended(MgVerification mgVerification) throws Exception{
		/*
		 *  ref doc: E-mail Notification- MG Verification Date Notification.doc
		 */
		
		// list of users who must receive the email notification
		List<Users> recivers = new ArrayList<>();
		
		// locates the company
		Company company = companyService.findByKey(mgVerification.getWsp().getCompany().getId());
		
		// locates region
		RegionTown companyRegion = RegionTownService.instance().findByTown(mgVerification.getWsp().getCompany().getResidentialAddress().getTown());
		
		// locates primary SDF
		SDFCompany primarySdfLink =  sdfCompanyService.locateFirstPrimarySDF(company);
		Users primarySdfUser = usersService.findByKey(primarySdfLink.getSdf().getId());
		recivers.add(primarySdfUser);
		
		// locates CLO and CRM assigned to MG Verification
		Users cloUser = usersService.findByKey(companyRegion.getClo().getUsers().getId());
		Users crmUser = usersService.findByKey(companyRegion.getCrm().getUsers().getId());
		
		// sets subject
		String subject = "MG VERIFICATION DATE NOTIFICATION";
		// sets body 
		String body = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #SDF_FIRST_NAME# #SDF_LAST_NAME#, </p>" + 
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\"><b>NOTIFICATION OF MANDATORY GRANT VERIFICATION FOR: #COMPANY_NAME# (#ENTITY_ID#)</b></p>" + 
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\">We write to inform you that the merSETA will be visiting your establishment to conduct a mandatory grant application verification. The mandatory grant application verification will be conducted by the designated Client Liaison Officer: #CLO_FIRST_NAME# #CLO_LAST_NAME# on #VISIT_DATE#.</p>" +
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The mandatory grant verification will focus on interventions that were planned for implementation and were to be funded using mandatory grant funding. Kindly note that you may be required to upload evidence pertaining to each intervention after the visit. Evidence may be in the form of proof of learner registration or proof of learner withdrawal(s) or explanation for why planned training has not commenced or proof of learner completion.</p>" +
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Should there be a change in the scheduled date, please contact the Client Liaison Officer before the visit to schedule a new date.</p>" +
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please do not hesitate to contact the Client Liaison Officer or Regional Office for further assistance.</p>" +
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> #CRM_FIRST_NAME# #CRM_LAST_NAME#" + 
				"<br/>" + 
				"<b>CLIENT RELATIONS MANAGER: #REGION#</b></p>";
		
		body = body.replace("#SDF_FIRST_NAME#", primarySdfUser.getFirstName().trim());
		body = body.replace("#SDF_LAST_NAME#", primarySdfUser.getLastName().trim());
		
		body = body.replace("#COMPANY_NAME#", company.getCompanyName().trim());
		body = body.replace("#ENTITY_ID#", company.getLevyNumber().trim());
		
		body = body.replace("#CLO_FIRST_NAME#", cloUser.getFirstName().trim());
		body = body.replace("#CLO_LAST_NAME#", cloUser.getLastName().trim());
		
		body = body.replace("#CRM_FIRST_NAME#", crmUser.getFirstName().trim());
		body = body.replace("#CRM_LAST_NAME#", crmUser.getLastName().trim());
		
		body = body.replace("#VISIT_DATE#", HAJConstants.sdfDDMMYYYY2.format(mgVerification.getReviewDate()));
		
		body = body.replace("#REGION#", companyRegion.getRegion().getDescription().trim());
		
		for (Users users : recivers) {
			GenericUtility.sendMail(users.getEmail(), subject, body, null);
//			GenericUtility.sendMail("testemail@a.com", subject, body, null);
		}
		
		// null objects
		company = null;
		companyRegion = null;
		primarySdfLink = null;
		primarySdfUser = null;
		cloUser = null;
		crmUser = null;
		recivers = null;
	}
	
	/**
	 * Sends notification to primary SDF to upload evidence against each line item on the MG Verirification.
	 * 
	 * Confirm Users sending to
	 * 
	 * @param mgVerification
	 * @throws Exception
	 */
	public void sendNotificationEvidence(MgVerification mgVerification) throws Exception{
		/*
		 *  ref doc: E-mail Notification - MG Verification Evidence Notification.doc
		 */
		// list of users who must receive the email notification
		List<Users> recivers = new ArrayList<>();
		
		// locates the company
		Company company = companyService.findByKey(mgVerification.getWsp().getCompany().getId());
		
		// locates region
		RegionTown companyRegion = RegionTownService.instance().findByTown(mgVerification.getWsp().getCompany().getResidentialAddress().getTown());
		
		// locates primary SDF
		SDFCompany primarySdfLink =  sdfCompanyService.locateFirstPrimarySDF(company);
		Users primarySdfUser = usersService.findByKey(primarySdfLink.getSdf().getId());
		recivers.add(primarySdfUser);
		
		// locates CLO and CRM assigned to MG Verification
		Users cloUser = usersService.findByKey(companyRegion.getClo().getUsers().getId());
		Users crmUser = usersService.findByKey(companyRegion.getCrm().getUsers().getId());
		
		// sets subject
		String subject = "MG VERIFICATION EVIDENCE NOTIFICATION";
		// sets body 
		String body = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #SDF_FIRST_NAME# #SDF_LAST_NAME#, </p>" + 
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\"><b>NOTIFICATION OF MANDATORY GRANT VERIFICATION FOR: #COMPANY_NAME# (#ENTITY_ID#)</b></p>" + 
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Kindly be advised that the mandatory grant verification visit was conducted by Client Liaison Officer: #CLO_FIRST_NAME# #CLO_LAST_NAME# on #VISIT_DATE#.</p>" +
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please upload evidence against each planned intervention. Evidence may be in the form of proof of learner registration or proof of learner withdrawal(s) or explanation for why planned training has not commenced or proof of learner completion.</p>" +
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\">For queries/support, please contact the Client Liaison Officer or Regional Office.</p>" +
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> #CRM_FIRST_NAME# #CRM_LAST_NAME#" + 
				"<br/>" + 
				"<b>CLIENT RELATIONS MANAGER: #REGION#</b></p>";
		body = body.replace("#SDF_FIRST_NAME#", primarySdfUser.getFirstName().trim());
		body = body.replace("#SDF_LAST_NAME#", primarySdfUser.getLastName().trim());
		
		body = body.replace("#COMPANY_NAME#", company.getCompanyName().trim());
		body = body.replace("#ENTITY_ID#", company.getLevyNumber().trim());
		
		body = body.replace("#CLO_FIRST_NAME#", cloUser.getFirstName().trim());
		body = body.replace("#CLO_LAST_NAME#", cloUser.getLastName().trim());
		
		body = body.replace("#CRM_FIRST_NAME#", crmUser.getFirstName().trim());
		body = body.replace("#CRM_LAST_NAME#", crmUser.getLastName().trim());
		
		body = body.replace("#VISIT_DATE#", HAJConstants.sdfDDMMYYYY2.format(mgVerification.getReviewDate()));
		
		body = body.replace("#REGION#", companyRegion.getRegion().getDescription().trim());
		
		for (Users users : recivers) {
			GenericUtility.sendMail(users.getEmail(), subject, body, null);
//			GenericUtility.sendMail("testemail@a.com", subject, body, null);
		}
		
		// null objects
		company = null;
		companyRegion = null;
		primarySdfLink = null;
		primarySdfUser = null;
		cloUser = null;
		crmUser = null;
		recivers = null;
	}
	
	/**
	 * Sends notification to primary SDF about MG Verification being approved.
	 * Also attaches letter
	 * 
	 * Confirm Users sending to
	 * 
	 * @param mgVerification
	 * @throws Exception
	 */
	public void sendNotificationSuccesfulOutcome(MgVerification mgVerification) throws Exception{
		/*
		 *  ref doc: E-mail Notification - SUCCESSFUL MG verification Outcome.doc
		 */
		// list of users who must receive the email notification
		List<Users> recivers = new ArrayList<>();
		
		// locates the company
		Company company = companyService.findByKey(mgVerification.getWsp().getCompany().getId());
		
		// locates company address for letter
		Address address  = AddressService.instance().findByKey(company.getResidentialAddress().getId());
		
		// locates region
		RegionTown companyRegion = RegionTownService.instance().findByTown(mgVerification.getWsp().getCompany().getResidentialAddress().getTown());
		
		// locates primary SDF
		SDFCompany primarySdfLink =  sdfCompanyService.locateFirstPrimarySDF(company);
		Users primarySdfUser = usersService.findByKey(primarySdfLink.getSdf().getId());
		recivers.add(primarySdfUser);
		
		// locates CLO and CRM assigned to MG Verification
		Users cloUser = usersService.findByKey(companyRegion.getClo().getUsers().getId());
		Users crmUser = usersService.findByKey(companyRegion.getCrm().getUsers().getId());
		
		/* Builds Email Contents */
		// sets subject
		String subject = "MG VERIFICATION SUCCESSFUL OUTCOME";
		// sets body 
		String body = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #SDF_FIRST_NAME# #SDF_LAST_NAME#, </p>" + 
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\"><b>MANDATORY GRANT VERIFICATION OUTCOME FOR: #COMPANY_NAME# (#ENTITY_ID#)</b></p>" + 
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Kindly be advised that the mandatory grant verification visit was conducted by Client Liaison Officer: #CLO_FIRST_NAME# #CLO_LAST_NAME# on #VISIT_DATE#.</p>" +
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The evidence submitted by the entity has been reviewed and has been found to be adequate/sufficient to support the reported training implementation.</p>" +
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Should you have any queries please do not hesitate to contact your Client Liaison Officer: #CLO_FIRST_NAME# #CLO_LAST_NAME# for assistance.</p>" +
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" +
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> #CRM_FIRST_NAME# #CRM_LAST_NAME#" + 
				"<br/>" + 
				"<b>CLIENT RELATIONS MANAGER: #REGION#</b></p>";
		body = body.replace("#SDF_FIRST_NAME#", primarySdfUser.getFirstName().trim());
		body = body.replace("#SDF_LAST_NAME#", primarySdfUser.getLastName().trim());
		
		body = body.replace("#COMPANY_NAME#", company.getCompanyName().trim());
		body = body.replace("#ENTITY_ID#", company.getLevyNumber().trim());
		
		body = body.replace("#CLO_FIRST_NAME#", cloUser.getFirstName().trim());
		body = body.replace("#CLO_LAST_NAME#", cloUser.getLastName().trim());
		
		body = body.replace("#CRM_FIRST_NAME#", crmUser.getFirstName().trim());
		body = body.replace("#CRM_LAST_NAME#", crmUser.getLastName().trim());
		
		body = body.replace("#VISIT_DATE#", HAJConstants.sdfDDMMYYYY2.format(mgVerification.getReviewDate()));
		
		body = body.replace("#REGION#", companyRegion.getRegion().getDescription().trim());
		
//		/* Generates Jasper Report */
//		Map<String, Object> params = new HashMap<String, Object>();
//		ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();
//		AttachmentBean letter = new AttachmentBean();
//		JasperService.addLogo(params);
//		params.put("sdf", primarySdfUser);
//		params.put("mgVerification", mgVerification);
//		params.put("address", address);
//		params.put("company", company);
//		params.put("wsp", mgVerification.getWsp());
//		params.put("crm_region", companyRegion.getRegion().getDescription().trim());
//		params.put("crm", crmUser);
//		byte[] first_bites = JasperService.instance().convertJasperReportToByte("Notification-SuccessfulMGverificationOutcome.jasper", params);
//		String fileName = "DiscretionaryGrantVerification.pdf";
//		letter.setExt("pdf");
//		letter.setFile(first_bites);
//		letter.setFilename(fileName);
//		attachmentBeans.add(letter);
		
		for (Users users : recivers) {
			GenericUtility.sendMail(users.getEmail(), subject, body, null);
		}
		
//		for (Users users : recivers) {
//			GenericUtility.sendMailWithAttachementTempWithLog(users.getEmail(), subject, body, attachmentBeans, null);
//		}
		
		// null objects
		company = null;
		address = null;
		companyRegion = null;
		primarySdfLink = null;
		primarySdfUser = null;
		cloUser = null;
		crmUser = null;
		recivers = null;
	}
	
	/**
	 * Sends notification to primary SDF and Company CLO about Unsuccessful Outcome.
	 * 
	 * Confirm Users sending to
	 * 
	 * @param mgVerification
	 * @param selectedRejectReason 
	 * @throws Exception
	 */
	public void sendNotificationUnsuccessfulOutcome(MgVerification mgVerification, List<RejectReasons> selectedRejectReason) throws Exception{
		/*
		 *  ref doc: E-mail Notification - MG verification Unsuccessful Outcome.doc
		 */
		
		// list of users who must receive the email notification
		List<Users> recivers = new ArrayList<>();
		
		// locates the company
		Company company = companyService.findByKey(mgVerification.getWsp().getCompany().getId());
		
		// locates company address for letter
		Address address  = AddressService.instance().findByKey(company.getResidentialAddress().getId());
		
		// locates region
		RegionTown companyRegion = RegionTownService.instance().findByTown(mgVerification.getWsp().getCompany().getResidentialAddress().getTown());
		
		// locates primary SDF
		SDFCompany primarySdfLink =  sdfCompanyService.locateFirstPrimarySDF(company);
		Users primarySdfUser = usersService.findByKey(primarySdfLink.getSdf().getId());
		recivers.add(primarySdfUser);
		
		// locates CLO and CRM assigned to MG Verification
		Users cloUser = usersService.findByKey(companyRegion.getClo().getUsers().getId());
		Users crmUser = usersService.findByKey(companyRegion.getCrm().getUsers().getId());
		
		// sets subject
		String subject = "MG VERIFICATION UNSUCCESSFUL OUTCOME";
		// sets body 
		String body = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #SDF_FIRST_NAME# #SDF_LAST_NAME#, </p>" + 
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\"><b>MANDATORY GRANT VERIFICATION OUTCOME FOR: #COMPANY_NAME# (#ENTITY_ID#)</b></p>" + 
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Kindly be advised that the mandatory grant verification visit was conducted by Client Liaison Officer: #CLO_FIRST_NAME# #CLO_LAST_NAME# on #VISIT_DATE#.</p>" +
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The evidence submitted by the entity has been reviewed and has not been found to be adequate/sufficient to support the reported training implementation. Kindly note that the merSETA may conduct further verification during other site visits.</p>" +
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Should you have any queries please do not hesitate to contact your Client Liaison Officer: #CLO_FIRST_NAME# #CLO_LAST_NAME# for assistance.</p>" +
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" +
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> #CRM_FIRST_NAME# #CRM_LAST_NAME#" + 
				"<br/>" + 
				"<b>CLIENT RELATIONS MANAGER: #REGION#</b></p>";
		body = body.replace("#SDF_FIRST_NAME#", primarySdfUser.getFirstName().trim());
		body = body.replace("#SDF_LAST_NAME#", primarySdfUser.getLastName().trim());
		
		body = body.replace("#COMPANY_NAME#", company.getCompanyName().trim());
		body = body.replace("#ENTITY_ID#", company.getLevyNumber().trim());
		
		body = body.replace("#CLO_FIRST_NAME#", cloUser.getFirstName().trim());
		body = body.replace("#CLO_LAST_NAME#", cloUser.getLastName().trim());
		
		body = body.replace("#CRM_FIRST_NAME#", crmUser.getFirstName().trim());
		body = body.replace("#CRM_LAST_NAME#", crmUser.getLastName().trim());
		
		body = body.replace("#VISIT_DATE#", HAJConstants.sdfDDMMYYYY2.format(mgVerification.getReviewDate()));
		
		body = body.replace("#REGION#", companyRegion.getRegion().getDescription().trim());
		
		for (Users users : recivers) {
			GenericUtility.sendMail(users.getEmail(), subject, body, null);
		}
		
//		/* Generates Jasper Report */
//		Map<String, Object> params = new HashMap<String, Object>();
//		ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();
//		AttachmentBean letter = new AttachmentBean();
//		JasperService.addLogo(params);
//		params.put("sdf", primarySdfUser);
//		params.put("mgVerification", mgVerification);
//		params.put("address", address);
//		params.put("company", company);
//		params.put("wsp", mgVerification.getWsp());
//		params.put("crm_region", companyRegion.getRegion().getDescription().trim());
//		params.put("crm", crmUser);
//		byte[] first_bites = JasperService.instance().convertJasperReportToByte("MandatoryGrantVerificationOutcomeUnsuccessful.jasper", params);
//		String fileName = "DiscretionaryGrantVerification.pdf";
//		letter.setExt("pdf");
//		letter.setFile(first_bites);
//		letter.setFilename(fileName);
//		attachmentBeans.add(letter);
//		
//		for (Users users : recivers) {
//			GenericUtility.sendMailWithAttachementTempWithLog(users.getEmail(), subject, body, attachmentBeans, null);
//		}
		
		// null objects
		company = null;
		companyRegion = null;
		primarySdfLink = null;
		primarySdfUser = null;
		cloUser = null;
		crmUser = null;
		recivers = null;
	}
	
	/*
	 * Reporting Start
	 */
	
	public List<MgVerificationReportBean> allMandatoryVerificationsByFinYearReport(Integer finYear) throws Exception {
		return dao.allMandatoryVerificationsByFinYearReport(finYear);
	}
	
	/*
	 * Reporting End
	 */
	
}
