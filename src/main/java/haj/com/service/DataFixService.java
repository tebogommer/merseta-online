package haj.com.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import haj.com.constants.HAJConstants;
import haj.com.dao.BlankDAO;
import haj.com.entity.ActiveContractDetail;
import haj.com.entity.ActiveContracts;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.AssessorModeratorCompanySites;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.CompanyUsers;
import haj.com.entity.DgAllocationParent;
import haj.com.entity.DgContractingBulkItems;
import haj.com.entity.ExtensionRequest;
import haj.com.entity.ProjectImplementationPlan;
import haj.com.entity.SDFCompany;
import haj.com.entity.SDPCompany;
import haj.com.entity.SDPExtensionOfScope;
import haj.com.entity.SitesSme;
import haj.com.entity.TaskUsers;
import haj.com.entity.Tasks;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.WspSignoff;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AssessorModType;
import haj.com.entity.enums.AssessorModeratorAppTypeEnum;
import haj.com.entity.enums.AssessorModeratorTypeEnum;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.TaskStatusEnum;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.entity.lookup.AuditorMonitorReview;
import haj.com.entity.lookup.LegacyApprenticeship;
import haj.com.entity.lookup.LegacyBursary;
import haj.com.entity.lookup.LegacyExperiential;
import haj.com.entity.lookup.LegacyInternship;
import haj.com.entity.lookup.LegacyLearnership;
import haj.com.entity.lookup.LegacySECTTwentyEight;
import haj.com.entity.lookup.LegacySkillsProgramme;
import haj.com.entity.lookup.LegacyTvet;
import haj.com.entity.lookup.LegacyUnitStandard;
import haj.com.entity.lookup.SdpType;
import haj.com.entity.lookup.Town;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.report.bean.ByChamberReportBean;
import haj.com.service.lookup.AuditorMonitorReviewService;
import haj.com.service.lookup.LegacyApprenticeshipService;
import haj.com.service.lookup.LegacyBursaryService;
import haj.com.service.lookup.LegacyInternshipService;
import haj.com.service.lookup.LegacyLearnershipService;
import haj.com.service.lookup.LegacySECTTwentyEightService;
import haj.com.service.lookup.LegacySkillsProgrammeService;
import haj.com.service.lookup.LegacyUnitStandardService;
import haj.com.service.lookup.SdpTypeService;
import haj.com.service.lookup.TownService;
import haj.com.utils.GenericUtility;

public class DataFixService extends AbstractService {

	/** Dao Level */
	private BlankDAO dao = new BlankDAO();

	/** Service Levels */
	private ScheduleChangesLogService scheduleChangesLogService = new ScheduleChangesLogService();
	private TasksService tasksService = new TasksService();
	private DgAllocationParentService dgAllocationParentService = new DgAllocationParentService();
	private CompanyService companyService = new CompanyService();
	private WspService wspService = new WspService();
	private SDFCompanyService sdfCompanyService = new SDFCompanyService();
	private UsersService usersService = new UsersService();
	private WspSignoffService wspSignoffService = new WspSignoffService();
	private MandatoryGrantDetailArchiveService grantDetailArchiveService = new MandatoryGrantDetailArchiveService();
	private ActiveContractsService activeContractsService;
	private ProjectImplementationPlanService projectImplementationPlanService;
	private ExtensionRequestService extensionRequestService;
	private MandatoryGrantDetailService mandatoryGrantDetailService;
	private DgVerificationService dgVerificationService;
	private MgVerificationService mgVerificationService;
	private SitesSmeService sitesSmeService;
	private SmeQualificationsService smeQualificationsService;
	private WorkPlaceApprovalService workPlaceApprovalService;
	private DgContractingBulkEntryService dgContractingBulkEntryService;
	private DgContractingBulkItemsService dgContractingBulkItemsService;
	private ActiveContractDetailService activeContractDetailService;
	private SDPExtensionOfScopeService sdpExtensionOfScopeService;
	private AuditorMonitorReviewService auditorMonitorReviewService;
	private CompanyLearnersService companyLearnersService;
	private TownService townService;
	private TrainingProviderApplicationService trainingProviderApplicationService;
	private QmrFinYearsService qmrFinYearsService = null;
	private ProjectImplementationPlanLearnersService projectImplementationPlanLearnersService = null;
	private AssessorModeratorApplicationService assessorModeratorApplicationService = null;
	
	private CompanyUsersService companyUsersService = null;
	private SDPCompanyService sdpCompanyService = null;
	private AssessorModeratorCompanySitesService assessorModeratorCompanySitesService = null;
	
	/** Simple Date Formats */
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");

	public void fixAllocationTasks(List<String> companyLNumbers, Long previousTaskID, Long createUserId) throws Exception {

		Tasks previousTask = tasksService.findByKey(previousTaskID);
		Users createUserOfPreviousTask = usersService.findByKey(createUserId);

		for (String lNumber : companyLNumbers) {

			List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
			Company comp = companyService.findByLevyNum(lNumber.trim()).get(0);
			Wsp wsp = wspService.findByCompanyAndFinancialYear(comp.getId(), 2019).get(0);
			DgAllocationParent dgParent = dgAllocationParentService.findByWSP(wsp.getId());

			SDFCompany primarySdf = sdfCompanyService.findPrimarySDF(comp);
			List<Users> taskUsers = new ArrayList<>();
			if (primarySdf != null) {
				taskUsers.add(primarySdf.getSdf());
			}
			String desc = "A Discretionary Grant allocation for company " + comp.getCompanyNameDisplay()
					+ " has been processed. Please review.";
			if (taskUsers.size() != 0) {

				// for (Users users : taskUsers) {
				// desc =
				// FixTaskDataService.instance().replaceStrings(DgAllocationParent.class.getName(),
				// desc, dgParent.getId(), createUserOfPreviousTask);
				// Tasks tasks = new Tasks(desc, createUserOfPreviousTask,
				// dgParent.getId(), DgAllocationParent.class.getName(),
				// TaskStatusEnum.NotStarted,
				// GenericUtility.add3DaysCountingFromStartDate(getSynchronizedDate()),
				// UUID.randomUUID().toString());
				// tasks.setWorkflowProcess(ConfigDocProcessEnum.DG_ALLOCATION);
				// tasks.setPreviousTask(previousTask);
				// tasks.setFirstInProcess(true);
				// dataEntities.add(tasks);
				// dataEntities.add(new TaskUsers(users, tasks));
				// }

				TasksService.instance().createTaskUser(taskUsers, DgAllocationParent.class.getName(), dgParent.getId(),
						desc, createUserOfPreviousTask, false, true, previousTask, ConfigDocProcessEnum.DG_ALLOCATION,
						true);
			} else {
				TasksService.instance().createTaskUserDgAllocationFallBack(taskUsers,
						DgAllocationParent.class.getName(), dgParent.getId(), desc, createUserOfPreviousTask, false,
						true, previousTask, ConfigDocProcessEnum.DG_ALLOCATION, true);
				GenericUtility.mailError("DG Allocation, Manual Generation Of Missing Task",
						"<p>Primary SDF not found for company: " + comp.getCompanyNameDisplay()
								+ ".</p> However task created.<p> Description for task: " + desc
								+ "</p><p> Refer to: haj.com.service.DataFixService.fixAllocationTasks(List<String> companyLNumbers, Long previousTaskID, Long createUserId) </p>");
			}
			// if (dataEntities.size() != 0) {
			// dao.createBatch(dataEntities);
			// if (taskUsers.size() != 0) {
			//
			// }
			// }
			// Tasks newTask = new Tasks(description, createUser, targetKey,
			// targetClass, taskStatus, dueDate, guid)
		}
	}

	public List<Wsp> locateDocumentUploadTaskToMerSETA() throws Exception {
		List<Wsp> wspFound = new ArrayList<>();
		List<Tasks> allDocumentUploadTasksNotCompleted = tasksService.findOpenTasksByProcessRoleId(63l);
		for (Tasks tasks : allDocumentUploadTasksNotCompleted) {
			Wsp wsp = wspService.findByKey(tasks.getTargetKey());
			if (wsp.getWspStatusEnum() != WspStatusEnum.Approved && wsp.getWspStatusEnum() != WspStatusEnum.Rejected) {
				wspFound.add(wsp);
			}

		}
		return wspFound;
	}

	public void sendDocumentUploadTaskToMerSETA() throws Exception {
		Users user = usersService.findByKey(1l);
		List<Tasks> allDocumentUploadTasksNotCompleted = tasksService.findOpenTasksByProcessRoleId(63l);
		for (Tasks task : allDocumentUploadTasksNotCompleted) {
			Wsp wsp = wspService.findByKey(task.getTargetKey());
			if (wsp.getWspStatusEnum() != WspStatusEnum.Approved && wsp.getWspStatusEnum() != WspStatusEnum.Rejected) {
				String message = "Grant Application for " + wsp.getCompany().getCompanyName()
						+ " has been submitted and with a sign off upload please review.";
				TasksService.instance().findFirstInProcessAndCreateTask(message, user, wsp.getId(),
						wsp.getClass().getName(), true, ConfigDocProcessEnum.WSP,
						MailDef.instance(MailEnum.WSPSubmittedAndSignedReview, MailTagsEnum.CompanyName,
								wsp.getCompany().getCompanyName()),
						null);
				TasksService.instance().completeTask(task);
			}
		}
	}

