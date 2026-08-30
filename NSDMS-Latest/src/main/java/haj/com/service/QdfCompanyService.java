package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.QdfCompanyDAO;
import haj.com.entity.Company;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.ProcessRoles;
import haj.com.entity.QdfCompany;
import haj.com.entity.QualificationsCurriculumDevelopment;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.SDFCompany;
import haj.com.entity.ScheduledEvent;
import haj.com.entity.ScheduledEventUsers;
import haj.com.entity.Sites;
import haj.com.entity.SkillsRegistrationUnitStandards;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.WorkPlaceApproval;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.QCDTemplateTypeEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.JobTitleService;
import haj.com.service.lookup.QualificationService;
import haj.com.service.lookup.RegionService;
import haj.com.service.lookup.RegionTownService;
import haj.com.utils.GenericUtility;

public class QdfCompanyService extends AbstractService {
	/** The dao. */
	private QdfCompanyDAO dao = new QdfCompanyDAO();

	/** Service Layers */
	private RegisterService registerService = new RegisterService();
	private UsersService usersService = new UsersService();
	private ConfigDocService configDocService = new ConfigDocService();
	private QualificationService qualificationService = new QualificationService();
	private ScheduledEventService scheduledEventService = new ScheduledEventService();
	/**
	 * Get all QdfCompany
	 * 
	 * @author TechFinium
	 * @see QdfCompany
	 * @return a list of {@link haj.com.entity.QdfCompany}
	 * @throws Exception
	 *  the exception
	 */
	public List<QdfCompany> allQdfCompany() throws Exception {
		return dao.allQdfCompany();
	}

	/**
	 * Create or update QdfCompany.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see QdfCompany
	 */
	public void create(QdfCompany entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Updates or creates the user added
	 * 
	 * @param user
	 * @return Users
	 * @throws Exception
	 */
	public Users updateCreateRegisterUser(Users user) throws Exception {
		if (user.getId() == null) {
			Users t = registerService.getUserByEmailIdNumberPassportNumber(user);
			if(t !=null) {
				throw new Exception("Users with same email or ID number already exist");
			}
			registerService.register(user,true);
		} else {
			usersService.update(user);
		}
		return usersService.findByKey(user.getId());
	}

	public void prepareNewRegistrationC(ConfigDocProcessEnum configDocProcess, Company entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null) {

			if (processRoles.getCompanyUserType() == null) entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			else entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), processRoles));

			List<ConfigDoc> l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), processRoles);

			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {

			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessA(configDocProcess);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}

	public void prepareNewRegistration(ConfigDocProcessEnum configDocProcess, Users entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null) {
			
			if (processRoles.getCompanyUserType() == null) entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			else entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), processRoles));

			List<ConfigDoc> l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), processRoles);

			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessA(configDocProcess);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}
	public void prepareDocuments(QdfCompany entity, boolean firstInProcess,ConfigDocProcessEnum configDocProcessEnum, CompanyUserTypeEnum companyUserTypeEnum, Tasks tasks) throws Exception {
		if (tasks.getProcessRole().getRolePermission() != UserPermissionEnum.EditUpload || entity.getApprovalEnum() == ApprovalEnum.Rejected) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcessEnum));
			List<ConfigDoc> l = new ArrayList<>();
			if (firstInProcess) {
				l = configDocService.findByProcessNotUploadedForReg(entity.getClass().getName(), entity.getId(),configDocProcessEnum, companyUserTypeEnum);
			} else {
				l = configDocService.findByProcessNotUploadedNotForReg(entity.getClass().getName(), entity.getId(), configDocProcessEnum, companyUserTypeEnum);
			}
			if (l != null && l.size() > 0) {
				for(ConfigDoc configDoc:l) {
					entity.getDocs().add(new Doc(configDoc));
				}
			}
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = configDocService.findByProcess(tasks.getWorkflowProcess(), companyUserTypeEnum);
			//List<ConfigDoc> l = ConfigDocService.instance().findByProcessNotUploadedNotForReg(entity.getClass().getName(), entity.getId(), configDocProcessEnum, companyUserTypeEnum);
			if (l != null && l.size() > 0) {
				for(ConfigDoc configDoc:l) {
					entity.getDocs().add(new Doc(configDoc));
				}
				
			}
		}
	}
	public void prepareNewRegistration(ConfigDocProcessEnum configDocProcess, QdfCompany entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null && processRoles != null ) {
			
			if (processRoles.getCompanyUserType() == null) entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			else entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), processRoles));

			List<ConfigDoc> l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), processRoles);

			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {
			
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessA(configDocProcess);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}

	public void prepareNewRegistration(ConfigDocProcessEnum configDocProcess, Company entityDoc, QdfCompany entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null) {
			if (processRoles.getCompanyUserType() == null) entityDoc.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			else entityDoc.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), processRoles));

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
	

