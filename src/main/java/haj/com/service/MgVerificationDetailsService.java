package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.primefaces.model.SortOrder;

import haj.com.dao.MgVerificationDetailsDAO;
import haj.com.entity.Company;
import haj.com.entity.CompanyUsers;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.LearnershipDevelopmentRegistration;
import haj.com.entity.MandatoryGrant;
import haj.com.entity.MgVerification;
import haj.com.entity.MgVerificationDetails;
import haj.com.entity.ProcessRoles;
import haj.com.entity.SDFCompany;
import haj.com.entity.Tasks;
import haj.com.entity.TrainingProviderMonitoring;
import haj.com.entity.TrainingProviderVerfication;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.WspReportEnum;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RegionTown;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.RegionService;
import haj.com.service.lookup.RegionTownService;

public class MgVerificationDetailsService extends AbstractService {
	/** The dao. */
	private MgVerificationDetailsDAO dao = new MgVerificationDetailsDAO();
	private RegionService regionService = new RegionService();
	private SDFCompanyService sdfCompanyService = new SDFCompanyService();
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	private ConfigDocService configDocService = new ConfigDocService();

	/**
	 * Get all MgVerificationDetails
	 * 
	 * @author TechFinium
	 * @see MgVerificationDetails
	 * @return a list of {@link haj.com.entity.MgVerificationDetails}
	 * @throws Exception
	 *             the exception
	 */
	public List<MgVerificationDetails> allMgVerificationDetails() throws Exception {
		return dao.allMgVerificationDetails();
	}

