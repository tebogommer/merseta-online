package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.ProjectImplementationPlanLearnersDAO;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.ProjectImplementationPlan;
import haj.com.entity.ProjectImplementationPlanLearners;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.TrancheEnum;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.InterventionTypeService;
import haj.com.utils.GenericUtility;

public class ProjectImplementationPlanLearnersService extends AbstractService {

	/** The dao. */
	private ProjectImplementationPlanLearnersDAO dao = new ProjectImplementationPlanLearnersDAO();
	
	private static ProjectImplementationPlanLearnersService projectImplementationPlanLearnersService;
	public static synchronized ProjectImplementationPlanLearnersService instance() {
		if (projectImplementationPlanLearnersService == null) {
			projectImplementationPlanLearnersService = new ProjectImplementationPlanLearnersService();
		}
		return projectImplementationPlanLearnersService;
	}

	/** Service Levels */
	private WspService wspService = null;
	private CompanyService companyService = null;
	private CompanyLearnersService companyLearnersService = null;
	private InterventionTypeService interventionTypeService = null;
	private ProjectImplementationPlanService projectImplementationPlanService = null;

	/**
	 * Get all ProjectImplementationPlanLearners
	 * 
	 * @author TechFinium
	 * @see ProjectImplementationPlanLearners
	 * @return a list of
	 *         {@link haj.com.entity.ProjectImplementationPlanLearners}
	 * @throws Exception
	 *             the exception
	 */
	public List<ProjectImplementationPlanLearners> allProjectImplementationPlanLearners() throws Exception {
		return dao.allProjectImplementationPlanLearners();
	}

	public Integer countAllProjectImplementationPlanLearners() throws Exception {
		return dao.countAllProjectImplementationPlanLearners();
	}

