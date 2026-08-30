package haj.com.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.jsoup.Jsoup;
import org.primefaces.model.SortOrder;

import haj.com.bean.ExtensionRequestReportBean;
import haj.com.bean.PipReportBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.ProjectImplementationPlanDAO;
import haj.com.entity.ActiveContracts;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.ConfigDoc;
import haj.com.entity.DgAllocation;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.MandatoryGrant;
import haj.com.entity.MandatoryGrantRecommendation;
import haj.com.entity.ProjectImplementationPlan;
import haj.com.entity.ProjectImplementationPlanLearners;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.InterventionTypeEnum;
import haj.com.entity.enums.MoaTypeEnum;
import haj.com.entity.enums.TrancheEnum;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.Qualification;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

import static haj.com.constants.HAJConstants.SKILLS_PROGRAM_LIST;
import static haj.com.constants.HAJConstants.SKILLS_SET_LIST;

public class ProjectImplementationPlanService extends AbstractService {

	/** The dao. */
	private ProjectImplementationPlanDAO dao = new ProjectImplementationPlanDAO();
	private MandatoryGrantDetailService mandatoryGrantDetailService = new MandatoryGrantDetailService();

	private MandatoryGrantService mandatoryGrantServicePIP;
	private MandatoryGrantRecommendationService mandatoryGrantRecommendationServicePIP;

	/**
	 * Get all ProjectImplementationPlan
	 * 
	 * @author TechFinium
	 * @see ProjectImplementationPlan
	 * @return a list of {@link haj.com.entity.ProjectImplementationPlan}
	 * @throws Exception
	 *             the exception
	 */
	public List<ProjectImplementationPlan> allProjectImplementationPlan() throws Exception {
		return dao.allProjectImplementationPlan();
	}

	public List<ProjectImplementationPlan> allProjectImplementationPlanNotLinkedToActiveContracts() throws Exception {
		return dao.allProjectImplementationPlanNotLinkedToActiveContracts();
	}

