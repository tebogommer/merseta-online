package haj.com.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.bean.BulkMailBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.LearnershipDevelopmentRegistrationDAO;
import haj.com.entity.Address;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearnersTransfer;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.DocumentTracker;
import haj.com.entity.LearnershipDevelopmentRegistration;
import haj.com.entity.ProcessRoles;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.DocumentRequiredCheckEnum;
import haj.com.entity.enums.DocumentTrackerEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.LearnershipService;
import haj.com.ui.LearnershipDevelopementUI;
import haj.com.utils.GenericUtility;

public class LearnershipDevelopmentRegistrationService extends AbstractService {
	/** The dao. */
	private LearnershipDevelopmentRegistrationDAO dao = new LearnershipDevelopmentRegistrationDAO();

	/** Service layers */
	private ConfigDocService configDocService = new ConfigDocService();
	private YesNoLookupService yesNoService = new YesNoLookupService();

	/**
	 * Get all LearnershipDevelopmentRegistration
	 * 
	 * @author TechFinium
	 * @see LearnershipDevelopmentRegistration
	 * @return a list of {@link haj.com.entity.LearnershipDevelopmentRegistration}
	 * @throws Exception
	 *             the exception
	 */
	public List<LearnershipDevelopmentRegistration> allLearnershipDevelopmentRegistration() throws Exception {
		return dao.allLearnershipDevelopmentRegistration();
	}