	/**
	 * Create or update ProjectImplementationPlanLearners.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ProjectImplementationPlanLearners
	 */
	public void create(ProjectImplementationPlanLearners entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update ProjectImplementationPlanLearners.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ProjectImplementationPlanLearners
	 */
	public void update(ProjectImplementationPlanLearners entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete ProjectImplementationPlanLearners.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ProjectImplementationPlanLearners
	 */
	public void delete(ProjectImplementationPlanLearners entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.ProjectImplementationPlanLearners}
	 * @throws Exception
	 *             the exception
	 * @see ProjectImplementationPlanLearners
	 */
	public ProjectImplementationPlanLearners findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find ProjectImplementationPlanLearners by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of
	 *         {@link haj.com.entity.ProjectImplementationPlanLearners}
	 * @throws Exception
	 *             the exception
	 * @see ProjectImplementationPlanLearners
	 */
	public List<ProjectImplementationPlanLearners> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load ProjectImplementationPlanLearners
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of
	 *         {@link haj.com.entity.ProjectImplementationPlanLearners}
	 * @throws Exception
	 *             the exception
	 */
	public List<ProjectImplementationPlanLearners> allProjectImplementationPlanLearners(int first, int pageSize) throws Exception {
		return dao.allProjectImplementationPlanLearners(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of ProjectImplementationPlanLearners for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the ProjectImplementationPlanLearners
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(ProjectImplementationPlanLearners.class);
	}

	/**
	 * Lazy load ProjectImplementationPlanLearners with pagination, filter,
	 * sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            ProjectImplementationPlanLearners class
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
	 * @return a list of
	 *         {@link haj.com.entity.ProjectImplementationPlanLearners}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlanLearners> allProjectImplementationPlanLearners(Class<ProjectImplementationPlanLearners> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<ProjectImplementationPlanLearners>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
	}

	/**
	 * Get count of ProjectImplementationPlanLearners for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            ProjectImplementationPlanLearners class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the ProjectImplementationPlanLearners entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<ProjectImplementationPlanLearners> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	public List<ProjectImplementationPlanLearners> findNextOpenLinkByPipId(Long pipId) throws Exception {
		return dao.findNextOpenLinkByPipId(pipId);
	}
	
	public Integer countByCompanyLearnerAndPip(Long companyLearnerId, Long pipId) throws Exception {
		return dao.countByCompanyLearnerAndPip(companyLearnerId, pipId);
	}
	
	public ProjectImplementationPlanLearners findByCompanyLearnerAndPip(Long companyLearnerId, Long pipId) throws Exception {
		return dao.findByCompanyLearnerAndPip(companyLearnerId, pipId);
	}
	
	public List<ProjectImplementationPlanLearners> findAllEntriesLinkedToCompanyByWsp(Company company) throws Exception {
		return populateProjectImplementationPlanLearnersInfoForCheck(dao.findAllEntriesLinkedToCompanyByWsp(company.getId()));
	}
	
	public List<ProjectImplementationPlanLearners> populateProjectImplementationPlanLearnersInfoForCheck(List<ProjectImplementationPlanLearners> entityList) throws Exception{
		for (ProjectImplementationPlanLearners entity : entityList) {
			populateProjectImplementationPlanLearnersInfoForCheck(entity);
		}
		return entityList;
	}
	
	public ProjectImplementationPlanLearners populateProjectImplementationPlanLearnersInfoForCheck(ProjectImplementationPlanLearners entity) throws Exception {
		// populate intervention type
		if (entity.getInterventionTypeLink() != null && entity.getInterventionTypeLink().getId() != null) {
			if (interventionTypeService == null) {
				interventionTypeService = new InterventionTypeService();
			}
			entity.setInterventionTypeLink(interventionTypeService.findByKey(entity.getInterventionTypeLink().getId()));
		}
		// populate Company learner
		if (entity.getCompanyLearners() != null && entity.getCompanyLearners().getId() != null) {
			if (companyLearnersService == null) {
				companyLearnersService = new CompanyLearnersService();
			}
			entity.setCompanyLearners(companyLearnersService.findByKey(entity.getCompanyLearners().getId()));
		}
		return entity;
	}
	
	public void linkCompanyLearnerToOpenPipLink(CompanyLearners companyLearners, ProjectImplementationPlan pip) throws Exception {
		// check if learner not already assigned
		if (countByCompanyLearnerAndPip(companyLearners.getId(), pip.getId()) == 0) {
			// check to see if there is an open slot. 
			List<ProjectImplementationPlanLearners> nextOpenList = findNextOpenLinkByPipId(pip.getId());
			if (!nextOpenList.isEmpty()) {
				List<IDataEntity> updateList = new ArrayList<>();
				// update link to learner
				for (ProjectImplementationPlanLearners pipLearnerLinkOpen : nextOpenList) {
					pipLearnerLinkOpen.setCompanyLearners(companyLearners);
					pipLearnerLinkOpen.setNextTranchPayment(TrancheEnum.TRANCHE_THREE);
					updateList.add(pipLearnerLinkOpen);
					break;
				}
				if (!updateList.isEmpty()) {
					dao.createBatch(updateList);
				}
			}
		}
	}

	/*
	 * This should only ever be ran once when deployed live. Never run again
	 * otherwise data will be broken.
	 */
	public void onceOffDataGeneration() throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					for (String email : GenericUtility.supportEmails()) {
						GenericUtility.sendMail(email, "Start: Generate PIP Learner Link", "Process Started On Generating Learner PIP Link On: " + HAJConstants.PL_LINK + ".", null);
					}
					// delete all existing entries
					if (countAllProjectImplementationPlanLearners() != 0) {
						List<IDataEntity> deleteList = new ArrayList<>();
						deleteList.addAll(allProjectImplementationPlanLearners());
						if (!deleteList.isEmpty()) {
							dao.deleteBatch(deleteList);
						}
					}
					if (projectImplementationPlanService == null) {
						projectImplementationPlanService = new ProjectImplementationPlanService();
					}
					List<ProjectImplementationPlan> allPip = projectImplementationPlanService.allProjectImplementationPlan();
					if (!allPip.isEmpty()) {
						List<IDataEntity> createList = new ArrayList<>();
						for (ProjectImplementationPlan pip : allPip) {
							int learnerNumber = 1;
							int totalLearners = 0;
							// dga.getMaxPossibleLearners() + dga.getCoFundingLearnersAwarded()
							if (pip != null && pip.getFullyFundedLearnerAwarded() != null) {
								totalLearners = totalLearners + pip.getFullyFundedLearnerAwarded();
							}
							if (pip != null && pip.getCoFundingLearnersAwarded() != null) {
								totalLearners = totalLearners + pip.getCoFundingLearnersAwarded();
							}	
							for (int i = 1; i <= totalLearners; i++) {
								ProjectImplementationPlanLearners link = new ProjectImplementationPlanLearners();
								link.setAllPaymentsCompleted(false);
								link.setNextTranchPayment(TrancheEnum.TRANCHE_TWO);
								link.setProjectImplementationPlan(pip);
								if (pip.getDgAllocation() != null && pip.getDgAllocation().getId() != null) {
									link.setDgAllocation(pip.getDgAllocation());	
								}
								if (pip.getWsp() != null && pip.getWsp().getId() != null) {
									link.setWsp(pip.getWsp());
								}
								if (pip.getInterventionType() != null && pip.getInterventionType().getId() != null) {
									link.setInterventionTypeLink(pip.getInterventionType());
								}
								if (pip.getActiveContracts() != null && pip.getActiveContracts().getId() != null) {
									link.setActiveContracts(pip.getActiveContracts());
								}
								link.setLearnerNumber(learnerNumber);
								createList.add(link);
								learnerNumber++;
							}	
						}
						if (!createList.isEmpty()) {
							dao.createBatch(createList, 2000);
						}
					}
					for (String email : GenericUtility.supportEmails()) {
						GenericUtility.sendMail(email, "End: Generate PIP Learner Link", "Process Completed On Generating learner PIP Link On: " + HAJConstants.PL_LINK + ".", null);
					}
				} catch (Exception e) {
					for (String email : GenericUtility.supportEmails()) {
						GenericUtility.sendMail(email, "Error: Generate PIP Learner Link", "Process Completed with Errors on site: " + HAJConstants.PL_LINK + ". Error Message: " + e.getMessage(), null);
					}
				}
			}
		}).start();
	}
	
	public void comapnyLearnerLinkOnceOff() throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					for (String email : GenericUtility.supportEmails()) {
						GenericUtility.sendMail(email, "Start: Company Learner PIP Auto Link", "Process Started Company Learner PIP Auto Link On: " + HAJConstants.PL_LINK + ".", null);
					}
					if (companyLearnersService == null) {
						companyLearnersService = new CompanyLearnersService();
					}
					List<CompanyLearners> learnersLinkedToPipList = companyLearnersService.findLearnersByStatusAndPipAssignedImport(LearnerStatusEnum.getLearnerStatusForMerge());
					if (!learnersLinkedToPipList.isEmpty()) {
						for (CompanyLearners companyLearners : learnersLinkedToPipList) {
							linkCompanyLearnerToOpenPipLink(companyLearners, companyLearners.getProjectImplementationPlan());
						}
					}
					for (String email : GenericUtility.supportEmails()) {
						GenericUtility.sendMail(email, "End: Company Learner PIP Auto Link", "Process Completed Company Learner PIP Auto Link On: " + HAJConstants.PL_LINK + ".", null);
					}
				} catch (Exception e) {
					for (String email : GenericUtility.supportEmails()) {
						GenericUtility.sendMail(email, "Error: Company Learner PIP Auto Link", "Process Completed with Errors on site: " + HAJConstants.PL_LINK + ". Error Message: " + e.getMessage(), null);
					}
				}
			}
		}).start();
	}
	
}