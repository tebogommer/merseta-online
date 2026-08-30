package haj.com.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.bean.AttachmentBean;
import haj.com.bean.ReportSignOffBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.TrainingProviderMonitoringDAO;
import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.ProcessRoles;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.Signoff;
import haj.com.entity.Tasks;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingProviderMonitoring;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.AuditorMonitorReview;
import haj.com.entity.lookup.JobTitle;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.AuditorMonitorReviewService;
import haj.com.service.lookup.JobTitleService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.service.lookup.RolesService;
import haj.com.service.lookup.TownService;
import haj.com.utils.GenericUtility;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class TrainingProviderMonitoringService extends AbstractService {
	
	/** The dao. */
	private TrainingProviderMonitoringDAO dao = new TrainingProviderMonitoringDAO();
	private CompanyUsersService companyUsersService = null;
	private ConfigDocService configDocService = new ConfigDocService();
	private AuditorMonitorReviewService auditorMonitorReviewService = new AuditorMonitorReviewService();
	private TrainingProviderApplicationService trainingProviderApplicationService;
	private CompanyService companyService;
	private SignoffService signoffService = null;
	private UsersService usersService;
	private RejectReasonsService rejectReasonsService;
	

	/**
	 * Get all TrainingProviderMonitoring
	 * 
	 * @author TechFinium
	 * @see TrainingProviderMonitoring
	 * @return a list of {@link haj.com.entity.TrainingProviderMonitoring}
	 * @throws Exception
	 *             the exception
	 */
	public List<TrainingProviderMonitoring> allTrainingProviderMonitoring() throws Exception {
		return dao.allTrainingProviderMonitoring();
	}

	/**
	 * Create or update TrainingProviderMonitoring.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see TrainingProviderMonitoring
	 */
	public void create(TrainingProviderMonitoring entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update TrainingProviderMonitoring.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see TrainingProviderMonitoring
	 */
	public void update(TrainingProviderMonitoring entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete TrainingProviderMonitoring.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see TrainingProviderMonitoring
	 */
	public void delete(TrainingProviderMonitoring entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.TrainingProviderMonitoring}
	 * @throws Exception
	 *             the exception
	 * @see TrainingProviderMonitoring
	 */
	public TrainingProviderMonitoring findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	public void prepareNewRegistration(TrainingProviderMonitoring entity) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), ConfigDocProcessEnum.ProviderMonitoring));
			List<ConfigDoc> l = configDocService.findByProcessNotUploaded(entity.getClass().getName(), entity.getId(), ConfigDocProcessEnum.ProviderMonitoring, CompanyUserTypeEnum.User);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = configDocService.findByProcess(ConfigDocProcessEnum.ProviderMonitoring, CompanyUserTypeEnum.User);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}
	
	public void prepareNewRegistrationWithConfig(TrainingProviderMonitoring entity, ConfigDocProcessEnum configDocProcess) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			List<ConfigDoc> l = configDocService.findByProcessNotUploaded(entity.getClass().getName(), entity.getId(), configDocProcess, CompanyUserTypeEnum.User);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = configDocService.findByProcess(configDocProcess, CompanyUserTypeEnum.User);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}

	/**
	 * Find TrainingProviderMonitoring by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.TrainingProviderMonitoring}
	 * @throws Exception
	 *             the exception
	 * @see TrainingProviderMonitoring
	 */
	public List<TrainingProviderMonitoring> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load TrainingProviderMonitoring
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.TrainingProviderMonitoring}
	 * @throws Exception
	 *             the exception
	 */
	public List<TrainingProviderMonitoring> allTrainingProviderMonitoring(int first, int pageSize) throws Exception {
		return dao.allTrainingProviderMonitoring(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of TrainingProviderMonitoring for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the TrainingProviderMonitoring
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(TrainingProviderMonitoring.class);
	}

	/**
	 * Lazy load TrainingProviderMonitoring with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            TrainingProviderMonitoring class
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
	 * @return a list of {@link haj.com.entity.TrainingProviderMonitoring}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<TrainingProviderMonitoring> allTrainingProviderMonitoring(Class<TrainingProviderMonitoring> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<TrainingProviderMonitoring>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of TrainingProviderMonitoring for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            TrainingProviderMonitoring class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the TrainingProviderMonitoring entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<TrainingProviderMonitoring> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	public int countOpenMonitoringReport(Company company, Boolean audit) throws Exception {
		return dao.countOpenMonitoringReport(company.getId(), audit);
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderMonitoring> allMonitoringForProvider(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from TrainingProviderMonitoring o where o.company.id = :companyID order by o.createDate desc";
		return (List<TrainingProviderMonitoring>) dao.hqlAndParamOnly(TrainingProviderMonitoring.class, first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countMonitoringForProvider(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderMonitoring o where o.company.id = :companyID";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderMonitoring> allAuditsForProvider(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from TrainingProviderMonitoring o where o.company.id = :companyID and o.audit = :auditValue order by o.createDate desc";
		return (List<TrainingProviderMonitoring>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllAuditsgForProvider(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderMonitoring o where o.company.id = :companyID and o.audit = :auditValue";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderMonitoring> allAuditsMonitoringByEvidanceAcceptableAndNotWithdrawn(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from TrainingProviderMonitoring o where o.status <> :status "
				+ "and o.id in (select distinct x.targetKey from AuditorMonitorReview x where x.evidenceRequired = :evidenceRequired and x.targetClass = :targetClass)";
		return populateAdditionalInformationListCal((List<TrainingProviderMonitoring>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countAllAuditsMonitoringByEvidanceAcceptableAndNotWithdrawn(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderMonitoring o where o.status <> :status "
				+ "and o.id in (select distinct x.targetKey from AuditorMonitorReview x where x.evidenceRequired = :evidenceRequired and x.targetClass = :targetClass)";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderMonitoring> allAuditsMonitoringByStatus(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from TrainingProviderMonitoring o where o.status = :status ";
		return populateAdditionalInformationListCal((List<TrainingProviderMonitoring>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllAuditsMonitoringByStatus(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderMonitoring o where o.status = :status ";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderMonitoring> allMonitoringWithOrWithoutIssues(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from TrainingProviderMonitoring o where o.id in (select distinct x.trainingProviderMonitoring.id from AuditorMonitorReview x where x.evidenceRequired = :evidenceRequired)";
		return (List<TrainingProviderMonitoring>) dao.hqlAndParamOnly(TrainingProviderMonitoring.class, first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countMonitoringWithOrWithoutIssues(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderMonitoring o where o.id in (select distinct x.trainingProviderMonitoring.id from AuditorMonitorReview x where x.evidenceRequired = :evidenceRequired)";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderMonitoring> allMonitoringWithoutIssues(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from TrainingProviderMonitoring o where o.id not in (select distinct x.trainingProviderMonitoring.id from AuditorMonitorReview x where x.evidenceRequired = :evidenceRequired and x.trainingProviderMonitoring is not null)";
		return (List<TrainingProviderMonitoring>) dao.hqlAndParamOnly(TrainingProviderMonitoring.class, first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countMonitoringWithoutIssues(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderMonitoring o where o.id not in (select distinct x.trainingProviderMonitoring.id from AuditorMonitorReview x where x.evidenceRequired = :evidenceRequired and x.trainingProviderMonitoring is not null)";
		return dao.countWhere(filters, hql);
	}

	public void submitCompanyLearners(TrainingProviderMonitoring trainingProviderMonitoring, Users qaUser, Users sdpUser, Tasks tasks) throws Exception {
		trainingProviderMonitoring.setStatus(ApprovalEnum.PendingApproval);
		if (trainingProviderMonitoring.getInitiatingQA() == null) {
			trainingProviderMonitoring.setInitiatingQA(qaUser);
		}
		create(trainingProviderMonitoring);
		auditorMonitorReviewService.prepNewReview(trainingProviderMonitoring);
		HostingCompanyEmployeesService hces = new HostingCompanyEmployeesService();
		TownService townService = new TownService();
		List<Users> qualityAssurorUsers = new ArrayList<>();
		
		Address address = null;
		if (trainingProviderMonitoring.getTrainingProviderApplication() != null && trainingProviderMonitoring.getTrainingProviderApplication().getTrainingSite() != null
				&& trainingProviderMonitoring.getTrainingProviderApplication().getTrainingSite().getResidentialAddress() != null) {
			address = AddressService.instance().findByKey(trainingProviderMonitoring.getTrainingProviderApplication().getTrainingSite().getResidentialAddress().getId());
		} else {
			if (trainingProviderMonitoring.getCompany() != null && trainingProviderMonitoring.getCompany().getResidentialAddress() != null && trainingProviderMonitoring.getCompany().getResidentialAddress().getId() != null) {
				address = AddressService.instance().findByKey(trainingProviderMonitoring.getCompany().getResidentialAddress().getId());
			}
		}
		if (address != null && address.getTown() != null) {
			qualityAssurorUsers = hces.locateHostingCompanyEmployeesByRegionAndRole(townService.findByKey(address.getTown().getId()), RolesService.instance().findByKey(HAJConstants.QUALITY_ASSUROR_ROLE_ID));
		}
		
		if (trainingProviderMonitoring.getInitiatingQA() != null) {
			qualityAssurorUsers = new ArrayList<>();
			qualityAssurorUsers.add(trainingProviderMonitoring.getInitiatingQA());
			if (trainingProviderMonitoring.getAudit()) {
				TasksService.instance().findFirstInProcessAndCreateTask("", qaUser, trainingProviderMonitoring.getId(), trainingProviderMonitoring.getClass().getName(), true, ConfigDocProcessEnum.AuditMonitoring, null, qualityAssurorUsers);
			} else {
				TasksService.instance().findFirstInProcessAndCreateTask("", qaUser, trainingProviderMonitoring.getId(), trainingProviderMonitoring.getClass().getName(), true, ConfigDocProcessEnum.ProviderMonitoring, null, qualityAssurorUsers);
			}
			populateSignOffForProviderMonitoringAudit(trainingProviderMonitoring);
		} else if (qualityAssurorUsers.size() == 0) {
			qualityAssurorUsers = new ArrayList<>();
			qualityAssurorUsers.add(trainingProviderMonitoring.getInitiatingQA());
			if (trainingProviderMonitoring.getAudit()) {
				TasksService.instance().findFirstInProcessAndCreateTask("", qaUser, trainingProviderMonitoring.getId(), trainingProviderMonitoring.getClass().getName(), true, ConfigDocProcessEnum.AuditMonitoring, null, qualityAssurorUsers);
			} else {
				TasksService.instance().findFirstInProcessAndCreateTask("", qaUser, trainingProviderMonitoring.getId(), trainingProviderMonitoring.getClass().getName(), true, ConfigDocProcessEnum.ProviderMonitoring, null, qualityAssurorUsers);
			}
			populateSignOffForProviderMonitoringAudit(trainingProviderMonitoring);
		} else {
			if (trainingProviderMonitoring.getAudit()) {
				TasksService.instance().findFirstInProcessAndCreateTask("", qaUser, trainingProviderMonitoring.getId(), trainingProviderMonitoring.getClass().getName(), true, ConfigDocProcessEnum.AuditMonitoring, null, qualityAssurorUsers);
			} else {
				TasksService.instance().findFirstInProcessAndCreateTask("", qaUser, trainingProviderMonitoring.getId(), trainingProviderMonitoring.getClass().getName(), true, ConfigDocProcessEnum.ProviderMonitoring, null, qualityAssurorUsers);
			}
			populateSignOffForProviderMonitoringAudit(trainingProviderMonitoring);
		}
		if (sdpUser != null) {
			HostingCompanyEmployeesService hceService = new HostingCompanyEmployeesService();
			List<Users> manager = hceService.findUserByJobTitle(HAJConstants.Manager_Quality_Assurance_ID);
			if (manager.size() != 0) {
				sendGenerationNotification(trainingProviderMonitoring, sdpUser, trainingProviderMonitoring.getInitiatingQA(), manager.get(0));
			} else {
				// fail safe
				sendGenerationNotification(trainingProviderMonitoring, sdpUser, trainingProviderMonitoring.getInitiatingQA(), null);
			}
		}
	}
	
	/*
	 * Populates sign off for audit monitoring
	 * Sign off users required:
	 * Main SDP, Quality Assurer, Manager QUALITY ASSURANCE
	 */
	public void populateSignOffForProviderMonitoringAudit(TrainingProviderMonitoring trainingProviderMonitoring) throws Exception {
		if (signoffService == null) {
			signoffService = new SignoffService();
		}
		if (trainingProviderApplicationService == null) {
			trainingProviderApplicationService = new TrainingProviderApplicationService();
		}
		UsersService usersService = new UsersService();
		
		// check if sign off assigned:
		List<Signoff> signOffList = signoffService.findByTargetKeyAndClass(trainingProviderMonitoring.getId(), trainingProviderMonitoring.getClass().getName());
		if (signOffList.size() == 0) {
			
			List<IDataEntity> createList = new ArrayList<>();
			
			Signoff signoffSdp = new Signoff(null, trainingProviderMonitoring.getId(), trainingProviderMonitoring.getClass().getName(), "COMPANY REPRESENTATIVE", null, null);
			signoffSdp.setExternalUserSignoff(true);
			createList.add(signoffSdp);
			
			Signoff signoffQa = new Signoff(usersService.findByKey(trainingProviderMonitoring.getInitiatingQA().getId()), trainingProviderMonitoring.getId(), trainingProviderMonitoring.getClass().getName(), "MERSETA: QUALITY ASSUROR", null, null);
			signoffQa.setExternalUserSignoff(false);
			createList.add(signoffQa);
			
			HostingCompanyEmployeesService hceService = new HostingCompanyEmployeesService();
			List<Users> manager = hceService.findUserByJobTitle(HAJConstants.Manager_Quality_Assurance_ID);
			Signoff signoffQaManager = new Signoff(usersService.findByKey(manager.get(0).getId()), trainingProviderMonitoring.getId(), trainingProviderMonitoring.getClass().getName(), "MERSETA: QUALITY ASSUROR MANAGER", null, null);
			signoffQaManager.setExternalUserSignoff(false);
			createList.add(signoffQaManager);
			
			if (createList.size() != 0) {
				dao.createBatch(createList);
			}
		
		}
	}

	private void sendGenerationNotification(TrainingProviderMonitoring trainingProviderMonitoring, Users user, Users qaUser, Users qaManager) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (trainingProviderApplicationService == null) {
			trainingProviderApplicationService = new TrainingProviderApplicationService();
		}
		String mssg = "";
		String sdpFullname = "";
		if (user.getTitle() != null && user.getTitle().getDescription() != null) {
			sdpFullname = user.getTitle().getDescription() + " ";
		}
		sdpFullname = sdpFullname + user.getFirstName() + " " +  user.getLastName();
		
		String managerName = "";
		if (qaManager != null) {
			managerName = qaManager.getFirstName() + " " +  qaManager.getLastName();
		} else {
			managerName = qaUser.getFirstName() + " " +  qaUser.getLastName();
		}
		
		TrainingProviderApplication tpa = null;
		if (trainingProviderMonitoring.getTrainingProviderApplication() != null && trainingProviderMonitoring.getTrainingProviderApplication().getId() != null) {
			tpa = trainingProviderApplicationService.findByKey(trainingProviderMonitoring.getTrainingProviderApplication().getId());
		}
		
		String subject = "";
		if (trainingProviderMonitoring.getAudit()) {
			subject= "PROVIDER AUDIT VISIT";
			mssg = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #SDP_FULL_NAME#, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The merSETA hereby advises that an audit visit has been scheduled to take place at the following location: #COMPANY_NAME# (#ACCREDITATION_NUMBER#) on #MONITORING_AUDIT_DATE#. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Should there be a change in the scheduled date, please contact the Regional Office before the visit to schedule a new date. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please do not hesitate to contact the Regional office for further assistance. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">#MANAGER_FULL_NAME#<br/> "
					+ "Manager: Quality Assurance"
					+ "</p>";
		} else {
			subject = "PROVIDER MONITORING VISIT";
			mssg = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #SDP_FULL_NAME#, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The merSETA hereby advises that a monitoring visit has been scheduled to take place at the following location: #COMPANY_NAME# (#ACCREDITATION_NUMBER#) on #MONITORING_AUDIT_DATE#. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Should there be a change in the scheduled date, please contact the Regional Office before the visit to schedule a new date. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please do not hesitate to contact the Regional office for further assistance. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">#MANAGER_FULL_NAME#<br/> "
					+ "Manager: Quality Assurance"
					+ "</p>";
		}

		mssg = mssg.replace("#SDP_FULL_NAME#", sdpFullname);
		mssg = mssg.replace("#COMPANY_NAME#", trainingProviderMonitoring.getCompany().getCompanyName());
		mssg = mssg.replace("#MANAGER_FULL_NAME#", managerName);
		mssg = mssg.replace("#MONITORING_AUDIT_DATE#", HAJConstants.sdfDDMMYYYY2.format(trainingProviderMonitoring.getMonitoringDate()));
		
		if (tpa != null && tpa.getId() != null) {
			if (tpa.getCertificateNumber() != null && !tpa.getCertificateNumber().isEmpty()) {
				mssg = mssg.replace("#ACCREDITATION_NUMBER#", tpa.getCertificateNumber());
			} else if (tpa.getAccreditationNumber() != null && !tpa.getAccreditationNumber().isEmpty()) {
				mssg = mssg.replace("#ACCREDITATION_NUMBER#", tpa.getAccreditationNumber());
			} else {
				mssg = mssg.replace("#ACCREDITATION_NUMBER#", " ");
			}
		} else {
			mssg = mssg.replace("#ACCREDITATION_NUMBER#", " ");
		}
		
		
		if (companyService == null) {
			companyService = new CompanyService();
		}
		Company company = companyService.findByKey(trainingProviderMonitoring.getCompany().getId());
		Address address = new Address();
		if (company.getResidentialAddress() != null && company.getResidentialAddress().getId() != null) {
			address = AddressService.instance().findByKey(company.getResidentialAddress().getId());
		}
		
		HostingCompanyEmployeesService hceService = new HostingCompanyEmployeesService();
		HostingCompanyEmployees hce = hceService.findByUserReturnHostCompany(trainingProviderMonitoring.getUser().getId());
		JobTitle jobTitle = JobTitleService.instance().findByKey(hce.getJobTitle().getId());
		
		if (signoffService == null) {
			signoffService = new SignoffService();
		}

		List<ReportSignOffBean> signoffBean = new ArrayList<>();
		List<Signoff> signOffList = signoffService.findByTargetKeyAndClass(trainingProviderMonitoring.getId(), trainingProviderMonitoring.getClass().getName());
		for (Signoff signoff : signOffList) {
			ReportSignOffBean entry = new ReportSignOffBean();
			if (signoff.getUser() != null) {
				entry.setSignOffFullName(signoff.getUser().getFirstName().trim() + " " + signoff.getUser().getLastName().trim());
			} else {
				entry.setSignOffFullName("");
			}
			
			if (signoff.getSignOffTitle() != null) {
				entry.setSignOffType(signoff.getSignOffTitle().trim());
			} else {
				entry.setSignOffType("");
			}
			
			if (signoff.getSignOffDate() != null) {
				entry.setDateSignedOff(HAJConstants.sdfDDMMYYYYHHmm.format(signoff.getSignOffDate()));
			} else {
				entry.setDateSignedOff("Awaiting Sign Off");
			}
			
			if (signoff.getAccept() != null) {
				entry.setSignedOff(signoff.getAccept());
			} else {
				entry.setSignedOff(false);
			}
			signoffBean.add(entry);
		}
		
		// Version Two
		byte[] bitesEtqTp049B = populateMonitorAuditReport(trainingProviderMonitoring, sdpFullname, managerName, company, address, jobTitle);
		GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bitesEtqTp049B, "ETQ-TP-049B-ProviderMonitorReport.pdf", "pdf", null);
		
		// version one