	public void reopenWspForAppeal(long wspId, Users user) throws Exception {

		String information = "Re-opened Mandatory Grant for the Appeal Process as per request. Mandatory Grant / WSP ID manually enetered: "
				+ wspId + ". User who initicated manuall process: " + user.getFirstName() + " " + user.getLastName()
				+ " ( User DB ID: " + user.getId() + " ).";

		Wsp originalWsp = wspService.findByKey(wspId);
		Wsp wsp = wspService.findByKey(wspId);
		wsp.setWithSdf(true);
		wsp.setSdfAppealedGrant(false);
		wsp.setGrantRejected(true);
		wsp.setGrantRejectedDate(getSynchronizedDate());
		wsp.setWspStatusEnum(WspStatusEnum.Pending);
		wsp.setApprovedDate(null);

		information += " DATA CHANGES -  ";
		information += " With SDF: from " + originalWsp.getWithSdf() + " to " + wsp.getWithSdf() + " ";
		information += ", SDF Appeladed Grant: from " + originalWsp.getSdfAppealedGrant() + " to "
				+ wsp.getSdfAppealedGrant() + " ";
		information += ", SDF Grant Rejection: from " + originalWsp.getGrantRejected() + " to " + wsp.getGrantRejected()
				+ " ";
		information += ", WSP Grant Rejection Date: from "
				+ ((originalWsp.getGrantRejectedDate() == null) ? "Not Set"
						: simpleDateFormat.format(originalWsp.getGrantRejectedDate()))
				+ " to " + ((wsp.getGrantRejectedDate() == null) ? "Not Set"
						: simpleDateFormat.format(wsp.getGrantRejectedDate()))
				+ " ";
		information += ", With SDF: from " + originalWsp.getWspStatusEnum().getFriendlyName() + " to "
				+ wsp.getWspStatusEnum().getFriendlyName() + " ";
		information += ", Approved Date: from " + ((originalWsp.getApprovedDate() == null) ? "Not Set"
				: simpleDateFormat.format(originalWsp.getApprovedDate())) + " to NULL ";

		originalWsp = null;
		wspService.update(wsp);

		information += ". Task to be manually reopened on the database. All tasks linked to workflow and wsp are closed.";
		TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.WSP, wsp.getId());
		ScheduleChangesLogService.instance().addNewEntry(wsp.getCompany(), null, wsp.getClass().getName(), wsp.getId(),
				information, true);

