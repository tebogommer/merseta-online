package haj.com.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.ActiveContractDetailDAO;
import haj.com.entity.ActiveContractDetail;
import haj.com.entity.ActiveContracts;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.ProjectImplementationPlan;
import haj.com.entity.ProjectImplementationPlanLearners;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.MoaTypeEnum;
import haj.com.entity.enums.TrancheEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.Roles;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RolesService;
import haj.com.utils.GenericUtility;

public class ActiveContractDetailService extends AbstractService {
	
	/** The dao. */
	private ActiveContractDetailDAO dao = new ActiveContractDetailDAO();
	
	/* Servce Levels */
	private ProjectImplementationPlanService projectImplementationPlanService;

	/**
	 * Get all ActiveContractDetail
	 * 
	 * @author TechFinium
	 * @see ActiveContractDetail
	 * @return a list of {@link haj.com.entity.ActiveContractDetail}
	 * @throws Exception
	 *             the exception
	 */
	public List<ActiveContractDetail> allActiveContractDetail() throws Exception {
		return dao.allActiveContractDetail();
	}

	/**
	 * Create or update ActiveContractDetail.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ActiveContractDetail
	 */
	public void create(ActiveContractDetail entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update ActiveContractDetail.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ActiveContractDetail
	 */
	public void update(ActiveContractDetail entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete ActiveContractDetail.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ActiveContractDetail
	 */
	public void delete(ActiveContractDetail entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.ActiveContractDetail}
	 * @throws Exception
	 *             the exception
	 * @see ActiveContractDetail
	 */
	public ActiveContractDetail findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find ActiveContractDetail by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.ActiveContractDetail}
	 * @throws Exception
	 *             the exception
	 * @see ActiveContractDetail
	 */
	public List<ActiveContractDetail> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load ActiveContractDetail
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.ActiveContractDetail}
	 * @throws Exception
	 *             the exception
	 */
	public List<ActiveContractDetail> allActiveContractDetail(int first, int pageSize) throws Exception {
		return dao.allActiveContractDetail(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of ActiveContractDetail for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the ActiveContractDetail
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(ActiveContractDetail.class);
	}

	/**
	 * Lazy load ActiveContractDetail with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            ActiveContractDetail class
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
	 * @return a list of {@link haj.com.entity.ActiveContractDetail} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<ActiveContractDetail> allActiveContractDetail(Class<ActiveContractDetail> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<ActiveContractDetail>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
	}

	/**
	 * Get count of ActiveContractDetail for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            ActiveContractDetail class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the ActiveContractDetail entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<ActiveContractDetail> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<ActiveContractDetail> allActiveContractDetailByProjectImplementationPlanId(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long projectImplementationPlanId) throws Exception {
		String hql = "select o from ActiveContractDetail o where o.projectImplementationPlan.id = :projectImplementationPlanId";
		filters.put("projectImplementationPlanId", projectImplementationPlanId);
		return (List<ActiveContractDetail>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllActiveContractDetailByProjectImplementationPlanId(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from ActiveContractDetail o where o.projectImplementationPlan.id = :projectImplementationPlanId";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<ActiveContractDetail> allActiveContractDetailByActiveContractId(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long activeContractId) throws Exception {
		String hql = "select o from ActiveContractDetail o where o.activeContracts.id = :activeContractId";
		filters.put("activeContractId", activeContractId);
		return (List<ActiveContractDetail>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllActiveContractDetailByActiveContractId(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from ActiveContractDetail o where o.activeContracts.id = :activeContractId";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<ActiveContractDetail> allActiveContractDetailByCompanyLearnerId(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long companyLearnersId) throws Exception {
		String hql = "select o from ActiveContractDetail o left join fetch o.companyLearners where o.companyLearners.id = :companyLearnersId";
		filters.put("companyLearnersId", companyLearnersId);
		return (List<ActiveContractDetail>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllActiveContractDetailByCompanyLearnerId(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from ActiveContractDetail o where o.companyLearners.id = :companyLearnersId";
		return dao.countWhere(filters, hql);
	}
	
	public Integer countByCompanyLearnerTranchAndNotEqualStatus(Long companyLearnerId, TrancheEnum tranchEnum, ApprovalEnum approvalEnum) throws Exception {
		return dao.countByCompanyLearnerTranchAndNotEqualStatus(companyLearnerId, tranchEnum, approvalEnum);
	}

	public void calculateTotals(ActiveContractDetail acd) throws Exception {
		// =V159-W159+X159-Y159-AA159-AB159
		BigDecimal totalPayments = BigDecimal.valueOf((acd.getCorrectiontobalances() == null ? 0.0 : acd.getCorrectiontobalances()));
		totalPayments = totalPayments.subtract(BigDecimal.valueOf((acd.getAccruals() == null ? 0.0 : acd.getAccruals())));
		totalPayments = totalPayments.add(BigDecimal.valueOf((acd.getAccrualsreversal() == null ? 0.0 : acd.getAccrualsreversal())));
		totalPayments = totalPayments.subtract(BigDecimal.valueOf((acd.getPayments() == null ? 0.0 : acd.getPayments())));
		totalPayments = totalPayments.subtract(BigDecimal.valueOf((acd.getTerminationvalue() == null ? 0.0 : acd.getTerminationvalue())));
		totalPayments = totalPayments.subtract(BigDecimal.valueOf((acd.getWriteback() == null ? 0.0 : acd.getWriteback())));
		acd.setTotal(totalPayments);
		// =S17+T17+U17+AC17
		if (acd.getOpeningbalance() == null) acd.setOpeningbalance(findLastClosingBalance(acd.getActiveContracts()));
		if (acd.getOpeningbalance() == null) acd.setOpeningbalance(BigDecimal.valueOf(acd.getActiveContracts().getContractvalue()));
		BigDecimal total = acd.getOpeningbalance();
		total = total.add(BigDecimal.valueOf(acd.getAdditions() == null ? 0.0 : acd.getAdditions()));
		total = total.add(BigDecimal.valueOf(acd.getAddendumsammendments() == null ? 0.0 : acd.getAddendumsammendments()));
		total = total.add(acd.getTotal() == null ? BigDecimal.ZERO : acd.getTotal());
		// if (total.compareTo(acd.getOpeningbalance()) == 1) throw new
		// ValidationException("Total cannot be higher than the opening balance");
		acd.setClosingbalance(total);

	}

	public void prepareNewRegistration(ActiveContractDetail entity) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessNotUploaded(entity.getClass().getName(), entity.getId(), ConfigDocProcessEnum.CONTRACT_DETAIL, CompanyUserTypeEnum.Company);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = ConfigDocService.instance().findByProcess(ConfigDocProcessEnum.CONTRACT_DETAIL, CompanyUserTypeEnum.Company);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}

	// Request Workflow for TRANCH Payments
	public void requesteWorkflow(ActiveContractDetail entity, Users u) throws Exception {
		entity.setStatus(ApprovalEnum.PendingApproval);
		dao.create(entity);
		List<Users> users = new ArrayList<>();
		RegionTown rt = null;
		if (entity.getActiveContracts().getDgAllocationParent() != null) {
			rt = RegionTownService.instance().findByTown(entity.getActiveContracts().getDgAllocationParent().getWsp().getCompany().getResidentialAddress().getTown());
		} else {
			rt = RegionTownService.instance().findByTown(entity.getActiveContracts().getCompany().getResidentialAddress().getTown());
		}
		if (rt != null && rt.getCrm() != null && rt.getCrm().getUsers() != null && rt.getCrm().getUsers().getId() != null) {
			users.add(rt.getCrm().getUsers());
		}
		
		if (users.isEmpty()) {
			if (rt != null && rt.getClo() != null && rt.getClo().getUsers() != null && rt.getClo().getUsers().getId() != null) {
				users.add(rt.getClo().getUsers());
			}
		}
		TasksService.instance().findFirstInProcessAndCreateTask("", u, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.CONTRACT_DETAIL, null, users);
	}
	
	public void requesteWorkflowTranchOne(ActiveContractDetail entity, Users u) throws Exception {
		if (entity.getStatus() == null) {
			entity.setStatus(ApprovalEnum.PendingApproval);
		}
		dao.create(entity);
		List<Users> users = new ArrayList<>();
		RegionTown rt = null;
		if (entity.getActiveContracts().getDgAllocationParent() != null) {
			rt = RegionTownService.instance().findByTown(entity.getActiveContracts().getDgAllocationParent().getWsp().getCompany().getResidentialAddress().getTown());
		} else {
			rt = RegionTownService.instance().findByTown(entity.getActiveContracts().getCompany().getResidentialAddress().getTown());
		}
		users.add(rt.getCrm().getUsers());
		TasksService.instance().findFirstInProcessAndCreateTask("", u, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.CONTRACT_DETAIL, null, users);
	}

	public void completeWorkflow(ActiveContractDetail entity, Users user, Tasks tasks) throws Exception {
		String error = "";
		if (entity.getDocs() != null && tasks.getProcessRole() != null && UserPermissionEnum.getUploadPermissions().contains(tasks.getProcessRole().getRolePermission())) {
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) doc.setData(docByte.getData());
				}
				if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
					error += "Upload " + doc.getConfigDoc().getName();
				}
			}
		}
		if (error.length() > 0) throw new ValidationException(error);
		update(entity);
		if(tasks.getProcessRole().getRoleOrder() == 7) {
			List<Users> userList = findRegionQualityAssuror(entity.getCompanyLearners().getCompany());
			if (userList.size() == 0) {
				throw new Exception("No Coodinator assigned to region");
			}
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
		}else {
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		}
	}
	
	public void completeWorkflowVerionTwo(ActiveContractDetail entity, Users user, Tasks tasks) throws Exception {
		String error = "";
		if (entity.getDocs() != null && tasks.getProcessRole() != null && UserPermissionEnum.getUploadPermissions().contains(tasks.getProcessRole().getRolePermission())) {
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) doc.setData(docByte.getData());
				}
				if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
					error += "Upload " + doc.getConfigDoc().getName();
				}
			}
		}
		
		if (error.length() > 0) throw new ValidationException(error);
		update(entity);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}

	public void approveWorkflow(ActiveContractDetail entity, Users user, Tasks tasks) throws Exception {
		entity.setStatus(ApprovalEnum.WaitingForManager);
		dao.update(entity);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}

	public void rejectWorkflow(ActiveContractDetail entity, Users user, Tasks tasks) throws Exception {
		TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, null);
	}

	public void finalApproveWorkflow(ActiveContractDetail entity, Users user, Tasks tasks) throws Exception {
		entity.setStatus(ApprovalEnum.Approved);
		entity.setApprovalDate(getSynchronizedDate());
		calculateTotals(entity);
		dao.update(entity);
		TasksService.instance().completeTask(tasks);
//		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}

	public void finalRejectWorkflow(ActiveContractDetail entity, Users user, Tasks tasks) throws Exception {
		entity.setStatus(ApprovalEnum.Rejected);
		entity.setApprovalDate(getSynchronizedDate());
		dao.update(entity);
		TasksService.instance().completeTask(tasks);
	}

	
	public ActiveContractDetail addTranchePaymentDetail(ActiveContracts entity, Users user, double percentageToPay, TrancheEnum trancheEnum, BigDecimal openVal, ProjectImplementationPlan projectImplementationPlan) throws Exception {
		ActiveContractDetail activeContractDetail = new ActiveContractDetail(entity);
		if (openVal == null || openVal.compareTo(BigDecimal.ZERO) == 0) {
			entity.setAccrualraised(0.0);
			activeContractDetail.setOpeningbalance(BigDecimal.valueOf(entity.getContractvalue()).subtract(BigDecimal.valueOf(entity.getAccrualraised())));
		} else {
			activeContractDetail.setOpeningbalance(openVal);
		}
		activeContractDetail.setProjectImplementationPlan(projectImplementationPlan);
		activeContractDetail.setPaymentMonth(getSynchronizedDate());
		activeContractDetail.setPayments(projectImplementationPlan.getTotalAwardAmount().doubleValue() * percentageToPay);
		activeContractDetail.setTrancheEnum(trancheEnum);
		activeContractDetail.setUsers(user);
		calculateTotals(activeContractDetail);
		activeContractDetail.setStatus(ApprovalEnum.Approved);
		return activeContractDetail;
	}

	public ActiveContracts findByCompanyYear(Company company, Integer year) throws Exception {
		List<ActiveContracts> acs = dao.findByCompanyYear(company, year);
		if (acs.isEmpty()) {
			return null;
		}
		return acs.get(0);
	}

	public BigDecimal findLastClosingBalance(ActiveContracts activeContracts) throws Exception {
		if (activeContracts == null) return null;
		List<BigDecimal> bigDecimals = dao.findLastClosingBalance(activeContracts);
		if (bigDecimals.isEmpty()) {
			return BigDecimal.ZERO;
		}
		return bigDecimals.get(0);
	}

	// Other TRANCH Payments 
	public void addTranchePaymentDetail(CompanyLearners companyLearners, Users user, double percentageToPay, TrancheEnum trancheEnum, Boolean createTask) throws Exception {
		ActiveContracts entity = companyLearners.getProjectImplementationPlan() != null ? companyLearners.getProjectImplementationPlan().getActiveContracts() : companyLearners.getDgYearTag() != null ? findByCompanyYear(companyLearners.getCompany(), companyLearners.getDgYearTag().getFinYear()) : null;
		if (entity != null && HAJConstants.DEV_TEST_PROD != "P") {
			ActiveContractDetail activeContractDetail = new ActiveContractDetail(entity, companyLearners);
			activeContractDetail.setOpeningbalance(findLastClosingBalance(entity));
			activeContractDetail.setProjectImplementationPlan(companyLearners.getProjectImplementationPlan());
			activeContractDetail.setPaymentMonth(getSynchronizedDate());
			if (trancheEnum == TrancheEnum.TRANCHE_ONE) {
				activeContractDetail.setPayments(companyLearners.getProjectImplementationPlan().getTotalAwardAmount().doubleValue() * percentageToPay);
			} else {
				Double avaibleAmountOnPipForTranch = GenericUtility.roundToPrecision(companyLearners.getProjectImplementationPlan().getTotalAwardAmount().doubleValue(), 2) * percentageToPay;
				Integer totalLearners = 0;
				if (companyLearners.getProjectImplementationPlan().getFullyFundedLearnerAwarded() != 0) {
					totalLearners = totalLearners + companyLearners.getProjectImplementationPlan().getFullyFundedLearnerAwarded();
				}
				if (companyLearners.getProjectImplementationPlan().getCoFundingLearnersAwarded() != 0) {
					totalLearners = totalLearners + companyLearners.getProjectImplementationPlan().getCoFundingLearnersAwarded();
				}
				if (totalLearners == 0) {
					activeContractDetail.setPayments(0.0);
				} else {
					activeContractDetail.setPayments(avaibleAmountOnPipForTranch / totalLearners);
				}
			}
			activeContractDetail.setTrancheEnum(trancheEnum);
			activeContractDetail.setUsers(user);
			calculateTotals(activeContractDetail);
			if (createTask) {
				activeContractDetail.setStatus(ApprovalEnum.PendingApproval);
				requesteWorkflow(activeContractDetail, user);
			} else {
				activeContractDetail.setStatus(ApprovalEnum.Approved);
				dao.create(activeContractDetail);
			}
		}
	}
	
	public void addTranchePaymentDetail(ProjectImplementationPlanLearners pipLearners, CompanyLearners companyLearners, Users user, double percentageToPay, TrancheEnum trancheEnum, Boolean createTask) throws Exception {
		ActiveContracts entity = companyLearners.getProjectImplementationPlan() != null ? companyLearners.getProjectImplementationPlan().getActiveContracts() : companyLearners.getDgYearTag() != null ? findByCompanyYear(companyLearners.getCompany(), companyLearners.getDgYearTag().getFinYear()) : null;
		if (entity != null && HAJConstants.DEV_TEST_PROD != "P") {
			ActiveContractDetail activeContractDetail = new ActiveContractDetail(entity, companyLearners);
			activeContractDetail.setOpeningbalance(findLastClosingBalance(entity));
			activeContractDetail.setProjectImplementationPlan(companyLearners.getProjectImplementationPlan());
			activeContractDetail.setPaymentMonth(getSynchronizedDate());
			if (pipLearners != null) {
				activeContractDetail.setProjectImplementationPlanLearners(pipLearners);
			}
			if (trancheEnum == TrancheEnum.TRANCHE_ONE) {
				activeContractDetail.setPayments(companyLearners.getProjectImplementationPlan().getTotalAwardAmount().doubleValue() * percentageToPay);
			} else {
				Double avaibleAmountOnPipForTranch = GenericUtility.roundToPrecision(companyLearners.getProjectImplementationPlan().getTotalAwardAmount().doubleValue(), 2) * percentageToPay;
				Integer totalLearners = 0;
				if (companyLearners.getProjectImplementationPlan().getFullyFundedLearnerAwarded() != 0) {
					totalLearners = totalLearners + companyLearners.getProjectImplementationPlan().getFullyFundedLearnerAwarded();
				}
				if (companyLearners.getProjectImplementationPlan().getCoFundingLearnersAwarded() != 0) {
					totalLearners = totalLearners + companyLearners.getProjectImplementationPlan().getCoFundingLearnersAwarded();
				}
				if (totalLearners == 0) {
					activeContractDetail.setPayments(0.0);
				} else {
					activeContractDetail.setPayments(avaibleAmountOnPipForTranch / totalLearners);
				}
			}
			activeContractDetail.setTrancheEnum(trancheEnum);
			activeContractDetail.setUsers(user);
			calculateTotals(activeContractDetail);
			if (createTask) {
				activeContractDetail.setStatus(ApprovalEnum.PendingApproval);
				requesteWorkflow(activeContractDetail, user);
			} else {
				activeContractDetail.setStatus(ApprovalEnum.Approved);
				dao.create(activeContractDetail);
			}
		}
	}

	public ActiveContractDetail addTranchePayment(ActiveContracts entity, Users user, double amountToPay, TrancheEnum trancheEnum, ProjectImplementationPlan projectImplementationPlan) throws Exception {
		ActiveContractDetail activeContractDetail = new ActiveContractDetail(entity);
		activeContractDetail.setOpeningbalance(findLastClosingBalance(entity));
		activeContractDetail.setProjectImplementationPlan(projectImplementationPlan);
		activeContractDetail.setPaymentMonth(getSynchronizedDate());
		activeContractDetail.setPayments(amountToPay);
		activeContractDetail.setTrancheEnum(trancheEnum);
		activeContractDetail.setUsers(user);
		activeContractDetail.setStatus(ApprovalEnum.Approved);
		calculateTotals(activeContractDetail);
		return activeContractDetail;
	}
	
	public List<ActiveContractDetail> findNotProcessedForGp() throws Exception {
		return dao.findNotProcessedForGp();
	}
	
	public List<Users> findRegionQualityAssuror(Company company) throws Exception {
		HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
		RolesService rolesService = new RolesService();
		List<Users> list = new ArrayList<>();
		Roles roles = rolesService.findByKey(HAJConstants.COORDINATOR_ROLE_ID);
		list = hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegionAndRole(company.getResidentialAddress().getTown(), roles);
		return list;
	}
	
	public Integer countActiveContractDetailByActiveContract(Long activeContractId) throws Exception {
		return dao.countActiveContractDetailByActiveContract(activeContractId);
	}
	
	public Integer countActiveContractDetailByPip(Long pipId) throws Exception {
		return dao.countActiveContractDetailByPip(pipId);
	}
	
	public List<ActiveContracts> findAllActiveContracts() throws Exception {
		return dao.findAllActiveContracts();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ActiveContractDetail> cloCrmActiveContractDetailRegionReportByMoaTypeAndGrantYear(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long userID, MoaTypeEnum moaType, Integer finYear) throws Exception {
		String hql = "select o from ActiveContractDetail o where "
				+ " o.activeContracts.moaType = :moaType and "
				+ " o.activeContracts.dgAllocationParent.wsp.finYear = :finYear and "
				+ " o.activeContracts.dgAllocationParent.wsp.company.nonSetaCompany = false and "
				+ " o.activeContracts.dgAllocationParent.wsp.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :userID or x.crm.users.id = :userID)";
		filters.put("userID", userID);
		filters.put("moaType", moaType);
		filters.put("finYear", finYear);
		return populateReportInformation((List<ActiveContractDetail>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countCloCrmActiveContractDetailRegionReportByMoaTypeAndGrantYear(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from ActiveContractDetail o where "
				+ " o.activeContracts.moaType = :moaType and "
				+ " o.activeContracts.dgAllocationParent.wsp.finYear = :finYear and "
				+ " o.activeContracts.dgAllocationParent.wsp.company.nonSetaCompany = false and "
				+ " o.activeContracts.dgAllocationParent.wsp.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :userID or x.crm.users.id = :userID)";
		return dao.countWhere(filters, hql);
	}
	
	public int countCloCrmActiveContractDetailRegionReportByMoaTypeAndGrantYear(Long userID, MoaTypeEnum moaType, Integer finYear) throws Exception {
		return dao.countCloCrmActiveContractDetailRegionReportByMoaTypeAndGrantYear(userID, moaType, finYear);
	}
	
	@SuppressWarnings("unchecked")
	public List<ActiveContractDetail> activeContractDetailReportByMoaTypeAndGrantYear(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, MoaTypeEnum moaType, Integer finYear) throws Exception {
		String hql = "select o from ActiveContractDetail o where "
				+ " o.activeContracts.moaType = :moaType and "
				+ " o.activeContracts.dgAllocationParent.wsp.finYear = :finYear and "
				+ " o.activeContracts.dgAllocationParent.wsp.company.nonSetaCompany = false";
		filters.put("moaType", moaType);
		filters.put("finYear", finYear);
		return populateReportInformation((List<ActiveContractDetail>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countActiveContractDetailReportByMoaTypeAndGrantYear(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from ActiveContractDetail o where "
				+ " o.activeContracts.moaType = :moaType and "
				+ " o.activeContracts.dgAllocationParent.wsp.finYear = :finYear and "
				+ " o.activeContracts.dgAllocationParent.wsp.company.nonSetaCompany = false";
		return dao.countWhere(filters, hql);
	}
	
	public int countActiveContractDetailReportByMoaTypeAndGrantYear(MoaTypeEnum moaType, Integer finYear) throws Exception {
		return dao.countActiveContractDetailReportByMoaTypeAndGrantYear(moaType, finYear);
	}
	
	public List<ActiveContractDetail> populateReportInformation(List<ActiveContractDetail> activeContractDetailList) throws Exception {
		for (ActiveContractDetail activeContractDetail : activeContractDetailList) {
			populateReportInformation(activeContractDetail);
		}
		return activeContractDetailList;
	}
	
	public ActiveContractDetail populateReportInformation(ActiveContractDetail activeContractDetail) throws Exception{
		if (activeContractDetail.getProjectImplementationPlan() != null && activeContractDetail.getProjectImplementationPlan().getId() != null) {
			if (projectImplementationPlanService == null) {
				projectImplementationPlanService = new ProjectImplementationPlanService();
			}
			projectImplementationPlanService.populatePipInterventionQualificationDesc(activeContractDetail.getProjectImplementationPlan());
		}
		return activeContractDetail;
	}
	
	public ActiveContractDetail addProjectPayment(ActiveContracts entity, Users user, ProjectImplementationPlan projectImplementationPlan) throws Exception {
		ActiveContractDetail activeContractDetail = new ActiveContractDetail(entity);
		activeContractDetail.setOpeningbalance(findLastClosingBalance(entity));
		activeContractDetail.setProjectImplementationPlan(projectImplementationPlan);
		activeContractDetail.setPaymentMonth(getSynchronizedDate());
		activeContractDetail.setPayments(projectImplementationPlan.getTotalAwardAmount().doubleValue());
		activeContractDetail.setUsers(user);
		activeContractDetail.setApprovalDate(getSynchronizedDate());
		activeContractDetail.setStatus(ApprovalEnum.Approved);
		activeContractDetail.setActiveContracts(entity);
		activeContractDetail.setOpeningbalance(BigDecimal.valueOf(entity.getContractvalue()));
		calculateTotals(activeContractDetail);
		create(activeContractDetail);
		return activeContractDetail;
	}
	
}