package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.WorkplaceMonitoringLearnerPaymentsDAO;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.ProjectImplementationPlanLearners;
import haj.com.entity.Users;
import haj.com.entity.WorkplaceMonitoringLearnerPayments;
import haj.com.entity.WorkplaceMonitoringMitigationPlan;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompetenceEnum;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.TradeTestTypeEnum;
import haj.com.entity.enums.TrancheEnum;
import haj.com.entity.enums.TrancheRuleEnum;
import haj.com.entity.lookup.DesignatedTradeLevel;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.DesignatedTradeLevelService;

public class WorkplaceMonitoringLearnerPaymentsService extends AbstractService {
	
	/** The dao. */
	private WorkplaceMonitoringLearnerPaymentsDAO dao = new WorkplaceMonitoringLearnerPaymentsDAO();
	
	private CompanyLearnersService companyLearnersService = null;
	private CompanyLearnersTradeTestService companyLearnersTradeTestService = null;
	private SummativeAssessmentReportService summativeAssessmentReportService = null;


	/**
	 * Get all WorkplaceMonitoringLearnerPayments
 	 * @author TechFinium 
 	 * @see   WorkplaceMonitoringLearnerPayments
 	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringLearnerPayments}
	 * @throws Exception the exception
 	 */
	public List<WorkplaceMonitoringLearnerPayments> allWorkplaceMonitoringLearnerPayments() throws Exception {
	  	return dao.allWorkplaceMonitoringLearnerPayments();
	}


	/**
	 * Create or update WorkplaceMonitoringLearnerPayments.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     WorkplaceMonitoringLearnerPayments
	 */
	public void create(WorkplaceMonitoringLearnerPayments entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}
	
	public void updateInfo(WorkplaceMonitoringLearnerPayments entity, Users sessionUser) throws Exception{
		entity.setLastActionDate(getSynchronizedDate());
		entity.setLastActionUser(sessionUser);
		update(entity);
	}