/*	public void prepareNewRegistration2(ConfigDocProcessEnum configDocProcess, QdfCompany entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null && processRoles != null ) {
			
			if (processRoles.getCompanyUserType() == null) entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			else entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), processRoles));
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), processRoles);
			switch (key) {
			case value:
				
				break;

			default:
				break;
			}
			
			
			

			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {
			
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessA(configDocProcess);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}
*/
	/**
	 * Updates the QDFCompany and User. If not linked will link form user to company
	 * on QDFCompany
	 * 
	 * @param entity
	 * @param formUser
	 * @throws Exception
	 */
	private void updateEntityAndFormUser(QdfCompany entity, Users formUser) throws Exception {
		if (formUser != null) {
			formUser = updateCreateRegisterUser(formUser);
			if (entity.getUser() == null) {
				entity.setUser(formUser);
			}
		}
		update(entity);
	}

	/**
	 * Update QdfCompany.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see QdfCompany
	 */
	public void update(QdfCompany entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete QdfCompany.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see QdfCompany
	 */
	public void delete(QdfCompany entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.QdfCompany}
	 * @throws Exception
	 *             the exception
	 * @see QdfCompany
	 */
	public QdfCompany findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find QdfCompany by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.QdfCompany}
	 * @throws Exception
	 *             the exception
	 * @see QdfCompany
	 */
	public List<QdfCompany> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load QdfCompany
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.QdfCompany}
	 * @throws Exception
	 *             the exception
	 */
	public List<QdfCompany> allQdfCompany(int first, int pageSize) throws Exception {
		return dao.allQdfCompany(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of QdfCompany for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the QdfCompany
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(QdfCompany.class);
	}

	/**
	 * Lazy load QdfCompany with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            QdfCompany class
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
	 * @return a list of {@link haj.com.entity.QdfCompany} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<QdfCompany> allQdfCompany(Class<QdfCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		List<QdfCompany> list = (List<QdfCompany>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
		resolveDocs(list);
		return list;
	}

	private void resolveDocs(List<QdfCompany> list) throws Exception {
		for(QdfCompany qdfCompany: list) {
			populateQdfCompanyDocs(qdfCompany);
		}
	}

	private void populateQdfCompanyDocs(QdfCompany entity) throws Exception {
		prepareQualificationsCurriculumDevelopmentDocs(ConfigDocProcessEnum.QDF_Registration, entity,entity, null);		
		if(entity.getQualificationsCurriculumDevelopment().getTemplateType() == QCDTemplateTypeEnum.ReAlignment) {
			entity.setQualification(qualificationService.findByKey(Long.parseLong(entity.getQualificationsCurriculumDevelopment().getReAlignmentQualificationIdList())));
		}else if(entity.getQualificationsCurriculumDevelopment().getTemplateType() == QCDTemplateTypeEnum.Review) {
			entity.setQualification(qualificationService.findByKey(Long.parseLong(entity.getQualificationsCurriculumDevelopment().getReviewQualificationIdList())));
		}
	}
	
	public void prepareQualificationsCurriculumDevelopmentDocs(ConfigDocProcessEnum configDocProcess, QdfCompany entityDoc, QdfCompany entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null) {	
			if (processRoles == null) {
				entityDoc.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			}
			else {
				entityDoc.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), processRoles));
			}
		}
	}

	/**
	 * Get count of QdfCompany for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            QdfCompany class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the QdfCompany entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<QdfCompany> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/** Actions Start */
	/**
	 * Completes the task and sends it to the first person in the process configured
	 * for: ConfigDocProcessEnum.QDF_Registration
	 * 
	 * @param entity
	 * @param formUser
	 * @param task
	 * @throws Exception
	 */
	public void completeToFirst(QdfCompany entity, Users user, Tasks task, Users formUser) throws Exception {
		entity.setApprovalEnum(ApprovalEnum.PendingApproval);
		updateEntityAndFormUser(entity, formUser);
		String desc = "QDF Company Registration has been submitted. Please Review.";
		TasksService.instance().findFirstInProcessAndCreateTask(desc, user, task.getTargetKey(), task.getTargetClass(), true, ConfigDocProcessEnum.QDF_Registration, null, null);
		TasksService.instance().completeTask(task);
		//sendAcknowledgementEmail(entity, entity.getUser());
	}

	/**
	 * Completes the task and sends it to the next process configured in
	 * ConfigDocProcessEnum.QDF_Registration
	 * 
	 * @param qdfCompany
	 * @param formUser
	 * @param task
	 * @throws Exception
	 */
	public void completeTask(QdfCompany entity, Users user, Tasks tasks, Users formUser) throws Exception {
		String error = checkRequiredDocs(entity);
		if(error.length() > 0 && error != "") {
			throw new Exception(error);
		}
		uploadDocuments(entity, tasks, formUser);
		entity.setApprovalDate(null);
		entity.setApprovalEnum(ApprovalEnum.PendingApproval);
		updateEntityAndFormUser(entity, formUser);		
		TasksService.instance().findNextInProcessAndCreateTask("", user, entity.getId(), QdfCompany.class.getName(), true, tasks, null, null);
	}
	
	/**
	 * The first set of the Work Place Approval Visit Date
	 * @param workplaceapproval
	 * @throws Exception
	 */
	public void setFirstVisitDate(QdfCompany qdfCompany, Users sessionUser, List<Users> selectedUsers) throws Exception{		
		List<IDataEntity> dataEntities = new ArrayList<>();
		ScheduledEvent scheduledEvent= new ScheduledEvent();
		
		scheduledEvent.setTargetKey(qdfCompany.getId());
		scheduledEvent.setTargetClass(QdfCompany.class.getName());
		scheduledEvent.setUser(sessionUser);
		dataEntities.add(scheduledEvent);
		
		for (Users user : selectedUsers){
			dataEntities.add(new ScheduledEventUsers(scheduledEvent, user));
			sendReviewDateEmail(qdfCompany, user);
		}
		
		dao.createBatch(dataEntities);		
		//qdfCompany.setScheduledEvent(scheduledEvent);
		qdfCompany.setReviewUser(sessionUser);
		create(qdfCompany);
	
	}
	
	public String checkRequiredDocs(QdfCompany entity) throws Exception {
		String error= "";
		if (entity.getDocs() != null) {
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) {
						doc.setData(docByte.getData());
					}
				}
				if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
					error += "Please ensure all documents are uplaoded";
					break;
				}
			}
		}
		return error;
	}

	/**
	 * Approves the QdfCompany and sends it to the next process configured in
	 * ConfigDocProcessEnum.QDF_Registration
	 * 
	 * @param entity
	 * @param user
	 * @param tasks
	 * @throws Exception
	 */
	public void approveTask(QdfCompany entity, Users user, Tasks tasks, Users formUser) throws Exception {
		uploadDocuments(entity, tasks, user);
		entity.setApprovalDate(null);
		entity.setApprovalEnum(ApprovalEnum.PendingApproval);
		updateEntityAndFormUser(entity, formUser);		
		TasksService.instance().findNextInProcessAndCreateTask("", user, entity.getId(), QdfCompany.class.getName(), true, tasks, null, null);

		if(tasks.getProcessRole().getRoleOrder() == 13) {
			if(entity.getQualificationsCurriculumDevelopment().getTemplateType() == QCDTemplateTypeEnum.NewDevelopment) {
				sendApprovalEmailForNewDevelopment(entity, entity.getUser());
				//sendFinalApprovalEmailForNewDevelopment(entity, entity.getUser());
			}else if(entity.getQualificationsCurriculumDevelopment().getTemplateType() == QCDTemplateTypeEnum.ReAlignment) {
				sendApprovalEmailForReAlignment(entity, entity.getUser());
				//sendFinalApprovalEmailForReAlignment(entity, entity.getUser());
			}else if(entity.getQualificationsCurriculumDevelopment().getTemplateType() == QCDTemplateTypeEnum.Review) {
				sendApprovalEmailForReview(entity, entity.getUser());
				//sendFinalApprovalEmailForReview(entity, entity.getUser());
			}
		}
		/// TasksService.instance().findNextInProcessAndCreateTaskWe glad to inform you that your Qualifications Development 
	}

	/**
	 * Rejects the task and sends it back to the previous process
	 * 
	 * @param entity
	 * @param user
	 * @param tasks
	 * @throws Exception
	 */
	public void rejectTask(QdfCompany entity, Users user, Tasks tasks, Users formUser, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		entity.setApprovalDate(getSynchronizedDate());
		entity.setApprovalEnum(ApprovalEnum.Rejected);
		updateEntityAndFormUser(entity, formUser);
		
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(entity.getId(), QdfCompany.class.getName(), selectedRejectReason, user, ConfigDocProcessEnum.QDF_Registration);
		for(RejectReasonMultipleSelection rrm:rrmList)
		{
			if(rrm !=null)
			{
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
			
		}
		// check if first in process, unable to reject if first
		if (!tasks.getFirstInProcess()) {
			TasksService.instance().findPreviousInProcessAndCreateTask("", user, entity.getId(), QdfCompany.class.getName(), true, tasks, null, null);
		}
	}

	/**
	 * Final Approves the QdfCompany Registration and closes the task
	 * 
	 * @param entity
	 * @param user
	 * @param tasks
	 * @throws Exception
	 */
	
	public void finalApproveTask(QdfCompany entity, Users user, Tasks tasks, Users formUser) throws Exception {
		uploadDocuments(entity, tasks, user);
		entity.setApprovalDate(getSynchronizedDate());
		entity.setApprovalEnum(ApprovalEnum.Approved);
		updateEntityAndFormUser(entity, formUser);		
		TasksService.instance().completeTask(tasks);
		
		QualificationService s = new QualificationService();
		Qualification qualification =  new Qualification();
		
		
		String description ="";
		String code = "";
		//sendApprovalEmail(entity, entity.getUser());

		if(entity.getQualificationsCurriculumDevelopment().getTemplateType() == QCDTemplateTypeEnum.NewDevelopment) {
			description = entity.getQualificationsCurriculumDevelopment().getOfoCodes().getDescription();
			code = entity.getQualificationsCurriculumDevelopment().getOfoCodes().getOfoCodeParent();
			//sendApprovalEmailForNewDevelopment(entity, entity.getUser());
			sendFinalApprovalEmailForNewDevelopment(entity, entity.getUser());
		}else if(entity.getQualificationsCurriculumDevelopment().getTemplateType() == QCDTemplateTypeEnum.ReAlignment) {
			description = entity.getQualificationsCurriculumDevelopment().getQualification().getDescription();
			code = entity.getQualificationsCurriculumDevelopment().getQualification().getCodeString();
			//sendApprovalEmailForReAlignment(entity, entity.getUser());
			sendFinalApprovalEmailForReAlignment(entity, entity.getUser());
		}else if(entity.getQualificationsCurriculumDevelopment().getTemplateType() == QCDTemplateTypeEnum.Review) {
			description = entity.getQualificationsCurriculumDevelopment().getQualification().getDescription();
			code = entity.getQualificationsCurriculumDevelopment().getQualification().getCodeString();
			//sendApprovalEmailForReview(entity, entity.getUser());
			sendFinalApprovalEmailForReview(entity, entity.getUser());
		}
		
		qualification.setDescription(description);
		qualification.setCodeString(code);
		s.create(qualification);
	}

	/**
	 * Final Rejection of the QdfCompany Registration and closes the task
	 * 
	 * @param entity
	 * @param user
	 * @param tasks
	 * @throws Exception
	 */
	public void finalRejectTask(QdfCompany entity, Users user, Tasks tasks, Users formUser, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		entity.setApprovalDate(getSynchronizedDate());
		entity.setApprovalEnum(ApprovalEnum.Rejected);
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(entity.getId(), QdfCompany.class.getName(), selectedRejectReason, user, ConfigDocProcessEnum.QDF_Registration);
		for(RejectReasonMultipleSelection rrm:rrmList)
		{
			if(rrm !=null)
			{
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
			
		}		
		updateEntityAndFormUser(entity, formUser);
		TasksService.instance().completeTask(tasks);
		
		if(entity.getQualificationsCurriculumDevelopment().getTemplateType() == QCDTemplateTypeEnum.NewDevelopment) {
			sendFinalRejectEmailForNewDevelopment(entity, entity.getUser());
		}else if(entity.getQualificationsCurriculumDevelopment().getTemplateType() == QCDTemplateTypeEnum.ReAlignment) {
			sendFinalRejectEmailForReAlignment(entity, entity.getUser());
		}else if(entity.getQualificationsCurriculumDevelopment().getTemplateType() == QCDTemplateTypeEnum.Review) {
			sendFinalRejectEmailForReview(entity, entity.getUser());
		}
		//sendRejectEmail(entity, entity.getUser());
	}

	public List<Doc> prepareNewRegistration(String entity, Long targetKey, ConfigDocProcessEnum configDocProcessEnum, CompanyUserTypeEnum companyUserTypeEnum) throws Exception {
		List<Doc> docs = new ArrayList<>();
		if (targetKey != null) {
			docs = DocService.instance().searchByTargetKeyAndClass(entity, targetKey, configDocProcessEnum, companyUserTypeEnum);
			List<ConfigDoc> l = configDocService.findByProcessNotUploaded(entity, targetKey, configDocProcessEnum, companyUserTypeEnum);
			if (l != null && l.size() > 0) {
				for (ConfigDoc a : l) {
					docs.add(new Doc(a));
				}
			}
		} else {
			List<ConfigDoc> l = configDocService.findByProcess(configDocProcessEnum, companyUserTypeEnum);
			if (l != null && l.size() > 0) {
				for (ConfigDoc a : l) {
					docs.add(new Doc(a));
				}
			}
		}
		return docs;
	}

	/** Actions End */

	/**
	 * Uploads docs uploaded against QdfCompany and user throws Exception if doc
	 * data is empty for permissions: Upload or EditUpload Only time documents
	 * required if permissions is: FinalUploadApproval or FinalUpload
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void uploadDocuments(QdfCompany entity, Tasks task, Users sessionUser) throws Exception {
		if (task != null && (task.getProcessRole().getRolePermission() == UserPermissionEnum.Upload || task.getProcessRole().getRolePermission() == UserPermissionEnum.EditUpload || task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalUploadApproval || task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalUpload)) {

			if (sessionUser != null && sessionUser.getDocs() != null) {
				for (Doc doc : sessionUser.getDocs()) {
					if (doc.getId() != null) {
						DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
						if (docByte != null) {
							doc.setData(docByte.getData());
						}
					}
					if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
						if (task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalUploadApproval || task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalUpload) {
							throw new Exception("Please ensure all documents are uploaded for the user");
						}
					}
				}

			}

			// check if data not empty and creates document
			
			if (sessionUser != null && sessionUser.getDocs() != null)  { 
				for (Doc doc : sessionUser.getDocs()) { 
					if (doc.getId() == null && doc.getData() != null) {
						 doc.setTargetKey(entity.getId());
						 doc.setTargetClass(QdfCompany.class.getName());
						// DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), sessionUser); 
						 DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), sessionUser, entity.getDocs());
					}else if(doc.getData() != null){
						doc.setTargetKey(entity.getId());
						doc.setTargetClass(QdfCompany.class.getName());
						DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), sessionUser);
					}
				} 
			}			
		}
	}

	public void sendAcknowledgementEmail(QdfCompany entity, Users u) {
		String title = "";
		if(u != null && u.getTitle()!=null) {
			title =u.getTitle().getDescription();
		}
		
		String welcome = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Dear #Title# #FirstName# #Surname#,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We acknowledge receipt of your Qualification Development Provider application."
				+ "</p>" 		
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The application will be evaluated by the merSETA and should any "
				+ "additional information be required, this will be communicated to you."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please note that it may take up to 5 working days to review the application."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Curriculum and Learning Programmes Unit"
				+ "</p>" 
				+ "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		GenericUtility.sendMail(u.getEmail(), "Qualification Development Application.".toUpperCase(), welcome, null);
	}
	
	public void sendRejectEmail(QdfCompany entity, Users u) {
		String title = "";
		if(u != null && u.getTitle()!=null) {
			title =u.getTitle().getDescription();
		}
		String welcome = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Dear #Title# #FirstName# #Surname#,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We regret to inform you that your Qualifications Development Provider Application has been rejected."
				+ "</p>" 						
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Regards"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The merSETA team"
				+ "</p>" 
				+ "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		GenericUtility.sendMail(u.getEmail(), "Qualification Development Application.".toUpperCase(), welcome, null);
	}
	
	public void sendApprovalEmailForNewDevelopment(QdfCompany entity, Users u) throws NumberFormatException, Exception {
		String title = "";
		if(u != null && u.getTitle()!=null) {
			title =u.getTitle().getDescription();
		}
		String codedesc = entity.getQualificationsCurriculumDevelopment().getOfoCodes().getOfoCodeParent();
		String welcome = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Dear #Title# #FirstName# #Surname#,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The merSETA hereby advises that the application for a new qualification against "
				+ "the following OFO Code: #codedesc# has been submitted to the QCTO."
				+ "</p>" 	
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please note that the merSETA may revert and request additional information."
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Kindly be advised that the application process with the QCTO may take longer "
				+ "than 30 days. The merSETA will communicate the outcome once the QCTO has reverted to the merSETA."
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Curriculum and Learning Programme Development Unit"
				+ "</p>" 
				+ "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#codedesc#", codedesc);
		GenericUtility.sendMail(u.getEmail(), "NOTIFICATION OF SUBMISSION OF APPLICATION TO THE QUALITY COUNCIL FOR TRADES AND OCCUPATIONS (QCTO)", welcome, null);
	}
	public void sendApprovalEmailForReAlignment(QdfCompany entity, Users u) throws NumberFormatException, Exception {
		String title = "";
		if(u != null && u.getTitle()!=null) {
			title =u.getTitle().getDescription();
		}
		QualificationService ser = new QualificationService();
		Qualification qual = ser.findByKey(Long.parseLong(entity.getQualificationsCurriculumDevelopment().getReAlignmentQualificationIdList()));
		String codedesc =  qual.getCode()+" "+ qual.getDescription();
		
		String welcome = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Dear #Title# #FirstName# #Surname#,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The merSETA hereby advises that the application for a new qualification against the "
				+ "following qualification: #codedesc# has been submitted to the QCTO."
				+ "</p>" 	
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please note that the merSETA may revert and request additional information.</p>" 
				+ "<p>Kindly be advised that the application process with the QCTO may take longer than 30 days. "
				+ "The merSETA will communicate the outcome once the QCTO has reverted to the merSETA."
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Curriculum and Learning Programme Development Unit"
				+ "</p>" 
				+ "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#codedesc#", codedesc);
		GenericUtility.sendMail(u.getEmail(), "NOTIFICATION OF SUBMISSION OF APPLICATION TO THE QUALITY COUNCIL FOR TRADES AND OCCUPATIONS (QCTO)", welcome, null);
	}
	
	public void sendApprovalEmailForReview(QdfCompany entity, Users u) throws NumberFormatException, Exception {
		String title = "";
		if(u != null && u.getTitle()!=null) {
			title =u.getTitle().getDescription();
		}
		QualificationService ser = new QualificationService();
		Qualification qual = ser.findByKey(Long.parseLong(entity.getQualificationsCurriculumDevelopment().getReviewQualificationIdList()));
		String codedesc =  qual.getCode()+" "+ qual.getDescription();
		
		String welcome = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Dear #Title# #FirstName# #Surname#,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The merSETA hereby advises that the application for a new qualification against the "
				+ "following qualification: #codedesc# has been submitted to the QCTO."
				+ "</p>" 	
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please note that the merSETA may revert and request additional information."
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Kindly be advised that the application process with the QCTO may take longer than 30 days. "
				+ "The merSETA will communicate the outcome once the QCTO has reverted to the merSETA."
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Curriculum and Learning Programme Development Unit"
				+ "</p>" 
				+ "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#codedesc#", codedesc);
		GenericUtility.sendMail(u.getEmail(), "NOTIFICATION OF SUBMISSION OF APPLICATION TO THE QUALITY COUNCIL FOR TRADES AND OCCUPATIONS (QCTO)", welcome, null);
	}
	
	public void sendFinalApprovalEmailForNewDevelopment(QdfCompany entity, Users u) throws NumberFormatException, Exception {
		String title = "";
		if(u != null && u.getTitle()!=null) {
			title =u.getTitle().getDescription();
		}
		String codedesc = entity.getQualificationsCurriculumDevelopment().getOfoCodes().getOfoCodeParent();
		String welcome = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Dear #Title# #FirstName# #Surname#,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The merSETA hereby advises that the application for a new qualification against the following OFO Code: "
				+ "#codedesc# has been reviewed by the QCTO and was approved and is/will be available on the "
				+ "South African Qualifications Authority (SAQA) database. "
				+ "</p>" 	
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please do not hesitate to contact the merSETA Curriculum and Learning "
				+ "Programmes Unit or the Regional office for further assistance."
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Manager: Curriculum and Learning Programme Development"
				+ "</p>" 
				+ "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#codedesc#", codedesc);
		for (Users users : findUsersJobtitle(u)) {
			GenericUtility.sendMail(users.getEmail(), "NOTIFICATION OF OUTCOME OF QUALIFICATION DEVELOPMENT APPLICATION TO THE QUALITY COUNCIL FOR TRADES AND OCCUPATIONS (QCTO)", welcome, null);
		}
	}
	public void sendFinalApprovalEmailForReAlignment(QdfCompany entity, Users u) throws NumberFormatException, Exception {
		String title = "";
		if(u != null && u.getTitle()!=null) {
			title =u.getTitle().getDescription();
		}
		QualificationService ser = new QualificationService();
		Qualification qual = ser.findByKey(Long.parseLong(entity.getQualificationsCurriculumDevelopment().getReAlignmentQualificationIdList()));
		String codedesc =  qual.getCode()+" "+ qual.getDescription();
		
		String welcome = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Dear #Title# #FirstName# #Surname#,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The merSETA hereby advises that the application for a new qualification "
				+ "against the following qualification: #codedesc# has been reviewed "
				+ "by the QCTO and was approved and is/will be available on "
				+ "the South African Qualifications Authority (SAQA) database. "
				+ "</p>" 	
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please do not hesitate to contact the merSETA Curriculum and "
				+ "Learning Programmes Unit or the Regional office for further assistance."
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Manager: Curriculum and Learning Programme Development"
				+ "</p>" 
				+ "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#codedesc#", codedesc);
		for (Users users : findUsersJobtitle(u)) {
			GenericUtility.sendMail(users.getEmail(), "NOTIFICATION OF OUTCOME OF QUALIFICATION DEVELOPMENT APPLICATION TO THE QUALITY COUNCIL FOR TRADES AND OCCUPATIONS (QCTO)", welcome, null);
		}
	}
	
	public void sendFinalApprovalEmailForReview(QdfCompany entity, Users u) throws NumberFormatException, Exception {
		String title = "";
		if(u != null && u.getTitle()!=null) {
			title =u.getTitle().getDescription();
		}
		QualificationService ser = new QualificationService();
		Qualification qual = ser.findByKey(Long.parseLong(entity.getQualificationsCurriculumDevelopment().getReviewQualificationIdList()));
		String codedesc =  qual.getCode()+" "+ qual.getDescription();
		
		String welcome = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Dear #Title# #FirstName# #Surname#,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The merSETA hereby advises that the application for a new qualification "
				+ "against the following qualification: #codedesc# has been reviewed by the "
				+ "QCTO and was approved and is/will be available on the South African "
				+ "Qualifications Authority (SAQA) database."
				+ "</p>" 	
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please do not hesitate to contact the merSETA Curriculum and "
				+ "Learning Programmes Unit or the Regional office for further assistance."
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Manager: Curriculum and Learning Programme Development"
				+ "</p>" 
				+ "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#codedesc#", codedesc);
		for (Users users : findUsersJobtitle(u)) {
			GenericUtility.sendMail(users.getEmail(), "NOTIFICATION OF OUTCOME OF QUALIFICATION DEVELOPMENT APPLICATION TO THE QUALITY COUNCIL FOR TRADES AND OCCUPATIONS (QCTO)", welcome, null);
		}
	}
	
	public void sendFinalRejectEmailForNewDevelopment(QdfCompany entity, Users u) throws NumberFormatException, Exception {
		String title = "";
		if(u != null && u.getTitle()!=null) {
			title =u.getTitle().getDescription();
		}
		String codedesc = entity.getQualificationsCurriculumDevelopment().getOfoCodes().getOfoCodeParent();
		String welcome = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Dear #Title# #FirstName# #Surname#,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The merSETA hereby advises that the application for a new qualification "
				+ "against the following OFO Code: #codedesc# has been reviewed by the QCTO and was not approved."
				+ "</p>" 	
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please do not hesitate to contact the merSETA Curriculum and "
				+ "Learning Programmes Unit or the Regional office for further assistance. "
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Manager: Curriculum and Learning Programme Development"
				+ "</p>" 
				+ "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#codedesc#", codedesc);
		for (Users users : findUsersJobtitle(u)) {
			GenericUtility.sendMail(users.getEmail(), "NOTIFICATION OF OUTCOME OF QUALIFICATION DEVELOPMENT APPLICATION TO THE QUALITY COUNCIL FOR TRADES AND OCCUPATIONS (QCTO)", welcome, null);
		}
	}
	public void sendFinalRejectEmailForReAlignment(QdfCompany entity, Users u) throws NumberFormatException, Exception {
		String title = "";
		if(u != null && u.getTitle()!=null) {
			title =u.getTitle().getDescription();
		}
		QualificationService ser = new QualificationService();
		Qualification qual = ser.findByKey(Long.parseLong(entity.getQualificationsCurriculumDevelopment().getReAlignmentQualificationIdList()));
		String codedesc =  qual.getCode()+" "+ qual.getDescription();
		
		String welcome = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Dear #Title# #FirstName# #Surname#,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The merSETA hereby advises that the application for a new qualification "
				+ "against the following qualification: #codedesc# has been "
				+ "reviewed by the QCTO and was not approved."
				+ "</p>" 	
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please do not hesitate to contact the merSETA Curriculum and "
				+ "Learning Programmes Unit or the Regional office for further assistance. "
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Manager: Curriculum and Learning Programme Development"
				+ "</p>" 
				+ "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#codedesc#", codedesc);
		for (Users users : findUsersJobtitle(u)) {
			GenericUtility.sendMail(users.getEmail(), "NOTIFICATION OF OUTCOME OF QUALIFICATION DEVELOPMENT APPLICATION TO THE QUALITY COUNCIL FOR TRADES AND OCCUPATIONS (QCTO)", welcome, null);
		}
	}
	
	public void sendFinalRejectEmailForReview(QdfCompany entity, Users u) throws NumberFormatException, Exception {
		String title = "";
		if(u != null && u.getTitle()!=null) {
			title =u.getTitle().getDescription();
		}
		QualificationService ser = new QualificationService();
		Qualification qual = ser.findByKey(Long.parseLong(entity.getQualificationsCurriculumDevelopment().getReviewQualificationIdList()));
		String codedesc =  qual.getCode()+" "+ qual.getDescription();
		
		String welcome = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Dear #Title# #FirstName# #Surname#,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The merSETA hereby advises that the application for a new qualification "
				+ "against the following qualification: #codedesc# has "
				+ "been reviewed by the QCTO and was not approved."
				+ "</p>" 	
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please do not hesitate to contact the merSETA Curriculum and Learning Programmes Unit or the Regional office for further assistance. "
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Manager: Curriculum and Learning Programme Development"
				+ "</p>" 
				+ "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#codedesc#", codedesc);
		for (Users users : findUsersJobtitle(u)) {
			GenericUtility.sendMail(users.getEmail(), "NOTIFICATION OF OUTCOME OF QUALIFICATION DEVELOPMENT APPLICATION TO THE QUALITY COUNCIL FOR TRADES AND OCCUPATIONS (QCTO)", welcome, null);
		}
	}
	
	private void sendReviewDateEmail(QdfCompany registration, Users sessionUser) throws Exception {
		//SDFCompany sdfCom = SDFCompanyService.instance().findPrimarySDF(registration.getCompany());		
		Users u = sessionUser;
		
		String idorpassport = "";
		String title ="";
		if(u.getTitle()!=null) {title = u.getTitle().getDescription();}
		

		String welcome = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Dear #title# #FirstName# #Surname#,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please be advised that a qualification development meeting for the "
				+ "following qualification: (#saqaID#) #qualification# has been for the #date#."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Should there be a change in the scheduled date, updated details will be sent."
				+ "</p>"				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please do not hesitate to contact the Qualification "
				+ "Development Facilitator for further assistance/enquiries."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "#QDFName# #QDFSurname#"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Qualification Development Facilitator</p>" 
				+ "<br/>";
				welcome = welcome.replaceAll("#date#", GenericUtility.sdf5.format(registration.getReviewDate()));
				welcome = welcome.replaceAll("#title#", title);
				welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
				welcome = welcome.replaceAll("#Surname#", u.getLastName());
				welcome = welcome.replaceAll("#QDFName#", registration.getUser().getFirstName());
				welcome = welcome.replaceAll("#QDFSurname#", registration.getUser().getLastName());
				welcome = welcome.replaceAll("#qualification#", registration.getQualification().getDescription());
				welcome = welcome.replaceAll("#saqaID#",String.valueOf(registration.getQualification().getCode()));
		
				
		GenericUtility.sendMail(sessionUser.getEmail(), "QUALIFICATION DEVELOPMENT ENGAGEMENT", welcome, null);		
	}
	
	public List<Users> findUsersJobtitle(Users u) {
		HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
		List<Users> userList = new ArrayList<>();
		  userList.add(u); 
		  userList.addAll(hostingCompanyEmployeesService.findUserByJobTitle(83L)); 
		  userList.addAll(hostingCompanyEmployeesService.findUserByJobTitle(146L)); 
		  userList.addAll(hostingCompanyEmployeesService.findUserByJobTitle(175L)); 
		  return userList;
	}
	
	public List<QdfCompany> findByQualificationCurriculumDevelopentID(QualificationsCurriculumDevelopment qualificationsCurriculumDevelopment) throws Exception {
		List<QdfCompany> l = new ArrayList<>();
		l = dao.findByQualificationCurriculumDevelopentID(qualificationsCurriculumDevelopment);
		resolveDocs(l);
		return l;
	}
}
