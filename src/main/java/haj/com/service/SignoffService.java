package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.primefaces.model.SortOrder;

import haj.com.dao.SignoffDAO;
import haj.com.entity.DgVerification;
import haj.com.entity.MgVerification;
import haj.com.entity.Signoff;
import haj.com.entity.SiteVisitReport;
import haj.com.entity.Tasks;
import haj.com.entity.TempSignoff;
import haj.com.entity.Users;
import haj.com.entity.WspDispute;
import haj.com.entity.enums.SignoffByEnum;
import haj.com.entity.lookup.Roles;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.RolesService;
import haj.com.utils.GenericUtility;

public class SignoffService extends AbstractService {
	
	/** The dao. */
	private SignoffDAO dao = new SignoffDAO();
	
	/** The password encryptor. */
	private static StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
	
	private static SignoffService signoffService;
	public static synchronized SignoffService instance() {
		if (signoffService == null) {
			signoffService = new SignoffService();
		}
		return signoffService;
	}

	/**
	 * Get all Signoff
	 * 
	 * @author TechFinium
	 * @see Signoff
	 * @return a list of {@link haj.com.entity.Signoff}
	 * @throws Exception
	 *             the exception
	 */
	public List<Signoff> allSignoff() throws Exception {
		return dao.allSignoff();
	}