//		AuditorMonitorReviewService auditorMonitorReviewService = new AuditorMonitorReviewService();
//		Map<String, Object> params = new HashMap<String, Object>();
//		JasperService js=new JasperService();
//		String fileNameID="";
//		TrainingProviderApplicationService trainingProviderApplicationService=new TrainingProviderApplicationService();
//		TrainingProviderApplication trainingProviderApplication=trainingProviderApplicationService.findByKey(1L);
//		params.put("applicationType", trainingProviderApplication.getAccreditationApplicationTypeEnum().getFriendlyName());
//		params.put("TrainingProviderApplication", trainingProviderApplication);
//		
//		List<AuditorMonitorReview> auditorMonitorReviewList = auditorMonitorReviewService.findByTargetKeyAndClassNoDoc(trainingProviderMonitoring.getClass().getName(), trainingProviderMonitoring.getId());
//		params.put("AuditorMonitorReviewdataSource", new JRBeanCollectionDataSource(auditorMonitorReviewList));
//		
//		JasperService.addFormTemplateParams(params);
//		params.put("footerDocTitle", "Self Evaluation Form".toUpperCase());
//		params.put("footerDocNum", "");
//		params.put("title", "Self Evaluation Form".toUpperCase());
//	    byte[] bites = JasperService.instance().convertJasperReportToByte("TrainingProviderSelfEvaluationForm.jasper", params);
//		GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "ETQ-TP-049B-ProviderMonitorReport.pdf", "pdf", null);	
	}

	/**
	 * Populate and return report ETQ-TP-049B-ProviderMonitorReport (MONITOR_AND_AUDIT_REPORT.jasper)
	 * 
	 * @param trainingProviderMonitoring the application
	 * @param sdf the simple date format
	 * @param sdpFullname the full name of the SDP
	 * @param managerName the full name of the QA manager
	 * @param company the comapny linked to the application
	 * @param address the address object of the company
	 * @param jobTitle the job title of the QA processing the application
	 * @return byte[] the report
	 * @throws Exception
	 */
	public byte[] populateMonitorAuditReport(TrainingProviderMonitoring trainingProviderMonitoring, String sdpFullname, String managerName, Company company, Address address, JobTitle jobTitle) throws Exception {
		SimpleDateFormat sdfReport = new SimpleDateFormat("dd MMMM yyyy HH:mm");
		AuditorMonitorReviewService auditorMonitorReviewService = new AuditorMonitorReviewService();
		if (trainingProviderApplicationService == null) {
			trainingProviderApplicationService = new TrainingProviderApplicationService();
		}
		
		TrainingProviderApplication tpa = null;
		if (trainingProviderMonitoring.getTrainingProviderApplication() != null && trainingProviderMonitoring.getTrainingProviderApplication().getId() != null) {
			tpa = trainingProviderApplicationService.findByKey(trainingProviderMonitoring.getTrainingProviderApplication().getId());
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		List<ReportSignOffBean> signoffBean = new ArrayList<>();
		List<Signoff> signOffList = signoffService.findByTargetKeyAndClass(trainingProviderMonitoring.getId(), trainingProviderMonitoring.getClass().getName());
		for (Signoff signoff : signOffList) {
			ReportSignOffBean entry = new ReportSignOffBean();
			if (signoff.getUser() != null) {
				entry.setSignOffFullName(signoff.getUser().getFirstName().trim() + " " + signoff.getUser().getLastName().trim());
			} else {
				entry.setSignOffFullName("Awaiting Assingment");
			}
			
			if (signoff.getSignOffTitle() != null) {
				entry.setSignOffType(signoff.getSignOffTitle().trim());
			} else {
				entry.setSignOffType("");
			}
			
			if (signoff.getSignOffDate() != null) {
				entry.setDateSignedOff(HAJConstants.sdfDDMMYYYYHHmm.format(signoff.getSignOffDate()));
			} else {
				entry.setDateSignedOff("Awaiting Sign Off");
			}
			
			if (signoff.getAccept() != null) {
				entry.setSignedOff(signoff.getAccept());
			} else {
				entry.setSignedOff(false);
			}
			signoffBean.add(entry);
		}
		if (trainingProviderMonitoring.getAudit() != null && trainingProviderMonitoring.getAudit()) {
			params.put("showsignoff", true);
		} else {
			params.put("showsignoff", true);
		}
		params.put("signoffdatasource", new JRBeanCollectionDataSource(signoffBean));
		params.put("address", address);
		params.put("company", company);
		
		if (tpa != null && tpa.getId() != null) {
			if (tpa.getCertificateNumber() != null && !tpa.getCertificateNumber().isEmpty()) {
				params.put("accreditation_number", tpa.getCertificateNumber());
			} else if (tpa.getAccreditationNumber() != null && !tpa.getAccreditationNumber().isEmpty()) {
				params.put("accreditation_number", tpa.getAccreditationNumber());
			} else {
				params.put("accreditation_number", " ");
			}
		} else {
			params.put("accreditation_number", " ");
		}
//		params.put("accreditation_number", ((tpa != null && tpa.getId() != null && tpa.getAccreditationNumber() != null) ?  tpa.getAccreditationNumber() : " "));
		params.put("site_visit_date", sdfReport.format(trainingProviderMonitoring.getMonitoringDate()));
		params.put("qa_comments",  ((trainingProviderMonitoring.getQaComments() == null) ? "N/A" : trainingProviderMonitoring.getQaComments()) );
		params.put("qa_recommendation", ((trainingProviderMonitoring.getQaRecommendation() == null) ? "N/A" : trainingProviderMonitoring.getQaRecommendation()));
		params.put("qa_name", trainingProviderMonitoring.getUser().getFirstName() + " " + trainingProviderMonitoring.getUser().getLastName());
		if (jobTitle.getRegion() != null) {
			params.put("region", jobTitle.getRegion().getDescription());
		} else {
			params.put("region", "N/A");
		}
		params.put("qa_date", ((trainingProviderMonitoring.getQaDateSubmission() == null) ? "AWAITING SIGN OFF" : sdfReport.format(trainingProviderMonitoring.getQaDateSubmission())));
		params.put("cp_one_name", sdpFullname);
		params.put("cp_one_date",  ((trainingProviderMonitoring.getSpdUserDateSubmission() == null) ? "AWAITING SIGN OFF" : sdfReport.format(trainingProviderMonitoring.getSpdUserDateSubmission())));
		params.put("cp_two_name", "SDP-POC");
		params.put("cp_two_date", ((trainingProviderMonitoring.getSpdUserDateSubmission() == null) ? "AWAITING SIGN OFF" : sdfReport.format(trainingProviderMonitoring.getSpdUserDateSubmission())));
		params.put("manager_qa_name", managerName);
		params.put("manager_qa_date", ((trainingProviderMonitoring.getApprovalDate() == null) ? "AWAITING SIGN OFF" : sdfReport.format(trainingProviderMonitoring.getApprovalDate())));
		params.put("status", trainingProviderMonitoring.getStatus().getFriendlyName());
		TrainingProviderApplicationService trainingProviderApplicationService=new TrainingProviderApplicationService();
		TrainingProviderApplication trainingProviderApplication=trainingProviderApplicationService.findByKey(1L);
		params.put("applicationType", trainingProviderApplication.getAccreditationApplicationTypeEnum().getFriendlyName());
		params.put("TrainingProviderApplication", trainingProviderApplication);
		List<AuditorMonitorReview> auditorMonitorReviewList = auditorMonitorReviewService.findByTargetKeyAndClassNoDoc(trainingProviderMonitoring.getClass().getName(), trainingProviderMonitoring.getId());
		params.put("AuditorMonitorReviewdataSource", new JRBeanCollectionDataSource(auditorMonitorReviewList));
		
		JasperService.addFormTemplateParams(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		params.put("footerDocTitle", "Self Evaluation Form".toUpperCase());
		params.put("footerDocNum", "");
		params.put("title", "Self Evaluation Form".toUpperCase());
	    byte[] bites = JasperService.instance().convertJasperReportToByte("MONITOR_AND_AUDIT_REPORT.jasper", params);
		return bites;
	}
	
	/*
	 * Downloads Monitoring_Audit_report
	 */
	public void downloadMonitorAuditReport(TrainingProviderMonitoring trainingProviderMonitoring) throws Exception {
		SimpleDateFormat sdfReport = new SimpleDateFormat("dd MMMM yyyy HH:mm");
		AuditorMonitorReviewService auditorMonitorReviewService = new AuditorMonitorReviewService();
		if (trainingProviderApplicationService == null) {
			trainingProviderApplicationService = new TrainingProviderApplicationService();
		}
	    if (companyService == null) {
			companyService = new CompanyService();
		}
	    if (signoffService == null) {
	    	signoffService = new SignoffService();
		}
		
		// locates SDP user
		Users sdpUser = trainingProviderApplicationService.findUsersByCompanyAndReturnUser(trainingProviderMonitoring.getCompany());
		
		// Locates QA manager
		HostingCompanyEmployeesService hceService = new HostingCompanyEmployeesService();
		List<Users> managers = hceService.findUserByJobTitle(HAJConstants.Manager_Quality_Assurance_ID);
		Users managerUser = null;
		if (managers.size() != 0) {
			UsersService usersService = new UsersService();
			managerUser = usersService.findByKey(managers.get(0).getId());
		}
		
	 
	    
		Company company = companyService.findByKey(trainingProviderMonitoring.getCompany().getId());
		Address address = new Address();
		if (company.getResidentialAddress() != null && company.getResidentialAddress().getId() != null) {
			address = AddressService.instance().findByKey(company.getResidentialAddress().getId());
		}
		
		HostingCompanyEmployees hce = hceService.findByUserReturnHostCompany(trainingProviderMonitoring.getUser().getId());
		JobTitle jobTitle = JobTitleService.instance().findByKey(hce.getJobTitle().getId());
		
		String sdpFullname = "";
		if (sdpUser.getTitle() != null && sdpUser.getTitle().getDescription() != null) {
			sdpFullname = sdpUser.getTitle().getDescription() + " ";
		}
		sdpFullname = sdpFullname + sdpUser.getFirstName() + " " +  sdpUser.getLastName();
		
		String managerName = "";
		if (managerUser != null) {
			managerName = managerUser.getFirstName() + " " +  managerUser.getLastName();
		} else {
			managerName = "";
		}

		TrainingProviderApplication tpa = null;
		if (trainingProviderMonitoring.getTrainingProviderApplication() != null && trainingProviderMonitoring.getTrainingProviderApplication().getId() != null) {
			tpa = trainingProviderApplicationService.findByKey(trainingProviderMonitoring.getTrainingProviderApplication().getId());
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		List<ReportSignOffBean> signoffBean = new ArrayList<>();
		List<Signoff> signOffList = signoffService.findByTargetKeyAndClass(trainingProviderMonitoring.getId(), trainingProviderMonitoring.getClass().getName());
		for (Signoff signoff : signOffList) {
			ReportSignOffBean entry = new ReportSignOffBean();
			if (signoff.getUser() != null) {
				entry.setSignOffFullName(signoff.getUser().getFirstName().trim() + " " + signoff.getUser().getLastName().trim());
			} else {
				entry.setSignOffFullName("Awaiting Assingment");
			}
			
			if (signoff.getSignOffTitle() != null) {
				entry.setSignOffType(signoff.getSignOffTitle().trim());
			} else {
				entry.setSignOffType("");
			}
			
			if (signoff.getSignOffDate() != null) {
				entry.setDateSignedOff(HAJConstants.sdfDDMMYYYYHHmm.format(signoff.getSignOffDate()));
			} else {
				entry.setDateSignedOff("Awaiting Sign Off");
			}
			
			if (signoff.getAccept() != null) {
				entry.setSignedOff(signoff.getAccept());
			} else {
				entry.setSignedOff(false);
			}
			signoffBean.add(entry);
		}
		if (trainingProviderMonitoring.getAudit() != null && trainingProviderMonitoring.getAudit()) {
			params.put("showsignoff", true);
		} else {
			params.put("showsignoff", true);
		}
		params.put("signoffdatasource", new JRBeanCollectionDataSource(signoffBean));
		params.put("address", address);
		params.put("company", company);
		
		if (tpa != null && tpa.getId() != null) {
			if (tpa.getCertificateNumber() != null && !tpa.getCertificateNumber().isEmpty()) {
				params.put("accreditation_number", tpa.getCertificateNumber());
			} else if (tpa.getAccreditationNumber() != null && !tpa.getAccreditationNumber().isEmpty()) {
				params.put("accreditation_number", tpa.getAccreditationNumber());
			} else {
				params.put("accreditation_number", " ");
			}
		} else {
			params.put("accreditation_number", " ");
		}
		
//		params.put("accreditation_number", ((tpa != null && tpa.getId() != null && tpa.getAccreditationNumber() != null) ? tpa.getAccreditationNumber() : " "));
		params.put("site_visit_date", sdfReport.format(trainingProviderMonitoring.getMonitoringDate()));
		params.put("qa_comments",  ((trainingProviderMonitoring.getQaComments() == null) ? "N/A" : trainingProviderMonitoring.getQaComments()) );
		params.put("qa_recommendation", ((trainingProviderMonitoring.getQaRecommendation() == null) ? "N/A" : trainingProviderMonitoring.getQaRecommendation()));
		params.put("qa_name", trainingProviderMonitoring.getUser().getFirstName() + " " + trainingProviderMonitoring.getUser().getLastName());
		if (jobTitle.getRegion() != null) {
			params.put("region", jobTitle.getRegion().getDescription());
		} else {
			params.put("region", "N/A");
		}
		params.put("qa_date", ((trainingProviderMonitoring.getQaDateSubmission() == null) ? "AWAITING SIGN OFF" : sdfReport.format(trainingProviderMonitoring.getQaDateSubmission())));
		params.put("cp_one_name", sdpFullname);
		params.put("cp_one_date",  ((trainingProviderMonitoring.getSpdUserDateSubmission() == null) ? "AWAITING SIGN OFF" : sdfReport.format(trainingProviderMonitoring.getSpdUserDateSubmission())));
		params.put("cp_two_name", "SDP-POC");
		params.put("cp_two_date", ((trainingProviderMonitoring.getSpdUserDateSubmission() == null) ? "AWAITING SIGN OFF" : sdfReport.format(trainingProviderMonitoring.getSpdUserDateSubmission())));
		params.put("manager_qa_name", managerName);
		params.put("manager_qa_date", ((trainingProviderMonitoring.getApprovalDate() == null) ? "AWAITING SIGN OFF" : sdfReport.format(trainingProviderMonitoring.getApprovalDate())));
		params.put("status", trainingProviderMonitoring.getStatus().getFriendlyName());
		TrainingProviderApplicationService trainingProviderApplicationService=new TrainingProviderApplicationService();
		TrainingProviderApplication trainingProviderApplication=trainingProviderApplicationService.findByKey(1L);
		params.put("applicationType", trainingProviderApplication.getAccreditationApplicationTypeEnum().getFriendlyName());
		params.put("TrainingProviderApplication", trainingProviderApplication);
		List<AuditorMonitorReview> auditorMonitorReviewList = auditorMonitorReviewService.findByTargetKeyAndClassNoDoc(trainingProviderMonitoring.getClass().getName(), trainingProviderMonitoring.getId());
		params.put("AuditorMonitorReviewdataSource", new JRBeanCollectionDataSource(auditorMonitorReviewList));
		JasperService.addFormTemplateParams(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		params.put("footerDocTitle", "Self Evaluation Form".toUpperCase());
		params.put("footerDocNum", "");
		params.put("title", "Self Evaluation Form".toUpperCase());
		JasperService.instance().createReportFromDBtoServletOutputStream("MONITOR_AND_AUDIT_REPORT.jasper", params,"MONITOR_AND_AUDIT_REPORT.pdf");
	}

	public void completeCompanyLearners(TrainingProviderMonitoring trainingProviderMonitoring, Users user, Tasks tasks, List<AuditorMonitorReview> auditorMonitorReviews) throws Exception {
		String error = "";
		for (AuditorMonitorReview auditorMonitorReview : auditorMonitorReviews) {
			if (auditorMonitorReview.getEvidenceRequired() == YesNoEnum.No && auditorMonitorReview.getDocs().size() == 0) {
				error += "Documents Outstanding, Unable To Proceed";
				break;
			}
		}
		if (trainingProviderMonitoring.getDocs() != null) {
			for (Doc doc : trainingProviderMonitoring.getDocs()) {
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
		for (Doc doc : trainingProviderMonitoring.getDocs()) {
			doc.setTargetKey(trainingProviderMonitoring.getId());
			doc.setTargetClass(TrainingProviderMonitoring.class.getName());
			if (doc.getId() == null) DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), user);
		}
		trainingProviderMonitoring.setSpdUser(user);
		trainingProviderMonitoring.setSpdUserDateSubmission(getSynchronizedDate());
		update(trainingProviderMonitoring);
		ProcessRolesService processRolesService = new ProcessRolesService();
		ProcessRoles pr = processRolesService.findByKey(tasks.getProcessRole().getId());
		List<Users> usersByRegion = new ArrayList<>();
		if (pr != null && pr.getNextTaskRole() != null) {
			HostingCompanyEmployeesService hces = new HostingCompanyEmployeesService();
			TownService townService = new TownService();
			Address address = null;
			if (trainingProviderMonitoring != null && trainingProviderMonitoring.getCompany() != null && trainingProviderMonitoring.getCompany().getResidentialAddress().getId() != null) {
				address = AddressService.instance().findByKey(trainingProviderMonitoring.getCompany().getResidentialAddress().getId());
			}
			if (address != null && address.getTown() != null) {
				usersByRegion = hces.locateHostingCompanyEmployeesByRegionAndRole(townService.findByKey(address.getTown().getId()), RolesService.instance().findByKey(pr.getNextTaskRole().getId()));
			}
		}		
		if (usersByRegion.size() == 0) {
			usersByRegion = new ArrayList<>();
			usersByRegion.add(trainingProviderMonitoring.getInitiatingQA());
			TasksService.instance().findNextInProcessAndCreateTask("", user, trainingProviderMonitoring.getId(), trainingProviderMonitoring.getClass().getName(), true, tasks, null, usersByRegion);
		} else {
			TasksService.instance().findNextInProcessAndCreateTask("", user, trainingProviderMonitoring.getId(), trainingProviderMonitoring.getClass().getName(), true, tasks, null, usersByRegion);
		}
		RejectReasonMultipleSelectionService.instance().clearEntries(trainingProviderMonitoring.getId(), trainingProviderMonitoring.getClass().getName(), ConfigDocProcessEnum.ProviderMonitoring);
		//TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}
	
	@SuppressWarnings("unchecked")
	public void updateEditAndValidiate(TrainingProviderMonitoring trainingProviderMonitoring, Users user, List<AuditorMonitorReview> auditorMonitorReviews, boolean validate) throws Exception{
		update(trainingProviderMonitoring);
		if (auditorMonitorReviews != null) this.dao.updateBatch((List<IDataEntity>) (List<?>) auditorMonitorReviews);
		if (validate) {
			for (AuditorMonitorReview auditorMonitorReview : auditorMonitorReviews) {
				if (auditorMonitorReview.getEvidenceRequired() == YesNoEnum.No && auditorMonitorReview.getDocs().size() == 0) {
					throw new Exception("Documents Outstanding, Unable To Proceed");
				}
			}
		}
	}
	
	public void clearSignoffs(List<Signoff> signOffList) throws Exception{
		List<IDataEntity> updateList = new ArrayList<>();
		for (Signoff signoff : signOffList) {
			signoff.setAccept(null);
			signoff.setSignOffDate(null);
			updateList.add(signoff);
		}
		if (updateList.size() != 0) {
			dao.updateBatch(updateList);
		}
	}

	@SuppressWarnings("unchecked")
	public void completeCompanyLearnersNoDoc(TrainingProviderMonitoring trainingProviderMonitoring, Users user, Tasks tasks, List<AuditorMonitorReview> auditorMonitorReviews) throws Exception {
		dao.update(trainingProviderMonitoring);
		ProcessRolesService processRolesService = new ProcessRolesService();
		ProcessRoles pr = processRolesService.findByKey(tasks.getProcessRole().getId());
		List<Users> usersByRegion = new ArrayList<>();
		if (pr != null && pr.getNextTaskRole() != null) {
			HostingCompanyEmployeesService hces = new HostingCompanyEmployeesService();
			TownService townService = new TownService();
			Address address = null;
			if (trainingProviderMonitoring != null && trainingProviderMonitoring.getCompany() != null && trainingProviderMonitoring.getCompany().getResidentialAddress().getId() != null) {
				address = AddressService.instance().findByKey(trainingProviderMonitoring.getCompany().getResidentialAddress().getId());
			}
			if (address != null && address.getTown() != null) {
				usersByRegion = hces.locateHostingCompanyEmployeesByRegionAndRole(townService.findByKey(address.getTown().getId()), RolesService.instance().findByKey(pr.getNextTaskRole().getId()));
			}
		}
		
		if (auditorMonitorReviews != null) this.dao.updateBatch((List<IDataEntity>) (List<?>) auditorMonitorReviews);
		
		if (usersByRegion.size() == 0) {
			usersByRegion = new ArrayList<>();
			usersByRegion.add(trainingProviderMonitoring.getInitiatingQA());
			TasksService.instance().findNextInProcessAndCreateTask("", user, trainingProviderMonitoring.getId(), trainingProviderMonitoring.getClass().getName(), true, tasks, null, null);
		} else {
			TasksService.instance().findNextInProcessAndCreateTask("", user, trainingProviderMonitoring.getId(), trainingProviderMonitoring.getClass().getName(), true, tasks, null, usersByRegion);
		}
		
		RejectReasonMultipleSelectionService.instance().clearEntries(trainingProviderMonitoring.getId(), trainingProviderMonitoring.getClass().getName(), ConfigDocProcessEnum.ProviderMonitoring);
		
		if (tasks.getFirstInProcess() == true) {
			// Validation if evidence is required for one item: send notification
			boolean evidanceRequired = false;
			if (auditorMonitorReviewService.countByTargetKeyAndClassWhereEvidanceIsRequired(trainingProviderMonitoring.getClass().getName(), trainingProviderMonitoring.getId()) != 0) {
				evidanceRequired = true;
			}
			if (trainingProviderApplicationService == null) {
				trainingProviderApplicationService = new TrainingProviderApplicationService();
			}
			Users sdpUser = trainingProviderApplicationService.findUsersByCompanyAndReturnUser(trainingProviderMonitoring.getCompany());
			HostingCompanyEmployeesService hceService = new HostingCompanyEmployeesService();
			List<Users> manager = hceService.findUserByJobTitle(HAJConstants.Manager_Quality_Assurance_ID);
			Users Manager = null;
			if (manager.size() != 0) {
				UsersService usersService = new UsersService();
				Manager = usersService.findByKey(manager.get(0).getId());
			}
			Users signoffUser = null;
			if (trainingProviderMonitoring.getSpdUser() != null && trainingProviderMonitoring.getSpdUser().getId() != null) {
				if (!trainingProviderMonitoring.getSpdUser().getId().equals(sdpUser.getId())) {
					if (usersService == null) {
						usersService = new UsersService();
					}
					signoffUser = usersService.findByKey(trainingProviderMonitoring.getSpdUser().getId());
				}
			}
			sendOutcomeAuditMonitoringVisit(trainingProviderMonitoring, sdpUser, signoffUser, user, Manager, evidanceRequired);
		}
	}
	
	private void sendOutcomeAuditMonitoringVisit(TrainingProviderMonitoring trainingProviderMonitoring, Users user, Users signoffUser, Users qaUser, Users qaManager, boolean evidanceRequired) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (trainingProviderApplicationService == null) {
			trainingProviderApplicationService = new TrainingProviderApplicationService();
		}
		TrainingProviderApplication tpa = null;
		if (trainingProviderMonitoring.getTrainingProviderApplication() != null && trainingProviderMonitoring.getTrainingProviderApplication().getId() != null) {
			tpa = trainingProviderApplicationService.findByKey(trainingProviderMonitoring.getTrainingProviderApplication().getId());
		}
		
		String sdpFullname = "";
		String managerName = "";
		String mssg = "";
		
		if (user.getTitle() != null && user.getTitle().getDescription() != null) {
			sdpFullname = user.getTitle().getDescription() + " ";
		}
		sdpFullname = sdpFullname + user.getFirstName() + " " +  user.getLastName();
		
		if (qaManager != null) {
			managerName = qaManager.getFirstName() + " " +  qaManager.getLastName();
		} else {
			managerName = qaUser.getFirstName() + " " +  qaUser.getLastName();
		}
		
		if (companyService == null) {
			companyService = new CompanyService();
		}
		Company company = companyService.findByKey(trainingProviderMonitoring.getCompany().getId());
		
		// add training provider application here 
		
		if (signoffService == null) {
			signoffService = new SignoffService();
		}
		List<ReportSignOffBean> signoffBean = new ArrayList<>();
		List<Signoff> signOffList = signoffService.findByTargetKeyAndClass(trainingProviderMonitoring.getId(), trainingProviderMonitoring.getClass().getName());
		for (Signoff signoff : signOffList) {
			ReportSignOffBean entry = new ReportSignOffBean();
			if (signoff.getUser() != null) {
				entry.setSignOffFullName(signoff.getUser().getFirstName().trim() + " " + signoff.getUser().getLastName().trim());
			} else {
				entry.setSignOffFullName("");
			}
			
			if (signoff.getSignOffTitle() != null) {
				entry.setSignOffType(signoff.getSignOffTitle().trim());
			} else {
				entry.setSignOffType("");
			}
			
			if (signoff.getSignOffDate() != null) {
				entry.setDateSignedOff(HAJConstants.sdfDDMMYYYYHHmm.format(signoff.getSignOffDate()));
			} else {
				entry.setDateSignedOff("Awaiting Sign Off");
			}
			
			if (signoff.getAccept() != null) {
				entry.setSignedOff(signoff.getAccept());
			} else {
				entry.setSignedOff(false);
			}
			signoffBean.add(entry);
		}
		
		String subject = "";
		String jasperReport = "";
		// CORRECT INFO
		if (trainingProviderMonitoring.getAudit()) {
			jasperReport = "ETQ-TP-052-OutcomeAuditSiteVisit";
			subject = "PROVIDER AUDIT VISIT OUTCOME";
			if (evidanceRequired) {
				mssg = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #FULL_NAME#, </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">OUTCOME OF THE AUDIT SITE VISIT: #COMPANY_NAME# (#ACCREDITATION_NUMBER#) </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">An audit site visit was conducted on #DATE#. A copy of the audit site visit report is attached outlining the non-conformances raised. </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The Quality Assuror will contact #COMPANY_NAME# in due course to arrange a suitable date and time for a follow-up site visit to agree on possible preventive and corrective actions to address the non-conformances.</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please do not hesitate to contact the merSETA Regional Office for any further assistance or clarification.</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely, </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">#MANAGER_FULL_NAME#<br/> "
						+ "Manager: Quality Assurance"
						+ "</p>";
			} else {
				mssg = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #FULL_NAME#, </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">OUTCOME OF THE AUDIT SITE VISIT: #COMPANY_NAME# (#ACCREDITATION_NUMBER#) </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">An audit site visit was conducted on #DATE#. A copy of the audit site visit report is attached for your information. </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Kindly note that you are required to sign off the report on the NSDMS.</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please do not hesitate to contact the merSETA Regional Office for any further assistance or clarification.</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely, </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">#MANAGER_FULL_NAME#<br/> "
						+ "Manager: Quality Assurance"
						+ "</p>";
			}
		} else {
			jasperReport = "ETQ-TP-050-OutcomeMonitoringSite";
			subject = "PROVIDER MONITORING VISIT OUTCOME";
			if (evidanceRequired) {
				mssg = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #FULL_NAME#, </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">OUTCOME OF THE MONITORING SITE VISIT: #COMPANY_NAME# (#ACCREDITATION_NUMBER#) </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">A monitoring site visit was conducted on #DATE#. A copy of the monitoring site visit report is attached for your information. </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">During the site visit the Quality Assuror identified areas where #COMPANY_NAME# must improve. "
						+ "A Quality Assuror will provide developmental support to #COMPANY_NAME# in order to rectify the areas as outlined in the Monitoring Report. "
						+ "The Quality Assuror will contact #COMPANY_NAME# in due course to set a suitable date and time for a developmental support site visit.</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please do not hesitate to contact the merSETA Regional Office for any further assistance or clarification.</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely, </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">#MANAGER_FULL_NAME#<br/> "
						+ "Manager: Quality Assurance"
						+ "</p>";
			} else {
				mssg = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #FULL_NAME#, </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">OUTCOME OF THE MONITORING SITE VISIT: #COMPANY_NAME# (#ACCREDITATION_NUMBER#) </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">A monitoring site visit was conducted on #DATE#. A copy of the monitoring site visit report is attached for your information. </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Kindly note that you are required to sign off the report on the NSDMS.</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please do not hesitate to contact the merSETA Regional Office for any further assistance or clarification.</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely, </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">#MANAGER_FULL_NAME#<br/> "
						+ "Manager: Quality Assurance"
						+ "</p>";
			}	
		}
		// replace information for messages
		mssg = mssg.replace("#COMPANY_NAME#", company.getCompanyName());
//		mssg = mssg.replace("#COMPANY_LEVY_NUMBER#", company.getCompanyName());
		mssg = mssg.replace("#DATE#", sdf.format(trainingProviderMonitoring.getMonitoringDate()));
		mssg = mssg.replace("#MANAGER_FULL_NAME#", managerName.trim());
		
		if (tpa != null && tpa.getId() != null) {
			if (tpa.getCertificateNumber() != null && !tpa.getCertificateNumber().isEmpty()) {
				mssg = mssg.replace("#ACCREDITATION_NUMBER#", tpa.getCertificateNumber());
			} else if (tpa.getAccreditationNumber() != null && !tpa.getAccreditationNumber().isEmpty()) {
				mssg = mssg.replace("#ACCREDITATION_NUMBER#", tpa.getAccreditationNumber());
			} else {
				mssg = mssg.replace("#ACCREDITATION_NUMBER#", " ");
			}
		} else {
			mssg = mssg.replace("#ACCREDITATION_NUMBER#", " ");
		}
//		mssg = mssg.replace("#ACCREDITATION_NUMBER#", ((tpa != null && tpa.getId() != null && tpa.getAccreditationNumber() != null) ? tpa.getAccreditationNumber() : " " ));

		
		List<AttachmentBean> filesToSend = new ArrayList<>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("address", company.getResidentialAddress());
		params.put("user", user);
		params.put("company", company);
		params.put("site_visit_date", sdf.format(trainingProviderMonitoring.getMonitoringDate()));
		params.put("regional_manager", managerName);
		if (user.getTitle() != null) {
			params.put("title", user.getTitle().getDescription());
		}else {
			params.put("title", "");
		}
		JasperService.addFormTemplateParams(params);
	    byte[] bites = JasperService.instance().convertJasperReportToByte(jasperReport+".jasper", params);
	    filesToSend.add(new AttachmentBean(jasperReport+".pdf", bites, "pdf"));
	    
	    /* Self Reg form Version one*/
//	    AuditorMonitorReviewService auditorMonitorReviewService = new AuditorMonitorReviewService();
//		params = new HashMap<String, Object>();
//		TrainingProviderApplicationService trainingProviderApplicationService=new TrainingProviderApplicationService();
//		TrainingProviderApplication trainingProviderApplication=trainingProviderApplicationService.findByKey(1L);
//		params.put("applicationType", trainingProviderApplication.getAccreditationApplicationTypeEnum().getFriendlyName());
//		params.put("TrainingProviderApplication", trainingProviderApplication);
//		List<AuditorMonitorReview> auditorMonitorReviewList = auditorMonitorReviewService.findByTargetKeyAndClassNoDoc(trainingProviderMonitoring.getClass().getName(), trainingProviderMonitoring.getId());
//		params.put("AuditorMonitorReviewdataSource", new JRBeanCollectionDataSource(auditorMonitorReviewList));
//		JasperService.addFormTemplateParams(params);
//		params.put("footerDocTitle", "Self Evaluation Form".toUpperCase());
//		params.put("footerDocNum", "");
//		params.put("title", "Self Evaluation Form".toUpperCase());		
//	    byte[] bitesTwo = JasperService.instance().convertJasperReportToByte("TrainingProviderSelfEvaluationForm.jasper", params);
//	    filesToSend.add(new AttachmentBean("ETQ-TP-049B-ProviderMonitorReport.pdf", bitesTwo, "pdf"));
	    
	    /* Self Reg form Version Two*/
	    if (companyService == null) {
			companyService = new CompanyService();
		}
	    
		company = companyService.findByKey(trainingProviderMonitoring.getCompany().getId());
		Address address = new Address();
		if (company.getResidentialAddress() != null && company.getResidentialAddress().getId() != null) {
			address = AddressService.instance().findByKey(company.getResidentialAddress().getId());
		}
		
		HostingCompanyEmployeesService hceService = new HostingCompanyEmployeesService();
		HostingCompanyEmployees hce = hceService.findByUserReturnHostCompany(trainingProviderMonitoring.getUser().getId());
		JobTitle jobTitle = JobTitleService.instance().findByKey(hce.getJobTitle().getId());
		
		// Version Two
		byte[] bitesEtqTp049B = populateMonitorAuditReport(trainingProviderMonitoring, sdpFullname, managerName, company, address, jobTitle);
		filesToSend.add(new AttachmentBean("ETQ-TP-049B-ProviderMonitorAuditReport.pdf", bitesEtqTp049B, "pdf"));
		
		List<Users> usersToNotify = new ArrayList<>();
		List<Signoff> signOffListTwo = signoffService.findByTargetKeyAndClass(trainingProviderMonitoring.getId(), trainingProviderMonitoring.getClass().getName());
		// adds sign off users to notification
		for (Signoff signoff : signOffListTwo) {
			if (signoff.getUser() != null) {
				usersToNotify.add(signoff.getUser());
			}
		}
		
//	    // SEND TO SDP
//	    if (user != null) {
//	    	usersToNotify.add(user);
//		}
	    
	    // check if Main SDP user is on notification list
	    if (user != null) {
	    	boolean addMainSdp = true;
		    for (Users userEntry : usersToNotify) {
		    	if (userEntry.getId().equals(user.getId())) {
		    		addMainSdp = false;
		    		break;
				}
			}
		    if (addMainSdp) {
				usersToNotify.add(user);
			}
	    }
	   
//	    List<Users> usersToNotify = new ArrayList<>();
//	    // SEND TO SDP
//	    if (user != null) {
//	    	usersToNotify.add(user);
//		}
//	    // sign off user
//	    if (signoffUser != null) {
//	    	usersToNotify.add(signoffUser);
//		}
//		// SEND TO QA
//	    if (qaUser != null) {
//	    	usersToNotify.add(qaUser);
//		}
//		// SEND TO QA MANAGER
//	    if (qaManager != null) {
//	    	usersToNotify.add(qaManager);
//		}
	    for (Users u : usersToNotify) {
	    	String fullName = "";
			if (u.getTitle() != null && u.getTitle().getDescription() != null) {
				fullName = u.getTitle().getDescription().trim() + " ";
			}
			fullName += u.getFirstName().trim() + " " +  u.getLastName().trim();
	    	GenericUtility.sendMailWithAttachementTempWithLog(u.getEmail(), subject, mssg.replace("#FULL_NAME#", fullName.trim()), filesToSend, null);
		}
	}

	public void approveCompanyLearners(TrainingProviderMonitoring  trainingProviderMonitoring, Users user, Tasks tasks) throws Exception {
		trainingProviderMonitoring.setStatus(ApprovalEnum.WaitingForManager);
		trainingProviderMonitoring.setQaDateSubmission(getSynchronizedDate());
		dao.update( trainingProviderMonitoring);
		ProcessRolesService processRolesService = new ProcessRolesService();
		ProcessRoles pr = processRolesService.findByKey(tasks.getProcessRole().getId());
		List<Users> usersByRegion = new ArrayList<>();
		if (pr != null && pr.getNextTaskRole() != null) {
			HostingCompanyEmployeesService hces = new HostingCompanyEmployeesService();
			TownService townService = new TownService();
			Address address = null;
			if ( trainingProviderMonitoring != null &&  trainingProviderMonitoring.getCompany() != null &&  trainingProviderMonitoring.getCompany().getResidentialAddress().getId() != null) {
				address = AddressService.instance().findByKey( trainingProviderMonitoring.getCompany().getResidentialAddress().getId());
			}
			if (address != null && address.getTown() != null) {
				usersByRegion = hces.locateHostingCompanyEmployeesByRegionAndRole(townService.findByKey(address.getTown().getId()), RolesService.instance().findByKey(pr.getNextTaskRole().getId()));
			}
		}
		if (usersByRegion.size() == 0) {
			TasksService.instance().findNextInProcessAndCreateTask("", user,  trainingProviderMonitoring.getId(),  trainingProviderMonitoring.getClass().getName(), true, tasks, null, null);
		} else {
			TasksService.instance().findNextInProcessAndCreateTask("", user,  trainingProviderMonitoring.getId(),  trainingProviderMonitoring.getClass().getName(), true, tasks, null, usersByRegion);
		}
		//TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}
	
	public void sendProvidorMonitoringAudit(TrainingProviderMonitoring tpm, Users user) throws Exception{
		AuditorMonitorReviewService auditorMonitorReviewService = new AuditorMonitorReviewService();
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService js=new JasperService();
		String fileNameID="";
		TrainingProviderApplicationService trainingProviderApplicationService=new TrainingProviderApplicationService();
		TrainingProviderApplication trainingProviderApplication=trainingProviderApplicationService.findByKey(1L);
		params.put("applicationType", trainingProviderApplication.getAccreditationApplicationTypeEnum().getFriendlyName());
		params.put("TrainingProviderApplication", trainingProviderApplication);
		
		List<ReportSignOffBean> signoffBean = new ArrayList<>();
		List<Signoff> signOffList = signoffService.findByTargetKeyAndClass(tpm.getId(), tpm.getClass().getName());
		for (Signoff signoff : signOffList) {
			ReportSignOffBean entry = new ReportSignOffBean();
			if (signoff.getUser() != null) {
				entry.setSignOffFullName(signoff.getUser().getFirstName().trim() + " " + signoff.getUser().getLastName().trim());
			} else {
				entry.setSignOffFullName("");
			}
			
			if (signoff.getSignOffTitle() != null) {
				entry.setSignOffType(signoff.getSignOffTitle().trim());
			} else {
				entry.setSignOffType("");
			}
			
			if (signoff.getSignOffDate() != null) {
				entry.setDateSignedOff(HAJConstants.sdfDDMMYYYYHHmm.format(signoff.getSignOffDate()));
			} else {
				entry.setDateSignedOff("Awaiting Sign Off");
			}
			
			if (signoff.getAccept() != null) {
				entry.setSignedOff(signoff.getAccept());
			} else {
				entry.setSignedOff(false);
			}
			signoffBean.add(entry);
		}
		
		//
		ArrayList<AuditorMonitorReview> auditorMonitorReviewList=(ArrayList<AuditorMonitorReview>) auditorMonitorReviewService.findByTargetKeyAndClass(tpm.getClass().getName(), tpm.getId());
		params.put("AuditorMonitorReviewdataSource", new JRBeanCollectionDataSource(auditorMonitorReviewList));
		
		JasperService.addFormTemplateParams(params);
		params.put("footerDocTitle", "Self Evaluation Form".toUpperCase());
		params.put("footerDocNum", "");
		params.put("title", "Self Evaluation Form".toUpperCase());
		
		//js.createReportFromDBtoServletOutputStream("TrainingProviderSelfEvaluationForm.jasper", params,fileNameID+"_SelfEvaluationForm.pdf");
		
	    byte[] bites = JasperService.instance().convertJasperReportToByte("TrainingProviderSelfEvaluationForm.jasper", params);
	
	    String subject = "PROVIDER MONITOR AUDIT";
	    String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+ user.getFirstName() + " " + user.getLastName() + ",</p>"
		
			+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
			+ "Please find the attached provider monitor audit."
			+ "</p>"
			+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
			+ "Yours sincerely,"
			+ "</p>" 
			+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
			+ "<b>Manager: Quality Assurance</b>"
			+ "</p>";
	    

		 GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "ETQ-TP-049B-ProviderMonitorReport.pdf", "pdf", null);
	}
	
	public void sendProvidorMonitoringApprovalReport(TrainingProviderMonitoring tpm, Users user) throws Exception
	{
		AuditorMonitorReviewService auditorMonitorReviewService = new AuditorMonitorReviewService();
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService js=new JasperService();
		String fileNameID="";
		TrainingProviderApplicationService trainingProviderApplicationService=new TrainingProviderApplicationService();
		TrainingProviderApplication trainingProviderApplication=trainingProviderApplicationService.findByKey(1L);
		params.put("applicationType", trainingProviderApplication.getAccreditationApplicationTypeEnum().getFriendlyName());
		params.put("TrainingProviderApplication", trainingProviderApplication);
		
		////
		ArrayList<AuditorMonitorReview> auditorMonitorReviewList=(ArrayList<AuditorMonitorReview>) auditorMonitorReviewService.findByTargetKeyAndClass(tpm.getClass().getName(), tpm.getId());
		params.put("AuditorMonitorReviewdataSource", new JRBeanCollectionDataSource(auditorMonitorReviewList));
		
		JasperService.addFormTemplateParams(params);
		params.put("footerDocTitle", "Self Evaluation Form".toUpperCase());
		params.put("footerDocNum", "");
		params.put("title", "Self Evaluation Form".toUpperCase());
		
		//js.createReportFromDBtoServletOutputStream("TrainingProviderSelfEvaluationForm.jasper", params,fileNameID+"_SelfEvaluationForm.pdf");
		
	    byte[] bites = JasperService.instance().convertJasperReportToByte("TrainingProviderSelfEvaluationForm.jasper", params);
	
	    String subject = "PROVIDER MONITOR REPORT";
	    String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+ user.getFirstName() + " " + user.getLastName() + ",</p>"
		
			+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
			+ "Please find the attached provider monitor report."
			+ "</p>"
			+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
			+ "Yours sincerely,"
			+ "</p>" 
			+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
			+ "<b>Manager: Quality Assurance</b>"
			+ "</p>";

	    
		 GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "ETQ-TP-049B-ProviderMonitorReport.pdf", "pdf", null);
	}
	
	public void sendProvidorMonitoringCancelledReport(TrainingProviderMonitoring tpm, Users user, List<RejectReasons> rejectReasons) throws Exception
	{
		AuditorMonitorReviewService auditorMonitorReviewService = new AuditorMonitorReviewService();
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService js=new JasperService();
		String fileNameID="";
		TrainingProviderApplicationService trainingProviderApplicationService=new TrainingProviderApplicationService();
		TrainingProviderApplication trainingProviderApplication=trainingProviderApplicationService.findByKey(1L);
//		params.put("applicationType", trainingProviderApplication.getAccreditationApplicationTypeEnum().getFriendlyName());
//		params.put("TrainingProviderApplication", trainingProviderApplication);
//		ArrayList<AuditorMonitorReview> auditorMonitorReviewList=(ArrayList<AuditorMonitorReview>) auditorMonitorReviewService.findByTargetKeyAndClass(tpm.getClass().getName(), tpm.getId());
//		params.put("AuditorMonitorReviewdataSource", new JRBeanCollectionDataSource(auditorMonitorReviewList));
//		JasperService.addFormTemplateParams(params);
//		params.put("footerDocTitle", "Self Evaluation Form".toUpperCase());
//		params.put("footerDocNum", "");
//		params.put("title", "Self Evaluation Form".toUpperCase());
		//js.createReportFromDBtoServletOutputStream("TrainingProviderSelfEvaluationForm.jasper", params,fileNameID+"_SelfEvaluationForm.pdf");
	    //byte[] bites = JasperService.instance().convertJasperReportToByte("TrainingProviderSelfEvaluationForm.jasper", params);
	    String subject = "PROVIDER MONITOR AUDIT CANCELLED";
	    String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+ user.getFirstName() + " " + user.getLastName() + ",</p>"
			+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
			+ "Provider monitor audit has been cancelled."
			+ "</p>"
			+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
			+ "Yours sincerely,"
			+ "</p>" 
			+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
			+ "<b>Manager: Quality Assurance</b>"
			+ "</p>";
	     GenericUtility.sendMail(user.getEmail(), subject, mssg, null);
	     // GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "ETQ-TP-049B-ProviderMonitorReport.pdf", "pdf", null);
	}
	
	//String rejectReason = convertToHTML(rejectReasonsList);

	public void rejectCompanyLearners(TrainingProviderMonitoring companyLearners, Users user, Tasks tasks, List<RejectReasons> rejectionReasonsList) throws Exception {
		List<Users> tasksUsersList = null;
		if (tasks != null) {
			if (tasks.getWorkflowProcess() != null && tasks.getProcessRole() != null && (tasks.getWorkflowProcess() == ConfigDocProcessEnum.ProviderMonitoring || tasks.getWorkflowProcess() == ConfigDocProcessEnum.AuditMonitoring)) {
				ProcessRoles pr = ProcessRolesService.instance().findByKey(tasks.getProcessRole().getId());
				if (pr != null && pr.getRoleOrder() != null) {
					tasksUsersList = new ArrayList<>();
					if (tasks.getWorkflowProcess() == ConfigDocProcessEnum.ProviderMonitoring) {
						// provider monitoring
						if (pr.getRoleOrder().equals(4)) {
							// send to QA
							if (companyLearners.getInitiatingQA() != null) {
								tasksUsersList.add(companyLearners.getInitiatingQA());
							}
						}
					} else {
						// provider audit
						if (pr.getRoleOrder().equals(4)) {
							// send to QA
							if (companyLearners.getInitiatingQA() != null) {
								tasksUsersList.add(companyLearners.getInitiatingQA());
							}
						}
					}
				}
			} 
		}
		TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, tasksUsersList);
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(companyLearners.getId(), TrainingProviderMonitoring.class.getName(), rejectionReasonsList, user, ConfigDocProcessEnum.ProviderMonitoring);
	}

	public void finalApproveCompanyLearners(TrainingProviderMonitoring trainingProviderMonitoring, Users user, Tasks tasks) throws Exception {
		trainingProviderMonitoring.setStatus(ApprovalEnum.Approved);
		trainingProviderMonitoring.setQaManager(user);
		trainingProviderMonitoring.setApprovalDate(getSynchronizedDate());
		dao.update(trainingProviderMonitoring);
		TasksService.instance().completeTask(tasks);
		sendFinalApprovalRejectionNotification(trainingProviderMonitoring, user, true);
		RejectReasonMultipleSelectionService.instance().clearEntries(trainingProviderMonitoring.getId(), TrainingProviderMonitoring.class.getName(), ConfigDocProcessEnum.ProviderMonitoring);
	}

	public void finalRejectCompanyLearners(TrainingProviderMonitoring trainingProviderMonitoring, Users user, Tasks tasks, List<RejectReasons> rejectionReasonsList) throws Exception {
		trainingProviderMonitoring.setStatus(ApprovalEnum.Rejected);
		trainingProviderMonitoring.setQaManager(user);
		trainingProviderMonitoring.setApprovalDate(getSynchronizedDate());
		dao.update(trainingProviderMonitoring);
		TasksService.instance().completeTask(tasks);
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(trainingProviderMonitoring.getId(), TrainingProviderMonitoring.class.getName(), rejectionReasonsList, user, ConfigDocProcessEnum.ProviderMonitoring);
		sendFinalApprovalRejectionNotification(trainingProviderMonitoring, user, false);
	}
	
	private void sendFinalApprovalRejectionNotification(TrainingProviderMonitoring trainingProviderMonitoring, Users qaManager, boolean approval) throws Exception {
		if (trainingProviderApplicationService == null) {
			trainingProviderApplicationService = new TrainingProviderApplicationService();
		}
		if (companyService == null) {
			companyService = new CompanyService();
		}
		if (trainingProviderApplicationService == null) {
			trainingProviderApplicationService = new TrainingProviderApplicationService();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		// populate service levels
		UsersService usersService = new UsersService();
		// populate objects
		Users qaUser = usersService.findByKey(trainingProviderMonitoring.getUser().getId());
//		Users mainSdpUser = trainingProviderApplicationService.findUsersByCompanyAndReturnUser(trainingProviderMonitoring.getCompany());
		if (companyUsersService == null) {
			companyUsersService = new CompanyUsersService();
		}
		Users mainSdpUser = companyUsersService.findSdpUsersByCompanyIdReturnFirstUser(trainingProviderMonitoring.getCompany().getId(), true); 
		
		Company company = companyService.findByKey(trainingProviderMonitoring.getCompany().getId());
		Address address = null;
		if (company.getResidentialAddress() != null && company.getResidentialAddress().getId() != null) {
			AddressService.instance().findByKey(company.getResidentialAddress().getId());
		}
		TrainingProviderApplication tpa = null;
		if (trainingProviderMonitoring.getTrainingProviderApplication() != null && trainingProviderMonitoring.getTrainingProviderApplication().getId() != null) {
			tpa = trainingProviderApplicationService.findByKey(trainingProviderMonitoring.getTrainingProviderApplication().getId());
		}
		
		if (signoffService == null) {
			signoffService = new SignoffService();
		}
		
		
		String sdpFullname = "";
		String managerName = "";
		String mssg = "";
		if (mainSdpUser.getTitle() != null && mainSdpUser.getTitle().getDescription() != null) {
			sdpFullname = mainSdpUser.getTitle().getDescription() + " ";
		}
		sdpFullname = sdpFullname + mainSdpUser.getFirstName() + " " +  mainSdpUser.getLastName();
		
		if (qaManager != null) {
			managerName = qaManager.getFirstName() + " " +  qaManager.getLastName();
		} else {
			managerName = qaUser.getFirstName() + " " +  qaUser.getLastName();
		}

		if (companyService == null) {
			companyService = new CompanyService();
		}
		String subject = "";
		if (approval) {
			// final approval notification
			if (trainingProviderMonitoring.getAudit()) {
				subject = "PROVIDER AUDIT VISIT OUTCOME APPROVAL";
				mssg = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">APPROVAL OUTCOME OF THE AUDIT SITE VISIT: #COMPANY_NAME# (#ACCREDITATION_NUMBER#) </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #FULL_NAME#, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please find the report from the audit visit that was conducted at the following location: #COMPANY_NAME# on " + sdf.format(trainingProviderMonitoring.getMonitoringDate()) + ".</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please do not hesitate to contact the Regional office for further assistance.</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">#MANAGER_FULL_NAME#<br/> "
					+ "Manager: Quality Assurance"
					+ "</p>";
			} else { 
				subject = "PROVIDER MONITORING VISIT OUTCOME APPROVAL";
				mssg = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">APPROVAL OUTCOME OF THE MONITORING SITE VISIT: #COMPANY_NAME# (#ACCREDITATION_NUMBER#) </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #FULL_NAME#, </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please find the report from the monitoring visit that was conducted at the following location: #COMPANY_NAME# on #DATE#.</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please do not hesitate to contact the Regional office for further assistance.</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely, </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">#MANAGER_FULL_NAME#<br/> "
						+ "Manager: Quality Assurance"
						+ "</p>";
			}
		} else {
			// final rejection notification
			if (trainingProviderMonitoring.getAudit()) {
				subject = "PROVIDER AUDIT VISIT OUTCOME NON-APPROVAL";
				mssg = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">NON-APPROVAL OUTCOME OF THE AUDIT SITE VISIT: #COMPANY_NAME# (#ACCREDITATION_NUMBER#) </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #FULL_NAME#, </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please find the report from the monitoring visit that was conducted at the following location: #COMPANY_NAME# on #DATE#.</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please do not hesitate to contact the Regional office for further assistance.</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely, </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">#MANAGER_FULL_NAME#<br/> "
						+ "Manager: Quality Assurance"
						+ "</p>";
			} else { 
				subject = "PROVIDER MONITORING VISIT OUTCOME NON-APPROVAL";
				mssg = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">NON-APPROVAL OUTCOME OF THE MONITORING SITE VISIT: #COMPANY_NAME# (#ACCREDITATION_NUMBER#) </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #FULL_NAME#, </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please find the report from the monitoring visit that was conducted at the following location: #COMPANY_NAME# on #DATE#.</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please do not hesitate to contact the Regional office for further assistance.</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely, </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">#MANAGER_FULL_NAME#<br/> "
						+ "Manager: Quality Assurance"
						+ "</p>";
			}
		}
		
		// replace information for messages
		mssg = mssg.replace("#COMPANY_NAME#", company.getCompanyName().trim());
		mssg = mssg.replace("#DATE#", sdf.format(trainingProviderMonitoring.getMonitoringDate()));
		mssg = mssg.replace("#MANAGER_FULL_NAME#", managerName.trim());
		if (tpa != null && tpa.getId() != null) {
			if (tpa.getCertificateNumber() != null && !tpa.getCertificateNumber().isEmpty()) {
				mssg = mssg.replace("#ACCREDITATION_NUMBER#", tpa.getCertificateNumber());
			} else if (tpa.getAccreditationNumber() != null && !tpa.getAccreditationNumber().isEmpty()) {
				mssg = mssg.replace("#ACCREDITATION_NUMBER#", tpa.getAccreditationNumber());
			} else {
				mssg = mssg.replace("#ACCREDITATION_NUMBER#", " ");
			}
		} else {
			mssg = mssg.replace("#ACCREDITATION_NUMBER#", " ");
		}
//		mssg = mssg.replace("#ACCREDITATION_NUMBER#", ((tpa != null && tpa.getId() != null && tpa.getAccreditationNumber() != null) ? tpa.getAccreditationNumber() : " "));

		 /* Self Reg form Version One*/
//		Map<String, Object> params = new HashMap<String, Object>();
//	    AuditorMonitorReviewService auditorMonitorReviewService = new AuditorMonitorReviewService();
//		params = new HashMap<String, Object>();
//		TrainingProviderApplicationService trainingProviderApplicationService=new TrainingProviderApplicationService();
//		TrainingProviderApplication trainingProviderApplication=trainingProviderApplicationService.findByKey(1L);
//		params.put("applicationType", trainingProviderApplication.getAccreditationApplicationTypeEnum().getFriendlyName());
//		params.put("TrainingProviderApplication", trainingProviderApplication);
//		List<AuditorMonitorReview> auditorMonitorReviewList = auditorMonitorReviewService.findByTargetKeyAndClassNoDoc(trainingProviderMonitoring.getClass().getName(), trainingProviderMonitoring.getId());
//		params.put("AuditorMonitorReviewdataSource", new JRBeanCollectionDataSource(auditorMonitorReviewList));
//		JasperService.addFormTemplateParams(params);
//		params.put("footerDocTitle", "Self Evaluation Form".toUpperCase());
//		params.put("footerDocNum", "");
//		params.put("title", "Self Evaluation Form".toUpperCase());		
//	    byte[] bites = JasperService.instance().convertJasperReportToByte("TrainingProviderSelfEvaluationForm.jasper", params);
		
		/* Self Reg form Version Two*/
		company = companyService.findByKey(trainingProviderMonitoring.getCompany().getId());
		address = new Address();
		if (company.getResidentialAddress() != null && company.getResidentialAddress().getId() != null) {
			address = AddressService.instance().findByKey(company.getResidentialAddress().getId());
		}
		HostingCompanyEmployeesService hceService = new HostingCompanyEmployeesService();
		HostingCompanyEmployees hce = hceService.findByUserReturnHostCompany(trainingProviderMonitoring.getUser().getId());
		JobTitle jobTitle = JobTitleService.instance().findByKey(hce.getJobTitle().getId());
		
		// Version Two
		byte[] bitesEtqTp049B = populateMonitorAuditReport(trainingProviderMonitoring, sdpFullname, managerName, company, address, jobTitle);
		
		List<Users> usersToNotify = new ArrayList<>();
		List<Signoff> signOffList = signoffService.findByTargetKeyAndClass(trainingProviderMonitoring.getId(), trainingProviderMonitoring.getClass().getName());
		// adds sign off users to notification
		for (Signoff signoff : signOffList) {
			if (signoff.getUser() != null) {
				usersToNotify.add(signoff.getUser());
			}
		}
	    
	    // check if Main SDP user is on notification list
	    if (mainSdpUser != null) {
	    	boolean addMainSdp = true;
		    for (Users user : usersToNotify) {
		    	if (user.getId().equals(mainSdpUser.getId())) {
		    		addMainSdp = false;
		    		break;
				}
			}
		    if (addMainSdp) {
				usersToNotify.add(mainSdpUser);
			}
	    }
	    
//	    Not Applicable anymore
//		// SEND TO QA
//	    if (qaUser != null) {
//	    	usersToNotify.add(qaUser);
//		}
//		// SEND TO QA MANAGER
//	    if (qaManager != null) {
//	    	usersToNotify.add(qaManager);
//		} 
	    
	    for (Users u : usersToNotify) {
	    	String fullName = "";
			if (u.getTitle() != null && u.getTitle().getDescription() != null) {
				fullName = u.getTitle().getDescription().trim() + " ";
			}
			fullName += u.getFirstName().trim() + " " +  u.getLastName().trim();
	    	GenericUtility.sendMailWithAttachement(u.getEmail(), subject,  mssg.replace("#FULL_NAME#", fullName.trim()), bitesEtqTp049B, "ETQ-TP-049B-ProviderMonitorAuditReport.pdf", "pdf", null);
		}
	}
	
	public void cancelTrainingProviderMonitoring(TrainingProviderMonitoring trainingProviderMonitoring, Users user, Tasks tasks, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		trainingProviderMonitoring.setStatus(ApprovalEnum.Withdrawn);
		trainingProviderMonitoring.setApprovalDate(getSynchronizedDate());
		dao.update(trainingProviderMonitoring);
		// Creating RejectReason
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(trainingProviderMonitoring.getId(), TrainingProviderMonitoring.class.getName(), selectedRejectReason, user, ConfigDocProcessEnum.ProviderMonitoring);
		for(RejectReasonMultipleSelection rrm:rrmList){
			if(rrm !=null){
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}	
		TasksService.instance().completeTaskByTargetKey(trainingProviderMonitoring.getClass().getName(), trainingProviderMonitoring.getId());
	}
	
	public void sendCancelNotification(Users sdpUser, TrainingProviderMonitoring trainingProviderMonitoring) throws Exception{
		HostingCompanyEmployeesService hceService = new HostingCompanyEmployeesService();
		List<Users> manager = hceService.findUserByJobTitle(HAJConstants.Manager_Quality_Assurance_ID);
		hceService = null;
		
		String sdpFullname = "";
		String managerfullName = "";
		String subject = "";
		String body = "";
		if (sdpUser.getTitle() != null && sdpUser.getTitle().getDescription() != null) {
			sdpFullname = sdpUser.getTitle().getDescription() + " ";
		}
		sdpFullname = sdpFullname + sdpUser.getFirstName().trim() + " " +  sdpUser.getLastName().trim();
		if (manager.size() != 0) {
			managerfullName = manager.get(0).getFirstName().trim() + " " + manager.get(0).getLastName().trim();
		}
		
		TrainingProviderApplication tpa = null;
		if (trainingProviderMonitoring.getTrainingProviderApplication() != null && trainingProviderMonitoring.getTrainingProviderApplication().getId() != null) {
			if (trainingProviderApplicationService == null) {
				trainingProviderApplicationService = new TrainingProviderApplicationService();
			}
			tpa = trainingProviderApplicationService.findByKey(trainingProviderMonitoring.getTrainingProviderApplication().getId());
		}
		/*
		 * The Provider Monitoring/Audit visit for #Provider Name# (#Accreditation_number#) scheduled for 04/06/2019 has been cancelled. 
		 * 
		 * A notification with the new date will be sent in due course.  
		 */
		if (trainingProviderMonitoring.getAudit()) {
			/* Audit Notification */
			subject = "PROVIDER AUDIT DATE CANCELLATION";
			body = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #SDP_FULL_NAME#, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The Provider Audit visit for #PROVIDER_NAME# (#ACCREDITATION_NUMBER#) scheduled for #MONITORING_AUDIT_DATE# has been cancelled.</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">A notification with the new date will be sent in due course.</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please do not hesitate to contact the Regional office for further assistance.</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> #MANAGER_FULL_NAME# <br/> "
					+ "Manager: Quality Assurance"
					+ "</p>";
		} else {
			/* Monitoring Notification */
			subject = "PROVIDER MONITORING DATE CANCELLATION";
			body = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #SDP_FULL_NAME#, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The Provider Monitoring visit for #PROVIDER_NAME# (#ACCREDITATION_NUMBER#) scheduled for #MONITORING_AUDIT_DATE# has been cancelled.</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">A notification with the new date will be sent in due course.</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please do not hesitate to contact the Regional office for further assistance.</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> #MANAGER_FULL_NAME# <br/> "
					+ "Manager: Quality Assurance"
					+ "</p>";
		}
		
		body = body.replace("#PROVIDER_NAME#", trainingProviderMonitoring.getCompany().getCompanyName());
		if (tpa != null && tpa.getId() != null) {
			if (tpa.getCertificateNumber() != null && !tpa.getCertificateNumber().isEmpty()) {
				body = body.replace("#ACCREDITATION_NUMBER#", tpa.getCertificateNumber());
			} else if (tpa.getAccreditationNumber() != null && !tpa.getAccreditationNumber().isEmpty()) {
				body = body.replace("#ACCREDITATION_NUMBER#", tpa.getAccreditationNumber());
			} else {
				body = body.replace("#ACCREDITATION_NUMBER#", " ");
			}
		} else {
			body = body.replace("#ACCREDITATION_NUMBER#", " ");
		}
		
//		if (trainingProviderMonitoring.getCompany().getAccreditationNumber() != null) {
//			body = body.replace("#ACCREDITATION_NUMBER#", trainingProviderMonitoring.getCompany().getAccreditationNumber());
//		}
		
		body = body.replace("#SDP_FULL_NAME#", sdpFullname);
		body = body.replace("#MANAGER_FULL_NAME#", managerfullName);
		if (trainingProviderMonitoring.getMonitoringDate() != null) {
			body = body.replace("#MONITORING_AUDIT_DATE#", HAJConstants.sdfDDMMYYYY2.format(trainingProviderMonitoring.getMonitoringDate()));
		}
		
		GenericUtility.sendMail(sdpUser.getEmail(), subject, body, null);
//		GenericUtility.sendMail("", subject, body, null);
	}
	
	public TrainingProviderMonitoring findMonitoringDate(Company company) throws Exception {
		return dao.findMonitoringDateByCompany(company);
	}
	
	/*
	 * Calendar View Start
	 */
	
	// display all information
	@SuppressWarnings("unchecked")
	public List<TrainingProviderMonitoring> allAudits(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> parameters) throws Exception {
		String hql = "select o from TrainingProviderMonitoring o ";
		return populateAdditionalInformationListCal((List<TrainingProviderMonitoring>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, parameters, hql));
	}

	public int countAllAudits(Map<String, Object> filters) {
		String hql = "select count(o) from TrainingProviderMonitoring o ";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderMonitoring> allAuditsBeforeAfterDate(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> parameters, boolean beforeDate, Date date) throws Exception {
		String hql = "select o from TrainingProviderMonitoring o where ";
		if (beforeDate) {
			hql += " DATE(o.monitoringDate) < DATE(:date) ";
		} else {
			hql += " DATE(o.monitoringDate) > DATE(:date) ";
		}
		parameters.put("date", date);
		return populateAdditionalInformationListCal((List<TrainingProviderMonitoring>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, parameters, hql));
	}

	public int countAllAuditsBeforeAfterDate(Map<String, Object> filters, boolean beforeDate) {
		String hql = "select count(o) from TrainingProviderMonitoring o where ";
		if (beforeDate) {
			hql += " DATE(o.monitoringDate) < DATE(:date) ";
		} else {
			hql += " DATE(o.monitoringDate) > DATE(:date) ";
		}
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderMonitoring> allAuditsBeforeAfterDateByUser(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> parameters, boolean beforeDate, Date date) throws Exception {
		String hql = "select o from TrainingProviderMonitoring o where o.user.id = :userId and ";
		if (beforeDate) {
			hql += " DATE(o.monitoringDate) < DATE(:date) ";
		} else {
			hql += " DATE(o.monitoringDate) > DATE(:date) ";
		}
		parameters.put("date", date);
		return populateAdditionalInformationListCal((List<TrainingProviderMonitoring>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, parameters, hql));
	}

	public int countAllAuditsBeforeAfterDateByUser(Map<String, Object> filters, boolean beforeDate) {
		String hql = "select count(o) from TrainingProviderMonitoring o where o.user.id = :userId and ";
		if (beforeDate) {
			hql += " DATE(o.monitoringDate) < DATE(:date) ";
		} else {
			hql += " DATE(o.monitoringDate) > DATE(:date) ";
		}
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderMonitoring> allAuditsByQa(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> parameters) throws Exception {
		String hql = "select o from TrainingProviderMonitoring o where o.user.id = :userId ";
		return populateAdditionalInformationListCal((List<TrainingProviderMonitoring>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, parameters, hql));
	}

	public int countAllAuditsByQa(Map<String, Object> filters) {
		String hql = "select count(o) from TrainingProviderMonitoring o where o.user.id = :userId ";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderMonitoring> allAuditsBeforeAfterDateWithUser(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> parameters, boolean beforeDate, Date date, Users user) throws Exception {
		String hql = "select o from TrainingProviderMonitoring o where o.user.id = :userId and ";
		if (beforeDate) {
			hql += " DATE(o.monitoringDate) < DATE(:date) ";
		} else {
			hql += " DATE(o.monitoringDate) > DATE(:date) ";
		}
		parameters.put("date", date);
		parameters.put("userId", user.getId());
		return populateAdditionalInformationListCal((List<TrainingProviderMonitoring>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, parameters, hql));
	}

	public int countAllAuditsBeforeAfterDateWithUser(Map<String, Object> filters, boolean beforeDate) {
		String hql = "select count(o) from TrainingProviderMonitoring o where o.user.id = :userId and ";
		if (beforeDate) {
			hql += " DATE(o.monitoringDate) < DATE(:date) ";
		} else {
			hql += " DATE(o.monitoringDate) > DATE(:date) ";
		}
		return dao.countWhere(filters, hql);
	}
	
	// display all information by region
	@SuppressWarnings("unchecked")
	public List<TrainingProviderMonitoring> allAuditsByRegion(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> parameters) throws Exception {
		String hql = "select o from TrainingProviderMonitoring o ";
		return (List<TrainingProviderMonitoring>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, parameters, hql);
	}
	
	public int countAllAuditsByRegion(Map<String, Object> filters) {
		String hql = "select count(o) from TrainingProviderMonitoring o ";
		return dao.countWhere(filters, hql);
	}
	
	// all up and coming audits
	@SuppressWarnings("unchecked")
	public List<TrainingProviderMonitoring> allUpcomingAudits(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> parameters) throws Exception {
		String hql = "select o from TrainingProviderMonitoring o where DATE(o.monitoringDate) > DATE(:datePassed)";
		return (List<TrainingProviderMonitoring>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, parameters, hql);
	}
	
	public int countAllUpcomingAudits(Map<String, Object> filters) {
		String hql = "select count(o) from TrainingProviderMonitoring o where DATE(o.monitoringDate) > DATE(:datePassed) ";
		return dao.countWhere(filters, hql);
	}
	
	// populates add info for schedule report
	private List<TrainingProviderMonitoring> populateAdditionalInformationListCal(List<TrainingProviderMonitoring> trainingProviderMonitoringList) throws Exception {
		for (TrainingProviderMonitoring trainingProviderMonitoring : trainingProviderMonitoringList) {
			populateAdditionalInformationCal(trainingProviderMonitoring);
		}
		return trainingProviderMonitoringList;
	}

	private void populateAdditionalInformationCal(TrainingProviderMonitoring trainingProviderMonitoring) throws Exception {
		if (trainingProviderMonitoring.getCompany() != null && trainingProviderMonitoring.getCompany().getId() != null) {
			if (companyService == null) {
				companyService = new CompanyService();
			}
			if (rejectReasonsService == null) {
				rejectReasonsService = new RejectReasonsService();
			}
			if (trainingProviderMonitoring.getCompany() != null && trainingProviderMonitoring.getCompany().getResidentialAddress() != null && trainingProviderMonitoring.getCompany().getResidentialAddress().getId() != null) {
				trainingProviderMonitoring.getCompany().setResidentialAddress(AddressService.instance().findByKey(trainingProviderMonitoring.getCompany().getResidentialAddress().getId()));
				trainingProviderMonitoring.getCompany().setRegionTown(RegionTownService.instance().findByTown(trainingProviderMonitoring.getCompany().getResidentialAddress().getTown()));
			}
			if (trainingProviderMonitoring.getId() != null && trainingProviderMonitoring.getStatus() != null && (trainingProviderMonitoring.getStatus() == ApprovalEnum.Rejected || trainingProviderMonitoring.getStatus() == ApprovalEnum.Withdrawn)) {
				// populate rejection reasons
				trainingProviderMonitoring.setRejectionReasons(rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcessAndReturnInString(trainingProviderMonitoring.getId(), trainingProviderMonitoring.getClass().getName(), null));
				trainingProviderMonitoring.setRejectionReasonsList(rejectReasonsService.locateReasonsSelectedByTargetKeyAndClass(trainingProviderMonitoring.getId(), trainingProviderMonitoring.getClass().getName()));
			}
			
			// populate training site regions
			if (trainingProviderMonitoring.getTrainingProviderApplication() != null && trainingProviderMonitoring.getTrainingProviderApplication().getTrainingSite() != null) {
				TrainingSiteService.instance().resolveAddressInformatioAndRegion(trainingProviderMonitoring.getTrainingProviderApplication().getTrainingSite());
			}
		}
	}
	
	/*
	 * Calendar View End
	 */

}