		Company company = companyService.findByKey(wsp.getCompany().getId());
		SDFCompany primarySDF = companyService.findPrimarySDF(company);
		if (primarySDF != null && primarySDF.getSdf() != null) {
			List<Tasks> wsptasksOrderByCreateDateDesc = TasksService.instance()
					.findTasksByTypeAndKey(Wsp.class.getName(), wsp.getId());
			if (wsptasksOrderByCreateDateDesc.size() != 0) {
				Tasks task = wsptasksOrderByCreateDateDesc.get(0);
				if (TasksService.instance().countTasksUsersByTask(task.getId()) == 0) {
					task.setDueDate(
							GenericUtility.getStartOfDay(GenericUtility.addDaysToDate(getSynchronizedDate(), 14)));
					task.setTaskStatus(TaskStatusEnum.NotStarted);
					dao.update(task);
					TaskUsers tu = new TaskUsers(primarySDF.getSdf(), task);
					dao.create(tu);
				} else {
					throw new Exception("Error when finding task users, user(s) found for WSP ID: " + wsp.getId()
							+ " and the task id is: " + task.getId());
				}
			} else {
				throw new Exception(
						"Unable to locate tasks for wsp however wsp was re-opened. Manually redo task for WSP ID:"
								+ wsp.getId());
			}
		} else {
			throw new Exception(
					"Unable to locate primary SDF for company however WSP was reopend for WSP ID: " + wsp.getId());
		}
	}

	public void reopenWspForFinalApprovalMerseta(long wspId, Users user) throws Exception {

		String information = "Re-opened Mandatory Grant for the Final Approval Process for Merseta as per request. Mandatory Grant / WSP ID manually enetered: "
				+ wspId + ". User who initicated process: " + user.getFirstName() + " " + user.getLastName()
				+ " ( User DB ID: " + user.getId() + " ).";

		Wsp originalWsp = wspService.findByKey(wspId);
		Wsp wsp = wspService.findByKey(wspId);
		wsp.setWithSdf(false);
		wsp.setSdfAppealedGrant(false);
		wsp.setGrantRejected(false);
		wsp.setGrantRejectedDate(null);
		wsp.setWspStatusEnum(WspStatusEnum.Pending);
		wsp.setApprovedDate(null);

		information += " DATA CHANGES -  ";
		information += " With SDF: from " + originalWsp.getWithSdf() + " to " + wsp.getWithSdf() + " ";
		information += ", SDF Appeladed Grant: from " + originalWsp.getSdfAppealedGrant() + " to "
				+ wsp.getSdfAppealedGrant() + " ";
		information += ", SDF Grant Rejection: from " + originalWsp.getGrantRejected() + " to " + wsp.getGrantRejected()
				+ " ";
		information += ", WSP Grant Rejection Date: from "
				+ ((originalWsp.getGrantRejectedDate() == null) ? "Not Set"
						: simpleDateFormat.format(originalWsp.getGrantRejectedDate()))
				+ " to "
				+ ((wsp.getGrantRejectedDate() == null) ? "NULL" : simpleDateFormat.format(wsp.getGrantRejectedDate()))
				+ " ";
		information += ", With SDF: from " + originalWsp.getWspStatusEnum().getFriendlyName() + " to "
				+ wsp.getWspStatusEnum().getFriendlyName() + " ";
		information += ", Approved Date: from " + ((originalWsp.getApprovedDate() == null) ? "Not Set"
				: simpleDateFormat.format(originalWsp.getApprovedDate())) + " to NULL ";

		originalWsp = null;
		wspService.update(wsp);
		information += ". Task to be manually reopened on the database. All tasks linked to workflow and wsp are closed.";
		TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.WSP, wsp.getId());
		ScheduleChangesLogService.instance().addNewEntry(wsp.getCompany(), null, wsp.getClass().getName(), wsp.getId(),
				information, true);
		WspCompanyHistoryCreateService.instance().clearExistingEntriesByTargetClassAndKey(wsp.getClass().getName(),
				wsp.getId());
	}

	public void setAcceptedForWspSignOff(long wspSignOffId, Users user) throws Exception {
		WspSignoff wso = wspSignoffService.findByKey(wspSignOffId);
		if (wso != null) {
			Wsp wsp = wspService.findByKey(wso.getWsp().getId());
			if (wsp != null) {
				Users signoffUser = usersService.findByKey(wso.getUser().getId());
				Company company = companyService.findByKey(wsp.getCompany().getId());
				String information = "Manual Fix to data regarding error in accpeted display on Wsp Sign off. Changed Accepted From: "
						+ ((wso.getAccept() == null) ? "NULL" : wso.getAccept()) + " To True for User: "
						+ signoffUser.getFirstName() + " " + signoffUser.getLastName() + ". Date Signed Off: "
						+ ((wso.getSignOffDate() == null) ? " No Date Captured "
								: simpleDateFormat.format(wso.getSignOffDate()))
						+ ". For grant year: " + wsp.getFinYear() + " and Company: " + company.getCompanyName() + " ( "
						+ company.getLevyNumber() + " ). Closed all open tasks assigned to WSP. ";
				wso.setAccept(true);
				wspSignoffService.update(wso);
				TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.WSP, wsp.getId());
				ScheduleChangesLogService.instance().addNewEntry(wsp.getCompany(), null, wsp.getClass().getName(),
						wsp.getId(), information, true);
				company = null;
				signoffUser = null;
			} else {
				throw new Exception("Unable to locate Wsp Assigned To WSP Sign Off ID: " + wso.getId());
			}
			wsp = null;
		} else {
			throw new Exception("Unable to locate Wsp Sign Off For ID Entered: " + wspSignOffId);
		}
		wso = null;
	}

	public void sendWspToMersetaForApproval(long wspId, Users user) throws Exception {
		Wsp wsp = wspService.findByKey(wspId);
		if (wsp != null) {
			List<WspSignoff> wsoList = wspSignoffService.findByWspLastestFirst(wsp);
			if (wsoList.size() != 0) {
				Users lastestSignOffUser = null;
				for (WspSignoff wspSignoff : wsoList) {
					if (wspSignoff.getUser() != null && wspSignoff.getSignOffDate() != null) {
						lastestSignOffUser = usersService.findByKey(wspSignoff.getUser().getId());
						break;
					}
				}
				if (lastestSignOffUser != null && lastestSignOffUser.getId() != null) {
					wsp.setWspStatusEnum(WspStatusEnum.Pending);
					Company company = companyService.findByKey(wsp.getCompany().getId());
					String information = "Error Fix: Wsp Task has been sent to MerSETA to emulate SDF submission after sign off from the client companies side. Wsp Grant Year: "
							+ wsp.getFinYear() + " For Company: " + company.getCompanyName() + " ( "
							+ company.getLevyNumber() + " ) ";
					wspService.update(wsp);
					TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.WSP, wsp.getId());
					String message = "Grant Application for " + wsp.getCompany().getCompanyName()
							+ " has been submitted and signed off please review.";
					TasksService.instance().findFirstInProcessAndCreateTask(message, user, wsp.getId(),
							wsp.getClass().getName(), true, ConfigDocProcessEnum.WSP,
							MailDef.instance(MailEnum.WSPRequiresSignOff, MailTagsEnum.CompanyName,
									wsp.getCompany().getCompanyName()),
							null);
					ScheduleChangesLogService.instance().addNewEntry(wsp.getCompany(), null, wsp.getClass().getName(),
							wsp.getId(), information, true);
					WspCompanyHistoryCreateService.instance()
							.clearExistingEntriesByTargetClassAndKey(wsp.getClass().getName(), wsp.getId());
					WspCompanyHistoryCreateService.instance().populateWspCompanyHistory(wsp.getCompany(),
							wsp.getClass().getName(), wsp.getId());
				} else {
					throw new Exception("Unable to Locate Sign Off User for Wsp ID: " + wsp.getId());
				}
				lastestSignOffUser = null;
			} else {
				throw new Exception("Unable to Wsp Sign Off List for Wsp ID: " + wsp.getId());
			}
			wsoList = null;
		} else {
			throw new Exception("Unable to locate Wsp ID Entered: " + wspId);
		}
		wsp = null;
	}

	public void linkActiveContractToPIP() throws Exception {
		// get service levels required for method
		if (projectImplementationPlanService == null) {
			projectImplementationPlanService = new ProjectImplementationPlanService();
		}
		if (activeContractsService == null) {
			activeContractsService = new ActiveContractsService();
		}
		// locate pip not assigned to a active contract
		List<ProjectImplementationPlan> pipNotAssignedList = projectImplementationPlanService
				.allProjectImplementationPlanNotLinkedToActiveContracts();
		List<IDataEntity> updateList = new ArrayList<IDataEntity>();
		for (ProjectImplementationPlan projectImplementationPlan : pipNotAssignedList) {
			// ensure wsp assigned, not really required
			if (projectImplementationPlan.getWsp() != null && projectImplementationPlan.getWsp().getId() != null
					&& projectImplementationPlan.getActiveContracts() == null) {
				ActiveContracts ac = activeContractsService.findByWSP(projectImplementationPlan.getWsp().getId());
				if (ac != null && ac.getId() != null) {
					projectImplementationPlan.setActiveContracts(ac);
					updateList.add(projectImplementationPlan);
				}
				ac = null;
			}
		}

		// updates all entries that did not have an active contract assigned to
		// them
		if (updateList.size() != 0) {
			dao.updateBatch(updateList, 500);
		}

		pipNotAssignedList = null;
		projectImplementationPlanService = null;
		activeContractsService = null;
	}

	public void archiveRecords() throws Exception {
		grantDetailArchiveService.archiveEntriesNotImportedByYear();
	}

	public void deleteFailedImportedEntriesByWspID(Long wspId, Integer batchSize, Long sessionUserId) throws Exception {
		if (mandatoryGrantDetailService == null) {
			mandatoryGrantDetailService = new MandatoryGrantDetailService();
		}
		int count = mandatoryGrantDetailService.countMandatoryGrantDetailFailedImportedByWspId(wspId);
		while (count > 0) {
			mandatoryGrantDetailService.deleteMandatoryGrantDetailFailedImportedByWspId(wspId, batchSize,
					sessionUserId);
			count = mandatoryGrantDetailService.countMandatoryGrantDetailFailedImportedByWspId(wspId);
		}
		mandatoryGrantDetailService = null;
	}

	public void createTaskForExtensionRequest(Long extensionRequestId, Users sessionUser) throws Exception {
		if (extensionRequestService == null) {
			extensionRequestService = new ExtensionRequestService();
		}
		ExtensionRequest entity = extensionRequestService.findByKey(extensionRequestId);
		if (entity != null && entity.getId() != null) {
			String desc = "Extension request has been submitted.";
			Users submissionUser = null;
			if (entity.getUser() != null && entity.getUser().getId() != null) {
				submissionUser = usersService.findByKey(entity.getUser().getId());
			} else {
				submissionUser = usersService.findByKey(sessionUser.getId());
			}
			TasksService.instance().findFirstInProcessAndCreateTask(desc, submissionUser, entity.getId(),
					entity.getClass().getName(), true, ConfigDocProcessEnum.EXTENSION_REQUEST,
					MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
		} else {
			throw new Exception("Unable to locate extension request by id: " + extensionRequestId);
		}
	}

	public void generateDgVerificationByWspID(long wspId, Users user) throws Exception {
		// Populate Service Levels
		if (wspService == null) {
			wspService = new WspService();
		}
		if (dgVerificationService == null) {
			dgVerificationService = new DgVerificationService();
		}

		Wsp wsp = wspService.findByKey(wspId);
		if (wsp != null) {
			dgVerificationService.generateForWspAdmin(wsp);
		} else {
			throw new Exception("Unable to locate Wsp ID Entered: " + wspId);
		}
		wsp = null;
	}

	public void generateDgVerificationByWspIDScript(Users user) throws Exception {
		// Populate Service Levels
		if (wspService == null) {
			wspService = new WspService();
		}
		if (dgVerificationService == null) {
			dgVerificationService = new DgVerificationService();
		}
		List<ByChamberReportBean> wspIdList = wspService.locateWspIDsThatNeedGenerationQuickFix();
		if (wspIdList.size() != 0) {
			for (ByChamberReportBean byChamberReportBean : wspIdList) {
				if (byChamberReportBean.getIdPassed() != null) {
					Wsp wsp = wspService.findByKey(byChamberReportBean.getIdPassed().longValue());
					if (wsp != null) {
						dgVerificationService.generateForWspAdmin(wsp);
					} else {
						throw new Exception(
								"Unable to locate Wsp ID Entered: " + byChamberReportBean.getIdPassed().longValue());
					}
					wsp = null;
				}
			}
		} else {
			throw new Exception("No Entries To Process");
		}
	}

	public void generateDgVerificationForSmallCompaniesRejectedWsp(WspStatusEnum wspStatus, Integer finYear)
			throws Exception {
		// populate service levels
		if (wspService == null) {
			wspService = new WspService();
		}
		if (dgVerificationService == null) {
			dgVerificationService = new DgVerificationService();
		}
		if (companyService == null) {
			companyService = new CompanyService();
		}
		List<Wsp> rejectedWspsForFinYear = wspService.findWspByStatusAndFinYear(wspStatus, finYear);
		for (Wsp wsp : rejectedWspsForFinYear) {
			Company company = companyService.findByKey(wsp.getCompany().getId());
			// small companies less than 50 employees
			if (company != null && company.getId() != null && company.getNumberOfEmployees() != null
					&& company.getNumberOfEmployees() < 50) {
				dgVerificationService.generateForRejectedWspSmallCompany(wsp);
			}
			company = null;
		}
		rejectedWspsForFinYear = null;
	}

	public void generateMgVerificationByFinYear(Long finYear) throws Exception {
		if (mandatoryGrantDetailService == null) {
			mandatoryGrantDetailService = new MandatoryGrantDetailService();
		}
		mandatoryGrantDetailService.createMGVerificationDataVersionTwoByFinYear(finYear);
	}

	public void fixSitesSme(long id, Users admin) throws Exception {
		if (sitesSmeService == null) {
			sitesSmeService = new SitesSmeService();
		}
		if (smeQualificationsService == null) {
			smeQualificationsService = new SmeQualificationsService();
		}
		SitesSme sitesSme = sitesSmeService.findByKey(id);
		Tasks openenedTasks = new Tasks();

		if (sitesSme != null && sitesSme.getId() != null) {
			/*
			 * if(sitesSme.getStatus() == ApprovalEnum.Approved ) { if
			 * (workPlaceApprovalService == null) { workPlaceApprovalService =
			 * new WorkPlaceApprovalService(); }
			 * List<WorkPlaceApprovalSites>list =
			 * workPlaceApprovalService.findByMentor(sitesSme); if(list != null
			 * && list.size() > 0) { for(WorkPlaceApprovalSites
			 * workPlaceApprovalSites : list) { WorkPlaceApproval wpa =
			 * workPlaceApprovalSites.getWorkPlaceApproval();
			 * 
			 * } } }
			 */
			if (sitesSme.getStatus() == ApprovalEnum.Approved || sitesSme.getStatus() == ApprovalEnum.PendingApproval) {
				List<Tasks> allTaskSitesSme = tasksService.findTasksByTypeAndKey(
						ConfigDocProcessEnum.SITE_SME_REGISTRATION, SitesSme.class.getName(), sitesSme.getId());
				if (allTaskSitesSme != null && allTaskSitesSme.size() > 0) {
					openenedTasks = allTaskSitesSme.get(0);
				}
				if (allTaskSitesSme != null && allTaskSitesSme.size() > 1) {
					for (Tasks task : allTaskSitesSme) {
						tasksService.completeTask(task);
					}
				}
				sitesSme.setSmeQualificationsList(smeQualificationsService.findBySme(sitesSme));
				sitesSmeService.systemRejectWorkflow(sitesSme, admin, openenedTasks);
			} else {
				throw new Exception("Mentor already being rejected");
			}
		} else {
			throw new Exception("Mentor with that ID is Not avalible");
		}

	}

	public void populateWspCompanyHistoryForLastestGrantYear() throws Exception {
		WspCompanyHistoryCreateService.instance().populateWspCompanyHistoryForAllWsp();
	}

	public void generateTranchOnePaymentForApprovedMoa() throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {

				try {

					if (usersService == null) {
						usersService = new UsersService();
					}
					if (activeContractsService == null) {
						activeContractsService = new ActiveContractsService();
					}
					if (dgContractingBulkEntryService == null) {
						dgContractingBulkEntryService = new DgContractingBulkEntryService();
					}
					if (dgContractingBulkItemsService == null) {
						dgContractingBulkItemsService = new DgContractingBulkItemsService();
					}
					if (activeContractDetailService == null) {
						activeContractDetailService = new ActiveContractDetailService();
					}

					// locate active contracts ready for tranch one payments
					List<DgContractingBulkItems> approvedActiveContracts = dgContractingBulkItemsService.findApprovedBulkItemsToGenerateTranchPayments();
					for (DgContractingBulkItems bulkItem : approvedActiveContracts) {
						if ((bulkItem.getActiveContracts() != null && bulkItem.getActiveContracts().getId() != null) && (bulkItem.getApprovalUser() != null && bulkItem.getApprovalUser().getId() != null)) {
							ActiveContracts activeContract = activeContractsService.findByKey(bulkItem.getActiveContracts().getId());
							Users approveUser = usersService.findByKey(bulkItem.getApprovalUser().getId());
							// check if active contract already on active contract detail level
							if (activeContract != null && activeContract.getId() != null && activeContractDetailService.countActiveContractDetailByActiveContract(bulkItem.getActiveContracts().getId()) == 0) {
								activeContractsService.dataFixPayTrancheOne(activeContract, approveUser);
								approveUser = null;
								activeContract = null;
							}
						}
					}
					approvedActiveContracts= null;
					for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(emailNotificiations, "generateTranchOnePaymentForApprovedMoa Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
					}
					
				} catch (Exception e) {
					logger.fatal(e);
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					GenericUtility.mailError("Error On generateTranchOnePaymentForApprovedMoa","<p>Error On generateTranchOnePaymentForApprovedMoa.</p><p> Stack Trace:<p/><p> " + exceptionAsString + "</p>");
				}

			}
		}).start();

	}
	
	public void fixToActiveContractDetail(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				Boolean updateComplete = false;
				Boolean deleteComplete = false;
				try {
					List<IDataEntity> updateList = new ArrayList<>();
					List<IDataEntity> deleteList = new ArrayList<>();
					
					if (activeContractDetailService == null) {
						activeContractDetailService = new ActiveContractDetailService();
					}
					if (activeContractsService == null) {
						activeContractsService = new ActiveContractsService();
					}
					if (projectImplementationPlanService == null) {
						projectImplementationPlanService = new ProjectImplementationPlanService();
					}
					
					List<ActiveContractDetail> allActiveContractDetail = activeContractDetailService.allActiveContractDetail();
					for (ActiveContractDetail activeContractDetail : allActiveContractDetail) {
						ActiveContracts linkedActiveContractOnLevel = null;
						ProjectImplementationPlan pipLinked = null;
						ActiveContracts pipActiveContract  = null;
						
						// locate currently linked active contracts
						if (activeContractDetail.getActiveContracts() != null) {
							linkedActiveContractOnLevel = activeContractsService.findByKey(activeContractDetail.getActiveContracts().getId());
						}
						// locate linked PIP
						if (activeContractDetail.getProjectImplementationPlan() != null) {
							pipLinked = projectImplementationPlanService.findByKey(activeContractDetail.getProjectImplementationPlan().getId());
						}
						// Locate Active Contract on PIP level
						if (pipLinked != null && pipLinked.getActiveContracts() != null) {
							pipActiveContract = activeContractsService.findByKey(pipLinked.getActiveContracts().getId()); 
						}
						
						if (pipLinked != null && pipActiveContract != null && linkedActiveContractOnLevel != null) {
							// check if correct active contract on pip level and current level
							if (!pipActiveContract.getId().equals(linkedActiveContractOnLevel.getId())) {
								// The correct active contract that should be used is on the PIP level as that is what tranch is linked to
								activeContractDetail.setActiveContracts(pipActiveContract);
								updateList.add(activeContractDetail);
								if (projectImplementationPlanService.countByActiveContract(linkedActiveContractOnLevel) == 0) {
									deleteList.add(linkedActiveContractOnLevel);
								}
							}
						}
						linkedActiveContractOnLevel = null;
						pipLinked = null;
						pipActiveContract  = null;
					}
					
					// update to correct information
					if (updateList.size() != 0) {
						dao.updateBatch(updateList);
						updateComplete = true;
					}
					
					// delete duplicate entries
					if (deleteList.size() != 0) {
						dao.deleteBatch(deleteList);
						deleteComplete = true;
					}
					for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(emailNotificiations, "fixToActiveContractDetail Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
					}
				} catch (Exception e) {
					logger.fatal(e);
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					GenericUtility.mailError("Error On fixToActiveContractDetail","<p>Error On fixToActiveContractDetail.</p><p>Update Completed: "+updateComplete.toString()+",Delete Complete: "+deleteComplete.toString()+".  Stack Trace:<p/><p> " + exceptionAsString + "</p>");
				}

			}
		}).start();
	}
	
	public void fixDataActiveContracts() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (activeContractsService == null) {
						activeContractsService = new ActiveContractsService();
					}
					if (dgContractingBulkItemsService == null) {
						dgContractingBulkItemsService = new DgContractingBulkItemsService();
					}
					
					List<IDataEntity> updateList = new ArrayList<>();
					
					List<ActiveContracts> activeContractsList = activeContractsService.findAllEletronicSignoff(true);
					for (ActiveContracts activeContracts : activeContractsList) {
						boolean update = false;
						if (activeContracts.getExtensionTerminationWorkflowActive() == null) {
							activeContracts.setExtensionTerminationWorkflowActive(false);
							update = true;
						}
						
						if (activeContracts.getDeadlineDate() == null) {
							activeContracts.setDeadlineDate(GenericUtility.getStartOfDay(GenericUtility.addDaysToDateExcludeWeekends(activeContracts.getCreateDate(), 30)));
							update = true;
						}
						
						if (dgContractingBulkItemsService.countApprovedActiveContractsByActiveContractId(activeContracts.getId()) != 0) {
							activeContracts.setSubmitted(true);
							update = true;
						} else if ((activeContracts.getSignOffState() != null && activeContracts.getSignOffState()) && (activeContracts.getAwaitingBatchSignOff() != null && activeContracts.getAwaitingBatchSignOff())) {
							activeContracts.setSubmitted(true);
							update = true;
						} else {
							activeContracts.setSubmitted(false);
							update = true;
						}
						
						if (update) {
							updateList.add(activeContracts);
						}
					}
			
					if (updateList.size() != 0) {
						dao.updateBatch(updateList);
					}	
					for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(emailNotificiations, "fixDataActiveContracts Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
					}
				} catch (Exception e) {
					logger.fatal(e);
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					GenericUtility.mailError("Error On fixDataActiveContracts","<p>Error On fixDataActiveContracts.</p><p>Stack Trace:<p/><p> " + exceptionAsString + "</p>");
				}
			}
		}).start();
	}
	
	public void populateUserCitizenType() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (usersService == null) {
						usersService = new UsersService();
					}
					for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(emailNotificiations, "populateUserCitizenType Process Started", "Processing Started on site: " + HAJConstants.PL_LINK, null);
					}
					List<Users> usersList = usersService.findUsersWhereRsaIdIsPopulatedAndRsaCitizenTypeIsNull();
					for (Users users : usersList) {
						try {
							if (users.getRsaIDNumber() != null && !users.getRsaIDNumber().isEmpty()) {
								GenericUtility.calcIDDataRsaCitizien(users);
							}
							usersService.update(users);
						} catch (Exception e) {
							logger.fatal(e);
						}
					}
					for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(emailNotificiations, "populateUserCitizenType Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
					}
				} catch (Exception e) {
					logger.fatal(e);
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					GenericUtility.mailError("Error On populateUserCitizenType","<p>Error On populateUserCitizenType.</p><p>Stack Trace:<p/><p> " + exceptionAsString + "</p>");
				}
			}
		}).start();
	}
	
	public void deleteDuplicateApprovedContracts() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
						
					if (dgContractingBulkItemsService == null) {
						dgContractingBulkItemsService = new DgContractingBulkItemsService();
					}
					if (activeContractsService == null) {
						activeContractsService = new ActiveContractsService();
					}
					
					List<IDataEntity> deleteList = new ArrayList<>();
					
					List<ActiveContracts> allApprovedActiveContracts = dgContractingBulkItemsService.allApprovedActiveContracts();
					int counterOfEntriesToBeDeleted = 0;
					
					for (ActiveContracts activeContracts : allApprovedActiveContracts) {
						ActiveContracts ac = activeContractsService.findByKey(activeContracts.getId());
						if (ac != null && dgContractingBulkItemsService.countByActiveContractId(ac.getId()) > 1) {
							List<DgContractingBulkItems> entriesList = dgContractingBulkItemsService.findByActiveContractId(ac.getId());
							// double check
							if (entriesList.size() > 1) {
								// delete 2nd item in list
								DgContractingBulkItems item = null;
								try {
									item = entriesList.get(1);
								} catch (Exception e) {
									logger.fatal(e);
								}
								if (item != null && item.getId() != null) {
									deleteList.add(item);
									item = null;
									counterOfEntriesToBeDeleted++;
								}
							}
							entriesList = null;
						}
						ac = null;
					}
					
					if (deleteList.size() > 0) {
						dao.deleteBatch(deleteList);
					}
					for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(emailNotificiations, "deleteDuplicateApprovedContracts Process", "Processing Complete on site: " + HAJConstants.PL_LINK + "<br/> Number Of Duplicates Deleted: " + counterOfEntriesToBeDeleted + ".", null);
					}
				} catch (Exception e) {
					logger.fatal(e);
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					GenericUtility.mailError("Error On deleteDuplicateApprovedContracts","<p>Error On deleteDuplicateApprovedContracts.</p><p>Stack Trace:<p/><p> " + exceptionAsString + "</p>");
				}
			}
		}).start();
	}
	
	public void fixDataActiveContractDetail() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
						
					if (activeContractDetailService == null) {
						activeContractDetailService = new ActiveContractDetailService();
					}
					if (activeContractsService == null) {
						activeContractsService = new ActiveContractsService();
					}
					
					List<IDataEntity> updateList = new ArrayList<>();
					int counter = 0;
					List<ActiveContracts> allActiveContracts = activeContractDetailService.findAllActiveContracts();
					for (ActiveContracts activeContracts : allActiveContracts) {
						
						ActiveContracts ac = activeContractsService.findByKey(activeContracts.getId());
						if (ac != null && ac.getId() != null) {
							boolean updateEntry = false;
							if (ac.getSignOffState() == null || ac.getSignOffState()) {
								ac.setSignOffState(false);
								updateEntry = true;
							}
							
							if (ac.getAwaitingBatchSignOff() == null || ac.getAwaitingBatchSignOff()) {
								ac.setAwaitingBatchSignOff(false);
								updateEntry = true;
							}
							
							if (ac.getStatus() == null || ac.getStatus() != ApprovalEnum.Approved) {
								ac.setStatus(ApprovalEnum.Approved);
								updateEntry = true;
							}
							
							if (updateEntry) {
								updateList.add(ac);
								counter++;
							}
						}
						ac = null;
					}
					allActiveContracts = null;
					
					
					if (updateList.size() > 0) {
						dao.updateBatch(updateList);
					}
					for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(emailNotificiations, "fixDataActiveContractDetail Process", "Processing Complete on site: " + HAJConstants.PL_LINK + ". Entries Updated: " + counter, null);
					}
				} catch (Exception e) {
					logger.fatal(e);
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					GenericUtility.mailError("Error On fixDataActiveContractDetail","<p>Error On fixDataActiveContractDetail.</p><p>Stack Trace:<p/><p> " + exceptionAsString + "</p>");
				}
			}
		}).start();
	}
	
	public void fixExtensionOfScopeSelfEvaluation() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (sdpExtensionOfScopeService == null) {
						sdpExtensionOfScopeService = new SDPExtensionOfScopeService();
					}
					if (auditorMonitorReviewService == null) {
						auditorMonitorReviewService = new AuditorMonitorReviewService();
					}
					List<IDataEntity> createList = new ArrayList<>();
					List<IDataEntity> deleteList = new ArrayList<>();
					
					List<SDPExtensionOfScope> scopeList = sdpExtensionOfScopeService.allSDPExtensionOfScope();
					for (SDPExtensionOfScope sdpExtensionOfScope : scopeList) {
						// populate delete List
						List<AuditorMonitorReview> entriesToBeDeleted = auditorMonitorReviewService.findByTargetKeyAndClassNoDoc(sdpExtensionOfScope.getClass().getName(), sdpExtensionOfScope.getId());
						
						if (!entriesToBeDeleted.isEmpty())
							deleteList.addAll(entriesToBeDeleted);
						
						entriesToBeDeleted = null;
						
						// populate entries to be created
						List<AuditorMonitorReview> amr = auditorMonitorReviewService.findByforProcessTP(ConfigDocProcessEnum.SDPExtensionOfScope);
						for (AuditorMonitorReview auditorMonitorReview : amr) {
							AuditorMonitorReview amrNew = (AuditorMonitorReview) auditorMonitorReview.clone();
							amrNew.setId(null);
							amrNew.setCreateDate(null);
							amrNew.setTargetClass(sdpExtensionOfScope.getClass().getName());
							amrNew.setTargetKey(sdpExtensionOfScope.getId());
							createList.add(amrNew);
						}
						amr = null;
					}
					scopeList = null;
					
					if (!deleteList.isEmpty()) 
						dao.deleteBatch(deleteList, 500);
					deleteList = null;
					
					if (!createList.isEmpty()) 
						dao.createBatch(createList, 500);
					createList = null;
					for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(emailNotificiations, "fixExtensionOfScopeSelfEvaluation Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
					}
				} catch (Exception e) {
					logger.fatal(e);
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					GenericUtility.mailError("Error On fixExtensionOfScopeSelfEvaluation","<p>Error On fixExtensionOfScopeSelfEvaluation.</p><p>Stack Trace:<p/><p> " + exceptionAsString + "</p>");
				}
			}
		}).start();
	}

	public void fixComapnyLearnersProviderApplicationData(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (companyLearnersService == null) {
						companyLearnersService = new CompanyLearnersService();
					}
					if (companyService == null) {
						companyService = new CompanyService();
					}
					if (trainingProviderApplicationService == null) {
						trainingProviderApplicationService = new TrainingProviderApplicationService();
					}
					
					List<CompanyLearners> companyLearnersList = companyLearnersService.allCompanyLearnersWhereproviderAssignedButNoApplication();
					for (CompanyLearners companyLearner : companyLearnersList) {
						boolean canUpdate = false;
						
						// check if there is  training provider company assigned
						if (companyLearner.getCompany() != null && companyLearner.getCompany().getId() != null) {
							Company provider = companyService.findByKey(companyLearner.getCompany().getId());
							if (provider != null && provider.getId() != null) {
								
								List<TrainingProviderApplication> approvedProviderApplications = trainingProviderApplicationService.findByCompanyIdAndStatus(provider.getId(), ApprovalEnum.Approved);
								if (approvedProviderApplications.size() == 1) {
									companyLearner.setTrainingProviderApplication(approvedProviderApplications.get(0));
									if (companyLearner.getTrainingProviderApplication() != null && companyLearner.getTrainingProviderApplication().getTrainingSite() != null && companyLearner.getTrainingProviderApplication().getTrainingSite().getId() != null ) {
										companyLearner.setTrainingSite(companyLearner.getTrainingProviderApplication().getTrainingSite());
									}
									canUpdate = true;
								} else if (approvedProviderApplications.size() > 1) {
									// check to see where learners qualification is assigned and corrected
								}
							}
						}
						
						if (canUpdate) {
							companyLearnersService.update(companyLearner);
						}
					}
					for (String email : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(email, "fixComapnyLearnersProviderApplicationData Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
					}
				} catch (Exception e) {
					logger.fatal(e);
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					GenericUtility.mailError("Error On fixComapnyLearnersProviderApplicationData","<p>Error On fixComapnyLearnersProviderApplicationData on site: "+HAJConstants.PL_LINK+".</p><p>Stack Trace:<p/><p> " + exceptionAsString + "</p>");
				}
			}
		}).start();
	}
	
	public void fixUsersData(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (usersService == null) {
						usersService = new UsersService();
					}
					List<Users> allUsers = usersService.allUsers();
					for (Users users : allUsers) {
						try {
							boolean canUpdate = false;
							if (users.getFirstName() != null && !users.getFirstName().isEmpty()) {
								users.setFirstName(users.getFirstName().trim());
								canUpdate = true;
							}
							if (users.getMiddleName() != null && !users.getMiddleName().isEmpty()) {
								users.setMiddleName(users.getMiddleName().trim());
								canUpdate = true;
							}
							if (users.getLastName() != null && !users.getLastName().isEmpty()) {
								users.setLastName(users.getLastName().trim());
								canUpdate = true;
							}
							if (canUpdate) {
								usersService.update(users);
							}
						} catch (Exception e) {
							logger.fatal(e);
						}
					}
					for (String email : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(email, "fixUsersData Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
					}
				} catch (Exception e) {
					logger.fatal(e);
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					GenericUtility.mailError("Error On fixUsersData","<p>Error On fixUsersData on site: "+HAJConstants.PL_LINK+".</p><p>Stack Trace:<p/><p> " + exceptionAsString + "</p>");
				}
			}
		}).start();
	}
	
	public void runUserNameFix(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (usersService == null) {
						usersService = new UsersService();
					}
					List<Users> allUsers = usersService.findUsersWhereEmptySpaceInName();
					for (Users users : allUsers) {
						try {
							StringBuilder sbfFirstName = new StringBuilder();
							StringBuilder sbfMiddleName = new StringBuilder();
							boolean addForMiddleName = false;
							for (int i = 0; i < users.getFirstName().trim().length(); i++) {
								if (i == 0 && String.valueOf(users.getFirstName().trim().charAt(i)).equals(" ")) {
									sbfFirstName.replace(0, 1, "");
								} else if (addForMiddleName) {
									sbfMiddleName.append(String.valueOf(users.getFirstName().trim().charAt(i)));
								} else {
									if (String.valueOf(users.getFirstName().trim().charAt(i)).equals(" ")) {
										addForMiddleName = true;
									} else {
										sbfFirstName.append(String.valueOf(users.getFirstName().trim().charAt(i)));
									}
								}
							}	
							if (users.getMiddleName() != null && !users.getMiddleName().trim().isEmpty()) {
								sbfMiddleName.append(" " + users.getMiddleName().trim());
							}
							
							// sets for audit purposes
							users.setFirstNameOrginal(users.getFirstName().trim());
							users.setFirstNameAltered(sbfFirstName.toString().trim());
							
							if (users.getMiddleName() != null) {
								users.setMiddleNameOrginal(users.getMiddleName().trim());
							}
							if (!sbfMiddleName.toString().isEmpty()) {
								users.setMiddleNameAltered(sbfMiddleName.toString().trim());
							}
							usersService.update(users);
						} catch (Exception e) {
							logger.fatal(e);
						}
					}
					for (String email : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(email, "runUserNameFix Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
					}
				} catch (Exception e) {
					logger.fatal(e);
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					GenericUtility.mailError("Error On runUserNameFix","<p>Error On runUserNameFix on site: "+HAJConstants.PL_LINK+".</p><p>Stack Trace:<p/><p> " + exceptionAsString + "</p>");
				}
			}	
		}).start();	
	}
	
	public void runUserBirthTownFix(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (usersService == null) {
						usersService = new UsersService();
					}
					if (townService == null) {
						townService = new TownService();
					}
					List<Users> allUsers = usersService.findUsersWithBirthAddresses();
					for (Users users : allUsers) {
						try {
							boolean canUpdate = false;
							if (users.getBirthAddress() != null && users.getBirthAddress().getId() != null) {
								users.setBirthAddress(AddressService.instance().findByKey(users.getBirthAddress().getId()));
								if (users.getBirthAddress().getTown() != null && users.getBirthAddress().getTown().getId() != null) {
									Town town = townService.findByKey(users.getBirthAddress().getTown().getId());
									if (town != null && town.getId() != null) {
										users.setBirthTown(town);
										canUpdate = true;
									}
								}
							}
							if (canUpdate) {
								usersService.update(users);
							}
						} catch (Exception e) {
							logger.fatal(e);
						}
					}
					for (String email : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(email, "runUserBirthTownFix Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
					}
				} catch (Exception e) {
					logger.fatal(e);
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					GenericUtility.mailError("Error On runUserBirthTownFix","<p>Error On runUserBirthTownFix on site: "+HAJConstants.PL_LINK+".</p><p>Stack Trace:<p/><p> " + exceptionAsString + "</p>");
				}
			}	
		}).start();	
	}
	
	public void runCompanySiteNumberAllocation(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {	
					if (companyService == null) {
						companyService = new CompanyService();
					}
					
					StringBuilder errorList = new StringBuilder();	
					// L number companies
					List<Company> companyListLNumber = companyService.allCompaniesByCompanyWhereSiteNumberNotAssigned("L");
					for (Company company : companyListLNumber) {
						try {
							companyService.populateCompanySiteNumber(company);
							companyService.upadeCompanyInfo(company);
						} catch (javax.validation.ConstraintViolationException e) {
							errorList.append(company.getLevyNumber() + " Update Error, validiation failed.<br/>");
						} catch (Exception e) {
							errorList.append(company.getLevyNumber() + " Update Error. Exception:" + e.getMessage() + " <br/>");
						}
					}
					companyListLNumber = null;
					
					// N number companies
					List<Company> companyListNNumber = companyService.allCompaniesByCompanyWhereSiteNumberNotAssigned("N");
					for (Company company : companyListNNumber) {
						try {
							companyService.populateCompanySiteNumber(company);
							companyService.upadeCompanyInfo(company);
						} catch (javax.validation.ConstraintViolationException e) {
							errorList.append(company.getLevyNumber() + " Update Error, validiation failed.<br/>");
						} catch (Exception e) {
							errorList.append(company.getLevyNumber() + " Update Error. Exception:" + e.getMessage() + " <br/>");
						}
					}
					companyListNNumber = null;
					if (!errorList.toString().equals("")) {
						GenericUtility.mailError("Completed runCompanySiteNumberAllocation with Errors","<p>Complete runCompanySiteNumberAllocation on site: "+HAJConstants.PL_LINK+" with errors.</p><p>Errors:<p/><p> " + errorList + "</p>");
					}
					
				} catch (Exception e) {
					logger.fatal(e);
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					GenericUtility.mailError("Error On runCompanySiteNumberAllocation","<p>Error On runCompanySiteNumberAllocation on site: "+HAJConstants.PL_LINK+".</p><p>Stack Trace:<p/><p> " + exceptionAsString + "</p>");
				}
			}	
		}).start();	
	}
	
	public void testQmrMonthGeneration(Date datePassed) throws Exception{
		if (qmrFinYearsService == null) {
			qmrFinYearsService = new QmrFinYearsService();
		}
		if (datePassed == null) {
			qmrFinYearsService.generateNewFinYear(getSynchronizedDate());
		}else {
			qmrFinYearsService.generateNewFinYear(GenericUtility.getStartOfDay(datePassed));
		}
	}
	
	public void testQmrQuarterGeneration() throws Exception{
		if (qmrFinYearsService == null) {
			qmrFinYearsService = new QmrFinYearsService();
		}
		qmrFinYearsService.generateDataForQuarter(GenericUtility.getStartOfDay(getSynchronizedDate()));
	}

	public void populateCompanyLearnersDateForQmr() throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					for (String email : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(email, "Action: populateCompanyLearnersDateForQmr Started", "Action: populateCompanyLearnersDateForQmr Started on site: "+HAJConstants.PL_LINK, null);
					}
					if (companyLearnersService == null) {
						companyLearnersService = new CompanyLearnersService();
					}
					List<CompanyLearners> companyLearners = companyLearnersService.findLearnersByStatusNonLegacyAndDateNotPopulated(ApprovalEnum.Approved, LearnerStatusEnum.Registered);
					for (CompanyLearners learner : companyLearners) {
						if (learner.getApprovalDate() != null) {
							learner.setDateLearnerRegistered(learner.getApprovalDate());
						}
						companyLearnersService.update(learner);
					}
					for (String email : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(email, "Action: populateCompanyLearnersDateForQmr Completed", "Action: populateCompanyLearnersDateForQmr completed on site: "+HAJConstants.PL_LINK, null);
					}
				} catch (Exception e) {
					logger.fatal(e);
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					GenericUtility.mailError("Error On populateCompanyLearnersDateForQmr","<p>Error On populateCompanyLearnersDateForQmr on site: "+HAJConstants.PL_LINK+".</p><p>Stack Trace:<p/><p> " + exceptionAsString + "</p>");
				}
			}	
		}).start();
	}
	
	public void populateCompanyLearnersLegacyDateForQmr() throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					
					for (String email : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(email, "Action: populateCompanyLearnersLegacyDateForQmr Started", "Action: populateCompanyLearnersLegacyDateForQmr Started on site: "+HAJConstants.PL_LINK, null);
					}
					
					if (companyLearnersService == null) {
						companyLearnersService = new CompanyLearnersService();
					}
					
					// 8 service levels of the legacy data
					LegacyUnitStandardService legacyUnitStandardService = new LegacyUnitStandardService();
					LegacySkillsProgrammeService legacySkillsProgrammeService = new LegacySkillsProgrammeService();
					LegacyBursaryService bursaryservice = new LegacyBursaryService();
					LegacyApprenticeshipService apprenticeshipservice = new LegacyApprenticeshipService();
					LegacyInternshipService internshiservice = new LegacyInternshipService();
					LegacyLearnershipService learnershipservice = new LegacyLearnershipService();