	/**
	 * Create or update ProjectImplementationPlan.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ProjectImplementationPlan
	 */
	public void create(ProjectImplementationPlan entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	public void create(List<ProjectImplementationPlan> pip) throws Exception {
		dao.updateBatch((List<IDataEntity>) (List<?>) pip);
	}

	/**
	 * Update ProjectImplementationPlan.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ProjectImplementationPlan
	 */
	public void update(ProjectImplementationPlan entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete ProjectImplementationPlan.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ProjectImplementationPlan
	 */
	public void delete(ProjectImplementationPlan entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.ProjectImplementationPlan}
	 * @throws Exception
	 *             the exception
	 * @see ProjectImplementationPlan
	 */
	public ProjectImplementationPlan findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find ProjectImplementationPlan by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.ProjectImplementationPlan}
	 * @throws Exception
	 *             the exception
	 * @see ProjectImplementationPlan
	 */
	public List<ProjectImplementationPlan> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load ProjectImplementationPlan
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.ProjectImplementationPlan}
	 * @throws Exception
	 *             the exception
	 */
	public List<ProjectImplementationPlan> allProjectImplementationPlan(int first, int pageSize) throws Exception {
		return dao.allProjectImplementationPlan(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of ProjectImplementationPlan for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the ProjectImplementationPlan
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(ProjectImplementationPlan.class);
	}

	/**
	 * Lazy load ProjectImplementationPlan with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            ProjectImplementationPlan class
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
	 * @return a list of {@link haj.com.entity.ProjectImplementationPlan}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlan> allProjectImplementationPlan(Class<ProjectImplementationPlan> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<ProjectImplementationPlan>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
	}

	/**
	 * Get count of ProjectImplementationPlan for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            ProjectImplementationPlan class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the ProjectImplementationPlan entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<ProjectImplementationPlan> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlan> sortAndFilterSearch(Class<ProjectImplementationPlan> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<ProjectImplementationPlan>) dao.sortAndFilterSearch(class1, first, pageSize, sortField, sortOrder,filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlan> sortAndFilterSearchByWspYear(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Integer finYear) throws Exception {
		String hql = "select o from ProjectImplementationPlan o where o.wsp.finYear = :finYear";
		filters.put("finYear", finYear);
		return (List<ProjectImplementationPlan>) dao.sortAndFilterSearchWhere( first, pageSize, sortField, sortOrder,filters, hql);
	}

	/**
	 * Get count of ProjectImplementationPlan for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            ProjectImplementationPlan class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the ProjectImplementationPlan entity
	 * @throws Exception
	 *             the exception
	 */
	public int countSearch(Class<ProjectImplementationPlan> entity, Map<String, Object> filters) throws Exception {
		return dao.countSearch(entity, filters);
	}
	
	public int countSearchByWspYear(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from ProjectImplementationPlan o where o.wsp.finYear = :finYear";
		return dao.countSearchWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlan> allProjectImplementationPlanByWsp(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters, Long wspId) throws Exception {
		String hql = "select o from ProjectImplementationPlan o where o.wsp.id = :wspId and o.totalAwardAmount > 0";
		filters.put("wspId", wspId);
		return (List<ProjectImplementationPlan>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllProjectImplementationPlanByWsp(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from ProjectImplementationPlan o where o.wsp.id = :wspId and o.totalAwardAmount > 0";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlan> allProjectImplementationPlan(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters, Company wsp) throws Exception {
		String hql = "select o from ProjectImplementationPlan o where o.wsp.company.id = :companyID";
		filters.put("companyID", wsp.getId());
		return (List<ProjectImplementationPlan>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters,
				hql);
	}

	public int count(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from ProjectImplementationPlan o where o.wsp.company.id = :companyID";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlan> allProjectImplementationPlanWhereTotalaountIsGreaterThanZero(int first,
			int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long wspId)
			throws Exception {
		String hql = "select o from ProjectImplementationPlan o where o.wsp.id = :wspId and o.totalAwardAmount > 0D";
		filters.put("wspId", wspId);
		return (List<ProjectImplementationPlan>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters,
				hql);
	}

	public int countAllProjectImplementationPlanWhereTotalaountIsGreaterThanZero(Map<String, Object> filters)
			throws Exception {
		String hql = "select count(o) from ProjectImplementationPlan o where o.wsp.id = :wspId and o.totalAwardAmount > 0";
		return dao.countWhere(filters, hql);
	}

	public List<ProjectImplementationPlan> findByCompany(Company wsp) throws Exception {
		return dao.findByCompany(wsp.getId());
	}

	public List<ProjectImplementationPlan> findByWspWhereAwarded(Long wspId) throws Exception {
		return dao.findByWspWhereAwarded(wspId);
	}

	public List<ProjectImplementationPlan> findByCompanyAndInterventionType(Company wsp,
			InterventionType interventionType) throws Exception {
		if (wsp != null && interventionType != null) {
			return dao.findByCompanyAndInterventionType(wsp.getId(), interventionType.getId());
		} else {
			return (new ArrayList<ProjectImplementationPlan>());
		}
	}

	public List<ProjectImplementationPlan> findByCompanyAndInterventionTypeForLearners(Company company,
			InterventionType interventionType) throws Exception {
		List<ProjectImplementationPlan> list = new ArrayList<ProjectImplementationPlan>();
		if (company != null && interventionType != null) {
			list = dao.findByCompanyAndInterventionType(company.getId(), interventionType.getId());
			for (ProjectImplementationPlan pip : list) {
				if (pip.getDgAllocation() != null && pip.getDgAllocation().getMandatoryGrant() != null) {
					resolveQualification(pip.getDgAllocation().getMandatoryGrant());
				}
			}
		}
		return list;
	}

	public List<ProjectImplementationPlan> findByCompanyAndInterventionTypeAndApproved(Company company,
			InterventionType interventionType) throws Exception {
		List<ProjectImplementationPlan> list = new ArrayList<ProjectImplementationPlan>();
		if (company != null && interventionType != null) {
			list = dao.findByCompanyAndInterventionTypeAndApproved(company.getId(), interventionType.getId());
			resolveMandatoryGrantRecommendation(list);
		}
		return list;
	}

	public List<ProjectImplementationPlan> findByCompanyAndInterventionTypeAndApproved(Company company,
			InterventionType interventionType, CompanyLearners cl) throws Exception {
		List<ProjectImplementationPlan> list = new ArrayList<ProjectImplementationPlan>();
		if (company != null && interventionType != null && cl != null) {
			if (SKILLS_PROGRAM_LIST.contains(interventionType.getId())) {
				list = dao.findByCompanyAndInterventionTypeAndSkillsProgramme(company.getId(), interventionType.getId(),
						cl.getSkillsProgram().getId());
			} else if (SKILLS_SET_LIST.contains(interventionType.getId())) {
				list = dao.findByCompanyAndInterventionTypeAndSkillsSet(company.getId(), interventionType.getId(),
						cl.getSkillsSet().getId());
			} else if (interventionType.getInterventionTypeEnum() == InterventionTypeEnum.Apprenticeship) {
				list = dao.findByCompanyAndInterventionTypeAndQualification(company.getId(), interventionType.getId(),
						cl.getQualification().getId());
			} else if (interventionType.getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
				list = dao.findByCompanyAndInterventionTypeAndQualification(company.getId(), interventionType.getId(),
						cl.getLearnership().getQualification().getId());
			} else if (cl.getUnitStandard() != null) {
				list = dao.findByCompanyAndInterventionTypeAndUnitStandards(company.getId(), interventionType.getId(),
						cl.getUnitStandard().getId());
			} else if (interventionType.getId() == HAJConstants.NON_CREDIT_BEARING_SHORT_COURSE) {
				// list =
				// dao.findByCompanyAndInterventionTypeAndApproved(company.getId(),
				// interventionType.getId());
			} else if (cl.getQualification() != null) {
				list = dao.findByCompanyAndInterventionTypeAndQualification(company.getId(), interventionType.getId(),
						cl.getQualification().getId());
			}
			resolveMandatoryGrantRecommendation(list);
		}
		return list;
	}

	private List<ProjectImplementationPlan> resolveMandatoryGrantRecommendation(List<ProjectImplementationPlan> list)
			throws Exception {
		MandatoryGrantRecommendationService mandatoryGrantRecommendationService = new MandatoryGrantRecommendationService();
		for (ProjectImplementationPlan pip : list) {
			if (pip.getDgAllocation() != null && pip.getDgAllocation().getMandatoryGrant() != null) {
				MandatoryGrantRecommendation mandatoryGrantRecommendation = mandatoryGrantRecommendationService
						.findByMandatoryGrant(pip.getDgAllocation().getMandatoryGrant());
				if (mandatoryGrantRecommendation != null) {
					resolveMandatoryGrantRecommendationQualification(pip.getDgAllocation().getMandatoryGrant(),
							mandatoryGrantRecommendation);
				} else {
					resolveQualification(pip.getDgAllocation().getMandatoryGrant());
				}
			}
		}
		return list;
	}

	public void resolveMandatoryGrantRecommendationQualification(MandatoryGrant mg,
			MandatoryGrantRecommendation mandatoryGrantRecommendation) {
		if (mandatoryGrantRecommendation != null) {
			if (mandatoryGrantRecommendation.getSkillsProgram() != null) {
				// mg.setQualification(mandatoryGrantRecommendation.getSkillsProgram()
				// .getQualification());
				Qualification q = new Qualification();
				q.setDescription(mandatoryGrantRecommendation.getSkillsProgram().getDescription());
				q.setCodeString(mandatoryGrantRecommendation.getSkillsProgram().getProgrammeID());
				mg.setQualification(q);
			} else if (mandatoryGrantRecommendation.getSkillsSet() != null) {
				// mg.setQualification(mandatoryGrantRecommendation.getSkillsSet().getQualification());
				Qualification q = new Qualification();
				q.setDescription(mandatoryGrantRecommendation.getSkillsSet().getDescription());
				q.setCodeString(mandatoryGrantRecommendation.getSkillsSet().getProgrammeID());
				mg.setQualification(q);
			} else if (mandatoryGrantRecommendation.getUnitStandards() != null) {
				mg.setQualification(mandatoryGrantRecommendation.getUnitStandards().getQualification());
			} else if (mandatoryGrantRecommendation.getNonCreditBearingIntervationTitle() != null) {
				Qualification q = new Qualification();
				q.setDescription(mandatoryGrantRecommendation.getNonCreditBearingIntervationTitle().getDescription());
				// q.setCode(mg.getNonCreditBearingIntervationTitle().get);
				mg.setQualification(q);
			} else if (mandatoryGrantRecommendation.getQualification() != null) {
				mg.setQualification(mandatoryGrantRecommendation.getQualification());
			} else {
				Qualification q = new Qualification();
				q.setDescription("N/A");
				q.setCode(0);
				q.setNqflevelg2description("N/A");
				mg.setQualification(q);
			}
		}
	}

	public void resolveQualification(MandatoryGrant mg) {
		if (mg.getQualification() == null) {
			if (mg.getSkillsProgram() != null) {
				// mg.setQualification(mg.getSkillsProgram()
				// .getQualification());
				Qualification q = new Qualification();
				q.setDescription(mg.getSkillsProgram().getDescription());
				q.setCodeString(mg.getSkillsProgram().getProgrammeID());
				mg.setQualification(q);
			} else if (mg.getSkillsSet() != null) {
				// mg.setQualification(mg.getSkillsSet().getQualification());
				Qualification q = new Qualification();
				q.setDescription(mg.getSkillsSet().getDescription());
				q.setCodeString(mg.getSkillsSet().getProgrammeID());
				mg.setQualification(q);
			} else if (mg.getUnitStandard() != null) {
				mg.setQualification(mg.getUnitStandard().getQualification());
			} else if (mg.getNonCreditBearingIntervationTitle() != null) {
				Qualification q = new Qualification();
				q.setDescription(mg.getNonCreditBearingIntervationTitle().getDescription());
				// q.setCode(mg.getNonCreditBearingIntervationTitle().get);
				mg.setQualification(q);
			} else {
				Qualification q = new Qualification();
				q.setDescription("N/A");
				q.setCode(0);
				q.setNqflevelg2description("N/A");
				mg.setQualification(q);
			}
		}
	}

	public List<ProjectImplementationPlan> findByCompanyAndInterventionTypeAndStatus(Company wsp,
			InterventionType interventionType, ApprovalEnum status) throws Exception {
		if (wsp != null && interventionType != null) {
			return dao.findByCompanyAndInterventionTypeAndStatus(wsp.getId(), interventionType.getId(), status);
		} else {
			return (new ArrayList<ProjectImplementationPlan>());
		}
	}

	public List<ProjectImplementationPlan> findByActiveContract(ActiveContracts activeContracts) throws Exception {
		return dao.findByActiveContract(activeContracts);
	}

	public Integer countByActiveContract(ActiveContracts activeContracts) throws Exception {
		return dao.countByActiveContract(activeContracts);
	}

	public List<ProjectImplementationPlan> findByWsp(Wsp wsp) throws Exception {
		return dao.findByWsp(wsp.getId());
	}

	public List<ProjectImplementationPlan> findByWspWhereTotalaountIsGreaterThanZero(Wsp wsp) throws Exception {
		return dao.findByWspWhereTotalaountIsGreaterThanZero(wsp.getId());
	}

	@SuppressWarnings("unchecked")
	public void generateImpementationPlan(Wsp wsp, ActiveContracts ac) throws Exception {
		List<IDataEntity> createList = new ArrayList<>();
		List<DgAllocation> list = mandatoryGrantDetailService.findDgAllocationPlanData(wsp.getId());
		for (DgAllocation dga : list) {
			ProjectImplementationPlan demo = new ProjectImplementationPlan();
			demo.setInterventionType(dga.getInterventionType());
			demo.setInterventionTypeDescription(dga.getInterventionType().getDescription());
			demo.setWsp(wsp);
			demo.setFullyFundedLearnerAwarded(dga.getMaxPossibleLearners());
			demo.setCoFundingLearnersAwarded(dga.getCoFundingLearnersAwarded());
			demo.setTotalAwardAmount(dga.getTotalAwardAmount());
			demo.setActiveContracts(ac);
			demo.setDgAllocation(dga);
			createList.add(demo);

			// create learner link list
			int learnerNumber = 1;
			int totalLearners = 0;
			// dga.getMaxPossibleLearners() + dga.getCoFundingLearnersAwarded()
			if (dga != null && dga.getMaxPossibleLearners() != null) {
				totalLearners = totalLearners + dga.getMaxPossibleLearners();
			}
			if (dga != null && dga.getCoFundingLearnersAwarded() != null) {
				totalLearners = totalLearners + dga.getCoFundingLearnersAwarded();
			}
			for (int i = 1; i <= totalLearners; i++) {
				ProjectImplementationPlanLearners link = new ProjectImplementationPlanLearners();
				link.setAllPaymentsCompleted(false);
				link.setNextTranchPayment(TrancheEnum.TRANCHE_TWO);
				link.setProjectImplementationPlan(demo);
				link.setDgAllocation(dga);
				link.setWsp(wsp);
				if (wsp != null && wsp.getCompany() != null && wsp.getCompany().getId() != null) {
					link.setCompany(wsp.getCompany());
				}
				link.setInterventionTypeLink(dga.getInterventionType());
				link.setActiveContracts(ac);
				link.setLearnerNumber(learnerNumber);
				createList.add(link);
				learnerNumber++;
			}
		}
		if (!createList.isEmpty()) {
			dao.createBatch(createList);
		}
		// List<ProjectImplementationPlan> pip =
		// mandatoryGrantDetailService.findProjectImplementationPlanData(wsp);
		/*
		 * List<ProjectImplementationPlan> pip =
		 * mandatoryGrantDetailService.findProjectImplementationPlanDataUngroup(
		 * wsp); for (ProjectImplementationPlan projectImplementationPlan : pip)
		 * { projectImplementationPlan.setWsp(wsp); }
		 * dao.createBatch((List<IDataEntity>) (List<?>) pip);
		 */
	}

	public void prepareNewRegistration(ProjectImplementationPlan entity) throws Exception {

		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(),
					ConfigDocProcessEnum.PIP_UPDATE));
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessNotUploaded(entity.getClass().getName(),
					entity.getId(), ConfigDocProcessEnum.PIP_UPDATE);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessA(ConfigDocProcessEnum.PIP_UPDATE);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}

	public void requestWorkflow(ProjectImplementationPlan entity, Users u) throws Exception {
		entity.setStatus(ApprovalEnum.PendingApproval);
		dao.update(entity);
		TasksService.instance().findFirstInProcessAndCreateTask(u, entity.getId(),
				ProjectImplementationPlan.class.getName(), ConfigDocProcessEnum.PIP_UPDATE, false);
	}

	public void completeWorkflow(ProjectImplementationPlan entity, Users user, Tasks tasks) throws Exception {
		String error = "";
		if (entity.getDocs() != null) {
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null)
						doc.setData(docByte.getData());
				}
				if ((doc.getData() == null || doc.getData().length == 0)
						&& doc.getConfigDoc().getRequiredDocument() != null
						&& doc.getConfigDoc().getRequiredDocument()) {
					error += "Upload " + doc.getConfigDoc().getName();
				}
			}
		}

