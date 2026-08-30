package haj.com.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;

import haj.com.constants.HAJConstants;
import haj.com.dao.UsersDAO;
import haj.com.entity.ActiveContractDetail;
import haj.com.entity.ActiveContractExtensionRequest;
import haj.com.entity.ActiveContractTerminationRequest;
import haj.com.entity.ActiveContracts;
import haj.com.entity.AdministrationOfAQP;
import haj.com.entity.AssessorModExtensionOfScope;
import haj.com.entity.AssessorModReRegistration;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.BankingDetails;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.CompanyLearnersChange;
import haj.com.entity.CompanyLearnersDetailsChange;
import haj.com.entity.CompanyLearnersLostTime;
import haj.com.entity.CompanyLearnersTermination;
import haj.com.entity.CompanyLearnersTradeTest;
import haj.com.entity.CompanyLearnersTransfer;
import haj.com.entity.CompanyUsers;
import haj.com.entity.CompletionLetter;
import haj.com.entity.CoursewareDistibution;
import haj.com.entity.DgAllocationParent;
import haj.com.entity.DgContractingBulkEntry;
import haj.com.entity.DgVerification;
import haj.com.entity.ExtensionRequest;
import haj.com.entity.GpGrantBatchList;
import haj.com.entity.InterSetaTransfer;
import haj.com.entity.LearnershipDevelopmentRegistration;
import haj.com.entity.MgVerification;
import haj.com.entity.NonSetaCompany;
import haj.com.entity.NonSetaQualificationsCompletion;
import haj.com.entity.ProcessRoles;
import haj.com.entity.QdfCompany;
import haj.com.entity.QualificationsCurriculumDevelopment;
import haj.com.entity.SDFCompany;
import haj.com.entity.SDPExtensionOfScope;
import haj.com.entity.SDPReAccreditation;
import haj.com.entity.ScheduledEvent;
import haj.com.entity.SdpCompanyActions;
import haj.com.entity.SiteVisit;
import haj.com.entity.SiteVisitReport;
import haj.com.entity.Sites;
import haj.com.entity.SitesSme;
import haj.com.entity.SkillsRegistration;
import haj.com.entity.TrainingComittee;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingProviderMonitoring;
import haj.com.entity.TrainingProviderVerfication;
import haj.com.entity.TrainingSite;
import haj.com.entity.Users;
import haj.com.entity.WorkPlaceApproval;
import haj.com.entity.WorkPlaceApprovalMentors;
import haj.com.entity.WorkplaceMonitoring;
import haj.com.entity.WorkplaceMonitoringSiteVisit;
import haj.com.entity.Wsp;
import haj.com.entity.WspChecklist;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.LearnerTransferTypeEnum;
import haj.com.entity.enums.TradeTestTypeEnum;
import haj.com.entity.enums.TransferRequestTypeEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.enums.WspTypeEnum;
import haj.com.entity.lookup.RegionTown;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.RegionTownService;

// TODO: Auto-generated Javadoc
/**
 * The Class AddressService.
 */
public class FixTaskDataService extends AbstractService {

	/** The dao. */
	private UsersDAO dao = new UsersDAO();
	private static FixTaskDataService findUsersService = null;
	private DgVerificationService dgVerificationService = new DgVerificationService();
	private UsersService usersService = new UsersService();
	private SDFCompanyService sdfCompanyService = new SDFCompanyService();
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	private SDPCompanyService sdpCompanyService = new SDPCompanyService();
	private TrainingProviderApplicationService trainingProviderApplicationService = null;

	/**
	 * Instance.
	 *
	 * @return the tasks service
	 */
	public static synchronized FixTaskDataService instance() {
		if (findUsersService == null) {
			findUsersService = new FixTaskDataService();
		}
		return findUsersService;
	}

