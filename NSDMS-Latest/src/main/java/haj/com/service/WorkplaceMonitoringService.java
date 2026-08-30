package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.WorkplaceMonitoringDAO;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.WorkplaceMonitoring;
import haj.com.entity.WorkplaceMonitoringPurposeOfSiteVisit;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.TrancheEnum;
import haj.com.entity.lookup.DesignatedTrade;
import haj.com.entity.lookup.DesignatedTradeLevel;
import haj.com.entity.lookup.LearnerMonitoringSurvey;
import haj.com.entity.lookup.PurposeOfSiteVisit;
import haj.com.entity.lookup.RegionTown;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.DesignatedTradeLevelService;
import haj.com.service.lookup.LearnerMonitoringSurveyService;
import haj.com.service.lookup.PurposeOfSiteVisitService;
import haj.com.service.lookup.RegionTownService;

public class WorkplaceMonitoringService extends AbstractService {
	/** The dao. */
	private WorkplaceMonitoringDAO dao = new WorkplaceMonitoringDAO();
	private LearnerMonitoringSurveyService learnerMonitoringSurveyService = new LearnerMonitoringSurveyService();
	private ActiveContractDetailService activeContractDetailService = new ActiveContractDetailService();
	private PurposeOfSiteVisitService purposeOfSiteVisitsService = new PurposeOfSiteVisitService();

	/**
	 * Get all WorkplaceMonitoring
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoring
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoring}
	 * @throws Exception
	 *             the exception
	 */
	public List<WorkplaceMonitoring> allWorkplaceMonitoring() throws Exception {
		return dao.allWorkplaceMonitoring();
	}

