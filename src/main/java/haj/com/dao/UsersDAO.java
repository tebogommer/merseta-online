package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.TaskUsers;
import haj.com.entity.Tasks;
import haj.com.entity.UserPermissions;
import haj.com.entity.Users;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class UsersDAO.
 */
public class UsersDAO extends AbstractDAO {

	/** The Constant leftJoin. */
	private static final String leftJoin = " " + "left join fetch o.residentialAddress ra left join fetch o.postalAddress pa left join fetch o.disabled left join fetch o.gender left join fetch o.disabilityStatus left join fetch ra.municipality left join fetch pa.municipality" + " ";

	/*
	 * (non-Javadoc)
	 *
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Users.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Users}
	 * @throws Exception the exception
	 * @see Users
	 */
	@SuppressWarnings("unchecked")
	public List<Users> allUsers() throws Exception {
		return (List<Users>) super.getList("select o from Users o");
	}

	/**
	 * Find by GUID.
	 *
	 * @param guid the guid
	 * @return the users
	 * @throws Exception the exception
	 */
	public Users findByGUID(String guid) throws Exception {
		String hql = "select o from Users o" + leftJoin + "where o.emailGuid LIKE :guid ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("guid", guid);
		return (Users) super.getUniqueResult(hql, parameters);
	}

