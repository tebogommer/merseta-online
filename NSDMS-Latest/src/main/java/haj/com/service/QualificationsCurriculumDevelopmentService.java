package haj.com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.QualificationsCurriculumDevelopmentDAO;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.DocumentTracker;
import haj.com.entity.Learners;
import haj.com.entity.LearnershipDevelopmentRegistration;
import haj.com.entity.ProcessRoles;
import haj.com.entity.QdfCompany;
import haj.com.entity.QualificationsCurriculumDevelopment;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.SiteVisit;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.DocumentTrackerEnum;
import haj.com.entity.enums.QCDTemplateTypeEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.RejectReasons;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.QualificationService;
import haj.com.utils.GenericUtility;

public class QualificationsCurriculumDevelopmentService extends AbstractService {

	/** The dao. */
	private QualificationsCurriculumDevelopmentDAO dao = new QualificationsCurriculumDevelopmentDAO();

	/** The Service Layer */
	private ConfigDocService configDocService = new ConfigDocService();
	private QdfCompanyService qdfCompanyService = new QdfCompanyService();

	/**
	 * Get all QualificationsCurriculumDevelopment
	 * 
	 * @author TechFinium
	 * @see QualificationsCurriculumDevelopment
	 * @return a list of {@link haj.com.entity.QualificationsCurriculumDevelopment}
	 * @throws Exception
	 *             the exception
	 */
	public List<QualificationsCurriculumDevelopment> allQualificationsCurriculumDevelopment() throws Exception {
		return dao.allQualificationsCurriculumDevelopment();
	}

