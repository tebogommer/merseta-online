package haj.com.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.SkillsRegistrationDAO;
import haj.com.entity.Address;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.Company;
import haj.com.entity.CompanyUsers;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.DocumentTracker;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.SDFCompany;
import haj.com.entity.SkillsRegistration;
import haj.com.entity.SkillsRegistrationQualificationUnitStandards;
import haj.com.entity.SkillsRegistrationUnitStandards;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.DocumentTrackerEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.SkillsTypeEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractService;
import haj.com.framework.AbstractUI;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.RegionService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.SkillsProgramService;
import haj.com.service.lookup.SkillsSetService;
import haj.com.utils.GenericUtility;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class SkillsRegistrationService extends AbstractService {
	
	String joins=  "left join fetch o.user u "
			+ "left join fetch u.residentialAddress "
			+ "left join fetch u.postalAddress "
			+ "left join fetch u.disabled "
			+ "left join fetch u.gender "
			+ "left join fetch u.equity "
			+ "left join fetch u.disabilityStatus "
			+ "left join fetch u.nationality "
			+ "left join fetch o.company c ";
	
	/** The dao. */
	private SkillsRegistrationDAO dao = new SkillsRegistrationDAO();
	private SkillsRegistrationUnitStandardsService registrationUnitStandardsService = new SkillsRegistrationUnitStandardsService();
	private SkillsRegistrationQualificationUnitStandardsService skillsRegistrationQualificationUnitStandardsService = new SkillsRegistrationQualificationUnitStandardsService();
	private RegionService regionService;
	/** The config doc service. */
	private ConfigDocService configDocService = new ConfigDocService();
	/**
	 * Get all SkillsRegistration
	 * 
	 * @author TechFinium
	 * @see SkillsRegistration
	 * @return a list of {@link haj.com.entity.SkillsRegistration}
	 * @throws Exception
	 *             the exception
	 */
	public List<SkillsRegistration> allSkillsRegistration() throws Exception {
		return dao.allSkillsRegistration();
	}

	/**
	 * Create or update SkillsRegistration.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SkillsRegistration
	 */
	public void create(SkillsRegistration entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update SkillsRegistration.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SkillsRegistration
	 */
	public void update(SkillsRegistration entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete SkillsRegistration.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SkillsRegistration
	 */
	public void delete(SkillsRegistration entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.SkillsRegistration}
	 * @throws Exception
	 *             the exception
	 * @see SkillsRegistration
	 */
	public SkillsRegistration findByKey(long id) throws Exception {
		return resolveSkillSkillsRegistrationUnitStandard(dao.findByKey(id));
	}

	/**
	 * Find SkillsRegistration by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.SkillsRegistration}
	 * @throws Exception
	 *             the exception
	 * @see SkillsRegistration
	 */
	public List<SkillsRegistration> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load SkillsRegistration
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.SkillsRegistration}
	 * @throws Exception
	 *             the exception
	 */
	public List<SkillsRegistration> allSkillsRegistration(int first, int pageSize) throws Exception {
		return dao.allSkillsRegistration(Long.valueOf(first), pageSize);
	}
	
	@SuppressWarnings("unchecked")
	public List<SkillsRegistration> sortAndFilterWhere(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
	
		String hql = "select o from SkillsRegistration o "+joins+" where o.approvalEnum =:approvalEnum";
		
		return  resolveSkillSkillsRegistrationUnitStandards(resolveSkillsDoc((List<SkillsRegistration>)dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters,hql)));

	}
	
	public void populateInformation(SkillsRegistration skillsRegistration) throws Exception {
		DocService docs=new DocService();
		skillsRegistration.setDocs(docs.searchByTargetKeyAndClass(skillsRegistration.getClass().getName(), skillsRegistration.getId()));
		
		SkillsRegistrationUnitStandardsService ser = new SkillsRegistrationUnitStandardsService();
		List<SkillsRegistrationUnitStandards> skillsRegistrationUnitStandards;
		skillsRegistrationUnitStandards = ser.allSkillsRegistrationUnitStandards(skillsRegistration);
		skillsRegistration.setSkillsRegistrationUnitStandards(skillsRegistrationUnitStandards);
		
		SkillsRegistrationQualificationUnitStandardsService s = new SkillsRegistrationQualificationUnitStandardsService();
		List<SkillsRegistrationQualificationUnitStandards> skillsRegistrationQualificationUnitStandardsList;
		skillsRegistrationQualificationUnitStandardsList = s.allSkillsRegistrationQualificationUnitStandards(skillsRegistration);
		skillsRegistration.setSkillsRegistrationQualificationUnitStandards(skillsRegistrationQualificationUnitStandardsList);		
	}
	
	public List<SkillsRegistration> resolveSkillSkillsRegistrationUnitStandards(List<SkillsRegistration> list) throws Exception
	{
		SkillsRegistrationUnitStandardsService ser = new SkillsRegistrationUnitStandardsService();
		List<SkillsRegistration> newList=new ArrayList<>();
		for(SkillsRegistration st:list)
		{			
			List<SkillsRegistrationUnitStandards> us = ser.allSkillsRegistrationUnitStandards(st);
			st.setSkillsRegistrationUnitStandards(us);
			newList.add(st);
		}
		
		return newList;
	}
	
	public SkillsRegistration resolveSkillSkillsRegistrationUnitStandard(SkillsRegistration st) throws Exception
	{
		SkillsRegistrationUnitStandardsService ser = new SkillsRegistrationUnitStandardsService();
		List<SkillsRegistrationUnitStandards> us = ser.allSkillsRegistrationUnitStandards(st);
		st.setSkillsRegistrationUnitStandards(us);
		return st;
	}
	
	public List<SkillsRegistration> resolveSkillsDoc(List<SkillsRegistration> list) throws Exception
	{
		DocService docs=new DocService();
		List<SkillsRegistration> newList=new ArrayList<>();
		for(SkillsRegistration st:list)
		{
			st.setDocs(docs.searchByTargetKeyAndClass(st.getClass().getName(), st.getId()));
			newList.add(st);
		}
		return newList;
		
	}

	/**
	 * Get count of SkillsRegistration for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the SkillsRegistration
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(SkillsRegistration.class);
	}

	/**
	 * Lazy load SkillsRegistration with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            SkillsRegistration class
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
	 * @return a list of {@link haj.com.entity.SkillsRegistration} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SkillsRegistration> allSkillsRegistration(Class<SkillsRegistration> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<SkillsRegistration>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of SkillsRegistration for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            SkillsRegistration class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the SkillsRegistration entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<SkillsRegistration> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public void submitRegistration(SkillsRegistration registration, Users user, List<UnitStandards> unitStandards) throws Exception {
		// SS_DEVELOPMENT
		List<IDataEntity> dataEntities = new ArrayList<>();
		ConfigDocProcessEnum configDocProcessEnum = null;
		String unitStandardID = "";
		for (UnitStandards standards : unitStandards)
		{
			unitStandardID += standards.getId() + ",";
			unitStandardID = unitStandardID.substring(0, unitStandardID.length() - 1);
			if (findSkillsByUnitStandard(unitStandardID, registration.getSkillsType())) {
				if (registration.getSkillsType() == SkillsTypeEnum.SkillsProgram) {
					configDocProcessEnum = ConfigDocProcessEnum.SP_DEVELOPMENT;
				} else {
					configDocProcessEnum = ConfigDocProcessEnum.SKILLS_SET_DEVELOPMENT;
				}
				registration.setUser(user);
				registration.setApprovalEnum(ApprovalEnum.PendingApproval);
				dataEntities.add(registration);
			} else {
				throw new Exception("There is already a " + registration.getSkillsType().getFriendlyName() + " with the selected unit standards.");
			}
		}
		
		for (UnitStandards unitStandard : unitStandards){
			dataEntities.add(new SkillsRegistrationUnitStandards(registration, unitStandard));
		}

		dao.createBatch(dataEntities);
		/*Adding Documents*/
		List<IDataEntity> docsDataEntities = new ArrayList<IDataEntity>();
		if (registration.getDocs() != null) {
			for (Doc doc : registration.getDocs()) {
				doc.setCompany(null);
				doc.setVersionNo(1);
				doc.setUsers(user);
				doc.setTargetKey(registration.getId());
				doc.setTargetClass(SkillsRegistration.class.getName());
				docsDataEntities.add(doc);
				docsDataEntities.add(new DocByte(doc.getData(), doc));
				docsDataEntities.add(new DocumentTracker(doc, user, new java.util.Date(), DocumentTrackerEnum.Upload));
			}
		}
		if (docsDataEntities.size() > 0) dao.createBatch(docsDataEntities);
		TasksService.instance().findFirstInProcessAndCreateTask(user, registration.getId(), SkillsRegistration.class.getName(), configDocProcessEnum, false);
	    //TasksService.instance().findFirstInProcessAndCreateTask("There is a new Skills Programme/Set Appliction Please review the application", user, registration.getId(), SkillsRegistration.class.getName(), true, configDocProcessEnum, null, null);
	    sendAcknoledgementEmail(registration, user,null,unitStandards);
	}
	
	public void submitRegistrationForSkillsRegistrationQualificationUnitStandardsList(SkillsRegistration registration, Users user,  List<SkillsRegistrationQualificationUnitStandards> skillsRegistrationQualificationUnitStandardsList) throws Exception {
		// SP_DEVELOPMENT
		List<IDataEntity> dataEntities = new ArrayList<>();
		ConfigDocProcessEnum configDocProcessEnum = null;
		String unitStandardID = "";
		Qualification qualification = null;
		for (SkillsRegistrationQualificationUnitStandards standards : skillsRegistrationQualificationUnitStandardsList)
		{
			unitStandardID += standards.getId() + ",";
			unitStandardID = unitStandardID.substring(0, unitStandardID.length() - 1);
			if (findSkillsByUnitStandard(unitStandardID, registration.getSkillsType())) {
				if (registration.getSkillsType() == SkillsTypeEnum.SkillsProgram) {
					configDocProcessEnum = ConfigDocProcessEnum.SP_DEVELOPMENT;
				} else {
					configDocProcessEnum = ConfigDocProcessEnum.SKILLS_SET_DEVELOPMENT;
					if(skillsRegistrationQualificationUnitStandardsList !=null) {
						List<Qualification> qualifications = new ArrayList<>();
						for(SkillsRegistrationQualificationUnitStandards skillsRegistrationQualificationUnitStandard: skillsRegistrationQualificationUnitStandardsList)
						{				
							qualifications.add(skillsRegistrationQualificationUnitStandard.getQualification());
						}
						
						if(qualifications != null) {
							qualification = returnQualificationWithHighCredit(qualifications);
						}
					}
					registration.setQualification(qualification);
				}
				registration.setUser(user);
				registration.setApprovalEnum(ApprovalEnum.PendingApproval);
				dataEntities.add(registration);
			} else {
				throw new Exception("There is already a " + registration.getSkillsType().getFriendlyName() + " with the selected unit standards.");
			}
		}
		
		for (SkillsRegistrationQualificationUnitStandards skillsRegistrationQualificationUnitStandards : skillsRegistrationQualificationUnitStandardsList){
			dataEntities.add(new SkillsRegistrationUnitStandards(registration, skillsRegistrationQualificationUnitStandards.getUnitStandards()));
			dataEntities.add(new SkillsRegistrationQualificationUnitStandards(registration, skillsRegistrationQualificationUnitStandards.getUnitStandards(), skillsRegistrationQualificationUnitStandards.getQualification()));
		}

		dao.createBatch(dataEntities);
		
		/*Adding Documents*/
		List<IDataEntity> docsDataEntities = new ArrayList<IDataEntity>();
		if (registration.getDocs() != null) {
			for (Doc doc : registration.getDocs()) {
				doc.setCompany(null);
				doc.setVersionNo(1);
				doc.setUsers(user);
				doc.setTargetKey(registration.getId());
				doc.setTargetClass(SkillsRegistration.class.getName());
				docsDataEntities.add(doc);
				docsDataEntities.add(new DocByte(doc.getData(), doc));
				docsDataEntities.add(new DocumentTracker(doc, user, new java.util.Date(), DocumentTrackerEnum.Upload));
			}
		}
		if (docsDataEntities.size() > 0) dao.createBatch(docsDataEntities);

		TasksService.instance().findFirstInProcessAndCreateTask(user, registration.getId(), SkillsRegistration.class.getName(), configDocProcessEnum, false);
	    //TasksService.instance().findFirstInProcessAndCreateTask("", user, registration.getId(), SkillsRegistration.class.getName(), true, configDocProcessEnum, null, null);
	    sendAcknoledgementEmail(registration, user,skillsRegistrationQualificationUnitStandardsList,null);
	}

	
	private Qualification returnQualificationWithHighCredit(List<Qualification> qualifications) {
		Qualification qualification = new Qualification();
		for(Qualification qual : qualifications) {
			qualification = qual;
			if(qualification.getCredits() < qual.getCredits()) {
				qualification = qual;
			}			
		}
		return qualification;		
	}
	
	public boolean findSkillsByUnitStandard(String standard, SkillsTypeEnum ste) throws Exception {
		return dao.findSkillsByUnitStandard(standard, ste);
	}

	@SuppressWarnings("unchecked")
	public void resubmitRegistration(SkillsRegistration registration, Users user, Tasks tasks, List<SkillsRegistrationQualificationUnitStandards> skillsRegistrationQualificationUnitStandardsList) throws Exception {

		List<IDataEntity> dataEntities = new ArrayList<>();
		ConfigDocProcessEnum configDocProcessEnum = null;
		String unitStandardID = "";
		
		for (SkillsRegistrationQualificationUnitStandards standards : skillsRegistrationQualificationUnitStandardsList)
		{
			unitStandardID += standards.getId() + ",";
			unitStandardID = unitStandardID.substring(0, unitStandardID.length() - 1);
			if (findSkillsByUnitStandard(unitStandardID, registration.getSkillsType())) {
				if (registration.getSkillsType() == SkillsTypeEnum.SkillsProgram) {
					configDocProcessEnum = ConfigDocProcessEnum.SP_DEVELOPMENT;
				} else {
					configDocProcessEnum = ConfigDocProcessEnum.SKILLS_SET_DEVELOPMENT;
					registration.setQualification(null);
				}
				registration.setUser(user);
				registration.setApprovalEnum(ApprovalEnum.PendingApproval);
				dataEntities.add(registration);
			} else {
				throw new Exception("There is already a " + registration.getSkillsType().getFriendlyName() + " with the selected unit standards.");
			}
		}
		
		for (SkillsRegistrationQualificationUnitStandards skillsRegistrationQualificationUnitStandards : skillsRegistrationQualificationUnitStandardsList){
			dataEntities.add(new SkillsRegistrationUnitStandards(registration, skillsRegistrationQualificationUnitStandards.getUnitStandards()));
			dataEntities.add(new SkillsRegistrationQualificationUnitStandards(registration, skillsRegistrationQualificationUnitStandards.getUnitStandards(), skillsRegistrationQualificationUnitStandards.getQualification()));
		}
		SkillsRegistrationQualificationUnitStandardsService s = new  SkillsRegistrationQualificationUnitStandardsService();
		skillsRegistrationQualificationUnitStandardsList = s.allSkillsRegistrationQualificationUnitStandards(registration);
		
		dao.deleteBatch((List<IDataEntity>) (List<?>) skillsRegistrationQualificationUnitStandardsList);
		
		dao.createBatch(dataEntities);

	    TasksService.instance().findFirstInProcessAndCreateTask("", user, registration.getId(), SkillsRegistration.class.getName(), true, configDocProcessEnum, null, null);
	    TasksService.instance().completeTask(tasks);
	    sendAcknoledgementEmail(registration, user,skillsRegistrationQualificationUnitStandardsList,null);
	}
	
	@SuppressWarnings("unchecked")
	public void resubmitRegistrationSkillsProgram(SkillsRegistration registration, Users user, Tasks tasks, List<UnitStandards> unitStandards) throws Exception {

		List<IDataEntity> dataEntities = new ArrayList<>();
		String unitStandardID = "";
		for (UnitStandards standards : unitStandards)
		{
			unitStandardID += standards.getId() + ",";
			unitStandardID = unitStandardID.substring(0, unitStandardID.length() - 1);
			if (findSkillsByUnitStandard(unitStandardID, registration.getSkillsType())) {
	
				ConfigDocProcessEnum configDocProcessEnum = null;
				if (registration.getSkillsType() == SkillsTypeEnum.SkillsProgram) {
					configDocProcessEnum = ConfigDocProcessEnum.SP_DEVELOPMENT;
				} else {
					configDocProcessEnum = ConfigDocProcessEnum.SKILLS_SET_DEVELOPMENT;
				}
	
				registration.setUser(user);
				registration.setApprovalEnum(ApprovalEnum.PendingApproval);
				// dataEntities.add(registration);
	
				for (UnitStandards unitStandard : unitStandards) {
					dataEntities.add(new SkillsRegistrationUnitStandards(registration, unitStandard));
				}
				List<SkillsRegistrationUnitStandards> us = registrationUnitStandardsService.allSkillsRegistrationUnitStandards(registration);
				dao.deleteBatch((List<IDataEntity>) (List<?>) us);
	
				dao.createBatch(dataEntities);
				create(registration);
				TasksService.instance().findFirstInProcessAndCreateTask("", user, registration.getId(), SkillsRegistration.class.getName(), true, configDocProcessEnum, null, null);
				TasksService.instance().completeTask(tasks);
				
			} else {
				throw new Exception("There is already a " + registration.getSkillsType().getFriendlyName() + " with the selected unit standards.");
			}
		}
		
		sendAcknoledgementEmail(registration, user,null,unitStandards);
	}

	public void completeRegistration(SkillsRegistration registration, Users user, Tasks tasks) throws Exception {
		update(registration);
		TasksService.instance().findNextInProcessAndCreateTask("", user, registration.getId(), SkillsRegistration.class.getName(), true, tasks, null, null);
		/*if (registration.checkforNo()) {
			TasksService.instance().findNextInProcessAndCreateTask("", user, registration.getId(), SkillsRegistration.class.getName(), true, tasks, null, null);
		}
		else {
			List<Users> signoffs = new ArrayList<>();
			signoffs.add(registration.getUser());
			registration.setApprovalEnum(ApprovalEnum.Rejected);
			registration.setApprovalDate(getSynchronizedDate());
			update(registration);
			TasksService.instance().createTaskUser(signoffs, SkillsRegistration.class.getName(), registration.getId(), "Your NSDMS application was rejected please login and view the reason and make the relevant changes.", user, true, true, tasks, tasks.getWorkflowProcess(), false);
			TasksService.instance().completeTask(tasks);
		}*/
	}
	
	public void rejectRegistrationBackToApplicant(SkillsRegistration registration, Users user, Tasks tasks, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		registration.setApprovalEnum(ApprovalEnum.Rejected);
		create(registration);
		List<Users> userList = new ArrayList<>();
		userList.add(registration.getUser());
		registration.setApprovalEnum(ApprovalEnum.Rejected);
		registration.setApprovalDate(getSynchronizedDate());
		update(registration);
		TasksService.instance().createTaskUser(userList, SkillsRegistration.class.getName(), registration.getId(), "Your NSDMS application was rejected please login and view the reason and make the relevant changes.", user, true, true, tasks, tasks.getWorkflowProcess(), false);
		TasksService.instance().completeTask(tasks);
		sendLPM_TP_001_RejectionEmail(registration, selectedRejectReason);
	}

	public void approveRegistration(SkillsRegistration registration, Users user, Tasks tasks) throws Exception {
		registration.setApprovalEnum(ApprovalEnum.WaitingForManager);
		registration.setApprovalDate(getSynchronizedDate());
		update(registration);
		TasksService.instance().findNextInProcessAndCreateTask("", user, registration.getId(), SkillsRegistration.class.getName(), true, tasks, null, null);
	}
	
	public void scheduleRegistration(SkillsRegistration registration, Users user, Tasks tasks)throws Exception {
		registration.setApprovalEnum(ApprovalEnum.PendingCommitteeApproval);
		update(registration);
		TasksService.instance().findNextInProcessAndCreateTask("", user, registration.getId(), SkillsRegistration.class.getName(), true, tasks, null, null);
		//TasksService.instance().completeTask(tasks);
	}

	public void finalApproveRegistration(SkillsRegistration registration, Users user, Tasks tasks) throws Exception {
		List<IDataEntity> iDataEntityList = new ArrayList<IDataEntity>();

		// adds new entry
		iDataEntityList.add(registration);

		// batch create
		dao.updateBatch(iDataEntityList);
		
		// Creates documents assigned to user
		List<IDataEntity> docsDataEntities = new ArrayList<IDataEntity>();
		if (registration.getDocs() != null) {
			for (Doc doc : registration.getDocs()) {
				doc.setCompany(null);
				doc.setVersionNo(1);
				//doc.setForUsers(formUser);
				doc.setUsers(user);
				doc.setTargetKey(registration.getId());
				doc.setTargetClass(registration.getClass().getName());
				docsDataEntities.add(doc);
				docsDataEntities.add(new DocByte(doc.getData(), doc));
				docsDataEntities.add(new DocumentTracker(doc, user, new java.util.Date(), DocumentTrackerEnum.Upload));
			}
		}
		if (docsDataEntities.size() > 0) dao.createBatch(docsDataEntities);
		//registration.setApprovalEnum(ApprovalEnum.PendingCommitteeApproval);
		update(registration);
		TasksService.instance().findNextInProcessAndCreateTask("", user, registration.getId(), SkillsRegistration.class.getName(), true, tasks, null, null);
	}
	
	public void finalApproveRegistrationByReviewCommitee(SkillsRegistration registration, Users user, Tasks tasks) throws Exception {
		String regNumber = generateRegistrationNumber(registration);
		registration.setRegistrationNumber(regNumber);
		registration.setApprovalEnum(ApprovalEnum.Approved);
		create(registration);
		
		if(registration.getSkillsType() == SkillsTypeEnum.SkillsSet){		
			SkillsSetService skillsSetService = new SkillsSetService();			
			//skillsSetService.createSkillSetWithSkillRegistration(registration);
			skillsSetService.createSkillsSetSkillBySkillsRegistration(registration);
		}
		else if(registration.getSkillsType() == SkillsTypeEnum.SkillsProgram){
			SkillsProgramService skillsProgramService = new SkillsProgramService();			
			skillsProgramService.createSkillProgramWithSkillRegistration(registration);
		}		
		//TasksService.instance().findNextInProcessAndCreateTask("", user, registration.getId(), SkillsRegistration.class.getName(), true, tasks, null, null);
		
		List<Users> usersList=new ArrayList<>();
		SDFCompanyService sdfCompanyService=new SDFCompanyService();
		if(registration.getCompany()!=null) {
			SDFCompany sdf = sdfCompanyService .findPrimarySDF(registration.getCompany());
			if (sdf != null) {
				usersList.add(sdf.getSdf());
			}
			CompanyUsersService companyUsersService = new CompanyUsersService();
			List<CompanyUsers>companyUsers = companyUsersService.findByCompanyType(registration.getCompany().getId(), ConfigDocProcessEnum.TP);
			if(companyUsers!=null) {
				for(CompanyUsers companyUser:companyUsers) {
					if(companyUser.getUser()!=null) {
						usersList.add(companyUser.getUser());
					}					
				}
			}
		}
		
		usersList.add(registration.getUser());
		super.removeDuplicatesFromList(usersList);
		
		if(registration.getAmendedTitle() == null || registration.getAmendedTitle().equals("")){
			for(Users u:usersList) {
				sendLPM_TP_020_ApprovalEmail(registration, u);
			}
		}else {
			for(Users u:usersList) {
				sendLPM_TP_021_ApprovalEmail(registration, u);
			}
		}
	}

	public void rejectRegistration(SkillsRegistration registration, Users user, Tasks tasks, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		registration.setApprovalEnum(ApprovalEnum.Rejected);
		create(registration);
		TasksService.instance().findPreviousInProcessAndCreateTask("", user, registration.getId(), SkillsRegistration.class.getName(), true, tasks, null, null);
		sendLPM_TP_001_RejectionEmail(registration,selectedRejectReason);
	}

	public void createRejectionForReviewCommitee(ConfigDocProcessEnum configDocProcessEnum,SkillsRegistration registration,Users formUser,List<RejectReasons> rejectReasons) throws Exception
	{
		
		registration.setApprovalEnum(ApprovalEnum.Rejected);
		registration.setApprovalDate(getSynchronizedDate());
		create(registration);
		String desc = AbstractUI.getEntryLanguage(configDocProcessEnum.getTaskDescription());
		
		TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, registration.getId(), registration.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.AMForApproval, MailTagsEnum.FirstName, formUser.getFirstName(), MailTagsEnum.LastName, formUser.getLastName(), MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), null);
		// Creating RejectReason
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(registration.getId(), AssessorModeratorApplication.class.getName(), rejectReasons, formUser, ConfigDocProcessEnum.AM);
		for(RejectReasonMultipleSelection rrm:rrmList)
		{
			if(rrm !=null)
			{
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}			
		}
		//Sending rejection latter
		sendLPM_TP_001_RejectionEmail(registration,rejectReasons);
	}
	
	public void finalRejectRegistration(SkillsRegistration registration, Users user, Tasks tasks, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		registration.setApprovalEnum(ApprovalEnum.Rejected);
		registration.setApprovalDate(getSynchronizedDate());
		create(registration);
		TasksService.instance().completeTask(tasks);
		sendLPM_TP_001_RejectionEmail(registration, selectedRejectReason);
	}

	public List<SkillsRegistration> findByCompany(Company company) throws Exception {
		return dao.findByCompany(company.getId());
	}
	
	public List<SkillsRegistration> findByUser(Users user) throws Exception {
		return dao.findByUser(user.getId());
	}
	
	public void sendLPM_TP_001_RejectionEmail(SkillsRegistration skillsRegistration, List<RejectReasons> rejectReasons) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		UsersService usersServicse= new UsersService();
		Users user=usersServicse.findByKey(skillsRegistration.getUser().getId());
		String revdate=GenericUtility.sdf7.format(new Date());
		JasperService.addLogo(params);
		Company company = new Company();
		company = skillsRegistration.getCompany();
		if(company ==null || company.getId()==null)
		{
			//Setting user ID/Passport as Barcode 
			String guide=user.getRsaIDNumber();
			if( guide==null || guide.isEmpty())
			{
				 guide=user.getPassportNumber();
			}
			company = new Company();
			company.setCompanyGuid(guide);
		}
		String title = skillsRegistration.getSkillsType().getFriendlyName();
		Address address = new Address();
		if(user.getPostalAddress() != null)
		{
			address = user.getPostalAddress();
		}
		List<SkillsRegistrationUnitStandards> skillsRegistrationUnitStandards = new ArrayList<SkillsRegistrationUnitStandards>(); 
		skillsRegistrationUnitStandards = skillsRegistration.getSkillsRegistrationUnitStandards();
		params.put("title",title.toUpperCase());
		params.put("titleLowercase",title);
		params.put("company",company);
		params.put("skillsRegistrationUnitStandards",skillsRegistrationUnitStandards);
		params.put("address",address);
		params.put("skillsRegistration",skillsRegistration);
		params.put("user",user);
		params.put("review_committee_date",revdate);
		params.put("rejectReasonDataSource", new JRBeanCollectionDataSource(rejectReasons));
		String quail = "";
		String unitstdString = "";
		List<UnitStandards> unitStandards = new ArrayList<UnitStandards>();
		if(skillsRegistration.getSkillsType() == SkillsTypeEnum.SkillsSet) {
			List<SkillsRegistrationQualificationUnitStandards> skillsRegistrationQualificationUnitStandardsList = skillsRegistrationQualificationUnitStandardsService.allSkillsRegistrationQualificationUnitStandards(skillsRegistration);
			List<Qualification> list = new ArrayList<>();
			for(SkillsRegistrationQualificationUnitStandards s: skillsRegistrationQualificationUnitStandardsList) {
				list.add(s.getQualification());
				unitStandards.add(s.getUnitStandards());
			}
			quail =  convertQualificationsToHTML(list);
		}else if(skillsRegistration.getSkillsType() == SkillsTypeEnum.SkillsProgram) {
			List<SkillsRegistrationUnitStandards> skillsRegistrationUnitStandard = skillsRegistration.getSkillsRegistrationUnitStandards();
			for(SkillsRegistrationUnitStandards std: skillsRegistrationUnitStandard) {
				unitStandards.add(std.getUnitStandards());
			}
			Qualification q = skillsRegistration.getQualification();			
			if(q!=null) {
				quail = convertQualificationToHTML(q);
			}
		}
		
		if(unitStandards.size()==0) {
			throw new Exception("Unit Standart Not Available");
		}else {
			unitstdString = convertUnitStandart(unitStandards);
		}
		
		byte[] bites = JasperService.instance().convertJasperReportToByte("SkillsProgrammeRejectionLetters.jasper", params);//LPM-TP-001-Skills-Programme-Rejection-Letter.jasper
		String subject = skillsRegistration.getSkillsType().getFriendlyName().toUpperCase()+" APPLICATION OUTCOME";
		
		String mssg =  "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + ",</p>"
				+ "<p>The merSETA hereby advises that the application for a "+skillsRegistration.getSkillsType().getFriendlyName()+" against the following Unit Standard(s): <br/>"+unitstdString+"<br/>has been reviewed by the ETQA  at a meeting held on "+revdate+" and was not approved.</p>" 	
				+ "<p>Please do not hesitate to contact the merSETA Curriculum and Learning Programmes Unit or the Regional office for further assistance. </p>" 
				+ "<p>Yours sincerely,</p>" 
				+ "<p>Manager: Quality Assurance </p>" 
				+ "<br/>";
		
		/*String mssg =  "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + ",</p>"

		+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
		+ "We regret to inform you that the ETQA Review Committee "
		+ "did not approve your "+skillsRegistration.getProposedTitle().toUpperCase()+" "
		+ "application at a meeting held on "+revdate+"."
		+ "</p>"
		
		+ "<p>The merSETA hereby advises that it has received an application for a Skills Set against the following Unit Standards(s):</p>"	
		+ quail
		
		+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
		+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"><b>Manager: Quality Assurance </b></p>";*/
		//user.getEmail()
		GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, skillsRegistration.getSkillsType().getFriendlyName()+".pdf", "pdf", null);
	}
	
	public void sendLPM_TP_020_ApprovalEmail(SkillsRegistration skillsRegistration,Users user) throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		String title = skillsRegistration.getSkillsType().getFriendlyName();
		
		String revdate=GenericUtility.sdf5.format(skillsRegistration.getApprovalDate());
		JasperService.addLogo(params);
		Company company = new Company();
		company = skillsRegistration.getCompany();
		if(company ==null || company.getId()==null)
		{
			//Setting user ID/Passport as Barcode 
			String guide=user.getRsaIDNumber();
			if(guide.isEmpty() || guide==null)
			{
				 guide=user.getPassportNumber();
			}
			company = new Company();
			company.setCompanyGuid(guide);
		}
		Address address = new Address();
		
		if(company!=null && company.getResidentialAddress()!=null) {
			address = company.getResidentialAddress();
		}
				
		params.put("address",address);
		List<SkillsRegistrationUnitStandards> skillsRegistrationUnitStandards = new ArrayList<SkillsRegistrationUnitStandards>(); 
		skillsRegistrationUnitStandards = skillsRegistration.getSkillsRegistrationUnitStandards();
		
		params.put("skillsRegistrationUnitStandards",skillsRegistrationUnitStandards);
		params.put("company",company);
		params.put("user",user);
		params.put("skillsRegistration",skillsRegistration);
		params.put("review_committee_date",revdate);
		params.put("title",title.toUpperCase());
		params.put("titleLowercase",title);

		
		byte[] bites = JasperService.instance().convertJasperReportToByte("SkillsProgrammeApprovalLetter.jasper", params);
		
		
		String subject = skillsRegistration.getSkillsType().getFriendlyName().toUpperCase()+" APPLICATION OUTCOME";
		String mssg =  "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We would like to inform you that the ETQA Review Committee "
				+ "has approved your application for the following skills Set/Programme: "+skillsRegistration.getProposedTitle().toUpperCase()+" "
				+ " at a meeting held on "+revdate+"."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"><b>Manager: Quality Assurance </b></p>";
		
		//JasperService js=new JasperService();		
		//js.createReportFromDBtoServletOutputStream("SkillsProgrammeApprovalLetter.jasper", params,"document.pdf");
		
		GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, skillsRegistration.getSkillsType().getFriendlyName()+".pdf", "pdf", null);
	}
	
	public void sendLPM_TP_021_ApprovalEmail(SkillsRegistration skillsRegistration,Users user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String title = skillsRegistration.getSkillsType().getFriendlyName();
		String revdate=GenericUtility.sdf5.format(skillsRegistration.getApprovalDate());
		JasperService.addLogo(params);
		Company company = new Company();
		company = skillsRegistration.getCompany();
		if(company !=null)
		{
			Address address = new Address();			
			if(company.getResidentialAddress()!=null) {
				address = company.getResidentialAddress();
			}
			RegionTown rt = RegionTownService.instance().findByTown(skillsRegistration.getCompany().getResidentialAddress().getTown());
			if (regionService == null) {
				regionService = new RegionService();
			}
			Region r = regionService.findByKey(rt.getRegion().getId());
			
			params.put("address",address);
			List<SkillsRegistrationUnitStandards> skillsRegistrationUnitStandards = new ArrayList<SkillsRegistrationUnitStandards>(); 
			skillsRegistrationUnitStandards = skillsRegistration.getSkillsRegistrationUnitStandards();
			
			params.put("skillsRegistrationUnitStandards",skillsRegistrationUnitStandards);
			params.put("company",company);
			params.put("user",user);
			params.put("skillsRegistration",skillsRegistration);
			params.put("review_committee_date",revdate);
			params.put("regional_office",r.getDescription());
			params.put("address",address);
			params.put("title",title.toUpperCase());
			params.put("titleLowercase",title);
			
			byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-TP-021-SkillsProgrammeApprovalLetterWithAnAmendmentToTheTitle.jasper", params);		
			
			String subject = skillsRegistration.getSkillsType().getFriendlyName().toUpperCase()+" APPLICATION OUTCOME";
			String mssg =  "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + ",</p>"
	
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					+ "We would like to inform you that the ETQA Review Committee "
					+ "has approved your application for the following skills Set/Programme: "+skillsRegistration.getProposedTitle().toUpperCase()+" "
					+ " at a meeting held on "+revdate+"."
					+ "</p>"
					
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"><b>Manager: Quality Assurance </b></p>";
	
			//JasperService js=new JasperService();		
			//js.createReportFromDBtoServletOutputStream("LPM-TP-021-SkillsProgrammeApprovalLetterWithAnAmendmentToTheTitle.jasper", params,"document.pdf");
			GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, skillsRegistration.getSkillsType().getFriendlyName()+".pdf", "pdf", null);
		}
		else
		{
			//Setting user ID/Passport as Barcode 
			String guide=user.getRsaIDNumber();
			if(guide.isEmpty() || guide==null)
			{
				 guide=user.getPassportNumber();
			}
			company = new Company();
			company.setCompanyGuid(guide);
			
			Address address = new Address();
			if(user.getPostalAddress() != null)
			{
				address = user.getPostalAddress();
			}
			params.put("address",address);
			List<SkillsRegistrationUnitStandards> skillsRegistrationUnitStandards = new ArrayList<SkillsRegistrationUnitStandards>(); 
			skillsRegistrationUnitStandards = skillsRegistration.getSkillsRegistrationUnitStandards();
			
			params.put("skillsRegistrationUnitStandards",skillsRegistrationUnitStandards);
			params.put("company",company);
			params.put("user",user);
			params.put("skillsRegistration",skillsRegistration);
			params.put("review_committee_date",revdate);
			params.put("regional_office","");
			params.put("address",address);
			params.put("title",title.toUpperCase());
			params.put("titleLowercase",title);
			
			byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-TP-021-SkillsProgrammeApprovalLetterWithAnAmendmentToTheTitle.jasper", params);		
			
			String subject = skillsRegistration.getSkillsType().getFriendlyName().toUpperCase()+" APPLICATION OUTCOME";
			String mssg =  "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + ",</p>"
	
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					+ "We would like to inform you that the ETQA Review Committee "
					+ "has approve your "+skillsRegistration.getProposedTitle().toUpperCase()+" "
					+ "application at a meeting held on "+revdate+"."
					+ "</p>"
					
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"><b>Manager: Quality Assurance </b></p>";
	
			GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, skillsRegistration.getSkillsType().getFriendlyName()+".pdf", "pdf", null);
		}
	}
	
	public String generateRegistrationNumber(SkillsRegistration registration ) throws Exception{
		String number = "";
		if(registration.getSkillsType() == SkillsTypeEnum.SkillsProgram) {
			number+="SP ";
		}else if(registration.getSkillsType() == SkillsTypeEnum.SkillsSet) {
			number+="SS ";
		}
		long seqNumber = 000000;
		
		DateFormat df = new SimpleDateFormat("yy");
		int y = Calendar.getInstance().get(Calendar.YEAR);
		String year = String.valueOf(y).substring(2,4); 
		//String year = df.format(Calendar.getInstance().get(Calendar.YEAR));
		//Skills Programme   SP 0002/06-17 where 0002 is the number of the latest number, 06 means year an 17 means merSETA 
		//Skills Set         SS 0002/15-17 where 0002 is the number of the latest number, 15 means year when approved an 17 means merSETA

		List<SkillsRegistration> list = dao.findListLastApproved();
		
		if(list != null && list.size() > 0)
		{
			SkillsRegistration skillsRegistration= list.get(0);
			
			if(skillsRegistration.getRegistrationNumber() != null)
			{
				String[] element = skillsRegistration.getRegistrationNumber().split("/");
				String firtsElement = element[0];
				String[] element1 = firtsElement.split(" ");
				long numberFromelement = Long.parseLong(element1[1]);
				
				seqNumber += (numberFromelement  + 1);
			}
			else
			{
				seqNumber+=1;
			}
			
		}
		
		number += seqNumber+"/"+year+"-17";
		return number;
	}
	
	public void prepareNewRegistration(ConfigDocProcessEnum configDocProcess, SkillsRegistration entity) throws Exception {
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
	
	public void sendAcknoledgementEmail(SkillsRegistration registration, Users u, List<SkillsRegistrationQualificationUnitStandards> skillsRegistrationQualificationUnitStandardsList, List<UnitStandards> unitStandards) throws Exception {	
		
		String skillsTile=registration.getSkillsType().getFriendlyName();
		String skillsRegTitle=registration.getProposedTitle();
		if(registration.getAmendedTitle() !=null && registration.getAmendedTitle().length()>0){
			skillsRegTitle=registration.getAmendedTitle();
		}
		if(registration.getCompany() !=null)
		{
			String leveNumber = "";
			String region ="N/A";
			if(registration.getCompany().getLevyNumber()!=null ||!registration.getCompany().getLevyNumber().equals("")) {
				leveNumber= registration.getCompany().getLevyNumber();
			}
			RegionTown rt = new RegionTown();
			if(registration.getCompany().getResidentialAddress()!=null) {rt = RegionTownService.instance().findByTown(registration.getCompany().getResidentialAddress().getTown());}
			if (regionService == null) {regionService = new RegionService();}
			
			Region r = new Region();
			if(rt !=null && rt.getRegion()!=null) {
				r = regionService.findByKey(rt.getRegion().getId());
				region=r.getDescription();
			}else {
				region = "N/A";
			}
			String quail = "";
			quail =  convertQualificationsToHTML(skillsRegistrationQualificationUnitStandardsList,unitStandards);
			String welcome = "<p>Dear #FirstName# #Surname#,</p>" 
							+ "<p>"+skillsTile.toUpperCase()+" APPLICATION: "+skillsRegTitle.toUpperCase()+" FOR #COMPANYNAME# (#ENTITYID#)." .toUpperCase()+"</p>"
							+ "<p>The merSETA acknowledges the application for a "+skillsTile+" against the following Unit Standard(s):</p>"	
							+ quail
							+ "<p>Kindly be advised that it may take up to 30 working days to review the application.</p>"
							+ "<p>Should you require any assistance or further information, kindly contact the Client Liaison Officer at the #Region# Office.</p>"
							+ "<p>Yours sincerely,</p>" 
							+ "<p>Manager: Quality Assurance</p>" 
							+ "<br/>";
			welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
			welcome = welcome.replaceAll("#Surname#", u.getLastName());
			welcome = welcome.replaceAll("#COMPANYNAME#", registration.getCompany().getCompanyName());	
			welcome = welcome.replaceAll("#ENTITYID#", leveNumber);
			welcome = welcome.replaceAll("#Region#", region);
					
			GenericUtility.sendMail(u.getEmail(), ""+skillsTile.toUpperCase()+" Application".toUpperCase(), welcome, null);
		}
		else
		{
			String quail = "";
			quail =  convertQualificationsToHTML(skillsRegistrationQualificationUnitStandardsList,unitStandards);
			String welcome = "<p>Dear #FirstName# #Surname#,</p>" 
							+ "<p>"+skillsTile.toUpperCase()+" APPLICATION: "+skillsRegTitle.toUpperCase()+"</p>"
							+ "<p>The merSETA acknowledges the application for a "+skillsTile+" against the following Unit Standard(s):</p>"	
							+ quail
							+ "<p>Kindly be advised that it may take up to 30 working days to review the application.</p>"
							+ "<p>Yours sincerely,</p>" 
							+ "<p>Manager: Quality Assurance</p>" 
							+ "<br/>";
			welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
			welcome = welcome.replaceAll("#Surname#", u.getLastName());
			GenericUtility.sendMail(u.getEmail(), ""+skillsTile.toUpperCase()+" Application".toUpperCase(), welcome, null);
		}
		
			
	}
	
	private String convertQualificationToHTML(Qualification qualification) {
		String sb ="<ul>"+"<li>" +qualification.getCode()+"  "+qualification.getDescription() +"</li>"+"</ul>"; 
		return sb;
	}
	public String convertQualificationsToHTML(List<Qualification> qualificationsList){		
		String sb ="<ul>"; 
		for (Qualification q: qualificationsList){
			sb +="<li>"+q.getCode() + "  "+q.getDescription() +"</li>";
		}
		sb +="</ul>"; 
		return sb;
	}
	
	public String convertQualificationsToHTML( List<SkillsRegistrationQualificationUnitStandards> skillsRegistrationQualificationUnitStandardsList, List<UnitStandards> unitStandards){		
		String sb ="<ul>"; 
		
		if(skillsRegistrationQualificationUnitStandardsList!=null)
		{
			for (SkillsRegistrationQualificationUnitStandards srQuallUS: skillsRegistrationQualificationUnitStandardsList){
				UnitStandards us=srQuallUS.getUnitStandards();
				sb +="<li>"+us.getCode() + "  "+us.getTitle() +"</li>";
			}
		}
		if(unitStandards !=null)
		{
			for (UnitStandards us: unitStandards){
				sb +="<li>"+us.getCode() + "  "+us.getTitle() +"</li>";
			}
		}
		
		sb +="</ul>"; 
		return sb;
	}
	
	public String convertUnitStandart(List<UnitStandards> unitStandards) {
		String sb ="<ul>"; 
		if(unitStandards !=null)
		{
			for (UnitStandards us: unitStandards){
				sb +="<li>"+us.getCode() + "  "+us.getTitle() +"</li>";
			}
		}
		sb +="</ul>"; 
		return sb;
	}
	
	public void prepareRegistrationDocument(ConfigDocProcessEnum configDocProcess, SkillsRegistration skillsRegistration,Users user) throws Exception {
		List<ConfigDoc> l = configDocService.findByProcessForRegByCompanyUserTypeEnum(configDocProcess, CompanyUserTypeEnum.User);
		if (l != null && l.size() > 0) {
			skillsRegistration.setDocs(new ArrayList<Doc>());
			l.forEach(a -> {
				skillsRegistration.getDocs().add(new Doc(a, user));
			});
		}

	}
	
    
    public List<Doc> prepareDocuments(String entity, Long targetKey, ConfigDocProcessEnum configDocProcessEnum, CompanyUserTypeEnum companyUserTypeEnum) throws Exception {
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
    
    @SuppressWarnings("unchecked")
	public List<SkillsRegistration> skillsByYearAndMonth(int first, int pageSize, String sortField,SortOrder sortOrder, Map<String, Object> filters, String searchType) throws Exception {
		return dao.skillsByYearAndMonth(SkillsRegistration.class, first, pageSize, sortField, sortOrder, filters,searchType);
	}
    
    public int countSkillsByYearAndMonth(Map<String, Object> filters,String searchType) throws Exception {
		return dao.countSkillsByYearAndMonth(filters,searchType);
	}
	
	

}