	public void getUsers(Long targetKey, String targetClass, List<Users> users, ConfigDocProcessEnum configDocProcessEnum, boolean firstInProcess, ProcessRoles processRoles, Users createUser) throws Exception {
		
		System.out.println("targetKey:"+targetKey);
		System.out.println("targetClass:"+targetClass);
		System.out.println("Users.class.getName():"+Users.class.getName());
		System.out.println("Company.class.getName():"+Company.class.getName());
		System.out.println("configDocProcessEnum:"+configDocProcessEnum);
		System.out.println("ConfigDocProcessEnum.LearnerReview:"+ConfigDocProcessEnum.LearnerReview);
		System.out.println("ConfigDocProcessEnum.TP:"+ConfigDocProcessEnum.TP);
		System.out.println("ConfigDocProcessEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL:"+ConfigDocProcessEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL);
		if (targetClass.equals(Users.class.getName())) {
			System.out.println("If 1");
			users.add(usersService.findByKey(targetKey));
		} else if (targetClass.equals(Company.class.getName())) {

			if (configDocProcessEnum == ConfigDocProcessEnum.LearnerReview) {
				System.out.println("If 2");
				users.addAll(companyUsersService.findUsersByCompanyType(targetKey, ConfigDocProcessEnum.TP));
			} else if (configDocProcessEnum == ConfigDocProcessEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL) {
				System.out.println("If 3");
				users.addAll(companyUsersService.findUsersByCompanyType(targetKey, ConfigDocProcessEnum.TP));
			} else {
				System.out.println("If 4");
				users.addAll(companyUsersService.findUsersByCompanyType(targetKey, configDocProcessEnum));
			}

		} else if (targetClass.equals(SDFCompany.class.getName())) {
			System.out.println("If 5:"+SDFCompany.class.getName());
			SDFCompany company = sdfCompanyService.findByKey(targetKey);
			if (company != null) users.add(company.getSdf());
		} else if (targetClass.equals(DgVerification.class.getName())) {
			System.out.println("If 6:"+DgVerification.class.getName());
			
			DgVerification company = dgVerificationService.findByKey(targetKey);			
			if (company != null) users.add(sdfCompanyService.findPrimarySDF(company.getWsp().getCompany()).getSdf());
		} else if (targetClass.equals(CompanyLearnersTransfer.class.getName())) {
			System.out.println("If 7:"+CompanyLearnersTransfer.class.getName());
			// CompanyLearnersTransfer companyLearners = (CompanyLearnersTransfer)
			// dao.getByClassAndKey(targetClass, targetKey);
			// work here
			CompanyLearnersTransfer companyLearnersTransfer = CompanyLearnersTransferService.instance().findByKey(targetKey);
			if (companyLearnersTransfer.getLearnerTransferApproval() == null) {
				System.out.println("If 8:"+TransferRequestTypeEnum.Learner);
				if (companyLearnersTransfer.getTransferRequestType() == TransferRequestTypeEnum.Learner) {
					System.out.println("If 9:"+companyLearnersTransfer.getCompanyLearners().getUser());
					users.add(companyLearnersTransfer.getCompanyLearners().getUser());
				} else if (companyLearnersTransfer.getTransferRequestType() == TransferRequestTypeEnum.Provider) {
					try {
						System.out.println("If 10:"+TransferRequestTypeEnum.Provider);
						System.out.println("companyLearnersTransfer.getTransferRequestType():"+companyLearnersTransfer.getTransferRequestType());
						if (companyLearnersTransfer.getCompanyLearners() != null && companyLearnersTransfer.getCompanyLearners().getTrainingSite() != null && companyLearnersTransfer.getCompanyLearners().getTrainingSite().getId() != null) {
							System.out.println("If 11:"+companyLearnersTransfer.getCompanyLearners().getTrainingSite().getId());
							users.addAll(sdpCompanyService.findAllSdpByCompanyIdAndTrainingSiteIdReturnUsers(companyLearnersTransfer.getCompanyLearners().getCompany().getId(), companyLearnersTransfer.getCompanyLearners().getTrainingSite().getId()));
						} else {
							System.out.println("If 12:"+companyLearnersTransfer.getCompanyLearners().getCompany().getId());
							users.addAll(sdpCompanyService.findAllSdpByCompanyIdReturnUsers(companyLearnersTransfer.getCompanyLearners().getCompany().getId()));
						}
					} catch (Exception e) {
					}
					if (users.isEmpty()) {
						// fall back: old version
						System.out.println("If 13:"+companyLearnersTransfer.getCompanyLearners().getCompany().getId());
						users.addAll(companyUsersService.findUsersByCompanyType(companyLearnersTransfer.getCompanyLearners().getCompany().getId(), ConfigDocProcessEnum.TP));
					}
				} else if (companyLearnersTransfer.getTransferRequestType() == TransferRequestTypeEnum.Workplace) {
					System.out.println("If 14:"+TransferRequestTypeEnum.Workplace);
					System.out.println("companyLearnersTransfer.getCompanyLearners().getCompany().getId():"+companyLearnersTransfer.getCompanyLearners().getCompany().getId());
					users.addAll(companyUsersService.findByCompanyNotProcess(companyLearnersTransfer.getCompanyLearners().getCompany().getId()));
				}
			} else {
				switch (companyLearnersTransfer.getLearnerTransferApproval()) {
				case NormalWorkflow:
					// sends task to the company should only send to MerSETA employees
					break;
				case WithCompany:
					// follows normal Work flow 
					if (companyLearnersTransfer.getLearnerTransferType() == LearnerTransferTypeEnum.ProviderTransfer) {
						// to SDP 
						System.out.println("If 15:"+LearnerTransferTypeEnum.ProviderTransfer);
						TrainingProviderApplication tpa = null;
						try {
							if (companyLearnersTransfer.getTransferTrainingProviderApplication() != null && companyLearnersTransfer.getTransferTrainingProviderApplication().getId() != null) {
								System.out.println("If 16:"+companyLearnersTransfer.getTransferTrainingProviderApplication().getId());
								if (trainingProviderApplicationService == null) {
									System.out.println("If 17:"+companyLearnersTransfer.getTransferTrainingProviderApplication().getId());
									trainingProviderApplicationService = new TrainingProviderApplicationService();
								}
								tpa = trainingProviderApplicationService.findByKey(companyLearnersTransfer.getTransferTrainingProviderApplication().getId());
							}
						} catch (Exception e) {
						}
						if (tpa != null) {
							try {
								System.out.println("If 18:"+tpa.getTrainingSite().getId());
								if (tpa.getTrainingSite() != null && tpa.getTrainingSite().getId() != null) {
									System.out.println("If 19:"+tpa.getTrainingSite().getId());
									System.out.println("companyLearnersTransfer.getTransferToCompany().getId():"+companyLearnersTransfer.getTransferToCompany().getId());
								    users.addAll(sdpCompanyService.findAllSdpByCompanyIdAndTrainingSiteIdReturnUsers(companyLearnersTransfer.getTransferToCompany().getId(), tpa.getTrainingSite().getId()));
								} else {
									System.out.println("If 20:"+companyLearnersTransfer.getTransferToCompany().getId());
								    users.addAll(sdpCompanyService.findAllSdpByCompanyIdReturnUsers(companyLearnersTransfer.getTransferToCompany().getId()));
								}
							} catch (Exception e) {
							}
							if (users.isEmpty()) {
								// fail safe: fall back to old method
								System.out.println("If 21:"+companyLearnersTransfer.getTransferToCompany().getId());
								users.addAll(companyUsersService.findUsersByCompanyType(companyLearnersTransfer.getTransferToCompany().getId(), ConfigDocProcessEnum.TP));
							}
						}else {
							System.out.println("If 22:"+companyLearnersTransfer.getTransferToCompany().getId());
							users.addAll(companyUsersService.findUsersByCompanyType(companyLearnersTransfer.getTransferToCompany().getId(), ConfigDocProcessEnum.TP));
						}
						
					} else if (companyLearnersTransfer.getLearnerTransferType() == LearnerTransferTypeEnum.WorkplaceTransfer) {
						// to Primary SDF
						System.out.println("If 23:");
						 users.add(sdfCompanyService.findPrimarySDF(companyLearnersTransfer.getTransferToCompany()).getSdf());
					}
					break;
				case WithInitiator:
					// sends task to the initiator of the application
					System.out.println("If 24:"+companyLearnersTransfer.getCreateUser());
					users.add(companyLearnersTransfer.getCreateUser());
					break;
				case WithLearner:
					System.out.println("If 25:"+companyLearnersTransfer.getLearnerUser());
					// sends task to the learner
					users.add(companyLearnersTransfer.getLearnerUser());
					break;
				default:
					break;
				}
				
//				old code not used
//				if (companyLearners.getLearnerTransferApproval() == LearnerTransferApproval.NormalWorkflow) {	
//					if (companyLearners.getTransferRequestType() == TransferRequestTypeEnum.Learner) users.add(companyLearners.getCompanyLearners().getUser());
//					else if (companyLearners.getTransferRequestType() == TransferRequestTypeEnum.Provider) users.addAll(companyUsersService.findUsersByCompanyType(companyLearners.getCompanyLearners().getCompany().getId(), ConfigDocProcessEnum.TP));
//					else if (companyLearners.getTransferRequestType() == TransferRequestTypeEnum.Workplace) users.addAll(companyUsersService.findByCompanyNotProcess(companyLearners.getCompanyLearners().getCompany().getId()));
//				} else if (companyLearners.getLearnerTransferApproval() == LearnerTransferApproval.WithLearner) {
//					users.add(companyLearners.getCompanyLearners().getUser());
//				} else if (companyLearners.getLearnerTransferApproval() == LearnerTransferApproval.WithCompany) {
//					if (companyLearners.getTransferRequestType() == TransferRequestTypeEnum.Provider ||  companyLearners.getTransferRequestType() == TransferRequestTypeEnum.Learner)  {
//						users.addAll(companyUsersService.findUsersByCompanyType(companyLearners.getCompanyLearners().getCompany().getId(), ConfigDocProcessEnum.TP));
//					} else if (companyLearners.getTransferRequestType() == TransferRequestTypeEnum.Workplace) {
//						users.add(SDFCompanyService.instance().findPrimarySDF(companyLearners.getTransferToCompany()).getSdf());
//						users.add(companyLearners.getCreateUser());
//					}	
//				}
			}
			
		} else if (targetClass.equals(CompanyLearnersLostTime.class.getName())) {
			// CompanyLearnersLostTime companyLearners = (CompanyLearnersLostTime)
			// dao.getByClassAndKey(targetClass, targetKey);
			System.out.println("If 26");
			CompanyLearnersLostTime companyLearners = CompanyLearnersLostTimeService.instance().findByKey(targetKey);
			if (companyLearners.getCompanyLearners().getCompany() != null) {
				try {
					System.out.println("If 27:"+companyLearners.getCompanyLearners().getCompany());
					if (companyLearners.getTrainingProviderApplication() != null && companyLearners.getTrainingProviderApplication().getTrainingSite() != null && companyLearners.getTrainingProviderApplication().getTrainingSite().getId() != null) {
						System.out.println("If 28:"+companyLearners.getTrainingProviderApplication().getTrainingSite().getId());
					    users.addAll(sdpCompanyService.findAllSdpByCompanyIdAndTrainingSiteIdReturnUsers(companyLearners.getCompanyLearners().getCompany().getId(), companyLearners.getTrainingProviderApplication().getTrainingSite().getId()));
					} else {
						System.out.println("If 29:"+companyLearners.getCompanyLearners().getCompany().getId());
					    users.addAll(sdpCompanyService.findAllSdpByCompanyIdReturnUsers(companyLearners.getCompanyLearners().getCompany().getId()));
					}
				} catch (Exception e) {
				}
				
				if (!users.isEmpty()) {
					System.out.println("If 30:"+companyLearners.getCompanyLearners().getCompany().getId());
					users.addAll(companyUsersService.findUsersByCompanyType(companyLearners.getCompanyLearners().getCompany().getId(), ConfigDocProcessEnum.TP));
				}
			}else if (companyLearners.getCompanyLearners().getNonSetaCompany() != null) {
				// for non seta company tasks
//				users.addAll(companyUsersService.findUsersByCompanyType(companyLearners.getCompanyLearners().getCompany().getId(), ConfigDocProcessEnum.TP));
			}
			
		} else if (targetClass.equals(CompanyLearnersTermination.class.getName())) {
			// CompanyLearnersTermination companyLearners = (CompanyLearnersTermination)
			// dao.getByClassAndKey(targetClass, targetKey);
			System.out.println("If 31");
			CompanyLearnersTermination companyLearners = CompanyLearnersTerminationService.instance().findByKey(targetKey);
			try {
				if (companyLearners.getTrainingProviderApplication() != null && companyLearners.getTrainingProviderApplication().getTrainingSite() != null && companyLearners.getTrainingProviderApplication().getTrainingSite().getId() != null) {
					System.out.println("If 31:"+companyLearners.getTrainingProviderApplication().getTrainingSite().getId());
				    users.addAll(sdpCompanyService.findAllSdpByCompanyIdAndTrainingSiteIdReturnUsers(companyLearners.getCompanyLearners().getCompany().getId(), companyLearners.getTrainingProviderApplication().getTrainingSite().getId()));
				} else {
					System.out.println("If 32:"+companyLearners.getTrainingProviderApplication().getTrainingSite().getId());
				    users.addAll(sdpCompanyService.findAllSdpByCompanyIdReturnUsers(companyLearners.getCompanyLearners().getCompany().getId()));
				}
			} catch (Exception e) {
			}
			
			if (!users.isEmpty()) {
				System.out.println("If 33:"+companyLearners.getTrainingProviderApplication().getTrainingSite().getId());
				users.addAll(companyUsersService.findUsersByCompanyType(companyLearners.getCompanyLearners().getCompany().getId(), ConfigDocProcessEnum.TP));
			}
		} else if (targetClass.equals(CompanyLearnersChange.class.getName())) {
			System.out.println("If 34");
			CompanyLearnersChange companyLearnersChange = (CompanyLearnersChange) dao.getByClassAndKey(targetClass, targetKey);
			if(firstInProcess) {
				System.out.println("If 35");
				users.add(companyLearnersChange.getCreateUser());
			}else {
				// CompanyLearnersChange companyLearners = (CompanyLearnersChange)
				// dao.getByClassAndKey(targetClass, targetKey);
				CompanyLearnersChange companyLearners = CompanyLearnersChangeService.instance().findByKey(targetKey);
				System.out.println("If 36");
				try {
					if (companyLearners.getTrainingProviderApplication() != null && companyLearners.getTrainingProviderApplication().getTrainingSite() != null && companyLearners.getTrainingProviderApplication().getTrainingSite().getId() != null) {
						System.out.println("If 37:"+companyLearners.getTrainingProviderApplication().getTrainingSite().getId());
					    users.addAll(sdpCompanyService.findAllSdpByCompanyIdAndTrainingSiteIdReturnUsers(companyLearners.getCompanyLearners().getCompany().getId(), companyLearners.getTrainingProviderApplication().getTrainingSite().getId()));
					} else {
						System.out.println("If 38:"+companyLearners.getCompanyLearners().getCompany().getId());
					    users.addAll(sdpCompanyService.findAllSdpByCompanyIdReturnUsers(companyLearners.getCompanyLearners().getCompany().getId()));
					}
				} catch (Exception e) {
				}
				if (!users.isEmpty()) {
					System.out.println("If 39:"+companyLearners.getCompanyLearners().getCompany().getId());
					users.addAll(companyUsersService.findUsersByCompanyType(companyLearners.getCompanyLearners().getCompany().getId(), ConfigDocProcessEnum.TP));
				}
			}
		} else if (targetClass.equals(CompanyLearners.class.getName())) {
			System.out.println("If 40:"+CompanyLearners.class.getName());
			CompanyLearners companyLearners = (CompanyLearners) dao.getByClassAndKey(targetClass, targetKey);
			if(firstInProcess) {
				System.out.println("If 41:"+companyLearners.getCreateUser());
				users.add(companyLearners.getCreateUser());
			}else{
				System.out.println("If 42:"+companyLearners.getCompany().getId());
				users.addAll(companyUsersService.findUsersByCompanyType(companyLearners.getCompany().getId(), ConfigDocProcessEnum.TP));
			}
		} else if (targetClass.equals(CompanyLearnersTradeTest.class.getName())) {
			// CompanyLearnersTradeTest companyLearners = (CompanyLearnersTradeTest)
			// dao.getByClassAndKey(targetClass, targetKey);
			System.out.println("If 43:"+CompanyLearnersTradeTest.class.getName());
			CompanyLearnersTradeTest clTradeTest = CompanyLearnersTradeTestService.instance().findByKey(targetKey);
			if (clTradeTest.getPreferredTrainingCenter() != null && clTradeTest.getTradeTestType() != null && clTradeTest.getTradeTestType() == TradeTestTypeEnum.ARPL) {
				try {
					System.out.println("If 44:"+clTradeTest.getPreferredTrainingCenter());
					System.out.println("clTradeTest.getTradeTestType():"+clTradeTest.getTradeTestType());
					System.out.println("TradeTestTypeEnum.ARPL:"+TradeTestTypeEnum.ARPL);
					if (clTradeTest.getTrainingProviderApplication() != null && clTradeTest.getTrainingProviderApplication().getTrainingSite() != null && clTradeTest.getTrainingProviderApplication().getTrainingSite().getId() != null) {
						System.out.println("If 45:"+clTradeTest.getTrainingProviderApplication());
						System.out.println("clTradeTest.getTrainingProviderApplication().getTrainingSite():"+clTradeTest.getTrainingProviderApplication().getTrainingSite());
						System.out.println("clTradeTest.getTrainingProviderApplication().getTrainingSite().getId():"+clTradeTest.getTrainingProviderApplication().getTrainingSite().getId());
						users.addAll(sdpCompanyService.findAllSdpByCompanyIdAndTrainingSiteIdReturnUsers(clTradeTest.getPreferredTrainingCenter().getId(), clTradeTest.getTrainingProviderApplication().getTrainingSite().getId()));
					} else {
						System.out.println("If 46:"+clTradeTest.getPreferredTrainingCenter().getId());
						users.addAll(sdpCompanyService.findAllSdpByCompanyIdReturnUsers(clTradeTest.getPreferredTrainingCenter().getId()));
					}
				} catch (Exception e) {
				}	
				
				if (users.isEmpty()) {
					// old version
					System.out.println("If 47:"+clTradeTest.getPreferredTrainingCenter().getId());
					users.addAll(companyUsersService.findUsersByCompanyType(clTradeTest.getPreferredTrainingCenter().getId(), ConfigDocProcessEnum.TP));
				}
			} else if(clTradeTest.getPreferredTrainingCenter() != null && clTradeTest.getTradeTestType() != null && clTradeTest.getTradeTestType() == TradeTestTypeEnum.TRADE_TEST){
				try {
					System.out.println("If 48:"+clTradeTest.getTradeTestType());
					System.out.println("TradeTestTypeEnum.TRADE_TEST:"+TradeTestTypeEnum.TRADE_TEST);
					if (clTradeTest.getTrainingProviderApplication() != null && clTradeTest.getTrainingProviderApplication().getTrainingSite() != null && clTradeTest.getTrainingProviderApplication().getTrainingSite().getId() != null) {
						System.out.println("If 49:"+clTradeTest.getTrainingProviderApplication().getTrainingSite().getId());
						users.addAll(sdpCompanyService.findAllSdpByCompanyIdAndTrainingSiteIdReturnUsers(clTradeTest.getPreferredTrainingCenter().getId(), clTradeTest.getTrainingProviderApplication().getTrainingSite().getId()));
					} else {
						System.out.println("If 50:"+clTradeTest.getPreferredTrainingCenter().getId());
						users.addAll(sdpCompanyService.findAllSdpByCompanyIdReturnUsers(clTradeTest.getPreferredTrainingCenter().getId()));
					}
				} catch (Exception e) {
				}
				if (users.isEmpty()) {
					// old version
					System.out.println("If 51:"+clTradeTest.getPreferredTrainingCenter().getId());
					users.addAll(companyUsersService.findUsersByCompanyType(clTradeTest.getPreferredTrainingCenter().getId(), ConfigDocProcessEnum.TP));
				}
			} else {
				try {
					System.out.println("If 52:"+clTradeTest.getTrainingProviderApplication().getTrainingSite().getId());
					if (clTradeTest.getTrainingProviderApplication() != null && clTradeTest.getTrainingProviderApplication().getTrainingSite() != null && clTradeTest.getTrainingProviderApplication().getTrainingSite().getId() != null) {
						System.out.println("If 53:"+clTradeTest.getTrainingProviderApplication().getTrainingSite().getId());
						users.addAll(sdpCompanyService.findAllSdpByCompanyIdAndTrainingSiteIdReturnUsers(clTradeTest.getPreferredTrainingCenter().getId(), clTradeTest.getTrainingProviderApplication().getTrainingSite().getId()));
					} else {
						System.out.println("If 54:"+clTradeTest.getPreferredTrainingCenter().getId());
						users.addAll(sdpCompanyService.findAllSdpByCompanyIdReturnUsers(clTradeTest.getPreferredTrainingCenter().getId()));
					}
				} catch (Exception e) {
				}
				if (users.isEmpty()) {
					// old version
					System.out.println("If 55:"+clTradeTest.getCompanyLearners().getCompany().getId());
					users.addAll(companyUsersService.findUsersByCompanyType(clTradeTest.getCompanyLearners().getCompany().getId(), ConfigDocProcessEnum.TP));
				}
			}
		} else if (targetClass.equals(TrainingProviderMonitoring.class.getName())) {
			
			TrainingProviderMonitoring companyLearners = (TrainingProviderMonitoring) dao.getByClassAndKey(targetClass, targetKey);
			System.out.println("If 56:"+companyLearners.getSpdUser().getId());
			// All Training Provider users will be removed from the provider user list below so that sdp involved in accreditation cant do audit / monitoring later on 
//			List<TrainingProviderApplication> trainingProviderApplications = tpas.findByCompany(companyLearners.getCompany());
//			List<Users> providerUsers = companyUsersService.findUsersByCompanyType(companyLearners.getCompany().getId(), ConfigDocProcessEnum.TP);
			TrainingProviderApplicationService tpas = new TrainingProviderApplicationService();
			if (companyLearners.getSpdUser() != null && companyLearners.getSpdUser().getId() != null) {
				System.out.println("If 57:"+Users.class.getName());
				System.out.println("companyLearners.getSpdUser().getId():"+companyLearners.getSpdUser().getId());
				Users sdpUser = (Users) dao.getByClassAndKey(Users.class.getName(), companyLearners.getSpdUser().getId());
				tpas = null;
				users.add(sdpUser);
			}else {
				System.out.println("If 58:"+companyLearners.getCompany());
				Users sdpUser = tpas.findUsersByCompanyAndReturnUser(companyLearners.getCompany());
				tpas = null;
				users.add(sdpUser);
			}
			
		} else if (targetClass.equals(NonSetaQualificationsCompletion.class.getName())) {
			System.out.println("If 59");
			NonSetaQualificationsCompletion companyLearners = (NonSetaQualificationsCompletion) dao.getByClassAndKey(targetClass, targetKey);
			if(firstInProcess) {
				System.out.println("If 60:"+companyLearners.getCreateUser());
				users.add(companyLearners.getCreateUser());
			}else{
				System.out.println("If 61:"+companyLearners.getCompany().getId());
				users.addAll(companyUsersService.findByCompanyNotProcess(companyLearners.getCompany().getId()));
				if (users.size() == 0) users.addAll(companyUsersService.findUsersByCompanyType(companyLearners.getCompany().getId(), ConfigDocProcessEnum.TP));
			}
		} else if (targetClass.equals(CompletionLetter.class.getName())) {
			System.out.println("If 62");
			CompletionLetter companyLearners = (CompletionLetter) dao.getByClassAndKey(targetClass, targetKey);
			if(firstInProcess) {
				System.out.println("If 63:"+companyLearners.getCreateUser());
				users.add(companyLearners.getCreateUser());
			}else{
				System.out.println("If 64:"+companyLearners.getCompany().getId());
				System.out.println("ConfigDocProcessEnum.TP:"+ConfigDocProcessEnum.TP);
				users.addAll(companyUsersService.findByCompanyNotProcess(companyLearners.getCompany().getId()));
				if (users.size() == 0) users.addAll(companyUsersService.findUsersByCompanyType(companyLearners.getCompany().getId(), ConfigDocProcessEnum.TP));
			}
		}else if (targetClass.equals(TrainingProviderVerfication.class.getName())) {
			System.out.println("If 65");
			TrainingProviderVerfication companyLearners = (TrainingProviderVerfication) dao.getByClassAndKey(targetClass, targetKey);
			if(firstInProcess) {
				System.out.println("If 66::"+companyLearners.getCreateUser());
				users.add(companyLearners.getCreateUser());
			}else{
				System.out.println("If 67:"+companyLearners.getTrainingProvider().getId());
				System.out.println("ConfigDocProcessEnum.TP:"+ConfigDocProcessEnum.TP);
				users.addAll(companyUsersService.findByCompanyNotProcess(companyLearners.getTrainingProvider().getId()));
				if (users.size() == 0) users.addAll(companyUsersService.findUsersByCompanyType(companyLearners.getTrainingProvider().getId(), ConfigDocProcessEnum.TP));
			}
		} else if (targetClass.equals(CompanyLearnersTradeTest.class.getName())) {
			// CompanyLearnersTradeTest companyLearners = (CompanyLearnersTradeTest)
			// dao.getByClassAndKey(targetClass, targetKey);
			System.out.println("If 68:"+ ApprovalEnum.Recommended);
			
			CompanyLearnersTradeTest companyLearners = CompanyLearnersTradeTestService.instance().findByKey(targetKey);
			System.out.println("companyLearners.getStatus():"+companyLearners.getStatus());
			System.out.println("companyLearners.getPreferredTrainingCenter().getId():"+companyLearners.getPreferredTrainingCenter().getId());
			System.out.println("ConfigDocProcessEnum.TP:"+ConfigDocProcessEnum.TP);
			if (companyLearners.getStatus() == ApprovalEnum.Recommended) users.addAll(companyUsersService.findUsersByCompanyType(companyLearners.getPreferredTrainingCenter().getId(), ConfigDocProcessEnum.TP));
			
			else users.addAll(companyUsersService.findUsersByCompanyType(companyLearners.getCompanyLearners().getCompany().getId(), ConfigDocProcessEnum.TP));
		} else if (targetClass.equals(SiteVisitReport.class.getName())) {
			
			SiteVisitReport siteVisitReport = (SiteVisitReport) dao.getByClassAndKey(SiteVisitReport.class, targetKey);
			SDFCompany sdfCompany = sdfCompanyService.findPrimarySDF(siteVisitReport.getCompany());
			System.out.println("If 69:"+siteVisitReport.getCompany());
			if (sdfCompany != null) users.add(sdfCompany.getSdf());
		} else if (targetClass.equals(AdministrationOfAQP.class.getName())) {
			System.out.println("If 70");
			AdministrationOfAQP siteVisitReport = (AdministrationOfAQP) dao.getByClassAndKey(targetClass, targetKey);			
			checkAQP(users, configDocProcessEnum, firstInProcess, processRoles, createUser, siteVisitReport);
		} else if (targetClass.equals(WorkplaceMonitoring.class.getName())) {
			
			WorkplaceMonitoring siteVisitReport = (WorkplaceMonitoring) dao.getByClassAndKey(targetClass, targetKey);
			List<SDFCompany> sdf = sdfCompanyService.findPrimaryAndLabourSDF(siteVisitReport.getCompany());
			System.out.println("If 71::"+siteVisitReport.getCompany());
			users.addAll(sdf.stream().map(sc -> sc.getSdf()).collect(Collectors.toList()));
		} else if (targetClass.equals(DgAllocationParent.class.getName())) {			
			DgAllocationParent siteVisitReport = (DgAllocationParent) dao.getByClassAndKey(targetClass, targetKey);
			System.out.println("If 72:"+siteVisitReport.getWsp().getCompany());
			SDFCompany sdf = sdfCompanyService.findPrimarySDF(siteVisitReport.getWsp().getCompany());
			users.add(sdf.getSdf());
		} else if (targetClass.equals(ActiveContracts.class.getName())) {
			System.out.println("If 73:"+ActiveContracts.class.getName());
			ActiveContracts siteVisitReport = (ActiveContracts) dao.getByClassAndKey(targetClass, targetKey);
			if (siteVisitReport.getDgAllocationParent() != null) {
				System.out.println("If 74:"+siteVisitReport.getDgAllocationParent());
				System.out.println("siteVisitReport.getDgAllocationParent().getWsp().getCompany():"+siteVisitReport.getDgAllocationParent().getWsp().getCompany());
				SDFCompany sdf = sdfCompanyService.findPrimarySDF(siteVisitReport.getDgAllocationParent().getWsp().getCompany());
				users.add(sdf.getSdf());				
			}else {
				System.out.println("If 75:"+siteVisitReport.getCompany());
				SDFCompany sdf = sdfCompanyService.findPrimarySDF(siteVisitReport.getCompany());
				users.add(sdf.getSdf());
			}
			if (configDocProcessEnum == ConfigDocProcessEnum.DG_PROJECT_TERMINATION || configDocProcessEnum == ConfigDocProcessEnum.SDF_DG_PROJECT_TERMINATION) {
				System.out.println("If 76:"+ConfigDocProcessEnum.DG_PROJECT_TERMINATION);
				System.out.println("ConfigDocProcessEnum.SDF_DG_PROJECT_TERMINATION:"+ConfigDocProcessEnum.SDF_DG_PROJECT_TERMINATION);
				if(firstInProcess) {
					System.out.println("If 77");
					if(users.size()>0) {
						System.out.println("If 78");
						users.remove(0);
					}
					users.add(createUser);
				}
			}
		} else if (targetClass.equals(QdfCompany.class.getName())) {
			System.out.println("If 79:"+QdfCompany.class.getName());
			QdfCompany qdfCompany = (QdfCompany) dao.getByClassAndKey(targetClass, targetKey);
			users.add(qdfCompany.getUser());
		}
	}