		if (error.length() > 0)
			throw new ValidationException(error);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}

	public void approveWorkflow(ProjectImplementationPlan entity, Users user, Tasks tasks) throws Exception {
		entity.setStatus(ApprovalEnum.WaitingForManager);
		dao.update(entity);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}

	public void rejectWorkflow(ProjectImplementationPlan entity, Users user, Tasks tasks) throws Exception {
		TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, null);
	}

	public void finalApproveWorkflow(ProjectImplementationPlan entity, Users user, Tasks tasks) throws Exception {
		entity.setStatus(ApprovalEnum.Approved);
		entity.setApprovalDate(getSynchronizedDate());
		dao.update(entity);
		TasksService.instance().completeTask(tasks);
	}

	public void finalRejectWorkflow(ProjectImplementationPlan entity, Users user, Tasks tasks) throws Exception {
		entity.setStatus(ApprovalEnum.Rejected);
		entity.setApprovalDate(getSynchronizedDate());
		dao.update(entity);
		TasksService.instance().completeTask(tasks);
	}

	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlan> cloCrmProjectImplementationPlanRegionReportByMoaTypeAndGrantYear(int first,
			int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long userID,
			MoaTypeEnum moaType, Integer finYear) throws Exception {
		String hql = "select o from ProjectImplementationPlan o where " + " o.activeContracts.moaType = :moaType and "
				+ " o.activeContracts.dgAllocationParent.wsp.finYear = :finYear and "
				+ " o.activeContracts.dgAllocationParent.wsp.company.nonSetaCompany = false and "
				+ " o.activeContracts.dgAllocationParent.wsp.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :userID or x.crm.users.id = :userID)";
		filters.put("userID", userID);
		filters.put("moaType", moaType);
		filters.put("finYear", finYear);
		return populateProjectImplementationPlanRegionReportInformation((List<ProjectImplementationPlan>) dao
				.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countCloCrmProjectImplementationPlanRegionReportByMoaTypeAndGrantYear(Map<String, Object> filters)
			throws Exception {
		String hql = "select count(o) from ProjectImplementationPlan o where "
				+ " o.activeContracts.moaType = :moaType and "
				+ " o.activeContracts.dgAllocationParent.wsp.finYear = :finYear and "
				+ " o.activeContracts.dgAllocationParent.wsp.company.nonSetaCompany = false and "
				+ " o.activeContracts.dgAllocationParent.wsp.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :userID or x.crm.users.id = :userID)";
		return dao.countWhere(filters, hql);
	}

	public int countByCloCrmProjectImplementationPlanByMoaTypeAndGrantYear(Long userID, MoaTypeEnum moaType,
			Integer finYear) throws Exception {
		return dao.countByCloCrmProjectImplementationPlanByMoaTypeAndGrantYear(userID, moaType, finYear);
	}

	private List<ProjectImplementationPlan> populateProjectImplementationPlanRegionReportInformation(
			List<ProjectImplementationPlan> projectImplementationPlanList) throws Exception {
		for (ProjectImplementationPlan pip : projectImplementationPlanList) {
			populateProjectImplementationPlanRegionReportInformation(pip);
		}
		return projectImplementationPlanList;
	}

	private ProjectImplementationPlan populateProjectImplementationPlanRegionReportInformation(
			ProjectImplementationPlan pip) throws Exception {
		pip.setTotalApprovedPayments(0.0d);
		// set total reminaing
		if (pip.getTotalAwardAmount() == null || pip.getTotalAwardAmount().doubleValue() == 0.0) {
			pip.setTotalOutstandingPayments(0.0);
		} else {
			pip.setTotalOutstandingPayments(GenericUtility
					.roundToPrecision(pip.getTotalAwardAmount().doubleValue() - pip.getTotalApprovedPayments(), 2));
		}
		return pip;
	}

	public void populatePipInterventionQualificationDesc(ProjectImplementationPlan pip) throws Exception {
		if (pip != null && pip.getId() != null) {
			StringBuilder result = new StringBuilder();
			// get Mandatory grant
			MandatoryGrant mandatoryGrant = null;
			if (pip.getDgAllocation() != null && pip.getDgAllocation().getMandatoryGrant() != null
					&& pip.getDgAllocation().getMandatoryGrant().getId() != null) {
				if (mandatoryGrantServicePIP == null) {
					mandatoryGrantServicePIP = new MandatoryGrantService();
				}
				mandatoryGrant = mandatoryGrantServicePIP
						.findByKeyExcludeImport(pip.getDgAllocation().getMandatoryGrant().getId());
			}

			if (mandatoryGrant != null && mandatoryGrant.getId() != null) {
				MandatoryGrantRecommendation mandatoryGrantRecommendation = null;
				if (mandatoryGrantRecommendationServicePIP == null) {
					mandatoryGrantRecommendationServicePIP = new MandatoryGrantRecommendationService();
				}
				mandatoryGrantRecommendation = mandatoryGrantRecommendationServicePIP
						.findByMandatoryGrant(mandatoryGrant);

				if (mandatoryGrantRecommendation != null && mandatoryGrantRecommendation.getId() != null) {
					if (mandatoryGrantRecommendation.getQualification() != null
							&& mandatoryGrantRecommendation.getQualification().getId() != null) {
						if (mandatoryGrantRecommendation.getQualification().getCode() != null) {
							result.append("(" + mandatoryGrantRecommendation.getQualification().getCode() + ") ");
						}
						if (mandatoryGrantRecommendation.getQualification().getDescription() != null
								&& !mandatoryGrantRecommendation.getQualification().getDescription().trim().isEmpty()) {
							result.append(mandatoryGrantRecommendation.getQualification().getDescription().trim());
						}
					} else if (mandatoryGrantRecommendation.getSkillsSet() != null
							&& mandatoryGrantRecommendation.getSkillsSet().getId() != null) {
						if (mandatoryGrantRecommendation.getSkillsSet().getCode() != null
								&& !mandatoryGrantRecommendation.getSkillsSet().getCode().trim().isEmpty()) {
							result.append("(" + mandatoryGrantRecommendation.getSkillsSet().getCode().trim() + ") ");
						}
						if (mandatoryGrantRecommendation.getSkillsSet().getDescription() != null
								&& !mandatoryGrantRecommendation.getSkillsSet().getDescription().trim().isEmpty()) {
							result.append(mandatoryGrantRecommendation.getSkillsSet().getDescription().trim());
						}
					} else if (mandatoryGrantRecommendation.getSkillsProgram() != null
							&& mandatoryGrantRecommendation.getSkillsProgram().getId() != null) {
						if (mandatoryGrantRecommendation.getSkillsProgram().getProgrammeID() != null
								&& !mandatoryGrantRecommendation.getSkillsProgram().getProgrammeID().trim().isEmpty()) {
							result.append("(" + mandatoryGrantRecommendation.getSkillsProgram().getProgrammeID().trim()
									+ ") ");
						}
						if (mandatoryGrantRecommendation.getSkillsProgram().getDescription() != null
								&& !mandatoryGrantRecommendation.getSkillsProgram().getDescription().trim().isEmpty()) {
							result.append(mandatoryGrantRecommendation.getSkillsProgram().getDescription().trim());
						}
					} else if (mandatoryGrantRecommendation.getUnitStandards() != null
							&& mandatoryGrantRecommendation.getUnitStandards().getId() != null) {
						if (mandatoryGrantRecommendation.getUnitStandards().getCode() != null) {
							result.append("(" + mandatoryGrantRecommendation.getUnitStandards().getCode() + ") ");
						}
						if (mandatoryGrantRecommendation.getUnitStandards().getTitle() != null
								&& !mandatoryGrantRecommendation.getUnitStandards().getTitle().trim().isEmpty()) {
							result.append(mandatoryGrantRecommendation.getUnitStandards().getTitle().trim());
						}
					} else if (mandatoryGrantRecommendation.getNonCreditBearingIntervationTitle() != null
							&& mandatoryGrantRecommendation.getNonCreditBearingIntervationTitle().getId() != null) {
						if (mandatoryGrantRecommendation.getNonCreditBearingIntervationTitle().getCode() != null
								&& !mandatoryGrantRecommendation.getNonCreditBearingIntervationTitle().getCode().trim()
										.isEmpty()) {
							result.append(
									"(" + mandatoryGrantRecommendation.getNonCreditBearingIntervationTitle().getCode()
											+ ") ");
						}
						if (mandatoryGrantRecommendation.getNonCreditBearingIntervationTitle().getDescription() != null
								&& !mandatoryGrantRecommendation.getNonCreditBearingIntervationTitle().getDescription()
										.trim().isEmpty()) {
							result.append(mandatoryGrantRecommendation.getNonCreditBearingIntervationTitle()
									.getDescription().trim());
						}
					}
				}

				if (result.toString().isEmpty()) {
					if (mandatoryGrant.getQualification() != null
							&& mandatoryGrant.getQualification().getId() != null) {
						if (mandatoryGrant.getQualification().getCode() != null) {
							result.append("(" + mandatoryGrant.getQualification().getCode() + ") ");
						}
						if (mandatoryGrant.getQualification().getDescription() != null
								&& !mandatoryGrant.getQualification().getDescription().trim().isEmpty()) {
							result.append(mandatoryGrant.getQualification().getDescription().trim());
						}
					} else if (mandatoryGrant.getSkillsSet() != null && mandatoryGrant.getSkillsSet().getId() != null) {
						if (mandatoryGrant.getSkillsSet().getCode() != null
								&& !mandatoryGrant.getSkillsSet().getCode().trim().isEmpty()) {
							result.append("(" + mandatoryGrant.getSkillsSet().getCode().trim() + ") ");
						}
						if (mandatoryGrant.getSkillsSet().getDescription() != null
								&& !mandatoryGrant.getSkillsSet().getDescription().trim().isEmpty()) {
							result.append(mandatoryGrant.getSkillsSet().getDescription().trim());
						}
					} else if (mandatoryGrant.getSkillsProgram() != null
							&& mandatoryGrant.getSkillsProgram().getId() != null) {
						if (mandatoryGrant.getSkillsProgram().getProgrammeID() != null
								&& !mandatoryGrant.getSkillsProgram().getProgrammeID().trim().isEmpty()) {
							result.append("(" + mandatoryGrant.getSkillsProgram().getProgrammeID().trim() + ") ");
						}
						if (mandatoryGrant.getSkillsProgram().getDescription() != null
								&& !mandatoryGrant.getSkillsProgram().getDescription().trim().isEmpty()) {
							result.append(mandatoryGrant.getSkillsProgram().getDescription().trim());
						}
					} else if (mandatoryGrant.getUnitStandard() != null
							&& mandatoryGrant.getUnitStandard().getId() != null) {
						if (mandatoryGrant.getUnitStandard().getCode() != null) {
							result.append("(" + mandatoryGrant.getUnitStandard().getCode() + ") ");
						}
						if (mandatoryGrant.getUnitStandard().getTitle() != null
								&& !mandatoryGrant.getUnitStandard().getTitle().trim().isEmpty()) {
							result.append(mandatoryGrant.getUnitStandard().getTitle().trim());
						}
					} else if (mandatoryGrant.getNonCreditBearingIntervationTitle() != null
							&& mandatoryGrant.getNonCreditBearingIntervationTitle().getId() != null) {
						if (mandatoryGrant.getNonCreditBearingIntervationTitle().getCode() != null
								&& !mandatoryGrant.getNonCreditBearingIntervationTitle().getCode().trim().isEmpty()) {
							result.append("(" + mandatoryGrant.getNonCreditBearingIntervationTitle().getCode() + ") ");
						}
						if (mandatoryGrant.getNonCreditBearingIntervationTitle().getDescription() != null
								&& !mandatoryGrant.getNonCreditBearingIntervationTitle().getDescription().trim()
										.isEmpty()) {
							result.append(mandatoryGrant.getNonCreditBearingIntervationTitle().getDescription().trim());
						}
					} else {
						result.append("Unable to locate information");
					}
				}

				mandatoryGrantRecommendation = null;
				mandatoryGrant = null;
			}

			pip.setInterventionQualificationDesc(result.toString());
		}
	}

	public List<PipReportBean> populatePipReportBean(Integer grantYear) throws Exception {
		return dao.populatePipReportBean(grantYear);
	}
	
	public void downloadPipReport(Integer grantYear) throws Exception{
		Workbook wb = new SXSSFWorkbook(100);
		Sheet sh = wb.createSheet();
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		Integer counter = 1;
		data.put(counter.toString(), new Object[] { "Entity ID", "Company Name", "Chamber", "Region", "Reference Number", "MOA Type", "Category", "Learners", "Projected Recruitment Date", "Projected Induction Date", "Projected Date of Registartion", "Estimate 50% Progress Date", "Estimated Complete Date", "Accredited Provider Identified", "Logistical Aspects Addressed", "CLO Name and Surname", "Primary SDF Name and Surname", "Primary SDF Email", "Primary SDF Contact Number", "Secondary SDF Name and Surname", "Secondary SDF Name Email", "Secondary SDF Contact Number" });
		counter++;
		populateDataForPipReport(data, counter, grantYear);
		counter = null;
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sh.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Integer)
					cell.setCellValue((Integer) obj);
				else if (obj instanceof Double)
					cell.setCellValue((Double) obj);;
			}
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			wb.write(bos);
			byte[] bytes = bos.toByteArray();
			String fileName = "PIP Report.xlsx";
			JasperService.instance().downloadFileExcel(bytes, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	private void populateDataForPipReport(Map<String, Object[]> data, Integer counter,Integer grantYear) throws Exception{
		int counterForPopulation = counter;
		List<PipReportBean> resultsList = populatePipReportBean(grantYear);
		// populate data found into report
		for (PipReportBean entry : resultsList) {
			populateDataPipReport(data, entry, counterForPopulation);
			counterForPopulation++;
		}
		resultsList.clear();
		counter = counterForPopulation;
	}

	private void populateDataPipReport(Map<String, Object[]> data, PipReportBean entry, Integer counter) throws Exception{	
		String projectedrecruitmentDate = "N/A";
		if (entry.getProjectedRecruitmentDate() != null) {
			projectedrecruitmentDate = HAJConstants.sdfDDMMYYYYHHmm.format(entry.getProjectedRecruitmentDate());
		}
		
		String projectedInductionDate = "N/A";
		if (entry.getProjectedInductionDate() != null) {
			projectedInductionDate = HAJConstants.sdfDDMMYYYYHHmm.format(entry.getProjectedInductionDate());
		}
		
		String projectedDateOfRegistartion = "N/A";
		if (entry.getProjectedDateOfRegistration() != null) {
			projectedDateOfRegistartion = HAJConstants.sdfDDMMYYYYHHmm.format(entry.getProjectedDateOfRegistration());
		}
		
		String estimateProgressDate = "N/A";
		if (entry.getEstimatedProgressDate() != null) {
			estimateProgressDate = HAJConstants.sdfDDMMYYYYHHmm.format(entry.getEstimatedProgressDate());
		}
		
		String estimateCompleteDate = "N/A";
		if (entry.getEstimatedCompleteDate() != null) {
			estimateCompleteDate = HAJConstants.sdfDDMMYYYYHHmm.format(entry.getEstimatedCompleteDate());
		}
		
		/*
		 *  "Entity ID", "Company Name", "Chamber", "Region", "Reference Number", "MOA Type", "Category", "Learners", 
		 *  "Projected Recruitment Date", "Projected Induction Date", "Projected Date of Registartion", "Estimate 50% Progress Date", "Estimated Complete Date", 
		 *  "Accredited Provider Identified", "Logistical Aspects Addressed", "CLO Name and Surname", "Primary SDF Name and Surname", 
		 *  "Primary SDF Email", "Primary SDF Contact Number", "Secondary SDF Name and Surname", "Secondary SDF Name Email", "Secondary SDF Contact Number"
		 *  , entry.get 
		 */
		data.put(counter.toString(), new Object[] { entry.getEntityId(), entry.getCompanyName(), entry.getChamber(), entry.getRegion(), entry.getReferanceNumber(), entry.getMoaType(), entry.getCategory(), entry.getFullyFundedLearnerAwarded()
				, projectedrecruitmentDate, projectedInductionDate, projectedDateOfRegistartion, estimateProgressDate, estimateCompleteDate 
				, entry.getAccreditedProviderIdentified(), entry.getLogisticalAspectsAddressed(), entry.getCloFullName(), entry.getSdfFullName()
				, entry.getSdfEmail(), entry.getSdfContactNumber(), entry.getSecSdfFullName(), entry.getSecSdfEmail(), entry.getSecSdfContactNumber()});
	}

}