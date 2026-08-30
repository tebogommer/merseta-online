package haj.com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.CompanyLearnersChangeDAO;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.CompanyLearnersChange;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.NonSetaQualificationsCompletion;
import haj.com.entity.ProcessRoles;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.Signoff;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.CreatedByEnum;
import haj.com.entity.enums.LearnerChangeTypeEnum;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.SignoffByEnum;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.Roles;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RolesService;
import haj.com.utils.GenericUtility;

public class CompanyLearnersChangeService extends AbstractService {
	/** The dao. */
	private CompanyLearnersChangeDAO dao = new CompanyLearnersChangeDAO();
	private ConfigDocService configDocService = new ConfigDocService();
	private CompanyLearnersService companyLearnersService = new CompanyLearnersService();
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
	private RolesService rolesService = new RolesService();
	
	/** Instance of service level */
	private static CompanyLearnersChangeService companyLearnersChangeService;
	public static synchronized CompanyLearnersChangeService instance() {
		if (companyLearnersChangeService == null) {
			companyLearnersChangeService = new CompanyLearnersChangeService();
		}
		return companyLearnersChangeService;
	}
	
	/**
	 * Populates additional information against CompanyLearnersChange
	 * refer to method: populateAdditionalInformation
	 * @param companyLearnersChangeList
	 * @return companyLearnersChangeList
	 * @throws Exception
	 */
	public List<CompanyLearnersChange> populateAdditionalInformationList(List<CompanyLearnersChange> companyLearnersChangeList) throws Exception{
		for (CompanyLearnersChange companyLearnersChange : companyLearnersChangeList) {
			populateAdditionalInformation(companyLearnersChange);
		}
		return companyLearnersChangeList;
	}
	
	/**
	 * Populates additional information against CompanyLearnersChange
	 * @param companyLearnersChange
	 * @return companyLearnersChange
	 * @throws Exception
	 */
	private CompanyLearnersChange populateAdditionalInformation(CompanyLearnersChange companyLearnersChange) throws Exception{
		if (companyLearnersChange.getCompanyLearners() != null && companyLearnersChange.getCompanyLearners().getId() != null) {
			companyLearnersChange.setCompanyLearners(companyLearnersService.findByKey(companyLearnersChange.getCompanyLearners().getId())) ;
		}
		return companyLearnersChange;
	}

	/**
	 * Get all CompanyLearnersChange
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersChange
	 * @return a list of {@link haj.com.entity.CompanyLearnersChange}
	 * @throws Exception
	 *             the exception
	 */
	public List<CompanyLearnersChange> allCompanyLearnersChange() throws Exception {
		return populateAdditionalInformationList(dao.allCompanyLearnersChange());
	}

	/**
	 * Create or update CompanyLearnersChange.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersChange
	 */
	public void create(CompanyLearnersChange entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update CompanyLearnersChange.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersChange
	 */
	public void update(CompanyLearnersChange entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete CompanyLearnersChange.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersChange
	 */
	public void delete(CompanyLearnersChange entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.CompanyLearnersChange}
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersChange
	 */
	public CompanyLearnersChange findByKey(long id) throws Exception {
		return populateAdditionalInformation(dao.findByKey(id));
	}

	public void prepareNewRegistration(CompanyLearnersChange entity,ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcessEnum));
			List<ConfigDoc> l = configDocService.findByProcessNotUploaded(entity.getClass().getName(), entity.getId(), configDocProcessEnum, CompanyUserTypeEnum.User);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = configDocService.findByProcess(ConfigDocProcessEnum.LearnerLostTime, CompanyUserTypeEnum.User);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}
	
