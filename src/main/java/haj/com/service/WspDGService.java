package haj.com.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.primefaces.model.SortOrder;

import haj.com.bean.StatusReportBean;
import haj.com.bean.WspCalData;
import haj.com.bean.WspHistoryBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.WspDAO;
import haj.com.entity.ActiveContracts;
import haj.com.entity.Company;
import haj.com.entity.CompanyUsers;
import haj.com.entity.ConfigDoc;
import haj.com.entity.DgAllocation;
import haj.com.entity.DgAllocationParent;
import haj.com.entity.DgVerification;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.DocumentTracker;
import haj.com.entity.SDFCompany;
import haj.com.entity.ScheduleChangesLog;
import haj.com.entity.Signoff;
import haj.com.entity.Tasks;
import haj.com.entity.TempSignoff;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.WspChecklist;
import haj.com.entity.WspDispute;
import haj.com.entity.WspImpactOfStaffTraining;
import haj.com.entity.WspSignoff;
import haj.com.entity.WspSkillsGap;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.DGWindowTypeEnum;
import haj.com.entity.enums.DocumentTrackerEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.enums.WSPSearchType;
import haj.com.entity.enums.WspDocRequirements;
import haj.com.entity.enums.WspReportEnum;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.entity.enums.WspTypeEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.WspHistoricData;
import haj.com.exceptions.ValidationErrorException;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.report.bean.ByChamberReportBean;
import haj.com.service.lookup.RegionService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.service.lookup.SizeOfCompanyService;
import haj.com.service.lookup.WspHistoricDataService;
import haj.com.utils.GenericUtility;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * The Class WspService.
 */
public class WspDGService extends AbstractService {

	private WspDAO                           dao                              = new WspDAO();
	private WspStrategicPrioritiesService    wspStrategicPrioritiesService    = new WspStrategicPrioritiesService();
	private DocService                       docService                       = new DocService();
	private CompanyService                   companyService                   = new CompanyService();
	private SDFCompanyService                sdfCompanyService                = new SDFCompanyService();
	private WspSignoffService                wspSignoffService                = new WspSignoffService();
	private ConfigDocService                 configDocService                 = new ConfigDocService();
	private WspSkillsRequirementsService     wspSkillsRequirementsService     = new WspSkillsRequirementsService();
	private MandatoryGrantDetailService      mandatoryGrantDetailService      = new MandatoryGrantDetailService();
	private WspSkillsGapService              wspSkillsGapService              = new WspSkillsGapService();
	private WspImpactOfStaffTrainingService  impactOfStaffTrainingService2    = new WspImpactOfStaffTrainingService();
	private TasksService                     tasksService                     = new TasksService();
	private WspHistoricDataService           wspHistoricDataService           = new WspHistoricDataService();
	private UsersService                     usersService                     = new UsersService();
	private ProjectImplementationPlanService projectImplementationPlanService = new ProjectImplementationPlanService();
	private MandatoryGrantService            mandatoryGrantService            = new MandatoryGrantService();
	private HostingCompanyEmployeesService   hostingCompanyEmployeesService   = new HostingCompanyEmployeesService();
	private ActiveContractsService           activeContractsService           = new ActiveContractsService();
	private CompanyUsersService              companyUsersService;
	private RegionService                    regionService;

	/**
	 * Instantiates a new wsp service.
	 */
	public WspDGService() {
		super();

	}

	/**
	 * Instantiates a new wsp service.
	 * @param auditlog
	 * the auditlog
	 * @param resourceBundle
	 * the resource bundle
	 */
	public WspDGService(Map<String, Object> auditlog, ResourceBundle resourceBundle) {
		super(auditlog, resourceBundle);

	}

	/**
	 * Instantiates a new wsp service.
	 * @param auditlog
	 * the auditlog
	 */
	public WspDGService(Map<String, Object> auditlog) {
		super(auditlog);

	}

	/**
	 * Instantiates a new wsp service.
	 * @param resourceBundle
	 * the resource bundle
	 */
	public WspDGService(ResourceBundle resourceBundle) {
		super(resourceBundle);

	}

	/**
	 * Get all Wsp.
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Wsp}
	 * @throws Exception
	 * the exception
	 * @see Wsp
	 */
	public List<Wsp> allWsp() throws Exception {
		return dao.allWsp();
	}

	/**
	 * Create or update Wsp.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see Wsp
	 */
	public void create(Wsp entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	public void updateIDataEntity(IDataEntity entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * Creates the with sign off.
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 */
	public void createWithSignOff(Wsp entity) throws Exception {
		if (entity.getId() == null) {
			List<IDataEntity> dataEntities = new ArrayList<>();
			dataEntities.add(entity);
			WspSignoff       signOff      = new WspSignoff(entity, entity.getCreateUsers());
			dataEntities.add(signOff);

			List<WspImpactOfStaffTraining> impactOfStaffTrainings = impactOfStaffTrainingService2.findByWspNull();
			for (WspImpactOfStaffTraining wspImpactOfStaffTraining : impactOfStaffTrainings) {
				WspImpactOfStaffTraining impactOfStaffTraining = (WspImpactOfStaffTraining) wspImpactOfStaffTraining.clone();
				impactOfStaffTraining.setId(null);
				impactOfStaffTraining.setCreateDate(null);
				impactOfStaffTraining.setWsp(entity);
				dataEntities.add(impactOfStaffTraining);
			}

			WspChecklist wspChecklist = new WspChecklist();
			wspChecklist.setWsp(entity);
			dataEntities.add(wspChecklist);

			// creates WSP skill gap sections
			List<WspSkillsGap> skillsGapList = wspSkillsGapService.findByWspNull();
			for (WspSkillsGap wspSkillsGap : skillsGapList) {
				WspSkillsGap newWspSkillGap = (WspSkillsGap) wspSkillsGap.clone();
				newWspSkillGap.setId(null);
				newWspSkillGap.setCreateDate(null);
				newWspSkillGap.setWsp(entity);
				dataEntities.add(newWspSkillGap);
			}
			dao.createBatch(dataEntities);
		} else this.dao.update(entity);
	}

	/**
	 * Update Wsp.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see Wsp
	 */
	public void update(Wsp entity) throws Exception {
		// entity.setQualificationAligned(null);
		this.dao.update(entity);
	}

	/* BEGIN WSP WORKFLOW METHOD */
	/**
	 * Submit WSP.
	 * @param entity
	 * the entity
	 * @param users
	 * the users
	 * @param createTask
	 * the create task
	 * @throws Exception
	 * the exception
	 */
	public void submitWSP(Wsp entity, Users users, boolean createTask) throws Exception {
		// validateWSP(entity, users, true, false, false, true, false, false, false, false);
		List<IDataEntity> dataEntities = new ArrayList<>();
		dataEntities.add(entity);
		if (entity.getRequireSignOffUpload() == null || !entity.getRequireSignOffUpload()) {
			List<Users> userList = new ArrayList<>();
			entity.setWspStatusEnum(WspStatusEnum.PendingSignOff);
			entity.setWspSignoffs(wspSignoffService.findByWsp(entity));
			for (WspSignoff wspSignoff : entity.getWspSignoffs()) {
				dataEntities.add(wspSignoff);
				if (wspSignoff.getAccept() == null || !wspSignoff.getAccept()) {
					userList.add(wspSignoff.getUser());
				}
			}
			TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.DG_APPLICATION, entity.getId());
			if (createTask) {
				String message = "Grant Application for " + entity.getCompany().getCompanyName() + " has been submitted and requires your sign off.";
				TasksService.instance().createTaskEachUser(entity.getClass().getName(), entity.getCompany(), message, "WSP form requires sign off", message, users, entity.getId(), true, createTask, null, userList, ConfigDocProcessEnum.DG_APPLICATION, MailDef.instance(MailEnum.WSPRequiresSignOff, MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()));
			}
		} else {
			sendAcknowledgementOfSubmissionEmail(users, entity);
			TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.DG_APPLICATION, entity.getId());
			entity.setWspStatusEnum(WspStatusEnum.Pending);
			String message = "Grant Application for " + entity.getCompany().getCompanyName() + " has been submitted and with a sign off upload please review.";
			TasksService.instance().findFirstInProcessAndCreateTask(message, users, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.DG_APPLICATION, MailDef.instance(MailEnum.WSPSubmittedAndSignedReview, MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()), null);
		}
		if (entity.getSubmissionDate() == null) {
			entity.setSubmissionDate(getSynchronizedDate());
		}
		this.dao.updateBatch(dataEntities);
	}

	@SuppressWarnings("unchecked")
	public void submitWSPWithSignOff(Wsp entity, Users users, boolean createTask) throws Exception {
		validateWSP(entity, users);
		List<IDataEntity> dataEntities = new ArrayList<>();
		entity.setWspSignoffs(wspSignoffService.findByWsp(entity));
		for (WspSignoff wspSignoff : entity.getWspSignoffs()) {
			wspSignoff.setAccept(true);
			wspSignoff.setSignOffDate(getSynchronizedDate());
			dataEntities.add(wspSignoff);
		}
		if (entity.getDgYear().getDgWindowType() == DGWindowTypeEnum.Pivitol) {
			entity.setWspStatusEnum(WspStatusEnum.ProjectReview);
			entity.setApprovedDate(getSynchronizedDate());
			entity.setLastActionUser(users);
			entity.setWithSdf(false);
			entity.setMancoDecisionReached(true);
			entity.setApprovedDate(getSynchronizedDate());
			WspCompanyHistoryCreateService.instance().clearAndPopulateWspCompanyHistory(entity.getCompany(), entity.getClass().getName(), entity.getId());
			DgVerificationService.instance().generateForWSP(entity, users);
		} else {
			entity.setWspStatusEnum(WspStatusEnum.ProjectReview);
			entity.setApprovedDate(getSynchronizedDate());
			entity.setLastActionUser(users);
			entity.setWithSdf(false);
			entity.setMancoDecisionReached(true);
			entity.setApprovedDate(getSynchronizedDate());
			WspCompanyHistoryCreateService.instance().clearAndPopulateWspCompanyHistory(entity.getCompany(), entity.getClass().getName(), entity.getId());
		}
		if (entity.getSubmissionDate() == null) {
			entity.setSubmissionDate(getSynchronizedDate());
		}
		dataEntities.add(entity);
		this.dao.updateBatch(dataEntities);
	}

	/**
	 * Runs validation check on WSP If passes will update entry
	 * @param wsp
	 * @param users
	 * @throws Exception
	 */
	public void runValidiationCheckOnWsp(Wsp wsp, Users users) throws Exception {
		// validateWSP(wsp, users, true, false, true, false, true, true, true, false);
		update(wsp);
	}

	public void saveWSP(Wsp entity, Users users, boolean createTask) throws Exception {

		// //validateWSP(entity, users, true, false, true, false, true, true);

		List<IDataEntity> dataEntities = new ArrayList<>();
		dataEntities.add(entity);
		entity.setWspStatusEnum(WspStatusEnum.Pending);

		if (entity.getRequireSignOffUpload() == null || !entity.getRequireSignOffUpload()) {
			for (WspSignoff wspSignoff : entity.getWspSignoffs()) {
				dataEntities.add(wspSignoff);
			}
		}
		if (entity.getSubmissionDate() == null) entity.setSubmissionDate(getSynchronizedDate());

		this.dao.updateBatch(dataEntities);
	}

	public void stopWSP(Wsp entity, Users users, boolean createTask) throws Exception {
		List<IDataEntity> updateList = new ArrayList<>();
		entity.setWspStatusEnum(WspStatusEnum.BoardReview);
		if (entity.getSubmissionDate() == null) entity.setSubmissionDate(getSynchronizedDate());

		updateList.add(entity);

		if (entity.getRequireSignOffUpload() == null || !entity.getRequireSignOffUpload()) {
			for (WspSignoff wspSignoff : entity.getWspSignoffs()) {
				if (wspSignoff.getUser().getId().equals(users.getId())) {
					if (Boolean.TRUE.equals(wspSignoff.getAccept())) {
						wspSignoff.setSignOffDate(getSynchronizedDate());
					}
					updateList.add(wspSignoff);
				}

			}
		}
		if (!updateList.isEmpty()) {
			dao.updateBatch(updateList);
		}
	}

	/**
	 * Sign off.
	 * @param entity
	 * the entity
	 * @param users
	 * the users
	 * @throws Exception
	 * the exception
	 */
	public void signOff(Wsp entity, Users users, Tasks tasks) throws Exception {
		// validateWSP(entity, users, false, false, false, true, false, false, false, false);
		List<IDataEntity> dataEntities = new ArrayList<>();
		dataEntities.add(entity);
		boolean changePending = true;
		for (WspSignoff wspSignoff : entity.getWspSignoffs()) {

			if (wspSignoff.getUser().equals(users)) {
				wspSignoff.setSignOffDate(getSynchronizedDate());
			}

			dataEntities.add(wspSignoff);
			if ((wspSignoff.getAccept() == null || !wspSignoff.getAccept()) && (wspSignoff.getDispute() == null || !wspSignoff.getDispute())) {
				changePending = false;
			}
		}

		TasksService.instance().completeTask(tasks);

		if (changePending) {
			entity.setWspStatusEnum(WspStatusEnum.Pending);
			String message = "Grant Application for " + entity.getCompany().getCompanyName() + " has been submitted and signed off please review.";
			TasksService.instance().findFirstInProcessAndCreateTask(message, users, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.DG_APPLICATION, MailDef.instance(MailEnum.WSPRequiresSignOff, MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()), null);
		}
		dao.updateBatch(dataEntities);
	}

	public void saveSignOff(Wsp entity, Users users, Tasks tasks) throws Exception {
		// validateWSP(entity, users, false, false, false, true, false, false, false, false);
		List<IDataEntity> dataEntities = new ArrayList<>();
		dataEntities.add(entity);
		for (WspSignoff wspSignoff : entity.getWspSignoffs()) {
			if (wspSignoff.getUser().equals(users)) {
				wspSignoff.setSignOffDate(getSynchronizedDate());
			}
			dataEntities.add(wspSignoff);
		}
		TasksService.instance().completeTask(tasks);

		if (entity.getSubmissionDate() == null) entity.setSubmissionDate(getSynchronizedDate());
		dao.updateBatch(dataEntities);
	}