	/**
	 * Create or update WorkplaceMonitoring.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WorkplaceMonitoring
	 */
	public void create(WorkplaceMonitoring entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update WorkplaceMonitoring.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WorkplaceMonitoring
	 */
	public void update(WorkplaceMonitoring entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete WorkplaceMonitoring.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WorkplaceMonitoring
	 */
	public void delete(WorkplaceMonitoring entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.WorkplaceMonitoring}
	 * @throws Exception
	 *             the exception
	 * @see WorkplaceMonitoring
	 */
	public WorkplaceMonitoring findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find WorkplaceMonitoring by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoring}
	 * @throws Exception
	 *             the exception
	 * @see WorkplaceMonitoring
	 */
	public List<WorkplaceMonitoring> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load WorkplaceMonitoring
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoring}
	 * @throws Exception
	 *             the exception
	 */
	public List<WorkplaceMonitoring> allWorkplaceMonitoring(int first, int pageSize) throws Exception {
		return dao.allWorkplaceMonitoring(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of WorkplaceMonitoring for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the WorkplaceMonitoring
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(WorkplaceMonitoring.class);
	}

	/**
	 * Lazy load WorkplaceMonitoring with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            WorkplaceMonitoring class
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
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoring} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoring> allWorkplaceMonitoring(Class<WorkplaceMonitoring> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<WorkplaceMonitoring>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of WorkplaceMonitoring for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            WorkplaceMonitoring class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the WorkplaceMonitoring entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<WorkplaceMonitoring> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public void prepareNewRegistration(WorkplaceMonitoring entity) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), ConfigDocProcessEnum.WorkplaceMonitoring));
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessNotUploaded(entity.getClass().getName(), entity.getId(), ConfigDocProcessEnum.WorkplaceMonitoring, CompanyUserTypeEnum.User);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = ConfigDocService.instance().findByProcess(ConfigDocProcessEnum.WorkplaceMonitoring, CompanyUserTypeEnum.User);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}

	public Long findByCompany(Long companyID) throws Exception {
		return dao.findByCompany(companyID);
	}

	public void requestWorkplaceMonitoring(Company company, Users user) throws Exception {
		WorkplaceMonitoring workplaceMonitoring = new WorkplaceMonitoring();
		workplaceMonitoring.setCompany(company);
		workplaceMonitoring.setUsers(user);
		workplaceMonitoring.setIgnoreTranchePayments(true);
		requesteWorkflow(workplaceMonitoring, user);
	}

	public void requesteWorkflow(WorkplaceMonitoring entity, Users u) throws Exception {

		Long count = findByCompany(entity.getCompany().getId());
		if (count == 0) {
			List<IDataEntity> dataentities = new ArrayList<>();

			entity.setStatus(ApprovalEnum.PendingApproval);
			List<LearnerMonitoringSurvey> learnerMonitoringSurveys = learnerMonitoringSurveyService.findLookups();
			for (LearnerMonitoringSurvey learnerMonitoringSurvey : learnerMonitoringSurveys) {
				learnerMonitoringSurvey.setWorkplaceMonitoring(entity);
			}
			dataentities.add(entity);
			dataentities.addAll(learnerMonitoringSurveys);
			dao.createBatch(dataentities);

			RegionTown rt = new RegionTown();
			if (entity.getCompany() != null && entity.getCompany().getResidentialAddress() != null && entity.getCompany().getResidentialAddress().getTown() != null) {
				rt = RegionTownService.instance().findByTown(entity.getCompany().getResidentialAddress().getTown());
			}

			List<Users> users = new ArrayList<>();

			if (rt != null && rt.getClo() != null) {
				users.add(rt.getClo().getUsers());
				TasksService.instance().findFirstInProcessAndCreateTask("", u, entity.getId(), WorkplaceMonitoring.class.getName(), true, ConfigDocProcessEnum.WorkplaceMonitoring, null, users);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void completeWorkflow(WorkplaceMonitoring entity, Users user, Tasks tasks, List<LearnerMonitoringSurvey> learnerMonitoringSurveys, List<LearnerMonitoringSurvey> discretionaryGrantMonitoringSurveys, List<PurposeOfSiteVisit> selectedPurposeOfSiteVisits) throws Exception {
		List<IDataEntity> dataentities = new ArrayList<>();
		List<IDataEntity> dataentitiesCreate = new ArrayList<>();

		dataentities.addAll(discretionaryGrantMonitoringSurveys);
		dataentities.addAll(learnerMonitoringSurveys);

		List<WorkplaceMonitoringPurposeOfSiteVisit> currentPurposeOfSiteVisits = purposeOfSiteVisitsService.findWorkplaceMonitoringPurposeOfSiteVisit(entity);
		dao.deleteBatch((List<IDataEntity>) (List<?>) currentPurposeOfSiteVisits);
		for (PurposeOfSiteVisit iDataEntity : selectedPurposeOfSiteVisits)
			dataentitiesCreate.add(new WorkplaceMonitoringPurposeOfSiteVisit(entity, iDataEntity));

		String error = "";
		if (entity.getDocs() != null) {
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
		dao.createAndUpdateBatch(dataentitiesCreate, dataentities);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, true);
	}

	public void approveWorkflow(WorkplaceMonitoring entity, Users user, Tasks tasks) throws Exception {
		Long taskCount = TasksService.instance().findTasksByTypeAndKeyOpen(tasks.getWorkflowProcess(), entity.getClass().getName(), entity.getId());
		if (taskCount == 1) {
			entity.setStatus(ApprovalEnum.WaitingForManager);
			dao.update(entity);
			RegionTown rt = RegionTownService.instance().findByTown(entity.getCompany().getResidentialAddress().getTown());
			List<Users> users = new ArrayList<>();
			users.add(rt.getCrm().getUsers());
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, users, false);
		} else {
			TasksService.instance().completeTask(tasks);
		}
	}

	public void rejectWorkflow(WorkplaceMonitoring entity, Users user, Tasks tasks) throws Exception {
		RegionTown rt = RegionTownService.instance().findByTown(entity.getCompany().getResidentialAddress().getTown());
		List<Users> users = new ArrayList<>();
		users.add(rt.getClo().getUsers());
		TasksService.instance().findFirstInProcessAndCreateTask("", user, entity.getId(), WorkplaceMonitoring.class.getName(), true, ConfigDocProcessEnum.WorkplaceMonitoring, null, users);
		TasksService.instance().completeTask(tasks);
	}

	public void finalApproveWorkflow(WorkplaceMonitoring entity, Users user, Tasks tasks) throws Exception {
		entity.setStatus(ApprovalEnum.Approved);
		entity.setApprovalDate(getSynchronizedDate());
		dao.update(entity);
		TasksService.instance().completeTask(tasks);
		if (HAJConstants.DEV_TEST_PROD != "P") {
			if (entity.getIgnoreTranchePayments() == null || !entity.getIgnoreTranchePayments()) {
				List<CompanyLearners> companyLearners = CompanyLearnersService.instance().findByEmployer(entity.getCompany().getId());
				for (CompanyLearners companyLearner : companyLearners) {
					List<DesignatedTradeLevel> dtl = new ArrayList<>();
					// locate designated trade 
					DesignatedTrade dt = DesignatedTradeLevelService.instance().locateDesignatedTradeByQualification(companyLearner.getQualification());
					if (dt != null && dt.getId() != null) {
						dtl = DesignatedTradeLevelService.instance().findBydesignatedTradeIdNotRecordedSingle(dt, companyLearner);	
					}					
					if (dtl.isEmpty()) {
						activeContractDetailService.addTranchePaymentDetail(companyLearner, user, companyLearner.getInterventionType().getTranchintervals() == 3 ? 0.5 : 0.25, companyLearner.getInterventionType().getTranchintervals() == 3 ? TrancheEnum.TRANCHE_THREE : TrancheEnum.TRANCHE_FOUR, true);
					} else if (companyLearner.getInterventionType().getTranchintervals() == 4) {
						double remainingLevels = (double) DesignatedTradeLevelService.instance().findBydesignatedTradeIdNotRecordedSingleCount(companyLearner.getQualification().getDesignatedTrade(), companyLearner);
						double total = (double) DesignatedTradeLevelService.instance().countEntiresByDesignatedTradeId(companyLearner.getQualification().getDesignatedTrade());
						double percentageCompleted = remainingLevels / total;
						if (percentageCompleted >= 0.5) activeContractDetailService.addTranchePaymentDetail(companyLearner, user, 0.25, TrancheEnum.TRANCHE_THREE, true);
					}
				}
			}

		}
	}

	public void finalRejectWorkflow(WorkplaceMonitoring entity, Users user, Tasks tasks) throws Exception {
		entity.setStatus(ApprovalEnum.Rejected);
		entity.setApprovalDate(getSynchronizedDate());
		dao.update(entity);
		TasksService.instance().completeTask(tasks);
	}
}