	private void checkAQP(List<Users> users, ConfigDocProcessEnum configDocProcessEnum, boolean firstInProcess, ProcessRoles processRoles, Users createUser, AdministrationOfAQP siteVisitReport) {
		if (configDocProcessEnum == ConfigDocProcessEnum.ApplicationAdministrationOfAQP) {

			if (firstInProcess) {
				users.add(siteVisitReport.getContactPerson());
			} else {
				if (createUser != null) {
					if (createUser.equals(siteVisitReport.getAssessor())) users.add(siteVisitReport.getModerator());
					else users.add(siteVisitReport.getAssessor());
				} else {
					users.add(siteVisitReport.getAssessor());
					users.add(siteVisitReport.getModerator());
				}
			}
		} else {
			if (processRoles.getRolePermission() == UserPermissionEnum.Upload) {
				if (createUser != null) {
					if (createUser.equals(siteVisitReport.getAssessor())) users.add(siteVisitReport.getModerator());
					else users.add(siteVisitReport.getAssessor());
				} else {
					users.add(siteVisitReport.getAssessor());
					users.add(siteVisitReport.getModerator());
				}
			} else {
				users.add(siteVisitReport.getContactPerson());
			}
		}
	}

	public String replaceStrings(String targetClass, String description, long targetKey, Users previousUser) throws Exception {
		if (description != null) {
			IDataEntity target = dao.getByClassAndKey(targetClass, targetKey);
			
			if (previousUser != null) {
				description = description.replace("#PREVIOUS_FIRST_NAME#", previousUser.getFirstName());
				description = description.replace("#PREVIOUS_LAST_NAME#", previousUser.getLastName());
				description = description.replace("#PREVIOUS_IDENTITY_NUMBER#", anIDNumber(previousUser));
				description = description.replace("#PREVIOUS_EMAIL#", previousUser.getEmail());
			}

			if (targetClass.equals(Users.class.getName())) {
				description = replaceStringsUsers((Users) target, description);
			} else if (targetClass.equals(Company.class.getName())) {
				description = replaceStringsCompany((Company) target, description);
			} else if (targetClass.equals(Wsp.class.getName())) {
				Wsp c = (Wsp) target;
				description = replaceStringsCompany(c.getCompany(), description);
				description = replaceStringsWspDeviation(c, description);
			} else if (targetClass.equals(TrainingComittee.class.getName())) {
				Users u = new Users();
				BeanUtils.copyProperties(u, ((TrainingComittee) target));
				description = replaceStringsUC(u, ((TrainingComittee) target).getCompany(), description);
			} else if (targetClass.equals(Sites.class.getName())) {
				Sites s = (Sites) target;
				description = replaceStringsCompany(((Sites) target).getCompany(), description);
				if (s.getFormUser() != null && s.getFormUser().getId() != null) {
					Users u = (Users) dao.getByClassAndKey(Users.class.getName(), s.getFormUser().getId());
					if (u != null) {
						description = replaceStringsUsers(u, description);
					}
				}
			} else if (targetClass.equals(SDFCompany.class.getName())) {
				SDFCompany c = (SDFCompany) target;
				description = replaceStringsUC(c.getSdf(), c.getCompany(), description);
			} else if (targetClass.equals(BankingDetails.class.getName())) {
				BankingDetails c = (BankingDetails) target;
				description = replaceStringsCompany(c.getCompany(), description);
			} else if (targetClass.equals(DgVerification.class.getName())) {
				DgVerification c = (DgVerification) target;
				description = replaceStringsCompany(c.getWsp().getCompany(), description);
				if (c.getCrmDecision() != null) {
					description = description.replace("#CRM_DECISION#", c.getCrmDecision().getFriendlyName());
				}
			} else if (targetClass.equals(MgVerification.class.getName())) {
				MgVerification c = (MgVerification) target;
				description = replaceStringsCompany(c.getWsp().getCompany(), description);
				if (c.getWsp().getCreateUsers() != null) {
					description = replaceStringsUsers(c.getWsp().getCreateUsers(), description);
				}
				Users clo = getCLO(c.getWsp());
				if (clo != null) {
					description = replaceStringsClo(clo, description);
				}
			} else if (targetClass.equals(InterSetaTransfer.class.getName())) {
				InterSetaTransfer c = (InterSetaTransfer) target;
				Company comp = null;
				if (c.getCompany() != null && c.getCompany().getId() != null) {
					comp = (Company) dao.getByClassAndKey(Company.class.getName(), c.getCompany().getId());
				}
				if (comp != null && comp.getId() != null) {
					description = replaceStringsUC(c.getUsers(), comp, description);
				}else {
					description = replaceStringsUsers(c.getUsers(), description);
				}
			} else if (targetClass.equals(ExtensionRequest.class.getName())) {
				ExtensionRequest c = (ExtensionRequest) target;
				if(c.getCompany() == null && c.getWsp() != null && c.getWsp().getCompany() !=null) {
					description = replaceStringsUC(c.getUser(), c.getWsp().getCompany(), description);
				}else {
					description = replaceStringsUC(c.getUser(), c.getCompany(), description);
				}				
			} else if (targetClass.equals(CompanyUsers.class.getName())) {
				CompanyUsers c = (CompanyUsers) target;
				description = replaceStringsUC(c.getUser(), c.getCompany(), description);
				if(c !=null && c.getTargetClass() !=null && c.getTargetClass().equals("haj.com.entity.TrainingProviderApplication")){
					TrainingProviderApplicationService tpService=new TrainingProviderApplicationService();
					TrainingProviderApplication tp=tpService.findByKey(c.getTargetKey());
					description =replaceStringsAccreditationNumber(tp, description);
				}
			} else if (targetClass.equals(CoursewareDistibution.class.getName())) {
				CoursewareDistibution c = (CoursewareDistibution) target;
				description = replaceStringsUC(c.getUser(), c.getCompany(), description);
			} else if (targetClass.equals(SkillsRegistration.class.getName())) {
				SkillsRegistration c = (SkillsRegistration) target;
				description = replaceStringsCompanyOrUser(c.getUser(), c.getCompany(), description);
			} else if (targetClass.equals(LearnershipDevelopmentRegistration.class.getName())) {
				LearnershipDevelopmentRegistration c = (LearnershipDevelopmentRegistration) target;
				description = replaceStringsUC(c.getUsers(), c.getCompany(), description);
			} else if (targetClass.equals(WorkPlaceApproval.class.getName())) {
				WorkPlaceApproval c = (WorkPlaceApproval) target;
				description = replaceStringsCompany(c.getCompany(), description);
			} else if (targetClass.equals(QualificationsCurriculumDevelopment.class.getName())) {
				QualificationsCurriculumDevelopment c = (QualificationsCurriculumDevelopment) target;
				description = replaceStringsUC(c.getCreateUser(), c.getCompany(), description);
			} else if (targetClass.equals(SiteVisit.class.getName())) {
				SiteVisit c = (SiteVisit) target;
				description = replaceStringsUC(c.getUsers(), c.getCompany(), description);
			} else if (targetClass.equals(CompanyLearnersTransfer.class.getName())) {
				// CompanyLearnersTransfer c = (CompanyLearnersTransfer) target;
				CompanyLearnersTransfer c = CompanyLearnersTransferService.instance().findByKey(((CompanyLearnersTransfer) target).getId());
				if (c.getLearnerTransferType() == LearnerTransferTypeEnum.ProviderTransfer) {
					description = replaceStringsCompany(c.getTransferToCompany(), description);
				} else {
					description = replaceStringsCompany(c.getCompanyLearners().getCompany(), description);
				}
				if (c != null && c.getCompanyLearners() != null && c.getCompanyLearners().getId() != null) {
					CompanyLearners cl = CompanyLearnersService.instance().findByKey(c.getCompanyLearners().getId());
					if (cl.getUser() != null) {
						description = replaceStringsLearner(cl.getUser(), description);
					}
				}
			} else if (targetClass.equals(CompanyLearnersLostTime.class.getName())) {
				// CompanyLearnersLostTime c = (CompanyLearnersLostTime) target;
				CompanyLearnersLostTime c = CompanyLearnersLostTimeService.instance().findByKey(((CompanyLearnersLostTime) target).getId());
				if (c.getCompanyLearners().getCompany() != null) {
					description = replaceStringsCompany(c.getCompanyLearners().getCompany(), description);
				}
				if (c.getCompanyLearners().getNonSetaCompany() != null) {
					description = replaceStringsNonSetaCompany(c.getCompanyLearners().getNonSetaCompany(), description);
				}
				if (c != null && c.getCompanyLearners() != null && c.getCompanyLearners().getId() != null) {
					CompanyLearners cl = CompanyLearnersService.instance().findByKey(c.getCompanyLearners().getId());
					if (cl.getUser() != null) {
						description = replaceStringsLearner(cl.getUser(), description);
					}
					if (cl.getCompany() != null && cl.getCompany().getId() != null) {
						Company company = (Company) dao.getByClassAndKey(Company.class.getName(), cl.getCompany().getId());
						description = replaceStringsCompany(company, description);
					}
				}
			}else if (targetClass.equals(CompanyLearnersDetailsChange.class.getName())) {
				CompanyLearnersDetailsChange c = CompanyLearnersDetailsChangeService.instance().findByKey(((CompanyLearnersDetailsChange) target).getId());
				if (c.getCompanyLearnersParent().getCompany() != null) {
					description = replaceStringsCompany(c.getCompanyLearnersParent().getCompany(), description);
				}
				if (c.getCompanyLearnersParent().getNonSetaCompany() != null) {
					description = replaceStringsNonSetaCompany(c.getCompanyLearnersParent().getNonSetaCompany(), description);
				}
				if (c != null && c.getCompanyLearnersParent() != null && c.getCompanyLearnersParent().getId() != null) {
					CompanyLearners cl = CompanyLearnersService.instance().findByKey(c.getCompanyLearnersParent().getId());
					if (cl.getUser() != null) {
						description = replaceStringsLearner(cl.getUser(), description);
					}
					if (cl.getCompany() != null && cl.getCompany().getId() != null) {
						Company company = (Company) dao.getByClassAndKey(Company.class.getName(), cl.getCompany().getId());
						description = replaceStringsCompany(company, description);
					}
				}
			} else if (targetClass.equals(CompanyLearnersTermination.class.getName())) {
				CompanyLearnersTermination c = CompanyLearnersTerminationService.instance().findByKey(((CompanyLearnersTermination) target).getId());
				description = replaceStringsCompany(c.getCompanyLearners().getCompany(), description);
				if (c != null && c.getCompanyLearners() != null && c.getCompanyLearners().getId() != null) {
					CompanyLearners cl = CompanyLearnersService.instance().findByKey(c.getCompanyLearners().getId());
					if (cl.getUser() != null) {
						description = replaceStringsLearner(cl.getUser(), description);
					}
				}
			} else if (targetClass.equals(CompanyLearnersChange.class.getName())) {
				// CompanyLearnersChange c = (CompanyLearnersChange) target;
				CompanyLearnersChange c = CompanyLearnersChangeService.instance().findByKey(((CompanyLearnersChange) target).getId());
				description = replaceStringsCompany(c.getCompanyLearners().getCompany(), description);
				if (c != null && c.getCompanyLearners() != null && c.getCompanyLearners().getId() != null) {
					CompanyLearners cl = CompanyLearnersService.instance().findByKey(c.getCompanyLearners().getId());
					if (cl.getUser() != null) {
						description = replaceStringsLearner(cl.getUser(), description);
						description = replaceStringsCompany(cl.getCompany(), description);
						description = replaceStringsUsers(cl.getCreateUser(), description);
					}
				}
			} else if (targetClass.equals(CompanyLearners.class.getName())) {
				CompanyLearners c = (CompanyLearners) target;
				description = replaceStringsCompany(c.getCompany(), description);
				if (c.getUser() != null) {
					description = replaceStringsLearner(c.getUser(), description);									
				}
				if(c.getCreateUser()!=null) {
					description = replaceStringsUsers(c.getCreateUser(), description);	
				}
			} else if (targetClass.equals(CompanyLearnersTradeTest.class.getName())) {
				CompanyLearnersTradeTest c = CompanyLearnersTradeTestService.instance().findByKey(((CompanyLearnersTradeTest) target).getId());
				// learner
				if (c != null && c.getLearner() != null && c.getLearner().getId() != null) {
					Users learner = usersService.findByKey(c.getLearner().getId());
					if (learner != null) {
						description = replaceStringsLearner(learner, description);
					}
				}
				// initiator
				if (c != null && c.getCreateUser() != null && c.getCreateUser().getId() != null) {
					Users createUser = usersService.findByKey(c.getCreateUser().getId());
					if (createUser != null) {
						description = replaceStringsArplInitiator(createUser, description);
					}
				}
				// Trade test Center
				if (c != null && c.getPreferredTrainingCenter() != null && c.getPreferredTrainingCenter().getId() != null) {
					description = replaceStringsArplTradeTestCenter(c.getPreferredTrainingCenter(), description);
				}
				// ARPL related info
				description = replaceStringsArplInformation(c, description);
			} else if (targetClass.equals(TrainingProviderMonitoring.class.getName())) {
				TrainingProviderMonitoring c = (TrainingProviderMonitoring) target;
				description = replaceStringsCompany(c.getCompany(), description);
				description = replaceStringsUsers(c.getUser(), description);
				description = replaceStringsTrainingProvider(c.getCompany(), description);
			} else if (targetClass.equals(NonSetaQualificationsCompletion.class.getName())) {
				NonSetaQualificationsCompletion c = (NonSetaQualificationsCompletion) target;
				description = replaceStringsCompany(c.getCompany(), description);
				description = replaceStringsUsers(c.getCreateUser(), description);
				if(c.getCompanyLearners() != null && c.getCompanyLearners().getUser()!=null) {
					description = replaceStringsLearner(c.getCompanyLearners().getUser(), description);
				}
			} else if (targetClass.equals(CompletionLetter.class.getName())) {
				CompletionLetter c = (CompletionLetter) target;
				description = replaceStringsCompany(c.getCompany(), description);
				description = replaceStringsUsers(c.getCreateUser(), description);
				if(c.getCompanyLearners() != null && c.getCompanyLearners().getUser()!=null) {
					description = replaceStringsLearner(c.getCompanyLearners().getUser(), description);
				}
			} else if (targetClass.equals(TrainingProviderVerfication.class.getName())) {
				TrainingProviderVerfication c = (TrainingProviderVerfication) target;
				if(c.getCompanyLearners() != null) {
					CompanyLearners cl = CompanyLearnersService.instance().findByKey(c.getCompanyLearners().getId());
					description = replaceStringsCompany(cl.getCompany(), description);
					description = replaceStringsUsers(cl.getUser(), description);
					if(cl != null && cl.getUser()!=null) {
						description = replaceStringsLearner(cl.getUser(), description);
					}
				}else {
					description = "There is an external moderation request application. Please review.";
				}
			} else if (targetClass.equals(CompanyLearnersTradeTest.class.getName())) {
				CompanyLearnersTradeTest c = CompanyLearnersTradeTestService.instance().findByKey(((CompanyLearnersTradeTest) target).getId());
				// learner
				if (c != null && c.getLearner() != null && c.getLearner().getId() != null) {
					Users learner = usersService.findByKey(c.getLearner().getId());
					if (learner != null) {
						description = replaceStringsLearner(learner, description);
					}
				}
				// initiator
				if (c != null && c.getCreateUser() != null && c.getCreateUser().getId() != null) {
					Users createUser = usersService.findByKey(c.getCreateUser().getId());
					if (createUser != null) {
						description = replaceStringsArplInitiator(createUser, description);
					}
				}
				// Trade test Center
				if (c != null && c.getPreferredTrainingCenter() != null && c.getPreferredTrainingCenter().getId() != null) {
					description = replaceStringsArplTradeTestCenter(c.getPreferredTrainingCenter(), description);
				}
				// ARPL related info
				description = replaceStringsArplInformation(c, description);
			} else if (targetClass.equals(DgAllocationParent.class.getName())) {
				DgAllocationParent c = (DgAllocationParent) target;
				if (c.getWsp() != null && c.getWsp().getCompany() != null) description = replaceStringsCompany(c.getWsp().getCompany(), description);
			} else if (targetClass.equals(ActiveContracts.class.getName())) {
				ActiveContracts c = (ActiveContracts) target;
				if (c.getDgAllocationParent() != null && c.getDgAllocationParent().getWsp().getCompany() != null){
					description = replaceStringsCompany(c.getDgAllocationParent().getWsp().getCompany(), description);
				}else{
					description = replaceStringsCompany(c.getCompany(), description);
				}
			} else if (targetClass.equals(SitesSme.class.getName())) {
				SitesSme c = (SitesSme) target;
				description = replaceStringsCompany(c.getCompany(), description);
				description = replaceStringsSitesSme(c, description);
			} else if (targetClass.equals(SkillsRegistration.class.getName())) {
				SkillsRegistration sr = (SkillsRegistration) target;
				description = replaceStringsCompanyOrUser(sr.getUser(),sr.getCompany(), description);
			} else if (targetClass.equals(AssessorModeratorApplication.class.getName())) {
				AssessorModeratorApplication assModApp = (AssessorModeratorApplication) target;
				description = replaceStringsUsers(assModApp.getUser(), description);
			} else if (targetClass.equals(AssessorModReRegistration.class.getName())) {
				AssessorModReRegistration assModApp = (AssessorModReRegistration) target;
				description = replaceStringsUsers(assModApp.getUser(), description);
			}
			
			else if (targetClass.equals(AssessorModExtensionOfScope.class.getName())) {
				AssessorModExtensionOfScope assModApp = (AssessorModExtensionOfScope) target;
				description = replaceStringsUsers(assModApp.getCreateUser(), description);
			
			} else if (targetClass.equals(SDPExtensionOfScope.class.getName())) {
				SDPExtensionOfScope sdpExtensionOfScope = (SDPExtensionOfScope) target;
				description = replaceStringsUsers(sdpExtensionOfScope.getUsers(), description);
				if (sdpExtensionOfScope.getTrainingProviderApplication() != null && sdpExtensionOfScope.getTrainingProviderApplication().getCompany() != null) {
					description = replaceStringsCompany(sdpExtensionOfScope.getTrainingProviderApplication().getCompany(), description);
				}
				if (sdpExtensionOfScope.getTrainingProviderApplication() != null) {
					description =replaceStringsAccreditationNumber(sdpExtensionOfScope.getTrainingProviderApplication(), description);
				}
			} else if (targetClass.equals(TrainingProviderApplication.class.getName())) {
				TrainingProviderApplication tpa =  (TrainingProviderApplication) target;
				if (tpa != null) {
					if (tpa.getCompany() != null) {
						description = replaceStringsCompany(tpa.getCompany(), description);
						description =replaceStringsAccreditationNumber(tpa, description);
					} else if (tpa.getNonSetaCompany() != null) {
						description = replaceStringsNonSetaCompany(tpa.getNonSetaCompany(), description);
						description =replaceStringsAccreditationNumber(tpa, description);
					}
				}
			}
			else if (targetClass.equals(SDPReAccreditation.class.getName())) {
				SDPReAccreditation sdpReAccreditation =  (SDPReAccreditation) target;
				if (sdpReAccreditation != null) {
					if (sdpReAccreditation.getTrainingProviderApplication().getCompany() != null) {
						description = replaceStringsCompany(sdpReAccreditation.getTrainingProviderApplication().getCompany(), description);
						description =replaceStringsAccreditationNumber(sdpReAccreditation.getTrainingProviderApplication(), description);
					}
				}
			}
	        else if (targetClass.equals(CompanyLearnersTermination.class.getName())) {
                CompanyLearnersTermination clt =  (CompanyLearnersTermination) target;
                if (clt != null) {
                    if (clt.getCompanyLearners().getCompany() != null) {
                        description = replaceStringsCompany(clt.getCompanyLearners().getCompany() , description);
                    } 
                    
                    if(clt.getCompanyLearners().getUser() !=null)
                    {
                        description = replaceStringsLearner(clt.getCompanyLearners().getUser(), description);
                    }
                }
            } else if (targetClass.equals(GpGrantBatchList.class.getName())) {
            	GpGrantBatchList gpGrantBatchList = (GpGrantBatchList) target;
            	description = replaceStringsMgDgTransfer(gpGrantBatchList, description);
			} else if (targetClass.equals(DgContractingBulkEntry.class.getName())) {
				DgContractingBulkEntry dgContractingBulkEntry = (DgContractingBulkEntry) target;
				description = replaceStringsDgContractBulkWorkflow(dgContractingBulkEntry, description);
			} else if (targetClass.equals(ActiveContractExtensionRequest.class.getName())) {
				ActiveContractExtensionRequest acr = (ActiveContractExtensionRequest) target;		
				if (acr.getActiveContracts() != null && acr.getActiveContracts().getId() != null) {
					ActiveContracts c =  (ActiveContracts) dao.getByClassAndKey(ActiveContracts.class, acr.getActiveContracts().getId());
					if (c.getDgAllocationParent() != null && c.getDgAllocationParent().getWsp().getCompany() != null){
						description = replaceStringsCompany(c.getDgAllocationParent().getWsp().getCompany(), description);
					}else{
						description = replaceStringsCompany(c.getCompany(), description);
					}
				}
			} else if (targetClass.equals(ActiveContractDetail.class.getName())) {
				ActiveContractDetail acd = (ActiveContractDetail) target;		
				if (acd.getActiveContracts() != null && acd.getActiveContracts().getId() != null) {
					ActiveContracts c =  (ActiveContracts) dao.getByClassAndKey(ActiveContracts.class, acd.getActiveContracts().getId());
					if (c.getDgAllocationParent() != null && c.getDgAllocationParent().getWsp().getCompany() != null){
						description = replaceStringsCompany(c.getDgAllocationParent().getWsp().getCompany(), description);
					}else{
						description = replaceStringsCompany(c.getCompany(), description);
					}
				}
			} else if (targetClass.equals(ActiveContractTerminationRequest.class.getName())) {
				ActiveContractTerminationRequest actr = (ActiveContractTerminationRequest) target;		
				if (actr.getActiveContracts() != null && actr.getActiveContracts().getId() != null) {
					ActiveContracts c =  (ActiveContracts) dao.getByClassAndKey(ActiveContracts.class, actr.getActiveContracts().getId());
					if (c.getDgAllocationParent() != null && c.getDgAllocationParent().getWsp().getCompany() != null){
						description = replaceStringsCompany(c.getDgAllocationParent().getWsp().getCompany(), description);
					}else{
						description = replaceStringsCompany(c.getCompany(), description);
					}
				} 
			} else if (targetClass.equals(WorkplaceMonitoringSiteVisit.class.getName())) {
				WorkplaceMonitoringSiteVisit wpm = (WorkplaceMonitoringSiteVisit) target;		
				if (wpm.getCompany() != null &&wpm.getCompany().getId() != null) {
					description = replaceStringsCompany(wpm.getCompany(), description);
				}
				description = replaceStringsWorkplaceMonitoringSiteVisist(wpm, description);
			} else if (targetClass.equals(ScheduledEvent.class.getName())) {
				ScheduledEvent scheduledEvent=(ScheduledEvent) target;
				description = replaceStringsCompany(scheduledEvent.getCompany(), description);
				description = replaceStringsUsers(scheduledEvent.getUser(), description);
			}else if (targetClass.equals(WorkPlaceApprovalMentors.class.getName())) {
				WorkPlaceApprovalMentors workPlaceApprovalMentors=(WorkPlaceApprovalMentors) target;
				WorkPlaceApprovalService wpas = new WorkPlaceApprovalService();
				WorkPlaceApproval workPlaceApproval =wpas.findByKey(workPlaceApprovalMentors.getWorkPlaceApproval().getId());
				description = replaceStringsCompany(workPlaceApproval.getCompany(), description);
				description = replaceStringsUsers(workPlaceApprovalMentors.getCreateUser(), description);
			} else if (targetClass.equals(SdpCompanyActions.class.getName())){
				SdpCompanyActions sdpCompanyActions = (SdpCompanyActions) target;
				// replace company information
				description = replaceStringsCompany(sdpCompanyActions.getCompany(), description);
				// replace training site information
				description = replaceStringsTrainingSite(sdpCompanyActions.getTrainingSite(), description);
				// replace info regarding process
				description = replaceStringsSdpCompanyActions(sdpCompanyActions, description);
			}
		}
		return description;
	}