//					LegacyTvetService tvetservice = new LegacyTvetService();
					LegacySECTTwentyEightService legacySECTTwentyEightService = new LegacySECTTwentyEightService();
//					LegacyExperientialService segacyExperientialService = new LegacyExperientialService();
					
					List<CompanyLearners> companyLearners = companyLearnersService.findLearnersByStatusLegacyAndDateNotPopulated(ApprovalEnum.Approved, LearnerStatusEnum.Registered);
					for (CompanyLearners learner : companyLearners) {
						
						SimpleDateFormat sdf = HAJConstants.sdfYYYYMMDD;
						
						if (learner.getLegacyTargetClass().equals(LegacyUnitStandard.class.getName())) {
							try {
								LegacyUnitStandard legacyUnitStandard = legacyUnitStandardService.findByKey(learner.getLegacyId());
								if (legacyUnitStandard != null) {
									if (legacyUnitStandard.getLearnerUSStatusDesc() != null && legacyUnitStandard.getLearnerUSStatusDesc().trim().equalsIgnoreCase("Registered")) {
										if (legacyUnitStandard.getDtEffectiveDate() != null && !legacyUnitStandard.getDtEffectiveDate().trim().isEmpty()) {
											Date dateLearnerRegistered=sdf.parse(legacyUnitStandard.getDtEffectiveDate());
											if (dateLearnerRegistered != null) {
												learner.setDateLearnerRegistered(dateLearnerRegistered);
												companyLearnersService.update(learner);
											}
										}
									} else {
										if (learner.getApprovalDate() != null) {
											learner.setDateLearnerRegistered(learner.getApprovalDate());
											companyLearnersService.update(learner);
										}
									}
								}
								legacyUnitStandard = null;
							} catch (Exception e) {
								logger.fatal(e);
							}
						} else if (learner.getLegacyTargetClass().equals(LegacySkillsProgramme.class.getName())) {
							try {
								LegacySkillsProgramme legacyEntry = legacySkillsProgrammeService.findByKey(learner.getLegacyId());
								if (legacyEntry != null) {
									if (legacyEntry.getLearnerLPStatusDesc() != null && legacyEntry.getLearnerLPStatusDesc().trim().equalsIgnoreCase("Registered")) {
										if (legacyEntry.getDtEffectiveDate() != null && !legacyEntry.getDtEffectiveDate().trim().isEmpty()) {
											Date dateLearnerRegistered=sdf.parse(legacyEntry.getDtEffectiveDate());
											if (dateLearnerRegistered != null) {
												learner.setDateLearnerRegistered(dateLearnerRegistered);
												companyLearnersService.update(learner);
											}
										}
									} else {
										if (learner.getApprovalDate() != null) {
											learner.setDateLearnerRegistered(learner.getApprovalDate());
											companyLearnersService.update(learner);
										}
									}
								}
								legacyEntry = null;
							} catch (Exception e) {
								logger.fatal(e);
							}
						} else if (learner.getLegacyTargetClass().equals(LegacyBursary.class.getName())) {
							try {
								LegacyBursary legacyEntry = bursaryservice.findByKey(learner.getLegacyId());
								if (legacyEntry != null) {
									if (legacyEntry.getDescription() != null && legacyEntry.getDescription().trim().equalsIgnoreCase("Registered")) {
										if (legacyEntry.getRegistrationDate() != null && !legacyEntry.getRegistrationDate().trim().isEmpty()) {
											Date dateLearnerRegistered=sdf.parse(legacyEntry.getRegistrationDate());
											if (dateLearnerRegistered != null) {
												learner.setDateLearnerRegistered(dateLearnerRegistered);
												companyLearnersService.update(learner);
											}
										}
									} else {
										if (learner.getApprovalDate() != null) {
											learner.setDateLearnerRegistered(learner.getApprovalDate());
											companyLearnersService.update(learner);
										}
									}
								}
								legacyEntry = null;
							} catch (Exception e) {
								logger.fatal(e);
							}
						} else if (learner.getLegacyTargetClass().equals(LegacyApprenticeship.class.getName())) {
							try {
								LegacyApprenticeship legacyEntry = apprenticeshipservice.findByKey(learner.getLegacyId());
								if (legacyEntry != null) {
									if (legacyEntry.getContractStatus() != null && legacyEntry.getContractStatus().trim().equalsIgnoreCase("Registered")) {
										if (legacyEntry.getApprenticeshipRegisterDate() != null && !legacyEntry.getApprenticeshipRegisterDate().trim().isEmpty()) {
											Date dateLearnerRegistered=sdf.parse(legacyEntry.getApprenticeshipRegisterDate());
											if (dateLearnerRegistered != null) {
												learner.setDateLearnerRegistered(dateLearnerRegistered);
												companyLearnersService.update(learner);
											}
										}
									} else {
										if (learner.getApprovalDate() != null) {
											learner.setDateLearnerRegistered(learner.getApprovalDate());
											companyLearnersService.update(learner);
										}
									}
								}
								legacyEntry = null;
							} catch (Exception e) {
								logger.fatal(e);
							}
						} else if (learner.getLegacyTargetClass().equals(LegacyInternship.class.getName())) {
							try {
								LegacyInternship legacyInternship = internshiservice.findByKey(learner.getLegacyId());
								if (legacyInternship != null) {
									if (legacyInternship.getStatus() != null && legacyInternship.getStatus().trim().equalsIgnoreCase("Registered")) {
										if (legacyInternship.getRegistrationDate() != null && !legacyInternship.getRegistrationDate().trim().isEmpty()) {
											Date dateLearnerRegistered=sdf.parse(legacyInternship.getRegistrationDate());
											if (dateLearnerRegistered != null) {
												learner.setDateLearnerRegistered(dateLearnerRegistered);
												companyLearnersService.update(learner);
											}
										}
									} else {
										if (learner.getApprovalDate() != null) {
											learner.setDateLearnerRegistered(learner.getApprovalDate());
											companyLearnersService.update(learner);
										}
									}
								}
								legacyInternship = null;
							} catch (Exception e) {
								logger.fatal(e);
							}
						} else if (learner.getLegacyTargetClass().equals(LegacyLearnership.class.getName())) {
							try {
								LegacyLearnership legacyEntry = learnershipservice.findByKey(learner.getLegacyId());
								if (legacyEntry != null) {
									if (legacyEntry.getAgreementStatusDesc() != null && legacyEntry.getAgreementStatusDesc().trim().equalsIgnoreCase("Registered")) {
										if (legacyEntry.getAgreementStatusEffectiveDate() != null && !legacyEntry.getAgreementStatusEffectiveDate().trim().isEmpty()) {
											Date dateLearnerRegistered=sdf.parse(legacyEntry.getAgreementStatusEffectiveDate());
											if (dateLearnerRegistered != null) {
												learner.setDateLearnerRegistered(dateLearnerRegistered);
												companyLearnersService.update(learner);
											}
										}
									} else {
										if (learner.getApprovalDate() != null) {
											learner.setDateLearnerRegistered(learner.getApprovalDate());
											companyLearnersService.update(learner);
										}
									}
								}
								legacyEntry = null;
							} catch (Exception e) {
								logger.fatal(e);
							}
						} else if (learner.getLegacyTargetClass().equals(LegacyTvet.class.getName())) {
							
							try {
								// no data on prod
//								LegacyTvet legacyEntry = tvetservice.findByKey(learner.getLegacyId());
//								if (legacyEntry != null) {
//									if (legacyEntry.getAgreementStatusDesc() != null && legacyEntry.getAgreementStatusDesc().trim().equalsIgnoreCase("Registered")) {
//								Date dateLearnerRegistered=sdf.parse(legacyTvet.getRegistrationDate());
//								if (dateLearnerRegistered != null) {
//									learner.setDateLearnerRegistered(dateLearnerRegistered);
//								}
//								companyLearnersService.update(learner);	
							} catch (Exception e) {
								logger.fatal(e);
							}
						}else if (learner.getLegacyTargetClass().equals(LegacySECTTwentyEight.class.getName())) {
							
							try {
								LegacySECTTwentyEight legacyEntry = legacySECTTwentyEightService.findByKey(learner.getLegacyId());
								if (legacyEntry != null) {
									if (legacyEntry.getDescription() != null && legacyEntry.getDescription().trim().equalsIgnoreCase("Registered")) {
										if (legacyEntry.getApprenticeshipStatusEffectiveDate() != null && !legacyEntry.getApprenticeshipStatusEffectiveDate().trim().isEmpty()) {
											Date dateLearnerRegistered=sdf.parse(legacyEntry.getApprenticeshipStatusEffectiveDate());
											if (dateLearnerRegistered != null) {
												learner.setDateLearnerRegistered(dateLearnerRegistered);
												companyLearnersService.update(learner);
											}
										}
									} else {
//										if (learner.getApprovalDate() != null) {
//											learner.setDateLearnerRegistered(learner.getApprovalDate());
//											companyLearnersService.update(learner);
//										}
									}
								}
								legacyEntry = null;
							} catch (Exception e) {
								logger.fatal(e);
							}
						} else if (learner.getLegacyTargetClass().equals(LegacyExperiential.class.getName())) {
							try {
								// no data on prod
//								LegacyExperiential legacyEntry = segacyExperientialService.findByKey(learner.getLegacyId());
//								
//								if (legacyEntry != null) {
//									if (legacyEntry.getDescription() != null && legacyEntry.getDescription().trim().equalsIgnoreCase("Registered")) {
//										
//									}else {
//										
//									}
//								}
//								Date dateLearnerRegistered=sdf.parse(legacyExperiential.getRegistrationDate());
//								if (dateLearnerRegistered != null) {
//									learner.setDateLearnerRegistered(dateLearnerRegistered);
//								}
//								companyLearnersService.update(learner);
							} catch (Exception e) {
								logger.fatal(e);
							}
						}
					}
					for (String email : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(email, "Action: populateCompanyLearnersLegacyDateForQmr Completed", "Action: populateCompanyLearnersLegacyDateForQmr completed on site: "+HAJConstants.PL_LINK, null);
					}
				} catch (Exception e) {
					logger.fatal(e);
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					GenericUtility.mailError("Error On populateCompanyLearnersLegacyDateForQmr","<p>Error On populateCompanyLearnersLegacyDateForQmr on site: "+HAJConstants.PL_LINK+".</p><p>Stack Trace:<p/><p> " + exceptionAsString + "</p>");
				}
			}	
		}).start();
	}
	
	public void populatePipLearnerLink() throws Exception {
		if (projectImplementationPlanLearnersService == null) {
			projectImplementationPlanLearnersService = new ProjectImplementationPlanLearnersService();
		}
		projectImplementationPlanLearnersService.onceOffDataGeneration();
	}
	
	public void populateLearnerLinkToPipLearnerLink() throws Exception {
		if (projectImplementationPlanLearnersService == null) {
			projectImplementationPlanLearnersService = new ProjectImplementationPlanLearnersService();
		}
		projectImplementationPlanLearnersService.comapnyLearnerLinkOnceOff();
	}
	
	public void runAssessorModeratorDeAccreditationSchedule(Date dateSelected) throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					for (String email : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(email, "Action: runAssessorModeratorDeAccreditationSchedule Started", "Action: runAssessorModeratorDeAccreditationSchedule started on site: "+HAJConstants.PL_LINK, null);
					}
					Date scheduleDate = null;
					if (dateSelected == null) {
						scheduleDate = GenericUtility.getStartOfDay(getSynchronizedDate());
					} else {
						scheduleDate = GenericUtility.getStartOfDay(dateSelected);
					}
					
					AssessorModeratorApplicationService ampService = new AssessorModeratorApplicationService();
					ampService.applicationDeAccreditationSchedule(scheduleDate);
					for (String email : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(email, "Action: runAssessorModeratorDeAccreditationSchedule Completed", "Action: runAssessorModeratorDeAccreditationSchedule completed on site: "+HAJConstants.PL_LINK, null);
					}
				} catch (Exception e) {
					logger.fatal(e);
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					GenericUtility.mailError("Error On runAssessorModeratorDeAccreditationSchedule","<p>Error On runAssessorModeratorDeAccreditationSchedule on site: "+HAJConstants.PL_LINK+".</p><p>Stack Trace:<p/><p> " + exceptionAsString + "</p>");
				}
			}	
		}).start();
	}
	
	public void runProviderDeAccreditationSchedule(Date dateSelected) throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					for (String email : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(email, "Action: runProviderDeAccreditationSchedule Started", "Action: runProviderDeAccreditationSchedule started on site: "+HAJConstants.PL_LINK, null);
					}
					Date scheduleDate = null;
					if (dateSelected == null) {
						scheduleDate = GenericUtility.getStartOfDay(getSynchronizedDate());
					} else {
						scheduleDate = GenericUtility.getStartOfDay(dateSelected);
					}
					if (trainingProviderApplicationService == null) {
						trainingProviderApplicationService = new TrainingProviderApplicationService();
					}
					trainingProviderApplicationService.applicationDeAccreditationSchedule(scheduleDate);
					for (String email : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(email, "Action: runProviderDeAccreditationSchedule Completed", "Action: runProviderDeAccreditationSchedule completed on site: "+HAJConstants.PL_LINK, null);
					}
				} catch (Exception e) {
					logger.fatal(e);
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					GenericUtility.mailError("Error On runProviderDeAccreditationSchedule","<p>Error On runProviderDeAccreditationSchedule on site: "+HAJConstants.PL_LINK+".</p><p>Stack Trace:<p/><p> " + exceptionAsString + "</p>");
				}
			}	
		}).start();
	}
	
	public void populateNewSdpStructure() throws Exception{
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					for (String email : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(email, "Action: populateNewSdpStructure Started", "Action: populateNewSdpStructure started on site: "+HAJConstants.PL_LINK, null);
					}
					StringBuilder errorList = new StringBuilder();
					if (companyUsersService == null) {
						companyUsersService = new CompanyUsersService();
					}
					if (sdpCompanyService == null) {
						sdpCompanyService = new SDPCompanyService();
					}
					// locate current SDP users
					List<CompanyUsers> sdpComapnyUsers = companyUsersService.findAllSdpCompanyUsersLinkedToCompany(true);
					for (CompanyUsers companyUsers : sdpComapnyUsers) {
						boolean createNewLink = false;
						SDPCompany sdpCompany = null;
						// check if user already assigned as SDP contact to holding company
						if (companyUsers.getCompany() != null && companyUsers.getCompany().getId() != null && companyUsers.getCompany().getCompanyStatus() != CompanyStatusEnum.InActive) {
							if (companyUsers.getUser() != null && companyUsers.getUser().getId() != null) {
								if (companyUsers.getDesignation() != null && companyUsers.getDesignation().getId() != null && companyUsers.getDesignation().getSdpDesignation() != null && companyUsers.getDesignation().getSdpDesignation()) {	
									// get SDP type linked to designation
									SdpType sdpType = SdpTypeService.instance().findByDesignationId(companyUsers.getDesignation().getId());
									if (sdpType != null && sdpType.getId() != null) {
										// check if user already linked to company with designation
										if (sdpCompanyService.countBySdpIdAndCompanyId(companyUsers.getUser().getId(), companyUsers.getCompany().getId()) == 0) {
											sdpCompany = new SDPCompany();
											sdpCompany.setCompany(companyUsers.getCompany());
											sdpCompany.setSdp(companyUsers.getUser());
											sdpCompany.setSdpType(sdpType);
											sdpCompany.setApprovalStatus(ApprovalEnum.Approved);
											createNewLink = true;
										}
									}
								}
							}
						}
						if (createNewLink) {
							try {
								sdpCompanyService.create(sdpCompany);
							} catch (Exception e) {
//								StringWriter sw = new StringWriter();
//								e.printStackTrace(new PrintWriter(sw));
//								String exceptionAsString = sw.toString();
//								errorList.append("<ul>");
//								errorList.append("<li>Company ID:" + sdpCompany.getCompany().getId() + "</li>");
//								errorList.append("<li>Company Levy Number:" + sdpCompany.getCompany().getLevyNumber() + "</li>");
//								errorList.append("<li>User ID:" + sdpCompany.getSdp().getId() + "</li>");
//								errorList.append("<li>User Email:" + sdpCompany.getSdp().getEmail() + "</li>");
//								errorList.append("<li>");
//								errorList.append(exceptionAsString);
//								errorList.append("</li>");
//								errorList.append("</ul>");
							}
						}
					}
					sdpComapnyUsers.clear();
					if (errorList.toString().isEmpty()) {
						for (String email : GenericUtility.processEmailNotfications()) {
							GenericUtility.sendMail(email, "Action: populateNewSdpStructure Completed", "Action: populateNewSdpStructure completed on site: "+HAJConstants.PL_LINK, null);
						}
					} else {
						for (String email : GenericUtility.processEmailNotfications()) {
							GenericUtility.sendMail(email, "Action: populateNewSdpStructure Completed With Errors", "<p>Action: populateNewSdpStructure completed on site: "+HAJConstants.PL_LINK + " with errors.</p><p>Stack Trace:<p/><p>"+ errorList.toString() + "</p>", null);
						}
					}
				} catch (Exception e) {
					logger.fatal(e);
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					GenericUtility.mailError("Error On populateSdpNewStructure","<p>Error On populateSdpNewStructure on site: "+HAJConstants.PL_LINK+".</p><p>Stack Trace:<p/><p> " + exceptionAsString + "</p>");
				}
			}	
		}).start();
	}	
	
	public void populateNewAssModStructure() throws Exception{
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					for (String email : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(email, "Action: populateNewAssModStructure Started", "Action: populateNewAssModStructure started on site: "+HAJConstants.PL_LINK, null);
					}
					StringBuilder errorList = new StringBuilder();
					if (companyUsersService == null) {
						companyUsersService = new CompanyUsersService();
					}
					if (assessorModeratorCompanySitesService == null) {
						assessorModeratorCompanySitesService = new AssessorModeratorCompanySitesService();
					}
					if (assessorModeratorApplicationService == null) {
						assessorModeratorApplicationService = new AssessorModeratorApplicationService();
					}
					// locate current SDP users
					List<CompanyUsers> assModComapnyUsers = companyUsersService.findAllAssModCompanyUsersLinkedToCompany();
					List<IDataEntity> createList = new ArrayList<>();
					for (CompanyUsers companyUsers : assModComapnyUsers) {
						createList = new ArrayList<>();
						// check if user already assigned as SDP contact to holding company
						if (companyUsers.getCompany() != null && companyUsers.getCompany().getId() != null && companyUsers.getCompany().getCompanyStatus() != CompanyStatusEnum.InActive) {
							if (companyUsers.getUser() != null && companyUsers.getUser().getId() != null) {
								if (companyUsers.getAssessorModType() != null) {
									switch (companyUsers.getAssessorModType()) {
									case Assessor:
										if (assessorModeratorCompanySitesService.countByUserHoldingCompanyAssModType(companyUsers.getUser().getId(), companyUsers.getCompany().getId(), AssessorModType.Assessor) == 0) {
											AssessorModeratorCompanySites newEntry = new AssessorModeratorCompanySites();
											newEntry.setAssessorModerator(companyUsers.getUser());
											newEntry.setAssessorModType(AssessorModType.Assessor);
											newEntry.setCompany(companyUsers.getCompany());
											newEntry.setApprovalStatus(ApprovalEnum.Approved);
											try {
												AssessorModeratorApplication app = assessorModeratorApplicationService.findByUserForAssessorModeratorTypeAndNotEqualStatus(
														companyUsers.getUser().getId(), AssessorModeratorTypeEnum.MerSETA_AM, ApprovalEnum.Rejected, AssessorModeratorAppTypeEnum.NewAssessorRegistration);
												if (app != null && app.getId() != null) {
													newEntry.setAssessorModeratorApplication(app);
												}
											} catch (Exception e) {
												logger.fatal(e);
											}
											createList.add(newEntry);
										}
										break;
									case Moderator:
										if (assessorModeratorCompanySitesService.countByUserHoldingCompanyAssModType(companyUsers.getUser().getId(), companyUsers.getCompany().getId(), AssessorModType.Moderator) == 0) {
											AssessorModeratorCompanySites newEntry = new AssessorModeratorCompanySites();
											newEntry.setAssessorModerator(companyUsers.getUser());
											newEntry.setAssessorModType(AssessorModType.Moderator);
											newEntry.setCompany(companyUsers.getCompany());
											newEntry.setApprovalStatus(ApprovalEnum.Approved);
											try {
												AssessorModeratorApplication app = assessorModeratorApplicationService.findByUserForAssessorModeratorTypeAndNotEqualStatus(
														companyUsers.getUser().getId(), AssessorModeratorTypeEnum.MerSETA_AM, ApprovalEnum.Rejected, AssessorModeratorAppTypeEnum.NewModeratorRegistration);
												if (app != null && app.getId() != null) {
													newEntry.setAssessorModeratorApplication(app);
												}
											} catch (Exception e) {
												logger.fatal(e);
											}
											createList.add(newEntry);
										}
										break;
									case AssessorAndModerator:
										if (assessorModeratorCompanySitesService.countByUserHoldingCompanyAssModType(companyUsers.getUser().getId(), companyUsers.getCompany().getId(), AssessorModType.Assessor) == 0) {
											AssessorModeratorCompanySites newEntry = new AssessorModeratorCompanySites();
											newEntry.setAssessorModerator(companyUsers.getUser());
											newEntry.setAssessorModType(AssessorModType.Assessor);
											newEntry.setCompany(companyUsers.getCompany());
											newEntry.setApprovalStatus(ApprovalEnum.Approved);
											try {
												AssessorModeratorApplication app = assessorModeratorApplicationService.findByUserForAssessorModeratorTypeAndNotEqualStatus(
														companyUsers.getUser().getId(), AssessorModeratorTypeEnum.MerSETA_AM, ApprovalEnum.Rejected, AssessorModeratorAppTypeEnum.NewAssessorRegistration);
												if (app != null && app.getId() != null) {
													newEntry.setAssessorModeratorApplication(app);
												}
											} catch (Exception e) {
												logger.fatal(e);
											}
											createList.add(newEntry);
										}
										if (assessorModeratorCompanySitesService.countByUserHoldingCompanyAssModType(companyUsers.getUser().getId(), companyUsers.getCompany().getId(), AssessorModType.Moderator) == 0) {
											AssessorModeratorCompanySites newEntry = new AssessorModeratorCompanySites();
											newEntry.setAssessorModerator(companyUsers.getUser());
											newEntry.setAssessorModType(AssessorModType.Moderator);
											newEntry.setCompany(companyUsers.getCompany());
											newEntry.setApprovalStatus(ApprovalEnum.Approved);
											try {
												AssessorModeratorApplication app = assessorModeratorApplicationService.findByUserForAssessorModeratorTypeAndNotEqualStatus(
														companyUsers.getUser().getId(), AssessorModeratorTypeEnum.MerSETA_AM, ApprovalEnum.Rejected, AssessorModeratorAppTypeEnum.NewModeratorRegistration);
												if (app != null && app.getId() != null) {
													newEntry.setAssessorModeratorApplication(app);
												}
											} catch (Exception e) {
												logger.fatal(e);
											}
											createList.add(newEntry);
										}
										break;
									default:
										break;
									}
									
								}
							}
						}
						try {
							if (!createList.isEmpty()) {
								dao.createBatch(createList);
							}
						} catch (Exception e) {
//							StringWriter sw = new StringWriter();
//							e.printStackTrace(new PrintWriter(sw));
//							String exceptionAsString = sw.toString();
//							errorList.append("<ul>");
//							errorList.append("<li>Company ID:" + sdpCompany.getCompany().getId() + "</li>");
//							errorList.append("<li>Company Levy Number:" + sdpCompany.getCompany().getLevyNumber() + "</li>");
//							errorList.append("<li>User ID:" + sdpCompany.getSdp().getId() + "</li>");
//							errorList.append("<li>User Email:" + sdpCompany.getSdp().getEmail() + "</li>");
//							errorList.append("<li>");
//							errorList.append(exceptionAsString);
//							errorList.append("</li>");
//							errorList.append("</ul>");
						}
					}
					assModComapnyUsers.clear();
					if (errorList.toString().isEmpty()) {
						for (String email : GenericUtility.processEmailNotfications()) {
							GenericUtility.sendMail(email, "Action: populateNewAssModStructure Completed", "Action: populateNewAssModStructure completed on site: "+HAJConstants.PL_LINK, null);
						}
					} else {
						for (String email : GenericUtility.processEmailNotfications()) {
							GenericUtility.sendMail(email, "Action: populateNewAssModStructure Completed With Errors", "<p>Action: populateNewAssModStructure completed on site: "+HAJConstants.PL_LINK + " with errors.</p><p>Stack Trace:<p/><p>"+ errorList.toString() + "</p>", null);
						}
					}
				} catch (Exception e) {
					logger.fatal(e);
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					GenericUtility.mailError("Error On populateNewAssModStructure","<p>Error On populateNewAssModStructure on site: "+HAJConstants.PL_LINK+".</p><p>Stack Trace:<p/><p> " + exceptionAsString + "</p>");
				}
			}	
		}).start();
	}	
}