	public void saveSignOffVersionTwo(Wsp entity, Users users, Tasks tasks) throws Exception {
		// validateWSP(entity, users, false, false, false, true, false, false, false, false);
		List<IDataEntity> dataEntities = new ArrayList<>();
		dataEntities.add(entity);
		boolean changePending = true;
		for (WspSignoff wspSignoff : entity.getWspSignoffs()) {
			if (wspSignoff.getUser().equals(users)) {
				wspSignoff.setSignOffDate(getSynchronizedDate());
				dataEntities.add(wspSignoff);
			}
			if ((wspSignoff.getAccept() == null || !wspSignoff.getAccept()) && (wspSignoff.getDispute() == null || !wspSignoff.getDispute())) {
				changePending = false;
			}
		}
		TasksService.instance().completeTask(tasks);
		if (entity.getSubmissionDate() == null) entity.setSubmissionDate(getSynchronizedDate());
		dao.updateBatch(dataEntities);
		if (changePending) {
			entity.setWspStatusEnum(WspStatusEnum.Pending);
			update(entity);
		}
	}

	public void sendAcknowledgementOfSubmission(Users user, Company company) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);

		Calendar currentYear = Calendar.getInstance();
		// currentYear.add(Calendar.YEAR, -1);
		int year = currentYear.get(Calendar.YEAR);

		String period = "1 January " + year + " to " + "31 December " + year;
		params.put("period", period);
		params.put("submited_by_user_id", user.getId());
		params.put("call_center_number", HAJConstants.CATHSSETA_HEAD_OFFICE);
		params.put("company_id", company.getId());

		byte[] bites = JasperService.instance().convertJasperReportToByte("Aknowledgement_Of_WSP_DG_Submission.jasper", params);

		String subject = "Acknowledgement Of Discretionary Grant Submission: " + company.getCompanyName() + " (" + company.getLevyNumber() + ")";
		String mssg    = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Thank you for using the Cathsseta online facility to submit the discretionary grant application for " + company.getCompanyName() + " (" + company.getLevyNumber() + ").</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">We acknowledge receipt of the application and include an acknowledgement of submission notification for your records.</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">We will be in contact with you should there be any outstanding information required. Please further note that the payment" + " of discretionary grants can only be made once the levies for the company have been received by the Cathsseta and if the application is approved.</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Should you have any queries please do not hesitate to contact the Cathsseta Call Centre on: " + HAJConstants.CATHSSETA_HEAD_OFFICE + " for assistance. </p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Cathsseta Administration</p>";

		String fileName = company.getCompanyNameDisplay() + "-Aknowledgement_Of_WSP_DG_Submission.pdf";

		GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, fileName, "pdf", null);

	}

	public void sendAcknowledgementOfSubmissionEmail(Users user, Wsp company) throws Exception {
		String subject = "Acknowledgement of discretionary grant application for " + company.getCompany().getCompanyNameDisplay();
		String mssg    = "<p>Dear #USER_NAME#,</p><br/>" + "<p>Thank you for submitting a discretionary grant application for the discretionary grant window #DG_YEAR#. </p>" + "<p>Kindly be advised that the merSETA will review the application in accordance with its policies and procedures, Annual Performance Plan and the development of skills as articulated in its Sector Skills Plan, subject to the availability of funding.</p>"
				+ "<p>For clarity/support, kindly contact the designated Client Liaison Officer: #CLO_NAME# (Email: #CLO_EMAIL#) or #REGION# Office.</p>" + "<p>System Generated Notification</p>";

		RegionTown rt = RegionTownService.instance().findByTown(company.getCompany().getResidentialAddress().getTown());
		if (rt != null) {
			mssg = mssg.replace("#USER_NAME#", user.getFirstName() + " " + user.getLastName());
			mssg = mssg.replace("#DG_YEAR#", "" + company.getDgYear().getDescription());
			mssg = mssg.replace("#CLO_NAME#", rt.getClo().getUsers().getFirstName() + " " + rt.getClo().getUsers().getLastName());
			mssg = mssg.replace("#CLO_EMAIL#", rt.getClo().getUsers().getEmail());
			mssg = mssg.replace("#REGION#", rt.getRegion().getDescription());
			GenericUtility.sendMail(user.getEmail(), subject, mssg, null);
		}
	}

	public void sendSubmissionStoppedEmail(Users user, Company company) throws Exception {
		String subject = "Discretionary Grant Submission Failed: " + company.getCompanyNameDisplay();
		String mssg    = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + ",</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Thank you for using the Cathsseta online facility to submit a discretionary grant application for" + company.getCompanyNameDisplay() + ".</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">We acknowledge receipt of the application.</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Your qualification are not aligned to Cathssetas Occupational Shortages and Skills Gaps. <br/> " + "Therefore, your submission was stopped and sent through to Cathsseta for Board Review </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">We will be in contact with you should there be any outstanding information required.</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Should you have any queries please do not hesitate to contact the Cathsseta Call Centre on:" + HAJConstants.CATHSSETA_HEAD_OFFICE + " for assistance. </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Cathsseta Administration</p>";
		GenericUtility.sendMail(user.getEmail(), subject, mssg, null);
	}

	/**
	 * Save status update.
	 * @param entity
	 * the entity
	 * @param users
	 * the users
	 * @param tasks
	 * the tasks
	 * @param wspStatusEnum
	 * the wsp status enum
	 * @throws Exception
	 * the exception
	 */
	public void saveStatusUpdate(Wsp entity, Users users, Tasks tasks, WspStatusEnum wspStatusEnum) throws Exception {
		List<IDataEntity> updateEntities = new ArrayList<>();
		List<IDataEntity> createEntities = new ArrayList<>();
		List<Users>       usersList      = new ArrayList<>();
		if (tasks != null && tasks.getFirstInProcess() != null && tasks.getFirstInProcess()) {
			// check qualification allignmnet provided for all entries
			// if (mandatoryGrantService.countByWspQalificationAlignmentNotProvided(entity, WspReportEnum.EMPLOYMENTDATA) != 0) {
			// throw new Exception("Provide All Qualification Aligment Data Before Proceeding.");
			// }
		}

		entity.setWspStatusEnum(wspStatusEnum);
		// if (entity.getQualificationAligned() != null) {
		// createEntities.add(entity.getQualificationAligned());
		// }
		updateEntities.add(entity);
		if (!createEntities.isEmpty() && !updateEntities.isEmpty()) {
			this.dao.createAndUpdateBatch(createEntities, updateEntities);
		} else {
			if (!createEntities.isEmpty()) {
				dao.createBatch(createEntities);
			}
			if (!updateEntities.isEmpty()) {
				dao.updateBatch(updateEntities);
			}
		}

		// if (Boolean.FALSE.equals(users.getComplianceReviewer())) {
		// usersList = usersService.findAllComplianceReviewer();
		// TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.DG_APPLICATION, entity.getId());
		// TasksService.instance().findNextInProcessAndCreateTask(null, users, entity.getId(), entity.getClass().getName(), true, tasks, MailDef.instance(MailEnum.WSPRequiresReview, MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()), usersList);
		// } else {
		TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.DG_APPLICATION, entity.getId());
		TasksService.instance().findNextInProcessAndCreateTask(null, users, entity.getId(), entity.getClass().getName(), true, tasks, MailDef.instance(MailEnum.WSPRequiresReview, MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()), null);
		// }
	}

	/**
	 * The Final Approval/ Rejection of the grant application
	 * @param entity
	 * @param users
	 * @param tasks
	 * @param wspStatusEnum
	 * @throws Exception
	 */
	public void finalApproveRejectGrantApplication(Wsp entity, Users users, Tasks tasks, WspStatusEnum wspStatusEnum) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		entity.setWithSdf(false);
		entity.setMancoDecisionReached(true);
		entity.setWspStatusEnum(wspStatusEnum);
		entity.setApprovedDate(getSynchronizedDate());
		dataEntities.add(entity);
		this.dao.updateBatch(dataEntities);
		if (tasks != null) {
			TasksService.instance().completeTask(tasks);
		}
	}

	/**
	 * Sends the task back to the primary SDF to either accept or appeal the grant rejection
	 * @param entity
	 * @param users
	 * @param tasks
	 * @param selectedRejectReasons
	 * @throws Exception
	 */
	public void rejectToSdfToAppeal(Wsp entity, Users users, Tasks tasks, List<RejectReasons> selectedRejectReasons) throws Exception {

		SDFCompany primarySDF = companyService.findPrimarySDF(entity.getCompany());
		if (primarySDF.getSdf() == null && primarySDF.getSdf().getId() == null) {
			throw new Exception("Unable to locate primary SDF for company: " + entity.getCompany().getLevyNumber());
		}
		List<Users> taskUsers = new ArrayList<>();
		taskUsers.add(primarySDF.getSdf());

		List<IDataEntity> dataEntities = new ArrayList<>();
		entity.setWithSdf(true);
		entity.setSdfAppealedGrant(false);
		entity.setGrantRejected(true);
		entity.setGrantRejectedDate(getSynchronizedDate());
		dataEntities.add(entity);
		if (dataEntities.size() != 0) {
			this.dao.updateBatch(dataEntities);
		}
		TasksService.instance().completeTask(tasks);

		TasksService.instance().findNextInProcessAndCreateTask(null, users, entity.getId(), entity.getClass().getName(), true, tasks, MailDef.instance(MailEnum.WSPRequiresReview, MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()), taskUsers);
		if (selectedRejectReasons != null && selectedRejectReasons.size() != 0) {
			setRejectedReasons(entity.getClass().getName(), entity.getId(), users, selectedRejectReasons, ConfigDocProcessEnum.DG_APPLICATION);
		}
		// add email notification
	}

	public void rejectTask(Wsp entity, Users users, Tasks tasks, List<RejectReasons> selectedRejectReasons) throws Exception {
		// TasksService.instance().findNextInProcessAndCreateTask(null, users, entity.getId(), entity.getClass().getName(), true, tasks, MailDef.instance(MailEnum.WSPRequiresReview, MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()), taskUsers);
		if (selectedRejectReasons != null && selectedRejectReasons.size() != 0) {
			setRejectedReasons(entity.getClass().getName(), entity.getId(), users, selectedRejectReasons, ConfigDocProcessEnum.DG_APPLICATION);
		}
		// add email notification
		TasksService.instance().findPreviousInProcessAndCreateTask(users, tasks, null);
	}

	public void finalRejectNoAppeal(Wsp entity, Users users, Tasks tasks, List<RejectReasons> selectedRejectReasons) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		entity.setWspStatusEnum(WspStatusEnum.Rejected);
		entity.setRejectionNotified(false);
		entity.setWithSdf(true);
		entity.setSdfAppealedGrant(false);
		entity.setGrantRejected(true);
		entity.setGrantRejectedDate(getSynchronizedDate());
		dataEntities.add(entity);
		if (dataEntities.size() != 0) {
			this.dao.updateBatch(dataEntities);
		}
		TasksService.instance().completeTask(tasks);

		// TasksService.instance().findNextInProcessAndCreateTask(null, users, entity.getId(), entity.getClass().getName(), true, tasks, MailDef.instance(MailEnum.WSPRequiresReview, MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()), taskUsers);
		if (selectedRejectReasons != null && selectedRejectReasons.size() != 0) {
			setRejectedReasons(entity.getClass().getName(), entity.getId(), users, selectedRejectReasons, ConfigDocProcessEnum.DG_APPLICATION);
		}
		// add email notification
	}

	/**
	 * Sets Grant Application to Awaiting MANCO approval
	 * @param entity
	 * @param users
	 * @param tasks
	 * @throws Exception
	 */
	public void awaitingMancoApproval(Wsp entity, Users users, Tasks tasks) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		entity.setMancoApprovalRequired(true);
		entity.setMancoDecisionReached(false);
		entity.setDateMancoApprovalRequired(getSynchronizedDate());
		entity.setUserRequestedMancoApproval(users);

		// To Be Confirmed
		// entity.setWspStatusEnum(WspStatusEnum.MancoApproval);

		dataEntities.add(entity);
		if (dataEntities.size() != 0) {
			this.dao.updateBatch(dataEntities);
		}
		TasksService.instance().completeTask(tasks);
	}

	@SuppressWarnings("unchecked")
	public List<Wsp> allWspAwaitingMancoApproval(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from Wsp o " + "where o.wspStatusEnum = :wspStatus " + "and o.mancoApprovalRequired = :mancoApprovalRequired " + "and o.mancoDecisionReached = :mancoDecisionReached ";
		filters.put("wspStatus", WspStatusEnum.Pending);
		filters.put("mancoApprovalRequired", true);
		filters.put("mancoDecisionReached", false);
		return resolveExtension((List<Wsp>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countAllWspAwaitingMancoApproval(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Wsp o " + "where o.wspStatusEnum = :wspStatus " + "and o.mancoApprovalRequired = :mancoApprovalRequired " + "and o.mancoDecisionReached = :mancoDecisionReached ";
		return dao.countWhere(filters, hql);
	}

	/**
	 * Final Rejection of grant application
	 * @param entity
	 * @param users
	 * @param tasks
	 * @throws Exception
	 */
	public void finalRejectionGrant(Wsp entity, Users users, Tasks tasks) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		entity.setWithSdf(false);
		entity.setWspStatusEnum(WspStatusEnum.Rejected);
		entity.setApprovedDate(getSynchronizedDate());
		dataEntities.add(entity);
		if (dataEntities.size() != 0) {
			this.dao.updateBatch(dataEntities);
		}
		TasksService.instance().completeTask(tasks);
	}

	/**
	 * SDF appealed grant application
	 * @param entity
	 * @param users
	 * @param tasks
	 * @throws Exception
	 */
	public void appealGrantApplication(Wsp entity, Users users, Tasks tasks) throws Exception {
		for (Doc document : entity.getDocs()) {
			if (document.getConfigDoc().getWspDocRequirements() == WspDocRequirements.AppealForm) {
				if (document.getId() == null) {
					throw new Exception("Provide Grant Appplication Appeal In Required Documents Section Before Proceeding With Appeal");
				}
			}
		}
		List<IDataEntity> dataEntities = new ArrayList<>();
		entity.setWithSdf(false);
		entity.setSdfAppealedGrant(true);
		entity.setSdfAppealedGrantDate(getSynchronizedDate());
		dataEntities.add(entity);
		if (dataEntities.size() != 0) {
			this.dao.updateBatch(dataEntities);
		}
		TasksService.instance().completeTask(tasks);
		TasksService.instance().findNextInProcessAndCreateTask(null, users, entity.getId(), entity.getClass().getName(), true, tasks, MailDef.instance(MailEnum.WSPRequiresReview, MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()), null);
	}

	/**
	 * Send back to SDF.
	 * @param entity
	 * the entity
	 * @param users
	 * the users
	 * @throws Exception
	 * the exception
	 */
	public void sendBackToSDF(Wsp entity, Users users) throws Exception {
		// validateWSP(entity, users, false, false, false, true, false, false, false, false);
		List<IDataEntity> dataEntities = new ArrayList<>();
		dataEntities.add(entity);
		entity.setWspStatusEnum(WspStatusEnum.Draft);
		for (WspSignoff wspSignoff : entity.getWspSignoffs()) {
			wspSignoff.setAccept(false);
			dataEntities.add(wspSignoff);
		}
		DgVerificationService.instance().clearDgVerification(entity);
		TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.DG_APPLICATION, entity.getId());
		String message = "Grant Application for " + entity.getCompany().getCompanyName() + " has been rejected please review before sign off and submission.";
		TasksService.instance().createTask(entity.getCompany(), "Grant Application form needs to be reviewed", message, users, entity.getId(), true, true, null, entity.getCreateUsers(), ConfigDocProcessEnum.DG_APPLICATION, MailDef.instance(MailEnum.WSPRejectedReview, MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()));
		this.dao.updateBatch(dataEntities);
		ExtensionRequestService.instance().locateAndUpdateExtnesionRequest(entity);
	}

	/**
	 * Send back to SDF for re-upload of documents.
	 * @param entity
	 * the entity
	 * @param users
	 * the users
	 * @throws Exception
	 * the exception
	 */
	public void sendBackToSDFDocumentRejection(Wsp entity, Users users, List<RejectReasons> rejectReasonsSelected) throws Exception {
		// validateWSP(entity, users, false, false, false, true, false, false, false, false);
		SDFCompany  company = sdfCompanyService.locateFirstPrimarySDF(entity.getCompany());
		List<Users> sdf     = new ArrayList<>();
		if (company != null) sdf.add(company.getSdf());
		TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.DG_APPLICATION, entity.getId());
		TasksService.instance().findFirstInProcessAndCreateTask(null, users, entity.getId(), Wsp.class.getName(), true, ConfigDocProcessEnum.WSPDocumentUpload, null, sdf);
		setRejectedReasons(entity.getClass().getName(), entity.getId(), users, rejectReasonsSelected, ConfigDocProcessEnum.WSPDocumentUpload);
	}

	public void setRejectedReasons(String className, Long targetkey, Users users, List<RejectReasons> rejectReasonsSelected, ConfigDocProcessEnum configProcess) throws Exception {
		List<IDataEntity> entityList = new ArrayList<IDataEntity>();
		if (rejectReasonsSelected != null && rejectReasonsSelected.size() != 0) {
			entityList.addAll(RejectReasonMultipleSelectionService.instance().removeCreateLinks(targetkey, className, rejectReasonsSelected, users, configProcess));
		} else {
			RejectReasonMultipleSelectionService.instance().clearEntries(targetkey, className, configProcess);
		}
		if (entityList.size() != 0) {
			dao.createBatch(entityList);
		}
	}

	/**
	 * Dispute WSP.
	 * @param entity
	 * the entity
	 * @param users
	 * the users
	 * @param tasks
	 * the tasks
	 * @param disputeReason
	 * the dispute reason
	 * @throws Exception
	 * the exception
	 */
	public void disputeWSP(Wsp entity, Users users, Tasks tasks, String disputeReason) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		entity.setRequireDisputeDocs(true);
		dataEntities.add(entity);
		boolean changePending = true;

		for (WspSignoff wspSignoff : entity.getWspSignoffs()) {
			dataEntities.add(wspSignoff);
			if (wspSignoff.getUser().equals(users)) {
				wspSignoff.setAccept(false);
				wspSignoff.setDispute(true);
				wspSignoff.setSignOffDate(getSynchronizedDate());
				WspDispute wspDispute = new WspDispute(entity, users, disputeReason);

				dao.create(wspDispute);
				if (entity.getCompany().getResidentialAddress() != null) {
					RegionTown  rt       = RegionTownService.instance().findByTown(entity.getCompany().getResidentialAddress().getTown());
					List<Users> signoffs = new ArrayList<>();
					if (rt != null) {
						dao.create(new Signoff(wspDispute, rt.getClo().getUsers(), "Cathsseta Region CLO"));
						dao.create(new Signoff(wspDispute, rt.getCrm().getUsers(), "Cathsseta Region CRM"));
						signoffs.add(rt.getClo().getUsers());
						TasksService.instance().createTaskUser(signoffs, entity.getClass().getName(), entity.getId(), "There is a new dispute on the grant application for company " + entity.getCompany().getCompanyName() + " (" + entity.getCompany().getLevyNumber() + ")", users, true, true, null, ConfigDocProcessEnum.DG_APPLICATION, false);
					}
				}
			} else if ((wspSignoff.getAccept() == null || !wspSignoff.getAccept()) && (wspSignoff.getDispute() == null || !wspSignoff.getDispute())) {
				changePending = false;
			}
		}
		TasksService.instance().completeTask(tasks);
		if (changePending) {
			// TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.DG_APPLICATION,
			// entity.getId());
			entity.setWspStatusEnum(WspStatusEnum.Pending);
			String message = "Grant Application for " + entity.getCompany().getCompanyName() + " has been submitted and signed off please review.";
			TasksService.instance().findFirstInProcessAndCreateTask(message, users, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.DG_APPLICATION, MailDef.instance(MailEnum.WSPSubmittedAndSignedReview, MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()), null);
		}
		this.dao.updateBatch(dataEntities);
	}

	public void createDisputeSignOff(WspDispute wspDispute, TempSignoff tempSignoff, String title) throws Exception {
		dao.create(tempSignoff);
		dao.create(new Signoff(wspDispute, tempSignoff, title));
	}

	/* END WSP WORKFLOW METHOD */

	/**
	 * Validate WSP.
	 * @param wsp
	 * the wsp
	 * @param users
	 * the users
	 * @param checkDocs
	 * the check docs
	 * @param checkImpactOfStaffTraining
	 * the check impact of staff training
	 * @param checkSkillsRequirments
	 * the check skills requirments
	 * @param checkSignOff
	 * the check sign off
	 * @param checkWSPData
	 * the check employed
	 * @param checkATRData
	 * the check unemployed
	 * @throws Exception
	 * the exception
	 */
	private void validateWSP(Wsp wsp, Users users) throws Exception {
		StringBuilder error      = new StringBuilder("");
		List<String>  exceptions = new ArrayList<>();
		if (wsp.getDgYear().getDgWindowType() == DGWindowTypeEnum.Project) {
			Map<String, Object> filters = new HashMap<String, Object>();
			filters.put("wspID", wsp.getId());
			if (wspStrategicPrioritiesService.count(filters) == 0) {
				exceptions.add("Please ensure you capture your key focus areas");
			}
		}
		if (exceptions.size() > 0) {
			for (String string : exceptions) {
				if (string.indexOf("DONE-") == -1) {
					error.append("<li>" + string + "</li>");
				} else {
					error.append("<li>" + string.replaceAll("DONE-", "") + "</li>");
				}
			}
			throw new ValidationErrorException("<ul>" + error.toString() + "</ul>");
		}
	}

	/**
	 * Delete Wsp.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see Wsp
	 */
	public void delete(Wsp entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 * @author TechFinium
	 * @param id
	 * the id
	 * @return a {@link haj.com.entity.Wsp}
	 * @throws Exception
	 * the exception
	 * @see Wsp
	 */
	public Wsp findByKey(long id) throws Exception {
		return populateDeviation(checkDocRequired(resolveDocs(dao.findByKey(id))));
	}

	public Wsp findByKeyPlain(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Calculates if the grant has deviated form previous year training
	 * @see Wsp
	 * @see WspCalculationDataService
	 * @param wsp
	 * @return wsp
	 * @throws Exception
	 */
	public Wsp populateDeviation(Wsp wsp) throws Exception {
		if (wsp.getWspStatusEnum() != WspStatusEnum.Draft) {
			BigDecimal minReq = (BigDecimal.valueOf(60));
			wsp.setPercentageCalculatedForDeviation(WspCalculationDataService.instance().calculateAndReturnDeviationAmount(wsp));
			if (wsp.getPercentageCalculatedForDeviation() != null) {
				wsp.setPercentageCalculatedForDeviationDoubleValue(GenericUtility.roundToPrecision(wsp.getPercentageCalculatedForDeviation().doubleValue(), 2));
				if (wsp.getPercentageCalculatedForDeviation().doubleValue() < minReq.doubleValue()) {
					wsp.setGrantDeviated(true);
				} else {
					wsp.setGrantDeviated(false);
				}
			} else {
				// sets to null to display no data
				wsp.setGrantDeviated(null);
			}
		}
		return wsp;
	}

	public Wsp populateInformationForReport(Wsp wsp) throws Exception {
		populateDeviation(wsp);
		// populates rejection reasons across the
		if (wsp.getWspStatusEnum() == WspStatusEnum.Rejected || wsp.getWspStatusEnum() == WspStatusEnum.Approved) {
			findLastActionUser(wsp);

			/**
			 * Tas Thing Locate lastest task employee user Users user = get form lastest task wsp.setEmployeeUsers(user.getFirstName() + " " + user.getLastName());
			 */

			if (wsp.getWspStatusEnum() == WspStatusEnum.Rejected) {

				if (wsp.getSystemApprovalRejection() != null && wsp.getSystemApprovalRejection()) {
					ScheduleChangesLog log = ScheduleChangesLogService.instance().findBytargetClassandTargetKey(wsp.getClass().getName(), wsp.getId());
					wsp.setSystemApprovalRejectionMessage(log.getInformation());
				} else {
					wsp.setSystemApprovalRejectionMessage("N/A");
				}

				// populate last employee task user
				RejectReasonsService rejectReasonsService = new RejectReasonsService();
				List<RejectReasons>  rrList               = rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(wsp.getId(), Wsp.class.getName(), ConfigDocProcessEnum.DG_APPLICATION);
				rejectReasonsService = null;
				String results = "";
				int    count   = 1;
				for (RejectReasons rejectReasons : rrList) {
					if (count == rrList.size()) {
						results += rejectReasons.getDescription();
					} else {
						results += rejectReasons.getDescription() + ", ";
					}
					count++;
				}
				if (results != "") {
					wsp.setRejectionReasons(results);
				} else {
					wsp.setRejectionReasons("None Found");
				}
			} else {
				wsp.setRejectionReasons("N/A");
				wsp.setSystemApprovalRejectionMessage("N/A");
			}

		} else {
			wsp.setRejectionReasons("N/A");
			wsp.setEmployeeUsers("N/A");
		}
		return wsp;
	}

	/**
	 * Find by key.
	 * @param id
	 * the id
	 * @param user
	 * the user
	 * @return the wsp
	 * @throws Exception
	 * the exception
	 */
	public Wsp findByKey(long id, Users user) throws Exception {
		return populateDeviation(checkDocRequired(resolveWspSignOff(resolveDocs(dao.findByKey(id)), user)));
	}

	/**
	 * Find Wsp by description.
	 * @author TechFinium
	 * @param desc
	 * the desc
	 * @return a list of {@link haj.com.entity.Wsp}
	 * @throws Exception
	 * the exception
	 * @see Wsp
	 */
	public List<Wsp> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load Wsp.
	 * @param first
	 * from key
	 * @param pageSize
	 * no of rows to fetch
	 * @return a list of {@link haj.com.entity.Wsp}
	 * @throws Exception
	 * the exception
	 */
	public List<Wsp> allWsp(int first, int pageSize) throws Exception {
		return dao.allWsp(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of Wsp for lazy load.
	 * @author TechFinium
	 * @return Number of rows in the Wsp
	 * @throws Exception
	 * the exception
	 */
	public Long count() throws Exception {
		return dao.count(Wsp.class);
	}

	/**
	 * Lazy load Wsp with pagination, filter, sorting.
	 * @author TechFinium
	 * @param class1
	 * Wsp class
	 * @param first
	 * the first
	 * @param pageSize
	 * the page size
	 * @param sortField
	 * the sort field
	 * @param sortOrder
	 * the sort order
	 * @param filters
	 * the filters
	 * @return a list of {@link haj.com.entity.Wsp} containing data
	 * @throws Exception
	 * the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Wsp> allWsp(Class<Wsp> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<Wsp>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
	}

	/**
	 * Get count of Wsp for lazy load with filters.
	 * @author TechFinium
	 * @param entity
	 * Wsp class
	 * @param filters
	 * the filters
	 * @return Number of rows in the Wsp entity
	 * @throws Exception
	 * the exception
	 */
	public int count(Class<Wsp> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public List<Wsp> allWspByCompany(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company company) throws Exception {
		String hql = "select o from Wsp o where o.company.id = :companyId";
		filters.put("companyId", company.getId());
		return resolveExtension((List<Wsp>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countAllWspByCompany(Map<String, Object> filters) throws Exception {
		String hql   = "select count(o) from Wsp o where o.company.id = :companyId";
		int    count = dao.countWhere(filters, hql);
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Wsp> allWspByCompanyWithAllocation(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company company) throws Exception {
		String hql = "select o from Wsp o where o.company.id = :companyId and o.id in (select x.wsp.id from DgAllocationParent x)";
		filters.put("companyId", company.getId());
		return resolveExtension((List<Wsp>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countAllWspByCompanyWithAllocation(Map<String, Object> filters) throws Exception {
		String hql   = "select count(o) from Wsp o where o.company.id = :companyId and o.id in (select x.wsp.id from DgAllocationParent x)";
		int    count = dao.countWhere(filters, hql);
		return count;
	}

	/**
	 * Find by company.
	 * @param company
	 * the company
	 * @return the list
	 * @throws Exception
	 * the exception
	 */
	public List<Wsp> findByCompany(Company company) throws Exception {
		return resolveExtension(dao.findByCompany(company.getId()));
	}

	public List<Wsp> findByCompany(Company company, WspTypeEnum wspTypeEnum) throws Exception {
		return resolveExtension(dao.findByCompany(company.getId(), wspTypeEnum));
	}

	private List<Wsp> resolveExtension(List<Wsp> wsps) throws Exception {
		for (Wsp wsp : wsps) {
			populateRejectReasons(wsp);
			// sets extension requests
			wsp.setExtensionRequests(ExtensionRequestService.instance().findByWSP(wsp));
			// sets DG Verification status on WSP
			wsp.setDgVerificationStatus(DgVerificationService.instance().findByWspAndReturnStatus(wsp.getId()));

			DgVerificationService dgVerificationService = new DgVerificationService();
			DgVerification        dg                    = dgVerificationService.findByWspId(wsp);

			if (dg == null) {
				wsp.setVerificationGeneratedForWsp(false);
			} else {
				wsp.setVerificationGeneratedForWsp(true);
				wsp.setDgVerification(dg);
				populateDGVerificationRejectReasons(dg);
			}
			dgVerificationService = null;
		}
		return wsps;
	}

	public void populateRejectReasons(Wsp wsp) throws Exception {
		if (wsp.getWspStatusEnum() == WspStatusEnum.Rejected) {
			// populate last employee task user
			RejectReasonsService rejectReasonsService = new RejectReasonsService();
			List<RejectReasons>  rrList               = rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(wsp.getId(), Wsp.class.getName(), ConfigDocProcessEnum.DG_APPLICATION);
			rejectReasonsService = null;
			String results = "";
			int    count   = 1;
			for (RejectReasons rejectReasons : rrList) {
				if (count == rrList.size()) {
					results += rejectReasons.getDescription();
				} else {
					results += rejectReasons.getDescription() + ", ";
				}
				count++;
			}
			if (results != "") {
				wsp.setRejectionReasons(results);
			} else {
				wsp.setRejectionReasons("None Found");
			}
		} else {
			wsp.setRejectionReasons("N/A");
			wsp.setSystemApprovalRejectionMessage("N/A");
		}
	}

	public void populateDGVerificationRejectReasons(DgVerification dg) throws Exception {
		if (dg.getStatus() == ApprovalEnum.Rejected) {
			// populate last employee task user
			RejectReasonsService rejectReasonsService = new RejectReasonsService();
			List<RejectReasons>  rrList               = rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(dg.getId(), DgVerification.class.getName(), ConfigDocProcessEnum.DG_VERIFICATION);
			rejectReasonsService = null;
			String results = "";
			int    count   = 1;
			for (RejectReasons rejectReasons : rrList) {
				if (count == rrList.size()) {
					results += rejectReasons.getDescription();
				} else {
					results += rejectReasons.getDescription() + ", ";
				}
				count++;
			}
			if (results != "") {
				dg.setDgVerificationRejectionReasons(results);
			} else {
				dg.setDgVerificationRejectionReasons("None Found");
			}
		} else {
			dg.setDgVerificationRejectionReasons("N/A");
			dg.setDgVerificationRejectionReasons("N/A");
		}
	}

	/**
	 * Find by guid.
	 * @param guid
	 * the guid
	 * @param user
	 * the user
	 * @return the wsp
	 * @throws Exception
	 * the exception
	 */
	public Wsp findByGuid(String guid, Users user) throws Exception {
		return checkDocRequired(resolveWspSignOff(resolveDocs(dao.findByGuid(guid)), user));
	}

	/**
	 * Check wsp fin year unique.
	 * @param wsp
	 * the wsp
	 * @throws Exception
	 * the exception
	 * @throws ValidationException
	 * the validation exception
	 */
	public void checkWspFinYearUnique(Wsp wsp) throws Exception, ValidationException {
		List<Wsp> l = new ArrayList<>();
		l = dao.findByCompany(wsp.getCompany().getId());
		for (Wsp wsp2 : l) {
			if (wsp2.getFinYear() != null) if (wsp2.getFinYear().intValue() == wsp.getFinYear().intValue()) throw new ValidationException("wsp.fin.year.exists", wsp.getFinYear());
		}
	}

	public void checkWspFinYearUnique(Wsp wsp, WspTypeEnum wspTypeEnum) throws Exception, ValidationException {
		List<Wsp> l = new ArrayList<>();
		l = dao.findByCompany(wsp.getCompany().getId(), wspTypeEnum);
		for (Wsp wsp2 : l) {
			if (wsp2.getFinYear() != null) if (wsp2.getFinYear().intValue() == wsp.getFinYear().intValue()) throw new ValidationException("wsp.fin.year.exists", wsp.getFinYear());
		}
	}

	public Wsp checkDocRequired(Wsp wsp) throws Exception {
		for (Doc doc : wsp.getDocs()) {

			if (wsp.getRequireDisputeDocs() != null && wsp.getRequireDisputeDocs()) {
				if (doc.getConfigDoc().getWspDocRequirements() == WspDocRequirements.Dispute) {
					doc.setRequired(true);
				}
			}
			if (wsp.getRequireSignOffUpload() != null && wsp.getRequireSignOffUpload()) {
				if (doc.getConfigDoc().getWspDocRequirements() == WspDocRequirements.SignOffPage) {
					doc.setRequired(true);
				}
			}
			if (wsp.getCompany().getTrainingCommitteeInPlace() != null && wsp.getCompany().getTrainingCommitteeInPlace()) {
				if (doc.getConfigDoc().getWspDocRequirements() == WspDocRequirements.TrainingCommittee) {
					doc.setRequired(true);
				}
			}

			if (wsp.getCompletedTraining() != null && wsp.getCompletedTraining().getId() == HAJConstants.NO_ID) {
				if (doc.getConfigDoc().getWspDocRequirements() == WspDocRequirements.TrainingMotivation) {
					doc.setRequired(true);
				}
			}
			// || mandatoryGrantDetailService.deviationAnalysis(wsp) > 40.0
			if ((wsp.getDeviatedWorkplaceSkillsPlan() != null && wsp.getDeviatedWorkplaceSkillsPlan().getId() == HAJConstants.YES_ID)) {
				if (doc.getConfigDoc().getWspDocRequirements() == WspDocRequirements.Deviation) {
					doc.setRequired(true);
				}
			}

			if (mandatoryGrantDetailService.findByWSPPivotalPlan(wsp, WspReportEnum.ATR, HAJConstants.NO_ID).size() > 0) {
				if (doc.getConfigDoc().getWspDocRequirements() == WspDocRequirements.NonNQFTrainingExplanation) {
					doc.setRequired(true);
				}
			}
			if (wsp.getGrantRejected() != null && wsp.getGrantRejected() == true) {
				if (doc.getConfigDoc().getWspDocRequirements() == WspDocRequirements.AppealForm) {
					doc.setRequired(true);
				}
			}
		}
		return wsp;
	}

	/**
	 * Resolve wsp sign off.
	 * @param wsp
	 * the wsp
	 * @param user
	 * the user
	 * @return the wsp
	 */
	@SuppressWarnings("unchecked")
	private Wsp resolveWspSignOff(Wsp wsp, Users user) {
		try {
			wsp.setWspSignoffs(wspSignoffService.findByWsp(wsp));

			if (wsp.getWspStatusEnum() == WspStatusEnum.Draft) {
				List<IDataEntity> dataEntities = new ArrayList<>();

				dao.deleteBatch((List<IDataEntity>) (List<?>) wsp.getWspSignoffs());

				WspSignoff       signOff      = new WspSignoff(wsp, wsp.getCreateUsers());
				dataEntities.add(signOff);

				dao.createBatch(dataEntities);
			}

			wsp.setWspSignoffs(wspSignoffService.findByWsp(wsp));
			wsp.setActiveSignOff(wspSignoffService.findByWspAndUser(wsp, user));
		} catch (Exception e) {
			logger.fatal(e.getMessage(), e);
		}
		return wsp;
	}

	public String fixSignOffs(Wsp wsp, Users users) throws Exception {
		wspSdfCheck(wsp);
		String           returnMessage = "No changes have been found for the grant sign offs";
		List<SDFCompany> sdfCompanies  = sdfCompanyService.findByCompanyAndSdfType(wsp.getCompany(), HAJConstants.PRIMARY_ID);
		List<Users>      userList      = new ArrayList<>();
		List<WspSignoff> signoffs      = wspSignoffService.findByWsp(wsp);

		for (SDFCompany sdfCompany : sdfCompanies) {
			if (sdfCompany.getSdfType().getId() != HAJConstants.PRIMARY_ID) {

				signoffs = signoffs.stream().filter(wso -> wso.getSdfType().equals(sdfCompany.getSdfType())).collect(Collectors.toList());

				if (signoffs.size() > 0) {

					WspSignoff wspSignoff = signoffs.get(0);
					if (!wspSignoff.getUser().equals(sdfCompany.getSdf())) {
						if (wspSignoff.getAccept() == null || !wspSignoff.getAccept()) {
							wspSignoff.setUser(sdfCompany.getSdf());
							dao.update(wspSignoff);
							userList.add(sdfCompany.getSdf());
						}
					}

				}

			}
		}

		if (userList.size() > 0) {
			TasksService.instance().completeTaskByTargetKey("haj.com.entity.Wsp", wsp.getId());
			String message = "Grant Application for " + wsp.getCompany().getCompanyName() + " has been submitted and requires your sign off.";
			TasksService.instance().createTaskEachUser(wsp.getClass().getName(), wsp.getCompany(), message, "WSP form requires sign off", message, users, wsp.getId(), true, true, null, userList, ConfigDocProcessEnum.DG_APPLICATION, MailDef.instance(MailEnum.WSPRequiresSignOff, MailTagsEnum.CompanyName, wsp.getCompany().getCompanyName()));

			returnMessage = "Changes have been found for the grant sign offs. The new SDF can now sign off.";
		}
		return returnMessage;
	}

	public void wspSdfCheck(Wsp wsp) throws Exception {
		// logic checks
		boolean employeeSdfRequired = false;
		boolean labourSdfRequired   = false;
		String  errorMsg            = "";
		// check if labour SDF required
		/*
		 * if (wsp.getCompany().getRecognitionAgreement() != null) { if (wsp.getCompany().getRecognitionAgreement().equals(YesNoLookupService. instance().findByKey(HAJConstants.YES_ID))) { labourSdfRequired = true; } } else { throw new Exception("Unable to locate Recognition Agreement. Contact support!"); }
		 */
		if (!labourSdfRequired) {
			// check if employee SDF required
			if (wsp.getCompany().getNumberOfEmployees() != null) {
				if (wsp.getCompany().getNumberOfEmployees() < 50) {
					employeeSdfRequired = false;
				} else {
					employeeSdfRequired = true;
				}
			} else {
				throw new Exception("Unable to locate Number of company employees. Contact support!");
			}
		}
		// loops through SDFs assigned for checks
		boolean          primaryFound  = false;
		boolean          labourFound   = false;
		boolean          employeeFound = false;
		List<SDFCompany> sdfCompany    = sdfCompanyService.findByCompany(wsp.getCompany());
		for (SDFCompany sdf : sdfCompany) {
			if (!primaryFound) {
				if (sdf.getSdfType().getId().equals(HAJConstants.PRIMARY_ID)) {
					primaryFound = true;
				}
			}
			if (!labourFound) {
				if (sdf.getSdfType().getId().equals(HAJConstants.LAB_ID)) {
					labourFound = true;
				}
			}
			if (!employeeFound) {
				if (sdf.getSdfType().getId().equals(HAJConstants.EMP_ID)) {
					employeeFound = true;
				}
			}
		}
		sdfCompany = null;

		// final checks for configuration
		if (!primaryFound) {
			errorMsg += " Primary SDF ";
		}
		/*
		 * if (employeeSdfRequired && !employeeFound) { errorMsg += " Employee SDF "; }
		 */
		/*
		 * if (labourSdfRequired && !labourFound) { errorMsg += " Labour/Union SDF "; }
		 */
		if (errorMsg != "") {
			throw new Exception("Configuration Error. Please configure the following SDF for " + wsp.getCompany().getCompanyName() + ": " + errorMsg);
		}
	}

	private List<Wsp> resolveWspSignOffs(List<Wsp> wsps) {
		try {
			for (Wsp wsp : wsps) {
				wsp.getCompany().setSizeOfCompany(SizeOfCompanyService.instance().findCompanySize(wsp.getCompany()));
				wsp.setWspSignoffs(wspSignoffService.findByWsp(wsp));

			}
		} catch (Exception e) {
			logger.fatal(e.getMessage(), e);
		}
		return wsps;
	}

	/**
	 * Resolve docs.
	 * @param wsp
	 * the wsp
	 * @return the wsp
	 * @throws Exception
	 * the exception
	 */
	public Wsp resolveDocs(Wsp wsp) throws Exception {
		try {
			wsp.setDocs(docService.searchByWSP(wsp));
			prepareDocs(ConfigDocProcessEnum.DG_APPLICATION, wsp);
		} catch (Exception e) {
			logger.fatal(e);
		}

		return wsp;
	}

	/**
	 * Prepare docs.
	 * @param configDocProcess
	 * the config doc process
	 * @param wsp
	 * the wsp
	 * @throws Exception
	 * the exception
	 */
	public void prepareDocs(ConfigDocProcessEnum configDocProcess, Wsp wsp) throws Exception {
		List<ConfigDoc> l = configDocService.findByProcessNotUploadedWSP(wsp, configDocProcess);
		if (l != null && l.size() > 0) {
			l.forEach(a -> {
				wsp.getDocs().add(new Doc(a, wsp));
			});
		}
	}

	/**
	 * Document upload.
	 * @param entity
	 * the entity
	 * @param users
	 * the users
	 * @throws Exception
	 * the exception
	 */
	public void documentUpload(Wsp entity, Users users) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		if (entity.getDocs() != null) {
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() == null && doc.getData() != null) {
					doc.setWsp(entity);
					doc.setVersionNo(1);
					doc.setUsers(users);
					dataEntities.add(doc);
					dataEntities.add(new DocumentTracker(doc, users, new java.util.Date(), DocumentTrackerEnum.Upload));
					dataEntities.add(new DocByte(doc.getData(), doc));
				}
			}
		}
		this.dao.createBatch(dataEntities);
	}

	/**
	 * Upload new version.
	 * @param doc
	 * the doc
	 * @param users
	 * the users
	 * @throws Exception
	 * the exception
	 */
	public void uploadNewVersion(Doc doc, Users users) throws Exception {
		List<Doc> docs = null;
		if (doc.getDocVerions() == null || doc.getDocVerions().isEmpty()) {
			docs = new ArrayList<>();
			docs.add(DocService.instance().findByKey(doc.getId()));
			doc.setId(null);
		} else {
			docs = doc.getDocVerions();
			Collections.reverse(docs);
			docs.add(0, doc);
		}
		if (doc.getData() != null) {
			DocService.instance().save(doc, users, docs);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Wsp> allMandatoryGrantWsp(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, WspReportEnum wspReport, String etqaCode, long fundingID) throws Exception {
		String hql = "select o.wsp from MandatoryGrant o where o.wspReport =:wspReport and (o.etqa.code = :etqaCode or o.funding.id = :fundingID) order by o.wsp.finYear desc";
		filters.put("wspReport", wspReport);
		filters.put("etqaCode", etqaCode);
		filters.put("fundingID", fundingID);
		return (List<Wsp>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public List<Wsp> allMandatoryGrantWsp(WspReportEnum wspReport, long fundingID, int finYear) throws Exception {
		return dao.allMandatoryGrantWsp(wspReport, fundingID, finYear);
	}

	@SuppressWarnings("unchecked")
	public List<Wsp> findCompaniesForVerification(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, int finYear) throws Exception {
		String hql = "select distinct o.wsp from MgVerification o where o.wsp.finYear =:finYear";
		filters.put("finYear", finYear);
		return (List<Wsp>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countCompaniesForVerification(Map<String, Object> filters, int finYear) throws Exception {
		String hql = "select count(distinct o.wsp) from MgVerification o where o.wsp.finYear =:finYear";
		filters.put("finYear", finYear);
		return dao.countWhere(filters, hql);
	}

	public int countWsp(Map<String, Object> filters, WspReportEnum wspReport, String etqaCode, long fundingID) throws Exception {
		String hql = "select count(o) from MandatoryGrant o where o.wspReport =:wspReport and (o.etqa.code = :etqaCode or o.funding.id = :fundingID)";
		filters.put("wspReport", wspReport);
		filters.put("etqaCode", etqaCode);
		filters.put("fundingID", fundingID);
		return dao.countWhere(filters, hql);
	}

	/**
	 * Find by company and Financial Year.
	 * @param companyId
	 * the company id
	 * @param finYear
	 * the finYear
	 * @return the list
	 * @throws Exception
	 * the exception
	 */
	public List<Wsp> findByCompanyAndFinancialYear(Long companyId, int finYear) throws Exception {
		return dao.findByCompanyAndFinancialYear(companyId, finYear);
	}

	public List<WspHistoryBean> findWSPHistByCompany(Company company) throws Exception {
		List<Wsp>             wspList  = dao.findByCompany(company.getId());
		List<WspHistoricData> wsplist2 = wspHistoricDataService.findByLevyNumber(company.getLevyNumber());
		return prepareHistoryBean(wspList, wsplist2);

	}

	public List<WspHistoryBean> findWSPHistByFinYear(Integer year) throws Exception {
		List<Wsp>             wspList  = dao.findByFinancialYear(year);
		List<WspHistoricData> wsplist2 = wspHistoricDataService.findByFinancialYear(year);
		return prepareHistoryBean(wspList, wsplist2);

	}

	public List<WspHistoryBean> findByYearRange(Integer fromYear, Integer toYear) throws Exception {
		List<Wsp>             wspList  = dao.findByYearRange(fromYear, toYear);
		List<WspHistoricData> wsplist2 = wspHistoricDataService.findByYearRange(fromYear, toYear);
		return prepareHistoryBean(wspList, wsplist2);

	}

	private List<WspHistoryBean> prepareHistoryBean(List<Wsp> wspList, List<WspHistoricData> wsplist2) {
		List<WspHistoryBean> list = new ArrayList<>();
		for (Wsp wsp : wspList) {
			String compName = "";
			String wspType  = "";
			String levyNum  = "";
			int    finYar   = 0;
			String status   = "";
			if (wsp.getCompany() != null && wsp.getCompany().getCompanyName() != null) {
				compName = wsp.getCompany().getCompanyName();
			}
			if (wsp.getWspType() != null && wsp.getWspType().getFriendlyName() != null) {
				wspType = wsp.getWspType().getFriendlyName();
			}
			if (wsp.getCompany() != null && wsp.getCompany().getLevyNumber() != null) {
				levyNum = wsp.getCompany().getLevyNumber();
			}
			if (wsp.getFinYear() != null) {
				finYar = wsp.getFinYear();
			}
			if (wsp.getWspStatusEnum() != null) {
				status = wsp.getWspStatusEnum().getFriendlyName();
			}
			WspHistoryBean b = new WspHistoryBean(compName, wspType, levyNum, finYar, status);
			b.setId(wsp.getId());
			list.add(b);
		}

		for (WspHistoricData wsp2 : wsplist2) {
			String compName = wsp2.getOrganisationNameLegal();
			if (compName == null || compName.equals("") || compName.equals(" ") || compName.isEmpty()) {
				compName = wsp2.getOrganistaionNameTrade();
			}
			WspHistoryBean b = new WspHistoryBean(compName, wsp2.getGrantNameRevised(), wsp2.getReferenceNo(), wsp2.getLevyYear(), wsp2.getFinalStatus());
			b.setId(wsp2.getId());
			list.add(b);
		}

		return list;
	}

	/**
	 * Count check for wsp by company id and fin year against wsp
	 * @see Company
	 * @see Wsp
	 * @param company
	 * @param finYear
	 * @return int
	 * @throws Exception
	 * @author jonathanbowett
	 */
	public int countByCompanyAndFinYear(Company company, int finYear) throws Exception {
		return dao.countByCompanyAndFinYear(company.getId(), finYear);
	}

	/**
	 * Find Wsp by primary key.
	 * @author TechFinium
	 * @param id
	 * the id
	 * @return a {@link haj.com.entity.Wsp}
	 * @throws Exception
	 * global exception
	 * @see Wsp
	 */
	public Wsp artPercentageCheck(Long id) throws Exception {
		return dao.artPercentageCheck(id);
	}

	public List<Wsp> findSubmittedWSP() throws Exception {
		return resolveWspSignOffs(dao.findSubmittedWSP());
	}

	public List<Wsp> findByStatus(WspStatusEnum statusEnum, int finYear) throws Exception {
		return dao.findByStatus(statusEnum, finYear);
	}

	public void checkIfCorrectSignOff() {
		try {
			List<Wsp> w             = resolveWspSignOffs(findByStatus(WspStatusEnum.PendingSignOff, 2019));
			boolean   changePending = false;
			for (Wsp wsp : w) {
				changePending = false;
				List<IDataEntity> dataEntities = new ArrayList<>();
				if (wsp.getCompany().getRecognitionAgreement().getId() == YesNoEnum.No.getYesNoLookupId() && wsp.getCompany().getNumberOfEmployees() < 50) {
					if (wsp.getWspSignoffs().size() > 1) {
						for (WspSignoff wspSignoff : wsp.getWspSignoffs()) {
							if (wspSignoff.getSdfType().getId() != HAJConstants.PRIMARY_ID) {
								dataEntities.add(wspSignoff);
							} else {
								if ((wspSignoff.getAccept() != null && wspSignoff.getAccept()) || (wspSignoff.getDispute() != null && wspSignoff.getDispute())) {
									changePending = true;
								}
							}
						}
						// wsp.setWspStatusEnum(wspStatusEnum);
					}
					// else if (wsp.getWspSignoffs().size() == 1) {
					// if ((wsp.getWspSignoffs().get(0).getAccept() != null &&
					// wsp.getWspSignoffs().get(0).getAccept()) ||
					// (wsp.getWspSignoffs().get(0).getDispute() != null &&
					// wsp.getWspSignoffs().get(0).getDispute())) {
					// changePending = true;
					// }
					// }
				}
				if (changePending && wsp.getWspStatusEnum() != WspStatusEnum.Pending) {
					wsp.setWspStatusEnum(WspStatusEnum.Pending);
					dao.update(wsp);
					TasksService.instance().completeTaskByTargetKey(wsp.getClass().getName(), wsp.getId());
					String message = "Grant Application for " + wsp.getCompany().getCompanyName() + " has been submitted and signed off please review.";
					TasksService.instance().findFirstInProcessAndCreateTask(message, wsp.getCreateUsers(), wsp.getId(), wsp.getClass().getName(), true, ConfigDocProcessEnum.DG_APPLICATION, MailDef.instance(MailEnum.WSPRequiresSignOff, MailTagsEnum.CompanyName, wsp.getCompany().getCompanyName()), null);
				}
				if (dataEntities.size() > 0) {
					dao.deleteBatch(dataEntities);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void submitDocumentUploadTask(Wsp entity, Users users, Tasks task) throws Exception {
		// TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.DG_APPLICATION,
		// entity.getId());
		docCheckForWsp(entity);
		TasksService.instance().completeTask(task);
		String message = "Grant Application for " + entity.getCompany().getCompanyName() + " has been submitted and with a sign off upload please review.";
		TasksService.instance().findFirstInProcessAndCreateTask(message, users, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.DG_APPLICATION, MailDef.instance(MailEnum.WSPSubmittedAndSignedReview, MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()), null);
	}

	private void docCheckForWsp(Wsp entity) throws Exception {
		Boolean    devicationDocRequired = false;
		Boolean    minutesRequired       = false;
		Boolean    nonNqfRequired        = false;
		BigDecimal minReq                = (BigDecimal.valueOf(60));

		// does the checks for required docs
		int previourYear = (Calendar.getInstance().get(Calendar.YEAR)) - 1;
		if (previourYear == 2017) {
			if (WspCalculationDataService.instance().findByCompanyCount(entity.getCompany()) == 0) {
				devicationDocRequired = false;
			} else {
				List<WspCalData> calcDataReportBean = WspCalculationDataService.instance().calPercentageByCompany(entity.getCompany().getId());
				for (WspCalData data : calcDataReportBean) {
					if (data.getPercentage().doubleValue() < minReq.doubleValue()) {
						devicationDocRequired = true;
					}
					break;
				}
			}
		} else {
			devicationDocRequired = false;
		}

		if (entity.getCompany().getRecognitionAgreement() == YesNoLookupService.instance().findByKey(HAJConstants.YES_ID)) {
			minutesRequired = true;
		}

		// loops through docs
		for (Doc doc : entity.getDocs()) {
			// check if deviation doc required
			if (devicationDocRequired) {
				if (doc.getConfigDoc().getId() == HAJConstants.TRAINING_DEVIATION_MOTIVATION_ID && doc.getId() == null) {
					throw new Exception("Provide: " + doc.getConfigDoc().getName() + " before submission.");
				}
			}

			// check if minutes required
			if (minutesRequired) {
				if (doc.getConfigDoc().getId() == HAJConstants.TRAINING_COMMITEE_MINUTES1_ID && doc.getId() == null) {
					throw new Exception("Provide: " + doc.getConfigDoc().getName() + " before submission.");
				}
				if (doc.getConfigDoc().getId() == HAJConstants.TRAINING_COMMITEE_MINUTES2_ID && doc.getId() == null) {
					throw new Exception("Provide: " + doc.getConfigDoc().getName() + " before submission.");
				}
			}

			// check if non nqf explination required
			if (nonNqfRequired) {
				if (doc.getConfigDoc().getId() == HAJConstants.NON_NQF_EXPLANATION_ID && doc.getId() == null) {
					throw new Exception("Provide: " + doc.getConfigDoc().getName() + " before submission.");
				}
			}
		}
	}

	/**
	 * Sends the relevant notification to users based on email Notification integer set: 1 - Sends Approval notification (None applicable for the appeal). Grant is approved when sent. 2 - Sends Approved Notification after SDF has appealed grant. Grant is approved when sent. 3 - Sends rejection notification to inform SDF to either accept the rejection or appeal the rejection. Grant is still pending approval when sent. 4 - Sends Final Rejection notice after the grant is rejected after the SDF has
	 * appealed the notification. Grant is rejected when sent.
	 * @param entity
	 * @param emailNotification
	 * @throws Exception
	 */
	public void sendReleventNotificationToUsers(Wsp entity, Integer emailNotification, List<RejectReasons> rejectReasons) throws Exception {
		List<Users> notificationUsers = locateClientUsersNotification(entity);
		Date        submissionDate    = null;
		// boolean atrWspSubmission = true; // for testing
		boolean atrWspSubmission = checkWspAtrSubmission(entity);
		if (atrWspSubmission) {
			if (entity.getSubmissionDate() != null) {
				submissionDate = entity.getSubmissionDate();
			} else {
				WspSignoffService wspSignoffService = new WspSignoffService();
				submissionDate    = wspSignoffService.returnLastestSignOffDate(entity);
				wspSignoffService = null;
			}
			if (emailNotification == 1) {
				for (Users users : notificationUsers) {
					// Approved without appeal
					sendApprovalDiscretionaryGrantEmail(users, entity.getCompany(), submissionDate, null, false);
				}
			} else if (emailNotification == 2) {
				for (Users users : notificationUsers) {
					// Approved with Appeal submission from the SDF
					sendDiscretionaryGrantAppealOutcomeEmail(users, entity.getCompany(), submissionDate, null, false);
				}
			} else if (emailNotification == 3) {
				for (Users users : notificationUsers) {
					// Rejected for the SDF to accept or appeal grant application
					sentDiscretionaryGrantNonApprovalEmail(users, entity.getCompany(), submissionDate, rejectReasons, null, false);
				}
			} else if (emailNotification == 4) {
				for (Users users : notificationUsers) {
					// Rejected after appeal has been submitted by SDF
					sentDiscretionaryGrantAppealOutcomeEmail(users, entity.getCompany(), submissionDate, entity.getSdfAppealedGrantDate(), rejectReasons, null, false);
				}
			}
		}
	}

	/**
	 * Check Is WSP contains both WSP and ATR submissions If both submitted returns true else false
	 * @param entity
	 * @return boolean
	 * @throws Exception
	 */
	private boolean checkWspAtrSubmission(Wsp entity) throws Exception {
		if (mandatoryGrantDetailService.findByWSPCount(entity, WspReportEnum.WSP) > 0 && mandatoryGrantDetailService.findByWSPCount(entity, WspReportEnum.ATR) > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Locates the additional users to notify for the SDF notification
	 * @param entity
	 * @return List<Users>
	 * @throws Exception
	 */
	private List<Users> locateClientUsersNotification(Wsp entity) throws Exception {
		List<Users> users = new ArrayList<Users>();
		if (sdfCompanyService == null) {
			sdfCompanyService = new SDFCompanyService();
		}
		users.add(sdfCompanyService.findPrimarySDF(entity.getCompany()).getSdf());
		if (companyUsersService == null) {
			companyUsersService = new CompanyUsersService();
		}
		/*
		 * Send list HR Manager (if provided), Training Manager (if provided),
		 */
		// locate Labour/Union SDF
		List<SDFCompany> sdfList = sdfCompanyService.findByCompanyAndSdfType(entity.getCompany(), (long) 3);
		for (SDFCompany sdfCompany : sdfList) {
			users.add(sdfCompany.getSdf());
			break;
		}
		// locate Employee SDF
		sdfList = sdfCompanyService.findByCompanyAndSdfType(entity.getCompany(), (long) 4);
		for (SDFCompany sdfCompany : sdfList) {
			users.add(sdfCompany.getSdf());
			break;
		}
		sdfList = null;
		// HR manager
		List<CompanyUsers> cu = companyUsersService.findByCompanyResponsibilityAndUserPosition(entity.getCompany(), ConfigDocProcessEnum.DG_VERIFICATION, (long) 3);
		for (CompanyUsers companyUsers : cu) {
			users.add(companyUsers.getUser());
			break;
		}
		// Training Manager
		cu = companyUsersService.findByCompanyResponsibilityAndUserPosition(entity.getCompany(), ConfigDocProcessEnum.DG_VERIFICATION, (long) 4);
		for (CompanyUsers companyUsers : cu) {
			users.add(companyUsers.getUser());
			break;
		}
		return users;
	}

	/**
	 * Approval Discretionary Grant outcome email notification Approved without appeal
	 */
	public void sendApprovalDiscretionaryGrantEmail(Users user, Company company, Date submissiondate, String emailAddress, boolean adminTest) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
		params.put("submissiondate", sdf.format(submissiondate));
		params.put("company_id", company.getId());
		params.put("user_id", user.getId());
		if (user.getTitle() != null) {
			params.put("title", user.getTitle().getDescription());
		} else {
			params.put("title", "");
		}
		String filename = company.getLevyNumber() + "_Approval_Discretionary_Grant_Outcome.pdf";
		byte[] bites    = JasperService.instance().convertJasperReportToByte("CSD-TP-ApprovalOfDiscretionaryGrant.jasper", params);
		String subject  = "DISCRETIONARY GRANT OUTCOME FOR " + company.getCompanyName().toUpperCase() + " - " + company.getLevyNumber() + "";
		String mssg     = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear ";
		if (user.getTitle() != null) {
			mssg += user.getTitle().getDescription() + " ";
		}
		mssg += "" + user.getFirstName() + "  " + user.getLastName() + ",</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "We confirm that the company has submitted its Discretionary Grant " + "application including the Workplace Skills Plan and Annual " + "Training Report on " + sdf.format(submissiondate) + "." + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Kindly note that the discretionary grant application for " + company.getCompanyName().toUpperCase()
				+ " has been <b>approved</b>.  " + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please keep this Discretionary Grant Approval letter for BBBEE" + " verification and BBBEE certification application purposes." + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"><b>Cathsseta Administration</b></p>";
		// user.getEmail()
		if (!adminTest) {
			GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, filename, "pdf", null);
		} else {
			GenericUtility.sendMailWithAttachement(emailAddress, subject, mssg, bites, filename, "pdf", null);
		}

	}

	/**
	 * Discretionary Grant Appeal Outcome Approved with Appeal
	 */
	public void sendDiscretionaryGrantAppealOutcomeEmail(Users user, Company company, Date submissiondate, String emailAddress, boolean adminTest) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
		params.put("submissiondate", sdf.format(submissiondate));
		params.put("company_id", company.getId());
		params.put("user_id", user.getId());
		if (user.getTitle() != null) {
			params.put("title", user.getTitle().getDescription());
		} else {
			params.put("title", "");
		}

		String filename = company.getLevyNumber() + "_Discretionary_Grant_Appeal_Outcome.pdf";
		byte[] bites    = JasperService.instance().convertJasperReportToByte("CSD-TP-DiscretionaryGrantNon-Approval.jasper", params);
		String subject  = "DISCRETIONARY GRANT APPEAL OUTCOME FOR " + company.getCompanyName().toUpperCase() + " - " + company.getLevyNumber() + "";
		String mssg     = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear ";
		if (user.getTitle() != null) {
			mssg += user.getTitle().getDescription() + " ";
		}
		mssg += user.getFirstName() + "  " + user.getLastName() + ",</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "We confirm that the company has submitted its Discretionary " + "Grant application including the Workplace Skills Plan " + "and Annual Training Report on " + sdf.format(submissiondate) + "." + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "We wish to inform you that the Cathsseta has processed the "
				+ "Discretionary Grant application appeal. The outcome of your " + "appeal is <b>successful</b> and the Discretionary Grant application " + "for " + company.getCompanyName() + " has been approved.  " + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please keep this Discretionary Grant Approval letter for " + "BBBEE verification and BBBEE certification application purposes." + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"><b>Cathsseta Administration</b></p>";

		if (!adminTest) {
			GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, filename, "pdf", null);
		} else {
			GenericUtility.sendMailWithAttachement(emailAddress, subject, mssg, bites, filename, "pdf", null);
		}
	}

	/**
	 * Approval Discretionary Grant Non Approval Email notification Rejected before Appeal
	 */
	public void sentDiscretionaryGrantNonApprovalEmail(Users user, Company company, Date submissiondate, List<RejectReasons> rejectReasons, String emailAddress, boolean adminTest) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
		params.put("submissiondate", sdf.format(submissiondate));
		params.put("company_id", company.getId());
		params.put("user_id", user.getId());
		params.put("rejectReasonDataSource", new JRBeanCollectionDataSource(rejectReasons));
		if (user.getTitle() != null) {
			params.put("title", user.getTitle().getDescription());
		} else {
			params.put("title", "");
		}
		byte[] bites   = JasperService.instance().convertJasperReportToByte("CSD-TP-DISCRETIONARY GRANT NON-APPROVAL.jasper", params);
		String subject = "DISCRETIONARY GRANT OUTCOME FOR  " + company.getCompanyName() + " - " + company.getLevyNumber() + " ";
		String mssg    = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear ";
		if (user.getTitle() != null) {
			mssg += user.getTitle().getDescription() + " ";
		}
		mssg += user.getFirstName() + " " + user.getLastName() + ",</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "We confirm that the company has submitted its Discretionary Grant application including the Workplace Skills Plan and Annual Training Report on " + sdf.format(submissiondate) + "." + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Kindly note that the discretionary grant application for " + company.getCompanyName() + " has not been approved" + "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The application will be kept for record purposes. " + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please be advised that should you wish to appeal the Discretionary Grant application outcome, please submit an appeal on the CIMS within 14 days of receipt of this notification." + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Should you have any queries, please do not hesitate to contact the Cathsseta Call Centre on: 086 163 7738 for assistance. " + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Cathsseta Administration</p>";
		if (!adminTest) {
			GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "CSD-TP-DISCRETIONARY GRANT NON-APPROVAL.pdf", "pdf", null);
		} else {
			GenericUtility.sendMailWithAttachement(emailAddress, subject, mssg, bites, "CSD-TP-DISCRETIONARY GRANT NON-APPROVAL.pdf", "pdf", null);
		}

	}

	/**
	 * Approval Discretionary Grant Non Approval Email notification DISCRETIONARY GRANT APPEAL OUTCOME Rejected after appeal
	 */
	public void sentDiscretionaryGrantAppealOutcomeEmail(Users user, Company company, Date submissiondate, Date appealsubmissiondate, List<RejectReasons> rejectReasons, String emailAddress, boolean adminTest) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
		params.put("submissiondate", sdf.format(submissiondate));
		params.put("appealsubmissiondate", sdf.format(appealsubmissiondate));
		params.put("company_id", company.getId());
		params.put("user_id", user.getId());
		params.put("rejectReasonDataSource", new JRBeanCollectionDataSource(rejectReasons));
		if (user.getTitle() != null) {
			params.put("title", user.getTitle().getDescription());
		} else {
			params.put("title", "");
		}
		byte[] bites   = JasperService.instance().convertJasperReportToByte("CSD-TP DISCRETIONARY GRANT_NON-APPROVAL APPEAL.jasper", params);
		String subject = "DISCRETIONARY GRANT APPEAL OUTCOME FOR  " + company.getCompanyName() + " - " + company.getLevyNumber() + " ";
		String mssg    = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear ";
		if (user.getTitle() != null) {
			mssg += user.getTitle().getDescription() + " ";
		}
		mssg += user.getFirstName() + " " + user.getLastName() + ",</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "We confirm that the company has submitted its Discretionary Grant application including the Workplace Skills Plan and Annual Training Report on " + sdf.format(submissiondate) + "." + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Kindly note that the appeal for the Discretionary Grant application for " + company.getCompanyName() + " is unsuccessful."
				+ "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The application will be kept for record purposes. " + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Should you have any queries, please do not hesitate to contact the Cathsseta Call Centre on: 086 163 7738 for assistance. " + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Cathsseta Administration</p>";

		if (!adminTest) {
			GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "CSD-TP DISCRETIONARY GRANT_NON-APPROVAL APPEAL.pdf", "pdf", null);
		} else {
			GenericUtility.sendMailWithAttachement(emailAddress, subject, mssg, bites, "CSD-TP DISCRETIONARY GRANT_NON-APPROVAL APPEAL.pdf", "pdf", null);
		}

	}

	// //validateWSP(entity, users, false, false, false, true, false, false);
	// List<IDataEntity> dataEntities = new ArrayList<>();
	// dataEntities.add(entity);
	// boolean changePending = true;
	// for (WspSignoff wspSignoff : entity.getWspSignoffs()) {
	//
	// if (wspSignoff.getUser().equals(users)) {
	// wspSignoff.setSignOffDate(getSynchronizedDate());
	// }
	//
	// dataEntities.add(wspSignoff);
	// if ((wspSignoff.getAccept() == null || !wspSignoff.getAccept()) &&
	// (wspSignoff.getDispute() == null || !wspSignoff.getDispute())) {
	// changePending = false;
	// }
	// }
	//
	// TasksService.instance().completeTask(tasks);
	//
	// if (changePending) {
	// entity.setWspStatusEnum(WspStatusEnum.Pending);
	// String message = "Grant Application for " +
	// entity.getCompany().getCompanyName() + " has been submitted and signed off
	// please review.";
	// TasksService.instance().findFirstInProcessAndCreateTask(message, users,
	// entity.getId(), entity.getClass().getName(), true,
	// hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA),
	// ConfigDocProcessEnum.DG_APPLICATION,
	// MailDef.instance(MailEnum.WSPRequiresSignOff,
	// MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()), null);
	// }
	// dao.updateBatch(dataEntities);

	public void downloadMOA(DgAllocationParent dgAllocationParent, Users user, Users clo, String cloRegion) {
		try {

			String cloFullName = clo.getFirstName() + " " + clo.getLastName();
			String cloEmail    = clo.getEmail();

			Wsp                 wsp    = dgAllocationParent.getWsp();
			Map<String, Object> params = new HashMap<String, Object>();
			String              path   = HAJConstants.APP_PATH;
			// Creating Discretionary grant year to be displayed on the cover page
			String lastTwoDigitsOfNextYear = String.valueOf(wsp.getFinYear()).substring(Math.max(String.valueOf(wsp.getFinYear()).length() - 2, 0));
			String year                    = wsp.getFinYear() - 1 + "/" + lastTwoDigitsOfNextYear + " (Yr" + lastTwoDigitsOfNextYear + ")";
			// Adding Discretionary_Grant_Agreeement_details params

			RegionTown rt = RegionTownService.instance().findByTown(wsp.getCompany().getResidentialAddress().getTown());
			if (regionService == null) {
				regionService = new RegionService();
			}
			Region r = regionService.findByKey(rt.getRegion().getId());

			// ***************************Dg Allocation Data*****************************
			ArrayList<DgAllocation> dgList              = new ArrayList<>();
			DgAllocationService     dgAllocationService = new DgAllocationService();
			if (wsp.getId() != null) {
				if (dgAllocationParent != null) {
					dgList = (ArrayList<DgAllocation>) dgAllocationService.findByParentWhereAmountAwarded(dgAllocationParent);
				}

			}
			params.put("DgAllocationDataSource", new JRBeanCollectionDataSource(dgList));
			params.put("AnnexureDataSource", new JRBeanCollectionDataSource(dgList));

			params.put("total_awarded_amnt", dgAllocationService.findTotalAllocatedWhereAmountAwarded(dgAllocationParent).doubleValue());

			params.put("wsp", wsp);
			params.put("company", companyService.findByKey(wsp.getCompany().getId()));
			params.put("wsp_id", wsp.getId());
			params.put("wsp_report", WspReportEnum.WSP.ordinal());
			params.put("etqa_code", HAJConstants.HOSTING_MERSETA_ETQA);
			params.put("funding_id", HAJConstants.DISC_FUNDING_ID);
			params.put("region", r.getDescription());
			params.put("year", year);
			params.put("merSETA_CEO", "");// Not in User
			params.put("terminationDate", "30 September " + (wsp.getFinYear() + 4));
			params.put("projectedStartDate", "1 Jan " + wsp.getFinYear());
			params.put("projectedEndDate", "31 Mar " + (wsp.getFinYear() + 1));
			JasperService.addLogo(params);
			JasperService.addDiscretionaryGrantSubReports(params);
			String fileName = wsp.getCompany().getLevyNumber() + "-(Yr" + lastTwoDigitsOfNextYear + ")-Discretionary_Grant_Agreement.pdf";
			// Adding books
			if (wsp.getFinYearNonNull() > 2021){
				params.put("cover_page", path + "reports/newmoaver12/DG_Agreement_Book_cover.jasper");
				params.put("table_of_content", path + "reports/newmoaver12/DG_Agreement_Book_toc.jasper");
			}
			else {
				params.put("cover_page", path + "reports/DG_Agreement_Book_cover.jasper");
				params.put("table_of_content", path + "reports/DG_Agreement_Book_toc.jasper");
			}
			params.put("DGA_details", path + "reports/Discretionary_Grant_Agreeement_details.jasper");

			byte[] bites = JasperService.instance().convertJasperReportToByte("DG_Agreement_Book.jasper", params);

			String subject = "DISCRETIONARY GRANT APPLICATION OUTCOME FOR: " + wsp.getCompany().getCompanyName() + " (" + wsp.getCompany().getLevyNumber() + ")";
			String mssg    = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + ",</p>"

					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Thank you for submitting a discretionary grant application for " + wsp.getFinYear() + "</p>"

					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "We are pleased to inform you that the application was successful. " + "Please may you complete the project implementation plan on the CIMS " + "and submit together with the signed Memorandum of Agreement (MOA) " + "within 30 business days from the date of receipt." + "</p>"

					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please note that should the enclosed MOA is not uploaded on the " + "CIMS within 30 business days from the date of issue, the Cathsseta " + "may withdraw the MOA. " + "</p>"

					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "For further assistance/support, kindly contact the designated " + "Client Liaison Officer: " + cloFullName + " (Email: " + cloEmail + ") or the respective " + cloRegion + " Office." + "</p>"

					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours sincerely," + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "<b>The Cathsseta Programme Implementation Unit</b>" + "</p>";

			GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, fileName, "pdf", null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Date determineSignOffClosureDate(Wsp wsp, Date closureDate) throws Exception {
		// Date closureDate =
		// GenericUtility.getEndOfDay(GenericUtility.sdf6.parse("30-04-" +
		// (wsp.getFinYear() - 1)));
		// ensure in sign off state
		if (wsp.getWspStatusEnum() == WspStatusEnum.PendingSignOff) {

			// locate primary SDF sign off
			WspSignoff primarySDFSignoff = null;
			for (WspSignoff wspSignoff : wsp.getWspSignoffs()) {
				// locate primary sdf sign off
				if (wspSignoff.getSdfType().getId().equals(1l)) {
					primarySDFSignoff = wspSignoff;
					break;
				}
			}

			if (primarySDFSignoff != null) {
				// check primary SDF accepted and sign off date before close off date
				if ((primarySDFSignoff.getAccept() != null && primarySDFSignoff.getAccept()) && (primarySDFSignoff.getSignOffDate() != null && primarySDFSignoff.getSignOffDate().before(closureDate))) {
					// check if current date before final submission date
					Date dateForSubmission = GenericUtility.getEndOfDay(GenericUtility.sdf6.parse("15-07-" + (wsp.getFinYear() - 1)));
					if (dateForSubmission.after(closureDate)) {
						closureDate = dateForSubmission;
					}
				}
			}
		}
		return closureDate;
	}

	public boolean determineSignOffSubmission(Wsp wsp, Date closeOffDate, Users sessionUser, Date finalSubmissionDate) throws Exception {

		boolean submissionAvalible = false;
		finalSubmissionDate = GenericUtility.getEndOfDay(finalSubmissionDate);
		// ensure in sign off state
		if (wsp.getWspStatusEnum() == WspStatusEnum.PendingSignOff) {
			// locate primary SDF sign off
			WspSignoff primarySDFSignoff = null;
			for (WspSignoff wspSignoff : wsp.getWspSignoffs()) {
				// locate primary sdf sign off
				if (wspSignoff.getSdfType().getId().equals(1)) {
					primarySDFSignoff = wspSignoff;
					break;
				}
			}

			if (primarySDFSignoff == null) {

				submissionAvalible = false;

			} else {

				// check primary SDF accepted and sign off date before close off date
				if ((primarySDFSignoff.getAccept() != null && primarySDFSignoff.getAccept()) && (primarySDFSignoff.getSignOffDate() != null && primarySDFSignoff.getSignOffDate().before(closeOffDate))) {
					// check if current date before final submission date
					if (getSynchronizedDate().before(finalSubmissionDate)) {
						submissionAvalible = true;
					} else {
						submissionAvalible = false;
					}
				} else {
					submissionAvalible = false;
				}

			}
		}

		return submissionAvalible;
	}

	public void runValidiationCheckFirstSubmit(Wsp wsp) throws Exception {
		// populates additional data for WSP validations
		wsp = findByKey(wsp.getId());
		// Locates primary SDF
		SDFCompany primarySdf = sdfCompanyService.findPrimarySDF(wsp.getCompany());
		if (primarySdf == null) {
			throw new Exception("Unable to locate primary sdf for company: " + wsp.getCompany().getCompanyName());
		} else {
			// validateWSPAdmin(wsp, primarySdf.getSdf(), true, false, true, false, true, true, true);
		}
	}

	public List<Wsp> findWspAwaitingMancoApprovalByStatus(WspStatusEnum wspStatusEnum) throws Exception {
		return dao.findWspAwaitingMancoApprovalByStatus(wspStatusEnum);
	}

	public List<Wsp> findWspAwaitingMancoApproval() throws Exception {
		return dao.findWspAwaitingMancoApproval();
	}

	public List<Wsp> findWspAwaitingMancoApprovalByFinYear(Integer finYear) throws Exception {
		return dao.findWspAwaitingMancoApprovalByFinYear(finYear);
	}

	public List<Wsp> findWspByStatusAndFinYear(WspStatusEnum wspStatus, Integer finYear) throws Exception {
		return dao.findWspByStatusAndFinYear(wspStatus, finYear);
	}

	public boolean determainDgVerificationGenerationRejectedGrant(Wsp wsp) throws Exception {
		boolean canGenerateVerification = false;
		Company company                 = companyService.findByKey(wsp.getCompany().getId());
		// small companies less than 50 employees
		if (company != null && company.getId() != null && company.getNumberOfEmployees() != null && company.getNumberOfEmployees() < 50) {
			canGenerateVerification = true;
		}
		company = null;
		return canGenerateVerification;
	}

	/*
	 * ADMIN functionality to re-send or view email notification with letters being sent out for the grant process. If re-send true will re-send notification to applicable users (CAUTION WILL SEND TO SDF AND COMPANY CONTACTS). If re-send false will send to email address provided and will use first user as holding place. Rejection reasons must be provided in order to work for the re-send false. If re-send true will find Latest assigned rejection reasons and send through. will error if unable to
	 * find.
	 */
	public void sendReleventNotificationToUsersAdmin(Wsp entity, Integer emailNotification, List<RejectReasons> rejectReasons, String emailAddress, boolean resend) throws Exception {
		if (resend) {
			RejectReasonsService rrs = new RejectReasonsService();
			rejectReasons = rrs.locateReasonsSelectedByTargetKeyClassAndProcess(entity.getId(), Wsp.class.getName(), ConfigDocProcessEnum.DG_APPLICATION);
		}
		List<Users> notificationUsers = locateClientUsersNotification(entity);
		Date        submissionDate    = null;
		boolean     atrWspSubmission  = checkWspAtrSubmission(entity);
		if (atrWspSubmission) {
			if (entity.getSubmissionDate() != null) {
				submissionDate = entity.getSubmissionDate();
			} else {
				WspSignoffService wspSignoffService = new WspSignoffService();
				submissionDate    = wspSignoffService.returnLastestSignOffDate(entity);
				wspSignoffService = null;
			}
			if (emailNotification == 1) {

				// Approved without appeal

				if (resend) {
					for (Users users : notificationUsers) {
						sendApprovalDiscretionaryGrantEmail(users, entity.getCompany(), submissionDate, null, false);
					}
				} else {
					for (Users users : notificationUsers) {
						sendApprovalDiscretionaryGrantEmail(users, entity.getCompany(), submissionDate, emailAddress.trim(), true);
						break;
					}
				}
			} else if (emailNotification == 2) {

				// Approved with Appeal submission from the SDF

				if (resend) {
					for (Users users : notificationUsers) {
						sendDiscretionaryGrantAppealOutcomeEmail(users, entity.getCompany(), submissionDate, null, false);
					}
				} else {
					for (Users users : notificationUsers) {
						sendDiscretionaryGrantAppealOutcomeEmail(users, entity.getCompany(), submissionDate, emailAddress.trim(), true);
						break;
					}
				}
			} else if (emailNotification == 3) {

				// Rejected for the SDF to accept or appeal grant application

				if (rejectReasons == null) {
					throw new Exception("Unable to locate rejection reasons. Notification failed!");
				}

				if (resend) {
					for (Users users : notificationUsers) {
						sentDiscretionaryGrantNonApprovalEmail(users, entity.getCompany(), submissionDate, rejectReasons, null, false);
					}
				} else {
					for (Users users : notificationUsers) {
						sentDiscretionaryGrantNonApprovalEmail(users, entity.getCompany(), submissionDate, rejectReasons, emailAddress.trim(), true);
						break;
					}
				}
			} else if (emailNotification == 4) {

				// Rejected after appeal has been submitted by SDF

				if (rejectReasons == null) {
					throw new Exception("Unable to locate rejection reasons. Notification failed!");
				}

				if (resend) {
					for (Users users : notificationUsers) {
						sentDiscretionaryGrantAppealOutcomeEmail(users, entity.getCompany(), submissionDate, entity.getSdfAppealedGrantDate(), rejectReasons, null, false);
					}
				} else {
					for (Users users : notificationUsers) {
						sentDiscretionaryGrantAppealOutcomeEmail(users, entity.getCompany(), submissionDate, entity.getSdfAppealedGrantDate(), rejectReasons, emailAddress.trim(), true);
						break;
					}
				}
			}
		}
	}

	/**
	 * Schedule Service Start
	 */
	public List<Wsp> findWspAwaitingDescionBySdfRejectionAppeal() throws Exception {
		return dao.findWspAwaitingDescionBySdfRejectionAppeal();
	}

	public void scheduleServiceWsp() throws Exception {
		/*
		 * Business rule: 14 days to complete from task being sent Example: - Manager rejects to SDF on 1st of September 2018 - 2nd, 3rd, 4th, 5th, 6th, 7th, 8th, 9th, 10th, 11th, 12th, 13th, 14th, 15th of September 2018 they can action - System must auto reject grant application on the 16th of September 2018 (morning schedule configuration) Java daysToComplate: 15
		 */
		Integer          daysToComplate         = 15;
		SimpleDateFormat sdf                    = new SimpleDateFormat("dd MMMM yyyy");
		Date             today                  = GenericUtility.getStartOfDay(new Date());
		List<Wsp>        wspRejecetdAwaitingSdf = findWspAwaitingDescionBySdfRejectionAppeal();
		for (Wsp wspRejected : wspRejecetdAwaitingSdf) {
			if (wspRejected.getGrantRejectedDate() != null) {
				Date   compltedByDateIncWeekends = GenericUtility.addDaysToDate(GenericUtility.getStartOfDay(wspRejected.getGrantRejectedDate()), daysToComplate);
				String information               = "14 Day System Rejection Business Rule For: Grant Application. Date SDF Received Application: " + sdf.format(wspRejected.getGrantRejectedDate()) + ".";
				if (today.equals(compltedByDateIncWeekends) || today.after(compltedByDateIncWeekends)) {
					information += " Changes: status change from " + wspRejected.getWspStatusEnum().getFriendlyName() + " to " + WspStatusEnum.Rejected.getFriendlyName() + ",";
					information += " set approved date to: " + sdf.format(getSynchronizedDate()) + ".";
					List<IDataEntity> dataEntities = new ArrayList<>();
					wspRejected.setSystemApprovalRejection(true);
					wspRejected.setWithSdf(false);
					wspRejected.setWspStatusEnum(WspStatusEnum.Rejected);
					wspRejected.setApprovedDate(getSynchronizedDate());
					dataEntities.add(wspRejected);
					if (dataEntities.size() != 0) {
						this.dao.updateBatch(dataEntities);
					}
					TasksService.instance().completeTaskByTargetKey(Wsp.class.getName(), wspRejected.getId());
					boolean generateVerification = determainDgVerificationGenerationRejectedGrant(wspRejected);
					if (generateVerification) {
						DgVerificationService.instance().generateForRejectedWspSmallCompany(wspRejected);
					}
					// creates log
					if (wspRejected != null && wspRejected.getCompany() != null) {
						ScheduleChangesLogService.instance().addNewEntry(wspRejected.getCompany(), wspRejected.getClass().getName(), wspRejected.getId(), information, false);
					} else {
						ScheduleChangesLogService.instance().addNewEntry(null, wspRejected.getClass().getName(), wspRejected.getId(), information, false);
					}
				}
				// old method
				// Integer daysBetweenDates =
				// GenericUtility.getDaysBetweenDates(GenericUtility.getStartOfDay(wsp.getGrantRejectedDate()),
				// today);
				// if (daysBetweenDates >= 5) {
				//
				// }
			}
		}
	}

	/**
	 * Schedule Service End
	 */

	/**
	 * Reporting Start
	 */

	public List<Integer> findDictinctFinYears() throws Exception {
		return dao.findDictinctFinYears();
	}

	/**
	 * Wsp Report By Financial Year Note: If finYear is null wont filter by financial year Note: if List of status not provided wont filter by status
	 * @param first
	 * @param pageSize
	 * @param sortField
	 * @param sortOrder
	 * @param filters
	 * @param finYear
	 * @param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Wsp> wspReportByFinancialYear(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Integer finYear, List<WspStatusEnum> statusList) throws Exception {
		String hql = "select o from Wsp o where o.company is not null";
		if (statusList != null) {

		}
		if (finYear != null) {
			hql += " and o.finYear = :finYear ";
			filters.put("finYear", finYear);
		}
		return resolveExtension((List<Wsp>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countWspReportByFinancialYear(Map<String, Object> filters, Integer finYear, List<WspStatusEnum> statusList) throws Exception {
		String hql = "select count(o) from Wsp o where o.company is not null";
		if (finYear != null) {
			hql += " and o.finYear = :finYear ";
		}
		return dao.countWhere(filters, hql);
	}

	public Integer countWspByStatusAndFinYear(WspStatusEnum status, List<Integer> finYear) throws Exception {
		return dao.countWspByStatusAndFinYear(status, finYear);
	}

	public Integer countWspByStatusAndFinYear(WspStatusEnum status, Integer finYear) throws Exception {
		return dao.countWspByStatusAndFinYear(status, finYear);
	}

	public Integer countAllDgFinYear(List<Integer> finYear) throws Exception {
		return dao.countAllDgFinYear(finYear);
	}

	public Integer countAllDgFinYear(Integer finYear) throws Exception {
		return dao.countAllDgFinYear(finYear);
	}

	/**
	 * Populates status count report for WSP
	 * @param finYear
	 * @return
	 * @throws Exception
	 */
	public List<StatusReportBean> populateStatusReportWsp(List<Integer> finYear) throws Exception {
		List<StatusReportBean> reportList = new ArrayList<>();
		reportList.add(new StatusReportBean("Total Grant Applications", countAllDgFinYear(finYear)));
		for (WspStatusEnum status : WspStatusEnum.values()) {
			if (status != WspStatusEnum.ManagerApproved) {
				if (status != WspStatusEnum.ManagerRejected) {
					if (status != WspStatusEnum.OpenedForReview) {
						if (status != WspStatusEnum.NA) {
							reportList.add(new StatusReportBean(status.getFriendlyName(), countWspByStatusAndFinYear(status, finYear)));
						}
					}
				}
			}
		}
		return reportList;
	}

	public List<StatusReportBean> populateStatusReportWspByYear(Integer finYear) throws Exception {
		List<StatusReportBean> reportList = new ArrayList<>();
		reportList.add(new StatusReportBean("Total Grant Applications", countAllDgFinYear(finYear)));
		for (WspStatusEnum status : WspStatusEnum.values()) {
			if (status != WspStatusEnum.ManagerApproved) {
				if (status != WspStatusEnum.ManagerRejected) {
					if (status != WspStatusEnum.OpenedForReview) {
						if (status != WspStatusEnum.NA) {
							reportList.add(new StatusReportBean(status.getFriendlyName(), countWspByStatusAndFinYear(status, finYear)));
						}
					}
				}
			}
		}
		return reportList;
	}

	public void findLastActionUser(Wsp wsp) throws Exception {
		List<Tasks> list = tasksService.findTasksByTypeAndKey(ConfigDocProcessEnum.DG_APPLICATION, wsp.getClass().getName(), wsp.getId());
		if (list.size() > 0) {
			if (wsp.getSystemApprovalRejection() != null && wsp.getSystemApprovalRejection()) {
				Users u = new Users();
				u.setFirstName("System");
				u.setLastName("Rejected");
				wsp.setLastActionUser(u);
			} else if (wsp.getSystemApprovalRejection() == null && wsp.getWspStatusEnum() == WspStatusEnum.Rejected) {
				for (Tasks task : list) {
					if (task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalApproval) {
						wsp.setLastActionUser(task.getActionUser());
						break;
					}
				}
			} else if (wsp.getWspStatusEnum() == WspStatusEnum.Approved) {
				if (list.get(0).getProcessRole().getRolePermission() == UserPermissionEnum.FinalApproval) {
					wsp.setLastActionUser(list.get(0).getActionUser());
				} else {
					wsp.setLastActionUser(new Users());
				}
			}
		} else {
			wsp.setLastActionUser(new Users());
		}
	}

	/**
	 * Wsp Report By Financial Year
	 */
	@SuppressWarnings("unchecked")
	public List<Wsp> wspByFinancialYear(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from Wsp o where o.finYear = :year";
		return (List<Wsp>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countWspByFinancialYear(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Wsp o where o.finYear = :year";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<Wsp> wspByFinancialYearRange(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from Wsp o where o.finYear >= :fromYear and o.finYear <= :toYear";
		return (List<Wsp>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countWspByFinancialYearRange(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Wsp o where o.finYear >= :fromYear and o.finYear <= :toYear";
		return dao.countWhere(filters, hql);
	}

	public List<WspHistoryBean> wspHistoryData(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, WSPSearchType searchType, Company company, Integer finYear, Integer fromYear, Integer toYear) {
		String                hql                = "";
		List<Wsp>             wspList            = new ArrayList<>();
		List<WspHistoricData> wspHistoryBeanList = new ArrayList<>();

		if (searchType == WSPSearchType.searchByCompany) {
			filters.put("companyKey", company.getId());
			hql     = "select o from Wsp o where o.company.id = :companyKey";
			wspList = (List<Wsp>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);

			filters.put("companyKey", company.getLevyNumber());
			hql                = "select o from WspHistoricData o where o.referenceNo = :companyKey";
			wspHistoryBeanList = (List<WspHistoricData>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);

		} else if (searchType == WSPSearchType.searchByFinancialYear) {
			filters.put("finYear", finYear);
			hql     = "select o from Wsp o where o.finYear = :finYear";
			wspList = (List<Wsp>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);

			hql                = "select o from WspHistoricData o where o.levyYear = :finYear";
			wspHistoryBeanList = (List<WspHistoricData>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
		} else if (searchType == WSPSearchType.searchByYearRange) {
			filters.put("fromYear", fromYear);
			filters.put("toYear", toYear);
			hql     = "select o from Wsp o where o.finYear >= :fromYear and o.finYear <= :toYear";
			wspList = (List<Wsp>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);

			hql                = "select o from WspHistoricData o where o.levyYear >= :fromYear and o.levyYear <= :toYear";
			wspHistoryBeanList = (List<WspHistoricData>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
		}

		return prepareHistoryBean(wspList, wspHistoryBeanList);
	}

	public int wspHistoryDataCount(Map<String, Object> filters, WSPSearchType searchType, Company company, Integer finYear, Integer fromYear, Integer toYear) {
		String                hql                = "";
		List<Wsp>             wspList            = new ArrayList<>();
		List<WspHistoricData> wspHistoryBeanList = new ArrayList<>();
		int                   totalCount         = 0;
		if (searchType == WSPSearchType.searchByCompany) {
			filters.put("companyKey", company.getId());
			hql = "select count(o) from Wsp o where o.company.id = :companyKey";
			int count1 = dao.countWhere(filters, hql);

			filters.put("companyKey", company.getLevyNumber());
			hql = "select count(o) from WspHistoricData o where o.referenceNo = :companyKey";
			int count2 = dao.countWhere(filters, hql);
			totalCount = count1 + count2;

		} else if (searchType == WSPSearchType.searchByFinancialYear) {
			filters.put("finYear", finYear);
			hql = "select count(o) from Wsp o where o.finYear = :finYear";
			int count1 = dao.countWhere(filters, hql);

			hql = "select count(o) from WspHistoricData o where o.levyYear = :finYear";
			int count2 = dao.countWhere(filters, hql);
			totalCount = count1 + count2;

		} else if (searchType == WSPSearchType.searchByYearRange) {
			filters.put("fromYear", fromYear);
			filters.put("toYear", toYear);
			hql = "select count(o) from Wsp o where o.finYear >= :fromYear and o.finYear <= :toYear";
			int count1 = dao.countWhere(filters, hql);

			hql = "select count(o) from WspHistoricData o where o.levyYear >= :fromYear and o.levyYear <= :toYear";
			int count2 = dao.countWhere(filters, hql);
			totalCount = count1 + count2;
		}

		return totalCount;
	}

	public List<Wsp> findWspbyFinYearApprovedAndApprovedVerification(Integer finYear) throws Exception {
		return dao.findWspbyFinYearApprovedAndApprovedVerification(finYear);
	}

	public List<Wsp> findWspbyFinYearAndInVerification(Integer finYear) throws Exception {
		return dao.findWspbyFinYearAndInVerification(finYear);
	}

	/**
	 * Reporting End
	 */

	/*
	 * For once off fix, not to be used again
	 */
	public List<ByChamberReportBean> locateWspIDsThatNeedGenerationQuickFix() throws Exception {
		return dao.locateWspIDsThatNeedGenerationQuickFix();
	}

	public List<Wsp> findByStatus(WspStatusEnum statusEnum) throws Exception {
		return dao.findByStatus(statusEnum);
	}

	public List<Wsp> allWspByStatusWithRecommendation(WspStatusEnum wspStatusEnum) throws Exception {
		return dao.allWspByStatusWithRecommendation(wspStatusEnum);
	}

	public List<Wsp> allWspByStatus(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<Wsp>) dao.allWspByStatus(first, pageSize, sortField, sortOrder, filters);
	}

	public int countWspByStatus(Map<String, Object> filters) throws Exception {
		return dao.countWspByStatus(filters);
	}

	public List<Wsp> allWspByStatusNotRecommended(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return dao.allWspByStatusNotRecommended(first, pageSize, sortField, sortOrder, filters);
	}

	public int countWspByStatusNotRecommended(Map<String, Object> filters) throws Exception {
		return dao.countWspByStatusNotRecommended(filters);
	}

	public List<Wsp> allWspByStatusProjectOwner(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return dao.allWspByStatusProjectOwner(first, pageSize, sortField, sortOrder, filters);
	}

	public int countWspByStatusProjectOwner(Map<String, Object> filters) throws Exception {
		return dao.countWspByStatusProjectOwner(filters);
	}

	public List<Wsp> allWspProjectOwner(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return dao.allWspProjectOwner(first, pageSize, sortField, sortOrder, filters);
	}

	public int countWspProjectOwner(Map<String, Object> filters) throws Exception {
		return dao.countWspProjectOwner(filters);
	}

	public void approveRecommendation(Users user) throws Exception {
		List<Wsp> forApproval = allWspByStatusWithRecommendation(WspStatusEnum.ProjectReview);
		for (Wsp wsp : forApproval) {
			if (wsp.getApprovedAmount() == null) {
				wsp.setApprovedAmount(wsp.getEstimatedOverallProjectCost());
			}
			switch (wsp.getHoldingRoomStatusEnum()) {
				case RECOMMENDED:
					sendToProjectOwner(wsp, user, null, null);
					break;
				case NOT_RECOMMENDED:
					sendToProjectOwner(wsp, user, null, null);
					break;
				case WITHDRAWAL_BY_THE_APPLICANT:
					withDraw(wsp, user);
					break;
				case RECOMMENDED_SUBJECT_TO_PROPOSAL_AMENDMENTS:
					reopenGrant(wsp, user);
					break;
				case RE_ASSIGNED_WITH_MER_SETA:
					sendToHoldingRoom(wsp, user);
					break;
				default:
					break;
			}
		}
	}

	public void sendToHoldingRoom(Wsp entity, Users user) throws Exception {
		entity.setWspStatusEnum(WspStatusEnum.ProjectReview);
		entity.setLastActionUser(user);
		entity.setWithSdf(true);
		entity.setMancoDecisionReached(false);
		entity.setHoldingRoomStatusEnum(null);
		create(entity);
	}

	public void reopenGrant(Wsp entity, Users user) throws Exception {
		entity.setWspStatusEnum(WspStatusEnum.Draft);
		entity.setLastActionUser(user);
		entity.setWithSdf(true);
		entity.setMancoDecisionReached(false);
		create(entity);
	}

	public void withDraw(Wsp entity, Users user) throws Exception {
		entity.setWspStatusEnum(WspStatusEnum.Withdrawn);
		entity.setLastActionUser(user);
		entity.setWithSdf(false);
		entity.setMancoDecisionReached(false);
		create(entity);
	}

	public void reject(Wsp entity, Users user) throws Exception {
		entity.setWspStatusEnum(WspStatusEnum.Rejected);
		entity.setLastActionUser(user);
		entity.setWithSdf(false);
		entity.setMancoDecisionReached(true);
		entity.setGrantRejected(true);
		entity.setGrantRejectedDate(getSynchronizedDate());
		create(entity);
	}

	public void sendToProjectOwner(Wsp entity, Users user, Tasks tasks, List<RejectReasons> selectedRejectReasons) throws Exception {
		if (selectedRejectReasons != null && selectedRejectReasons.size() != 0) {
			setRejectedReasons(entity.getClass().getName(), entity.getId(), user, selectedRejectReasons, ConfigDocProcessEnum.DG_APPLICATION);
		}
		entity.setWspStatusEnum(WspStatusEnum.AwaitingContract);
		entity.setLastActionUser(user);
		entity.setWithSdf(false);
		entity.setMancoDecisionReached(true);
		List<Users> users = new ArrayList<>();
		users.add(entity.getProjectOwner());
		String message = "Grant Application for " + entity.getCompany().getCompanyName() + " has been submitted and with a sign off upload please review.";
		TasksService.instance().findFirstInProcessAndCreateTask(message, user, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.DG_APPLICATION, MailDef.instance(MailEnum.WSPSubmittedAndSignedReview, MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()), users);
		TasksService.instance().completeTask(tasks);
		create(entity);
	}

	public void sendToManager(Wsp entity, Users user, Tasks tasks, boolean reject, List<RejectReasons> selectedRejectReasons) throws Exception {
		if (selectedRejectReasons != null && selectedRejectReasons.size() != 0) {
			setRejectedReasons(entity.getClass().getName(), entity.getId(), user, selectedRejectReasons, ConfigDocProcessEnum.DG_APPLICATION);
		}
		entity.setWspStatusEnum(WspStatusEnum.PendingManagerReview);
		entity.setLastActionUser(user);
		entity.setWithSdf(false);
		entity.setMancoDecisionReached(true);
		String message = "Grant Application for " + entity.getCompany().getCompanyName() + " has been submitted and with a sign off upload please review.";
		if (reject) {
			TasksService.instance().findPreviousInProcessAndCreateTask(message, user, entity.getId(), Wsp.class.getName(), true, tasks, null, null);
		} else {
			TasksService.instance().findNextInProcessAndCreateTask(message, user, entity.getId(), Wsp.class.getName(), true, tasks, null, null);
		}
		create(entity);
	}

	public void sendToSeniorManager(Wsp entity, Users user, Tasks tasks, boolean reject, List<RejectReasons> selectedRejectReasons) throws Exception {
		if (selectedRejectReasons != null && selectedRejectReasons.size() != 0) {
			setRejectedReasons(entity.getClass().getName(), entity.getId(), user, selectedRejectReasons, ConfigDocProcessEnum.DG_APPLICATION);
		}
		entity.setWspStatusEnum(WspStatusEnum.ManagerApproved);
		entity.setLastActionUser(user);
		entity.setWithSdf(false);
		entity.setMancoDecisionReached(true);
		List<Users> users = new ArrayList<>();
		users.add(entity.getProjectManager());
		String message = "Grant Application for " + entity.getCompany().getCompanyName() + " has been submitted and with a sign off upload please review.";
		if (reject) {
			TasksService.instance().findPreviousInProcessAndCreateTask(message, user, entity.getId(), Wsp.class.getName(), true, tasks, null, users);
		} else {
			TasksService.instance().findNextInProcessAndCreateTask(message, user, entity.getId(), Wsp.class.getName(), true, tasks, null, users);
		}
		create(entity);
	}

	public void sendToCEO(Wsp entity, Users user, Tasks tasks, boolean reject) throws Exception {
		entity.setWspStatusEnum(WspStatusEnum.PendingCEOApproval);
		entity.setLastActionUser(user);
		entity.setWithSdf(false);
		entity.setMancoDecisionReached(true);
		List<Users> users   = hostingCompanyEmployeesService.findByAllActiveUsersByJobTitleId(HAJConstants.CEO_JOB_TITLE_ID);
		String      message = "Grant Application for " + entity.getCompany().getCompanyName() + " has been submitted and with a sign off upload please review.";
		if (reject) {
			TasksService.instance().findPreviousInProcessAndCreateTask(message, user, entity.getId(), Wsp.class.getName(), true, tasks, null, users);
		} else {
			TasksService.instance().findNextInProcessAndCreateTask(message, user, entity.getId(), Wsp.class.getName(), true, tasks, null, users);
		}
		create(entity);
	}

	public void completeApplication(Wsp entity, Users user, Tasks tasks) throws Exception {
		entity.setWspStatusEnum(WspStatusEnum.Approved);
		entity.setLastActionUser(user);
		entity.setWithSdf(false);
		entity.setMancoDecisionReached(true);
		List<Users> users = new ArrayList<>();
		users.add(entity.getProjectOwner());
		String message = "Grant Application for " + entity.getCompany().getCompanyName() + " has been submitted and with a sign off upload please review.";
		TasksService.instance().findNextInProcessAndCreateTask(message, user, entity.getId(), Wsp.class.getName(), true, tasks, null, users);
		create(entity);
	}

	public void completeWorkflow(Wsp entity, Users user, Tasks tasks, ActiveContracts activeContracts) throws Exception {
		entity.setWspStatusEnum(WspStatusEnum.Approved);
		entity.setLastActionUser(user);
		entity.setWithSdf(false);
		entity.setMancoDecisionReached(true);
		TasksService.instance().completeTask(tasks);
		create(entity);
		activeContractsService.generateContract(entity, activeContracts);
	}

}