	private String replaceStringsWspDeviation(Wsp c, String description) {
		String toReturn = description;
		try {
			WspChecklistService service = new WspChecklistService();
			WspChecklist checklist = service.findByWSP(c);
			if (checklist != null) {
				if (checklist.getGrantDeviated() != null) {
					if (checklist.getGrantDeviated()) {
						toReturn = toReturn.replace("#DEVIATION_STATUS#", "Yes");
					} else {
						toReturn = toReturn.replace("#DEVIATION_STATUS#", "No");
					}
				} else {
					toReturn = toReturn.replace("#DEVIATION_STATUS#", "No Data");
				}
				checklist = null;
			}
			service = null;
		} catch (Exception ex) {
		}
		return toReturn;
	}

	private String replaceStringsCompany(Company c, String description) {
		String toReturn = description;
		try { 
			toReturn = toReturn.replace("#COMPANY_NAME#", c.getCompanyName());
			toReturn = toReturn.replace("#TRADING_NAME#", c.getTradingName() != null ? c.getTradingName() : "N/A");
			toReturn = toReturn.replace("#ENTITY_ID#", c.getLevyNumber() != null ? c.getLevyNumber() : "N/A");
			toReturn = toReturn.replace("#TEL_NUMBER#", c.getTelNumber() != null ? c.getTelNumber() : "N/A");
			toReturn = toReturn.replace("#COMPANY_EMAIL#", c.getEmail() != null ? c.getEmail() : "N/A");
		} catch (Exception ex) {
		}
		return toReturn;
	}
	