	/**
	 * Create or update MgVerificationDetails.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see MgVerificationDetails
	 */
	public void create(MgVerificationDetails entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	public void prepareNewRegistration(ConfigDocProcessEnum configDocProcess, MgVerification entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null) {

			if (processRoles == null) entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			else if (processRoles.getCompanyUserType() == null) entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			else entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), processRoles));

			List<ConfigDoc> l = ConfigDocService.instance().findByProcessNotUploaded(MgVerification.class.getName(), entity.getId(), configDocProcess);

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

	/*
	 * public void createWithSignOff(MgVerificationDetails MgVerificationDetails,
	 * Users user, Tasks task) throws Exception {
	 * MgVerificationDetails.setStatus(ApprovalEnum.PendingSignOff);
	 * create(MgVerificationDetails); List<Signoff> signoffs =
	 * MgVerificationDetails.getSignOffs().stream().filter(sign ->
	 * !sign.getUser().equals(user)).collect(Collectors.toList());
	 * MgVerificationDetails.getSignOffs().stream().filter(sign ->
	 * sign.getUser().equals(user)).forEach(sign -> { try { sign.setAccept(true);
	 * sign.setCompleted(true); dao.update(sign); } catch (Exception e) {
	 * e.printStackTrace(); } });
	 * 
	 * TasksService.instance().createTask(signoffs,
	 * MgVerificationDetails.class.getName(), MgVerificationDetails.getId(),
	 * "Please sign off the MG Verication Form", user, true, true, task,
	 * ConfigDocProcessEnum.MG_VERIFICATION, MailDef.instance(MailEnum.Task,
	 * MailTagsEnum.CompanyName,
	 * MgVerificationDetails.getWsp().getCompany().getCompanyName())); }
	 */

	/*
	 * public void saveSignOff(MgVerificationDetails MgVerificationDetails, Users
	 * user, Tasks task) throws Exception { boolean changeComplete = true; for
	 * (Signoff sign : MgVerificationDetails.getSignOffs()) { if (sign.getAccept()
	 * == null || !sign.getAccept()) { changeComplete = false; } if
	 * (sign.getUser().equals(user)) { sign.setCompleted(true); } dao.update(sign);
	 * } if (changeComplete) {
	 * MgVerificationDetails.setStatus(ApprovalEnum.Completed); }
	 * create(MgVerificationDetails);
	 * 
	 * TasksService.instance().completeTask(task); }
	 */

	/**
	 * Update MgVerificationDetails.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see MgVerificationDetails
	 */
	public void update(MgVerificationDetails entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete MgVerificationDetails.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see MgVerificationDetails
	 */
	public void delete(MgVerificationDetails entity) throws Exception {
		this.dao.delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<MgVerificationDetails> allMgVerificationDetails(Class<MgVerificationDetails> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Wsp wsp) throws Exception {
		String hql = "select o from MgVerificationDetails o where o.mandatoryGrantDetail.wsp.id = :wspId";
		filters.put("wspId", wsp.getId());
		return populate((List<MgVerificationDetails>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	@SuppressWarnings("unchecked")
	public List<MgVerificationDetails> allMgVerificationDetailsByWspId(Class<MgVerificationDetails> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Wsp wsp) throws Exception {
		String hql = "select o from MgVerificationDetails o where o.wsp.id = :wspId";
		filters.put("wspId", wsp.getId());
		return populate((List<MgVerificationDetails>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public List<MgVerificationDetails> allMgVerificationDetailsWsp(Wsp wsp)throws Exception{
		return populate(dao.mgVerificationDetailsInfoWsp(wsp.getId()));
	}
	
	public List<MgVerificationDetails> mgVerificationDetailsInfoByWsp(Wsp wsp) throws Exception{
		return populate(dao.mgVerificationDetailsInfoByWsp(wsp.getId()));
	}
	
	private List<MgVerificationDetails> populate(List<MgVerificationDetails> list) throws Exception {
		for(MgVerificationDetails entity:list) {
			populateDoc(entity);
		}
		return list;
	}
	
	private void populateDoc(MgVerificationDetails entity) throws Exception {
		List<Doc> docs = DocService.instance().searchByTargetKeyAndClass(MgVerificationDetails.class.getName(), entity.getId());
		if(docs.size()>0) {
			entity.setDoc(docs.get(docs.size() - 1));
			entity.setDocs(docs);
		} else {
			entity.setDoc(new Doc());
			entity.setDocs(new ArrayList<>());
		}
	}

	public List<MgVerificationDetails> prepareMGVerificationDetailsDocs(List<MgVerificationDetails> list) throws Exception {
		for (MgVerificationDetails mg : list) {
			prepareNewRegistration(mg);
		}
		return list;
	}

	public void resolveMGDocs(MgVerificationDetails mg) throws Exception {
		List<ConfigDoc> l = ConfigDocService.instance().findByProcessNotForUpload(MgVerification.class.getName(), mg.getId(), ConfigDocProcessEnum.MG_VERIFICATION);
		// (String targetClass, Long targetKey, ConfigDocProcessEnum configDocProcess)
		if (l != null && l.size() > 0) {
			l.forEach(a -> {
				mg.getDocs().add(new Doc(a));
			});
		}
	}

	public void prepareNewRegistration(MgVerificationDetails entity) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), ConfigDocProcessEnum.MG_VERIFICATION));
			List<ConfigDoc> l = configDocService.findByProcessNotUploaded(entity.getClass().getName(), entity.getId(), ConfigDocProcessEnum.MG_VERIFICATION, CompanyUserTypeEnum.User);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = configDocService.findByProcess(ConfigDocProcessEnum.MG_VERIFICATION, CompanyUserTypeEnum.User);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}

	public int count(Class<MgVerificationDetails> entity, Map<String, Object> filters, Wsp company) throws Exception {
		String hql = "select count(o) from MgVerificationDetails o where o.mandatoryGrantDetail.wsp.id = :wspId";
		filters.put("wspId", company.getId());
		return dao.countWhere(filters, hql);
	}
	
	public int countByWspId(Class<MgVerificationDetails> entity, Map<String, Object> filters, Wsp company) throws Exception {
		String hql = "select count(o) from MgVerificationDetails o where o.wsp.id = :wspId";
		filters.put("wspId", company.getId());
		return dao.countWhere(filters, hql);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.MgVerificationDetails}
	 * @throws Exception
	 *             the exception
	 * @see MgVerificationDetails
	 */
	public MgVerificationDetails findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find MgVerificationDetails by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.MgVerificationDetails}
	 * @throws Exception
	 *             the exception
	 * @see MgVerificationDetails
	 */
	public List<MgVerificationDetails> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load MgVerificationDetails
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.MgVerificationDetails}
	 * @throws Exception
	 *             the exception
	 */
	public List<MgVerificationDetails> allMgVerificationDetails(int first, int pageSize) throws Exception {
		return dao.allMgVerificationDetails(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of MgVerificationDetails for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the MgVerificationDetails
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(MgVerificationDetails.class);
	}

	/**
	 * Lazy load MgVerificationDetails with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            MgVerificationDetails class
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
	 * @return a list of {@link haj.com.entity.MgVerificationDetails} containing
	 *         data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<MgVerificationDetails> allMgVerificationDetails(Class<MgVerificationDetails> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateInformation((List<MgVerificationDetails>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters));
	}

	@SuppressWarnings("unchecked")
	public List<MgVerificationDetails> sortAndFilterSearch(Class<MgVerificationDetails> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateInformation((List<MgVerificationDetails>) dao.sortAndFilterSearch(class1, first, pageSize, sortField, sortOrder, filters));

	}

	public List<MgVerificationDetails> rejectedMgVerificationDetails(Class<MgVerificationDetails> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateInformation((List<MgVerificationDetails>) dao.rejectedMgVerificationDetails(class1, first, pageSize, sortField, sortOrder, filters));

	}

	public int countRejectedSearch(Class<MgVerificationDetails> entity, Map<String, Object> filters) throws Exception {
		return dao.countRejectedSearch(entity, filters);
	}

	public List<MgVerificationDetails> MgVerificationDetailsByStatus(Class<MgVerificationDetails> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, ApprovalEnum status) throws Exception {
		return populateInformation((List<MgVerificationDetails>) dao.MgVerificationDetailsByStatus(class1, first, pageSize, sortField, sortOrder, filters, status));

	}

	public int countByStatus(Class<MgVerificationDetails> entity, Map<String, Object> filters, ApprovalEnum status) throws Exception {
		return dao.countByStatus(entity, filters, status);
	}

	@SuppressWarnings("unchecked")
	public List<MgVerificationDetails> sortAndFilterSearchStatus(Class<MgVerificationDetails> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, ApprovalEnum status) throws Exception {
		return populateInformation((List<MgVerificationDetails>) dao.sortAndFilterSearchStatus(class1, first, pageSize, sortField, sortOrder, filters, status));

	}

	public int countSearchStatus(Class<MgVerificationDetails> entity, Map<String, Object> filters, ApprovalEnum status) throws Exception {
		return dao.countSearchStatus(entity, filters, status);
	}

	/**
	 * Get count of MgVerificationDetails for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            MgVerificationDetails class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the MgVerificationDetails entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<MgVerificationDetails> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public int countSearch(Class<MgVerificationDetails> entity, Map<String, Object> filters) throws Exception {
		return dao.countSearch(entity, filters);
	}

	/**
	 * Find object by WSP Id
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see MgVerificationDetails
	 * @return a {@link haj.com.entity.MgVerificationDetails}
	 * @throws Exception
	 *             global exception
	 */
	public MgVerificationDetails findByWspId(Wsp wsp) throws Exception {
		return dao.findByWspId(wsp.getId());
	}

	private List<MgVerificationDetails> populateInformation(List<MgVerificationDetails> list) throws Exception {
		for (MgVerificationDetails MgVerificationDetails : list) {
			populateAdditionalInfo(MgVerificationDetails);
		}
		return list;
	}

	private void populateAdditionalInfo(MgVerificationDetails MgVerificationDetails) throws Exception {
		Region r = getRegion(MgVerificationDetails.getMandatoryGrantDetail().getWsp());
		// MgVerificationDetails.setRegion(r);
		Users user = getCLO(MgVerificationDetails.getMandatoryGrantDetail().getWsp());
		// MgVerificationDetails.setCloUser(user);
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

	/*
	 * public void submitMgVerificationDetailsToSdf(Users sessionUser,
	 * MgVerificationDetails entity, Tasks tasks) throws Exception { // adds new
	 * entry entity.setStatus(ApprovalEnum.PendingFinalApproval); // batch create
	 * dao.update(entity); // creates the new task
	 * 
	 * List<Users> users = new ArrayList<>(); SDFCompany sdf =
	 * sdfCompanyService.findPrimarySDF(entity.getWsp().getCompany()); if(sdf!=null)
	 * { users.add(sdf.getSdf()); } String desc = "MgVerificationDetails"; if
	 * (users.size() != 0) { TasksService.instance().createTaskUser(users,
	 * MgVerificationDetails.class.getName(), entity.getId(), desc, sessionUser,
	 * true, true, tasks, ConfigDocProcessEnum.MG_VERIFICATION, true);
	 * //TasksService.instance().completeTask(tasks); } }
	 */
	/*
	 * new methods for mg verification tasks
	 */
	/*
	 * public void submitMgVerificationDetails(Users sessionUser,
	 * MgVerificationDetails entity, Tasks tasks) throws Exception { // adds new
	 * entry entity.setStatus(ApprovalEnum.PendingApproval); // batch create
	 * dao.update(entity); // creates the new task
	 * TasksService.instance().findFirstInProcessAndCreateTask("", sessionUser,
	 * entity.getId(), LearnershipDevelopmentRegistration.class.getName(), true,
	 * ConfigDocProcessEnum.MG_VERIFICATION, null, null);
	 * //TasksService.instance().completeTask(tasks); }
	 * 
	 * public void completeMgVerificationDetails(Users sessionUser,
	 * MgVerificationDetails entity, Tasks tasks) throws Exception { // adds new
	 * entry entity.setStatus(ApprovalEnum.PendingApproval); // batch create
	 * dao.update(entity); // creates the new task
	 * TasksService.instance().findNextInProcessAndCreateTask("", sessionUser,
	 * entity.getId(), MgVerificationDetails.class.getName(), true, tasks, null,
	 * null); //TasksService.instance().completeTask(tasks); }
	 */

	/*
	 * public void uploadMgVerificationDetailsEvidence(Users sessionUser,
	 * MgVerificationDetails entity, Tasks tasks) throws Exception {
	 * entity.setStatus(ApprovalEnum.PendingFinalApproval);
	 * 
	 * update(entity); //uploadDocuments(entity, tasks, sessionUser);
	 * TasksService.instance().findFirstInProcessAndCreateTask("", sessionUser,
	 * entity.getId(), MgVerificationDetails.class.getName(), true,
	 * ConfigDocProcessEnum.MG_VERIFICATION, null, null);
	 * TasksService.instance().completeTask(tasks); }
	 */

	/*
	 * public void cloCrmReviewMgVerificationDetailsEvidence(Users sessionUser,
	 * MgVerificationDetails entity, Tasks tasks) throws Exception {
	 * entity.setStatus(ApprovalEnum.PendingFinalApproval); update(entity);
	 * TasksService.instance().findNextInProcessAndCreateTask("", sessionUser,
	 * entity.getId(), MgVerificationDetails.class.getName(), true, tasks, null,
	 * null); }
	 */

	/*
	 * public void finalApproveMgVerificationDetails(Users sessionUser,
	 * MgVerificationDetails MgVerificationDetails, Tasks tasks) throws Exception {
	 * MgVerificationDetails.setStatus(ApprovalEnum.Approved);
	 * MgVerificationDetails.setApprovedDate(getSynchronizedDate());
	 * update(MgVerificationDetails);
	 * TasksService.instance().findNextInProcessAndCreateTask("", sessionUser,
	 * MgVerificationDetails.getId(), MgVerificationDetails.class.getName(), true,
	 * tasks, null, null); TasksService.instance().completeTask(tasks); }
	 */
	/*
	 * public void finalApproveMgVerificationDetails(Users sessionUser,
	 * MgVerificationDetails MgVerificationDetails) throws Exception {
	 * MgVerificationDetails.setStatus(ApprovalEnum.Approved);
	 * MgVerificationDetails.setApprovedDate(getSynchronizedDate());
	 * update(MgVerificationDetails); }
	 */

	/*
	 * public void crmApproveMloaMgVerificationDetailsEvidence(Users sessionUser,
	 * MgVerificationDetails entity, Tasks tasks) throws Exception {
	 * entity.setStatus(ApprovalEnum.PendingFinalApproval); update(entity);
	 * TasksService.instance().findNextInProcessAndCreateTask("", sessionUser,
	 * entity.getId(), MgVerificationDetails.class.getName(), true, tasks, null,
	 * null); }
	 */

	public void seniorManagerApproveMloaMgVerificationDetailsEvidence(Users sessionUser, MgVerificationDetails entity, Tasks tasks) throws Exception {
		update(entity);
		TasksService.instance().findNextInProcessAndCreateTask("", sessionUser, entity.getId(), MgVerificationDetails.class.getName(), true, tasks, null, null);
	}

	/*
	 * public void finalApproveTask(MgVerificationDetails MgVerificationDetails,
	 * Users user, Tasks task) throws Exception {
	 * MgVerificationDetails.setStatus(ApprovalEnum.Approved);
	 * MgVerificationDetails.setApprovedDate(getSynchronizedDate());
	 * update(MgVerificationDetails); TasksService.instance()
	 * .findNextInProcessAndCreateTask("", user, MgVerificationDetails.getId(),
	 * MgVerificationDetails.getClass().getName(), true, task,
	 * MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, user.getFirstName(),
	 * MailTagsEnum.LastName, user.getLastName()), null); }
	 */

	/*
	 * public void rejectTask(MgVerificationDetails MgVerificationDetails, Users
	 * user, Tasks task, ArrayList<RejectReasons> selectedRejectReason) throws
	 * Exception { MgVerificationDetails.setStatus(ApprovalEnum.Rejected);
	 * MgVerificationDetails.setApprovedDate(getSynchronizedDate());
	 * update(MgVerificationDetails); List<RejectReasonMultipleSelection>
	 * rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(
	 * MgVerificationDetails.getId(), MgVerificationDetails.class.getName(),
	 * selectedRejectReason, user, ConfigDocProcessEnum.MG_VERIFICATION);
	 * for(RejectReasonMultipleSelection rrm:rrmList) { if(rrm !=null) {
	 * RejectReasonMultipleSelectionService.instance().create(rrm); } }
	 * 
	 * if(task.getFirstInProcess()) { List<Users> users = new ArrayList<>();
	 * SDFCompany sdf =
	 * sdfCompanyService.findPrimarySDF(MgVerificationDetails.getWsp().getCompany())
	 * ; if(sdf!=null) { users.add(sdf.getSdf()); } String desc =
	 * "MgVerificationDetails"; if (users.size() != 0) {
	 * TasksService.instance().createTaskUser(users,
	 * MgVerificationDetails.class.getName(), MgVerificationDetails.getId(), desc,
	 * user, true, true, task, ConfigDocProcessEnum.MG_VERIFICATION, true);
	 * TasksService.instance().completeTask(task); } } else {
	 * TasksService.instance().findPreviousInProcessAndCreateTask("", user,
	 * MgVerificationDetails.getId(), MgVerificationDetails.getClass().getName(),
	 * true, task, MailDef.instance(MailEnum.Task), null);
	 * TasksService.instance().completeTask(task); } }
	 * 
	 * public void finalRejectTask(MgVerificationDetails MgVerificationDetails,
	 * Users user, Tasks task, ArrayList<RejectReasons> selectedRejectReason) throws
	 * Exception {
	 * MgVerificationDetails.setStatus(ApprovalEnum.PendingCommitteeApproval);
	 * MgVerificationDetails.setApprovedDate(getSynchronizedDate());
	 * update(MgVerificationDetails); List<RejectReasonMultipleSelection>
	 * rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(
	 * MgVerificationDetails.getId(), MgVerificationDetails.class.getName(),
	 * selectedRejectReason, user, ConfigDocProcessEnum.MG_VERIFICATION);
	 * for(RejectReasonMultipleSelection rrm:rrmList) { if(rrm !=null) {
	 * RejectReasonMultipleSelectionService.instance().create(rrm); } }
	 * TasksService.instance().completeTask(task); }
	 * 
	 * public void finalReject(MgVerificationDetails MgVerificationDetails, Users
	 * user, Tasks task, ArrayList<RejectReasons> selectedRejectReason) throws
	 * Exception { MgVerificationDetails.setStatus(ApprovalEnum.RejectedByMANCO);
	 * MgVerificationDetails.setApprovedDate(getSynchronizedDate());
	 * update(MgVerificationDetails); List<RejectReasonMultipleSelection>
	 * rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(
	 * MgVerificationDetails.getId(), MgVerificationDetails.class.getName(),
	 * selectedRejectReason, user, ConfigDocProcessEnum.MG_VERIFICATION);
	 * for(RejectReasonMultipleSelection rrm:rrmList) { if(rrm !=null) {
	 * RejectReasonMultipleSelectionService.instance().create(rrm); } }
	 * //TasksService.instance().completeTask(task); }
	 */
	/*
	 * public void crmApproveTask(MgVerificationDetails MgVerificationDetails, Users
	 * user, Tasks task) throws Exception {
	 * MgVerificationDetails.setStatus(ApprovalEnum.PendingFinalApproval);
	 * //MgVerificationDetails.setCrmDecision(CloRecommendationEnum.Approval);
	 * //MgVerificationDetails.setCrmApprovalRejectionDate(getSynchronizedDate());
	 * //MgVerificationDetails.setWithSdf(true); update(MgVerificationDetails);
	 * TasksService.instance() .findNextInProcessAndCreateTask("", user,
	 * MgVerificationDetails.getId(), MgVerificationDetails.getClass().getName(),
	 * true, task, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName,
	 * user.getFirstName(), MailTagsEnum.LastName, user.getLastName()), null);
	 * notifyMainSdfAndContact(true, MgVerificationDetails, null); }
	 */

	/*
	 * private void notifyMainSdfAndContact(boolean approved, MgVerificationDetails
	 * MgVerificationDetails, List<RejectReasons> rejectReasons) throws Exception {
	 * List<Users> users = locateClientUsersNotification(MgVerificationDetails);
	 * 
	 * RegionTown rt = RegionTownService.instance()
	 * .findByTown(MgVerificationDetails.getWsp().getCompany().getResidentialAddress
	 * ().getTown()); if (regionService == null) { regionService = new
	 * RegionService(); } Region r =
	 * regionService.findByKey(rt.getRegion().getId()); regionService = null; //
	 * locates company's region CRM and CLO Users crmUser = rt.getCrm().getUsers();
	 * Users cloUser = rt.getClo().getUsers(); for (Users notifyUsers : users) { if
	 * (approved) { sendSuccessfulMgVerificationDetailsOutcomeEmail(notifyUsers,
	 * MgVerificationDetails.getWsp().getCompany(),
	 * MgVerificationDetails.getWsp().getFinYear().toString(),
	 * cloUser.getFirstName() + " " + cloUser.getLastName(), crmUser,
	 * r.getDescription()); } else {
	 * sendDiscritionaryGrandRejectionEmail(notifyUsers,
	 * MgVerificationDetails.getWsp().getCompany(),
	 * MgVerificationDetails.getWsp().getFinYear().toString(),
	 * cloUser.getFirstName() + " " + cloUser.getLastName(), rejectReasons, crmUser,
	 * r.getDescription()); } } users = null; crmUser = null; cloUser = null;
	 * //sdfCompanyService = null; r = null; }
	 */

	/**
	 * Locates the additional users to notify for the SDF notification
	 * 
	 * @param MgVerificationDetails
	 * @return List<Users>
	 * @throws Exception
	 */
	private List<Users> locateClientUsersNotification(MgVerificationDetails MgVerificationDetails) throws Exception {
		List<Users> users = new ArrayList<Users>();

		if (sdfCompanyService == null) {
			sdfCompanyService = new SDFCompanyService();
		}
		users.add(sdfCompanyService.findPrimarySDF(MgVerificationDetails.getMandatoryGrantDetail().getWsp().getCompany()).getSdf());

		if (companyUsersService == null) {
			companyUsersService = new CompanyUsersService();
		}

		/*
		 * Send list HR Manager (if provided), Training Manager (if provided),
		 */
		// locate Labour/Union SDF
		List<SDFCompany> sdfList = sdfCompanyService.findByCompanyAndSdfType(MgVerificationDetails.getMandatoryGrantDetail().getWsp().getCompany(), (long) 3);
		for (SDFCompany sdfCompany : sdfList) {
			users.add(sdfCompany.getSdf());
			break;
		}

		// locate Employee SDF
		sdfList = sdfCompanyService.findByCompanyAndSdfType(MgVerificationDetails.getMandatoryGrantDetail().getWsp().getCompany(), (long) 4);
		for (SDFCompany sdfCompany : sdfList) {
			users.add(sdfCompany.getSdf());
			break;
		}
		sdfList = null;

		// HR manager
		List<CompanyUsers> cu = companyUsersService.findByCompanyResponsibilityAndUserPosition(MgVerificationDetails.getMandatoryGrantDetail().getWsp().getCompany(), ConfigDocProcessEnum.DG_VERIFICATION, (long) 3);
		for (CompanyUsers companyUsers : cu) {
			users.add(companyUsers.getUser());
			break;
		}

		// Training Manager
		cu = companyUsersService.findByCompanyResponsibilityAndUserPosition(MgVerificationDetails.getMandatoryGrantDetail().getWsp().getCompany(), ConfigDocProcessEnum.DG_VERIFICATION, (long) 4);
		for (CompanyUsers companyUsers : cu) {
			users.add(companyUsers.getUser());
			break;
		}
		return users;
	}

	public int findCountByWspId(Wsp wsp) {
		return dao.findCountByWspId(wsp.getId());
	}
	
	public int countEntriesNotPopulatedByWspId(Wsp wsp) throws Exception{
		return dao.countEntriesNotPopulatedByWspId(wsp.getId());
	}
	
	public int findCountByWspIdWhereEvidanceRequired(Wsp wsp) throws Exception{
		return dao.findCountByWspIdWhereEvidanceRequired(wsp.getId());
	}
	
	public int countByWspIdWhereEvidanceRequired(Wsp wsp) throws Exception{
		return dao.countByWspIdWhereEvidanceRequired(wsp.getId());
	}
	
	public void checkValues(MgVerificationDetails mg) throws Exception {
		//String error = "";
		if(mg.getNoLearnersStarted() == null || mg.getNoLearnersWithdrawn()== null || mg.getNoLearnersCompleted()== null || mg.getActionPlan()== null) {
			throw new Exception("Please provide required information");
		}else {	
			if(mg.getNoLearnersStarted() < 0) {
				throw new Exception("Number of Learners Started cannot be less than zero");
			}
			if(mg.getNoLearnersWithdrawn()< 0) {
				throw new Exception("Number of Learners Withdrawn cannot be less than zero");
			}
			if(mg.getNoPlannedLearners()< 0) {
				throw new Exception("Number of Learners Planned cannot be less than zero");
			}
			if(mg.getNoLearnersCompleted()< 0) {
				throw new Exception("Number of Learners Completed cannot be less than zero");
			}
			if(mg.getNoLearnersCompleted() > mg.getNoLearnersStarted()) {
				throw new Exception( "Number of Learners Completed cannot be greater than Number of Learners Started");
			}
			if(mg.getNoLearnersWithdrawn() > mg.getNoLearnersStarted()) {
				throw new Exception("Number of Learners Withdrawn cannot be greater than Number of Learners Started");
			}
			if(mg.getNoLearnersCompleted() + mg.getNoLearnersWithdrawn() >mg.getNoLearnersStarted()) {
				throw new Exception("Number of Learners Completed with Number of Learners Withdrawn  cannot be greater than Number of Learners Started");
			}
		}
	}
	
	public boolean determanEvidenceRequired(MgVerificationDetails mg) throws Exception {
		boolean evidenceRequired = false;
		if (mg.getNoLearnersWithdrawn() != 0) {
			evidenceRequired = true;
		}
		return evidenceRequired;
	}
	
	public List<MgVerificationDetails> mgVerificationDetailsByWspIdAndEvidanceRequired(Long wspId) throws Exception{
		return dao.mgVerificationDetailsByWspIdAndEvidanceRequired(wspId);
	}
	
	public List<MgVerificationDetails> findMgVerificationDetailsByWspIdAndEvidanceRequired(Long wspId) throws Exception{
		return dao.findMgVerificationDetailsByWspIdAndEvidanceRequired(wspId);
	}
	
	public void validateUploadEvidance(MgVerification mgVerification) throws Exception{
		boolean docsOutstanding = false;
//		List<MgVerificationDetails> allEntries = mgVerificationDetailsByWspIdAndEvidanceRequired(mgVerification.getWsp().getId());
		List<MgVerificationDetails> allEntries = findMgVerificationDetailsByWspIdAndEvidanceRequired(mgVerification.getWsp().getId());
		for (MgVerificationDetails mgVerificationDetails : allEntries) {
			populateDoc(mgVerificationDetails);
			if (mgVerificationDetails.getDocs().size() == 0) {
				docsOutstanding = true;
			}
		}
		allEntries = null;
		if (docsOutstanding) {
			throw new Exception("Provide all documents stipulated by evidance required before proceeding.");
		}
	}

	/**
	 * Uploads docs uploaded against LearnershipDevelopmentRegistration throws
	 * Exception if doc data is empty for permissions: Upload or EditUpload Only
	 * time documents required if permissions is: FinalUploadApproval or FinalUpload
	 * 
	 * @param entity
	 * @throws Exception
	 */
	/*
	 * public void uploadDocuments(MgVerificationDetails entity, Tasks task, Users
	 * sessionUser) throws Exception { // check if all docs provided for correct
	 * permissions if (task != null && (task.getProcessRole().getRolePermission() ==
	 * UserPermissionEnum.Upload || task.getProcessRole().getRolePermission() ==
	 * UserPermissionEnum.EditUpload || task.getProcessRole().getRolePermission() ==
	 * UserPermissionEnum.FinalUploadApproval ||
	 * task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalUpload
	 * || task.getProcessRole().getRolePermission() ==
	 * UserPermissionEnum.UploadApprove)) {
	 * //prepareNewRegistration(task.getWorkflowProcess(), entity,
	 * task.getProcessRole()); for (Doc doc : entity.getDocs()) { if (doc.getId() !=
	 * null) { DocByte docByte =
	 * DocService.instance().findDocByteByKey(doc.getId()); if (docByte != null) {
	 * doc.setData(docByte.getData()); } } }
	 * 
	 * // check if data not empty and creates document for (Doc doc :
	 * entity.getDocs()) { if (doc.getId() == null && doc.getData() != null) {
	 * doc.setTargetKey(entity.getId());
	 * doc.setTargetClass(LearnershipDevelopmentRegistration.class.getName());
	 * DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(),
	 * sessionUser); }else{
	 * //if(doc.getConfigDoc().getConfigDocProcess().getConfigDocProcessEnumByValue(
	 * 1).compareTo(doc.getConfigDoc().getProcessRoles().getRoleOrder()) ) if
	 * (doc.getData() != null) { if(doc.getId() == null) {
	 * doc.setTargetKey(entity.getId());
	 * doc.setTargetClass(LearnershipDevelopmentRegistration.class.getName());
	 * DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(),
	 * sessionUser); } }else { throw new
	 * Exception("Please ensure all documents are uploaded"); } } } } }
	 */
}
