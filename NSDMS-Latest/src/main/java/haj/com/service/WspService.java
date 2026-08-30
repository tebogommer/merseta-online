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

import haj.com.bean.CounterBean;
import haj.com.bean.GrantsSubmissionReportBean;
import haj.com.bean.StatusReportBean;
import haj.com.bean.WspCalData;
import haj.com.bean.WspCompanyReportBean;
import haj.com.bean.WspFirmSubmissionBean;
import haj.com.bean.WspHistoryBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.WspDAO;
import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.CompanyUsers;
import haj.com.entity.ConfigDoc;
import haj.com.entity.DgAllocation;
import haj.com.entity.DgAllocationParent;
import haj.com.entity.DgVerification;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.DocumentTracker;
import haj.com.entity.QmrFinYears;
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
import haj.com.entity.enums.CompanySizeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.DocumentTrackerEnum;
import haj.com.entity.enums.FinYearQuartersEnum;
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
import haj.com.entity.lookup.Roles;
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
import za.co.merseta.nsdms.framework.configuration.NSDMSConfiguration;

/**
 * The Class WspService.
 */
public class WspService extends AbstractService {
	
	/** The dao. */
	private WspDAO dao = new WspDAO();

	/** The doc service. */
	private DocService docService = new DocService();
	private CompanyService companyService = new CompanyService();

	/** The sdf company service. */
	private SDFCompanyService sdfCompanyService = new SDFCompanyService();

	/** The wsp signoff service. */
	private WspSignoffService wspSignoffService = new WspSignoffService();

	/** The config doc service. */
	private ConfigDocService configDocService = new ConfigDocService();

	/** The wsp skills requirements service. */
	private WspSkillsRequirementsService wspSkillsRequirementsService = new WspSkillsRequirementsService();

	private MandatoryGrantDetailService mandatoryGrantDetailService = new MandatoryGrantDetailService();

	/** The wsp skills gap service. */
	private WspSkillsGapService wspSkillsGapService = new WspSkillsGapService();

	private WspImpactOfStaffTrainingService impactOfStaffTrainingService2 = new WspImpactOfStaffTrainingService();

	private CompanyUsersService companyUsersService;
	private RegionService regionService;
	private TasksService tasksService = new TasksService();
	
	private WspHistoricDataService wspHistoricDataService=new WspHistoricDataService();
	
	
	/**
	 * Instantiates a new wsp service.
	 */
	public WspService() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new wsp service.
	 *
	 * @param auditlog
	 *            the auditlog
	 * @param resourceBundle
	 *            the resource bundle
	 */
	public WspService(Map<String, Object> auditlog, ResourceBundle resourceBundle) {
		super(auditlog, resourceBundle);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new wsp service.
	 *
	 * @param auditlog
	 *            the auditlog
	 */
	public WspService(Map<String, Object> auditlog) {
		super(auditlog);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new wsp service.
	 *
	 * @param resourceBundle
	 *            the resource bundle
	 */
	public WspService(ResourceBundle resourceBundle) {
		super(resourceBundle);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get all Wsp.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Wsp}
	 * @throws Exception
	 *             the exception
	 * @see Wsp
	 */
	public List<Wsp> allWsp() throws Exception {
		return dao.allWsp();
	}

	/**
	 * Create or update Wsp.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Wsp
	 */
	public void create(Wsp entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}
	
	public void updateIDataEntity(IDataEntity entity) throws Exception{
		dao.update(entity);
	}

	/**
	 * Creates the with sign off.
	 *
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 */
	public void createWithSignOff(Wsp entity) throws Exception {
		if (entity.getId() == null) {
			List<IDataEntity> dataEntities = new ArrayList<>();
			dataEntities.add(entity);
			if (entity.getWspType() != WspTypeEnum.LegacyLinkedGrantApplication) {
				List<SDFCompany> sdfCompanies = sdfCompanyService.findByCompanySignOff(entity.getCompany(), true);
				for (SDFCompany sdfCompany : sdfCompanies) {
					if (sdfCompany.getSdfType().getSignOnRecognition() == null) {
						WspSignoff signOff = new WspSignoff(entity, sdfCompany.getSdf());
						signOff.setSdfType(sdfCompany.getSdfType());
						dataEntities.add(signOff);
					} else if (sdfCompany.getSdfType().getSignOnRecognition().equals(sdfCompany.getCompany().getRecognitionAgreement())) {
						WspSignoff signOff = new WspSignoff(entity, sdfCompany.getSdf());
						signOff.setSdfType(sdfCompany.getSdfType());
						dataEntities.add(signOff);
					}
				}
			}

			if (entity.getWspType() == WspTypeEnum.Mandatory || entity.getWspType() == WspTypeEnum.Discretionary || entity.getWspType() == WspTypeEnum.Both || entity.getWspType() == WspTypeEnum.AdditionalFunding) {
				List<WspImpactOfStaffTraining> impactOfStaffTrainings = impactOfStaffTrainingService2.findByWspNull();
				for (WspImpactOfStaffTraining wspImpactOfStaffTraining : impactOfStaffTrainings) {
					WspImpactOfStaffTraining impactOfStaffTraining = (WspImpactOfStaffTraining) wspImpactOfStaffTraining.clone();
					impactOfStaffTraining.setId(null);
					impactOfStaffTraining.setCreateDate(null);
					impactOfStaffTraining.setWsp(entity);
					dataEntities.add(impactOfStaffTraining);
				}
			}

			WspChecklist wspChecklist = new WspChecklist();
			wspChecklist.setWsp(entity);
			dataEntities.add(wspChecklist);

			// creates WSP skill gap sections
			if (entity.getWspType() == WspTypeEnum.Mandatory || entity.getWspType() == WspTypeEnum.Discretionary || entity.getWspType() == WspTypeEnum.Both) {
				List<WspSkillsGap> skillsGapList = wspSkillsGapService.findByWspNull();
				for (WspSkillsGap wspSkillsGap : skillsGapList) {
					WspSkillsGap newWspSkillGap = (WspSkillsGap) wspSkillsGap.clone();
					newWspSkillGap.setId(null);
					newWspSkillGap.setCreateDate(null);
					newWspSkillGap.setWsp(entity);
					dataEntities.add(newWspSkillGap);
				}
			}
			
			dao.createBatch(dataEntities);
		} else {
			this.dao.update(entity);
		}
	}

	/**
	 * Creates Manual MG Application with manual sign off.
	 * JIRA #530
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 */
	public void createManualWspWithManualSignOff(Wsp entity) throws Exception {
		if (entity.getId() == null) {
			List<IDataEntity> dataEntities = new ArrayList<>();
			dataEntities.add(entity);
			if (entity.getWspType() != WspTypeEnum.LegacyLinkedGrantApplication) {
				List<SDFCompany> sdfCompanies = sdfCompanyService.findByCompanySignOff(entity.getCompany(), true);
				for (SDFCompany sdfCompany : sdfCompanies) {
					if (sdfCompany.getSdfType().getSignOnRecognition() == null) {
						WspSignoff signOff = new WspSignoff(entity, sdfCompany.getSdf());
						signOff.setSdfType(sdfCompany.getSdfType());
						signOff.setAccept(true);
						signOff.setSignOffDate(getSynchronizedDate());
						dataEntities.add(signOff);
					} else if (sdfCompany.getSdfType().getSignOnRecognition().equals(sdfCompany.getCompany().getRecognitionAgreement())) {
						WspSignoff signOff = new WspSignoff(entity, sdfCompany.getSdf());
						signOff.setSdfType(sdfCompany.getSdfType());
						signOff.setAccept(true);
						signOff.setSignOffDate(getSynchronizedDate());
						dataEntities.add(signOff);
					}
				}
			}

			if (entity.getWspType() == WspTypeEnum.Mandatory) {
				List<WspImpactOfStaffTraining> impactOfStaffTrainings = impactOfStaffTrainingService2.findByWspNull();
				for (WspImpactOfStaffTraining wspImpactOfStaffTraining : impactOfStaffTrainings) {
					WspImpactOfStaffTraining impactOfStaffTraining = (WspImpactOfStaffTraining) wspImpactOfStaffTraining.clone();
					impactOfStaffTraining.setId(null);
					impactOfStaffTraining.setCreateDate(null);
					impactOfStaffTraining.setWsp(entity);
					dataEntities.add(impactOfStaffTraining);
				}
			}

			WspChecklist wspChecklist = new WspChecklist();
			wspChecklist.setWsp(entity);
			dataEntities.add(wspChecklist);

			// creates WSP skill gap sections
			if (entity.getWspType() == WspTypeEnum.Mandatory) {
				List<WspSkillsGap> skillsGapList = wspSkillsGapService.findByWspNull();
				for (WspSkillsGap wspSkillsGap : skillsGapList) {
					WspSkillsGap newWspSkillGap = (WspSkillsGap) wspSkillsGap.clone();
					newWspSkillGap.setId(null);
					newWspSkillGap.setCreateDate(null);
					newWspSkillGap.setWsp(entity);
					dataEntities.add(newWspSkillGap);
				}
			}
			
			dao.createBatch(dataEntities);
		} else {
			this.dao.update(entity);
		}
	}
	
	public void createLinkedWsp(Wsp entity, Users sessionUser) throws Exception {
		if (entity.getId() == null) {
			
			// Doc Validations
			boolean error = false;
			String err = "";
			if (entity.getDocs() != null) {
				for (Doc doc : entity.getDocs()) {
					if (doc.getId() != null) {
						DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
						if (docByte != null) doc.setData(docByte.getData());
					}
					if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
						error = true;
						err = err + "Upload " + doc.getConfigDoc().getName() + " Before Proceeding.";
					}
				}
			}
			if (error) throw new Exception(err);
			
			
			entity.setSubmissionDate(getSynchronizedDate());
			entity.setWspStatusEnum(WspStatusEnum.Pending);
			List<IDataEntity> dataEntities = new ArrayList<>();
			
			dataEntities.add(entity);
			
			// Creates documents assigned to user
			if (entity.getDocs() != null) {
				for (Doc doc : entity.getDocs()) {
					doc.setVersionNo(1);
					doc.setUsers(sessionUser);
					dataEntities.add(doc);
					dataEntities.add(new DocByte(doc.getData(), doc));
					dataEntities.add(new DocumentTracker(doc, sessionUser, new java.util.Date(), DocumentTrackerEnum.Upload));
				}
			}


			WspChecklist wspChecklist = new WspChecklist();
			wspChecklist.setWsp(entity);
			dataEntities.add(wspChecklist);

			dao.createBatch(dataEntities);
			
			entity = findByKey(entity.getId());
			
			// creates task
			TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.WSP, entity.getId());
			String message = "Grant Application for " + entity.getCompany().getCompanyName() + " has been submitted. Please review.";
			TasksService.instance().findFirstInProcessAndCreateTask(message, sessionUser, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.WSP, MailDef.instance(MailEnum.WSPSubmittedAndSignedReview, MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()), null);
			WspCompanyHistoryCreateService.instance().clearAndPopulateWspCompanyHistory(entity.getCompany(), entity.getClass().getName(), entity.getId());
		} else this.dao.update(entity);
	}

	/**
	 * Update Wsp.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Wsp
	 */
	public void update(Wsp entity) throws Exception {
		this.dao.update(entity);
	}