	private String replaceStringsTrainingSite(TrainingSite ts, String description) {
		String toReturn = description;
		try { 
			if (ts != null) {
				toReturn = toReturn.replace("#TRAINING_SITE_NAME#", ts.getSiteName());
			} else {
				toReturn = toReturn.replace("#TRAINING_SITE_NAME#", "N/A");
			}
		} catch (Exception ex) {
		}
		return toReturn;
	}
	
	private String replaceStringsSdpCompanyActions(SdpCompanyActions sdp, String description) {
		String toReturn = description;
		try { 
			toReturn = toReturn.replace("#REQUEST_DATE#", (sdp.getCreateDate() != null ? HAJConstants.sdfDDMMYYYY2.format(sdp.getCreateDate()) : "N/A"));
			toReturn = toReturn.replace("#ACTION_REQUESTED#",( sdp.getSdpCompanyAction() != null ? sdp.getSdpCompanyAction().getFriendlyName() : "N/A"));
			toReturn = toReturn.replace("#SDP_USER_IDENTITY_NUMBER#", anIDNumber(sdp.getSdpUser()));
			toReturn = toReturn.replace("#SDP_USER_FIRST_NAME#", sdp.getSdpUser().getFirstName());
			toReturn = toReturn.replace("#SDP_USER_LAST_NAME#", sdp.getSdpUser().getLastName());
			toReturn = toReturn.replace("#SDP_USER_EMAIL#", sdp.getSdpUser().getEmail());
		} catch (Exception ex) {
		}
		return toReturn;
	}
	
