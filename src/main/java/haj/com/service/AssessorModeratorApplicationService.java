package haj.com.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.bean.AttachmentBean;
import haj.com.bean.AssessorModeratorBean;
import haj.com.bean.QualificationUnitStandardBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.AssessorModeratorApplicationDAO;
import haj.com.entity.AssessorModExtensionOfScope;
import haj.com.entity.AssessorModReRegistration;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.AssessorModeratorCompany;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.CompanyQualifications;
import haj.com.entity.CompanyUnitStandard;
import haj.com.entity.CompanyUsers;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.DocumentTracker;
import haj.com.entity.HostingCompany;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.ReviewCommitteeMeetingAgenda;
import haj.com.entity.ReviewCommitteeMeetingUsers;
import haj.com.entity.Tasks;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.UserLearnership;
import haj.com.entity.UserQualifications;
import haj.com.entity.UserSkillsProgramme;
import haj.com.entity.UserUnitStandard;
import haj.com.entity.Users;
import haj.com.entity.UsersLanguage;
import haj.com.entity.enums.AccreditationApplicationTypeEnum;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AssessorModeratorAppTypeEnum;
import haj.com.entity.enums.AssessorModeratorTypeEnum;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.DocumentTrackerEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.enums.UsersStatusEnum;
import haj.com.entity.lookup.LegacyAssessorAccreditation;
import haj.com.entity.lookup.LegacyAssessorLearnership;
import haj.com.entity.lookup.LegacyAssessorQualification;
import haj.com.entity.lookup.LegacyAssessorSkillsProgramme;
import haj.com.entity.lookup.LegacyAssessorUnitStandard;
import haj.com.entity.lookup.LegacyModeratorLearnership;
import haj.com.entity.lookup.LegacyModeratorQualification;
import haj.com.entity.lookup.LegacyModeratorSkillsProgramme;
import haj.com.entity.lookup.LegacyModeratorUnitStandard;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.framework.AbstractUI;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.LegacyAssessorAccreditationService;
import haj.com.utils.GenericUtility;
import haj.com.validators.exports.services.AssessorModeratorApplicaitonValidationService;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class AssessorModeratorApplicationService extends AbstractService {
	
	/** The dao. */
	private AssessorModeratorApplicationDAO dao = new AssessorModeratorApplicationDAO();
	
	/** The Service. */
	private DocService docService = new DocService();
	private ReviewCommitteeMeetingAgendaService reviewCommitteeMeetingAgendaSevice=new ReviewCommitteeMeetingAgendaService();
	ReviewCommitteeMeetingUsersService reviewCommitteeMeetingUsersService=new ReviewCommitteeMeetingUsersService();
	AssessorModExtensionOfScopeService assessorModExtensionOfScopeService=new AssessorModExtensionOfScopeService();
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
	
	String joins=" left join fetch o.jobTitle jt "
			+ "left join fetch o.ofoCodes ofo "
			+ "left join fetch o.user u "
			+ "left join fetch u.residentialAddress "
			+ "left join fetch u.postalAddress "
			+ "left join fetch u.disabled "
			+ "left join fetch u.gender "
			+ "left join fetch u.equity "
			+ "left join fetch u.disabilityStatus "
			+ "left join fetch u.nationality ";

	private RegisterService registerService = new RegisterService();
	HostingCompanyEmployeesService companyEmployeesService = new HostingCompanyEmployeesService();
	
	private CompanyService companyService = new CompanyService();
	
	TrainingComitteeService trainingComitteeService = new TrainingComitteeService();
	/** The user qualifications service. */
	private UserQualificationsService userQualificationsService = new UserQualificationsService();
	/** The user unit standard service. */
	private UserUnitStandardService userUnitStandardService = new UserUnitStandardService();

	/**
	 * Get all AssessorModeratorApplication
	 * 
	 * @author TechFinium
	 * @see AssessorModeratorApplication
	 * @return a list of {@link haj.com.entity.AssessorModeratorApplication}
	 * @throws Exception
	 *             the exception
	 */
	public List<AssessorModeratorApplication> allAssessorModeratorApplication() throws Exception {
		return dao.allAssessorModeratorApplication();
	}

	/**
	 * Create or update AssessorModeratorApplication.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see AssessorModeratorApplication
	 */
	public void create(AssessorModeratorApplication entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update AssessorModeratorApplication.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see AssessorModeratorApplication
	 */
	public void update(AssessorModeratorApplication entity) throws Exception {
		this.dao.update((IDataEntity) entity);
	}
	
	public void update(List<IDataEntity> entity) throws Exception {
		if(entity !=null && entity.size()>0){
			this.dao.updateBatch(entity);
		}
	}
	
	public void create(List<IDataEntity> entity) throws Exception {
		if(entity !=null && entity.size()>0){
			this.dao.createBatch(entity);
		}
	}

	/**
	 * Delete AssessorModeratorApplication.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see AssessorModeratorApplication
	 */
	public void delete(AssessorModeratorApplication entity) throws Exception {
		this.dao.delete((IDataEntity) entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.AssessorModeratorApplication}
	 * @throws Exception
	 *             the exception
	 * @see AssessorModeratorApplication
	 */
	public AssessorModeratorApplication findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}
	
	public List<AssessorModeratorApplication> findByUserAndAppType(Long userId,AssessorModeratorAppTypeEnum applicationType) throws Exception
	{
		return dao.findByUserAndAppType(userId,applicationType);
	}

	/**
	 * Find AssessorModeratorApplication by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.AssessorModeratorApplication}
	 * @throws Exception
	 *             the exception
	 * @see AssessorModeratorApplication
	 */
	public List<AssessorModeratorApplication> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Find AssessorModeratorApplication by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.AssessorModeratorApplication}
	 * @throws Exception
	 *             the exception
	 * @see AssessorModeratorApplication
	 */
	public List<AssessorModeratorApplication> findByCerticateNumber(String desc) throws Exception {
		return dao.findByCerticateNumber(desc);
	}


	/**
	 * Lazy load AssessorModeratorApplication
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.AssessorModeratorApplication}
	 * @throws Exception
	 *             the exception
	 */
	public List<AssessorModeratorApplication> allAssessorModeratorApplication(int first, int pageSize) throws Exception {
		return dao.allAssessorModeratorApplication(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of AssessorModeratorApplication for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the AssessorModeratorApplication
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(AssessorModeratorApplication.class);
	}

	/**
	 * Lazy load AssessorModeratorApplication with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            AssessorModeratorApplication class
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
	 * @return a list of {@link haj.com.entity.AssessorModeratorApplication}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> allAssessorModeratorApplication(Class<AssessorModeratorApplication> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<AssessorModeratorApplication>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}
	
	/**
	 * Lazy load AssessorModeratorApplication with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            AssessorModeratorApplication class
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
	 * @return a list of {@link haj.com.entity.AssessorModeratorApplication}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> sortAndFilterWhere(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		
		String hql = "select o from AssessorModeratorApplication o "+joins+" where o.status =:status";
		return resolveMettingusersAndAgendas((List<AssessorModeratorApplication>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters,hql));

	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> sortAndFilterWhereUserApp(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from AssessorModeratorApplication o "+joins+" where o.user.id =:userId";
		return resolveUserDocs(resolveMettingusersAndAgendas((List<AssessorModeratorApplication>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters,hql)));
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> allApplicationsByUserIdAndType(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long userId, AssessorModeratorTypeEnum assessorModeratorType) throws Exception {
		String hql = "select o from AssessorModeratorApplication o "+joins+" where o.user.id = :userId and o.assessorModeratorType = :assessorModeratorType";
		filters.put("userId", userId);
		filters.put("assessorModeratorType", assessorModeratorType);
		return resolveUserDocs(resolveMettingusersAndAgendas((List<AssessorModeratorApplication>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters,hql)));
	}
	

	public int countAllApplicationsByUserIdAndType(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from AssessorModeratorApplication o where o.user.id = :userId and o.assessorModeratorType = :assessorModeratorType";
		return dao.countWhereUserAppInfo(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> sortAndFilterUserUnitStandard(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {		
		String hql = "select o from AssessorModeratorApplication o where o.user.id in (select x.user.id from UserUnitStandard x where x.unitStandard.id = :unitStandardId) and (o.applicationType = :applicationType or o.applicationType = :applicationType2)";
		List<AssessorModeratorApplication> dataModelAssessorModeratorApplication = resolveMettingusersAndAgendas((List<AssessorModeratorApplication>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters,hql));
		return dataModelAssessorModeratorApplication;
	}
	
	public int countUserUnitStandard(Class<AssessorModeratorApplication> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from AssessorModeratorApplication o where o.user.id in (select x.user.id from UserUnitStandard x where x.unitStandard.id = :unitStandardId)  and (o.applicationType = :applicationType or o.applicationType = :applicationType2)";
		int recordCount = dao.countWhereUserAppInfo(filters, hql);
		return recordCount;
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> sortAndFilterUserQualification(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {		
		String hql = "select o.forAssessorModeratorApplication from UserQualifications o where o.qualification.id = :qualificationID and (o.forAssessorModeratorApplication.applicationType = :applicationType or o.forAssessorModeratorApplication.applicationType = :applicationType2)";
		return resolveMettingusersAndAgendas((List<AssessorModeratorApplication>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters,hql));
	}
	
	public int countUserQualification(Class<AssessorModeratorApplication> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o.forAssessorModeratorApplication) from UserQualifications o where o.qualification.id = :qualificationID and (o.forAssessorModeratorApplication.applicationType = :applicationType or o.forAssessorModeratorApplication.applicationType = :applicationType2)";		
		return dao.countWhereUserAppInfo(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> sortAndFilterUserSkillsProgramme(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o.forAssessorModeratorApplication from UserSkillsProgramme o where o.skillsProgram.id = :skillsProgramID and (o.forAssessorModeratorApplication.applicationType = :applicationType or o.forAssessorModeratorApplication.applicationType = :applicationType2)";
		return resolveMettingusersAndAgendas((List<AssessorModeratorApplication>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters,hql));
	}
	
	public int countUserSkillsProgramme(Class<AssessorModeratorApplication> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o.forAssessorModeratorApplication) from UserSkillsProgramme o where o.skillsProgram.id = :skillsProgramID and (o.forAssessorModeratorApplication.applicationType = :applicationType or o.forAssessorModeratorApplication.applicationType = :applicationType2)";		
		return dao.countWhereUserAppInfo(filters, hql);
	}
	
	public List<AssessorModeratorApplication> resolveDoc(List<AssessorModeratorApplication> list) throws Exception
	{
		UsersService usersService=new UsersService();
		for(AssessorModeratorApplication am:list)
		{
			usersService.resolveDocs(am.getUser(), ConfigDocProcessEnum.AM, CompanyUserTypeEnum.User, am.getId(), am.getClass().getName());
		}
		
		return list;
	}
	public List<AssessorModeratorApplication>  resolveMettingusersAndAgendas(List<AssessorModeratorApplication> amList)
	{
		for(AssessorModeratorApplication amApp:amList)
		{
			try {
				if(amApp !=null && amApp.getReviewCommitteeMeeting()!=null)
				{
					amApp.getReviewCommitteeMeeting().setReviewCommitteeMeetingUsersList((ArrayList<ReviewCommitteeMeetingUsers>) reviewCommitteeMeetingUsersService.findByReviewCommitteeMeeting(amApp.getReviewCommitteeMeeting().getId()));
					amApp.getReviewCommitteeMeeting().setReviewCommitteeMeetingAgendaList((ArrayList<ReviewCommitteeMeetingAgenda>) reviewCommitteeMeetingAgendaSevice.findByReviewCommitteeMeeting(amApp.getReviewCommitteeMeeting().getId()));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return amList;
	}

	/**
	 * Get count of AssessorModeratorApplication for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            AssessorModeratorApplication class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the AssessorModeratorApplication entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<AssessorModeratorApplication> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	public int countUserInfoWhere(Class<AssessorModeratorApplication> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from AssessorModeratorApplication o where o.user.id =:userId";
		
		return dao.countWhereUserAppInfo(filters, hql);
	}
	
	

	/**
	 * Approve task for assessor and Moderator.
	 *
	 * @param assessorModerator
	 *            the assessor moderator
	 * @param task
	 *            the task
	 * @param user
	 *            the user
	 * @param company
	 *            the company
	 * @throws Exception
	 *             the exception
	 */
	public void approveTaskAssessorModerator(Users assessorModerator, Tasks task, Users user, List<AssessorModeratorCompany> companies) throws Exception {
		List<IDataEntity> iDataEntities = new ArrayList<>();
		assessorModerator.setActive(true);
		assessorModerator.setStatus(UsersStatusEnum.Active);
		iDataEntities.add(assessorModerator);

		for (AssessorModeratorCompany assessorModeratorCompany : companies) {
			Company company = assessorModeratorCompany.getCompany();
			if (company.getCompanyStatus() == CompanyStatusEnum.Pending) {
				company.setCompanyStatus(CompanyStatusEnum.Active);
			}
			iDataEntities.add(company);
			// completeTask("New Training Provider for approval", assessorModerator.getId(),
			// assessorModerator.getClass().getName(), task, user,
			// MailDef.instance(MailEnum.TPNewRequiresApproval, MailTagsEnum.CompanyName,
			// company.getCompanyName()));

		}
		dao.updateBatch(iDataEntities);
	}
	
	
	/**
	 * Creates Assessor/Moderator Application and send task.
	 *
	 * @param companyList
	 *            the entity
	 * @param formUser
	 *            the form user
	 * @param createTask
	 *            the create task
	 * @param configDocProcessEnum
	 *            the config doc process enum
	 * @param hostingCompany
	 *            the hosting company
	 * @param unitStandards
	 *            the unit standards
	 * @param qualifications
	 *            the qualifications
	 * @throws Exception
	 *             the exception
	 */
	public void createAMApplicationAndSendTask_Old(List<Company> companyList, Users formUser, boolean createTask, ConfigDocProcessEnum configDocProcessEnum, HostingCompany hostingCompany, List<UnitStandards> unitStandards, List<Qualification> qualifications, AssessorModeratorApplication am, ArrayList<UsersLanguage> userLanguageList) throws Exception {

		if (companyList.size() > 0) {
			// register check
			preRegisterChecks(companyList, formUser);
			if (configDocProcessEnum != ConfigDocProcessEnum.Learner && formUser != null) preUserRegisterChecks(formUser);
			// Updating user Info
			if (formUser.getId() != null) {
				UsersService usersService = new UsersService();
				usersService.update(formUser);
			}

			// registers new user
			if (formUser.getId() == null) formUser = registerService.register(formUser, true);
			
			if (companyEmployeesService.findByUserCount(formUser.getId(), HAJConstants.HOSTING_MERSETA) > 0) throw new Exception("Employees cannot register companies or register as an SDF.");
			
			List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
			
			// Creating Assessor moderator
			am.setStatus(ApprovalEnum.PendingApproval);
			am.setUser(formUser);
			dataEntities.add(am);

			// links Assessor Moderator to qualifications and unit standards
			linkUserOrCompanyToQualificationUnitStandards(dataEntities, unitStandards, qualifications, formUser, null, configDocProcessEnum, am);

			// Adding user languages
			for (UsersLanguage ul : userLanguageList) {
				if(ul.getId()==null)
				{
					ul.setUser(formUser);
					dataEntities.add(ul);
				}
			}

			for (Company company : companyList) {
				company.setLocked(true);
				AddressService addressService=new AddressService();
				//Creating company address if company is new
				if(company.getId()==null)
				{
					
					addressService.create(company.getPostalAddress());
					addressService.create(company.getResidentialAddress());
				}
				else
				{
					if(company.getPostalAddress() !=null)
					{
						addressService.create(company.getPostalAddress());
					}

					if(company.getResidentialAddress() !=null)
					{
						addressService.create(company.getResidentialAddress());
					}
				}
				
				/*//Crate Or update company address
				AddressService addressService=new AddressService();
				addressService.create(company.getPostalAddress());
				addressService.create(company.getResidentialAddress());*/
				companyService = new CompanyService(AbstractUI.getResourceBundle());
				companyService.createAMCompanyAndSendTask(dataEntities, company, formUser, false, configDocProcessEnum, hostingCompany, am);
			}

			if (dataEntities.size() > 0) dao.createBatch(dataEntities);
			
			// Creates documents assigned to user
			List<IDataEntity> docsDataEntities = new ArrayList<IDataEntity>();
			if (formUser.getDocs() != null) {
				for (Doc doc : formUser.getDocs()) {
					doc.setCompany(null);
					doc.setVersionNo(1);
					doc.setForUsers(formUser);
					doc.setUsers(formUser);
					doc.setTargetKey(am.getId());
					doc.setTargetClass(AssessorModeratorApplication.class.getName());
					docsDataEntities.add(doc);
					docsDataEntities.add(new DocByte(doc.getData(), doc));
					docsDataEntities.add(new DocumentTracker(doc, formUser, new java.util.Date(), DocumentTrackerEnum.Upload));
				}
			}
			if (docsDataEntities.size() > 0) dao.createBatch(docsDataEntities);


			// check if task generation is true
			if (createTask) {
				String desc = AbstractUI.getEntryLanguage(configDocProcessEnum.getTaskDescription());
				String extraInfo = "";
				TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, am.getId(), am.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.AMForApproval, MailTagsEnum.FirstName, formUser.getFirstName(), MailTagsEnum.LastName, formUser.getLastName(), MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), null);
			}
			
			//Sending Acknowledgement Latter
			sendAssessorModAcknowledgementLatter(am.getApplicationType().getDisplayName(),formUser);

		} else {
			throw new ValidationException("company.registration.list.validation.error");
		}
	}

	/**
	 * Creates Assessor/Moderator Application and send task.
	 *
	 * @param companyList
	 *            the entity
	 * @param formUser
	 *            the form user
	 * @param createTask
	 *            the create task
	 * @param configDocProcessEnum
	 *            the config doc process enum
	 * @param hostingCompany
	 *            the hosting company
	 * @param unitStandards
	 *            the unit standards
	 * @param qualifications
	 *            the qualifications
	 * @throws Exception
	 *             the exception
	 */
	public void createAMApplicationAndSendTask(Users formUser, boolean createTask, ConfigDocProcessEnum configDocProcessEnum, List<UnitStandards> unitStandards, List<Qualification> qualifications, AssessorModeratorApplication am, ArrayList<UsersLanguage> userLanguageList) throws Exception {

		
			preUserRegisterChecks(formUser);
			//Saving user address
			AddressService.instance().create(formUser.getResidentialAddress());
			AddressService.instance().create(formUser.getPostalAddress());
			// Updating user Info
			if (formUser.getId() != null) {
				UsersService usersService = new UsersService();
				usersService.update(formUser);
			}
			else// registers new user
			{
				formUser.setAcceptPopi(false);
				formUser.setAdmin(false);
				formUser = registerService.register(formUser, true);
			}
			if (companyEmployeesService.findByUserCount(formUser.getId(), HAJConstants.HOSTING_MERSETA) > 0) throw new Exception("Employees cannot register as an Assessor or Moderator");
			List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
			// Creating Assessor moderator
			am.setStatus(ApprovalEnum.PendingApproval);
			am.setUser(formUser);
			dataEntities.add(am);

			// links Assessor Moderator to qualifications and unit standards
			linkUserOrCompanyToQualificationUnitStandards(dataEntities, unitStandards, qualifications, formUser, null, configDocProcessEnum, am);

			// Adding user languages
			for (UsersLanguage ul : userLanguageList) {
				if(ul.getId()==null)
				{
					ul.setUser(formUser);
					dataEntities.add(ul);
				}
			}

			if (dataEntities.size() > 0) dao.createBatch(dataEntities);
			
			// Creates documents assigned to user
			List<IDataEntity> docsDataEntities = new ArrayList<IDataEntity>();
			if (formUser.getDocs() != null) {
				for (Doc doc : formUser.getDocs()) {
					doc.setCompany(null);
					doc.setVersionNo(1);
					doc.setForUsers(formUser);
					doc.setUsers(formUser);
					doc.setTargetKey(am.getId());
					doc.setTargetClass(AssessorModeratorApplication.class.getName());
					docsDataEntities.add(doc);
					docsDataEntities.add(new DocByte(doc.getData(), doc));
					docsDataEntities.add(new DocumentTracker(doc, formUser, new java.util.Date(), DocumentTrackerEnum.Upload));
				}
			}
			if (docsDataEntities.size() > 0) dao.createBatch(docsDataEntities);


			// check if task generation is true
			if (createTask) {
				String desc = AbstractUI.getEntryLanguage(configDocProcessEnum.getTaskDescription());
				TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, am.getId(), am.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.AMForApproval, MailTagsEnum.FirstName, formUser.getFirstName(), MailTagsEnum.LastName, formUser.getLastName(), MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), null);
			}
			
			//Sending Acknowledgement Latter
			sendAssessorModAcknowledgementLatter(am.getApplicationType().getDisplayName(),formUser);

		
	}

	public void  createModeratorLegacyAMApplicationAndSendTask(Users formUser, boolean createTask,
				ConfigDocProcessEnum configDocProcessEnum, AssessorModeratorApplication am,
				ArrayList<UsersLanguage> usersLanguageList,
				List<LegacyModeratorQualification> legacyModeratorQualificationList,
				List<LegacyModeratorSkillsProgramme> legacyModeratorSkillsProgrammeList,
				List<LegacyModeratorLearnership> legacyModeratorLearnershipList,
				List<LegacyModeratorUnitStandard> legacyModeratorUnitStandardList)  throws Exception {
			preUserRegisterChecks(formUser);
			//Saving user address
			AddressService.instance().create(formUser.getResidentialAddress());
			AddressService.instance().create(formUser.getPostalAddress());
			// Updating user Info
			if (formUser.getId() != null) {
				UsersService usersService = new UsersService();
				usersService.update(formUser);
			}
			else// registers new user
			{
				formUser.setAcceptPopi(false);
				formUser.setAdmin(false);
				formUser = registerService.register(formUser, true);
			}
			if (companyEmployeesService.findByUserCount(formUser.getId(), HAJConstants.HOSTING_MERSETA) > 0) throw new Exception("Employees cannot register as an Assessor or Moderator");
			List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
			List<IDataEntity> dataEntitiesUpdate = new ArrayList<IDataEntity>();
			// Creating Assessor moderator
			am.setStatus(ApprovalEnum.PendingApproval);
			am.setUser(formUser);
			dataEntities.add(am);
			
			// links Assessor Moderator to qualifications 
			for(LegacyModeratorQualification lq:legacyModeratorQualificationList){
				if(lq.getQualification() !=null && lq.getQualificationExpired() !=null && !lq.getQualificationExpired()){
					UserQualifications userQualifications = new UserQualifications(formUser, lq.getQualification());
					userQualifications.setForAssessorModeratorApplication(am);
					dataEntities.add(userQualifications);
					lq.setApplicationSubmited(true);
					dataEntitiesUpdate.add(lq);
				}
			}
			// links Assessor Moderator to Unit Standard 
			for(LegacyModeratorUnitStandard lu:legacyModeratorUnitStandardList){
				if(lu.getUnitStandard() !=null && lu.getUnitStandardExpired() !=null && !lu.getUnitStandardExpired()){
					UserUnitStandard userUnitStandard = new UserUnitStandard(formUser, lu.getUnitStandard());
					userUnitStandard.setForAssessorModeratorApplication(am);
					dataEntities.add(userUnitStandard);
					lu.setApplicationSubmited(true);
					dataEntitiesUpdate.add(lu);
				}
			}
			
			// links Assessor Moderator to Skills Programme 
			for(LegacyModeratorSkillsProgramme ls:legacyModeratorSkillsProgrammeList){
				if(ls.getSkillsProgram() !=null && ls.getQualificationExpired() !=null && !ls.getQualificationExpired()){
					UserSkillsProgramme usp = new UserSkillsProgramme(ls.getSkillsProgram(),formUser);
					usp.setForAssessorModeratorApplication(am);
					dataEntities.add(usp);
					ls.setApplicationSubmited(true);
					dataEntitiesUpdate.add(usp);
				}
			}
			
			// links Assessor Moderator to Skills Programme 
			for(LegacyModeratorLearnership ll:legacyModeratorLearnershipList){
				if(ll.getLearnership() !=null && ll.getQualificationExpired() !=null && !ll.getQualificationExpired()){
					UserLearnership ul = new UserLearnership(ll.getLearnership(),formUser );
					ul.setForAssessorModeratorApplication(am);
					dataEntities.add(ul);
					ll.setApplicationSubmited(true);
					dataEntitiesUpdate.add(ul);
				}
			}
			
			// Adding user languages
			for (UsersLanguage ul : usersLanguageList) {
				if(ul.getId()==null){
					ul.setUser(formUser);
					dataEntities.add(ul);
				}
			}
	
			if (dataEntities.size() > 0) dao.createBatch(dataEntities);
			if (dataEntitiesUpdate.size() > 0) dao.updateBatch(dataEntitiesUpdate);
			// Creates documents assigned to user
			List<Doc> tempDocList=new ArrayList<>();
			List<IDataEntity> docsDataEntities = new ArrayList<IDataEntity>();
			if (formUser.getDocs() != null) {
				tempDocList.addAll(formUser.getDocs());
				for (Doc doc : formUser.getDocs()) {
					doc.setCompany(null);
					doc.setVersionNo(1);
					doc.setForUsers(formUser);
					doc.setUsers(formUser);
					doc.setTargetKey(am.getId());
					doc.setTargetClass(AssessorModeratorApplication.class.getName());
					docsDataEntities.add(doc);
					docsDataEntities.add(new DocByte(doc.getData(), doc));
					docsDataEntities.add(new DocumentTracker(doc, formUser, new java.util.Date(), DocumentTrackerEnum.Upload));
				}
			}
			if (docsDataEntities.size() > 0) dao.createBatch(docsDataEntities);
			if(configDocProcessEnum==ConfigDocProcessEnum.LEGACY_AM_APPLICATION)
			{
				// check if task generation is true
				if (createTask) {
					String desc = AbstractUI.getEntryLanguage(configDocProcessEnum.getTaskDescription());
					TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, am.getId(), am.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, formUser.getFirstName(), MailTagsEnum.LastName, formUser.getLastName(), MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), null);
				}
				
				if(am.getLegacyAssessorAccreditation() !=null)
				{
					LegacyAssessorAccreditationService legacyAssessorAccreditationService=new LegacyAssessorAccreditationService();
					LegacyAssessorAccreditation legacyAm=am.getLegacyAssessorAccreditation();
					legacyAm.setProcessed(true);
					legacyAssessorAccreditationService.update(legacyAm);
				}
				//Sending Acknowledgement Latter
				sendLegacyAssessorModAcknowledgementLatter(am.getApplicationType().getDisplayName(),formUser);
			}
			else
			{
				AssessorModReRegistration assessorModReRegistration =new AssessorModReRegistration();
				assessorModReRegistration.setDocs(tempDocList);
				sendReRegistrationRequest(assessorModReRegistration, formUser, createTask, configDocProcessEnum, am, true);
				if(am.getLegacyAssessorAccreditation() !=null){
					LegacyAssessorAccreditationService legacyAssessorAccreditationService=new LegacyAssessorAccreditationService();
					LegacyAssessorAccreditation legacyAm=am.getLegacyAssessorAccreditation();
					legacyAm.setProcessed(true);
					legacyAssessorAccreditationService.update(legacyAm);
				}
			}
	
		
	}
	
	public void  createAssessorLegacyAMApplicationAndSendTask(Users formUser, boolean createTask,
			ConfigDocProcessEnum configDocProcessEnum, AssessorModeratorApplication am,
			ArrayList<UsersLanguage> usersLanguageList,
			List<LegacyAssessorQualification> legacyAssessorQualificationList,
			List<LegacyAssessorUnitStandard> legacyAssessorUnitStandardList,
			List<LegacyAssessorSkillsProgramme> legacyAssessorSkillsProgrammeList,
			List<LegacyAssessorLearnership> legacyAssessorLearnershipList)  throws Exception {
		preUserRegisterChecks(formUser);
		//Saving user address
		AddressService.instance().create(formUser.getResidentialAddress());
		AddressService.instance().create(formUser.getPostalAddress());
		// Updating user Info
		if (formUser.getId() != null) {
			UsersService usersService = new UsersService();
			usersService.update(formUser);
		}
		else// registers new user
		{
			formUser.setAcceptPopi(false);
			formUser.setAdmin(false);
			formUser = registerService.register(formUser, true);
		}
		if (companyEmployeesService.findByUserCount(formUser.getId(), HAJConstants.HOSTING_MERSETA) > 0) throw new Exception("Employees cannot register as an Assessor or Moderator");
		List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
		List<IDataEntity> dataEntitiesUpdate = new ArrayList<IDataEntity>();
		// Creating Assessor moderator
		am.setStatus(ApprovalEnum.PendingApproval);
		am.setUser(formUser);
		dataEntities.add(am);
		
		// links Assessor Moderator to qualifications 
		for(LegacyAssessorQualification lq:legacyAssessorQualificationList){
			if(lq.getQualification() !=null && lq.getQualificationExpired() !=null && !lq.getQualificationExpired()){
				UserQualifications userQualifications = new UserQualifications(formUser, lq.getQualification());
				userQualifications.setForAssessorModeratorApplication(am);
				dataEntities.add(userQualifications);
				lq.setApplicationSubmited(true);
				dataEntitiesUpdate.add(lq);
			}
		}
		// links Assessor Moderator to Unit Standard 
		for(LegacyAssessorUnitStandard lu:legacyAssessorUnitStandardList){
			if(lu.getUnitStandard() !=null  && lu.getUnitStandardExpired() !=null && !lu.getUnitStandardExpired()){
				UserUnitStandard userUnitStandard = new UserUnitStandard(formUser, lu.getUnitStandard());
				userUnitStandard.setForAssessorModeratorApplication(am);
				dataEntities.add(userUnitStandard);
				if(lu.getId() != null) {
					lu.setApplicationSubmited(true);
					dataEntitiesUpdate.add(lu);
				}
			}
		}
		
		// links Assessor Moderator to Skills Programme 
		for(LegacyAssessorSkillsProgramme ls:legacyAssessorSkillsProgrammeList ){
			if(ls.getSkillsProgram() !=null &&  ls.getQualificationExpired() !=null && !ls.getQualificationExpired()){
				UserSkillsProgramme usp = new UserSkillsProgramme(ls.getSkillsProgram(),formUser);
				usp.setForAssessorModeratorApplication(am);
				dataEntities.add(usp);
				ls.setApplicationSubmited(true);
				dataEntitiesUpdate.add(usp);
			}
		}
		
		// links Assessor Moderator to Skills Programme 
		for(LegacyAssessorLearnership ll:legacyAssessorLearnershipList){
			if(ll.getLearnership() !=null &&  ll.getQualificationExpired() !=null && !ll.getQualificationExpired()){
				UserLearnership ul = new UserLearnership(ll.getLearnership(),formUser );
				ul.setForAssessorModeratorApplication(am);
				dataEntities.add(ul);
				ll.setApplicationSubmited(true);
				dataEntitiesUpdate.add(ul);
			}
		}
		
		// Adding user languages
		for (UsersLanguage ul : usersLanguageList) {
			if(ul.getId()==null){
				ul.setUser(formUser);
				dataEntities.add(ul);
			}
		}

		if (dataEntities.size() > 0) dao.createBatch(dataEntities);
		if (dataEntitiesUpdate.size() > 0) dao.updateBatch(dataEntitiesUpdate);
		// Creates documents assigned to user
		List<Doc> tempDocs=new ArrayList<>();
		List<IDataEntity> docsDataEntities = new ArrayList<IDataEntity>();
		if (formUser.getDocs() != null) {
			tempDocs.addAll(formUser.getDocs());
			for (Doc doc : formUser.getDocs()) {
				doc.setCompany(null);
				doc.setVersionNo(1);
				doc.setForUsers(formUser);
				doc.setUsers(formUser);
				doc.setTargetKey(am.getId());
				doc.setTargetClass(AssessorModeratorApplication.class.getName());
				docsDataEntities.add(doc);
				docsDataEntities.add(new DocByte(doc.getData(), doc));
				docsDataEntities.add(new DocumentTracker(doc, formUser, new java.util.Date(), DocumentTrackerEnum.Upload));
			}
		}
		if (docsDataEntities.size() > 0) dao.createBatch(docsDataEntities);
		if(configDocProcessEnum==ConfigDocProcessEnum.LEGACY_AM_APPLICATION)
		{
			// check if task generation is true
			if (createTask) {
				String desc = AbstractUI.getEntryLanguage(configDocProcessEnum.getTaskDescription());
				TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, am.getId(), am.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, formUser.getFirstName(), MailTagsEnum.LastName, formUser.getLastName(), MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), null);
			}
			
			if(am.getLegacyAssessorAccreditation() !=null)
			{
				LegacyAssessorAccreditationService legacyAssessorAccreditationService=new LegacyAssessorAccreditationService();
				LegacyAssessorAccreditation legacyAm=am.getLegacyAssessorAccreditation();
				legacyAm.setProcessed(true);
				legacyAssessorAccreditationService.update(legacyAm);
			}
			//Sending Acknowledgement Latter
			sendLegacyAssessorModAcknowledgementLatter(am.getApplicationType().getDisplayName(),formUser);
		}
		else
		{
			AssessorModReRegistration assessorModReRegistration =new AssessorModReRegistration();
			assessorModReRegistration.setDocs(tempDocs);
			sendReRegistrationRequest(assessorModReRegistration, formUser, createTask, configDocProcessEnum, am, true);
			if(am.getLegacyAssessorAccreditation() !=null){
				LegacyAssessorAccreditationService legacyAssessorAccreditationService=new LegacyAssessorAccreditationService();
				LegacyAssessorAccreditation legacyAm=am.getLegacyAssessorAccreditation();
				legacyAm.setProcessed(true);
				legacyAssessorAccreditationService.update(legacyAm);
			}
		}

	
}
	
	public void sendReRegistrationRequest(AssessorModReRegistration assessorModReRegistration,Users formUser, boolean createTask, ConfigDocProcessEnum configDocProcessEnum, AssessorModeratorApplication am, Boolean codeOfConductAccepted) throws Exception {
		AssessorModeratorApplicationService assessorModeratorApplicationSe=new AssessorModeratorApplicationService();
		AssessorModReRegistrationService assessorModReRegistrationService=new AssessorModReRegistrationService();
		assessorModReRegistration.setAssessorModeratorApplication(am);
		assessorModReRegistration.setCodeOfConductAccepted(codeOfConductAccepted);
		assessorModReRegistration.setUser(formUser);
		assessorModReRegistration.setPreviousApplicationType(am.getApplicationType());
		assessorModReRegistration.setPreviousCodeOfConductAcceptDate(am.getCodeOfConductAcceptDate());
		assessorModReRegistration.setPreviousReviewCommitteeMeetingAgenda(am.getReviewCommitteeMeetingAgenda());
		assessorModReRegistration.setPreviousReviewCommitteeMeeting(am.getReviewCommitteeMeeting());
		assessorModReRegistration.setStatus(ApprovalEnum.PendingApproval);
		assessorModReRegistrationService.create(assessorModReRegistration);
		
		if(am.getApplicationType()==AssessorModeratorAppTypeEnum.AssessorExtensionOfScope ||
		am.getApplicationType()==AssessorModeratorAppTypeEnum.NewAssessorRegistration){
			am.setApplicationType(AssessorModeratorAppTypeEnum.AssessorReRegistration);
		}
		else{
			am.setApplicationType(AssessorModeratorAppTypeEnum.ModeratorReRegistration);
		}
		am.setCodeOfConductAccepted(codeOfConductAccepted);
		am.setCodeOfConductAcceptDate(new Date());
		am.setStatus(ApprovalEnum.PendingApproval);
		assessorModeratorApplicationSe.update(am);
		
		// Creates documents assigned to user
		List<IDataEntity> docsDataEntities = new ArrayList<IDataEntity>();
		if (assessorModReRegistration.getDocs() != null) {
			for (Doc doc : assessorModReRegistration.getDocs()) {
				doc.setCompany(null);
				doc.setVersionNo(1);
				doc.setForUsers(formUser);
				doc.setUsers(formUser);
				doc.setTargetKey(assessorModReRegistration.getId());
				doc.setTargetClass(assessorModReRegistration.getClass().getName());
				docsDataEntities.add(doc);
				docsDataEntities.add(new DocByte(doc.getData(), doc));
				docsDataEntities.add(new DocumentTracker(doc, formUser, new java.util.Date(), DocumentTrackerEnum.Upload));
			}
		}
		if (docsDataEntities.size() > 0) dao.createBatch(docsDataEntities);
		if (createTask) {
			String desc = AbstractUI.getEntryLanguage(configDocProcessEnum.getTaskDescription());
			TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, assessorModReRegistration.getId(), assessorModReRegistration.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, formUser.getFirstName(), MailTagsEnum.LastName, formUser.getLastName(), MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), null);
		}
		//Sending Acknowledgement Latter
		sendAssessorModAcknowledgementLatter(am.getApplicationType().getDisplayName(),formUser);
		
	}
	
	
	public void prepareRegistrationDocs(ConfigDocProcessEnum configDocProcess,AssessorModReRegistration assessorModReRegistration, Company company, Users user, AssessorModExtensionOfScope assessorModExtensionOfScope, CompanyUserTypeEnum companyUserType) throws Exception {
		ConfigDocService configDocService=new ConfigDocService();
		List<ConfigDoc> l = configDocService.findByProcessForRegByCompanyUserTypeEnum(configDocProcess, companyUserType);
		if (company != null) {
			if (l != null && l.size() > 0) {
				company.setDocs(new ArrayList<Doc>());
				l.forEach(a -> {
					company.getDocs().add(new Doc(a, company));
				});
			}
		} else if(user!=null) {
			user.setDocs(new ArrayList<Doc>());
			if (l != null && l.size() > 0) {
				user.setDocs(new ArrayList<Doc>());
				l.forEach(a -> {
					user.getDocs().add(new Doc(a, company));
				});
			}
		}
	    else if(assessorModReRegistration!=null) {
	    	assessorModReRegistration.setDocs(new ArrayList<Doc>());
			if (l != null && l.size() > 0) {
				assessorModReRegistration.setDocs(new ArrayList<Doc>());
				l.forEach(a -> {
					assessorModReRegistration.getDocs().add(new Doc(a, company));
				});
			}
		}
	    else if(assessorModExtensionOfScope!=null) {
	    	assessorModExtensionOfScope.setDocs(new ArrayList<Doc>());
			if (l != null && l.size() > 0) {
				assessorModExtensionOfScope.setDocs(new ArrayList<Doc>());
				l.forEach(a -> {
					assessorModExtensionOfScope.getDocs().add(new Doc(a, company));
				});
			}
		}

	}
	
	public void requestExternsionOfScope(AssessorModExtensionOfScope assessorModExtensionOfScope,Users formUser, boolean createTask, ConfigDocProcessEnum configDocProcessEnum, List<UnitStandards> unitStandards, List<Qualification> qualifications, AssessorModeratorApplication am, ArrayList<UsersLanguage> userLanguageList, Boolean codeOfConductAccepted) throws Exception {

		if(qualifications.size()>0 ||unitStandards.size()>0)
		{
			List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
			assessorModExtensionOfScope.setCodeOfConductAccepted(codeOfConductAccepted);
			assessorModExtensionOfScope.setCreateUser(formUser);
			assessorModExtensionOfScope.setAssessorModeratorApplication(am);
			assessorModExtensionOfScope.setStatus(ApprovalEnum.PendingApproval);
			
			assessorModExtensionOfScope.setPreviousApplicationType(am.getApplicationType());
			assessorModExtensionOfScope.setPreviousCodeOfConductAcceptDate(am.getCodeOfConductAcceptDate());
			assessorModExtensionOfScope.setPreviousReviewCommitteeMeeting(am.getReviewCommitteeMeeting());
			assessorModExtensionOfScope.setPreviousReviewCommitteeMeetingAgenda(am.getReviewCommitteeMeetingAgenda());
			assessorModExtensionOfScopeService.create(assessorModExtensionOfScope);
			
			am.setCodeOfConductAcceptDate(new Date());
			am.setStatus(ApprovalEnum.PendingApproval);
			update(am);
			
			for (Qualification qualification : qualifications) {
				UserQualifications userQualifications = new UserQualifications(formUser, qualification);
				userQualifications.setTargetClass(AssessorModExtensionOfScope.class.getName());
				userQualifications.setTargetKey(assessorModExtensionOfScope.getId());
				userQualifications.setQualification(qualification);
				dataEntities.add(userQualifications);
			}
			// assigns unit standards
			for (UnitStandards unitStandard : unitStandards) {
				UserUnitStandard userUnitStandard = new UserUnitStandard();
				userUnitStandard.setTargetClass(AssessorModExtensionOfScope.class.getName());
				userUnitStandard.setTargetKey(assessorModExtensionOfScope.getId());
				userUnitStandard.setUnitStandard(unitStandard);
				if(unitStandard.getQualification() !=null){
					userUnitStandard.setForQualification(unitStandard.getQualification());
				}
				dataEntities.add(userUnitStandard);
			}
			
			//Saving Documents
			if (assessorModExtensionOfScope.getDocs() != null) {
				for (Doc doc : assessorModExtensionOfScope.getDocs()) {
					doc.setCompany(null);
					doc.setVersionNo(1);
					doc.setForUsers(formUser);
					doc.setUsers(formUser);
					doc.setTargetKey(assessorModExtensionOfScope.getId());
					doc.setTargetClass(assessorModExtensionOfScope.getClass().getName());
					dataEntities.add(doc);
					dataEntities.add(new DocByte(doc.getData(), doc));
					dataEntities.add(new DocumentTracker(doc, formUser, new java.util.Date(), DocumentTrackerEnum.Upload));
				}
			}
			
			if (dataEntities.size() > 0) dao.createBatch(dataEntities);
	
			String desc = AbstractUI.getEntryLanguage(configDocProcessEnum.getTaskDescription());
			TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, assessorModExtensionOfScope.getId(), assessorModExtensionOfScope.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, formUser.getFirstName(), MailTagsEnum.LastName, formUser.getLastName(), MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), null);
		   //Sending Acknowledgement Latter
			String appTitle="";
			if(am.getApplicationType()==AssessorModeratorAppTypeEnum.NewAssessorRegistration || 
			  am.getApplicationType()==AssessorModeratorAppTypeEnum.AssessorExtensionOfScope   ||
			  am.getApplicationType()==AssessorModeratorAppTypeEnum.AssessorReRegistration     ){
				appTitle="Assessor Extension Of Scope";
				
			}
			else{
				appTitle="Moderator Extension Of Scope";
			}
			sendAssessorModAcknowledgementLatter(appTitle,formUser);
		}
		else
		{
			throw new Exception("Please provide at least one qualification or Unit");
		}
	
}
	
	public void createSeniorManagerApprovalTask(ConfigDocProcessEnum configDocProcessEnum,AssessorModeratorApplication am,Users formUser) throws Exception
	{
		am.setStatus(ApprovalEnum.ApprovedByETQA);
		update(am);
		String desc = AbstractUI.getEntryLanguage(configDocProcessEnum.getTaskDescription());
		if(configDocProcessEnum==ConfigDocProcessEnum.AM_ETQA_APPROVAL){
			TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, am.getId(), am.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.AMForApproval, MailTagsEnum.FirstName, formUser.getFirstName(), MailTagsEnum.LastName, formUser.getLastName(), MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), null);
		}
		else
		{
			AssessorModReRegistrationService assessorModReRegistrationService=new AssessorModReRegistrationService();
			 List<AssessorModReRegistration> list=assessorModReRegistrationService.findByAssessorModeratorApplication(am.getId());
			 AssessorModReRegistration assessorModReRegistration=null;
			 if(list !=null && list.size()>0){
				 assessorModReRegistration=list.get(0);
				 assessorModReRegistration.setStatus(ApprovalEnum.ApprovedByETQA);
				 assessorModReRegistrationService.update(assessorModReRegistration);
			 }
			 TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, assessorModReRegistration.getId(), assessorModReRegistration.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, formUser.getFirstName(), MailTagsEnum.LastName, formUser.getLastName(), MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), null);
				
		}
		
	}
	
	public void createSeniorManagerRejectionTask(ConfigDocProcessEnum configDocProcessEnum,AssessorModeratorApplication am,Users formUser,List<RejectReasons> rejectReasons) throws Exception
	{
		
		am.setStatus(ApprovalEnum.RejectedByEQTA);
		update(am);
		Long targetKey= am.getId();
		String targetClass= am.getClass().getName();
		String desc = AbstractUI.getEntryLanguage(configDocProcessEnum.getTaskDescription());
		if(configDocProcessEnum==ConfigDocProcessEnum.AM_ETQA_APPROVAL){
			TasksService.instance().findFirstInProcessAndCreateRejectTask(desc, formUser, am.getId(), am.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.AMForApproval, MailTagsEnum.FirstName, formUser.getFirstName(), MailTagsEnum.LastName, formUser.getLastName(), MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), null);
		}
		else{
			AssessorModReRegistrationService assessorModReRegistrationService=new AssessorModReRegistrationService();
			 List<AssessorModReRegistration> list=assessorModReRegistrationService.findByAssessorModeratorApplication(am.getId());
			 AssessorModReRegistration assessorModReRegistration=null;
			 if(list !=null && list.size()>0){
				 assessorModReRegistration=list.get(0);
				 assessorModReRegistration.setStatus(ApprovalEnum.RejectedByEQTA);
				 targetKey=assessorModReRegistration.getId();
				 targetClass=assessorModReRegistration.getClass().getName();
				 assessorModReRegistrationService.update(assessorModReRegistration);
			 }
			TasksService.instance().findFirstInProcessAndCreateRejectTask(desc, formUser, assessorModReRegistration.getId(), assessorModReRegistration.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, formUser.getFirstName(), MailTagsEnum.LastName, formUser.getLastName(), MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), null);
		}
		// Creating RejectReason
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(targetKey, targetClass, rejectReasons, formUser, configDocProcessEnum);
		for(RejectReasonMultipleSelection rrm:rrmList){
			if(rrm !=null){
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
			
		}
	}
	
	public void finalApproveTask(AssessorModeratorApplication am,Tasks task) throws Exception
	{
		AssessorModeratorApplicationService applicationService=new AssessorModeratorApplicationService();
		am.setStatus(ApprovalEnum.Approved);
		Date tempApproveDate=new Date();
		if(am.getApprovedDate() !=null){tempApproveDate=am.getApprovedDate();}
		/**
		 * Updating Approval date only if 
		 * the date is null or is 
		 * Re-Registration application
		 * */
		if(am.getApprovedDate()==null || am.getAssessorModReRegistration() !=null ){
			am.setApprovedDate(new Date());
		}
		String certificateNum="";
		if(am.getApplicationType()==AssessorModeratorAppTypeEnum.NewAssessorRegistration){
			certificateNum=getAssessorTrainingProviderCertificateNum(am,null);
			sendAssessorCertificate(certificateNum, am);
		}
		else if(am.getApplicationType()==AssessorModeratorAppTypeEnum.NewModeratorRegistration){
			certificateNum=getModeratorTrainingProviderCerticateNumber(am,null);
			sendModeratorCertificate(certificateNum, am);
		}
		else if(am.getApplicationType()==AssessorModeratorAppTypeEnum.AssessorExtensionOfScope){
			certificateNum=am.getCertificateNumber();
			sendAssessorCertificate(certificateNum, am);
		}
		else if(am.getApplicationType()==AssessorModeratorAppTypeEnum.ModeratorExtensionOfScope){
			certificateNum=am.getCertificateNumber();
			sendModeratorCertificate(certificateNum, am);
		}
		else if(am.getApplicationType()==AssessorModeratorAppTypeEnum.ModeratorReRegistration){
			certificateNum=getModeratorTrainingProviderCerticateNumber(am,null);
			sendModeratorCertificate(certificateNum, am);
			if(am.getAssessorModReRegistration() !=null){
				am.setApplicationType(am.getAssessorModReRegistration().getPreviousApplicationType());
				am.getAssessorModReRegistration().setStatus(ApprovalEnum.Approved);
				am.getAssessorModReRegistration().setApprovedDate(new Date());
				am.getAssessorModReRegistration().setPreviousCreateDate(tempApproveDate);
				AssessorModReRegistrationService assessorModReRegistrationService=new AssessorModReRegistrationService();
				assessorModReRegistrationService.update(am.getAssessorModReRegistration());
			}
		}
		else if(am.getApplicationType()==AssessorModeratorAppTypeEnum.AssessorReRegistration){
			certificateNum=getAssessorTrainingProviderCertificateNum(am,null);
			sendAssessorCertificate(certificateNum, am);
			if(am.getAssessorModReRegistration() !=null){
				am.setApplicationType(am.getAssessorModReRegistration().getPreviousApplicationType());
				am.getAssessorModReRegistration().setStatus(ApprovalEnum.Approved);
				am.getAssessorModReRegistration().setApprovedDate(new Date());
				am.getAssessorModReRegistration().setPreviousCreateDate(tempApproveDate);
				AssessorModReRegistrationService assessorModReRegistrationService=new AssessorModReRegistrationService();
				assessorModReRegistrationService.update(am.getAssessorModReRegistration());
			}
		}
		
		am.setCertificateNumber(certificateNum);
		am.setFinalApproved(true);
		applicationService.update(am);
		TasksService.instance().completeTask(task);
		
		
	}
	
	

	
	/**
	 * Approve task for assessor and Moderator.
	 */
	public void approveAMTask(Users assessorModerator, Tasks task, Users user, List<AssessorModeratorCompany> assessorModeratorCompaniesList, AssessorModeratorApplication amApplication) throws Exception {
		List<IDataEntity> iDataEntities = new ArrayList<>();
		
		amApplication.setStatus(ApprovalEnum.PendingApproval);
		assessorModerator.setActive(true);
		assessorModerator.setStatus(UsersStatusEnum.Active);
		iDataEntities.add(assessorModerator);
		iDataEntities.add(amApplication);
		dao.updateBatch(iDataEntities);
		String desc = AbstractUI.getEntryLanguage(ConfigDocProcessEnum.AM.getTaskDescription());
		TasksService.instance().findNextInProcessAndCreateTask(desc, user, amApplication.getId(), AssessorModeratorApplication.class.getName(), true, task, null, null);
	}
	
	/**
	 * Approve task for assessor and Moderator.
	 */
	public void seniorManagerFinalapproveTask(Users assessorModerator, Tasks task, Users user, List<AssessorModeratorCompany> assessorModeratorCompaniesList, AssessorModeratorApplication amApplication) throws Exception {
		List<IDataEntity> iDataEntities = new ArrayList<>();
		assignMeetingAgender(amApplication,user);
		amApplication.setStatus(ApprovalEnum.PendingCommitteeApproval);
		assessorModerator.setActive(true);
		assessorModerator.setStatus(UsersStatusEnum.Active);
		iDataEntities.add(assessorModerator);
		iDataEntities.add(amApplication);
		if(amApplication.getAssessorModReRegistration()!=null)
		{
			if(amApplication.getReviewCommitteeMeeting() !=null){
				amApplication.getAssessorModReRegistration().setReviewCommitteeMeeting(amApplication.getReviewCommitteeMeeting());
			}
			if(amApplication.getReviewCommitteeMeetingAgenda()!=null){
				amApplication.getAssessorModReRegistration().setReviewCommitteeMeetingAgenda(amApplication.getReviewCommitteeMeetingAgenda());
			}
			amApplication.getAssessorModReRegistration().setStatus(ApprovalEnum.PendingCommitteeApproval);
			iDataEntities.add(amApplication.getAssessorModReRegistration());
			dao.updateBatch(iDataEntities);
			dao.updateBatch(iDataEntities);
			String desc = AbstractUI.getEntryLanguage(ConfigDocProcessEnum.AM_RE_REGISTRATION.getTaskDescription());
			TasksService.instance().findNextInProcessAndCreateTask(desc, user, amApplication.getAssessorModReRegistration().getId(), amApplication.getAssessorModReRegistration().getClass().getName(), true, task, null, null);
		}
		else{
			dao.updateBatch(iDataEntities);
			String desc = AbstractUI.getEntryLanguage(ConfigDocProcessEnum.AM.getTaskDescription());
			TasksService.instance().findNextInProcessAndCreateTask(desc, user, amApplication.getId(), AssessorModeratorApplication.class.getName(), true, task, null, null);
		}
	}
	
	public void assignMeetingAgender(AssessorModeratorApplication amApplication,Users user) throws Exception
	{
		ReviewCommitteeMeetingAgendaService reviewCommitteeMeetingAgendaSevice=new ReviewCommitteeMeetingAgendaService();
		ScheduledEventService scheduledEventService=new ScheduledEventService();
		ReviewCommitteeMeetingUsersService reviewCommitteeMeetingUsersService=new ReviewCommitteeMeetingUsersService();
		if(amApplication.getReviewCommitteeMeeting() !=null)
		{
			Long meetingAgendaID=3L;//Assessor/Moderator Registration Approval ID
			ReviewCommitteeMeetingAgenda meetingAgender=reviewCommitteeMeetingAgendaSevice.findByMeetingAgendaAndReviewCommitteeMeeting(meetingAgendaID, amApplication.getReviewCommitteeMeeting().getId());
			if(meetingAgender !=null)
			{
				amApplication.setReviewCommitteeMeetingAgenda(meetingAgender);
			}
			else
			{
				throw new Exception("Please add Assessor/Moderator Registration Approval to the agenda of the selected Review Committee meeting");
			}
			//Adding meeting details to Events schedule
			List<Users> userList=reviewCommitteeMeetingUsersService.findUsersByReviewCommitteeMeeting(amApplication.getReviewCommitteeMeeting().getId());
			scheduledEventService.addSheduleInfo(TrainingProviderApplication.class.getName(), amApplication.getId(), user, userList, null, null, meetingAgender.getMeetingAgenda().getDescription(), amApplication.getReviewCommitteeMeeting());
		
		}
		
	}
	
	public void sendLegacyAssessorModAcknowledgementLatter( String appType,Users user) throws Exception {
		String appDate=GenericUtility.sdf5.format(new Date());
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		if(user.getRsaIDNumber() !=null)
		{
			params.put("barcode", user.getRsaIDNumber());
		}
		else
		{
			params.put("barcode", user.getPassportNumber());
		}
		
		params.put("app_type", appType.toUpperCase());
		params.put("app_date",appDate);
		params.put("user_id", user.getId());
		

		byte[] bites = JasperService.instance().convertJasperReportToByte("ETQ-TP-012-Acknowledgment_Of_Legacy_Assessor_Application.jasper", params);
		String subject = "ACKNOWLEDGEMENT OF "+appType.toUpperCase()+" APPLICATION";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " +user.getFirstName()+ " " + user.getLastName()+ ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Your application dated "+appDate+" is hereby acknowledged."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please be advised that the review process may take up to four (4) weeks."
				+ "Your application will be evaluated by "
				+ "Administrator and should any additional information "
				+ "be required, this will be communicated to you."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">ETQA Administrator</p>";

		GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "Acknowledgment_Of_"+appType.replace(" ", "_")+"_Application.pdf", "pdf", null);

}
	
	
	
	public void sendAssessorModAcknowledgementLatter( String appType,Users user) throws Exception {
		
			String appDate=GenericUtility.sdf5.format(new Date());
			
			Map<String, Object> params = new HashMap<String, Object>();
			JasperService.addLogo(params);
			if(user.getRsaIDNumber() !=null)
			{
				params.put("barcode", user.getRsaIDNumber());
			}
			else
			{
				params.put("barcode", user.getPassportNumber());
			}
			
			params.put("app_type", appType.toUpperCase());
			params.put("app_date",appDate);
			params.put("user_id", user.getId());
			

			byte[] bites = JasperService.instance().convertJasperReportToByte("ETQ-TP-012-Acknowledgment_Of_Assessor_Application.jasper", params);
			String subject = "ACKNOWLEDGEMENT OF "+appType.toUpperCase()+" APPLICATION";
			String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " +user.getFirstName()+ " " + user.getLastName()+ ",</p>"

					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					+ "Your application dated "+appDate+" is hereby acknowledged."
					+ "</p>"
					
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					+ "Please be advised that the review process may take up to eight (8) weeks."
					+ "Your application will be evaluated by the ETQA "
					+ "Administrator and should any additional information "
					+ "be required, this will be communicated to you."
					+ "</p>"
					
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					+ "Once the evidence received indicates conformance "
					+ "to the registration criteria a recommendation for "
					+ "registration will be made to the ETQA Review "
					+ "Committee after which you will be informed of the decision."
					+ "</p>"
                    
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">ETQA Administrator</p>";

			GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "Acknowledgment_Of_"+appType.replace(" ", "_")+"_Application.pdf", "pdf", null);

	}
	
	public void amExtOfScopeRejectionEmail(AssessorModExtensionOfScope amExtApp,Users user, List<RejectReasons> rejectReasons) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String barcode="";
		if(amExtApp.getCreateUser().getRsaIDNumber() !=null)
		{
			barcode=amExtApp.getCreateUser().getRsaIDNumber();
		}
		else
		{
			barcode=amExtApp.getCreateUser().getPassportNumber();
		}
		String revdate=GenericUtility.sdf5.format(amExtApp.getReviewCommitteeMeeting().getFromDateTime());
		String appTitle="";
		if(amExtApp.getAssessorModeratorApplication().getApplicationType()==AssessorModeratorAppTypeEnum.NewAssessorRegistration || 
		amExtApp.getAssessorModeratorApplication().getApplicationType()==AssessorModeratorAppTypeEnum.AssessorExtensionOfScope   ||
		amExtApp.getAssessorModeratorApplication().getApplicationType()==AssessorModeratorAppTypeEnum.AssessorReRegistration     ){
			appTitle="Assessor";
			
		}
		else{
			appTitle="Moderator";
		}
		JasperService.addLogo(params);
		params.put("barcode",barcode);
		params.put("appTitle",appTitle);
		params.put("am_app_id", amExtApp.getAssessorModeratorApplication().getId());
		params.put("review_committee_date",revdate);
		params.put("rejectReasonDataSource", new JRBeanCollectionDataSource(rejectReasons));
		params.put("appTitle",appTitle);
		
		byte[] bites = JasperService.instance().convertJasperReportToByte("ETQ-TP-013-AssessorModeratorExtensionOfScopeRejectionLetter.jasper", params);
		String subject =appTitle.toUpperCase()+" EXTENSION OF SCOPE APPLICATION OUTCOME";
		String mssg =  "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We regret to inform you that the ETQA Review Committee "
				+ "did not approve your "+appTitle+" Extension Of Scope "
				+ "application at a meeting held on "+revdate+"."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"><b>Manager: Quality Assurance </b></p>";

		GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, ""+appTitle+"_Extension_Of_Scope_Rejection_Lette.pdf", "pdf", null);
	}
	
	public void sendSeniorManagerRejectionEmail(AssessorModeratorApplication amApp,Users user, List<RejectReasons> rejectReasons) throws Exception
	{
		if(amApp.getLegacyAssessorAccreditation() !=null || amApp.getLegacyModeratorAccreditation() !=null){
			sendLegacySeniorManagerRejectionEmail(amApp, user, rejectReasons);
		}
		else{
			sendAMApplicationRejectionEmail(amApp, user, rejectReasons);
		}
	}
	
	public void sendfinalRejecApplicationEmail(AssessorModeratorApplication amApp,Users user, List<RejectReasons> rejectReasons) throws Exception
	{
		sendfinalRejecApplicationEmailJasper(amApp, user, rejectReasons);
	}
	
	public void sendAMApplicationRejectionEmail(AssessorModeratorApplication amApp,Users user, List<RejectReasons> rejectReasons) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String barcode="";
		if(amApp.getUser().getRsaIDNumber() !=null && !amApp.getUser().getRsaIDNumber().isEmpty() && !amApp.getUser().getRsaIDNumber().equals(" ") ){
			barcode=amApp.getUser().getRsaIDNumber();
		}
		else{
			barcode=amApp.getUser().getPassportNumber();
		}
		String revdate=GenericUtility.sdf5.format(new Date());
		JasperService.addLogo(params);
		params.put("barcode",barcode);
		params.put("am_app_id", amApp.getId());
		params.put("review_committee_date",revdate);
		params.put("rejectReasonDataSource", new JRBeanCollectionDataSource(rejectReasons));
		String fileName=amApp.getApplicationType().getDisplayName().replace(" ", "_")+"_Rejection_Lette.pdf";
		byte[] bites = JasperService.instance().convertJasperReportToByte("ETQ-TP-013-AssessorModeratorRejectionLetter.jasper", params);
		String subject = amApp.getApplicationType().getDisplayName().toUpperCase()+" APPLICATION OUTCOME";
		String mssg =  "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We regret to inform you that the ETQA Review Committee "
				+ "did not approve your "+amApp.getApplicationType().getDisplayName()+" "
				+ "application at a meeting held on "+revdate+"."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"><b>Manager: Quality Assurance </b></p>";

		GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, fileName, "pdf", null);
	}
	
	public void sendfinalRejecApplicationEmailJasper(AssessorModeratorApplication amApp,Users user, List<RejectReasons> rejectReasons) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String barcode="";
		if(amApp.getUser().getRsaIDNumber() !=null && !amApp.getUser().getRsaIDNumber().isEmpty() && !amApp.getUser().getRsaIDNumber().equals(" ") ){
			barcode=amApp.getUser().getRsaIDNumber();
		}
		else{
			barcode=amApp.getUser().getPassportNumber();
		}
		String revdate=GenericUtility.sdf5.format(new Date());
		JasperService.addLogo(params);
		params.put("barcode",barcode);
		params.put("am_app_id", amApp.getId());
		params.put("review_committee_date",revdate);
		params.put("rejectReasonDataSource", new JRBeanCollectionDataSource(rejectReasons));
		String fileName=amApp.getApplicationType().getDisplayName().replace(" ", "_")+"_Rejection_Lette.pdf";
		byte[] bites = JasperService.instance().convertJasperReportToByte("ETQ-TP-013-AssessorModeratorRejectionLetterNew.jasper", params);
		String subject = amApp.getApplicationType().getDisplayName().toUpperCase()+" APPLICATION OUTCOME";
		String mssg =  "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We regret to inform you that your "+amApp.getApplicationType().getDisplayName()+"application has not been approved for the following reason(s)." + "</p>"				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ ""+convertToHTML(rejectReasons)+""
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please contact the Regional Office or merSETA Head Office for further assistance.</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"><b>Manager: Quality Assurance </b></p>";

		GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, fileName, "pdf", null);
	}
	
	public void sendLegacySeniorManagerRejectionEmail(AssessorModeratorApplication amApp,Users user, List<RejectReasons> rejectReasons) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String barcode="";
		if(amApp.getUser().getRsaIDNumber() !=null && !amApp.getUser().getRsaIDNumber().isEmpty() && !amApp.getUser().getRsaIDNumber().equals(" ") ){
			barcode=amApp.getUser().getRsaIDNumber();
		}
		else{
			barcode=amApp.getUser().getPassportNumber();
		}
		JasperService.addLogo(params);
		params.put("barcode",barcode);
		params.put("am_app_id", amApp.getId());
		params.put("rejectReasonDataSource", new JRBeanCollectionDataSource(rejectReasons));
		String fileName=amApp.getApplicationType().getDisplayName().replace(" ", "_")+"_Rejection_Lette.pdf";
		byte[] bites = JasperService.instance().convertJasperReportToByte("ETQ-TP-013-LegacyAssessorModeratorRejectionLetter.jasper", params);
		String subject = amApp.getApplicationType().getDisplayName().toUpperCase()+" APPLICATION OUTCOME";
		String mssg =  "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please be advised that the merSETA has reviewed your "+amApp.getApplicationType().getDisplayName()+" "
				+ "and has not approved the application for the following reason(s):"
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ ""+convertToHTML(rejectReasons)+""
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "For any assistance, please contact merSETA Head Office."
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"><b>Manager: Quality Assurance </b></p>";

		GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, fileName, "pdf", null);
	}
	
	public String convertToHTML(List<RejectReasons> rejectReasons){		
		String sb ="<ul  style=\"font-size:11.0pt;\";font-family:\"Arial\">"; 
		for (RejectReasons r: rejectReasons){
			sb +="<li>"+r.getDescription() +"</li>";
		}
		sb +="</ul>"; 
		return sb;
}
	
	public void sendAssessorCertificate( String certificateNumber,AssessorModeratorApplication am) throws Exception {
		
			String barcode="";
			if(am.getUser().getRsaIDNumber() !=null && am.getUser().getRsaIDNumber().isEmpty()==false){
				barcode=am.getUser().getRsaIDNumber();
			}
			else{
				barcode=am.getUser().getPassportNumber();
			}
			
			if(am.getLegacyAssessorAccreditation() !=null && am.getAssessorModReRegistration()==null && (am.getFinalApproved()==null || !am.getFinalApproved())){
				/**Update stat and end date only if its null, 
				 * else use legacy start and end date
				 **/
				if(am.getStartDate()==null){
					am.setStartDate(new Date());
				}
				if(am.getEndDate()==null){
					am.setEndDate(GenericUtility.addYearsToDate(new Date(), 3));
				}
			}
			else {
				
				am.setStartDate(new Date());
				am.setEndDate(GenericUtility.addYearsToDate(new Date(), 3));
			}
			//Updating Start And End Date
			update(am);
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("certificate_number", certificateNumber);
			params.put("date_of_expiry", GenericUtility.sdf5.format(am.getEndDate()));
			params.put("date_of_registration", GenericUtility.sdf5.format(am.getApprovedDate()));
			params.put("barcode",barcode);
			params.put("user_id", am.getUser().getId());
			JasperService.addImage(params, "top_bottom_boder.png", "top_bottom_border");
			JasperService.addImage(params, "left_right_boder.png", "left_right_border");
			JasperService.addImage(params, "certificate_signature.png", "certificate_signature");
			JasperService.addImage(params, "corner_image.png", "corner_image");
			JasperService.addImage(params, "logo2.png", "logo");
			

			
			
			//Certificate Attachment
			AttachmentBean beanAttachment=new AttachmentBean();
			ArrayList<AttachmentBean> attachmentBeans=new ArrayList<>();
			byte[] bites = JasperService.instance().convertJasperReportToByte("AssessorCertificate.jasper", params);
			beanAttachment.setExt("pdf");
			beanAttachment.setFile(bites);
			beanAttachment.setFilename("AssessorCertificate.pdf");
			attachmentBeans.add(beanAttachment);
			
			//ETQ-TP-027-AssessorCertificateLetter Attachment
			params = new HashMap<String, Object>();
			JasperService.addLogo(params);
			params.put("barcode",barcode);
			params.put("am_app_id", am.getId());
			byte[] bites2 = JasperService.instance().convertJasperReportToByte("ETQ-TP-027-AssessorCertificateLetter.jasper", params);
			beanAttachment=new AttachmentBean();
			beanAttachment.setExt("pdf");
			beanAttachment.setFile(bites2);
			beanAttachment.setFilename("AssessorCertificateLetter.pdf");
			attachmentBeans.add(beanAttachment);
			
			Users user = new Users();
			List<Users> list=hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.Senior_Manager_Quality_Assurance_and_Partnerships_ID);
			if(list!=null && list.size()>0) {
				user = list.get(0) ;
			}
			params = new HashMap<String, Object>();
			JasperService.addLogo(params);
			params.put("assessorModeratorApplication",am);
			params.put("user",user);
			params.put("user_id",am.getUser().getId() );
			params.put("registration_number",certificateNumber);
			params.put("status",am.getStatus().getFriendlyName());
			params.put("barcode",barcode);
			params.put("date", GenericUtility.sdf7.format(new Date()));
			ArrayList<QualificationUnitStandardBean> qualificationUnitStandardList=new ArrayList<>();
			//Creating QualificationUnitStandardBeanDataSource
			
			// version one
