package haj.com.service;

import java.util.Date;

import org.jasypt.util.password.StrongPasswordEncryptor;

import haj.com.dao.UsersDAO;
import haj.com.entity.UserPermissions;
//import haj.com.entity.HostingCompany;
import haj.com.entity.Users;
import haj.com.entity.enums.UsersStatusEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.utils.GenericUtility;
import za.co.merseta.nsdms.framework.configuration.NSDMSConfiguration;

// TODO: Auto-generated Javadoc
/**
 * The Class LogonService.
 */
public class LogonService extends AbstractService {

	/** The dao. */
	private UsersDAO dao = new UsersDAO();
	private UserPermissionsService userPermissionsService = new UserPermissionsService();

	/** The password encryptor. */
	private static StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

	/**
	 * Encrypt the users password.
	 *
	 * @param pwd
	 *            the pwd
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public static String encryptPassword(String pwd) throws Exception {
		return passwordEncryptor.encryptPassword(pwd);
	}
	
	public void update(Users entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Process logon via email.
	 *
	 * @param email
	 *            the email
	 * @param inputPassword
	 *            the input password
	 * @return the users
	 * @throws Exception
	 *             the exception
	 */
	public Users logonByEmail(String email, String inputPassword) throws Exception {
		Users u = dao.getUserByEmail(email);
		if (u == null)
			throw new ValidationException("email.not.on.system");

		else if (u.getStatus() == UsersStatusEnum.InActive)
			throw new ValidationException("not.active.profile");
		else if (u.getStatus() == UsersStatusEnum.EmailNotConfrimed)
			throw new ValidationException("not.confirmed.email.check.mail");
		else if (u.getActive() != null && !u.getActive())
			throw new ValidationException("not.active.profile");
		else {
			StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
			if (!passwordEncryptor.checkPassword(inputPassword.trim(), u.getPassword().trim())) {
				throw new ValidationException("invalid.password.profile");
			}
		}
		if (u.getLastLogin() == null) {
			u.setChgPwdNow(true);
		} else {
			u.setLastLogin(new Date());
			u.setChgPwdNow(false);
			dao.update(u);
		}

		return u;
	}

	public Users impersonateByEmailWithoutUpdates(String email, String inputPassword, String emailToImpersonate) throws Exception{
		Users user = logonByEmailWithoutUpdates(email,inputPassword);
		Users impersonatedUser = dao.getUserByEmail(emailToImpersonate);
		if(impersonatedUser != null){
			user = impersonatedUser;
		}
		return user;
	}
	
	public Users logonByEmailWithoutUpdates(String email, String inputPassword) throws Exception {
		Users u = dao.getUserByEmail(email);
		if (u == null)
			throw new ValidationException("email.not.on.system");

		else if (u.getStatus() == UsersStatusEnum.InActive)
			throw new ValidationException("not.active.profile");
		else if (u.getStatus() == UsersStatusEnum.EmailNotConfrimed)
			throw new ValidationException("not.confirmed.email.check.mail");
		else if (u.getActive() != null && !u.getActive())
			throw new ValidationException("not.active.profile");
		else {
			StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
			if (!passwordEncryptor.checkPassword(inputPassword.trim(), u.getPassword().trim())) {
				throw new ValidationException("invalid.password.profile");
			}
		}
		return u;
	}
	
	public Users updateUserInfoAfterLogin(Users u) throws Exception {
		if (u.getLastLogin() == null) {
			u.setChgPwdNow(true);
		} else {
			u.setLastLogin(new Date());
			u.setChgPwdNow(false);
			dao.update(u);
		}
		return u;
	}

	/**
	 * Change a users password.
	 *
	 * @param email
	 *            the email
	 * @param password
	 *            the password
	 * @param newPassword
	 *            the new password
	 * @return the users
	 * @throws Exception
	 *             the exception
	 */
	public Users changePassword(String email, String password, String newPassword) throws Exception {
		Users u = new UsersDAO().getUserByEmail(email);
		if (u == null)
			throw new ValidationException("user.email.not.registered", email);
		else {
			StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
			if (!passwordEncryptor.checkPassword(password.trim(), u.getPassword().trim())) {
				throw new ValidationException("invalid.password.id", email);
			}
		}
		u.setPassword(encryptPassword(newPassword));
		dao.update(u);
		return u;
	}

	/**
	 * Change a users password.
	 *
	 * @param u
	 *            the u
	 * @param pwd
	 *            the pwd
	 * @return the users
	 * @throws Exception
	 *             the exception
	 */
	public Users changePassword(Users u, String pwd) throws Exception {
		u.setPassword(encryptPassword(pwd));
		u.setLastLogin(new java.util.Date());
		dao.update(u);
		return u;
	}

	/**
	 * Change users password.
	 *
	 * @param u
	 *            the u
	 * @return the users
	 * @throws Exception
	 *             the exception
	 */
	public Users changePassword(Users u) throws Exception {
		u.setPassword(encryptPassword(u.getPassword()));
		dao.update(u);
		return u;
	}
	/*
	 * public void notifyUserNewPassword(String email, HostingCompany
	 * hostingCompany) throws Exception { Users ul = dao.getUserByEmail(email); if
	 * (ul == null) throw new Exception("User with email : " + email +
	 * " is not registered on the system! If you typed in the correct username please contact support."
	 * ); notifyUserNewPassword(ul, hostingCompany); }
	 * 
	 * public void notifyUserNewPassword(Users u, HostingCompany hostingCompany)
	 * throws Exception { String pwd = GenericUtility.genPassord();
	 * u.setPassword(encryptPassword(pwd)); dao.update(u); String welcome =
	 * "<p>Dear #NAME#,</p>" + "<br/>" + "<p>This is your new password: <b>" + pwd +
	 * "</b> for email: <b>" + u.getEmail() + "</b></p>" +
	 * "<p>You can change it after you have logged in.</p>" + "<p>Regards</p>" +
	 * "<p>The Compliance Portal team</p>" + "<br/>";
	 * 
	 * 
	 * welcome = welcome.replaceAll("#NAME#", u.getFirstName());
	 * GenericUtility.sendMail(u.getEmail(), "Rock COP password reset", welcome); }
	 */

	/**
	 * Notify a user of their new password via email.
	 *
	 * @param email
	 *            the email
	 * @throws Exception
	 *             the exception
	 */
	public void notifyUserNewPasswordEmail(String email) throws Exception {
		Users ul = dao.getUserByEmail(email);
		if (ul == null)
			throw new ValidationException("user.email.not.registered", email);
		notifyUserNewPassword(ul);
	}

	/**
	 * Notify a user of new password.
	 *
	 * @param u
	 *            the u
	 * @throws Exception
	 *             the exception
	 */
	public void notifyUserNewPassword(Users u) throws Exception {
		String pwd = GenericUtility.genPassord();
		u.setPassword(encryptPassword(pwd));
		u.setLastLogin(null);
		dao.update(u);
		String welcome = "<p>Dear #NAME#,</p>" + "<br/>" + "<p>This is your new password: <b>" + pwd
				+ "</b> for email: <b>" + u.getEmail() + "</b></p>"
				+ "<p>You have to change it when you login.</p>" + "<p>Regards</p>" + "<p>The merSETA team</p>"
				+ "<br/>";
		welcome = welcome.replaceAll("#NAME#", u.getFirstName());
		GenericUtility.sendMail(u.getEmail(), "merSETA NSDMS PASSWORD RESET DETAILS", welcome, null);
	}
	
	public UserPermissions populateUserPermissions(Users u) throws Exception {
		return userPermissionsService.findByUserAndCreate(u);
	}
}
