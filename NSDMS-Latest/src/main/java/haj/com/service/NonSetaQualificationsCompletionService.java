package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.NonSetaQualificationsCompletionDAO;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.CompletionLetter;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.NonSetaQualificationsCompletion;
import haj.com.entity.ProcessRoles;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.SDFCompany;
import haj.com.entity.SummativeAssessmentReport;
import haj.com.entity.SummativeAssessmentReportUnitStandards;
import haj.com.entity.Tasks;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

import static haj.com.constants.HAJConstants.SKILLS_PROGRAM_LIST;
import static haj.com.constants.HAJConstants.SKILLS_SET_LIST;

public class NonSetaQualificationsCompletionService extends AbstractService {
	/** The dao. */
	private NonSetaQualificationsCompletionDAO dao = new NonSetaQualificationsCompletionDAO();
	private ConfigDocService configDocService = new ConfigDocService();
	private UnitStandardsService unitStandardsService = new UnitStandardsService();
	private SummativeAssessmentReportUnitStandardsService summativeAssessmentReportUnitStandardsService = new SummativeAssessmentReportUnitStandardsService();
	/**
	 * Get all NonSetaQualificationsCompletion
	 * 
	 * @author TechFinium
	 * @see NonSetaQualificationsCompletion
	 * @return a list of {@link haj.com.entity.NonSetaQualificationsCompletion}
	 * @throws Exception
	 *             the exception
	 */
	public List<NonSetaQualificationsCompletion> allNonSetaQualificationsCompletion() throws Exception {
		return dao.allNonSetaQualificationsCompletion();
	}

