package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import haj.com.constants.HAJConstants;
import haj.com.dao.UsersDAO;
import haj.com.entity.Address;
import haj.com.entity.HostingCompany;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.Users;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.UsersStatusEnum;
import haj.com.exceptions.ValidationErrorException;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class RegisterService.
 */
public class RegisterService extends AbstractService {

	/** The dao. */
	private UsersDAO dao = new UsersDAO();

	/** The address service. */
	private AddressService addressService = new AddressService();

	/** The hosting company employees service. */
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();

	/**
	 * Register.
	 *
	 * @param u
	 *            the u
	 * @return the users
	 * @throws Exception
	 *             the exception
	 */
	public Users register(Users u, boolean notifyUser) throws Exception {
		Users t = dao.getUserByEmailIdNumberPassportNumber(u.getEmail(), u.getRsaIDNumber(), u.getPassportNumber());
		if (t == null) {
			String pwd = GenericUtility.genPassord();
			u.setPassword(LogonService.encryptPassword(pwd));
			u.setLastLogin(null);
			u.setStatus(UsersStatusEnum.EmailNotConfrimed);
			u.setEmailGuid(UUID.randomUUID().toString());
			u.setRegistrationDate(new java.util.Date());
			u.setConfirmExpireDate(GenericUtility.add3DaysCountingFromStartDate(getSynchronizedDate()));
			dao.create(u);
			if (notifyUser) {
				notifyUser(u, pwd, HAJConstants.WORKING_DAYS);
			}
		} else {
			u = t;
		}
		return u;
	}
	
	public Users getUserByEmailIdNumberPassportNumber(Users u) throws Exception {
		return dao.getUserByEmailIdNumberPassportNumber(u.getEmail(), u.getRsaIDNumber(), u.getPassportNumber());
	}

	/**
	 * Re register.
	 *
	 * @param u
	 *            the u
	 * @throws Exception
	 *             the exception
	 */
	public void reRegister(Users u) throws Exception {
		String pwd = GenericUtility.genPassord();
		u.setPassword(LogonService.encryptPassword(pwd));
		u.setLastLogin(null);
		u.setStatus(UsersStatusEnum.EmailNotConfrimed);
		u.setEmailGuid(UUID.randomUUID().toString());
		u.setRegistrationDate(new java.util.Date());
		u.setConfirmExpireDate(GenericUtility.add3DaysCountingFromStartDate(getSynchronizedDate()));
		dao.update(u);
		notifyUser(u, pwd, null);
	}

	/**
	 * Register with exception.
	 *
	 * @param u
	 *            the u
	 * @return the users
	 * @throws Exception
	 *             the exception
	 */
	public Users registerWithException(Users u) throws Exception {
		if (dao.getUserByEmailIdNumberPassportNumber(u.getEmail(), u.getRsaIDNumber(), u.getPassportNumber()) != null) throw new Exception("Another user is already registered with the given details");
		String pwd = GenericUtility.genPassord();
		u.setPassword(LogonService.encryptPassword(pwd));
		u.setLastLogin(null);
		u.setStatus(UsersStatusEnum.EmailNotConfrimed);
		u.setEmailGuid(UUID.randomUUID().toString());
		u.setRegistrationDate(new java.util.Date());
		u.setConfirmExpireDate(GenericUtility.add3DaysCountingFromStartDate(getSynchronizedDate()));
		dao.create(u);
		notifyUser(u, pwd, null);
		return u;
	}

	public void checkUserException(Users u) throws Exception {
		if (dao.getUserByEmailIdNumberPassportNumber(u.getEmail(), u.getRsaIDNumber(), u.getPassportNumber()) != null) throw new Exception("Another user is already registered with the given details");
	}

	/**
	 * Approve.
	 *
	 * @param u
	 *            the u
	 * @param notifyUser
	 *            the notify user
	 * @throws Exception
	 *             the exception
	 */
	public void approve(Users u, boolean notifyUser) throws Exception {
		String pwd = GenericUtility.genPassord();
		u.setPassword(LogonService.encryptPassword(pwd));
		u.setLastLogin(null);
		u.setStatus(UsersStatusEnum.Active);
		dao.update(u);
		if (notifyUser) {
			notifyUser(u, pwd, null);
		}
	}

