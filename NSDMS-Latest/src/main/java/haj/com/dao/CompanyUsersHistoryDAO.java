package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.CompanyUsersHistory;
import haj.com.entity.Users;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyUsersHistoryDAO.
 */
public class CompanyUsersHistoryDAO extends AbstractDAO {

	/** The Constant leftJoinsNoVar. */
	private static final String leftJoinsNoVar = " " + "left join fetch o.company left join fetch o.user" + " ";
	
	/** The Constant leftJoinsVar. */
	private static final String leftJoinsVar = " " + "left join fetch o.company z left join fetch o.user x" + " ";

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all CompanyUsersHistoryHistory.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.CompanyUsersHistory}
	 * @throws Exception             global exception
	 * @see CompanyUsersHistory
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUsersHistory> allCompanyUsersHistory() throws Exception {
		return (List<CompanyUsersHistory>) super.getList("select o from CompanyUsersHistory o" + leftJoinsNoVar);
	}

	/**
	 * Find users type of company.
	 *
	 * @param userType the user type
	 * @param companyId the company id
	 * @return the list
	 */
	public List<CompanyUsersHistory> findUsersTypeOfCompany(ConfigDocProcessEnum userType, Long companyId) {
		String hql = "select o from CompanyUsersHistory o" + leftJoinsVar + "left join fetch x.residentialAddress left join fetch x.postalAddress left join fetch o.userResponsibility ur where o.company.id = :companyId and o.companyUserType = :userType";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("userType", userType);
		return (List<CompanyUsersHistory>) super.getList(hql, parameters);
	}

	/**
	 * Get all CompanyUsersHistory between from and noRows.
	 *
	 * @author TechFinium
	 * @param from            the from
	 * @param noRows            the no rows
	 * @return a list of {@link haj.com.entity.CompanyUsersHistory}
	 * @throws Exception             global exception
	 * @see CompanyUsersHistory
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUsersHistory> allCompanyUsersHistory(Long from, int noRows) throws Exception {
		String hql = "select o from CompanyUsersHistory o" + leftJoinsNoVar;
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<CompanyUsersHistory>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id            the id
	 * @return a {@link haj.com.entity.CompanyUsersHistory}
	 * @throws Exception             global exception
	 * @see CompanyUsersHistory
	 */
	public CompanyUsersHistory findByKey(Long id) throws Exception {
		String hql = "select o from CompanyUsersHistory o" + leftJoinsNoVar + "where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (CompanyUsersHistory) super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find object by CompanyUsersHistoryID.
	 *
	 * @author TechFinium
	 * @param CompanyUsersHistory the CompanyUsersHistory
	 * @return a {@link haj.com.entity.CompanyUsersHistory}
	 * @throws Exception             global exception
	 * @see CompanyUsersHistory
	 */
	public CompanyUsersHistory findByCompanyUser(Long id) throws Exception {
		String joins=" left join fetch o.company z "
				+ "left join fetch o.user x "
				+ "left join fetch x.residentialAddress ra "
				+ "left join fetch ra.municipality "
				+ "left join fetch x.postalAddress pa "
				+ "left join fetch pa.municipality "
				+ "left join fetch o.position p "
				+ "left join fetch o.userResponsibility ur ";
		
		String hql = "select o from CompanyUsersHistory o" + joins + "where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (CompanyUsersHistory) super.getUniqueResult(hql, parameters);
	}
	
	
	/**
	 * Get all by Company user history id CompanyUsersHistoryHistory.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.CompanyUsersHistory}
	 * @throws Exception             global exception
	 * @see CompanyUsersHistory
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUsersHistory> findAllByCompanyUserID(Long id) throws Exception {
		String joins=" left join fetch o.company z "
				+ "left join fetch o.user x "
				+ "left join fetch x.residentialAddress ra "
				+ "left join fetch ra.municipality "
				+ "left join fetch x.postalAddress pa "
				+ "left join fetch pa.municipality "
				+ "left join fetch o.position p "
				+ "left join fetch o.userResponsibility ur ";
		String hql = "select o from CompanyUsersHistory o" + joins + "where o.id = :id";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		
		return (List<CompanyUsersHistory>) super.getList(hql, parameters);
	}
	
	/**
	 * Get all by Company forCompanyUser.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.CompanyUsersHistory}
	 * @throws Exception             global exception
	 * @see CompanyUsersHistory
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUsersHistory> findAllByForID(Long id) throws Exception {
		String joins=" left join fetch o.company z "
				+ "left join fetch o.user x "
				+ "left join fetch x.residentialAddress ra "
				+ "left join fetch ra.municipality "
				+ "left join fetch x.postalAddress pa "
				+ "left join fetch pa.municipality "
				+ "left join fetch o.position p "
				+ "left join fetch o.forCompanyUser f "
				+ "left join fetch o.userResponsibility ur ";
		String hql = "select o from CompanyUsersHistory o" + joins + "where f.id = :id order by o.createDate desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		
		return (List<CompanyUsersHistory>) super.getList(hql, parameters);
	}
	

	/**
	 * Find by name.
	 *
	 * @param description the description
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUsersHistory> findByName(String description) throws Exception {
		String hql = "select o from CompanyUsersHistory o left join fetch o.company x left join fetch o.user z left join fetch o.userResponsibility ur where z.firstName like :description or x.companyName like :description";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<CompanyUsersHistory>) super.getList(hql, parameters);
	}
	


	/**
	 * Find object by Users_id and Company_id.
	 *
	 * @author TechFinium
	 * @param companyId the company id
	 * @param userId the user id
	 * @return a {@link haj.com.entity.CompanyUsersHistory}
	 * @throws Exception             global exception
	 * @see CompanyUsersHistory
	 */
	public CompanyUsersHistory findByUserAndCompany(Long companyId, Long userId) throws Exception {
		String hql = "select o from CompanyUsersHistory o left join fetch o.company c left join fetch o.user u left join fetch o.position p where c.id = :companyId and u.id = :userId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("userId", userId);
		return (CompanyUsersHistory) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find by user.
	 *
	 * @param userId the user id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUsersHistory> findByUser(Long userId) throws Exception {
		String hql = "select o from CompanyUsersHistory o left join fetch o.company x where o.user.id = :userId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("userId", userId);
		return (List<CompanyUsersHistory>) super.getList(hql, parameters);
	}

	/**
	 * Find by company type.
	 *
	 * @param companyId the company id
	 * @param configDocProcessEnum the config doc process enum
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUsersHistory> findByCompanyType(Long companyId, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		String hql = "select o from CompanyUsersHistory o" + leftJoinsNoVar + "where o.company.id = :companyId and o.companyUserType = :configDocProcessEnum";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("configDocProcessEnum", configDocProcessEnum);
		return (List<CompanyUsersHistory>) super.getList(hql, parameters);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Users> findUsersByCompanyType(Long companyId, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		String hql = "select o.user from CompanyUsersHistory o" + leftJoinsNoVar + "where o.company.id = :companyId and o.companyUserType = :configDocProcessEnum";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("configDocProcessEnum", configDocProcessEnum);
		return (List<Users>) super.getList(hql, parameters);
	}
	
	/**
	 * Find by company.
	 *
	 * @param companyId the company id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUsersHistory> findByCompany(Long companyId) throws Exception {
		String hql = "select o from CompanyUsersHistory o left join fetch o.company z left join fetch o.user x left join fetch x.residentialAddress ra left join fetch ra.municipality left join fetch x.postalAddress pa left join fetch pa.municipality left join fetch o.position p left join fetch o.userResponsibility ur where o.company.id = :companyId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		return (List<CompanyUsersHistory>) super.getList(hql, parameters);
	}
	
	/**
	 * Find by company not SDF.
	 *
	 * @param companyId the company id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUsersHistory> findByCompanyNotSDF(Long companyId) throws Exception {
		
		String hql = "select o from CompanyUsersHistory o "
				+ "left join fetch o.company z "
				+ "left join fetch o.user x "
				+ "left join fetch x.residentialAddress ra "
				+ "left join fetch ra.municipality "
				+ "left join fetch x.postalAddress pa "
				+ "left join fetch pa.municipality "
				+ "left join fetch o.position p "
				+ "left join fetch o.userResponsibility ur "
				+ "where o.company.id = :companyId "
				+ "and o.companyUserType is null";
		
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		return (List<CompanyUsersHistory>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyUsersHistory> findByCompanyResponsibility(Long companyId, ConfigDocProcessEnum forProcess) throws Exception {
		String hql = "select distinct(o.CompanyUsersHistory) from UsersResponsibilities o "
				+ "where o.CompanyUsersHistory.company.id = :companyId "
				+ "and o.userResponsibility.forProcess = :forProcess";
		
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("forProcess", forProcess);
		return (List<CompanyUsersHistory>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyUsersHistory> findByCompanyWithPrimary(Long companyId) throws Exception {
		
		String hql = "select o from CompanyUsersHistory o "
				+ "left join fetch o.company z "
				+ "left join fetch o.user x "
				+ "left join fetch x.residentialAddress ra "
				+ "left join fetch ra.municipality "
				+ "left join fetch x.postalAddress pa "
				+ "left join fetch pa.municipality "
				+ "left join fetch o.position p "
				+ "left join fetch o.userResponsibility ur "
				+ "where (o.company.id = :companyId and o.position is not null)"
				+ "or (o.company.id = :companyId and o.user.id in ("
				+ "select k.sdf.id from SDFCompany k where k.company.id = :companyId and  k.sdfType.code = :primary"
				+ "))";
		
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		parameters.put("primary", "PRM");
		return (List<CompanyUsersHistory>) super.getList(hql, parameters);
	}
}