	/**
	 * Create or update LearnershipDevelopmentRegistration.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LearnershipDevelopmentRegistration
	 */
	public void create(LearnershipDevelopmentRegistration entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update LearnershipDevelopmentRegistration.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LearnershipDevelopmentRegistration
	 */
	public void update(LearnershipDevelopmentRegistration entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LearnershipDevelopmentRegistration.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LearnershipDevelopmentRegistration
	 */
	public void delete(LearnershipDevelopmentRegistration entity) throws Exception {
		this.dao.delete(entity);
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
	
	public void prepareNewLearnershipDevelopmentRegistrationDocs(ConfigDocProcessEnum configDocProcess, LearnershipDevelopmentRegistration entityDoc, LearnershipDevelopmentRegistration entity, ProcessRoles processRoles) throws Exception {
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
	
	public void prepareNewRegistration(ConfigDocProcessEnum configDocProcess, LearnershipDevelopmentRegistration entity, ProcessRoles processRoles) throws Exception {
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

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LearnershipDevelopmentRegistration}
	 * @throws Exception
	 *             the exception
	 * @see LearnershipDevelopmentRegistration
	 */
	public LearnershipDevelopmentRegistration findByKey(long id) throws Exception {
		return populateAdditionalInformation(dao.findByKey(id));
	}
	
	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LearnershipDevelopmentRegistration}
	 * @throws Exception
	 *             the exception
	 * @see LearnershipDevelopmentRegistration
	 */
	public LearnershipDevelopmentRegistration findByKeyAndSetDocs(long id,ProcessRoles processRoles) throws Exception {
		LearnershipDevelopmentRegistration learnershipDevelopmentRegistration=dao.findByKey(id);
		prepareNewLearnershipDevelopmentRegistrationDocs(ConfigDocProcessEnum.LEARNERSHIP_DEVELOPMENT_REGISTRATION, learnershipDevelopmentRegistration, learnershipDevelopmentRegistration, processRoles);
		
		return learnershipDevelopmentRegistration;
	}
	
	public void prepareTransferDocumentByProcess(ConfigDocProcessEnum configDocProcess, LearnershipDevelopmentRegistration entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), processRoles);

			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
			
			if(entity.getDocs() != null && entity.getDocs().size()>0) {
				for(Doc doc:entity.getDocs()) {
					if(doc != null && doc.getConfigDoc() !=null && doc.getConfigDoc().getProcessRoles() !=null && processRoles != null && doc.getConfigDoc().getProcessRoles().equals(processRoles)) {
						doc.setEnableReupload(true);
					}
				}
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

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LearnershipDevelopmentRegistration}
	 * @throws Exception
	 *             the exception
	 * @see LearnershipDevelopmentRegistration
	 */
	public LearnershipDevelopmentRegistration findByKeyNoAdditionalInfo(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LearnershipDevelopmentRegistration by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LearnershipDevelopmentRegistration}
	 * @throws Exception
	 *             the exception
	 * @see LearnershipDevelopmentRegistration
	 */
	public List<LearnershipDevelopmentRegistration> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LearnershipDevelopmentRegistration
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LearnershipDevelopmentRegistration}
	 * @throws Exception
	 *             the exception
	 */
	public List<LearnershipDevelopmentRegistration> allLearnershipDevelopmentRegistration(int first, int pageSize) throws Exception {
		return dao.allLearnershipDevelopmentRegistration(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LearnershipDevelopmentRegistration for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LearnershipDevelopmentRegistration
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LearnershipDevelopmentRegistration.class);
	}

	/**
	 * Lazy load LearnershipDevelopmentRegistration with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LearnershipDevelopmentRegistration class
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
	 * @return a list of {@link haj.com.entity.LearnershipDevelopmentRegistration}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LearnershipDevelopmentRegistration> allLearnershipDevelopmentRegistration(Class<LearnershipDevelopmentRegistration> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		List<LearnershipDevelopmentRegistration>list = (List<LearnershipDevelopmentRegistration>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
		populateAdditionalInformationList(list);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<LearnershipDevelopmentRegistration> allUserLearnershipDevelopmentRegistration( int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql="select o from LearnershipDevelopmentRegistration o where o.users = :users ";
		List<LearnershipDevelopmentRegistration>list = (List<LearnershipDevelopmentRegistration>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
		populateAdditionalInformationList(list);
		return list;
	}

	/**
	 * Get count of LearnershipDevelopmentRegistration for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LearnershipDevelopmentRegistration class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LearnershipDevelopmentRegistration entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LearnershipDevelopmentRegistration> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	public int countUserlearnership(Class<LearnershipDevelopmentRegistration> entity, Map<String, Object> filters) throws Exception {
		String hql="select count(o) from LearnershipDevelopmentRegistration o where o.users = :users ";
		return dao.countWhere(filters, hql);
	}

	/**
	 * JMB 07 05 2018
	 * 
	 * Find LearnershipDevelopmentRegistration by company Id
	 * 
	 * @param company
	 * @return List<LearnershipDevelopmentRegistration>
	 * @throws Exception
	 */
	public List<LearnershipDevelopmentRegistration> findByCompany(Company company) throws Exception {
		return dao.findByCompany(company.getId());
	}

	/**
	 * JMB 07 05 2018
	 * 
	 * Locates LearnershipDevelopmentRegistration by company id
	 * 
	 * @param class1
	 * @param first
	 * @param pageSize
	 * @param sortField
	 * @param sortOrder
	 * @param filters
	 * @param company
	 * @return List<LearnershipDevelopmentRegistration>
	 * @throws Exception
	 */
	public List<LearnershipDevelopmentRegistration> allLearnershipDevelopmentRegistrationByCompany(Class<LearnershipDevelopmentRegistration> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company company) throws Exception {
		return dao.allLearnershipDevelopmentRegistrationByCompany(class1, first, pageSize, sortField, sortOrder, filters, company.getId());
	}

	/**
	 * JMB 07 05 2018
	 * 
	 * Counts LearnershipDevelopmentRegistration by company id
	 * 
	 * @param filters
	 * @param company
	 * @return long
	 * @throws Exception
	 */
	public long allLearnershipDevelopmentRegistrationByCompanyCount(Map<String, Object> filters, Company company) throws Exception {
		return dao.allLearnershipDevelopmentRegistrationByCompanyCount(filters, company.getId());
	}

	/**
	 * Apply interveion data
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void applyInterventionData(LearnershipDevelopmentRegistration entity) throws Exception {
		if (entity.getInterventionType() == null) {
			entity.setPivotNonPivot(null);
		} else {
			entity.setPivotNonPivot(entity.getInterventionType().getPivotNonPivot());
			if (entity.getPivotNonPivot() == PivotNonPivotEnum.Pivotal) {
				entity.setNqfAligned(yesNoService.findByKey(YesNoEnum.Yes.getYesNoLookupId()));
			} else {
				entity.setNqfAligned(yesNoService.findByKey(HAJConstants.NO_ID));
			}
		}
	}

	/**
	 * Populates additional information for a list of
	 * LearnershipDevelopmentRegistration
	 * 
	 * @param entityList
	 * @return entityList
	 * @throws Exception
	 */
	private List<LearnershipDevelopmentRegistration> populateAdditionalInformationList(List<LearnershipDevelopmentRegistration> entityList) throws Exception {
		for (LearnershipDevelopmentRegistration learnershipDevelopmentRegistration : entityList) {
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
	private LearnershipDevelopmentRegistration populateAdditionalInformation(LearnershipDevelopmentRegistration entity) throws Exception {
		// preps docs against LearnershipDevelopmentRegistration
		prepareNewDocs(entity, ConfigDocProcessEnum.LEARNERSHIP_DEVELOPMENT_REGISTRATION, CompanyUserTypeEnum.Company);
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
	public LearnershipDevelopmentRegistration prepareNewDocs(LearnershipDevelopmentRegistration entity, ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
		entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
		List<ConfigDoc> l = configDocService.findByProcessNotUploaded(entity.getClass().getName(), entity.getId(), configDocProcess, companyUserType);
		if (l != null && l.size() > 0) {
			l.forEach(a -> {
				entity.getDocs().add(new Doc(a));
			});
		}
		return entity;
	}

	/**
	 * Uploads docs uploaded against LearnershipDevelopmentRegistration throws
	 * Exception if doc data is empty for permissions: Upload or EditUpload Only
	 * time documents required if permissions is: FinalUploadApproval or FinalUpload
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void uploadDocuments(LearnershipDevelopmentRegistration entity, Tasks task, Users sessionUser) throws Exception {
		// check if all docs provided for correct permissions
		if (task != null && (task.getProcessRole().getRolePermission() == UserPermissionEnum.Upload || task.getProcessRole().getRolePermission() == UserPermissionEnum.EditUpload || task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalUploadApproval || task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalUpload || task.getProcessRole().getRolePermission() == UserPermissionEnum.UploadApprove)) {
			//prepareNewRegistration(task.getWorkflowProcess(), entity, task.getProcessRole());
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
					doc.setTargetClass(LearnershipDevelopmentRegistration.class.getName());
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), sessionUser);
				}else{
					//if(doc.getConfigDoc().getConfigDocProcess().getConfigDocProcessEnumByValue(1).compareTo(doc.getConfigDoc().getProcessRoles().getRoleOrder())  ) 
					if (doc.getData() != null) {
						if(doc.getId() == null) {
							doc.setTargetKey(entity.getId());
							doc.setTargetClass(LearnershipDevelopmentRegistration.class.getName());
							DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), sessionUser);
						}
					}else {
						throw new Exception("Please ensure all documents are uploaded");
					}
				}
			}
		}
	}

	/**
	 * Creates and submits the new LearnershipDevelopmentRegistration
	 * 
	 * @param sessionUser
	 * @param entity
	 * @throws Exception
	 */
	public void submitNewLearnership(Users sessionUser, LearnershipDevelopmentRegistration entity, boolean sendMail) throws Exception {
		List<IDataEntity> iDataEntityList = new ArrayList<IDataEntity>();

		// adds new entry
		iDataEntityList.add(entity);
		// batch create
		dao.createBatch(iDataEntityList);
		// Creates documents assigned to user
		List<IDataEntity> docsDataEntities = new ArrayList<IDataEntity>();
		if (entity.getDocs() != null) {
			for (Doc doc : entity.getDocs()) {
				doc.setCompany(null);
				doc.setVersionNo(1);
				//doc.setForUsers(formUser);
				doc.setUsers(sessionUser);
				doc.setTargetKey(entity.getId());
				doc.setTargetClass(LearnershipDevelopmentRegistration.class.getName());
				docsDataEntities.add(doc);
				if(doc.getData() != null)
				{
					docsDataEntities.add(new DocByte(doc.getData(), doc));
					docsDataEntities.add(new DocumentTracker(doc, sessionUser, new java.util.Date(), DocumentTrackerEnum.Upload));
				}
				else
				{
					throw new Exception("Please upload required document ");
				}
			}
		}
		
		if (docsDataEntities.size() > 0) {
			dao.createBatch(docsDataEntities);
		}
		
		// creates the new task
		TasksService.instance().findFirstInProcessAndCreateTask("", sessionUser, entity.getId(), LearnershipDevelopmentRegistration.class.getName(), true, ConfigDocProcessEnum.LEARNERSHIP_DEVELOPMENT_REGISTRATION, null, null);
	
		//send email
		sendAcknoledgementEmail(sessionUser, entity);
	
	}
	
	public void submitLearnership(Users sessionUser, LearnershipDevelopmentRegistration entity, Tasks tasks) throws Exception {

		// adds new entry
		entity.setApprovalEnum(ApprovalEnum.PendingApproval);

		// batch create
		dao.update(entity);
		
		// Creates documents assigned to user
		List<IDataEntity> docsDataEntities = new ArrayList<IDataEntity>();
		if (entity.getDocs() != null) {
			for (Doc doc : entity.getDocs()) {
				doc.setCompany(null);
				doc.setVersionNo(1);
				//doc.setForUsers(formUser);
				doc.setUsers(sessionUser);
				doc.setTargetKey(entity.getId());
				doc.setTargetClass(LearnershipDevelopmentRegistration.class.getName());
				docsDataEntities.add(doc);
				if(doc.getData() != null)
				{
					docsDataEntities.add(new DocByte(doc.getData(), doc));
					docsDataEntities.add(new DocumentTracker(doc, sessionUser, new java.util.Date(), DocumentTrackerEnum.Upload));
				}
			}
		}
		
		if (docsDataEntities.size() > 0) {
			dao.createBatch(docsDataEntities);
		}
		
		// creates the new task
		TasksService.instance().findFirstInProcessAndCreateTask("", sessionUser, entity.getId(), LearnershipDevelopmentRegistration.class.getName(), true, ConfigDocProcessEnum.LEARNERSHIP_DEVELOPMENT_REGISTRATION, null, null);
		TasksService.instance().completeTask(tasks);
		//send email
		sendAcknoledgementEmail(sessionUser, entity);
	
	}
	
	public void sendAcknoledgementEmail(Users u, LearnershipDevelopmentRegistration entity) {
		String title ="";
		if(u.getTitle()!=null) {
			title = u.getTitle().getDescription();
		}
		String welcome = "<p>Dear #Title# #FirstName# #Surname#,</p>" 
						+ "<p>The merSETA acknowledges receipt of the application for a learnership against the following qualification: #QualificationCode# #QualificationTitle#.</p>"
						+ "<p>Kindly be advised that it may take up to five (5) working days to review the application. </p>"
						+ "<p>Yours sincerely,</p>" 
						+ "<p>Curriculum and Learning Programme Development Unit</p>" 
						+ "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#QualificationCode#", entity.getQualification().getCodeString());
		welcome = welcome.replaceAll("#QualificationTitle#", entity.getQualification().getDescription());
		GenericUtility.sendMail(u.getEmail(), "ACKNOWLEDGEMENT OF LEARNERSHIP APPLICATION", welcome, null);
	}
	
	public void sendApprovalEmail(Users u, LearnershipDevelopmentRegistration learnershipDevelopmentRegistration) {
		
		String title ="";
		if(u.getTitle()!=null) {
			title = u.getTitle().getDescription();
		}
		String welcome = "<p>Dear #Title# #FirstName# #Surname#,</p>" 				
						+ "<p>The merSETA acknowledges receipt of the application for a learnership against the following qualification: #QualificationCode# #QualificationTitle#.</p>" 
						+ "<p>Kindly be advised that it may take up to five (5) working days to process the application.  </p>"
						+ "<p>Yours sincerely,</p>" 
						+ "<p>Curriculum and Learning Programme Development Unit</p>" 
						+ "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#QualificationCode#", learnershipDevelopmentRegistration.getQualification().getCodeString());
		welcome = welcome.replaceAll("#QualificationTitle#", learnershipDevelopmentRegistration.getQualification().getDescription());
		
		GenericUtility.sendMail(u.getEmail(), "LEARNERSHIP APPLICATION OUTCOME: APPROVAL", welcome, null);
	}
	
	public void sendBulkMailToSelected(List<Users> list, String emailContents,LearnershipDevelopmentRegistration learnershipDevelopmentRegistration) {
		List<BulkMailBean> users = new ArrayList<>();
		for (Users user : list) {
			BulkMailBean bean = new BulkMailBean(user, replaceText(emailContents, learnershipDevelopmentRegistration, user));
			users.add(bean);
		}
		
		if (users.size() != 0) {
			GenericUtility.sendMailToSelectedUsers("LEARNERSHIP APPLICATION OUTCOME: APPROVAL", emailContents, null, users);
		}
	}
	
	public void sendRejectBulkMailSelected(List<Users> list, String emailContents,LearnershipDevelopmentRegistration learnershipDevelopmentRegistration) {
		List<BulkMailBean> users = new ArrayList<>();
		for (Users user : list) {
			BulkMailBean bean = new BulkMailBean(user, replaceRejectText(emailContents, learnershipDevelopmentRegistration, user));
			users.add(bean);
		}
		
		if (users.size() != 0) {
			GenericUtility.sendMailToSelectedUsers("LEARNERSHIP APPLICATION OUTCOME: REJECTED", emailContents, null, users);
		}
	}
	
	public String replaceText(String mailContents, LearnershipDevelopmentRegistration learnershipDevelopmentRegistration, Users u){
		
		String newContents = mailContents;
		
		String title = "";String QualificationCode="";String QualificationTitle ="";String SaqaTitleDescription ="";String NqfLevel=""; String RegistrationNumber="";
		
		if(u.getTitle()!=null) {title = u.getTitle().getDescription();}
		
		if(learnershipDevelopmentRegistration.getQualification()!=null) {
			QualificationCode = learnershipDevelopmentRegistration.getQualification().getCodeString();
			QualificationTitle= learnershipDevelopmentRegistration.getQualification().getDescription();
			SaqaTitleDescription=learnershipDevelopmentRegistration.getQualification().getDescription();
			NqfLevel= learnershipDevelopmentRegistration.getQualification().getNqflevel().getDescription();
		}
		
		RegistrationNumber = learnershipDevelopmentRegistration.getRegistrationNumber();
		
		newContents = newContents.replace("#Title#", title);
		newContents = newContents.replace("#FirstName#", u.getFirstName());
		newContents = newContents.replace("#Surname#", u.getLastName());
		newContents = newContents.replace("#QualificationCode#", QualificationCode);
		newContents = newContents.replace("#QualificationTitle#", QualificationTitle);				
		newContents = newContents.replace("#SaqaTitleDescription#", SaqaTitleDescription);
		newContents = newContents.replace("#NqfLevel#", NqfLevel);
		newContents = newContents.replace("#RegistrationNumber#", RegistrationNumber);
		
		return newContents;
	}
	
	public String replaceRejectText(String mailContents, LearnershipDevelopmentRegistration learnershipDevelopmentRegistration, Users u){
		
		String newContents = mailContents;
		
		String title = "";String QualificationCode="";String QualificationTitle ="";String SaqaTitleDescription ="";String NqfLevel=""; String RegistrationNumber="";
		
		if(u.getTitle()!=null) {title = u.getTitle().getDescription();}
		
		if(learnershipDevelopmentRegistration.getQualification()!=null) {
			QualificationCode = learnershipDevelopmentRegistration.getQualification().getCodeString();
			QualificationTitle= learnershipDevelopmentRegistration.getQualification().getDescription();
			SaqaTitleDescription=learnershipDevelopmentRegistration.getQualification().getDescription();
			NqfLevel= learnershipDevelopmentRegistration.getQualification().getNqflevel().getDescription();
		}
		
		RegistrationNumber = learnershipDevelopmentRegistration.getRegistrationNumber();
		
		newContents = newContents.replace("#Title#", title);
		newContents = newContents.replace("#FirstName#", u.getFirstName());
		newContents = newContents.replace("#Surname#", u.getLastName());
		newContents = newContents.replace("#QualificationCode#", QualificationCode);
		newContents = newContents.replace("#QualificationTitle#", QualificationTitle);				
		newContents = newContents.replace("#SaqaTitleDescription#", SaqaTitleDescription);
		newContents = newContents.replace("#NqfLevel#", NqfLevel);

		
		return newContents;
	}
	
	public void sendApprovalEmailToList(List<Users> list, LearnershipDevelopmentRegistration learnershipDevelopmentRegistration) {
		
		
		String welcome = "<p>Dear #Title# #FirstName# #Surname#,</p>" 			
						+ "<p>The merSETA hereby advises that application for a learnership against the following qualification: #QualificationCode# #QualificationTitle# has been reviewed and has been approved by the Department of Higher Education and Training (DHET).</p>" 
						+ "<p>The Learnership details are:</p>"
						+ "<p>Learnership Title: #SaqaTitleDescription# #NqfLevel#</p>"
						+ "<p>Registration No: #RegistrationNumber#</p>"
						+ "<p>Please do not hesitate to contact the merSETA Curriculum and Learning Programmes Unit or the Regional office for further assistance. </p>"
						+ "<p>Yours sincerely,</p>" 
						+ "<p>Curriculum and Learning Programme Development Unit</p>" 
						+ "<br/>";
		sendBulkMailToSelected(list, welcome, learnershipDevelopmentRegistration);
	}
	
	public void sendApprovalEmailToDHET(Users u, LearnershipDevelopmentRegistration learnershipDevelopmentRegistration) {
		String title ="";
		if(u.getTitle()!=null) {
			title = u.getTitle().getDescription();
		}
		String welcome = "<p>Dear #Title# #FirstName# #Surname#,</p>" 			
						+ "<p>The merSETA hereby advises that application for a learnership against the following qualification: #QualificationCode# #QualificationTitle# has been reviewed and an application will now be submitted to the Department of Higher Education and Training (DHET).</p>" 
						+ "<p>Please note that the merSETA may revert and request additional information.</p>"
						+ "<p>Kindly be advised that the application process with the DHET may take longer than 30 days. The merSETA will communicate the outcome once the DHET has reverted to the merSETA.</p>"
						+ "<p>Yours sincerely,</p>" 
						+ "<p>Curriculum and Learning Programme Development Unit</p>" 
						+ "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#QualificationCode#", learnershipDevelopmentRegistration.getQualification().getCodeString());
		welcome = welcome.replaceAll("#QualificationTitle#", learnershipDevelopmentRegistration.getQualification().getDescription());
		
		GenericUtility.sendMail(u.getEmail(), "NOTIFICATION OF LEARNERSHIP APPLICATION TO DEPARTMENT OF HIGHER EDUCATION AND TRAINING", welcome, null);
	}
	
	public void sendRejectEmail(LearnershipDevelopmentRegistration entity,Users u, ArrayList<RejectReasons> selectedRejectReason) {
		String qualificationIDandDescription ="";
		if(entity !=null && entity.getQualification()!=null) {
			qualificationIDandDescription = "("+entity.getQualification().getCode()+")"+entity.getQualification().getDescription();
		}
		String title ="";
		String rejectReason = convertToHTML(selectedRejectReason);
		if(u.getTitle()!=null) {
			title = u.getTitle().getDescription();
		}
		String welcome = "<p>Dear #Title# #FirstName# #Surname#,</p>"
						+ "<p>The merSETA has reviewed the application for a learnership against the following qualification: #qualificationIDandDescription#.</p>" 
						+ "<p>We regret to inform you that your Learnership Application has been rejected due to the following:</p>" 
						+ "<p>"+rejectReason+"</p>"
						+ "<p>We regret to inform you that your Learnership Application has been rejected due to the following:</p>" 
						+ "<p>Yours sincerely,</p>" 
						+ "<p>Curriculum and Learning Programme Development Unit</p>" 
						+ "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#qualificationIDandDescription#", qualificationIDandDescription);
		
		GenericUtility.sendMail(u.getEmail(), "LEARNERSHIP APPLICATION OUTCOME: REJECTED", welcome, null);
	}
	