//			 List<UserQualifications> userQualifications=userQualificationsService.findByUserAMApplication(am.getUser().getId(), am.getId());
//			 List<UserUnitStandard> userUnitStandards=userUnitStandardService.findByUserAndAPApplication(am.getUser().getId(), am.getId());
			// version two
			List<UserQualifications> userQualifications=userQualificationsService.findByUserAMApplicationAndAccept(am.getUser().getId(), am.getId(), true);
			 List<UserUnitStandard> userUnitStandards=userUnitStandardService.findByUserAndAPApplicationAndAccept(am.getUser().getId(), am.getId(), true);
			 for(UserQualifications qual:userQualifications){
				 qualificationUnitStandardList.add(new QualificationUnitStandardBean(String.valueOf(qual.getQualification().getCode()), qual.getQualification().getDescription(), GenericUtility.sdf7.format(am.getApprovedDate())));
			 }
			 
			 for(UserUnitStandard us:userUnitStandards){
				 qualificationUnitStandardList.add(new QualificationUnitStandardBean(String.valueOf(us.getUnitStandard().getCode()), us.getUnitStandard().getTitle(), GenericUtility.sdf7.format(am.getApprovedDate())));
			 }
			params.put("QualificationUnitStandardBeanDataSource", new JRBeanCollectionDataSource(qualificationUnitStandardList));
			byte[] bites3 = JasperService.instance().convertJasperReportToByte("ETQ-TP-011-StatementOfQualificationsandorUnitStandardsNew.jasper", params);
			beanAttachment=new AttachmentBean();
			beanAttachment.setExt("pdf");
			beanAttachment.setFile(bites3);
			beanAttachment.setFilename("Statement_Of_Qualifications_And_Or_UnitStandards.pdf");
			attachmentBeans.add(beanAttachment);
			

			String subject = "ASSESSOR ACCREDITATION CERTIFICATE";
			String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " +am.getUser().getFirstName()+ " " + am.getUser().getLastName()+ ",</p>"

					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					
					+ "The merSETA would like to congratulate you for having successfully "
					+ "been registered as an Assessor as per the attached Statement of "
					+ "Results of the Qualification(s) and/or Unit Statement(s)."
				
					+ "</p>"
					
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"

					+ "You are requested to go through the merSETA Quality Assurance "
					+ "& Partnerships policies, particularly the registration of "
					+ "Assessor and the code of good conduct in the Assessor "
					+ "section to acclimatise yourself with them."
					+ "</p>"
					
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"

					+ "Looking forward to you having a fruitful relationship "
					+ "with the merSETA. Enclosed is your certificate."
				
					+ "</p>"
                    
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">MerSETA Administration</p>";

			//GenericUtility.sendMailWithAttachement(am.getUser().getEmail(), subject, mssg, bites, "Assessor_Certificate.pdf", "pdf", null);
			GenericUtility.sendMailWithAttachementTempWithLog(am.getUser().getEmail(), subject, mssg, attachmentBeans, null,am.getClass().getName(),am.getId());

	}
	
	
	public void sendModeratorCertificate( String certificateNumber,AssessorModeratorApplication am) throws Exception {
			String barcode="";
			if(am.getUser().getRsaIDNumber() !=null && am.getUser().getRsaIDNumber().isEmpty()==false)
			{
				barcode=am.getUser().getRsaIDNumber();
			}
			else
			{
				barcode=am.getUser().getPassportNumber();
			}
			if(am.getLegacyModeratorAccreditation() !=null && am.getAssessorModReRegistration()==null && (am.getFinalApproved()==null || !am.getFinalApproved())){
				/**Update stat and end date only if its null, 
				 * else use legacy start and end date
				 **/
				if(am.getStartDate()==null){
					am.setStartDate(new Date());
				}
				if(am.getEndDate()==null){
					am.setEndDate(GenericUtility.addYearsToDate(new Date(), 3));
				}
			}
			else {
				am.setStartDate(new Date());
				am.setEndDate(GenericUtility.addYearsToDate(new Date(), 3));
			}
			//Updating Start And End Date
			update(am);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("certificate_number", certificateNumber);
			params.put("date_of_expiry", GenericUtility.sdf5.format(am.getEndDate()));
			params.put("date_of_registration", GenericUtility.sdf5.format(am.getApprovedDate()));
			params.put("barcode", barcode);
			params.put("user_id", am.getUser().getId());
			JasperService.addImage(params, "top_bottom_boder.png", "top_bottom_border");
			JasperService.addImage(params, "left_right_boder.png", "left_right_border");
			JasperService.addImage(params, "certificate_signature.png", "certificate_signature");
			JasperService.addImage(params, "corner_image.png", "corner_image");
			JasperService.addImage(params, "logo2.png", "logo");
			AttachmentBean beanAttachment=new AttachmentBean();
			ArrayList<AttachmentBean> attachmentBeans=new ArrayList<>();
			
			
			//Certificate Bites
			byte[] bites = JasperService.instance().convertJasperReportToByte("ModeratorCertificate.jasper", params);
			beanAttachment.setExt("pdf");
			beanAttachment.setFile(bites);
			beanAttachment.setFilename("ModeratorCertificate.pdf");
			attachmentBeans.add(beanAttachment);
			
			
			//Moderator: ETQ-TP-028 Moderator Letter  bites
			params = new HashMap<String, Object>();
			JasperService.addLogo(params);
			params.put("barcode",barcode);
			params.put("am_app_id",am.getId() );
			byte[] bites2 = JasperService.instance().convertJasperReportToByte("ETQ-TP-028-ModeratorCertificateLetter.jasper", params);
			beanAttachment=new AttachmentBean();
			beanAttachment.setExt("pdf");
			beanAttachment.setFile(bites2);
			beanAttachment.setFilename("ModeratorCertificateLetter.pdf");
			attachmentBeans.add(beanAttachment);
			

			Users user = new Users();
			List<Users> list=hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.Senior_Manager_Quality_Assurance_and_Partnerships_ID);
			if(list!=null && list.size()>0) {
				user = list.get(0) ;
			}
			params = new HashMap<String, Object>();
			JasperService.addLogo(params);
			params.put("assessorModeratorApplication",am);
			params.put("user",user);
			params.put("user_id",am.getUser().getId());
			params.put("registration_number",certificateNumber);
			params.put("status",am.getStatus().getFriendlyName());
			params.put("barcode",barcode);
			params.put("date", GenericUtility.sdf7.format(new Date()));
			ArrayList<QualificationUnitStandardBean> qualificationUnitStandardList=new ArrayList<>();
			//Creating QualificationUnitStandardBeanDataSource
			// version one