	/**
	 * All users.
	 *
	 * @param from   the from
	 * @param noRows the no rows
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Users> allUsers(Long from, int noRows) throws Exception {
		String hql = "select o from Users o ";
		Map<String, Object> parameters = new HashMap<>();

		return (List<Users>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return Users
	 * @throws Exception the exception
	 * @see Users
	 */
	public Users findByKey(Long id) throws Exception {
		String hql = "select o from Users o left join fetch o.residentialAddress ra left join fetch o.postalAddress pa left join fetch o.disabled left join fetch o.gender left join fetch o.equity left join fetch o.disabilityStatus left join fetch o.nationality left join fetch ra.municipality left join fetch pa.municipality where o.id = :id ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", id);
		return (Users) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Users by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.Users}
	 * @throws Exception the exception
	 * @see Users
	 */
	@SuppressWarnings("unchecked")
	public List<Users> findByName(String description) throws Exception {
		String hql = "select o from Users o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<Users>) super.getList(hql, parameters);
	}

	/**
	 * Find by rsa id.
	 *
	 * @param rsaId the rsa id
	 * @return the users
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public Users findByRsaId(String rsaId) throws Exception {
		String hql = "select o from Users o where o.rsaIDNumber = :rsaId ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("rsaId", "" + rsaId.trim() + "");
		return (Users) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find by passport number.
	 *
	 * @param passportNum the passport num
	 * @return the users
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public Users findByPassportNumber(String passportNum) throws Exception {
		String hql = "select o from Users o where o.passportNumber = :passportNum ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("passportNum", "" + passportNum.trim() + "");
		return (Users) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find by rsa id join address.
	 *
	 * @param rsaId the rsa id
	 * @return the users
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public Users findByRsaIdJoinAddress(String rsaId) throws Exception {
		String hql = "select o from Users o left join fetch o.residentialAddress left join fetch o.postalAddress left join fetch o.disabled left join fetch o.gender left join fetch o.disabilityStatus  where o.rsaIDNumber = :rsaId ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("rsaId", "" + rsaId.trim() + "");
		return (Users) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find by passport number join address.
	 *
	 * @param passportNum the passport num
	 * @return the users
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public Users findByPassportNumberJoinAddress(String passportNum) throws Exception {
		String hql = "select o from Users o left join fetch o.residentialAddress left join fetch o.postalAddress left join fetch o.disabled left join fetch o.gender left join fetch o.disabilityStatus where o.passportNumber = :passportNum ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("passportNum", "" + passportNum.trim() + "");
		return (Users) super.getUniqueResult(hql, parameters);
	}

	/**
	 * All users not confirmed.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Users> allUsersNotConfirmed() throws Exception {
		return (List<Users>) super.getList("select o from Users o where o.emailConfirmDate is null");
	}

	/**
	 * Gets the user by email.
	 *
	 * @param email the email
	 * @return the user by email
	 * @throws Exception the exception
	 */
	public Users getUserByEmail(String email) throws Exception {
		String hql = "select o from Users o where upper(o.email) = :email ";
		// + "and (o.status = " + UsersStatusEnum.Active.ordinal() + ")";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("email", email.toUpperCase());
		return (Users) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Gets the user by email id number passport number.
	 *
	 * @param email          the email
	 * @param idNumber       the id number
	 * @param passportNumber the passport number
	 * @return the user by email id number passport number
	 * @throws Exception the exception
	 */
	public Users getUserByEmailIdNumberPassportNumber(String email, String idNumber, String passportNumber) throws Exception {
		String hql = "select o from Users o where upper(o.email) = :email";
		// + "and (o.status = " + UsersStatusEnum.Active.ordinal() + ")";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("email", email.toUpperCase());

		if (idNumber != null && !idNumber.isEmpty()) {
			hql += " or upper(o.rsaIDNumber) = :idNumber";
			parameters.put("idNumber", idNumber);
		}

		if (passportNumber != null && !passportNumber.isEmpty()) {
			hql += " or upper(o.passportNumber) = :passportNumber";
			parameters.put("passportNumber", passportNumber.toUpperCase());
		}
		return (Users) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Email available.
	 *
	 * @param email the email
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean emailAvailable(String email) throws Exception {
		boolean available = false;
		List<Users> l = getAllUserByEmail(email);
		if (l == null || l.size() == 0)
			available = true;
		return available;
	}

	/**
	 * Gets the all user by email.
	 *
	 * @param email the email
	 * @return the all user by email
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Users> getAllUserByEmail(String email) throws Exception {
		String hql = "select o from Users o where upper(o.email) = :email ";
		// + "and (o.status = " + UsersStatusEnum.Active.ordinal() + ")";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("email", email.toUpperCase());
		return (List<Users>) super.getList(hql, parameters);
	}

	/**
	 * Check email used.
	 *
	 * @param email the email
	 * @param uid   the uid
	 * @return the users
	 * @throws Exception the exception
	 */
	public Users checkEmailUsed(String email, Long uid) throws Exception {
		String hql = "select o from Users o where upper(o.email) = :email and o.id <> :uid";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("email", email.toUpperCase());
		parameters.put("uid", uid);
		return (Users) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Check email used email only.
	 *
	 * @param email the email
	 * @return the users
	 * @throws Exception the exception
	 */
	public Users checkEmailUsedEmailOnly(String email) throws Exception {
		String hql = "select o from Users o where upper(o.email) = :email";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("email", email);
		return (Users) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find user by email GUID.
	 *
	 * @param emailGuid the email guid
	 * @return the users
	 * @throws Exception the exception
	 */
	public Users findUserByEmailGUID(String emailGuid) throws Exception {
		String hql = "select o from Users o where o.emailGuid = :emailGuid";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("emailGuid", emailGuid);
		return (Users) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find admin users.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Users> findAdminUsers() throws Exception {
		String hql = "select o from Users o where o.admin = true";
		return (List<Users>) super.getList(hql);
	}

	@SuppressWarnings("unchecked")
	public List<Users> findByRole(Long roleId) throws Exception {
		String hql = "select o.users from HostingCompanyEmployees o, JobTitle j " + "where o.jobTitle.id = j.id " + "and o.hostingCompany.id = :hostingCompanyId " + "and j.roles.id = :roleId ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("hostingCompanyId", HAJConstants.HOSTING_MERSETA);
		parameters.put("roleId", roleId);
		return (List<Users>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<Users> searchUser(String description) throws Exception {
		String hql = "select o from Users o " + "where o.firstName like :description or " + "o.lastName like :description " + "order by o.firstName ";

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<Users>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<Users> searchUserNotEmployee(String description) throws Exception {
		String hql = "select o from Users o " + "where (" + "o.firstName like :description or " + "o.lastName like :description or " + "o.rsaIDNumber like :description or " + "o.passportNumber like :description" + ") " + "and o.id not in(" + "select x.users.id from HostingCompanyEmployees x" + ")" + "order by o.firstName ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<Users>) super.getList(hql, parameters);
	}

	/*
	 * select o.users from HostingCompanyEmployees o, JobTitle j where o.jobTitle.id
	 * = j.id and o.hostingCompany.id = 1 and j.roles.id = 4
	 */

	public List<?> sortAndFilterSDF(Class<?> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		String hql = "select distinct o from Users o inner join SDFCompany s on o.id = s.sdf.id ";
		if (filters != null) {
			boolean ft = true;
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (ft) {
					hql += " where o." + entry.getKey() + " like " + " :" + hvar;
					ft = false;
				} else {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}

		if (sortField != null) {

			switch (sortOrder) {
			case ASCENDING:
				hql += " order by o." + sortField + " asc ";
				break;
			case DESCENDING:
				hql += " order by o." + sortField + " desc ";
				break;
			default:
				break;
			}
		}
		return getList(hql, filters, startingAt, pageSize);
	}

	public int countSDFUsers(Class<?> entity, Map<String, Object> filters) {
		String hql = "select count (distinct o) from Users o inner join SDFCompany s on o.id = s.sdf.id ";
		if (filters != null) {
			boolean ft = true;
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (ft) {
					hql += " where o." + entry.getKey() + " like " + " :" + hvar;
					ft = false;
				} else {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Users> findUserByTask(Tasks tasks) throws Exception {
		String hql = "select u from Users u "
				+ " inner join TaskUsers tu on u.id = tu.user.id "
				+ " where tu.task.id = :taskID";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("taskID", tasks.getId());
		return (List<Users>) super.getList(hql, parameters);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Users> findByDgContractBulk(Boolean value) throws Exception {
		String hql = "select o from Users o where o.dgContractingBulkApproval = :value ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("value", value);
		return (List<Users>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> findUsersWhereRsaIdIsPopulatedAndRsaCitizenTypeIsNull() throws Exception {
		String hql = "select o from Users o where o.rsaIDNumber is not null and o.rsaCitizenTypeEnum is null";
		return (List<Users>) super.getList(hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> findUsersWhereEmptySpaceInName() throws Exception {
		String hql = "select o from Users o where o.firstName like :value";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("value", "% %");
		return (List<Users>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> findUsersWithBirthAddresses() throws Exception {
		String hql = "select o from Users o where o.birthAddress is not null and o.birthTown is null";
		return (List<Users>) super.getList(hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> allPrimarySdfUsers() throws Exception {
		String hql = "select o from Users o "
				+ " where o.id in (select x.sdf.id from SDFCompany x where x.sdfType.id = :primarySdfID)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("primarySdfID", HAJConstants.PRIMARY_ID);
		return (List<Users>) super.getList(hql, parameters);
	}
	
	public Integer countUsersByEmail(String email) throws Exception {
		String hql = "select count(o) from Users o where upper(o.email) = :email";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("email", email.toUpperCase());
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	public Integer countUsersByEmailAndNotUserId(String email, Long uid) throws Exception {
		String hql = "select count(o) from Users o where upper(o.email) = :email and o.id <> :uid";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("email", email);
		parameters.put("uid", uid);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
}