	public void sendRejectEmailToList(List<Users> list, LearnershipDevelopmentRegistration learnershipDevelopmentRegistration) {		
		
		String welcome = "<p>Dear #FirstName# #Surname#,</p>" 
				+ "<p>The merSETA hereby advises that learnership application has not been duly approved and registered accordingly by the Department of Higher Education and Training.</p>"
				+ "<p>Please do not hesitate to contact the merSETA Curriculum and Learning Programmes Unit or the Regional office for further assistance.</p>" 	
				+ "<p>Yours sincerely,</p>" 
				+ "<p>Manager: Curriculum and Learning Programme Development</p>" 
				+ "<br/>";

		sendRejectBulkMailSelected(list, welcome, learnershipDevelopmentRegistration);
	}
	
	public void sendFanalRejection(LearnershipDevelopmentRegistration entity, ArrayList<RejectReasons> selectedRejectReason) {
		//String title ="";
		//String rejectReason = convertToHTML(selectedRejectReason);
		/*if(entity.getUsers().getTitle()!=null) {
			title = entity.getUsers().getTitle().getDescription();
		}*/
		String welcome = "<p>Dear #NAME# #SURNAME#,</p>" 
						+ "<p>The merSETA hereby advises that learnership application has not been duly approved and registered accordingly by the Department of Higher Education and Training.</p>"
						+ "<p>Please do not hesitate to contact the merSETA Curriculum and Learning Programmes Unit or the Regional office for further assistance.</p>" 	
						+ "<p>Yours sincerely,</p>" 
						+ "<p>Manager: Curriculum and Learning Programme Development</p>" 
						+ "<br/>";
		//welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#NAME#", entity.getUsers().getFirstName());
		welcome = welcome.replaceAll("#SURNAME#", entity.getUsers().getLastName());
		welcome = welcome.replaceAll("#QualificationID#", entity.getQualification().getSaqaQualification());
		GenericUtility.sendMail(entity.getUsers().getEmail(), "LEARNERSHIP APPLICATION OUTCOME: REJECTED", welcome, null);
	}
	/**
	 * Creates and submits the new LearnershipDevelopmentRegistration
	 * 
	 * @param sessionUser
	 * @param entity
	 * @throws Exception
	 */
	public void submitNewExternalLearnership(Users sessionUser, LearnershipDevelopmentRegistration entity,Company company,Address postalAddress,Address residentialAddress, boolean  sendMail) throws Exception {
		List<IDataEntity> iDataEntityList = new ArrayList<IDataEntity>();
		AddressService addressService=new AddressService();
		addressService.create(residentialAddress);
		addressService.create(postalAddress);
		entity.setCompany(company);
		
		company.setPostalAddress(postalAddress);
		company.setResidentialAddress(residentialAddress);
		// adds new entry
		iDataEntityList.add(entity);
		iDataEntityList.add(company);
		// batch create
		dao.createBatch(iDataEntityList);

		// Creates documents assigned to user
				List<IDataEntity> docsDataEntities = new ArrayList<IDataEntity>();
				if (entity.getDocs() != null) {
					for (Doc doc : entity.getDocs()) {
						doc.setCompany(null);
						doc.setVersionNo(1);
						//doc.setForUsers(formUser);
						doc.setUsers(sessionUser);
						doc.setTargetKey(entity.getId());
						doc.setTargetClass(LearnershipDevelopmentRegistration.class.getName());
						docsDataEntities.add(doc);
						docsDataEntities.add(new DocByte(doc.getData(), doc));
						docsDataEntities.add(new DocumentTracker(doc, sessionUser, new java.util.Date(), DocumentTrackerEnum.Upload));
					}
				}
				if (docsDataEntities.size() > 0) dao.createBatch(docsDataEntities);
				sendAcknoledgementEmail(sessionUser, entity);
		// creates the new task
		TasksService.instance().findFirstInProcessAndCreateTask("", sessionUser, entity.getId(), LearnershipDevelopmentRegistration.class.getName(), true, ConfigDocProcessEnum.LEARNERSHIP_DEVELOPMENT_REGISTRATION, null, null);
	}