	/**
	 * Create or update QualificationsCurriculumDevelopment.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see QualificationsCurriculumDevelopment
	 */
	public void create(QualificationsCurriculumDevelopment entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	public void createIdataEntity(IDataEntity entity) throws Exception {
		dao.create(entity);
	}

	/**
	 * Update QualificationsCurriculumDevelopment.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see QualificationsCurriculumDevelopment
	 */
	public void update(QualificationsCurriculumDevelopment entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete QualificationsCurriculumDevelopment.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see QualificationsCurriculumDevelopment
	 */
	public void delete(QualificationsCurriculumDevelopment entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.QualificationsCurriculumDevelopment}
	 * @throws Exception
	 *             the exception
	 * @see QualificationsCurriculumDevelopment
	 */
	public QualificationsCurriculumDevelopment findByKey(long id) throws Exception {
		return populateAdditionalInformation(dao.findByKey(id));
	}

	/**
	 * Find QualificationsCurriculumDevelopment by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.QualificationsCurriculumDevelopment}
	 * @throws Exception
	 *             the exception
	 * @see QualificationsCurriculumDevelopment
	 */
	public List<QualificationsCurriculumDevelopment> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load QualificationsCurriculumDevelopment
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.QualificationsCurriculumDevelopment}
	 * @throws Exception
	 *             the exception
	 */
	public List<QualificationsCurriculumDevelopment> allQualificationsCurriculumDevelopment(int first, int pageSize) throws Exception {
		return dao.allQualificationsCurriculumDevelopment(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of QualificationsCurriculumDevelopment for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the QualificationsCurriculumDevelopment
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(QualificationsCurriculumDevelopment.class);
	}

	/**
	 * Lazy load QualificationsCurriculumDevelopment with pagination, filter,
	 * sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            QualificationsCurriculumDevelopment class
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
	 * @return a list of {@link haj.com.entity.QualificationsCurriculumDevelopment}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<QualificationsCurriculumDevelopment> allQualificationsCurriculumDevelopment(Class<QualificationsCurriculumDevelopment> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<QualificationsCurriculumDevelopment>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
	}

	/**
	 * Find QualificationsCurriculumDevelopment by company ID
	 * 
	 * @author TechFinium
	 * @param companyId
	 *            the company id
	 * @see QualificationsCurriculumDevelopment
	 * @return a list of {@link haj.com.entity.QualificationsCurriculumDevelopment}
	 * @throws Exception
	 *             global exception
	 */
	public List<QualificationsCurriculumDevelopment> findByCompany(Company company) throws Exception {
		return dao.findByCompany(company.getId());
	}

	@SuppressWarnings("unchecked")
	public List<QualificationsCurriculumDevelopment> findByUser(Long userID) throws Exception {
		return dao.findByUser(userID);
	}

	/**
	 * Get count of QualificationsCurriculumDevelopment for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            QualificationsCurriculumDevelopment class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the QualificationsCurriculumDevelopment entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<QualificationsCurriculumDevelopment> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/**
	 * Populates additional information for a list of
	 * LearnershipDevelopmentRegistration
	 * 
	 * @param entityList
	 * @return entityList
	 * @throws Exception
	 */
	private List<QualificationsCurriculumDevelopment> populateAdditionalInformationList(List<QualificationsCurriculumDevelopment> entityList) throws Exception {
		for (QualificationsCurriculumDevelopment learnershipDevelopmentRegistration : entityList) {
			populateAdditionalInformation(learnershipDevelopmentRegistration);
		}
		return entityList;
	}

	/**
	 * Populates additional information for a LearnershipDevelopmentRegistration
	 * 
	 * @param entity
	 * @return entity
	 * @throws Exception
	 */
	private QualificationsCurriculumDevelopment populateAdditionalInformation(QualificationsCurriculumDevelopment entity) throws Exception {
		// preps docs against LearnershipDevelopmentRegistration
		prepareNewDocs(entity, ConfigDocProcessEnum.QUALIFICATIONS_CURRICULUM_DEVELOPMENT, CompanyUserTypeEnum.Company);
		return entity;
	}

	/**
	 * Populates docs against the LearnershipDevelopmentRegistration entity
	 * 
	 * @param entity
	 * @param configDocProcess
	 * @param companyUserType
	 * @return entity
	 * @throws Exception
	 */
	public QualificationsCurriculumDevelopment prepareNewDocs(QualificationsCurriculumDevelopment entity, ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
		entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
		List<ConfigDoc> l = configDocService.findByProcessNotUploaded(entity.getClass().getName(), entity.getId(), configDocProcess, companyUserType);
		if (l != null && l.size() > 0) {
			l.forEach(a -> {
				entity.getDocs().add(new Doc(a));
			});
		}
		return entity;
	}

	public void prepareNewDocs(QualificationsCurriculumDevelopment siteVisit) throws Exception {
		siteVisit.setDocs(new ArrayList<Doc>());
		List<ConfigDoc> l = configDocService.findByProcess(ConfigDocProcessEnum.QUALIFICATIONS_CURRICULUM_DEVELOPMENT, CompanyUserTypeEnum.Company);
		if (l != null && l.size() > 0) {
			l.forEach(a -> {
				siteVisit.getDocs().add(new Doc(a));
			});
		}
	}

	public void prepareQualificationsCurriculumDevelopmentDocs(ConfigDocProcessEnum configDocProcess, QualificationsCurriculumDevelopment entityDoc, QualificationsCurriculumDevelopment entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null) {
			if (processRoles == null) {
				entityDoc.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			} else {
				entityDoc.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), processRoles));
			}
		}
	}

	public void prepareNewLearnershipDevelopmentRegistrationDocs(ConfigDocProcessEnum configDocProcess, QualificationsCurriculumDevelopment entityDoc, QualificationsCurriculumDevelopment entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null) {

			if (processRoles == null) {
				entityDoc.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			} else {
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

	public void submitQualificationsCurriculumDevelopment(QualificationsCurriculumDevelopment qualificationsCurriculumDevelopment, Users user) throws Exception {
		ConfigDocProcessEnum configDocProcessEnum = ConfigDocProcessEnum.QUALIFICATIONS_CURRICULUM_DEVELOPMENT;
		List<IDataEntity> dataEntities = new ArrayList<>();
		qualificationsCurriculumDevelopment.setSubmitDate(new Date(System.currentTimeMillis()));
		qualificationsCurriculumDevelopment.setStatus(ApprovalEnum.PendingApproval);
		dataEntities.add(qualificationsCurriculumDevelopment);

		dao.createBatch(dataEntities);
		// Creates documents assigned to user
		List<IDataEntity> docsDataEntities = new ArrayList<IDataEntity>();
		if (qualificationsCurriculumDevelopment.getDocs() != null) {
			for (Doc doc : qualificationsCurriculumDevelopment.getDocs()) {
				doc.setCompany(null);
				doc.setVersionNo(1);
				// doc.setForUsers(formUser);
				doc.setUsers(user);
				doc.setTargetKey(qualificationsCurriculumDevelopment.getId());
				doc.setTargetClass(QualificationsCurriculumDevelopment.class.getName());
				docsDataEntities.add(doc);
				if (doc.getData() != null) {
					docsDataEntities.add(new DocByte(doc.getData(), doc));
					docsDataEntities.add(new DocumentTracker(doc, user, new java.util.Date(), DocumentTrackerEnum.Upload));
				} else {
					throw new Exception("Please upload required document ");
				}
			}
		}

		if (docsDataEntities.size() > 0) {
			dao.createBatch(docsDataEntities);
		}

		TasksService.instance().findFirstInProcessAndCreateTask("", user, qualificationsCurriculumDevelopment.getId(), QualificationsCurriculumDevelopment.class.getName(), true, configDocProcessEnum, null, null);

		if (qualificationsCurriculumDevelopment.getTemplateType() == QCDTemplateTypeEnum.NewDevelopment) {
			sendAcknowledgementEmailForNewDevelopment(user, qualificationsCurriculumDevelopment);
		} else if (qualificationsCurriculumDevelopment.getTemplateType() == QCDTemplateTypeEnum.ReAlignment) {
			sendAcknowledgementEmailForReAlignment(user, qualificationsCurriculumDevelopment);
		} else if (qualificationsCurriculumDevelopment.getTemplateType() == QCDTemplateTypeEnum.Review) {
			sendAcknowledgementEmailForReview(user, qualificationsCurriculumDevelopment);
		}
	}

	public void reSubmitQualificationsCurriculumDevelopment(QualificationsCurriculumDevelopment qualificationsCurriculumDevelopment, Users user, Tasks tasks) throws Exception {
		ConfigDocProcessEnum configDocProcessEnum = ConfigDocProcessEnum.QUALIFICATIONS_CURRICULUM_DEVELOPMENT;

		qualificationsCurriculumDevelopment.setSubmitDate(new Date(System.currentTimeMillis()));
		qualificationsCurriculumDevelopment.setStatus(ApprovalEnum.PendingApproval);

		dao.update(qualificationsCurriculumDevelopment);
		TasksService.instance().findFirstInProcessAndCreateTask("", user, qualificationsCurriculumDevelopment.getId(), QualificationsCurriculumDevelopment.class.getName(), true, configDocProcessEnum, null, null);
		TasksService.instance().completeTask(tasks);

		if (qualificationsCurriculumDevelopment.getTemplateType() == QCDTemplateTypeEnum.NewDevelopment) {
			sendAcknowledgementEmailForNewDevelopment(user, qualificationsCurriculumDevelopment);
		} else if (qualificationsCurriculumDevelopment.getTemplateType() == QCDTemplateTypeEnum.ReAlignment) {
			sendAcknowledgementEmailForReAlignment(user, qualificationsCurriculumDevelopment);
		} else if (qualificationsCurriculumDevelopment.getTemplateType() == QCDTemplateTypeEnum.Review) {
			sendAcknowledgementEmailForReview(user, qualificationsCurriculumDevelopment);
		}
	}

	public void completeQualificationsCurriculumDevelopment(QualificationsCurriculumDevelopment qualificationsCurriculumDevelopment, Users user, Tasks tasks) throws Exception {
		uploadDocuments(qualificationsCurriculumDevelopment, tasks, user);
		qualificationsCurriculumDevelopment.setStatus(ApprovalEnum.PendingApproval);
		create(qualificationsCurriculumDevelopment);
		storeDocs(qualificationsCurriculumDevelopment, tasks, user);
		TasksService.instance().findNextInProcessAndCreateTask("", user, qualificationsCurriculumDevelopment.getId(), QualificationsCurriculumDevelopment.class.getName(), true, tasks, null, null);
	}

	public void approveQualificationsCurriculumDevelopment(QualificationsCurriculumDevelopment qualificationsCurriculumDevelopment, Users user, Tasks tasks, Users createUser) throws Exception {
		uploadDocuments(qualificationsCurriculumDevelopment, tasks, user);
		qualificationsCurriculumDevelopment.setStatus(ApprovalEnum.WaitingForManager);
		qualificationsCurriculumDevelopment.setApprovalDate(getSynchronizedDate());
		create(qualificationsCurriculumDevelopment);
		storeDocs(qualificationsCurriculumDevelopment, tasks, user);
		TasksService.instance().findNextInProcessAndCreateTask("", user, qualificationsCurriculumDevelopment.getId(), QualificationsCurriculumDevelopment.class.getName(), true, tasks, null, null);

		if (qualificationsCurriculumDevelopment.getTemplateType() == QCDTemplateTypeEnum.NewDevelopment) {
			sendManagerEmailForNewDevelopment(createUser, qualificationsCurriculumDevelopment);
		} else if (qualificationsCurriculumDevelopment.getTemplateType() == QCDTemplateTypeEnum.ReAlignment) {
			sendManagerEmailForReAlignment(createUser, qualificationsCurriculumDevelopment);
		} else if (qualificationsCurriculumDevelopment.getTemplateType() == QCDTemplateTypeEnum.Review) {
			sendManagerEmailForReview(createUser, qualificationsCurriculumDevelopment);
		}
	}

	public void rejectQualificationsCurriculumDevelopment(QualificationsCurriculumDevelopment qualificationsCurriculumDevelopment, Users user, Tasks tasks, ArrayList<RejectReasons> selectedRejectReason, Users createUser) throws Exception {
		qualificationsCurriculumDevelopment.setStatus(ApprovalEnum.Rejected);
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(qualificationsCurriculumDevelopment.getId(), QualificationsCurriculumDevelopment.class.getName(), selectedRejectReason, user, ConfigDocProcessEnum.QUALIFICATIONS_CURRICULUM_DEVELOPMENT);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}

		}
		create(qualificationsCurriculumDevelopment);
		storeDocs(qualificationsCurriculumDevelopment, tasks, user);
		if (tasks.getFirstInProcess()) {
			String desc = "The new Qualification Development application submitted by #FIRST_NAME# #LAST_NAME# (#IDENTITY_NUMBER#) has not been approved. Please review the application.";
			desc = desc.replaceAll("#FIRST_NAME#", qualificationsCurriculumDevelopment.getCreateUser().getFirstName());
			desc = desc.replaceAll("#LAST_NAME#", qualificationsCurriculumDevelopment.getCreateUser().getLastName());
			desc = desc.replaceAll("#IDENTITY_NUMBER#", qualificationsCurriculumDevelopment.getCreateUser().getRsaIDNumber());
			List<Users> users = new ArrayList<>();
			users.add(qualificationsCurriculumDevelopment.getCreateUser());
			TasksService.instance().createTaskUser(users, QualificationsCurriculumDevelopment.class.getName(), qualificationsCurriculumDevelopment.getId(), desc, user, true, true, tasks, ConfigDocProcessEnum.QUALIFICATIONS_CURRICULUM_DEVELOPMENT, false);
			TasksService.instance().completeTask(tasks);
		} else {
			TasksService.instance().findPreviousInProcessAndCreateTask("", user, qualificationsCurriculumDevelopment.getId(), QualificationsCurriculumDevelopment.class.getName(), true, tasks, null, null);
		}
		if (qualificationsCurriculumDevelopment.getTemplateType() == QCDTemplateTypeEnum.NewDevelopment) {
			sendRejectManagerEmailForNewDevelopment(user, qualificationsCurriculumDevelopment, rrmList);
		} else if (qualificationsCurriculumDevelopment.getTemplateType() == QCDTemplateTypeEnum.ReAlignment) {
			sendRejectManagerEmailForReAlignment(user, qualificationsCurriculumDevelopment, rrmList);
		} else if (qualificationsCurriculumDevelopment.getTemplateType() == QCDTemplateTypeEnum.Review) {
			sendRejectManagerEmailForReview(user, qualificationsCurriculumDevelopment, rrmList);
		}
	}

	public void rejectQualificationsCurriculumDevelopmentToApplicant(QualificationsCurriculumDevelopment qualificationsCurriculumDevelopment, Users user, Tasks tasks, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		qualificationsCurriculumDevelopment.setStatus(ApprovalEnum.Rejected);
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(qualificationsCurriculumDevelopment.getId(), QualificationsCurriculumDevelopment.class.getName(), selectedRejectReason, user, ConfigDocProcessEnum.QUALIFICATIONS_CURRICULUM_DEVELOPMENT);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}

		}
		create(qualificationsCurriculumDevelopment);
		storeDocs(qualificationsCurriculumDevelopment, tasks, user);
		// TasksService.instance().findFirstInProcessAndCreateTask("", user,
		// qualificationsCurriculumDevelopment.getId(),
		// QualificationsCurriculumDevelopment.class.getName(), true,
		// ConfigDocProcessEnum.QUALIFICATIONS_CURRICULUM_DEVELOPMENT, null, null);
		// List<Users> users, String targetClass, Long targetKey, String description,
		// Users createUser, boolean sendMail, boolean createTask, Tasks task,
		// ConfigDocProcessEnum configDocProcessEnum, boolean firstInProcess
		List<Users> users = new ArrayList<>();
		users.add(qualificationsCurriculumDevelopment.getCreateUser());
		TasksService.instance().createTaskUser(users, QualificationsCurriculumDevelopment.class.getName(), qualificationsCurriculumDevelopment.getId(), "", user, true, true, tasks, ConfigDocProcessEnum.QUALIFICATIONS_CURRICULUM_DEVELOPMENT, false);
		TasksService.instance().completeTask(tasks);
	}

	public void finalApproveQualificationsCurriculumDevelopment(QualificationsCurriculumDevelopment qualificationsCurriculumDevelopment, Users user, Tasks tasks, Users createUser) throws Exception {
		uploadDocuments(qualificationsCurriculumDevelopment, tasks, user);
		qualificationsCurriculumDevelopment.setStatus(ApprovalEnum.Approved);
		create(qualificationsCurriculumDevelopment);
		storeDocs(qualificationsCurriculumDevelopment, tasks, user);
		// TasksService.instance().completeTask(tasks);
		TasksService.instance().findNextInProcessAndCreateTask("", user, qualificationsCurriculumDevelopment.getId(), QualificationsCurriculumDevelopment.class.getName(), true, tasks, null, null);
		// startQdfCompanyRegistartion(qualificationsCurriculumDevelopment.getCompany(),
		// user, null, qualificationsCurriculumDevelopment);

		if (qualificationsCurriculumDevelopment.getTemplateType() == QCDTemplateTypeEnum.NewDevelopment) {
			sendApprovalEmailForNewDevelopment(createUser, qualificationsCurriculumDevelopment);
		} else if (qualificationsCurriculumDevelopment.getTemplateType() == QCDTemplateTypeEnum.ReAlignment) {
			sendApprovalEmailForReAlignment(createUser, qualificationsCurriculumDevelopment);
		} else if (qualificationsCurriculumDevelopment.getTemplateType() == QCDTemplateTypeEnum.Review) {
			sendApprovalEmailForReview(createUser, qualificationsCurriculumDevelopment);
		}
	}

	public void completeUploadQualificationsCurriculumDevelopment(QualificationsCurriculumDevelopment qualificationsCurriculumDevelopment, Users user, Tasks tasks, Users createUser) throws Exception {
		uploadDocuments(qualificationsCurriculumDevelopment, tasks, user);
		storeDocs(qualificationsCurriculumDevelopment, tasks, user);
		startQdfCompanyRegistartion(qualificationsCurriculumDevelopment.getCompany(), user, null, qualificationsCurriculumDevelopment);
		TasksService.instance().completeTask(tasks);
		/*
		 * if(qualificationsCurriculumDevelopment.getTemplateType() ==
		 * QCDTemplateTypeEnum.NewDevelopment) {
		 * sendManagerEmailForNewDevelopment(createUser,
		 * qualificationsCurriculumDevelopment); }else
		 * if(qualificationsCurriculumDevelopment.getTemplateType() ==
		 * QCDTemplateTypeEnum.ReAlignment) { sendManagerEmailForReAlignment(createUser,
		 * qualificationsCurriculumDevelopment); }else
		 * if(qualificationsCurriculumDevelopment.getTemplateType() ==
		 * QCDTemplateTypeEnum.Review) { sendManagerEmailForReview(createUser,
		 * qualificationsCurriculumDevelopment); }
		 */
	}

	public void finalRejectQualificationsCurriculumDevelopment(QualificationsCurriculumDevelopment qualificationsCurriculumDevelopment, Users user, Tasks tasks, ArrayList<RejectReasons> selectedRejectReason, Users createUser) throws Exception {
		qualificationsCurriculumDevelopment.setStatus(ApprovalEnum.Rejected);
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(qualificationsCurriculumDevelopment.getId(), QualificationsCurriculumDevelopment.class.getName(), selectedRejectReason, user, ConfigDocProcessEnum.QUALIFICATIONS_CURRICULUM_DEVELOPMENT);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}

		}
		qualificationsCurriculumDevelopment.setApprovalDate(getSynchronizedDate());
		create(qualificationsCurriculumDevelopment);
		storeDocs(qualificationsCurriculumDevelopment, tasks, user);
		TasksService.instance().completeTask(tasks);
		// sendRejectEmail(qualificationsCurriculumDevelopment.getCreateUser());
		// send email LPM template to send out at this point
		if (qualificationsCurriculumDevelopment.getTemplateType() == QCDTemplateTypeEnum.NewDevelopment) {
			sendRejectApprovalEmailForNewDevelopment(createUser, qualificationsCurriculumDevelopment, rrmList);
		} else if (qualificationsCurriculumDevelopment.getTemplateType() == QCDTemplateTypeEnum.ReAlignment) {
			sendRejectApprovalEmailForReAlignment(createUser, qualificationsCurriculumDevelopment, rrmList);
		} else if (qualificationsCurriculumDevelopment.getTemplateType() == QCDTemplateTypeEnum.Review) {
			sendRejectApprovalEmailForReview(createUser, qualificationsCurriculumDevelopment, rrmList);
		}
	}

	private void storeDocs(QualificationsCurriculumDevelopment entity, Tasks tasks, Users users) throws Exception {
		if (tasks.getProcessRole() != null && (tasks.getProcessRole().getRolePermission() == UserPermissionEnum.Upload || tasks.getProcessRole().getRolePermission() == UserPermissionEnum.EditUpload)) {
			for (Doc doc : entity.getDocs()) {
				doc.setTargetKey(entity.getId());
				doc.setTargetClass(SiteVisit.class.getName());
				DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), users);
			}
		}
	}

	private void startQdfCompanyRegistartion(Company company, Users user, Users applicant, QualificationsCurriculumDevelopment entity) throws Exception {
		QdfCompany qdfComapny = null;
		if (company != null) {
			qdfComapny = new QdfCompany(company);
		} else {
			qdfComapny = new QdfCompany();
		}
		qdfComapny.setUser(applicant);
		qdfComapny.setQualificationsCurriculumDevelopment(entity);
		qdfCompanyService.create(qdfComapny);
		TasksService.instance().findFirstInProcessAndCreateTask("", user, qdfComapny.getId(), QdfCompany.class.getName(), true, ConfigDocProcessEnum.QDF_Registration, null, null);
	}

	public void prepareNewRegistration(ConfigDocProcessEnum configDocProcess, QualificationsCurriculumDevelopment entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null && processRoles != null) {
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

	@SuppressWarnings("unchecked")
	public List<Company> allQualificationAsCompany(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select distinct o.company from QualificationsCurriculumDevelopment o where o.id is not null ";
		return (List<Company>) dao.sortAndFilterWhereInfo(first, pageSize, sortField, sortOrder, filters, hql);
		// return (List<Company>)
		// dao.hqlAndParamOnly(QualificationsCurriculumDevelopment.class, first,
		// pageSize, sortField, sortOrder, filters, hql);

	}

	public int countQualificationAsCompany(Class<QualificationsCurriculumDevelopment> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(distinct o.company) from QualificationsCurriculumDevelopment o where o.id is not null ";
		// return dao.countWhere(filters, hql);
		return dao.countWhereInfo(filters, hql);
	}

	/**
	 * Uploads docs uploaded against LearnershipDevelopmentRegistration throws
	 * Exception if doc data is empty for permissions: Upload or EditUpload Only
	 * time documents required if permissions is: FinalUploadApproval or FinalUpload
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void uploadDocuments(QualificationsCurriculumDevelopment entity, Tasks task, Users sessionUser) throws Exception {
		// check if all docs provided for correct permissions
		if (task != null && (task.getProcessRole().getRolePermission() == UserPermissionEnum.Upload || task.getProcessRole().getRolePermission() == UserPermissionEnum.EditUpload || task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalUploadApproval || task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalUpload)) {
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) {
						doc.setData(docByte.getData());
					}
				}
				if (doc.getId() == null) {
					if (task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalUploadApproval || task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalUpload || task.getProcessRole().getRolePermission() == UserPermissionEnum.Upload) {
						throw new Exception("Please ensure all documents are uploaded");
					}
				}
			}
			// // check if data not empty and creates document
			// for (Doc doc : entity.getDocs()) {
			// if (doc.getId() == null && doc.getData() != null) {
			// doc.setTargetKey(entity.getId());
			// doc.setTargetClass(QualificationsCurriculumDevelopment.class.getName());
			// DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(),
			// sessionUser);
			// }
			// }
		}
	}

	public void sendAcknowledgementEmailForNewDevelopment(Users u, QualificationsCurriculumDevelopment qualificationsCurriculumDevelopment) {
		String title = "";
		if (u != null && u.getTitle() != null) {
			title = u.getTitle().getDescription();
		}
		String codedesc = qualificationsCurriculumDevelopment.getOfoCodes().getOfoCodeParent() + " " + qualificationsCurriculumDevelopment.getOfoCodes().getDescription();

		// The merSETA acknowledges receipt of the application for a qualification
		// development against the following OFO code: #OFO Code and Description#.
		String welcome = "<p>Dear #Title# #FirstName# #Surname#,</p>" + "<p>The merSETA acknowledges receipt of the application for a qualification development against the following OFO code: #codedesc#</p>" + "<p>Kindly be advised that it may take up to five (5) working days to process the application.</p>" + "<p>Yours sincerely,</p>" + "<p>Curriculum and Learning Programme Development Unit</p>" + "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#codedesc#", codedesc);
		GenericUtility.sendMail(u.getEmail(), "Acknowledgement of Qualification Development", welcome, null);
	}

	public void sendAcknowledgementEmailForReview(Users u, QualificationsCurriculumDevelopment qualificationsCurriculumDevelopment) throws Exception {
		String title = "";
		if (u != null && u.getTitle() != null) {
			title = u.getTitle().getDescription();
		}
		QualificationService ser = new QualificationService();
		Qualification qual = ser.findByKey(Long.parseLong(qualificationsCurriculumDevelopment.getReviewQualificationIdList()));
		// The merSETA acknowledges receipt of the application for a qualification
		// development against the following qualification: #Qualification Code#
		// #Qualification Title#.
		String codedesc = qual.getCode() + " " + qual.getDescription();
		String welcome = "<p>Dear #Title# #FirstName# #Surname#,</p>" + "<p>The merSETA acknowledges receipt of the application for a qualification development against the following qualification: #codedesc#</p>" + "<p>Kindly be advised that it may take up to five (5) working days to process the application.  </p>" + "<p>Yours sincerely,</p>" + "<p>Curriculum and Learning Programme Development Unit</p>" + "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#codedesc#", codedesc);
		GenericUtility.sendMail(u.getEmail(), "Acknowledgement of Qualification Development", welcome, null);
	}

	public void sendAcknowledgementEmailForReAlignment(Users u, QualificationsCurriculumDevelopment qualificationsCurriculumDevelopment) throws Exception {
		String title = "";
		if (u != null && u.getTitle() != null) {
			title = u.getTitle().getDescription();
		}
		QualificationService ser = new QualificationService();
		Qualification qual = ser.findByKey(Long.parseLong(qualificationsCurriculumDevelopment.getReAlignmentQualificationIdList()));
		// The merSETA acknowledges receipt of the application for a qualification
		// development against the following qualification: #Qualification Code#
		// #Qualification Title#.
		String codedesc = qual.getCode() + " " + qual.getDescription();

		String welcome = "<p>Dear #Title# #FirstName# #Surname#,</p>" + "<p>The merSETA acknowledges receipt of the application for a qualification development against the following qualification: #codedesc#</p>" + "<p>Kindly be advised that it may take up to five (5) working days to process the application.</p>" + "<p>Yours sincerely,</p>" + "<p>Curriculum and Learning Programme Development Unit</p>" + "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#codedesc#", codedesc);
		GenericUtility.sendMail(u.getEmail(), "Acknowledgement of Qualification Development", welcome, null);
	}

	public void sendManagerEmailForNewDevelopment(Users u, QualificationsCurriculumDevelopment qualificationsCurriculumDevelopment) throws Exception {
		String title = "";
		if (u != null && u.getTitle() != null) {
			title = u.getTitle().getDescription();
		}
		String codedesc = qualificationsCurriculumDevelopment.getOfoCodes().getOfoCodeParent();
		String welcome = "<p>Dear #Title# #FirstName# #Surname#,</p>" + "The merSETA hereby advises that the application for a new qualification against the following OFO Code: #codedesc# has been reviewed and an application will now be submitted to the QCTO." + "<p>Please note that the merSETA may revert and request additional information.</p>"
				+ "<p>Kindly be advised that the application process with the QCTO may take longer than 30 days. The merSETA will communicate the outcome once the QCTO has reverted to the merSETA. </p>" + "<p>Yours sincerely,</p>" + "<p>Curriculum and Learning Programme Development Unit</p>" + "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#codedesc#", codedesc);
		GenericUtility.sendMail(u.getEmail(), "Notification of Submission of Application to the Quality Council for Trades and Occupations(QCTO)", welcome, null);
	}

	public void sendManagerEmailForReAlignment(Users u, QualificationsCurriculumDevelopment qualificationsCurriculumDevelopment) throws NumberFormatException, Exception {
		String title = "";
		if (u != null && u.getTitle() != null) {
			title = u.getTitle().getDescription();
		}
		QualificationService ser = new QualificationService();
		Qualification qual = ser.findByKey(Long.parseLong(qualificationsCurriculumDevelopment.getReAlignmentQualificationIdList()));
		// The merSETA acknowledges receipt of the application for a qualification
		// development against the following qualification: #Qualification Code#
		// #Qualification Title#.
		String codedesc = qual.getCode() + " " + qual.getDescription();

		String welcome = "<p>Dear #Title# #FirstName# #Surname#,</p>" + "The merSETA hereby advises that the application for a new qualification against for a new qualification against the following qualification: #codedesc# has been reviewed and an application will now be submitted to the QCTO." + "<p>Please note that the merSETA may revert and request additional information.</p>"
				+ "<p>Kindly be advised that the application process with the QCTO may take longer than 30 days. The merSETA will communicate the outcome once the QCTO has reverted to the merSETA. </p>" + "<p>Yours sincerely,</p>" + "<p>Curriculum and Learning Programme Development Unit</p>" + "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#codedesc#", codedesc);
		GenericUtility.sendMail(u.getEmail(), "Notification of Submission of Application to the Quality Council for Trades and Occupations(QCTO)", welcome, null);
	}

	public void sendManagerEmailForReview(Users u, QualificationsCurriculumDevelopment qualificationsCurriculumDevelopment) throws NumberFormatException, Exception {
		String title = "";
		if (u != null && u.getTitle() != null) {
			title = u.getTitle().getDescription();
		}
		QualificationService ser = new QualificationService();
		Qualification qual = ser.findByKey(Long.parseLong(qualificationsCurriculumDevelopment.getReviewQualificationIdList()));
		// The merSETA acknowledges receipt of the application for a qualification
		// development against the following qualification: #Qualification Code#
		// #Qualification Title#.
		String codedesc = qual.getCode() + " " + qual.getDescription();
		String welcome = "<p>Dear #Title# #FirstName# #Surname#,</p>" + "The merSETA hereby advises that the application for a new qualification against for a new qualification against the following qualification: #codedesc# has been reviewed and an application will now be submitted to the QCTO." + "<p>Please note that the merSETA may revert and request additional information.</p>"
				+ "<p>Kindly be advised that the application process with the QCTO may take longer than 30 days. The merSETA will communicate the outcome once the QCTO has reverted to the merSETA. </p>" + "<p>Yours sincerely,</p>" + "<p>Curriculum and Learning Programme Development Unit</p>" + "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#codedesc#", codedesc);
		GenericUtility.sendMail(u.getEmail(), "Notification of Submission of Application to the Quality Council for Trades and Occupations(QCTO)", welcome, null);
	}

	public void sendApprovalEmailForNewDevelopment(Users u, QualificationsCurriculumDevelopment qualificationsCurriculumDevelopment) throws Exception {
		String title = "";
		if (u != null && u.getTitle() != null) {
			title = u.getTitle().getDescription();
		}
		String codedesc = qualificationsCurriculumDevelopment.getOfoCodes().getOfoCodeParent();
		String welcome = "<p>Dear #Title# #FirstName# #Surname#,</p>" + "The merSETA hereby advises that the application for a new qualification against the following OFO Code: #codedesc# has been reviewed by QCTO and was approved." + "<p>Kindly be advised that the qualification development process will now commence and is expected to take longer than 30 days. Please note that the merSETA may revert and request additional information.</p>" + "<p>Yours sincerely,</p>"
				+ "<p>Manager: Curriculum and Learning Programme Development Unit</p>" + "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#codedesc#", codedesc);
		GenericUtility.sendMail(u.getEmail(), "Notification of Submission of Application to the Quality Council for Trades and Occupations(QCTO)", welcome, null);
	}

	public void sendApprovalEmailForReAlignment(Users u, QualificationsCurriculumDevelopment qualificationsCurriculumDevelopment) throws Exception {
		String title = "";
		if (u != null && u.getTitle() != null) {
			title = u.getTitle().getDescription();
		}
		QualificationService ser = new QualificationService();
		Qualification qual = ser.findByKey(Long.parseLong(qualificationsCurriculumDevelopment.getReAlignmentQualificationIdList()));

		String codedesc = qual.getCode() + " " + qual.getDescription();
		String welcome = "<p>Dear #Title# #FirstName# #Surname#,</p>" + "The merSETA hereby advises that the application for a new qualification against for a new qualification against the following qualification: #codedesc# has been reviewed by QCTO and was approved." + "<p>Kindly be advised that the qualification development process will now commence and is expected to take longer than 30 days. Please note that the merSETA may revert and request additional information.</p>"
				+ "<p>Yours sincerely,</p>" + "<p>Manager: Curriculum and Learning Programme Development Unit</p>" + "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#codedesc#", codedesc);
		GenericUtility.sendMail(u.getEmail(), "Notification of Submission of Application to the Quality Council for Trades and Occupations(QCTO)", welcome, null);
	}

	public void sendApprovalEmailForReview(Users u, QualificationsCurriculumDevelopment qualificationsCurriculumDevelopment) throws Exception {
		String title = "";
		if (u != null && u.getTitle() != null) {
			title = u.getTitle().getDescription();
		}
		QualificationService ser = new QualificationService();
		Qualification qual = ser.findByKey(Long.parseLong(qualificationsCurriculumDevelopment.getReviewQualificationIdList()));
		// The merSETA acknowledges receipt of the application for a qualification
		// development against the following qualification: #Qualification Code#
		// #Qualification Title#.
		String codedesc = qual.getCode() + " " + qual.getDescription();
		String welcome = "<p>Dear #Title# #FirstName# #Surname#,</p>" + "The merSETA hereby advises that the application for a new qualification against for a new qualification against the following qualification: #codedesc# has been reviewed by QCTO and was approved." + "<p>Kindly be advised that the qualification development process will now commence and is expected to take longer than 30 days. Please note that the merSETA may revert and request additional information.</p>"
				+ "<p>Yours sincerely,</p>" + "<p>Manager: Curriculum and Learning Programme Development Unit</p>" + "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#codedesc#", codedesc);
		GenericUtility.sendMail(u.getEmail(), "Notification of Submission of Application to the Quality Council for Trades and Occupations(QCTO)", welcome, null);
	}

	public void sendRejectManagerEmailForNewDevelopment(Users u, QualificationsCurriculumDevelopment qualificationsCurriculumDevelopment, List<RejectReasonMultipleSelection> rrmList) throws Exception {
		String title = "";
		if (u != null && u.getTitle() != null) {
			title = u.getTitle().getDescription();
		}
		List<RejectReasons> rejectReasonsList = new ArrayList<>();
		for (RejectReasonMultipleSelection rejectReasonMultipleSelection : rrmList) {
			rejectReasonsList.add(rejectReasonMultipleSelection.getRejectReason());
		}
		String rejectResons = convertToHTML(rejectReasonsList);
		String codedesc = qualificationsCurriculumDevelopment.getOfoCodes().getOfoCodeParent();
		String welcome = "<p>Dear #Title# #FirstName# #Surname#,</p>" 
				+ "The merSETA hereby advises that the application for a new qualification against the following OFO Code: #codedesc# has been reviewed by the QCTO and was not approved for development for the following reason(s) <br/>" 
				+ rejectResons
				+ "<p>Please do not hesitate to contact the merSETA Curriculum and Learning Programmes Unit or the Regional office for further assistance. </p>" 
				+ "<p>Yours sincerely,</p>" 
				+ "<p>Curriculum and Learning Programme Development Unit</p>" 
				+ "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#codedesc#", codedesc);
		GenericUtility.sendMail(qualificationsCurriculumDevelopment.getCreateUser().getEmail(), "Notification of Submission of Application to the Quality Council for Trades and Occupations(QCTO)", welcome, null);
	}

	public void sendRejectManagerEmailForReAlignment(Users u, QualificationsCurriculumDevelopment qualificationsCurriculumDevelopment, List<RejectReasonMultipleSelection> rrmList) throws NumberFormatException, Exception {
		String title = "";
		if (u != null && u.getTitle() != null) {
			title = u.getTitle().getDescription();
		}
		QualificationService ser = new QualificationService();
		Qualification qual = ser.findByKey(Long.parseLong(qualificationsCurriculumDevelopment.getReAlignmentQualificationIdList()));
		// The merSETA acknowledges receipt of the application for a qualification
		// development against the following qualification: #Qualification Code#
		// #Qualification Title#.
		String codedesc = qual.getCode() + " " + qual.getDescription();
		List<RejectReasons> rejectReasonsList = new ArrayList<>();
		for (RejectReasonMultipleSelection rejectReasonMultipleSelection : rrmList) {
			rejectReasonsList.add(rejectReasonMultipleSelection.getRejectReason());
		}
		String rejectResons = convertToHTML(rejectReasonsList);

		String welcome = "<p>Dear #Title# #FirstName# #Surname#,</p>" 
				+ "The merSETA hereby advises that the application for a new qualification against for a new qualification against the following qualification: #codedesc# has been reviewed by the QCTO and was not approved for the following reason(s)" 
				+ rejectResons
				+ "<p>Please do not hesitate to contact the merSETA Curriculum and Learning Programmes Unit or the Regional office for further assistance. </p>" 
				+ "<p>Yours sincerely,</p>"
				+ "<p>Curriculum and Learning Programme Development Unit</p>" + "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#codedesc#", codedesc);
		GenericUtility.sendMail(qualificationsCurriculumDevelopment.getCreateUser().getEmail(), "Notification of Submission of Application to the Quality Council for Trades and Occupations(QCTO)", welcome, null);
	}

	public void sendRejectManagerEmailForReview(Users u, QualificationsCurriculumDevelopment qualificationsCurriculumDevelopment, List<RejectReasonMultipleSelection> rrmList) throws NumberFormatException, Exception {
		String title = "";
		if (u != null && u.getTitle() != null) {
			title = u.getTitle().getDescription();
		}
		QualificationService ser = new QualificationService();
		Qualification qual = ser.findByKey(Long.parseLong(qualificationsCurriculumDevelopment.getReviewQualificationIdList()));
		// The merSETA acknowledges receipt of the application for a qualification
		// development against the following qualification: #Qualification Code#
		// #Qualification Title#.
		String codedesc = qual.getCode() + " " + qual.getDescription();
		List<RejectReasons> rejectReasonsList = new ArrayList<>();
		for (RejectReasonMultipleSelection rejectReasonMultipleSelection : rrmList) {
			rejectReasonsList.add(rejectReasonMultipleSelection.getRejectReason());
		}
		String rejectResons = convertToHTML(rejectReasonsList);
		String welcome = "<p>Dear #Title# #FirstName# #Surname#,</p>" 
				+ "The merSETA hereby advises that the application for a new qualification against for a new qualification against the following qualification: #codedesc# has been reviewed by the QCTO and was not approved for the following reason(s)" 
				+ rejectResons
				+ "<p>Please do not hesitate to contact the merSETA Curriculum and Learning Programmes Unit or the Regional office for further assistance.  </p>" 
				+ "<p>Yours sincerely,</p>"
				+ "<p>Curriculum and Learning Programme Development Unit</p>" + "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#codedesc#", codedesc);
		GenericUtility.sendMail(qualificationsCurriculumDevelopment.getCreateUser().getEmail(), "Notification of Submission of Application to the Quality Council for Trades and Occupations(QCTO)", welcome, null);
	}

	public void sendRejectApprovalEmailForNewDevelopment(Users u, QualificationsCurriculumDevelopment qualificationsCurriculumDevelopment, List<RejectReasonMultipleSelection> rrmList) throws Exception {
		String title = "";
		if (u != null && u.getTitle() != null) {
			title = u.getTitle().getDescription();
		}
		List<RejectReasons> rejectReasonsList = new ArrayList<>();
		for (RejectReasonMultipleSelection rejectReasonMultipleSelection : rrmList) {
			rejectReasonsList.add(rejectReasonMultipleSelection.getRejectReason());
		}
		String rejectResons = convertToHTML(rejectReasonsList);
		String codedesc = qualificationsCurriculumDevelopment.getOfoCodes().getOfoCodeParent() + "  " + qualificationsCurriculumDevelopment.getOfoCodes().getOfoDescription();
		String welcome = "<p>Dear #Title# #FirstName# #Surname#,</p>" 
				+ "The merSETA hereby advises that the application for a new qualification against the following OFO Code: #codedesc# has been reviewed by the QCTO and was not approved for development for the following reason(s)"
				+ rejectResons
				+ "<p>Please do not hesitate to contact the merSETA Curriculum and Learning Programmes Unit or the Regional office for further assistance. </p>" 
				+ "<p>Yours sincerely,</p>" 
				+ "<p>Manager: Curriculum and Learning Programme Development Unit</p>" 
				+ "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#codedesc#", codedesc);
		GenericUtility.sendMail(qualificationsCurriculumDevelopment.getCreateUser().getEmail(), "Notification of Submission of Application to the Quality Council for Trades and Occupations(QCTO)", welcome, null);
	}

	public void sendRejectApprovalEmailForReAlignment(Users u, QualificationsCurriculumDevelopment qualificationsCurriculumDevelopment, List<RejectReasonMultipleSelection> rrmList) throws Exception {
		String title = "";
		if (u != null && u.getTitle() != null) {
			title = u.getTitle().getDescription();
		}
		QualificationService ser = new QualificationService();
		Qualification qual = ser.findByKey(Long.parseLong(qualificationsCurriculumDevelopment.getReAlignmentQualificationIdList()));
		// The merSETA acknowledges receipt of the application for a qualification
		// development against the following qualification: #Qualification Code#
		// #Qualification Title#.
		List<RejectReasons> rejectReasonsList = new ArrayList<>();
		for (RejectReasonMultipleSelection rejectReasonMultipleSelection : rrmList) {
			rejectReasonsList.add(rejectReasonMultipleSelection.getRejectReason());
		}
		String rejectResons = convertToHTML(rejectReasonsList);
		String codedesc = qual.getCode() + " " + qual.getDescription();
		String welcome = "<p>Dear #Title# #FirstName# #Surname#,</p>" 
				+ "The merSETA hereby advises that the application for a new qualification against for a new qualification against the following qualification: #codedesc# has been reviewed by the QCTO and was not approved for development for the following reason(s)" 
				+ rejectResons
				+ "<p>Please do not hesitate to contact the merSETA Curriculum and Learning Programmes Unit or the Regional office for further assistance. </p>" 
				+ "<p>Yours sincerely,</p>"
				+ "<p>Manager: Curriculum and Learning Programme Development Unit</p>" + "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#codedesc#", codedesc);
		GenericUtility.sendMail(qualificationsCurriculumDevelopment.getCreateUser().getEmail(), "Notification of Submission of Application to the Quality Council for Trades and Occupations(QCTO)", welcome, null);
	}

	public void sendRejectApprovalEmailForReview(Users u, QualificationsCurriculumDevelopment qualificationsCurriculumDevelopment, List<RejectReasonMultipleSelection> rrmList) throws Exception {
		String title = "";
		if (u != null && u.getTitle() != null) {
			title = u.getTitle().getDescription();
		}
		QualificationService ser = new QualificationService();
		Qualification qual = ser.findByKey(Long.parseLong(qualificationsCurriculumDevelopment.getReviewQualificationIdList()));
		// The merSETA acknowledges receipt of the application for a qualification
		// development against the following qualification: #Qualification Code#
		// #Qualification Title#.
		String codedesc = qual.getCode() + " " + qual.getDescription();
		List<RejectReasons> rejectReasonsList = new ArrayList<>();
		for (RejectReasonMultipleSelection rejectReasonMultipleSelection : rrmList) {
			rejectReasonsList.add(rejectReasonMultipleSelection.getRejectReason());
		}
		String rejectResons = convertToHTML(rejectReasonsList);
		String welcome = "<p>Dear #Title# #FirstName# #Surname#,</p>" 
				+ "The merSETA hereby advises that the application for a new qualification against for a new qualification against the following qualification: #codedesc# has been reviewed by the QCTO and was not approved for development for the following reason(s)"
				+ rejectResons
				+ "<p>Please do not hesitate to contact the merSETA Curriculum and Learning Programmes Unit or the Regional office for further assistance. </p>" 
				+ "<p>Yours sincerely,</p>"
				+ "<p>Manager: Curriculum and Learning Programme Development Unit</p>" + "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#codedesc#", codedesc);
		GenericUtility.sendMail(qualificationsCurriculumDevelopment.getCreateUser().getEmail(), "Notification of Submission of Application to the Quality Council for Trades and Occupations(QCTO)", welcome, null);
	}
	
	public String convertToHTML(List<RejectReasons> rejectReasons) {

		String sb = "<ul>";
		for (RejectReasons r : rejectReasons) {
			sb += "<li>" + r.getDescription() + "</li>";
		}
		sb += "</ul>";
		return sb;
	}
}