	/**
	 * Create or update Signoff.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Signoff
	 */
	public void create(Signoff entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	public void saveSignOff(Signoff entity, Tasks tasks) throws Exception {
		entity.setCompleted(true);
		entity.setSignOffDate(getSynchronizedDate());
		this.dao.update(entity);
		TasksService.instance().completeTask(tasks);
	}

	/**
	 * Update Signoff.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Signoff
	 */
	public void update(Signoff entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete Signoff.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Signoff
	 */
	public void delete(Signoff entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.Signoff}
	 * @throws Exception
	 *             the exception
	 * @see Signoff
	 */
	public Signoff findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	public Signoff findByGUID(String guid) throws Exception {
		return dao.findByGUID(guid);
	}
	
	public Signoff findByGUIDnotAccepted(String guid) throws Exception {
		return dao.findByGUIDnotAccepted(guid);
	}
	
	public Integer countByGUID(String guid) throws Exception {
		return dao.countByGUID(guid);
	}

	/**
	 * Find Signoff by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.Signoff}
	 * @throws Exception
	 *             the exception
	 * @see Signoff
	 */
	public List<Signoff> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load Signoff
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.Signoff}
	 * @throws Exception
	 *             the exception
	 */
	public List<Signoff> allSignoff(int first, int pageSize) throws Exception {
		return dao.allSignoff(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of Signoff for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the Signoff
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(Signoff.class);
	}

	/**
	 * Lazy load Signoff with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            Signoff class
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
	 * @return a list of {@link haj.com.entity.Signoff} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Signoff> allSignoff(Class<Signoff> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<Signoff>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of Signoff for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            Signoff class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the Signoff entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<Signoff> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public MgVerification resolveMgSignOff(MgVerification mgVerification) throws Exception {
		mgVerification.setSignOffs(findByVerifivcation(mgVerification));
		return mgVerification;
	}

	public List<Signoff> findByVerifivcation(MgVerification mgVerification) throws Exception {
		return dao.findByVerifivcation(mgVerification);
	}

	public List<Signoff> findByDGVerifivcation(DgVerification dgVerification) throws Exception {
		return dao.findByDGVerifivcation(dgVerification);
	}
	
	public List<Signoff> findByWspDispute(WspDispute wspDispute) throws Exception {
		return dao.findByWspDispute(wspDispute);
	}
	
	public List<Signoff> findByTargetKeyAndClass(Long targetKey, String targetClass) throws Exception {
		return dao.findByTargetKeyAndClass(targetKey, targetClass);
	}
	
	public List<Signoff> findByTargetKeyAndClassAndLastest(Long targetKey, String targetClass, Boolean lastestSignOff) throws Exception {
		return dao.findByTargetKeyAndClassAndLastest(targetKey, targetClass, lastestSignOff);
	}
	public List<Signoff> findByTargetKeyAndClassAndLastest(Long targetKey, String targetClass, SignoffByEnum signoffByEnum) throws Exception {
		return dao.findByTargetKeyAndClassAndLastest(targetKey, targetClass,signoffByEnum);
	}
	public Signoff findByTargetKeyAndClassAndSignoffByEnum(Long targetKey, String targetClass, SignoffByEnum signoffByEnum) throws Exception {
		return dao.findByTargetKeyAndClassAndSignoffByEnum(targetKey, targetClass,signoffByEnum);
	}
	public List<Signoff> findByTargetKeyAndClassAndLastest(Long targetKey, String targetClass, Boolean lastestSignOff, SignoffByEnum signoffByEnum) throws Exception {
		return dao.findByTargetKeyAndClassAndLastest(targetKey, targetClass, lastestSignOff,signoffByEnum);
	}
	public Signoff findSingleByTargetKeyAndClassAndLastest(Long targetKey, String targetClass, Boolean lastestSignOff, SignoffByEnum signoffByEnum) throws Exception {
		return dao.findSingleByTargetKeyAndClassAndLastest(targetKey, targetClass, lastestSignOff,signoffByEnum);
	}
	public List<Signoff> findByTargetKeyAndClassTempUsers(Long targetKey, String targetClass) throws Exception {
		return dao.findByTargetKeyAndClassTempUsers(targetKey, targetClass);
	}
	
	public List<Signoff> findByTargetKeyAndClassTempUsersLastest(Long targetKey, String targetClass, Boolean lastestSignOff) throws Exception {
		return dao.findByTargetKeyAndClassTempUsersLastest(targetKey, targetClass, lastestSignOff);
	}
	
	public List<Signoff> findByTargetKeyAndClassNsdmsUsers(Long targetKey, String targetClass) throws Exception {
		return dao.findByTargetKeyAndClassNsdmsUsers(targetKey, targetClass);
	}
	
	public List<Signoff> findByTargetKeyAndClassNsdmsUsersLastest(Long targetKey, String targetClass, Boolean lastestSignOff) throws Exception {
		return dao.findByTargetKeyAndClassNsdmsUsersLastest(targetKey, targetClass, lastestSignOff);
	}
	
	public List<Signoff> findByTargetKeyAndClassAndLastest(Long targetKey, String targetClass, boolean lastestSignoff) throws Exception {
		return dao.findByTargetKeyAndClassAndLastest(targetKey, targetClass, lastestSignoff);
	}
	
	public List<Signoff> findByTargetKeyAndClassAndLastestAndRole(Long targetKey, String targetClass, boolean lastestSignoff, Long roleId) throws Exception {
		return dao.findByTargetKeyAndClassAndLastestAndRole(targetKey, targetClass, lastestSignoff, roleId);
	}
	
	public String getInitialsOfSignOffs(Long targetKey, String targetClass) throws Exception {
		UsersService userService = new UsersService();
		List<Signoff> signOffs = findByTargetKeyAndClass(targetKey, targetClass);
		String inStringForm = "";
		for (Signoff signoff : signOffs) {
			if (signoff.getUser() != null && signoff.getUser().getId() != null) {
				Users user = userService.findByKey(signoff.getUser().getId());
				inStringForm = inStringForm + user.getFirstName().trim().charAt(0) + user.getLastName().trim().charAt(0) +"<br/>";
				user = null;
			}
		}
		signOffs = null;
		return inStringForm;
	}
	
	public List<Signoff> findByTargetKeyAndClassWithInfo(Long targetKey, String targetClass) throws Exception {
		return populateInfo(dao.findByTargetKeyAndClass(targetKey, targetClass));
	}

	private List<Signoff> populateInfo(List<Signoff> list) {
		for(Signoff signoff: list) {
			pupulateDesignation(signoff);
		}
		return list;
	}

	private void pupulateDesignation(Signoff signoff) {
		
	}
	
	/*
	 * Old version with no encryption
	 */
	public void generateNewOneTimePin(Signoff signoff) throws Exception{
		Integer otp = GenericUtility.generateFourNumberPin();
		signoff.setOneTimePassword(otp.toString());
		if (signoff.getId() != null) {
			update(signoff);
		} else {
			create(signoff);
		}
	}
	
	/*
	 * Version Two with encryption
	 */
	public Integer generateAndReturnNewOneTimePin(Signoff signoff) throws Exception{
		Integer otp = GenericUtility.generateFourNumberPin();
		signoff.setOneTimePassword(encryptPassword(otp.toString()));
		if (signoff.getId() != null) {
			update(signoff);
		} else {
			create(signoff);
		}
		return otp;
	}
	
	/**
	 * Encrypt the otp
	 *
	 * @param otp
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public static String encryptPassword(String otp) throws Exception {
		return passwordEncryptor.encryptPassword(otp);
	}

	public void validateSignoff(List<Signoff> signOffList, Users user) throws Exception{
		boolean signOffRequired = false;
		for (Signoff signoff : signOffList) {
			if (signoff.getUser().getId().equals(user.getId()) && (signoff.getAccept() == null || !signoff.getAccept())) {
				signOffRequired = true;
				break;
			}
		}
		if (signOffRequired) {
			throw new Exception("Please Sign Off Before Proceeding.");
		}
	}
	
	public void validateSignoffUsers(List<Signoff> signOffList, Users user) throws Exception{
		boolean userRequired = false;
		for (Signoff signoff : signOffList) {
			if (signoff.getUser() == null ) {
				userRequired = true;
				break;
			}
		}
		if (userRequired) {
			throw new Exception("Please Assign A User For Sign Off");
		}
	}
	
	public Signoff updateSignOffGuid(Signoff signoff) throws Exception {
		Integer counter = 1;
		String guid = "";
		while (counter != 0) {
			guid = UUID.randomUUID().toString();
			counter = SignoffService.instance().countByGUID(guid);
		}
		signoff.setGuid(guid.trim());
		SignoffService.instance().update(signoff);
		return findByKey(signoff.getId());
	}
	
	public Signoff newSignOffToBeCreated(Users user, String descriptionOfSignOff, Boolean accepted, String targetClass, Long targetKey, Long roleId, Boolean externalSignOff, Boolean canChangeSignOffUser) throws Exception {
		Signoff signoff = null;
		if (user != null && user.getId() != null) {
			signoff = new Signoff(user, descriptionOfSignOff);
		} else {
			signoff = new Signoff();
			signoff.setSignOffTitle(descriptionOfSignOff);
		}
		signoff.setTargetClass(targetClass);
		signoff.setTargetKey(targetKey);
		if (accepted) {
			signoff.setAccept(true);
			signoff.setSignOffDate(getSynchronizedDate());
		} else {
			signoff.setAccept(false);
		}
		signoff.setExternalUserSignoff(externalSignOff);
		signoff.setLastestSignoff(true);
		signoff.setCanChangeSignOffUser(canChangeSignOffUser);
		signoff.setGuid(UUID.randomUUID().toString());
		if (roleId != null) {
			Roles role = RolesService.instance().findByKey(roleId);
			if (role != null && role.getId() != null) {
				signoff.setRole(role);
			}
		}
		Integer otp = GenericUtility.generateFourNumberPin();
		signoff.setOneTimePassword(encryptPassword(otp.toString()));
		signoff.setExpiryDate(GenericUtility.addDaysToDate(getSynchronizedDate(), 5));
		return signoff;
	} 
	
	public void createSignOff(Users user, String descriptionOfSignOff, Boolean accepted, String targetClass, Long targetKey, Long roleId, Boolean externalSignOff, Boolean canChangeSignOffUser) throws Exception {
		List<IDataEntity> createList = new ArrayList<>();
		Signoff signoff = null;
		if (user != null && user.getId() != null) {
			signoff = new Signoff(user, descriptionOfSignOff);
		} else {
			signoff = new Signoff();
			signoff.setSignOffTitle(descriptionOfSignOff);
		}
		signoff.setTargetClass(targetClass);
		signoff.setTargetKey(targetKey);
		if (accepted) {
			signoff.setAccept(true);
			signoff.setSignOffDate(getSynchronizedDate());
		} else {
			signoff.setAccept(false);
		}
		signoff.setExternalUserSignoff(externalSignOff);
		signoff.setLastestSignoff(true);
		signoff.setCanChangeSignOffUser(canChangeSignOffUser);
		signoff.setGuid(UUID.randomUUID().toString());
		if (roleId != null) {
			Roles role = RolesService.instance().findByKey(roleId);
			if (role != null && role.getId() != null) {
				signoff.setRole(role);
			}
		}
		Integer otp = GenericUtility.generateFourNumberPin();
		signoff.setOneTimePassword(encryptPassword(otp.toString()));
		signoff.setExpiryDate(GenericUtility.addDaysToDate(getSynchronizedDate(), 5));
		createList.add(signoff);
		if (!createList.isEmpty()) {
			dao.createBatch(createList);
		}
	} 
	
	public void testExternalUserEmailSent(Signoff signoff) throws Exception{
		if (signoff.getExternalUserSignoff()) {
			if (signoff.getTempSignoff() == null || signoff.getTempSignoff().getId() == null) {
				throw new Exception("Unable to locate external user information, contact support!");
			}else {
				if (signoff.getTempSignoff().getEmail() != null && !signoff.getTempSignoff().getEmail().trim().isEmpty()) {
					String subject = "Test Notification";
					String msg = "Test Notification";
					GenericUtility.sendMail(signoff.getTempSignoff().getEmail().trim(), subject, msg, null);
				} else {
					throw new Exception("Unable to locate external user email address, contact support!");
				}
			}
		} else {
			throw new Exception("Sign off is not an extenral user, functionlaity not required.");
		}
	}
}