	public void completeToFirst(LearnershipDevelopmentRegistration entity, Users formUser, Tasks task) throws Exception {
		update(entity);
		String desc = "Learnership Registration has been submitted. Please Review.";
		TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, task.getTargetKey(), task.getTargetClass(), true, ConfigDocProcessEnum.LEARNERSHIP_DEVELOPMENT_REGISTRATION, null, null);
		TasksService.instance().completeTask(task);
	}

	/**
	 * Completes action on viewing LearnershipDevelopmentRegistration
	 * 
	 * @param entity
	 * @param user
	 * @param tasks
	 * @throws Exception
	 */
	public void completeLearnership(LearnershipDevelopmentRegistration entity, Users user, Tasks tasks) throws Exception {
		entity.setApprovalEnum(ApprovalEnum.PendingApproval);
		update(entity);
		uploadDocuments(entity, tasks, user);
		TasksService.instance().findNextInProcessAndCreateTask("", user, entity.getId(), LearnershipDevelopmentRegistration.class.getName(), true, tasks, null, null);
	}
	
	public void completeLearnershipTask(LearnershipDevelopmentRegistration entity, Users user, Tasks tasks) throws Exception {
		//finalApproveLearnership(entity, user, tasks);
		update(entity);
		uploadDocuments(entity, tasks, user);
		TasksService.instance().findNextInProcessAndCreateTask("", user, entity.getId(), LearnershipDevelopmentRegistration.class.getName(), true, tasks, null, null);
		//Send Task to list
		
		List<Users>list= getUsersToSendEmails();
		list.add(entity.getUsers());
		//list.add(user);
		
		LearnershipService lSevice = new LearnershipService();
		lSevice.createLearnership(entity);
		
		sendApprovalEmailToList(list, entity);
	}

	/**
	 * Approves the LearnershipDevelopmentRegistration and sends it to the next task
	 * in process
	 * 
	 * @param entity
	 * @param user
	 * @param tasks
	 * @throws Exception
	 */
	public void approveLearnership(LearnershipDevelopmentRegistration entity, Users user, Tasks tasks) throws Exception {
		entity.setApprovalEnum(ApprovalEnum.WaitingForManager);
		entity.setApprovalDate(getSynchronizedDate());
		update(entity);		
		TasksService.instance().findNextInProcessAndCreateTask("", user, entity.getId(), LearnershipDevelopmentRegistration.class.getName(), true, tasks, null, null);
		sendApprovalEmailToDHET(entity.getUsers(), entity);
	}

	/**
	 * Approves the LearnershipDevelopmentRegistration and sets it to AwaitingDHET
	 * Also closes the task
	 * 
	 * @param entity
	 * @param user
	 * @param tasks
	 * @throws Exception
	 */
	public void approveLearnershipForDhet(LearnershipDevelopmentRegistration entity, Users user, Tasks tasks) throws Exception {
		entity.setApprovalEnum(ApprovalEnum.AwaitingDHET);
		entity.setApprovalDate(getSynchronizedDate());
		update(entity);
		TasksService.instance().completeTask(tasks);
		TasksService.instance().findNextInProcessAndCreateTask("", user, entity.getId(), LearnershipDevelopmentRegistration.class.getName(), true, tasks, null, null);
	}

	/**
	 * Final Approves the LearnershipDevelopmentRegistration and closes the task
	 * 
	 * @param entity
	 * @param user
	 * @param tasks
	 * @throws Exception
	 */
	public void finalApproveLearnership(LearnershipDevelopmentRegistration entity, Users user, Tasks tasks) throws Exception {
		String learnerNumber = "";
		learnerNumber = generateLearnerNumber();
		entity.setLearnerNumber(learnerNumber);
		entity.setApprovalEnum(ApprovalEnum.Approved);
		entity.setApprovalDate(getSynchronizedDate());
		update(entity);
		uploadDocuments(entity, tasks, user);
		TasksService.instance().findNextInProcessAndCreateTask("", user, entity.getId(), LearnershipDevelopmentRegistration.class.getName(), true, tasks, null, null);
		//LearnershipService lSevice = new LearnershipService();
		//lSevice.createLearnership(entity);
		//sendApprovalEmailToList(tasks.getCreateUser(), entity);
		sendApprovalEmail(tasks.getCreateUser(), entity);
	}
	
	private List<Users> getUsersToSendEmails() throws Exception{
		List<Users>l = new ArrayList<>();
		HostingCompanyEmployeesService hostingCompanyEmployeesService=new HostingCompanyEmployeesService();
		
		List<Users> seniorManagerQAP=hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.Senior_Manager_Quality_Assurance_and_Partnerships_ID);
		List<Users> seniorManagerA=hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.Senior_Manager_Administration_ID);
		List<Users> managerQA=hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.Manager_Quality_Assurance_ID);
		List<Users> ManagerLearningAdministration=hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.Manager_Learning_Administration_ID);		
		List<Users> coordinatorCLPD=hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.Coordinator_Curriculum_and_Learning_Programmes_Development_ID);
		List<Users> chamberManager=hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.Chambers_Manager_ID);
		
		if(seniorManagerQAP.size()>0) {l.addAll(seniorManagerQAP);}
		
		if(seniorManagerA.size()>0) {l.addAll(seniorManagerA);}
		
		if(managerQA.size()>0) {l.addAll(managerQA);}
		
		if(ManagerLearningAdministration.size()>0) {l.addAll(ManagerLearningAdministration);}

		if(coordinatorCLPD.size()>0) {l.addAll(coordinatorCLPD);}
		
		if(chamberManager.size()>0) {l.addAll(chamberManager);}
		
		return l;
	}

	private String generateLearnerNumber() throws Exception {
		String number = "";
		String seqNumber = "1000000";
		int year = Calendar.getInstance().get(Calendar.YEAR);
		
		/**New Learner number/agreement syntax for all learners 17/1000000/18 (17 = merSETA; 1000000 = learner number to be serialized 18 = year of registration)*/

		List<LearnershipDevelopmentRegistration> list = dao.findListLastApproved();
		
		if(list != null && list.size() > 0)
		{
			LearnershipDevelopmentRegistration learnershipDevelopmentRegistration= list.get(0);
			
			if(learnershipDevelopmentRegistration.getLearnerNumber() != null)
			{
				String[] element = learnershipDevelopmentRegistration.getLearnerNumber() .split("/");
				long numberFromelement = Long.parseLong(element[1]) + 1;
				seqNumber = ""+numberFromelement;
			}			
			
		}
		
		number = "17/"+seqNumber+"/"+year;
		return number;
	}

	/**
	 * Rejects the LearnershipDevelopmentRegistration and sends it to the previous
	 * process
	 * 
	 * @param entity
	 * @param user
	 * @param tasks
	 * @throws Exception
	 */
	public void rejectLearnership(LearnershipDevelopmentRegistration entity, Users user, Tasks tasks,ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		uploadDocuments(entity, tasks, user);
		entity.setApprovalEnum(ApprovalEnum.Rejected);
		entity.setApprovalDate(getSynchronizedDate());
		if(entity.getRegistrationNumber() != null && entity.getRegistrationNumber().length() > 0) {
			entity.setRegistrationNumber(null);
		}		
		update(entity);
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(entity.getId(), LearnershipDevelopmentRegistration.class.getName(), selectedRejectReason, user, ConfigDocProcessEnum.LEARNERSHIP_DEVELOPMENT_REGISTRATION);
		for(RejectReasonMultipleSelection rrm:rrmList)
		{
			if(rrm !=null)
			{
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
			
		}
		
		if (tasks.getFirstInProcess()) {
			List<Users> signoffs = new ArrayList<>();
			signoffs.add(entity.getUsers());
			//TasksService.instance().createTaskUser(signoffs, tasks.getTargetClass(), tasks.getTargetKey(), "Learnership registration was rejected please login and make the relevant changes.", user, true, true, tasks, ConfigDocProcessEnum.LEARNERSHIP_DEVELOPMENT_REGISTRATION, false);
			TasksService.instance().completeTask(tasks);			
		} else {
			TasksService.instance().findPreviousInProcessAndCreateTask("", user, entity.getId(), LearnershipDevelopmentRegistration.class.getName(), true, tasks, null, null);
		}
		sendRejectEmail(entity, tasks.getCreateUser(), selectedRejectReason);
	}
	
	/**
	 * Rejects the LearnershipDevelopmentRegistration and sends it to the previous
	 * process
	 * 
	 * @param entity
	 * @param user
	 * @param tasks
	 * @throws Exception
	 */
	public void rejectLearnershipTask(LearnershipDevelopmentRegistration entity, Users user, Tasks tasks,ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		entity.setApprovalEnum(ApprovalEnum.Rejected);
		update(entity);
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(entity.getId(), LearnershipDevelopmentRegistration.class.getName(), selectedRejectReason, user, ConfigDocProcessEnum.LEARNERSHIP_DEVELOPMENT_REGISTRATION);
		for(RejectReasonMultipleSelection rrm:rrmList)
		{
			if(rrm !=null)
			{
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
			
		}
		
		if (tasks.getFirstInProcess()) {
			List<Users> signoffs = new ArrayList<>();
			signoffs.add(entity.getUsers());
			TasksService.instance().createTaskUser(signoffs, tasks.getTargetClass(), tasks.getTargetKey(), "Learnership registration was rejected please login and make the relevant changes.", user, true, true, tasks, ConfigDocProcessEnum.LEARNERSHIP_DEVELOPMENT_REGISTRATION, false);
			TasksService.instance().completeTask(tasks);
			
		} else {
			TasksService.instance().findPreviousInProcessAndCreateTask("", user, entity.getId(), LearnershipDevelopmentRegistration.class.getName(), true, tasks, null, null);
		}		
		
	}

	/**
	 * Rejects the LearnershipDevelopmentRegistration and closes the task
	 * 
	 * @param entity
	 * @param user
	 * @param tasks
	 * @throws Exception
	 */
	public void finalRejectLearnership(LearnershipDevelopmentRegistration entity, Users user, Tasks tasks, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		entity.setApprovalEnum(ApprovalEnum.Rejected);
		entity.setApprovalDate(getSynchronizedDate());
		update(entity);
		
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(entity.getId(), LearnershipDevelopmentRegistration.class.getName(), selectedRejectReason, user, ConfigDocProcessEnum.LEARNERSHIP_DEVELOPMENT_REGISTRATION);
		for(RejectReasonMultipleSelection rrm:rrmList)
		{
			if(rrm !=null)
			{
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
			
		}
		
		TasksService.instance().completeTask(tasks);
		sendFanalRejection(entity, selectedRejectReason);
	}
	
	public void createDhetRejectTask(LearnershipDevelopmentRegistration entity, Users user, Tasks tasks, ConfigDocProcessEnum learnershipDevelopmentRegistration, boolean sendMail, Doc doc)throws Exception {
		ArrayList<RejectReasons> selectedRejectReason = new ArrayList<>();
		RejectReasons rejectReasons = new RejectReasons();
		rejectReasons.setDescription(doc.getNote());
		selectedRejectReason.add(rejectReasons);
		
		entity.setApprovalEnum(ApprovalEnum.Rejected);
		entity.setApprovalDate(getSynchronizedDate());
		update(entity);
		if (doc.getData() != null) {
			if(doc.getId() == null) {
				doc.setTargetKey(entity.getId());
				doc.setTargetClass(LearnershipDevelopmentRegistration.class.getName());	
				doc.setApprovalStatus(ApprovalEnum.Rejected);
				DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), user);
			}
		}else {
			throw new Exception("Please ensure all documents are uploaded");
		}
		TasksService.instance().completeTask(tasks);
		
		List<Users>list= getUsersToSendEmails();
		list.add(entity.getUsers());
		//list.add(user);
		//TasksService.instance().findPreviousInProcessAndCreateTask("", user, entity.getId(), LearnershipDevelopmentRegistration.class.getName(), true, tasks, null, null);
		//sendFanalRejection(entity, selectedRejectReason);
		sendRejectEmailToList(list, entity);
	}
	
	public String convertToHTML(List<RejectReasons> rejectReasons){		
		String sb ="<ul>"; 
		for (RejectReasons r: rejectReasons){
			sb +="<li>"+r.getDescription() +"</li>";
		}
		sb +="</ul>"; 
		return sb;
	}

}