	/**
	 * Create or update NonSetaQualificationsCompletion.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see NonSetaQualificationsCompletion
	 */
	public void create(NonSetaQualificationsCompletion entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update NonSetaQualificationsCompletion.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see NonSetaQualificationsCompletion
	 */
	public void update(NonSetaQualificationsCompletion entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete NonSetaQualificationsCompletion.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see NonSetaQualificationsCompletion
	 */
	public void delete(NonSetaQualificationsCompletion entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.NonSetaQualificationsCompletion}
	 * @throws Exception
	 *             the exception
	 * @see NonSetaQualificationsCompletion
	 */
	public NonSetaQualificationsCompletion findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}
	
	public List<NonSetaQualificationsCompletion> findByCompanyLearner(CompanyLearners companyLearners) {
		return dao.findByCompanyLearner(companyLearners.getId());
	}
	
	public NonSetaQualificationsCompletion findByKeyAndPopulateLearnerDocs(long id) throws Exception {
		 NonSetaQualificationsCompletion nonSetaQualificationsCompletion = dao.findByKey(id);
		 List<Doc>docs = DocService.instance().searchByTargetKeyAndClassNoConfigDoc(CompanyLearners.class.getName(), nonSetaQualificationsCompletion.getCompanyLearners().getId());
		 nonSetaQualificationsCompletion.getCompanyLearners().setDocs(docs);
		 return nonSetaQualificationsCompletion;
	}
	
	public void requestNonSetaQualificationsCompletion(NonSetaQualificationsCompletion nonSetaQualificationsCompletion, Users user)throws Exception {
		//List<IDataEntity> entityList = new ArrayList<IDataEntity>();
		//List<UnitStandards> unitStandards = new ArrayList<>();
		nonSetaQualificationsCompletion.setCreateUser(user);
		nonSetaQualificationsCompletion.setStatus(ApprovalEnum.PendingApproval);
		
		if(nonSetaQualificationsCompletion.getCompanyLearners()!=null ) {
			CompanyLearners cl = CompanyLearnersService.instance().findByKey(nonSetaQualificationsCompletion.getCompanyLearners().getId());
			nonSetaQualificationsCompletion.setTrainingProvider(cl.getCompany());
		}
				
		//unitStandards = unitStandardsService.findByQualification(nonSetaQualificationsCompletion.getCompanyLearners().getQualification().getCode());
		//unitStandards = unitStandardsService.findByQualification(23114);
		//if(unitStandards.size() == 0)throw new Exception("No unit standard(s) available");
		
		dao.create(nonSetaQualificationsCompletion);
		
		/*SummativeAssessmentReport summativeAssessmentReport = new SummativeAssessmentReport();
		summativeAssessmentReport.setNonSetaQualificationsCompletion(nonSetaQualificationsCompletion);
		summativeAssessmentReport.setInterventionType(nonSetaQualificationsCompletion.getCompanyLearners().getInterventionType());
		summativeAssessmentReport.setCompanyLearners(nonSetaQualificationsCompletion.getCompanyLearners());
		summativeAssessmentReport.setQualification(nonSetaQualificationsCompletion.getCompanyLearners().getQualification());
		summativeAssessmentReport.setUsers(user);
		
		nonSetaQualificationsCompletion.setSummativeAssessmentReport(summativeAssessmentReport);
		entityList.add(summativeAssessmentReport);
		entityList.add(nonSetaQualificationsCompletion);
		
		
		for(UnitStandards unitStandard :unitStandards) {
			entityList.add(new SummativeAssessmentReportUnitStandards(summativeAssessmentReport, unitStandard));
		}
		
		dao.createBatch(entityList);	*/	
		
		TasksService.instance().findFirstInProcessAndCreateTask(user, nonSetaQualificationsCompletion.getId(), nonSetaQualificationsCompletion.getClass().getName(), ConfigDocProcessEnum.RegisterNonSetaQualification, false);
		//sendNotificationOfQualification(nonSetaQualificationsCompletion,  user);
	}

	public void prepareNewRegistration(NonSetaQualificationsCompletion entity, CompanyUserTypeEnum companyUserTypeEnum) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), ConfigDocProcessEnum.RegisterNonSetaQualification));
			List<ConfigDoc> l = configDocService.findByProcessNotUploadedForReg(entity.getClass().getName(), entity.getId(), ConfigDocProcessEnum.RegisterNonSetaQualification, companyUserTypeEnum);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = configDocService.findByProcess(ConfigDocProcessEnum.RegisterNonSetaQualification, companyUserTypeEnum);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}
	
	public void prepareNewLearnershipDevelopmentRegistrationDocs(ConfigDocProcessEnum configDocProcess, NonSetaQualificationsCompletion entityDoc, NonSetaQualificationsCompletion entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null) {
			
			
			if (processRoles.getCompanyUserType() == null) entityDoc.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			else entityDoc.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), processRoles));

			List<ConfigDoc> l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), processRoles);

			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entityDoc.getDocs().add(new Doc(a));
				});
			}
			
			/*l = ConfigDocService.instance().findByProcessA(configDocProcess);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entityDoc.getDocs().add(new Doc(a));
				});
			}*/
			
		} else {
			entityDoc.setDocs(new ArrayList<>());
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessA(configDocProcess);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entityDoc.getDocs().add(new Doc(a));
				});
			}
		}
	}

	/**
	 * Find NonSetaQualificationsCompletion by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.NonSetaQualificationsCompletion}
	 * @throws Exception
	 *             the exception
	 * @see NonSetaQualificationsCompletion
	 */
	public List<NonSetaQualificationsCompletion> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load NonSetaQualificationsCompletion
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.NonSetaQualificationsCompletion}
	 * @throws Exception
	 *             the exception
	 */
	public List<NonSetaQualificationsCompletion> allNonSetaQualificationsCompletion(int first, int pageSize) throws Exception {
		return dao.allNonSetaQualificationsCompletion(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of NonSetaQualificationsCompletion for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the NonSetaQualificationsCompletion
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(NonSetaQualificationsCompletion.class);
	}

	/**
	 * Lazy load NonSetaQualificationsCompletion with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            NonSetaQualificationsCompletion class
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
	 * @return a list of {@link haj.com.entity.NonSetaQualificationsCompletion}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<NonSetaQualificationsCompletion> allNonSetaQualificationsCompletion(Class<NonSetaQualificationsCompletion> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<NonSetaQualificationsCompletion>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of NonSetaQualificationsCompletion for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            NonSetaQualificationsCompletion class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the NonSetaQualificationsCompletion entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<NonSetaQualificationsCompletion> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void completeWorkflow(NonSetaQualificationsCompletion nonSetaQualificationsCompletion, Users user, Tasks tasks, List<SummativeAssessmentReportUnitStandards> summativeAssessmentReportUnitStandards) throws Exception {
		uploadDocuments(nonSetaQualificationsCompletion, tasks, user);
		//this.dao.updateBatch((List<IDataEntity>) (List<?>) summativeAssessmentReportUnitStandards);
		nonSetaQualificationsCompletion.setStatus(ApprovalEnum.PendingApproval);
		this.dao.update(nonSetaQualificationsCompletion);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		sendNotificationOfQualification(nonSetaQualificationsCompletion,  user);
	}
	
	public void recommendWorkflow(NonSetaQualificationsCompletion companyLearners, Users user, Tasks tasks) throws Exception {
		companyLearners.setStatus(ApprovalEnum.Recommended);
		dao.update(companyLearners);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}

	public void approveWorkflow(NonSetaQualificationsCompletion companyLearners, Users user, Tasks tasks) throws Exception {
		companyLearners.setStatus(ApprovalEnum.WaitingForManager);
		dao.update(companyLearners);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}

	public void rejectWorkflow(NonSetaQualificationsCompletion companyLearners, Users user, Tasks tasks, ArrayList<RejectReasons> rejectReasons) throws Exception {
		companyLearners.setStatus(ApprovalEnum.Rejected);
		dao.update(companyLearners);
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearners.getId(), NonSetaQualificationsCompletion.class.getName(), rejectReasons, user, ConfigDocProcessEnum.RegisterNonSetaQualification);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		
		List<Users>users = new ArrayList<>();
		users.add(companyLearners.getCreateUser());
		TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, users);
		//TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, null);
		sendRejectNotificationOfQualification(companyLearners.getCompany(), companyLearners, rejectReasons);
	}

	public void finalApproveWorkflow(NonSetaQualificationsCompletion nonSetaQualificationsCompletion, Users user, Company trainingProvider, Tasks tasks) throws Exception {
		
		CompanyLearners cl = nonSetaQualificationsCompletion.getCompanyLearners();
		if(cl.getInterventionType().getId() == HAJConstants.NON_CREDIT_BEARING_SHORT_COURSE) {
			cl.setLearnerStatus(LearnerStatusEnum.Achieved);
			nonSetaQualificationsCompletion.setStatus(ApprovalEnum.Completed);
		}else {
			cl.setLearnerStatus(LearnerStatusEnum.Achieved);
			nonSetaQualificationsCompletion.setStatus(ApprovalEnum.Completed);
		}
		
		cl.setApprovalDate(getSynchronizedDate());
		CompanyLearnersService.instance().update(cl);		
		nonSetaQualificationsCompletion.setApprovalDate(getSynchronizedDate());
		
		dao.update(nonSetaQualificationsCompletion);
		trainingProvider = findUserByKey(nonSetaQualificationsCompletion.getTrainingProvider().getId());		
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		sendApprovaNotificationOfQualification(trainingProvider,nonSetaQualificationsCompletion);
	}

	public void finalRejectWorkflow(NonSetaQualificationsCompletion nonSetaQualificationsCompletion, Users user, Tasks tasks, ArrayList<RejectReasons> rejectReasons) throws Exception {
		nonSetaQualificationsCompletion.setStatus(ApprovalEnum.Rejected);
		nonSetaQualificationsCompletion.setApprovalDate(getSynchronizedDate());
		CompanyLearners cl = nonSetaQualificationsCompletion.getCompanyLearners();
		cl.setLearnerStatus(LearnerStatusEnum.Registered);
		CompanyLearnersService.instance().update(cl);
		dao.update(nonSetaQualificationsCompletion);
		TasksService.instance().completeTask(tasks);
		
		Company trainingProvider = findUserByKey(nonSetaQualificationsCompletion.getTrainingProvider().getId());
		sendRejectNotificationOfQualification(trainingProvider,nonSetaQualificationsCompletion, rejectReasons);
	}
	public void sendNotificationOfQualification(NonSetaQualificationsCompletion nonSetaQualificationsCompletion, Users createuser) throws Exception {
		
		CompanyLearners cl = CompanyLearnersService.instance().findByKey(nonSetaQualificationsCompletion.getCompanyLearners().getId());
		List<Users>users = findCompanyEmployerUsers(cl);
		users.add(createuser);		
		super.removeDuplicatesFromList(users);
		for(Users user:users) {
			
			Users leaner = nonSetaQualificationsCompletion.getCompanyLearners().getUser();
			
			String qual  = getQualiDescription(nonSetaQualificationsCompletion.getCompanyLearners());
			String IDPassport = anIDNumber(leaner);
			String welcome = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " +"#SDPNameAndSurname#,</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA acknowledges receipt of the submission for learner #LearnerFirstNameAndSurname# (#IDPassport#) who has completed for a non-merSETA scope qualification: #qualification# for review.</p>" 	
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please be advised that the review process may take up to five days. The application will be evaluated by the merSETA and should any additional information be required, this will be communicated to you. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours sincerely,</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA Administration </p>" 
					+ "<br/>";
			
			welcome = welcome.replaceAll("#SDPNameAndSurname#",user.getFirstName() + " "+ user.getLastName());
			welcome = welcome.replaceAll("#CompanyName#", nonSetaQualificationsCompletion.getCompany().getCompanyName());
			
			welcome = welcome.replaceAll("#LearnerFirstNameAndSurname#",leaner.getFirstName() + " "+ leaner.getLastName());
			welcome = welcome.replaceAll("#IDPassport#", IDPassport);
			
			welcome = welcome.replaceAll("#qualification#", qual);
			GenericUtility.sendMail(user.getEmail(), "ACKNOWLEDGEMENT OF SUBMISSION OF A LEARNER COMPLETION FOR A NON-merSETA SCOPE QUALIFICATION", welcome, null);
		}
	}
	public void sendApprovaNotificationOfQualification(Company trainingProvider, NonSetaQualificationsCompletion nonSetaQualificationsCompletion) throws Exception {
		Users createuser = nonSetaQualificationsCompletion.getCreateUser();		
		CompanyLearners cl = CompanyLearnersService.instance().findByKey(nonSetaQualificationsCompletion.getCompanyLearners().getId());
		List<Users>users = findCompanyEmployerUsers(cl);
		users.add(createuser);		
		super.removeDuplicatesFromList(users);
		for(Users user:users) {
			Users leaner = nonSetaQualificationsCompletion.getCompanyLearners().getUser();
			String qual = "";
		
			qual = getQualiDescription(nonSetaQualificationsCompletion.getCompanyLearners());
			
			String IDPassport = anIDNumber(leaner);
			String welcome = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " +"#SDPNameAndSurname#,</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA wishes to advise that the submission for learner #LearnerFirstNameAndSurname# (#IDPassport#) who completed for a non-merSETA scope qualification: #qualification# was reviewed and approved.</p>" 	
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The status of the learner will now reflect as "+nonSetaQualificationsCompletion.getStatus().getFriendlyName()+". </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "For any further information/queries, please do not hesitate to contact the merSETA Curriculum and Learning Programmes Unit or the Regional office for further assistance.</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours sincerely,</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Manager: Quality Assurance</p>" 
					+ "<br/>";
			
			welcome = welcome.replaceAll("#SDPNameAndSurname#",user.getFirstName() + " "+ user.getLastName());
			welcome = welcome.replaceAll("#CompanyName#", nonSetaQualificationsCompletion.getCompany().getCompanyName());
			
			welcome = welcome.replaceAll("#LearnerFirstNameAndSurname#",leaner.getFirstName() + " "+ leaner.getLastName());
			welcome = welcome.replaceAll("#IDPassport#", IDPassport);
			
			welcome = welcome.replaceAll("#qualification#", qual);
			GenericUtility.sendMail(user.getEmail(), "LEARNER COMPLETION FOR A NON-merSETA SCOPE QUALIFICATION OUTCOME: QUALIFICATION OBTAINED STATUS", welcome, null);
		}
	}
	public void sendRejectNotificationOfQualification(Company trainingProvider, NonSetaQualificationsCompletion nonSetaQualificationsCompletion, ArrayList<RejectReasons> rejectReasons) throws Exception {
		Users createuser = nonSetaQualificationsCompletion.getCreateUser();		
		CompanyLearners cl = CompanyLearnersService.instance().findByKey(nonSetaQualificationsCompletion.getCompanyLearners().getId());
		List<Users>users = findCompanyEmployerUsers(cl);
		users.add(createuser);		
		super.removeDuplicatesFromList(users);
		for(Users user:users) {
			String reasons = convertToHTML(rejectReasons);
			//Users user = nonSetaQualificationsCompletion.getCreateUser();
			Users leaner = nonSetaQualificationsCompletion.getCompanyLearners().getUser();
			String qual = getQualiDescription(nonSetaQualificationsCompletion.getCompanyLearners());
			String IDPassport = anIDNumber(leaner);
			String welcome = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " +"#SDPNameAndSurname#,</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA wishes to advise that the submission for #LearnerFirstNameAndSurname# (#IDPassport#) who completed for a non-merSETA scope qualification: #qualification# was reviewed and not approved for the following reason(s):</p>" 	
					+ reasons 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please review the submission. For any further information/queries, please do not hesitate to contact the merSETA Curriculum and Learning Programmes Unit or the Regional office for further assistance.  </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours sincerely,</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Manager: Quality Assurance</p>" 
					+ "<br/>";
			
			welcome = welcome.replaceAll("#SDPNameAndSurname#",user.getFirstName() + " "+ user.getLastName());
			welcome = welcome.replaceAll("#CompanyName#", nonSetaQualificationsCompletion.getCompany().getCompanyName());
			
			welcome = welcome.replaceAll("#LearnerFirstNameAndSurname#",leaner.getFirstName() + " "+ leaner.getLastName());
			welcome = welcome.replaceAll("#IDPassport#", IDPassport);
			
			welcome = welcome.replaceAll("#qualification#", qual);
			GenericUtility.sendMail(user.getEmail(), "LEARNER COMPLETION FOR A NON-merSETA SCOPE QUALIFICATION OUTCOME: SUBMISSION NOT APPROVED", welcome, null);
		}
	}
	
	public String getQualiDescription(CompanyLearners cl) {
		String desc = "";
		
		if (SKILLS_PROGRAM_LIST.contains(cl.getInterventionType().getId())) {
			desc = "("+cl.getSkillsProgram().getCode() + ") "+cl.getSkillsProgram().getDescription();
		}else if (SKILLS_SET_LIST.contains(cl.getInterventionType().getId())) {
			desc = "("+cl.getSkillsSet().getCode() + ") "+cl.getSkillsSet().getDescription();
		}else if (cl.getInterventionType().getId() ==HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
			desc = "("+cl.getUnitStandard().getCode() + ") "+cl.getUnitStandard().getTitle();
		}else if (cl.getInterventionType().getId() ==HAJConstants.NON_CREDIT_BEARING_SHORT_COURSE) {
			desc = "("+cl.getNonCredidBearingDescription()+")";
		}else if(cl.getQualification() != null) {
			desc = "("+cl.getQualification().getCode() + ") "+cl.getQualification().getDescription();
		}else {
			desc = "N/A";
		}
		
		return desc;
	}
	
	
	public String convertToHTML(List<RejectReasons> rejectReasons) {

		String sb = "<ul style=\"font-size:11.0pt;\";font-family:\"Arial\">";
		for (RejectReasons r : rejectReasons) {
			sb += "<li>" + r.getDescription() + "</li>";
		}
		sb += "</ul>";
		return sb;
	}
	
	public Company findUserByKey(Long id) throws Exception {
		return dao.findUserByKey(id);
	}

	public void resolveUnitStandarts(SummativeAssessmentReport summativeAssessmentReport) throws Exception {
		 List<SummativeAssessmentReportUnitStandards> list = summativeAssessmentReportUnitStandardsService.findBySummativeAssessmentReport(summativeAssessmentReport.getId());
		for(SummativeAssessmentReportUnitStandards l: list) {
			System.out.println("1");
		}
		summativeAssessmentReport.setUnitStandards(list);
	}

	public List<SummativeAssessmentReportUnitStandards> findBySummativeAssessmentRepor(SummativeAssessmentReport summativeAssessmentReport) throws Exception {
		if(summativeAssessmentReport!= null && summativeAssessmentReport.getId()!=null) {
			return summativeAssessmentReportUnitStandardsService.findBySummativeAssessmentReport(summativeAssessmentReport.getId());
		}else {
			return new ArrayList<SummativeAssessmentReportUnitStandards>();
		}
	}
	
	public NonSetaQualificationsCompletion findByKeyCompanyLearners(CompanyLearners companyLearners) throws Exception {
		return dao.findByKeyCompanyLearners(companyLearners.getId());
	}
	
	public List<NonSetaQualificationsCompletion> findByKeyCompanyLearner(CompanyLearners companyLearners) throws Exception {
		return dao.findByKeyCompanyLearner(companyLearners.getId());
	}
	
	public void uploadDocuments(NonSetaQualificationsCompletion entity, Tasks task, Users sessionUser) throws Exception {
		// check if all docs provided for correct permissions
		if (task != null && task.getProcessRole() != null && (task.getProcessRole().getRolePermission() == UserPermissionEnum.Upload || task.getProcessRole().getRolePermission() == UserPermissionEnum.EditUpload || task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalUploadApproval || task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalUpload || task.getProcessRole().getRolePermission() == UserPermissionEnum.UploadApprove)) {
			// prepareNewRegistration(task.getWorkflowProcess(), entity,
			// task.getProcessRole());
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) {
						doc.setData(docByte.getData());
					}
				}
			}

			// check if data not empty and creates document
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() == null && doc.getData() != null) {
					doc.setTargetKey(entity.getId());
					doc.setTargetClass(NonSetaQualificationsCompletion.class.getName());
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), sessionUser);
				} else {
					// if(doc.getConfigDoc().getConfigDocProcess().getConfigDocProcessEnumByValue(1).compareTo(doc.getConfigDoc().getProcessRoles().getRoleOrder())
					// )
					if (doc.getData() != null) {
						if (doc.getId() == null) {
							doc.setTargetKey(entity.getId());
							doc.setTargetClass(NonSetaQualificationsCompletion.class.getName());
							DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), sessionUser);
						}
					} else {
						throw new Exception("Please ensure all documents are uploaded");						
					}
				}
			}
		}
	}
	
	private String anIDNumber(Users user) {
		if (user.getRsaIDNumber() != null && user.getRsaIDNumber() != "" && !user.getRsaIDNumber().isEmpty()) {
			return user.getRsaIDNumber();
		} else if (user.getPassportNumber() != null && user.getPassportNumber() != "" && !user.getPassportNumber().isEmpty()) {
			return user.getPassportNumber();
		} else {
			return "N/A";
		}
	}
	
	public List<Users>findCompanyEmployerUsers(CompanyLearners cl) throws Exception{
		List<Users>list = new ArrayList<>();
		SDFCompany sdf=SDFCompanyService.instance().findPrimarySDF(cl.getEmployer());
		List<TrainingProviderApplication> trainingProviderApplications = TrainingProviderApplicationService.instance().findByCompany(cl.getCompany());
		if(trainingProviderApplications !=null && trainingProviderApplications.size()>0) {
			TrainingProviderApplication trainingProviderApplication = trainingProviderApplications.get(0);
			list.add(trainingProviderApplication.getUsers());
		}
		if(sdf !=null){
			Users empUser=sdf.getSdf();
			list.add(empUser);
		}
		return list;
	}
}
