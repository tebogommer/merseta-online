package haj.com.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import haj.com.constants.HAJConstants;
import haj.com.dao.AdministrationOfAQPDAO;
import haj.com.entity.AdministrationOfAQP;
import haj.com.entity.AdministrationOfAQPLearners;
import haj.com.entity.CompanyLearners;
import haj.com.entity.CompanyLearnersAchievementStatusEISA;
import haj.com.entity.CompanyLearnersQualificationAchievementStatus;
import haj.com.entity.AdministrationOfAQP;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.ProcessRoles;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class AdministrationOfAQPService extends AbstractService {
	/** The dao. */
	private AdministrationOfAQPDAO dao = new AdministrationOfAQPDAO();

	/**
	 * Get all AdministrationOfAQP
	 * 
	 * @author TechFinium
	 * @see AdministrationOfAQP
	 * @return a list of {@link haj.com.entity.AdministrationOfAQP}
	 * @throws Exception
	 *             the exception
	 */
	public List<AdministrationOfAQP> allAdministrationOfAQP() throws Exception {
		return dao.allAdministrationOfAQP();
	}

	/**
	 * Create or update AdministrationOfAQP.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see AdministrationOfAQP
	 */
	public void create(AdministrationOfAQP entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	public void downloadManagerJasper(AdministrationOfAQP administrationofaqp) {
		Map<String, Map<String, Object>> zipParams = new HashMap<String, Map<String, Object>>();
		String fileName = "EISA.zip";
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMM yyyy");
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
		// INSERT JASPER HERE
		// check haj.com.ui.TestUI.downloadCertificates() for an example
		// add MM-319
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");

		Qualification qualification = administrationofaqp.getQualification();
		String dateOfEISA = sdfDate.format(administrationofaqp.getEisaDate());
		String timeOfEISA = sdfTime.format(administrationofaqp.getEisaDate());
		String duration = "";// To be fixed
		String totalMarks = "";// To be fixed
		String passMark = "";// To be fixed
		String dateRegistered = sdfDate.format(new Date());// To be confirm

		// Check list parms
		boolean receivedRequiredTraining = false;
		boolean inPossessionFinalEISA = false;
		boolean exemplarEISAHasBeenPublished = false;
		boolean entryRequirementsMet = false;
		if (administrationofaqp.getRecievedRequiredTraining().getFriendlyName().equalsIgnoreCase("yes")) {
			receivedRequiredTraining = true;
		}
		if (administrationofaqp.getInPossessionFinalEISA().getFriendlyName().equalsIgnoreCase("yes")) {
			inPossessionFinalEISA = true;
		}
		if (administrationofaqp.getExemplarEISAHasBeenPublished().getFriendlyName().equalsIgnoreCase("yes")) {
			exemplarEISAHasBeenPublished = true;
		}
		if (administrationofaqp.getEntryRequirementsMet().getFriendlyName().equalsIgnoreCase("yes")) {
			entryRequirementsMet = true;
		}
		params.put("receivedRequiredTraining", receivedRequiredTraining);
		params.put("inPossessionFinalEISA", inPossessionFinalEISA);
		params.put("exemplarEISAHasBeenPublished", exemplarEISAHasBeenPublished);
		params.put("entryRequirementsMet", entryRequirementsMet);
		// End Check list parms

		params.put("qualification", qualification);
		params.put("dateOfEISA", dateOfEISA);
		params.put("timeOfEISA", timeOfEISA);
		params.put("duration", duration);
		params.put("totalMarks", totalMarks);
		params.put("passMark", passMark);
		params.put("dateRegistered", dateRegistered);

		zipParams.put("NotificationOfEISA.jasper", params);

		// add MM-321
		params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");

		String registrationStartDate = sdfDate.format(administrationofaqp.getEisaDate());
		String registrationEndDate = sdfDate.format(administrationofaqp.getEndTime());
		String lastDateForEnrolment = "";// To be fixed
		String lastDateForAchievement = "";// To be fixed

		params.put("qualification", qualification);
		params.put("registrationStartDate", registrationStartDate);
		params.put("registrationEndDate", registrationEndDate);
		params.put("lastDateForEnrolment", lastDateForEnrolment);
		params.put("lastDateForAchievement", lastDateForAchievement);

		zipParams.put("ConfidentialityAgreementAQP.jasper", params);

		JasperService.instance().multipleJasperToZip(zipParams, fileName);
	}

	public void prepareNewRegistration(ConfigDocProcessEnum configDocProcess, AdministrationOfAQP entity) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessNotUploaded(entity.getClass().getName(), entity.getId(), configDocProcess);
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

	public void prepareNewRegistration(ConfigDocProcessEnum configDocProcess, AdministrationOfAQP entity, ProcessRoles processRoles) throws Exception {
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

	public void requestAdministrationOfAQP(AdministrationOfAQP entity, Users createUser) throws Exception {
		List<IDataEntity> createBatch = new ArrayList<>();
		List<IDataEntity> updateBatch = new ArrayList<>();
		String error = "";
		createBatch.add(entity);
		if (entity.getDocs() != null) {
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) doc.setData(docByte.getData());
				}
				if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
					error += "Upload " + doc.getConfigDoc().getName() + " for Administration Of AQP";
				} else if (doc.getId() != null) {
					updateBatch.add(doc);
				} else {
					createBatch.add(doc);
				}
			}
		}
		dao.createAndUpdateBatch(createBatch, updateBatch);
		if (error.length() > 0) throw new ValidationException(error);
		TasksService.instance().findFirstInProcessAndCreateTask(createUser, entity.getId(), AdministrationOfAQP.class.getName(), ConfigDocProcessEnum.ApplicationAdministrationOfAQP, false);
	}

	public void completeWorkflow(AdministrationOfAQP entity, Users user, Tasks tasks) throws Exception {
		String error = "";

		long taskCount = TasksService.instance().findTasksByTypeAndKeyOpen(tasks.getWorkflowProcess(), tasks.getTargetClass(), tasks.getTargetKey());

		if (entity.getDocs() != null) {
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) doc.setData(docByte.getData());
				}
				if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
					error += "Upload " + doc.getConfigDoc().getName() + " for Administration Of AQP";
				}
			}
		}

		if (error.length() > 0) throw new ValidationException(error);
		create(entity);
		if (taskCount == 1) TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, (tasks.getFirstInProcess() != null && tasks.getFirstInProcess()));
	}

	public void approveWorkflow(AdministrationOfAQP entity, Users user, Tasks tasks) throws Exception {
		entity.setStatus(ApprovalEnum.WaitingForManager);
		dao.update(entity);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}

	public void rejectWorkflow(AdministrationOfAQP entity, Users user, Tasks tasks) throws Exception {
		TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, null);
	}

	public void finalApproveWorkflow(AdministrationOfAQP entity, Users user, Tasks tasks) throws Exception {
		entity.setStatus(ApprovalEnum.Approved);
		entity.setApprovalDate(getSynchronizedDate());
		dao.update(entity);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		TasksService.instance().findFirstInProcessAndCreateTask(user, entity.getId(), AdministrationOfAQP.class.getName(), ConfigDocProcessEnum.AdministrationOfAQP, true);
	}

	public void finalRejectWorkflow(AdministrationOfAQP entity, Users user, Tasks tasks) throws Exception {
		entity.setStatus(ApprovalEnum.Rejected);
		entity.setApprovalDate(getSynchronizedDate());
		dao.update(entity);
		TasksService.instance().completeTask(tasks);
	}

	/**
	 * Update AdministrationOfAQP.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see AdministrationOfAQP
	 */
	public void update(AdministrationOfAQP entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete AdministrationOfAQP.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see AdministrationOfAQP
	 */
	public void delete(AdministrationOfAQP entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.AdministrationOfAQP}
	 * @throws Exception
	 *             the exception
	 * @see AdministrationOfAQP
	 */
	public AdministrationOfAQP findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find AdministrationOfAQP by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.AdministrationOfAQP}
	 * @throws Exception
	 *             the exception
	 * @see AdministrationOfAQP
	 */
	public List<AdministrationOfAQP> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load AdministrationOfAQP
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.AdministrationOfAQP}
	 * @throws Exception
	 *             the exception
	 */
	public List<AdministrationOfAQP> allAdministrationOfAQP(int first, int pageSize) throws Exception {
		return dao.allAdministrationOfAQP(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of AdministrationOfAQP for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the AdministrationOfAQP
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(AdministrationOfAQP.class);
	}

	/**
	 * Lazy load AdministrationOfAQP with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            AdministrationOfAQP class
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
	 * @return a list of {@link haj.com.entity.AdministrationOfAQP} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<AdministrationOfAQP> allAdministrationOfAQP(Class<AdministrationOfAQP> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<AdministrationOfAQP>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of AdministrationOfAQP for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            AdministrationOfAQP class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the AdministrationOfAQP entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<AdministrationOfAQP> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public List<AdministrationOfAQP> allAdministrationOfAQP(Users u, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return dao.allAdministrationOfAQP(u, first, pageSize, sortField, sortOrder, filters);
	}

	public int count(Users u, Map<String, Object> filters) throws Exception {
		return dao.count(u, filters);
	}

	public void saveNewRegistration(AdministrationOfAQPLearners entity, Users user) throws Exception {
		// List<IDataEntity> dataEntities = new ArrayList<>();
		String error = "";
		if (entity.getDocs() != null) {
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) doc.setData(docByte.getData());
				}
				if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
					error += "Upload " + doc.getConfigDoc().getName() + " ";
				}
			}
		}
		if (error.length() > 0) throw new Exception(error);
		dao.create(entity);
		if (entity.getDocs() != null) {
			for (Doc doc : entity.getDocs()) {
				if (doc.getData() != null) {
					doc.setTargetKey(entity.getId());
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), user);
				}
			}
		}
		// dataEntities

	}

	public void prepareNewRegistration(AdministrationOfAQPLearners entity) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), ConfigDocProcessEnum.LearnerRegistrationOfAQP));
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessNotUploaded(entity.getClass().getName(), entity.getId(), ConfigDocProcessEnum.LearnerRegistrationOfAQP);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessA(ConfigDocProcessEnum.LearnerRegistrationOfAQP);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}

	public AdministrationOfAQPLearners findAdministrationOfAQPLearners(String rsaID, String passportNumber) throws Exception {
		return dao.findAdministrationOfAQPLearners(rsaID, passportNumber);
	}

	@SuppressWarnings("unchecked")
	public void processFile(List<CompanyLearnersQualificationAchievementStatus> companyLearnersQualificationAchievementStatus) throws Exception {
		for (CompanyLearnersQualificationAchievementStatus achievementStatus : companyLearnersQualificationAchievementStatus) {
			AdministrationOfAQPLearners aoal = findAdministrationOfAQPLearners(achievementStatus.getNationalID(), achievementStatus.getLearnerAlternateId());
			achievementStatus.setAdministrationOfAQPLearners(aoal);
		}
		dao.createBatch((List<IDataEntity>) (List<?>) companyLearnersQualificationAchievementStatus);
	}

	@SuppressWarnings("unchecked")
	public void processFileEISA(List<CompanyLearnersAchievementStatusEISA> achievementStatusEISAs) throws Exception {
		for (CompanyLearnersAchievementStatusEISA achievementStatus : achievementStatusEISAs) {
			AdministrationOfAQPLearners aoal = findAdministrationOfAQPLearners(achievementStatus.getNationalID(), achievementStatus.getLearnerAlternateId());
			achievementStatus.setAdministrationOfAQPLearners(aoal);
		}
		dao.createBatch((List<IDataEntity>) (List<?>) achievementStatusEISAs);
	}

}