	private String replaceStringsAccreditationNumber (TrainingProviderApplication tp, String description) {
		String toReturn = description;
		try {
			if(tp !=null)
			{
				String accReNum="N/A";
				if(tp.getAccreditationNumber() !=null)
				{
					accReNum=tp.getAccreditationNumber();
				}
				else if(tp.getCertificateNumber()!=null)
				{
					accReNum=tp.getCertificateNumber();
				}
				toReturn = toReturn.replace("#ACCREDITATION_NUMBER#", accReNum);
			}
		} catch (Exception ex) {
		}
		return toReturn;
	}
	
	private String replaceStringsNonSetaCompany(NonSetaCompany c, String description) {
		String toReturn = description;
		try {
			toReturn = toReturn.replace("#COMPANY_NAME#", c.getCompanyName());
			toReturn = toReturn.replace("#TEL_NUMBER#", c.getTelNumber() != null ? c.getTelNumber() : "N/A");
			toReturn = toReturn.replace("#COMPANY_EMAIL#", c.getEmail() != null ? c.getEmail() : "N/A");
			toReturn = toReturn.replace("#TRADING_NAME#",  "N/A");
			toReturn = toReturn.replace("#ENTITY_ID#", "N/A");
			toReturn = toReturn.replace("#TEL_NUMBER#", c.getTelNumber() != null ? c.getTelNumber() : "N/A");
		} catch (Exception ex) {
		}
		return toReturn;
	}

