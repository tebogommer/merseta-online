package haj.com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.NonSetaCompanyDAO;
import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.CompanyQualifications;
import haj.com.entity.CompanyUnitStandard;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.DocumentTracker;
import haj.com.entity.HostingCompany;
import haj.com.entity.NonSetaCompany;
import haj.com.entity.NonSetaCompanyUsers;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.Tasks;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingProviderDocParent;
import haj.com.entity.TrainingProviderSkillsProgramme;
import haj.com.entity.TrainingProviderSkillsSet;
import haj.com.entity.Users;
import haj.com.entity.UsersDisability;
import haj.com.entity.UsersLanguage;
import haj.com.entity.WorkplaceMonitoring;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.CompanyTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.DocumentTrackerEnum;
import haj.com.entity.enums.InterventionTypeEnum;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.SubmissionEnum;
import haj.com.entity.enums.TrancheEnum;
import haj.com.entity.enums.UsersStatusEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.Roles;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.RegionService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RolesService;
import haj.com.utils.GenericUtility;

import static haj.com.constants.HAJConstants.SKILLS_PROGRAM_LIST;
import static haj.com.constants.HAJConstants.SKILLS_SET_LIST;

public class NonSetaCompanyService extends AbstractService {
	/** The dao. */
	private NonSetaCompanyDAO dao = new NonSetaCompanyDAO();

	/** The register service. */
	private RegisterService registerService = new RegisterService();
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
	private RolesService rolesService = new RolesService();
	/** The Users Service */
	UsersService usersService = new UsersService();

	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
	
	private RegionService regionService;

	/**
	 * Get all NonSetaCompany
	 * 
	 * @author TechFinium
	 * @see NonSetaCompany
	 * @return a list of {@link haj.com.entity.NonSetaCompany}
	 * @throws Exception
	 *             the exception
	 */
	public List<NonSetaCompany> allNonSetaCompany() throws Exception {
		return dao.allNonSetaCompany();
	}