	/* BEGIN WSP WORKFLOW METHOD */
	/**
	 * Submit WSP.
	 *
	 * @param entity
	 *            the entity
	 * @param users
	 *            the users
	 * @param createTask
	 *            the create task
	 * @throws Exception
	 *             the exception
	 */
	public void submitWSP(Wsp entity, Users users, boolean createTask) throws Exception {
		if (entity.getWspType() == WspTypeEnum.IntersetaTransferMandGrant) {
			validateWSP(entity, users, true, false, false, false, false, false, false);
		}else {
			validateWSP(entity, users, true, false, true, false, true, true, true);
		}
		
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
			TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.WSP, entity.getId());
			if (createTask) {
				String message = "Grant Application for " + entity.getCompany().getCompanyName() + " has been submitted and requires your sign off.";
				TasksService.instance().createTaskEachUser(entity.getClass().getName(), entity.getCompany(), message, "WSP form requires sign off", message, users, entity.getId(), true, createTask, null, userList, ConfigDocProcessEnum.WSP, MailDef.instance(MailEnum.WSPRequiresSignOff, MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()));
			}
			WspCompanyHistoryCreateService.instance().clearAndPopulateWspCompanyHistory(entity.getCompany(), entity.getClass().getName(), entity.getId());
		} else {
			TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.WSP, entity.getId());
			entity.setWspStatusEnum(WspStatusEnum.Pending);
			String message = "Grant Application for " + entity.getCompany().getCompanyName() + " has been submitted and with a sign off upload please review.";
			TasksService.instance().findFirstInProcessAndCreateTask(message, users, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.WSP, MailDef.instance(MailEnum.WSPSubmittedAndSignedReview, MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()), null);
			WspCompanyHistoryCreateService.instance().clearAndPopulateWspCompanyHistory(entity.getCompany(), entity.getClass().getName(), entity.getId());
		}
		if (entity.getSubmissionDate() == null) {
			entity.setSubmissionDate(getSynchronizedDate());
		}
		this.dao.updateBatch(dataEntities);
	}
	
	public void submitWSPWithSignOff(Wsp entity, Users users, boolean createTask) throws Exception {
		
		validateWSP(entity, users, true, false, true, false, true, true, true);
		List<IDataEntity> dataEntities = new ArrayList<>();
		dataEntities.add(entity);
		
		if (entity.getRequireSignOffUpload() == null || !entity.getRequireSignOffUpload()) {
			
			List<Users> userList = new ArrayList<>();
			entity.setWspStatusEnum(WspStatusEnum.PendingSignOff);
			entity.setWspSignoffs(wspSignoffService.findByWsp(entity));
			for (WspSignoff wspSignoff : entity.getWspSignoffs()) {
				if (users.getId().equals(wspSignoff.getUser().getId())) {
					wspSignoff.setAccept(true);
					wspSignoff.setSignOffDate(getSynchronizedDate());
					dataEntities.add(wspSignoff);
				} else {
					if (wspSignoff.getAccept() == null || !wspSignoff.getAccept()) {
						userList.add(wspSignoff.getUser());
					}
				}
				dataEntities.add(wspSignoff);
			}
			TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.WSP, entity.getId());
			if (userList.size() != 0) {
				// generate tasks for other users to sign off
				if (createTask) {
					String message = "Grant Application for " + entity.getCompany().getCompanyName() + " has been submitted and requires your sign off.";
					TasksService.instance().createTaskEachUser(entity.getClass().getName(), entity.getCompany(), message, "WSP form requires sign off", message, users, entity.getId(), true, createTask, null, userList, ConfigDocProcessEnum.WSP, MailDef.instance(MailEnum.WSPRequiresSignOff, MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()));
				}
			} else {
				// send work flow to MerSETA, all sign offs provided
				entity.setWspStatusEnum(WspStatusEnum.Pending);
				String message = "Grant Application for " + entity.getCompany().getCompanyName() + " has been submitted and signed off please review.";
				TasksService.instance().findFirstInProcessAndCreateTask(message, users, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.WSP, MailDef.instance(MailEnum.WSPRequiresSignOff, MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()), null);
				WspCompanyHistoryCreateService.instance().clearAndPopulateWspCompanyHistory(entity.getCompany(), entity.getClass().getName(), entity.getId());
			}	
			
		} else {
			TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.WSP, entity.getId());
			entity.setWspStatusEnum(WspStatusEnum.Pending);
			String message = "Grant Application for " + entity.getCompany().getCompanyName() + " has been submitted and with a sign off upload please review.";
			TasksService.instance().findFirstInProcessAndCreateTask(message, users, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.WSP, MailDef.instance(MailEnum.WSPSubmittedAndSignedReview, MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()), null);
			WspCompanyHistoryCreateService.instance().clearAndPopulateWspCompanyHistory(entity.getCompany(), entity.getClass().getName(), entity.getId());
		}
		if (entity.getSubmissionDate() == null) {
			entity.setSubmissionDate(getSynchronizedDate());
		}
		this.dao.updateBatch(dataEntities);
	}
	
	/**
	 * Submit Manual WSP.
	 * 
	 * JIRA #530
	 */
	
	public void submitWSPWithSignOffManually(Wsp entity, Users users, boolean createTask) throws Exception {
		
		validateWSP(entity, users, true, false, true, false, true, true, true);
		List<IDataEntity> dataEntities = new ArrayList<>();
		dataEntities.add(entity);
		List<Users> userList = new ArrayList<>();
		for (WspSignoff wspSignoff : entity.getWspSignoffs()) {
			wspSignoff.setAccept(wspSignoff.getAccept());
			wspSignoff.setSignOffDate(wspSignoff.getSignOffDate());
			dataEntities.add(wspSignoff);
		}
		TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.WSP, entity.getId());
		entity.setWspStatusEnum(WspStatusEnum.Pending);
		String message = "Grant Application for " + entity.getCompany().getCompanyName() + " has been submitted and with a sign off upload please review.";
		TasksService.instance().findFirstInProcessAndCreateTask(message, users, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.WSP, MailDef.instance(MailEnum.WSPSubmittedAndSignedReview, MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()), null);
		WspCompanyHistoryCreateService.instance().clearAndPopulateWspCompanyHistory(entity.getCompany(), entity.getClass().getName(), entity.getId());
		if (entity.getSubmissionDate() == null) {
			entity.setSubmissionDate(getSynchronizedDate());
		}
		this.dao.updateBatch(dataEntities);
	}
	
	/**
	 * Runs validation check on WSP
	 * If passes will update entry
	 * @param wsp
	 * @param users
	 * @throws Exception
	 */
	public void runValidiationCheckOnWsp(Wsp wsp, Users users) throws Exception {
		if (wsp.getWspType() == WspTypeEnum.IntersetaTransferMandGrant) {
			validateWSP(wsp, users, true, false, false, false, false, false, false);
		}else {
			validateWSP(wsp, users, true, false, true, false, true, true, true);
		}
		
		update(wsp);
	}

	public void saveWSP(Wsp entity, Users users, boolean createTask) throws Exception {

		// validateWSP(entity, users, true, false, true, false, true, true);

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
		WspCompanyHistoryCreateService.instance().clearAndPopulateWspCompanyHistory(entity.getCompany(), entity.getClass().getName(), entity.getId());
	}
	
//	public void determainIfUserSignedOff(Wsp entity, Users users) throws Exception{
//		boolean signedOff = true;
//		
//		
//		if (signedOff) {
//			
//		}
//	}

	/**
	 * Sign off.
	 *
	 * @param entity
	 *            the entity
	 * @param users
	 *            the users
	 * @throws Exception
	 *             the exception
	 */
	public void signOff(Wsp entity, Users users, Tasks tasks) throws Exception {
		// LOOK HERE
		validateWSP(entity, users, false, false, false, true, false, false, false);
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
			// Not required
//			WspChecklistService wcs = new WspChecklistService();
//			WspChecklist wspChecklist = wcs.findByWSP(entity);
//			if (wspChecklist != null) {
//				wcs.calculateDeviation(wspChecklist, entity);
//				dao.update(wspChecklist);
//			}
//			wspChecklist = null;
//			wcs = null;
			String message = "Grant Application for " + entity.getCompany().getCompanyName() + " has been submitted and signed off please review.";
			TasksService.instance().findFirstInProcessAndCreateTask(message, users, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.WSP, MailDef.instance(MailEnum.WSPRequiresSignOff, MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()), null);
		}
		dao.updateBatch(dataEntities);
	}
	
	/**
	 * Sign off Manually.
	 *
	 * @param entity
	 *            the entity
	 * @param users
	 *            the users
	 * @throws Exception
	 *             the exception
	 */
	public void signOffManually(Wsp entity, Users users, Tasks tasks) throws Exception {
		// LOOK HERE
		validateWSP(entity, users, false, false, false, true, false, false, false);
		List<IDataEntity> dataEntities = new ArrayList<>();
		entity.setManuallyAdded(true);
		entity.setManuallySubmissionDate(entity.getCreateDate());
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
			// Not required
//			WspChecklistService wcs = new WspChecklistService();
//			WspChecklist wspChecklist = wcs.findByWSP(entity);
//			if (wspChecklist != null) {
//				wcs.calculateDeviation(wspChecklist, entity);
//				dao.update(wspChecklist);
//			}
//			wspChecklist = null;
//			wcs = null;
			String message = "Grant Application for " + entity.getCompany().getCompanyName() + " has been submitted and signed off please review.";
			TasksService.instance().findFirstInProcessAndCreateTask(message, users, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.WSP, MailDef.instance(MailEnum.WSPRequiresSignOff, MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()), null);
		}
		dao.updateBatch(dataEntities);
	}
	

	public void saveSignOff(Wsp entity, Users users, Tasks tasks) throws Exception {
		validateWSP(entity, users, false, false, false, true, false, false, false);
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
		validateWSP(entity, users, false, false, false, true, false, false, false);
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
			WspCompanyHistoryCreateService.instance().clearAndPopulateWspCompanyHistory(entity.getCompany(), entity.getClass().getName(), entity.getId());
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
		params.put("call_center_number", HAJConstants.MERSETA_CALL_CENTRE);
		params.put("company_id", company.getId());

		byte[] bites = JasperService.instance().convertJasperReportToByte("Aknowledgement_Of_WSP_Submission.jasper", params);

		String subject = "Acknowledgement Of Mandatory Grant Submission: " + company.getCompanyName() + " (" + company.getLevyNumber() + ")";
		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Thank you for using the merSETA online facility to submit the mandatory grant application for " + company.getCompanyName() + " (" + company.getLevyNumber() + ").</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">We acknowledge receipt of the application and include an acknowledgement of submission notification for your records.</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">We will be in contact with you should there be any outstanding information required. Please further note that the payment" + " of mandatory grants can only be made once the levies for the company have been received by the merSETA and if the application is approved.</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Should you have any queries please do not hesitate to contact the merSETA Call Centre on: " + HAJConstants.MERSETA_CALL_CENTRE + " for assistance. </p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">merSETA Administration</p>";

		String fileName = company.getCompanyNameDisplay() + "-Aknowledgement_Of_Grant_Submission.pdf";

		GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, fileName, "pdf", null);

	}

	public void sendAcknowledgementOfSubmissionEmail(Users user, Company company) throws Exception {
		String subject = "Acknowledgement Of Discretionary Grant Submission: " + company.getCompanyNameDisplay();
		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + ",</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Thank you for using the merSETA online facility to submit a discretionary grant application for" + company.getCompanyNameDisplay() + ".</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">We acknowledge receipt of the application.</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">We will be in contact with you should there be any outstanding information required.</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Should you have any queries please do not hesitate to contact the merSETA Call Centre on:" + HAJConstants.MERSETA_CALL_CENTRE + " for assistance. </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">merSETA Administration</p>";
		GenericUtility.sendMail(user.getEmail(), subject, mssg, null);
	}

	/**
	 * Save status update.
	 *
	 * @param entity
	 *            the entity
	 * @param users
	 *            the users
	 * @param tasks
	 *            the tasks
	 * @param wspStatusEnum
	 *            the wsp status enum
	 * @throws Exception
	 *             the exception
	 */
	public void saveStatusUpdate(Wsp entity, Users users, Tasks tasks, WspStatusEnum wspStatusEnum) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		entity.setWspStatusEnum(wspStatusEnum);
		dataEntities.add(entity);
		this.dao.updateBatch(dataEntities);
		TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.WSP, entity.getId());