	private String replaceStringsUsers(Users u, String description) {
		String toReturn = description;
		toReturn = toReturn.replace("#IDENTITY_NUMBER#", anIDNumber(u));
		toReturn = toReturn.replace("#FIRST_NAME#", u.getFirstName());
		toReturn = toReturn.replace("#LAST_NAME#", u.getLastName());
		toReturn = toReturn.replace("#EMAIL#", u.getEmail());
		return toReturn;
	}
	
	private String replaceStringsLearner(Users u, String description) {
		String toReturn = description;
		toReturn = toReturn.replace("#LEARNER_IDENTITY_NUMBER#", anIDNumber(u));
		toReturn = toReturn.replace("#LEARNER_FIRST_NAME#", u.getFirstName());
		toReturn = toReturn.replace("#LEARNER_LAST_NAME#", u.getLastName());
		if (u.getEmail() != null) {
			toReturn = toReturn.replace("#LEARNER_EMAIL#", u.getEmail());
		}
		return toReturn;
	}
	
	private String replaceStringsArplInitiator(Users u, String description) {
		String toReturn = description;
		toReturn = toReturn.replace("#INITIATOR_IDENTITY_NUMBER#", anIDNumber(u));
		toReturn = toReturn.replace("#INITIATOR_FIRST_NAME#", u.getFirstName());
		toReturn = toReturn.replace("#INITIATOR_LAST_NAME#", u.getLastName());
		if (u.getEmail() != null) {
			toReturn = toReturn.replace("#INITIATOR_EMAIL#", u.getEmail());
		}
		return toReturn;
	}
	
	private String replaceStringsWorkplaceMonitoringSiteVisist(WorkplaceMonitoringSiteVisit c, String description){
		String toReturn = description;
		try {
			toReturn = toReturn.replace("#SITE_VISIT_DATE#", (c.getMonitoringDate() != null ? HAJConstants.sdfDDMMYYYY2.format(c.getMonitoringDate()) : "N/A"));
		} catch (Exception e) {
			logger.fatal(e);
		}
		return toReturn;
	}
	