	/**
	 * Create or update NonSetaCompany.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see NonSetaCompany
	 */
	public void create(NonSetaCompany entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update NonSetaCompany.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see NonSetaCompany
	 */
	public void update(NonSetaCompany entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete NonSetaCompany.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see NonSetaCompany
	 */
	public void delete(NonSetaCompany entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.NonSetaCompany}
	 * @throws Exception
	 *             the exception
	 * @see NonSetaCompany
	 */
	public NonSetaCompany findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	public NonSetaCompany findRegistrationNumber(String regNumber) {
		return dao.findRegistrationNumber(regNumber);
	}

	/**
	 * Find NonSetaCompany by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.NonSetaCompany}
	 * @throws Exception
	 *             the exception
	 * @see NonSetaCompany
	 */
	public List<NonSetaCompany> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load NonSetaCompany
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.NonSetaCompany}
	 * @throws Exception
	 *             the exception
	 */
	public List<NonSetaCompany> allNonSetaCompany(int first, int pageSize) throws Exception {
		return dao.allNonSetaCompany(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of NonSetaCompany for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the NonSetaCompany
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(NonSetaCompany.class);
	}

	/**
	 * Lazy load NonSetaCompany with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            NonSetaCompany class
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
	 * @return a list of {@link haj.com.entity.NonSetaCompany} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<NonSetaCompany> allNonSetaCompany(Class<NonSetaCompany> class1, int first, int pageSize,
			String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<NonSetaCompany>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of NonSetaCompany for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            NonSetaCompany class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the NonSetaCompany entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<NonSetaCompany> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public void createNonSetaTPSendTask(List<CompanyUnitStandard> companyUnitStandardsList,
			List<CompanyQualifications> companyQualificationsList, NonSetaCompany nonSetaCompany, Users formUser,
			boolean createTask, ConfigDocProcessEnum configDocProcessEnum, HostingCompany hostingCompany,
			TrainingProviderApplication trainingProviderApplication, Address tpResidentialAddress,
			Address tpPostalAddress, ArrayList<Users> contactPersonList,
			List<TrainingProviderSkillsProgramme> tpSkillsProgramme, List<TrainingProviderSkillsSet> tpSkillsSetList,
			List<TrainingProviderDocParent> docParentList) throws Exception {

		preUserRegisterChecks(formUser);
		if (formUser.getId() == null) {
			formUser = registerService.register(formUser, true);
		} else {
			usersService.update(formUser);
		}
		AddressService.instance().create(tpResidentialAddress);
		AddressService.instance().create(tpPostalAddress);
		nonSetaCompany.setResidentialAddress(tpResidentialAddress);
		nonSetaCompany.setPostalAddress(tpPostalAddress);
		nonSetaCompany.setCompanyType(CompanyTypeEnum.TP);
		// Creating Non Seta Company
		create(nonSetaCompany);
		List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
		if (formUser.getDocs() != null) {
			for (Doc doc : formUser.getDocs()) {
				doc.setCompany(null);
				doc.setVersionNo(1);
				doc.setForUsers(formUser);
				doc.setUsers(formUser);
				doc.setTargetKey(trainingProviderApplication.getId());
				doc.setTargetClass(TrainingProviderApplication.class.getName());
				dataEntities.add(doc);
				dataEntities.add(new DocByte(doc.getData(), doc));
				dataEntities.add(new DocumentTracker(doc, formUser, new java.util.Date(), DocumentTrackerEnum.Upload));
			}
		}
		for (TrainingProviderDocParent docParent : docParentList) {
			dataEntities.add(docParent);
			Doc doc = docParent.getDoc();
			doc.setVersionNo(1);
			doc.setUsers(formUser);
			doc.setTargetKey(docParent.getId());
			doc.setTargetClass(TrainingProviderDocParent.class.getName());
			dataEntities.add(doc);
			dataEntities.add(new DocByte(doc.getData(), doc));
			dataEntities.add(new DocumentTracker(doc, formUser, new java.util.Date(), DocumentTrackerEnum.Upload));
		}
		// Creating Training Provider Application
		trainingProviderApplication.setUsers(formUser);
		trainingProviderApplication.setApprovalStatus(ApprovalEnum.PendingApproval);
		trainingProviderApplication.setNonSetaCompany(nonSetaCompany);
		trainingProviderApplicationService.create(trainingProviderApplication);

		if (dataEntities.size() > 0) {
			dao.createBatch(dataEntities);
		}
		// Creating TP Contacts
		dataEntities = new ArrayList<IDataEntity>();
		for (Users u : contactPersonList) {
			if (u != formUser) {
				NonSetaCompanyUsers companyUsers = new NonSetaCompanyUsers(u, nonSetaCompany);
				companyUsers.setCompanyUserType(ConfigDocProcessEnum.NON_SETA_PROVIDERS);
				if (u.getDesignation() != null) {
					companyUsers.setDesignation(u.getDesignation());
				}
				if (u.getAssessorModType() != null) {
					companyUsers.setAssessorModType(u.getAssessorModType());

				}

				if (u.getId() == null) {
					companyUsers.setExistingUser(false);
					registerService.register(u, true);
				} else {
					companyUsers.setExistingUser(true);
					usersService.update(u);
				}

				dataEntities.add(companyUsers);
			}

		}

		/* Creating qualifications */
		/*
		 * for (Qualification qualification : qualificationsList) {
		 * CompanyQualifications companyQualifications = new
		 * CompanyQualifications(nonSetaCompany,
		 * qualification,TrainingProviderApplication.class.getName(),
		 * trainingProviderApplication.getId());
		 * dataEntities.add(companyQualifications); }
		 */

		for (CompanyQualifications cp : companyQualificationsList) {
			cp.setTargetClass(TrainingProviderApplication.class.getName());
			cp.setTargetKey(trainingProviderApplication.getId());
			cp.setNonSetaCompany(nonSetaCompany);
			dataEntities.add(cp);
		}

		/* Creating unit standards */
		/*
		 * for (UnitStandards unitStandard : unitStandardsList) {
		 * CompanyUnitStandard companyUnitStandard = new
		 * CompanyUnitStandard(nonSetaCompany,
		 * unitStandard,TrainingProviderApplication.class.getName(),
		 * trainingProviderApplication.getId());
		 * dataEntities.add(companyUnitStandard); }
		 */

		for (CompanyUnitStandard us : companyUnitStandardsList) {
			us.setTargetClass(TrainingProviderApplication.class.getName());
			us.setTargetKey(trainingProviderApplication.getId());
			us.setNonSetaCompany(nonSetaCompany);
			dataEntities.add(us);
		}

		/* Creating training Provider Skills Program **/
		/*
		 * for(SkillsProgram sp:skillsProgramList){
		 * TrainingProviderSkillsProgramme tpSp=new
		 * TrainingProviderSkillsProgramme(sp,trainingProviderApplication);
		 * dataEntities.add(tpSp); }
		 */

		// List<TrainingProviderSkillsProgramme> tpSkillsProgramme,
		for (TrainingProviderSkillsProgramme tpSkillsProg : tpSkillsProgramme) {
			tpSkillsProg.setTrainingProviderApplication(trainingProviderApplication);
			dataEntities.add(tpSkillsProg);
		}

		/* Creating training Provider Skills Set **/
		/*
		 * for(SkillsSet ss:skillsSetList){ TrainingProviderSkillsSet tpSs=new
		 * TrainingProviderSkillsSet(ss,trainingProviderApplication);
		 * dataEntities.add(tpSs); }
		 */
		// List<TrainingProviderSkillsSet> tpSkillsSetList
		for (TrainingProviderSkillsSet tpSkillsSet : tpSkillsSetList) {
			tpSkillsSet.setTrainingProviderApplication(trainingProviderApplication);
			dataEntities.add(tpSkillsSet);
		}

		if (dataEntities.size() > 0) {
			dao.createBatch(dataEntities);
		}

		/** Crating task */
		String desc = "There is a new Skills Programme application submitted. Please review the application.";
		TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, trainingProviderApplication.getId(),
				trainingProviderApplication.getClass().getName(), true, configDocProcessEnum,
				MailDef.instance(MailEnum.Task, MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), null);

		/* Sending Application form via email */
		// trainingProviderApplicationService.sendTPApplicationEmail(unitStandardsList,
		// qualificationsList, company, formUser, trainingProviderApplication);

	}