	public void prepareNewLearnershipDevelopmentRegistrationDocs(ConfigDocProcessEnum configDocProcess, CompanyLearnersChange entityDoc, CompanyLearnersChange entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null) {
			entityDoc.setDocs(DocService.instance().searchByClassAndKey( entity.getId(), entity.getClass().getName()));
			if (processRoles.getCompanyUserType() == null) {
				entityDoc.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			}else {
				entityDoc.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), processRoles));
			}

			List<ConfigDoc> l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), processRoles);

			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entityDoc.getDocs().add(new Doc(a));
				});
			}
			
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
	
	public void prepareCompanyLearnersChangeDocs(ConfigDocProcessEnum configDocProcess, CompanyLearnersChange entityDoc, ProcessRoles processRoles) throws Exception {
		entityDoc.setDocs(DocService.instance().searchByClassAndKeyConfigDocNotNull( entityDoc.getId(), entityDoc.getClass().getName()));
		if (processRoles.getCompanyUserType() == null) {
			entityDoc.setDocs(DocService.instance().searchByTargetKeyAndClass(entityDoc.getClass().getName(), entityDoc.getId(), configDocProcess));
		}else {
			entityDoc.setDocs(DocService.instance().searchByTargetKeyAndClass(entityDoc.getClass().getName(), entityDoc.getId(), processRoles));
		}

		List<ConfigDoc> l = ConfigDocService.instance().findByProcessRolesNotUploaded(entityDoc.getClass().getName(), entityDoc.getId(), processRoles);

		if (l != null && l.size() > 0) {
			l.forEach(a -> {
				entityDoc.getDocs().add(new Doc(a));
			});
		}
	}

	/**
	 * Find CompanyLearnersChange by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.CompanyLearnersChange}
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersChange
	 */
	public List<CompanyLearnersChange> findByName(String desc) throws Exception {
		return populateAdditionalInformationList(dao.findByName(desc));
	}

	/**
	 * Lazy load CompanyLearnersChange
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.CompanyLearnersChange}
	 * @throws Exception
	 *             the exception
	 */
	public List<CompanyLearnersChange> allCompanyLearnersChange(int first, int pageSize) throws Exception {
		return populateAdditionalInformationList(dao.allCompanyLearnersChange(Long.valueOf(first), pageSize));
	}

	/**
	 * Get count of CompanyLearnersChange for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the CompanyLearnersChange
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(CompanyLearnersChange.class);
	}

	/**
	 * Lazy load CompanyLearnersChange with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            CompanyLearnersChange class
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
	 * @return a list of {@link haj.com.entity.CompanyLearnersChange} containing
	 *         data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersChange> allCompanyLearnersChange(Class<CompanyLearnersChange> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateAdditionalInformationList((List<CompanyLearnersChange>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters));

	}

	/**
	 * Get count of CompanyLearnersChange for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            CompanyLearnersChange class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the CompanyLearnersChange entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<CompanyLearnersChange> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public void completeCompanyLearners(CompanyLearnersChange companyLearnersChange, Users user, Tasks tasks) throws Exception {
		String error = "";
		
		if (companyLearnersChange.getDocs() != null) {
			for (Doc doc : companyLearnersChange.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) doc.setData(docByte.getData());
				}
				if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
					error += "Upload " + doc.getConfigDoc().getName() + " for " + companyLearnersChange.getCompanyLearners().getUser().getFirstName() + " " + companyLearnersChange.getCompanyLearners().getUser().getLastName();
				}
			}
		}
		if (error.length() > 0) throw new ValidationException(error);

		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}
	
	public void completeCompanyLearnersELearner(CompanyLearnersChange companyLearnersChange, Users user, Tasks tasks) throws Exception {
		if(tasks.getFirstInProcess()) {
			String error = "";
			
			if (companyLearnersChange.getDocs() != null) {
				for (Doc doc : companyLearnersChange.getDocs()) {
					if (doc.getId() != null) {
						DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
						if (docByte != null) doc.setData(docByte.getData());
					}
					if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
						error += "Upload " + doc.getConfigDoc().getName() + " for " + companyLearnersChange.getCompanyLearners().getUser().getFirstName() + " " + companyLearnersChange.getCompanyLearners().getUser().getLastName();
					}
				}
			}
			if (error.length() > 0) throw new ValidationException(error);
			companyLearnersChange.setSignoffByEnum(SignoffByEnum.Learner);
			update(companyLearnersChange);
			CompanyLearners companyLearners = CompanyLearnersService.instance().findByKey(companyLearnersChange.getCompanyLearners().getId());
			List<Users>users = new ArrayList<>();
			users.add(companyLearners.getUser());
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, users, false);
		}else {
			companyLearnersChange.setSignoffByEnum(SignoffByEnum.Completed);
			update(companyLearnersChange);
			CompanyLearners companyLearners = CompanyLearnersService.instance().findByKey(companyLearnersChange.getCompanyLearners().getId());
			List<Users> userList = findRegionClinetServiceAdministrator(companyLearners.getCompany());
			if (userList.size() == 0) {
				throw new Exception("No Client Service Administrator assigned to region");
			}
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
			//TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		}
	}

	public void approveCompanyLearners(CompanyLearnersChange companyLearners, Users user, Tasks tasks) throws Exception {
		companyLearners.setStatus(ApprovalEnum.WaitingForManager);
		dao.update(companyLearners);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}

	public void rejectCompanyLearners(CompanyLearnersChange companyLearners, Users user, Tasks tasks,  List<RejectReasons> rejectReasons) throws Exception {
		companyLearners.setStatus(ApprovalEnum.Rejected);
		dao.update(companyLearners);
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearners.getId(), CompanyLearnersChange.class.getName(), rejectReasons, user, ConfigDocProcessEnum.LearnerChange);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		
		List<Users>users = new ArrayList<>();
		users.add(companyLearners.getCreateUser());
		TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, users);
		sendRejectionNotification(companyLearners, user,rejectReasons);
	}
	
	public void rejectCompanyLearnersEchange(CompanyLearnersChange companyLearners, Users user, Tasks tasks,  List<RejectReasons> rejectReasons) throws Exception {
		if(companyLearners.getCreatedByEnum() == CreatedByEnum.merSETA) {
			companyLearners.setSignoffByEnum(SignoffByEnum.merSETA);
		}else if(companyLearners.getCreatedByEnum() == CreatedByEnum.sdp) {
			companyLearners.setSignoffByEnum(SignoffByEnum.sdp);
		}else if(companyLearners.getCreatedByEnum() == CreatedByEnum.sdf) {
			companyLearners.setSignoffByEnum(SignoffByEnum.sdf);
		}
		companyLearners.setStatus(ApprovalEnum.Rejected);
		dao.update(companyLearners);
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearners.getId(), CompanyLearnersChange.class.getName(), rejectReasons, user, tasks.getWorkflowProcess());
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		
		List<Users>users = new ArrayList<>();
		users.add(companyLearners.getCreateUser());
		
		CompanyLearners cl = CompanyLearnersService.instance().findByKey(companyLearners.getCompanyLearners().getId());
		String description = "The amendment request for "+cl.getUser().getFirstName()+ " " +cl.getUser().getLastName()+"("+ anIDNumber(cl.getUser())+","+ cl.getUser().getEmail()+")" +" has not been approved. Please process.";		
		TasksService.instance().completeTask(tasks);
		TasksService.instance().findPreviousInProcessAndCreateFirstInProcessTask(user,  companyLearners.getId(), companyLearners.getClass().getName(), tasks.getWorkflowProcess(), false, description);
		//TasksService.instance().findFirstInProcessAndCreateTask(description, user, companyLearners.getId(), companyLearners.getClass().getName(), true, ConfigDocProcessEnum.ELearnerChange, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, user.getFirstName()), users);
		//TasksService.instance().findFirstInProcessAndCreateTask(user, companyLearners.getId(), companyLearners.getClass().getName(), ConfigDocProcessEnum.ELearnerChange, false);
		sendRejectionNotification(companyLearners, user,rejectReasons);
		updateSignoffs(companyLearners);
	}

	public void updateSignoffs(CompanyLearnersChange companyLearners) throws Exception {
		List<Signoff>signOffs = SignoffService.instance().findByTargetKeyAndClass(companyLearners.getId(), companyLearners.getClass().getName());
		for(Signoff s:signOffs) {
			s.setAccept(false);
			SignoffService.instance().update(s);
		}
	}
	
	public void finalApproveCompanyLearners(CompanyLearnersChange companyLearners, Users user, Tasks tasks) throws Exception {

		companyLearners.setStatus(ApprovalEnum.Approved);
		companyLearners.setApprovalDate(getSynchronizedDate());
		companyLearners.setApprovalUser(user);
		dao.update(companyLearners);

		CompanyLearners cl = companyLearners.getCompanyLearners();
		cl.setStatus(ApprovalEnum.Approved);
		cl.setLearnerStatus(companyLearners.getLearnerStatus());
		if (companyLearners.getLearnerChangeTypeEnum() == LearnerChangeTypeEnum.ChangeCommencementDate) {
			cl.setCommencementDate(companyLearners.getCommencmentDate());
			cl.setCompletionDate(companyLearners.getCompletionDate());
		} 
		if (companyLearners.getLearnerChangeTypeEnum() == LearnerChangeTypeEnum.ChangeNonCreditBearingTitle) {
			cl.setNonCredidBearingDescription(companyLearners.getNonCredidBearingDescription());
		}else {
			
			if(companyLearners.getSkillsProgram() !=null){
				cl.setSkillsProgram(companyLearners.getSkillsProgram());
				if(cl.getQualification()!=null) {
					cl.setQualification(companyLearners.getSkillsProgram().getQualification());
				}
			}
			
			if(companyLearners.getSkillsSet() !=null){
				cl.setSkillsSet(companyLearners.getSkillsSet());
				if(cl.getQualification()!=null) {
					cl.setQualification(companyLearners.getSkillsSet().getQualification());
				}
			}
			
			if(companyLearners.getUnitStandard() !=null){
				cl.setUnitStandard(companyLearners.getUnitStandard());
				cl.setQualification(companyLearners.getQualification());
			}
			
			if(companyLearners.getQualification() !=null){
				cl.setQualification(companyLearners.getQualification());
			}
			
			if(companyLearners.getLearnership() !=null){
				cl.setLearnership(companyLearners.getLearnership());
				if(cl.getQualification()!=null) {
					cl.setQualification(companyLearners.getLearnership().getQualification());
				}
			}
		}
		dao.update(cl);
		TasksService.instance().completeTask(tasks);
		List<Users>users = new ArrayList<>();
		users.add(companyLearners.getCreateUser());
		users.add(companyLearners.getCompanyLearners().getUser());
		users.add(getCLO(companyLearners.getCompanyLearners().getCompany()));
		users=super.removeDuplicatesFromList(users);
		for(Users sendToUser: users) {
			sendApprovalNotification(companyLearners, sendToUser);
		}		
	}

	public void finalRejectCompanyLearners(CompanyLearnersChange companyLearners, Users user, Tasks tasks,List<RejectReasons> rejectReasons) throws Exception {
		companyLearners.setStatus(ApprovalEnum.Rejected);
		companyLearners.setApprovalDate(getSynchronizedDate());
		dao.update(companyLearners);
		
		CompanyLearners cl = CompanyLearnersService.instance().findByKey(companyLearners.getCompanyLearners().getId());
		cl.setLearnerStatus(companyLearners.getLearnerStatus());
		CompanyLearnersService.instance().update(cl);
		
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearners.getId(), CompanyLearnersChange.class.getName(), rejectReasons, user, ConfigDocProcessEnum.LearnerChange);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		TasksService.instance().completeTask(tasks);
		List<Users>users = new ArrayList<>();
		users.add(companyLearners.getCreateUser());
		users.add(companyLearners.getCompanyLearners().getUser());
		users.add(getCLO(companyLearners.getCompanyLearners().getCompany()));
		users=super.removeDuplicatesFromList(users);
		for(Users u:users) {
			sendRejectionNotification(companyLearners, u,rejectReasons);
		}
	}
	
	public void sendApprovalNotification(CompanyLearnersChange companyLearnersChange, Users u) throws Exception {
			String endDate = "";
			String subject = "APPROVAL NOTIFICATION";
			if(companyLearnersChange.getCompletionDate() != null) {
				endDate = HAJConstants.sdfDDMMYYYY2.format(companyLearnersChange.getCompletionDate());
			}		 
			CompanyLearners companyLearners = CompanyLearnersService.instance().findByKey(companyLearnersChange.getCompanyLearners().getId());
			String mssg="";
			switch (companyLearnersChange.getLearnerChangeTypeEnum()) {
			case ChangeCommencementDate:
				subject="AGREEMENT COMMENCEMENT DATE AMENDMENT OUTCOME: APPROVED";
				mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + u.getFirstName() + " " + u.getLastName() + " </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+"The merSETA hereby advises that the request for change in commencement date for the following learner: "
						+ companyLearners.getUser().getFirstName() + " " + companyLearners.getUser().getLastName() + " (" + anIDNumber( companyLearners.getUser()) + ") "
						+ "has been approved."
						+ "</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" 
						+ "Please be advised that the new agreement end date is: "+endDate+ "</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" 
						+ "For any assistance, please contact your Regional Office or merSETA Head Office." 
						+ "</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Your sincerely,"
						+ "</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "merSETA Administration"
						+ "</p>";
				break;
			case ChangeOfProgramQalification:
				subject="PROGRAMME AMENDMENT OUTCOME: APPROVED";
				String qualificationCode = "";
				String qualificationDecsription = "";
				if(companyLearnersChange.getQualification() != null) {
					qualificationCode=companyLearnersChange.getQualification().getCodeString();
					qualificationDecsription=companyLearnersChange.getQualification().getDescription();
				}else if (companyLearnersChange.getLearnership() != null) {
					qualificationCode=companyLearnersChange.getLearnership().getCode();
					if(companyLearnersChange.getLearnership().getDescription() != null) {
						qualificationDecsription=companyLearnersChange.getLearnership().getDescription();
					}else if(companyLearnersChange.getLearnership().getQualification() != null) {
						qualificationDecsription = companyLearnersChange.getLearnership().getQualification().getDescription();
					}					
				}else if (companyLearnersChange.getSkillsProgram() !=null) {
					qualificationCode=companyLearnersChange.getSkillsProgram().getProgrammeID();
					if(companyLearnersChange.getSkillsProgram().getDescription() != null) {
						qualificationDecsription=companyLearnersChange.getSkillsProgram().getDescription();
					}					
				}else if (companyLearnersChange.getSkillsSet()!=null) {
					qualificationCode=companyLearnersChange.getSkillsSet().getProgrammeID();
					if(companyLearnersChange.getSkillsSet().getDescription() != null) {
						qualificationDecsription=companyLearnersChange.getSkillsSet().getDescription();
					}					
				}else if (companyLearnersChange.getUnitStandard() != null) {
					qualificationCode=companyLearnersChange.getUnitStandard().getCodeString();
					if(companyLearnersChange.getUnitStandard().getTitle()!= null) {
						qualificationDecsription=companyLearnersChange.getUnitStandard().getTitle();
					}					
				}
				 mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + u.getFirstName() + " " + u.getLastName() + " </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+"The merSETA hereby advises that the request for change in qualification for the following learner: "
						+ ""+ companyLearners.getUser().getFirstName() + " " + companyLearners.getUser().getLastName() + " (" + anIDNumber( companyLearners.getUser()) + ") "
						+ "has been approved.</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" 
						+ "Please be advised that the learner is now registered for the following qualification : ("+qualificationCode+") "+qualificationDecsription+""
						+ "</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" 
						+ "For any assistance, please contact your Regional Office or merSETA Head Office." 
						+ "</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Your sincerely,</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "merSETA Administration"
						+ "</p>";
				break;
			case ChangeOfProgramTrade:
				subject="TRADE AMENDMENT OUTCOME: APPROVED";
			    mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + u.getFirstName() + " " + u.getLastName() + " </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "The merSETA hereby advises that the request for change in trade for the following learner: "
						+ companyLearners.getUser().getFirstName() + " " + companyLearners.getUser().getLastName() + " (" + anIDNumber( companyLearners.getUser()) + ") "
						+ "has been approved."
						+ "</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" 
						+ "Please be advised that the learner is now registered for the following trade: ("+companyLearnersChange.getQualification().getCode()+") "+companyLearnersChange.getQualification().getDescription()+""
						+"</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" 
						+ "For any assistance, please contact your Regional Office or merSETA Head Office." 
						+ "</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Your sincerely,"
						+ "</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">merSETA Administration</p>";
			    break;
			case ChangeNonCreditBearingTitle:
				subject="NON-CREDIT BEARING PROGRAMME AMENDMENT OUTCOME: APPROVED";
			    mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + u.getFirstName() + " " + u.getLastName() + " </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "The merSETA hereby advises that the request for change in non-credit bearing programme for the following learner: "
						+ companyLearners.getUser().getFirstName() + " " + companyLearners.getUser().getLastName() + " (" + anIDNumber( companyLearners.getUser()) + ") "
						+ "has been approved."
						+ "</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" 
						+ "Please be advised that the learner is now registered for the following programme: "+companyLearnersChange.getNonCredidBearingDescription()+""
						+"</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" 
						+ "For any assistance, please contact your Regional Office or merSETA Head Office." 
						+ "</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Your sincerely,"
						+ "</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">merSETA Administration</p>";
			    break;
			default:
				break;
			}
			
			GenericUtility.sendMail(u.getEmail(), subject, mssg, null);
	}
	
	public void sendRejectionNotification(CompanyLearnersChange companyLearnersChange, Users u,List<RejectReasons> rejectReasons) throws Exception {
		CompanyLearners companyLearners = CompanyLearnersService.instance().findByKey(companyLearnersChange.getCompanyLearners().getId());
		String mainMessage = "";
		String subject = "REJECTION NOTIFICATION";
		String rejectReason=convertToHTML(rejectReasons);
		switch (companyLearnersChange.getLearnerChangeTypeEnum()) {
		case ChangeCommencementDate:
			subject="AGREEMENT COMMENCEMENT DATE AMENDMENT OUTCOME: NOT APPROVED";
			mainMessage = "The merSETA hereby advises that the request for change in commencement date for the following learner: ";
			break;
		case ChangeOfProgramQalification:
			subject="PROGRAMME AMENDMENT OUTCOME: NOT APPROVED";
			mainMessage = "The merSETA hereby advises that the request for change in qualification for the following learner: ";
			break;
		case ChangeOfProgramTrade:
			subject="TRADE AMENDMENT OUTCOME: NOT APPROVED";
			mainMessage = "The merSETA hereby advises that the request for change in trade for the following learner: ";
		break;
		case ChangeNonCreditBearingTitle:
			subject="NON CREDIT BEARING AMENDMENT OUTCOME: NOT APPROVED";
			mainMessage = "The merSETA hereby advises that the request for change in Non Credit Bearing Title for the following learner: ";
		break;

		default:
			break;
		}
		
		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + u.getFirstName() + " " + u.getLastName() + " </p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" 
				+ mainMessage + companyLearners.getUser().getFirstName() + " " + companyLearners.getUser().getLastName() + " (" + anIDNumber( companyLearners.getUser()) + ") "
				+ "has not been approved for the following reason(s): "
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ ""+rejectReason+"" 
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" 
				+ "For any assistance or queries, please contact your Regional Office or merSETA Head Office." 
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Your sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "merSETA Administration"
				+ "</p>";
		GenericUtility.sendMail(u.getEmail(), subject, mssg, null);
}
	
	private String anIDNumber(Users user) {
		if (user.getRsaIDNumber() != null && user.getRsaIDNumber() != "" && !user.getRsaIDNumber().isEmpty()) {
			return user.getRsaIDNumber();
		}else if (user.getPassportNumber() != null && user.getPassportNumber() != "" && !user.getPassportNumber().isEmpty()) {
			return user.getPassportNumber();
		}else {
			return "N/A";
		}
	}
	
	public String convertToHTML(List<RejectReasons> rejectReasons) {

		String sb =  "<ul style=\"font-size:11.0pt;\";font-family:\"Arial\">" ;
		for (RejectReasons r : rejectReasons) {
			sb += "<li>" + r.getDescription() + "</li>";
		}
		sb += "</ul>";
		return sb;
	}
	
	public Users getCLO(Company company) throws Exception {
		Users cloUser = new Users();
		if (company.getResidentialAddress() != null && company.getResidentialAddress().getTown() != null) {
			RegionTown rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
			cloUser = rt.getClo().getUsers();
		}
		return cloUser;
	}

	public void downloadLPMAD005(CompanyLearnersChange companyLearnersChange) throws Exception {
		UsersService usersService = new UsersService();
		Map<String, Object> params = new HashMap<String, Object>();
		String qualificationChange = "";
		if (companyLearnersChange.getCompanyLearners().getQualification() != null && companyLearnersChange.getQualification() != null) {
			qualificationChange = "<b>Current:</b> (" + companyLearnersChange.getCompanyLearners().getQualification().getCode() + ") " + companyLearnersChange.getCompanyLearners().getQualification().getDescription() + " <br/>" + "<b>Amend To: </b> (" + companyLearnersChange.getQualification().getCode() + ") " + companyLearnersChange.getQualification().getDescription();
		}
		Users learner = usersService.findByKey(companyLearnersChange.getCompanyLearners().getUser().getId());
		String learnerRegNum = "";
		if (companyLearnersChange.getCompanyLearners().getRegistrationNumber() != null) {
			learnerRegNum = companyLearnersChange.getCompanyLearners().getRegistrationNumber();
		}

		if (companyLearnersChange.getCompanyLearners().getRegistrationNumber() != null) {
			learnerRegNum = companyLearnersChange.getCompanyLearners().getRegistrationNumber();
		}

		params.put("company_id", companyLearnersChange.getCompanyLearners().getEmployer().getId());
		params.put("learner_id", companyLearnersChange.getCompanyLearners().getUser().getId());
		
		if(companyLearnersChange.getQualification() != null) {
			params.put("qual_id", companyLearnersChange.getQualification().getId());
		}else if(companyLearnersChange.getLearnership() != null) {
			params.put("qual_id", companyLearnersChange.getLearnership().getQualification().getId());
		}

		params.put("learner_reg_num", learnerRegNum);
		SignoffService  signoffService = new SignoffService();
		List<Signoff>signOffs = signoffService.findByTargetKeyAndClass(companyLearnersChange.getId(), companyLearnersChange.getClass().getName());
		if(signOffs.size()>0) {
			Signoff signoff = signOffs.get(0);
			if(signoff != null && signoff.getAccept()) {
				params.put("signoff_initials", populateInitial(signoff.getUser()));
				params.put("signoff", signoff);
			}
			Signoff learner_signoff = signOffs.get(1);
			if(learner_signoff != null && learner_signoff.getAccept()) {
				params.put("learner_signoff_initials", populateInitial(learner_signoff.getUser()));
				params.put("learner_signoff",learner_signoff);
			}
		}
		
		JasperService.addLogo(params);

		JasperService js = new JasperService();
		js.createReportFromDBtoServletOutputStream("LPM-AD-005-AddendumToAmendQualificationSignoff.jasper", params, learner.getFirstName()+"_"+learner.getLastName()+"_"+"AggrementForm.pdf");
	}

	public void downloadLPMAD010(CompanyLearnersChange companyLearnersChange) throws Exception {
		UsersService usersService = new UsersService();
		Map<String, Object> params = new HashMap<String, Object>();

		Users learner = usersService.findByKey(companyLearnersChange.getCompanyLearners().getUser().getId());

		String learnerRegNum = "";
		String trade = "";
		if (companyLearnersChange != null && companyLearnersChange.getQualification() != null && companyLearnersChange.getQualification().getDescription() != null) {
			trade = companyLearnersChange.getQualification().getDescription();
		}

		if (companyLearnersChange.getCompanyLearners().getRegistrationNumber() != null) {
			learnerRegNum = companyLearnersChange.getCompanyLearners().getRegistrationNumber();
		}
		params.put("company_id", companyLearnersChange.getCompanyLearners().getEmployer().getId());
		params.put("learner_id", companyLearnersChange.getCompanyLearners().getUser().getId());
		params.put("registration_number", learnerRegNum);
		params.put("trade", trade);
		SignoffService  signoffService = new SignoffService();
		List<Signoff>signOffs = signoffService.findByTargetKeyAndClass(companyLearnersChange.getId(), companyLearnersChange.getClass().getName());
		if(signOffs.size()>0) {
			Signoff signoff = signOffs.get(0);
			if(signoff != null && signoff.getAccept()) {
				params.put("signoff_initials", populateInitial(signoff.getUser()));
				params.put("signoff", signoff);
			}
			Signoff learner_signoff = signOffs.get(1);
			if(learner_signoff != null && learner_signoff.getAccept()) {
				params.put("learner_signoff_initials", populateInitial(learner_signoff.getUser()));
				params.put("learner_signoff",learner_signoff);
			}
		}
		
		JasperService.addLogo(params);


		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Change_of_Programme_Letter.pdf";
		JasperService js = new JasperService();
		js.createReportFromDBtoServletOutputStream("LPM-AD-010-AddendumToAmendATradeSignoff.jasper", params, fileName);
	}

	public void downloadLPMAD001(CompanyLearnersChange companyLearnersChange) throws Exception {
		UsersService usersService = new UsersService();
		Map<String, Object> params = new HashMap<String, Object>();

		Users learner = usersService.findByKey(companyLearnersChange.getCompanyLearners().getUser().getId());
		String commencementDate = HAJConstants.sdf.format(companyLearnersChange.getCommencmentDate());
		String endDate = HAJConstants.sdf.format(companyLearnersChange.getCompletionDate());// To be Confirmed

		if (companyLearnersChange.getCompanyLearners().getRegistrationNumber() != null) {
			companyLearnersChange.getCompanyLearners().getRegistrationNumber();
		}
		
		params.put("registration_number", companyLearnersChange.getCompanyLearners().getRegistrationNumber());
		params.put("company_id", companyLearnersChange.getCompanyLearners().getCompany().getId());
		params.put("learner_id", companyLearnersChange.getCompanyLearners().getUser().getId());
		params.put("commencement_date", commencementDate);
		params.put("end_date", endDate);
		params.put("company_name", companyLearnersChange.getCompanyLearners().getCompany().getCompanyName());
		SignoffService  signoffService = new SignoffService();
		List<Signoff>signOffs = signoffService.findByTargetKeyAndClass(companyLearnersChange.getId(), companyLearnersChange.getClass().getName());
		if(signOffs.size()>0) {
			Signoff signoff = signOffs.get(0);
			if(signoff != null && signoff.getAccept()) {
				params.put("signoff_initials", populateInitial(signoff.getUser()));
				params.put("signoff", signoff);
			}
			Signoff learner_signoff = signOffs.get(1);
			if(learner_signoff != null && learner_signoff.getAccept()) {
				params.put("learner_signoff_initials", populateInitial(learner_signoff.getUser()));
				params.put("learner_signoff",learner_signoff);
			}
		}
		
		JasperService.addLogo(params);


		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Change_of_Programme _Letter.pdf";

		JasperService js = new JasperService();
		js.createReportFromDBtoServletOutputStream("LPM-AD-001-Addendum-LearnershipAgreement-Signoff.jasper", params, fileName);
	}

	public void downloadLPMAD005NonCreditBearingEmail(CompanyLearnersChange companyLearnersChange) throws Exception {
		UsersService usersService = new UsersService();
		Map<String, Object> params = new HashMap<String, Object>();
		String qualificationChange = "";
		if (companyLearnersChange.getNonCredidBearingDescription() != null) {
			qualificationChange = "<b>Current:</b> " + companyLearnersChange.getCompanyLearners().getNonCredidBearingDescription() + " <br/>" + "<b>Amend To: </b> " + companyLearnersChange.getNonCredidBearingDescription() + " </b>";
		}
		Users learner = usersService.findByKey(companyLearnersChange.getCompanyLearners().getUser().getId());
		String learnerRegNum = "";
		if (companyLearnersChange.getCompanyLearners().getRegistrationNumber() != null) {
			learnerRegNum = companyLearnersChange.getCompanyLearners().getRegistrationNumber();
		}

		if (companyLearnersChange.getCompanyLearners().getRegistrationNumber() != null) {
			learnerRegNum = companyLearnersChange.getCompanyLearners().getRegistrationNumber();
		}

		params.put("company_id", companyLearnersChange.getCompanyLearners().getEmployer().getId());
		params.put("learner_id", companyLearnersChange.getCompanyLearners().getUser().getId());
		params.put("NonCreditBearingTitle", companyLearnersChange.getNonCredidBearingDescription());
		params.put("learner_reg_num", learnerRegNum);
		SignoffService  signoffService = new SignoffService();
		List<Signoff>signOffs = signoffService.findByTargetKeyAndClass(companyLearnersChange.getId(), companyLearnersChange.getClass().getName());
		if(signOffs.size()>0) {
			Signoff signoff = signOffs.get(0);
			if(signoff != null && signoff.getAccept()) {
				params.put("signoff_initials", populateInitial(signoff.getUser()));
				params.put("signoff", signoff);
			}
			Signoff learner_signoff = signOffs.get(1);
			if(learner_signoff != null && learner_signoff.getAccept()) {
				params.put("learner_signoff_initials", populateInitial(learner_signoff.getUser()));
				params.put("learner_signoff",learner_signoff);
			}
		}
		
		JasperService.addLogo(params);
	
		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Change_of_Non_Credit_Bearing_Title _Letter.pdf";

		JasperService js = new JasperService();
		js.createReportFromDBtoServletOutputStream("LPM-AD-005-AddendumToAmendNonCreditBearingTitleSignoff.jasper", params, fileName);
	}
	
	private String populateInitial(Users user) {
		String inStringForm = "";
		if(user != null) {
			inStringForm = inStringForm + user.getFirstName().trim().charAt(0) + user.getLastName().trim().charAt(0);
		}
		return inStringForm;
	}
	
	public List<Users> findRegionClinetServiceAdministrator(Company company) throws Exception {
		List<Users> list = new ArrayList<>();
		if(company != null && company.getResidentialAddress() != null){
			Roles roles = rolesService.findByKey(HAJConstants.CLIENT_SERVICE_ADMIN_ROLE_ID);
			list = hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegionAndRole(company.getResidentialAddress().getTown(), roles);
		}
		return list;
	}
}