	private String replaceStringsArplInformation(CompanyLearnersTradeTest c, String description) {
		String toReturn = description;
		try {
			toReturn = toReturn.replace("#DATE_SUBMISSION_ORIGINAL_DOC#", (c.getDateSubmissionOriginalDoc() != null ? HAJConstants.sdfDDMMYYYY2.format(c.getDateSubmissionOriginalDoc()) : "N/A"));
			toReturn = toReturn.replace("#SERIAL_NUMBER#", (c.getSerialNumber() != null && !c.getSerialNumber().isEmpty() ? c.getSerialNumber().trim() : "N/A"));
			toReturn = toReturn.replace("#FROM_DATE_OF_ASSESMENT#", (c.getDateOfTest() != null ? HAJConstants.sdfDDMMYYYY2.format(c.getDateOfTest()) : "N/A"));
			toReturn = toReturn.replace("#TO_DATE_OF_ASSESMENT#", (c.getDateOfTestToDate() != null ? HAJConstants.sdfDDMMYYYY2.format(c.getDateOfTestToDate()) : "N/A"));
		} catch (Exception e) {
			logger.fatal(e);
		}
		return toReturn;
	}
	
	private String replaceStringsArplTradeTestCenter(Company c, String description) {
		String toReturn = description;
		try {
			toReturn = toReturn.replace("#TTC_COMPANY_NAME#", c.getCompanyName());
			toReturn = toReturn.replace("#TTC_TRADING_NAME#", c.getTradingName() != null ? c.getTradingName() : "N/A");
			toReturn = toReturn.replace("#TTC_ENTITY_ID#", c.getLevyNumber() != null ? c.getLevyNumber() : "N/A");
			toReturn = toReturn.replace("#TTC_TEL_NUMBER#", c.getTelNumber() != null ? c.getTelNumber() : "N/A");
			toReturn = toReturn.replace("#TTC_COMPANY_EMAIL#", c.getEmail() != null ? c.getEmail() : "N/A");
		} catch (Exception ex) {
		}
		return toReturn;
	}


	private String replaceStringsUC(Users u, Company c, String description) {
		String toReturn = description;
		toReturn = replaceStringsUsers(u, toReturn);
		toReturn = replaceStringsCompany(c, toReturn);
		return toReturn;
	}
	
	private String replaceStringsCompanyOrUser(Users u, Company c, String description) {
		String toReturn = description;
		if(c !=null)
		{
			toReturn = toReturn.replace("#IDENTITY_NUMBER#", "");
			toReturn = toReturn.replace("#FIRST_NAME#", "");
			toReturn = toReturn.replace("#LAST_NAME#","");
			toReturn = toReturn.replace("#EMAIL#", "");
			toReturn = replaceStringsCompany(c, toReturn);
		}
		else
		{
			toReturn = toReturn.replace("#COMPANY_NAME#", "");
			toReturn = toReturn.replace("#TRADING_NAME#", "");
			toReturn = toReturn.replace("#ENTITY_ID#", "");
			toReturn = toReturn.replace("#TEL_NUMBER#","");
			toReturn = toReturn.replace("#COMPANY_EMAIL#","");
			toReturn = replaceStringsUsers(u, toReturn);
		}
		toReturn = toReturn.replace("()", "");
		return toReturn;
	}

	public String anIDNumber(Users user) {
		if (user.getRsaIDNumber() != null && user.getRsaIDNumber() != "" && !user.getRsaIDNumber().isEmpty()) {
			return user.getRsaIDNumber();
		}else if (user.getPassportNumber() != null && user.getPassportNumber() != "" && !user.getPassportNumber().isEmpty()) {
			return user.getPassportNumber();
		}else {
			return "N/A";
		}
	}
	
	private String replaceStringsSitesSme(SitesSme c, String description) {
		String toReturn = description;
		try {
			if(c.getSites() != null && c.getSites().getCompanyName()!= null) {
				toReturn = toReturn.replace("#SITE_NAME#", c.getSites().getCompanyName());	
			}else if(c.getCompany()!=null){
				toReturn = toReturn.replace("#SITE_NAME#", c.getCompany().getCompanyName());	
			}else {
				toReturn = toReturn.replace("#SITE_NAME#", "N/A");
			}
					
		} catch (Exception ex) {
		}
		return toReturn;
	}
	
	private String replaceStringsMgDgTransfer(GpGrantBatchList gpGrantBatchLis, String description) {
		String toReturn = description;
		try {
			if (gpGrantBatchLis.getWspType() == WspTypeEnum.Mandatory) {
				toReturn = toReturn.replace("#BATCH_NUMBER#", "GRANTS " + gpGrantBatchLis.getBatchNumber());
			} else if (gpGrantBatchLis.getWspType() == WspTypeEnum.Discretionary) {
				toReturn = toReturn.replace("#BATCH_NUMBER#", "DG " + gpGrantBatchLis.getBatchNumber());
			}
			toReturn = toReturn.replace("#BATCH_DESCRIPTION#", gpGrantBatchLis.getBatchDescription());
			toReturn = toReturn.replace("#BATCH_TYPE#", gpGrantBatchLis.getWspType().getFriendlyName());
		} catch (Exception ex) {
		}
		return toReturn;
	}
	
	private String replaceStringsClo(Users u, String description) {
		String toReturn = description;
		toReturn = toReturn.replace("#CLO_IDENTITY_NUMBER#", anIDNumber(u));
		toReturn = toReturn.replace("#CLO_FIRST_NAME#", u.getFirstName());
		toReturn = toReturn.replace("#CLO_LAST_NAME#", u.getLastName());
		toReturn = toReturn.replace("#CLO_EMAIL#", u.getEmail());
		return toReturn;
	}
	
	@SuppressWarnings("unused")
	private String replaceStringsCrm(Users u, String description) {
		String toReturn = description;
		toReturn = toReturn.replace("#CRM_IDENTITY_NUMBER#", anIDNumber(u));
		toReturn = toReturn.replace("#CRM_FIRST_NAME#", u.getFirstName());
		toReturn = toReturn.replace("#CRM_LAST_NAME#", u.getLastName());
		toReturn = toReturn.replace("#CRM_EMAIL#", u.getEmail());
		return toReturn;
	}
	
	public Users getCLO(Wsp wsp) {
		try {
			RegionTown rt = RegionTownService.instance().findByTown(wsp.getCompany().getResidentialAddress().getTown());
			Users cloUser = rt.getClo().getUsers();
			return cloUser;
		} catch (Exception e) {
			logger.fatal(e);
			return null;
		}
	}
	
	/*
	 *  #PROVIDER_ACCREDITATION_NUMBER# #PROVIDER_ACCREDITATION_START_DATE# #PROVIDER_ACCREDITATION_END_DATE#
	 *  This will need to be looked at for future proofing
	 */
	private String replaceStringsTrainingProvider(Company c, String description) {
		String toReturn = description;
		try {
			TrainingProviderApplicationService tpaService = new TrainingProviderApplicationService();
			if (c.getAccreditationNumber() == null) {
				toReturn = toReturn.replace("#PROVIDER_ACCREDITATION_NUMBER#", "N/A");
				toReturn = toReturn.replace("#PROVIDER_ACCREDITATION_START_DATE#", "N/A");
				toReturn = toReturn.replace("#PROVIDER_ACCREDITATION_END_DATE##", "N/A");
			} else {
				toReturn = toReturn.replace("#PROVIDER_ACCREDITATION_NUMBER#", c.getAccreditationNumber() != null ? c.getAccreditationNumber().trim() : "N/A");
				try {
					List<TrainingProviderApplication> applicationList = tpaService.findByAccreditationNumber(c.getAccreditationNumber());
					if (applicationList.size() != 0) {
						toReturn = toReturn.replace("#PROVIDER_ACCREDITATION_START_DATE#", applicationList.get(0).getStartDate() != null ? HAJConstants.sdfDDMMYYYY2.format(applicationList.get(0).getStartDate()) : "N/A");
						toReturn = toReturn.replace("#PROVIDER_ACCREDITATION_END_DATE##", applicationList.get(0).getExpiriyDate() != null ? HAJConstants.sdfDDMMYYYY2.format(applicationList.get(0).getExpiriyDate()) : "N/A");
					} else {
						toReturn = toReturn.replace("#PROVIDER_ACCREDITATION_START_DATE#", c.getTradingName() != null ? c.getTradingName() : "N/A");
						toReturn = toReturn.replace("#PROVIDER_ACCREDITATION_END_DATE##", c.getLevyNumber() != null ? c.getLevyNumber() : "N/A");
					}
				} catch (Exception e) {
					toReturn = toReturn.replace("#PROVIDER_ACCREDITATION_START_DATE#", c.getTradingName() != null ? c.getTradingName() : "N/A");
					toReturn = toReturn.replace("#PROVIDER_ACCREDITATION_END_DATE##", c.getLevyNumber() != null ? c.getLevyNumber() : "N/A");
				}
			}
			tpaService = null;
		} catch (Exception ex) {
		}
		return toReturn;
	}
	
	// #CREATE_USER_FIRST_NAME# #CREATE_USER_LAST_NAME# #PREVIOUS_LAST_NAME# #PREVIOUS_FIRST_NAME# #PREVIOUS_IDENTITY_NUMBER# #PREVIOUS_EMAIL# #BATCH_NUMBER#
	private String replaceStringsDgContractBulkWorkflow(DgContractingBulkEntry dgContractingBulkEntry, String description) {
		String toReturn = description;
		try {
			toReturn = toReturn.replace("#BATCH_NUMBER#", dgContractingBulkEntry.getBatchNumber().toString());
		} catch (Exception ex) {
		}
		return toReturn;
	}
}
