package haj.com.service;

import java.util.ArrayList;
import java.util.List;

import haj.com.constants.HAJConstants;
import haj.com.dao.CompletionLetterDAO;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.CompletionLetter;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.ProcessRoles;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.SDFCompany;
import haj.com.entity.Tasks;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.framework.AbstractService;
import haj.com.utils.GenericUtility;

import java.util.Map;
import org.primefaces.model.SortOrder;

import static haj.com.constants.HAJConstants.SKILLS_PROGRAM_LIST;
import static haj.com.constants.HAJConstants.SKILLS_SET_LIST;

public class CompletionLetterService extends AbstractService {
	/** The dao. */
	private CompletionLetterDAO dao = new CompletionLetterDAO();
	private CompanyService companyService = new CompanyService();
	/**
	 * Get all CompletionLetter
 	 * @author TechFinium 
 	 * @see   CompletionLetter
 	 * @return a list of {@link haj.com.entity.CompletionLetter}
	 * @throws Exception the exception
 	 */
	public List<CompletionLetter> allCompletionLetter() throws Exception {
	  	return dao.allCompletionLetter();
	}


	/**
	 * Create or update CompletionLetter.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     CompletionLetter
	 */
	public void create(CompletionLetter entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  CompletionLetter.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    CompletionLetter
	 */
	public void update(CompletionLetter entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  CompletionLetter.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    CompletionLetter
	 */
	public void delete(CompletionLetter entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.CompletionLetter}
	 * @throws Exception the exception
	 * @see    CompletionLetter
	 */
	public CompletionLetter findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find CompletionLetter by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.CompletionLetter}
	 * @throws Exception the exception
	 * @see    CompletionLetter
	 */
	public List<CompletionLetter> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load CompletionLetter
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.CompletionLetter}
	 * @throws Exception the exception
	 */
	public List<CompletionLetter> allCompletionLetter(int first, int pageSize) throws Exception {
		return dao.allCompletionLetter(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of CompletionLetter for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the CompletionLetter
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(CompletionLetter.class);
	}
	
    /**
     * Lazy load CompletionLetter with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 CompletionLetter class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.CompletionLetter} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<CompletionLetter> allCompletionLetter(Class<CompletionLetter> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<CompletionLetter>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
	public List<CompletionLetter> findByCompanyLearner(CompanyLearners companyLearners) {
		return dao.findByCompanyLearner(companyLearners.getId());
	}
	
    /**
     * Get count of CompletionLetter for lazy load with filters
     * @author TechFinium 
     * @param entity CompletionLetter class
     * @param filters the filters
     * @return Number of rows in the CompletionLetter entity
     * @throws Exception the exception     
     */	
	public int count(Class<CompletionLetter> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public CompletionLetter findByKeyAndPopulateLearnerDocs(long id) throws Exception {
		 CompletionLetter completionLetter = dao.findByKey(id);
		 completionLetter.setCompanyLearners(CompanyLearnersService.instance().findByKey(completionLetter.getCompanyLearners().getId()));
		 if(completionLetter.getCompany()!=null) {
			 completionLetter.setCompany(companyService.findByKey(completionLetter.getCompany().getId()));
		 }	
		 if(completionLetter.getTrainingProvider()!=null) {
			 completionLetter.setTrainingProvider(companyService.findByKey(completionLetter.getTrainingProvider().getId()));
		 }
		 List<Doc>docs = DocService.instance().searchByTargetKeyAndClass(CompanyLearners.class.getName(), completionLetter.getCompanyLearners().getId(), ConfigDocProcessEnum.Learner);
		 completionLetter.getCompanyLearners().setDocs(docs);
		 return completionLetter;
	}
	
	public void prepareNewLearnershipDevelopmentRegistrationDocs(ConfigDocProcessEnum configDocProcess, CompletionLetter entityDoc, CompletionLetter entity, ProcessRoles processRoles) throws Exception {
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
	
	public void requestCompletionLetter(CompletionLetter completionLetter, Users user)throws Exception {
		completionLetter.setCreateUser(user);
		completionLetter.setStatus(ApprovalEnum.PendingApproval);
		if(completionLetter.getCompanyLearners()!=null && completionLetter.getCompanyLearners().getEmployer()!=null) {
			completionLetter.setTrainingProvider(completionLetter.getCompanyLearners().getEmployer());
		}
		dao.create(completionLetter);	
		TasksService.instance().findFirstInProcessAndCreateTask(user, completionLetter.getId(), completionLetter.getClass().getName(), ConfigDocProcessEnum.CompletionLetter, false);
	}
	
	public void completeWorkflow(CompletionLetter completionLetter, Users user, Tasks tasks) throws Exception {
		uploadDocuments(completionLetter, tasks, user);
		//this.dao.updateBatch((List<IDataEntity>) (List<?>) summativeAssessmentReportUnitStandards);
		completionLetter.setStatus(ApprovalEnum.PendingApproval);
		this.dao.update(completionLetter);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		sendNotificationOfQualification(completionLetter,  user);
	}
	
	public void approveWorkflow(CompletionLetter completionLetter, Users user, Tasks tasks) throws Exception {
		completionLetter.setStatus(ApprovalEnum.WaitingForManager);
		dao.update(completionLetter);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}
	
	public void rejectWorkflow(CompletionLetter companyLearners, Users user, Tasks tasks, ArrayList<RejectReasons> rejectReasons) throws Exception {
		companyLearners.setStatus(ApprovalEnum.Rejected);
		dao.update(companyLearners);
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearners.getId(), CompletionLetter.class.getName(), rejectReasons, user, ConfigDocProcessEnum.CompletionLetter);
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
	
	public void finalApproveWorkflow(CompletionLetter completionLetter, Users user, Tasks tasks) throws Exception {		
		CompanyLearners cl = completionLetter.getCompanyLearners();
		if(cl.getInterventionType().getId() == HAJConstants.NON_CREDIT_BEARING_SHORT_COURSE) {
			cl.setLearnerStatus(LearnerStatusEnum.Completed);
			completionLetter.setStatus(ApprovalEnum.Completed);
		}else {
			cl.setLearnerStatus(LearnerStatusEnum.Completed);
			completionLetter.setStatus(ApprovalEnum.Completed);
		}
		if(completionLetter.getAchievementDate()!= null) {
			cl.setDateQualificationObtained(completionLetter.getAchievementDate());
			cl.setCompletionDate(completionLetter.getAchievementDate());
			//cl.setDateLearnerCompleted(completionLetter.getAchievementDate());
			cl.setDateLearnerCompleted(getSynchronizedDate());
		}else {
			cl.setDateQualificationObtained(getSynchronizedDate());
			cl.setDateLearnerCompleted(getSynchronizedDate());
			cl.setCompletionDate(getSynchronizedDate());
		}		
		cl.setDateLearnerCompleted(getSynchronizedDate());
		CompanyLearnersService.instance().update(cl);
		completionLetter.setApprovalDate(getSynchronizedDate());
		dao.update(completionLetter);
		
		Company trainingProvider = findUserByKey(completionLetter.getTrainingProvider().getId());		
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		sendApprovaNotificationOfQualification(trainingProvider,completionLetter);		
	}
	
	public void finalRejectWorkflow(CompletionLetter completionLetter, Users user, Tasks tasks, ArrayList<RejectReasons> rejectReasons) throws Exception {
		completionLetter.setStatus(ApprovalEnum.Rejected);
		completionLetter.setApprovalDate(getSynchronizedDate());
		CompanyLearners cl = completionLetter.getCompanyLearners();
		cl.setLearnerStatus(LearnerStatusEnum.Registered);
		CompanyLearnersService.instance().update(cl);
		dao.update(completionLetter);
		TasksService.instance().completeTask(tasks);
		
		Company trainingProvider = findUserByKey(completionLetter.getTrainingProvider().getId());
		sendRejectNotificationOfQualification(trainingProvider,completionLetter, rejectReasons);
	}
	
	public void uploadDocuments(CompletionLetter entity, Tasks task, Users sessionUser) throws Exception {
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
					doc.setTargetClass(CompletionLetter.class.getName());
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), sessionUser);
				} else {
					// if(doc.getConfigDoc().getConfigDocProcess().getConfigDocProcessEnumByValue(1).compareTo(doc.getConfigDoc().getProcessRoles().getRoleOrder())
					// )
					if (doc.getData() != null) {
						if (doc.getId() == null) {
							doc.setTargetKey(entity.getId());
							doc.setTargetClass(CompletionLetter.class.getName());
							DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), sessionUser);
						}
					} else {
						throw new Exception("Please ensure all documents are uploaded");						
					}
				}
			}
		}
	}
	
	public void sendNotificationOfQualification(CompletionLetter completionLetter, Users createuser) throws Exception {
		CompanyLearners cl = CompanyLearnersService.instance().findByKey(completionLetter.getCompanyLearners().getId());
		List<Users>users = findCompanyEmployerUsers(cl);
		users.add(createuser);		
		super.removeDuplicatesFromList(users);
		for(Users user:users) {
			Users leaner = completionLetter.getCompanyLearners().getUser();
			
			String qual  = getQualiDescription(completionLetter.getCompanyLearners());
			String IDPassport = anIDNumber(leaner);
			String welcome = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " +"#SDPNameAndSurname#,</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA acknowledges receipt of the evidence submitted for learner #LearnerFirstNameAndSurname# (#IDPassport#) who has completed the following programme: #InterventionType# (#qualification#) for review.</p>" 	
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please be advised that the review process may take up to five days. The application will be evaluated by the merSETA and should any additional information be required, this will be communicated to you. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours sincerely,</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA Administration </p>" 
					+ "<br/>";
			
			welcome = welcome.replaceAll("#SDPNameAndSurname#",user.getFirstName() + " "+ user.getLastName());
			welcome = welcome.replaceAll("#CompanyName#", completionLetter.getCompany().getCompanyName());
			welcome = welcome.replaceAll("#InterventionType#", completionLetter.getCompanyLearners().getInterventionType().getDescription());		
			welcome = welcome.replaceAll("#LearnerFirstNameAndSurname#",leaner.getFirstName() + " "+ leaner.getLastName());
			welcome = welcome.replaceAll("#IDPassport#", IDPassport);		
			welcome = welcome.replaceAll("#qualification#", qual);
			GenericUtility.sendMail(user.getEmail(), "ACKNOWLEDGEMENT OF SUBMISSION OF A LEARNER COMPLETION FOR A NON-merSETA SCOPE QUALIFICATION", welcome, null);
		}
	}
	
	
	public void sendApprovaNotificationOfQualification(Company trainingProvider, CompletionLetter completionLetter) throws Exception {
		
		Users createuser = completionLetter.getCreateUser();		
		CompanyLearners cl = CompanyLearnersService.instance().findByKey(completionLetter.getCompanyLearners().getId());
		List<Users>users = findCompanyEmployerUsers(cl);
		users.add(createuser);		
		super.removeDuplicatesFromList(users);
		for(Users user:users) {
			//Users user = completionLetter.getCreateUser();
			Users leaner = completionLetter.getCompanyLearners().getUser();
			String qual = "";
		
			qual = getQualiDescription(completionLetter.getCompanyLearners());
			
			String IDPassport = anIDNumber(leaner);
			
			String welcome = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " +"#SDPNameAndSurname#,</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA acknowledges receipt of the evidence submitted for learner #LearnerFirstNameAndSurname# (#IDPassport#) who has completed the following programme: #InterventionType# #qualification# for review.</p>" 	
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The status of the learner will now reflect as "+completionLetter.getStatus().getFriendlyName()+". </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "For any further information/queries, please do not hesitate to contact the merSETA Curriculum and Learning Programmes Unit or the Regional office for further assistance.</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours sincerely,</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Manager: Quality Assurance</p>" 
					+ "<br/>";
			
			welcome = welcome.replaceAll("#SDPNameAndSurname#",user.getFirstName() + " "+ user.getLastName());
			welcome = welcome.replaceAll("#CompanyName#", completionLetter.getCompany().getCompanyName());
			welcome = welcome.replaceAll("#InterventionType#", completionLetter.getCompanyLearners().getInterventionType().getDescription());	
			welcome = welcome.replaceAll("#LearnerFirstNameAndSurname#",leaner.getFirstName() + " "+ leaner.getLastName());
			welcome = welcome.replaceAll("#IDPassport#", IDPassport);
			
			welcome = welcome.replaceAll("#qualification#", qual);
			GenericUtility.sendMail(user.getEmail(), "LEARNER COMPLETION FOR A NON-merSETA SCOPE QUALIFICATION OUTCOME: QUALIFICATION OBTAINED STATUS", welcome, null);
		}
	}
	
	public void sendRejectNotificationOfQualification(Company trainingProvider, CompletionLetter nonSetaQualificationsCompletion, ArrayList<RejectReasons> rejectReasons) throws Exception {
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
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA acknowledges receipt of the submission for learner #LearnerFirstNameAndSurname# (#IDPassport#) who has completed for a non-merSETA scope qualification: #qualification# was reviewed and not approved for the following reason(s):</p>" 	
					+ reasons 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please review the submission. For any further information/queries, please do not hesitate to contact the merSETA Curriculum and Learning Programmes Unit or the Regional office for further assistance.  </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours sincerely,</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Manager: Quality Assurance</p>" 
					+ "<br/>";
			
			welcome = welcome.replaceAll("#SDPNameAndSurname#",user.getFirstName() + " "+ user.getLastName());
			welcome = welcome.replaceAll("#CompanyName#", nonSetaQualificationsCompletion.getCompany().getCompanyName());
			welcome = welcome.replaceAll("#InterventionType#", nonSetaQualificationsCompletion.getCompanyLearners().getInterventionType().getDescription());
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