//			 List<UserQualifications> userQualifications=userQualificationsService.findByUserAMApplication(am.getUser().getId(), am.getId());
//			 List<UserUnitStandard> userUnitStandards=userUnitStandardService.findByUserAndAPApplication(am.getUser().getId(), am.getId());
			
			// version two
			 List<UserQualifications> userQualifications=userQualificationsService.findByUserAMApplicationAndAccept(am.getUser().getId(), am.getId(), true);
			 List<UserUnitStandard> userUnitStandards=userUnitStandardService.findByUserAndAPApplicationAndAccept(am.getUser().getId(), am.getId(), true);
			 for(UserQualifications qual:userQualifications){
				 qualificationUnitStandardList.add(new QualificationUnitStandardBean(String.valueOf(qual.getQualification().getCode()), qual.getQualification().getDescription(), GenericUtility.sdf7.format(am.getApprovedDate())));
			 }
			 
			 for(UserUnitStandard us:userUnitStandards){
				 qualificationUnitStandardList.add(new QualificationUnitStandardBean(String.valueOf(us.getUnitStandard().getCode()), us.getUnitStandard().getTitle(), GenericUtility.sdf7.format(am.getApprovedDate())));
			 }
			params.put("QualificationUnitStandardBeanDataSource", new JRBeanCollectionDataSource(qualificationUnitStandardList));
			byte[] bites3 = JasperService.instance().convertJasperReportToByte("ETQ-TP-011-StatementOfQualificationsandorUnitStandardsNew.jasper", params);
			beanAttachment=new AttachmentBean();
			beanAttachment.setExt("pdf");
			beanAttachment.setFile(bites3);
			beanAttachment.setFilename("Statement_Of_Qualifications_And_Or_UnitStandards.pdf");
			attachmentBeans.add(beanAttachment);
			
			
			String subject = "MODERATOR ACCREDITATION CERTIFICATE";
			String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " +am.getUser().getFirstName()+ " " + am.getUser().getLastName()+ ",</p>"

					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					
					+ "The merSETA would like to congratulate you for "
					+ "having successfully been registered as a Moderator "
					+ "as per the attached Statement of Results of "
					+ "the Qualification(s) and/or Unit Statement(s)."
				
					+ "</p>"
					
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"

					+ "You are requested to go through the merSETA Quality Assurance "
					+ "& Partnerships policies, particularly the registration of "
					+ "Moderators and the code of good conduct in the Moderator "
					+ "section to acclimatise yourself with them."
				
					+ "</p>"
					
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"

					+ "Looking forward to you having a "
					+ "fruitful relationship with merSETA. Enclosed is your certificate."
				
					+ "</p>"
                    
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">MerSETA Administration</p>";

			//GenericUtility.sendMailWithAttachement(am.getUser().getEmail(), subject, mssg, bites, "Moderator_Certificate.pdf", "pdf", null);
			GenericUtility.sendMailWithAttachementTempWithLog(am.getUser().getEmail(), subject, mssg, attachmentBeans, null,am.getClass().getName(),am.getId());
		
	}
	
	public void sendExtensionOfScopeNotification(AssessorModExtensionOfScope assessorModExtensionOfScope) throws Exception {
		AssessorModeratorApplication am=assessorModExtensionOfScope.getAssessorModeratorApplication();
		String barcode="";
		if(am.getUser().getRsaIDNumber() !=null && am.getUser().getRsaIDNumber().isEmpty()==false)
		{
			barcode=am.getUser().getRsaIDNumber();
		}
		else
		{
			barcode=am.getUser().getPassportNumber();
		}
		Map<String, Object> params = new HashMap<String, Object>();
		AttachmentBean beanAttachment=new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans=new ArrayList<>();
		String appTitle="";
		String aOrAn="";
		params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		params.put("barcode",barcode);
		params.put("am_app_id",am.getId() );
		if(am.getApplicationType()==AssessorModeratorAppTypeEnum.NewAssessorRegistration || 
		am.getApplicationType()==AssessorModeratorAppTypeEnum.AssessorExtensionOfScope   ||
		am.getApplicationType()==AssessorModeratorAppTypeEnum.AssessorReRegistration     )
		{
			appTitle="Assessor";
			aOrAn="an";
			byte[] bites2 = JasperService.instance().convertJasperReportToByte("ETQ-TP-027-AssessorCertificateLetter.jasper", params);
			beanAttachment=new AttachmentBean();
			beanAttachment.setExt("pdf");
			beanAttachment.setFile(bites2);
			beanAttachment.setFilename("AssessorCertificateLetter.pdf");
			attachmentBeans.add(beanAttachment);
		}
		else
		{
			appTitle="Moderator";
			aOrAn="a";
			byte[] bites2 = JasperService.instance().convertJasperReportToByte("ETQ-TP-028-ModeratorCertificateLetter.jasper", params);
			beanAttachment=new AttachmentBean();
			beanAttachment.setExt("pdf");
			beanAttachment.setFile(bites2);
			beanAttachment.setFilename("ModeratorCertificateLetter.pdf");
			attachmentBeans.add(beanAttachment);
		}
		
		Users user = new Users();
		List<Users> list=hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.Senior_Manager_Quality_Assurance_and_Partnerships_ID);
		if(list!=null && list.size()>0) {
			user = list.get(0) ;
		}
		params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		params.put("assessorModeratorApplication",am);
		params.put("user",user);
		params.put("user_id",am.getUser().getId());
		params.put("registration_number",am.getCertificateNumber());
		params.put("status",assessorModExtensionOfScope.getStatus().getFriendlyName());
		params.put("barcode",barcode);
		params.put("date", GenericUtility.sdf7.format(new Date()));
		ArrayList<QualificationUnitStandardBean> qualificationUnitStandardList=new ArrayList<>();
		//Creating QualificationUnitStandardBeanDataSource
		 List<UserQualifications> userQualifications = userQualificationsService.findByTargetKeyAndTargetClas(assessorModExtensionOfScope.getId(),AssessorModExtensionOfScope.class.getName());
		 List<UserUnitStandard> userUnitStandards = userUnitStandardService.findByTargetKeyAndTargetClas(assessorModExtensionOfScope.getId(),AssessorModExtensionOfScope.class.getName());
		 for(UserQualifications qual:userQualifications){
			 qualificationUnitStandardList.add(new QualificationUnitStandardBean(String.valueOf(qual.getQualification().getCode()), qual.getQualification().getDescription(), GenericUtility.sdf7.format(new Date())));
		 }
		 
		 for(UserUnitStandard us:userUnitStandards){
			 qualificationUnitStandardList.add(new QualificationUnitStandardBean(String.valueOf(us.getUnitStandard().getCode()), us.getUnitStandard().getTitle(), GenericUtility.sdf7.format(new Date())));
		 }
		params.put("QualificationUnitStandardBeanDataSource", new JRBeanCollectionDataSource(qualificationUnitStandardList));
		byte[] bites3 = JasperService.instance().convertJasperReportToByte("ETQ-TP-011-StatementOfQualificationsandorUnitStandardsNew.jasper", params);
		beanAttachment=new AttachmentBean();
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites3);
		beanAttachment.setFilename("Statement_Of_Qualifications_And_Or_UnitStandards.pdf");
		attachmentBeans.add(beanAttachment);
		
		
		String subject =appTitle.toUpperCase()+ " EXTENSION OF SCOPE OUTCOME";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " +am.getUser().getFirstName()+ " " + am.getUser().getLastName()+ ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				
				+ "The merSETA would like to congratulate you for "
				+ "having successfully been registered as "+aOrAn+" "+appTitle+" "
				+ "as per the attached Statement of Results of "
				+ "the Qualification(s) and/or Unit Statement(s)."
			
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"

				+ "You are requested to go through the merSETA Quality Assurance "
				+ "& Partnerships policies, particularly the registration of "
				+ ""+appTitle+" and the code of good conduct in the Moderator "
				+ "section to acclimatise yourself with them."
			
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"

				+ "Looking forward to you having a "
				+ "fruitful relationship with merSETA."
			
				+ "</p>"
                
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">MerSETA Administration</p>";

		GenericUtility.sendMailWithAttachementTempWithLog(am.getUser().getEmail(), subject, mssg, attachmentBeans, null,am.getClass().getName(),am.getId());
	
}
	

	public void downloadAssessorModCertificate(AssessorModeratorApplication am) throws Exception
	{
		if(am.getCertificateNumber()==null){
			throw new Exception("Certificate is not avaialable for this application");
		}
		String barcode="";
		if(am.getUser().getRsaIDNumber() !=null && am.getUser().getRsaIDNumber().isEmpty()==false){
			barcode=am.getUser().getRsaIDNumber();
		}
		else{
			barcode=am.getUser().getPassportNumber();
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("certificate_number", am.getCertificateNumber());
		params.put("date_of_expiry", GenericUtility.sdf5.format(am.getEndDate()));
		params.put("date_of_registration", GenericUtility.sdf5.format(am.getApprovedDate()));
		params.put("barcode",barcode);
		params.put("user_id", am.getUser().getId());
		JasperService.addImage(params, "top_bottom_boder.png", "top_bottom_border");
		JasperService.addImage(params, "left_right_boder.png", "left_right_border");
		JasperService.addImage(params, "certificate_signature.png", "certificate_signature");
		JasperService.addImage(params, "corner_image.png", "corner_image");
		JasperService.addImage(params, "logo2.png", "logo");
		
		if(am.getApplicationType()==AssessorModeratorAppTypeEnum.NewAssessorRegistration || 
		am.getApplicationType()==AssessorModeratorAppTypeEnum.AssessorExtensionOfScope   ||
		am.getApplicationType()==AssessorModeratorAppTypeEnum.AssessorReRegistration     )
		{
			JasperService.instance().createReportFromDBtoServletOutputStream("AssessorCertificate.jasper", params,barcode+"_Assessor_Certificate.pdf");
		}
		else
		{
			JasperService.instance().createReportFromDBtoServletOutputStream("ModeratorCertificate.jasper", params,barcode+"_Moderator_Certificate.pdf");
		}
	}
	
	public void downloadAssessorModCertificateLetter(AssessorModeratorApplication am) throws Exception
	{
		if(am.getCertificateNumber()==null){
			throw new Exception("Certificate is not avaialable for this application");
		}
		String barcode="";
		if(am.getUser().getRsaIDNumber() !=null && am.getUser().getRsaIDNumber().isEmpty()==false){
			barcode=am.getUser().getRsaIDNumber();
		}
		else{
			barcode=am.getUser().getPassportNumber();
		}
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		params.put("barcode",barcode);
		params.put("am_app_id", am.getId());
		JasperService.addImage(params, "certificate_signature.png", "certificate_signature");
		if(am.getApprovedDate() !=null){
			params.put("create_date",new SimpleDateFormat("dd MMMM yyyy").format(am.getApprovedDate()));
			params.put("create_date_time",new SimpleDateFormat("dd/MM/yyyy HH:mm").format(am.getApprovedDate()));
		}
		if(am.getApplicationType()==AssessorModeratorAppTypeEnum.NewAssessorRegistration || 
		am.getApplicationType()==AssessorModeratorAppTypeEnum.AssessorExtensionOfScope   ||
		am.getApplicationType()==AssessorModeratorAppTypeEnum.AssessorReRegistration     )
		{
			JasperService.instance().createReportFromDBtoServletOutputStream("ETQ-TP-027-AssessorCertificateLetter.jasper", params,barcode+"_Assessor_Certificate_Letter.pdf");
		}
		else
		{
			JasperService.instance().createReportFromDBtoServletOutputStream("ETQ-TP-028-ModeratorCertificateLetter.jasper", params,barcode+"_Moderator_Certificate_Letter.pdf");
		}
	}
	
	public void downloadStatementOfQualifications(AssessorModeratorApplication am) throws Exception
	{
		if(am.getCertificateNumber()==null){
			throw new Exception("Certificate is not avaialable for this application");
		}
		String barcode="";
		if(am.getUser().getRsaIDNumber() !=null && am.getUser().getRsaIDNumber().isEmpty()==false && am.getUser().getRsaIDNumber().isEmpty()==false){
			barcode=am.getUser().getRsaIDNumber();
		}
		else{
			barcode=am.getUser().getPassportNumber();
		}
		Users user = new Users();
		List<Users> list=hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.Senior_Manager_Quality_Assurance_and_Partnerships_ID);
		if(list!=null && list.size()>0) {
			user = list.get(0) ;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		params.put("assessorModeratorApplication",am);
		params.put("user",user);
		params.put("user_id",am.getUser().getId() );
		params.put("registration_number",am.getCertificateNumber());
		params.put("status",am.getStatus().getFriendlyName());
		params.put("barcode",barcode);
		params.put("date", GenericUtility.sdf7.format(am.getApprovedDate()));
		ArrayList<QualificationUnitStandardBean> qualificationUnitStandardList=new ArrayList<>();
		//Creating QualificationUnitStandardBeanDataSource
//		List<UserQualifications> userQualifications=userQualificationsService.findByUserAMApplication(am.getUser().getId(), am.getId());
		List<UserQualifications> userQualifications=userQualificationsService.findByUserAMApplicationAndAccept(am.getUser().getId(), am.getId(), true);
//		List<UserUnitStandard> userUnitStandards=userUnitStandardService.findByUserAndAPApplication(am.getUser().getId(), am.getId());
		List<UserUnitStandard> userUnitStandards=userUnitStandardService.findByUserAndAPApplicationAndAccept(am.getUser().getId(), am.getId(), true);
		for(UserQualifications qual:userQualifications){
			qualificationUnitStandardList.add(new QualificationUnitStandardBean(String.valueOf(qual.getQualification().getCode()), qual.getQualification().getDescription(), GenericUtility.sdf7.format(am.getApprovedDate())));
		} 
		for(UserUnitStandard us:userUnitStandards){
			qualificationUnitStandardList.add(new QualificationUnitStandardBean(String.valueOf(us.getUnitStandard().getCode()), us.getUnitStandard().getTitle(), GenericUtility.sdf7.format(am.getApprovedDate())));
		}
		params.put("QualificationUnitStandardBeanDataSource", new JRBeanCollectionDataSource(qualificationUnitStandardList));
	    JasperService.instance().createReportFromDBtoServletOutputStream("ETQ-TP-011-StatementOfQualificationsandorUnitStandardsNew.jasper", params,barcode+"_Statement_Of_Qualifications_And_Or_UnitStandards.pdf");
			
	}
	
	
	/*
	 *  e.g. 17-QA/ACC/0767/18
		17 = merSETA
		QA = Quality Assurance (i.e. Quality Assurance Unit)
		ASS = Assessor (appears when it is an Assessor accreditation)
		MOD = Moderator (appears when it is a Moderator accreditation)
		0767 = number sequence (numbering continues from the latest number)
		18 = the year of accreditation
	 * */
	public String  getModeratorTrainingProviderCerticateNumber(AssessorModeratorApplication am,TrainingProviderApplication tpApp) throws Exception
	{
		//int year = Calendar.getInstance().get(Calendar.YEAR);
		SimpleDateFormat sdf=new SimpleDateFormat("yy");
		String year=sdf.format(new Date());
		String certificateNum="";
		if(am !=null)
		{
			/**Generating New Certificate Number*/
			if(am.getCertificateNumber()==null || !am.getCertificateNumber().contains("17-QA/MOD/")){
				certificateNum="17-QA/MOD/"+getNumberSequence(am.getApplicationType(),null)+"/"+year;
			}
			else{
				/**For First time Legacy SDP  application*/
				if((am.getLegacyAssessorAccreditation() !=null || am.getLegacyModeratorAccreditation() !=null) && (am.getFinalApproved()==null || !am.getFinalApproved())){
					certificateNum=am.getCertificateNumber();
				}
				else {
					/**Updating the year of Certificate Number*/
					//17-QA/MOD/0767/18
					String[] element=am.getCertificateNumber().split("/");
					if(element.length>2){
						certificateNum=element[0]+"/"+element[1]+"/"+element[2]+"/"+year;
					}
					else{
						certificateNum=am.getCertificateNumber();
					}
				}
				
			}
		}
		else if(tpApp !=null){
			/**Generating New Certificate Number*/
			if(tpApp.getCertificateNumber()==null || !tpApp.getCertificateNumber().contains("17-QA/ACC/")){
				certificateNum="17-QA/ACC/"+getNumberSequence(null,tpApp.getAccreditationApplicationTypeEnum())+"/"+year;
			}
			/**Updating the year of Certificate Number*/
			else{
				//17-QA/ACC/0767/18
				String[] element=tpApp.getCertificateNumber().split("/");
				if(element.length>2){
					certificateNum=element[0]+"/"+element[1]+"/"+element[2]+"/"+year;
				}
				else{
					certificateNum=tpApp.getCertificateNumber();
				}
				
			}
		}
	    
		return certificateNum;
	}
	
	public String  getAssessorTrainingProviderCertificateNum(AssessorModeratorApplication am,TrainingProviderApplication tpApp) throws Exception {
		//int year = Calendar.getInstance().get(Calendar.YEAR);
		SimpleDateFormat sdf=new SimpleDateFormat("yy");
		String year=sdf.format(new Date());
		String certificateNum="";
		if(am !=null) {
			/**Generating New Certificate Number*/
			if(am.getCertificateNumber()==null || !am.getCertificateNumber().contains("17-QA/ASS/")) {
				certificateNum="17-QA/ASS/" + getNumberSequence(am.getApplicationType(),null) + "/" + year;
			} else {
				// version two
				/**For First time Legacy am  application*/
				/**Updating the year of Certificate Number*/
				//17-QA/ASS/0767/18
				String[] element= am.getCertificateNumber().split("/");
				if(element.length > 2){
					certificateNum=element[0]+"/"+element[1]+"/"+element[2]+"/"+year;
				} else {
					certificateNum=am.getCertificateNumber();
				}

				// version one
//				/**For First time Legacy am  application*/
//				if((am.getLegacyAssessorAccreditation() !=null || am.getLegacyModeratorAccreditation() !=null) && (am.getFinalApproved()==null || !am.getFinalApproved())){
//					certificateNum=am.getCertificateNumber();
//				} else {
//					/**Updating the year of Certificate Number*/
//					//17-QA/ASS/0767/18
//					String[] element=am.getCertificateNumber().split("/");
//					if(element.length>2){
//						certificateNum=element[0]+"/"+element[1]+"/"+element[2]+"/"+year;
//					} else {
//						certificateNum=am.getCertificateNumber();
//					}
//				}	
			}
		} else if(tpApp !=null) {
			/**Generating New Certificate Number*/
			if(tpApp.getCertificateNumber()==null || !tpApp.getCertificateNumber().contains("17-QA/ACC/")){
				certificateNum="17-QA/ACC/"+getNumberSequence(null,tpApp.getAccreditationApplicationTypeEnum())+"/"+year;
			} else{
				/**Updating the year of Certificate Number*/
				//17-QA/ACC/0767/18
				String[] element=tpApp.getCertificateNumber().split("/");
				if(element.length>2){
					certificateNum=element[0]+"/"+element[1]+"/"+element[2]+"/"+year;
				} else{
					certificateNum=tpApp.getCertificateNumber();
				}
			}
		}
		return certificateNum;
	}
	
	public String getNumberSequence(AssessorModeratorAppTypeEnum amAppType,AccreditationApplicationTypeEnum tpAppType) throws Exception{
		TrainingProviderApplicationService trainingProviderApplicationService=new TrainingProviderApplicationService();
	    int number =0; 
		if(amAppType !=null){
			if(amAppType ==AssessorModeratorAppTypeEnum.AssessorExtensionOfScope ||
			amAppType ==AssessorModeratorAppTypeEnum.AssessorReRegistration ||
			amAppType==AssessorModeratorAppTypeEnum.NewAssessorRegistration){
				number=7800 + (dao.countAllApprovedApplication(AssessorModeratorAppTypeEnum.NewAssessorRegistration)); 
			}
			else
			{
				number=3000 + (dao.countAllApprovedApplication(AssessorModeratorAppTypeEnum.NewModeratorRegistration)); 
			}
		}
		else if(tpAppType !=null){
			number=1000 + (trainingProviderApplicationService.countAllApprovedApplication()); 
		}
		
		return String.valueOf(number);
	}

	/**
	 * Pre register checks.
	 *
	 * @param entity
	 *            the entity
	 * @param formUser
	 *            the form user
	 * @throws Exception
	 *             the exception
	 */
	private void preRegisterChecks(List<Company> entity, Users formUser) throws Exception {
		boolean error = false;
		String err = "";
		if (formUser != null && StringUtils.isBlank(formUser.getEmail())) {
			error = true;
			err = err + "Enter your email address!<br/>";
		}
		for (Company company : entity) {
			if (company.getDocs() != null) {
				for (Doc doc : company.getDocs()) {
					if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
						error = true;
						err = err + "Upload <i>" + doc.getConfigDoc().getName() + "</i> for <i>" + company.getCompanyName() + "</i><br/>";
					}
				}
			}
		}
		if (error) throw new Exception(err);
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
				if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
					error = true;
					err = err + "Upload <i>" + doc.getConfigDoc().getName() + "</i> for <i>" + formUser.getFirstName() + " " + formUser.getLastName() + "</i><br/>";
				}
			}
		}
		if (error) throw new Exception(err);
	}

	/**
	 * Adds link to unit standards and qualifications for a company or assessor and
	 * moderator.
	 * 
	 * Based on config will link the appropriate items
	 *
	 * @param unitStandards
	 *            the unit standards
	 * @param qualifications
	 *            the qualifications
	 * @param formUser
	 *            the form user
	 * @param company
	 *            the company
	 * @param configDocProcessEnum
	 *            the config doc process enum
	 * @throws Exception
	 *             the exception
	 */
	private void linkUserOrCompanyToQualificationUnitStandards(List<IDataEntity> dataEntities, List<UnitStandards> unitStandards, List<Qualification> qualifications, Users formUser, Company company, ConfigDocProcessEnum configDocProcessEnum, AssessorModeratorApplication amApplication) throws Exception {
		// assigns qualifications
		for (Qualification qualification : qualifications) {
			// if assessor moderator will assign to user else to company
			if (configDocProcessEnum == ConfigDocProcessEnum.AM || configDocProcessEnum == ConfigDocProcessEnum.LEGACY_AM_APPLICATION) {
				UserQualifications userQualifications = new UserQualifications(formUser, qualification);
				userQualifications.setForAssessorModeratorApplication(amApplication);
				dataEntities.add(userQualifications);
			} else {
				CompanyQualifications companyQualifications = new CompanyQualifications(company, qualification);
				dataEntities.add(companyQualifications);
			}
		}
		// assigns unit standards
		for (UnitStandards unitStandard : unitStandards) {
			// if assessor moderator will assign to user else to company
			if (configDocProcessEnum == ConfigDocProcessEnum.AM || configDocProcessEnum == ConfigDocProcessEnum.LEGACY_AM_APPLICATION) {
				UserUnitStandard userUnitStandard = new UserUnitStandard(formUser, unitStandard);
				if(unitStandard.getQualification() !=null){
					userUnitStandard.setForQualification(unitStandard.getQualification());
				}
				userUnitStandard.setForAssessorModeratorApplication(amApplication);
				dataEntities.add(userUnitStandard);
			} else {
				CompanyUnitStandard companyUnitStandard = new CompanyUnitStandard(company, unitStandard);
				dataEntities.add(companyUnitStandard);
			}
		}

		// creates batch
	}

	/**
	 * Completes task to first process
	 * 
	 * @param entity
	 * @param formUser
	 * @param task
	 * @throws Exception
	 */
	public void completeToFirst(AssessorModeratorApplication entity, Users formUser, Tasks task) throws Exception {
		update(entity);
		String desc = task.getDescription();
		TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, task.getTargetKey(), task.getTargetClass(), true, ConfigDocProcessEnum.AM, null, null);
		TasksService.instance().completeTask(task);
	}

	/**
	 * Completes task
	 * 
	 * @param entity
	 * @param user
	 * @param tasks
	 * @throws Exception
	 */
	public void completeLearnership(AssessorModeratorApplication entity, Users user, Tasks tasks) throws Exception {
		update(entity);
		// uploadDocuments(entity, tasks, user);
		TasksService.instance().findNextInProcessAndCreateTask("", user, entity.getId(), AssessorModeratorApplication.class.getName(), true, tasks, null, null);
	}

	/**
	 * Final Approves Assessor Moderator Application and closes task
	 * 
	 * @param entity
	 * @param users
	 * @param tasks
	 * @throws Exception
	 */
	public void finalApproveAM(AssessorModeratorApplication entity, Users users, Tasks tasks) throws Exception {
		entity.setStatus(ApprovalEnum.Approved);
		update(entity);
		TasksService.instance().findNextInProcessAndCreateTask("", users, entity.getId(), AssessorModeratorApplication.class.getName(), true, tasks, null, null);
	}

	/**
	 * Rejects rejectAMRegistration and sends it to the previous process
	 * 
	 * @param entity
	 * @param user
	 * @param task
	 * @throws Exception
	 */
	public void rejectAMRegistration(AssessorModeratorApplication entity, Users user, Tasks task) throws Exception {
		update(entity);
		if (task.getFirstInProcess()) {
			List<Users> signOffs = new ArrayList<>();
			signOffs.add(entity.getUser());
			TasksService.instance().createTaskUser(signOffs, task.getTargetClass(), task.getTargetKey(), task.getDescription(), user, true, true, task, ConfigDocProcessEnum.AM, false);
			TasksService.instance().completeTask(task);
		} else {
			entity.setStatus(ApprovalEnum.Rejected);
			TasksService.instance().findPreviousInProcessAndCreateTask("", user, entity.getId(), AssessorModeratorApplication.class.getName(), true, task, null, null);
		}
	}

	/**
	 * Rejects Assessor Moderator Application and closes task
	 * 
	 * @param amApplication
	 * @param user
	 * @param tasks
	 * @throws Exception
	 */
	public void finalRejectAmRegistration(AssessorModeratorApplication amApplication, Users user, Tasks tasks,ArrayList<RejectReasons> selectedRejectReason,String additionalInformation) throws Exception {
		amApplication.setStatus(ApprovalEnum.Rejected);
		update(amApplication);
		TasksService.instance().completeTask(tasks);
		// Creating RejectReason
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(amApplication.getId(), AssessorModeratorApplication.class.getName(), selectedRejectReason, user, ConfigDocProcessEnum.AM);
		for(RejectReasonMultipleSelection rrm:rrmList)
		{
			if(rrm !=null)
			{
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
			
		}
		emailRejectedApplicant(amApplication, selectedRejectReason, additionalInformation);
	}

	/**
	 * Email Applicant upon AssessorModeratorApplication rejection
	 * 
	 * @param entity
	 * @param selectedRejectReason
	 * @param additionalInformation
	 * @throws Exception
	 */
	public void emailRejectedApplicant(AssessorModeratorApplication entity, List<RejectReasons> selectedRejectReason, String additionalInformation) throws Exception {
		String desc = "Assessor moderator application for " + entity.getUser().getFirstName() + " " + entity.getUser().getLastName() + " was rejected.";
		String subject = "Assessor Moderator Application Rejection";

		if (selectedRejectReason != null) {
			desc += "<br/>Reason/s: ";

			for (RejectReasons rejectReasons : selectedRejectReason) {
				desc += rejectReasons.getDescription();
			}
		}

		if (!additionalInformation.isEmpty()) {
			desc += "<br/>Additional Information: ";
			desc += "<br/>" + additionalInformation;
		}
		GenericUtility.sendMail(entity.getUser().getEmail(), subject, desc, null);
	}
	
	/**
	 * Reject task for assessor and Moderator.
	 * If not approved by ETQA Review Committee, 
	 * application Senior Manager to reject
	 * application and must go back to 
	 * applicant to address issues identified
	 */
	public void finalRejecTask(Users assessorModerator, Tasks task, Users user, List<AssessorModeratorCompany> assessorModeratorCompaniesList, AssessorModeratorApplication amApplication,ArrayList<RejectReasons> rejectReasonsList) throws Exception {
		List<IDataEntity> iDataEntities = new ArrayList<>();
		amApplication.setStatus(ApprovalEnum.Rejected);
		Long targetKey=amApplication.getId();
		String targetClass=amApplication.getClass().getName();
		ConfigDocProcessEnum configDocProcessEnum=ConfigDocProcessEnum.AM;
		if(amApplication.getAssessorModReRegistration() !=null){
			
			targetKey=amApplication.getAssessorModReRegistration().getId();
			targetClass=amApplication.getAssessorModReRegistration().getClass().getName();
			configDocProcessEnum=ConfigDocProcessEnum.AM_RE_REGISTRATION;
			amApplication.getAssessorModReRegistration().setStatus(ApprovalEnum.Rejected);
			iDataEntities.add(amApplication.getAssessorModReRegistration());
		}
		if (task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalApproval) {
			assessorModerator.setActive(true);
			amApplication.setFinalRejected(true);
		}
		iDataEntities.add(assessorModerator);
		iDataEntities.add(amApplication);
		// Creating RejectReason
		/*List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(targetKey, targetClass, rejectReasonsList, user, configDocProcessEnum);
		for(RejectReasonMultipleSelection rrm:rrmList){
			if(rrm !=null)
			{
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}*/
		
		if(amApplication.getAssessorModReRegistration() !=null){
			amApplication.setApplicationType(amApplication.getAssessorModReRegistration().getPreviousApplicationType());
		}
		dao.updateBatch(iDataEntities);
		TasksService.instance().completeTask(task);
		
		sendSeniorManagerRejectionEmail(amApplication, amApplication.getUser(), rejectReasonsList);
	}
	
	
	public void finalRejecApplication(Users assessorModerator, Tasks task, Users user, List<AssessorModeratorCompany> assessorModeratorCompaniesList, AssessorModeratorApplication amApplication,ArrayList<RejectReasons> rejectReasonsList) throws Exception {
		List<IDataEntity> iDataEntities = new ArrayList<>();
		amApplication.setStatus(ApprovalEnum.Rejected);
		Long targetKey=amApplication.getId();
		String targetClass=amApplication.getClass().getName();
		ConfigDocProcessEnum configDocProcessEnum=ConfigDocProcessEnum.AM;
		if(amApplication.getAssessorModReRegistration() !=null){
			
			targetKey=amApplication.getAssessorModReRegistration().getId();
			targetClass=amApplication.getAssessorModReRegistration().getClass().getName();
			configDocProcessEnum=ConfigDocProcessEnum.AM_RE_REGISTRATION;
			amApplication.getAssessorModReRegistration().setStatus(ApprovalEnum.Rejected);
			iDataEntities.add(amApplication.getAssessorModReRegistration());
		}
		if (task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalApproval) {
			assessorModerator.setActive(true);
			amApplication.setFinalRejected(true);
		}
		iDataEntities.add(assessorModerator);
		iDataEntities.add(amApplication);
		// Creating RejectReason
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(targetKey, targetClass, rejectReasonsList, user, configDocProcessEnum);
		for(RejectReasonMultipleSelection rrm:rrmList){
			if(rrm !=null)
			{
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		
		if(amApplication.getAssessorModReRegistration() !=null){
			amApplication.setApplicationType(amApplication.getAssessorModReRegistration().getPreviousApplicationType());
		}
		dao.updateBatch(iDataEntities);
		TasksService.instance().completeTask(task);
		
		sendfinalRejecApplicationEmail(amApplication, amApplication.getUser(), rejectReasonsList);
	}
	/**
	 * Find AssessorModeratorApplication by status
 	 * @author TechFinium 
 	 * @param ApprovalEnum the status 
 	 * @see    AssessorModeratorApplication
  	 * @return a list of {@link haj.com.entity.AssessorModeratorApplication}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> findByStatus(ApprovalEnum status) throws Exception {
	 	
		return dao.findByStatus(status);
	}
	
	/**
	 * Find object by primary user ID and accreditation number
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    AssessorModeratorApplication
 	 * @return a {@link haj.com.entity.AssessorModeratorApplication}
 	 * @throws Exception global exception
 	 */
	public AssessorModeratorApplication findByAccreditation(String accreditation,Long userID) throws Exception {
	 	return dao.findByAccreditation(accreditation, userID);
	}
	
	/**
	 * Reject task for assessor and Moderator.
	 */
	public void rejectAMTask(Users assessorModerator, Tasks task, Users user, AssessorModeratorApplication amApplication,ArrayList<RejectReasons> rejectReasonsList,String additionalInformation) throws Exception {
		List<IDataEntity> iDataEntities = new ArrayList<>();
		amApplication.setStatus(ApprovalEnum.Rejected);
		Long targetKey=amApplication.getId();
		String targetClass=amApplication.getClass().getName();
		ConfigDocProcessEnum configDocProcessEnum=ConfigDocProcessEnum.AM;
		if(amApplication.getLegacyAssessorAccreditation() !=null){
			configDocProcessEnum=ConfigDocProcessEnum.LEGACY_AM_APPLICATION;
		}
		if(amApplication.getAssessorModReRegistration() !=null){
			amApplication.getAssessorModReRegistration().setStatus(amApplication.getStatus());
			iDataEntities.add(amApplication.getAssessorModReRegistration());
			targetKey=amApplication.getAssessorModReRegistration().getId();
			targetClass=amApplication.getAssessorModReRegistration().getClass().getName();
			configDocProcessEnum=ConfigDocProcessEnum.AM_RE_REGISTRATION;
		}
		if (task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalApproval) {
			assessorModerator.setActive(true);
		}
		iDataEntities.add(assessorModerator);
		iDataEntities.add(amApplication);
		// Creating RejectReason
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(targetKey,targetClass, rejectReasonsList, user,configDocProcessEnum);
		for(RejectReasonMultipleSelection rrm:rrmList){
			if(rrm !=null){
				rrm.setAdditionalInformation(additionalInformation);
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		dao.updateBatch(iDataEntities);
		if (task.getFirstInProcess()) {
			List<Users> userList = new ArrayList<>();
			userList.add(assessorModerator);
			String desc= "Your "+amApplication.getApplicationType().getDisplayName()+" application was rejected please login and view the reason and make the relevant changes.";
			TasksService.instance().createTaskUser(userList, task.getTargetClass(), task.getTargetKey(),desc, user, true, true, task,configDocProcessEnum, false);
		} else {
			TasksService.instance().findPreviousInProcessAndCreateTask("", user, targetKey, targetClass, true, task, null, null);
		}
		
	}
	
	public void resubmitAMApplication(Users assessorModerator, AssessorModeratorApplication amApplication, Tasks task) throws Exception
	{
		List<IDataEntity> iDataEntities = new ArrayList<>();
		if(amApplication.getAssessorModReRegistration()==null)
		{
			if(amApplication.getLegacyAssessorAccreditation()==null && amApplication.getLegacyModeratorAccreditation()==null)
			{
				String desc = AbstractUI.getEntryLanguage(ConfigDocProcessEnum.AM.getTaskDescription());
				TasksService.instance().findFirstInProcessAndCreateTask(desc, assessorModerator, amApplication.getId(), amApplication.getClass().getName(), true, ConfigDocProcessEnum.AM, MailDef.instance(MailEnum.AMForApproval, MailTagsEnum.FirstName, assessorModerator.getFirstName(), MailTagsEnum.LastName, assessorModerator.getLastName(), MailTagsEnum.Task, ConfigDocProcessEnum.AM.getTaskDescription()), null);
			}
			else{
				String desc = AbstractUI.getEntryLanguage(ConfigDocProcessEnum.LEGACY_AM_APPLICATION.getTaskDescription());
				TasksService.instance().findFirstInProcessAndCreateTask(desc, assessorModerator, amApplication.getId(), amApplication.getClass().getName(), true, ConfigDocProcessEnum.LEGACY_AM_APPLICATION, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, assessorModerator.getFirstName(), MailTagsEnum.LastName, assessorModerator.getLastName(), MailTagsEnum.Task, ConfigDocProcessEnum.LEGACY_AM_APPLICATION.getTaskDescription()), null);
			}
		}
		else{
			amApplication.getAssessorModReRegistration().setStatus(ApprovalEnum.PendingApproval);
			iDataEntities.add(amApplication.getAssessorModReRegistration());
			String desc = AbstractUI.getEntryLanguage(ConfigDocProcessEnum.AM_RE_REGISTRATION.getTaskDescription());
			TasksService.instance().findFirstInProcessAndCreateTask(desc, assessorModerator, amApplication.getAssessorModReRegistration().getId(), amApplication.getAssessorModReRegistration().getClass().getName(), true, ConfigDocProcessEnum.AM_RE_REGISTRATION, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, assessorModerator.getFirstName(), MailTagsEnum.LastName, assessorModerator.getLastName(), MailTagsEnum.Task, ConfigDocProcessEnum.AM_RE_REGISTRATION.getTaskDescription()), null);
		}
		TasksService.instance().completeTask(task);
		amApplication.setStatus(ApprovalEnum.PendingApproval);
		iDataEntities.add(amApplication);
		dao.updateBatch(iDataEntities);
	}
	
	public void sendNotification(ArrayList<RejectReasons> selectedRejectReason,Users assessorModerator,AssessorModeratorApplication amApplication)
	{
		String reason="<ul style=\"font-size:11.0pt;\";font-family:\"Arial\">";
		for(RejectReasons theReason:selectedRejectReason)
		{
			reason +="<li>"+theReason.getDescription()+"</li>";
		}
		reason +="</ul>";
		
		String appType="";
		if(amApplication.getApplicationType() ==AssessorModeratorAppTypeEnum.NewAssessorRegistration){
			appType="New Assessor";
		}
		else{
			appType="New Moderator";
		}
		
		String subject = ""+appType.toUpperCase()+" Application rejection".toUpperCase();
		String mssg = "<br/>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " +assessorModerator.getFirstName() + " " +assessorModerator.getLastName()+ ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Your "+appType+" application has been rejected because of the following reason(s):<br/>"
				+  reason
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">merSETA Administration</p>";
		GenericUtility.sendMail(assessorModerator.getEmail(), subject, mssg, null);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> sortAndFilterWhereAMInfoApprovedAndByApplicationType(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, List<AssessorModeratorAppTypeEnum> applicationTypeList) throws Exception {
		String hql = "select o from AssessorModeratorApplication o where o.finalApproved = :booleanValue ";
		filters.put("booleanValue", true);
		if (!applicationTypeList.isEmpty()) {
			int counter = 1;
			hql += " and (  ";
			for (AssessorModeratorAppTypeEnum assessorModeratorAppTypeEnum : applicationTypeList) {
				if (counter == applicationTypeList.size()) {
					hql += " o.applicationType = :applicationType"+counter;
				} else {
					hql += " o.applicationType = :applicationType"+counter+" or ";
				}
				filters.put("applicationType" + counter, assessorModeratorAppTypeEnum);
				counter++;
			}
			hql += ") ";
		}
		return resolveUserDocs((List<AssessorModeratorApplication>) dao.sortAndFilterWhereAMInfo(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countWhereAMInfoApprovedAndByApplicationType(Map<String, Object> filters, List<AssessorModeratorAppTypeEnum> applicationTypeList) throws Exception {
		String hql = "select count(o) from AssessorModeratorApplication o where o.finalApproved = :booleanValue ";
		if (!applicationTypeList.isEmpty()) {
			int counter = 1;
			hql += " and (  ";
			for (AssessorModeratorAppTypeEnum assessorModeratorAppTypeEnum : applicationTypeList) {
				if (counter == applicationTypeList.size()) {
					hql += " o.applicationType = :applicationType"+counter;
				} else {
					hql += " o.applicationType = :applicationType"+counter+" or ";
				}
				counter++;
			}
			hql += " ) ";
		}
		return dao.countWhereAMInfo(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> sortAndFilterWhereAMInfoApproved(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from AssessorModeratorApplication o where o.finalApproved = :booleanValue ";
		filters.put("booleanValue", true);
		return resolveUserDocs((List<AssessorModeratorApplication>) dao.sortAndFilterWhereAMInfo(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countWhereAMInfoApproved(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from AssessorModeratorApplication o where o.finalApproved = :booleanValue ";
		return dao.countWhereAMInfo(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> sortAndFilterWhereAMInfoApplicationType(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, List<AssessorModeratorAppTypeEnum> applicationTypeList) throws Exception {
		String hql = "select o from AssessorModeratorApplication o where o.id is not null ";
		if (!applicationTypeList.isEmpty()) {
			int counter = 1;
			hql += " and (  ";
			for (AssessorModeratorAppTypeEnum assessorModeratorAppTypeEnum : applicationTypeList) {
				if (counter == applicationTypeList.size()) {
					hql += " o.applicationType = :applicationType"+counter;
				} else {
					hql += " o.applicationType = :applicationType"+counter+" or ";
				}
				filters.put("applicationType" + counter, assessorModeratorAppTypeEnum);
				counter++;
			}
			hql += ") ";
		}
		return resolveUserDocs((List<AssessorModeratorApplication>) dao.sortAndFilterWhereAMInfo(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countWhereAMInfoApplicationType(Map<String, Object> filters,List<AssessorModeratorAppTypeEnum> applicationTypeList) throws Exception {
		String hql = "select count(o) from AssessorModeratorApplication o where o.id is not null ";
		if (!applicationTypeList.isEmpty()) {
			int counter = 1;
			hql += " and (  ";
			for (AssessorModeratorAppTypeEnum assessorModeratorAppTypeEnum : applicationTypeList) {
				if (counter == applicationTypeList.size()) {
					hql += " o.applicationType = :applicationType"+counter;
				} else {
					hql += " o.applicationType = :applicationType"+counter+" or ";
				}
				counter++;
			}
			hql += ") ";
		}
		return dao.countWhereAMInfo(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> sortAndFilterWhereAMInfo(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from AssessorModeratorApplication o where o.id is not null ";
		return resolveUserDocs((List<AssessorModeratorApplication>) dao.sortAndFilterWhereAMInfo(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countWhereAMInfo(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from AssessorModeratorApplication o where o.id is not null ";
		return dao.countWhereAMInfo(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> allAssessorModeratorApplicationByType(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, AssessorModeratorTypeEnum assessorModeratorType) throws Exception {
		String hql = "select o from AssessorModeratorApplication o where o.assessorModeratorType = :assessorModeratorType ";
		filters.put("assessorModeratorType", assessorModeratorType);
		return resolveUserDocs((List<AssessorModeratorApplication>) dao.sortAndFilterWhereAMInfo(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countAllAssessorModeratorApplicationByType(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from AssessorModeratorApplication o where o.assessorModeratorType = :assessorModeratorType ";
		return dao.countWhereAMInfo(filters, hql);
	}
	

	
	/**
	 * Resolve docs.
	 *
	 * @param amc the amc
	 * @return the list
	 * @throws Exception the exception
	 */
	private List<AssessorModeratorApplication> resolveUserDocs(List<AssessorModeratorApplication> amc) throws Exception {
		amc.forEach(a -> {
			try {
				a.getUser().setDocs(docService.searchByUser(a.getUser()));
				if(a.getUser().getDocs() !=null && a.getUser().getDocs().size()>0)
				{
					ArrayList<Doc> docsForCurrentApplication=new ArrayList<>();
					for(Doc doc:a.getUser().getDocs())
					{
						if(doc.getTargetKey()!=null && doc.getTargetKey().equals(a.getId()) && doc.getTargetClass().equals(a.getClass().getName()))
						{
							docsForCurrentApplication.add(doc);
						}
						
					}
					a.getUser().setDocs(docsForCurrentApplication);
				}
				//companyService.prepareDocs(ConfigDocProcessEnum.AM, a.getCompany(), CompanyUserTypeEnum.Company);
			} catch (Exception e) {
				logger.fatal(e);
			}
		});
		
		return amc;
	}
	
	/**
	 * Find Active AssessorModeratorApplication for user
 	 * @author TechFinium 
 	 * @param Users the Users 
 	 * @see    AssessorModeratorApplication
  	 * @return a list of {@link haj.com.entity.AssessorModeratorApplication}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> findByUserForValidation(Users user) throws Exception {
		return dao.findByUserForValidation(user);
	}
	
	public List<AssessorModeratorApplication> findByUser(Long userId)throws Exception {	
		return dao.findByUser(userId);
	}
	
	public List<AssessorModeratorApplication> findByUser(Long userId,Boolean finalRejected)throws Exception {	
		return dao.findByUser(userId,finalRejected);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> findByApprovedUserApplications(Users user) throws Exception {
		return dao.findByApprovedUserApplications(user);
	}
	
	public List<AssessorModeratorApplication> findApplicationByUserAndType(Users user, AssessorModeratorTypeEnum assessorModeratorType) throws Exception {
		return dao.findApplicationByUserAndType(user, assessorModeratorType);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> findByApprovedUserByType(Users user,AssessorModeratorAppTypeEnum applicationType) throws Exception {
		return dao.findByApprovedUserByType(user, applicationType);
	}

	public void approveExtensionOfScope(AssessorModExtensionOfScope assessorModExtensionOfScope,
		List<UserQualifications> userQualifications, List<UserUnitStandard> userUnitStandards, AssessorModeratorApplication amApplication, Tasks tasks, Users user) throws Exception {
		
		List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
		assessorModExtensionOfScope.setStatus(ApprovalEnum.Approved);
		assessorModExtensionOfScope.setApprovedDate(new Date());
		amApplication.setStatus(ApprovalEnum.Approved);
		dataEntities.add(assessorModExtensionOfScope);
		dataEntities.add(amApplication);
		for (UserQualifications userQualification : userQualifications) {
			if(userQualification.getAccept() !=null && userQualification.getAccept()==true)
			{
				userQualification.setForAssessorModeratorApplication(amApplication);
				userQualification.setUser(assessorModExtensionOfScope.getCreateUser());
				dataEntities.add(userQualification);
			}
		}
		// assigns unit standards
		for (UserUnitStandard unitStandard : userUnitStandards) {
			if(unitStandard.getAccept() !=null && unitStandard.getAccept()==true)
			{
				unitStandard.setForAssessorModeratorApplication(amApplication);
				unitStandard.setUser(assessorModExtensionOfScope.getCreateUser());
				dataEntities.add(unitStandard);
			}
		}
		dao.updateBatch(dataEntities);
		String desc = AbstractUI.getEntryLanguage(ConfigDocProcessEnum.AM_EXTENSION_OF_SCOPE.getTaskDescription());
		TasksService.instance().findNextInProcessAndCreateTask(desc, user, assessorModExtensionOfScope.getId(), AssessorModExtensionOfScope.class.getName(), true, tasks, null, null);
		//Send Email
		sendExtensionOfScopeNotification(assessorModExtensionOfScope);
		
	}

	public void approveExtensionOfScopeTask(AssessorModExtensionOfScope assessorModExtensionOfScope,
			AssessorModeratorApplication amApplication,Tasks task,Users user) throws Exception {
		
		assignMeetingAgender(assessorModExtensionOfScope, user);
		if(task.getProcessRole().getRolePermission()==UserPermissionEnum.FinalApproval)
		{
			assessorModExtensionOfScope.setStatus(ApprovalEnum.PendingCommitteeApproval);
		}
		else
		{
			assessorModExtensionOfScope.setStatus(ApprovalEnum.PendingApproval);
		}
		
		AssessorModExtensionOfScopeService service= new AssessorModExtensionOfScopeService();
		service.update(assessorModExtensionOfScope);
		update(amApplication);
		String desc = AbstractUI.getEntryLanguage(ConfigDocProcessEnum.AM_EXTENSION_OF_SCOPE.getTaskDescription());
		TasksService.instance().findNextInProcessAndCreateTask(desc, user, assessorModExtensionOfScope.getId(), AssessorModExtensionOfScope.class.getName(), true, task, null, null);
	
		
	}
	
	public void assignMeetingAgender(AssessorModExtensionOfScope amApplication,Users user) throws Exception
	{
		ReviewCommitteeMeetingAgendaService reviewCommitteeMeetingAgendaSevice=new ReviewCommitteeMeetingAgendaService();
		ScheduledEventService scheduledEventService=new ScheduledEventService();
		ReviewCommitteeMeetingUsersService reviewCommitteeMeetingUsersService=new ReviewCommitteeMeetingUsersService();
		if(amApplication.getReviewCommitteeMeeting() !=null)
		{
			Long meetingAgendaID=3L;//Assessor/Moderator Registration Approval ID
			ReviewCommitteeMeetingAgenda meetingAgender=reviewCommitteeMeetingAgendaSevice.findByMeetingAgendaAndReviewCommitteeMeeting(meetingAgendaID, amApplication.getReviewCommitteeMeeting().getId());
			if(meetingAgender !=null)
			{
				amApplication.setReviewCommitteeMeetingAgenda(meetingAgender);
			}
			else
			{
				throw new Exception("Please add Assessor/Moderator Registration Approval to the agenda of the selected Review Committee meeting");
			}
			//Adding meeting details to Events schedule
			List<Users> userList=reviewCommitteeMeetingUsersService.findUsersByReviewCommitteeMeeting(amApplication.getReviewCommitteeMeeting().getId());
			scheduledEventService.addSheduleInfo(TrainingProviderApplication.class.getName(), amApplication.getId(), user, userList, null, null, meetingAgender.getMeetingAgenda().getDescription(), amApplication.getReviewCommitteeMeeting());
		
		}
		
	}

	public void createETQAExtentionOfScopeTask(ConfigDocProcessEnum amExtensionOfScopeEtqaApproval,
		AssessorModExtensionOfScope assessorModExtensionOfScope, Users activeUser) throws Exception {
		assessorModExtensionOfScope.setStatus(ApprovalEnum.ApprovedByETQA);
		assessorModExtensionOfScopeService.update(assessorModExtensionOfScope);
		String desc = AbstractUI.getEntryLanguage(amExtensionOfScopeEtqaApproval.getTaskDescription());
		TasksService.instance().findFirstInProcessAndCreateTask(desc, activeUser, assessorModExtensionOfScope.getId(), assessorModExtensionOfScope.getClass().getName(), true, amExtensionOfScopeEtqaApproval, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, activeUser.getFirstName(), MailTagsEnum.LastName, activeUser.getLastName(), MailTagsEnum.Task, amExtensionOfScopeEtqaApproval.getTaskDescription()), null);
	
		
	}

	public void createETQAExtentionOfScopeRejectTask(ConfigDocProcessEnum configDocProcessEnum,
			AssessorModExtensionOfScope assessorModExtensionOfScope, Users formUser,
			ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		assessorModExtensionOfScope.setStatus(ApprovalEnum.RejectedByEQTA);
		assessorModExtensionOfScopeService.update(assessorModExtensionOfScope);
		String desc = "Assessor/Moderator extension of scope application has been rejected, please review";
		/*List<Users> userslist=new ArrayList();
		userslist.add(assessorModExtensionOfScope.getCreateUser());*/
		
		TasksService.instance().findFirstInProcessAndCreateRejectTask(desc, formUser, assessorModExtensionOfScope.getId(), assessorModExtensionOfScope.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, formUser.getFirstName(), MailTagsEnum.LastName, formUser.getLastName(), MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), null);
		// Creating RejectReason
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(assessorModExtensionOfScope.getId(), AssessorModExtensionOfScope.class.getName(), selectedRejectReason, formUser, ConfigDocProcessEnum.AM_EXTENSION_OF_SCOPE);
		for(RejectReasonMultipleSelection rrm:rrmList)
		{
			if(rrm !=null)
			{
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
			
		}
		
	}
	
	public void rejectExtentionOfScope(ConfigDocProcessEnum configDocProcessEnum,
			AssessorModExtensionOfScope assessorModExtensionOfScope, Users user,
			ArrayList<RejectReasons> selectedRejectReason,Tasks task) throws Exception {
		
		assessorModExtensionOfScope.setStatus(ApprovalEnum.Rejected);
		assessorModExtensionOfScopeService.update(assessorModExtensionOfScope);
		
		// Creating RejectReason
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(assessorModExtensionOfScope.getId(), AssessorModExtensionOfScope.class.getName(), selectedRejectReason, user, ConfigDocProcessEnum.AM_EXTENSION_OF_SCOPE);
		for(RejectReasonMultipleSelection rrm:rrmList)
		{
			if(rrm !=null)
			{
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		
		if (task.getFirstInProcess()) {
			List<Users> userslist = new ArrayList<>();
			userslist.add(assessorModExtensionOfScope.getCreateUser());
			String desc= "Your Extension Of Scope application was rejected please login and view the reason and make the relevant changes.";
			TasksService.instance().findFirstInProcessAndCreateRejectTask(desc, user, assessorModExtensionOfScope.getId(), assessorModExtensionOfScope.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName(), MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), userslist);
			TasksService.instance().completeTask(task);
		} else {
			TasksService.instance().findPreviousInProcessAndCreateTask("", user, assessorModExtensionOfScope.getId(),assessorModExtensionOfScope.getClass().getName(), true, task, null, null);
		}
		
		
	}
	
	public void finalRrejectExtentionOfScope(ConfigDocProcessEnum configDocProcessEnum,
			AssessorModExtensionOfScope assessorModExtensionOfScope, Users user,
			ArrayList<RejectReasons> selectedRejectReason,Tasks task) throws Exception {
		
		assessorModExtensionOfScope.setStatus(ApprovalEnum.Rejected);
		AssessorModeratorApplication amApp=assessorModExtensionOfScope.getAssessorModeratorApplication();
		amApp.setStatus(ApprovalEnum.Approved);
		update(amApp);
		assessorModExtensionOfScopeService.update(assessorModExtensionOfScope);
		
		// Creating RejectReason
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(assessorModExtensionOfScope.getId(), AssessorModExtensionOfScope.class.getName(), selectedRejectReason, user, ConfigDocProcessEnum.AM_EXTENSION_OF_SCOPE);
		for(RejectReasonMultipleSelection rrm:rrmList)
		{
			if(rrm !=null)
			{
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		
		TasksService.instance().completeTask(task);
		
		//Sending rejection latter
		amExtOfScopeRejectionEmail(assessorModExtensionOfScope,assessorModExtensionOfScope.getCreateUser(), selectedRejectReason);
		
	}
	
	public int countAllApprovedApplication(AssessorModeratorAppTypeEnum applicationType) throws Exception {
		return dao.countAllApprovedApplication(applicationType);
	}
	
	/**Ensure that you are not 
	 * accreditated for the 
	 * same Qualification 
	 * @throws Exception */
	public void validateQualificationAccreditatation(Qualification qual,Users formUser,AssessorModeratorAppTypeEnum appType) throws Exception
	{
		if (formUser !=null && formUser.getId() !=null && qual !=null && qual.getId() !=null) {
			List<AssessorModeratorApplication> assessorModeratorApplicationList = userQualificationsService
					.findAMApplicationByUserQualAppTypeAndAccept(formUser.getId(), qual.getId(),appType,true);
			if(assessorModeratorApplicationList !=null && assessorModeratorApplicationList.size()>0)
			{
				if(assessorModeratorApplicationList.get(0).getStatus() ==ApprovalEnum.Approved || assessorModeratorApplicationList.get(0).getCertificateNumber() !=null)
				{
					throw new Exception("You are accreditated for this qualification, please select a different qualification");
				}
				else
				{
					throw new Exception("There is an Assessor/Moderator application in process with the same qualification, please select a different qualification");
				}
				
				
			}
		}
	}
	
	
	/**Ensure that you are not 
	 * accreditated for the 
	 * same Unit Standards 
	 * @throws Exception */
	public void validateUniStandardAccreditatation(UnitStandards us,Users formUser,AssessorModeratorAppTypeEnum appType) throws Exception
	{
		
		if (formUser !=null && formUser.getId() !=null &&  us !=null && us.getId() !=null) {
			List<AssessorModeratorApplication> assessorModeratorApplicationList = userUnitStandardService
					.findAMApplicationByUserUSAppTypeAndAccept(formUser.getId(), us.getId(),appType,true);
			if(assessorModeratorApplicationList !=null && assessorModeratorApplicationList.size()>0)
			{
				if(assessorModeratorApplicationList.get(0).getStatus()==ApprovalEnum.Approved ||
				assessorModeratorApplicationList.get(0).getCertificateNumber() !=null)
				{
					throw new Exception("You are accreditated for this  Unit Standard, please select a different Unit Standard");
				}
				else
				{
					throw new Exception("There is an application in process with the same Unit Standard, please select a different Unit Standard");
				}
				
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public int countByReviewCommitteeMeetingAgenda(ReviewCommitteeMeetingAgenda reviewCommitteeMeetingAgenda) throws Exception {
		String hql = "select count(o) from AssessorModeratorApplication o where o.reviewCommitteeMeetingAgenda.id =:id";
		Map<String, Object> filters=new HashMap<>();
		filters.put("id", reviewCommitteeMeetingAgenda.getId());
		return dao.countWhere(filters, hql);

	}
	
	public void validateAssessorModeratorApp(Users formUser,AssessorModeratorAppTypeEnum applicationType)throws Exception
	{
		List<AssessorModeratorApplication> amAppList= findByUserAndAppType(formUser.getId(), applicationType);
		if(amAppList !=null && amAppList.size()>0)
		{
			AssessorModeratorApplication amApp=amAppList.get(0);
			if(amApp.getStatus() ==ApprovalEnum.Approved)
			{
				throw new Exception("You are already registered as "+getQualificationTitle(applicationType));
			}
			else
			{
				throw new Exception("The application cannot be processed as there is currently an application under review");
			}
			
			
		}
		if(applicationType ==AssessorModeratorAppTypeEnum.NewAssessorAndNewModerator)
		{
			amAppList=findByUser(formUser.getId(),false);
			if(amAppList !=null && amAppList.size()>0)
			{
				throw new Exception("This option is available for first time applicants");
			}
		}
		//validation Check for Re-Registration and Extension of scope
		AssessorModeratorAppTypeEnum extensionAppType=AssessorModeratorAppTypeEnum.ModeratorExtensionOfScope;
		AssessorModeratorAppTypeEnum reRegAppType=AssessorModeratorAppTypeEnum.ModeratorReRegistration;
		if(applicationType==AssessorModeratorAppTypeEnum.NewAssessorRegistration)
		{
			 extensionAppType=AssessorModeratorAppTypeEnum.AssessorExtensionOfScope;
			 reRegAppType=AssessorModeratorAppTypeEnum.AssessorReRegistration;
		}
		
		amAppList= findByUserAndAppType(formUser.getId(), extensionAppType);
		if(amAppList !=null && amAppList.size()>0) {
			AssessorModeratorApplication amApp=amAppList.get(0);
			if(amApp.getStatus() ==ApprovalEnum.Approved)
			{
				throw new Exception("You are already registered as "+getQualificationTitle(applicationType));
			}
			else
			{
				throw new Exception("The application cannot be processed as there is currently an application under review");
			}
		}
		
		amAppList= findByUserAndAppType(formUser.getId(), reRegAppType);
		if(amAppList !=null && amAppList.size()>0) {
			AssessorModeratorApplication amApp=amAppList.get(0);
			if(amApp.getStatus() ==ApprovalEnum.Approved)
			{
				throw new Exception("You are already registered as "+getQualificationTitle(applicationType));
			}
			else
			{
				throw new Exception("The application cannot be processed as there is currently an application under review");
			}
		}
	   
		
	}
	
	public String getQualificationTitle(AssessorModeratorAppTypeEnum applicationType)
	{
		String title="";
		if(applicationType==AssessorModeratorAppTypeEnum.NewAssessorRegistration)
		{
			title="Assessor";
		}
		else
		{
			title="Moderator";
		}
		return title;
	}

	
	public void resendStatmentOfQualificationToAllApprovedApplicationsByTypeList(List<AssessorModeratorAppTypeEnum> applicationTypeList) throws Exception {
		List<AssessorModeratorApplication> allApprovedApplications = findAllApprovedAssessorModeratorApplicationsByTypeListStatus(applicationTypeList);
		for (AssessorModeratorApplication approvedApplication : allApprovedApplications) {
			boolean sendNotfication = true;
			if (approvedApplication.getUser() != null && approvedApplication.getUser().getId() != null) {
				List<UserQualifications> userQualifications=userQualificationsService.findByUserAMApplicationAndAccept(approvedApplication.getUser().getId(), approvedApplication.getId(), true);
				List<UserUnitStandard> userUnitStandards=userUnitStandardService.findByUserAndAPApplicationAndAccept(approvedApplication.getUser().getId(), approvedApplication.getId(), true);
				if (userQualifications.isEmpty() && userUnitStandards.isEmpty()) {
					sendNotfication = false;
				}
				userQualifications = null;
				userUnitStandards = null;
			} else {
				sendNotfication = false;
			}
		
			if (Boolean.TRUE.equals(sendNotfication)) {
				sendStatementOfQualificationsError(approvedApplication);
			}
		}
		allApprovedApplications = null;
	}
	
	public List<AssessorModeratorApplication> findAllApprovedAssessorModeratorApplicationsByTypeListStatus(List<AssessorModeratorAppTypeEnum> applicationTypeList) throws Exception {
		return dao.findAllApprovedAssessorModeratorApplicationsByTypeListStatus(applicationTypeList);
	}

	public void sendStatementOfQualificationsError(AssessorModeratorApplication am) throws Exception {
		if (am.getCertificateNumber()==null){
			throw new Exception("Certificate is not avaialable for this application");
		}
		String barcode="";
		if (am.getUser().getRsaIDNumber() !=null && am.getUser().getRsaIDNumber().isEmpty()==false && am.getUser().getRsaIDNumber().isEmpty()==false){
			barcode=am.getUser().getRsaIDNumber();
		} else {
			barcode=am.getUser().getPassportNumber();
		}
		Users user = new Users();
		List<Users> list=hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.Senior_Manager_Quality_Assurance_and_Partnerships_ID);
		if(list!=null && list.size()>0) {
			user = list.get(0) ;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		params.put("assessorModeratorApplication",am);
		params.put("user",user);
		params.put("user_id",am.getUser().getId() );
		params.put("registration_number",am.getCertificateNumber());
		params.put("status",am.getStatus().getFriendlyName());
		params.put("barcode",barcode);
		params.put("date", GenericUtility.sdf7.format(am.getApprovedDate()));
		
		ArrayList<QualificationUnitStandardBean> qualificationUnitStandardList=new ArrayList<>();
		
		List<UserQualifications> userQualifications=userQualificationsService.findByUserAMApplicationAndAccept(am.getUser().getId(), am.getId(), true);
		List<UserUnitStandard> userUnitStandards=userUnitStandardService.findByUserAndAPApplicationAndAccept(am.getUser().getId(), am.getId(), true);
		
		for(UserQualifications qual:userQualifications){
			qualificationUnitStandardList.add(new QualificationUnitStandardBean(String.valueOf(qual.getQualification().getCode()), qual.getQualification().getDescription(), GenericUtility.sdf7.format(am.getApprovedDate())));
		} 
		for(UserUnitStandard us:userUnitStandards){
			qualificationUnitStandardList.add(new QualificationUnitStandardBean(String.valueOf(us.getUnitStandard().getCode()), us.getUnitStandard().getTitle(), GenericUtility.sdf7.format(am.getApprovedDate())));
		}
		params.put("QualificationUnitStandardBeanDataSource", new JRBeanCollectionDataSource(qualificationUnitStandardList));
		
		ArrayList<AttachmentBean> attachmentBeans=new ArrayList<>();
		
	    byte[] bites = JasperService.instance().convertJasperReportToByte("ETQ-TP-011-StatementOfQualificationsandorUnitStandardsNew.jasper", params);
	    
	    AttachmentBean beanAttachment=new AttachmentBean();
	    beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename("Statement_Of_Qualifications_And_Or_UnitStandards.pdf");
		attachmentBeans.add(beanAttachment);
	    
	    
	    String subject = "STATEMENT OF QUALIFICATIONS AND UNIT STANDARDS";
	    String msg =  "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #FULL_NAME#, </p>"
	    		+ " <p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please be advised that there was a system error that may have resulted in missing/incorrect details on the Statement of Qualifications/Unit Standards document issued when your accreditation/registration process was completed.</p>"
	    		+ " <p style=\"font-size:11.0pt;\";font-family:\"Arial\">Kindly be advised that this has been resolved and the correct details are now reflected on the Statement of Qualifications/Unit Standards document. Please be advised that the document is also available under your respective profile.</p>"
	    		+ " <p style=\"font-size:11.0pt;\";font-family:\"Arial\">For further clarification/support, kindly contact your Regional Office.</p>"
	    		+ " <p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>"
	    		+ " <p style=\"font-size:11.0pt;\";font-family:\"Arial\">Senior Manager: Quality Assurance and Partnerships</p>";
	    String userFullName = "";
	    if (am.getUser() != null && am.getUser().getTitle() != null) {
	    	userFullName = am.getUser().getTitle().getDescription() + " ";
		}
	    userFullName += am.getUser().getFirstName().trim() + " " + am.getUser().getLastName().trim();
	    
	    msg = msg.replaceAll("#FULL_NAME#", userFullName);
	    
	    GenericUtility.sendMailWithAttachementTempWithLog(am.getUser().getEmail(), subject, msg, attachmentBeans, null);
	}

	public AssessorModeratorApplication findByCerticateNumberOrIdNumber(String idnumber, Company trainingProvider, CompanyLearners companyLearners, Qualification qualification) throws Exception{
		CompanyUsersService companyUsersService = new CompanyUsersService();
		CompanyUsers companyUsers = companyUsersService.findByCompanyUserID(qualification.getId());
		return dao.findByCerticateNumberOrIdNumber(idnumber, trainingProvider, companyLearners, qualification);
	}
	
	//AssessorAccreditation
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> sortAndFilterWhereAMInfoApprovedAndByApplicationTypeLegacy(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, List<AssessorModeratorAppTypeEnum> applicationTypeList) throws Exception {
		String hql = "select o from AssessorModeratorApplication o where o.finalApproved = :booleanValue and (o.legacyAssessorAccreditation is not null or o.legacyModeratorAccreditation is not null) ";
		filters.put("booleanValue", true);
		if (!applicationTypeList.isEmpty()) {
			int counter = 1;
			hql += " and (  ";
			for (AssessorModeratorAppTypeEnum assessorModeratorAppTypeEnum : applicationTypeList) {
				if (counter == applicationTypeList.size()) {
					hql += " o.applicationType = :applicationType"+counter;
				} else {
					hql += " o.applicationType = :applicationType"+counter+" or ";
				}
				filters.put("applicationType" + counter, assessorModeratorAppTypeEnum);
				counter++;
			}
			hql += ") ";
		}
		return resolveUserDocs((List<AssessorModeratorApplication>) dao.sortAndFilterWhereAMInfo(first, pageSize, sortField, sortOrder, filters, hql));
	}

	//AssessorAccreditation
	public int countWhereAMInfoApprovedAndByApplicationTypeLegacy(Map<String, Object> filters, List<AssessorModeratorAppTypeEnum> applicationTypeList) throws Exception {
		String hql = "select count(o) from AssessorModeratorApplication o where o.finalApproved = :booleanValue and (o.legacyAssessorAccreditation is not null or o.legacyModeratorAccreditation is not null) ";
		if (!applicationTypeList.isEmpty()) {
			int counter = 1;
			hql += " and (  ";
			for (AssessorModeratorAppTypeEnum assessorModeratorAppTypeEnum : applicationTypeList) {
				if (counter == applicationTypeList.size()) {
					hql += " o.applicationType = :applicationType"+counter;
				} else {
					hql += " o.applicationType = :applicationType"+counter+" or ";
				}
				counter++;
			}
			hql += " ) ";
		}
		return dao.countWhereAMInfo(filters, hql);
	}
	
	//AssessorAccreditation
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorApplication> sortAndFilterWhereAMInfoApplicationTypeLegacy(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, List<AssessorModeratorAppTypeEnum> applicationTypeList) throws Exception {
		String hql = "select o from AssessorModeratorApplication o where o.id is not null and (o.legacyAssessorAccreditation is not null or o.legacyModeratorAccreditation is not null) ";
		if (!applicationTypeList.isEmpty()) {
			int counter = 1;
			hql += " and (  ";
			for (AssessorModeratorAppTypeEnum assessorModeratorAppTypeEnum : applicationTypeList) {
				if (counter == applicationTypeList.size()) {
					hql += " o.applicationType = :applicationType"+counter;
				} else {
					hql += " o.applicationType = :applicationType"+counter+" or ";
				}
				filters.put("applicationType" + counter, assessorModeratorAppTypeEnum);
				counter++;
			}
			hql += ") ";
		}
		return resolveUserDocs((List<AssessorModeratorApplication>) dao.sortAndFilterWhereAMInfo(first, pageSize, sortField, sortOrder, filters, hql));
	}

	//AssessorAccreditation
	public int countWhereAMInfoApplicationTypeLegacy(Map<String, Object> filters,List<AssessorModeratorAppTypeEnum> applicationTypeList) throws Exception {
		String hql = "select count(o) from AssessorModeratorApplication o where o.id is not null and (o.legacyAssessorAccreditation is not null or o.legacyModeratorAccreditation is not null)";
		if (!applicationTypeList.isEmpty()) {
			int counter = 1;
			hql += " and (  ";
			for (AssessorModeratorAppTypeEnum assessorModeratorAppTypeEnum : applicationTypeList) {
				if (counter == applicationTypeList.size()) {
					hql += " o.applicationType = :applicationType"+counter;
				} else {
					hql += " o.applicationType = :applicationType"+counter+" or ";
				}
				counter++;
			}
			hql += ") ";
		}
		return dao.countWhereAMInfo(filters, hql);
	}
	
	public AssessorModeratorApplication findByUserForAssessorModeratorType(Long userId, AssessorModeratorTypeEnum assessorModeratorType) {
		return dao.findByUserForAssessorModeratorType(userId, assessorModeratorType);
	}
	
	public AssessorModeratorApplication findByUserForAssessorModeratorTypeAndApplicationType(Long userId, AssessorModeratorTypeEnum assessorModeratorType, List<AssessorModeratorAppTypeEnum> applicationTypeList) throws Exception {
		return dao.findByUserForAssessorModeratorTypeAndApplicationType(userId, assessorModeratorType, applicationTypeList);
	}
	
	public AssessorModeratorApplication findByCerticateNumberAssessorModeratorTypeAndApplicationType(String certicateNumber, AssessorModeratorTypeEnum assessorModeratorType, List<AssessorModeratorAppTypeEnum> applicationTypeList) throws Exception {
		return dao.findByCerticateNumberAssessorModeratorTypeAndApplicationType(certicateNumber, assessorModeratorType,applicationTypeList);
	}
    
	public void createUpdateAssessorModQualificationsTradeTestUsers(AssessorModeratorApplication app, List<Qualification> qualificationsList, Users sessionUser) throws Exception {
		
		List<IDataEntity> updateList = new ArrayList<>();
		List<IDataEntity> createList = new ArrayList<>();
		
		List<UserQualifications> allQualificationsByUserAndApplicationAssigned = userQualificationsService.findUserQualificationByAssessModeratorAppllication(app.getId());
		for (UserQualifications userQualifications : allQualificationsByUserAndApplicationAssigned) {
			userQualifications.setAccept(false);
			userQualifications.setUpdateUser(sessionUser);
			userQualifications.setUpdateDate(getSynchronizedDate());
			updateList.add(userQualifications);
		}
		allQualificationsByUserAndApplicationAssigned.clear();
		if (!updateList.isEmpty()) {
			dao.updateBatch(updateList);
			updateList.clear();
		}
		
		for (Qualification qualification : qualificationsList) {
			UserQualifications userQualifications = null;
			allQualificationsByUserAndApplicationAssigned = userQualificationsService.findUserQualificationByAssessModeratorAppllicationAndQualification(app.getId(), qualification.getId());
			if (!allQualificationsByUserAndApplicationAssigned.isEmpty()) {
				userQualifications = userQualificationsService.findByKeyWithJoin(allQualificationsByUserAndApplicationAssigned.get(0).getId());
				userQualifications.setAccept(true);
				userQualifications.setUpdateUser(sessionUser);
				userQualifications.setUpdateDate(getSynchronizedDate());
				userQualifications.setExpirtyDate(app.getEndDate());
				updateList.add(userQualifications);
			} else {
				userQualifications = new UserQualifications(app.getUser(), qualification);
				userQualifications.setForAssessorModeratorApplication(app);
				userQualifications.setAccept(true);
				userQualifications.setUpdateUser(sessionUser);
				userQualifications.setUpdateDate(getSynchronizedDate());
				userQualifications.setExpirtyDate(app.getEndDate());
				createList.add(userQualifications);
			}
		}
		
		if (!updateList.isEmpty()) {
			dao.updateBatch(updateList);
			updateList.clear();
		}
		if (!createList.isEmpty()) {
			dao.createBatch(createList);
			createList.clear();
		}
	}
	
	public void addNewScopeQualifications(AssessorModeratorApplication app, List<Qualification> qualificationsList, Users sessionUser) throws Exception {
		List<IDataEntity> createList = new ArrayList<>();
		for (Qualification newQual : qualificationsList) {
			UserQualifications userQualifications = null;
			List<UserQualifications> assignedList = userQualificationsService.findUserQualificationByAssessModeratorAppllicationAndQualification(app.getId(), newQual.getId());
			if (assignedList.isEmpty()) {
				userQualifications = new UserQualifications(app.getUser(), newQual);
				userQualifications.setForAssessorModeratorApplication(app);
				userQualifications.setAccept(true);
				userQualifications.setUpdateUser(sessionUser);
				userQualifications.setUpdateDate(getSynchronizedDate());
				userQualifications.setExpirtyDate(app.getEndDate());
				createList.add(userQualifications);
			}
			assignedList.clear();
		}
		if (!createList.isEmpty()) {
			dao.createBatch(createList);
			createList.clear();
		}
	}
	// return true if already linked to qualification
	public Boolean checkIfApplicationLinkedToQualification(AssessorModeratorApplication app, Qualification qualification) throws Exception{
		Boolean assigned = false;
		List<UserQualifications> assignedList = userQualificationsService.findUserQualificationByAssessModeratorAppllicationAndQualification(app.getId(), qualification.getId());
		if (!assignedList.isEmpty()) {
			assigned = true;
		}
		assignedList.clear();
		return assigned;
	}
	
	/**
	 * This will run all nesseary check for when validiatiing assess/moderator application for normal / TTC 
	 * @param user
	 * @return StringBuilder a entry of errors
	 */
	public StringBuilder validiateSetmisInformation(AssessorModeratorApplication assessorModeratorApplication) {
		StringBuilder errors = new StringBuilder("");
		if (assessorModeratorApplication.getStartDate() != null) {
			if (assessorModeratorApplication.getEndDate() != null) {
				// 5 years minus one day
				Date maxDate  = GenericUtility.getEndOfDay(GenericUtility.deductDaysFromDate(GenericUtility.addYearsToDate(GenericUtility.getStartOfDay(assessorModeratorApplication.getStartDate()), 5), 1));
				if (GenericUtility.getEndOfDay(assessorModeratorApplication.getEndDate()).after(maxDate)) {
					errors.append("Validation Failed For SETMIS Designation End Date <ul><li>Date Range Error. Designation End Date can not be more than 5 Years from Designation Start Date.</li></ul>");
					errors.append("<br/>");
				}
			} else {
				errors.append("Validation Failed For SETMIS Designation End Date <ul><li>Designation End Date must be provided </li></ul>");
				errors.append("<br/>");
			}
		} else {
			errors.append("Validation Failed For SETMIS Designation Start Date <ul><li>Designation Start Date must be provided </li></ul>");
			errors.append("<br/>");
		}
		
		if (assessorModeratorApplication.getStartDate() != null && assessorModeratorApplication.getEndDate() != null) {
			if (assessorModeratorApplication.getStartDate().after(assessorModeratorApplication.getEndDate() )) {
				errors.append("Validation Failed For SETMIS Date Range <ul><li>Designation Start Date: must be before Designation End Date </li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (assessorModeratorApplication.getCertificateNumber() != null && !assessorModeratorApplication.getCertificateNumber().trim().isEmpty()) {
			if (!AssessorModeratorApplicaitonValidationService.instance().validateCertificateNumber(assessorModeratorApplication.getCertificateNumber())) {
				errors.append("Validation Failed For SETMIS Designation Registration Number<ul><li>Field may not start with a space.</li><li>Field may not be more than 20 characters.</li><li>Value in field may only contain the characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\\:._-</li><li>Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or –</li></ul>");
				errors.append("<br/>");
			}
		}
		
		
		
		return errors;
	}
	
	public StringBuilder additionalValidiationChecks(AssessorModeratorApplication assessorModeratorApplication) {
		StringBuilder errors = new StringBuilder("");
		try {
			if (assessorModeratorApplication != null) {
				// check if certificate number is unique
				if (assessorModeratorApplication.getCertificateNumber() != null && !assessorModeratorApplication.getCertificateNumber().trim().isEmpty()) {
					if (assessorModeratorApplication.getId() != null) {
						if (countByCertificateNumberExcludingId(assessorModeratorApplication.getCertificateNumber().trim(), assessorModeratorApplication.getId()) > 0) {
							errors.append("Validation Failed For SETMIS Certificate Number<ul><li>Certificate / Designation Registartion Number is already registered on the application. Provide a different Certificate / Designation Registartion Number </li></ul>");
							errors.append("<br/>");
						}
					} else {
						if (countByCertificateNumber(assessorModeratorApplication.getCertificateNumber().trim()) > 0) {
							errors.append("Validation Failed For SETMIS Certificate Number<ul><li>Certificate / Designation Registartion Number is already registered on the application. Provide a different Certificate / Designation Registartion Number </li></ul>");
							errors.append("<br/>");
						}
					}
				}
			}
		} catch (Exception e) {
			logger.fatal(e);
			// add to error list for future
		}
		return errors;
	}
	
	public int countByCertificateNumber(String certificateNumber) throws Exception {
		return dao.countByCertificateNumber(certificateNumber);
	}
	
	public int countByCertificateNumberExcludingId(String certificateNumber, Long id) throws Exception {
		return dao.countByCertificateNumberExcludingId(certificateNumber, id);
	}
	
	public void applicationDeAccreditationSchedule(Date today) throws Exception {
		List<IDataEntity> createList = new ArrayList<>();
		List<IDataEntity> updateList = new ArrayList<>();
		Date dayAfter = GenericUtility.getStartOfDay(GenericUtility.addDaysToDate(today, 1));
		List<AssessorModeratorApplication> applicationList = findByStatus(ApprovalEnum.Approved);
		for (AssessorModeratorApplication assessorModeratorApplication : applicationList) {
			if (assessorModeratorApplication.getEndDate() != null && dayAfter.after(GenericUtility.getStartOfDay(assessorModeratorApplication.getEndDate()))) {
				assessorModeratorApplication.setStatus(ApprovalEnum.DeRegistered);
				assessorModeratorApplication.setLastUpdateDate(getSynchronizedDate());
				assessorModeratorApplication.setSystemUpdate(true);
				updateList.add(assessorModeratorApplication);
				StringBuilder sb = new StringBuilder();
				sb.append("System Assessor/Moderator Deregistered Business Rule For: Assessor/Moderator. ");
				sb.append("Accreditation End Date: " + HAJConstants.sdfDateMonthYear.format(assessorModeratorApplication.getEndDate()) +". ");
				sb.append("Changes: status change from Approved to Deregistered on status change date: "+HAJConstants.sdf3.format(getSynchronizedDate())+". ");
				createList.add(ScheduleChangesLogService.instance().addNewEntryWithoutCreate(null, assessorModeratorApplication.getUser(), assessorModeratorApplication.getClass().getName(), assessorModeratorApplication.getId(), sb.toString(), false));
			}
		}
		if (!updateList.isEmpty() && !createList.isEmpty()) {
			dao.createAndUpdateBatch(createList, updateList);
		}
	}
	
	public AssessorModeratorApplication findByUserForAssessorModeratorTypeAndNotEqualStatus(Long userId, AssessorModeratorTypeEnum assessorModeratorType, ApprovalEnum approvalEnum, AssessorModeratorAppTypeEnum applicationType) throws Exception {
		return dao.findByUserForAssessorModeratorTypeAndNotEqualStatus(userId, assessorModeratorType, approvalEnum, applicationType);
	}

	public void downloadReport(AssessorModeratorTypeEnum assessorModeratorTypeEnum) throws Exception{
		Workbook wb = new SXSSFWorkbook(100);
		Sheet sh = wb.createSheet();
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		Integer counter = 1;
		// headers
		data.put(counter.toString(), new Object[] {"First Name", "Middle Name", "Last Name", "RSA ID Number",  "Passport Number", "Type Of Application", "Type Of Assessor/Moderator","Status","Registration Number", "Start Date", "End Date", "Review Committee Date", "Decision Number"});
		counter++;
		// data population
		populateDataForReport(data, counter,assessorModeratorTypeEnum);
		counter = null;
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sh.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Integer)
					cell.setCellValue((Integer) obj);
				else if (obj instanceof Double)
					cell.setCellValue((Double) obj);;
			}
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			wb.write(bos);
			byte[] bytes = bos.toByteArray();
			String fileName = "Assessor/Moderator Report.xlsx";
			JasperService.instance().downloadFileExcel(bytes, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void populateDataForReport(Map<String, Object[]> data, Integer counter, AssessorModeratorTypeEnum assessorModeratorTypeEnum) throws Exception{
		int counterForPopulation = counter;
		List<AssessorModeratorBean> resultsList = populateReport(assessorModeratorTypeEnum);
		// populate data found into report
		for (AssessorModeratorBean entry : resultsList) {
			populateDataReport(data, entry, counterForPopulation);
			counterForPopulation++;
		}
		resultsList.clear();
		counter = counterForPopulation;
	}
	
	private void populateDataReport(Map<String, Object[]> data, AssessorModeratorBean entry, Integer counter) throws Exception{
		data.put(counter.toString(), new Object[] {entry.getFirstName(), entry.getMiddleName(), entry.getLastName(), entry.getRsaIDNumber(), entry.getPassportNumber(), entry.getType(), entry.getAssModType(), entry.getStatus(),entry.getRegistrationNumber(), entry.getStartDateString(), entry.getEndDateString(), entry.getReviewDateString(), entry.getDecisionNumber()});
	}
	
	public List<AssessorModeratorBean> populateReport() throws Exception {
		return dao.populateReport(AssessorModeratorTypeEnum.MerSETA_AM);
	}
	
	public List<AssessorModeratorBean> populateReport(AssessorModeratorTypeEnum assessorModeratorTypeEnum) throws Exception {
		return dao.populateReport(assessorModeratorTypeEnum);
	}
}