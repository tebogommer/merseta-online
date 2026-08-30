package haj.com.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.primefaces.model.SortOrder;

import haj.com.bean.AddressUpdateInfoBean;
import haj.com.bean.UserUpdateInfoBean;
import haj.com.dao.UsersDAO;
import haj.com.entity.ChangeReason;
import haj.com.entity.Company;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.DocumentTracker;
import haj.com.entity.HostingCompany;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.Tasks;
import haj.com.entity.UserChangeRequest;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.DocumentTrackerEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.UsersStatusEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.framework.GenericAuditLog;
import haj.com.framework.IDataEntity;
import haj.com.rest.idverification.IdVerificationRealTime;
import haj.com.rest.idverification.IdVerificationService;
import haj.com.utils.GenericUtility;
import haj.com.validators.exports.services.CompanyValidationService;
import haj.com.validators.exports.services.UserValidationService;

// TODO: Auto-generated Javadoc
/**
 * The Class UsersService.
 */
public class UsersService extends AbstractService {

	/** The dao. */
	private UsersDAO dao = new UsersDAO();

	/** The register service. */
	private RegisterService registerService = new RegisterService();

	/** The config doc service. */
	private ConfigDocService configDocService = new ConfigDocService();

	/** The doc service. */
	private DocService docService = new DocService();

	/** The users training provider service. */
	private UsersTrainingProviderService usersTrainingProviderService = new UsersTrainingProviderService();

	/** The company users service. */
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	
	/** The IdVerificationService service. */
	private IdVerificationService ivs = new IdVerificationService();

	/**
	 * Instantiates a new users service.
	 */
	public UsersService() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new users service.
	 *
	 * @param auditlog
	 *            the auditlog
	 */
	public UsersService(Map<String, Object> auditlog) {
		super(auditlog);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get all Users.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Users}
	 * @throws Exception
	 *             the exception
	 * @see Users
	 */
	public List<Users> allUsers() throws Exception {
		return dao.allUsers();
	}
	
	public List<Users> allPrimarySdfUsers() throws Exception {
		return dao.allPrimarySdfUsers();
	}

	/**
	 * Find by GUID.
	 *
	 * @param guid
	 *            the guid
	 * @return the users
	 * @throws Exception
	 *             the exception
	 */
	public Users findByGUID(String guid) throws Exception {
		return dao.findByGUID(guid.trim());
	}