	/**
	 * Update  WorkplaceMonitoringLearnerPayments.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringLearnerPayments
	 */
	public void update(WorkplaceMonitoringLearnerPayments entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  WorkplaceMonitoringLearnerPayments.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringLearnerPayments
	 */
	public void delete(WorkplaceMonitoringLearnerPayments entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WorkplaceMonitoringLearnerPayments}
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringLearnerPayments
	 */
	public WorkplaceMonitoringLearnerPayments findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find WorkplaceMonitoringLearnerPayments by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringLearnerPayments}
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringLearnerPayments
	 */
	public List<WorkplaceMonitoringLearnerPayments> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load WorkplaceMonitoringLearnerPayments
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringLearnerPayments}
	 * @throws Exception the exception
	 */
	public List<WorkplaceMonitoringLearnerPayments> allWorkplaceMonitoringLearnerPayments(int first, int pageSize) throws Exception {
		return dao.allWorkplaceMonitoringLearnerPayments(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of WorkplaceMonitoringLearnerPayments for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the WorkplaceMonitoringLearnerPayments
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(WorkplaceMonitoringLearnerPayments.class);
	}
	
    /**
     * Lazy load WorkplaceMonitoringLearnerPayments with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 WorkplaceMonitoringLearnerPayments class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.WorkplaceMonitoringLearnerPayments} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringLearnerPayments> allWorkplaceMonitoringLearnerPayments(Class<WorkplaceMonitoringLearnerPayments> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<WorkplaceMonitoringLearnerPayments>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of WorkplaceMonitoringLearnerPayments for lazy load with filters
     * @author TechFinium 
     * @param entity WorkplaceMonitoringLearnerPayments class
     * @param filters the filters
     * @return Number of rows in the WorkplaceMonitoringLearnerPayments entity
     * @throws Exception the exception     
     */	
	public int count(Class<WorkplaceMonitoringLearnerPayments> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringLearnerPayments> allWorkplaceMonitoringLearnerPaymentsByTargetClassAndKey(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String targetClass, Long targetKey) throws Exception {
		String hql = "select o from WorkplaceMonitoringLearnerPayments o where o.targetClass = :targetClass and o.targetKey = :targetKey";
		filters.put("targetClass", targetClass);
		filters.put("targetKey", targetKey);
		return populateAdditionalInformationList((List<WorkplaceMonitoringLearnerPayments>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql));
	}
	
	public int countAllWorkplaceMonitoringLearnerPaymentsByTargetClassAndKey(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WorkplaceMonitoringLearnerPayments o where o.targetClass = :targetClass and o.targetKey = :targetKey";
		return dao.countWhere(filters, hql);
	}
	
	private List<WorkplaceMonitoringLearnerPayments> populateAdditionalInformationList(List<WorkplaceMonitoringLearnerPayments> list) throws Exception {
		for (WorkplaceMonitoringLearnerPayments entity : list) {
			populateAdditionalInformation(entity);
		}
		return list;
	}

	private WorkplaceMonitoringLearnerPayments populateAdditionalInformation(WorkplaceMonitoringLearnerPayments entity) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
		}
		// resolve Learner Info. Basic just for the user information
		if (entity.getProjectImplementationPlanLearners() != null && entity.getProjectImplementationPlanLearners().getCompanyLearners() != null && entity.getProjectImplementationPlanLearners().getCompanyLearners().getId() != null) {
			if (companyLearnersService == null) {
				companyLearnersService = new CompanyLearnersService();
			}
			entity.getProjectImplementationPlanLearners().setCompanyLearners(companyLearnersService.findByKey(entity.getProjectImplementationPlanLearners().getCompanyLearners().getId()));
		}
		return entity;
	}
	
	public List<WorkplaceMonitoringLearnerPayments> findByTargetClassAndKey(String targetClass, Long targetKey) throws Exception {
		return dao.findByTargetClassAndKey(targetClass, targetKey);
	}
	
	public List<WorkplaceMonitoringLearnerPayments> findByTargetClassAndKeyWithDataResolved(String targetClass, Long targetKey) throws Exception {
		return populateAdditionalInformationForPayments(dao.findByTargetClassAndKey(targetClass, targetKey));
	}
	
	private List<WorkplaceMonitoringLearnerPayments> populateAdditionalInformationForPayments(List<WorkplaceMonitoringLearnerPayments> list) throws Exception {
		for (WorkplaceMonitoringLearnerPayments entity : list) {
			populateAdditionalInformationForPayments(entity);
		}
		return list;
	}

	private WorkplaceMonitoringLearnerPayments populateAdditionalInformationForPayments(WorkplaceMonitoringLearnerPayments entity) throws Exception {
		// resolve Learner Info. Basic just for the user information
		if (entity.getProjectImplementationPlanLearners() != null && entity.getProjectImplementationPlanLearners().getCompanyLearners() != null && entity.getProjectImplementationPlanLearners().getCompanyLearners().getId() != null) {
			if (companyLearnersService == null) {
				companyLearnersService = new CompanyLearnersService();
			}
			entity.getProjectImplementationPlanLearners().setCompanyLearners(companyLearnersService.findByKey(entity.getProjectImplementationPlanLearners().getCompanyLearners().getId()));
		}
		return entity;
	}
	
	public void reRunChecksForPayments(String targetClass, Long targetKey) throws Exception {
		List<WorkplaceMonitoringLearnerPayments> wpm = findByTargetClassAndKey(targetClass, targetKey);
		for (WorkplaceMonitoringLearnerPayments entry : wpm) {
			entry.setFinalTranchePaid(false);
			entry.setRanCheck(false);
			entry.setPaymentAvalible(false);
			entry.setReasonPaymentNotAvalaible("");
			entry.setPayTranchPayment(false);
			runCheck(entry);
		}
	}
	
	public List<WorkplaceMonitoringLearnerPayments> createNewWorkplaceMonitoringLearnerPayments(Company company, String targetClass, Long targetKey, Users createUser) throws Exception {
		List<ProjectImplementationPlanLearners> entriesLinkedToCompany = ProjectImplementationPlanLearnersService.instance().findAllEntriesLinkedToCompanyByWsp(company);
		if (!entriesLinkedToCompany.isEmpty()) {
			List<WorkplaceMonitoringLearnerPayments> returnList = new ArrayList<>();
			for (ProjectImplementationPlanLearners learnerLink : entriesLinkedToCompany) {
				// locate all Learner PIP linked to company by WSP
				WorkplaceMonitoringLearnerPayments newEntry = new WorkplaceMonitoringLearnerPayments();
				newEntry.setTargetClass(targetClass);
				newEntry.setTargetKey(targetKey);
				if (createUser != null && createUser.getId() != null) {
					newEntry.setLastActionUser(createUser);
				}
				newEntry.setProjectImplementationPlanLearners(learnerLink);
				newEntry.setLastActionDate(getSynchronizedDate());
				// default values
				newEntry.setFinalTranchePaid(false);
				newEntry.setRanCheck(false);
				newEntry.setPaymentAvalible(false);
				newEntry.setReasonPaymentNotAvalaible("");
				newEntry.setPayTranchPayment(false);
				runCheck(newEntry);
				returnList.add(newEntry);
			}
			return returnList;
		} else {
			return new ArrayList<>();
		}
	}
	
	// very long checks for payments
	public void runCheck(WorkplaceMonitoringLearnerPayments entity) throws Exception{
		StringBuilder reason = new StringBuilder();
		Boolean paymentAvalaible = true;
		// fail safe
		if (entity.getProjectImplementationPlanLearners() == null) {
			reason.append("Unable to locate link to learner and PIP. Contact support!");
			paymentAvalaible = false;
		} else {
			
			// check if learner registered
			if (entity.getProjectImplementationPlanLearners().getCompanyLearners() == null) {
				reason.append("Learner not registered.");
				paymentAvalaible = false;
			} else {				
				// resolve data
				ProjectImplementationPlanLearnersService.instance().populateProjectImplementationPlanLearnersInfoForCheck(entity.getProjectImplementationPlanLearners());
				// check if final payments already paid
				if (entity.getProjectImplementationPlanLearners().getAllPaymentsCompleted() != null && entity.getProjectImplementationPlanLearners().getAllPaymentsCompleted()) {
					reason.append("Final tranche was already paid.");
					entity.setFinalTranchePaid(true);
					paymentAvalaible = false;
				} else {
					
					// ADD TRANCH Logic checks
					if (entity.getProjectImplementationPlanLearners().getNextTranchPayment() == null) {
						reason.append("Unable to locate next tranche payment. Contact Support!.");
						paymentAvalaible = false;
					} else {
						
						// check if its a 3 Tranche payment or 4 Tranche payment
						if (entity.getProjectImplementationPlanLearners().getProjectImplementationPlan().getInterventionType() == null ||
								entity.getProjectImplementationPlanLearners().getProjectImplementationPlan().getInterventionType().getTranchintervals() == null) {
							reason.append("Unable to locate next intervention type / number of tranches. Contact Support!.");
							paymentAvalaible = false;
						}else {
							switch (entity.getProjectImplementationPlanLearners().getProjectImplementationPlan().getInterventionType().getTranchintervals()) {
							case 3:
								// 3 tranche payments
								if (entity.getProjectImplementationPlanLearners().getNextTranchPayment() == TrancheEnum.TRANCHE_THREE) {
									// check if learner status completed / qualification obtained
									if (checkLearnerStatus(LearnerStatusEnum.getLearnerCompletedStatusList(), entity.getProjectImplementationPlanLearners().getCompanyLearners().getLearnerStatus())) {
										reason.append("Payment Available");
										paymentAvalaible = true;
									} else {
										reason.append("Rules not met for final tranche three payment.");
										paymentAvalaible = false;
									}
								} else {
									reason.append("Not within tranche rules.");
									paymentAvalaible = false;
								}
								break;
							case 4:
								// 4 Tranche payments
								if (entity.getProjectImplementationPlanLearners().getNextTranchPayment() == TrancheEnum.TRANCHE_THREE) {
									if (entity.getProjectImplementationPlanLearners().getProjectImplementationPlan().getInterventionType().getQualificationTypeSelection() == null) {
										reason.append("Qualification Type Selection configured against intervention. Contact Support!");
										paymentAvalaible = false;
										break;
									} else {
										try {
											if (companyLearnersService == null) {
												companyLearnersService = new CompanyLearnersService();
											}
											// resolve qualification Data for learner assigned
											companyLearnersService.resolveQualificationData(entity.getProjectImplementationPlanLearners().getCompanyLearners());
										} catch (Exception e) {
											reason.append("Error When Trying To Resolve Qualification Information For Learner. Contact Support!. Error message: " + e.getMessage() + ".");
											paymentAvalaible = false;
											break;
										}
										
										switch (entity.getProjectImplementationPlanLearners().getProjectImplementationPlan().getInterventionType().getQualificationTypeSelection()) {
										case Qualification:
											// check to ensure config correct
											if (entity.getProjectImplementationPlanLearners().getCompanyLearners().getQualification() == null) {
												reason.append("Unable to locate qualification as currently configured to search qualification on intervention. Contact Support!.");
												paymentAvalaible = false;
											} else {
												// check if designated trade
												boolean designatedTrade = (DesignatedTradeLevelService.instance().countEntiresByQualificationId(entity.getProjectImplementationPlanLearners().getCompanyLearners().getQualification()) != 0);
												if (designatedTrade) {
													// check how many levels
													List<DesignatedTradeLevel> list = DesignatedTradeLevelService.instance().findByQualificationIdOrderByLevel(entity.getProjectImplementationPlanLearners().getCompanyLearners().getQualification());
													Integer numberOfLevels = list.size();
													if (numberOfLevels == 0) {
														reason.append("Identified as designated trade however error on locating how many levels assigned. Contact Support!.");
														paymentAvalaible = false;
														break;
													} else if (numberOfLevels == 1) {
														// auto pass since only one level to complete
														reason.append("Payment Available");
														paymentAvalaible = true;
													} else {
														if (companyLearnersTradeTestService == null) {
															companyLearnersTradeTestService = new CompanyLearnersTradeTestService();
														}
														int numberOfCompletedLevels = 0;
														for (DesignatedTradeLevel designatedTradeLevel : list) {
															boolean passed = (
																	companyLearnersTradeTestService.countTradeTestByCompanyLearnerDesignatedTradeAndCompleted(TradeTestTypeEnum.TRADE_TEST, entity.getProjectImplementationPlanLearners().getCompanyLearners(), designatedTradeLevel, CompetenceEnum.Competent, ApprovalEnum.Completed) != 0
																	);
															if (passed) {
																numberOfCompletedLevels++;
															}
														}
														double percentageOfCompletion = 0.0d;
														if (numberOfCompletedLevels != 0) {
															percentageOfCompletion = numberOfCompletedLevels / numberOfLevels;
														}
														if (numberOfLevels == 3 && percentageOfCompletion >= 33.00) {
															reason.append("Payment Available");
															paymentAvalaible = true;
														} else if (percentageOfCompletion >= 50.00) {
															reason.append("Payment Available");
															paymentAvalaible = true;
														} else {
															reason.append("Rules not met for Tranche Three Payment.");
															paymentAvalaible = false;
														}
													}
												} else {
													// check for learning program
													if (entity.getProjectImplementationPlanLearners().getCompanyLearners().getLeaningProgramme() != null) {
														if (entity.getProjectImplementationPlanLearners().getCompanyLearners().getLeaningProgramme().getEtqaid() != null &&
																entity.getProjectImplementationPlanLearners().getCompanyLearners().getLeaningProgramme().getEtqaid().trim().equals(HAJConstants.HOSTING_MERSETA_ETQA)) {
															if (checkLearnerCreditsAchived(entity.getProjectImplementationPlanLearners().getCompanyLearners(), 80)) {
																reason.append("Payment Available");
																paymentAvalaible = true;
															} else {
																reason.append("Rules not met for Tranche Three Payment.");
																paymentAvalaible = false;
															}
														}else {
															// auto pass since not merseta learning program
															reason.append("Payment Available");
															paymentAvalaible = true;
														}
													} else {
														if (entity.getProjectImplementationPlanLearners().getCompanyLearners().getQualification().getEtqaid() != null &&
																entity.getProjectImplementationPlanLearners().getCompanyLearners().getQualification().getEtqaid().trim().equals(HAJConstants.HOSTING_MERSETA_ETQA)) {
															if (checkLearnerCreditsAchived(entity.getProjectImplementationPlanLearners().getCompanyLearners(), 80)) {
																reason.append("Payment Available");
																paymentAvalaible = true;
															} else {
																reason.append("Rules not met for tranche three payment.");
																paymentAvalaible = false;
															}
														}else {
															// auto pass since not merseta qualification
															reason.append("Payment Available");
															paymentAvalaible = true;
														}
													}
												}
											}
											break;
										case SkillsSet:
											// check to ensure config correct
											if (entity.getProjectImplementationPlanLearners().getCompanyLearners().getSkillsSet() == null) {
												reason.append("Unable to locate skills set as currently configured to search skills set on intervention. Contact Support!.");
												paymentAvalaible = false;
											} else {
												// check if MerSETA skills set
												if (entity.getProjectImplementationPlanLearners().getCompanyLearners().getSkillsSet().getEtqa() != null &&
														entity.getProjectImplementationPlanLearners().getCompanyLearners().getSkillsSet().getEtqa().getCode() != null &&
														entity.getProjectImplementationPlanLearners().getCompanyLearners().getSkillsSet().getEtqa().getCode().trim().equals(HAJConstants.HOSTING_MERSETA_ETQA)) {
													if (checkLearnerCreditsAchived(entity.getProjectImplementationPlanLearners().getCompanyLearners(), 80)) {
														reason.append("Payment Available");
														paymentAvalaible = true;
													} else {
														reason.append("Rules not met for tranche three payment.");
														paymentAvalaible = false;
													}
												}else {
													// auto pass since not merseta learnership
													reason.append("Payment Available");
													paymentAvalaible = true;
												}
											}
											break;
										case SkillsProgram:
											// check to ensure config correct
											if (entity.getProjectImplementationPlanLearners().getCompanyLearners().getSkillsProgram() == null) {
												reason.append("Unable to locate skills programme as currently configured to search skills programme on intervention. Contact Support!.");
												paymentAvalaible = false;
											} else {
												// check if MerSETA skills program
												if (entity.getProjectImplementationPlanLearners().getCompanyLearners().getSkillsProgram().getEtqa() != null &&
														entity.getProjectImplementationPlanLearners().getCompanyLearners().getSkillsProgram().getEtqa().getCode() != null &&
														entity.getProjectImplementationPlanLearners().getCompanyLearners().getSkillsProgram().getEtqa().getCode().trim().equals(HAJConstants.HOSTING_MERSETA_ETQA)) {
													if (checkLearnerCreditsAchived(entity.getProjectImplementationPlanLearners().getCompanyLearners(), 80)) {
														reason.append("Payment Available");
														paymentAvalaible = true;
													} else {
														reason.append("Rules not met for tranche three payment.");
														paymentAvalaible = false;
													}
												}else {
													// auto pass since not merseta learnership
													reason.append("Payment Available");
													paymentAvalaible = true;
												}
											}
											break;
										case Learnership:
											// check to ensure config correct
											if (entity.getProjectImplementationPlanLearners().getCompanyLearners().getLearnership() == null) {
												reason.append("Unable to locate learnership as currently configured to search learnership on intervention. Contact Support!.");
												paymentAvalaible = false;
											} else {
												// check if merseta learnership
												if (entity.getProjectImplementationPlanLearners().getCompanyLearners().getLearnership().getEtqa() != null &&
														entity.getProjectImplementationPlanLearners().getCompanyLearners().getLearnership().getEtqa().getCode() != null &&
														entity.getProjectImplementationPlanLearners().getCompanyLearners().getLearnership().getEtqa().getCode().trim().equals(HAJConstants.HOSTING_MERSETA_ETQA)) {
													if (checkLearnerCreditsAchived(entity.getProjectImplementationPlanLearners().getCompanyLearners(), 80)) {
														reason.append("Payment Available");
														paymentAvalaible = true;
													} else {
														reason.append("Rules not met for tranche three payment.");
														paymentAvalaible = false;
													}
												}else {
													// auto pass since not merseta learnership
													reason.append("Payment Available");
													paymentAvalaible = true;
												}
											}
											break;
										case NonCreditBearingIntervationTitle:
											// auto pass since progress not captured
											reason.append("Payment Available");
											paymentAvalaible = true;
											break;
										case UnitStandards:
											// auto pass since progress not captured
											reason.append("Payment Available");
											paymentAvalaible = true;
											break;
										default:
											// auto fail if not able to link
											reason.append("Unable to identify Qualification Type Selection. Contact Support!");
											paymentAvalaible = false;
											break;
										}
									}
								} else if (entity.getProjectImplementationPlanLearners().getNextTranchPayment() == TrancheEnum.TRANCHE_FOUR) {
									// check if learner status completed / qualification obtained
									if (checkLearnerStatus(LearnerStatusEnum.getLearnerCompletedStatusList(), entity.getProjectImplementationPlanLearners().getCompanyLearners().getLearnerStatus())) {
										reason.append("Payment Avalible");
										paymentAvalaible = true;
									} else {
										reason.append("Rules not met for tranche four payment.");
										paymentAvalaible = false;
									}
								} else {
									reason.append("Not within tranche rules.");
									paymentAvalaible = false;
								}
								break;
							default:
								reason.append("Number of total tranches does not fall within 3 or 4. Contact Support!.");
								paymentAvalaible = false;
								break;
							}
						}
					}
				}
			}
		}
		// check ran
		entity.setRanCheck(true);
		// reason populated above
		entity.setReasonPaymentNotAvalaible(reason.toString().trim());
		// Check if the payment can be done
		entity.setPaymentAvalible(paymentAvalaible);
		// this is for if CLO will pay
		entity.setPayTranchPayment(false);
		// update if it already exists 
		if (entity.getId() != null) {
			update(entity);
		}
	}
	
	public boolean checkLearnerStatus(List<LearnerStatusEnum> list, LearnerStatusEnum entry) throws Exception{
		if (list == null || list.isEmpty() || entry == null) {
			return false;
		} else {
			for (LearnerStatusEnum learnerStatusEnum : list) {
				if (entry.equals(learnerStatusEnum)) {
					return true;
				}
			}
			return false;
		}
	}
	
	public boolean checkLearnerCreditsAchived(CompanyLearners companyLearner, Integer numberOfCredits) throws Exception {
		if (summativeAssessmentReportService == null) {
			summativeAssessmentReportService = new SummativeAssessmentReportService();
		}
		int amountAchived = summativeAssessmentReportService.checkCompanyLearnersCreditProgress(companyLearner);
		// add logic to check learner achievement
		if (numberOfCredits == null) {
			return false;
		}
		if (amountAchived >= numberOfCredits) {
			return true;
		}else {
			return false;
		}
	}
	
}
