package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Column;
import javax.persistence.Enumerated;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.Company;
import haj.com.entity.CompanyUsers;
import haj.com.entity.Users;
import haj.com.entity.enums.AssessorModType;
import haj.com.entity.enums.AssessorModeratorAppTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyUsersDAO.
 */
public class CompanyUsersDAO extends AbstractDAO {

	/** The Constant leftJoinsNoVar. */
	private static final String leftJoinsNoVar = " " + "left join fetch o.company left join fetch o.user" + " ";

	/** The Constant leftJoinsVar. */
	private static final String leftJoinsVar = " " + "left join fetch o.company z left join fetch o.user x" + " ";

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
	 * Get all CompanyUsers.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.CompanyUsers}
	 * @throws Exception
	 *             global exception
	 * @see CompanyUsers
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUsers> allCompanyUsers() throws Exception {
		return (List<CompanyUsers>) super.getList("select o from CompanyUsers o" + leftJoinsNoVar);
	}

	/**
	 * Find users type of company.
	 *
	 * @param userType
	 *            the user type
	 * @param companyId
	 *            the company id
	 * @return the list
	 */
	public List<CompanyUsers> findUsersTypeOfCompany(ConfigDocProcessEnum userType, Long companyId) {
		String hql = "select o from CompanyUsers o" + leftJoinsVar + "left join fetch x.residentialAddress left join fetch x.postalAddress left join fetch o.userResponsibility ur where o.company.id = :companyId and o.companyUserType = :userType";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("userType", userType);
		return (List<CompanyUsers>) super.getList(hql, parameters);
	}
	
	/**
	 * Find users type of company.
	 * 
	 * @param companyId
	 *            the company id
	 * @return the list
	 */
	public List<CompanyUsers> findCompanyContactPerson(Long companyId) {
		String hql = "select o from CompanyUsers o" + leftJoinsVar + " where o.company.id = :companyId ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		return (List<CompanyUsers>) super.getList(hql, parameters);
	}
	
	/*
	 * public List<CompanyUsers> findCompanyContactPerson(Long companyId) { String
	 * hql = "select o from CompanyUsers o" + leftJoinsVar +
	 * " where o.company.id = :companyId and o.userResponsibility.id = :contactPersonID"
	 * ; Map<String, Object> parameters = new Hashtable<String, Object>();
	 * parameters.put("companyId", companyId); parameters.put("contactPersonID",
	 * HAJConstants.CONTACT_PERSON_ID); return (List<CompanyUsers>)
	 * super.getList(hql, parameters); }
	 */

	/**
	 * Get all CompanyUsers between from and noRows.
	 *
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @return a list of {@link haj.com.entity.CompanyUsers}
	 * @throws Exception
	 *             global exception
	 * @see CompanyUsers
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUsers> allCompanyUsers(Long from, int noRows) throws Exception {
		String hql = "select o from CompanyUsers o" + leftJoinsNoVar;
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<CompanyUsers>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.CompanyUsers}
	 * @throws Exception
	 *             global exception
	 * @see CompanyUsers
	 */
	public CompanyUsers findByKey(Long id) throws Exception {
		String hql = "select o from CompanyUsers o" + leftJoinsNoVar + "where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (CompanyUsers) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find object by companyUsersID.
	 *
	 * @author TechFinium
	 * @param CompanyUsers
	 *            the CompanyUsers
	 * @return a {@link haj.com.entity.CompanyUsers}
	 * @throws Exception
	 *             global exception
	 * @see CompanyUsers
	 */
	public CompanyUsers findByCompanyUser(Long id) throws Exception {
		String joins = " left join fetch o.company z " + "left join fetch o.user x " + "left join fetch x.residentialAddress ra " + "left join fetch ra.municipality " + "left join fetch x.postalAddress pa " + "left join fetch pa.municipality " + "left join fetch o.position p " + "left join fetch o.userResponsibility ur ";

		String hql = "select o from CompanyUsers o" + joins + "where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (CompanyUsers) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find by name.
	 *
	 * @param description
	 *            the description
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUsers> findByName(String description) throws Exception {
		String hql = "select o from CompanyUsers o left join fetch o.company x left join fetch o.user z left join fetch o.userResponsibility ur where z.firstName like :description or x.companyName like :description";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<CompanyUsers>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyUsers> findByUserAndName(String description, Long userID) throws Exception {
		String hql = "select o from CompanyUsers o where o.user.id = :userID and o.company.companyName like :description";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		parameters.put("userID", userID);
		return (List<CompanyUsers>) super.getList(hql, parameters);
	}

	/**
	 * Check email used email only.
	 *
	 * @param email
	 *            the email
	 * @return the users
	 * @throws Exception
	 *             the exception
	 */
	public Users checkEmailUsedEmailOnly(String email) throws Exception {
		String hql = "select o from Users o where upper(o.email) = :email";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("email", email);
		return (Users) super.getUniqueResult(hql, parameters);
	}
	
	public Users checkEmailUsedWithUserId(String email, Long uid) throws Exception {
		String hql = "select o from Users o where upper(o.email) = :email and o.id <> :uid";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("email", email.toUpperCase());
		parameters.put("uid", uid);
		return (Users) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find object by Users_id and Company_id.
	 *
	 * @author TechFinium
	 * @param companyId
	 *            the company id
	 * @param userId
	 *            the user id
	 * @return a {@link haj.com.entity.CompanyUsers}
	 * @throws Exception
	 *             global exception
	 * @see CompanyUsers
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUsers> findByUserAndCompany(Long companyId, Long userId) throws Exception {
		String hql = "select o from CompanyUsers o left join fetch o.company c left join fetch o.user u left join fetch o.position p where c.id = :companyId and u.id = :userId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("userId", userId);
		return (List<CompanyUsers>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyUsers> findByUserAndCompanyType(Long companyId, Long userId,ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		String hql = "select o from CompanyUsers o left join fetch o.company c left join fetch o.user u left join fetch o.position p where c.id = :companyId and u.id = :userId and o.companyUserType = :configDocProcessEnum";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("userId", userId);
		parameters.put("configDocProcessEnum", configDocProcessEnum);
		return (List<CompanyUsers>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyUsers> findByUserAndCompanyAndTypeAndDesignation(Long companyId, Long userId,ConfigDocProcessEnum configDocProcessEnum,Long desinationID) throws Exception {
		String hql = "select o from CompanyUsers o left join fetch o.company c left join fetch o.user u left join fetch o.position p where c.id = :companyId and u.id = :userId and o.companyUserType = :configDocProcessEnum and o.designation.id =:designation";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("userId", userId);
		parameters.put("configDocProcessEnum", configDocProcessEnum);
		parameters.put("designation", desinationID);
		return (List<CompanyUsers>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyUsers> findByUserAndCompanyAndTypeAndDesignationIsNotNull(Long companyId, Long userId,ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		String hql = "select o from CompanyUsers o left join fetch o.company c left join fetch o.user u left join fetch o.position p where c.id = :companyId and u.id = :userId and o.companyUserType = :configDocProcessEnum and o.designation is not null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("userId", userId);
		parameters.put("configDocProcessEnum", configDocProcessEnum);
		return (List<CompanyUsers>) super.getList(hql, parameters);
	}
	@SuppressWarnings("unchecked")
	public List<CompanyUsers> findByUserAndCompanyAndTypeAndAssessorModType(Long companyId, Long userId,ConfigDocProcessEnum configDocProcessEnum,AssessorModType assessorModType) throws Exception {
		String hql = "select o from CompanyUsers o left join fetch o.company c left join fetch o.user u left join fetch o.position p where c.id = :companyId and u.id = :userId and o.companyUserType = :configDocProcessEnum and o.assessorModType =:assessorModType";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("userId", userId);
		parameters.put("configDocProcessEnum", configDocProcessEnum);
		parameters.put("assessorModType", assessorModType);
		return (List<CompanyUsers>) super.getList(hql, parameters);
	}
	

	/**
	 * Find by user.
	 *
	 * @param userId
	 *            the user id
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUsers> findByUser(Long userId) throws Exception {
		String hql = "select o from CompanyUsers o left join fetch o.company x where o.user.id = :userId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("userId", userId);
		return (List<CompanyUsers>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyUsers> find() throws Exception {
		String hql = "select o from CompanyUsers o left join fetch o.company x ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<CompanyUsers>) super.getList(hql, parameters);
	}

	/**
	 * Find by company type.
	 *
	 * @param companyId
	 *            the company id
	 * @param configDocProcessEnum
	 *            the config doc process enum
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUsers> findByCompanyType(Long companyId, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		String hql = "select o from CompanyUsers o" + leftJoinsNoVar + "where o.company.id = :companyId and o.companyUserType = :configDocProcessEnum";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("configDocProcessEnum", configDocProcessEnum);
		return (List<CompanyUsers>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<Company> findByUserType(Long companyId, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		String hql = "select distinct o.company from CompanyUsers o where o.user.id = :userID and o.companyUserType = :configDocProcessEnum";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("userID", companyId);
		parameters.put("configDocProcessEnum", configDocProcessEnum);
		return (List<Company>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public Long findByUserTypeCount(Long companyId, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		String hql = "select count(distinct o.company) from CompanyUsers o where o.user.id = :userID and o.companyUserType = :configDocProcessEnum";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("userID", companyId);
		parameters.put("configDocProcessEnum", configDocProcessEnum);
		return (Long) super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public Long findByUserTypeCountAndCanRegisterMentor(Long userId, ConfigDocProcessEnum configDocProcessEnum, Boolean registerMentors) throws Exception {
		String hql = "select count(distinct o.company) from CompanyUsers o where o.user.id = :userID and o.companyUserType = :configDocProcessEnum and o.user.id in (select k.sdf.id from SDFCompany k where  k.sdfType.registerMentors = :registerMentors)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("userID", userId);
		parameters.put("configDocProcessEnum", configDocProcessEnum);
		parameters.put("registerMentors", registerMentors);
		return (Long) super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> findUsersByCompanyType(Long companyId, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		String hql = "select o.user from CompanyUsers o where o.company.id = :companyId and o.companyUserType = :configDocProcessEnum";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("configDocProcessEnum", configDocProcessEnum);
		return (List<Users>) super.getList(hql, parameters);
	}
	
	public Integer countUsersByCompanyType(Long companyId, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		String hql = "select count(o) from CompanyUsers o where o.company.id = :companyId and o.companyUserType = :configDocProcessEnum";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("configDocProcessEnum", configDocProcessEnum);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	public Long findLearnerCount(Long userID) throws Exception {
		String hql = "select count(o.user) from CompanyLearners o where o.user.id = :userID ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("userID", userID);
		return (Long) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find by company.
	 *
	 * @param companyId
	 *            the company id
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUsers> findByCompany(Long companyId) throws Exception {
		String hql = "select o from CompanyUsers o left join fetch o.company z left join fetch o.user x left join fetch x.residentialAddress ra left join fetch ra.municipality left join fetch x.postalAddress pa left join fetch pa.municipality left join fetch o.position p left join fetch o.userResponsibility ur where o.company.id = :companyId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		return (List<CompanyUsers>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> findUsersByCompany(Long companyId) throws Exception {
		String hql = "select o.user from CompanyUsers o where o.company.id = :companyId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		return (List<Users>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> findDistinctUsersByCompany(Long companyId) throws Exception {
		String hql = "select distinct(o.user) from CompanyUsers o where o.company.id = :companyId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		return (List<Users>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> findCompanyUsersByCompanyPopulateRoles(Long companyId) throws Exception {
		String hql = "select distinct(o.user) from CompanyUsers o where o.company.id = :companyId and o.position is not null ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		return (List<Users>) super.getList(hql, parameters);
	}
	

	@SuppressWarnings("unchecked")
	public List<Users> findByCompanyNotProcess(Long companyId) throws Exception {
		String hql = "select o.user from CompanyUsers o where o.company.id = :companyId and o.companyUserType is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		return (List<Users>) super.getList(hql, parameters);
	}

	/**
	 * Find by company not SDF.
	 *
	 * @param companyId
	 *            the company id
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUsers> findByCompanyNotSDF(Long companyId) throws Exception {

		String hql = "select o from CompanyUsers o " + "left join fetch o.company z " + "left join fetch o.user x " + "left join fetch x.residentialAddress ra " + "left join fetch ra.municipality " + "left join fetch x.postalAddress pa " + "left join fetch pa.municipality " + "left join fetch o.position p " + "left join fetch o.userResponsibility ur " + "where o.company.id = :companyId " + "and o.companyUserType is null";

		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		return (List<CompanyUsers>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<CompanyUsers> findByCompanyResponsibility(Long companyId, ConfigDocProcessEnum forProcess) throws Exception {
		String hql = "select distinct(o.companyUsers) from UsersResponsibilities o " + "where o.companyUsers.company.id = :companyId " + "and o.userResponsibility.forProcess = :forProcess";

		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("forProcess", forProcess);
		return (List<CompanyUsers>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyUsers> findByCompanyAndUserPosition(Long companyId, Long userPosistion) throws Exception {
		String hql = "select distinct(o.companyUsers) from UsersResponsibilities o " + "where o.companyUsers.company.id = :companyId "
		        + "and o.companyUsers.position.id = :userPosistion";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("userPosistion", userPosistion);
		return (List<CompanyUsers>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> findUsersByCompanyResponsibility(Long companyId, ConfigDocProcessEnum forProcess) throws Exception {
		String hql = "select distinct(o.companyUsers.user) from UsersResponsibilities o " + "where o.companyUsers.company.id = :companyId " + "and o.userResponsibility.forProcess = :forProcess";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("forProcess", forProcess);
		return (List<Users>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyUsers> findByCompanyResponsibilityAndUserPosition(Long companyId, ConfigDocProcessEnum forProcess, Long userPosistion) throws Exception {
		String hql = "select distinct(o.companyUsers) from UsersResponsibilities o " + "where o.companyUsers.company.id = :companyId " 
				+ "and o.userResponsibility.forProcess = :forProcess "
		        + "and o.companyUsers.position.id = :userPosistion";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("forProcess", forProcess);
		parameters.put("userPosistion", userPosistion);
		return (List<CompanyUsers>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyUsers> findByCompanyUserPosition(Long companyId, Long userPosistion) throws Exception {
		String hql = "select distinct(o) from CompanyUsers o " + "where o.company.id = :companyId " 
		        + "and o.position.id = :userPosistion";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("userPosistion", userPosistion);
		return (List<CompanyUsers>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyUsers> findByCompanyNotInResponsibilityAndUserPosition(Long companyId, ConfigDocProcessEnum forProcess, Long userPosistion) throws Exception {
		String hql = "select distinct(o.companyUsers) from UsersResponsibilities o " + "where o.companyUsers.company.id = :companyId " 
				+ "and o.userResponsibility.forProcess <> :forProcess "
		        + "and o.companyUsers.position.id = :userPosistion";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("forProcess", forProcess);
		parameters.put("userPosistion", userPosistion);
		return (List<CompanyUsers>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<CompanyUsers> findByCompanyWithPrimary(Long companyId) throws Exception {

		String hql = "select o from CompanyUsers o " + "left join fetch o.company z " + "left join fetch o.user x " + "left join fetch x.residentialAddress ra " + "left join fetch ra.municipality " + "left join fetch x.postalAddress pa " + "left join fetch pa.municipality " + "left join fetch o.position p " + "left join fetch o.userResponsibility ur " + "where (o.company.id = :companyId and o.position is not null)" + "or (o.company.id = :companyId and o.user.id in ("
				+ "select k.sdf.id from SDFCompany k where k.company.id = :companyId and  k.sdfType.code = :primary" + "))";

		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("primary", "PRM");
		return (List<CompanyUsers>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> findUsersByCompanyNotInResponsibilityAndUserPosition(Long companyId, ConfigDocProcessEnum forProcess, Long userPosistion) throws Exception {
		String hql = "select distinct(o.companyUsers.user) from UsersResponsibilities o " + "where o.companyUsers.company.id = :companyId " 
				+ "and o.userResponsibility.forProcess <> :forProcess "
		        + "and o.companyUsers.position.id = :userPosistion";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("forProcess", forProcess);
		parameters.put("userPosistion", userPosistion);
		return (List<Users>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> findUsersByCompanyInResponsibilityAndUserPosition(Long userId, ConfigDocProcessEnum forProcess, Long userPosistion) throws Exception {
		String hql = "select distinct(o.companyUsers.user) from UsersResponsibilities o " + "where o.companyUsers.user.id = :userId " 
				+ "and o.userResponsibility.forProcess <> :forProcess "
		        + "and o.companyUsers.position.id = :userPosistion";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("userId", userId);
		parameters.put("forProcess", forProcess);
		parameters.put("userPosistion", userPosistion);
		return (List<Users>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> findUsersByCompanyInResponsibilityAndUser(Long userId, ConfigDocProcessEnum forProcess) throws Exception {
		String hql = "select distinct(o.companyUsers.user) from UsersResponsibilities o " + "where o.companyUsers.user.id = :userId " 
				+ "and o.userResponsibility.forProcess <> :forProcess ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("userId", userId);
		parameters.put("forProcess", forProcess);
		return (List<Users>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> findUsersByCompanyNotInResponsibilityAndUser(Long companyId, ConfigDocProcessEnum forProcess, Long userId) throws Exception {
		String hql = "select distinct(o.companyUsers.user) from UsersResponsibilities o " + "where o.companyUsers.company.id = :companyId " 
				+ "and o.userResponsibility.forProcess <> :forProcess "
		        + "and o.companyUsers.user.id = :userId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("forProcess", forProcess);
		parameters.put("userId", userId);
		return (List<Users>) super.getList(hql, parameters);
	}
	
	public List<?> sortAndFilterSearch(Class<?> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		String hql = "select o from " + entity.getName() + " o " + " where o.position is not null ";
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
					
					if (entry.getKey().equals("companyName") || entry.getKey().equals("levyNumber") || entry.getKey().equals("email")) {
						hql += " and o.company." + entry.getKey() + " like " + " :" + hvar;
						ft = false;
					}
					else {
						hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					}
				} else {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}

		if (sortField != null) {
			
			switch (sortOrder) {
			case ASCENDING:
				if (sortField.equals("companyName") || sortField.equals("levyNumber") ||sortField.equals("email")) {
					hql += " order by o.company." + sortField + " asc ";
				}
				else{
					hql += " order by o." + sortField + " asc ";
				}
				
				break;
			case DESCENDING:
				if (sortField.equals("companyName") || sortField.equals("levyNumber") ||sortField.equals("email")) {
					hql += " order by o.company." + sortField + " asdescc ";
				}
				
				else{
					hql += " order by o." + sortField + " desc ";
				}
				break;
			default:
				break;
		}
		}
		return getList(hql, filters, startingAt, pageSize);
	}
	
	public int countSearch(Class<?> entity, Map<String, Object> filters) {
		String hql = "select count(o) from " + entity.getName() + " o " + " where o.position is not null ";
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
					if (entry.getKey().equals("companyName") || entry.getKey().equals("levyNumber") || entry.getKey().equals("email")) {
						hql += " and o.company." + entry.getKey() + " like " + " :" + hvar;
						ft = false;
					}
					else {
						hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					}
					
				} else {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}
	
	  @SuppressWarnings("unchecked")
	    public List<CompanyUsers> findByUserAndCompanyAndCompanyUserTypeNotNullDes(Long companyId, Long userId,ConfigDocProcessEnum configDocProcessEnum) throws Exception {
	        String hql = "select o from CompanyUsers o left join fetch o.company c left join fetch o.user u left join fetch o.position p where c.id = :companyId and u.id = :userId  and o.companyUserType = :configDocProcessEnum and o.designation is not null";
	        Map<String, Object> parameters = new Hashtable<String, Object>();
	        parameters.put("companyId", companyId);
	        parameters.put("userId", userId);
	        parameters.put("configDocProcessEnum", configDocProcessEnum);
	        return (List<CompanyUsers>) super.getList(hql, parameters);
	    }
	    @SuppressWarnings("unchecked")
	    public List<CompanyUsers> findByUserAndCompanyAndTypeNullDes(Long companyId, Long userId,ConfigDocProcessEnum configDocProcessEnum) throws Exception {
	        String hql = "select o from CompanyUsers o left join fetch o.company c left join fetch o.user u left join fetch o.position p where c.id = :companyId and u.id = :userId  and o.companyUserType = :configDocProcessEnum and o.designation is null";
	        Map<String, Object> parameters = new Hashtable<String, Object>();
	        parameters.put("companyId", companyId);
	        parameters.put("userId", userId);
	        parameters.put("configDocProcessEnum", configDocProcessEnum);
	        return (List<CompanyUsers>) super.getList(hql, parameters);
	    }
		
		@SuppressWarnings("unchecked")
	    public List<CompanyUsers> findTPContact(Long companyId, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
	        String hql = "select o from CompanyUsers o" + leftJoinsNoVar + "where o.company.id = :companyId and o.companyUserType = :configDocProcessEnum and o.designation is not null";
	        Map<String, Object> parameters = new Hashtable<String, Object>();
	        parameters.put("companyId", companyId);
	        parameters.put("configDocProcessEnum", configDocProcessEnum);
	        return (List<CompanyUsers>) super.getList(hql, parameters);
		}
	    @SuppressWarnings("unchecked")
	    public List<CompanyUsers> findTPAssessorMod(Long companyId, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
	        String hql = "select o from CompanyUsers o" + leftJoinsNoVar + "where o.company.id = :companyId and o.companyUserType = :configDocProcessEnum and o.assessorModType is not null";
	        Map<String, Object> parameters = new Hashtable<String, Object>();
	        parameters.put("companyId", companyId);
	        parameters.put("configDocProcessEnum", configDocProcessEnum);
	        return (List<CompanyUsers>) super.getList(hql, parameters);
	    }
	    
	    @SuppressWarnings("unchecked")
	    public List<CompanyUsers> findTPAssessorModByModType(Long companyId, ConfigDocProcessEnum configDocProcessEnum, AssessorModType assessorModType) throws Exception {
	        String hql = "select o from CompanyUsers o" + leftJoinsNoVar + "where o.company.id = :companyId and o.companyUserType = :configDocProcessEnum and o.assessorModType = :assessorModType";
	        Map<String, Object> parameters = new Hashtable<String, Object>();
	        parameters.put("companyId", companyId);
	        parameters.put("configDocProcessEnum", configDocProcessEnum);
	        parameters.put("assessorModType", assessorModType);
	        return (List<CompanyUsers>) super.getList(hql, parameters);
	    }
	    
	    @SuppressWarnings("unchecked")
	    public List<CompanyUsers> findTPAssessorModByModTypeList(Long companyId, ConfigDocProcessEnum configDocProcessEnum, List<AssessorModType> assessorModTypeList) throws Exception {
	        String hql = "select o from CompanyUsers o" + leftJoinsNoVar + "where o.company.id = :companyId and o.companyUserType = :configDocProcessEnum ";
	        Map<String, Object> parameters = new Hashtable<String, Object>();
	        parameters.put("companyId", companyId);
	        parameters.put("configDocProcessEnum", configDocProcessEnum);
	        if (!assessorModTypeList.isEmpty()) {
				int counter = 1;
				hql += " and (  ";
				for (AssessorModType assessorModType : assessorModTypeList) {
					if (counter == assessorModTypeList.size()) {
						hql += " o.assessorModType = :assessorModType"+counter;
					} else {
						hql += " o.assessorModType = :assessorModType"+counter+" or ";
					}
					parameters.put("assessorModType" + counter, assessorModType);
					counter++;
				}
				hql += ") ";
			}
	        return (List<CompanyUsers>) super.getList(hql, parameters);
	    }
	    
		@SuppressWarnings("unchecked")
		public List<Company> findCompanyProvider() throws Exception {
			String sql = "select c.* from company c inner join training_provider_application tpa on c.id = tpa.company_id inner join training_provider_monitoring tpm on tpm.company_id = c.id  where tpm.monitoring_date between tpa.start_date and tpa.expiriy_date and (select count(tpmt.company_id) from training_provider_monitoring tpmt having count(tpmt.company_id)>2)";
			return (List<Company>) super.nativeSelectSqlList(sql, Company.class);
		}
		
	@SuppressWarnings("unchecked")
	public List<CompanyUsers> findByCompanyIdProcessAndUserId(Long companyId, ConfigDocProcessEnum forProcess, Long userId) throws Exception {
		String hql = "select o from CompanyUsers o where o.company.id = :companyId and o.companyUserType = :forProcess and o.user.id = :userId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("forProcess", forProcess);
		parameters.put("userId", userId);
		parameters.put("sdpDes", true);
		return (List<CompanyUsers>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyUsers> findByCompanyIdProcessAndUserIdWithSdpDesignation(Long companyId, ConfigDocProcessEnum forProcess, Long userId, Boolean sdpDesignation) throws Exception {
		String hql = "select o from CompanyUsers o where o.company.id = :companyId and o.companyUserType = :forProcess and o.user.id = :userId and o.designation.sdpDesignation = :sdpDesignation";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("forProcess", forProcess);
		parameters.put("userId", userId);
		parameters.put("sdpDesignation", sdpDesignation);
		return (List<CompanyUsers>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> findSdpUsersByCompanyId(Long companyId, Boolean sdpDesignation) throws Exception {
		String hql = "select o.user from CompanyUsers o where o.company.id = :companyId and o.designation.sdpDesignation = :sdpDesignation";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("sdpDesignation", sdpDesignation);
		return (List<Users>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyUsers> findAllSdpCompanyUsersLinkedToCompany(Boolean sdpDesignation) throws Exception {
		String hql = "select o from CompanyUsers o where o.company.id is not null and o.designation.sdpDesignation = :sdpDesignation";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("sdpDesignation", sdpDesignation);
		return (List<CompanyUsers>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyUsers> findAllAssModCompanyUsersLinkedToCompany() throws Exception {
		String hql = "select o from CompanyUsers o where o.company.id is not null and o.assessorModType is not null";
		return (List<CompanyUsers>) super.getList(hql);
	}
}