	/**
	 * registers user.
	 *
	 * @author TechFinium
	 * @param user
	 *            the user
	 * @param address
	 *            the address
	 * @param postalAddress
	 *            the postal address
	 * @return a list of {@link haj.com.entity.HostingCompanyEmployees}
	 * @throws Exception
	 *             the exception
	 * @see HostingCompanyEmployees
	 */
	public void register(Users user, Address address, Address postalAddress) throws Exception {

		List<IDataEntity> entityList = new ArrayList<IDataEntity>();
		register(user, true);
		entityList.add(user);
		addressService.lookupLongitudeLatitude(address);
		entityList.add(address);
		entityList.add(postalAddress);
		notifyUser(user);

	}

	/**
	 * Notify user.
	 *
	 * @param u
	 *            the u
	 * @throws Exception
	 *             the exception
	 */
	public void notifyUser(Users u) throws Exception {
		// welcome message to the user

		String welcome = "<p>Dear #NAME#,</p>" + "<br/>" + "<p>Welcome to merSETA</p>" + "<p>Please " + "<a href=\"" + HAJConstants.PL_LINK + "confirmemail.jsf?uuid=" + u.getEmailGuid().trim() + "\"><b>CONFIRM</b></a> your email address.</p>" + "<p>After you have confirmed your email address you will then be verified, once verification has been completed you will receive a email and then be able to sign in.</p>"
				+ "<p>Please Note: Sign ups after 6pm will be processed at 9am of the next working day.</p>" + "<p>Regards</p>" + "<p>The merSETA team</p>" + "<br/>";
		welcome = welcome.replaceAll("#NAME#", u.getFirstName());
		GenericUtility.sendMail(u.getEmail(), "Welcome to merSETA", welcome, null);
	}

	/**
	 * Confirm email.
	 *
	 * @param u
	 *            the u
	 * @throws Exception
	 *             the exception
	 */
	/*
	 * public void notifyUser(ClientUsers u, String pwd) throws Exception { //
	 * welcome message to the user
	 * 
	 * String welcome = "<p>Dear #NAME#,</p>"+ "<br/>" +
	 * "<p>You have been invited to joining TechFINIUM LTD Q/A</p>"+ "<p>Please "+
	 * "<a href=\""+HAJConstants.PL_LINK +
	 * "confirmemail.jsf?uuid="+u.getUsers().getEmailGuid().trim()+
	 * "\">confirm</a> your email address.</p>"+ "<p>Your username is: <b>"
	 * +u.getUsers().getUsername()+"</b> and your password is: <b>"+pwd+ "</b></p>"+
	 * "<p>You can change it after you have logged in.</p>"+ "<p>Regards</p>"+
	 * "<p>The TechFINIUM LTD team</p>"+ "<br/>"; welcome =
	 * welcome.replaceAll("#NAME#",u.getUsers().getFirstName());
	 * GenericUtility.sendMail(u.getUsers().getEmail(),
	 * "Complete infoFINIUM registration", welcome); }
	 */
	public void confirmEmail(Users u) throws Exception {
		u.setEmailConfirmDate(new java.util.Date());
		u.setStatus(UsersStatusEnum.Active);
		dao.update(u);

	}

	/**
	 * Notify user.
	 *
	 * @param u
	 *            the u
	 * @param pwd
	 *            the pwd
	 * @throws Exception
	 *             the exception
	 */
	public void notifyUser(Users u, String pwd, String workingDays) throws Exception {

		String workingHoursMssg = "";
		if (workingDays != null) {
			workingHoursMssg = "<p>Your application may take " + workingDays + " working day(s) to be processed</p>";
		}

		String welcome = "<p>Dear #NAME#,</p><br/>" + "<p>Please <a href=\"" + HAJConstants.PL_LINK + "confirmemail.jsf?uuid=" + u.getEmailGuid().trim() + "\"> confirm</a> your email address.</p>" + "<p>Your email is: <b>" + u.getEmail() + "</b> and your password is: <b>" + pwd + "</b></p>" + "<p>You can change it after you have logged in.</p>" + "<p>Please note that the link will expire after 72 hours.</p>" + workingHoursMssg + "<p>Regards</p>" + "<p>The merSETA team</p>" + "<br/>";

		welcome = welcome.replaceAll("#NAME#", u.getFirstName());
		GenericUtility.sendMail(u.getEmail(), "merSETA NSDMS NEW REGISTRATION", welcome, null);
	}