//		String message = "Grant Application for " + entity.getCompany().getCompanyName() + " is ready for review.";
		TasksService.instance().findNextInProcessAndCreateTask(null, users, entity.getId(), entity.getClass().getName(), true, tasks, MailDef.instance(MailEnum.WSPRequiresReview, MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()), null);
	}
	
	/**
	 * The Final Approval/ Rejection of the grant application
	 * @param entity
	 * @param users
	 * @param tasks
	 * @param wspStatusEnum
	 * @throws Exception
	 */
	public void finalApproveRejectGrantApplication(Wsp entity, Users users, Tasks tasks, WspStatusEnum wspStatusEnum) throws Exception{
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
		
		WspCompanyHistoryCreateService.instance().clearAndPopulateWspCompanyHistory(entity.getCompany(), entity.getClass().getName(), entity.getId());
	}
	
	/**
	 * Sends the task back to the primary SDF to either accept or appeal the grant rejection
	 * @param entity
	 * @param users
	 * @param tasks
	 * @param selectedRejectReasons
	 * @throws Exception
	 */
	public void rejectToSdfToAppeal(Wsp entity, Users users, Tasks tasks, List<RejectReasons> selectedRejectReasons, String additionalInformation) throws Exception{
		
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
			setRejectedReasons(entity.getClass().getName(), entity.getId(), users, selectedRejectReasons, ConfigDocProcessEnum.WSP, additionalInformation);
		}
		// add email notification 
	}
	
	/**
	 * Sends the task back to the primary SDF to either accept or appeal the grant rejection
	 * @param entity
	 * @param users
	 * @param tasks
	 * @param selectedRejectReasons
	 * @throws Exception
	 */
	public void rejectToSdfToAppealVersionTwo(Wsp entity, Users users, Roles role, Tasks tasks, List<RejectReasons> selectedRejectReasons, String additionalInformation) throws Exception{
		
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
		
		if (role != null && role.getId() != null) {
			WspRejectionInformationService.instance().updateCreateLastestEntries(entity, selectedRejectReasons, users, tasks, role);
		} else {
			WspRejectionInformationService.instance().updateCreateLastestEntries(entity, selectedRejectReasons, users, tasks, null);
		}

		
		TasksService.instance().findNextInProcessAndCreateTask(null, users, entity.getId(), entity.getClass().getName(), true, tasks, MailDef.instance(MailEnum.WSPRequiresReview, MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()), taskUsers);
		if (selectedRejectReasons != null && selectedRejectReasons.size() != 0) {
			setRejectedReasons(entity.getClass().getName(), entity.getId(), users, selectedRejectReasons, ConfigDocProcessEnum.WSP, additionalInformation);
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
//		entity.setWspStatusEnum(WspStatusEnum.MancoApproval);
		
		dataEntities.add(entity);
		if (dataEntities.size() != 0) {
			this.dao.updateBatch(dataEntities);
		}
		TasksService.instance().completeTask(tasks);
	}
	
	@SuppressWarnings("unchecked")
	public List<Wsp> allWspAwaitingMancoApproval(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from Wsp o " + 
				"where o.wspStatusEnum = :wspStatus " + 
				"and o.mancoApprovalRequired = :mancoApprovalRequired " + 
				"and o.mancoDecisionReached = :mancoDecisionReached " + 
				"and o.finYear is not null";
		filters.put("wspStatus", WspStatusEnum.Pending);
		filters.put("mancoApprovalRequired", true);
		filters.put("mancoDecisionReached", false);
		int counter = 1;
		for (WspTypeEnum type : WspTypeEnum.getStandardFundingTypes()) {
			if (counter == 1) {
				hql += " and o.wspType in ( ";
			}
			hql += ":type" + counter;
			filters.put("type" + counter, type);
			if (counter == WspTypeEnum.getStandardFundingTypes().size()) {
				hql += " ) ";
			} else {
				hql += " , ";
			}
			counter++;
		}
		return resolveExtension((List<Wsp>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllWspAwaitingMancoApproval(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Wsp o " + 
				"where o.wspStatusEnum = :wspStatus " + 
				"and o.mancoApprovalRequired = :mancoApprovalRequired " + 
				"and o.mancoDecisionReached = :mancoDecisionReached " + 
				"and o.finYear is not null";
		int counter = 1;
		for (WspTypeEnum type : WspTypeEnum.getStandardFundingTypes()) {
			if (counter == 1) {
				hql += " and o.wspType in ( ";
			}
			hql += ":type" + counter;
			if (counter == WspTypeEnum.getStandardFundingTypes().size()) {
				hql += " ) ";
			} else {
				hql += " , ";
			}
			counter++;
		}
		return dao.countWhere(filters, hql);
	}
	
	/**
	 * Final Rejection of grant application
	 * @param entity
	 * @param users
	 * @param tasks
	 * @throws Exception
	 */
	public void finalRejectionGrant(Wsp entity, Users users, Tasks tasks) throws Exception{
		List<IDataEntity> dataEntities = new ArrayList<>();
		entity.setWithSdf(false);
		entity.setWspStatusEnum(WspStatusEnum.Rejected);
		entity.setApprovedDate(getSynchronizedDate());
		dataEntities.add(entity);
		if (dataEntities.size() != 0) {
			this.dao.updateBatch(dataEntities);
		}
		TasksService.instance().completeTask(tasks);
		WspCompanyHistoryCreateService.instance().clearAndPopulateWspCompanyHistory(entity.getCompany(), entity.getClass().getName(), entity.getId());
	}
	
	/**
	 * SDF appealed grant application
	 * @param entity
	 * @param users
	 * @param tasks
	 * @throws Exception
	 */
	public void appealGrantApplication(Wsp entity, Users users, Tasks tasks) throws Exception{
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
	 *
	 * @param entity
	 *            the entity
	 * @param users
	 *            the users
	 * @throws Exception
	 *             the exception
	 */
	public void sendBackToSDF(Wsp entity, Users users) throws Exception {
		validateWSP(entity, users, false, false, false, true, false, false, false);
		List<IDataEntity> dataEntities = new ArrayList<>();
		dataEntities.add(entity);
		entity.setWspStatusEnum(WspStatusEnum.Draft);
		for (WspSignoff wspSignoff : entity.getWspSignoffs()) {
			wspSignoff.setAccept(false);
			dataEntities.add(wspSignoff);
		}
		DgVerificationService.instance().clearDgVerification(entity);
		TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.WSP, entity.getId());
		String message = "Grant Application for " + entity.getCompany().getCompanyName() + " has been rejected please review before sign off and submission.";
		TasksService.instance().createTask(entity.getCompany(), "Grant Application form needs to be reviewed", message, users, entity.getId(), true, true, null, entity.getCreateUsers(), ConfigDocProcessEnum.WSP, MailDef.instance(MailEnum.WSPRejectedReview, MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()));
		this.dao.updateBatch(dataEntities);
		ExtensionRequestService.instance().locateAndUpdateExtnesionRequest(entity);
		
	}
	
	/**
	 * Send back to SDF for re-upload of documents.
	 *
	 * @param entity
	 *            the entity
	 * @param users
	 *            the users
	 * @throws Exception
	 *             the exception
	 */
	public void sendBackToSDFDocumentRejection(Wsp entity, Users users, List<RejectReasons> rejectReasonsSelected, String additionalInformation) throws Exception {
		validateWSP(entity, users, false, false, false, true, false, false, false);
		SDFCompany company = sdfCompanyService.locateFirstPrimarySDF(entity.getCompany());
		List<Users> sdf = new ArrayList<>();
		if (company != null) sdf.add(company.getSdf());
		TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.WSP, entity.getId());
		TasksService.instance().findFirstInProcessAndCreateTask(null, users, entity.getId(), Wsp.class.getName(), true, ConfigDocProcessEnum.WSPDocumentUpload, null, sdf);
		setRejectedReasons(entity.getClass().getName(), entity.getId(), users, rejectReasonsSelected, ConfigDocProcessEnum.WSPDocumentUpload, additionalInformation);
	}

	public void setRejectedReasons(String className,Long targetkey, Users users, List<RejectReasons> rejectReasonsSelected, ConfigDocProcessEnum configProcess, String additionalInformation) throws Exception {
		List<IDataEntity> entityList = new ArrayList<IDataEntity>();
		if (rejectReasonsSelected != null && rejectReasonsSelected.size() != 0) {
			entityList.addAll(RejectReasonMultipleSelectionService.instance().removeCreateLinks(targetkey, className, rejectReasonsSelected, users, configProcess, additionalInformation));
		} else {
			RejectReasonMultipleSelectionService.instance().clearEntries(targetkey, className, configProcess);
		}
		if (entityList.size() != 0) {
			dao.createBatch(entityList);
		}
	}

	/**
	 * Dispute WSP.
	 *
	 * @param entity
	 *            the entity
	 * @param users
	 *            the users
	 * @param tasks
	 *            the tasks
	 * @param disputeReason
	 *            the dispute reason
	 * @throws Exception
	 *             the exception
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
					RegionTown rt = RegionTownService.instance().findByTown(entity.getCompany().getResidentialAddress().getTown());
					List<Users> signoffs = new ArrayList<>();
					if (rt != null) {
						dao.create(new Signoff(wspDispute, rt.getClo().getUsers(), "merSETA Region CLO"));
						dao.create(new Signoff(wspDispute, rt.getCrm().getUsers(), "merSETA Region CRM"));
						signoffs.add(rt.getClo().getUsers());
						// THIS IS THE ONE
						TasksService.instance().createTaskUser(signoffs, entity.getClass().getName(), entity.getId(), "There is a new dispute on the grant application for company " + entity.getCompany().getCompanyName() + " (" + entity.getCompany().getLevyNumber() + ")", users, true, true, null, ConfigDocProcessEnum.WSP, false);
					}
				}
			} else if ((wspSignoff.getAccept() == null || !wspSignoff.getAccept()) && (wspSignoff.getDispute() == null || !wspSignoff.getDispute())) {
				changePending = false;
			}
		}
		TasksService.instance().completeTask(tasks);
//		if (changePending) {
//			// TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.WSP, entity.getId());
//			entity.setWspStatusEnum(WspStatusEnum.Pending);
//			String message = "Grant Application for " + entity.getCompany().getCompanyName() + " has been submitted and signed off please review.";
//			TasksService.instance().findFirstInProcessAndCreateTask(message, users, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.WSP, MailDef.instance(MailEnum.WSPSubmittedAndSignedReview, MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()), null);
//		}
		this.dao.updateBatch(dataEntities);
	}

	public void createDisputeSignOff(WspDispute wspDispute, TempSignoff tempSignoff, String title) throws Exception {
		dao.create(tempSignoff);
		dao.create(new Signoff(wspDispute, tempSignoff, title));
	}

	/* END WSP WORKFLOW METHOD */

	/**
	 * Validate WSP.
	 *
	 * @param wsp
	 *            the wsp
	 * @param users
	 *            the users
	 * @param checkDocs
	 *            the check docs
	 * @param checkImpactOfStaffTraining
	 *            the check impact of staff training
	 * @param checkSkillsRequirments
	 *            the check skills requirments
	 * @param checkSignOff
	 *            the check sign off
	 * @param checkWSPData
	 *            the check employed
	 * @param checkATRData
	 *            the check unemployed
	 * @throws Exception
	 *             the exception
	 */
	private void validateWSP(Wsp wsp, Users users, boolean checkDocs, boolean checkImpactOfStaffTraining, boolean checkSkillsRequirments, boolean checkSignOff, boolean checkWSPData, boolean checkATRData, boolean checkGrantInfo) throws Exception {
		StringBuilder error = new StringBuilder("");
		List<String> exceptions = new ArrayList<>();
		if (checkDocs) {
			for (Doc doc : wsp.getDocs()) {
				
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) {
						doc.setData(docByte.getData());
					}
				}
				
				if ((doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) && (doc.getData() == null || doc.getData().length == 0)) {
					exceptions.add("DONE-" + getEntryLanguage("document.outstanding", doc.getConfigDoc().getName()));
				} else if (doc.isRequired() && (doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
					exceptions.add("DONE-" + getEntryLanguage("document.outstanding", doc.getConfigDoc().getName()));
				}
				
				// Validation check for training deviation upload
				if (wsp.getWspType() != WspTypeEnum.IntersetaTransferMandGrant) {
					if (doc.getConfigDoc().getWspDocRequirements() == WspDocRequirements.Deviation) {
						if (wsp.getDeviatedWorkplaceSkillsPlan() != null && wsp.getDeviatedWorkplaceSkillsPlan().getId() == HAJConstants.YES_ID) {
							if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
								doc.getConfigDoc().setRequiredDocument(true);
								exceptions.add("DONE-" + getEntryLanguage("document.outstanding", doc.getConfigDoc().getName()));
							}
						} else {
							if (WspCalculationDataService.instance().calculateDeviationByWsp(wsp)) {
								if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
									doc.getConfigDoc().setRequiredDocument(true);
									exceptions.add("DONE-" + getEntryLanguage("document.outstanding", doc.getConfigDoc().getName()));
								}
							}
						}
					}
				}
			}
		}

		if (checkSkillsRequirments && (wsp.getCompany().getNonLevyPaying() == null || !wsp.getCompany().getNonLevyPaying())) {
			if (wspSkillsRequirementsService.findByWspCount(wsp) == 0) {
				exceptions.add("skills.requirement.outstanding");
			}
		}
		
		if (checkImpactOfStaffTraining) {
			List<WspImpactOfStaffTraining> it = impactOfStaffTrainingService2.findByWsp(wsp.getId());
			if (it == null || it.size() > 0) {
				exceptions.add("impact.of.staff.training.outstanding");
			}
		}
		if (checkSignOff) {
			for (WspSignoff wspSignoff : wsp.getWspSignoffs()) {
				if (wspSignoff.getUser().equals(users) && (wspSignoff.getAccept() == null || !wspSignoff.getAccept())) {
					exceptions.add("accept.ts.cs");
				}
			}
		}
		if (checkWSPData) {
			if (mandatoryGrantDetailService.findByWSPCount(wsp, WspReportEnum.WSP) == 0) {
				exceptions.add("no.wsp.captured");
			}
		}
		if (checkATRData && wsp.getWspType() != WspTypeEnum.Discretionary) {
			if (mandatoryGrantDetailService.findByWSPCount(wsp, WspReportEnum.ATR) == 0 && (wsp.getCompletedTraining() == null || wsp.getCompletedTraining().getId() == HAJConstants.YES_ID)) {
				exceptions.add("no.atr.captured");
			}
			if (wsp.getCompletedTraining() != null && wsp.getCompletedTraining().getId() == HAJConstants.YES_ID) {
				if (wsp.getTotalPayroll() == null) {
					// #{initiatewspUI.wsp.totalPayroll}
					exceptions.add("no.atr.payroll.captured");
				}
			}	
		}
		
		// validation checks on the information that is entered on the screen
		if (checkGrantInfo) {
//			if (wsp.getWspType() != WspTypeEnum.Discretionary) {}
			boolean nonMersetaNonLevyPaying = (wsp.getCompany().getNonLevyPaying() != null && wsp.getCompany().getNonLevyPaying()
					&& wsp.getCompany().getSicCode() != null && "Unknown".equals(wsp.getCompany().getSicCode().getChamber().getCode()));
			if (!nonMersetaNonLevyPaying) {
				/*
				 *  Skills Gap Validation Start
				 *  /MerSETA/src/main/webapp/pages/externalparty/wsp/skillsgap.xhtml
				 */
				// Do you keep track of skills gaps in your organization?
				if (wsp.getTrackSkillsGap() == null) {
					exceptions.add("no.skills.gap.indicated");
				} else if (wsp.getTrackSkillsGap().getId() == HAJConstants.YES_ID) {
					// How do you keep track of skills gap?
					if (wsp.getSkillsGapTrackSelection() == null) {
						exceptions.add("no.skills.gap.record.kept");
					}
				}
				/*
				 *  Skills Gap Validation End
				 */
			}
			// #{not initiatewspUI.wsp.company.nonLevyPaying}
			if (!wsp.getCompany().getNonLevyPaying()) {
				/*
				 *  ATR Validation Start
				 *  /MerSETA/src/main/webapp/pages/externalparty/wsp/atr.xhtml
				 */
				// Have you completed any training in the current reporting period?
				if (wsp.getCompletedTraining() == null) {
					exceptions.add("atr.no.completed.training.provided"); 
				}
				// Have you deviated from the Workplace Skills Plan?
				if (wsp.getDeviatedWorkplaceSkillsPlan() == null) {
					exceptions.add("atr.training.deviation.no.provided"); 
				}
				/*
				 *  ATR Validation End
				 *  /MerSETA/src/main/webapp/pages/externalparty/wsp/atr.xhtml
				 */
				
				/*
				 *  PIVITOL Training Report Validation Start
				 *  /MerSETA/src/main/webapp/pages/externalparty/wsp/pivitoltraining.xhtml
				 *  NEED MORE INFORMATION
				 */
				/*
				 *  PIVITOL Training Report Validation End
				 *  /MerSETA/src/main/webapp/pages/externalparty/wsp/pivitoltraining.xhtml
				 */
			}
			
			/*
			 *  WSP FULL
			 *  /MerSETA/src/main/webapp/pages/externalparty/wsp/wsp.xhtml
			 */
			// Indicate whether your planned training is in line with your Employment Equity Plan?
			if (wsp.getEmploymentEquityPlanInline() == null) {
				exceptions.add("wsp.no.planned.training.provided"); 
			}
			// If applying for a Discretionary Grant and the application exceeds the 49.5% value of the levy contribution, would your organisation consider a partial funding partnership with merSETA? Please Refer to the merSETA Grant Policy if unclear.
			if (wsp.getWspType() != WspTypeEnum.Mandatory) {
				if (wsp.getCoFundingPartnership() == null) {
					exceptions.add("wsp.no.co.funding.indicated");
				}
			}
			/*
			 *  WSP FULL
			 *  /MerSETA/src/main/webapp/pages/externalparty/wsp/wsp.xhtml
			 */
		}

		if (exceptions.size() > 0) {
			for (String string : exceptions) {
				if (string.indexOf("DONE-") == -1) {
					error.append("<li>" + getEntryLanguage(string) + "</li>");
				} else {
					error.append("<li>" + string.replaceAll("DONE-", "") + "</li>");
				}
			}
			throw new ValidationErrorException("<ul>" + error.toString() + "</ul>");
		}
	}
	
	private void validateWSPAdmin(Wsp wsp, Users users, boolean checkDocs, boolean checkImpactOfStaffTraining, boolean checkSkillsRequirments, boolean checkSignOff, boolean checkWSPData, boolean checkATRData, boolean checkGrantInfo) throws Exception {
		StringBuilder error = new StringBuilder("");
		List<String> exceptions = new ArrayList<>();
		if (checkDocs) {
			for (Doc doc : wsp.getDocs()) {
				
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) {
						doc.setData(docByte.getData());
					}
				}
				
				if ((doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) && (doc.getData() == null || doc.getData().length == 0)) {
					exceptions.add("DONE- document.outstanding " +  doc.getConfigDoc().getName());
				} else if (doc.isRequired() && (doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
					exceptions.add("DONE- document.outstanding " +  doc.getConfigDoc().getName());
				}
				
				// Validation check for training deviation upload
				if (doc.getConfigDoc().getWspDocRequirements() == WspDocRequirements.Deviation) {
					if (wsp.getDeviatedWorkplaceSkillsPlan() != null && wsp.getDeviatedWorkplaceSkillsPlan().getId() == HAJConstants.YES_ID) {
						if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
							doc.getConfigDoc().setRequiredDocument(true);
							exceptions.add("DONE- document.outstanding " +  doc.getConfigDoc().getName());
						}
					} else {
						if (WspCalculationDataService.instance().calculateDeviationByWsp(wsp)) {
							if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
								doc.getConfigDoc().setRequiredDocument(true);
								exceptions.add("DONE- document.outstanding " +  doc.getConfigDoc().getName());
							}
						}
					}
				}
			}
		}
		
		if (checkSkillsRequirments && (wsp.getCompany().getNonLevyPaying() == null || !wsp.getCompany().getNonLevyPaying())) {
			if (wspSkillsRequirementsService.findByWspCount(wsp) == 0) {
				exceptions.add("skills.requirement.outstanding");
			}
		}
		
		if (checkImpactOfStaffTraining) {
			List<WspImpactOfStaffTraining> it = impactOfStaffTrainingService2.findByWsp(wsp.getId());
			if (it == null || it.size() > 0) {
				exceptions.add("impact.of.staff.training.outstanding");
			}
		}
		if (checkSignOff) {
			for (WspSignoff wspSignoff : wsp.getWspSignoffs()) {
				if (wspSignoff.getUser().equals(users) && (wspSignoff.getAccept() == null || !wspSignoff.getAccept())) {
					exceptions.add("accept.ts.cs");
				}
			}
		}
		if (checkWSPData) {
			if (mandatoryGrantDetailService.findByWSPCount(wsp, WspReportEnum.WSP) == 0) {
				exceptions.add("no.wsp.captured");
			}
		}
		if (checkATRData && wsp.getWspType() != WspTypeEnum.Discretionary) {
			if (mandatoryGrantDetailService.findByWSPCount(wsp, WspReportEnum.ATR) == 0 && (wsp.getCompletedTraining() == null || wsp.getCompletedTraining().getId() == HAJConstants.YES_ID)) {
				exceptions.add("no.atr.captured");
			}
			if (wsp.getCompletedTraining() != null && wsp.getCompletedTraining().getId() == HAJConstants.YES_ID) {
				if (wsp.getTotalPayroll() == null) {
					// #{initiatewspUI.wsp.totalPayroll}
					exceptions.add("no.atr.payroll.captured");
				}
			}	
		}
		
		// validation checks on the information that is entered on the screen
		if (checkGrantInfo) {
//					if (wsp.getWspType() != WspTypeEnum.Discretionary) {}
			boolean nonMersetaNonLevyPaying = (wsp.getCompany().getNonLevyPaying() != null && wsp.getCompany().getNonLevyPaying()
					&& wsp.getCompany().getSicCode() != null && "Unknown".equals(wsp.getCompany().getSicCode().getChamber().getCode()));
			if (!nonMersetaNonLevyPaying) {
				/*
				 *  Skills Gap Validation Start
				 *  /MerSETA/src/main/webapp/pages/externalparty/wsp/skillsgap.xhtml
				 */
				// Do you keep track of skills gaps in your organization?
				if (wsp.getTrackSkillsGap() == null) {
					exceptions.add("no.skills.gap.indicated");
				} else if (wsp.getTrackSkillsGap().getId() == HAJConstants.YES_ID) {
					// How do you keep track of skills gap?
					if (wsp.getSkillsGapTrackSelection() == null) {
						exceptions.add("no.skills.gap.record.kept");
					}
				}
				/*
				 *  Skills Gap Validation End
				 */
			}
			// #{not initiatewspUI.wsp.company.nonLevyPaying}
			if (!wsp.getCompany().getNonLevyPaying()) {
				/*
				 *  ATR Validation Start
				 *  /MerSETA/src/main/webapp/pages/externalparty/wsp/atr.xhtml
				 */
				// Have you completed any training in the current reporting period?
				if (wsp.getCompletedTraining() == null) {
					exceptions.add("atr.no.completed.training.provided"); 
				}
				// Have you deviated from the Workplace Skills Plan?
				if (wsp.getDeviatedWorkplaceSkillsPlan() == null) {
					exceptions.add("atr.training.deviation.no.provided"); 
				}
				/*
				 *  ATR Validation End
				 *  /MerSETA/src/main/webapp/pages/externalparty/wsp/atr.xhtml
				 */
				
				/*
				 *  PIVITOL Training Report Validation Start
				 *  /MerSETA/src/main/webapp/pages/externalparty/wsp/pivitoltraining.xhtml
				 *  NEED MORE INFORMATION
				 */
				/*
				 *  PIVITOL Training Report Validation End
				 *  /MerSETA/src/main/webapp/pages/externalparty/wsp/pivitoltraining.xhtml
				 */
			}
			
			/*
			 *  WSP FULL
			 *  /MerSETA/src/main/webapp/pages/externalparty/wsp/wsp.xhtml
			 */
			// Indicate whether your planned training is in line with your Employment Equity Plan?
			if (wsp.getEmploymentEquityPlanInline() == null) {
				exceptions.add("wsp.no.planned.training.provided"); 
			}
			// If applying for a Discretionary Grant and the application exceeds the 49.5% value of the levy contribution, would your organisation consider a partial funding partnership with merSETA? Please Refer to the merSETA Grant Policy if unclear.
			if (wsp.getWspType() != WspTypeEnum.Mandatory) {
				if (wsp.getCoFundingPartnership() == null) {
					exceptions.add("wsp.no.co.funding.indicated");
				}
			}
			/*
			 *  WSP FULL
			 *  /MerSETA/src/main/webapp/pages/externalparty/wsp/wsp.xhtml
			 */
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
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Wsp
	 */
	public void delete(Wsp entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.Wsp}
	 * @throws Exception
	 *             the exception
	 * @see Wsp
	 */
	public Wsp findByKey(long id) throws Exception {
		return populateDeviation(checkDocRequired(resolveDocs(dao.findByKey(id))));
	}

	/**
	 * Calculates if the grant has deviated form previous year training
	 * @see Wsp
	 * @see WspCalculationDataService
	 * @param wsp
	 * @return wsp
	 * @throws Exception
	 */
	public Wsp populateDeviation(Wsp wsp) throws Exception{
		if (wsp.getWspStatusEnum() != WspStatusEnum.Draft) {
			BigDecimal minReq = (BigDecimal.valueOf(60));
			wsp.setPercentageCalculatedForDeviation(WspCalculationDataService.instance().calculateAndReturnDeviationAmount(wsp));
			if (wsp.getPercentageCalculatedForDeviation() != null) {
				wsp.setPercentageCalculatedForDeviationDoubleValue(GenericUtility.roundToPrecision(wsp.getPercentageCalculatedForDeviation().doubleValue(), 2));
				if (wsp.getPercentageCalculatedForDeviation().doubleValue() < minReq.doubleValue()) {
					wsp.setGrantDeviated(true);
				}else {
					wsp.setGrantDeviated(false);
				}
			} else {
				// sets to null to display no data
				wsp.setGrantDeviated(null);
			}
		}
		return wsp;
	}
	
	public Wsp populateInformationForReport(Wsp wsp) throws Exception{
		populateDeviation(wsp);
		// populates rejection reasons across the
		if (wsp.getWspStatusEnum() == WspStatusEnum.Rejected || wsp.getWspStatusEnum() == WspStatusEnum.Approved) {			
			findLastActionUser(wsp);
			
			/**
			 * Tas Thing
			 * 
			 * Locate lastest task employee user 
			 * Users user = get form lastest task
			 * wsp.setEmployeeUsers(user.getFirstName() + " " + user.getLastName());
			 */
			
			if (wsp.getWspStatusEnum() == WspStatusEnum.Rejected) {
				
				if (wsp.getSystemApprovalRejection() != null && wsp.getSystemApprovalRejection()) {
					Integer countEntriesFound = ScheduleChangesLogService.instance().countBytargetClassandTargetKey(wsp.getClass().getName(), wsp.getId());
					if (countEntriesFound == 1) {
						ScheduleChangesLog log = ScheduleChangesLogService.instance().findBytargetClassandTargetKey(wsp.getClass().getName(), wsp.getId());
						if (log != null && log.getId() != null) {
							wsp.setSystemApprovalRejectionMessage(log.getInformation());
						}else {
							wsp.setSystemApprovalRejectionMessage("N/A");
						}
					} else if (countEntriesFound > 1) {
						ScheduleChangesLog log = ScheduleChangesLogService.instance().findBytargetClassandTargetKeyListReturnFirst(wsp.getClass().getName(), wsp.getId());
						if (log != null && log.getId() != null) {
							wsp.setSystemApprovalRejectionMessage(log.getInformation());
						}else {
							wsp.setSystemApprovalRejectionMessage("N/A");
						}
					} else {
						wsp.setSystemApprovalRejectionMessage("N/A");
					}
					
				} else {
					wsp.setSystemApprovalRejectionMessage("N/A");
				}
				
				// populate last employee task user
				RejectReasonsService rejectReasonsService = new RejectReasonsService();
				List<RejectReasons> rrList = rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(wsp.getId(), Wsp.class.getName(),ConfigDocProcessEnum.WSP);
				rejectReasonsService = null;
				String results = "";
				int count = 1;
				for (RejectReasons rejectReasons : rrList) {
					if (count == rrList.size()) {
						results+= rejectReasons.getDescription();
					}else {
						results+= rejectReasons.getDescription() + ", ";
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
	 *
	 * @param id
	 *            the id
	 * @param user
	 *            the user
	 * @return the wsp
	 * @throws Exception
	 *             the exception
	 */
	public Wsp findByKey(long id, Users user) throws Exception {
		return populateDeviation(checkDocRequired(resolveWspSignOff(resolveDocs(dao.findByKey(id)), user)));
	}

	/**
	 * Find Wsp by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.Wsp}
	 * @throws Exception
	 *             the exception
	 * @see Wsp
	 */
	public List<Wsp> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load Wsp.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.Wsp}
	 * @throws Exception
	 *             the exception
	 */
	public List<Wsp> allWsp(int first, int pageSize) throws Exception {
		return dao.allWsp(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of Wsp for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Wsp
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(Wsp.class);
	}

	/**
	 * Lazy load Wsp with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            Wsp class
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
	 * @return a list of {@link haj.com.entity.Wsp} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Wsp> allWsp(Class<Wsp> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<Wsp>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
	}

	/**
	 * Get count of Wsp for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            Wsp class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the Wsp entity
	 * @throws Exception
	 *             the exception
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
		String hql = "select count(o) from Wsp o where o.company.id = :companyId";
		int count = dao.countWhere(filters, hql);
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public List<Wsp> allWspByCompanyWithAllocation(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company company) throws Exception {
		String hql = "select o from Wsp o where o.company.id = :companyId and o.id in (select x.wsp.id from DgAllocationParent x)";
		filters.put("companyId", company.getId());
		return resolveExtension((List<Wsp>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllWspByCompanyWithAllocation(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Wsp o where o.company.id = :companyId and o.id in (select x.wsp.id from DgAllocationParent x)";
		int count = dao.countWhere(filters, hql);
		return count;
	}

	/**
	 * Find by company.
	 *
	 * @param company
	 *            the company
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<Wsp> findByCompany(Company company) throws Exception {
		return resolveExtension(dao.findByCompany(company.getId()));
	}
	
	private List<Wsp> resolveExtension(List<Wsp> wsps) throws Exception {
		for (Wsp wsp : wsps) {
			populateRejectReasons(wsp);
			// sets extension requests
			wsp.setExtensionRequests(ExtensionRequestService.instance().findByWSP(wsp));
			// sets DG Verification status on WSP
			wsp.setDgVerificationStatus(DgVerificationService.instance().findByWspAndReturnStatus(wsp.getId()));
			
			calcIfCanApplyForExtension(wsp);
			
			DgVerificationService dgVerificationService = new DgVerificationService();
			DgVerification dg = dgVerificationService.findByWspId(wsp);
			
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
	
	private void calcIfCanApplyForExtension(Wsp wsp)  throws Exception {
		
		boolean canApply = true;
		
		// if no status assigned or does not equal draft and peinding sign off
		if (wsp.getWspStatusEnum() == null || (wsp.getWspStatusEnum() != WspStatusEnum.Draft && wsp.getWspStatusEnum() != WspStatusEnum.PendingSignOff) ) {
			canApply = false;
		}
		
		// if other extension request found
		if (wsp.getExtensionRequests() == null || wsp.getExtensionRequests().size() != 0) {
			canApply = false;
		}
		
		if (canApply) {
			
			Date today = GenericUtility.getStartOfDay(getSynchronizedDate());
			SimpleDateFormat df = new SimpleDateFormat("dd MM yyyy");
			
			// passed the date for the year
			if (wsp.getFinYear() == null) {
				canApply = false;
			} else if (wsp.getFinYear() == 2021) {
				// can apply for extension up till 31 May for fin year 2020/2021
				Integer wspFinYearStart = 2021 - 1;
				if (today.after(GenericUtility.getStartOfDay(df.parse("31 05 " + wspFinYearStart)))) {
					canApply = false;
				}
			} else {
				// can apply for extension up till 30 April for all other fin years
				Integer wspFinYearStart = wsp.getFinYear() - 1;
				if (today.after(GenericUtility.getStartOfDay(df.parse("30 04 " + wspFinYearStart)))) {
					canApply = false;
				}
			}
		}
		
		wsp.setApplyForExtension(canApply);
	}

	public void populateRejectReasons(Wsp wsp)throws Exception{
		if (wsp.getWspStatusEnum() == WspStatusEnum.Rejected) {
			// populate last employee task user
			RejectReasonsService rejectReasonsService = new RejectReasonsService();
			List<RejectReasons> rrList = rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(wsp.getId(), Wsp.class.getName(),ConfigDocProcessEnum.WSP);
			rejectReasonsService = null;
			String results = "";
			int count = 1;
			for (RejectReasons rejectReasons : rrList) {
				if (count == rrList.size()) {
					results+= rejectReasons.getDescription();
				}else {
					results+= rejectReasons.getDescription() + ", ";
				}
				count++;
			}
			if (results != "") {
				wsp.setRejectionReasons(results);
			} else {
				wsp.setRejectionReasons("None Found");
			}
		}else {
			wsp.setRejectionReasons("N/A");
			wsp.setSystemApprovalRejectionMessage("N/A");
		}
	}
	
	public void populateDGVerificationRejectReasons(DgVerification dg)throws Exception{
		if (dg.getStatus() == ApprovalEnum.Rejected) {
			// populate last employee task user
			RejectReasonsService rejectReasonsService = new RejectReasonsService();
			List<RejectReasons> rrList = rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(dg.getId(), DgVerification.class.getName(),ConfigDocProcessEnum.DG_VERIFICATION);
			rejectReasonsService = null;
			String results = "";
			int count = 1;
			for (RejectReasons rejectReasons : rrList) {
				if (count == rrList.size()) {
					results+= rejectReasons.getDescription();
				}else {
					results+= rejectReasons.getDescription() + ", ";
				}
				count++;
			}
			if (results != "") {
				dg.setDgVerificationRejectionReasons(results);
			} else {
				dg.setDgVerificationRejectionReasons("None Found");
			}
		}else {
			dg.setDgVerificationRejectionReasons("N/A");
			dg.setDgVerificationRejectionReasons("N/A");
		}
	}

	/**
	 * Find by guid.
	 *
	 * @param guid
	 *            the guid
	 * @param user
	 *            the user
	 * @return the wsp
	 * @throws Exception
	 *             the exception
	 */
	public Wsp findByGuid(String guid, Users user) throws Exception {
		return populateDeviation(checkDocRequired(resolveWspSignOff(resolveDocs(dao.findByGuid(guid)), user)));
	}

	/**
	 * Check wsp fin year unique.
	 *
	 * @param wsp
	 *            the wsp
	 * @throws Exception
	 *             the exception
	 * @throws ValidationException
	 *             the validation exception
	 */
	public void checkWspFinYearUnique(Wsp wsp) throws Exception, ValidationException {
//		List<Wsp> l = new ArrayList<>();
//		l = dao.findByCompany(wsp.getCompany().getId());
//		for (Wsp wsp2 : l) {
//			if (wsp2.getFinYear() != null) if (wsp2.getFinYear().intValue() == wsp.getFinYear().intValue()) throw new ValidationException("wsp.fin.year.exists", wsp.getFinYear());
//		}
		int wspCount = dao.countByCompanyAndFinYear(wsp.getCompany().getId(), wsp.getFinYear());
		if (wspCount > 0) {
			throw new ValidationException("wsp.fin.year.exists", wsp.getFinYear());
		}
	}

	public Wsp checkDocRequired(Wsp wsp) throws Exception {
		for (Doc doc : wsp.getDocs()) {

			if (wsp.getWspType() != WspTypeEnum.IntersetaTransferMandGrant && wsp.getWspType() != WspTypeEnum.LegacyLinkedGrantApplication) {
				
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
				if ((wsp.getDeviatedWorkplaceSkillsPlan() != null &&  wsp.getDeviatedWorkplaceSkillsPlan().getId() == HAJConstants.YES_ID)) {
					if (doc.getConfigDoc().getWspDocRequirements() == WspDocRequirements.Deviation) {
						doc.setRequired(true);
					}
				}
				
				if (mandatoryGrantDetailService.findByWSPPivotalPlan(wsp, WspReportEnum.ATR, HAJConstants.NO_ID).size() > 0) {
					if (doc.getConfigDoc().getWspDocRequirements() == WspDocRequirements.NonNQFTrainingExplanation) {
						doc.setRequired(true);
					}
				}
				
				if (wsp.getManuallyAdded() != null) {
					if (wsp.getManuallyAdded()) {
						if (doc.getConfigDoc().getWspDocRequirements() == WspDocRequirements.ManualSubmissionDocument) {
							doc.setRequired(true);
						}
					}
				}
				
			} else {
				
				if (doc.getConfigDoc().getWspDocRequirements() == WspDocRequirements.InterSetaTransferDoc) {
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
	 *
	 * @param wsp
	 *            the wsp
	 * @param user
	 *            the user
	 * @return the wsp
	 */
	@SuppressWarnings("unchecked")
	private Wsp resolveWspSignOff(Wsp wsp, Users user) {
		try {
			wsp.setWspSignoffs(wspSignoffService.findByWsp(wsp));

			if (wsp.getWspStatusEnum() == WspStatusEnum.Draft) {
				List<IDataEntity> dataEntities = new ArrayList<>();

				dao.deleteBatch((List<IDataEntity>) (List<?>) wsp.getWspSignoffs());

				List<SDFCompany> sdfCompanies = sdfCompanyService.findByCompanySignOff(wsp.getCompany(), true);
				for (SDFCompany sdfCompany : sdfCompanies) {
					if (sdfCompany.getSdfType().getSignOnRecognition() == null) {
						WspSignoff signOff = new WspSignoff(wsp, sdfCompany.getSdf());
						signOff.setSdfType(sdfCompany.getSdfType());
						dataEntities.add(signOff);
					} else if (sdfCompany.getSdfType().getSignOnRecognition().equals(sdfCompany.getCompany().getRecognitionAgreement())) {
						if ((sdfCompany.getCompany().getRecognitionAgreement().getId() == YesNoEnum.No.getYesNoLookupId() && sdfCompany.getCompany().getNumberOfEmployees() > 49) || sdfCompany.getCompany().getRecognitionAgreement().getId() == YesNoEnum.Yes.getYesNoLookupId()) {
							WspSignoff signOff = new WspSignoff(wsp, sdfCompany.getSdf());
							signOff.setSdfType(sdfCompany.getSdfType());
							dataEntities.add(signOff);
						}
					}
				}

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
		String returnMessage = "No changes have been found for the grant sign offs";
		List<SDFCompany> sdfCompanies = sdfCompanyService.findByCompanySignOff(wsp.getCompany(), true);
		List<Users> userList = new ArrayList<>();
		List<WspSignoff> signoffs = wspSignoffService.findByWsp(wsp);

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
			TasksService.instance().createTaskEachUser(wsp.getClass().getName(), wsp.getCompany(), message, "WSP form requires sign off", message, users, wsp.getId(), true, true, null, userList, ConfigDocProcessEnum.WSP, MailDef.instance(MailEnum.WSPRequiresSignOff, MailTagsEnum.CompanyName, wsp.getCompany().getCompanyName()));

			returnMessage = "Changes have been found for the grant sign offs. The new SDF can now sign off.";
		}
		return returnMessage;
	}

	public void wspSdfCheck(Wsp wsp) throws Exception{
		// logic checks
		boolean employeeSdfRequired = false;
		boolean labourSdfRequired = false;
		String errorMsg = "";
		// check if labour SDF required
		if (wsp.getCompany().getRecognitionAgreement() != null) {
			if (wsp.getCompany().getRecognitionAgreement().equals(YesNoLookupService.instance().findByKey(HAJConstants.YES_ID))) {
				labourSdfRequired = true;
			}
		} else {
			throw new Exception("Unable to locate Recognition Agreement. Contact support!");
		}
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
		boolean primaryFound = false;
		boolean labourFound = false;
		boolean employeeFound = false;
		List<SDFCompany> sdfCompany = sdfCompanyService.findByCompany(wsp.getCompany());
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
		if (employeeSdfRequired && !employeeFound) {
			errorMsg += " Employee SDF ";
		}
		if (labourSdfRequired && !labourFound) {
			errorMsg += " Labour/Union SDF ";
		}
		if (errorMsg != "") {
			throw new Exception("Configuration Error. Please configure the following SDF for " + wsp.getCompany().getCompanyName()+": " + errorMsg);
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
	 *
	 * @param wsp
	 *            the wsp
	 * @return the wsp
	 * @throws Exception
	 *             the exception
	 */
	public Wsp resolveDocs(Wsp wsp) throws Exception {
		try {
			wsp.setDocs(docService.searchByWSP(wsp));
			prepareDocs(ConfigDocProcessEnum.WSP, wsp);
		} catch (Exception e) {
			logger.fatal(e);
		}

		return wsp;
	}

	/**
	 * Prepare docs.
	 *
	 * @param configDocProcess
	 *            the config doc process
	 * @param wsp
	 *            the wsp
	 * @throws Exception
	 *             the exception
	 */
	public void prepareDocs(ConfigDocProcessEnum configDocProcess, Wsp wsp) throws Exception {
		List<ConfigDoc> l = configDocService.findByProcessNotUploadedWSP(wsp, configDocProcess);
		if (l != null && l.size() > 0) {
			l.forEach(a -> {
				wsp.getDocs().add(new Doc(a, wsp));
			});
		}
	}
	
	public void prepareDocForNewWsp(ConfigDocProcessEnum configDocProcess, Wsp wsp) throws Exception{
		if (wsp != null && wsp.getId() == null) {
			wsp.setDocs(new ArrayList<>());
			List<ConfigDoc> l = configDocService.findByProcessNotUploadedNewWSP(configDocProcess);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					wsp.getDocs().add(new Doc(a, wsp));
				});
			}
		} else {
			prepareDocs(configDocProcess, wsp);
		}
	}
	
	public void prepareDocForNewWspByWspDocList(ConfigDocProcessEnum configDocProcess, Wsp wsp) throws Exception{
		if (wsp != null && wsp.getId() == null) {
			List<WspDocRequirements> wspDocReqList = new ArrayList<>();
			switch (wsp.getWspType()) {
			case LegacyLinkedGrantApplication:
				wspDocReqList.add(WspDocRequirements.InterSetaTransferDoc);
				break;
			case IntersetaTransferMandGrant:
				wspDocReqList.add(WspDocRequirements.InterSetaTransferDoc);
				break;
			default:
				break;
			}
			wsp.setDocs(new ArrayList<>());
			List<ConfigDoc> l = configDocService.findByProcessWSPDocRequirements(configDocProcess, wspDocReqList);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					wsp.getDocs().add(new Doc(a, wsp));
				});
			}
		} else {
			prepareDocs(configDocProcess, wsp);
		}
	}

	/**
	 * Document upload.
	 *
	 * @param entity
	 *            the entity
	 * @param users
	 *            the users
	 * @throws Exception
	 *             the exception
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
	 *
	 * @param doc
	 *            the doc
	 * @param users
	 *            the users
	 * @throws Exception
	 *             the exception
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
	 *
	 * @param companyId
	 *            the company id
	 * @param finYear
	 *            the finYear
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<Wsp> findByCompanyAndFinancialYear(Long companyId, int finYear) throws Exception {
		return dao.findByCompanyAndFinancialYear(companyId, finYear);
	}
	
	public List<WspHistoryBean> findWSPHistByCompany(Company company) throws Exception {
		List<Wsp> wspList= dao.findByCompany(company.getId());
		List<WspHistoricData> wsplist2= wspHistoricDataService.findByLevyNumber(company.getLevyNumber());
		return prepareHistoryBean(wspList,wsplist2);
		
	}
	
	public List<WspHistoryBean> findWSPHistByFinYear(Integer year) throws Exception {
		List<Wsp> wspList= dao.findByFinancialYear(year);
		List<WspHistoricData> wsplist2= wspHistoricDataService.findByFinancialYear(year);
		return prepareHistoryBean(wspList,wsplist2);
	}
	
	public List<Wsp> findByFinancialYear(int finYear) throws Exception {
		return dao.findByFinancialYear(finYear);
	}
	
	public List<WspHistoryBean> findByYearRange(Integer fromYear,Integer toYear) throws Exception {
		List<Wsp> wspList= dao.findByYearRange(fromYear,toYear);
		List<WspHistoricData> wsplist2= wspHistoricDataService.findByYearRange(fromYear,toYear);
		return prepareHistoryBean(wspList,wsplist2);
		
	}
	
	private List<WspHistoryBean> prepareHistoryBean(List<Wsp> wspList, List<WspHistoricData> wsplist2) {
		 List<WspHistoryBean> list=new ArrayList<>();
		 for(Wsp wsp:wspList)
		 {
			 String compName="";
			 String wspType="";
			 String levyNum="";
			 int finYar=0;
			 String status="";
			 if(wsp.getCompany() !=null && wsp.getCompany().getCompanyName()!=null){compName=wsp.getCompany().getCompanyName();}
			 if(wsp.getWspType() !=null && wsp.getWspType().getFriendlyName() !=null){ wspType =wsp.getWspType().getFriendlyName();}
			 if(wsp.getCompany() !=null && wsp.getCompany().getLevyNumber()!=null){levyNum=wsp.getCompany().getLevyNumber();}
			 if(wsp.getFinYear() !=null ){ finYar=wsp.getFinYear();}
			 if(wsp.getWspStatusEnum() !=null ){status=wsp.getWspStatusEnum().getFriendlyName();}
			 WspHistoryBean b=new WspHistoryBean(compName,wspType,levyNum,finYar,status);
			 b.setId(wsp.getId());
			 list.add(b);
		 }
			 
		 for(WspHistoricData wsp2:wsplist2)
		 {
			 String compName=wsp2.getOrganisationNameLegal();
			 if(compName==null || compName.equals("") ||  compName.equals(" ") || compName.isEmpty())
			 {
				 compName=wsp2.getOrganistaionNameTrade();
			 }
			 WspHistoryBean b=new WspHistoryBean(compName, wsp2.getGrantNameRevised(), wsp2.getReferenceNo(), wsp2.getLevyYear(), wsp2.getFinalStatus());
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
	
	public int countByCompanyAndFinYearAndWspApprovedRejectedStatus(Long companyId, int finYear) throws Exception {
		return dao.countByCompanyAndFinYearAndWspApprovedRejectedStatus(companyId, finYear);
	}

	/**
	 * Find Wsp by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.Wsp}
	 * @throws Exception
	 *             global exception
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
			List<Wsp> w = resolveWspSignOffs(findByStatus(WspStatusEnum.PendingSignOff, 2019));
			boolean changePending = false;
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
//					else if (wsp.getWspSignoffs().size() == 1) {
//						if ((wsp.getWspSignoffs().get(0).getAccept() != null && wsp.getWspSignoffs().get(0).getAccept()) || (wsp.getWspSignoffs().get(0).getDispute() != null && wsp.getWspSignoffs().get(0).getDispute())) {
//							changePending = true;
//						}
//					}
				}
				if (changePending && wsp.getWspStatusEnum() != WspStatusEnum.Pending) {
					wsp.setWspStatusEnum(WspStatusEnum.Pending);
					dao.update(wsp);
					TasksService.instance().completeTaskByTargetKey(wsp.getClass().getName(), wsp.getId());
					String message = "Grant Application for " + wsp.getCompany().getCompanyName() + " has been submitted and signed off please review.";
					TasksService.instance().findFirstInProcessAndCreateTask(message, wsp.getCreateUsers(), wsp.getId(), wsp.getClass().getName(), true, ConfigDocProcessEnum.WSP, MailDef.instance(MailEnum.WSPRequiresSignOff, MailTagsEnum.CompanyName, wsp.getCompany().getCompanyName()), null);					
					WspCompanyHistoryCreateService.instance().clearAndPopulateWspCompanyHistory(wsp.getCompany(), wsp.getClass().getName(), wsp.getId());
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
	
	public void submitDocumentUploadTask(Wsp entity, Users users, Tasks task) throws Exception{
//		TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.WSP, entity.getId());
		docCheckForWsp(entity);
		TasksService.instance().completeTask(task);
		String message = "Grant Application for " + entity.getCompany().getCompanyName() + " has been submitted and with a sign off upload please review.";
		TasksService.instance().findFirstInProcessAndCreateTask(message, users, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.WSP, MailDef.instance(MailEnum.WSPSubmittedAndSignedReview, MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()), null);
	}

	private void docCheckForWsp(Wsp entity) throws Exception{
		Boolean devicationDocRequired = false;
		Boolean minutesRequired = false;
		Boolean nonNqfRequired = false;
		BigDecimal minReq = (BigDecimal.valueOf(60));
		
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
					throw new Exception("Provide: "+ doc.getConfigDoc().getName() +" before submission.");
				}
			}
			
			// check if minutes required
			if (minutesRequired) {
				if (doc.getConfigDoc().getId() == HAJConstants.TRAINING_COMMITEE_MINUTES1_ID && doc.getId() == null) {
					throw new Exception("Provide: "+ doc.getConfigDoc().getName() +" before submission.");
				}
				if (doc.getConfigDoc().getId() == HAJConstants.TRAINING_COMMITEE_MINUTES2_ID && doc.getId() == null) {
					throw new Exception("Provide: "+ doc.getConfigDoc().getName() +" before submission.");
				}
			}
			
			// check if non nqf explination required
			if (nonNqfRequired) {
				if (doc.getConfigDoc().getId() == HAJConstants.NON_NQF_EXPLANATION_ID && doc.getId() == null) {
					throw new Exception("Provide: "+ doc.getConfigDoc().getName() +" before submission.");
				}
			}
		}
	}
	
	
	
	/**
	 * Sends the relevant notification to users based on email Notification integer set:
	 * 1 - Sends Approval notification (None applicable for the appeal). Grant is approved when sent.
	 * 2 - Sends Approved Notification after SDF has appealed grant. Grant is approved when sent. 
	 * 3 - Sends rejection notification to inform SDF to either accept the rejection or appeal the rejection. Grant is still pending approval when sent.
	 * 4 - Sends Final Rejection notice after the grant is rejected after the SDF has appealed the notification. Grant is rejected when sent. 
	 * 
	 * @param entity
	 * @param emailNotification
	 * @throws Exception
	 */
	public void sendReleventNotificationToUsers(Wsp entity, Integer emailNotification, List<RejectReasons> rejectReasons) throws Exception {
		List<Users> notificationUsers = locateClientUsersNotification(entity);
		Date submissionDate = null;
//		boolean atrWspSubmission = true; // for testing
		boolean atrWspSubmission = checkWspAtrSubmission(entity); 
		if (atrWspSubmission) {
			if (entity.getSubmissionDate() != null) {
				submissionDate = entity.getSubmissionDate();
			} else {
				WspSignoffService wspSignoffService = new WspSignoffService();
				submissionDate = wspSignoffService.returnLastestSignOffDate(entity);
				wspSignoffService = null;
			}
			if (emailNotification == 1) {
				for (Users users : notificationUsers) {
					// Approved without appeal
					sendApprovalMandatoryGrantEmail(users, entity.getCompany(), submissionDate, null, false);
				}
			} else if (emailNotification == 2) {
				for (Users users : notificationUsers) {
					// Approved with Appeal submission from the SDF
					sendMandatoryGrantAppealOutcomeEmail(users, entity.getCompany(), submissionDate, null, false);
				}
			} else if (emailNotification == 3) {
				for (Users users : notificationUsers) {
					// Rejected for teh SDF to accept or appeal grant application
					sentMandatoryGrantNonApprovalEmail(users, entity.getCompany(), submissionDate, rejectReasons, null, false);
				}
			} else if (emailNotification == 4) {
				for (Users users : notificationUsers) {
					// Rejected after appeal has been submitted by SDF
					sentMandatoryGrantAppealOutcomeEmail(users, entity.getCompany(), submissionDate, entity.getSdfAppealedGrantDate(), rejectReasons, null, false);
				}
			}
		}
	}
	
	/**
	 * Check Is WSP contains both WSP and ATR submissions
	 * If both submitted returns true else false
	 * @param entity
	 * @return boolean
	 * @throws Exception
	 */
	private boolean checkWspAtrSubmission(Wsp entity) throws Exception{
//		System.out.println("WSP Provided: " + mandatoryGrantDetailService.findByWSPCount(entity, WspReportEnum.WSP));
//		System.out.println("ATR Provided: " + mandatoryGrantDetailService.findByWSPCount(entity, WspReportEnum.ATR));
		if (mandatoryGrantDetailService.findByWSPCount(entity, WspReportEnum.WSP) > 0 && mandatoryGrantDetailService.findByWSPCount(entity, WspReportEnum.ATR) > 0) {
			return true;
		}else {
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
		    Send list
			HR Manager (if provided),
			Training Manager (if provided),
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
	 * Approval Mandatory Grant outcome email notification
	 * Approved without appeal
	 */
	public void sendApprovalMandatoryGrantEmail(Users user, Company company,Date submissiondate, String emailAddress, boolean adminTest) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
		params.put("submissiondate", sdf.format(submissiondate));
		params.put("company_id",company.getId());
		params.put("user_id", user.getId());
		if (user.getTitle() != null) {
			params.put("title", user.getTitle().getDescription());
		} else {
			params.put("title", "");
		}
		String filename=company.getLevyNumber()+"_Approval_Mandatory_Grant_Outcome.pdf";
		byte[] bites = JasperService.instance().convertJasperReportToByte("CSD-TP-ApprovalOfMandatoryGrant.jasper", params);
		String subject = "MANDATORY GRANT OUTCOME FOR "+company.getCompanyName().toUpperCase()+" - "+company.getLevyNumber()+"";
		String mssg =  "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear ";
			if (user.getTitle() != null) {
				mssg+=user.getTitle().getDescription()+" ";
			}
				mssg+= ""+user.getFirstName()+"  "+user.getLastName()+",</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We confirm that the company has submitted its Mandatory Grant "
				+ "application including the Workplace Skills Plan and Annual "
				+ "Training Report on "+sdf.format(submissiondate)+"."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Kindly note that the mandatory grant application for "+company.getCompanyName().toUpperCase()+" has been <b>approved</b>.  "
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please keep this Mandatory Grant Approval letter for BBBEE"
				+ " verification and BBBEE certification application purposes."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"><b>merSETA Administration</b></p>";
		//user.getEmail() 
		if (!adminTest) {
			GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, filename, "pdf", null);
		} else {
			GenericUtility.sendMailWithAttachement(emailAddress, subject, mssg, bites, filename, "pdf", null);
		}
		
	}

	/**
	 * Mandatory Grant Appeal Outcome
	 * Approved with Appeal
	 */
	public void sendMandatoryGrantAppealOutcomeEmail(Users user, Company company,Date submissiondate, String emailAddress, boolean adminTest) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
		params.put("submissiondate", sdf.format(submissiondate));
		params.put("company_id",company.getId());
		params.put("user_id", user.getId());
		if (user.getTitle() != null) {
			params.put("title", user.getTitle().getDescription());
		} else {
			params.put("title", "");
		}
		
		String filename=company.getLevyNumber()+"_Mandatory_Grant_Appeal_Outcome.pdf";
		byte[] bites = JasperService.instance().convertJasperReportToByte("CSD-TP-MandatoryGrantNon-Approval.jasper", params);
		String subject = "MANDATORY GRANT APPEAL OUTCOME FOR "+company.getCompanyName().toUpperCase()+" - "+company.getLevyNumber()+"";
		String mssg =  "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear ";
		if (user.getTitle() != null) {
			mssg+=user.getTitle().getDescription()+" ";
		}
			mssg+= user.getFirstName()+"  "+user.getLastName()+",</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We confirm that the company has submitted its Mandatory "
				+ "Grant application including the Workplace Skills Plan "
				+ "and Annual Training Report on "+sdf.format(submissiondate)+"."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We wish to inform you that the merSETA has processed the "
				+ "Mandatory Grant application appeal. The outcome of your "
				+ "appeal is <b>successful</b> and the Mandatory Grant application "
				+ "for "+company.getCompanyName()+" has been approved.  "
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please keep this Mandatory Grant Approval letter for "
				+ "BBBEE verification and BBBEE certification application purposes."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"><b>merSETA Administration</b></p>";
			
		if (!adminTest) {
			GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, filename, "pdf", null);
		} else {
			GenericUtility.sendMailWithAttachement(emailAddress, subject, mssg, bites, filename, "pdf", null);
		}
	}
	
	/**
	 * Approval Mandatory Grant Non Approval Email notification
	 * Rejected before Appeal
	 */
	public void sentMandatoryGrantNonApprovalEmail(Users user, Company company,Date submissiondate, List<RejectReasons> rejectReasons, String emailAddress, boolean adminTest) throws Exception {
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
		byte[] bites = JasperService.instance().convertJasperReportToByte("CSD-TP-MANDATORY GRANT NON-APPROVAL.jasper", params);
		String subject = "MANDATORY GRANT OUTCOME FOR  "+company.getCompanyName()+" - "+company.getLevyNumber()+" ";
		String mssg =  "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear ";
			if (user.getTitle() != null) {
				mssg+=user.getTitle().getDescription()+" ";
			}
			mssg+= user.getFirstName() + " " + user.getLastName() + ",</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We confirm that the company has submitted its Mandatory Grant application including the Workplace Skills Plan and Annual Training Report on "+sdf.format(submissiondate)+"."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Kindly note that the mandatory grant application for "+company.getCompanyName()+" has not been approved"
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The application will be kept for record purposes. "
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please be advised that should you wish to appeal the Mandatory Grant application outcome, please submit an appeal on the NSDMS within 14 days of receipt of this notification."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Should you have any queries, please do not hesitate to contact the merSETA Call Centre on: 086 163 7738 for assistance. "
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">merSETA Administration</p>";
		if (!adminTest) {
			GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "CSD-TP-MANDATORY GRANT NON-APPROVAL.pdf", "pdf", null);	
		}else {
			GenericUtility.sendMailWithAttachement(emailAddress, subject, mssg, bites, "CSD-TP-MANDATORY GRANT NON-APPROVAL.pdf", "pdf", null);
		}
		
	}
	
	/**
	 * Approval Mandatory Grant Non Approval Email notification
	 * MANDATORY GRANT APPEAL OUTCOME 
	 * Rejected after appeal
	 * */
	public void sentMandatoryGrantAppealOutcomeEmail(Users user, Company company,Date submissiondate, Date appealsubmissiondate,List<RejectReasons> rejectReasons, String emailAddress, boolean adminTest) throws Exception {
		
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
		byte[] bites = JasperService.instance().convertJasperReportToByte("CSD-TP MANDATORY GRANT_NON-APPROVAL APPEAL.jasper", params);
		String subject = "MANDATORY GRANT APPEAL OUTCOME FOR  "+company.getCompanyName()+" - "+company.getLevyNumber()+" ";
		String mssg =  "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear ";
		if (user.getTitle() != null) {
			mssg+=user.getTitle().getDescription()+" ";
		}
		mssg+= user.getFirstName() + " " + user.getLastName() + ",</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We confirm that the company has submitted its Mandatory Grant application including the Workplace Skills Plan and Annual Training Report on "+sdf.format(submissiondate)+"."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Kindly note that the appeal for the Mandatory Grant application for "+company.getCompanyName()+" is unsuccessful."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The application will be kept for record purposes. "
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Should you have any queries, please do not hesitate to contact the merSETA Call Centre on: 086 163 7738 for assistance. "
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">merSETA Administration</p>";
		
		if (!adminTest) {
			GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "CSD-TP MANDATORY GRANT_NON-APPROVAL APPEAL.pdf", "pdf", null);
		} else {
			GenericUtility.sendMailWithAttachement(emailAddress, subject, mssg, bites, "CSD-TP MANDATORY GRANT_NON-APPROVAL APPEAL.pdf", "pdf", null);
		}
		
	}

	// validateWSP(entity, users, false, false, false, true, false, false);
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
	// ConfigDocProcessEnum.WSP, MailDef.instance(MailEnum.WSPRequiresSignOff,
	// MailTagsEnum.CompanyName, entity.getCompany().getCompanyName()), null);
	// }
	// dao.updateBatch(dataEntities);
	
	
	public void downloadMOA(DgAllocationParent dgAllocationParent, Users user,Users clo,String cloRegion) {
	try {
		
		String cloFullName=clo.getFirstName()+" "+clo.getLastName();
		String cloEmail=clo.getEmail();
		
		 Wsp wsp=dgAllocationParent.getWsp();
		Map<String, Object> params = new HashMap<String, Object>();
		String path = HAJConstants.APP_PATH;
		// Creating Discretionary grant year to be displayed on the cover page
		String lastTwoDigitsOfNextYear = String.valueOf(wsp.getFinYear()).substring(Math.max(String.valueOf(wsp.getFinYear()).length() - 2, 0));
		String year = wsp.getFinYear() - 1 + "/" + lastTwoDigitsOfNextYear + " (Yr" + lastTwoDigitsOfNextYear + ")";
		// Adding Discretionary_Grant_Agreeement_details params

		RegionTown rt = RegionTownService.instance().findByTown(wsp.getCompany().getResidentialAddress().getTown());
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = regionService.findByKey(rt.getRegion().getId());
		
		//***************************Dg Allocation Data*****************************
		ArrayList<DgAllocation> dgList=new ArrayList<>();
		DgAllocationService dgAllocationService=new DgAllocationService();
		if(wsp.getId() !=null)
		{
			if(dgAllocationParent !=null)
			{
				dgList=(ArrayList<DgAllocation>) dgAllocationService.findByParentWhereAmountAwarded(dgAllocationParent);
			}
		   
		}
		params.put("DgAllocationDataSource", new JRBeanCollectionDataSource(dgList));
		params.put("AnnexureDataSource", new JRBeanCollectionDataSource(dgList));
		
		params.put("total_awarded_amnt",dgAllocationService.findTotalAllocatedWhereAmountAwarded(dgAllocationParent).doubleValue());
		
		
		params.put("wsp", wsp);
		params.put("company", companyService.findByKey(wsp.getCompany().getId()));
		params.put("wsp_id", wsp.getId());
		params.put("wsp_report", WspReportEnum.WSP.ordinal());
		params.put("etqa_code", HAJConstants.HOSTING_MERSETA_ETQA);
		params.put("funding_id", HAJConstants.DISC_FUNDING_ID);
		params.put("region", r.getDescription());
		params.put("year", year);
		params.put("merSETA_CEO", "");//Not in User
		params.put("terminationDate", "30 September " + (wsp.getFinYear() + 4));
		params.put("projectedStartDate", "1 Jan "+wsp.getFinYear());
		params.put("projectedEndDate", "31 Mar "+(wsp.getFinYear()+1));
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
		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Thank you for submitting a discretionary grant application for "+wsp.getFinYear()
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We are pleased to inform you that the application was successful. "
				+ "Please may you complete the project implementation plan on the NSDMS "
				+ "and submit together with the signed Memorandum of Agreement (MOA) "
				+ "within 30 business days from the date of receipt."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please note that should the enclosed MOA is not uploaded on the "
				+ "NSDMS within 30 business days from the date of issue, the merSETA "
				+ "may withdraw the MOA. "
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "For further assistance/support, kindly contact the designated "
				+ "Client Liaison Officer: "+cloFullName+" (Email: "+cloEmail+") or the respective "+cloRegion+" Office."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "<b>The merSETA Programme Implementation Unit</b>"
				+ "</p>";

		GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, fileName, "pdf", null);
		
		
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}
	public Date determineSignOffClosureDate(Wsp wsp, Date closureDate) throws Exception {
//		Date closureDate = GenericUtility.getEndOfDay(GenericUtility.sdf6.parse("30-04-" + (wsp.getFinYear() - 1)));
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
					}else {
						submissionAvalible = false;
					}
				} else {
					submissionAvalible = false;
				}
				
			}
		}
		
		return submissionAvalible;
	}
	
	public void runValidiationCheckFirstSubmit(Wsp wsp) throws Exception{
		// populates additional data for WSP validations
		wsp = findByKey(wsp.getId());
		// Locates primary SDF
		SDFCompany primarySdf = sdfCompanyService.findPrimarySDF(wsp.getCompany());
		if (primarySdf == null) {
			throw new Exception("Unable to locate primary sdf for company: " + wsp.getCompany().getCompanyName());
		} else {
			validateWSPAdmin(wsp, primarySdf.getSdf(), true, false, true, false, true, true, true);
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
	
	public boolean determainDgVerificationGenerationRejectedGrant(Wsp wsp) throws Exception{
		boolean canGenerateVerification = false;
		Company company = companyService.findByKey(wsp.getCompany().getId());
		// small companies less than 50 employees
		if (company != null && company.getId() != null && company.getNumberOfEmployees() != null && company.getNumberOfEmployees() < 50) {
			canGenerateVerification = true;
		}
		company = null;
		return canGenerateVerification;
	}
	
	/*
	 * ADMIN functionality to re-send or view email notification with letters being sent out for the grant process.
	 * If re-send true will re-send notification to applicable users (CAUTION WILL SEND TO SDF AND COMPANY CONTACTS).
	 * If re-send false will send to email address provided and will use first user as holding place.
	 * Rejection reasons must be provided in order to work for the re-send false.
	 * If re-send true will find Latest assigned rejection reasons and send through. will error if unable to find.
	 */
	public void sendReleventNotificationToUsersAdmin(Wsp entity, Integer emailNotification, List<RejectReasons> rejectReasons, String emailAddress, boolean resend) throws Exception {
		if (resend) {
			RejectReasonsService rrs = new RejectReasonsService();
			rejectReasons = rrs.locateReasonsSelectedByTargetKeyClassAndProcess(entity.getId(), Wsp.class.getName(), ConfigDocProcessEnum.WSP);
		}
		List<Users> notificationUsers = locateClientUsersNotification(entity);
		Date submissionDate = null;
		boolean atrWspSubmission = checkWspAtrSubmission(entity); 
		if (atrWspSubmission) {
			if (entity.getSubmissionDate() != null) {
				submissionDate = entity.getSubmissionDate();
			} else {
				WspSignoffService wspSignoffService = new WspSignoffService();
				submissionDate = wspSignoffService.returnLastestSignOffDate(entity);
				wspSignoffService = null;
			}
			if (emailNotification == 1) {
				
				// Approved without appeal
				
				if (resend) {
					for (Users users : notificationUsers) {
						sendApprovalMandatoryGrantEmail(users, entity.getCompany(), submissionDate, null, false);
					}
				} else {
					for (Users users : notificationUsers) {
						sendApprovalMandatoryGrantEmail(users, entity.getCompany(), submissionDate, emailAddress.trim(), true);
						break;
					}
				}
			} else if (emailNotification == 2) {
				
				// Approved with Appeal submission from the SDF
				
				if (resend) {
					for (Users users : notificationUsers) {
						sendMandatoryGrantAppealOutcomeEmail(users, entity.getCompany(), submissionDate, null, false);
					}
				} else {
					for (Users users : notificationUsers) {
						sendMandatoryGrantAppealOutcomeEmail(users, entity.getCompany(), submissionDate, emailAddress.trim(), true);
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
						sentMandatoryGrantNonApprovalEmail(users, entity.getCompany(), submissionDate, rejectReasons, null, false);
					}
				} else {
					for (Users users : notificationUsers) {
						sentMandatoryGrantNonApprovalEmail(users, entity.getCompany(), submissionDate, rejectReasons, emailAddress.trim(), true);
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
						sentMandatoryGrantAppealOutcomeEmail(users, entity.getCompany(), submissionDate, entity.getSdfAppealedGrantDate(), rejectReasons, null, false);
					}
				} else {
					for (Users users : notificationUsers) {
						sentMandatoryGrantAppealOutcomeEmail(users, entity.getCompany(), submissionDate, entity.getSdfAppealedGrantDate(), rejectReasons, emailAddress.trim(), true);
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
		/*  Version One
		 *  Business rule: 14 days to complete from task being sent
		 *  Example: 
		 *  - Manager rejects to SDF on 1st of September 2018
		 *	- 2nd, 3rd, 4th, 5th, 6th, 7th, 8th, 9th, 10th, 11th, 12th, 13th, 14th, 15th of September 2018 they can action
		 *  - System must auto reject grant application on the 16th of September 2018 (morning schedule configuration) 
		 *  Java daysToComplate: 15
		 *  
		 *  
		 *  Version Two
		 *  Business rule: 14 business days to complete from task being sent (Excluding weekends)
		 *  Example: 
		 *  - Manager rejects to SDF on 1st of September 2020
		 *	- 2nd, 3rd, 4th, 5th, 6th, 7th, 8th, 9th, 10th, 11th, 12th, 13th, 14th, 15th of September 2020 they can action
		 *  - System must auto reject grant application on the 22nd of September 2020 (morning schedule configuration) 
		 *  Java daysToComplate: 15
		 */
		Integer daysToComplate = 15;
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
		Date today = GenericUtility.getStartOfDay(new Date());
		List<Wsp> wspRejecetdAwaitingSdf = findWspAwaitingDescionBySdfRejectionAppeal();
		for (Wsp wspRejected : wspRejecetdAwaitingSdf) {
			if (wspRejected.getGrantRejectedDate() != null) {
				Date compltedByDateIncWeekends = GenericUtility.addDaysToDateExcludeWeekends(GenericUtility.getStartOfDay(wspRejected.getGrantRejectedDate()), daysToComplate);
				String information = "14 Business Days System Rejection Business Rule For: Grant Application. Date SDF Received Application: " + sdf.format(wspRejected.getGrantRejectedDate()) + ".";
				if (today.equals(compltedByDateIncWeekends) || today.after(compltedByDateIncWeekends)) {
					information += " Changes: status change from "+ wspRejected.getWspStatusEnum().getFriendlyName()+" to " + WspStatusEnum.Rejected.getFriendlyName() + ",";
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
					
					// version one
//					boolean generateVerification = determainDgVerificationGenerationRejectedGrant(wspRejected);
					
					// version two
					boolean generateVerification = false;
					if (wspRejected.getFinYear() != null && wspRejected.getFinYear() > 2020) {
						generateVerification = true;
					} else {
						generateVerification = determainDgVerificationGenerationRejectedGrant(wspRejected);
					}
					
					
					if (generateVerification) {
						DgVerificationService.instance().generateForRejectedWspSmallCompany(wspRejected);
					}
					// creates log
					if (wspRejected != null && wspRejected.getCompany() != null) {
						ScheduleChangesLogService.instance().addNewEntry(wspRejected.getCompany(), null, wspRejected.getClass().getName(), wspRejected.getId(), information, false);
					} else {
						ScheduleChangesLogService.instance().addNewEntry(null, null, wspRejected.getClass().getName(), wspRejected.getId(), information, false);
					}
				}
//				old method
//				Integer daysBetweenDates = GenericUtility.getDaysBetweenDates(GenericUtility.getStartOfDay(wsp.getGrantRejectedDate()), today);
//				if (daysBetweenDates >= 5) {
//					
//				}
			}
		}
	}
	
	public void checkIfAfterFebForGrantInitiation(Integer finYear, Date now) throws Exception{
		Date grantStartDate = GenericUtility.getStartOfDay(GenericUtility.sdf7.parse(NSDMSConfiguration.getString("grant.start.date") + (finYear - 1)));
		if (GenericUtility.getEndOfDay(now).before(grantStartDate)) {
			throw new Exception("You can only initaite a application for financial year " + finYear + " on: " + GenericUtility.sdf7.format(grantStartDate) );
		}
	}
	
	public void checkIfGpsAssignedToCompany(Wsp wsp) throws Exception{
		boolean missingInformation = false;
		Company company = companyService.findByKey(wsp.getCompany().getId());
		if (company.getResidentialAddress() != null && company.getResidentialAddress().getId() != null) {
			Address address = AddressService.instance().findByKey(company.getResidentialAddress().getId());
			if (address == null || address.getId() == null) {
				missingInformation = true;
			}else {
				if (address.getLatitudeDegrees() == null) {
					missingInformation = true;
				}
				if (address.getLatitudeMinutes() == null) {
					missingInformation = true;
				}
				if (address.getLatitudeSeconds() == null) {
					missingInformation = true;
				}
				
				if (address.getLongitudeDegrees() == null) {
					missingInformation = true;
				}
				if (address.getLongitudeMinutes() == null) {
					missingInformation = true;				
				}
				if (address.getLongitudeSeconds() == null) {
					missingInformation = true;
				}
			}
		} else {
			missingInformation = true;
		}
		if (missingInformation) {
			throw new Exception("Please provide: "+company.getLevyNumber()+" GPS coordinates before attempting to initiate. This can be done through company management.");
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
	 * Wsp Report By Financial Year
	 * Note: If finYear is null wont filter by financial year
	 * Note: if List of status not provided wont filter by status
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
	
	@SuppressWarnings("unchecked")
	public List<Wsp> wspReportByFinancialYearWithAllResolves(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Integer finYear, List<WspStatusEnum> statusList) throws Exception {
		String hql = "select o from Wsp o where o.company is not null";
		if (finYear != null) {
			hql += " and o.finYear = :finYear ";
			filters.put("finYear", finYear);
		}
		return resolveReportInformation((List<Wsp>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public List<Wsp> resolveReportInformation(List<Wsp> entityList) throws Exception {
		for (Wsp wsp : entityList) {
			resolveReportInformation(wsp);
		}
		return entityList;
	}
	
	public Wsp resolveReportInformation(Wsp entity) throws Exception {
		populateRejectReasons(entity);
		entity.setExtensionRequests(ExtensionRequestService.instance().findByWSP(entity));
		populateInformationForReport(entity);
		if (companyService == null) {
			companyService = new CompanyService();
		}
		companyService.resolveCompanyDataCloCrmRegionReport(entity.getCompany());
		entity.getCompany().setRegion(companyService.populateCompanyRegion(entity.getCompany()));
		entity.getCompany().setProvince(companyService.populateCompanyProvince(entity.getCompany()));
//		companyService.populateCompanyRegionTown(entity.getCompany());
		return entity;
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
	public List<StatusReportBean> populateStatusReportWsp(List<Integer> finYear) throws Exception{
		List<StatusReportBean> reportList = new ArrayList<>();
		reportList.add(new StatusReportBean("Total Grant Applications", countAllDgFinYear(finYear)));
		for (WspStatusEnum status : WspStatusEnum.values()) {
			if (status != WspStatusEnum.ManagerApproved) {
				if (status != WspStatusEnum.ManagerRejected ) {
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
	
	public List<StatusReportBean> populateStatusReportWspByYear(Integer finYear) throws Exception{
		List<StatusReportBean> reportList = new ArrayList<>();
		reportList.add(new StatusReportBean("Total Grant Applications", countAllDgFinYear(finYear)));
		for (WspStatusEnum status : WspStatusEnum.values()) {
			if (status != WspStatusEnum.ManagerApproved) {
				if (status != WspStatusEnum.ManagerRejected ) {
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
	
	public void findLastActionUser(Wsp wsp)throws Exception{
		List<Tasks>list = tasksService.findTasksByTypeAndKey(ConfigDocProcessEnum.WSP, wsp.getClass().getName(), wsp.getId());
		if(list.size()>0){
			if(wsp.getSystemApprovalRejection()!= null && wsp.getSystemApprovalRejection()) {
				Users u= new Users();
				u.setFirstName("System");
				u.setLastName("Rejected");
				wsp.setLastActionUser(u);
			}else if(wsp.getSystemApprovalRejection()== null && wsp.getWspStatusEnum() == WspStatusEnum.Rejected ) {
				for(Tasks task: list) {
					if(task.getProcessRole() != null && task.getProcessRole().getRolePermission() != null && task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalApproval) {
						wsp.setLastActionUser(task.getActionUser());
						break;
					}
				}
			}else if(wsp.getWspStatusEnum() == WspStatusEnum.Approved){
				if(list.get(0).getProcessRole() != null && list.get(0).getProcessRole().getRolePermission() != null && list.get(0).getProcessRole().getRolePermission() == UserPermissionEnum.FinalApproval) {
					wsp.setLastActionUser(list.get(0).getActionUser());
				}else {
					wsp.setLastActionUser(new Users());
				}
			}
		}else {
			wsp.setLastActionUser(new Users());
		}		
	}
	
	
	
    /**
     * Wsp Report By Financial Year*/
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
    
	public List<WspHistoryBean> wspHistoryData(int first, int pageSize, String sortField, SortOrder sortOrder,Map<String, Object> filters,WSPSearchType searchType,Company company,Integer finYear,Integer fromYear,Integer toYear) {
		String hql ="";
		List<Wsp> wspList=new ArrayList<>();
		List<WspHistoricData> wspHistoryBeanList=new ArrayList<>();
		
		if(searchType ==WSPSearchType.searchByCompany)
		{
			filters.put("companyKey", company.getId());
			hql = "select o from Wsp o where o.company.id = :companyKey";
		    wspList=(List<Wsp>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
		    
		    filters.put("companyKey", company.getLevyNumber());
			hql = "select o from WspHistoricData o where o.referenceNo = :companyKey";
			wspHistoryBeanList=(List<WspHistoricData>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
			
		}
		else if(searchType ==WSPSearchType.searchByFinancialYear)
		{
			  filters.put("finYear", finYear);
			  hql = "select o from Wsp o where o.finYear = :finYear";
			  wspList=(List<Wsp>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
			  
			  hql = "select o from WspHistoricData o where o.levyYear = :finYear";
			  wspHistoryBeanList=(List<WspHistoricData>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
		}
		else if(searchType ==WSPSearchType.searchByYearRange)
		{
			  filters.put("fromYear", fromYear);
			  filters.put("toYear", toYear);
			  hql = "select o from Wsp o where o.finYear >= :fromYear and o.finYear <= :toYear";
			  wspList=(List<Wsp>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
			  
			  hql = "select o from WspHistoricData o where o.levyYear >= :fromYear and o.levyYear <= :toYear";
			  wspHistoryBeanList=(List<WspHistoricData>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
		}
		
	   return prepareHistoryBean(wspList, wspHistoryBeanList);
	}
	
	public int wspHistoryDataCount(Map<String, Object> filters,WSPSearchType searchType,Company company,Integer finYear,Integer fromYear,Integer toYear) {
		String hql ="";
		List<Wsp> wspList=new ArrayList<>();
		List<WspHistoricData> wspHistoryBeanList=new ArrayList<>();
		int totalCount=0;
		if(searchType ==WSPSearchType.searchByCompany)
		{
			filters.put("companyKey", company.getId());
			hql = "select count(o) from Wsp o where o.company.id = :companyKey";
		    int count1= dao.countWhere(filters, hql);
		   
		     
		    filters.put("companyKey", company.getLevyNumber());
			hql = "select count(o) from WspHistoricData o where o.referenceNo = :companyKey";
			int count2= dao.countWhere(filters, hql);
			totalCount=count1+count2;
			
		}
		else if(searchType ==WSPSearchType.searchByFinancialYear)
		{
			  filters.put("finYear", finYear);
			  hql = "select count(o) from Wsp o where o.finYear = :finYear";
			  int count1= dao.countWhere(filters, hql);
			  
			  hql = "select count(o) from WspHistoricData o where o.levyYear = :finYear";
			  int count2= dao.countWhere(filters, hql);
			  totalCount=count1+count2;
			
		}
		else if(searchType ==WSPSearchType.searchByYearRange)
		{
			  filters.put("fromYear", fromYear);
			  filters.put("toYear", toYear);
			  hql = "select count(o) from Wsp o where o.finYear >= :fromYear and o.finYear <= :toYear";
			  int count1= dao.countWhere(filters, hql);
			  
			  hql = "select count(o) from WspHistoricData o where o.levyYear >= :fromYear and o.levyYear <= :toYear";
			  int count2= dao.countWhere(filters, hql);
			  totalCount=count1+count2;
		}
		
	   return totalCount;
	}
	
	public List<Wsp> findWspbyFinYearApprovedAndApprovedVerification(Integer finYear) throws Exception {
		return dao.findWspbyFinYearApprovedAndApprovedVerification(finYear);
	}
	
	public List<Wsp> findWspbyFinYearAndInVerification(Integer finYear) throws Exception {
		return dao.findWspbyFinYearAndInVerification(finYear);
	}
	
	public List<CounterBean> countGrantApprovalsByGrantYearAndCompanySize(Integer grantYear, CompanySizeEnum companySizeEnum, Date fromDate, Date toDate, List<WspStatusEnum> wspStatusList, List<WspTypeEnum> wspTypeList) throws Exception {
		return dao.countGrantApprovalsByGrantYearAndCompanySize(grantYear, companySizeEnum, fromDate, toDate, wspStatusList, wspTypeList);
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

	public List<WspCompanyReportBean> populateGrantSubmissionReportByCompany(List<WspStatusEnum> statusList) throws Exception {
		return dao.populateGrantSubmissionReportByCompany(statusList);
	}
	
	public List<WspCompanyReportBean> populateGrantSubmissionReportByCompanySummary(List<WspStatusEnum> statusList) throws Exception {	
		return dao.populateGrantSubmissionReportByCompanySummary(statusList);
	}
	
	public List<WspFirmSubmissionBean> populateGrantSubmissionNsdpReportByQmrQuarter(Integer grantYear, QmrFinYears qmrFinYear) throws Exception {
		Date finYearStartDate = GenericUtility.getStartOfDay(HAJConstants.sdf.parse(qmrFinYear.getFinYearStart() + GenericUtility.APRIL));
		Date finYearEndDate =  GenericUtility.getEndOfDay(HAJConstants.sdf.parse(qmrFinYear.getFinYearEnd() + GenericUtility.MARCH));
		
		Date formDate = new Date();
		Date toDate = new Date();
		
		if (qmrFinYear.getFinYearQuarters() != null && qmrFinYear.getFinYearQuarters() == FinYearQuartersEnum.QuarterOne) {
			// instead of 01 April must start be 01 Feb for Quarter 1 
			formDate = GenericUtility.getStartOfDay(GenericUtility.getFirstDayOfMonth(GenericUtility.deductMonthsFromDate(qmrFinYear.getFromDate(), 2)));
		} else {
			formDate = GenericUtility.getStartOfDay(qmrFinYear.getFromDate());
		}
		toDate = GenericUtility.getEndOfDay(qmrFinYear.getToDate());
		
		return populateGrantSubmissionNsdpReport(grantYear, finYearStartDate, finYearEndDate, formDate, toDate, WspStatusEnum.getWspStatusEnumApprovedList(), WspTypeEnum.getNsdpReportWspTypeList(), null);
	}
			
	public List<WspFirmSubmissionBean> populateGrantSubmissionNsdpReport(Integer grantYear, Date finYearStartDate, Date finYearEndDate, Date quarterStartDate, Date quarterEndDate
			, List<WspStatusEnum> wspStatusList, List<WspTypeEnum> wspTypeList, CompanySizeEnum companySizeEnum) throws Exception {
		return dao.populateGrantSubmissionNsdpReport(grantYear, finYearStartDate, finYearEndDate, quarterStartDate, quarterEndDate, wspStatusList, wspTypeList, companySizeEnum);
	}
	
	public List<GrantsSubmissionReportBean> populateGrantsSubmissionReportByGrantYear(Integer grantYear) throws Exception {
		return dao.populateGrantsSubmissionReportByGrantYear(grantYear);
	}
	
}