	/**
	 * Create or update Users.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Users
	 */
	public void create(Users entity) throws Exception {
		if (entity.getId() == null) {
			checkEmailUsedEmailOnly(entity.getEmail());
			String pwd = GenericUtility.genPassord();
			new LogonService();
			entity.setPassword(LogonService.encryptPassword(pwd));
			entity.setStatus(UsersStatusEnum.EmailNotConfrimed);
			entity.setEmailGuid(UUID.randomUUID().toString());
			entity.setRegistrationDate(new java.util.Date());
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Creates the learner and docs.
	 *
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 */
	public void createLearnerAndDocs(Users entity, Company company) throws Exception {
		if (entity.getId() == null) {
			checkEmailUsedEmailOnly(entity.getEmail());
			String pwd = GenericUtility.genPassord();
			new LogonService();
			entity.setPassword(LogonService.encryptPassword(pwd));
			entity.setStatus(UsersStatusEnum.EmailNotConfrimed);
			entity.setEmailGuid(UUID.randomUUID().toString());
			entity.setRegistrationDate(new java.util.Date());
			this.dao.create(entity);
			registerService.registerLearner(entity, pwd);
		} else this.dao.update(entity);

		entity.getDocs().forEach(doc -> {
			try {
				docService.create(doc);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		this.dao.update(entity);
	}

	/**
	 * Create or update Users with Hosting Company.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @param hc
	 *            the hc
	 * @throws Exception
	 *             the exception
	 * @see Users
	 */
	public void create(Users entity, HostingCompany hc) throws Exception {
		if (entity.getId() == null) {
			checkEmailUsedEmailOnly(entity.getEmail());
			String pwd = GenericUtility.genPassord();
			new LogonService();
			entity.setPassword(LogonService.encryptPassword(pwd));
			entity.setStatus(UsersStatusEnum.EmailNotConfrimed);
			entity.setEmailGuid(UUID.randomUUID().toString());
			entity.setRegistrationDate(new java.util.Date());
			this.dao.create(entity);
			registerService.registerUserHostCompany(entity, hc, pwd);
		} else this.dao.update(entity);
	}

	/**
	 * Update Users.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Users
	 */
	/*
	 * public void update(Users entity) throws Exception { this.dao.update(entity);
	 * }
	 */
	public void update(Users entity) throws Exception {
		if (checkEmailUsed(entity.getEmail(), entity.getId()) != null) throw new ValidationException("user.already.email");
		this.dao.update(entity);

		/**
		 * JMB 10 Oct 2016
		 *
		 * log when a user is updated
		 */
		GenericAuditLog.log(auditlog, "targetKey", entity.getId(), "Update User", "Updated User Information - ID: " + entity.getId() + " ( " + entity.getFirstName() + " " + entity.getLastName() + " )");

	}
	
	public void updateWithValidiationDate(Users entity) throws Exception{
		entity.setValidiationRanDate(getSynchronizedDate());
		update(entity);
	}
	
	public void updateAfterLoginValidiation(Users entity) throws Exception{
		List<IDataEntity> updateList = new ArrayList<>();
		if (entity.getBirthAddress() != null && entity.getBirthAddress().getId() != null) {
			updateList.add(entity.getBirthAddress());
		}
		if (entity.getPostalAddress() != null && entity.getPostalAddress().getId() != null) {
			updateList.add(entity.getPostalAddress());
		}
		if (entity.getResidentialAddress() != null && entity.getResidentialAddress().getId() != null) {
			updateList.add(entity.getResidentialAddress());
		}
		updateList.add(entity);
		dao.updateBatch(updateList);
	}
	
	

	public void save(Users entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete Users.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Users
	 */
	public void delete(Users entity) throws Exception {
		entity.setActive(false);
		this.dao.update(entity);
		// this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a Users object
	 * @throws Exception
	 *             the exception
	 * @see Users
	 */
	public Users findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	public IdVerificationRealTime idVerificationRealTime(Users users) throws Exception {		
		return ivs.idVerificationRealTime(users.getRsaIDNumber(), users.getFirstName() + users.getLastName());
	}
	
	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @param configDocProcess
	 *            the config doc process
	 * @param companyOrUserType
	 *            the company or user type
	 * @return a Users object
	 * @throws Exception
	 *             the exception
	 * @see Users
	 */
	public Users findByKey(long id, ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyOrUserType) throws Exception {
		return resolveDocs(dao.findByKey(id), configDocProcess, companyOrUserType);
	}
	
	public Users findByKeyAndResolveDocTargetKeyAndClass(long id, ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyOrUserType,Long targetKey,String targetClass) throws Exception {
		return resolveDocs(dao.findByKey(id), configDocProcess, companyOrUserType,targetKey,targetClass);
	}

	/**
	 * Find Users by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.Users}
	 * @throws Exception
	 *             the exception
	 * @see Users
	 */
	public List<Users> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Find by passport number.
	 *
	 * @param passportNum
	 *            the passport num
	 * @return the users
	 * @throws Exception
	 *             the exception
	 */
	public Users findByPassportNumber(String passportNum) throws Exception {
		return dao.findByPassportNumber(passportNum);
	}

	/**
	 * Find by rsa id.
	 *
	 * @param rsaId
	 *            the rsa id
	 * @return the users
	 * @throws Exception
	 *             the exception
	 */
	public Users findByRsaId(String rsaId) throws Exception {
		return dao.findByRsaId(rsaId);
	}

	/**
	 * Find by rsa id join address.
	 *
	 * @param rsaId
	 *            the rsa id
	 * @return the users
	 * @throws Exception
	 *             the exception
	 */
	public Users findByRsaIdJoinAddress(String rsaId) throws Exception {
		return dao.findByRsaIdJoinAddress(rsaId);
	}

	/**
	 * Find by passport number join address.
	 *
	 * @param passportNum
	 *            the passport num
	 * @return the users
	 * @throws Exception
	 *             the exception
	 */
	public Users findByPassportNumberJoinAddress(String passportNum) throws Exception {
		return dao.findByPassportNumberJoinAddress(passportNum);
	}

	/**
	 * Lazy load Users.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.Users}
	 * @throws Exception
	 *             the exception
	 */
	public List<Users> allUsers(int first, int pageSize) throws Exception {
		return dao.allUsers(Long.valueOf(first), pageSize);
	}

	/**
	 * Count.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Users
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(Users.class);
	}

	/**
	 * All users.
	 *
	 * @author TechFinium
	 * @param class1
	 *            Users class
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
	 * @return a list of {@link haj.com.entity.Users} containing data
	 */
	@SuppressWarnings("unchecked")
	public List<Users> allUsers(Class<Users> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		return (List<Users>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	@SuppressWarnings("unchecked")
	public List<Users> allSDFUsers(Class<Users> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		return (List<Users>) dao.sortAndFilterSDF(class1, first, pageSize, sortField, sortOrder, filters);

	}

	public int countSDFUsers(Class<Users> entity, Map<String, Object> filters) {
		return dao.countSDFUsers(entity, filters);
	}

	/**
	 * Count.
	 *
	 * @param entity
	 *            Users class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the Users entity
	 */
	public int count(Class<Users> entity, Map<String, Object> filters) {
		return dao.count(entity, filters);
	}

	/**
	 * Find user by email GUID.
	 *
	 * @param emailGuid
	 *            the email guid
	 * @return the users
	 * @throws Exception
	 *             the exception
	 */
	public Users findUserByEmailGUID(String emailGuid) throws Exception {
		return dao.findUserByEmailGUID(emailGuid);
	}

	/**
	 * Check email used.
	 *
	 * @param email
	 *            the email
	 * @param uid
	 *            the uid
	 * @return the users
	 * @throws Exception
	 *             the exception
	 */
	public Users checkEmailUsed(String email, Long uid) throws Exception {
		return dao.checkEmailUsed(email, uid);
	}

	/**
	 * Gets the user by email.
	 *
	 * @param email
	 *            the email
	 * @return the user by email
	 * @throws Exception
	 *             the exception
	 */
	public Users getUserByEmail(String email) throws Exception {
		return dao.getUserByEmail(email);
	}

	/**
	 * Checks if is mail used.
	 *
	 * @param email
	 *            the email
	 * @param user
	 *            the user
	 * @throws Exception
	 *             the exception
	 */
	public void isMailUsed(String email, Users user) throws Exception {
		Users u = dao.checkEmailUsed(email, user.getId());
		//if (u != null) throw new ValidationException(getEntryLanguage("user.already.email"));
		if (u != null) throw new ValidationException("Email already in use on the system.");
	}

	/**
	 * Check if email is already used.
	 *
	 * @param email
	 *            the email
	 * @throws Exception
	 *             the exception
	 */
	public void checkEmailUsedEmailOnly(String email) throws Exception {
		Users u = dao.checkEmailUsedEmailOnly(email);
		//if (u != null) throw new ValidationException(getEntryLanguage("user.already.email"));
		if (u != null) throw new Exception("Email already in use on the system.");
	}
	
	public void emailValidation(Users users) throws Exception {
		Users u = dao.checkEmailUsedEmailOnly(users.getEmail());
		if(u  !=null)
		{
			if(users.getId()==null)
			{
				new ValidationException("Email already in use on the system.");
			}
			else if (!u.getId().equals(users.getId()))
			{
				new ValidationException("Email already in use on the system.");
			}
		}
		
	}

	/**
	 * Find admin users.
	 *
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<Users> findAdminUsers() throws Exception {
		return dao.findAdminUsers();
	}

	/**
	 * Prepare new registration.
	 *
	 * @param configDocProcess
	 *            the config doc process
	 * @param user
	 *            the user
	 * @throws Exception
	 *             the exception
	 */
	public void prepareNewRegistration(ConfigDocProcessEnum configDocProcess, Users user) throws Exception {
		List<ConfigDoc> l = configDocService.findByProcessForReg(configDocProcess);
		if (l != null && l.size() > 0) {
			user.setDocs(new ArrayList<Doc>());
			l.forEach(a -> {
				user.getDocs().add(new Doc(a, user));

			});
		}

	}

	/**
	 * Creates the learner with company training provider and docs.
	 *
	 * @param user
	 *            the user
	 * @param company
	 *            the company
	 * @param trainingProvider
	 *            the training provider
	 * @throws Exception
	 *             the exception
	 * @throws ValidationException
	 *             the validation exception
	 */
	public void createLearnerWithCompanyTrainingProviderAndDocs(Users user, Company company, Company trainingProvider) throws Exception, ValidationException {
		createLearnerAndDocs(user, company);
		if (company.getId() != null && user.getId() != null) {
			usersTrainingProviderService.createUsersTrainingProvider(user, trainingProvider);
			companyUsersService.createCompanyUsersCheckExistence(trainingProvider, user);
		}
	}

	/**
	 * Document upload.
	 *
	 * @param forUser
	 *            the for user
	 * @param users
	 *            the users
	 * @throws Exception
	 *             the exception
	 */
	public void documentUpload(Users forUser, Users users) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		if (forUser.getDocs() != null) {
			for (Doc doc : forUser.getDocs()) {
				if (doc.getId() == null && doc.getData() != null) {
					doc.setForUsers(forUser);
					doc.setVersionNo(1);
					doc.setUsers(users);
					dataEntities.add(doc);
					dataEntities.add(new DocByte(doc.getData(), doc));
					dataEntities.add(new DocumentTracker(doc, users, new java.util.Date(), DocumentTrackerEnum.Upload));
				}
			}
		}
		this.dao.createBatch(dataEntities);
	}

	/**
	 * Upload new version.
	 *
	 * @param doc
	 *            the doc
	 * @param users
	 *            the users
	 * @throws Exception
	 *             the exception
	 */
	public void uploadNewVersion(Doc doc, Users users) throws Exception {
		List<Doc> docs = null;
		if (doc.getDocVerions() == null || doc.getDocVerions().isEmpty()) {
			docs = new ArrayList<>();
			docs.add(DocService.instance().findByKey(doc.getId()));
			doc.setId(null);
		} else {
			docs = doc.getDocVerions();
			Collections.reverse(docs);
			docs.add(0, doc);
		}
		if (doc.getData() != null) {
			DocService.instance().save(doc, users, docs);
		}
	}

	/**
	 * Locates Doucments Against User.
	 *
	 * @param user
	 *            the user
	 * @param configDocProcess
	 *            the config doc process
	 * @param companyOrUserType
	 *            the company or user type
	 * @return the users
	 * @throws Exception
	 *             the exception
	 */
	public Users resolveDocs(Users user, ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyOrUserType) throws Exception {
		user.setDocs(docService.searchByUser(user));
		prepareDocs(user, configDocProcess, companyOrUserType);
		return user;
	}
	
	public Users resolveDocs(Users user, ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyOrUserType,Long targetKey,String targetClass) throws Exception {
		user.setDocs(docService.searchByUserConfigtargetClassAndKey(user, configDocProcess, targetClass, targetKey));
		prepareDocs(user, configDocProcess, companyOrUserType);
		return user;
	}
	
	public Users locateUserDocs(Users user) throws Exception{
		user.setDocs(docService.searchByUser(user));
		return user;
	}

	/**
	 * Prepare docs.
	 *
	 * @param user
	 *            the user
	 * @param configDocProcess
	 *            the config doc process
	 * @param companyOrUserType
	 *            the company or user type
	 * @throws Exception
	 *             the exception
	 */
	public void prepareDocs(Users user, ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyOrUserType) throws Exception {
		List<ConfigDoc> l = configDocService.findByProcessNotUploadedUser(user, configDocProcess, companyOrUserType);
		if (l != null && l.size() > 0) {
			l.forEach(a -> {
				user.getDocs().add(new Doc(user, a));
			});
		}
	}

	/**
	 * Do check.
	 *
	 * @param user
	 *            the user
	 * @return the string
	 */
	public String doCheck(Users user) {
		StringBuilder exceptions = new StringBuilder("");
		String ex = user.getValidUser();
		if (ex.length() > 0) {
			exceptions.append("<h3>" + user.getFirstName() + " " + user.getLastName() + "</h3><ul>" + ex + "</ul>");
		}
		return exceptions.toString();
	}

	public List<Users> findByRole(Long roleId) throws Exception {
		return dao.findByRole(roleId);
	}

	public List<Users> searchUser(String description) throws Exception {
		return dao.searchUser(description);
	}

	public List<Users> searchUserNotEmployee(String description) throws Exception {
		return dao.searchUserNotEmployee(description);
	}
	
	/* Validates user 
	 * when updating info
	 **/
	public void updateUserValidation(Users oldUser, Users newUser)  throws Exception 
	{
		/* User email has been changed */
		if (!oldUser.getEmail().trim().equals(newUser.getEmail().trim())) {
			Users u=getUserByEmail(newUser.getEmail());
			if(u !=null && u.getId() !=null)
			{
				 throw new Exception("Another user is already registered with the given email");
			}
		}

		if ((oldUser.getRsaIDNumber() !=null && !oldUser.getRsaIDNumber().isEmpty() && !oldUser.getRsaIDNumber().equals("")) || 
			(oldUser.getPassportNumber() !=null && !oldUser.getPassportNumber().isEmpty() && !oldUser.getPassportNumber().equals("")) ) {
			/* User RSA ID number has been changed */
			if((oldUser.getRsaIDNumber() !=null && newUser.getRsaIDNumber() !=null) &&  !oldUser.getRsaIDNumber().trim().equals(newUser.getRsaIDNumber().trim()))
			{
				Users u=findByRsaId(newUser.getRsaIDNumber());
				if(u !=null && u.getId() !=null)
				{
					 throw new Exception("Another user is already registered with the given RSA ID number");
				}
			}
			/* User Passport number has been changed */
			else if((oldUser.getPassportNumber() !=null && newUser.getPassportNumber() !=null) && !oldUser.getPassportNumber().trim().equals(newUser.getPassportNumber().trim()))
			{
				Users u=findByPassportNumber(newUser.getPassportNumber());
				if(u !=null && u.getId() !=null)
				{
					 throw new Exception("Another user is already registered with the given passport number");
				}
			}
		}
	}

	public void createTask(Users user, ChangeReason changeReason)   throws Exception {
		UserChangeRequestService userChangeRequestService=new UserChangeRequestService();
	    Users oldUser=findByKey(user.getId());
		updateUserValidation(oldUser, user);
		UserChangeRequest userChangeRequest=new UserChangeRequest(user);
		userChangeRequest.setApprovalStatus(ApprovalEnum.PendingApproval);
		userChangeRequestService.create(userChangeRequest);
		//Adding Change Reason for Creating CompanyUser
		ChangeReasonService.instance().createChangeReason(userChangeRequest.getId(), userChangeRequest.getClass().getName(),changeReason);
		String desc = "User details has been updated, please approve the information provided";
		TasksService.instance().findFirstInProcessAndCreateTask(desc, user, userChangeRequest.getId(), userChangeRequest.getClass().getName(), true, ConfigDocProcessEnum.USER_DETAILS_CHANGE, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
		
	}

	public void approveUserChangesTask(UserChangeRequest userChangeRequest, ChangeReason changeReason, Tasks tasks) throws Exception {
		UserChangeRequestService userChangeRequestService=new UserChangeRequestService();
		userChangeRequest.setApprovalStatus(ApprovalEnum.Approved);
		Users user=populateChangeRequest(userChangeRequest);
		updateUserValidation(userChangeRequest.getUser(), user);
		userChangeRequestService.update(userChangeRequest);
		update(user);
		TasksService.instance().completeTask(tasks);
		//Sending Email
		userChangeRequestSuccessful(userChangeRequest.getUser());
	}
	
	
	public Users populateChangeRequest(UserChangeRequest userChangeRequest)
	{
		Users user=userChangeRequest.getUser();
		user.setFirstName(userChangeRequest.getFirstName());
		user.setMiddleName(userChangeRequest.getMiddleName());
		user.setLastName(userChangeRequest.getLastName());
		user.setNationality(userChangeRequest.getNationality());
		user.setRsaIDNumber(userChangeRequest.getRsaIDNumber());
		user.setPassportNumber(userChangeRequest.getPassportNumber());
		user.setTelNumber(userChangeRequest.getTelNumber());
		user.setCellNumber(userChangeRequest.getCellNumber());
		user.setFaxNumber(userChangeRequest.getFaxNumber());
		user.setEmail(userChangeRequest.getEmail());
		user.setGender(userChangeRequest.getGender());
		user.setEquity(userChangeRequest.getEquity());
		user.setDisabilityStatus(userChangeRequest.getDisabilityStatus());
		user.setTitle(userChangeRequest.getTitle());
		return user;
	}
	
	public void rejectUserChangesTask(UserChangeRequest userChangeRequest, ChangeReason changeReason, Tasks task,
			ArrayList<RejectReasons> selectedRejectReason,Users user) throws Exception {
		UserChangeRequestService userChangeRequestService=new UserChangeRequestService();
		userChangeRequest.setApprovalStatus(ApprovalEnum.Rejected);
		userChangeRequestService.create(userChangeRequest);
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(userChangeRequest.getId(), userChangeRequest.getClass().getName(), selectedRejectReason, user, ConfigDocProcessEnum.USER_DETAILS_CHANGE);
		for(RejectReasonMultipleSelection rrm:rrmList)
		{
			if(rrm !=null)
			{
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
			
		}
		TasksService.instance().completeTask(task);
		userChangeRequestUnsuccessful(userChangeRequest.getUser(),selectedRejectReason);
		
	}
	
	public void userChangeRequestSuccessful(Users user) throws Exception {
		
		String subject = "USER DETAILS CHANGE REQUEST";
		String title="";
		if(user.getTitle() !=null)
		{
			title=user.getTitle().getDescription();
		}
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+title+" "+user.getFirstName()+" "+user.getLastName()+",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please be advised that the merSETA has considered the request to change your user details. "
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We are pleased to inform you that the application was "
				+ "<b>successful</b>. "
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "<b>merSETA Administration</b>"
				+ "</p>";
		
		GenericUtility.sendMail(user.getEmail(), subject, mssg, null);

	}
	
	public void userChangeRequestUnsuccessful(Users user,ArrayList<RejectReasons> selectedRejectReason) throws Exception {
	
		String subject = "USER DETAILS CHANGE REQUEST";
		String title="";
		if(user.getTitle() !=null)
		{
			title=user.getTitle().getDescription();
		}
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+title+" "+user.getFirstName()+" "+user.getLastName()+",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please be advised that the merSETA has considered the request to change your user details."
				
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We regret to advise that the outcome for the request "
				+ "is <b>unsuccessful</b>. "
				+ "</p>"
				

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "<b>Rejection Reason(s):</b><br/>"
				+ ""+convertToHTML(selectedRejectReason)+""
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "To rectify, please log in and go to Profile and update the details and submit. "
				+ "</p>"
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "<b>merSETA Administration</b>"
				+ "</p>";
		
		GenericUtility.sendMail(user.getEmail(), subject, mssg, null);

	}
	
	public String convertToHTML(List<RejectReasons> rejectReasons){		
		String sb ="<ul>"; 
		for (RejectReasons r: rejectReasons){
			sb +="<li>"+r.getDescription() +"</li>";
		}
		sb +="</ul>"; 
		return sb;
	}
	
	public List<Users> findUserByTask(Tasks tasks) throws Exception {
		return dao.findUserByTask(tasks);
	}

	public List<Users> findByDgContractBulk(Boolean value) throws Exception {
		return dao.findByDgContractBulk(value);
	}
	
	public List<Users> findUsersWhereRsaIdIsPopulatedAndRsaCitizenTypeIsNull() throws Exception {
		return dao.findUsersWhereRsaIdIsPopulatedAndRsaCitizenTypeIsNull();
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> allUsersByID(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long userId) throws Exception {
		String hql = "select o from Users o where o.id = :id ";
		filters.put("id", userId);
		return (List<Users>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllUsersByID(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Users o where o.id = :id";
		return dao.countWhere(filters,hql);
	}
	
	public void resolveUserAddresses(Users user) throws Exception{
		if (user != null) {
			if (user.getPostalAddress() != null && user.getPostalAddress().getId() != null) {
				user.setPostalAddress(AddressService.instance().findByKey(user.getPostalAddress().getId()));
			}
			if (user.getResidentialAddress() != null && user.getResidentialAddress().getId() != null) {
				user.setResidentialAddress(AddressService.instance().findByKey(user.getResidentialAddress().getId()));
			}
			if (user.getBirthAddress() != null && user.getBirthAddress().getId() != null) {
				user.setBirthAddress(AddressService.instance().findByKey(user.getBirthAddress().getId()));
			}
		}
	}
	
	public void updateUserBatchInformation(Users user) throws Exception {
		List<IDataEntity> updateList = new ArrayList<>();
		if (user != null && user.getId() != null) {
			updateList.add(user);
		}
		if (user.getPostalAddress() != null && user.getPostalAddress().getId() != null) {
			updateList.add(user.getPostalAddress());
		}
		if (user.getResidentialAddress() != null && user.getResidentialAddress().getId() != null) {
			updateList.add(user.getResidentialAddress());
		}	
		if (!updateList.isEmpty()) {
			dao.updateBatch(updateList);
		}
	}
	
	public List<Users> findUsersWhereEmptySpaceInName() throws Exception {
		return dao.findUsersWhereEmptySpaceInName();
	}
	
	public List<Users> findUsersWithBirthAddresses() throws Exception {
		return dao.findUsersWithBirthAddresses();
	}
	
	public Integer countUsersByEmail(String email) throws Exception {
		return dao.countUsersByEmail(email);
	}
	
	public Integer countUsersByEmailAndNotUserId(String email, Long uid) throws Exception {
		return dao.countUsersByEmailAndNotUserId(email, uid);
	}
	
	public void validateEmailAddressByUser(Users user) throws Exception {
		Integer emailFound = 0;
		if (user.getId() == null) {
			emailFound = countUsersByEmail(user.getEmail());
		} else {
			emailFound = countUsersByEmailAndNotUserId(user.getEmail(), user.getId());
		}
		if (emailFound > 0) {
			throw new Exception("Email already in use, please provide a different email");
		}
	}
	
	/*SETMIS VALIDIATIONS START*/
	public StringBuilder validiateUserInformation(Users user) {
		StringBuilder errors = new StringBuilder("");
		
		if (user.getFirstName() != null && !user.getFirstName().isEmpty()) {
			if (!UserValidationService.instance().validateFirstName(user.getFirstName())) {
				errors.append("Validation Failed For SETMIS First Name <ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided </li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'-</li><li>Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or -</li></ul>");
				errors.append("<br/>");
			}
		}
	
		if (user.getMiddleName() != null && !user.getMiddleName().isEmpty()) {
			if (!UserValidationService.instance().validateMiddleName(user.getMiddleName())) {
				errors.append("Validation Failed For SETMIS Middle Name <ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided </li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`' -</li><li>Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or -</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (user.getLastName() != null && !user.getLastName().isEmpty()) {
			if (!UserValidationService.instance().validateLastName(user.getLastName())) {
				errors.append("Validation Failed For SETMIS Last Name <ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided </li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`' -</li><li>Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or -</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (user.getRsaIDNumber() != null && !user.getRsaIDNumber().trim().isEmpty()) {
			if (!UserValidationService.instance().validateID(user.getRsaIDNumber())) {
				errors.append("Validation Failed For SETMIS ID Number <ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided</li><li>Uppercase value in field may only contain characters 1234567890</li><li>Value must have a length of exactly 13</li><li>Field value may not have characters 0000 from characters 7 to 10</li><li>Field may not have characters 0000 from characters 1 to 4</li><li>Uppercase value in field may not contain characters 1111111111111 or 2222222222222 or 3333333333333 or 4444444444444 or 5555555555555 or 6666666666666 or 7777777777777 or 8888888888888 or 9999999999999</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (user.getPassportNumber() != null && !user.getPassportNumber().trim().isEmpty()) {
			if (!UserValidationService.instance().validateAltID(user.getPassportNumber())) {
				errors.append("Validation Failed For SETMIS Passport Number <ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided</li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ-1234567890@_</li><li>Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or -</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (user.getResidentialAddress() != null) {
			StringBuilder residentialAddressErrors = new StringBuilder("");
			residentialAddressErrors = AddressService.instance().validiateAddressInformation(user.getResidentialAddress());
			if (!residentialAddressErrors.toString().isEmpty()) {
				errors.append("Residential Address Validation Errors:");
				errors.append("<br/>");
				errors.append(residentialAddressErrors.toString());
				errors.append("<br/>");
			}
		}
		
		if (user.getPostalAddress() != null) {
			StringBuilder postalAddressErrors = new StringBuilder("");
			postalAddressErrors = AddressService.instance().validiateAddressInformation(user.getPostalAddress());
			if (!postalAddressErrors.toString().isEmpty()) {
				errors.append("Postal Address Validation Errors:");
				errors.append("<br/>");
				errors.append(postalAddressErrors.toString());
				errors.append("<br/>");
			}
		}
		
		if (user.getPassportNumber() != null && !user.getPassportNumber().trim().isEmpty()) {
			if (!UserValidationService.instance().validateAltID(user.getPassportNumber())) {
				errors.append("Validation Failed For SETMIS Passport Number <ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided</li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ-1234567890@_</li><li>Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or -</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (user.getTelNumber() != null && !user.getTelNumber().trim().isEmpty()) {
			if (!UserValidationService.instance().validateNumber(user.getTelNumber())) {
				errors.append("Validation Failed For SETMIS Tel Number <ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided</li><li>Uppercase value in field may only contain characters 1234567890 ()-</li><li>Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or -</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (user.getCellNumber() != null && !user.getCellNumber().trim().isEmpty()) {
			if (!UserValidationService.instance().validateNumber(user.getCellNumber())) {
				errors.append("Validation Failed For SETMIS Cell Number <ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided</li><li>Uppercase value in field may only contain characters 1234567890 ()-</li><li>Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or -</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (user.getFaxNumber() != null && !user.getFaxNumber().trim().isEmpty()) {
			if (!UserValidationService.instance().validateFaxNumber(user.getFaxNumber())) {
				errors.append("Validation Failed For SETMIS Fax Number <ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided</li><li>Uppercase value in field may only contain characters 1234567890 ()-</li><li>Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or -</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (user.getWorkContactNumber() != null && !user.getWorkContactNumber().trim().isEmpty()) {
			if (!UserValidationService.instance().validateNumber(user.getWorkContactNumber())) {
				errors.append("Validation Failed For SETMIS Work Number <ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided</li><li>Uppercase value in field may only contain characters 1234567890 ()-</li><li>Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or -</li></ul>");
				errors.append("<br/>");
			}
		}
		
		return errors;
	}
	/*SETMIS VALIDIATIONS END*/
	
	// generate uuid temp email
	public String generateUuidEmailAddress() throws Exception {
		return UUID.randomUUID().toString() + "@NSDMS.TEMP";
	}

	public void identifyFieldAlteration(Users user) throws Exception {
		if (user != null) { 
			UserUpdateInfoBean userUpdateInfoBean = new UserUpdateInfoBean();
			
			if (user.getFirstName() == null || user.getFirstName().trim().isEmpty()) {
				userUpdateInfoBean.setFirstName(true);
			}
			if (user.getMiddleName() == null || user.getMiddleName().trim().isEmpty()) {
				userUpdateInfoBean.setMiddleName(true);
			}
			if (user.getLastName() == null || user.getLastName().trim().isEmpty()) {
				userUpdateInfoBean.setLastName(true);
			}
			if (user.getTitle() == null) {
				userUpdateInfoBean.setTitle(true);
			}
			if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
				userUpdateInfoBean.setEmail(true);
			}
			
			if (user.getRsaIDNumber() == null || user.getRsaIDNumber().trim().isEmpty()) {
				userUpdateInfoBean.setRsaIDNumber(true);
			}
			
			if (user.getPassportNumber() == null || user.getPassportNumber().trim().isEmpty()) {
				userUpdateInfoBean.setPassportNumber(true);
			}
			
			if (user.getOfoCodes() == null) {
				userUpdateInfoBean.setOfoCodes(true);
			}
			
			if (user.getCellNumber() == null || user.getCellNumber().trim().isEmpty()) {
				userUpdateInfoBean.setCellNumber(true);
			}
			
			if (user.getTelNumber() == null || user.getTelNumber().trim().isEmpty()) {
				userUpdateInfoBean.setTelNumber(true);
			}
			
			if (user.getNationality() == null) {
				userUpdateInfoBean.setNationality(true);
			}
			
			if (user.getDateOfBirth() == null) {
				userUpdateInfoBean.setDateOfBirth(true);
			}
			
			if (user.getGender() == null) {
				userUpdateInfoBean.setGender(true);
			}
			
			if (user.getEquity() == null) {
				userUpdateInfoBean.setEquity(true);
			}
			
			if (user.getLastSchoolYear()== null) {
				userUpdateInfoBean.setLastSchoolYear(true);
			}
			
			if (user.getPreviousSchools() == null) {
				userUpdateInfoBean.setPreviousSchools(true);
			}
			
			if (user.getFaxNumber() == null || user.getFaxNumber().trim().isEmpty()) {
				userUpdateInfoBean.setPreviousSchools(true);
			}
			
			if (user.getDisability() == null) {
				userUpdateInfoBean.setDisability(true);
				userUpdateInfoBean.setDisbaitiesAssigned(true);
			} else {
				if (user.getId() != null && user.getDisability() == YesNoEnum.Yes) {
					if (UsersDisabilityService.instance().countByKeyUser(user.getId()) == 0) {
						userUpdateInfoBean.setDisbaitiesAssigned(true);
					} else {
						userUpdateInfoBean.setDisbaitiesAssigned(false);
					}
				}
			}
			
			if (user.getEmploymentStatus() == null) {
				userUpdateInfoBean.setEmploymentStatus(true);
			}
			
			if (user.getId() != null) {
				if (UsersLanguageService.instance().countByUserId(user.getId()) == 0) {
					userUpdateInfoBean.setLanguagesAssigned(true);
				} else {
					userUpdateInfoBean.setLanguagesAssigned(false);
				}
			}
			
			
			
			// Address Information if populated
			if (user.getResidentialAddress() != null) {
				AddressUpdateInfoBean residentalAddressUpdate = new AddressUpdateInfoBean();
				AddressService.instance().setTrainingProviderRegsitartionExistingAddressWithTryCatch(user.getResidentialAddress(), residentalAddressUpdate, true);
				user.getResidentialAddress().setAddressUpdateInfoBean(residentalAddressUpdate);
				residentalAddressUpdate = null;
			}
			if (user.getPostalAddress() != null) {
				AddressUpdateInfoBean postalAddressUpdate = new AddressUpdateInfoBean();
				AddressService.instance().setTrainingProviderRegsitartionExistingAddressWithTryCatch(user.getPostalAddress(), postalAddressUpdate, true);
				user.getPostalAddress().setAddressUpdateInfoBean(postalAddressUpdate);
				postalAddressUpdate = null;
			}
	//		if (user.getBirthAddress() != null) {
	//			AddressUpdateInfoBean birthAddressUpdate = new AddressUpdateInfoBean();
	//			AddressService.instance().setTrainingProviderRegsitartionExistingAddressWithTryCatch(user.getBirthAddress(), birthAddressUpdate, true);
	//			user.getBirthAddress().setAddressUpdateInfoBean(birthAddressUpdate);
	//			birthAddressUpdate = null;
	//		}
			user.setUpdateBean(userUpdateInfoBean);
		}
	}
}