	/**
	 * registers user with basic details and against HostingCompanyEmployees.
	 *
	 * @author TechFinium
	 * @param u
	 *            the u
	 * @param hc
	 *            the hc
	 * @param pwd
	 *            the pwd
	 * @throws Exception
	 *             the exception
	 * @see HostingCompanyEmployees
	 */
	public void registerUserHostCompany(Users u, HostingCompany hc, String pwd) throws Exception {
		HostingCompanyEmployees hcEmployee = new HostingCompanyEmployees();
		hcEmployee.setHostingCompany(hc);
		hcEmployee.setUsers(u);
		hostingCompanyEmployeesService.create(hcEmployee);
		notifyUser(u, hc, pwd);
	}

	/**
	 * This is the intial registeration email sent to the user.
	 *
	 * @author TechFinium
	 * @param u
	 *            the u
	 * @param hc
	 *            the hc
	 * @param pwd
	 *            the pwd
	 * @throws Exception
	 *             the exception
	 * @see HostingCompanyEmployees
	 */
	public void notifyUser(Users u, HostingCompany hc, String pwd) throws Exception {
		String welcome = "<p>Dear #NAME#,</p>" + "<br/>" + "<p>Thank you for joining merSETA</p>" + "<p>Please " + "<a href=\"" + HAJConstants.PL_LINK + "confirmemail.jsf?uuid=" + u.getEmailGuid().trim() + "\">confirm</a> your email address.</p><p>password: " + pwd + "</p>" + "<p>Regards</p>" + "<p>merSETA team</p>" + "<br/>";
		welcome = welcome.replaceAll("#NAME#", u.getFirstName());
		GenericUtility.sendMail(u.getEmail(), "COMPLETE REGISTRATION ON merSETA NSDMS", welcome, hc.getLogo());
	}

	/**
	 * registers user with basic details and against HostingCompanyEmployees.
	 *
	 * @author TechFinium
	 * @param u
	 *            the u
	 * @param pwd
	 *            the pwd
	 * @throws Exception
	 *             the exception
	 * @see HostingCompanyEmployees
	 */
	public void registerLearner(Users u, String pwd) throws Exception {
		notifyLearnerRegistered(u, pwd);
	}

	/**
	 * This is the intial registeration email sent to the user.
	 *
	 * @author TechFinium
	 * @param u
	 *            the u
	 * @param pwd
	 *            the pwd
	 * @throws Exception
	 *             the exception
	 * @see HostingCompanyEmployees
	 */
	public void notifyLearnerRegistered(Users u, String pwd) throws Exception {
		String welcome = "<p>Dear #NAME#,</p>" + "<br/>" + "<p>Thank you for joining merSETA as a Learner</p>" + "<p>Please " + "<a href=\"" + HAJConstants.PL_LINK + "confirmemail.jsf?uuid=" + u.getEmailGuid().trim() + "\">confirm</a> your email address.</p><p>password: " + pwd + "</p>" + "<p>Regards</p>" + "<p>merSETA team</p>" + "<br/>";
		welcome = welcome.replaceAll("#NAME#", u.getFirstName());
		GenericUtility.sendMail(u.getEmail(), "COMPLETE REGISTRATION ON merSETA NSDMS", welcome, "");
	}

	public void resendChecks(Users dbUser) throws Exception {
		if (StringUtils.isBlank(dbUser.getEmail())) throw new Exception("You are not registered on the system. Please register");
		if (dbUser.getStatus() == null || dbUser.getStatus() != UsersStatusEnum.EmailNotConfrimed) throw new Exception("Your email address has already been confirmed.");
//		if (dbUser.getEmailConfirmDate() != null) throw new Exception("Your email address has already been confirmed.");
	}

	public void validateResend(Users dbUser, String email) throws ValidationErrorException, Exception {
		if (dbUser.getEmail().trim().equalsIgnoreCase(email.trim())) {
			reRegister(dbUser);
		} else {
			TasksService.instance().findFirstInProcessAndCreateTask("User requested a re-send of verification email but email addresses do not match", dbUser, dbUser.getId(), dbUser.getClass().getName(), true, ConfigDocProcessEnum.RESEND_VERIFICATION_EMAIL_NOT_MATCH, null, null);
			throw new ValidationErrorException("Someone from helpdesk will be in contact shortly to assist you.");
		}

	}

}