	public void createNonSetaTPSendTaskVTwo(List<CompanyUnitStandard> companyUnitStandardsList,
			List<CompanyQualifications> companyQualificationsList, NonSetaCompany nonSetaCompany, Users formUser,
			boolean createTask, ConfigDocProcessEnum configDocProcessEnum, HostingCompany hostingCompany,
			TrainingProviderApplication trainingProviderApplication, Address tpResidentialAddress,
			Address tpPostalAddress, ArrayList<Users> contactPersonList,
			List<TrainingProviderSkillsProgramme> tpSkillsProgramme, List<TrainingProviderSkillsSet> tpSkillsSetList,
			List<TrainingProviderDocParent> docParentList) throws Exception {

		preUserRegisterChecks(formUser);
		if (formUser.getId() == null) {
			formUser = registerService.register(formUser, true);
		} else {
			usersService.update(formUser);
		}
		AddressService.instance().create(tpResidentialAddress);
		AddressService.instance().create(tpPostalAddress);
		nonSetaCompany.setResidentialAddress(tpResidentialAddress);
		nonSetaCompany.setPostalAddress(tpPostalAddress);
		nonSetaCompany.setCompanyType(CompanyTypeEnum.TP);
		nonSetaCompany.setCompanyStatus(CompanyStatusEnum.Pending);
		// Creating Non Seta Company
		create(nonSetaCompany);
		
		// Creating Training Provider Application
		trainingProviderApplication.setUsers(formUser);
		trainingProviderApplication.setApprovalStatus(ApprovalEnum.PendingApproval);
		trainingProviderApplication.setNonSetaCompany(nonSetaCompany);
		trainingProviderApplicationService.create(trainingProviderApplication);
		
		List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
		if (formUser.getDocs() != null) {
			for (Doc doc : formUser.getDocs()) {
				doc.setCompany(null);
				doc.setVersionNo(1);
				doc.setForUsers(formUser);
				doc.setUsers(formUser);
				doc.setTargetKey(trainingProviderApplication.getId());
				doc.setTargetClass(TrainingProviderApplication.class.getName());
				dataEntities.add(doc);
				dataEntities.add(new DocByte(doc.getData(), doc));
				dataEntities.add(new DocumentTracker(doc, formUser, new java.util.Date(), DocumentTrackerEnum.Upload));
			}
		}
		for (TrainingProviderDocParent docParent : docParentList) {
			dataEntities.add(docParent);
			Doc doc = docParent.getDoc();
			doc.setVersionNo(1);
			doc.setUsers(formUser);
			doc.setTargetKey(docParent.getId());
			doc.setTargetClass(TrainingProviderDocParent.class.getName());
			dataEntities.add(doc);
			dataEntities.add(new DocByte(doc.getData(), doc));
			dataEntities.add(new DocumentTracker(doc, formUser, new java.util.Date(), DocumentTrackerEnum.Upload));
		}

		if (dataEntities.size() > 0) {
			dao.createBatch(dataEntities);
		}

		// Creating TP Contacts
		dataEntities = new ArrayList<IDataEntity>();
		for (Users u : contactPersonList) {
			
			NonSetaCompanyUsers companyUsers = new NonSetaCompanyUsers(u, nonSetaCompany);
			companyUsers.setCompanyUserType(ConfigDocProcessEnum.NON_SETA_PROVIDERS);
			
			if (u.getDesignation() != null) {
				companyUsers.setDesignation(u.getDesignation());
			}
			
			if (u.getAssessorModType() != null) {
				companyUsers.setAssessorModType(u.getAssessorModType());
			}
			
			if (u.getId() == null) {
				companyUsers.setExistingUser(false);
				registerService.register(u, true);
			} else {
				companyUsers.setExistingUser(true);
			}
			dataEntities.add(companyUsers);
		}

		// /* Creating qualifications */
		// for (Qualification qualification : qualificationsList) {
		// CompanyQualifications companyQualifications = new
		// CompanyQualifications(nonSetaCompany, qualification,
		// TrainingProviderApplication.class.getName(),
		// trainingProviderApplication.getId());
		// dataEntities.add(companyQualifications);
		// }
		//
		// /* Creating unit standards */
		// for (UnitStandards unitStandard : unitStandardsList) {
		// CompanyUnitStandard companyUnitStandard = new
		// CompanyUnitStandard(nonSetaCompany, unitStandard,
		// TrainingProviderApplication.class.getName(),
		// trainingProviderApplication.getId());
		// dataEntities.add(companyUnitStandard);
		// }
		//
		// /* Creating training Provider Skills Program **/
		// for (SkillsProgram sp : skillsProgrammeList) {
		// TrainingProviderSkillsProgramme tpSp = new
		// TrainingProviderSkillsProgramme(sp, trainingProviderApplication);
		// dataEntities.add(tpSp);
		// }
		//
		// /* Creating training Provider Skills Set **/
		// for (SkillsSet ss : skillsSetList) {
		// TrainingProviderSkillsSet tpSs = new TrainingProviderSkillsSet(ss,
		// trainingProviderApplication);
		// dataEntities.add(tpSs);
		// }

		/* Creating qualifications */
		/*
		 * for (Qualification qualification : qualificationsList) {
		 * CompanyQualifications companyQualifications = new
		 * CompanyQualifications(nonSetaCompany,
		 * qualification,TrainingProviderApplication.class.getName(),
		 * trainingProviderApplication.getId());
		 * dataEntities.add(companyQualifications); }
		 */

		for (CompanyQualifications cp : companyQualificationsList) {
			cp.setTargetClass(TrainingProviderApplication.class.getName());
			cp.setTargetKey(trainingProviderApplication.getId());
			cp.setNonSetaCompany(nonSetaCompany);
			dataEntities.add(cp);
		}

		/* Creating unit standards */
		/*
		 * for (UnitStandards unitStandard : unitStandardsList) {
		 * CompanyUnitStandard companyUnitStandard = new
		 * CompanyUnitStandard(nonSetaCompany,
		 * unitStandard,TrainingProviderApplication.class.getName(),
		 * trainingProviderApplication.getId());
		 * dataEntities.add(companyUnitStandard); }
		 */

		for (CompanyUnitStandard us : companyUnitStandardsList) {
			us.setTargetClass(TrainingProviderApplication.class.getName());
			us.setTargetKey(trainingProviderApplication.getId());
			us.setNonSetaCompany(nonSetaCompany);
			dataEntities.add(us);
		}

		/* Creating training Provider Skills Program **/
		/*
		 * for(SkillsProgram sp:skillsProgramList){
		 * TrainingProviderSkillsProgramme tpSp=new
		 * TrainingProviderSkillsProgramme(sp,trainingProviderApplication);
		 * dataEntities.add(tpSp); }
		 */

		// List<TrainingProviderSkillsProgramme> tpSkillsProgramme,
		for (TrainingProviderSkillsProgramme tpSkillsProg : tpSkillsProgramme) {
			tpSkillsProg.setTrainingProviderApplication(trainingProviderApplication);
			dataEntities.add(tpSkillsProg);
		}

		/* Creating training Provider Skills Set **/
		/*
		 * for(SkillsSet ss:skillsSetList){ TrainingProviderSkillsSet tpSs=new
		 * TrainingProviderSkillsSet(ss,trainingProviderApplication);
		 * dataEntities.add(tpSs); }
		 */
		// List<TrainingProviderSkillsSet> tpSkillsSetList
		for (TrainingProviderSkillsSet tpSkillsSet : tpSkillsSetList) {
			tpSkillsSet.setTrainingProviderApplication(trainingProviderApplication);
			dataEntities.add(tpSkillsSet);
		}

		if (dataEntities.size() > 0) {
			dao.createBatch(dataEntities);
		}

		/** Crating task */
		String desc = "There is a new Skills Programme application submitted. Please review the application.";
		TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, trainingProviderApplication.getId(), trainingProviderApplication.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), null);
		/* Sending Application form via email */
		// trainingProviderApplicationService.sendTPApplicationEmail(unitStandardsList,
		// qualificationsList, company, formUser, trainingProviderApplication);
	}

	/**
	 * Pre user register checks.
	 *
	 * @param formUser
	 *            the form user
	 * @throws Exception
	 *             the exception
	 */
	public void preUserRegisterChecks(Users formUser) throws Exception {
		boolean error = false;
		String err = "";
		if (formUser.getDocs() != null) {
			for (Doc doc : formUser.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null)
						doc.setData(docByte.getData());
				}
				if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
					error = true;
					err = err + "Upload <i>" + doc.getConfigDoc().getName() + "</i> for <i>" + formUser.getFirstName()
							+ " " + formUser.getLastName() + "</i><br/>";
				}
			}
		}
		if (error)
			throw new Exception(err);
	}

	public void rejectNonSETATPApplication(String desc, Long targetKey, String targetClass, Tasks task, Users user,
			MailDef mailDef, ArrayList<RejectReasons> selectedRejectReason,
			TrainingProviderApplication trainingProviderApplication) throws Exception {

		List<Users> usersList = new ArrayList();
		usersList.add(trainingProviderApplication.getUsers());
		TasksService.instance().findPreviousInProcessAndCreateTask(desc, user, targetKey, targetClass, true, task,
				mailDef, usersList);
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(
				targetKey, targetClass, selectedRejectReason, user, ConfigDocProcessEnum.TP);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		NonSetaCompany nonSetaCompany = trainingProviderApplication.getNonSetaCompany();
		nonSetaCompany.setCompanyStatus(CompanyStatusEnum.Rejected);
		nonSetaCompany.setApprovalEnum(ApprovalEnum.Rejected);
		update(nonSetaCompany);
		trainingProviderApplication.setApprovalStatus(ApprovalEnum.Rejected);
		trainingProviderApplicationService.update(trainingProviderApplication);
	}

	public void approveNonSETATPApplication(Tasks task, TrainingProviderApplication trainingProviderApplication)
			throws Exception {

		NonSetaCompany nonSetaCompany = trainingProviderApplication.getNonSetaCompany();
		nonSetaCompany.setCompanyStatus(CompanyStatusEnum.Active);
		nonSetaCompany.setApprovalEnum(ApprovalEnum.Approved);
		generateCompanyNumber(nonSetaCompany);
		update(nonSetaCompany);
		trainingProviderApplication.setApprovalStatus(ApprovalEnum.Approved);
		TasksService.instance().completeTask(task);
	}

	public void generateCompanyNumber(NonSetaCompany nonSetaCompany) {
		String companyNumber = String.format("%08d", nonSetaCompany.getId());
		companyNumber += "N" + companyNumber;
		nonSetaCompany.setCompanyNumber(companyNumber);

	}

	/* Jonos Code Start */
	@SuppressWarnings("unchecked")
	public List<NonSetaCompany> allNonSetaCompanyByUserAssigned(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters, Users sessionUser) throws Exception {
		String hql = "select o from NonSetaCompany o where o.id in (select distinct(x.nonSetaCompany.id) from NonSetaCompanyUsers x where x.user.id = :sessionUserId )";
		filters.put("sessionUserId", sessionUser.getId());
		return (List<NonSetaCompany>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllNonSetaCompanyByUserAssigned(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from NonSetaCompany o where o.id in (select distinct(x.nonSetaCompany.id) from NonSetaCompanyUsers x where x.user.id = :sessionUserId )";
		return dao.countWhere(filters, hql);
	}
	/* Jonos Code End */
	
	public void createLearner(Users createUser, Users entity, Company company, Company trainingProvider, CompanyLearners cl, boolean submitWorkflow, boolean requireGaurdian, List<UsersLanguage> usersLanguages, List<UsersDisability> usersDisabilityList) throws Exception {
		List<Users> userList = findRegionClinetServiceCoodinator(trainingProvider);
		if (userList.size() == 0) {
			throw new Exception("No Client Services Coordinator assigned to region");
		}
		
		if(company == null) {
			throw new Exception("Employer error on registration");
		}
		
		if(trainingProvider == null) {
			throw new Exception("Provider error on registration");
		}
		if(trainingProvider!=null && trainingProvider.getId() == null) {
			throw new Exception("Provider cerror on registration");
		}
		cl.setEmployer(company);
				
		Users gaurdian = null;
		if (requireGaurdian) {
			gaurdian = entity.getLegalGaurdian();
		} else {
			gaurdian = null;
			entity.setLegalGaurdian(gaurdian);
		}

		List<IDataEntity> createBatch = new ArrayList<>();
		List<IDataEntity> updateBatch = new ArrayList<>();
		String error = "";
		String pwdLearner = "";
		cl.setCreateUser(createUser);
		cl.setMersetaCompany(false);
		if (entity.getId() == null) {
			usersService.checkEmailUsedEmailOnly(entity.getEmail());
		}
		if (entity.getLegalGaurdian() != null && entity.getLegalGaurdian().getId() == null) {
			usersService.checkEmailUsedEmailOnly(entity.getEmail());
		}
		
		List<IDataEntity> docsDataEntities = new ArrayList<IDataEntity>();
		if (cl.getDocs() != null) {
			for (Doc doc : cl.getDocs()) {
				doc.setCompany(null);
				doc.setVersionNo(1);
				//doc.setForUsers(formUser);
				doc.setUsers(createUser);
				doc.setTargetKey(cl.getId());
				doc.setTargetClass(CompanyLearners.class.getName());
				docsDataEntities.add(doc);
				if(doc.getData() != null)
				{
					docsDataEntities.add(new DocByte(doc.getData(), doc));
					docsDataEntities.add(new DocumentTracker(doc, createUser, new java.util.Date(), DocumentTrackerEnum.Upload));
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

		if (error.length() > 0) throw new ValidationException(error);

		if (gaurdian != null) {
			if (gaurdian.getId() == null) {
				AddressService.instance().saveAddresses(entity.getResidentialAddress(), gaurdian.getPostalAddress());
				String pwd = GenericUtility.genPassord();
				gaurdian.setPassword(LogonService.encryptPassword(pwd));
				gaurdian.setStatus(UsersStatusEnum.EmailNotConfrimed);
				gaurdian.setEmailGuid(UUID.randomUUID().toString());
				gaurdian.setRegistrationDate(new java.util.Date());
				gaurdian.setAdmin(false);
				gaurdian.setAcceptPopi(false);
				createBatch.add(gaurdian);
			} else {
				updateBatch.add(gaurdian);
			}

			entity.setLegalGaurdian(gaurdian);
		}

		if (entity.getId() == null) {
			pwdLearner = GenericUtility.genPassord();
			new LogonService();
			entity.setPassword(LogonService.encryptPassword(pwdLearner));
			entity.setStatus(UsersStatusEnum.EmailNotConfrimed);
			entity.setEmailGuid(UUID.randomUUID().toString());
			entity.setRegistrationDate(new java.util.Date());
			entity.setAdmin(false);
			entity.setAcceptPopi(false);
			createBatch.add(entity);
		} else {
			updateBatch.add(entity);
		}
		
		if(usersLanguages != null && usersLanguages.size()>0) {
			if(entity!=null && entity.getId()!=null) {
				UsersLanguageService uls = new UsersLanguageService();				
				List<UsersLanguage> list=(ArrayList<UsersLanguage>) uls.findByUser(entity);
				if(list != null && list.size()>0) {
					List<IDataEntity> usersLanguagesList = new ArrayList<>();
					usersLanguagesList.addAll(list);
					dao.deleteBatch(usersLanguagesList);
				}
			}
			
			for(UsersLanguage ul:usersLanguages) {
				ul.setUser(entity);
				createBatch.add(ul);
			}
		}
		
		if(usersDisabilityList != null && usersDisabilityList.size() >0) {
			if(entity!=null && entity.getId()!=null) {
				UsersDisabilityService usersDisabilityService = new UsersDisabilityService();
				List<UsersDisability> usersDisabilitylistRemove=(ArrayList<UsersDisability>) usersDisabilityService.findByKeyUser(entity);
				if(usersDisabilitylistRemove != null && usersDisabilitylistRemove.size()>0) {
					List<IDataEntity> usersDisabilitylistIDataEntity = new ArrayList<>();
					usersDisabilitylistIDataEntity.addAll(usersDisabilitylistRemove);
					dao.deleteBatch(usersDisabilitylistIDataEntity);
				}
			}
			
			for(UsersDisability ud:usersDisabilityList ) {
				ud.setUser(entity);
				createBatch.add(ud);
			}
		}

		if (company.getId() == null) {
			createBatch.add(company);
		}else {
			updateBatch.add(company);
		}

		cl.setUser(entity);
		cl.setCompany(trainingProvider);
		
		if (submitWorkflow) cl.setStatus(ApprovalEnum.PendingApproval);
		if (cl.getId() == null) createBatch.add(cl);
		else if (cl.getId() != null) updateBatch.add(cl);

		AddressService.instance().saveAddresses(entity.getResidentialAddress(), entity.getPostalAddress());
		AddressService.instance().saveAddresses(company.getResidentialAddress(), company.getPostalAddress());
		if (gaurdian != null && gaurdian.getId() == null) {
			AddressService.instance().saveAddresses(gaurdian.getResidentialAddress(), gaurdian.getPostalAddress());
		}
		
		this.dao.createAndUpdateBatch(createBatch, updateBatch);

		for (Doc doc : cl.getDocs()) {
			if (doc.getConfigDoc().getReqImmediate()) {
				doc.setTargetKey(cl.getId());
				doc.setTargetClass(CompanyLearners.class.getName());
				DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), createUser);
			}
		}
		if (pwdLearner.length() > 0) registerService.registerLearner(entity, pwdLearner);
		if (submitWorkflow) {
			String desc="";
			TasksService.instance().findFirstInProcessAndCreateRejectTask(desc, createUser, cl.getId(),  cl.getClass().getName(), true, ConfigDocProcessEnum.LearnerRegistrationByNonMersetaCompanies, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), userList);
			//TasksService.instance().findFirstInProcessAndCreateTask(createUser, cl.getId(), CompanyLearners.class.getName(), ConfigDocProcessEnum.LearnerRegistrationByNonMersetaCompanies, false);
		}
	}
	
	public void createLearnerWorkflow(CompanyLearners entity, Users user,Tasks tasks) throws Exception {
		List<Users> userList = findRegionClinetServiceCoodinator(entity.getCompany());
		if (userList.size() == 0) {
			throw new Exception("No Client Services Coordinator assigned to region");
		}
		String desc = "Please review  ";
		entity.setStatus(ApprovalEnum.PendingApproval);
		dao.update(entity);
		TasksService.instance().findFirstInProcessAndCreateTask(desc, user, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.LearnerRegistrationByNonMersetaCompanies, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), userList);

		if(tasks != null) {
			TasksService.instance().completeTask(tasks);
		}
	}
	
	public void finalApproveWorkflow(CompanyLearners companyLearners, Users user, Tasks tasks, String documentBoxID) throws Exception {

		companyLearners.setStatus(ApprovalEnum.Approved);
		companyLearners.setRegistrationNumber(CompanyLearnersService.instance().generateCompanyLearnerRegNumber(companyLearners));
		companyLearners.setLearnerStatus(LearnerStatusEnum.Registered);
		companyLearners.setSubmissionEnum(SubmissionEnum.Completed);
		companyLearners.setApprovalDate(getSynchronizedDate());
		companyLearners.setDocumentBoxID(documentBoxID);

		dao.update(companyLearners);

		List<Users> list = new ArrayList<>();
		list.add(companyLearners.getCreateUser());
		list.add(companyLearners.getUser());

		TasksService.instance().completeTask(tasks);

		super.removeDuplicatesFromList(list);
		for (Users u : list) {
			sendApprovalEmail(companyLearners, u);
		}

		createLearnerFileManagementWorkflow(companyLearners, user, null);
	}
	
	public void rejectCompanyLearner(CompanyLearners companyLearners, Users user, Tasks tasks, List<RejectReasons> rejectReasons) throws Exception {
		companyLearners.setStatus(ApprovalEnum.Rejected);
		companyLearners.setApprovalDate(getSynchronizedDate());
		dao.update(companyLearners);
		
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearners.getId(), CompanyLearners.class.getName(), rejectReasons, user, ConfigDocProcessEnum.LearnerRegistrationByNonMersetaCompanies);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		List<Users>list = new ArrayList<>();
		list.add(companyLearners.getCreateUser());

		if (tasks != null) {
			TasksService.instance().completeTask(tasks);
		}
		
		String emailMessage = "Please Review Learner Registration By Non-Merseta Companies doing merSETA qualifications and are not funded by merSETA";
		String subject = "Learner Registration By Non-Merseta Companies doing merSETA qualifications and are not funded by merSETA";
		String description = "The learner registration application for: "+companyLearners.getUser().getFirstName()+" "+companyLearners.getUser().getLastName()+" ("+anIDNumber(companyLearners.getUser())+", "+companyLearners.getUser().getEmail()+") for "+companyLearners.getEmployer().getCompanyName()+" ("+companyLearners.getEmployer().getLevyNumber()+") has not been approved. Please review the application.";
		//TasksService.instance().findFirstInProcessAndCreateTask("", user, companyLearners.getId(), companyLearners.getClass().getName(), true, ConfigDocProcessEnum.LearnerRegistrationByNonMersetaCompanies, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, ""), list);
		TasksService.instance().createTask(CompanyLearners.class.getName(), null, emailMessage, subject, description, user, companyLearners.getId(), true, true, tasks, list, ConfigDocProcessEnum.LearnerRegistrationByNonMersetaCompanies, null);

		list.add(companyLearners.getUser());
		
		super.removeDuplicatesFromList(list);
		for(Users u : list) {
			sendRejectEmail(companyLearners, rejectReasons, u);
		}	
		//sendRejectEmailToTheLearner(companyLearners, rejectReasons);
	}
	
	public void finalRejectCompanyLearners(CompanyLearners companyLearners, Users user, Tasks tasks, List<RejectReasons> rejectReasons) throws Exception {
		companyLearners.setStatus(ApprovalEnum.Rejected);
		companyLearners.setLearnerStatus(LearnerStatusEnum.Rejected);
		companyLearners.setApprovalDate(getSynchronizedDate());
		dao.update(companyLearners);
		// TasksService.instance().completeTask(tasks);
		List<Users> users = new ArrayList<>();
		users.add(tasks.getCreateUser());
		new MailDef();
		TasksService.instance().completeTask(tasks);
		//TasksService.instance().createTask(CompanyLearners.class.getName(), companyLearners.getCompany(), emailMessage, subject, description, user, companyLearners.getId(), sendMail, createTask, tasks, users, tasks.getWorkflowProcess(), mailDef);
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearners.getId(), CompanyLearners.class.getName(), rejectReasons, user, tasks.getWorkflowProcess());
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}

		List<Users>list = new ArrayList<>();
		list.add(companyLearners.getCreateUser());
		list.add(companyLearners.getUser());
		
		super.removeDuplicatesFromList(list);
		for(Users u : list) {
			sendRejectEmail(companyLearners, rejectReasons, u);
		}	
	}
	
	public void sendRejectEmail(CompanyLearners companyLearners, List<RejectReasons> rejectReasons, Users u) throws Exception {
		CompanyService companyService = new CompanyService();
		Company company = companyService.findByKey(companyLearners.getCompany().getId());
		String accreditationNumber = "N/A";
		if(company !=null && company.getAccreditationNumber()!=null) {
			accreditationNumber = company.getAccreditationNumber();
		}
		String rsaIdPassport = anIDNumber(companyLearners.getUser());
		String QualificationString = "";
		Qualification qualification = getCompanyLearnerQualification(companyLearners);
		QualificationString = "("+qualification.getCodeString() + ") " +qualification.getDescription();
		String reasons = convertToHTML(rejectReasons);
		
		String welcome = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #FirstName# #Surname#,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "#LearnerFirstName# #LearnerSurname# (#rsaIdPassport#) "
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + 
				"The merSETA hereby confirms that the application documents for the above learner have been not been registered accordingly for the following reason(s):</p>" 
				+ reasons
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" 
				+ "Please attend to the issues and re-submit the application. Should you require any assistance or further information, kindly contact the Client Liaison Officer at the "+getRegionString(company)+" Office</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours faithfully,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Client Services Administrator</p>" + "<br/>";
		welcome = welcome.replaceAll("#FirstName#", companyLearners.getCreateUser().getFirstName());
		welcome = welcome.replaceAll("#Surname#", companyLearners.getCreateUser().getLastName());
		welcome = welcome.replaceAll("#LearnerFirstName#", companyLearners.getUser().getFirstName());
		welcome = welcome.replaceAll("#LearnerSurname#", companyLearners.getUser().getLastName());
		welcome = welcome.replaceAll("#rsaIdPassport#", rsaIdPassport);
		welcome = welcome.replaceAll("#QualificationString#", QualificationString);
		welcome = welcome.replaceAll("#CompanyName#", company.getCompanyName());
		welcome = welcome.replaceAll("#AccreditationNumber#", accreditationNumber);

		GenericUtility.sendMail(u.getEmail(), "merSETA LEARNER REGISTRATION APPLICATION", welcome, null);
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
	
	public Qualification getCompanyLearnerQualification(CompanyLearners companylearners) throws Exception {
		//CompanyQualificationsService companyQualificationsService = new CompanyQualificationsService();
		Qualification qualification = null;
		if(companylearners.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
			qualification = companylearners.getQualification();
		}else if(SKILLS_SET_LIST.contains(companylearners.getInterventionType().getId())){
			qualification = companylearners.getSkillsSet().getQualification();
		}else if(SKILLS_PROGRAM_LIST.contains(companylearners.getInterventionType().getId())){
			qualification = companylearners.getSkillsProgram().getQualification();
		}else if(companylearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership ){
			qualification = companylearners.getLearnership().getQualification();
		}else if(companylearners.getQualification() !=null &&  companylearners.getInterventionType().getId() != HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
			qualification = companylearners.getQualification();
		}else if(companylearners.getQualification() !=null &&  companylearners.getInterventionType().getId() != HAJConstants.NON_CREDIT_BEARING_SHORT_COURSE) {
			qualification = new Qualification();
			qualification.setCode(0000);
			qualification.setDescription(companylearners.getNonCredidBearingDescription());
		}
		return qualification;		
	}
	
	public String convertToHTML(List<RejectReasons> rejectReasons) {

		String sb = "<ul style=\"font-size:11.0pt;\";font-family:\"Arial\">";
		for (RejectReasons r : rejectReasons) {
			sb += "<li>" + r.getDescription() + "</li>";
		}
		sb += "</ul>";
		return sb;
	}
	
	public String getRegionString(Company company) throws Exception {
		String regionDescription = "";
		RegionTown rt = new RegionTown();
		if (company.getResidentialAddress() != null && company.getResidentialAddress().getTown() != null) {
			rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
		}
		if (regionService == null) {
			regionService = new RegionService();
		}
		
		Region r = new Region();
		if (rt.getRegion() != null) {
			r = regionService.findByKey(rt.getRegion().getId());
			if(r!=null) {
				regionDescription = r.getDescription();
			}
		}
		return regionDescription;
	}
	
	public List<Users> findRegionClinetServiceCoodinator(Company company) throws Exception {
		List<Users> list = new ArrayList<>();
		Roles roles = rolesService.findByKey(HAJConstants.CLIENT_SERVICE_COORDINATOR_ROLE_ID);
		list = hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegionAndRole(company.getResidentialAddress().getTown(), roles);
		return list;
	}
	
	public void sendApprovalEmail(CompanyLearners companyLearners, Users user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		// Qualification qualification =
		// getCompanyLearnerQualification(companyLearners);
		String toEmail = user.getEmail();
		JasperService.addLogo(params);

		// String regionDescription = getRegionString(companyLearners.getCompany());

		String rsaIdPassport = anIDNumber(companyLearners.getUser());

		String subject = "LEARNER REGISTRATION APPLICATION OUTCOME";

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + companyLearners.getCreateUser().getFirstName() + " " + companyLearners.getCreateUser().getLastName() + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Learner:  " + companyLearners.getUser().getFirstName() + " " + companyLearners.getUser().getLastName() + " (" + rsaIdPassport + ")" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Registration Number: "
				+ companyLearners.getRegistrationNumber() + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA hereby confirms that the application documents for the above learner have been registered accordingly." + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please be advised that the learner registration documents are available under the learner's profile." + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "You are requested to note that if the operations of the business changes, or if it is desired to transfer the agreement to another employer, the merSETA must be notified beforehand." + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Client Services Administrator </p>";

		GenericUtility.sendMail(toEmail, subject, mssg, null);

	}
	
	public void createLearnerFileManagementWorkflow(CompanyLearners entity, Users user, Tasks tasks) throws Exception {
		String desc = "Please review the learner file management ";
		entity.setFileApprovalEnum(ApprovalEnum.PendingApproval);
		dao.update(entity);
		TasksService.instance().findFirstInProcessAndCreateTask(desc, user, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.LearnerFileManagement, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);

		if (tasks != null) {
			TasksService.instance().completeTask(tasks);
		}
	}
}
